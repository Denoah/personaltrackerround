package androidx.camera.view;

import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import androidx.camera.core.Preview.SurfaceProvider;
import androidx.camera.core.SurfaceRequest;
import androidx.core.content.ContextCompat;

final class SurfaceViewImplementation
  implements PreviewView.Implementation
{
  private static final String TAG = "SurfaceViewPreviewView";
  private Preview.SurfaceProvider mSurfaceProvider = new Preview.SurfaceProvider()
  {
    public void onSurfaceRequested(SurfaceRequest paramAnonymousSurfaceRequest)
    {
      SurfaceViewImplementation.this.mSurfaceView.post(new _..Lambda.SurfaceViewImplementation.1.cfCQQ8H8Jw_4K9II0AfvdFRGiok(this, paramAnonymousSurfaceRequest));
    }
  };
  final SurfaceRequestCallback mSurfaceRequestCallback = new SurfaceRequestCallback();
  TransformableSurfaceView mSurfaceView;
  
  SurfaceViewImplementation() {}
  
  public Preview.SurfaceProvider getSurfaceProvider()
  {
    return this.mSurfaceProvider;
  }
  
  public void init(FrameLayout paramFrameLayout)
  {
    TransformableSurfaceView localTransformableSurfaceView = new TransformableSurfaceView(paramFrameLayout.getContext());
    this.mSurfaceView = localTransformableSurfaceView;
    localTransformableSurfaceView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
    paramFrameLayout.addView(this.mSurfaceView);
    this.mSurfaceView.getHolder().addCallback(this.mSurfaceRequestCallback);
  }
  
  public void onDisplayChanged() {}
  
  class SurfaceRequestCallback
    implements SurfaceHolder.Callback
  {
    private Size mCurrentSurfaceSize;
    private SurfaceRequest mSurfaceRequest;
    private Size mTargetSize;
    
    SurfaceRequestCallback() {}
    
    private void cancelPreviousRequest()
    {
      if (this.mSurfaceRequest != null)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Request canceled: ");
        localStringBuilder.append(this.mSurfaceRequest);
        Log.d("SurfaceViewPreviewView", localStringBuilder.toString());
        this.mSurfaceRequest.willNotProvideSurface();
        this.mSurfaceRequest = null;
      }
      this.mTargetSize = null;
    }
    
    private boolean tryToComplete()
    {
      Surface localSurface = SurfaceViewImplementation.this.mSurfaceView.getHolder().getSurface();
      if (this.mSurfaceRequest != null)
      {
        Size localSize = this.mTargetSize;
        if ((localSize != null) && (localSize.equals(this.mCurrentSurfaceSize)))
        {
          Log.d("SurfaceViewPreviewView", "Surface set on Preview.");
          this.mSurfaceRequest.provideSurface(localSurface, ContextCompat.getMainExecutor(SurfaceViewImplementation.this.mSurfaceView.getContext()), _..Lambda.SurfaceViewImplementation.SurfaceRequestCallback.ylR0NKdu9BLnNlHrGPzfpYLWdGI.INSTANCE);
          this.mSurfaceRequest = null;
          this.mTargetSize = null;
          return true;
        }
      }
      return false;
    }
    
    void setSurfaceRequest(SurfaceRequest paramSurfaceRequest)
    {
      cancelPreviousRequest();
      this.mSurfaceRequest = paramSurfaceRequest;
      paramSurfaceRequest = paramSurfaceRequest.getResolution();
      this.mTargetSize = paramSurfaceRequest;
      if (!tryToComplete())
      {
        Log.d("SurfaceViewPreviewView", "Wait for new Surface creation.");
        SurfaceViewImplementation.this.mSurfaceView.getHolder().setFixedSize(paramSurfaceRequest.getWidth(), paramSurfaceRequest.getHeight());
      }
    }
    
    public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
    {
      paramSurfaceHolder = new StringBuilder();
      paramSurfaceHolder.append("Surface changed. Size: ");
      paramSurfaceHolder.append(paramInt2);
      paramSurfaceHolder.append("x");
      paramSurfaceHolder.append(paramInt3);
      Log.d("SurfaceViewPreviewView", paramSurfaceHolder.toString());
      this.mCurrentSurfaceSize = new Size(paramInt2, paramInt3);
      tryToComplete();
    }
    
    public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
    {
      Log.d("SurfaceViewPreviewView", "Surface created.");
    }
    
    public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
    {
      Log.d("SurfaceViewPreviewView", "Surface destroyed.");
      this.mCurrentSurfaceSize = null;
      cancelPreviousRequest();
    }
  }
}

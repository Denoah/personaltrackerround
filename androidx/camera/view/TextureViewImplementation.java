package androidx.camera.view;

import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.util.Pair;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import androidx.camera.core.Preview.SurfaceProvider;
import androidx.camera.core.SurfaceRequest;
import androidx.camera.core.SurfaceRequest.Result;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.content.ContextCompat;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;

public class TextureViewImplementation
  implements PreviewView.Implementation
{
  private static final String TAG = "TextureViewImpl";
  private FrameLayout mParent;
  private Size mResolution;
  ListenableFuture<SurfaceRequest.Result> mSurfaceReleaseFuture;
  SurfaceRequest mSurfaceRequest;
  SurfaceTexture mSurfaceTexture;
  TextureView mTextureView;
  
  public TextureViewImplementation() {}
  
  private void correctPreviewForCenterCrop(View paramView, TextureView paramTextureView, Size paramSize)
  {
    paramSize = ScaleTypeTransform.getFillScaleWithBufferAspectRatio(paramView, paramTextureView, paramSize);
    paramTextureView.setScaleX(((Float)paramSize.first).floatValue());
    paramTextureView.setScaleY(((Float)paramSize.second).floatValue());
    paramView = ScaleTypeTransform.getOriginOfCenteredView(paramView, paramTextureView);
    paramTextureView.setX(paramView.x);
    paramTextureView.setY(paramView.y);
    paramTextureView.setRotation(-ScaleTypeTransform.getRotationDegrees(paramTextureView));
  }
  
  private void initInternal()
  {
    TextureView localTextureView = new TextureView(this.mParent.getContext());
    this.mTextureView = localTextureView;
    localTextureView.setLayoutParams(new FrameLayout.LayoutParams(this.mResolution.getWidth(), this.mResolution.getHeight()));
    this.mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener()
    {
      public void onSurfaceTextureAvailable(SurfaceTexture paramAnonymousSurfaceTexture, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        TextureViewImplementation.this.mSurfaceTexture = paramAnonymousSurfaceTexture;
        TextureViewImplementation.this.tryToProvidePreviewSurface();
      }
      
      public boolean onSurfaceTextureDestroyed(final SurfaceTexture paramAnonymousSurfaceTexture)
      {
        TextureViewImplementation.this.mSurfaceTexture = null;
        if ((TextureViewImplementation.this.mSurfaceRequest == null) && (TextureViewImplementation.this.mSurfaceReleaseFuture != null))
        {
          Futures.addCallback(TextureViewImplementation.this.mSurfaceReleaseFuture, new FutureCallback()
          {
            public void onFailure(Throwable paramAnonymous2Throwable)
            {
              throw new IllegalStateException("SurfaceReleaseFuture did not complete nicely.", paramAnonymous2Throwable);
            }
            
            public void onSuccess(SurfaceRequest.Result paramAnonymous2Result)
            {
              boolean bool;
              if (paramAnonymous2Result.getResultCode() != 3) {
                bool = true;
              } else {
                bool = false;
              }
              Preconditions.checkState(bool, "Unexpected result from SurfaceRequest. Surface was provided twice.");
              paramAnonymousSurfaceTexture.release();
            }
          }, ContextCompat.getMainExecutor(TextureViewImplementation.this.mTextureView.getContext()));
          return false;
        }
        return true;
      }
      
      public void onSurfaceTextureSizeChanged(SurfaceTexture paramAnonymousSurfaceTexture, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        paramAnonymousSurfaceTexture = new StringBuilder();
        paramAnonymousSurfaceTexture.append("onSurfaceTextureSizeChanged(width:");
        paramAnonymousSurfaceTexture.append(paramAnonymousInt1);
        paramAnonymousSurfaceTexture.append(", height: ");
        paramAnonymousSurfaceTexture.append(paramAnonymousInt2);
        paramAnonymousSurfaceTexture.append(" )");
        Log.d("TextureViewImpl", paramAnonymousSurfaceTexture.toString());
      }
      
      public void onSurfaceTextureUpdated(SurfaceTexture paramAnonymousSurfaceTexture) {}
    });
    this.mParent.removeAllViews();
    this.mParent.addView(this.mTextureView);
  }
  
  public Preview.SurfaceProvider getSurfaceProvider()
  {
    return new _..Lambda.TextureViewImplementation.XgqhEkmrW85l6BejpQN4wcoi9P0(this);
  }
  
  public void init(FrameLayout paramFrameLayout)
  {
    this.mParent = paramFrameLayout;
  }
  
  public void onDisplayChanged()
  {
    FrameLayout localFrameLayout = this.mParent;
    if (localFrameLayout != null)
    {
      TextureView localTextureView = this.mTextureView;
      if (localTextureView != null)
      {
        Size localSize = this.mResolution;
        if (localSize != null) {
          correctPreviewForCenterCrop(localFrameLayout, localTextureView, localSize);
        }
      }
    }
  }
  
  void tryToProvidePreviewSurface()
  {
    Object localObject1 = this.mResolution;
    if (localObject1 != null)
    {
      Object localObject2 = this.mSurfaceTexture;
      if ((localObject2 != null) && (this.mSurfaceRequest != null))
      {
        ((SurfaceTexture)localObject2).setDefaultBufferSize(((Size)localObject1).getWidth(), this.mResolution.getHeight());
        localObject1 = new Surface(this.mSurfaceTexture);
        localObject2 = CallbackToFutureAdapter.getFuture(new _..Lambda.TextureViewImplementation.gXACiFKHKklRXpW_l9D1anoNeic(this, (Surface)localObject1));
        this.mSurfaceReleaseFuture = ((ListenableFuture)localObject2);
        ((ListenableFuture)localObject2).addListener(new _..Lambda.TextureViewImplementation.DcccDK6Uc6TT8LKCNr9D4Ob6X30(this, (Surface)localObject1, (ListenableFuture)localObject2), ContextCompat.getMainExecutor(this.mTextureView.getContext()));
        this.mSurfaceRequest = null;
        correctPreviewForCenterCrop(this.mParent, this.mTextureView, this.mResolution);
      }
    }
  }
}

package androidx.camera.view;

import android.content.Context;
import android.util.Log;
import android.util.Rational;
import android.util.Size;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraSelector.Builder;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCapture.Builder;
import androidx.camera.core.ImageCapture.Metadata;
import androidx.camera.core.ImageCapture.OnImageCapturedCallback;
import androidx.camera.core.ImageCapture.OnImageSavedCallback;
import androidx.camera.core.ImageCapture.OutputFileOptions.Builder;
import androidx.camera.core.Preview;
import androidx.camera.core.Preview.Builder;
import androidx.camera.core.UseCase;
import androidx.camera.core.VideoCapture;
import androidx.camera.core.VideoCapture.OnVideoSavedCallback;
import androidx.camera.core.ZoomState;
import androidx.camera.core.impl.LensFacingConverter;
import androidx.camera.core.impl.VideoCaptureConfig.Builder;
import androidx.camera.core.impl.utils.CameraOrientationUtil;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.util.Preconditions;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.OnLifecycleEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

final class CameraXModule
{
  private static final Rational ASPECT_RATIO_16_9 = new Rational(16, 9);
  private static final Rational ASPECT_RATIO_3_4 = new Rational(3, 4);
  private static final Rational ASPECT_RATIO_4_3 = new Rational(4, 3);
  private static final Rational ASPECT_RATIO_9_16 = new Rational(9, 16);
  public static final String TAG = "CameraXModule";
  private static final float UNITY_ZOOM_SCALE = 1.0F;
  private static final float ZOOM_NOT_SUPPORTED = 1.0F;
  Camera mCamera;
  Integer mCameraLensFacing = Integer.valueOf(1);
  ProcessCameraProvider mCameraProvider;
  private final CameraView mCameraView;
  private CameraView.CaptureMode mCaptureMode = CameraView.CaptureMode.IMAGE;
  LifecycleOwner mCurrentLifecycle;
  private final LifecycleObserver mCurrentLifecycleObserver = new LifecycleObserver()
  {
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(LifecycleOwner paramAnonymousLifecycleOwner)
    {
      if (paramAnonymousLifecycleOwner == CameraXModule.this.mCurrentLifecycle)
      {
        CameraXModule.this.clearCurrentLifecycle();
        CameraXModule.this.mPreview.setSurfaceProvider(null);
      }
    }
  };
  private int mFlash = 2;
  private ImageCapture mImageCapture;
  private final ImageCapture.Builder mImageCaptureBuilder;
  private long mMaxVideoDuration = -1L;
  private long mMaxVideoSize = -1L;
  private LifecycleOwner mNewLifecycle;
  Preview mPreview;
  private final Preview.Builder mPreviewBuilder;
  private VideoCapture mVideoCapture;
  private final VideoCaptureConfig.Builder mVideoCaptureConfigBuilder;
  final AtomicBoolean mVideoIsRecording = new AtomicBoolean(false);
  
  CameraXModule(CameraView paramCameraView)
  {
    this.mCameraView = paramCameraView;
    Futures.addCallback(ProcessCameraProvider.getInstance(paramCameraView.getContext()), new FutureCallback()
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        throw new RuntimeException("CameraX failed to initialize.", paramAnonymousThrowable);
      }
      
      public void onSuccess(ProcessCameraProvider paramAnonymousProcessCameraProvider)
      {
        Preconditions.checkNotNull(paramAnonymousProcessCameraProvider);
        CameraXModule.this.mCameraProvider = paramAnonymousProcessCameraProvider;
        if (CameraXModule.this.mCurrentLifecycle != null)
        {
          paramAnonymousProcessCameraProvider = CameraXModule.this;
          paramAnonymousProcessCameraProvider.bindToLifecycle(paramAnonymousProcessCameraProvider.mCurrentLifecycle);
        }
      }
    }, CameraXExecutors.mainThreadExecutor());
    this.mPreviewBuilder = new Preview.Builder().setTargetName("Preview");
    this.mImageCaptureBuilder = new ImageCapture.Builder().setTargetName("ImageCapture");
    this.mVideoCaptureConfigBuilder = new VideoCaptureConfig.Builder().setTargetName("VideoCapture");
  }
  
  private Set<Integer> getAvailableCameraLensFacing()
  {
    LinkedHashSet localLinkedHashSet = new LinkedHashSet(Arrays.asList(LensFacingConverter.values()));
    if (this.mCurrentLifecycle != null)
    {
      if (!hasCameraWithLensFacing(1)) {
        localLinkedHashSet.remove(Integer.valueOf(1));
      }
      if (!hasCameraWithLensFacing(0)) {
        localLinkedHashSet.remove(Integer.valueOf(0));
      }
    }
    return localLinkedHashSet;
  }
  
  private int getMeasuredHeight()
  {
    return this.mCameraView.getMeasuredHeight();
  }
  
  private int getMeasuredWidth()
  {
    return this.mCameraView.getMeasuredWidth();
  }
  
  private void rebindToLifecycle()
  {
    LifecycleOwner localLifecycleOwner = this.mCurrentLifecycle;
    if (localLifecycleOwner != null) {
      bindToLifecycle(localLifecycleOwner);
    }
  }
  
  private void updateViewInfo()
  {
    Object localObject = this.mImageCapture;
    if (localObject != null)
    {
      ((ImageCapture)localObject).setCropAspectRatio(new Rational(getWidth(), getHeight()));
      this.mImageCapture.setTargetRotation(getDisplaySurfaceRotation());
    }
    localObject = this.mVideoCapture;
    if (localObject != null) {
      ((VideoCapture)localObject).setTargetRotation(getDisplaySurfaceRotation());
    }
  }
  
  void bindToLifecycle(LifecycleOwner paramLifecycleOwner)
  {
    this.mNewLifecycle = paramLifecycleOwner;
    if ((getMeasuredWidth() > 0) && (getMeasuredHeight() > 0)) {
      bindToLifecycleAfterViewMeasured();
    }
  }
  
  void bindToLifecycleAfterViewMeasured()
  {
    if (this.mNewLifecycle == null) {
      return;
    }
    clearCurrentLifecycle();
    Object localObject1 = this.mNewLifecycle;
    this.mCurrentLifecycle = ((LifecycleOwner)localObject1);
    this.mNewLifecycle = null;
    if (((LifecycleOwner)localObject1).getLifecycle().getCurrentState() != Lifecycle.State.DESTROYED)
    {
      if (this.mCameraProvider == null) {
        return;
      }
      localObject1 = getAvailableCameraLensFacing();
      if (((Set)localObject1).isEmpty())
      {
        Log.w("CameraXModule", "Unable to bindToLifeCycle since no cameras available");
        this.mCameraLensFacing = null;
      }
      Object localObject2 = this.mCameraLensFacing;
      if ((localObject2 != null) && (!((Set)localObject1).contains(localObject2)))
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("Camera does not exist with direction ");
        ((StringBuilder)localObject2).append(this.mCameraLensFacing);
        Log.w("CameraXModule", ((StringBuilder)localObject2).toString());
        this.mCameraLensFacing = ((Integer)((Set)localObject1).iterator().next());
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("Defaulting to primary camera with direction ");
        ((StringBuilder)localObject1).append(this.mCameraLensFacing);
        Log.w("CameraXModule", ((StringBuilder)localObject1).toString());
      }
      if (this.mCameraLensFacing == null) {
        return;
      }
      if ((getDisplayRotationDegrees() != 0) && (getDisplayRotationDegrees() != 180)) {
        i = 0;
      } else {
        i = 1;
      }
      if (getCaptureMode() == CameraView.CaptureMode.IMAGE)
      {
        this.mImageCaptureBuilder.setTargetAspectRatio(0);
        if (i != 0) {
          localObject1 = ASPECT_RATIO_3_4;
        } else {
          localObject1 = ASPECT_RATIO_4_3;
        }
      }
      else
      {
        this.mImageCaptureBuilder.setTargetAspectRatio(1);
        if (i != 0) {
          localObject1 = ASPECT_RATIO_9_16;
        } else {
          localObject1 = ASPECT_RATIO_16_9;
        }
      }
      this.mImageCaptureBuilder.setTargetRotation(getDisplaySurfaceRotation());
      this.mImageCapture = this.mImageCaptureBuilder.build();
      this.mVideoCaptureConfigBuilder.setTargetRotation(getDisplaySurfaceRotation());
      this.mVideoCapture = this.mVideoCaptureConfigBuilder.build();
      int i = (int)(getMeasuredWidth() / ((Rational)localObject1).floatValue());
      this.mPreviewBuilder.setTargetResolution(new Size(getMeasuredWidth(), i));
      localObject1 = this.mPreviewBuilder.build();
      this.mPreview = ((Preview)localObject1);
      ((Preview)localObject1).setSurfaceProvider(this.mCameraView.getPreviewView().getPreviewSurfaceProvider());
      localObject1 = new CameraSelector.Builder().requireLensFacing(this.mCameraLensFacing.intValue()).build();
      if (getCaptureMode() == CameraView.CaptureMode.IMAGE) {
        this.mCamera = this.mCameraProvider.bindToLifecycle(this.mCurrentLifecycle, (CameraSelector)localObject1, new UseCase[] { this.mImageCapture, this.mPreview });
      } else if (getCaptureMode() == CameraView.CaptureMode.VIDEO) {
        this.mCamera = this.mCameraProvider.bindToLifecycle(this.mCurrentLifecycle, (CameraSelector)localObject1, new UseCase[] { this.mVideoCapture, this.mPreview });
      } else {
        this.mCamera = this.mCameraProvider.bindToLifecycle(this.mCurrentLifecycle, (CameraSelector)localObject1, new UseCase[] { this.mImageCapture, this.mVideoCapture, this.mPreview });
      }
      setZoomRatio(1.0F);
      this.mCurrentLifecycle.getLifecycle().addObserver(this.mCurrentLifecycleObserver);
      setFlash(getFlash());
      return;
    }
    this.mCurrentLifecycle = null;
    throw new IllegalArgumentException("Cannot bind to lifecycle in a destroyed state.");
  }
  
  void clearCurrentLifecycle()
  {
    if ((this.mCurrentLifecycle != null) && (this.mCameraProvider != null))
    {
      ArrayList localArrayList = new ArrayList();
      Object localObject = this.mImageCapture;
      if ((localObject != null) && (this.mCameraProvider.isBound((UseCase)localObject))) {
        localArrayList.add(this.mImageCapture);
      }
      localObject = this.mVideoCapture;
      if ((localObject != null) && (this.mCameraProvider.isBound((UseCase)localObject))) {
        localArrayList.add(this.mVideoCapture);
      }
      localObject = this.mPreview;
      if ((localObject != null) && (this.mCameraProvider.isBound((UseCase)localObject))) {
        localArrayList.add(this.mPreview);
      }
      if (!localArrayList.isEmpty()) {
        this.mCameraProvider.unbind((UseCase[])localArrayList.toArray(new UseCase[0]));
      }
    }
    this.mCamera = null;
    this.mCurrentLifecycle = null;
  }
  
  public void close()
  {
    throw new UnsupportedOperationException("Explicit open/close of camera not yet supported. Use bindtoLifecycle() instead.");
  }
  
  public void enableTorch(boolean paramBoolean)
  {
    Camera localCamera = this.mCamera;
    if (localCamera == null) {
      return;
    }
    Futures.addCallback(localCamera.getCameraControl().enableTorch(paramBoolean), new FutureCallback()
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        throw new RuntimeException(paramAnonymousThrowable);
      }
      
      public void onSuccess(Void paramAnonymousVoid) {}
    }, CameraXExecutors.directExecutor());
  }
  
  public Camera getCamera()
  {
    return this.mCamera;
  }
  
  public CameraView.CaptureMode getCaptureMode()
  {
    return this.mCaptureMode;
  }
  
  public Context getContext()
  {
    return this.mCameraView.getContext();
  }
  
  public int getDisplayRotationDegrees()
  {
    return CameraOrientationUtil.surfaceRotationToDegrees(getDisplaySurfaceRotation());
  }
  
  protected int getDisplaySurfaceRotation()
  {
    return this.mCameraView.getDisplaySurfaceRotation();
  }
  
  public int getFlash()
  {
    return this.mFlash;
  }
  
  public int getHeight()
  {
    return this.mCameraView.getHeight();
  }
  
  public Integer getLensFacing()
  {
    return this.mCameraLensFacing;
  }
  
  public long getMaxVideoDuration()
  {
    return this.mMaxVideoDuration;
  }
  
  public long getMaxVideoSize()
  {
    return this.mMaxVideoSize;
  }
  
  public float getMaxZoomRatio()
  {
    Camera localCamera = this.mCamera;
    if (localCamera != null) {
      return ((ZoomState)localCamera.getCameraInfo().getZoomState().getValue()).getMaxZoomRatio();
    }
    return 1.0F;
  }
  
  public float getMinZoomRatio()
  {
    Camera localCamera = this.mCamera;
    if (localCamera != null) {
      return ((ZoomState)localCamera.getCameraInfo().getZoomState().getValue()).getMinZoomRatio();
    }
    return 1.0F;
  }
  
  int getRelativeCameraOrientation(boolean paramBoolean)
  {
    Camera localCamera = this.mCamera;
    int j;
    if (localCamera != null)
    {
      int i = localCamera.getCameraInfo().getSensorRotationDegrees(getDisplaySurfaceRotation());
      j = i;
      if (paramBoolean) {
        j = (360 - i) % 360;
      }
    }
    else
    {
      j = 0;
    }
    return j;
  }
  
  public int getWidth()
  {
    return this.mCameraView.getWidth();
  }
  
  public float getZoomRatio()
  {
    Camera localCamera = this.mCamera;
    if (localCamera != null) {
      return ((ZoomState)localCamera.getCameraInfo().getZoomState().getValue()).getZoomRatio();
    }
    return 1.0F;
  }
  
  public boolean hasCameraWithLensFacing(int paramInt)
  {
    try
    {
      String str = CameraX.getCameraWithLensFacing(paramInt);
      boolean bool;
      if (str != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    catch (Exception localException)
    {
      throw new IllegalStateException("Unable to query lens facing.", localException);
    }
  }
  
  public void invalidateView()
  {
    updateViewInfo();
  }
  
  public boolean isPaused()
  {
    return false;
  }
  
  public boolean isRecording()
  {
    return this.mVideoIsRecording.get();
  }
  
  public boolean isTorchOn()
  {
    Camera localCamera = this.mCamera;
    boolean bool = false;
    if (localCamera == null) {
      return false;
    }
    if (((Integer)localCamera.getCameraInfo().getTorchState().getValue()).intValue() == 1) {
      bool = true;
    }
    return bool;
  }
  
  public boolean isZoomSupported()
  {
    boolean bool;
    if (getMaxZoomRatio() != 1.0F) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void open()
  {
    throw new UnsupportedOperationException("Explicit open/close of camera not yet supported. Use bindtoLifecycle() instead.");
  }
  
  public void setCameraLensFacing(Integer paramInteger)
  {
    if (!Objects.equals(this.mCameraLensFacing, paramInteger))
    {
      this.mCameraLensFacing = paramInteger;
      paramInteger = this.mCurrentLifecycle;
      if (paramInteger != null) {
        bindToLifecycle(paramInteger);
      }
    }
  }
  
  public void setCaptureMode(CameraView.CaptureMode paramCaptureMode)
  {
    this.mCaptureMode = paramCaptureMode;
    rebindToLifecycle();
  }
  
  public void setFlash(int paramInt)
  {
    this.mFlash = paramInt;
    ImageCapture localImageCapture = this.mImageCapture;
    if (localImageCapture == null) {
      return;
    }
    localImageCapture.setFlashMode(paramInt);
  }
  
  public void setMaxVideoDuration(long paramLong)
  {
    this.mMaxVideoDuration = paramLong;
  }
  
  public void setMaxVideoSize(long paramLong)
  {
    this.mMaxVideoSize = paramLong;
  }
  
  public void setZoomRatio(float paramFloat)
  {
    Camera localCamera = this.mCamera;
    if (localCamera != null) {
      Futures.addCallback(localCamera.getCameraControl().setZoomRatio(paramFloat), new FutureCallback()
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          throw new RuntimeException(paramAnonymousThrowable);
        }
        
        public void onSuccess(Void paramAnonymousVoid) {}
      }, CameraXExecutors.directExecutor());
    } else {
      Log.e("CameraXModule", "Failed to set zoom ratio");
    }
  }
  
  public void startRecording(File paramFile, Executor paramExecutor, final VideoCapture.OnVideoSavedCallback paramOnVideoSavedCallback)
  {
    if (this.mVideoCapture == null) {
      return;
    }
    if (getCaptureMode() != CameraView.CaptureMode.IMAGE)
    {
      if (paramOnVideoSavedCallback != null)
      {
        this.mVideoIsRecording.set(true);
        this.mVideoCapture.startRecording(paramFile, paramExecutor, new VideoCapture.OnVideoSavedCallback()
        {
          public void onError(int paramAnonymousInt, String paramAnonymousString, Throwable paramAnonymousThrowable)
          {
            CameraXModule.this.mVideoIsRecording.set(false);
            Log.e("CameraXModule", paramAnonymousString, paramAnonymousThrowable);
            paramOnVideoSavedCallback.onError(paramAnonymousInt, paramAnonymousString, paramAnonymousThrowable);
          }
          
          public void onVideoSaved(File paramAnonymousFile)
          {
            CameraXModule.this.mVideoIsRecording.set(false);
            paramOnVideoSavedCallback.onVideoSaved(paramAnonymousFile);
          }
        });
        return;
      }
      throw new IllegalArgumentException("OnVideoSavedCallback should not be empty");
    }
    throw new IllegalStateException("Can not record video under IMAGE capture mode.");
  }
  
  public void stopRecording()
  {
    VideoCapture localVideoCapture = this.mVideoCapture;
    if (localVideoCapture == null) {
      return;
    }
    localVideoCapture.stopRecording();
  }
  
  public void takePicture(File paramFile, Executor paramExecutor, ImageCapture.OnImageSavedCallback paramOnImageSavedCallback)
  {
    if (this.mImageCapture == null) {
      return;
    }
    if (getCaptureMode() != CameraView.CaptureMode.VIDEO)
    {
      if (paramOnImageSavedCallback != null)
      {
        ImageCapture.Metadata localMetadata = new ImageCapture.Metadata();
        Integer localInteger = this.mCameraLensFacing;
        boolean bool;
        if ((localInteger != null) && (localInteger.intValue() == 0)) {
          bool = true;
        } else {
          bool = false;
        }
        localMetadata.setReversedHorizontal(bool);
        paramFile = new ImageCapture.OutputFileOptions.Builder(paramFile).setMetadata(localMetadata).build();
        this.mImageCapture.takePicture(paramFile, paramExecutor, paramOnImageSavedCallback);
        return;
      }
      throw new IllegalArgumentException("OnImageSavedCallback should not be empty");
    }
    throw new IllegalStateException("Can not take picture under VIDEO capture mode.");
  }
  
  public void takePicture(Executor paramExecutor, ImageCapture.OnImageCapturedCallback paramOnImageCapturedCallback)
  {
    if (this.mImageCapture == null) {
      return;
    }
    if (getCaptureMode() != CameraView.CaptureMode.VIDEO)
    {
      if (paramOnImageCapturedCallback != null)
      {
        this.mImageCapture.takePicture(paramExecutor, paramOnImageCapturedCallback);
        return;
      }
      throw new IllegalArgumentException("OnImageCapturedCallback should not be empty");
    }
    throw new IllegalStateException("Can not take picture under VIDEO capture mode.");
  }
  
  public void toggleCamera()
  {
    Set localSet = getAvailableCameraLensFacing();
    if (localSet.isEmpty()) {
      return;
    }
    Integer localInteger = this.mCameraLensFacing;
    if (localInteger == null)
    {
      setCameraLensFacing((Integer)localSet.iterator().next());
      return;
    }
    if ((localInteger.intValue() == 1) && (localSet.contains(Integer.valueOf(0))))
    {
      setCameraLensFacing(Integer.valueOf(0));
      return;
    }
    if ((this.mCameraLensFacing.intValue() == 0) && (localSet.contains(Integer.valueOf(1)))) {
      setCameraLensFacing(Integer.valueOf(1));
    }
  }
}

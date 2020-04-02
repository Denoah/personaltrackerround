package androidx.camera.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManager.DisplayListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraSelector.Builder;
import androidx.camera.core.DisplayOrientedMeteringPointFactory;
import androidx.camera.core.FocusMeteringAction.Builder;
import androidx.camera.core.FocusMeteringResult;
import androidx.camera.core.ImageCapture.OnImageCapturedCallback;
import androidx.camera.core.ImageCapture.OnImageSavedCallback;
import androidx.camera.core.MeteringPoint;
import androidx.camera.core.VideoCapture.OnVideoSavedCallback;
import androidx.camera.core.impl.LensFacingConverter;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.lifecycle.LifecycleOwner;
import java.io.File;
import java.util.concurrent.Executor;

public final class CameraView
  extends FrameLayout
{
  static final boolean DEBUG = false;
  private static final String EXTRA_CAMERA_DIRECTION = "camera_direction";
  private static final String EXTRA_CAPTURE_MODE = "captureMode";
  private static final String EXTRA_FLASH = "flash";
  private static final String EXTRA_MAX_VIDEO_DURATION = "max_video_duration";
  private static final String EXTRA_MAX_VIDEO_SIZE = "max_video_size";
  private static final String EXTRA_PINCH_TO_ZOOM_ENABLED = "pinch_to_zoom_enabled";
  private static final String EXTRA_SCALE_TYPE = "scale_type";
  private static final String EXTRA_SUPER = "super";
  private static final String EXTRA_ZOOM_RATIO = "zoom_ratio";
  private static final int FLASH_MODE_AUTO = 1;
  private static final int FLASH_MODE_OFF = 4;
  private static final int FLASH_MODE_ON = 2;
  static final int INDEFINITE_VIDEO_DURATION = -1;
  static final int INDEFINITE_VIDEO_SIZE = -1;
  private static final int LENS_FACING_BACK = 2;
  private static final int LENS_FACING_FRONT = 1;
  private static final int LENS_FACING_NONE = 0;
  static final String TAG = CameraView.class.getSimpleName();
  CameraXModule mCameraModule;
  private final DisplayManager.DisplayListener mDisplayListener = new DisplayManager.DisplayListener()
  {
    public void onDisplayAdded(int paramAnonymousInt) {}
    
    public void onDisplayChanged(int paramAnonymousInt)
    {
      CameraView.this.mCameraModule.invalidateView();
    }
    
    public void onDisplayRemoved(int paramAnonymousInt) {}
  };
  private long mDownEventTimestamp;
  private boolean mIsPinchToZoomEnabled = true;
  private PinchToZoomGestureDetector mPinchToZoomGestureDetector;
  private PreviewView mPreviewView;
  private ScaleType mScaleType = ScaleType.CENTER_CROP;
  private MotionEvent mUpEvent;
  
  public CameraView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public CameraView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public CameraView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }
  
  public CameraView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    init(paramContext, paramAttributeSet);
  }
  
  private long delta()
  {
    return System.currentTimeMillis() - this.mDownEventTimestamp;
  }
  
  private long getMaxVideoSize()
  {
    return this.mCameraModule.getMaxVideoSize();
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet)
  {
    PreviewView localPreviewView = new PreviewView(getContext());
    this.mPreviewView = localPreviewView;
    addView(localPreviewView, 0);
    this.mCameraModule = new CameraXModule(this);
    if (paramAttributeSet != null)
    {
      paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.CameraView);
      setScaleType(ScaleType.fromId(paramAttributeSet.getInteger(R.styleable.CameraView_scaleType, getScaleType().getId())));
      setPinchToZoomEnabled(paramAttributeSet.getBoolean(R.styleable.CameraView_pinchToZoomEnabled, isPinchToZoomEnabled()));
      setCaptureMode(CaptureMode.fromId(paramAttributeSet.getInteger(R.styleable.CameraView_captureMode, getCaptureMode().getId())));
      int i = paramAttributeSet.getInt(R.styleable.CameraView_lensFacing, 2);
      if (i != 0)
      {
        if (i != 1)
        {
          if (i == 2) {
            setCameraLensFacing(Integer.valueOf(1));
          }
        }
        else {
          setCameraLensFacing(Integer.valueOf(0));
        }
      }
      else {
        setCameraLensFacing(null);
      }
      i = paramAttributeSet.getInt(R.styleable.CameraView_flash, 0);
      if (i != 1)
      {
        if (i != 2)
        {
          if (i == 4) {
            setFlash(2);
          }
        }
        else {
          setFlash(1);
        }
      }
      else {
        setFlash(0);
      }
      paramAttributeSet.recycle();
    }
    if (getBackground() == null) {
      setBackgroundColor(-15658735);
    }
    this.mPinchToZoomGestureDetector = new PinchToZoomGestureDetector(paramContext);
  }
  
  private void setMaxVideoDuration(long paramLong)
  {
    this.mCameraModule.setMaxVideoDuration(paramLong);
  }
  
  private void setMaxVideoSize(long paramLong)
  {
    this.mCameraModule.setMaxVideoSize(paramLong);
  }
  
  public void bindToLifecycle(LifecycleOwner paramLifecycleOwner)
  {
    this.mCameraModule.bindToLifecycle(paramLifecycleOwner);
  }
  
  public void enableTorch(boolean paramBoolean)
  {
    this.mCameraModule.enableTorch(paramBoolean);
  }
  
  protected FrameLayout.LayoutParams generateDefaultLayoutParams()
  {
    return new FrameLayout.LayoutParams(-1, -1);
  }
  
  public Integer getCameraLensFacing()
  {
    return this.mCameraModule.getLensFacing();
  }
  
  public CaptureMode getCaptureMode()
  {
    return this.mCameraModule.getCaptureMode();
  }
  
  int getDisplaySurfaceRotation()
  {
    Display localDisplay = getDisplay();
    if (localDisplay == null) {
      return 0;
    }
    return localDisplay.getRotation();
  }
  
  public int getFlash()
  {
    return this.mCameraModule.getFlash();
  }
  
  public long getMaxVideoDuration()
  {
    return this.mCameraModule.getMaxVideoDuration();
  }
  
  public float getMaxZoomRatio()
  {
    return this.mCameraModule.getMaxZoomRatio();
  }
  
  public float getMinZoomRatio()
  {
    return this.mCameraModule.getMinZoomRatio();
  }
  
  PreviewView getPreviewView()
  {
    return this.mPreviewView;
  }
  
  public ScaleType getScaleType()
  {
    return this.mScaleType;
  }
  
  public float getZoomRatio()
  {
    return this.mCameraModule.getZoomRatio();
  }
  
  public boolean hasCameraWithLensFacing(int paramInt)
  {
    return this.mCameraModule.hasCameraWithLensFacing(paramInt);
  }
  
  public boolean isPinchToZoomEnabled()
  {
    return this.mIsPinchToZoomEnabled;
  }
  
  public boolean isRecording()
  {
    return this.mCameraModule.isRecording();
  }
  
  public boolean isTorchOn()
  {
    return this.mCameraModule.isTorchOn();
  }
  
  public boolean isZoomSupported()
  {
    return this.mCameraModule.isZoomSupported();
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    ((DisplayManager)getContext().getSystemService("display")).registerDisplayListener(this.mDisplayListener, new Handler(Looper.getMainLooper()));
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    ((DisplayManager)getContext().getSystemService("display")).unregisterDisplayListener(this.mDisplayListener);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mCameraModule.bindToLifecycleAfterViewMeasured();
    this.mCameraModule.invalidateView();
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if ((getMeasuredWidth() > 0) && (getMeasuredHeight() > 0)) {
      this.mCameraModule.bindToLifecycleAfterViewMeasured();
    }
    super.onMeasure(paramInt1, paramInt2);
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable instanceof Bundle))
    {
      Bundle localBundle = (Bundle)paramParcelable;
      super.onRestoreInstanceState(localBundle.getParcelable("super"));
      setScaleType(ScaleType.fromId(localBundle.getInt("scale_type")));
      setZoomRatio(localBundle.getFloat("zoom_ratio"));
      setPinchToZoomEnabled(localBundle.getBoolean("pinch_to_zoom_enabled"));
      setFlash(FlashModeConverter.valueOf(localBundle.getString("flash")));
      setMaxVideoDuration(localBundle.getLong("max_video_duration"));
      setMaxVideoSize(localBundle.getLong("max_video_size"));
      paramParcelable = localBundle.getString("camera_direction");
      if (TextUtils.isEmpty(paramParcelable)) {
        paramParcelable = null;
      } else {
        paramParcelable = Integer.valueOf(LensFacingConverter.valueOf(paramParcelable));
      }
      setCameraLensFacing(paramParcelable);
      setCaptureMode(CaptureMode.fromId(localBundle.getInt("captureMode")));
    }
    else
    {
      super.onRestoreInstanceState(paramParcelable);
    }
  }
  
  protected Parcelable onSaveInstanceState()
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("super", super.onSaveInstanceState());
    localBundle.putInt("scale_type", getScaleType().getId());
    localBundle.putFloat("zoom_ratio", getZoomRatio());
    localBundle.putBoolean("pinch_to_zoom_enabled", isPinchToZoomEnabled());
    localBundle.putString("flash", FlashModeConverter.nameOf(getFlash()));
    localBundle.putLong("max_video_duration", getMaxVideoDuration());
    localBundle.putLong("max_video_size", getMaxVideoSize());
    if (getCameraLensFacing() != null) {
      localBundle.putString("camera_direction", LensFacingConverter.nameOf(getCameraLensFacing().intValue()));
    }
    localBundle.putInt("captureMode", getCaptureMode().getId());
    return localBundle;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.mCameraModule.isPaused()) {
      return false;
    }
    if (isPinchToZoomEnabled()) {
      this.mPinchToZoomGestureDetector.onTouchEvent(paramMotionEvent);
    }
    if ((paramMotionEvent.getPointerCount() == 2) && (isPinchToZoomEnabled()) && (isZoomSupported())) {
      return true;
    }
    int i = paramMotionEvent.getAction();
    if (i != 0)
    {
      if (i != 1) {
        return false;
      }
      if (delta() < ViewConfiguration.getLongPressTimeout())
      {
        this.mUpEvent = paramMotionEvent;
        performClick();
      }
    }
    else
    {
      this.mDownEventTimestamp = System.currentTimeMillis();
    }
    return true;
  }
  
  public boolean performClick()
  {
    super.performClick();
    Object localObject1 = this.mUpEvent;
    float f1;
    if (localObject1 != null) {
      f1 = ((MotionEvent)localObject1).getX();
    } else {
      f1 = getX() + getWidth() / 2.0F;
    }
    localObject1 = this.mUpEvent;
    float f2;
    if (localObject1 != null) {
      f2 = ((MotionEvent)localObject1).getY();
    } else {
      f2 = getY() + getHeight() / 2.0F;
    }
    this.mUpEvent = null;
    localObject1 = new CameraSelector.Builder().requireLensFacing(this.mCameraModule.getLensFacing().intValue()).build();
    Object localObject2 = new DisplayOrientedMeteringPointFactory(getDisplay(), (CameraSelector)localObject1, this.mPreviewView.getWidth(), this.mPreviewView.getHeight());
    localObject1 = ((DisplayOrientedMeteringPointFactory)localObject2).createPoint(f1, f2, 0.16666667F);
    localObject2 = ((DisplayOrientedMeteringPointFactory)localObject2).createPoint(f1, f2, 0.25F);
    Camera localCamera = this.mCameraModule.getCamera();
    if (localCamera != null) {
      Futures.addCallback(localCamera.getCameraControl().startFocusAndMetering(new FocusMeteringAction.Builder((MeteringPoint)localObject1, 1).addPoint((MeteringPoint)localObject2, 2).build()), new FutureCallback()
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          throw new RuntimeException(paramAnonymousThrowable);
        }
        
        public void onSuccess(FocusMeteringResult paramAnonymousFocusMeteringResult) {}
      }, CameraXExecutors.directExecutor());
    } else {
      Log.d(TAG, "cannot access camera");
    }
    return true;
  }
  
  float rangeLimit(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return Math.min(Math.max(paramFloat1, paramFloat3), paramFloat2);
  }
  
  public void setCameraLensFacing(Integer paramInteger)
  {
    this.mCameraModule.setCameraLensFacing(paramInteger);
  }
  
  public void setCaptureMode(CaptureMode paramCaptureMode)
  {
    this.mCameraModule.setCaptureMode(paramCaptureMode);
  }
  
  public void setFlash(int paramInt)
  {
    this.mCameraModule.setFlash(paramInt);
  }
  
  public void setPinchToZoomEnabled(boolean paramBoolean)
  {
    this.mIsPinchToZoomEnabled = paramBoolean;
  }
  
  public void setScaleType(ScaleType paramScaleType)
  {
    if (paramScaleType != this.mScaleType)
    {
      this.mScaleType = paramScaleType;
      requestLayout();
    }
  }
  
  public void setZoomRatio(float paramFloat)
  {
    this.mCameraModule.setZoomRatio(paramFloat);
  }
  
  public void startRecording(File paramFile, Executor paramExecutor, VideoCapture.OnVideoSavedCallback paramOnVideoSavedCallback)
  {
    this.mCameraModule.startRecording(paramFile, paramExecutor, paramOnVideoSavedCallback);
  }
  
  public void stopRecording()
  {
    this.mCameraModule.stopRecording();
  }
  
  public void takePicture(File paramFile, Executor paramExecutor, ImageCapture.OnImageSavedCallback paramOnImageSavedCallback)
  {
    this.mCameraModule.takePicture(paramFile, paramExecutor, paramOnImageSavedCallback);
  }
  
  public void takePicture(Executor paramExecutor, ImageCapture.OnImageCapturedCallback paramOnImageCapturedCallback)
  {
    this.mCameraModule.takePicture(paramExecutor, paramOnImageCapturedCallback);
  }
  
  public void toggleCamera()
  {
    this.mCameraModule.toggleCamera();
  }
  
  public static enum CaptureMode
  {
    private final int mId;
    
    static
    {
      CaptureMode localCaptureMode = new CaptureMode("MIXED", 2, 2);
      MIXED = localCaptureMode;
      $VALUES = new CaptureMode[] { IMAGE, VIDEO, localCaptureMode };
    }
    
    private CaptureMode(int paramInt)
    {
      this.mId = paramInt;
    }
    
    static CaptureMode fromId(int paramInt)
    {
      for (CaptureMode localCaptureMode : ) {
        if (localCaptureMode.mId == paramInt) {
          return localCaptureMode;
        }
      }
      throw new IllegalArgumentException();
    }
    
    int getId()
    {
      return this.mId;
    }
  }
  
  private class PinchToZoomGestureDetector
    extends ScaleGestureDetector
    implements ScaleGestureDetector.OnScaleGestureListener
  {
    PinchToZoomGestureDetector(Context paramContext)
    {
      this(paramContext, new CameraView.S());
    }
    
    PinchToZoomGestureDetector(Context paramContext, CameraView.S paramS)
    {
      super(paramS);
      paramS.setRealGestureDetector(this);
    }
    
    public boolean onScale(ScaleGestureDetector paramScaleGestureDetector)
    {
      float f1 = paramScaleGestureDetector.getScaleFactor();
      if (f1 > 1.0F) {
        f1 = (f1 - 1.0F) * 2.0F + 1.0F;
      } else {
        f1 = 1.0F - (1.0F - f1) * 2.0F;
      }
      float f2 = CameraView.this.getZoomRatio();
      paramScaleGestureDetector = CameraView.this;
      f1 = paramScaleGestureDetector.rangeLimit(f2 * f1, paramScaleGestureDetector.getMaxZoomRatio(), CameraView.this.getMinZoomRatio());
      CameraView.this.setZoomRatio(f1);
      return true;
    }
    
    public boolean onScaleBegin(ScaleGestureDetector paramScaleGestureDetector)
    {
      return true;
    }
    
    public void onScaleEnd(ScaleGestureDetector paramScaleGestureDetector) {}
  }
  
  static class S
    extends ScaleGestureDetector.SimpleOnScaleGestureListener
  {
    private ScaleGestureDetector.OnScaleGestureListener mListener;
    
    S() {}
    
    public boolean onScale(ScaleGestureDetector paramScaleGestureDetector)
    {
      return this.mListener.onScale(paramScaleGestureDetector);
    }
    
    void setRealGestureDetector(ScaleGestureDetector.OnScaleGestureListener paramOnScaleGestureListener)
    {
      this.mListener = paramOnScaleGestureListener;
    }
  }
  
  public static enum ScaleType
  {
    private final int mId;
    
    static
    {
      ScaleType localScaleType = new ScaleType("CENTER_INSIDE", 1, 1);
      CENTER_INSIDE = localScaleType;
      $VALUES = new ScaleType[] { CENTER_CROP, localScaleType };
    }
    
    private ScaleType(int paramInt)
    {
      this.mId = paramInt;
    }
    
    static ScaleType fromId(int paramInt)
    {
      for (ScaleType localScaleType : ) {
        if (localScaleType.mId == paramInt) {
          return localScaleType;
        }
      }
      throw new IllegalArgumentException();
    }
    
    int getId()
    {
      return this.mId;
    }
  }
}

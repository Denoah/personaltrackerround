package androidx.camera.core;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import android.util.Rational;
import android.util.Size;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureFailure;
import androidx.camera.core.impl.CameraCaptureMetaData.AeState;
import androidx.camera.core.impl.CameraCaptureMetaData.AfMode;
import androidx.camera.core.impl.CameraCaptureMetaData.AfState;
import androidx.camera.core.impl.CameraCaptureMetaData.AwbState;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.CameraCaptureResult.EmptyCameraCaptureResult;
import androidx.camera.core.impl.CameraControlInternal;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.CaptureBundle;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.CaptureConfig.Builder;
import androidx.camera.core.impl.CaptureConfig.OptionUnpacker;
import androidx.camera.core.impl.CaptureProcessor;
import androidx.camera.core.impl.CaptureStage;
import androidx.camera.core.impl.ConfigProvider;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImageCaptureConfig;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.ImageOutputConfig.Builder;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.ImageReaderProxy.OnImageAvailableListener;
import androidx.camera.core.impl.ImmediateSurface;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.OptionsBundle;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.SessionConfig.Builder;
import androidx.camera.core.impl.SessionConfig.OptionUnpacker;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.UseCaseConfig.Builder;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.FutureChain;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.core.internal.IoConfig.Builder;
import androidx.camera.core.internal.TargetConfig;
import androidx.camera.core.internal.utils.UseCaseConfigUtil;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.CallbackToFutureAdapter.Completer;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.File;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class ImageCapture
  extends UseCase
{
  public static final int CAPTURE_MODE_MAXIMIZE_QUALITY = 0;
  public static final int CAPTURE_MODE_MINIMIZE_LATENCY = 1;
  private static final long CHECK_3A_TIMEOUT_IN_MS = 1000L;
  public static final Defaults DEFAULT_CONFIG = new Defaults();
  public static final int ERROR_CAMERA_CLOSED = 3;
  public static final int ERROR_CAPTURE_FAILED = 2;
  public static final int ERROR_FILE_IO = 1;
  public static final int ERROR_INVALID_CAMERA = 4;
  public static final int ERROR_UNKNOWN = 0;
  public static final int FLASH_MODE_AUTO = 0;
  public static final int FLASH_MODE_OFF = 2;
  public static final int FLASH_MODE_ON = 1;
  private static final int MAX_IMAGES = 2;
  private static final String TAG = "ImageCapture";
  private final CaptureBundle mCaptureBundle;
  private final CaptureConfig mCaptureConfig;
  private final int mCaptureMode;
  private final CaptureProcessor mCaptureProcessor;
  private final ImageReaderProxy.OnImageAvailableListener mClosingListener = _..Lambda.ImageCapture._NPEX6f_pK2w8zMZHH6SI_c_xrU.INSTANCE;
  private ImageCaptureConfig mConfig;
  private DeferrableSurface mDeferrableSurface;
  private boolean mEnableCheck3AConverged;
  private final ExecutorService mExecutor = Executors.newFixedThreadPool(1, new ThreadFactory()
  {
    private final AtomicInteger mId = new AtomicInteger(0);
    
    public Thread newThread(Runnable paramAnonymousRunnable)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("CameraX-image_capture_");
      localStringBuilder.append(this.mId.getAndIncrement());
      return new Thread(paramAnonymousRunnable, localStringBuilder.toString());
    }
  });
  private int mFlashMode;
  ImageReaderProxy mImageReader;
  final Executor mIoExecutor;
  private final int mMaxCaptureStages;
  private CameraCaptureCallback mMetadataMatchingCaptureCallback;
  final ForwardingImageProxy.OnImageCloseListener mOnImageCloseListener = new ForwardingImageProxy.OnImageCloseListener()
  {
    public void onImageClose(ImageProxy paramAnonymousImageProxy)
    {
      if (Looper.getMainLooper() != Looper.myLooper())
      {
        CameraXExecutors.mainThreadExecutor().execute(new _..Lambda.ImageCapture.5.D4kGilBzCG3_Q3cVCqYA9kFIzJU(this, paramAnonymousImageProxy));
        return;
      }
      ImageCapture.this.issueImageCaptureRequests();
    }
  };
  final Deque<ImageCaptureRequest> mPendingImageCaptureRequests = new ConcurrentLinkedDeque();
  private final CaptureCallbackChecker mSessionCallbackChecker = new CaptureCallbackChecker();
  SessionConfig.Builder mSessionConfigBuilder;
  final TakePictureLock mTakePictureLock = new TakePictureLock(2, this);
  
  ImageCapture(ImageCaptureConfig paramImageCaptureConfig)
  {
    super(paramImageCaptureConfig);
    paramImageCaptureConfig = (ImageCaptureConfig)getUseCaseConfig();
    this.mConfig = paramImageCaptureConfig;
    this.mCaptureMode = paramImageCaptureConfig.getCaptureMode();
    this.mFlashMode = this.mConfig.getFlashMode();
    this.mCaptureProcessor = this.mConfig.getCaptureProcessor(null);
    int i = this.mConfig.getMaxCaptureStages(2);
    this.mMaxCaptureStages = i;
    boolean bool;
    if (i >= 1) {
      bool = true;
    } else {
      bool = false;
    }
    Preconditions.checkArgument(bool, "Maximum outstanding image count must be at least 1");
    paramImageCaptureConfig = this.mConfig.getBufferFormat(null);
    if (paramImageCaptureConfig != null)
    {
      if (this.mCaptureProcessor == null) {
        bool = true;
      } else {
        bool = false;
      }
      Preconditions.checkArgument(bool, "Cannot set buffer format with CaptureProcessor defined.");
      setImageFormat(paramImageCaptureConfig.intValue());
    }
    else if (this.mCaptureProcessor != null)
    {
      setImageFormat(35);
    }
    else
    {
      setImageFormat(ImageReaderFormatRecommender.chooseCombo().imageCaptureFormat());
    }
    this.mCaptureBundle = this.mConfig.getCaptureBundle(CaptureBundles.singleDefaultCaptureBundle());
    this.mIoExecutor = ((Executor)Preconditions.checkNotNull(this.mConfig.getIoExecutor(CameraXExecutors.ioExecutor())));
    i = this.mCaptureMode;
    if (i == 0) {
      this.mEnableCheck3AConverged = true;
    } else if (i == 1) {
      this.mEnableCheck3AConverged = false;
    }
    this.mCaptureConfig = CaptureConfig.Builder.createFrom(this.mConfig).build();
  }
  
  private void abortImageCaptureRequests()
  {
    CameraClosedException localCameraClosedException = new CameraClosedException("Camera is closed.");
    Iterator localIterator = this.mPendingImageCaptureRequests.iterator();
    while (localIterator.hasNext()) {
      ((ImageCaptureRequest)localIterator.next()).notifyCallbackError(getError(localCameraClosedException), localCameraClosedException.getMessage(), localCameraClosedException);
    }
    this.mPendingImageCaptureRequests.clear();
    this.mTakePictureLock.cancelTakePicture(localCameraClosedException);
  }
  
  private CaptureBundle getCaptureBundle(CaptureBundle paramCaptureBundle)
  {
    List localList = this.mCaptureBundle.getCaptureStages();
    CaptureBundle localCaptureBundle = paramCaptureBundle;
    if (localList != null) {
      if (localList.isEmpty()) {
        localCaptureBundle = paramCaptureBundle;
      } else {
        localCaptureBundle = CaptureBundles.createCaptureBundle(localList);
      }
    }
    return localCaptureBundle;
  }
  
  private CameraControlInternal getCurrentCameraControl()
  {
    return getCameraControl(getBoundCameraId());
  }
  
  static int getError(Throwable paramThrowable)
  {
    if ((paramThrowable instanceof CameraClosedException)) {
      return 3;
    }
    if ((paramThrowable instanceof CaptureFailedException)) {
      return 2;
    }
    return 0;
  }
  
  private ListenableFuture<CameraCaptureResult> getPreCaptureStateIfNeeded()
  {
    if ((!this.mEnableCheck3AConverged) && (getFlashMode() != 0)) {
      return Futures.immediateFuture(null);
    }
    this.mSessionCallbackChecker.checkCaptureResult(new ImageCapture.CaptureCallbackChecker.CaptureResultChecker()
    {
      public CameraCaptureResult check(CameraCaptureResult paramAnonymousCameraCaptureResult)
      {
        return paramAnonymousCameraCaptureResult;
      }
    });
  }
  
  private ListenableFuture<Void> preTakePicture(TakePictureState paramTakePictureState)
  {
    return FutureChain.from(getPreCaptureStateIfNeeded()).transformAsync(new _..Lambda.ImageCapture.titIgcLzCEMQ_nAX52EZpzdp7WA(this, paramTakePictureState), this.mExecutor).transform(_..Lambda.ImageCapture.C_GFpnhq4iWGnsxqH98jMkWKOWg.INSTANCE, this.mExecutor);
  }
  
  private void sendImageCaptureRequest(Executor paramExecutor, OnImageCapturedCallback paramOnImageCapturedCallback)
  {
    Object localObject = getBoundCamera();
    if (localObject == null)
    {
      paramExecutor = new StringBuilder();
      paramExecutor.append("Not bound to a valid Camera [");
      paramExecutor.append(this);
      paramExecutor.append("]");
      paramOnImageCapturedCallback.onError(new ImageCaptureException(4, paramExecutor.toString(), null));
      return;
    }
    int i = ((CameraInternal)localObject).getCameraInfoInternal().getSensorRotationDegrees(this.mConfig.getTargetRotation(0));
    localObject = ImageUtil.rotate(this.mConfig.getTargetAspectRatioCustom(null), i);
    this.mPendingImageCaptureRequests.offer(new ImageCaptureRequest(i, (Rational)localObject, paramExecutor, paramOnImageCapturedCallback));
    issueImageCaptureRequests();
  }
  
  private boolean takePictureInternal(final ImageCaptureRequest paramImageCaptureRequest)
  {
    if (!this.mTakePictureLock.lockTakePicture(paramImageCaptureRequest)) {
      return false;
    }
    this.mImageReader.setOnImageAvailableListener(new _..Lambda.ImageCapture.Se2_g8H9Ol9SWt0Z5CI8LH4ED7w(this, paramImageCaptureRequest), CameraXExecutors.mainThreadExecutor());
    final TakePictureState localTakePictureState = new TakePictureState();
    FutureChain.from(preTakePicture(localTakePictureState)).transformAsync(new _..Lambda.ImageCapture.FwmdWImX9Ye3d0zGMvHct0lO2Xk(this, paramImageCaptureRequest), this.mExecutor).addCallback(new FutureCallback()
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        Log.e("ImageCapture", "takePictureInternal onFailure", paramAnonymousThrowable);
        ImageCapture.this.postTakePicture(localTakePictureState);
        CameraXExecutors.mainThreadExecutor().execute(new _..Lambda.ImageCapture.4.MTDpzzYIsR_R_GpGEj2QXeu1160(this, paramImageCaptureRequest, paramAnonymousThrowable));
      }
      
      public void onSuccess(Void paramAnonymousVoid)
      {
        ImageCapture.this.postTakePicture(localTakePictureState);
      }
    }, this.mExecutor);
    return true;
  }
  
  private void triggerAf(TakePictureState paramTakePictureState)
  {
    paramTakePictureState.mIsAfTriggered = true;
    getCurrentCameraControl().triggerAf();
  }
  
  void cancelAfAeTrigger(TakePictureState paramTakePictureState)
  {
    if ((!paramTakePictureState.mIsAfTriggered) && (!paramTakePictureState.mIsAePrecaptureTriggered)) {
      return;
    }
    getCurrentCameraControl().cancelAfAeTrigger(paramTakePictureState.mIsAfTriggered, paramTakePictureState.mIsAePrecaptureTriggered);
    paramTakePictureState.mIsAfTriggered = false;
    paramTakePictureState.mIsAePrecaptureTriggered = false;
  }
  
  ListenableFuture<Boolean> check3AConverged(TakePictureState paramTakePictureState)
  {
    boolean bool = this.mEnableCheck3AConverged;
    Boolean localBoolean = Boolean.valueOf(false);
    if ((!bool) && (!paramTakePictureState.mIsFlashTriggered)) {
      return Futures.immediateFuture(localBoolean);
    }
    if (is3AConverged(paramTakePictureState.mPreCaptureState)) {
      return Futures.immediateFuture(Boolean.valueOf(true));
    }
    this.mSessionCallbackChecker.checkCaptureResult(new ImageCapture.CaptureCallbackChecker.CaptureResultChecker()
    {
      public Boolean check(CameraCaptureResult paramAnonymousCameraCaptureResult)
      {
        if (ImageCapture.this.is3AConverged(paramAnonymousCameraCaptureResult)) {
          return Boolean.valueOf(true);
        }
        return null;
      }
    }, 1000L, localBoolean);
  }
  
  public void clear()
  {
    clearPipeline();
    this.mExecutor.shutdown();
    super.clear();
  }
  
  void clearPipeline()
  {
    Threads.checkMainThread();
    DeferrableSurface localDeferrableSurface = this.mDeferrableSurface;
    this.mDeferrableSurface = null;
    this.mImageReader = null;
    if (localDeferrableSurface != null) {
      localDeferrableSurface.close();
    }
  }
  
  SessionConfig.Builder createPipeline(String paramString, ImageCaptureConfig paramImageCaptureConfig, Size paramSize)
  {
    Threads.checkMainThread();
    SessionConfig.Builder localBuilder = SessionConfig.Builder.createFrom(paramImageCaptureConfig);
    localBuilder.addRepeatingCameraCaptureCallback(this.mSessionCallbackChecker);
    HandlerThread localHandlerThread = new HandlerThread("OnImageAvailableHandlerThread");
    localHandlerThread.start();
    Object localObject1 = new Handler(localHandlerThread.getLooper());
    if (this.mCaptureProcessor != null)
    {
      localObject1 = new ProcessingImageReader(paramSize.getWidth(), paramSize.getHeight(), getImageFormat(), this.mMaxCaptureStages, (Handler)localObject1, getCaptureBundle(CaptureBundles.singleDefaultCaptureBundle()), this.mCaptureProcessor);
      this.mMetadataMatchingCaptureCallback = ((ProcessingImageReader)localObject1).getCameraCaptureCallback();
      this.mImageReader = ((ImageReaderProxy)localObject1);
    }
    else
    {
      localObject1 = new MetadataImageReader(paramSize.getWidth(), paramSize.getHeight(), getImageFormat(), 2, (Handler)localObject1);
      this.mMetadataMatchingCaptureCallback = ((MetadataImageReader)localObject1).getCameraCaptureCallback();
      this.mImageReader = ((ImageReaderProxy)localObject1);
    }
    this.mImageReader.setOnImageAvailableListener(this.mClosingListener, CameraXExecutors.mainThreadExecutor());
    localObject1 = this.mImageReader;
    Object localObject2 = this.mDeferrableSurface;
    if (localObject2 != null) {
      ((DeferrableSurface)localObject2).close();
    }
    localObject2 = new ImmediateSurface(this.mImageReader.getSurface());
    this.mDeferrableSurface = ((DeferrableSurface)localObject2);
    ((DeferrableSurface)localObject2).getTerminationFuture().addListener(new _..Lambda.ImageCapture.F_1wvTR60w78NQyIONsr09a1OLQ((ImageReaderProxy)localObject1, localHandlerThread), CameraXExecutors.mainThreadExecutor());
    localBuilder.addNonRepeatingSurface(this.mDeferrableSurface);
    localBuilder.addErrorListener(new _..Lambda.ImageCapture.vo2FN_NBh2zJowDghO6l4OnBl_M(this, paramString, paramImageCaptureConfig, paramSize));
    return localBuilder;
  }
  
  public int getCaptureMode()
  {
    return this.mCaptureMode;
  }
  
  protected UseCaseConfig.Builder<?, ?, ?> getDefaultBuilder(CameraInfo paramCameraInfo)
  {
    paramCameraInfo = (ImageCaptureConfig)CameraX.getDefaultUseCaseConfig(ImageCaptureConfig.class, paramCameraInfo);
    if (paramCameraInfo != null) {
      return Builder.fromConfig(paramCameraInfo);
    }
    return null;
  }
  
  public int getFlashMode()
  {
    return this.mFlashMode;
  }
  
  public int getTargetRotation()
  {
    return ((ImageOutputConfig)getUseCaseConfig()).getTargetRotation();
  }
  
  boolean is3AConverged(CameraCaptureResult paramCameraCaptureResult)
  {
    boolean bool1 = false;
    if (paramCameraCaptureResult == null) {
      return false;
    }
    int i;
    if ((paramCameraCaptureResult.getAfMode() != CameraCaptureMetaData.AfMode.ON_CONTINUOUS_AUTO) && (paramCameraCaptureResult.getAfMode() != CameraCaptureMetaData.AfMode.OFF) && (paramCameraCaptureResult.getAfMode() != CameraCaptureMetaData.AfMode.UNKNOWN) && (paramCameraCaptureResult.getAfState() != CameraCaptureMetaData.AfState.FOCUSED) && (paramCameraCaptureResult.getAfState() != CameraCaptureMetaData.AfState.LOCKED_FOCUSED) && (paramCameraCaptureResult.getAfState() != CameraCaptureMetaData.AfState.LOCKED_NOT_FOCUSED)) {
      i = 0;
    } else {
      i = 1;
    }
    int j;
    if ((paramCameraCaptureResult.getAeState() != CameraCaptureMetaData.AeState.CONVERGED) && (paramCameraCaptureResult.getAeState() != CameraCaptureMetaData.AeState.UNKNOWN)) {
      j = 0;
    } else {
      j = 1;
    }
    int k;
    if ((paramCameraCaptureResult.getAwbState() != CameraCaptureMetaData.AwbState.CONVERGED) && (paramCameraCaptureResult.getAwbState() != CameraCaptureMetaData.AwbState.UNKNOWN)) {
      k = 0;
    } else {
      k = 1;
    }
    boolean bool2 = bool1;
    if (i != 0)
    {
      bool2 = bool1;
      if (j != 0)
      {
        bool2 = bool1;
        if (k != 0) {
          bool2 = true;
        }
      }
    }
    return bool2;
  }
  
  boolean isFlashRequired(TakePictureState paramTakePictureState)
  {
    int i = getFlashMode();
    boolean bool = false;
    if (i != 0)
    {
      if (i != 1)
      {
        if (i == 2) {
          return false;
        }
        throw new AssertionError(getFlashMode());
      }
      return true;
    }
    if (paramTakePictureState.mPreCaptureState.getAeState() == CameraCaptureMetaData.AeState.FLASH_REQUIRED) {
      bool = true;
    }
    return bool;
  }
  
  void issueImageCaptureRequests()
  {
    Object localObject = (ImageCaptureRequest)this.mPendingImageCaptureRequests.poll();
    if (localObject == null) {
      return;
    }
    if (!takePictureInternal((ImageCaptureRequest)localObject))
    {
      Log.d("ImageCapture", "Unable to issue take picture. Re-queuing image capture request");
      this.mPendingImageCaptureRequests.offerFirst(localObject);
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Size of image capture request queue: ");
    ((StringBuilder)localObject).append(this.mPendingImageCaptureRequests.size());
    Log.d("ImageCapture", ((StringBuilder)localObject).toString());
  }
  
  ListenableFuture<Void> issueTakePicture(ImageCaptureRequest paramImageCaptureRequest)
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    Object localObject1;
    if (this.mCaptureProcessor != null)
    {
      localObject1 = getCaptureBundle(null);
      if (localObject1 == null) {
        return Futures.immediateFailedFuture(new IllegalArgumentException("ImageCapture cannot set empty CaptureBundle."));
      }
      if (((CaptureBundle)localObject1).getCaptureStages().size() > this.mMaxCaptureStages) {
        return Futures.immediateFailedFuture(new IllegalArgumentException("ImageCapture has CaptureStages > Max CaptureStage size"));
      }
      ((ProcessingImageReader)this.mImageReader).setCaptureBundle((CaptureBundle)localObject1);
    }
    else
    {
      localObject2 = getCaptureBundle(CaptureBundles.singleDefaultCaptureBundle());
      localObject1 = localObject2;
      if (((CaptureBundle)localObject2).getCaptureStages().size() > 1) {
        return Futures.immediateFailedFuture(new IllegalArgumentException("ImageCapture have no CaptureProcess set with CaptureBundle size > 1."));
      }
    }
    Object localObject2 = ((CaptureBundle)localObject1).getCaptureStages().iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject1 = (CaptureStage)((Iterator)localObject2).next();
      CaptureConfig.Builder localBuilder = new CaptureConfig.Builder();
      localBuilder.setTemplateType(this.mCaptureConfig.getTemplateType());
      localBuilder.addImplementationOptions(this.mCaptureConfig.getImplementationOptions());
      localBuilder.addAllCameraCaptureCallbacks(this.mSessionConfigBuilder.getSingleCameraCaptureCallbacks());
      localBuilder.addSurface(this.mDeferrableSurface);
      localBuilder.addImplementationOption(CaptureConfig.OPTION_ROTATION, Integer.valueOf(paramImageCaptureRequest.mRotationDegrees));
      localBuilder.addImplementationOptions(((CaptureStage)localObject1).getCaptureConfig().getImplementationOptions());
      localBuilder.setTag(((CaptureStage)localObject1).getCaptureConfig().getTag());
      localBuilder.addCameraCaptureCallback(this.mMetadataMatchingCaptureCallback);
      localArrayList1.add(CallbackToFutureAdapter.getFuture(new _..Lambda.ImageCapture.5JLyPf4u5drRQ_HJ2fPCo738ics(this, localBuilder, localArrayList2, (CaptureStage)localObject1)));
    }
    getCurrentCameraControl().submitCaptureRequests(localArrayList2);
    return Futures.transform(Futures.allAsList(localArrayList1), _..Lambda.ImageCapture.WNq6_haNO4IMmVes0WnqB75kIGY.INSTANCE, CameraXExecutors.directExecutor());
  }
  
  protected void onCameraControlReady(String paramString)
  {
    getCameraControl(paramString).setFlashMode(this.mFlashMode);
  }
  
  public void onStateOffline(String paramString)
  {
    super.onStateOffline(paramString);
    abortImageCaptureRequests();
  }
  
  protected Map<String, Size> onSuggestedResolutionUpdated(Map<String, Size> paramMap)
  {
    String str = getBoundCameraId();
    Object localObject = (Size)paramMap.get(str);
    if (localObject != null)
    {
      localObject = createPipeline(str, this.mConfig, (Size)localObject);
      this.mSessionConfigBuilder = ((SessionConfig.Builder)localObject);
      attachToCamera(str, ((SessionConfig.Builder)localObject).build());
      notifyActive();
      return paramMap;
    }
    paramMap = new StringBuilder();
    paramMap.append("Suggested resolution map missing resolution for camera ");
    paramMap.append(str);
    throw new IllegalArgumentException(paramMap.toString());
  }
  
  void postTakePicture(TakePictureState paramTakePictureState)
  {
    this.mExecutor.execute(new _..Lambda.ImageCapture.ynb_pYfrtrQASytXNtToI3IXMF4(this, paramTakePictureState));
  }
  
  public void setCropAspectRatio(Rational paramRational)
  {
    ImageCaptureConfig localImageCaptureConfig = (ImageCaptureConfig)getUseCaseConfig();
    Builder localBuilder = Builder.fromConfig(localImageCaptureConfig);
    if (!paramRational.equals(localImageCaptureConfig.getTargetAspectRatioCustom(null)))
    {
      localBuilder.setTargetAspectRatioCustom(paramRational);
      updateUseCaseConfig(localBuilder.getUseCaseConfig());
      this.mConfig = ((ImageCaptureConfig)getUseCaseConfig());
    }
  }
  
  public void setFlashMode(int paramInt)
  {
    this.mFlashMode = paramInt;
    if (getBoundCamera() != null) {
      getCurrentCameraControl().setFlashMode(paramInt);
    }
  }
  
  public void setTargetRotation(int paramInt)
  {
    ImageCaptureConfig localImageCaptureConfig = (ImageCaptureConfig)getUseCaseConfig();
    Builder localBuilder = Builder.fromConfig(localImageCaptureConfig);
    int i = localImageCaptureConfig.getTargetRotation(-1);
    if ((i == -1) || (i != paramInt))
    {
      UseCaseConfigUtil.updateTargetRotationAndRelatedConfigs(localBuilder, paramInt);
      updateUseCaseConfig(localBuilder.getUseCaseConfig());
      this.mConfig = ((ImageCaptureConfig)getUseCaseConfig());
    }
  }
  
  public void takePicture(final OutputFileOptions paramOutputFileOptions, final Executor paramExecutor, final OnImageSavedCallback paramOnImageSavedCallback)
  {
    if (Looper.getMainLooper() != Looper.myLooper())
    {
      CameraXExecutors.mainThreadExecutor().execute(new _..Lambda.ImageCapture.GKCJoIxQ7x6CHqe5VLbTJu0vu5E(this, paramOutputFileOptions, paramExecutor, paramOnImageSavedCallback));
      return;
    }
    paramOutputFileOptions = new OnImageCapturedCallback()
    {
      public void onError(ImageSaver.SaveError paramAnonymousSaveError, String paramAnonymousString, Throwable paramAnonymousThrowable)
      {
        int i = ImageCapture.9.$SwitchMap$androidx$camera$core$ImageSaver$SaveError[paramAnonymousSaveError.ordinal()];
        int j = 1;
        if (i != 1) {
          j = 0;
        }
        paramOnImageSavedCallback.onError(new ImageCaptureException(j, paramAnonymousString, paramAnonymousThrowable));
      }
      
      public void onImageSaved(ImageCapture.OutputFileResults paramAnonymousOutputFileResults)
      {
        paramOnImageSavedCallback.onImageSaved(paramAnonymousOutputFileResults);
      }
    }
    {
      public void onCaptureSuccess(ImageProxy paramAnonymousImageProxy)
      {
        ImageCapture.this.mIoExecutor.execute(new ImageSaver(paramAnonymousImageProxy, paramOutputFileOptions, paramAnonymousImageProxy.getImageInfo().getRotationDegrees(), paramExecutor, this.val$imageSavedCallbackWrapper));
      }
      
      public void onError(ImageCaptureException paramAnonymousImageCaptureException)
      {
        paramOnImageSavedCallback.onError(paramAnonymousImageCaptureException);
      }
    };
    sendImageCaptureRequest(CameraXExecutors.mainThreadExecutor(), paramOutputFileOptions);
  }
  
  public void takePicture(Executor paramExecutor, OnImageCapturedCallback paramOnImageCapturedCallback)
  {
    if (Looper.getMainLooper() != Looper.myLooper())
    {
      CameraXExecutors.mainThreadExecutor().execute(new _..Lambda.ImageCapture.3TBxQKmNJ9VTGCKo1ZKi7E3ESgI(this, paramExecutor, paramOnImageCapturedCallback));
      return;
    }
    sendImageCaptureRequest(paramExecutor, paramOnImageCapturedCallback);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ImageCapture:");
    localStringBuilder.append(getName());
    return localStringBuilder.toString();
  }
  
  void triggerAePrecapture(TakePictureState paramTakePictureState)
  {
    paramTakePictureState.mIsAePrecaptureTriggered = true;
    getCurrentCameraControl().triggerAePrecapture();
  }
  
  void triggerAfIfNeeded(TakePictureState paramTakePictureState)
  {
    if ((this.mEnableCheck3AConverged) && (paramTakePictureState.mPreCaptureState.getAfMode() == CameraCaptureMetaData.AfMode.ON_MANUAL_AUTO) && (paramTakePictureState.mPreCaptureState.getAfState() == CameraCaptureMetaData.AfState.INACTIVE)) {
      triggerAf(paramTakePictureState);
    }
  }
  
  public static final class Builder
    implements UseCaseConfig.Builder<ImageCapture, ImageCaptureConfig, Builder>, ImageOutputConfig.Builder<Builder>, IoConfig.Builder<Builder>
  {
    private final MutableOptionsBundle mMutableConfig;
    
    public Builder()
    {
      this(MutableOptionsBundle.create());
    }
    
    private Builder(MutableOptionsBundle paramMutableOptionsBundle)
    {
      this.mMutableConfig = paramMutableOptionsBundle;
      Class localClass = (Class)paramMutableOptionsBundle.retrieveOption(TargetConfig.OPTION_TARGET_CLASS, null);
      if ((localClass != null) && (!localClass.equals(ImageCapture.class)))
      {
        paramMutableOptionsBundle = new StringBuilder();
        paramMutableOptionsBundle.append("Invalid target class configuration for ");
        paramMutableOptionsBundle.append(this);
        paramMutableOptionsBundle.append(": ");
        paramMutableOptionsBundle.append(localClass);
        throw new IllegalArgumentException(paramMutableOptionsBundle.toString());
      }
      setTargetClass(ImageCapture.class);
    }
    
    public static Builder fromConfig(ImageCaptureConfig paramImageCaptureConfig)
    {
      return new Builder(MutableOptionsBundle.from(paramImageCaptureConfig));
    }
    
    public ImageCapture build()
    {
      if ((getMutableConfig().retrieveOption(ImageCaptureConfig.OPTION_TARGET_ASPECT_RATIO, null) != null) && (getMutableConfig().retrieveOption(ImageCaptureConfig.OPTION_TARGET_RESOLUTION, null) != null)) {
        throw new IllegalArgumentException("Cannot use both setTargetResolution and setTargetAspectRatio on the same config.");
      }
      return new ImageCapture(getUseCaseConfig());
    }
    
    public MutableConfig getMutableConfig()
    {
      return this.mMutableConfig;
    }
    
    public ImageCaptureConfig getUseCaseConfig()
    {
      return new ImageCaptureConfig(OptionsBundle.from(this.mMutableConfig));
    }
    
    public Builder setBufferFormat(int paramInt)
    {
      getMutableConfig().insertOption(ImageCaptureConfig.OPTION_BUFFER_FORMAT, Integer.valueOf(paramInt));
      return this;
    }
    
    public Builder setCameraSelector(CameraSelector paramCameraSelector)
    {
      getMutableConfig().insertOption(UseCaseConfig.OPTION_CAMERA_SELECTOR, paramCameraSelector);
      return this;
    }
    
    public Builder setCaptureBundle(CaptureBundle paramCaptureBundle)
    {
      getMutableConfig().insertOption(ImageCaptureConfig.OPTION_CAPTURE_BUNDLE, paramCaptureBundle);
      return this;
    }
    
    public Builder setCaptureMode(int paramInt)
    {
      getMutableConfig().insertOption(ImageCaptureConfig.OPTION_IMAGE_CAPTURE_MODE, Integer.valueOf(paramInt));
      return this;
    }
    
    public Builder setCaptureOptionUnpacker(CaptureConfig.OptionUnpacker paramOptionUnpacker)
    {
      getMutableConfig().insertOption(ImageCaptureConfig.OPTION_CAPTURE_CONFIG_UNPACKER, paramOptionUnpacker);
      return this;
    }
    
    public Builder setCaptureProcessor(CaptureProcessor paramCaptureProcessor)
    {
      getMutableConfig().insertOption(ImageCaptureConfig.OPTION_CAPTURE_PROCESSOR, paramCaptureProcessor);
      return this;
    }
    
    public Builder setDefaultCaptureConfig(CaptureConfig paramCaptureConfig)
    {
      getMutableConfig().insertOption(ImageCaptureConfig.OPTION_DEFAULT_CAPTURE_CONFIG, paramCaptureConfig);
      return this;
    }
    
    public Builder setDefaultResolution(Size paramSize)
    {
      getMutableConfig().insertOption(ImageOutputConfig.OPTION_DEFAULT_RESOLUTION, paramSize);
      return this;
    }
    
    public Builder setDefaultSessionConfig(SessionConfig paramSessionConfig)
    {
      getMutableConfig().insertOption(ImageCaptureConfig.OPTION_DEFAULT_SESSION_CONFIG, paramSessionConfig);
      return this;
    }
    
    public Builder setFlashMode(int paramInt)
    {
      getMutableConfig().insertOption(ImageCaptureConfig.OPTION_FLASH_MODE, Integer.valueOf(paramInt));
      return this;
    }
    
    public Builder setIoExecutor(Executor paramExecutor)
    {
      getMutableConfig().insertOption(ImageCaptureConfig.OPTION_IO_EXECUTOR, paramExecutor);
      return this;
    }
    
    public Builder setMaxCaptureStages(int paramInt)
    {
      getMutableConfig().insertOption(ImageCaptureConfig.OPTION_MAX_CAPTURE_STAGES, Integer.valueOf(paramInt));
      return this;
    }
    
    public Builder setMaxResolution(Size paramSize)
    {
      getMutableConfig().insertOption(ImageCaptureConfig.OPTION_MAX_RESOLUTION, paramSize);
      return this;
    }
    
    public Builder setSessionOptionUnpacker(SessionConfig.OptionUnpacker paramOptionUnpacker)
    {
      getMutableConfig().insertOption(ImageCaptureConfig.OPTION_SESSION_CONFIG_UNPACKER, paramOptionUnpacker);
      return this;
    }
    
    public Builder setSupportedResolutions(List<Pair<Integer, Size[]>> paramList)
    {
      getMutableConfig().insertOption(ImageCaptureConfig.OPTION_SUPPORTED_RESOLUTIONS, paramList);
      return this;
    }
    
    public Builder setSurfaceOccupancyPriority(int paramInt)
    {
      getMutableConfig().insertOption(ImageCaptureConfig.OPTION_SURFACE_OCCUPANCY_PRIORITY, Integer.valueOf(paramInt));
      return this;
    }
    
    public Builder setTargetAspectRatio(int paramInt)
    {
      getMutableConfig().insertOption(ImageCaptureConfig.OPTION_TARGET_ASPECT_RATIO, Integer.valueOf(paramInt));
      return this;
    }
    
    public Builder setTargetAspectRatioCustom(Rational paramRational)
    {
      getMutableConfig().insertOption(ImageCaptureConfig.OPTION_TARGET_ASPECT_RATIO_CUSTOM, paramRational);
      getMutableConfig().removeOption(ImageCaptureConfig.OPTION_TARGET_ASPECT_RATIO);
      return this;
    }
    
    public Builder setTargetClass(Class<ImageCapture> paramClass)
    {
      getMutableConfig().insertOption(ImageCaptureConfig.OPTION_TARGET_CLASS, paramClass);
      if (getMutableConfig().retrieveOption(ImageCaptureConfig.OPTION_TARGET_NAME, null) == null)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramClass.getCanonicalName());
        localStringBuilder.append("-");
        localStringBuilder.append(UUID.randomUUID());
        setTargetName(localStringBuilder.toString());
      }
      return this;
    }
    
    public Builder setTargetName(String paramString)
    {
      getMutableConfig().insertOption(ImageCaptureConfig.OPTION_TARGET_NAME, paramString);
      return this;
    }
    
    public Builder setTargetResolution(Size paramSize)
    {
      getMutableConfig().insertOption(ImageCaptureConfig.OPTION_TARGET_RESOLUTION, paramSize);
      if (paramSize != null) {
        getMutableConfig().insertOption(ImageCaptureConfig.OPTION_TARGET_ASPECT_RATIO_CUSTOM, new Rational(paramSize.getWidth(), paramSize.getHeight()));
      }
      return this;
    }
    
    public Builder setTargetRotation(int paramInt)
    {
      getMutableConfig().insertOption(ImageCaptureConfig.OPTION_TARGET_ROTATION, Integer.valueOf(paramInt));
      return this;
    }
    
    public Builder setUseCaseEventCallback(UseCase.EventCallback paramEventCallback)
    {
      getMutableConfig().insertOption(ImageCaptureConfig.OPTION_USE_CASE_EVENT_CALLBACK, paramEventCallback);
      return this;
    }
  }
  
  static final class CaptureCallbackChecker
    extends CameraCaptureCallback
  {
    private static final long NO_TIMEOUT = 0L;
    private final Set<CaptureResultListener> mCaptureResultListeners = new HashSet();
    
    CaptureCallbackChecker() {}
    
    private void deliverCaptureResultToListeners(CameraCaptureResult paramCameraCaptureResult)
    {
      Set localSet = this.mCaptureResultListeners;
      Object localObject1 = null;
      try
      {
        Object localObject2 = new java/util/HashSet;
        ((HashSet)localObject2).<init>(this.mCaptureResultListeners);
        Iterator localIterator = ((HashSet)localObject2).iterator();
        while (localIterator.hasNext())
        {
          CaptureResultListener localCaptureResultListener = (CaptureResultListener)localIterator.next();
          if (localCaptureResultListener.onCaptureResult(paramCameraCaptureResult))
          {
            localObject2 = localObject1;
            if (localObject1 == null)
            {
              localObject2 = new java/util/HashSet;
              ((HashSet)localObject2).<init>();
            }
            ((Set)localObject2).add(localCaptureResultListener);
            localObject1 = localObject2;
          }
        }
        if (localObject1 != null) {
          this.mCaptureResultListeners.removeAll(localObject1);
        }
        return;
      }
      finally {}
    }
    
    void addListener(CaptureResultListener paramCaptureResultListener)
    {
      synchronized (this.mCaptureResultListeners)
      {
        this.mCaptureResultListeners.add(paramCaptureResultListener);
        return;
      }
    }
    
    <T> ListenableFuture<T> checkCaptureResult(CaptureResultChecker<T> paramCaptureResultChecker)
    {
      return checkCaptureResult(paramCaptureResultChecker, 0L, null);
    }
    
    <T> ListenableFuture<T> checkCaptureResult(CaptureResultChecker<T> paramCaptureResultChecker, long paramLong, T paramT)
    {
      long l = 0L;
      boolean bool = paramLong < 0L;
      if (!bool)
      {
        if (bool) {
          l = SystemClock.elapsedRealtime();
        }
        return CallbackToFutureAdapter.getFuture(new _..Lambda.ImageCapture.CaptureCallbackChecker.RVxDy_zAdeqk9wi1C8KZybyVmF8(this, paramCaptureResultChecker, l, paramLong, paramT));
      }
      paramCaptureResultChecker = new StringBuilder();
      paramCaptureResultChecker.append("Invalid timeout value: ");
      paramCaptureResultChecker.append(paramLong);
      throw new IllegalArgumentException(paramCaptureResultChecker.toString());
    }
    
    public void onCaptureCompleted(CameraCaptureResult paramCameraCaptureResult)
    {
      deliverCaptureResultToListeners(paramCameraCaptureResult);
    }
    
    public static abstract interface CaptureResultChecker<T>
    {
      public abstract T check(CameraCaptureResult paramCameraCaptureResult);
    }
    
    private static abstract interface CaptureResultListener
    {
      public abstract boolean onCaptureResult(CameraCaptureResult paramCameraCaptureResult);
    }
  }
  
  static final class CaptureFailedException
    extends RuntimeException
  {
    CaptureFailedException(String paramString)
    {
      super();
    }
    
    CaptureFailedException(String paramString, Throwable paramThrowable)
    {
      super(paramThrowable);
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface CaptureMode {}
  
  public static final class Defaults
    implements ConfigProvider<ImageCaptureConfig>
  {
    private static final int DEFAULT_CAPTURE_MODE = 1;
    private static final ImageCaptureConfig DEFAULT_CONFIG = new ImageCapture.Builder().setCaptureMode(1).setFlashMode(2).setSurfaceOccupancyPriority(4).getUseCaseConfig();
    private static final int DEFAULT_FLASH_MODE = 2;
    private static final int DEFAULT_SURFACE_OCCUPANCY_PRIORITY = 4;
    
    public Defaults() {}
    
    public ImageCaptureConfig getConfig(CameraInfo paramCameraInfo)
    {
      return DEFAULT_CONFIG;
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface FlashMode {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface ImageCaptureError {}
  
  private static final class ImageCaptureRequest
  {
    private final ImageCapture.OnImageCapturedCallback mCallback;
    AtomicBoolean mDispatched = new AtomicBoolean(false);
    private final Executor mListenerExecutor;
    final int mRotationDegrees;
    private final Rational mTargetRatio;
    
    ImageCaptureRequest(int paramInt, Rational paramRational, Executor paramExecutor, ImageCapture.OnImageCapturedCallback paramOnImageCapturedCallback)
    {
      this.mRotationDegrees = paramInt;
      this.mTargetRatio = paramRational;
      this.mListenerExecutor = paramExecutor;
      this.mCallback = paramOnImageCapturedCallback;
    }
    
    void dispatchImage(ImageProxy paramImageProxy)
    {
      if (!this.mDispatched.compareAndSet(false, true)) {
        return;
      }
      try
      {
        Executor localExecutor = this.mListenerExecutor;
        _..Lambda.ImageCapture.ImageCaptureRequest.E13UUGhRx8PID5WbaCuwcGP87BA localE13UUGhRx8PID5WbaCuwcGP87BA = new androidx/camera/core/_$$Lambda$ImageCapture$ImageCaptureRequest$E13UUGhRx8PID5WbaCuwcGP87BA;
        localE13UUGhRx8PID5WbaCuwcGP87BA.<init>(this, paramImageProxy);
        localExecutor.execute(localE13UUGhRx8PID5WbaCuwcGP87BA);
      }
      catch (RejectedExecutionException localRejectedExecutionException)
      {
        Log.e("ImageCapture", "Unable to post to the supplied executor.");
        paramImageProxy.close();
      }
    }
    
    void notifyCallbackError(int paramInt, String paramString, Throwable paramThrowable)
    {
      if (!this.mDispatched.compareAndSet(false, true)) {
        return;
      }
      try
      {
        Executor localExecutor = this.mListenerExecutor;
        _..Lambda.ImageCapture.ImageCaptureRequest.1G7WSvt8TANxhZtOyewefm68pg4 local1G7WSvt8TANxhZtOyewefm68pg4 = new androidx/camera/core/_$$Lambda$ImageCapture$ImageCaptureRequest$1G7WSvt8TANxhZtOyewefm68pg4;
        local1G7WSvt8TANxhZtOyewefm68pg4.<init>(this, paramInt, paramString, paramThrowable);
        localExecutor.execute(local1G7WSvt8TANxhZtOyewefm68pg4);
      }
      catch (RejectedExecutionException paramString)
      {
        Log.e("ImageCapture", "Unable to post to the supplied executor.");
      }
    }
  }
  
  public static final class Metadata
  {
    private boolean mIsReversedHorizontal;
    private boolean mIsReversedVertical;
    private Location mLocation;
    
    public Metadata() {}
    
    public Location getLocation()
    {
      return this.mLocation;
    }
    
    public boolean isReversedHorizontal()
    {
      return this.mIsReversedHorizontal;
    }
    
    public boolean isReversedVertical()
    {
      return this.mIsReversedVertical;
    }
    
    public void setLocation(Location paramLocation)
    {
      this.mLocation = paramLocation;
    }
    
    public void setReversedHorizontal(boolean paramBoolean)
    {
      this.mIsReversedHorizontal = paramBoolean;
    }
    
    public void setReversedVertical(boolean paramBoolean)
    {
      this.mIsReversedVertical = paramBoolean;
    }
  }
  
  public static abstract class OnImageCapturedCallback
  {
    public OnImageCapturedCallback() {}
    
    public void onCaptureSuccess(ImageProxy paramImageProxy)
    {
      paramImageProxy.close();
    }
    
    public void onError(ImageCaptureException paramImageCaptureException) {}
  }
  
  public static abstract interface OnImageSavedCallback
  {
    public abstract void onError(ImageCaptureException paramImageCaptureException);
    
    public abstract void onImageSaved(ImageCapture.OutputFileResults paramOutputFileResults);
  }
  
  public static final class OutputFileOptions
  {
    private static final ImageCapture.Metadata EMPTY_METADATA = new ImageCapture.Metadata();
    private final ContentResolver mContentResolver;
    private final ContentValues mContentValues;
    private final File mFile;
    private final ImageCapture.Metadata mMetadata;
    private final OutputStream mOutputStream;
    private final Uri mSaveCollection;
    
    OutputFileOptions(File paramFile, ContentResolver paramContentResolver, Uri paramUri, ContentValues paramContentValues, OutputStream paramOutputStream, ImageCapture.Metadata paramMetadata)
    {
      this.mFile = paramFile;
      this.mContentResolver = paramContentResolver;
      this.mSaveCollection = paramUri;
      this.mContentValues = paramContentValues;
      this.mOutputStream = paramOutputStream;
      paramFile = paramMetadata;
      if (paramMetadata == null) {
        paramFile = EMPTY_METADATA;
      }
      this.mMetadata = paramFile;
    }
    
    ContentResolver getContentResolver()
    {
      return this.mContentResolver;
    }
    
    ContentValues getContentValues()
    {
      return this.mContentValues;
    }
    
    File getFile()
    {
      return this.mFile;
    }
    
    ImageCapture.Metadata getMetadata()
    {
      return this.mMetadata;
    }
    
    OutputStream getOutputStream()
    {
      return this.mOutputStream;
    }
    
    Uri getSaveCollection()
    {
      return this.mSaveCollection;
    }
    
    public static final class Builder
    {
      private ContentResolver mContentResolver;
      private ContentValues mContentValues;
      private File mFile;
      private ImageCapture.Metadata mMetadata;
      private OutputStream mOutputStream;
      private Uri mSaveCollection;
      
      public Builder(ContentResolver paramContentResolver, Uri paramUri, ContentValues paramContentValues)
      {
        this.mContentResolver = paramContentResolver;
        this.mSaveCollection = paramUri;
        this.mContentValues = paramContentValues;
      }
      
      public Builder(File paramFile)
      {
        this.mFile = paramFile;
      }
      
      public Builder(OutputStream paramOutputStream)
      {
        this.mOutputStream = paramOutputStream;
      }
      
      public ImageCapture.OutputFileOptions build()
      {
        return new ImageCapture.OutputFileOptions(this.mFile, this.mContentResolver, this.mSaveCollection, this.mContentValues, this.mOutputStream, this.mMetadata);
      }
      
      public Builder setMetadata(ImageCapture.Metadata paramMetadata)
      {
        this.mMetadata = paramMetadata;
        return this;
      }
    }
  }
  
  public static class OutputFileResults
  {
    private Uri mSavedUri;
    
    OutputFileResults(Uri paramUri)
    {
      this.mSavedUri = paramUri;
    }
    
    public Uri getSavedUri()
    {
      return this.mSavedUri;
    }
  }
  
  private static class TakePictureLock
    implements ForwardingImageProxy.OnImageCloseListener
  {
    private ImageCapture.ImageCaptureRequest mCurrentRequest = null;
    private final ImageCapture mImageCapture;
    private final Object mLock = new Object();
    private final int mMaxImages;
    private int mOutstandingImages = 0;
    
    TakePictureLock(int paramInt, ImageCapture paramImageCapture)
    {
      this.mMaxImages = paramInt;
      this.mImageCapture = paramImageCapture;
    }
    
    void cancelTakePicture(Throwable paramThrowable)
    {
      synchronized (this.mLock)
      {
        if (this.mCurrentRequest != null) {
          this.mCurrentRequest.notifyCallbackError(ImageCapture.getError(paramThrowable), paramThrowable.getMessage(), paramThrowable);
        }
        this.mCurrentRequest = null;
        return;
      }
    }
    
    boolean lockTakePicture(ImageCapture.ImageCaptureRequest paramImageCaptureRequest)
    {
      synchronized (this.mLock)
      {
        if ((this.mOutstandingImages < this.mMaxImages) && (this.mCurrentRequest == null))
        {
          this.mCurrentRequest = paramImageCaptureRequest;
          return true;
        }
        return false;
      }
    }
    
    public void onImageClose(ImageProxy arg1)
    {
      synchronized (this.mLock)
      {
        this.mOutstandingImages -= 1;
        ScheduledExecutorService localScheduledExecutorService = CameraXExecutors.mainThreadExecutor();
        ImageCapture localImageCapture = this.mImageCapture;
        localImageCapture.getClass();
        _..Lambda.yfOD6i21YfFnMd1B6A6ekn93X8I localYfOD6i21YfFnMd1B6A6ekn93X8I = new androidx/camera/core/_$$Lambda$yfOD6i21YfFnMd1B6A6ekn93X8I;
        localYfOD6i21YfFnMd1B6A6ekn93X8I.<init>(localImageCapture);
        localScheduledExecutorService.execute(localYfOD6i21YfFnMd1B6A6ekn93X8I);
        return;
      }
    }
    
    ImageProxy tryAcquireImage(ImageReaderProxy paramImageReaderProxy, ImageCapture.ImageCaptureRequest paramImageCaptureRequest)
    {
      synchronized (this.mLock)
      {
        ImageCapture.ImageCaptureRequest localImageCaptureRequest = this.mCurrentRequest;
        Object localObject2 = null;
        Object localObject3 = null;
        if (localImageCaptureRequest != paramImageCaptureRequest)
        {
          Log.e("ImageCapture", "Attempting to acquire image with incorrect request");
          return null;
        }
        try
        {
          paramImageCaptureRequest = paramImageReaderProxy.acquireLatestImage();
          paramImageReaderProxy = localObject2;
          if (paramImageCaptureRequest != null)
          {
            paramImageReaderProxy = new androidx/camera/core/SingleCloseImageProxy;
            paramImageReaderProxy.<init>(paramImageCaptureRequest);
            try
            {
              paramImageReaderProxy.addOnImageCloseListener(this);
              this.mOutstandingImages += 1;
            }
            catch (IllegalStateException paramImageCaptureRequest) {}
            Log.e("ImageCapture", "Failed to acquire latest image.", paramImageCaptureRequest);
          }
        }
        catch (IllegalStateException paramImageCaptureRequest)
        {
          paramImageReaderProxy = localObject3;
        }
        return paramImageReaderProxy;
      }
    }
    
    boolean unlockTakePicture(ImageCapture.ImageCaptureRequest paramImageCaptureRequest)
    {
      synchronized (this.mLock)
      {
        if (this.mCurrentRequest != paramImageCaptureRequest) {
          return false;
        }
        this.mCurrentRequest = null;
        paramImageCaptureRequest = CameraXExecutors.mainThreadExecutor();
        ImageCapture localImageCapture = this.mImageCapture;
        localImageCapture.getClass();
        _..Lambda.yfOD6i21YfFnMd1B6A6ekn93X8I localYfOD6i21YfFnMd1B6A6ekn93X8I = new androidx/camera/core/_$$Lambda$yfOD6i21YfFnMd1B6A6ekn93X8I;
        localYfOD6i21YfFnMd1B6A6ekn93X8I.<init>(localImageCapture);
        paramImageCaptureRequest.execute(localYfOD6i21YfFnMd1B6A6ekn93X8I);
        return true;
      }
    }
  }
  
  static final class TakePictureState
  {
    boolean mIsAePrecaptureTriggered = false;
    boolean mIsAfTriggered = false;
    boolean mIsFlashTriggered = false;
    CameraCaptureResult mPreCaptureState = CameraCaptureResult.EmptyCameraCaptureResult.create();
    
    TakePictureState() {}
  }
}

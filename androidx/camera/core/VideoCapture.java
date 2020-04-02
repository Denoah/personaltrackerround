package androidx.camera.core;

import android.location.Location;
import android.media.AudioRecord;
import android.media.CamcorderProfile;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import androidx.camera.core.impl.ConfigProvider;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImmediateSurface;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.SessionConfig.Builder;
import androidx.camera.core.impl.SessionConfig.ErrorListener;
import androidx.camera.core.impl.SessionConfig.SessionError;
import androidx.camera.core.impl.UseCaseConfig.Builder;
import androidx.camera.core.impl.VideoCaptureConfig;
import androidx.camera.core.impl.VideoCaptureConfig.Builder;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.internal.utils.UseCaseConfigUtil;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

public class VideoCapture
  extends UseCase
{
  private static final String AUDIO_MIME_TYPE = "audio/mp4a-latm";
  private static final int[] CamcorderQuality = { 8, 6, 5, 4 };
  public static final Defaults DEFAULT_CONFIG = new Defaults();
  private static final int DEQUE_TIMEOUT_USEC = 10000;
  private static final Metadata EMPTY_METADATA = new Metadata();
  public static final int ERROR_ENCODER = 1;
  public static final int ERROR_MUXER = 2;
  public static final int ERROR_RECORDING_IN_PROGRESS = 3;
  public static final int ERROR_UNKNOWN = 0;
  private static final String TAG = "VideoCapture";
  private static final String VIDEO_MIME_TYPE = "video/avc";
  private static final short[] sAudioEncoding = { 2, 3, 4 };
  private int mAudioBitRate;
  private final MediaCodec.BufferInfo mAudioBufferInfo = new MediaCodec.BufferInfo();
  private int mAudioBufferSize;
  private int mAudioChannelCount;
  private MediaCodec mAudioEncoder;
  private final Handler mAudioHandler;
  private final HandlerThread mAudioHandlerThread = new HandlerThread("CameraX-audio encoding thread");
  private AudioRecord mAudioRecorder;
  private int mAudioSampleRate;
  private int mAudioTrackIndex;
  Surface mCameraSurface;
  private DeferrableSurface mDeferrableSurface;
  private final AtomicBoolean mEndOfAudioStreamSignal = new AtomicBoolean(true);
  private final AtomicBoolean mEndOfAudioVideoSignal = new AtomicBoolean(true);
  private final AtomicBoolean mEndOfVideoStreamSignal = new AtomicBoolean(true);
  private final AtomicBoolean mIsFirstAudioSampleWrite = new AtomicBoolean(false);
  private final AtomicBoolean mIsFirstVideoSampleWrite = new AtomicBoolean(false);
  private boolean mIsRecording = false;
  private MediaMuxer mMuxer;
  private final Object mMuxerLock = new Object();
  private boolean mMuxerStarted = false;
  private final MediaCodec.BufferInfo mVideoBufferInfo = new MediaCodec.BufferInfo();
  MediaCodec mVideoEncoder;
  private final Handler mVideoHandler;
  private final HandlerThread mVideoHandlerThread = new HandlerThread("CameraX-video encoding thread");
  private int mVideoTrackIndex;
  
  public VideoCapture(VideoCaptureConfig paramVideoCaptureConfig)
  {
    super(paramVideoCaptureConfig);
    this.mVideoHandlerThread.start();
    this.mVideoHandler = new Handler(this.mVideoHandlerThread.getLooper());
    this.mAudioHandlerThread.start();
    this.mAudioHandler = new Handler(this.mAudioHandlerThread.getLooper());
  }
  
  private AudioRecord autoConfigAudioRecordSource(VideoCaptureConfig paramVideoCaptureConfig)
  {
    short[] arrayOfShort = sAudioEncoding;
    int i = arrayOfShort.length;
    int j = 0;
    while (j < i)
    {
      int k = arrayOfShort[j];
      int m;
      if (this.mAudioChannelCount == 1) {
        m = 16;
      } else {
        m = 12;
      }
      int n = paramVideoCaptureConfig.getAudioRecordSource();
      try
      {
        int i1 = AudioRecord.getMinBufferSize(this.mAudioSampleRate, m, k);
        int i2 = i1;
        if (i1 <= 0) {
          i2 = paramVideoCaptureConfig.getAudioMinBufferSize();
        }
        AudioRecord localAudioRecord = new android/media/AudioRecord;
        localAudioRecord.<init>(n, this.mAudioSampleRate, m, k, i2 * 2);
        if (localAudioRecord.getState() == 1)
        {
          this.mAudioBufferSize = i2;
          StringBuilder localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>();
          localStringBuilder.append("source: ");
          localStringBuilder.append(n);
          localStringBuilder.append(" audioSampleRate: ");
          localStringBuilder.append(this.mAudioSampleRate);
          localStringBuilder.append(" channelConfig: ");
          localStringBuilder.append(m);
          localStringBuilder.append(" audioFormat: ");
          localStringBuilder.append(k);
          localStringBuilder.append(" bufferSize: ");
          localStringBuilder.append(i2);
          Log.i("VideoCapture", localStringBuilder.toString());
          return localAudioRecord;
        }
      }
      catch (Exception localException)
      {
        Log.e("VideoCapture", "Exception, keep trying.", localException);
        j++;
      }
    }
    return null;
  }
  
  private MediaFormat createAudioMediaFormat()
  {
    MediaFormat localMediaFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", this.mAudioSampleRate, this.mAudioChannelCount);
    localMediaFormat.setInteger("aac-profile", 2);
    localMediaFormat.setInteger("bitrate", this.mAudioBitRate);
    return localMediaFormat;
  }
  
  private static MediaFormat createMediaFormat(VideoCaptureConfig paramVideoCaptureConfig, Size paramSize)
  {
    paramSize = MediaFormat.createVideoFormat("video/avc", paramSize.getWidth(), paramSize.getHeight());
    paramSize.setInteger("color-format", 2130708361);
    paramSize.setInteger("bitrate", paramVideoCaptureConfig.getBitRate());
    paramSize.setInteger("frame-rate", paramVideoCaptureConfig.getVideoFrameRate());
    paramSize.setInteger("i-frame-interval", paramVideoCaptureConfig.getIFrameInterval());
    return paramSize;
  }
  
  private ByteBuffer getInputBuffer(MediaCodec paramMediaCodec, int paramInt)
  {
    return paramMediaCodec.getInputBuffer(paramInt);
  }
  
  private ByteBuffer getOutputBuffer(MediaCodec paramMediaCodec, int paramInt)
  {
    return paramMediaCodec.getOutputBuffer(paramInt);
  }
  
  private void releaseCameraSurface(boolean paramBoolean)
  {
    DeferrableSurface localDeferrableSurface = this.mDeferrableSurface;
    if (localDeferrableSurface == null) {
      return;
    }
    MediaCodec localMediaCodec = this.mVideoEncoder;
    localDeferrableSurface.close();
    this.mDeferrableSurface.getTerminationFuture().addListener(new _..Lambda.VideoCapture.vFHGdUhQ9YSrmNYVYvi35pHBmEc(paramBoolean, localMediaCodec), CameraXExecutors.mainThreadExecutor());
    if (paramBoolean) {
      this.mVideoEncoder = null;
    }
    this.mCameraSurface = null;
    this.mDeferrableSurface = null;
  }
  
  private void setAudioParametersByCamcorderProfile(Size paramSize, String paramString)
  {
    int[] arrayOfInt = CamcorderQuality;
    int i = arrayOfInt.length;
    int j = 0;
    int m;
    for (int k = 0;; k++)
    {
      m = j;
      if (k >= i) {
        break;
      }
      m = arrayOfInt[k];
      if (CamcorderProfile.hasProfile(Integer.parseInt(paramString), m))
      {
        CamcorderProfile localCamcorderProfile = CamcorderProfile.get(Integer.parseInt(paramString), m);
        if ((paramSize.getWidth() == localCamcorderProfile.videoFrameWidth) && (paramSize.getHeight() == localCamcorderProfile.videoFrameHeight))
        {
          this.mAudioChannelCount = localCamcorderProfile.audioChannels;
          this.mAudioSampleRate = localCamcorderProfile.audioSampleRate;
          this.mAudioBitRate = localCamcorderProfile.audioBitRate;
          m = 1;
          break;
        }
      }
    }
    if (m == 0)
    {
      paramSize = (VideoCaptureConfig)getUseCaseConfig();
      this.mAudioChannelCount = paramSize.getAudioChannelCount();
      this.mAudioSampleRate = paramSize.getAudioSampleRate();
      this.mAudioBitRate = paramSize.getAudioBitRate();
    }
  }
  
  private boolean writeAudioEncodedBuffer(int paramInt)
  {
    ByteBuffer localByteBuffer = getOutputBuffer(this.mAudioEncoder, paramInt);
    localByteBuffer.position(this.mAudioBufferInfo.offset);
    int i = this.mAudioTrackIndex;
    boolean bool = true;
    if ((i >= 0) && (this.mVideoTrackIndex >= 0) && (this.mAudioBufferInfo.size > 0) && (this.mAudioBufferInfo.presentationTimeUs > 0L)) {
      try
      {
        synchronized (this.mMuxerLock)
        {
          if (!this.mIsFirstAudioSampleWrite.get())
          {
            Log.i("VideoCapture", "First audio sample written.");
            this.mIsFirstAudioSampleWrite.set(true);
          }
          this.mMuxer.writeSampleData(this.mAudioTrackIndex, localByteBuffer, this.mAudioBufferInfo);
        }
        StringBuilder localStringBuilder;
        this.mAudioEncoder.releaseOutputBuffer(paramInt, false);
      }
      catch (Exception localException)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("audio error:size=");
        localStringBuilder.append(this.mAudioBufferInfo.size);
        localStringBuilder.append("/offset=");
        localStringBuilder.append(this.mAudioBufferInfo.offset);
        localStringBuilder.append("/timeUs=");
        localStringBuilder.append(this.mAudioBufferInfo.presentationTimeUs);
        Log.e("VideoCapture", localStringBuilder.toString());
        localException.printStackTrace();
      }
    }
    if ((this.mAudioBufferInfo.flags & 0x4) == 0) {
      bool = false;
    }
    return bool;
  }
  
  private boolean writeVideoEncodedBuffer(int paramInt)
  {
    boolean bool = false;
    if (paramInt < 0)
    {
      ??? = new StringBuilder();
      ((StringBuilder)???).append("Output buffer should not have negative index: ");
      ((StringBuilder)???).append(paramInt);
      Log.e("VideoCapture", ((StringBuilder)???).toString());
      return false;
    }
    ByteBuffer localByteBuffer = this.mVideoEncoder.getOutputBuffer(paramInt);
    if (localByteBuffer == null)
    {
      Log.d("VideoCapture", "OutputBuffer was null.");
      return false;
    }
    if ((this.mAudioTrackIndex >= 0) && (this.mVideoTrackIndex >= 0) && (this.mVideoBufferInfo.size > 0))
    {
      localByteBuffer.position(this.mVideoBufferInfo.offset);
      localByteBuffer.limit(this.mVideoBufferInfo.offset + this.mVideoBufferInfo.size);
      this.mVideoBufferInfo.presentationTimeUs = (System.nanoTime() / 1000L);
      synchronized (this.mMuxerLock)
      {
        if (!this.mIsFirstVideoSampleWrite.get())
        {
          Log.i("VideoCapture", "First video sample written.");
          this.mIsFirstVideoSampleWrite.set(true);
        }
        this.mMuxer.writeSampleData(this.mVideoTrackIndex, localByteBuffer, this.mVideoBufferInfo);
      }
    }
    this.mVideoEncoder.releaseOutputBuffer(paramInt, false);
    if ((this.mVideoBufferInfo.flags & 0x4) != 0) {
      bool = true;
    }
    return bool;
  }
  
  boolean audioEncode(OnVideoSavedCallback paramOnVideoSavedCallback)
  {
    boolean bool1 = false;
    while ((!bool1) && (this.mIsRecording))
    {
      if (this.mEndOfAudioStreamSignal.get())
      {
        this.mEndOfAudioStreamSignal.set(false);
        this.mIsRecording = false;
      }
      ??? = this.mAudioEncoder;
      if ((??? != null) && (this.mAudioRecorder != null))
      {
        int i = ((MediaCodec)???).dequeueInputBuffer(-1L);
        boolean bool2 = bool1;
        int j;
        if (i >= 0)
        {
          ??? = getInputBuffer(this.mAudioEncoder, i);
          ((ByteBuffer)???).clear();
          j = this.mAudioRecorder.read((ByteBuffer)???, this.mAudioBufferSize);
          bool2 = bool1;
          if (j > 0)
          {
            ??? = this.mAudioEncoder;
            long l = System.nanoTime() / 1000L;
            if (this.mIsRecording) {
              k = 0;
            } else {
              k = 4;
            }
            ((MediaCodec)???).queueInputBuffer(i, 0, j, l, k);
            bool2 = bool1;
          }
        }
        label153:
        int k = this.mAudioEncoder.dequeueOutputBuffer(this.mAudioBufferInfo, 0L);
        boolean bool3;
        if (k != -2)
        {
          bool3 = bool2;
          if (k == -1) {
            break label254;
          }
          bool3 = writeAudioEncodedBuffer(k);
        }
        synchronized (this.mMuxerLock)
        {
          j = this.mMuxer.addTrack(this.mAudioEncoder.getOutputFormat());
          this.mAudioTrackIndex = j;
          if ((j >= 0) && (this.mVideoTrackIndex >= 0))
          {
            this.mMuxerStarted = true;
            this.mMuxer.start();
          }
          bool3 = bool2;
          label254:
          bool1 = bool3;
          if (k >= 0)
          {
            bool2 = bool3;
            if (!bool3) {
              break label153;
            }
            bool1 = bool3;
          }
        }
      }
    }
    try
    {
      Log.i("VideoCapture", "audioRecorder stop");
      this.mAudioRecorder.stop();
    }
    catch (IllegalStateException localIllegalStateException1)
    {
      paramOnVideoSavedCallback.onError(1, "Audio recorder stop failed!", localIllegalStateException1);
    }
    try
    {
      this.mAudioEncoder.stop();
    }
    catch (IllegalStateException localIllegalStateException2)
    {
      paramOnVideoSavedCallback.onError(1, "Audio encoder stop failed!", localIllegalStateException2);
    }
    Log.i("VideoCapture", "Audio encode thread end");
    this.mEndOfVideoStreamSignal.set(true);
    return false;
  }
  
  public void clear()
  {
    this.mVideoHandlerThread.quitSafely();
    this.mAudioHandlerThread.quitSafely();
    Object localObject = this.mAudioEncoder;
    if (localObject != null)
    {
      ((MediaCodec)localObject).release();
      this.mAudioEncoder = null;
    }
    localObject = this.mAudioRecorder;
    if (localObject != null)
    {
      ((AudioRecord)localObject).release();
      this.mAudioRecorder = null;
    }
    if (this.mCameraSurface != null) {
      releaseCameraSurface(true);
    }
    super.clear();
  }
  
  protected UseCaseConfig.Builder<?, ?, ?> getDefaultBuilder(CameraInfo paramCameraInfo)
  {
    paramCameraInfo = (VideoCaptureConfig)CameraX.getDefaultUseCaseConfig(VideoCaptureConfig.class, paramCameraInfo);
    if (paramCameraInfo != null) {
      return VideoCaptureConfig.Builder.fromConfig(paramCameraInfo);
    }
    return null;
  }
  
  protected Map<String, Size> onSuggestedResolutionUpdated(Map<String, Size> paramMap)
  {
    if (this.mCameraSurface != null)
    {
      this.mVideoEncoder.stop();
      this.mVideoEncoder.release();
      this.mAudioEncoder.stop();
      this.mAudioEncoder.release();
      releaseCameraSurface(false);
    }
    try
    {
      this.mVideoEncoder = MediaCodec.createEncoderByType("video/avc");
      this.mAudioEncoder = MediaCodec.createEncoderByType("audio/mp4a-latm");
      localObject = getBoundCameraId();
      Size localSize = (Size)paramMap.get(localObject);
      if (localSize != null)
      {
        setupEncoder((String)localObject, localSize);
        return paramMap;
      }
      paramMap = new StringBuilder();
      paramMap.append("Suggested resolution map missing resolution for camera ");
      paramMap.append((String)localObject);
      throw new IllegalArgumentException(paramMap.toString());
    }
    catch (IOException paramMap)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Unable to create MediaCodec due to: ");
      ((StringBuilder)localObject).append(paramMap.getCause());
      throw new IllegalStateException(((StringBuilder)localObject).toString());
    }
  }
  
  public void setTargetRotation(int paramInt)
  {
    VideoCaptureConfig localVideoCaptureConfig = (VideoCaptureConfig)getUseCaseConfig();
    VideoCaptureConfig.Builder localBuilder = VideoCaptureConfig.Builder.fromConfig(localVideoCaptureConfig);
    int i = localVideoCaptureConfig.getTargetRotation(-1);
    if ((i == -1) || (i != paramInt))
    {
      UseCaseConfigUtil.updateTargetRotationAndRelatedConfigs(localBuilder, paramInt);
      updateUseCaseConfig(localBuilder.getUseCaseConfig());
    }
  }
  
  void setupEncoder(final String paramString, final Size paramSize)
  {
    VideoCaptureConfig localVideoCaptureConfig = (VideoCaptureConfig)getUseCaseConfig();
    this.mVideoEncoder.reset();
    this.mVideoEncoder.configure(createMediaFormat(localVideoCaptureConfig, paramSize), null, null, 1);
    if (this.mCameraSurface != null) {
      releaseCameraSurface(false);
    }
    Surface localSurface = this.mVideoEncoder.createInputSurface();
    this.mCameraSurface = localSurface;
    SessionConfig.Builder localBuilder = SessionConfig.Builder.createFrom(localVideoCaptureConfig);
    Object localObject = this.mDeferrableSurface;
    if (localObject != null) {
      ((DeferrableSurface)localObject).close();
    }
    localObject = new ImmediateSurface(this.mCameraSurface);
    this.mDeferrableSurface = ((DeferrableSurface)localObject);
    localObject = ((DeferrableSurface)localObject).getTerminationFuture();
    localSurface.getClass();
    ((ListenableFuture)localObject).addListener(new _..Lambda.bKhot3B1n1f2PgvvZExesMq2yMg(localSurface), CameraXExecutors.mainThreadExecutor());
    localBuilder.addSurface(this.mDeferrableSurface);
    localBuilder.addErrorListener(new SessionConfig.ErrorListener()
    {
      public void onError(SessionConfig paramAnonymousSessionConfig, SessionConfig.SessionError paramAnonymousSessionError)
      {
        if (VideoCapture.this.isCurrentlyBoundCamera(paramString)) {
          VideoCapture.this.setupEncoder(paramString, paramSize);
        }
      }
    });
    attachToCamera(paramString, localBuilder.build());
    setAudioParametersByCamcorderProfile(paramSize, paramString);
    this.mAudioEncoder.reset();
    this.mAudioEncoder.configure(createAudioMediaFormat(), null, null, 1);
    paramString = this.mAudioRecorder;
    if (paramString != null) {
      paramString.release();
    }
    paramString = autoConfigAudioRecordSource(localVideoCaptureConfig);
    this.mAudioRecorder = paramString;
    if (paramString == null) {
      Log.e("VideoCapture", "AudioRecord object cannot initialized correctly!");
    }
    this.mVideoTrackIndex = -1;
    this.mAudioTrackIndex = -1;
    this.mIsRecording = false;
  }
  
  /* Error */
  public void startRecording(final File paramFile, Metadata paramMetadata, final Executor paramExecutor, final OnVideoSavedCallback paramOnVideoSavedCallback)
  {
    // Byte code:
    //   0: ldc 49
    //   2: ldc_w 657
    //   5: invokestatic 232	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   8: pop
    //   9: new 24	androidx/camera/core/VideoCapture$VideoSavedListenerWrapper
    //   12: dup
    //   13: aload_0
    //   14: aload_3
    //   15: aload 4
    //   17: invokespecial 660	androidx/camera/core/VideoCapture$VideoSavedListenerWrapper:<init>	(Landroidx/camera/core/VideoCapture;Ljava/util/concurrent/Executor;Landroidx/camera/core/VideoCapture$OnVideoSavedCallback;)V
    //   20: astore_3
    //   21: aload_0
    //   22: getfield 145	androidx/camera/core/VideoCapture:mEndOfAudioVideoSignal	Ljava/util/concurrent/atomic/AtomicBoolean;
    //   25: invokevirtual 407	java/util/concurrent/atomic/AtomicBoolean:get	()Z
    //   28: ifne +15 -> 43
    //   31: aload_3
    //   32: iconst_3
    //   33: ldc_w 662
    //   36: aconst_null
    //   37: invokeinterface 513 4 0
    //   42: return
    //   43: aload_0
    //   44: getfield 468	androidx/camera/core/VideoCapture:mAudioRecorder	Landroid/media/AudioRecord;
    //   47: invokevirtual 664	android/media/AudioRecord:startRecording	()V
    //   50: aload_0
    //   51: invokevirtual 668	androidx/camera/core/VideoCapture:getBoundCamera	()Landroidx/camera/core/impl/CameraInternal;
    //   54: astore 5
    //   56: aload_0
    //   57: invokevirtual 553	androidx/camera/core/VideoCapture:getBoundCameraId	()Ljava/lang/String;
    //   60: astore 4
    //   62: aload_0
    //   63: aload 4
    //   65: invokevirtual 672	androidx/camera/core/VideoCapture:getAttachedSurfaceResolution	(Ljava/lang/String;)Landroid/util/Size;
    //   68: astore 6
    //   70: ldc 49
    //   72: ldc_w 674
    //   75: invokestatic 232	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   78: pop
    //   79: aload_0
    //   80: getfield 305	androidx/camera/core/VideoCapture:mVideoEncoder	Landroid/media/MediaCodec;
    //   83: invokevirtual 675	android/media/MediaCodec:start	()V
    //   86: ldc 49
    //   88: ldc_w 677
    //   91: invokestatic 232	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   94: pop
    //   95: aload_0
    //   96: getfield 382	androidx/camera/core/VideoCapture:mAudioEncoder	Landroid/media/MediaCodec;
    //   99: invokevirtual 675	android/media/MediaCodec:start	()V
    //   102: aload 5
    //   104: invokeinterface 683 1 0
    //   109: aload_0
    //   110: invokevirtual 369	androidx/camera/core/VideoCapture:getUseCaseConfig	()Landroidx/camera/core/impl/UseCaseConfig;
    //   113: checkcast 685	androidx/camera/core/impl/ImageOutputConfig
    //   116: iconst_0
    //   117: invokeinterface 686 2 0
    //   122: invokeinterface 691 2 0
    //   127: istore 7
    //   129: aload_0
    //   130: getfield 121	androidx/camera/core/VideoCapture:mMuxerLock	Ljava/lang/Object;
    //   133: astore 5
    //   135: aload 5
    //   137: monitorenter
    //   138: new 416	android/media/MediaMuxer
    //   141: astore 8
    //   143: aload 8
    //   145: aload_1
    //   146: invokevirtual 696	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   149: iconst_0
    //   150: invokespecial 698	android/media/MediaMuxer:<init>	(Ljava/lang/String;I)V
    //   153: aload_0
    //   154: aload 8
    //   156: putfield 414	androidx/camera/core/VideoCapture:mMuxer	Landroid/media/MediaMuxer;
    //   159: aload 8
    //   161: iload 7
    //   163: invokevirtual 701	android/media/MediaMuxer:setOrientationHint	(I)V
    //   166: aload_2
    //   167: getfield 705	androidx/camera/core/VideoCapture$Metadata:location	Landroid/location/Location;
    //   170: ifnull +26 -> 196
    //   173: aload_0
    //   174: getfield 414	androidx/camera/core/VideoCapture:mMuxer	Landroid/media/MediaMuxer;
    //   177: aload_2
    //   178: getfield 705	androidx/camera/core/VideoCapture$Metadata:location	Landroid/location/Location;
    //   181: invokevirtual 711	android/location/Location:getLatitude	()D
    //   184: d2f
    //   185: aload_2
    //   186: getfield 705	androidx/camera/core/VideoCapture$Metadata:location	Landroid/location/Location;
    //   189: invokevirtual 714	android/location/Location:getLongitude	()D
    //   192: d2f
    //   193: invokevirtual 718	android/media/MediaMuxer:setLocation	(FF)V
    //   196: aload 5
    //   198: monitorexit
    //   199: aload_0
    //   200: getfield 141	androidx/camera/core/VideoCapture:mEndOfVideoStreamSignal	Ljava/util/concurrent/atomic/AtomicBoolean;
    //   203: iconst_0
    //   204: invokevirtual 412	java/util/concurrent/atomic/AtomicBoolean:set	(Z)V
    //   207: aload_0
    //   208: getfield 143	androidx/camera/core/VideoCapture:mEndOfAudioStreamSignal	Ljava/util/concurrent/atomic/AtomicBoolean;
    //   211: iconst_0
    //   212: invokevirtual 412	java/util/concurrent/atomic/AtomicBoolean:set	(Z)V
    //   215: aload_0
    //   216: getfield 145	androidx/camera/core/VideoCapture:mEndOfAudioVideoSignal	Ljava/util/concurrent/atomic/AtomicBoolean;
    //   219: iconst_0
    //   220: invokevirtual 412	java/util/concurrent/atomic/AtomicBoolean:set	(Z)V
    //   223: aload_0
    //   224: iconst_1
    //   225: putfield 155	androidx/camera/core/VideoCapture:mIsRecording	Z
    //   228: aload_0
    //   229: invokevirtual 721	androidx/camera/core/VideoCapture:notifyActive	()V
    //   232: aload_0
    //   233: getfield 171	androidx/camera/core/VideoCapture:mAudioHandler	Landroid/os/Handler;
    //   236: new 6	androidx/camera/core/VideoCapture$1
    //   239: dup
    //   240: aload_0
    //   241: aload_3
    //   242: invokespecial 724	androidx/camera/core/VideoCapture$1:<init>	(Landroidx/camera/core/VideoCapture;Landroidx/camera/core/VideoCapture$OnVideoSavedCallback;)V
    //   245: invokevirtual 728	android/os/Handler:post	(Ljava/lang/Runnable;)Z
    //   248: pop
    //   249: aload_0
    //   250: getfield 169	androidx/camera/core/VideoCapture:mVideoHandler	Landroid/os/Handler;
    //   253: new 8	androidx/camera/core/VideoCapture$2
    //   256: dup
    //   257: aload_0
    //   258: aload_3
    //   259: aload 4
    //   261: aload 6
    //   263: aload_1
    //   264: invokespecial 731	androidx/camera/core/VideoCapture$2:<init>	(Landroidx/camera/core/VideoCapture;Landroidx/camera/core/VideoCapture$OnVideoSavedCallback;Ljava/lang/String;Landroid/util/Size;Ljava/io/File;)V
    //   267: invokevirtual 728	android/os/Handler:post	(Ljava/lang/Runnable;)Z
    //   270: pop
    //   271: return
    //   272: astore_1
    //   273: aload 5
    //   275: monitorexit
    //   276: aload_1
    //   277: athrow
    //   278: astore_1
    //   279: aload_0
    //   280: aload 4
    //   282: aload 6
    //   284: invokevirtual 562	androidx/camera/core/VideoCapture:setupEncoder	(Ljava/lang/String;Landroid/util/Size;)V
    //   287: aload_3
    //   288: iconst_2
    //   289: ldc_w 733
    //   292: aload_1
    //   293: invokeinterface 513 4 0
    //   298: return
    //   299: astore_1
    //   300: aload_0
    //   301: aload 4
    //   303: aload 6
    //   305: invokevirtual 562	androidx/camera/core/VideoCapture:setupEncoder	(Ljava/lang/String;Landroid/util/Size;)V
    //   308: aload_3
    //   309: iconst_1
    //   310: ldc_w 735
    //   313: aload_1
    //   314: invokeinterface 513 4 0
    //   319: return
    //   320: astore_1
    //   321: aload_3
    //   322: iconst_1
    //   323: ldc_w 737
    //   326: aload_1
    //   327: invokeinterface 513 4 0
    //   332: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	333	0	this	VideoCapture
    //   0	333	1	paramFile	File
    //   0	333	2	paramMetadata	Metadata
    //   0	333	3	paramExecutor	Executor
    //   0	333	4	paramOnVideoSavedCallback	OnVideoSavedCallback
    //   68	236	6	localSize	Size
    //   127	35	7	i	int
    //   141	19	8	localMediaMuxer	MediaMuxer
    // Exception table:
    //   from	to	target	type
    //   138	196	272	finally
    //   196	199	272	finally
    //   273	276	272	finally
    //   129	138	278	java/io/IOException
    //   276	278	278	java/io/IOException
    //   70	102	299	java/lang/IllegalStateException
    //   43	50	320	java/lang/IllegalStateException
  }
  
  public void startRecording(File paramFile, Executor paramExecutor, OnVideoSavedCallback paramOnVideoSavedCallback)
  {
    this.mIsFirstVideoSampleWrite.set(false);
    this.mIsFirstAudioSampleWrite.set(false);
    startRecording(paramFile, EMPTY_METADATA, paramExecutor, paramOnVideoSavedCallback);
  }
  
  public void stopRecording()
  {
    Log.i("VideoCapture", "stopRecording");
    notifyInactive();
    if ((!this.mEndOfAudioVideoSignal.get()) && (this.mIsRecording)) {
      this.mEndOfAudioStreamSignal.set(true);
    }
  }
  
  boolean videoEncode(OnVideoSavedCallback paramOnVideoSavedCallback, String paramString, Size paramSize)
  {
    boolean bool1 = false;
    bool2 = bool1;
    while ((!bool1) && (!bool2))
    {
      if (this.mEndOfVideoStreamSignal.get())
      {
        this.mVideoEncoder.signalEndOfInputStream();
        this.mEndOfVideoStreamSignal.set(false);
      }
      int i = this.mVideoEncoder.dequeueOutputBuffer(this.mVideoBufferInfo, 10000L);
      if (i != -2)
      {
        bool1 = writeVideoEncodedBuffer(i);
      }
      else
      {
        if (this.mMuxerStarted)
        {
          paramOnVideoSavedCallback.onError(1, "Unexpected change in video encoding format.", null);
          bool2 = true;
        }
        synchronized (this.mMuxerLock)
        {
          i = this.mMuxer.addTrack(this.mVideoEncoder.getOutputFormat());
          this.mVideoTrackIndex = i;
          if ((this.mAudioTrackIndex >= 0) && (i >= 0))
          {
            this.mMuxerStarted = true;
            Log.i("VideoCapture", "media mMuxer start");
            this.mMuxer.start();
          }
        }
      }
    }
    try
    {
      Log.i("VideoCapture", "videoEncoder stop");
      this.mVideoEncoder.stop();
    }
    catch (IllegalStateException localIllegalStateException1)
    {
      paramOnVideoSavedCallback.onError(1, "Video encoder stop failed!", localIllegalStateException1);
      bool2 = true;
    }
    try
    {
      synchronized (this.mMuxerLock)
      {
        if (this.mMuxer != null)
        {
          if (this.mMuxerStarted) {
            this.mMuxer.stop();
          }
          this.mMuxer.release();
          this.mMuxer = null;
        }
      }
      return bool2;
    }
    catch (IllegalStateException localIllegalStateException2)
    {
      paramOnVideoSavedCallback.onError(2, "Muxer stop failed!", localIllegalStateException2);
      bool2 = true;
      this.mMuxerStarted = false;
      setupEncoder(paramString, paramSize);
      notifyReset();
      this.mEndOfAudioVideoSignal.set(true);
      Log.i("VideoCapture", "Video encode thread end.");
    }
  }
  
  public static final class Defaults
    implements ConfigProvider<VideoCaptureConfig>
  {
    private static final int DEFAULT_AUDIO_BIT_RATE = 64000;
    private static final int DEFAULT_AUDIO_CHANNEL_COUNT = 1;
    private static final int DEFAULT_AUDIO_MIN_BUFFER_SIZE = 1024;
    private static final int DEFAULT_AUDIO_RECORD_SOURCE = 1;
    private static final int DEFAULT_AUDIO_SAMPLE_RATE = 8000;
    private static final int DEFAULT_BIT_RATE = 8388608;
    private static final VideoCaptureConfig DEFAULT_CONFIG = new VideoCaptureConfig.Builder().setVideoFrameRate(30).setBitRate(8388608).setIFrameInterval(1).setAudioBitRate(64000).setAudioSampleRate(8000).setAudioChannelCount(1).setAudioRecordSource(1).setAudioMinBufferSize(1024).setMaxResolution(DEFAULT_MAX_RESOLUTION).setSurfaceOccupancyPriority(3).getUseCaseConfig();
    private static final int DEFAULT_INTRA_FRAME_INTERVAL = 1;
    private static final Size DEFAULT_MAX_RESOLUTION = new Size(1920, 1080);
    private static final int DEFAULT_SURFACE_OCCUPANCY_PRIORITY = 3;
    private static final int DEFAULT_VIDEO_FRAME_RATE = 30;
    
    public Defaults() {}
    
    public VideoCaptureConfig getConfig(CameraInfo paramCameraInfo)
    {
      return DEFAULT_CONFIG;
    }
  }
  
  public static final class Metadata
  {
    public Location location;
    
    public Metadata() {}
  }
  
  public static abstract interface OnVideoSavedCallback
  {
    public abstract void onError(int paramInt, String paramString, Throwable paramThrowable);
    
    public abstract void onVideoSaved(File paramFile);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface VideoCaptureError {}
  
  private final class VideoSavedListenerWrapper
    implements VideoCapture.OnVideoSavedCallback
  {
    Executor mExecutor;
    VideoCapture.OnVideoSavedCallback mOnVideoSavedCallback;
    
    VideoSavedListenerWrapper(Executor paramExecutor, VideoCapture.OnVideoSavedCallback paramOnVideoSavedCallback)
    {
      this.mExecutor = paramExecutor;
      this.mOnVideoSavedCallback = paramOnVideoSavedCallback;
    }
    
    public void onError(int paramInt, String paramString, Throwable paramThrowable)
    {
      try
      {
        Executor localExecutor = this.mExecutor;
        _..Lambda.VideoCapture.VideoSavedListenerWrapper.ZG5otqrkESy2VwQvd4RLRJQ1fFY localZG5otqrkESy2VwQvd4RLRJQ1fFY = new androidx/camera/core/_$$Lambda$VideoCapture$VideoSavedListenerWrapper$ZG5otqrkESy2VwQvd4RLRJQ1fFY;
        localZG5otqrkESy2VwQvd4RLRJQ1fFY.<init>(this, paramInt, paramString, paramThrowable);
        localExecutor.execute(localZG5otqrkESy2VwQvd4RLRJQ1fFY);
      }
      catch (RejectedExecutionException paramString)
      {
        Log.e("VideoCapture", "Unable to post to the supplied executor.");
      }
    }
    
    public void onVideoSaved(File paramFile)
    {
      try
      {
        Executor localExecutor = this.mExecutor;
        _..Lambda.VideoCapture.VideoSavedListenerWrapper.vLMoiAzzt8RX4_cghVgVbALA4Mc localVLMoiAzzt8RX4_cghVgVbALA4Mc = new androidx/camera/core/_$$Lambda$VideoCapture$VideoSavedListenerWrapper$vLMoiAzzt8RX4_cghVgVbALA4Mc;
        localVLMoiAzzt8RX4_cghVgVbALA4Mc.<init>(this, paramFile);
        localExecutor.execute(localVLMoiAzzt8RX4_cghVgVbALA4Mc);
      }
      catch (RejectedExecutionException paramFile)
      {
        Log.e("VideoCapture", "Unable to post to the supplied executor.");
      }
    }
  }
}

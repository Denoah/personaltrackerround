package androidx.camera.core;

import android.util.Size;
import android.view.Surface;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.DeferrableSurface.SurfaceUnavailableException;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.CallbackToFutureAdapter.Completer;
import androidx.core.util.Consumer;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

public final class SurfaceRequest
{
  private DeferrableSurface mInternalDeferrableSurface;
  private final CallbackToFutureAdapter.Completer<Void> mRequestCancellationCompleter;
  private final Size mResolution;
  private final ListenableFuture<Void> mSessionStatusFuture;
  private final CallbackToFutureAdapter.Completer<Surface> mSurfaceCompleter;
  final ListenableFuture<Surface> mSurfaceFuture;
  
  public SurfaceRequest(final Size paramSize)
  {
    this.mResolution = paramSize;
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("SurfaceRequest[size: ");
    ((StringBuilder)localObject1).append(paramSize);
    ((StringBuilder)localObject1).append(", id: ");
    ((StringBuilder)localObject1).append(hashCode());
    ((StringBuilder)localObject1).append("]");
    paramSize = ((StringBuilder)localObject1).toString();
    Object localObject2 = new AtomicReference(null);
    localObject1 = CallbackToFutureAdapter.getFuture(new _..Lambda.SurfaceRequest.odG17THPHlbCF8n40ySxsMVBMjU((AtomicReference)localObject2, paramSize));
    localObject2 = (CallbackToFutureAdapter.Completer)Preconditions.checkNotNull(((AtomicReference)localObject2).get());
    this.mRequestCancellationCompleter = ((CallbackToFutureAdapter.Completer)localObject2);
    AtomicReference localAtomicReference = new AtomicReference(null);
    ListenableFuture localListenableFuture = CallbackToFutureAdapter.getFuture(new _..Lambda.SurfaceRequest.sEXLWXS66apbUecgY06U3wMjup4(localAtomicReference, paramSize));
    this.mSessionStatusFuture = localListenableFuture;
    Futures.addCallback(localListenableFuture, new FutureCallback()
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        if ((paramAnonymousThrowable instanceof SurfaceRequest.RequestCancelledException)) {
          Preconditions.checkState(this.val$requestCancellationFuture.cancel(false));
        } else {
          Preconditions.checkState(this.val$requestCancellationCompleter.set(null));
        }
      }
      
      public void onSuccess(Void paramAnonymousVoid)
      {
        Preconditions.checkState(this.val$requestCancellationCompleter.set(null));
      }
    }, CameraXExecutors.directExecutor());
    localObject1 = (CallbackToFutureAdapter.Completer)Preconditions.checkNotNull(localAtomicReference.get());
    localObject2 = new AtomicReference(null);
    this.mSurfaceFuture = CallbackToFutureAdapter.getFuture(new _..Lambda.SurfaceRequest.izJhW7Kwab2vgiWRDUyBSJPuRwo((AtomicReference)localObject2, paramSize));
    this.mSurfaceCompleter = ((CallbackToFutureAdapter.Completer)Preconditions.checkNotNull(((AtomicReference)localObject2).get()));
    localObject2 = new DeferrableSurface()
    {
      protected ListenableFuture<Surface> provideSurface()
      {
        return SurfaceRequest.this.mSurfaceFuture;
      }
    };
    this.mInternalDeferrableSurface = ((DeferrableSurface)localObject2);
    localObject2 = ((DeferrableSurface)localObject2).getTerminationFuture();
    Futures.addCallback(this.mSurfaceFuture, new FutureCallback()
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        if ((paramAnonymousThrowable instanceof CancellationException))
        {
          CallbackToFutureAdapter.Completer localCompleter = this.val$sessionStatusCompleter;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append(paramSize);
          localStringBuilder.append(" cancelled.");
          Preconditions.checkState(localCompleter.setException(new SurfaceRequest.RequestCancelledException(localStringBuilder.toString(), paramAnonymousThrowable)));
        }
        else
        {
          this.val$sessionStatusCompleter.set(null);
        }
      }
      
      public void onSuccess(Surface paramAnonymousSurface)
      {
        Futures.propagate(this.val$terminationFuture, this.val$sessionStatusCompleter);
      }
    }, CameraXExecutors.directExecutor());
    ((ListenableFuture)localObject2).addListener(new _..Lambda.SurfaceRequest.ngtA0g5dJH3w7teLIgUUOnvVWD4(this), CameraXExecutors.directExecutor());
  }
  
  public void addRequestCancellationListener(Executor paramExecutor, Runnable paramRunnable)
  {
    this.mRequestCancellationCompleter.addCancellationListener(paramRunnable, paramExecutor);
  }
  
  public DeferrableSurface getDeferrableSurface()
  {
    return this.mInternalDeferrableSurface;
  }
  
  public Size getResolution()
  {
    return this.mResolution;
  }
  
  public void provideSurface(final Surface paramSurface, Executor paramExecutor, final Consumer<Result> paramConsumer)
  {
    if ((!this.mSurfaceCompleter.set(paramSurface)) && (!this.mSurfaceFuture.isCancelled()))
    {
      Preconditions.checkState(this.mSurfaceFuture.isDone());
      try
      {
        this.mSurfaceFuture.get();
        _..Lambda.SurfaceRequest.aC9bT1pfUxuJxiP2CHR2nby0fcw localAC9bT1pfUxuJxiP2CHR2nby0fcw = new androidx/camera/core/_$$Lambda$SurfaceRequest$aC9bT1pfUxuJxiP2CHR2nby0fcw;
        localAC9bT1pfUxuJxiP2CHR2nby0fcw.<init>(paramConsumer, paramSurface);
        paramExecutor.execute(localAC9bT1pfUxuJxiP2CHR2nby0fcw);
      }
      catch (InterruptedException|ExecutionException localInterruptedException)
      {
        paramExecutor.execute(new _..Lambda.SurfaceRequest.1B_FEIX2iizhnKAclUlsKNJ3zuM(paramConsumer, paramSurface));
      }
    }
    else
    {
      Futures.addCallback(this.mSessionStatusFuture, new FutureCallback()
      {
        public void onFailure(Throwable paramAnonymousThrowable)
        {
          boolean bool = paramAnonymousThrowable instanceof SurfaceRequest.RequestCancelledException;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Camera surface session should only fail with request cancellation. Instead failed due to:\n");
          localStringBuilder.append(paramAnonymousThrowable);
          Preconditions.checkState(bool, localStringBuilder.toString());
          paramConsumer.accept(SurfaceRequest.Result.of(1, paramSurface));
        }
        
        public void onSuccess(Void paramAnonymousVoid)
        {
          paramConsumer.accept(SurfaceRequest.Result.of(0, paramSurface));
        }
      }, paramExecutor);
    }
  }
  
  public boolean willNotProvideSurface()
  {
    return this.mSurfaceCompleter.setException(new DeferrableSurface.SurfaceUnavailableException("Surface request will not complete."));
  }
  
  private static final class RequestCancelledException
    extends RuntimeException
  {
    RequestCancelledException(String paramString, Throwable paramThrowable)
    {
      super(paramThrowable);
    }
  }
  
  public static abstract class Result
  {
    public static final int RESULT_INVALID_SURFACE = 2;
    public static final int RESULT_REQUEST_CANCELLED = 1;
    public static final int RESULT_SURFACE_ALREADY_PROVIDED = 3;
    public static final int RESULT_SURFACE_USED_SUCCESSFULLY = 0;
    public static final int RESULT_WILL_NOT_PROVIDE_SURFACE = 4;
    
    Result() {}
    
    static Result of(int paramInt, Surface paramSurface)
    {
      return new AutoValue_SurfaceRequest_Result(paramInt, paramSurface);
    }
    
    public abstract int getResultCode();
    
    public abstract Surface getSurface();
    
    @Retention(RetentionPolicy.SOURCE)
    public static @interface ResultCode {}
  }
}

package androidx.camera.core.impl;

import androidx.camera.core.UseCase;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.CallbackToFutureAdapter.Completer;
import androidx.concurrent.futures.CallbackToFutureAdapter.Resolver;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class CameraRepository
  implements UseCaseGroup.StateChangeCallback
{
  private static final String TAG = "CameraRepository";
  private final Map<String, CameraInternal> mCameras = new HashMap();
  private final Object mCamerasLock = new Object();
  private CallbackToFutureAdapter.Completer<Void> mDeinitCompleter;
  private ListenableFuture<Void> mDeinitFuture;
  private final Set<CameraInternal> mReleasingCameras = new HashSet();
  
  public CameraRepository() {}
  
  private void attachUseCasesToCamera(CameraInternal paramCameraInternal, Set<UseCase> paramSet)
  {
    paramCameraInternal.addOnlineUseCase(paramSet);
  }
  
  private void detachUseCasesFromCamera(CameraInternal paramCameraInternal, Set<UseCase> paramSet)
  {
    paramCameraInternal.removeOnlineUseCase(paramSet);
  }
  
  public ListenableFuture<Void> deinit()
  {
    synchronized (this.mCamerasLock)
    {
      if (this.mCameras.isEmpty())
      {
        if (this.mDeinitFuture == null) {
          localObject2 = Futures.immediateFuture(null);
        } else {
          localObject2 = this.mDeinitFuture;
        }
        return localObject2;
      }
      ListenableFuture localListenableFuture = this.mDeinitFuture;
      Object localObject2 = localListenableFuture;
      if (localListenableFuture == null)
      {
        localObject2 = new androidx/camera/core/impl/_$$Lambda$CameraRepository$GfCuwjwqCywAr4DgCn1JSOYgBEg;
        ((_..Lambda.CameraRepository.GfCuwjwqCywAr4DgCn1JSOYgBEg)localObject2).<init>(this);
        localObject2 = CallbackToFutureAdapter.getFuture((CallbackToFutureAdapter.Resolver)localObject2);
        this.mDeinitFuture = ((ListenableFuture)localObject2);
      }
      this.mReleasingCameras.addAll(this.mCameras.values());
      Iterator localIterator = this.mCameras.values().iterator();
      while (localIterator.hasNext())
      {
        CameraInternal localCameraInternal = (CameraInternal)localIterator.next();
        localListenableFuture = localCameraInternal.release();
        _..Lambda.CameraRepository.vZy2hmnvCLGH5kuV__iaqXOZ0ng localVZy2hmnvCLGH5kuV__iaqXOZ0ng = new androidx/camera/core/impl/_$$Lambda$CameraRepository$vZy2hmnvCLGH5kuV__iaqXOZ0ng;
        localVZy2hmnvCLGH5kuV__iaqXOZ0ng.<init>(this, localCameraInternal);
        localListenableFuture.addListener(localVZy2hmnvCLGH5kuV__iaqXOZ0ng, CameraXExecutors.directExecutor());
      }
      this.mCameras.clear();
      return localObject2;
    }
  }
  
  public CameraInternal getCamera(String paramString)
  {
    synchronized (this.mCamerasLock)
    {
      Object localObject2 = (CameraInternal)this.mCameras.get(paramString);
      if (localObject2 != null) {
        return localObject2;
      }
      localObject2 = new java/lang/IllegalArgumentException;
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("Invalid camera: ");
      localStringBuilder.append(paramString);
      ((IllegalArgumentException)localObject2).<init>(localStringBuilder.toString());
      throw ((Throwable)localObject2);
    }
  }
  
  Set<String> getCameraIds()
  {
    synchronized (this.mCamerasLock)
    {
      HashSet localHashSet = new java/util/HashSet;
      localHashSet.<init>(this.mCameras.keySet());
      return localHashSet;
    }
  }
  
  /* Error */
  public void init(CameraFactory paramCameraFactory)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 30	androidx/camera/core/impl/CameraRepository:mCamerasLock	Ljava/lang/Object;
    //   4: astore_2
    //   5: aload_2
    //   6: monitorenter
    //   7: aload_1
    //   8: invokeinterface 172 1 0
    //   13: invokeinterface 173 1 0
    //   18: astore_3
    //   19: aload_3
    //   20: invokeinterface 103 1 0
    //   25: ifeq +74 -> 99
    //   28: aload_3
    //   29: invokeinterface 107 1 0
    //   34: checkcast 175	java/lang/String
    //   37: astore 4
    //   39: new 141	java/lang/StringBuilder
    //   42: astore 5
    //   44: aload 5
    //   46: invokespecial 142	java/lang/StringBuilder:<init>	()V
    //   49: aload 5
    //   51: ldc -79
    //   53: invokevirtual 148	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   56: pop
    //   57: aload 5
    //   59: aload 4
    //   61: invokevirtual 148	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   64: pop
    //   65: ldc 10
    //   67: aload 5
    //   69: invokevirtual 152	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   72: invokestatic 183	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   75: pop
    //   76: aload_0
    //   77: getfield 35	androidx/camera/core/impl/CameraRepository:mCameras	Ljava/util/Map;
    //   80: aload 4
    //   82: aload_1
    //   83: aload 4
    //   85: invokeinterface 185 2 0
    //   90: invokeinterface 189 3 0
    //   95: pop
    //   96: goto -77 -> 19
    //   99: aload_2
    //   100: monitorexit
    //   101: return
    //   102: astore_1
    //   103: goto +17 -> 120
    //   106: astore_3
    //   107: new 191	java/lang/IllegalStateException
    //   110: astore_1
    //   111: aload_1
    //   112: ldc -63
    //   114: aload_3
    //   115: invokespecial 196	java/lang/IllegalStateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   118: aload_1
    //   119: athrow
    //   120: aload_2
    //   121: monitorexit
    //   122: aload_1
    //   123: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	124	0	this	CameraRepository
    //   0	124	1	paramCameraFactory	CameraFactory
    //   4	117	2	localObject	Object
    //   18	11	3	localIterator	Iterator
    //   106	9	3	localException	Exception
    //   37	47	4	str	String
    //   42	26	5	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   7	19	102	finally
    //   19	96	102	finally
    //   99	101	102	finally
    //   107	120	102	finally
    //   120	122	102	finally
    //   7	19	106	java/lang/Exception
    //   19	96	106	java/lang/Exception
  }
  
  public void onGroupActive(UseCaseGroup paramUseCaseGroup)
  {
    synchronized (this.mCamerasLock)
    {
      paramUseCaseGroup = paramUseCaseGroup.getCameraIdToUseCaseMap().entrySet().iterator();
      while (paramUseCaseGroup.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramUseCaseGroup.next();
        attachUseCasesToCamera(getCamera((String)localEntry.getKey()), (Set)localEntry.getValue());
      }
      return;
    }
  }
  
  public void onGroupInactive(UseCaseGroup paramUseCaseGroup)
  {
    synchronized (this.mCamerasLock)
    {
      Iterator localIterator = paramUseCaseGroup.getCameraIdToUseCaseMap().entrySet().iterator();
      while (localIterator.hasNext())
      {
        paramUseCaseGroup = (Map.Entry)localIterator.next();
        detachUseCasesFromCamera(getCamera((String)paramUseCaseGroup.getKey()), (Set)paramUseCaseGroup.getValue());
      }
      return;
    }
  }
}

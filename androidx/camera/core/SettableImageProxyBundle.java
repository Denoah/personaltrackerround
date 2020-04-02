package androidx.camera.core;

import android.util.SparseArray;
import androidx.camera.core.impl.ImageProxyBundle;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.CallbackToFutureAdapter.Completer;
import androidx.concurrent.futures.CallbackToFutureAdapter.Resolver;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

final class SettableImageProxyBundle
  implements ImageProxyBundle
{
  private final List<Integer> mCaptureIdList;
  private boolean mClosed = false;
  final SparseArray<CallbackToFutureAdapter.Completer<ImageProxy>> mCompleters = new SparseArray();
  private final SparseArray<ListenableFuture<ImageProxy>> mFutureResults = new SparseArray();
  final Object mLock = new Object();
  private final List<ImageProxy> mOwnedImageProxies = new ArrayList();
  
  SettableImageProxyBundle(List<Integer> paramList)
  {
    this.mCaptureIdList = paramList;
    setup();
  }
  
  private void setup()
  {
    synchronized (this.mLock)
    {
      Iterator localIterator = this.mCaptureIdList.iterator();
      while (localIterator.hasNext())
      {
        int i = ((Integer)localIterator.next()).intValue();
        Object localObject3 = new androidx/camera/core/SettableImageProxyBundle$1;
        ((1)localObject3).<init>(this, i);
        localObject3 = CallbackToFutureAdapter.getFuture((CallbackToFutureAdapter.Resolver)localObject3);
        this.mFutureResults.put(i, localObject3);
      }
      return;
    }
  }
  
  void addImageProxy(ImageProxy paramImageProxy)
  {
    synchronized (this.mLock)
    {
      if (this.mClosed) {
        return;
      }
      Integer localInteger = (Integer)paramImageProxy.getImageInfo().getTag();
      if (localInteger != null)
      {
        Object localObject2 = (CallbackToFutureAdapter.Completer)this.mCompleters.get(localInteger.intValue());
        if (localObject2 != null)
        {
          this.mOwnedImageProxies.add(paramImageProxy);
          ((CallbackToFutureAdapter.Completer)localObject2).set(paramImageProxy);
          return;
        }
        paramImageProxy = new java/lang/IllegalArgumentException;
        localObject2 = new java/lang/StringBuilder;
        ((StringBuilder)localObject2).<init>();
        ((StringBuilder)localObject2).append("ImageProxyBundle does not contain this id: ");
        ((StringBuilder)localObject2).append(localInteger);
        paramImageProxy.<init>(((StringBuilder)localObject2).toString());
        throw paramImageProxy;
      }
      paramImageProxy = new java/lang/IllegalArgumentException;
      paramImageProxy.<init>("CaptureId is null.");
      throw paramImageProxy;
    }
  }
  
  void close()
  {
    synchronized (this.mLock)
    {
      if (this.mClosed) {
        return;
      }
      Iterator localIterator = this.mOwnedImageProxies.iterator();
      while (localIterator.hasNext()) {
        ((ImageProxy)localIterator.next()).close();
      }
      this.mOwnedImageProxies.clear();
      this.mFutureResults.clear();
      this.mCompleters.clear();
      this.mClosed = true;
      return;
    }
  }
  
  public List<Integer> getCaptureIds()
  {
    return Collections.unmodifiableList(this.mCaptureIdList);
  }
  
  public ListenableFuture<ImageProxy> getImageProxy(int paramInt)
  {
    synchronized (this.mLock)
    {
      if (!this.mClosed)
      {
        localObject2 = (ListenableFuture)this.mFutureResults.get(paramInt);
        if (localObject2 != null) {
          return localObject2;
        }
        IllegalArgumentException localIllegalArgumentException = new java/lang/IllegalArgumentException;
        localObject2 = new java/lang/StringBuilder;
        ((StringBuilder)localObject2).<init>();
        ((StringBuilder)localObject2).append("ImageProxyBundle does not contain this id: ");
        ((StringBuilder)localObject2).append(paramInt);
        localIllegalArgumentException.<init>(((StringBuilder)localObject2).toString());
        throw localIllegalArgumentException;
      }
      Object localObject2 = new java/lang/IllegalStateException;
      ((IllegalStateException)localObject2).<init>("ImageProxyBundle already closed.");
      throw ((Throwable)localObject2);
    }
  }
  
  void reset()
  {
    synchronized (this.mLock)
    {
      if (this.mClosed) {
        return;
      }
      Iterator localIterator = this.mOwnedImageProxies.iterator();
      while (localIterator.hasNext()) {
        ((ImageProxy)localIterator.next()).close();
      }
      this.mOwnedImageProxies.clear();
      this.mFutureResults.clear();
      this.mCompleters.clear();
      setup();
      return;
    }
  }
}

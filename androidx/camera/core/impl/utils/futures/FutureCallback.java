package androidx.camera.core.impl.utils.futures;

public abstract interface FutureCallback<V>
{
  public abstract void onFailure(Throwable paramThrowable);
  
  public abstract void onSuccess(V paramV);
}

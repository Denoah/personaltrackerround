package io.fabric.sdk.android;

public abstract interface InitializationCallback<T>
{
  public static final InitializationCallback EMPTY = new Empty(null);
  
  public abstract void failure(Exception paramException);
  
  public abstract void success(T paramT);
  
  public static class Empty
    implements InitializationCallback<Object>
  {
    private Empty() {}
    
    public void failure(Exception paramException) {}
    
    public void success(Object paramObject) {}
  }
}

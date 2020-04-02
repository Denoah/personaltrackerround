package io.reactivex.internal.fuseable;

public abstract interface QueueFuseable<T>
  extends SimpleQueue<T>
{
  public static final int ANY = 3;
  public static final int ASYNC = 2;
  public static final int BOUNDARY = 4;
  public static final int NONE = 0;
  public static final int SYNC = 1;
  
  public abstract int requestFusion(int paramInt);
}

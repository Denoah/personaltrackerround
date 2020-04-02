package kotlin.reflect.jvm.internal.impl.storage;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

class NoLock
  implements Lock
{
  public static final Lock INSTANCE = new NoLock();
  
  private NoLock() {}
  
  public void lock() {}
  
  public void lockInterruptibly()
    throws InterruptedException
  {
    throw new UnsupportedOperationException("Should not be called");
  }
  
  public Condition newCondition()
  {
    throw new UnsupportedOperationException("Should not be called");
  }
  
  public boolean tryLock()
  {
    throw new UnsupportedOperationException("Should not be called");
  }
  
  public boolean tryLock(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException
  {
    if (paramTimeUnit == null) {
      $$$reportNull$$$0(0);
    }
    throw new UnsupportedOperationException("Should not be called");
  }
  
  public void unlock() {}
}

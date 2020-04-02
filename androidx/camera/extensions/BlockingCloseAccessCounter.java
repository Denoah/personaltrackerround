package androidx.camera.extensions;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

final class BlockingCloseAccessCounter
{
  private static final int COUNTER_DESTROYED_FLAG = -1;
  private AtomicInteger mAccessCount = new AtomicInteger(0);
  private final Condition mDoneCondition;
  private final Lock mLock;
  
  BlockingCloseAccessCounter()
  {
    ReentrantLock localReentrantLock = new ReentrantLock();
    this.mLock = localReentrantLock;
    this.mDoneCondition = localReentrantLock.newCondition();
  }
  
  void decrement()
  {
    this.mLock.lock();
    try
    {
      int i = this.mAccessCount.getAndDecrement();
      if (i != -1)
      {
        if (i != 0)
        {
          this.mDoneCondition.signal();
          return;
        }
        localIllegalStateException = new java/lang/IllegalStateException;
        localIllegalStateException.<init>("Unable to decrement. No corresponding counter increment");
        throw localIllegalStateException;
      }
      IllegalStateException localIllegalStateException = new java/lang/IllegalStateException;
      localIllegalStateException.<init>("Unable to decrement. Counter already destroyed");
      throw localIllegalStateException;
    }
    finally
    {
      this.mLock.unlock();
    }
  }
  
  void destroyAndWaitForZeroAccess()
  {
    this.mLock.lock();
    for (;;)
    {
      try
      {
        boolean bool = this.mAccessCount.compareAndSet(0, -1);
        if (bool) {}
      }
      finally
      {
        this.mLock.unlock();
      }
      try
      {
        this.mDoneCondition.await();
      }
      catch (InterruptedException localInterruptedException) {}
      this.mLock.unlock();
      return;
    }
  }
  
  boolean tryIncrement()
  {
    this.mLock.lock();
    try
    {
      int i = this.mAccessCount.get();
      if (i == -1) {
        return false;
      }
      this.mAccessCount.getAndIncrement();
      return true;
    }
    finally
    {
      this.mLock.unlock();
    }
  }
}

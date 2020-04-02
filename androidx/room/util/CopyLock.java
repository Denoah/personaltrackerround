package androidx.room.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CopyLock
{
  private static final Map<String, Lock> sThreadLocks = new HashMap();
  private final File mCopyLockFile;
  private final boolean mFileLevelLock;
  private FileChannel mLockChannel;
  private final Lock mThreadLock;
  
  public CopyLock(String paramString, File paramFile, boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append(".lck");
    paramString = new File(paramFile, localStringBuilder.toString());
    this.mCopyLockFile = paramString;
    this.mThreadLock = getThreadLock(paramString.getAbsolutePath());
    this.mFileLevelLock = paramBoolean;
  }
  
  private static Lock getThreadLock(String paramString)
  {
    synchronized (sThreadLocks)
    {
      Lock localLock = (Lock)sThreadLocks.get(paramString);
      Object localObject = localLock;
      if (localLock == null)
      {
        localObject = new java/util/concurrent/locks/ReentrantLock;
        ((ReentrantLock)localObject).<init>();
        sThreadLocks.put(paramString, localObject);
      }
      return localObject;
    }
  }
  
  public void lock()
  {
    this.mThreadLock.lock();
    if (this.mFileLevelLock) {
      try
      {
        Object localObject = new java/io/FileOutputStream;
        ((FileOutputStream)localObject).<init>(this.mCopyLockFile);
        localObject = ((FileOutputStream)localObject).getChannel();
        this.mLockChannel = ((FileChannel)localObject);
        ((FileChannel)localObject).lock();
      }
      catch (IOException localIOException)
      {
        throw new IllegalStateException("Unable to grab copy lock.", localIOException);
      }
    }
  }
  
  public void unlock()
  {
    FileChannel localFileChannel = this.mLockChannel;
    if (localFileChannel != null) {}
    try
    {
      localFileChannel.close();
      this.mThreadLock.unlock();
      return;
    }
    catch (IOException localIOException)
    {
      for (;;) {}
    }
  }
}

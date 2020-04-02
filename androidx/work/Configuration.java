package androidx.work;

import android.os.Build.VERSION;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class Configuration
{
  public static final int MIN_SCHEDULER_LIMIT = 20;
  final Executor mExecutor;
  final InputMergerFactory mInputMergerFactory;
  private final boolean mIsUsingDefaultTaskExecutor;
  final int mLoggingLevel;
  final int mMaxJobSchedulerId;
  final int mMaxSchedulerLimit;
  final int mMinJobSchedulerId;
  final Executor mTaskExecutor;
  final WorkerFactory mWorkerFactory;
  
  Configuration(Builder paramBuilder)
  {
    if (paramBuilder.mExecutor == null) {
      this.mExecutor = createDefaultExecutor();
    } else {
      this.mExecutor = paramBuilder.mExecutor;
    }
    if (paramBuilder.mTaskExecutor == null)
    {
      this.mIsUsingDefaultTaskExecutor = true;
      this.mTaskExecutor = createDefaultExecutor();
    }
    else
    {
      this.mIsUsingDefaultTaskExecutor = false;
      this.mTaskExecutor = paramBuilder.mTaskExecutor;
    }
    if (paramBuilder.mWorkerFactory == null) {
      this.mWorkerFactory = WorkerFactory.getDefaultWorkerFactory();
    } else {
      this.mWorkerFactory = paramBuilder.mWorkerFactory;
    }
    if (paramBuilder.mInputMergerFactory == null) {
      this.mInputMergerFactory = InputMergerFactory.getDefaultInputMergerFactory();
    } else {
      this.mInputMergerFactory = paramBuilder.mInputMergerFactory;
    }
    this.mLoggingLevel = paramBuilder.mLoggingLevel;
    this.mMinJobSchedulerId = paramBuilder.mMinJobSchedulerId;
    this.mMaxJobSchedulerId = paramBuilder.mMaxJobSchedulerId;
    this.mMaxSchedulerLimit = paramBuilder.mMaxSchedulerLimit;
  }
  
  private Executor createDefaultExecutor()
  {
    return Executors.newFixedThreadPool(Math.max(2, Math.min(Runtime.getRuntime().availableProcessors() - 1, 4)));
  }
  
  public Executor getExecutor()
  {
    return this.mExecutor;
  }
  
  public InputMergerFactory getInputMergerFactory()
  {
    return this.mInputMergerFactory;
  }
  
  public int getMaxJobSchedulerId()
  {
    return this.mMaxJobSchedulerId;
  }
  
  public int getMaxSchedulerLimit()
  {
    if (Build.VERSION.SDK_INT == 23) {
      return this.mMaxSchedulerLimit / 2;
    }
    return this.mMaxSchedulerLimit;
  }
  
  public int getMinJobSchedulerId()
  {
    return this.mMinJobSchedulerId;
  }
  
  public int getMinimumLoggingLevel()
  {
    return this.mLoggingLevel;
  }
  
  public Executor getTaskExecutor()
  {
    return this.mTaskExecutor;
  }
  
  public WorkerFactory getWorkerFactory()
  {
    return this.mWorkerFactory;
  }
  
  public boolean isUsingDefaultTaskExecutor()
  {
    return this.mIsUsingDefaultTaskExecutor;
  }
  
  public static final class Builder
  {
    Executor mExecutor;
    InputMergerFactory mInputMergerFactory;
    int mLoggingLevel;
    int mMaxJobSchedulerId;
    int mMaxSchedulerLimit;
    int mMinJobSchedulerId;
    Executor mTaskExecutor;
    WorkerFactory mWorkerFactory;
    
    public Builder()
    {
      this.mLoggingLevel = 4;
      this.mMinJobSchedulerId = 0;
      this.mMaxJobSchedulerId = Integer.MAX_VALUE;
      this.mMaxSchedulerLimit = 20;
    }
    
    public Builder(Configuration paramConfiguration)
    {
      this.mExecutor = paramConfiguration.mExecutor;
      this.mWorkerFactory = paramConfiguration.mWorkerFactory;
      this.mInputMergerFactory = paramConfiguration.mInputMergerFactory;
      this.mTaskExecutor = paramConfiguration.mTaskExecutor;
      this.mLoggingLevel = paramConfiguration.mLoggingLevel;
      this.mMinJobSchedulerId = paramConfiguration.mMinJobSchedulerId;
      this.mMaxJobSchedulerId = paramConfiguration.mMaxJobSchedulerId;
      this.mMaxSchedulerLimit = paramConfiguration.mMaxSchedulerLimit;
    }
    
    public Configuration build()
    {
      return new Configuration(this);
    }
    
    public Builder setExecutor(Executor paramExecutor)
    {
      this.mExecutor = paramExecutor;
      return this;
    }
    
    public Builder setInputMergerFactory(InputMergerFactory paramInputMergerFactory)
    {
      this.mInputMergerFactory = paramInputMergerFactory;
      return this;
    }
    
    public Builder setJobSchedulerJobIdRange(int paramInt1, int paramInt2)
    {
      if (paramInt2 - paramInt1 >= 1000)
      {
        this.mMinJobSchedulerId = paramInt1;
        this.mMaxJobSchedulerId = paramInt2;
        return this;
      }
      throw new IllegalArgumentException("WorkManager needs a range of at least 1000 job ids.");
    }
    
    public Builder setMaxSchedulerLimit(int paramInt)
    {
      if (paramInt >= 20)
      {
        this.mMaxSchedulerLimit = Math.min(paramInt, 50);
        return this;
      }
      throw new IllegalArgumentException("WorkManager needs to be able to schedule at least 20 jobs in JobScheduler.");
    }
    
    public Builder setMinimumLoggingLevel(int paramInt)
    {
      this.mLoggingLevel = paramInt;
      return this;
    }
    
    public Builder setTaskExecutor(Executor paramExecutor)
    {
      this.mTaskExecutor = paramExecutor;
      return this;
    }
    
    public Builder setWorkerFactory(WorkerFactory paramWorkerFactory)
    {
      this.mWorkerFactory = paramWorkerFactory;
      return this;
    }
  }
  
  public static abstract interface Provider
  {
    public abstract Configuration getWorkManagerConfiguration();
  }
}

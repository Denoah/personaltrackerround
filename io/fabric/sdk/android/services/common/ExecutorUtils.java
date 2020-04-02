package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.concurrency.internal.Backoff;
import io.fabric.sdk.android.services.concurrency.internal.RetryPolicy;
import io.fabric.sdk.android.services.concurrency.internal.RetryThreadPoolExecutor;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public final class ExecutorUtils
{
  private static final long DEFAULT_TERMINATION_TIMEOUT = 2L;
  
  private ExecutorUtils() {}
  
  private static final void addDelayedShutdownHook(String paramString, ExecutorService paramExecutorService)
  {
    addDelayedShutdownHook(paramString, paramExecutorService, 2L, TimeUnit.SECONDS);
  }
  
  public static final void addDelayedShutdownHook(String paramString, final ExecutorService paramExecutorService, final long paramLong, TimeUnit paramTimeUnit)
  {
    Runtime localRuntime = Runtime.getRuntime();
    paramExecutorService = new BackgroundPriorityRunnable()
    {
      public void onRun()
      {
        try
        {
          Logger localLogger = Fabric.getLogger();
          StringBuilder localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>();
          localStringBuilder.append("Executing shutdown hook for ");
          localStringBuilder.append(this.val$serviceName);
          localLogger.d("Fabric", localStringBuilder.toString());
          paramExecutorService.shutdown();
          if (!paramExecutorService.awaitTermination(paramLong, this.val$timeUnit))
          {
            localLogger = Fabric.getLogger();
            localStringBuilder = new java/lang/StringBuilder;
            localStringBuilder.<init>();
            localStringBuilder.append(this.val$serviceName);
            localStringBuilder.append(" did not shut down in the allocated time. Requesting immediate shutdown.");
            localLogger.d("Fabric", localStringBuilder.toString());
            paramExecutorService.shutdownNow();
          }
        }
        catch (InterruptedException localInterruptedException)
        {
          Fabric.getLogger().d("Fabric", String.format(Locale.US, "Interrupted while waiting for %s to shut down. Requesting immediate shutdown.", new Object[] { this.val$serviceName }));
          paramExecutorService.shutdownNow();
        }
      }
    };
    paramTimeUnit = new StringBuilder();
    paramTimeUnit.append("Crashlytics Shutdown Hook for ");
    paramTimeUnit.append(paramString);
    localRuntime.addShutdownHook(new Thread(paramExecutorService, paramTimeUnit.toString()));
  }
  
  public static RetryThreadPoolExecutor buildRetryThreadPoolExecutor(String paramString, int paramInt, RetryPolicy paramRetryPolicy, Backoff paramBackoff)
  {
    paramRetryPolicy = new RetryThreadPoolExecutor(paramInt, getNamedThreadFactory(paramString), paramRetryPolicy, paramBackoff);
    addDelayedShutdownHook(paramString, paramRetryPolicy);
    return paramRetryPolicy;
  }
  
  public static ExecutorService buildSingleThreadExecutorService(String paramString)
  {
    ExecutorService localExecutorService = Executors.newSingleThreadExecutor(getNamedThreadFactory(paramString));
    addDelayedShutdownHook(paramString, localExecutorService);
    return localExecutorService;
  }
  
  public static ScheduledExecutorService buildSingleThreadScheduledExecutorService(String paramString)
  {
    ScheduledExecutorService localScheduledExecutorService = Executors.newSingleThreadScheduledExecutor(getNamedThreadFactory(paramString));
    addDelayedShutdownHook(paramString, localScheduledExecutorService);
    return localScheduledExecutorService;
  }
  
  public static final ThreadFactory getNamedThreadFactory(String paramString)
  {
    new ThreadFactory()
    {
      public Thread newThread(final Runnable paramAnonymousRunnable)
      {
        Thread localThread = Executors.defaultThreadFactory().newThread(new BackgroundPriorityRunnable()
        {
          public void onRun()
          {
            paramAnonymousRunnable.run();
          }
        });
        paramAnonymousRunnable = new StringBuilder();
        paramAnonymousRunnable.append(this.val$threadNameTemplate);
        paramAnonymousRunnable.append(this.val$count.getAndIncrement());
        localThread.setName(paramAnonymousRunnable.toString());
        return localThread;
      }
    };
  }
}

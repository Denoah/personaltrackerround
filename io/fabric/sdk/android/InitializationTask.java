package io.fabric.sdk.android;

import io.fabric.sdk.android.services.common.TimingMetric;
import io.fabric.sdk.android.services.concurrency.Priority;
import io.fabric.sdk.android.services.concurrency.PriorityAsyncTask;

class InitializationTask<Result>
  extends PriorityAsyncTask<Void, Void, Result>
{
  private static final String TIMING_METRIC_TAG = "KitInitialization";
  final Kit<Result> kit;
  
  public InitializationTask(Kit<Result> paramKit)
  {
    this.kit = paramKit;
  }
  
  private TimingMetric createAndStartTimingMetric(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.kit.getIdentifier());
    localStringBuilder.append(".");
    localStringBuilder.append(paramString);
    paramString = new TimingMetric(localStringBuilder.toString(), "KitInitialization");
    paramString.startMeasuring();
    return paramString;
  }
  
  protected Result doInBackground(Void... paramVarArgs)
  {
    TimingMetric localTimingMetric = createAndStartTimingMetric("doInBackground");
    if (!isCancelled()) {
      paramVarArgs = this.kit.doInBackground();
    } else {
      paramVarArgs = null;
    }
    localTimingMetric.stopMeasuring();
    return paramVarArgs;
  }
  
  public Priority getPriority()
  {
    return Priority.HIGH;
  }
  
  protected void onCancelled(Result paramResult)
  {
    this.kit.onCancelled(paramResult);
    paramResult = new StringBuilder();
    paramResult.append(this.kit.getIdentifier());
    paramResult.append(" Initialization was cancelled");
    paramResult = new InitializationException(paramResult.toString());
    this.kit.initializationCallback.failure(paramResult);
  }
  
  protected void onPostExecute(Result paramResult)
  {
    this.kit.onPostExecute(paramResult);
    this.kit.initializationCallback.success(paramResult);
  }
  
  /* Error */
  protected void onPreExecute()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 114	io/fabric/sdk/android/services/concurrency/PriorityAsyncTask:onPreExecute	()V
    //   4: aload_0
    //   5: ldc 115
    //   7: invokespecial 60	io/fabric/sdk/android/InitializationTask:createAndStartTimingMetric	(Ljava/lang/String;)Lio/fabric/sdk/android/services/common/TimingMetric;
    //   10: astore_1
    //   11: aload_0
    //   12: getfield 19	io/fabric/sdk/android/InitializationTask:kit	Lio/fabric/sdk/android/Kit;
    //   15: invokevirtual 117	io/fabric/sdk/android/Kit:onPreExecute	()Z
    //   18: istore_2
    //   19: aload_1
    //   20: invokevirtual 70	io/fabric/sdk/android/services/common/TimingMetric:stopMeasuring	()V
    //   23: iload_2
    //   24: ifne +37 -> 61
    //   27: aload_0
    //   28: iconst_1
    //   29: invokevirtual 121	io/fabric/sdk/android/InitializationTask:cancel	(Z)Z
    //   32: pop
    //   33: goto +28 -> 61
    //   36: astore_3
    //   37: goto +28 -> 65
    //   40: astore_3
    //   41: invokestatic 127	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   44: ldc -127
    //   46: ldc -125
    //   48: aload_3
    //   49: invokeinterface 137 4 0
    //   54: aload_1
    //   55: invokevirtual 70	io/fabric/sdk/android/services/common/TimingMetric:stopMeasuring	()V
    //   58: goto -31 -> 27
    //   61: return
    //   62: astore_3
    //   63: aload_3
    //   64: athrow
    //   65: aload_1
    //   66: invokevirtual 70	io/fabric/sdk/android/services/common/TimingMetric:stopMeasuring	()V
    //   69: aload_0
    //   70: iconst_1
    //   71: invokevirtual 121	io/fabric/sdk/android/InitializationTask:cancel	(Z)Z
    //   74: pop
    //   75: aload_3
    //   76: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	77	0	this	InitializationTask
    //   10	56	1	localTimingMetric	TimingMetric
    //   18	6	2	bool	boolean
    //   36	1	3	localObject	Object
    //   40	9	3	localException	Exception
    //   62	14	3	localUnmetDependencyException	io.fabric.sdk.android.services.concurrency.UnmetDependencyException
    // Exception table:
    //   from	to	target	type
    //   11	19	36	finally
    //   41	54	36	finally
    //   63	65	36	finally
    //   11	19	40	java/lang/Exception
    //   11	19	62	io/fabric/sdk/android/services/concurrency/UnmetDependencyException
  }
}

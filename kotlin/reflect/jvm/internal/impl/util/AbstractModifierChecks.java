package kotlin.reflect.jvm.internal.impl.util;

import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;

public abstract class AbstractModifierChecks
{
  public AbstractModifierChecks() {}
  
  public final CheckResult check(FunctionDescriptor paramFunctionDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramFunctionDescriptor, "functionDescriptor");
    Iterator localIterator = getChecks$descriptors().iterator();
    while (localIterator.hasNext())
    {
      Checks localChecks = (Checks)localIterator.next();
      if (localChecks.isApplicable(paramFunctionDescriptor)) {
        return localChecks.checkAll(paramFunctionDescriptor);
      }
    }
    return (CheckResult)CheckResult.IllegalFunctionName.INSTANCE;
  }
  
  public abstract List<Checks> getChecks$descriptors();
}

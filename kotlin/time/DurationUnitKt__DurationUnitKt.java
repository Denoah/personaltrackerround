package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\020\n\000\n\002\020\016\n\002\030\002\n\002\030\002\n\000\032\020\020\000\032\0020\001*\0060\002j\002`\003H\001?\006\004"}, d2={"shortName", "", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/time/DurationUnitKt")
class DurationUnitKt__DurationUnitKt
  extends DurationUnitKt__DurationUnitJvmKt
{
  public DurationUnitKt__DurationUnitKt() {}
  
  public static final String shortName(TimeUnit paramTimeUnit)
  {
    Intrinsics.checkParameterIsNotNull(paramTimeUnit, "$this$shortName");
    switch (DurationUnitKt.WhenMappings.$EnumSwitchMapping$0[paramTimeUnit.ordinal()])
    {
    default: 
      throw new NoWhenBranchMatchedException();
    case 7: 
      paramTimeUnit = "d";
      break;
    case 6: 
      paramTimeUnit = "h";
      break;
    case 5: 
      paramTimeUnit = "m";
      break;
    case 4: 
      paramTimeUnit = "s";
      break;
    case 3: 
      paramTimeUnit = "ms";
      break;
    case 2: 
      paramTimeUnit = "us";
      break;
    case 1: 
      paramTimeUnit = "ns";
    }
    return paramTimeUnit;
  }
}

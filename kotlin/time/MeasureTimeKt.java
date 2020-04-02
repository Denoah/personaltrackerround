package kotlin.time;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\032,\020\000\032\0020\0012\f\020\002\032\b\022\004\022\0020\0040\003H?\b?\001\000?\002\n\n\b\b\001\022\002\020\001 \001?\006\002\020\005\0320\020\006\032\b\022\004\022\002H\b0\007\"\004\b\000\020\b2\f\020\002\032\b\022\004\022\002H\b0\003H?\b?\002\n\n\b\b\001\022\002\020\001 \001\0320\020\000\032\0020\001*\0020\t2\f\020\002\032\b\022\004\022\0020\0040\003H?\b?\001\000?\002\n\n\b\b\001\022\002\020\001 \001?\006\002\020\n\0324\020\006\032\b\022\004\022\002H\b0\007\"\004\b\000\020\b*\0020\t2\f\020\002\032\b\022\004\022\002H\b0\003H?\b?\002\n\n\b\b\001\022\002\020\001 \001?\002\004\n\002\b\031?\006\013"}, d2={"measureTime", "Lkotlin/time/Duration;", "block", "Lkotlin/Function0;", "", "(Lkotlin/jvm/functions/Function0;)D", "measureTimedValue", "Lkotlin/time/TimedValue;", "T", "Lkotlin/time/TimeSource;", "(Lkotlin/time/TimeSource;Lkotlin/jvm/functions/Function0;)D", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class MeasureTimeKt
{
  public static final double measureTime(Function0<Unit> paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction0, "block");
    TimeMark localTimeMark = ((TimeSource)TimeSource.Monotonic.INSTANCE).markNow();
    paramFunction0.invoke();
    return localTimeMark.elapsedNow();
  }
  
  public static final double measureTime(TimeSource paramTimeSource, Function0<Unit> paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramTimeSource, "$this$measureTime");
    Intrinsics.checkParameterIsNotNull(paramFunction0, "block");
    paramTimeSource = paramTimeSource.markNow();
    paramFunction0.invoke();
    return paramTimeSource.elapsedNow();
  }
  
  public static final <T> TimedValue<T> measureTimedValue(Function0<? extends T> paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction0, "block");
    TimeMark localTimeMark = ((TimeSource)TimeSource.Monotonic.INSTANCE).markNow();
    return new TimedValue(paramFunction0.invoke(), localTimeMark.elapsedNow(), null);
  }
  
  public static final <T> TimedValue<T> measureTimedValue(TimeSource paramTimeSource, Function0<? extends T> paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramTimeSource, "$this$measureTimedValue");
    Intrinsics.checkParameterIsNotNull(paramFunction0, "block");
    paramTimeSource = paramTimeSource.markNow();
    return new TimedValue(paramFunction0.invoke(), paramTimeSource.elapsedNow(), null);
  }
}

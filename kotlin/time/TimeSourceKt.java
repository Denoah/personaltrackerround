package kotlin.time;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0006\n\000\n\002\020\b\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\t\032\025\020\000\032\0020\001*\0020\0022\006\020\003\032\0020\002H?\n\032\035\020\004\032\0020\005*\0020\0022\006\020\003\032\0020\002H?\n?\001\000?\006\002\020\006*P\b\007\020\007\"\0020\b2\0020\bB\f\b\t\022\b\b\n\022\004\b\b(\013B\002\b\fB0\b\r\022\b\b\016\022\004\b\b(\017\022\"\b\020\022\036\b\013B\032\b\021\022\f\b\022\022\b\b\fJ\004\b\b(\023\022\b\b\024\022\004\b\b(\025*P\b\007\020\026\"\0020\0022\0020\002B\f\b\t\022\b\b\n\022\004\b\b(\013B\002\b\fB0\b\r\022\b\b\016\022\004\b\b(\027\022\"\b\020\022\036\b\013B\032\b\021\022\f\b\022\022\b\b\fJ\004\b\b(\030\022\b\b\024\022\004\b\b(\031?\002\004\n\002\b\031?\006\032"}, d2={"compareTo", "", "Lkotlin/time/TimeMark;", "other", "minus", "Lkotlin/time/Duration;", "(Lkotlin/time/TimeMark;Lkotlin/time/TimeMark;)D", "Clock", "Lkotlin/time/TimeSource;", "Lkotlin/SinceKotlin;", "version", "1.3", "Lkotlin/time/ExperimentalTime;", "Lkotlin/Deprecated;", "message", "Use TimeSource interface instead.", "replaceWith", "Lkotlin/ReplaceWith;", "imports", "kotlin.time.TimeSource", "expression", "TimeSource", "ClockMark", "Use TimeMark class instead.", "kotlin.time.TimeMark", "TimeMark", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class TimeSourceKt
{
  @Deprecated(level=DeprecationLevel.ERROR, message="Comparing one TimeMark to another is not a well defined operation because these time marks could have been obtained from the different time sources.")
  private static final int compareTo(TimeMark paramTimeMark1, TimeMark paramTimeMark2)
  {
    Intrinsics.checkParameterIsNotNull(paramTimeMark1, "$this$compareTo");
    throw ((Throwable)new Error("Operation is disallowed."));
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Subtracting one TimeMark from another is not a well defined operation because these time marks could have been obtained from the different time sources.")
  private static final double minus(TimeMark paramTimeMark1, TimeMark paramTimeMark2)
  {
    Intrinsics.checkParameterIsNotNull(paramTimeMark1, "$this$minus");
    throw ((Throwable)new Error("Operation is disallowed."));
  }
}

package kotlin.time;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\f\b\003\030\0002\0020\001B\030\022\006\020\002\032\0020\001\022\006\020\003\032\0020\004?\001\000?\006\002\020\005J\020\020\013\032\0020\004H\026?\001\000?\006\002\020\007J\033\020\f\032\0020\0012\006\020\r\032\0020\004H?\002?\001\000?\006\004\b\016\020\017R\026\020\003\032\0020\004?\001\000?\006\n\n\002\020\b\032\004\b\006\020\007R\021\020\002\032\0020\001?\006\b\n\000\032\004\b\t\020\n?\002\004\n\002\b\031?\006\020"}, d2={"Lkotlin/time/AdjustedTimeMark;", "Lkotlin/time/TimeMark;", "mark", "adjustment", "Lkotlin/time/Duration;", "(Lkotlin/time/TimeMark;DLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAdjustment", "()D", "D", "getMark", "()Lkotlin/time/TimeMark;", "elapsedNow", "plus", "duration", "plus-LRDsOJo", "(D)Lkotlin/time/TimeMark;", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
final class AdjustedTimeMark
  extends TimeMark
{
  private final double adjustment;
  private final TimeMark mark;
  
  private AdjustedTimeMark(TimeMark paramTimeMark, double paramDouble)
  {
    this.mark = paramTimeMark;
    this.adjustment = paramDouble;
  }
  
  public double elapsedNow()
  {
    return Duration.minus-LRDsOJo(this.mark.elapsedNow(), this.adjustment);
  }
  
  public final double getAdjustment()
  {
    return this.adjustment;
  }
  
  public final TimeMark getMark()
  {
    return this.mark;
  }
  
  public TimeMark plus-LRDsOJo(double paramDouble)
  {
    return (TimeMark)new AdjustedTimeMark(this.mark, Duration.plus-LRDsOJo(this.adjustment, paramDouble), null);
  }
}

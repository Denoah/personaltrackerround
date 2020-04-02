package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\020\006\n\002\b\002\b'\030\0002\0020\001:\001\fB\021\022\n\020\002\032\0060\003j\002`\004?\006\002\020\005J\b\020\b\032\0020\tH\026J\b\020\n\032\0020\013H$R\030\020\002\032\0060\003j\002`\004X?\004?\006\b\n\000\032\004\b\006\020\007?\002\004\n\002\b\031?\006\r"}, d2={"Lkotlin/time/AbstractDoubleTimeSource;", "Lkotlin/time/TimeSource;", "unit", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "(Ljava/util/concurrent/TimeUnit;)V", "getUnit", "()Ljava/util/concurrent/TimeUnit;", "markNow", "Lkotlin/time/TimeMark;", "read", "", "DoubleTimeMark", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public abstract class AbstractDoubleTimeSource
  implements TimeSource
{
  private final TimeUnit unit;
  
  public AbstractDoubleTimeSource(TimeUnit paramTimeUnit)
  {
    this.unit = paramTimeUnit;
  }
  
  protected final TimeUnit getUnit()
  {
    return this.unit;
  }
  
  public TimeMark markNow()
  {
    return (TimeMark)new DoubleTimeMark(read(), this, Duration.Companion.getZERO(), null);
  }
  
  protected abstract double read();
  
  @Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\030\002\n\000\n\002\020\006\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\t\b\002\030\0002\0020\001B \022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007?\001\000?\006\002\020\bJ\020\020\n\032\0020\007H\026?\001\000?\006\002\020\013J\033\020\f\032\0020\0012\006\020\r\032\0020\007H?\002?\001\000?\006\004\b\016\020\017R\023\020\006\032\0020\007X?\004?\001\000?\006\004\n\002\020\tR\016\020\002\032\0020\003X?\004?\006\002\n\000R\016\020\004\032\0020\005X?\004?\006\002\n\000?\002\004\n\002\b\031?\006\020"}, d2={"Lkotlin/time/AbstractDoubleTimeSource$DoubleTimeMark;", "Lkotlin/time/TimeMark;", "startedAt", "", "timeSource", "Lkotlin/time/AbstractDoubleTimeSource;", "offset", "Lkotlin/time/Duration;", "(DLkotlin/time/AbstractDoubleTimeSource;DLkotlin/jvm/internal/DefaultConstructorMarker;)V", "D", "elapsedNow", "()D", "plus", "duration", "plus-LRDsOJo", "(D)Lkotlin/time/TimeMark;", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
  private static final class DoubleTimeMark
    extends TimeMark
  {
    private final double offset;
    private final double startedAt;
    private final AbstractDoubleTimeSource timeSource;
    
    private DoubleTimeMark(double paramDouble1, AbstractDoubleTimeSource paramAbstractDoubleTimeSource, double paramDouble2)
    {
      this.startedAt = paramDouble1;
      this.timeSource = paramAbstractDoubleTimeSource;
      this.offset = paramDouble2;
    }
    
    public double elapsedNow()
    {
      return Duration.minus-LRDsOJo(DurationKt.toDuration(this.timeSource.read() - this.startedAt, this.timeSource.getUnit()), this.offset);
    }
    
    public TimeMark plus-LRDsOJo(double paramDouble)
    {
      return (TimeMark)new DoubleTimeMark(this.startedAt, this.timeSource, Duration.plus-LRDsOJo(this.offset, paramDouble), null);
    }
  }
}

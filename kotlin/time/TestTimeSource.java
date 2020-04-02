package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\t\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\006\b\007\030\0002\0020\001B\005?\006\002\020\002J\032\020\005\032\0020\0062\006\020\007\032\0020\bH\002?\001\000?\006\004\b\t\020\nJ\033\020\013\032\0020\0062\006\020\007\032\0020\bH?\002?\001\000?\006\004\b\f\020\nJ\b\020\r\032\0020\004H\024R\016\020\003\032\0020\004X?\016?\006\002\n\000?\002\004\n\002\b\031?\006\016"}, d2={"Lkotlin/time/TestTimeSource;", "Lkotlin/time/AbstractLongTimeSource;", "()V", "reading", "", "overflow", "", "duration", "Lkotlin/time/Duration;", "overflow-LRDsOJo", "(D)V", "plusAssign", "plusAssign-LRDsOJo", "read", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class TestTimeSource
  extends AbstractLongTimeSource
{
  private long reading;
  
  public TestTimeSource()
  {
    super(TimeUnit.NANOSECONDS);
  }
  
  private final void overflow-LRDsOJo(double paramDouble)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("TestTimeSource will overflow if its reading ");
    localStringBuilder.append(this.reading);
    localStringBuilder.append("ns is advanced by ");
    localStringBuilder.append(Duration.toString-impl(paramDouble));
    localStringBuilder.append('.');
    throw ((Throwable)new IllegalStateException(localStringBuilder.toString()));
  }
  
  public final void plusAssign-LRDsOJo(double paramDouble)
  {
    double d = Duration.toDouble-impl(paramDouble, getUnit());
    long l1 = d;
    long l4;
    if ((l1 != Long.MIN_VALUE) && (l1 != Long.MAX_VALUE))
    {
      long l2 = this.reading;
      long l3 = l2 + l1;
      l4 = l3;
      if ((l1 ^ l2) >= 0L)
      {
        l4 = l3;
        if ((l2 ^ l3) < 0L)
        {
          overflow-LRDsOJo(paramDouble);
          l4 = l3;
        }
      }
    }
    else
    {
      d = this.reading + d;
      if ((d > Long.MAX_VALUE) || (d < Long.MIN_VALUE)) {
        overflow-LRDsOJo(paramDouble);
      }
      l4 = d;
    }
    this.reading = l4;
  }
  
  protected long read()
  {
    return this.reading;
  }
}

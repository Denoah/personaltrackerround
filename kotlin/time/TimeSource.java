package kotlin.time;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\b\003\bg\030\000 \0042\0020\001:\002\004\005J\b\020\002\032\0020\003H&?\006\006"}, d2={"Lkotlin/time/TimeSource;", "", "markNow", "Lkotlin/time/TimeMark;", "Companion", "Monotonic", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public abstract interface TimeSource
{
  public static final Companion Companion = Companion.$$INSTANCE;
  
  public abstract TimeMark markNow();
  
  @Metadata(bv={1, 0, 3}, d1={"\000\f\n\002\030\002\n\002\020\000\n\002\b\002\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002?\006\003"}, d2={"Lkotlin/time/TimeSource$Companion;", "", "()V", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\016\n\000\b?\002\030\0002\0020\001B\007\b\002?\006\002\020\002J\t\020\003\032\0020\004H?\001J\b\020\005\032\0020\006H\026?\006\007"}, d2={"Lkotlin/time/TimeSource$Monotonic;", "Lkotlin/time/TimeSource;", "()V", "markNow", "Lkotlin/time/TimeMark;", "toString", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
  public static final class Monotonic
    implements TimeSource
  {
    public static final Monotonic INSTANCE = new Monotonic();
    
    private Monotonic() {}
    
    public TimeMark markNow()
    {
      return this.$$delegate_0.markNow();
    }
    
    public String toString()
    {
      return MonotonicTimeSource.INSTANCE.toString();
    }
  }
}

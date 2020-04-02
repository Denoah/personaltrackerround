package kotlinx.coroutines;

import java.util.concurrent.atomic.AtomicLong;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.internal.SystemPropsKt;

@Metadata(bv={1, 0, 3}, d1={"\000(\n\000\n\002\020\013\n\002\b\003\n\002\030\002\n\002\b\005\n\002\020\016\n\002\b\007\n\002\020\002\n\000\n\002\030\002\n\002\b\002\032\027\020\022\032\0020\0232\f\020\024\032\b\022\004\022\0020\0010\025H?\b\032\b\020\026\032\0020\023H\000\"\024\020\000\032\0020\001X?\004?\006\b\n\000\032\004\b\002\020\003\"\024\020\004\032\0020\005X?\004?\006\b\n\000\032\004\b\006\020\007\"\024\020\b\032\0020\001X?\004?\006\b\n\000\032\004\b\t\020\003\"\016\020\n\032\0020\013X?T?\006\002\n\000\"\016\020\f\032\0020\013X?T?\006\002\n\000\"\016\020\r\032\0020\013X?T?\006\002\n\000\"\016\020\016\032\0020\013X?T?\006\002\n\000\"\024\020\017\032\0020\001X?\004?\006\b\n\000\032\004\b\020\020\003\"\016\020\021\032\0020\013X?T?\006\002\n\000?\006\027"}, d2={"ASSERTIONS_ENABLED", "", "getASSERTIONS_ENABLED", "()Z", "COROUTINE_ID", "Ljava/util/concurrent/atomic/AtomicLong;", "getCOROUTINE_ID", "()Ljava/util/concurrent/atomic/AtomicLong;", "DEBUG", "getDEBUG", "DEBUG_PROPERTY_NAME", "", "DEBUG_PROPERTY_VALUE_AUTO", "DEBUG_PROPERTY_VALUE_OFF", "DEBUG_PROPERTY_VALUE_ON", "RECOVER_STACK_TRACES", "getRECOVER_STACK_TRACES", "STACKTRACE_RECOVERY_PROPERTY_NAME", "assert", "", "value", "Lkotlin/Function0;", "resetCoroutineId", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class DebugKt
{
  private static final boolean ASSERTIONS_ENABLED = CoroutineId.class.desiredAssertionStatus();
  private static final AtomicLong COROUTINE_ID;
  private static final boolean DEBUG;
  public static final String DEBUG_PROPERTY_NAME = "kotlinx.coroutines.debug";
  public static final String DEBUG_PROPERTY_VALUE_AUTO = "auto";
  public static final String DEBUG_PROPERTY_VALUE_OFF = "off";
  public static final String DEBUG_PROPERTY_VALUE_ON = "on";
  private static final boolean RECOVER_STACK_TRACES;
  public static final String STACKTRACE_RECOVERY_PROPERTY_NAME = "kotlinx.coroutines.stacktrace.recovery";
  
  static
  {
    String str = SystemPropsKt.systemProp("kotlinx.coroutines.debug");
    boolean bool1 = false;
    boolean bool2;
    if (str != null)
    {
      int i = str.hashCode();
      if (i == 0) {
        break label93;
      }
      if (i == 3551) {
        break label81;
      }
      if (i != 109935) {
        if ((i != 3005871) || (!str.equals("auto"))) {
          break label147;
        }
      }
    }
    else
    {
      bool2 = ASSERTIONS_ENABLED;
      break label104;
    }
    if (str.equals("off"))
    {
      bool2 = false;
      label81:
      if (str.equals("on")) {
        label93:
        if (str.equals(""))
        {
          bool2 = true;
          label104:
          DEBUG = bool2;
          boolean bool3 = bool1;
          if (bool2)
          {
            bool3 = bool1;
            if (SystemPropsKt.systemProp("kotlinx.coroutines.stacktrace.recovery", true)) {
              bool3 = true;
            }
          }
          RECOVER_STACK_TRACES = bool3;
          COROUTINE_ID = new AtomicLong(0L);
          return;
        }
      }
    }
    label147:
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("System property 'kotlinx.coroutines.debug' has unrecognized value '");
    localStringBuilder.append(str);
    localStringBuilder.append('\'');
    throw ((Throwable)new IllegalStateException(localStringBuilder.toString().toString()));
  }
  
  private static final void jdMethod_assert(Function0<Boolean> paramFunction0)
  {
    if ((getASSERTIONS_ENABLED()) && (!((Boolean)paramFunction0.invoke()).booleanValue())) {
      throw ((Throwable)new AssertionError());
    }
  }
  
  public static final boolean getASSERTIONS_ENABLED()
  {
    return ASSERTIONS_ENABLED;
  }
  
  public static final AtomicLong getCOROUTINE_ID()
  {
    return COROUTINE_ID;
  }
  
  public static final boolean getDEBUG()
  {
    return DEBUG;
  }
  
  public static final boolean getRECOVER_STACK_TRACES()
  {
    return RECOVER_STACK_TRACES;
  }
  
  public static final void resetCoroutineId()
  {
    COROUTINE_ID.set(0L);
  }
}

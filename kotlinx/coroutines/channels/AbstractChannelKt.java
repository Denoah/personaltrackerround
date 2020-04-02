package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlinx.coroutines.internal.Symbol;

@Metadata(bv={1, 0, 3}, d1={"\0000\n\000\n\002\020\000\n\002\b\013\n\002\020\b\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020\003\n\002\020\002\n\000\032#\020\020\032\b\022\004\022\002H\0220\021\"\004\b\000\020\022*\004\030\0010\001H?\b?\001\000?\006\002\020\023\032%\020\020\032\b\022\004\022\002H\0220\021\"\004\b\000\020\022*\006\022\002\b\0030\024H?\b?\001\000?\006\002\020\025\"\026\020\000\032\0020\0018\000X?\004?\006\b\n\000\022\004\b\002\020\003\"\026\020\004\032\0020\0018\000X?\004?\006\b\n\000\022\004\b\005\020\003\"\026\020\006\032\0020\0018\000X?\004?\006\b\n\000\022\004\b\007\020\003\"\026\020\b\032\0020\0018\000X?\004?\006\b\n\000\022\004\b\t\020\003\"\026\020\n\032\0020\0018\000X?\004?\006\b\n\000\022\004\b\013\020\003\"\016\020\f\032\0020\rX?T?\006\002\n\000\"\016\020\016\032\0020\rX?T?\006\002\n\000\"\016\020\017\032\0020\rX?T?\006\002\n\000*(\b\000\020\026\"\020\022\006\022\004\030\0010\030\022\004\022\0020\0310\0272\020\022\006\022\004\030\0010\030\022\004\022\0020\0310\027?\002\004\n\002\b\031?\006\032"}, d2={"ENQUEUE_FAILED", "", "ENQUEUE_FAILED$annotations", "()V", "HANDLER_INVOKED", "HANDLER_INVOKED$annotations", "OFFER_FAILED", "OFFER_FAILED$annotations", "OFFER_SUCCESS", "OFFER_SUCCESS$annotations", "POLL_FAILED", "POLL_FAILED$annotations", "RECEIVE_NULL_ON_CLOSE", "", "RECEIVE_RESULT", "RECEIVE_THROWS_ON_CLOSE", "toResult", "Lkotlinx/coroutines/channels/ValueOrClosed;", "E", "(Ljava/lang/Object;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/Closed;", "(Lkotlinx/coroutines/channels/Closed;)Ljava/lang/Object;", "Handler", "Lkotlin/Function1;", "", "", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class AbstractChannelKt
{
  public static final Object ENQUEUE_FAILED = new Symbol("ENQUEUE_FAILED");
  public static final Object HANDLER_INVOKED = new Symbol("ON_CLOSE_HANDLER_INVOKED");
  public static final Object OFFER_FAILED;
  public static final Object OFFER_SUCCESS = new Symbol("OFFER_SUCCESS");
  public static final Object POLL_FAILED;
  public static final int RECEIVE_NULL_ON_CLOSE = 1;
  public static final int RECEIVE_RESULT = 2;
  public static final int RECEIVE_THROWS_ON_CLOSE = 0;
  
  static
  {
    OFFER_FAILED = new Symbol("OFFER_FAILED");
    POLL_FAILED = new Symbol("POLL_FAILED");
  }
  
  private static final <E> Object toResult(Object paramObject)
  {
    ValueOrClosed.Companion localCompanion;
    if ((paramObject instanceof Closed))
    {
      localCompanion = ValueOrClosed.Companion;
      paramObject = ValueOrClosed.constructor-impl(new ValueOrClosed.Closed(((Closed)paramObject).closeCause));
    }
    else
    {
      localCompanion = ValueOrClosed.Companion;
      paramObject = ValueOrClosed.constructor-impl(paramObject);
    }
    return paramObject;
  }
  
  private static final <E> Object toResult(Closed<?> paramClosed)
  {
    ValueOrClosed.Companion localCompanion = ValueOrClosed.Companion;
    return ValueOrClosed.constructor-impl(new ValueOrClosed.Closed(paramClosed.closeCause));
  }
}

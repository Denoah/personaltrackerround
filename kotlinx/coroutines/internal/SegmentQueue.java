package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\030\002\n\002\b\004\n\002\020\t\n\002\b\005\n\002\020\002\n\002\b\f\n\002\020\000\b \030\000*\016\b\000\020\002*\b\022\004\022\0028\0000\0012\0020\031B\007?\006\004\b\003\020\004J!\020\b\032\004\030\0018\0002\006\020\005\032\0028\0002\006\020\007\032\0020\006H\004?\006\004\b\b\020\tJ!\020\n\032\004\030\0018\0002\006\020\005\032\0028\0002\006\020\007\032\0020\006H\004?\006\004\b\n\020\tJ\027\020\r\032\0020\f2\006\020\013\032\0028\000H\002?\006\004\b\r\020\016J\027\020\017\032\0020\f2\006\020\013\032\0028\000H\002?\006\004\b\017\020\016J#\020\021\032\0028\0002\006\020\007\032\0020\0062\n\b\002\020\020\032\004\030\0018\000H&?\006\004\b\021\020\022R\026\020\025\032\0028\0008D@\004X?\004?\006\006\032\004\b\023\020\024R\026\020\027\032\0028\0008D@\004X?\004?\006\006\032\004\b\026\020\024?\006\030"}, d2={"Lkotlinx/coroutines/internal/SegmentQueue;", "Lkotlinx/coroutines/internal/Segment;", "S", "<init>", "()V", "startFrom", "", "id", "getSegment", "(Lkotlinx/coroutines/internal/Segment;J)Lkotlinx/coroutines/internal/Segment;", "getSegmentAndMoveHead", "new", "", "moveHeadForward", "(Lkotlinx/coroutines/internal/Segment;)V", "moveTailForward", "prev", "newSegment", "(JLkotlinx/coroutines/internal/Segment;)Lkotlinx/coroutines/internal/Segment;", "getHead", "()Lkotlinx/coroutines/internal/Segment;", "head", "getTail", "tail", "kotlinx-coroutines-core", ""}, k=1, mv={1, 1, 16})
public abstract class SegmentQueue<S extends Segment<S>>
{
  private static final AtomicReferenceFieldUpdater _head$FU = AtomicReferenceFieldUpdater.newUpdater(SegmentQueue.class, Object.class, "_head");
  private static final AtomicReferenceFieldUpdater _tail$FU = AtomicReferenceFieldUpdater.newUpdater(SegmentQueue.class, Object.class, "_tail");
  private volatile Object _head;
  private volatile Object _tail;
  
  public SegmentQueue()
  {
    Segment localSegment = newSegment$default(this, 0L, null, 2, null);
    this._head = localSegment;
    this._tail = localSegment;
  }
  
  private final void moveHeadForward(S paramS)
  {
    Segment localSegment;
    do
    {
      localSegment = (Segment)this._head;
      if (localSegment.getId() > paramS.getId()) {
        return;
      }
    } while (!_head$FU.compareAndSet(this, localSegment, paramS));
    paramS.prev = null;
  }
  
  private final void moveTailForward(S paramS)
  {
    Segment localSegment;
    do
    {
      localSegment = (Segment)this._tail;
      if (localSegment.getId() > paramS.getId()) {
        return;
      }
    } while (!_tail$FU.compareAndSet(this, localSegment, paramS));
  }
  
  protected final S getHead()
  {
    return (Segment)this._head;
  }
  
  protected final S getSegment(S paramS, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramS, "startFrom");
    for (Object localObject = paramS; ((Segment)localObject).getId() < paramLong; localObject = paramS)
    {
      Segment localSegment = ((Segment)localObject).getNext();
      paramS = localSegment;
      if (localSegment == null)
      {
        paramS = newSegment(((Segment)localObject).getId() + 1L, (Segment)localObject);
        if (((Segment)localObject).casNext(null, paramS))
        {
          if (((Segment)localObject).getRemoved()) {
            ((Segment)localObject).remove();
          }
          moveTailForward(paramS);
        }
        else
        {
          localObject = ((Segment)localObject).getNext();
          paramS = (TS)localObject;
          if (localObject == null)
          {
            Intrinsics.throwNpe();
            paramS = (TS)localObject;
          }
        }
      }
    }
    if (((Segment)localObject).getId() != paramLong) {
      return null;
    }
    return localObject;
  }
  
  protected final S getSegmentAndMoveHead(S paramS, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramS, "startFrom");
    if (paramS.getId() == paramLong) {
      return paramS;
    }
    paramS = getSegment(paramS, paramLong);
    if (paramS != null)
    {
      moveHeadForward(paramS);
      return paramS;
    }
    return null;
  }
  
  protected final S getTail()
  {
    return (Segment)this._tail;
  }
  
  public abstract S newSegment(long paramLong, S paramS);
}

package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlinx.coroutines.DebugKt;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\000\n\002\020\t\n\002\b\006\n\002\020\013\n\002\b\003\n\002\020\002\n\002\b\016\n\002\020\000\b \030\000*\016\b\000\020\001*\b\022\004\022\0028\0000\0002\0020\034B\031\022\006\020\003\032\0020\002\022\b\020\004\032\004\030\0018\000?\006\004\b\005\020\006J!\020\n\032\0020\t2\b\020\007\032\004\030\0018\0002\b\020\b\032\004\030\0018\000?\006\004\b\n\020\013J\027\020\016\032\0020\r2\006\020\f\032\0028\000H\002?\006\004\b\016\020\017J\027\020\020\032\0020\r2\006\020\004\032\0028\000H\002?\006\004\b\020\020\017J\r\020\021\032\0020\r?\006\004\b\021\020\022R\031\020\003\032\0020\0028\006@\006?\006\f\n\004\b\003\020\023\032\004\b\024\020\025R\025\020\f\032\004\030\0018\0008F@\006?\006\006\032\004\b\026\020\027R\026\020\032\032\0020\t8&@&X¦\004?\006\006\032\004\b\030\020\031?\006\033"}, d2={"Lkotlinx/coroutines/internal/Segment;", "S", "", "id", "prev", "<init>", "(JLkotlinx/coroutines/internal/Segment;)V", "expected", "value", "", "casNext", "(Lkotlinx/coroutines/internal/Segment;Lkotlinx/coroutines/internal/Segment;)Z", "next", "", "moveNextToRight", "(Lkotlinx/coroutines/internal/Segment;)V", "movePrevToLeft", "remove", "()V", "J", "getId", "()J", "getNext", "()Lkotlinx/coroutines/internal/Segment;", "getRemoved", "()Z", "removed", "kotlinx-coroutines-core", ""}, k=1, mv={1, 1, 16})
public abstract class Segment<S extends Segment<S>>
{
  private static final AtomicReferenceFieldUpdater _next$FU = AtomicReferenceFieldUpdater.newUpdater(Segment.class, Object.class, "_next");
  static final AtomicReferenceFieldUpdater prev$FU = AtomicReferenceFieldUpdater.newUpdater(Segment.class, Object.class, "prev");
  private volatile Object _next;
  private final long id;
  volatile Object prev;
  
  public Segment(long paramLong, S paramS)
  {
    this.id = paramLong;
    this._next = null;
    this.prev = null;
    this.prev = paramS;
  }
  
  private final void moveNextToRight(S paramS)
  {
    Object localObject;
    do
    {
      localObject = this._next;
      if (localObject == null) {
        break;
      }
      localObject = (Segment)localObject;
      if (paramS.id <= ((Segment)localObject).id) {
        return;
      }
    } while (!_next$FU.compareAndSet(this, localObject, paramS));
    return;
    throw new TypeCastException("null cannot be cast to non-null type S");
  }
  
  private final void movePrevToLeft(S paramS)
  {
    Segment localSegment;
    do
    {
      localSegment = (Segment)this.prev;
      if (localSegment == null) {
        break;
      }
      if (localSegment.id <= paramS.id) {
        return;
      }
    } while (!prev$FU.compareAndSet(this, localSegment, paramS));
  }
  
  public final boolean casNext(S paramS1, S paramS2)
  {
    return _next$FU.compareAndSet(this, paramS1, paramS2);
  }
  
  public final long getId()
  {
    return this.id;
  }
  
  public final S getNext()
  {
    return (Segment)this._next;
  }
  
  public abstract boolean getRemoved();
  
  public final void remove()
  {
    if ((DebugKt.getASSERTIONS_ENABLED()) && (!getRemoved())) {
      throw ((Throwable)new AssertionError());
    }
    Segment localSegment1 = (Segment)this._next;
    if (localSegment1 != null)
    {
      Object localObject = (Segment)this.prev;
      if (localObject != null)
      {
        ((Segment)localObject).moveNextToRight(localSegment1);
        while (((Segment)localObject).getRemoved())
        {
          Segment localSegment2 = (Segment)((Segment)localObject).prev;
          if (localSegment2 == null) {
            break;
          }
          localSegment2.moveNextToRight(localSegment1);
          localObject = localSegment2;
        }
        localSegment1.movePrevToLeft((Segment)localObject);
        while (localSegment1.getRemoved())
        {
          localSegment1 = localSegment1.getNext();
          if (localSegment1 == null) {
            break;
          }
          localSegment1.movePrevToLeft((Segment)localObject);
        }
      }
    }
  }
}

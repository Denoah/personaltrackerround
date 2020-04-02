package kotlinx.coroutines.sync;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationKt;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuation.DefaultImpls;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.internal.AtomicDesc;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.AtomicOp;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.LockFreeLinkedListNode.CondAddOp;
import kotlinx.coroutines.internal.OpDescriptor;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;

@Metadata(bv={1, 0, 3}, d1={"\000B\n\002\030\002\n\002\020\013\n\002\b\003\n\002\020\000\n\002\b\003\n\002\020\002\n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\016\n\002\b\t\n\002\030\002\n\002\b\n\b\000\030\0002\0020\0212\020\022\006\022\004\030\0010\005\022\004\022\0020\0210 :\006$%&'()B\017\022\006\020\002\032\0020\001?\006\004\b\003\020\004J\027\020\007\032\0020\0012\006\020\006\032\0020\005H\026?\006\004\b\007\020\bJ\035\020\n\032\0020\t2\b\020\006\032\004\030\0010\005H?@?\001\000?\006\004\b\n\020\013J\035\020\f\032\0020\t2\b\020\006\032\004\030\0010\005H?@?\001\000?\006\004\b\f\020\013JT\020\024\032\0020\t\"\004\b\000\020\r2\f\020\017\032\b\022\004\022\0028\0000\0162\b\020\006\032\004\030\0010\0052\"\020\023\032\036\b\001\022\004\022\0020\021\022\n\022\b\022\004\022\0028\0000\022\022\006\022\004\030\0010\0050\020H\026?\001\000?\006\004\b\024\020\025J\017\020\027\032\0020\026H\026?\006\004\b\027\020\030J\031\020\031\032\0020\0012\b\020\006\032\004\030\0010\005H\026?\006\004\b\031\020\bJ\031\020\032\032\0020\t2\b\020\006\032\004\030\0010\005H\026?\006\004\b\032\020\033R\026\020\034\032\0020\0018V@\026X?\004?\006\006\032\004\b\034\020\035R\026\020\037\032\0020\0018@@\000X?\004?\006\006\032\004\b\036\020\035R$\020#\032\020\022\006\022\004\030\0010\005\022\004\022\0020\0210 8V@\026X?\004?\006\006\032\004\b!\020\"?\002\004\n\002\b\031?\006*"}, d2={"Lkotlinx/coroutines/sync/MutexImpl;", "", "locked", "<init>", "(Z)V", "", "owner", "holdsLock", "(Ljava/lang/Object;)Z", "", "lock", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "lockSuspend", "R", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "Lkotlin/Function2;", "Lkotlinx/coroutines/sync/Mutex;", "Lkotlin/coroutines/Continuation;", "block", "registerSelectClause2", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "", "toString", "()Ljava/lang/String;", "tryLock", "unlock", "(Ljava/lang/Object;)V", "isLocked", "()Z", "isLockedEmptyQueueState$kotlinx_coroutines_core", "isLockedEmptyQueueState", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnLock", "()Lkotlinx/coroutines/selects/SelectClause2;", "onLock", "LockCont", "LockSelect", "LockWaiter", "LockedQueue", "TryLockDesc", "UnlockOp", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class MutexImpl
  implements Mutex, SelectClause2<Object, Mutex>
{
  static final AtomicReferenceFieldUpdater _state$FU = AtomicReferenceFieldUpdater.newUpdater(MutexImpl.class, Object.class, "_state");
  volatile Object _state;
  
  public MutexImpl(boolean paramBoolean)
  {
    Empty localEmpty;
    if (paramBoolean) {
      localEmpty = MutexKt.access$getEMPTY_LOCKED$p();
    } else {
      localEmpty = MutexKt.access$getEMPTY_UNLOCKED$p();
    }
    this._state = localEmpty;
  }
  
  public SelectClause2<Object, Mutex> getOnLock()
  {
    return (SelectClause2)this;
  }
  
  public boolean holdsLock(Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramObject, "owner");
    Object localObject = this._state;
    boolean bool1 = localObject instanceof Empty;
    boolean bool2 = true;
    if (bool1 ? ((Empty)localObject).locked == paramObject : (!(localObject instanceof LockedQueue)) || (((LockedQueue)localObject).owner != paramObject)) {
      bool2 = false;
    }
    return bool2;
  }
  
  public boolean isLocked()
  {
    Object localObject;
    for (;;)
    {
      localObject = this._state;
      boolean bool1 = localObject instanceof Empty;
      boolean bool2 = true;
      if (bool1)
      {
        if (((Empty)localObject).locked == MutexKt.access$getUNLOCKED$p()) {
          bool2 = false;
        }
        return bool2;
      }
      if ((localObject instanceof LockedQueue)) {
        return true;
      }
      if (!(localObject instanceof OpDescriptor)) {
        break;
      }
      ((OpDescriptor)localObject).perform(this);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Illegal state ");
    localStringBuilder.append(localObject);
    throw ((Throwable)new IllegalStateException(localStringBuilder.toString().toString()));
  }
  
  public final boolean isLockedEmptyQueueState$kotlinx_coroutines_core()
  {
    Object localObject = this._state;
    boolean bool;
    if (((localObject instanceof LockedQueue)) && (((LockedQueue)localObject).isEmpty())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public Object lock(Object paramObject, Continuation<? super Unit> paramContinuation)
  {
    if (tryLock(paramObject)) {
      return Unit.INSTANCE;
    }
    paramObject = lockSuspend(paramObject, paramContinuation);
    if (paramObject == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramObject;
    }
    return Unit.INSTANCE;
  }
  
  public <R> void registerSelectClause2(SelectInstance<? super R> paramSelectInstance, Object paramObject, Function2<? super Mutex, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramSelectInstance, "select");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "block");
    final Object localObject1;
    for (;;)
    {
      if (paramSelectInstance.isSelected()) {
        return;
      }
      localObject1 = this._state;
      Object localObject2;
      if ((localObject1 instanceof Empty))
      {
        localObject2 = (Empty)localObject1;
        if (((Empty)localObject2).locked != MutexKt.access$getUNLOCKED$p())
        {
          _state$FU.compareAndSet(this, localObject1, new LockedQueue(((Empty)localObject2).locked));
        }
        else
        {
          localObject2 = paramSelectInstance.performAtomicTrySelect((AtomicDesc)new TryLockDesc(this, paramObject));
          if (localObject2 == null)
          {
            UndispatchedKt.startCoroutineUnintercepted(paramFunction2, this, paramSelectInstance.getCompletion());
            return;
          }
          if (localObject2 == SelectKt.getALREADY_SELECTED()) {
            return;
          }
          if ((localObject2 != MutexKt.access$getLOCK_FAIL$p()) && (localObject2 != AtomicKt.RETRY_ATOMIC))
          {
            paramSelectInstance = new StringBuilder();
            paramSelectInstance.append("performAtomicTrySelect(TryLockDesc) returned ");
            paramSelectInstance.append(localObject2);
            throw ((Throwable)new IllegalStateException(paramSelectInstance.toString().toString()));
          }
        }
      }
      else if ((localObject1 instanceof LockedQueue))
      {
        localObject2 = (LockedQueue)localObject1;
        Object localObject3 = ((LockedQueue)localObject2).owner;
        int i = 0;
        int j;
        if (localObject3 != paramObject) {
          j = 1;
        } else {
          j = 0;
        }
        if (j != 0)
        {
          LockSelect localLockSelect = new LockSelect(paramObject, (Mutex)this, paramSelectInstance, paramFunction2);
          localObject3 = (LockFreeLinkedListNode)localLockSelect;
          LockFreeLinkedListNode.CondAddOp localCondAddOp = (LockFreeLinkedListNode.CondAddOp)new LockFreeLinkedListNode.CondAddOp((LockFreeLinkedListNode)localObject3)
          {
            public Object prepare(LockFreeLinkedListNode paramAnonymousLockFreeLinkedListNode)
            {
              Intrinsics.checkParameterIsNotNull(paramAnonymousLockFreeLinkedListNode, "affected");
              int i;
              if (jdField_this._state == localObject1) {
                i = 1;
              } else {
                i = 0;
              }
              if (i != 0) {
                paramAnonymousLockFreeLinkedListNode = null;
              } else {
                paramAnonymousLockFreeLinkedListNode = LockFreeLinkedListKt.getCONDITION_FALSE();
              }
              return paramAnonymousLockFreeLinkedListNode;
            }
          };
          for (;;)
          {
            localObject1 = ((LockFreeLinkedListNode)localObject2).getPrev();
            if (localObject1 == null) {
              break label342;
            }
            int k = ((LockFreeLinkedListNode)localObject1).tryCondAddNext((LockFreeLinkedListNode)localObject3, (LockFreeLinkedListNode)localObject2, localCondAddOp);
            if (k == 1) {
              break;
            }
            j = i;
            if (k == 2) {
              break label325;
            }
          }
          j = 1;
          label325:
          if (j != 0)
          {
            paramSelectInstance.disposeOnSelect((DisposableHandle)localLockSelect);
            return;
            label342:
            throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
          }
        }
        else
        {
          paramSelectInstance = new StringBuilder();
          paramSelectInstance.append("Already locked by ");
          paramSelectInstance.append(paramObject);
          throw ((Throwable)new IllegalStateException(paramSelectInstance.toString().toString()));
        }
      }
      else
      {
        if (!(localObject1 instanceof OpDescriptor)) {
          break;
        }
        ((OpDescriptor)localObject1).perform(this);
      }
    }
    paramSelectInstance = new StringBuilder();
    paramSelectInstance.append("Illegal state ");
    paramSelectInstance.append(localObject1);
    throw ((Throwable)new IllegalStateException(paramSelectInstance.toString().toString()));
  }
  
  public String toString()
  {
    Object localObject;
    for (;;)
    {
      localObject = this._state;
      if ((localObject instanceof Empty))
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("Mutex[");
        localStringBuilder.append(((Empty)localObject).locked);
        localStringBuilder.append(']');
        return localStringBuilder.toString();
      }
      if (!(localObject instanceof OpDescriptor)) {
        break;
      }
      ((OpDescriptor)localObject).perform(this);
    }
    if ((localObject instanceof LockedQueue))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Mutex[");
      localStringBuilder.append(((LockedQueue)localObject).owner);
      localStringBuilder.append(']');
      return localStringBuilder.toString();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Illegal state ");
    localStringBuilder.append(localObject);
    throw ((Throwable)new IllegalStateException(localStringBuilder.toString().toString()));
  }
  
  public boolean tryLock(Object paramObject)
  {
    Object localObject1;
    for (;;)
    {
      localObject1 = this._state;
      boolean bool = localObject1 instanceof Empty;
      int i = 1;
      Object localObject2;
      if (bool)
      {
        if (((Empty)localObject1).locked != MutexKt.access$getUNLOCKED$p()) {
          return false;
        }
        if (paramObject == null) {
          localObject2 = MutexKt.access$getEMPTY_LOCKED$p();
        } else {
          localObject2 = new Empty(paramObject);
        }
        if (_state$FU.compareAndSet(this, localObject1, localObject2)) {
          return true;
        }
      }
      else
      {
        if ((localObject1 instanceof LockedQueue))
        {
          if (((LockedQueue)localObject1).owner == paramObject) {
            i = 0;
          }
          if (i != 0) {
            return false;
          }
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("Already locked by ");
          ((StringBuilder)localObject2).append(paramObject);
          throw ((Throwable)new IllegalStateException(((StringBuilder)localObject2).toString().toString()));
        }
        if (!(localObject1 instanceof OpDescriptor)) {
          break;
        }
        ((OpDescriptor)localObject1).perform(this);
      }
    }
    paramObject = new StringBuilder();
    paramObject.append("Illegal state ");
    paramObject.append(localObject1);
    throw ((Throwable)new IllegalStateException(paramObject.toString().toString()));
  }
  
  public void unlock(Object paramObject)
  {
    Object localObject1;
    Object localObject2;
    label116:
    Object localObject3;
    do
    {
      do
      {
        int j;
        int k;
        for (;;)
        {
          localObject1 = this._state;
          boolean bool = localObject1 instanceof Empty;
          int i = 1;
          j = 1;
          k = 1;
          if (bool)
          {
            if (paramObject == null)
            {
              if (((Empty)localObject1).locked == MutexKt.access$getUNLOCKED$p()) {
                k = 0;
              }
              if (k == 0) {
                throw ((Throwable)new IllegalStateException("Mutex is not locked".toString()));
              }
            }
            else
            {
              localObject2 = (Empty)localObject1;
              if (((Empty)localObject2).locked == paramObject) {
                k = i;
              } else {
                k = 0;
              }
              if (k == 0) {
                break label116;
              }
            }
            if (_state$FU.compareAndSet(this, localObject1, MutexKt.access$getEMPTY_UNLOCKED$p()))
            {
              return;
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("Mutex is locked by ");
              ((StringBuilder)localObject1).append(((Empty)localObject2).locked);
              ((StringBuilder)localObject1).append(" but expected ");
              ((StringBuilder)localObject1).append(paramObject);
              throw ((Throwable)new IllegalStateException(((StringBuilder)localObject1).toString().toString()));
            }
          }
          else
          {
            if (!(localObject1 instanceof OpDescriptor)) {
              break;
            }
            ((OpDescriptor)localObject1).perform(this);
          }
        }
        if (!(localObject1 instanceof LockedQueue)) {
          break label393;
        }
        if (paramObject != null)
        {
          localObject2 = (LockedQueue)localObject1;
          if (((LockedQueue)localObject2).owner == paramObject) {
            k = j;
          } else {
            k = 0;
          }
          if (k == 0)
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("Mutex is locked by ");
            ((StringBuilder)localObject1).append(((LockedQueue)localObject2).owner);
            ((StringBuilder)localObject1).append(" but expected ");
            ((StringBuilder)localObject1).append(paramObject);
            throw ((Throwable)new IllegalStateException(((StringBuilder)localObject1).toString().toString()));
          }
        }
        localObject2 = (LockedQueue)localObject1;
        localObject3 = ((LockedQueue)localObject2).removeFirstOrNull();
        if (localObject3 != null) {
          break;
        }
        localObject2 = new UnlockOp((LockedQueue)localObject2);
      } while ((!_state$FU.compareAndSet(this, localObject1, localObject2)) || (((UnlockOp)localObject2).perform(this) != null));
      return;
      localObject1 = (LockWaiter)localObject3;
      localObject3 = ((LockWaiter)localObject1).tryResumeLockWaiter();
    } while (localObject3 == null);
    paramObject = ((LockWaiter)localObject1).owner;
    if (paramObject == null) {
      paramObject = MutexKt.access$getLOCKED$p();
    }
    ((LockedQueue)localObject2).owner = paramObject;
    ((LockWaiter)localObject1).completeResumeLockWaiter(localObject3);
    return;
    label393:
    paramObject = new StringBuilder();
    paramObject.append("Illegal state ");
    paramObject.append(localObject1);
    throw ((Throwable)new IllegalStateException(paramObject.toString().toString()));
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\030\002\n\000\n\002\020\000\n\000\n\002\030\002\n\002\020\002\n\002\b\004\n\002\020\016\n\002\b\002\b\002\030\0002\0020\001B\035\022\b\020\002\032\004\030\0010\003\022\f\020\004\032\b\022\004\022\0020\0060\005?\006\002\020\007J\020\020\b\032\0020\0062\006\020\t\032\0020\003H\026J\b\020\n\032\0020\013H\026J\n\020\f\032\004\030\0010\003H\026R\026\020\004\032\b\022\004\022\0020\0060\0058\006X?\004?\006\002\n\000?\006\r"}, d2={"Lkotlinx/coroutines/sync/MutexImpl$LockCont;", "Lkotlinx/coroutines/sync/MutexImpl$LockWaiter;", "owner", "", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;)V", "completeResumeLockWaiter", "token", "toString", "", "tryResumeLockWaiter", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  private static final class LockCont
    extends MutexImpl.LockWaiter
  {
    public final CancellableContinuation<Unit> cont;
    
    public LockCont(Object paramObject, CancellableContinuation<? super Unit> paramCancellableContinuation)
    {
      super();
      this.cont = paramCancellableContinuation;
    }
    
    public void completeResumeLockWaiter(Object paramObject)
    {
      Intrinsics.checkParameterIsNotNull(paramObject, "token");
      this.cont.completeResume(paramObject);
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("LockCont[");
      localStringBuilder.append(this.owner);
      localStringBuilder.append(", ");
      localStringBuilder.append(this.cont);
      localStringBuilder.append(']');
      return localStringBuilder.toString();
    }
    
    public Object tryResumeLockWaiter()
    {
      return CancellableContinuation.DefaultImpls.tryResume$default(this.cont, Unit.INSTANCE, null, 2, null);
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000:\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\000\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\002\n\002\020\016\n\002\b\002\b\002\030\000*\004\b\000\020\0012\0020\002BL\022\b\020\003\032\004\030\0010\004\022\006\020\005\032\0020\006\022\f\020\007\032\b\022\004\022\0028\0000\b\022\"\020\t\032\036\b\001\022\004\022\0020\006\022\n\022\b\022\004\022\0028\0000\013\022\006\022\004\030\0010\0040\n?\001\000?\006\002\020\fJ\020\020\016\032\0020\0172\006\020\020\032\0020\004H\026J\b\020\021\032\0020\022H\026J\n\020\023\032\004\030\0010\004H\026R1\020\t\032\036\b\001\022\004\022\0020\006\022\n\022\b\022\004\022\0028\0000\013\022\006\022\004\030\0010\0040\n8\006X?\004?\001\000?\006\004\n\002\020\rR\020\020\005\032\0020\0068\006X?\004?\006\002\n\000R\026\020\007\032\b\022\004\022\0028\0000\b8\006X?\004?\006\002\n\000?\002\004\n\002\b\031?\006\024"}, d2={"Lkotlinx/coroutines/sync/MutexImpl$LockSelect;", "R", "Lkotlinx/coroutines/sync/MutexImpl$LockWaiter;", "owner", "", "mutex", "Lkotlinx/coroutines/sync/Mutex;", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "(Ljava/lang/Object;Lkotlinx/coroutines/sync/Mutex;Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "completeResumeLockWaiter", "", "token", "toString", "", "tryResumeLockWaiter", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  private static final class LockSelect<R>
    extends MutexImpl.LockWaiter
  {
    public final Function2<Mutex, Continuation<? super R>, Object> block;
    public final Mutex mutex;
    public final SelectInstance<R> select;
    
    public LockSelect(Object paramObject, Mutex paramMutex, SelectInstance<? super R> paramSelectInstance, Function2<? super Mutex, ? super Continuation<? super R>, ? extends Object> paramFunction2)
    {
      super();
      this.mutex = paramMutex;
      this.select = paramSelectInstance;
      this.block = paramFunction2;
    }
    
    public void completeResumeLockWaiter(Object paramObject)
    {
      Intrinsics.checkParameterIsNotNull(paramObject, "token");
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        int i;
        if (paramObject == MutexKt.access$getSELECT_SUCCESS$p()) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0) {
          throw ((Throwable)new AssertionError());
        }
      }
      ContinuationKt.startCoroutine(this.block, this.mutex, this.select.getCompletion());
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("LockSelect[");
      localStringBuilder.append(this.owner);
      localStringBuilder.append(", ");
      localStringBuilder.append(this.mutex);
      localStringBuilder.append(", ");
      localStringBuilder.append(this.select);
      localStringBuilder.append(']');
      return localStringBuilder.toString();
    }
    
    public Object tryResumeLockWaiter()
    {
      Symbol localSymbol;
      if (this.select.trySelect()) {
        localSymbol = MutexKt.access$getSELECT_SUCCESS$p();
      } else {
        localSymbol = null;
      }
      return localSymbol;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\000\n\002\b\002\n\002\020\002\n\002\b\004\b\"\030\0002\0020\0012\0020\002B\017\022\b\020\003\032\004\030\0010\004?\006\002\020\005J\020\020\006\032\0020\0072\006\020\b\032\0020\004H&J\006\020\t\032\0020\007J\n\020\n\032\004\030\0010\004H&R\022\020\003\032\004\030\0010\0048\006X?\004?\006\002\n\000?\006\013"}, d2={"Lkotlinx/coroutines/sync/MutexImpl$LockWaiter;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/DisposableHandle;", "owner", "", "(Ljava/lang/Object;)V", "completeResumeLockWaiter", "", "token", "dispose", "tryResumeLockWaiter", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  private static abstract class LockWaiter
    extends LockFreeLinkedListNode
    implements DisposableHandle
  {
    public final Object owner;
    
    public LockWaiter(Object paramObject)
    {
      this.owner = paramObject;
    }
    
    public abstract void completeResumeLockWaiter(Object paramObject);
    
    public final void dispose()
    {
      remove();
    }
    
    public abstract Object tryResumeLockWaiter();
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\030\002\n\000\n\002\020\000\n\002\b\002\n\002\020\016\n\000\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\b\020\005\032\0020\006H\026R\022\020\002\032\0020\0038\006@\006X?\016?\006\002\n\000?\006\007"}, d2={"Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "owner", "", "(Ljava/lang/Object;)V", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  private static final class LockedQueue
    extends LockFreeLinkedListHead
  {
    public Object owner;
    
    public LockedQueue(Object paramObject)
    {
      this.owner = paramObject;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("LockedQueue[");
      localStringBuilder.append(this.owner);
      localStringBuilder.append(']');
      return localStringBuilder.toString();
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\000\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\b\004\b\002\030\0002\0020\001:\001\rB\027\022\006\020\002\032\0020\003\022\b\020\004\032\004\030\0010\005?\006\002\020\006J\036\020\007\032\0020\b2\n\020\t\032\006\022\002\b\0030\n2\b\020\013\032\004\030\0010\005H\026J\026\020\f\032\004\030\0010\0052\n\020\t\032\006\022\002\b\0030\nH\026R\020\020\002\032\0020\0038\006X?\004?\006\002\n\000R\022\020\004\032\004\030\0010\0058\006X?\004?\006\002\n\000?\006\016"}, d2={"Lkotlinx/coroutines/sync/MutexImpl$TryLockDesc;", "Lkotlinx/coroutines/internal/AtomicDesc;", "mutex", "Lkotlinx/coroutines/sync/MutexImpl;", "owner", "", "(Lkotlinx/coroutines/sync/MutexImpl;Ljava/lang/Object;)V", "complete", "", "op", "Lkotlinx/coroutines/internal/AtomicOp;", "failure", "prepare", "PrepareOp", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  private static final class TryLockDesc
    extends AtomicDesc
  {
    public final MutexImpl mutex;
    public final Object owner;
    
    public TryLockDesc(MutexImpl paramMutexImpl, Object paramObject)
    {
      this.mutex = paramMutexImpl;
      this.owner = paramObject;
    }
    
    public void complete(AtomicOp<?> paramAtomicOp, Object paramObject)
    {
      Intrinsics.checkParameterIsNotNull(paramAtomicOp, "op");
      if (paramObject != null)
      {
        paramObject = MutexKt.access$getEMPTY_UNLOCKED$p();
      }
      else
      {
        paramObject = this.owner;
        if (paramObject == null) {
          paramObject = MutexKt.access$getEMPTY_LOCKED$p();
        } else {
          paramObject = new Empty(paramObject);
        }
      }
      MutexImpl localMutexImpl = this.mutex;
      MutexImpl._state$FU.compareAndSet(localMutexImpl, paramAtomicOp, paramObject);
    }
    
    public Object prepare(AtomicOp<?> paramAtomicOp)
    {
      Intrinsics.checkParameterIsNotNull(paramAtomicOp, "op");
      PrepareOp localPrepareOp = new PrepareOp(paramAtomicOp);
      paramAtomicOp = this.mutex;
      if (!MutexImpl._state$FU.compareAndSet(paramAtomicOp, MutexKt.access$getEMPTY_UNLOCKED$p(), localPrepareOp)) {
        return MutexKt.access$getLOCK_FAIL$p();
      }
      return localPrepareOp.perform(this.mutex);
    }
    
    @Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\020\000\n\002\b\002\b?\004\030\0002\0020\001B\021\022\n\020\002\032\006\022\002\b\0030\003?\006\002\020\004J\024\020\007\032\004\030\0010\b2\b\020\t\032\004\030\0010\bH\026R\030\020\002\032\006\022\002\b\0030\003X?\004?\006\b\n\000\032\004\b\005\020\006?\006\n"}, d2={"Lkotlinx/coroutines/sync/MutexImpl$TryLockDesc$PrepareOp;", "Lkotlinx/coroutines/internal/OpDescriptor;", "atomicOp", "Lkotlinx/coroutines/internal/AtomicOp;", "(Lkotlinx/coroutines/sync/MutexImpl$TryLockDesc;Lkotlinx/coroutines/internal/AtomicOp;)V", "getAtomicOp", "()Lkotlinx/coroutines/internal/AtomicOp;", "perform", "", "affected", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
    private final class PrepareOp
      extends OpDescriptor
    {
      private final AtomicOp<?> atomicOp;
      
      public PrepareOp()
      {
        this.atomicOp = localObject;
      }
      
      public AtomicOp<?> getAtomicOp()
      {
        return this.atomicOp;
      }
      
      public Object perform(Object paramObject)
      {
        Object localObject;
        if (getAtomicOp().isDecided()) {
          localObject = MutexKt.access$getEMPTY_UNLOCKED$p();
        } else {
          localObject = getAtomicOp();
        }
        if (paramObject != null)
        {
          paramObject = (MutexImpl)paramObject;
          MutexImpl._state$FU.compareAndSet(paramObject, this, localObject);
          return null;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.sync.MutexImpl");
      }
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\000\n\002\b\002\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\024\020\t\032\004\030\0010\n2\b\020\013\032\004\030\0010\nH\026R\032\020\005\032\b\022\002\b\003\030\0010\0068VX?\004?\006\006\032\004\b\007\020\bR\020\020\002\032\0020\0038\006X?\004?\006\002\n\000?\002\004\n\002\b\031?\006\f"}, d2={"Lkotlinx/coroutines/sync/MutexImpl$UnlockOp;", "Lkotlinx/coroutines/internal/OpDescriptor;", "queue", "Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;", "(Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;)V", "atomicOp", "Lkotlinx/coroutines/internal/AtomicOp;", "getAtomicOp", "()Lkotlinx/coroutines/internal/AtomicOp;", "perform", "", "affected", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  private static final class UnlockOp
    extends OpDescriptor
  {
    public final MutexImpl.LockedQueue queue;
    
    public UnlockOp(MutexImpl.LockedQueue paramLockedQueue)
    {
      this.queue = paramLockedQueue;
    }
    
    public AtomicOp<?> getAtomicOp()
    {
      return null;
    }
    
    public Object perform(Object paramObject)
    {
      Object localObject;
      if (this.queue.isEmpty()) {
        localObject = MutexKt.access$getEMPTY_UNLOCKED$p();
      } else {
        localObject = this.queue;
      }
      if (paramObject != null)
      {
        paramObject = (MutexImpl)paramObject;
        MutexImpl._state$FU.compareAndSet(paramObject, this, localObject);
        if (paramObject._state == this.queue) {
          paramObject = MutexKt.access$getUNLOCK_FAIL$p();
        } else {
          paramObject = null;
        }
        return paramObject;
      }
      throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.sync.MutexImpl");
    }
  }
}

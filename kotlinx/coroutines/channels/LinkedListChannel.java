package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.AtomicDesc;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\005\n\002\020\000\n\002\b\004\n\002\030\002\n\002\b\002\b\020\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B\005?\006\002\020\003J\025\020\n\032\0020\0132\006\020\f\032\0028\000H\024?\006\002\020\rJ!\020\016\032\0020\0132\006\020\f\032\0028\0002\n\020\017\032\006\022\002\b\0030\020H\024?\006\002\020\021R\024\020\004\032\0020\0058DX?\004?\006\006\032\004\b\004\020\006R\024\020\007\032\0020\0058DX?\004?\006\006\032\004\b\007\020\006R\024\020\b\032\0020\0058DX?\004?\006\006\032\004\b\b\020\006R\024\020\t\032\0020\0058DX?\004?\006\006\032\004\b\t\020\006?\006\022"}, d2={"Lkotlinx/coroutines/channels/LinkedListChannel;", "E", "Lkotlinx/coroutines/channels/AbstractChannel;", "()V", "isBufferAlwaysEmpty", "", "()Z", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "offerInternal", "", "element", "(Ljava/lang/Object;)Ljava/lang/Object;", "offerSelectInternal", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public class LinkedListChannel<E>
  extends AbstractChannel<E>
{
  public LinkedListChannel() {}
  
  protected final boolean isBufferAlwaysEmpty()
  {
    return true;
  }
  
  protected final boolean isBufferAlwaysFull()
  {
    return false;
  }
  
  protected final boolean isBufferEmpty()
  {
    return true;
  }
  
  protected final boolean isBufferFull()
  {
    return false;
  }
  
  protected Object offerInternal(E paramE)
  {
    Object localObject;
    do
    {
      localObject = super.offerInternal(paramE);
      if (localObject == AbstractChannelKt.OFFER_SUCCESS) {
        return AbstractChannelKt.OFFER_SUCCESS;
      }
      if (localObject != AbstractChannelKt.OFFER_FAILED) {
        break;
      }
      localObject = sendBuffered(paramE);
      if (localObject == null) {
        return AbstractChannelKt.OFFER_SUCCESS;
      }
    } while (!(localObject instanceof Closed));
    return localObject;
    if ((localObject instanceof Closed)) {
      return localObject;
    }
    paramE = new StringBuilder();
    paramE.append("Invalid offerInternal result ");
    paramE.append(localObject);
    throw ((Throwable)new IllegalStateException(paramE.toString().toString()));
  }
  
  protected Object offerSelectInternal(E paramE, SelectInstance<?> paramSelectInstance)
  {
    Intrinsics.checkParameterIsNotNull(paramSelectInstance, "select");
    Object localObject;
    do
    {
      if (getHasReceiveOrClosed())
      {
        localObject = super.offerSelectInternal(paramE, paramSelectInstance);
      }
      else
      {
        localObject = paramSelectInstance.performAtomicTrySelect((AtomicDesc)describeSendBuffered(paramE));
        if (localObject == null) {
          localObject = AbstractChannelKt.OFFER_SUCCESS;
        }
      }
      if (localObject == SelectKt.getALREADY_SELECTED()) {
        return SelectKt.getALREADY_SELECTED();
      }
      if (localObject == AbstractChannelKt.OFFER_SUCCESS) {
        return AbstractChannelKt.OFFER_SUCCESS;
      }
    } while ((localObject == AbstractChannelKt.OFFER_FAILED) || (localObject == AtomicKt.RETRY_ATOMIC));
    if ((localObject instanceof Closed)) {
      return localObject;
    }
    paramE = new StringBuilder();
    paramE.append("Invalid result ");
    paramE.append(localObject);
    throw ((Throwable)new IllegalStateException(paramE.toString().toString()));
  }
}

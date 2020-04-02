package io.reactivex.internal.queue;

import io.reactivex.internal.fuseable.SimplePlainQueue;
import java.util.concurrent.atomic.AtomicReference;

public final class MpscLinkedQueue<T>
  implements SimplePlainQueue<T>
{
  private final AtomicReference<LinkedQueueNode<T>> consumerNode = new AtomicReference();
  private final AtomicReference<LinkedQueueNode<T>> producerNode = new AtomicReference();
  
  public MpscLinkedQueue()
  {
    LinkedQueueNode localLinkedQueueNode = new LinkedQueueNode();
    spConsumerNode(localLinkedQueueNode);
    xchgProducerNode(localLinkedQueueNode);
  }
  
  public void clear()
  {
    while ((poll() != null) && (!isEmpty())) {}
  }
  
  public boolean isEmpty()
  {
    boolean bool;
    if (lvConsumerNode() == lvProducerNode()) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  LinkedQueueNode<T> lpConsumerNode()
  {
    return (LinkedQueueNode)this.consumerNode.get();
  }
  
  LinkedQueueNode<T> lvConsumerNode()
  {
    return (LinkedQueueNode)this.consumerNode.get();
  }
  
  LinkedQueueNode<T> lvProducerNode()
  {
    return (LinkedQueueNode)this.producerNode.get();
  }
  
  public boolean offer(T paramT)
  {
    if (paramT != null)
    {
      paramT = new LinkedQueueNode(paramT);
      xchgProducerNode(paramT).soNext(paramT);
      return true;
    }
    throw new NullPointerException("Null is not a valid element");
  }
  
  public boolean offer(T paramT1, T paramT2)
  {
    offer(paramT1);
    offer(paramT2);
    return true;
  }
  
  public T poll()
  {
    Object localObject = lpConsumerNode();
    LinkedQueueNode localLinkedQueueNode = ((LinkedQueueNode)localObject).lvNext();
    if (localLinkedQueueNode != null)
    {
      localObject = localLinkedQueueNode.getAndNullValue();
      spConsumerNode(localLinkedQueueNode);
      return localObject;
    }
    if (localObject != lvProducerNode())
    {
      do
      {
        localLinkedQueueNode = ((LinkedQueueNode)localObject).lvNext();
      } while (localLinkedQueueNode == null);
      localObject = localLinkedQueueNode.getAndNullValue();
      spConsumerNode(localLinkedQueueNode);
      return localObject;
    }
    return null;
  }
  
  void spConsumerNode(LinkedQueueNode<T> paramLinkedQueueNode)
  {
    this.consumerNode.lazySet(paramLinkedQueueNode);
  }
  
  LinkedQueueNode<T> xchgProducerNode(LinkedQueueNode<T> paramLinkedQueueNode)
  {
    return (LinkedQueueNode)this.producerNode.getAndSet(paramLinkedQueueNode);
  }
  
  static final class LinkedQueueNode<E>
    extends AtomicReference<LinkedQueueNode<E>>
  {
    private static final long serialVersionUID = 2404266111789071508L;
    private E value;
    
    LinkedQueueNode() {}
    
    LinkedQueueNode(E paramE)
    {
      spValue(paramE);
    }
    
    public E getAndNullValue()
    {
      Object localObject = lpValue();
      spValue(null);
      return localObject;
    }
    
    public E lpValue()
    {
      return this.value;
    }
    
    public LinkedQueueNode<E> lvNext()
    {
      return (LinkedQueueNode)get();
    }
    
    public void soNext(LinkedQueueNode<E> paramLinkedQueueNode)
    {
      lazySet(paramLinkedQueueNode);
    }
    
    public void spValue(E paramE)
    {
      this.value = paramE;
    }
  }
}

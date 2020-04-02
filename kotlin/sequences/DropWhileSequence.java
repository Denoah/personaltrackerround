package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020\013\n\002\b\002\n\002\020(\n\000\b\000\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B'\022\f\020\003\032\b\022\004\022\0028\0000\002\022\022\020\004\032\016\022\004\022\0028\000\022\004\022\0020\0060\005?\006\002\020\007J\017\020\b\032\b\022\004\022\0028\0000\tH?\002R\032\020\004\032\016\022\004\022\0028\000\022\004\022\0020\0060\005X?\004?\006\002\n\000R\024\020\003\032\b\022\004\022\0028\0000\002X?\004?\006\002\n\000?\006\n"}, d2={"Lkotlin/sequences/DropWhileSequence;", "T", "Lkotlin/sequences/Sequence;", "sequence", "predicate", "Lkotlin/Function1;", "", "(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function1;)V", "iterator", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class DropWhileSequence<T>
  implements Sequence<T>
{
  private final Function1<T, Boolean> predicate;
  private final Sequence<T> sequence;
  
  public DropWhileSequence(Sequence<? extends T> paramSequence, Function1<? super T, Boolean> paramFunction1)
  {
    this.sequence = paramSequence;
    this.predicate = paramFunction1;
  }
  
  public Iterator<T> iterator()
  {
    (Iterator)new Iterator()
    {
      private int dropState;
      private final Iterator<T> iterator;
      private T nextItem;
      
      private final void drop()
      {
        while (this.iterator.hasNext())
        {
          Object localObject = this.iterator.next();
          if (!((Boolean)DropWhileSequence.access$getPredicate$p(this.this$0).invoke(localObject)).booleanValue())
          {
            this.nextItem = localObject;
            this.dropState = 1;
            return;
          }
        }
        this.dropState = 0;
      }
      
      public final int getDropState()
      {
        return this.dropState;
      }
      
      public final Iterator<T> getIterator()
      {
        return this.iterator;
      }
      
      public final T getNextItem()
      {
        return this.nextItem;
      }
      
      public boolean hasNext()
      {
        if (this.dropState == -1) {
          drop();
        }
        int i = this.dropState;
        boolean bool1 = true;
        boolean bool2 = bool1;
        if (i != 1) {
          if (this.iterator.hasNext()) {
            bool2 = bool1;
          } else {
            bool2 = false;
          }
        }
        return bool2;
      }
      
      public T next()
      {
        if (this.dropState == -1) {
          drop();
        }
        if (this.dropState == 1)
        {
          Object localObject = this.nextItem;
          this.nextItem = null;
          this.dropState = 0;
          return localObject;
        }
        return this.iterator.next();
      }
      
      public void remove()
      {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }
      
      public final void setDropState(int paramAnonymousInt)
      {
        this.dropState = paramAnonymousInt;
      }
      
      public final void setNextItem(T paramAnonymousT)
      {
        this.nextItem = paramAnonymousT;
      }
    };
  }
}

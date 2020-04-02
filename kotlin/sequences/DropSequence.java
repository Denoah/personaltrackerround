package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\004\n\002\020(\n\002\b\002\b\000\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003B\033\022\f\020\004\032\b\022\004\022\0028\0000\002\022\006\020\005\032\0020\006?\006\002\020\007J\026\020\b\032\b\022\004\022\0028\0000\0022\006\020\t\032\0020\006H\026J\017\020\n\032\b\022\004\022\0028\0000\013H?\002J\026\020\f\032\b\022\004\022\0028\0000\0022\006\020\t\032\0020\006H\026R\016\020\005\032\0020\006X?\004?\006\002\n\000R\024\020\004\032\b\022\004\022\0028\0000\002X?\004?\006\002\n\000?\006\r"}, d2={"Lkotlin/sequences/DropSequence;", "T", "Lkotlin/sequences/Sequence;", "Lkotlin/sequences/DropTakeSequence;", "sequence", "count", "", "(Lkotlin/sequences/Sequence;I)V", "drop", "n", "iterator", "", "take", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class DropSequence<T>
  implements Sequence<T>, DropTakeSequence<T>
{
  private final int count;
  private final Sequence<T> sequence;
  
  public DropSequence(Sequence<? extends T> paramSequence, int paramInt)
  {
    this.sequence = paramSequence;
    this.count = paramInt;
    if (paramInt >= 0) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    if (paramInt != 0) {
      return;
    }
    paramSequence = new StringBuilder();
    paramSequence.append("count must be non-negative, but was ");
    paramSequence.append(this.count);
    paramSequence.append('.');
    throw ((Throwable)new IllegalArgumentException(paramSequence.toString().toString()));
  }
  
  public Sequence<T> drop(int paramInt)
  {
    int i = this.count + paramInt;
    Sequence localSequence;
    if (i < 0) {
      localSequence = (Sequence)new DropSequence((Sequence)this, paramInt);
    } else {
      localSequence = (Sequence)new DropSequence(this.sequence, i);
    }
    return localSequence;
  }
  
  public Iterator<T> iterator()
  {
    (Iterator)new Iterator()
    {
      private final Iterator<T> iterator;
      private int left;
      
      private final void drop()
      {
        while ((this.left > 0) && (this.iterator.hasNext()))
        {
          this.iterator.next();
          this.left -= 1;
        }
      }
      
      public final Iterator<T> getIterator()
      {
        return this.iterator;
      }
      
      public final int getLeft()
      {
        return this.left;
      }
      
      public boolean hasNext()
      {
        drop();
        return this.iterator.hasNext();
      }
      
      public T next()
      {
        drop();
        return this.iterator.next();
      }
      
      public void remove()
      {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }
      
      public final void setLeft(int paramAnonymousInt)
      {
        this.left = paramAnonymousInt;
      }
    };
  }
  
  public Sequence<T> take(int paramInt)
  {
    int i = this.count;
    int j = i + paramInt;
    Sequence localSequence;
    if (j < 0) {
      localSequence = (Sequence)new TakeSequence((Sequence)this, paramInt);
    } else {
      localSequence = (Sequence)new SubSequence(this.sequence, i, j);
    }
    return localSequence;
  }
}

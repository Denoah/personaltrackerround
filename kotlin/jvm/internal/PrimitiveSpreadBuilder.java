package kotlin.jvm.internal;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\000\n\002\020\000\n\000\n\002\020\b\n\002\b\006\n\002\020\021\n\002\b\004\n\002\020\002\n\002\b\t\b&\030\000*\b\b\000\020\001*\0020\0022\0020\002B\r\022\006\020\003\032\0020\004?\006\002\020\005J\023\020\017\032\0020\0202\006\020\021\032\0028\000?\006\002\020\022J\b\020\003\032\0020\004H\004J\035\020\023\032\0028\0002\006\020\024\032\0028\0002\006\020\025\032\0028\000H\004?\006\002\020\026J\021\020\027\032\0020\004*\0028\000H$?\006\002\020\030R\032\020\006\032\0020\004X?\016?\006\016\n\000\032\004\b\007\020\b\"\004\b\t\020\005R\016\020\003\032\0020\004X?\004?\006\002\n\000R\036\020\n\032\n\022\006\022\004\030\0018\0000\013X?\004?\006\n\n\002\020\016\022\004\b\f\020\r?\006\031"}, d2={"Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "T", "", "size", "", "(I)V", "position", "getPosition", "()I", "setPosition", "spreads", "", "spreads$annotations", "()V", "[Ljava/lang/Object;", "addSpread", "", "spreadArgument", "(Ljava/lang/Object;)V", "toArray", "values", "result", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "getSize", "(Ljava/lang/Object;)I", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public abstract class PrimitiveSpreadBuilder<T>
{
  private int position;
  private final int size;
  private final T[] spreads;
  
  public PrimitiveSpreadBuilder(int paramInt)
  {
    this.size = paramInt;
    this.spreads = new Object[paramInt];
  }
  
  public final void addSpread(T paramT)
  {
    Intrinsics.checkParameterIsNotNull(paramT, "spreadArgument");
    Object[] arrayOfObject = this.spreads;
    int i = this.position;
    this.position = (i + 1);
    arrayOfObject[i] = paramT;
  }
  
  protected final int getPosition()
  {
    return this.position;
  }
  
  protected abstract int getSize(T paramT);
  
  protected final void setPosition(int paramInt)
  {
    this.position = paramInt;
  }
  
  protected final int size()
  {
    int i = this.size - 1;
    int j = 0;
    int k = 0;
    if (i >= 0) {
      for (int m = 0;; m++)
      {
        Object localObject = this.spreads[m];
        if (localObject != null) {
          j = getSize(localObject);
        } else {
          j = 1;
        }
        k += j;
        j = k;
        if (m == i) {
          break;
        }
      }
    }
    return j;
  }
  
  protected final T toArray(T paramT1, T paramT2)
  {
    Intrinsics.checkParameterIsNotNull(paramT1, "values");
    Intrinsics.checkParameterIsNotNull(paramT2, "result");
    int i = this.size - 1;
    int j = 0;
    int m;
    if (i >= 0)
    {
      int k = 0;
      m = k;
      j = m;
      int n = m;
      for (;;)
      {
        Object localObject = this.spreads[k];
        i1 = n;
        m = j;
        if (localObject != null)
        {
          m = j;
          if (n < k)
          {
            m = k - n;
            System.arraycopy(paramT1, n, paramT2, j, m);
            m = j + m;
          }
          j = getSize(localObject);
          System.arraycopy(localObject, 0, paramT2, m, j);
          m += j;
          i1 = k + 1;
        }
        if (k == i) {
          break;
        }
        k++;
        n = i1;
        j = m;
      }
      j = i1;
    }
    else
    {
      m = 0;
    }
    int i1 = this.size;
    if (j < i1) {
      System.arraycopy(paramT1, j, paramT2, m, i1 - j);
    }
    return paramT2;
  }
}

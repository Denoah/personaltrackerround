package kotlin.jvm.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class SpreadBuilder
{
  private final ArrayList<Object> list;
  
  public SpreadBuilder(int paramInt)
  {
    this.list = new ArrayList(paramInt);
  }
  
  public void add(Object paramObject)
  {
    this.list.add(paramObject);
  }
  
  public void addSpread(Object paramObject)
  {
    if (paramObject == null) {
      return;
    }
    if ((paramObject instanceof Object[]))
    {
      localObject = (Object[])paramObject;
      if (localObject.length > 0)
      {
        paramObject = this.list;
        paramObject.ensureCapacity(paramObject.size() + localObject.length);
        Collections.addAll(this.list, (Object[])localObject);
      }
    }
    else if ((paramObject instanceof Collection))
    {
      this.list.addAll((Collection)paramObject);
    }
    else
    {
      if ((paramObject instanceof Iterable))
      {
        localObject = ((Iterable)paramObject).iterator();
        while (((Iterator)localObject).hasNext())
        {
          paramObject = ((Iterator)localObject).next();
          this.list.add(paramObject);
        }
      }
      if (!(paramObject instanceof Iterator)) {
        break label156;
      }
      paramObject = (Iterator)paramObject;
      while (paramObject.hasNext()) {
        this.list.add(paramObject.next());
      }
    }
    return;
    label156:
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Don't know how to spread ");
    ((StringBuilder)localObject).append(paramObject.getClass());
    throw new UnsupportedOperationException(((StringBuilder)localObject).toString());
  }
  
  public int size()
  {
    return this.list.size();
  }
  
  public Object[] toArray(Object[] paramArrayOfObject)
  {
    return this.list.toArray(paramArrayOfObject);
  }
}

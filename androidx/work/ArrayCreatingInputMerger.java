package androidx.work;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class ArrayCreatingInputMerger
  extends InputMerger
{
  public ArrayCreatingInputMerger() {}
  
  private Object concatenateArrayAndNonArray(Object paramObject1, Object paramObject2)
  {
    int i = Array.getLength(paramObject1);
    Object localObject = Array.newInstance(paramObject2.getClass(), i + 1);
    System.arraycopy(paramObject1, 0, localObject, 0, i);
    Array.set(localObject, i, paramObject2);
    return localObject;
  }
  
  private Object concatenateArrays(Object paramObject1, Object paramObject2)
  {
    int i = Array.getLength(paramObject1);
    int j = Array.getLength(paramObject2);
    Object localObject = Array.newInstance(paramObject1.getClass().getComponentType(), i + j);
    System.arraycopy(paramObject1, 0, localObject, 0, i);
    System.arraycopy(paramObject2, 0, localObject, i, j);
    return localObject;
  }
  
  private Object concatenateNonArrays(Object paramObject1, Object paramObject2)
  {
    Object localObject = Array.newInstance(paramObject1.getClass(), 2);
    Array.set(localObject, 0, paramObject1);
    Array.set(localObject, 1, paramObject2);
    return localObject;
  }
  
  private Object createArrayFor(Object paramObject)
  {
    Object localObject = Array.newInstance(paramObject.getClass(), 1);
    Array.set(localObject, 0, paramObject);
    return localObject;
  }
  
  public Data merge(List<Data> paramList)
  {
    Data.Builder localBuilder = new Data.Builder();
    HashMap localHashMap = new HashMap();
    Iterator localIterator1 = paramList.iterator();
    while (localIterator1.hasNext())
    {
      Iterator localIterator2 = ((Data)localIterator1.next()).getKeyValueMap().entrySet().iterator();
      while (localIterator2.hasNext())
      {
        paramList = (Map.Entry)localIterator2.next();
        String str = (String)paramList.getKey();
        paramList = paramList.getValue();
        Class localClass1 = paramList.getClass();
        Object localObject = localHashMap.get(str);
        if (localObject == null)
        {
          if (!localClass1.isArray()) {
            paramList = createArrayFor(paramList);
          }
        }
        else
        {
          Class localClass2 = localObject.getClass();
          if (localClass2.equals(localClass1))
          {
            if (localClass2.isArray()) {
              paramList = concatenateArrays(localObject, paramList);
            } else {
              paramList = concatenateNonArrays(localObject, paramList);
            }
          }
          else if ((localClass2.isArray()) && (localClass2.getComponentType().equals(localClass1)))
          {
            paramList = concatenateArrayAndNonArray(localObject, paramList);
          }
          else
          {
            if ((!localClass1.isArray()) || (!localClass1.getComponentType().equals(localClass2))) {
              break label260;
            }
            paramList = concatenateArrayAndNonArray(paramList, localObject);
          }
        }
        localHashMap.put(str, paramList);
      }
      continue;
      label260:
      throw new IllegalArgumentException();
    }
    localBuilder.putAll(localHashMap);
    return localBuilder.build();
  }
}

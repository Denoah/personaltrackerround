package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaArrayAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.name.Name;

public final class ReflectJavaArrayAnnotationArgument
  extends ReflectJavaAnnotationArgument
  implements JavaArrayAnnotationArgument
{
  private final Object[] values;
  
  public ReflectJavaArrayAnnotationArgument(Name paramName, Object[] paramArrayOfObject)
  {
    super(paramName);
    this.values = paramArrayOfObject;
  }
  
  public List<ReflectJavaAnnotationArgument> getElements()
  {
    Object[] arrayOfObject = this.values;
    Collection localCollection = (Collection)new ArrayList(arrayOfObject.length);
    int i = arrayOfObject.length;
    for (int j = 0; j < i; j++)
    {
      Object localObject = arrayOfObject[j];
      ReflectJavaAnnotationArgument.Factory localFactory = ReflectJavaAnnotationArgument.Factory;
      if (localObject == null) {
        Intrinsics.throwNpe();
      }
      localCollection.add(localFactory.create(localObject, null));
    }
    return (List)localCollection;
  }
}

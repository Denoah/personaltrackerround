package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaConstructor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaValueParameter;

public final class ReflectJavaConstructor
  extends ReflectJavaMember
  implements JavaConstructor
{
  private final Constructor<?> member;
  
  public ReflectJavaConstructor(Constructor<?> paramConstructor)
  {
    this.member = paramConstructor;
  }
  
  public Constructor<?> getMember()
  {
    return this.member;
  }
  
  public List<ReflectJavaTypeParameter> getTypeParameters()
  {
    TypeVariable[] arrayOfTypeVariable = getMember().getTypeParameters();
    Intrinsics.checkExpressionValueIsNotNull(arrayOfTypeVariable, "member.typeParameters");
    Collection localCollection = (Collection)new ArrayList(arrayOfTypeVariable.length);
    int i = arrayOfTypeVariable.length;
    for (int j = 0; j < i; j++) {
      localCollection.add(new ReflectJavaTypeParameter(arrayOfTypeVariable[j]));
    }
    return (List)localCollection;
  }
  
  public List<JavaValueParameter> getValueParameters()
  {
    Object localObject1 = getMember().getGenericParameterTypes();
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "types");
    int i;
    if (localObject1.length == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return CollectionsKt.emptyList();
    }
    Object localObject2 = getMember().getDeclaringClass();
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "klass");
    Object localObject3 = localObject1;
    if (((Class)localObject2).getDeclaringClass() != null)
    {
      localObject3 = localObject1;
      if (!Modifier.isStatic(((Class)localObject2).getModifiers())) {
        localObject3 = (Type[])ArraysKt.copyOfRange((Object[])localObject1, 1, localObject1.length);
      }
    }
    localObject2 = getMember().getParameterAnnotations();
    Object[] arrayOfObject = (Object[])localObject2;
    if (arrayOfObject.length >= localObject3.length)
    {
      localObject1 = localObject2;
      if (arrayOfObject.length > localObject3.length)
      {
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "annotations");
        localObject1 = (Annotation[][])ArraysKt.copyOfRange(arrayOfObject, arrayOfObject.length - localObject3.length, arrayOfObject.length);
      }
      Intrinsics.checkExpressionValueIsNotNull(localObject3, "realTypes");
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "realAnnotations");
      return getValueParameters((Type[])localObject3, (Annotation[][])localObject1, getMember().isVarArgs());
    }
    localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("Illegal generic signature: ");
    ((StringBuilder)localObject3).append(getMember());
    throw ((Throwable)new IllegalStateException(((StringBuilder)localObject3).toString()));
  }
}

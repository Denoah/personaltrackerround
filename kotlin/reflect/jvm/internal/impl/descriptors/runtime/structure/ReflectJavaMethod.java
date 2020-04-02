package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMethod;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMethod.DefaultImpls;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaValueParameter;

public final class ReflectJavaMethod
  extends ReflectJavaMember
  implements JavaMethod
{
  private final Method member;
  
  public ReflectJavaMethod(Method paramMethod)
  {
    this.member = paramMethod;
  }
  
  public JavaAnnotationArgument getAnnotationParameterDefaultValue()
  {
    Object localObject = getMember().getDefaultValue();
    ReflectJavaAnnotationArgument localReflectJavaAnnotationArgument = null;
    if (localObject != null) {
      localReflectJavaAnnotationArgument = ReflectJavaAnnotationArgument.Factory.create(localObject, null);
    }
    return (JavaAnnotationArgument)localReflectJavaAnnotationArgument;
  }
  
  public boolean getHasAnnotationParameterDefaultValue()
  {
    return JavaMethod.DefaultImpls.getHasAnnotationParameterDefaultValue(this);
  }
  
  public Method getMember()
  {
    return this.member;
  }
  
  public ReflectJavaType getReturnType()
  {
    ReflectJavaType.Factory localFactory = ReflectJavaType.Factory;
    Type localType = getMember().getGenericReturnType();
    Intrinsics.checkExpressionValueIsNotNull(localType, "member.genericReturnType");
    return localFactory.create(localType);
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
    Type[] arrayOfType = getMember().getGenericParameterTypes();
    Intrinsics.checkExpressionValueIsNotNull(arrayOfType, "member.genericParameterTypes");
    Annotation[][] arrayOfAnnotation = getMember().getParameterAnnotations();
    Intrinsics.checkExpressionValueIsNotNull(arrayOfAnnotation, "member.parameterAnnotations");
    return getValueParameters(arrayOfType, arrayOfAnnotation, getMember().isVarArgs());
  }
}

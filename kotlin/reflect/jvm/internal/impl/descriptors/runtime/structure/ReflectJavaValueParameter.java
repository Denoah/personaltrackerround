package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaValueParameter;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;

public final class ReflectJavaValueParameter
  extends ReflectJavaElement
  implements JavaValueParameter
{
  private final boolean isVararg;
  private final Annotation[] reflectAnnotations;
  private final String reflectName;
  private final ReflectJavaType type;
  
  public ReflectJavaValueParameter(ReflectJavaType paramReflectJavaType, Annotation[] paramArrayOfAnnotation, String paramString, boolean paramBoolean)
  {
    this.type = paramReflectJavaType;
    this.reflectAnnotations = paramArrayOfAnnotation;
    this.reflectName = paramString;
    this.isVararg = paramBoolean;
  }
  
  public ReflectJavaAnnotation findAnnotation(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    return ReflectJavaAnnotationOwnerKt.findAnnotation(this.reflectAnnotations, paramFqName);
  }
  
  public List<ReflectJavaAnnotation> getAnnotations()
  {
    return ReflectJavaAnnotationOwnerKt.getAnnotations(this.reflectAnnotations);
  }
  
  public Name getName()
  {
    Object localObject = this.reflectName;
    if (localObject != null) {
      localObject = Name.guessByFirstCharacter((String)localObject);
    } else {
      localObject = null;
    }
    return localObject;
  }
  
  public ReflectJavaType getType()
  {
    return this.type;
  }
  
  public boolean isDeprecatedInJavaDoc()
  {
    return false;
  }
  
  public boolean isVararg()
  {
    return this.isVararg;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getName());
    localStringBuilder.append(": ");
    String str;
    if (isVararg()) {
      str = "vararg ";
    } else {
      str = "";
    }
    localStringBuilder.append(str);
    localStringBuilder.append(getName());
    localStringBuilder.append(": ");
    localStringBuilder.append(getType());
    return localStringBuilder.toString();
  }
}

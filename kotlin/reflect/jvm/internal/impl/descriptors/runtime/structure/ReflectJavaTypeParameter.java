package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameter;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;

public final class ReflectJavaTypeParameter
  extends ReflectJavaElement
  implements ReflectJavaAnnotationOwner, JavaTypeParameter
{
  private final TypeVariable<?> typeVariable;
  
  public ReflectJavaTypeParameter(TypeVariable<?> paramTypeVariable)
  {
    this.typeVariable = paramTypeVariable;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof ReflectJavaTypeParameter)) && (Intrinsics.areEqual(this.typeVariable, ((ReflectJavaTypeParameter)paramObject).typeVariable))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public ReflectJavaAnnotation findAnnotation(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    return ReflectJavaAnnotationOwner.DefaultImpls.findAnnotation(this, paramFqName);
  }
  
  public List<ReflectJavaAnnotation> getAnnotations()
  {
    return ReflectJavaAnnotationOwner.DefaultImpls.getAnnotations(this);
  }
  
  public AnnotatedElement getElement()
  {
    TypeVariable localTypeVariable1 = this.typeVariable;
    TypeVariable localTypeVariable2 = localTypeVariable1;
    if (!(localTypeVariable1 instanceof AnnotatedElement)) {
      localTypeVariable2 = null;
    }
    return (AnnotatedElement)localTypeVariable2;
  }
  
  public Name getName()
  {
    Name localName = Name.identifier(this.typeVariable.getName());
    Intrinsics.checkExpressionValueIsNotNull(localName, "Name.identifier(typeVariable.name)");
    return localName;
  }
  
  public List<ReflectJavaClassifierType> getUpperBounds()
  {
    Object localObject1 = this.typeVariable.getBounds();
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "typeVariable.bounds");
    Object localObject2 = (Collection)new ArrayList(localObject1.length);
    int i = localObject1.length;
    for (int j = 0; j < i; j++) {
      ((Collection)localObject2).add(new ReflectJavaClassifierType(localObject1[j]));
    }
    localObject1 = (List)localObject2;
    localObject2 = (ReflectJavaClassifierType)CollectionsKt.singleOrNull((List)localObject1);
    if (localObject2 != null) {
      localObject2 = ((ReflectJavaClassifierType)localObject2).getReflectType();
    } else {
      localObject2 = null;
    }
    if (Intrinsics.areEqual(localObject2, Object.class)) {
      return CollectionsKt.emptyList();
    }
    return localObject1;
  }
  
  public int hashCode()
  {
    return this.typeVariable.hashCode();
  }
  
  public boolean isDeprecatedInJavaDoc()
  {
    return ReflectJavaAnnotationOwner.DefaultImpls.isDeprecatedInJavaDoc(this);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getName());
    localStringBuilder.append(": ");
    localStringBuilder.append(this.typeVariable);
    return localStringBuilder.toString();
  }
}

package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMember;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaValueParameter;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.name.SpecialNames;

public abstract class ReflectJavaMember
  extends ReflectJavaElement
  implements ReflectJavaAnnotationOwner, ReflectJavaModifierListOwner, JavaMember
{
  public ReflectJavaMember() {}
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof ReflectJavaMember)) && (Intrinsics.areEqual(getMember(), ((ReflectJavaMember)paramObject).getMember()))) {
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
  
  public ReflectJavaClass getContainingClass()
  {
    Class localClass = getMember().getDeclaringClass();
    Intrinsics.checkExpressionValueIsNotNull(localClass, "member.declaringClass");
    return new ReflectJavaClass(localClass);
  }
  
  public AnnotatedElement getElement()
  {
    Member localMember = getMember();
    if (localMember != null) {
      return (AnnotatedElement)localMember;
    }
    throw new TypeCastException("null cannot be cast to non-null type java.lang.reflect.AnnotatedElement");
  }
  
  public abstract Member getMember();
  
  public int getModifiers()
  {
    return getMember().getModifiers();
  }
  
  public Name getName()
  {
    Object localObject = getMember().getName();
    if (localObject != null)
    {
      localObject = Name.identifier((String)localObject);
      if (localObject != null) {}
    }
    else
    {
      localObject = SpecialNames.NO_NAME_PROVIDED;
      Intrinsics.checkExpressionValueIsNotNull(localObject, "SpecialNames.NO_NAME_PROVIDED");
    }
    return localObject;
  }
  
  protected final List<JavaValueParameter> getValueParameters(Type[] paramArrayOfType, Annotation[][] paramArrayOfAnnotation, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfType, "parameterTypes");
    Intrinsics.checkParameterIsNotNull(paramArrayOfAnnotation, "parameterAnnotations");
    ArrayList localArrayList = new ArrayList(paramArrayOfType.length);
    List localList = Java8ParameterNamesLoader.INSTANCE.loadParameterNames(getMember());
    int i;
    if (localList != null) {
      i = localList.size() - paramArrayOfType.length;
    } else {
      i = 0;
    }
    int j = paramArrayOfType.length;
    for (int k = 0; k < j; k++)
    {
      ReflectJavaType localReflectJavaType = ReflectJavaType.Factory.create(paramArrayOfType[k]);
      String str;
      if (localList != null)
      {
        str = (String)CollectionsKt.getOrNull(localList, k + i);
        if (str == null)
        {
          paramArrayOfType = new StringBuilder();
          paramArrayOfType.append("No parameter with index ");
          paramArrayOfType.append(k);
          paramArrayOfType.append('+');
          paramArrayOfType.append(i);
          paramArrayOfType.append(" (name=");
          paramArrayOfType.append(getName());
          paramArrayOfType.append(" type=");
          paramArrayOfType.append(localReflectJavaType);
          paramArrayOfType.append(") in ");
          paramArrayOfType.append(localList);
          paramArrayOfType.append("@ReflectJavaMember");
          throw ((Throwable)new IllegalStateException(paramArrayOfType.toString().toString()));
        }
      }
      else
      {
        str = null;
      }
      boolean bool;
      if ((paramBoolean) && (k == ArraysKt.getLastIndex(paramArrayOfType))) {
        bool = true;
      } else {
        bool = false;
      }
      localArrayList.add(new ReflectJavaValueParameter(localReflectJavaType, paramArrayOfAnnotation[k], str, bool));
    }
    return (List)localArrayList;
  }
  
  public Visibility getVisibility()
  {
    return ReflectJavaModifierListOwner.DefaultImpls.getVisibility(this);
  }
  
  public int hashCode()
  {
    return getMember().hashCode();
  }
  
  public boolean isAbstract()
  {
    return ReflectJavaModifierListOwner.DefaultImpls.isAbstract(this);
  }
  
  public boolean isDeprecatedInJavaDoc()
  {
    return ReflectJavaAnnotationOwner.DefaultImpls.isDeprecatedInJavaDoc(this);
  }
  
  public boolean isFinal()
  {
    return ReflectJavaModifierListOwner.DefaultImpls.isFinal(this);
  }
  
  public boolean isStatic()
  {
    return ReflectJavaModifierListOwner.DefaultImpls.isStatic(this);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getName());
    localStringBuilder.append(": ");
    localStringBuilder.append(getMember());
    return localStringBuilder.toString();
  }
}

package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotation;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaPackage;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;

public final class ReflectJavaPackage
  extends ReflectJavaElement
  implements JavaPackage
{
  private final FqName fqName;
  
  public ReflectJavaPackage(FqName paramFqName)
  {
    this.fqName = paramFqName;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof ReflectJavaPackage)) && (Intrinsics.areEqual(getFqName(), ((ReflectJavaPackage)paramObject).getFqName()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public JavaAnnotation findAnnotation(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    return null;
  }
  
  public List<JavaAnnotation> getAnnotations()
  {
    return CollectionsKt.emptyList();
  }
  
  public Collection<JavaClass> getClasses(Function1<? super Name, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction1, "nameFilter");
    return (Collection)CollectionsKt.emptyList();
  }
  
  public FqName getFqName()
  {
    return this.fqName;
  }
  
  public Collection<JavaPackage> getSubPackages()
  {
    return (Collection)CollectionsKt.emptyList();
  }
  
  public int hashCode()
  {
    return getFqName().hashCode();
  }
  
  public boolean isDeprecatedInJavaDoc()
  {
    return false;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getName());
    localStringBuilder.append(": ");
    localStringBuilder.append(getFqName());
    return localStringBuilder.toString();
  }
}

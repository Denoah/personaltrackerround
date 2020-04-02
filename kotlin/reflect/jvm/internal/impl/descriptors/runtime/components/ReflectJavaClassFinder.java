package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import java.util.Set;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaClass;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaPackage;
import kotlin.reflect.jvm.internal.impl.load.java.JavaClassFinder;
import kotlin.reflect.jvm.internal.impl.load.java.JavaClassFinder.Request;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaPackage;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.text.StringsKt;

public final class ReflectJavaClassFinder
  implements JavaClassFinder
{
  private final ClassLoader classLoader;
  
  public ReflectJavaClassFinder(ClassLoader paramClassLoader)
  {
    this.classLoader = paramClassLoader;
  }
  
  public JavaClass findClass(JavaClassFinder.Request paramRequest)
  {
    Intrinsics.checkParameterIsNotNull(paramRequest, "request");
    paramRequest = paramRequest.getClassId();
    FqName localFqName = paramRequest.getPackageFqName();
    Intrinsics.checkExpressionValueIsNotNull(localFqName, "classId.packageFqName");
    paramRequest = paramRequest.getRelativeClassName().asString();
    Intrinsics.checkExpressionValueIsNotNull(paramRequest, "classId.relativeClassName.asString()");
    paramRequest = StringsKt.replace$default(paramRequest, '.', '$', false, 4, null);
    if (!localFqName.isRoot())
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(localFqName.asString());
      localStringBuilder.append(".");
      localStringBuilder.append(paramRequest);
      paramRequest = localStringBuilder.toString();
    }
    paramRequest = ReflectJavaClassFinderKt.tryLoadClass(this.classLoader, paramRequest);
    if (paramRequest != null) {
      paramRequest = (JavaClass)new ReflectJavaClass(paramRequest);
    } else {
      paramRequest = null;
    }
    return paramRequest;
  }
  
  public JavaPackage findPackage(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    return (JavaPackage)new ReflectJavaPackage(paramFqName);
  }
  
  public Set<String> knownClassNamesInPackage(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "packageFqName");
    return null;
  }
}

package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import java.io.InputStream;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinder;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinder.Result;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinder.Result.KotlinClass;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins.BuiltInSerializerProtocol;

public final class ReflectKotlinClassFinder
  implements KotlinClassFinder
{
  private final ClassLoader classLoader;
  
  public ReflectKotlinClassFinder(ClassLoader paramClassLoader)
  {
    this.classLoader = paramClassLoader;
  }
  
  private final KotlinClassFinder.Result findKotlinClass(String paramString)
  {
    paramString = ReflectJavaClassFinderKt.tryLoadClass(this.classLoader, paramString);
    if (paramString != null)
    {
      paramString = ReflectKotlinClass.Factory.create(paramString);
      if (paramString != null)
      {
        paramString = new KotlinClassFinder.Result.KotlinClass((KotlinJvmBinaryClass)paramString);
        break label42;
      }
    }
    paramString = null;
    label42:
    return (KotlinClassFinder.Result)paramString;
  }
  
  public InputStream findBuiltInsData(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "packageFqName");
    if (!paramFqName.startsWith(KotlinBuiltIns.BUILT_INS_PACKAGE_NAME)) {
      return null;
    }
    return this.classLoader.getResourceAsStream(BuiltInSerializerProtocol.INSTANCE.getBuiltInsFilePath(paramFqName));
  }
  
  public KotlinClassFinder.Result findKotlinClassOrContent(JavaClass paramJavaClass)
  {
    Intrinsics.checkParameterIsNotNull(paramJavaClass, "javaClass");
    paramJavaClass = paramJavaClass.getFqName();
    if (paramJavaClass != null)
    {
      paramJavaClass = paramJavaClass.asString();
      if (paramJavaClass != null) {
        return findKotlinClass(paramJavaClass);
      }
    }
    return null;
  }
  
  public KotlinClassFinder.Result findKotlinClassOrContent(ClassId paramClassId)
  {
    Intrinsics.checkParameterIsNotNull(paramClassId, "classId");
    return findKotlinClass(ReflectKotlinClassFinderKt.access$toRuntimeFqName(paramClassId));
  }
}

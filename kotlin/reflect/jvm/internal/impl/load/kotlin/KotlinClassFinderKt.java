package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.name.ClassId;

public final class KotlinClassFinderKt
{
  public static final KotlinJvmBinaryClass findKotlinClass(KotlinClassFinder paramKotlinClassFinder, JavaClass paramJavaClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinClassFinder, "$this$findKotlinClass");
    Intrinsics.checkParameterIsNotNull(paramJavaClass, "javaClass");
    paramKotlinClassFinder = paramKotlinClassFinder.findKotlinClassOrContent(paramJavaClass);
    if (paramKotlinClassFinder != null) {
      paramKotlinClassFinder = paramKotlinClassFinder.toKotlinJvmBinaryClass();
    } else {
      paramKotlinClassFinder = null;
    }
    return paramKotlinClassFinder;
  }
  
  public static final KotlinJvmBinaryClass findKotlinClass(KotlinClassFinder paramKotlinClassFinder, ClassId paramClassId)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinClassFinder, "$this$findKotlinClass");
    Intrinsics.checkParameterIsNotNull(paramClassId, "classId");
    paramKotlinClassFinder = paramKotlinClassFinder.findKotlinClassOrContent(paramClassId);
    if (paramKotlinClassFinder != null) {
      paramKotlinClassFinder = paramKotlinClassFinder.toKotlinJvmBinaryClass();
    } else {
      paramKotlinClassFinder = null;
    }
    return paramKotlinClassFinder;
  }
}

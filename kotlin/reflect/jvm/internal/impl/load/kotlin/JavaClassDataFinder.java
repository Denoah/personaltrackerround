package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin._Assertions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassData;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassDataFinder;

public final class JavaClassDataFinder
  implements ClassDataFinder
{
  private final DeserializedDescriptorResolver deserializedDescriptorResolver;
  private final KotlinClassFinder kotlinClassFinder;
  
  public JavaClassDataFinder(KotlinClassFinder paramKotlinClassFinder, DeserializedDescriptorResolver paramDeserializedDescriptorResolver)
  {
    this.kotlinClassFinder = paramKotlinClassFinder;
    this.deserializedDescriptorResolver = paramDeserializedDescriptorResolver;
  }
  
  public ClassData findClassData(ClassId paramClassId)
  {
    Intrinsics.checkParameterIsNotNull(paramClassId, "classId");
    KotlinJvmBinaryClass localKotlinJvmBinaryClass = KotlinClassFinderKt.findKotlinClass(this.kotlinClassFinder, paramClassId);
    if (localKotlinJvmBinaryClass != null)
    {
      boolean bool = Intrinsics.areEqual(localKotlinJvmBinaryClass.getClassId(), paramClassId);
      if ((_Assertions.ENABLED) && (!bool))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Class with incorrect id found: expected ");
        localStringBuilder.append(paramClassId);
        localStringBuilder.append(", actual ");
        localStringBuilder.append(localKotlinJvmBinaryClass.getClassId());
        throw ((Throwable)new AssertionError(localStringBuilder.toString()));
      }
      return this.deserializedDescriptorResolver.readClassData$descriptors_jvm(localKotlinJvmBinaryClass);
    }
    return null;
  }
}

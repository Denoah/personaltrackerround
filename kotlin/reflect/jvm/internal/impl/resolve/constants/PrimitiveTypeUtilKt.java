package kotlin.reflect.jvm.internal.impl.resolve.constants;

import java.util.Collection;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;

public final class PrimitiveTypeUtilKt
{
  public static final Collection<KotlinType> getAllSignedLiteralTypes(ModuleDescriptor paramModuleDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "$this$allSignedLiteralTypes");
    return (Collection)CollectionsKt.listOf(new SimpleType[] { paramModuleDescriptor.getBuiltIns().getIntType(), paramModuleDescriptor.getBuiltIns().getLongType(), paramModuleDescriptor.getBuiltIns().getByteType(), paramModuleDescriptor.getBuiltIns().getShortType() });
  }
}

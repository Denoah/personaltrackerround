package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.Collection;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public final class TypeMappingConfigurationImpl
  implements TypeMappingConfiguration<JvmType>
{
  public static final TypeMappingConfigurationImpl INSTANCE = new TypeMappingConfigurationImpl();
  
  private TypeMappingConfigurationImpl() {}
  
  public KotlinType commonSupertype(Collection<? extends KotlinType> paramCollection)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "types");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("There should be no intersection type in existing descriptors, but found: ");
    localStringBuilder.append(CollectionsKt.joinToString$default((Iterable)paramCollection, null, null, null, 0, null, null, 63, null));
    throw ((Throwable)new AssertionError(localStringBuilder.toString()));
  }
  
  public String getPredefinedFullInternalNameForClass(ClassDescriptor paramClassDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "classDescriptor");
    return TypeMappingConfiguration.DefaultImpls.getPredefinedFullInternalNameForClass(this, paramClassDescriptor);
  }
  
  public String getPredefinedInternalNameForClass(ClassDescriptor paramClassDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "classDescriptor");
    return null;
  }
  
  public JvmType getPredefinedTypeForClass(ClassDescriptor paramClassDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "classDescriptor");
    return null;
  }
  
  public KotlinType preprocessType(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "kotlinType");
    return TypeMappingConfiguration.DefaultImpls.preprocessType(this, paramKotlinType);
  }
  
  public void processErrorType(KotlinType paramKotlinType, ClassDescriptor paramClassDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "kotlinType");
    Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "descriptor");
  }
  
  public boolean releaseCoroutines()
  {
    return TypeMappingConfiguration.DefaultImpls.releaseCoroutines(this);
  }
}

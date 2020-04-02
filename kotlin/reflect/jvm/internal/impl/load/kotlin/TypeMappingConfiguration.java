package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.Collection;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public abstract interface TypeMappingConfiguration<T>
{
  public abstract KotlinType commonSupertype(Collection<KotlinType> paramCollection);
  
  public abstract String getPredefinedFullInternalNameForClass(ClassDescriptor paramClassDescriptor);
  
  public abstract String getPredefinedInternalNameForClass(ClassDescriptor paramClassDescriptor);
  
  public abstract T getPredefinedTypeForClass(ClassDescriptor paramClassDescriptor);
  
  public abstract KotlinType preprocessType(KotlinType paramKotlinType);
  
  public abstract void processErrorType(KotlinType paramKotlinType, ClassDescriptor paramClassDescriptor);
  
  public abstract boolean releaseCoroutines();
  
  public static final class DefaultImpls
  {
    public static <T> String getPredefinedFullInternalNameForClass(TypeMappingConfiguration<? extends T> paramTypeMappingConfiguration, ClassDescriptor paramClassDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "classDescriptor");
      return null;
    }
    
    public static <T> KotlinType preprocessType(TypeMappingConfiguration<? extends T> paramTypeMappingConfiguration, KotlinType paramKotlinType)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinType, "kotlinType");
      return null;
    }
    
    public static <T> boolean releaseCoroutines(TypeMappingConfiguration<? extends T> paramTypeMappingConfiguration)
    {
      return true;
    }
  }
}

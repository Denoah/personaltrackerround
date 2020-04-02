package kotlin.reflect.jvm.internal.impl.descriptors.deserialization;

import java.util.Collection;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public abstract interface AdditionalClassPartsProvider
{
  public abstract Collection<ClassConstructorDescriptor> getConstructors(ClassDescriptor paramClassDescriptor);
  
  public abstract Collection<SimpleFunctionDescriptor> getFunctions(Name paramName, ClassDescriptor paramClassDescriptor);
  
  public abstract Collection<Name> getFunctionsNames(ClassDescriptor paramClassDescriptor);
  
  public abstract Collection<KotlinType> getSupertypes(ClassDescriptor paramClassDescriptor);
  
  public static final class None
    implements AdditionalClassPartsProvider
  {
    public static final None INSTANCE = new None();
    
    private None() {}
    
    public Collection<ClassConstructorDescriptor> getConstructors(ClassDescriptor paramClassDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "classDescriptor");
      return (Collection)CollectionsKt.emptyList();
    }
    
    public Collection<SimpleFunctionDescriptor> getFunctions(Name paramName, ClassDescriptor paramClassDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramName, "name");
      Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "classDescriptor");
      return (Collection)CollectionsKt.emptyList();
    }
    
    public Collection<Name> getFunctionsNames(ClassDescriptor paramClassDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "classDescriptor");
      return (Collection)CollectionsKt.emptyList();
    }
    
    public Collection<KotlinType> getSupertypes(ClassDescriptor paramClassDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "classDescriptor");
      return (Collection)CollectionsKt.emptyList();
    }
  }
}

package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;

public abstract interface ConstructorDescriptor
  extends FunctionDescriptor
{
  public abstract ClassDescriptor getConstructedClass();
  
  public abstract ClassifierDescriptorWithTypeParameters getContainingDeclaration();
  
  public abstract KotlinType getReturnType();
  
  public abstract List<TypeParameterDescriptor> getTypeParameters();
  
  public abstract boolean isPrimary();
  
  public abstract ConstructorDescriptor substitute(TypeSubstitutor paramTypeSubstitutor);
}

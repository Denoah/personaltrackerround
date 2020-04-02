package kotlin.reflect.jvm.internal.impl.types;

import java.util.Collection;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;

public abstract interface TypeConstructor
  extends TypeConstructorMarker
{
  public abstract KotlinBuiltIns getBuiltIns();
  
  public abstract ClassifierDescriptor getDeclarationDescriptor();
  
  public abstract List<TypeParameterDescriptor> getParameters();
  
  public abstract Collection<KotlinType> getSupertypes();
  
  public abstract boolean isDenotable();
  
  public abstract TypeConstructor refine(KotlinTypeRefiner paramKotlinTypeRefiner);
}

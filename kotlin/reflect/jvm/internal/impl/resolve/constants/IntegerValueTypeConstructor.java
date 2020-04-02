package kotlin.reflect.jvm.internal.impl.resolve.constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

public final class IntegerValueTypeConstructor
  implements TypeConstructor
{
  private final ModuleDescriptor module;
  private final ArrayList<KotlinType> supertypes;
  private final long value;
  
  public KotlinBuiltIns getBuiltIns()
  {
    return this.module.getBuiltIns();
  }
  
  public Void getDeclarationDescriptor()
  {
    return null;
  }
  
  public List<TypeParameterDescriptor> getParameters()
  {
    return CollectionsKt.emptyList();
  }
  
  public Collection<KotlinType> getSupertypes()
  {
    return (Collection)this.supertypes;
  }
  
  public boolean isDenotable()
  {
    return false;
  }
  
  public TypeConstructor refine(KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeRefiner, "kotlinTypeRefiner");
    return (TypeConstructor)this;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("IntegerValueType(");
    localStringBuilder.append(this.value);
    localStringBuilder.append(')');
    return localStringBuilder.toString();
  }
}

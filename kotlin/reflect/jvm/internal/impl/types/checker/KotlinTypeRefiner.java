package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.Collection;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;

public abstract class KotlinTypeRefiner
{
  public KotlinTypeRefiner() {}
  
  public abstract ClassDescriptor findClassAcrossModuleDependencies(ClassId paramClassId);
  
  public abstract <S extends MemberScope> S getOrPutScopeForClass(ClassDescriptor paramClassDescriptor, Function0<? extends S> paramFunction0);
  
  public abstract boolean isRefinementNeededForModule(ModuleDescriptor paramModuleDescriptor);
  
  public abstract boolean isRefinementNeededForTypeConstructor(TypeConstructor paramTypeConstructor);
  
  public abstract ClassifierDescriptor refineDescriptor(DeclarationDescriptor paramDeclarationDescriptor);
  
  public abstract Collection<KotlinType> refineSupertypes(ClassDescriptor paramClassDescriptor);
  
  public abstract KotlinType refineType(KotlinType paramKotlinType);
  
  public static final class Default
    extends KotlinTypeRefiner
  {
    public static final Default INSTANCE = new Default();
    
    private Default() {}
    
    public ClassDescriptor findClassAcrossModuleDependencies(ClassId paramClassId)
    {
      Intrinsics.checkParameterIsNotNull(paramClassId, "classId");
      return null;
    }
    
    public <S extends MemberScope> S getOrPutScopeForClass(ClassDescriptor paramClassDescriptor, Function0<? extends S> paramFunction0)
    {
      Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "classDescriptor");
      Intrinsics.checkParameterIsNotNull(paramFunction0, "compute");
      return (MemberScope)paramFunction0.invoke();
    }
    
    public boolean isRefinementNeededForModule(ModuleDescriptor paramModuleDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "moduleDescriptor");
      return false;
    }
    
    public boolean isRefinementNeededForTypeConstructor(TypeConstructor paramTypeConstructor)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeConstructor, "typeConstructor");
      return false;
    }
    
    public ClassDescriptor refineDescriptor(DeclarationDescriptor paramDeclarationDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptor, "descriptor");
      return null;
    }
    
    public Collection<KotlinType> refineSupertypes(ClassDescriptor paramClassDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "classDescriptor");
      paramClassDescriptor = paramClassDescriptor.getTypeConstructor();
      Intrinsics.checkExpressionValueIsNotNull(paramClassDescriptor, "classDescriptor.typeConstructor");
      paramClassDescriptor = paramClassDescriptor.getSupertypes();
      Intrinsics.checkExpressionValueIsNotNull(paramClassDescriptor, "classDescriptor.typeConstructor.supertypes");
      return paramClassDescriptor;
    }
    
    public KotlinType refineType(KotlinType paramKotlinType)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinType, "type");
      return paramKotlinType;
    }
  }
}

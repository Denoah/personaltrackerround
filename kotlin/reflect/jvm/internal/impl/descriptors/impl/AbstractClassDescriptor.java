package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.InnerClassesScopeWrapper;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.SubstitutingScope;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

public abstract class AbstractClassDescriptor
  extends ModuleAwareClassDescriptor
{
  protected final NotNullLazyValue<SimpleType> defaultType;
  private final Name name;
  private final NotNullLazyValue<ReceiverParameterDescriptor> thisAsReceiverParameter;
  private final NotNullLazyValue<MemberScope> unsubstitutedInnerClassesScope;
  
  public AbstractClassDescriptor(StorageManager paramStorageManager, Name paramName)
  {
    this.name = paramName;
    this.defaultType = paramStorageManager.createLazyValue(new Function0()
    {
      public SimpleType invoke()
      {
        AbstractClassDescriptor localAbstractClassDescriptor = AbstractClassDescriptor.this;
        TypeUtils.makeUnsubstitutedType(localAbstractClassDescriptor, localAbstractClassDescriptor.getUnsubstitutedMemberScope(), new Function1()
        {
          public SimpleType invoke(KotlinTypeRefiner paramAnonymous2KotlinTypeRefiner)
          {
            ClassifierDescriptor localClassifierDescriptor = paramAnonymous2KotlinTypeRefiner.refineDescriptor(AbstractClassDescriptor.this);
            if (localClassifierDescriptor == null) {
              return (SimpleType)AbstractClassDescriptor.this.defaultType.invoke();
            }
            if ((localClassifierDescriptor instanceof TypeAliasDescriptor)) {
              return KotlinTypeFactory.computeExpandedType((TypeAliasDescriptor)localClassifierDescriptor, TypeUtils.getDefaultTypeProjections(localClassifierDescriptor.getTypeConstructor().getParameters()));
            }
            if ((localClassifierDescriptor instanceof ModuleAwareClassDescriptor)) {
              return TypeUtils.makeUnsubstitutedType(localClassifierDescriptor.getTypeConstructor().refine(paramAnonymous2KotlinTypeRefiner), ((ModuleAwareClassDescriptor)localClassifierDescriptor).getUnsubstitutedMemberScope(paramAnonymous2KotlinTypeRefiner), this);
            }
            return localClassifierDescriptor.getDefaultType();
          }
        });
      }
    });
    this.unsubstitutedInnerClassesScope = paramStorageManager.createLazyValue(new Function0()
    {
      public MemberScope invoke()
      {
        return new InnerClassesScopeWrapper(AbstractClassDescriptor.this.getUnsubstitutedMemberScope());
      }
    });
    this.thisAsReceiverParameter = paramStorageManager.createLazyValue(new Function0()
    {
      public ReceiverParameterDescriptor invoke()
      {
        return new LazyClassReceiverParameterDescriptor(AbstractClassDescriptor.this);
      }
    });
  }
  
  public <R, D> R accept(DeclarationDescriptorVisitor<R, D> paramDeclarationDescriptorVisitor, D paramD)
  {
    return paramDeclarationDescriptorVisitor.visitClassDescriptor(this, paramD);
  }
  
  public SimpleType getDefaultType()
  {
    SimpleType localSimpleType = (SimpleType)this.defaultType.invoke();
    if (localSimpleType == null) {
      $$$reportNull$$$0(19);
    }
    return localSimpleType;
  }
  
  public MemberScope getMemberScope(TypeSubstitution paramTypeSubstitution)
  {
    if (paramTypeSubstitution == null) {
      $$$reportNull$$$0(14);
    }
    paramTypeSubstitution = getMemberScope(paramTypeSubstitution, DescriptorUtilsKt.getKotlinTypeRefiner(DescriptorUtils.getContainingModule(this)));
    if (paramTypeSubstitution == null) {
      $$$reportNull$$$0(15);
    }
    return paramTypeSubstitution;
  }
  
  public MemberScope getMemberScope(TypeSubstitution paramTypeSubstitution, KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    if (paramTypeSubstitution == null) {
      $$$reportNull$$$0(9);
    }
    if (paramKotlinTypeRefiner == null) {
      $$$reportNull$$$0(10);
    }
    if (paramTypeSubstitution.isEmpty())
    {
      paramTypeSubstitution = getUnsubstitutedMemberScope(paramKotlinTypeRefiner);
      if (paramTypeSubstitution == null) {
        $$$reportNull$$$0(11);
      }
      return paramTypeSubstitution;
    }
    paramTypeSubstitution = TypeSubstitutor.create(paramTypeSubstitution);
    return new SubstitutingScope(getUnsubstitutedMemberScope(paramKotlinTypeRefiner), paramTypeSubstitution);
  }
  
  public Name getName()
  {
    Name localName = this.name;
    if (localName == null) {
      $$$reportNull$$$0(2);
    }
    return localName;
  }
  
  public ClassDescriptor getOriginal()
  {
    return this;
  }
  
  public ReceiverParameterDescriptor getThisAsReceiverParameter()
  {
    ReceiverParameterDescriptor localReceiverParameterDescriptor = (ReceiverParameterDescriptor)this.thisAsReceiverParameter.invoke();
    if (localReceiverParameterDescriptor == null) {
      $$$reportNull$$$0(5);
    }
    return localReceiverParameterDescriptor;
  }
  
  public MemberScope getUnsubstitutedInnerClassesScope()
  {
    MemberScope localMemberScope = (MemberScope)this.unsubstitutedInnerClassesScope.invoke();
    if (localMemberScope == null) {
      $$$reportNull$$$0(4);
    }
    return localMemberScope;
  }
  
  public MemberScope getUnsubstitutedMemberScope()
  {
    MemberScope localMemberScope = getUnsubstitutedMemberScope(DescriptorUtilsKt.getKotlinTypeRefiner(DescriptorUtils.getContainingModule(this)));
    if (localMemberScope == null) {
      $$$reportNull$$$0(16);
    }
    return localMemberScope;
  }
  
  public ClassDescriptor substitute(TypeSubstitutor paramTypeSubstitutor)
  {
    if (paramTypeSubstitutor == null) {
      $$$reportNull$$$0(17);
    }
    if (paramTypeSubstitutor.isEmpty()) {
      return this;
    }
    return new LazySubstitutingClassDescriptor(this, paramTypeSubstitutor);
  }
}

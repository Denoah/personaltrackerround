package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.SupertypeLoopChecker;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.LazyScopeAdapter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.TypeIntersectionScope;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.Variance;

public abstract class AbstractTypeParameterDescriptor
  extends DeclarationDescriptorNonRootImpl
  implements TypeParameterDescriptor
{
  private final NotNullLazyValue<SimpleType> defaultType;
  private final int index;
  private final boolean reified;
  private final NotNullLazyValue<TypeConstructor> typeConstructor;
  private final Variance variance;
  
  protected AbstractTypeParameterDescriptor(final StorageManager paramStorageManager, DeclarationDescriptor paramDeclarationDescriptor, Annotations paramAnnotations, final Name paramName, Variance paramVariance, boolean paramBoolean, int paramInt, SourceElement paramSourceElement, final SupertypeLoopChecker paramSupertypeLoopChecker)
  {
    super(paramDeclarationDescriptor, paramAnnotations, paramName, paramSourceElement);
    this.variance = paramVariance;
    this.reified = paramBoolean;
    this.index = paramInt;
    this.typeConstructor = paramStorageManager.createLazyValue(new Function0()
    {
      public TypeConstructor invoke()
      {
        return new AbstractTypeParameterDescriptor.TypeParameterTypeConstructor(AbstractTypeParameterDescriptor.this, paramStorageManager, paramSupertypeLoopChecker);
      }
    });
    this.defaultType = paramStorageManager.createLazyValue(new Function0()
    {
      public SimpleType invoke()
      {
        KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(Annotations.Companion.getEMPTY(), AbstractTypeParameterDescriptor.this.getTypeConstructor(), Collections.emptyList(), false, new LazyScopeAdapter(paramStorageManager.createLazyValue(new Function0()
        {
          public MemberScope invoke()
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("Scope for type parameter ");
            localStringBuilder.append(AbstractTypeParameterDescriptor.2.this.val$name.asString());
            return TypeIntersectionScope.create(localStringBuilder.toString(), AbstractTypeParameterDescriptor.this.getUpperBounds());
          }
        })));
      }
    });
  }
  
  public <R, D> R accept(DeclarationDescriptorVisitor<R, D> paramDeclarationDescriptorVisitor, D paramD)
  {
    return paramDeclarationDescriptorVisitor.visitTypeParameterDescriptor(this, paramD);
  }
  
  public SimpleType getDefaultType()
  {
    SimpleType localSimpleType = (SimpleType)this.defaultType.invoke();
    if (localSimpleType == null) {
      $$$reportNull$$$0(10);
    }
    return localSimpleType;
  }
  
  public int getIndex()
  {
    return this.index;
  }
  
  public TypeParameterDescriptor getOriginal()
  {
    TypeParameterDescriptor localTypeParameterDescriptor = (TypeParameterDescriptor)super.getOriginal();
    if (localTypeParameterDescriptor == null) {
      $$$reportNull$$$0(11);
    }
    return localTypeParameterDescriptor;
  }
  
  public final TypeConstructor getTypeConstructor()
  {
    TypeConstructor localTypeConstructor = (TypeConstructor)this.typeConstructor.invoke();
    if (localTypeConstructor == null) {
      $$$reportNull$$$0(9);
    }
    return localTypeConstructor;
  }
  
  public List<KotlinType> getUpperBounds()
  {
    List localList = ((TypeParameterTypeConstructor)getTypeConstructor()).getSupertypes();
    if (localList == null) {
      $$$reportNull$$$0(8);
    }
    return localList;
  }
  
  public Variance getVariance()
  {
    Variance localVariance = this.variance;
    if (localVariance == null) {
      $$$reportNull$$$0(7);
    }
    return localVariance;
  }
  
  public boolean isCapturedFromOuterDeclaration()
  {
    return false;
  }
  
  public boolean isReified()
  {
    return this.reified;
  }
  
  protected abstract void reportSupertypeLoopError(KotlinType paramKotlinType);
  
  protected abstract List<KotlinType> resolveUpperBounds();
  
  private class TypeParameterTypeConstructor
    extends AbstractTypeConstructor
  {
    private final SupertypeLoopChecker supertypeLoopChecker;
    
    public TypeParameterTypeConstructor(StorageManager paramStorageManager, SupertypeLoopChecker paramSupertypeLoopChecker)
    {
      super();
      this.supertypeLoopChecker = paramSupertypeLoopChecker;
    }
    
    protected Collection<KotlinType> computeSupertypes()
    {
      List localList = AbstractTypeParameterDescriptor.this.resolveUpperBounds();
      if (localList == null) {
        $$$reportNull$$$0(1);
      }
      return localList;
    }
    
    protected KotlinType defaultSupertypeIfEmpty()
    {
      return ErrorUtils.createErrorType("Cyclic upper bounds");
    }
    
    public KotlinBuiltIns getBuiltIns()
    {
      KotlinBuiltIns localKotlinBuiltIns = DescriptorUtilsKt.getBuiltIns(AbstractTypeParameterDescriptor.this);
      if (localKotlinBuiltIns == null) {
        $$$reportNull$$$0(4);
      }
      return localKotlinBuiltIns;
    }
    
    public ClassifierDescriptor getDeclarationDescriptor()
    {
      AbstractTypeParameterDescriptor localAbstractTypeParameterDescriptor = AbstractTypeParameterDescriptor.this;
      if (localAbstractTypeParameterDescriptor == null) {
        $$$reportNull$$$0(3);
      }
      return localAbstractTypeParameterDescriptor;
    }
    
    public List<TypeParameterDescriptor> getParameters()
    {
      List localList = Collections.emptyList();
      if (localList == null) {
        $$$reportNull$$$0(2);
      }
      return localList;
    }
    
    protected SupertypeLoopChecker getSupertypeLoopChecker()
    {
      SupertypeLoopChecker localSupertypeLoopChecker = this.supertypeLoopChecker;
      if (localSupertypeLoopChecker == null) {
        $$$reportNull$$$0(5);
      }
      return localSupertypeLoopChecker;
    }
    
    public boolean isDenotable()
    {
      return true;
    }
    
    protected void reportSupertypeLoopError(KotlinType paramKotlinType)
    {
      if (paramKotlinType == null) {
        $$$reportNull$$$0(6);
      }
      AbstractTypeParameterDescriptor.this.reportSupertypeLoopError(paramKotlinType);
    }
    
    public String toString()
    {
      return AbstractTypeParameterDescriptor.this.getName().toString();
    }
  }
}

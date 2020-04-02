package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.SupertypeLoopChecker;
import kotlin.reflect.jvm.internal.impl.descriptors.SupertypeLoopChecker.EMPTY;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.Variance;

public class TypeParameterDescriptorImpl
  extends AbstractTypeParameterDescriptor
{
  private boolean initialized = false;
  private final Function1<KotlinType, Void> reportCycleError;
  private final List<KotlinType> upperBounds = new ArrayList(1);
  
  private TypeParameterDescriptorImpl(DeclarationDescriptor paramDeclarationDescriptor, Annotations paramAnnotations, boolean paramBoolean, Variance paramVariance, Name paramName, int paramInt, SourceElement paramSourceElement, Function1<KotlinType, Void> paramFunction1, SupertypeLoopChecker paramSupertypeLoopChecker)
  {
    super(LockBasedStorageManager.NO_LOCKS, paramDeclarationDescriptor, paramAnnotations, paramName, paramVariance, paramBoolean, paramInt, paramSourceElement, paramSupertypeLoopChecker);
    this.reportCycleError = paramFunction1;
  }
  
  private void checkInitialized()
  {
    if (this.initialized) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Type parameter descriptor is not initialized: ");
    localStringBuilder.append(nameForAssertions());
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  private void checkUninitialized()
  {
    if (!this.initialized) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Type parameter descriptor is already initialized: ");
    localStringBuilder.append(nameForAssertions());
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  public static TypeParameterDescriptorImpl createForFurtherModification(DeclarationDescriptor paramDeclarationDescriptor, Annotations paramAnnotations, boolean paramBoolean, Variance paramVariance, Name paramName, int paramInt, SourceElement paramSourceElement)
  {
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(5);
    }
    if (paramAnnotations == null) {
      $$$reportNull$$$0(6);
    }
    if (paramVariance == null) {
      $$$reportNull$$$0(7);
    }
    if (paramName == null) {
      $$$reportNull$$$0(8);
    }
    if (paramSourceElement == null) {
      $$$reportNull$$$0(9);
    }
    return createForFurtherModification(paramDeclarationDescriptor, paramAnnotations, paramBoolean, paramVariance, paramName, paramInt, paramSourceElement, null, SupertypeLoopChecker.EMPTY.INSTANCE);
  }
  
  public static TypeParameterDescriptorImpl createForFurtherModification(DeclarationDescriptor paramDeclarationDescriptor, Annotations paramAnnotations, boolean paramBoolean, Variance paramVariance, Name paramName, int paramInt, SourceElement paramSourceElement, Function1<KotlinType, Void> paramFunction1, SupertypeLoopChecker paramSupertypeLoopChecker)
  {
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(10);
    }
    if (paramAnnotations == null) {
      $$$reportNull$$$0(11);
    }
    if (paramVariance == null) {
      $$$reportNull$$$0(12);
    }
    if (paramName == null) {
      $$$reportNull$$$0(13);
    }
    if (paramSourceElement == null) {
      $$$reportNull$$$0(14);
    }
    if (paramSupertypeLoopChecker == null) {
      $$$reportNull$$$0(15);
    }
    return new TypeParameterDescriptorImpl(paramDeclarationDescriptor, paramAnnotations, paramBoolean, paramVariance, paramName, paramInt, paramSourceElement, paramFunction1, paramSupertypeLoopChecker);
  }
  
  public static TypeParameterDescriptor createWithDefaultBound(DeclarationDescriptor paramDeclarationDescriptor, Annotations paramAnnotations, boolean paramBoolean, Variance paramVariance, Name paramName, int paramInt)
  {
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(0);
    }
    if (paramAnnotations == null) {
      $$$reportNull$$$0(1);
    }
    if (paramVariance == null) {
      $$$reportNull$$$0(2);
    }
    if (paramName == null) {
      $$$reportNull$$$0(3);
    }
    paramAnnotations = createForFurtherModification(paramDeclarationDescriptor, paramAnnotations, paramBoolean, paramVariance, paramName, paramInt, SourceElement.NO_SOURCE);
    paramAnnotations.addUpperBound(DescriptorUtilsKt.getBuiltIns(paramDeclarationDescriptor).getDefaultBound());
    paramAnnotations.setInitialized();
    if (paramAnnotations == null) {
      $$$reportNull$$$0(4);
    }
    return paramAnnotations;
  }
  
  private void doAddUpperBound(KotlinType paramKotlinType)
  {
    if (KotlinTypeKt.isError(paramKotlinType)) {
      return;
    }
    this.upperBounds.add(paramKotlinType);
  }
  
  private String nameForAssertions()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getName());
    localStringBuilder.append(" declared in ");
    localStringBuilder.append(DescriptorUtils.getFqName(getContainingDeclaration()));
    return localStringBuilder.toString();
  }
  
  public void addUpperBound(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(22);
    }
    checkUninitialized();
    doAddUpperBound(paramKotlinType);
  }
  
  protected void reportSupertypeLoopError(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(23);
    }
    Function1 localFunction1 = this.reportCycleError;
    if (localFunction1 == null) {
      return;
    }
    localFunction1.invoke(paramKotlinType);
  }
  
  protected List<KotlinType> resolveUpperBounds()
  {
    checkInitialized();
    List localList = this.upperBounds;
    if (localList == null) {
      $$$reportNull$$$0(24);
    }
    return localList;
  }
  
  public void setInitialized()
  {
    checkUninitialized();
    this.initialized = true;
  }
}

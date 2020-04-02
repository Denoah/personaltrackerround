package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import java.util.List;
import kotlin.Pair;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor.UserDataKey;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstUtil;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyGetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertySetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeEnhancementKt;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public class JavaPropertyDescriptor
  extends PropertyDescriptorImpl
  implements JavaCallableMemberDescriptor
{
  private final boolean isStaticFinal;
  private final Pair<CallableDescriptor.UserDataKey<?>, ?> singleUserData;
  
  protected JavaPropertyDescriptor(DeclarationDescriptor paramDeclarationDescriptor, Annotations paramAnnotations, Modality paramModality, Visibility paramVisibility, boolean paramBoolean1, Name paramName, SourceElement paramSourceElement, PropertyDescriptor paramPropertyDescriptor, CallableMemberDescriptor.Kind paramKind, boolean paramBoolean2, Pair<CallableDescriptor.UserDataKey<?>, ?> paramPair)
  {
    super(paramDeclarationDescriptor, paramPropertyDescriptor, paramAnnotations, paramModality, paramVisibility, paramBoolean1, paramName, paramKind, paramSourceElement, false, false, false, false, false, false);
    this.isStaticFinal = paramBoolean2;
    this.singleUserData = paramPair;
  }
  
  public static JavaPropertyDescriptor create(DeclarationDescriptor paramDeclarationDescriptor, Annotations paramAnnotations, Modality paramModality, Visibility paramVisibility, boolean paramBoolean1, Name paramName, SourceElement paramSourceElement, boolean paramBoolean2)
  {
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(7);
    }
    if (paramAnnotations == null) {
      $$$reportNull$$$0(8);
    }
    if (paramModality == null) {
      $$$reportNull$$$0(9);
    }
    if (paramVisibility == null) {
      $$$reportNull$$$0(10);
    }
    if (paramName == null) {
      $$$reportNull$$$0(11);
    }
    if (paramSourceElement == null) {
      $$$reportNull$$$0(12);
    }
    return new JavaPropertyDescriptor(paramDeclarationDescriptor, paramAnnotations, paramModality, paramVisibility, paramBoolean1, paramName, paramSourceElement, null, CallableMemberDescriptor.Kind.DECLARATION, paramBoolean2, null);
  }
  
  protected PropertyDescriptorImpl createSubstitutedCopy(DeclarationDescriptor paramDeclarationDescriptor, Modality paramModality, Visibility paramVisibility, PropertyDescriptor paramPropertyDescriptor, CallableMemberDescriptor.Kind paramKind, Name paramName, SourceElement paramSourceElement)
  {
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(13);
    }
    if (paramModality == null) {
      $$$reportNull$$$0(14);
    }
    if (paramVisibility == null) {
      $$$reportNull$$$0(15);
    }
    if (paramKind == null) {
      $$$reportNull$$$0(16);
    }
    if (paramName == null) {
      $$$reportNull$$$0(17);
    }
    if (paramSourceElement == null) {
      $$$reportNull$$$0(18);
    }
    return new JavaPropertyDescriptor(paramDeclarationDescriptor, getAnnotations(), paramModality, paramVisibility, isVar(), paramName, paramSourceElement, paramPropertyDescriptor, paramKind, this.isStaticFinal, this.singleUserData);
  }
  
  public JavaCallableMemberDescriptor enhance(KotlinType paramKotlinType1, List<ValueParameterData> paramList, KotlinType paramKotlinType2, Pair<CallableDescriptor.UserDataKey<?>, ?> paramPair)
  {
    if (paramList == null) {
      $$$reportNull$$$0(19);
    }
    if (paramKotlinType2 == null) {
      $$$reportNull$$$0(20);
    }
    if (getOriginal() == this) {
      paramList = null;
    } else {
      paramList = getOriginal();
    }
    JavaPropertyDescriptor localJavaPropertyDescriptor = new JavaPropertyDescriptor(getContainingDeclaration(), getAnnotations(), getModality(), getVisibility(), isVar(), getName(), getSource(), paramList, getKind(), this.isStaticFinal, paramPair);
    Object localObject1 = getGetter();
    Object localObject2;
    Object localObject3;
    Object localObject4;
    boolean bool1;
    boolean bool2;
    boolean bool3;
    Object localObject5;
    if (localObject1 != null)
    {
      localObject2 = ((PropertyGetterDescriptorImpl)localObject1).getAnnotations();
      localObject3 = ((PropertyGetterDescriptorImpl)localObject1).getModality();
      localObject4 = ((PropertyGetterDescriptorImpl)localObject1).getVisibility();
      bool1 = ((PropertyGetterDescriptorImpl)localObject1).isDefault();
      bool2 = ((PropertyGetterDescriptorImpl)localObject1).isExternal();
      bool3 = ((PropertyGetterDescriptorImpl)localObject1).isInline();
      localObject5 = getKind();
      if (paramList == null) {
        paramPair = null;
      } else {
        paramPair = paramList.getGetter();
      }
      paramPair = new PropertyGetterDescriptorImpl(localJavaPropertyDescriptor, (Annotations)localObject2, (Modality)localObject3, (Visibility)localObject4, bool1, bool2, bool3, (CallableMemberDescriptor.Kind)localObject5, paramPair, ((PropertyGetterDescriptorImpl)localObject1).getSource());
      paramPair.setInitialSignatureDescriptor(((PropertyGetterDescriptorImpl)localObject1).getInitialSignatureDescriptor());
      paramPair.initialize(paramKotlinType2);
    }
    else
    {
      paramPair = null;
    }
    localObject1 = getSetter();
    if (localObject1 != null)
    {
      localObject4 = ((PropertySetterDescriptor)localObject1).getAnnotations();
      localObject5 = ((PropertySetterDescriptor)localObject1).getModality();
      localObject3 = ((PropertySetterDescriptor)localObject1).getVisibility();
      bool1 = ((PropertySetterDescriptor)localObject1).isDefault();
      bool2 = ((PropertySetterDescriptor)localObject1).isExternal();
      bool3 = ((PropertySetterDescriptor)localObject1).isInline();
      localObject2 = getKind();
      if (paramList == null) {
        paramList = null;
      } else {
        paramList = paramList.getSetter();
      }
      paramList = new PropertySetterDescriptorImpl(localJavaPropertyDescriptor, (Annotations)localObject4, (Modality)localObject5, (Visibility)localObject3, bool1, bool2, bool3, (CallableMemberDescriptor.Kind)localObject2, paramList, ((PropertySetterDescriptor)localObject1).getSource());
      paramList.setInitialSignatureDescriptor(paramList.getInitialSignatureDescriptor());
      paramList.initialize((ValueParameterDescriptor)((PropertySetterDescriptor)localObject1).getValueParameters().get(0));
    }
    else
    {
      paramList = null;
    }
    localJavaPropertyDescriptor.initialize(paramPair, paramList, getBackingField(), getDelegateField());
    localJavaPropertyDescriptor.setSetterProjectedOut(isSetterProjectedOut());
    if (this.compileTimeInitializer != null) {
      localJavaPropertyDescriptor.setCompileTimeInitializer(this.compileTimeInitializer);
    }
    localJavaPropertyDescriptor.setOverriddenDescriptors(getOverriddenDescriptors());
    if (paramKotlinType1 == null) {
      paramKotlinType1 = null;
    } else {
      paramKotlinType1 = DescriptorFactory.createExtensionReceiverParameterForCallable(this, paramKotlinType1, Annotations.Companion.getEMPTY());
    }
    localJavaPropertyDescriptor.setType(paramKotlinType2, getTypeParameters(), getDispatchReceiverParameter(), paramKotlinType1);
    return localJavaPropertyDescriptor;
  }
  
  public <V> V getUserData(CallableDescriptor.UserDataKey<V> paramUserDataKey)
  {
    Pair localPair = this.singleUserData;
    if ((localPair != null) && (((CallableDescriptor.UserDataKey)localPair.getFirst()).equals(paramUserDataKey))) {
      return this.singleUserData.getSecond();
    }
    return null;
  }
  
  public boolean hasSynthesizedParameterNames()
  {
    return false;
  }
  
  public boolean isConst()
  {
    KotlinType localKotlinType = getType();
    boolean bool;
    if ((this.isStaticFinal) && (ConstUtil.canBeUsedForConstVal(localKotlinType)) && ((!TypeEnhancementKt.hasEnhancedNullability(localKotlinType)) || (KotlinBuiltIns.isString(localKotlinType)))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}

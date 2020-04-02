package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.reflect.jvm.internal.impl.builtins.CompanionObjectMapping;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FieldDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;

public final class JvmAbi
{
  public static final FqName JVM_FIELD_ANNOTATION_FQ_NAME = new FqName("kotlin.jvm.JvmField");
  public static final ClassId REFLECTION_FACTORY_IMPL = ClassId.topLevel(new FqName("kotlin.reflect.jvm.internal.ReflectionFactoryImpl"));
  
  public static String getterName(String paramString)
  {
    if (paramString == null) {
      $$$reportNull$$$0(6);
    }
    if (!startsWithIsPrefix(paramString))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("get");
      localStringBuilder.append(CapitalizeDecapitalizeKt.capitalizeAsciiOnly(paramString));
      paramString = localStringBuilder.toString();
    }
    if (paramString == null) {
      $$$reportNull$$$0(7);
    }
    return paramString;
  }
  
  public static boolean hasJvmFieldAnnotation(CallableMemberDescriptor paramCallableMemberDescriptor)
  {
    if (paramCallableMemberDescriptor == null) {
      $$$reportNull$$$0(13);
    }
    if ((paramCallableMemberDescriptor instanceof PropertyDescriptor))
    {
      FieldDescriptor localFieldDescriptor = ((PropertyDescriptor)paramCallableMemberDescriptor).getBackingField();
      if ((localFieldDescriptor != null) && (localFieldDescriptor.getAnnotations().hasAnnotation(JVM_FIELD_ANNOTATION_FQ_NAME))) {
        return true;
      }
    }
    return paramCallableMemberDescriptor.getAnnotations().hasAnnotation(JVM_FIELD_ANNOTATION_FQ_NAME);
  }
  
  public static boolean isClassCompanionObjectWithBackingFieldsInOuter(DeclarationDescriptor paramDeclarationDescriptor)
  {
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(11);
    }
    boolean bool;
    if ((DescriptorUtils.isCompanionObject(paramDeclarationDescriptor)) && (DescriptorUtils.isClassOrEnumClass(paramDeclarationDescriptor.getContainingDeclaration())) && (!isMappedIntrinsicCompanionObject((ClassDescriptor)paramDeclarationDescriptor))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isGetterName(String paramString)
  {
    if (paramString == null) {
      $$$reportNull$$$0(4);
    }
    boolean bool;
    if ((!paramString.startsWith("get")) && (!paramString.startsWith("is"))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static boolean isMappedIntrinsicCompanionObject(ClassDescriptor paramClassDescriptor)
  {
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(12);
    }
    return CompanionObjectMapping.INSTANCE.isMappedIntrinsicCompanionObject(paramClassDescriptor);
  }
  
  public static boolean isPropertyWithBackingFieldInOuterClass(PropertyDescriptor paramPropertyDescriptor)
  {
    if (paramPropertyDescriptor == null) {
      $$$reportNull$$$0(10);
    }
    CallableMemberDescriptor.Kind localKind1 = paramPropertyDescriptor.getKind();
    CallableMemberDescriptor.Kind localKind2 = CallableMemberDescriptor.Kind.FAKE_OVERRIDE;
    boolean bool1 = false;
    if (localKind1 == localKind2) {
      return false;
    }
    if (isClassCompanionObjectWithBackingFieldsInOuter(paramPropertyDescriptor.getContainingDeclaration())) {
      return true;
    }
    boolean bool2 = bool1;
    if (DescriptorUtils.isCompanionObject(paramPropertyDescriptor.getContainingDeclaration()))
    {
      bool2 = bool1;
      if (hasJvmFieldAnnotation(paramPropertyDescriptor)) {
        bool2 = true;
      }
    }
    return bool2;
  }
  
  public static boolean isSetterName(String paramString)
  {
    if (paramString == null) {
      $$$reportNull$$$0(5);
    }
    return paramString.startsWith("set");
  }
  
  public static String setterName(String paramString)
  {
    if (paramString == null) {
      $$$reportNull$$$0(8);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("set");
    if (startsWithIsPrefix(paramString)) {
      paramString = paramString.substring(2);
    } else {
      paramString = CapitalizeDecapitalizeKt.capitalizeAsciiOnly(paramString);
    }
    localStringBuilder.append(paramString);
    paramString = localStringBuilder.toString();
    if (paramString == null) {
      $$$reportNull$$$0(9);
    }
    return paramString;
  }
  
  public static boolean startsWithIsPrefix(String paramString)
  {
    boolean bool1 = paramString.startsWith("is");
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    if (paramString.length() == 2) {
      return false;
    }
    int i = paramString.charAt(2);
    if ((97 > i) || (i > 122)) {
      bool2 = true;
    }
    return bool2;
  }
}

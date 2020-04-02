package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;

public final class JavaForKotlinOverridePropertyDescriptor
  extends JavaPropertyDescriptor
{
  public JavaForKotlinOverridePropertyDescriptor(ClassDescriptor paramClassDescriptor, SimpleFunctionDescriptor paramSimpleFunctionDescriptor1, SimpleFunctionDescriptor paramSimpleFunctionDescriptor2, PropertyDescriptor paramPropertyDescriptor)
  {
    super(localDeclarationDescriptor, localAnnotations, paramClassDescriptor, localVisibility, bool, paramPropertyDescriptor.getName(), paramSimpleFunctionDescriptor1.getSource(), null, CallableMemberDescriptor.Kind.DECLARATION, false, null);
  }
}

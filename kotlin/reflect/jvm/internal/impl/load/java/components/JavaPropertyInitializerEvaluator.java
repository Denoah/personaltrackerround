package kotlin.reflect.jvm.internal.impl.load.java.components;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaField;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;

public abstract interface JavaPropertyInitializerEvaluator
{
  public abstract ConstantValue<?> getInitializerConstant(JavaField paramJavaField, PropertyDescriptor paramPropertyDescriptor);
  
  public static final class DoNothing
    implements JavaPropertyInitializerEvaluator
  {
    public static final DoNothing INSTANCE = new DoNothing();
    
    private DoNothing() {}
    
    public ConstantValue<?> getInitializerConstant(JavaField paramJavaField, PropertyDescriptor paramPropertyDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramJavaField, "field");
      Intrinsics.checkParameterIsNotNull(paramPropertyDescriptor, "descriptor");
      return null;
    }
  }
}

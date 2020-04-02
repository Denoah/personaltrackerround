package kotlin.reflect.jvm.internal.impl.load.java.components;

import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaElement;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaField;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMethod;
import kotlin.reflect.jvm.internal.impl.name.FqName;

public abstract interface JavaResolverCache
{
  public static final JavaResolverCache EMPTY = new JavaResolverCache()
  {
    public ClassDescriptor getClassResolvedFromSource(FqName paramAnonymousFqName)
    {
      if (paramAnonymousFqName == null) {
        $$$reportNull$$$0(0);
      }
      return null;
    }
    
    public void recordClass(JavaClass paramAnonymousJavaClass, ClassDescriptor paramAnonymousClassDescriptor)
    {
      if (paramAnonymousJavaClass == null) {
        $$$reportNull$$$0(7);
      }
      if (paramAnonymousClassDescriptor == null) {
        $$$reportNull$$$0(8);
      }
    }
    
    public void recordConstructor(JavaElement paramAnonymousJavaElement, ConstructorDescriptor paramAnonymousConstructorDescriptor)
    {
      if (paramAnonymousJavaElement == null) {
        $$$reportNull$$$0(3);
      }
      if (paramAnonymousConstructorDescriptor == null) {
        $$$reportNull$$$0(4);
      }
    }
    
    public void recordField(JavaField paramAnonymousJavaField, PropertyDescriptor paramAnonymousPropertyDescriptor)
    {
      if (paramAnonymousJavaField == null) {
        $$$reportNull$$$0(5);
      }
      if (paramAnonymousPropertyDescriptor == null) {
        $$$reportNull$$$0(6);
      }
    }
    
    public void recordMethod(JavaMethod paramAnonymousJavaMethod, SimpleFunctionDescriptor paramAnonymousSimpleFunctionDescriptor)
    {
      if (paramAnonymousJavaMethod == null) {
        $$$reportNull$$$0(1);
      }
      if (paramAnonymousSimpleFunctionDescriptor == null) {
        $$$reportNull$$$0(2);
      }
    }
  };
  
  public abstract ClassDescriptor getClassResolvedFromSource(FqName paramFqName);
  
  public abstract void recordClass(JavaClass paramJavaClass, ClassDescriptor paramClassDescriptor);
  
  public abstract void recordConstructor(JavaElement paramJavaElement, ConstructorDescriptor paramConstructorDescriptor);
  
  public abstract void recordField(JavaField paramJavaField, PropertyDescriptor paramPropertyDescriptor);
  
  public abstract void recordMethod(JavaMethod paramJavaMethod, SimpleFunctionDescriptor paramSimpleFunctionDescriptor);
}

package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameter;

public abstract interface TypeParameterResolver
{
  public abstract TypeParameterDescriptor resolveTypeParameter(JavaTypeParameter paramJavaTypeParameter);
  
  public static final class EMPTY
    implements TypeParameterResolver
  {
    public static final EMPTY INSTANCE = new EMPTY();
    
    private EMPTY() {}
    
    public TypeParameterDescriptor resolveTypeParameter(JavaTypeParameter paramJavaTypeParameter)
    {
      Intrinsics.checkParameterIsNotNull(paramJavaTypeParameter, "javaTypeParameter");
      return null;
    }
  }
}

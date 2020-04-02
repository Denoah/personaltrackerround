package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.List;

public abstract interface JavaMethod
  extends JavaMember, JavaTypeParameterListOwner
{
  public abstract JavaAnnotationArgument getAnnotationParameterDefaultValue();
  
  public abstract boolean getHasAnnotationParameterDefaultValue();
  
  public abstract JavaType getReturnType();
  
  public abstract List<JavaValueParameter> getValueParameters();
  
  public static final class DefaultImpls
  {
    public static boolean getHasAnnotationParameterDefaultValue(JavaMethod paramJavaMethod)
    {
      boolean bool;
      if (paramJavaMethod.getAnnotationParameterDefaultValue() != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
}

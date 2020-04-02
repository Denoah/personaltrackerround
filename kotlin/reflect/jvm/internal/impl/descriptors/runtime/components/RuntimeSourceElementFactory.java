package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceFile;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaElement;
import kotlin.reflect.jvm.internal.impl.load.java.sources.JavaSourceElement;
import kotlin.reflect.jvm.internal.impl.load.java.sources.JavaSourceElementFactory;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaElement;

public final class RuntimeSourceElementFactory
  implements JavaSourceElementFactory
{
  public static final RuntimeSourceElementFactory INSTANCE = new RuntimeSourceElementFactory();
  
  private RuntimeSourceElementFactory() {}
  
  public JavaSourceElement source(JavaElement paramJavaElement)
  {
    Intrinsics.checkParameterIsNotNull(paramJavaElement, "javaElement");
    return (JavaSourceElement)new RuntimeSourceElement((ReflectJavaElement)paramJavaElement);
  }
  
  public static final class RuntimeSourceElement
    implements JavaSourceElement
  {
    private final ReflectJavaElement javaElement;
    
    public RuntimeSourceElement(ReflectJavaElement paramReflectJavaElement)
    {
      this.javaElement = paramReflectJavaElement;
    }
    
    public SourceFile getContainingFile()
    {
      SourceFile localSourceFile = SourceFile.NO_SOURCE_FILE;
      Intrinsics.checkExpressionValueIsNotNull(localSourceFile, "SourceFile.NO_SOURCE_FILE");
      return localSourceFile;
    }
    
    public ReflectJavaElement getJavaElement()
    {
      return this.javaElement;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(getClass().getName());
      localStringBuilder.append(": ");
      localStringBuilder.append(getJavaElement().toString());
      return localStringBuilder.toString();
    }
  }
}

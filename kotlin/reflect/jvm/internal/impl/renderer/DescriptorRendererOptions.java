package kotlin.reflect.jvm.internal.impl.renderer;

import java.util.Set;
import kotlin.reflect.jvm.internal.impl.name.FqName;

public abstract interface DescriptorRendererOptions
{
  public abstract AnnotationArgumentsRenderingPolicy getAnnotationArgumentsRenderingPolicy();
  
  public abstract boolean getDebugMode();
  
  public abstract boolean getEnhancedTypes();
  
  public abstract Set<FqName> getExcludedTypeAnnotationClasses();
  
  public abstract void setAnnotationArgumentsRenderingPolicy(AnnotationArgumentsRenderingPolicy paramAnnotationArgumentsRenderingPolicy);
  
  public abstract void setClassifierNamePolicy(ClassifierNamePolicy paramClassifierNamePolicy);
  
  public abstract void setDebugMode(boolean paramBoolean);
  
  public abstract void setExcludedTypeAnnotationClasses(Set<FqName> paramSet);
  
  public abstract void setModifiers(Set<? extends DescriptorRendererModifier> paramSet);
  
  public abstract void setParameterNameRenderingPolicy(ParameterNameRenderingPolicy paramParameterNameRenderingPolicy);
  
  public abstract void setReceiverAfterName(boolean paramBoolean);
  
  public abstract void setRenderCompanionObjectName(boolean paramBoolean);
  
  public abstract void setStartFromName(boolean paramBoolean);
  
  public abstract void setTextFormat(RenderingFormat paramRenderingFormat);
  
  public abstract void setVerbose(boolean paramBoolean);
  
  public abstract void setWithDefinedIn(boolean paramBoolean);
  
  public abstract void setWithoutSuperTypes(boolean paramBoolean);
  
  public abstract void setWithoutTypeParameters(boolean paramBoolean);
  
  public static final class DefaultImpls
  {
    public static boolean getIncludeAnnotationArguments(DescriptorRendererOptions paramDescriptorRendererOptions)
    {
      return paramDescriptorRendererOptions.getAnnotationArgumentsRenderingPolicy().getIncludeAnnotationArguments();
    }
    
    public static boolean getIncludeEmptyAnnotationArguments(DescriptorRendererOptions paramDescriptorRendererOptions)
    {
      return paramDescriptorRendererOptions.getAnnotationArgumentsRenderingPolicy().getIncludeEmptyAnnotationArguments();
    }
  }
}

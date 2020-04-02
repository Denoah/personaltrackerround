package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.List;

public final class PredefinedFunctionEnhancementInfo
{
  private final List<TypeEnhancementInfo> parametersInfo;
  private final TypeEnhancementInfo returnTypeInfo;
  
  public PredefinedFunctionEnhancementInfo()
  {
    this(null, null, 3, null);
  }
  
  public PredefinedFunctionEnhancementInfo(TypeEnhancementInfo paramTypeEnhancementInfo, List<TypeEnhancementInfo> paramList)
  {
    this.returnTypeInfo = paramTypeEnhancementInfo;
    this.parametersInfo = paramList;
  }
  
  public final List<TypeEnhancementInfo> getParametersInfo()
  {
    return this.parametersInfo;
  }
  
  public final TypeEnhancementInfo getReturnTypeInfo()
  {
    return this.returnTypeInfo;
  }
}

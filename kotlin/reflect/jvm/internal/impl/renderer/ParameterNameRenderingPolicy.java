package kotlin.reflect.jvm.internal.impl.renderer;

public enum ParameterNameRenderingPolicy
{
  static
  {
    ParameterNameRenderingPolicy localParameterNameRenderingPolicy1 = new ParameterNameRenderingPolicy("ALL", 0);
    ALL = localParameterNameRenderingPolicy1;
    ParameterNameRenderingPolicy localParameterNameRenderingPolicy2 = new ParameterNameRenderingPolicy("ONLY_NON_SYNTHESIZED", 1);
    ONLY_NON_SYNTHESIZED = localParameterNameRenderingPolicy2;
    ParameterNameRenderingPolicy localParameterNameRenderingPolicy3 = new ParameterNameRenderingPolicy("NONE", 2);
    NONE = localParameterNameRenderingPolicy3;
    $VALUES = new ParameterNameRenderingPolicy[] { localParameterNameRenderingPolicy1, localParameterNameRenderingPolicy2, localParameterNameRenderingPolicy3 };
  }
  
  private ParameterNameRenderingPolicy() {}
}

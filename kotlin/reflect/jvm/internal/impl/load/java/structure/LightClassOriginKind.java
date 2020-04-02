package kotlin.reflect.jvm.internal.impl.load.java.structure;

public enum LightClassOriginKind
{
  static
  {
    LightClassOriginKind localLightClassOriginKind1 = new LightClassOriginKind("SOURCE", 0);
    SOURCE = localLightClassOriginKind1;
    LightClassOriginKind localLightClassOriginKind2 = new LightClassOriginKind("BINARY", 1);
    BINARY = localLightClassOriginKind2;
    $VALUES = new LightClassOriginKind[] { localLightClassOriginKind1, localLightClassOriginKind2 };
  }
  
  private LightClassOriginKind() {}
}

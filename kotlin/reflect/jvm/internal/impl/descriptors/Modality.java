package kotlin.reflect.jvm.internal.impl.descriptors;

public enum Modality
{
  public static final Companion Companion = new Companion(null);
  
  static
  {
    Modality localModality1 = new Modality("FINAL", 0);
    FINAL = localModality1;
    Modality localModality2 = new Modality("SEALED", 1);
    SEALED = localModality2;
    Modality localModality3 = new Modality("OPEN", 2);
    OPEN = localModality3;
    Modality localModality4 = new Modality("ABSTRACT", 3);
    ABSTRACT = localModality4;
    $VALUES = new Modality[] { localModality1, localModality2, localModality3, localModality4 };
  }
  
  private Modality() {}
  
  public static final class Companion
  {
    private Companion() {}
    
    public final Modality convertFromFlags(boolean paramBoolean1, boolean paramBoolean2)
    {
      if (paramBoolean1) {
        return Modality.ABSTRACT;
      }
      if (paramBoolean2) {
        return Modality.OPEN;
      }
      return Modality.FINAL;
    }
  }
}

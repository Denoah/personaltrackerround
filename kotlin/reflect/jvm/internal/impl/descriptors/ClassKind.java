package kotlin.reflect.jvm.internal.impl.descriptors;

public enum ClassKind
{
  static
  {
    ENUM_CLASS = new ClassKind("ENUM_CLASS", 2);
    ENUM_ENTRY = new ClassKind("ENUM_ENTRY", 3);
    ANNOTATION_CLASS = new ClassKind("ANNOTATION_CLASS", 4);
    ClassKind localClassKind = new ClassKind("OBJECT", 5);
    OBJECT = localClassKind;
    $VALUES = new ClassKind[] { CLASS, INTERFACE, ENUM_CLASS, ENUM_ENTRY, ANNOTATION_CLASS, localClassKind };
  }
  
  private ClassKind() {}
  
  public boolean isSingleton()
  {
    boolean bool;
    if ((this != OBJECT) && (this != ENUM_ENTRY)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
}

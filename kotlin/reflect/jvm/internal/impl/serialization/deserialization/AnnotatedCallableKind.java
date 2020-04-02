package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

public enum AnnotatedCallableKind
{
  static
  {
    AnnotatedCallableKind localAnnotatedCallableKind = new AnnotatedCallableKind("PROPERTY_SETTER", 3);
    PROPERTY_SETTER = localAnnotatedCallableKind;
    $VALUES = new AnnotatedCallableKind[] { FUNCTION, PROPERTY, PROPERTY_GETTER, localAnnotatedCallableKind };
  }
  
  private AnnotatedCallableKind() {}
}

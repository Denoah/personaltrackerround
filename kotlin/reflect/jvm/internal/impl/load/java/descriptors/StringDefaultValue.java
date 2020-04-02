package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

public final class StringDefaultValue
  extends AnnotationDefaultValue
{
  private final String value;
  
  public StringDefaultValue(String paramString)
  {
    super(null);
    this.value = paramString;
  }
  
  public final String getValue()
  {
    return this.value;
  }
}

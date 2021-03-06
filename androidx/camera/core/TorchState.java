package androidx.camera.core;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TorchState
{
  public static final int OFF = 0;
  public static final int ON = 1;
  
  private TorchState() {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface State {}
}

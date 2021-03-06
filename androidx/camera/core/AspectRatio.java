package androidx.camera.core;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AspectRatio
{
  public static final int RATIO_16_9 = 1;
  public static final int RATIO_4_3 = 0;
  
  private AspectRatio() {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Ratio {}
}

package com.google.auto.value;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface AutoValue
{
  @Retention(RetentionPolicy.CLASS)
  @Target({java.lang.annotation.ElementType.TYPE})
  public static @interface Builder {}
  
  @Retention(RetentionPolicy.CLASS)
  @Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.METHOD})
  public static @interface CopyAnnotations
  {
    Class<? extends Annotation>[] exclude() default {};
  }
}

package dagger;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface Component
{
  Class<?>[] dependencies() default {};
  
  Class<?>[] modules() default {};
  
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target({java.lang.annotation.ElementType.TYPE})
  public static @interface Builder {}
  
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target({java.lang.annotation.ElementType.TYPE})
  public static @interface Factory {}
}

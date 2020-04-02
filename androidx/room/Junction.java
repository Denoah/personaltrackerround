package androidx.room;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({})
public @interface Junction
{
  String entityColumn() default "";
  
  String parentColumn() default "";
  
  Class<?> value();
}

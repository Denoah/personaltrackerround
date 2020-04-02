package androidx.room;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD})
public @interface Relation
{
  Junction associateBy() default @Junction(Object.class);
  
  Class<?> entity() default Object.class;
  
  String entityColumn();
  
  String parentColumn();
  
  String[] projection() default {};
}

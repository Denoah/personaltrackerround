package kotlin;

import java.lang.annotation.Annotation;
import java.lang.annotation.RetentionPolicy;
import kotlin.annotation.AnnotationRetention;

@java.lang.annotation.Retention(RetentionPolicy.CLASS)
@java.lang.annotation.Target({java.lang.annotation.ElementType.ANNOTATION_TYPE})
@Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\020\033\n\000\n\002\020\016\n\000\n\002\030\002\n\002\b\004\b?\002\030\0002\0020\001:\001\bB\024\022\b\b\002\020\002\032\0020\003\022\b\b\002\020\004\032\0020\005R\017\020\004\032\0020\005?\006\006\032\004\b\004\020\006R\017\020\002\032\0020\003?\006\006\032\004\b\002\020\007?\001\000?\002\007\n\005\b?F0\001?\006\t"}, d2={"Lkotlin/RequiresOptIn;", "", "message", "", "level", "Lkotlin/RequiresOptIn$Level;", "()Lkotlin/RequiresOptIn$Level;", "()Ljava/lang/String;", "Level", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
@kotlin.annotation.Target(allowedTargets={kotlin.annotation.AnnotationTarget.ANNOTATION_CLASS})
public @interface RequiresOptIn
{
  Level level() default Level.ERROR;
  
  String message() default "";
  
  @Metadata(bv={1, 0, 3}, d1={"\000\f\n\002\030\002\n\002\020\020\n\002\b\004\b?\001\030\0002\b\022\004\022\0020\0000\001B\007\b\002?\006\002\020\002j\002\b\003j\002\b\004?\006\005"}, d2={"Lkotlin/RequiresOptIn$Level;", "", "(Ljava/lang/String;I)V", "WARNING", "ERROR", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
  public static enum Level
  {
    static
    {
      Level localLevel1 = new Level("WARNING", 0);
      WARNING = localLevel1;
      Level localLevel2 = new Level("ERROR", 1);
      ERROR = localLevel2;
      $VALUES = new Level[] { localLevel1, localLevel2 };
    }
    
    private Level() {}
  }
}

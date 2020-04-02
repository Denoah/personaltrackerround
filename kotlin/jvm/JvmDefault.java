package kotlin.jvm;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;

@Retention(RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD})
@Metadata(bv={1, 0, 3}, d1={"\000\n\n\002\030\002\n\002\020\033\n\000\b?\002\030\0002\0020\001B\000?\001\000?\002\007\n\005\b?(0\001?\006\002"}, d2={"Lkotlin/jvm/JvmDefault;", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
@kotlin.annotation.Target(allowedTargets={kotlin.annotation.AnnotationTarget.FUNCTION, kotlin.annotation.AnnotationTarget.PROPERTY})
public @interface JvmDefault {}

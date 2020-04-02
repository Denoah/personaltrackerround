package kotlin.jvm;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.MustBeDocumented;

@Documented
@java.lang.annotation.Retention(RetentionPolicy.SOURCE)
@java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD})
@Metadata(bv={1, 0, 3}, d1={"\000\n\n\002\030\002\n\002\020\033\n\000\b?\002\030\0002\0020\001B\000?\006\002"}, d2={"Lkotlin/jvm/Volatile;", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@kotlin.annotation.Target(allowedTargets={kotlin.annotation.AnnotationTarget.FIELD})
public @interface Volatile {}

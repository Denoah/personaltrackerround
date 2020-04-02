package kotlin.reflect.jvm.internal;

import kotlin.Metadata;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\b\003\b`\030\0002\0020\001R\022\020\002\032\0020\003X¦\004?\006\006\032\004\b\004\020\005?\006\006"}, d2={"Lkotlin/reflect/jvm/internal/KClassifierImpl;", "", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/ClassifierDescriptor;", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
public abstract interface KClassifierImpl
{
  public abstract ClassifierDescriptor getDescriptor();
}

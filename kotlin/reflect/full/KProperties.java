package kotlin.reflect.full;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty1;
import kotlin.reflect.KProperty2;
import kotlin.reflect.jvm.internal.KPropertyImpl;
import kotlin.reflect.jvm.internal.KPropertyImpl.Companion;

@Metadata(bv={1, 0, 3}, d1={"\000\024\n\000\n\002\020\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\032\026\020\000\032\004\030\0010\001*\n\022\002\b\003\022\002\b\0030\002H\007\032/\020\000\032\004\030\0010\001\"\004\b\000\020\003*\020\022\004\022\002H\003\022\002\b\003\022\002\b\0030\0042\006\020\005\032\002H\003H\007?\006\002\020\006?\006\007"}, d2={"getExtensionDelegate", "", "Lkotlin/reflect/KProperty1;", "D", "Lkotlin/reflect/KProperty2;", "receiver", "(Lkotlin/reflect/KProperty2;Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"}, k=2, mv={1, 1, 15})
public final class KProperties
{
  public static final Object getExtensionDelegate(KProperty1<?, ?> paramKProperty1)
  {
    Intrinsics.checkParameterIsNotNull(paramKProperty1, "$this$getExtensionDelegate");
    return paramKProperty1.getDelegate(KPropertyImpl.Companion.getEXTENSION_PROPERTY_DELEGATE());
  }
  
  public static final <D> Object getExtensionDelegate(KProperty2<D, ?, ?> paramKProperty2, D paramD)
  {
    Intrinsics.checkParameterIsNotNull(paramKProperty2, "$this$getExtensionDelegate");
    return paramKProperty2.getDelegate(paramD, KPropertyImpl.Companion.getEXTENSION_PROPERTY_DELEGATE());
  }
}

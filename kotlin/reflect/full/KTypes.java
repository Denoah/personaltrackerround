package kotlin.reflect.full;

import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.KType;
import kotlin.reflect.jvm.internal.KTypeImpl;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

@Metadata(bv={1, 0, 3}, d1={"\000\016\n\000\n\002\020\013\n\002\030\002\n\002\b\005\032\024\020\000\032\0020\001*\0020\0022\006\020\003\032\0020\002H\007\032\024\020\004\032\0020\001*\0020\0022\006\020\003\032\0020\002H\007\032\024\020\005\032\0020\002*\0020\0022\006\020\006\032\0020\001H\007?\006\007"}, d2={"isSubtypeOf", "", "Lkotlin/reflect/KType;", "other", "isSupertypeOf", "withNullability", "nullable", "kotlin-reflection"}, k=2, mv={1, 1, 15})
public final class KTypes
{
  public static final boolean isSubtypeOf(KType paramKType1, KType paramKType2)
  {
    Intrinsics.checkParameterIsNotNull(paramKType1, "$this$isSubtypeOf");
    Intrinsics.checkParameterIsNotNull(paramKType2, "other");
    return TypeUtilsKt.isSubtypeOf(((KTypeImpl)paramKType1).getType(), ((KTypeImpl)paramKType2).getType());
  }
  
  public static final boolean isSupertypeOf(KType paramKType1, KType paramKType2)
  {
    Intrinsics.checkParameterIsNotNull(paramKType1, "$this$isSupertypeOf");
    Intrinsics.checkParameterIsNotNull(paramKType2, "other");
    return isSubtypeOf(paramKType2, paramKType1);
  }
  
  public static final KType withNullability(KType paramKType, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramKType, "$this$withNullability");
    if (paramKType.isMarkedNullable())
    {
      if (!paramBoolean)
      {
        localKotlinType = TypeUtils.makeNotNullable(((KTypeImpl)paramKType).getType());
        Intrinsics.checkExpressionValueIsNotNull(localKotlinType, "TypeUtils.makeNotNullabl…(this as KTypeImpl).type)");
        paramKType = (KType)new KTypeImpl(localKotlinType, (Function0)new Lambda(paramKType)
        {
          public final Type invoke()
          {
            return ((KTypeImpl)this.$this_withNullability).getJavaType$kotlin_reflection();
          }
        });
      }
      return paramKType;
    }
    KotlinType localKotlinType = ((KTypeImpl)paramKType).getType();
    if (FlexibleTypesKt.isFlexible(localKotlinType))
    {
      localKotlinType = TypeUtils.makeNullableAsSpecified(localKotlinType, paramBoolean);
      Intrinsics.checkExpressionValueIsNotNull(localKotlinType, "TypeUtils.makeNullableAs…ied(kotlinType, nullable)");
      (KType)new KTypeImpl(localKotlinType, (Function0)new Lambda(paramKType)
      {
        public final Type invoke()
        {
          return ((KTypeImpl)this.$this_withNullability).getJavaType$kotlin_reflection();
        }
      });
    }
    if (paramBoolean)
    {
      localKotlinType = TypeUtils.makeNullable(localKotlinType);
      Intrinsics.checkExpressionValueIsNotNull(localKotlinType, "TypeUtils.makeNullable(kotlinType)");
      paramKType = (KType)new KTypeImpl(localKotlinType, (Function0)new Lambda(paramKType)
      {
        public final Type invoke()
        {
          return ((KTypeImpl)this.$this_withNullability).getJavaType$kotlin_reflection();
        }
      });
    }
    return paramKType;
  }
}

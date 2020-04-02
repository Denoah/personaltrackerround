package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeParameterMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext;

public abstract interface TypeSystemCommonBackendContext
  extends TypeSystemContext
{
  public abstract FqNameUnsafe getClassFqNameUnsafe(TypeConstructorMarker paramTypeConstructorMarker);
  
  public abstract PrimitiveType getPrimitiveArrayType(TypeConstructorMarker paramTypeConstructorMarker);
  
  public abstract PrimitiveType getPrimitiveType(TypeConstructorMarker paramTypeConstructorMarker);
  
  public abstract KotlinTypeMarker getRepresentativeUpperBound(TypeParameterMarker paramTypeParameterMarker);
  
  public abstract KotlinTypeMarker getSubstitutedUnderlyingType(KotlinTypeMarker paramKotlinTypeMarker);
  
  public abstract TypeParameterMarker getTypeParameterClassifier(TypeConstructorMarker paramTypeConstructorMarker);
  
  public abstract boolean hasAnnotation(KotlinTypeMarker paramKotlinTypeMarker, FqName paramFqName);
  
  public abstract boolean isInlineClass(TypeConstructorMarker paramTypeConstructorMarker);
  
  public abstract boolean isMarkedNullable(KotlinTypeMarker paramKotlinTypeMarker);
  
  public abstract boolean isUnderKotlinPackage(TypeConstructorMarker paramTypeConstructorMarker);
  
  public abstract KotlinTypeMarker makeNullable(KotlinTypeMarker paramKotlinTypeMarker);
  
  public static final class DefaultImpls
  {
    public static boolean isMarkedNullable(TypeSystemCommonBackendContext paramTypeSystemCommonBackendContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isMarkedNullable");
      boolean bool;
      if (((paramKotlinTypeMarker instanceof SimpleTypeMarker)) && (paramTypeSystemCommonBackendContext.isMarkedNullable((SimpleTypeMarker)paramKotlinTypeMarker))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public static KotlinTypeMarker makeNullable(TypeSystemCommonBackendContext paramTypeSystemCommonBackendContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$makeNullable");
      SimpleTypeMarker localSimpleTypeMarker = paramTypeSystemCommonBackendContext.asSimpleType(paramKotlinTypeMarker);
      KotlinTypeMarker localKotlinTypeMarker = paramKotlinTypeMarker;
      if (localSimpleTypeMarker != null)
      {
        paramTypeSystemCommonBackendContext = paramTypeSystemCommonBackendContext.withNullability(localSimpleTypeMarker, true);
        localKotlinTypeMarker = paramKotlinTypeMarker;
        if (paramTypeSystemCommonBackendContext != null) {
          localKotlinTypeMarker = (KotlinTypeMarker)paramTypeSystemCommonBackendContext;
        }
      }
      return localKotlinTypeMarker;
    }
  }
}

package kotlin.reflect.jvm.internal.impl.types.model;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;

public abstract interface TypeSystemInferenceExtensionContext
  extends TypeSystemCommonSuperTypesContext, TypeSystemContext
{
  public static final class DefaultImpls
  {
    public static List<SimpleTypeMarker> fastCorrespondingSupertypes(TypeSystemInferenceExtensionContext paramTypeSystemInferenceExtensionContext, SimpleTypeMarker paramSimpleTypeMarker, TypeConstructorMarker paramTypeConstructorMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$fastCorrespondingSupertypes");
      Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "constructor");
      return TypeSystemContext.DefaultImpls.fastCorrespondingSupertypes((TypeSystemContext)paramTypeSystemInferenceExtensionContext, paramSimpleTypeMarker, paramTypeConstructorMarker);
    }
    
    public static TypeArgumentMarker get(TypeSystemInferenceExtensionContext paramTypeSystemInferenceExtensionContext, TypeArgumentListMarker paramTypeArgumentListMarker, int paramInt)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeArgumentListMarker, "$this$get");
      return TypeSystemContext.DefaultImpls.get((TypeSystemContext)paramTypeSystemInferenceExtensionContext, paramTypeArgumentListMarker, paramInt);
    }
    
    public static TypeArgumentMarker getArgumentOrNull(TypeSystemInferenceExtensionContext paramTypeSystemInferenceExtensionContext, SimpleTypeMarker paramSimpleTypeMarker, int paramInt)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$getArgumentOrNull");
      return TypeSystemContext.DefaultImpls.getArgumentOrNull((TypeSystemContext)paramTypeSystemInferenceExtensionContext, paramSimpleTypeMarker, paramInt);
    }
    
    public static boolean hasFlexibleNullability(TypeSystemInferenceExtensionContext paramTypeSystemInferenceExtensionContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$hasFlexibleNullability");
      return TypeSystemContext.DefaultImpls.hasFlexibleNullability((TypeSystemContext)paramTypeSystemInferenceExtensionContext, paramKotlinTypeMarker);
    }
    
    public static boolean isClassType(TypeSystemInferenceExtensionContext paramTypeSystemInferenceExtensionContext, SimpleTypeMarker paramSimpleTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$isClassType");
      return TypeSystemContext.DefaultImpls.isClassType((TypeSystemContext)paramTypeSystemInferenceExtensionContext, paramSimpleTypeMarker);
    }
    
    public static boolean isDefinitelyNotNullType(TypeSystemInferenceExtensionContext paramTypeSystemInferenceExtensionContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isDefinitelyNotNullType");
      return TypeSystemContext.DefaultImpls.isDefinitelyNotNullType((TypeSystemContext)paramTypeSystemInferenceExtensionContext, paramKotlinTypeMarker);
    }
    
    public static boolean isDynamic(TypeSystemInferenceExtensionContext paramTypeSystemInferenceExtensionContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isDynamic");
      return TypeSystemContext.DefaultImpls.isDynamic((TypeSystemContext)paramTypeSystemInferenceExtensionContext, paramKotlinTypeMarker);
    }
    
    public static boolean isIntegerLiteralType(TypeSystemInferenceExtensionContext paramTypeSystemInferenceExtensionContext, SimpleTypeMarker paramSimpleTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$isIntegerLiteralType");
      return TypeSystemContext.DefaultImpls.isIntegerLiteralType((TypeSystemContext)paramTypeSystemInferenceExtensionContext, paramSimpleTypeMarker);
    }
    
    public static boolean isNothing(TypeSystemInferenceExtensionContext paramTypeSystemInferenceExtensionContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isNothing");
      return TypeSystemContext.DefaultImpls.isNothing((TypeSystemContext)paramTypeSystemInferenceExtensionContext, paramKotlinTypeMarker);
    }
    
    public static SimpleTypeMarker lowerBoundIfFlexible(TypeSystemInferenceExtensionContext paramTypeSystemInferenceExtensionContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$lowerBoundIfFlexible");
      return TypeSystemContext.DefaultImpls.lowerBoundIfFlexible((TypeSystemContext)paramTypeSystemInferenceExtensionContext, paramKotlinTypeMarker);
    }
    
    public static int size(TypeSystemInferenceExtensionContext paramTypeSystemInferenceExtensionContext, TypeArgumentListMarker paramTypeArgumentListMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeArgumentListMarker, "$this$size");
      return TypeSystemContext.DefaultImpls.size((TypeSystemContext)paramTypeSystemInferenceExtensionContext, paramTypeArgumentListMarker);
    }
    
    public static TypeConstructorMarker typeConstructor(TypeSystemInferenceExtensionContext paramTypeSystemInferenceExtensionContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$typeConstructor");
      return TypeSystemContext.DefaultImpls.typeConstructor((TypeSystemContext)paramTypeSystemInferenceExtensionContext, paramKotlinTypeMarker);
    }
    
    public static SimpleTypeMarker upperBoundIfFlexible(TypeSystemInferenceExtensionContext paramTypeSystemInferenceExtensionContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$upperBoundIfFlexible");
      return TypeSystemContext.DefaultImpls.upperBoundIfFlexible((TypeSystemContext)paramTypeSystemInferenceExtensionContext, paramKotlinTypeMarker);
    }
  }
}

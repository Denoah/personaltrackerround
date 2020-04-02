package kotlin.reflect.jvm.internal.impl.types.model;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

public abstract interface TypeSystemContext
  extends TypeSystemOptimizationContext
{
  public abstract int argumentsCount(KotlinTypeMarker paramKotlinTypeMarker);
  
  public abstract TypeArgumentListMarker asArgumentList(SimpleTypeMarker paramSimpleTypeMarker);
  
  public abstract CapturedTypeMarker asCapturedType(SimpleTypeMarker paramSimpleTypeMarker);
  
  public abstract DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker paramSimpleTypeMarker);
  
  public abstract DynamicTypeMarker asDynamicType(FlexibleTypeMarker paramFlexibleTypeMarker);
  
  public abstract FlexibleTypeMarker asFlexibleType(KotlinTypeMarker paramKotlinTypeMarker);
  
  public abstract SimpleTypeMarker asSimpleType(KotlinTypeMarker paramKotlinTypeMarker);
  
  public abstract TypeArgumentMarker asTypeArgument(KotlinTypeMarker paramKotlinTypeMarker);
  
  public abstract SimpleTypeMarker captureFromArguments(SimpleTypeMarker paramSimpleTypeMarker, CaptureStatus paramCaptureStatus);
  
  public abstract TypeArgumentMarker get(TypeArgumentListMarker paramTypeArgumentListMarker, int paramInt);
  
  public abstract TypeArgumentMarker getArgument(KotlinTypeMarker paramKotlinTypeMarker, int paramInt);
  
  public abstract TypeParameterMarker getParameter(TypeConstructorMarker paramTypeConstructorMarker, int paramInt);
  
  public abstract KotlinTypeMarker getType(TypeArgumentMarker paramTypeArgumentMarker);
  
  public abstract TypeVariance getVariance(TypeArgumentMarker paramTypeArgumentMarker);
  
  public abstract TypeVariance getVariance(TypeParameterMarker paramTypeParameterMarker);
  
  public abstract KotlinTypeMarker intersectTypes(List<? extends KotlinTypeMarker> paramList);
  
  public abstract boolean isAnyConstructor(TypeConstructorMarker paramTypeConstructorMarker);
  
  public abstract boolean isClassTypeConstructor(TypeConstructorMarker paramTypeConstructorMarker);
  
  public abstract boolean isCommonFinalClassConstructor(TypeConstructorMarker paramTypeConstructorMarker);
  
  public abstract boolean isDenotable(TypeConstructorMarker paramTypeConstructorMarker);
  
  public abstract boolean isEqualTypeConstructors(TypeConstructorMarker paramTypeConstructorMarker1, TypeConstructorMarker paramTypeConstructorMarker2);
  
  public abstract boolean isError(KotlinTypeMarker paramKotlinTypeMarker);
  
  public abstract boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker paramTypeConstructorMarker);
  
  public abstract boolean isIntersection(TypeConstructorMarker paramTypeConstructorMarker);
  
  public abstract boolean isMarkedNullable(SimpleTypeMarker paramSimpleTypeMarker);
  
  public abstract boolean isNothingConstructor(TypeConstructorMarker paramTypeConstructorMarker);
  
  public abstract boolean isNullableType(KotlinTypeMarker paramKotlinTypeMarker);
  
  public abstract boolean isPrimitiveType(SimpleTypeMarker paramSimpleTypeMarker);
  
  public abstract boolean isSingleClassifierType(SimpleTypeMarker paramSimpleTypeMarker);
  
  public abstract boolean isStarProjection(TypeArgumentMarker paramTypeArgumentMarker);
  
  public abstract boolean isStubType(SimpleTypeMarker paramSimpleTypeMarker);
  
  public abstract SimpleTypeMarker lowerBound(FlexibleTypeMarker paramFlexibleTypeMarker);
  
  public abstract SimpleTypeMarker lowerBoundIfFlexible(KotlinTypeMarker paramKotlinTypeMarker);
  
  public abstract KotlinTypeMarker lowerType(CapturedTypeMarker paramCapturedTypeMarker);
  
  public abstract int parametersCount(TypeConstructorMarker paramTypeConstructorMarker);
  
  public abstract Collection<KotlinTypeMarker> possibleIntegerTypes(SimpleTypeMarker paramSimpleTypeMarker);
  
  public abstract int size(TypeArgumentListMarker paramTypeArgumentListMarker);
  
  public abstract Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker paramTypeConstructorMarker);
  
  public abstract TypeConstructorMarker typeConstructor(KotlinTypeMarker paramKotlinTypeMarker);
  
  public abstract TypeConstructorMarker typeConstructor(SimpleTypeMarker paramSimpleTypeMarker);
  
  public abstract SimpleTypeMarker upperBound(FlexibleTypeMarker paramFlexibleTypeMarker);
  
  public abstract SimpleTypeMarker upperBoundIfFlexible(KotlinTypeMarker paramKotlinTypeMarker);
  
  public abstract SimpleTypeMarker withNullability(SimpleTypeMarker paramSimpleTypeMarker, boolean paramBoolean);
  
  public static final class DefaultImpls
  {
    public static List<SimpleTypeMarker> fastCorrespondingSupertypes(TypeSystemContext paramTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker, TypeConstructorMarker paramTypeConstructorMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$fastCorrespondingSupertypes");
      Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "constructor");
      return null;
    }
    
    public static TypeArgumentMarker get(TypeSystemContext paramTypeSystemContext, TypeArgumentListMarker paramTypeArgumentListMarker, int paramInt)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeArgumentListMarker, "$this$get");
      if ((paramTypeArgumentListMarker instanceof SimpleTypeMarker))
      {
        paramTypeSystemContext = paramTypeSystemContext.getArgument((KotlinTypeMarker)paramTypeArgumentListMarker, paramInt);
      }
      else
      {
        if (!(paramTypeArgumentListMarker instanceof ArgumentList)) {
          break label57;
        }
        paramTypeSystemContext = ((ArgumentList)paramTypeArgumentListMarker).get(paramInt);
        Intrinsics.checkExpressionValueIsNotNull(paramTypeSystemContext, "get(index)");
        paramTypeSystemContext = (TypeArgumentMarker)paramTypeSystemContext;
      }
      return paramTypeSystemContext;
      label57:
      paramTypeSystemContext = new StringBuilder();
      paramTypeSystemContext.append("unknown type argument list type: ");
      paramTypeSystemContext.append(paramTypeArgumentListMarker);
      paramTypeSystemContext.append(", ");
      paramTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeArgumentListMarker.getClass()));
      throw ((Throwable)new IllegalStateException(paramTypeSystemContext.toString().toString()));
    }
    
    public static TypeArgumentMarker getArgumentOrNull(TypeSystemContext paramTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker, int paramInt)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$getArgumentOrNull");
      paramSimpleTypeMarker = (KotlinTypeMarker)paramSimpleTypeMarker;
      int i = paramTypeSystemContext.argumentsCount(paramSimpleTypeMarker);
      if ((paramInt >= 0) && (i > paramInt)) {
        return paramTypeSystemContext.getArgument(paramSimpleTypeMarker, paramInt);
      }
      return null;
    }
    
    public static boolean hasFlexibleNullability(TypeSystemContext paramTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$hasFlexibleNullability");
      boolean bool;
      if (paramTypeSystemContext.isMarkedNullable(paramTypeSystemContext.lowerBoundIfFlexible(paramKotlinTypeMarker)) != paramTypeSystemContext.isMarkedNullable(paramTypeSystemContext.upperBoundIfFlexible(paramKotlinTypeMarker))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public static boolean identicalArguments(TypeSystemContext paramTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker1, SimpleTypeMarker paramSimpleTypeMarker2)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker1, "a");
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker2, "b");
      return TypeSystemOptimizationContext.DefaultImpls.identicalArguments((TypeSystemOptimizationContext)paramTypeSystemContext, paramSimpleTypeMarker1, paramSimpleTypeMarker2);
    }
    
    public static boolean isClassType(TypeSystemContext paramTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$isClassType");
      return paramTypeSystemContext.isClassTypeConstructor(paramTypeSystemContext.typeConstructor(paramSimpleTypeMarker));
    }
    
    public static boolean isDefinitelyNotNullType(TypeSystemContext paramTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isDefinitelyNotNullType");
      paramKotlinTypeMarker = paramTypeSystemContext.asSimpleType(paramKotlinTypeMarker);
      if (paramKotlinTypeMarker != null) {
        paramTypeSystemContext = paramTypeSystemContext.asDefinitelyNotNullType(paramKotlinTypeMarker);
      } else {
        paramTypeSystemContext = null;
      }
      boolean bool;
      if (paramTypeSystemContext != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public static boolean isDynamic(TypeSystemContext paramTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isDynamic");
      paramKotlinTypeMarker = paramTypeSystemContext.asFlexibleType(paramKotlinTypeMarker);
      if (paramKotlinTypeMarker != null) {
        paramTypeSystemContext = paramTypeSystemContext.asDynamicType(paramKotlinTypeMarker);
      } else {
        paramTypeSystemContext = null;
      }
      boolean bool;
      if (paramTypeSystemContext != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public static boolean isIntegerLiteralType(TypeSystemContext paramTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$isIntegerLiteralType");
      return paramTypeSystemContext.isIntegerLiteralTypeConstructor(paramTypeSystemContext.typeConstructor(paramSimpleTypeMarker));
    }
    
    public static boolean isNothing(TypeSystemContext paramTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isNothing");
      boolean bool;
      if ((paramTypeSystemContext.isNothingConstructor(paramTypeSystemContext.typeConstructor(paramKotlinTypeMarker))) && (!paramTypeSystemContext.isNullableType(paramKotlinTypeMarker))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public static SimpleTypeMarker lowerBoundIfFlexible(TypeSystemContext paramTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$lowerBoundIfFlexible");
      Object localObject = paramTypeSystemContext.asFlexibleType(paramKotlinTypeMarker);
      if (localObject != null)
      {
        localObject = paramTypeSystemContext.lowerBound((FlexibleTypeMarker)localObject);
        if (localObject != null) {
          return (TypeSystemContext)localObject;
        }
      }
      paramKotlinTypeMarker = paramTypeSystemContext.asSimpleType(paramKotlinTypeMarker);
      paramTypeSystemContext = paramKotlinTypeMarker;
      if (paramKotlinTypeMarker == null)
      {
        Intrinsics.throwNpe();
        paramTypeSystemContext = paramKotlinTypeMarker;
      }
      return paramTypeSystemContext;
    }
    
    public static int size(TypeSystemContext paramTypeSystemContext, TypeArgumentListMarker paramTypeArgumentListMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeArgumentListMarker, "$this$size");
      int i;
      if ((paramTypeArgumentListMarker instanceof SimpleTypeMarker))
      {
        i = paramTypeSystemContext.argumentsCount((KotlinTypeMarker)paramTypeArgumentListMarker);
      }
      else
      {
        if (!(paramTypeArgumentListMarker instanceof ArgumentList)) {
          break label44;
        }
        i = ((ArgumentList)paramTypeArgumentListMarker).size();
      }
      return i;
      label44:
      paramTypeSystemContext = new StringBuilder();
      paramTypeSystemContext.append("unknown type argument list type: ");
      paramTypeSystemContext.append(paramTypeArgumentListMarker);
      paramTypeSystemContext.append(", ");
      paramTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeArgumentListMarker.getClass()));
      throw ((Throwable)new IllegalStateException(paramTypeSystemContext.toString().toString()));
    }
    
    public static TypeConstructorMarker typeConstructor(TypeSystemContext paramTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$typeConstructor");
      SimpleTypeMarker localSimpleTypeMarker = paramTypeSystemContext.asSimpleType(paramKotlinTypeMarker);
      if (localSimpleTypeMarker != null) {
        paramKotlinTypeMarker = localSimpleTypeMarker;
      } else {
        paramKotlinTypeMarker = paramTypeSystemContext.lowerBoundIfFlexible(paramKotlinTypeMarker);
      }
      return paramTypeSystemContext.typeConstructor(paramKotlinTypeMarker);
    }
    
    public static SimpleTypeMarker upperBoundIfFlexible(TypeSystemContext paramTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$upperBoundIfFlexible");
      Object localObject = paramTypeSystemContext.asFlexibleType(paramKotlinTypeMarker);
      if (localObject != null)
      {
        localObject = paramTypeSystemContext.upperBound((FlexibleTypeMarker)localObject);
        if (localObject != null) {
          return (TypeSystemContext)localObject;
        }
      }
      paramKotlinTypeMarker = paramTypeSystemContext.asSimpleType(paramKotlinTypeMarker);
      paramTypeSystemContext = paramKotlinTypeMarker;
      if (paramKotlinTypeMarker == null)
      {
        Intrinsics.throwNpe();
        paramTypeSystemContext = paramKotlinTypeMarker;
      }
      return paramTypeSystemContext;
    }
  }
}

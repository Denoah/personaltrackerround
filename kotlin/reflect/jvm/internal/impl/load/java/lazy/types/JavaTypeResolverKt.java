package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.components.TypeUsage;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionImplKt;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

public final class JavaTypeResolverKt
{
  private static final FqName JAVA_LANG_CLASS_FQ_NAME = new FqName("java.lang.Class");
  
  public static final KotlinType getErasedUpperBound(TypeParameterDescriptor paramTypeParameterDescriptor1, TypeParameterDescriptor paramTypeParameterDescriptor2, Function0<? extends KotlinType> paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeParameterDescriptor1, "$this$getErasedUpperBound");
    Intrinsics.checkParameterIsNotNull(paramFunction0, "defaultValue");
    if (paramTypeParameterDescriptor1 == paramTypeParameterDescriptor2) {
      return (KotlinType)paramFunction0.invoke();
    }
    Object localObject = paramTypeParameterDescriptor1.getUpperBounds();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "upperBounds");
    localObject = (KotlinType)CollectionsKt.first((List)localObject);
    if ((((KotlinType)localObject).getConstructor().getDeclarationDescriptor() instanceof ClassDescriptor))
    {
      Intrinsics.checkExpressionValueIsNotNull(localObject, "firstUpperBound");
      return TypeUtilsKt.replaceArgumentsWithStarProjections((KotlinType)localObject);
    }
    if (paramTypeParameterDescriptor2 != null) {
      paramTypeParameterDescriptor1 = paramTypeParameterDescriptor2;
    }
    paramTypeParameterDescriptor2 = ((KotlinType)localObject).getConstructor().getDeclarationDescriptor();
    if (paramTypeParameterDescriptor2 != null)
    {
      paramTypeParameterDescriptor2 = (TypeParameterDescriptor)paramTypeParameterDescriptor2;
      while ((Intrinsics.areEqual(paramTypeParameterDescriptor2, paramTypeParameterDescriptor1) ^ true))
      {
        paramTypeParameterDescriptor2 = paramTypeParameterDescriptor2.getUpperBounds();
        Intrinsics.checkExpressionValueIsNotNull(paramTypeParameterDescriptor2, "current.upperBounds");
        paramTypeParameterDescriptor2 = (KotlinType)CollectionsKt.first(paramTypeParameterDescriptor2);
        if ((paramTypeParameterDescriptor2.getConstructor().getDeclarationDescriptor() instanceof ClassDescriptor))
        {
          Intrinsics.checkExpressionValueIsNotNull(paramTypeParameterDescriptor2, "nextUpperBound");
          return TypeUtilsKt.replaceArgumentsWithStarProjections(paramTypeParameterDescriptor2);
        }
        paramTypeParameterDescriptor2 = paramTypeParameterDescriptor2.getConstructor().getDeclarationDescriptor();
        if (paramTypeParameterDescriptor2 != null) {
          paramTypeParameterDescriptor2 = (TypeParameterDescriptor)paramTypeParameterDescriptor2;
        } else {
          throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.TypeParameterDescriptor");
        }
      }
      return (KotlinType)paramFunction0.invoke();
    }
    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.TypeParameterDescriptor");
  }
  
  public static final TypeProjection makeStarProjection(TypeParameterDescriptor paramTypeParameterDescriptor, JavaTypeAttributes paramJavaTypeAttributes)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeParameterDescriptor, "typeParameter");
    Intrinsics.checkParameterIsNotNull(paramJavaTypeAttributes, "attr");
    if (paramJavaTypeAttributes.getHowThisTypeIsUsed() == TypeUsage.SUPERTYPE) {
      paramTypeParameterDescriptor = (TypeProjection)new TypeProjectionImpl(StarProjectionImplKt.starProjectionType(paramTypeParameterDescriptor));
    } else {
      paramTypeParameterDescriptor = (TypeProjection)new StarProjectionImpl(paramTypeParameterDescriptor);
    }
    return paramTypeParameterDescriptor;
  }
  
  public static final JavaTypeAttributes toAttributes(TypeUsage paramTypeUsage, boolean paramBoolean, TypeParameterDescriptor paramTypeParameterDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeUsage, "$this$toAttributes");
    return new JavaTypeAttributes(paramTypeUsage, null, paramBoolean, paramTypeParameterDescriptor, 2, null);
  }
}

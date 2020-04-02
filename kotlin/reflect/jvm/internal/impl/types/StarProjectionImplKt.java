package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;

public final class StarProjectionImplKt
{
  public static final KotlinType starProjectionType(TypeParameterDescriptor paramTypeParameterDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeParameterDescriptor, "$this$starProjectionType");
    Object localObject1 = paramTypeParameterDescriptor.getContainingDeclaration();
    if (localObject1 != null)
    {
      localObject1 = ((ClassifierDescriptorWithTypeParameters)localObject1).getTypeConstructor();
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "classDescriptor.typeConstructor");
      localObject1 = ((TypeConstructor)localObject1).getParameters();
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "classDescriptor.typeConstructor.parameters");
      Object localObject2 = (Iterable)localObject1;
      localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
      localObject2 = ((Iterable)localObject2).iterator();
      while (((Iterator)localObject2).hasNext())
      {
        TypeParameterDescriptor localTypeParameterDescriptor = (TypeParameterDescriptor)((Iterator)localObject2).next();
        Intrinsics.checkExpressionValueIsNotNull(localTypeParameterDescriptor, "it");
        ((Collection)localObject1).add(localTypeParameterDescriptor.getTypeConstructor());
      }
      localObject1 = TypeSubstitutor.create((TypeSubstitution)new TypeConstructorSubstitution()
      {
        public TypeProjection get(TypeConstructor paramAnonymousTypeConstructor)
        {
          Intrinsics.checkParameterIsNotNull(paramAnonymousTypeConstructor, "key");
          if (this.$typeParameters.contains(paramAnonymousTypeConstructor))
          {
            paramAnonymousTypeConstructor = paramAnonymousTypeConstructor.getDeclarationDescriptor();
            if (paramAnonymousTypeConstructor != null) {
              paramAnonymousTypeConstructor = TypeUtils.makeStarProjection((TypeParameterDescriptor)paramAnonymousTypeConstructor);
            } else {
              throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.TypeParameterDescriptor");
            }
          }
          else
          {
            paramAnonymousTypeConstructor = null;
          }
          return paramAnonymousTypeConstructor;
        }
      });
      localObject2 = paramTypeParameterDescriptor.getUpperBounds();
      Intrinsics.checkExpressionValueIsNotNull(localObject2, "this.upperBounds");
      localObject1 = ((TypeSubstitutor)localObject1).substitute((KotlinType)CollectionsKt.first((List)localObject2), Variance.OUT_VARIANCE);
      if (localObject1 != null)
      {
        paramTypeParameterDescriptor = (TypeParameterDescriptor)localObject1;
      }
      else
      {
        paramTypeParameterDescriptor = DescriptorUtilsKt.getBuiltIns((DeclarationDescriptor)paramTypeParameterDescriptor).getDefaultBound();
        Intrinsics.checkExpressionValueIsNotNull(paramTypeParameterDescriptor, "builtIns.defaultBound");
        paramTypeParameterDescriptor = (KotlinType)paramTypeParameterDescriptor;
      }
      return paramTypeParameterDescriptor;
    }
    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters");
  }
}

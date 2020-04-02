package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.components.TypeUsage;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

public final class RawSubstitution
  extends TypeSubstitution
{
  public static final RawSubstitution INSTANCE = new RawSubstitution();
  private static final JavaTypeAttributes lowerTypeAttr = JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, false, null, 3, null).withFlexibility(JavaTypeFlexibility.FLEXIBLE_LOWER_BOUND);
  private static final JavaTypeAttributes upperTypeAttr = JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, false, null, 3, null).withFlexibility(JavaTypeFlexibility.FLEXIBLE_UPPER_BOUND);
  
  private RawSubstitution() {}
  
  private final Pair<SimpleType, Boolean> eraseInflexibleBasedOnClassDescriptor(final SimpleType paramSimpleType, ClassDescriptor paramClassDescriptor, final JavaTypeAttributes paramJavaTypeAttributes)
  {
    boolean bool = paramSimpleType.getConstructor().getParameters().isEmpty();
    Object localObject1 = Boolean.valueOf(false);
    if (bool) {
      return TuplesKt.to(paramSimpleType, localObject1);
    }
    Object localObject2 = (KotlinType)paramSimpleType;
    if (KotlinBuiltIns.isArray((KotlinType)localObject2))
    {
      paramJavaTypeAttributes = (TypeProjection)paramSimpleType.getArguments().get(0);
      paramClassDescriptor = paramJavaTypeAttributes.getProjectionKind();
      paramJavaTypeAttributes = paramJavaTypeAttributes.getType();
      Intrinsics.checkExpressionValueIsNotNull(paramJavaTypeAttributes, "componentTypeProjection.type");
      paramClassDescriptor = CollectionsKt.listOf(new TypeProjectionImpl(paramClassDescriptor, eraseType(paramJavaTypeAttributes)));
      return TuplesKt.to(KotlinTypeFactory.simpleType$default(paramSimpleType.getAnnotations(), paramSimpleType.getConstructor(), paramClassDescriptor, paramSimpleType.isMarkedNullable(), null, 16, null), localObject1);
    }
    if (KotlinTypeKt.isError((KotlinType)localObject2))
    {
      paramClassDescriptor = new StringBuilder();
      paramClassDescriptor.append("Raw error type: ");
      paramClassDescriptor.append(paramSimpleType.getConstructor());
      return TuplesKt.to(ErrorUtils.createErrorType(paramClassDescriptor.toString()), localObject1);
    }
    MemberScope localMemberScope = paramClassDescriptor.getMemberScope((TypeSubstitution)INSTANCE);
    Intrinsics.checkExpressionValueIsNotNull(localMemberScope, "declaration.getMemberScope(RawSubstitution)");
    localObject1 = paramSimpleType.getAnnotations();
    localObject2 = paramClassDescriptor.getTypeConstructor();
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "declaration.typeConstructor");
    Object localObject3 = paramClassDescriptor.getTypeConstructor();
    Intrinsics.checkExpressionValueIsNotNull(localObject3, "declaration.typeConstructor");
    localObject3 = ((TypeConstructor)localObject3).getParameters();
    Intrinsics.checkExpressionValueIsNotNull(localObject3, "declaration.typeConstructor.parameters");
    Object localObject4 = (Iterable)localObject3;
    localObject3 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject4, 10));
    Iterator localIterator = ((Iterable)localObject4).iterator();
    while (localIterator.hasNext())
    {
      localObject4 = (TypeParameterDescriptor)localIterator.next();
      RawSubstitution localRawSubstitution = INSTANCE;
      Intrinsics.checkExpressionValueIsNotNull(localObject4, "parameter");
      ((Collection)localObject3).add(computeProjection$default(localRawSubstitution, (TypeParameterDescriptor)localObject4, paramJavaTypeAttributes, null, 4, null));
    }
    TuplesKt.to(KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope((Annotations)localObject1, (TypeConstructor)localObject2, (List)localObject3, paramSimpleType.isMarkedNullable(), localMemberScope, (Function1)new Lambda(paramClassDescriptor)
    {
      public final SimpleType invoke(KotlinTypeRefiner paramAnonymousKotlinTypeRefiner)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousKotlinTypeRefiner, "kotlinTypeRefiner");
        ClassDescriptor localClassDescriptor = this.$declaration;
        Object localObject = localClassDescriptor;
        if (!(localClassDescriptor instanceof ClassDescriptor)) {
          localObject = null;
        }
        if (localObject != null)
        {
          localObject = DescriptorUtilsKt.getClassId((ClassifierDescriptor)localObject);
          if (localObject != null)
          {
            paramAnonymousKotlinTypeRefiner = paramAnonymousKotlinTypeRefiner.findClassAcrossModuleDependencies((ClassId)localObject);
            if (paramAnonymousKotlinTypeRefiner != null)
            {
              if (Intrinsics.areEqual(paramAnonymousKotlinTypeRefiner, this.$declaration)) {
                return null;
              }
              return (SimpleType)RawSubstitution.access$eraseInflexibleBasedOnClassDescriptor(RawSubstitution.INSTANCE, paramSimpleType, paramAnonymousKotlinTypeRefiner, paramJavaTypeAttributes).getFirst();
            }
          }
        }
        return null;
      }
    }), Boolean.valueOf(true));
  }
  
  private final KotlinType eraseType(KotlinType paramKotlinType)
  {
    Object localObject1 = paramKotlinType.getConstructor().getDeclarationDescriptor();
    Object localObject2;
    if ((localObject1 instanceof TypeParameterDescriptor))
    {
      paramKotlinType = eraseType(JavaTypeResolverKt.getErasedUpperBound$default((TypeParameterDescriptor)localObject1, null, null, 3, null));
    }
    else
    {
      if (!(localObject1 instanceof ClassDescriptor)) {
        break label249;
      }
      localObject2 = FlexibleTypesKt.upperIfFlexible(paramKotlinType).getConstructor().getDeclarationDescriptor();
      if (!(localObject2 instanceof ClassDescriptor)) {
        break label180;
      }
      Pair localPair = eraseInflexibleBasedOnClassDescriptor(FlexibleTypesKt.lowerIfFlexible(paramKotlinType), (ClassDescriptor)localObject1, lowerTypeAttr);
      localObject1 = (SimpleType)localPair.component1();
      boolean bool1 = ((Boolean)localPair.component2()).booleanValue();
      localObject2 = eraseInflexibleBasedOnClassDescriptor(FlexibleTypesKt.upperIfFlexible(paramKotlinType), (ClassDescriptor)localObject2, upperTypeAttr);
      paramKotlinType = (SimpleType)((Pair)localObject2).component1();
      boolean bool2 = ((Boolean)((Pair)localObject2).component2()).booleanValue();
      if ((!bool1) && (!bool2)) {
        paramKotlinType = KotlinTypeFactory.flexibleType((SimpleType)localObject1, paramKotlinType);
      } else {
        paramKotlinType = (UnwrappedType)new RawTypeImpl((SimpleType)localObject1, paramKotlinType);
      }
      paramKotlinType = (KotlinType)paramKotlinType;
    }
    return paramKotlinType;
    label180:
    paramKotlinType = new StringBuilder();
    paramKotlinType.append("For some reason declaration for upper bound is not a class ");
    paramKotlinType.append("but \"");
    paramKotlinType.append(localObject2);
    paramKotlinType.append("\" while for lower it's \"");
    paramKotlinType.append(localObject1);
    paramKotlinType.append('"');
    throw ((Throwable)new IllegalStateException(paramKotlinType.toString().toString()));
    label249:
    paramKotlinType = new StringBuilder();
    paramKotlinType.append("Unexpected declaration kind: ");
    paramKotlinType.append(localObject1);
    throw ((Throwable)new IllegalStateException(paramKotlinType.toString().toString()));
  }
  
  public final TypeProjection computeProjection(TypeParameterDescriptor paramTypeParameterDescriptor, JavaTypeAttributes paramJavaTypeAttributes, KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeParameterDescriptor, "parameter");
    Intrinsics.checkParameterIsNotNull(paramJavaTypeAttributes, "attr");
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "erasedUpperBound");
    Object localObject = paramJavaTypeAttributes.getFlexibility();
    int i = RawSubstitution.WhenMappings.$EnumSwitchMapping$0[localObject.ordinal()];
    if (i != 1)
    {
      if ((i != 2) && (i != 3)) {
        throw new NoWhenBranchMatchedException();
      }
      if (!paramTypeParameterDescriptor.getVariance().getAllowsOutPosition())
      {
        paramTypeParameterDescriptor = (TypeProjection)new TypeProjectionImpl(Variance.INVARIANT, (KotlinType)DescriptorUtilsKt.getBuiltIns((DeclarationDescriptor)paramTypeParameterDescriptor).getNothingType());
      }
      else
      {
        localObject = paramKotlinType.getConstructor().getParameters();
        Intrinsics.checkExpressionValueIsNotNull(localObject, "erasedUpperBound.constructor.parameters");
        if ((((Collection)localObject).isEmpty() ^ true)) {
          paramTypeParameterDescriptor = (TypeProjection)new TypeProjectionImpl(Variance.OUT_VARIANCE, paramKotlinType);
        } else {
          paramTypeParameterDescriptor = JavaTypeResolverKt.makeStarProjection(paramTypeParameterDescriptor, paramJavaTypeAttributes);
        }
      }
    }
    else
    {
      paramTypeParameterDescriptor = (TypeProjection)new TypeProjectionImpl(Variance.INVARIANT, paramKotlinType);
    }
    return paramTypeParameterDescriptor;
  }
  
  public TypeProjectionImpl get(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "key");
    return new TypeProjectionImpl(eraseType(paramKotlinType));
  }
  
  public boolean isEmpty()
  {
    return false;
  }
}

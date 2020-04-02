package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleAwareClassDescriptorKt;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntegerLiteralTypeConstructor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

public final class KotlinTypeFactory
{
  private static final Function1<KotlinTypeRefiner, SimpleType> EMPTY_REFINED_TYPE_FACTORY = (Function1)EMPTY_REFINED_TYPE_FACTORY.1.INSTANCE;
  public static final KotlinTypeFactory INSTANCE = new KotlinTypeFactory();
  
  private KotlinTypeFactory() {}
  
  @JvmStatic
  public static final SimpleType computeExpandedType(TypeAliasDescriptor paramTypeAliasDescriptor, List<? extends TypeProjection> paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeAliasDescriptor, "$this$computeExpandedType");
    Intrinsics.checkParameterIsNotNull(paramList, "arguments");
    return new TypeAliasExpander((TypeAliasExpansionReportStrategy)TypeAliasExpansionReportStrategy.DO_NOTHING.INSTANCE, false).expand(TypeAliasExpansion.Companion.create(null, paramTypeAliasDescriptor, paramList), Annotations.Companion.getEMPTY());
  }
  
  private final MemberScope computeMemberScope(TypeConstructor paramTypeConstructor, List<? extends TypeProjection> paramList, KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    ClassifierDescriptor localClassifierDescriptor = paramTypeConstructor.getDeclarationDescriptor();
    if ((localClassifierDescriptor instanceof TypeParameterDescriptor))
    {
      paramTypeConstructor = localClassifierDescriptor.getDefaultType().getMemberScope();
    }
    else if ((localClassifierDescriptor instanceof ClassDescriptor))
    {
      if (paramKotlinTypeRefiner == null) {
        paramKotlinTypeRefiner = DescriptorUtilsKt.getKotlinTypeRefiner(DescriptorUtilsKt.getModule((DeclarationDescriptor)localClassifierDescriptor));
      }
      if (paramList.isEmpty()) {
        paramTypeConstructor = ModuleAwareClassDescriptorKt.getRefinedUnsubstitutedMemberScopeIfPossible((ClassDescriptor)localClassifierDescriptor, paramKotlinTypeRefiner);
      } else {
        paramTypeConstructor = ModuleAwareClassDescriptorKt.getRefinedMemberScopeIfPossible((ClassDescriptor)localClassifierDescriptor, TypeConstructorSubstitution.Companion.create(paramTypeConstructor, paramList), paramKotlinTypeRefiner);
      }
    }
    else
    {
      if (!(localClassifierDescriptor instanceof TypeAliasDescriptor)) {
        break label155;
      }
      paramTypeConstructor = new StringBuilder();
      paramTypeConstructor.append("Scope for abbreviation: ");
      paramTypeConstructor.append(((TypeAliasDescriptor)localClassifierDescriptor).getName());
      paramTypeConstructor = ErrorUtils.createErrorScope(paramTypeConstructor.toString(), true);
      Intrinsics.checkExpressionValueIsNotNull(paramTypeConstructor, "ErrorUtils.createErrorSc…{descriptor.name}\", true)");
    }
    return paramTypeConstructor;
    label155:
    paramList = new StringBuilder();
    paramList.append("Unsupported classifier: ");
    paramList.append(localClassifierDescriptor);
    paramList.append(" for constructor: ");
    paramList.append(paramTypeConstructor);
    throw ((Throwable)new IllegalStateException(paramList.toString()));
  }
  
  @JvmStatic
  public static final UnwrappedType flexibleType(SimpleType paramSimpleType1, SimpleType paramSimpleType2)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleType1, "lowerBound");
    Intrinsics.checkParameterIsNotNull(paramSimpleType2, "upperBound");
    if (Intrinsics.areEqual(paramSimpleType1, paramSimpleType2)) {
      return (UnwrappedType)paramSimpleType1;
    }
    return (UnwrappedType)new FlexibleTypeImpl(paramSimpleType1, paramSimpleType2);
  }
  
  @JvmStatic
  public static final SimpleType integerLiteralType(Annotations paramAnnotations, IntegerLiteralTypeConstructor paramIntegerLiteralTypeConstructor, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "annotations");
    Intrinsics.checkParameterIsNotNull(paramIntegerLiteralTypeConstructor, "constructor");
    paramIntegerLiteralTypeConstructor = (TypeConstructor)paramIntegerLiteralTypeConstructor;
    List localList = CollectionsKt.emptyList();
    MemberScope localMemberScope = ErrorUtils.createErrorScope("Scope for integer literal type", true);
    Intrinsics.checkExpressionValueIsNotNull(localMemberScope, "ErrorUtils.createErrorSc…eger literal type\", true)");
    return simpleTypeWithNonTrivialMemberScope(paramAnnotations, paramIntegerLiteralTypeConstructor, localList, paramBoolean, localMemberScope);
  }
  
  private final ExpandedTypeOrRefinedConstructor refineConstructor(TypeConstructor paramTypeConstructor, KotlinTypeRefiner paramKotlinTypeRefiner, List<? extends TypeProjection> paramList)
  {
    paramTypeConstructor = paramTypeConstructor.getDeclarationDescriptor();
    if (paramTypeConstructor != null)
    {
      paramTypeConstructor = paramKotlinTypeRefiner.refineDescriptor((DeclarationDescriptor)paramTypeConstructor);
      if (paramTypeConstructor != null)
      {
        if ((paramTypeConstructor instanceof TypeAliasDescriptor)) {
          return new ExpandedTypeOrRefinedConstructor(computeExpandedType((TypeAliasDescriptor)paramTypeConstructor, paramList), null);
        }
        paramTypeConstructor = paramTypeConstructor.getTypeConstructor().refine(paramKotlinTypeRefiner);
        Intrinsics.checkExpressionValueIsNotNull(paramTypeConstructor, "descriptor.typeConstruct…refine(kotlinTypeRefiner)");
        return new ExpandedTypeOrRefinedConstructor(null, paramTypeConstructor);
      }
    }
    return null;
  }
  
  @JvmStatic
  public static final SimpleType simpleNotNullType(Annotations paramAnnotations, ClassDescriptor paramClassDescriptor, List<? extends TypeProjection> paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "annotations");
    Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "descriptor");
    Intrinsics.checkParameterIsNotNull(paramList, "arguments");
    paramClassDescriptor = paramClassDescriptor.getTypeConstructor();
    Intrinsics.checkExpressionValueIsNotNull(paramClassDescriptor, "descriptor.typeConstructor");
    return simpleType$default(paramAnnotations, paramClassDescriptor, paramList, false, null, 16, null);
  }
  
  @JvmStatic
  public static final SimpleType simpleType(final Annotations paramAnnotations, TypeConstructor paramTypeConstructor, final List<? extends TypeProjection> paramList, final boolean paramBoolean, KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "annotations");
    Intrinsics.checkParameterIsNotNull(paramTypeConstructor, "constructor");
    Intrinsics.checkParameterIsNotNull(paramList, "arguments");
    if ((paramAnnotations.isEmpty()) && (paramList.isEmpty()) && (!paramBoolean) && (paramTypeConstructor.getDeclarationDescriptor() != null))
    {
      paramAnnotations = paramTypeConstructor.getDeclarationDescriptor();
      if (paramAnnotations == null) {
        Intrinsics.throwNpe();
      }
      Intrinsics.checkExpressionValueIsNotNull(paramAnnotations, "constructor.declarationDescriptor!!");
      paramAnnotations = paramAnnotations.getDefaultType();
      Intrinsics.checkExpressionValueIsNotNull(paramAnnotations, "constructor.declarationDescriptor!!.defaultType");
      return paramAnnotations;
    }
    simpleTypeWithNonTrivialMemberScope(paramAnnotations, paramTypeConstructor, paramList, paramBoolean, INSTANCE.computeMemberScope(paramTypeConstructor, paramList, paramKotlinTypeRefiner), (Function1)new Lambda(paramTypeConstructor)
    {
      public final SimpleType invoke(KotlinTypeRefiner paramAnonymousKotlinTypeRefiner)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousKotlinTypeRefiner, "refiner");
        Object localObject1 = KotlinTypeFactory.access$refineConstructor(KotlinTypeFactory.INSTANCE, this.$constructor, paramAnonymousKotlinTypeRefiner, paramList);
        if (localObject1 != null)
        {
          Object localObject2 = ((KotlinTypeFactory.ExpandedTypeOrRefinedConstructor)localObject1).getExpandedType();
          if (localObject2 != null) {
            return localObject2;
          }
          localObject2 = paramAnnotations;
          localObject1 = ((KotlinTypeFactory.ExpandedTypeOrRefinedConstructor)localObject1).getRefinedConstructor();
          if (localObject1 == null) {
            Intrinsics.throwNpe();
          }
          return KotlinTypeFactory.simpleType((Annotations)localObject2, (TypeConstructor)localObject1, paramList, paramBoolean, paramAnonymousKotlinTypeRefiner);
        }
        return null;
      }
    });
  }
  
  @JvmStatic
  public static final SimpleType simpleTypeWithNonTrivialMemberScope(final Annotations paramAnnotations, TypeConstructor paramTypeConstructor, final List<? extends TypeProjection> paramList, final boolean paramBoolean, final MemberScope paramMemberScope)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "annotations");
    Intrinsics.checkParameterIsNotNull(paramTypeConstructor, "constructor");
    Intrinsics.checkParameterIsNotNull(paramList, "arguments");
    Intrinsics.checkParameterIsNotNull(paramMemberScope, "memberScope");
    paramTypeConstructor = new SimpleTypeImpl(paramTypeConstructor, paramList, paramBoolean, paramMemberScope, (Function1)new Lambda(paramTypeConstructor)
    {
      public final SimpleType invoke(KotlinTypeRefiner paramAnonymousKotlinTypeRefiner)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousKotlinTypeRefiner, "kotlinTypeRefiner");
        paramAnonymousKotlinTypeRefiner = KotlinTypeFactory.access$refineConstructor(KotlinTypeFactory.INSTANCE, this.$constructor, paramAnonymousKotlinTypeRefiner, paramList);
        if (paramAnonymousKotlinTypeRefiner != null)
        {
          Object localObject = paramAnonymousKotlinTypeRefiner.getExpandedType();
          if (localObject != null) {
            return localObject;
          }
          localObject = paramAnnotations;
          paramAnonymousKotlinTypeRefiner = paramAnonymousKotlinTypeRefiner.getRefinedConstructor();
          if (paramAnonymousKotlinTypeRefiner == null) {
            Intrinsics.throwNpe();
          }
          return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope((Annotations)localObject, paramAnonymousKotlinTypeRefiner, paramList, paramBoolean, paramMemberScope);
        }
        return null;
      }
    });
    if (paramAnnotations.isEmpty()) {
      paramAnnotations = (SimpleType)paramTypeConstructor;
    } else {
      paramAnnotations = (SimpleType)new AnnotatedSimpleType((SimpleType)paramTypeConstructor, paramAnnotations);
    }
    return paramAnnotations;
  }
  
  @JvmStatic
  public static final SimpleType simpleTypeWithNonTrivialMemberScope(Annotations paramAnnotations, TypeConstructor paramTypeConstructor, List<? extends TypeProjection> paramList, boolean paramBoolean, MemberScope paramMemberScope, Function1<? super KotlinTypeRefiner, ? extends SimpleType> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "annotations");
    Intrinsics.checkParameterIsNotNull(paramTypeConstructor, "constructor");
    Intrinsics.checkParameterIsNotNull(paramList, "arguments");
    Intrinsics.checkParameterIsNotNull(paramMemberScope, "memberScope");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "refinedTypeFactory");
    paramTypeConstructor = new SimpleTypeImpl(paramTypeConstructor, paramList, paramBoolean, paramMemberScope, paramFunction1);
    if (paramAnnotations.isEmpty()) {
      paramAnnotations = (SimpleType)paramTypeConstructor;
    } else {
      paramAnnotations = (SimpleType)new AnnotatedSimpleType((SimpleType)paramTypeConstructor, paramAnnotations);
    }
    return paramAnnotations;
  }
  
  private static final class ExpandedTypeOrRefinedConstructor
  {
    private final SimpleType expandedType;
    private final TypeConstructor refinedConstructor;
    
    public ExpandedTypeOrRefinedConstructor(SimpleType paramSimpleType, TypeConstructor paramTypeConstructor)
    {
      this.expandedType = paramSimpleType;
      this.refinedConstructor = paramTypeConstructor;
    }
    
    public final SimpleType getExpandedType()
    {
      return this.expandedType;
    }
    
    public final TypeConstructor getRefinedConstructor()
    {
      return this.refinedConstructor;
    }
  }
}

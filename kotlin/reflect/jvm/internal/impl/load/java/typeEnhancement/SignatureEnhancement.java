package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin._Assertions;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationsKt;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyGetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver.QualifierApplicabilityType;
import kotlin.reflect.jvm.internal.impl.load.java.DeprecationCausedByFunctionN;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNamesKt;
import kotlin.reflect.jvm.internal.impl.load.java.UtilsKt;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.AnnotationDefaultValue;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaCallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaPropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.NullDefaultValue;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.PossiblyExternalAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.StringDefaultValue;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.UtilKt;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.ValueParameterData;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.JavaTypeQualifiersByElementType;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.JavaDescriptorUtilKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import kotlin.reflect.jvm.internal.impl.resolve.deprecation.DeprecationKt;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.utils.Jsr305State;
import kotlin.reflect.jvm.internal.impl.utils.ReportLevel;

public final class SignatureEnhancement
{
  private final AnnotationTypeQualifierResolver annotationTypeQualifierResolver;
  private final Jsr305State jsr305State;
  
  public SignatureEnhancement(AnnotationTypeQualifierResolver paramAnnotationTypeQualifierResolver, Jsr305State paramJsr305State)
  {
    this.annotationTypeQualifierResolver = paramAnnotationTypeQualifierResolver;
    this.jsr305State = paramJsr305State;
  }
  
  private final <D extends CallableMemberDescriptor> D enhanceSignature(D paramD, LazyJavaResolverContext paramLazyJavaResolverContext)
  {
    if (!(paramD instanceof JavaCallableMemberDescriptor)) {
      return paramD;
    }
    JavaCallableMemberDescriptor localJavaCallableMemberDescriptor = (JavaCallableMemberDescriptor)paramD;
    Object localObject1 = localJavaCallableMemberDescriptor.getKind();
    Object localObject2 = CallableMemberDescriptor.Kind.FAKE_OVERRIDE;
    int i = 1;
    if (localObject1 == localObject2)
    {
      localObject2 = localJavaCallableMemberDescriptor.getOriginal();
      Intrinsics.checkExpressionValueIsNotNull(localObject2, "original");
      if (((CallableMemberDescriptor)localObject2).getOverriddenDescriptors().size() == 1) {
        return paramD;
      }
    }
    Object localObject3 = ContextKt.copyWithNewDefaultTypeQualifiers(paramLazyJavaResolverContext, paramD.getAnnotations());
    if ((paramD instanceof JavaPropertyDescriptor))
    {
      localObject2 = (JavaPropertyDescriptor)paramD;
      paramLazyJavaResolverContext = ((JavaPropertyDescriptor)localObject2).getGetter();
      if ((paramLazyJavaResolverContext != null) && (!paramLazyJavaResolverContext.isDefault()))
      {
        paramLazyJavaResolverContext = ((JavaPropertyDescriptor)localObject2).getGetter();
        if (paramLazyJavaResolverContext == null) {
          Intrinsics.throwNpe();
        }
        Intrinsics.checkExpressionValueIsNotNull(paramLazyJavaResolverContext, "getter!!");
        paramLazyJavaResolverContext = (CallableMemberDescriptor)paramLazyJavaResolverContext;
        break label141;
      }
    }
    paramLazyJavaResolverContext = paramD;
    label141:
    if (localJavaCallableMemberDescriptor.getExtensionReceiverParameter() != null)
    {
      if (!(paramLazyJavaResolverContext instanceof FunctionDescriptor)) {
        localObject2 = null;
      } else {
        localObject2 = paramLazyJavaResolverContext;
      }
      localObject2 = (FunctionDescriptor)localObject2;
      if (localObject2 != null) {
        localObject2 = (ValueParameterDescriptor)((FunctionDescriptor)localObject2).getUserData(JavaMethodDescriptor.ORIGINAL_VALUE_PARAMETER_FOR_EXTENSION_RECEIVER);
      } else {
        localObject2 = null;
      }
      localObject2 = SignatureParts.enhance$default(partsForValueParameter(paramD, (ValueParameterDescriptor)localObject2, (LazyJavaResolverContext)localObject3, (Function1)enhanceSignature.receiverTypeEnhancement.1.INSTANCE), null, 1, null);
    }
    else
    {
      localObject2 = null;
    }
    if (!(paramD instanceof JavaMethodDescriptor)) {
      localObject1 = null;
    } else {
      localObject1 = paramD;
    }
    Object localObject4 = (JavaMethodDescriptor)localObject1;
    if (localObject4 != null)
    {
      localObject5 = SignatureBuildingComponents.INSTANCE;
      localObject1 = ((JavaMethodDescriptor)localObject4).getContainingDeclaration();
      if (localObject1 != null)
      {
        localObject1 = ((SignatureBuildingComponents)localObject5).signature((ClassDescriptor)localObject1, MethodSignatureMappingKt.computeJvmDescriptor$default((FunctionDescriptor)localObject4, false, false, 3, null));
        if (localObject1 != null)
        {
          localObject1 = (PredefinedFunctionEnhancementInfo)PredefinedEnhancementInfoKt.getPREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE().get(localObject1);
          break label333;
        }
      }
      else
      {
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
      }
    }
    localObject1 = null;
    label333:
    if (localObject1 != null)
    {
      if (((PredefinedFunctionEnhancementInfo)localObject1).getParametersInfo().size() == localJavaCallableMemberDescriptor.getValueParameters().size()) {
        j = 1;
      } else {
        j = 0;
      }
      if ((_Assertions.ENABLED) && (j == 0))
      {
        paramLazyJavaResolverContext = new StringBuilder();
        paramLazyJavaResolverContext.append("Predefined enhancement info for ");
        paramLazyJavaResolverContext.append(paramD);
        paramLazyJavaResolverContext.append(" has ");
        paramLazyJavaResolverContext.append(((PredefinedFunctionEnhancementInfo)localObject1).getParametersInfo().size());
        paramLazyJavaResolverContext.append(", but ");
        paramLazyJavaResolverContext.append(localJavaCallableMemberDescriptor.getValueParameters().size());
        paramLazyJavaResolverContext.append(" expected");
        throw ((Throwable)new AssertionError(paramLazyJavaResolverContext.toString()));
      }
    }
    localObject4 = paramLazyJavaResolverContext.getValueParameters();
    Intrinsics.checkExpressionValueIsNotNull(localObject4, "annotationOwnerForMember.valueParameters");
    localObject4 = (Iterable)localObject4;
    Object localObject5 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject4, 10));
    Iterator localIterator = ((Iterable)localObject4).iterator();
    while (localIterator.hasNext())
    {
      ValueParameterDescriptor localValueParameterDescriptor = (ValueParameterDescriptor)localIterator.next();
      Object localObject6 = partsForValueParameter(paramD, localValueParameterDescriptor, (LazyJavaResolverContext)localObject3, (Function1)new Lambda(localValueParameterDescriptor)
      {
        public final KotlinType invoke(CallableMemberDescriptor paramAnonymousCallableMemberDescriptor)
        {
          Intrinsics.checkParameterIsNotNull(paramAnonymousCallableMemberDescriptor, "it");
          paramAnonymousCallableMemberDescriptor = paramAnonymousCallableMemberDescriptor.getValueParameters().get(this.$p.getIndex());
          Intrinsics.checkExpressionValueIsNotNull(paramAnonymousCallableMemberDescriptor, "it.valueParameters[p.index]");
          paramAnonymousCallableMemberDescriptor = ((ValueParameterDescriptor)paramAnonymousCallableMemberDescriptor).getType();
          Intrinsics.checkExpressionValueIsNotNull(paramAnonymousCallableMemberDescriptor, "it.valueParameters[p.index].type");
          return paramAnonymousCallableMemberDescriptor;
        }
      });
      if (localObject1 != null)
      {
        localObject4 = ((PredefinedFunctionEnhancementInfo)localObject1).getParametersInfo();
        if (localObject4 != null)
        {
          localObject4 = (TypeEnhancementInfo)CollectionsKt.getOrNull((List)localObject4, localValueParameterDescriptor.getIndex());
          break label608;
        }
      }
      localObject4 = null;
      label608:
      localObject6 = ((SignatureParts)localObject6).enhance((TypeEnhancementInfo)localObject4);
      if (((PartEnhancementResult)localObject6).getWereChanges())
      {
        localObject4 = ((PartEnhancementResult)localObject6).getType();
      }
      else
      {
        Intrinsics.checkExpressionValueIsNotNull(localValueParameterDescriptor, "p");
        localObject4 = localValueParameterDescriptor.getType();
        Intrinsics.checkExpressionValueIsNotNull(localObject4, "p.type");
      }
      Intrinsics.checkExpressionValueIsNotNull(localValueParameterDescriptor, "p");
      boolean bool1 = hasDefaultValueInAnnotation(localValueParameterDescriptor, (KotlinType)localObject4);
      boolean bool2;
      if ((!((PartEnhancementResult)localObject6).getWereChanges()) && (bool1 == localValueParameterDescriptor.declaresDefaultValue())) {
        bool2 = false;
      } else {
        bool2 = true;
      }
      ((Collection)localObject5).add(new ValueParameterEnhancementResult(((PartEnhancementResult)localObject6).getType(), bool1, bool2, ((PartEnhancementResult)localObject6).getContainsFunctionN()));
    }
    localObject4 = (List)localObject5;
    localObject5 = (Annotated)paramLazyJavaResolverContext;
    if (!(paramD instanceof PropertyDescriptor)) {
      paramLazyJavaResolverContext = null;
    } else {
      paramLazyJavaResolverContext = paramD;
    }
    paramLazyJavaResolverContext = (PropertyDescriptor)paramLazyJavaResolverContext;
    if ((paramLazyJavaResolverContext != null) && (JavaDescriptorUtilKt.isJavaField(paramLazyJavaResolverContext) == true)) {
      paramLazyJavaResolverContext = AnnotationTypeQualifierResolver.QualifierApplicabilityType.FIELD;
    } else {
      paramLazyJavaResolverContext = AnnotationTypeQualifierResolver.QualifierApplicabilityType.METHOD_RETURN_TYPE;
    }
    localObject3 = parts(paramD, (Annotated)localObject5, true, (LazyJavaResolverContext)localObject3, paramLazyJavaResolverContext, (Function1)enhanceSignature.returnTypeEnhancement.1.INSTANCE);
    if (localObject1 != null) {
      paramLazyJavaResolverContext = ((PredefinedFunctionEnhancementInfo)localObject1).getReturnTypeInfo();
    } else {
      paramLazyJavaResolverContext = null;
    }
    localObject1 = ((SignatureParts)localObject3).enhance(paramLazyJavaResolverContext);
    if (((localObject2 == null) || (((PartEnhancementResult)localObject2).getContainsFunctionN() != true)) && (!((PartEnhancementResult)localObject1).getContainsFunctionN()))
    {
      paramLazyJavaResolverContext = (Iterable)localObject4;
      if (((paramLazyJavaResolverContext instanceof Collection)) && (((Collection)paramLazyJavaResolverContext).isEmpty())) {}
      do
      {
        while (!paramLazyJavaResolverContext.hasNext())
        {
          j = 0;
          break;
          paramLazyJavaResolverContext = paramLazyJavaResolverContext.iterator();
        }
      } while (!((ValueParameterEnhancementResult)paramLazyJavaResolverContext.next()).getContainsFunctionN());
      j = 1;
      if (j == 0)
      {
        j = 0;
        break label944;
      }
    }
    int j = 1;
    label944:
    if (((localObject2 == null) || (((PartEnhancementResult)localObject2).getWereChanges() != true)) && (!((PartEnhancementResult)localObject1).getWereChanges()))
    {
      paramLazyJavaResolverContext = (Iterable)localObject4;
      if (((paramLazyJavaResolverContext instanceof Collection)) && (((Collection)paramLazyJavaResolverContext).isEmpty())) {}
      do
      {
        while (!paramLazyJavaResolverContext.hasNext())
        {
          i = 0;
          break;
          paramLazyJavaResolverContext = paramLazyJavaResolverContext.iterator();
        }
      } while (!((ValueParameterEnhancementResult)paramLazyJavaResolverContext.next()).getWereChanges());
      if ((i == 0) && (j == 0)) {
        return paramD;
      }
    }
    if (j != 0) {
      paramD = TuplesKt.to(DeprecationKt.getDEPRECATED_FUNCTION_KEY(), new DeprecationCausedByFunctionN((DeclarationDescriptor)paramD));
    } else {
      paramD = null;
    }
    if (localObject2 != null) {
      paramLazyJavaResolverContext = ((PartEnhancementResult)localObject2).getType();
    } else {
      paramLazyJavaResolverContext = null;
    }
    localObject4 = (Iterable)localObject4;
    localObject2 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject4, 10));
    localObject4 = ((Iterable)localObject4).iterator();
    while (((Iterator)localObject4).hasNext())
    {
      localObject3 = (ValueParameterEnhancementResult)((Iterator)localObject4).next();
      ((Collection)localObject2).add(new ValueParameterData(((ValueParameterEnhancementResult)localObject3).getType(), ((ValueParameterEnhancementResult)localObject3).getHasDefaultValue()));
    }
    paramD = localJavaCallableMemberDescriptor.enhance(paramLazyJavaResolverContext, (List)localObject2, ((PartEnhancementResult)localObject1).getType(), paramD);
    if (paramD != null) {
      return (CallableMemberDescriptor)paramD;
    }
    throw new TypeCastException("null cannot be cast to non-null type D");
  }
  
  private final NullabilityQualifierWithMigrationStatus extractNullabilityFromKnownAnnotations(AnnotationDescriptor paramAnnotationDescriptor)
  {
    Object localObject1 = paramAnnotationDescriptor.getFqName();
    Object localObject2 = null;
    Object localObject3 = localObject2;
    if (localObject1 != null)
    {
      if (JvmAnnotationNamesKt.getNULLABLE_ANNOTATIONS().contains(localObject1)) {
        localObject1 = new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, false, 2, null);
      } else if (JvmAnnotationNamesKt.getNOT_NULL_ANNOTATIONS().contains(localObject1)) {
        localObject1 = new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null);
      } else if (Intrinsics.areEqual(localObject1, JvmAnnotationNamesKt.getJAVAX_NONNULL_ANNOTATION())) {
        localObject1 = extractNullabilityTypeFromArgument(paramAnnotationDescriptor);
      } else if ((Intrinsics.areEqual(localObject1, JvmAnnotationNamesKt.getCOMPATQUAL_NULLABLE_ANNOTATION())) && (this.jsr305State.getEnableCompatqualCheckerFrameworkAnnotations())) {
        localObject1 = new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, false, 2, null);
      } else if ((Intrinsics.areEqual(localObject1, JvmAnnotationNamesKt.getCOMPATQUAL_NONNULL_ANNOTATION())) && (this.jsr305State.getEnableCompatqualCheckerFrameworkAnnotations())) {
        localObject1 = new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null);
      } else if (Intrinsics.areEqual(localObject1, JvmAnnotationNamesKt.getANDROIDX_RECENTLY_NON_NULL_ANNOTATION())) {
        localObject1 = new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, true);
      } else if (Intrinsics.areEqual(localObject1, JvmAnnotationNamesKt.getANDROIDX_RECENTLY_NULLABLE_ANNOTATION())) {
        localObject1 = new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, true);
      } else {
        localObject1 = null;
      }
      localObject3 = localObject2;
      if (localObject1 != null) {
        if ((!((NullabilityQualifierWithMigrationStatus)localObject1).isForWarningOnly()) && ((paramAnnotationDescriptor instanceof PossiblyExternalAnnotationDescriptor)) && (((PossiblyExternalAnnotationDescriptor)paramAnnotationDescriptor).isIdeExternalAnnotation())) {
          localObject3 = NullabilityQualifierWithMigrationStatus.copy$default((NullabilityQualifierWithMigrationStatus)localObject1, null, true, 1, null);
        } else {
          localObject3 = localObject1;
        }
      }
    }
    return localObject3;
  }
  
  private final NullabilityQualifierWithMigrationStatus extractNullabilityTypeFromArgument(AnnotationDescriptor paramAnnotationDescriptor)
  {
    paramAnnotationDescriptor = DescriptorUtilsKt.firstArgument(paramAnnotationDescriptor);
    boolean bool = paramAnnotationDescriptor instanceof EnumValue;
    Object localObject = null;
    if (!bool) {
      paramAnnotationDescriptor = null;
    }
    paramAnnotationDescriptor = (EnumValue)paramAnnotationDescriptor;
    if (paramAnnotationDescriptor != null)
    {
      String str = paramAnnotationDescriptor.getEnumEntryName().asString();
      switch (str.hashCode())
      {
      default: 
        paramAnnotationDescriptor = localObject;
        break;
      case 1933739535: 
        paramAnnotationDescriptor = localObject;
        if (str.equals("ALWAYS")) {
          paramAnnotationDescriptor = new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null);
        }
        break;
      case 433141802: 
      case 74175084: 
      case 73135176: 
        for (;;)
        {
          break;
          paramAnnotationDescriptor = localObject;
          if (!str.equals("UNKNOWN")) {
            break;
          }
          paramAnnotationDescriptor = new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.FORCE_FLEXIBILITY, false, 2, null);
          continue;
          paramAnnotationDescriptor = localObject;
          if (!str.equals("NEVER")) {
            break;
          }
          break label178;
          paramAnnotationDescriptor = localObject;
          if (!str.equals("MAYBE")) {
            break;
          }
          label178:
          paramAnnotationDescriptor = new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, false, 2, null);
        }
      }
      return paramAnnotationDescriptor;
    }
    return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null);
  }
  
  private final boolean hasDefaultValueInAnnotation(ValueParameterDescriptor paramValueParameterDescriptor, KotlinType paramKotlinType)
  {
    AnnotationDefaultValue localAnnotationDefaultValue = UtilKt.getDefaultValueFromAnnotation(paramValueParameterDescriptor);
    boolean bool1 = localAnnotationDefaultValue instanceof StringDefaultValue;
    boolean bool2 = true;
    if (bool1)
    {
      if (UtilsKt.lexicalCastFrom(paramKotlinType, ((StringDefaultValue)localAnnotationDefaultValue).getValue()) != null) {
        bool1 = true;
      } else {
        bool1 = false;
      }
    }
    else if (Intrinsics.areEqual(localAnnotationDefaultValue, NullDefaultValue.INSTANCE))
    {
      bool1 = TypeUtils.acceptsNullable(paramKotlinType);
    }
    else
    {
      if (localAnnotationDefaultValue != null) {
        break label108;
      }
      bool1 = paramValueParameterDescriptor.declaresDefaultValue();
    }
    if ((bool1) && (paramValueParameterDescriptor.getOverriddenDescriptors().isEmpty())) {
      bool1 = bool2;
    } else {
      bool1 = false;
    }
    return bool1;
    label108:
    throw new NoWhenBranchMatchedException();
  }
  
  private final SignatureParts parts(CallableMemberDescriptor paramCallableMemberDescriptor, Annotated paramAnnotated, boolean paramBoolean, LazyJavaResolverContext paramLazyJavaResolverContext, AnnotationTypeQualifierResolver.QualifierApplicabilityType paramQualifierApplicabilityType, Function1<? super CallableMemberDescriptor, ? extends KotlinType> paramFunction1)
  {
    KotlinType localKotlinType = (KotlinType)paramFunction1.invoke(paramCallableMemberDescriptor);
    Collection localCollection = paramCallableMemberDescriptor.getOverriddenDescriptors();
    Intrinsics.checkExpressionValueIsNotNull(localCollection, "this.overriddenDescriptors");
    Object localObject = (Iterable)localCollection;
    localCollection = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject, 10));
    localObject = ((Iterable)localObject).iterator();
    while (((Iterator)localObject).hasNext())
    {
      CallableMemberDescriptor localCallableMemberDescriptor = (CallableMemberDescriptor)((Iterator)localObject).next();
      Intrinsics.checkExpressionValueIsNotNull(localCallableMemberDescriptor, "it");
      localCollection.add((KotlinType)paramFunction1.invoke(localCallableMemberDescriptor));
    }
    return new SignatureParts(paramAnnotated, localKotlinType, (Collection)localCollection, paramBoolean, ContextKt.copyWithNewDefaultTypeQualifiers(paramLazyJavaResolverContext, ((KotlinType)paramFunction1.invoke(paramCallableMemberDescriptor)).getAnnotations()), paramQualifierApplicabilityType);
  }
  
  private final SignatureParts partsForValueParameter(CallableMemberDescriptor paramCallableMemberDescriptor, ValueParameterDescriptor paramValueParameterDescriptor, LazyJavaResolverContext paramLazyJavaResolverContext, Function1<? super CallableMemberDescriptor, ? extends KotlinType> paramFunction1)
  {
    Annotated localAnnotated = (Annotated)paramValueParameterDescriptor;
    if (paramValueParameterDescriptor != null)
    {
      paramValueParameterDescriptor = ContextKt.copyWithNewDefaultTypeQualifiers(paramLazyJavaResolverContext, paramValueParameterDescriptor.getAnnotations());
      if (paramValueParameterDescriptor != null) {}
    }
    else
    {
      paramValueParameterDescriptor = paramLazyJavaResolverContext;
    }
    return parts(paramCallableMemberDescriptor, localAnnotated, false, paramValueParameterDescriptor, AnnotationTypeQualifierResolver.QualifierApplicabilityType.VALUE_PARAMETER, paramFunction1);
  }
  
  public final <D extends CallableMemberDescriptor> Collection<D> enhanceSignatures(LazyJavaResolverContext paramLazyJavaResolverContext, Collection<? extends D> paramCollection)
  {
    Intrinsics.checkParameterIsNotNull(paramLazyJavaResolverContext, "c");
    Intrinsics.checkParameterIsNotNull(paramCollection, "platformSignatures");
    Object localObject = (Iterable)paramCollection;
    paramCollection = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject, 10));
    localObject = ((Iterable)localObject).iterator();
    while (((Iterator)localObject).hasNext()) {
      paramCollection.add(enhanceSignature((CallableMemberDescriptor)((Iterator)localObject).next(), paramLazyJavaResolverContext));
    }
    return (Collection)paramCollection;
  }
  
  public final NullabilityQualifierWithMigrationStatus extractNullability(AnnotationDescriptor paramAnnotationDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotationDescriptor, "annotationDescriptor");
    Object localObject1 = extractNullabilityFromKnownAnnotations(paramAnnotationDescriptor);
    if (localObject1 != null) {
      return localObject1;
    }
    Object localObject2 = this.annotationTypeQualifierResolver.resolveTypeQualifierAnnotation(paramAnnotationDescriptor);
    Object localObject3 = null;
    localObject1 = localObject3;
    if (localObject2 != null)
    {
      paramAnnotationDescriptor = this.annotationTypeQualifierResolver.resolveJsr305AnnotationState(paramAnnotationDescriptor);
      if (paramAnnotationDescriptor.isIgnore()) {
        return null;
      }
      localObject2 = extractNullabilityFromKnownAnnotations((AnnotationDescriptor)localObject2);
      localObject1 = localObject3;
      if (localObject2 != null) {
        localObject1 = NullabilityQualifierWithMigrationStatus.copy$default((NullabilityQualifierWithMigrationStatus)localObject2, null, paramAnnotationDescriptor.isWarning(), 1, null);
      }
    }
    return localObject1;
  }
  
  private static class PartEnhancementResult
  {
    private final boolean containsFunctionN;
    private final KotlinType type;
    private final boolean wereChanges;
    
    public PartEnhancementResult(KotlinType paramKotlinType, boolean paramBoolean1, boolean paramBoolean2)
    {
      this.type = paramKotlinType;
      this.wereChanges = paramBoolean1;
      this.containsFunctionN = paramBoolean2;
    }
    
    public final boolean getContainsFunctionN()
    {
      return this.containsFunctionN;
    }
    
    public final KotlinType getType()
    {
      return this.type;
    }
    
    public final boolean getWereChanges()
    {
      return this.wereChanges;
    }
  }
  
  private final class SignatureParts
  {
    private final AnnotationTypeQualifierResolver.QualifierApplicabilityType containerApplicabilityType;
    private final LazyJavaResolverContext containerContext;
    private final Collection<KotlinType> fromOverridden;
    private final KotlinType fromOverride;
    private final boolean isCovariant;
    private final Annotated typeContainer;
    
    public SignatureParts(KotlinType paramKotlinType, Collection<? extends KotlinType> paramCollection, boolean paramBoolean, LazyJavaResolverContext paramLazyJavaResolverContext, AnnotationTypeQualifierResolver.QualifierApplicabilityType paramQualifierApplicabilityType)
    {
      this.typeContainer = paramKotlinType;
      this.fromOverride = paramCollection;
      this.fromOverridden = paramBoolean;
      this.isCovariant = paramLazyJavaResolverContext;
      this.containerContext = paramQualifierApplicabilityType;
      this.containerApplicabilityType = localObject;
    }
    
    private final Function1<Integer, JavaTypeQualifiers> computeIndexedQualifiersForOverride()
    {
      Object localObject1 = (Iterable)this.fromOverridden;
      Object localObject2 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject1, 10));
      localObject1 = ((Iterable)localObject1).iterator();
      while (((Iterator)localObject1).hasNext()) {
        ((Collection)localObject2).add(toIndexed((KotlinType)((Iterator)localObject1).next()));
      }
      List localList = (List)localObject2;
      localObject1 = toIndexed(this.fromOverride);
      if (this.isCovariant)
      {
        localObject2 = (Iterable)this.fromOverridden;
        if (((localObject2 instanceof Collection)) && (((Collection)localObject2).isEmpty())) {}
        do
        {
          while (!((Iterator)localObject3).hasNext())
          {
            i = 0;
            break;
            localObject3 = ((Iterable)localObject2).iterator();
          }
          localObject2 = (KotlinType)((Iterator)localObject3).next();
        } while (!(KotlinTypeChecker.DEFAULT.equalTypes((KotlinType)localObject2, this.fromOverride) ^ true));
        i = 1;
        if (i != 0)
        {
          i = 1;
          break label182;
        }
      }
      int i = 0;
      label182:
      int j;
      if (i != 0) {
        j = 1;
      } else {
        j = ((List)localObject1).size();
      }
      Object localObject3 = new JavaTypeQualifiers[j];
      for (int k = 0; k < j; k++)
      {
        boolean bool;
        if (k == 0) {
          bool = true;
        } else {
          bool = false;
        }
        int m;
        if ((!bool) && (i != 0)) {
          m = 0;
        } else {
          m = 1;
        }
        if ((_Assertions.ENABLED) && (m == 0)) {
          throw ((Throwable)new AssertionError("Only head type constructors should be computed"));
        }
        localObject2 = (TypeAndDefaultQualifiers)((List)localObject1).get(k);
        KotlinType localKotlinType = ((TypeAndDefaultQualifiers)localObject2).component1();
        JavaTypeQualifiers localJavaTypeQualifiers = ((TypeAndDefaultQualifiers)localObject2).component2();
        localObject2 = (Iterable)localList;
        Collection localCollection = (Collection)new ArrayList();
        Iterator localIterator = ((Iterable)localObject2).iterator();
        while (localIterator.hasNext())
        {
          localObject2 = (TypeAndDefaultQualifiers)CollectionsKt.getOrNull((List)localIterator.next(), k);
          if (localObject2 != null) {
            localObject2 = ((TypeAndDefaultQualifiers)localObject2).getType();
          } else {
            localObject2 = null;
          }
          if (localObject2 != null) {
            localCollection.add(localObject2);
          }
        }
        localObject3[k] = computeQualifiersForOverride(localKotlinType, (Collection)(List)localCollection, localJavaTypeQualifiers, bool);
      }
      (Function1)new Lambda((JavaTypeQualifiers[])localObject3)
      {
        public final JavaTypeQualifiers invoke(int paramAnonymousInt)
        {
          Object localObject = this.$computedResult;
          if ((paramAnonymousInt >= 0) && (paramAnonymousInt <= ArraysKt.getLastIndex((Object[])localObject))) {
            localObject = localObject[paramAnonymousInt];
          } else {
            localObject = JavaTypeQualifiers.Companion.getNONE();
          }
          return localObject;
        }
      };
    }
    
    private final JavaTypeQualifiers computeQualifiersForOverride(KotlinType paramKotlinType, Collection<? extends KotlinType> paramCollection, JavaTypeQualifiers paramJavaTypeQualifiers, boolean paramBoolean)
    {
      paramCollection = (Iterable)paramCollection;
      Object localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault(paramCollection, 10));
      Object localObject2 = paramCollection.iterator();
      while (((Iterator)localObject2).hasNext()) {
        ((Collection)localObject1).add(extractQualifiers((KotlinType)((Iterator)localObject2).next()));
      }
      Iterable localIterable = (Iterable)localObject1;
      Object localObject3 = (Collection)new ArrayList();
      localObject2 = localIterable.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject1 = ((JavaTypeQualifiers)((Iterator)localObject2).next()).getMutability();
        if (localObject1 != null) {
          ((Collection)localObject3).add(localObject1);
        }
      }
      localObject3 = CollectionsKt.toSet((Iterable)localObject3);
      localObject2 = (Collection)new ArrayList();
      Object localObject4 = localIterable.iterator();
      while (((Iterator)localObject4).hasNext())
      {
        localObject1 = ((JavaTypeQualifiers)((Iterator)localObject4).next()).getNullability();
        if (localObject1 != null) {
          ((Collection)localObject2).add(localObject1);
        }
      }
      localObject4 = CollectionsKt.toSet((Iterable)localObject2);
      localObject2 = (Collection)new ArrayList();
      localObject1 = paramCollection.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        paramCollection = extractQualifiers(TypeWithEnhancementKt.unwrapEnhancement((KotlinType)((Iterator)localObject1).next())).getNullability();
        if (paramCollection != null) {
          ((Collection)localObject2).add(paramCollection);
        }
      }
      Set localSet = CollectionsKt.toSet((Iterable)localObject2);
      paramJavaTypeQualifiers = extractQualifiersFromAnnotations(paramKotlinType, paramBoolean, paramJavaTypeQualifiers);
      boolean bool1 = paramJavaTypeQualifiers.isNullabilityQualifierForWarning();
      boolean bool2 = true;
      localObject2 = null;
      if ((bool1 ^ true)) {
        paramKotlinType = paramJavaTypeQualifiers;
      } else {
        paramKotlinType = null;
      }
      if (paramKotlinType != null) {
        paramKotlinType = paramKotlinType.getNullability();
      } else {
        paramKotlinType = null;
      }
      NullabilityQualifier localNullabilityQualifier = paramJavaTypeQualifiers.getNullability();
      if ((this.isCovariant) && (paramBoolean)) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      localObject1 = SignatureEnhancementKt.select((Set)localObject4, paramKotlinType, bool1);
      paramCollection = (Collection<? extends KotlinType>)localObject2;
      int i;
      if (localObject1 != null)
      {
        if ((isForVarargParameter()) && (paramBoolean) && (localObject1 == NullabilityQualifier.NULLABLE)) {
          i = 1;
        } else {
          i = 0;
        }
        paramCollection = (Collection<? extends KotlinType>)localObject2;
        if (i == 0) {
          paramCollection = (Collection<? extends KotlinType>)localObject1;
        }
      }
      localObject2 = (MutabilityQualifier)SignatureEnhancementKt.select((Set)localObject3, MutabilityQualifier.MUTABLE, MutabilityQualifier.READ_ONLY, paramJavaTypeQualifiers.getMutability(), bool1);
      if ((localNullabilityQualifier == paramKotlinType) && (!(Intrinsics.areEqual(localSet, localObject4) ^ true))) {
        i = 0;
      } else {
        i = 1;
      }
      if (!paramJavaTypeQualifiers.isNotNullTypeParameter())
      {
        if (((localIterable instanceof Collection)) && (((Collection)localIterable).isEmpty())) {}
        do
        {
          while (!paramKotlinType.hasNext())
          {
            j = 0;
            break;
            paramKotlinType = localIterable.iterator();
          }
        } while (!((JavaTypeQualifiers)paramKotlinType.next()).isNotNullTypeParameter());
        int j = 1;
        if (j == 0)
        {
          paramBoolean = false;
          break label582;
        }
      }
      paramBoolean = true;
      label582:
      if ((paramCollection == null) && (i != 0)) {
        return SignatureEnhancementKt.createJavaTypeQualifiers(SignatureEnhancementKt.select(localSet, localNullabilityQualifier, bool1), (MutabilityQualifier)localObject2, true, paramBoolean);
      }
      if (paramCollection == null) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
      return SignatureEnhancementKt.createJavaTypeQualifiers(paramCollection, (MutabilityQualifier)localObject2, bool1, paramBoolean);
    }
    
    private final NullabilityQualifierWithMigrationStatus extractNullability(Annotations paramAnnotations)
    {
      paramAnnotations = (Iterable)paramAnnotations;
      SignatureEnhancement localSignatureEnhancement = SignatureEnhancement.this;
      Iterator localIterator = paramAnnotations.iterator();
      while (localIterator.hasNext())
      {
        paramAnnotations = localSignatureEnhancement.extractNullability((AnnotationDescriptor)localIterator.next());
        if (paramAnnotations != null) {
          return paramAnnotations;
        }
      }
      paramAnnotations = null;
      return paramAnnotations;
    }
    
    private final JavaTypeQualifiers extractQualifiers(KotlinType paramKotlinType)
    {
      if (FlexibleTypesKt.isFlexible(paramKotlinType))
      {
        localObject1 = FlexibleTypesKt.asFlexibleType(paramKotlinType);
        localObject1 = new Pair(((FlexibleType)localObject1).getLowerBound(), ((FlexibleType)localObject1).getUpperBound());
      }
      else
      {
        localObject1 = new Pair(paramKotlinType, paramKotlinType);
      }
      KotlinType localKotlinType1 = (KotlinType)((Pair)localObject1).component1();
      KotlinType localKotlinType2 = (KotlinType)((Pair)localObject1).component2();
      JavaToKotlinClassMap localJavaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
      if (localKotlinType1.isMarkedNullable()) {}
      for (Object localObject1 = NullabilityQualifier.NULLABLE;; localObject1 = NullabilityQualifier.NOT_NULL)
      {
        localObject2 = localObject1;
        break label98;
        if (localKotlinType2.isMarkedNullable()) {
          break;
        }
      }
      Object localObject2 = null;
      label98:
      if (localJavaToKotlinClassMap.isReadOnly(localKotlinType1)) {
        localObject1 = MutabilityQualifier.READ_ONLY;
      } else if (localJavaToKotlinClassMap.isMutable(localKotlinType2)) {
        localObject1 = MutabilityQualifier.MUTABLE;
      } else {
        localObject1 = null;
      }
      return new JavaTypeQualifiers(localObject2, (MutabilityQualifier)localObject1, paramKotlinType.unwrap() instanceof NotNullTypeParameter, false, 8, null);
    }
    
    private final JavaTypeQualifiers extractQualifiersFromAnnotations(KotlinType paramKotlinType, boolean paramBoolean, JavaTypeQualifiers paramJavaTypeQualifiers)
    {
      if (paramBoolean)
      {
        localObject1 = this.typeContainer;
        if (localObject1 != null)
        {
          localObject1 = AnnotationsKt.composeAnnotations(((Annotated)localObject1).getAnnotations(), paramKotlinType.getAnnotations());
          break label40;
        }
      }
      Object localObject1 = paramKotlinType.getAnnotations();
      label40:
      Object localObject2 = new Lambda((Annotations)localObject1)
      {
        public final <T> T invoke(List<FqName> paramAnonymousList, T paramAnonymousT)
        {
          Intrinsics.checkParameterIsNotNull(paramAnonymousList, "$this$ifPresent");
          Intrinsics.checkParameterIsNotNull(paramAnonymousT, "qualifier");
          paramAnonymousList = (Iterable)paramAnonymousList;
          boolean bool = paramAnonymousList instanceof Collection;
          int i = 1;
          if ((bool) && (((Collection)paramAnonymousList).isEmpty())) {}
          do
          {
            while (!paramAnonymousList.hasNext())
            {
              j = 0;
              break;
              paramAnonymousList = paramAnonymousList.iterator();
            }
            FqName localFqName = (FqName)paramAnonymousList.next();
            if (this.$composedAnnotation.findAnnotation(localFqName) != null) {
              j = 1;
            } else {
              j = 0;
            }
          } while (j == 0);
          int j = i;
          if (j == 0) {
            paramAnonymousT = null;
          }
          return paramAnonymousT;
        }
      };
      Object localObject3 = extractQualifiersFromAnnotations.2.INSTANCE;
      NullabilityQualifier localNullabilityQualifier = null;
      if (paramBoolean)
      {
        paramJavaTypeQualifiers = this.containerContext.getDefaultTypeQualifiers();
        if (paramJavaTypeQualifiers != null) {
          paramJavaTypeQualifiers = paramJavaTypeQualifiers.get(this.containerApplicabilityType);
        } else {
          paramJavaTypeQualifiers = null;
        }
      }
      localObject1 = extractNullability((Annotations)localObject1);
      if (localObject1 != null) {
        paramJavaTypeQualifiers = (JavaTypeQualifiers)localObject1;
      } else if ((paramJavaTypeQualifiers != null) && (paramJavaTypeQualifiers.getNullability() != null)) {
        paramJavaTypeQualifiers = new NullabilityQualifierWithMigrationStatus(paramJavaTypeQualifiers.getNullability(), paramJavaTypeQualifiers.isNullabilityQualifierForWarning());
      } else {
        paramJavaTypeQualifiers = null;
      }
      if (paramJavaTypeQualifiers != null) {
        localObject1 = paramJavaTypeQualifiers.getQualifier();
      } else {
        localObject1 = null;
      }
      localObject2 = (MutabilityQualifier)((extractQualifiersFromAnnotations.2)localObject3).invoke(((extractQualifiersFromAnnotations.1)localObject2).invoke(JvmAnnotationNamesKt.getREAD_ONLY_ANNOTATIONS(), MutabilityQualifier.READ_ONLY), ((extractQualifiersFromAnnotations.1)localObject2).invoke(JvmAnnotationNamesKt.getMUTABLE_ANNOTATIONS(), MutabilityQualifier.MUTABLE));
      if (paramJavaTypeQualifiers != null) {
        localNullabilityQualifier = paramJavaTypeQualifiers.getQualifier();
      }
      localObject3 = NullabilityQualifier.NOT_NULL;
      boolean bool1 = false;
      if ((localNullabilityQualifier == localObject3) && (TypeUtilsKt.isTypeParameter(paramKotlinType))) {
        paramBoolean = true;
      } else {
        paramBoolean = false;
      }
      boolean bool2 = bool1;
      if (paramJavaTypeQualifiers != null)
      {
        bool2 = bool1;
        if (paramJavaTypeQualifiers.isForWarningOnly() == true) {
          bool2 = true;
        }
      }
      return new JavaTypeQualifiers((NullabilityQualifier)localObject1, (MutabilityQualifier)localObject2, paramBoolean, bool2);
    }
    
    private final boolean isForVarargParameter()
    {
      Object localObject1 = this.typeContainer;
      boolean bool = localObject1 instanceof ValueParameterDescriptor;
      Object localObject2 = null;
      if (!bool) {
        localObject1 = null;
      }
      ValueParameterDescriptor localValueParameterDescriptor = (ValueParameterDescriptor)localObject1;
      localObject1 = localObject2;
      if (localValueParameterDescriptor != null) {
        localObject1 = localValueParameterDescriptor.getVarargElementType();
      }
      if (localObject1 != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    private final List<TypeAndDefaultQualifiers> toIndexed(KotlinType paramKotlinType)
    {
      ArrayList localArrayList = new ArrayList(1);
      new Lambda(localArrayList)
      {
        public final void invoke(KotlinType paramAnonymousKotlinType, LazyJavaResolverContext paramAnonymousLazyJavaResolverContext)
        {
          Intrinsics.checkParameterIsNotNull(paramAnonymousKotlinType, "type");
          Intrinsics.checkParameterIsNotNull(paramAnonymousLazyJavaResolverContext, "ownerContext");
          LazyJavaResolverContext localLazyJavaResolverContext = ContextKt.copyWithNewDefaultTypeQualifiers(paramAnonymousLazyJavaResolverContext, paramAnonymousKotlinType.getAnnotations());
          Object localObject = this.$list;
          paramAnonymousLazyJavaResolverContext = localLazyJavaResolverContext.getDefaultTypeQualifiers();
          if (paramAnonymousLazyJavaResolverContext != null) {
            paramAnonymousLazyJavaResolverContext = paramAnonymousLazyJavaResolverContext.get(AnnotationTypeQualifierResolver.QualifierApplicabilityType.TYPE_USE);
          } else {
            paramAnonymousLazyJavaResolverContext = null;
          }
          ((ArrayList)localObject).add(new TypeAndDefaultQualifiers(paramAnonymousKotlinType, paramAnonymousLazyJavaResolverContext));
          paramAnonymousKotlinType = paramAnonymousKotlinType.getArguments().iterator();
          while (paramAnonymousKotlinType.hasNext())
          {
            localObject = (TypeProjection)paramAnonymousKotlinType.next();
            if (((TypeProjection)localObject).isStarProjection())
            {
              paramAnonymousLazyJavaResolverContext = this.$list;
              localObject = ((TypeProjection)localObject).getType();
              Intrinsics.checkExpressionValueIsNotNull(localObject, "arg.type");
              paramAnonymousLazyJavaResolverContext.add(new TypeAndDefaultQualifiers((KotlinType)localObject, null));
            }
            else
            {
              paramAnonymousLazyJavaResolverContext = (1)this;
              localObject = ((TypeProjection)localObject).getType();
              Intrinsics.checkExpressionValueIsNotNull(localObject, "arg.type");
              paramAnonymousLazyJavaResolverContext.invoke((KotlinType)localObject, localLazyJavaResolverContext);
            }
          }
        }
      }.invoke(paramKotlinType, this.containerContext);
      return (List)localArrayList;
    }
    
    public final SignatureEnhancement.PartEnhancementResult enhance(TypeEnhancementInfo paramTypeEnhancementInfo)
    {
      Object localObject = computeIndexedQualifiersForOverride();
      if (paramTypeEnhancementInfo != null) {
        paramTypeEnhancementInfo = (Function1)new Lambda(paramTypeEnhancementInfo)
        {
          public final JavaTypeQualifiers invoke(int paramAnonymousInt)
          {
            JavaTypeQualifiers localJavaTypeQualifiers = (JavaTypeQualifiers)this.$predefined$inlined.getMap().get(Integer.valueOf(paramAnonymousInt));
            if (localJavaTypeQualifiers == null) {
              localJavaTypeQualifiers = (JavaTypeQualifiers)this.$qualifiers$inlined.invoke(Integer.valueOf(paramAnonymousInt));
            }
            return localJavaTypeQualifiers;
          }
        };
      } else {
        paramTypeEnhancementInfo = null;
      }
      boolean bool = TypeUtils.contains(this.fromOverride, (Function1)enhance.containsFunctionN.1.INSTANCE);
      KotlinType localKotlinType = this.fromOverride;
      if (paramTypeEnhancementInfo != null) {
        localObject = paramTypeEnhancementInfo;
      }
      paramTypeEnhancementInfo = TypeEnhancementKt.enhance(localKotlinType, (Function1)localObject);
      if (paramTypeEnhancementInfo != null) {
        paramTypeEnhancementInfo = new SignatureEnhancement.PartEnhancementResult(paramTypeEnhancementInfo, true, bool);
      } else {
        paramTypeEnhancementInfo = new SignatureEnhancement.PartEnhancementResult(this.fromOverride, false, bool);
      }
      return paramTypeEnhancementInfo;
    }
  }
  
  private static final class ValueParameterEnhancementResult
    extends SignatureEnhancement.PartEnhancementResult
  {
    private final boolean hasDefaultValue;
    
    public ValueParameterEnhancementResult(KotlinType paramKotlinType, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    {
      super(paramBoolean2, paramBoolean3);
      this.hasDefaultValue = paramBoolean1;
    }
    
    public final boolean getHasDefaultValue()
    {
      return this.hasDefaultValue;
    }
  }
}

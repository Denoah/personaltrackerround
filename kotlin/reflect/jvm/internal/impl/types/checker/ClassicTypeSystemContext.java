package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.Collection;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns.FqNames;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModalityKt;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedType;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntegerLiteralTypeConstructor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext;
import kotlin.reflect.jvm.internal.impl.types.DefinitelyNotNullType;
import kotlin.reflect.jvm.internal.impl.types.DynamicType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.StubType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext;
import kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext.DefaultImpls;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.model.CaptureStatus;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.DefinitelyNotNullTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.DynamicTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.FlexibleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentListMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeParameterMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemInferenceExtensionContext;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemInferenceExtensionContext.DefaultImpls;
import kotlin.reflect.jvm.internal.impl.types.model.TypeVariance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

public abstract interface ClassicTypeSystemContext
  extends TypeSystemCommonBackendContext, TypeSystemInferenceExtensionContext
{
  public abstract SimpleTypeMarker asSimpleType(KotlinTypeMarker paramKotlinTypeMarker);
  
  public abstract TypeConstructorMarker typeConstructor(SimpleTypeMarker paramSimpleTypeMarker);
  
  public static final class DefaultImpls
  {
    public static int argumentsCount(ClassicTypeSystemContext paramClassicTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$argumentsCount");
      if ((paramKotlinTypeMarker instanceof KotlinType)) {
        return ((KotlinType)paramKotlinTypeMarker).getArguments().size();
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramKotlinTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramKotlinTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static TypeArgumentListMarker asArgumentList(ClassicTypeSystemContext paramClassicTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$asArgumentList");
      if ((paramSimpleTypeMarker instanceof SimpleType)) {
        return (TypeArgumentListMarker)paramSimpleTypeMarker;
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramSimpleTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramSimpleTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static CapturedTypeMarker asCapturedType(ClassicTypeSystemContext paramClassicTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$asCapturedType");
      if ((paramSimpleTypeMarker instanceof SimpleType))
      {
        paramClassicTypeSystemContext = paramSimpleTypeMarker;
        if (!(paramSimpleTypeMarker instanceof NewCapturedType)) {
          paramClassicTypeSystemContext = null;
        }
        return (CapturedTypeMarker)paramClassicTypeSystemContext;
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramSimpleTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramSimpleTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static DefinitelyNotNullTypeMarker asDefinitelyNotNullType(ClassicTypeSystemContext paramClassicTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$asDefinitelyNotNullType");
      if ((paramSimpleTypeMarker instanceof SimpleType))
      {
        paramClassicTypeSystemContext = paramSimpleTypeMarker;
        if (!(paramSimpleTypeMarker instanceof DefinitelyNotNullType)) {
          paramClassicTypeSystemContext = null;
        }
        return (DefinitelyNotNullTypeMarker)paramClassicTypeSystemContext;
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramSimpleTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramSimpleTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static DynamicTypeMarker asDynamicType(ClassicTypeSystemContext paramClassicTypeSystemContext, FlexibleTypeMarker paramFlexibleTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramFlexibleTypeMarker, "$this$asDynamicType");
      if ((paramFlexibleTypeMarker instanceof FlexibleType))
      {
        paramClassicTypeSystemContext = paramFlexibleTypeMarker;
        if (!(paramFlexibleTypeMarker instanceof DynamicType)) {
          paramClassicTypeSystemContext = null;
        }
        return (DynamicTypeMarker)paramClassicTypeSystemContext;
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramFlexibleTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramFlexibleTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static FlexibleTypeMarker asFlexibleType(ClassicTypeSystemContext paramClassicTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$asFlexibleType");
      if ((paramKotlinTypeMarker instanceof KotlinType))
      {
        paramKotlinTypeMarker = ((KotlinType)paramKotlinTypeMarker).unwrap();
        paramClassicTypeSystemContext = paramKotlinTypeMarker;
        if (!(paramKotlinTypeMarker instanceof FlexibleType)) {
          paramClassicTypeSystemContext = null;
        }
        return (FlexibleTypeMarker)paramClassicTypeSystemContext;
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramKotlinTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramKotlinTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static SimpleTypeMarker asSimpleType(ClassicTypeSystemContext paramClassicTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$asSimpleType");
      if ((paramKotlinTypeMarker instanceof KotlinType))
      {
        paramKotlinTypeMarker = ((KotlinType)paramKotlinTypeMarker).unwrap();
        paramClassicTypeSystemContext = paramKotlinTypeMarker;
        if (!(paramKotlinTypeMarker instanceof SimpleType)) {
          paramClassicTypeSystemContext = null;
        }
        return (SimpleTypeMarker)paramClassicTypeSystemContext;
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramKotlinTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramKotlinTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static TypeArgumentMarker asTypeArgument(ClassicTypeSystemContext paramClassicTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$asTypeArgument");
      if ((paramKotlinTypeMarker instanceof KotlinType)) {
        return (TypeArgumentMarker)TypeUtilsKt.asTypeProjection((KotlinType)paramKotlinTypeMarker);
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramKotlinTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramKotlinTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static SimpleTypeMarker captureFromArguments(ClassicTypeSystemContext paramClassicTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker, CaptureStatus paramCaptureStatus)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "type");
      Intrinsics.checkParameterIsNotNull(paramCaptureStatus, "status");
      if ((paramSimpleTypeMarker instanceof SimpleType)) {
        return (SimpleTypeMarker)NewCapturedTypeKt.captureFromArguments((SimpleType)paramSimpleTypeMarker, paramCaptureStatus);
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramSimpleTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramSimpleTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static List<SimpleTypeMarker> fastCorrespondingSupertypes(ClassicTypeSystemContext paramClassicTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker, TypeConstructorMarker paramTypeConstructorMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$fastCorrespondingSupertypes");
      Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "constructor");
      return TypeSystemInferenceExtensionContext.DefaultImpls.fastCorrespondingSupertypes((TypeSystemInferenceExtensionContext)paramClassicTypeSystemContext, paramSimpleTypeMarker, paramTypeConstructorMarker);
    }
    
    public static TypeArgumentMarker get(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeArgumentListMarker paramTypeArgumentListMarker, int paramInt)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeArgumentListMarker, "$this$get");
      return TypeSystemInferenceExtensionContext.DefaultImpls.get((TypeSystemInferenceExtensionContext)paramClassicTypeSystemContext, paramTypeArgumentListMarker, paramInt);
    }
    
    public static TypeArgumentMarker getArgument(ClassicTypeSystemContext paramClassicTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker, int paramInt)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$getArgument");
      if ((paramKotlinTypeMarker instanceof KotlinType)) {
        return (TypeArgumentMarker)((KotlinType)paramKotlinTypeMarker).getArguments().get(paramInt);
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramKotlinTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramKotlinTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static TypeArgumentMarker getArgumentOrNull(ClassicTypeSystemContext paramClassicTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker, int paramInt)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$getArgumentOrNull");
      return TypeSystemInferenceExtensionContext.DefaultImpls.getArgumentOrNull((TypeSystemInferenceExtensionContext)paramClassicTypeSystemContext, paramSimpleTypeMarker, paramInt);
    }
    
    public static FqNameUnsafe getClassFqNameUnsafe(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeConstructorMarker paramTypeConstructorMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$getClassFqNameUnsafe");
      if ((paramTypeConstructorMarker instanceof TypeConstructor))
      {
        paramClassicTypeSystemContext = ((TypeConstructor)paramTypeConstructorMarker).getDeclarationDescriptor();
        if (paramClassicTypeSystemContext != null) {
          return DescriptorUtilsKt.getFqNameUnsafe((DeclarationDescriptor)paramClassicTypeSystemContext);
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramTypeConstructorMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeConstructorMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static TypeParameterMarker getParameter(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeConstructorMarker paramTypeConstructorMarker, int paramInt)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$getParameter");
      if ((paramTypeConstructorMarker instanceof TypeConstructor))
      {
        paramClassicTypeSystemContext = ((TypeConstructor)paramTypeConstructorMarker).getParameters().get(paramInt);
        Intrinsics.checkExpressionValueIsNotNull(paramClassicTypeSystemContext, "this.parameters[index]");
        return (TypeParameterMarker)paramClassicTypeSystemContext;
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramTypeConstructorMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeConstructorMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static PrimitiveType getPrimitiveArrayType(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeConstructorMarker paramTypeConstructorMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$getPrimitiveArrayType");
      if ((paramTypeConstructorMarker instanceof TypeConstructor))
      {
        paramClassicTypeSystemContext = ((TypeConstructor)paramTypeConstructorMarker).getDeclarationDescriptor();
        if (paramClassicTypeSystemContext != null) {
          return KotlinBuiltIns.getPrimitiveArrayType((DeclarationDescriptor)paramClassicTypeSystemContext);
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramTypeConstructorMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeConstructorMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static PrimitiveType getPrimitiveType(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeConstructorMarker paramTypeConstructorMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$getPrimitiveType");
      if ((paramTypeConstructorMarker instanceof TypeConstructor))
      {
        paramClassicTypeSystemContext = ((TypeConstructor)paramTypeConstructorMarker).getDeclarationDescriptor();
        if (paramClassicTypeSystemContext != null) {
          return KotlinBuiltIns.getPrimitiveType((DeclarationDescriptor)paramClassicTypeSystemContext);
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramTypeConstructorMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeConstructorMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static KotlinTypeMarker getRepresentativeUpperBound(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeParameterMarker paramTypeParameterMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeParameterMarker, "$this$getRepresentativeUpperBound");
      if ((paramTypeParameterMarker instanceof TypeParameterDescriptor)) {
        return (KotlinTypeMarker)TypeUtilsKt.getRepresentativeUpperBound((TypeParameterDescriptor)paramTypeParameterMarker);
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramTypeParameterMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeParameterMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static KotlinTypeMarker getSubstitutedUnderlyingType(ClassicTypeSystemContext paramClassicTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$getSubstitutedUnderlyingType");
      if ((paramKotlinTypeMarker instanceof KotlinType)) {
        return (KotlinTypeMarker)InlineClassesUtilsKt.substitutedUnderlyingType((KotlinType)paramKotlinTypeMarker);
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramKotlinTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramKotlinTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static KotlinTypeMarker getType(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeArgumentMarker paramTypeArgumentMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeArgumentMarker, "$this$getType");
      if ((paramTypeArgumentMarker instanceof TypeProjection)) {
        return (KotlinTypeMarker)((TypeProjection)paramTypeArgumentMarker).getType().unwrap();
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramTypeArgumentMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeArgumentMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static TypeParameterMarker getTypeParameterClassifier(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeConstructorMarker paramTypeConstructorMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$getTypeParameterClassifier");
      if ((paramTypeConstructorMarker instanceof TypeConstructor))
      {
        paramTypeConstructorMarker = ((TypeConstructor)paramTypeConstructorMarker).getDeclarationDescriptor();
        paramClassicTypeSystemContext = paramTypeConstructorMarker;
        if (!(paramTypeConstructorMarker instanceof TypeParameterDescriptor)) {
          paramClassicTypeSystemContext = null;
        }
        return (TypeParameterMarker)paramClassicTypeSystemContext;
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramTypeConstructorMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeConstructorMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static TypeVariance getVariance(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeArgumentMarker paramTypeArgumentMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeArgumentMarker, "$this$getVariance");
      if ((paramTypeArgumentMarker instanceof TypeProjection))
      {
        paramClassicTypeSystemContext = ((TypeProjection)paramTypeArgumentMarker).getProjectionKind();
        Intrinsics.checkExpressionValueIsNotNull(paramClassicTypeSystemContext, "this.projectionKind");
        return ClassicTypeSystemContextKt.convertVariance(paramClassicTypeSystemContext);
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramTypeArgumentMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeArgumentMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static TypeVariance getVariance(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeParameterMarker paramTypeParameterMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeParameterMarker, "$this$getVariance");
      if ((paramTypeParameterMarker instanceof TypeParameterDescriptor))
      {
        paramClassicTypeSystemContext = ((TypeParameterDescriptor)paramTypeParameterMarker).getVariance();
        Intrinsics.checkExpressionValueIsNotNull(paramClassicTypeSystemContext, "this.variance");
        return ClassicTypeSystemContextKt.convertVariance(paramClassicTypeSystemContext);
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramTypeParameterMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeParameterMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static boolean hasAnnotation(ClassicTypeSystemContext paramClassicTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker, FqName paramFqName)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$hasAnnotation");
      Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
      if ((paramKotlinTypeMarker instanceof KotlinType)) {
        return ((KotlinType)paramKotlinTypeMarker).getAnnotations().hasAnnotation(paramFqName);
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramKotlinTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramKotlinTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static boolean hasFlexibleNullability(ClassicTypeSystemContext paramClassicTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$hasFlexibleNullability");
      return TypeSystemInferenceExtensionContext.DefaultImpls.hasFlexibleNullability((TypeSystemInferenceExtensionContext)paramClassicTypeSystemContext, paramKotlinTypeMarker);
    }
    
    public static boolean identicalArguments(ClassicTypeSystemContext paramClassicTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker1, SimpleTypeMarker paramSimpleTypeMarker2)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker1, "a");
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker2, "b");
      if ((paramSimpleTypeMarker1 instanceof SimpleType))
      {
        if ((paramSimpleTypeMarker2 instanceof SimpleType))
        {
          boolean bool;
          if (((SimpleType)paramSimpleTypeMarker1).getArguments() == ((SimpleType)paramSimpleTypeMarker2).getArguments()) {
            bool = true;
          } else {
            bool = false;
          }
          return bool;
        }
        paramClassicTypeSystemContext = new StringBuilder();
        paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
        paramClassicTypeSystemContext.append(paramSimpleTypeMarker2);
        paramClassicTypeSystemContext.append(", ");
        paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramSimpleTypeMarker2.getClass()));
        throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramSimpleTypeMarker1);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramSimpleTypeMarker1.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static KotlinTypeMarker intersectTypes(ClassicTypeSystemContext paramClassicTypeSystemContext, List<? extends KotlinTypeMarker> paramList)
    {
      Intrinsics.checkParameterIsNotNull(paramList, "types");
      return (KotlinTypeMarker)IntersectionTypeKt.intersectTypes(paramList);
    }
    
    public static boolean isAnyConstructor(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeConstructorMarker paramTypeConstructorMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$isAnyConstructor");
      if ((paramTypeConstructorMarker instanceof TypeConstructor)) {
        return KotlinBuiltIns.isTypeConstructorForGivenClass((TypeConstructor)paramTypeConstructorMarker, KotlinBuiltIns.FQ_NAMES.any);
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramTypeConstructorMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeConstructorMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static boolean isClassType(ClassicTypeSystemContext paramClassicTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$isClassType");
      return TypeSystemInferenceExtensionContext.DefaultImpls.isClassType((TypeSystemInferenceExtensionContext)paramClassicTypeSystemContext, paramSimpleTypeMarker);
    }
    
    public static boolean isClassTypeConstructor(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeConstructorMarker paramTypeConstructorMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$isClassTypeConstructor");
      if ((paramTypeConstructorMarker instanceof TypeConstructor)) {
        return ((TypeConstructor)paramTypeConstructorMarker).getDeclarationDescriptor() instanceof ClassDescriptor;
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramTypeConstructorMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeConstructorMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static boolean isCommonFinalClassConstructor(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeConstructorMarker paramTypeConstructorMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$isCommonFinalClassConstructor");
      if ((paramTypeConstructorMarker instanceof TypeConstructor))
      {
        paramTypeConstructorMarker = ((TypeConstructor)paramTypeConstructorMarker).getDeclarationDescriptor();
        paramClassicTypeSystemContext = paramTypeConstructorMarker;
        if (!(paramTypeConstructorMarker instanceof ClassDescriptor)) {
          paramClassicTypeSystemContext = null;
        }
        paramClassicTypeSystemContext = (ClassDescriptor)paramClassicTypeSystemContext;
        boolean bool1 = false;
        boolean bool2 = bool1;
        if (paramClassicTypeSystemContext != null)
        {
          bool2 = bool1;
          if (ModalityKt.isFinalClass(paramClassicTypeSystemContext))
          {
            bool2 = bool1;
            if (paramClassicTypeSystemContext.getKind() != ClassKind.ENUM_ENTRY)
            {
              bool2 = bool1;
              if (paramClassicTypeSystemContext.getKind() != ClassKind.ANNOTATION_CLASS) {
                bool2 = true;
              }
            }
          }
        }
        return bool2;
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramTypeConstructorMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeConstructorMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static boolean isDefinitelyNotNullType(ClassicTypeSystemContext paramClassicTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isDefinitelyNotNullType");
      return TypeSystemInferenceExtensionContext.DefaultImpls.isDefinitelyNotNullType((TypeSystemInferenceExtensionContext)paramClassicTypeSystemContext, paramKotlinTypeMarker);
    }
    
    public static boolean isDenotable(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeConstructorMarker paramTypeConstructorMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$isDenotable");
      if ((paramTypeConstructorMarker instanceof TypeConstructor)) {
        return ((TypeConstructor)paramTypeConstructorMarker).isDenotable();
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramTypeConstructorMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeConstructorMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static boolean isDynamic(ClassicTypeSystemContext paramClassicTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isDynamic");
      return TypeSystemInferenceExtensionContext.DefaultImpls.isDynamic((TypeSystemInferenceExtensionContext)paramClassicTypeSystemContext, paramKotlinTypeMarker);
    }
    
    public static boolean isEqualTypeConstructors(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeConstructorMarker paramTypeConstructorMarker1, TypeConstructorMarker paramTypeConstructorMarker2)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker1, "c1");
      Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker2, "c2");
      if ((paramTypeConstructorMarker1 instanceof TypeConstructor))
      {
        if ((paramTypeConstructorMarker2 instanceof TypeConstructor)) {
          return Intrinsics.areEqual(paramTypeConstructorMarker1, paramTypeConstructorMarker2);
        }
        paramClassicTypeSystemContext = new StringBuilder();
        paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
        paramClassicTypeSystemContext.append(paramTypeConstructorMarker2);
        paramClassicTypeSystemContext.append(", ");
        paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeConstructorMarker2.getClass()));
        throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramTypeConstructorMarker1);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeConstructorMarker1.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static boolean isError(ClassicTypeSystemContext paramClassicTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isError");
      if ((paramKotlinTypeMarker instanceof KotlinType)) {
        return KotlinTypeKt.isError((KotlinType)paramKotlinTypeMarker);
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramKotlinTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramKotlinTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static boolean isInlineClass(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeConstructorMarker paramTypeConstructorMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$isInlineClass");
      if ((paramTypeConstructorMarker instanceof TypeConstructor))
      {
        paramTypeConstructorMarker = ((TypeConstructor)paramTypeConstructorMarker).getDeclarationDescriptor();
        paramClassicTypeSystemContext = paramTypeConstructorMarker;
        if (!(paramTypeConstructorMarker instanceof ClassDescriptor)) {
          paramClassicTypeSystemContext = null;
        }
        paramClassicTypeSystemContext = (ClassDescriptor)paramClassicTypeSystemContext;
        boolean bool = true;
        if ((paramClassicTypeSystemContext == null) || (paramClassicTypeSystemContext.isInline() != true)) {
          bool = false;
        }
        return bool;
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramTypeConstructorMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeConstructorMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static boolean isIntegerLiteralType(ClassicTypeSystemContext paramClassicTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$isIntegerLiteralType");
      return TypeSystemInferenceExtensionContext.DefaultImpls.isIntegerLiteralType((TypeSystemInferenceExtensionContext)paramClassicTypeSystemContext, paramSimpleTypeMarker);
    }
    
    public static boolean isIntegerLiteralTypeConstructor(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeConstructorMarker paramTypeConstructorMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$isIntegerLiteralTypeConstructor");
      if ((paramTypeConstructorMarker instanceof TypeConstructor)) {
        return paramTypeConstructorMarker instanceof IntegerLiteralTypeConstructor;
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramTypeConstructorMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeConstructorMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static boolean isIntersection(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeConstructorMarker paramTypeConstructorMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$isIntersection");
      if ((paramTypeConstructorMarker instanceof TypeConstructor)) {
        return paramTypeConstructorMarker instanceof IntersectionTypeConstructor;
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramTypeConstructorMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeConstructorMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static boolean isMarkedNullable(ClassicTypeSystemContext paramClassicTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isMarkedNullable");
      return TypeSystemCommonBackendContext.DefaultImpls.isMarkedNullable((TypeSystemCommonBackendContext)paramClassicTypeSystemContext, paramKotlinTypeMarker);
    }
    
    public static boolean isMarkedNullable(ClassicTypeSystemContext paramClassicTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$isMarkedNullable");
      if ((paramSimpleTypeMarker instanceof SimpleType)) {
        return ((SimpleType)paramSimpleTypeMarker).isMarkedNullable();
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramSimpleTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramSimpleTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static boolean isNothing(ClassicTypeSystemContext paramClassicTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isNothing");
      return TypeSystemInferenceExtensionContext.DefaultImpls.isNothing((TypeSystemInferenceExtensionContext)paramClassicTypeSystemContext, paramKotlinTypeMarker);
    }
    
    public static boolean isNothingConstructor(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeConstructorMarker paramTypeConstructorMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$isNothingConstructor");
      if ((paramTypeConstructorMarker instanceof TypeConstructor)) {
        return KotlinBuiltIns.isTypeConstructorForGivenClass((TypeConstructor)paramTypeConstructorMarker, KotlinBuiltIns.FQ_NAMES.nothing);
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramTypeConstructorMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeConstructorMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static boolean isNullableType(ClassicTypeSystemContext paramClassicTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isNullableType");
      if ((paramKotlinTypeMarker instanceof KotlinType)) {
        return TypeUtils.isNullableType((KotlinType)paramKotlinTypeMarker);
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramKotlinTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramKotlinTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static boolean isPrimitiveType(ClassicTypeSystemContext paramClassicTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$isPrimitiveType");
      if ((paramSimpleTypeMarker instanceof KotlinType)) {
        return KotlinBuiltIns.isPrimitiveType((KotlinType)paramSimpleTypeMarker);
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramSimpleTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramSimpleTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static boolean isSingleClassifierType(ClassicTypeSystemContext paramClassicTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$isSingleClassifierType");
      if ((paramSimpleTypeMarker instanceof SimpleType))
      {
        if (!KotlinTypeKt.isError((KotlinType)paramSimpleTypeMarker))
        {
          paramClassicTypeSystemContext = (SimpleType)paramSimpleTypeMarker;
          if ((!(paramClassicTypeSystemContext.getConstructor().getDeclarationDescriptor() instanceof TypeAliasDescriptor)) && ((paramClassicTypeSystemContext.getConstructor().getDeclarationDescriptor() != null) || ((paramSimpleTypeMarker instanceof CapturedType)) || ((paramSimpleTypeMarker instanceof NewCapturedType)) || ((paramSimpleTypeMarker instanceof DefinitelyNotNullType)) || ((paramClassicTypeSystemContext.getConstructor() instanceof IntegerLiteralTypeConstructor))))
          {
            bool = true;
            break label94;
          }
        }
        boolean bool = false;
        label94:
        return bool;
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramSimpleTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramSimpleTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static boolean isStarProjection(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeArgumentMarker paramTypeArgumentMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeArgumentMarker, "$this$isStarProjection");
      if ((paramTypeArgumentMarker instanceof TypeProjection)) {
        return ((TypeProjection)paramTypeArgumentMarker).isStarProjection();
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramTypeArgumentMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeArgumentMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static boolean isStubType(ClassicTypeSystemContext paramClassicTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$isStubType");
      if ((paramSimpleTypeMarker instanceof SimpleType)) {
        return paramSimpleTypeMarker instanceof StubType;
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramSimpleTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramSimpleTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static boolean isUnderKotlinPackage(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeConstructorMarker paramTypeConstructorMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$isUnderKotlinPackage");
      if ((paramTypeConstructorMarker instanceof TypeConstructor))
      {
        paramClassicTypeSystemContext = ((TypeConstructor)paramTypeConstructorMarker).getDeclarationDescriptor();
        boolean bool = true;
        if ((paramClassicTypeSystemContext == null) || (KotlinBuiltIns.isUnderKotlinPackage((DeclarationDescriptor)paramClassicTypeSystemContext) != true)) {
          bool = false;
        }
        return bool;
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramTypeConstructorMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeConstructorMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static SimpleTypeMarker lowerBound(ClassicTypeSystemContext paramClassicTypeSystemContext, FlexibleTypeMarker paramFlexibleTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramFlexibleTypeMarker, "$this$lowerBound");
      if ((paramFlexibleTypeMarker instanceof FlexibleType)) {
        return (SimpleTypeMarker)((FlexibleType)paramFlexibleTypeMarker).getLowerBound();
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramFlexibleTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramFlexibleTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static SimpleTypeMarker lowerBoundIfFlexible(ClassicTypeSystemContext paramClassicTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$lowerBoundIfFlexible");
      return TypeSystemInferenceExtensionContext.DefaultImpls.lowerBoundIfFlexible((TypeSystemInferenceExtensionContext)paramClassicTypeSystemContext, paramKotlinTypeMarker);
    }
    
    public static KotlinTypeMarker lowerType(ClassicTypeSystemContext paramClassicTypeSystemContext, CapturedTypeMarker paramCapturedTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramCapturedTypeMarker, "$this$lowerType");
      if ((paramCapturedTypeMarker instanceof NewCapturedType)) {
        return (KotlinTypeMarker)((NewCapturedType)paramCapturedTypeMarker).getLowerType();
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramCapturedTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramCapturedTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static KotlinTypeMarker makeNullable(ClassicTypeSystemContext paramClassicTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$makeNullable");
      return TypeSystemCommonBackendContext.DefaultImpls.makeNullable((TypeSystemCommonBackendContext)paramClassicTypeSystemContext, paramKotlinTypeMarker);
    }
    
    public static AbstractTypeCheckerContext newBaseTypeCheckerContext(ClassicTypeSystemContext paramClassicTypeSystemContext, boolean paramBoolean)
    {
      return (AbstractTypeCheckerContext)new ClassicTypeCheckerContext(paramBoolean, false, null, 6, null);
    }
    
    public static int parametersCount(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeConstructorMarker paramTypeConstructorMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$parametersCount");
      if ((paramTypeConstructorMarker instanceof TypeConstructor)) {
        return ((TypeConstructor)paramTypeConstructorMarker).getParameters().size();
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramTypeConstructorMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeConstructorMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static Collection<KotlinTypeMarker> possibleIntegerTypes(ClassicTypeSystemContext paramClassicTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$possibleIntegerTypes");
      paramClassicTypeSystemContext = paramClassicTypeSystemContext.typeConstructor(paramSimpleTypeMarker);
      if ((paramClassicTypeSystemContext instanceof IntegerLiteralTypeConstructor)) {
        return (Collection)((IntegerLiteralTypeConstructor)paramClassicTypeSystemContext).getPossibleTypes();
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramSimpleTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramSimpleTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static int size(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeArgumentListMarker paramTypeArgumentListMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeArgumentListMarker, "$this$size");
      return TypeSystemInferenceExtensionContext.DefaultImpls.size((TypeSystemInferenceExtensionContext)paramClassicTypeSystemContext, paramTypeArgumentListMarker);
    }
    
    public static Collection<KotlinTypeMarker> supertypes(ClassicTypeSystemContext paramClassicTypeSystemContext, TypeConstructorMarker paramTypeConstructorMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$supertypes");
      if ((paramTypeConstructorMarker instanceof TypeConstructor))
      {
        paramClassicTypeSystemContext = ((TypeConstructor)paramTypeConstructorMarker).getSupertypes();
        Intrinsics.checkExpressionValueIsNotNull(paramClassicTypeSystemContext, "this.supertypes");
        return paramClassicTypeSystemContext;
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramTypeConstructorMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramTypeConstructorMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static TypeConstructorMarker typeConstructor(ClassicTypeSystemContext paramClassicTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$typeConstructor");
      return TypeSystemInferenceExtensionContext.DefaultImpls.typeConstructor((TypeSystemInferenceExtensionContext)paramClassicTypeSystemContext, paramKotlinTypeMarker);
    }
    
    public static TypeConstructorMarker typeConstructor(ClassicTypeSystemContext paramClassicTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$typeConstructor");
      if ((paramSimpleTypeMarker instanceof SimpleType)) {
        return (TypeConstructorMarker)((SimpleType)paramSimpleTypeMarker).getConstructor();
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramSimpleTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramSimpleTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static SimpleTypeMarker upperBound(ClassicTypeSystemContext paramClassicTypeSystemContext, FlexibleTypeMarker paramFlexibleTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramFlexibleTypeMarker, "$this$upperBound");
      if ((paramFlexibleTypeMarker instanceof FlexibleType)) {
        return (SimpleTypeMarker)((FlexibleType)paramFlexibleTypeMarker).getUpperBound();
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramFlexibleTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramFlexibleTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
    
    public static SimpleTypeMarker upperBoundIfFlexible(ClassicTypeSystemContext paramClassicTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$upperBoundIfFlexible");
      return TypeSystemInferenceExtensionContext.DefaultImpls.upperBoundIfFlexible((TypeSystemInferenceExtensionContext)paramClassicTypeSystemContext, paramKotlinTypeMarker);
    }
    
    public static SimpleTypeMarker withNullability(ClassicTypeSystemContext paramClassicTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker, boolean paramBoolean)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$withNullability");
      if ((paramSimpleTypeMarker instanceof SimpleType)) {
        return (SimpleTypeMarker)((SimpleType)paramSimpleTypeMarker).makeNullableAsSpecified(paramBoolean);
      }
      paramClassicTypeSystemContext = new StringBuilder();
      paramClassicTypeSystemContext.append("ClassicTypeSystemContext couldn't handle: ");
      paramClassicTypeSystemContext.append(paramSimpleTypeMarker);
      paramClassicTypeSystemContext.append(", ");
      paramClassicTypeSystemContext.append(Reflection.getOrCreateKotlinClass(paramSimpleTypeMarker.getClass()));
      throw ((Throwable)new IllegalArgumentException(paramClassicTypeSystemContext.toString().toString()));
    }
  }
}

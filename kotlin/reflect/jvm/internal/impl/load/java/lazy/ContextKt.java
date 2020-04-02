package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassOrPackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver.QualifierApplicabilityType;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver.TypeQualifierWithApplicability;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameterListOwner;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement;
import kotlin.reflect.jvm.internal.impl.utils.ReportLevel;

public final class ContextKt
{
  private static final LazyJavaResolverContext child(LazyJavaResolverContext paramLazyJavaResolverContext, DeclarationDescriptor paramDeclarationDescriptor, JavaTypeParameterListOwner paramJavaTypeParameterListOwner, int paramInt, Lazy<JavaTypeQualifiersByElementType> paramLazy)
  {
    JavaResolverComponents localJavaResolverComponents = paramLazyJavaResolverContext.getComponents();
    if (paramJavaTypeParameterListOwner != null) {
      paramLazyJavaResolverContext = (TypeParameterResolver)new LazyJavaTypeParameterResolver(paramLazyJavaResolverContext, paramDeclarationDescriptor, paramJavaTypeParameterListOwner, paramInt);
    } else {
      paramLazyJavaResolverContext = paramLazyJavaResolverContext.getTypeParameterResolver();
    }
    return new LazyJavaResolverContext(localJavaResolverComponents, paramLazyJavaResolverContext, paramLazy);
  }
  
  public static final LazyJavaResolverContext child(LazyJavaResolverContext paramLazyJavaResolverContext, TypeParameterResolver paramTypeParameterResolver)
  {
    Intrinsics.checkParameterIsNotNull(paramLazyJavaResolverContext, "$this$child");
    Intrinsics.checkParameterIsNotNull(paramTypeParameterResolver, "typeParameterResolver");
    return new LazyJavaResolverContext(paramLazyJavaResolverContext.getComponents(), paramTypeParameterResolver, paramLazyJavaResolverContext.getDelegateForDefaultTypeQualifiers$descriptors_jvm());
  }
  
  public static final LazyJavaResolverContext childForClassOrPackage(LazyJavaResolverContext paramLazyJavaResolverContext, final ClassOrPackageFragmentDescriptor paramClassOrPackageFragmentDescriptor, JavaTypeParameterListOwner paramJavaTypeParameterListOwner, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramLazyJavaResolverContext, "$this$childForClassOrPackage");
    Intrinsics.checkParameterIsNotNull(paramClassOrPackageFragmentDescriptor, "containingDeclaration");
    child(paramLazyJavaResolverContext, (DeclarationDescriptor)paramClassOrPackageFragmentDescriptor, paramJavaTypeParameterListOwner, paramInt, LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0)new Lambda(paramLazyJavaResolverContext)
    {
      public final JavaTypeQualifiersByElementType invoke()
      {
        return ContextKt.computeNewDefaultTypeQualifiers(this.$this_childForClassOrPackage, paramClassOrPackageFragmentDescriptor.getAnnotations());
      }
    }));
  }
  
  public static final LazyJavaResolverContext childForMethod(LazyJavaResolverContext paramLazyJavaResolverContext, DeclarationDescriptor paramDeclarationDescriptor, JavaTypeParameterListOwner paramJavaTypeParameterListOwner, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramLazyJavaResolverContext, "$this$childForMethod");
    Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptor, "containingDeclaration");
    Intrinsics.checkParameterIsNotNull(paramJavaTypeParameterListOwner, "typeParameterOwner");
    return child(paramLazyJavaResolverContext, paramDeclarationDescriptor, paramJavaTypeParameterListOwner, paramInt, paramLazyJavaResolverContext.getDelegateForDefaultTypeQualifiers$descriptors_jvm());
  }
  
  public static final JavaTypeQualifiersByElementType computeNewDefaultTypeQualifiers(LazyJavaResolverContext paramLazyJavaResolverContext, Annotations paramAnnotations)
  {
    Intrinsics.checkParameterIsNotNull(paramLazyJavaResolverContext, "$this$computeNewDefaultTypeQualifiers");
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "additionalAnnotations");
    if (paramLazyJavaResolverContext.getComponents().getAnnotationTypeQualifierResolver().getDisabled()) {
      return paramLazyJavaResolverContext.getDefaultTypeQualifiers();
    }
    Object localObject1 = (Iterable)paramAnnotations;
    paramAnnotations = (Collection)new ArrayList();
    Iterator localIterator1 = ((Iterable)localObject1).iterator();
    while (localIterator1.hasNext())
    {
      localObject1 = extractDefaultNullabilityQualifier(paramLazyJavaResolverContext, (AnnotationDescriptor)localIterator1.next());
      if (localObject1 != null) {
        paramAnnotations.add(localObject1);
      }
    }
    localObject1 = (List)paramAnnotations;
    if (((List)localObject1).isEmpty()) {
      return paramLazyJavaResolverContext.getDefaultTypeQualifiers();
    }
    paramAnnotations = paramLazyJavaResolverContext.getDefaultTypeQualifiers();
    if (paramAnnotations != null)
    {
      paramAnnotations = paramAnnotations.getNullabilityQualifiers();
      if (paramAnnotations != null)
      {
        paramAnnotations = new EnumMap(paramAnnotations);
        break label150;
      }
    }
    paramAnnotations = new EnumMap(AnnotationTypeQualifierResolver.QualifierApplicabilityType.class);
    label150:
    int i = 0;
    localIterator1 = ((List)localObject1).iterator();
    while (localIterator1.hasNext())
    {
      Object localObject2 = (NullabilityQualifierWithApplicability)localIterator1.next();
      localObject1 = ((NullabilityQualifierWithApplicability)localObject2).component1();
      Iterator localIterator2 = ((NullabilityQualifierWithApplicability)localObject2).component2().iterator();
      while (localIterator2.hasNext())
      {
        localObject2 = (AnnotationTypeQualifierResolver.QualifierApplicabilityType)localIterator2.next();
        ((Map)paramAnnotations).put(localObject2, localObject1);
        i = 1;
      }
    }
    if (i == 0) {
      paramLazyJavaResolverContext = paramLazyJavaResolverContext.getDefaultTypeQualifiers();
    } else {
      paramLazyJavaResolverContext = new JavaTypeQualifiersByElementType(paramAnnotations);
    }
    return paramLazyJavaResolverContext;
  }
  
  public static final LazyJavaResolverContext copyWithNewDefaultTypeQualifiers(LazyJavaResolverContext paramLazyJavaResolverContext, final Annotations paramAnnotations)
  {
    Intrinsics.checkParameterIsNotNull(paramLazyJavaResolverContext, "$this$copyWithNewDefaultTypeQualifiers");
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "additionalAnnotations");
    if (!paramAnnotations.isEmpty()) {
      paramLazyJavaResolverContext = new LazyJavaResolverContext(paramLazyJavaResolverContext.getComponents(), paramLazyJavaResolverContext.getTypeParameterResolver(), LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0)new Lambda(paramLazyJavaResolverContext)
      {
        public final JavaTypeQualifiersByElementType invoke()
        {
          return ContextKt.computeNewDefaultTypeQualifiers(this.$this_copyWithNewDefaultTypeQualifiers, paramAnnotations);
        }
      }));
    }
    return paramLazyJavaResolverContext;
  }
  
  private static final NullabilityQualifierWithApplicability extractDefaultNullabilityQualifier(LazyJavaResolverContext paramLazyJavaResolverContext, AnnotationDescriptor paramAnnotationDescriptor)
  {
    AnnotationTypeQualifierResolver localAnnotationTypeQualifierResolver = paramLazyJavaResolverContext.getComponents().getAnnotationTypeQualifierResolver();
    Object localObject = localAnnotationTypeQualifierResolver.resolveQualifierBuiltInDefaultAnnotation(paramAnnotationDescriptor);
    if (localObject != null) {
      return localObject;
    }
    localObject = localAnnotationTypeQualifierResolver.resolveTypeQualifierDefaultAnnotation(paramAnnotationDescriptor);
    if (localObject != null)
    {
      AnnotationDescriptor localAnnotationDescriptor = ((AnnotationTypeQualifierResolver.TypeQualifierWithApplicability)localObject).component1();
      localObject = ((AnnotationTypeQualifierResolver.TypeQualifierWithApplicability)localObject).component2();
      paramAnnotationDescriptor = localAnnotationTypeQualifierResolver.resolveJsr305CustomState(paramAnnotationDescriptor);
      if (paramAnnotationDescriptor == null) {
        paramAnnotationDescriptor = localAnnotationTypeQualifierResolver.resolveJsr305AnnotationState(localAnnotationDescriptor);
      }
      if (paramAnnotationDescriptor.isIgnore()) {
        return null;
      }
      paramLazyJavaResolverContext = paramLazyJavaResolverContext.getComponents().getSignatureEnhancement().extractNullability(localAnnotationDescriptor);
      if (paramLazyJavaResolverContext != null)
      {
        paramLazyJavaResolverContext = NullabilityQualifierWithMigrationStatus.copy$default(paramLazyJavaResolverContext, null, paramAnnotationDescriptor.isWarning(), 1, null);
        if (paramLazyJavaResolverContext != null) {
          return new NullabilityQualifierWithApplicability(paramLazyJavaResolverContext, (Collection)localObject);
        }
      }
    }
    return null;
  }
  
  public static final LazyJavaResolverContext replaceComponents(LazyJavaResolverContext paramLazyJavaResolverContext, JavaResolverComponents paramJavaResolverComponents)
  {
    Intrinsics.checkParameterIsNotNull(paramLazyJavaResolverContext, "$this$replaceComponents");
    Intrinsics.checkParameterIsNotNull(paramJavaResolverComponents, "components");
    return new LazyJavaResolverContext(paramJavaResolverComponents, paramLazyJavaResolverContext.getTypeParameterResolver(), paramLazyJavaResolverContext.getDelegateForDefaultTypeQualifiers$descriptors_jvm());
  }
}

package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.AbstractLazyTypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.components.TypeUsage;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.JavaResolverComponents;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaAnnotations;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolver;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolverKt;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationOwner;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifierType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameter;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.Variance;

public final class LazyJavaTypeParameterDescriptor
  extends AbstractLazyTypeParameterDescriptor
{
  private final LazyJavaAnnotations annotations;
  private final LazyJavaResolverContext c;
  private final JavaTypeParameter javaTypeParameter;
  
  public LazyJavaTypeParameterDescriptor(LazyJavaResolverContext paramLazyJavaResolverContext, JavaTypeParameter paramJavaTypeParameter, int paramInt, DeclarationDescriptor paramDeclarationDescriptor)
  {
    super(paramLazyJavaResolverContext.getStorageManager(), paramDeclarationDescriptor, paramJavaTypeParameter.getName(), Variance.INVARIANT, false, paramInt, SourceElement.NO_SOURCE, paramLazyJavaResolverContext.getComponents().getSupertypeLoopChecker());
    this.c = paramLazyJavaResolverContext;
    this.javaTypeParameter = paramJavaTypeParameter;
    this.annotations = new LazyJavaAnnotations(this.c, (JavaAnnotationOwner)this.javaTypeParameter);
  }
  
  public LazyJavaAnnotations getAnnotations()
  {
    return this.annotations;
  }
  
  protected void reportSupertypeLoopError(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "type");
  }
  
  protected List<KotlinType> resolveUpperBounds()
  {
    Object localObject1 = this.javaTypeParameter.getUpperBounds();
    if (((Collection)localObject1).isEmpty())
    {
      localObject1 = this.c.getModule().getBuiltIns().getAnyType();
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "c.module.builtIns.anyType");
      localObject2 = this.c.getModule().getBuiltIns().getNullableAnyType();
      Intrinsics.checkExpressionValueIsNotNull(localObject2, "c.module.builtIns.nullableAnyType");
      return CollectionsKt.listOf(KotlinTypeFactory.flexibleType((SimpleType)localObject1, (SimpleType)localObject2));
    }
    Object localObject2 = (Iterable)localObject1;
    localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
    Iterator localIterator = ((Iterable)localObject2).iterator();
    while (localIterator.hasNext())
    {
      localObject2 = (JavaClassifierType)localIterator.next();
      ((Collection)localObject1).add(this.c.getTypeResolver().transformJavaType((JavaType)localObject2, JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, false, (TypeParameterDescriptor)this, 1, null)));
    }
    return (List)localObject1;
  }
}

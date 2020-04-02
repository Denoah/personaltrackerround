package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.RawType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.text.StringsKt;

public final class RawTypeImpl
  extends FlexibleType
  implements RawType
{
  public RawTypeImpl(SimpleType paramSimpleType1, SimpleType paramSimpleType2)
  {
    this(paramSimpleType1, paramSimpleType2, false);
  }
  
  private RawTypeImpl(SimpleType paramSimpleType1, SimpleType paramSimpleType2, boolean paramBoolean)
  {
    super(paramSimpleType1, paramSimpleType2);
    if (!paramBoolean)
    {
      paramBoolean = KotlinTypeChecker.DEFAULT.isSubtypeOf((KotlinType)paramSimpleType1, (KotlinType)paramSimpleType2);
      if ((_Assertions.ENABLED) && (!paramBoolean))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Lower bound ");
        localStringBuilder.append(paramSimpleType1);
        localStringBuilder.append(" of a flexible type must be a subtype of the upper bound ");
        localStringBuilder.append(paramSimpleType2);
        throw ((Throwable)new AssertionError(localStringBuilder.toString()));
      }
    }
  }
  
  public SimpleType getDelegate()
  {
    return getLowerBound();
  }
  
  public MemberScope getMemberScope()
  {
    ClassifierDescriptor localClassifierDescriptor = getConstructor().getDeclarationDescriptor();
    Object localObject = localClassifierDescriptor;
    if (!(localClassifierDescriptor instanceof ClassDescriptor)) {
      localObject = null;
    }
    localObject = (ClassDescriptor)localObject;
    if (localObject != null)
    {
      localObject = ((ClassDescriptor)localObject).getMemberScope((TypeSubstitution)RawSubstitution.INSTANCE);
      Intrinsics.checkExpressionValueIsNotNull(localObject, "classDescriptor.getMemberScope(RawSubstitution)");
      return localObject;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Incorrect classifier: ");
    ((StringBuilder)localObject).append(getConstructor().getDeclarationDescriptor());
    throw ((Throwable)new IllegalStateException(((StringBuilder)localObject).toString().toString()));
  }
  
  public RawTypeImpl makeNullableAsSpecified(boolean paramBoolean)
  {
    return new RawTypeImpl(getLowerBound().makeNullableAsSpecified(paramBoolean), getUpperBound().makeNullableAsSpecified(paramBoolean));
  }
  
  public FlexibleType refine(KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeRefiner, "kotlinTypeRefiner");
    Object localObject = paramKotlinTypeRefiner.refineType((KotlinType)getLowerBound());
    if (localObject != null)
    {
      localObject = (SimpleType)localObject;
      paramKotlinTypeRefiner = paramKotlinTypeRefiner.refineType((KotlinType)getUpperBound());
      if (paramKotlinTypeRefiner != null) {
        return (FlexibleType)new RawTypeImpl((SimpleType)localObject, (SimpleType)paramKotlinTypeRefiner, true);
      }
      throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
    }
    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
  }
  
  public String render(DescriptorRenderer paramDescriptorRenderer, DescriptorRendererOptions paramDescriptorRendererOptions)
  {
    Intrinsics.checkParameterIsNotNull(paramDescriptorRenderer, "renderer");
    Intrinsics.checkParameterIsNotNull(paramDescriptorRendererOptions, "options");
    Object localObject1 = render.1.INSTANCE;
    Object localObject2 = new Lambda(paramDescriptorRenderer)
    {
      public final List<String> invoke(KotlinType paramAnonymousKotlinType)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousKotlinType, "type");
        Object localObject = (Iterable)paramAnonymousKotlinType.getArguments();
        paramAnonymousKotlinType = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject, 10));
        Iterator localIterator = ((Iterable)localObject).iterator();
        while (localIterator.hasNext())
        {
          localObject = (TypeProjection)localIterator.next();
          paramAnonymousKotlinType.add(this.$renderer.renderTypeProjection((TypeProjection)localObject));
        }
        return (List)paramAnonymousKotlinType;
      }
    };
    Lambda local3 = render.3.INSTANCE;
    String str = paramDescriptorRenderer.renderType((KotlinType)getLowerBound());
    localObject1 = paramDescriptorRenderer.renderType((KotlinType)getUpperBound());
    if (paramDescriptorRendererOptions.getDebugMode())
    {
      paramDescriptorRenderer = new StringBuilder();
      paramDescriptorRenderer.append("raw (");
      paramDescriptorRenderer.append(str);
      paramDescriptorRenderer.append("..");
      paramDescriptorRenderer.append((String)localObject1);
      paramDescriptorRenderer.append(')');
      return paramDescriptorRenderer.toString();
    }
    if (getUpperBound().getArguments().isEmpty()) {
      return paramDescriptorRenderer.renderFlexibleType(str, (String)localObject1, TypeUtilsKt.getBuiltIns(this));
    }
    Object localObject3 = ((render.2)localObject2).invoke((KotlinType)getLowerBound());
    paramDescriptorRendererOptions = ((render.2)localObject2).invoke((KotlinType)getUpperBound());
    localObject3 = (Iterable)localObject3;
    localObject2 = CollectionsKt.joinToString$default((Iterable)localObject3, (CharSequence)", ", null, null, 0, null, (Function1)render.newArgs.1.INSTANCE, 30, null);
    paramDescriptorRendererOptions = (Iterable)CollectionsKt.zip((Iterable)localObject3, (Iterable)paramDescriptorRendererOptions);
    boolean bool = paramDescriptorRendererOptions instanceof Collection;
    int i = 1;
    int j;
    if ((bool) && (((Collection)paramDescriptorRendererOptions).isEmpty()))
    {
      j = i;
    }
    else
    {
      localObject3 = paramDescriptorRendererOptions.iterator();
      do
      {
        j = i;
        if (!((Iterator)localObject3).hasNext()) {
          break;
        }
        paramDescriptorRendererOptions = (Pair)((Iterator)localObject3).next();
      } while (render.1.INSTANCE.invoke((String)paramDescriptorRendererOptions.getFirst(), (String)paramDescriptorRendererOptions.getSecond()));
      j = 0;
    }
    paramDescriptorRendererOptions = (DescriptorRendererOptions)localObject1;
    if (j != 0) {
      paramDescriptorRendererOptions = local3.invoke((String)localObject1, (String)localObject2);
    }
    localObject1 = local3.invoke(str, (String)localObject2);
    if (Intrinsics.areEqual(localObject1, paramDescriptorRendererOptions)) {
      return localObject1;
    }
    return paramDescriptorRenderer.renderFlexibleType((String)localObject1, paramDescriptorRendererOptions, TypeUtilsKt.getBuiltIns(this));
  }
  
  public RawTypeImpl replaceAnnotations(Annotations paramAnnotations)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "newAnnotations");
    return new RawTypeImpl(getLowerBound().replaceAnnotations(paramAnnotations), getUpperBound().replaceAnnotations(paramAnnotations));
  }
}

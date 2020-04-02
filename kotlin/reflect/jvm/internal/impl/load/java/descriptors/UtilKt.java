package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNames;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaStaticClassScope;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public final class UtilKt
{
  public static final List<ValueParameterDescriptor> copyValueParameters(Collection<ValueParameterData> paramCollection, Collection<? extends ValueParameterDescriptor> paramCollection1, CallableDescriptor paramCallableDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "newValueParametersTypes");
    Intrinsics.checkParameterIsNotNull(paramCollection1, "oldValueParameters");
    Intrinsics.checkParameterIsNotNull(paramCallableDescriptor, "newOwner");
    int i;
    if (paramCollection.size() == paramCollection1.size()) {
      i = 1;
    } else {
      i = 0;
    }
    if ((_Assertions.ENABLED) && (i == 0))
    {
      paramCallableDescriptor = new StringBuilder();
      paramCallableDescriptor.append("Different value parameters sizes: Enhanced = ");
      paramCallableDescriptor.append(paramCollection.size());
      paramCallableDescriptor.append(", Old = ");
      paramCallableDescriptor.append(paramCollection1.size());
      throw ((Throwable)new AssertionError(paramCallableDescriptor.toString()));
    }
    paramCollection = (Iterable)CollectionsKt.zip((Iterable)paramCollection, (Iterable)paramCollection1);
    paramCollection1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault(paramCollection, 10));
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      Object localObject1 = (Pair)localIterator.next();
      paramCollection = (ValueParameterData)((Pair)localObject1).component1();
      Object localObject2 = (ValueParameterDescriptor)((Pair)localObject1).component2();
      i = ((ValueParameterDescriptor)localObject2).getIndex();
      Annotations localAnnotations = ((ValueParameterDescriptor)localObject2).getAnnotations();
      Name localName = ((ValueParameterDescriptor)localObject2).getName();
      Intrinsics.checkExpressionValueIsNotNull(localName, "oldParameter.name");
      localObject1 = paramCollection.getType();
      boolean bool1 = paramCollection.getHasDefaultValue();
      boolean bool2 = ((ValueParameterDescriptor)localObject2).isCrossinline();
      boolean bool3 = ((ValueParameterDescriptor)localObject2).isNoinline();
      if (((ValueParameterDescriptor)localObject2).getVarargElementType() != null) {
        paramCollection = DescriptorUtilsKt.getModule((DeclarationDescriptor)paramCallableDescriptor).getBuiltIns().getArrayElementType(paramCollection.getType());
      } else {
        paramCollection = null;
      }
      localObject2 = ((ValueParameterDescriptor)localObject2).getSource();
      Intrinsics.checkExpressionValueIsNotNull(localObject2, "oldParameter.source");
      paramCollection1.add(new ValueParameterDescriptorImpl(paramCallableDescriptor, null, i, localAnnotations, localName, (KotlinType)localObject1, bool1, bool2, bool3, paramCollection, (SourceElement)localObject2));
    }
    return (List)paramCollection1;
  }
  
  public static final AnnotationDefaultValue getDefaultValueFromAnnotation(ValueParameterDescriptor paramValueParameterDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramValueParameterDescriptor, "$this$getDefaultValueFromAnnotation");
    Object localObject1 = paramValueParameterDescriptor.getAnnotations();
    Object localObject2 = JvmAnnotationNames.DEFAULT_VALUE_FQ_NAME;
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "JvmAnnotationNames.DEFAULT_VALUE_FQ_NAME");
    localObject2 = ((Annotations)localObject1).findAnnotation((FqName)localObject2);
    if (localObject2 != null)
    {
      localObject1 = DescriptorUtilsKt.firstArgument((AnnotationDescriptor)localObject2);
      if (localObject1 != null)
      {
        localObject2 = localObject1;
        if (!(localObject1 instanceof StringValue)) {
          localObject2 = null;
        }
        localObject2 = (StringValue)localObject2;
        if (localObject2 != null)
        {
          localObject2 = (String)((StringValue)localObject2).getValue();
          if (localObject2 != null) {
            return (AnnotationDefaultValue)new StringDefaultValue((String)localObject2);
          }
        }
      }
    }
    localObject2 = paramValueParameterDescriptor.getAnnotations();
    paramValueParameterDescriptor = JvmAnnotationNames.DEFAULT_NULL_FQ_NAME;
    Intrinsics.checkExpressionValueIsNotNull(paramValueParameterDescriptor, "JvmAnnotationNames.DEFAULT_NULL_FQ_NAME");
    if (((Annotations)localObject2).hasAnnotation(paramValueParameterDescriptor)) {
      return (AnnotationDefaultValue)NullDefaultValue.INSTANCE;
    }
    return null;
  }
  
  public static final LazyJavaStaticClassScope getParentJavaStaticClassScope(ClassDescriptor paramClassDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "$this$getParentJavaStaticClassScope");
    ClassDescriptor localClassDescriptor = DescriptorUtilsKt.getSuperClassNotAny(paramClassDescriptor);
    paramClassDescriptor = null;
    Object localObject = null;
    if (localClassDescriptor != null)
    {
      paramClassDescriptor = localClassDescriptor.getStaticScope();
      if (!(paramClassDescriptor instanceof LazyJavaStaticClassScope)) {
        paramClassDescriptor = localObject;
      }
      paramClassDescriptor = (LazyJavaStaticClassScope)paramClassDescriptor;
      if (paramClassDescriptor == null) {
        paramClassDescriptor = getParentJavaStaticClassScope(localClassDescriptor);
      }
    }
    return paramClassDescriptor;
  }
}

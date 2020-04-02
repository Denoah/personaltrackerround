package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation.Argument;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation.Argument.Value;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags.BooleanFlagField;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.constants.AnnotationValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ArrayValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.BooleanValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ByteValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.CharValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValueFactory;
import kotlin.reflect.jvm.internal.impl.resolve.constants.DoubleValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ErrorValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ErrorValue.Companion;
import kotlin.reflect.jvm.internal.impl.resolve.constants.FloatValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.LongValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ShortValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.UByteValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.UIntValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ULongValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.UShortValue;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;

public final class AnnotationDeserializer
{
  private final ModuleDescriptor module;
  private final NotFoundClasses notFoundClasses;
  
  public AnnotationDeserializer(ModuleDescriptor paramModuleDescriptor, NotFoundClasses paramNotFoundClasses)
  {
    this.module = paramModuleDescriptor;
    this.notFoundClasses = paramNotFoundClasses;
  }
  
  private final boolean doesValueConformToExpectedType(ConstantValue<?> paramConstantValue, KotlinType paramKotlinType, ProtoBuf.Annotation.Argument.Value paramValue)
  {
    Object localObject = paramValue.getType();
    boolean bool = false;
    int i;
    if (localObject != null)
    {
      i = AnnotationDeserializer.WhenMappings.$EnumSwitchMapping$1[localObject.ordinal()];
      if (i == 1) {
        break label281;
      }
      if (i == 2) {}
    }
    else
    {
      bool = Intrinsics.areEqual(paramConstantValue.getType(this.module), paramKotlinType);
      break label321;
    }
    if (((paramConstantValue instanceof ArrayValue)) && (((List)((ArrayValue)paramConstantValue).getValue()).size() == paramValue.getArrayElementList().size())) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      paramKotlinType = getBuiltIns().getArrayElementType(paramKotlinType);
      Intrinsics.checkExpressionValueIsNotNull(paramKotlinType, "builtIns.getArrayElementType(expectedType)");
      paramConstantValue = (ArrayValue)paramConstantValue;
      localObject = (Iterable)CollectionsKt.getIndices((Collection)paramConstantValue.getValue());
      if ((!(localObject instanceof Collection)) || (!((Collection)localObject).isEmpty()))
      {
        Iterator localIterator = ((Iterable)localObject).iterator();
        ConstantValue localConstantValue;
        do
        {
          if (!localIterator.hasNext()) {
            break;
          }
          i = ((IntIterator)localIterator).nextInt();
          localConstantValue = (ConstantValue)((List)paramConstantValue.getValue()).get(i);
          localObject = paramValue.getArrayElement(i);
          Intrinsics.checkExpressionValueIsNotNull(localObject, "value.getArrayElement(i)");
        } while (doesValueConformToExpectedType(localConstantValue, paramKotlinType, (ProtoBuf.Annotation.Argument.Value)localObject));
        break label321;
      }
    }
    else
    {
      paramKotlinType = new StringBuilder();
      paramKotlinType.append("Deserialized ArrayValue should have the same number of elements as the original array value: ");
      paramKotlinType.append(paramConstantValue);
      throw ((Throwable)new IllegalStateException(paramKotlinType.toString().toString()));
      label281:
      paramKotlinType = paramKotlinType.getConstructor().getDeclarationDescriptor();
      paramConstantValue = paramKotlinType;
      if (!(paramKotlinType instanceof ClassDescriptor)) {
        paramConstantValue = null;
      }
      paramConstantValue = (ClassDescriptor)paramConstantValue;
      if ((paramConstantValue != null) && (!KotlinBuiltIns.isKClass(paramConstantValue))) {
        break label321;
      }
    }
    bool = true;
    label321:
    return bool;
  }
  
  private final KotlinBuiltIns getBuiltIns()
  {
    return this.module.getBuiltIns();
  }
  
  private final Pair<Name, ConstantValue<?>> resolveArgument(ProtoBuf.Annotation.Argument paramArgument, Map<Name, ? extends ValueParameterDescriptor> paramMap, NameResolver paramNameResolver)
  {
    Object localObject = (ValueParameterDescriptor)paramMap.get(NameResolverUtilKt.getName(paramNameResolver, paramArgument.getNameId()));
    if (localObject != null)
    {
      paramMap = NameResolverUtilKt.getName(paramNameResolver, paramArgument.getNameId());
      localObject = ((ValueParameterDescriptor)localObject).getType();
      Intrinsics.checkExpressionValueIsNotNull(localObject, "parameter.type");
      paramArgument = paramArgument.getValue();
      Intrinsics.checkExpressionValueIsNotNull(paramArgument, "proto.value");
      return new Pair(paramMap, resolveValueAndCheckExpectedType((KotlinType)localObject, paramArgument, paramNameResolver));
    }
    return null;
  }
  
  private final ClassDescriptor resolveClass(ClassId paramClassId)
  {
    return FindClassInModuleKt.findNonGenericClassAcrossDependencies(this.module, paramClassId, this.notFoundClasses);
  }
  
  private final ConstantValue<?> resolveValueAndCheckExpectedType(KotlinType paramKotlinType, ProtoBuf.Annotation.Argument.Value paramValue, NameResolver paramNameResolver)
  {
    paramNameResolver = resolveValue(paramKotlinType, paramValue, paramNameResolver);
    if (!doesValueConformToExpectedType(paramNameResolver, paramKotlinType, paramValue)) {
      paramNameResolver = null;
    }
    if (paramNameResolver == null)
    {
      paramNameResolver = ErrorValue.Companion;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unexpected argument value: actual type ");
      localStringBuilder.append(paramValue.getType());
      localStringBuilder.append(" != expected type ");
      localStringBuilder.append(paramKotlinType);
      paramNameResolver = (ConstantValue)paramNameResolver.create(localStringBuilder.toString());
    }
    return paramNameResolver;
  }
  
  public final AnnotationDescriptor deserializeAnnotation(ProtoBuf.Annotation paramAnnotation, NameResolver paramNameResolver)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotation, "proto");
    Intrinsics.checkParameterIsNotNull(paramNameResolver, "nameResolver");
    ClassDescriptor localClassDescriptor = resolveClass(NameResolverUtilKt.getClassId(paramNameResolver, paramAnnotation.getId()));
    Object localObject1 = MapsKt.emptyMap();
    Object localObject2 = localObject1;
    if (paramAnnotation.getArgumentCount() != 0)
    {
      Object localObject3 = (DeclarationDescriptor)localClassDescriptor;
      localObject2 = localObject1;
      if (!ErrorUtils.isError((DeclarationDescriptor)localObject3))
      {
        localObject2 = localObject1;
        if (DescriptorUtils.isAnnotationClass((DeclarationDescriptor)localObject3))
        {
          localObject2 = localClassDescriptor.getConstructors();
          Intrinsics.checkExpressionValueIsNotNull(localObject2, "annotationClass.constructors");
          localObject3 = (ClassConstructorDescriptor)CollectionsKt.singleOrNull((Iterable)localObject2);
          localObject2 = localObject1;
          if (localObject3 != null)
          {
            localObject2 = ((ClassConstructorDescriptor)localObject3).getValueParameters();
            Intrinsics.checkExpressionValueIsNotNull(localObject2, "constructor.valueParameters");
            localObject1 = (Iterable)localObject2;
            localObject2 = (Map)new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault((Iterable)localObject1, 10)), 16));
            Iterator localIterator = ((Iterable)localObject1).iterator();
            while (localIterator.hasNext())
            {
              localObject1 = localIterator.next();
              localObject3 = (ValueParameterDescriptor)localObject1;
              Intrinsics.checkExpressionValueIsNotNull(localObject3, "it");
              ((Map)localObject2).put(((ValueParameterDescriptor)localObject3).getName(), localObject1);
            }
            paramAnnotation = paramAnnotation.getArgumentList();
            Intrinsics.checkExpressionValueIsNotNull(paramAnnotation, "proto.argumentList");
            localObject1 = (Iterable)paramAnnotation;
            paramAnnotation = (Collection)new ArrayList();
            localObject1 = ((Iterable)localObject1).iterator();
            while (((Iterator)localObject1).hasNext())
            {
              localObject3 = (ProtoBuf.Annotation.Argument)((Iterator)localObject1).next();
              Intrinsics.checkExpressionValueIsNotNull(localObject3, "it");
              localObject3 = resolveArgument((ProtoBuf.Annotation.Argument)localObject3, (Map)localObject2, paramNameResolver);
              if (localObject3 != null) {
                paramAnnotation.add(localObject3);
              }
            }
            localObject2 = MapsKt.toMap((Iterable)paramAnnotation);
          }
        }
      }
    }
    return (AnnotationDescriptor)new AnnotationDescriptorImpl((KotlinType)localClassDescriptor.getDefaultType(), (Map)localObject2, SourceElement.NO_SOURCE);
  }
  
  public final ConstantValue<?> resolveValue(KotlinType paramKotlinType, ProtoBuf.Annotation.Argument.Value paramValue, NameResolver paramNameResolver)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "expectedType");
    Intrinsics.checkParameterIsNotNull(paramValue, "value");
    Intrinsics.checkParameterIsNotNull(paramNameResolver, "nameResolver");
    Object localObject1 = Flags.IS_UNSIGNED.get(paramValue.getFlags());
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "Flags.IS_UNSIGNED.get(value.flags)");
    boolean bool = ((Boolean)localObject1).booleanValue();
    localObject1 = paramValue.getType();
    if (localObject1 != null)
    {
      switch (AnnotationDeserializer.WhenMappings.$EnumSwitchMapping$0[localObject1.ordinal()])
      {
      default: 
        break;
      case 13: 
        localObject1 = ConstantValueFactory.INSTANCE;
        paramValue = paramValue.getArrayElementList();
        Intrinsics.checkExpressionValueIsNotNull(paramValue, "value.arrayElementList");
        Object localObject2 = (Iterable)paramValue;
        paramValue = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
        Iterator localIterator = ((Iterable)localObject2).iterator();
        while (localIterator.hasNext())
        {
          localObject2 = (ProtoBuf.Annotation.Argument.Value)localIterator.next();
          Object localObject3 = getBuiltIns().getAnyType();
          Intrinsics.checkExpressionValueIsNotNull(localObject3, "builtIns.anyType");
          localObject3 = (KotlinType)localObject3;
          Intrinsics.checkExpressionValueIsNotNull(localObject2, "it");
          paramValue.add(resolveValue((KotlinType)localObject3, (ProtoBuf.Annotation.Argument.Value)localObject2, paramNameResolver));
        }
        paramKotlinType = (ConstantValue)((ConstantValueFactory)localObject1).createArrayValue((List)paramValue, paramKotlinType);
        break;
      case 12: 
        paramKotlinType = paramValue.getAnnotation();
        Intrinsics.checkExpressionValueIsNotNull(paramKotlinType, "value.annotation");
        paramKotlinType = (ConstantValue)new AnnotationValue(deserializeAnnotation(paramKotlinType, paramNameResolver));
        break;
      case 11: 
        paramKotlinType = (ConstantValue)new EnumValue(NameResolverUtilKt.getClassId(paramNameResolver, paramValue.getClassId()), NameResolverUtilKt.getName(paramNameResolver, paramValue.getEnumValueId()));
        break;
      case 10: 
        paramKotlinType = (ConstantValue)new KClassValue(NameResolverUtilKt.getClassId(paramNameResolver, paramValue.getClassId()), paramValue.getArrayDimensionCount());
        break;
      case 9: 
        paramKotlinType = (ConstantValue)new StringValue(paramNameResolver.getString(paramValue.getStringValue()));
        break;
      case 8: 
        if (paramValue.getIntValue() != 0L) {
          bool = true;
        } else {
          bool = false;
        }
        paramKotlinType = (ConstantValue)new BooleanValue(bool);
        break;
      case 7: 
        paramKotlinType = (ConstantValue)new DoubleValue(paramValue.getDoubleValue());
        break;
      case 6: 
        paramKotlinType = (ConstantValue)new FloatValue(paramValue.getFloatValue());
        break;
      case 5: 
        long l = paramValue.getIntValue();
        if (bool) {
          paramKotlinType = new ULongValue(l);
        } else {
          paramKotlinType = new LongValue(l);
        }
        paramKotlinType = (ConstantValue)paramKotlinType;
        break;
      case 4: 
        int i = (int)paramValue.getIntValue();
        if (bool) {
          paramKotlinType = new UIntValue(i);
        } else {
          paramKotlinType = new IntValue(i);
        }
        paramKotlinType = (ConstantValue)paramKotlinType;
        break;
      case 3: 
        short s = (short)(int)paramValue.getIntValue();
        if (bool) {
          paramKotlinType = new UShortValue(s);
        } else {
          paramKotlinType = new ShortValue(s);
        }
        paramKotlinType = (ConstantValue)paramKotlinType;
        break;
      case 2: 
        paramKotlinType = (ConstantValue)new CharValue((char)(int)paramValue.getIntValue());
        break;
      case 1: 
        byte b = (byte)(int)paramValue.getIntValue();
        if (bool) {
          paramKotlinType = new UByteValue(b);
        } else {
          paramKotlinType = new ByteValue(b);
        }
        paramKotlinType = (ConstantValue)paramKotlinType;
      }
      return paramKotlinType;
    }
    paramNameResolver = new StringBuilder();
    paramNameResolver.append("Unsupported annotation argument type: ");
    paramNameResolver.append(paramValue.getType());
    paramNameResolver.append(" (expected ");
    paramNameResolver.append(paramKotlinType);
    paramNameResolver.append(')');
    throw ((Throwable)new IllegalStateException(paramNameResolver.toString().toString()));
  }
}

package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation.Argument.Value;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Class;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Constructor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.EnumEntry;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Function;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Property;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeParameter;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.ValueParameter;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoBufUtilKt;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.ExtendableMessage;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.serialization.SerializerExtensionProtocol;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public final class AnnotationAndConstantLoaderImpl
  implements AnnotationAndConstantLoader<AnnotationDescriptor, ConstantValue<?>>
{
  private final AnnotationDeserializer deserializer;
  private final SerializerExtensionProtocol protocol;
  
  public AnnotationAndConstantLoaderImpl(ModuleDescriptor paramModuleDescriptor, NotFoundClasses paramNotFoundClasses, SerializerExtensionProtocol paramSerializerExtensionProtocol)
  {
    this.protocol = paramSerializerExtensionProtocol;
    this.deserializer = new AnnotationDeserializer(paramModuleDescriptor, paramNotFoundClasses);
  }
  
  public List<AnnotationDescriptor> loadCallableAnnotations(ProtoContainer paramProtoContainer, MessageLite paramMessageLite, AnnotatedCallableKind paramAnnotatedCallableKind)
  {
    Intrinsics.checkParameterIsNotNull(paramProtoContainer, "container");
    Intrinsics.checkParameterIsNotNull(paramMessageLite, "proto");
    Intrinsics.checkParameterIsNotNull(paramAnnotatedCallableKind, "kind");
    if ((paramMessageLite instanceof ProtoBuf.Constructor))
    {
      paramMessageLite = (List)((ProtoBuf.Constructor)paramMessageLite).getExtension(this.protocol.getConstructorAnnotation());
    }
    else if ((paramMessageLite instanceof ProtoBuf.Function))
    {
      paramMessageLite = (List)((ProtoBuf.Function)paramMessageLite).getExtension(this.protocol.getFunctionAnnotation());
    }
    else
    {
      if (!(paramMessageLite instanceof ProtoBuf.Property)) {
        break label273;
      }
      int i = AnnotationAndConstantLoaderImpl.WhenMappings.$EnumSwitchMapping$0[paramAnnotatedCallableKind.ordinal()];
      if (i != 1)
      {
        if (i != 2)
        {
          if (i == 3) {
            paramMessageLite = (List)((ProtoBuf.Property)paramMessageLite).getExtension(this.protocol.getPropertySetterAnnotation());
          } else {
            throw ((Throwable)new IllegalStateException("Unsupported callable kind with property proto".toString()));
          }
        }
        else {
          paramMessageLite = (List)((ProtoBuf.Property)paramMessageLite).getExtension(this.protocol.getPropertyGetterAnnotation());
        }
      }
      else {
        paramMessageLite = (List)((ProtoBuf.Property)paramMessageLite).getExtension(this.protocol.getPropertyAnnotation());
      }
    }
    if (paramMessageLite == null) {
      paramMessageLite = CollectionsKt.emptyList();
    }
    paramAnnotatedCallableKind = (Iterable)paramMessageLite;
    paramMessageLite = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault(paramAnnotatedCallableKind, 10));
    paramAnnotatedCallableKind = paramAnnotatedCallableKind.iterator();
    while (paramAnnotatedCallableKind.hasNext())
    {
      ProtoBuf.Annotation localAnnotation = (ProtoBuf.Annotation)paramAnnotatedCallableKind.next();
      paramMessageLite.add(this.deserializer.deserializeAnnotation(localAnnotation, paramProtoContainer.getNameResolver()));
    }
    return (List)paramMessageLite;
    label273:
    paramProtoContainer = new StringBuilder();
    paramProtoContainer.append("Unknown message: ");
    paramProtoContainer.append(paramMessageLite);
    throw ((Throwable)new IllegalStateException(paramProtoContainer.toString().toString()));
  }
  
  public List<AnnotationDescriptor> loadClassAnnotations(ProtoContainer.Class paramClass)
  {
    Intrinsics.checkParameterIsNotNull(paramClass, "container");
    Object localObject1 = (List)paramClass.getClassProto().getExtension(this.protocol.getClassAnnotation());
    if (localObject1 == null) {
      localObject1 = CollectionsKt.emptyList();
    }
    Object localObject2 = (Iterable)localObject1;
    localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
    localObject2 = ((Iterable)localObject2).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      ProtoBuf.Annotation localAnnotation = (ProtoBuf.Annotation)((Iterator)localObject2).next();
      ((Collection)localObject1).add(this.deserializer.deserializeAnnotation(localAnnotation, paramClass.getNameResolver()));
    }
    return (List)localObject1;
  }
  
  public List<AnnotationDescriptor> loadEnumEntryAnnotations(ProtoContainer paramProtoContainer, ProtoBuf.EnumEntry paramEnumEntry)
  {
    Intrinsics.checkParameterIsNotNull(paramProtoContainer, "container");
    Intrinsics.checkParameterIsNotNull(paramEnumEntry, "proto");
    paramEnumEntry = (List)paramEnumEntry.getExtension(this.protocol.getEnumEntryAnnotation());
    if (paramEnumEntry == null) {
      paramEnumEntry = CollectionsKt.emptyList();
    }
    Object localObject = (Iterable)paramEnumEntry;
    paramEnumEntry = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject, 10));
    localObject = ((Iterable)localObject).iterator();
    while (((Iterator)localObject).hasNext())
    {
      ProtoBuf.Annotation localAnnotation = (ProtoBuf.Annotation)((Iterator)localObject).next();
      paramEnumEntry.add(this.deserializer.deserializeAnnotation(localAnnotation, paramProtoContainer.getNameResolver()));
    }
    return (List)paramEnumEntry;
  }
  
  public List<AnnotationDescriptor> loadExtensionReceiverParameterAnnotations(ProtoContainer paramProtoContainer, MessageLite paramMessageLite, AnnotatedCallableKind paramAnnotatedCallableKind)
  {
    Intrinsics.checkParameterIsNotNull(paramProtoContainer, "container");
    Intrinsics.checkParameterIsNotNull(paramMessageLite, "proto");
    Intrinsics.checkParameterIsNotNull(paramAnnotatedCallableKind, "kind");
    return CollectionsKt.emptyList();
  }
  
  public List<AnnotationDescriptor> loadPropertyBackingFieldAnnotations(ProtoContainer paramProtoContainer, ProtoBuf.Property paramProperty)
  {
    Intrinsics.checkParameterIsNotNull(paramProtoContainer, "container");
    Intrinsics.checkParameterIsNotNull(paramProperty, "proto");
    return CollectionsKt.emptyList();
  }
  
  public ConstantValue<?> loadPropertyConstant(ProtoContainer paramProtoContainer, ProtoBuf.Property paramProperty, KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramProtoContainer, "container");
    Intrinsics.checkParameterIsNotNull(paramProperty, "proto");
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "expectedType");
    paramProperty = (ProtoBuf.Annotation.Argument.Value)ProtoBufUtilKt.getExtensionOrNull((GeneratedMessageLite.ExtendableMessage)paramProperty, this.protocol.getCompileTimeValue());
    if (paramProperty != null) {
      return this.deserializer.resolveValue(paramKotlinType, paramProperty, paramProtoContainer.getNameResolver());
    }
    return null;
  }
  
  public List<AnnotationDescriptor> loadPropertyDelegateFieldAnnotations(ProtoContainer paramProtoContainer, ProtoBuf.Property paramProperty)
  {
    Intrinsics.checkParameterIsNotNull(paramProtoContainer, "container");
    Intrinsics.checkParameterIsNotNull(paramProperty, "proto");
    return CollectionsKt.emptyList();
  }
  
  public List<AnnotationDescriptor> loadTypeAnnotations(ProtoBuf.Type paramType, NameResolver paramNameResolver)
  {
    Intrinsics.checkParameterIsNotNull(paramType, "proto");
    Intrinsics.checkParameterIsNotNull(paramNameResolver, "nameResolver");
    paramType = (List)paramType.getExtension(this.protocol.getTypeAnnotation());
    if (paramType == null) {
      paramType = CollectionsKt.emptyList();
    }
    Object localObject = (Iterable)paramType;
    paramType = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject, 10));
    Iterator localIterator = ((Iterable)localObject).iterator();
    while (localIterator.hasNext())
    {
      localObject = (ProtoBuf.Annotation)localIterator.next();
      paramType.add(this.deserializer.deserializeAnnotation((ProtoBuf.Annotation)localObject, paramNameResolver));
    }
    return (List)paramType;
  }
  
  public List<AnnotationDescriptor> loadTypeParameterAnnotations(ProtoBuf.TypeParameter paramTypeParameter, NameResolver paramNameResolver)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeParameter, "proto");
    Intrinsics.checkParameterIsNotNull(paramNameResolver, "nameResolver");
    paramTypeParameter = (List)paramTypeParameter.getExtension(this.protocol.getTypeParameterAnnotation());
    if (paramTypeParameter == null) {
      paramTypeParameter = CollectionsKt.emptyList();
    }
    Object localObject = (Iterable)paramTypeParameter;
    paramTypeParameter = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject, 10));
    Iterator localIterator = ((Iterable)localObject).iterator();
    while (localIterator.hasNext())
    {
      localObject = (ProtoBuf.Annotation)localIterator.next();
      paramTypeParameter.add(this.deserializer.deserializeAnnotation((ProtoBuf.Annotation)localObject, paramNameResolver));
    }
    return (List)paramTypeParameter;
  }
  
  public List<AnnotationDescriptor> loadValueParameterAnnotations(ProtoContainer paramProtoContainer, MessageLite paramMessageLite, AnnotatedCallableKind paramAnnotatedCallableKind, int paramInt, ProtoBuf.ValueParameter paramValueParameter)
  {
    Intrinsics.checkParameterIsNotNull(paramProtoContainer, "container");
    Intrinsics.checkParameterIsNotNull(paramMessageLite, "callableProto");
    Intrinsics.checkParameterIsNotNull(paramAnnotatedCallableKind, "kind");
    Intrinsics.checkParameterIsNotNull(paramValueParameter, "proto");
    paramMessageLite = (List)paramValueParameter.getExtension(this.protocol.getParameterAnnotation());
    if (paramMessageLite == null) {
      paramMessageLite = CollectionsKt.emptyList();
    }
    paramAnnotatedCallableKind = (Iterable)paramMessageLite;
    paramMessageLite = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault(paramAnnotatedCallableKind, 10));
    paramAnnotatedCallableKind = paramAnnotatedCallableKind.iterator();
    while (paramAnnotatedCallableKind.hasNext())
    {
      paramValueParameter = (ProtoBuf.Annotation)paramAnnotatedCallableKind.next();
      paramMessageLite.add(this.deserializer.deserializeAnnotation(paramValueParameter, paramProtoContainer.getNameResolver()));
    }
    return (List)paramMessageLite;
  }
}

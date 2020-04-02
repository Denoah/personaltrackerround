package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.EnumEntry;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Property;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeParameter;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.ValueParameter;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public abstract interface AnnotationAndConstantLoader<A, C>
{
  public abstract List<A> loadCallableAnnotations(ProtoContainer paramProtoContainer, MessageLite paramMessageLite, AnnotatedCallableKind paramAnnotatedCallableKind);
  
  public abstract List<A> loadClassAnnotations(ProtoContainer.Class paramClass);
  
  public abstract List<A> loadEnumEntryAnnotations(ProtoContainer paramProtoContainer, ProtoBuf.EnumEntry paramEnumEntry);
  
  public abstract List<A> loadExtensionReceiverParameterAnnotations(ProtoContainer paramProtoContainer, MessageLite paramMessageLite, AnnotatedCallableKind paramAnnotatedCallableKind);
  
  public abstract List<A> loadPropertyBackingFieldAnnotations(ProtoContainer paramProtoContainer, ProtoBuf.Property paramProperty);
  
  public abstract C loadPropertyConstant(ProtoContainer paramProtoContainer, ProtoBuf.Property paramProperty, KotlinType paramKotlinType);
  
  public abstract List<A> loadPropertyDelegateFieldAnnotations(ProtoContainer paramProtoContainer, ProtoBuf.Property paramProperty);
  
  public abstract List<A> loadTypeAnnotations(ProtoBuf.Type paramType, NameResolver paramNameResolver);
  
  public abstract List<A> loadTypeParameterAnnotations(ProtoBuf.TypeParameter paramTypeParameter, NameResolver paramNameResolver);
  
  public abstract List<A> loadValueParameterAnnotations(ProtoContainer paramProtoContainer, MessageLite paramMessageLite, AnnotatedCallableKind paramAnnotatedCallableKind, int paramInt, ProtoBuf.ValueParameter paramValueParameter);
}

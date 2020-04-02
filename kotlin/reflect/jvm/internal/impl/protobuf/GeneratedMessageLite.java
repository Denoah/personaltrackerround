package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public abstract class GeneratedMessageLite
  extends AbstractMessageLite
  implements Serializable
{
  protected GeneratedMessageLite() {}
  
  protected GeneratedMessageLite(Builder paramBuilder) {}
  
  static Method getMethodOrDie(Class paramClass, String paramString, Class... paramVarArgs)
  {
    try
    {
      paramVarArgs = paramClass.getMethod(paramString, paramVarArgs);
      return paramVarArgs;
    }
    catch (NoSuchMethodException paramVarArgs)
    {
      paramClass = String.valueOf(String.valueOf(paramClass.getName()));
      paramString = String.valueOf(String.valueOf(paramString));
      StringBuilder localStringBuilder = new StringBuilder(paramClass.length() + 45 + paramString.length());
      localStringBuilder.append("Generated message class \"");
      localStringBuilder.append(paramClass);
      localStringBuilder.append("\" missing method \"");
      localStringBuilder.append(paramString);
      localStringBuilder.append("\".");
      throw new RuntimeException(localStringBuilder.toString(), paramVarArgs);
    }
  }
  
  static Object invokeOrDie(Method paramMethod, Object paramObject, Object... paramVarArgs)
  {
    try
    {
      paramMethod = paramMethod.invoke(paramObject, paramVarArgs);
      return paramMethod;
    }
    catch (InvocationTargetException paramMethod)
    {
      paramMethod = paramMethod.getCause();
      if (!(paramMethod instanceof RuntimeException))
      {
        if ((paramMethod instanceof Error)) {
          throw ((Error)paramMethod);
        }
        throw new RuntimeException("Unexpected exception thrown by generated accessor method.", paramMethod);
      }
      throw ((RuntimeException)paramMethod);
    }
    catch (IllegalAccessException paramMethod)
    {
      throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", paramMethod);
    }
  }
  
  public static <ContainingType extends MessageLite, Type> GeneratedExtension<ContainingType, Type> newRepeatedGeneratedExtension(ContainingType paramContainingType, MessageLite paramMessageLite, Internal.EnumLiteMap<?> paramEnumLiteMap, int paramInt, WireFormat.FieldType paramFieldType, boolean paramBoolean, Class paramClass)
  {
    return new GeneratedExtension(paramContainingType, Collections.emptyList(), paramMessageLite, new ExtensionDescriptor(paramEnumLiteMap, paramInt, paramFieldType, true, paramBoolean), paramClass);
  }
  
  public static <ContainingType extends MessageLite, Type> GeneratedExtension<ContainingType, Type> newSingularGeneratedExtension(ContainingType paramContainingType, Type paramType, MessageLite paramMessageLite, Internal.EnumLiteMap<?> paramEnumLiteMap, int paramInt, WireFormat.FieldType paramFieldType, Class paramClass)
  {
    return new GeneratedExtension(paramContainingType, paramType, paramMessageLite, new ExtensionDescriptor(paramEnumLiteMap, paramInt, paramFieldType, false, false), paramClass);
  }
  
  private static <MessageType extends MessageLite> boolean parseUnknownField(FieldSet<ExtensionDescriptor> paramFieldSet, MessageType paramMessageType, CodedInputStream paramCodedInputStream, CodedOutputStream paramCodedOutputStream, ExtensionRegistryLite paramExtensionRegistryLite, int paramInt)
    throws IOException
  {
    int i = WireFormat.getTagWireType(paramInt);
    GeneratedExtension localGeneratedExtension = paramExtensionRegistryLite.findLiteExtensionByNumber(paramMessageType, WireFormat.getTagFieldNumber(paramInt));
    if (localGeneratedExtension == null) {}
    do
    {
      j = 0;
      i = 1;
      break;
      if (i == FieldSet.getWireFormatForFieldType(localGeneratedExtension.descriptor.getLiteType(), false))
      {
        i = 0;
        j = i;
        break;
      }
    } while ((!localGeneratedExtension.descriptor.isRepeated) || (!localGeneratedExtension.descriptor.type.isPackable()) || (i != FieldSet.getWireFormatForFieldType(localGeneratedExtension.descriptor.getLiteType(), true)));
    i = 0;
    int j = 1;
    if (i != 0) {
      return paramCodedInputStream.skipField(paramInt, paramCodedOutputStream);
    }
    if (j != 0)
    {
      i = paramCodedInputStream.pushLimit(paramCodedInputStream.readRawVarint32());
      if (localGeneratedExtension.descriptor.getLiteType() == WireFormat.FieldType.ENUM) {
        while (paramCodedInputStream.getBytesUntilLimit() > 0)
        {
          paramInt = paramCodedInputStream.readEnum();
          paramMessageType = localGeneratedExtension.descriptor.getEnumType().findValueByNumber(paramInt);
          if (paramMessageType == null) {
            return true;
          }
          paramFieldSet.addRepeatedField(localGeneratedExtension.descriptor, localGeneratedExtension.singularToFieldSetType(paramMessageType));
        }
      }
      while (paramCodedInputStream.getBytesUntilLimit() > 0)
      {
        paramMessageType = FieldSet.readPrimitiveField(paramCodedInputStream, localGeneratedExtension.descriptor.getLiteType(), false);
        paramFieldSet.addRepeatedField(localGeneratedExtension.descriptor, paramMessageType);
      }
      paramCodedInputStream.popLimit(i);
    }
    else
    {
      i = 1.$SwitchMap$com$google$protobuf$WireFormat$JavaType[localGeneratedExtension.descriptor.getLiteJavaType().ordinal()];
      if (i != 1)
      {
        if (i != 2)
        {
          paramMessageType = FieldSet.readPrimitiveField(paramCodedInputStream, localGeneratedExtension.descriptor.getLiteType(), false);
        }
        else
        {
          i = paramCodedInputStream.readEnum();
          paramMessageType = localGeneratedExtension.descriptor.getEnumType().findValueByNumber(i);
          if (paramMessageType == null)
          {
            paramCodedOutputStream.writeRawVarint32(paramInt);
            paramCodedOutputStream.writeUInt32NoTag(i);
            return true;
          }
        }
      }
      else
      {
        paramCodedOutputStream = null;
        paramMessageType = paramCodedOutputStream;
        if (!localGeneratedExtension.descriptor.isRepeated())
        {
          MessageLite localMessageLite = (MessageLite)paramFieldSet.getField(localGeneratedExtension.descriptor);
          paramMessageType = paramCodedOutputStream;
          if (localMessageLite != null) {
            paramMessageType = localMessageLite.toBuilder();
          }
        }
        paramCodedOutputStream = paramMessageType;
        if (paramMessageType == null) {
          paramCodedOutputStream = localGeneratedExtension.getMessageDefaultInstance().newBuilderForType();
        }
        if (localGeneratedExtension.descriptor.getLiteType() == WireFormat.FieldType.GROUP) {
          paramCodedInputStream.readGroup(localGeneratedExtension.getNumber(), paramCodedOutputStream, paramExtensionRegistryLite);
        } else {
          paramCodedInputStream.readMessage(paramCodedOutputStream, paramExtensionRegistryLite);
        }
        paramMessageType = paramCodedOutputStream.build();
      }
      if (localGeneratedExtension.descriptor.isRepeated()) {
        paramFieldSet.addRepeatedField(localGeneratedExtension.descriptor, localGeneratedExtension.singularToFieldSetType(paramMessageType));
      } else {
        paramFieldSet.setField(localGeneratedExtension.descriptor, localGeneratedExtension.singularToFieldSetType(paramMessageType));
      }
    }
    return true;
  }
  
  public Parser<? extends MessageLite> getParserForType()
  {
    throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
  }
  
  protected void makeExtensionsImmutable() {}
  
  protected boolean parseUnknownField(CodedInputStream paramCodedInputStream, CodedOutputStream paramCodedOutputStream, ExtensionRegistryLite paramExtensionRegistryLite, int paramInt)
    throws IOException
  {
    return paramCodedInputStream.skipField(paramInt, paramCodedOutputStream);
  }
  
  public static abstract class Builder<MessageType extends GeneratedMessageLite, BuilderType extends Builder>
    extends AbstractMessageLite.Builder<BuilderType>
  {
    private ByteString unknownFields = ByteString.EMPTY;
    
    protected Builder() {}
    
    public BuilderType clone()
    {
      throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
    }
    
    public abstract MessageType getDefaultInstanceForType();
    
    public final ByteString getUnknownFields()
    {
      return this.unknownFields;
    }
    
    public abstract BuilderType mergeFrom(MessageType paramMessageType);
    
    public final BuilderType setUnknownFields(ByteString paramByteString)
    {
      this.unknownFields = paramByteString;
      return this;
    }
  }
  
  public static abstract class ExtendableBuilder<MessageType extends GeneratedMessageLite.ExtendableMessage<MessageType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>>
    extends GeneratedMessageLite.Builder<MessageType, BuilderType>
    implements GeneratedMessageLite.ExtendableMessageOrBuilder<MessageType>
  {
    private FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions = FieldSet.emptySet();
    private boolean extensionsIsMutable;
    
    protected ExtendableBuilder() {}
    
    private FieldSet<GeneratedMessageLite.ExtensionDescriptor> buildExtensions()
    {
      this.extensions.makeImmutable();
      this.extensionsIsMutable = false;
      return this.extensions;
    }
    
    private void ensureExtensionsIsMutable()
    {
      if (!this.extensionsIsMutable)
      {
        this.extensions = this.extensions.clone();
        this.extensionsIsMutable = true;
      }
    }
    
    public BuilderType clone()
    {
      throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
    }
    
    protected boolean extensionsAreInitialized()
    {
      return this.extensions.isInitialized();
    }
    
    protected final void mergeExtensionFields(MessageType paramMessageType)
    {
      ensureExtensionsIsMutable();
      this.extensions.mergeFrom(GeneratedMessageLite.ExtendableMessage.access$200(paramMessageType));
    }
  }
  
  public static abstract class ExtendableMessage<MessageType extends ExtendableMessage<MessageType>>
    extends GeneratedMessageLite
    implements GeneratedMessageLite.ExtendableMessageOrBuilder<MessageType>
  {
    private final FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions;
    
    protected ExtendableMessage()
    {
      this.extensions = FieldSet.newFieldSet();
    }
    
    protected ExtendableMessage(GeneratedMessageLite.ExtendableBuilder<MessageType, ?> paramExtendableBuilder)
    {
      this.extensions = paramExtendableBuilder.buildExtensions();
    }
    
    private void verifyExtensionContainingType(GeneratedMessageLite.GeneratedExtension<MessageType, ?> paramGeneratedExtension)
    {
      if (paramGeneratedExtension.getContainingTypeDefaultInstance() == getDefaultInstanceForType()) {
        return;
      }
      throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
    }
    
    protected boolean extensionsAreInitialized()
    {
      return this.extensions.isInitialized();
    }
    
    protected int extensionsSerializedSize()
    {
      return this.extensions.getSerializedSize();
    }
    
    public final <Type> Type getExtension(GeneratedMessageLite.GeneratedExtension<MessageType, Type> paramGeneratedExtension)
    {
      verifyExtensionContainingType(paramGeneratedExtension);
      Object localObject = this.extensions.getField(paramGeneratedExtension.descriptor);
      if (localObject == null) {
        return paramGeneratedExtension.defaultValue;
      }
      return paramGeneratedExtension.fromFieldSetType(localObject);
    }
    
    public final <Type> Type getExtension(GeneratedMessageLite.GeneratedExtension<MessageType, List<Type>> paramGeneratedExtension, int paramInt)
    {
      verifyExtensionContainingType(paramGeneratedExtension);
      return paramGeneratedExtension.singularFromFieldSetType(this.extensions.getRepeatedField(paramGeneratedExtension.descriptor, paramInt));
    }
    
    public final <Type> int getExtensionCount(GeneratedMessageLite.GeneratedExtension<MessageType, List<Type>> paramGeneratedExtension)
    {
      verifyExtensionContainingType(paramGeneratedExtension);
      return this.extensions.getRepeatedFieldCount(paramGeneratedExtension.descriptor);
    }
    
    public final <Type> boolean hasExtension(GeneratedMessageLite.GeneratedExtension<MessageType, Type> paramGeneratedExtension)
    {
      verifyExtensionContainingType(paramGeneratedExtension);
      return this.extensions.hasField(paramGeneratedExtension.descriptor);
    }
    
    protected void makeExtensionsImmutable()
    {
      this.extensions.makeImmutable();
    }
    
    protected ExtendableMessage<MessageType>.ExtensionWriter newExtensionWriter()
    {
      return new ExtensionWriter(false, null);
    }
    
    protected boolean parseUnknownField(CodedInputStream paramCodedInputStream, CodedOutputStream paramCodedOutputStream, ExtensionRegistryLite paramExtensionRegistryLite, int paramInt)
      throws IOException
    {
      return GeneratedMessageLite.parseUnknownField(this.extensions, getDefaultInstanceForType(), paramCodedInputStream, paramCodedOutputStream, paramExtensionRegistryLite, paramInt);
    }
    
    protected class ExtensionWriter
    {
      private final Iterator<Map.Entry<GeneratedMessageLite.ExtensionDescriptor, Object>> iter;
      private final boolean messageSetWireFormat;
      private Map.Entry<GeneratedMessageLite.ExtensionDescriptor, Object> next;
      
      private ExtensionWriter(boolean paramBoolean)
      {
        this$1 = GeneratedMessageLite.ExtendableMessage.this.extensions.iterator();
        this.iter = GeneratedMessageLite.ExtendableMessage.this;
        if (GeneratedMessageLite.ExtendableMessage.this.hasNext()) {
          this.next = ((Map.Entry)this.iter.next());
        }
        this.messageSetWireFormat = paramBoolean;
      }
      
      public void writeUntil(int paramInt, CodedOutputStream paramCodedOutputStream)
        throws IOException
      {
        for (;;)
        {
          Object localObject = this.next;
          if ((localObject == null) || (((GeneratedMessageLite.ExtensionDescriptor)((Map.Entry)localObject).getKey()).getNumber() >= paramInt)) {
            break;
          }
          localObject = (GeneratedMessageLite.ExtensionDescriptor)this.next.getKey();
          if ((this.messageSetWireFormat) && (((GeneratedMessageLite.ExtensionDescriptor)localObject).getLiteJavaType() == WireFormat.JavaType.MESSAGE) && (!((GeneratedMessageLite.ExtensionDescriptor)localObject).isRepeated())) {
            paramCodedOutputStream.writeMessageSetExtension(((GeneratedMessageLite.ExtensionDescriptor)localObject).getNumber(), (MessageLite)this.next.getValue());
          } else {
            FieldSet.writeField((FieldSet.FieldDescriptorLite)localObject, this.next.getValue(), paramCodedOutputStream);
          }
          if (this.iter.hasNext()) {
            this.next = ((Map.Entry)this.iter.next());
          } else {
            this.next = null;
          }
        }
      }
    }
  }
  
  public static abstract interface ExtendableMessageOrBuilder<MessageType extends GeneratedMessageLite.ExtendableMessage>
    extends MessageLiteOrBuilder
  {}
  
  static final class ExtensionDescriptor
    implements FieldSet.FieldDescriptorLite<ExtensionDescriptor>
  {
    final Internal.EnumLiteMap<?> enumTypeMap;
    final boolean isPacked;
    final boolean isRepeated;
    final int number;
    final WireFormat.FieldType type;
    
    ExtensionDescriptor(Internal.EnumLiteMap<?> paramEnumLiteMap, int paramInt, WireFormat.FieldType paramFieldType, boolean paramBoolean1, boolean paramBoolean2)
    {
      this.enumTypeMap = paramEnumLiteMap;
      this.number = paramInt;
      this.type = paramFieldType;
      this.isRepeated = paramBoolean1;
      this.isPacked = paramBoolean2;
    }
    
    public int compareTo(ExtensionDescriptor paramExtensionDescriptor)
    {
      return this.number - paramExtensionDescriptor.number;
    }
    
    public Internal.EnumLiteMap<?> getEnumType()
    {
      return this.enumTypeMap;
    }
    
    public WireFormat.JavaType getLiteJavaType()
    {
      return this.type.getJavaType();
    }
    
    public WireFormat.FieldType getLiteType()
    {
      return this.type;
    }
    
    public int getNumber()
    {
      return this.number;
    }
    
    public MessageLite.Builder internalMergeFrom(MessageLite.Builder paramBuilder, MessageLite paramMessageLite)
    {
      return ((GeneratedMessageLite.Builder)paramBuilder).mergeFrom((GeneratedMessageLite)paramMessageLite);
    }
    
    public boolean isPacked()
    {
      return this.isPacked;
    }
    
    public boolean isRepeated()
    {
      return this.isRepeated;
    }
  }
  
  public static class GeneratedExtension<ContainingType extends MessageLite, Type>
  {
    final ContainingType containingTypeDefaultInstance;
    final Type defaultValue;
    final GeneratedMessageLite.ExtensionDescriptor descriptor;
    final Method enumValueOf;
    final MessageLite messageDefaultInstance;
    final Class singularType;
    
    GeneratedExtension(ContainingType paramContainingType, Type paramType, MessageLite paramMessageLite, GeneratedMessageLite.ExtensionDescriptor paramExtensionDescriptor, Class paramClass)
    {
      if (paramContainingType != null)
      {
        if ((paramExtensionDescriptor.getLiteType() == WireFormat.FieldType.MESSAGE) && (paramMessageLite == null)) {
          throw new IllegalArgumentException("Null messageDefaultInstance");
        }
        this.containingTypeDefaultInstance = paramContainingType;
        this.defaultValue = paramType;
        this.messageDefaultInstance = paramMessageLite;
        this.descriptor = paramExtensionDescriptor;
        this.singularType = paramClass;
        if (Internal.EnumLite.class.isAssignableFrom(paramClass)) {
          this.enumValueOf = GeneratedMessageLite.getMethodOrDie(paramClass, "valueOf", new Class[] { Integer.TYPE });
        } else {
          this.enumValueOf = null;
        }
        return;
      }
      throw new IllegalArgumentException("Null containingTypeDefaultInstance");
    }
    
    Object fromFieldSetType(Object paramObject)
    {
      if (this.descriptor.isRepeated())
      {
        if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM)
        {
          ArrayList localArrayList = new ArrayList();
          paramObject = ((List)paramObject).iterator();
          while (paramObject.hasNext()) {
            localArrayList.add(singularFromFieldSetType(paramObject.next()));
          }
          return localArrayList;
        }
        return paramObject;
      }
      return singularFromFieldSetType(paramObject);
    }
    
    public ContainingType getContainingTypeDefaultInstance()
    {
      return this.containingTypeDefaultInstance;
    }
    
    public MessageLite getMessageDefaultInstance()
    {
      return this.messageDefaultInstance;
    }
    
    public int getNumber()
    {
      return this.descriptor.getNumber();
    }
    
    Object singularFromFieldSetType(Object paramObject)
    {
      Object localObject = paramObject;
      if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
        localObject = GeneratedMessageLite.invokeOrDie(this.enumValueOf, null, new Object[] { (Integer)paramObject });
      }
      return localObject;
    }
    
    Object singularToFieldSetType(Object paramObject)
    {
      Object localObject = paramObject;
      if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
        localObject = Integer.valueOf(((Internal.EnumLite)paramObject).getNumber());
      }
      return localObject;
    }
  }
}

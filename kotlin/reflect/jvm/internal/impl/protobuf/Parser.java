package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.InputStream;

public abstract interface Parser<MessageType>
{
  public abstract MessageType parseDelimitedFrom(InputStream paramInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
    throws InvalidProtocolBufferException;
  
  public abstract MessageType parseFrom(InputStream paramInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
    throws InvalidProtocolBufferException;
  
  public abstract MessageType parseFrom(ByteString paramByteString, ExtensionRegistryLite paramExtensionRegistryLite)
    throws InvalidProtocolBufferException;
  
  public abstract MessageType parsePartialFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
    throws InvalidProtocolBufferException;
}

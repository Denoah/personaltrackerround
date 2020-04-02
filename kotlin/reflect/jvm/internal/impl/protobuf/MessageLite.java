package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.IOException;

public abstract interface MessageLite
  extends MessageLiteOrBuilder
{
  public abstract Parser<? extends MessageLite> getParserForType();
  
  public abstract int getSerializedSize();
  
  public abstract Builder newBuilderForType();
  
  public abstract Builder toBuilder();
  
  public abstract void writeTo(CodedOutputStream paramCodedOutputStream)
    throws IOException;
  
  public static abstract interface Builder
    extends Cloneable, MessageLiteOrBuilder
  {
    public abstract MessageLite build();
    
    public abstract Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws IOException;
  }
}

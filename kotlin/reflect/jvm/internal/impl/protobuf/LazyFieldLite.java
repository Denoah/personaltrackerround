package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.IOException;

public class LazyFieldLite
{
  private ByteString bytes;
  private ExtensionRegistryLite extensionRegistry;
  private volatile boolean isDirty;
  protected volatile MessageLite value;
  
  protected void ensureInitialized(MessageLite paramMessageLite)
  {
    if (this.value != null) {
      return;
    }
    for (;;)
    {
      try
      {
        if (this.value != null) {
          return;
        }
      }
      finally {}
      try
      {
        if (this.bytes != null) {
          this.value = ((MessageLite)paramMessageLite.getParserForType().parseFrom(this.bytes, this.extensionRegistry));
        } else {
          this.value = paramMessageLite;
        }
      }
      catch (IOException paramMessageLite) {}
    }
  }
  
  public int getSerializedSize()
  {
    if (this.isDirty) {
      return this.value.getSerializedSize();
    }
    return this.bytes.size();
  }
  
  public MessageLite getValue(MessageLite paramMessageLite)
  {
    ensureInitialized(paramMessageLite);
    return this.value;
  }
  
  public MessageLite setValue(MessageLite paramMessageLite)
  {
    MessageLite localMessageLite = this.value;
    this.value = paramMessageLite;
    this.bytes = null;
    this.isDirty = true;
    return localMessageLite;
  }
}

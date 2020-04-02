package kotlin.reflect.jvm.internal.impl.protobuf;

import java.util.List;

public abstract interface LazyStringList
  extends ProtocolStringList
{
  public abstract void add(ByteString paramByteString);
  
  public abstract ByteString getByteString(int paramInt);
  
  public abstract List<?> getUnderlyingElements();
  
  public abstract LazyStringList getUnmodifiableView();
}

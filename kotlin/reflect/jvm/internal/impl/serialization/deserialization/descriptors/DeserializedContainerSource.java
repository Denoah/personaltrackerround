package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;

public abstract interface DeserializedContainerSource
  extends SourceElement
{
  public abstract String getPresentableString();
}

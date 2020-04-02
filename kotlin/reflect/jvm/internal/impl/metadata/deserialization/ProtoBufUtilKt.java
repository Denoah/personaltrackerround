package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.ExtendableMessage;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.GeneratedExtension;

public final class ProtoBufUtilKt
{
  public static final <M extends GeneratedMessageLite.ExtendableMessage<M>, T> T getExtensionOrNull(GeneratedMessageLite.ExtendableMessage<M> paramExtendableMessage, GeneratedMessageLite.GeneratedExtension<M, T> paramGeneratedExtension)
  {
    Intrinsics.checkParameterIsNotNull(paramExtendableMessage, "$this$getExtensionOrNull");
    Intrinsics.checkParameterIsNotNull(paramGeneratedExtension, "extension");
    if (paramExtendableMessage.hasExtension(paramGeneratedExtension)) {
      paramExtendableMessage = paramExtendableMessage.getExtension(paramGeneratedExtension);
    } else {
      paramExtendableMessage = null;
    }
    return paramExtendableMessage;
  }
  
  public static final <M extends GeneratedMessageLite.ExtendableMessage<M>, T> T getExtensionOrNull(GeneratedMessageLite.ExtendableMessage<M> paramExtendableMessage, GeneratedMessageLite.GeneratedExtension<M, List<T>> paramGeneratedExtension, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramExtendableMessage, "$this$getExtensionOrNull");
    Intrinsics.checkParameterIsNotNull(paramGeneratedExtension, "extension");
    if (paramInt < paramExtendableMessage.getExtensionCount(paramGeneratedExtension)) {
      paramExtendableMessage = paramExtendableMessage.getExtension(paramGeneratedExtension, paramInt);
    } else {
      paramExtendableMessage = null;
    }
    return paramExtendableMessage;
  }
}

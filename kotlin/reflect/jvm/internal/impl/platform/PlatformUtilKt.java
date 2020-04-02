package kotlin.reflect.jvm.internal.impl.platform;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

public final class PlatformUtilKt
{
  public static final String getPresentableDescription(TargetPlatform paramTargetPlatform)
  {
    Intrinsics.checkParameterIsNotNull(paramTargetPlatform, "$this$presentableDescription");
    return CollectionsKt.joinToString$default((Iterable)paramTargetPlatform.getComponentPlatforms(), (CharSequence)"/", null, null, 0, null, null, 62, null);
  }
}

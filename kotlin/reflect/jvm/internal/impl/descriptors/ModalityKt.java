package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.Intrinsics;

public final class ModalityKt
{
  public static final boolean isFinalClass(ClassDescriptor paramClassDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "$this$isFinalClass");
    boolean bool;
    if ((paramClassDescriptor.getModality() == Modality.FINAL) && (paramClassDescriptor.getKind() != ClassKind.ENUM_CLASS)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}

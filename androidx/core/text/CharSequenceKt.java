package androidx.core.text;

import android.text.TextUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\000\n\002\020\013\n\002\020\r\n\000\n\002\020\b\n\000\032\r\020\000\032\0020\001*\0020\002H?\b\032\r\020\003\032\0020\004*\0020\002H?\b?\006\005"}, d2={"isDigitsOnly", "", "", "trimmedLength", "", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class CharSequenceKt
{
  public static final boolean isDigitsOnly(CharSequence paramCharSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramCharSequence, "$this$isDigitsOnly");
    return TextUtils.isDigitsOnly(paramCharSequence);
  }
  
  public static final int trimmedLength(CharSequence paramCharSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramCharSequence, "$this$trimmedLength");
    return TextUtils.getTrimmedLength(paramCharSequence);
  }
}

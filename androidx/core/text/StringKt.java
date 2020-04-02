package androidx.core.text;

import android.text.TextUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\b\n\000\n\002\020\016\n\000\032\r\020\000\032\0020\001*\0020\001H?\b?\006\002"}, d2={"htmlEncode", "", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class StringKt
{
  public static final String htmlEncode(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$htmlEncode");
    paramString = TextUtils.htmlEncode(paramString);
    Intrinsics.checkExpressionValueIsNotNull(paramString, "TextUtils.htmlEncode(this)");
    return paramString;
  }
}

package androidx.core.text;

import android.text.TextUtils;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\016\n\000\n\002\020\b\n\002\030\002\n\002\b\003\"\026\020\000\032\0020\001*\0020\0028?\002?\006\006\032\004\b\003\020\004?\006\005"}, d2={"layoutDirection", "", "Ljava/util/Locale;", "getLayoutDirection", "(Ljava/util/Locale;)I", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class LocaleKt
{
  public static final int getLayoutDirection(Locale paramLocale)
  {
    Intrinsics.checkParameterIsNotNull(paramLocale, "$this$layoutDirection");
    return TextUtils.getLayoutDirectionFromLocale(paramLocale);
  }
}

package androidx.core.text;

import android.text.Html.ImageGetter;
import android.text.Html.TagHandler;
import android.text.Spanned;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\000\n\002\030\002\n\002\020\016\n\000\n\002\020\b\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\032/\020\000\032\0020\001*\0020\0022\b\b\002\020\003\032\0020\0042\n\b\002\020\005\032\004\030\0010\0062\n\b\002\020\007\032\004\030\0010\bH?\b\032\027\020\t\032\0020\002*\0020\0012\b\b\002\020\n\032\0020\004H?\b?\006\013"}, d2={"parseAsHtml", "Landroid/text/Spanned;", "", "flags", "", "imageGetter", "Landroid/text/Html$ImageGetter;", "tagHandler", "Landroid/text/Html$TagHandler;", "toHtml", "option", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class HtmlKt
{
  public static final Spanned parseAsHtml(String paramString, int paramInt, Html.ImageGetter paramImageGetter, Html.TagHandler paramTagHandler)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$parseAsHtml");
    paramString = HtmlCompat.fromHtml(paramString, paramInt, paramImageGetter, paramTagHandler);
    Intrinsics.checkExpressionValueIsNotNull(paramString, "HtmlCompat.fromHtml(this… imageGetter, tagHandler)");
    return paramString;
  }
  
  public static final String toHtml(Spanned paramSpanned, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramSpanned, "$this$toHtml");
    paramSpanned = HtmlCompat.toHtml(paramSpanned, paramInt);
    Intrinsics.checkExpressionValueIsNotNull(paramSpanned, "HtmlCompat.toHtml(this, option)");
    return paramSpanned;
  }
}

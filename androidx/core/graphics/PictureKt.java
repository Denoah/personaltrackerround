package androidx.core.graphics;

import android.graphics.Canvas;
import android.graphics.Picture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\030\002\n\000\0326\020\000\032\0020\001*\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\0032\027\020\005\032\023\022\004\022\0020\007\022\004\022\0020\b0\006?\006\002\b\tH?\b?\006\n"}, d2={"record", "Landroid/graphics/Picture;", "width", "", "height", "block", "Lkotlin/Function1;", "Landroid/graphics/Canvas;", "", "Lkotlin/ExtensionFunctionType;", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class PictureKt
{
  public static final Picture record(Picture paramPicture, int paramInt1, int paramInt2, Function1<? super Canvas, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramPicture, "$this$record");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "block");
    Canvas localCanvas = paramPicture.beginRecording(paramInt1, paramInt2);
    try
    {
      Intrinsics.checkExpressionValueIsNotNull(localCanvas, "c");
      paramFunction1.invoke(localCanvas);
      return paramPicture;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramPicture.endRecording();
      InlineMarker.finallyEnd(1);
    }
  }
}

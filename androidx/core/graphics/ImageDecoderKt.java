package androidx.core.graphics;

import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.ImageDecoder.ImageInfo;
import android.graphics.ImageDecoder.OnHeaderDecodedListener;
import android.graphics.ImageDecoder.Source;
import android.graphics.drawable.Drawable;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0000\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\002\n\002\030\002\n\000\n\002\030\002\n\000\032R\020\000\032\0020\001*\0020\0022C\b\004\020\003\032=\022\004\022\0020\005\022\023\022\0210\006?\006\f\b\007\022\b\b\b\022\004\b\b(\t\022\023\022\0210\002?\006\f\b\007\022\b\b\b\022\004\b\b(\n\022\004\022\0020\0130\004?\006\002\b\fH?\b\032R\020\r\032\0020\016*\0020\0022C\b\004\020\003\032=\022\004\022\0020\005\022\023\022\0210\006?\006\f\b\007\022\b\b\b\022\004\b\b(\t\022\023\022\0210\002?\006\f\b\007\022\b\b\b\022\004\b\b(\n\022\004\022\0020\0130\004?\006\002\b\fH?\b?\006\017"}, d2={"decodeBitmap", "Landroid/graphics/Bitmap;", "Landroid/graphics/ImageDecoder$Source;", "action", "Lkotlin/Function3;", "Landroid/graphics/ImageDecoder;", "Landroid/graphics/ImageDecoder$ImageInfo;", "Lkotlin/ParameterName;", "name", "info", "source", "", "Lkotlin/ExtensionFunctionType;", "decodeDrawable", "Landroid/graphics/drawable/Drawable;", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class ImageDecoderKt
{
  public static final Bitmap decodeBitmap(ImageDecoder.Source paramSource, Function3<? super ImageDecoder, ? super ImageDecoder.ImageInfo, ? super ImageDecoder.Source, Unit> paramFunction3)
  {
    Intrinsics.checkParameterIsNotNull(paramSource, "$this$decodeBitmap");
    Intrinsics.checkParameterIsNotNull(paramFunction3, "action");
    paramSource = ImageDecoder.decodeBitmap(paramSource, (ImageDecoder.OnHeaderDecodedListener)new ImageDecoder.OnHeaderDecodedListener()
    {
      public final void onHeaderDecoded(ImageDecoder paramAnonymousImageDecoder, ImageDecoder.ImageInfo paramAnonymousImageInfo, ImageDecoder.Source paramAnonymousSource)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousImageDecoder, "decoder");
        Intrinsics.checkParameterIsNotNull(paramAnonymousImageInfo, "info");
        Intrinsics.checkParameterIsNotNull(paramAnonymousSource, "source");
        this.$action.invoke(paramAnonymousImageDecoder, paramAnonymousImageInfo, paramAnonymousSource);
      }
    });
    Intrinsics.checkExpressionValueIsNotNull(paramSource, "ImageDecoder.decodeBitma…ction(info, source)\n    }");
    return paramSource;
  }
  
  public static final Drawable decodeDrawable(ImageDecoder.Source paramSource, Function3<? super ImageDecoder, ? super ImageDecoder.ImageInfo, ? super ImageDecoder.Source, Unit> paramFunction3)
  {
    Intrinsics.checkParameterIsNotNull(paramSource, "$this$decodeDrawable");
    Intrinsics.checkParameterIsNotNull(paramFunction3, "action");
    paramSource = ImageDecoder.decodeDrawable(paramSource, (ImageDecoder.OnHeaderDecodedListener)new ImageDecoder.OnHeaderDecodedListener()
    {
      public final void onHeaderDecoded(ImageDecoder paramAnonymousImageDecoder, ImageDecoder.ImageInfo paramAnonymousImageInfo, ImageDecoder.Source paramAnonymousSource)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousImageDecoder, "decoder");
        Intrinsics.checkParameterIsNotNull(paramAnonymousImageInfo, "info");
        Intrinsics.checkParameterIsNotNull(paramAnonymousSource, "source");
        this.$action.invoke(paramAnonymousImageDecoder, paramAnonymousImageInfo, paramAnonymousSource);
      }
    });
    Intrinsics.checkExpressionValueIsNotNull(paramSource, "ImageDecoder.decodeDrawa…ction(info, source)\n    }");
    return paramSource;
  }
}

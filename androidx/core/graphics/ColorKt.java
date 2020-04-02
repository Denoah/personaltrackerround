package androidx.core.graphics;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.ColorSpace.Named;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000>\n\000\n\002\020\b\n\002\b\002\n\002\020\007\n\002\020\t\n\002\b\004\n\002\030\002\n\002\b\005\n\002\020\013\n\002\b\b\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\004\n\002\020\016\n\002\b\002\032\r\020\030\032\0020\004*\0020\031H?\n\032\r\020\030\032\0020\001*\0020\001H?\n\032\r\020\030\032\0020\004*\0020\005H?\n\032\r\020\032\032\0020\004*\0020\031H?\n\032\r\020\032\032\0020\001*\0020\001H?\n\032\r\020\032\032\0020\004*\0020\005H?\n\032\r\020\033\032\0020\004*\0020\031H?\n\032\r\020\033\032\0020\001*\0020\001H?\n\032\r\020\033\032\0020\004*\0020\005H?\n\032\r\020\034\032\0020\004*\0020\031H?\n\032\r\020\034\032\0020\001*\0020\001H?\n\032\r\020\034\032\0020\004*\0020\005H?\n\032\025\020\035\032\0020\031*\0020\0312\006\020\t\032\0020\nH?\f\032\025\020\035\032\0020\031*\0020\0312\006\020\t\032\0020\036H?\f\032\025\020\035\032\0020\005*\0020\0012\006\020\t\032\0020\nH?\f\032\025\020\035\032\0020\005*\0020\0012\006\020\t\032\0020\036H?\f\032\025\020\035\032\0020\005*\0020\0052\006\020\t\032\0020\nH?\f\032\025\020\035\032\0020\005*\0020\0052\006\020\t\032\0020\036H?\f\032\025\020\037\032\0020\031*\0020\0312\006\020 \032\0020\031H?\002\032\r\020!\032\0020\031*\0020\001H?\b\032\r\020!\032\0020\031*\0020\005H?\b\032\r\020\"\032\0020\001*\0020\005H?\b\032\r\020\"\032\0020\001*\0020#H?\b\032\r\020$\032\0020\005*\0020\001H?\b\"\026\020\000\032\0020\001*\0020\0018?\002?\006\006\032\004\b\002\020\003\"\026\020\000\032\0020\004*\0020\0058?\002?\006\006\032\004\b\002\020\006\"\026\020\007\032\0020\001*\0020\0018?\002?\006\006\032\004\b\b\020\003\"\026\020\007\032\0020\004*\0020\0058?\002?\006\006\032\004\b\b\020\006\"\026\020\t\032\0020\n*\0020\0058?\002?\006\006\032\004\b\013\020\f\"\026\020\r\032\0020\001*\0020\0018?\002?\006\006\032\004\b\016\020\003\"\026\020\r\032\0020\004*\0020\0058?\002?\006\006\032\004\b\016\020\006\"\026\020\017\032\0020\020*\0020\0058?\002?\006\006\032\004\b\017\020\021\"\026\020\022\032\0020\020*\0020\0058?\002?\006\006\032\004\b\022\020\021\"\026\020\023\032\0020\004*\0020\0018?\002?\006\006\032\004\b\024\020\025\"\026\020\023\032\0020\004*\0020\0058?\002?\006\006\032\004\b\024\020\006\"\026\020\026\032\0020\001*\0020\0018?\002?\006\006\032\004\b\027\020\003\"\026\020\026\032\0020\004*\0020\0058?\002?\006\006\032\004\b\027\020\006?\006%"}, d2={"alpha", "", "getAlpha", "(I)I", "", "", "(J)F", "blue", "getBlue", "colorSpace", "Landroid/graphics/ColorSpace;", "getColorSpace", "(J)Landroid/graphics/ColorSpace;", "green", "getGreen", "isSrgb", "", "(J)Z", "isWideGamut", "luminance", "getLuminance", "(I)F", "red", "getRed", "component1", "Landroid/graphics/Color;", "component2", "component3", "component4", "convertTo", "Landroid/graphics/ColorSpace$Named;", "plus", "c", "toColor", "toColorInt", "", "toColorLong", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class ColorKt
{
  public static final float component1(long paramLong)
  {
    return Color.red(paramLong);
  }
  
  public static final float component1(Color paramColor)
  {
    Intrinsics.checkParameterIsNotNull(paramColor, "$this$component1");
    return paramColor.getComponent(0);
  }
  
  public static final int component1(int paramInt)
  {
    return paramInt >> 24 & 0xFF;
  }
  
  public static final float component2(long paramLong)
  {
    return Color.green(paramLong);
  }
  
  public static final float component2(Color paramColor)
  {
    Intrinsics.checkParameterIsNotNull(paramColor, "$this$component2");
    return paramColor.getComponent(1);
  }
  
  public static final int component2(int paramInt)
  {
    return paramInt >> 16 & 0xFF;
  }
  
  public static final float component3(long paramLong)
  {
    return Color.blue(paramLong);
  }
  
  public static final float component3(Color paramColor)
  {
    Intrinsics.checkParameterIsNotNull(paramColor, "$this$component3");
    return paramColor.getComponent(2);
  }
  
  public static final int component3(int paramInt)
  {
    return paramInt >> 8 & 0xFF;
  }
  
  public static final float component4(long paramLong)
  {
    return Color.alpha(paramLong);
  }
  
  public static final float component4(Color paramColor)
  {
    Intrinsics.checkParameterIsNotNull(paramColor, "$this$component4");
    return paramColor.getComponent(3);
  }
  
  public static final int component4(int paramInt)
  {
    return paramInt & 0xFF;
  }
  
  public static final long convertTo(int paramInt, ColorSpace.Named paramNamed)
  {
    Intrinsics.checkParameterIsNotNull(paramNamed, "colorSpace");
    return Color.convert(paramInt, ColorSpace.get(paramNamed));
  }
  
  public static final long convertTo(int paramInt, ColorSpace paramColorSpace)
  {
    Intrinsics.checkParameterIsNotNull(paramColorSpace, "colorSpace");
    return Color.convert(paramInt, paramColorSpace);
  }
  
  public static final long convertTo(long paramLong, ColorSpace.Named paramNamed)
  {
    Intrinsics.checkParameterIsNotNull(paramNamed, "colorSpace");
    return Color.convert(paramLong, ColorSpace.get(paramNamed));
  }
  
  public static final long convertTo(long paramLong, ColorSpace paramColorSpace)
  {
    Intrinsics.checkParameterIsNotNull(paramColorSpace, "colorSpace");
    return Color.convert(paramLong, paramColorSpace);
  }
  
  public static final Color convertTo(Color paramColor, ColorSpace.Named paramNamed)
  {
    Intrinsics.checkParameterIsNotNull(paramColor, "$this$convertTo");
    Intrinsics.checkParameterIsNotNull(paramNamed, "colorSpace");
    paramColor = paramColor.convert(ColorSpace.get(paramNamed));
    Intrinsics.checkExpressionValueIsNotNull(paramColor, "convert(ColorSpace.get(colorSpace))");
    return paramColor;
  }
  
  public static final Color convertTo(Color paramColor, ColorSpace paramColorSpace)
  {
    Intrinsics.checkParameterIsNotNull(paramColor, "$this$convertTo");
    Intrinsics.checkParameterIsNotNull(paramColorSpace, "colorSpace");
    paramColor = paramColor.convert(paramColorSpace);
    Intrinsics.checkExpressionValueIsNotNull(paramColor, "convert(colorSpace)");
    return paramColor;
  }
  
  public static final float getAlpha(long paramLong)
  {
    return Color.alpha(paramLong);
  }
  
  public static final int getAlpha(int paramInt)
  {
    return paramInt >> 24 & 0xFF;
  }
  
  public static final float getBlue(long paramLong)
  {
    return Color.blue(paramLong);
  }
  
  public static final int getBlue(int paramInt)
  {
    return paramInt & 0xFF;
  }
  
  public static final ColorSpace getColorSpace(long paramLong)
  {
    ColorSpace localColorSpace = Color.colorSpace(paramLong);
    Intrinsics.checkExpressionValueIsNotNull(localColorSpace, "Color.colorSpace(this)");
    return localColorSpace;
  }
  
  public static final float getGreen(long paramLong)
  {
    return Color.green(paramLong);
  }
  
  public static final int getGreen(int paramInt)
  {
    return paramInt >> 8 & 0xFF;
  }
  
  public static final float getLuminance(int paramInt)
  {
    return Color.luminance(paramInt);
  }
  
  public static final float getLuminance(long paramLong)
  {
    return Color.luminance(paramLong);
  }
  
  public static final float getRed(long paramLong)
  {
    return Color.red(paramLong);
  }
  
  public static final int getRed(int paramInt)
  {
    return paramInt >> 16 & 0xFF;
  }
  
  public static final boolean isSrgb(long paramLong)
  {
    return Color.isSrgb(paramLong);
  }
  
  public static final boolean isWideGamut(long paramLong)
  {
    return Color.isWideGamut(paramLong);
  }
  
  public static final Color plus(Color paramColor1, Color paramColor2)
  {
    Intrinsics.checkParameterIsNotNull(paramColor1, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramColor2, "c");
    paramColor1 = ColorUtils.compositeColors(paramColor2, paramColor1);
    Intrinsics.checkExpressionValueIsNotNull(paramColor1, "ColorUtils.compositeColors(c, this)");
    return paramColor1;
  }
  
  public static final Color toColor(int paramInt)
  {
    Color localColor = Color.valueOf(paramInt);
    Intrinsics.checkExpressionValueIsNotNull(localColor, "Color.valueOf(this)");
    return localColor;
  }
  
  public static final Color toColor(long paramLong)
  {
    Color localColor = Color.valueOf(paramLong);
    Intrinsics.checkExpressionValueIsNotNull(localColor, "Color.valueOf(this)");
    return localColor;
  }
  
  public static final int toColorInt(long paramLong)
  {
    return Color.toArgb(paramLong);
  }
  
  public static final int toColorInt(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toColorInt");
    return Color.parseColor(paramString);
  }
  
  public static final long toColorLong(int paramInt)
  {
    return Color.pack(paramInt);
  }
}

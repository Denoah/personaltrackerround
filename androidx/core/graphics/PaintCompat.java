package androidx.core.graphics;

import android.graphics.BlendMode;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build.VERSION;
import androidx.core.util.Pair;

public final class PaintCompat
{
  private static final String EM_STRING = "m";
  private static final String TOFU_STRING = "?";
  private static final ThreadLocal<Pair<Rect, Rect>> sRectThreadLocal = new ThreadLocal();
  
  private PaintCompat() {}
  
  public static boolean hasGlyph(Paint paramPaint, String paramString)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return paramPaint.hasGlyph(paramString);
    }
    int i = paramString.length();
    if ((i == 1) && (Character.isWhitespace(paramString.charAt(0)))) {
      return true;
    }
    float f1 = paramPaint.measureText("?");
    float f2 = paramPaint.measureText("m");
    float f3 = paramPaint.measureText(paramString);
    float f4 = 0.0F;
    if (f3 == 0.0F) {
      return false;
    }
    if (paramString.codePointCount(0, paramString.length()) > 1)
    {
      if (f3 > f2 * 2.0F) {
        return false;
      }
      int k;
      for (int j = 0; j < i; j = k)
      {
        k = Character.charCount(paramString.codePointAt(j)) + j;
        f4 += paramPaint.measureText(paramString, j, k);
      }
      if (f3 >= f4) {
        return false;
      }
    }
    if (f3 != f1) {
      return true;
    }
    Pair localPair = obtainEmptyRects();
    paramPaint.getTextBounds("?", 0, 2, (Rect)localPair.first);
    paramPaint.getTextBounds(paramString, 0, i, (Rect)localPair.second);
    return ((Rect)localPair.first).equals(localPair.second) ^ true;
  }
  
  private static Pair<Rect, Rect> obtainEmptyRects()
  {
    Pair localPair = (Pair)sRectThreadLocal.get();
    if (localPair == null)
    {
      localPair = new Pair(new Rect(), new Rect());
      sRectThreadLocal.set(localPair);
    }
    else
    {
      ((Rect)localPair.first).setEmpty();
      ((Rect)localPair.second).setEmpty();
    }
    return localPair;
  }
  
  public static boolean setBlendMode(Paint paramPaint, BlendModeCompat paramBlendModeCompat)
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool = true;
    Object localObject = null;
    PorterDuff.Mode localMode = null;
    if (i >= 29)
    {
      localObject = localMode;
      if (paramBlendModeCompat != null) {
        localObject = BlendModeUtils.obtainBlendModeFromCompat(paramBlendModeCompat);
      }
      paramPaint.setBlendMode((BlendMode)localObject);
      return true;
    }
    if (paramBlendModeCompat != null)
    {
      localMode = BlendModeUtils.obtainPorterDuffFromCompat(paramBlendModeCompat);
      paramBlendModeCompat = (BlendModeCompat)localObject;
      if (localMode != null) {
        paramBlendModeCompat = new PorterDuffXfermode(localMode);
      }
      paramPaint.setXfermode(paramBlendModeCompat);
      if (localMode == null) {
        bool = false;
      }
      return bool;
    }
    paramPaint.setXfermode(null);
    return true;
  }
}

package androidx.core.graphics;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.os.Build.VERSION;

public class BlendModeColorFilterCompat
{
  private BlendModeColorFilterCompat() {}
  
  public static ColorFilter createBlendModeColorFilterCompat(int paramInt, BlendModeCompat paramBlendModeCompat)
  {
    int i = Build.VERSION.SDK_INT;
    BlendMode localBlendMode = null;
    PorterDuff.Mode localMode = null;
    if (i >= 29)
    {
      localBlendMode = BlendModeUtils.obtainBlendModeFromCompat(paramBlendModeCompat);
      paramBlendModeCompat = localMode;
      if (localBlendMode != null) {
        paramBlendModeCompat = new BlendModeColorFilter(paramInt, localBlendMode);
      }
      return paramBlendModeCompat;
    }
    localMode = BlendModeUtils.obtainPorterDuffFromCompat(paramBlendModeCompat);
    paramBlendModeCompat = localBlendMode;
    if (localMode != null) {
      paramBlendModeCompat = new PorterDuffColorFilter(paramInt, localMode);
    }
    return paramBlendModeCompat;
  }
}

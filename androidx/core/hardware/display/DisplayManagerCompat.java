package androidx.core.hardware.display;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Build.VERSION;
import android.view.Display;
import android.view.WindowManager;
import java.util.WeakHashMap;

public final class DisplayManagerCompat
{
  public static final String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
  private static final WeakHashMap<Context, DisplayManagerCompat> sInstances = new WeakHashMap();
  private final Context mContext;
  
  private DisplayManagerCompat(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public static DisplayManagerCompat getInstance(Context paramContext)
  {
    synchronized (sInstances)
    {
      DisplayManagerCompat localDisplayManagerCompat1 = (DisplayManagerCompat)sInstances.get(paramContext);
      DisplayManagerCompat localDisplayManagerCompat2 = localDisplayManagerCompat1;
      if (localDisplayManagerCompat1 == null)
      {
        localDisplayManagerCompat2 = new androidx/core/hardware/display/DisplayManagerCompat;
        localDisplayManagerCompat2.<init>(paramContext);
        sInstances.put(paramContext, localDisplayManagerCompat2);
      }
      return localDisplayManagerCompat2;
    }
  }
  
  public Display getDisplay(int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return ((DisplayManager)this.mContext.getSystemService("display")).getDisplay(paramInt);
    }
    Display localDisplay = ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay();
    if (localDisplay.getDisplayId() == paramInt) {
      return localDisplay;
    }
    return null;
  }
  
  public Display[] getDisplays()
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return ((DisplayManager)this.mContext.getSystemService("display")).getDisplays();
    }
    return new Display[] { ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay() };
  }
  
  public Display[] getDisplays(String paramString)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return ((DisplayManager)this.mContext.getSystemService("display")).getDisplays(paramString);
    }
    if (paramString == null) {
      return new Display[0];
    }
    return new Display[] { ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay() };
  }
}

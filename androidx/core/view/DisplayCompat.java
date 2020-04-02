package androidx.core.view;

import android.app.UiModeManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.Display;
import android.view.Display.Mode;
import androidx.core.util.Preconditions;
import java.lang.reflect.Method;
import java.util.ArrayList;

public final class DisplayCompat
{
  private static final int DISPLAY_SIZE_4K_HEIGHT = 2160;
  private static final int DISPLAY_SIZE_4K_WIDTH = 3840;
  
  private DisplayCompat() {}
  
  private static Point getPhysicalDisplaySize(Context paramContext, Display paramDisplay)
  {
    Point localPoint;
    if (Build.VERSION.SDK_INT < 28) {
      localPoint = parsePhysicalDisplaySizeFromSystemProperties("sys.display-size", paramDisplay);
    } else {
      localPoint = parsePhysicalDisplaySizeFromSystemProperties("vendor.display-size", paramDisplay);
    }
    if (localPoint != null) {
      return localPoint;
    }
    if (isSonyBravia4kTv(paramContext)) {
      return new Point(3840, 2160);
    }
    paramContext = new Point();
    if (Build.VERSION.SDK_INT >= 23)
    {
      paramDisplay = paramDisplay.getMode();
      paramContext.x = paramDisplay.getPhysicalWidth();
      paramContext.y = paramDisplay.getPhysicalHeight();
    }
    else if (Build.VERSION.SDK_INT >= 17)
    {
      paramDisplay.getRealSize(paramContext);
    }
    else
    {
      paramDisplay.getSize(paramContext);
    }
    return paramContext;
  }
  
  public static ModeCompat[] getSupportedModes(Context paramContext, Display paramDisplay)
  {
    paramContext = getPhysicalDisplaySize(paramContext, paramDisplay);
    if (Build.VERSION.SDK_INT >= 23)
    {
      paramDisplay = paramDisplay.getSupportedModes();
      ArrayList localArrayList = new ArrayList(paramDisplay.length);
      int i = 0;
      int j = i;
      while (i < paramDisplay.length)
      {
        if (physicalSizeEquals(paramDisplay[i], paramContext))
        {
          localArrayList.add(i, new ModeCompat(paramDisplay[i], true));
          j = 1;
        }
        else
        {
          localArrayList.add(i, new ModeCompat(paramDisplay[i], false));
        }
        i++;
      }
      if (j == 0) {
        localArrayList.add(new ModeCompat(paramContext));
      }
      return (ModeCompat[])localArrayList.toArray(new ModeCompat[0]);
    }
    return new ModeCompat[] { new ModeCompat(paramContext) };
  }
  
  private static String getSystemProperty(String paramString)
  {
    try
    {
      Class localClass = Class.forName("android.os.SystemProperties");
      paramString = (String)localClass.getMethod("get", new Class[] { String.class }).invoke(localClass, new Object[] { paramString });
      return paramString;
    }
    catch (Exception paramString) {}
    return null;
  }
  
  private static boolean isSonyBravia4kTv(Context paramContext)
  {
    boolean bool;
    if ((isTv(paramContext)) && ("Sony".equals(Build.MANUFACTURER)) && (Build.MODEL.startsWith("BRAVIA")) && (paramContext.getPackageManager().hasSystemFeature("com.sony.dtv.hardware.panel.qfhd"))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static boolean isTv(Context paramContext)
  {
    paramContext = (UiModeManager)paramContext.getSystemService("uimode");
    boolean bool;
    if ((paramContext != null) && (paramContext.getCurrentModeType() == 4)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static Point parseDisplaySize(String paramString)
    throws NumberFormatException
  {
    paramString = paramString.trim().split("x", -1);
    if (paramString.length == 2)
    {
      int i = Integer.parseInt(paramString[0]);
      int j = Integer.parseInt(paramString[1]);
      if ((i > 0) && (j > 0)) {
        return new Point(i, j);
      }
    }
    throw new NumberFormatException();
  }
  
  private static Point parsePhysicalDisplaySizeFromSystemProperties(String paramString, Display paramDisplay)
  {
    if (paramDisplay.getDisplayId() == 0)
    {
      paramString = getSystemProperty(paramString);
      if (TextUtils.isEmpty(paramString)) {}
    }
    try
    {
      paramString = parseDisplaySize(paramString);
      return paramString;
    }
    catch (NumberFormatException paramString)
    {
      for (;;) {}
    }
    return null;
  }
  
  private static boolean physicalSizeEquals(Display.Mode paramMode, Point paramPoint)
  {
    boolean bool;
    if (((paramMode.getPhysicalWidth() == paramPoint.x) && (paramMode.getPhysicalHeight() == paramPoint.y)) || ((paramMode.getPhysicalWidth() == paramPoint.y) && (paramMode.getPhysicalHeight() == paramPoint.x))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final class ModeCompat
  {
    private final boolean mIsNative;
    private final Display.Mode mMode;
    private final Point mPhysicalDisplaySize;
    
    ModeCompat(Point paramPoint)
    {
      Preconditions.checkNotNull(paramPoint, "physicalDisplaySize == null");
      this.mIsNative = true;
      this.mPhysicalDisplaySize = paramPoint;
      this.mMode = null;
    }
    
    ModeCompat(Display.Mode paramMode, boolean paramBoolean)
    {
      Preconditions.checkNotNull(paramMode, "Display.Mode == null, can't wrap a null reference");
      this.mIsNative = paramBoolean;
      this.mPhysicalDisplaySize = new Point(paramMode.getPhysicalWidth(), paramMode.getPhysicalHeight());
      this.mMode = paramMode;
    }
    
    public int getPhysicalHeight()
    {
      return this.mPhysicalDisplaySize.y;
    }
    
    public int getPhysicalWidth()
    {
      return this.mPhysicalDisplaySize.x;
    }
    
    public boolean isNative()
    {
      return this.mIsNative;
    }
    
    public Display.Mode toMode()
    {
      return this.mMode;
    }
  }
}

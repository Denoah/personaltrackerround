package androidx.core.content.pm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.os.Build.VERSION;
import android.text.TextUtils;
import androidx.core.content.ContextCompat;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShortcutManagerCompat
{
  static final String ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
  public static final String EXTRA_SHORTCUT_ID = "android.intent.extra.shortcut.ID";
  static final String INSTALL_SHORTCUT_PERMISSION = "com.android.launcher.permission.INSTALL_SHORTCUT";
  private static volatile ShortcutInfoCompatSaver<?> sShortcutInfoCompatSaver;
  
  private ShortcutManagerCompat() {}
  
  public static boolean addDynamicShortcuts(Context paramContext, List<ShortcutInfoCompat> paramList)
  {
    if (Build.VERSION.SDK_INT >= 25)
    {
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext()) {
        localArrayList.add(((ShortcutInfoCompat)localIterator.next()).toShortcutInfo());
      }
      if (!((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).addDynamicShortcuts(localArrayList)) {
        return false;
      }
    }
    getShortcutInfoSaverInstance(paramContext).addShortcuts(paramList);
    return true;
  }
  
  public static Intent createShortcutResultIntent(Context paramContext, ShortcutInfoCompat paramShortcutInfoCompat)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      paramContext = ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).createShortcutResultIntent(paramShortcutInfoCompat.toShortcutInfo());
    } else {
      paramContext = null;
    }
    Object localObject = paramContext;
    if (paramContext == null) {
      localObject = new Intent();
    }
    return paramShortcutInfoCompat.addToIntent((Intent)localObject);
  }
  
  public static List<ShortcutInfoCompat> getDynamicShortcuts(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 25)
    {
      Object localObject = ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).getDynamicShortcuts();
      ArrayList localArrayList = new ArrayList(((List)localObject).size());
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        localArrayList.add(new ShortcutInfoCompat.Builder(paramContext, (ShortcutInfo)((Iterator)localObject).next()).build());
      }
      return localArrayList;
    }
    try
    {
      paramContext = getShortcutInfoSaverInstance(paramContext).getShortcuts();
      return paramContext;
    }
    catch (Exception paramContext) {}
    return new ArrayList();
  }
  
  public static int getMaxShortcutCountPerActivity(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 25) {
      return ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).getMaxShortcutCountPerActivity();
    }
    return 0;
  }
  
  private static ShortcutInfoCompatSaver<?> getShortcutInfoSaverInstance(Context paramContext)
  {
    if ((sShortcutInfoCompatSaver != null) || (Build.VERSION.SDK_INT >= 23)) {}
    try
    {
      sShortcutInfoCompatSaver = (ShortcutInfoCompatSaver)Class.forName("androidx.sharetarget.ShortcutInfoCompatSaverImpl", false, ShortcutManagerCompat.class.getClassLoader()).getMethod("getInstance", new Class[] { Context.class }).invoke(null, new Object[] { paramContext });
      if (sShortcutInfoCompatSaver == null) {
        sShortcutInfoCompatSaver = new ShortcutInfoCompatSaver.NoopImpl();
      }
      return sShortcutInfoCompatSaver;
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
  }
  
  public static boolean isRequestPinShortcutSupported(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).isRequestPinShortcutSupported();
    }
    if (ContextCompat.checkSelfPermission(paramContext, "com.android.launcher.permission.INSTALL_SHORTCUT") != 0) {
      return false;
    }
    paramContext = paramContext.getPackageManager().queryBroadcastReceivers(new Intent("com.android.launcher.action.INSTALL_SHORTCUT"), 0).iterator();
    while (paramContext.hasNext())
    {
      String str = ((ResolveInfo)paramContext.next()).activityInfo.permission;
      if ((TextUtils.isEmpty(str)) || ("com.android.launcher.permission.INSTALL_SHORTCUT".equals(str))) {
        return true;
      }
    }
    return false;
  }
  
  public static void removeAllDynamicShortcuts(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 25) {
      ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).removeAllDynamicShortcuts();
    }
    getShortcutInfoSaverInstance(paramContext).removeAllShortcuts();
  }
  
  public static void removeDynamicShortcuts(Context paramContext, List<String> paramList)
  {
    if (Build.VERSION.SDK_INT >= 25) {
      ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).removeDynamicShortcuts(paramList);
    }
    getShortcutInfoSaverInstance(paramContext).removeShortcuts(paramList);
  }
  
  public static boolean requestPinShortcut(Context paramContext, ShortcutInfoCompat paramShortcutInfoCompat, IntentSender paramIntentSender)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).requestPinShortcut(paramShortcutInfoCompat.toShortcutInfo(), paramIntentSender);
    }
    if (!isRequestPinShortcutSupported(paramContext)) {
      return false;
    }
    paramShortcutInfoCompat = paramShortcutInfoCompat.addToIntent(new Intent("com.android.launcher.action.INSTALL_SHORTCUT"));
    if (paramIntentSender == null)
    {
      paramContext.sendBroadcast(paramShortcutInfoCompat);
      return true;
    }
    paramContext.sendOrderedBroadcast(paramShortcutInfoCompat, null, new BroadcastReceiver()
    {
      public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
      {
        try
        {
          this.val$callback.sendIntent(paramAnonymousContext, 0, null, null, null);
          return;
        }
        catch (IntentSender.SendIntentException paramAnonymousContext)
        {
          for (;;) {}
        }
      }
    }, null, -1, null, null);
    return true;
  }
  
  public static boolean updateShortcuts(Context paramContext, List<ShortcutInfoCompat> paramList)
  {
    if (Build.VERSION.SDK_INT >= 25)
    {
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext()) {
        localArrayList.add(((ShortcutInfoCompat)localIterator.next()).toShortcutInfo());
      }
      if (!((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).updateShortcuts(localArrayList)) {
        return false;
      }
    }
    getShortcutInfoSaverInstance(paramContext).addShortcuts(paramList);
    return true;
  }
}

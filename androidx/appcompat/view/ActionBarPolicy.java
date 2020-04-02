package androidx.appcompat.view;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.ViewConfiguration;
import androidx.appcompat.R.attr;
import androidx.appcompat.R.bool;
import androidx.appcompat.R.dimen;
import androidx.appcompat.R.styleable;

public class ActionBarPolicy
{
  private Context mContext;
  
  private ActionBarPolicy(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public static ActionBarPolicy get(Context paramContext)
  {
    return new ActionBarPolicy(paramContext);
  }
  
  public boolean enableHomeButtonByDefault()
  {
    boolean bool;
    if (this.mContext.getApplicationInfo().targetSdkVersion < 14) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public int getEmbeddedMenuWidthLimit()
  {
    return this.mContext.getResources().getDisplayMetrics().widthPixels / 2;
  }
  
  public int getMaxActionButtons()
  {
    Configuration localConfiguration = this.mContext.getResources().getConfiguration();
    int i = localConfiguration.screenWidthDp;
    int j = localConfiguration.screenHeightDp;
    if ((localConfiguration.smallestScreenWidthDp <= 600) && (i <= 600) && ((i <= 960) || (j <= 720)) && ((i <= 720) || (j <= 960)))
    {
      if ((i < 500) && ((i <= 640) || (j <= 480)) && ((i <= 480) || (j <= 640)))
      {
        if (i >= 360) {
          return 3;
        }
        return 2;
      }
      return 4;
    }
    return 5;
  }
  
  public int getStackedTabMaxWidth()
  {
    return this.mContext.getResources().getDimensionPixelSize(R.dimen.abc_action_bar_stacked_tab_max_width);
  }
  
  public int getTabContainerHeight()
  {
    TypedArray localTypedArray = this.mContext.obtainStyledAttributes(null, R.styleable.ActionBar, R.attr.actionBarStyle, 0);
    int i = localTypedArray.getLayoutDimension(R.styleable.ActionBar_height, 0);
    Resources localResources = this.mContext.getResources();
    int j = i;
    if (!hasEmbeddedTabs()) {
      j = Math.min(i, localResources.getDimensionPixelSize(R.dimen.abc_action_bar_stacked_max_height));
    }
    localTypedArray.recycle();
    return j;
  }
  
  public boolean hasEmbeddedTabs()
  {
    return this.mContext.getResources().getBoolean(R.bool.abc_action_bar_embed_tabs);
  }
  
  public boolean showsOverflowMenuButton()
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return true;
    }
    return ViewConfiguration.get(this.mContext).hasPermanentMenuKey() ^ true;
  }
}

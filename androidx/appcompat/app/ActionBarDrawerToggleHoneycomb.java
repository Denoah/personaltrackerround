package androidx.appcompat.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.lang.reflect.Method;

class ActionBarDrawerToggleHoneycomb
{
  private static final String TAG = "ActionBarDrawerToggleHC";
  private static final int[] THEME_ATTRS = { 16843531 };
  
  private ActionBarDrawerToggleHoneycomb() {}
  
  public static Drawable getThemeUpIndicator(Activity paramActivity)
  {
    paramActivity = paramActivity.obtainStyledAttributes(THEME_ATTRS);
    Drawable localDrawable = paramActivity.getDrawable(0);
    paramActivity.recycle();
    return localDrawable;
  }
  
  public static SetIndicatorInfo setActionBarDescription(SetIndicatorInfo paramSetIndicatorInfo, Activity paramActivity, int paramInt)
  {
    SetIndicatorInfo localSetIndicatorInfo = paramSetIndicatorInfo;
    if (paramSetIndicatorInfo == null) {
      localSetIndicatorInfo = new SetIndicatorInfo(paramActivity);
    }
    if (localSetIndicatorInfo.setHomeAsUpIndicator != null) {
      try
      {
        paramSetIndicatorInfo = paramActivity.getActionBar();
        localSetIndicatorInfo.setHomeActionContentDescription.invoke(paramSetIndicatorInfo, new Object[] { Integer.valueOf(paramInt) });
        if (Build.VERSION.SDK_INT <= 19) {
          paramSetIndicatorInfo.setSubtitle(paramSetIndicatorInfo.getSubtitle());
        }
      }
      catch (Exception paramSetIndicatorInfo)
      {
        Log.w("ActionBarDrawerToggleHC", "Couldn't set content description via JB-MR2 API", paramSetIndicatorInfo);
      }
    }
    return localSetIndicatorInfo;
  }
  
  public static SetIndicatorInfo setActionBarUpIndicator(Activity paramActivity, Drawable paramDrawable, int paramInt)
  {
    SetIndicatorInfo localSetIndicatorInfo = new SetIndicatorInfo(paramActivity);
    if (localSetIndicatorInfo.setHomeAsUpIndicator != null) {
      try
      {
        paramActivity = paramActivity.getActionBar();
        localSetIndicatorInfo.setHomeAsUpIndicator.invoke(paramActivity, new Object[] { paramDrawable });
        localSetIndicatorInfo.setHomeActionContentDescription.invoke(paramActivity, new Object[] { Integer.valueOf(paramInt) });
      }
      catch (Exception paramActivity)
      {
        Log.w("ActionBarDrawerToggleHC", "Couldn't set home-as-up indicator via JB-MR2 API", paramActivity);
      }
    } else if (localSetIndicatorInfo.upIndicatorView != null) {
      localSetIndicatorInfo.upIndicatorView.setImageDrawable(paramDrawable);
    } else {
      Log.w("ActionBarDrawerToggleHC", "Couldn't set home-as-up indicator");
    }
    return localSetIndicatorInfo;
  }
  
  static class SetIndicatorInfo
  {
    public Method setHomeActionContentDescription;
    public Method setHomeAsUpIndicator;
    public ImageView upIndicatorView;
    
    SetIndicatorInfo(Activity paramActivity)
    {
      try
      {
        this.setHomeAsUpIndicator = ActionBar.class.getDeclaredMethod("setHomeAsUpIndicator", new Class[] { Drawable.class });
        this.setHomeActionContentDescription = ActionBar.class.getDeclaredMethod("setHomeActionContentDescription", new Class[] { Integer.TYPE });
        return;
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        paramActivity = paramActivity.findViewById(16908332);
        if (paramActivity == null) {
          return;
        }
        paramActivity = (ViewGroup)paramActivity.getParent();
        if (paramActivity.getChildCount() != 2) {
          return;
        }
        View localView1 = paramActivity.getChildAt(0);
        View localView2 = paramActivity.getChildAt(1);
        paramActivity = localView1;
        if (localView1.getId() == 16908332) {
          paramActivity = localView2;
        }
        if ((paramActivity instanceof ImageView)) {
          this.upIndicatorView = ((ImageView)paramActivity);
        }
      }
    }
  }
}

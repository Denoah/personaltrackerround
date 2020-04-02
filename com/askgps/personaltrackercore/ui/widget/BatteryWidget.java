package com.askgps.personaltrackercore.ui.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentManager;
import com.askgps.personaltrackercore.BaseMainActivity;
import com.askgps.personaltrackercore.BaseMainActivity.Companion;
import com.askgps.personaltrackercore.LogKt;
import com.askgps.personaltrackercore.R.drawable;
import com.askgps.personaltrackercore.R.id;
import com.askgps.personaltrackercore.R.layout;
import com.askgps.personaltrackercore.R.string;
import com.askgps.personaltrackercore.config.CustomerCategory;
import com.askgps.personaltrackercore.ui.infoscreen.InfoFragment;
import com.askgps.personaltrackercore.ui.infoscreen.InfoFragment.Companion;
import com.google.android.material.textview.MaterialTextView;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000U\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\002\n\002\020\016\n\000\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\005\n\002\030\002\n\000\n\002\020\013\n\002\b\005\n\002\020\002\n\002\b\002*\001\016\030\0002\0020\001B\017\b\026\022\006\020\002\032\0020\003?\006\002\020\004B\031\b\026\022\006\020\002\032\0020\003\022\b\020\005\032\004\030\0010\006?\006\002\020\007B!\b\026\022\006\020\002\032\0020\003\022\b\020\005\032\004\030\0010\006\022\006\020\b\032\0020\t?\006\002\020\nJ&\020\033\032\0020\0342\006\020\035\032\0020\t2\006\020\036\032\0020\t2\006\020\037\032\0020\t2\006\020 \032\0020\tJ\006\020!\032\0020\"J\006\020#\032\0020\"R\016\020\013\032\0020\fX?D?\006\002\n\000R\020\020\r\032\0020\016X?\004?\006\004\n\002\020\017R\016\020\020\032\0020\021X?\004?\006\002\n\000R\016\020\022\032\0020\tX?\016?\006\002\n\000R\032\020\023\032\0020\024X?.?\006\016\n\000\032\004\b\025\020\026\"\004\b\027\020\030R\016\020\031\032\0020\032X?\004?\006\002\n\000?\006$"}, d2={"Lcom/askgps/personaltrackercore/ui/widget/BatteryWidget;", "Landroid/widget/FrameLayout;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyle", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "TAG", "", "batteryReceiver", "com/askgps/personaltrackercore/ui/widget/BatteryWidget$batteryReceiver$1", "Lcom/askgps/personaltrackercore/ui/widget/BatteryWidget$batteryReceiver$1;", "iFilter", "Landroid/content/IntentFilter;", "lastBatteryValue", "parentFragmentManager", "Landroidx/fragment/app/FragmentManager;", "getParentFragmentManager", "()Landroidx/fragment/app/FragmentManager;", "setParentFragmentManager", "(Landroidx/fragment/app/FragmentManager;)V", "view", "Landroid/view/View;", "checkBatteryLow", "", "lastValue", "currValue", "alarmValue", "strId", "registerBatteryReceiver", "", "unregisterBatteryReceiver", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class BatteryWidget
  extends FrameLayout
{
  private final String TAG = "BatteryWidget";
  private HashMap _$_findViewCache;
  private final batteryReceiver.1 batteryReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      Intrinsics.checkParameterIsNotNull(paramAnonymousContext, "context");
      Intrinsics.checkParameterIsNotNull(paramAnonymousIntent, "intent");
      try
      {
        int i = paramAnonymousIntent.getIntExtra("level", -1);
        Object localObject = BatteryWidget.access$getTAG$p(this.this$0);
        paramAnonymousContext = new java/lang/StringBuilder;
        paramAnonymousContext.<init>();
        paramAnonymousContext.append("Last battery level: ");
        paramAnonymousContext.append(BatteryWidget.access$getLastBatteryValue$p(this.this$0));
        Log.e((String)localObject, paramAnonymousContext.toString());
        if ((!this.this$0.checkBatteryLow(BatteryWidget.access$getLastBatteryValue$p(this.this$0), i, 5, R.string.low_battery_5)) && (!this.this$0.checkBatteryLow(BatteryWidget.access$getLastBatteryValue$p(this.this$0), i, 10, R.string.low_battery_10))) {
          this.this$0.checkBatteryLow(BatteryWidget.access$getLastBatteryValue$p(this.this$0), i, 20, R.string.low_battery_20);
        }
        BatteryWidget.access$setLastBatteryValue$p(this.this$0, i);
        if (i != -1)
        {
          localObject = (MaterialTextView)this.this$0._$_findCachedViewById(R.id.battery_widget_tv_percent);
          paramAnonymousContext = new com/askgps/personaltrackercore/ui/widget/BatteryWidget$batteryReceiver$1$onReceive$1;
          paramAnonymousContext.<init>(this, i);
          ((MaterialTextView)localObject).post((Runnable)paramAnonymousContext);
        }
        i = paramAnonymousIntent.getIntExtra("status", -1);
        if ((i != 2) && (i != 5)) {
          i = 0;
        } else {
          i = 1;
        }
        if (i != 0)
        {
          paramAnonymousContext = (AppCompatImageView)this.this$0._$_findCachedViewById(R.id.chargingIcon);
          Intrinsics.checkExpressionValueIsNotNull(paramAnonymousContext, "chargingIcon");
          paramAnonymousContext.setVisibility(0);
        }
        else
        {
          paramAnonymousContext = (AppCompatImageView)this.this$0._$_findCachedViewById(R.id.chargingIcon);
          Intrinsics.checkExpressionValueIsNotNull(paramAnonymousContext, "chargingIcon");
          paramAnonymousContext.setVisibility(4);
        }
      }
      catch (Exception paramAnonymousContext)
      {
        LogKt.toFile$default(paramAnonymousContext.getMessage(), "ACTION_BATTERY_CHANGED Exception", null, null, 6, null);
      }
    }
  };
  private final IntentFilter iFilter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
  private int lastBatteryValue = 100;
  public FragmentManager parentFragmentManager;
  private final View view;
  
  public BatteryWidget(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public BatteryWidget(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public BatteryWidget(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    paramContext = LayoutInflater.from(paramContext).inflate(R.layout.battery_widget, (ViewGroup)this, true);
    Intrinsics.checkExpressionValueIsNotNull(paramContext, "LayoutInflater.from(cont…ttery_widget, this, true)");
    this.view = paramContext;
  }
  
  public void _$_clearFindViewByIdCache()
  {
    HashMap localHashMap = this._$_findViewCache;
    if (localHashMap != null) {
      localHashMap.clear();
    }
  }
  
  public View _$_findCachedViewById(int paramInt)
  {
    if (this._$_findViewCache == null) {
      this._$_findViewCache = new HashMap();
    }
    View localView1 = (View)this._$_findViewCache.get(Integer.valueOf(paramInt));
    View localView2 = localView1;
    if (localView1 == null)
    {
      localView2 = findViewById(paramInt);
      this._$_findViewCache.put(Integer.valueOf(paramInt), localView2);
    }
    return localView2;
  }
  
  public final boolean checkBatteryLow(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt1 >= paramInt3) && (paramInt2 < paramInt3))
    {
      Object localObject1 = this.TAG;
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("notify ");
      ((StringBuilder)localObject2).append(paramInt3);
      Log.e((String)localObject1, ((StringBuilder)localObject2).toString());
      boolean bool;
      if (BaseMainActivity.Companion.getCustomer() != CustomerCategory.BUILDER_WATCH) {
        bool = true;
      } else {
        bool = false;
      }
      localObject1 = InfoFragment.Companion;
      localObject2 = this.parentFragmentManager;
      if (localObject2 == null) {
        Intrinsics.throwUninitializedPropertyAccessException("parentFragmentManager");
      }
      InfoFragment.Companion.showBatteryLowDialog$default((InfoFragment.Companion)localObject1, (FragmentManager)localObject2, paramInt4, bool, null, 8, null);
      return true;
    }
    return false;
  }
  
  public final FragmentManager getParentFragmentManager()
  {
    FragmentManager localFragmentManager = this.parentFragmentManager;
    if (localFragmentManager == null) {
      Intrinsics.throwUninitializedPropertyAccessException("parentFragmentManager");
    }
    return localFragmentManager;
  }
  
  public final void registerBatteryReceiver()
  {
    Context localContext = getContext();
    if (localContext != null) {
      localContext.registerReceiver((BroadcastReceiver)this.batteryReceiver, this.iFilter);
    }
  }
  
  public final void setParentFragmentManager(FragmentManager paramFragmentManager)
  {
    Intrinsics.checkParameterIsNotNull(paramFragmentManager, "<set-?>");
    this.parentFragmentManager = paramFragmentManager;
  }
  
  public final void unregisterBatteryReceiver()
  {
    Context localContext = getContext();
    if (localContext != null) {
      localContext.unregisterReceiver((BroadcastReceiver)this.batteryReceiver);
    }
  }
}

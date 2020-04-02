package com.askgps.personaltrackercore.ui.statusbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.askgps.personaltrackercore.R.id;
import com.askgps.personaltrackercore.R.layout;
import com.askgps.personaltrackercore.ui.widget.BatteryWidget;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\030\000 \0202\0020\001:\001\020B\005?\006\002\020\002J\022\020\005\032\0020\0062\b\020\007\032\004\030\0010\bH\026J&\020\t\032\004\030\0010\n2\006\020\013\032\0020\f2\b\020\r\032\004\030\0010\0162\b\020\007\032\004\030\0010\bH\026J\b\020\017\032\0020\006H\026R\016\020\003\032\0020\004X?.?\006\002\n\000?\006\021"}, d2={"Lcom/askgps/personaltrackercore/ui/statusbar/StatusBarFragment;", "Landroidx/fragment/app/Fragment;", "()V", "viewModel", "Lcom/askgps/personaltrackercore/ui/statusbar/StatusBarViewModel;", "onActivityCreated", "", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDestroyView", "Companion", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class StatusBarFragment
  extends Fragment
{
  public static final Companion Companion = new Companion(null);
  private HashMap _$_findViewCache;
  private StatusBarViewModel viewModel;
  
  public StatusBarFragment() {}
  
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
      localView2 = getView();
      if (localView2 == null) {
        return null;
      }
      localView2 = localView2.findViewById(paramInt);
      this._$_findViewCache.put(Integer.valueOf(paramInt), localView2);
    }
    return localView2;
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    paramBundle = ViewModelProviders.of((Fragment)this).get(StatusBarViewModel.class);
    Intrinsics.checkExpressionValueIsNotNull(paramBundle, "ViewModelProviders.of(th…BarViewModel::class.java)");
    this.viewModel = ((StatusBarViewModel)paramBundle);
    paramBundle = (BatteryWidget)_$_findCachedViewById(R.id.status_bar_battery_widget);
    if (paramBundle != null) {
      paramBundle.registerBatteryReceiver();
    }
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    Intrinsics.checkParameterIsNotNull(paramLayoutInflater, "inflater");
    return paramLayoutInflater.inflate(R.layout.status_bar_fragment, paramViewGroup, false);
  }
  
  public void onDestroyView()
  {
    BatteryWidget localBatteryWidget = (BatteryWidget)_$_findCachedViewById(R.id.status_bar_battery_widget);
    if (localBatteryWidget != null) {
      localBatteryWidget.unregisterBatteryReceiver();
    }
    super.onDestroyView();
    _$_clearFindViewByIdCache();
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\006\020\003\032\0020\004?\006\005"}, d2={"Lcom/askgps/personaltrackercore/ui/statusbar/StatusBarFragment$Companion;", "", "()V", "newInstance", "Lcom/askgps/personaltrackercore/ui/statusbar/StatusBarFragment;", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final StatusBarFragment newInstance()
    {
      return new StatusBarFragment();
    }
  }
}

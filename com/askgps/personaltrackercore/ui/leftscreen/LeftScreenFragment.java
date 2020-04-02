package com.askgps.personaltrackercore.ui.leftscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.askgps.personaltrackercore.R.id;
import com.askgps.personaltrackercore.R.layout;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.koin.android.ext.android.ComponentCallbackExtKt;
import org.koin.core.Koin;
import org.koin.core.qualifier.Qualifier;
import org.koin.core.qualifier.QualifierKt;
import org.koin.core.registry.ScopeRegistry;
import org.koin.core.scope.Scope;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\030\000 \0172\0020\001:\001\017B\005?\006\002\020\002J\022\020\005\032\0020\0062\b\020\007\032\004\030\0010\bH\026J&\020\t\032\004\030\0010\n2\006\020\013\032\0020\f2\b\020\r\032\004\030\0010\0162\b\020\007\032\004\030\0010\bH\026R\016\020\003\032\0020\004X?.?\006\002\n\000?\006\020"}, d2={"Lcom/askgps/personaltrackercore/ui/leftscreen/LeftScreenFragment;", "Landroidx/fragment/app/Fragment;", "()V", "viewModel", "Lcom/askgps/personaltrackercore/ui/leftscreen/LeftScreenViewModel;", "onActivityCreated", "", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "Companion", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class LeftScreenFragment
  extends Fragment
{
  public static final Companion Companion = new Companion(null);
  private HashMap _$_findViewCache;
  private LeftScreenViewModel viewModel;
  
  public LeftScreenFragment() {}
  
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
    paramBundle = ViewModelProviders.of((Fragment)this).get(LeftScreenViewModel.class);
    Intrinsics.checkExpressionValueIsNotNull(paramBundle, "ViewModelProviders.of(th…eenViewModel::class.java)");
    this.viewModel = ((LeftScreenViewModel)paramBundle);
    paramBundle = getChildFragmentManager().beginTransaction();
    int i = R.id.leftScreen_frame_container;
    Qualifier localQualifier = (Qualifier)QualifierKt.named("mainFragment");
    Function0 localFunction0 = (Function0)null;
    paramBundle.replace(i, (Fragment)ComponentCallbackExtKt.getKoin(this).get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(Fragment.class), localQualifier, localFunction0)).commit();
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    Intrinsics.checkParameterIsNotNull(paramLayoutInflater, "inflater");
    return paramLayoutInflater.inflate(R.layout.left_screen_fragment, paramViewGroup, false);
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\006\020\003\032\0020\004?\006\005"}, d2={"Lcom/askgps/personaltrackercore/ui/leftscreen/LeftScreenFragment$Companion;", "", "()V", "newInstance", "Lcom/askgps/personaltrackercore/ui/leftscreen/LeftScreenFragment;", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final LeftScreenFragment newInstance()
    {
      return new LeftScreenFragment();
    }
  }
}

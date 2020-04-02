package com.askgps.personaltrackercore.ui.rightscreen;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.askgps.personaltrackercore.R.id;
import com.askgps.personaltrackercore.R.layout;
import com.askgps.personaltrackercore.R.string;
import com.askgps.personaltrackercore.ui.QrDialog;
import com.askgps.personaltrackercore.ui.phonebook.PhoneBookFragment;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\030\000 \0202\0020\001:\001\020B\005?\006\002\020\002J\022\020\005\032\0020\0062\b\020\007\032\004\030\0010\bH\026J&\020\t\032\004\030\0010\n2\006\020\013\032\0020\f2\b\020\r\032\004\030\0010\0162\b\020\007\032\004\030\0010\bH\026J\b\020\017\032\0020\006H\002R\016\020\003\032\0020\004X?.?\006\002\n\000?\006\021"}, d2={"Lcom/askgps/personaltrackercore/ui/rightscreen/RightScreenFragment;", "Landroidx/fragment/app/Fragment;", "()V", "viewModel", "Lcom/askgps/personaltrackercore/ui/rightscreen/RightScreenViewModel;", "onActivityCreated", "", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "setOnClickListeners", "Companion", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class RightScreenFragment
  extends Fragment
{
  public static final Companion Companion = new Companion(null);
  public static final String SETTINGS_PASSWORD_HASH = "428f2ad9945663b04963f7398887a65";
  private HashMap _$_findViewCache;
  private RightScreenViewModel viewModel;
  
  public RightScreenFragment() {}
  
  private final void setOnClickListeners()
  {
    ((AppCompatImageButton)_$_findCachedViewById(R.id.right_btn_settings)).setOnClickListener((View.OnClickListener)new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        this.this$0.startActivity(new Intent("android.settings.SETTINGS"));
      }
    });
    ((AppCompatImageButton)_$_findCachedViewById(R.id.right_btn_qr)).setOnClickListener((View.OnClickListener)new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        new QrDialog().show(this.this$0.getChildFragmentManager(), null);
      }
    });
    ((AppCompatImageButton)_$_findCachedViewById(R.id.right_btn_market)).setOnClickListener((View.OnClickListener)new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        try
        {
          paramAnonymousView = this.this$0;
          Context localContext = this.this$0.getContext();
          if (localContext == null) {
            Intrinsics.throwNpe();
          }
          Intrinsics.checkExpressionValueIsNotNull(localContext, "context!!");
          paramAnonymousView.startActivity(localContext.getPackageManager().getLaunchIntentForPackage("com.android.vending"));
        }
        catch (Exception paramAnonymousView)
        {
          paramAnonymousView = this.this$0.getContext();
          if (paramAnonymousView == null) {
            Intrinsics.throwNpe();
          }
          Toast.makeText(paramAnonymousView, R.string.cannot_open_market, 0).show();
        }
      }
    });
    View localView = getView();
    if (localView != null)
    {
      localView = localView.findViewById(R.id.right_btn_contacts);
      if (localView != null) {
        localView.setOnClickListener((View.OnClickListener)new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            new PhoneBookFragment().show(this.this$0.getChildFragmentManager(), Reflection.getOrCreateKotlinClass(PhoneBookFragment.class).getSimpleName());
          }
        });
      }
    }
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
    paramBundle = ViewModelProviders.of((Fragment)this).get(RightScreenViewModel.class);
    Intrinsics.checkExpressionValueIsNotNull(paramBundle, "ViewModelProviders.of(th…eenViewModel::class.java)");
    this.viewModel = ((RightScreenViewModel)paramBundle);
    setOnClickListeners();
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    Intrinsics.checkParameterIsNotNull(paramLayoutInflater, "inflater");
    return paramLayoutInflater.inflate(R.layout.right_screen_fragment, paramViewGroup, false);
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\000\n\002\030\002\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\006\020\005\032\0020\006R\016\020\003\032\0020\004X?T?\006\002\n\000?\006\007"}, d2={"Lcom/askgps/personaltrackercore/ui/rightscreen/RightScreenFragment$Companion;", "", "()V", "SETTINGS_PASSWORD_HASH", "", "newInstance", "Lcom/askgps/personaltrackercore/ui/rightscreen/RightScreenFragment;", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final RightScreenFragment newInstance()
    {
      return new RightScreenFragment();
    }
  }
}

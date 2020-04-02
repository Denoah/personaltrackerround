package com.askgps.personaltrackercore.ui.sos;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.askgps.personaltrackercore.R.id;
import com.askgps.personaltrackercore.R.layout;
import com.askgps.personaltrackercore.R.string;
import com.askgps.personaltrackercore.R.style;
import com.google.android.material.textview.MaterialTextView;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0008\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\030\000 \0212\0020\001:\001\021B\005?\006\002\020\002J\022\020\005\032\0020\0062\b\020\007\032\004\030\0010\bH\026J\022\020\t\032\0020\n2\b\020\007\032\004\030\0010\bH\026J&\020\013\032\004\030\0010\f2\006\020\r\032\0020\0162\b\020\017\032\004\030\0010\0202\b\020\007\032\004\030\0010\bH\026R\016\020\003\032\0020\004X?.?\006\002\n\000?\006\022"}, d2={"Lcom/askgps/personaltrackercore/ui/sos/SosFragment;", "Landroidx/fragment/app/DialogFragment;", "()V", "viewModel", "Lcom/askgps/personaltrackercore/ui/sos/SosViewModel;", "onActivityCreated", "", "savedInstanceState", "Landroid/os/Bundle;", "onCreateDialog", "Landroid/app/Dialog;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "Companion", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class SosFragment
  extends DialogFragment
{
  public static final Companion Companion = new Companion(null);
  private HashMap _$_findViewCache;
  private SosViewModel viewModel;
  
  public SosFragment() {}
  
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
    paramBundle = ViewModelProviders.of((Fragment)this).get(SosViewModel.class);
    Intrinsics.checkExpressionValueIsNotNull(paramBundle, "ViewModelProviders.of(th…SosViewModel::class.java)");
    paramBundle = (SosViewModel)paramBundle;
    this.viewModel = paramBundle;
    if (paramBundle == null) {
      Intrinsics.throwUninitializedPropertyAccessException("viewModel");
    }
    paramBundle.startTimer();
    ((AppCompatButton)_$_findCachedViewById(R.id.sos_btn_cancel)).setOnClickListener((View.OnClickListener)new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        SosFragment.access$getViewModel$p(this.this$0).cancel();
        this.this$0.dismiss();
      }
    });
    paramBundle = this.viewModel;
    if (paramBundle == null) {
      Intrinsics.throwUninitializedPropertyAccessException("viewModel");
    }
    paramBundle.getTimer().observe(getViewLifecycleOwner(), (Observer)new Observer()
    {
      public final void onChanged(Integer paramAnonymousInteger)
      {
        MaterialTextView localMaterialTextView = (MaterialTextView)this.this$0._$_findCachedViewById(R.id.sos_tv_through);
        Intrinsics.checkExpressionValueIsNotNull(localMaterialTextView, "sos_tv_through");
        localMaterialTextView.setText((CharSequence)this.this$0.getResources().getString(R.string.send_through, new Object[] { paramAnonymousInteger }));
        if ((paramAnonymousInteger != null) && (paramAnonymousInteger.intValue() == 0)) {
          this.this$0.dismiss();
        }
      }
    });
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    paramBundle = getContext();
    if (paramBundle == null) {
      Intrinsics.throwNpe();
    }
    return new Dialog(paramBundle, R.style.FullScreenDialog);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    Intrinsics.checkParameterIsNotNull(paramLayoutInflater, "inflater");
    return paramLayoutInflater.inflate(R.layout.sos_fragment, paramViewGroup, false);
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\006\020\003\032\0020\004?\006\005"}, d2={"Lcom/askgps/personaltrackercore/ui/sos/SosFragment$Companion;", "", "()V", "newInstance", "Lcom/askgps/personaltrackercore/ui/sos/SosFragment;", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final SosFragment newInstance()
    {
      return new SosFragment();
    }
  }
}

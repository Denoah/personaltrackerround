package com.askgps.personaltrackercore.ui.endwork;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.askgps.personaltrackercore.R.id;
import com.askgps.personaltrackercore.R.layout;
import com.askgps.personaltrackercore.R.style;
import com.askgps.personaltrackercore.extension.ContextExtensionKt;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0008\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\030\000 \0212\0020\001:\001\021B\005?\006\002\020\002J\022\020\005\032\0020\0062\b\020\007\032\004\030\0010\bH\026J\022\020\t\032\0020\n2\b\020\007\032\004\030\0010\bH\026J&\020\013\032\004\030\0010\f2\006\020\r\032\0020\0162\b\020\017\032\004\030\0010\0202\b\020\007\032\004\030\0010\bH\026R\016\020\003\032\0020\004X?.?\006\002\n\000?\006\022"}, d2={"Lcom/askgps/personaltrackercore/ui/endwork/EndWorkFragment;", "Landroidx/fragment/app/DialogFragment;", "()V", "viewModel", "Lcom/askgps/personaltrackercore/ui/endwork/EndWorkViewModel;", "onActivityCreated", "", "savedInstanceState", "Landroid/os/Bundle;", "onCreateDialog", "Landroid/app/Dialog;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "Companion", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class EndWorkFragment
  extends DialogFragment
{
  public static final Companion Companion = new Companion(null);
  private HashMap _$_findViewCache;
  private EndWorkViewModel viewModel;
  
  public EndWorkFragment() {}
  
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
    paramBundle = ViewModelProviders.of((Fragment)this).get(EndWorkViewModel.class);
    Intrinsics.checkExpressionValueIsNotNull(paramBundle, "ViewModelProviders.of(th…orkViewModel::class.java)");
    this.viewModel = ((EndWorkViewModel)paramBundle);
    ((AppCompatButton)_$_findCachedViewById(R.id.end_work_btn_cancel)).setOnClickListener((View.OnClickListener)new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        this.this$0.dismiss();
      }
    });
    ((AppCompatButton)_$_findCachedViewById(R.id.end_work_btn_end)).setOnClickListener((View.OnClickListener)new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        paramAnonymousView = this.this$0.getContext();
        if (paramAnonymousView == null) {
          Intrinsics.throwNpe();
        }
        Intrinsics.checkExpressionValueIsNotNull(paramAnonymousView, "context!!");
        paramAnonymousView = ContextExtensionKt.getID(paramAnonymousView);
        if (paramAnonymousView != null) {
          EndWorkFragment.access$getViewModel$p(this.this$0).endWork(paramAnonymousView);
        }
        this.this$0.dismiss();
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
    return paramLayoutInflater.inflate(R.layout.end_work_fragment, paramViewGroup, false);
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\006\020\003\032\0020\004?\006\005"}, d2={"Lcom/askgps/personaltrackercore/ui/endwork/EndWorkFragment$Companion;", "", "()V", "newInstance", "Lcom/askgps/personaltrackercore/ui/endwork/EndWorkFragment;", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final EndWorkFragment newInstance()
    {
      return new EndWorkFragment();
    }
  }
}

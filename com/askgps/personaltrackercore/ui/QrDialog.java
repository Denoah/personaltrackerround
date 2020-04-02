package com.askgps.personaltrackercore.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import com.askgps.personaltrackercore.R.style;
import com.askgps.personaltrackercore.databinding.QrDialogFragmentBinding;
import com.askgps.personaltrackercore.extension.ContextExtensionKt;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0008\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\002\b\002\030\0002\0020\001B\005?\006\002\020\002J\022\020\005\032\0020\0062\b\020\007\032\004\030\0010\bH\026J$\020\t\032\0020\n2\006\020\013\032\0020\f2\b\020\r\032\004\030\0010\0162\b\020\007\032\004\030\0010\bH\026J\032\020\017\032\0020\0202\006\020\021\032\0020\n2\b\020\007\032\004\030\0010\bH\026R\016\020\003\032\0020\004X?.?\006\002\n\000?\006\022"}, d2={"Lcom/askgps/personaltrackercore/ui/QrDialog;", "Landroidx/fragment/app/DialogFragment;", "()V", "binding", "Lcom/askgps/personaltrackercore/databinding/QrDialogFragmentBinding;", "onCreateDialog", "Landroid/app/Dialog;", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onViewCreated", "", "view", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class QrDialog
  extends DialogFragment
{
  private HashMap _$_findViewCache;
  private QrDialogFragmentBinding binding;
  
  public QrDialog() {}
  
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
    paramLayoutInflater = QrDialogFragmentBinding.inflate(paramLayoutInflater, paramViewGroup, false);
    Intrinsics.checkExpressionValueIsNotNull(paramLayoutInflater, "QrDialogFragmentBinding.…flater, container, false)");
    this.binding = paramLayoutInflater;
    if (paramLayoutInflater == null) {
      Intrinsics.throwUninitializedPropertyAccessException("binding");
    }
    paramLayoutInflater = paramLayoutInflater.getRoot();
    Intrinsics.checkExpressionValueIsNotNull(paramLayoutInflater, "binding.root");
    return paramLayoutInflater;
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "view");
    super.onViewCreated(paramView, paramBundle);
    QrDialogFragmentBinding localQrDialogFragmentBinding = this.binding;
    if (localQrDialogFragmentBinding == null) {
      Intrinsics.throwUninitializedPropertyAccessException("binding");
    }
    paramBundle = getContext();
    if (paramBundle == null) {
      Intrinsics.throwNpe();
    }
    Intrinsics.checkExpressionValueIsNotNull(paramBundle, "context!!");
    String str = ContextExtensionKt.getID(paramBundle);
    if (str != null)
    {
      paramBundle = getContext();
      if (paramBundle == null) {
        Intrinsics.throwNpe();
      }
      Intrinsics.checkExpressionValueIsNotNull(paramBundle, "context!!");
      paramBundle = ContextExtensionKt.generateQr(paramBundle, str);
    }
    else
    {
      paramBundle = null;
    }
    localQrDialogFragmentBinding.setQr(paramBundle);
    paramBundle = this.binding;
    if (paramBundle == null) {
      Intrinsics.throwUninitializedPropertyAccessException("binding");
    }
    paramBundle.executePendingBindings();
    paramView.setOnClickListener((View.OnClickListener)new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        this.this$0.dismiss();
      }
    });
  }
}

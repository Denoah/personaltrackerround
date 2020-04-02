package com.askgps.personaltrackercore.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import com.askgps.personaltrackercore.HelperKt;
import com.askgps.personaltrackercore.R.id;
import com.askgps.personaltrackercore.R.layout;
import com.askgps.personaltrackercore.R.string;
import com.askgps.personaltrackercore.R.style;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000,\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\020\013\n\002\020\002\n\002\b\003\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\030\0002\0020\001B\031\022\022\020\002\032\016\022\004\022\0020\004\022\004\022\0020\0050\003?\006\002\020\006J\020\020\007\032\0020\0042\006\020\b\032\0020\tH\002J\022\020\n\032\0020\0132\b\020\f\032\004\030\0010\rH\026R\032\020\002\032\016\022\004\022\0020\004\022\004\022\0020\0050\003X?\004?\006\002\n\000?\006\016"}, d2={"Lcom/askgps/personaltrackercore/ui/PasswordDialog;", "Landroidx/fragment/app/DialogFragment;", "callback", "Lkotlin/Function1;", "", "", "(Lkotlin/jvm/functions/Function1;)V", "checkPassword", "pass", "", "onCreateDialog", "Landroid/app/Dialog;", "savedInstanceState", "Landroid/os/Bundle;", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class PasswordDialog
  extends DialogFragment
{
  private HashMap _$_findViewCache;
  private final Function1<Boolean, Unit> callback;
  
  public PasswordDialog(Function1<? super Boolean, Unit> paramFunction1)
  {
    this.callback = paramFunction1;
  }
  
  private final boolean checkPassword(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append(paramString);
    return Intrinsics.areEqual(HelperKt.md5(localStringBuilder.toString()), "428f2ad9945663b04963f7398887a65");
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
  
  public Dialog onCreateDialog(final Bundle paramBundle)
  {
    paramBundle = getContext();
    if (paramBundle == null) {
      Intrinsics.throwNpe();
    }
    paramBundle = LayoutInflater.from(paramBundle).inflate(R.layout.password_dialog, null);
    Object localObject = getContext();
    if (localObject == null) {
      Intrinsics.throwNpe();
    }
    localObject = new AlertDialog.Builder((Context)localObject, R.style.Dialog).setView(paramBundle).setPositiveButton(R.string.yes, null).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener()
    {
      public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        PasswordDialog.access$getCallback$p(this.this$0).invoke(Boolean.valueOf(false));
      }
    }).create();
    ((AlertDialog)localObject).setOnShowListener((DialogInterface.OnShowListener)new DialogInterface.OnShowListener()
    {
      public final void onShow(DialogInterface paramAnonymousDialogInterface)
      {
        paramAnonymousDialogInterface = this.$this_apply.getButton(-1);
        Intrinsics.checkExpressionValueIsNotNull(paramAnonymousDialogInterface, "getButton(AlertDialog.BUTTON_POSITIVE)");
        paramAnonymousDialogInterface.setOnClickListener((View.OnClickListener)new View.OnClickListener()
        {
          public final void onClick(View paramAnonymous2View)
          {
            PasswordDialog localPasswordDialog = this.this$0.this$0;
            paramAnonymous2View = this.this$0.$view$inlined.findViewById(R.id.password);
            Intrinsics.checkExpressionValueIsNotNull(paramAnonymous2View, "view.findViewById<EditText>(R.id.password)");
            if (PasswordDialog.access$checkPassword(localPasswordDialog, ((EditText)paramAnonymous2View).getText().toString()))
            {
              PasswordDialog.access$getCallback$p(this.this$0.this$0).invoke(Boolean.valueOf(true));
              this.this$0.$this_apply.dismiss();
            }
            else
            {
              Toast.makeText(this.this$0.$this_apply.getContext(), R.string.wrong_password, 0).show();
            }
          }
        });
      }
    });
    setCancelable(false);
    Intrinsics.checkExpressionValueIsNotNull(localObject, "AlertDialog.Builder(cont…ble = false\n            }");
    return (Dialog)localObject;
  }
}

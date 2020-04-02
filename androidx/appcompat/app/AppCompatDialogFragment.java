package androidx.appcompat.app;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import androidx.fragment.app.DialogFragment;

public class AppCompatDialogFragment
  extends DialogFragment
{
  public AppCompatDialogFragment() {}
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    return new AppCompatDialog(getContext(), getTheme());
  }
  
  public void setupDialog(Dialog paramDialog, int paramInt)
  {
    if ((paramDialog instanceof AppCompatDialog))
    {
      AppCompatDialog localAppCompatDialog = (AppCompatDialog)paramDialog;
      if ((paramInt != 1) && (paramInt != 2))
      {
        if (paramInt == 3) {
          paramDialog.getWindow().addFlags(24);
        }
      }
      else {
        localAppCompatDialog.supportRequestWindowFeature(1);
      }
    }
    else
    {
      super.setupDialog(paramDialog, paramInt);
    }
  }
}

package androidx.fragment.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

public class DialogFragment
  extends Fragment
  implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener
{
  private static final String SAVED_BACK_STACK_ID = "android:backStackId";
  private static final String SAVED_CANCELABLE = "android:cancelable";
  private static final String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";
  private static final String SAVED_SHOWS_DIALOG = "android:showsDialog";
  private static final String SAVED_STYLE = "android:style";
  private static final String SAVED_THEME = "android:theme";
  public static final int STYLE_NORMAL = 0;
  public static final int STYLE_NO_FRAME = 2;
  public static final int STYLE_NO_INPUT = 3;
  public static final int STYLE_NO_TITLE = 1;
  int mBackStackId = -1;
  boolean mCancelable = true;
  Dialog mDialog;
  private Runnable mDismissRunnable = new Runnable()
  {
    public void run()
    {
      DialogFragment.this.mOnDismissListener.onDismiss(DialogFragment.this.mDialog);
    }
  };
  boolean mDismissed;
  private Handler mHandler;
  DialogInterface.OnCancelListener mOnCancelListener = new DialogInterface.OnCancelListener()
  {
    public void onCancel(DialogInterface paramAnonymousDialogInterface)
    {
      if (DialogFragment.this.mDialog != null)
      {
        paramAnonymousDialogInterface = DialogFragment.this;
        paramAnonymousDialogInterface.onCancel(paramAnonymousDialogInterface.mDialog);
      }
    }
  };
  DialogInterface.OnDismissListener mOnDismissListener = new DialogInterface.OnDismissListener()
  {
    public void onDismiss(DialogInterface paramAnonymousDialogInterface)
    {
      if (DialogFragment.this.mDialog != null)
      {
        paramAnonymousDialogInterface = DialogFragment.this;
        paramAnonymousDialogInterface.onDismiss(paramAnonymousDialogInterface.mDialog);
      }
    }
  };
  boolean mShownByMe;
  boolean mShowsDialog = true;
  int mStyle = 0;
  int mTheme = 0;
  boolean mViewDestroyed;
  
  public DialogFragment() {}
  
  public void dismiss()
  {
    dismissInternal(false, false);
  }
  
  public void dismissAllowingStateLoss()
  {
    dismissInternal(true, false);
  }
  
  void dismissInternal(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.mDismissed) {
      return;
    }
    this.mDismissed = true;
    this.mShownByMe = false;
    Object localObject = this.mDialog;
    if (localObject != null)
    {
      ((Dialog)localObject).setOnDismissListener(null);
      this.mDialog.dismiss();
      if (!paramBoolean2) {
        if (Looper.myLooper() == this.mHandler.getLooper()) {
          onDismiss(this.mDialog);
        } else {
          this.mHandler.post(this.mDismissRunnable);
        }
      }
    }
    this.mViewDestroyed = true;
    if (this.mBackStackId >= 0)
    {
      getParentFragmentManager().popBackStack(this.mBackStackId, 1);
      this.mBackStackId = -1;
    }
    else
    {
      localObject = getParentFragmentManager().beginTransaction();
      ((FragmentTransaction)localObject).remove(this);
      if (paramBoolean1) {
        ((FragmentTransaction)localObject).commitAllowingStateLoss();
      } else {
        ((FragmentTransaction)localObject).commit();
      }
    }
  }
  
  public Dialog getDialog()
  {
    return this.mDialog;
  }
  
  public boolean getShowsDialog()
  {
    return this.mShowsDialog;
  }
  
  public int getTheme()
  {
    return this.mTheme;
  }
  
  public boolean isCancelable()
  {
    return this.mCancelable;
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    if (!this.mShowsDialog) {
      return;
    }
    Object localObject = getView();
    if (localObject != null) {
      if (((View)localObject).getParent() == null) {
        this.mDialog.setContentView((View)localObject);
      } else {
        throw new IllegalStateException("DialogFragment can not be attached to a container view");
      }
    }
    localObject = getActivity();
    if (localObject != null) {
      this.mDialog.setOwnerActivity((Activity)localObject);
    }
    this.mDialog.setCancelable(this.mCancelable);
    this.mDialog.setOnCancelListener(this.mOnCancelListener);
    this.mDialog.setOnDismissListener(this.mOnDismissListener);
    if (paramBundle != null)
    {
      paramBundle = paramBundle.getBundle("android:savedDialogState");
      if (paramBundle != null) {
        this.mDialog.onRestoreInstanceState(paramBundle);
      }
    }
  }
  
  public void onAttach(Context paramContext)
  {
    super.onAttach(paramContext);
    if (!this.mShownByMe) {
      this.mDismissed = false;
    }
  }
  
  public void onCancel(DialogInterface paramDialogInterface) {}
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mHandler = new Handler();
    boolean bool;
    if (this.mContainerId == 0) {
      bool = true;
    } else {
      bool = false;
    }
    this.mShowsDialog = bool;
    if (paramBundle != null)
    {
      this.mStyle = paramBundle.getInt("android:style", 0);
      this.mTheme = paramBundle.getInt("android:theme", 0);
      this.mCancelable = paramBundle.getBoolean("android:cancelable", true);
      this.mShowsDialog = paramBundle.getBoolean("android:showsDialog", this.mShowsDialog);
      this.mBackStackId = paramBundle.getInt("android:backStackId", -1);
    }
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    return new Dialog(requireContext(), getTheme());
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    Dialog localDialog = this.mDialog;
    if (localDialog != null)
    {
      this.mViewDestroyed = true;
      localDialog.setOnDismissListener(null);
      this.mDialog.dismiss();
      if (!this.mDismissed) {
        onDismiss(this.mDialog);
      }
      this.mDialog = null;
    }
  }
  
  public void onDetach()
  {
    super.onDetach();
    if ((!this.mShownByMe) && (!this.mDismissed)) {
      this.mDismissed = true;
    }
  }
  
  public void onDismiss(DialogInterface paramDialogInterface)
  {
    if (!this.mViewDestroyed) {
      dismissInternal(true, true);
    }
  }
  
  public LayoutInflater onGetLayoutInflater(Bundle paramBundle)
  {
    if (!this.mShowsDialog) {
      return super.onGetLayoutInflater(paramBundle);
    }
    paramBundle = onCreateDialog(paramBundle);
    this.mDialog = paramBundle;
    if (paramBundle != null)
    {
      setupDialog(paramBundle, this.mStyle);
      return (LayoutInflater)this.mDialog.getContext().getSystemService("layout_inflater");
    }
    return (LayoutInflater)this.mHost.getContext().getSystemService("layout_inflater");
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    Object localObject = this.mDialog;
    if (localObject != null)
    {
      localObject = ((Dialog)localObject).onSaveInstanceState();
      if (localObject != null) {
        paramBundle.putBundle("android:savedDialogState", (Bundle)localObject);
      }
    }
    int i = this.mStyle;
    if (i != 0) {
      paramBundle.putInt("android:style", i);
    }
    i = this.mTheme;
    if (i != 0) {
      paramBundle.putInt("android:theme", i);
    }
    boolean bool = this.mCancelable;
    if (!bool) {
      paramBundle.putBoolean("android:cancelable", bool);
    }
    bool = this.mShowsDialog;
    if (!bool) {
      paramBundle.putBoolean("android:showsDialog", bool);
    }
    i = this.mBackStackId;
    if (i != -1) {
      paramBundle.putInt("android:backStackId", i);
    }
  }
  
  public void onStart()
  {
    super.onStart();
    Dialog localDialog = this.mDialog;
    if (localDialog != null)
    {
      this.mViewDestroyed = false;
      localDialog.show();
    }
  }
  
  public void onStop()
  {
    super.onStop();
    Dialog localDialog = this.mDialog;
    if (localDialog != null) {
      localDialog.hide();
    }
  }
  
  public final Dialog requireDialog()
  {
    Object localObject = getDialog();
    if (localObject != null) {
      return localObject;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("DialogFragment ");
    ((StringBuilder)localObject).append(this);
    ((StringBuilder)localObject).append(" does not have a Dialog.");
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public void setCancelable(boolean paramBoolean)
  {
    this.mCancelable = paramBoolean;
    Dialog localDialog = this.mDialog;
    if (localDialog != null) {
      localDialog.setCancelable(paramBoolean);
    }
  }
  
  public void setShowsDialog(boolean paramBoolean)
  {
    this.mShowsDialog = paramBoolean;
  }
  
  public void setStyle(int paramInt1, int paramInt2)
  {
    this.mStyle = paramInt1;
    if ((paramInt1 == 2) || (paramInt1 == 3)) {
      this.mTheme = 16973913;
    }
    if (paramInt2 != 0) {
      this.mTheme = paramInt2;
    }
  }
  
  public void setupDialog(Dialog paramDialog, int paramInt)
  {
    if ((paramInt != 1) && (paramInt != 2))
    {
      if (paramInt == 3) {
        paramDialog.getWindow().addFlags(24);
      }
    }
    else {
      paramDialog.requestWindowFeature(1);
    }
  }
  
  public int show(FragmentTransaction paramFragmentTransaction, String paramString)
  {
    this.mDismissed = false;
    this.mShownByMe = true;
    paramFragmentTransaction.add(this, paramString);
    this.mViewDestroyed = false;
    int i = paramFragmentTransaction.commit();
    this.mBackStackId = i;
    return i;
  }
  
  public void show(FragmentManager paramFragmentManager, String paramString)
  {
    this.mDismissed = false;
    this.mShownByMe = true;
    paramFragmentManager = paramFragmentManager.beginTransaction();
    paramFragmentManager.add(this, paramString);
    paramFragmentManager.commit();
  }
  
  public void showNow(FragmentManager paramFragmentManager, String paramString)
  {
    this.mDismissed = false;
    this.mShownByMe = true;
    paramFragmentManager = paramFragmentManager.beginTransaction();
    paramFragmentManager.add(this, paramString);
    paramFragmentManager.commitNow();
  }
}

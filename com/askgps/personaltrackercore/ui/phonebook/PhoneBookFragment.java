package com.askgps.personaltrackercore.ui.phonebook;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.askgps.personaltrackercore.R.id;
import com.askgps.personaltrackercore.R.layout;
import com.askgps.personaltrackercore.R.string;
import com.askgps.personaltrackercore.R.style;
import com.askgps.personaltrackercore.database.model.PhoneNumber;
import com.askgps.personaltrackercore.databinding.PhoneBookFragmentBinding;
import com.askgps.personaltrackercore.extension.ContextExtensionKt;
import com.google.android.material.textfield.TextInputEditText;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import java.util.HashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000F\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\030\000 \0262\0020\001:\001\026B\005?\006\002\020\002J\b\020\t\032\0020\nH\002J\022\020\013\032\0020\n2\b\020\f\032\004\030\0010\rH\026J\022\020\016\032\0020\0172\b\020\f\032\004\030\0010\rH\026J&\020\020\032\004\030\0010\0212\006\020\022\032\0020\0232\b\020\024\032\004\030\0010\0252\b\020\f\032\004\030\0010\rH\026R\016\020\003\032\0020\004X?.?\006\002\n\000R\016\020\005\032\0020\006X?\004?\006\002\n\000R\016\020\007\032\0020\bX?.?\006\002\n\000?\006\027"}, d2={"Lcom/askgps/personaltrackercore/ui/phonebook/PhoneBookFragment;", "Landroidx/fragment/app/DialogFragment;", "()V", "binding", "Lcom/askgps/personaltrackercore/databinding/PhoneBookFragmentBinding;", "compositeDisposable", "Lio/reactivex/disposables/CompositeDisposable;", "viewModel", "Lcom/askgps/personaltrackercore/ui/phonebook/PhoneBookViewModel;", "observeViewModel", "", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onCreateDialog", "Landroid/app/Dialog;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "Companion", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class PhoneBookFragment
  extends DialogFragment
{
  public static final Companion Companion = new Companion(null);
  private HashMap _$_findViewCache;
  private PhoneBookFragmentBinding binding;
  private final CompositeDisposable compositeDisposable = new CompositeDisposable();
  private PhoneBookViewModel viewModel;
  
  public PhoneBookFragment() {}
  
  private final void observeViewModel()
  {
    PhoneBookViewModel localPhoneBookViewModel = this.viewModel;
    if (localPhoneBookViewModel == null) {
      Intrinsics.throwUninitializedPropertyAccessException("viewModel");
    }
    localPhoneBookViewModel.getPhoneNumbers().observe(getViewLifecycleOwner(), (Observer)new Observer()
    {
      public final void onChanged(List<PhoneNumber> paramAnonymousList)
      {
        PhoneBookAdapter localPhoneBookAdapter = PhoneBookFragment.access$getViewModel$p(this.this$0).getAdapter();
        Intrinsics.checkExpressionValueIsNotNull(paramAnonymousList, "it");
        localPhoneBookAdapter.submitList(paramAnonymousList);
      }
    });
    CompositeDisposable localCompositeDisposable = this.compositeDisposable;
    localPhoneBookViewModel = this.viewModel;
    if (localPhoneBookViewModel == null) {
      Intrinsics.throwUninitializedPropertyAccessException("viewModel");
    }
    localCompositeDisposable.add(localPhoneBookViewModel.getActionPhoneNumber().subscribe((Consumer)new Consumer()
    {
      public final void accept(final Pair<? extends PhoneBookViewModel.Action, PhoneNumber> paramAnonymousPair)
      {
        Object localObject = (PhoneBookViewModel.Action)paramAnonymousPair.getFirst();
        int i = PhoneBookFragment.WhenMappings.$EnumSwitchMapping$0[localObject.ordinal()];
        if (i != 1)
        {
          if (i == 2)
          {
            localObject = this.this$0.getContext();
            if (localObject == null) {
              Intrinsics.throwNpe();
            }
            new AlertDialog.Builder((Context)localObject, R.style.Dialog).setTitle(R.string.delete_contact).setPositiveButton(R.string.yes, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener()
            {
              public final void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
              {
                PhoneBookFragment.access$getViewModel$p(this.this$0.this$0).deletePhoneNumber((PhoneNumber)paramAnonymousPair.getSecond());
              }
            }).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener)4.INSTANCE).create().show();
          }
        }
        else
        {
          localObject = this.this$0.getContext();
          if (localObject == null) {
            Intrinsics.throwNpe();
          }
          new AlertDialog.Builder((Context)localObject, R.style.Dialog).setTitle(R.string.make_call).setMessage((CharSequence)((PhoneNumber)paramAnonymousPair.getSecond()).getNumber()).setPositiveButton(R.string.yes, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener()
          {
            public final void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
            {
              paramAnonymous2DialogInterface = this.this$0.this$0.getContext();
              if (paramAnonymous2DialogInterface != null) {
                ContextExtensionKt.makeCall(paramAnonymous2DialogInterface, ((PhoneNumber)paramAnonymousPair.getSecond()).getNumber());
              }
            }
          }).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener)2.INSTANCE).create().show();
        }
      }
    }));
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
    paramBundle = ViewModelProviders.of((Fragment)this).get(PhoneBookViewModel.class);
    Intrinsics.checkExpressionValueIsNotNull(paramBundle, "ViewModelProviders.of(th…ookViewModel::class.java)");
    this.viewModel = ((PhoneBookViewModel)paramBundle);
    PhoneBookFragmentBinding localPhoneBookFragmentBinding = this.binding;
    if (localPhoneBookFragmentBinding == null) {
      Intrinsics.throwUninitializedPropertyAccessException("binding");
    }
    paramBundle = this.viewModel;
    if (paramBundle == null) {
      Intrinsics.throwUninitializedPropertyAccessException("viewModel");
    }
    localPhoneBookFragmentBinding.setVm(paramBundle);
    paramBundle = this.binding;
    if (paramBundle == null) {
      Intrinsics.throwUninitializedPropertyAccessException("binding");
    }
    paramBundle.setLifecycleOwner(getViewLifecycleOwner());
    paramBundle = this.binding;
    if (paramBundle == null) {
      Intrinsics.throwUninitializedPropertyAccessException("binding");
    }
    paramBundle.executePendingBindings();
    observeViewModel();
    paramBundle = this.binding;
    if (paramBundle == null) {
      Intrinsics.throwUninitializedPropertyAccessException("binding");
    }
    paramBundle.phoneBookBtnAdd.setOnClickListener((View.OnClickListener)new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        paramAnonymousView = this.this$0.getActivity();
        if (paramAnonymousView == null) {
          Intrinsics.throwNpe();
        }
        Intrinsics.checkExpressionValueIsNotNull(paramAnonymousView, "activity!!");
        final View localView = paramAnonymousView.getLayoutInflater().inflate(R.layout.add_contact, null);
        paramAnonymousView = this.this$0.getContext();
        if (paramAnonymousView == null) {
          Intrinsics.throwNpe();
        }
        new AlertDialog.Builder(paramAnonymousView, R.style.Dialog).setTitle(R.string.add_contact).setView(localView).setPositiveButton(R.string.yes, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener()
        {
          public final void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            paramAnonymous2DialogInterface = localView.findViewById(R.id.add_contact_te_name);
            Intrinsics.checkExpressionValueIsNotNull(paramAnonymous2DialogInterface, "view.findViewById<TextIn…R.id.add_contact_te_name)");
            paramAnonymous2DialogInterface = String.valueOf(((TextInputEditText)paramAnonymous2DialogInterface).getText());
            Object localObject = localView.findViewById(R.id.add_contact_te_number);
            Intrinsics.checkExpressionValueIsNotNull(localObject, "view.findViewById<TextIn…id.add_contact_te_number)");
            localObject = String.valueOf(((TextInputEditText)localObject).getText());
            PhoneBookFragment.access$getViewModel$p(this.this$0.this$0).addPhoneNumber(new PhoneNumber(paramAnonymous2DialogInterface, (String)localObject));
          }
        }).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener)2.INSTANCE).create().show();
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
    paramLayoutInflater = PhoneBookFragmentBinding.inflate(paramLayoutInflater, paramViewGroup, false);
    Intrinsics.checkExpressionValueIsNotNull(paramLayoutInflater, "PhoneBookFragmentBinding…flater, container, false)");
    this.binding = paramLayoutInflater;
    if (paramLayoutInflater == null) {
      Intrinsics.throwUninitializedPropertyAccessException("binding");
    }
    return paramLayoutInflater.getRoot();
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\006\020\003\032\0020\004?\006\005"}, d2={"Lcom/askgps/personaltrackercore/ui/phonebook/PhoneBookFragment$Companion;", "", "()V", "newInstance", "Lcom/askgps/personaltrackercore/ui/phonebook/PhoneBookFragment;", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final PhoneBookFragment newInstance()
    {
      return new PhoneBookFragment();
    }
  }
}

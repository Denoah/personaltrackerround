package com.askgps.personaltrackercore.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.askgps.personaltrackercore.R.layout;
import com.askgps.personaltrackercore.ui.phonebook.PhoneBookViewModel;

public abstract class PhoneBookFragmentBinding
  extends ViewDataBinding
{
  @Bindable
  protected PhoneBookViewModel mVm;
  public final AppCompatButton phoneBookBtnAdd;
  public final RecyclerView recyclerView;
  
  protected PhoneBookFragmentBinding(Object paramObject, View paramView, int paramInt, AppCompatButton paramAppCompatButton, RecyclerView paramRecyclerView)
  {
    super(paramObject, paramView, paramInt);
    this.phoneBookBtnAdd = paramAppCompatButton;
    this.recyclerView = paramRecyclerView;
  }
  
  public static PhoneBookFragmentBinding bind(View paramView)
  {
    return bind(paramView, DataBindingUtil.getDefaultComponent());
  }
  
  @Deprecated
  public static PhoneBookFragmentBinding bind(View paramView, Object paramObject)
  {
    return (PhoneBookFragmentBinding)bind(paramObject, paramView, R.layout.phone_book_fragment);
  }
  
  public static PhoneBookFragmentBinding inflate(LayoutInflater paramLayoutInflater)
  {
    return inflate(paramLayoutInflater, DataBindingUtil.getDefaultComponent());
  }
  
  public static PhoneBookFragmentBinding inflate(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, boolean paramBoolean)
  {
    return inflate(paramLayoutInflater, paramViewGroup, paramBoolean, DataBindingUtil.getDefaultComponent());
  }
  
  @Deprecated
  public static PhoneBookFragmentBinding inflate(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, boolean paramBoolean, Object paramObject)
  {
    return (PhoneBookFragmentBinding)ViewDataBinding.inflateInternal(paramLayoutInflater, R.layout.phone_book_fragment, paramViewGroup, paramBoolean, paramObject);
  }
  
  @Deprecated
  public static PhoneBookFragmentBinding inflate(LayoutInflater paramLayoutInflater, Object paramObject)
  {
    return (PhoneBookFragmentBinding)ViewDataBinding.inflateInternal(paramLayoutInflater, R.layout.phone_book_fragment, null, false, paramObject);
  }
  
  public PhoneBookViewModel getVm()
  {
    return this.mVm;
  }
  
  public abstract void setVm(PhoneBookViewModel paramPhoneBookViewModel);
}

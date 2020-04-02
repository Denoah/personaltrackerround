package com.askgps.personaltrackercore.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.askgps.personaltrackercore.R.layout;
import com.askgps.personaltrackercore.database.model.PhoneNumber;

public abstract class PhoneNumberBinding
  extends ViewDataBinding
{
  @Bindable
  protected PhoneNumber mPhoneNumber;
  
  protected PhoneNumberBinding(Object paramObject, View paramView, int paramInt)
  {
    super(paramObject, paramView, paramInt);
  }
  
  public static PhoneNumberBinding bind(View paramView)
  {
    return bind(paramView, DataBindingUtil.getDefaultComponent());
  }
  
  @Deprecated
  public static PhoneNumberBinding bind(View paramView, Object paramObject)
  {
    return (PhoneNumberBinding)bind(paramObject, paramView, R.layout.phone_number);
  }
  
  public static PhoneNumberBinding inflate(LayoutInflater paramLayoutInflater)
  {
    return inflate(paramLayoutInflater, DataBindingUtil.getDefaultComponent());
  }
  
  public static PhoneNumberBinding inflate(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, boolean paramBoolean)
  {
    return inflate(paramLayoutInflater, paramViewGroup, paramBoolean, DataBindingUtil.getDefaultComponent());
  }
  
  @Deprecated
  public static PhoneNumberBinding inflate(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, boolean paramBoolean, Object paramObject)
  {
    return (PhoneNumberBinding)ViewDataBinding.inflateInternal(paramLayoutInflater, R.layout.phone_number, paramViewGroup, paramBoolean, paramObject);
  }
  
  @Deprecated
  public static PhoneNumberBinding inflate(LayoutInflater paramLayoutInflater, Object paramObject)
  {
    return (PhoneNumberBinding)ViewDataBinding.inflateInternal(paramLayoutInflater, R.layout.phone_number, null, false, paramObject);
  }
  
  public PhoneNumber getPhoneNumber()
  {
    return this.mPhoneNumber;
  }
  
  public abstract void setPhoneNumber(PhoneNumber paramPhoneNumber);
}

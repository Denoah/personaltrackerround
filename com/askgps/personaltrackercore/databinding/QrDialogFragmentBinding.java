package com.askgps.personaltrackercore.databinding;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.askgps.personaltrackercore.R.layout;

public abstract class QrDialogFragmentBinding
  extends ViewDataBinding
{
  @Bindable
  protected Bitmap mQr;
  public final AppCompatImageView qrDialogImgQr;
  
  protected QrDialogFragmentBinding(Object paramObject, View paramView, int paramInt, AppCompatImageView paramAppCompatImageView)
  {
    super(paramObject, paramView, paramInt);
    this.qrDialogImgQr = paramAppCompatImageView;
  }
  
  public static QrDialogFragmentBinding bind(View paramView)
  {
    return bind(paramView, DataBindingUtil.getDefaultComponent());
  }
  
  @Deprecated
  public static QrDialogFragmentBinding bind(View paramView, Object paramObject)
  {
    return (QrDialogFragmentBinding)bind(paramObject, paramView, R.layout.qr_dialog_fragment);
  }
  
  public static QrDialogFragmentBinding inflate(LayoutInflater paramLayoutInflater)
  {
    return inflate(paramLayoutInflater, DataBindingUtil.getDefaultComponent());
  }
  
  public static QrDialogFragmentBinding inflate(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, boolean paramBoolean)
  {
    return inflate(paramLayoutInflater, paramViewGroup, paramBoolean, DataBindingUtil.getDefaultComponent());
  }
  
  @Deprecated
  public static QrDialogFragmentBinding inflate(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, boolean paramBoolean, Object paramObject)
  {
    return (QrDialogFragmentBinding)ViewDataBinding.inflateInternal(paramLayoutInflater, R.layout.qr_dialog_fragment, paramViewGroup, paramBoolean, paramObject);
  }
  
  @Deprecated
  public static QrDialogFragmentBinding inflate(LayoutInflater paramLayoutInflater, Object paramObject)
  {
    return (QrDialogFragmentBinding)ViewDataBinding.inflateInternal(paramLayoutInflater, R.layout.qr_dialog_fragment, null, false, paramObject);
  }
  
  public Bitmap getQr()
  {
    return this.mQr;
  }
  
  public abstract void setQr(Bitmap paramBitmap);
}

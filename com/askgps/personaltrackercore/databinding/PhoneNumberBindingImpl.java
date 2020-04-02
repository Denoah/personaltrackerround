package com.askgps.personaltrackercore.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding.IncludedLayouts;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.askgps.personaltrackercore.BR;
import com.askgps.personaltrackercore.binding.BindingHelperKt;
import com.askgps.personaltrackercore.database.model.PhoneNumber;
import com.google.android.material.textview.MaterialTextView;

public class PhoneNumberBindingImpl
  extends PhoneNumberBinding
{
  private static final ViewDataBinding.IncludedLayouts sIncludes;
  private static final SparseIntArray sViewsWithIds;
  private long mDirtyFlags = -1L;
  private final LinearLayout mboundView0;
  private final MaterialTextView mboundView1;
  private final MaterialTextView mboundView2;
  private final MaterialTextView mboundView3;
  
  public PhoneNumberBindingImpl(DataBindingComponent paramDataBindingComponent, View paramView)
  {
    this(paramDataBindingComponent, paramView, mapBindings(paramDataBindingComponent, paramView, 4, sIncludes, sViewsWithIds));
  }
  
  private PhoneNumberBindingImpl(DataBindingComponent paramDataBindingComponent, View paramView, Object[] paramArrayOfObject)
  {
    super(paramDataBindingComponent, paramView, 0);
    paramDataBindingComponent = (LinearLayout)paramArrayOfObject[0];
    this.mboundView0 = paramDataBindingComponent;
    paramDataBindingComponent.setTag(null);
    paramDataBindingComponent = (MaterialTextView)paramArrayOfObject[1];
    this.mboundView1 = paramDataBindingComponent;
    paramDataBindingComponent.setTag(null);
    paramDataBindingComponent = (MaterialTextView)paramArrayOfObject[2];
    this.mboundView2 = paramDataBindingComponent;
    paramDataBindingComponent.setTag(null);
    paramDataBindingComponent = (MaterialTextView)paramArrayOfObject[3];
    this.mboundView3 = paramDataBindingComponent;
    paramDataBindingComponent.setTag(null);
    setRootTag(paramView);
    invalidateAll();
  }
  
  protected void executeBindings()
  {
    try
    {
      long l = this.mDirtyFlags;
      this.mDirtyFlags = 0L;
      Object localObject1 = this.mPhoneNumber;
      boolean bool = (l & 0x3) < 0L;
      String str = null;
      if ((bool) && (localObject1 != null))
      {
        str = ((PhoneNumber)localObject1).getName();
        localObject1 = ((PhoneNumber)localObject1).getNumber();
      }
      else
      {
        localObject1 = null;
      }
      if (bool)
      {
        BindingHelperKt.setLastChar(this.mboundView1, str);
        TextViewBindingAdapter.setText(this.mboundView2, str);
        TextViewBindingAdapter.setText(this.mboundView3, (CharSequence)localObject1);
      }
      return;
    }
    finally {}
  }
  
  public boolean hasPendingBindings()
  {
    try
    {
      return this.mDirtyFlags != 0L;
    }
    finally {}
  }
  
  public void invalidateAll()
  {
    try
    {
      this.mDirtyFlags = 2L;
      requestRebind();
      return;
    }
    finally {}
  }
  
  protected boolean onFieldChange(int paramInt1, Object paramObject, int paramInt2)
  {
    return false;
  }
  
  public void setPhoneNumber(PhoneNumber paramPhoneNumber)
  {
    this.mPhoneNumber = paramPhoneNumber;
    try
    {
      this.mDirtyFlags |= 1L;
      notifyPropertyChanged(BR.phoneNumber);
      super.requestRebind();
      return;
    }
    finally {}
  }
  
  public boolean setVariable(int paramInt, Object paramObject)
  {
    boolean bool;
    if (BR.phoneNumber == paramInt)
    {
      setPhoneNumber((PhoneNumber)paramObject);
      bool = true;
    }
    else
    {
      bool = false;
    }
    return bool;
  }
}

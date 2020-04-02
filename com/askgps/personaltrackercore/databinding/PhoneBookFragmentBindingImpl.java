package com.askgps.personaltrackercore.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding.IncludedLayouts;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.askgps.personaltrackercore.BR;
import com.askgps.personaltrackercore.R.id;
import com.askgps.personaltrackercore.binding.BindingHelperKt;
import com.askgps.personaltrackercore.ui.phonebook.PhoneBookViewModel;

public class PhoneBookFragmentBindingImpl
  extends PhoneBookFragmentBinding
{
  private static final ViewDataBinding.IncludedLayouts sIncludes;
  private static final SparseIntArray sViewsWithIds;
  private long mDirtyFlags = -1L;
  private final ConstraintLayout mboundView0;
  
  static
  {
    SparseIntArray localSparseIntArray = new SparseIntArray();
    sViewsWithIds = localSparseIntArray;
    localSparseIntArray.put(R.id.phone_book_btn_add, 2);
  }
  
  public PhoneBookFragmentBindingImpl(DataBindingComponent paramDataBindingComponent, View paramView)
  {
    this(paramDataBindingComponent, paramView, mapBindings(paramDataBindingComponent, paramView, 3, sIncludes, sViewsWithIds));
  }
  
  private PhoneBookFragmentBindingImpl(DataBindingComponent paramDataBindingComponent, View paramView, Object[] paramArrayOfObject)
  {
    super(paramDataBindingComponent, paramView, 0, (AppCompatButton)paramArrayOfObject[2], (RecyclerView)paramArrayOfObject[1]);
    paramDataBindingComponent = (ConstraintLayout)paramArrayOfObject[0];
    this.mboundView0 = paramDataBindingComponent;
    paramDataBindingComponent.setTag(null);
    this.recyclerView.setTag(null);
    setRootTag(paramView);
    invalidateAll();
  }
  
  protected void executeBindings()
  {
    try
    {
      long l = this.mDirtyFlags;
      this.mDirtyFlags = 0L;
      PhoneBookViewModel localPhoneBookViewModel = this.mVm;
      Object localObject1 = null;
      boolean bool = (l & 0x3) < 0L;
      Object localObject2 = localObject1;
      if (bool)
      {
        localObject2 = localObject1;
        if (localPhoneBookViewModel != null) {
          localObject2 = localPhoneBookViewModel.getAdapter();
        }
      }
      if (bool) {
        BindingHelperKt.setAdapter(this.recyclerView, (RecyclerView.Adapter)localObject2);
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
  
  public boolean setVariable(int paramInt, Object paramObject)
  {
    boolean bool;
    if (BR.vm == paramInt)
    {
      setVm((PhoneBookViewModel)paramObject);
      bool = true;
    }
    else
    {
      bool = false;
    }
    return bool;
  }
  
  public void setVm(PhoneBookViewModel paramPhoneBookViewModel)
  {
    this.mVm = paramPhoneBookViewModel;
    try
    {
      this.mDirtyFlags |= 1L;
      notifyPropertyChanged(BR.vm);
      super.requestRebind();
      return;
    }
    finally {}
  }
}

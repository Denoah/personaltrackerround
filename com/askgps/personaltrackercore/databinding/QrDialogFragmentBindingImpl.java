package com.askgps.personaltrackercore.databinding;

import android.graphics.Bitmap;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding.IncludedLayouts;
import com.askgps.personaltrackercore.BR;
import com.askgps.personaltrackercore.binding.BindingHelperKt;

public class QrDialogFragmentBindingImpl
  extends QrDialogFragmentBinding
{
  private static final ViewDataBinding.IncludedLayouts sIncludes;
  private static final SparseIntArray sViewsWithIds;
  private long mDirtyFlags = -1L;
  private final RelativeLayout mboundView0;
  
  public QrDialogFragmentBindingImpl(DataBindingComponent paramDataBindingComponent, View paramView)
  {
    this(paramDataBindingComponent, paramView, mapBindings(paramDataBindingComponent, paramView, 2, sIncludes, sViewsWithIds));
  }
  
  private QrDialogFragmentBindingImpl(DataBindingComponent paramDataBindingComponent, View paramView, Object[] paramArrayOfObject)
  {
    super(paramDataBindingComponent, paramView, 0, (AppCompatImageView)paramArrayOfObject[1]);
    paramDataBindingComponent = (RelativeLayout)paramArrayOfObject[0];
    this.mboundView0 = paramDataBindingComponent;
    paramDataBindingComponent.setTag(null);
    this.qrDialogImgQr.setTag(null);
    setRootTag(paramView);
    invalidateAll();
  }
  
  protected void executeBindings()
  {
    try
    {
      long l = this.mDirtyFlags;
      this.mDirtyFlags = 0L;
      Bitmap localBitmap = this.mQr;
      if ((l & 0x3) != 0L) {
        BindingHelperKt.loadImage(this.qrDialogImgQr, localBitmap);
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
  
  public void setQr(Bitmap paramBitmap)
  {
    this.mQr = paramBitmap;
    try
    {
      this.mDirtyFlags |= 1L;
      notifyPropertyChanged(BR.qr);
      super.requestRebind();
      return;
    }
    finally {}
  }
  
  public boolean setVariable(int paramInt, Object paramObject)
  {
    boolean bool;
    if (BR.qr == paramInt)
    {
      setQr((Bitmap)paramObject);
      bool = true;
    }
    else
    {
      bool = false;
    }
    return bool;
  }
}

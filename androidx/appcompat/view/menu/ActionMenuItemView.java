package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import androidx.appcompat.R.styleable;
import androidx.appcompat.widget.ActionMenuView.ActionMenuChildView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.ForwardingListener;
import androidx.appcompat.widget.TooltipCompat;

public class ActionMenuItemView
  extends AppCompatTextView
  implements MenuView.ItemView, View.OnClickListener, ActionMenuView.ActionMenuChildView
{
  private static final int MAX_ICON_SIZE = 32;
  private static final String TAG = "ActionMenuItemView";
  private boolean mAllowTextWithIcon;
  private boolean mExpandedFormat;
  private ForwardingListener mForwardingListener;
  private Drawable mIcon;
  MenuItemImpl mItemData;
  MenuBuilder.ItemInvoker mItemInvoker;
  private int mMaxIconSize;
  private int mMinWidth;
  PopupCallback mPopupCallback;
  private int mSavedPaddingLeft;
  private CharSequence mTitle;
  
  public ActionMenuItemView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ActionMenuItemView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ActionMenuItemView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    Resources localResources = paramContext.getResources();
    this.mAllowTextWithIcon = shouldAllowTextWithIcon();
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ActionMenuItemView, paramInt, 0);
    this.mMinWidth = paramContext.getDimensionPixelSize(R.styleable.ActionMenuItemView_android_minWidth, 0);
    paramContext.recycle();
    this.mMaxIconSize = ((int)(localResources.getDisplayMetrics().density * 32.0F + 0.5F));
    setOnClickListener(this);
    this.mSavedPaddingLeft = -1;
    setSaveEnabled(false);
  }
  
  private boolean shouldAllowTextWithIcon()
  {
    Configuration localConfiguration = getContext().getResources().getConfiguration();
    int i = localConfiguration.screenWidthDp;
    int j = localConfiguration.screenHeightDp;
    boolean bool;
    if ((i < 480) && ((i < 640) || (j < 480)) && (localConfiguration.orientation != 2)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private void updateTextButtonVisibility()
  {
    boolean bool = TextUtils.isEmpty(this.mTitle);
    int i = 1;
    int j = i;
    if (this.mIcon != null)
    {
      if (this.mItemData.showsTextAsAction())
      {
        j = i;
        if (this.mAllowTextWithIcon) {
          break label52;
        }
        if (this.mExpandedFormat)
        {
          j = i;
          break label52;
        }
      }
      j = 0;
    }
    label52:
    j = (bool ^ true) & j;
    Object localObject1 = null;
    if (j != 0) {
      localObject2 = this.mTitle;
    } else {
      localObject2 = null;
    }
    setText((CharSequence)localObject2);
    Object localObject2 = this.mItemData.getContentDescription();
    if (TextUtils.isEmpty((CharSequence)localObject2))
    {
      if (j != 0) {
        localObject2 = null;
      } else {
        localObject2 = this.mItemData.getTitle();
      }
      setContentDescription((CharSequence)localObject2);
    }
    else
    {
      setContentDescription((CharSequence)localObject2);
    }
    localObject2 = this.mItemData.getTooltipText();
    if (TextUtils.isEmpty((CharSequence)localObject2))
    {
      if (j != 0) {
        localObject2 = localObject1;
      } else {
        localObject2 = this.mItemData.getTitle();
      }
      TooltipCompat.setTooltipText(this, (CharSequence)localObject2);
    }
    else
    {
      TooltipCompat.setTooltipText(this, (CharSequence)localObject2);
    }
  }
  
  public MenuItemImpl getItemData()
  {
    return this.mItemData;
  }
  
  public boolean hasText()
  {
    return TextUtils.isEmpty(getText()) ^ true;
  }
  
  public void initialize(MenuItemImpl paramMenuItemImpl, int paramInt)
  {
    this.mItemData = paramMenuItemImpl;
    setIcon(paramMenuItemImpl.getIcon());
    setTitle(paramMenuItemImpl.getTitleForItemView(this));
    setId(paramMenuItemImpl.getItemId());
    if (paramMenuItemImpl.isVisible()) {
      paramInt = 0;
    } else {
      paramInt = 8;
    }
    setVisibility(paramInt);
    setEnabled(paramMenuItemImpl.isEnabled());
    if ((paramMenuItemImpl.hasSubMenu()) && (this.mForwardingListener == null)) {
      this.mForwardingListener = new ActionMenuItemForwardingListener();
    }
  }
  
  public boolean needsDividerAfter()
  {
    return hasText();
  }
  
  public boolean needsDividerBefore()
  {
    boolean bool;
    if ((hasText()) && (this.mItemData.getIcon() == null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void onClick(View paramView)
  {
    paramView = this.mItemInvoker;
    if (paramView != null) {
      paramView.invokeItem(this.mItemData);
    }
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    this.mAllowTextWithIcon = shouldAllowTextWithIcon();
    updateTextButtonVisibility();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    boolean bool = hasText();
    if (bool)
    {
      i = this.mSavedPaddingLeft;
      if (i >= 0) {
        super.setPadding(i, getPaddingTop(), getPaddingRight(), getPaddingBottom());
      }
    }
    super.onMeasure(paramInt1, paramInt2);
    int i = View.MeasureSpec.getMode(paramInt1);
    paramInt1 = View.MeasureSpec.getSize(paramInt1);
    int j = getMeasuredWidth();
    if (i == Integer.MIN_VALUE) {
      paramInt1 = Math.min(paramInt1, this.mMinWidth);
    } else {
      paramInt1 = this.mMinWidth;
    }
    if ((i != 1073741824) && (this.mMinWidth > 0) && (j < paramInt1)) {
      super.onMeasure(View.MeasureSpec.makeMeasureSpec(paramInt1, 1073741824), paramInt2);
    }
    if ((!bool) && (this.mIcon != null)) {
      super.setPadding((getMeasuredWidth() - this.mIcon.getBounds().width()) / 2, getPaddingTop(), getPaddingRight(), getPaddingBottom());
    }
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    super.onRestoreInstanceState(null);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.mItemData.hasSubMenu())
    {
      ForwardingListener localForwardingListener = this.mForwardingListener;
      if ((localForwardingListener != null) && (localForwardingListener.onTouch(this, paramMotionEvent))) {
        return true;
      }
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public boolean prefersCondensedTitle()
  {
    return true;
  }
  
  public void setCheckable(boolean paramBoolean) {}
  
  public void setChecked(boolean paramBoolean) {}
  
  public void setExpandedFormat(boolean paramBoolean)
  {
    if (this.mExpandedFormat != paramBoolean)
    {
      this.mExpandedFormat = paramBoolean;
      MenuItemImpl localMenuItemImpl = this.mItemData;
      if (localMenuItemImpl != null) {
        localMenuItemImpl.actionFormatChanged();
      }
    }
  }
  
  public void setIcon(Drawable paramDrawable)
  {
    this.mIcon = paramDrawable;
    if (paramDrawable != null)
    {
      int i = paramDrawable.getIntrinsicWidth();
      int j = paramDrawable.getIntrinsicHeight();
      int k = this.mMaxIconSize;
      int m = i;
      int n = j;
      float f;
      if (i > k)
      {
        f = k / i;
        n = (int)(j * f);
        m = k;
      }
      i = this.mMaxIconSize;
      j = m;
      k = n;
      if (n > i)
      {
        f = i / n;
        j = (int)(m * f);
        k = i;
      }
      paramDrawable.setBounds(0, 0, j, k);
    }
    setCompoundDrawables(paramDrawable, null, null, null);
    updateTextButtonVisibility();
  }
  
  public void setItemInvoker(MenuBuilder.ItemInvoker paramItemInvoker)
  {
    this.mItemInvoker = paramItemInvoker;
  }
  
  public void setPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mSavedPaddingLeft = paramInt1;
    super.setPadding(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void setPopupCallback(PopupCallback paramPopupCallback)
  {
    this.mPopupCallback = paramPopupCallback;
  }
  
  public void setShortcut(boolean paramBoolean, char paramChar) {}
  
  public void setTitle(CharSequence paramCharSequence)
  {
    this.mTitle = paramCharSequence;
    updateTextButtonVisibility();
  }
  
  public boolean showsIcon()
  {
    return true;
  }
  
  private class ActionMenuItemForwardingListener
    extends ForwardingListener
  {
    public ActionMenuItemForwardingListener()
    {
      super();
    }
    
    public ShowableListMenu getPopup()
    {
      if (ActionMenuItemView.this.mPopupCallback != null) {
        return ActionMenuItemView.this.mPopupCallback.getPopup();
      }
      return null;
    }
    
    protected boolean onForwardingStarted()
    {
      Object localObject = ActionMenuItemView.this.mItemInvoker;
      boolean bool1 = false;
      boolean bool2 = bool1;
      if (localObject != null)
      {
        bool2 = bool1;
        if (ActionMenuItemView.this.mItemInvoker.invokeItem(ActionMenuItemView.this.mItemData))
        {
          localObject = getPopup();
          bool2 = bool1;
          if (localObject != null)
          {
            bool2 = bool1;
            if (((ShowableListMenu)localObject).isShowing()) {
              bool2 = true;
            }
          }
        }
      }
      return bool2;
    }
  }
  
  public static abstract class PopupCallback
  {
    public PopupCallback() {}
    
    public abstract ShowableListMenu getPopup();
  }
}

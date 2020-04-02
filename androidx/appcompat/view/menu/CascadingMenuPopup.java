package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import androidx.appcompat.R.dimen;
import androidx.appcompat.R.layout;
import androidx.appcompat.widget.MenuItemHoverListener;
import androidx.appcompat.widget.MenuPopupWindow;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class CascadingMenuPopup
  extends MenuPopup
  implements MenuPresenter, View.OnKeyListener, PopupWindow.OnDismissListener
{
  static final int HORIZ_POSITION_LEFT = 0;
  static final int HORIZ_POSITION_RIGHT = 1;
  private static final int ITEM_LAYOUT = R.layout.abc_cascading_menu_item_layout;
  static final int SUBMENU_TIMEOUT_MS = 200;
  private View mAnchorView;
  private final View.OnAttachStateChangeListener mAttachStateChangeListener = new View.OnAttachStateChangeListener()
  {
    public void onViewAttachedToWindow(View paramAnonymousView) {}
    
    public void onViewDetachedFromWindow(View paramAnonymousView)
    {
      if (CascadingMenuPopup.this.mTreeObserver != null)
      {
        if (!CascadingMenuPopup.this.mTreeObserver.isAlive()) {
          CascadingMenuPopup.this.mTreeObserver = paramAnonymousView.getViewTreeObserver();
        }
        CascadingMenuPopup.this.mTreeObserver.removeGlobalOnLayoutListener(CascadingMenuPopup.this.mGlobalLayoutListener);
      }
      paramAnonymousView.removeOnAttachStateChangeListener(this);
    }
  };
  private final Context mContext;
  private int mDropDownGravity = 0;
  private boolean mForceShowIcon;
  final ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener()
  {
    public void onGlobalLayout()
    {
      if ((CascadingMenuPopup.this.isShowing()) && (CascadingMenuPopup.this.mShowingMenus.size() > 0) && (!((CascadingMenuPopup.CascadingMenuInfo)CascadingMenuPopup.this.mShowingMenus.get(0)).window.isModal()))
      {
        Object localObject = CascadingMenuPopup.this.mShownAnchorView;
        if ((localObject != null) && (((View)localObject).isShown())) {
          localObject = CascadingMenuPopup.this.mShowingMenus.iterator();
        }
        while (((Iterator)localObject).hasNext())
        {
          ((CascadingMenuPopup.CascadingMenuInfo)((Iterator)localObject).next()).window.show();
          continue;
          CascadingMenuPopup.this.dismiss();
        }
      }
    }
  };
  private boolean mHasXOffset;
  private boolean mHasYOffset;
  private int mLastPosition;
  private final MenuItemHoverListener mMenuItemHoverListener = new MenuItemHoverListener()
  {
    public void onItemHoverEnter(final MenuBuilder paramAnonymousMenuBuilder, final MenuItem paramAnonymousMenuItem)
    {
      Handler localHandler = CascadingMenuPopup.this.mSubMenuHoverHandler;
      final CascadingMenuPopup.CascadingMenuInfo localCascadingMenuInfo = null;
      localHandler.removeCallbacksAndMessages(null);
      int i = CascadingMenuPopup.this.mShowingMenus.size();
      for (int j = 0; j < i; j++) {
        if (paramAnonymousMenuBuilder == ((CascadingMenuPopup.CascadingMenuInfo)CascadingMenuPopup.this.mShowingMenus.get(j)).menu) {
          break label76;
        }
      }
      j = -1;
      label76:
      if (j == -1) {
        return;
      }
      j++;
      if (j < CascadingMenuPopup.this.mShowingMenus.size()) {
        localCascadingMenuInfo = (CascadingMenuPopup.CascadingMenuInfo)CascadingMenuPopup.this.mShowingMenus.get(j);
      }
      paramAnonymousMenuItem = new Runnable()
      {
        public void run()
        {
          if (localCascadingMenuInfo != null)
          {
            CascadingMenuPopup.this.mShouldCloseImmediately = true;
            localCascadingMenuInfo.menu.close(false);
            CascadingMenuPopup.this.mShouldCloseImmediately = false;
          }
          if ((paramAnonymousMenuItem.isEnabled()) && (paramAnonymousMenuItem.hasSubMenu())) {
            paramAnonymousMenuBuilder.performItemAction(paramAnonymousMenuItem, 4);
          }
        }
      };
      long l = SystemClock.uptimeMillis();
      CascadingMenuPopup.this.mSubMenuHoverHandler.postAtTime(paramAnonymousMenuItem, paramAnonymousMenuBuilder, l + 200L);
    }
    
    public void onItemHoverExit(MenuBuilder paramAnonymousMenuBuilder, MenuItem paramAnonymousMenuItem)
    {
      CascadingMenuPopup.this.mSubMenuHoverHandler.removeCallbacksAndMessages(paramAnonymousMenuBuilder);
    }
  };
  private final int mMenuMaxWidth;
  private PopupWindow.OnDismissListener mOnDismissListener;
  private final boolean mOverflowOnly;
  private final List<MenuBuilder> mPendingMenus = new ArrayList();
  private final int mPopupStyleAttr;
  private final int mPopupStyleRes;
  private MenuPresenter.Callback mPresenterCallback;
  private int mRawDropDownGravity = 0;
  boolean mShouldCloseImmediately;
  private boolean mShowTitle;
  final List<CascadingMenuInfo> mShowingMenus = new ArrayList();
  View mShownAnchorView;
  final Handler mSubMenuHoverHandler;
  ViewTreeObserver mTreeObserver;
  private int mXOffset;
  private int mYOffset;
  
  public CascadingMenuPopup(Context paramContext, View paramView, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    this.mContext = paramContext;
    this.mAnchorView = paramView;
    this.mPopupStyleAttr = paramInt1;
    this.mPopupStyleRes = paramInt2;
    this.mOverflowOnly = paramBoolean;
    this.mForceShowIcon = false;
    this.mLastPosition = getInitialMenuPosition();
    paramContext = paramContext.getResources();
    this.mMenuMaxWidth = Math.max(paramContext.getDisplayMetrics().widthPixels / 2, paramContext.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
    this.mSubMenuHoverHandler = new Handler();
  }
  
  private MenuPopupWindow createPopupWindow()
  {
    MenuPopupWindow localMenuPopupWindow = new MenuPopupWindow(this.mContext, null, this.mPopupStyleAttr, this.mPopupStyleRes);
    localMenuPopupWindow.setHoverListener(this.mMenuItemHoverListener);
    localMenuPopupWindow.setOnItemClickListener(this);
    localMenuPopupWindow.setOnDismissListener(this);
    localMenuPopupWindow.setAnchorView(this.mAnchorView);
    localMenuPopupWindow.setDropDownGravity(this.mDropDownGravity);
    localMenuPopupWindow.setModal(true);
    localMenuPopupWindow.setInputMethodMode(2);
    return localMenuPopupWindow;
  }
  
  private int findIndexOfAddedMenu(MenuBuilder paramMenuBuilder)
  {
    int i = this.mShowingMenus.size();
    for (int j = 0; j < i; j++) {
      if (paramMenuBuilder == ((CascadingMenuInfo)this.mShowingMenus.get(j)).menu) {
        return j;
      }
    }
    return -1;
  }
  
  private MenuItem findMenuItemForSubmenu(MenuBuilder paramMenuBuilder1, MenuBuilder paramMenuBuilder2)
  {
    int i = paramMenuBuilder1.size();
    for (int j = 0; j < i; j++)
    {
      MenuItem localMenuItem = paramMenuBuilder1.getItem(j);
      if ((localMenuItem.hasSubMenu()) && (paramMenuBuilder2 == localMenuItem.getSubMenu())) {
        return localMenuItem;
      }
    }
    return null;
  }
  
  private View findParentViewForSubmenu(CascadingMenuInfo paramCascadingMenuInfo, MenuBuilder paramMenuBuilder)
  {
    paramMenuBuilder = findMenuItemForSubmenu(paramCascadingMenuInfo.menu, paramMenuBuilder);
    if (paramMenuBuilder == null) {
      return null;
    }
    ListView localListView = paramCascadingMenuInfo.getListView();
    paramCascadingMenuInfo = localListView.getAdapter();
    boolean bool = paramCascadingMenuInfo instanceof HeaderViewListAdapter;
    int i = 0;
    int j;
    if (bool)
    {
      paramCascadingMenuInfo = (HeaderViewListAdapter)paramCascadingMenuInfo;
      j = paramCascadingMenuInfo.getHeadersCount();
      paramCascadingMenuInfo = (MenuAdapter)paramCascadingMenuInfo.getWrappedAdapter();
    }
    else
    {
      paramCascadingMenuInfo = (MenuAdapter)paramCascadingMenuInfo;
      j = 0;
    }
    int k = paramCascadingMenuInfo.getCount();
    while (i < k)
    {
      if (paramMenuBuilder == paramCascadingMenuInfo.getItem(i)) {
        break label105;
      }
      i++;
    }
    i = -1;
    label105:
    if (i == -1) {
      return null;
    }
    i = i + j - localListView.getFirstVisiblePosition();
    if ((i >= 0) && (i < localListView.getChildCount())) {
      return localListView.getChildAt(i);
    }
    return null;
  }
  
  private int getInitialMenuPosition()
  {
    int i = ViewCompat.getLayoutDirection(this.mAnchorView);
    int j = 1;
    if (i == 1) {
      j = 0;
    }
    return j;
  }
  
  private int getNextMenuPosition(int paramInt)
  {
    Object localObject = this.mShowingMenus;
    ListView localListView = ((CascadingMenuInfo)((List)localObject).get(((List)localObject).size() - 1)).getListView();
    int[] arrayOfInt = new int[2];
    localListView.getLocationOnScreen(arrayOfInt);
    localObject = new Rect();
    this.mShownAnchorView.getWindowVisibleDisplayFrame((Rect)localObject);
    if (this.mLastPosition == 1)
    {
      if (arrayOfInt[0] + localListView.getWidth() + paramInt > ((Rect)localObject).right) {
        return 0;
      }
      return 1;
    }
    if (arrayOfInt[0] - paramInt < 0) {
      return 1;
    }
    return 0;
  }
  
  private void showMenu(MenuBuilder paramMenuBuilder)
  {
    Object localObject1 = LayoutInflater.from(this.mContext);
    Object localObject2 = new MenuAdapter(paramMenuBuilder, (LayoutInflater)localObject1, this.mOverflowOnly, ITEM_LAYOUT);
    if ((!isShowing()) && (this.mForceShowIcon)) {
      ((MenuAdapter)localObject2).setForceShowIcon(true);
    } else if (isShowing()) {
      ((MenuAdapter)localObject2).setForceShowIcon(MenuPopup.shouldPreserveIconSpacing(paramMenuBuilder));
    }
    int i = measureIndividualMenuWidth((ListAdapter)localObject2, null, this.mContext, this.mMenuMaxWidth);
    MenuPopupWindow localMenuPopupWindow = createPopupWindow();
    localMenuPopupWindow.setAdapter((ListAdapter)localObject2);
    localMenuPopupWindow.setContentWidth(i);
    localMenuPopupWindow.setDropDownGravity(this.mDropDownGravity);
    if (this.mShowingMenus.size() > 0)
    {
      localObject2 = this.mShowingMenus;
      localObject2 = (CascadingMenuInfo)((List)localObject2).get(((List)localObject2).size() - 1);
      localObject3 = findParentViewForSubmenu((CascadingMenuInfo)localObject2, paramMenuBuilder);
    }
    else
    {
      localObject2 = null;
      localObject3 = localObject2;
    }
    if (localObject3 != null)
    {
      localMenuPopupWindow.setTouchModal(false);
      localMenuPopupWindow.setEnterTransition(null);
      int j = getNextMenuPosition(i);
      int k;
      if (j == 1) {
        k = 1;
      } else {
        k = 0;
      }
      this.mLastPosition = j;
      int m;
      if (Build.VERSION.SDK_INT >= 26)
      {
        localMenuPopupWindow.setAnchorView((View)localObject3);
        j = 0;
        m = j;
      }
      else
      {
        int[] arrayOfInt1 = new int[2];
        this.mAnchorView.getLocationOnScreen(arrayOfInt1);
        int[] arrayOfInt2 = new int[2];
        ((View)localObject3).getLocationOnScreen(arrayOfInt2);
        if ((this.mDropDownGravity & 0x7) == 5)
        {
          arrayOfInt1[0] += this.mAnchorView.getWidth();
          arrayOfInt2[0] += ((View)localObject3).getWidth();
        }
        m = arrayOfInt2[0] - arrayOfInt1[0];
        j = arrayOfInt2[1] - arrayOfInt1[1];
      }
      if ((this.mDropDownGravity & 0x5) == 5)
      {
        if (k == 0)
        {
          i = ((View)localObject3).getWidth();
          break label366;
        }
      }
      else
      {
        if (k == 0) {
          break label366;
        }
        i = ((View)localObject3).getWidth();
      }
      i = m + i;
      break label373;
      label366:
      i = m - i;
      label373:
      localMenuPopupWindow.setHorizontalOffset(i);
      localMenuPopupWindow.setOverlapAnchor(true);
      localMenuPopupWindow.setVerticalOffset(j);
    }
    else
    {
      if (this.mHasXOffset) {
        localMenuPopupWindow.setHorizontalOffset(this.mXOffset);
      }
      if (this.mHasYOffset) {
        localMenuPopupWindow.setVerticalOffset(this.mYOffset);
      }
      localMenuPopupWindow.setEpicenterBounds(getEpicenterBounds());
    }
    Object localObject3 = new CascadingMenuInfo(localMenuPopupWindow, paramMenuBuilder, this.mLastPosition);
    this.mShowingMenus.add(localObject3);
    localMenuPopupWindow.show();
    localObject3 = localMenuPopupWindow.getListView();
    ((ListView)localObject3).setOnKeyListener(this);
    if ((localObject2 == null) && (this.mShowTitle) && (paramMenuBuilder.getHeaderTitle() != null))
    {
      localObject1 = (FrameLayout)((LayoutInflater)localObject1).inflate(R.layout.abc_popup_menu_header_item_layout, (ViewGroup)localObject3, false);
      localObject2 = (TextView)((FrameLayout)localObject1).findViewById(16908310);
      ((FrameLayout)localObject1).setEnabled(false);
      ((TextView)localObject2).setText(paramMenuBuilder.getHeaderTitle());
      ((ListView)localObject3).addHeaderView((View)localObject1, null, false);
      localMenuPopupWindow.show();
    }
  }
  
  public void addMenu(MenuBuilder paramMenuBuilder)
  {
    paramMenuBuilder.addMenuPresenter(this, this.mContext);
    if (isShowing()) {
      showMenu(paramMenuBuilder);
    } else {
      this.mPendingMenus.add(paramMenuBuilder);
    }
  }
  
  protected boolean closeMenuOnSubMenuOpened()
  {
    return false;
  }
  
  public void dismiss()
  {
    int i = this.mShowingMenus.size();
    if (i > 0)
    {
      CascadingMenuInfo[] arrayOfCascadingMenuInfo = (CascadingMenuInfo[])this.mShowingMenus.toArray(new CascadingMenuInfo[i]);
      i--;
      while (i >= 0)
      {
        CascadingMenuInfo localCascadingMenuInfo = arrayOfCascadingMenuInfo[i];
        if (localCascadingMenuInfo.window.isShowing()) {
          localCascadingMenuInfo.window.dismiss();
        }
        i--;
      }
    }
  }
  
  public boolean flagActionItems()
  {
    return false;
  }
  
  public ListView getListView()
  {
    Object localObject;
    if (this.mShowingMenus.isEmpty())
    {
      localObject = null;
    }
    else
    {
      localObject = this.mShowingMenus;
      localObject = ((CascadingMenuInfo)((List)localObject).get(((List)localObject).size() - 1)).getListView();
    }
    return localObject;
  }
  
  public boolean isShowing()
  {
    int i = this.mShowingMenus.size();
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (i > 0)
    {
      bool2 = bool1;
      if (((CascadingMenuInfo)this.mShowingMenus.get(0)).window.isShowing()) {
        bool2 = true;
      }
    }
    return bool2;
  }
  
  public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
  {
    int i = findIndexOfAddedMenu(paramMenuBuilder);
    if (i < 0) {
      return;
    }
    int j = i + 1;
    if (j < this.mShowingMenus.size()) {
      ((CascadingMenuInfo)this.mShowingMenus.get(j)).menu.close(false);
    }
    Object localObject = (CascadingMenuInfo)this.mShowingMenus.remove(i);
    ((CascadingMenuInfo)localObject).menu.removeMenuPresenter(this);
    if (this.mShouldCloseImmediately)
    {
      ((CascadingMenuInfo)localObject).window.setExitTransition(null);
      ((CascadingMenuInfo)localObject).window.setAnimationStyle(0);
    }
    ((CascadingMenuInfo)localObject).window.dismiss();
    j = this.mShowingMenus.size();
    if (j > 0) {
      this.mLastPosition = ((CascadingMenuInfo)this.mShowingMenus.get(j - 1)).position;
    } else {
      this.mLastPosition = getInitialMenuPosition();
    }
    if (j == 0)
    {
      dismiss();
      localObject = this.mPresenterCallback;
      if (localObject != null) {
        ((MenuPresenter.Callback)localObject).onCloseMenu(paramMenuBuilder, true);
      }
      paramMenuBuilder = this.mTreeObserver;
      if (paramMenuBuilder != null)
      {
        if (paramMenuBuilder.isAlive()) {
          this.mTreeObserver.removeGlobalOnLayoutListener(this.mGlobalLayoutListener);
        }
        this.mTreeObserver = null;
      }
      this.mShownAnchorView.removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
      this.mOnDismissListener.onDismiss();
    }
    else if (paramBoolean)
    {
      ((CascadingMenuInfo)this.mShowingMenus.get(0)).menu.close(false);
    }
  }
  
  public void onDismiss()
  {
    int i = this.mShowingMenus.size();
    for (int j = 0; j < i; j++)
    {
      localCascadingMenuInfo = (CascadingMenuInfo)this.mShowingMenus.get(j);
      if (!localCascadingMenuInfo.window.isShowing()) {
        break label52;
      }
    }
    CascadingMenuInfo localCascadingMenuInfo = null;
    label52:
    if (localCascadingMenuInfo != null) {
      localCascadingMenuInfo.menu.close(false);
    }
  }
  
  public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramKeyEvent.getAction() == 1) && (paramInt == 82))
    {
      dismiss();
      return true;
    }
    return false;
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable) {}
  
  public Parcelable onSaveInstanceState()
  {
    return null;
  }
  
  public boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder)
  {
    Iterator localIterator = this.mShowingMenus.iterator();
    Object localObject;
    while (localIterator.hasNext())
    {
      localObject = (CascadingMenuInfo)localIterator.next();
      if (paramSubMenuBuilder == ((CascadingMenuInfo)localObject).menu)
      {
        ((CascadingMenuInfo)localObject).getListView().requestFocus();
        return true;
      }
    }
    if (paramSubMenuBuilder.hasVisibleItems())
    {
      addMenu(paramSubMenuBuilder);
      localObject = this.mPresenterCallback;
      if (localObject != null) {
        ((MenuPresenter.Callback)localObject).onOpenSubMenu(paramSubMenuBuilder);
      }
      return true;
    }
    return false;
  }
  
  public void setAnchorView(View paramView)
  {
    if (this.mAnchorView != paramView)
    {
      this.mAnchorView = paramView;
      this.mDropDownGravity = GravityCompat.getAbsoluteGravity(this.mRawDropDownGravity, ViewCompat.getLayoutDirection(paramView));
    }
  }
  
  public void setCallback(MenuPresenter.Callback paramCallback)
  {
    this.mPresenterCallback = paramCallback;
  }
  
  public void setForceShowIcon(boolean paramBoolean)
  {
    this.mForceShowIcon = paramBoolean;
  }
  
  public void setGravity(int paramInt)
  {
    if (this.mRawDropDownGravity != paramInt)
    {
      this.mRawDropDownGravity = paramInt;
      this.mDropDownGravity = GravityCompat.getAbsoluteGravity(paramInt, ViewCompat.getLayoutDirection(this.mAnchorView));
    }
  }
  
  public void setHorizontalOffset(int paramInt)
  {
    this.mHasXOffset = true;
    this.mXOffset = paramInt;
  }
  
  public void setOnDismissListener(PopupWindow.OnDismissListener paramOnDismissListener)
  {
    this.mOnDismissListener = paramOnDismissListener;
  }
  
  public void setShowTitle(boolean paramBoolean)
  {
    this.mShowTitle = paramBoolean;
  }
  
  public void setVerticalOffset(int paramInt)
  {
    this.mHasYOffset = true;
    this.mYOffset = paramInt;
  }
  
  public void show()
  {
    if (isShowing()) {
      return;
    }
    Object localObject = this.mPendingMenus.iterator();
    while (((Iterator)localObject).hasNext()) {
      showMenu((MenuBuilder)((Iterator)localObject).next());
    }
    this.mPendingMenus.clear();
    localObject = this.mAnchorView;
    this.mShownAnchorView = ((View)localObject);
    if (localObject != null)
    {
      int i;
      if (this.mTreeObserver == null) {
        i = 1;
      } else {
        i = 0;
      }
      localObject = this.mShownAnchorView.getViewTreeObserver();
      this.mTreeObserver = ((ViewTreeObserver)localObject);
      if (i != 0) {
        ((ViewTreeObserver)localObject).addOnGlobalLayoutListener(this.mGlobalLayoutListener);
      }
      this.mShownAnchorView.addOnAttachStateChangeListener(this.mAttachStateChangeListener);
    }
  }
  
  public void updateMenuView(boolean paramBoolean)
  {
    Iterator localIterator = this.mShowingMenus.iterator();
    while (localIterator.hasNext()) {
      toMenuAdapter(((CascadingMenuInfo)localIterator.next()).getListView().getAdapter()).notifyDataSetChanged();
    }
  }
  
  private static class CascadingMenuInfo
  {
    public final MenuBuilder menu;
    public final int position;
    public final MenuPopupWindow window;
    
    public CascadingMenuInfo(MenuPopupWindow paramMenuPopupWindow, MenuBuilder paramMenuBuilder, int paramInt)
    {
      this.window = paramMenuPopupWindow;
      this.menu = paramMenuBuilder;
      this.position = paramInt;
    }
    
    public ListView getListView()
    {
      return this.window.getListView();
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface HorizPosition {}
}

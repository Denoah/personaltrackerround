package androidx.appcompat.app;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window.Callback;
import android.widget.SpinnerAdapter;
import androidx.appcompat.view.WindowCallbackWrapper;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuBuilder.Callback;
import androidx.appcompat.view.menu.MenuPresenter.Callback;
import androidx.appcompat.widget.DecorToolbar;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.core.view.ViewCompat;
import java.util.ArrayList;

class ToolbarActionBar
  extends ActionBar
{
  DecorToolbar mDecorToolbar;
  private boolean mLastMenuVisibility;
  private boolean mMenuCallbackSet;
  private final Toolbar.OnMenuItemClickListener mMenuClicker = new Toolbar.OnMenuItemClickListener()
  {
    public boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
    {
      return ToolbarActionBar.this.mWindowCallback.onMenuItemSelected(0, paramAnonymousMenuItem);
    }
  };
  private final Runnable mMenuInvalidator = new Runnable()
  {
    public void run()
    {
      ToolbarActionBar.this.populateOptionsMenu();
    }
  };
  private ArrayList<ActionBar.OnMenuVisibilityListener> mMenuVisibilityListeners = new ArrayList();
  boolean mToolbarMenuPrepared;
  Window.Callback mWindowCallback;
  
  ToolbarActionBar(Toolbar paramToolbar, CharSequence paramCharSequence, Window.Callback paramCallback)
  {
    this.mDecorToolbar = new ToolbarWidgetWrapper(paramToolbar, false);
    paramCallback = new ToolbarCallbackWrapper(paramCallback);
    this.mWindowCallback = paramCallback;
    this.mDecorToolbar.setWindowCallback(paramCallback);
    paramToolbar.setOnMenuItemClickListener(this.mMenuClicker);
    this.mDecorToolbar.setWindowTitle(paramCharSequence);
  }
  
  private Menu getMenu()
  {
    if (!this.mMenuCallbackSet)
    {
      this.mDecorToolbar.setMenuCallbacks(new ActionMenuPresenterCallback(), new MenuBuilderCallback());
      this.mMenuCallbackSet = true;
    }
    return this.mDecorToolbar.getMenu();
  }
  
  public void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener paramOnMenuVisibilityListener)
  {
    this.mMenuVisibilityListeners.add(paramOnMenuVisibilityListener);
  }
  
  public void addTab(ActionBar.Tab paramTab)
  {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }
  
  public void addTab(ActionBar.Tab paramTab, int paramInt)
  {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }
  
  public void addTab(ActionBar.Tab paramTab, int paramInt, boolean paramBoolean)
  {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }
  
  public void addTab(ActionBar.Tab paramTab, boolean paramBoolean)
  {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }
  
  public boolean closeOptionsMenu()
  {
    return this.mDecorToolbar.hideOverflowMenu();
  }
  
  public boolean collapseActionView()
  {
    if (this.mDecorToolbar.hasExpandedActionView())
    {
      this.mDecorToolbar.collapseActionView();
      return true;
    }
    return false;
  }
  
  public void dispatchMenuVisibilityChanged(boolean paramBoolean)
  {
    if (paramBoolean == this.mLastMenuVisibility) {
      return;
    }
    this.mLastMenuVisibility = paramBoolean;
    int i = this.mMenuVisibilityListeners.size();
    for (int j = 0; j < i; j++) {
      ((ActionBar.OnMenuVisibilityListener)this.mMenuVisibilityListeners.get(j)).onMenuVisibilityChanged(paramBoolean);
    }
  }
  
  public View getCustomView()
  {
    return this.mDecorToolbar.getCustomView();
  }
  
  public int getDisplayOptions()
  {
    return this.mDecorToolbar.getDisplayOptions();
  }
  
  public float getElevation()
  {
    return ViewCompat.getElevation(this.mDecorToolbar.getViewGroup());
  }
  
  public int getHeight()
  {
    return this.mDecorToolbar.getHeight();
  }
  
  public int getNavigationItemCount()
  {
    return 0;
  }
  
  public int getNavigationMode()
  {
    return 0;
  }
  
  public int getSelectedNavigationIndex()
  {
    return -1;
  }
  
  public ActionBar.Tab getSelectedTab()
  {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }
  
  public CharSequence getSubtitle()
  {
    return this.mDecorToolbar.getSubtitle();
  }
  
  public ActionBar.Tab getTabAt(int paramInt)
  {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }
  
  public int getTabCount()
  {
    return 0;
  }
  
  public Context getThemedContext()
  {
    return this.mDecorToolbar.getContext();
  }
  
  public CharSequence getTitle()
  {
    return this.mDecorToolbar.getTitle();
  }
  
  public Window.Callback getWrappedWindowCallback()
  {
    return this.mWindowCallback;
  }
  
  public void hide()
  {
    this.mDecorToolbar.setVisibility(8);
  }
  
  public boolean invalidateOptionsMenu()
  {
    this.mDecorToolbar.getViewGroup().removeCallbacks(this.mMenuInvalidator);
    ViewCompat.postOnAnimation(this.mDecorToolbar.getViewGroup(), this.mMenuInvalidator);
    return true;
  }
  
  public boolean isShowing()
  {
    boolean bool;
    if (this.mDecorToolbar.getVisibility() == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isTitleTruncated()
  {
    return super.isTitleTruncated();
  }
  
  public ActionBar.Tab newTab()
  {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }
  
  void onDestroy()
  {
    this.mDecorToolbar.getViewGroup().removeCallbacks(this.mMenuInvalidator);
  }
  
  public boolean onKeyShortcut(int paramInt, KeyEvent paramKeyEvent)
  {
    Menu localMenu = getMenu();
    if (localMenu != null)
    {
      if (paramKeyEvent != null) {
        i = paramKeyEvent.getDeviceId();
      } else {
        i = -1;
      }
      int i = KeyCharacterMap.load(i).getKeyboardType();
      boolean bool = true;
      if (i == 1) {
        bool = false;
      }
      localMenu.setQwertyMode(bool);
      return localMenu.performShortcut(paramInt, paramKeyEvent, 0);
    }
    return false;
  }
  
  public boolean onMenuKeyEvent(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() == 1) {
      openOptionsMenu();
    }
    return true;
  }
  
  public boolean openOptionsMenu()
  {
    return this.mDecorToolbar.showOverflowMenu();
  }
  
  void populateOptionsMenu()
  {
    Menu localMenu = getMenu();
    MenuBuilder localMenuBuilder;
    if ((localMenu instanceof MenuBuilder)) {
      localMenuBuilder = (MenuBuilder)localMenu;
    } else {
      localMenuBuilder = null;
    }
    if (localMenuBuilder != null) {
      localMenuBuilder.stopDispatchingItemsChanged();
    }
    try
    {
      localMenu.clear();
      if ((!this.mWindowCallback.onCreatePanelMenu(0, localMenu)) || (!this.mWindowCallback.onPreparePanel(0, null, localMenu))) {
        localMenu.clear();
      }
      return;
    }
    finally
    {
      if (localMenuBuilder != null) {
        localMenuBuilder.startDispatchingItemsChanged();
      }
    }
  }
  
  public void removeAllTabs()
  {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }
  
  public void removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener paramOnMenuVisibilityListener)
  {
    this.mMenuVisibilityListeners.remove(paramOnMenuVisibilityListener);
  }
  
  public void removeTab(ActionBar.Tab paramTab)
  {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }
  
  public void removeTabAt(int paramInt)
  {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }
  
  public boolean requestFocus()
  {
    ViewGroup localViewGroup = this.mDecorToolbar.getViewGroup();
    if ((localViewGroup != null) && (!localViewGroup.hasFocus()))
    {
      localViewGroup.requestFocus();
      return true;
    }
    return false;
  }
  
  public void selectTab(ActionBar.Tab paramTab)
  {
    throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
  }
  
  public void setBackgroundDrawable(Drawable paramDrawable)
  {
    this.mDecorToolbar.setBackgroundDrawable(paramDrawable);
  }
  
  public void setCustomView(int paramInt)
  {
    setCustomView(LayoutInflater.from(this.mDecorToolbar.getContext()).inflate(paramInt, this.mDecorToolbar.getViewGroup(), false));
  }
  
  public void setCustomView(View paramView)
  {
    setCustomView(paramView, new ActionBar.LayoutParams(-2, -2));
  }
  
  public void setCustomView(View paramView, ActionBar.LayoutParams paramLayoutParams)
  {
    if (paramView != null) {
      paramView.setLayoutParams(paramLayoutParams);
    }
    this.mDecorToolbar.setCustomView(paramView);
  }
  
  public void setDefaultDisplayHomeAsUpEnabled(boolean paramBoolean) {}
  
  public void setDisplayHomeAsUpEnabled(boolean paramBoolean)
  {
    int i;
    if (paramBoolean) {
      i = 4;
    } else {
      i = 0;
    }
    setDisplayOptions(i, 4);
  }
  
  public void setDisplayOptions(int paramInt)
  {
    setDisplayOptions(paramInt, -1);
  }
  
  public void setDisplayOptions(int paramInt1, int paramInt2)
  {
    int i = this.mDecorToolbar.getDisplayOptions();
    this.mDecorToolbar.setDisplayOptions(paramInt1 & paramInt2 | paramInt2 & i);
  }
  
  public void setDisplayShowCustomEnabled(boolean paramBoolean)
  {
    int i;
    if (paramBoolean) {
      i = 16;
    } else {
      i = 0;
    }
    setDisplayOptions(i, 16);
  }
  
  public void setDisplayShowHomeEnabled(boolean paramBoolean)
  {
    int i;
    if (paramBoolean) {
      i = 2;
    } else {
      i = 0;
    }
    setDisplayOptions(i, 2);
  }
  
  public void setDisplayShowTitleEnabled(boolean paramBoolean)
  {
    int i;
    if (paramBoolean) {
      i = 8;
    } else {
      i = 0;
    }
    setDisplayOptions(i, 8);
  }
  
  public void setDisplayUseLogoEnabled(boolean paramBoolean)
  {
    setDisplayOptions(paramBoolean, 1);
  }
  
  public void setElevation(float paramFloat)
  {
    ViewCompat.setElevation(this.mDecorToolbar.getViewGroup(), paramFloat);
  }
  
  public void setHomeActionContentDescription(int paramInt)
  {
    this.mDecorToolbar.setNavigationContentDescription(paramInt);
  }
  
  public void setHomeActionContentDescription(CharSequence paramCharSequence)
  {
    this.mDecorToolbar.setNavigationContentDescription(paramCharSequence);
  }
  
  public void setHomeAsUpIndicator(int paramInt)
  {
    this.mDecorToolbar.setNavigationIcon(paramInt);
  }
  
  public void setHomeAsUpIndicator(Drawable paramDrawable)
  {
    this.mDecorToolbar.setNavigationIcon(paramDrawable);
  }
  
  public void setHomeButtonEnabled(boolean paramBoolean) {}
  
  public void setIcon(int paramInt)
  {
    this.mDecorToolbar.setIcon(paramInt);
  }
  
  public void setIcon(Drawable paramDrawable)
  {
    this.mDecorToolbar.setIcon(paramDrawable);
  }
  
  public void setListNavigationCallbacks(SpinnerAdapter paramSpinnerAdapter, ActionBar.OnNavigationListener paramOnNavigationListener)
  {
    this.mDecorToolbar.setDropdownParams(paramSpinnerAdapter, new NavItemSelectedListener(paramOnNavigationListener));
  }
  
  public void setLogo(int paramInt)
  {
    this.mDecorToolbar.setLogo(paramInt);
  }
  
  public void setLogo(Drawable paramDrawable)
  {
    this.mDecorToolbar.setLogo(paramDrawable);
  }
  
  public void setNavigationMode(int paramInt)
  {
    if (paramInt != 2)
    {
      this.mDecorToolbar.setNavigationMode(paramInt);
      return;
    }
    throw new IllegalArgumentException("Tabs not supported in this configuration");
  }
  
  public void setSelectedNavigationItem(int paramInt)
  {
    if (this.mDecorToolbar.getNavigationMode() == 1)
    {
      this.mDecorToolbar.setDropdownSelectedPosition(paramInt);
      return;
    }
    throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
  }
  
  public void setShowHideAnimationEnabled(boolean paramBoolean) {}
  
  public void setSplitBackgroundDrawable(Drawable paramDrawable) {}
  
  public void setStackedBackgroundDrawable(Drawable paramDrawable) {}
  
  public void setSubtitle(int paramInt)
  {
    DecorToolbar localDecorToolbar = this.mDecorToolbar;
    CharSequence localCharSequence;
    if (paramInt != 0) {
      localCharSequence = localDecorToolbar.getContext().getText(paramInt);
    } else {
      localCharSequence = null;
    }
    localDecorToolbar.setSubtitle(localCharSequence);
  }
  
  public void setSubtitle(CharSequence paramCharSequence)
  {
    this.mDecorToolbar.setSubtitle(paramCharSequence);
  }
  
  public void setTitle(int paramInt)
  {
    DecorToolbar localDecorToolbar = this.mDecorToolbar;
    CharSequence localCharSequence;
    if (paramInt != 0) {
      localCharSequence = localDecorToolbar.getContext().getText(paramInt);
    } else {
      localCharSequence = null;
    }
    localDecorToolbar.setTitle(localCharSequence);
  }
  
  public void setTitle(CharSequence paramCharSequence)
  {
    this.mDecorToolbar.setTitle(paramCharSequence);
  }
  
  public void setWindowTitle(CharSequence paramCharSequence)
  {
    this.mDecorToolbar.setWindowTitle(paramCharSequence);
  }
  
  public void show()
  {
    this.mDecorToolbar.setVisibility(0);
  }
  
  private final class ActionMenuPresenterCallback
    implements MenuPresenter.Callback
  {
    private boolean mClosingActionMenu;
    
    ActionMenuPresenterCallback() {}
    
    public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
    {
      if (this.mClosingActionMenu) {
        return;
      }
      this.mClosingActionMenu = true;
      ToolbarActionBar.this.mDecorToolbar.dismissPopupMenus();
      if (ToolbarActionBar.this.mWindowCallback != null) {
        ToolbarActionBar.this.mWindowCallback.onPanelClosed(108, paramMenuBuilder);
      }
      this.mClosingActionMenu = false;
    }
    
    public boolean onOpenSubMenu(MenuBuilder paramMenuBuilder)
    {
      if (ToolbarActionBar.this.mWindowCallback != null)
      {
        ToolbarActionBar.this.mWindowCallback.onMenuOpened(108, paramMenuBuilder);
        return true;
      }
      return false;
    }
  }
  
  private final class MenuBuilderCallback
    implements MenuBuilder.Callback
  {
    MenuBuilderCallback() {}
    
    public boolean onMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem)
    {
      return false;
    }
    
    public void onMenuModeChange(MenuBuilder paramMenuBuilder)
    {
      if (ToolbarActionBar.this.mWindowCallback != null) {
        if (ToolbarActionBar.this.mDecorToolbar.isOverflowMenuShowing()) {
          ToolbarActionBar.this.mWindowCallback.onPanelClosed(108, paramMenuBuilder);
        } else if (ToolbarActionBar.this.mWindowCallback.onPreparePanel(0, null, paramMenuBuilder)) {
          ToolbarActionBar.this.mWindowCallback.onMenuOpened(108, paramMenuBuilder);
        }
      }
    }
  }
  
  private class ToolbarCallbackWrapper
    extends WindowCallbackWrapper
  {
    public ToolbarCallbackWrapper(Window.Callback paramCallback)
    {
      super();
    }
    
    public View onCreatePanelView(int paramInt)
    {
      if (paramInt == 0) {
        return new View(ToolbarActionBar.this.mDecorToolbar.getContext());
      }
      return super.onCreatePanelView(paramInt);
    }
    
    public boolean onPreparePanel(int paramInt, View paramView, Menu paramMenu)
    {
      boolean bool = super.onPreparePanel(paramInt, paramView, paramMenu);
      if ((bool) && (!ToolbarActionBar.this.mToolbarMenuPrepared))
      {
        ToolbarActionBar.this.mDecorToolbar.setMenuPrepared();
        ToolbarActionBar.this.mToolbarMenuPrepared = true;
      }
      return bool;
    }
  }
}

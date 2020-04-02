package androidx.appcompat.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.SpinnerAdapter;
import androidx.appcompat.R.attr;
import androidx.appcompat.R.id;
import androidx.appcompat.R.styleable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.ActionBarPolicy;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.ActionMode.Callback;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.ViewPropertyAnimatorCompatSet;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuBuilder.Callback;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.appcompat.widget.ActionBarContainer;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.appcompat.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback;
import androidx.appcompat.widget.DecorToolbar;
import androidx.appcompat.widget.ScrollingTabContainerView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.core.view.ViewPropertyAnimatorUpdateListener;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class WindowDecorActionBar
  extends ActionBar
  implements ActionBarOverlayLayout.ActionBarVisibilityCallback
{
  private static final long FADE_IN_DURATION_MS = 200L;
  private static final long FADE_OUT_DURATION_MS = 100L;
  private static final int INVALID_POSITION = -1;
  private static final String TAG = "WindowDecorActionBar";
  private static final Interpolator sHideInterpolator = new AccelerateInterpolator();
  private static final Interpolator sShowInterpolator = new DecelerateInterpolator();
  ActionModeImpl mActionMode;
  private Activity mActivity;
  ActionBarContainer mContainerView;
  boolean mContentAnimations = true;
  View mContentView;
  Context mContext;
  ActionBarContextView mContextView;
  private int mCurWindowVisibility = 0;
  ViewPropertyAnimatorCompatSet mCurrentShowAnim;
  DecorToolbar mDecorToolbar;
  ActionMode mDeferredDestroyActionMode;
  ActionMode.Callback mDeferredModeDestroyCallback;
  private Dialog mDialog;
  private boolean mDisplayHomeAsUpSet;
  private boolean mHasEmbeddedTabs;
  boolean mHiddenByApp;
  boolean mHiddenBySystem;
  final ViewPropertyAnimatorListener mHideListener = new ViewPropertyAnimatorListenerAdapter()
  {
    public void onAnimationEnd(View paramAnonymousView)
    {
      if ((WindowDecorActionBar.this.mContentAnimations) && (WindowDecorActionBar.this.mContentView != null))
      {
        WindowDecorActionBar.this.mContentView.setTranslationY(0.0F);
        WindowDecorActionBar.this.mContainerView.setTranslationY(0.0F);
      }
      WindowDecorActionBar.this.mContainerView.setVisibility(8);
      WindowDecorActionBar.this.mContainerView.setTransitioning(false);
      WindowDecorActionBar.this.mCurrentShowAnim = null;
      WindowDecorActionBar.this.completeDeferredDestroyActionMode();
      if (WindowDecorActionBar.this.mOverlayLayout != null) {
        ViewCompat.requestApplyInsets(WindowDecorActionBar.this.mOverlayLayout);
      }
    }
  };
  boolean mHideOnContentScroll;
  private boolean mLastMenuVisibility;
  private ArrayList<ActionBar.OnMenuVisibilityListener> mMenuVisibilityListeners = new ArrayList();
  private boolean mNowShowing = true;
  ActionBarOverlayLayout mOverlayLayout;
  private int mSavedTabPosition = -1;
  private TabImpl mSelectedTab;
  private boolean mShowHideAnimationEnabled;
  final ViewPropertyAnimatorListener mShowListener = new ViewPropertyAnimatorListenerAdapter()
  {
    public void onAnimationEnd(View paramAnonymousView)
    {
      WindowDecorActionBar.this.mCurrentShowAnim = null;
      WindowDecorActionBar.this.mContainerView.requestLayout();
    }
  };
  private boolean mShowingForMode;
  ScrollingTabContainerView mTabScrollView;
  private ArrayList<TabImpl> mTabs = new ArrayList();
  private Context mThemedContext;
  final ViewPropertyAnimatorUpdateListener mUpdateListener = new ViewPropertyAnimatorUpdateListener()
  {
    public void onAnimationUpdate(View paramAnonymousView)
    {
      ((View)WindowDecorActionBar.this.mContainerView.getParent()).invalidate();
    }
  };
  
  public WindowDecorActionBar(Activity paramActivity, boolean paramBoolean)
  {
    this.mActivity = paramActivity;
    paramActivity = paramActivity.getWindow().getDecorView();
    init(paramActivity);
    if (!paramBoolean) {
      this.mContentView = paramActivity.findViewById(16908290);
    }
  }
  
  public WindowDecorActionBar(Dialog paramDialog)
  {
    this.mDialog = paramDialog;
    init(paramDialog.getWindow().getDecorView());
  }
  
  public WindowDecorActionBar(View paramView)
  {
    init(paramView);
  }
  
  static boolean checkShowingFlags(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if (paramBoolean3) {
      return true;
    }
    return (!paramBoolean1) && (!paramBoolean2);
  }
  
  private void cleanupTabs()
  {
    if (this.mSelectedTab != null) {
      selectTab(null);
    }
    this.mTabs.clear();
    ScrollingTabContainerView localScrollingTabContainerView = this.mTabScrollView;
    if (localScrollingTabContainerView != null) {
      localScrollingTabContainerView.removeAllTabs();
    }
    this.mSavedTabPosition = -1;
  }
  
  private void configureTab(ActionBar.Tab paramTab, int paramInt)
  {
    paramTab = (TabImpl)paramTab;
    if (paramTab.getCallback() != null)
    {
      paramTab.setPosition(paramInt);
      this.mTabs.add(paramInt, paramTab);
      int i = this.mTabs.size();
      for (;;)
      {
        paramInt++;
        if (paramInt >= i) {
          break;
        }
        ((TabImpl)this.mTabs.get(paramInt)).setPosition(paramInt);
      }
      return;
    }
    throw new IllegalStateException("Action Bar Tab must have a Callback");
  }
  
  private void ensureTabsExist()
  {
    if (this.mTabScrollView != null) {
      return;
    }
    ScrollingTabContainerView localScrollingTabContainerView = new ScrollingTabContainerView(this.mContext);
    if (this.mHasEmbeddedTabs)
    {
      localScrollingTabContainerView.setVisibility(0);
      this.mDecorToolbar.setEmbeddedTabView(localScrollingTabContainerView);
    }
    else
    {
      if (getNavigationMode() == 2)
      {
        localScrollingTabContainerView.setVisibility(0);
        ActionBarOverlayLayout localActionBarOverlayLayout = this.mOverlayLayout;
        if (localActionBarOverlayLayout != null) {
          ViewCompat.requestApplyInsets(localActionBarOverlayLayout);
        }
      }
      else
      {
        localScrollingTabContainerView.setVisibility(8);
      }
      this.mContainerView.setTabContainer(localScrollingTabContainerView);
    }
    this.mTabScrollView = localScrollingTabContainerView;
  }
  
  private DecorToolbar getDecorToolbar(View paramView)
  {
    if ((paramView instanceof DecorToolbar)) {
      return (DecorToolbar)paramView;
    }
    if ((paramView instanceof Toolbar)) {
      return ((Toolbar)paramView).getWrapper();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Can't make a decor toolbar out of ");
    if (paramView != null) {
      paramView = paramView.getClass().getSimpleName();
    } else {
      paramView = "null";
    }
    localStringBuilder.append(paramView);
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  private void hideForActionMode()
  {
    if (this.mShowingForMode)
    {
      this.mShowingForMode = false;
      ActionBarOverlayLayout localActionBarOverlayLayout = this.mOverlayLayout;
      if (localActionBarOverlayLayout != null) {
        localActionBarOverlayLayout.setShowingForActionMode(false);
      }
      updateVisibility(false);
    }
  }
  
  private void init(View paramView)
  {
    Object localObject = (ActionBarOverlayLayout)paramView.findViewById(R.id.decor_content_parent);
    this.mOverlayLayout = ((ActionBarOverlayLayout)localObject);
    if (localObject != null) {
      ((ActionBarOverlayLayout)localObject).setActionBarVisibilityCallback(this);
    }
    this.mDecorToolbar = getDecorToolbar(paramView.findViewById(R.id.action_bar));
    this.mContextView = ((ActionBarContextView)paramView.findViewById(R.id.action_context_bar));
    localObject = (ActionBarContainer)paramView.findViewById(R.id.action_bar_container);
    this.mContainerView = ((ActionBarContainer)localObject);
    paramView = this.mDecorToolbar;
    if ((paramView != null) && (this.mContextView != null) && (localObject != null))
    {
      this.mContext = paramView.getContext();
      if ((this.mDecorToolbar.getDisplayOptions() & 0x4) != 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        this.mDisplayHomeAsUpSet = true;
      }
      paramView = ActionBarPolicy.get(this.mContext);
      boolean bool;
      if ((!paramView.enableHomeButtonByDefault()) && (i == 0)) {
        bool = false;
      } else {
        bool = true;
      }
      setHomeButtonEnabled(bool);
      setHasEmbeddedTabs(paramView.hasEmbeddedTabs());
      paramView = this.mContext.obtainStyledAttributes(null, R.styleable.ActionBar, R.attr.actionBarStyle, 0);
      if (paramView.getBoolean(R.styleable.ActionBar_hideOnContentScroll, false)) {
        setHideOnContentScrollEnabled(true);
      }
      int i = paramView.getDimensionPixelSize(R.styleable.ActionBar_elevation, 0);
      if (i != 0) {
        setElevation(i);
      }
      paramView.recycle();
      return;
    }
    paramView = new StringBuilder();
    paramView.append(getClass().getSimpleName());
    paramView.append(" can only be used with a compatible window decor layout");
    throw new IllegalStateException(paramView.toString());
  }
  
  private void setHasEmbeddedTabs(boolean paramBoolean)
  {
    this.mHasEmbeddedTabs = paramBoolean;
    if (!paramBoolean)
    {
      this.mDecorToolbar.setEmbeddedTabView(null);
      this.mContainerView.setTabContainer(this.mTabScrollView);
    }
    else
    {
      this.mContainerView.setTabContainer(null);
      this.mDecorToolbar.setEmbeddedTabView(this.mTabScrollView);
    }
    int i = getNavigationMode();
    boolean bool = true;
    if (i == 2) {
      i = 1;
    } else {
      i = 0;
    }
    Object localObject = this.mTabScrollView;
    if (localObject != null) {
      if (i != 0)
      {
        ((ScrollingTabContainerView)localObject).setVisibility(0);
        localObject = this.mOverlayLayout;
        if (localObject != null) {
          ViewCompat.requestApplyInsets((View)localObject);
        }
      }
      else
      {
        ((ScrollingTabContainerView)localObject).setVisibility(8);
      }
    }
    localObject = this.mDecorToolbar;
    if ((!this.mHasEmbeddedTabs) && (i != 0)) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    }
    ((DecorToolbar)localObject).setCollapsible(paramBoolean);
    localObject = this.mOverlayLayout;
    if ((!this.mHasEmbeddedTabs) && (i != 0)) {
      paramBoolean = bool;
    } else {
      paramBoolean = false;
    }
    ((ActionBarOverlayLayout)localObject).setHasNonEmbeddedTabs(paramBoolean);
  }
  
  private boolean shouldAnimateContextView()
  {
    return ViewCompat.isLaidOut(this.mContainerView);
  }
  
  private void showForActionMode()
  {
    if (!this.mShowingForMode)
    {
      this.mShowingForMode = true;
      ActionBarOverlayLayout localActionBarOverlayLayout = this.mOverlayLayout;
      if (localActionBarOverlayLayout != null) {
        localActionBarOverlayLayout.setShowingForActionMode(true);
      }
      updateVisibility(false);
    }
  }
  
  private void updateVisibility(boolean paramBoolean)
  {
    if (checkShowingFlags(this.mHiddenByApp, this.mHiddenBySystem, this.mShowingForMode))
    {
      if (!this.mNowShowing)
      {
        this.mNowShowing = true;
        doShow(paramBoolean);
      }
    }
    else if (this.mNowShowing)
    {
      this.mNowShowing = false;
      doHide(paramBoolean);
    }
  }
  
  public void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener paramOnMenuVisibilityListener)
  {
    this.mMenuVisibilityListeners.add(paramOnMenuVisibilityListener);
  }
  
  public void addTab(ActionBar.Tab paramTab)
  {
    addTab(paramTab, this.mTabs.isEmpty());
  }
  
  public void addTab(ActionBar.Tab paramTab, int paramInt)
  {
    addTab(paramTab, paramInt, this.mTabs.isEmpty());
  }
  
  public void addTab(ActionBar.Tab paramTab, int paramInt, boolean paramBoolean)
  {
    ensureTabsExist();
    this.mTabScrollView.addTab(paramTab, paramInt, paramBoolean);
    configureTab(paramTab, paramInt);
    if (paramBoolean) {
      selectTab(paramTab);
    }
  }
  
  public void addTab(ActionBar.Tab paramTab, boolean paramBoolean)
  {
    ensureTabsExist();
    this.mTabScrollView.addTab(paramTab, paramBoolean);
    configureTab(paramTab, this.mTabs.size());
    if (paramBoolean) {
      selectTab(paramTab);
    }
  }
  
  public void animateToMode(boolean paramBoolean)
  {
    if (paramBoolean) {
      showForActionMode();
    } else {
      hideForActionMode();
    }
    if (shouldAnimateContextView())
    {
      ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat1;
      ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat2;
      if (paramBoolean)
      {
        localViewPropertyAnimatorCompat1 = this.mDecorToolbar.setupAnimatorToVisibility(4, 100L);
        localViewPropertyAnimatorCompat2 = this.mContextView.setupAnimatorToVisibility(0, 200L);
      }
      else
      {
        localViewPropertyAnimatorCompat2 = this.mDecorToolbar.setupAnimatorToVisibility(0, 200L);
        localViewPropertyAnimatorCompat1 = this.mContextView.setupAnimatorToVisibility(8, 100L);
      }
      ViewPropertyAnimatorCompatSet localViewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
      localViewPropertyAnimatorCompatSet.playSequentially(localViewPropertyAnimatorCompat1, localViewPropertyAnimatorCompat2);
      localViewPropertyAnimatorCompatSet.start();
    }
    else if (paramBoolean)
    {
      this.mDecorToolbar.setVisibility(4);
      this.mContextView.setVisibility(0);
    }
    else
    {
      this.mDecorToolbar.setVisibility(0);
      this.mContextView.setVisibility(8);
    }
  }
  
  public boolean collapseActionView()
  {
    DecorToolbar localDecorToolbar = this.mDecorToolbar;
    if ((localDecorToolbar != null) && (localDecorToolbar.hasExpandedActionView()))
    {
      this.mDecorToolbar.collapseActionView();
      return true;
    }
    return false;
  }
  
  void completeDeferredDestroyActionMode()
  {
    ActionMode.Callback localCallback = this.mDeferredModeDestroyCallback;
    if (localCallback != null)
    {
      localCallback.onDestroyActionMode(this.mDeferredDestroyActionMode);
      this.mDeferredDestroyActionMode = null;
      this.mDeferredModeDestroyCallback = null;
    }
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
  
  public void doHide(boolean paramBoolean)
  {
    ViewPropertyAnimatorCompatSet localViewPropertyAnimatorCompatSet = this.mCurrentShowAnim;
    if (localViewPropertyAnimatorCompatSet != null) {
      localViewPropertyAnimatorCompatSet.cancel();
    }
    if ((this.mCurWindowVisibility == 0) && ((this.mShowHideAnimationEnabled) || (paramBoolean)))
    {
      this.mContainerView.setAlpha(1.0F);
      this.mContainerView.setTransitioning(true);
      localViewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
      float f1 = -this.mContainerView.getHeight();
      float f2 = f1;
      if (paramBoolean)
      {
        localObject = new int[2];
        Object tmp79_77 = localObject;
        tmp79_77[0] = 0;
        Object tmp83_79 = tmp79_77;
        tmp83_79[1] = 0;
        tmp83_79;
        this.mContainerView.getLocationInWindow((int[])localObject);
        f2 = f1 - localObject[1];
      }
      Object localObject = ViewCompat.animate(this.mContainerView).translationY(f2);
      ((ViewPropertyAnimatorCompat)localObject).setUpdateListener(this.mUpdateListener);
      localViewPropertyAnimatorCompatSet.play((ViewPropertyAnimatorCompat)localObject);
      if (this.mContentAnimations)
      {
        localObject = this.mContentView;
        if (localObject != null) {
          localViewPropertyAnimatorCompatSet.play(ViewCompat.animate((View)localObject).translationY(f2));
        }
      }
      localViewPropertyAnimatorCompatSet.setInterpolator(sHideInterpolator);
      localViewPropertyAnimatorCompatSet.setDuration(250L);
      localViewPropertyAnimatorCompatSet.setListener(this.mHideListener);
      this.mCurrentShowAnim = localViewPropertyAnimatorCompatSet;
      localViewPropertyAnimatorCompatSet.start();
    }
    else
    {
      this.mHideListener.onAnimationEnd(null);
    }
  }
  
  public void doShow(boolean paramBoolean)
  {
    Object localObject1 = this.mCurrentShowAnim;
    if (localObject1 != null) {
      ((ViewPropertyAnimatorCompatSet)localObject1).cancel();
    }
    this.mContainerView.setVisibility(0);
    if ((this.mCurWindowVisibility == 0) && ((this.mShowHideAnimationEnabled) || (paramBoolean)))
    {
      this.mContainerView.setTranslationY(0.0F);
      float f1 = -this.mContainerView.getHeight();
      float f2 = f1;
      if (paramBoolean)
      {
        localObject1 = new int[2];
        Object tmp69_68 = localObject1;
        tmp69_68[0] = 0;
        Object tmp73_69 = tmp69_68;
        tmp73_69[1] = 0;
        tmp73_69;
        this.mContainerView.getLocationInWindow((int[])localObject1);
        f2 = f1 - localObject1[1];
      }
      this.mContainerView.setTranslationY(f2);
      localObject1 = new ViewPropertyAnimatorCompatSet();
      Object localObject2 = ViewCompat.animate(this.mContainerView).translationY(0.0F);
      ((ViewPropertyAnimatorCompat)localObject2).setUpdateListener(this.mUpdateListener);
      ((ViewPropertyAnimatorCompatSet)localObject1).play((ViewPropertyAnimatorCompat)localObject2);
      if (this.mContentAnimations)
      {
        localObject2 = this.mContentView;
        if (localObject2 != null)
        {
          ((View)localObject2).setTranslationY(f2);
          ((ViewPropertyAnimatorCompatSet)localObject1).play(ViewCompat.animate(this.mContentView).translationY(0.0F));
        }
      }
      ((ViewPropertyAnimatorCompatSet)localObject1).setInterpolator(sShowInterpolator);
      ((ViewPropertyAnimatorCompatSet)localObject1).setDuration(250L);
      ((ViewPropertyAnimatorCompatSet)localObject1).setListener(this.mShowListener);
      this.mCurrentShowAnim = ((ViewPropertyAnimatorCompatSet)localObject1);
      ((ViewPropertyAnimatorCompatSet)localObject1).start();
    }
    else
    {
      this.mContainerView.setAlpha(1.0F);
      this.mContainerView.setTranslationY(0.0F);
      if (this.mContentAnimations)
      {
        localObject1 = this.mContentView;
        if (localObject1 != null) {
          ((View)localObject1).setTranslationY(0.0F);
        }
      }
      this.mShowListener.onAnimationEnd(null);
    }
    localObject1 = this.mOverlayLayout;
    if (localObject1 != null) {
      ViewCompat.requestApplyInsets((View)localObject1);
    }
  }
  
  public void enableContentAnimations(boolean paramBoolean)
  {
    this.mContentAnimations = paramBoolean;
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
    return ViewCompat.getElevation(this.mContainerView);
  }
  
  public int getHeight()
  {
    return this.mContainerView.getHeight();
  }
  
  public int getHideOffset()
  {
    return this.mOverlayLayout.getActionBarHideOffset();
  }
  
  public int getNavigationItemCount()
  {
    int i = this.mDecorToolbar.getNavigationMode();
    if (i != 1)
    {
      if (i != 2) {
        return 0;
      }
      return this.mTabs.size();
    }
    return this.mDecorToolbar.getDropdownItemCount();
  }
  
  public int getNavigationMode()
  {
    return this.mDecorToolbar.getNavigationMode();
  }
  
  public int getSelectedNavigationIndex()
  {
    int i = this.mDecorToolbar.getNavigationMode();
    if (i != 1)
    {
      int j = -1;
      if (i != 2) {
        return -1;
      }
      TabImpl localTabImpl = this.mSelectedTab;
      if (localTabImpl != null) {
        j = localTabImpl.getPosition();
      }
      return j;
    }
    return this.mDecorToolbar.getDropdownSelectedPosition();
  }
  
  public ActionBar.Tab getSelectedTab()
  {
    return this.mSelectedTab;
  }
  
  public CharSequence getSubtitle()
  {
    return this.mDecorToolbar.getSubtitle();
  }
  
  public ActionBar.Tab getTabAt(int paramInt)
  {
    return (ActionBar.Tab)this.mTabs.get(paramInt);
  }
  
  public int getTabCount()
  {
    return this.mTabs.size();
  }
  
  public Context getThemedContext()
  {
    if (this.mThemedContext == null)
    {
      TypedValue localTypedValue = new TypedValue();
      this.mContext.getTheme().resolveAttribute(R.attr.actionBarWidgetTheme, localTypedValue, true);
      int i = localTypedValue.resourceId;
      if (i != 0) {
        this.mThemedContext = new ContextThemeWrapper(this.mContext, i);
      } else {
        this.mThemedContext = this.mContext;
      }
    }
    return this.mThemedContext;
  }
  
  public CharSequence getTitle()
  {
    return this.mDecorToolbar.getTitle();
  }
  
  public boolean hasIcon()
  {
    return this.mDecorToolbar.hasIcon();
  }
  
  public boolean hasLogo()
  {
    return this.mDecorToolbar.hasLogo();
  }
  
  public void hide()
  {
    if (!this.mHiddenByApp)
    {
      this.mHiddenByApp = true;
      updateVisibility(false);
    }
  }
  
  public void hideForSystem()
  {
    if (!this.mHiddenBySystem)
    {
      this.mHiddenBySystem = true;
      updateVisibility(true);
    }
  }
  
  public boolean isHideOnContentScrollEnabled()
  {
    return this.mOverlayLayout.isHideOnContentScrollEnabled();
  }
  
  public boolean isShowing()
  {
    int i = getHeight();
    boolean bool;
    if ((this.mNowShowing) && ((i == 0) || (getHideOffset() < i))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isTitleTruncated()
  {
    DecorToolbar localDecorToolbar = this.mDecorToolbar;
    boolean bool;
    if ((localDecorToolbar != null) && (localDecorToolbar.isTitleTruncated())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public ActionBar.Tab newTab()
  {
    return new TabImpl();
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    setHasEmbeddedTabs(ActionBarPolicy.get(this.mContext).hasEmbeddedTabs());
  }
  
  public void onContentScrollStarted()
  {
    ViewPropertyAnimatorCompatSet localViewPropertyAnimatorCompatSet = this.mCurrentShowAnim;
    if (localViewPropertyAnimatorCompatSet != null)
    {
      localViewPropertyAnimatorCompatSet.cancel();
      this.mCurrentShowAnim = null;
    }
  }
  
  public void onContentScrollStopped() {}
  
  public boolean onKeyShortcut(int paramInt, KeyEvent paramKeyEvent)
  {
    Object localObject = this.mActionMode;
    if (localObject == null) {
      return false;
    }
    localObject = ((ActionModeImpl)localObject).getMenu();
    if (localObject != null)
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
      ((Menu)localObject).setQwertyMode(bool);
      return ((Menu)localObject).performShortcut(paramInt, paramKeyEvent, 0);
    }
    return false;
  }
  
  public void onWindowVisibilityChanged(int paramInt)
  {
    this.mCurWindowVisibility = paramInt;
  }
  
  public void removeAllTabs()
  {
    cleanupTabs();
  }
  
  public void removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener paramOnMenuVisibilityListener)
  {
    this.mMenuVisibilityListeners.remove(paramOnMenuVisibilityListener);
  }
  
  public void removeTab(ActionBar.Tab paramTab)
  {
    removeTabAt(paramTab.getPosition());
  }
  
  public void removeTabAt(int paramInt)
  {
    if (this.mTabScrollView == null) {
      return;
    }
    TabImpl localTabImpl = this.mSelectedTab;
    int i;
    if (localTabImpl != null) {
      i = localTabImpl.getPosition();
    } else {
      i = this.mSavedTabPosition;
    }
    this.mTabScrollView.removeTabAt(paramInt);
    localTabImpl = (TabImpl)this.mTabs.remove(paramInt);
    if (localTabImpl != null) {
      localTabImpl.setPosition(-1);
    }
    int j = this.mTabs.size();
    for (int k = paramInt; k < j; k++) {
      ((TabImpl)this.mTabs.get(k)).setPosition(k);
    }
    if (i == paramInt)
    {
      if (this.mTabs.isEmpty()) {
        localTabImpl = null;
      } else {
        localTabImpl = (TabImpl)this.mTabs.get(Math.max(0, paramInt - 1));
      }
      selectTab(localTabImpl);
    }
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
    int i = getNavigationMode();
    int j = -1;
    if (i != 2)
    {
      if (paramTab != null) {
        j = paramTab.getPosition();
      }
      this.mSavedTabPosition = j;
      return;
    }
    FragmentTransaction localFragmentTransaction;
    if (((this.mActivity instanceof FragmentActivity)) && (!this.mDecorToolbar.getViewGroup().isInEditMode())) {
      localFragmentTransaction = ((FragmentActivity)this.mActivity).getSupportFragmentManager().beginTransaction().disallowAddToBackStack();
    } else {
      localFragmentTransaction = null;
    }
    Object localObject = this.mSelectedTab;
    if (localObject == paramTab)
    {
      if (localObject != null)
      {
        ((TabImpl)localObject).getCallback().onTabReselected(this.mSelectedTab, localFragmentTransaction);
        this.mTabScrollView.animateToTab(paramTab.getPosition());
      }
    }
    else
    {
      localObject = this.mTabScrollView;
      if (paramTab != null) {
        j = paramTab.getPosition();
      }
      ((ScrollingTabContainerView)localObject).setTabSelected(j);
      localObject = this.mSelectedTab;
      if (localObject != null) {
        ((TabImpl)localObject).getCallback().onTabUnselected(this.mSelectedTab, localFragmentTransaction);
      }
      paramTab = (TabImpl)paramTab;
      this.mSelectedTab = paramTab;
      if (paramTab != null) {
        paramTab.getCallback().onTabSelected(this.mSelectedTab, localFragmentTransaction);
      }
    }
    if ((localFragmentTransaction != null) && (!localFragmentTransaction.isEmpty())) {
      localFragmentTransaction.commit();
    }
  }
  
  public void setBackgroundDrawable(Drawable paramDrawable)
  {
    this.mContainerView.setPrimaryBackground(paramDrawable);
  }
  
  public void setCustomView(int paramInt)
  {
    setCustomView(LayoutInflater.from(getThemedContext()).inflate(paramInt, this.mDecorToolbar.getViewGroup(), false));
  }
  
  public void setCustomView(View paramView)
  {
    this.mDecorToolbar.setCustomView(paramView);
  }
  
  public void setCustomView(View paramView, ActionBar.LayoutParams paramLayoutParams)
  {
    paramView.setLayoutParams(paramLayoutParams);
    this.mDecorToolbar.setCustomView(paramView);
  }
  
  public void setDefaultDisplayHomeAsUpEnabled(boolean paramBoolean)
  {
    if (!this.mDisplayHomeAsUpSet) {
      setDisplayHomeAsUpEnabled(paramBoolean);
    }
  }
  
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
    if ((paramInt & 0x4) != 0) {
      this.mDisplayHomeAsUpSet = true;
    }
    this.mDecorToolbar.setDisplayOptions(paramInt);
  }
  
  public void setDisplayOptions(int paramInt1, int paramInt2)
  {
    int i = this.mDecorToolbar.getDisplayOptions();
    if ((paramInt2 & 0x4) != 0) {
      this.mDisplayHomeAsUpSet = true;
    }
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
    ViewCompat.setElevation(this.mContainerView, paramFloat);
  }
  
  public void setHideOffset(int paramInt)
  {
    if ((paramInt != 0) && (!this.mOverlayLayout.isInOverlayMode())) {
      throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to set a non-zero hide offset");
    }
    this.mOverlayLayout.setActionBarHideOffset(paramInt);
  }
  
  public void setHideOnContentScrollEnabled(boolean paramBoolean)
  {
    if ((paramBoolean) && (!this.mOverlayLayout.isInOverlayMode())) {
      throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
    }
    this.mHideOnContentScroll = paramBoolean;
    this.mOverlayLayout.setHideOnContentScrollEnabled(paramBoolean);
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
  
  public void setHomeButtonEnabled(boolean paramBoolean)
  {
    this.mDecorToolbar.setHomeButtonEnabled(paramBoolean);
  }
  
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
    int i = this.mDecorToolbar.getNavigationMode();
    if (i == 2)
    {
      this.mSavedTabPosition = getSelectedNavigationIndex();
      selectTab(null);
      this.mTabScrollView.setVisibility(8);
    }
    if ((i != paramInt) && (!this.mHasEmbeddedTabs))
    {
      localObject = this.mOverlayLayout;
      if (localObject != null) {
        ViewCompat.requestApplyInsets((View)localObject);
      }
    }
    this.mDecorToolbar.setNavigationMode(paramInt);
    boolean bool1 = false;
    if (paramInt == 2)
    {
      ensureTabsExist();
      this.mTabScrollView.setVisibility(0);
      i = this.mSavedTabPosition;
      if (i != -1)
      {
        setSelectedNavigationItem(i);
        this.mSavedTabPosition = -1;
      }
    }
    Object localObject = this.mDecorToolbar;
    if ((paramInt == 2) && (!this.mHasEmbeddedTabs)) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    ((DecorToolbar)localObject).setCollapsible(bool2);
    localObject = this.mOverlayLayout;
    boolean bool2 = bool1;
    if (paramInt == 2)
    {
      bool2 = bool1;
      if (!this.mHasEmbeddedTabs) {
        bool2 = true;
      }
    }
    ((ActionBarOverlayLayout)localObject).setHasNonEmbeddedTabs(bool2);
  }
  
  public void setSelectedNavigationItem(int paramInt)
  {
    int i = this.mDecorToolbar.getNavigationMode();
    if (i != 1)
    {
      if (i == 2) {
        selectTab((ActionBar.Tab)this.mTabs.get(paramInt));
      } else {
        throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
      }
    }
    else {
      this.mDecorToolbar.setDropdownSelectedPosition(paramInt);
    }
  }
  
  public void setShowHideAnimationEnabled(boolean paramBoolean)
  {
    this.mShowHideAnimationEnabled = paramBoolean;
    if (!paramBoolean)
    {
      ViewPropertyAnimatorCompatSet localViewPropertyAnimatorCompatSet = this.mCurrentShowAnim;
      if (localViewPropertyAnimatorCompatSet != null) {
        localViewPropertyAnimatorCompatSet.cancel();
      }
    }
  }
  
  public void setSplitBackgroundDrawable(Drawable paramDrawable) {}
  
  public void setStackedBackgroundDrawable(Drawable paramDrawable)
  {
    this.mContainerView.setStackedBackground(paramDrawable);
  }
  
  public void setSubtitle(int paramInt)
  {
    setSubtitle(this.mContext.getString(paramInt));
  }
  
  public void setSubtitle(CharSequence paramCharSequence)
  {
    this.mDecorToolbar.setSubtitle(paramCharSequence);
  }
  
  public void setTitle(int paramInt)
  {
    setTitle(this.mContext.getString(paramInt));
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
    if (this.mHiddenByApp)
    {
      this.mHiddenByApp = false;
      updateVisibility(false);
    }
  }
  
  public void showForSystem()
  {
    if (this.mHiddenBySystem)
    {
      this.mHiddenBySystem = false;
      updateVisibility(true);
    }
  }
  
  public ActionMode startActionMode(ActionMode.Callback paramCallback)
  {
    ActionModeImpl localActionModeImpl = this.mActionMode;
    if (localActionModeImpl != null) {
      localActionModeImpl.finish();
    }
    this.mOverlayLayout.setHideOnContentScrollEnabled(false);
    this.mContextView.killMode();
    paramCallback = new ActionModeImpl(this.mContextView.getContext(), paramCallback);
    if (paramCallback.dispatchOnCreate())
    {
      this.mActionMode = paramCallback;
      paramCallback.invalidate();
      this.mContextView.initForMode(paramCallback);
      animateToMode(true);
      this.mContextView.sendAccessibilityEvent(32);
      return paramCallback;
    }
    return null;
  }
  
  public class ActionModeImpl
    extends ActionMode
    implements MenuBuilder.Callback
  {
    private final Context mActionModeContext;
    private ActionMode.Callback mCallback;
    private WeakReference<View> mCustomView;
    private final MenuBuilder mMenu;
    
    public ActionModeImpl(Context paramContext, ActionMode.Callback paramCallback)
    {
      this.mActionModeContext = paramContext;
      this.mCallback = paramCallback;
      this$1 = new MenuBuilder(paramContext).setDefaultShowAsAction(1);
      this.mMenu = WindowDecorActionBar.this;
      WindowDecorActionBar.this.setCallback(this);
    }
    
    public boolean dispatchOnCreate()
    {
      this.mMenu.stopDispatchingItemsChanged();
      try
      {
        boolean bool = this.mCallback.onCreateActionMode(this, this.mMenu);
        return bool;
      }
      finally
      {
        this.mMenu.startDispatchingItemsChanged();
      }
    }
    
    public void finish()
    {
      if (WindowDecorActionBar.this.mActionMode != this) {
        return;
      }
      if (!WindowDecorActionBar.checkShowingFlags(WindowDecorActionBar.this.mHiddenByApp, WindowDecorActionBar.this.mHiddenBySystem, false))
      {
        WindowDecorActionBar.this.mDeferredDestroyActionMode = this;
        WindowDecorActionBar.this.mDeferredModeDestroyCallback = this.mCallback;
      }
      else
      {
        this.mCallback.onDestroyActionMode(this);
      }
      this.mCallback = null;
      WindowDecorActionBar.this.animateToMode(false);
      WindowDecorActionBar.this.mContextView.closeMode();
      WindowDecorActionBar.this.mDecorToolbar.getViewGroup().sendAccessibilityEvent(32);
      WindowDecorActionBar.this.mOverlayLayout.setHideOnContentScrollEnabled(WindowDecorActionBar.this.mHideOnContentScroll);
      WindowDecorActionBar.this.mActionMode = null;
    }
    
    public View getCustomView()
    {
      Object localObject = this.mCustomView;
      if (localObject != null) {
        localObject = (View)((WeakReference)localObject).get();
      } else {
        localObject = null;
      }
      return localObject;
    }
    
    public Menu getMenu()
    {
      return this.mMenu;
    }
    
    public MenuInflater getMenuInflater()
    {
      return new SupportMenuInflater(this.mActionModeContext);
    }
    
    public CharSequence getSubtitle()
    {
      return WindowDecorActionBar.this.mContextView.getSubtitle();
    }
    
    public CharSequence getTitle()
    {
      return WindowDecorActionBar.this.mContextView.getTitle();
    }
    
    public void invalidate()
    {
      if (WindowDecorActionBar.this.mActionMode != this) {
        return;
      }
      this.mMenu.stopDispatchingItemsChanged();
      try
      {
        this.mCallback.onPrepareActionMode(this, this.mMenu);
        return;
      }
      finally
      {
        this.mMenu.startDispatchingItemsChanged();
      }
    }
    
    public boolean isTitleOptional()
    {
      return WindowDecorActionBar.this.mContextView.isTitleOptional();
    }
    
    public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean) {}
    
    public void onCloseSubMenu(SubMenuBuilder paramSubMenuBuilder) {}
    
    public boolean onMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem)
    {
      paramMenuBuilder = this.mCallback;
      if (paramMenuBuilder != null) {
        return paramMenuBuilder.onActionItemClicked(this, paramMenuItem);
      }
      return false;
    }
    
    public void onMenuModeChange(MenuBuilder paramMenuBuilder)
    {
      if (this.mCallback == null) {
        return;
      }
      invalidate();
      WindowDecorActionBar.this.mContextView.showOverflowMenu();
    }
    
    public boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder)
    {
      if (this.mCallback == null) {
        return false;
      }
      if (!paramSubMenuBuilder.hasVisibleItems()) {
        return true;
      }
      new MenuPopupHelper(WindowDecorActionBar.this.getThemedContext(), paramSubMenuBuilder).show();
      return true;
    }
    
    public void setCustomView(View paramView)
    {
      WindowDecorActionBar.this.mContextView.setCustomView(paramView);
      this.mCustomView = new WeakReference(paramView);
    }
    
    public void setSubtitle(int paramInt)
    {
      setSubtitle(WindowDecorActionBar.this.mContext.getResources().getString(paramInt));
    }
    
    public void setSubtitle(CharSequence paramCharSequence)
    {
      WindowDecorActionBar.this.mContextView.setSubtitle(paramCharSequence);
    }
    
    public void setTitle(int paramInt)
    {
      setTitle(WindowDecorActionBar.this.mContext.getResources().getString(paramInt));
    }
    
    public void setTitle(CharSequence paramCharSequence)
    {
      WindowDecorActionBar.this.mContextView.setTitle(paramCharSequence);
    }
    
    public void setTitleOptionalHint(boolean paramBoolean)
    {
      super.setTitleOptionalHint(paramBoolean);
      WindowDecorActionBar.this.mContextView.setTitleOptional(paramBoolean);
    }
  }
  
  public class TabImpl
    extends ActionBar.Tab
  {
    private ActionBar.TabListener mCallback;
    private CharSequence mContentDesc;
    private View mCustomView;
    private Drawable mIcon;
    private int mPosition = -1;
    private Object mTag;
    private CharSequence mText;
    
    public TabImpl() {}
    
    public ActionBar.TabListener getCallback()
    {
      return this.mCallback;
    }
    
    public CharSequence getContentDescription()
    {
      return this.mContentDesc;
    }
    
    public View getCustomView()
    {
      return this.mCustomView;
    }
    
    public Drawable getIcon()
    {
      return this.mIcon;
    }
    
    public int getPosition()
    {
      return this.mPosition;
    }
    
    public Object getTag()
    {
      return this.mTag;
    }
    
    public CharSequence getText()
    {
      return this.mText;
    }
    
    public void select()
    {
      WindowDecorActionBar.this.selectTab(this);
    }
    
    public ActionBar.Tab setContentDescription(int paramInt)
    {
      return setContentDescription(WindowDecorActionBar.this.mContext.getResources().getText(paramInt));
    }
    
    public ActionBar.Tab setContentDescription(CharSequence paramCharSequence)
    {
      this.mContentDesc = paramCharSequence;
      if (this.mPosition >= 0) {
        WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
      }
      return this;
    }
    
    public ActionBar.Tab setCustomView(int paramInt)
    {
      return setCustomView(LayoutInflater.from(WindowDecorActionBar.this.getThemedContext()).inflate(paramInt, null));
    }
    
    public ActionBar.Tab setCustomView(View paramView)
    {
      this.mCustomView = paramView;
      if (this.mPosition >= 0) {
        WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
      }
      return this;
    }
    
    public ActionBar.Tab setIcon(int paramInt)
    {
      return setIcon(AppCompatResources.getDrawable(WindowDecorActionBar.this.mContext, paramInt));
    }
    
    public ActionBar.Tab setIcon(Drawable paramDrawable)
    {
      this.mIcon = paramDrawable;
      if (this.mPosition >= 0) {
        WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
      }
      return this;
    }
    
    public void setPosition(int paramInt)
    {
      this.mPosition = paramInt;
    }
    
    public ActionBar.Tab setTabListener(ActionBar.TabListener paramTabListener)
    {
      this.mCallback = paramTabListener;
      return this;
    }
    
    public ActionBar.Tab setTag(Object paramObject)
    {
      this.mTag = paramObject;
      return this;
    }
    
    public ActionBar.Tab setText(int paramInt)
    {
      return setText(WindowDecorActionBar.this.mContext.getResources().getText(paramInt));
    }
    
    public ActionBar.Tab setText(CharSequence paramCharSequence)
    {
      this.mText = paramCharSequence;
      if (this.mPosition >= 0) {
        WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
      }
      return this;
    }
  }
}

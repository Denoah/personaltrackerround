package androidx.appcompat.app;

import android.app.Activity;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory2;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.Window;
import android.view.Window.Callback;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.R.attr;
import androidx.appcompat.R.color;
import androidx.appcompat.R.id;
import androidx.appcompat.R.layout;
import androidx.appcompat.R.style;
import androidx.appcompat.R.styleable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.StandaloneActionMode;
import androidx.appcompat.view.SupportActionModeWrapper.CallbackWrapper;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.WindowCallbackWrapper;
import androidx.appcompat.view.menu.ListMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuBuilder.Callback;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuPresenter.Callback;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.ContentFrameLayout.OnAttachListener;
import androidx.appcompat.widget.DecorContentParent;
import androidx.appcompat.widget.FitWindowsViewGroup;
import androidx.appcompat.widget.FitWindowsViewGroup.OnFitSystemWindowsListener;
import androidx.appcompat.widget.TintTypedArray;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ViewStubCompat;
import androidx.appcompat.widget.ViewUtils;
import androidx.collection.ArrayMap;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.core.view.KeyEventDispatcher;
import androidx.core.view.KeyEventDispatcher.Component;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.PopupWindowCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.LifecycleOwner;
import java.util.List;
import java.util.Map;

class AppCompatDelegateImpl
  extends AppCompatDelegate
  implements MenuBuilder.Callback, LayoutInflater.Factory2
{
  private static final boolean DEBUG = false;
  static final String EXCEPTION_HANDLER_MESSAGE_SUFFIX = ". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.";
  private static final boolean IS_PRE_LOLLIPOP;
  private static final boolean sAlwaysOverrideConfiguration;
  private static boolean sInstalledExceptionHandler;
  private static final Map<Class<?>, Integer> sLocalNightModes = new ArrayMap();
  private static final int[] sWindowBackgroundStyleable;
  ActionBar mActionBar;
  private ActionMenuPresenterCallback mActionMenuPresenterCallback;
  androidx.appcompat.view.ActionMode mActionMode;
  PopupWindow mActionModePopup;
  ActionBarContextView mActionModeView;
  private boolean mActivityHandlesUiMode;
  private boolean mActivityHandlesUiModeChecked;
  final AppCompatCallback mAppCompatCallback;
  private AppCompatViewInflater mAppCompatViewInflater;
  private AppCompatWindowCallback mAppCompatWindowCallback;
  private AutoNightModeManager mAutoBatteryNightModeManager;
  private AutoNightModeManager mAutoTimeNightModeManager;
  private boolean mBaseContextAttached;
  private boolean mClosingActionMenu;
  final Context mContext;
  private boolean mCreated;
  private DecorContentParent mDecorContentParent;
  private boolean mEnableDefaultActionBarUp;
  ViewPropertyAnimatorCompat mFadeAnim = null;
  private boolean mFeatureIndeterminateProgress;
  private boolean mFeatureProgress;
  private boolean mHandleNativeActionModes = true;
  boolean mHasActionBar;
  final Object mHost;
  int mInvalidatePanelMenuFeatures;
  boolean mInvalidatePanelMenuPosted;
  private final Runnable mInvalidatePanelMenuRunnable = new Runnable()
  {
    public void run()
    {
      if ((AppCompatDelegateImpl.this.mInvalidatePanelMenuFeatures & 0x1) != 0) {
        AppCompatDelegateImpl.this.doInvalidatePanelMenu(0);
      }
      if ((AppCompatDelegateImpl.this.mInvalidatePanelMenuFeatures & 0x1000) != 0) {
        AppCompatDelegateImpl.this.doInvalidatePanelMenu(108);
      }
      AppCompatDelegateImpl.this.mInvalidatePanelMenuPosted = false;
      AppCompatDelegateImpl.this.mInvalidatePanelMenuFeatures = 0;
    }
  };
  boolean mIsDestroyed;
  boolean mIsFloating;
  private int mLocalNightMode = -100;
  private boolean mLongPressBackDown;
  MenuInflater mMenuInflater;
  boolean mOverlayActionBar;
  boolean mOverlayActionMode;
  private PanelMenuPresenterCallback mPanelMenuPresenterCallback;
  private PanelFeatureState[] mPanels;
  private PanelFeatureState mPreparedPanel;
  Runnable mShowActionModePopup;
  private boolean mStarted;
  private View mStatusGuard;
  private ViewGroup mSubDecor;
  private boolean mSubDecorInstalled;
  private Rect mTempRect1;
  private Rect mTempRect2;
  private int mThemeResId;
  private CharSequence mTitle;
  private TextView mTitleView;
  Window mWindow;
  boolean mWindowNoTitle;
  
  static
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool1 = false;
    if (i < 21) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    IS_PRE_LOLLIPOP = bool2;
    sWindowBackgroundStyleable = new int[] { 16842836 };
    boolean bool2 = bool1;
    if (Build.VERSION.SDK_INT >= 21)
    {
      bool2 = bool1;
      if (Build.VERSION.SDK_INT <= 25) {
        bool2 = true;
      }
    }
    sAlwaysOverrideConfiguration = bool2;
    if ((IS_PRE_LOLLIPOP) && (!sInstalledExceptionHandler))
    {
      Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
      {
        private boolean shouldWrapException(Throwable paramAnonymousThrowable)
        {
          boolean bool1 = paramAnonymousThrowable instanceof Resources.NotFoundException;
          boolean bool2 = false;
          boolean bool3 = bool2;
          if (bool1)
          {
            paramAnonymousThrowable = paramAnonymousThrowable.getMessage();
            bool3 = bool2;
            if (paramAnonymousThrowable != null) {
              if (!paramAnonymousThrowable.contains("drawable"))
              {
                bool3 = bool2;
                if (!paramAnonymousThrowable.contains("Drawable")) {}
              }
              else
              {
                bool3 = true;
              }
            }
          }
          return bool3;
        }
        
        public void uncaughtException(Thread paramAnonymousThread, Throwable paramAnonymousThrowable)
        {
          if (shouldWrapException(paramAnonymousThrowable))
          {
            Object localObject = new StringBuilder();
            ((StringBuilder)localObject).append(paramAnonymousThrowable.getMessage());
            ((StringBuilder)localObject).append(". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.");
            localObject = new Resources.NotFoundException(((StringBuilder)localObject).toString());
            ((Throwable)localObject).initCause(paramAnonymousThrowable.getCause());
            ((Throwable)localObject).setStackTrace(paramAnonymousThrowable.getStackTrace());
            this.val$defHandler.uncaughtException(paramAnonymousThread, (Throwable)localObject);
          }
          else
          {
            this.val$defHandler.uncaughtException(paramAnonymousThread, paramAnonymousThrowable);
          }
        }
      });
      sInstalledExceptionHandler = true;
    }
  }
  
  AppCompatDelegateImpl(Activity paramActivity, AppCompatCallback paramAppCompatCallback)
  {
    this(paramActivity, null, paramAppCompatCallback, paramActivity);
  }
  
  AppCompatDelegateImpl(Dialog paramDialog, AppCompatCallback paramAppCompatCallback)
  {
    this(paramDialog.getContext(), paramDialog.getWindow(), paramAppCompatCallback, paramDialog);
  }
  
  AppCompatDelegateImpl(Context paramContext, Activity paramActivity, AppCompatCallback paramAppCompatCallback)
  {
    this(paramContext, null, paramAppCompatCallback, paramActivity);
  }
  
  AppCompatDelegateImpl(Context paramContext, Window paramWindow, AppCompatCallback paramAppCompatCallback)
  {
    this(paramContext, paramWindow, paramAppCompatCallback, paramContext);
  }
  
  private AppCompatDelegateImpl(Context paramContext, Window paramWindow, AppCompatCallback paramAppCompatCallback, Object paramObject)
  {
    this.mContext = paramContext;
    this.mAppCompatCallback = paramAppCompatCallback;
    this.mHost = paramObject;
    if ((this.mLocalNightMode == -100) && ((paramObject instanceof Dialog)))
    {
      paramContext = tryUnwrapContext();
      if (paramContext != null) {
        this.mLocalNightMode = paramContext.getDelegate().getLocalNightMode();
      }
    }
    if (this.mLocalNightMode == -100)
    {
      paramContext = (Integer)sLocalNightModes.get(this.mHost.getClass());
      if (paramContext != null)
      {
        this.mLocalNightMode = paramContext.intValue();
        sLocalNightModes.remove(this.mHost.getClass());
      }
    }
    if (paramWindow != null) {
      attachToWindow(paramWindow);
    }
    AppCompatDrawableManager.preload();
  }
  
  private boolean applyDayNight(boolean paramBoolean)
  {
    if (this.mIsDestroyed) {
      return false;
    }
    int i = calculateNightMode();
    paramBoolean = updateForNightMode(mapNightMode(i), paramBoolean);
    AutoNightModeManager localAutoNightModeManager;
    if (i == 0)
    {
      getAutoTimeNightModeManager().setup();
    }
    else
    {
      localAutoNightModeManager = this.mAutoTimeNightModeManager;
      if (localAutoNightModeManager != null) {
        localAutoNightModeManager.cleanup();
      }
    }
    if (i == 3)
    {
      getAutoBatteryNightModeManager().setup();
    }
    else
    {
      localAutoNightModeManager = this.mAutoBatteryNightModeManager;
      if (localAutoNightModeManager != null) {
        localAutoNightModeManager.cleanup();
      }
    }
    return paramBoolean;
  }
  
  private void applyFixedSizeWindow()
  {
    ContentFrameLayout localContentFrameLayout = (ContentFrameLayout)this.mSubDecor.findViewById(16908290);
    Object localObject = this.mWindow.getDecorView();
    localContentFrameLayout.setDecorPadding(((View)localObject).getPaddingLeft(), ((View)localObject).getPaddingTop(), ((View)localObject).getPaddingRight(), ((View)localObject).getPaddingBottom());
    localObject = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme);
    ((TypedArray)localObject).getValue(R.styleable.AppCompatTheme_windowMinWidthMajor, localContentFrameLayout.getMinWidthMajor());
    ((TypedArray)localObject).getValue(R.styleable.AppCompatTheme_windowMinWidthMinor, localContentFrameLayout.getMinWidthMinor());
    if (((TypedArray)localObject).hasValue(R.styleable.AppCompatTheme_windowFixedWidthMajor)) {
      ((TypedArray)localObject).getValue(R.styleable.AppCompatTheme_windowFixedWidthMajor, localContentFrameLayout.getFixedWidthMajor());
    }
    if (((TypedArray)localObject).hasValue(R.styleable.AppCompatTheme_windowFixedWidthMinor)) {
      ((TypedArray)localObject).getValue(R.styleable.AppCompatTheme_windowFixedWidthMinor, localContentFrameLayout.getFixedWidthMinor());
    }
    if (((TypedArray)localObject).hasValue(R.styleable.AppCompatTheme_windowFixedHeightMajor)) {
      ((TypedArray)localObject).getValue(R.styleable.AppCompatTheme_windowFixedHeightMajor, localContentFrameLayout.getFixedHeightMajor());
    }
    if (((TypedArray)localObject).hasValue(R.styleable.AppCompatTheme_windowFixedHeightMinor)) {
      ((TypedArray)localObject).getValue(R.styleable.AppCompatTheme_windowFixedHeightMinor, localContentFrameLayout.getFixedHeightMinor());
    }
    ((TypedArray)localObject).recycle();
    localContentFrameLayout.requestLayout();
  }
  
  private void attachToWindow(Window paramWindow)
  {
    if (this.mWindow == null)
    {
      Object localObject = paramWindow.getCallback();
      if (!(localObject instanceof AppCompatWindowCallback))
      {
        localObject = new AppCompatWindowCallback((Window.Callback)localObject);
        this.mAppCompatWindowCallback = ((AppCompatWindowCallback)localObject);
        paramWindow.setCallback((Window.Callback)localObject);
        TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(this.mContext, null, sWindowBackgroundStyleable);
        localObject = localTintTypedArray.getDrawableIfKnown(0);
        if (localObject != null) {
          paramWindow.setBackgroundDrawable((Drawable)localObject);
        }
        localTintTypedArray.recycle();
        this.mWindow = paramWindow;
        return;
      }
      throw new IllegalStateException("AppCompat has already installed itself into the Window");
    }
    throw new IllegalStateException("AppCompat has already installed itself into the Window");
  }
  
  private int calculateNightMode()
  {
    int i = this.mLocalNightMode;
    if (i == -100) {
      i = getDefaultNightMode();
    }
    return i;
  }
  
  private void cleanupAutoManagers()
  {
    AutoNightModeManager localAutoNightModeManager = this.mAutoTimeNightModeManager;
    if (localAutoNightModeManager != null) {
      localAutoNightModeManager.cleanup();
    }
    localAutoNightModeManager = this.mAutoBatteryNightModeManager;
    if (localAutoNightModeManager != null) {
      localAutoNightModeManager.cleanup();
    }
  }
  
  private ViewGroup createSubDecor()
  {
    Object localObject1 = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme);
    if (((TypedArray)localObject1).hasValue(R.styleable.AppCompatTheme_windowActionBar))
    {
      if (((TypedArray)localObject1).getBoolean(R.styleable.AppCompatTheme_windowNoTitle, false)) {
        requestWindowFeature(1);
      } else if (((TypedArray)localObject1).getBoolean(R.styleable.AppCompatTheme_windowActionBar, false)) {
        requestWindowFeature(108);
      }
      if (((TypedArray)localObject1).getBoolean(R.styleable.AppCompatTheme_windowActionBarOverlay, false)) {
        requestWindowFeature(109);
      }
      if (((TypedArray)localObject1).getBoolean(R.styleable.AppCompatTheme_windowActionModeOverlay, false)) {
        requestWindowFeature(10);
      }
      this.mIsFloating = ((TypedArray)localObject1).getBoolean(R.styleable.AppCompatTheme_android_windowIsFloating, false);
      ((TypedArray)localObject1).recycle();
      ensureWindow();
      this.mWindow.getDecorView();
      localObject1 = LayoutInflater.from(this.mContext);
      Object localObject2;
      if (!this.mWindowNoTitle)
      {
        if (this.mIsFloating)
        {
          localObject1 = (ViewGroup)((LayoutInflater)localObject1).inflate(R.layout.abc_dialog_title_material, null);
          this.mOverlayActionBar = false;
          this.mHasActionBar = false;
        }
        else if (this.mHasActionBar)
        {
          localObject1 = new TypedValue();
          this.mContext.getTheme().resolveAttribute(R.attr.actionBarTheme, (TypedValue)localObject1, true);
          if (((TypedValue)localObject1).resourceId != 0) {
            localObject1 = new androidx.appcompat.view.ContextThemeWrapper(this.mContext, ((TypedValue)localObject1).resourceId);
          } else {
            localObject1 = this.mContext;
          }
          localObject2 = (ViewGroup)LayoutInflater.from((Context)localObject1).inflate(R.layout.abc_screen_toolbar, null);
          localObject1 = (DecorContentParent)((ViewGroup)localObject2).findViewById(R.id.decor_content_parent);
          this.mDecorContentParent = ((DecorContentParent)localObject1);
          ((DecorContentParent)localObject1).setWindowCallback(getWindowCallback());
          if (this.mOverlayActionBar) {
            this.mDecorContentParent.initFeature(109);
          }
          if (this.mFeatureProgress) {
            this.mDecorContentParent.initFeature(2);
          }
          localObject1 = localObject2;
          if (this.mFeatureIndeterminateProgress)
          {
            this.mDecorContentParent.initFeature(5);
            localObject1 = localObject2;
          }
        }
        else
        {
          localObject1 = null;
        }
      }
      else
      {
        if (this.mOverlayActionMode) {
          localObject1 = (ViewGroup)((LayoutInflater)localObject1).inflate(R.layout.abc_screen_simple_overlay_action_mode, null);
        } else {
          localObject1 = (ViewGroup)((LayoutInflater)localObject1).inflate(R.layout.abc_screen_simple, null);
        }
        if (Build.VERSION.SDK_INT >= 21) {
          ViewCompat.setOnApplyWindowInsetsListener((View)localObject1, new OnApplyWindowInsetsListener()
          {
            public WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat)
            {
              int i = paramAnonymousWindowInsetsCompat.getSystemWindowInsetTop();
              int j = AppCompatDelegateImpl.this.updateStatusGuard(i);
              WindowInsetsCompat localWindowInsetsCompat = paramAnonymousWindowInsetsCompat;
              if (i != j) {
                localWindowInsetsCompat = paramAnonymousWindowInsetsCompat.replaceSystemWindowInsets(paramAnonymousWindowInsetsCompat.getSystemWindowInsetLeft(), j, paramAnonymousWindowInsetsCompat.getSystemWindowInsetRight(), paramAnonymousWindowInsetsCompat.getSystemWindowInsetBottom());
              }
              return ViewCompat.onApplyWindowInsets(paramAnonymousView, localWindowInsetsCompat);
            }
          });
        } else {
          ((FitWindowsViewGroup)localObject1).setOnFitSystemWindowsListener(new FitWindowsViewGroup.OnFitSystemWindowsListener()
          {
            public void onFitSystemWindows(Rect paramAnonymousRect)
            {
              paramAnonymousRect.top = AppCompatDelegateImpl.this.updateStatusGuard(paramAnonymousRect.top);
            }
          });
        }
      }
      if (localObject1 != null)
      {
        if (this.mDecorContentParent == null) {
          this.mTitleView = ((TextView)((ViewGroup)localObject1).findViewById(R.id.title));
        }
        ViewUtils.makeOptionalFitsSystemWindows((View)localObject1);
        localObject2 = (ContentFrameLayout)((ViewGroup)localObject1).findViewById(R.id.action_bar_activity_content);
        ViewGroup localViewGroup = (ViewGroup)this.mWindow.findViewById(16908290);
        if (localViewGroup != null)
        {
          while (localViewGroup.getChildCount() > 0)
          {
            View localView = localViewGroup.getChildAt(0);
            localViewGroup.removeViewAt(0);
            ((ContentFrameLayout)localObject2).addView(localView);
          }
          localViewGroup.setId(-1);
          ((ContentFrameLayout)localObject2).setId(16908290);
          if ((localViewGroup instanceof FrameLayout)) {
            ((FrameLayout)localViewGroup).setForeground(null);
          }
        }
        this.mWindow.setContentView((View)localObject1);
        ((ContentFrameLayout)localObject2).setAttachListener(new ContentFrameLayout.OnAttachListener()
        {
          public void onAttachedFromWindow() {}
          
          public void onDetachedFromWindow()
          {
            AppCompatDelegateImpl.this.dismissPopups();
          }
        });
        return localObject1;
      }
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("AppCompat does not support the current theme features: { windowActionBar: ");
      ((StringBuilder)localObject1).append(this.mHasActionBar);
      ((StringBuilder)localObject1).append(", windowActionBarOverlay: ");
      ((StringBuilder)localObject1).append(this.mOverlayActionBar);
      ((StringBuilder)localObject1).append(", android:windowIsFloating: ");
      ((StringBuilder)localObject1).append(this.mIsFloating);
      ((StringBuilder)localObject1).append(", windowActionModeOverlay: ");
      ((StringBuilder)localObject1).append(this.mOverlayActionMode);
      ((StringBuilder)localObject1).append(", windowNoTitle: ");
      ((StringBuilder)localObject1).append(this.mWindowNoTitle);
      ((StringBuilder)localObject1).append(" }");
      throw new IllegalArgumentException(((StringBuilder)localObject1).toString());
    }
    ((TypedArray)localObject1).recycle();
    throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
  }
  
  private void ensureSubDecor()
  {
    if (!this.mSubDecorInstalled)
    {
      this.mSubDecor = createSubDecor();
      Object localObject1 = getTitle();
      if (!TextUtils.isEmpty((CharSequence)localObject1))
      {
        Object localObject2 = this.mDecorContentParent;
        if (localObject2 != null)
        {
          ((DecorContentParent)localObject2).setWindowTitle((CharSequence)localObject1);
        }
        else if (peekSupportActionBar() != null)
        {
          peekSupportActionBar().setWindowTitle((CharSequence)localObject1);
        }
        else
        {
          localObject2 = this.mTitleView;
          if (localObject2 != null) {
            ((TextView)localObject2).setText((CharSequence)localObject1);
          }
        }
      }
      applyFixedSizeWindow();
      onSubDecorInstalled(this.mSubDecor);
      this.mSubDecorInstalled = true;
      localObject1 = getPanelState(0, false);
      if ((!this.mIsDestroyed) && ((localObject1 == null) || (((PanelFeatureState)localObject1).menu == null))) {
        invalidatePanelMenu(108);
      }
    }
  }
  
  private void ensureWindow()
  {
    if (this.mWindow == null)
    {
      Object localObject = this.mHost;
      if ((localObject instanceof Activity)) {
        attachToWindow(((Activity)localObject).getWindow());
      }
    }
    if (this.mWindow != null) {
      return;
    }
    throw new IllegalStateException("We have not been given a Window");
  }
  
  private AutoNightModeManager getAutoBatteryNightModeManager()
  {
    if (this.mAutoBatteryNightModeManager == null) {
      this.mAutoBatteryNightModeManager = new AutoBatteryNightModeManager(this.mContext);
    }
    return this.mAutoBatteryNightModeManager;
  }
  
  private void initWindowDecorActionBar()
  {
    ensureSubDecor();
    if ((this.mHasActionBar) && (this.mActionBar == null))
    {
      Object localObject = this.mHost;
      if ((localObject instanceof Activity)) {
        this.mActionBar = new WindowDecorActionBar((Activity)this.mHost, this.mOverlayActionBar);
      } else if ((localObject instanceof Dialog)) {
        this.mActionBar = new WindowDecorActionBar((Dialog)this.mHost);
      }
      localObject = this.mActionBar;
      if (localObject != null) {
        ((ActionBar)localObject).setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp);
      }
    }
  }
  
  private boolean initializePanelContent(PanelFeatureState paramPanelFeatureState)
  {
    View localView = paramPanelFeatureState.createdPanelView;
    boolean bool = true;
    if (localView != null)
    {
      paramPanelFeatureState.shownPanelView = paramPanelFeatureState.createdPanelView;
      return true;
    }
    if (paramPanelFeatureState.menu == null) {
      return false;
    }
    if (this.mPanelMenuPresenterCallback == null) {
      this.mPanelMenuPresenterCallback = new PanelMenuPresenterCallback();
    }
    paramPanelFeatureState.shownPanelView = ((View)paramPanelFeatureState.getListMenuView(this.mPanelMenuPresenterCallback));
    if (paramPanelFeatureState.shownPanelView == null) {
      bool = false;
    }
    return bool;
  }
  
  private boolean initializePanelDecor(PanelFeatureState paramPanelFeatureState)
  {
    paramPanelFeatureState.setStyle(getActionBarThemedContext());
    paramPanelFeatureState.decorView = new ListMenuDecorView(paramPanelFeatureState.listPresenterContext);
    paramPanelFeatureState.gravity = 81;
    return true;
  }
  
  private boolean initializePanelMenu(PanelFeatureState paramPanelFeatureState)
  {
    Context localContext = this.mContext;
    if (paramPanelFeatureState.featureId != 0)
    {
      localObject1 = localContext;
      if (paramPanelFeatureState.featureId != 108) {}
    }
    else
    {
      localObject1 = localContext;
      if (this.mDecorContentParent != null)
      {
        TypedValue localTypedValue = new TypedValue();
        Resources.Theme localTheme = localContext.getTheme();
        localTheme.resolveAttribute(R.attr.actionBarTheme, localTypedValue, true);
        localObject1 = null;
        if (localTypedValue.resourceId != 0)
        {
          localObject1 = localContext.getResources().newTheme();
          ((Resources.Theme)localObject1).setTo(localTheme);
          ((Resources.Theme)localObject1).applyStyle(localTypedValue.resourceId, true);
          ((Resources.Theme)localObject1).resolveAttribute(R.attr.actionBarWidgetTheme, localTypedValue, true);
        }
        else
        {
          localTheme.resolveAttribute(R.attr.actionBarWidgetTheme, localTypedValue, true);
        }
        Object localObject2 = localObject1;
        if (localTypedValue.resourceId != 0)
        {
          localObject2 = localObject1;
          if (localObject1 == null)
          {
            localObject2 = localContext.getResources().newTheme();
            ((Resources.Theme)localObject2).setTo(localTheme);
          }
          ((Resources.Theme)localObject2).applyStyle(localTypedValue.resourceId, true);
        }
        localObject1 = localContext;
        if (localObject2 != null)
        {
          localObject1 = new androidx.appcompat.view.ContextThemeWrapper(localContext, 0);
          ((Context)localObject1).getTheme().setTo((Resources.Theme)localObject2);
        }
      }
    }
    Object localObject1 = new MenuBuilder((Context)localObject1);
    ((MenuBuilder)localObject1).setCallback(this);
    paramPanelFeatureState.setMenu((MenuBuilder)localObject1);
    return true;
  }
  
  private void invalidatePanelMenu(int paramInt)
  {
    this.mInvalidatePanelMenuFeatures = (1 << paramInt | this.mInvalidatePanelMenuFeatures);
    if (!this.mInvalidatePanelMenuPosted)
    {
      ViewCompat.postOnAnimation(this.mWindow.getDecorView(), this.mInvalidatePanelMenuRunnable);
      this.mInvalidatePanelMenuPosted = true;
    }
  }
  
  private boolean isActivityManifestHandlingUiMode()
  {
    if ((!this.mActivityHandlesUiModeChecked) && ((this.mHost instanceof Activity)))
    {
      Object localObject = this.mContext.getPackageManager();
      if (localObject == null) {
        return false;
      }
      try
      {
        ComponentName localComponentName = new android/content/ComponentName;
        localComponentName.<init>(this.mContext, this.mHost.getClass());
        localObject = ((PackageManager)localObject).getActivityInfo(localComponentName, 0);
        boolean bool;
        if ((localObject != null) && ((((ActivityInfo)localObject).configChanges & 0x200) != 0)) {
          bool = true;
        } else {
          bool = false;
        }
        this.mActivityHandlesUiMode = bool;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        Log.d("AppCompatDelegate", "Exception while getting ActivityInfo", localNameNotFoundException);
        this.mActivityHandlesUiMode = false;
      }
    }
    this.mActivityHandlesUiModeChecked = true;
    return this.mActivityHandlesUiMode;
  }
  
  private boolean onKeyDownPanel(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getRepeatCount() == 0)
    {
      PanelFeatureState localPanelFeatureState = getPanelState(paramInt, true);
      if (!localPanelFeatureState.isOpen) {
        return preparePanel(localPanelFeatureState, paramKeyEvent);
      }
    }
    return false;
  }
  
  private boolean onKeyUpPanel(int paramInt, KeyEvent paramKeyEvent)
  {
    if (this.mActionMode != null) {
      return false;
    }
    boolean bool1 = true;
    PanelFeatureState localPanelFeatureState = getPanelState(paramInt, true);
    boolean bool2;
    if (paramInt == 0)
    {
      DecorContentParent localDecorContentParent = this.mDecorContentParent;
      if ((localDecorContentParent != null) && (localDecorContentParent.canShowOverflowMenu()) && (!ViewConfiguration.get(this.mContext).hasPermanentMenuKey()))
      {
        if (!this.mDecorContentParent.isOverflowMenuShowing())
        {
          if ((this.mIsDestroyed) || (!preparePanel(localPanelFeatureState, paramKeyEvent))) {
            break label188;
          }
          bool2 = this.mDecorContentParent.showOverflowMenu();
          break label208;
        }
        bool2 = this.mDecorContentParent.hideOverflowMenu();
        break label208;
      }
    }
    if ((!localPanelFeatureState.isOpen) && (!localPanelFeatureState.isHandled))
    {
      if (localPanelFeatureState.isPrepared)
      {
        if (localPanelFeatureState.refreshMenuContent)
        {
          localPanelFeatureState.isPrepared = false;
          bool2 = preparePanel(localPanelFeatureState, paramKeyEvent);
        }
        else
        {
          bool2 = true;
        }
        if (bool2)
        {
          openPanel(localPanelFeatureState, paramKeyEvent);
          bool2 = bool1;
          break label208;
        }
      }
      label188:
      bool2 = false;
    }
    else
    {
      bool2 = localPanelFeatureState.isOpen;
      closePanel(localPanelFeatureState, true);
    }
    label208:
    if (bool2)
    {
      paramKeyEvent = (AudioManager)this.mContext.getSystemService("audio");
      if (paramKeyEvent != null) {
        paramKeyEvent.playSoundEffect(0);
      } else {
        Log.w("AppCompatDelegate", "Couldn't get audio manager");
      }
    }
    return bool2;
  }
  
  private void openPanel(PanelFeatureState paramPanelFeatureState, KeyEvent paramKeyEvent)
  {
    if ((!paramPanelFeatureState.isOpen) && (!this.mIsDestroyed))
    {
      if (paramPanelFeatureState.featureId == 0)
      {
        if ((this.mContext.getResources().getConfiguration().screenLayout & 0xF) == 4) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0) {
          return;
        }
      }
      Object localObject = getWindowCallback();
      if ((localObject != null) && (!((Window.Callback)localObject).onMenuOpened(paramPanelFeatureState.featureId, paramPanelFeatureState.menu)))
      {
        closePanel(paramPanelFeatureState, true);
        return;
      }
      WindowManager localWindowManager = (WindowManager)this.mContext.getSystemService("window");
      if (localWindowManager == null) {
        return;
      }
      if (!preparePanel(paramPanelFeatureState, paramKeyEvent)) {
        return;
      }
      if ((paramPanelFeatureState.decorView != null) && (!paramPanelFeatureState.refreshDecorView))
      {
        if (paramPanelFeatureState.createdPanelView != null)
        {
          paramKeyEvent = paramPanelFeatureState.createdPanelView.getLayoutParams();
          if ((paramKeyEvent != null) && (paramKeyEvent.width == -1))
          {
            i = -1;
            break label341;
          }
        }
      }
      else
      {
        if (paramPanelFeatureState.decorView == null)
        {
          if ((initializePanelDecor(paramPanelFeatureState)) && (paramPanelFeatureState.decorView != null)) {}
        }
        else if ((paramPanelFeatureState.refreshDecorView) && (paramPanelFeatureState.decorView.getChildCount() > 0)) {
          paramPanelFeatureState.decorView.removeAllViews();
        }
        if ((!initializePanelContent(paramPanelFeatureState)) || (!paramPanelFeatureState.hasPanelItems())) {
          return;
        }
        localObject = paramPanelFeatureState.shownPanelView.getLayoutParams();
        paramKeyEvent = (KeyEvent)localObject;
        if (localObject == null) {
          paramKeyEvent = new ViewGroup.LayoutParams(-2, -2);
        }
        i = paramPanelFeatureState.background;
        paramPanelFeatureState.decorView.setBackgroundResource(i);
        localObject = paramPanelFeatureState.shownPanelView.getParent();
        if ((localObject instanceof ViewGroup)) {
          ((ViewGroup)localObject).removeView(paramPanelFeatureState.shownPanelView);
        }
        paramPanelFeatureState.decorView.addView(paramPanelFeatureState.shownPanelView, paramKeyEvent);
        if (!paramPanelFeatureState.shownPanelView.hasFocus()) {
          paramPanelFeatureState.shownPanelView.requestFocus();
        }
      }
      int i = -2;
      label341:
      paramPanelFeatureState.isHandled = false;
      paramKeyEvent = new WindowManager.LayoutParams(i, -2, paramPanelFeatureState.x, paramPanelFeatureState.y, 1002, 8519680, -3);
      paramKeyEvent.gravity = paramPanelFeatureState.gravity;
      paramKeyEvent.windowAnimations = paramPanelFeatureState.windowAnimations;
      localWindowManager.addView(paramPanelFeatureState.decorView, paramKeyEvent);
      paramPanelFeatureState.isOpen = true;
    }
  }
  
  private boolean performPanelShortcut(PanelFeatureState paramPanelFeatureState, int paramInt1, KeyEvent paramKeyEvent, int paramInt2)
  {
    boolean bool1 = paramKeyEvent.isSystem();
    boolean bool2 = false;
    if (bool1) {
      return false;
    }
    if (!paramPanelFeatureState.isPrepared)
    {
      bool1 = bool2;
      if (!preparePanel(paramPanelFeatureState, paramKeyEvent)) {}
    }
    else
    {
      bool1 = bool2;
      if (paramPanelFeatureState.menu != null) {
        bool1 = paramPanelFeatureState.menu.performShortcut(paramInt1, paramKeyEvent, paramInt2);
      }
    }
    if ((bool1) && ((paramInt2 & 0x1) == 0) && (this.mDecorContentParent == null)) {
      closePanel(paramPanelFeatureState, true);
    }
    return bool1;
  }
  
  private boolean preparePanel(PanelFeatureState paramPanelFeatureState, KeyEvent paramKeyEvent)
  {
    if (this.mIsDestroyed) {
      return false;
    }
    if (paramPanelFeatureState.isPrepared) {
      return true;
    }
    Object localObject = this.mPreparedPanel;
    if ((localObject != null) && (localObject != paramPanelFeatureState)) {
      closePanel((PanelFeatureState)localObject, false);
    }
    Window.Callback localCallback = getWindowCallback();
    if (localCallback != null) {
      paramPanelFeatureState.createdPanelView = localCallback.onCreatePanelView(paramPanelFeatureState.featureId);
    }
    int i;
    if ((paramPanelFeatureState.featureId != 0) && (paramPanelFeatureState.featureId != 108)) {
      i = 0;
    } else {
      i = 1;
    }
    if (i != 0)
    {
      localObject = this.mDecorContentParent;
      if (localObject != null) {
        ((DecorContentParent)localObject).setMenuPrepared();
      }
    }
    if ((paramPanelFeatureState.createdPanelView == null) && ((i == 0) || (!(peekSupportActionBar() instanceof ToolbarActionBar))))
    {
      if ((paramPanelFeatureState.menu == null) || (paramPanelFeatureState.refreshMenuContent))
      {
        if ((paramPanelFeatureState.menu == null) && ((!initializePanelMenu(paramPanelFeatureState)) || (paramPanelFeatureState.menu == null))) {
          return false;
        }
        if ((i != 0) && (this.mDecorContentParent != null))
        {
          if (this.mActionMenuPresenterCallback == null) {
            this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback();
          }
          this.mDecorContentParent.setMenu(paramPanelFeatureState.menu, this.mActionMenuPresenterCallback);
        }
        paramPanelFeatureState.menu.stopDispatchingItemsChanged();
        if (!localCallback.onCreatePanelMenu(paramPanelFeatureState.featureId, paramPanelFeatureState.menu))
        {
          paramPanelFeatureState.setMenu(null);
          if (i != 0)
          {
            paramPanelFeatureState = this.mDecorContentParent;
            if (paramPanelFeatureState != null) {
              paramPanelFeatureState.setMenu(null, this.mActionMenuPresenterCallback);
            }
          }
          return false;
        }
        paramPanelFeatureState.refreshMenuContent = false;
      }
      paramPanelFeatureState.menu.stopDispatchingItemsChanged();
      if (paramPanelFeatureState.frozenActionViewState != null)
      {
        paramPanelFeatureState.menu.restoreActionViewStates(paramPanelFeatureState.frozenActionViewState);
        paramPanelFeatureState.frozenActionViewState = null;
      }
      if (!localCallback.onPreparePanel(0, paramPanelFeatureState.createdPanelView, paramPanelFeatureState.menu))
      {
        if (i != 0)
        {
          paramKeyEvent = this.mDecorContentParent;
          if (paramKeyEvent != null) {
            paramKeyEvent.setMenu(null, this.mActionMenuPresenterCallback);
          }
        }
        paramPanelFeatureState.menu.startDispatchingItemsChanged();
        return false;
      }
      if (paramKeyEvent != null) {
        i = paramKeyEvent.getDeviceId();
      } else {
        i = -1;
      }
      boolean bool;
      if (KeyCharacterMap.load(i).getKeyboardType() != 1) {
        bool = true;
      } else {
        bool = false;
      }
      paramPanelFeatureState.qwertyMode = bool;
      paramPanelFeatureState.menu.setQwertyMode(paramPanelFeatureState.qwertyMode);
      paramPanelFeatureState.menu.startDispatchingItemsChanged();
    }
    paramPanelFeatureState.isPrepared = true;
    paramPanelFeatureState.isHandled = false;
    this.mPreparedPanel = paramPanelFeatureState;
    return true;
  }
  
  private void reopenMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
  {
    paramMenuBuilder = this.mDecorContentParent;
    if ((paramMenuBuilder != null) && (paramMenuBuilder.canShowOverflowMenu()) && ((!ViewConfiguration.get(this.mContext).hasPermanentMenuKey()) || (this.mDecorContentParent.isOverflowMenuShowPending())))
    {
      Window.Callback localCallback = getWindowCallback();
      if ((this.mDecorContentParent.isOverflowMenuShowing()) && (paramBoolean))
      {
        this.mDecorContentParent.hideOverflowMenu();
        if (!this.mIsDestroyed) {
          localCallback.onPanelClosed(108, getPanelState(0, true).menu);
        }
      }
      else if ((localCallback != null) && (!this.mIsDestroyed))
      {
        if ((this.mInvalidatePanelMenuPosted) && ((this.mInvalidatePanelMenuFeatures & 0x1) != 0))
        {
          this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
          this.mInvalidatePanelMenuRunnable.run();
        }
        paramMenuBuilder = getPanelState(0, true);
        if ((paramMenuBuilder.menu != null) && (!paramMenuBuilder.refreshMenuContent) && (localCallback.onPreparePanel(0, paramMenuBuilder.createdPanelView, paramMenuBuilder.menu)))
        {
          localCallback.onMenuOpened(108, paramMenuBuilder.menu);
          this.mDecorContentParent.showOverflowMenu();
        }
      }
      return;
    }
    paramMenuBuilder = getPanelState(0, true);
    paramMenuBuilder.refreshDecorView = true;
    closePanel(paramMenuBuilder, false);
    openPanel(paramMenuBuilder, null);
  }
  
  private int sanitizeWindowFeatureId(int paramInt)
  {
    if (paramInt == 8)
    {
      Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
      return 108;
    }
    int i = paramInt;
    if (paramInt == 9)
    {
      Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
      i = 109;
    }
    return i;
  }
  
  private boolean shouldInheritContext(ViewParent paramViewParent)
  {
    if (paramViewParent == null) {
      return false;
    }
    View localView = this.mWindow.getDecorView();
    for (;;)
    {
      if (paramViewParent == null) {
        return true;
      }
      if ((paramViewParent == localView) || (!(paramViewParent instanceof View)) || (ViewCompat.isAttachedToWindow((View)paramViewParent))) {
        break;
      }
      paramViewParent = paramViewParent.getParent();
    }
    return false;
  }
  
  private void throwFeatureRequestIfSubDecorInstalled()
  {
    if (!this.mSubDecorInstalled) {
      return;
    }
    throw new AndroidRuntimeException("Window feature must be requested before adding content");
  }
  
  private AppCompatActivity tryUnwrapContext()
  {
    for (Context localContext = this.mContext; localContext != null; localContext = ((ContextWrapper)localContext).getBaseContext())
    {
      if ((localContext instanceof AppCompatActivity)) {
        return (AppCompatActivity)localContext;
      }
      if (!(localContext instanceof ContextWrapper)) {
        break;
      }
    }
    return null;
  }
  
  private boolean updateForNightMode(int paramInt, boolean paramBoolean)
  {
    int i = this.mContext.getApplicationContext().getResources().getConfiguration().uiMode & 0x30;
    boolean bool1 = true;
    int j;
    if (paramInt != 1)
    {
      if (paramInt != 2) {
        j = i;
      } else {
        j = 32;
      }
    }
    else {
      j = 16;
    }
    boolean bool2 = isActivityManifestHandlingUiMode();
    boolean bool3 = sAlwaysOverrideConfiguration;
    boolean bool4 = false;
    if (!bool3)
    {
      bool3 = bool4;
      if (j == i) {}
    }
    else
    {
      bool3 = bool4;
      if (!bool2)
      {
        bool3 = bool4;
        if (Build.VERSION.SDK_INT >= 17)
        {
          bool3 = bool4;
          if (!this.mBaseContextAttached)
          {
            bool3 = bool4;
            if ((this.mHost instanceof android.view.ContextThemeWrapper))
            {
              Configuration localConfiguration = new Configuration();
              localConfiguration.uiMode = (localConfiguration.uiMode & 0xFFFFFFCF | j);
              try
              {
                ((android.view.ContextThemeWrapper)this.mHost).applyOverrideConfiguration(localConfiguration);
                bool3 = true;
              }
              catch (IllegalStateException localIllegalStateException)
              {
                Log.e("AppCompatDelegate", "updateForNightMode. Calling applyOverrideConfiguration() failed with an exception. Will fall back to using Resources.updateConfiguration()", localIllegalStateException);
                bool3 = bool4;
              }
            }
          }
        }
      }
    }
    i = this.mContext.getResources().getConfiguration().uiMode & 0x30;
    bool4 = bool3;
    Object localObject;
    if (!bool3)
    {
      bool4 = bool3;
      if (i != j)
      {
        bool4 = bool3;
        if (paramBoolean)
        {
          bool4 = bool3;
          if (!bool2)
          {
            bool4 = bool3;
            if (this.mBaseContextAttached) {
              if (Build.VERSION.SDK_INT < 17)
              {
                bool4 = bool3;
                if (!this.mCreated) {}
              }
              else
              {
                localObject = this.mHost;
                bool4 = bool3;
                if ((localObject instanceof Activity))
                {
                  ActivityCompat.recreate((Activity)localObject);
                  bool4 = true;
                }
              }
            }
          }
        }
      }
    }
    if ((!bool4) && (i != j))
    {
      updateResourcesConfigurationForNightMode(j, bool2);
      paramBoolean = bool1;
    }
    else
    {
      paramBoolean = bool4;
    }
    if (paramBoolean)
    {
      localObject = this.mHost;
      if ((localObject instanceof AppCompatActivity)) {
        ((AppCompatActivity)localObject).onNightModeChanged(paramInt);
      }
    }
    return paramBoolean;
  }
  
  private void updateResourcesConfigurationForNightMode(int paramInt, boolean paramBoolean)
  {
    Object localObject = this.mContext.getResources();
    Configuration localConfiguration = new Configuration(((Resources)localObject).getConfiguration());
    localConfiguration.uiMode = (paramInt | ((Resources)localObject).getConfiguration().uiMode & 0xFFFFFFCF);
    ((Resources)localObject).updateConfiguration(localConfiguration, null);
    if (Build.VERSION.SDK_INT < 26) {
      ResourcesFlusher.flush((Resources)localObject);
    }
    paramInt = this.mThemeResId;
    if (paramInt != 0)
    {
      this.mContext.setTheme(paramInt);
      if (Build.VERSION.SDK_INT >= 23) {
        this.mContext.getTheme().applyStyle(this.mThemeResId, true);
      }
    }
    if (paramBoolean)
    {
      localObject = this.mHost;
      if ((localObject instanceof Activity))
      {
        localObject = (Activity)localObject;
        if ((localObject instanceof LifecycleOwner))
        {
          if (((LifecycleOwner)localObject).getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((Activity)localObject).onConfigurationChanged(localConfiguration);
          }
        }
        else if (this.mStarted) {
          ((Activity)localObject).onConfigurationChanged(localConfiguration);
        }
      }
    }
  }
  
  public void addContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    ensureSubDecor();
    ((ViewGroup)this.mSubDecor.findViewById(16908290)).addView(paramView, paramLayoutParams);
    this.mAppCompatWindowCallback.getWrapped().onContentChanged();
  }
  
  public boolean applyDayNight()
  {
    return applyDayNight(true);
  }
  
  public void attachBaseContext(Context paramContext)
  {
    applyDayNight(false);
    this.mBaseContextAttached = true;
  }
  
  void callOnPanelClosed(int paramInt, PanelFeatureState paramPanelFeatureState, Menu paramMenu)
  {
    Object localObject1 = paramPanelFeatureState;
    Object localObject2 = paramMenu;
    if (paramMenu == null)
    {
      PanelFeatureState localPanelFeatureState = paramPanelFeatureState;
      if (paramPanelFeatureState == null)
      {
        localPanelFeatureState = paramPanelFeatureState;
        if (paramInt >= 0)
        {
          localObject1 = this.mPanels;
          localPanelFeatureState = paramPanelFeatureState;
          if (paramInt < localObject1.length) {
            localPanelFeatureState = localObject1[paramInt];
          }
        }
      }
      localObject1 = localPanelFeatureState;
      localObject2 = paramMenu;
      if (localPanelFeatureState != null)
      {
        localObject2 = localPanelFeatureState.menu;
        localObject1 = localPanelFeatureState;
      }
    }
    if ((localObject1 != null) && (!((PanelFeatureState)localObject1).isOpen)) {
      return;
    }
    if (!this.mIsDestroyed) {
      this.mAppCompatWindowCallback.getWrapped().onPanelClosed(paramInt, (Menu)localObject2);
    }
  }
  
  void checkCloseActionMenu(MenuBuilder paramMenuBuilder)
  {
    if (this.mClosingActionMenu) {
      return;
    }
    this.mClosingActionMenu = true;
    this.mDecorContentParent.dismissPopups();
    Window.Callback localCallback = getWindowCallback();
    if ((localCallback != null) && (!this.mIsDestroyed)) {
      localCallback.onPanelClosed(108, paramMenuBuilder);
    }
    this.mClosingActionMenu = false;
  }
  
  void closePanel(int paramInt)
  {
    closePanel(getPanelState(paramInt, true), true);
  }
  
  void closePanel(PanelFeatureState paramPanelFeatureState, boolean paramBoolean)
  {
    if ((paramBoolean) && (paramPanelFeatureState.featureId == 0))
    {
      localObject = this.mDecorContentParent;
      if ((localObject != null) && (((DecorContentParent)localObject).isOverflowMenuShowing()))
      {
        checkCloseActionMenu(paramPanelFeatureState.menu);
        return;
      }
    }
    Object localObject = (WindowManager)this.mContext.getSystemService("window");
    if ((localObject != null) && (paramPanelFeatureState.isOpen) && (paramPanelFeatureState.decorView != null))
    {
      ((WindowManager)localObject).removeView(paramPanelFeatureState.decorView);
      if (paramBoolean) {
        callOnPanelClosed(paramPanelFeatureState.featureId, paramPanelFeatureState, null);
      }
    }
    paramPanelFeatureState.isPrepared = false;
    paramPanelFeatureState.isHandled = false;
    paramPanelFeatureState.isOpen = false;
    paramPanelFeatureState.shownPanelView = null;
    paramPanelFeatureState.refreshDecorView = true;
    if (this.mPreparedPanel == paramPanelFeatureState) {
      this.mPreparedPanel = null;
    }
  }
  
  /* Error */
  public View createView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 1201	androidx/appcompat/app/AppCompatDelegateImpl:mAppCompatViewInflater	Landroidx/appcompat/app/AppCompatViewInflater;
    //   4: astore 5
    //   6: iconst_0
    //   7: istore 6
    //   9: aload 5
    //   11: ifnonnull +148 -> 159
    //   14: aload_0
    //   15: getfield 219	androidx/appcompat/app/AppCompatDelegateImpl:mContext	Landroid/content/Context;
    //   18: getstatic 341	androidx/appcompat/R$styleable:AppCompatTheme	[I
    //   21: invokevirtual 347	android/content/Context:obtainStyledAttributes	([I)Landroid/content/res/TypedArray;
    //   24: getstatic 1204	androidx/appcompat/R$styleable:AppCompatTheme_viewInflaterClass	I
    //   27: invokevirtual 1208	android/content/res/TypedArray:getString	(I)Ljava/lang/String;
    //   30: astore 7
    //   32: aload 7
    //   34: ifnull +114 -> 148
    //   37: ldc_w 1210
    //   40: invokevirtual 1215	java/lang/Class:getName	()Ljava/lang/String;
    //   43: aload 7
    //   45: invokevirtual 1221	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   48: ifeq +6 -> 54
    //   51: goto +97 -> 148
    //   54: aload_0
    //   55: aload 7
    //   57: invokestatic 1225	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   60: iconst_0
    //   61: anewarray 1212	java/lang/Class
    //   64: invokevirtual 1229	java/lang/Class:getDeclaredConstructor	([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
    //   67: iconst_0
    //   68: anewarray 239	java/lang/Object
    //   71: invokevirtual 1235	java/lang/reflect/Constructor:newInstance	([Ljava/lang/Object;)Ljava/lang/Object;
    //   74: checkcast 1210	androidx/appcompat/app/AppCompatViewInflater
    //   77: putfield 1201	androidx/appcompat/app/AppCompatDelegateImpl:mAppCompatViewInflater	Landroidx/appcompat/app/AppCompatViewInflater;
    //   80: goto +79 -> 159
    //   83: astore 5
    //   85: new 609	java/lang/StringBuilder
    //   88: dup
    //   89: invokespecial 610	java/lang/StringBuilder:<init>	()V
    //   92: astore 8
    //   94: aload 8
    //   96: ldc_w 1237
    //   99: invokevirtual 616	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   102: pop
    //   103: aload 8
    //   105: aload 7
    //   107: invokevirtual 616	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   110: pop
    //   111: aload 8
    //   113: ldc_w 1239
    //   116: invokevirtual 616	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   119: pop
    //   120: ldc_w 817
    //   123: aload 8
    //   125: invokevirtual 635	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   128: aload 5
    //   130: invokestatic 1241	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   133: pop
    //   134: aload_0
    //   135: new 1210	androidx/appcompat/app/AppCompatViewInflater
    //   138: dup
    //   139: invokespecial 1242	androidx/appcompat/app/AppCompatViewInflater:<init>	()V
    //   142: putfield 1201	androidx/appcompat/app/AppCompatDelegateImpl:mAppCompatViewInflater	Landroidx/appcompat/app/AppCompatViewInflater;
    //   145: goto +14 -> 159
    //   148: aload_0
    //   149: new 1210	androidx/appcompat/app/AppCompatViewInflater
    //   152: dup
    //   153: invokespecial 1242	androidx/appcompat/app/AppCompatViewInflater:<init>	()V
    //   156: putfield 1201	androidx/appcompat/app/AppCompatDelegateImpl:mAppCompatViewInflater	Landroidx/appcompat/app/AppCompatViewInflater;
    //   159: iload 6
    //   161: istore 9
    //   163: getstatic 168	androidx/appcompat/app/AppCompatDelegateImpl:IS_PRE_LOLLIPOP	Z
    //   166: ifeq +45 -> 211
    //   169: aload 4
    //   171: instanceof 1244
    //   174: ifeq +27 -> 201
    //   177: iload 6
    //   179: istore 9
    //   181: aload 4
    //   183: checkcast 1244	org/xmlpull/v1/XmlPullParser
    //   186: invokeinterface 1247 1 0
    //   191: iconst_1
    //   192: if_icmple +19 -> 211
    //   195: iconst_1
    //   196: istore 9
    //   198: goto +13 -> 211
    //   201: aload_0
    //   202: aload_1
    //   203: checkcast 1080	android/view/ViewParent
    //   206: invokespecial 1249	androidx/appcompat/app/AppCompatDelegateImpl:shouldInheritContext	(Landroid/view/ViewParent;)Z
    //   209: istore 9
    //   211: aload_0
    //   212: getfield 1201	androidx/appcompat/app/AppCompatDelegateImpl:mAppCompatViewInflater	Landroidx/appcompat/app/AppCompatViewInflater;
    //   215: aload_1
    //   216: aload_2
    //   217: aload_3
    //   218: aload 4
    //   220: iload 9
    //   222: getstatic 168	androidx/appcompat/app/AppCompatDelegateImpl:IS_PRE_LOLLIPOP	Z
    //   225: iconst_1
    //   226: invokestatic 1254	androidx/appcompat/widget/VectorEnabledTintResources:shouldBeUsed	()Z
    //   229: invokevirtual 1257	androidx/appcompat/app/AppCompatViewInflater:createView	(Landroid/view/View;Ljava/lang/String;Landroid/content/Context;Landroid/util/AttributeSet;ZZZZ)Landroid/view/View;
    //   232: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	233	0	this	AppCompatDelegateImpl
    //   0	233	1	paramView	View
    //   0	233	2	paramString	String
    //   0	233	3	paramContext	Context
    //   0	233	4	paramAttributeSet	AttributeSet
    //   4	6	5	localAppCompatViewInflater	AppCompatViewInflater
    //   83	46	5	localThrowable	Throwable
    //   7	171	6	bool1	boolean
    //   30	76	7	str	String
    //   92	32	8	localStringBuilder	StringBuilder
    //   161	60	9	bool2	boolean
    // Exception table:
    //   from	to	target	type
    //   54	80	83	finally
  }
  
  void dismissPopups()
  {
    Object localObject = this.mDecorContentParent;
    if (localObject != null) {
      ((DecorContentParent)localObject).dismissPopups();
    }
    if (this.mActionModePopup != null)
    {
      this.mWindow.getDecorView().removeCallbacks(this.mShowActionModePopup);
      if (!this.mActionModePopup.isShowing()) {}
    }
    try
    {
      this.mActionModePopup.dismiss();
      this.mActionModePopup = null;
      endOnGoingFadeAnimation();
      localObject = getPanelState(0, false);
      if ((localObject != null) && (((PanelFeatureState)localObject).menu != null)) {
        ((PanelFeatureState)localObject).menu.close();
      }
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      for (;;) {}
    }
  }
  
  boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    Object localObject = this.mHost;
    boolean bool = localObject instanceof KeyEventDispatcher.Component;
    int i = 1;
    if ((bool) || ((localObject instanceof AppCompatDialog)))
    {
      localObject = this.mWindow.getDecorView();
      if ((localObject != null) && (KeyEventDispatcher.dispatchBeforeHierarchy((View)localObject, paramKeyEvent))) {
        return true;
      }
    }
    if ((paramKeyEvent.getKeyCode() == 82) && (this.mAppCompatWindowCallback.getWrapped().dispatchKeyEvent(paramKeyEvent))) {
      return true;
    }
    int j = paramKeyEvent.getKeyCode();
    if (paramKeyEvent.getAction() != 0) {
      i = 0;
    }
    if (i != 0) {
      bool = onKeyDown(j, paramKeyEvent);
    } else {
      bool = onKeyUp(j, paramKeyEvent);
    }
    return bool;
  }
  
  void doInvalidatePanelMenu(int paramInt)
  {
    PanelFeatureState localPanelFeatureState = getPanelState(paramInt, true);
    Object localObject;
    if (localPanelFeatureState.menu != null)
    {
      localObject = new Bundle();
      localPanelFeatureState.menu.saveActionViewStates((Bundle)localObject);
      if (((Bundle)localObject).size() > 0) {
        localPanelFeatureState.frozenActionViewState = ((Bundle)localObject);
      }
      localPanelFeatureState.menu.stopDispatchingItemsChanged();
      localPanelFeatureState.menu.clear();
    }
    localPanelFeatureState.refreshMenuContent = true;
    localPanelFeatureState.refreshDecorView = true;
    if (((paramInt == 108) || (paramInt == 0)) && (this.mDecorContentParent != null))
    {
      localObject = getPanelState(0, false);
      if (localObject != null)
      {
        ((PanelFeatureState)localObject).isPrepared = false;
        preparePanel((PanelFeatureState)localObject, null);
      }
    }
  }
  
  void endOnGoingFadeAnimation()
  {
    ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat = this.mFadeAnim;
    if (localViewPropertyAnimatorCompat != null) {
      localViewPropertyAnimatorCompat.cancel();
    }
  }
  
  PanelFeatureState findMenuPanel(Menu paramMenu)
  {
    PanelFeatureState[] arrayOfPanelFeatureState = this.mPanels;
    int i = 0;
    int j;
    if (arrayOfPanelFeatureState != null) {
      j = arrayOfPanelFeatureState.length;
    } else {
      j = 0;
    }
    while (i < j)
    {
      PanelFeatureState localPanelFeatureState = arrayOfPanelFeatureState[i];
      if ((localPanelFeatureState != null) && (localPanelFeatureState.menu == paramMenu)) {
        return localPanelFeatureState;
      }
      i++;
    }
    return null;
  }
  
  public <T extends View> T findViewById(int paramInt)
  {
    ensureSubDecor();
    return this.mWindow.findViewById(paramInt);
  }
  
  final Context getActionBarThemedContext()
  {
    Object localObject1 = getSupportActionBar();
    if (localObject1 != null) {
      localObject1 = ((ActionBar)localObject1).getThemedContext();
    } else {
      localObject1 = null;
    }
    Object localObject2 = localObject1;
    if (localObject1 == null) {
      localObject2 = this.mContext;
    }
    return localObject2;
  }
  
  final AutoNightModeManager getAutoTimeNightModeManager()
  {
    if (this.mAutoTimeNightModeManager == null) {
      this.mAutoTimeNightModeManager = new AutoTimeNightModeManager(TwilightManager.getInstance(this.mContext));
    }
    return this.mAutoTimeNightModeManager;
  }
  
  public final ActionBarDrawerToggle.Delegate getDrawerToggleDelegate()
  {
    return new ActionBarDrawableToggleImpl();
  }
  
  public int getLocalNightMode()
  {
    return this.mLocalNightMode;
  }
  
  public MenuInflater getMenuInflater()
  {
    if (this.mMenuInflater == null)
    {
      initWindowDecorActionBar();
      Object localObject = this.mActionBar;
      if (localObject != null) {
        localObject = ((ActionBar)localObject).getThemedContext();
      } else {
        localObject = this.mContext;
      }
      this.mMenuInflater = new SupportMenuInflater((Context)localObject);
    }
    return this.mMenuInflater;
  }
  
  protected PanelFeatureState getPanelState(int paramInt, boolean paramBoolean)
  {
    Object localObject1 = this.mPanels;
    Object localObject2;
    if (localObject1 != null)
    {
      localObject2 = localObject1;
      if (localObject1.length > paramInt) {}
    }
    else
    {
      localObject2 = new PanelFeatureState[paramInt + 1];
      if (localObject1 != null) {
        System.arraycopy(localObject1, 0, localObject2, 0, localObject1.length);
      }
      this.mPanels = ((PanelFeatureState[])localObject2);
    }
    Object localObject3 = localObject2[paramInt];
    localObject1 = localObject3;
    if (localObject3 == null)
    {
      localObject1 = new PanelFeatureState(paramInt);
      localObject2[paramInt] = localObject1;
    }
    return localObject1;
  }
  
  ViewGroup getSubDecor()
  {
    return this.mSubDecor;
  }
  
  public ActionBar getSupportActionBar()
  {
    initWindowDecorActionBar();
    return this.mActionBar;
  }
  
  final CharSequence getTitle()
  {
    Object localObject = this.mHost;
    if ((localObject instanceof Activity)) {
      return ((Activity)localObject).getTitle();
    }
    return this.mTitle;
  }
  
  final Window.Callback getWindowCallback()
  {
    return this.mWindow.getCallback();
  }
  
  public boolean hasWindowFeature(int paramInt)
  {
    int i = sanitizeWindowFeatureId(paramInt);
    boolean bool1 = true;
    boolean bool2;
    if (i != 1)
    {
      if (i != 2)
      {
        if (i != 5)
        {
          if (i != 10)
          {
            if (i != 108)
            {
              if (i != 109) {
                bool2 = false;
              } else {
                bool2 = this.mOverlayActionBar;
              }
            }
            else {
              bool2 = this.mHasActionBar;
            }
          }
          else {
            bool2 = this.mOverlayActionMode;
          }
        }
        else {
          bool2 = this.mFeatureIndeterminateProgress;
        }
      }
      else {
        bool2 = this.mFeatureProgress;
      }
    }
    else {
      bool2 = this.mWindowNoTitle;
    }
    boolean bool3 = bool1;
    if (!bool2) {
      if (this.mWindow.hasFeature(paramInt)) {
        bool3 = bool1;
      } else {
        bool3 = false;
      }
    }
    return bool3;
  }
  
  public void installViewFactory()
  {
    LayoutInflater localLayoutInflater = LayoutInflater.from(this.mContext);
    if (localLayoutInflater.getFactory() == null) {
      LayoutInflaterCompat.setFactory2(localLayoutInflater, this);
    } else if (!(localLayoutInflater.getFactory2() instanceof AppCompatDelegateImpl)) {
      Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
    }
  }
  
  public void invalidateOptionsMenu()
  {
    ActionBar localActionBar = getSupportActionBar();
    if ((localActionBar != null) && (localActionBar.invalidateOptionsMenu())) {
      return;
    }
    invalidatePanelMenu(0);
  }
  
  public boolean isHandleNativeActionModesEnabled()
  {
    return this.mHandleNativeActionModes;
  }
  
  int mapNightMode(int paramInt)
  {
    if (paramInt != -100)
    {
      int i = paramInt;
      if (paramInt != -1) {
        if (paramInt != 0)
        {
          i = paramInt;
          if (paramInt != 1)
          {
            i = paramInt;
            if (paramInt != 2)
            {
              if (paramInt == 3) {
                return getAutoBatteryNightModeManager().getApplyableNightMode();
              }
              throw new IllegalStateException("Unknown value set for night mode. Please use one of the MODE_NIGHT values from AppCompatDelegate.");
            }
          }
        }
        else
        {
          if ((Build.VERSION.SDK_INT >= 23) && (((UiModeManager)this.mContext.getSystemService(UiModeManager.class)).getNightMode() == 0)) {
            return -1;
          }
          i = getAutoTimeNightModeManager().getApplyableNightMode();
        }
      }
      return i;
    }
    return -1;
  }
  
  boolean onBackPressed()
  {
    Object localObject = this.mActionMode;
    if (localObject != null)
    {
      ((androidx.appcompat.view.ActionMode)localObject).finish();
      return true;
    }
    localObject = getSupportActionBar();
    return (localObject != null) && (((ActionBar)localObject).collapseActionView());
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    if ((this.mHasActionBar) && (this.mSubDecorInstalled))
    {
      ActionBar localActionBar = getSupportActionBar();
      if (localActionBar != null) {
        localActionBar.onConfigurationChanged(paramConfiguration);
      }
    }
    AppCompatDrawableManager.get().onConfigurationChanged(this.mContext);
    applyDayNight(false);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    this.mBaseContextAttached = true;
    applyDayNight(false);
    ensureWindow();
    Object localObject = this.mHost;
    if ((localObject instanceof Activity)) {
      paramBundle = null;
    }
    try
    {
      localObject = NavUtils.getParentActivityName((Activity)localObject);
      paramBundle = (Bundle)localObject;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      for (;;) {}
    }
    if (paramBundle != null)
    {
      paramBundle = peekSupportActionBar();
      if (paramBundle == null) {
        this.mEnableDefaultActionBarUp = true;
      } else {
        paramBundle.setDefaultDisplayHomeAsUpEnabled(true);
      }
    }
    this.mCreated = true;
  }
  
  public final View onCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    return createView(paramView, paramString, paramContext, paramAttributeSet);
  }
  
  public View onCreateView(String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    return onCreateView(null, paramString, paramContext, paramAttributeSet);
  }
  
  public void onDestroy()
  {
    markStopped(this);
    if (this.mInvalidatePanelMenuPosted) {
      this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
    }
    this.mStarted = false;
    this.mIsDestroyed = true;
    ActionBar localActionBar = this.mActionBar;
    if (localActionBar != null) {
      localActionBar.onDestroy();
    }
    cleanupAutoManagers();
  }
  
  boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool = true;
    if (paramInt != 4)
    {
      if (paramInt == 82)
      {
        onKeyDownPanel(0, paramKeyEvent);
        return true;
      }
    }
    else
    {
      if ((paramKeyEvent.getFlags() & 0x80) == 0) {
        bool = false;
      }
      this.mLongPressBackDown = bool;
    }
    return false;
  }
  
  boolean onKeyShortcut(int paramInt, KeyEvent paramKeyEvent)
  {
    Object localObject = getSupportActionBar();
    if ((localObject != null) && (((ActionBar)localObject).onKeyShortcut(paramInt, paramKeyEvent))) {
      return true;
    }
    localObject = this.mPreparedPanel;
    if ((localObject != null) && (performPanelShortcut((PanelFeatureState)localObject, paramKeyEvent.getKeyCode(), paramKeyEvent, 1)))
    {
      paramKeyEvent = this.mPreparedPanel;
      if (paramKeyEvent != null) {
        paramKeyEvent.isHandled = true;
      }
      return true;
    }
    if (this.mPreparedPanel == null)
    {
      localObject = getPanelState(0, true);
      preparePanel((PanelFeatureState)localObject, paramKeyEvent);
      boolean bool = performPanelShortcut((PanelFeatureState)localObject, paramKeyEvent.getKeyCode(), paramKeyEvent, 1);
      ((PanelFeatureState)localObject).isPrepared = false;
      if (bool) {
        return true;
      }
    }
    return false;
  }
  
  boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt != 4)
    {
      if (paramInt == 82)
      {
        onKeyUpPanel(0, paramKeyEvent);
        return true;
      }
    }
    else
    {
      boolean bool = this.mLongPressBackDown;
      this.mLongPressBackDown = false;
      paramKeyEvent = getPanelState(0, false);
      if ((paramKeyEvent != null) && (paramKeyEvent.isOpen))
      {
        if (!bool) {
          closePanel(paramKeyEvent, true);
        }
        return true;
      }
      if (onBackPressed()) {
        return true;
      }
    }
    return false;
  }
  
  public boolean onMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem)
  {
    Window.Callback localCallback = getWindowCallback();
    if ((localCallback != null) && (!this.mIsDestroyed))
    {
      paramMenuBuilder = findMenuPanel(paramMenuBuilder.getRootMenu());
      if (paramMenuBuilder != null) {
        return localCallback.onMenuItemSelected(paramMenuBuilder.featureId, paramMenuItem);
      }
    }
    return false;
  }
  
  public void onMenuModeChange(MenuBuilder paramMenuBuilder)
  {
    reopenMenu(paramMenuBuilder, true);
  }
  
  void onMenuOpened(int paramInt)
  {
    if (paramInt == 108)
    {
      ActionBar localActionBar = getSupportActionBar();
      if (localActionBar != null) {
        localActionBar.dispatchMenuVisibilityChanged(true);
      }
    }
  }
  
  void onPanelClosed(int paramInt)
  {
    Object localObject;
    if (paramInt == 108)
    {
      localObject = getSupportActionBar();
      if (localObject != null) {
        ((ActionBar)localObject).dispatchMenuVisibilityChanged(false);
      }
    }
    else if (paramInt == 0)
    {
      localObject = getPanelState(paramInt, true);
      if (((PanelFeatureState)localObject).isOpen) {
        closePanel((PanelFeatureState)localObject, false);
      }
    }
  }
  
  public void onPostCreate(Bundle paramBundle)
  {
    ensureSubDecor();
  }
  
  public void onPostResume()
  {
    ActionBar localActionBar = getSupportActionBar();
    if (localActionBar != null) {
      localActionBar.setShowHideAnimationEnabled(true);
    }
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    if (this.mLocalNightMode != -100) {
      sLocalNightModes.put(this.mHost.getClass(), Integer.valueOf(this.mLocalNightMode));
    }
  }
  
  public void onStart()
  {
    this.mStarted = true;
    applyDayNight();
    markStarted(this);
  }
  
  public void onStop()
  {
    this.mStarted = false;
    markStopped(this);
    ActionBar localActionBar = getSupportActionBar();
    if (localActionBar != null) {
      localActionBar.setShowHideAnimationEnabled(false);
    }
    if ((this.mHost instanceof Dialog)) {
      cleanupAutoManagers();
    }
  }
  
  void onSubDecorInstalled(ViewGroup paramViewGroup) {}
  
  final ActionBar peekSupportActionBar()
  {
    return this.mActionBar;
  }
  
  public boolean requestWindowFeature(int paramInt)
  {
    paramInt = sanitizeWindowFeatureId(paramInt);
    if ((this.mWindowNoTitle) && (paramInt == 108)) {
      return false;
    }
    if ((this.mHasActionBar) && (paramInt == 1)) {
      this.mHasActionBar = false;
    }
    if (paramInt != 1)
    {
      if (paramInt != 2)
      {
        if (paramInt != 5)
        {
          if (paramInt != 10)
          {
            if (paramInt != 108)
            {
              if (paramInt != 109) {
                return this.mWindow.requestFeature(paramInt);
              }
              throwFeatureRequestIfSubDecorInstalled();
              this.mOverlayActionBar = true;
              return true;
            }
            throwFeatureRequestIfSubDecorInstalled();
            this.mHasActionBar = true;
            return true;
          }
          throwFeatureRequestIfSubDecorInstalled();
          this.mOverlayActionMode = true;
          return true;
        }
        throwFeatureRequestIfSubDecorInstalled();
        this.mFeatureIndeterminateProgress = true;
        return true;
      }
      throwFeatureRequestIfSubDecorInstalled();
      this.mFeatureProgress = true;
      return true;
    }
    throwFeatureRequestIfSubDecorInstalled();
    this.mWindowNoTitle = true;
    return true;
  }
  
  public void setContentView(int paramInt)
  {
    ensureSubDecor();
    ViewGroup localViewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
    localViewGroup.removeAllViews();
    LayoutInflater.from(this.mContext).inflate(paramInt, localViewGroup);
    this.mAppCompatWindowCallback.getWrapped().onContentChanged();
  }
  
  public void setContentView(View paramView)
  {
    ensureSubDecor();
    ViewGroup localViewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
    localViewGroup.removeAllViews();
    localViewGroup.addView(paramView);
    this.mAppCompatWindowCallback.getWrapped().onContentChanged();
  }
  
  public void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    ensureSubDecor();
    ViewGroup localViewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
    localViewGroup.removeAllViews();
    localViewGroup.addView(paramView, paramLayoutParams);
    this.mAppCompatWindowCallback.getWrapped().onContentChanged();
  }
  
  public void setHandleNativeActionModesEnabled(boolean paramBoolean)
  {
    this.mHandleNativeActionModes = paramBoolean;
  }
  
  public void setLocalNightMode(int paramInt)
  {
    if (this.mLocalNightMode != paramInt)
    {
      this.mLocalNightMode = paramInt;
      applyDayNight();
    }
  }
  
  public void setSupportActionBar(Toolbar paramToolbar)
  {
    if (!(this.mHost instanceof Activity)) {
      return;
    }
    ActionBar localActionBar = getSupportActionBar();
    if (!(localActionBar instanceof WindowDecorActionBar))
    {
      this.mMenuInflater = null;
      if (localActionBar != null) {
        localActionBar.onDestroy();
      }
      if (paramToolbar != null)
      {
        paramToolbar = new ToolbarActionBar(paramToolbar, getTitle(), this.mAppCompatWindowCallback);
        this.mActionBar = paramToolbar;
        this.mWindow.setCallback(paramToolbar.getWrappedWindowCallback());
      }
      else
      {
        this.mActionBar = null;
        this.mWindow.setCallback(this.mAppCompatWindowCallback);
      }
      invalidateOptionsMenu();
      return;
    }
    throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
  }
  
  public void setTheme(int paramInt)
  {
    this.mThemeResId = paramInt;
  }
  
  public final void setTitle(CharSequence paramCharSequence)
  {
    this.mTitle = paramCharSequence;
    Object localObject = this.mDecorContentParent;
    if (localObject != null)
    {
      ((DecorContentParent)localObject).setWindowTitle(paramCharSequence);
    }
    else if (peekSupportActionBar() != null)
    {
      peekSupportActionBar().setWindowTitle(paramCharSequence);
    }
    else
    {
      localObject = this.mTitleView;
      if (localObject != null) {
        ((TextView)localObject).setText(paramCharSequence);
      }
    }
  }
  
  final boolean shouldAnimateActionModeView()
  {
    if (this.mSubDecorInstalled)
    {
      ViewGroup localViewGroup = this.mSubDecor;
      if ((localViewGroup != null) && (ViewCompat.isLaidOut(localViewGroup))) {
        return true;
      }
    }
    boolean bool = false;
    return bool;
  }
  
  public androidx.appcompat.view.ActionMode startSupportActionMode(androidx.appcompat.view.ActionMode.Callback paramCallback)
  {
    if (paramCallback != null)
    {
      Object localObject = this.mActionMode;
      if (localObject != null) {
        ((androidx.appcompat.view.ActionMode)localObject).finish();
      }
      paramCallback = new ActionModeCallbackWrapperV9(paramCallback);
      localObject = getSupportActionBar();
      if (localObject != null)
      {
        androidx.appcompat.view.ActionMode localActionMode = ((ActionBar)localObject).startActionMode(paramCallback);
        this.mActionMode = localActionMode;
        if (localActionMode != null)
        {
          localObject = this.mAppCompatCallback;
          if (localObject != null) {
            ((AppCompatCallback)localObject).onSupportActionModeStarted(localActionMode);
          }
        }
      }
      if (this.mActionMode == null) {
        this.mActionMode = startSupportActionModeFromWindow(paramCallback);
      }
      return this.mActionMode;
    }
    throw new IllegalArgumentException("ActionMode callback can not be null.");
  }
  
  androidx.appcompat.view.ActionMode startSupportActionModeFromWindow(androidx.appcompat.view.ActionMode.Callback paramCallback)
  {
    endOnGoingFadeAnimation();
    Object localObject1 = this.mActionMode;
    if (localObject1 != null) {
      ((androidx.appcompat.view.ActionMode)localObject1).finish();
    }
    localObject1 = paramCallback;
    if (!(paramCallback instanceof ActionModeCallbackWrapperV9)) {
      localObject1 = new ActionModeCallbackWrapperV9(paramCallback);
    }
    paramCallback = this.mAppCompatCallback;
    if ((paramCallback != null) && (!this.mIsDestroyed)) {}
    try
    {
      paramCallback = paramCallback.onWindowStartingSupportActionMode((androidx.appcompat.view.ActionMode.Callback)localObject1);
    }
    catch (AbstractMethodError paramCallback)
    {
      boolean bool;
      Object localObject2;
      Object localObject3;
      int i;
      for (;;) {}
    }
    paramCallback = null;
    if (paramCallback != null)
    {
      this.mActionMode = paramCallback;
    }
    else
    {
      paramCallback = this.mActionModeView;
      bool = true;
      if (paramCallback == null) {
        if (this.mIsFloating)
        {
          localObject2 = new TypedValue();
          paramCallback = this.mContext.getTheme();
          paramCallback.resolveAttribute(R.attr.actionBarTheme, (TypedValue)localObject2, true);
          if (((TypedValue)localObject2).resourceId != 0)
          {
            localObject3 = this.mContext.getResources().newTheme();
            ((Resources.Theme)localObject3).setTo(paramCallback);
            ((Resources.Theme)localObject3).applyStyle(((TypedValue)localObject2).resourceId, true);
            paramCallback = new androidx.appcompat.view.ContextThemeWrapper(this.mContext, 0);
            paramCallback.getTheme().setTo((Resources.Theme)localObject3);
          }
          else
          {
            paramCallback = this.mContext;
          }
          this.mActionModeView = new ActionBarContextView(paramCallback);
          localObject3 = new PopupWindow(paramCallback, null, R.attr.actionModePopupWindowStyle);
          this.mActionModePopup = ((PopupWindow)localObject3);
          PopupWindowCompat.setWindowLayoutType((PopupWindow)localObject3, 2);
          this.mActionModePopup.setContentView(this.mActionModeView);
          this.mActionModePopup.setWidth(-1);
          paramCallback.getTheme().resolveAttribute(R.attr.actionBarSize, (TypedValue)localObject2, true);
          i = TypedValue.complexToDimensionPixelSize(((TypedValue)localObject2).data, paramCallback.getResources().getDisplayMetrics());
          this.mActionModeView.setContentHeight(i);
          this.mActionModePopup.setHeight(-2);
          this.mShowActionModePopup = new Runnable()
          {
            public void run()
            {
              AppCompatDelegateImpl.this.mActionModePopup.showAtLocation(AppCompatDelegateImpl.this.mActionModeView, 55, 0, 0);
              AppCompatDelegateImpl.this.endOnGoingFadeAnimation();
              if (AppCompatDelegateImpl.this.shouldAnimateActionModeView())
              {
                AppCompatDelegateImpl.this.mActionModeView.setAlpha(0.0F);
                AppCompatDelegateImpl localAppCompatDelegateImpl = AppCompatDelegateImpl.this;
                localAppCompatDelegateImpl.mFadeAnim = ViewCompat.animate(localAppCompatDelegateImpl.mActionModeView).alpha(1.0F);
                AppCompatDelegateImpl.this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter()
                {
                  public void onAnimationEnd(View paramAnonymous2View)
                  {
                    AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0F);
                    AppCompatDelegateImpl.this.mFadeAnim.setListener(null);
                    AppCompatDelegateImpl.this.mFadeAnim = null;
                  }
                  
                  public void onAnimationStart(View paramAnonymous2View)
                  {
                    AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
                  }
                });
              }
              else
              {
                AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0F);
                AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
              }
            }
          };
        }
        else
        {
          paramCallback = (ViewStubCompat)this.mSubDecor.findViewById(R.id.action_mode_bar_stub);
          if (paramCallback != null)
          {
            paramCallback.setLayoutInflater(LayoutInflater.from(getActionBarThemedContext()));
            this.mActionModeView = ((ActionBarContextView)paramCallback.inflate());
          }
        }
      }
      if (this.mActionModeView != null)
      {
        endOnGoingFadeAnimation();
        this.mActionModeView.killMode();
        localObject2 = this.mActionModeView.getContext();
        paramCallback = this.mActionModeView;
        if (this.mActionModePopup != null) {
          bool = false;
        }
        paramCallback = new StandaloneActionMode((Context)localObject2, paramCallback, (androidx.appcompat.view.ActionMode.Callback)localObject1, bool);
        if (((androidx.appcompat.view.ActionMode.Callback)localObject1).onCreateActionMode(paramCallback, paramCallback.getMenu()))
        {
          paramCallback.invalidate();
          this.mActionModeView.initForMode(paramCallback);
          this.mActionMode = paramCallback;
          if (shouldAnimateActionModeView())
          {
            this.mActionModeView.setAlpha(0.0F);
            paramCallback = ViewCompat.animate(this.mActionModeView).alpha(1.0F);
            this.mFadeAnim = paramCallback;
            paramCallback.setListener(new ViewPropertyAnimatorListenerAdapter()
            {
              public void onAnimationEnd(View paramAnonymousView)
              {
                AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0F);
                AppCompatDelegateImpl.this.mFadeAnim.setListener(null);
                AppCompatDelegateImpl.this.mFadeAnim = null;
              }
              
              public void onAnimationStart(View paramAnonymousView)
              {
                AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
                AppCompatDelegateImpl.this.mActionModeView.sendAccessibilityEvent(32);
                if ((AppCompatDelegateImpl.this.mActionModeView.getParent() instanceof View)) {
                  ViewCompat.requestApplyInsets((View)AppCompatDelegateImpl.this.mActionModeView.getParent());
                }
              }
            });
          }
          else
          {
            this.mActionModeView.setAlpha(1.0F);
            this.mActionModeView.setVisibility(0);
            this.mActionModeView.sendAccessibilityEvent(32);
            if ((this.mActionModeView.getParent() instanceof View)) {
              ViewCompat.requestApplyInsets((View)this.mActionModeView.getParent());
            }
          }
          if (this.mActionModePopup != null) {
            this.mWindow.getDecorView().post(this.mShowActionModePopup);
          }
        }
        else
        {
          this.mActionMode = null;
        }
      }
    }
    localObject1 = this.mActionMode;
    if (localObject1 != null)
    {
      paramCallback = this.mAppCompatCallback;
      if (paramCallback != null) {
        paramCallback.onSupportActionModeStarted((androidx.appcompat.view.ActionMode)localObject1);
      }
    }
    return this.mActionMode;
  }
  
  int updateStatusGuard(int paramInt)
  {
    Object localObject1 = this.mActionModeView;
    int i = 0;
    int j;
    int n;
    if ((localObject1 != null) && ((((ActionBarContextView)localObject1).getLayoutParams() instanceof ViewGroup.MarginLayoutParams)))
    {
      localObject1 = (ViewGroup.MarginLayoutParams)this.mActionModeView.getLayoutParams();
      boolean bool = this.mActionModeView.isShown();
      j = 1;
      int k = 1;
      int m;
      if (bool)
      {
        if (this.mTempRect1 == null)
        {
          this.mTempRect1 = new Rect();
          this.mTempRect2 = new Rect();
        }
        Object localObject2 = this.mTempRect1;
        Rect localRect = this.mTempRect2;
        ((Rect)localObject2).set(0, paramInt, 0, 0);
        ViewUtils.computeFitSystemWindows(this.mSubDecor, (Rect)localObject2, localRect);
        if (localRect.top == 0) {
          m = paramInt;
        } else {
          m = 0;
        }
        if (((ViewGroup.MarginLayoutParams)localObject1).topMargin != m)
        {
          ((ViewGroup.MarginLayoutParams)localObject1).topMargin = paramInt;
          localObject2 = this.mStatusGuard;
          if (localObject2 == null)
          {
            localObject2 = new View(this.mContext);
            this.mStatusGuard = ((View)localObject2);
            ((View)localObject2).setBackgroundColor(this.mContext.getResources().getColor(R.color.abc_input_method_navigation_guard));
            this.mSubDecor.addView(this.mStatusGuard, -1, new ViewGroup.LayoutParams(-1, paramInt));
          }
          else
          {
            localObject2 = ((View)localObject2).getLayoutParams();
            if (((ViewGroup.LayoutParams)localObject2).height != paramInt)
            {
              ((ViewGroup.LayoutParams)localObject2).height = paramInt;
              this.mStatusGuard.setLayoutParams((ViewGroup.LayoutParams)localObject2);
            }
          }
          m = 1;
        }
        else
        {
          m = 0;
        }
        if (this.mStatusGuard == null) {
          k = 0;
        }
        j = paramInt;
        if (!this.mOverlayActionMode)
        {
          j = paramInt;
          if (k != 0) {
            j = 0;
          }
        }
        paramInt = m;
        m = k;
        k = paramInt;
        paramInt = j;
      }
      else if (((ViewGroup.MarginLayoutParams)localObject1).topMargin != 0)
      {
        ((ViewGroup.MarginLayoutParams)localObject1).topMargin = 0;
        m = 0;
        k = j;
      }
      else
      {
        m = 0;
        k = m;
      }
      n = m;
      j = paramInt;
      if (k != 0)
      {
        this.mActionModeView.setLayoutParams((ViewGroup.LayoutParams)localObject1);
        n = m;
        j = paramInt;
      }
    }
    else
    {
      n = 0;
      j = paramInt;
    }
    localObject1 = this.mStatusGuard;
    if (localObject1 != null)
    {
      if (n != 0) {
        paramInt = i;
      } else {
        paramInt = 8;
      }
      ((View)localObject1).setVisibility(paramInt);
    }
    return j;
  }
  
  private class ActionBarDrawableToggleImpl
    implements ActionBarDrawerToggle.Delegate
  {
    ActionBarDrawableToggleImpl() {}
    
    public Context getActionBarThemedContext()
    {
      return AppCompatDelegateImpl.this.getActionBarThemedContext();
    }
    
    public Drawable getThemeUpIndicator()
    {
      TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(getActionBarThemedContext(), null, new int[] { R.attr.homeAsUpIndicator });
      Drawable localDrawable = localTintTypedArray.getDrawable(0);
      localTintTypedArray.recycle();
      return localDrawable;
    }
    
    public boolean isNavigationVisible()
    {
      ActionBar localActionBar = AppCompatDelegateImpl.this.getSupportActionBar();
      boolean bool;
      if ((localActionBar != null) && ((localActionBar.getDisplayOptions() & 0x4) != 0)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void setActionBarDescription(int paramInt)
    {
      ActionBar localActionBar = AppCompatDelegateImpl.this.getSupportActionBar();
      if (localActionBar != null) {
        localActionBar.setHomeActionContentDescription(paramInt);
      }
    }
    
    public void setActionBarUpIndicator(Drawable paramDrawable, int paramInt)
    {
      ActionBar localActionBar = AppCompatDelegateImpl.this.getSupportActionBar();
      if (localActionBar != null)
      {
        localActionBar.setHomeAsUpIndicator(paramDrawable);
        localActionBar.setHomeActionContentDescription(paramInt);
      }
    }
  }
  
  private final class ActionMenuPresenterCallback
    implements MenuPresenter.Callback
  {
    ActionMenuPresenterCallback() {}
    
    public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
    {
      AppCompatDelegateImpl.this.checkCloseActionMenu(paramMenuBuilder);
    }
    
    public boolean onOpenSubMenu(MenuBuilder paramMenuBuilder)
    {
      Window.Callback localCallback = AppCompatDelegateImpl.this.getWindowCallback();
      if (localCallback != null) {
        localCallback.onMenuOpened(108, paramMenuBuilder);
      }
      return true;
    }
  }
  
  class ActionModeCallbackWrapperV9
    implements androidx.appcompat.view.ActionMode.Callback
  {
    private androidx.appcompat.view.ActionMode.Callback mWrapped;
    
    public ActionModeCallbackWrapperV9(androidx.appcompat.view.ActionMode.Callback paramCallback)
    {
      this.mWrapped = paramCallback;
    }
    
    public boolean onActionItemClicked(androidx.appcompat.view.ActionMode paramActionMode, MenuItem paramMenuItem)
    {
      return this.mWrapped.onActionItemClicked(paramActionMode, paramMenuItem);
    }
    
    public boolean onCreateActionMode(androidx.appcompat.view.ActionMode paramActionMode, Menu paramMenu)
    {
      return this.mWrapped.onCreateActionMode(paramActionMode, paramMenu);
    }
    
    public void onDestroyActionMode(androidx.appcompat.view.ActionMode paramActionMode)
    {
      this.mWrapped.onDestroyActionMode(paramActionMode);
      if (AppCompatDelegateImpl.this.mActionModePopup != null) {
        AppCompatDelegateImpl.this.mWindow.getDecorView().removeCallbacks(AppCompatDelegateImpl.this.mShowActionModePopup);
      }
      if (AppCompatDelegateImpl.this.mActionModeView != null)
      {
        AppCompatDelegateImpl.this.endOnGoingFadeAnimation();
        paramActionMode = AppCompatDelegateImpl.this;
        paramActionMode.mFadeAnim = ViewCompat.animate(paramActionMode.mActionModeView).alpha(0.0F);
        AppCompatDelegateImpl.this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter()
        {
          public void onAnimationEnd(View paramAnonymousView)
          {
            AppCompatDelegateImpl.this.mActionModeView.setVisibility(8);
            if (AppCompatDelegateImpl.this.mActionModePopup != null) {
              AppCompatDelegateImpl.this.mActionModePopup.dismiss();
            } else if ((AppCompatDelegateImpl.this.mActionModeView.getParent() instanceof View)) {
              ViewCompat.requestApplyInsets((View)AppCompatDelegateImpl.this.mActionModeView.getParent());
            }
            AppCompatDelegateImpl.this.mActionModeView.removeAllViews();
            AppCompatDelegateImpl.this.mFadeAnim.setListener(null);
            AppCompatDelegateImpl.this.mFadeAnim = null;
          }
        });
      }
      if (AppCompatDelegateImpl.this.mAppCompatCallback != null) {
        AppCompatDelegateImpl.this.mAppCompatCallback.onSupportActionModeFinished(AppCompatDelegateImpl.this.mActionMode);
      }
      AppCompatDelegateImpl.this.mActionMode = null;
    }
    
    public boolean onPrepareActionMode(androidx.appcompat.view.ActionMode paramActionMode, Menu paramMenu)
    {
      return this.mWrapped.onPrepareActionMode(paramActionMode, paramMenu);
    }
  }
  
  class AppCompatWindowCallback
    extends WindowCallbackWrapper
  {
    AppCompatWindowCallback(Window.Callback paramCallback)
    {
      super();
    }
    
    public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
    {
      boolean bool;
      if ((!AppCompatDelegateImpl.this.dispatchKeyEvent(paramKeyEvent)) && (!super.dispatchKeyEvent(paramKeyEvent))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public boolean dispatchKeyShortcutEvent(KeyEvent paramKeyEvent)
    {
      boolean bool;
      if ((!super.dispatchKeyShortcutEvent(paramKeyEvent)) && (!AppCompatDelegateImpl.this.onKeyShortcut(paramKeyEvent.getKeyCode(), paramKeyEvent))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public void onContentChanged() {}
    
    public boolean onCreatePanelMenu(int paramInt, Menu paramMenu)
    {
      if ((paramInt == 0) && (!(paramMenu instanceof MenuBuilder))) {
        return false;
      }
      return super.onCreatePanelMenu(paramInt, paramMenu);
    }
    
    public boolean onMenuOpened(int paramInt, Menu paramMenu)
    {
      super.onMenuOpened(paramInt, paramMenu);
      AppCompatDelegateImpl.this.onMenuOpened(paramInt);
      return true;
    }
    
    public void onPanelClosed(int paramInt, Menu paramMenu)
    {
      super.onPanelClosed(paramInt, paramMenu);
      AppCompatDelegateImpl.this.onPanelClosed(paramInt);
    }
    
    public boolean onPreparePanel(int paramInt, View paramView, Menu paramMenu)
    {
      MenuBuilder localMenuBuilder;
      if ((paramMenu instanceof MenuBuilder)) {
        localMenuBuilder = (MenuBuilder)paramMenu;
      } else {
        localMenuBuilder = null;
      }
      if ((paramInt == 0) && (localMenuBuilder == null)) {
        return false;
      }
      if (localMenuBuilder != null) {
        localMenuBuilder.setOverrideVisibleItems(true);
      }
      boolean bool = super.onPreparePanel(paramInt, paramView, paramMenu);
      if (localMenuBuilder != null) {
        localMenuBuilder.setOverrideVisibleItems(false);
      }
      return bool;
    }
    
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> paramList, Menu paramMenu, int paramInt)
    {
      AppCompatDelegateImpl.PanelFeatureState localPanelFeatureState = AppCompatDelegateImpl.this.getPanelState(0, true);
      if ((localPanelFeatureState != null) && (localPanelFeatureState.menu != null)) {
        super.onProvideKeyboardShortcuts(paramList, localPanelFeatureState.menu, paramInt);
      } else {
        super.onProvideKeyboardShortcuts(paramList, paramMenu, paramInt);
      }
    }
    
    public android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback paramCallback)
    {
      if (Build.VERSION.SDK_INT >= 23) {
        return null;
      }
      if (AppCompatDelegateImpl.this.isHandleNativeActionModesEnabled()) {
        return startAsSupportActionMode(paramCallback);
      }
      return super.onWindowStartingActionMode(paramCallback);
    }
    
    public android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback paramCallback, int paramInt)
    {
      if ((AppCompatDelegateImpl.this.isHandleNativeActionModesEnabled()) && (paramInt == 0)) {
        return startAsSupportActionMode(paramCallback);
      }
      return super.onWindowStartingActionMode(paramCallback, paramInt);
    }
    
    final android.view.ActionMode startAsSupportActionMode(android.view.ActionMode.Callback paramCallback)
    {
      SupportActionModeWrapper.CallbackWrapper localCallbackWrapper = new SupportActionModeWrapper.CallbackWrapper(AppCompatDelegateImpl.this.mContext, paramCallback);
      paramCallback = AppCompatDelegateImpl.this.startSupportActionMode(localCallbackWrapper);
      if (paramCallback != null) {
        return localCallbackWrapper.getActionModeWrapper(paramCallback);
      }
      return null;
    }
  }
  
  private class AutoBatteryNightModeManager
    extends AppCompatDelegateImpl.AutoNightModeManager
  {
    private final PowerManager mPowerManager;
    
    AutoBatteryNightModeManager(Context paramContext)
    {
      super();
      this.mPowerManager = ((PowerManager)paramContext.getSystemService("power"));
    }
    
    IntentFilter createIntentFilterForBroadcastReceiver()
    {
      if (Build.VERSION.SDK_INT >= 21)
      {
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
        return localIntentFilter;
      }
      return null;
    }
    
    public int getApplyableNightMode()
    {
      int i = Build.VERSION.SDK_INT;
      int j = 1;
      int k = j;
      if (i >= 21)
      {
        k = j;
        if (this.mPowerManager.isPowerSaveMode()) {
          k = 2;
        }
      }
      return k;
    }
    
    public void onChange()
    {
      AppCompatDelegateImpl.this.applyDayNight();
    }
  }
  
  abstract class AutoNightModeManager
  {
    private BroadcastReceiver mReceiver;
    
    AutoNightModeManager() {}
    
    void cleanup()
    {
      if (this.mReceiver != null) {}
      try
      {
        AppCompatDelegateImpl.this.mContext.unregisterReceiver(this.mReceiver);
        this.mReceiver = null;
        return;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        for (;;) {}
      }
    }
    
    abstract IntentFilter createIntentFilterForBroadcastReceiver();
    
    abstract int getApplyableNightMode();
    
    boolean isListening()
    {
      boolean bool;
      if (this.mReceiver != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    abstract void onChange();
    
    void setup()
    {
      cleanup();
      IntentFilter localIntentFilter = createIntentFilterForBroadcastReceiver();
      if ((localIntentFilter != null) && (localIntentFilter.countActions() != 0))
      {
        if (this.mReceiver == null) {
          this.mReceiver = new BroadcastReceiver()
          {
            public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
            {
              AppCompatDelegateImpl.AutoNightModeManager.this.onChange();
            }
          };
        }
        AppCompatDelegateImpl.this.mContext.registerReceiver(this.mReceiver, localIntentFilter);
      }
    }
  }
  
  private class AutoTimeNightModeManager
    extends AppCompatDelegateImpl.AutoNightModeManager
  {
    private final TwilightManager mTwilightManager;
    
    AutoTimeNightModeManager(TwilightManager paramTwilightManager)
    {
      super();
      this.mTwilightManager = paramTwilightManager;
    }
    
    IntentFilter createIntentFilterForBroadcastReceiver()
    {
      IntentFilter localIntentFilter = new IntentFilter();
      localIntentFilter.addAction("android.intent.action.TIME_SET");
      localIntentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
      localIntentFilter.addAction("android.intent.action.TIME_TICK");
      return localIntentFilter;
    }
    
    public int getApplyableNightMode()
    {
      int i;
      if (this.mTwilightManager.isNight()) {
        i = 2;
      } else {
        i = 1;
      }
      return i;
    }
    
    public void onChange()
    {
      AppCompatDelegateImpl.this.applyDayNight();
    }
  }
  
  private class ListMenuDecorView
    extends ContentFrameLayout
  {
    public ListMenuDecorView(Context paramContext)
    {
      super();
    }
    
    private boolean isOutOfBounds(int paramInt1, int paramInt2)
    {
      boolean bool;
      if ((paramInt1 >= -5) && (paramInt2 >= -5) && (paramInt1 <= getWidth() + 5) && (paramInt2 <= getHeight() + 5)) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
    {
      boolean bool;
      if ((!AppCompatDelegateImpl.this.dispatchKeyEvent(paramKeyEvent)) && (!super.dispatchKeyEvent(paramKeyEvent))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
    {
      if ((paramMotionEvent.getAction() == 0) && (isOutOfBounds((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY())))
      {
        AppCompatDelegateImpl.this.closePanel(0);
        return true;
      }
      return super.onInterceptTouchEvent(paramMotionEvent);
    }
    
    public void setBackgroundResource(int paramInt)
    {
      setBackgroundDrawable(AppCompatResources.getDrawable(getContext(), paramInt));
    }
  }
  
  protected static final class PanelFeatureState
  {
    int background;
    View createdPanelView;
    ViewGroup decorView;
    int featureId;
    Bundle frozenActionViewState;
    Bundle frozenMenuState;
    int gravity;
    boolean isHandled;
    boolean isOpen;
    boolean isPrepared;
    ListMenuPresenter listMenuPresenter;
    Context listPresenterContext;
    MenuBuilder menu;
    public boolean qwertyMode;
    boolean refreshDecorView;
    boolean refreshMenuContent;
    View shownPanelView;
    boolean wasLastOpen;
    int windowAnimations;
    int x;
    int y;
    
    PanelFeatureState(int paramInt)
    {
      this.featureId = paramInt;
      this.refreshDecorView = false;
    }
    
    void applyFrozenState()
    {
      MenuBuilder localMenuBuilder = this.menu;
      if (localMenuBuilder != null)
      {
        Bundle localBundle = this.frozenMenuState;
        if (localBundle != null)
        {
          localMenuBuilder.restorePresenterStates(localBundle);
          this.frozenMenuState = null;
        }
      }
    }
    
    public void clearMenuPresenters()
    {
      MenuBuilder localMenuBuilder = this.menu;
      if (localMenuBuilder != null) {
        localMenuBuilder.removeMenuPresenter(this.listMenuPresenter);
      }
      this.listMenuPresenter = null;
    }
    
    MenuView getListMenuView(MenuPresenter.Callback paramCallback)
    {
      if (this.menu == null) {
        return null;
      }
      if (this.listMenuPresenter == null)
      {
        ListMenuPresenter localListMenuPresenter = new ListMenuPresenter(this.listPresenterContext, R.layout.abc_list_menu_item_layout);
        this.listMenuPresenter = localListMenuPresenter;
        localListMenuPresenter.setCallback(paramCallback);
        this.menu.addMenuPresenter(this.listMenuPresenter);
      }
      return this.listMenuPresenter.getMenuView(this.decorView);
    }
    
    public boolean hasPanelItems()
    {
      View localView = this.shownPanelView;
      boolean bool = false;
      if (localView == null) {
        return false;
      }
      if (this.createdPanelView != null) {
        return true;
      }
      if (this.listMenuPresenter.getAdapter().getCount() > 0) {
        bool = true;
      }
      return bool;
    }
    
    void onRestoreInstanceState(Parcelable paramParcelable)
    {
      paramParcelable = (SavedState)paramParcelable;
      this.featureId = paramParcelable.featureId;
      this.wasLastOpen = paramParcelable.isOpen;
      this.frozenMenuState = paramParcelable.menuState;
      this.shownPanelView = null;
      this.decorView = null;
    }
    
    Parcelable onSaveInstanceState()
    {
      SavedState localSavedState = new SavedState();
      localSavedState.featureId = this.featureId;
      localSavedState.isOpen = this.isOpen;
      if (this.menu != null)
      {
        localSavedState.menuState = new Bundle();
        this.menu.savePresenterStates(localSavedState.menuState);
      }
      return localSavedState;
    }
    
    void setMenu(MenuBuilder paramMenuBuilder)
    {
      Object localObject = this.menu;
      if (paramMenuBuilder == localObject) {
        return;
      }
      if (localObject != null) {
        ((MenuBuilder)localObject).removeMenuPresenter(this.listMenuPresenter);
      }
      this.menu = paramMenuBuilder;
      if (paramMenuBuilder != null)
      {
        localObject = this.listMenuPresenter;
        if (localObject != null) {
          paramMenuBuilder.addMenuPresenter((MenuPresenter)localObject);
        }
      }
    }
    
    void setStyle(Context paramContext)
    {
      TypedValue localTypedValue = new TypedValue();
      Resources.Theme localTheme = paramContext.getResources().newTheme();
      localTheme.setTo(paramContext.getTheme());
      localTheme.resolveAttribute(R.attr.actionBarPopupTheme, localTypedValue, true);
      if (localTypedValue.resourceId != 0) {
        localTheme.applyStyle(localTypedValue.resourceId, true);
      }
      localTheme.resolveAttribute(R.attr.panelMenuListTheme, localTypedValue, true);
      if (localTypedValue.resourceId != 0) {
        localTheme.applyStyle(localTypedValue.resourceId, true);
      } else {
        localTheme.applyStyle(R.style.Theme_AppCompat_CompactMenu, true);
      }
      paramContext = new androidx.appcompat.view.ContextThemeWrapper(paramContext, 0);
      paramContext.getTheme().setTo(localTheme);
      this.listPresenterContext = paramContext;
      paramContext = paramContext.obtainStyledAttributes(R.styleable.AppCompatTheme);
      this.background = paramContext.getResourceId(R.styleable.AppCompatTheme_panelBackground, 0);
      this.windowAnimations = paramContext.getResourceId(R.styleable.AppCompatTheme_android_windowAnimationStyle, 0);
      paramContext.recycle();
    }
    
    private static class SavedState
      implements Parcelable
    {
      public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
      {
        public AppCompatDelegateImpl.PanelFeatureState.SavedState createFromParcel(Parcel paramAnonymousParcel)
        {
          return AppCompatDelegateImpl.PanelFeatureState.SavedState.readFromParcel(paramAnonymousParcel, null);
        }
        
        public AppCompatDelegateImpl.PanelFeatureState.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
        {
          return AppCompatDelegateImpl.PanelFeatureState.SavedState.readFromParcel(paramAnonymousParcel, paramAnonymousClassLoader);
        }
        
        public AppCompatDelegateImpl.PanelFeatureState.SavedState[] newArray(int paramAnonymousInt)
        {
          return new AppCompatDelegateImpl.PanelFeatureState.SavedState[paramAnonymousInt];
        }
      };
      int featureId;
      boolean isOpen;
      Bundle menuState;
      
      SavedState() {}
      
      static SavedState readFromParcel(Parcel paramParcel, ClassLoader paramClassLoader)
      {
        SavedState localSavedState = new SavedState();
        localSavedState.featureId = paramParcel.readInt();
        int i = paramParcel.readInt();
        boolean bool = true;
        if (i != 1) {
          bool = false;
        }
        localSavedState.isOpen = bool;
        if (bool) {
          localSavedState.menuState = paramParcel.readBundle(paramClassLoader);
        }
        return localSavedState;
      }
      
      public int describeContents()
      {
        return 0;
      }
      
      public void writeToParcel(Parcel paramParcel, int paramInt)
      {
        paramParcel.writeInt(this.featureId);
        paramParcel.writeInt(this.isOpen);
        if (this.isOpen) {
          paramParcel.writeBundle(this.menuState);
        }
      }
    }
  }
  
  private final class PanelMenuPresenterCallback
    implements MenuPresenter.Callback
  {
    PanelMenuPresenterCallback() {}
    
    public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
    {
      MenuBuilder localMenuBuilder = paramMenuBuilder.getRootMenu();
      int i;
      if (localMenuBuilder != paramMenuBuilder) {
        i = 1;
      } else {
        i = 0;
      }
      AppCompatDelegateImpl localAppCompatDelegateImpl = AppCompatDelegateImpl.this;
      if (i != 0) {
        paramMenuBuilder = localMenuBuilder;
      }
      paramMenuBuilder = localAppCompatDelegateImpl.findMenuPanel(paramMenuBuilder);
      if (paramMenuBuilder != null) {
        if (i != 0)
        {
          AppCompatDelegateImpl.this.callOnPanelClosed(paramMenuBuilder.featureId, paramMenuBuilder, localMenuBuilder);
          AppCompatDelegateImpl.this.closePanel(paramMenuBuilder, true);
        }
        else
        {
          AppCompatDelegateImpl.this.closePanel(paramMenuBuilder, paramBoolean);
        }
      }
    }
    
    public boolean onOpenSubMenu(MenuBuilder paramMenuBuilder)
    {
      if ((paramMenuBuilder == null) && (AppCompatDelegateImpl.this.mHasActionBar))
      {
        Window.Callback localCallback = AppCompatDelegateImpl.this.getWindowCallback();
        if ((localCallback != null) && (!AppCompatDelegateImpl.this.mIsDestroyed)) {
          localCallback.onMenuOpened(108, paramMenuBuilder);
        }
      }
      return true;
    }
  }
}

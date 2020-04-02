package androidx.activity;

import android.app.Application;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ReportFragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider.Factory;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;

public class ComponentActivity
  extends androidx.core.app.ComponentActivity
  implements LifecycleOwner, ViewModelStoreOwner, HasDefaultViewModelProviderFactory, SavedStateRegistryOwner, OnBackPressedDispatcherOwner
{
  private int mContentLayoutId;
  private ViewModelProvider.Factory mDefaultFactory;
  private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
  private final OnBackPressedDispatcher mOnBackPressedDispatcher = new OnBackPressedDispatcher(new Runnable()
  {
    public void run()
    {
      ComponentActivity.this.onBackPressed();
    }
  });
  private final SavedStateRegistryController mSavedStateRegistryController = SavedStateRegistryController.create(this);
  private ViewModelStore mViewModelStore;
  
  public ComponentActivity()
  {
    if (getLifecycle() != null)
    {
      if (Build.VERSION.SDK_INT >= 19) {
        getLifecycle().addObserver(new LifecycleEventObserver()
        {
          public void onStateChanged(LifecycleOwner paramAnonymousLifecycleOwner, Lifecycle.Event paramAnonymousEvent)
          {
            if (paramAnonymousEvent == Lifecycle.Event.ON_STOP)
            {
              paramAnonymousLifecycleOwner = ComponentActivity.this.getWindow();
              if (paramAnonymousLifecycleOwner != null) {
                paramAnonymousLifecycleOwner = paramAnonymousLifecycleOwner.peekDecorView();
              } else {
                paramAnonymousLifecycleOwner = null;
              }
              if (paramAnonymousLifecycleOwner != null) {
                paramAnonymousLifecycleOwner.cancelPendingInputEvents();
              }
            }
          }
        });
      }
      getLifecycle().addObserver(new LifecycleEventObserver()
      {
        public void onStateChanged(LifecycleOwner paramAnonymousLifecycleOwner, Lifecycle.Event paramAnonymousEvent)
        {
          if ((paramAnonymousEvent == Lifecycle.Event.ON_DESTROY) && (!ComponentActivity.this.isChangingConfigurations())) {
            ComponentActivity.this.getViewModelStore().clear();
          }
        }
      });
      if ((19 <= Build.VERSION.SDK_INT) && (Build.VERSION.SDK_INT <= 23)) {
        getLifecycle().addObserver(new ImmLeaksCleaner(this));
      }
      return;
    }
    throw new IllegalStateException("getLifecycle() returned null in ComponentActivity's constructor. Please make sure you are lazily constructing your Lifecycle in the first call to getLifecycle() rather than relying on field initialization.");
  }
  
  public ComponentActivity(int paramInt)
  {
    this();
    this.mContentLayoutId = paramInt;
  }
  
  public ViewModelProvider.Factory getDefaultViewModelProviderFactory()
  {
    if (getApplication() != null)
    {
      if (this.mDefaultFactory == null)
      {
        Application localApplication = getApplication();
        Bundle localBundle;
        if (getIntent() != null) {
          localBundle = getIntent().getExtras();
        } else {
          localBundle = null;
        }
        this.mDefaultFactory = new SavedStateViewModelFactory(localApplication, this, localBundle);
      }
      return this.mDefaultFactory;
    }
    throw new IllegalStateException("Your activity is not yet attached to the Application instance. You can't request ViewModel before onCreate call.");
  }
  
  @Deprecated
  public Object getLastCustomNonConfigurationInstance()
  {
    Object localObject = (NonConfigurationInstances)getLastNonConfigurationInstance();
    if (localObject != null) {
      localObject = ((NonConfigurationInstances)localObject).custom;
    } else {
      localObject = null;
    }
    return localObject;
  }
  
  public Lifecycle getLifecycle()
  {
    return this.mLifecycleRegistry;
  }
  
  public final OnBackPressedDispatcher getOnBackPressedDispatcher()
  {
    return this.mOnBackPressedDispatcher;
  }
  
  public final SavedStateRegistry getSavedStateRegistry()
  {
    return this.mSavedStateRegistryController.getSavedStateRegistry();
  }
  
  public ViewModelStore getViewModelStore()
  {
    if (getApplication() != null)
    {
      if (this.mViewModelStore == null)
      {
        NonConfigurationInstances localNonConfigurationInstances = (NonConfigurationInstances)getLastNonConfigurationInstance();
        if (localNonConfigurationInstances != null) {
          this.mViewModelStore = localNonConfigurationInstances.viewModelStore;
        }
        if (this.mViewModelStore == null) {
          this.mViewModelStore = new ViewModelStore();
        }
      }
      return this.mViewModelStore;
    }
    throw new IllegalStateException("Your activity is not yet attached to the Application instance. You can't request ViewModel before onCreate call.");
  }
  
  public void onBackPressed()
  {
    this.mOnBackPressedDispatcher.onBackPressed();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mSavedStateRegistryController.performRestore(paramBundle);
    ReportFragment.injectIfNeededIn(this);
    int i = this.mContentLayoutId;
    if (i != 0) {
      setContentView(i);
    }
  }
  
  @Deprecated
  public Object onRetainCustomNonConfigurationInstance()
  {
    return null;
  }
  
  public final Object onRetainNonConfigurationInstance()
  {
    Object localObject1 = onRetainCustomNonConfigurationInstance();
    Object localObject2 = this.mViewModelStore;
    Object localObject3 = localObject2;
    if (localObject2 == null)
    {
      NonConfigurationInstances localNonConfigurationInstances = (NonConfigurationInstances)getLastNonConfigurationInstance();
      localObject3 = localObject2;
      if (localNonConfigurationInstances != null) {
        localObject3 = localNonConfigurationInstances.viewModelStore;
      }
    }
    if ((localObject3 == null) && (localObject1 == null)) {
      return null;
    }
    localObject2 = new NonConfigurationInstances();
    ((NonConfigurationInstances)localObject2).custom = localObject1;
    ((NonConfigurationInstances)localObject2).viewModelStore = ((ViewModelStore)localObject3);
    return localObject2;
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    Lifecycle localLifecycle = getLifecycle();
    if ((localLifecycle instanceof LifecycleRegistry)) {
      ((LifecycleRegistry)localLifecycle).setCurrentState(Lifecycle.State.CREATED);
    }
    super.onSaveInstanceState(paramBundle);
    this.mSavedStateRegistryController.performSave(paramBundle);
  }
  
  static final class NonConfigurationInstances
  {
    Object custom;
    ViewModelStore viewModelStore;
    
    NonConfigurationInstances() {}
  }
}

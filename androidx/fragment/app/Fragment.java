package androidx.fragment.app;

import android.animation.Animator;
import android.app.Activity;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.core.app.SharedElementCallback;
import androidx.core.view.LayoutInflaterCompat;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider.Factory;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.loader.app.LoaderManager;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Fragment
  implements ComponentCallbacks, View.OnCreateContextMenuListener, LifecycleOwner, ViewModelStoreOwner, HasDefaultViewModelProviderFactory, SavedStateRegistryOwner
{
  static final int ACTIVITY_CREATED = 2;
  static final int ATTACHED = 0;
  static final int CREATED = 1;
  static final int INITIALIZING = -1;
  static final int RESUMED = 4;
  static final int STARTED = 3;
  static final Object USE_DEFAULT_TRANSITION = new Object();
  boolean mAdded;
  AnimationInfo mAnimationInfo;
  Bundle mArguments;
  int mBackStackNesting;
  private boolean mCalled;
  FragmentManager mChildFragmentManager = new FragmentManagerImpl();
  ViewGroup mContainer;
  int mContainerId;
  private int mContentLayoutId;
  private ViewModelProvider.Factory mDefaultFactory;
  boolean mDeferStart;
  boolean mDetached;
  int mFragmentId;
  FragmentManager mFragmentManager;
  boolean mFromLayout;
  boolean mHasMenu;
  boolean mHidden;
  boolean mHiddenChanged;
  FragmentHostCallback<?> mHost;
  boolean mInLayout;
  boolean mIsCreated;
  boolean mIsNewlyAdded;
  private Boolean mIsPrimaryNavigationFragment = null;
  LayoutInflater mLayoutInflater;
  LifecycleRegistry mLifecycleRegistry;
  Lifecycle.State mMaxState = Lifecycle.State.RESUMED;
  boolean mMenuVisible = true;
  Fragment mParentFragment;
  boolean mPerformedCreateView;
  float mPostponedAlpha;
  Runnable mPostponedDurationRunnable = new Runnable()
  {
    public void run()
    {
      Fragment.this.startPostponedEnterTransition();
    }
  };
  boolean mRemoving;
  boolean mRestored;
  boolean mRetainInstance;
  boolean mRetainInstanceChangedWhileDetached;
  Bundle mSavedFragmentState;
  SavedStateRegistryController mSavedStateRegistryController;
  Boolean mSavedUserVisibleHint;
  SparseArray<Parcelable> mSavedViewState;
  int mState = -1;
  String mTag;
  Fragment mTarget;
  int mTargetRequestCode;
  String mTargetWho = null;
  boolean mUserVisibleHint = true;
  View mView;
  FragmentViewLifecycleOwner mViewLifecycleOwner;
  MutableLiveData<LifecycleOwner> mViewLifecycleOwnerLiveData = new MutableLiveData();
  String mWho = UUID.randomUUID().toString();
  
  public Fragment()
  {
    initLifecycle();
  }
  
  public Fragment(int paramInt)
  {
    this();
    this.mContentLayoutId = paramInt;
  }
  
  private AnimationInfo ensureAnimationInfo()
  {
    if (this.mAnimationInfo == null) {
      this.mAnimationInfo = new AnimationInfo();
    }
    return this.mAnimationInfo;
  }
  
  private void initLifecycle()
  {
    this.mLifecycleRegistry = new LifecycleRegistry(this);
    this.mSavedStateRegistryController = SavedStateRegistryController.create(this);
    if (Build.VERSION.SDK_INT >= 19) {
      this.mLifecycleRegistry.addObserver(new LifecycleEventObserver()
      {
        public void onStateChanged(LifecycleOwner paramAnonymousLifecycleOwner, Lifecycle.Event paramAnonymousEvent)
        {
          if ((paramAnonymousEvent == Lifecycle.Event.ON_STOP) && (Fragment.this.mView != null)) {
            Fragment.this.mView.cancelPendingInputEvents();
          }
        }
      });
    }
  }
  
  @Deprecated
  public static Fragment instantiate(Context paramContext, String paramString)
  {
    return instantiate(paramContext, paramString, null);
  }
  
  @Deprecated
  public static Fragment instantiate(Context paramContext, String paramString, Bundle paramBundle)
  {
    try
    {
      paramContext = (Fragment)FragmentFactory.loadFragmentClass(paramContext.getClassLoader(), paramString).getConstructor(new Class[0]).newInstance(new Object[0]);
      if (paramBundle != null)
      {
        paramBundle.setClassLoader(paramContext.getClass().getClassLoader());
        paramContext.setArguments(paramBundle);
      }
      return paramContext;
    }
    catch (InvocationTargetException paramContext)
    {
      paramBundle = new StringBuilder();
      paramBundle.append("Unable to instantiate fragment ");
      paramBundle.append(paramString);
      paramBundle.append(": calling Fragment constructor caused an exception");
      throw new InstantiationException(paramBundle.toString(), paramContext);
    }
    catch (NoSuchMethodException paramContext)
    {
      paramBundle = new StringBuilder();
      paramBundle.append("Unable to instantiate fragment ");
      paramBundle.append(paramString);
      paramBundle.append(": could not find Fragment constructor");
      throw new InstantiationException(paramBundle.toString(), paramContext);
    }
    catch (IllegalAccessException paramBundle)
    {
      paramContext = new StringBuilder();
      paramContext.append("Unable to instantiate fragment ");
      paramContext.append(paramString);
      paramContext.append(": make sure class name exists, is public, and has an empty constructor that is public");
      throw new InstantiationException(paramContext.toString(), paramBundle);
    }
    catch (InstantiationException paramContext)
    {
      paramBundle = new StringBuilder();
      paramBundle.append("Unable to instantiate fragment ");
      paramBundle.append(paramString);
      paramBundle.append(": make sure class name exists, is public, and has an empty constructor that is public");
      throw new InstantiationException(paramBundle.toString(), paramContext);
    }
  }
  
  void callStartTransitionListener()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    OnStartEnterTransitionListener localOnStartEnterTransitionListener = null;
    if (localAnimationInfo != null)
    {
      localAnimationInfo.mEnterTransitionPostponed = false;
      localOnStartEnterTransitionListener = this.mAnimationInfo.mStartEnterTransitionListener;
      this.mAnimationInfo.mStartEnterTransitionListener = null;
    }
    if (localOnStartEnterTransitionListener != null) {
      localOnStartEnterTransitionListener.onStartEnterTransition();
    }
  }
  
  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mFragmentId=#");
    paramPrintWriter.print(Integer.toHexString(this.mFragmentId));
    paramPrintWriter.print(" mContainerId=#");
    paramPrintWriter.print(Integer.toHexString(this.mContainerId));
    paramPrintWriter.print(" mTag=");
    paramPrintWriter.println(this.mTag);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mState=");
    paramPrintWriter.print(this.mState);
    paramPrintWriter.print(" mWho=");
    paramPrintWriter.print(this.mWho);
    paramPrintWriter.print(" mBackStackNesting=");
    paramPrintWriter.println(this.mBackStackNesting);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mAdded=");
    paramPrintWriter.print(this.mAdded);
    paramPrintWriter.print(" mRemoving=");
    paramPrintWriter.print(this.mRemoving);
    paramPrintWriter.print(" mFromLayout=");
    paramPrintWriter.print(this.mFromLayout);
    paramPrintWriter.print(" mInLayout=");
    paramPrintWriter.println(this.mInLayout);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mHidden=");
    paramPrintWriter.print(this.mHidden);
    paramPrintWriter.print(" mDetached=");
    paramPrintWriter.print(this.mDetached);
    paramPrintWriter.print(" mMenuVisible=");
    paramPrintWriter.print(this.mMenuVisible);
    paramPrintWriter.print(" mHasMenu=");
    paramPrintWriter.println(this.mHasMenu);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mRetainInstance=");
    paramPrintWriter.print(this.mRetainInstance);
    paramPrintWriter.print(" mUserVisibleHint=");
    paramPrintWriter.println(this.mUserVisibleHint);
    if (this.mFragmentManager != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mFragmentManager=");
      paramPrintWriter.println(this.mFragmentManager);
    }
    if (this.mHost != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mHost=");
      paramPrintWriter.println(this.mHost);
    }
    if (this.mParentFragment != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mParentFragment=");
      paramPrintWriter.println(this.mParentFragment);
    }
    if (this.mArguments != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mArguments=");
      paramPrintWriter.println(this.mArguments);
    }
    if (this.mSavedFragmentState != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mSavedFragmentState=");
      paramPrintWriter.println(this.mSavedFragmentState);
    }
    if (this.mSavedViewState != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mSavedViewState=");
      paramPrintWriter.println(this.mSavedViewState);
    }
    Object localObject = getTargetFragment();
    if (localObject != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mTarget=");
      paramPrintWriter.print(localObject);
      paramPrintWriter.print(" mTargetRequestCode=");
      paramPrintWriter.println(this.mTargetRequestCode);
    }
    if (getNextAnim() != 0)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mNextAnim=");
      paramPrintWriter.println(getNextAnim());
    }
    if (this.mContainer != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mContainer=");
      paramPrintWriter.println(this.mContainer);
    }
    if (this.mView != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mView=");
      paramPrintWriter.println(this.mView);
    }
    if (getAnimatingAway() != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mAnimatingAway=");
      paramPrintWriter.println(getAnimatingAway());
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mStateAfterAnimating=");
      paramPrintWriter.println(getStateAfterAnimating());
    }
    if (getContext() != null) {
      LoaderManager.getInstance(this).dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
    }
    paramPrintWriter.print(paramString);
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Child ");
    ((StringBuilder)localObject).append(this.mChildFragmentManager);
    ((StringBuilder)localObject).append(":");
    paramPrintWriter.println(((StringBuilder)localObject).toString());
    localObject = this.mChildFragmentManager;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append("  ");
    ((FragmentManager)localObject).dump(localStringBuilder.toString(), paramFileDescriptor, paramPrintWriter, paramArrayOfString);
  }
  
  public final boolean equals(Object paramObject)
  {
    return super.equals(paramObject);
  }
  
  Fragment findFragmentByWho(String paramString)
  {
    if (paramString.equals(this.mWho)) {
      return this;
    }
    return this.mChildFragmentManager.findFragmentByWho(paramString);
  }
  
  public final FragmentActivity getActivity()
  {
    Object localObject = this.mHost;
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = (FragmentActivity)((FragmentHostCallback)localObject).getActivity();
    }
    return localObject;
  }
  
  public boolean getAllowEnterTransitionOverlap()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    boolean bool;
    if ((localAnimationInfo != null) && (localAnimationInfo.mAllowEnterTransitionOverlap != null)) {
      bool = this.mAnimationInfo.mAllowEnterTransitionOverlap.booleanValue();
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean getAllowReturnTransitionOverlap()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    boolean bool;
    if ((localAnimationInfo != null) && (localAnimationInfo.mAllowReturnTransitionOverlap != null)) {
      bool = this.mAnimationInfo.mAllowReturnTransitionOverlap.booleanValue();
    } else {
      bool = true;
    }
    return bool;
  }
  
  View getAnimatingAway()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return null;
    }
    return localAnimationInfo.mAnimatingAway;
  }
  
  Animator getAnimator()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return null;
    }
    return localAnimationInfo.mAnimator;
  }
  
  public final Bundle getArguments()
  {
    return this.mArguments;
  }
  
  public final FragmentManager getChildFragmentManager()
  {
    if (this.mHost != null) {
      return this.mChildFragmentManager;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Fragment ");
    localStringBuilder.append(this);
    localStringBuilder.append(" has not been attached yet.");
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  public Context getContext()
  {
    Object localObject = this.mHost;
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = ((FragmentHostCallback)localObject).getContext();
    }
    return localObject;
  }
  
  public ViewModelProvider.Factory getDefaultViewModelProviderFactory()
  {
    if (this.mFragmentManager != null)
    {
      if (this.mDefaultFactory == null) {
        this.mDefaultFactory = new SavedStateViewModelFactory(requireActivity().getApplication(), this, getArguments());
      }
      return this.mDefaultFactory;
    }
    throw new IllegalStateException("Can't access ViewModels from detached fragment");
  }
  
  public Object getEnterTransition()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return null;
    }
    return localAnimationInfo.mEnterTransition;
  }
  
  SharedElementCallback getEnterTransitionCallback()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return null;
    }
    return localAnimationInfo.mEnterTransitionCallback;
  }
  
  public Object getExitTransition()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return null;
    }
    return localAnimationInfo.mExitTransition;
  }
  
  SharedElementCallback getExitTransitionCallback()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return null;
    }
    return localAnimationInfo.mExitTransitionCallback;
  }
  
  @Deprecated
  public final FragmentManager getFragmentManager()
  {
    return this.mFragmentManager;
  }
  
  public final Object getHost()
  {
    Object localObject = this.mHost;
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = ((FragmentHostCallback)localObject).onGetHost();
    }
    return localObject;
  }
  
  public final int getId()
  {
    return this.mFragmentId;
  }
  
  public final LayoutInflater getLayoutInflater()
  {
    LayoutInflater localLayoutInflater1 = this.mLayoutInflater;
    LayoutInflater localLayoutInflater2 = localLayoutInflater1;
    if (localLayoutInflater1 == null) {
      localLayoutInflater2 = performGetLayoutInflater(null);
    }
    return localLayoutInflater2;
  }
  
  @Deprecated
  public LayoutInflater getLayoutInflater(Bundle paramBundle)
  {
    paramBundle = this.mHost;
    if (paramBundle != null)
    {
      paramBundle = paramBundle.onGetLayoutInflater();
      LayoutInflaterCompat.setFactory2(paramBundle, this.mChildFragmentManager.getLayoutInflaterFactory());
      return paramBundle;
    }
    throw new IllegalStateException("onGetLayoutInflater() cannot be executed until the Fragment is attached to the FragmentManager.");
  }
  
  public Lifecycle getLifecycle()
  {
    return this.mLifecycleRegistry;
  }
  
  @Deprecated
  public LoaderManager getLoaderManager()
  {
    return LoaderManager.getInstance(this);
  }
  
  int getNextAnim()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return 0;
    }
    return localAnimationInfo.mNextAnim;
  }
  
  int getNextTransition()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return 0;
    }
    return localAnimationInfo.mNextTransition;
  }
  
  public final Fragment getParentFragment()
  {
    return this.mParentFragment;
  }
  
  public final FragmentManager getParentFragmentManager()
  {
    Object localObject = this.mFragmentManager;
    if (localObject != null) {
      return localObject;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Fragment ");
    ((StringBuilder)localObject).append(this);
    ((StringBuilder)localObject).append(" not associated with a fragment manager.");
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public Object getReenterTransition()
  {
    Object localObject = this.mAnimationInfo;
    if (localObject == null) {
      return null;
    }
    if (((AnimationInfo)localObject).mReenterTransition == USE_DEFAULT_TRANSITION) {
      localObject = getExitTransition();
    } else {
      localObject = this.mAnimationInfo.mReenterTransition;
    }
    return localObject;
  }
  
  public final Resources getResources()
  {
    return requireContext().getResources();
  }
  
  public final boolean getRetainInstance()
  {
    return this.mRetainInstance;
  }
  
  public Object getReturnTransition()
  {
    Object localObject = this.mAnimationInfo;
    if (localObject == null) {
      return null;
    }
    if (((AnimationInfo)localObject).mReturnTransition == USE_DEFAULT_TRANSITION) {
      localObject = getEnterTransition();
    } else {
      localObject = this.mAnimationInfo.mReturnTransition;
    }
    return localObject;
  }
  
  public final SavedStateRegistry getSavedStateRegistry()
  {
    return this.mSavedStateRegistryController.getSavedStateRegistry();
  }
  
  public Object getSharedElementEnterTransition()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return null;
    }
    return localAnimationInfo.mSharedElementEnterTransition;
  }
  
  public Object getSharedElementReturnTransition()
  {
    Object localObject = this.mAnimationInfo;
    if (localObject == null) {
      return null;
    }
    if (((AnimationInfo)localObject).mSharedElementReturnTransition == USE_DEFAULT_TRANSITION) {
      localObject = getSharedElementEnterTransition();
    } else {
      localObject = this.mAnimationInfo.mSharedElementReturnTransition;
    }
    return localObject;
  }
  
  int getStateAfterAnimating()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return 0;
    }
    return localAnimationInfo.mStateAfterAnimating;
  }
  
  public final String getString(int paramInt)
  {
    return getResources().getString(paramInt);
  }
  
  public final String getString(int paramInt, Object... paramVarArgs)
  {
    return getResources().getString(paramInt, paramVarArgs);
  }
  
  public final String getTag()
  {
    return this.mTag;
  }
  
  public final Fragment getTargetFragment()
  {
    Object localObject = this.mTarget;
    if (localObject != null) {
      return localObject;
    }
    localObject = this.mFragmentManager;
    if (localObject != null)
    {
      String str = this.mTargetWho;
      if (str != null) {
        return ((FragmentManager)localObject).findActiveFragment(str);
      }
    }
    return null;
  }
  
  public final int getTargetRequestCode()
  {
    return this.mTargetRequestCode;
  }
  
  public final CharSequence getText(int paramInt)
  {
    return getResources().getText(paramInt);
  }
  
  @Deprecated
  public boolean getUserVisibleHint()
  {
    return this.mUserVisibleHint;
  }
  
  public View getView()
  {
    return this.mView;
  }
  
  public LifecycleOwner getViewLifecycleOwner()
  {
    FragmentViewLifecycleOwner localFragmentViewLifecycleOwner = this.mViewLifecycleOwner;
    if (localFragmentViewLifecycleOwner != null) {
      return localFragmentViewLifecycleOwner;
    }
    throw new IllegalStateException("Can't access the Fragment View's LifecycleOwner when getView() is null i.e., before onCreateView() or after onDestroyView()");
  }
  
  public LiveData<LifecycleOwner> getViewLifecycleOwnerLiveData()
  {
    return this.mViewLifecycleOwnerLiveData;
  }
  
  public ViewModelStore getViewModelStore()
  {
    FragmentManager localFragmentManager = this.mFragmentManager;
    if (localFragmentManager != null) {
      return localFragmentManager.getViewModelStore(this);
    }
    throw new IllegalStateException("Can't access ViewModels from detached fragment");
  }
  
  public final boolean hasOptionsMenu()
  {
    return this.mHasMenu;
  }
  
  public final int hashCode()
  {
    return super.hashCode();
  }
  
  void initState()
  {
    initLifecycle();
    this.mWho = UUID.randomUUID().toString();
    this.mAdded = false;
    this.mRemoving = false;
    this.mFromLayout = false;
    this.mInLayout = false;
    this.mRestored = false;
    this.mBackStackNesting = 0;
    this.mFragmentManager = null;
    this.mChildFragmentManager = new FragmentManagerImpl();
    this.mHost = null;
    this.mFragmentId = 0;
    this.mContainerId = 0;
    this.mTag = null;
    this.mHidden = false;
    this.mDetached = false;
  }
  
  public final boolean isAdded()
  {
    boolean bool;
    if ((this.mHost != null) && (this.mAdded)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final boolean isDetached()
  {
    return this.mDetached;
  }
  
  public final boolean isHidden()
  {
    return this.mHidden;
  }
  
  boolean isHideReplaced()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return false;
    }
    return localAnimationInfo.mIsHideReplaced;
  }
  
  final boolean isInBackStack()
  {
    boolean bool;
    if (this.mBackStackNesting > 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final boolean isInLayout()
  {
    return this.mInLayout;
  }
  
  public final boolean isMenuVisible()
  {
    return this.mMenuVisible;
  }
  
  boolean isPostponed()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return false;
    }
    return localAnimationInfo.mEnterTransitionPostponed;
  }
  
  public final boolean isRemoving()
  {
    return this.mRemoving;
  }
  
  final boolean isRemovingParent()
  {
    Fragment localFragment = getParentFragment();
    boolean bool;
    if ((localFragment != null) && ((localFragment.isRemoving()) || (localFragment.isRemovingParent()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final boolean isResumed()
  {
    boolean bool;
    if (this.mState >= 4) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final boolean isStateSaved()
  {
    FragmentManager localFragmentManager = this.mFragmentManager;
    if (localFragmentManager == null) {
      return false;
    }
    return localFragmentManager.isStateSaved();
  }
  
  public final boolean isVisible()
  {
    if ((isAdded()) && (!isHidden()))
    {
      View localView = this.mView;
      if ((localView != null) && (localView.getWindowToken() != null) && (this.mView.getVisibility() == 0)) {
        return true;
      }
    }
    boolean bool = false;
    return bool;
  }
  
  void noteStateNotSaved()
  {
    this.mChildFragmentManager.noteStateNotSaved();
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    this.mCalled = true;
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {}
  
  @Deprecated
  public void onAttach(Activity paramActivity)
  {
    this.mCalled = true;
  }
  
  public void onAttach(Context paramContext)
  {
    this.mCalled = true;
    paramContext = this.mHost;
    if (paramContext == null) {
      paramContext = null;
    } else {
      paramContext = paramContext.getActivity();
    }
    if (paramContext != null)
    {
      this.mCalled = false;
      onAttach(paramContext);
    }
  }
  
  public void onAttachFragment(Fragment paramFragment) {}
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    this.mCalled = true;
  }
  
  public boolean onContextItemSelected(MenuItem paramMenuItem)
  {
    return false;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    this.mCalled = true;
    restoreChildFragmentState(paramBundle);
    if (!this.mChildFragmentManager.isStateAtLeast(1)) {
      this.mChildFragmentManager.dispatchCreate();
    }
  }
  
  public Animation onCreateAnimation(int paramInt1, boolean paramBoolean, int paramInt2)
  {
    return null;
  }
  
  public Animator onCreateAnimator(int paramInt1, boolean paramBoolean, int paramInt2)
  {
    return null;
  }
  
  public void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenu.ContextMenuInfo paramContextMenuInfo)
  {
    requireActivity().onCreateContextMenu(paramContextMenu, paramView, paramContextMenuInfo);
  }
  
  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater) {}
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    int i = this.mContentLayoutId;
    if (i != 0) {
      return paramLayoutInflater.inflate(i, paramViewGroup, false);
    }
    return null;
  }
  
  public void onDestroy()
  {
    this.mCalled = true;
  }
  
  public void onDestroyOptionsMenu() {}
  
  public void onDestroyView()
  {
    this.mCalled = true;
  }
  
  public void onDetach()
  {
    this.mCalled = true;
  }
  
  public LayoutInflater onGetLayoutInflater(Bundle paramBundle)
  {
    return getLayoutInflater(paramBundle);
  }
  
  public void onHiddenChanged(boolean paramBoolean) {}
  
  @Deprecated
  public void onInflate(Activity paramActivity, AttributeSet paramAttributeSet, Bundle paramBundle)
  {
    this.mCalled = true;
  }
  
  public void onInflate(Context paramContext, AttributeSet paramAttributeSet, Bundle paramBundle)
  {
    this.mCalled = true;
    paramContext = this.mHost;
    if (paramContext == null) {
      paramContext = null;
    } else {
      paramContext = paramContext.getActivity();
    }
    if (paramContext != null)
    {
      this.mCalled = false;
      onInflate(paramContext, paramAttributeSet, paramBundle);
    }
  }
  
  public void onLowMemory()
  {
    this.mCalled = true;
  }
  
  public void onMultiWindowModeChanged(boolean paramBoolean) {}
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    return false;
  }
  
  public void onOptionsMenuClosed(Menu paramMenu) {}
  
  public void onPause()
  {
    this.mCalled = true;
  }
  
  public void onPictureInPictureModeChanged(boolean paramBoolean) {}
  
  public void onPrepareOptionsMenu(Menu paramMenu) {}
  
  public void onPrimaryNavigationFragmentChanged(boolean paramBoolean) {}
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfInt) {}
  
  public void onResume()
  {
    this.mCalled = true;
  }
  
  public void onSaveInstanceState(Bundle paramBundle) {}
  
  public void onStart()
  {
    this.mCalled = true;
  }
  
  public void onStop()
  {
    this.mCalled = true;
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle) {}
  
  public void onViewStateRestored(Bundle paramBundle)
  {
    this.mCalled = true;
  }
  
  void performActivityCreated(Bundle paramBundle)
  {
    this.mChildFragmentManager.noteStateNotSaved();
    this.mState = 2;
    this.mCalled = false;
    onActivityCreated(paramBundle);
    if (this.mCalled)
    {
      this.mChildFragmentManager.dispatchActivityCreated();
      return;
    }
    paramBundle = new StringBuilder();
    paramBundle.append("Fragment ");
    paramBundle.append(this);
    paramBundle.append(" did not call through to super.onActivityCreated()");
    throw new SuperNotCalledException(paramBundle.toString());
  }
  
  void performAttach()
  {
    this.mChildFragmentManager.attachController(this.mHost, new FragmentContainer()
    {
      public View onFindViewById(int paramAnonymousInt)
      {
        if (Fragment.this.mView != null) {
          return Fragment.this.mView.findViewById(paramAnonymousInt);
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Fragment ");
        localStringBuilder.append(this);
        localStringBuilder.append(" does not have a view");
        throw new IllegalStateException(localStringBuilder.toString());
      }
      
      public boolean onHasView()
      {
        boolean bool;
        if (Fragment.this.mView != null) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
    }, this);
    this.mState = 0;
    this.mCalled = false;
    onAttach(this.mHost.getContext());
    if (this.mCalled) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Fragment ");
    localStringBuilder.append(this);
    localStringBuilder.append(" did not call through to super.onAttach()");
    throw new SuperNotCalledException(localStringBuilder.toString());
  }
  
  void performConfigurationChanged(Configuration paramConfiguration)
  {
    onConfigurationChanged(paramConfiguration);
    this.mChildFragmentManager.dispatchConfigurationChanged(paramConfiguration);
  }
  
  boolean performContextItemSelected(MenuItem paramMenuItem)
  {
    if (!this.mHidden)
    {
      if (onContextItemSelected(paramMenuItem)) {
        return true;
      }
      if (this.mChildFragmentManager.dispatchContextItemSelected(paramMenuItem)) {
        return true;
      }
    }
    return false;
  }
  
  void performCreate(Bundle paramBundle)
  {
    this.mChildFragmentManager.noteStateNotSaved();
    this.mState = 1;
    this.mCalled = false;
    this.mSavedStateRegistryController.performRestore(paramBundle);
    onCreate(paramBundle);
    this.mIsCreated = true;
    if (this.mCalled)
    {
      this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
      return;
    }
    paramBundle = new StringBuilder();
    paramBundle.append("Fragment ");
    paramBundle.append(this);
    paramBundle.append(" did not call through to super.onCreate()");
    throw new SuperNotCalledException(paramBundle.toString());
  }
  
  boolean performCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    boolean bool1 = this.mHidden;
    boolean bool2 = false;
    int i = 0;
    if (!bool1)
    {
      int j = i;
      if (this.mHasMenu)
      {
        j = i;
        if (this.mMenuVisible)
        {
          j = 1;
          onCreateOptionsMenu(paramMenu, paramMenuInflater);
        }
      }
      bool2 = j | this.mChildFragmentManager.dispatchCreateOptionsMenu(paramMenu, paramMenuInflater);
    }
    return bool2;
  }
  
  void performCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mChildFragmentManager.noteStateNotSaved();
    this.mPerformedCreateView = true;
    this.mViewLifecycleOwner = new FragmentViewLifecycleOwner();
    paramLayoutInflater = onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    this.mView = paramLayoutInflater;
    if (paramLayoutInflater != null)
    {
      this.mViewLifecycleOwner.initialize();
      this.mViewLifecycleOwnerLiveData.setValue(this.mViewLifecycleOwner);
    }
    else
    {
      if (this.mViewLifecycleOwner.isInitialized()) {
        break label77;
      }
      this.mViewLifecycleOwner = null;
    }
    return;
    label77:
    throw new IllegalStateException("Called getViewLifecycleOwner() but onCreateView() returned null");
  }
  
  void performDestroy()
  {
    this.mChildFragmentManager.dispatchDestroy();
    this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    this.mState = 0;
    this.mCalled = false;
    this.mIsCreated = false;
    onDestroy();
    if (this.mCalled) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Fragment ");
    localStringBuilder.append(this);
    localStringBuilder.append(" did not call through to super.onDestroy()");
    throw new SuperNotCalledException(localStringBuilder.toString());
  }
  
  void performDestroyView()
  {
    this.mChildFragmentManager.dispatchDestroyView();
    if (this.mView != null) {
      this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }
    this.mState = 1;
    this.mCalled = false;
    onDestroyView();
    if (this.mCalled)
    {
      LoaderManager.getInstance(this).markForRedelivery();
      this.mPerformedCreateView = false;
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Fragment ");
    localStringBuilder.append(this);
    localStringBuilder.append(" did not call through to super.onDestroyView()");
    throw new SuperNotCalledException(localStringBuilder.toString());
  }
  
  void performDetach()
  {
    this.mState = -1;
    this.mCalled = false;
    onDetach();
    this.mLayoutInflater = null;
    if (this.mCalled)
    {
      if (!this.mChildFragmentManager.isDestroyed())
      {
        this.mChildFragmentManager.dispatchDestroy();
        this.mChildFragmentManager = new FragmentManagerImpl();
      }
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Fragment ");
    localStringBuilder.append(this);
    localStringBuilder.append(" did not call through to super.onDetach()");
    throw new SuperNotCalledException(localStringBuilder.toString());
  }
  
  LayoutInflater performGetLayoutInflater(Bundle paramBundle)
  {
    paramBundle = onGetLayoutInflater(paramBundle);
    this.mLayoutInflater = paramBundle;
    return paramBundle;
  }
  
  void performLowMemory()
  {
    onLowMemory();
    this.mChildFragmentManager.dispatchLowMemory();
  }
  
  void performMultiWindowModeChanged(boolean paramBoolean)
  {
    onMultiWindowModeChanged(paramBoolean);
    this.mChildFragmentManager.dispatchMultiWindowModeChanged(paramBoolean);
  }
  
  boolean performOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (!this.mHidden)
    {
      if ((this.mHasMenu) && (this.mMenuVisible) && (onOptionsItemSelected(paramMenuItem))) {
        return true;
      }
      if (this.mChildFragmentManager.dispatchOptionsItemSelected(paramMenuItem)) {
        return true;
      }
    }
    return false;
  }
  
  void performOptionsMenuClosed(Menu paramMenu)
  {
    if (!this.mHidden)
    {
      if ((this.mHasMenu) && (this.mMenuVisible)) {
        onOptionsMenuClosed(paramMenu);
      }
      this.mChildFragmentManager.dispatchOptionsMenuClosed(paramMenu);
    }
  }
  
  void performPause()
  {
    this.mChildFragmentManager.dispatchPause();
    if (this.mView != null) {
      this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }
    this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    this.mState = 3;
    this.mCalled = false;
    onPause();
    if (this.mCalled) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Fragment ");
    localStringBuilder.append(this);
    localStringBuilder.append(" did not call through to super.onPause()");
    throw new SuperNotCalledException(localStringBuilder.toString());
  }
  
  void performPictureInPictureModeChanged(boolean paramBoolean)
  {
    onPictureInPictureModeChanged(paramBoolean);
    this.mChildFragmentManager.dispatchPictureInPictureModeChanged(paramBoolean);
  }
  
  boolean performPrepareOptionsMenu(Menu paramMenu)
  {
    boolean bool1 = this.mHidden;
    boolean bool2 = false;
    int i = 0;
    if (!bool1)
    {
      int j = i;
      if (this.mHasMenu)
      {
        j = i;
        if (this.mMenuVisible)
        {
          j = 1;
          onPrepareOptionsMenu(paramMenu);
        }
      }
      bool2 = j | this.mChildFragmentManager.dispatchPrepareOptionsMenu(paramMenu);
    }
    return bool2;
  }
  
  void performPrimaryNavigationFragmentChanged()
  {
    boolean bool = this.mFragmentManager.isPrimaryNavigation(this);
    Boolean localBoolean = this.mIsPrimaryNavigationFragment;
    if ((localBoolean == null) || (localBoolean.booleanValue() != bool))
    {
      this.mIsPrimaryNavigationFragment = Boolean.valueOf(bool);
      onPrimaryNavigationFragmentChanged(bool);
      this.mChildFragmentManager.dispatchPrimaryNavigationFragmentChanged();
    }
  }
  
  void performResume()
  {
    this.mChildFragmentManager.noteStateNotSaved();
    this.mChildFragmentManager.execPendingActions(true);
    this.mState = 4;
    this.mCalled = false;
    onResume();
    if (this.mCalled)
    {
      this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
      if (this.mView != null) {
        this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
      }
      this.mChildFragmentManager.dispatchResume();
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Fragment ");
    localStringBuilder.append(this);
    localStringBuilder.append(" did not call through to super.onResume()");
    throw new SuperNotCalledException(localStringBuilder.toString());
  }
  
  void performSaveInstanceState(Bundle paramBundle)
  {
    onSaveInstanceState(paramBundle);
    this.mSavedStateRegistryController.performSave(paramBundle);
    Parcelable localParcelable = this.mChildFragmentManager.saveAllState();
    if (localParcelable != null) {
      paramBundle.putParcelable("android:support:fragments", localParcelable);
    }
  }
  
  void performStart()
  {
    this.mChildFragmentManager.noteStateNotSaved();
    this.mChildFragmentManager.execPendingActions(true);
    this.mState = 3;
    this.mCalled = false;
    onStart();
    if (this.mCalled)
    {
      this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
      if (this.mView != null) {
        this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_START);
      }
      this.mChildFragmentManager.dispatchStart();
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Fragment ");
    localStringBuilder.append(this);
    localStringBuilder.append(" did not call through to super.onStart()");
    throw new SuperNotCalledException(localStringBuilder.toString());
  }
  
  void performStop()
  {
    this.mChildFragmentManager.dispatchStop();
    if (this.mView != null) {
      this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }
    this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    this.mState = 2;
    this.mCalled = false;
    onStop();
    if (this.mCalled) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Fragment ");
    localStringBuilder.append(this);
    localStringBuilder.append(" did not call through to super.onStop()");
    throw new SuperNotCalledException(localStringBuilder.toString());
  }
  
  public void postponeEnterTransition()
  {
    ensureAnimationInfo().mEnterTransitionPostponed = true;
  }
  
  public final void postponeEnterTransition(long paramLong, TimeUnit paramTimeUnit)
  {
    ensureAnimationInfo().mEnterTransitionPostponed = true;
    Object localObject = this.mFragmentManager;
    if (localObject != null) {
      localObject = ((FragmentManager)localObject).mHost.getHandler();
    } else {
      localObject = new Handler(Looper.getMainLooper());
    }
    ((Handler)localObject).removeCallbacks(this.mPostponedDurationRunnable);
    ((Handler)localObject).postDelayed(this.mPostponedDurationRunnable, paramTimeUnit.toMillis(paramLong));
  }
  
  public void registerForContextMenu(View paramView)
  {
    paramView.setOnCreateContextMenuListener(this);
  }
  
  public final void requestPermissions(String[] paramArrayOfString, int paramInt)
  {
    FragmentHostCallback localFragmentHostCallback = this.mHost;
    if (localFragmentHostCallback != null)
    {
      localFragmentHostCallback.onRequestPermissionsFromFragment(this, paramArrayOfString, paramInt);
      return;
    }
    paramArrayOfString = new StringBuilder();
    paramArrayOfString.append("Fragment ");
    paramArrayOfString.append(this);
    paramArrayOfString.append(" not attached to Activity");
    throw new IllegalStateException(paramArrayOfString.toString());
  }
  
  public final FragmentActivity requireActivity()
  {
    Object localObject = getActivity();
    if (localObject != null) {
      return localObject;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Fragment ");
    ((StringBuilder)localObject).append(this);
    ((StringBuilder)localObject).append(" not attached to an activity.");
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public final Bundle requireArguments()
  {
    Object localObject = getArguments();
    if (localObject != null) {
      return localObject;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Fragment ");
    ((StringBuilder)localObject).append(this);
    ((StringBuilder)localObject).append(" does not have any arguments.");
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public final Context requireContext()
  {
    Object localObject = getContext();
    if (localObject != null) {
      return localObject;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Fragment ");
    ((StringBuilder)localObject).append(this);
    ((StringBuilder)localObject).append(" not attached to a context.");
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  @Deprecated
  public final FragmentManager requireFragmentManager()
  {
    return getParentFragmentManager();
  }
  
  public final Object requireHost()
  {
    Object localObject = getHost();
    if (localObject != null) {
      return localObject;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Fragment ");
    ((StringBuilder)localObject).append(this);
    ((StringBuilder)localObject).append(" not attached to a host.");
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public final Fragment requireParentFragment()
  {
    Object localObject = getParentFragment();
    if (localObject == null)
    {
      if (getContext() == null)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Fragment ");
        ((StringBuilder)localObject).append(this);
        ((StringBuilder)localObject).append(" is not attached to any Fragment or host");
        throw new IllegalStateException(((StringBuilder)localObject).toString());
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Fragment ");
      ((StringBuilder)localObject).append(this);
      ((StringBuilder)localObject).append(" is not a child Fragment, it is directly attached to ");
      ((StringBuilder)localObject).append(getContext());
      throw new IllegalStateException(((StringBuilder)localObject).toString());
    }
    return localObject;
  }
  
  public final View requireView()
  {
    Object localObject = getView();
    if (localObject != null) {
      return localObject;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Fragment ");
    ((StringBuilder)localObject).append(this);
    ((StringBuilder)localObject).append(" did not return a View from onCreateView() or this was called before onCreateView().");
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  void restoreChildFragmentState(Bundle paramBundle)
  {
    if (paramBundle != null)
    {
      paramBundle = paramBundle.getParcelable("android:support:fragments");
      if (paramBundle != null)
      {
        this.mChildFragmentManager.restoreSaveState(paramBundle);
        this.mChildFragmentManager.dispatchCreate();
      }
    }
  }
  
  final void restoreViewState(Bundle paramBundle)
  {
    SparseArray localSparseArray = this.mSavedViewState;
    if (localSparseArray != null)
    {
      this.mView.restoreHierarchyState(localSparseArray);
      this.mSavedViewState = null;
    }
    this.mCalled = false;
    onViewStateRestored(paramBundle);
    if (this.mCalled)
    {
      if (this.mView != null) {
        this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
      }
      return;
    }
    paramBundle = new StringBuilder();
    paramBundle.append("Fragment ");
    paramBundle.append(this);
    paramBundle.append(" did not call through to super.onViewStateRestored()");
    throw new SuperNotCalledException(paramBundle.toString());
  }
  
  public void setAllowEnterTransitionOverlap(boolean paramBoolean)
  {
    ensureAnimationInfo().mAllowEnterTransitionOverlap = Boolean.valueOf(paramBoolean);
  }
  
  public void setAllowReturnTransitionOverlap(boolean paramBoolean)
  {
    ensureAnimationInfo().mAllowReturnTransitionOverlap = Boolean.valueOf(paramBoolean);
  }
  
  void setAnimatingAway(View paramView)
  {
    ensureAnimationInfo().mAnimatingAway = paramView;
  }
  
  void setAnimator(Animator paramAnimator)
  {
    ensureAnimationInfo().mAnimator = paramAnimator;
  }
  
  public void setArguments(Bundle paramBundle)
  {
    if ((this.mFragmentManager != null) && (isStateSaved())) {
      throw new IllegalStateException("Fragment already added and state has been saved");
    }
    this.mArguments = paramBundle;
  }
  
  public void setEnterSharedElementCallback(SharedElementCallback paramSharedElementCallback)
  {
    ensureAnimationInfo().mEnterTransitionCallback = paramSharedElementCallback;
  }
  
  public void setEnterTransition(Object paramObject)
  {
    ensureAnimationInfo().mEnterTransition = paramObject;
  }
  
  public void setExitSharedElementCallback(SharedElementCallback paramSharedElementCallback)
  {
    ensureAnimationInfo().mExitTransitionCallback = paramSharedElementCallback;
  }
  
  public void setExitTransition(Object paramObject)
  {
    ensureAnimationInfo().mExitTransition = paramObject;
  }
  
  public void setHasOptionsMenu(boolean paramBoolean)
  {
    if (this.mHasMenu != paramBoolean)
    {
      this.mHasMenu = paramBoolean;
      if ((isAdded()) && (!isHidden())) {
        this.mHost.onSupportInvalidateOptionsMenu();
      }
    }
  }
  
  void setHideReplaced(boolean paramBoolean)
  {
    ensureAnimationInfo().mIsHideReplaced = paramBoolean;
  }
  
  public void setInitialSavedState(SavedState paramSavedState)
  {
    if (this.mFragmentManager == null)
    {
      if ((paramSavedState != null) && (paramSavedState.mState != null)) {
        paramSavedState = paramSavedState.mState;
      } else {
        paramSavedState = null;
      }
      this.mSavedFragmentState = paramSavedState;
      return;
    }
    throw new IllegalStateException("Fragment already added");
  }
  
  public void setMenuVisibility(boolean paramBoolean)
  {
    if (this.mMenuVisible != paramBoolean)
    {
      this.mMenuVisible = paramBoolean;
      if ((this.mHasMenu) && (isAdded()) && (!isHidden())) {
        this.mHost.onSupportInvalidateOptionsMenu();
      }
    }
  }
  
  void setNextAnim(int paramInt)
  {
    if ((this.mAnimationInfo == null) && (paramInt == 0)) {
      return;
    }
    ensureAnimationInfo().mNextAnim = paramInt;
  }
  
  void setNextTransition(int paramInt)
  {
    if ((this.mAnimationInfo == null) && (paramInt == 0)) {
      return;
    }
    ensureAnimationInfo();
    this.mAnimationInfo.mNextTransition = paramInt;
  }
  
  void setOnStartEnterTransitionListener(OnStartEnterTransitionListener paramOnStartEnterTransitionListener)
  {
    ensureAnimationInfo();
    if (paramOnStartEnterTransitionListener == this.mAnimationInfo.mStartEnterTransitionListener) {
      return;
    }
    if ((paramOnStartEnterTransitionListener != null) && (this.mAnimationInfo.mStartEnterTransitionListener != null))
    {
      paramOnStartEnterTransitionListener = new StringBuilder();
      paramOnStartEnterTransitionListener.append("Trying to set a replacement startPostponedEnterTransition on ");
      paramOnStartEnterTransitionListener.append(this);
      throw new IllegalStateException(paramOnStartEnterTransitionListener.toString());
    }
    if (this.mAnimationInfo.mEnterTransitionPostponed) {
      this.mAnimationInfo.mStartEnterTransitionListener = paramOnStartEnterTransitionListener;
    }
    if (paramOnStartEnterTransitionListener != null) {
      paramOnStartEnterTransitionListener.startListening();
    }
  }
  
  public void setReenterTransition(Object paramObject)
  {
    ensureAnimationInfo().mReenterTransition = paramObject;
  }
  
  public void setRetainInstance(boolean paramBoolean)
  {
    this.mRetainInstance = paramBoolean;
    FragmentManager localFragmentManager = this.mFragmentManager;
    if (localFragmentManager != null)
    {
      if (paramBoolean) {
        localFragmentManager.addRetainedFragment(this);
      } else {
        localFragmentManager.removeRetainedFragment(this);
      }
    }
    else {
      this.mRetainInstanceChangedWhileDetached = true;
    }
  }
  
  public void setReturnTransition(Object paramObject)
  {
    ensureAnimationInfo().mReturnTransition = paramObject;
  }
  
  public void setSharedElementEnterTransition(Object paramObject)
  {
    ensureAnimationInfo().mSharedElementEnterTransition = paramObject;
  }
  
  public void setSharedElementReturnTransition(Object paramObject)
  {
    ensureAnimationInfo().mSharedElementReturnTransition = paramObject;
  }
  
  void setStateAfterAnimating(int paramInt)
  {
    ensureAnimationInfo().mStateAfterAnimating = paramInt;
  }
  
  public void setTargetFragment(Fragment paramFragment, int paramInt)
  {
    FragmentManager localFragmentManager = this.mFragmentManager;
    if (paramFragment != null) {
      localObject = paramFragment.mFragmentManager;
    } else {
      localObject = null;
    }
    if ((localFragmentManager != null) && (localObject != null) && (localFragmentManager != localObject))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Fragment ");
      ((StringBuilder)localObject).append(paramFragment);
      ((StringBuilder)localObject).append(" must share the same FragmentManager to be set as a target fragment");
      throw new IllegalArgumentException(((StringBuilder)localObject).toString());
    }
    Object localObject = paramFragment;
    while (localObject != null) {
      if (localObject != this)
      {
        localObject = ((Fragment)localObject).getTargetFragment();
      }
      else
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Setting ");
        ((StringBuilder)localObject).append(paramFragment);
        ((StringBuilder)localObject).append(" as the target of ");
        ((StringBuilder)localObject).append(this);
        ((StringBuilder)localObject).append(" would create a target cycle");
        throw new IllegalArgumentException(((StringBuilder)localObject).toString());
      }
    }
    if (paramFragment == null)
    {
      this.mTargetWho = null;
      this.mTarget = null;
    }
    else if ((this.mFragmentManager != null) && (paramFragment.mFragmentManager != null))
    {
      this.mTargetWho = paramFragment.mWho;
      this.mTarget = null;
    }
    else
    {
      this.mTargetWho = null;
      this.mTarget = paramFragment;
    }
    this.mTargetRequestCode = paramInt;
  }
  
  @Deprecated
  public void setUserVisibleHint(boolean paramBoolean)
  {
    if ((!this.mUserVisibleHint) && (paramBoolean) && (this.mState < 3) && (this.mFragmentManager != null) && (isAdded()) && (this.mIsCreated)) {
      this.mFragmentManager.performPendingDeferredStart(this);
    }
    this.mUserVisibleHint = paramBoolean;
    boolean bool;
    if ((this.mState < 3) && (!paramBoolean)) {
      bool = true;
    } else {
      bool = false;
    }
    this.mDeferStart = bool;
    if (this.mSavedFragmentState != null) {
      this.mSavedUserVisibleHint = Boolean.valueOf(paramBoolean);
    }
  }
  
  public boolean shouldShowRequestPermissionRationale(String paramString)
  {
    FragmentHostCallback localFragmentHostCallback = this.mHost;
    if (localFragmentHostCallback != null) {
      return localFragmentHostCallback.onShouldShowRequestPermissionRationale(paramString);
    }
    return false;
  }
  
  public void startActivity(Intent paramIntent)
  {
    startActivity(paramIntent, null);
  }
  
  public void startActivity(Intent paramIntent, Bundle paramBundle)
  {
    FragmentHostCallback localFragmentHostCallback = this.mHost;
    if (localFragmentHostCallback != null)
    {
      localFragmentHostCallback.onStartActivityFromFragment(this, paramIntent, -1, paramBundle);
      return;
    }
    paramIntent = new StringBuilder();
    paramIntent.append("Fragment ");
    paramIntent.append(this);
    paramIntent.append(" not attached to Activity");
    throw new IllegalStateException(paramIntent.toString());
  }
  
  public void startActivityForResult(Intent paramIntent, int paramInt)
  {
    startActivityForResult(paramIntent, paramInt, null);
  }
  
  public void startActivityForResult(Intent paramIntent, int paramInt, Bundle paramBundle)
  {
    FragmentHostCallback localFragmentHostCallback = this.mHost;
    if (localFragmentHostCallback != null)
    {
      localFragmentHostCallback.onStartActivityFromFragment(this, paramIntent, paramInt, paramBundle);
      return;
    }
    paramIntent = new StringBuilder();
    paramIntent.append("Fragment ");
    paramIntent.append(this);
    paramIntent.append(" not attached to Activity");
    throw new IllegalStateException(paramIntent.toString());
  }
  
  public void startIntentSenderForResult(IntentSender paramIntentSender, int paramInt1, Intent paramIntent, int paramInt2, int paramInt3, int paramInt4, Bundle paramBundle)
    throws IntentSender.SendIntentException
  {
    FragmentHostCallback localFragmentHostCallback = this.mHost;
    if (localFragmentHostCallback != null)
    {
      localFragmentHostCallback.onStartIntentSenderFromFragment(this, paramIntentSender, paramInt1, paramIntent, paramInt2, paramInt3, paramInt4, paramBundle);
      return;
    }
    paramIntentSender = new StringBuilder();
    paramIntentSender.append("Fragment ");
    paramIntentSender.append(this);
    paramIntentSender.append(" not attached to Activity");
    throw new IllegalStateException(paramIntentSender.toString());
  }
  
  public void startPostponedEnterTransition()
  {
    FragmentManager localFragmentManager = this.mFragmentManager;
    if ((localFragmentManager != null) && (localFragmentManager.mHost != null))
    {
      if (Looper.myLooper() != this.mFragmentManager.mHost.getHandler().getLooper()) {
        this.mFragmentManager.mHost.getHandler().postAtFrontOfQueue(new Runnable()
        {
          public void run()
          {
            Fragment.this.callStartTransitionListener();
          }
        });
      } else {
        callStartTransitionListener();
      }
    }
    else {
      ensureAnimationInfo().mEnterTransitionPostponed = false;
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    localStringBuilder.append(getClass().getSimpleName());
    localStringBuilder.append("{");
    localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    localStringBuilder.append("}");
    localStringBuilder.append(" (");
    localStringBuilder.append(this.mWho);
    localStringBuilder.append(")");
    if (this.mFragmentId != 0)
    {
      localStringBuilder.append(" id=0x");
      localStringBuilder.append(Integer.toHexString(this.mFragmentId));
    }
    if (this.mTag != null)
    {
      localStringBuilder.append(" ");
      localStringBuilder.append(this.mTag);
    }
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
  
  public void unregisterForContextMenu(View paramView)
  {
    paramView.setOnCreateContextMenuListener(null);
  }
  
  static class AnimationInfo
  {
    Boolean mAllowEnterTransitionOverlap;
    Boolean mAllowReturnTransitionOverlap;
    View mAnimatingAway;
    Animator mAnimator;
    Object mEnterTransition = null;
    SharedElementCallback mEnterTransitionCallback = null;
    boolean mEnterTransitionPostponed;
    Object mExitTransition = null;
    SharedElementCallback mExitTransitionCallback = null;
    boolean mIsHideReplaced;
    int mNextAnim;
    int mNextTransition;
    Object mReenterTransition = Fragment.USE_DEFAULT_TRANSITION;
    Object mReturnTransition = Fragment.USE_DEFAULT_TRANSITION;
    Object mSharedElementEnterTransition = null;
    Object mSharedElementReturnTransition = Fragment.USE_DEFAULT_TRANSITION;
    Fragment.OnStartEnterTransitionListener mStartEnterTransitionListener;
    int mStateAfterAnimating;
    
    AnimationInfo() {}
  }
  
  public static class InstantiationException
    extends RuntimeException
  {
    public InstantiationException(String paramString, Exception paramException)
    {
      super(paramException);
    }
  }
  
  static abstract interface OnStartEnterTransitionListener
  {
    public abstract void onStartEnterTransition();
    
    public abstract void startListening();
  }
  
  public static class SavedState
    implements Parcelable
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public Fragment.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new Fragment.SavedState(paramAnonymousParcel, null);
      }
      
      public Fragment.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new Fragment.SavedState(paramAnonymousParcel, paramAnonymousClassLoader);
      }
      
      public Fragment.SavedState[] newArray(int paramAnonymousInt)
      {
        return new Fragment.SavedState[paramAnonymousInt];
      }
    };
    final Bundle mState;
    
    SavedState(Bundle paramBundle)
    {
      this.mState = paramBundle;
    }
    
    SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      paramParcel = paramParcel.readBundle();
      this.mState = paramParcel;
      if ((paramClassLoader != null) && (paramParcel != null)) {
        paramParcel.setClassLoader(paramClassLoader);
      }
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeBundle(this.mState);
    }
  }
}

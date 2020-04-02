package androidx.fragment.app;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import androidx.fragment.R.id;
import androidx.lifecycle.ViewModelStoreOwner;

class FragmentStateManager
{
  private static final String TAG = "FragmentManager";
  private static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
  private static final String TARGET_STATE_TAG = "android:target_state";
  private static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
  private static final String VIEW_STATE_TAG = "android:view_state";
  private final FragmentLifecycleCallbacksDispatcher mDispatcher;
  private final Fragment mFragment;
  private int mFragmentManagerState = -1;
  
  FragmentStateManager(FragmentLifecycleCallbacksDispatcher paramFragmentLifecycleCallbacksDispatcher, Fragment paramFragment)
  {
    this.mDispatcher = paramFragmentLifecycleCallbacksDispatcher;
    this.mFragment = paramFragment;
  }
  
  FragmentStateManager(FragmentLifecycleCallbacksDispatcher paramFragmentLifecycleCallbacksDispatcher, Fragment paramFragment, FragmentState paramFragmentState)
  {
    this.mDispatcher = paramFragmentLifecycleCallbacksDispatcher;
    this.mFragment = paramFragment;
    paramFragment.mSavedViewState = null;
    this.mFragment.mBackStackNesting = 0;
    this.mFragment.mInLayout = false;
    this.mFragment.mAdded = false;
    paramFragment = this.mFragment;
    if (paramFragment.mTarget != null) {
      paramFragmentLifecycleCallbacksDispatcher = this.mFragment.mTarget.mWho;
    } else {
      paramFragmentLifecycleCallbacksDispatcher = null;
    }
    paramFragment.mTargetWho = paramFragmentLifecycleCallbacksDispatcher;
    this.mFragment.mTarget = null;
    if (paramFragmentState.mSavedFragmentState != null) {
      this.mFragment.mSavedFragmentState = paramFragmentState.mSavedFragmentState;
    } else {
      this.mFragment.mSavedFragmentState = new Bundle();
    }
  }
  
  FragmentStateManager(FragmentLifecycleCallbacksDispatcher paramFragmentLifecycleCallbacksDispatcher, ClassLoader paramClassLoader, FragmentFactory paramFragmentFactory, FragmentState paramFragmentState)
  {
    this.mDispatcher = paramFragmentLifecycleCallbacksDispatcher;
    this.mFragment = paramFragmentFactory.instantiate(paramClassLoader, paramFragmentState.mClassName);
    if (paramFragmentState.mArguments != null) {
      paramFragmentState.mArguments.setClassLoader(paramClassLoader);
    }
    this.mFragment.setArguments(paramFragmentState.mArguments);
    this.mFragment.mWho = paramFragmentState.mWho;
    this.mFragment.mFromLayout = paramFragmentState.mFromLayout;
    this.mFragment.mRestored = true;
    this.mFragment.mFragmentId = paramFragmentState.mFragmentId;
    this.mFragment.mContainerId = paramFragmentState.mContainerId;
    this.mFragment.mTag = paramFragmentState.mTag;
    this.mFragment.mRetainInstance = paramFragmentState.mRetainInstance;
    this.mFragment.mRemoving = paramFragmentState.mRemoving;
    this.mFragment.mDetached = paramFragmentState.mDetached;
    this.mFragment.mHidden = paramFragmentState.mHidden;
    this.mFragment.mMaxState = androidx.lifecycle.Lifecycle.State.values()[paramFragmentState.mMaxLifecycleState];
    if (paramFragmentState.mSavedFragmentState != null) {
      this.mFragment.mSavedFragmentState = paramFragmentState.mSavedFragmentState;
    } else {
      this.mFragment.mSavedFragmentState = new Bundle();
    }
    if (FragmentManager.isLoggingEnabled(2))
    {
      paramFragmentLifecycleCallbacksDispatcher = new StringBuilder();
      paramFragmentLifecycleCallbacksDispatcher.append("Instantiated fragment ");
      paramFragmentLifecycleCallbacksDispatcher.append(this.mFragment);
      Log.v("FragmentManager", paramFragmentLifecycleCallbacksDispatcher.toString());
    }
  }
  
  private Bundle saveBasicState()
  {
    Object localObject1 = new Bundle();
    this.mFragment.performSaveInstanceState((Bundle)localObject1);
    this.mDispatcher.dispatchOnFragmentSaveInstanceState(this.mFragment, (Bundle)localObject1, false);
    Object localObject2 = localObject1;
    if (((Bundle)localObject1).isEmpty()) {
      localObject2 = null;
    }
    if (this.mFragment.mView != null) {
      saveViewState();
    }
    localObject1 = localObject2;
    if (this.mFragment.mSavedViewState != null)
    {
      localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = new Bundle();
      }
      ((Bundle)localObject1).putSparseParcelableArray("android:view_state", this.mFragment.mSavedViewState);
    }
    localObject2 = localObject1;
    if (!this.mFragment.mUserVisibleHint)
    {
      localObject2 = localObject1;
      if (localObject1 == null) {
        localObject2 = new Bundle();
      }
      ((Bundle)localObject2).putBoolean("android:user_visible_hint", this.mFragment.mUserVisibleHint);
    }
    return localObject2;
  }
  
  void activityCreated()
  {
    if (FragmentManager.isLoggingEnabled(3))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("moveto ACTIVITY_CREATED: ");
      ((StringBuilder)localObject).append(this.mFragment);
      Log.d("FragmentManager", ((StringBuilder)localObject).toString());
    }
    Object localObject = this.mFragment;
    ((Fragment)localObject).performActivityCreated(((Fragment)localObject).mSavedFragmentState);
    FragmentLifecycleCallbacksDispatcher localFragmentLifecycleCallbacksDispatcher = this.mDispatcher;
    localObject = this.mFragment;
    localFragmentLifecycleCallbacksDispatcher.dispatchOnFragmentActivityCreated((Fragment)localObject, ((Fragment)localObject).mSavedFragmentState, false);
  }
  
  void attach(FragmentHostCallback<?> paramFragmentHostCallback, FragmentManager paramFragmentManager, Fragment paramFragment)
  {
    this.mFragment.mHost = paramFragmentHostCallback;
    this.mFragment.mParentFragment = paramFragment;
    this.mFragment.mFragmentManager = paramFragmentManager;
    this.mDispatcher.dispatchOnFragmentPreAttached(this.mFragment, paramFragmentHostCallback.getContext(), false);
    this.mFragment.performAttach();
    if (this.mFragment.mParentFragment == null) {
      paramFragmentHostCallback.onAttachFragment(this.mFragment);
    } else {
      this.mFragment.mParentFragment.onAttachFragment(this.mFragment);
    }
    this.mDispatcher.dispatchOnFragmentAttached(this.mFragment, paramFragmentHostCallback.getContext(), false);
  }
  
  int computeMaxState()
  {
    int i = this.mFragmentManagerState;
    int j = i;
    if (this.mFragment.mFromLayout) {
      if (this.mFragment.mInLayout) {
        j = Math.max(this.mFragmentManagerState, 1);
      } else {
        j = Math.min(i, 1);
      }
    }
    i = j;
    if (!this.mFragment.mAdded) {
      i = Math.min(j, 1);
    }
    j = i;
    if (this.mFragment.mRemoving) {
      if (this.mFragment.isInBackStack()) {
        j = Math.min(i, 1);
      } else {
        j = Math.min(i, -1);
      }
    }
    i = j;
    if (this.mFragment.mDeferStart)
    {
      i = j;
      if (this.mFragment.mState < 3) {
        i = Math.min(j, 2);
      }
    }
    int k = 1.$SwitchMap$androidx$lifecycle$Lifecycle$State[this.mFragment.mMaxState.ordinal()];
    j = i;
    if (k != 1) {
      if (k != 2)
      {
        if (k != 3) {
          j = Math.min(i, -1);
        } else {
          j = Math.min(i, 1);
        }
      }
      else {
        j = Math.min(i, 3);
      }
    }
    return j;
  }
  
  void create()
  {
    Object localObject;
    if (FragmentManager.isLoggingEnabled(3))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("moveto CREATED: ");
      ((StringBuilder)localObject).append(this.mFragment);
      Log.d("FragmentManager", ((StringBuilder)localObject).toString());
    }
    if (!this.mFragment.mIsCreated)
    {
      FragmentLifecycleCallbacksDispatcher localFragmentLifecycleCallbacksDispatcher = this.mDispatcher;
      localObject = this.mFragment;
      localFragmentLifecycleCallbacksDispatcher.dispatchOnFragmentPreCreated((Fragment)localObject, ((Fragment)localObject).mSavedFragmentState, false);
      localObject = this.mFragment;
      ((Fragment)localObject).performCreate(((Fragment)localObject).mSavedFragmentState);
      localFragmentLifecycleCallbacksDispatcher = this.mDispatcher;
      localObject = this.mFragment;
      localFragmentLifecycleCallbacksDispatcher.dispatchOnFragmentCreated((Fragment)localObject, ((Fragment)localObject).mSavedFragmentState, false);
    }
    else
    {
      localObject = this.mFragment;
      ((Fragment)localObject).restoreChildFragmentState(((Fragment)localObject).mSavedFragmentState);
      this.mFragment.mState = 1;
    }
  }
  
  void createView(FragmentContainer paramFragmentContainer)
  {
    if (this.mFragment.mFromLayout) {
      return;
    }
    if (FragmentManager.isLoggingEnabled(3))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("moveto CREATE_VIEW: ");
      ((StringBuilder)localObject).append(this.mFragment);
      Log.d("FragmentManager", ((StringBuilder)localObject).toString());
    }
    Object localObject = null;
    if (this.mFragment.mContainer != null) {
      localObject = this.mFragment.mContainer;
    } else if (this.mFragment.mContainerId != 0) {
      if (this.mFragment.mContainerId != -1)
      {
        paramFragmentContainer = (ViewGroup)paramFragmentContainer.onFindViewById(this.mFragment.mContainerId);
        localObject = paramFragmentContainer;
        if (paramFragmentContainer == null) {
          if (this.mFragment.mRestored)
          {
            localObject = paramFragmentContainer;
          }
          else
          {
            try
            {
              paramFragmentContainer = this.mFragment.getResources().getResourceName(this.mFragment.mContainerId);
            }
            catch (Resources.NotFoundException paramFragmentContainer)
            {
              paramFragmentContainer = "unknown";
            }
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("No view found for id 0x");
            ((StringBuilder)localObject).append(Integer.toHexString(this.mFragment.mContainerId));
            ((StringBuilder)localObject).append(" (");
            ((StringBuilder)localObject).append(paramFragmentContainer);
            ((StringBuilder)localObject).append(") for fragment ");
            ((StringBuilder)localObject).append(this.mFragment);
            throw new IllegalArgumentException(((StringBuilder)localObject).toString());
          }
        }
      }
      else
      {
        paramFragmentContainer = new StringBuilder();
        paramFragmentContainer.append("Cannot create fragment ");
        paramFragmentContainer.append(this.mFragment);
        paramFragmentContainer.append(" for a container view with no id");
        throw new IllegalArgumentException(paramFragmentContainer.toString());
      }
    }
    this.mFragment.mContainer = ((ViewGroup)localObject);
    paramFragmentContainer = this.mFragment;
    paramFragmentContainer.performCreateView(paramFragmentContainer.performGetLayoutInflater(paramFragmentContainer.mSavedFragmentState), (ViewGroup)localObject, this.mFragment.mSavedFragmentState);
    if (this.mFragment.mView != null)
    {
      paramFragmentContainer = this.mFragment.mView;
      boolean bool1 = false;
      paramFragmentContainer.setSaveFromParentEnabled(false);
      this.mFragment.mView.setTag(R.id.fragment_container_view_tag, this.mFragment);
      if (localObject != null) {
        ((ViewGroup)localObject).addView(this.mFragment.mView);
      }
      if (this.mFragment.mHidden) {
        this.mFragment.mView.setVisibility(8);
      }
      ViewCompat.requestApplyInsets(this.mFragment.mView);
      paramFragmentContainer = this.mFragment;
      paramFragmentContainer.onViewCreated(paramFragmentContainer.mView, this.mFragment.mSavedFragmentState);
      paramFragmentContainer = this.mDispatcher;
      localObject = this.mFragment;
      paramFragmentContainer.dispatchOnFragmentViewCreated((Fragment)localObject, ((Fragment)localObject).mView, this.mFragment.mSavedFragmentState, false);
      paramFragmentContainer = this.mFragment;
      boolean bool2 = bool1;
      if (paramFragmentContainer.mView.getVisibility() == 0)
      {
        bool2 = bool1;
        if (this.mFragment.mContainer != null) {
          bool2 = true;
        }
      }
      paramFragmentContainer.mIsNewlyAdded = bool2;
    }
  }
  
  void destroy(FragmentHostCallback<?> paramFragmentHostCallback, FragmentManagerViewModel paramFragmentManagerViewModel)
  {
    if (FragmentManager.isLoggingEnabled(3))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("movefrom CREATED: ");
      localStringBuilder.append(this.mFragment);
      Log.d("FragmentManager", localStringBuilder.toString());
    }
    boolean bool1 = this.mFragment.mRemoving;
    boolean bool2 = true;
    int i;
    if ((bool1) && (!this.mFragment.isInBackStack())) {
      i = 1;
    } else {
      i = 0;
    }
    int j;
    if ((i == 0) && (!paramFragmentManagerViewModel.shouldDestroy(this.mFragment))) {
      j = 0;
    } else {
      j = 1;
    }
    if (j != 0)
    {
      if ((paramFragmentHostCallback instanceof ViewModelStoreOwner)) {
        bool2 = paramFragmentManagerViewModel.isCleared();
      } else if ((paramFragmentHostCallback.getContext() instanceof Activity)) {
        bool2 = true ^ ((Activity)paramFragmentHostCallback.getContext()).isChangingConfigurations();
      }
      if ((i != 0) || (bool2)) {
        paramFragmentManagerViewModel.clearNonConfigState(this.mFragment);
      }
      this.mFragment.performDestroy();
      this.mDispatcher.dispatchOnFragmentDestroyed(this.mFragment, false);
    }
    else
    {
      this.mFragment.mState = 0;
    }
  }
  
  void detach(FragmentManagerViewModel paramFragmentManagerViewModel)
  {
    if (FragmentManager.isLoggingEnabled(3))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("movefrom ATTACHED: ");
      ((StringBuilder)localObject).append(this.mFragment);
      Log.d("FragmentManager", ((StringBuilder)localObject).toString());
    }
    this.mFragment.performDetach();
    FragmentLifecycleCallbacksDispatcher localFragmentLifecycleCallbacksDispatcher = this.mDispatcher;
    Object localObject = this.mFragment;
    int i = 0;
    localFragmentLifecycleCallbacksDispatcher.dispatchOnFragmentDetached((Fragment)localObject, false);
    this.mFragment.mState = -1;
    this.mFragment.mHost = null;
    this.mFragment.mParentFragment = null;
    this.mFragment.mFragmentManager = null;
    int j = i;
    if (this.mFragment.mRemoving)
    {
      j = i;
      if (!this.mFragment.isInBackStack()) {
        j = 1;
      }
    }
    if ((j != 0) || (paramFragmentManagerViewModel.shouldDestroy(this.mFragment)))
    {
      if (FragmentManager.isLoggingEnabled(3))
      {
        paramFragmentManagerViewModel = new StringBuilder();
        paramFragmentManagerViewModel.append("initState called for fragment: ");
        paramFragmentManagerViewModel.append(this.mFragment);
        Log.d("FragmentManager", paramFragmentManagerViewModel.toString());
      }
      this.mFragment.initState();
    }
  }
  
  void ensureInflatedView()
  {
    if ((this.mFragment.mFromLayout) && (this.mFragment.mInLayout) && (!this.mFragment.mPerformedCreateView))
    {
      if (FragmentManager.isLoggingEnabled(3))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("moveto CREATE_VIEW: ");
        ((StringBuilder)localObject).append(this.mFragment);
        Log.d("FragmentManager", ((StringBuilder)localObject).toString());
      }
      Object localObject = this.mFragment;
      ((Fragment)localObject).performCreateView(((Fragment)localObject).performGetLayoutInflater(((Fragment)localObject).mSavedFragmentState), null, this.mFragment.mSavedFragmentState);
      if (this.mFragment.mView != null)
      {
        this.mFragment.mView.setSaveFromParentEnabled(false);
        if (this.mFragment.mHidden) {
          this.mFragment.mView.setVisibility(8);
        }
        localObject = this.mFragment;
        ((Fragment)localObject).onViewCreated(((Fragment)localObject).mView, this.mFragment.mSavedFragmentState);
        FragmentLifecycleCallbacksDispatcher localFragmentLifecycleCallbacksDispatcher = this.mDispatcher;
        localObject = this.mFragment;
        localFragmentLifecycleCallbacksDispatcher.dispatchOnFragmentViewCreated((Fragment)localObject, ((Fragment)localObject).mView, this.mFragment.mSavedFragmentState, false);
      }
    }
  }
  
  Fragment getFragment()
  {
    return this.mFragment;
  }
  
  void pause()
  {
    if (FragmentManager.isLoggingEnabled(3))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("movefrom RESUMED: ");
      localStringBuilder.append(this.mFragment);
      Log.d("FragmentManager", localStringBuilder.toString());
    }
    this.mFragment.performPause();
    this.mDispatcher.dispatchOnFragmentPaused(this.mFragment, false);
  }
  
  void restoreState(ClassLoader paramClassLoader)
  {
    if (this.mFragment.mSavedFragmentState == null) {
      return;
    }
    this.mFragment.mSavedFragmentState.setClassLoader(paramClassLoader);
    paramClassLoader = this.mFragment;
    paramClassLoader.mSavedViewState = paramClassLoader.mSavedFragmentState.getSparseParcelableArray("android:view_state");
    paramClassLoader = this.mFragment;
    paramClassLoader.mTargetWho = paramClassLoader.mSavedFragmentState.getString("android:target_state");
    if (this.mFragment.mTargetWho != null)
    {
      paramClassLoader = this.mFragment;
      paramClassLoader.mTargetRequestCode = paramClassLoader.mSavedFragmentState.getInt("android:target_req_state", 0);
    }
    if (this.mFragment.mSavedUserVisibleHint != null)
    {
      paramClassLoader = this.mFragment;
      paramClassLoader.mUserVisibleHint = paramClassLoader.mSavedUserVisibleHint.booleanValue();
      this.mFragment.mSavedUserVisibleHint = null;
    }
    else
    {
      paramClassLoader = this.mFragment;
      paramClassLoader.mUserVisibleHint = paramClassLoader.mSavedFragmentState.getBoolean("android:user_visible_hint", true);
    }
    if (!this.mFragment.mUserVisibleHint) {
      this.mFragment.mDeferStart = true;
    }
  }
  
  void restoreViewState()
  {
    Object localObject;
    if (FragmentManager.isLoggingEnabled(3))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("moveto RESTORE_VIEW_STATE: ");
      ((StringBuilder)localObject).append(this.mFragment);
      Log.d("FragmentManager", ((StringBuilder)localObject).toString());
    }
    if (this.mFragment.mView != null)
    {
      localObject = this.mFragment;
      ((Fragment)localObject).restoreViewState(((Fragment)localObject).mSavedFragmentState);
    }
    this.mFragment.mSavedFragmentState = null;
  }
  
  void resume()
  {
    if (FragmentManager.isLoggingEnabled(3))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("moveto RESUMED: ");
      localStringBuilder.append(this.mFragment);
      Log.d("FragmentManager", localStringBuilder.toString());
    }
    this.mFragment.performResume();
    this.mDispatcher.dispatchOnFragmentResumed(this.mFragment, false);
    this.mFragment.mSavedFragmentState = null;
    this.mFragment.mSavedViewState = null;
  }
  
  Fragment.SavedState saveInstanceState()
  {
    int i = this.mFragment.mState;
    Object localObject1 = null;
    Object localObject2 = localObject1;
    if (i > -1)
    {
      Bundle localBundle = saveBasicState();
      localObject2 = localObject1;
      if (localBundle != null) {
        localObject2 = new Fragment.SavedState(localBundle);
      }
    }
    return localObject2;
  }
  
  FragmentState saveState()
  {
    FragmentState localFragmentState = new FragmentState(this.mFragment);
    if ((this.mFragment.mState > -1) && (localFragmentState.mSavedFragmentState == null))
    {
      localFragmentState.mSavedFragmentState = saveBasicState();
      if (this.mFragment.mTargetWho != null)
      {
        if (localFragmentState.mSavedFragmentState == null) {
          localFragmentState.mSavedFragmentState = new Bundle();
        }
        localFragmentState.mSavedFragmentState.putString("android:target_state", this.mFragment.mTargetWho);
        if (this.mFragment.mTargetRequestCode != 0) {
          localFragmentState.mSavedFragmentState.putInt("android:target_req_state", this.mFragment.mTargetRequestCode);
        }
      }
    }
    else
    {
      localFragmentState.mSavedFragmentState = this.mFragment.mSavedFragmentState;
    }
    return localFragmentState;
  }
  
  void saveViewState()
  {
    if (this.mFragment.mView == null) {
      return;
    }
    SparseArray localSparseArray = new SparseArray();
    this.mFragment.mView.saveHierarchyState(localSparseArray);
    if (localSparseArray.size() > 0) {
      this.mFragment.mSavedViewState = localSparseArray;
    }
  }
  
  void setFragmentManagerState(int paramInt)
  {
    this.mFragmentManagerState = paramInt;
  }
  
  void start()
  {
    if (FragmentManager.isLoggingEnabled(3))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("moveto STARTED: ");
      localStringBuilder.append(this.mFragment);
      Log.d("FragmentManager", localStringBuilder.toString());
    }
    this.mFragment.performStart();
    this.mDispatcher.dispatchOnFragmentStarted(this.mFragment, false);
  }
  
  void stop()
  {
    if (FragmentManager.isLoggingEnabled(3))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("movefrom STARTED: ");
      localStringBuilder.append(this.mFragment);
      Log.d("FragmentManager", localStringBuilder.toString());
    }
    this.mFragment.performStop();
    this.mDispatcher.dispatchOnFragmentStopped(this.mFragment, false);
  }
}

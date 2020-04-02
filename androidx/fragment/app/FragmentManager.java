package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater.Factory2;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.collection.ArraySet;
import androidx.core.os.CancellationSignal;
import androidx.core.util.LogWriter;
import androidx.fragment.R.id;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class FragmentManager
{
  private static boolean DEBUG = false;
  public static final int POP_BACK_STACK_INCLUSIVE = 1;
  static final String TAG = "FragmentManager";
  ArrayList<BackStackRecord> mBackStack;
  private ArrayList<OnBackStackChangedListener> mBackStackChangeListeners;
  private final AtomicInteger mBackStackIndex = new AtomicInteger();
  FragmentContainer mContainer;
  private ArrayList<Fragment> mCreatedMenus;
  int mCurState = -1;
  private boolean mDestroyed;
  private Runnable mExecCommit = new Runnable()
  {
    public void run()
    {
      FragmentManager.this.execPendingActions(true);
    }
  };
  private boolean mExecutingActions;
  private ConcurrentHashMap<Fragment, HashSet<CancellationSignal>> mExitAnimationCancellationSignals = new ConcurrentHashMap();
  private FragmentFactory mFragmentFactory = null;
  private final FragmentStore mFragmentStore = new FragmentStore();
  private final FragmentTransition.Callback mFragmentTransitionCallback = new FragmentTransition.Callback()
  {
    public void onComplete(Fragment paramAnonymousFragment, CancellationSignal paramAnonymousCancellationSignal)
    {
      if (!paramAnonymousCancellationSignal.isCanceled()) {
        FragmentManager.this.removeCancellationSignal(paramAnonymousFragment, paramAnonymousCancellationSignal);
      }
    }
    
    public void onStart(Fragment paramAnonymousFragment, CancellationSignal paramAnonymousCancellationSignal)
    {
      FragmentManager.this.addCancellationSignal(paramAnonymousFragment, paramAnonymousCancellationSignal);
    }
  };
  private boolean mHavePendingDeferredStart;
  FragmentHostCallback<?> mHost;
  private FragmentFactory mHostFragmentFactory = new FragmentFactory()
  {
    public Fragment instantiate(ClassLoader paramAnonymousClassLoader, String paramAnonymousString)
    {
      return FragmentManager.this.mHost.instantiate(FragmentManager.this.mHost.getContext(), paramAnonymousString, null);
    }
  };
  private final FragmentLayoutInflaterFactory mLayoutInflaterFactory = new FragmentLayoutInflaterFactory(this);
  private final FragmentLifecycleCallbacksDispatcher mLifecycleCallbacksDispatcher = new FragmentLifecycleCallbacksDispatcher(this);
  private boolean mNeedMenuInvalidate;
  private FragmentManagerViewModel mNonConfig;
  private final OnBackPressedCallback mOnBackPressedCallback = new OnBackPressedCallback(false)
  {
    public void handleOnBackPressed()
    {
      FragmentManager.this.handleOnBackPressed();
    }
  };
  private OnBackPressedDispatcher mOnBackPressedDispatcher;
  private Fragment mParent;
  private final ArrayList<OpGenerator> mPendingActions = new ArrayList();
  private ArrayList<StartEnterTransitionListener> mPostponedTransactions;
  Fragment mPrimaryNav;
  private boolean mStateSaved;
  private boolean mStopped;
  private ArrayList<Fragment> mTmpAddedFragments;
  private ArrayList<Boolean> mTmpIsPop;
  private ArrayList<BackStackRecord> mTmpRecords;
  
  public FragmentManager() {}
  
  private void addAddedFragments(ArraySet<Fragment> paramArraySet)
  {
    int i = this.mCurState;
    if (i < 1) {
      return;
    }
    i = Math.min(i, 3);
    Iterator localIterator = this.mFragmentStore.getFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if (localFragment.mState < i)
      {
        moveToState(localFragment, i);
        if ((localFragment.mView != null) && (!localFragment.mHidden) && (localFragment.mIsNewlyAdded)) {
          paramArraySet.add(localFragment);
        }
      }
    }
  }
  
  private void cancelExitAnimation(Fragment paramFragment)
  {
    HashSet localHashSet = (HashSet)this.mExitAnimationCancellationSignals.get(paramFragment);
    if (localHashSet != null)
    {
      Iterator localIterator = localHashSet.iterator();
      while (localIterator.hasNext()) {
        ((CancellationSignal)localIterator.next()).cancel();
      }
      localHashSet.clear();
      destroyFragmentView(paramFragment);
      this.mExitAnimationCancellationSignals.remove(paramFragment);
    }
  }
  
  private void checkStateLoss()
  {
    if (!isStateSaved()) {
      return;
    }
    throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
  }
  
  private void cleanupExec()
  {
    this.mExecutingActions = false;
    this.mTmpIsPop.clear();
    this.mTmpRecords.clear();
  }
  
  private void completeShowHideFragment(final Fragment paramFragment)
  {
    if (paramFragment.mView != null)
    {
      FragmentAnim.AnimationOrAnimator localAnimationOrAnimator = FragmentAnim.loadAnimation(this.mHost.getContext(), this.mContainer, paramFragment, paramFragment.mHidden ^ true);
      if ((localAnimationOrAnimator != null) && (localAnimationOrAnimator.animator != null))
      {
        localAnimationOrAnimator.animator.setTarget(paramFragment.mView);
        if (paramFragment.mHidden)
        {
          if (paramFragment.isHideReplaced())
          {
            paramFragment.setHideReplaced(false);
          }
          else
          {
            final ViewGroup localViewGroup = paramFragment.mContainer;
            final View localView = paramFragment.mView;
            localViewGroup.startViewTransition(localView);
            localAnimationOrAnimator.animator.addListener(new AnimatorListenerAdapter()
            {
              public void onAnimationEnd(Animator paramAnonymousAnimator)
              {
                localViewGroup.endViewTransition(localView);
                paramAnonymousAnimator.removeListener(this);
                if ((paramFragment.mView != null) && (paramFragment.mHidden)) {
                  paramFragment.mView.setVisibility(8);
                }
              }
            });
          }
        }
        else {
          paramFragment.mView.setVisibility(0);
        }
        localAnimationOrAnimator.animator.start();
      }
      else
      {
        if (localAnimationOrAnimator != null)
        {
          paramFragment.mView.startAnimation(localAnimationOrAnimator.animation);
          localAnimationOrAnimator.animation.start();
        }
        int i;
        if ((paramFragment.mHidden) && (!paramFragment.isHideReplaced())) {
          i = 8;
        } else {
          i = 0;
        }
        paramFragment.mView.setVisibility(i);
        if (paramFragment.isHideReplaced()) {
          paramFragment.setHideReplaced(false);
        }
      }
    }
    if ((paramFragment.mAdded) && (isMenuAvailable(paramFragment))) {
      this.mNeedMenuInvalidate = true;
    }
    paramFragment.mHiddenChanged = false;
    paramFragment.onHiddenChanged(paramFragment.mHidden);
  }
  
  private void destroyFragmentView(Fragment paramFragment)
  {
    paramFragment.performDestroyView();
    this.mLifecycleCallbacksDispatcher.dispatchOnFragmentViewDestroyed(paramFragment, false);
    paramFragment.mContainer = null;
    paramFragment.mView = null;
    paramFragment.mViewLifecycleOwner = null;
    paramFragment.mViewLifecycleOwnerLiveData.setValue(null);
    paramFragment.mInLayout = false;
  }
  
  private void dispatchParentPrimaryNavigationFragmentChanged(Fragment paramFragment)
  {
    if ((paramFragment != null) && (paramFragment.equals(findActiveFragment(paramFragment.mWho)))) {
      paramFragment.performPrimaryNavigationFragmentChanged();
    }
  }
  
  private void dispatchStateChange(int paramInt)
  {
    try
    {
      this.mExecutingActions = true;
      this.mFragmentStore.dispatchStateChange(paramInt);
      moveToState(paramInt, false);
      this.mExecutingActions = false;
      execPendingActions(true);
      return;
    }
    finally
    {
      this.mExecutingActions = false;
    }
  }
  
  private void doPendingDeferredStart()
  {
    if (this.mHavePendingDeferredStart)
    {
      this.mHavePendingDeferredStart = false;
      startPendingDeferredFragments();
    }
  }
  
  @Deprecated
  public static void enableDebugLogging(boolean paramBoolean)
  {
    DEBUG = paramBoolean;
  }
  
  private void endAnimatingAwayFragments()
  {
    if (!this.mExitAnimationCancellationSignals.isEmpty())
    {
      Iterator localIterator = this.mExitAnimationCancellationSignals.keySet().iterator();
      while (localIterator.hasNext())
      {
        Fragment localFragment = (Fragment)localIterator.next();
        cancelExitAnimation(localFragment);
        moveToState(localFragment, localFragment.getStateAfterAnimating());
      }
    }
  }
  
  private void ensureExecReady(boolean paramBoolean)
  {
    if (!this.mExecutingActions)
    {
      if (this.mHost == null)
      {
        if (this.mDestroyed) {
          throw new IllegalStateException("FragmentManager has been destroyed");
        }
        throw new IllegalStateException("FragmentManager has not been attached to a host.");
      }
      if (Looper.myLooper() == this.mHost.getHandler().getLooper())
      {
        if (!paramBoolean) {
          checkStateLoss();
        }
        if (this.mTmpRecords == null)
        {
          this.mTmpRecords = new ArrayList();
          this.mTmpIsPop = new ArrayList();
        }
        this.mExecutingActions = true;
        try
        {
          executePostponedTransaction(null, null);
          return;
        }
        finally
        {
          this.mExecutingActions = false;
        }
      }
      throw new IllegalStateException("Must be called from main thread of fragment host");
    }
    throw new IllegalStateException("FragmentManager is already executing transactions");
  }
  
  private static void executeOps(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2)
  {
    while (paramInt1 < paramInt2)
    {
      BackStackRecord localBackStackRecord = (BackStackRecord)paramArrayList.get(paramInt1);
      boolean bool1 = ((Boolean)paramArrayList1.get(paramInt1)).booleanValue();
      boolean bool2 = true;
      if (bool1)
      {
        localBackStackRecord.bumpBackStackNesting(-1);
        if (paramInt1 != paramInt2 - 1) {
          bool2 = false;
        }
        localBackStackRecord.executePopOps(bool2);
      }
      else
      {
        localBackStackRecord.bumpBackStackNesting(1);
        localBackStackRecord.executeOps();
      }
      paramInt1++;
    }
  }
  
  private void executeOpsTogether(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2)
  {
    int i = paramInt1;
    boolean bool = ((BackStackRecord)paramArrayList.get(i)).mReorderingAllowed;
    Object localObject = this.mTmpAddedFragments;
    if (localObject == null) {
      this.mTmpAddedFragments = new ArrayList();
    } else {
      ((ArrayList)localObject).clear();
    }
    this.mTmpAddedFragments.addAll(this.mFragmentStore.getFragments());
    localObject = getPrimaryNavigationFragment();
    int j = 0;
    for (int k = i; k < paramInt2; k++)
    {
      BackStackRecord localBackStackRecord = (BackStackRecord)paramArrayList.get(k);
      if (!((Boolean)paramArrayList1.get(k)).booleanValue()) {
        localObject = localBackStackRecord.expandOps(this.mTmpAddedFragments, (Fragment)localObject);
      } else {
        localObject = localBackStackRecord.trackAddedFragmentsInPop(this.mTmpAddedFragments, (Fragment)localObject);
      }
      if ((j == 0) && (!localBackStackRecord.mAddToBackStack)) {
        j = 0;
      } else {
        j = 1;
      }
    }
    this.mTmpAddedFragments.clear();
    if (!bool) {
      FragmentTransition.startTransitions(this, paramArrayList, paramArrayList1, paramInt1, paramInt2, false, this.mFragmentTransitionCallback);
    }
    executeOps(paramArrayList, paramArrayList1, paramInt1, paramInt2);
    int m;
    if (bool)
    {
      localObject = new ArraySet();
      addAddedFragments((ArraySet)localObject);
      m = postponePostponableTransactions(paramArrayList, paramArrayList1, paramInt1, paramInt2, (ArraySet)localObject);
      makeRemovedFragmentsInvisible((ArraySet)localObject);
    }
    else
    {
      m = paramInt2;
    }
    k = i;
    if (m != i)
    {
      k = i;
      if (bool)
      {
        FragmentTransition.startTransitions(this, paramArrayList, paramArrayList1, paramInt1, m, true, this.mFragmentTransitionCallback);
        moveToState(this.mCurState, true);
      }
    }
    for (k = i; k < paramInt2; k++)
    {
      localObject = (BackStackRecord)paramArrayList.get(k);
      if ((((Boolean)paramArrayList1.get(k)).booleanValue()) && (((BackStackRecord)localObject).mIndex >= 0)) {
        ((BackStackRecord)localObject).mIndex = -1;
      }
      ((BackStackRecord)localObject).runOnCommitRunnables();
    }
    if (j != 0) {
      reportBackStackChanged();
    }
  }
  
  private void executePostponedTransaction(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1)
  {
    Object localObject = this.mPostponedTransactions;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = ((ArrayList)localObject).size();
    }
    int j = 0;
    for (int k = i; j < k; k = i)
    {
      localObject = (StartEnterTransitionListener)this.mPostponedTransactions.get(j);
      int m;
      if ((paramArrayList != null) && (!((StartEnterTransitionListener)localObject).mIsBack))
      {
        i = paramArrayList.indexOf(((StartEnterTransitionListener)localObject).mRecord);
        if ((i != -1) && (paramArrayList1 != null) && (((Boolean)paramArrayList1.get(i)).booleanValue()))
        {
          this.mPostponedTransactions.remove(j);
          m = j - 1;
          i = k - 1;
          ((StartEnterTransitionListener)localObject).cancelTransaction();
          break label245;
        }
      }
      if (!((StartEnterTransitionListener)localObject).isReady())
      {
        i = k;
        m = j;
        if (paramArrayList != null)
        {
          i = k;
          m = j;
          if (!((StartEnterTransitionListener)localObject).mRecord.interactsWith(paramArrayList, 0, paramArrayList.size())) {}
        }
      }
      else
      {
        this.mPostponedTransactions.remove(j);
        m = j - 1;
        i = k - 1;
        if ((paramArrayList != null) && (!((StartEnterTransitionListener)localObject).mIsBack))
        {
          j = paramArrayList.indexOf(((StartEnterTransitionListener)localObject).mRecord);
          if ((j != -1) && (paramArrayList1 != null) && (((Boolean)paramArrayList1.get(j)).booleanValue()))
          {
            ((StartEnterTransitionListener)localObject).cancelTransaction();
            break label245;
          }
        }
        ((StartEnterTransitionListener)localObject).completeTransaction();
      }
      label245:
      j = m + 1;
    }
  }
  
  public static <F extends Fragment> F findFragment(View paramView)
  {
    Object localObject = findViewFragment(paramView);
    if (localObject != null) {
      return localObject;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("View ");
    ((StringBuilder)localObject).append(paramView);
    ((StringBuilder)localObject).append(" does not have a Fragment set");
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  static FragmentManager findFragmentManager(View paramView)
  {
    Object localObject1 = findViewFragment(paramView);
    if (localObject1 != null)
    {
      paramView = ((Fragment)localObject1).getChildFragmentManager();
    }
    else
    {
      Context localContext = paramView.getContext();
      Object localObject2 = null;
      for (;;)
      {
        localObject1 = localObject2;
        if (!(localContext instanceof ContextWrapper)) {
          break;
        }
        if ((localContext instanceof FragmentActivity))
        {
          localObject1 = (FragmentActivity)localContext;
          break;
        }
        localContext = ((ContextWrapper)localContext).getBaseContext();
      }
      if (localObject1 == null) {
        break label70;
      }
      paramView = ((FragmentActivity)localObject1).getSupportFragmentManager();
    }
    return paramView;
    label70:
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("View ");
    ((StringBuilder)localObject1).append(paramView);
    ((StringBuilder)localObject1).append(" is not within a subclass of FragmentActivity.");
    throw new IllegalStateException(((StringBuilder)localObject1).toString());
  }
  
  private static Fragment findViewFragment(View paramView)
  {
    while (paramView != null)
    {
      Fragment localFragment = getViewFragment(paramView);
      if (localFragment != null) {
        return localFragment;
      }
      paramView = paramView.getParent();
      if ((paramView instanceof View)) {
        paramView = (View)paramView;
      } else {
        paramView = null;
      }
    }
    return null;
  }
  
  private void forcePostponedTransactions()
  {
    if (this.mPostponedTransactions != null) {
      while (!this.mPostponedTransactions.isEmpty()) {
        ((StartEnterTransitionListener)this.mPostponedTransactions.remove(0)).completeTransaction();
      }
    }
  }
  
  private boolean generateOpsForPendingActions(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1)
  {
    synchronized (this.mPendingActions)
    {
      boolean bool = this.mPendingActions.isEmpty();
      int i = 0;
      if (bool) {
        return false;
      }
      int j = this.mPendingActions.size();
      bool = false;
      while (i < j)
      {
        bool |= ((OpGenerator)this.mPendingActions.get(i)).generateOps(paramArrayList, paramArrayList1);
        i++;
      }
      this.mPendingActions.clear();
      this.mHost.getHandler().removeCallbacks(this.mExecCommit);
      return bool;
    }
  }
  
  private FragmentManagerViewModel getChildNonConfig(Fragment paramFragment)
  {
    return this.mNonConfig.getChildNonConfig(paramFragment);
  }
  
  private ViewGroup getFragmentContainer(Fragment paramFragment)
  {
    if (paramFragment.mContainerId <= 0) {
      return null;
    }
    if (this.mContainer.onHasView())
    {
      paramFragment = this.mContainer.onFindViewById(paramFragment.mContainerId);
      if ((paramFragment instanceof ViewGroup)) {
        return (ViewGroup)paramFragment;
      }
    }
    return null;
  }
  
  static Fragment getViewFragment(View paramView)
  {
    paramView = paramView.getTag(R.id.fragment_container_view_tag);
    if ((paramView instanceof Fragment)) {
      return (Fragment)paramView;
    }
    return null;
  }
  
  static boolean isLoggingEnabled(int paramInt)
  {
    boolean bool;
    if ((!DEBUG) && (!Log.isLoggable("FragmentManager", paramInt))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private boolean isMenuAvailable(Fragment paramFragment)
  {
    boolean bool;
    if (((paramFragment.mHasMenu) && (paramFragment.mMenuVisible)) || (paramFragment.mChildFragmentManager.checkForMenus())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void makeInactive(FragmentStateManager paramFragmentStateManager)
  {
    Fragment localFragment = paramFragmentStateManager.getFragment();
    if (!this.mFragmentStore.containsActiveFragment(localFragment.mWho)) {
      return;
    }
    if (isLoggingEnabled(2))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Removed fragment from active set ");
      localStringBuilder.append(localFragment);
      Log.v("FragmentManager", localStringBuilder.toString());
    }
    this.mFragmentStore.makeInactive(paramFragmentStateManager);
    removeRetainedFragment(localFragment);
  }
  
  private void makeRemovedFragmentsInvisible(ArraySet<Fragment> paramArraySet)
  {
    int i = paramArraySet.size();
    for (int j = 0; j < i; j++)
    {
      Fragment localFragment = (Fragment)paramArraySet.valueAt(j);
      if (!localFragment.mAdded)
      {
        View localView = localFragment.requireView();
        localFragment.mPostponedAlpha = localView.getAlpha();
        localView.setAlpha(0.0F);
      }
    }
  }
  
  private boolean popBackStackImmediate(String paramString, int paramInt1, int paramInt2)
  {
    execPendingActions(false);
    ensureExecReady(true);
    Fragment localFragment = this.mPrimaryNav;
    if ((localFragment != null) && (paramInt1 < 0) && (paramString == null) && (localFragment.getChildFragmentManager().popBackStackImmediate())) {
      return true;
    }
    boolean bool = popBackStackState(this.mTmpRecords, this.mTmpIsPop, paramString, paramInt1, paramInt2);
    if (bool) {
      this.mExecutingActions = true;
    }
    try
    {
      removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
      cleanupExec();
    }
    finally
    {
      cleanupExec();
    }
    doPendingDeferredStart();
    this.mFragmentStore.burpActive();
    return bool;
  }
  
  private int postponePostponableTransactions(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2, ArraySet<Fragment> paramArraySet)
  {
    int i = paramInt2 - 1;
    int m;
    for (int j = paramInt2; i >= paramInt1; j = m)
    {
      BackStackRecord localBackStackRecord = (BackStackRecord)paramArrayList.get(i);
      boolean bool = ((Boolean)paramArrayList1.get(i)).booleanValue();
      int k;
      if ((localBackStackRecord.isPostponed()) && (!localBackStackRecord.interactsWith(paramArrayList, i + 1, paramInt2))) {
        k = 1;
      } else {
        k = 0;
      }
      m = j;
      if (k != 0)
      {
        if (this.mPostponedTransactions == null) {
          this.mPostponedTransactions = new ArrayList();
        }
        StartEnterTransitionListener localStartEnterTransitionListener = new StartEnterTransitionListener(localBackStackRecord, bool);
        this.mPostponedTransactions.add(localStartEnterTransitionListener);
        localBackStackRecord.setOnStartPostponedListener(localStartEnterTransitionListener);
        if (bool) {
          localBackStackRecord.executeOps();
        } else {
          localBackStackRecord.executePopOps(false);
        }
        m = j - 1;
        if (i != m)
        {
          paramArrayList.remove(i);
          paramArrayList.add(m, localBackStackRecord);
        }
        addAddedFragments(paramArraySet);
      }
      i--;
    }
    return j;
  }
  
  private void removeRedundantOperationsAndExecute(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1)
  {
    if (paramArrayList.isEmpty()) {
      return;
    }
    if (paramArrayList.size() == paramArrayList1.size())
    {
      executePostponedTransaction(paramArrayList, paramArrayList1);
      int i = paramArrayList.size();
      int j = 0;
      int n;
      for (int k = 0; j < i; k = n)
      {
        int m = j;
        n = k;
        if (!((BackStackRecord)paramArrayList.get(j)).mReorderingAllowed)
        {
          if (k != j) {
            executeOpsTogether(paramArrayList, paramArrayList1, k, j);
          }
          k = j + 1;
          n = k;
          if (((Boolean)paramArrayList1.get(j)).booleanValue()) {
            for (;;)
            {
              n = k;
              if (k >= i) {
                break;
              }
              n = k;
              if (!((Boolean)paramArrayList1.get(k)).booleanValue()) {
                break;
              }
              n = k;
              if (((BackStackRecord)paramArrayList.get(k)).mReorderingAllowed) {
                break;
              }
              k++;
            }
          }
          executeOpsTogether(paramArrayList, paramArrayList1, j, n);
          m = n - 1;
        }
        j = m + 1;
      }
      if (k != i) {
        executeOpsTogether(paramArrayList, paramArrayList1, k, i);
      }
      return;
    }
    throw new IllegalStateException("Internal error with the back stack records");
  }
  
  private void reportBackStackChanged()
  {
    if (this.mBackStackChangeListeners != null) {
      for (int i = 0; i < this.mBackStackChangeListeners.size(); i++) {
        ((OnBackStackChangedListener)this.mBackStackChangeListeners.get(i)).onBackStackChanged();
      }
    }
  }
  
  static int reverseTransit(int paramInt)
  {
    int i = 8194;
    if (paramInt != 4097) {
      if (paramInt != 4099)
      {
        if (paramInt != 8194) {
          i = 0;
        } else {
          i = 4097;
        }
      }
      else {
        i = 4099;
      }
    }
    return i;
  }
  
  private void setVisibleRemovingFragment(Fragment paramFragment)
  {
    ViewGroup localViewGroup = getFragmentContainer(paramFragment);
    if (localViewGroup != null)
    {
      if (localViewGroup.getTag(R.id.visible_removing_fragment_view_tag) == null) {
        localViewGroup.setTag(R.id.visible_removing_fragment_view_tag, paramFragment);
      }
      ((Fragment)localViewGroup.getTag(R.id.visible_removing_fragment_view_tag)).setNextAnim(paramFragment.getNextAnim());
    }
  }
  
  private void startPendingDeferredFragments()
  {
    Iterator localIterator = this.mFragmentStore.getActiveFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if (localFragment != null) {
        performPendingDeferredStart(localFragment);
      }
    }
  }
  
  private void throwException(RuntimeException paramRuntimeException)
  {
    Log.e("FragmentManager", paramRuntimeException.getMessage());
    Log.e("FragmentManager", "Activity state:");
    PrintWriter localPrintWriter = new PrintWriter(new LogWriter("FragmentManager"));
    FragmentHostCallback localFragmentHostCallback = this.mHost;
    if (localFragmentHostCallback != null) {
      try
      {
        localFragmentHostCallback.onDump("  ", null, localPrintWriter, new String[0]);
      }
      catch (Exception localException1)
      {
        Log.e("FragmentManager", "Failed dumping state", localException1);
      }
    } else {
      try
      {
        dump("  ", null, localPrintWriter, new String[0]);
      }
      catch (Exception localException2)
      {
        Log.e("FragmentManager", "Failed dumping state", localException2);
      }
    }
    throw paramRuntimeException;
  }
  
  private void updateOnBackPressedCallbackEnabled()
  {
    synchronized (this.mPendingActions)
    {
      boolean bool1 = this.mPendingActions.isEmpty();
      boolean bool2 = true;
      if (!bool1)
      {
        this.mOnBackPressedCallback.setEnabled(true);
        return;
      }
      OnBackPressedCallback localOnBackPressedCallback = this.mOnBackPressedCallback;
      if ((getBackStackEntryCount() <= 0) || (!isPrimaryNavigation(this.mParent))) {
        bool2 = false;
      }
      localOnBackPressedCallback.setEnabled(bool2);
      return;
    }
  }
  
  void addBackStackState(BackStackRecord paramBackStackRecord)
  {
    if (this.mBackStack == null) {
      this.mBackStack = new ArrayList();
    }
    this.mBackStack.add(paramBackStackRecord);
  }
  
  void addCancellationSignal(Fragment paramFragment, CancellationSignal paramCancellationSignal)
  {
    if (this.mExitAnimationCancellationSignals.get(paramFragment) == null) {
      this.mExitAnimationCancellationSignals.put(paramFragment, new HashSet());
    }
    ((HashSet)this.mExitAnimationCancellationSignals.get(paramFragment)).add(paramCancellationSignal);
  }
  
  void addFragment(Fragment paramFragment)
  {
    if (isLoggingEnabled(2))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("add: ");
      localStringBuilder.append(paramFragment);
      Log.v("FragmentManager", localStringBuilder.toString());
    }
    makeActive(paramFragment);
    if (!paramFragment.mDetached)
    {
      this.mFragmentStore.addFragment(paramFragment);
      paramFragment.mRemoving = false;
      if (paramFragment.mView == null) {
        paramFragment.mHiddenChanged = false;
      }
      if (isMenuAvailable(paramFragment)) {
        this.mNeedMenuInvalidate = true;
      }
    }
  }
  
  public void addOnBackStackChangedListener(OnBackStackChangedListener paramOnBackStackChangedListener)
  {
    if (this.mBackStackChangeListeners == null) {
      this.mBackStackChangeListeners = new ArrayList();
    }
    this.mBackStackChangeListeners.add(paramOnBackStackChangedListener);
  }
  
  void addRetainedFragment(Fragment paramFragment)
  {
    if (isStateSaved())
    {
      if (isLoggingEnabled(2)) {
        Log.v("FragmentManager", "Ignoring addRetainedFragment as the state is already saved");
      }
      return;
    }
    if ((this.mNonConfig.addRetainedFragment(paramFragment)) && (isLoggingEnabled(2)))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Updating retained Fragments: Added ");
      localStringBuilder.append(paramFragment);
      Log.v("FragmentManager", localStringBuilder.toString());
    }
  }
  
  int allocBackStackIndex()
  {
    return this.mBackStackIndex.getAndIncrement();
  }
  
  void attachController(FragmentHostCallback<?> paramFragmentHostCallback, FragmentContainer paramFragmentContainer, Fragment paramFragment)
  {
    if (this.mHost == null)
    {
      this.mHost = paramFragmentHostCallback;
      this.mContainer = paramFragmentContainer;
      this.mParent = paramFragment;
      if (paramFragment != null) {
        updateOnBackPressedCallbackEnabled();
      }
      if ((paramFragmentHostCallback instanceof OnBackPressedDispatcherOwner))
      {
        paramFragmentContainer = (OnBackPressedDispatcherOwner)paramFragmentHostCallback;
        this.mOnBackPressedDispatcher = paramFragmentContainer.getOnBackPressedDispatcher();
        if (paramFragment != null) {
          paramFragmentContainer = paramFragment;
        }
        this.mOnBackPressedDispatcher.addCallback(paramFragmentContainer, this.mOnBackPressedCallback);
      }
      if (paramFragment != null) {
        this.mNonConfig = paramFragment.mFragmentManager.getChildNonConfig(paramFragment);
      } else if ((paramFragmentHostCallback instanceof ViewModelStoreOwner)) {
        this.mNonConfig = FragmentManagerViewModel.getInstance(((ViewModelStoreOwner)paramFragmentHostCallback).getViewModelStore());
      } else {
        this.mNonConfig = new FragmentManagerViewModel(false);
      }
      return;
    }
    throw new IllegalStateException("Already attached");
  }
  
  void attachFragment(Fragment paramFragment)
  {
    StringBuilder localStringBuilder;
    if (isLoggingEnabled(2))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("attach: ");
      localStringBuilder.append(paramFragment);
      Log.v("FragmentManager", localStringBuilder.toString());
    }
    if (paramFragment.mDetached)
    {
      paramFragment.mDetached = false;
      if (!paramFragment.mAdded)
      {
        this.mFragmentStore.addFragment(paramFragment);
        if (isLoggingEnabled(2))
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("add from attach: ");
          localStringBuilder.append(paramFragment);
          Log.v("FragmentManager", localStringBuilder.toString());
        }
        if (isMenuAvailable(paramFragment)) {
          this.mNeedMenuInvalidate = true;
        }
      }
    }
  }
  
  public FragmentTransaction beginTransaction()
  {
    return new BackStackRecord(this);
  }
  
  boolean checkForMenus()
  {
    Iterator localIterator = this.mFragmentStore.getActiveFragments().iterator();
    boolean bool1 = false;
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      boolean bool2 = bool1;
      if (localFragment != null) {
        bool2 = isMenuAvailable(localFragment);
      }
      bool1 = bool2;
      if (bool2) {
        return true;
      }
    }
    return false;
  }
  
  void completeExecute(BackStackRecord paramBackStackRecord, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if (paramBoolean1) {
      paramBackStackRecord.executePopOps(paramBoolean3);
    } else {
      paramBackStackRecord.executeOps();
    }
    Object localObject1 = new ArrayList(1);
    Object localObject2 = new ArrayList(1);
    ((ArrayList)localObject1).add(paramBackStackRecord);
    ((ArrayList)localObject2).add(Boolean.valueOf(paramBoolean1));
    if (paramBoolean2) {
      FragmentTransition.startTransitions(this, (ArrayList)localObject1, (ArrayList)localObject2, 0, 1, true, this.mFragmentTransitionCallback);
    }
    if (paramBoolean3) {
      moveToState(this.mCurState, true);
    }
    localObject1 = this.mFragmentStore.getActiveFragments().iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Fragment)((Iterator)localObject1).next();
      if ((localObject2 != null) && (((Fragment)localObject2).mView != null) && (((Fragment)localObject2).mIsNewlyAdded) && (paramBackStackRecord.interactsWith(((Fragment)localObject2).mContainerId)))
      {
        if (((Fragment)localObject2).mPostponedAlpha > 0.0F) {
          ((Fragment)localObject2).mView.setAlpha(((Fragment)localObject2).mPostponedAlpha);
        }
        if (paramBoolean3)
        {
          ((Fragment)localObject2).mPostponedAlpha = 0.0F;
        }
        else
        {
          ((Fragment)localObject2).mPostponedAlpha = -1.0F;
          ((Fragment)localObject2).mIsNewlyAdded = false;
        }
      }
    }
  }
  
  void detachFragment(Fragment paramFragment)
  {
    StringBuilder localStringBuilder;
    if (isLoggingEnabled(2))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("detach: ");
      localStringBuilder.append(paramFragment);
      Log.v("FragmentManager", localStringBuilder.toString());
    }
    if (!paramFragment.mDetached)
    {
      paramFragment.mDetached = true;
      if (paramFragment.mAdded)
      {
        if (isLoggingEnabled(2))
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("remove from detach: ");
          localStringBuilder.append(paramFragment);
          Log.v("FragmentManager", localStringBuilder.toString());
        }
        this.mFragmentStore.removeFragment(paramFragment);
        if (isMenuAvailable(paramFragment)) {
          this.mNeedMenuInvalidate = true;
        }
        setVisibleRemovingFragment(paramFragment);
      }
    }
  }
  
  void dispatchActivityCreated()
  {
    this.mStateSaved = false;
    this.mStopped = false;
    dispatchStateChange(2);
  }
  
  void dispatchConfigurationChanged(Configuration paramConfiguration)
  {
    Iterator localIterator = this.mFragmentStore.getFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if (localFragment != null) {
        localFragment.performConfigurationChanged(paramConfiguration);
      }
    }
  }
  
  boolean dispatchContextItemSelected(MenuItem paramMenuItem)
  {
    if (this.mCurState < 1) {
      return false;
    }
    Iterator localIterator = this.mFragmentStore.getFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if ((localFragment != null) && (localFragment.performContextItemSelected(paramMenuItem))) {
        return true;
      }
    }
    return false;
  }
  
  void dispatchCreate()
  {
    this.mStateSaved = false;
    this.mStopped = false;
    dispatchStateChange(1);
  }
  
  boolean dispatchCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    int i = this.mCurState;
    int j = 0;
    if (i < 1) {
      return false;
    }
    Object localObject1 = null;
    Iterator localIterator = this.mFragmentStore.getFragments().iterator();
    boolean bool = false;
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if ((localFragment != null) && (localFragment.performCreateOptionsMenu(paramMenu, paramMenuInflater)))
      {
        Object localObject2 = localObject1;
        if (localObject1 == null) {
          localObject2 = new ArrayList();
        }
        ((ArrayList)localObject2).add(localFragment);
        bool = true;
        localObject1 = localObject2;
      }
    }
    if (this.mCreatedMenus != null) {
      while (j < this.mCreatedMenus.size())
      {
        paramMenu = (Fragment)this.mCreatedMenus.get(j);
        if ((localObject1 == null) || (!localObject1.contains(paramMenu))) {
          paramMenu.onDestroyOptionsMenu();
        }
        j++;
      }
    }
    this.mCreatedMenus = localObject1;
    return bool;
  }
  
  void dispatchDestroy()
  {
    this.mDestroyed = true;
    execPendingActions(true);
    endAnimatingAwayFragments();
    dispatchStateChange(-1);
    this.mHost = null;
    this.mContainer = null;
    this.mParent = null;
    if (this.mOnBackPressedDispatcher != null)
    {
      this.mOnBackPressedCallback.remove();
      this.mOnBackPressedDispatcher = null;
    }
  }
  
  void dispatchDestroyView()
  {
    dispatchStateChange(1);
  }
  
  void dispatchLowMemory()
  {
    Iterator localIterator = this.mFragmentStore.getFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if (localFragment != null) {
        localFragment.performLowMemory();
      }
    }
  }
  
  void dispatchMultiWindowModeChanged(boolean paramBoolean)
  {
    Iterator localIterator = this.mFragmentStore.getFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if (localFragment != null) {
        localFragment.performMultiWindowModeChanged(paramBoolean);
      }
    }
  }
  
  boolean dispatchOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (this.mCurState < 1) {
      return false;
    }
    Iterator localIterator = this.mFragmentStore.getFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if ((localFragment != null) && (localFragment.performOptionsItemSelected(paramMenuItem))) {
        return true;
      }
    }
    return false;
  }
  
  void dispatchOptionsMenuClosed(Menu paramMenu)
  {
    if (this.mCurState < 1) {
      return;
    }
    Iterator localIterator = this.mFragmentStore.getFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if (localFragment != null) {
        localFragment.performOptionsMenuClosed(paramMenu);
      }
    }
  }
  
  void dispatchPause()
  {
    dispatchStateChange(3);
  }
  
  void dispatchPictureInPictureModeChanged(boolean paramBoolean)
  {
    Iterator localIterator = this.mFragmentStore.getFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if (localFragment != null) {
        localFragment.performPictureInPictureModeChanged(paramBoolean);
      }
    }
  }
  
  boolean dispatchPrepareOptionsMenu(Menu paramMenu)
  {
    int i = this.mCurState;
    boolean bool = false;
    if (i < 1) {
      return false;
    }
    Iterator localIterator = this.mFragmentStore.getFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if ((localFragment != null) && (localFragment.performPrepareOptionsMenu(paramMenu))) {
        bool = true;
      }
    }
    return bool;
  }
  
  void dispatchPrimaryNavigationFragmentChanged()
  {
    updateOnBackPressedCallbackEnabled();
    dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
  }
  
  void dispatchResume()
  {
    this.mStateSaved = false;
    this.mStopped = false;
    dispatchStateChange(4);
  }
  
  void dispatchStart()
  {
    this.mStateSaved = false;
    this.mStopped = false;
    dispatchStateChange(3);
  }
  
  void dispatchStop()
  {
    this.mStopped = true;
    dispatchStateChange(2);
  }
  
  public void dump(String paramString, FileDescriptor arg2, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append("    ");
    localObject = ((StringBuilder)localObject).toString();
    this.mFragmentStore.dump(paramString, ???, paramPrintWriter, paramArrayOfString);
    ??? = this.mCreatedMenus;
    int i = 0;
    int j;
    int k;
    if (??? != null)
    {
      j = ???.size();
      if (j > 0)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.println("Fragments Created Menus:");
        for (k = 0; k < j; k++)
        {
          ??? = (Fragment)this.mCreatedMenus.get(k);
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("  #");
          paramPrintWriter.print(k);
          paramPrintWriter.print(": ");
          paramPrintWriter.println(???.toString());
        }
      }
    }
    ??? = this.mBackStack;
    if (??? != null)
    {
      j = ???.size();
      if (j > 0)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.println("Back Stack:");
        for (k = 0; k < j; k++)
        {
          ??? = (BackStackRecord)this.mBackStack.get(k);
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("  #");
          paramPrintWriter.print(k);
          paramPrintWriter.print(": ");
          paramPrintWriter.println(???.toString());
          ???.dump((String)localObject, paramPrintWriter);
        }
      }
    }
    paramPrintWriter.print(paramString);
    ??? = new StringBuilder();
    ???.append("Back Stack Index: ");
    ???.append(this.mBackStackIndex.get());
    paramPrintWriter.println(???.toString());
    synchronized (this.mPendingActions)
    {
      j = this.mPendingActions.size();
      if (j > 0)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.println("Pending Actions:");
        for (k = i; k < j; k++)
        {
          paramArrayOfString = (OpGenerator)this.mPendingActions.get(k);
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("  #");
          paramPrintWriter.print(k);
          paramPrintWriter.print(": ");
          paramPrintWriter.println(paramArrayOfString);
        }
      }
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("FragmentManager misc state:");
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("  mHost=");
      paramPrintWriter.println(this.mHost);
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("  mContainer=");
      paramPrintWriter.println(this.mContainer);
      if (this.mParent != null)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  mParent=");
        paramPrintWriter.println(this.mParent);
      }
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("  mCurState=");
      paramPrintWriter.print(this.mCurState);
      paramPrintWriter.print(" mStateSaved=");
      paramPrintWriter.print(this.mStateSaved);
      paramPrintWriter.print(" mStopped=");
      paramPrintWriter.print(this.mStopped);
      paramPrintWriter.print(" mDestroyed=");
      paramPrintWriter.println(this.mDestroyed);
      if (this.mNeedMenuInvalidate)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  mNeedMenuInvalidate=");
        paramPrintWriter.println(this.mNeedMenuInvalidate);
      }
      return;
    }
  }
  
  void enqueueAction(OpGenerator paramOpGenerator, boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      if (this.mHost == null)
      {
        if (this.mDestroyed) {
          throw new IllegalStateException("FragmentManager has been destroyed");
        }
        throw new IllegalStateException("FragmentManager has not been attached to a host.");
      }
      checkStateLoss();
    }
    synchronized (this.mPendingActions)
    {
      if (this.mHost == null)
      {
        if (paramBoolean) {
          return;
        }
        paramOpGenerator = new java/lang/IllegalStateException;
        paramOpGenerator.<init>("Activity has been destroyed");
        throw paramOpGenerator;
      }
      this.mPendingActions.add(paramOpGenerator);
      scheduleCommit();
      return;
    }
  }
  
  boolean execPendingActions(boolean paramBoolean)
  {
    ensureExecReady(paramBoolean);
    paramBoolean = false;
    for (;;)
    {
      if (generateOpsForPendingActions(this.mTmpRecords, this.mTmpIsPop)) {
        this.mExecutingActions = true;
      }
      try
      {
        removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
        cleanupExec();
        paramBoolean = true;
      }
      finally
      {
        cleanupExec();
      }
    }
    doPendingDeferredStart();
    this.mFragmentStore.burpActive();
    return paramBoolean;
  }
  
  void execSingleAction(OpGenerator paramOpGenerator, boolean paramBoolean)
  {
    if ((paramBoolean) && ((this.mHost == null) || (this.mDestroyed))) {
      return;
    }
    ensureExecReady(paramBoolean);
    if (paramOpGenerator.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
      this.mExecutingActions = true;
    }
    try
    {
      removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
      cleanupExec();
    }
    finally
    {
      cleanupExec();
    }
    doPendingDeferredStart();
    this.mFragmentStore.burpActive();
  }
  
  public boolean executePendingTransactions()
  {
    boolean bool = execPendingActions(true);
    forcePostponedTransactions();
    return bool;
  }
  
  Fragment findActiveFragment(String paramString)
  {
    return this.mFragmentStore.findActiveFragment(paramString);
  }
  
  public Fragment findFragmentById(int paramInt)
  {
    return this.mFragmentStore.findFragmentById(paramInt);
  }
  
  public Fragment findFragmentByTag(String paramString)
  {
    return this.mFragmentStore.findFragmentByTag(paramString);
  }
  
  Fragment findFragmentByWho(String paramString)
  {
    return this.mFragmentStore.findFragmentByWho(paramString);
  }
  
  int getActiveFragmentCount()
  {
    return this.mFragmentStore.getActiveFragmentCount();
  }
  
  List<Fragment> getActiveFragments()
  {
    return this.mFragmentStore.getActiveFragments();
  }
  
  public BackStackEntry getBackStackEntryAt(int paramInt)
  {
    return (BackStackEntry)this.mBackStack.get(paramInt);
  }
  
  public int getBackStackEntryCount()
  {
    ArrayList localArrayList = this.mBackStack;
    int i;
    if (localArrayList != null) {
      i = localArrayList.size();
    } else {
      i = 0;
    }
    return i;
  }
  
  public Fragment getFragment(Bundle paramBundle, String paramString)
  {
    String str = paramBundle.getString(paramString);
    if (str == null) {
      return null;
    }
    Fragment localFragment = findActiveFragment(str);
    if (localFragment == null)
    {
      paramBundle = new StringBuilder();
      paramBundle.append("Fragment no longer exists for key ");
      paramBundle.append(paramString);
      paramBundle.append(": unique id ");
      paramBundle.append(str);
      throwException(new IllegalStateException(paramBundle.toString()));
    }
    return localFragment;
  }
  
  public FragmentFactory getFragmentFactory()
  {
    Object localObject = this.mFragmentFactory;
    if (localObject != null) {
      return localObject;
    }
    localObject = this.mParent;
    if (localObject != null) {
      return ((Fragment)localObject).mFragmentManager.getFragmentFactory();
    }
    return this.mHostFragmentFactory;
  }
  
  public List<Fragment> getFragments()
  {
    return this.mFragmentStore.getFragments();
  }
  
  LayoutInflater.Factory2 getLayoutInflaterFactory()
  {
    return this.mLayoutInflaterFactory;
  }
  
  FragmentLifecycleCallbacksDispatcher getLifecycleCallbacksDispatcher()
  {
    return this.mLifecycleCallbacksDispatcher;
  }
  
  Fragment getParent()
  {
    return this.mParent;
  }
  
  public Fragment getPrimaryNavigationFragment()
  {
    return this.mPrimaryNav;
  }
  
  ViewModelStore getViewModelStore(Fragment paramFragment)
  {
    return this.mNonConfig.getViewModelStore(paramFragment);
  }
  
  void handleOnBackPressed()
  {
    execPendingActions(true);
    if (this.mOnBackPressedCallback.isEnabled()) {
      popBackStackImmediate();
    } else {
      this.mOnBackPressedDispatcher.onBackPressed();
    }
  }
  
  void hideFragment(Fragment paramFragment)
  {
    if (isLoggingEnabled(2))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("hide: ");
      localStringBuilder.append(paramFragment);
      Log.v("FragmentManager", localStringBuilder.toString());
    }
    if (!paramFragment.mHidden)
    {
      paramFragment.mHidden = true;
      paramFragment.mHiddenChanged = (true ^ paramFragment.mHiddenChanged);
      setVisibleRemovingFragment(paramFragment);
    }
  }
  
  public boolean isDestroyed()
  {
    return this.mDestroyed;
  }
  
  boolean isPrimaryNavigation(Fragment paramFragment)
  {
    boolean bool = true;
    if (paramFragment == null) {
      return true;
    }
    FragmentManager localFragmentManager = paramFragment.mFragmentManager;
    if ((!paramFragment.equals(localFragmentManager.getPrimaryNavigationFragment())) || (!isPrimaryNavigation(localFragmentManager.mParent))) {
      bool = false;
    }
    return bool;
  }
  
  boolean isStateAtLeast(int paramInt)
  {
    boolean bool;
    if (this.mCurState >= paramInt) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isStateSaved()
  {
    boolean bool;
    if ((!this.mStateSaved) && (!this.mStopped)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  void makeActive(Fragment paramFragment)
  {
    if (this.mFragmentStore.containsActiveFragment(paramFragment.mWho)) {
      return;
    }
    Object localObject = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, paramFragment);
    ((FragmentStateManager)localObject).restoreState(this.mHost.getContext().getClassLoader());
    this.mFragmentStore.makeActive((FragmentStateManager)localObject);
    if (paramFragment.mRetainInstanceChangedWhileDetached)
    {
      if (paramFragment.mRetainInstance) {
        addRetainedFragment(paramFragment);
      } else {
        removeRetainedFragment(paramFragment);
      }
      paramFragment.mRetainInstanceChangedWhileDetached = false;
    }
    ((FragmentStateManager)localObject).setFragmentManagerState(this.mCurState);
    if (isLoggingEnabled(2))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Added fragment to active set ");
      ((StringBuilder)localObject).append(paramFragment);
      Log.v("FragmentManager", ((StringBuilder)localObject).toString());
    }
  }
  
  void moveFragmentToExpectedState(Fragment paramFragment)
  {
    Object localObject;
    if (!this.mFragmentStore.containsActiveFragment(paramFragment.mWho))
    {
      if (isLoggingEnabled(3))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Ignoring moving ");
        ((StringBuilder)localObject).append(paramFragment);
        ((StringBuilder)localObject).append(" to state ");
        ((StringBuilder)localObject).append(this.mCurState);
        ((StringBuilder)localObject).append("since it is not added to ");
        ((StringBuilder)localObject).append(this);
        Log.d("FragmentManager", ((StringBuilder)localObject).toString());
      }
      return;
    }
    moveToState(paramFragment);
    if (paramFragment.mView != null)
    {
      localObject = this.mFragmentStore.findFragmentUnder(paramFragment);
      if (localObject != null)
      {
        localObject = ((Fragment)localObject).mView;
        ViewGroup localViewGroup = paramFragment.mContainer;
        int i = localViewGroup.indexOfChild((View)localObject);
        int j = localViewGroup.indexOfChild(paramFragment.mView);
        if (j < i)
        {
          localViewGroup.removeViewAt(j);
          localViewGroup.addView(paramFragment.mView, i);
        }
      }
      if ((paramFragment.mIsNewlyAdded) && (paramFragment.mContainer != null))
      {
        if (paramFragment.mPostponedAlpha > 0.0F) {
          paramFragment.mView.setAlpha(paramFragment.mPostponedAlpha);
        }
        paramFragment.mPostponedAlpha = 0.0F;
        paramFragment.mIsNewlyAdded = false;
        localObject = FragmentAnim.loadAnimation(this.mHost.getContext(), this.mContainer, paramFragment, true);
        if (localObject != null) {
          if (((FragmentAnim.AnimationOrAnimator)localObject).animation != null)
          {
            paramFragment.mView.startAnimation(((FragmentAnim.AnimationOrAnimator)localObject).animation);
          }
          else
          {
            ((FragmentAnim.AnimationOrAnimator)localObject).animator.setTarget(paramFragment.mView);
            ((FragmentAnim.AnimationOrAnimator)localObject).animator.start();
          }
        }
      }
    }
    if (paramFragment.mHiddenChanged) {
      completeShowHideFragment(paramFragment);
    }
  }
  
  void moveToState(int paramInt, boolean paramBoolean)
  {
    if ((this.mHost == null) && (paramInt != -1)) {
      throw new IllegalStateException("No activity");
    }
    if ((!paramBoolean) && (paramInt == this.mCurState)) {
      return;
    }
    this.mCurState = paramInt;
    Object localObject = this.mFragmentStore.getFragments().iterator();
    while (((Iterator)localObject).hasNext()) {
      moveFragmentToExpectedState((Fragment)((Iterator)localObject).next());
    }
    Iterator localIterator = this.mFragmentStore.getActiveFragments().iterator();
    while (localIterator.hasNext())
    {
      localObject = (Fragment)localIterator.next();
      if ((localObject != null) && (!((Fragment)localObject).mIsNewlyAdded)) {
        moveFragmentToExpectedState((Fragment)localObject);
      }
    }
    startPendingDeferredFragments();
    if (this.mNeedMenuInvalidate)
    {
      localObject = this.mHost;
      if ((localObject != null) && (this.mCurState == 4))
      {
        ((FragmentHostCallback)localObject).onSupportInvalidateOptionsMenu();
        this.mNeedMenuInvalidate = false;
      }
    }
  }
  
  void moveToState(Fragment paramFragment)
  {
    moveToState(paramFragment, this.mCurState);
  }
  
  void moveToState(Fragment paramFragment, int paramInt)
  {
    Object localObject1 = this.mFragmentStore.getFragmentStateManager(paramFragment.mWho);
    int i = 1;
    Object localObject2 = localObject1;
    if (localObject1 == null)
    {
      localObject2 = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, paramFragment);
      ((FragmentStateManager)localObject2).setFragmentManagerState(1);
    }
    paramInt = Math.min(paramInt, ((FragmentStateManager)localObject2).computeMaxState());
    int j = paramFragment.mState;
    ViewGroup localViewGroup = null;
    if (j <= paramInt)
    {
      if ((paramFragment.mState < paramInt) && (!this.mExitAnimationCancellationSignals.isEmpty())) {
        cancelExitAnimation(paramFragment);
      }
      j = paramFragment.mState;
      if (j != -1)
      {
        if (j != 0)
        {
          if (j == 1) {
            break label434;
          }
          if (j == 2) {
            break label468;
          }
          if (j == 3) {
            break label478;
          }
          j = paramInt;
          break label965;
        }
      }
      else if (paramInt > -1)
      {
        if (isLoggingEnabled(3))
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("moveto ATTACHED: ");
          ((StringBuilder)localObject1).append(paramFragment);
          Log.d("FragmentManager", ((StringBuilder)localObject1).toString());
        }
        if (paramFragment.mTarget != null) {
          if (paramFragment.mTarget.equals(findActiveFragment(paramFragment.mTarget.mWho)))
          {
            if (paramFragment.mTarget.mState < 1) {
              moveToState(paramFragment.mTarget, 1);
            }
            paramFragment.mTargetWho = paramFragment.mTarget.mWho;
            paramFragment.mTarget = null;
          }
          else
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("Fragment ");
            ((StringBuilder)localObject2).append(paramFragment);
            ((StringBuilder)localObject2).append(" declared target fragment ");
            ((StringBuilder)localObject2).append(paramFragment.mTarget);
            ((StringBuilder)localObject2).append(" that does not belong to this FragmentManager!");
            throw new IllegalStateException(((StringBuilder)localObject2).toString());
          }
        }
        if (paramFragment.mTargetWho != null)
        {
          localObject1 = findActiveFragment(paramFragment.mTargetWho);
          if (localObject1 != null)
          {
            if (((Fragment)localObject1).mState < 1) {
              moveToState((Fragment)localObject1, 1);
            }
          }
          else
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("Fragment ");
            ((StringBuilder)localObject2).append(paramFragment);
            ((StringBuilder)localObject2).append(" declared target fragment ");
            ((StringBuilder)localObject2).append(paramFragment.mTargetWho);
            ((StringBuilder)localObject2).append(" that does not belong to this FragmentManager!");
            throw new IllegalStateException(((StringBuilder)localObject2).toString());
          }
        }
        ((FragmentStateManager)localObject2).attach(this.mHost, this, this.mParent);
      }
      if (paramInt > 0) {
        ((FragmentStateManager)localObject2).create();
      }
      label434:
      if (paramInt > -1) {
        ((FragmentStateManager)localObject2).ensureInflatedView();
      }
      if (paramInt > 1)
      {
        ((FragmentStateManager)localObject2).createView(this.mContainer);
        ((FragmentStateManager)localObject2).activityCreated();
        ((FragmentStateManager)localObject2).restoreViewState();
      }
      label468:
      if (paramInt > 2) {
        ((FragmentStateManager)localObject2).start();
      }
      label478:
      j = paramInt;
      if (paramInt > 3)
      {
        ((FragmentStateManager)localObject2).resume();
        j = paramInt;
      }
    }
    else
    {
      j = paramInt;
      if (paramFragment.mState > paramInt)
      {
        j = paramFragment.mState;
        if (j != 0)
        {
          int k = 0;
          if (j != 1)
          {
            if (j != 2)
            {
              if (j != 3)
              {
                if (j != 4)
                {
                  j = paramInt;
                  break label965;
                }
                if (paramInt < 4) {
                  ((FragmentStateManager)localObject2).pause();
                }
              }
              if (paramInt < 3) {
                ((FragmentStateManager)localObject2).stop();
              }
            }
            if (paramInt < 2)
            {
              if (isLoggingEnabled(3))
              {
                localObject1 = new StringBuilder();
                ((StringBuilder)localObject1).append("movefrom ACTIVITY_CREATED: ");
                ((StringBuilder)localObject1).append(paramFragment);
                Log.d("FragmentManager", ((StringBuilder)localObject1).toString());
              }
              if ((paramFragment.mView != null) && (this.mHost.onShouldSaveFragmentState(paramFragment)) && (paramFragment.mSavedViewState == null)) {
                ((FragmentStateManager)localObject2).saveViewState();
              }
              if ((paramFragment.mView != null) && (paramFragment.mContainer != null))
              {
                paramFragment.mContainer.endViewTransition(paramFragment.mView);
                paramFragment.mView.clearAnimation();
                if (!paramFragment.isRemovingParent())
                {
                  localObject1 = localViewGroup;
                  if (this.mCurState > -1)
                  {
                    localObject1 = localViewGroup;
                    if (!this.mDestroyed)
                    {
                      localObject1 = localViewGroup;
                      if (paramFragment.mView.getVisibility() == 0)
                      {
                        localObject1 = localViewGroup;
                        if (paramFragment.mPostponedAlpha >= 0.0F) {
                          localObject1 = FragmentAnim.loadAnimation(this.mHost.getContext(), this.mContainer, paramFragment, false);
                        }
                      }
                    }
                  }
                  paramFragment.mPostponedAlpha = 0.0F;
                  localViewGroup = paramFragment.mContainer;
                  View localView = paramFragment.mView;
                  if (localObject1 != null)
                  {
                    paramFragment.setStateAfterAnimating(paramInt);
                    FragmentAnim.animateRemoveFragment(paramFragment, (FragmentAnim.AnimationOrAnimator)localObject1, this.mFragmentTransitionCallback);
                  }
                  localViewGroup.removeView(localView);
                  if (localViewGroup != paramFragment.mContainer) {
                    return;
                  }
                }
              }
              if (this.mExitAnimationCancellationSignals.get(paramFragment) == null) {
                destroyFragmentView(paramFragment);
              } else {
                paramFragment.setStateAfterAnimating(paramInt);
              }
            }
          }
          if (paramInt < 1)
          {
            j = k;
            if (paramFragment.mRemoving)
            {
              j = k;
              if (!paramFragment.isInBackStack()) {
                j = 1;
              }
            }
            if ((j == 0) && (!this.mNonConfig.shouldDestroy(paramFragment)))
            {
              if (paramFragment.mTargetWho != null)
              {
                localObject1 = findActiveFragment(paramFragment.mTargetWho);
                if ((localObject1 != null) && (((Fragment)localObject1).getRetainInstance())) {
                  paramFragment.mTarget = ((Fragment)localObject1);
                }
              }
            }
            else {
              makeInactive((FragmentStateManager)localObject2);
            }
            if (this.mExitAnimationCancellationSignals.get(paramFragment) != null)
            {
              paramFragment.setStateAfterAnimating(paramInt);
              paramInt = i;
            }
            else
            {
              ((FragmentStateManager)localObject2).destroy(this.mHost, this.mNonConfig);
            }
          }
        }
        if (paramInt < 0) {
          ((FragmentStateManager)localObject2).detach(this.mNonConfig);
        }
        j = paramInt;
      }
    }
    label965:
    if (paramFragment.mState != j)
    {
      if (isLoggingEnabled(3))
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("moveToState: Fragment state for ");
        ((StringBuilder)localObject2).append(paramFragment);
        ((StringBuilder)localObject2).append(" not updated inline; expected state ");
        ((StringBuilder)localObject2).append(j);
        ((StringBuilder)localObject2).append(" found ");
        ((StringBuilder)localObject2).append(paramFragment.mState);
        Log.d("FragmentManager", ((StringBuilder)localObject2).toString());
      }
      paramFragment.mState = j;
    }
  }
  
  void noteStateNotSaved()
  {
    this.mStateSaved = false;
    this.mStopped = false;
    Iterator localIterator = this.mFragmentStore.getFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if (localFragment != null) {
        localFragment.noteStateNotSaved();
      }
    }
  }
  
  @Deprecated
  public FragmentTransaction openTransaction()
  {
    return beginTransaction();
  }
  
  void performPendingDeferredStart(Fragment paramFragment)
  {
    if (paramFragment.mDeferStart)
    {
      if (this.mExecutingActions)
      {
        this.mHavePendingDeferredStart = true;
        return;
      }
      paramFragment.mDeferStart = false;
      moveToState(paramFragment, this.mCurState);
    }
  }
  
  public void popBackStack()
  {
    enqueueAction(new PopBackStackState(null, -1, 0), false);
  }
  
  public void popBackStack(int paramInt1, int paramInt2)
  {
    if (paramInt1 >= 0)
    {
      enqueueAction(new PopBackStackState(null, paramInt1, paramInt2), false);
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Bad id: ");
    localStringBuilder.append(paramInt1);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public void popBackStack(String paramString, int paramInt)
  {
    enqueueAction(new PopBackStackState(paramString, -1, paramInt), false);
  }
  
  public boolean popBackStackImmediate()
  {
    return popBackStackImmediate(null, -1, 0);
  }
  
  public boolean popBackStackImmediate(int paramInt1, int paramInt2)
  {
    if (paramInt1 >= 0) {
      return popBackStackImmediate(null, paramInt1, paramInt2);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Bad id: ");
    localStringBuilder.append(paramInt1);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public boolean popBackStackImmediate(String paramString, int paramInt)
  {
    return popBackStackImmediate(paramString, -1, paramInt);
  }
  
  boolean popBackStackState(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, String paramString, int paramInt1, int paramInt2)
  {
    Object localObject = this.mBackStack;
    if (localObject == null) {
      return false;
    }
    if ((paramString == null) && (paramInt1 < 0) && ((paramInt2 & 0x1) == 0))
    {
      paramInt1 = ((ArrayList)localObject).size() - 1;
      if (paramInt1 < 0) {
        return false;
      }
      paramArrayList.add(this.mBackStack.remove(paramInt1));
      paramArrayList1.add(Boolean.valueOf(true));
    }
    else
    {
      if ((paramString == null) && (paramInt1 < 0))
      {
        paramInt1 = -1;
      }
      else
      {
        for (int i = this.mBackStack.size() - 1; i >= 0; i--)
        {
          localObject = (BackStackRecord)this.mBackStack.get(i);
          if (((paramString != null) && (paramString.equals(((BackStackRecord)localObject).getName()))) || ((paramInt1 >= 0) && (paramInt1 == ((BackStackRecord)localObject).mIndex))) {
            break;
          }
        }
        if (i < 0) {
          return false;
        }
        int j = i;
        if ((paramInt2 & 0x1) != 0) {
          for (;;)
          {
            paramInt2 = i - 1;
            j = paramInt2;
            if (paramInt2 < 0) {
              break;
            }
            localObject = (BackStackRecord)this.mBackStack.get(paramInt2);
            if (paramString != null)
            {
              i = paramInt2;
              if (paramString.equals(((BackStackRecord)localObject).getName())) {}
            }
            else
            {
              j = paramInt2;
              if (paramInt1 < 0) {
                break;
              }
              j = paramInt2;
              if (paramInt1 != ((BackStackRecord)localObject).mIndex) {
                break;
              }
              i = paramInt2;
            }
          }
        }
        paramInt1 = j;
      }
      if (paramInt1 == this.mBackStack.size() - 1) {
        return false;
      }
      for (paramInt2 = this.mBackStack.size() - 1; paramInt2 > paramInt1; paramInt2--)
      {
        paramArrayList.add(this.mBackStack.remove(paramInt2));
        paramArrayList1.add(Boolean.valueOf(true));
      }
    }
    return true;
  }
  
  public void putFragment(Bundle paramBundle, String paramString, Fragment paramFragment)
  {
    if (paramFragment.mFragmentManager != this)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Fragment ");
      localStringBuilder.append(paramFragment);
      localStringBuilder.append(" is not currently in the FragmentManager");
      throwException(new IllegalStateException(localStringBuilder.toString()));
    }
    paramBundle.putString(paramString, paramFragment.mWho);
  }
  
  public void registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacks paramFragmentLifecycleCallbacks, boolean paramBoolean)
  {
    this.mLifecycleCallbacksDispatcher.registerFragmentLifecycleCallbacks(paramFragmentLifecycleCallbacks, paramBoolean);
  }
  
  void removeCancellationSignal(Fragment paramFragment, CancellationSignal paramCancellationSignal)
  {
    HashSet localHashSet = (HashSet)this.mExitAnimationCancellationSignals.get(paramFragment);
    if ((localHashSet != null) && (localHashSet.remove(paramCancellationSignal)) && (localHashSet.isEmpty()))
    {
      this.mExitAnimationCancellationSignals.remove(paramFragment);
      if (paramFragment.mState < 3)
      {
        destroyFragmentView(paramFragment);
        moveToState(paramFragment, paramFragment.getStateAfterAnimating());
      }
    }
  }
  
  void removeFragment(Fragment paramFragment)
  {
    if (isLoggingEnabled(2))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("remove: ");
      localStringBuilder.append(paramFragment);
      localStringBuilder.append(" nesting=");
      localStringBuilder.append(paramFragment.mBackStackNesting);
      Log.v("FragmentManager", localStringBuilder.toString());
    }
    boolean bool = paramFragment.isInBackStack();
    if ((!paramFragment.mDetached) || ((bool ^ true)))
    {
      this.mFragmentStore.removeFragment(paramFragment);
      if (isMenuAvailable(paramFragment)) {
        this.mNeedMenuInvalidate = true;
      }
      paramFragment.mRemoving = true;
      setVisibleRemovingFragment(paramFragment);
    }
  }
  
  public void removeOnBackStackChangedListener(OnBackStackChangedListener paramOnBackStackChangedListener)
  {
    ArrayList localArrayList = this.mBackStackChangeListeners;
    if (localArrayList != null) {
      localArrayList.remove(paramOnBackStackChangedListener);
    }
  }
  
  void removeRetainedFragment(Fragment paramFragment)
  {
    if (isStateSaved())
    {
      if (isLoggingEnabled(2)) {
        Log.v("FragmentManager", "Ignoring removeRetainedFragment as the state is already saved");
      }
      return;
    }
    if ((this.mNonConfig.removeRetainedFragment(paramFragment)) && (isLoggingEnabled(2)))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Updating retained Fragments: Removed ");
      localStringBuilder.append(paramFragment);
      Log.v("FragmentManager", localStringBuilder.toString());
    }
  }
  
  void restoreAllState(Parcelable paramParcelable, FragmentManagerNonConfig paramFragmentManagerNonConfig)
  {
    if ((this.mHost instanceof ViewModelStoreOwner)) {
      throwException(new IllegalStateException("You must use restoreSaveState when your FragmentHostCallback implements ViewModelStoreOwner"));
    }
    this.mNonConfig.restoreFromSnapshot(paramFragmentManagerNonConfig);
    restoreSaveState(paramParcelable);
  }
  
  void restoreSaveState(Parcelable paramParcelable)
  {
    if (paramParcelable == null) {
      return;
    }
    FragmentManagerState localFragmentManagerState = (FragmentManagerState)paramParcelable;
    if (localFragmentManagerState.mActive == null) {
      return;
    }
    this.mFragmentStore.resetActiveFragments();
    Object localObject1 = localFragmentManagerState.mActive.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      Object localObject2 = (FragmentState)((Iterator)localObject1).next();
      if (localObject2 != null)
      {
        localObject3 = this.mNonConfig.findRetainedFragmentByWho(((FragmentState)localObject2).mWho);
        if (localObject3 != null)
        {
          if (isLoggingEnabled(2))
          {
            paramParcelable = new StringBuilder();
            paramParcelable.append("restoreSaveState: re-attaching retained ");
            paramParcelable.append(localObject3);
            Log.v("FragmentManager", paramParcelable.toString());
          }
          paramParcelable = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, (Fragment)localObject3, (FragmentState)localObject2);
        }
        else
        {
          paramParcelable = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mHost.getContext().getClassLoader(), getFragmentFactory(), (FragmentState)localObject2);
        }
        localObject2 = paramParcelable.getFragment();
        ((Fragment)localObject2).mFragmentManager = this;
        if (isLoggingEnabled(2))
        {
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append("restoreSaveState: active (");
          ((StringBuilder)localObject3).append(((Fragment)localObject2).mWho);
          ((StringBuilder)localObject3).append("): ");
          ((StringBuilder)localObject3).append(localObject2);
          Log.v("FragmentManager", ((StringBuilder)localObject3).toString());
        }
        paramParcelable.restoreState(this.mHost.getContext().getClassLoader());
        this.mFragmentStore.makeActive(paramParcelable);
        paramParcelable.setFragmentManagerState(this.mCurState);
      }
    }
    Object localObject3 = this.mNonConfig.getRetainedFragments().iterator();
    while (((Iterator)localObject3).hasNext())
    {
      localObject1 = (Fragment)((Iterator)localObject3).next();
      if (!this.mFragmentStore.containsActiveFragment(((Fragment)localObject1).mWho))
      {
        if (isLoggingEnabled(2))
        {
          paramParcelable = new StringBuilder();
          paramParcelable.append("Discarding retained Fragment ");
          paramParcelable.append(localObject1);
          paramParcelable.append(" that was not found in the set of active Fragments ");
          paramParcelable.append(localFragmentManagerState.mActive);
          Log.v("FragmentManager", paramParcelable.toString());
        }
        moveToState((Fragment)localObject1, 1);
        ((Fragment)localObject1).mRemoving = true;
        moveToState((Fragment)localObject1, -1);
      }
    }
    this.mFragmentStore.restoreAddedFragments(localFragmentManagerState.mAdded);
    if (localFragmentManagerState.mBackStack != null)
    {
      this.mBackStack = new ArrayList(localFragmentManagerState.mBackStack.length);
      for (int i = 0; i < localFragmentManagerState.mBackStack.length; i++)
      {
        paramParcelable = localFragmentManagerState.mBackStack[i].instantiate(this);
        if (isLoggingEnabled(2))
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("restoreAllState: back stack #");
          ((StringBuilder)localObject1).append(i);
          ((StringBuilder)localObject1).append(" (index ");
          ((StringBuilder)localObject1).append(paramParcelable.mIndex);
          ((StringBuilder)localObject1).append("): ");
          ((StringBuilder)localObject1).append(paramParcelable);
          Log.v("FragmentManager", ((StringBuilder)localObject1).toString());
          localObject1 = new PrintWriter(new LogWriter("FragmentManager"));
          paramParcelable.dump("  ", (PrintWriter)localObject1, false);
          ((PrintWriter)localObject1).close();
        }
        this.mBackStack.add(paramParcelable);
      }
    }
    this.mBackStack = null;
    this.mBackStackIndex.set(localFragmentManagerState.mBackStackIndex);
    if (localFragmentManagerState.mPrimaryNavActiveWho != null)
    {
      paramParcelable = findActiveFragment(localFragmentManagerState.mPrimaryNavActiveWho);
      this.mPrimaryNav = paramParcelable;
      dispatchParentPrimaryNavigationFragmentChanged(paramParcelable);
    }
  }
  
  @Deprecated
  FragmentManagerNonConfig retainNonConfig()
  {
    if ((this.mHost instanceof ViewModelStoreOwner)) {
      throwException(new IllegalStateException("You cannot use retainNonConfig when your FragmentHostCallback implements ViewModelStoreOwner."));
    }
    return this.mNonConfig.getSnapshot();
  }
  
  Parcelable saveAllState()
  {
    forcePostponedTransactions();
    endAnimatingAwayFragments();
    execPendingActions(true);
    this.mStateSaved = true;
    ArrayList localArrayList1 = this.mFragmentStore.saveActiveFragments();
    boolean bool = localArrayList1.isEmpty();
    Object localObject1 = null;
    if (bool)
    {
      if (isLoggingEnabled(2)) {
        Log.v("FragmentManager", "saveAllState: no fragments!");
      }
      return null;
    }
    ArrayList localArrayList2 = this.mFragmentStore.saveAddedFragments();
    ArrayList localArrayList3 = this.mBackStack;
    Object localObject2 = localObject1;
    if (localArrayList3 != null)
    {
      int i = localArrayList3.size();
      localObject2 = localObject1;
      if (i > 0)
      {
        localObject1 = new BackStackState[i];
        for (int j = 0;; j++)
        {
          localObject2 = localObject1;
          if (j >= i) {
            break;
          }
          localObject1[j] = new BackStackState((BackStackRecord)this.mBackStack.get(j));
          if (isLoggingEnabled(2))
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("saveAllState: adding back stack #");
            ((StringBuilder)localObject2).append(j);
            ((StringBuilder)localObject2).append(": ");
            ((StringBuilder)localObject2).append(this.mBackStack.get(j));
            Log.v("FragmentManager", ((StringBuilder)localObject2).toString());
          }
        }
      }
    }
    localObject1 = new FragmentManagerState();
    ((FragmentManagerState)localObject1).mActive = localArrayList1;
    ((FragmentManagerState)localObject1).mAdded = localArrayList2;
    ((FragmentManagerState)localObject1).mBackStack = ((BackStackState[])localObject2);
    ((FragmentManagerState)localObject1).mBackStackIndex = this.mBackStackIndex.get();
    localObject2 = this.mPrimaryNav;
    if (localObject2 != null) {
      ((FragmentManagerState)localObject1).mPrimaryNavActiveWho = ((Fragment)localObject2).mWho;
    }
    return localObject1;
  }
  
  public Fragment.SavedState saveFragmentInstanceState(Fragment paramFragment)
  {
    FragmentStateManager localFragmentStateManager = this.mFragmentStore.getFragmentStateManager(paramFragment.mWho);
    if ((localFragmentStateManager == null) || (!localFragmentStateManager.getFragment().equals(paramFragment)))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Fragment ");
      localStringBuilder.append(paramFragment);
      localStringBuilder.append(" is not currently in the FragmentManager");
      throwException(new IllegalStateException(localStringBuilder.toString()));
    }
    return localFragmentStateManager.saveInstanceState();
  }
  
  void scheduleCommit()
  {
    synchronized (this.mPendingActions)
    {
      ArrayList localArrayList2 = this.mPostponedTransactions;
      int i = 0;
      int j;
      if ((localArrayList2 != null) && (!this.mPostponedTransactions.isEmpty())) {
        j = 1;
      } else {
        j = 0;
      }
      if (this.mPendingActions.size() == 1) {
        i = 1;
      }
      if ((j != 0) || (i != 0))
      {
        this.mHost.getHandler().removeCallbacks(this.mExecCommit);
        this.mHost.getHandler().post(this.mExecCommit);
        updateOnBackPressedCallbackEnabled();
      }
      return;
    }
  }
  
  void setExitAnimationOrder(Fragment paramFragment, boolean paramBoolean)
  {
    paramFragment = getFragmentContainer(paramFragment);
    if ((paramFragment != null) && ((paramFragment instanceof FragmentContainerView))) {
      ((FragmentContainerView)paramFragment).setDrawDisappearingViewsLast(paramBoolean ^ true);
    }
  }
  
  public void setFragmentFactory(FragmentFactory paramFragmentFactory)
  {
    this.mFragmentFactory = paramFragmentFactory;
  }
  
  void setMaxLifecycle(Fragment paramFragment, Lifecycle.State paramState)
  {
    if ((paramFragment.equals(findActiveFragment(paramFragment.mWho))) && ((paramFragment.mHost == null) || (paramFragment.mFragmentManager == this)))
    {
      paramFragment.mMaxState = paramState;
      return;
    }
    paramState = new StringBuilder();
    paramState.append("Fragment ");
    paramState.append(paramFragment);
    paramState.append(" is not an active fragment of FragmentManager ");
    paramState.append(this);
    throw new IllegalArgumentException(paramState.toString());
  }
  
  void setPrimaryNavigationFragment(Fragment paramFragment)
  {
    if ((paramFragment != null) && ((!paramFragment.equals(findActiveFragment(paramFragment.mWho))) || ((paramFragment.mHost != null) && (paramFragment.mFragmentManager != this))))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Fragment ");
      ((StringBuilder)localObject).append(paramFragment);
      ((StringBuilder)localObject).append(" is not an active fragment of FragmentManager ");
      ((StringBuilder)localObject).append(this);
      throw new IllegalArgumentException(((StringBuilder)localObject).toString());
    }
    Object localObject = this.mPrimaryNav;
    this.mPrimaryNav = paramFragment;
    dispatchParentPrimaryNavigationFragmentChanged((Fragment)localObject);
    dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
  }
  
  void showFragment(Fragment paramFragment)
  {
    if (isLoggingEnabled(2))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("show: ");
      localStringBuilder.append(paramFragment);
      Log.v("FragmentManager", localStringBuilder.toString());
    }
    if (paramFragment.mHidden)
    {
      paramFragment.mHidden = false;
      paramFragment.mHiddenChanged ^= true;
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    localStringBuilder.append("FragmentManager{");
    localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    localStringBuilder.append(" in ");
    Fragment localFragment = this.mParent;
    if (localFragment != null)
    {
      localStringBuilder.append(localFragment.getClass().getSimpleName());
      localStringBuilder.append("{");
      localStringBuilder.append(Integer.toHexString(System.identityHashCode(this.mParent)));
      localStringBuilder.append("}");
    }
    else
    {
      localStringBuilder.append(this.mHost.getClass().getSimpleName());
      localStringBuilder.append("{");
      localStringBuilder.append(Integer.toHexString(System.identityHashCode(this.mHost)));
      localStringBuilder.append("}");
    }
    localStringBuilder.append("}}");
    return localStringBuilder.toString();
  }
  
  public void unregisterFragmentLifecycleCallbacks(FragmentLifecycleCallbacks paramFragmentLifecycleCallbacks)
  {
    this.mLifecycleCallbacksDispatcher.unregisterFragmentLifecycleCallbacks(paramFragmentLifecycleCallbacks);
  }
  
  public static abstract interface BackStackEntry
  {
    @Deprecated
    public abstract CharSequence getBreadCrumbShortTitle();
    
    @Deprecated
    public abstract int getBreadCrumbShortTitleRes();
    
    @Deprecated
    public abstract CharSequence getBreadCrumbTitle();
    
    @Deprecated
    public abstract int getBreadCrumbTitleRes();
    
    public abstract int getId();
    
    public abstract String getName();
  }
  
  public static abstract class FragmentLifecycleCallbacks
  {
    public FragmentLifecycleCallbacks() {}
    
    public void onFragmentActivityCreated(FragmentManager paramFragmentManager, Fragment paramFragment, Bundle paramBundle) {}
    
    public void onFragmentAttached(FragmentManager paramFragmentManager, Fragment paramFragment, Context paramContext) {}
    
    public void onFragmentCreated(FragmentManager paramFragmentManager, Fragment paramFragment, Bundle paramBundle) {}
    
    public void onFragmentDestroyed(FragmentManager paramFragmentManager, Fragment paramFragment) {}
    
    public void onFragmentDetached(FragmentManager paramFragmentManager, Fragment paramFragment) {}
    
    public void onFragmentPaused(FragmentManager paramFragmentManager, Fragment paramFragment) {}
    
    public void onFragmentPreAttached(FragmentManager paramFragmentManager, Fragment paramFragment, Context paramContext) {}
    
    public void onFragmentPreCreated(FragmentManager paramFragmentManager, Fragment paramFragment, Bundle paramBundle) {}
    
    public void onFragmentResumed(FragmentManager paramFragmentManager, Fragment paramFragment) {}
    
    public void onFragmentSaveInstanceState(FragmentManager paramFragmentManager, Fragment paramFragment, Bundle paramBundle) {}
    
    public void onFragmentStarted(FragmentManager paramFragmentManager, Fragment paramFragment) {}
    
    public void onFragmentStopped(FragmentManager paramFragmentManager, Fragment paramFragment) {}
    
    public void onFragmentViewCreated(FragmentManager paramFragmentManager, Fragment paramFragment, View paramView, Bundle paramBundle) {}
    
    public void onFragmentViewDestroyed(FragmentManager paramFragmentManager, Fragment paramFragment) {}
  }
  
  public static abstract interface OnBackStackChangedListener
  {
    public abstract void onBackStackChanged();
  }
  
  static abstract interface OpGenerator
  {
    public abstract boolean generateOps(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1);
  }
  
  private class PopBackStackState
    implements FragmentManager.OpGenerator
  {
    final int mFlags;
    final int mId;
    final String mName;
    
    PopBackStackState(String paramString, int paramInt1, int paramInt2)
    {
      this.mName = paramString;
      this.mId = paramInt1;
      this.mFlags = paramInt2;
    }
    
    public boolean generateOps(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1)
    {
      if ((FragmentManager.this.mPrimaryNav != null) && (this.mId < 0) && (this.mName == null) && (FragmentManager.this.mPrimaryNav.getChildFragmentManager().popBackStackImmediate())) {
        return false;
      }
      return FragmentManager.this.popBackStackState(paramArrayList, paramArrayList1, this.mName, this.mId, this.mFlags);
    }
  }
  
  static class StartEnterTransitionListener
    implements Fragment.OnStartEnterTransitionListener
  {
    final boolean mIsBack;
    private int mNumPostponed;
    final BackStackRecord mRecord;
    
    StartEnterTransitionListener(BackStackRecord paramBackStackRecord, boolean paramBoolean)
    {
      this.mIsBack = paramBoolean;
      this.mRecord = paramBackStackRecord;
    }
    
    void cancelTransaction()
    {
      this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, false, false);
    }
    
    void completeTransaction()
    {
      int i;
      if (this.mNumPostponed > 0) {
        i = 1;
      } else {
        i = 0;
      }
      Iterator localIterator = this.mRecord.mManager.getFragments().iterator();
      while (localIterator.hasNext())
      {
        Fragment localFragment = (Fragment)localIterator.next();
        localFragment.setOnStartEnterTransitionListener(null);
        if ((i != 0) && (localFragment.isPostponed())) {
          localFragment.startPostponedEnterTransition();
        }
      }
      this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, i ^ 0x1, true);
    }
    
    public boolean isReady()
    {
      boolean bool;
      if (this.mNumPostponed == 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onStartEnterTransition()
    {
      int i = this.mNumPostponed - 1;
      this.mNumPostponed = i;
      if (i != 0) {
        return;
      }
      this.mRecord.mManager.scheduleCommit();
    }
    
    public void startListening()
    {
      this.mNumPostponed += 1;
    }
  }
}

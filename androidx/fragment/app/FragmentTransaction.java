package androidx.fragment.app;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Lifecycle.State;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public abstract class FragmentTransaction
{
  static final int OP_ADD = 1;
  static final int OP_ATTACH = 7;
  static final int OP_DETACH = 6;
  static final int OP_HIDE = 4;
  static final int OP_NULL = 0;
  static final int OP_REMOVE = 3;
  static final int OP_REPLACE = 2;
  static final int OP_SET_MAX_LIFECYCLE = 10;
  static final int OP_SET_PRIMARY_NAV = 8;
  static final int OP_SHOW = 5;
  static final int OP_UNSET_PRIMARY_NAV = 9;
  public static final int TRANSIT_ENTER_MASK = 4096;
  public static final int TRANSIT_EXIT_MASK = 8192;
  public static final int TRANSIT_FRAGMENT_CLOSE = 8194;
  public static final int TRANSIT_FRAGMENT_FADE = 4099;
  public static final int TRANSIT_FRAGMENT_OPEN = 4097;
  public static final int TRANSIT_NONE = 0;
  public static final int TRANSIT_UNSET = -1;
  boolean mAddToBackStack;
  boolean mAllowAddToBackStack = true;
  int mBreadCrumbShortTitleRes;
  CharSequence mBreadCrumbShortTitleText;
  int mBreadCrumbTitleRes;
  CharSequence mBreadCrumbTitleText;
  private final ClassLoader mClassLoader;
  ArrayList<Runnable> mCommitRunnables;
  int mEnterAnim;
  int mExitAnim;
  private final FragmentFactory mFragmentFactory;
  String mName;
  ArrayList<Op> mOps = new ArrayList();
  int mPopEnterAnim;
  int mPopExitAnim;
  boolean mReorderingAllowed = false;
  ArrayList<String> mSharedElementSourceNames;
  ArrayList<String> mSharedElementTargetNames;
  int mTransition;
  
  @Deprecated
  public FragmentTransaction()
  {
    this.mFragmentFactory = null;
    this.mClassLoader = null;
  }
  
  FragmentTransaction(FragmentFactory paramFragmentFactory, ClassLoader paramClassLoader)
  {
    this.mFragmentFactory = paramFragmentFactory;
    this.mClassLoader = paramClassLoader;
  }
  
  private Fragment createFragment(Class<? extends Fragment> paramClass, Bundle paramBundle)
  {
    FragmentFactory localFragmentFactory = this.mFragmentFactory;
    if (localFragmentFactory != null)
    {
      ClassLoader localClassLoader = this.mClassLoader;
      if (localClassLoader != null)
      {
        paramClass = localFragmentFactory.instantiate(localClassLoader, paramClass.getName());
        if (paramBundle != null) {
          paramClass.setArguments(paramBundle);
        }
        return paramClass;
      }
      throw new IllegalStateException("The FragmentManager must be attached to itshost to create a Fragment");
    }
    throw new IllegalStateException("Creating a Fragment requires that this FragmentTransaction was built with FragmentManager.beginTransaction()");
  }
  
  public FragmentTransaction add(int paramInt, Fragment paramFragment)
  {
    doAddOp(paramInt, paramFragment, null, 1);
    return this;
  }
  
  public FragmentTransaction add(int paramInt, Fragment paramFragment, String paramString)
  {
    doAddOp(paramInt, paramFragment, paramString, 1);
    return this;
  }
  
  public final FragmentTransaction add(int paramInt, Class<? extends Fragment> paramClass, Bundle paramBundle)
  {
    return add(paramInt, createFragment(paramClass, paramBundle));
  }
  
  public final FragmentTransaction add(int paramInt, Class<? extends Fragment> paramClass, Bundle paramBundle, String paramString)
  {
    return add(paramInt, createFragment(paramClass, paramBundle), paramString);
  }
  
  FragmentTransaction add(ViewGroup paramViewGroup, Fragment paramFragment, String paramString)
  {
    paramFragment.mContainer = paramViewGroup;
    return add(paramViewGroup.getId(), paramFragment, paramString);
  }
  
  public FragmentTransaction add(Fragment paramFragment, String paramString)
  {
    doAddOp(0, paramFragment, paramString, 1);
    return this;
  }
  
  public final FragmentTransaction add(Class<? extends Fragment> paramClass, Bundle paramBundle, String paramString)
  {
    return add(createFragment(paramClass, paramBundle), paramString);
  }
  
  void addOp(Op paramOp)
  {
    this.mOps.add(paramOp);
    paramOp.mEnterAnim = this.mEnterAnim;
    paramOp.mExitAnim = this.mExitAnim;
    paramOp.mPopEnterAnim = this.mPopEnterAnim;
    paramOp.mPopExitAnim = this.mPopExitAnim;
  }
  
  public FragmentTransaction addSharedElement(View paramView, String paramString)
  {
    if (FragmentTransition.supportsTransition())
    {
      paramView = ViewCompat.getTransitionName(paramView);
      if (paramView != null)
      {
        if (this.mSharedElementSourceNames == null)
        {
          this.mSharedElementSourceNames = new ArrayList();
          this.mSharedElementTargetNames = new ArrayList();
        }
        else
        {
          if (this.mSharedElementTargetNames.contains(paramString)) {
            break label130;
          }
          if (this.mSharedElementSourceNames.contains(paramView)) {
            break label90;
          }
        }
        this.mSharedElementSourceNames.add(paramView);
        this.mSharedElementTargetNames.add(paramString);
        return this;
        label90:
        paramString = new StringBuilder();
        paramString.append("A shared element with the source name '");
        paramString.append(paramView);
        paramString.append("' has already been added to the transaction.");
        throw new IllegalArgumentException(paramString.toString());
        label130:
        paramView = new StringBuilder();
        paramView.append("A shared element with the target name '");
        paramView.append(paramString);
        paramView.append("' has already been added to the transaction.");
        throw new IllegalArgumentException(paramView.toString());
      }
      else
      {
        throw new IllegalArgumentException("Unique transitionNames are required for all sharedElements");
      }
    }
    return this;
  }
  
  public FragmentTransaction addToBackStack(String paramString)
  {
    if (this.mAllowAddToBackStack)
    {
      this.mAddToBackStack = true;
      this.mName = paramString;
      return this;
    }
    throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
  }
  
  public FragmentTransaction attach(Fragment paramFragment)
  {
    addOp(new Op(7, paramFragment));
    return this;
  }
  
  public abstract int commit();
  
  public abstract int commitAllowingStateLoss();
  
  public abstract void commitNow();
  
  public abstract void commitNowAllowingStateLoss();
  
  public FragmentTransaction detach(Fragment paramFragment)
  {
    addOp(new Op(6, paramFragment));
    return this;
  }
  
  public FragmentTransaction disallowAddToBackStack()
  {
    if (!this.mAddToBackStack)
    {
      this.mAllowAddToBackStack = false;
      return this;
    }
    throw new IllegalStateException("This transaction is already being added to the back stack");
  }
  
  void doAddOp(int paramInt1, Fragment paramFragment, String paramString, int paramInt2)
  {
    Object localObject = paramFragment.getClass();
    int i = ((Class)localObject).getModifiers();
    if ((!((Class)localObject).isAnonymousClass()) && (Modifier.isPublic(i)) && ((!((Class)localObject).isMemberClass()) || (Modifier.isStatic(i))))
    {
      if (paramString != null)
      {
        if ((paramFragment.mTag != null) && (!paramString.equals(paramFragment.mTag)))
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Can't change tag of fragment ");
          ((StringBuilder)localObject).append(paramFragment);
          ((StringBuilder)localObject).append(": was ");
          ((StringBuilder)localObject).append(paramFragment.mTag);
          ((StringBuilder)localObject).append(" now ");
          ((StringBuilder)localObject).append(paramString);
          throw new IllegalStateException(((StringBuilder)localObject).toString());
        }
        paramFragment.mTag = paramString;
      }
      if (paramInt1 != 0) {
        if (paramInt1 != -1)
        {
          if ((paramFragment.mFragmentId != 0) && (paramFragment.mFragmentId != paramInt1))
          {
            paramString = new StringBuilder();
            paramString.append("Can't change container ID of fragment ");
            paramString.append(paramFragment);
            paramString.append(": was ");
            paramString.append(paramFragment.mFragmentId);
            paramString.append(" now ");
            paramString.append(paramInt1);
            throw new IllegalStateException(paramString.toString());
          }
          paramFragment.mFragmentId = paramInt1;
          paramFragment.mContainerId = paramInt1;
        }
        else
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Can't add fragment ");
          ((StringBuilder)localObject).append(paramFragment);
          ((StringBuilder)localObject).append(" with tag ");
          ((StringBuilder)localObject).append(paramString);
          ((StringBuilder)localObject).append(" to container view with no id");
          throw new IllegalArgumentException(((StringBuilder)localObject).toString());
        }
      }
      addOp(new Op(paramInt2, paramFragment));
      return;
    }
    paramFragment = new StringBuilder();
    paramFragment.append("Fragment ");
    paramFragment.append(((Class)localObject).getCanonicalName());
    paramFragment.append(" must be a public static class to be  properly recreated from instance state.");
    throw new IllegalStateException(paramFragment.toString());
  }
  
  public FragmentTransaction hide(Fragment paramFragment)
  {
    addOp(new Op(4, paramFragment));
    return this;
  }
  
  public boolean isAddToBackStackAllowed()
  {
    return this.mAllowAddToBackStack;
  }
  
  public boolean isEmpty()
  {
    return this.mOps.isEmpty();
  }
  
  public FragmentTransaction remove(Fragment paramFragment)
  {
    addOp(new Op(3, paramFragment));
    return this;
  }
  
  public FragmentTransaction replace(int paramInt, Fragment paramFragment)
  {
    return replace(paramInt, paramFragment, null);
  }
  
  public FragmentTransaction replace(int paramInt, Fragment paramFragment, String paramString)
  {
    if (paramInt != 0)
    {
      doAddOp(paramInt, paramFragment, paramString, 2);
      return this;
    }
    throw new IllegalArgumentException("Must use non-zero containerViewId");
  }
  
  public final FragmentTransaction replace(int paramInt, Class<? extends Fragment> paramClass, Bundle paramBundle)
  {
    return replace(paramInt, paramClass, paramBundle, null);
  }
  
  public final FragmentTransaction replace(int paramInt, Class<? extends Fragment> paramClass, Bundle paramBundle, String paramString)
  {
    return replace(paramInt, createFragment(paramClass, paramBundle), paramString);
  }
  
  public FragmentTransaction runOnCommit(Runnable paramRunnable)
  {
    disallowAddToBackStack();
    if (this.mCommitRunnables == null) {
      this.mCommitRunnables = new ArrayList();
    }
    this.mCommitRunnables.add(paramRunnable);
    return this;
  }
  
  @Deprecated
  public FragmentTransaction setAllowOptimization(boolean paramBoolean)
  {
    return setReorderingAllowed(paramBoolean);
  }
  
  @Deprecated
  public FragmentTransaction setBreadCrumbShortTitle(int paramInt)
  {
    this.mBreadCrumbShortTitleRes = paramInt;
    this.mBreadCrumbShortTitleText = null;
    return this;
  }
  
  @Deprecated
  public FragmentTransaction setBreadCrumbShortTitle(CharSequence paramCharSequence)
  {
    this.mBreadCrumbShortTitleRes = 0;
    this.mBreadCrumbShortTitleText = paramCharSequence;
    return this;
  }
  
  @Deprecated
  public FragmentTransaction setBreadCrumbTitle(int paramInt)
  {
    this.mBreadCrumbTitleRes = paramInt;
    this.mBreadCrumbTitleText = null;
    return this;
  }
  
  @Deprecated
  public FragmentTransaction setBreadCrumbTitle(CharSequence paramCharSequence)
  {
    this.mBreadCrumbTitleRes = 0;
    this.mBreadCrumbTitleText = paramCharSequence;
    return this;
  }
  
  public FragmentTransaction setCustomAnimations(int paramInt1, int paramInt2)
  {
    return setCustomAnimations(paramInt1, paramInt2, 0, 0);
  }
  
  public FragmentTransaction setCustomAnimations(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mEnterAnim = paramInt1;
    this.mExitAnim = paramInt2;
    this.mPopEnterAnim = paramInt3;
    this.mPopExitAnim = paramInt4;
    return this;
  }
  
  public FragmentTransaction setMaxLifecycle(Fragment paramFragment, Lifecycle.State paramState)
  {
    addOp(new Op(10, paramFragment, paramState));
    return this;
  }
  
  public FragmentTransaction setPrimaryNavigationFragment(Fragment paramFragment)
  {
    addOp(new Op(8, paramFragment));
    return this;
  }
  
  public FragmentTransaction setReorderingAllowed(boolean paramBoolean)
  {
    this.mReorderingAllowed = paramBoolean;
    return this;
  }
  
  public FragmentTransaction setTransition(int paramInt)
  {
    this.mTransition = paramInt;
    return this;
  }
  
  @Deprecated
  public FragmentTransaction setTransitionStyle(int paramInt)
  {
    return this;
  }
  
  public FragmentTransaction show(Fragment paramFragment)
  {
    addOp(new Op(5, paramFragment));
    return this;
  }
  
  static final class Op
  {
    int mCmd;
    Lifecycle.State mCurrentMaxState;
    int mEnterAnim;
    int mExitAnim;
    Fragment mFragment;
    Lifecycle.State mOldMaxState;
    int mPopEnterAnim;
    int mPopExitAnim;
    
    Op() {}
    
    Op(int paramInt, Fragment paramFragment)
    {
      this.mCmd = paramInt;
      this.mFragment = paramFragment;
      this.mOldMaxState = Lifecycle.State.RESUMED;
      this.mCurrentMaxState = Lifecycle.State.RESUMED;
    }
    
    Op(int paramInt, Fragment paramFragment, Lifecycle.State paramState)
    {
      this.mCmd = paramInt;
      this.mFragment = paramFragment;
      this.mOldMaxState = paramFragment.mMaxState;
      this.mCurrentMaxState = paramState;
    }
  }
}

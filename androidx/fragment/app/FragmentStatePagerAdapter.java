package androidx.fragment.app;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Lifecycle.State;
import androidx.viewpager.widget.PagerAdapter;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class FragmentStatePagerAdapter
  extends PagerAdapter
{
  public static final int BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT = 1;
  @Deprecated
  public static final int BEHAVIOR_SET_USER_VISIBLE_HINT = 0;
  private static final boolean DEBUG = false;
  private static final String TAG = "FragmentStatePagerAdapt";
  private final int mBehavior;
  private FragmentTransaction mCurTransaction = null;
  private Fragment mCurrentPrimaryItem = null;
  private final FragmentManager mFragmentManager;
  private ArrayList<Fragment> mFragments = new ArrayList();
  private ArrayList<Fragment.SavedState> mSavedState = new ArrayList();
  
  @Deprecated
  public FragmentStatePagerAdapter(FragmentManager paramFragmentManager)
  {
    this(paramFragmentManager, 0);
  }
  
  public FragmentStatePagerAdapter(FragmentManager paramFragmentManager, int paramInt)
  {
    this.mFragmentManager = paramFragmentManager;
    this.mBehavior = paramInt;
  }
  
  public void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject)
  {
    Fragment localFragment = (Fragment)paramObject;
    if (this.mCurTransaction == null) {
      this.mCurTransaction = this.mFragmentManager.beginTransaction();
    }
    while (this.mSavedState.size() <= paramInt) {
      this.mSavedState.add(null);
    }
    paramObject = this.mSavedState;
    if (localFragment.isAdded()) {
      paramViewGroup = this.mFragmentManager.saveFragmentInstanceState(localFragment);
    } else {
      paramViewGroup = null;
    }
    paramObject.set(paramInt, paramViewGroup);
    this.mFragments.set(paramInt, null);
    this.mCurTransaction.remove(localFragment);
    if (localFragment.equals(this.mCurrentPrimaryItem)) {
      this.mCurrentPrimaryItem = null;
    }
  }
  
  public void finishUpdate(ViewGroup paramViewGroup)
  {
    paramViewGroup = this.mCurTransaction;
    if (paramViewGroup != null)
    {
      try
      {
        paramViewGroup.commitNowAllowingStateLoss();
      }
      catch (IllegalStateException paramViewGroup)
      {
        this.mCurTransaction.commitAllowingStateLoss();
      }
      this.mCurTransaction = null;
    }
  }
  
  public abstract Fragment getItem(int paramInt);
  
  public Object instantiateItem(ViewGroup paramViewGroup, int paramInt)
  {
    if (this.mFragments.size() > paramInt)
    {
      localFragment = (Fragment)this.mFragments.get(paramInt);
      if (localFragment != null) {
        return localFragment;
      }
    }
    if (this.mCurTransaction == null) {
      this.mCurTransaction = this.mFragmentManager.beginTransaction();
    }
    Fragment localFragment = getItem(paramInt);
    if (this.mSavedState.size() > paramInt)
    {
      Fragment.SavedState localSavedState = (Fragment.SavedState)this.mSavedState.get(paramInt);
      if (localSavedState != null) {
        localFragment.setInitialSavedState(localSavedState);
      }
    }
    while (this.mFragments.size() <= paramInt) {
      this.mFragments.add(null);
    }
    localFragment.setMenuVisibility(false);
    if (this.mBehavior == 0) {
      localFragment.setUserVisibleHint(false);
    }
    this.mFragments.set(paramInt, localFragment);
    this.mCurTransaction.add(paramViewGroup.getId(), localFragment);
    if (this.mBehavior == 1) {
      this.mCurTransaction.setMaxLifecycle(localFragment, Lifecycle.State.STARTED);
    }
    return localFragment;
  }
  
  public boolean isViewFromObject(View paramView, Object paramObject)
  {
    boolean bool;
    if (((Fragment)paramObject).getView() == paramView) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void restoreState(Parcelable paramParcelable, ClassLoader paramClassLoader)
  {
    if (paramParcelable != null)
    {
      paramParcelable = (Bundle)paramParcelable;
      paramParcelable.setClassLoader(paramClassLoader);
      paramClassLoader = paramParcelable.getParcelableArray("states");
      this.mSavedState.clear();
      this.mFragments.clear();
      int i;
      if (paramClassLoader != null) {
        for (i = 0; i < paramClassLoader.length; i++) {
          this.mSavedState.add((Fragment.SavedState)paramClassLoader[i]);
        }
      }
      Iterator localIterator = paramParcelable.keySet().iterator();
      while (localIterator.hasNext())
      {
        paramClassLoader = (String)localIterator.next();
        if (paramClassLoader.startsWith("f"))
        {
          i = Integer.parseInt(paramClassLoader.substring(1));
          Object localObject = this.mFragmentManager.getFragment(paramParcelable, paramClassLoader);
          if (localObject != null)
          {
            while (this.mFragments.size() <= i) {
              this.mFragments.add(null);
            }
            ((Fragment)localObject).setMenuVisibility(false);
            this.mFragments.set(i, localObject);
          }
          else
          {
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("Bad fragment at key ");
            ((StringBuilder)localObject).append(paramClassLoader);
            Log.w("FragmentStatePagerAdapt", ((StringBuilder)localObject).toString());
          }
        }
      }
    }
  }
  
  public Parcelable saveState()
  {
    Object localObject1;
    Object localObject2;
    if (this.mSavedState.size() > 0)
    {
      localObject1 = new Bundle();
      localObject2 = new Fragment.SavedState[this.mSavedState.size()];
      this.mSavedState.toArray((Object[])localObject2);
      ((Bundle)localObject1).putParcelableArray("states", (Parcelable[])localObject2);
    }
    else
    {
      localObject1 = null;
    }
    int i = 0;
    while (i < this.mFragments.size())
    {
      Fragment localFragment = (Fragment)this.mFragments.get(i);
      localObject2 = localObject1;
      if (localFragment != null)
      {
        localObject2 = localObject1;
        if (localFragment.isAdded())
        {
          localObject2 = localObject1;
          if (localObject1 == null) {
            localObject2 = new Bundle();
          }
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("f");
          ((StringBuilder)localObject1).append(i);
          localObject1 = ((StringBuilder)localObject1).toString();
          this.mFragmentManager.putFragment((Bundle)localObject2, (String)localObject1, localFragment);
        }
      }
      i++;
      localObject1 = localObject2;
    }
    return localObject1;
  }
  
  public void setPrimaryItem(ViewGroup paramViewGroup, int paramInt, Object paramObject)
  {
    paramViewGroup = (Fragment)paramObject;
    paramObject = this.mCurrentPrimaryItem;
    if (paramViewGroup != paramObject)
    {
      if (paramObject != null)
      {
        paramObject.setMenuVisibility(false);
        if (this.mBehavior == 1)
        {
          if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
          }
          this.mCurTransaction.setMaxLifecycle(this.mCurrentPrimaryItem, Lifecycle.State.STARTED);
        }
        else
        {
          this.mCurrentPrimaryItem.setUserVisibleHint(false);
        }
      }
      paramViewGroup.setMenuVisibility(true);
      if (this.mBehavior == 1)
      {
        if (this.mCurTransaction == null) {
          this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        this.mCurTransaction.setMaxLifecycle(paramViewGroup, Lifecycle.State.RESUMED);
      }
      else
      {
        paramViewGroup.setUserVisibleHint(true);
      }
      this.mCurrentPrimaryItem = paramViewGroup;
    }
  }
  
  public void startUpdate(ViewGroup paramViewGroup)
  {
    if (paramViewGroup.getId() != -1) {
      return;
    }
    paramViewGroup = new StringBuilder();
    paramViewGroup.append("ViewPager with adapter ");
    paramViewGroup.append(this);
    paramViewGroup.append(" requires a view id");
    throw new IllegalStateException(paramViewGroup.toString());
  }
}

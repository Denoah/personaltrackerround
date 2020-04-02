package androidx.fragment.app;

import android.util.Log;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProvider.Factory;
import androidx.lifecycle.ViewModelStore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

final class FragmentManagerViewModel
  extends ViewModel
{
  private static final ViewModelProvider.Factory FACTORY = new ViewModelProvider.Factory()
  {
    public <T extends ViewModel> T create(Class<T> paramAnonymousClass)
    {
      return new FragmentManagerViewModel(true);
    }
  };
  private static final String TAG = "FragmentManager";
  private final HashMap<String, FragmentManagerViewModel> mChildNonConfigs = new HashMap();
  private boolean mHasBeenCleared = false;
  private boolean mHasSavedSnapshot = false;
  private final HashMap<String, Fragment> mRetainedFragments = new HashMap();
  private final boolean mStateAutomaticallySaved;
  private final HashMap<String, ViewModelStore> mViewModelStores = new HashMap();
  
  FragmentManagerViewModel(boolean paramBoolean)
  {
    this.mStateAutomaticallySaved = paramBoolean;
  }
  
  static FragmentManagerViewModel getInstance(ViewModelStore paramViewModelStore)
  {
    return (FragmentManagerViewModel)new ViewModelProvider(paramViewModelStore, FACTORY).get(FragmentManagerViewModel.class);
  }
  
  boolean addRetainedFragment(Fragment paramFragment)
  {
    if (this.mRetainedFragments.containsKey(paramFragment.mWho)) {
      return false;
    }
    this.mRetainedFragments.put(paramFragment.mWho, paramFragment);
    return true;
  }
  
  void clearNonConfigState(Fragment paramFragment)
  {
    if (FragmentManager.isLoggingEnabled(3))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Clearing non-config state for ");
      ((StringBuilder)localObject).append(paramFragment);
      Log.d("FragmentManager", ((StringBuilder)localObject).toString());
    }
    Object localObject = (FragmentManagerViewModel)this.mChildNonConfigs.get(paramFragment.mWho);
    if (localObject != null)
    {
      ((FragmentManagerViewModel)localObject).onCleared();
      this.mChildNonConfigs.remove(paramFragment.mWho);
    }
    localObject = (ViewModelStore)this.mViewModelStores.get(paramFragment.mWho);
    if (localObject != null)
    {
      ((ViewModelStore)localObject).clear();
      this.mViewModelStores.remove(paramFragment.mWho);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {
      return true;
    }
    if ((paramObject != null) && (getClass() == paramObject.getClass()))
    {
      paramObject = (FragmentManagerViewModel)paramObject;
      if ((!this.mRetainedFragments.equals(paramObject.mRetainedFragments)) || (!this.mChildNonConfigs.equals(paramObject.mChildNonConfigs)) || (!this.mViewModelStores.equals(paramObject.mViewModelStores))) {
        bool = false;
      }
      return bool;
    }
    return false;
  }
  
  Fragment findRetainedFragmentByWho(String paramString)
  {
    return (Fragment)this.mRetainedFragments.get(paramString);
  }
  
  FragmentManagerViewModel getChildNonConfig(Fragment paramFragment)
  {
    FragmentManagerViewModel localFragmentManagerViewModel1 = (FragmentManagerViewModel)this.mChildNonConfigs.get(paramFragment.mWho);
    FragmentManagerViewModel localFragmentManagerViewModel2 = localFragmentManagerViewModel1;
    if (localFragmentManagerViewModel1 == null)
    {
      localFragmentManagerViewModel2 = new FragmentManagerViewModel(this.mStateAutomaticallySaved);
      this.mChildNonConfigs.put(paramFragment.mWho, localFragmentManagerViewModel2);
    }
    return localFragmentManagerViewModel2;
  }
  
  Collection<Fragment> getRetainedFragments()
  {
    return this.mRetainedFragments.values();
  }
  
  @Deprecated
  FragmentManagerNonConfig getSnapshot()
  {
    if ((this.mRetainedFragments.isEmpty()) && (this.mChildNonConfigs.isEmpty()) && (this.mViewModelStores.isEmpty())) {
      return null;
    }
    HashMap localHashMap = new HashMap();
    Iterator localIterator = this.mChildNonConfigs.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      FragmentManagerNonConfig localFragmentManagerNonConfig = ((FragmentManagerViewModel)localEntry.getValue()).getSnapshot();
      if (localFragmentManagerNonConfig != null) {
        localHashMap.put(localEntry.getKey(), localFragmentManagerNonConfig);
      }
    }
    this.mHasSavedSnapshot = true;
    if ((this.mRetainedFragments.isEmpty()) && (localHashMap.isEmpty()) && (this.mViewModelStores.isEmpty())) {
      return null;
    }
    return new FragmentManagerNonConfig(new ArrayList(this.mRetainedFragments.values()), localHashMap, new HashMap(this.mViewModelStores));
  }
  
  ViewModelStore getViewModelStore(Fragment paramFragment)
  {
    ViewModelStore localViewModelStore1 = (ViewModelStore)this.mViewModelStores.get(paramFragment.mWho);
    ViewModelStore localViewModelStore2 = localViewModelStore1;
    if (localViewModelStore1 == null)
    {
      localViewModelStore2 = new ViewModelStore();
      this.mViewModelStores.put(paramFragment.mWho, localViewModelStore2);
    }
    return localViewModelStore2;
  }
  
  public int hashCode()
  {
    return (this.mRetainedFragments.hashCode() * 31 + this.mChildNonConfigs.hashCode()) * 31 + this.mViewModelStores.hashCode();
  }
  
  boolean isCleared()
  {
    return this.mHasBeenCleared;
  }
  
  protected void onCleared()
  {
    if (FragmentManager.isLoggingEnabled(3))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("onCleared called for ");
      localStringBuilder.append(this);
      Log.d("FragmentManager", localStringBuilder.toString());
    }
    this.mHasBeenCleared = true;
  }
  
  boolean removeRetainedFragment(Fragment paramFragment)
  {
    boolean bool;
    if (this.mRetainedFragments.remove(paramFragment.mWho) != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  @Deprecated
  void restoreFromSnapshot(FragmentManagerNonConfig paramFragmentManagerNonConfig)
  {
    this.mRetainedFragments.clear();
    this.mChildNonConfigs.clear();
    this.mViewModelStores.clear();
    if (paramFragmentManagerNonConfig != null)
    {
      Object localObject1 = paramFragmentManagerNonConfig.getFragments();
      Object localObject2;
      if (localObject1 != null)
      {
        localObject2 = ((Collection)localObject1).iterator();
        while (((Iterator)localObject2).hasNext())
        {
          localObject1 = (Fragment)((Iterator)localObject2).next();
          if (localObject1 != null) {
            this.mRetainedFragments.put(((Fragment)localObject1).mWho, localObject1);
          }
        }
      }
      localObject1 = paramFragmentManagerNonConfig.getChildNonConfigs();
      if (localObject1 != null)
      {
        localObject1 = ((Map)localObject1).entrySet().iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (Map.Entry)((Iterator)localObject1).next();
          FragmentManagerViewModel localFragmentManagerViewModel = new FragmentManagerViewModel(this.mStateAutomaticallySaved);
          localFragmentManagerViewModel.restoreFromSnapshot((FragmentManagerNonConfig)((Map.Entry)localObject2).getValue());
          this.mChildNonConfigs.put(((Map.Entry)localObject2).getKey(), localFragmentManagerViewModel);
        }
      }
      paramFragmentManagerNonConfig = paramFragmentManagerNonConfig.getViewModelStores();
      if (paramFragmentManagerNonConfig != null) {
        this.mViewModelStores.putAll(paramFragmentManagerNonConfig);
      }
    }
    this.mHasSavedSnapshot = false;
  }
  
  boolean shouldDestroy(Fragment paramFragment)
  {
    if (!this.mRetainedFragments.containsKey(paramFragment.mWho)) {
      return true;
    }
    if (this.mStateAutomaticallySaved) {
      return this.mHasBeenCleared;
    }
    return this.mHasSavedSnapshot ^ true;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("FragmentManagerViewModel{");
    localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    localStringBuilder.append("} Fragments (");
    Iterator localIterator = this.mRetainedFragments.values().iterator();
    while (localIterator.hasNext())
    {
      localStringBuilder.append(localIterator.next());
      if (localIterator.hasNext()) {
        localStringBuilder.append(", ");
      }
    }
    localStringBuilder.append(") Child Non Config (");
    localIterator = this.mChildNonConfigs.keySet().iterator();
    while (localIterator.hasNext())
    {
      localStringBuilder.append((String)localIterator.next());
      if (localIterator.hasNext()) {
        localStringBuilder.append(", ");
      }
    }
    localStringBuilder.append(") ViewModelStores (");
    localIterator = this.mViewModelStores.keySet().iterator();
    while (localIterator.hasNext())
    {
      localStringBuilder.append((String)localIterator.next());
      if (localIterator.hasNext()) {
        localStringBuilder.append(", ");
      }
    }
    localStringBuilder.append(')');
    return localStringBuilder.toString();
  }
}

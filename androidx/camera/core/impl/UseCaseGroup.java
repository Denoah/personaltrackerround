package androidx.camera.core.impl;

import android.util.Log;
import androidx.camera.core.UseCase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class UseCaseGroup
{
  private static final String TAG = "UseCaseGroup";
  private volatile boolean mIsActive = false;
  private final Object mListenerLock = new Object();
  private StateChangeCallback mStateChangeCallback;
  private final Set<UseCase> mUseCases = new HashSet();
  private final Object mUseCasesLock = new Object();
  
  public UseCaseGroup() {}
  
  public boolean addUseCase(UseCase paramUseCase)
  {
    synchronized (this.mUseCasesLock)
    {
      boolean bool = this.mUseCases.add(paramUseCase);
      return bool;
    }
  }
  
  public void clear()
  {
    Object localObject1 = new ArrayList();
    synchronized (this.mUseCasesLock)
    {
      ((List)localObject1).addAll(this.mUseCases);
      this.mUseCases.clear();
      ??? = ((List)localObject1).iterator();
      while (((Iterator)???).hasNext())
      {
        localObject1 = (UseCase)((Iterator)???).next();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Clearing use case: ");
        localStringBuilder.append(((UseCase)localObject1).getName());
        Log.d("UseCaseGroup", localStringBuilder.toString());
        ((UseCase)localObject1).clear();
      }
      return;
    }
  }
  
  public boolean contains(UseCase paramUseCase)
  {
    synchronized (this.mUseCasesLock)
    {
      boolean bool = this.mUseCases.contains(paramUseCase);
      return bool;
    }
  }
  
  public Map<String, Set<UseCase>> getCameraIdToUseCaseMap()
  {
    HashMap localHashMap = new HashMap();
    synchronized (this.mUseCasesLock)
    {
      Iterator localIterator1 = this.mUseCases.iterator();
      while (localIterator1.hasNext())
      {
        UseCase localUseCase = (UseCase)localIterator1.next();
        Iterator localIterator2 = localUseCase.getAttachedCameraIds().iterator();
        while (localIterator2.hasNext())
        {
          String str = (String)localIterator2.next();
          Set localSet = (Set)localHashMap.get(str);
          Object localObject2 = localSet;
          if (localSet == null)
          {
            localObject2 = new java/util/HashSet;
            ((HashSet)localObject2).<init>();
          }
          ((Set)localObject2).add(localUseCase);
          localHashMap.put(str, localObject2);
        }
      }
      return Collections.unmodifiableMap(localHashMap);
    }
  }
  
  public Collection<UseCase> getUseCases()
  {
    synchronized (this.mUseCasesLock)
    {
      Collection localCollection = Collections.unmodifiableCollection(this.mUseCases);
      return localCollection;
    }
  }
  
  public boolean isActive()
  {
    return this.mIsActive;
  }
  
  public boolean removeUseCase(UseCase paramUseCase)
  {
    synchronized (this.mUseCasesLock)
    {
      boolean bool = this.mUseCases.remove(paramUseCase);
      return bool;
    }
  }
  
  public void setListener(StateChangeCallback paramStateChangeCallback)
  {
    synchronized (this.mListenerLock)
    {
      this.mStateChangeCallback = paramStateChangeCallback;
      return;
    }
  }
  
  public void start()
  {
    synchronized (this.mListenerLock)
    {
      if (this.mStateChangeCallback != null) {
        this.mStateChangeCallback.onGroupActive(this);
      }
      this.mIsActive = true;
      return;
    }
  }
  
  public void stop()
  {
    synchronized (this.mListenerLock)
    {
      if (this.mStateChangeCallback != null) {
        this.mStateChangeCallback.onGroupInactive(this);
      }
      this.mIsActive = false;
      return;
    }
  }
  
  public static abstract interface StateChangeCallback
  {
    public abstract void onGroupActive(UseCaseGroup paramUseCaseGroup);
    
    public abstract void onGroupInactive(UseCaseGroup paramUseCaseGroup);
  }
}

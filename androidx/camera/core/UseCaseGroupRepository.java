package androidx.camera.core;

import androidx.camera.core.impl.UseCaseGroup;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

final class UseCaseGroupRepository
{
  final List<LifecycleOwner> mActiveLifecycleOwnerList = new ArrayList();
  LifecycleOwner mCurrentActiveLifecycleOwner = null;
  final Map<LifecycleOwner, UseCaseGroupLifecycleController> mLifecycleToUseCaseGroupControllerMap = new HashMap();
  final Object mUseCasesLock = new Object();
  
  UseCaseGroupRepository() {}
  
  private LifecycleObserver createLifecycleObserver()
  {
    new LifecycleObserver()
    {
      @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
      public void onDestroy(LifecycleOwner paramAnonymousLifecycleOwner)
      {
        synchronized (UseCaseGroupRepository.this.mUseCasesLock)
        {
          UseCaseGroupRepository.this.mLifecycleToUseCaseGroupControllerMap.remove(paramAnonymousLifecycleOwner);
          paramAnonymousLifecycleOwner.getLifecycle().removeObserver(this);
          return;
        }
      }
      
      @OnLifecycleEvent(Lifecycle.Event.ON_START)
      public void onStart(LifecycleOwner paramAnonymousLifecycleOwner)
      {
        synchronized (UseCaseGroupRepository.this.mUseCasesLock)
        {
          Iterator localIterator = UseCaseGroupRepository.this.mLifecycleToUseCaseGroupControllerMap.entrySet().iterator();
          while (localIterator.hasNext())
          {
            Object localObject2 = (Map.Entry)localIterator.next();
            if (((Map.Entry)localObject2).getKey() != paramAnonymousLifecycleOwner)
            {
              localObject2 = ((UseCaseGroupLifecycleController)((Map.Entry)localObject2).getValue()).getUseCaseGroup();
              if (((UseCaseGroup)localObject2).isActive()) {
                ((UseCaseGroup)localObject2).stop();
              }
            }
          }
          UseCaseGroupRepository.this.mCurrentActiveLifecycleOwner = paramAnonymousLifecycleOwner;
          UseCaseGroupRepository.this.mActiveLifecycleOwnerList.add(0, UseCaseGroupRepository.this.mCurrentActiveLifecycleOwner);
          return;
        }
      }
      
      @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
      public void onStop(LifecycleOwner paramAnonymousLifecycleOwner)
      {
        synchronized (UseCaseGroupRepository.this.mUseCasesLock)
        {
          UseCaseGroupRepository.this.mActiveLifecycleOwnerList.remove(paramAnonymousLifecycleOwner);
          if (UseCaseGroupRepository.this.mCurrentActiveLifecycleOwner == paramAnonymousLifecycleOwner) {
            if (UseCaseGroupRepository.this.mActiveLifecycleOwnerList.size() > 0)
            {
              UseCaseGroupRepository.this.mCurrentActiveLifecycleOwner = ((LifecycleOwner)UseCaseGroupRepository.this.mActiveLifecycleOwnerList.get(0));
              ((UseCaseGroupLifecycleController)UseCaseGroupRepository.this.mLifecycleToUseCaseGroupControllerMap.get(UseCaseGroupRepository.this.mCurrentActiveLifecycleOwner)).getUseCaseGroup().start();
            }
            else
            {
              UseCaseGroupRepository.this.mCurrentActiveLifecycleOwner = null;
            }
          }
          return;
        }
      }
    };
  }
  
  private UseCaseGroupLifecycleController createUseCaseGroup(LifecycleOwner paramLifecycleOwner)
  {
    if (paramLifecycleOwner.getLifecycle().getCurrentState() != Lifecycle.State.DESTROYED)
    {
      paramLifecycleOwner.getLifecycle().addObserver(createLifecycleObserver());
      UseCaseGroupLifecycleController localUseCaseGroupLifecycleController = new UseCaseGroupLifecycleController(paramLifecycleOwner.getLifecycle());
      synchronized (this.mUseCasesLock)
      {
        this.mLifecycleToUseCaseGroupControllerMap.put(paramLifecycleOwner, localUseCaseGroupLifecycleController);
        return localUseCaseGroupLifecycleController;
      }
    }
    throw new IllegalArgumentException("Trying to create use case group with destroyed lifecycle.");
  }
  
  UseCaseGroupLifecycleController getOrCreateUseCaseGroup(LifecycleOwner paramLifecycleOwner)
  {
    getOrCreateUseCaseGroup(paramLifecycleOwner, new UseCaseGroupSetup()
    {
      public void setup(UseCaseGroup paramAnonymousUseCaseGroup) {}
    });
  }
  
  UseCaseGroupLifecycleController getOrCreateUseCaseGroup(LifecycleOwner paramLifecycleOwner, UseCaseGroupSetup paramUseCaseGroupSetup)
  {
    synchronized (this.mUseCasesLock)
    {
      UseCaseGroupLifecycleController localUseCaseGroupLifecycleController1 = (UseCaseGroupLifecycleController)this.mLifecycleToUseCaseGroupControllerMap.get(paramLifecycleOwner);
      UseCaseGroupLifecycleController localUseCaseGroupLifecycleController2 = localUseCaseGroupLifecycleController1;
      if (localUseCaseGroupLifecycleController1 == null)
      {
        localUseCaseGroupLifecycleController2 = createUseCaseGroup(paramLifecycleOwner);
        paramUseCaseGroupSetup.setup(localUseCaseGroupLifecycleController2.getUseCaseGroup());
      }
      return localUseCaseGroupLifecycleController2;
    }
  }
  
  Collection<UseCaseGroupLifecycleController> getUseCaseGroups()
  {
    synchronized (this.mUseCasesLock)
    {
      Collection localCollection = Collections.unmodifiableCollection(this.mLifecycleToUseCaseGroupControllerMap.values());
      return localCollection;
    }
  }
  
  Map<LifecycleOwner, UseCaseGroupLifecycleController> getUseCasesMap()
  {
    synchronized (this.mUseCasesLock)
    {
      Map localMap = this.mLifecycleToUseCaseGroupControllerMap;
      return localMap;
    }
  }
  
  public static abstract interface UseCaseGroupSetup
  {
    public abstract void setup(UseCaseGroup paramUseCaseGroup);
  }
}

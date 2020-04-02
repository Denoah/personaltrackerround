package androidx.camera.core;

import androidx.camera.core.impl.UseCaseGroup;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import java.util.Collection;
import java.util.Iterator;

final class UseCaseGroupLifecycleController
  implements LifecycleObserver
{
  private final Lifecycle mLifecycle;
  private final UseCaseGroup mUseCaseGroup;
  private final Object mUseCaseGroupLock = new Object();
  
  UseCaseGroupLifecycleController(Lifecycle paramLifecycle)
  {
    this(paramLifecycle, new UseCaseGroup());
  }
  
  UseCaseGroupLifecycleController(Lifecycle paramLifecycle, UseCaseGroup paramUseCaseGroup)
  {
    this.mUseCaseGroup = paramUseCaseGroup;
    this.mLifecycle = paramLifecycle;
    paramLifecycle.addObserver(this);
  }
  
  UseCaseGroup getUseCaseGroup()
  {
    synchronized (this.mUseCaseGroupLock)
    {
      UseCaseGroup localUseCaseGroup = this.mUseCaseGroup;
      return localUseCaseGroup;
    }
  }
  
  void notifyState()
  {
    synchronized (this.mUseCaseGroupLock)
    {
      if (this.mLifecycle.getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
        this.mUseCaseGroup.start();
      }
      Iterator localIterator = this.mUseCaseGroup.getUseCases().iterator();
      while (localIterator.hasNext()) {
        ((UseCase)localIterator.next()).notifyState();
      }
      return;
    }
  }
  
  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  public void onDestroy(LifecycleOwner paramLifecycleOwner)
  {
    synchronized (this.mUseCaseGroupLock)
    {
      this.mUseCaseGroup.clear();
      return;
    }
  }
  
  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  public void onStart(LifecycleOwner arg1)
  {
    synchronized (this.mUseCaseGroupLock)
    {
      this.mUseCaseGroup.start();
      return;
    }
  }
  
  @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
  public void onStop(LifecycleOwner arg1)
  {
    synchronized (this.mUseCaseGroupLock)
    {
      this.mUseCaseGroup.stop();
      return;
    }
  }
  
  void release()
  {
    this.mLifecycle.removeObserver(this);
  }
}

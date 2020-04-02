package androidx.fragment.app;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

class FragmentViewLifecycleOwner
  implements LifecycleOwner
{
  private LifecycleRegistry mLifecycleRegistry = null;
  
  FragmentViewLifecycleOwner() {}
  
  public Lifecycle getLifecycle()
  {
    initialize();
    return this.mLifecycleRegistry;
  }
  
  void handleLifecycleEvent(Lifecycle.Event paramEvent)
  {
    this.mLifecycleRegistry.handleLifecycleEvent(paramEvent);
  }
  
  void initialize()
  {
    if (this.mLifecycleRegistry == null) {
      this.mLifecycleRegistry = new LifecycleRegistry(this);
    }
  }
  
  boolean isInitialized()
  {
    boolean bool;
    if (this.mLifecycleRegistry != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}

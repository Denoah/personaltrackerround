package androidx.activity;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import java.util.ArrayDeque;
import java.util.Iterator;

public final class OnBackPressedDispatcher
{
  private final Runnable mFallbackOnBackPressed;
  final ArrayDeque<OnBackPressedCallback> mOnBackPressedCallbacks = new ArrayDeque();
  
  public OnBackPressedDispatcher()
  {
    this(null);
  }
  
  public OnBackPressedDispatcher(Runnable paramRunnable)
  {
    this.mFallbackOnBackPressed = paramRunnable;
  }
  
  public void addCallback(OnBackPressedCallback paramOnBackPressedCallback)
  {
    addCancellableCallback(paramOnBackPressedCallback);
  }
  
  public void addCallback(LifecycleOwner paramLifecycleOwner, OnBackPressedCallback paramOnBackPressedCallback)
  {
    paramLifecycleOwner = paramLifecycleOwner.getLifecycle();
    if (paramLifecycleOwner.getCurrentState() == Lifecycle.State.DESTROYED) {
      return;
    }
    paramOnBackPressedCallback.addCancellable(new LifecycleOnBackPressedCancellable(paramLifecycleOwner, paramOnBackPressedCallback));
  }
  
  Cancellable addCancellableCallback(OnBackPressedCallback paramOnBackPressedCallback)
  {
    this.mOnBackPressedCallbacks.add(paramOnBackPressedCallback);
    OnBackPressedCancellable localOnBackPressedCancellable = new OnBackPressedCancellable(paramOnBackPressedCallback);
    paramOnBackPressedCallback.addCancellable(localOnBackPressedCancellable);
    return localOnBackPressedCancellable;
  }
  
  public boolean hasEnabledCallbacks()
  {
    Iterator localIterator = this.mOnBackPressedCallbacks.descendingIterator();
    while (localIterator.hasNext()) {
      if (((OnBackPressedCallback)localIterator.next()).isEnabled()) {
        return true;
      }
    }
    return false;
  }
  
  public void onBackPressed()
  {
    Object localObject = this.mOnBackPressedCallbacks.descendingIterator();
    while (((Iterator)localObject).hasNext())
    {
      OnBackPressedCallback localOnBackPressedCallback = (OnBackPressedCallback)((Iterator)localObject).next();
      if (localOnBackPressedCallback.isEnabled())
      {
        localOnBackPressedCallback.handleOnBackPressed();
        return;
      }
    }
    localObject = this.mFallbackOnBackPressed;
    if (localObject != null) {
      ((Runnable)localObject).run();
    }
  }
  
  private class LifecycleOnBackPressedCancellable
    implements LifecycleEventObserver, Cancellable
  {
    private Cancellable mCurrentCancellable;
    private final Lifecycle mLifecycle;
    private final OnBackPressedCallback mOnBackPressedCallback;
    
    LifecycleOnBackPressedCancellable(Lifecycle paramLifecycle, OnBackPressedCallback paramOnBackPressedCallback)
    {
      this.mLifecycle = paramLifecycle;
      this.mOnBackPressedCallback = paramOnBackPressedCallback;
      paramLifecycle.addObserver(this);
    }
    
    public void cancel()
    {
      this.mLifecycle.removeObserver(this);
      this.mOnBackPressedCallback.removeCancellable(this);
      Cancellable localCancellable = this.mCurrentCancellable;
      if (localCancellable != null)
      {
        localCancellable.cancel();
        this.mCurrentCancellable = null;
      }
    }
    
    public void onStateChanged(LifecycleOwner paramLifecycleOwner, Lifecycle.Event paramEvent)
    {
      if (paramEvent == Lifecycle.Event.ON_START)
      {
        this.mCurrentCancellable = OnBackPressedDispatcher.this.addCancellableCallback(this.mOnBackPressedCallback);
      }
      else if (paramEvent == Lifecycle.Event.ON_STOP)
      {
        paramLifecycleOwner = this.mCurrentCancellable;
        if (paramLifecycleOwner != null) {
          paramLifecycleOwner.cancel();
        }
      }
      else if (paramEvent == Lifecycle.Event.ON_DESTROY)
      {
        cancel();
      }
    }
  }
  
  private class OnBackPressedCancellable
    implements Cancellable
  {
    private final OnBackPressedCallback mOnBackPressedCallback;
    
    OnBackPressedCancellable(OnBackPressedCallback paramOnBackPressedCallback)
    {
      this.mOnBackPressedCallback = paramOnBackPressedCallback;
    }
    
    public void cancel()
    {
      OnBackPressedDispatcher.this.mOnBackPressedCallbacks.remove(this.mOnBackPressedCallback);
      this.mOnBackPressedCallback.removeCancellable(this);
    }
  }
}

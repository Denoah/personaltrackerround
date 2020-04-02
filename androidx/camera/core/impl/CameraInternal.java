package androidx.camera.core.impl;

import androidx.camera.core.Camera;
import androidx.camera.core.UseCase;
import androidx.camera.core.UseCase.StateChangeCallback;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Collection;

public abstract interface CameraInternal
  extends Camera, UseCase.StateChangeCallback
{
  public abstract void addOnlineUseCase(Collection<UseCase> paramCollection);
  
  public abstract void close();
  
  public abstract CameraControlInternal getCameraControlInternal();
  
  public abstract CameraInfoInternal getCameraInfoInternal();
  
  public abstract Observable<State> getCameraState();
  
  public abstract void open();
  
  public abstract ListenableFuture<Void> release();
  
  public abstract void removeOnlineUseCase(Collection<UseCase> paramCollection);
  
  public static enum State
  {
    static
    {
      OPENING = new State("OPENING", 1);
      OPEN = new State("OPEN", 2);
      CLOSING = new State("CLOSING", 3);
      CLOSED = new State("CLOSED", 4);
      RELEASING = new State("RELEASING", 5);
      State localState = new State("RELEASED", 6);
      RELEASED = localState;
      $VALUES = new State[] { PENDING_OPEN, OPENING, OPEN, CLOSING, CLOSED, RELEASING, localState };
    }
    
    private State() {}
  }
}

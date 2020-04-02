package androidx.camera.core.internal;

import androidx.camera.core.UseCase.EventCallback;
import androidx.camera.core.impl.Config.Option;

public abstract interface UseCaseEventConfig
{
  public static final Config.Option<UseCase.EventCallback> OPTION_USE_CASE_EVENT_CALLBACK = Config.Option.create("camerax.core.useCaseEventCallback", UseCase.EventCallback.class);
  
  public abstract UseCase.EventCallback getUseCaseEventCallback();
  
  public abstract UseCase.EventCallback getUseCaseEventCallback(UseCase.EventCallback paramEventCallback);
  
  public static abstract interface Builder<B>
  {
    public abstract B setUseCaseEventCallback(UseCase.EventCallback paramEventCallback);
  }
}

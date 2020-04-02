package androidx.camera.core.internal;

import androidx.camera.core.impl.Config.Option;

public abstract interface TargetConfig<T>
{
  public static final Config.Option<Class<?>> OPTION_TARGET_CLASS = Config.Option.create("camerax.core.target.class", Class.class);
  public static final Config.Option<String> OPTION_TARGET_NAME = Config.Option.create("camerax.core.target.name", String.class);
  
  public abstract Class<T> getTargetClass();
  
  public abstract Class<T> getTargetClass(Class<T> paramClass);
  
  public abstract String getTargetName();
  
  public abstract String getTargetName(String paramString);
  
  public static abstract interface Builder<T, B>
  {
    public abstract B setTargetClass(Class<T> paramClass);
    
    public abstract B setTargetName(String paramString);
  }
}

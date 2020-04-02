package androidx.camera.core.impl;

import java.util.Set;

public abstract interface Config
{
  public abstract boolean containsOption(Option<?> paramOption);
  
  public abstract void findOptions(String paramString, OptionMatcher paramOptionMatcher);
  
  public abstract Set<Option<?>> listOptions();
  
  public abstract <ValueT> ValueT retrieveOption(Option<ValueT> paramOption);
  
  public abstract <ValueT> ValueT retrieveOption(Option<ValueT> paramOption, ValueT paramValueT);
  
  public static abstract class Option<T>
  {
    Option() {}
    
    public static <T> Option<T> create(String paramString, Class<?> paramClass)
    {
      return create(paramString, paramClass, null);
    }
    
    public static <T> Option<T> create(String paramString, Class<?> paramClass, Object paramObject)
    {
      return new AutoValue_Config_Option(paramString, paramClass, paramObject);
    }
    
    public abstract String getId();
    
    public abstract Object getToken();
    
    public abstract Class<T> getValueClass();
  }
  
  public static abstract interface OptionMatcher
  {
    public abstract boolean onOptionMatched(Config.Option<?> paramOption);
  }
}

package androidx.camera.core.impl;

public abstract interface MutableConfig
  extends Config
{
  public abstract <ValueT> void insertOption(Config.Option<ValueT> paramOption, ValueT paramValueT);
  
  public abstract <ValueT> ValueT removeOption(Config.Option<ValueT> paramOption);
}

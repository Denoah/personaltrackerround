package androidx.camera.core.impl;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public final class MutableOptionsBundle
  extends OptionsBundle
  implements MutableConfig
{
  private static final Comparator<Config.Option<?>> ID_COMPARE = new Comparator()
  {
    public int compare(Config.Option<?> paramAnonymousOption1, Config.Option<?> paramAnonymousOption2)
    {
      return paramAnonymousOption1.getId().compareTo(paramAnonymousOption2.getId());
    }
  };
  
  private MutableOptionsBundle(TreeMap<Config.Option<?>, Object> paramTreeMap)
  {
    super(paramTreeMap);
  }
  
  public static MutableOptionsBundle create()
  {
    return new MutableOptionsBundle(new TreeMap(ID_COMPARE));
  }
  
  public static MutableOptionsBundle from(Config paramConfig)
  {
    TreeMap localTreeMap = new TreeMap(ID_COMPARE);
    Iterator localIterator = paramConfig.listOptions().iterator();
    while (localIterator.hasNext())
    {
      Config.Option localOption = (Config.Option)localIterator.next();
      localTreeMap.put(localOption, paramConfig.retrieveOption(localOption));
    }
    return new MutableOptionsBundle(localTreeMap);
  }
  
  public <ValueT> void insertOption(Config.Option<ValueT> paramOption, ValueT paramValueT)
  {
    this.mOptions.put(paramOption, paramValueT);
  }
  
  public <ValueT> ValueT removeOption(Config.Option<ValueT> paramOption)
  {
    return this.mOptions.remove(paramOption);
  }
}

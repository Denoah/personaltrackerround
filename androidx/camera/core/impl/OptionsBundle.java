package androidx.camera.core.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class OptionsBundle
  implements Config
{
  private static final OptionsBundle EMPTY_BUNDLE = new OptionsBundle(new TreeMap(new Comparator()
  {
    public int compare(Config.Option<?> paramAnonymousOption1, Config.Option<?> paramAnonymousOption2)
    {
      return paramAnonymousOption1.getId().compareTo(paramAnonymousOption2.getId());
    }
  }));
  protected final TreeMap<Config.Option<?>, Object> mOptions;
  
  OptionsBundle(TreeMap<Config.Option<?>, Object> paramTreeMap)
  {
    this.mOptions = paramTreeMap;
  }
  
  public static OptionsBundle emptyBundle()
  {
    return EMPTY_BUNDLE;
  }
  
  public static OptionsBundle from(Config paramConfig)
  {
    if (OptionsBundle.class.equals(paramConfig.getClass())) {
      return (OptionsBundle)paramConfig;
    }
    TreeMap localTreeMap = new TreeMap(new Comparator()
    {
      public int compare(Config.Option<?> paramAnonymousOption1, Config.Option<?> paramAnonymousOption2)
      {
        return paramAnonymousOption1.getId().compareTo(paramAnonymousOption2.getId());
      }
    });
    Iterator localIterator = paramConfig.listOptions().iterator();
    while (localIterator.hasNext())
    {
      Config.Option localOption = (Config.Option)localIterator.next();
      localTreeMap.put(localOption, paramConfig.retrieveOption(localOption));
    }
    return new OptionsBundle(localTreeMap);
  }
  
  public boolean containsOption(Config.Option<?> paramOption)
  {
    return this.mOptions.containsKey(paramOption);
  }
  
  public void findOptions(String paramString, Config.OptionMatcher paramOptionMatcher)
  {
    Object localObject = Config.Option.create(paramString, Void.class);
    localObject = this.mOptions.tailMap(localObject).entrySet().iterator();
    Map.Entry localEntry;
    do
    {
      if (!((Iterator)localObject).hasNext()) {
        break;
      }
      localEntry = (Map.Entry)((Iterator)localObject).next();
    } while ((((Config.Option)localEntry.getKey()).getId().startsWith(paramString)) && (paramOptionMatcher.onOptionMatched((Config.Option)localEntry.getKey())));
  }
  
  public Set<Config.Option<?>> listOptions()
  {
    return Collections.unmodifiableSet(this.mOptions.keySet());
  }
  
  public <ValueT> ValueT retrieveOption(Config.Option<ValueT> paramOption)
  {
    if (this.mOptions.containsKey(paramOption)) {
      return this.mOptions.get(paramOption);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Option does not exist: ");
    localStringBuilder.append(paramOption);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public <ValueT> ValueT retrieveOption(Config.Option<ValueT> paramOption, ValueT paramValueT)
  {
    if (this.mOptions.containsKey(paramOption)) {
      paramValueT = this.mOptions.get(paramOption);
    }
    return paramValueT;
  }
}

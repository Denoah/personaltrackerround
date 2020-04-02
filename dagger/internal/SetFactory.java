package dagger.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.inject.Provider;

public final class SetFactory<T>
  implements Factory<Set<T>>
{
  private static final Factory<Set<Object>> EMPTY_FACTORY = InstanceFactory.create(Collections.emptySet());
  private final List<Provider<Collection<T>>> collectionProviders;
  private final List<Provider<T>> individualProviders;
  
  private SetFactory(List<Provider<T>> paramList, List<Provider<Collection<T>>> paramList1)
  {
    this.individualProviders = paramList;
    this.collectionProviders = paramList1;
  }
  
  public static <T> Builder<T> builder(int paramInt1, int paramInt2)
  {
    return new Builder(paramInt1, paramInt2, null);
  }
  
  public static <T> Factory<Set<T>> empty()
  {
    return EMPTY_FACTORY;
  }
  
  public Set<T> get()
  {
    int i = this.individualProviders.size();
    ArrayList localArrayList = new ArrayList(this.collectionProviders.size());
    int j = this.collectionProviders.size();
    int k = 0;
    Object localObject;
    for (int m = 0; m < j; m++)
    {
      localObject = (Collection)((Provider)this.collectionProviders.get(m)).get();
      i += ((Collection)localObject).size();
      localArrayList.add(localObject);
    }
    HashSet localHashSet = DaggerCollections.newHashSetWithExpectedSize(i);
    i = this.individualProviders.size();
    for (m = 0; m < i; m++) {
      localHashSet.add(Preconditions.checkNotNull(((Provider)this.individualProviders.get(m)).get()));
    }
    i = localArrayList.size();
    for (m = k; m < i; m++)
    {
      localObject = ((Collection)localArrayList.get(m)).iterator();
      while (((Iterator)localObject).hasNext()) {
        localHashSet.add(Preconditions.checkNotNull(((Iterator)localObject).next()));
      }
    }
    return Collections.unmodifiableSet(localHashSet);
  }
  
  public static final class Builder<T>
  {
    private final List<Provider<Collection<T>>> collectionProviders;
    private final List<Provider<T>> individualProviders;
    
    private Builder(int paramInt1, int paramInt2)
    {
      this.individualProviders = DaggerCollections.presizedList(paramInt1);
      this.collectionProviders = DaggerCollections.presizedList(paramInt2);
    }
    
    public Builder<T> addCollectionProvider(Provider<? extends Collection<? extends T>> paramProvider)
    {
      this.collectionProviders.add(paramProvider);
      return this;
    }
    
    public Builder<T> addProvider(Provider<? extends T> paramProvider)
    {
      this.individualProviders.add(paramProvider);
      return this;
    }
    
    public SetFactory<T> build()
    {
      return new SetFactory(this.individualProviders, this.collectionProviders, null);
    }
  }
}

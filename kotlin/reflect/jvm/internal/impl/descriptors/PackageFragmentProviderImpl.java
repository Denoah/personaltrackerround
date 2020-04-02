package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.sequences.SequencesKt;

public final class PackageFragmentProviderImpl
  implements PackageFragmentProvider
{
  private final Collection<PackageFragmentDescriptor> packageFragments;
  
  public PackageFragmentProviderImpl(Collection<? extends PackageFragmentDescriptor> paramCollection)
  {
    this.packageFragments = paramCollection;
  }
  
  public List<PackageFragmentDescriptor> getPackageFragments(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    Object localObject = (Iterable)this.packageFragments;
    Collection localCollection = (Collection)new ArrayList();
    Iterator localIterator = ((Iterable)localObject).iterator();
    while (localIterator.hasNext())
    {
      localObject = localIterator.next();
      if (Intrinsics.areEqual(((PackageFragmentDescriptor)localObject).getFqName(), paramFqName)) {
        localCollection.add(localObject);
      }
    }
    return (List)localCollection;
  }
  
  public Collection<FqName> getSubPackagesOf(FqName paramFqName, Function1<? super Name, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "nameFilter");
    (Collection)SequencesKt.toList(SequencesKt.filter(SequencesKt.map(CollectionsKt.asSequence((Iterable)this.packageFragments), (Function1)getSubPackagesOf.1.INSTANCE), (Function1)new Lambda(paramFqName)
    {
      public final boolean invoke(FqName paramAnonymousFqName)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFqName, "it");
        boolean bool;
        if ((!paramAnonymousFqName.isRoot()) && (Intrinsics.areEqual(paramAnonymousFqName.parent(), this.$fqName))) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
    }));
  }
}

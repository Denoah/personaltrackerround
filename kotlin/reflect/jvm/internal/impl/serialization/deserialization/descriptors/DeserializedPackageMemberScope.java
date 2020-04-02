package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.incremental.UtilsKt;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Package;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationComponents;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationContext;

public class DeserializedPackageMemberScope
  extends DeserializedMemberScope
{
  private final PackageFragmentDescriptor packageDescriptor;
  private final FqName packageFqName;
  
  public DeserializedPackageMemberScope(PackageFragmentDescriptor paramPackageFragmentDescriptor, ProtoBuf.Package paramPackage, NameResolver paramNameResolver, BinaryVersion paramBinaryVersion, DeserializedContainerSource paramDeserializedContainerSource, DeserializationComponents paramDeserializationComponents, Function0<? extends Collection<Name>> paramFunction0)
  {
    super(paramNameResolver, paramBinaryVersion, paramDeserializedContainerSource, (Collection)paramPackage, paramFunction0);
    this.packageDescriptor = paramPackageFragmentDescriptor;
    this.packageFqName = paramPackageFragmentDescriptor.getFqName();
  }
  
  protected void addEnumEntryDescriptors(Collection<DeclarationDescriptor> paramCollection, Function1<? super Name, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "result");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "nameFilter");
  }
  
  protected ClassId createClassId(Name paramName)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    return new ClassId(this.packageFqName, paramName);
  }
  
  public ClassifierDescriptor getContributedClassifier(Name paramName, LookupLocation paramLookupLocation)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    Intrinsics.checkParameterIsNotNull(paramLookupLocation, "location");
    recordLookup(paramName, paramLookupLocation);
    return super.getContributedClassifier(paramName, paramLookupLocation);
  }
  
  public List<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter paramDescriptorKindFilter, Function1<? super Name, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramDescriptorKindFilter, "kindFilter");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "nameFilter");
    paramDescriptorKindFilter = computeDescriptors(paramDescriptorKindFilter, paramFunction1, (LookupLocation)NoLookupLocation.WHEN_GET_ALL_DESCRIPTORS);
    Object localObject = getC().getComponents().getFictitiousClassDescriptorFactories();
    paramFunction1 = (Collection)new ArrayList();
    localObject = ((Iterable)localObject).iterator();
    while (((Iterator)localObject).hasNext()) {
      CollectionsKt.addAll(paramFunction1, (Iterable)((ClassDescriptorFactory)((Iterator)localObject).next()).getAllContributedClassesIfPossible(this.packageFqName));
    }
    return CollectionsKt.plus(paramDescriptorKindFilter, (Iterable)paramFunction1);
  }
  
  protected Set<Name> getNonDeclaredFunctionNames()
  {
    return SetsKt.emptySet();
  }
  
  protected Set<Name> getNonDeclaredVariableNames()
  {
    return SetsKt.emptySet();
  }
  
  protected boolean hasClass(Name paramName)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    boolean bool1 = super.hasClass(paramName);
    boolean bool2 = true;
    boolean bool3 = bool2;
    if (!bool1)
    {
      Object localObject = getC().getComponents().getFictitiousClassDescriptorFactories();
      if (((localObject instanceof Collection)) && (((Collection)localObject).isEmpty())) {}
      do
      {
        while (!((Iterator)localObject).hasNext())
        {
          i = 0;
          break;
          localObject = ((Iterable)localObject).iterator();
        }
      } while (!((ClassDescriptorFactory)((Iterator)localObject).next()).shouldCreateClass(this.packageFqName, paramName));
      int i = 1;
      if (i != 0) {
        bool3 = bool2;
      } else {
        bool3 = false;
      }
    }
    return bool3;
  }
  
  public void recordLookup(Name paramName, LookupLocation paramLookupLocation)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    Intrinsics.checkParameterIsNotNull(paramLookupLocation, "location");
    UtilsKt.record(getC().getComponents().getLookupTracker(), paramLookupLocation, this.packageDescriptor, paramName);
  }
}

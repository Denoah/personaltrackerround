package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.Collection;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;

public abstract interface ResolutionScope
{
  public abstract ClassifierDescriptor getContributedClassifier(Name paramName, LookupLocation paramLookupLocation);
  
  public abstract Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter paramDescriptorKindFilter, Function1<? super Name, Boolean> paramFunction1);
  
  public abstract Collection<? extends FunctionDescriptor> getContributedFunctions(Name paramName, LookupLocation paramLookupLocation);
  
  public static final class DefaultImpls
  {
    public static void recordLookup(ResolutionScope paramResolutionScope, Name paramName, LookupLocation paramLookupLocation)
    {
      Intrinsics.checkParameterIsNotNull(paramName, "name");
      Intrinsics.checkParameterIsNotNull(paramLookupLocation, "location");
      paramResolutionScope.getContributedFunctions(paramName, paramLookupLocation);
    }
  }
}

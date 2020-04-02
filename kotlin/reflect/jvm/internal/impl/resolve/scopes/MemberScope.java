package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.Collection;
import java.util.Set;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;

public abstract interface MemberScope
  extends ResolutionScope
{
  public static final Companion Companion = Companion.$$INSTANCE;
  
  public abstract Collection<? extends SimpleFunctionDescriptor> getContributedFunctions(Name paramName, LookupLocation paramLookupLocation);
  
  public abstract Collection<? extends PropertyDescriptor> getContributedVariables(Name paramName, LookupLocation paramLookupLocation);
  
  public abstract Set<Name> getFunctionNames();
  
  public abstract Set<Name> getVariableNames();
  
  public static final class Companion
  {
    private static final Function1<Name, Boolean> ALL_NAME_FILTER = (Function1)ALL_NAME_FILTER.1.INSTANCE;
    
    private Companion() {}
    
    public final Function1<Name, Boolean> getALL_NAME_FILTER()
    {
      return ALL_NAME_FILTER;
    }
  }
  
  public static final class DefaultImpls
  {
    public static void recordLookup(MemberScope paramMemberScope, Name paramName, LookupLocation paramLookupLocation)
    {
      Intrinsics.checkParameterIsNotNull(paramName, "name");
      Intrinsics.checkParameterIsNotNull(paramLookupLocation, "location");
      ResolutionScope.DefaultImpls.recordLookup((ResolutionScope)paramMemberScope, paramName, paramLookupLocation);
    }
  }
  
  public static final class Empty
    extends MemberScopeImpl
  {
    public static final Empty INSTANCE = new Empty();
    
    private Empty() {}
    
    public Set<Name> getFunctionNames()
    {
      return SetsKt.emptySet();
    }
    
    public Set<Name> getVariableNames()
    {
      return SetsKt.emptySet();
    }
  }
}

package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.load.java.components.DescriptorResolverUtils;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.UtilKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.JavaResolverComponents;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMember;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.NewKotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.utils.DFS;
import kotlin.reflect.jvm.internal.impl.utils.DFS.AbstractNodeHandler;
import kotlin.reflect.jvm.internal.impl.utils.DFS.Neighbors;
import kotlin.reflect.jvm.internal.impl.utils.DFS.NodeHandler;
import kotlin.sequences.SequencesKt;

public final class LazyJavaStaticClassScope
  extends LazyJavaStaticScope
{
  private final JavaClass jClass;
  private final LazyJavaClassDescriptor ownerDescriptor;
  
  public LazyJavaStaticClassScope(LazyJavaResolverContext paramLazyJavaResolverContext, JavaClass paramJavaClass, LazyJavaClassDescriptor paramLazyJavaClassDescriptor)
  {
    super(paramLazyJavaResolverContext);
    this.jClass = paramJavaClass;
    this.ownerDescriptor = paramLazyJavaClassDescriptor;
  }
  
  private final <R> Set<R> flatMapJavaStaticSupertypesScopes(ClassDescriptor paramClassDescriptor, final Set<R> paramSet, final Function1<? super MemberScope, ? extends Collection<? extends R>> paramFunction1)
  {
    DFS.dfs((Collection)CollectionsKt.listOf(paramClassDescriptor), (DFS.Neighbors)flatMapJavaStaticSupertypesScopes.1.INSTANCE, (DFS.NodeHandler)new DFS.AbstractNodeHandler()
    {
      public boolean beforeChildren(ClassDescriptor paramAnonymousClassDescriptor)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousClassDescriptor, "current");
        if (paramAnonymousClassDescriptor == this.$root) {
          return true;
        }
        paramAnonymousClassDescriptor = paramAnonymousClassDescriptor.getStaticScope();
        Intrinsics.checkExpressionValueIsNotNull(paramAnonymousClassDescriptor, "current.staticScope");
        if ((paramAnonymousClassDescriptor instanceof LazyJavaStaticScope))
        {
          paramSet.addAll((Collection)paramFunction1.invoke(paramAnonymousClassDescriptor));
          return false;
        }
        return true;
      }
      
      public void result() {}
    });
    return paramSet;
  }
  
  private final PropertyDescriptor getRealOriginal(PropertyDescriptor paramPropertyDescriptor)
  {
    Object localObject = paramPropertyDescriptor.getKind();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "this.kind");
    if (((CallableMemberDescriptor.Kind)localObject).isReal()) {
      return paramPropertyDescriptor;
    }
    paramPropertyDescriptor = paramPropertyDescriptor.getOverriddenDescriptors();
    Intrinsics.checkExpressionValueIsNotNull(paramPropertyDescriptor, "this.overriddenDescriptors");
    localObject = (Iterable)paramPropertyDescriptor;
    paramPropertyDescriptor = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject, 10));
    localObject = ((Iterable)localObject).iterator();
    while (((Iterator)localObject).hasNext())
    {
      PropertyDescriptor localPropertyDescriptor = (PropertyDescriptor)((Iterator)localObject).next();
      Intrinsics.checkExpressionValueIsNotNull(localPropertyDescriptor, "it");
      paramPropertyDescriptor.add(getRealOriginal(localPropertyDescriptor));
    }
    return (PropertyDescriptor)CollectionsKt.single(CollectionsKt.distinct((Iterable)paramPropertyDescriptor));
  }
  
  private final Set<SimpleFunctionDescriptor> getStaticFunctionsFromJavaSuperClasses(Name paramName, ClassDescriptor paramClassDescriptor)
  {
    paramClassDescriptor = UtilKt.getParentJavaStaticClassScope(paramClassDescriptor);
    if (paramClassDescriptor != null) {
      return CollectionsKt.toSet((Iterable)paramClassDescriptor.getContributedFunctions(paramName, (LookupLocation)NoLookupLocation.WHEN_GET_SUPER_MEMBERS));
    }
    return SetsKt.emptySet();
  }
  
  protected Set<Name> computeClassNames(DescriptorKindFilter paramDescriptorKindFilter, Function1<? super Name, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramDescriptorKindFilter, "kindFilter");
    return SetsKt.emptySet();
  }
  
  protected Set<Name> computeFunctionNames(DescriptorKindFilter paramDescriptorKindFilter, Function1<? super Name, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramDescriptorKindFilter, "kindFilter");
    paramFunction1 = CollectionsKt.toMutableSet((Iterable)((DeclaredMemberIndex)getDeclaredMemberIndex().invoke()).getMethodNames());
    paramDescriptorKindFilter = UtilKt.getParentJavaStaticClassScope((ClassDescriptor)getOwnerDescriptor());
    if (paramDescriptorKindFilter != null) {
      paramDescriptorKindFilter = paramDescriptorKindFilter.getFunctionNames();
    } else {
      paramDescriptorKindFilter = null;
    }
    if (paramDescriptorKindFilter == null) {
      paramDescriptorKindFilter = SetsKt.emptySet();
    }
    paramFunction1.addAll((Collection)paramDescriptorKindFilter);
    if (this.jClass.isEnum()) {
      paramFunction1.addAll((Collection)CollectionsKt.listOf(new Name[] { DescriptorUtils.ENUM_VALUE_OF, DescriptorUtils.ENUM_VALUES }));
    }
    return paramFunction1;
  }
  
  protected ClassDeclaredMemberIndex computeMemberIndex()
  {
    return new ClassDeclaredMemberIndex(this.jClass, (Function1)computeMemberIndex.1.INSTANCE);
  }
  
  protected void computeNonDeclaredFunctions(Collection<SimpleFunctionDescriptor> paramCollection, Name paramName)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "result");
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    Collection localCollection = DescriptorResolverUtils.resolveOverridesForStaticMembers(paramName, (Collection)getStaticFunctionsFromJavaSuperClasses(paramName, (ClassDescriptor)getOwnerDescriptor()), paramCollection, (ClassDescriptor)getOwnerDescriptor(), getC().getComponents().getErrorReporter(), getC().getComponents().getKotlinTypeChecker().getOverridingUtil());
    Intrinsics.checkExpressionValueIsNotNull(localCollection, "resolveOverridesForStati….overridingUtil\n        )");
    paramCollection.addAll(localCollection);
    if (this.jClass.isEnum()) {
      if (Intrinsics.areEqual(paramName, DescriptorUtils.ENUM_VALUE_OF))
      {
        paramName = DescriptorFactory.createEnumValueOfMethod((ClassDescriptor)getOwnerDescriptor());
        Intrinsics.checkExpressionValueIsNotNull(paramName, "createEnumValueOfMethod(ownerDescriptor)");
        paramCollection.add(paramName);
      }
      else if (Intrinsics.areEqual(paramName, DescriptorUtils.ENUM_VALUES))
      {
        paramName = DescriptorFactory.createEnumValuesMethod((ClassDescriptor)getOwnerDescriptor());
        Intrinsics.checkExpressionValueIsNotNull(paramName, "createEnumValuesMethod(ownerDescriptor)");
        paramCollection.add(paramName);
      }
    }
  }
  
  protected void computeNonDeclaredProperties(Name paramName, Collection<PropertyDescriptor> paramCollection)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    Intrinsics.checkParameterIsNotNull(paramCollection, "result");
    Object localObject1 = flatMapJavaStaticSupertypesScopes((ClassDescriptor)getOwnerDescriptor(), (Set)new LinkedHashSet(), (Function1)new Lambda(paramName)
    {
      public final Collection<? extends PropertyDescriptor> invoke(MemberScope paramAnonymousMemberScope)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousMemberScope, "it");
        return paramAnonymousMemberScope.getContributedVariables(this.$name, (LookupLocation)NoLookupLocation.WHEN_GET_SUPER_MEMBERS);
      }
    });
    if ((paramCollection.isEmpty() ^ true))
    {
      paramName = DescriptorResolverUtils.resolveOverridesForStaticMembers(paramName, (Collection)localObject1, paramCollection, (ClassDescriptor)getOwnerDescriptor(), getC().getComponents().getErrorReporter(), getC().getComponents().getKotlinTypeChecker().getOverridingUtil());
      Intrinsics.checkExpressionValueIsNotNull(paramName, "resolveOverridesForStati…ingUtil\n                )");
      paramCollection.addAll(paramName);
    }
    else
    {
      localObject1 = (Iterable)localObject1;
      Map localMap = (Map)new LinkedHashMap();
      Iterator localIterator = ((Iterable)localObject1).iterator();
      while (localIterator.hasNext())
      {
        Object localObject2 = localIterator.next();
        PropertyDescriptor localPropertyDescriptor = getRealOriginal((PropertyDescriptor)localObject2);
        localObject3 = localMap.get(localPropertyDescriptor);
        localObject1 = localObject3;
        if (localObject3 == null)
        {
          localObject1 = new ArrayList();
          localMap.put(localPropertyDescriptor, localObject1);
        }
        ((List)localObject1).add(localObject2);
      }
      localObject1 = (Collection)new ArrayList();
      Object localObject3 = localMap.entrySet().iterator();
      while (((Iterator)localObject3).hasNext()) {
        CollectionsKt.addAll((Collection)localObject1, (Iterable)DescriptorResolverUtils.resolveOverridesForStaticMembers(paramName, (Collection)((Map.Entry)((Iterator)localObject3).next()).getValue(), paramCollection, (ClassDescriptor)getOwnerDescriptor(), getC().getComponents().getErrorReporter(), getC().getComponents().getKotlinTypeChecker().getOverridingUtil()));
      }
      paramCollection.addAll((Collection)localObject1);
    }
  }
  
  protected Set<Name> computePropertyNames(DescriptorKindFilter paramDescriptorKindFilter, Function1<? super Name, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramDescriptorKindFilter, "kindFilter");
    paramDescriptorKindFilter = CollectionsKt.toMutableSet((Iterable)((DeclaredMemberIndex)getDeclaredMemberIndex().invoke()).getFieldNames());
    flatMapJavaStaticSupertypesScopes((ClassDescriptor)getOwnerDescriptor(), paramDescriptorKindFilter, (Function1)computePropertyNames.1.1.INSTANCE);
    return paramDescriptorKindFilter;
  }
  
  public ClassifierDescriptor getContributedClassifier(Name paramName, LookupLocation paramLookupLocation)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    Intrinsics.checkParameterIsNotNull(paramLookupLocation, "location");
    return null;
  }
  
  protected LazyJavaClassDescriptor getOwnerDescriptor()
  {
    return this.ownerDescriptor;
  }
}

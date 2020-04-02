package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Queue;
import java.util.ServiceLoader;
import java.util.Set;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithVisibility;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyAccessorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyAccessorDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker.TypeConstructorEquality;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeCheckerImpl;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner.Default;
import kotlin.reflect.jvm.internal.impl.utils.SmartSet;

public class OverridingUtil
{
  public static final OverridingUtil DEFAULT = new OverridingUtil(DEFAULT_TYPE_CONSTRUCTOR_EQUALITY, KotlinTypeRefiner.Default.INSTANCE);
  private static final KotlinTypeChecker.TypeConstructorEquality DEFAULT_TYPE_CONSTRUCTOR_EQUALITY;
  private static final List<ExternalOverridabilityCondition> EXTERNAL_CONDITIONS = CollectionsKt.toList(ServiceLoader.load(ExternalOverridabilityCondition.class, ExternalOverridabilityCondition.class.getClassLoader()));
  private final KotlinTypeChecker.TypeConstructorEquality equalityAxioms;
  private final KotlinTypeRefiner kotlinTypeRefiner;
  
  static
  {
    DEFAULT_TYPE_CONSTRUCTOR_EQUALITY = new KotlinTypeChecker.TypeConstructorEquality()
    {
      public boolean equals(TypeConstructor paramAnonymousTypeConstructor1, TypeConstructor paramAnonymousTypeConstructor2)
      {
        if (paramAnonymousTypeConstructor1 == null) {
          $$$reportNull$$$0(0);
        }
        if (paramAnonymousTypeConstructor2 == null) {
          $$$reportNull$$$0(1);
        }
        return paramAnonymousTypeConstructor1.equals(paramAnonymousTypeConstructor2);
      }
    };
  }
  
  private OverridingUtil(KotlinTypeChecker.TypeConstructorEquality paramTypeConstructorEquality, KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    this.equalityAxioms = paramTypeConstructorEquality;
    this.kotlinTypeRefiner = paramKotlinTypeRefiner;
  }
  
  private static boolean allHasSameContainingDeclaration(Collection<CallableMemberDescriptor> paramCollection)
  {
    if (paramCollection == null) {
      $$$reportNull$$$0(59);
    }
    if (paramCollection.size() < 2) {
      return true;
    }
    CollectionsKt.all(paramCollection, new Function1()
    {
      public Boolean invoke(CallableMemberDescriptor paramAnonymousCallableMemberDescriptor)
      {
        boolean bool;
        if (paramAnonymousCallableMemberDescriptor.getContainingDeclaration() == this.val$containingDeclaration) {
          bool = true;
        } else {
          bool = false;
        }
        return Boolean.valueOf(bool);
      }
    });
  }
  
  private boolean areTypeParametersEquivalent(TypeParameterDescriptor paramTypeParameterDescriptor1, TypeParameterDescriptor paramTypeParameterDescriptor2, KotlinTypeChecker paramKotlinTypeChecker)
  {
    if (paramTypeParameterDescriptor1 == null) {
      $$$reportNull$$$0(45);
    }
    if (paramTypeParameterDescriptor2 == null) {
      $$$reportNull$$$0(46);
    }
    if (paramKotlinTypeChecker == null) {
      $$$reportNull$$$0(47);
    }
    Object localObject = paramTypeParameterDescriptor1.getUpperBounds();
    paramTypeParameterDescriptor1 = new ArrayList(paramTypeParameterDescriptor2.getUpperBounds());
    if (((List)localObject).size() != paramTypeParameterDescriptor1.size()) {
      return false;
    }
    paramTypeParameterDescriptor2 = ((List)localObject).iterator();
    while (paramTypeParameterDescriptor2.hasNext())
    {
      KotlinType localKotlinType = (KotlinType)paramTypeParameterDescriptor2.next();
      localObject = paramTypeParameterDescriptor1.listIterator();
      for (;;)
      {
        if (((ListIterator)localObject).hasNext()) {
          if (areTypesEquivalent(localKotlinType, (KotlinType)((ListIterator)localObject).next(), paramKotlinTypeChecker))
          {
            ((ListIterator)localObject).remove();
            break;
          }
        }
      }
      return false;
    }
    return true;
  }
  
  private boolean areTypesEquivalent(KotlinType paramKotlinType1, KotlinType paramKotlinType2, KotlinTypeChecker paramKotlinTypeChecker)
  {
    if (paramKotlinType1 == null) {
      $$$reportNull$$$0(42);
    }
    if (paramKotlinType2 == null) {
      $$$reportNull$$$0(43);
    }
    if (paramKotlinTypeChecker == null) {
      $$$reportNull$$$0(44);
    }
    int i;
    if ((KotlinTypeKt.isError(paramKotlinType1)) && (KotlinTypeKt.isError(paramKotlinType2))) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return true;
    }
    return paramKotlinTypeChecker.equalTypes(this.kotlinTypeRefiner.refineType(paramKotlinType1), this.kotlinTypeRefiner.refineType(paramKotlinType2));
  }
  
  private static OverrideCompatibilityInfo checkReceiverAndParameterCount(CallableDescriptor paramCallableDescriptor1, CallableDescriptor paramCallableDescriptor2)
  {
    ReceiverParameterDescriptor localReceiverParameterDescriptor = paramCallableDescriptor1.getExtensionReceiverParameter();
    int i = 1;
    int j;
    if (localReceiverParameterDescriptor == null) {
      j = 1;
    } else {
      j = 0;
    }
    if (paramCallableDescriptor2.getExtensionReceiverParameter() != null) {
      i = 0;
    }
    if (j != i) {
      return OverrideCompatibilityInfo.incompatible("Receiver presence mismatch");
    }
    if (paramCallableDescriptor1.getValueParameters().size() != paramCallableDescriptor2.getValueParameters().size()) {
      return OverrideCompatibilityInfo.incompatible("Value parameter number mismatch");
    }
    return null;
  }
  
  private static void collectOverriddenDeclarations(CallableMemberDescriptor paramCallableMemberDescriptor, Set<CallableMemberDescriptor> paramSet)
  {
    if (paramCallableMemberDescriptor == null) {
      $$$reportNull$$$0(13);
    }
    if (paramSet == null) {
      $$$reportNull$$$0(14);
    }
    if (paramCallableMemberDescriptor.getKind().isReal())
    {
      paramSet.add(paramCallableMemberDescriptor);
    }
    else
    {
      if (paramCallableMemberDescriptor.getOverriddenDescriptors().isEmpty()) {
        break label93;
      }
      paramCallableMemberDescriptor = paramCallableMemberDescriptor.getOverriddenDescriptors().iterator();
      while (paramCallableMemberDescriptor.hasNext()) {
        collectOverriddenDeclarations((CallableMemberDescriptor)paramCallableMemberDescriptor.next(), paramSet);
      }
    }
    return;
    label93:
    paramSet = new StringBuilder();
    paramSet.append("No overridden descriptors found for (fake override) ");
    paramSet.append(paramCallableMemberDescriptor);
    throw new IllegalStateException(paramSet.toString());
  }
  
  private static List<KotlinType> compiledValueParameters(CallableDescriptor paramCallableDescriptor)
  {
    ReceiverParameterDescriptor localReceiverParameterDescriptor = paramCallableDescriptor.getExtensionReceiverParameter();
    ArrayList localArrayList = new ArrayList();
    if (localReceiverParameterDescriptor != null) {
      localArrayList.add(localReceiverParameterDescriptor.getType());
    }
    paramCallableDescriptor = paramCallableDescriptor.getValueParameters().iterator();
    while (paramCallableDescriptor.hasNext()) {
      localArrayList.add(((ValueParameterDescriptor)paramCallableDescriptor.next()).getType());
    }
    return localArrayList;
  }
  
  private static Visibility computeVisibilityToInherit(CallableMemberDescriptor paramCallableMemberDescriptor)
  {
    if (paramCallableMemberDescriptor == null) {
      $$$reportNull$$$0(103);
    }
    Object localObject = paramCallableMemberDescriptor.getOverriddenDescriptors();
    Visibility localVisibility = findMaxVisibility((Collection)localObject);
    if (localVisibility == null) {
      return null;
    }
    if (paramCallableMemberDescriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE)
    {
      localObject = ((Collection)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        paramCallableMemberDescriptor = (CallableMemberDescriptor)((Iterator)localObject).next();
        if ((paramCallableMemberDescriptor.getModality() != Modality.ABSTRACT) && (!paramCallableMemberDescriptor.getVisibility().equals(localVisibility))) {
          return null;
        }
      }
      return localVisibility;
    }
    return localVisibility.normalize();
  }
  
  private static void createAndBindFakeOverride(Collection<CallableMemberDescriptor> paramCollection, ClassDescriptor paramClassDescriptor, OverridingStrategy paramOverridingStrategy)
  {
    if (paramCollection == null) {
      $$$reportNull$$$0(80);
    }
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(81);
    }
    if (paramOverridingStrategy == null) {
      $$$reportNull$$$0(82);
    }
    Object localObject = filterVisibleFakeOverrides(paramClassDescriptor, paramCollection);
    boolean bool = ((Collection)localObject).isEmpty();
    if (!bool) {
      paramCollection = (Collection<CallableMemberDescriptor>)localObject;
    }
    Modality localModality = determineModalityForFakeOverride(paramCollection, paramClassDescriptor);
    if (bool) {
      localObject = Visibilities.INVISIBLE_FAKE;
    } else {
      localObject = Visibilities.INHERITED;
    }
    paramClassDescriptor = ((CallableMemberDescriptor)selectMostSpecificMember(paramCollection, new Function1()
    {
      public CallableMemberDescriptor invoke(CallableMemberDescriptor paramAnonymousCallableMemberDescriptor)
      {
        return paramAnonymousCallableMemberDescriptor;
      }
    })).copy(paramClassDescriptor, localModality, (Visibility)localObject, CallableMemberDescriptor.Kind.FAKE_OVERRIDE, false);
    paramOverridingStrategy.setOverriddenDescriptors(paramClassDescriptor, paramCollection);
    paramOverridingStrategy.addFakeOverride(paramClassDescriptor);
  }
  
  private static void createAndBindFakeOverrides(ClassDescriptor paramClassDescriptor, Collection<CallableMemberDescriptor> paramCollection, OverridingStrategy paramOverridingStrategy)
  {
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(60);
    }
    if (paramCollection == null) {
      $$$reportNull$$$0(61);
    }
    if (paramOverridingStrategy == null) {
      $$$reportNull$$$0(62);
    }
    if (allHasSameContainingDeclaration(paramCollection))
    {
      paramCollection = paramCollection.iterator();
      while (paramCollection.hasNext()) {
        createAndBindFakeOverride(Collections.singleton((CallableMemberDescriptor)paramCollection.next()), paramClassDescriptor, paramOverridingStrategy);
      }
      return;
    }
    paramCollection = new LinkedList(paramCollection);
    while (!paramCollection.isEmpty()) {
      createAndBindFakeOverride(extractMembersOverridableInBothWays(VisibilityUtilKt.findMemberWithMaxVisibility(paramCollection), paramCollection, paramOverridingStrategy), paramClassDescriptor, paramOverridingStrategy);
    }
  }
  
  private KotlinTypeChecker createTypeChecker(List<TypeParameterDescriptor> paramList1, List<TypeParameterDescriptor> paramList2)
  {
    if (paramList1 == null) {
      $$$reportNull$$$0(38);
    }
    if (paramList2 == null) {
      $$$reportNull$$$0(39);
    }
    if (paramList1.isEmpty())
    {
      paramList1 = KotlinTypeCheckerImpl.withAxioms(this.equalityAxioms);
      if (paramList1 == null) {
        $$$reportNull$$$0(40);
      }
      return paramList1;
    }
    final HashMap localHashMap = new HashMap();
    for (int i = 0; i < paramList1.size(); i++) {
      localHashMap.put(((TypeParameterDescriptor)paramList1.get(i)).getTypeConstructor(), ((TypeParameterDescriptor)paramList2.get(i)).getTypeConstructor());
    }
    paramList1 = KotlinTypeCheckerImpl.withAxioms(new KotlinTypeChecker.TypeConstructorEquality()
    {
      public boolean equals(TypeConstructor paramAnonymousTypeConstructor1, TypeConstructor paramAnonymousTypeConstructor2)
      {
        boolean bool1 = false;
        if (paramAnonymousTypeConstructor1 == null) {
          $$$reportNull$$$0(0);
        }
        if (paramAnonymousTypeConstructor2 == null) {
          $$$reportNull$$$0(1);
        }
        if (OverridingUtil.this.equalityAxioms.equals(paramAnonymousTypeConstructor1, paramAnonymousTypeConstructor2)) {
          return true;
        }
        TypeConstructor localTypeConstructor1 = (TypeConstructor)localHashMap.get(paramAnonymousTypeConstructor1);
        TypeConstructor localTypeConstructor2 = (TypeConstructor)localHashMap.get(paramAnonymousTypeConstructor2);
        boolean bool2;
        if ((localTypeConstructor1 == null) || (!localTypeConstructor1.equals(paramAnonymousTypeConstructor2)))
        {
          bool2 = bool1;
          if (localTypeConstructor2 != null)
          {
            bool2 = bool1;
            if (!localTypeConstructor2.equals(paramAnonymousTypeConstructor1)) {}
          }
        }
        else
        {
          bool2 = true;
        }
        return bool2;
      }
    });
    if (paramList1 == null) {
      $$$reportNull$$$0(41);
    }
    return paramList1;
  }
  
  public static OverridingUtil createWithEqualityAxioms(KotlinTypeChecker.TypeConstructorEquality paramTypeConstructorEquality)
  {
    if (paramTypeConstructorEquality == null) {
      $$$reportNull$$$0(0);
    }
    return new OverridingUtil(paramTypeConstructorEquality, KotlinTypeRefiner.Default.INSTANCE);
  }
  
  public static OverridingUtil createWithTypeRefiner(KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    if (paramKotlinTypeRefiner == null) {
      $$$reportNull$$$0(1);
    }
    return new OverridingUtil(DEFAULT_TYPE_CONSTRUCTOR_EQUALITY, paramKotlinTypeRefiner);
  }
  
  private static Modality determineModalityForFakeOverride(Collection<CallableMemberDescriptor> paramCollection, ClassDescriptor paramClassDescriptor)
  {
    if (paramCollection == null) {
      $$$reportNull$$$0(83);
    }
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(84);
    }
    Iterator localIterator = paramCollection.iterator();
    boolean bool1 = false;
    int i = 0;
    int j = i;
    while (localIterator.hasNext())
    {
      localObject = (CallableMemberDescriptor)localIterator.next();
      int k = 9.$SwitchMap$org$jetbrains$kotlin$descriptors$Modality[localObject.getModality().ordinal()];
      if (k != 1)
      {
        if (k != 2)
        {
          if (k != 3)
          {
            if (k == 4) {
              j = 1;
            }
          }
          else {
            i = 1;
          }
        }
        else
        {
          paramCollection = new StringBuilder();
          paramCollection.append("Member cannot have SEALED modality: ");
          paramCollection.append(localObject);
          throw new IllegalStateException(paramCollection.toString());
        }
      }
      else
      {
        paramCollection = Modality.FINAL;
        if (paramCollection == null) {
          $$$reportNull$$$0(85);
        }
        return paramCollection;
      }
    }
    boolean bool2 = bool1;
    if (paramClassDescriptor.isExpect())
    {
      bool2 = bool1;
      if (paramClassDescriptor.getModality() != Modality.ABSTRACT)
      {
        bool2 = bool1;
        if (paramClassDescriptor.getModality() != Modality.SEALED) {
          bool2 = true;
        }
      }
    }
    if ((i != 0) && (j == 0))
    {
      paramCollection = Modality.OPEN;
      if (paramCollection == null) {
        $$$reportNull$$$0(86);
      }
      return paramCollection;
    }
    if ((i == 0) && (j != 0))
    {
      if (bool2) {
        paramCollection = paramClassDescriptor.getModality();
      } else {
        paramCollection = Modality.ABSTRACT;
      }
      if (paramCollection == null) {
        $$$reportNull$$$0(87);
      }
      return paramCollection;
    }
    Object localObject = new HashSet();
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext()) {
      ((Set)localObject).addAll(getOverriddenDeclarations((CallableMemberDescriptor)paramCollection.next()));
    }
    return getMinimalModality(filterOutOverridden((Set)localObject), bool2, paramClassDescriptor.getModality());
  }
  
  private Collection<CallableMemberDescriptor> extractAndBindOverridesForMember(CallableMemberDescriptor paramCallableMemberDescriptor, Collection<? extends CallableMemberDescriptor> paramCollection, ClassDescriptor paramClassDescriptor, OverridingStrategy paramOverridingStrategy)
  {
    if (paramCallableMemberDescriptor == null) {
      $$$reportNull$$$0(55);
    }
    if (paramCollection == null) {
      $$$reportNull$$$0(56);
    }
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(57);
    }
    if (paramOverridingStrategy == null) {
      $$$reportNull$$$0(58);
    }
    ArrayList localArrayList = new ArrayList(paramCollection.size());
    SmartSet localSmartSet = SmartSet.create();
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext())
    {
      CallableMemberDescriptor localCallableMemberDescriptor = (CallableMemberDescriptor)paramCollection.next();
      OverridingUtil.OverrideCompatibilityInfo.Result localResult = isOverridableBy(localCallableMemberDescriptor, paramCallableMemberDescriptor, paramClassDescriptor).getResult();
      boolean bool = isVisibleForOverride(paramCallableMemberDescriptor, localCallableMemberDescriptor);
      int i = 9.$SwitchMap$org$jetbrains$kotlin$resolve$OverridingUtil$OverrideCompatibilityInfo$Result[localResult.ordinal()];
      if (i != 1)
      {
        if (i == 2)
        {
          if (bool) {
            paramOverridingStrategy.overrideConflict(localCallableMemberDescriptor, paramCallableMemberDescriptor);
          }
          localArrayList.add(localCallableMemberDescriptor);
        }
      }
      else
      {
        if (bool) {
          localSmartSet.add(localCallableMemberDescriptor);
        }
        localArrayList.add(localCallableMemberDescriptor);
      }
    }
    paramOverridingStrategy.setOverriddenDescriptors(paramCallableMemberDescriptor, localSmartSet);
    return localArrayList;
  }
  
  public static <H> Collection<H> extractMembersOverridableInBothWays(H paramH, Collection<H> paramCollection, Function1<H, CallableDescriptor> paramFunction1, Function1<H, Unit> paramFunction11)
  {
    if (paramH == null) {
      $$$reportNull$$$0(94);
    }
    if (paramCollection == null) {
      $$$reportNull$$$0(95);
    }
    if (paramFunction1 == null) {
      $$$reportNull$$$0(96);
    }
    if (paramFunction11 == null) {
      $$$reportNull$$$0(97);
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramH);
    CallableDescriptor localCallableDescriptor = (CallableDescriptor)paramFunction1.invoke(paramH);
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      paramCollection = localIterator.next();
      Object localObject = (CallableDescriptor)paramFunction1.invoke(paramCollection);
      if (paramH == paramCollection)
      {
        localIterator.remove();
      }
      else
      {
        localObject = getBothWaysOverridability(localCallableDescriptor, (CallableDescriptor)localObject);
        if (localObject == OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE)
        {
          localArrayList.add(paramCollection);
          localIterator.remove();
        }
        else if (localObject == OverridingUtil.OverrideCompatibilityInfo.Result.CONFLICT)
        {
          paramFunction11.invoke(paramCollection);
          localIterator.remove();
        }
      }
    }
    return localArrayList;
  }
  
  private static Collection<CallableMemberDescriptor> extractMembersOverridableInBothWays(final CallableMemberDescriptor paramCallableMemberDescriptor, Queue<CallableMemberDescriptor> paramQueue, OverridingStrategy paramOverridingStrategy)
  {
    if (paramCallableMemberDescriptor == null) {
      $$$reportNull$$$0(99);
    }
    if (paramQueue == null) {
      $$$reportNull$$$0(100);
    }
    if (paramOverridingStrategy == null) {
      $$$reportNull$$$0(101);
    }
    extractMembersOverridableInBothWays(paramCallableMemberDescriptor, paramQueue, new Function1()new Function1
    {
      public CallableDescriptor invoke(CallableMemberDescriptor paramAnonymousCallableMemberDescriptor)
      {
        return paramAnonymousCallableMemberDescriptor;
      }
    }, new Function1()
    {
      public Unit invoke(CallableMemberDescriptor paramAnonymousCallableMemberDescriptor)
      {
        this.val$strategy.inheritanceConflict(paramCallableMemberDescriptor, paramAnonymousCallableMemberDescriptor);
        return Unit.INSTANCE;
      }
    });
  }
  
  public static <D extends CallableDescriptor> Set<D> filterOutOverridden(Set<D> paramSet)
  {
    if (paramSet == null) {
      $$$reportNull$$$0(4);
    }
    boolean bool;
    if ((!paramSet.isEmpty()) && (DescriptorUtilsKt.isTypeRefinementEnabled(DescriptorUtilsKt.getModule((DeclarationDescriptor)paramSet.iterator().next())))) {
      bool = true;
    } else {
      bool = false;
    }
    filterOverrides(paramSet, bool, new Function2()
    {
      public Pair<CallableDescriptor, CallableDescriptor> invoke(D paramAnonymousD1, D paramAnonymousD2)
      {
        return new Pair(paramAnonymousD1, paramAnonymousD2);
      }
    });
  }
  
  public static <D> Set<D> filterOverrides(Set<D> paramSet, boolean paramBoolean, Function2<? super D, ? super D, Pair<CallableDescriptor, CallableDescriptor>> paramFunction2)
  {
    if (paramSet == null) {
      $$$reportNull$$$0(5);
    }
    if (paramFunction2 == null) {
      $$$reportNull$$$0(6);
    }
    if (paramSet.size() <= 1)
    {
      if (paramSet == null) {
        $$$reportNull$$$0(7);
      }
      return paramSet;
    }
    LinkedHashSet localLinkedHashSet = new LinkedHashSet();
    Iterator localIterator1 = paramSet.iterator();
    while (localIterator1.hasNext())
    {
      Object localObject1 = localIterator1.next();
      Iterator localIterator2 = localLinkedHashSet.iterator();
      for (;;)
      {
        if (localIterator2.hasNext())
        {
          Object localObject2 = (Pair)paramFunction2.invoke(localObject1, localIterator2.next());
          paramSet = (CallableDescriptor)((Pair)localObject2).component1();
          localObject2 = (CallableDescriptor)((Pair)localObject2).component2();
          if (overrides(paramSet, (CallableDescriptor)localObject2, paramBoolean)) {
            localIterator2.remove();
          } else if (overrides((CallableDescriptor)localObject2, paramSet, paramBoolean)) {
            break;
          }
        }
      }
      localLinkedHashSet.add(localObject1);
    }
    return localLinkedHashSet;
  }
  
  private static Collection<CallableMemberDescriptor> filterVisibleFakeOverrides(ClassDescriptor paramClassDescriptor, Collection<CallableMemberDescriptor> paramCollection)
  {
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(91);
    }
    if (paramCollection == null) {
      $$$reportNull$$$0(92);
    }
    paramClassDescriptor = CollectionsKt.filter(paramCollection, new Function1()
    {
      public Boolean invoke(CallableMemberDescriptor paramAnonymousCallableMemberDescriptor)
      {
        boolean bool;
        if ((!Visibilities.isPrivate(paramAnonymousCallableMemberDescriptor.getVisibility())) && (Visibilities.isVisibleIgnoringReceiver(paramAnonymousCallableMemberDescriptor, this.val$current))) {
          bool = true;
        } else {
          bool = false;
        }
        return Boolean.valueOf(bool);
      }
    });
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(93);
    }
    return paramClassDescriptor;
  }
  
  public static Visibility findMaxVisibility(Collection<? extends CallableMemberDescriptor> paramCollection)
  {
    if (paramCollection == null) {
      $$$reportNull$$$0(104);
    }
    if (paramCollection.isEmpty()) {
      return Visibilities.DEFAULT_VISIBILITY;
    }
    Iterator localIterator = paramCollection.iterator();
    Object localObject1 = null;
    label31:
    if (localIterator.hasNext())
    {
      localObject2 = ((CallableMemberDescriptor)localIterator.next()).getVisibility();
      if (localObject1 == null) {}
      for (;;)
      {
        localObject1 = localObject2;
        break label31;
        Integer localInteger = Visibilities.compare((Visibility)localObject2, localObject1);
        if (localInteger == null) {
          break;
        }
        if (localInteger.intValue() <= 0) {
          break label31;
        }
      }
    }
    if (localObject1 == null) {
      return null;
    }
    Object localObject2 = paramCollection.iterator();
    while (((Iterator)localObject2).hasNext())
    {
      paramCollection = Visibilities.compare(localObject1, ((CallableMemberDescriptor)((Iterator)localObject2).next()).getVisibility());
      if ((paramCollection == null) || (paramCollection.intValue() < 0)) {
        return null;
      }
    }
    return localObject1;
  }
  
  public static OverrideCompatibilityInfo getBasicOverridabilityProblem(CallableDescriptor paramCallableDescriptor1, CallableDescriptor paramCallableDescriptor2)
  {
    if (paramCallableDescriptor1 == null) {
      $$$reportNull$$$0(36);
    }
    if (paramCallableDescriptor2 == null) {
      $$$reportNull$$$0(37);
    }
    boolean bool1 = paramCallableDescriptor1 instanceof FunctionDescriptor;
    boolean bool2;
    if ((!bool1) || ((paramCallableDescriptor2 instanceof FunctionDescriptor)))
    {
      bool2 = paramCallableDescriptor1 instanceof PropertyDescriptor;
      if ((!bool2) || ((paramCallableDescriptor2 instanceof PropertyDescriptor))) {}
    }
    else
    {
      return OverrideCompatibilityInfo.incompatible("Member kind mismatch");
    }
    if ((!bool1) && (!bool2))
    {
      paramCallableDescriptor2 = new StringBuilder();
      paramCallableDescriptor2.append("This type of CallableDescriptor cannot be checked for overridability: ");
      paramCallableDescriptor2.append(paramCallableDescriptor1);
      throw new IllegalArgumentException(paramCallableDescriptor2.toString());
    }
    if (!paramCallableDescriptor1.getName().equals(paramCallableDescriptor2.getName())) {
      return OverrideCompatibilityInfo.incompatible("Name mismatch");
    }
    paramCallableDescriptor1 = checkReceiverAndParameterCount(paramCallableDescriptor1, paramCallableDescriptor2);
    if (paramCallableDescriptor1 != null) {
      return paramCallableDescriptor1;
    }
    return null;
  }
  
  public static OverridingUtil.OverrideCompatibilityInfo.Result getBothWaysOverridability(CallableDescriptor paramCallableDescriptor1, CallableDescriptor paramCallableDescriptor2)
  {
    OverridingUtil.OverrideCompatibilityInfo.Result localResult = DEFAULT.isOverridableBy(paramCallableDescriptor2, paramCallableDescriptor1, null).getResult();
    paramCallableDescriptor1 = DEFAULT.isOverridableBy(paramCallableDescriptor1, paramCallableDescriptor2, null).getResult();
    if ((localResult == OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE) && (paramCallableDescriptor1 == OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE)) {
      paramCallableDescriptor1 = OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE;
    } else if ((localResult != OverridingUtil.OverrideCompatibilityInfo.Result.CONFLICT) && (paramCallableDescriptor1 != OverridingUtil.OverrideCompatibilityInfo.Result.CONFLICT)) {
      paramCallableDescriptor1 = OverridingUtil.OverrideCompatibilityInfo.Result.INCOMPATIBLE;
    } else {
      paramCallableDescriptor1 = OverridingUtil.OverrideCompatibilityInfo.Result.CONFLICT;
    }
    return paramCallableDescriptor1;
  }
  
  private static Modality getMinimalModality(Collection<CallableMemberDescriptor> paramCollection, boolean paramBoolean, Modality paramModality)
  {
    if (paramCollection == null) {
      $$$reportNull$$$0(88);
    }
    if (paramModality == null) {
      $$$reportNull$$$0(89);
    }
    Object localObject = Modality.ABSTRACT;
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      paramCollection = (CallableMemberDescriptor)localIterator.next();
      if ((paramBoolean) && (paramCollection.getModality() == Modality.ABSTRACT)) {
        paramCollection = paramModality;
      } else {
        paramCollection = paramCollection.getModality();
      }
      if (paramCollection.compareTo((Enum)localObject) < 0) {
        localObject = paramCollection;
      }
    }
    if (localObject == null) {
      $$$reportNull$$$0(90);
    }
    return localObject;
  }
  
  public static Set<CallableMemberDescriptor> getOverriddenDeclarations(CallableMemberDescriptor paramCallableMemberDescriptor)
  {
    if (paramCallableMemberDescriptor == null) {
      $$$reportNull$$$0(11);
    }
    LinkedHashSet localLinkedHashSet = new LinkedHashSet();
    collectOverriddenDeclarations(paramCallableMemberDescriptor, localLinkedHashSet);
    return localLinkedHashSet;
  }
  
  private static boolean isAccessorMoreSpecific(PropertyAccessorDescriptor paramPropertyAccessorDescriptor1, PropertyAccessorDescriptor paramPropertyAccessorDescriptor2)
  {
    if ((paramPropertyAccessorDescriptor1 != null) && (paramPropertyAccessorDescriptor2 != null)) {
      return isVisibilityMoreSpecific(paramPropertyAccessorDescriptor1, paramPropertyAccessorDescriptor2);
    }
    return true;
  }
  
  public static boolean isMoreSpecific(CallableDescriptor paramCallableDescriptor1, CallableDescriptor paramCallableDescriptor2)
  {
    if (paramCallableDescriptor1 == null) {
      $$$reportNull$$$0(63);
    }
    if (paramCallableDescriptor2 == null) {
      $$$reportNull$$$0(64);
    }
    KotlinType localKotlinType1 = paramCallableDescriptor1.getReturnType();
    KotlinType localKotlinType2 = paramCallableDescriptor2.getReturnType();
    boolean bool1 = isVisibilityMoreSpecific(paramCallableDescriptor1, paramCallableDescriptor2);
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    if ((paramCallableDescriptor1 instanceof FunctionDescriptor)) {
      return isReturnTypeMoreSpecific(paramCallableDescriptor1, localKotlinType1, paramCallableDescriptor2, localKotlinType2);
    }
    if ((paramCallableDescriptor1 instanceof PropertyDescriptor))
    {
      PropertyDescriptor localPropertyDescriptor1 = (PropertyDescriptor)paramCallableDescriptor1;
      PropertyDescriptor localPropertyDescriptor2 = (PropertyDescriptor)paramCallableDescriptor2;
      if (!isAccessorMoreSpecific(localPropertyDescriptor1.getSetter(), localPropertyDescriptor2.getSetter())) {
        return false;
      }
      if ((localPropertyDescriptor1.isVar()) && (localPropertyDescriptor2.isVar())) {
        return DEFAULT.createTypeChecker(paramCallableDescriptor1.getTypeParameters(), paramCallableDescriptor2.getTypeParameters()).equalTypes(localKotlinType1, localKotlinType2);
      }
      if (!localPropertyDescriptor1.isVar())
      {
        bool1 = bool2;
        if (localPropertyDescriptor2.isVar()) {}
      }
      else
      {
        bool1 = bool2;
        if (isReturnTypeMoreSpecific(paramCallableDescriptor1, localKotlinType1, paramCallableDescriptor2, localKotlinType2)) {
          bool1 = true;
        }
      }
      return bool1;
    }
    paramCallableDescriptor2 = new StringBuilder();
    paramCallableDescriptor2.append("Unexpected callable: ");
    paramCallableDescriptor2.append(paramCallableDescriptor1.getClass());
    throw new IllegalArgumentException(paramCallableDescriptor2.toString());
  }
  
  private static boolean isMoreSpecificThenAllOf(CallableDescriptor paramCallableDescriptor, Collection<CallableDescriptor> paramCollection)
  {
    if (paramCallableDescriptor == null) {
      $$$reportNull$$$0(67);
    }
    if (paramCollection == null) {
      $$$reportNull$$$0(68);
    }
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext()) {
      if (!isMoreSpecific(paramCallableDescriptor, (CallableDescriptor)paramCollection.next())) {
        return false;
      }
    }
    return true;
  }
  
  private static boolean isReturnTypeMoreSpecific(CallableDescriptor paramCallableDescriptor1, KotlinType paramKotlinType1, CallableDescriptor paramCallableDescriptor2, KotlinType paramKotlinType2)
  {
    if (paramCallableDescriptor1 == null) {
      $$$reportNull$$$0(69);
    }
    if (paramKotlinType1 == null) {
      $$$reportNull$$$0(70);
    }
    if (paramCallableDescriptor2 == null) {
      $$$reportNull$$$0(71);
    }
    if (paramKotlinType2 == null) {
      $$$reportNull$$$0(72);
    }
    return DEFAULT.createTypeChecker(paramCallableDescriptor1.getTypeParameters(), paramCallableDescriptor2.getTypeParameters()).isSubtypeOf(paramKotlinType1, paramKotlinType2);
  }
  
  private static boolean isVisibilityMoreSpecific(DeclarationDescriptorWithVisibility paramDeclarationDescriptorWithVisibility1, DeclarationDescriptorWithVisibility paramDeclarationDescriptorWithVisibility2)
  {
    if (paramDeclarationDescriptorWithVisibility1 == null) {
      $$$reportNull$$$0(65);
    }
    if (paramDeclarationDescriptorWithVisibility2 == null) {
      $$$reportNull$$$0(66);
    }
    paramDeclarationDescriptorWithVisibility1 = Visibilities.compare(paramDeclarationDescriptorWithVisibility1.getVisibility(), paramDeclarationDescriptorWithVisibility2.getVisibility());
    boolean bool;
    if ((paramDeclarationDescriptorWithVisibility1 != null) && (paramDeclarationDescriptorWithVisibility1.intValue() < 0)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static boolean isVisibleForOverride(MemberDescriptor paramMemberDescriptor1, MemberDescriptor paramMemberDescriptor2)
  {
    if (paramMemberDescriptor1 == null) {
      $$$reportNull$$$0(53);
    }
    if (paramMemberDescriptor2 == null) {
      $$$reportNull$$$0(54);
    }
    boolean bool;
    if ((!Visibilities.isPrivate(paramMemberDescriptor2.getVisibility())) && (Visibilities.isVisibleIgnoringReceiver(paramMemberDescriptor2, paramMemberDescriptor1))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static <D extends CallableDescriptor> boolean overrides(D paramD1, D paramD2, boolean paramBoolean)
  {
    if (paramD1 == null) {
      $$$reportNull$$$0(9);
    }
    if (paramD2 == null) {
      $$$reportNull$$$0(10);
    }
    if ((!paramD1.equals(paramD2)) && (DescriptorEquivalenceForOverrides.INSTANCE.areEquivalent(paramD1.getOriginal(), paramD2.getOriginal(), paramBoolean))) {
      return true;
    }
    paramD2 = paramD2.getOriginal();
    Iterator localIterator = DescriptorUtils.getAllOverriddenDescriptors(paramD1).iterator();
    while (localIterator.hasNext())
    {
      paramD1 = (CallableDescriptor)localIterator.next();
      if (DescriptorEquivalenceForOverrides.INSTANCE.areEquivalent(paramD2, paramD1, paramBoolean)) {
        return true;
      }
    }
    return false;
  }
  
  public static void resolveUnknownVisibilityForMember(CallableMemberDescriptor paramCallableMemberDescriptor, Function1<CallableMemberDescriptor, Unit> paramFunction1)
  {
    if (paramCallableMemberDescriptor == null) {
      $$$reportNull$$$0(102);
    }
    Object localObject1 = paramCallableMemberDescriptor.getOverriddenDescriptors().iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (CallableMemberDescriptor)((Iterator)localObject1).next();
      if (((CallableMemberDescriptor)localObject2).getVisibility() == Visibilities.INHERITED) {
        resolveUnknownVisibilityForMember((CallableMemberDescriptor)localObject2, paramFunction1);
      }
    }
    if (paramCallableMemberDescriptor.getVisibility() != Visibilities.INHERITED) {
      return;
    }
    Object localObject2 = computeVisibilityToInherit(paramCallableMemberDescriptor);
    if (localObject2 == null)
    {
      if (paramFunction1 != null) {
        paramFunction1.invoke(paramCallableMemberDescriptor);
      }
      localObject1 = Visibilities.PUBLIC;
    }
    else
    {
      localObject1 = localObject2;
    }
    if ((paramCallableMemberDescriptor instanceof PropertyDescriptorImpl))
    {
      ((PropertyDescriptorImpl)paramCallableMemberDescriptor).setVisibility((Visibility)localObject1);
      localObject1 = ((PropertyDescriptor)paramCallableMemberDescriptor).getAccessors().iterator();
      while (((Iterator)localObject1).hasNext())
      {
        PropertyAccessorDescriptor localPropertyAccessorDescriptor = (PropertyAccessorDescriptor)((Iterator)localObject1).next();
        if (localObject2 == null) {
          paramCallableMemberDescriptor = null;
        } else {
          paramCallableMemberDescriptor = paramFunction1;
        }
        resolveUnknownVisibilityForMember(localPropertyAccessorDescriptor, paramCallableMemberDescriptor);
      }
    }
    if ((paramCallableMemberDescriptor instanceof FunctionDescriptorImpl))
    {
      ((FunctionDescriptorImpl)paramCallableMemberDescriptor).setVisibility((Visibility)localObject1);
    }
    else
    {
      paramCallableMemberDescriptor = (PropertyAccessorDescriptorImpl)paramCallableMemberDescriptor;
      paramCallableMemberDescriptor.setVisibility((Visibility)localObject1);
      if (localObject1 != paramCallableMemberDescriptor.getCorrespondingProperty().getVisibility()) {
        paramCallableMemberDescriptor.setDefault(false);
      }
    }
  }
  
  public static <H> H selectMostSpecificMember(Collection<H> paramCollection, Function1<H, CallableDescriptor> paramFunction1)
  {
    if (paramCollection == null) {
      $$$reportNull$$$0(73);
    }
    if (paramFunction1 == null) {
      $$$reportNull$$$0(74);
    }
    if (paramCollection.size() == 1)
    {
      paramCollection = CollectionsKt.first(paramCollection);
      if (paramCollection == null) {
        $$$reportNull$$$0(75);
      }
      return paramCollection;
    }
    ArrayList localArrayList = new ArrayList(2);
    Object localObject1 = CollectionsKt.map(paramCollection, paramFunction1);
    Object localObject2 = CollectionsKt.first(paramCollection);
    CallableDescriptor localCallableDescriptor1 = (CallableDescriptor)paramFunction1.invoke(localObject2);
    Iterator localIterator = paramCollection.iterator();
    for (paramCollection = localObject2; localIterator.hasNext(); paramCollection = localObject2)
    {
      label89:
      localObject2 = localIterator.next();
      CallableDescriptor localCallableDescriptor2 = (CallableDescriptor)paramFunction1.invoke(localObject2);
      if (isMoreSpecificThenAllOf(localCallableDescriptor2, (Collection)localObject1)) {
        localArrayList.add(localObject2);
      }
      if ((!isMoreSpecific(localCallableDescriptor2, localCallableDescriptor1)) || (isMoreSpecific(localCallableDescriptor1, localCallableDescriptor2))) {
        break label89;
      }
    }
    if (localArrayList.isEmpty())
    {
      if (paramCollection == null) {
        $$$reportNull$$$0(76);
      }
      return paramCollection;
    }
    if (localArrayList.size() == 1)
    {
      paramCollection = CollectionsKt.first(localArrayList);
      if (paramCollection == null) {
        $$$reportNull$$$0(77);
      }
      return paramCollection;
    }
    localObject2 = null;
    localObject1 = localArrayList.iterator();
    do
    {
      paramCollection = localObject2;
      if (!((Iterator)localObject1).hasNext()) {
        break;
      }
      paramCollection = ((Iterator)localObject1).next();
    } while (FlexibleTypesKt.isFlexible(((CallableDescriptor)paramFunction1.invoke(paramCollection)).getReturnType()));
    if (paramCollection != null)
    {
      if (paramCollection == null) {
        $$$reportNull$$$0(78);
      }
      return paramCollection;
    }
    paramCollection = CollectionsKt.first(localArrayList);
    if (paramCollection == null) {
      $$$reportNull$$$0(79);
    }
    return paramCollection;
  }
  
  public void generateOverridesInFunctionGroup(Name paramName, Collection<? extends CallableMemberDescriptor> paramCollection1, Collection<? extends CallableMemberDescriptor> paramCollection2, ClassDescriptor paramClassDescriptor, OverridingStrategy paramOverridingStrategy)
  {
    if (paramName == null) {
      $$$reportNull$$$0(48);
    }
    if (paramCollection1 == null) {
      $$$reportNull$$$0(49);
    }
    if (paramCollection2 == null) {
      $$$reportNull$$$0(50);
    }
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(51);
    }
    if (paramOverridingStrategy == null) {
      $$$reportNull$$$0(52);
    }
    paramName = new LinkedHashSet(paramCollection1);
    paramCollection2 = paramCollection2.iterator();
    while (paramCollection2.hasNext()) {
      paramName.removeAll(extractAndBindOverridesForMember((CallableMemberDescriptor)paramCollection2.next(), paramCollection1, paramClassDescriptor, paramOverridingStrategy));
    }
    createAndBindFakeOverrides(paramClassDescriptor, paramName, paramOverridingStrategy);
  }
  
  public OverrideCompatibilityInfo isOverridableBy(CallableDescriptor paramCallableDescriptor1, CallableDescriptor paramCallableDescriptor2, ClassDescriptor paramClassDescriptor)
  {
    if (paramCallableDescriptor1 == null) {
      $$$reportNull$$$0(15);
    }
    if (paramCallableDescriptor2 == null) {
      $$$reportNull$$$0(16);
    }
    paramCallableDescriptor1 = isOverridableBy(paramCallableDescriptor1, paramCallableDescriptor2, paramClassDescriptor, false);
    if (paramCallableDescriptor1 == null) {
      $$$reportNull$$$0(17);
    }
    return paramCallableDescriptor1;
  }
  
  public OverrideCompatibilityInfo isOverridableBy(CallableDescriptor paramCallableDescriptor1, CallableDescriptor paramCallableDescriptor2, ClassDescriptor paramClassDescriptor, boolean paramBoolean)
  {
    if (paramCallableDescriptor1 == null) {
      $$$reportNull$$$0(18);
    }
    if (paramCallableDescriptor2 == null) {
      $$$reportNull$$$0(19);
    }
    Object localObject1 = isOverridableByWithoutExternalConditions(paramCallableDescriptor1, paramCallableDescriptor2, paramBoolean);
    int i;
    if (((OverrideCompatibilityInfo)localObject1).getResult() == OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE) {
      i = 1;
    } else {
      i = 0;
    }
    Object localObject2 = EXTERNAL_CONDITIONS.iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject3 = (ExternalOverridabilityCondition)((Iterator)localObject2).next();
      if ((((ExternalOverridabilityCondition)localObject3).getContract() != ExternalOverridabilityCondition.Contract.CONFLICTS_ONLY) && ((i == 0) || (((ExternalOverridabilityCondition)localObject3).getContract() != ExternalOverridabilityCondition.Contract.SUCCESS_ONLY)))
      {
        localObject3 = ((ExternalOverridabilityCondition)localObject3).isOverridable(paramCallableDescriptor1, paramCallableDescriptor2, paramClassDescriptor);
        int j = 9.$SwitchMap$org$jetbrains$kotlin$resolve$ExternalOverridabilityCondition$Result[localObject3.ordinal()];
        if (j != 1)
        {
          if (j != 2)
          {
            if (j == 3)
            {
              paramCallableDescriptor1 = OverrideCompatibilityInfo.incompatible("External condition");
              if (paramCallableDescriptor1 == null) {
                $$$reportNull$$$0(21);
              }
              return paramCallableDescriptor1;
            }
          }
          else
          {
            paramCallableDescriptor1 = OverrideCompatibilityInfo.conflict("External condition failed");
            if (paramCallableDescriptor1 == null) {
              $$$reportNull$$$0(20);
            }
            return paramCallableDescriptor1;
          }
        }
        else {
          i = 1;
        }
      }
    }
    if (i == 0)
    {
      if (localObject1 == null) {
        $$$reportNull$$$0(22);
      }
      return localObject1;
    }
    Object localObject3 = EXTERNAL_CONDITIONS.iterator();
    while (((Iterator)localObject3).hasNext())
    {
      localObject2 = (ExternalOverridabilityCondition)((Iterator)localObject3).next();
      if (((ExternalOverridabilityCondition)localObject2).getContract() == ExternalOverridabilityCondition.Contract.CONFLICTS_ONLY)
      {
        localObject1 = ((ExternalOverridabilityCondition)localObject2).isOverridable(paramCallableDescriptor1, paramCallableDescriptor2, paramClassDescriptor);
        i = 9.$SwitchMap$org$jetbrains$kotlin$resolve$ExternalOverridabilityCondition$Result[localObject1.ordinal()];
        if (i != 1)
        {
          if (i != 2)
          {
            if (i == 3)
            {
              paramCallableDescriptor1 = OverrideCompatibilityInfo.incompatible("External condition");
              if (paramCallableDescriptor1 == null) {
                $$$reportNull$$$0(24);
              }
              return paramCallableDescriptor1;
            }
          }
          else
          {
            paramCallableDescriptor1 = OverrideCompatibilityInfo.conflict("External condition failed");
            if (paramCallableDescriptor1 == null) {
              $$$reportNull$$$0(23);
            }
            return paramCallableDescriptor1;
          }
        }
        else
        {
          paramCallableDescriptor1 = new StringBuilder();
          paramCallableDescriptor1.append("Contract violation in ");
          paramCallableDescriptor1.append(localObject2.getClass().getName());
          paramCallableDescriptor1.append(" condition. It's not supposed to end with success");
          throw new IllegalStateException(paramCallableDescriptor1.toString());
        }
      }
    }
    paramCallableDescriptor1 = OverrideCompatibilityInfo.success();
    if (paramCallableDescriptor1 == null) {
      $$$reportNull$$$0(25);
    }
    return paramCallableDescriptor1;
  }
  
  public OverrideCompatibilityInfo isOverridableByWithoutExternalConditions(CallableDescriptor paramCallableDescriptor1, CallableDescriptor paramCallableDescriptor2, boolean paramBoolean)
  {
    if (paramCallableDescriptor1 == null) {
      $$$reportNull$$$0(26);
    }
    if (paramCallableDescriptor2 == null) {
      $$$reportNull$$$0(27);
    }
    Object localObject = getBasicOverridabilityProblem(paramCallableDescriptor1, paramCallableDescriptor2);
    if (localObject != null)
    {
      if (localObject == null) {
        $$$reportNull$$$0(28);
      }
      return localObject;
    }
    List localList1 = compiledValueParameters(paramCallableDescriptor1);
    List localList2 = compiledValueParameters(paramCallableDescriptor2);
    List localList3 = paramCallableDescriptor1.getTypeParameters();
    List localList4 = paramCallableDescriptor2.getTypeParameters();
    int i = localList3.size();
    int j = localList4.size();
    int k = 0;
    int m = 0;
    if (i != j)
    {
      while (m < localList1.size())
      {
        if (!KotlinTypeChecker.DEFAULT.equalTypes((KotlinType)localList1.get(m), (KotlinType)localList2.get(m)))
        {
          paramCallableDescriptor1 = OverrideCompatibilityInfo.incompatible("Type parameter number mismatch");
          if (paramCallableDescriptor1 == null) {
            $$$reportNull$$$0(29);
          }
          return paramCallableDescriptor1;
        }
        m++;
      }
      paramCallableDescriptor1 = OverrideCompatibilityInfo.conflict("Type parameter number mismatch");
      if (paramCallableDescriptor1 == null) {
        $$$reportNull$$$0(30);
      }
      return paramCallableDescriptor1;
    }
    localObject = createTypeChecker(localList3, localList4);
    for (m = 0; m < localList3.size(); m++) {
      if (!areTypeParametersEquivalent((TypeParameterDescriptor)localList3.get(m), (TypeParameterDescriptor)localList4.get(m), (KotlinTypeChecker)localObject))
      {
        paramCallableDescriptor1 = OverrideCompatibilityInfo.incompatible("Type parameter bounds mismatch");
        if (paramCallableDescriptor1 == null) {
          $$$reportNull$$$0(31);
        }
        return paramCallableDescriptor1;
      }
    }
    for (m = 0; m < localList1.size(); m++) {
      if (!areTypesEquivalent((KotlinType)localList1.get(m), (KotlinType)localList2.get(m), (KotlinTypeChecker)localObject))
      {
        paramCallableDescriptor1 = OverrideCompatibilityInfo.incompatible("Value parameter type mismatch");
        if (paramCallableDescriptor1 == null) {
          $$$reportNull$$$0(32);
        }
        return paramCallableDescriptor1;
      }
    }
    if (((paramCallableDescriptor1 instanceof FunctionDescriptor)) && ((paramCallableDescriptor2 instanceof FunctionDescriptor)) && (((FunctionDescriptor)paramCallableDescriptor1).isSuspend() != ((FunctionDescriptor)paramCallableDescriptor2).isSuspend()))
    {
      paramCallableDescriptor1 = OverrideCompatibilityInfo.conflict("Incompatible suspendability");
      if (paramCallableDescriptor1 == null) {
        $$$reportNull$$$0(33);
      }
      return paramCallableDescriptor1;
    }
    if (paramBoolean)
    {
      paramCallableDescriptor1 = paramCallableDescriptor1.getReturnType();
      paramCallableDescriptor2 = paramCallableDescriptor2.getReturnType();
      if ((paramCallableDescriptor1 != null) && (paramCallableDescriptor2 != null))
      {
        m = k;
        if (KotlinTypeKt.isError(paramCallableDescriptor2))
        {
          m = k;
          if (KotlinTypeKt.isError(paramCallableDescriptor1)) {
            m = 1;
          }
        }
        if ((m == 0) && (!((KotlinTypeChecker)localObject).isSubtypeOf(this.kotlinTypeRefiner.refineType(paramCallableDescriptor2), this.kotlinTypeRefiner.refineType(paramCallableDescriptor1))))
        {
          paramCallableDescriptor1 = OverrideCompatibilityInfo.conflict("Return type mismatch");
          if (paramCallableDescriptor1 == null) {
            $$$reportNull$$$0(34);
          }
          return paramCallableDescriptor1;
        }
      }
    }
    paramCallableDescriptor1 = OverrideCompatibilityInfo.success();
    if (paramCallableDescriptor1 == null) {
      $$$reportNull$$$0(35);
    }
    return paramCallableDescriptor1;
  }
  
  public static class OverrideCompatibilityInfo
  {
    private static final OverrideCompatibilityInfo SUCCESS = new OverrideCompatibilityInfo(Result.OVERRIDABLE, "SUCCESS");
    private final String debugMessage;
    private final Result overridable;
    
    public OverrideCompatibilityInfo(Result paramResult, String paramString)
    {
      this.overridable = paramResult;
      this.debugMessage = paramString;
    }
    
    public static OverrideCompatibilityInfo conflict(String paramString)
    {
      if (paramString == null) {
        $$$reportNull$$$0(2);
      }
      return new OverrideCompatibilityInfo(Result.CONFLICT, paramString);
    }
    
    public static OverrideCompatibilityInfo incompatible(String paramString)
    {
      if (paramString == null) {
        $$$reportNull$$$0(1);
      }
      return new OverrideCompatibilityInfo(Result.INCOMPATIBLE, paramString);
    }
    
    public static OverrideCompatibilityInfo success()
    {
      OverrideCompatibilityInfo localOverrideCompatibilityInfo = SUCCESS;
      if (localOverrideCompatibilityInfo == null) {
        $$$reportNull$$$0(0);
      }
      return localOverrideCompatibilityInfo;
    }
    
    public Result getResult()
    {
      Result localResult = this.overridable;
      if (localResult == null) {
        $$$reportNull$$$0(5);
      }
      return localResult;
    }
    
    public static enum Result
    {
      static
      {
        INCOMPATIBLE = new Result("INCOMPATIBLE", 1);
        Result localResult = new Result("CONFLICT", 2);
        CONFLICT = localResult;
        $VALUES = new Result[] { OVERRIDABLE, INCOMPATIBLE, localResult };
      }
      
      private Result() {}
    }
  }
}

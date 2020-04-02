package kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyAccessorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope.DefaultImpls;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner.Default;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefinerKt;
import kotlin.reflect.jvm.internal.impl.types.checker.Ref;
import kotlin.reflect.jvm.internal.impl.utils.DFS;
import kotlin.reflect.jvm.internal.impl.utils.DFS.AbstractNodeHandler;
import kotlin.reflect.jvm.internal.impl.utils.DFS.Neighbors;
import kotlin.reflect.jvm.internal.impl.utils.DFS.NodeHandler;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

public final class DescriptorUtilsKt
{
  private static final Name RETENTION_PARAMETER_NAME;
  
  static
  {
    Name localName = Name.identifier("value");
    Intrinsics.checkExpressionValueIsNotNull(localName, "Name.identifier(\"value\")");
    RETENTION_PARAMETER_NAME = localName;
  }
  
  public static final Collection<ClassDescriptor> computeSealedSubclasses(ClassDescriptor paramClassDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "sealedClass");
    if (paramClassDescriptor.getModality() != Modality.SEALED) {
      return (Collection)CollectionsKt.emptyList();
    }
    final LinkedHashSet localLinkedHashSet = new LinkedHashSet();
    Lambda local1 = new Lambda(paramClassDescriptor)
    {
      public final void invoke(MemberScope paramAnonymousMemberScope, boolean paramAnonymousBoolean)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousMemberScope, "scope");
        paramAnonymousMemberScope = ResolutionScope.DefaultImpls.getContributedDescriptors$default(paramAnonymousMemberScope, DescriptorKindFilter.CLASSIFIERS, null, 2, null).iterator();
        while (paramAnonymousMemberScope.hasNext())
        {
          Object localObject1 = (DeclarationDescriptor)paramAnonymousMemberScope.next();
          if ((localObject1 instanceof ClassDescriptor))
          {
            Object localObject2 = (ClassDescriptor)localObject1;
            if (DescriptorUtils.isDirectSubclass((ClassDescriptor)localObject2, this.$sealedClass)) {
              localLinkedHashSet.add(localObject1);
            }
            if (paramAnonymousBoolean)
            {
              localObject1 = (1)this;
              localObject2 = ((ClassDescriptor)localObject2).getUnsubstitutedInnerClassesScope();
              Intrinsics.checkExpressionValueIsNotNull(localObject2, "descriptor.unsubstitutedInnerClassesScope");
              ((1)localObject1).invoke((MemberScope)localObject2, paramAnonymousBoolean);
            }
          }
        }
      }
    };
    DeclarationDescriptor localDeclarationDescriptor = paramClassDescriptor.getContainingDeclaration();
    Intrinsics.checkExpressionValueIsNotNull(localDeclarationDescriptor, "sealedClass.containingDeclaration");
    if ((localDeclarationDescriptor instanceof PackageFragmentDescriptor)) {
      local1.invoke(((PackageFragmentDescriptor)localDeclarationDescriptor).getMemberScope(), false);
    }
    paramClassDescriptor = paramClassDescriptor.getUnsubstitutedInnerClassesScope();
    Intrinsics.checkExpressionValueIsNotNull(paramClassDescriptor, "sealedClass.unsubstitutedInnerClassesScope");
    local1.invoke(paramClassDescriptor, true);
    return (Collection)localLinkedHashSet;
  }
  
  public static final boolean declaresOrInheritsDefaultValue(ValueParameterDescriptor paramValueParameterDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramValueParameterDescriptor, "$this$declaresOrInheritsDefaultValue");
    paramValueParameterDescriptor = DFS.ifAny((Collection)CollectionsKt.listOf(paramValueParameterDescriptor), (DFS.Neighbors)declaresOrInheritsDefaultValue.1.INSTANCE, (Function1)declaresOrInheritsDefaultValue.2.INSTANCE);
    Intrinsics.checkExpressionValueIsNotNull(paramValueParameterDescriptor, "DFS.ifAny(\n        listO…eclaresDefaultValue\n    )");
    return paramValueParameterDescriptor.booleanValue();
  }
  
  public static final ConstantValue<?> firstArgument(AnnotationDescriptor paramAnnotationDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotationDescriptor, "$this$firstArgument");
    return (ConstantValue)CollectionsKt.firstOrNull((Iterable)paramAnnotationDescriptor.getAllValueArguments().values());
  }
  
  public static final CallableMemberDescriptor firstOverridden(CallableMemberDescriptor paramCallableMemberDescriptor, boolean paramBoolean, final Function1<? super CallableMemberDescriptor, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramCallableMemberDescriptor, "$this$firstOverridden");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    Ref.ObjectRef localObjectRef = new Ref.ObjectRef();
    localObjectRef.element = ((CallableMemberDescriptor)null);
    (CallableMemberDescriptor)DFS.dfs((Collection)CollectionsKt.listOf(paramCallableMemberDescriptor), (DFS.Neighbors)new DFS.Neighbors()
    {
      public final Iterable<CallableMemberDescriptor> getNeighbors(CallableMemberDescriptor paramAnonymousCallableMemberDescriptor)
      {
        CallableMemberDescriptor localCallableMemberDescriptor = paramAnonymousCallableMemberDescriptor;
        if (this.$useOriginal) {
          if (paramAnonymousCallableMemberDescriptor != null) {
            localCallableMemberDescriptor = paramAnonymousCallableMemberDescriptor.getOriginal();
          } else {
            localCallableMemberDescriptor = null;
          }
        }
        if (localCallableMemberDescriptor != null)
        {
          paramAnonymousCallableMemberDescriptor = localCallableMemberDescriptor.getOverriddenDescriptors();
          if (paramAnonymousCallableMemberDescriptor != null) {}
        }
        else
        {
          paramAnonymousCallableMemberDescriptor = CollectionsKt.emptyList();
        }
        return (Iterable)paramAnonymousCallableMemberDescriptor;
      }
    }, (DFS.NodeHandler)new DFS.AbstractNodeHandler()
    {
      public void afterChildren(CallableMemberDescriptor paramAnonymousCallableMemberDescriptor)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousCallableMemberDescriptor, "current");
        if (((CallableMemberDescriptor)this.$result.element == null) && (((Boolean)paramFunction1.invoke(paramAnonymousCallableMemberDescriptor)).booleanValue())) {
          this.$result.element = paramAnonymousCallableMemberDescriptor;
        }
      }
      
      public boolean beforeChildren(CallableMemberDescriptor paramAnonymousCallableMemberDescriptor)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousCallableMemberDescriptor, "current");
        boolean bool;
        if ((CallableMemberDescriptor)this.$result.element == null) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public CallableMemberDescriptor result()
      {
        return (CallableMemberDescriptor)this.$result.element;
      }
    });
  }
  
  public static final FqName fqNameOrNull(DeclarationDescriptor paramDeclarationDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptor, "$this$fqNameOrNull");
    paramDeclarationDescriptor = getFqNameUnsafe(paramDeclarationDescriptor);
    boolean bool = paramDeclarationDescriptor.isSafe();
    FqName localFqName = null;
    if (!bool) {
      paramDeclarationDescriptor = null;
    }
    if (paramDeclarationDescriptor != null) {
      localFqName = paramDeclarationDescriptor.toSafe();
    }
    return localFqName;
  }
  
  public static final ClassDescriptor getAnnotationClass(AnnotationDescriptor paramAnnotationDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotationDescriptor, "$this$annotationClass");
    ClassifierDescriptor localClassifierDescriptor = paramAnnotationDescriptor.getType().getConstructor().getDeclarationDescriptor();
    paramAnnotationDescriptor = localClassifierDescriptor;
    if (!(localClassifierDescriptor instanceof ClassDescriptor)) {
      paramAnnotationDescriptor = null;
    }
    return (ClassDescriptor)paramAnnotationDescriptor;
  }
  
  public static final KotlinBuiltIns getBuiltIns(DeclarationDescriptor paramDeclarationDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptor, "$this$builtIns");
    return getModule(paramDeclarationDescriptor).getBuiltIns();
  }
  
  public static final ClassId getClassId(ClassifierDescriptor paramClassifierDescriptor)
  {
    Object localObject1 = null;
    Object localObject2 = localObject1;
    if (paramClassifierDescriptor != null)
    {
      Object localObject3 = paramClassifierDescriptor.getContainingDeclaration();
      localObject2 = localObject1;
      if (localObject3 != null) {
        if ((localObject3 instanceof PackageFragmentDescriptor))
        {
          localObject2 = new ClassId(((PackageFragmentDescriptor)localObject3).getFqName(), paramClassifierDescriptor.getName());
        }
        else
        {
          localObject2 = localObject1;
          if ((localObject3 instanceof ClassifierDescriptorWithTypeParameters))
          {
            localObject3 = getClassId((ClassifierDescriptor)localObject3);
            localObject2 = localObject1;
            if (localObject3 != null) {
              localObject2 = ((ClassId)localObject3).createNestedClassId(paramClassifierDescriptor.getName());
            }
          }
        }
      }
    }
    return localObject2;
  }
  
  public static final FqName getFqNameSafe(DeclarationDescriptor paramDeclarationDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptor, "$this$fqNameSafe");
    paramDeclarationDescriptor = DescriptorUtils.getFqNameSafe(paramDeclarationDescriptor);
    Intrinsics.checkExpressionValueIsNotNull(paramDeclarationDescriptor, "DescriptorUtils.getFqNameSafe(this)");
    return paramDeclarationDescriptor;
  }
  
  public static final FqNameUnsafe getFqNameUnsafe(DeclarationDescriptor paramDeclarationDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptor, "$this$fqNameUnsafe");
    paramDeclarationDescriptor = DescriptorUtils.getFqName(paramDeclarationDescriptor);
    Intrinsics.checkExpressionValueIsNotNull(paramDeclarationDescriptor, "DescriptorUtils.getFqName(this)");
    return paramDeclarationDescriptor;
  }
  
  public static final KotlinTypeRefiner getKotlinTypeRefiner(ModuleDescriptor paramModuleDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "$this$getKotlinTypeRefiner");
    paramModuleDescriptor = (Ref)paramModuleDescriptor.getCapability(KotlinTypeRefinerKt.getREFINER_CAPABILITY());
    if (paramModuleDescriptor != null)
    {
      paramModuleDescriptor = (KotlinTypeRefiner)paramModuleDescriptor.getValue();
      if (paramModuleDescriptor != null) {}
    }
    else
    {
      paramModuleDescriptor = (KotlinTypeRefiner)KotlinTypeRefiner.Default.INSTANCE;
    }
    return paramModuleDescriptor;
  }
  
  public static final ModuleDescriptor getModule(DeclarationDescriptor paramDeclarationDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptor, "$this$module");
    paramDeclarationDescriptor = DescriptorUtils.getContainingModule(paramDeclarationDescriptor);
    Intrinsics.checkExpressionValueIsNotNull(paramDeclarationDescriptor, "DescriptorUtils.getContainingModule(this)");
    return paramDeclarationDescriptor;
  }
  
  public static final Sequence<DeclarationDescriptor> getParents(DeclarationDescriptor paramDeclarationDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptor, "$this$parents");
    return SequencesKt.drop(getParentsWithSelf(paramDeclarationDescriptor), 1);
  }
  
  public static final Sequence<DeclarationDescriptor> getParentsWithSelf(DeclarationDescriptor paramDeclarationDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptor, "$this$parentsWithSelf");
    return SequencesKt.generateSequence(paramDeclarationDescriptor, (Function1)parentsWithSelf.1.INSTANCE);
  }
  
  public static final CallableMemberDescriptor getPropertyIfAccessor(CallableMemberDescriptor paramCallableMemberDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramCallableMemberDescriptor, "$this$propertyIfAccessor");
    CallableMemberDescriptor localCallableMemberDescriptor = paramCallableMemberDescriptor;
    if ((paramCallableMemberDescriptor instanceof PropertyAccessorDescriptor))
    {
      paramCallableMemberDescriptor = ((PropertyAccessorDescriptor)paramCallableMemberDescriptor).getCorrespondingProperty();
      Intrinsics.checkExpressionValueIsNotNull(paramCallableMemberDescriptor, "correspondingProperty");
      localCallableMemberDescriptor = (CallableMemberDescriptor)paramCallableMemberDescriptor;
    }
    return localCallableMemberDescriptor;
  }
  
  public static final ClassDescriptor getSuperClassNotAny(ClassDescriptor paramClassDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "$this$getSuperClassNotAny");
    paramClassDescriptor = paramClassDescriptor.getDefaultType().getConstructor().getSupertypes().iterator();
    while (paramClassDescriptor.hasNext())
    {
      Object localObject = (KotlinType)paramClassDescriptor.next();
      if (!KotlinBuiltIns.isAnyOrNullableAny((KotlinType)localObject))
      {
        localObject = ((KotlinType)localObject).getConstructor().getDeclarationDescriptor();
        if (DescriptorUtils.isClassOrEnumClass((DeclarationDescriptor)localObject))
        {
          if (localObject != null) {
            return (ClassDescriptor)localObject;
          }
          throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        }
      }
    }
    return null;
  }
  
  public static final boolean isTypeRefinementEnabled(ModuleDescriptor paramModuleDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "$this$isTypeRefinementEnabled");
    paramModuleDescriptor = (Ref)paramModuleDescriptor.getCapability(KotlinTypeRefinerKt.getREFINER_CAPABILITY());
    if (paramModuleDescriptor != null) {
      paramModuleDescriptor = (KotlinTypeRefiner)paramModuleDescriptor.getValue();
    } else {
      paramModuleDescriptor = null;
    }
    boolean bool;
    if (paramModuleDescriptor != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final ClassDescriptor resolveTopLevelClass(ModuleDescriptor paramModuleDescriptor, FqName paramFqName, LookupLocation paramLookupLocation)
  {
    Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "$this$resolveTopLevelClass");
    Intrinsics.checkParameterIsNotNull(paramFqName, "topLevelClassFqName");
    Intrinsics.checkParameterIsNotNull(paramLookupLocation, "location");
    boolean bool = paramFqName.isRoot();
    if ((_Assertions.ENABLED) && (!(bool ^ true))) {
      throw ((Throwable)new AssertionError("Assertion failed"));
    }
    FqName localFqName = paramFqName.parent();
    Intrinsics.checkExpressionValueIsNotNull(localFqName, "topLevelClassFqName.parent()");
    paramModuleDescriptor = paramModuleDescriptor.getPackage(localFqName).getMemberScope();
    paramFqName = paramFqName.shortName();
    Intrinsics.checkExpressionValueIsNotNull(paramFqName, "topLevelClassFqName.shortName()");
    paramFqName = paramModuleDescriptor.getContributedClassifier(paramFqName, paramLookupLocation);
    paramModuleDescriptor = paramFqName;
    if (!(paramFqName instanceof ClassDescriptor)) {
      paramModuleDescriptor = null;
    }
    return (ClassDescriptor)paramModuleDescriptor;
  }
}

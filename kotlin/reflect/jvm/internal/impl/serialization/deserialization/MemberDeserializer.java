package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor.UserDataKey;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FieldDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.FieldDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyGetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertySetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Constructor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Function;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.MemberKind;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Modality;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Property;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeAlias;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.ValueParameter;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.VersionRequirement.VersionKind;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Visibility;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags.BooleanFlagField;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags.FlagField;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoTypeTableUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirement;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirement.Version;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable.Companion;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedAnnotations;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedCallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberDescriptor.CoroutinesCompatibilityMode;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedPropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedSimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedTypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.NonEmptyDeserializedAnnotations;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

public final class MemberDeserializer
{
  private final AnnotationDeserializer annotationDeserializer;
  private final DeserializationContext c;
  
  public MemberDeserializer(DeserializationContext paramDeserializationContext)
  {
    this.c = paramDeserializationContext;
    this.annotationDeserializer = new AnnotationDeserializer(paramDeserializationContext.getComponents().getModuleDescriptor(), this.c.getComponents().getNotFoundClasses());
  }
  
  private final ProtoContainer asProtoContainer(DeclarationDescriptor paramDeclarationDescriptor)
  {
    if ((paramDeclarationDescriptor instanceof PackageFragmentDescriptor)) {
      paramDeclarationDescriptor = (ProtoContainer)new ProtoContainer.Package(((PackageFragmentDescriptor)paramDeclarationDescriptor).getFqName(), this.c.getNameResolver(), this.c.getTypeTable(), (SourceElement)this.c.getContainerSource());
    } else if ((paramDeclarationDescriptor instanceof DeserializedClassDescriptor)) {
      paramDeclarationDescriptor = (ProtoContainer)((DeserializedClassDescriptor)paramDeclarationDescriptor).getThisAsProtoContainer$deserialization();
    } else {
      paramDeclarationDescriptor = null;
    }
    return paramDeclarationDescriptor;
  }
  
  private final DeserializedMemberDescriptor.CoroutinesCompatibilityMode checkExperimentalCoroutine(DeserializedMemberDescriptor paramDeserializedMemberDescriptor, TypeDeserializer paramTypeDeserializer)
  {
    if (!versionAndReleaseCoroutinesMismatch(paramDeserializedMemberDescriptor)) {
      return DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
    }
    forceUpperBoundsComputation(paramTypeDeserializer);
    if (paramTypeDeserializer.getExperimentalSuspendFunctionTypeEncountered()) {
      paramDeserializedMemberDescriptor = DeserializedMemberDescriptor.CoroutinesCompatibilityMode.INCOMPATIBLE;
    } else {
      paramDeserializedMemberDescriptor = DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
    }
    return paramDeserializedMemberDescriptor;
  }
  
  private final DeserializedMemberDescriptor.CoroutinesCompatibilityMode computeExperimentalityModeForFunctions(DeserializedCallableMemberDescriptor paramDeserializedCallableMemberDescriptor, ReceiverParameterDescriptor paramReceiverParameterDescriptor, Collection<? extends ValueParameterDescriptor> paramCollection, Collection<? extends TypeParameterDescriptor> paramCollection1, KotlinType paramKotlinType, boolean paramBoolean)
  {
    if (!versionAndReleaseCoroutinesMismatch((DeserializedMemberDescriptor)paramDeserializedCallableMemberDescriptor)) {
      return DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
    }
    if (Intrinsics.areEqual(DescriptorUtilsKt.fqNameOrNull((DeclarationDescriptor)paramDeserializedCallableMemberDescriptor), SuspendFunctionTypeUtilKt.KOTLIN_SUSPEND_BUILT_IN_FUNCTION_FQ_NAME)) {
      return DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
    }
    paramCollection = (Iterable)paramCollection;
    paramDeserializedCallableMemberDescriptor = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault(paramCollection, 10));
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext()) {
      paramDeserializedCallableMemberDescriptor.add(((ValueParameterDescriptor)paramCollection.next()).getType());
    }
    paramCollection = (Collection)paramDeserializedCallableMemberDescriptor;
    if (paramReceiverParameterDescriptor != null) {
      paramDeserializedCallableMemberDescriptor = paramReceiverParameterDescriptor.getType();
    } else {
      paramDeserializedCallableMemberDescriptor = null;
    }
    paramDeserializedCallableMemberDescriptor = CollectionsKt.plus(paramCollection, (Iterable)CollectionsKt.listOfNotNull(paramDeserializedCallableMemberDescriptor));
    if ((paramKotlinType != null) && (containsSuspendFunctionType(paramKotlinType) == true)) {
      return DeserializedMemberDescriptor.CoroutinesCompatibilityMode.INCOMPATIBLE;
    }
    paramReceiverParameterDescriptor = (Iterable)paramCollection1;
    if (((paramReceiverParameterDescriptor instanceof Collection)) && (((Collection)paramReceiverParameterDescriptor).isEmpty())) {}
    do
    {
      while (!paramReceiverParameterDescriptor.hasNext())
      {
        i = 0;
        break;
        paramReceiverParameterDescriptor = paramReceiverParameterDescriptor.iterator();
      }
      paramCollection = ((TypeParameterDescriptor)paramReceiverParameterDescriptor.next()).getUpperBounds();
      Intrinsics.checkExpressionValueIsNotNull(paramCollection, "typeParameter.upperBounds");
      paramCollection = (Iterable)paramCollection;
      if (((paramCollection instanceof Collection)) && (((Collection)paramCollection).isEmpty())) {}
      do
      {
        while (!paramCollection.hasNext())
        {
          i = 0;
          break;
          paramCollection = paramCollection.iterator();
        }
        paramCollection1 = (KotlinType)paramCollection.next();
        Intrinsics.checkExpressionValueIsNotNull(paramCollection1, "it");
      } while (!containsSuspendFunctionType(paramCollection1));
      i = 1;
    } while (i == 0);
    int i = 1;
    if (i != 0) {
      return DeserializedMemberDescriptor.CoroutinesCompatibilityMode.INCOMPATIBLE;
    }
    paramDeserializedCallableMemberDescriptor = (Iterable)paramDeserializedCallableMemberDescriptor;
    paramReceiverParameterDescriptor = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault(paramDeserializedCallableMemberDescriptor, 10));
    paramCollection = paramDeserializedCallableMemberDescriptor.iterator();
    while (paramCollection.hasNext())
    {
      paramDeserializedCallableMemberDescriptor = (KotlinType)paramCollection.next();
      Intrinsics.checkExpressionValueIsNotNull(paramDeserializedCallableMemberDescriptor, "type");
      if ((FunctionTypesKt.isSuspendFunctionType(paramDeserializedCallableMemberDescriptor)) && (paramDeserializedCallableMemberDescriptor.getArguments().size() <= 3))
      {
        paramDeserializedCallableMemberDescriptor = (Iterable)paramDeserializedCallableMemberDescriptor.getArguments();
        if (((paramDeserializedCallableMemberDescriptor instanceof Collection)) && (((Collection)paramDeserializedCallableMemberDescriptor).isEmpty())) {}
        do
        {
          while (!paramDeserializedCallableMemberDescriptor.hasNext())
          {
            i = 0;
            break;
            paramDeserializedCallableMemberDescriptor = paramDeserializedCallableMemberDescriptor.iterator();
          }
          paramCollection1 = ((TypeProjection)paramDeserializedCallableMemberDescriptor.next()).getType();
          Intrinsics.checkExpressionValueIsNotNull(paramCollection1, "it.type");
        } while (!containsSuspendFunctionType(paramCollection1));
        i = 1;
        if (i != 0) {
          paramDeserializedCallableMemberDescriptor = DeserializedMemberDescriptor.CoroutinesCompatibilityMode.INCOMPATIBLE;
        } else {
          paramDeserializedCallableMemberDescriptor = DeserializedMemberDescriptor.CoroutinesCompatibilityMode.NEEDS_WRAPPER;
        }
      }
      else if (containsSuspendFunctionType(paramDeserializedCallableMemberDescriptor))
      {
        paramDeserializedCallableMemberDescriptor = DeserializedMemberDescriptor.CoroutinesCompatibilityMode.INCOMPATIBLE;
      }
      else
      {
        paramDeserializedCallableMemberDescriptor = DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
      }
      paramReceiverParameterDescriptor.add(paramDeserializedCallableMemberDescriptor);
    }
    paramDeserializedCallableMemberDescriptor = (DeserializedMemberDescriptor.CoroutinesCompatibilityMode)CollectionsKt.max((Iterable)paramReceiverParameterDescriptor);
    if (paramDeserializedCallableMemberDescriptor == null) {
      paramDeserializedCallableMemberDescriptor = DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
    }
    if (paramBoolean) {
      paramReceiverParameterDescriptor = DeserializedMemberDescriptor.CoroutinesCompatibilityMode.NEEDS_WRAPPER;
    } else {
      paramReceiverParameterDescriptor = DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
    }
    return (DeserializedMemberDescriptor.CoroutinesCompatibilityMode)ComparisonsKt.maxOf((Comparable)paramReceiverParameterDescriptor, (Comparable)paramDeserializedCallableMemberDescriptor);
  }
  
  private final boolean containsSuspendFunctionType(KotlinType paramKotlinType)
  {
    return TypeUtilsKt.contains(paramKotlinType, (Function1)MemberDeserializer.containsSuspendFunctionType.1.INSTANCE);
  }
  
  private final void forceUpperBoundsComputation(TypeDeserializer paramTypeDeserializer)
  {
    paramTypeDeserializer = ((Iterable)paramTypeDeserializer.getOwnTypeParameters()).iterator();
    while (paramTypeDeserializer.hasNext()) {
      ((TypeParameterDescriptor)paramTypeDeserializer.next()).getUpperBounds();
    }
  }
  
  private final Annotations getAnnotations(final MessageLite paramMessageLite, int paramInt, final AnnotatedCallableKind paramAnnotatedCallableKind)
  {
    if (!Flags.HAS_ANNOTATIONS.get(paramInt).booleanValue()) {
      return Annotations.Companion.getEMPTY();
    }
    (Annotations)new NonEmptyDeserializedAnnotations(this.c.getStorageManager(), (Function0)new Lambda(paramMessageLite)
    {
      public final List<AnnotationDescriptor> invoke()
      {
        Object localObject = this.this$0;
        localObject = MemberDeserializer.access$asProtoContainer((MemberDeserializer)localObject, MemberDeserializer.access$getC$p((MemberDeserializer)localObject).getContainingDeclaration());
        if (localObject != null) {
          localObject = CollectionsKt.toList((Iterable)MemberDeserializer.access$getC$p(this.this$0).getComponents().getAnnotationAndConstantLoader().loadCallableAnnotations((ProtoContainer)localObject, paramMessageLite, paramAnnotatedCallableKind));
        } else {
          localObject = null;
        }
        if (localObject == null) {
          localObject = CollectionsKt.emptyList();
        }
        return localObject;
      }
    });
  }
  
  private final ReceiverParameterDescriptor getDispatchReceiverParameter()
  {
    Object localObject1 = this.c.getContainingDeclaration();
    boolean bool = localObject1 instanceof ClassDescriptor;
    Object localObject2 = null;
    if (!bool) {
      localObject1 = null;
    }
    ClassDescriptor localClassDescriptor = (ClassDescriptor)localObject1;
    localObject1 = localObject2;
    if (localClassDescriptor != null) {
      localObject1 = localClassDescriptor.getThisAsReceiverParameter();
    }
    return localObject1;
  }
  
  private final Annotations getPropertyFieldAnnotations(final ProtoBuf.Property paramProperty, final boolean paramBoolean)
  {
    if (!Flags.HAS_ANNOTATIONS.get(paramProperty.getFlags()).booleanValue()) {
      return Annotations.Companion.getEMPTY();
    }
    (Annotations)new NonEmptyDeserializedAnnotations(this.c.getStorageManager(), (Function0)new Lambda(paramBoolean)
    {
      public final List<AnnotationDescriptor> invoke()
      {
        Object localObject = this.this$0;
        localObject = MemberDeserializer.access$asProtoContainer((MemberDeserializer)localObject, MemberDeserializer.access$getC$p((MemberDeserializer)localObject).getContainingDeclaration());
        if (localObject != null)
        {
          if (paramBoolean) {
            localObject = CollectionsKt.toList((Iterable)MemberDeserializer.access$getC$p(this.this$0).getComponents().getAnnotationAndConstantLoader().loadPropertyDelegateFieldAnnotations((ProtoContainer)localObject, paramProperty));
          } else {
            localObject = CollectionsKt.toList((Iterable)MemberDeserializer.access$getC$p(this.this$0).getComponents().getAnnotationAndConstantLoader().loadPropertyBackingFieldAnnotations((ProtoContainer)localObject, paramProperty));
          }
        }
        else {
          localObject = null;
        }
        if (localObject == null) {
          localObject = CollectionsKt.emptyList();
        }
        return localObject;
      }
    });
  }
  
  private final Annotations getReceiverParameterAnnotations(final MessageLite paramMessageLite, final AnnotatedCallableKind paramAnnotatedCallableKind)
  {
    (Annotations)new DeserializedAnnotations(this.c.getStorageManager(), (Function0)new Lambda(paramMessageLite)
    {
      public final List<AnnotationDescriptor> invoke()
      {
        Object localObject = this.this$0;
        localObject = MemberDeserializer.access$asProtoContainer((MemberDeserializer)localObject, MemberDeserializer.access$getC$p((MemberDeserializer)localObject).getContainingDeclaration());
        if (localObject != null) {
          localObject = MemberDeserializer.access$getC$p(this.this$0).getComponents().getAnnotationAndConstantLoader().loadExtensionReceiverParameterAnnotations((ProtoContainer)localObject, paramMessageLite, paramAnnotatedCallableKind);
        } else {
          localObject = null;
        }
        if (localObject == null) {
          localObject = CollectionsKt.emptyList();
        }
        return localObject;
      }
    });
  }
  
  private final void initializeWithCoroutinesExperimentalityStatus(DeserializedSimpleFunctionDescriptor paramDeserializedSimpleFunctionDescriptor, ReceiverParameterDescriptor paramReceiverParameterDescriptor1, ReceiverParameterDescriptor paramReceiverParameterDescriptor2, List<? extends TypeParameterDescriptor> paramList, List<? extends ValueParameterDescriptor> paramList1, KotlinType paramKotlinType, Modality paramModality, Visibility paramVisibility, Map<? extends CallableDescriptor.UserDataKey<?>, ?> paramMap, boolean paramBoolean)
  {
    paramDeserializedSimpleFunctionDescriptor.initialize(paramReceiverParameterDescriptor1, paramReceiverParameterDescriptor2, paramList, paramList1, paramKotlinType, paramModality, paramVisibility, paramMap, computeExperimentalityModeForFunctions((DeserializedCallableMemberDescriptor)paramDeserializedSimpleFunctionDescriptor, paramReceiverParameterDescriptor1, (Collection)paramList1, (Collection)paramList, paramKotlinType, paramBoolean));
  }
  
  private final int loadOldFlags(int paramInt)
  {
    return (paramInt & 0x3F) + (paramInt >> 8 << 6);
  }
  
  private final List<ValueParameterDescriptor> valueParameters(List<ProtoBuf.ValueParameter> paramList, final MessageLite paramMessageLite, final AnnotatedCallableKind paramAnnotatedCallableKind)
  {
    Object localObject1 = this.c.getContainingDeclaration();
    if (localObject1 != null)
    {
      final CallableDescriptor localCallableDescriptor = (CallableDescriptor)localObject1;
      localObject1 = localCallableDescriptor.getContainingDeclaration();
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "callableDescriptor.containingDeclaration");
      final ProtoContainer localProtoContainer = asProtoContainer((DeclarationDescriptor)localObject1);
      localObject1 = (Iterable)paramList;
      paramList = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject1, 10));
      Iterator localIterator = ((Iterable)localObject1).iterator();
      for (int i = 0; localIterator.hasNext(); i++)
      {
        localObject1 = localIterator.next();
        if (i < 0) {
          CollectionsKt.throwIndexOverflow();
        }
        Object localObject2 = (ProtoBuf.ValueParameter)localObject1;
        int j;
        if (((ProtoBuf.ValueParameter)localObject2).hasFlags()) {
          j = ((ProtoBuf.ValueParameter)localObject2).getFlags();
        } else {
          j = 0;
        }
        if (localProtoContainer != null)
        {
          localObject1 = Flags.HAS_ANNOTATIONS.get(j);
          Intrinsics.checkExpressionValueIsNotNull(localObject1, "Flags.HAS_ANNOTATIONS.get(flags)");
          if (((Boolean)localObject1).booleanValue())
          {
            localObject1 = (Annotations)new NonEmptyDeserializedAnnotations(this.c.getStorageManager(), (Function0)new Lambda(i)
            {
              public final List<AnnotationDescriptor> invoke()
              {
                return CollectionsKt.toList((Iterable)MemberDeserializer.access$getC$p(jdField_this).getComponents().getAnnotationAndConstantLoader().loadValueParameterAnnotations(localProtoContainer, paramMessageLite, paramAnnotatedCallableKind, this.$i, this.$proto));
              }
            });
            break label219;
          }
        }
        localObject1 = Annotations.Companion.getEMPTY();
        label219:
        Name localName = NameResolverUtilKt.getName(this.c.getNameResolver(), ((ProtoBuf.ValueParameter)localObject2).getName());
        KotlinType localKotlinType = this.c.getTypeDeserializer().type(ProtoTypeTableUtilKt.type((ProtoBuf.ValueParameter)localObject2, this.c.getTypeTable()));
        Object localObject3 = Flags.DECLARES_DEFAULT_VALUE.get(j);
        Intrinsics.checkExpressionValueIsNotNull(localObject3, "Flags.DECLARES_DEFAULT_VALUE.get(flags)");
        boolean bool1 = ((Boolean)localObject3).booleanValue();
        localObject3 = Flags.IS_CROSSINLINE.get(j);
        Intrinsics.checkExpressionValueIsNotNull(localObject3, "Flags.IS_CROSSINLINE.get(flags)");
        boolean bool2 = ((Boolean)localObject3).booleanValue();
        localObject3 = Flags.IS_NOINLINE.get(j);
        Intrinsics.checkExpressionValueIsNotNull(localObject3, "Flags.IS_NOINLINE.get(flags)");
        boolean bool3 = ((Boolean)localObject3).booleanValue();
        localObject2 = ProtoTypeTableUtilKt.varargElementType((ProtoBuf.ValueParameter)localObject2, this.c.getTypeTable());
        if (localObject2 != null) {
          localObject2 = this.c.getTypeDeserializer().type((ProtoBuf.Type)localObject2);
        } else {
          localObject2 = null;
        }
        localObject3 = SourceElement.NO_SOURCE;
        Intrinsics.checkExpressionValueIsNotNull(localObject3, "SourceElement.NO_SOURCE");
        paramList.add(new ValueParameterDescriptorImpl(localCallableDescriptor, null, i, (Annotations)localObject1, localName, localKotlinType, bool1, bool2, bool3, (KotlinType)localObject2, (SourceElement)localObject3));
      }
      return CollectionsKt.toList((Iterable)paramList);
    }
    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.CallableDescriptor");
  }
  
  private final boolean versionAndReleaseCoroutinesMismatch(DeserializedMemberDescriptor paramDeserializedMemberDescriptor)
  {
    boolean bool1 = this.c.getComponents().getConfiguration().getReleaseCoroutines();
    boolean bool2 = false;
    boolean bool3 = bool2;
    if (bool1)
    {
      paramDeserializedMemberDescriptor = (Iterable)paramDeserializedMemberDescriptor.getVersionRequirements();
      if (((paramDeserializedMemberDescriptor instanceof Collection)) && (((Collection)paramDeserializedMemberDescriptor).isEmpty())) {}
      do
      {
        while (!paramDeserializedMemberDescriptor.hasNext())
        {
          i = 1;
          break;
          paramDeserializedMemberDescriptor = paramDeserializedMemberDescriptor.iterator();
        }
        VersionRequirement localVersionRequirement = (VersionRequirement)paramDeserializedMemberDescriptor.next();
        if ((Intrinsics.areEqual(localVersionRequirement.getVersion(), new VersionRequirement.Version(1, 3, 0, 4, null))) && (localVersionRequirement.getKind() == ProtoBuf.VersionRequirement.VersionKind.LANGUAGE_VERSION)) {
          i = 1;
        } else {
          i = 0;
        }
      } while (i == 0);
      int i = 0;
      bool3 = bool2;
      if (i != 0) {
        bool3 = true;
      }
    }
    return bool3;
  }
  
  public final ClassConstructorDescriptor loadConstructor(ProtoBuf.Constructor paramConstructor, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramConstructor, "proto");
    Object localObject1 = this.c.getContainingDeclaration();
    if (localObject1 != null)
    {
      Object localObject2 = (ClassDescriptor)localObject1;
      MessageLite localMessageLite = (MessageLite)paramConstructor;
      localObject1 = new DeserializedClassConstructorDescriptor((ClassDescriptor)localObject2, null, getAnnotations(localMessageLite, paramConstructor.getFlags(), AnnotatedCallableKind.FUNCTION), paramBoolean, CallableMemberDescriptor.Kind.DECLARATION, paramConstructor, this.c.getNameResolver(), this.c.getTypeTable(), this.c.getVersionRequirementTable(), this.c.getContainerSource(), null, 1024, null);
      Object localObject3 = DeserializationContext.childContext$default(this.c, (DeclarationDescriptor)localObject1, CollectionsKt.emptyList(), null, null, null, null, 60, null).getMemberDeserializer();
      List localList = paramConstructor.getValueParameterList();
      Intrinsics.checkExpressionValueIsNotNull(localList, "proto.valueParameterList");
      ((DeserializedClassConstructorDescriptor)localObject1).initialize(((MemberDeserializer)localObject3).valueParameters(localList, localMessageLite, AnnotatedCallableKind.FUNCTION), ProtoEnumFlags.INSTANCE.visibility((ProtoBuf.Visibility)Flags.VISIBILITY.get(paramConstructor.getFlags())));
      ((DeserializedClassConstructorDescriptor)localObject1).setReturnType((KotlinType)((ClassDescriptor)localObject2).getDefaultType());
      localObject2 = this.c.getContainingDeclaration();
      paramConstructor = (ProtoBuf.Constructor)localObject2;
      if (!(localObject2 instanceof DeserializedClassDescriptor)) {
        paramConstructor = null;
      }
      paramConstructor = (DeserializedClassDescriptor)paramConstructor;
      int i = 1;
      if (paramConstructor != null)
      {
        paramConstructor = paramConstructor.getC();
        if (paramConstructor != null)
        {
          paramConstructor = paramConstructor.getTypeDeserializer();
          if ((paramConstructor != null) && (paramConstructor.getExperimentalSuspendFunctionTypeEncountered() == true) && (versionAndReleaseCoroutinesMismatch((DeserializedMemberDescriptor)localObject1))) {
            break label260;
          }
        }
      }
      i = 0;
      label260:
      if (i != 0)
      {
        paramConstructor = DeserializedMemberDescriptor.CoroutinesCompatibilityMode.INCOMPATIBLE;
      }
      else
      {
        paramConstructor = (DeserializedCallableMemberDescriptor)localObject1;
        localObject2 = ((DeserializedClassConstructorDescriptor)localObject1).getValueParameters();
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "descriptor.valueParameters");
        localObject2 = (Collection)localObject2;
        localObject3 = ((DeserializedClassConstructorDescriptor)localObject1).getTypeParameters();
        Intrinsics.checkExpressionValueIsNotNull(localObject3, "descriptor.typeParameters");
        paramConstructor = computeExperimentalityModeForFunctions(paramConstructor, null, (Collection)localObject2, (Collection)localObject3, ((DeserializedClassConstructorDescriptor)localObject1).getReturnType(), false);
      }
      ((DeserializedClassConstructorDescriptor)localObject1).setCoroutinesExperimentalCompatibilityMode$deserialization(paramConstructor);
      return (ClassConstructorDescriptor)localObject1;
    }
    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
  }
  
  public final SimpleFunctionDescriptor loadFunction(ProtoBuf.Function paramFunction)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction, "proto");
    int i;
    if (paramFunction.hasFlags()) {
      i = paramFunction.getFlags();
    } else {
      i = loadOldFlags(paramFunction.getOldFlags());
    }
    Object localObject1 = (MessageLite)paramFunction;
    Object localObject2 = getAnnotations((MessageLite)localObject1, i, AnnotatedCallableKind.FUNCTION);
    if (ProtoTypeTableUtilKt.hasReceiver(paramFunction)) {
      localObject3 = getReceiverParameterAnnotations((MessageLite)localObject1, AnnotatedCallableKind.FUNCTION);
    } else {
      localObject3 = Annotations.Companion.getEMPTY();
    }
    if (Intrinsics.areEqual(DescriptorUtilsKt.getFqNameSafe(this.c.getContainingDeclaration()).child(NameResolverUtilKt.getName(this.c.getNameResolver(), paramFunction.getName())), SuspendFunctionTypeUtilKt.KOTLIN_SUSPEND_BUILT_IN_FUNCTION_FQ_NAME)) {
      localObject4 = VersionRequirementTable.Companion.getEMPTY();
    } else {
      localObject4 = this.c.getVersionRequirementTable();
    }
    Object localObject4 = new DeserializedSimpleFunctionDescriptor(this.c.getContainingDeclaration(), null, (Annotations)localObject2, NameResolverUtilKt.getName(this.c.getNameResolver(), paramFunction.getName()), ProtoEnumFlags.INSTANCE.memberKind((ProtoBuf.MemberKind)Flags.MEMBER_KIND.get(i)), paramFunction, this.c.getNameResolver(), this.c.getTypeTable(), (VersionRequirementTable)localObject4, this.c.getContainerSource(), null, 1024, null);
    Object localObject5 = this.c;
    localObject2 = (DeclarationDescriptor)localObject4;
    Object localObject6 = paramFunction.getTypeParameterList();
    Intrinsics.checkExpressionValueIsNotNull(localObject6, "proto.typeParameterList");
    localObject5 = DeserializationContext.childContext$default((DeserializationContext)localObject5, (DeclarationDescriptor)localObject2, (List)localObject6, null, null, null, null, 60, null);
    localObject2 = ProtoTypeTableUtilKt.receiverType(paramFunction, this.c.getTypeTable());
    if (localObject2 != null)
    {
      localObject2 = ((DeserializationContext)localObject5).getTypeDeserializer().type((ProtoBuf.Type)localObject2);
      if (localObject2 != null)
      {
        localObject3 = DescriptorFactory.createExtensionReceiverParameterForCallable((CallableDescriptor)localObject4, (KotlinType)localObject2, (Annotations)localObject3);
        break label309;
      }
    }
    Object localObject3 = null;
    label309:
    localObject6 = getDispatchReceiverParameter();
    localObject2 = ((DeserializationContext)localObject5).getTypeDeserializer().getOwnTypeParameters();
    Object localObject7 = ((DeserializationContext)localObject5).getMemberDeserializer();
    Object localObject8 = paramFunction.getValueParameterList();
    Intrinsics.checkExpressionValueIsNotNull(localObject8, "proto.valueParameterList");
    localObject1 = ((MemberDeserializer)localObject7).valueParameters((List)localObject8, (MessageLite)localObject1, AnnotatedCallableKind.FUNCTION);
    KotlinType localKotlinType = ((DeserializationContext)localObject5).getTypeDeserializer().type(ProtoTypeTableUtilKt.returnType(paramFunction, this.c.getTypeTable()));
    localObject8 = ProtoEnumFlags.INSTANCE.modality((ProtoBuf.Modality)Flags.MODALITY.get(i));
    Visibility localVisibility = ProtoEnumFlags.INSTANCE.visibility((ProtoBuf.Visibility)Flags.VISIBILITY.get(i));
    localObject5 = MapsKt.emptyMap();
    localObject7 = Flags.IS_SUSPEND.get(i);
    Intrinsics.checkExpressionValueIsNotNull(localObject7, "Flags.IS_SUSPEND.get(flags)");
    initializeWithCoroutinesExperimentalityStatus((DeserializedSimpleFunctionDescriptor)localObject4, (ReceiverParameterDescriptor)localObject3, (ReceiverParameterDescriptor)localObject6, (List)localObject2, (List)localObject1, localKotlinType, (Modality)localObject8, localVisibility, (Map)localObject5, ((Boolean)localObject7).booleanValue());
    localObject3 = Flags.IS_OPERATOR.get(i);
    Intrinsics.checkExpressionValueIsNotNull(localObject3, "Flags.IS_OPERATOR.get(flags)");
    ((DeserializedSimpleFunctionDescriptor)localObject4).setOperator(((Boolean)localObject3).booleanValue());
    localObject3 = Flags.IS_INFIX.get(i);
    Intrinsics.checkExpressionValueIsNotNull(localObject3, "Flags.IS_INFIX.get(flags)");
    ((DeserializedSimpleFunctionDescriptor)localObject4).setInfix(((Boolean)localObject3).booleanValue());
    localObject3 = Flags.IS_EXTERNAL_FUNCTION.get(i);
    Intrinsics.checkExpressionValueIsNotNull(localObject3, "Flags.IS_EXTERNAL_FUNCTION.get(flags)");
    ((DeserializedSimpleFunctionDescriptor)localObject4).setExternal(((Boolean)localObject3).booleanValue());
    localObject3 = Flags.IS_INLINE.get(i);
    Intrinsics.checkExpressionValueIsNotNull(localObject3, "Flags.IS_INLINE.get(flags)");
    ((DeserializedSimpleFunctionDescriptor)localObject4).setInline(((Boolean)localObject3).booleanValue());
    localObject3 = Flags.IS_TAILREC.get(i);
    Intrinsics.checkExpressionValueIsNotNull(localObject3, "Flags.IS_TAILREC.get(flags)");
    ((DeserializedSimpleFunctionDescriptor)localObject4).setTailrec(((Boolean)localObject3).booleanValue());
    localObject3 = Flags.IS_SUSPEND.get(i);
    Intrinsics.checkExpressionValueIsNotNull(localObject3, "Flags.IS_SUSPEND.get(flags)");
    ((DeserializedSimpleFunctionDescriptor)localObject4).setSuspend(((Boolean)localObject3).booleanValue());
    localObject3 = Flags.IS_EXPECT_FUNCTION.get(i);
    Intrinsics.checkExpressionValueIsNotNull(localObject3, "Flags.IS_EXPECT_FUNCTION.get(flags)");
    ((DeserializedSimpleFunctionDescriptor)localObject4).setExpect(((Boolean)localObject3).booleanValue());
    paramFunction = this.c.getComponents().getContractDeserializer().deserializeContractFromFunction(paramFunction, (FunctionDescriptor)localObject4, this.c.getTypeTable(), this.c.getTypeDeserializer());
    if (paramFunction != null) {
      ((DeserializedSimpleFunctionDescriptor)localObject4).putInUserDataMap((CallableDescriptor.UserDataKey)paramFunction.getFirst(), paramFunction.getSecond());
    }
    return (SimpleFunctionDescriptor)localObject4;
  }
  
  public final PropertyDescriptor loadProperty(final ProtoBuf.Property paramProperty)
  {
    Intrinsics.checkParameterIsNotNull(paramProperty, "proto");
    int i;
    if (paramProperty.hasFlags()) {
      i = paramProperty.getFlags();
    } else {
      i = loadOldFlags(paramProperty.getOldFlags());
    }
    Object localObject1 = this.c.getContainingDeclaration();
    Object localObject2 = (MessageLite)paramProperty;
    Object localObject3 = getAnnotations((MessageLite)localObject2, i, AnnotatedCallableKind.PROPERTY);
    Object localObject4 = ProtoEnumFlags.INSTANCE.modality((ProtoBuf.Modality)Flags.MODALITY.get(i));
    Object localObject5 = ProtoEnumFlags.INSTANCE.visibility((ProtoBuf.Visibility)Flags.VISIBILITY.get(i));
    Object localObject6 = Flags.IS_VAR.get(i);
    Intrinsics.checkExpressionValueIsNotNull(localObject6, "Flags.IS_VAR.get(flags)");
    boolean bool1 = ((Boolean)localObject6).booleanValue();
    Object localObject7 = NameResolverUtilKt.getName(this.c.getNameResolver(), paramProperty.getName());
    localObject6 = ProtoEnumFlags.INSTANCE.memberKind((ProtoBuf.MemberKind)Flags.MEMBER_KIND.get(i));
    Object localObject8 = Flags.IS_LATEINIT.get(i);
    Intrinsics.checkExpressionValueIsNotNull(localObject8, "Flags.IS_LATEINIT.get(flags)");
    boolean bool2 = ((Boolean)localObject8).booleanValue();
    localObject8 = Flags.IS_CONST.get(i);
    Intrinsics.checkExpressionValueIsNotNull(localObject8, "Flags.IS_CONST.get(flags)");
    boolean bool3 = ((Boolean)localObject8).booleanValue();
    localObject8 = Flags.IS_EXTERNAL_PROPERTY.get(i);
    Intrinsics.checkExpressionValueIsNotNull(localObject8, "Flags.IS_EXTERNAL_PROPERTY.get(flags)");
    boolean bool4 = ((Boolean)localObject8).booleanValue();
    localObject8 = Flags.IS_DELEGATED.get(i);
    Intrinsics.checkExpressionValueIsNotNull(localObject8, "Flags.IS_DELEGATED.get(flags)");
    boolean bool5 = ((Boolean)localObject8).booleanValue();
    localObject8 = Flags.IS_EXPECT_PROPERTY.get(i);
    Intrinsics.checkExpressionValueIsNotNull(localObject8, "Flags.IS_EXPECT_PROPERTY.get(flags)");
    localObject4 = new DeserializedPropertyDescriptor((DeclarationDescriptor)localObject1, null, (Annotations)localObject3, (Modality)localObject4, (Visibility)localObject5, bool1, (Name)localObject7, (CallableMemberDescriptor.Kind)localObject6, bool2, bool3, bool4, bool5, ((Boolean)localObject8).booleanValue(), paramProperty, this.c.getNameResolver(), this.c.getTypeTable(), this.c.getVersionRequirementTable(), this.c.getContainerSource());
    localObject1 = this.c;
    localObject5 = (DeclarationDescriptor)localObject4;
    localObject3 = paramProperty.getTypeParameterList();
    Intrinsics.checkExpressionValueIsNotNull(localObject3, "proto.typeParameterList");
    localObject6 = DeserializationContext.childContext$default((DeserializationContext)localObject1, (DeclarationDescriptor)localObject5, (List)localObject3, null, null, null, null, 60, null);
    localObject3 = Flags.HAS_GETTER.get(i);
    Intrinsics.checkExpressionValueIsNotNull(localObject3, "Flags.HAS_GETTER.get(flags)");
    bool3 = ((Boolean)localObject3).booleanValue();
    if ((bool3) && (ProtoTypeTableUtilKt.hasReceiver(paramProperty))) {
      localObject3 = getReceiverParameterAnnotations((MessageLite)localObject2, AnnotatedCallableKind.PROPERTY_GETTER);
    } else {
      localObject3 = Annotations.Companion.getEMPTY();
    }
    localObject1 = ((DeserializationContext)localObject6).getTypeDeserializer().type(ProtoTypeTableUtilKt.returnType(paramProperty, this.c.getTypeTable()));
    localObject8 = ((DeserializationContext)localObject6).getTypeDeserializer().getOwnTypeParameters();
    localObject7 = getDispatchReceiverParameter();
    Object localObject9 = ProtoTypeTableUtilKt.receiverType(paramProperty, this.c.getTypeTable());
    localObject5 = null;
    if (localObject9 != null)
    {
      localObject9 = ((DeserializationContext)localObject6).getTypeDeserializer().type((ProtoBuf.Type)localObject9);
      if (localObject9 != null)
      {
        localObject3 = DescriptorFactory.createExtensionReceiverParameterForCallable((CallableDescriptor)localObject4, (KotlinType)localObject9, (Annotations)localObject3);
        break label524;
      }
    }
    localObject3 = null;
    label524:
    ((DeserializedPropertyDescriptor)localObject4).setType((KotlinType)localObject1, (List)localObject8, (ReceiverParameterDescriptor)localObject7, (ReceiverParameterDescriptor)localObject3);
    localObject3 = Flags.HAS_ANNOTATIONS.get(i);
    Intrinsics.checkExpressionValueIsNotNull(localObject3, "Flags.HAS_ANNOTATIONS.get(flags)");
    int j = Flags.getAccessorFlags(((Boolean)localObject3).booleanValue(), (ProtoBuf.Visibility)Flags.VISIBILITY.get(i), (ProtoBuf.Modality)Flags.MODALITY.get(i), false, false, false);
    if (bool3)
    {
      int k;
      if (paramProperty.hasGetterFlags()) {
        k = paramProperty.getGetterFlags();
      } else {
        k = j;
      }
      localObject3 = Flags.IS_NOT_DEFAULT.get(k);
      Intrinsics.checkExpressionValueIsNotNull(localObject3, "Flags.IS_NOT_DEFAULT.get(getterFlags)");
      bool4 = ((Boolean)localObject3).booleanValue();
      localObject3 = Flags.IS_EXTERNAL_ACCESSOR.get(k);
      Intrinsics.checkExpressionValueIsNotNull(localObject3, "Flags.IS_EXTERNAL_ACCESSOR.get(getterFlags)");
      bool5 = ((Boolean)localObject3).booleanValue();
      localObject3 = Flags.IS_INLINE_ACCESSOR.get(k);
      Intrinsics.checkExpressionValueIsNotNull(localObject3, "Flags.IS_INLINE_ACCESSOR.get(getterFlags)");
      bool3 = ((Boolean)localObject3).booleanValue();
      localObject3 = getAnnotations((MessageLite)localObject2, k, AnnotatedCallableKind.PROPERTY_GETTER);
      if (bool4)
      {
        localObject3 = new PropertyGetterDescriptorImpl((PropertyDescriptor)localObject4, (Annotations)localObject3, ProtoEnumFlags.INSTANCE.modality((ProtoBuf.Modality)Flags.MODALITY.get(k)), ProtoEnumFlags.INSTANCE.visibility((ProtoBuf.Visibility)Flags.VISIBILITY.get(k)), bool4 ^ true, bool5, bool3, ((DeserializedPropertyDescriptor)localObject4).getKind(), null, SourceElement.NO_SOURCE);
      }
      else
      {
        localObject3 = DescriptorFactory.createDefaultGetter((PropertyDescriptor)localObject4, (Annotations)localObject3);
        Intrinsics.checkExpressionValueIsNotNull(localObject3, "DescriptorFactory.create…er(property, annotations)");
      }
      ((PropertyGetterDescriptorImpl)localObject3).initialize(((DeserializedPropertyDescriptor)localObject4).getReturnType());
      localObject1 = localObject3;
    }
    else
    {
      localObject1 = null;
    }
    localObject3 = Flags.HAS_SETTER.get(i);
    Intrinsics.checkExpressionValueIsNotNull(localObject3, "Flags.HAS_SETTER.get(flags)");
    if (((Boolean)localObject3).booleanValue())
    {
      if (paramProperty.hasSetterFlags()) {
        j = paramProperty.getSetterFlags();
      }
      localObject3 = Flags.IS_NOT_DEFAULT.get(j);
      Intrinsics.checkExpressionValueIsNotNull(localObject3, "Flags.IS_NOT_DEFAULT.get(setterFlags)");
      bool4 = ((Boolean)localObject3).booleanValue();
      localObject3 = Flags.IS_EXTERNAL_ACCESSOR.get(j);
      Intrinsics.checkExpressionValueIsNotNull(localObject3, "Flags.IS_EXTERNAL_ACCESSOR.get(setterFlags)");
      bool5 = ((Boolean)localObject3).booleanValue();
      localObject3 = Flags.IS_INLINE_ACCESSOR.get(j);
      Intrinsics.checkExpressionValueIsNotNull(localObject3, "Flags.IS_INLINE_ACCESSOR.get(setterFlags)");
      bool3 = ((Boolean)localObject3).booleanValue();
      localObject3 = getAnnotations((MessageLite)localObject2, j, AnnotatedCallableKind.PROPERTY_SETTER);
      if (bool4)
      {
        localObject3 = new PropertySetterDescriptorImpl((PropertyDescriptor)localObject4, (Annotations)localObject3, ProtoEnumFlags.INSTANCE.modality((ProtoBuf.Modality)Flags.MODALITY.get(j)), ProtoEnumFlags.INSTANCE.visibility((ProtoBuf.Visibility)Flags.VISIBILITY.get(j)), bool4 ^ true, bool5, bool3, ((DeserializedPropertyDescriptor)localObject4).getKind(), null, SourceElement.NO_SOURCE);
        ((PropertySetterDescriptorImpl)localObject3).initialize((ValueParameterDescriptor)CollectionsKt.single(DeserializationContext.childContext$default((DeserializationContext)localObject6, (DeclarationDescriptor)localObject3, CollectionsKt.emptyList(), null, null, null, null, 60, null).getMemberDeserializer().valueParameters(CollectionsKt.listOf(paramProperty.getSetterValueParameter()), (MessageLite)localObject2, AnnotatedCallableKind.PROPERTY_SETTER)));
      }
      else
      {
        localObject3 = DescriptorFactory.createDefaultSetter((PropertyDescriptor)localObject4, (Annotations)localObject3, Annotations.Companion.getEMPTY());
        Intrinsics.checkExpressionValueIsNotNull(localObject3, "DescriptorFactory.create…ptor */\n                )");
      }
    }
    else
    {
      localObject3 = localObject5;
    }
    localObject2 = Flags.HAS_CONSTANT.get(i);
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "Flags.HAS_CONSTANT.get(flags)");
    if (((Boolean)localObject2).booleanValue()) {
      ((DeserializedPropertyDescriptor)localObject4).setCompileTimeInitializer(this.c.getStorageManager().createNullableLazyValue((Function0)new Lambda(paramProperty)
      {
        public final ConstantValue<?> invoke()
        {
          Object localObject = this.this$0;
          ProtoContainer localProtoContainer = MemberDeserializer.access$asProtoContainer((MemberDeserializer)localObject, MemberDeserializer.access$getC$p((MemberDeserializer)localObject).getContainingDeclaration());
          if (localProtoContainer == null) {
            Intrinsics.throwNpe();
          }
          localObject = MemberDeserializer.access$getC$p(this.this$0).getComponents().getAnnotationAndConstantLoader();
          ProtoBuf.Property localProperty = paramProperty;
          KotlinType localKotlinType = this.$property.getReturnType();
          Intrinsics.checkExpressionValueIsNotNull(localKotlinType, "property.returnType");
          return (ConstantValue)((AnnotationAndConstantLoader)localObject).loadPropertyConstant(localProtoContainer, localProperty, localKotlinType);
        }
      }));
    }
    localObject5 = (PropertySetterDescriptor)localObject3;
    localObject3 = getPropertyFieldAnnotations(paramProperty, false);
    localObject2 = (PropertyDescriptor)localObject4;
    ((DeserializedPropertyDescriptor)localObject4).initialize((PropertyGetterDescriptorImpl)localObject1, (PropertySetterDescriptor)localObject5, (FieldDescriptor)new FieldDescriptorImpl((Annotations)localObject3, (PropertyDescriptor)localObject2), (FieldDescriptor)new FieldDescriptorImpl(getPropertyFieldAnnotations(paramProperty, true), (PropertyDescriptor)localObject2), checkExperimentalCoroutine((DeserializedMemberDescriptor)localObject4, ((DeserializationContext)localObject6).getTypeDeserializer()));
    return localObject2;
  }
  
  public final TypeAliasDescriptor loadTypeAlias(ProtoBuf.TypeAlias paramTypeAlias)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeAlias, "proto");
    Object localObject1 = Annotations.Companion;
    Object localObject2 = paramTypeAlias.getAnnotationList();
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "proto.annotationList");
    Object localObject3 = (Iterable)localObject2;
    localObject2 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject3, 10));
    Object localObject4 = ((Iterable)localObject3).iterator();
    while (((Iterator)localObject4).hasNext())
    {
      localObject3 = (ProtoBuf.Annotation)((Iterator)localObject4).next();
      AnnotationDeserializer localAnnotationDeserializer = this.annotationDeserializer;
      Intrinsics.checkExpressionValueIsNotNull(localObject3, "it");
      ((Collection)localObject2).add(localAnnotationDeserializer.deserializeAnnotation((ProtoBuf.Annotation)localObject3, this.c.getNameResolver()));
    }
    localObject2 = ((Annotations.Companion)localObject1).create((List)localObject2);
    localObject1 = ProtoEnumFlags.INSTANCE.visibility((ProtoBuf.Visibility)Flags.VISIBILITY.get(paramTypeAlias.getFlags()));
    localObject1 = new DeserializedTypeAliasDescriptor(this.c.getStorageManager(), this.c.getContainingDeclaration(), (Annotations)localObject2, NameResolverUtilKt.getName(this.c.getNameResolver(), paramTypeAlias.getName()), (Visibility)localObject1, paramTypeAlias, this.c.getNameResolver(), this.c.getTypeTable(), this.c.getVersionRequirementTable(), this.c.getContainerSource());
    localObject2 = this.c;
    localObject3 = (DeclarationDescriptor)localObject1;
    localObject4 = paramTypeAlias.getTypeParameterList();
    Intrinsics.checkExpressionValueIsNotNull(localObject4, "proto.typeParameterList");
    localObject2 = DeserializationContext.childContext$default((DeserializationContext)localObject2, (DeclarationDescriptor)localObject3, (List)localObject4, null, null, null, null, 60, null);
    ((DeserializedTypeAliasDescriptor)localObject1).initialize(((DeserializationContext)localObject2).getTypeDeserializer().getOwnTypeParameters(), ((DeserializationContext)localObject2).getTypeDeserializer().simpleType(ProtoTypeTableUtilKt.underlyingType(paramTypeAlias, this.c.getTypeTable())), ((DeserializationContext)localObject2).getTypeDeserializer().simpleType(ProtoTypeTableUtilKt.expandedType(paramTypeAlias, this.c.getTypeTable())), checkExperimentalCoroutine((DeserializedMemberDescriptor)localObject1, ((DeserializationContext)localObject2).getTypeDeserializer()));
    return (TypeAliasDescriptor)localObject1;
  }
}

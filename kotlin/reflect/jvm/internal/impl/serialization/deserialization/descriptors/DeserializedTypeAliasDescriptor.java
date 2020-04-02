package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterUtilsKt;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.AbstractTypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeAliasConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeAlias;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirement;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutionKt;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.Variance;

public final class DeserializedTypeAliasDescriptor
  extends AbstractTypeAliasDescriptor
  implements DeserializedMemberDescriptor
{
  private Collection<? extends TypeAliasConstructorDescriptor> constructors;
  private final DeserializedContainerSource containerSource;
  private DeserializedMemberDescriptor.CoroutinesCompatibilityMode coroutinesExperimentalCompatibilityMode;
  private SimpleType defaultTypeImpl;
  private SimpleType expandedType;
  private final NameResolver nameResolver;
  private final ProtoBuf.TypeAlias proto;
  private final StorageManager storageManager;
  private List<? extends TypeParameterDescriptor> typeConstructorParameters;
  private final TypeTable typeTable;
  private SimpleType underlyingType;
  private final VersionRequirementTable versionRequirementTable;
  
  public DeserializedTypeAliasDescriptor(StorageManager paramStorageManager, DeclarationDescriptor paramDeclarationDescriptor, Annotations paramAnnotations, Name paramName, Visibility paramVisibility, ProtoBuf.TypeAlias paramTypeAlias, NameResolver paramNameResolver, TypeTable paramTypeTable, VersionRequirementTable paramVersionRequirementTable, DeserializedContainerSource paramDeserializedContainerSource)
  {
    super(paramDeclarationDescriptor, paramAnnotations, paramName, localSourceElement, paramVisibility);
    this.storageManager = paramStorageManager;
    this.proto = paramTypeAlias;
    this.nameResolver = paramNameResolver;
    this.typeTable = paramTypeTable;
    this.versionRequirementTable = paramVersionRequirementTable;
    this.containerSource = paramDeserializedContainerSource;
    this.coroutinesExperimentalCompatibilityMode = DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
  }
  
  public ClassDescriptor getClassDescriptor()
  {
    boolean bool = KotlinTypeKt.isError((KotlinType)getExpandedType());
    ClassifierDescriptor localClassifierDescriptor = null;
    Object localObject = null;
    if (bool)
    {
      localObject = localClassifierDescriptor;
    }
    else
    {
      localClassifierDescriptor = getExpandedType().getConstructor().getDeclarationDescriptor();
      if ((localClassifierDescriptor instanceof ClassDescriptor)) {
        localObject = localClassifierDescriptor;
      }
      localObject = (ClassDescriptor)localObject;
    }
    return localObject;
  }
  
  public DeserializedContainerSource getContainerSource()
  {
    return this.containerSource;
  }
  
  public DeserializedMemberDescriptor.CoroutinesCompatibilityMode getCoroutinesExperimentalCompatibilityMode()
  {
    return this.coroutinesExperimentalCompatibilityMode;
  }
  
  public SimpleType getDefaultType()
  {
    SimpleType localSimpleType = this.defaultTypeImpl;
    if (localSimpleType == null) {
      Intrinsics.throwUninitializedPropertyAccessException("defaultTypeImpl");
    }
    return localSimpleType;
  }
  
  public SimpleType getExpandedType()
  {
    SimpleType localSimpleType = this.expandedType;
    if (localSimpleType == null) {
      Intrinsics.throwUninitializedPropertyAccessException("expandedType");
    }
    return localSimpleType;
  }
  
  public NameResolver getNameResolver()
  {
    return this.nameResolver;
  }
  
  public ProtoBuf.TypeAlias getProto()
  {
    return this.proto;
  }
  
  protected StorageManager getStorageManager()
  {
    return this.storageManager;
  }
  
  protected List<TypeParameterDescriptor> getTypeConstructorTypeParameters()
  {
    List localList = this.typeConstructorParameters;
    if (localList == null) {
      Intrinsics.throwUninitializedPropertyAccessException("typeConstructorParameters");
    }
    return localList;
  }
  
  public TypeTable getTypeTable()
  {
    return this.typeTable;
  }
  
  public SimpleType getUnderlyingType()
  {
    SimpleType localSimpleType = this.underlyingType;
    if (localSimpleType == null) {
      Intrinsics.throwUninitializedPropertyAccessException("underlyingType");
    }
    return localSimpleType;
  }
  
  public VersionRequirementTable getVersionRequirementTable()
  {
    return this.versionRequirementTable;
  }
  
  public List<VersionRequirement> getVersionRequirements()
  {
    return DeserializedMemberDescriptor.DefaultImpls.getVersionRequirements(this);
  }
  
  public final void initialize(List<? extends TypeParameterDescriptor> paramList, SimpleType paramSimpleType1, SimpleType paramSimpleType2, DeserializedMemberDescriptor.CoroutinesCompatibilityMode paramCoroutinesCompatibilityMode)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "declaredTypeParameters");
    Intrinsics.checkParameterIsNotNull(paramSimpleType1, "underlyingType");
    Intrinsics.checkParameterIsNotNull(paramSimpleType2, "expandedType");
    Intrinsics.checkParameterIsNotNull(paramCoroutinesCompatibilityMode, "isExperimentalCoroutineInReleaseEnvironment");
    initialize(paramList);
    this.underlyingType = paramSimpleType1;
    this.expandedType = paramSimpleType2;
    this.typeConstructorParameters = TypeParameterUtilsKt.computeConstructorTypeParameters(this);
    this.defaultTypeImpl = computeDefaultType();
    this.constructors = getTypeAliasConstructors();
    this.coroutinesExperimentalCompatibilityMode = paramCoroutinesCompatibilityMode;
  }
  
  public TypeAliasDescriptor substitute(TypeSubstitutor paramTypeSubstitutor)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeSubstitutor, "substitutor");
    if (paramTypeSubstitutor.isEmpty()) {
      return (TypeAliasDescriptor)this;
    }
    StorageManager localStorageManager = getStorageManager();
    Object localObject1 = getContainingDeclaration();
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "containingDeclaration");
    Object localObject2 = getAnnotations();
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "annotations");
    Object localObject3 = getName();
    Intrinsics.checkExpressionValueIsNotNull(localObject3, "name");
    localObject2 = new DeserializedTypeAliasDescriptor(localStorageManager, (DeclarationDescriptor)localObject1, (Annotations)localObject2, (Name)localObject3, getVisibility(), getProto(), getNameResolver(), getTypeTable(), getVersionRequirementTable(), getContainerSource());
    localObject1 = getDeclaredTypeParameters();
    localObject3 = paramTypeSubstitutor.safeSubstitute((KotlinType)getUnderlyingType(), Variance.INVARIANT);
    Intrinsics.checkExpressionValueIsNotNull(localObject3, "substitutor.safeSubstitu…Type, Variance.INVARIANT)");
    localObject3 = TypeSubstitutionKt.asSimpleType((KotlinType)localObject3);
    paramTypeSubstitutor = paramTypeSubstitutor.safeSubstitute((KotlinType)getExpandedType(), Variance.INVARIANT);
    Intrinsics.checkExpressionValueIsNotNull(paramTypeSubstitutor, "substitutor.safeSubstitu…Type, Variance.INVARIANT)");
    ((DeserializedTypeAliasDescriptor)localObject2).initialize((List)localObject1, (SimpleType)localObject3, TypeSubstitutionKt.asSimpleType(paramTypeSubstitutor), getCoroutinesExperimentalCompatibilityMode());
    return (TypeAliasDescriptor)localObject2;
  }
}

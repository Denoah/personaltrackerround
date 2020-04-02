package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.List;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FieldDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyGetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Property;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags.BooleanFlagField;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirement;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.name.Name;

public final class DeserializedPropertyDescriptor
  extends PropertyDescriptorImpl
  implements DeserializedCallableMemberDescriptor
{
  private final DeserializedContainerSource containerSource;
  private DeserializedMemberDescriptor.CoroutinesCompatibilityMode coroutinesExperimentalCompatibilityMode;
  private final NameResolver nameResolver;
  private final ProtoBuf.Property proto;
  private final TypeTable typeTable;
  private final VersionRequirementTable versionRequirementTable;
  
  public DeserializedPropertyDescriptor(DeclarationDescriptor paramDeclarationDescriptor, PropertyDescriptor paramPropertyDescriptor, Annotations paramAnnotations, Modality paramModality, Visibility paramVisibility, boolean paramBoolean1, Name paramName, CallableMemberDescriptor.Kind paramKind, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, ProtoBuf.Property paramProperty, NameResolver paramNameResolver, TypeTable paramTypeTable, VersionRequirementTable paramVersionRequirementTable, DeserializedContainerSource paramDeserializedContainerSource)
  {
    super(paramDeclarationDescriptor, paramPropertyDescriptor, paramAnnotations, paramModality, paramVisibility, paramBoolean1, paramName, paramKind, SourceElement.NO_SOURCE, paramBoolean2, paramBoolean3, paramBoolean6, false, paramBoolean4, paramBoolean5);
    this.proto = paramProperty;
    this.nameResolver = paramNameResolver;
    this.typeTable = paramTypeTable;
    this.versionRequirementTable = paramVersionRequirementTable;
    this.containerSource = paramDeserializedContainerSource;
    this.coroutinesExperimentalCompatibilityMode = DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
  }
  
  protected PropertyDescriptorImpl createSubstitutedCopy(DeclarationDescriptor paramDeclarationDescriptor, Modality paramModality, Visibility paramVisibility, PropertyDescriptor paramPropertyDescriptor, CallableMemberDescriptor.Kind paramKind, Name paramName, SourceElement paramSourceElement)
  {
    Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptor, "newOwner");
    Intrinsics.checkParameterIsNotNull(paramModality, "newModality");
    Intrinsics.checkParameterIsNotNull(paramVisibility, "newVisibility");
    Intrinsics.checkParameterIsNotNull(paramKind, "kind");
    Intrinsics.checkParameterIsNotNull(paramName, "newName");
    Intrinsics.checkParameterIsNotNull(paramSourceElement, "source");
    return (PropertyDescriptorImpl)new DeserializedPropertyDescriptor(paramDeclarationDescriptor, paramPropertyDescriptor, getAnnotations(), paramModality, paramVisibility, isVar(), paramName, paramKind, isLateInit(), isConst(), isExternal(), isDelegated(), isExpect(), getProto(), getNameResolver(), getTypeTable(), getVersionRequirementTable(), getContainerSource());
  }
  
  public DeserializedContainerSource getContainerSource()
  {
    return this.containerSource;
  }
  
  public NameResolver getNameResolver()
  {
    return this.nameResolver;
  }
  
  public ProtoBuf.Property getProto()
  {
    return this.proto;
  }
  
  public TypeTable getTypeTable()
  {
    return this.typeTable;
  }
  
  public VersionRequirementTable getVersionRequirementTable()
  {
    return this.versionRequirementTable;
  }
  
  public List<VersionRequirement> getVersionRequirements()
  {
    return DeserializedCallableMemberDescriptor.DefaultImpls.getVersionRequirements(this);
  }
  
  public final void initialize(PropertyGetterDescriptorImpl paramPropertyGetterDescriptorImpl, PropertySetterDescriptor paramPropertySetterDescriptor, FieldDescriptor paramFieldDescriptor1, FieldDescriptor paramFieldDescriptor2, DeserializedMemberDescriptor.CoroutinesCompatibilityMode paramCoroutinesCompatibilityMode)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutinesCompatibilityMode, "isExperimentalCoroutineInReleaseEnvironment");
    super.initialize(paramPropertyGetterDescriptorImpl, paramPropertySetterDescriptor, paramFieldDescriptor1, paramFieldDescriptor2);
    paramPropertyGetterDescriptorImpl = Unit.INSTANCE;
    this.coroutinesExperimentalCompatibilityMode = paramCoroutinesCompatibilityMode;
  }
  
  public boolean isExternal()
  {
    Boolean localBoolean = Flags.IS_EXTERNAL_PROPERTY.get(getProto().getFlags());
    Intrinsics.checkExpressionValueIsNotNull(localBoolean, "Flags.IS_EXTERNAL_PROPERTY.get(proto.flags)");
    return localBoolean.booleanValue();
  }
}

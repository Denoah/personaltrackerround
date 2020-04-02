package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassConstructorDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Constructor;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirement;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.name.Name;

public final class DeserializedClassConstructorDescriptor
  extends ClassConstructorDescriptorImpl
  implements DeserializedCallableMemberDescriptor
{
  private final DeserializedContainerSource containerSource;
  private DeserializedMemberDescriptor.CoroutinesCompatibilityMode coroutinesExperimentalCompatibilityMode;
  private final NameResolver nameResolver;
  private final ProtoBuf.Constructor proto;
  private final TypeTable typeTable;
  private final VersionRequirementTable versionRequirementTable;
  
  public DeserializedClassConstructorDescriptor(ClassDescriptor paramClassDescriptor, ConstructorDescriptor paramConstructorDescriptor, Annotations paramAnnotations, boolean paramBoolean, CallableMemberDescriptor.Kind paramKind, ProtoBuf.Constructor paramConstructor, NameResolver paramNameResolver, TypeTable paramTypeTable, VersionRequirementTable paramVersionRequirementTable, DeserializedContainerSource paramDeserializedContainerSource, SourceElement paramSourceElement)
  {
    super(paramClassDescriptor, paramConstructorDescriptor, paramAnnotations, paramBoolean, paramKind, paramSourceElement);
    this.proto = paramConstructor;
    this.nameResolver = paramNameResolver;
    this.typeTable = paramTypeTable;
    this.versionRequirementTable = paramVersionRequirementTable;
    this.containerSource = paramDeserializedContainerSource;
    this.coroutinesExperimentalCompatibilityMode = DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
  }
  
  protected DeserializedClassConstructorDescriptor createSubstitutedCopy(DeclarationDescriptor paramDeclarationDescriptor, FunctionDescriptor paramFunctionDescriptor, CallableMemberDescriptor.Kind paramKind, Name paramName, Annotations paramAnnotations, SourceElement paramSourceElement)
  {
    Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptor, "newOwner");
    Intrinsics.checkParameterIsNotNull(paramKind, "kind");
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "annotations");
    Intrinsics.checkParameterIsNotNull(paramSourceElement, "source");
    paramDeclarationDescriptor = new DeserializedClassConstructorDescriptor((ClassDescriptor)paramDeclarationDescriptor, (ConstructorDescriptor)paramFunctionDescriptor, paramAnnotations, this.isPrimary, paramKind, getProto(), getNameResolver(), getTypeTable(), getVersionRequirementTable(), getContainerSource(), paramSourceElement);
    paramDeclarationDescriptor.setCoroutinesExperimentalCompatibilityMode$deserialization(getCoroutinesExperimentalCompatibilityMode());
    return paramDeclarationDescriptor;
  }
  
  public DeserializedContainerSource getContainerSource()
  {
    return this.containerSource;
  }
  
  public DeserializedMemberDescriptor.CoroutinesCompatibilityMode getCoroutinesExperimentalCompatibilityMode()
  {
    return this.coroutinesExperimentalCompatibilityMode;
  }
  
  public NameResolver getNameResolver()
  {
    return this.nameResolver;
  }
  
  public ProtoBuf.Constructor getProto()
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
  
  public boolean isExternal()
  {
    return false;
  }
  
  public boolean isInline()
  {
    return false;
  }
  
  public boolean isSuspend()
  {
    return false;
  }
  
  public boolean isTailrec()
  {
    return false;
  }
  
  public void setCoroutinesExperimentalCompatibilityMode$deserialization(DeserializedMemberDescriptor.CoroutinesCompatibilityMode paramCoroutinesCompatibilityMode)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutinesCompatibilityMode, "<set-?>");
    this.coroutinesExperimentalCompatibilityMode = paramCoroutinesCompatibilityMode;
  }
}

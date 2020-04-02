package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor.UserDataKey;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.SimpleFunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Function;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirement;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public final class DeserializedSimpleFunctionDescriptor
  extends SimpleFunctionDescriptorImpl
  implements DeserializedCallableMemberDescriptor
{
  private final DeserializedContainerSource containerSource;
  private DeserializedMemberDescriptor.CoroutinesCompatibilityMode coroutinesExperimentalCompatibilityMode;
  private final NameResolver nameResolver;
  private final ProtoBuf.Function proto;
  private final TypeTable typeTable;
  private final VersionRequirementTable versionRequirementTable;
  
  public DeserializedSimpleFunctionDescriptor(DeclarationDescriptor paramDeclarationDescriptor, SimpleFunctionDescriptor paramSimpleFunctionDescriptor, Annotations paramAnnotations, Name paramName, CallableMemberDescriptor.Kind paramKind, ProtoBuf.Function paramFunction, NameResolver paramNameResolver, TypeTable paramTypeTable, VersionRequirementTable paramVersionRequirementTable, DeserializedContainerSource paramDeserializedContainerSource, SourceElement paramSourceElement)
  {
    super(paramDeclarationDescriptor, paramSimpleFunctionDescriptor, paramAnnotations, paramName, paramKind, paramSourceElement);
    this.proto = paramFunction;
    this.nameResolver = paramNameResolver;
    this.typeTable = paramTypeTable;
    this.versionRequirementTable = paramVersionRequirementTable;
    this.containerSource = paramDeserializedContainerSource;
    this.coroutinesExperimentalCompatibilityMode = DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
  }
  
  protected FunctionDescriptorImpl createSubstitutedCopy(DeclarationDescriptor paramDeclarationDescriptor, FunctionDescriptor paramFunctionDescriptor, CallableMemberDescriptor.Kind paramKind, Name paramName, Annotations paramAnnotations, SourceElement paramSourceElement)
  {
    Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptor, "newOwner");
    Intrinsics.checkParameterIsNotNull(paramKind, "kind");
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "annotations");
    Intrinsics.checkParameterIsNotNull(paramSourceElement, "source");
    SimpleFunctionDescriptor localSimpleFunctionDescriptor = (SimpleFunctionDescriptor)paramFunctionDescriptor;
    if (paramName != null)
    {
      paramFunctionDescriptor = paramName;
    }
    else
    {
      paramFunctionDescriptor = getName();
      Intrinsics.checkExpressionValueIsNotNull(paramFunctionDescriptor, "name");
    }
    paramDeclarationDescriptor = new DeserializedSimpleFunctionDescriptor(paramDeclarationDescriptor, localSimpleFunctionDescriptor, paramAnnotations, paramFunctionDescriptor, paramKind, getProto(), getNameResolver(), getTypeTable(), getVersionRequirementTable(), getContainerSource(), paramSourceElement);
    paramDeclarationDescriptor.coroutinesExperimentalCompatibilityMode = getCoroutinesExperimentalCompatibilityMode();
    return (FunctionDescriptorImpl)paramDeclarationDescriptor;
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
  
  public ProtoBuf.Function getProto()
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
  
  public final SimpleFunctionDescriptorImpl initialize(ReceiverParameterDescriptor paramReceiverParameterDescriptor1, ReceiverParameterDescriptor paramReceiverParameterDescriptor2, List<? extends TypeParameterDescriptor> paramList, List<? extends ValueParameterDescriptor> paramList1, KotlinType paramKotlinType, Modality paramModality, Visibility paramVisibility, Map<? extends CallableDescriptor.UserDataKey<?>, ?> paramMap, DeserializedMemberDescriptor.CoroutinesCompatibilityMode paramCoroutinesCompatibilityMode)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "typeParameters");
    Intrinsics.checkParameterIsNotNull(paramList1, "unsubstitutedValueParameters");
    Intrinsics.checkParameterIsNotNull(paramVisibility, "visibility");
    Intrinsics.checkParameterIsNotNull(paramMap, "userDataMap");
    Intrinsics.checkParameterIsNotNull(paramCoroutinesCompatibilityMode, "isExperimentalCoroutineInReleaseEnvironment");
    paramReceiverParameterDescriptor1 = super.initialize(paramReceiverParameterDescriptor1, paramReceiverParameterDescriptor2, paramList, paramList1, paramKotlinType, paramModality, paramVisibility, paramMap);
    this.coroutinesExperimentalCompatibilityMode = paramCoroutinesCompatibilityMode;
    Intrinsics.checkExpressionValueIsNotNull(paramReceiverParameterDescriptor1, "super.initialize(\n      …easeEnvironment\n        }");
    return paramReceiverParameterDescriptor1;
  }
}

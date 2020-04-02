package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeParameter;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionSpecificBehaviorKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedContainerSource;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;

public final class DeserializationContext
{
  private final DeserializationComponents components;
  private final DeserializedContainerSource containerSource;
  private final DeclarationDescriptor containingDeclaration;
  private final MemberDeserializer memberDeserializer;
  private final BinaryVersion metadataVersion;
  private final NameResolver nameResolver;
  private final TypeDeserializer typeDeserializer;
  private final TypeTable typeTable;
  private final VersionRequirementTable versionRequirementTable;
  
  public DeserializationContext(DeserializationComponents paramDeserializationComponents, NameResolver paramNameResolver, DeclarationDescriptor paramDeclarationDescriptor, TypeTable paramTypeTable, VersionRequirementTable paramVersionRequirementTable, BinaryVersion paramBinaryVersion, DeserializedContainerSource paramDeserializedContainerSource, TypeDeserializer paramTypeDeserializer, List<ProtoBuf.TypeParameter> paramList)
  {
    this.components = paramDeserializationComponents;
    this.nameResolver = paramNameResolver;
    this.containingDeclaration = paramDeclarationDescriptor;
    this.typeTable = paramTypeTable;
    this.versionRequirementTable = paramVersionRequirementTable;
    this.metadataVersion = paramBinaryVersion;
    this.containerSource = paramDeserializedContainerSource;
    paramDeserializationComponents = new StringBuilder();
    paramDeserializationComponents.append("Deserializer for \"");
    paramDeserializationComponents.append(this.containingDeclaration.getName());
    paramDeserializationComponents.append('"');
    paramNameResolver = paramDeserializationComponents.toString();
    paramDeserializationComponents = this.containerSource;
    if (paramDeserializationComponents != null)
    {
      paramDeserializationComponents = paramDeserializationComponents.getPresentableString();
      if (paramDeserializationComponents != null) {}
    }
    else
    {
      paramDeserializationComponents = "[container not found]";
    }
    this.typeDeserializer = new TypeDeserializer(this, paramTypeDeserializer, paramList, paramNameResolver, paramDeserializationComponents, false, 32, null);
    this.memberDeserializer = new MemberDeserializer(this);
  }
  
  public final DeserializationContext childContext(DeclarationDescriptor paramDeclarationDescriptor, List<ProtoBuf.TypeParameter> paramList, NameResolver paramNameResolver, TypeTable paramTypeTable, VersionRequirementTable paramVersionRequirementTable, BinaryVersion paramBinaryVersion)
  {
    Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptor, "descriptor");
    Intrinsics.checkParameterIsNotNull(paramList, "typeParameterProtos");
    Intrinsics.checkParameterIsNotNull(paramNameResolver, "nameResolver");
    Intrinsics.checkParameterIsNotNull(paramTypeTable, "typeTable");
    Intrinsics.checkParameterIsNotNull(paramVersionRequirementTable, "versionRequirementTable");
    Intrinsics.checkParameterIsNotNull(paramBinaryVersion, "metadataVersion");
    DeserializationComponents localDeserializationComponents = this.components;
    if (!VersionSpecificBehaviorKt.isVersionRequirementTableWrittenCorrectly(paramBinaryVersion)) {
      paramVersionRequirementTable = this.versionRequirementTable;
    }
    return new DeserializationContext(localDeserializationComponents, paramNameResolver, paramDeclarationDescriptor, paramTypeTable, paramVersionRequirementTable, paramBinaryVersion, this.containerSource, this.typeDeserializer, paramList);
  }
  
  public final DeserializationComponents getComponents()
  {
    return this.components;
  }
  
  public final DeserializedContainerSource getContainerSource()
  {
    return this.containerSource;
  }
  
  public final DeclarationDescriptor getContainingDeclaration()
  {
    return this.containingDeclaration;
  }
  
  public final MemberDeserializer getMemberDeserializer()
  {
    return this.memberDeserializer;
  }
  
  public final NameResolver getNameResolver()
  {
    return this.nameResolver;
  }
  
  public final StorageManager getStorageManager()
  {
    return this.components.getStorageManager();
  }
  
  public final TypeDeserializer getTypeDeserializer()
  {
    return this.typeDeserializer;
  }
  
  public final TypeTable getTypeTable()
  {
    return this.typeTable;
  }
  
  public final VersionRequirementTable getVersionRequirementTable()
  {
    return this.versionRequirementTable;
  }
}

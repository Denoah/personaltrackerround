package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirement;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirement.Companion;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;

public abstract interface DeserializedMemberDescriptor
  extends MemberDescriptor, DescriptorWithContainerSource
{
  public abstract NameResolver getNameResolver();
  
  public abstract MessageLite getProto();
  
  public abstract TypeTable getTypeTable();
  
  public abstract VersionRequirementTable getVersionRequirementTable();
  
  public abstract List<VersionRequirement> getVersionRequirements();
  
  public static enum CoroutinesCompatibilityMode
  {
    static
    {
      CoroutinesCompatibilityMode localCoroutinesCompatibilityMode1 = new CoroutinesCompatibilityMode("COMPATIBLE", 0);
      COMPATIBLE = localCoroutinesCompatibilityMode1;
      CoroutinesCompatibilityMode localCoroutinesCompatibilityMode2 = new CoroutinesCompatibilityMode("NEEDS_WRAPPER", 1);
      NEEDS_WRAPPER = localCoroutinesCompatibilityMode2;
      CoroutinesCompatibilityMode localCoroutinesCompatibilityMode3 = new CoroutinesCompatibilityMode("INCOMPATIBLE", 2);
      INCOMPATIBLE = localCoroutinesCompatibilityMode3;
      $VALUES = new CoroutinesCompatibilityMode[] { localCoroutinesCompatibilityMode1, localCoroutinesCompatibilityMode2, localCoroutinesCompatibilityMode3 };
    }
    
    private CoroutinesCompatibilityMode() {}
  }
  
  public static final class DefaultImpls
  {
    public static List<VersionRequirement> getVersionRequirements(DeserializedMemberDescriptor paramDeserializedMemberDescriptor)
    {
      return VersionRequirement.Companion.create(paramDeserializedMemberDescriptor.getProto(), paramDeserializedMemberDescriptor.getNameResolver(), paramDeserializedMemberDescriptor.getVersionRequirementTable());
    }
  }
}

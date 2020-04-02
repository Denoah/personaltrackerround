package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirement;

public abstract interface DeserializedCallableMemberDescriptor
  extends CallableMemberDescriptor, DeserializedMemberDescriptor
{
  public static final class DefaultImpls
  {
    public static List<VersionRequirement> getVersionRequirements(DeserializedCallableMemberDescriptor paramDeserializedCallableMemberDescriptor)
    {
      return DeserializedMemberDescriptor.DefaultImpls.getVersionRequirements((DeserializedMemberDescriptor)paramDeserializedCallableMemberDescriptor);
    }
  }
}

package kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;

public class ImplicitClassReceiver
  implements ImplicitReceiver, ThisClassReceiver
{
  private final ClassDescriptor classDescriptor;
  private final ClassDescriptor declarationDescriptor;
  private final ImplicitClassReceiver original;
  
  public ImplicitClassReceiver(ClassDescriptor paramClassDescriptor, ImplicitClassReceiver paramImplicitClassReceiver)
  {
    this.classDescriptor = paramClassDescriptor;
    if (paramImplicitClassReceiver == null) {
      paramImplicitClassReceiver = this;
    }
    this.original = paramImplicitClassReceiver;
    this.declarationDescriptor = this.classDescriptor;
  }
  
  public boolean equals(Object paramObject)
  {
    ClassDescriptor localClassDescriptor = this.classDescriptor;
    boolean bool = paramObject instanceof ImplicitClassReceiver;
    Object localObject = null;
    if (!bool) {
      paramObject = null;
    }
    ImplicitClassReceiver localImplicitClassReceiver = (ImplicitClassReceiver)paramObject;
    paramObject = localObject;
    if (localImplicitClassReceiver != null) {
      paramObject = localImplicitClassReceiver.classDescriptor;
    }
    return Intrinsics.areEqual(localClassDescriptor, paramObject);
  }
  
  public final ClassDescriptor getClassDescriptor()
  {
    return this.classDescriptor;
  }
  
  public SimpleType getType()
  {
    SimpleType localSimpleType = this.classDescriptor.getDefaultType();
    Intrinsics.checkExpressionValueIsNotNull(localSimpleType, "classDescriptor.defaultType");
    return localSimpleType;
  }
  
  public int hashCode()
  {
    return this.classDescriptor.hashCode();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Class{");
    localStringBuilder.append(getType());
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
}

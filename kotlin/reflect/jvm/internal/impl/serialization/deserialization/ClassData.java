package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Class;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;

public final class ClassData
{
  private final ProtoBuf.Class classProto;
  private final BinaryVersion metadataVersion;
  private final NameResolver nameResolver;
  private final SourceElement sourceElement;
  
  public ClassData(NameResolver paramNameResolver, ProtoBuf.Class paramClass, BinaryVersion paramBinaryVersion, SourceElement paramSourceElement)
  {
    this.nameResolver = paramNameResolver;
    this.classProto = paramClass;
    this.metadataVersion = paramBinaryVersion;
    this.sourceElement = paramSourceElement;
  }
  
  public final NameResolver component1()
  {
    return this.nameResolver;
  }
  
  public final ProtoBuf.Class component2()
  {
    return this.classProto;
  }
  
  public final BinaryVersion component3()
  {
    return this.metadataVersion;
  }
  
  public final SourceElement component4()
  {
    return this.sourceElement;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof ClassData))
      {
        paramObject = (ClassData)paramObject;
        if ((Intrinsics.areEqual(this.nameResolver, paramObject.nameResolver)) && (Intrinsics.areEqual(this.classProto, paramObject.classProto)) && (Intrinsics.areEqual(this.metadataVersion, paramObject.metadataVersion)) && (Intrinsics.areEqual(this.sourceElement, paramObject.sourceElement))) {}
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  public int hashCode()
  {
    Object localObject = this.nameResolver;
    int i = 0;
    int j;
    if (localObject != null) {
      j = localObject.hashCode();
    } else {
      j = 0;
    }
    localObject = this.classProto;
    int k;
    if (localObject != null) {
      k = localObject.hashCode();
    } else {
      k = 0;
    }
    localObject = this.metadataVersion;
    int m;
    if (localObject != null) {
      m = localObject.hashCode();
    } else {
      m = 0;
    }
    localObject = this.sourceElement;
    if (localObject != null) {
      i = localObject.hashCode();
    }
    return ((j * 31 + k) * 31 + m) * 31 + i;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ClassData(nameResolver=");
    localStringBuilder.append(this.nameResolver);
    localStringBuilder.append(", classProto=");
    localStringBuilder.append(this.classProto);
    localStringBuilder.append(", metadataVersion=");
    localStringBuilder.append(this.metadataVersion);
    localStringBuilder.append(", sourceElement=");
    localStringBuilder.append(this.sourceElement);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}

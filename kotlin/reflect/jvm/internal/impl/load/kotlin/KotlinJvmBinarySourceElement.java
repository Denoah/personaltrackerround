package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceFile;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMetadataVersion;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.IncompatibleVersionErrorData;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedContainerSource;

public final class KotlinJvmBinarySourceElement
  implements DeserializedContainerSource
{
  private final KotlinJvmBinaryClass binaryClass;
  private final IncompatibleVersionErrorData<JvmMetadataVersion> incompatibility;
  private final boolean isPreReleaseInvisible;
  
  public KotlinJvmBinarySourceElement(KotlinJvmBinaryClass paramKotlinJvmBinaryClass, IncompatibleVersionErrorData<JvmMetadataVersion> paramIncompatibleVersionErrorData, boolean paramBoolean)
  {
    this.binaryClass = paramKotlinJvmBinaryClass;
    this.incompatibility = paramIncompatibleVersionErrorData;
    this.isPreReleaseInvisible = paramBoolean;
  }
  
  public final KotlinJvmBinaryClass getBinaryClass()
  {
    return this.binaryClass;
  }
  
  public SourceFile getContainingFile()
  {
    SourceFile localSourceFile = SourceFile.NO_SOURCE_FILE;
    Intrinsics.checkExpressionValueIsNotNull(localSourceFile, "SourceFile.NO_SOURCE_FILE");
    return localSourceFile;
  }
  
  public String getPresentableString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Class '");
    localStringBuilder.append(this.binaryClass.getClassId().asSingleFqName().asString());
    localStringBuilder.append('\'');
    return localStringBuilder.toString();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getSimpleName());
    localStringBuilder.append(": ");
    localStringBuilder.append(this.binaryClass);
    return localStringBuilder.toString();
  }
}

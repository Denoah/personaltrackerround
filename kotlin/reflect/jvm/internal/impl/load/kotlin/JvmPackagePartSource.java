package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceFile;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Package;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoBufUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMetadataVersion;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.ExtendableMessage;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmClassName;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.IncompatibleVersionErrorData;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedContainerSource;
import kotlin.text.StringsKt;

public final class JvmPackagePartSource
  implements DeserializedContainerSource
{
  private final JvmClassName className;
  private final JvmClassName facadeClassName;
  private final IncompatibleVersionErrorData<JvmMetadataVersion> incompatibility;
  private final boolean isPreReleaseInvisible;
  private final KotlinJvmBinaryClass knownJvmBinaryClass;
  private final String moduleName;
  
  public JvmPackagePartSource(KotlinJvmBinaryClass paramKotlinJvmBinaryClass, ProtoBuf.Package paramPackage, NameResolver paramNameResolver, IncompatibleVersionErrorData<JvmMetadataVersion> paramIncompatibleVersionErrorData, boolean paramBoolean)
  {
    this(localJvmClassName, (JvmClassName)localObject2, paramPackage, paramNameResolver, paramIncompatibleVersionErrorData, paramBoolean, paramKotlinJvmBinaryClass);
  }
  
  public JvmPackagePartSource(JvmClassName paramJvmClassName1, JvmClassName paramJvmClassName2, ProtoBuf.Package paramPackage, NameResolver paramNameResolver, IncompatibleVersionErrorData<JvmMetadataVersion> paramIncompatibleVersionErrorData, boolean paramBoolean, KotlinJvmBinaryClass paramKotlinJvmBinaryClass)
  {
    this.className = paramJvmClassName1;
    this.facadeClassName = paramJvmClassName2;
    this.incompatibility = paramIncompatibleVersionErrorData;
    this.isPreReleaseInvisible = paramBoolean;
    this.knownJvmBinaryClass = paramKotlinJvmBinaryClass;
    paramJvmClassName2 = (GeneratedMessageLite.ExtendableMessage)paramPackage;
    paramJvmClassName1 = JvmProtoBuf.packageModuleName;
    Intrinsics.checkExpressionValueIsNotNull(paramJvmClassName1, "JvmProtoBuf.packageModuleName");
    paramJvmClassName1 = (Integer)ProtoBufUtilKt.getExtensionOrNull(paramJvmClassName2, paramJvmClassName1);
    if (paramJvmClassName1 != null)
    {
      paramJvmClassName1 = paramNameResolver.getString(((Number)paramJvmClassName1).intValue());
      if (paramJvmClassName1 != null) {}
    }
    else
    {
      paramJvmClassName1 = "main";
    }
    this.moduleName = paramJvmClassName1;
  }
  
  public final ClassId getClassId()
  {
    return new ClassId(this.className.getPackageFqName(), getSimpleName());
  }
  
  public SourceFile getContainingFile()
  {
    SourceFile localSourceFile = SourceFile.NO_SOURCE_FILE;
    Intrinsics.checkExpressionValueIsNotNull(localSourceFile, "SourceFile.NO_SOURCE_FILE");
    return localSourceFile;
  }
  
  public final JvmClassName getFacadeClassName()
  {
    return this.facadeClassName;
  }
  
  public final KotlinJvmBinaryClass getKnownJvmBinaryClass()
  {
    return this.knownJvmBinaryClass;
  }
  
  public String getPresentableString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Class '");
    localStringBuilder.append(getClassId().asSingleFqName().asString());
    localStringBuilder.append('\'');
    return localStringBuilder.toString();
  }
  
  public final Name getSimpleName()
  {
    Object localObject = this.className.getInternalName();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "className.internalName");
    localObject = Name.identifier(StringsKt.substringAfterLast$default((String)localObject, '/', null, 2, null));
    Intrinsics.checkExpressionValueIsNotNull(localObject, "Name.identifier(classNam….substringAfterLast('/'))");
    return localObject;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getSimpleName());
    localStringBuilder.append(": ");
    localStringBuilder.append(this.className);
    return localStringBuilder.toString();
  }
}

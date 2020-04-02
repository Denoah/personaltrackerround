package kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.GeneratedExtension;
import kotlin.reflect.jvm.internal.impl.serialization.SerializerExtensionProtocol;
import kotlin.text.StringsKt;

public final class BuiltInSerializerProtocol
  extends SerializerExtensionProtocol
{
  public static final BuiltInSerializerProtocol INSTANCE = new BuiltInSerializerProtocol();
  
  private BuiltInSerializerProtocol()
  {
    super(localExtensionRegistryLite, localGeneratedExtension1, localGeneratedExtension2, localGeneratedExtension3, localGeneratedExtension4, localGeneratedExtension5, localGeneratedExtension6, localGeneratedExtension7, localGeneratedExtension8, localGeneratedExtension9, localGeneratedExtension10, localGeneratedExtension11, localGeneratedExtension12);
  }
  
  private final String shortName(FqName paramFqName)
  {
    if (paramFqName.isRoot())
    {
      paramFqName = "default-package";
    }
    else
    {
      paramFqName = paramFqName.shortName().asString();
      Intrinsics.checkExpressionValueIsNotNull(paramFqName, "fqName.shortName().asString()");
    }
    return paramFqName;
  }
  
  public final String getBuiltInsFileName(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(shortName(paramFqName));
    localStringBuilder.append(".");
    localStringBuilder.append("kotlin_builtins");
    return localStringBuilder.toString();
  }
  
  public final String getBuiltInsFilePath(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    StringBuilder localStringBuilder = new StringBuilder();
    String str = paramFqName.asString();
    Intrinsics.checkExpressionValueIsNotNull(str, "fqName.asString()");
    localStringBuilder.append(StringsKt.replace$default(str, '.', '/', false, 4, null));
    localStringBuilder.append("/");
    localStringBuilder.append(getBuiltInsFileName(paramFqName));
    return localStringBuilder.toString();
  }
}

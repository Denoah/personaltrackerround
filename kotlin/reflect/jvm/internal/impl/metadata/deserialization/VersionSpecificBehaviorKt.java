package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import kotlin.jvm.internal.Intrinsics;

public final class VersionSpecificBehaviorKt
{
  public static final boolean isKotlin1Dot4OrLater(BinaryVersion paramBinaryVersion)
  {
    Intrinsics.checkParameterIsNotNull(paramBinaryVersion, "version");
    int i = paramBinaryVersion.getMajor();
    boolean bool = true;
    if ((i != 1) || (paramBinaryVersion.getMinor() < 4)) {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean isVersionRequirementTableWrittenCorrectly(BinaryVersion paramBinaryVersion)
  {
    Intrinsics.checkParameterIsNotNull(paramBinaryVersion, "version");
    return isKotlin1Dot4OrLater(paramBinaryVersion);
  }
}

package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import java.util.Arrays;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;

public final class JvmMetadataVersion
  extends BinaryVersion
{
  public static final Companion Companion = new Companion(null);
  public static final JvmMetadataVersion INSTANCE = new JvmMetadataVersion(new int[] { 1, 1, 16 });
  public static final JvmMetadataVersion INVALID_VERSION = new JvmMetadataVersion(new int[0]);
  private final boolean isStrictSemantics;
  
  public JvmMetadataVersion(int... paramVarArgs)
  {
    this(paramVarArgs, false);
  }
  
  public JvmMetadataVersion(int[] paramArrayOfInt, boolean paramBoolean)
  {
    super(Arrays.copyOf(paramArrayOfInt, paramArrayOfInt.length));
    this.isStrictSemantics = paramBoolean;
  }
  
  public boolean isCompatible()
  {
    int i = getMajor();
    boolean bool1 = false;
    boolean bool2;
    if (i == 1)
    {
      bool2 = bool1;
      if (getMinor() == 0) {}
    }
    else
    {
      boolean bool3;
      if (this.isStrictSemantics) {
        bool3 = isCompatibleTo((BinaryVersion)INSTANCE);
      } else if ((getMajor() == 1) && (getMinor() <= 4)) {
        bool3 = true;
      } else {
        bool3 = false;
      }
      bool2 = bool1;
      if (bool3) {
        bool2 = true;
      }
    }
    return bool2;
  }
  
  public static final class Companion
  {
    private Companion() {}
  }
}

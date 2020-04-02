package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import java.util.Arrays;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;

public final class JvmBytecodeBinaryVersion
  extends BinaryVersion
{
  public static final Companion Companion = new Companion(null);
  public static final JvmBytecodeBinaryVersion INSTANCE = new JvmBytecodeBinaryVersion(new int[] { 1, 0, 3 });
  public static final JvmBytecodeBinaryVersion INVALID_VERSION = new JvmBytecodeBinaryVersion(new int[0]);
  
  public JvmBytecodeBinaryVersion(int... paramVarArgs)
  {
    super(Arrays.copyOf(paramVarArgs, paramVarArgs.length));
  }
  
  public static final class Companion
  {
    private Companion() {}
  }
}

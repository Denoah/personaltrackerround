package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

public abstract class BinaryVersion
{
  public static final Companion Companion = new Companion(null);
  private final int major;
  private final int minor;
  private final int[] numbers;
  private final int patch;
  private final List<Integer> rest;
  
  public BinaryVersion(int... paramVarArgs)
  {
    this.numbers = paramVarArgs;
    paramVarArgs = ArraysKt.getOrNull(paramVarArgs, 0);
    int i = -1;
    if (paramVarArgs != null) {
      j = paramVarArgs.intValue();
    } else {
      j = -1;
    }
    this.major = j;
    paramVarArgs = ArraysKt.getOrNull(this.numbers, 1);
    if (paramVarArgs != null) {
      j = paramVarArgs.intValue();
    } else {
      j = -1;
    }
    this.minor = j;
    paramVarArgs = ArraysKt.getOrNull(this.numbers, 2);
    int j = i;
    if (paramVarArgs != null) {
      j = paramVarArgs.intValue();
    }
    this.patch = j;
    paramVarArgs = this.numbers;
    if (paramVarArgs.length > 3) {
      paramVarArgs = CollectionsKt.toList((Iterable)ArraysKt.asList(paramVarArgs).subList(3, this.numbers.length));
    } else {
      paramVarArgs = CollectionsKt.emptyList();
    }
    this.rest = paramVarArgs;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject != null) && (Intrinsics.areEqual(getClass(), paramObject.getClass())))
    {
      int i = this.major;
      paramObject = (BinaryVersion)paramObject;
      if ((i == paramObject.major) && (this.minor == paramObject.minor) && (this.patch == paramObject.patch) && (Intrinsics.areEqual(this.rest, paramObject.rest))) {
        return true;
      }
    }
    boolean bool = false;
    return bool;
  }
  
  public final int getMajor()
  {
    return this.major;
  }
  
  public final int getMinor()
  {
    return this.minor;
  }
  
  public int hashCode()
  {
    int i = this.major;
    i += i * 31 + this.minor;
    i += i * 31 + this.patch;
    return i + (i * 31 + this.rest.hashCode());
  }
  
  public final boolean isAtLeast(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = this.major;
    boolean bool = true;
    if (i > paramInt1) {
      return true;
    }
    if (i < paramInt1) {
      return false;
    }
    paramInt1 = this.minor;
    if (paramInt1 > paramInt2) {
      return true;
    }
    if (paramInt1 < paramInt2) {
      return false;
    }
    if (this.patch < paramInt3) {
      bool = false;
    }
    return bool;
  }
  
  public final boolean isAtLeast(BinaryVersion paramBinaryVersion)
  {
    Intrinsics.checkParameterIsNotNull(paramBinaryVersion, "version");
    return isAtLeast(paramBinaryVersion.major, paramBinaryVersion.minor, paramBinaryVersion.patch);
  }
  
  protected final boolean isCompatibleTo(BinaryVersion paramBinaryVersion)
  {
    Intrinsics.checkParameterIsNotNull(paramBinaryVersion, "ourVersion");
    int i = this.major;
    boolean bool = true;
    if (i == 0 ? (paramBinaryVersion.major == 0) && (this.minor == paramBinaryVersion.minor) : (i != paramBinaryVersion.major) || (this.minor > paramBinaryVersion.minor)) {
      bool = false;
    }
    return bool;
  }
  
  public final int[] toArray()
  {
    return this.numbers;
  }
  
  public String toString()
  {
    int[] arrayOfInt = toArray();
    Object localObject = new ArrayList();
    int i = arrayOfInt.length;
    for (int j = 0; j < i; j++)
    {
      int k = arrayOfInt[j];
      int m;
      if (k != -1) {
        m = 1;
      } else {
        m = 0;
      }
      if (m == 0) {
        break;
      }
      ((ArrayList)localObject).add(Integer.valueOf(k));
    }
    localObject = (List)localObject;
    if (((List)localObject).isEmpty()) {
      localObject = "unknown";
    } else {
      localObject = CollectionsKt.joinToString$default((Iterable)localObject, (CharSequence)".", null, null, 0, null, null, 62, null);
    }
    return localObject;
  }
  
  public static final class Companion
  {
    private Companion() {}
  }
}

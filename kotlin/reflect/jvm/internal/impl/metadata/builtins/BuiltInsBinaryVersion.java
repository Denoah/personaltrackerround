package kotlin.reflect.jvm.internal.impl.metadata.builtins;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;

public final class BuiltInsBinaryVersion
  extends BinaryVersion
{
  public static final Companion Companion = new Companion(null);
  public static final BuiltInsBinaryVersion INSTANCE = new BuiltInsBinaryVersion(new int[] { 1, 0, 7 });
  public static final BuiltInsBinaryVersion INVALID_VERSION = new BuiltInsBinaryVersion(new int[0]);
  
  public BuiltInsBinaryVersion(int... paramVarArgs)
  {
    super(Arrays.copyOf(paramVarArgs, paramVarArgs.length));
  }
  
  public boolean isCompatible()
  {
    return isCompatibleTo((BinaryVersion)INSTANCE);
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    public final BuiltInsBinaryVersion readFrom(InputStream paramInputStream)
    {
      Intrinsics.checkParameterIsNotNull(paramInputStream, "stream");
      DataInputStream localDataInputStream = new DataInputStream(paramInputStream);
      Object localObject = (Iterable)new IntRange(1, localDataInputStream.readInt());
      paramInputStream = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject, 10));
      localObject = ((Iterable)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        ((IntIterator)localObject).nextInt();
        paramInputStream.add(Integer.valueOf(localDataInputStream.readInt()));
      }
      paramInputStream = CollectionsKt.toIntArray((Collection)paramInputStream);
      return new BuiltInsBinaryVersion(Arrays.copyOf(paramInputStream, paramInputStream.length));
    }
  }
}

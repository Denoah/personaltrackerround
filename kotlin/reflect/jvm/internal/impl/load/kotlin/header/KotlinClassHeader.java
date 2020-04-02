package kotlin.reflect.jvm.internal.impl.load.kotlin.header;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmBytecodeBinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMetadataVersion;

public final class KotlinClassHeader
{
  private final JvmBytecodeBinaryVersion bytecodeVersion;
  private final String[] data;
  private final int extraInt;
  private final String extraString;
  private final String[] incompatibleData;
  private final Kind kind;
  private final JvmMetadataVersion metadataVersion;
  private final String packageName;
  private final String[] strings;
  
  public KotlinClassHeader(Kind paramKind, JvmMetadataVersion paramJvmMetadataVersion, JvmBytecodeBinaryVersion paramJvmBytecodeBinaryVersion, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String paramString1, int paramInt, String paramString2)
  {
    this.kind = paramKind;
    this.metadataVersion = paramJvmMetadataVersion;
    this.bytecodeVersion = paramJvmBytecodeBinaryVersion;
    this.data = paramArrayOfString1;
    this.incompatibleData = paramArrayOfString2;
    this.strings = paramArrayOfString3;
    this.extraString = paramString1;
    this.extraInt = paramInt;
    this.packageName = paramString2;
  }
  
  public final String[] getData()
  {
    return this.data;
  }
  
  public final String[] getIncompatibleData()
  {
    return this.incompatibleData;
  }
  
  public final Kind getKind()
  {
    return this.kind;
  }
  
  public final JvmMetadataVersion getMetadataVersion()
  {
    return this.metadataVersion;
  }
  
  public final String getMultifileClassName()
  {
    String str = this.extraString;
    int i;
    if (this.kind == Kind.MULTIFILE_CLASS_PART) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0) {
      str = null;
    }
    return str;
  }
  
  public final List<String> getMultifilePartNames()
  {
    String[] arrayOfString = this.data;
    int i;
    if (this.kind == Kind.MULTIFILE_CLASS) {
      i = 1;
    } else {
      i = 0;
    }
    List localList = null;
    if (i == 0) {
      arrayOfString = null;
    }
    if (arrayOfString != null) {
      localList = ArraysKt.asList(arrayOfString);
    }
    if (localList == null) {
      localList = CollectionsKt.emptyList();
    }
    return localList;
  }
  
  public final String[] getStrings()
  {
    return this.strings;
  }
  
  public final boolean isPreRelease()
  {
    boolean bool;
    if ((this.extraInt & 0x2) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.kind);
    localStringBuilder.append(" version=");
    localStringBuilder.append(this.metadataVersion);
    return localStringBuilder.toString();
  }
  
  public static enum Kind
  {
    public static final Companion Companion;
    private static final Map<Integer, Kind> entryById;
    private final int id;
    
    static
    {
      int i = 0;
      Object localObject1 = new Kind("UNKNOWN", 0, 0);
      UNKNOWN = (Kind)localObject1;
      Kind localKind1 = new Kind("CLASS", 1, 1);
      CLASS = localKind1;
      Kind localKind2 = new Kind("FILE_FACADE", 2, 2);
      FILE_FACADE = localKind2;
      Kind localKind3 = new Kind("SYNTHETIC_CLASS", 3, 3);
      SYNTHETIC_CLASS = localKind3;
      Kind localKind4 = new Kind("MULTIFILE_CLASS", 4, 4);
      MULTIFILE_CLASS = localKind4;
      Object localObject2 = new Kind("MULTIFILE_CLASS_PART", 5, 5);
      MULTIFILE_CLASS_PART = (Kind)localObject2;
      $VALUES = new Kind[] { localObject1, localKind1, localKind2, localKind3, localKind4, localObject2 };
      Companion = new Companion(null);
      localObject2 = values();
      localObject1 = (Map)new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(localObject2.length), 16));
      int j = localObject2.length;
      while (i < j)
      {
        localKind4 = localObject2[i];
        ((Map)localObject1).put(Integer.valueOf(localKind4.id), localKind4);
        i++;
      }
      entryById = (Map)localObject1;
    }
    
    private Kind(int paramInt)
    {
      this.id = paramInt;
    }
    
    @JvmStatic
    public static final Kind getById(int paramInt)
    {
      return Companion.getById(paramInt);
    }
    
    public static final class Companion
    {
      private Companion() {}
      
      @JvmStatic
      public final KotlinClassHeader.Kind getById(int paramInt)
      {
        KotlinClassHeader.Kind localKind = (KotlinClassHeader.Kind)KotlinClassHeader.Kind.access$getEntryById$cp().get(Integer.valueOf(paramInt));
        if (localKind == null) {
          localKind = KotlinClassHeader.Kind.UNKNOWN;
        }
        return localKind;
      }
    }
  }
}

package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.DeprecationLevel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Class;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Constructor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Function;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Property;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeAlias;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.VersionRequirement;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.VersionRequirement.VersionKind;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;

public final class VersionRequirement
{
  public static final Companion Companion = new Companion(null);
  private final Integer errorCode;
  private final ProtoBuf.VersionRequirement.VersionKind kind;
  private final DeprecationLevel level;
  private final String message;
  private final Version version;
  
  public VersionRequirement(Version paramVersion, ProtoBuf.VersionRequirement.VersionKind paramVersionKind, DeprecationLevel paramDeprecationLevel, Integer paramInteger, String paramString)
  {
    this.version = paramVersion;
    this.kind = paramVersionKind;
    this.level = paramDeprecationLevel;
    this.errorCode = paramInteger;
    this.message = paramString;
  }
  
  public final ProtoBuf.VersionRequirement.VersionKind getKind()
  {
    return this.kind;
  }
  
  public final Version getVersion()
  {
    return this.version;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("since ");
    localStringBuilder.append(this.version);
    localStringBuilder.append(' ');
    localStringBuilder.append(this.level);
    Object localObject = this.errorCode;
    String str = "";
    if (localObject != null)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(" error ");
      ((StringBuilder)localObject).append(this.errorCode);
      localObject = ((StringBuilder)localObject).toString();
    }
    else
    {
      localObject = "";
    }
    localStringBuilder.append((String)localObject);
    localObject = str;
    if (this.message != null)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(": ");
      ((StringBuilder)localObject).append(this.message);
      localObject = ((StringBuilder)localObject).toString();
    }
    localStringBuilder.append((String)localObject);
    return localStringBuilder.toString();
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    public final List<VersionRequirement> create(MessageLite paramMessageLite, NameResolver paramNameResolver, VersionRequirementTable paramVersionRequirementTable)
    {
      Intrinsics.checkParameterIsNotNull(paramMessageLite, "proto");
      Intrinsics.checkParameterIsNotNull(paramNameResolver, "nameResolver");
      Intrinsics.checkParameterIsNotNull(paramVersionRequirementTable, "table");
      if ((paramMessageLite instanceof ProtoBuf.Class))
      {
        paramMessageLite = ((ProtoBuf.Class)paramMessageLite).getVersionRequirementList();
      }
      else if ((paramMessageLite instanceof ProtoBuf.Constructor))
      {
        paramMessageLite = ((ProtoBuf.Constructor)paramMessageLite).getVersionRequirementList();
      }
      else if ((paramMessageLite instanceof ProtoBuf.Function))
      {
        paramMessageLite = ((ProtoBuf.Function)paramMessageLite).getVersionRequirementList();
      }
      else if ((paramMessageLite instanceof ProtoBuf.Property))
      {
        paramMessageLite = ((ProtoBuf.Property)paramMessageLite).getVersionRequirementList();
      }
      else
      {
        if (!(paramMessageLite instanceof ProtoBuf.TypeAlias)) {
          break label207;
        }
        paramMessageLite = ((ProtoBuf.TypeAlias)paramMessageLite).getVersionRequirementList();
      }
      Intrinsics.checkExpressionValueIsNotNull(paramMessageLite, "ids");
      Object localObject1 = (Iterable)paramMessageLite;
      paramMessageLite = (Collection)new ArrayList();
      localObject1 = ((Iterable)localObject1).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        Object localObject2 = (Integer)((Iterator)localObject1).next();
        Companion localCompanion = VersionRequirement.Companion;
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "id");
        localObject2 = localCompanion.create(((Integer)localObject2).intValue(), paramNameResolver, paramVersionRequirementTable);
        if (localObject2 != null) {
          paramMessageLite.add(localObject2);
        }
      }
      return (List)paramMessageLite;
      label207:
      paramNameResolver = new StringBuilder();
      paramNameResolver.append("Unexpected declaration: ");
      paramNameResolver.append(paramMessageLite.getClass());
      throw ((Throwable)new IllegalStateException(paramNameResolver.toString()));
    }
    
    public final VersionRequirement create(int paramInt, NameResolver paramNameResolver, VersionRequirementTable paramVersionRequirementTable)
    {
      Intrinsics.checkParameterIsNotNull(paramNameResolver, "nameResolver");
      Intrinsics.checkParameterIsNotNull(paramVersionRequirementTable, "table");
      ProtoBuf.VersionRequirement localVersionRequirement = paramVersionRequirementTable.get(paramInt);
      String str = null;
      if (localVersionRequirement != null)
      {
        Object localObject = VersionRequirement.Version.Companion;
        if (localVersionRequirement.hasVersion()) {
          paramVersionRequirementTable = Integer.valueOf(localVersionRequirement.getVersion());
        } else {
          paramVersionRequirementTable = null;
        }
        Integer localInteger;
        if (localVersionRequirement.hasVersionFull()) {
          localInteger = Integer.valueOf(localVersionRequirement.getVersionFull());
        } else {
          localInteger = null;
        }
        localObject = ((VersionRequirement.Version.Companion)localObject).decode(paramVersionRequirementTable, localInteger);
        paramVersionRequirementTable = localVersionRequirement.getLevel();
        if (paramVersionRequirementTable == null) {
          Intrinsics.throwNpe();
        }
        paramInt = VersionRequirement.Companion.WhenMappings.$EnumSwitchMapping$0[paramVersionRequirementTable.ordinal()];
        if (paramInt != 1)
        {
          if (paramInt != 2)
          {
            if (paramInt == 3) {
              paramVersionRequirementTable = DeprecationLevel.HIDDEN;
            } else {
              throw new NoWhenBranchMatchedException();
            }
          }
          else {
            paramVersionRequirementTable = DeprecationLevel.ERROR;
          }
        }
        else {
          paramVersionRequirementTable = DeprecationLevel.WARNING;
        }
        if (localVersionRequirement.hasErrorCode()) {
          localInteger = Integer.valueOf(localVersionRequirement.getErrorCode());
        } else {
          localInteger = null;
        }
        if (localVersionRequirement.hasMessage()) {
          str = paramNameResolver.getString(localVersionRequirement.getMessage());
        }
        paramNameResolver = localVersionRequirement.getVersionKind();
        Intrinsics.checkExpressionValueIsNotNull(paramNameResolver, "info.versionKind");
        return new VersionRequirement((VersionRequirement.Version)localObject, paramNameResolver, paramVersionRequirementTable, localInteger, str);
      }
      return null;
    }
  }
  
  public static final class Version
  {
    public static final Companion Companion = new Companion(null);
    public static final Version INFINITY = new Version(256, 256, 256);
    private final int major;
    private final int minor;
    private final int patch;
    
    public Version(int paramInt1, int paramInt2, int paramInt3)
    {
      this.major = paramInt1;
      this.minor = paramInt2;
      this.patch = paramInt3;
    }
    
    public final String asString()
    {
      StringBuilder localStringBuilder;
      int i;
      if (this.patch == 0)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(this.major);
        localStringBuilder.append('.');
        i = this.minor;
      }
      else
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(this.major);
        localStringBuilder.append('.');
        localStringBuilder.append(this.minor);
        localStringBuilder.append('.');
        i = this.patch;
      }
      localStringBuilder.append(i);
      return localStringBuilder.toString();
    }
    
    public boolean equals(Object paramObject)
    {
      if (this != paramObject)
      {
        if ((paramObject instanceof Version))
        {
          paramObject = (Version)paramObject;
          int i;
          if (this.major == paramObject.major) {
            i = 1;
          } else {
            i = 0;
          }
          if (i != 0)
          {
            if (this.minor == paramObject.minor) {
              i = 1;
            } else {
              i = 0;
            }
            if (i != 0)
            {
              if (this.patch == paramObject.patch) {
                i = 1;
              } else {
                i = 0;
              }
              if (i != 0) {
                break label88;
              }
            }
          }
        }
        return false;
      }
      label88:
      return true;
    }
    
    public int hashCode()
    {
      return (this.major * 31 + this.minor) * 31 + this.patch;
    }
    
    public String toString()
    {
      return asString();
    }
    
    public static final class Companion
    {
      private Companion() {}
      
      public final VersionRequirement.Version decode(Integer paramInteger1, Integer paramInteger2)
      {
        if (paramInteger2 != null) {
          paramInteger1 = new VersionRequirement.Version(paramInteger2.intValue() & 0xFF, paramInteger2.intValue() >> 8 & 0xFF, paramInteger2.intValue() >> 16 & 0xFF);
        } else if (paramInteger1 != null) {
          paramInteger1 = new VersionRequirement.Version(paramInteger1.intValue() & 0x7, paramInteger1.intValue() >> 3 & 0xF, paramInteger1.intValue() >> 7 & 0x7F);
        } else {
          paramInteger1 = VersionRequirement.Version.INFINITY;
        }
        return paramInteger1;
      }
    }
  }
}

package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Class;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Class.Kind;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags.BooleanFlagField;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags.FlagField;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;

public abstract class ProtoContainer
{
  private final NameResolver nameResolver;
  private final SourceElement source;
  private final TypeTable typeTable;
  
  private ProtoContainer(NameResolver paramNameResolver, TypeTable paramTypeTable, SourceElement paramSourceElement)
  {
    this.nameResolver = paramNameResolver;
    this.typeTable = paramTypeTable;
    this.source = paramSourceElement;
  }
  
  public abstract FqName debugFqName();
  
  public final NameResolver getNameResolver()
  {
    return this.nameResolver;
  }
  
  public final SourceElement getSource()
  {
    return this.source;
  }
  
  public final TypeTable getTypeTable()
  {
    return this.typeTable;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getSimpleName());
    localStringBuilder.append(": ");
    localStringBuilder.append(debugFqName());
    return localStringBuilder.toString();
  }
  
  public static final class Class
    extends ProtoContainer
  {
    private final ClassId classId;
    private final ProtoBuf.Class classProto;
    private final boolean isInner;
    private final ProtoBuf.Class.Kind kind;
    private final Class outerClass;
    
    public Class(ProtoBuf.Class paramClass, NameResolver paramNameResolver, TypeTable paramTypeTable, SourceElement paramSourceElement, Class paramClass1)
    {
      super(paramTypeTable, paramSourceElement, null);
      this.classProto = paramClass;
      this.outerClass = paramClass1;
      this.classId = NameResolverUtilKt.getClassId(paramNameResolver, paramClass.getFqName());
      paramClass = (ProtoBuf.Class.Kind)Flags.CLASS_KIND.get(this.classProto.getFlags());
      if (paramClass == null) {
        paramClass = ProtoBuf.Class.Kind.CLASS;
      }
      this.kind = paramClass;
      paramClass = Flags.IS_INNER.get(this.classProto.getFlags());
      Intrinsics.checkExpressionValueIsNotNull(paramClass, "Flags.IS_INNER.get(classProto.flags)");
      this.isInner = paramClass.booleanValue();
    }
    
    public FqName debugFqName()
    {
      FqName localFqName = this.classId.asSingleFqName();
      Intrinsics.checkExpressionValueIsNotNull(localFqName, "classId.asSingleFqName()");
      return localFqName;
    }
    
    public final ClassId getClassId()
    {
      return this.classId;
    }
    
    public final ProtoBuf.Class getClassProto()
    {
      return this.classProto;
    }
    
    public final ProtoBuf.Class.Kind getKind()
    {
      return this.kind;
    }
    
    public final Class getOuterClass()
    {
      return this.outerClass;
    }
    
    public final boolean isInner()
    {
      return this.isInner;
    }
  }
  
  public static final class Package
    extends ProtoContainer
  {
    private final FqName fqName;
    
    public Package(FqName paramFqName, NameResolver paramNameResolver, TypeTable paramTypeTable, SourceElement paramSourceElement)
    {
      super(paramTypeTable, paramSourceElement, null);
      this.fqName = paramFqName;
    }
    
    public FqName debugFqName()
    {
      return this.fqName;
    }
  }
}

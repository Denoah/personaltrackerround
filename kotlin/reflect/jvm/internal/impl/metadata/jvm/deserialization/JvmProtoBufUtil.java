package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Class;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Constructor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Function;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Package;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Property;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.ValueParameter;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags.BooleanFlagField;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoBufUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoTypeTableUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf.JvmFieldSignature;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf.JvmMethodSignature;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf.JvmPropertySignature;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf.StringTableTypes;
import kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.ExtendableMessage;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.GeneratedExtension;

public final class JvmProtoBufUtil
{
  private static final ExtensionRegistryLite EXTENSION_REGISTRY;
  public static final JvmProtoBufUtil INSTANCE = new JvmProtoBufUtil();
  
  static
  {
    ExtensionRegistryLite localExtensionRegistryLite = ExtensionRegistryLite.newInstance();
    JvmProtoBuf.registerAllExtensions(localExtensionRegistryLite);
    Intrinsics.checkExpressionValueIsNotNull(localExtensionRegistryLite, "ExtensionRegistryLite.ne�f::registerAllExtensions)");
    EXTENSION_REGISTRY = localExtensionRegistryLite;
  }
  
  private JvmProtoBufUtil() {}
  
  @JvmStatic
  public static final boolean isMovedFromInterfaceCompanion(ProtoBuf.Property paramProperty)
  {
    Intrinsics.checkParameterIsNotNull(paramProperty, "proto");
    Flags.BooleanFlagField localBooleanFlagField = JvmFlags.INSTANCE.getIS_MOVED_FROM_INTERFACE_COMPANION();
    paramProperty = paramProperty.getExtension(JvmProtoBuf.flags);
    Intrinsics.checkExpressionValueIsNotNull(paramProperty, "proto.getExtension(JvmProtoBuf.flags)");
    paramProperty = localBooleanFlagField.get(((Number)paramProperty).intValue());
    Intrinsics.checkExpressionValueIsNotNull(paramProperty, "JvmFlags.IS_MOVED_FROM_I�nsion(JvmProtoBuf.flags))");
    return paramProperty.booleanValue();
  }
  
  private final String mapTypeDefault(ProtoBuf.Type paramType, NameResolver paramNameResolver)
  {
    if (paramType.hasClassName()) {
      paramType = ClassMapperLite.mapClass(paramNameResolver.getQualifiedClassName(paramType.getClassName()));
    } else {
      paramType = null;
    }
    return paramType;
  }
  
  @JvmStatic
  public static final Pair<JvmNameResolver, ProtoBuf.Class> readClassDataFrom(byte[] paramArrayOfByte, String[] paramArrayOfString)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "bytes");
    Intrinsics.checkParameterIsNotNull(paramArrayOfString, "strings");
    Object localObject = new ByteArrayInputStream(paramArrayOfByte);
    paramArrayOfByte = INSTANCE;
    localObject = (InputStream)localObject;
    return new Pair(paramArrayOfByte.readNameResolver((InputStream)localObject, paramArrayOfString), ProtoBuf.Class.parseFrom((InputStream)localObject, EXTENSION_REGISTRY));
  }
  
  @JvmStatic
  public static final Pair<JvmNameResolver, ProtoBuf.Class> readClassDataFrom(String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfString1, "data");
    Intrinsics.checkParameterIsNotNull(paramArrayOfString2, "strings");
    paramArrayOfString1 = BitEncoding.decodeBytes(paramArrayOfString1);
    Intrinsics.checkExpressionValueIsNotNull(paramArrayOfString1, "BitEncoding.decodeBytes(data)");
    return readClassDataFrom(paramArrayOfString1, paramArrayOfString2);
  }
  
  @JvmStatic
  public static final Pair<JvmNameResolver, ProtoBuf.Function> readFunctionDataFrom(String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfString1, "data");
    Intrinsics.checkParameterIsNotNull(paramArrayOfString2, "strings");
    Object localObject = new ByteArrayInputStream(BitEncoding.decodeBytes(paramArrayOfString1));
    paramArrayOfString1 = INSTANCE;
    localObject = (InputStream)localObject;
    return new Pair(paramArrayOfString1.readNameResolver((InputStream)localObject, paramArrayOfString2), ProtoBuf.Function.parseFrom((InputStream)localObject, EXTENSION_REGISTRY));
  }
  
  private final JvmNameResolver readNameResolver(InputStream paramInputStream, String[] paramArrayOfString)
  {
    paramInputStream = JvmProtoBuf.StringTableTypes.parseDelimitedFrom(paramInputStream, EXTENSION_REGISTRY);
    Intrinsics.checkExpressionValueIsNotNull(paramInputStream, "JvmProtoBuf.StringTableT�this, EXTENSION_REGISTRY)");
    return new JvmNameResolver(paramInputStream, paramArrayOfString);
  }
  
  @JvmStatic
  public static final Pair<JvmNameResolver, ProtoBuf.Package> readPackageDataFrom(byte[] paramArrayOfByte, String[] paramArrayOfString)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "bytes");
    Intrinsics.checkParameterIsNotNull(paramArrayOfString, "strings");
    Object localObject = new ByteArrayInputStream(paramArrayOfByte);
    paramArrayOfByte = INSTANCE;
    localObject = (InputStream)localObject;
    return new Pair(paramArrayOfByte.readNameResolver((InputStream)localObject, paramArrayOfString), ProtoBuf.Package.parseFrom((InputStream)localObject, EXTENSION_REGISTRY));
  }
  
  @JvmStatic
  public static final Pair<JvmNameResolver, ProtoBuf.Package> readPackageDataFrom(String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfString1, "data");
    Intrinsics.checkParameterIsNotNull(paramArrayOfString2, "strings");
    paramArrayOfString1 = BitEncoding.decodeBytes(paramArrayOfString1);
    Intrinsics.checkExpressionValueIsNotNull(paramArrayOfString1, "BitEncoding.decodeBytes(data)");
    return readPackageDataFrom(paramArrayOfString1, paramArrayOfString2);
  }
  
  public final ExtensionRegistryLite getEXTENSION_REGISTRY()
  {
    return EXTENSION_REGISTRY;
  }
  
  public final JvmMemberSignature.Method getJvmConstructorSignature(ProtoBuf.Constructor paramConstructor, NameResolver paramNameResolver, TypeTable paramTypeTable)
  {
    Intrinsics.checkParameterIsNotNull(paramConstructor, "proto");
    Intrinsics.checkParameterIsNotNull(paramNameResolver, "nameResolver");
    Intrinsics.checkParameterIsNotNull(paramTypeTable, "typeTable");
    Object localObject1 = (GeneratedMessageLite.ExtendableMessage)paramConstructor;
    Object localObject2 = JvmProtoBuf.constructorSignature;
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "JvmProtoBuf.constructorSignature");
    localObject2 = (JvmProtoBuf.JvmMethodSignature)ProtoBufUtilKt.getExtensionOrNull((GeneratedMessageLite.ExtendableMessage)localObject1, (GeneratedMessageLite.GeneratedExtension)localObject2);
    if ((localObject2 != null) && (((JvmProtoBuf.JvmMethodSignature)localObject2).hasName())) {
      localObject1 = paramNameResolver.getString(((JvmProtoBuf.JvmMethodSignature)localObject2).getName());
    } else {
      localObject1 = "<init>";
    }
    if ((localObject2 != null) && (((JvmProtoBuf.JvmMethodSignature)localObject2).hasDesc()))
    {
      paramConstructor = paramNameResolver.getString(((JvmProtoBuf.JvmMethodSignature)localObject2).getDesc());
    }
    else
    {
      paramConstructor = paramConstructor.getValueParameterList();
      Intrinsics.checkExpressionValueIsNotNull(paramConstructor, "proto.valueParameterList");
      localObject2 = (Iterable)paramConstructor;
      paramConstructor = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
      localObject2 = ((Iterable)localObject2).iterator();
      while (((Iterator)localObject2).hasNext())
      {
        ProtoBuf.ValueParameter localValueParameter = (ProtoBuf.ValueParameter)((Iterator)localObject2).next();
        Object localObject3 = INSTANCE;
        Intrinsics.checkExpressionValueIsNotNull(localValueParameter, "it");
        localObject3 = ((JvmProtoBufUtil)localObject3).mapTypeDefault(ProtoTypeTableUtilKt.type(localValueParameter, paramTypeTable), paramNameResolver);
        if (localObject3 != null) {
          paramConstructor.add(localObject3);
        } else {
          return null;
        }
      }
      paramConstructor = CollectionsKt.joinToString$default((Iterable)paramConstructor, (CharSequence)"", (CharSequence)"(", (CharSequence)")V", 0, null, null, 56, null);
    }
    return new JvmMemberSignature.Method((String)localObject1, paramConstructor);
  }
  
  public final JvmMemberSignature.Field getJvmFieldSignature(ProtoBuf.Property paramProperty, NameResolver paramNameResolver, TypeTable paramTypeTable, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramProperty, "proto");
    Intrinsics.checkParameterIsNotNull(paramNameResolver, "nameResolver");
    Intrinsics.checkParameterIsNotNull(paramTypeTable, "typeTable");
    Object localObject = (GeneratedMessageLite.ExtendableMessage)paramProperty;
    GeneratedMessageLite.GeneratedExtension localGeneratedExtension = JvmProtoBuf.propertySignature;
    Intrinsics.checkExpressionValueIsNotNull(localGeneratedExtension, "JvmProtoBuf.propertySignature");
    localObject = (JvmProtoBuf.JvmPropertySignature)ProtoBufUtilKt.getExtensionOrNull((GeneratedMessageLite.ExtendableMessage)localObject, localGeneratedExtension);
    if (localObject != null)
    {
      if (((JvmProtoBuf.JvmPropertySignature)localObject).hasField()) {
        localObject = ((JvmProtoBuf.JvmPropertySignature)localObject).getField();
      } else {
        localObject = null;
      }
      if ((localObject == null) && (paramBoolean)) {
        return null;
      }
      int i;
      if ((localObject != null) && (((JvmProtoBuf.JvmFieldSignature)localObject).hasName())) {
        i = ((JvmProtoBuf.JvmFieldSignature)localObject).getName();
      } else {
        i = paramProperty.getName();
      }
      if ((localObject != null) && (((JvmProtoBuf.JvmFieldSignature)localObject).hasDesc()))
      {
        paramProperty = paramNameResolver.getString(((JvmProtoBuf.JvmFieldSignature)localObject).getDesc());
      }
      else
      {
        paramProperty = mapTypeDefault(ProtoTypeTableUtilKt.returnType(paramProperty, paramTypeTable), paramNameResolver);
        if (paramProperty == null) {
          break label176;
        }
      }
      return new JvmMemberSignature.Field(paramNameResolver.getString(i), paramProperty);
    }
    label176:
    return null;
  }
  
  public final JvmMemberSignature.Method getJvmMethodSignature(ProtoBuf.Function paramFunction, NameResolver paramNameResolver, TypeTable paramTypeTable)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction, "proto");
    Intrinsics.checkParameterIsNotNull(paramNameResolver, "nameResolver");
    Intrinsics.checkParameterIsNotNull(paramTypeTable, "typeTable");
    Object localObject1 = (GeneratedMessageLite.ExtendableMessage)paramFunction;
    Object localObject2 = JvmProtoBuf.methodSignature;
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "JvmProtoBuf.methodSignature");
    localObject2 = (JvmProtoBuf.JvmMethodSignature)ProtoBufUtilKt.getExtensionOrNull((GeneratedMessageLite.ExtendableMessage)localObject1, (GeneratedMessageLite.GeneratedExtension)localObject2);
    int i;
    if ((localObject2 != null) && (((JvmProtoBuf.JvmMethodSignature)localObject2).hasName())) {
      i = ((JvmProtoBuf.JvmMethodSignature)localObject2).getName();
    } else {
      i = paramFunction.getName();
    }
    if ((localObject2 != null) && (((JvmProtoBuf.JvmMethodSignature)localObject2).hasDesc()))
    {
      paramFunction = paramNameResolver.getString(((JvmProtoBuf.JvmMethodSignature)localObject2).getDesc());
    }
    else
    {
      localObject2 = (Collection)CollectionsKt.listOfNotNull(ProtoTypeTableUtilKt.receiverType(paramFunction, paramTypeTable));
      localObject1 = paramFunction.getValueParameterList();
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "proto.valueParameterList");
      Object localObject3 = (Iterable)localObject1;
      localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject3, 10));
      localObject3 = ((Iterable)localObject3).iterator();
      while (((Iterator)localObject3).hasNext())
      {
        ProtoBuf.ValueParameter localValueParameter = (ProtoBuf.ValueParameter)((Iterator)localObject3).next();
        Intrinsics.checkExpressionValueIsNotNull(localValueParameter, "it");
        ((Collection)localObject1).add(ProtoTypeTableUtilKt.type(localValueParameter, paramTypeTable));
      }
      localObject1 = (Iterable)CollectionsKt.plus((Collection)localObject2, (Iterable)localObject1);
      localObject2 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject1, 10));
      localObject1 = ((Iterable)localObject1).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject3 = (ProtoBuf.Type)((Iterator)localObject1).next();
        localObject3 = INSTANCE.mapTypeDefault((ProtoBuf.Type)localObject3, paramNameResolver);
        if (localObject3 != null) {
          ((Collection)localObject2).add(localObject3);
        } else {
          return null;
        }
      }
      localObject2 = (List)localObject2;
      paramTypeTable = mapTypeDefault(ProtoTypeTableUtilKt.returnType(paramFunction, paramTypeTable), paramNameResolver);
      if (paramTypeTable == null) {
        break label408;
      }
      paramFunction = new StringBuilder();
      paramFunction.append(CollectionsKt.joinToString$default((Iterable)localObject2, (CharSequence)"", (CharSequence)"(", (CharSequence)")", 0, null, null, 56, null));
      paramFunction.append(paramTypeTable);
      paramFunction = paramFunction.toString();
    }
    return new JvmMemberSignature.Method(paramNameResolver.getString(i), paramFunction);
    label408:
    return null;
  }
}

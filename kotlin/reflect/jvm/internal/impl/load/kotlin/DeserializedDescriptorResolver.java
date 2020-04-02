package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.KotlinClassHeader;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.KotlinClassHeader.Kind;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMetadataVersion;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassData;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassDeserializer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationComponents;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationConfiguration;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.IncompatibleVersionErrorData;

public final class DeserializedDescriptorResolver
{
  public static final Companion Companion = new Companion(null);
  private static final JvmMetadataVersion KOTLIN_1_1_EAP_METADATA_VERSION = new JvmMetadataVersion(new int[] { 1, 1, 2 });
  private static final JvmMetadataVersion KOTLIN_1_3_M1_METADATA_VERSION = new JvmMetadataVersion(new int[] { 1, 1, 11 });
  private static final JvmMetadataVersion KOTLIN_1_3_RC_METADATA_VERSION = new JvmMetadataVersion(new int[] { 1, 1, 13 });
  private static final Set<KotlinClassHeader.Kind> KOTLIN_CLASS = SetsKt.setOf(KotlinClassHeader.Kind.CLASS);
  private static final Set<KotlinClassHeader.Kind> KOTLIN_FILE_FACADE_OR_MULTIFILE_CLASS_PART = SetsKt.setOf(new KotlinClassHeader.Kind[] { KotlinClassHeader.Kind.FILE_FACADE, KotlinClassHeader.Kind.MULTIFILE_CLASS_PART });
  public DeserializationComponents components;
  
  public DeserializedDescriptorResolver() {}
  
  private final IncompatibleVersionErrorData<JvmMetadataVersion> getIncompatibility(KotlinJvmBinaryClass paramKotlinJvmBinaryClass)
  {
    if ((!getSkipMetadataVersionCheck()) && (!paramKotlinJvmBinaryClass.getClassHeader().getMetadataVersion().isCompatible())) {
      return new IncompatibleVersionErrorData((BinaryVersion)paramKotlinJvmBinaryClass.getClassHeader().getMetadataVersion(), (BinaryVersion)JvmMetadataVersion.INSTANCE, paramKotlinJvmBinaryClass.getLocation(), paramKotlinJvmBinaryClass.getClassId());
    }
    return null;
  }
  
  private final boolean getSkipMetadataVersionCheck()
  {
    DeserializationComponents localDeserializationComponents = this.components;
    if (localDeserializationComponents == null) {
      Intrinsics.throwUninitializedPropertyAccessException("components");
    }
    return localDeserializationComponents.getConfiguration().getSkipMetadataVersionCheck();
  }
  
  private final boolean isCompiledWith13M1(KotlinJvmBinaryClass paramKotlinJvmBinaryClass)
  {
    DeserializationComponents localDeserializationComponents = this.components;
    if (localDeserializationComponents == null) {
      Intrinsics.throwUninitializedPropertyAccessException("components");
    }
    boolean bool;
    if ((!localDeserializationComponents.getConfiguration().getSkipMetadataVersionCheck()) && (paramKotlinJvmBinaryClass.getClassHeader().isPreRelease()) && (Intrinsics.areEqual(paramKotlinJvmBinaryClass.getClassHeader().getMetadataVersion(), KOTLIN_1_3_M1_METADATA_VERSION))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private final boolean isPreReleaseInvisible(KotlinJvmBinaryClass paramKotlinJvmBinaryClass)
  {
    DeserializationComponents localDeserializationComponents = this.components;
    if (localDeserializationComponents == null) {
      Intrinsics.throwUninitializedPropertyAccessException("components");
    }
    boolean bool;
    if (((localDeserializationComponents.getConfiguration().getReportErrorsOnPreReleaseDependencies()) && ((paramKotlinJvmBinaryClass.getClassHeader().isPreRelease()) || (Intrinsics.areEqual(paramKotlinJvmBinaryClass.getClassHeader().getMetadataVersion(), KOTLIN_1_1_EAP_METADATA_VERSION)))) || (isCompiledWith13M1(paramKotlinJvmBinaryClass))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private final String[] readData(KotlinJvmBinaryClass paramKotlinJvmBinaryClass, Set<? extends KotlinClassHeader.Kind> paramSet)
  {
    KotlinClassHeader localKotlinClassHeader = paramKotlinJvmBinaryClass.getClassHeader();
    paramKotlinJvmBinaryClass = localKotlinClassHeader.getData();
    if (paramKotlinJvmBinaryClass == null) {
      paramKotlinJvmBinaryClass = localKotlinClassHeader.getIncompatibleData();
    }
    KotlinJvmBinaryClass localKotlinJvmBinaryClass = null;
    if (paramKotlinJvmBinaryClass != null)
    {
      if (!paramSet.contains(localKotlinClassHeader.getKind())) {
        paramKotlinJvmBinaryClass = null;
      }
      localKotlinJvmBinaryClass = paramKotlinJvmBinaryClass;
    }
    return localKotlinJvmBinaryClass;
  }
  
  /* Error */
  public final kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope createKotlinPackagePartScope(kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor paramPackageFragmentDescriptor, KotlinJvmBinaryClass paramKotlinJvmBinaryClass)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc -86
    //   3: invokestatic 174	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_2
    //   7: ldc -80
    //   9: invokestatic 174	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   12: aload_0
    //   13: aload_2
    //   14: getstatic 53	kotlin/reflect/jvm/internal/impl/load/kotlin/DeserializedDescriptorResolver:KOTLIN_FILE_FACADE_OR_MULTIFILE_CLASS_PART	Ljava/util/Set;
    //   17: invokespecial 178	kotlin/reflect/jvm/internal/impl/load/kotlin/DeserializedDescriptorResolver:readData	(Lkotlin/reflect/jvm/internal/impl/load/kotlin/KotlinJvmBinaryClass;Ljava/util/Set;)[Ljava/lang/String;
    //   20: astore_3
    //   21: aload_3
    //   22: ifnull +229 -> 251
    //   25: aload_2
    //   26: invokeinterface 83 1 0
    //   31: invokevirtual 181	kotlin/reflect/jvm/internal/impl/load/kotlin/header/KotlinClassHeader:getStrings	()[Ljava/lang/String;
    //   34: astore 4
    //   36: aload 4
    //   38: ifnull +213 -> 251
    //   41: aload_3
    //   42: aload 4
    //   44: invokestatic 187	kotlin/reflect/jvm/internal/impl/metadata/jvm/deserialization/JvmProtoBufUtil:readPackageDataFrom	([Ljava/lang/String;[Ljava/lang/String;)Lkotlin/Pair;
    //   47: astore_3
    //   48: goto +86 -> 134
    //   51: astore_3
    //   52: goto +58 -> 110
    //   55: astore 4
    //   57: new 189	java/lang/IllegalStateException
    //   60: astore_3
    //   61: new 191	java/lang/StringBuilder
    //   64: astore 5
    //   66: aload 5
    //   68: invokespecial 192	java/lang/StringBuilder:<init>	()V
    //   71: aload 5
    //   73: ldc -62
    //   75: invokevirtual 198	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   78: pop
    //   79: aload 5
    //   81: aload_2
    //   82: invokeinterface 102 1 0
    //   87: invokevirtual 198	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   90: pop
    //   91: aload_3
    //   92: aload 5
    //   94: invokevirtual 201	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   97: aload 4
    //   99: checkcast 203	java/lang/Throwable
    //   102: invokespecial 206	java/lang/IllegalStateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   105: aload_3
    //   106: checkcast 203	java/lang/Throwable
    //   109: athrow
    //   110: aload_0
    //   111: invokestatic 208	kotlin/reflect/jvm/internal/impl/load/kotlin/DeserializedDescriptorResolver:access$getSkipMetadataVersionCheck$p	(Lkotlin/reflect/jvm/internal/impl/load/kotlin/DeserializedDescriptorResolver;)Z
    //   114: ifne +135 -> 249
    //   117: aload_2
    //   118: invokeinterface 83 1 0
    //   123: invokevirtual 88	kotlin/reflect/jvm/internal/impl/load/kotlin/header/KotlinClassHeader:getMetadataVersion	()Lkotlin/reflect/jvm/internal/impl/metadata/jvm/deserialization/JvmMetadataVersion;
    //   126: invokevirtual 91	kotlin/reflect/jvm/internal/impl/metadata/jvm/deserialization/JvmMetadataVersion:isCompatible	()Z
    //   129: ifne +120 -> 249
    //   132: aconst_null
    //   133: astore_3
    //   134: aload_3
    //   135: ifnull +112 -> 247
    //   138: aload_3
    //   139: invokevirtual 214	kotlin/Pair:component1	()Ljava/lang/Object;
    //   142: checkcast 216	kotlin/reflect/jvm/internal/impl/metadata/jvm/deserialization/JvmNameResolver
    //   145: astore 4
    //   147: aload_3
    //   148: invokevirtual 219	kotlin/Pair:component2	()Ljava/lang/Object;
    //   151: checkcast 221	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package
    //   154: astore_3
    //   155: aload 4
    //   157: checkcast 223	kotlin/reflect/jvm/internal/impl/metadata/deserialization/NameResolver
    //   160: astore 4
    //   162: new 225	kotlin/reflect/jvm/internal/impl/load/kotlin/JvmPackagePartSource
    //   165: dup
    //   166: aload_2
    //   167: aload_3
    //   168: aload 4
    //   170: aload_0
    //   171: aload_2
    //   172: invokespecial 227	kotlin/reflect/jvm/internal/impl/load/kotlin/DeserializedDescriptorResolver:getIncompatibility	(Lkotlin/reflect/jvm/internal/impl/load/kotlin/KotlinJvmBinaryClass;)Lkotlin/reflect/jvm/internal/impl/serialization/deserialization/IncompatibleVersionErrorData;
    //   175: aload_0
    //   176: aload_2
    //   177: invokespecial 229	kotlin/reflect/jvm/internal/impl/load/kotlin/DeserializedDescriptorResolver:isPreReleaseInvisible	(Lkotlin/reflect/jvm/internal/impl/load/kotlin/KotlinJvmBinaryClass;)Z
    //   180: invokespecial 232	kotlin/reflect/jvm/internal/impl/load/kotlin/JvmPackagePartSource:<init>	(Lkotlin/reflect/jvm/internal/impl/load/kotlin/KotlinJvmBinaryClass;Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package;Lkotlin/reflect/jvm/internal/impl/metadata/deserialization/NameResolver;Lkotlin/reflect/jvm/internal/impl/serialization/deserialization/IncompatibleVersionErrorData;Z)V
    //   183: astore 5
    //   185: aload_2
    //   186: invokeinterface 83 1 0
    //   191: invokevirtual 88	kotlin/reflect/jvm/internal/impl/load/kotlin/header/KotlinClassHeader:getMetadataVersion	()Lkotlin/reflect/jvm/internal/impl/metadata/jvm/deserialization/JvmMetadataVersion;
    //   194: checkcast 95	kotlin/reflect/jvm/internal/impl/metadata/deserialization/BinaryVersion
    //   197: astore_2
    //   198: aload 5
    //   200: checkcast 234	kotlin/reflect/jvm/internal/impl/serialization/deserialization/descriptors/DeserializedContainerSource
    //   203: astore 5
    //   205: aload_0
    //   206: getfield 113	kotlin/reflect/jvm/internal/impl/load/kotlin/DeserializedDescriptorResolver:components	Lkotlin/reflect/jvm/internal/impl/serialization/deserialization/DeserializationComponents;
    //   209: astore 6
    //   211: aload 6
    //   213: ifnonnull +8 -> 221
    //   216: ldc 114
    //   218: invokestatic 120	kotlin/jvm/internal/Intrinsics:throwUninitializedPropertyAccessException	(Ljava/lang/String;)V
    //   221: new 236	kotlin/reflect/jvm/internal/impl/serialization/deserialization/descriptors/DeserializedPackageMemberScope
    //   224: dup
    //   225: aload_1
    //   226: aload_3
    //   227: aload 4
    //   229: aload_2
    //   230: aload 5
    //   232: aload 6
    //   234: getstatic 239	kotlin/reflect/jvm/internal/impl/load/kotlin/DeserializedDescriptorResolver$createKotlinPackagePartScope$2:INSTANCE	Lkotlin/reflect/jvm/internal/impl/load/kotlin/DeserializedDescriptorResolver$createKotlinPackagePartScope$2;
    //   237: checkcast 241	kotlin/jvm/functions/Function0
    //   240: invokespecial 244	kotlin/reflect/jvm/internal/impl/serialization/deserialization/descriptors/DeserializedPackageMemberScope:<init>	(Lkotlin/reflect/jvm/internal/impl/descriptors/PackageFragmentDescriptor;Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package;Lkotlin/reflect/jvm/internal/impl/metadata/deserialization/NameResolver;Lkotlin/reflect/jvm/internal/impl/metadata/deserialization/BinaryVersion;Lkotlin/reflect/jvm/internal/impl/serialization/deserialization/descriptors/DeserializedContainerSource;Lkotlin/reflect/jvm/internal/impl/serialization/deserialization/DeserializationComponents;Lkotlin/jvm/functions/Function0;)V
    //   243: checkcast 246	kotlin/reflect/jvm/internal/impl/resolve/scopes/MemberScope
    //   246: areturn
    //   247: aconst_null
    //   248: areturn
    //   249: aload_3
    //   250: athrow
    //   251: aconst_null
    //   252: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	253	0	this	DeserializedDescriptorResolver
    //   0	253	1	paramPackageFragmentDescriptor	kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor
    //   0	253	2	paramKotlinJvmBinaryClass	KotlinJvmBinaryClass
    //   20	28	3	localObject1	Object
    //   51	1	3	localObject2	Object
    //   60	190	3	localObject3	Object
    //   34	9	4	arrayOfString	String[]
    //   55	43	4	localInvalidProtocolBufferException	kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException
    //   145	83	4	localObject4	Object
    //   64	167	5	localObject5	Object
    //   209	24	6	localDeserializationComponents	DeserializationComponents
    // Exception table:
    //   from	to	target	type
    //   41	48	51	finally
    //   57	110	51	finally
    //   41	48	55	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
  }
  
  public final DeserializationComponents getComponents()
  {
    DeserializationComponents localDeserializationComponents = this.components;
    if (localDeserializationComponents == null) {
      Intrinsics.throwUninitializedPropertyAccessException("components");
    }
    return localDeserializationComponents;
  }
  
  /* Error */
  public final ClassData readClassData$descriptors_jvm(KotlinJvmBinaryClass paramKotlinJvmBinaryClass)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc -80
    //   3: invokestatic 174	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_0
    //   7: aload_1
    //   8: getstatic 42	kotlin/reflect/jvm/internal/impl/load/kotlin/DeserializedDescriptorResolver:KOTLIN_CLASS	Ljava/util/Set;
    //   11: invokespecial 178	kotlin/reflect/jvm/internal/impl/load/kotlin/DeserializedDescriptorResolver:readData	(Lkotlin/reflect/jvm/internal/impl/load/kotlin/KotlinJvmBinaryClass;Ljava/util/Set;)[Ljava/lang/String;
    //   14: astore_2
    //   15: aload_2
    //   16: ifnull +178 -> 194
    //   19: aload_1
    //   20: invokeinterface 83 1 0
    //   25: invokevirtual 181	kotlin/reflect/jvm/internal/impl/load/kotlin/header/KotlinClassHeader:getStrings	()[Ljava/lang/String;
    //   28: astore_3
    //   29: aload_3
    //   30: ifnull +164 -> 194
    //   33: aload_2
    //   34: aload_3
    //   35: invokestatic 253	kotlin/reflect/jvm/internal/impl/metadata/jvm/deserialization/JvmProtoBufUtil:readClassDataFrom	([Ljava/lang/String;[Ljava/lang/String;)Lkotlin/Pair;
    //   38: astore_2
    //   39: goto +81 -> 120
    //   42: astore_2
    //   43: goto +53 -> 96
    //   46: astore 4
    //   48: new 189	java/lang/IllegalStateException
    //   51: astore_2
    //   52: new 191	java/lang/StringBuilder
    //   55: astore_3
    //   56: aload_3
    //   57: invokespecial 192	java/lang/StringBuilder:<init>	()V
    //   60: aload_3
    //   61: ldc -62
    //   63: invokevirtual 198	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   66: pop
    //   67: aload_3
    //   68: aload_1
    //   69: invokeinterface 102 1 0
    //   74: invokevirtual 198	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: pop
    //   78: aload_2
    //   79: aload_3
    //   80: invokevirtual 201	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   83: aload 4
    //   85: checkcast 203	java/lang/Throwable
    //   88: invokespecial 206	java/lang/IllegalStateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   91: aload_2
    //   92: checkcast 203	java/lang/Throwable
    //   95: athrow
    //   96: aload_0
    //   97: invokestatic 208	kotlin/reflect/jvm/internal/impl/load/kotlin/DeserializedDescriptorResolver:access$getSkipMetadataVersionCheck$p	(Lkotlin/reflect/jvm/internal/impl/load/kotlin/DeserializedDescriptorResolver;)Z
    //   100: ifne +92 -> 192
    //   103: aload_1
    //   104: invokeinterface 83 1 0
    //   109: invokevirtual 88	kotlin/reflect/jvm/internal/impl/load/kotlin/header/KotlinClassHeader:getMetadataVersion	()Lkotlin/reflect/jvm/internal/impl/metadata/jvm/deserialization/JvmMetadataVersion;
    //   112: invokevirtual 91	kotlin/reflect/jvm/internal/impl/metadata/jvm/deserialization/JvmMetadataVersion:isCompatible	()Z
    //   115: ifne +77 -> 192
    //   118: aconst_null
    //   119: astore_2
    //   120: aload_2
    //   121: ifnull +69 -> 190
    //   124: aload_2
    //   125: invokevirtual 214	kotlin/Pair:component1	()Ljava/lang/Object;
    //   128: checkcast 216	kotlin/reflect/jvm/internal/impl/metadata/jvm/deserialization/JvmNameResolver
    //   131: astore_3
    //   132: aload_2
    //   133: invokevirtual 219	kotlin/Pair:component2	()Ljava/lang/Object;
    //   136: checkcast 255	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class
    //   139: astore_2
    //   140: new 257	kotlin/reflect/jvm/internal/impl/load/kotlin/KotlinJvmBinarySourceElement
    //   143: dup
    //   144: aload_1
    //   145: aload_0
    //   146: aload_1
    //   147: invokespecial 227	kotlin/reflect/jvm/internal/impl/load/kotlin/DeserializedDescriptorResolver:getIncompatibility	(Lkotlin/reflect/jvm/internal/impl/load/kotlin/KotlinJvmBinaryClass;)Lkotlin/reflect/jvm/internal/impl/serialization/deserialization/IncompatibleVersionErrorData;
    //   150: aload_0
    //   151: aload_1
    //   152: invokespecial 229	kotlin/reflect/jvm/internal/impl/load/kotlin/DeserializedDescriptorResolver:isPreReleaseInvisible	(Lkotlin/reflect/jvm/internal/impl/load/kotlin/KotlinJvmBinaryClass;)Z
    //   155: invokespecial 260	kotlin/reflect/jvm/internal/impl/load/kotlin/KotlinJvmBinarySourceElement:<init>	(Lkotlin/reflect/jvm/internal/impl/load/kotlin/KotlinJvmBinaryClass;Lkotlin/reflect/jvm/internal/impl/serialization/deserialization/IncompatibleVersionErrorData;Z)V
    //   158: astore 4
    //   160: new 262	kotlin/reflect/jvm/internal/impl/serialization/deserialization/ClassData
    //   163: dup
    //   164: aload_3
    //   165: checkcast 223	kotlin/reflect/jvm/internal/impl/metadata/deserialization/NameResolver
    //   168: aload_2
    //   169: aload_1
    //   170: invokeinterface 83 1 0
    //   175: invokevirtual 88	kotlin/reflect/jvm/internal/impl/load/kotlin/header/KotlinClassHeader:getMetadataVersion	()Lkotlin/reflect/jvm/internal/impl/metadata/jvm/deserialization/JvmMetadataVersion;
    //   178: checkcast 95	kotlin/reflect/jvm/internal/impl/metadata/deserialization/BinaryVersion
    //   181: aload 4
    //   183: checkcast 264	kotlin/reflect/jvm/internal/impl/descriptors/SourceElement
    //   186: invokespecial 267	kotlin/reflect/jvm/internal/impl/serialization/deserialization/ClassData:<init>	(Lkotlin/reflect/jvm/internal/impl/metadata/deserialization/NameResolver;Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class;Lkotlin/reflect/jvm/internal/impl/metadata/deserialization/BinaryVersion;Lkotlin/reflect/jvm/internal/impl/descriptors/SourceElement;)V
    //   189: areturn
    //   190: aconst_null
    //   191: areturn
    //   192: aload_2
    //   193: athrow
    //   194: aconst_null
    //   195: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	196	0	this	DeserializedDescriptorResolver
    //   0	196	1	paramKotlinJvmBinaryClass	KotlinJvmBinaryClass
    //   14	25	2	localObject1	Object
    //   42	1	2	localObject2	Object
    //   51	142	2	localObject3	Object
    //   28	137	3	localObject4	Object
    //   46	38	4	localInvalidProtocolBufferException	kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException
    //   158	24	4	localKotlinJvmBinarySourceElement	KotlinJvmBinarySourceElement
    // Exception table:
    //   from	to	target	type
    //   33	39	42	finally
    //   48	96	42	finally
    //   33	39	46	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
  }
  
  public final ClassDescriptor resolveClass(KotlinJvmBinaryClass paramKotlinJvmBinaryClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinJvmBinaryClass, "kotlinClass");
    ClassData localClassData = readClassData$descriptors_jvm(paramKotlinJvmBinaryClass);
    if (localClassData != null)
    {
      DeserializationComponents localDeserializationComponents = this.components;
      if (localDeserializationComponents == null) {
        Intrinsics.throwUninitializedPropertyAccessException("components");
      }
      return localDeserializationComponents.getClassDeserializer().deserializeClass(paramKotlinJvmBinaryClass.getClassId(), localClassData);
    }
    return null;
  }
  
  public final void setComponents(DeserializationComponentsForJava paramDeserializationComponentsForJava)
  {
    Intrinsics.checkParameterIsNotNull(paramDeserializationComponentsForJava, "components");
    this.components = paramDeserializationComponentsForJava.getComponents();
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    public final JvmMetadataVersion getKOTLIN_1_3_RC_METADATA_VERSION$descriptors_jvm()
    {
      return DeserializedDescriptorResolver.access$getKOTLIN_1_3_RC_METADATA_VERSION$cp();
    }
  }
}

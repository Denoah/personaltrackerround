package kotlin.reflect.jvm.internal.impl.metadata.jvm;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Class;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Constructor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Function;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Package;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Property;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeParameter;
import kotlin.reflect.jvm.internal.impl.protobuf.AbstractParser;
import kotlin.reflect.jvm.internal.impl.protobuf.ByteString;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedOutputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.GeneratedExtension;
import kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLite;
import kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLiteMap;
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.Parser;
import kotlin.reflect.jvm.internal.impl.protobuf.WireFormat.FieldType;

public final class JvmProtoBuf
{
  public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, Integer> anonymousObjectOriginName;
  public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, List<ProtoBuf.Property>> classLocalVariable;
  public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, Integer> classModuleName;
  public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Constructor, JvmMethodSignature> constructorSignature = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Constructor.getDefaultInstance(), JvmMethodSignature.getDefaultInstance(), JvmMethodSignature.getDefaultInstance(), null, 100, WireFormat.FieldType.MESSAGE, JvmMethodSignature.class);
  public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, Integer> flags;
  public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Type, Boolean> isRaw;
  public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, Integer> lambdaClassOriginName;
  public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, JvmMethodSignature> methodSignature = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Function.getDefaultInstance(), JvmMethodSignature.getDefaultInstance(), JvmMethodSignature.getDefaultInstance(), null, 100, WireFormat.FieldType.MESSAGE, JvmMethodSignature.class);
  public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, List<ProtoBuf.Property>> packageLocalVariable = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.Package.getDefaultInstance(), ProtoBuf.Property.getDefaultInstance(), null, 102, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Property.class);
  public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, Integer> packageModuleName;
  public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, JvmPropertySignature> propertySignature;
  public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Type, List<ProtoBuf.Annotation>> typeAnnotation;
  public static final GeneratedMessageLite.GeneratedExtension<ProtoBuf.TypeParameter, List<ProtoBuf.Annotation>> typeParameterAnnotation;
  
  static
  {
    ProtoBuf.Function localFunction = ProtoBuf.Function.getDefaultInstance();
    Integer localInteger = Integer.valueOf(0);
    lambdaClassOriginName = GeneratedMessageLite.newSingularGeneratedExtension(localFunction, localInteger, null, null, 101, WireFormat.FieldType.INT32, Integer.class);
    propertySignature = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Property.getDefaultInstance(), JvmPropertySignature.getDefaultInstance(), JvmPropertySignature.getDefaultInstance(), null, 100, WireFormat.FieldType.MESSAGE, JvmPropertySignature.class);
    flags = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Property.getDefaultInstance(), localInteger, null, null, 101, WireFormat.FieldType.INT32, Integer.class);
    typeAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.Type.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 100, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    isRaw = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Type.getDefaultInstance(), Boolean.valueOf(false), null, null, 101, WireFormat.FieldType.BOOL, Boolean.class);
    typeParameterAnnotation = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.TypeParameter.getDefaultInstance(), ProtoBuf.Annotation.getDefaultInstance(), null, 100, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Annotation.class);
    classModuleName = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Class.getDefaultInstance(), localInteger, null, null, 101, WireFormat.FieldType.INT32, Integer.class);
    classLocalVariable = GeneratedMessageLite.newRepeatedGeneratedExtension(ProtoBuf.Class.getDefaultInstance(), ProtoBuf.Property.getDefaultInstance(), null, 102, WireFormat.FieldType.MESSAGE, false, ProtoBuf.Property.class);
    anonymousObjectOriginName = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Class.getDefaultInstance(), localInteger, null, null, 103, WireFormat.FieldType.INT32, Integer.class);
    packageModuleName = GeneratedMessageLite.newSingularGeneratedExtension(ProtoBuf.Package.getDefaultInstance(), localInteger, null, null, 101, WireFormat.FieldType.INT32, Integer.class);
  }
  
  public static void registerAllExtensions(ExtensionRegistryLite paramExtensionRegistryLite)
  {
    paramExtensionRegistryLite.add(constructorSignature);
    paramExtensionRegistryLite.add(methodSignature);
    paramExtensionRegistryLite.add(lambdaClassOriginName);
    paramExtensionRegistryLite.add(propertySignature);
    paramExtensionRegistryLite.add(flags);
    paramExtensionRegistryLite.add(typeAnnotation);
    paramExtensionRegistryLite.add(isRaw);
    paramExtensionRegistryLite.add(typeParameterAnnotation);
    paramExtensionRegistryLite.add(classModuleName);
    paramExtensionRegistryLite.add(classLocalVariable);
    paramExtensionRegistryLite.add(anonymousObjectOriginName);
    paramExtensionRegistryLite.add(packageModuleName);
    paramExtensionRegistryLite.add(packageLocalVariable);
  }
  
  public static final class JvmFieldSignature
    extends GeneratedMessageLite
    implements JvmProtoBuf.JvmFieldSignatureOrBuilder
  {
    public static Parser<JvmFieldSignature> PARSER = new AbstractParser()
    {
      public JvmProtoBuf.JvmFieldSignature parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new JvmProtoBuf.JvmFieldSignature(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final JvmFieldSignature defaultInstance;
    private int bitField0_;
    private int desc_;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private int name_;
    private final ByteString unknownFields;
    
    static
    {
      JvmFieldSignature localJvmFieldSignature = new JvmFieldSignature(true);
      defaultInstance = localJvmFieldSignature;
      localJvmFieldSignature.initFields();
    }
    
    /* Error */
    private JvmFieldSignature(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 50	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: i2b
      //   7: putfield 52	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature:memoizedIsInitialized	B
      //   10: aload_0
      //   11: iconst_m1
      //   12: putfield 54	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature:memoizedSerializedSize	I
      //   15: aload_0
      //   16: invokespecial 43	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature:initFields	()V
      //   19: invokestatic 60	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   22: astore_3
      //   23: aload_3
      //   24: iconst_1
      //   25: invokestatic 66	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   28: astore 4
      //   30: iconst_0
      //   31: istore 5
      //   33: iload 5
      //   35: ifne +155 -> 190
      //   38: aload_1
      //   39: invokevirtual 72	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   42: istore 6
      //   44: iload 6
      //   46: ifeq +75 -> 121
      //   49: iload 6
      //   51: bipush 8
      //   53: if_icmpeq +47 -> 100
      //   56: iload 6
      //   58: bipush 16
      //   60: if_icmpeq +19 -> 79
      //   63: aload_0
      //   64: aload_1
      //   65: aload 4
      //   67: aload_2
      //   68: iload 6
      //   70: invokevirtual 76	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   73: ifne -40 -> 33
      //   76: goto +45 -> 121
      //   79: aload_0
      //   80: aload_0
      //   81: getfield 78	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature:bitField0_	I
      //   84: iconst_2
      //   85: ior
      //   86: putfield 78	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature:bitField0_	I
      //   89: aload_0
      //   90: aload_1
      //   91: invokevirtual 81	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   94: putfield 83	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature:desc_	I
      //   97: goto -64 -> 33
      //   100: aload_0
      //   101: aload_0
      //   102: getfield 78	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature:bitField0_	I
      //   105: iconst_1
      //   106: ior
      //   107: putfield 78	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature:bitField0_	I
      //   110: aload_0
      //   111: aload_1
      //   112: invokevirtual 81	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   115: putfield 85	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature:name_	I
      //   118: goto -85 -> 33
      //   121: iconst_1
      //   122: istore 5
      //   124: goto -91 -> 33
      //   127: astore_1
      //   128: goto +29 -> 157
      //   131: astore_2
      //   132: new 47	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   135: astore_1
      //   136: aload_1
      //   137: aload_2
      //   138: invokevirtual 89	java/io/IOException:getMessage	()Ljava/lang/String;
      //   141: invokespecial 92	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   144: aload_1
      //   145: aload_0
      //   146: invokevirtual 96	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   149: athrow
      //   150: astore_1
      //   151: aload_1
      //   152: aload_0
      //   153: invokevirtual 96	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   156: athrow
      //   157: aload 4
      //   159: invokevirtual 99	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   162: aload_0
      //   163: aload_3
      //   164: invokevirtual 105	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   167: putfield 107	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   170: goto +14 -> 184
      //   173: astore_1
      //   174: aload_0
      //   175: aload_3
      //   176: invokevirtual 105	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   179: putfield 107	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   182: aload_1
      //   183: athrow
      //   184: aload_0
      //   185: invokevirtual 110	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature:makeExtensionsImmutable	()V
      //   188: aload_1
      //   189: athrow
      //   190: aload 4
      //   192: invokevirtual 99	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   195: aload_0
      //   196: aload_3
      //   197: invokevirtual 105	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   200: putfield 107	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   203: goto +14 -> 217
      //   206: astore_1
      //   207: aload_0
      //   208: aload_3
      //   209: invokevirtual 105	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   212: putfield 107	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   215: aload_1
      //   216: athrow
      //   217: aload_0
      //   218: invokevirtual 110	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature:makeExtensionsImmutable	()V
      //   221: return
      //   222: astore_2
      //   223: goto -61 -> 162
      //   226: astore_1
      //   227: goto -32 -> 195
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	230	0	this	JvmFieldSignature
      //   0	230	1	paramCodedInputStream	CodedInputStream
      //   0	230	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   22	187	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   28	163	4	localCodedOutputStream	CodedOutputStream
      //   31	92	5	i	int
      //   42	27	6	j	int
      // Exception table:
      //   from	to	target	type
      //   38	44	127	finally
      //   63	76	127	finally
      //   79	97	127	finally
      //   100	118	127	finally
      //   132	150	127	finally
      //   151	157	127	finally
      //   38	44	131	java/io/IOException
      //   63	76	131	java/io/IOException
      //   79	97	131	java/io/IOException
      //   100	118	131	java/io/IOException
      //   38	44	150	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   63	76	150	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   79	97	150	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   100	118	150	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   157	162	173	finally
      //   190	195	206	finally
      //   157	162	222	java/io/IOException
      //   190	195	226	java/io/IOException
    }
    
    private JvmFieldSignature(GeneratedMessageLite.Builder paramBuilder)
    {
      super();
      this.unknownFields = paramBuilder.getUnknownFields();
    }
    
    private JvmFieldSignature(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static JvmFieldSignature getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.name_ = 0;
      this.desc_ = 0;
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$2500();
    }
    
    public static Builder newBuilder(JvmFieldSignature paramJvmFieldSignature)
    {
      return newBuilder().mergeFrom(paramJvmFieldSignature);
    }
    
    public JvmFieldSignature getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public int getDesc()
    {
      return this.desc_;
    }
    
    public int getName()
    {
      return this.name_;
    }
    
    public Parser<JvmFieldSignature> getParserForType()
    {
      return PARSER;
    }
    
    public int getSerializedSize()
    {
      int i = this.memoizedSerializedSize;
      if (i != -1) {
        return i;
      }
      i = 0;
      if ((this.bitField0_ & 0x1) == 1) {
        i = 0 + CodedOutputStream.computeInt32Size(1, this.name_);
      }
      int j = i;
      if ((this.bitField0_ & 0x2) == 2) {
        j = i + CodedOutputStream.computeInt32Size(2, this.desc_);
      }
      i = j + this.unknownFields.size();
      this.memoizedSerializedSize = i;
      return i;
    }
    
    public boolean hasDesc()
    {
      boolean bool;
      if ((this.bitField0_ & 0x2) == 2) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasName()
    {
      int i = this.bitField0_;
      boolean bool = true;
      if ((i & 0x1) != 1) {
        bool = false;
      }
      return bool;
    }
    
    public final boolean isInitialized()
    {
      int i = this.memoizedIsInitialized;
      if (i == 1) {
        return true;
      }
      if (i == 0) {
        return false;
      }
      this.memoizedIsInitialized = ((byte)1);
      return true;
    }
    
    public Builder newBuilderForType()
    {
      return newBuilder();
    }
    
    public Builder toBuilder()
    {
      return newBuilder(this);
    }
    
    public void writeTo(CodedOutputStream paramCodedOutputStream)
      throws IOException
    {
      getSerializedSize();
      if ((this.bitField0_ & 0x1) == 1) {
        paramCodedOutputStream.writeInt32(1, this.name_);
      }
      if ((this.bitField0_ & 0x2) == 2) {
        paramCodedOutputStream.writeInt32(2, this.desc_);
      }
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Builder
      extends GeneratedMessageLite.Builder<JvmProtoBuf.JvmFieldSignature, Builder>
      implements JvmProtoBuf.JvmFieldSignatureOrBuilder
    {
      private int bitField0_;
      private int desc_;
      private int name_;
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public JvmProtoBuf.JvmFieldSignature build()
      {
        JvmProtoBuf.JvmFieldSignature localJvmFieldSignature = buildPartial();
        if (localJvmFieldSignature.isInitialized()) {
          return localJvmFieldSignature;
        }
        throw newUninitializedMessageException(localJvmFieldSignature);
      }
      
      public JvmProtoBuf.JvmFieldSignature buildPartial()
      {
        JvmProtoBuf.JvmFieldSignature localJvmFieldSignature = new JvmProtoBuf.JvmFieldSignature(this, null);
        int i = this.bitField0_;
        int j = 1;
        if ((i & 0x1) != 1) {
          j = 0;
        }
        JvmProtoBuf.JvmFieldSignature.access$2702(localJvmFieldSignature, this.name_);
        int k = j;
        if ((i & 0x2) == 2) {
          k = j | 0x2;
        }
        JvmProtoBuf.JvmFieldSignature.access$2802(localJvmFieldSignature, this.desc_);
        JvmProtoBuf.JvmFieldSignature.access$2902(localJvmFieldSignature, k);
        return localJvmFieldSignature;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public JvmProtoBuf.JvmFieldSignature getDefaultInstanceForType()
      {
        return JvmProtoBuf.JvmFieldSignature.getDefaultInstance();
      }
      
      public final boolean isInitialized()
      {
        return true;
      }
      
      public Builder mergeFrom(JvmProtoBuf.JvmFieldSignature paramJvmFieldSignature)
      {
        if (paramJvmFieldSignature == JvmProtoBuf.JvmFieldSignature.getDefaultInstance()) {
          return this;
        }
        if (paramJvmFieldSignature.hasName()) {
          setName(paramJvmFieldSignature.getName());
        }
        if (paramJvmFieldSignature.hasDesc()) {
          setDesc(paramJvmFieldSignature.getDesc());
        }
        setUnknownFields(getUnknownFields().concat(paramJvmFieldSignature.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 133	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 139 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 77	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature;)Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_2
        //   29: aload_3
        //   30: astore_1
        //   31: goto +15 -> 46
        //   34: astore_2
        //   35: aload_2
        //   36: invokevirtual 142	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   39: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature
        //   42: astore_1
        //   43: aload_2
        //   44: athrow
        //   45: astore_2
        //   46: aload_1
        //   47: ifnull +9 -> 56
        //   50: aload_0
        //   51: aload_1
        //   52: invokevirtual 77	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature;)Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature$Builder;
        //   55: pop
        //   56: aload_2
        //   57: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	58	0	this	Builder
        //   0	58	1	paramCodedInputStream	CodedInputStream
        //   0	58	2	paramExtensionRegistryLite	ExtensionRegistryLite
        //   1	29	3	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   2	16	28	finally
        //   35	43	28	finally
        //   2	16	34	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   43	45	45	finally
      }
      
      public Builder setDesc(int paramInt)
      {
        this.bitField0_ |= 0x2;
        this.desc_ = paramInt;
        return this;
      }
      
      public Builder setName(int paramInt)
      {
        this.bitField0_ |= 0x1;
        this.name_ = paramInt;
        return this;
      }
    }
  }
  
  public static final class JvmMethodSignature
    extends GeneratedMessageLite
    implements JvmProtoBuf.JvmMethodSignatureOrBuilder
  {
    public static Parser<JvmMethodSignature> PARSER = new AbstractParser()
    {
      public JvmProtoBuf.JvmMethodSignature parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new JvmProtoBuf.JvmMethodSignature(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final JvmMethodSignature defaultInstance;
    private int bitField0_;
    private int desc_;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private int name_;
    private final ByteString unknownFields;
    
    static
    {
      JvmMethodSignature localJvmMethodSignature = new JvmMethodSignature(true);
      defaultInstance = localJvmMethodSignature;
      localJvmMethodSignature.initFields();
    }
    
    /* Error */
    private JvmMethodSignature(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 50	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: i2b
      //   7: putfield 52	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:memoizedIsInitialized	B
      //   10: aload_0
      //   11: iconst_m1
      //   12: putfield 54	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:memoizedSerializedSize	I
      //   15: aload_0
      //   16: invokespecial 43	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:initFields	()V
      //   19: invokestatic 60	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   22: astore_3
      //   23: aload_3
      //   24: iconst_1
      //   25: invokestatic 66	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   28: astore 4
      //   30: iconst_0
      //   31: istore 5
      //   33: iload 5
      //   35: ifne +155 -> 190
      //   38: aload_1
      //   39: invokevirtual 72	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   42: istore 6
      //   44: iload 6
      //   46: ifeq +75 -> 121
      //   49: iload 6
      //   51: bipush 8
      //   53: if_icmpeq +47 -> 100
      //   56: iload 6
      //   58: bipush 16
      //   60: if_icmpeq +19 -> 79
      //   63: aload_0
      //   64: aload_1
      //   65: aload 4
      //   67: aload_2
      //   68: iload 6
      //   70: invokevirtual 76	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   73: ifne -40 -> 33
      //   76: goto +45 -> 121
      //   79: aload_0
      //   80: aload_0
      //   81: getfield 78	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:bitField0_	I
      //   84: iconst_2
      //   85: ior
      //   86: putfield 78	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:bitField0_	I
      //   89: aload_0
      //   90: aload_1
      //   91: invokevirtual 81	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   94: putfield 83	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:desc_	I
      //   97: goto -64 -> 33
      //   100: aload_0
      //   101: aload_0
      //   102: getfield 78	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:bitField0_	I
      //   105: iconst_1
      //   106: ior
      //   107: putfield 78	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:bitField0_	I
      //   110: aload_0
      //   111: aload_1
      //   112: invokevirtual 81	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   115: putfield 85	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:name_	I
      //   118: goto -85 -> 33
      //   121: iconst_1
      //   122: istore 5
      //   124: goto -91 -> 33
      //   127: astore_1
      //   128: goto +29 -> 157
      //   131: astore_2
      //   132: new 47	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   135: astore_1
      //   136: aload_1
      //   137: aload_2
      //   138: invokevirtual 89	java/io/IOException:getMessage	()Ljava/lang/String;
      //   141: invokespecial 92	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   144: aload_1
      //   145: aload_0
      //   146: invokevirtual 96	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   149: athrow
      //   150: astore_1
      //   151: aload_1
      //   152: aload_0
      //   153: invokevirtual 96	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   156: athrow
      //   157: aload 4
      //   159: invokevirtual 99	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   162: aload_0
      //   163: aload_3
      //   164: invokevirtual 105	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   167: putfield 107	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   170: goto +14 -> 184
      //   173: astore_1
      //   174: aload_0
      //   175: aload_3
      //   176: invokevirtual 105	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   179: putfield 107	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   182: aload_1
      //   183: athrow
      //   184: aload_0
      //   185: invokevirtual 110	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:makeExtensionsImmutable	()V
      //   188: aload_1
      //   189: athrow
      //   190: aload 4
      //   192: invokevirtual 99	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   195: aload_0
      //   196: aload_3
      //   197: invokevirtual 105	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   200: putfield 107	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   203: goto +14 -> 217
      //   206: astore_1
      //   207: aload_0
      //   208: aload_3
      //   209: invokevirtual 105	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   212: putfield 107	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   215: aload_1
      //   216: athrow
      //   217: aload_0
      //   218: invokevirtual 110	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:makeExtensionsImmutable	()V
      //   221: return
      //   222: astore_2
      //   223: goto -61 -> 162
      //   226: astore_1
      //   227: goto -32 -> 195
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	230	0	this	JvmMethodSignature
      //   0	230	1	paramCodedInputStream	CodedInputStream
      //   0	230	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   22	187	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   28	163	4	localCodedOutputStream	CodedOutputStream
      //   31	92	5	i	int
      //   42	27	6	j	int
      // Exception table:
      //   from	to	target	type
      //   38	44	127	finally
      //   63	76	127	finally
      //   79	97	127	finally
      //   100	118	127	finally
      //   132	150	127	finally
      //   151	157	127	finally
      //   38	44	131	java/io/IOException
      //   63	76	131	java/io/IOException
      //   79	97	131	java/io/IOException
      //   100	118	131	java/io/IOException
      //   38	44	150	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   63	76	150	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   79	97	150	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   100	118	150	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   157	162	173	finally
      //   190	195	206	finally
      //   157	162	222	java/io/IOException
      //   190	195	226	java/io/IOException
    }
    
    private JvmMethodSignature(GeneratedMessageLite.Builder paramBuilder)
    {
      super();
      this.unknownFields = paramBuilder.getUnknownFields();
    }
    
    private JvmMethodSignature(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static JvmMethodSignature getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.name_ = 0;
      this.desc_ = 0;
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$1800();
    }
    
    public static Builder newBuilder(JvmMethodSignature paramJvmMethodSignature)
    {
      return newBuilder().mergeFrom(paramJvmMethodSignature);
    }
    
    public JvmMethodSignature getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public int getDesc()
    {
      return this.desc_;
    }
    
    public int getName()
    {
      return this.name_;
    }
    
    public Parser<JvmMethodSignature> getParserForType()
    {
      return PARSER;
    }
    
    public int getSerializedSize()
    {
      int i = this.memoizedSerializedSize;
      if (i != -1) {
        return i;
      }
      i = 0;
      if ((this.bitField0_ & 0x1) == 1) {
        i = 0 + CodedOutputStream.computeInt32Size(1, this.name_);
      }
      int j = i;
      if ((this.bitField0_ & 0x2) == 2) {
        j = i + CodedOutputStream.computeInt32Size(2, this.desc_);
      }
      i = j + this.unknownFields.size();
      this.memoizedSerializedSize = i;
      return i;
    }
    
    public boolean hasDesc()
    {
      boolean bool;
      if ((this.bitField0_ & 0x2) == 2) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasName()
    {
      int i = this.bitField0_;
      boolean bool = true;
      if ((i & 0x1) != 1) {
        bool = false;
      }
      return bool;
    }
    
    public final boolean isInitialized()
    {
      int i = this.memoizedIsInitialized;
      if (i == 1) {
        return true;
      }
      if (i == 0) {
        return false;
      }
      this.memoizedIsInitialized = ((byte)1);
      return true;
    }
    
    public Builder newBuilderForType()
    {
      return newBuilder();
    }
    
    public Builder toBuilder()
    {
      return newBuilder(this);
    }
    
    public void writeTo(CodedOutputStream paramCodedOutputStream)
      throws IOException
    {
      getSerializedSize();
      if ((this.bitField0_ & 0x1) == 1) {
        paramCodedOutputStream.writeInt32(1, this.name_);
      }
      if ((this.bitField0_ & 0x2) == 2) {
        paramCodedOutputStream.writeInt32(2, this.desc_);
      }
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Builder
      extends GeneratedMessageLite.Builder<JvmProtoBuf.JvmMethodSignature, Builder>
      implements JvmProtoBuf.JvmMethodSignatureOrBuilder
    {
      private int bitField0_;
      private int desc_;
      private int name_;
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public JvmProtoBuf.JvmMethodSignature build()
      {
        JvmProtoBuf.JvmMethodSignature localJvmMethodSignature = buildPartial();
        if (localJvmMethodSignature.isInitialized()) {
          return localJvmMethodSignature;
        }
        throw newUninitializedMessageException(localJvmMethodSignature);
      }
      
      public JvmProtoBuf.JvmMethodSignature buildPartial()
      {
        JvmProtoBuf.JvmMethodSignature localJvmMethodSignature = new JvmProtoBuf.JvmMethodSignature(this, null);
        int i = this.bitField0_;
        int j = 1;
        if ((i & 0x1) != 1) {
          j = 0;
        }
        JvmProtoBuf.JvmMethodSignature.access$2002(localJvmMethodSignature, this.name_);
        int k = j;
        if ((i & 0x2) == 2) {
          k = j | 0x2;
        }
        JvmProtoBuf.JvmMethodSignature.access$2102(localJvmMethodSignature, this.desc_);
        JvmProtoBuf.JvmMethodSignature.access$2202(localJvmMethodSignature, k);
        return localJvmMethodSignature;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public JvmProtoBuf.JvmMethodSignature getDefaultInstanceForType()
      {
        return JvmProtoBuf.JvmMethodSignature.getDefaultInstance();
      }
      
      public final boolean isInitialized()
      {
        return true;
      }
      
      public Builder mergeFrom(JvmProtoBuf.JvmMethodSignature paramJvmMethodSignature)
      {
        if (paramJvmMethodSignature == JvmProtoBuf.JvmMethodSignature.getDefaultInstance()) {
          return this;
        }
        if (paramJvmMethodSignature.hasName()) {
          setName(paramJvmMethodSignature.getName());
        }
        if (paramJvmMethodSignature.hasDesc()) {
          setDesc(paramJvmMethodSignature.getDesc());
        }
        setUnknownFields(getUnknownFields().concat(paramJvmMethodSignature.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 133	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 139 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 77	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature;)Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_1
        //   29: aload_3
        //   30: astore_2
        //   31: goto +15 -> 46
        //   34: astore_1
        //   35: aload_1
        //   36: invokevirtual 142	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   39: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature
        //   42: astore_2
        //   43: aload_1
        //   44: athrow
        //   45: astore_1
        //   46: aload_2
        //   47: ifnull +9 -> 56
        //   50: aload_0
        //   51: aload_2
        //   52: invokevirtual 77	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature;)Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature$Builder;
        //   55: pop
        //   56: aload_1
        //   57: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	58	0	this	Builder
        //   0	58	1	paramCodedInputStream	CodedInputStream
        //   0	58	2	paramExtensionRegistryLite	ExtensionRegistryLite
        //   1	29	3	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   2	16	28	finally
        //   35	43	28	finally
        //   2	16	34	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   43	45	45	finally
      }
      
      public Builder setDesc(int paramInt)
      {
        this.bitField0_ |= 0x2;
        this.desc_ = paramInt;
        return this;
      }
      
      public Builder setName(int paramInt)
      {
        this.bitField0_ |= 0x1;
        this.name_ = paramInt;
        return this;
      }
    }
  }
  
  public static final class JvmPropertySignature
    extends GeneratedMessageLite
    implements JvmProtoBuf.JvmPropertySignatureOrBuilder
  {
    public static Parser<JvmPropertySignature> PARSER = new AbstractParser()
    {
      public JvmProtoBuf.JvmPropertySignature parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new JvmProtoBuf.JvmPropertySignature(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final JvmPropertySignature defaultInstance;
    private int bitField0_;
    private JvmProtoBuf.JvmFieldSignature field_;
    private JvmProtoBuf.JvmMethodSignature getter_;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private JvmProtoBuf.JvmMethodSignature setter_;
    private JvmProtoBuf.JvmMethodSignature syntheticMethod_;
    private final ByteString unknownFields;
    
    static
    {
      JvmPropertySignature localJvmPropertySignature = new JvmPropertySignature(true);
      defaultInstance = localJvmPropertySignature;
      localJvmPropertySignature.initFields();
    }
    
    /* Error */
    private JvmPropertySignature(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 54	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: i2b
      //   7: putfield 56	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:memoizedIsInitialized	B
      //   10: aload_0
      //   11: iconst_m1
      //   12: putfield 58	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:memoizedSerializedSize	I
      //   15: aload_0
      //   16: invokespecial 47	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:initFields	()V
      //   19: invokestatic 64	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   22: astore_3
      //   23: aload_3
      //   24: iconst_1
      //   25: invokestatic 70	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   28: astore 4
      //   30: iconst_0
      //   31: istore 5
      //   33: iload 5
      //   35: ifne +446 -> 481
      //   38: aload_1
      //   39: invokevirtual 76	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   42: istore 6
      //   44: iload 6
      //   46: ifeq +366 -> 412
      //   49: aconst_null
      //   50: astore 7
      //   52: aconst_null
      //   53: astore 8
      //   55: aconst_null
      //   56: astore 9
      //   58: aconst_null
      //   59: astore 10
      //   61: iload 6
      //   63: bipush 10
      //   65: if_icmpeq +270 -> 335
      //   68: iload 6
      //   70: bipush 18
      //   72: if_icmpeq +186 -> 258
      //   75: iload 6
      //   77: bipush 26
      //   79: if_icmpeq +102 -> 181
      //   82: iload 6
      //   84: bipush 34
      //   86: if_icmpeq +19 -> 105
      //   89: aload_0
      //   90: aload_1
      //   91: aload 4
      //   93: aload_2
      //   94: iload 6
      //   96: invokevirtual 80	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   99: ifne -66 -> 33
      //   102: goto +310 -> 412
      //   105: aload_0
      //   106: getfield 82	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:bitField0_	I
      //   109: bipush 8
      //   111: iand
      //   112: bipush 8
      //   114: if_icmpne +12 -> 126
      //   117: aload_0
      //   118: getfield 84	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:setter_	Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature;
      //   121: invokevirtual 90	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature$Builder;
      //   124: astore 10
      //   126: aload_1
      //   127: getstatic 91	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   130: aload_2
      //   131: invokevirtual 95	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   134: checkcast 86	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature
      //   137: astore 7
      //   139: aload_0
      //   140: aload 7
      //   142: putfield 84	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:setter_	Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature;
      //   145: aload 10
      //   147: ifnull +20 -> 167
      //   150: aload 10
      //   152: aload 7
      //   154: invokevirtual 101	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature;)Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature$Builder;
      //   157: pop
      //   158: aload_0
      //   159: aload 10
      //   161: invokevirtual 105	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature;
      //   164: putfield 84	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:setter_	Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature;
      //   167: aload_0
      //   168: aload_0
      //   169: getfield 82	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:bitField0_	I
      //   172: bipush 8
      //   174: ior
      //   175: putfield 82	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:bitField0_	I
      //   178: goto -145 -> 33
      //   181: aload 7
      //   183: astore 10
      //   185: aload_0
      //   186: getfield 82	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:bitField0_	I
      //   189: iconst_4
      //   190: iand
      //   191: iconst_4
      //   192: if_icmpne +12 -> 204
      //   195: aload_0
      //   196: getfield 107	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:getter_	Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature;
      //   199: invokevirtual 90	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature$Builder;
      //   202: astore 10
      //   204: aload_1
      //   205: getstatic 91	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   208: aload_2
      //   209: invokevirtual 95	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   212: checkcast 86	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature
      //   215: astore 7
      //   217: aload_0
      //   218: aload 7
      //   220: putfield 107	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:getter_	Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature;
      //   223: aload 10
      //   225: ifnull +20 -> 245
      //   228: aload 10
      //   230: aload 7
      //   232: invokevirtual 101	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature;)Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature$Builder;
      //   235: pop
      //   236: aload_0
      //   237: aload 10
      //   239: invokevirtual 105	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature;
      //   242: putfield 107	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:getter_	Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature;
      //   245: aload_0
      //   246: aload_0
      //   247: getfield 82	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:bitField0_	I
      //   250: iconst_4
      //   251: ior
      //   252: putfield 82	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:bitField0_	I
      //   255: goto -222 -> 33
      //   258: aload 8
      //   260: astore 10
      //   262: aload_0
      //   263: getfield 82	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:bitField0_	I
      //   266: iconst_2
      //   267: iand
      //   268: iconst_2
      //   269: if_icmpne +12 -> 281
      //   272: aload_0
      //   273: getfield 109	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:syntheticMethod_	Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature;
      //   276: invokevirtual 90	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature$Builder;
      //   279: astore 10
      //   281: aload_1
      //   282: getstatic 91	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   285: aload_2
      //   286: invokevirtual 95	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   289: checkcast 86	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature
      //   292: astore 7
      //   294: aload_0
      //   295: aload 7
      //   297: putfield 109	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:syntheticMethod_	Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature;
      //   300: aload 10
      //   302: ifnull +20 -> 322
      //   305: aload 10
      //   307: aload 7
      //   309: invokevirtual 101	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature;)Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature$Builder;
      //   312: pop
      //   313: aload_0
      //   314: aload 10
      //   316: invokevirtual 105	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature;
      //   319: putfield 109	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:syntheticMethod_	Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmMethodSignature;
      //   322: aload_0
      //   323: aload_0
      //   324: getfield 82	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:bitField0_	I
      //   327: iconst_2
      //   328: ior
      //   329: putfield 82	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:bitField0_	I
      //   332: goto -299 -> 33
      //   335: aload 9
      //   337: astore 10
      //   339: aload_0
      //   340: getfield 82	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:bitField0_	I
      //   343: iconst_1
      //   344: iand
      //   345: iconst_1
      //   346: if_icmpne +12 -> 358
      //   349: aload_0
      //   350: getfield 111	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:field_	Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature;
      //   353: invokevirtual 116	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature$Builder;
      //   356: astore 10
      //   358: aload_1
      //   359: getstatic 117	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   362: aload_2
      //   363: invokevirtual 95	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   366: checkcast 113	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature
      //   369: astore 7
      //   371: aload_0
      //   372: aload 7
      //   374: putfield 111	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:field_	Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature;
      //   377: aload 10
      //   379: ifnull +20 -> 399
      //   382: aload 10
      //   384: aload 7
      //   386: invokevirtual 122	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature;)Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature$Builder;
      //   389: pop
      //   390: aload_0
      //   391: aload 10
      //   393: invokevirtual 125	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature;
      //   396: putfield 111	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:field_	Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmFieldSignature;
      //   399: aload_0
      //   400: aload_0
      //   401: getfield 82	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:bitField0_	I
      //   404: iconst_1
      //   405: ior
      //   406: putfield 82	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:bitField0_	I
      //   409: goto -376 -> 33
      //   412: iconst_1
      //   413: istore 5
      //   415: goto -382 -> 33
      //   418: astore_1
      //   419: goto +29 -> 448
      //   422: astore_2
      //   423: new 51	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   426: astore_1
      //   427: aload_1
      //   428: aload_2
      //   429: invokevirtual 129	java/io/IOException:getMessage	()Ljava/lang/String;
      //   432: invokespecial 132	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   435: aload_1
      //   436: aload_0
      //   437: invokevirtual 136	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   440: athrow
      //   441: astore_1
      //   442: aload_1
      //   443: aload_0
      //   444: invokevirtual 136	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   447: athrow
      //   448: aload 4
      //   450: invokevirtual 139	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   453: aload_0
      //   454: aload_3
      //   455: invokevirtual 145	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   458: putfield 147	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   461: goto +14 -> 475
      //   464: astore_1
      //   465: aload_0
      //   466: aload_3
      //   467: invokevirtual 145	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   470: putfield 147	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   473: aload_1
      //   474: athrow
      //   475: aload_0
      //   476: invokevirtual 150	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:makeExtensionsImmutable	()V
      //   479: aload_1
      //   480: athrow
      //   481: aload 4
      //   483: invokevirtual 139	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   486: aload_0
      //   487: aload_3
      //   488: invokevirtual 145	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   491: putfield 147	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   494: goto +14 -> 508
      //   497: astore_1
      //   498: aload_0
      //   499: aload_3
      //   500: invokevirtual 145	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   503: putfield 147	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   506: aload_1
      //   507: athrow
      //   508: aload_0
      //   509: invokevirtual 150	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:makeExtensionsImmutable	()V
      //   512: return
      //   513: astore_2
      //   514: goto -61 -> 453
      //   517: astore_1
      //   518: goto -32 -> 486
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	521	0	this	JvmPropertySignature
      //   0	521	1	paramCodedInputStream	CodedInputStream
      //   0	521	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   22	478	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   28	454	4	localCodedOutputStream	CodedOutputStream
      //   31	383	5	i	int
      //   42	53	6	j	int
      //   50	335	7	localObject1	Object
      //   53	206	8	localObject2	Object
      //   56	280	9	localObject3	Object
      //   59	333	10	localObject4	Object
      // Exception table:
      //   from	to	target	type
      //   38	44	418	finally
      //   89	102	418	finally
      //   105	126	418	finally
      //   126	145	418	finally
      //   150	167	418	finally
      //   167	178	418	finally
      //   185	204	418	finally
      //   204	223	418	finally
      //   228	245	418	finally
      //   245	255	418	finally
      //   262	281	418	finally
      //   281	300	418	finally
      //   305	322	418	finally
      //   322	332	418	finally
      //   339	358	418	finally
      //   358	377	418	finally
      //   382	399	418	finally
      //   399	409	418	finally
      //   423	441	418	finally
      //   442	448	418	finally
      //   38	44	422	java/io/IOException
      //   89	102	422	java/io/IOException
      //   105	126	422	java/io/IOException
      //   126	145	422	java/io/IOException
      //   150	167	422	java/io/IOException
      //   167	178	422	java/io/IOException
      //   185	204	422	java/io/IOException
      //   204	223	422	java/io/IOException
      //   228	245	422	java/io/IOException
      //   245	255	422	java/io/IOException
      //   262	281	422	java/io/IOException
      //   281	300	422	java/io/IOException
      //   305	322	422	java/io/IOException
      //   322	332	422	java/io/IOException
      //   339	358	422	java/io/IOException
      //   358	377	422	java/io/IOException
      //   382	399	422	java/io/IOException
      //   399	409	422	java/io/IOException
      //   38	44	441	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   89	102	441	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   105	126	441	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   126	145	441	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   150	167	441	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   167	178	441	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   185	204	441	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   204	223	441	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   228	245	441	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   245	255	441	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   262	281	441	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   281	300	441	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   305	322	441	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   322	332	441	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   339	358	441	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   358	377	441	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   382	399	441	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   399	409	441	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   448	453	464	finally
      //   481	486	497	finally
      //   448	453	513	java/io/IOException
      //   481	486	517	java/io/IOException
    }
    
    private JvmPropertySignature(GeneratedMessageLite.Builder paramBuilder)
    {
      super();
      this.unknownFields = paramBuilder.getUnknownFields();
    }
    
    private JvmPropertySignature(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static JvmPropertySignature getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.field_ = JvmProtoBuf.JvmFieldSignature.getDefaultInstance();
      this.syntheticMethod_ = JvmProtoBuf.JvmMethodSignature.getDefaultInstance();
      this.getter_ = JvmProtoBuf.JvmMethodSignature.getDefaultInstance();
      this.setter_ = JvmProtoBuf.JvmMethodSignature.getDefaultInstance();
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$3200();
    }
    
    public static Builder newBuilder(JvmPropertySignature paramJvmPropertySignature)
    {
      return newBuilder().mergeFrom(paramJvmPropertySignature);
    }
    
    public JvmPropertySignature getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public JvmProtoBuf.JvmFieldSignature getField()
    {
      return this.field_;
    }
    
    public JvmProtoBuf.JvmMethodSignature getGetter()
    {
      return this.getter_;
    }
    
    public Parser<JvmPropertySignature> getParserForType()
    {
      return PARSER;
    }
    
    public int getSerializedSize()
    {
      int i = this.memoizedSerializedSize;
      if (i != -1) {
        return i;
      }
      int j = 0;
      if ((this.bitField0_ & 0x1) == 1) {
        j = 0 + CodedOutputStream.computeMessageSize(1, this.field_);
      }
      i = j;
      if ((this.bitField0_ & 0x2) == 2) {
        i = j + CodedOutputStream.computeMessageSize(2, this.syntheticMethod_);
      }
      j = i;
      if ((this.bitField0_ & 0x4) == 4) {
        j = i + CodedOutputStream.computeMessageSize(3, this.getter_);
      }
      i = j;
      if ((this.bitField0_ & 0x8) == 8) {
        i = j + CodedOutputStream.computeMessageSize(4, this.setter_);
      }
      i += this.unknownFields.size();
      this.memoizedSerializedSize = i;
      return i;
    }
    
    public JvmProtoBuf.JvmMethodSignature getSetter()
    {
      return this.setter_;
    }
    
    public JvmProtoBuf.JvmMethodSignature getSyntheticMethod()
    {
      return this.syntheticMethod_;
    }
    
    public boolean hasField()
    {
      int i = this.bitField0_;
      boolean bool = true;
      if ((i & 0x1) != 1) {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasGetter()
    {
      boolean bool;
      if ((this.bitField0_ & 0x4) == 4) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasSetter()
    {
      boolean bool;
      if ((this.bitField0_ & 0x8) == 8) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasSyntheticMethod()
    {
      boolean bool;
      if ((this.bitField0_ & 0x2) == 2) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public final boolean isInitialized()
    {
      int i = this.memoizedIsInitialized;
      if (i == 1) {
        return true;
      }
      if (i == 0) {
        return false;
      }
      this.memoizedIsInitialized = ((byte)1);
      return true;
    }
    
    public Builder newBuilderForType()
    {
      return newBuilder();
    }
    
    public Builder toBuilder()
    {
      return newBuilder(this);
    }
    
    public void writeTo(CodedOutputStream paramCodedOutputStream)
      throws IOException
    {
      getSerializedSize();
      if ((this.bitField0_ & 0x1) == 1) {
        paramCodedOutputStream.writeMessage(1, this.field_);
      }
      if ((this.bitField0_ & 0x2) == 2) {
        paramCodedOutputStream.writeMessage(2, this.syntheticMethod_);
      }
      if ((this.bitField0_ & 0x4) == 4) {
        paramCodedOutputStream.writeMessage(3, this.getter_);
      }
      if ((this.bitField0_ & 0x8) == 8) {
        paramCodedOutputStream.writeMessage(4, this.setter_);
      }
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Builder
      extends GeneratedMessageLite.Builder<JvmProtoBuf.JvmPropertySignature, Builder>
      implements JvmProtoBuf.JvmPropertySignatureOrBuilder
    {
      private int bitField0_;
      private JvmProtoBuf.JvmFieldSignature field_ = JvmProtoBuf.JvmFieldSignature.getDefaultInstance();
      private JvmProtoBuf.JvmMethodSignature getter_ = JvmProtoBuf.JvmMethodSignature.getDefaultInstance();
      private JvmProtoBuf.JvmMethodSignature setter_ = JvmProtoBuf.JvmMethodSignature.getDefaultInstance();
      private JvmProtoBuf.JvmMethodSignature syntheticMethod_ = JvmProtoBuf.JvmMethodSignature.getDefaultInstance();
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public JvmProtoBuf.JvmPropertySignature build()
      {
        JvmProtoBuf.JvmPropertySignature localJvmPropertySignature = buildPartial();
        if (localJvmPropertySignature.isInitialized()) {
          return localJvmPropertySignature;
        }
        throw newUninitializedMessageException(localJvmPropertySignature);
      }
      
      public JvmProtoBuf.JvmPropertySignature buildPartial()
      {
        JvmProtoBuf.JvmPropertySignature localJvmPropertySignature = new JvmProtoBuf.JvmPropertySignature(this, null);
        int i = this.bitField0_;
        int j = 1;
        if ((i & 0x1) != 1) {
          j = 0;
        }
        JvmProtoBuf.JvmPropertySignature.access$3402(localJvmPropertySignature, this.field_);
        int k = j;
        if ((i & 0x2) == 2) {
          k = j | 0x2;
        }
        JvmProtoBuf.JvmPropertySignature.access$3502(localJvmPropertySignature, this.syntheticMethod_);
        j = k;
        if ((i & 0x4) == 4) {
          j = k | 0x4;
        }
        JvmProtoBuf.JvmPropertySignature.access$3602(localJvmPropertySignature, this.getter_);
        k = j;
        if ((i & 0x8) == 8) {
          k = j | 0x8;
        }
        JvmProtoBuf.JvmPropertySignature.access$3702(localJvmPropertySignature, this.setter_);
        JvmProtoBuf.JvmPropertySignature.access$3802(localJvmPropertySignature, k);
        return localJvmPropertySignature;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public JvmProtoBuf.JvmPropertySignature getDefaultInstanceForType()
      {
        return JvmProtoBuf.JvmPropertySignature.getDefaultInstance();
      }
      
      public final boolean isInitialized()
      {
        return true;
      }
      
      public Builder mergeField(JvmProtoBuf.JvmFieldSignature paramJvmFieldSignature)
      {
        if (((this.bitField0_ & 0x1) == 1) && (this.field_ != JvmProtoBuf.JvmFieldSignature.getDefaultInstance())) {
          this.field_ = JvmProtoBuf.JvmFieldSignature.newBuilder(this.field_).mergeFrom(paramJvmFieldSignature).buildPartial();
        } else {
          this.field_ = paramJvmFieldSignature;
        }
        this.bitField0_ |= 0x1;
        return this;
      }
      
      public Builder mergeFrom(JvmProtoBuf.JvmPropertySignature paramJvmPropertySignature)
      {
        if (paramJvmPropertySignature == JvmProtoBuf.JvmPropertySignature.getDefaultInstance()) {
          return this;
        }
        if (paramJvmPropertySignature.hasField()) {
          mergeField(paramJvmPropertySignature.getField());
        }
        if (paramJvmPropertySignature.hasSyntheticMethod()) {
          mergeSyntheticMethod(paramJvmPropertySignature.getSyntheticMethod());
        }
        if (paramJvmPropertySignature.hasGetter()) {
          mergeGetter(paramJvmPropertySignature.getGetter());
        }
        if (paramJvmPropertySignature.hasSetter()) {
          mergeSetter(paramJvmPropertySignature.getSetter());
        }
        setUnknownFields(getUnknownFields().concat(paramJvmPropertySignature.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 187	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 193 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 104	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature;)Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_1
        //   29: aload_3
        //   30: astore_2
        //   31: goto +15 -> 46
        //   34: astore_1
        //   35: aload_1
        //   36: invokevirtual 196	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   39: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature
        //   42: astore_2
        //   43: aload_1
        //   44: athrow
        //   45: astore_1
        //   46: aload_2
        //   47: ifnull +9 -> 56
        //   50: aload_0
        //   51: aload_2
        //   52: invokevirtual 104	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature;)Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature$Builder;
        //   55: pop
        //   56: aload_1
        //   57: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	58	0	this	Builder
        //   0	58	1	paramCodedInputStream	CodedInputStream
        //   0	58	2	paramExtensionRegistryLite	ExtensionRegistryLite
        //   1	29	3	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   2	16	28	finally
        //   35	43	28	finally
        //   2	16	34	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   43	45	45	finally
      }
      
      public Builder mergeGetter(JvmProtoBuf.JvmMethodSignature paramJvmMethodSignature)
      {
        if (((this.bitField0_ & 0x4) == 4) && (this.getter_ != JvmProtoBuf.JvmMethodSignature.getDefaultInstance())) {
          this.getter_ = JvmProtoBuf.JvmMethodSignature.newBuilder(this.getter_).mergeFrom(paramJvmMethodSignature).buildPartial();
        } else {
          this.getter_ = paramJvmMethodSignature;
        }
        this.bitField0_ |= 0x4;
        return this;
      }
      
      public Builder mergeSetter(JvmProtoBuf.JvmMethodSignature paramJvmMethodSignature)
      {
        if (((this.bitField0_ & 0x8) == 8) && (this.setter_ != JvmProtoBuf.JvmMethodSignature.getDefaultInstance())) {
          this.setter_ = JvmProtoBuf.JvmMethodSignature.newBuilder(this.setter_).mergeFrom(paramJvmMethodSignature).buildPartial();
        } else {
          this.setter_ = paramJvmMethodSignature;
        }
        this.bitField0_ |= 0x8;
        return this;
      }
      
      public Builder mergeSyntheticMethod(JvmProtoBuf.JvmMethodSignature paramJvmMethodSignature)
      {
        if (((this.bitField0_ & 0x2) == 2) && (this.syntheticMethod_ != JvmProtoBuf.JvmMethodSignature.getDefaultInstance())) {
          this.syntheticMethod_ = JvmProtoBuf.JvmMethodSignature.newBuilder(this.syntheticMethod_).mergeFrom(paramJvmMethodSignature).buildPartial();
        } else {
          this.syntheticMethod_ = paramJvmMethodSignature;
        }
        this.bitField0_ |= 0x2;
        return this;
      }
    }
  }
  
  public static final class StringTableTypes
    extends GeneratedMessageLite
    implements JvmProtoBuf.StringTableTypesOrBuilder
  {
    public static Parser<StringTableTypes> PARSER = new AbstractParser()
    {
      public JvmProtoBuf.StringTableTypes parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new JvmProtoBuf.StringTableTypes(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final StringTableTypes defaultInstance;
    private int localNameMemoizedSerializedSize = -1;
    private List<Integer> localName_;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private List<Record> record_;
    private final ByteString unknownFields;
    
    static
    {
      StringTableTypes localStringTableTypes = new StringTableTypes(true);
      defaultInstance = localStringTableTypes;
      localStringTableTypes.initFields();
    }
    
    /* Error */
    private StringTableTypes(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 65	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: putfield 67	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:localNameMemoizedSerializedSize	I
      //   9: aload_0
      //   10: iconst_m1
      //   11: i2b
      //   12: putfield 69	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:memoizedIsInitialized	B
      //   15: aload_0
      //   16: iconst_m1
      //   17: putfield 71	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:memoizedSerializedSize	I
      //   20: aload_0
      //   21: invokespecial 58	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:initFields	()V
      //   24: invokestatic 77	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   27: astore_3
      //   28: aload_3
      //   29: iconst_1
      //   30: invokestatic 83	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   33: astore 4
      //   35: iconst_0
      //   36: istore 5
      //   38: iconst_0
      //   39: istore 6
      //   41: iload 5
      //   43: ifne +602 -> 645
      //   46: iload 6
      //   48: istore 7
      //   50: iload 6
      //   52: istore 8
      //   54: iload 6
      //   56: istore 9
      //   58: aload_1
      //   59: invokevirtual 89	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   62: istore 10
      //   64: iload 10
      //   66: ifeq +456 -> 522
      //   69: iload 10
      //   71: bipush 10
      //   73: if_icmpeq +342 -> 415
      //   76: iload 10
      //   78: bipush 40
      //   80: if_icmpeq +229 -> 309
      //   83: iload 10
      //   85: bipush 42
      //   87: if_icmpeq +31 -> 118
      //   90: iload 6
      //   92: istore 7
      //   94: iload 6
      //   96: istore 8
      //   98: iload 6
      //   100: istore 9
      //   102: aload_0
      //   103: aload_1
      //   104: aload 4
      //   106: aload_2
      //   107: iload 10
      //   109: invokevirtual 93	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   112: ifne -71 -> 41
      //   115: goto +407 -> 522
      //   118: iload 6
      //   120: istore 7
      //   122: iload 6
      //   124: istore 8
      //   126: iload 6
      //   128: istore 9
      //   130: aload_1
      //   131: aload_1
      //   132: invokevirtual 96	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readRawVarint32	()I
      //   135: invokevirtual 100	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:pushLimit	(I)I
      //   138: istore 11
      //   140: iload 6
      //   142: istore 10
      //   144: iload 6
      //   146: iconst_2
      //   147: iand
      //   148: iconst_2
      //   149: if_icmpeq +84 -> 233
      //   152: iload 6
      //   154: istore 10
      //   156: iload 6
      //   158: istore 7
      //   160: iload 6
      //   162: istore 8
      //   164: iload 6
      //   166: istore 9
      //   168: aload_1
      //   169: invokevirtual 103	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
      //   172: ifle +61 -> 233
      //   175: iload 6
      //   177: istore 7
      //   179: iload 6
      //   181: istore 8
      //   183: iload 6
      //   185: istore 9
      //   187: new 105	java/util/ArrayList
      //   190: astore 12
      //   192: iload 6
      //   194: istore 7
      //   196: iload 6
      //   198: istore 8
      //   200: iload 6
      //   202: istore 9
      //   204: aload 12
      //   206: invokespecial 106	java/util/ArrayList:<init>	()V
      //   209: iload 6
      //   211: istore 7
      //   213: iload 6
      //   215: istore 8
      //   217: iload 6
      //   219: istore 9
      //   221: aload_0
      //   222: aload 12
      //   224: putfield 108	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:localName_	Ljava/util/List;
      //   227: iload 6
      //   229: iconst_2
      //   230: ior
      //   231: istore 10
      //   233: iload 10
      //   235: istore 7
      //   237: iload 10
      //   239: istore 8
      //   241: iload 10
      //   243: istore 9
      //   245: aload_1
      //   246: invokevirtual 103	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
      //   249: ifle +35 -> 284
      //   252: iload 10
      //   254: istore 7
      //   256: iload 10
      //   258: istore 8
      //   260: iload 10
      //   262: istore 9
      //   264: aload_0
      //   265: getfield 108	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:localName_	Ljava/util/List;
      //   268: aload_1
      //   269: invokevirtual 111	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   272: invokestatic 117	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   275: invokeinterface 123 2 0
      //   280: pop
      //   281: goto -48 -> 233
      //   284: iload 10
      //   286: istore 7
      //   288: iload 10
      //   290: istore 8
      //   292: iload 10
      //   294: istore 9
      //   296: aload_1
      //   297: iload 11
      //   299: invokevirtual 127	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:popLimit	(I)V
      //   302: iload 10
      //   304: istore 6
      //   306: goto -265 -> 41
      //   309: iload 6
      //   311: istore 10
      //   313: iload 6
      //   315: iconst_2
      //   316: iand
      //   317: iconst_2
      //   318: if_icmpeq +61 -> 379
      //   321: iload 6
      //   323: istore 7
      //   325: iload 6
      //   327: istore 8
      //   329: iload 6
      //   331: istore 9
      //   333: new 105	java/util/ArrayList
      //   336: astore 12
      //   338: iload 6
      //   340: istore 7
      //   342: iload 6
      //   344: istore 8
      //   346: iload 6
      //   348: istore 9
      //   350: aload 12
      //   352: invokespecial 106	java/util/ArrayList:<init>	()V
      //   355: iload 6
      //   357: istore 7
      //   359: iload 6
      //   361: istore 8
      //   363: iload 6
      //   365: istore 9
      //   367: aload_0
      //   368: aload 12
      //   370: putfield 108	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:localName_	Ljava/util/List;
      //   373: iload 6
      //   375: iconst_2
      //   376: ior
      //   377: istore 10
      //   379: iload 10
      //   381: istore 7
      //   383: iload 10
      //   385: istore 8
      //   387: iload 10
      //   389: istore 9
      //   391: aload_0
      //   392: getfield 108	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:localName_	Ljava/util/List;
      //   395: aload_1
      //   396: invokevirtual 111	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   399: invokestatic 117	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   402: invokeinterface 123 2 0
      //   407: pop
      //   408: iload 10
      //   410: istore 6
      //   412: goto -371 -> 41
      //   415: iload 6
      //   417: istore 10
      //   419: iload 6
      //   421: iconst_1
      //   422: iand
      //   423: iconst_1
      //   424: if_icmpeq +61 -> 485
      //   427: iload 6
      //   429: istore 7
      //   431: iload 6
      //   433: istore 8
      //   435: iload 6
      //   437: istore 9
      //   439: new 105	java/util/ArrayList
      //   442: astore 12
      //   444: iload 6
      //   446: istore 7
      //   448: iload 6
      //   450: istore 8
      //   452: iload 6
      //   454: istore 9
      //   456: aload 12
      //   458: invokespecial 106	java/util/ArrayList:<init>	()V
      //   461: iload 6
      //   463: istore 7
      //   465: iload 6
      //   467: istore 8
      //   469: iload 6
      //   471: istore 9
      //   473: aload_0
      //   474: aload 12
      //   476: putfield 129	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:record_	Ljava/util/List;
      //   479: iload 6
      //   481: iconst_1
      //   482: ior
      //   483: istore 10
      //   485: iload 10
      //   487: istore 7
      //   489: iload 10
      //   491: istore 8
      //   493: iload 10
      //   495: istore 9
      //   497: aload_0
      //   498: getfield 129	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:record_	Ljava/util/List;
      //   501: aload_1
      //   502: getstatic 130	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   505: aload_2
      //   506: invokevirtual 134	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   509: invokeinterface 123 2 0
      //   514: pop
      //   515: iload 10
      //   517: istore 6
      //   519: goto -478 -> 41
      //   522: iconst_1
      //   523: istore 5
      //   525: goto -484 -> 41
      //   528: astore_1
      //   529: goto +45 -> 574
      //   532: astore_1
      //   533: iload 8
      //   535: istore 7
      //   537: new 62	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   540: astore_2
      //   541: iload 8
      //   543: istore 7
      //   545: aload_2
      //   546: aload_1
      //   547: invokevirtual 138	java/io/IOException:getMessage	()Ljava/lang/String;
      //   550: invokespecial 141	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   553: iload 8
      //   555: istore 7
      //   557: aload_2
      //   558: aload_0
      //   559: invokevirtual 145	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   562: athrow
      //   563: astore_1
      //   564: iload 9
      //   566: istore 7
      //   568: aload_1
      //   569: aload_0
      //   570: invokevirtual 145	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   573: athrow
      //   574: iload 7
      //   576: iconst_1
      //   577: iand
      //   578: iconst_1
      //   579: if_icmpne +14 -> 593
      //   582: aload_0
      //   583: aload_0
      //   584: getfield 129	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:record_	Ljava/util/List;
      //   587: invokestatic 151	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   590: putfield 129	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:record_	Ljava/util/List;
      //   593: iload 7
      //   595: iconst_2
      //   596: iand
      //   597: iconst_2
      //   598: if_icmpne +14 -> 612
      //   601: aload_0
      //   602: aload_0
      //   603: getfield 108	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:localName_	Ljava/util/List;
      //   606: invokestatic 151	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   609: putfield 108	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:localName_	Ljava/util/List;
      //   612: aload 4
      //   614: invokevirtual 154	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   617: aload_0
      //   618: aload_3
      //   619: invokevirtual 160	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   622: putfield 162	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   625: goto +14 -> 639
      //   628: astore_1
      //   629: aload_0
      //   630: aload_3
      //   631: invokevirtual 160	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   634: putfield 162	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   637: aload_1
      //   638: athrow
      //   639: aload_0
      //   640: invokevirtual 165	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:makeExtensionsImmutable	()V
      //   643: aload_1
      //   644: athrow
      //   645: iload 6
      //   647: iconst_1
      //   648: iand
      //   649: iconst_1
      //   650: if_icmpne +14 -> 664
      //   653: aload_0
      //   654: aload_0
      //   655: getfield 129	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:record_	Ljava/util/List;
      //   658: invokestatic 151	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   661: putfield 129	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:record_	Ljava/util/List;
      //   664: iload 6
      //   666: iconst_2
      //   667: iand
      //   668: iconst_2
      //   669: if_icmpne +14 -> 683
      //   672: aload_0
      //   673: aload_0
      //   674: getfield 108	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:localName_	Ljava/util/List;
      //   677: invokestatic 151	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   680: putfield 108	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:localName_	Ljava/util/List;
      //   683: aload 4
      //   685: invokevirtual 154	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   688: aload_0
      //   689: aload_3
      //   690: invokevirtual 160	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   693: putfield 162	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   696: goto +14 -> 710
      //   699: astore_1
      //   700: aload_0
      //   701: aload_3
      //   702: invokevirtual 160	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   705: putfield 162	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   708: aload_1
      //   709: athrow
      //   710: aload_0
      //   711: invokevirtual 165	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:makeExtensionsImmutable	()V
      //   714: return
      //   715: astore_2
      //   716: goto -99 -> 617
      //   719: astore_1
      //   720: goto -32 -> 688
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	723	0	this	StringTableTypes
      //   0	723	1	paramCodedInputStream	CodedInputStream
      //   0	723	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   27	675	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   33	651	4	localCodedOutputStream	CodedOutputStream
      //   36	488	5	i	int
      //   39	629	6	j	int
      //   48	549	7	k	int
      //   52	502	8	m	int
      //   56	509	9	n	int
      //   62	454	10	i1	int
      //   138	160	11	i2	int
      //   190	285	12	localArrayList	ArrayList
      // Exception table:
      //   from	to	target	type
      //   58	64	528	finally
      //   102	115	528	finally
      //   130	140	528	finally
      //   168	175	528	finally
      //   187	192	528	finally
      //   204	209	528	finally
      //   221	227	528	finally
      //   245	252	528	finally
      //   264	281	528	finally
      //   296	302	528	finally
      //   333	338	528	finally
      //   350	355	528	finally
      //   367	373	528	finally
      //   391	408	528	finally
      //   439	444	528	finally
      //   456	461	528	finally
      //   473	479	528	finally
      //   497	515	528	finally
      //   537	541	528	finally
      //   545	553	528	finally
      //   557	563	528	finally
      //   568	574	528	finally
      //   58	64	532	java/io/IOException
      //   102	115	532	java/io/IOException
      //   130	140	532	java/io/IOException
      //   168	175	532	java/io/IOException
      //   187	192	532	java/io/IOException
      //   204	209	532	java/io/IOException
      //   221	227	532	java/io/IOException
      //   245	252	532	java/io/IOException
      //   264	281	532	java/io/IOException
      //   296	302	532	java/io/IOException
      //   333	338	532	java/io/IOException
      //   350	355	532	java/io/IOException
      //   367	373	532	java/io/IOException
      //   391	408	532	java/io/IOException
      //   439	444	532	java/io/IOException
      //   456	461	532	java/io/IOException
      //   473	479	532	java/io/IOException
      //   497	515	532	java/io/IOException
      //   58	64	563	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   102	115	563	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   130	140	563	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   168	175	563	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   187	192	563	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   204	209	563	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   221	227	563	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   245	252	563	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   264	281	563	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   296	302	563	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   333	338	563	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   350	355	563	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   367	373	563	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   391	408	563	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   439	444	563	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   456	461	563	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   473	479	563	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   497	515	563	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   612	617	628	finally
      //   683	688	699	finally
      //   612	617	715	java/io/IOException
      //   683	688	719	java/io/IOException
    }
    
    private StringTableTypes(GeneratedMessageLite.Builder paramBuilder)
    {
      super();
      this.unknownFields = paramBuilder.getUnknownFields();
    }
    
    private StringTableTypes(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static StringTableTypes getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.record_ = Collections.emptyList();
      this.localName_ = Collections.emptyList();
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$1200();
    }
    
    public static Builder newBuilder(StringTableTypes paramStringTableTypes)
    {
      return newBuilder().mergeFrom(paramStringTableTypes);
    }
    
    public static StringTableTypes parseDelimitedFrom(InputStream paramInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws IOException
    {
      return (StringTableTypes)PARSER.parseDelimitedFrom(paramInputStream, paramExtensionRegistryLite);
    }
    
    public StringTableTypes getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public List<Integer> getLocalNameList()
    {
      return this.localName_;
    }
    
    public Parser<StringTableTypes> getParserForType()
    {
      return PARSER;
    }
    
    public List<Record> getRecordList()
    {
      return this.record_;
    }
    
    public int getSerializedSize()
    {
      int i = this.memoizedSerializedSize;
      if (i != -1) {
        return i;
      }
      int j = 0;
      int k = 0;
      i = k;
      while (k < this.record_.size())
      {
        i += CodedOutputStream.computeMessageSize(1, (MessageLite)this.record_.get(k));
        k++;
      }
      k = 0;
      while (j < this.localName_.size())
      {
        k += CodedOutputStream.computeInt32SizeNoTag(((Integer)this.localName_.get(j)).intValue());
        j++;
      }
      j = i + k;
      i = j;
      if (!getLocalNameList().isEmpty()) {
        i = j + 1 + CodedOutputStream.computeInt32SizeNoTag(k);
      }
      this.localNameMemoizedSerializedSize = k;
      i += this.unknownFields.size();
      this.memoizedSerializedSize = i;
      return i;
    }
    
    public final boolean isInitialized()
    {
      int i = this.memoizedIsInitialized;
      if (i == 1) {
        return true;
      }
      if (i == 0) {
        return false;
      }
      this.memoizedIsInitialized = ((byte)1);
      return true;
    }
    
    public Builder newBuilderForType()
    {
      return newBuilder();
    }
    
    public Builder toBuilder()
    {
      return newBuilder(this);
    }
    
    public void writeTo(CodedOutputStream paramCodedOutputStream)
      throws IOException
    {
      getSerializedSize();
      int i = 0;
      for (int j = 0; j < this.record_.size(); j++) {
        paramCodedOutputStream.writeMessage(1, (MessageLite)this.record_.get(j));
      }
      j = i;
      if (getLocalNameList().size() > 0)
      {
        paramCodedOutputStream.writeRawVarint32(42);
        paramCodedOutputStream.writeRawVarint32(this.localNameMemoizedSerializedSize);
      }
      for (j = i; j < this.localName_.size(); j++) {
        paramCodedOutputStream.writeInt32NoTag(((Integer)this.localName_.get(j)).intValue());
      }
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Builder
      extends GeneratedMessageLite.Builder<JvmProtoBuf.StringTableTypes, Builder>
      implements JvmProtoBuf.StringTableTypesOrBuilder
    {
      private int bitField0_;
      private List<Integer> localName_ = Collections.emptyList();
      private List<JvmProtoBuf.StringTableTypes.Record> record_ = Collections.emptyList();
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void ensureLocalNameIsMutable()
      {
        if ((this.bitField0_ & 0x2) != 2)
        {
          this.localName_ = new ArrayList(this.localName_);
          this.bitField0_ |= 0x2;
        }
      }
      
      private void ensureRecordIsMutable()
      {
        if ((this.bitField0_ & 0x1) != 1)
        {
          this.record_ = new ArrayList(this.record_);
          this.bitField0_ |= 0x1;
        }
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public JvmProtoBuf.StringTableTypes build()
      {
        JvmProtoBuf.StringTableTypes localStringTableTypes = buildPartial();
        if (localStringTableTypes.isInitialized()) {
          return localStringTableTypes;
        }
        throw newUninitializedMessageException(localStringTableTypes);
      }
      
      public JvmProtoBuf.StringTableTypes buildPartial()
      {
        JvmProtoBuf.StringTableTypes localStringTableTypes = new JvmProtoBuf.StringTableTypes(this, null);
        if ((this.bitField0_ & 0x1) == 1)
        {
          this.record_ = Collections.unmodifiableList(this.record_);
          this.bitField0_ &= 0xFFFFFFFE;
        }
        JvmProtoBuf.StringTableTypes.access$1402(localStringTableTypes, this.record_);
        if ((this.bitField0_ & 0x2) == 2)
        {
          this.localName_ = Collections.unmodifiableList(this.localName_);
          this.bitField0_ &= 0xFFFFFFFD;
        }
        JvmProtoBuf.StringTableTypes.access$1502(localStringTableTypes, this.localName_);
        return localStringTableTypes;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public JvmProtoBuf.StringTableTypes getDefaultInstanceForType()
      {
        return JvmProtoBuf.StringTableTypes.getDefaultInstance();
      }
      
      public final boolean isInitialized()
      {
        return true;
      }
      
      public Builder mergeFrom(JvmProtoBuf.StringTableTypes paramStringTableTypes)
      {
        if (paramStringTableTypes == JvmProtoBuf.StringTableTypes.getDefaultInstance()) {
          return this;
        }
        if (!paramStringTableTypes.record_.isEmpty()) {
          if (this.record_.isEmpty())
          {
            this.record_ = paramStringTableTypes.record_;
            this.bitField0_ &= 0xFFFFFFFE;
          }
          else
          {
            ensureRecordIsMutable();
            this.record_.addAll(paramStringTableTypes.record_);
          }
        }
        if (!paramStringTableTypes.localName_.isEmpty()) {
          if (this.localName_.isEmpty())
          {
            this.localName_ = paramStringTableTypes.localName_;
            this.bitField0_ &= 0xFFFFFFFD;
          }
          else
          {
            ensureLocalNameIsMutable();
            this.localName_.addAll(paramStringTableTypes.localName_);
          }
        }
        setUnknownFields(getUnknownFields().concat(paramStringTableTypes.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 150	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 156 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 94	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes;)Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_1
        //   29: aload_3
        //   30: astore_2
        //   31: aload_1
        //   32: astore_3
        //   33: goto +17 -> 50
        //   36: astore_2
        //   37: aload_2
        //   38: invokevirtual 159	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   41: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes
        //   44: astore_1
        //   45: aload_2
        //   46: athrow
        //   47: astore_3
        //   48: aload_1
        //   49: astore_2
        //   50: aload_2
        //   51: ifnull +9 -> 60
        //   54: aload_0
        //   55: aload_2
        //   56: invokevirtual 94	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes;)Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Builder;
        //   59: pop
        //   60: aload_3
        //   61: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	62	0	this	Builder
        //   0	62	1	paramCodedInputStream	CodedInputStream
        //   0	62	2	paramExtensionRegistryLite	ExtensionRegistryLite
        //   1	32	3	localCodedInputStream	CodedInputStream
        //   47	14	3	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   2	16	28	finally
        //   37	45	28	finally
        //   2	16	36	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   45	47	47	finally
      }
    }
    
    public static final class Record
      extends GeneratedMessageLite
      implements JvmProtoBuf.StringTableTypes.RecordOrBuilder
    {
      public static Parser<Record> PARSER = new AbstractParser()
      {
        public JvmProtoBuf.StringTableTypes.Record parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
          throws InvalidProtocolBufferException
        {
          return new JvmProtoBuf.StringTableTypes.Record(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
        }
      };
      private static final Record defaultInstance;
      private int bitField0_;
      private byte memoizedIsInitialized = (byte)-1;
      private int memoizedSerializedSize = -1;
      private Operation operation_;
      private int predefinedIndex_;
      private int range_;
      private int replaceCharMemoizedSerializedSize = -1;
      private List<Integer> replaceChar_;
      private Object string_;
      private int substringIndexMemoizedSerializedSize = -1;
      private List<Integer> substringIndex_;
      private final ByteString unknownFields;
      
      static
      {
        Record localRecord = new Record(true);
        defaultInstance = localRecord;
        localRecord.initFields();
      }
      
      /* Error */
      private Record(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        // Byte code:
        //   0: aload_0
        //   1: invokespecial 68	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite:<init>	()V
        //   4: aload_0
        //   5: iconst_m1
        //   6: putfield 70	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:substringIndexMemoizedSerializedSize	I
        //   9: aload_0
        //   10: iconst_m1
        //   11: putfield 72	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:replaceCharMemoizedSerializedSize	I
        //   14: aload_0
        //   15: iconst_m1
        //   16: i2b
        //   17: putfield 74	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:memoizedIsInitialized	B
        //   20: aload_0
        //   21: iconst_m1
        //   22: putfield 76	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:memoizedSerializedSize	I
        //   25: aload_0
        //   26: invokespecial 61	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:initFields	()V
        //   29: invokestatic 82	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
        //   32: astore_3
        //   33: aload_3
        //   34: iconst_1
        //   35: invokestatic 88	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
        //   38: astore 4
        //   40: iconst_0
        //   41: istore 5
        //   43: iconst_0
        //   44: istore 6
        //   46: iload 5
        //   48: ifne +1121 -> 1169
        //   51: iload 6
        //   53: istore 7
        //   55: iload 6
        //   57: istore 8
        //   59: iload 6
        //   61: istore 9
        //   63: aload_1
        //   64: invokevirtual 94	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
        //   67: istore 10
        //   69: iload 10
        //   71: ifeq +971 -> 1042
        //   74: iload 10
        //   76: bipush 8
        //   78: if_icmpeq +919 -> 997
        //   81: iload 10
        //   83: bipush 16
        //   85: if_icmpeq +867 -> 952
        //   88: iload 10
        //   90: bipush 24
        //   92: if_icmpeq +733 -> 825
        //   95: iload 10
        //   97: bipush 32
        //   99: if_icmpeq +617 -> 716
        //   102: iload 10
        //   104: bipush 34
        //   106: if_icmpeq +416 -> 522
        //   109: iload 10
        //   111: bipush 40
        //   113: if_icmpeq +300 -> 413
        //   116: iload 10
        //   118: bipush 42
        //   120: if_icmpeq +99 -> 219
        //   123: iload 10
        //   125: bipush 50
        //   127: if_icmpeq +31 -> 158
        //   130: iload 6
        //   132: istore 7
        //   134: iload 6
        //   136: istore 8
        //   138: iload 6
        //   140: istore 9
        //   142: aload_0
        //   143: aload_1
        //   144: aload 4
        //   146: aload_2
        //   147: iload 10
        //   149: invokevirtual 98	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
        //   152: ifne -106 -> 46
        //   155: goto +887 -> 1042
        //   158: iload 6
        //   160: istore 7
        //   162: iload 6
        //   164: istore 8
        //   166: iload 6
        //   168: istore 9
        //   170: aload_1
        //   171: invokevirtual 102	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readBytes	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   174: astore 11
        //   176: iload 6
        //   178: istore 7
        //   180: iload 6
        //   182: istore 8
        //   184: iload 6
        //   186: istore 9
        //   188: aload_0
        //   189: aload_0
        //   190: getfield 104	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:bitField0_	I
        //   193: iconst_4
        //   194: ior
        //   195: putfield 104	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:bitField0_	I
        //   198: iload 6
        //   200: istore 7
        //   202: iload 6
        //   204: istore 8
        //   206: iload 6
        //   208: istore 9
        //   210: aload_0
        //   211: aload 11
        //   213: putfield 106	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:string_	Ljava/lang/Object;
        //   216: goto -170 -> 46
        //   219: iload 6
        //   221: istore 7
        //   223: iload 6
        //   225: istore 8
        //   227: iload 6
        //   229: istore 9
        //   231: aload_1
        //   232: aload_1
        //   233: invokevirtual 109	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readRawVarint32	()I
        //   236: invokevirtual 113	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:pushLimit	(I)I
        //   239: istore 10
        //   241: iload 6
        //   243: istore 12
        //   245: iload 6
        //   247: bipush 32
        //   249: iand
        //   250: bipush 32
        //   252: if_icmpeq +85 -> 337
        //   255: iload 6
        //   257: istore 12
        //   259: iload 6
        //   261: istore 7
        //   263: iload 6
        //   265: istore 8
        //   267: iload 6
        //   269: istore 9
        //   271: aload_1
        //   272: invokevirtual 116	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
        //   275: ifle +62 -> 337
        //   278: iload 6
        //   280: istore 7
        //   282: iload 6
        //   284: istore 8
        //   286: iload 6
        //   288: istore 9
        //   290: new 118	java/util/ArrayList
        //   293: astore 11
        //   295: iload 6
        //   297: istore 7
        //   299: iload 6
        //   301: istore 8
        //   303: iload 6
        //   305: istore 9
        //   307: aload 11
        //   309: invokespecial 119	java/util/ArrayList:<init>	()V
        //   312: iload 6
        //   314: istore 7
        //   316: iload 6
        //   318: istore 8
        //   320: iload 6
        //   322: istore 9
        //   324: aload_0
        //   325: aload 11
        //   327: putfield 121	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:replaceChar_	Ljava/util/List;
        //   330: iload 6
        //   332: bipush 32
        //   334: ior
        //   335: istore 12
        //   337: iload 12
        //   339: istore 7
        //   341: iload 12
        //   343: istore 8
        //   345: iload 12
        //   347: istore 9
        //   349: aload_1
        //   350: invokevirtual 116	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
        //   353: ifle +35 -> 388
        //   356: iload 12
        //   358: istore 7
        //   360: iload 12
        //   362: istore 8
        //   364: iload 12
        //   366: istore 9
        //   368: aload_0
        //   369: getfield 121	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:replaceChar_	Ljava/util/List;
        //   372: aload_1
        //   373: invokevirtual 124	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
        //   376: invokestatic 130	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
        //   379: invokeinterface 136 2 0
        //   384: pop
        //   385: goto -48 -> 337
        //   388: iload 12
        //   390: istore 7
        //   392: iload 12
        //   394: istore 8
        //   396: iload 12
        //   398: istore 9
        //   400: aload_1
        //   401: iload 10
        //   403: invokevirtual 140	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:popLimit	(I)V
        //   406: iload 12
        //   408: istore 6
        //   410: goto -364 -> 46
        //   413: iload 6
        //   415: istore 12
        //   417: iload 6
        //   419: bipush 32
        //   421: iand
        //   422: bipush 32
        //   424: if_icmpeq +62 -> 486
        //   427: iload 6
        //   429: istore 7
        //   431: iload 6
        //   433: istore 8
        //   435: iload 6
        //   437: istore 9
        //   439: new 118	java/util/ArrayList
        //   442: astore 11
        //   444: iload 6
        //   446: istore 7
        //   448: iload 6
        //   450: istore 8
        //   452: iload 6
        //   454: istore 9
        //   456: aload 11
        //   458: invokespecial 119	java/util/ArrayList:<init>	()V
        //   461: iload 6
        //   463: istore 7
        //   465: iload 6
        //   467: istore 8
        //   469: iload 6
        //   471: istore 9
        //   473: aload_0
        //   474: aload 11
        //   476: putfield 121	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:replaceChar_	Ljava/util/List;
        //   479: iload 6
        //   481: bipush 32
        //   483: ior
        //   484: istore 12
        //   486: iload 12
        //   488: istore 7
        //   490: iload 12
        //   492: istore 8
        //   494: iload 12
        //   496: istore 9
        //   498: aload_0
        //   499: getfield 121	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:replaceChar_	Ljava/util/List;
        //   502: aload_1
        //   503: invokevirtual 124	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
        //   506: invokestatic 130	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
        //   509: invokeinterface 136 2 0
        //   514: pop
        //   515: iload 12
        //   517: istore 6
        //   519: goto -473 -> 46
        //   522: iload 6
        //   524: istore 7
        //   526: iload 6
        //   528: istore 8
        //   530: iload 6
        //   532: istore 9
        //   534: aload_1
        //   535: aload_1
        //   536: invokevirtual 109	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readRawVarint32	()I
        //   539: invokevirtual 113	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:pushLimit	(I)I
        //   542: istore 10
        //   544: iload 6
        //   546: istore 12
        //   548: iload 6
        //   550: bipush 16
        //   552: iand
        //   553: bipush 16
        //   555: if_icmpeq +85 -> 640
        //   558: iload 6
        //   560: istore 12
        //   562: iload 6
        //   564: istore 7
        //   566: iload 6
        //   568: istore 8
        //   570: iload 6
        //   572: istore 9
        //   574: aload_1
        //   575: invokevirtual 116	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
        //   578: ifle +62 -> 640
        //   581: iload 6
        //   583: istore 7
        //   585: iload 6
        //   587: istore 8
        //   589: iload 6
        //   591: istore 9
        //   593: new 118	java/util/ArrayList
        //   596: astore 11
        //   598: iload 6
        //   600: istore 7
        //   602: iload 6
        //   604: istore 8
        //   606: iload 6
        //   608: istore 9
        //   610: aload 11
        //   612: invokespecial 119	java/util/ArrayList:<init>	()V
        //   615: iload 6
        //   617: istore 7
        //   619: iload 6
        //   621: istore 8
        //   623: iload 6
        //   625: istore 9
        //   627: aload_0
        //   628: aload 11
        //   630: putfield 142	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:substringIndex_	Ljava/util/List;
        //   633: iload 6
        //   635: bipush 16
        //   637: ior
        //   638: istore 12
        //   640: iload 12
        //   642: istore 7
        //   644: iload 12
        //   646: istore 8
        //   648: iload 12
        //   650: istore 9
        //   652: aload_1
        //   653: invokevirtual 116	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
        //   656: ifle +35 -> 691
        //   659: iload 12
        //   661: istore 7
        //   663: iload 12
        //   665: istore 8
        //   667: iload 12
        //   669: istore 9
        //   671: aload_0
        //   672: getfield 142	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:substringIndex_	Ljava/util/List;
        //   675: aload_1
        //   676: invokevirtual 124	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
        //   679: invokestatic 130	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
        //   682: invokeinterface 136 2 0
        //   687: pop
        //   688: goto -48 -> 640
        //   691: iload 12
        //   693: istore 7
        //   695: iload 12
        //   697: istore 8
        //   699: iload 12
        //   701: istore 9
        //   703: aload_1
        //   704: iload 10
        //   706: invokevirtual 140	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:popLimit	(I)V
        //   709: iload 12
        //   711: istore 6
        //   713: goto -667 -> 46
        //   716: iload 6
        //   718: istore 12
        //   720: iload 6
        //   722: bipush 16
        //   724: iand
        //   725: bipush 16
        //   727: if_icmpeq +62 -> 789
        //   730: iload 6
        //   732: istore 7
        //   734: iload 6
        //   736: istore 8
        //   738: iload 6
        //   740: istore 9
        //   742: new 118	java/util/ArrayList
        //   745: astore 11
        //   747: iload 6
        //   749: istore 7
        //   751: iload 6
        //   753: istore 8
        //   755: iload 6
        //   757: istore 9
        //   759: aload 11
        //   761: invokespecial 119	java/util/ArrayList:<init>	()V
        //   764: iload 6
        //   766: istore 7
        //   768: iload 6
        //   770: istore 8
        //   772: iload 6
        //   774: istore 9
        //   776: aload_0
        //   777: aload 11
        //   779: putfield 142	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:substringIndex_	Ljava/util/List;
        //   782: iload 6
        //   784: bipush 16
        //   786: ior
        //   787: istore 12
        //   789: iload 12
        //   791: istore 7
        //   793: iload 12
        //   795: istore 8
        //   797: iload 12
        //   799: istore 9
        //   801: aload_0
        //   802: getfield 142	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:substringIndex_	Ljava/util/List;
        //   805: aload_1
        //   806: invokevirtual 124	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
        //   809: invokestatic 130	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
        //   812: invokeinterface 136 2 0
        //   817: pop
        //   818: iload 12
        //   820: istore 6
        //   822: goto -776 -> 46
        //   825: iload 6
        //   827: istore 7
        //   829: iload 6
        //   831: istore 8
        //   833: iload 6
        //   835: istore 9
        //   837: aload_1
        //   838: invokevirtual 145	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readEnum	()I
        //   841: istore 12
        //   843: iload 6
        //   845: istore 7
        //   847: iload 6
        //   849: istore 8
        //   851: iload 6
        //   853: istore 9
        //   855: iload 12
        //   857: invokestatic 148	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record$Operation:valueOf	(I)Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record$Operation;
        //   860: astore 11
        //   862: aload 11
        //   864: ifnonnull +44 -> 908
        //   867: iload 6
        //   869: istore 7
        //   871: iload 6
        //   873: istore 8
        //   875: iload 6
        //   877: istore 9
        //   879: aload 4
        //   881: iload 10
        //   883: invokevirtual 151	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:writeRawVarint32	(I)V
        //   886: iload 6
        //   888: istore 7
        //   890: iload 6
        //   892: istore 8
        //   894: iload 6
        //   896: istore 9
        //   898: aload 4
        //   900: iload 12
        //   902: invokevirtual 151	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:writeRawVarint32	(I)V
        //   905: goto -859 -> 46
        //   908: iload 6
        //   910: istore 7
        //   912: iload 6
        //   914: istore 8
        //   916: iload 6
        //   918: istore 9
        //   920: aload_0
        //   921: aload_0
        //   922: getfield 104	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:bitField0_	I
        //   925: bipush 8
        //   927: ior
        //   928: putfield 104	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:bitField0_	I
        //   931: iload 6
        //   933: istore 7
        //   935: iload 6
        //   937: istore 8
        //   939: iload 6
        //   941: istore 9
        //   943: aload_0
        //   944: aload 11
        //   946: putfield 153	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:operation_	Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record$Operation;
        //   949: goto -903 -> 46
        //   952: iload 6
        //   954: istore 7
        //   956: iload 6
        //   958: istore 8
        //   960: iload 6
        //   962: istore 9
        //   964: aload_0
        //   965: aload_0
        //   966: getfield 104	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:bitField0_	I
        //   969: iconst_2
        //   970: ior
        //   971: putfield 104	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:bitField0_	I
        //   974: iload 6
        //   976: istore 7
        //   978: iload 6
        //   980: istore 8
        //   982: iload 6
        //   984: istore 9
        //   986: aload_0
        //   987: aload_1
        //   988: invokevirtual 124	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
        //   991: putfield 155	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:predefinedIndex_	I
        //   994: goto -948 -> 46
        //   997: iload 6
        //   999: istore 7
        //   1001: iload 6
        //   1003: istore 8
        //   1005: iload 6
        //   1007: istore 9
        //   1009: aload_0
        //   1010: aload_0
        //   1011: getfield 104	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:bitField0_	I
        //   1014: iconst_1
        //   1015: ior
        //   1016: putfield 104	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:bitField0_	I
        //   1019: iload 6
        //   1021: istore 7
        //   1023: iload 6
        //   1025: istore 8
        //   1027: iload 6
        //   1029: istore 9
        //   1031: aload_0
        //   1032: aload_1
        //   1033: invokevirtual 124	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
        //   1036: putfield 157	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:range_	I
        //   1039: goto -993 -> 46
        //   1042: iconst_1
        //   1043: istore 5
        //   1045: goto -999 -> 46
        //   1048: astore_1
        //   1049: goto +45 -> 1094
        //   1052: astore_2
        //   1053: iload 8
        //   1055: istore 7
        //   1057: new 65	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   1060: astore_1
        //   1061: iload 8
        //   1063: istore 7
        //   1065: aload_1
        //   1066: aload_2
        //   1067: invokevirtual 161	java/io/IOException:getMessage	()Ljava/lang/String;
        //   1070: invokespecial 164	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
        //   1073: iload 8
        //   1075: istore 7
        //   1077: aload_1
        //   1078: aload_0
        //   1079: invokevirtual 168	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
        //   1082: athrow
        //   1083: astore_1
        //   1084: iload 9
        //   1086: istore 7
        //   1088: aload_1
        //   1089: aload_0
        //   1090: invokevirtual 168	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
        //   1093: athrow
        //   1094: iload 7
        //   1096: bipush 16
        //   1098: iand
        //   1099: bipush 16
        //   1101: if_icmpne +14 -> 1115
        //   1104: aload_0
        //   1105: aload_0
        //   1106: getfield 142	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:substringIndex_	Ljava/util/List;
        //   1109: invokestatic 174	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
        //   1112: putfield 142	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:substringIndex_	Ljava/util/List;
        //   1115: iload 7
        //   1117: bipush 32
        //   1119: iand
        //   1120: bipush 32
        //   1122: if_icmpne +14 -> 1136
        //   1125: aload_0
        //   1126: aload_0
        //   1127: getfield 121	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:replaceChar_	Ljava/util/List;
        //   1130: invokestatic 174	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
        //   1133: putfield 121	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:replaceChar_	Ljava/util/List;
        //   1136: aload 4
        //   1138: invokevirtual 177	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
        //   1141: aload_0
        //   1142: aload_3
        //   1143: invokevirtual 182	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   1146: putfield 184	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   1149: goto +14 -> 1163
        //   1152: astore_1
        //   1153: aload_0
        //   1154: aload_3
        //   1155: invokevirtual 182	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   1158: putfield 184	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   1161: aload_1
        //   1162: athrow
        //   1163: aload_0
        //   1164: invokevirtual 187	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:makeExtensionsImmutable	()V
        //   1167: aload_1
        //   1168: athrow
        //   1169: iload 6
        //   1171: bipush 16
        //   1173: iand
        //   1174: bipush 16
        //   1176: if_icmpne +14 -> 1190
        //   1179: aload_0
        //   1180: aload_0
        //   1181: getfield 142	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:substringIndex_	Ljava/util/List;
        //   1184: invokestatic 174	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
        //   1187: putfield 142	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:substringIndex_	Ljava/util/List;
        //   1190: iload 6
        //   1192: bipush 32
        //   1194: iand
        //   1195: bipush 32
        //   1197: if_icmpne +14 -> 1211
        //   1200: aload_0
        //   1201: aload_0
        //   1202: getfield 121	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:replaceChar_	Ljava/util/List;
        //   1205: invokestatic 174	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
        //   1208: putfield 121	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:replaceChar_	Ljava/util/List;
        //   1211: aload 4
        //   1213: invokevirtual 177	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
        //   1216: aload_0
        //   1217: aload_3
        //   1218: invokevirtual 182	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   1221: putfield 184	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   1224: goto +14 -> 1238
        //   1227: astore_1
        //   1228: aload_0
        //   1229: aload_3
        //   1230: invokevirtual 182	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   1233: putfield 184	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   1236: aload_1
        //   1237: athrow
        //   1238: aload_0
        //   1239: invokevirtual 187	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:makeExtensionsImmutable	()V
        //   1242: return
        //   1243: astore_2
        //   1244: goto -103 -> 1141
        //   1247: astore_1
        //   1248: goto -32 -> 1216
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	1251	0	this	Record
        //   0	1251	1	paramCodedInputStream	CodedInputStream
        //   0	1251	2	paramExtensionRegistryLite	ExtensionRegistryLite
        //   32	1198	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
        //   38	1174	4	localCodedOutputStream	CodedOutputStream
        //   41	1003	5	i	int
        //   44	1151	6	j	int
        //   53	1067	7	k	int
        //   57	1017	8	m	int
        //   61	1024	9	n	int
        //   67	815	10	i1	int
        //   174	771	11	localObject	Object
        //   243	658	12	i2	int
        // Exception table:
        //   from	to	target	type
        //   63	69	1048	finally
        //   142	155	1048	finally
        //   170	176	1048	finally
        //   188	198	1048	finally
        //   210	216	1048	finally
        //   231	241	1048	finally
        //   271	278	1048	finally
        //   290	295	1048	finally
        //   307	312	1048	finally
        //   324	330	1048	finally
        //   349	356	1048	finally
        //   368	385	1048	finally
        //   400	406	1048	finally
        //   439	444	1048	finally
        //   456	461	1048	finally
        //   473	479	1048	finally
        //   498	515	1048	finally
        //   534	544	1048	finally
        //   574	581	1048	finally
        //   593	598	1048	finally
        //   610	615	1048	finally
        //   627	633	1048	finally
        //   652	659	1048	finally
        //   671	688	1048	finally
        //   703	709	1048	finally
        //   742	747	1048	finally
        //   759	764	1048	finally
        //   776	782	1048	finally
        //   801	818	1048	finally
        //   837	843	1048	finally
        //   855	862	1048	finally
        //   879	886	1048	finally
        //   898	905	1048	finally
        //   920	931	1048	finally
        //   943	949	1048	finally
        //   964	974	1048	finally
        //   986	994	1048	finally
        //   1009	1019	1048	finally
        //   1031	1039	1048	finally
        //   1057	1061	1048	finally
        //   1065	1073	1048	finally
        //   1077	1083	1048	finally
        //   1088	1094	1048	finally
        //   63	69	1052	java/io/IOException
        //   142	155	1052	java/io/IOException
        //   170	176	1052	java/io/IOException
        //   188	198	1052	java/io/IOException
        //   210	216	1052	java/io/IOException
        //   231	241	1052	java/io/IOException
        //   271	278	1052	java/io/IOException
        //   290	295	1052	java/io/IOException
        //   307	312	1052	java/io/IOException
        //   324	330	1052	java/io/IOException
        //   349	356	1052	java/io/IOException
        //   368	385	1052	java/io/IOException
        //   400	406	1052	java/io/IOException
        //   439	444	1052	java/io/IOException
        //   456	461	1052	java/io/IOException
        //   473	479	1052	java/io/IOException
        //   498	515	1052	java/io/IOException
        //   534	544	1052	java/io/IOException
        //   574	581	1052	java/io/IOException
        //   593	598	1052	java/io/IOException
        //   610	615	1052	java/io/IOException
        //   627	633	1052	java/io/IOException
        //   652	659	1052	java/io/IOException
        //   671	688	1052	java/io/IOException
        //   703	709	1052	java/io/IOException
        //   742	747	1052	java/io/IOException
        //   759	764	1052	java/io/IOException
        //   776	782	1052	java/io/IOException
        //   801	818	1052	java/io/IOException
        //   837	843	1052	java/io/IOException
        //   855	862	1052	java/io/IOException
        //   879	886	1052	java/io/IOException
        //   898	905	1052	java/io/IOException
        //   920	931	1052	java/io/IOException
        //   943	949	1052	java/io/IOException
        //   964	974	1052	java/io/IOException
        //   986	994	1052	java/io/IOException
        //   1009	1019	1052	java/io/IOException
        //   1031	1039	1052	java/io/IOException
        //   63	69	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   142	155	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   170	176	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   188	198	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   210	216	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   231	241	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   271	278	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   290	295	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   307	312	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   324	330	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   349	356	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   368	385	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   400	406	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   439	444	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   456	461	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   473	479	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   498	515	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   534	544	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   574	581	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   593	598	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   610	615	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   627	633	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   652	659	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   671	688	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   703	709	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   742	747	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   759	764	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   776	782	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   801	818	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   837	843	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   855	862	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   879	886	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   898	905	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   920	931	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   943	949	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   964	974	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   986	994	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   1009	1019	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   1031	1039	1083	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   1136	1141	1152	finally
        //   1211	1216	1227	finally
        //   1136	1141	1243	java/io/IOException
        //   1211	1216	1247	java/io/IOException
      }
      
      private Record(GeneratedMessageLite.Builder paramBuilder)
      {
        super();
        this.unknownFields = paramBuilder.getUnknownFields();
      }
      
      private Record(boolean paramBoolean)
      {
        this.unknownFields = ByteString.EMPTY;
      }
      
      public static Record getDefaultInstance()
      {
        return defaultInstance;
      }
      
      private void initFields()
      {
        this.range_ = 1;
        this.predefinedIndex_ = 0;
        this.string_ = "";
        this.operation_ = Operation.NONE;
        this.substringIndex_ = Collections.emptyList();
        this.replaceChar_ = Collections.emptyList();
      }
      
      public static Builder newBuilder()
      {
        return Builder.access$200();
      }
      
      public static Builder newBuilder(Record paramRecord)
      {
        return newBuilder().mergeFrom(paramRecord);
      }
      
      public Record getDefaultInstanceForType()
      {
        return defaultInstance;
      }
      
      public Operation getOperation()
      {
        return this.operation_;
      }
      
      public Parser<Record> getParserForType()
      {
        return PARSER;
      }
      
      public int getPredefinedIndex()
      {
        return this.predefinedIndex_;
      }
      
      public int getRange()
      {
        return this.range_;
      }
      
      public int getReplaceCharCount()
      {
        return this.replaceChar_.size();
      }
      
      public List<Integer> getReplaceCharList()
      {
        return this.replaceChar_;
      }
      
      public int getSerializedSize()
      {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
          return i;
        }
        i = this.bitField0_;
        int j = 0;
        if ((i & 0x1) == 1) {
          k = CodedOutputStream.computeInt32Size(1, this.range_) + 0;
        } else {
          k = 0;
        }
        i = k;
        if ((this.bitField0_ & 0x2) == 2) {
          i = k + CodedOutputStream.computeInt32Size(2, this.predefinedIndex_);
        }
        int k = i;
        if ((this.bitField0_ & 0x8) == 8) {
          k = i + CodedOutputStream.computeEnumSize(3, this.operation_.getNumber());
        }
        i = 0;
        int m = i;
        while (i < this.substringIndex_.size())
        {
          m += CodedOutputStream.computeInt32SizeNoTag(((Integer)this.substringIndex_.get(i)).intValue());
          i++;
        }
        k += m;
        i = k;
        if (!getSubstringIndexList().isEmpty()) {
          i = k + 1 + CodedOutputStream.computeInt32SizeNoTag(m);
        }
        this.substringIndexMemoizedSerializedSize = m;
        k = 0;
        for (m = j; m < this.replaceChar_.size(); m++) {
          k += CodedOutputStream.computeInt32SizeNoTag(((Integer)this.replaceChar_.get(m)).intValue());
        }
        m = i + k;
        i = m;
        if (!getReplaceCharList().isEmpty()) {
          i = m + 1 + CodedOutputStream.computeInt32SizeNoTag(k);
        }
        this.replaceCharMemoizedSerializedSize = k;
        k = i;
        if ((this.bitField0_ & 0x4) == 4) {
          k = i + CodedOutputStream.computeBytesSize(6, getStringBytes());
        }
        i = k + this.unknownFields.size();
        this.memoizedSerializedSize = i;
        return i;
      }
      
      public String getString()
      {
        Object localObject = this.string_;
        if ((localObject instanceof String)) {
          return (String)localObject;
        }
        ByteString localByteString = (ByteString)localObject;
        localObject = localByteString.toStringUtf8();
        if (localByteString.isValidUtf8()) {
          this.string_ = localObject;
        }
        return localObject;
      }
      
      public ByteString getStringBytes()
      {
        Object localObject = this.string_;
        if ((localObject instanceof String))
        {
          localObject = ByteString.copyFromUtf8((String)localObject);
          this.string_ = localObject;
          return localObject;
        }
        return (ByteString)localObject;
      }
      
      public int getSubstringIndexCount()
      {
        return this.substringIndex_.size();
      }
      
      public List<Integer> getSubstringIndexList()
      {
        return this.substringIndex_;
      }
      
      public boolean hasOperation()
      {
        boolean bool;
        if ((this.bitField0_ & 0x8) == 8) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasPredefinedIndex()
      {
        boolean bool;
        if ((this.bitField0_ & 0x2) == 2) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasRange()
      {
        int i = this.bitField0_;
        boolean bool = true;
        if ((i & 0x1) != 1) {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasString()
      {
        boolean bool;
        if ((this.bitField0_ & 0x4) == 4) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public final boolean isInitialized()
      {
        int i = this.memoizedIsInitialized;
        if (i == 1) {
          return true;
        }
        if (i == 0) {
          return false;
        }
        this.memoizedIsInitialized = ((byte)1);
        return true;
      }
      
      public Builder newBuilderForType()
      {
        return newBuilder();
      }
      
      public Builder toBuilder()
      {
        return newBuilder(this);
      }
      
      public void writeTo(CodedOutputStream paramCodedOutputStream)
        throws IOException
      {
        getSerializedSize();
        if ((this.bitField0_ & 0x1) == 1) {
          paramCodedOutputStream.writeInt32(1, this.range_);
        }
        if ((this.bitField0_ & 0x2) == 2) {
          paramCodedOutputStream.writeInt32(2, this.predefinedIndex_);
        }
        if ((this.bitField0_ & 0x8) == 8) {
          paramCodedOutputStream.writeEnum(3, this.operation_.getNumber());
        }
        if (getSubstringIndexList().size() > 0)
        {
          paramCodedOutputStream.writeRawVarint32(34);
          paramCodedOutputStream.writeRawVarint32(this.substringIndexMemoizedSerializedSize);
        }
        int i = 0;
        for (int j = 0; j < this.substringIndex_.size(); j++) {
          paramCodedOutputStream.writeInt32NoTag(((Integer)this.substringIndex_.get(j)).intValue());
        }
        j = i;
        if (getReplaceCharList().size() > 0)
        {
          paramCodedOutputStream.writeRawVarint32(42);
          paramCodedOutputStream.writeRawVarint32(this.replaceCharMemoizedSerializedSize);
        }
        for (j = i; j < this.replaceChar_.size(); j++) {
          paramCodedOutputStream.writeInt32NoTag(((Integer)this.replaceChar_.get(j)).intValue());
        }
        if ((this.bitField0_ & 0x4) == 4) {
          paramCodedOutputStream.writeBytes(6, getStringBytes());
        }
        paramCodedOutputStream.writeRawBytes(this.unknownFields);
      }
      
      public static final class Builder
        extends GeneratedMessageLite.Builder<JvmProtoBuf.StringTableTypes.Record, Builder>
        implements JvmProtoBuf.StringTableTypes.RecordOrBuilder
      {
        private int bitField0_;
        private JvmProtoBuf.StringTableTypes.Record.Operation operation_ = JvmProtoBuf.StringTableTypes.Record.Operation.NONE;
        private int predefinedIndex_;
        private int range_ = 1;
        private List<Integer> replaceChar_ = Collections.emptyList();
        private Object string_ = "";
        private List<Integer> substringIndex_ = Collections.emptyList();
        
        private Builder()
        {
          maybeForceBuilderInitialization();
        }
        
        private static Builder create()
        {
          return new Builder();
        }
        
        private void ensureReplaceCharIsMutable()
        {
          if ((this.bitField0_ & 0x20) != 32)
          {
            this.replaceChar_ = new ArrayList(this.replaceChar_);
            this.bitField0_ |= 0x20;
          }
        }
        
        private void ensureSubstringIndexIsMutable()
        {
          if ((this.bitField0_ & 0x10) != 16)
          {
            this.substringIndex_ = new ArrayList(this.substringIndex_);
            this.bitField0_ |= 0x10;
          }
        }
        
        private void maybeForceBuilderInitialization() {}
        
        public JvmProtoBuf.StringTableTypes.Record build()
        {
          JvmProtoBuf.StringTableTypes.Record localRecord = buildPartial();
          if (localRecord.isInitialized()) {
            return localRecord;
          }
          throw newUninitializedMessageException(localRecord);
        }
        
        public JvmProtoBuf.StringTableTypes.Record buildPartial()
        {
          JvmProtoBuf.StringTableTypes.Record localRecord = new JvmProtoBuf.StringTableTypes.Record(this, null);
          int i = this.bitField0_;
          int j = 1;
          if ((i & 0x1) != 1) {
            j = 0;
          }
          JvmProtoBuf.StringTableTypes.Record.access$402(localRecord, this.range_);
          int k = j;
          if ((i & 0x2) == 2) {
            k = j | 0x2;
          }
          JvmProtoBuf.StringTableTypes.Record.access$502(localRecord, this.predefinedIndex_);
          j = k;
          if ((i & 0x4) == 4) {
            j = k | 0x4;
          }
          JvmProtoBuf.StringTableTypes.Record.access$602(localRecord, this.string_);
          k = j;
          if ((i & 0x8) == 8) {
            k = j | 0x8;
          }
          JvmProtoBuf.StringTableTypes.Record.access$702(localRecord, this.operation_);
          if ((this.bitField0_ & 0x10) == 16)
          {
            this.substringIndex_ = Collections.unmodifiableList(this.substringIndex_);
            this.bitField0_ &= 0xFFFFFFEF;
          }
          JvmProtoBuf.StringTableTypes.Record.access$802(localRecord, this.substringIndex_);
          if ((this.bitField0_ & 0x20) == 32)
          {
            this.replaceChar_ = Collections.unmodifiableList(this.replaceChar_);
            this.bitField0_ &= 0xFFFFFFDF;
          }
          JvmProtoBuf.StringTableTypes.Record.access$902(localRecord, this.replaceChar_);
          JvmProtoBuf.StringTableTypes.Record.access$1002(localRecord, k);
          return localRecord;
        }
        
        public Builder clone()
        {
          return create().mergeFrom(buildPartial());
        }
        
        public JvmProtoBuf.StringTableTypes.Record getDefaultInstanceForType()
        {
          return JvmProtoBuf.StringTableTypes.Record.getDefaultInstance();
        }
        
        public final boolean isInitialized()
        {
          return true;
        }
        
        public Builder mergeFrom(JvmProtoBuf.StringTableTypes.Record paramRecord)
        {
          if (paramRecord == JvmProtoBuf.StringTableTypes.Record.getDefaultInstance()) {
            return this;
          }
          if (paramRecord.hasRange()) {
            setRange(paramRecord.getRange());
          }
          if (paramRecord.hasPredefinedIndex()) {
            setPredefinedIndex(paramRecord.getPredefinedIndex());
          }
          if (paramRecord.hasString())
          {
            this.bitField0_ |= 0x4;
            this.string_ = paramRecord.string_;
          }
          if (paramRecord.hasOperation()) {
            setOperation(paramRecord.getOperation());
          }
          if (!paramRecord.substringIndex_.isEmpty()) {
            if (this.substringIndex_.isEmpty())
            {
              this.substringIndex_ = paramRecord.substringIndex_;
              this.bitField0_ &= 0xFFFFFFEF;
            }
            else
            {
              ensureSubstringIndexIsMutable();
              this.substringIndex_.addAll(paramRecord.substringIndex_);
            }
          }
          if (!paramRecord.replaceChar_.isEmpty()) {
            if (this.replaceChar_.isEmpty())
            {
              this.replaceChar_ = paramRecord.replaceChar_;
              this.bitField0_ &= 0xFFFFFFDF;
            }
            else
            {
              ensureReplaceCharIsMutable();
              this.replaceChar_.addAll(paramRecord.replaceChar_);
            }
          }
          setUnknownFields(getUnknownFields().concat(paramRecord.unknownFields));
          return this;
        }
        
        /* Error */
        public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
          throws IOException
        {
          // Byte code:
          //   0: aconst_null
          //   1: astore_3
          //   2: getstatic 229	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
          //   5: aload_1
          //   6: aload_2
          //   7: invokeinterface 235 3 0
          //   12: checkcast 14	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record
          //   15: astore_1
          //   16: aload_1
          //   17: ifnull +9 -> 26
          //   20: aload_0
          //   21: aload_1
          //   22: invokevirtual 135	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record;)Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record$Builder;
          //   25: pop
          //   26: aload_0
          //   27: areturn
          //   28: astore_1
          //   29: aload_3
          //   30: astore_2
          //   31: goto +15 -> 46
          //   34: astore_1
          //   35: aload_1
          //   36: invokevirtual 238	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
          //   39: checkcast 14	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record
          //   42: astore_2
          //   43: aload_1
          //   44: athrow
          //   45: astore_1
          //   46: aload_2
          //   47: ifnull +9 -> 56
          //   50: aload_0
          //   51: aload_2
          //   52: invokevirtual 135	kotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record;)Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$StringTableTypes$Record$Builder;
          //   55: pop
          //   56: aload_1
          //   57: athrow
          // Local variable table:
          //   start	length	slot	name	signature
          //   0	58	0	this	Builder
          //   0	58	1	paramCodedInputStream	CodedInputStream
          //   0	58	2	paramExtensionRegistryLite	ExtensionRegistryLite
          //   1	29	3	localObject	Object
          // Exception table:
          //   from	to	target	type
          //   2	16	28	finally
          //   35	43	28	finally
          //   2	16	34	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   43	45	45	finally
        }
        
        public Builder setOperation(JvmProtoBuf.StringTableTypes.Record.Operation paramOperation)
        {
          if (paramOperation != null)
          {
            this.bitField0_ |= 0x8;
            this.operation_ = paramOperation;
            return this;
          }
          throw null;
        }
        
        public Builder setPredefinedIndex(int paramInt)
        {
          this.bitField0_ |= 0x2;
          this.predefinedIndex_ = paramInt;
          return this;
        }
        
        public Builder setRange(int paramInt)
        {
          this.bitField0_ |= 0x1;
          this.range_ = paramInt;
          return this;
        }
      }
      
      public static enum Operation
        implements Internal.EnumLite
      {
        private static Internal.EnumLiteMap<Operation> internalValueMap = new Internal.EnumLiteMap()
        {
          public JvmProtoBuf.StringTableTypes.Record.Operation findValueByNumber(int paramAnonymousInt)
          {
            return JvmProtoBuf.StringTableTypes.Record.Operation.valueOf(paramAnonymousInt);
          }
        };
        private final int value;
        
        static
        {
          INTERNAL_TO_CLASS_ID = new Operation("INTERNAL_TO_CLASS_ID", 1, 1, 1);
          Operation localOperation = new Operation("DESC_TO_CLASS_ID", 2, 2, 2);
          DESC_TO_CLASS_ID = localOperation;
          $VALUES = new Operation[] { NONE, INTERNAL_TO_CLASS_ID, localOperation };
        }
        
        private Operation(int paramInt1, int paramInt2)
        {
          this.value = paramInt2;
        }
        
        public static Operation valueOf(int paramInt)
        {
          if (paramInt != 0)
          {
            if (paramInt != 1)
            {
              if (paramInt != 2) {
                return null;
              }
              return DESC_TO_CLASS_ID;
            }
            return INTERNAL_TO_CLASS_ID;
          }
          return NONE;
        }
        
        public final int getNumber()
        {
          return this.value;
        }
      }
    }
  }
}

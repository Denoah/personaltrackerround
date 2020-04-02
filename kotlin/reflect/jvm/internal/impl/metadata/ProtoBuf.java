package kotlin.reflect.jvm.internal.impl.metadata;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.protobuf.AbstractParser;
import kotlin.reflect.jvm.internal.impl.protobuf.ByteString;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedOutputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.Builder;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.ExtendableBuilder;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.ExtendableMessage;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.ExtendableMessage.ExtensionWriter;
import kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLite;
import kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLiteMap;
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException;
import kotlin.reflect.jvm.internal.impl.protobuf.LazyStringArrayList;
import kotlin.reflect.jvm.internal.impl.protobuf.LazyStringList;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.Parser;
import kotlin.reflect.jvm.internal.impl.protobuf.ProtocolStringList;

public final class ProtoBuf
{
  public static final class Annotation
    extends GeneratedMessageLite
    implements ProtoBuf.AnnotationOrBuilder
  {
    public static Parser<Annotation> PARSER = new AbstractParser()
    {
      public ProtoBuf.Annotation parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new ProtoBuf.Annotation(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final Annotation defaultInstance;
    private List<Argument> argument_;
    private int bitField0_;
    private int id_;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private final ByteString unknownFields;
    
    static
    {
      Annotation localAnnotation = new Annotation(true);
      defaultInstance = localAnnotation;
      localAnnotation.initFields();
    }
    
    /* Error */
    private Annotation(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 71	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: i2b
      //   7: putfield 73	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:memoizedIsInitialized	B
      //   10: aload_0
      //   11: iconst_m1
      //   12: putfield 75	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:memoizedSerializedSize	I
      //   15: aload_0
      //   16: invokespecial 64	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:initFields	()V
      //   19: invokestatic 81	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   22: astore_3
      //   23: aload_3
      //   24: iconst_1
      //   25: invokestatic 87	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   28: astore 4
      //   30: iconst_0
      //   31: istore 5
      //   33: iconst_0
      //   34: istore 6
      //   36: iload 5
      //   38: ifne +324 -> 362
      //   41: iload 6
      //   43: istore 7
      //   45: iload 6
      //   47: istore 8
      //   49: iload 6
      //   51: istore 9
      //   53: aload_1
      //   54: invokevirtual 93	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   57: istore 10
      //   59: iload 10
      //   61: ifeq +197 -> 258
      //   64: iload 10
      //   66: bipush 8
      //   68: if_icmpeq +145 -> 213
      //   71: iload 10
      //   73: bipush 18
      //   75: if_icmpeq +31 -> 106
      //   78: iload 6
      //   80: istore 7
      //   82: iload 6
      //   84: istore 8
      //   86: iload 6
      //   88: istore 9
      //   90: aload_0
      //   91: aload_1
      //   92: aload 4
      //   94: aload_2
      //   95: iload 10
      //   97: invokevirtual 97	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   100: ifne -64 -> 36
      //   103: goto +155 -> 258
      //   106: iload 6
      //   108: istore 10
      //   110: iload 6
      //   112: iconst_2
      //   113: iand
      //   114: iconst_2
      //   115: if_icmpeq +61 -> 176
      //   118: iload 6
      //   120: istore 7
      //   122: iload 6
      //   124: istore 8
      //   126: iload 6
      //   128: istore 9
      //   130: new 99	java/util/ArrayList
      //   133: astore 11
      //   135: iload 6
      //   137: istore 7
      //   139: iload 6
      //   141: istore 8
      //   143: iload 6
      //   145: istore 9
      //   147: aload 11
      //   149: invokespecial 100	java/util/ArrayList:<init>	()V
      //   152: iload 6
      //   154: istore 7
      //   156: iload 6
      //   158: istore 8
      //   160: iload 6
      //   162: istore 9
      //   164: aload_0
      //   165: aload 11
      //   167: putfield 102	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:argument_	Ljava/util/List;
      //   170: iload 6
      //   172: iconst_2
      //   173: ior
      //   174: istore 10
      //   176: iload 10
      //   178: istore 7
      //   180: iload 10
      //   182: istore 8
      //   184: iload 10
      //   186: istore 9
      //   188: aload_0
      //   189: getfield 102	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:argument_	Ljava/util/List;
      //   192: aload_1
      //   193: getstatic 103	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   196: aload_2
      //   197: invokevirtual 107	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   200: invokeinterface 113 2 0
      //   205: pop
      //   206: iload 10
      //   208: istore 6
      //   210: goto -174 -> 36
      //   213: iload 6
      //   215: istore 7
      //   217: iload 6
      //   219: istore 8
      //   221: iload 6
      //   223: istore 9
      //   225: aload_0
      //   226: aload_0
      //   227: getfield 115	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:bitField0_	I
      //   230: iconst_1
      //   231: ior
      //   232: putfield 115	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:bitField0_	I
      //   235: iload 6
      //   237: istore 7
      //   239: iload 6
      //   241: istore 8
      //   243: iload 6
      //   245: istore 9
      //   247: aload_0
      //   248: aload_1
      //   249: invokevirtual 118	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   252: putfield 120	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:id_	I
      //   255: goto -219 -> 36
      //   258: iconst_1
      //   259: istore 5
      //   261: goto -225 -> 36
      //   264: astore_1
      //   265: goto +45 -> 310
      //   268: astore_2
      //   269: iload 8
      //   271: istore 7
      //   273: new 68	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   276: astore_1
      //   277: iload 8
      //   279: istore 7
      //   281: aload_1
      //   282: aload_2
      //   283: invokevirtual 124	java/io/IOException:getMessage	()Ljava/lang/String;
      //   286: invokespecial 127	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   289: iload 8
      //   291: istore 7
      //   293: aload_1
      //   294: aload_0
      //   295: invokevirtual 131	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   298: athrow
      //   299: astore_1
      //   300: iload 9
      //   302: istore 7
      //   304: aload_1
      //   305: aload_0
      //   306: invokevirtual 131	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   309: athrow
      //   310: iload 7
      //   312: iconst_2
      //   313: iand
      //   314: iconst_2
      //   315: if_icmpne +14 -> 329
      //   318: aload_0
      //   319: aload_0
      //   320: getfield 102	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:argument_	Ljava/util/List;
      //   323: invokestatic 137	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   326: putfield 102	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:argument_	Ljava/util/List;
      //   329: aload 4
      //   331: invokevirtual 140	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   334: aload_0
      //   335: aload_3
      //   336: invokevirtual 146	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   339: putfield 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   342: goto +14 -> 356
      //   345: astore_1
      //   346: aload_0
      //   347: aload_3
      //   348: invokevirtual 146	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   351: putfield 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   354: aload_1
      //   355: athrow
      //   356: aload_0
      //   357: invokevirtual 151	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:makeExtensionsImmutable	()V
      //   360: aload_1
      //   361: athrow
      //   362: iload 6
      //   364: iconst_2
      //   365: iand
      //   366: iconst_2
      //   367: if_icmpne +14 -> 381
      //   370: aload_0
      //   371: aload_0
      //   372: getfield 102	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:argument_	Ljava/util/List;
      //   375: invokestatic 137	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   378: putfield 102	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:argument_	Ljava/util/List;
      //   381: aload 4
      //   383: invokevirtual 140	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   386: aload_0
      //   387: aload_3
      //   388: invokevirtual 146	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   391: putfield 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   394: goto +14 -> 408
      //   397: astore_1
      //   398: aload_0
      //   399: aload_3
      //   400: invokevirtual 146	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   403: putfield 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   406: aload_1
      //   407: athrow
      //   408: aload_0
      //   409: invokevirtual 151	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:makeExtensionsImmutable	()V
      //   412: return
      //   413: astore_2
      //   414: goto -80 -> 334
      //   417: astore_1
      //   418: goto -32 -> 386
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	421	0	this	Annotation
      //   0	421	1	paramCodedInputStream	CodedInputStream
      //   0	421	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   22	378	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   28	354	4	localCodedOutputStream	CodedOutputStream
      //   31	229	5	i	int
      //   34	332	6	j	int
      //   43	271	7	k	int
      //   47	243	8	m	int
      //   51	250	9	n	int
      //   57	150	10	i1	int
      //   133	33	11	localArrayList	ArrayList
      // Exception table:
      //   from	to	target	type
      //   53	59	264	finally
      //   90	103	264	finally
      //   130	135	264	finally
      //   147	152	264	finally
      //   164	170	264	finally
      //   188	206	264	finally
      //   225	235	264	finally
      //   247	255	264	finally
      //   273	277	264	finally
      //   281	289	264	finally
      //   293	299	264	finally
      //   304	310	264	finally
      //   53	59	268	java/io/IOException
      //   90	103	268	java/io/IOException
      //   130	135	268	java/io/IOException
      //   147	152	268	java/io/IOException
      //   164	170	268	java/io/IOException
      //   188	206	268	java/io/IOException
      //   225	235	268	java/io/IOException
      //   247	255	268	java/io/IOException
      //   53	59	299	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   90	103	299	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   130	135	299	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   147	152	299	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   164	170	299	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   188	206	299	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   225	235	299	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   247	255	299	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   329	334	345	finally
      //   381	386	397	finally
      //   329	334	413	java/io/IOException
      //   381	386	417	java/io/IOException
    }
    
    private Annotation(GeneratedMessageLite.Builder paramBuilder)
    {
      super();
      this.unknownFields = paramBuilder.getUnknownFields();
    }
    
    private Annotation(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static Annotation getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.id_ = 0;
      this.argument_ = Collections.emptyList();
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$4200();
    }
    
    public static Builder newBuilder(Annotation paramAnnotation)
    {
      return newBuilder().mergeFrom(paramAnnotation);
    }
    
    public Argument getArgument(int paramInt)
    {
      return (Argument)this.argument_.get(paramInt);
    }
    
    public int getArgumentCount()
    {
      return this.argument_.size();
    }
    
    public List<Argument> getArgumentList()
    {
      return this.argument_;
    }
    
    public Annotation getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public int getId()
    {
      return this.id_;
    }
    
    public Parser<Annotation> getParserForType()
    {
      return PARSER;
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
        i = CodedOutputStream.computeInt32Size(1, this.id_) + 0;
      } else {
        i = 0;
      }
      while (j < this.argument_.size())
      {
        i += CodedOutputStream.computeMessageSize(2, (MessageLite)this.argument_.get(j));
        j++;
      }
      i += this.unknownFields.size();
      this.memoizedSerializedSize = i;
      return i;
    }
    
    public boolean hasId()
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
      if (!hasId())
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      for (i = 0; i < getArgumentCount(); i++) {
        if (!getArgument(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
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
        paramCodedOutputStream.writeInt32(1, this.id_);
      }
      for (int i = 0; i < this.argument_.size(); i++) {
        paramCodedOutputStream.writeMessage(2, (MessageLite)this.argument_.get(i));
      }
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Argument
      extends GeneratedMessageLite
      implements ProtoBuf.Annotation.ArgumentOrBuilder
    {
      public static Parser<Argument> PARSER = new AbstractParser()
      {
        public ProtoBuf.Annotation.Argument parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
          throws InvalidProtocolBufferException
        {
          return new ProtoBuf.Annotation.Argument(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
        }
      };
      private static final Argument defaultInstance;
      private int bitField0_;
      private byte memoizedIsInitialized = (byte)-1;
      private int memoizedSerializedSize = -1;
      private int nameId_;
      private final ByteString unknownFields;
      private Value value_;
      
      static
      {
        Argument localArgument = new Argument(true);
        defaultInstance = localArgument;
        localArgument.initFields();
      }
      
      /* Error */
      private Argument(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        // Byte code:
        //   0: aload_0
        //   1: invokespecial 66	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite:<init>	()V
        //   4: aload_0
        //   5: iconst_m1
        //   6: i2b
        //   7: putfield 68	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument:memoizedIsInitialized	B
        //   10: aload_0
        //   11: iconst_m1
        //   12: putfield 70	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument:memoizedSerializedSize	I
        //   15: aload_0
        //   16: invokespecial 59	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument:initFields	()V
        //   19: invokestatic 76	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
        //   22: astore_3
        //   23: aload_3
        //   24: iconst_1
        //   25: invokestatic 82	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
        //   28: astore 4
        //   30: iconst_0
        //   31: istore 5
        //   33: iload 5
        //   35: ifne +210 -> 245
        //   38: aload_1
        //   39: invokevirtual 88	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
        //   42: istore 6
        //   44: iload 6
        //   46: ifeq +130 -> 176
        //   49: iload 6
        //   51: bipush 8
        //   53: if_icmpeq +102 -> 155
        //   56: iload 6
        //   58: bipush 18
        //   60: if_icmpeq +19 -> 79
        //   63: aload_0
        //   64: aload_1
        //   65: aload 4
        //   67: aload_2
        //   68: iload 6
        //   70: invokevirtual 92	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
        //   73: ifne -40 -> 33
        //   76: goto +100 -> 176
        //   79: aconst_null
        //   80: astore 7
        //   82: aload_0
        //   83: getfield 94	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument:bitField0_	I
        //   86: iconst_2
        //   87: iand
        //   88: iconst_2
        //   89: if_icmpne +12 -> 101
        //   92: aload_0
        //   93: getfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument:value_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value;
        //   96: invokevirtual 100	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value$Builder;
        //   99: astore 7
        //   101: aload_1
        //   102: getstatic 101	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   105: aload_2
        //   106: invokevirtual 105	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   109: checkcast 19	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value
        //   112: astore 8
        //   114: aload_0
        //   115: aload 8
        //   117: putfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument:value_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value;
        //   120: aload 7
        //   122: ifnull +20 -> 142
        //   125: aload 7
        //   127: aload 8
        //   129: invokevirtual 109	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value$Builder;
        //   132: pop
        //   133: aload_0
        //   134: aload 7
        //   136: invokevirtual 113	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value;
        //   139: putfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument:value_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value;
        //   142: aload_0
        //   143: aload_0
        //   144: getfield 94	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument:bitField0_	I
        //   147: iconst_2
        //   148: ior
        //   149: putfield 94	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument:bitField0_	I
        //   152: goto -119 -> 33
        //   155: aload_0
        //   156: aload_0
        //   157: getfield 94	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument:bitField0_	I
        //   160: iconst_1
        //   161: ior
        //   162: putfield 94	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument:bitField0_	I
        //   165: aload_0
        //   166: aload_1
        //   167: invokevirtual 116	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
        //   170: putfield 118	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument:nameId_	I
        //   173: goto -140 -> 33
        //   176: iconst_1
        //   177: istore 5
        //   179: goto -146 -> 33
        //   182: astore_1
        //   183: goto +29 -> 212
        //   186: astore_1
        //   187: new 63	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   190: astore_2
        //   191: aload_2
        //   192: aload_1
        //   193: invokevirtual 122	java/io/IOException:getMessage	()Ljava/lang/String;
        //   196: invokespecial 125	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
        //   199: aload_2
        //   200: aload_0
        //   201: invokevirtual 129	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
        //   204: athrow
        //   205: astore_1
        //   206: aload_1
        //   207: aload_0
        //   208: invokevirtual 129	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
        //   211: athrow
        //   212: aload 4
        //   214: invokevirtual 132	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
        //   217: aload_0
        //   218: aload_3
        //   219: invokevirtual 138	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   222: putfield 140	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   225: goto +14 -> 239
        //   228: astore_1
        //   229: aload_0
        //   230: aload_3
        //   231: invokevirtual 138	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   234: putfield 140	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   237: aload_1
        //   238: athrow
        //   239: aload_0
        //   240: invokevirtual 143	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument:makeExtensionsImmutable	()V
        //   243: aload_1
        //   244: athrow
        //   245: aload 4
        //   247: invokevirtual 132	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
        //   250: aload_0
        //   251: aload_3
        //   252: invokevirtual 138	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   255: putfield 140	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   258: goto +14 -> 272
        //   261: astore_1
        //   262: aload_0
        //   263: aload_3
        //   264: invokevirtual 138	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   267: putfield 140	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   270: aload_1
        //   271: athrow
        //   272: aload_0
        //   273: invokevirtual 143	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument:makeExtensionsImmutable	()V
        //   276: return
        //   277: astore_2
        //   278: goto -61 -> 217
        //   281: astore_1
        //   282: goto -32 -> 250
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	285	0	this	Argument
        //   0	285	1	paramCodedInputStream	CodedInputStream
        //   0	285	2	paramExtensionRegistryLite	ExtensionRegistryLite
        //   22	242	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
        //   28	218	4	localCodedOutputStream	CodedOutputStream
        //   31	147	5	i	int
        //   42	27	6	j	int
        //   80	55	7	localBuilder	ProtoBuf.Annotation.Argument.Value.Builder
        //   112	16	8	localValue	Value
        // Exception table:
        //   from	to	target	type
        //   38	44	182	finally
        //   63	76	182	finally
        //   82	101	182	finally
        //   101	120	182	finally
        //   125	142	182	finally
        //   142	152	182	finally
        //   155	173	182	finally
        //   187	205	182	finally
        //   206	212	182	finally
        //   38	44	186	java/io/IOException
        //   63	76	186	java/io/IOException
        //   82	101	186	java/io/IOException
        //   101	120	186	java/io/IOException
        //   125	142	186	java/io/IOException
        //   142	152	186	java/io/IOException
        //   155	173	186	java/io/IOException
        //   38	44	205	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   63	76	205	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   82	101	205	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   101	120	205	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   125	142	205	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   142	152	205	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   155	173	205	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   212	217	228	finally
        //   245	250	261	finally
        //   212	217	277	java/io/IOException
        //   245	250	281	java/io/IOException
      }
      
      private Argument(GeneratedMessageLite.Builder paramBuilder)
      {
        super();
        this.unknownFields = paramBuilder.getUnknownFields();
      }
      
      private Argument(boolean paramBoolean)
      {
        this.unknownFields = ByteString.EMPTY;
      }
      
      public static Argument getDefaultInstance()
      {
        return defaultInstance;
      }
      
      private void initFields()
      {
        this.nameId_ = 0;
        this.value_ = Value.getDefaultInstance();
      }
      
      public static Builder newBuilder()
      {
        return Builder.access$3600();
      }
      
      public static Builder newBuilder(Argument paramArgument)
      {
        return newBuilder().mergeFrom(paramArgument);
      }
      
      public Argument getDefaultInstanceForType()
      {
        return defaultInstance;
      }
      
      public int getNameId()
      {
        return this.nameId_;
      }
      
      public Parser<Argument> getParserForType()
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
          i = 0 + CodedOutputStream.computeInt32Size(1, this.nameId_);
        }
        int j = i;
        if ((this.bitField0_ & 0x2) == 2) {
          j = i + CodedOutputStream.computeMessageSize(2, this.value_);
        }
        i = j + this.unknownFields.size();
        this.memoizedSerializedSize = i;
        return i;
      }
      
      public Value getValue()
      {
        return this.value_;
      }
      
      public boolean hasNameId()
      {
        int i = this.bitField0_;
        boolean bool = true;
        if ((i & 0x1) != 1) {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasValue()
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
        if (!hasNameId())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
        if (!hasValue())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
        if (!getValue().isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
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
          paramCodedOutputStream.writeInt32(1, this.nameId_);
        }
        if ((this.bitField0_ & 0x2) == 2) {
          paramCodedOutputStream.writeMessage(2, this.value_);
        }
        paramCodedOutputStream.writeRawBytes(this.unknownFields);
      }
      
      public static final class Builder
        extends GeneratedMessageLite.Builder<ProtoBuf.Annotation.Argument, Builder>
        implements ProtoBuf.Annotation.ArgumentOrBuilder
      {
        private int bitField0_;
        private int nameId_;
        private ProtoBuf.Annotation.Argument.Value value_ = ProtoBuf.Annotation.Argument.Value.getDefaultInstance();
        
        private Builder()
        {
          maybeForceBuilderInitialization();
        }
        
        private static Builder create()
        {
          return new Builder();
        }
        
        private void maybeForceBuilderInitialization() {}
        
        public ProtoBuf.Annotation.Argument build()
        {
          ProtoBuf.Annotation.Argument localArgument = buildPartial();
          if (localArgument.isInitialized()) {
            return localArgument;
          }
          throw newUninitializedMessageException(localArgument);
        }
        
        public ProtoBuf.Annotation.Argument buildPartial()
        {
          ProtoBuf.Annotation.Argument localArgument = new ProtoBuf.Annotation.Argument(this, null);
          int i = this.bitField0_;
          int j = 1;
          if ((i & 0x1) != 1) {
            j = 0;
          }
          ProtoBuf.Annotation.Argument.access$3802(localArgument, this.nameId_);
          int k = j;
          if ((i & 0x2) == 2) {
            k = j | 0x2;
          }
          ProtoBuf.Annotation.Argument.access$3902(localArgument, this.value_);
          ProtoBuf.Annotation.Argument.access$4002(localArgument, k);
          return localArgument;
        }
        
        public Builder clone()
        {
          return create().mergeFrom(buildPartial());
        }
        
        public ProtoBuf.Annotation.Argument getDefaultInstanceForType()
        {
          return ProtoBuf.Annotation.Argument.getDefaultInstance();
        }
        
        public ProtoBuf.Annotation.Argument.Value getValue()
        {
          return this.value_;
        }
        
        public boolean hasNameId()
        {
          int i = this.bitField0_;
          boolean bool = true;
          if ((i & 0x1) != 1) {
            bool = false;
          }
          return bool;
        }
        
        public boolean hasValue()
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
          if (!hasNameId()) {
            return false;
          }
          if (!hasValue()) {
            return false;
          }
          return getValue().isInitialized();
        }
        
        public Builder mergeFrom(ProtoBuf.Annotation.Argument paramArgument)
        {
          if (paramArgument == ProtoBuf.Annotation.Argument.getDefaultInstance()) {
            return this;
          }
          if (paramArgument.hasNameId()) {
            setNameId(paramArgument.getNameId());
          }
          if (paramArgument.hasValue()) {
            mergeValue(paramArgument.getValue());
          }
          setUnknownFields(getUnknownFields().concat(paramArgument.unknownFields));
          return this;
        }
        
        /* Error */
        public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
          throws IOException
        {
          // Byte code:
          //   0: aconst_null
          //   1: astore_3
          //   2: getstatic 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
          //   5: aload_1
          //   6: aload_2
          //   7: invokeinterface 154 3 0
          //   12: checkcast 14	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument
          //   15: astore_1
          //   16: aload_1
          //   17: ifnull +9 -> 26
          //   20: aload_0
          //   21: aload_1
          //   22: invokevirtual 88	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Builder;
          //   25: pop
          //   26: aload_0
          //   27: areturn
          //   28: astore_2
          //   29: aload_3
          //   30: astore_1
          //   31: goto +21 -> 52
          //   34: astore_1
          //   35: aload_1
          //   36: invokevirtual 157	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
          //   39: checkcast 14	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument
          //   42: astore_2
          //   43: aload_1
          //   44: athrow
          //   45: astore_1
          //   46: aload_2
          //   47: astore_3
          //   48: aload_1
          //   49: astore_2
          //   50: aload_3
          //   51: astore_1
          //   52: aload_1
          //   53: ifnull +9 -> 62
          //   56: aload_0
          //   57: aload_1
          //   58: invokevirtual 88	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Builder;
          //   61: pop
          //   62: aload_2
          //   63: athrow
          // Local variable table:
          //   start	length	slot	name	signature
          //   0	64	0	this	Builder
          //   0	64	1	paramCodedInputStream	CodedInputStream
          //   0	64	2	paramExtensionRegistryLite	ExtensionRegistryLite
          //   1	50	3	localExtensionRegistryLite	ExtensionRegistryLite
          // Exception table:
          //   from	to	target	type
          //   2	16	28	finally
          //   35	43	28	finally
          //   2	16	34	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   43	45	45	finally
        }
        
        public Builder mergeValue(ProtoBuf.Annotation.Argument.Value paramValue)
        {
          if (((this.bitField0_ & 0x2) == 2) && (this.value_ != ProtoBuf.Annotation.Argument.Value.getDefaultInstance())) {
            this.value_ = ProtoBuf.Annotation.Argument.Value.newBuilder(this.value_).mergeFrom(paramValue).buildPartial();
          } else {
            this.value_ = paramValue;
          }
          this.bitField0_ |= 0x2;
          return this;
        }
        
        public Builder setNameId(int paramInt)
        {
          this.bitField0_ |= 0x1;
          this.nameId_ = paramInt;
          return this;
        }
      }
      
      public static final class Value
        extends GeneratedMessageLite
        implements ProtoBuf.Annotation.Argument.ValueOrBuilder
      {
        public static Parser<Value> PARSER = new AbstractParser()
        {
          public ProtoBuf.Annotation.Argument.Value parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
            throws InvalidProtocolBufferException
          {
            return new ProtoBuf.Annotation.Argument.Value(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
          }
        };
        private static final Value defaultInstance;
        private ProtoBuf.Annotation annotation_;
        private int arrayDimensionCount_;
        private List<Value> arrayElement_;
        private int bitField0_;
        private int classId_;
        private double doubleValue_;
        private int enumValueId_;
        private int flags_;
        private float floatValue_;
        private long intValue_;
        private byte memoizedIsInitialized = (byte)-1;
        private int memoizedSerializedSize = -1;
        private int stringValue_;
        private Type type_;
        private final ByteString unknownFields;
        
        static
        {
          Value localValue = new Value(true);
          defaultInstance = localValue;
          localValue.initFields();
        }
        
        /* Error */
        private Value(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
          throws InvalidProtocolBufferException
        {
          // Byte code:
          //   0: aload_0
          //   1: invokespecial 77	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite:<init>	()V
          //   4: aload_0
          //   5: iconst_m1
          //   6: i2b
          //   7: putfield 79	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:memoizedIsInitialized	B
          //   10: aload_0
          //   11: iconst_m1
          //   12: putfield 81	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:memoizedSerializedSize	I
          //   15: aload_0
          //   16: invokespecial 70	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:initFields	()V
          //   19: invokestatic 87	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
          //   22: astore_3
          //   23: aload_3
          //   24: iconst_1
          //   25: invokestatic 93	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
          //   28: astore 4
          //   30: iconst_0
          //   31: istore 5
          //   33: iconst_0
          //   34: istore 6
          //   36: iload 5
          //   38: ifne +1046 -> 1084
          //   41: iload 6
          //   43: istore 7
          //   45: iload 6
          //   47: istore 8
          //   49: iload 6
          //   51: istore 9
          //   53: aload_1
          //   54: invokevirtual 99	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
          //   57: istore 10
          //   59: iload 10
          //   61: lookupswitch	default:+107->168, 0:+907->968, 8:+781->842, 16:+736->797, 29:+691->752, 33:+645->706, 40:+599->660, 48:+553->614, 56:+507->568, 66:+341->402, 74:+228->289, 80:+181->242, 88:+134->195
          //   168: iload 6
          //   170: istore 7
          //   172: iload 6
          //   174: istore 8
          //   176: iload 6
          //   178: istore 9
          //   180: aload_0
          //   181: aload_1
          //   182: aload 4
          //   184: aload_2
          //   185: iload 10
          //   187: invokevirtual 103	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
          //   190: istore 11
          //   192: goto +782 -> 974
          //   195: iload 6
          //   197: istore 7
          //   199: iload 6
          //   201: istore 8
          //   203: iload 6
          //   205: istore 9
          //   207: aload_0
          //   208: aload_0
          //   209: getfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:bitField0_	I
          //   212: sipush 256
          //   215: ior
          //   216: putfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:bitField0_	I
          //   219: iload 6
          //   221: istore 7
          //   223: iload 6
          //   225: istore 8
          //   227: iload 6
          //   229: istore 9
          //   231: aload_0
          //   232: aload_1
          //   233: invokevirtual 108	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
          //   236: putfield 110	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:arrayDimensionCount_	I
          //   239: goto -203 -> 36
          //   242: iload 6
          //   244: istore 7
          //   246: iload 6
          //   248: istore 8
          //   250: iload 6
          //   252: istore 9
          //   254: aload_0
          //   255: aload_0
          //   256: getfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:bitField0_	I
          //   259: sipush 512
          //   262: ior
          //   263: putfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:bitField0_	I
          //   266: iload 6
          //   268: istore 7
          //   270: iload 6
          //   272: istore 8
          //   274: iload 6
          //   276: istore 9
          //   278: aload_0
          //   279: aload_1
          //   280: invokevirtual 108	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
          //   283: putfield 112	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:flags_	I
          //   286: goto -250 -> 36
          //   289: iload 6
          //   291: istore 10
          //   293: iload 6
          //   295: sipush 256
          //   298: iand
          //   299: sipush 256
          //   302: if_icmpeq +63 -> 365
          //   305: iload 6
          //   307: istore 7
          //   309: iload 6
          //   311: istore 8
          //   313: iload 6
          //   315: istore 9
          //   317: new 114	java/util/ArrayList
          //   320: astore 12
          //   322: iload 6
          //   324: istore 7
          //   326: iload 6
          //   328: istore 8
          //   330: iload 6
          //   332: istore 9
          //   334: aload 12
          //   336: invokespecial 115	java/util/ArrayList:<init>	()V
          //   339: iload 6
          //   341: istore 7
          //   343: iload 6
          //   345: istore 8
          //   347: iload 6
          //   349: istore 9
          //   351: aload_0
          //   352: aload 12
          //   354: putfield 117	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:arrayElement_	Ljava/util/List;
          //   357: iload 6
          //   359: sipush 256
          //   362: ior
          //   363: istore 10
          //   365: iload 10
          //   367: istore 7
          //   369: iload 10
          //   371: istore 8
          //   373: iload 10
          //   375: istore 9
          //   377: aload_0
          //   378: getfield 117	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:arrayElement_	Ljava/util/List;
          //   381: aload_1
          //   382: getstatic 62	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
          //   385: aload_2
          //   386: invokevirtual 121	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
          //   389: invokeinterface 127 2 0
          //   394: pop
          //   395: iload 10
          //   397: istore 6
          //   399: goto -363 -> 36
          //   402: aconst_null
          //   403: astore 12
          //   405: iload 6
          //   407: istore 7
          //   409: iload 6
          //   411: istore 8
          //   413: iload 6
          //   415: istore 9
          //   417: aload_0
          //   418: getfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:bitField0_	I
          //   421: sipush 128
          //   424: iand
          //   425: sipush 128
          //   428: if_icmpne +24 -> 452
          //   431: iload 6
          //   433: istore 7
          //   435: iload 6
          //   437: istore 8
          //   439: iload 6
          //   441: istore 9
          //   443: aload_0
          //   444: getfield 129	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:annotation_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation;
          //   447: invokevirtual 133	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Builder;
          //   450: astore 12
          //   452: iload 6
          //   454: istore 7
          //   456: iload 6
          //   458: istore 8
          //   460: iload 6
          //   462: istore 9
          //   464: aload_1
          //   465: getstatic 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
          //   468: aload_2
          //   469: invokevirtual 121	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
          //   472: checkcast 8	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation
          //   475: astore 13
          //   477: iload 6
          //   479: istore 7
          //   481: iload 6
          //   483: istore 8
          //   485: iload 6
          //   487: istore 9
          //   489: aload_0
          //   490: aload 13
          //   492: putfield 129	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:annotation_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation;
          //   495: aload 12
          //   497: ifnull +44 -> 541
          //   500: iload 6
          //   502: istore 7
          //   504: iload 6
          //   506: istore 8
          //   508: iload 6
          //   510: istore 9
          //   512: aload 12
          //   514: aload 13
          //   516: invokevirtual 140	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Builder;
          //   519: pop
          //   520: iload 6
          //   522: istore 7
          //   524: iload 6
          //   526: istore 8
          //   528: iload 6
          //   530: istore 9
          //   532: aload_0
          //   533: aload 12
          //   535: invokevirtual 144	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation;
          //   538: putfield 129	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:annotation_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation;
          //   541: iload 6
          //   543: istore 7
          //   545: iload 6
          //   547: istore 8
          //   549: iload 6
          //   551: istore 9
          //   553: aload_0
          //   554: aload_0
          //   555: getfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:bitField0_	I
          //   558: sipush 128
          //   561: ior
          //   562: putfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:bitField0_	I
          //   565: goto -529 -> 36
          //   568: iload 6
          //   570: istore 7
          //   572: iload 6
          //   574: istore 8
          //   576: iload 6
          //   578: istore 9
          //   580: aload_0
          //   581: aload_0
          //   582: getfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:bitField0_	I
          //   585: bipush 64
          //   587: ior
          //   588: putfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:bitField0_	I
          //   591: iload 6
          //   593: istore 7
          //   595: iload 6
          //   597: istore 8
          //   599: iload 6
          //   601: istore 9
          //   603: aload_0
          //   604: aload_1
          //   605: invokevirtual 108	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
          //   608: putfield 146	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:enumValueId_	I
          //   611: goto -575 -> 36
          //   614: iload 6
          //   616: istore 7
          //   618: iload 6
          //   620: istore 8
          //   622: iload 6
          //   624: istore 9
          //   626: aload_0
          //   627: aload_0
          //   628: getfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:bitField0_	I
          //   631: bipush 32
          //   633: ior
          //   634: putfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:bitField0_	I
          //   637: iload 6
          //   639: istore 7
          //   641: iload 6
          //   643: istore 8
          //   645: iload 6
          //   647: istore 9
          //   649: aload_0
          //   650: aload_1
          //   651: invokevirtual 108	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
          //   654: putfield 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:classId_	I
          //   657: goto -621 -> 36
          //   660: iload 6
          //   662: istore 7
          //   664: iload 6
          //   666: istore 8
          //   668: iload 6
          //   670: istore 9
          //   672: aload_0
          //   673: aload_0
          //   674: getfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:bitField0_	I
          //   677: bipush 16
          //   679: ior
          //   680: putfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:bitField0_	I
          //   683: iload 6
          //   685: istore 7
          //   687: iload 6
          //   689: istore 8
          //   691: iload 6
          //   693: istore 9
          //   695: aload_0
          //   696: aload_1
          //   697: invokevirtual 108	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
          //   700: putfield 150	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:stringValue_	I
          //   703: goto -667 -> 36
          //   706: iload 6
          //   708: istore 7
          //   710: iload 6
          //   712: istore 8
          //   714: iload 6
          //   716: istore 9
          //   718: aload_0
          //   719: aload_0
          //   720: getfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:bitField0_	I
          //   723: bipush 8
          //   725: ior
          //   726: putfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:bitField0_	I
          //   729: iload 6
          //   731: istore 7
          //   733: iload 6
          //   735: istore 8
          //   737: iload 6
          //   739: istore 9
          //   741: aload_0
          //   742: aload_1
          //   743: invokevirtual 154	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readDouble	()D
          //   746: putfield 156	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:doubleValue_	D
          //   749: goto -713 -> 36
          //   752: iload 6
          //   754: istore 7
          //   756: iload 6
          //   758: istore 8
          //   760: iload 6
          //   762: istore 9
          //   764: aload_0
          //   765: aload_0
          //   766: getfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:bitField0_	I
          //   769: iconst_4
          //   770: ior
          //   771: putfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:bitField0_	I
          //   774: iload 6
          //   776: istore 7
          //   778: iload 6
          //   780: istore 8
          //   782: iload 6
          //   784: istore 9
          //   786: aload_0
          //   787: aload_1
          //   788: invokevirtual 160	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readFloat	()F
          //   791: putfield 162	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:floatValue_	F
          //   794: goto -758 -> 36
          //   797: iload 6
          //   799: istore 7
          //   801: iload 6
          //   803: istore 8
          //   805: iload 6
          //   807: istore 9
          //   809: aload_0
          //   810: aload_0
          //   811: getfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:bitField0_	I
          //   814: iconst_2
          //   815: ior
          //   816: putfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:bitField0_	I
          //   819: iload 6
          //   821: istore 7
          //   823: iload 6
          //   825: istore 8
          //   827: iload 6
          //   829: istore 9
          //   831: aload_0
          //   832: aload_1
          //   833: invokevirtual 166	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readSInt64	()J
          //   836: putfield 168	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:intValue_	J
          //   839: goto -803 -> 36
          //   842: iload 6
          //   844: istore 7
          //   846: iload 6
          //   848: istore 8
          //   850: iload 6
          //   852: istore 9
          //   854: aload_1
          //   855: invokevirtual 171	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readEnum	()I
          //   858: istore 14
          //   860: iload 6
          //   862: istore 7
          //   864: iload 6
          //   866: istore 8
          //   868: iload 6
          //   870: istore 9
          //   872: iload 14
          //   874: invokestatic 175	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value$Type:valueOf	(I)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value$Type;
          //   877: astore 12
          //   879: aload 12
          //   881: ifnonnull +44 -> 925
          //   884: iload 6
          //   886: istore 7
          //   888: iload 6
          //   890: istore 8
          //   892: iload 6
          //   894: istore 9
          //   896: aload 4
          //   898: iload 10
          //   900: invokevirtual 179	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:writeRawVarint32	(I)V
          //   903: iload 6
          //   905: istore 7
          //   907: iload 6
          //   909: istore 8
          //   911: iload 6
          //   913: istore 9
          //   915: aload 4
          //   917: iload 14
          //   919: invokevirtual 179	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:writeRawVarint32	(I)V
          //   922: goto -886 -> 36
          //   925: iload 6
          //   927: istore 7
          //   929: iload 6
          //   931: istore 8
          //   933: iload 6
          //   935: istore 9
          //   937: aload_0
          //   938: aload_0
          //   939: getfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:bitField0_	I
          //   942: iconst_1
          //   943: ior
          //   944: putfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:bitField0_	I
          //   947: iload 6
          //   949: istore 7
          //   951: iload 6
          //   953: istore 8
          //   955: iload 6
          //   957: istore 9
          //   959: aload_0
          //   960: aload 12
          //   962: putfield 181	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:type_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value$Type;
          //   965: goto -929 -> 36
          //   968: iconst_1
          //   969: istore 5
          //   971: goto -935 -> 36
          //   974: iload 11
          //   976: ifne -940 -> 36
          //   979: goto -11 -> 968
          //   982: astore_1
          //   983: goto +45 -> 1028
          //   986: astore_2
          //   987: iload 8
          //   989: istore 7
          //   991: new 74	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   994: astore_1
          //   995: iload 8
          //   997: istore 7
          //   999: aload_1
          //   1000: aload_2
          //   1001: invokevirtual 185	java/io/IOException:getMessage	()Ljava/lang/String;
          //   1004: invokespecial 188	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
          //   1007: iload 8
          //   1009: istore 7
          //   1011: aload_1
          //   1012: aload_0
          //   1013: invokevirtual 192	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
          //   1016: athrow
          //   1017: astore_1
          //   1018: iload 9
          //   1020: istore 7
          //   1022: aload_1
          //   1023: aload_0
          //   1024: invokevirtual 192	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
          //   1027: athrow
          //   1028: iload 7
          //   1030: sipush 256
          //   1033: iand
          //   1034: sipush 256
          //   1037: if_icmpne +14 -> 1051
          //   1040: aload_0
          //   1041: aload_0
          //   1042: getfield 117	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:arrayElement_	Ljava/util/List;
          //   1045: invokestatic 198	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
          //   1048: putfield 117	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:arrayElement_	Ljava/util/List;
          //   1051: aload 4
          //   1053: invokevirtual 201	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
          //   1056: aload_0
          //   1057: aload_3
          //   1058: invokevirtual 207	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
          //   1061: putfield 209	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
          //   1064: goto +14 -> 1078
          //   1067: astore_1
          //   1068: aload_0
          //   1069: aload_3
          //   1070: invokevirtual 207	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
          //   1073: putfield 209	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
          //   1076: aload_1
          //   1077: athrow
          //   1078: aload_0
          //   1079: invokevirtual 212	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:makeExtensionsImmutable	()V
          //   1082: aload_1
          //   1083: athrow
          //   1084: iload 6
          //   1086: sipush 256
          //   1089: iand
          //   1090: sipush 256
          //   1093: if_icmpne +14 -> 1107
          //   1096: aload_0
          //   1097: aload_0
          //   1098: getfield 117	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:arrayElement_	Ljava/util/List;
          //   1101: invokestatic 198	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
          //   1104: putfield 117	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:arrayElement_	Ljava/util/List;
          //   1107: aload 4
          //   1109: invokevirtual 201	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
          //   1112: aload_0
          //   1113: aload_3
          //   1114: invokevirtual 207	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
          //   1117: putfield 209	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
          //   1120: goto +14 -> 1134
          //   1123: astore_1
          //   1124: aload_0
          //   1125: aload_3
          //   1126: invokevirtual 207	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
          //   1129: putfield 209	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
          //   1132: aload_1
          //   1133: athrow
          //   1134: aload_0
          //   1135: invokevirtual 212	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:makeExtensionsImmutable	()V
          //   1138: return
          //   1139: astore_2
          //   1140: goto -84 -> 1056
          //   1143: astore_1
          //   1144: goto -32 -> 1112
          // Local variable table:
          //   start	length	slot	name	signature
          //   0	1147	0	this	Value
          //   0	1147	1	paramCodedInputStream	CodedInputStream
          //   0	1147	2	paramExtensionRegistryLite	ExtensionRegistryLite
          //   22	1104	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
          //   28	1080	4	localCodedOutputStream	CodedOutputStream
          //   31	939	5	i	int
          //   34	1056	6	j	int
          //   43	991	7	k	int
          //   47	961	8	m	int
          //   51	968	9	n	int
          //   57	842	10	i1	int
          //   190	785	11	bool	boolean
          //   320	641	12	localObject	Object
          //   475	40	13	localAnnotation	ProtoBuf.Annotation
          //   858	60	14	i2	int
          // Exception table:
          //   from	to	target	type
          //   53	59	982	finally
          //   180	192	982	finally
          //   207	219	982	finally
          //   231	239	982	finally
          //   254	266	982	finally
          //   278	286	982	finally
          //   317	322	982	finally
          //   334	339	982	finally
          //   351	357	982	finally
          //   377	395	982	finally
          //   417	431	982	finally
          //   443	452	982	finally
          //   464	477	982	finally
          //   489	495	982	finally
          //   512	520	982	finally
          //   532	541	982	finally
          //   553	565	982	finally
          //   580	591	982	finally
          //   603	611	982	finally
          //   626	637	982	finally
          //   649	657	982	finally
          //   672	683	982	finally
          //   695	703	982	finally
          //   718	729	982	finally
          //   741	749	982	finally
          //   764	774	982	finally
          //   786	794	982	finally
          //   809	819	982	finally
          //   831	839	982	finally
          //   854	860	982	finally
          //   872	879	982	finally
          //   896	903	982	finally
          //   915	922	982	finally
          //   937	947	982	finally
          //   959	965	982	finally
          //   991	995	982	finally
          //   999	1007	982	finally
          //   1011	1017	982	finally
          //   1022	1028	982	finally
          //   53	59	986	java/io/IOException
          //   180	192	986	java/io/IOException
          //   207	219	986	java/io/IOException
          //   231	239	986	java/io/IOException
          //   254	266	986	java/io/IOException
          //   278	286	986	java/io/IOException
          //   317	322	986	java/io/IOException
          //   334	339	986	java/io/IOException
          //   351	357	986	java/io/IOException
          //   377	395	986	java/io/IOException
          //   417	431	986	java/io/IOException
          //   443	452	986	java/io/IOException
          //   464	477	986	java/io/IOException
          //   489	495	986	java/io/IOException
          //   512	520	986	java/io/IOException
          //   532	541	986	java/io/IOException
          //   553	565	986	java/io/IOException
          //   580	591	986	java/io/IOException
          //   603	611	986	java/io/IOException
          //   626	637	986	java/io/IOException
          //   649	657	986	java/io/IOException
          //   672	683	986	java/io/IOException
          //   695	703	986	java/io/IOException
          //   718	729	986	java/io/IOException
          //   741	749	986	java/io/IOException
          //   764	774	986	java/io/IOException
          //   786	794	986	java/io/IOException
          //   809	819	986	java/io/IOException
          //   831	839	986	java/io/IOException
          //   854	860	986	java/io/IOException
          //   872	879	986	java/io/IOException
          //   896	903	986	java/io/IOException
          //   915	922	986	java/io/IOException
          //   937	947	986	java/io/IOException
          //   959	965	986	java/io/IOException
          //   53	59	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   180	192	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   207	219	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   231	239	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   254	266	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   278	286	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   317	322	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   334	339	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   351	357	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   377	395	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   417	431	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   443	452	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   464	477	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   489	495	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   512	520	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   532	541	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   553	565	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   580	591	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   603	611	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   626	637	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   649	657	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   672	683	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   695	703	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   718	729	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   741	749	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   764	774	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   786	794	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   809	819	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   831	839	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   854	860	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   872	879	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   896	903	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   915	922	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   937	947	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   959	965	1017	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   1051	1056	1067	finally
          //   1107	1112	1123	finally
          //   1051	1056	1139	java/io/IOException
          //   1107	1112	1143	java/io/IOException
        }
        
        private Value(GeneratedMessageLite.Builder paramBuilder)
        {
          super();
          this.unknownFields = paramBuilder.getUnknownFields();
        }
        
        private Value(boolean paramBoolean)
        {
          this.unknownFields = ByteString.EMPTY;
        }
        
        public static Value getDefaultInstance()
        {
          return defaultInstance;
        }
        
        private void initFields()
        {
          this.type_ = Type.BYTE;
          this.intValue_ = 0L;
          this.floatValue_ = 0.0F;
          this.doubleValue_ = 0.0D;
          this.stringValue_ = 0;
          this.classId_ = 0;
          this.enumValueId_ = 0;
          this.annotation_ = ProtoBuf.Annotation.getDefaultInstance();
          this.arrayElement_ = Collections.emptyList();
          this.arrayDimensionCount_ = 0;
          this.flags_ = 0;
        }
        
        public static Builder newBuilder()
        {
          return Builder.access$2100();
        }
        
        public static Builder newBuilder(Value paramValue)
        {
          return newBuilder().mergeFrom(paramValue);
        }
        
        public ProtoBuf.Annotation getAnnotation()
        {
          return this.annotation_;
        }
        
        public int getArrayDimensionCount()
        {
          return this.arrayDimensionCount_;
        }
        
        public Value getArrayElement(int paramInt)
        {
          return (Value)this.arrayElement_.get(paramInt);
        }
        
        public int getArrayElementCount()
        {
          return this.arrayElement_.size();
        }
        
        public List<Value> getArrayElementList()
        {
          return this.arrayElement_;
        }
        
        public int getClassId()
        {
          return this.classId_;
        }
        
        public Value getDefaultInstanceForType()
        {
          return defaultInstance;
        }
        
        public double getDoubleValue()
        {
          return this.doubleValue_;
        }
        
        public int getEnumValueId()
        {
          return this.enumValueId_;
        }
        
        public int getFlags()
        {
          return this.flags_;
        }
        
        public float getFloatValue()
        {
          return this.floatValue_;
        }
        
        public long getIntValue()
        {
          return this.intValue_;
        }
        
        public Parser<Value> getParserForType()
        {
          return PARSER;
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
            k = CodedOutputStream.computeEnumSize(1, this.type_.getNumber()) + 0;
          } else {
            k = 0;
          }
          i = k;
          if ((this.bitField0_ & 0x2) == 2) {
            i = k + CodedOutputStream.computeSInt64Size(2, this.intValue_);
          }
          int k = i;
          if ((this.bitField0_ & 0x4) == 4) {
            k = i + CodedOutputStream.computeFloatSize(3, this.floatValue_);
          }
          i = k;
          if ((this.bitField0_ & 0x8) == 8) {
            i = k + CodedOutputStream.computeDoubleSize(4, this.doubleValue_);
          }
          k = i;
          if ((this.bitField0_ & 0x10) == 16) {
            k = i + CodedOutputStream.computeInt32Size(5, this.stringValue_);
          }
          i = k;
          if ((this.bitField0_ & 0x20) == 32) {
            i = k + CodedOutputStream.computeInt32Size(6, this.classId_);
          }
          k = i;
          if ((this.bitField0_ & 0x40) == 64) {
            k = i + CodedOutputStream.computeInt32Size(7, this.enumValueId_);
          }
          i = k;
          int m = j;
          if ((this.bitField0_ & 0x80) == 128) {
            i = k + CodedOutputStream.computeMessageSize(8, this.annotation_);
          }
          for (m = j; m < this.arrayElement_.size(); m++) {
            i += CodedOutputStream.computeMessageSize(9, (MessageLite)this.arrayElement_.get(m));
          }
          k = i;
          if ((this.bitField0_ & 0x200) == 512) {
            k = i + CodedOutputStream.computeInt32Size(10, this.flags_);
          }
          i = k;
          if ((this.bitField0_ & 0x100) == 256) {
            i = k + CodedOutputStream.computeInt32Size(11, this.arrayDimensionCount_);
          }
          i += this.unknownFields.size();
          this.memoizedSerializedSize = i;
          return i;
        }
        
        public int getStringValue()
        {
          return this.stringValue_;
        }
        
        public Type getType()
        {
          return this.type_;
        }
        
        public boolean hasAnnotation()
        {
          boolean bool;
          if ((this.bitField0_ & 0x80) == 128) {
            bool = true;
          } else {
            bool = false;
          }
          return bool;
        }
        
        public boolean hasArrayDimensionCount()
        {
          boolean bool;
          if ((this.bitField0_ & 0x100) == 256) {
            bool = true;
          } else {
            bool = false;
          }
          return bool;
        }
        
        public boolean hasClassId()
        {
          boolean bool;
          if ((this.bitField0_ & 0x20) == 32) {
            bool = true;
          } else {
            bool = false;
          }
          return bool;
        }
        
        public boolean hasDoubleValue()
        {
          boolean bool;
          if ((this.bitField0_ & 0x8) == 8) {
            bool = true;
          } else {
            bool = false;
          }
          return bool;
        }
        
        public boolean hasEnumValueId()
        {
          boolean bool;
          if ((this.bitField0_ & 0x40) == 64) {
            bool = true;
          } else {
            bool = false;
          }
          return bool;
        }
        
        public boolean hasFlags()
        {
          boolean bool;
          if ((this.bitField0_ & 0x200) == 512) {
            bool = true;
          } else {
            bool = false;
          }
          return bool;
        }
        
        public boolean hasFloatValue()
        {
          boolean bool;
          if ((this.bitField0_ & 0x4) == 4) {
            bool = true;
          } else {
            bool = false;
          }
          return bool;
        }
        
        public boolean hasIntValue()
        {
          boolean bool;
          if ((this.bitField0_ & 0x2) == 2) {
            bool = true;
          } else {
            bool = false;
          }
          return bool;
        }
        
        public boolean hasStringValue()
        {
          boolean bool;
          if ((this.bitField0_ & 0x10) == 16) {
            bool = true;
          } else {
            bool = false;
          }
          return bool;
        }
        
        public boolean hasType()
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
          if ((hasAnnotation()) && (!getAnnotation().isInitialized()))
          {
            this.memoizedIsInitialized = ((byte)0);
            return false;
          }
          for (i = 0; i < getArrayElementCount(); i++) {
            if (!getArrayElement(i).isInitialized())
            {
              this.memoizedIsInitialized = ((byte)0);
              return false;
            }
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
            paramCodedOutputStream.writeEnum(1, this.type_.getNumber());
          }
          if ((this.bitField0_ & 0x2) == 2) {
            paramCodedOutputStream.writeSInt64(2, this.intValue_);
          }
          if ((this.bitField0_ & 0x4) == 4) {
            paramCodedOutputStream.writeFloat(3, this.floatValue_);
          }
          if ((this.bitField0_ & 0x8) == 8) {
            paramCodedOutputStream.writeDouble(4, this.doubleValue_);
          }
          if ((this.bitField0_ & 0x10) == 16) {
            paramCodedOutputStream.writeInt32(5, this.stringValue_);
          }
          if ((this.bitField0_ & 0x20) == 32) {
            paramCodedOutputStream.writeInt32(6, this.classId_);
          }
          if ((this.bitField0_ & 0x40) == 64) {
            paramCodedOutputStream.writeInt32(7, this.enumValueId_);
          }
          if ((this.bitField0_ & 0x80) == 128) {
            paramCodedOutputStream.writeMessage(8, this.annotation_);
          }
          for (int i = 0; i < this.arrayElement_.size(); i++) {
            paramCodedOutputStream.writeMessage(9, (MessageLite)this.arrayElement_.get(i));
          }
          if ((this.bitField0_ & 0x200) == 512) {
            paramCodedOutputStream.writeInt32(10, this.flags_);
          }
          if ((this.bitField0_ & 0x100) == 256) {
            paramCodedOutputStream.writeInt32(11, this.arrayDimensionCount_);
          }
          paramCodedOutputStream.writeRawBytes(this.unknownFields);
        }
        
        public static final class Builder
          extends GeneratedMessageLite.Builder<ProtoBuf.Annotation.Argument.Value, Builder>
          implements ProtoBuf.Annotation.Argument.ValueOrBuilder
        {
          private ProtoBuf.Annotation annotation_ = ProtoBuf.Annotation.getDefaultInstance();
          private int arrayDimensionCount_;
          private List<ProtoBuf.Annotation.Argument.Value> arrayElement_ = Collections.emptyList();
          private int bitField0_;
          private int classId_;
          private double doubleValue_;
          private int enumValueId_;
          private int flags_;
          private float floatValue_;
          private long intValue_;
          private int stringValue_;
          private ProtoBuf.Annotation.Argument.Value.Type type_ = ProtoBuf.Annotation.Argument.Value.Type.BYTE;
          
          private Builder()
          {
            maybeForceBuilderInitialization();
          }
          
          private static Builder create()
          {
            return new Builder();
          }
          
          private void ensureArrayElementIsMutable()
          {
            if ((this.bitField0_ & 0x100) != 256)
            {
              this.arrayElement_ = new ArrayList(this.arrayElement_);
              this.bitField0_ |= 0x100;
            }
          }
          
          private void maybeForceBuilderInitialization() {}
          
          public ProtoBuf.Annotation.Argument.Value build()
          {
            ProtoBuf.Annotation.Argument.Value localValue = buildPartial();
            if (localValue.isInitialized()) {
              return localValue;
            }
            throw newUninitializedMessageException(localValue);
          }
          
          public ProtoBuf.Annotation.Argument.Value buildPartial()
          {
            ProtoBuf.Annotation.Argument.Value localValue = new ProtoBuf.Annotation.Argument.Value(this, null);
            int i = this.bitField0_;
            int j = 1;
            if ((i & 0x1) != 1) {
              j = 0;
            }
            ProtoBuf.Annotation.Argument.Value.access$2302(localValue, this.type_);
            int k = j;
            if ((i & 0x2) == 2) {
              k = j | 0x2;
            }
            ProtoBuf.Annotation.Argument.Value.access$2402(localValue, this.intValue_);
            j = k;
            if ((i & 0x4) == 4) {
              j = k | 0x4;
            }
            ProtoBuf.Annotation.Argument.Value.access$2502(localValue, this.floatValue_);
            int m = j;
            if ((i & 0x8) == 8) {
              m = j | 0x8;
            }
            ProtoBuf.Annotation.Argument.Value.access$2602(localValue, this.doubleValue_);
            k = m;
            if ((i & 0x10) == 16) {
              k = m | 0x10;
            }
            ProtoBuf.Annotation.Argument.Value.access$2702(localValue, this.stringValue_);
            j = k;
            if ((i & 0x20) == 32) {
              j = k | 0x20;
            }
            ProtoBuf.Annotation.Argument.Value.access$2802(localValue, this.classId_);
            k = j;
            if ((i & 0x40) == 64) {
              k = j | 0x40;
            }
            ProtoBuf.Annotation.Argument.Value.access$2902(localValue, this.enumValueId_);
            j = k;
            if ((i & 0x80) == 128) {
              j = k | 0x80;
            }
            ProtoBuf.Annotation.Argument.Value.access$3002(localValue, this.annotation_);
            if ((this.bitField0_ & 0x100) == 256)
            {
              this.arrayElement_ = Collections.unmodifiableList(this.arrayElement_);
              this.bitField0_ &= 0xFEFF;
            }
            ProtoBuf.Annotation.Argument.Value.access$3102(localValue, this.arrayElement_);
            k = j;
            if ((i & 0x200) == 512) {
              k = j | 0x100;
            }
            ProtoBuf.Annotation.Argument.Value.access$3202(localValue, this.arrayDimensionCount_);
            j = k;
            if ((i & 0x400) == 1024) {
              j = k | 0x200;
            }
            ProtoBuf.Annotation.Argument.Value.access$3302(localValue, this.flags_);
            ProtoBuf.Annotation.Argument.Value.access$3402(localValue, j);
            return localValue;
          }
          
          public Builder clone()
          {
            return create().mergeFrom(buildPartial());
          }
          
          public ProtoBuf.Annotation getAnnotation()
          {
            return this.annotation_;
          }
          
          public ProtoBuf.Annotation.Argument.Value getArrayElement(int paramInt)
          {
            return (ProtoBuf.Annotation.Argument.Value)this.arrayElement_.get(paramInt);
          }
          
          public int getArrayElementCount()
          {
            return this.arrayElement_.size();
          }
          
          public ProtoBuf.Annotation.Argument.Value getDefaultInstanceForType()
          {
            return ProtoBuf.Annotation.Argument.Value.getDefaultInstance();
          }
          
          public boolean hasAnnotation()
          {
            boolean bool;
            if ((this.bitField0_ & 0x80) == 128) {
              bool = true;
            } else {
              bool = false;
            }
            return bool;
          }
          
          public final boolean isInitialized()
          {
            if ((hasAnnotation()) && (!getAnnotation().isInitialized())) {
              return false;
            }
            for (int i = 0; i < getArrayElementCount(); i++) {
              if (!getArrayElement(i).isInitialized()) {
                return false;
              }
            }
            return true;
          }
          
          public Builder mergeAnnotation(ProtoBuf.Annotation paramAnnotation)
          {
            if (((this.bitField0_ & 0x80) == 128) && (this.annotation_ != ProtoBuf.Annotation.getDefaultInstance())) {
              this.annotation_ = ProtoBuf.Annotation.newBuilder(this.annotation_).mergeFrom(paramAnnotation).buildPartial();
            } else {
              this.annotation_ = paramAnnotation;
            }
            this.bitField0_ |= 0x80;
            return this;
          }
          
          public Builder mergeFrom(ProtoBuf.Annotation.Argument.Value paramValue)
          {
            if (paramValue == ProtoBuf.Annotation.Argument.Value.getDefaultInstance()) {
              return this;
            }
            if (paramValue.hasType()) {
              setType(paramValue.getType());
            }
            if (paramValue.hasIntValue()) {
              setIntValue(paramValue.getIntValue());
            }
            if (paramValue.hasFloatValue()) {
              setFloatValue(paramValue.getFloatValue());
            }
            if (paramValue.hasDoubleValue()) {
              setDoubleValue(paramValue.getDoubleValue());
            }
            if (paramValue.hasStringValue()) {
              setStringValue(paramValue.getStringValue());
            }
            if (paramValue.hasClassId()) {
              setClassId(paramValue.getClassId());
            }
            if (paramValue.hasEnumValueId()) {
              setEnumValueId(paramValue.getEnumValueId());
            }
            if (paramValue.hasAnnotation()) {
              mergeAnnotation(paramValue.getAnnotation());
            }
            if (!paramValue.arrayElement_.isEmpty()) {
              if (this.arrayElement_.isEmpty())
              {
                this.arrayElement_ = paramValue.arrayElement_;
                this.bitField0_ &= 0xFEFF;
              }
              else
              {
                ensureArrayElementIsMutable();
                this.arrayElement_.addAll(paramValue.arrayElement_);
              }
            }
            if (paramValue.hasArrayDimensionCount()) {
              setArrayDimensionCount(paramValue.getArrayDimensionCount());
            }
            if (paramValue.hasFlags()) {
              setFlags(paramValue.getFlags());
            }
            setUnknownFields(getUnknownFields().concat(paramValue.unknownFields));
            return this;
          }
          
          /* Error */
          public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
            throws IOException
          {
            // Byte code:
            //   0: aconst_null
            //   1: astore_3
            //   2: getstatic 353	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
            //   5: aload_1
            //   6: aload_2
            //   7: invokeinterface 359 3 0
            //   12: checkcast 17	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value
            //   15: astore_1
            //   16: aload_1
            //   17: ifnull +9 -> 26
            //   20: aload_0
            //   21: aload_1
            //   22: invokevirtual 175	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value$Builder;
            //   25: pop
            //   26: aload_0
            //   27: areturn
            //   28: astore_2
            //   29: aload_3
            //   30: astore_1
            //   31: goto +15 -> 46
            //   34: astore_2
            //   35: aload_2
            //   36: invokevirtual 362	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
            //   39: checkcast 17	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value
            //   42: astore_1
            //   43: aload_2
            //   44: athrow
            //   45: astore_2
            //   46: aload_1
            //   47: ifnull +9 -> 56
            //   50: aload_0
            //   51: aload_1
            //   52: invokevirtual 175	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Argument$Value$Builder;
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
          
          public Builder setArrayDimensionCount(int paramInt)
          {
            this.bitField0_ |= 0x200;
            this.arrayDimensionCount_ = paramInt;
            return this;
          }
          
          public Builder setClassId(int paramInt)
          {
            this.bitField0_ |= 0x20;
            this.classId_ = paramInt;
            return this;
          }
          
          public Builder setDoubleValue(double paramDouble)
          {
            this.bitField0_ |= 0x8;
            this.doubleValue_ = paramDouble;
            return this;
          }
          
          public Builder setEnumValueId(int paramInt)
          {
            this.bitField0_ |= 0x40;
            this.enumValueId_ = paramInt;
            return this;
          }
          
          public Builder setFlags(int paramInt)
          {
            this.bitField0_ |= 0x400;
            this.flags_ = paramInt;
            return this;
          }
          
          public Builder setFloatValue(float paramFloat)
          {
            this.bitField0_ |= 0x4;
            this.floatValue_ = paramFloat;
            return this;
          }
          
          public Builder setIntValue(long paramLong)
          {
            this.bitField0_ |= 0x2;
            this.intValue_ = paramLong;
            return this;
          }
          
          public Builder setStringValue(int paramInt)
          {
            this.bitField0_ |= 0x10;
            this.stringValue_ = paramInt;
            return this;
          }
          
          public Builder setType(ProtoBuf.Annotation.Argument.Value.Type paramType)
          {
            if (paramType != null)
            {
              this.bitField0_ |= 0x1;
              this.type_ = paramType;
              return this;
            }
            throw null;
          }
        }
        
        public static enum Type
          implements Internal.EnumLite
        {
          private static Internal.EnumLiteMap<Type> internalValueMap = new Internal.EnumLiteMap()
          {
            public ProtoBuf.Annotation.Argument.Value.Type findValueByNumber(int paramAnonymousInt)
            {
              return ProtoBuf.Annotation.Argument.Value.Type.valueOf(paramAnonymousInt);
            }
          };
          private final int value;
          
          static
          {
            INT = new Type("INT", 3, 3, 3);
            LONG = new Type("LONG", 4, 4, 4);
            FLOAT = new Type("FLOAT", 5, 5, 5);
            DOUBLE = new Type("DOUBLE", 6, 6, 6);
            BOOLEAN = new Type("BOOLEAN", 7, 7, 7);
            STRING = new Type("STRING", 8, 8, 8);
            CLASS = new Type("CLASS", 9, 9, 9);
            ENUM = new Type("ENUM", 10, 10, 10);
            ANNOTATION = new Type("ANNOTATION", 11, 11, 11);
            Type localType = new Type("ARRAY", 12, 12, 12);
            ARRAY = localType;
            $VALUES = new Type[] { BYTE, CHAR, SHORT, INT, LONG, FLOAT, DOUBLE, BOOLEAN, STRING, CLASS, ENUM, ANNOTATION, localType };
          }
          
          private Type(int paramInt1, int paramInt2)
          {
            this.value = paramInt2;
          }
          
          public static Type valueOf(int paramInt)
          {
            switch (paramInt)
            {
            default: 
              return null;
            case 12: 
              return ARRAY;
            case 11: 
              return ANNOTATION;
            case 10: 
              return ENUM;
            case 9: 
              return CLASS;
            case 8: 
              return STRING;
            case 7: 
              return BOOLEAN;
            case 6: 
              return DOUBLE;
            case 5: 
              return FLOAT;
            case 4: 
              return LONG;
            case 3: 
              return INT;
            case 2: 
              return SHORT;
            case 1: 
              return CHAR;
            }
            return BYTE;
          }
          
          public final int getNumber()
          {
            return this.value;
          }
        }
      }
    }
    
    public static final class Builder
      extends GeneratedMessageLite.Builder<ProtoBuf.Annotation, Builder>
      implements ProtoBuf.AnnotationOrBuilder
    {
      private List<ProtoBuf.Annotation.Argument> argument_ = Collections.emptyList();
      private int bitField0_;
      private int id_;
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void ensureArgumentIsMutable()
      {
        if ((this.bitField0_ & 0x2) != 2)
        {
          this.argument_ = new ArrayList(this.argument_);
          this.bitField0_ |= 0x2;
        }
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public ProtoBuf.Annotation build()
      {
        ProtoBuf.Annotation localAnnotation = buildPartial();
        if (localAnnotation.isInitialized()) {
          return localAnnotation;
        }
        throw newUninitializedMessageException(localAnnotation);
      }
      
      public ProtoBuf.Annotation buildPartial()
      {
        ProtoBuf.Annotation localAnnotation = new ProtoBuf.Annotation(this, null);
        int i = this.bitField0_;
        int j = 1;
        if ((i & 0x1) != 1) {
          j = 0;
        }
        ProtoBuf.Annotation.access$4402(localAnnotation, this.id_);
        if ((this.bitField0_ & 0x2) == 2)
        {
          this.argument_ = Collections.unmodifiableList(this.argument_);
          this.bitField0_ &= 0xFFFFFFFD;
        }
        ProtoBuf.Annotation.access$4502(localAnnotation, this.argument_);
        ProtoBuf.Annotation.access$4602(localAnnotation, j);
        return localAnnotation;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public ProtoBuf.Annotation.Argument getArgument(int paramInt)
      {
        return (ProtoBuf.Annotation.Argument)this.argument_.get(paramInt);
      }
      
      public int getArgumentCount()
      {
        return this.argument_.size();
      }
      
      public ProtoBuf.Annotation getDefaultInstanceForType()
      {
        return ProtoBuf.Annotation.getDefaultInstance();
      }
      
      public boolean hasId()
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
        if (!hasId()) {
          return false;
        }
        for (int i = 0; i < getArgumentCount(); i++) {
          if (!getArgument(i).isInitialized()) {
            return false;
          }
        }
        return true;
      }
      
      public Builder mergeFrom(ProtoBuf.Annotation paramAnnotation)
      {
        if (paramAnnotation == ProtoBuf.Annotation.getDefaultInstance()) {
          return this;
        }
        if (paramAnnotation.hasId()) {
          setId(paramAnnotation.getId());
        }
        if (!paramAnnotation.argument_.isEmpty()) {
          if (this.argument_.isEmpty())
          {
            this.argument_ = paramAnnotation.argument_;
            this.bitField0_ &= 0xFFFFFFFD;
          }
          else
          {
            ensureArgumentIsMutable();
            this.argument_.addAll(paramAnnotation.argument_);
          }
        }
        setUnknownFields(getUnknownFields().concat(paramAnnotation.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 176	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 182 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_1
        //   29: aload_3
        //   30: astore_2
        //   31: aload_1
        //   32: astore_3
        //   33: goto +19 -> 52
        //   36: astore_2
        //   37: aload_2
        //   38: invokevirtual 185	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   41: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation
        //   44: astore_1
        //   45: aload_2
        //   46: athrow
        //   47: astore_2
        //   48: aload_2
        //   49: astore_3
        //   50: aload_1
        //   51: astore_2
        //   52: aload_2
        //   53: ifnull +9 -> 62
        //   56: aload_0
        //   57: aload_2
        //   58: invokevirtual 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation$Builder;
        //   61: pop
        //   62: aload_3
        //   63: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	64	0	this	Builder
        //   0	64	1	paramCodedInputStream	CodedInputStream
        //   0	64	2	paramExtensionRegistryLite	ExtensionRegistryLite
        //   1	62	3	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   2	16	28	finally
        //   37	45	28	finally
        //   2	16	36	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   45	47	47	finally
      }
      
      public Builder setId(int paramInt)
      {
        this.bitField0_ |= 0x1;
        this.id_ = paramInt;
        return this;
      }
    }
  }
  
  public static final class Class
    extends GeneratedMessageLite.ExtendableMessage<Class>
    implements ProtoBuf.ClassOrBuilder
  {
    public static Parser<Class> PARSER = new AbstractParser()
    {
      public ProtoBuf.Class parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new ProtoBuf.Class(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final Class defaultInstance;
    private int bitField0_;
    private int companionObjectName_;
    private List<ProtoBuf.Constructor> constructor_;
    private List<ProtoBuf.EnumEntry> enumEntry_;
    private int flags_;
    private int fqName_;
    private List<ProtoBuf.Function> function_;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private int nestedClassNameMemoizedSerializedSize = -1;
    private List<Integer> nestedClassName_;
    private List<ProtoBuf.Property> property_;
    private int sealedSubclassFqNameMemoizedSerializedSize = -1;
    private List<Integer> sealedSubclassFqName_;
    private int supertypeIdMemoizedSerializedSize = -1;
    private List<Integer> supertypeId_;
    private List<ProtoBuf.Type> supertype_;
    private List<ProtoBuf.TypeAlias> typeAlias_;
    private List<ProtoBuf.TypeParameter> typeParameter_;
    private ProtoBuf.TypeTable typeTable_;
    private final ByteString unknownFields;
    private ProtoBuf.VersionRequirementTable versionRequirementTable_;
    private List<Integer> versionRequirement_;
    
    static
    {
      Class localClass = new Class(true);
      defaultInstance = localClass;
      localClass.initFields();
    }
    
    /* Error */
    private Class(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 84	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite$ExtendableMessage:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: putfield 86	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:supertypeIdMemoizedSerializedSize	I
      //   9: aload_0
      //   10: iconst_m1
      //   11: putfield 88	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:nestedClassNameMemoizedSerializedSize	I
      //   14: aload_0
      //   15: iconst_m1
      //   16: putfield 90	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:sealedSubclassFqNameMemoizedSerializedSize	I
      //   19: aload_0
      //   20: iconst_m1
      //   21: i2b
      //   22: putfield 92	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:memoizedIsInitialized	B
      //   25: aload_0
      //   26: iconst_m1
      //   27: putfield 94	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:memoizedSerializedSize	I
      //   30: aload_0
      //   31: invokespecial 77	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:initFields	()V
      //   34: invokestatic 100	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   37: astore_3
      //   38: aload_3
      //   39: iconst_1
      //   40: invokestatic 106	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   43: astore 4
      //   45: iconst_0
      //   46: istore 5
      //   48: iconst_0
      //   49: istore 6
      //   51: iload 5
      //   53: ifne +3063 -> 3116
      //   56: iload 6
      //   58: istore 7
      //   60: iload 6
      //   62: istore 8
      //   64: iload 6
      //   66: istore 9
      //   68: aload_1
      //   69: invokevirtual 112	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   72: istore 10
      //   74: aconst_null
      //   75: astore 11
      //   77: aconst_null
      //   78: astore 12
      //   80: iload 10
      //   82: lookupswitch	default:+178->260, 0:+2681->2763, 8:+2632->2714, 16:+2519->2601, 18:+2325->2407, 24:+2280->2362, 32:+2235->2317, 42:+2125->2207, 50:+2015->2097, 56:+1906->1988, 58:+1712->1794, 66:+1599->1681, 74:+1486->1568, 82:+1373->1455, 90:+1260->1342, 106:+1147->1229, 128:+1035->1117, 130:+838->920, 242:+674->756, 248:+562->644, 250:+365->447, 258:+205->287
      //   260: iload 6
      //   262: istore 7
      //   264: iload 6
      //   266: istore 8
      //   268: iload 6
      //   270: istore 9
      //   272: aload_0
      //   273: aload_1
      //   274: aload 4
      //   276: aload_2
      //   277: iload 10
      //   279: invokevirtual 116	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   282: istore 13
      //   284: goto +2489 -> 2773
      //   287: iload 6
      //   289: istore 7
      //   291: iload 6
      //   293: istore 8
      //   295: iload 6
      //   297: istore 9
      //   299: aload_0
      //   300: getfield 118	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:bitField0_	I
      //   303: bipush 16
      //   305: iand
      //   306: bipush 16
      //   308: if_icmpne +24 -> 332
      //   311: iload 6
      //   313: istore 7
      //   315: iload 6
      //   317: istore 8
      //   319: iload 6
      //   321: istore 9
      //   323: aload_0
      //   324: getfield 120	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:versionRequirementTable_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable;
      //   327: invokevirtual 126	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable$Builder;
      //   330: astore 12
      //   332: iload 6
      //   334: istore 7
      //   336: iload 6
      //   338: istore 8
      //   340: iload 6
      //   342: istore 9
      //   344: aload_1
      //   345: getstatic 127	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   348: aload_2
      //   349: invokevirtual 131	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   352: checkcast 122	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable
      //   355: astore 11
      //   357: iload 6
      //   359: istore 7
      //   361: iload 6
      //   363: istore 8
      //   365: iload 6
      //   367: istore 9
      //   369: aload_0
      //   370: aload 11
      //   372: putfield 120	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:versionRequirementTable_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable;
      //   375: aload 12
      //   377: ifnull +44 -> 421
      //   380: iload 6
      //   382: istore 7
      //   384: iload 6
      //   386: istore 8
      //   388: iload 6
      //   390: istore 9
      //   392: aload 12
      //   394: aload 11
      //   396: invokevirtual 137	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable$Builder;
      //   399: pop
      //   400: iload 6
      //   402: istore 7
      //   404: iload 6
      //   406: istore 8
      //   408: iload 6
      //   410: istore 9
      //   412: aload_0
      //   413: aload 12
      //   415: invokevirtual 141	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable;
      //   418: putfield 120	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:versionRequirementTable_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable;
      //   421: iload 6
      //   423: istore 7
      //   425: iload 6
      //   427: istore 8
      //   429: iload 6
      //   431: istore 9
      //   433: aload_0
      //   434: aload_0
      //   435: getfield 118	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:bitField0_	I
      //   438: bipush 16
      //   440: ior
      //   441: putfield 118	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:bitField0_	I
      //   444: goto +2263 -> 2707
      //   447: iload 6
      //   449: istore 7
      //   451: iload 6
      //   453: istore 8
      //   455: iload 6
      //   457: istore 9
      //   459: aload_1
      //   460: aload_1
      //   461: invokevirtual 144	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readRawVarint32	()I
      //   464: invokevirtual 148	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:pushLimit	(I)I
      //   467: istore 14
      //   469: iload 6
      //   471: istore 10
      //   473: iload 6
      //   475: sipush 16384
      //   478: iand
      //   479: sipush 16384
      //   482: if_icmpeq +86 -> 568
      //   485: iload 6
      //   487: istore 10
      //   489: iload 6
      //   491: istore 7
      //   493: iload 6
      //   495: istore 8
      //   497: iload 6
      //   499: istore 9
      //   501: aload_1
      //   502: invokevirtual 151	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
      //   505: ifle +63 -> 568
      //   508: iload 6
      //   510: istore 7
      //   512: iload 6
      //   514: istore 8
      //   516: iload 6
      //   518: istore 9
      //   520: new 153	java/util/ArrayList
      //   523: astore 12
      //   525: iload 6
      //   527: istore 7
      //   529: iload 6
      //   531: istore 8
      //   533: iload 6
      //   535: istore 9
      //   537: aload 12
      //   539: invokespecial 154	java/util/ArrayList:<init>	()V
      //   542: iload 6
      //   544: istore 7
      //   546: iload 6
      //   548: istore 8
      //   550: iload 6
      //   552: istore 9
      //   554: aload_0
      //   555: aload 12
      //   557: putfield 156	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:versionRequirement_	Ljava/util/List;
      //   560: iload 6
      //   562: sipush 16384
      //   565: ior
      //   566: istore 10
      //   568: iload 10
      //   570: istore 7
      //   572: iload 10
      //   574: istore 8
      //   576: iload 10
      //   578: istore 9
      //   580: aload_1
      //   581: invokevirtual 151	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
      //   584: ifle +35 -> 619
      //   587: iload 10
      //   589: istore 7
      //   591: iload 10
      //   593: istore 8
      //   595: iload 10
      //   597: istore 9
      //   599: aload_0
      //   600: getfield 156	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:versionRequirement_	Ljava/util/List;
      //   603: aload_1
      //   604: invokevirtual 159	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   607: invokestatic 165	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   610: invokeinterface 171 2 0
      //   615: pop
      //   616: goto -48 -> 568
      //   619: iload 10
      //   621: istore 7
      //   623: iload 10
      //   625: istore 8
      //   627: iload 10
      //   629: istore 9
      //   631: aload_1
      //   632: iload 14
      //   634: invokevirtual 175	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:popLimit	(I)V
      //   637: iload 10
      //   639: istore 6
      //   641: goto +2066 -> 2707
      //   644: iload 6
      //   646: istore 10
      //   648: iload 6
      //   650: sipush 16384
      //   653: iand
      //   654: sipush 16384
      //   657: if_icmpeq +63 -> 720
      //   660: iload 6
      //   662: istore 7
      //   664: iload 6
      //   666: istore 8
      //   668: iload 6
      //   670: istore 9
      //   672: new 153	java/util/ArrayList
      //   675: astore 12
      //   677: iload 6
      //   679: istore 7
      //   681: iload 6
      //   683: istore 8
      //   685: iload 6
      //   687: istore 9
      //   689: aload 12
      //   691: invokespecial 154	java/util/ArrayList:<init>	()V
      //   694: iload 6
      //   696: istore 7
      //   698: iload 6
      //   700: istore 8
      //   702: iload 6
      //   704: istore 9
      //   706: aload_0
      //   707: aload 12
      //   709: putfield 156	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:versionRequirement_	Ljava/util/List;
      //   712: iload 6
      //   714: sipush 16384
      //   717: ior
      //   718: istore 10
      //   720: iload 10
      //   722: istore 7
      //   724: iload 10
      //   726: istore 8
      //   728: iload 10
      //   730: istore 9
      //   732: aload_0
      //   733: getfield 156	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:versionRequirement_	Ljava/util/List;
      //   736: aload_1
      //   737: invokevirtual 159	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   740: invokestatic 165	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   743: invokeinterface 171 2 0
      //   748: pop
      //   749: iload 10
      //   751: istore 6
      //   753: goto +1954 -> 2707
      //   756: aload 11
      //   758: astore 12
      //   760: iload 6
      //   762: istore 7
      //   764: iload 6
      //   766: istore 8
      //   768: iload 6
      //   770: istore 9
      //   772: aload_0
      //   773: getfield 118	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:bitField0_	I
      //   776: bipush 8
      //   778: iand
      //   779: bipush 8
      //   781: if_icmpne +24 -> 805
      //   784: iload 6
      //   786: istore 7
      //   788: iload 6
      //   790: istore 8
      //   792: iload 6
      //   794: istore 9
      //   796: aload_0
      //   797: getfield 177	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:typeTable_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable;
      //   800: invokevirtual 182	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable$Builder;
      //   803: astore 12
      //   805: iload 6
      //   807: istore 7
      //   809: iload 6
      //   811: istore 8
      //   813: iload 6
      //   815: istore 9
      //   817: aload_1
      //   818: getstatic 183	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   821: aload_2
      //   822: invokevirtual 131	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   825: checkcast 179	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable
      //   828: astore 11
      //   830: iload 6
      //   832: istore 7
      //   834: iload 6
      //   836: istore 8
      //   838: iload 6
      //   840: istore 9
      //   842: aload_0
      //   843: aload 11
      //   845: putfield 177	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:typeTable_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable;
      //   848: aload 12
      //   850: ifnull +44 -> 894
      //   853: iload 6
      //   855: istore 7
      //   857: iload 6
      //   859: istore 8
      //   861: iload 6
      //   863: istore 9
      //   865: aload 12
      //   867: aload 11
      //   869: invokevirtual 188	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable$Builder;
      //   872: pop
      //   873: iload 6
      //   875: istore 7
      //   877: iload 6
      //   879: istore 8
      //   881: iload 6
      //   883: istore 9
      //   885: aload_0
      //   886: aload 12
      //   888: invokevirtual 191	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable;
      //   891: putfield 177	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:typeTable_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable;
      //   894: iload 6
      //   896: istore 7
      //   898: iload 6
      //   900: istore 8
      //   902: iload 6
      //   904: istore 9
      //   906: aload_0
      //   907: aload_0
      //   908: getfield 118	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:bitField0_	I
      //   911: bipush 8
      //   913: ior
      //   914: putfield 118	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:bitField0_	I
      //   917: goto +1790 -> 2707
      //   920: iload 6
      //   922: istore 7
      //   924: iload 6
      //   926: istore 8
      //   928: iload 6
      //   930: istore 9
      //   932: aload_1
      //   933: aload_1
      //   934: invokevirtual 144	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readRawVarint32	()I
      //   937: invokevirtual 148	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:pushLimit	(I)I
      //   940: istore 14
      //   942: iload 6
      //   944: istore 10
      //   946: iload 6
      //   948: sipush 4096
      //   951: iand
      //   952: sipush 4096
      //   955: if_icmpeq +86 -> 1041
      //   958: iload 6
      //   960: istore 10
      //   962: iload 6
      //   964: istore 7
      //   966: iload 6
      //   968: istore 8
      //   970: iload 6
      //   972: istore 9
      //   974: aload_1
      //   975: invokevirtual 151	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
      //   978: ifle +63 -> 1041
      //   981: iload 6
      //   983: istore 7
      //   985: iload 6
      //   987: istore 8
      //   989: iload 6
      //   991: istore 9
      //   993: new 153	java/util/ArrayList
      //   996: astore 12
      //   998: iload 6
      //   1000: istore 7
      //   1002: iload 6
      //   1004: istore 8
      //   1006: iload 6
      //   1008: istore 9
      //   1010: aload 12
      //   1012: invokespecial 154	java/util/ArrayList:<init>	()V
      //   1015: iload 6
      //   1017: istore 7
      //   1019: iload 6
      //   1021: istore 8
      //   1023: iload 6
      //   1025: istore 9
      //   1027: aload_0
      //   1028: aload 12
      //   1030: putfield 193	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:sealedSubclassFqName_	Ljava/util/List;
      //   1033: iload 6
      //   1035: sipush 4096
      //   1038: ior
      //   1039: istore 10
      //   1041: iload 10
      //   1043: istore 7
      //   1045: iload 10
      //   1047: istore 8
      //   1049: iload 10
      //   1051: istore 9
      //   1053: aload_1
      //   1054: invokevirtual 151	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
      //   1057: ifle +35 -> 1092
      //   1060: iload 10
      //   1062: istore 7
      //   1064: iload 10
      //   1066: istore 8
      //   1068: iload 10
      //   1070: istore 9
      //   1072: aload_0
      //   1073: getfield 193	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:sealedSubclassFqName_	Ljava/util/List;
      //   1076: aload_1
      //   1077: invokevirtual 159	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   1080: invokestatic 165	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   1083: invokeinterface 171 2 0
      //   1088: pop
      //   1089: goto -48 -> 1041
      //   1092: iload 10
      //   1094: istore 7
      //   1096: iload 10
      //   1098: istore 8
      //   1100: iload 10
      //   1102: istore 9
      //   1104: aload_1
      //   1105: iload 14
      //   1107: invokevirtual 175	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:popLimit	(I)V
      //   1110: iload 10
      //   1112: istore 6
      //   1114: goto +1593 -> 2707
      //   1117: iload 6
      //   1119: istore 10
      //   1121: iload 6
      //   1123: sipush 4096
      //   1126: iand
      //   1127: sipush 4096
      //   1130: if_icmpeq +63 -> 1193
      //   1133: iload 6
      //   1135: istore 7
      //   1137: iload 6
      //   1139: istore 8
      //   1141: iload 6
      //   1143: istore 9
      //   1145: new 153	java/util/ArrayList
      //   1148: astore 12
      //   1150: iload 6
      //   1152: istore 7
      //   1154: iload 6
      //   1156: istore 8
      //   1158: iload 6
      //   1160: istore 9
      //   1162: aload 12
      //   1164: invokespecial 154	java/util/ArrayList:<init>	()V
      //   1167: iload 6
      //   1169: istore 7
      //   1171: iload 6
      //   1173: istore 8
      //   1175: iload 6
      //   1177: istore 9
      //   1179: aload_0
      //   1180: aload 12
      //   1182: putfield 193	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:sealedSubclassFqName_	Ljava/util/List;
      //   1185: iload 6
      //   1187: sipush 4096
      //   1190: ior
      //   1191: istore 10
      //   1193: iload 10
      //   1195: istore 7
      //   1197: iload 10
      //   1199: istore 8
      //   1201: iload 10
      //   1203: istore 9
      //   1205: aload_0
      //   1206: getfield 193	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:sealedSubclassFqName_	Ljava/util/List;
      //   1209: aload_1
      //   1210: invokevirtual 159	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   1213: invokestatic 165	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   1216: invokeinterface 171 2 0
      //   1221: pop
      //   1222: iload 10
      //   1224: istore 6
      //   1226: goto +1481 -> 2707
      //   1229: iload 6
      //   1231: istore 10
      //   1233: iload 6
      //   1235: sipush 2048
      //   1238: iand
      //   1239: sipush 2048
      //   1242: if_icmpeq +63 -> 1305
      //   1245: iload 6
      //   1247: istore 7
      //   1249: iload 6
      //   1251: istore 8
      //   1253: iload 6
      //   1255: istore 9
      //   1257: new 153	java/util/ArrayList
      //   1260: astore 12
      //   1262: iload 6
      //   1264: istore 7
      //   1266: iload 6
      //   1268: istore 8
      //   1270: iload 6
      //   1272: istore 9
      //   1274: aload 12
      //   1276: invokespecial 154	java/util/ArrayList:<init>	()V
      //   1279: iload 6
      //   1281: istore 7
      //   1283: iload 6
      //   1285: istore 8
      //   1287: iload 6
      //   1289: istore 9
      //   1291: aload_0
      //   1292: aload 12
      //   1294: putfield 195	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:enumEntry_	Ljava/util/List;
      //   1297: iload 6
      //   1299: sipush 2048
      //   1302: ior
      //   1303: istore 10
      //   1305: iload 10
      //   1307: istore 7
      //   1309: iload 10
      //   1311: istore 8
      //   1313: iload 10
      //   1315: istore 9
      //   1317: aload_0
      //   1318: getfield 195	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:enumEntry_	Ljava/util/List;
      //   1321: aload_1
      //   1322: getstatic 198	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   1325: aload_2
      //   1326: invokevirtual 131	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   1329: invokeinterface 171 2 0
      //   1334: pop
      //   1335: iload 10
      //   1337: istore 6
      //   1339: goto +1368 -> 2707
      //   1342: iload 6
      //   1344: istore 10
      //   1346: iload 6
      //   1348: sipush 1024
      //   1351: iand
      //   1352: sipush 1024
      //   1355: if_icmpeq +63 -> 1418
      //   1358: iload 6
      //   1360: istore 7
      //   1362: iload 6
      //   1364: istore 8
      //   1366: iload 6
      //   1368: istore 9
      //   1370: new 153	java/util/ArrayList
      //   1373: astore 12
      //   1375: iload 6
      //   1377: istore 7
      //   1379: iload 6
      //   1381: istore 8
      //   1383: iload 6
      //   1385: istore 9
      //   1387: aload 12
      //   1389: invokespecial 154	java/util/ArrayList:<init>	()V
      //   1392: iload 6
      //   1394: istore 7
      //   1396: iload 6
      //   1398: istore 8
      //   1400: iload 6
      //   1402: istore 9
      //   1404: aload_0
      //   1405: aload 12
      //   1407: putfield 200	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:typeAlias_	Ljava/util/List;
      //   1410: iload 6
      //   1412: sipush 1024
      //   1415: ior
      //   1416: istore 10
      //   1418: iload 10
      //   1420: istore 7
      //   1422: iload 10
      //   1424: istore 8
      //   1426: iload 10
      //   1428: istore 9
      //   1430: aload_0
      //   1431: getfield 200	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:typeAlias_	Ljava/util/List;
      //   1434: aload_1
      //   1435: getstatic 203	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   1438: aload_2
      //   1439: invokevirtual 131	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   1442: invokeinterface 171 2 0
      //   1447: pop
      //   1448: iload 10
      //   1450: istore 6
      //   1452: goto +1255 -> 2707
      //   1455: iload 6
      //   1457: istore 10
      //   1459: iload 6
      //   1461: sipush 512
      //   1464: iand
      //   1465: sipush 512
      //   1468: if_icmpeq +63 -> 1531
      //   1471: iload 6
      //   1473: istore 7
      //   1475: iload 6
      //   1477: istore 8
      //   1479: iload 6
      //   1481: istore 9
      //   1483: new 153	java/util/ArrayList
      //   1486: astore 12
      //   1488: iload 6
      //   1490: istore 7
      //   1492: iload 6
      //   1494: istore 8
      //   1496: iload 6
      //   1498: istore 9
      //   1500: aload 12
      //   1502: invokespecial 154	java/util/ArrayList:<init>	()V
      //   1505: iload 6
      //   1507: istore 7
      //   1509: iload 6
      //   1511: istore 8
      //   1513: iload 6
      //   1515: istore 9
      //   1517: aload_0
      //   1518: aload 12
      //   1520: putfield 205	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:property_	Ljava/util/List;
      //   1523: iload 6
      //   1525: sipush 512
      //   1528: ior
      //   1529: istore 10
      //   1531: iload 10
      //   1533: istore 7
      //   1535: iload 10
      //   1537: istore 8
      //   1539: iload 10
      //   1541: istore 9
      //   1543: aload_0
      //   1544: getfield 205	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:property_	Ljava/util/List;
      //   1547: aload_1
      //   1548: getstatic 208	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   1551: aload_2
      //   1552: invokevirtual 131	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   1555: invokeinterface 171 2 0
      //   1560: pop
      //   1561: iload 10
      //   1563: istore 6
      //   1565: goto +1142 -> 2707
      //   1568: iload 6
      //   1570: istore 10
      //   1572: iload 6
      //   1574: sipush 256
      //   1577: iand
      //   1578: sipush 256
      //   1581: if_icmpeq +63 -> 1644
      //   1584: iload 6
      //   1586: istore 7
      //   1588: iload 6
      //   1590: istore 8
      //   1592: iload 6
      //   1594: istore 9
      //   1596: new 153	java/util/ArrayList
      //   1599: astore 12
      //   1601: iload 6
      //   1603: istore 7
      //   1605: iload 6
      //   1607: istore 8
      //   1609: iload 6
      //   1611: istore 9
      //   1613: aload 12
      //   1615: invokespecial 154	java/util/ArrayList:<init>	()V
      //   1618: iload 6
      //   1620: istore 7
      //   1622: iload 6
      //   1624: istore 8
      //   1626: iload 6
      //   1628: istore 9
      //   1630: aload_0
      //   1631: aload 12
      //   1633: putfield 210	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:function_	Ljava/util/List;
      //   1636: iload 6
      //   1638: sipush 256
      //   1641: ior
      //   1642: istore 10
      //   1644: iload 10
      //   1646: istore 7
      //   1648: iload 10
      //   1650: istore 8
      //   1652: iload 10
      //   1654: istore 9
      //   1656: aload_0
      //   1657: getfield 210	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:function_	Ljava/util/List;
      //   1660: aload_1
      //   1661: getstatic 213	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   1664: aload_2
      //   1665: invokevirtual 131	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   1668: invokeinterface 171 2 0
      //   1673: pop
      //   1674: iload 10
      //   1676: istore 6
      //   1678: goto +1029 -> 2707
      //   1681: iload 6
      //   1683: istore 10
      //   1685: iload 6
      //   1687: sipush 128
      //   1690: iand
      //   1691: sipush 128
      //   1694: if_icmpeq +63 -> 1757
      //   1697: iload 6
      //   1699: istore 7
      //   1701: iload 6
      //   1703: istore 8
      //   1705: iload 6
      //   1707: istore 9
      //   1709: new 153	java/util/ArrayList
      //   1712: astore 12
      //   1714: iload 6
      //   1716: istore 7
      //   1718: iload 6
      //   1720: istore 8
      //   1722: iload 6
      //   1724: istore 9
      //   1726: aload 12
      //   1728: invokespecial 154	java/util/ArrayList:<init>	()V
      //   1731: iload 6
      //   1733: istore 7
      //   1735: iload 6
      //   1737: istore 8
      //   1739: iload 6
      //   1741: istore 9
      //   1743: aload_0
      //   1744: aload 12
      //   1746: putfield 215	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:constructor_	Ljava/util/List;
      //   1749: iload 6
      //   1751: sipush 128
      //   1754: ior
      //   1755: istore 10
      //   1757: iload 10
      //   1759: istore 7
      //   1761: iload 10
      //   1763: istore 8
      //   1765: iload 10
      //   1767: istore 9
      //   1769: aload_0
      //   1770: getfield 215	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:constructor_	Ljava/util/List;
      //   1773: aload_1
      //   1774: getstatic 218	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   1777: aload_2
      //   1778: invokevirtual 131	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   1781: invokeinterface 171 2 0
      //   1786: pop
      //   1787: iload 10
      //   1789: istore 6
      //   1791: goto +916 -> 2707
      //   1794: iload 6
      //   1796: istore 7
      //   1798: iload 6
      //   1800: istore 8
      //   1802: iload 6
      //   1804: istore 9
      //   1806: aload_1
      //   1807: aload_1
      //   1808: invokevirtual 144	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readRawVarint32	()I
      //   1811: invokevirtual 148	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:pushLimit	(I)I
      //   1814: istore 14
      //   1816: iload 6
      //   1818: istore 10
      //   1820: iload 6
      //   1822: bipush 64
      //   1824: iand
      //   1825: bipush 64
      //   1827: if_icmpeq +85 -> 1912
      //   1830: iload 6
      //   1832: istore 10
      //   1834: iload 6
      //   1836: istore 7
      //   1838: iload 6
      //   1840: istore 8
      //   1842: iload 6
      //   1844: istore 9
      //   1846: aload_1
      //   1847: invokevirtual 151	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
      //   1850: ifle +62 -> 1912
      //   1853: iload 6
      //   1855: istore 7
      //   1857: iload 6
      //   1859: istore 8
      //   1861: iload 6
      //   1863: istore 9
      //   1865: new 153	java/util/ArrayList
      //   1868: astore 12
      //   1870: iload 6
      //   1872: istore 7
      //   1874: iload 6
      //   1876: istore 8
      //   1878: iload 6
      //   1880: istore 9
      //   1882: aload 12
      //   1884: invokespecial 154	java/util/ArrayList:<init>	()V
      //   1887: iload 6
      //   1889: istore 7
      //   1891: iload 6
      //   1893: istore 8
      //   1895: iload 6
      //   1897: istore 9
      //   1899: aload_0
      //   1900: aload 12
      //   1902: putfield 220	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:nestedClassName_	Ljava/util/List;
      //   1905: iload 6
      //   1907: bipush 64
      //   1909: ior
      //   1910: istore 10
      //   1912: iload 10
      //   1914: istore 7
      //   1916: iload 10
      //   1918: istore 8
      //   1920: iload 10
      //   1922: istore 9
      //   1924: aload_1
      //   1925: invokevirtual 151	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
      //   1928: ifle +35 -> 1963
      //   1931: iload 10
      //   1933: istore 7
      //   1935: iload 10
      //   1937: istore 8
      //   1939: iload 10
      //   1941: istore 9
      //   1943: aload_0
      //   1944: getfield 220	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:nestedClassName_	Ljava/util/List;
      //   1947: aload_1
      //   1948: invokevirtual 159	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   1951: invokestatic 165	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   1954: invokeinterface 171 2 0
      //   1959: pop
      //   1960: goto -48 -> 1912
      //   1963: iload 10
      //   1965: istore 7
      //   1967: iload 10
      //   1969: istore 8
      //   1971: iload 10
      //   1973: istore 9
      //   1975: aload_1
      //   1976: iload 14
      //   1978: invokevirtual 175	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:popLimit	(I)V
      //   1981: iload 10
      //   1983: istore 6
      //   1985: goto +722 -> 2707
      //   1988: iload 6
      //   1990: istore 10
      //   1992: iload 6
      //   1994: bipush 64
      //   1996: iand
      //   1997: bipush 64
      //   1999: if_icmpeq +62 -> 2061
      //   2002: iload 6
      //   2004: istore 7
      //   2006: iload 6
      //   2008: istore 8
      //   2010: iload 6
      //   2012: istore 9
      //   2014: new 153	java/util/ArrayList
      //   2017: astore 12
      //   2019: iload 6
      //   2021: istore 7
      //   2023: iload 6
      //   2025: istore 8
      //   2027: iload 6
      //   2029: istore 9
      //   2031: aload 12
      //   2033: invokespecial 154	java/util/ArrayList:<init>	()V
      //   2036: iload 6
      //   2038: istore 7
      //   2040: iload 6
      //   2042: istore 8
      //   2044: iload 6
      //   2046: istore 9
      //   2048: aload_0
      //   2049: aload 12
      //   2051: putfield 220	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:nestedClassName_	Ljava/util/List;
      //   2054: iload 6
      //   2056: bipush 64
      //   2058: ior
      //   2059: istore 10
      //   2061: iload 10
      //   2063: istore 7
      //   2065: iload 10
      //   2067: istore 8
      //   2069: iload 10
      //   2071: istore 9
      //   2073: aload_0
      //   2074: getfield 220	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:nestedClassName_	Ljava/util/List;
      //   2077: aload_1
      //   2078: invokevirtual 159	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   2081: invokestatic 165	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   2084: invokeinterface 171 2 0
      //   2089: pop
      //   2090: iload 10
      //   2092: istore 6
      //   2094: goto +613 -> 2707
      //   2097: iload 6
      //   2099: istore 10
      //   2101: iload 6
      //   2103: bipush 16
      //   2105: iand
      //   2106: bipush 16
      //   2108: if_icmpeq +62 -> 2170
      //   2111: iload 6
      //   2113: istore 7
      //   2115: iload 6
      //   2117: istore 8
      //   2119: iload 6
      //   2121: istore 9
      //   2123: new 153	java/util/ArrayList
      //   2126: astore 12
      //   2128: iload 6
      //   2130: istore 7
      //   2132: iload 6
      //   2134: istore 8
      //   2136: iload 6
      //   2138: istore 9
      //   2140: aload 12
      //   2142: invokespecial 154	java/util/ArrayList:<init>	()V
      //   2145: iload 6
      //   2147: istore 7
      //   2149: iload 6
      //   2151: istore 8
      //   2153: iload 6
      //   2155: istore 9
      //   2157: aload_0
      //   2158: aload 12
      //   2160: putfield 222	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:supertype_	Ljava/util/List;
      //   2163: iload 6
      //   2165: bipush 16
      //   2167: ior
      //   2168: istore 10
      //   2170: iload 10
      //   2172: istore 7
      //   2174: iload 10
      //   2176: istore 8
      //   2178: iload 10
      //   2180: istore 9
      //   2182: aload_0
      //   2183: getfield 222	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:supertype_	Ljava/util/List;
      //   2186: aload_1
      //   2187: getstatic 225	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   2190: aload_2
      //   2191: invokevirtual 131	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   2194: invokeinterface 171 2 0
      //   2199: pop
      //   2200: iload 10
      //   2202: istore 6
      //   2204: goto +503 -> 2707
      //   2207: iload 6
      //   2209: istore 10
      //   2211: iload 6
      //   2213: bipush 8
      //   2215: iand
      //   2216: bipush 8
      //   2218: if_icmpeq +62 -> 2280
      //   2221: iload 6
      //   2223: istore 7
      //   2225: iload 6
      //   2227: istore 8
      //   2229: iload 6
      //   2231: istore 9
      //   2233: new 153	java/util/ArrayList
      //   2236: astore 12
      //   2238: iload 6
      //   2240: istore 7
      //   2242: iload 6
      //   2244: istore 8
      //   2246: iload 6
      //   2248: istore 9
      //   2250: aload 12
      //   2252: invokespecial 154	java/util/ArrayList:<init>	()V
      //   2255: iload 6
      //   2257: istore 7
      //   2259: iload 6
      //   2261: istore 8
      //   2263: iload 6
      //   2265: istore 9
      //   2267: aload_0
      //   2268: aload 12
      //   2270: putfield 227	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:typeParameter_	Ljava/util/List;
      //   2273: iload 6
      //   2275: bipush 8
      //   2277: ior
      //   2278: istore 10
      //   2280: iload 10
      //   2282: istore 7
      //   2284: iload 10
      //   2286: istore 8
      //   2288: iload 10
      //   2290: istore 9
      //   2292: aload_0
      //   2293: getfield 227	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:typeParameter_	Ljava/util/List;
      //   2296: aload_1
      //   2297: getstatic 230	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   2300: aload_2
      //   2301: invokevirtual 131	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   2304: invokeinterface 171 2 0
      //   2309: pop
      //   2310: iload 10
      //   2312: istore 6
      //   2314: goto +393 -> 2707
      //   2317: iload 6
      //   2319: istore 7
      //   2321: iload 6
      //   2323: istore 8
      //   2325: iload 6
      //   2327: istore 9
      //   2329: aload_0
      //   2330: aload_0
      //   2331: getfield 118	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:bitField0_	I
      //   2334: iconst_4
      //   2335: ior
      //   2336: putfield 118	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:bitField0_	I
      //   2339: iload 6
      //   2341: istore 7
      //   2343: iload 6
      //   2345: istore 8
      //   2347: iload 6
      //   2349: istore 9
      //   2351: aload_0
      //   2352: aload_1
      //   2353: invokevirtual 159	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   2356: putfield 232	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:companionObjectName_	I
      //   2359: goto +348 -> 2707
      //   2362: iload 6
      //   2364: istore 7
      //   2366: iload 6
      //   2368: istore 8
      //   2370: iload 6
      //   2372: istore 9
      //   2374: aload_0
      //   2375: aload_0
      //   2376: getfield 118	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:bitField0_	I
      //   2379: iconst_2
      //   2380: ior
      //   2381: putfield 118	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:bitField0_	I
      //   2384: iload 6
      //   2386: istore 7
      //   2388: iload 6
      //   2390: istore 8
      //   2392: iload 6
      //   2394: istore 9
      //   2396: aload_0
      //   2397: aload_1
      //   2398: invokevirtual 159	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   2401: putfield 234	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:fqName_	I
      //   2404: goto +303 -> 2707
      //   2407: iload 6
      //   2409: istore 7
      //   2411: iload 6
      //   2413: istore 8
      //   2415: iload 6
      //   2417: istore 9
      //   2419: aload_1
      //   2420: aload_1
      //   2421: invokevirtual 144	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readRawVarint32	()I
      //   2424: invokevirtual 148	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:pushLimit	(I)I
      //   2427: istore 14
      //   2429: iload 6
      //   2431: istore 10
      //   2433: iload 6
      //   2435: bipush 32
      //   2437: iand
      //   2438: bipush 32
      //   2440: if_icmpeq +85 -> 2525
      //   2443: iload 6
      //   2445: istore 10
      //   2447: iload 6
      //   2449: istore 7
      //   2451: iload 6
      //   2453: istore 8
      //   2455: iload 6
      //   2457: istore 9
      //   2459: aload_1
      //   2460: invokevirtual 151	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
      //   2463: ifle +62 -> 2525
      //   2466: iload 6
      //   2468: istore 7
      //   2470: iload 6
      //   2472: istore 8
      //   2474: iload 6
      //   2476: istore 9
      //   2478: new 153	java/util/ArrayList
      //   2481: astore 12
      //   2483: iload 6
      //   2485: istore 7
      //   2487: iload 6
      //   2489: istore 8
      //   2491: iload 6
      //   2493: istore 9
      //   2495: aload 12
      //   2497: invokespecial 154	java/util/ArrayList:<init>	()V
      //   2500: iload 6
      //   2502: istore 7
      //   2504: iload 6
      //   2506: istore 8
      //   2508: iload 6
      //   2510: istore 9
      //   2512: aload_0
      //   2513: aload 12
      //   2515: putfield 236	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:supertypeId_	Ljava/util/List;
      //   2518: iload 6
      //   2520: bipush 32
      //   2522: ior
      //   2523: istore 10
      //   2525: iload 10
      //   2527: istore 7
      //   2529: iload 10
      //   2531: istore 8
      //   2533: iload 10
      //   2535: istore 9
      //   2537: aload_1
      //   2538: invokevirtual 151	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
      //   2541: ifle +35 -> 2576
      //   2544: iload 10
      //   2546: istore 7
      //   2548: iload 10
      //   2550: istore 8
      //   2552: iload 10
      //   2554: istore 9
      //   2556: aload_0
      //   2557: getfield 236	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:supertypeId_	Ljava/util/List;
      //   2560: aload_1
      //   2561: invokevirtual 159	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   2564: invokestatic 165	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   2567: invokeinterface 171 2 0
      //   2572: pop
      //   2573: goto -48 -> 2525
      //   2576: iload 10
      //   2578: istore 7
      //   2580: iload 10
      //   2582: istore 8
      //   2584: iload 10
      //   2586: istore 9
      //   2588: aload_1
      //   2589: iload 14
      //   2591: invokevirtual 175	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:popLimit	(I)V
      //   2594: iload 10
      //   2596: istore 6
      //   2598: goto +109 -> 2707
      //   2601: iload 6
      //   2603: istore 10
      //   2605: iload 6
      //   2607: bipush 32
      //   2609: iand
      //   2610: bipush 32
      //   2612: if_icmpeq +62 -> 2674
      //   2615: iload 6
      //   2617: istore 7
      //   2619: iload 6
      //   2621: istore 8
      //   2623: iload 6
      //   2625: istore 9
      //   2627: new 153	java/util/ArrayList
      //   2630: astore 12
      //   2632: iload 6
      //   2634: istore 7
      //   2636: iload 6
      //   2638: istore 8
      //   2640: iload 6
      //   2642: istore 9
      //   2644: aload 12
      //   2646: invokespecial 154	java/util/ArrayList:<init>	()V
      //   2649: iload 6
      //   2651: istore 7
      //   2653: iload 6
      //   2655: istore 8
      //   2657: iload 6
      //   2659: istore 9
      //   2661: aload_0
      //   2662: aload 12
      //   2664: putfield 236	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:supertypeId_	Ljava/util/List;
      //   2667: iload 6
      //   2669: bipush 32
      //   2671: ior
      //   2672: istore 10
      //   2674: iload 10
      //   2676: istore 7
      //   2678: iload 10
      //   2680: istore 8
      //   2682: iload 10
      //   2684: istore 9
      //   2686: aload_0
      //   2687: getfield 236	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:supertypeId_	Ljava/util/List;
      //   2690: aload_1
      //   2691: invokevirtual 159	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   2694: invokestatic 165	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   2697: invokeinterface 171 2 0
      //   2702: pop
      //   2703: iload 10
      //   2705: istore 6
      //   2707: iload 6
      //   2709: istore 7
      //   2711: goto +74 -> 2785
      //   2714: iload 6
      //   2716: istore 7
      //   2718: iload 6
      //   2720: istore 8
      //   2722: iload 6
      //   2724: istore 9
      //   2726: aload_0
      //   2727: aload_0
      //   2728: getfield 118	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:bitField0_	I
      //   2731: iconst_1
      //   2732: ior
      //   2733: putfield 118	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:bitField0_	I
      //   2736: iload 6
      //   2738: istore 7
      //   2740: iload 6
      //   2742: istore 8
      //   2744: iload 6
      //   2746: istore 9
      //   2748: aload_0
      //   2749: aload_1
      //   2750: invokevirtual 159	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   2753: putfield 238	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:flags_	I
      //   2756: iload 6
      //   2758: istore 7
      //   2760: goto +25 -> 2785
      //   2763: iconst_1
      //   2764: istore 5
      //   2766: iload 6
      //   2768: istore 7
      //   2770: goto +15 -> 2785
      //   2773: iload 6
      //   2775: istore 7
      //   2777: iload 13
      //   2779: ifne +6 -> 2785
      //   2782: goto -19 -> 2763
      //   2785: iload 7
      //   2787: istore 6
      //   2789: goto -2738 -> 51
      //   2792: astore_1
      //   2793: goto +45 -> 2838
      //   2796: astore_2
      //   2797: iload 8
      //   2799: istore 7
      //   2801: new 81	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2804: astore_1
      //   2805: iload 8
      //   2807: istore 7
      //   2809: aload_1
      //   2810: aload_2
      //   2811: invokevirtual 242	java/io/IOException:getMessage	()Ljava/lang/String;
      //   2814: invokespecial 245	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   2817: iload 8
      //   2819: istore 7
      //   2821: aload_1
      //   2822: aload_0
      //   2823: invokevirtual 249	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   2826: athrow
      //   2827: astore_1
      //   2828: iload 9
      //   2830: istore 7
      //   2832: aload_1
      //   2833: aload_0
      //   2834: invokevirtual 249	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   2837: athrow
      //   2838: iload 7
      //   2840: bipush 32
      //   2842: iand
      //   2843: bipush 32
      //   2845: if_icmpne +14 -> 2859
      //   2848: aload_0
      //   2849: aload_0
      //   2850: getfield 236	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:supertypeId_	Ljava/util/List;
      //   2853: invokestatic 255	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   2856: putfield 236	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:supertypeId_	Ljava/util/List;
      //   2859: iload 7
      //   2861: bipush 8
      //   2863: iand
      //   2864: bipush 8
      //   2866: if_icmpne +14 -> 2880
      //   2869: aload_0
      //   2870: aload_0
      //   2871: getfield 227	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:typeParameter_	Ljava/util/List;
      //   2874: invokestatic 255	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   2877: putfield 227	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:typeParameter_	Ljava/util/List;
      //   2880: iload 7
      //   2882: bipush 16
      //   2884: iand
      //   2885: bipush 16
      //   2887: if_icmpne +14 -> 2901
      //   2890: aload_0
      //   2891: aload_0
      //   2892: getfield 222	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:supertype_	Ljava/util/List;
      //   2895: invokestatic 255	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   2898: putfield 222	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:supertype_	Ljava/util/List;
      //   2901: iload 7
      //   2903: bipush 64
      //   2905: iand
      //   2906: bipush 64
      //   2908: if_icmpne +14 -> 2922
      //   2911: aload_0
      //   2912: aload_0
      //   2913: getfield 220	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:nestedClassName_	Ljava/util/List;
      //   2916: invokestatic 255	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   2919: putfield 220	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:nestedClassName_	Ljava/util/List;
      //   2922: iload 7
      //   2924: sipush 128
      //   2927: iand
      //   2928: sipush 128
      //   2931: if_icmpne +14 -> 2945
      //   2934: aload_0
      //   2935: aload_0
      //   2936: getfield 215	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:constructor_	Ljava/util/List;
      //   2939: invokestatic 255	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   2942: putfield 215	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:constructor_	Ljava/util/List;
      //   2945: iload 7
      //   2947: sipush 256
      //   2950: iand
      //   2951: sipush 256
      //   2954: if_icmpne +14 -> 2968
      //   2957: aload_0
      //   2958: aload_0
      //   2959: getfield 210	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:function_	Ljava/util/List;
      //   2962: invokestatic 255	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   2965: putfield 210	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:function_	Ljava/util/List;
      //   2968: iload 7
      //   2970: sipush 512
      //   2973: iand
      //   2974: sipush 512
      //   2977: if_icmpne +14 -> 2991
      //   2980: aload_0
      //   2981: aload_0
      //   2982: getfield 205	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:property_	Ljava/util/List;
      //   2985: invokestatic 255	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   2988: putfield 205	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:property_	Ljava/util/List;
      //   2991: iload 7
      //   2993: sipush 1024
      //   2996: iand
      //   2997: sipush 1024
      //   3000: if_icmpne +14 -> 3014
      //   3003: aload_0
      //   3004: aload_0
      //   3005: getfield 200	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:typeAlias_	Ljava/util/List;
      //   3008: invokestatic 255	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   3011: putfield 200	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:typeAlias_	Ljava/util/List;
      //   3014: iload 7
      //   3016: sipush 2048
      //   3019: iand
      //   3020: sipush 2048
      //   3023: if_icmpne +14 -> 3037
      //   3026: aload_0
      //   3027: aload_0
      //   3028: getfield 195	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:enumEntry_	Ljava/util/List;
      //   3031: invokestatic 255	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   3034: putfield 195	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:enumEntry_	Ljava/util/List;
      //   3037: iload 7
      //   3039: sipush 4096
      //   3042: iand
      //   3043: sipush 4096
      //   3046: if_icmpne +14 -> 3060
      //   3049: aload_0
      //   3050: aload_0
      //   3051: getfield 193	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:sealedSubclassFqName_	Ljava/util/List;
      //   3054: invokestatic 255	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   3057: putfield 193	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:sealedSubclassFqName_	Ljava/util/List;
      //   3060: iload 7
      //   3062: sipush 16384
      //   3065: iand
      //   3066: sipush 16384
      //   3069: if_icmpne +14 -> 3083
      //   3072: aload_0
      //   3073: aload_0
      //   3074: getfield 156	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:versionRequirement_	Ljava/util/List;
      //   3077: invokestatic 255	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   3080: putfield 156	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:versionRequirement_	Ljava/util/List;
      //   3083: aload 4
      //   3085: invokevirtual 258	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   3088: aload_0
      //   3089: aload_3
      //   3090: invokevirtual 264	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   3093: putfield 266	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   3096: goto +14 -> 3110
      //   3099: astore_1
      //   3100: aload_0
      //   3101: aload_3
      //   3102: invokevirtual 264	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   3105: putfield 266	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   3108: aload_1
      //   3109: athrow
      //   3110: aload_0
      //   3111: invokevirtual 269	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:makeExtensionsImmutable	()V
      //   3114: aload_1
      //   3115: athrow
      //   3116: iload 6
      //   3118: bipush 32
      //   3120: iand
      //   3121: bipush 32
      //   3123: if_icmpne +14 -> 3137
      //   3126: aload_0
      //   3127: aload_0
      //   3128: getfield 236	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:supertypeId_	Ljava/util/List;
      //   3131: invokestatic 255	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   3134: putfield 236	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:supertypeId_	Ljava/util/List;
      //   3137: iload 6
      //   3139: bipush 8
      //   3141: iand
      //   3142: bipush 8
      //   3144: if_icmpne +14 -> 3158
      //   3147: aload_0
      //   3148: aload_0
      //   3149: getfield 227	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:typeParameter_	Ljava/util/List;
      //   3152: invokestatic 255	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   3155: putfield 227	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:typeParameter_	Ljava/util/List;
      //   3158: iload 6
      //   3160: bipush 16
      //   3162: iand
      //   3163: bipush 16
      //   3165: if_icmpne +14 -> 3179
      //   3168: aload_0
      //   3169: aload_0
      //   3170: getfield 222	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:supertype_	Ljava/util/List;
      //   3173: invokestatic 255	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   3176: putfield 222	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:supertype_	Ljava/util/List;
      //   3179: iload 6
      //   3181: bipush 64
      //   3183: iand
      //   3184: bipush 64
      //   3186: if_icmpne +14 -> 3200
      //   3189: aload_0
      //   3190: aload_0
      //   3191: getfield 220	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:nestedClassName_	Ljava/util/List;
      //   3194: invokestatic 255	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   3197: putfield 220	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:nestedClassName_	Ljava/util/List;
      //   3200: iload 6
      //   3202: sipush 128
      //   3205: iand
      //   3206: sipush 128
      //   3209: if_icmpne +14 -> 3223
      //   3212: aload_0
      //   3213: aload_0
      //   3214: getfield 215	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:constructor_	Ljava/util/List;
      //   3217: invokestatic 255	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   3220: putfield 215	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:constructor_	Ljava/util/List;
      //   3223: iload 6
      //   3225: sipush 256
      //   3228: iand
      //   3229: sipush 256
      //   3232: if_icmpne +14 -> 3246
      //   3235: aload_0
      //   3236: aload_0
      //   3237: getfield 210	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:function_	Ljava/util/List;
      //   3240: invokestatic 255	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   3243: putfield 210	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:function_	Ljava/util/List;
      //   3246: iload 6
      //   3248: sipush 512
      //   3251: iand
      //   3252: sipush 512
      //   3255: if_icmpne +14 -> 3269
      //   3258: aload_0
      //   3259: aload_0
      //   3260: getfield 205	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:property_	Ljava/util/List;
      //   3263: invokestatic 255	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   3266: putfield 205	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:property_	Ljava/util/List;
      //   3269: iload 6
      //   3271: sipush 1024
      //   3274: iand
      //   3275: sipush 1024
      //   3278: if_icmpne +14 -> 3292
      //   3281: aload_0
      //   3282: aload_0
      //   3283: getfield 200	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:typeAlias_	Ljava/util/List;
      //   3286: invokestatic 255	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   3289: putfield 200	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:typeAlias_	Ljava/util/List;
      //   3292: iload 6
      //   3294: sipush 2048
      //   3297: iand
      //   3298: sipush 2048
      //   3301: if_icmpne +14 -> 3315
      //   3304: aload_0
      //   3305: aload_0
      //   3306: getfield 195	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:enumEntry_	Ljava/util/List;
      //   3309: invokestatic 255	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   3312: putfield 195	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:enumEntry_	Ljava/util/List;
      //   3315: iload 6
      //   3317: sipush 4096
      //   3320: iand
      //   3321: sipush 4096
      //   3324: if_icmpne +14 -> 3338
      //   3327: aload_0
      //   3328: aload_0
      //   3329: getfield 193	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:sealedSubclassFqName_	Ljava/util/List;
      //   3332: invokestatic 255	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   3335: putfield 193	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:sealedSubclassFqName_	Ljava/util/List;
      //   3338: iload 6
      //   3340: sipush 16384
      //   3343: iand
      //   3344: sipush 16384
      //   3347: if_icmpne +14 -> 3361
      //   3350: aload_0
      //   3351: aload_0
      //   3352: getfield 156	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:versionRequirement_	Ljava/util/List;
      //   3355: invokestatic 255	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   3358: putfield 156	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:versionRequirement_	Ljava/util/List;
      //   3361: aload 4
      //   3363: invokevirtual 258	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   3366: aload_0
      //   3367: aload_3
      //   3368: invokevirtual 264	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   3371: putfield 266	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   3374: goto +14 -> 3388
      //   3377: astore_1
      //   3378: aload_0
      //   3379: aload_3
      //   3380: invokevirtual 264	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   3383: putfield 266	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   3386: aload_1
      //   3387: athrow
      //   3388: aload_0
      //   3389: invokevirtual 269	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:makeExtensionsImmutable	()V
      //   3392: return
      //   3393: astore_2
      //   3394: goto -306 -> 3088
      //   3397: astore_1
      //   3398: goto -32 -> 3366
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	3401	0	this	Class
      //   0	3401	1	paramCodedInputStream	CodedInputStream
      //   0	3401	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   37	3343	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   43	3319	4	localCodedOutputStream	CodedOutputStream
      //   46	2719	5	i	int
      //   49	3295	6	j	int
      //   58	3008	7	k	int
      //   62	2756	8	m	int
      //   66	2763	9	n	int
      //   72	2632	10	i1	int
      //   75	793	11	localObject1	Object
      //   78	2585	12	localObject2	Object
      //   282	2496	13	bool	boolean
      //   467	2123	14	i2	int
      // Exception table:
      //   from	to	target	type
      //   68	74	2792	finally
      //   272	284	2792	finally
      //   299	311	2792	finally
      //   323	332	2792	finally
      //   344	357	2792	finally
      //   369	375	2792	finally
      //   392	400	2792	finally
      //   412	421	2792	finally
      //   433	444	2792	finally
      //   459	469	2792	finally
      //   501	508	2792	finally
      //   520	525	2792	finally
      //   537	542	2792	finally
      //   554	560	2792	finally
      //   580	587	2792	finally
      //   599	616	2792	finally
      //   631	637	2792	finally
      //   672	677	2792	finally
      //   689	694	2792	finally
      //   706	712	2792	finally
      //   732	749	2792	finally
      //   772	784	2792	finally
      //   796	805	2792	finally
      //   817	830	2792	finally
      //   842	848	2792	finally
      //   865	873	2792	finally
      //   885	894	2792	finally
      //   906	917	2792	finally
      //   932	942	2792	finally
      //   974	981	2792	finally
      //   993	998	2792	finally
      //   1010	1015	2792	finally
      //   1027	1033	2792	finally
      //   1053	1060	2792	finally
      //   1072	1089	2792	finally
      //   1104	1110	2792	finally
      //   1145	1150	2792	finally
      //   1162	1167	2792	finally
      //   1179	1185	2792	finally
      //   1205	1222	2792	finally
      //   1257	1262	2792	finally
      //   1274	1279	2792	finally
      //   1291	1297	2792	finally
      //   1317	1335	2792	finally
      //   1370	1375	2792	finally
      //   1387	1392	2792	finally
      //   1404	1410	2792	finally
      //   1430	1448	2792	finally
      //   1483	1488	2792	finally
      //   1500	1505	2792	finally
      //   1517	1523	2792	finally
      //   1543	1561	2792	finally
      //   1596	1601	2792	finally
      //   1613	1618	2792	finally
      //   1630	1636	2792	finally
      //   1656	1674	2792	finally
      //   1709	1714	2792	finally
      //   1726	1731	2792	finally
      //   1743	1749	2792	finally
      //   1769	1787	2792	finally
      //   1806	1816	2792	finally
      //   1846	1853	2792	finally
      //   1865	1870	2792	finally
      //   1882	1887	2792	finally
      //   1899	1905	2792	finally
      //   1924	1931	2792	finally
      //   1943	1960	2792	finally
      //   1975	1981	2792	finally
      //   2014	2019	2792	finally
      //   2031	2036	2792	finally
      //   2048	2054	2792	finally
      //   2073	2090	2792	finally
      //   2123	2128	2792	finally
      //   2140	2145	2792	finally
      //   2157	2163	2792	finally
      //   2182	2200	2792	finally
      //   2233	2238	2792	finally
      //   2250	2255	2792	finally
      //   2267	2273	2792	finally
      //   2292	2310	2792	finally
      //   2329	2339	2792	finally
      //   2351	2359	2792	finally
      //   2374	2384	2792	finally
      //   2396	2404	2792	finally
      //   2419	2429	2792	finally
      //   2459	2466	2792	finally
      //   2478	2483	2792	finally
      //   2495	2500	2792	finally
      //   2512	2518	2792	finally
      //   2537	2544	2792	finally
      //   2556	2573	2792	finally
      //   2588	2594	2792	finally
      //   2627	2632	2792	finally
      //   2644	2649	2792	finally
      //   2661	2667	2792	finally
      //   2686	2703	2792	finally
      //   2726	2736	2792	finally
      //   2748	2756	2792	finally
      //   2801	2805	2792	finally
      //   2809	2817	2792	finally
      //   2821	2827	2792	finally
      //   2832	2838	2792	finally
      //   68	74	2796	java/io/IOException
      //   272	284	2796	java/io/IOException
      //   299	311	2796	java/io/IOException
      //   323	332	2796	java/io/IOException
      //   344	357	2796	java/io/IOException
      //   369	375	2796	java/io/IOException
      //   392	400	2796	java/io/IOException
      //   412	421	2796	java/io/IOException
      //   433	444	2796	java/io/IOException
      //   459	469	2796	java/io/IOException
      //   501	508	2796	java/io/IOException
      //   520	525	2796	java/io/IOException
      //   537	542	2796	java/io/IOException
      //   554	560	2796	java/io/IOException
      //   580	587	2796	java/io/IOException
      //   599	616	2796	java/io/IOException
      //   631	637	2796	java/io/IOException
      //   672	677	2796	java/io/IOException
      //   689	694	2796	java/io/IOException
      //   706	712	2796	java/io/IOException
      //   732	749	2796	java/io/IOException
      //   772	784	2796	java/io/IOException
      //   796	805	2796	java/io/IOException
      //   817	830	2796	java/io/IOException
      //   842	848	2796	java/io/IOException
      //   865	873	2796	java/io/IOException
      //   885	894	2796	java/io/IOException
      //   906	917	2796	java/io/IOException
      //   932	942	2796	java/io/IOException
      //   974	981	2796	java/io/IOException
      //   993	998	2796	java/io/IOException
      //   1010	1015	2796	java/io/IOException
      //   1027	1033	2796	java/io/IOException
      //   1053	1060	2796	java/io/IOException
      //   1072	1089	2796	java/io/IOException
      //   1104	1110	2796	java/io/IOException
      //   1145	1150	2796	java/io/IOException
      //   1162	1167	2796	java/io/IOException
      //   1179	1185	2796	java/io/IOException
      //   1205	1222	2796	java/io/IOException
      //   1257	1262	2796	java/io/IOException
      //   1274	1279	2796	java/io/IOException
      //   1291	1297	2796	java/io/IOException
      //   1317	1335	2796	java/io/IOException
      //   1370	1375	2796	java/io/IOException
      //   1387	1392	2796	java/io/IOException
      //   1404	1410	2796	java/io/IOException
      //   1430	1448	2796	java/io/IOException
      //   1483	1488	2796	java/io/IOException
      //   1500	1505	2796	java/io/IOException
      //   1517	1523	2796	java/io/IOException
      //   1543	1561	2796	java/io/IOException
      //   1596	1601	2796	java/io/IOException
      //   1613	1618	2796	java/io/IOException
      //   1630	1636	2796	java/io/IOException
      //   1656	1674	2796	java/io/IOException
      //   1709	1714	2796	java/io/IOException
      //   1726	1731	2796	java/io/IOException
      //   1743	1749	2796	java/io/IOException
      //   1769	1787	2796	java/io/IOException
      //   1806	1816	2796	java/io/IOException
      //   1846	1853	2796	java/io/IOException
      //   1865	1870	2796	java/io/IOException
      //   1882	1887	2796	java/io/IOException
      //   1899	1905	2796	java/io/IOException
      //   1924	1931	2796	java/io/IOException
      //   1943	1960	2796	java/io/IOException
      //   1975	1981	2796	java/io/IOException
      //   2014	2019	2796	java/io/IOException
      //   2031	2036	2796	java/io/IOException
      //   2048	2054	2796	java/io/IOException
      //   2073	2090	2796	java/io/IOException
      //   2123	2128	2796	java/io/IOException
      //   2140	2145	2796	java/io/IOException
      //   2157	2163	2796	java/io/IOException
      //   2182	2200	2796	java/io/IOException
      //   2233	2238	2796	java/io/IOException
      //   2250	2255	2796	java/io/IOException
      //   2267	2273	2796	java/io/IOException
      //   2292	2310	2796	java/io/IOException
      //   2329	2339	2796	java/io/IOException
      //   2351	2359	2796	java/io/IOException
      //   2374	2384	2796	java/io/IOException
      //   2396	2404	2796	java/io/IOException
      //   2419	2429	2796	java/io/IOException
      //   2459	2466	2796	java/io/IOException
      //   2478	2483	2796	java/io/IOException
      //   2495	2500	2796	java/io/IOException
      //   2512	2518	2796	java/io/IOException
      //   2537	2544	2796	java/io/IOException
      //   2556	2573	2796	java/io/IOException
      //   2588	2594	2796	java/io/IOException
      //   2627	2632	2796	java/io/IOException
      //   2644	2649	2796	java/io/IOException
      //   2661	2667	2796	java/io/IOException
      //   2686	2703	2796	java/io/IOException
      //   2726	2736	2796	java/io/IOException
      //   2748	2756	2796	java/io/IOException
      //   68	74	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   272	284	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   299	311	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   323	332	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   344	357	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   369	375	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   392	400	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   412	421	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   433	444	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   459	469	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   501	508	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   520	525	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   537	542	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   554	560	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   580	587	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   599	616	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   631	637	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   672	677	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   689	694	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   706	712	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   732	749	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   772	784	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   796	805	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   817	830	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   842	848	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   865	873	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   885	894	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   906	917	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   932	942	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   974	981	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   993	998	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1010	1015	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1027	1033	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1053	1060	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1072	1089	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1104	1110	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1145	1150	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1162	1167	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1179	1185	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1205	1222	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1257	1262	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1274	1279	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1291	1297	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1317	1335	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1370	1375	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1387	1392	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1404	1410	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1430	1448	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1483	1488	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1500	1505	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1517	1523	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1543	1561	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1596	1601	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1613	1618	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1630	1636	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1656	1674	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1709	1714	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1726	1731	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1743	1749	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1769	1787	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1806	1816	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1846	1853	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1865	1870	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1882	1887	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1899	1905	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1924	1931	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1943	1960	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1975	1981	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2014	2019	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2031	2036	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2048	2054	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2073	2090	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2123	2128	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2140	2145	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2157	2163	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2182	2200	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2233	2238	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2250	2255	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2267	2273	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2292	2310	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2329	2339	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2351	2359	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2374	2384	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2396	2404	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2419	2429	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2459	2466	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2478	2483	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2495	2500	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2512	2518	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2537	2544	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2556	2573	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2588	2594	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2627	2632	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2644	2649	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2661	2667	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2686	2703	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2726	2736	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   2748	2756	2827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   3083	3088	3099	finally
      //   3361	3366	3377	finally
      //   3083	3088	3393	java/io/IOException
      //   3361	3366	3397	java/io/IOException
    }
    
    private Class(GeneratedMessageLite.ExtendableBuilder<Class, ?> paramExtendableBuilder)
    {
      super();
      this.unknownFields = paramExtendableBuilder.getUnknownFields();
    }
    
    private Class(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static Class getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.flags_ = 6;
      this.fqName_ = 0;
      this.companionObjectName_ = 0;
      this.typeParameter_ = Collections.emptyList();
      this.supertype_ = Collections.emptyList();
      this.supertypeId_ = Collections.emptyList();
      this.nestedClassName_ = Collections.emptyList();
      this.constructor_ = Collections.emptyList();
      this.function_ = Collections.emptyList();
      this.property_ = Collections.emptyList();
      this.typeAlias_ = Collections.emptyList();
      this.enumEntry_ = Collections.emptyList();
      this.sealedSubclassFqName_ = Collections.emptyList();
      this.typeTable_ = ProtoBuf.TypeTable.getDefaultInstance();
      this.versionRequirement_ = Collections.emptyList();
      this.versionRequirementTable_ = ProtoBuf.VersionRequirementTable.getDefaultInstance();
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$8700();
    }
    
    public static Builder newBuilder(Class paramClass)
    {
      return newBuilder().mergeFrom(paramClass);
    }
    
    public static Class parseFrom(InputStream paramInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws IOException
    {
      return (Class)PARSER.parseFrom(paramInputStream, paramExtensionRegistryLite);
    }
    
    public int getCompanionObjectName()
    {
      return this.companionObjectName_;
    }
    
    public ProtoBuf.Constructor getConstructor(int paramInt)
    {
      return (ProtoBuf.Constructor)this.constructor_.get(paramInt);
    }
    
    public int getConstructorCount()
    {
      return this.constructor_.size();
    }
    
    public List<ProtoBuf.Constructor> getConstructorList()
    {
      return this.constructor_;
    }
    
    public Class getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public ProtoBuf.EnumEntry getEnumEntry(int paramInt)
    {
      return (ProtoBuf.EnumEntry)this.enumEntry_.get(paramInt);
    }
    
    public int getEnumEntryCount()
    {
      return this.enumEntry_.size();
    }
    
    public List<ProtoBuf.EnumEntry> getEnumEntryList()
    {
      return this.enumEntry_;
    }
    
    public int getFlags()
    {
      return this.flags_;
    }
    
    public int getFqName()
    {
      return this.fqName_;
    }
    
    public ProtoBuf.Function getFunction(int paramInt)
    {
      return (ProtoBuf.Function)this.function_.get(paramInt);
    }
    
    public int getFunctionCount()
    {
      return this.function_.size();
    }
    
    public List<ProtoBuf.Function> getFunctionList()
    {
      return this.function_;
    }
    
    public List<Integer> getNestedClassNameList()
    {
      return this.nestedClassName_;
    }
    
    public Parser<Class> getParserForType()
    {
      return PARSER;
    }
    
    public ProtoBuf.Property getProperty(int paramInt)
    {
      return (ProtoBuf.Property)this.property_.get(paramInt);
    }
    
    public int getPropertyCount()
    {
      return this.property_.size();
    }
    
    public List<ProtoBuf.Property> getPropertyList()
    {
      return this.property_;
    }
    
    public List<Integer> getSealedSubclassFqNameList()
    {
      return this.sealedSubclassFqName_;
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
        i = CodedOutputStream.computeInt32Size(1, this.flags_) + 0;
      } else {
        i = 0;
      }
      int k = 0;
      int m = k;
      while (k < this.supertypeId_.size())
      {
        m += CodedOutputStream.computeInt32SizeNoTag(((Integer)this.supertypeId_.get(k)).intValue());
        k++;
      }
      k = i + m;
      i = k;
      if (!getSupertypeIdList().isEmpty()) {
        i = k + 1 + CodedOutputStream.computeInt32SizeNoTag(m);
      }
      this.supertypeIdMemoizedSerializedSize = m;
      m = i;
      if ((this.bitField0_ & 0x2) == 2) {
        m = i + CodedOutputStream.computeInt32Size(3, this.fqName_);
      }
      i = m;
      if ((this.bitField0_ & 0x4) == 4) {
        i = m + CodedOutputStream.computeInt32Size(4, this.companionObjectName_);
      }
      for (m = 0; m < this.typeParameter_.size(); m++) {
        i += CodedOutputStream.computeMessageSize(5, (MessageLite)this.typeParameter_.get(m));
      }
      for (m = 0; m < this.supertype_.size(); m++) {
        i += CodedOutputStream.computeMessageSize(6, (MessageLite)this.supertype_.get(m));
      }
      k = 0;
      m = k;
      while (k < this.nestedClassName_.size())
      {
        m += CodedOutputStream.computeInt32SizeNoTag(((Integer)this.nestedClassName_.get(k)).intValue());
        k++;
      }
      k = i + m;
      i = k;
      if (!getNestedClassNameList().isEmpty()) {
        i = k + 1 + CodedOutputStream.computeInt32SizeNoTag(m);
      }
      this.nestedClassNameMemoizedSerializedSize = m;
      for (m = 0; m < this.constructor_.size(); m++) {
        i += CodedOutputStream.computeMessageSize(8, (MessageLite)this.constructor_.get(m));
      }
      for (m = 0; m < this.function_.size(); m++) {
        i += CodedOutputStream.computeMessageSize(9, (MessageLite)this.function_.get(m));
      }
      for (m = 0; m < this.property_.size(); m++) {
        i += CodedOutputStream.computeMessageSize(10, (MessageLite)this.property_.get(m));
      }
      for (m = 0; m < this.typeAlias_.size(); m++) {
        i += CodedOutputStream.computeMessageSize(11, (MessageLite)this.typeAlias_.get(m));
      }
      for (m = 0; m < this.enumEntry_.size(); m++) {
        i += CodedOutputStream.computeMessageSize(13, (MessageLite)this.enumEntry_.get(m));
      }
      k = 0;
      m = k;
      while (k < this.sealedSubclassFqName_.size())
      {
        m += CodedOutputStream.computeInt32SizeNoTag(((Integer)this.sealedSubclassFqName_.get(k)).intValue());
        k++;
      }
      k = i + m;
      i = k;
      if (!getSealedSubclassFqNameList().isEmpty()) {
        i = k + 2 + CodedOutputStream.computeInt32SizeNoTag(m);
      }
      this.sealedSubclassFqNameMemoizedSerializedSize = m;
      m = i;
      if ((this.bitField0_ & 0x8) == 8) {
        m = i + CodedOutputStream.computeMessageSize(30, this.typeTable_);
      }
      k = 0;
      for (i = j; i < this.versionRequirement_.size(); i++) {
        k += CodedOutputStream.computeInt32SizeNoTag(((Integer)this.versionRequirement_.get(i)).intValue());
      }
      m = m + k + getVersionRequirementList().size() * 2;
      i = m;
      if ((this.bitField0_ & 0x10) == 16) {
        i = m + CodedOutputStream.computeMessageSize(32, this.versionRequirementTable_);
      }
      i = i + extensionsSerializedSize() + this.unknownFields.size();
      this.memoizedSerializedSize = i;
      return i;
    }
    
    public ProtoBuf.Type getSupertype(int paramInt)
    {
      return (ProtoBuf.Type)this.supertype_.get(paramInt);
    }
    
    public int getSupertypeCount()
    {
      return this.supertype_.size();
    }
    
    public List<Integer> getSupertypeIdList()
    {
      return this.supertypeId_;
    }
    
    public List<ProtoBuf.Type> getSupertypeList()
    {
      return this.supertype_;
    }
    
    public ProtoBuf.TypeAlias getTypeAlias(int paramInt)
    {
      return (ProtoBuf.TypeAlias)this.typeAlias_.get(paramInt);
    }
    
    public int getTypeAliasCount()
    {
      return this.typeAlias_.size();
    }
    
    public List<ProtoBuf.TypeAlias> getTypeAliasList()
    {
      return this.typeAlias_;
    }
    
    public ProtoBuf.TypeParameter getTypeParameter(int paramInt)
    {
      return (ProtoBuf.TypeParameter)this.typeParameter_.get(paramInt);
    }
    
    public int getTypeParameterCount()
    {
      return this.typeParameter_.size();
    }
    
    public List<ProtoBuf.TypeParameter> getTypeParameterList()
    {
      return this.typeParameter_;
    }
    
    public ProtoBuf.TypeTable getTypeTable()
    {
      return this.typeTable_;
    }
    
    public List<Integer> getVersionRequirementList()
    {
      return this.versionRequirement_;
    }
    
    public ProtoBuf.VersionRequirementTable getVersionRequirementTable()
    {
      return this.versionRequirementTable_;
    }
    
    public boolean hasCompanionObjectName()
    {
      boolean bool;
      if ((this.bitField0_ & 0x4) == 4) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasFlags()
    {
      int i = this.bitField0_;
      boolean bool = true;
      if ((i & 0x1) != 1) {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasFqName()
    {
      boolean bool;
      if ((this.bitField0_ & 0x2) == 2) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasTypeTable()
    {
      boolean bool;
      if ((this.bitField0_ & 0x8) == 8) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasVersionRequirementTable()
    {
      boolean bool;
      if ((this.bitField0_ & 0x10) == 16) {
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
      if (!hasFqName())
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      for (i = 0; i < getTypeParameterCount(); i++) {
        if (!getTypeParameter(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
      }
      for (i = 0; i < getSupertypeCount(); i++) {
        if (!getSupertype(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
      }
      for (i = 0; i < getConstructorCount(); i++) {
        if (!getConstructor(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
      }
      for (i = 0; i < getFunctionCount(); i++) {
        if (!getFunction(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
      }
      for (i = 0; i < getPropertyCount(); i++) {
        if (!getProperty(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
      }
      for (i = 0; i < getTypeAliasCount(); i++) {
        if (!getTypeAlias(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
      }
      for (i = 0; i < getEnumEntryCount(); i++) {
        if (!getEnumEntry(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
      }
      if ((hasTypeTable()) && (!getTypeTable().isInitialized()))
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      if (!extensionsAreInitialized())
      {
        this.memoizedIsInitialized = ((byte)0);
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
      GeneratedMessageLite.ExtendableMessage.ExtensionWriter localExtensionWriter = newExtensionWriter();
      if ((this.bitField0_ & 0x1) == 1) {
        paramCodedOutputStream.writeInt32(1, this.flags_);
      }
      if (getSupertypeIdList().size() > 0)
      {
        paramCodedOutputStream.writeRawVarint32(18);
        paramCodedOutputStream.writeRawVarint32(this.supertypeIdMemoizedSerializedSize);
      }
      int i = 0;
      for (int j = 0; j < this.supertypeId_.size(); j++) {
        paramCodedOutputStream.writeInt32NoTag(((Integer)this.supertypeId_.get(j)).intValue());
      }
      if ((this.bitField0_ & 0x2) == 2) {
        paramCodedOutputStream.writeInt32(3, this.fqName_);
      }
      if ((this.bitField0_ & 0x4) == 4) {
        paramCodedOutputStream.writeInt32(4, this.companionObjectName_);
      }
      for (j = 0; j < this.typeParameter_.size(); j++) {
        paramCodedOutputStream.writeMessage(5, (MessageLite)this.typeParameter_.get(j));
      }
      for (j = 0; j < this.supertype_.size(); j++) {
        paramCodedOutputStream.writeMessage(6, (MessageLite)this.supertype_.get(j));
      }
      if (getNestedClassNameList().size() > 0)
      {
        paramCodedOutputStream.writeRawVarint32(58);
        paramCodedOutputStream.writeRawVarint32(this.nestedClassNameMemoizedSerializedSize);
      }
      for (j = 0; j < this.nestedClassName_.size(); j++) {
        paramCodedOutputStream.writeInt32NoTag(((Integer)this.nestedClassName_.get(j)).intValue());
      }
      for (j = 0; j < this.constructor_.size(); j++) {
        paramCodedOutputStream.writeMessage(8, (MessageLite)this.constructor_.get(j));
      }
      for (j = 0; j < this.function_.size(); j++) {
        paramCodedOutputStream.writeMessage(9, (MessageLite)this.function_.get(j));
      }
      for (j = 0; j < this.property_.size(); j++) {
        paramCodedOutputStream.writeMessage(10, (MessageLite)this.property_.get(j));
      }
      for (j = 0; j < this.typeAlias_.size(); j++) {
        paramCodedOutputStream.writeMessage(11, (MessageLite)this.typeAlias_.get(j));
      }
      for (j = 0; j < this.enumEntry_.size(); j++) {
        paramCodedOutputStream.writeMessage(13, (MessageLite)this.enumEntry_.get(j));
      }
      if (getSealedSubclassFqNameList().size() > 0)
      {
        paramCodedOutputStream.writeRawVarint32(130);
        paramCodedOutputStream.writeRawVarint32(this.sealedSubclassFqNameMemoizedSerializedSize);
      }
      for (j = 0; j < this.sealedSubclassFqName_.size(); j++) {
        paramCodedOutputStream.writeInt32NoTag(((Integer)this.sealedSubclassFqName_.get(j)).intValue());
      }
      j = i;
      if ((this.bitField0_ & 0x8) == 8) {
        paramCodedOutputStream.writeMessage(30, this.typeTable_);
      }
      for (j = i; j < this.versionRequirement_.size(); j++) {
        paramCodedOutputStream.writeInt32(31, ((Integer)this.versionRequirement_.get(j)).intValue());
      }
      if ((this.bitField0_ & 0x10) == 16) {
        paramCodedOutputStream.writeMessage(32, this.versionRequirementTable_);
      }
      localExtensionWriter.writeUntil(19000, paramCodedOutputStream);
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Builder
      extends GeneratedMessageLite.ExtendableBuilder<ProtoBuf.Class, Builder>
      implements ProtoBuf.ClassOrBuilder
    {
      private int bitField0_;
      private int companionObjectName_;
      private List<ProtoBuf.Constructor> constructor_ = Collections.emptyList();
      private List<ProtoBuf.EnumEntry> enumEntry_ = Collections.emptyList();
      private int flags_ = 6;
      private int fqName_;
      private List<ProtoBuf.Function> function_ = Collections.emptyList();
      private List<Integer> nestedClassName_ = Collections.emptyList();
      private List<ProtoBuf.Property> property_ = Collections.emptyList();
      private List<Integer> sealedSubclassFqName_ = Collections.emptyList();
      private List<Integer> supertypeId_ = Collections.emptyList();
      private List<ProtoBuf.Type> supertype_ = Collections.emptyList();
      private List<ProtoBuf.TypeAlias> typeAlias_ = Collections.emptyList();
      private List<ProtoBuf.TypeParameter> typeParameter_ = Collections.emptyList();
      private ProtoBuf.TypeTable typeTable_ = ProtoBuf.TypeTable.getDefaultInstance();
      private ProtoBuf.VersionRequirementTable versionRequirementTable_ = ProtoBuf.VersionRequirementTable.getDefaultInstance();
      private List<Integer> versionRequirement_ = Collections.emptyList();
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void ensureConstructorIsMutable()
      {
        if ((this.bitField0_ & 0x80) != 128)
        {
          this.constructor_ = new ArrayList(this.constructor_);
          this.bitField0_ |= 0x80;
        }
      }
      
      private void ensureEnumEntryIsMutable()
      {
        if ((this.bitField0_ & 0x800) != 2048)
        {
          this.enumEntry_ = new ArrayList(this.enumEntry_);
          this.bitField0_ |= 0x800;
        }
      }
      
      private void ensureFunctionIsMutable()
      {
        if ((this.bitField0_ & 0x100) != 256)
        {
          this.function_ = new ArrayList(this.function_);
          this.bitField0_ |= 0x100;
        }
      }
      
      private void ensureNestedClassNameIsMutable()
      {
        if ((this.bitField0_ & 0x40) != 64)
        {
          this.nestedClassName_ = new ArrayList(this.nestedClassName_);
          this.bitField0_ |= 0x40;
        }
      }
      
      private void ensurePropertyIsMutable()
      {
        if ((this.bitField0_ & 0x200) != 512)
        {
          this.property_ = new ArrayList(this.property_);
          this.bitField0_ |= 0x200;
        }
      }
      
      private void ensureSealedSubclassFqNameIsMutable()
      {
        if ((this.bitField0_ & 0x1000) != 4096)
        {
          this.sealedSubclassFqName_ = new ArrayList(this.sealedSubclassFqName_);
          this.bitField0_ |= 0x1000;
        }
      }
      
      private void ensureSupertypeIdIsMutable()
      {
        if ((this.bitField0_ & 0x20) != 32)
        {
          this.supertypeId_ = new ArrayList(this.supertypeId_);
          this.bitField0_ |= 0x20;
        }
      }
      
      private void ensureSupertypeIsMutable()
      {
        if ((this.bitField0_ & 0x10) != 16)
        {
          this.supertype_ = new ArrayList(this.supertype_);
          this.bitField0_ |= 0x10;
        }
      }
      
      private void ensureTypeAliasIsMutable()
      {
        if ((this.bitField0_ & 0x400) != 1024)
        {
          this.typeAlias_ = new ArrayList(this.typeAlias_);
          this.bitField0_ |= 0x400;
        }
      }
      
      private void ensureTypeParameterIsMutable()
      {
        if ((this.bitField0_ & 0x8) != 8)
        {
          this.typeParameter_ = new ArrayList(this.typeParameter_);
          this.bitField0_ |= 0x8;
        }
      }
      
      private void ensureVersionRequirementIsMutable()
      {
        if ((this.bitField0_ & 0x4000) != 16384)
        {
          this.versionRequirement_ = new ArrayList(this.versionRequirement_);
          this.bitField0_ |= 0x4000;
        }
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public ProtoBuf.Class build()
      {
        ProtoBuf.Class localClass = buildPartial();
        if (localClass.isInitialized()) {
          return localClass;
        }
        throw newUninitializedMessageException(localClass);
      }
      
      public ProtoBuf.Class buildPartial()
      {
        ProtoBuf.Class localClass = new ProtoBuf.Class(this, null);
        int i = this.bitField0_;
        int j = 1;
        if ((i & 0x1) != 1) {
          j = 0;
        }
        ProtoBuf.Class.access$8902(localClass, this.flags_);
        int k = j;
        if ((i & 0x2) == 2) {
          k = j | 0x2;
        }
        ProtoBuf.Class.access$9002(localClass, this.fqName_);
        j = k;
        if ((i & 0x4) == 4) {
          j = k | 0x4;
        }
        ProtoBuf.Class.access$9102(localClass, this.companionObjectName_);
        if ((this.bitField0_ & 0x8) == 8)
        {
          this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
          this.bitField0_ &= 0xFFFFFFF7;
        }
        ProtoBuf.Class.access$9202(localClass, this.typeParameter_);
        if ((this.bitField0_ & 0x10) == 16)
        {
          this.supertype_ = Collections.unmodifiableList(this.supertype_);
          this.bitField0_ &= 0xFFFFFFEF;
        }
        ProtoBuf.Class.access$9302(localClass, this.supertype_);
        if ((this.bitField0_ & 0x20) == 32)
        {
          this.supertypeId_ = Collections.unmodifiableList(this.supertypeId_);
          this.bitField0_ &= 0xFFFFFFDF;
        }
        ProtoBuf.Class.access$9402(localClass, this.supertypeId_);
        if ((this.bitField0_ & 0x40) == 64)
        {
          this.nestedClassName_ = Collections.unmodifiableList(this.nestedClassName_);
          this.bitField0_ &= 0xFFFFFFBF;
        }
        ProtoBuf.Class.access$9502(localClass, this.nestedClassName_);
        if ((this.bitField0_ & 0x80) == 128)
        {
          this.constructor_ = Collections.unmodifiableList(this.constructor_);
          this.bitField0_ &= 0xFF7F;
        }
        ProtoBuf.Class.access$9602(localClass, this.constructor_);
        if ((this.bitField0_ & 0x100) == 256)
        {
          this.function_ = Collections.unmodifiableList(this.function_);
          this.bitField0_ &= 0xFEFF;
        }
        ProtoBuf.Class.access$9702(localClass, this.function_);
        if ((this.bitField0_ & 0x200) == 512)
        {
          this.property_ = Collections.unmodifiableList(this.property_);
          this.bitField0_ &= 0xFDFF;
        }
        ProtoBuf.Class.access$9802(localClass, this.property_);
        if ((this.bitField0_ & 0x400) == 1024)
        {
          this.typeAlias_ = Collections.unmodifiableList(this.typeAlias_);
          this.bitField0_ &= 0xFBFF;
        }
        ProtoBuf.Class.access$9902(localClass, this.typeAlias_);
        if ((this.bitField0_ & 0x800) == 2048)
        {
          this.enumEntry_ = Collections.unmodifiableList(this.enumEntry_);
          this.bitField0_ &= 0xF7FF;
        }
        ProtoBuf.Class.access$10002(localClass, this.enumEntry_);
        if ((this.bitField0_ & 0x1000) == 4096)
        {
          this.sealedSubclassFqName_ = Collections.unmodifiableList(this.sealedSubclassFqName_);
          this.bitField0_ &= 0xEFFF;
        }
        ProtoBuf.Class.access$10102(localClass, this.sealedSubclassFqName_);
        k = j;
        if ((i & 0x2000) == 8192) {
          k = j | 0x8;
        }
        ProtoBuf.Class.access$10202(localClass, this.typeTable_);
        if ((this.bitField0_ & 0x4000) == 16384)
        {
          this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
          this.bitField0_ &= 0xBFFF;
        }
        ProtoBuf.Class.access$10302(localClass, this.versionRequirement_);
        j = k;
        if ((i & 0x8000) == 32768) {
          j = k | 0x10;
        }
        ProtoBuf.Class.access$10402(localClass, this.versionRequirementTable_);
        ProtoBuf.Class.access$10502(localClass, j);
        return localClass;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public ProtoBuf.Constructor getConstructor(int paramInt)
      {
        return (ProtoBuf.Constructor)this.constructor_.get(paramInt);
      }
      
      public int getConstructorCount()
      {
        return this.constructor_.size();
      }
      
      public ProtoBuf.Class getDefaultInstanceForType()
      {
        return ProtoBuf.Class.getDefaultInstance();
      }
      
      public ProtoBuf.EnumEntry getEnumEntry(int paramInt)
      {
        return (ProtoBuf.EnumEntry)this.enumEntry_.get(paramInt);
      }
      
      public int getEnumEntryCount()
      {
        return this.enumEntry_.size();
      }
      
      public ProtoBuf.Function getFunction(int paramInt)
      {
        return (ProtoBuf.Function)this.function_.get(paramInt);
      }
      
      public int getFunctionCount()
      {
        return this.function_.size();
      }
      
      public ProtoBuf.Property getProperty(int paramInt)
      {
        return (ProtoBuf.Property)this.property_.get(paramInt);
      }
      
      public int getPropertyCount()
      {
        return this.property_.size();
      }
      
      public ProtoBuf.Type getSupertype(int paramInt)
      {
        return (ProtoBuf.Type)this.supertype_.get(paramInt);
      }
      
      public int getSupertypeCount()
      {
        return this.supertype_.size();
      }
      
      public ProtoBuf.TypeAlias getTypeAlias(int paramInt)
      {
        return (ProtoBuf.TypeAlias)this.typeAlias_.get(paramInt);
      }
      
      public int getTypeAliasCount()
      {
        return this.typeAlias_.size();
      }
      
      public ProtoBuf.TypeParameter getTypeParameter(int paramInt)
      {
        return (ProtoBuf.TypeParameter)this.typeParameter_.get(paramInt);
      }
      
      public int getTypeParameterCount()
      {
        return this.typeParameter_.size();
      }
      
      public ProtoBuf.TypeTable getTypeTable()
      {
        return this.typeTable_;
      }
      
      public boolean hasFqName()
      {
        boolean bool;
        if ((this.bitField0_ & 0x2) == 2) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasTypeTable()
      {
        boolean bool;
        if ((this.bitField0_ & 0x2000) == 8192) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public final boolean isInitialized()
      {
        if (!hasFqName()) {
          return false;
        }
        for (int i = 0; i < getTypeParameterCount(); i++) {
          if (!getTypeParameter(i).isInitialized()) {
            return false;
          }
        }
        for (i = 0; i < getSupertypeCount(); i++) {
          if (!getSupertype(i).isInitialized()) {
            return false;
          }
        }
        for (i = 0; i < getConstructorCount(); i++) {
          if (!getConstructor(i).isInitialized()) {
            return false;
          }
        }
        for (i = 0; i < getFunctionCount(); i++) {
          if (!getFunction(i).isInitialized()) {
            return false;
          }
        }
        for (i = 0; i < getPropertyCount(); i++) {
          if (!getProperty(i).isInitialized()) {
            return false;
          }
        }
        for (i = 0; i < getTypeAliasCount(); i++) {
          if (!getTypeAlias(i).isInitialized()) {
            return false;
          }
        }
        for (i = 0; i < getEnumEntryCount(); i++) {
          if (!getEnumEntry(i).isInitialized()) {
            return false;
          }
        }
        if ((hasTypeTable()) && (!getTypeTable().isInitialized())) {
          return false;
        }
        return extensionsAreInitialized();
      }
      
      public Builder mergeFrom(ProtoBuf.Class paramClass)
      {
        if (paramClass == ProtoBuf.Class.getDefaultInstance()) {
          return this;
        }
        if (paramClass.hasFlags()) {
          setFlags(paramClass.getFlags());
        }
        if (paramClass.hasFqName()) {
          setFqName(paramClass.getFqName());
        }
        if (paramClass.hasCompanionObjectName()) {
          setCompanionObjectName(paramClass.getCompanionObjectName());
        }
        if (!paramClass.typeParameter_.isEmpty()) {
          if (this.typeParameter_.isEmpty())
          {
            this.typeParameter_ = paramClass.typeParameter_;
            this.bitField0_ &= 0xFFFFFFF7;
          }
          else
          {
            ensureTypeParameterIsMutable();
            this.typeParameter_.addAll(paramClass.typeParameter_);
          }
        }
        if (!paramClass.supertype_.isEmpty()) {
          if (this.supertype_.isEmpty())
          {
            this.supertype_ = paramClass.supertype_;
            this.bitField0_ &= 0xFFFFFFEF;
          }
          else
          {
            ensureSupertypeIsMutable();
            this.supertype_.addAll(paramClass.supertype_);
          }
        }
        if (!paramClass.supertypeId_.isEmpty()) {
          if (this.supertypeId_.isEmpty())
          {
            this.supertypeId_ = paramClass.supertypeId_;
            this.bitField0_ &= 0xFFFFFFDF;
          }
          else
          {
            ensureSupertypeIdIsMutable();
            this.supertypeId_.addAll(paramClass.supertypeId_);
          }
        }
        if (!paramClass.nestedClassName_.isEmpty()) {
          if (this.nestedClassName_.isEmpty())
          {
            this.nestedClassName_ = paramClass.nestedClassName_;
            this.bitField0_ &= 0xFFFFFFBF;
          }
          else
          {
            ensureNestedClassNameIsMutable();
            this.nestedClassName_.addAll(paramClass.nestedClassName_);
          }
        }
        if (!paramClass.constructor_.isEmpty()) {
          if (this.constructor_.isEmpty())
          {
            this.constructor_ = paramClass.constructor_;
            this.bitField0_ &= 0xFF7F;
          }
          else
          {
            ensureConstructorIsMutable();
            this.constructor_.addAll(paramClass.constructor_);
          }
        }
        if (!paramClass.function_.isEmpty()) {
          if (this.function_.isEmpty())
          {
            this.function_ = paramClass.function_;
            this.bitField0_ &= 0xFEFF;
          }
          else
          {
            ensureFunctionIsMutable();
            this.function_.addAll(paramClass.function_);
          }
        }
        if (!paramClass.property_.isEmpty()) {
          if (this.property_.isEmpty())
          {
            this.property_ = paramClass.property_;
            this.bitField0_ &= 0xFDFF;
          }
          else
          {
            ensurePropertyIsMutable();
            this.property_.addAll(paramClass.property_);
          }
        }
        if (!paramClass.typeAlias_.isEmpty()) {
          if (this.typeAlias_.isEmpty())
          {
            this.typeAlias_ = paramClass.typeAlias_;
            this.bitField0_ &= 0xFBFF;
          }
          else
          {
            ensureTypeAliasIsMutable();
            this.typeAlias_.addAll(paramClass.typeAlias_);
          }
        }
        if (!paramClass.enumEntry_.isEmpty()) {
          if (this.enumEntry_.isEmpty())
          {
            this.enumEntry_ = paramClass.enumEntry_;
            this.bitField0_ &= 0xF7FF;
          }
          else
          {
            ensureEnumEntryIsMutable();
            this.enumEntry_.addAll(paramClass.enumEntry_);
          }
        }
        if (!paramClass.sealedSubclassFqName_.isEmpty()) {
          if (this.sealedSubclassFqName_.isEmpty())
          {
            this.sealedSubclassFqName_ = paramClass.sealedSubclassFqName_;
            this.bitField0_ &= 0xEFFF;
          }
          else
          {
            ensureSealedSubclassFqNameIsMutable();
            this.sealedSubclassFqName_.addAll(paramClass.sealedSubclassFqName_);
          }
        }
        if (paramClass.hasTypeTable()) {
          mergeTypeTable(paramClass.getTypeTable());
        }
        if (!paramClass.versionRequirement_.isEmpty()) {
          if (this.versionRequirement_.isEmpty())
          {
            this.versionRequirement_ = paramClass.versionRequirement_;
            this.bitField0_ &= 0xBFFF;
          }
          else
          {
            ensureVersionRequirementIsMutable();
            this.versionRequirement_.addAll(paramClass.versionRequirement_);
          }
        }
        if (paramClass.hasVersionRequirementTable()) {
          mergeVersionRequirementTable(paramClass.getVersionRequirementTable());
        }
        mergeExtensionFields(paramClass);
        setUnknownFields(getUnknownFields().concat(paramClass.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 451	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 457 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 213	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_1
        //   29: aload_3
        //   30: astore_2
        //   31: goto +15 -> 46
        //   34: astore_1
        //   35: aload_1
        //   36: invokevirtual 460	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   39: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class
        //   42: astore_2
        //   43: aload_1
        //   44: athrow
        //   45: astore_1
        //   46: aload_2
        //   47: ifnull +9 -> 56
        //   50: aload_0
        //   51: aload_2
        //   52: invokevirtual 213	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class$Builder;
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
      
      public Builder mergeTypeTable(ProtoBuf.TypeTable paramTypeTable)
      {
        if (((this.bitField0_ & 0x2000) == 8192) && (this.typeTable_ != ProtoBuf.TypeTable.getDefaultInstance())) {
          this.typeTable_ = ProtoBuf.TypeTable.newBuilder(this.typeTable_).mergeFrom(paramTypeTable).buildPartial();
        } else {
          this.typeTable_ = paramTypeTable;
        }
        this.bitField0_ |= 0x2000;
        return this;
      }
      
      public Builder mergeVersionRequirementTable(ProtoBuf.VersionRequirementTable paramVersionRequirementTable)
      {
        if (((this.bitField0_ & 0x8000) == 32768) && (this.versionRequirementTable_ != ProtoBuf.VersionRequirementTable.getDefaultInstance())) {
          this.versionRequirementTable_ = ProtoBuf.VersionRequirementTable.newBuilder(this.versionRequirementTable_).mergeFrom(paramVersionRequirementTable).buildPartial();
        } else {
          this.versionRequirementTable_ = paramVersionRequirementTable;
        }
        this.bitField0_ |= 0x8000;
        return this;
      }
      
      public Builder setCompanionObjectName(int paramInt)
      {
        this.bitField0_ |= 0x4;
        this.companionObjectName_ = paramInt;
        return this;
      }
      
      public Builder setFlags(int paramInt)
      {
        this.bitField0_ |= 0x1;
        this.flags_ = paramInt;
        return this;
      }
      
      public Builder setFqName(int paramInt)
      {
        this.bitField0_ |= 0x2;
        this.fqName_ = paramInt;
        return this;
      }
    }
    
    public static enum Kind
      implements Internal.EnumLite
    {
      private static Internal.EnumLiteMap<Kind> internalValueMap = new Internal.EnumLiteMap()
      {
        public ProtoBuf.Class.Kind findValueByNumber(int paramAnonymousInt)
        {
          return ProtoBuf.Class.Kind.valueOf(paramAnonymousInt);
        }
      };
      private final int value;
      
      static
      {
        ENUM_CLASS = new Kind("ENUM_CLASS", 2, 2, 2);
        ENUM_ENTRY = new Kind("ENUM_ENTRY", 3, 3, 3);
        ANNOTATION_CLASS = new Kind("ANNOTATION_CLASS", 4, 4, 4);
        OBJECT = new Kind("OBJECT", 5, 5, 5);
        Kind localKind = new Kind("COMPANION_OBJECT", 6, 6, 6);
        COMPANION_OBJECT = localKind;
        $VALUES = new Kind[] { CLASS, INTERFACE, ENUM_CLASS, ENUM_ENTRY, ANNOTATION_CLASS, OBJECT, localKind };
      }
      
      private Kind(int paramInt1, int paramInt2)
      {
        this.value = paramInt2;
      }
      
      public static Kind valueOf(int paramInt)
      {
        switch (paramInt)
        {
        default: 
          return null;
        case 6: 
          return COMPANION_OBJECT;
        case 5: 
          return OBJECT;
        case 4: 
          return ANNOTATION_CLASS;
        case 3: 
          return ENUM_ENTRY;
        case 2: 
          return ENUM_CLASS;
        case 1: 
          return INTERFACE;
        }
        return CLASS;
      }
      
      public final int getNumber()
      {
        return this.value;
      }
    }
  }
  
  public static final class Constructor
    extends GeneratedMessageLite.ExtendableMessage<Constructor>
    implements ProtoBuf.ConstructorOrBuilder
  {
    public static Parser<Constructor> PARSER = new AbstractParser()
    {
      public ProtoBuf.Constructor parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new ProtoBuf.Constructor(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final Constructor defaultInstance;
    private int bitField0_;
    private int flags_;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private final ByteString unknownFields;
    private List<ProtoBuf.ValueParameter> valueParameter_;
    private List<Integer> versionRequirement_;
    
    static
    {
      Constructor localConstructor = new Constructor(true);
      defaultInstance = localConstructor;
      localConstructor.initFields();
    }
    
    /* Error */
    private Constructor(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 55	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite$ExtendableMessage:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: i2b
      //   7: putfield 57	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:memoizedIsInitialized	B
      //   10: aload_0
      //   11: iconst_m1
      //   12: putfield 59	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:memoizedSerializedSize	I
      //   15: aload_0
      //   16: invokespecial 48	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:initFields	()V
      //   19: invokestatic 65	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   22: astore_3
      //   23: aload_3
      //   24: iconst_1
      //   25: invokestatic 71	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   28: astore 4
      //   30: iconst_0
      //   31: istore 5
      //   33: iconst_0
      //   34: istore 6
      //   36: iload 5
      //   38: ifne +656 -> 694
      //   41: iload 6
      //   43: istore 7
      //   45: iload 6
      //   47: istore 8
      //   49: iload 6
      //   51: istore 9
      //   53: aload_1
      //   54: invokevirtual 77	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   57: istore 10
      //   59: iload 10
      //   61: ifeq +510 -> 571
      //   64: iload 10
      //   66: bipush 8
      //   68: if_icmpeq +458 -> 526
      //   71: iload 10
      //   73: bipush 18
      //   75: if_icmpeq +344 -> 419
      //   78: iload 10
      //   80: sipush 248
      //   83: if_icmpeq +230 -> 313
      //   86: iload 10
      //   88: sipush 250
      //   91: if_icmpeq +31 -> 122
      //   94: iload 6
      //   96: istore 7
      //   98: iload 6
      //   100: istore 8
      //   102: iload 6
      //   104: istore 9
      //   106: aload_0
      //   107: aload_1
      //   108: aload 4
      //   110: aload_2
      //   111: iload 10
      //   113: invokevirtual 81	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   116: ifne -80 -> 36
      //   119: goto +452 -> 571
      //   122: iload 6
      //   124: istore 7
      //   126: iload 6
      //   128: istore 8
      //   130: iload 6
      //   132: istore 9
      //   134: aload_1
      //   135: aload_1
      //   136: invokevirtual 84	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readRawVarint32	()I
      //   139: invokevirtual 88	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:pushLimit	(I)I
      //   142: istore 11
      //   144: iload 6
      //   146: istore 10
      //   148: iload 6
      //   150: iconst_4
      //   151: iand
      //   152: iconst_4
      //   153: if_icmpeq +84 -> 237
      //   156: iload 6
      //   158: istore 10
      //   160: iload 6
      //   162: istore 7
      //   164: iload 6
      //   166: istore 8
      //   168: iload 6
      //   170: istore 9
      //   172: aload_1
      //   173: invokevirtual 91	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
      //   176: ifle +61 -> 237
      //   179: iload 6
      //   181: istore 7
      //   183: iload 6
      //   185: istore 8
      //   187: iload 6
      //   189: istore 9
      //   191: new 93	java/util/ArrayList
      //   194: astore 12
      //   196: iload 6
      //   198: istore 7
      //   200: iload 6
      //   202: istore 8
      //   204: iload 6
      //   206: istore 9
      //   208: aload 12
      //   210: invokespecial 94	java/util/ArrayList:<init>	()V
      //   213: iload 6
      //   215: istore 7
      //   217: iload 6
      //   219: istore 8
      //   221: iload 6
      //   223: istore 9
      //   225: aload_0
      //   226: aload 12
      //   228: putfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:versionRequirement_	Ljava/util/List;
      //   231: iload 6
      //   233: iconst_4
      //   234: ior
      //   235: istore 10
      //   237: iload 10
      //   239: istore 7
      //   241: iload 10
      //   243: istore 8
      //   245: iload 10
      //   247: istore 9
      //   249: aload_1
      //   250: invokevirtual 91	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
      //   253: ifle +35 -> 288
      //   256: iload 10
      //   258: istore 7
      //   260: iload 10
      //   262: istore 8
      //   264: iload 10
      //   266: istore 9
      //   268: aload_0
      //   269: getfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:versionRequirement_	Ljava/util/List;
      //   272: aload_1
      //   273: invokevirtual 99	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   276: invokestatic 105	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   279: invokeinterface 111 2 0
      //   284: pop
      //   285: goto -48 -> 237
      //   288: iload 10
      //   290: istore 7
      //   292: iload 10
      //   294: istore 8
      //   296: iload 10
      //   298: istore 9
      //   300: aload_1
      //   301: iload 11
      //   303: invokevirtual 115	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:popLimit	(I)V
      //   306: iload 10
      //   308: istore 6
      //   310: goto -274 -> 36
      //   313: iload 6
      //   315: istore 10
      //   317: iload 6
      //   319: iconst_4
      //   320: iand
      //   321: iconst_4
      //   322: if_icmpeq +61 -> 383
      //   325: iload 6
      //   327: istore 7
      //   329: iload 6
      //   331: istore 8
      //   333: iload 6
      //   335: istore 9
      //   337: new 93	java/util/ArrayList
      //   340: astore 12
      //   342: iload 6
      //   344: istore 7
      //   346: iload 6
      //   348: istore 8
      //   350: iload 6
      //   352: istore 9
      //   354: aload 12
      //   356: invokespecial 94	java/util/ArrayList:<init>	()V
      //   359: iload 6
      //   361: istore 7
      //   363: iload 6
      //   365: istore 8
      //   367: iload 6
      //   369: istore 9
      //   371: aload_0
      //   372: aload 12
      //   374: putfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:versionRequirement_	Ljava/util/List;
      //   377: iload 6
      //   379: iconst_4
      //   380: ior
      //   381: istore 10
      //   383: iload 10
      //   385: istore 7
      //   387: iload 10
      //   389: istore 8
      //   391: iload 10
      //   393: istore 9
      //   395: aload_0
      //   396: getfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:versionRequirement_	Ljava/util/List;
      //   399: aload_1
      //   400: invokevirtual 99	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   403: invokestatic 105	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   406: invokeinterface 111 2 0
      //   411: pop
      //   412: iload 10
      //   414: istore 6
      //   416: goto -380 -> 36
      //   419: iload 6
      //   421: istore 10
      //   423: iload 6
      //   425: iconst_2
      //   426: iand
      //   427: iconst_2
      //   428: if_icmpeq +61 -> 489
      //   431: iload 6
      //   433: istore 7
      //   435: iload 6
      //   437: istore 8
      //   439: iload 6
      //   441: istore 9
      //   443: new 93	java/util/ArrayList
      //   446: astore 12
      //   448: iload 6
      //   450: istore 7
      //   452: iload 6
      //   454: istore 8
      //   456: iload 6
      //   458: istore 9
      //   460: aload 12
      //   462: invokespecial 94	java/util/ArrayList:<init>	()V
      //   465: iload 6
      //   467: istore 7
      //   469: iload 6
      //   471: istore 8
      //   473: iload 6
      //   475: istore 9
      //   477: aload_0
      //   478: aload 12
      //   480: putfield 117	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:valueParameter_	Ljava/util/List;
      //   483: iload 6
      //   485: iconst_2
      //   486: ior
      //   487: istore 10
      //   489: iload 10
      //   491: istore 7
      //   493: iload 10
      //   495: istore 8
      //   497: iload 10
      //   499: istore 9
      //   501: aload_0
      //   502: getfield 117	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:valueParameter_	Ljava/util/List;
      //   505: aload_1
      //   506: getstatic 120	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   509: aload_2
      //   510: invokevirtual 124	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   513: invokeinterface 111 2 0
      //   518: pop
      //   519: iload 10
      //   521: istore 6
      //   523: goto -487 -> 36
      //   526: iload 6
      //   528: istore 7
      //   530: iload 6
      //   532: istore 8
      //   534: iload 6
      //   536: istore 9
      //   538: aload_0
      //   539: aload_0
      //   540: getfield 126	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:bitField0_	I
      //   543: iconst_1
      //   544: ior
      //   545: putfield 126	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:bitField0_	I
      //   548: iload 6
      //   550: istore 7
      //   552: iload 6
      //   554: istore 8
      //   556: iload 6
      //   558: istore 9
      //   560: aload_0
      //   561: aload_1
      //   562: invokevirtual 99	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   565: putfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:flags_	I
      //   568: goto -532 -> 36
      //   571: iconst_1
      //   572: istore 5
      //   574: goto -538 -> 36
      //   577: astore_1
      //   578: goto +45 -> 623
      //   581: astore_2
      //   582: iload 8
      //   584: istore 7
      //   586: new 52	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   589: astore_1
      //   590: iload 8
      //   592: istore 7
      //   594: aload_1
      //   595: aload_2
      //   596: invokevirtual 132	java/io/IOException:getMessage	()Ljava/lang/String;
      //   599: invokespecial 135	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   602: iload 8
      //   604: istore 7
      //   606: aload_1
      //   607: aload_0
      //   608: invokevirtual 139	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   611: athrow
      //   612: astore_1
      //   613: iload 9
      //   615: istore 7
      //   617: aload_1
      //   618: aload_0
      //   619: invokevirtual 139	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   622: athrow
      //   623: iload 7
      //   625: iconst_2
      //   626: iand
      //   627: iconst_2
      //   628: if_icmpne +14 -> 642
      //   631: aload_0
      //   632: aload_0
      //   633: getfield 117	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:valueParameter_	Ljava/util/List;
      //   636: invokestatic 145	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   639: putfield 117	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:valueParameter_	Ljava/util/List;
      //   642: iload 7
      //   644: iconst_4
      //   645: iand
      //   646: iconst_4
      //   647: if_icmpne +14 -> 661
      //   650: aload_0
      //   651: aload_0
      //   652: getfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:versionRequirement_	Ljava/util/List;
      //   655: invokestatic 145	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   658: putfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:versionRequirement_	Ljava/util/List;
      //   661: aload 4
      //   663: invokevirtual 148	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   666: aload_0
      //   667: aload_3
      //   668: invokevirtual 154	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   671: putfield 156	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   674: goto +14 -> 688
      //   677: astore_1
      //   678: aload_0
      //   679: aload_3
      //   680: invokevirtual 154	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   683: putfield 156	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   686: aload_1
      //   687: athrow
      //   688: aload_0
      //   689: invokevirtual 159	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:makeExtensionsImmutable	()V
      //   692: aload_1
      //   693: athrow
      //   694: iload 6
      //   696: iconst_2
      //   697: iand
      //   698: iconst_2
      //   699: if_icmpne +14 -> 713
      //   702: aload_0
      //   703: aload_0
      //   704: getfield 117	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:valueParameter_	Ljava/util/List;
      //   707: invokestatic 145	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   710: putfield 117	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:valueParameter_	Ljava/util/List;
      //   713: iload 6
      //   715: iconst_4
      //   716: iand
      //   717: iconst_4
      //   718: if_icmpne +14 -> 732
      //   721: aload_0
      //   722: aload_0
      //   723: getfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:versionRequirement_	Ljava/util/List;
      //   726: invokestatic 145	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   729: putfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:versionRequirement_	Ljava/util/List;
      //   732: aload 4
      //   734: invokevirtual 148	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   737: aload_0
      //   738: aload_3
      //   739: invokevirtual 154	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   742: putfield 156	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   745: goto +14 -> 759
      //   748: astore_1
      //   749: aload_0
      //   750: aload_3
      //   751: invokevirtual 154	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   754: putfield 156	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   757: aload_1
      //   758: athrow
      //   759: aload_0
      //   760: invokevirtual 159	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:makeExtensionsImmutable	()V
      //   763: return
      //   764: astore_2
      //   765: goto -99 -> 666
      //   768: astore_1
      //   769: goto -32 -> 737
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	772	0	this	Constructor
      //   0	772	1	paramCodedInputStream	CodedInputStream
      //   0	772	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   22	729	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   28	705	4	localCodedOutputStream	CodedOutputStream
      //   31	542	5	i	int
      //   34	683	6	j	int
      //   43	603	7	k	int
      //   47	556	8	m	int
      //   51	563	9	n	int
      //   57	463	10	i1	int
      //   142	160	11	i2	int
      //   194	285	12	localArrayList	ArrayList
      // Exception table:
      //   from	to	target	type
      //   53	59	577	finally
      //   106	119	577	finally
      //   134	144	577	finally
      //   172	179	577	finally
      //   191	196	577	finally
      //   208	213	577	finally
      //   225	231	577	finally
      //   249	256	577	finally
      //   268	285	577	finally
      //   300	306	577	finally
      //   337	342	577	finally
      //   354	359	577	finally
      //   371	377	577	finally
      //   395	412	577	finally
      //   443	448	577	finally
      //   460	465	577	finally
      //   477	483	577	finally
      //   501	519	577	finally
      //   538	548	577	finally
      //   560	568	577	finally
      //   586	590	577	finally
      //   594	602	577	finally
      //   606	612	577	finally
      //   617	623	577	finally
      //   53	59	581	java/io/IOException
      //   106	119	581	java/io/IOException
      //   134	144	581	java/io/IOException
      //   172	179	581	java/io/IOException
      //   191	196	581	java/io/IOException
      //   208	213	581	java/io/IOException
      //   225	231	581	java/io/IOException
      //   249	256	581	java/io/IOException
      //   268	285	581	java/io/IOException
      //   300	306	581	java/io/IOException
      //   337	342	581	java/io/IOException
      //   354	359	581	java/io/IOException
      //   371	377	581	java/io/IOException
      //   395	412	581	java/io/IOException
      //   443	448	581	java/io/IOException
      //   460	465	581	java/io/IOException
      //   477	483	581	java/io/IOException
      //   501	519	581	java/io/IOException
      //   538	548	581	java/io/IOException
      //   560	568	581	java/io/IOException
      //   53	59	612	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   106	119	612	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   134	144	612	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   172	179	612	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   191	196	612	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   208	213	612	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   225	231	612	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   249	256	612	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   268	285	612	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   300	306	612	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   337	342	612	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   354	359	612	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   371	377	612	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   395	412	612	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   443	448	612	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   460	465	612	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   477	483	612	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   501	519	612	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   538	548	612	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   560	568	612	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   661	666	677	finally
      //   732	737	748	finally
      //   661	666	764	java/io/IOException
      //   732	737	768	java/io/IOException
    }
    
    private Constructor(GeneratedMessageLite.ExtendableBuilder<Constructor, ?> paramExtendableBuilder)
    {
      super();
      this.unknownFields = paramExtendableBuilder.getUnknownFields();
    }
    
    private Constructor(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static Constructor getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.flags_ = 6;
      this.valueParameter_ = Collections.emptyList();
      this.versionRequirement_ = Collections.emptyList();
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$12500();
    }
    
    public static Builder newBuilder(Constructor paramConstructor)
    {
      return newBuilder().mergeFrom(paramConstructor);
    }
    
    public Constructor getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public int getFlags()
    {
      return this.flags_;
    }
    
    public Parser<Constructor> getParserForType()
    {
      return PARSER;
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
        i = CodedOutputStream.computeInt32Size(1, this.flags_) + 0;
      } else {
        i = 0;
      }
      for (int k = 0; k < this.valueParameter_.size(); k++) {
        i += CodedOutputStream.computeMessageSize(2, (MessageLite)this.valueParameter_.get(k));
      }
      int m = 0;
      k = j;
      j = m;
      while (k < this.versionRequirement_.size())
      {
        j += CodedOutputStream.computeInt32SizeNoTag(((Integer)this.versionRequirement_.get(k)).intValue());
        k++;
      }
      i = i + j + getVersionRequirementList().size() * 2 + extensionsSerializedSize() + this.unknownFields.size();
      this.memoizedSerializedSize = i;
      return i;
    }
    
    public ProtoBuf.ValueParameter getValueParameter(int paramInt)
    {
      return (ProtoBuf.ValueParameter)this.valueParameter_.get(paramInt);
    }
    
    public int getValueParameterCount()
    {
      return this.valueParameter_.size();
    }
    
    public List<ProtoBuf.ValueParameter> getValueParameterList()
    {
      return this.valueParameter_;
    }
    
    public List<Integer> getVersionRequirementList()
    {
      return this.versionRequirement_;
    }
    
    public boolean hasFlags()
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
      for (i = 0; i < getValueParameterCount(); i++) {
        if (!getValueParameter(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
      }
      if (!extensionsAreInitialized())
      {
        this.memoizedIsInitialized = ((byte)0);
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
      GeneratedMessageLite.ExtendableMessage.ExtensionWriter localExtensionWriter = newExtensionWriter();
      if ((this.bitField0_ & 0x1) == 1) {
        paramCodedOutputStream.writeInt32(1, this.flags_);
      }
      int i = 0;
      int k;
      for (int j = 0;; j++)
      {
        k = i;
        if (j >= this.valueParameter_.size()) {
          break;
        }
        paramCodedOutputStream.writeMessage(2, (MessageLite)this.valueParameter_.get(j));
      }
      while (k < this.versionRequirement_.size())
      {
        paramCodedOutputStream.writeInt32(31, ((Integer)this.versionRequirement_.get(k)).intValue());
        k++;
      }
      localExtensionWriter.writeUntil(19000, paramCodedOutputStream);
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Builder
      extends GeneratedMessageLite.ExtendableBuilder<ProtoBuf.Constructor, Builder>
      implements ProtoBuf.ConstructorOrBuilder
    {
      private int bitField0_;
      private int flags_ = 6;
      private List<ProtoBuf.ValueParameter> valueParameter_ = Collections.emptyList();
      private List<Integer> versionRequirement_ = Collections.emptyList();
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void ensureValueParameterIsMutable()
      {
        if ((this.bitField0_ & 0x2) != 2)
        {
          this.valueParameter_ = new ArrayList(this.valueParameter_);
          this.bitField0_ |= 0x2;
        }
      }
      
      private void ensureVersionRequirementIsMutable()
      {
        if ((this.bitField0_ & 0x4) != 4)
        {
          this.versionRequirement_ = new ArrayList(this.versionRequirement_);
          this.bitField0_ |= 0x4;
        }
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public ProtoBuf.Constructor build()
      {
        ProtoBuf.Constructor localConstructor = buildPartial();
        if (localConstructor.isInitialized()) {
          return localConstructor;
        }
        throw newUninitializedMessageException(localConstructor);
      }
      
      public ProtoBuf.Constructor buildPartial()
      {
        ProtoBuf.Constructor localConstructor = new ProtoBuf.Constructor(this, null);
        int i = this.bitField0_;
        int j = 1;
        if ((i & 0x1) != 1) {
          j = 0;
        }
        ProtoBuf.Constructor.access$12702(localConstructor, this.flags_);
        if ((this.bitField0_ & 0x2) == 2)
        {
          this.valueParameter_ = Collections.unmodifiableList(this.valueParameter_);
          this.bitField0_ &= 0xFFFFFFFD;
        }
        ProtoBuf.Constructor.access$12802(localConstructor, this.valueParameter_);
        if ((this.bitField0_ & 0x4) == 4)
        {
          this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
          this.bitField0_ &= 0xFFFFFFFB;
        }
        ProtoBuf.Constructor.access$12902(localConstructor, this.versionRequirement_);
        ProtoBuf.Constructor.access$13002(localConstructor, j);
        return localConstructor;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public ProtoBuf.Constructor getDefaultInstanceForType()
      {
        return ProtoBuf.Constructor.getDefaultInstance();
      }
      
      public ProtoBuf.ValueParameter getValueParameter(int paramInt)
      {
        return (ProtoBuf.ValueParameter)this.valueParameter_.get(paramInt);
      }
      
      public int getValueParameterCount()
      {
        return this.valueParameter_.size();
      }
      
      public final boolean isInitialized()
      {
        for (int i = 0; i < getValueParameterCount(); i++) {
          if (!getValueParameter(i).isInitialized()) {
            return false;
          }
        }
        return extensionsAreInitialized();
      }
      
      public Builder mergeFrom(ProtoBuf.Constructor paramConstructor)
      {
        if (paramConstructor == ProtoBuf.Constructor.getDefaultInstance()) {
          return this;
        }
        if (paramConstructor.hasFlags()) {
          setFlags(paramConstructor.getFlags());
        }
        if (!paramConstructor.valueParameter_.isEmpty()) {
          if (this.valueParameter_.isEmpty())
          {
            this.valueParameter_ = paramConstructor.valueParameter_;
            this.bitField0_ &= 0xFFFFFFFD;
          }
          else
          {
            ensureValueParameterIsMutable();
            this.valueParameter_.addAll(paramConstructor.valueParameter_);
          }
        }
        if (!paramConstructor.versionRequirement_.isEmpty()) {
          if (this.versionRequirement_.isEmpty())
          {
            this.versionRequirement_ = paramConstructor.versionRequirement_;
            this.bitField0_ &= 0xFFFFFFFB;
          }
          else
          {
            ensureVersionRequirementIsMutable();
            this.versionRequirement_.addAll(paramConstructor.versionRequirement_);
          }
        }
        mergeExtensionFields(paramConstructor);
        setUnknownFields(getUnknownFields().concat(paramConstructor.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 196	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 202 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 104	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_1
        //   29: aload_3
        //   30: astore_2
        //   31: aload_1
        //   32: astore_3
        //   33: goto +19 -> 52
        //   36: astore_2
        //   37: aload_2
        //   38: invokevirtual 205	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   41: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor
        //   44: astore_1
        //   45: aload_2
        //   46: athrow
        //   47: astore_2
        //   48: aload_2
        //   49: astore_3
        //   50: aload_1
        //   51: astore_2
        //   52: aload_2
        //   53: ifnull +9 -> 62
        //   56: aload_0
        //   57: aload_2
        //   58: invokevirtual 104	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Constructor$Builder;
        //   61: pop
        //   62: aload_3
        //   63: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	64	0	this	Builder
        //   0	64	1	paramCodedInputStream	CodedInputStream
        //   0	64	2	paramExtensionRegistryLite	ExtensionRegistryLite
        //   1	62	3	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   2	16	28	finally
        //   37	45	28	finally
        //   2	16	36	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   45	47	47	finally
      }
      
      public Builder setFlags(int paramInt)
      {
        this.bitField0_ |= 0x1;
        this.flags_ = paramInt;
        return this;
      }
    }
  }
  
  public static final class Contract
    extends GeneratedMessageLite
    implements ProtoBuf.ContractOrBuilder
  {
    public static Parser<Contract> PARSER = new AbstractParser()
    {
      public ProtoBuf.Contract parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new ProtoBuf.Contract(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final Contract defaultInstance;
    private List<ProtoBuf.Effect> effect_;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private final ByteString unknownFields;
    
    static
    {
      Contract localContract = new Contract(true);
      defaultInstance = localContract;
      localContract.initFields();
    }
    
    /* Error */
    private Contract(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 50	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: i2b
      //   7: putfield 52	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract:memoizedIsInitialized	B
      //   10: aload_0
      //   11: iconst_m1
      //   12: putfield 54	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract:memoizedSerializedSize	I
      //   15: aload_0
      //   16: invokespecial 43	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract:initFields	()V
      //   19: invokestatic 60	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   22: astore_3
      //   23: aload_3
      //   24: iconst_1
      //   25: invokestatic 66	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   28: astore 4
      //   30: iconst_0
      //   31: istore 5
      //   33: iconst_0
      //   34: istore 6
      //   36: iload 5
      //   38: ifne +272 -> 310
      //   41: iload 6
      //   43: istore 7
      //   45: iload 6
      //   47: istore 8
      //   49: iload 6
      //   51: istore 9
      //   53: aload_1
      //   54: invokevirtual 72	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   57: istore 10
      //   59: iload 10
      //   61: ifeq +145 -> 206
      //   64: iload 10
      //   66: bipush 10
      //   68: if_icmpeq +31 -> 99
      //   71: iload 6
      //   73: istore 7
      //   75: iload 6
      //   77: istore 8
      //   79: iload 6
      //   81: istore 9
      //   83: aload_0
      //   84: aload_1
      //   85: aload 4
      //   87: aload_2
      //   88: iload 10
      //   90: invokevirtual 76	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   93: ifne -57 -> 36
      //   96: goto +110 -> 206
      //   99: iload 6
      //   101: istore 10
      //   103: iload 6
      //   105: iconst_1
      //   106: iand
      //   107: iconst_1
      //   108: if_icmpeq +61 -> 169
      //   111: iload 6
      //   113: istore 7
      //   115: iload 6
      //   117: istore 8
      //   119: iload 6
      //   121: istore 9
      //   123: new 78	java/util/ArrayList
      //   126: astore 11
      //   128: iload 6
      //   130: istore 7
      //   132: iload 6
      //   134: istore 8
      //   136: iload 6
      //   138: istore 9
      //   140: aload 11
      //   142: invokespecial 79	java/util/ArrayList:<init>	()V
      //   145: iload 6
      //   147: istore 7
      //   149: iload 6
      //   151: istore 8
      //   153: iload 6
      //   155: istore 9
      //   157: aload_0
      //   158: aload 11
      //   160: putfield 81	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract:effect_	Ljava/util/List;
      //   163: iload 6
      //   165: iconst_1
      //   166: ior
      //   167: istore 10
      //   169: iload 10
      //   171: istore 7
      //   173: iload 10
      //   175: istore 8
      //   177: iload 10
      //   179: istore 9
      //   181: aload_0
      //   182: getfield 81	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract:effect_	Ljava/util/List;
      //   185: aload_1
      //   186: getstatic 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   189: aload_2
      //   190: invokevirtual 88	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   193: invokeinterface 94 2 0
      //   198: pop
      //   199: iload 10
      //   201: istore 6
      //   203: goto -167 -> 36
      //   206: iconst_1
      //   207: istore 5
      //   209: goto -173 -> 36
      //   212: astore_1
      //   213: goto +45 -> 258
      //   216: astore_1
      //   217: iload 8
      //   219: istore 7
      //   221: new 47	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   224: astore_2
      //   225: iload 8
      //   227: istore 7
      //   229: aload_2
      //   230: aload_1
      //   231: invokevirtual 98	java/io/IOException:getMessage	()Ljava/lang/String;
      //   234: invokespecial 101	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   237: iload 8
      //   239: istore 7
      //   241: aload_2
      //   242: aload_0
      //   243: invokevirtual 105	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   246: athrow
      //   247: astore_1
      //   248: iload 9
      //   250: istore 7
      //   252: aload_1
      //   253: aload_0
      //   254: invokevirtual 105	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   257: athrow
      //   258: iload 7
      //   260: iconst_1
      //   261: iand
      //   262: iconst_1
      //   263: if_icmpne +14 -> 277
      //   266: aload_0
      //   267: aload_0
      //   268: getfield 81	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract:effect_	Ljava/util/List;
      //   271: invokestatic 111	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   274: putfield 81	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract:effect_	Ljava/util/List;
      //   277: aload 4
      //   279: invokevirtual 114	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   282: aload_0
      //   283: aload_3
      //   284: invokevirtual 120	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   287: putfield 122	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   290: goto +14 -> 304
      //   293: astore_1
      //   294: aload_0
      //   295: aload_3
      //   296: invokevirtual 120	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   299: putfield 122	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   302: aload_1
      //   303: athrow
      //   304: aload_0
      //   305: invokevirtual 125	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract:makeExtensionsImmutable	()V
      //   308: aload_1
      //   309: athrow
      //   310: iload 6
      //   312: iconst_1
      //   313: iand
      //   314: iconst_1
      //   315: if_icmpne +14 -> 329
      //   318: aload_0
      //   319: aload_0
      //   320: getfield 81	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract:effect_	Ljava/util/List;
      //   323: invokestatic 111	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   326: putfield 81	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract:effect_	Ljava/util/List;
      //   329: aload 4
      //   331: invokevirtual 114	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   334: aload_0
      //   335: aload_3
      //   336: invokevirtual 120	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   339: putfield 122	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   342: goto +14 -> 356
      //   345: astore_1
      //   346: aload_0
      //   347: aload_3
      //   348: invokevirtual 120	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   351: putfield 122	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   354: aload_1
      //   355: athrow
      //   356: aload_0
      //   357: invokevirtual 125	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract:makeExtensionsImmutable	()V
      //   360: return
      //   361: astore_2
      //   362: goto -80 -> 282
      //   365: astore_1
      //   366: goto -32 -> 334
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	369	0	this	Contract
      //   0	369	1	paramCodedInputStream	CodedInputStream
      //   0	369	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   22	326	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   28	302	4	localCodedOutputStream	CodedOutputStream
      //   31	177	5	i	int
      //   34	280	6	j	int
      //   43	219	7	k	int
      //   47	191	8	m	int
      //   51	198	9	n	int
      //   57	143	10	i1	int
      //   126	33	11	localArrayList	ArrayList
      // Exception table:
      //   from	to	target	type
      //   53	59	212	finally
      //   83	96	212	finally
      //   123	128	212	finally
      //   140	145	212	finally
      //   157	163	212	finally
      //   181	199	212	finally
      //   221	225	212	finally
      //   229	237	212	finally
      //   241	247	212	finally
      //   252	258	212	finally
      //   53	59	216	java/io/IOException
      //   83	96	216	java/io/IOException
      //   123	128	216	java/io/IOException
      //   140	145	216	java/io/IOException
      //   157	163	216	java/io/IOException
      //   181	199	216	java/io/IOException
      //   53	59	247	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   83	96	247	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   123	128	247	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   140	145	247	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   157	163	247	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   181	199	247	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   277	282	293	finally
      //   329	334	345	finally
      //   277	282	361	java/io/IOException
      //   329	334	365	java/io/IOException
    }
    
    private Contract(GeneratedMessageLite.Builder paramBuilder)
    {
      super();
      this.unknownFields = paramBuilder.getUnknownFields();
    }
    
    private Contract(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static Contract getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.effect_ = Collections.emptyList();
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$22300();
    }
    
    public static Builder newBuilder(Contract paramContract)
    {
      return newBuilder().mergeFrom(paramContract);
    }
    
    public Contract getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public ProtoBuf.Effect getEffect(int paramInt)
    {
      return (ProtoBuf.Effect)this.effect_.get(paramInt);
    }
    
    public int getEffectCount()
    {
      return this.effect_.size();
    }
    
    public Parser<Contract> getParserForType()
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
      i = 0;
      while (j < this.effect_.size())
      {
        i += CodedOutputStream.computeMessageSize(1, (MessageLite)this.effect_.get(j));
        j++;
      }
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
      for (i = 0; i < getEffectCount(); i++) {
        if (!getEffect(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
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
      for (int i = 0; i < this.effect_.size(); i++) {
        paramCodedOutputStream.writeMessage(1, (MessageLite)this.effect_.get(i));
      }
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Builder
      extends GeneratedMessageLite.Builder<ProtoBuf.Contract, Builder>
      implements ProtoBuf.ContractOrBuilder
    {
      private int bitField0_;
      private List<ProtoBuf.Effect> effect_ = Collections.emptyList();
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void ensureEffectIsMutable()
      {
        if ((this.bitField0_ & 0x1) != 1)
        {
          this.effect_ = new ArrayList(this.effect_);
          this.bitField0_ |= 0x1;
        }
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public ProtoBuf.Contract build()
      {
        ProtoBuf.Contract localContract = buildPartial();
        if (localContract.isInitialized()) {
          return localContract;
        }
        throw newUninitializedMessageException(localContract);
      }
      
      public ProtoBuf.Contract buildPartial()
      {
        ProtoBuf.Contract localContract = new ProtoBuf.Contract(this, null);
        if ((this.bitField0_ & 0x1) == 1)
        {
          this.effect_ = Collections.unmodifiableList(this.effect_);
          this.bitField0_ &= 0xFFFFFFFE;
        }
        ProtoBuf.Contract.access$22502(localContract, this.effect_);
        return localContract;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public ProtoBuf.Contract getDefaultInstanceForType()
      {
        return ProtoBuf.Contract.getDefaultInstance();
      }
      
      public ProtoBuf.Effect getEffect(int paramInt)
      {
        return (ProtoBuf.Effect)this.effect_.get(paramInt);
      }
      
      public int getEffectCount()
      {
        return this.effect_.size();
      }
      
      public final boolean isInitialized()
      {
        for (int i = 0; i < getEffectCount(); i++) {
          if (!getEffect(i).isInitialized()) {
            return false;
          }
        }
        return true;
      }
      
      public Builder mergeFrom(ProtoBuf.Contract paramContract)
      {
        if (paramContract == ProtoBuf.Contract.getDefaultInstance()) {
          return this;
        }
        if (!paramContract.effect_.isEmpty()) {
          if (this.effect_.isEmpty())
          {
            this.effect_ = paramContract.effect_;
            this.bitField0_ &= 0xFFFFFFFE;
          }
          else
          {
            ensureEffectIsMutable();
            this.effect_.addAll(paramContract.effect_);
          }
        }
        setUnknownFields(getUnknownFields().concat(paramContract.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 155	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 161 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 86	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_2
        //   29: aload_3
        //   30: astore_1
        //   31: goto +21 -> 52
        //   34: astore_1
        //   35: aload_1
        //   36: invokevirtual 164	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   39: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract
        //   42: astore_2
        //   43: aload_1
        //   44: athrow
        //   45: astore_1
        //   46: aload_2
        //   47: astore_3
        //   48: aload_1
        //   49: astore_2
        //   50: aload_3
        //   51: astore_1
        //   52: aload_1
        //   53: ifnull +9 -> 62
        //   56: aload_0
        //   57: aload_1
        //   58: invokevirtual 86	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract$Builder;
        //   61: pop
        //   62: aload_2
        //   63: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	64	0	this	Builder
        //   0	64	1	paramCodedInputStream	CodedInputStream
        //   0	64	2	paramExtensionRegistryLite	ExtensionRegistryLite
        //   1	50	3	localExtensionRegistryLite	ExtensionRegistryLite
        // Exception table:
        //   from	to	target	type
        //   2	16	28	finally
        //   35	43	28	finally
        //   2	16	34	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   43	45	45	finally
      }
    }
  }
  
  public static final class Effect
    extends GeneratedMessageLite
    implements ProtoBuf.EffectOrBuilder
  {
    public static Parser<Effect> PARSER = new AbstractParser()
    {
      public ProtoBuf.Effect parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new ProtoBuf.Effect(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final Effect defaultInstance;
    private int bitField0_;
    private ProtoBuf.Expression conclusionOfConditionalEffect_;
    private List<ProtoBuf.Expression> effectConstructorArgument_;
    private EffectType effectType_;
    private InvocationKind kind_;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private final ByteString unknownFields;
    
    static
    {
      Effect localEffect = new Effect(true);
      defaultInstance = localEffect;
      localEffect.initFields();
    }
    
    /* Error */
    private Effect(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 67	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: i2b
      //   7: putfield 69	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:memoizedIsInitialized	B
      //   10: aload_0
      //   11: iconst_m1
      //   12: putfield 71	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:memoizedSerializedSize	I
      //   15: aload_0
      //   16: invokespecial 60	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:initFields	()V
      //   19: invokestatic 77	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   22: astore_3
      //   23: aload_3
      //   24: iconst_1
      //   25: invokestatic 83	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   28: astore 4
      //   30: iconst_0
      //   31: istore 5
      //   33: iconst_0
      //   34: istore 6
      //   36: iload 5
      //   38: ifne +705 -> 743
      //   41: iload 6
      //   43: istore 7
      //   45: iload 6
      //   47: istore 8
      //   49: iload 6
      //   51: istore 9
      //   53: aload_1
      //   54: invokevirtual 89	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   57: istore 10
      //   59: iload 10
      //   61: ifeq +578 -> 639
      //   64: iload 10
      //   66: bipush 8
      //   68: if_icmpeq +445 -> 513
      //   71: iload 10
      //   73: bipush 18
      //   75: if_icmpeq +331 -> 406
      //   78: iload 10
      //   80: bipush 26
      //   82: if_icmpeq +164 -> 246
      //   85: iload 10
      //   87: bipush 32
      //   89: if_icmpeq +31 -> 120
      //   92: iload 6
      //   94: istore 7
      //   96: iload 6
      //   98: istore 8
      //   100: iload 6
      //   102: istore 9
      //   104: aload_0
      //   105: aload_1
      //   106: aload 4
      //   108: aload_2
      //   109: iload 10
      //   111: invokevirtual 93	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   114: ifne -78 -> 36
      //   117: goto +522 -> 639
      //   120: iload 6
      //   122: istore 7
      //   124: iload 6
      //   126: istore 8
      //   128: iload 6
      //   130: istore 9
      //   132: aload_1
      //   133: invokevirtual 96	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readEnum	()I
      //   136: istore 11
      //   138: iload 6
      //   140: istore 7
      //   142: iload 6
      //   144: istore 8
      //   146: iload 6
      //   148: istore 9
      //   150: iload 11
      //   152: invokestatic 100	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect$InvocationKind:valueOf	(I)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect$InvocationKind;
      //   155: astore 12
      //   157: aload 12
      //   159: ifnonnull +44 -> 203
      //   162: iload 6
      //   164: istore 7
      //   166: iload 6
      //   168: istore 8
      //   170: iload 6
      //   172: istore 9
      //   174: aload 4
      //   176: iload 10
      //   178: invokevirtual 104	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:writeRawVarint32	(I)V
      //   181: iload 6
      //   183: istore 7
      //   185: iload 6
      //   187: istore 8
      //   189: iload 6
      //   191: istore 9
      //   193: aload 4
      //   195: iload 11
      //   197: invokevirtual 104	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:writeRawVarint32	(I)V
      //   200: goto -164 -> 36
      //   203: iload 6
      //   205: istore 7
      //   207: iload 6
      //   209: istore 8
      //   211: iload 6
      //   213: istore 9
      //   215: aload_0
      //   216: aload_0
      //   217: getfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:bitField0_	I
      //   220: iconst_4
      //   221: ior
      //   222: putfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:bitField0_	I
      //   225: iload 6
      //   227: istore 7
      //   229: iload 6
      //   231: istore 8
      //   233: iload 6
      //   235: istore 9
      //   237: aload_0
      //   238: aload 12
      //   240: putfield 108	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:kind_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect$InvocationKind;
      //   243: goto -207 -> 36
      //   246: aconst_null
      //   247: astore 12
      //   249: iload 6
      //   251: istore 7
      //   253: iload 6
      //   255: istore 8
      //   257: iload 6
      //   259: istore 9
      //   261: aload_0
      //   262: getfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:bitField0_	I
      //   265: iconst_2
      //   266: iand
      //   267: iconst_2
      //   268: if_icmpne +24 -> 292
      //   271: iload 6
      //   273: istore 7
      //   275: iload 6
      //   277: istore 8
      //   279: iload 6
      //   281: istore 9
      //   283: aload_0
      //   284: getfield 110	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:conclusionOfConditionalEffect_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression;
      //   287: invokevirtual 116	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression$Builder;
      //   290: astore 12
      //   292: iload 6
      //   294: istore 7
      //   296: iload 6
      //   298: istore 8
      //   300: iload 6
      //   302: istore 9
      //   304: aload_1
      //   305: getstatic 117	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   308: aload_2
      //   309: invokevirtual 121	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   312: checkcast 112	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression
      //   315: astore 13
      //   317: iload 6
      //   319: istore 7
      //   321: iload 6
      //   323: istore 8
      //   325: iload 6
      //   327: istore 9
      //   329: aload_0
      //   330: aload 13
      //   332: putfield 110	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:conclusionOfConditionalEffect_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression;
      //   335: aload 12
      //   337: ifnull +44 -> 381
      //   340: iload 6
      //   342: istore 7
      //   344: iload 6
      //   346: istore 8
      //   348: iload 6
      //   350: istore 9
      //   352: aload 12
      //   354: aload 13
      //   356: invokevirtual 127	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression$Builder;
      //   359: pop
      //   360: iload 6
      //   362: istore 7
      //   364: iload 6
      //   366: istore 8
      //   368: iload 6
      //   370: istore 9
      //   372: aload_0
      //   373: aload 12
      //   375: invokevirtual 131	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression;
      //   378: putfield 110	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:conclusionOfConditionalEffect_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression;
      //   381: iload 6
      //   383: istore 7
      //   385: iload 6
      //   387: istore 8
      //   389: iload 6
      //   391: istore 9
      //   393: aload_0
      //   394: aload_0
      //   395: getfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:bitField0_	I
      //   398: iconst_2
      //   399: ior
      //   400: putfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:bitField0_	I
      //   403: goto -367 -> 36
      //   406: iload 6
      //   408: istore 10
      //   410: iload 6
      //   412: iconst_2
      //   413: iand
      //   414: iconst_2
      //   415: if_icmpeq +61 -> 476
      //   418: iload 6
      //   420: istore 7
      //   422: iload 6
      //   424: istore 8
      //   426: iload 6
      //   428: istore 9
      //   430: new 133	java/util/ArrayList
      //   433: astore 12
      //   435: iload 6
      //   437: istore 7
      //   439: iload 6
      //   441: istore 8
      //   443: iload 6
      //   445: istore 9
      //   447: aload 12
      //   449: invokespecial 134	java/util/ArrayList:<init>	()V
      //   452: iload 6
      //   454: istore 7
      //   456: iload 6
      //   458: istore 8
      //   460: iload 6
      //   462: istore 9
      //   464: aload_0
      //   465: aload 12
      //   467: putfield 136	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:effectConstructorArgument_	Ljava/util/List;
      //   470: iload 6
      //   472: iconst_2
      //   473: ior
      //   474: istore 10
      //   476: iload 10
      //   478: istore 7
      //   480: iload 10
      //   482: istore 8
      //   484: iload 10
      //   486: istore 9
      //   488: aload_0
      //   489: getfield 136	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:effectConstructorArgument_	Ljava/util/List;
      //   492: aload_1
      //   493: getstatic 117	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   496: aload_2
      //   497: invokevirtual 121	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   500: invokeinterface 142 2 0
      //   505: pop
      //   506: iload 10
      //   508: istore 6
      //   510: goto -474 -> 36
      //   513: iload 6
      //   515: istore 7
      //   517: iload 6
      //   519: istore 8
      //   521: iload 6
      //   523: istore 9
      //   525: aload_1
      //   526: invokevirtual 96	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readEnum	()I
      //   529: istore 11
      //   531: iload 6
      //   533: istore 7
      //   535: iload 6
      //   537: istore 8
      //   539: iload 6
      //   541: istore 9
      //   543: iload 11
      //   545: invokestatic 145	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect$EffectType:valueOf	(I)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect$EffectType;
      //   548: astore 12
      //   550: aload 12
      //   552: ifnonnull +44 -> 596
      //   555: iload 6
      //   557: istore 7
      //   559: iload 6
      //   561: istore 8
      //   563: iload 6
      //   565: istore 9
      //   567: aload 4
      //   569: iload 10
      //   571: invokevirtual 104	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:writeRawVarint32	(I)V
      //   574: iload 6
      //   576: istore 7
      //   578: iload 6
      //   580: istore 8
      //   582: iload 6
      //   584: istore 9
      //   586: aload 4
      //   588: iload 11
      //   590: invokevirtual 104	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:writeRawVarint32	(I)V
      //   593: goto -557 -> 36
      //   596: iload 6
      //   598: istore 7
      //   600: iload 6
      //   602: istore 8
      //   604: iload 6
      //   606: istore 9
      //   608: aload_0
      //   609: aload_0
      //   610: getfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:bitField0_	I
      //   613: iconst_1
      //   614: ior
      //   615: putfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:bitField0_	I
      //   618: iload 6
      //   620: istore 7
      //   622: iload 6
      //   624: istore 8
      //   626: iload 6
      //   628: istore 9
      //   630: aload_0
      //   631: aload 12
      //   633: putfield 147	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:effectType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect$EffectType;
      //   636: goto -600 -> 36
      //   639: iconst_1
      //   640: istore 5
      //   642: goto -606 -> 36
      //   645: astore_1
      //   646: goto +45 -> 691
      //   649: astore_2
      //   650: iload 8
      //   652: istore 7
      //   654: new 64	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   657: astore_1
      //   658: iload 8
      //   660: istore 7
      //   662: aload_1
      //   663: aload_2
      //   664: invokevirtual 151	java/io/IOException:getMessage	()Ljava/lang/String;
      //   667: invokespecial 154	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   670: iload 8
      //   672: istore 7
      //   674: aload_1
      //   675: aload_0
      //   676: invokevirtual 158	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   679: athrow
      //   680: astore_1
      //   681: iload 9
      //   683: istore 7
      //   685: aload_1
      //   686: aload_0
      //   687: invokevirtual 158	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   690: athrow
      //   691: iload 7
      //   693: iconst_2
      //   694: iand
      //   695: iconst_2
      //   696: if_icmpne +14 -> 710
      //   699: aload_0
      //   700: aload_0
      //   701: getfield 136	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:effectConstructorArgument_	Ljava/util/List;
      //   704: invokestatic 164	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   707: putfield 136	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:effectConstructorArgument_	Ljava/util/List;
      //   710: aload 4
      //   712: invokevirtual 167	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   715: aload_0
      //   716: aload_3
      //   717: invokevirtual 173	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   720: putfield 175	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   723: goto +14 -> 737
      //   726: astore_1
      //   727: aload_0
      //   728: aload_3
      //   729: invokevirtual 173	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   732: putfield 175	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   735: aload_1
      //   736: athrow
      //   737: aload_0
      //   738: invokevirtual 178	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:makeExtensionsImmutable	()V
      //   741: aload_1
      //   742: athrow
      //   743: iload 6
      //   745: iconst_2
      //   746: iand
      //   747: iconst_2
      //   748: if_icmpne +14 -> 762
      //   751: aload_0
      //   752: aload_0
      //   753: getfield 136	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:effectConstructorArgument_	Ljava/util/List;
      //   756: invokestatic 164	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   759: putfield 136	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:effectConstructorArgument_	Ljava/util/List;
      //   762: aload 4
      //   764: invokevirtual 167	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   767: aload_0
      //   768: aload_3
      //   769: invokevirtual 173	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   772: putfield 175	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   775: goto +14 -> 789
      //   778: astore_1
      //   779: aload_0
      //   780: aload_3
      //   781: invokevirtual 173	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   784: putfield 175	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   787: aload_1
      //   788: athrow
      //   789: aload_0
      //   790: invokevirtual 178	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:makeExtensionsImmutable	()V
      //   793: return
      //   794: astore_2
      //   795: goto -80 -> 715
      //   798: astore_1
      //   799: goto -32 -> 767
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	802	0	this	Effect
      //   0	802	1	paramCodedInputStream	CodedInputStream
      //   0	802	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   22	759	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   28	735	4	localCodedOutputStream	CodedOutputStream
      //   31	610	5	i	int
      //   34	713	6	j	int
      //   43	652	7	k	int
      //   47	624	8	m	int
      //   51	631	9	n	int
      //   57	513	10	i1	int
      //   136	453	11	i2	int
      //   155	477	12	localObject	Object
      //   315	40	13	localExpression	ProtoBuf.Expression
      // Exception table:
      //   from	to	target	type
      //   53	59	645	finally
      //   104	117	645	finally
      //   132	138	645	finally
      //   150	157	645	finally
      //   174	181	645	finally
      //   193	200	645	finally
      //   215	225	645	finally
      //   237	243	645	finally
      //   261	271	645	finally
      //   283	292	645	finally
      //   304	317	645	finally
      //   329	335	645	finally
      //   352	360	645	finally
      //   372	381	645	finally
      //   393	403	645	finally
      //   430	435	645	finally
      //   447	452	645	finally
      //   464	470	645	finally
      //   488	506	645	finally
      //   525	531	645	finally
      //   543	550	645	finally
      //   567	574	645	finally
      //   586	593	645	finally
      //   608	618	645	finally
      //   630	636	645	finally
      //   654	658	645	finally
      //   662	670	645	finally
      //   674	680	645	finally
      //   685	691	645	finally
      //   53	59	649	java/io/IOException
      //   104	117	649	java/io/IOException
      //   132	138	649	java/io/IOException
      //   150	157	649	java/io/IOException
      //   174	181	649	java/io/IOException
      //   193	200	649	java/io/IOException
      //   215	225	649	java/io/IOException
      //   237	243	649	java/io/IOException
      //   261	271	649	java/io/IOException
      //   283	292	649	java/io/IOException
      //   304	317	649	java/io/IOException
      //   329	335	649	java/io/IOException
      //   352	360	649	java/io/IOException
      //   372	381	649	java/io/IOException
      //   393	403	649	java/io/IOException
      //   430	435	649	java/io/IOException
      //   447	452	649	java/io/IOException
      //   464	470	649	java/io/IOException
      //   488	506	649	java/io/IOException
      //   525	531	649	java/io/IOException
      //   543	550	649	java/io/IOException
      //   567	574	649	java/io/IOException
      //   586	593	649	java/io/IOException
      //   608	618	649	java/io/IOException
      //   630	636	649	java/io/IOException
      //   53	59	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   104	117	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   132	138	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   150	157	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   174	181	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   193	200	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   215	225	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   237	243	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   261	271	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   283	292	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   304	317	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   329	335	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   352	360	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   372	381	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   393	403	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   430	435	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   447	452	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   464	470	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   488	506	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   525	531	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   543	550	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   567	574	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   586	593	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   608	618	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   630	636	680	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   710	715	726	finally
      //   762	767	778	finally
      //   710	715	794	java/io/IOException
      //   762	767	798	java/io/IOException
    }
    
    private Effect(GeneratedMessageLite.Builder paramBuilder)
    {
      super();
      this.unknownFields = paramBuilder.getUnknownFields();
    }
    
    private Effect(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static Effect getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.effectType_ = EffectType.RETURNS_CONSTANT;
      this.effectConstructorArgument_ = Collections.emptyList();
      this.conclusionOfConditionalEffect_ = ProtoBuf.Expression.getDefaultInstance();
      this.kind_ = InvocationKind.AT_MOST_ONCE;
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$22800();
    }
    
    public static Builder newBuilder(Effect paramEffect)
    {
      return newBuilder().mergeFrom(paramEffect);
    }
    
    public ProtoBuf.Expression getConclusionOfConditionalEffect()
    {
      return this.conclusionOfConditionalEffect_;
    }
    
    public Effect getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public ProtoBuf.Expression getEffectConstructorArgument(int paramInt)
    {
      return (ProtoBuf.Expression)this.effectConstructorArgument_.get(paramInt);
    }
    
    public int getEffectConstructorArgumentCount()
    {
      return this.effectConstructorArgument_.size();
    }
    
    public EffectType getEffectType()
    {
      return this.effectType_;
    }
    
    public InvocationKind getKind()
    {
      return this.kind_;
    }
    
    public Parser<Effect> getParserForType()
    {
      return PARSER;
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
        i = CodedOutputStream.computeEnumSize(1, this.effectType_.getNumber()) + 0;
      } else {
        i = 0;
      }
      while (j < this.effectConstructorArgument_.size())
      {
        i += CodedOutputStream.computeMessageSize(2, (MessageLite)this.effectConstructorArgument_.get(j));
        j++;
      }
      j = i;
      if ((this.bitField0_ & 0x2) == 2) {
        j = i + CodedOutputStream.computeMessageSize(3, this.conclusionOfConditionalEffect_);
      }
      i = j;
      if ((this.bitField0_ & 0x4) == 4) {
        i = j + CodedOutputStream.computeEnumSize(4, this.kind_.getNumber());
      }
      i += this.unknownFields.size();
      this.memoizedSerializedSize = i;
      return i;
    }
    
    public boolean hasConclusionOfConditionalEffect()
    {
      boolean bool;
      if ((this.bitField0_ & 0x2) == 2) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasEffectType()
    {
      int i = this.bitField0_;
      boolean bool = true;
      if ((i & 0x1) != 1) {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasKind()
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
      for (i = 0; i < getEffectConstructorArgumentCount(); i++) {
        if (!getEffectConstructorArgument(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
      }
      if ((hasConclusionOfConditionalEffect()) && (!getConclusionOfConditionalEffect().isInitialized()))
      {
        this.memoizedIsInitialized = ((byte)0);
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
        paramCodedOutputStream.writeEnum(1, this.effectType_.getNumber());
      }
      for (int i = 0; i < this.effectConstructorArgument_.size(); i++) {
        paramCodedOutputStream.writeMessage(2, (MessageLite)this.effectConstructorArgument_.get(i));
      }
      if ((this.bitField0_ & 0x2) == 2) {
        paramCodedOutputStream.writeMessage(3, this.conclusionOfConditionalEffect_);
      }
      if ((this.bitField0_ & 0x4) == 4) {
        paramCodedOutputStream.writeEnum(4, this.kind_.getNumber());
      }
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Builder
      extends GeneratedMessageLite.Builder<ProtoBuf.Effect, Builder>
      implements ProtoBuf.EffectOrBuilder
    {
      private int bitField0_;
      private ProtoBuf.Expression conclusionOfConditionalEffect_ = ProtoBuf.Expression.getDefaultInstance();
      private List<ProtoBuf.Expression> effectConstructorArgument_ = Collections.emptyList();
      private ProtoBuf.Effect.EffectType effectType_ = ProtoBuf.Effect.EffectType.RETURNS_CONSTANT;
      private ProtoBuf.Effect.InvocationKind kind_ = ProtoBuf.Effect.InvocationKind.AT_MOST_ONCE;
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void ensureEffectConstructorArgumentIsMutable()
      {
        if ((this.bitField0_ & 0x2) != 2)
        {
          this.effectConstructorArgument_ = new ArrayList(this.effectConstructorArgument_);
          this.bitField0_ |= 0x2;
        }
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public ProtoBuf.Effect build()
      {
        ProtoBuf.Effect localEffect = buildPartial();
        if (localEffect.isInitialized()) {
          return localEffect;
        }
        throw newUninitializedMessageException(localEffect);
      }
      
      public ProtoBuf.Effect buildPartial()
      {
        ProtoBuf.Effect localEffect = new ProtoBuf.Effect(this, null);
        int i = this.bitField0_;
        int j = 1;
        if ((i & 0x1) != 1) {
          j = 0;
        }
        ProtoBuf.Effect.access$23002(localEffect, this.effectType_);
        if ((this.bitField0_ & 0x2) == 2)
        {
          this.effectConstructorArgument_ = Collections.unmodifiableList(this.effectConstructorArgument_);
          this.bitField0_ &= 0xFFFFFFFD;
        }
        ProtoBuf.Effect.access$23102(localEffect, this.effectConstructorArgument_);
        int k = j;
        if ((i & 0x4) == 4) {
          k = j | 0x2;
        }
        ProtoBuf.Effect.access$23202(localEffect, this.conclusionOfConditionalEffect_);
        j = k;
        if ((i & 0x8) == 8) {
          j = k | 0x4;
        }
        ProtoBuf.Effect.access$23302(localEffect, this.kind_);
        ProtoBuf.Effect.access$23402(localEffect, j);
        return localEffect;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public ProtoBuf.Expression getConclusionOfConditionalEffect()
      {
        return this.conclusionOfConditionalEffect_;
      }
      
      public ProtoBuf.Effect getDefaultInstanceForType()
      {
        return ProtoBuf.Effect.getDefaultInstance();
      }
      
      public ProtoBuf.Expression getEffectConstructorArgument(int paramInt)
      {
        return (ProtoBuf.Expression)this.effectConstructorArgument_.get(paramInt);
      }
      
      public int getEffectConstructorArgumentCount()
      {
        return this.effectConstructorArgument_.size();
      }
      
      public boolean hasConclusionOfConditionalEffect()
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
        for (int i = 0; i < getEffectConstructorArgumentCount(); i++) {
          if (!getEffectConstructorArgument(i).isInitialized()) {
            return false;
          }
        }
        return (!hasConclusionOfConditionalEffect()) || (getConclusionOfConditionalEffect().isInitialized());
      }
      
      public Builder mergeConclusionOfConditionalEffect(ProtoBuf.Expression paramExpression)
      {
        if (((this.bitField0_ & 0x4) == 4) && (this.conclusionOfConditionalEffect_ != ProtoBuf.Expression.getDefaultInstance())) {
          this.conclusionOfConditionalEffect_ = ProtoBuf.Expression.newBuilder(this.conclusionOfConditionalEffect_).mergeFrom(paramExpression).buildPartial();
        } else {
          this.conclusionOfConditionalEffect_ = paramExpression;
        }
        this.bitField0_ |= 0x4;
        return this;
      }
      
      public Builder mergeFrom(ProtoBuf.Effect paramEffect)
      {
        if (paramEffect == ProtoBuf.Effect.getDefaultInstance()) {
          return this;
        }
        if (paramEffect.hasEffectType()) {
          setEffectType(paramEffect.getEffectType());
        }
        if (!paramEffect.effectConstructorArgument_.isEmpty()) {
          if (this.effectConstructorArgument_.isEmpty())
          {
            this.effectConstructorArgument_ = paramEffect.effectConstructorArgument_;
            this.bitField0_ &= 0xFFFFFFFD;
          }
          else
          {
            ensureEffectConstructorArgumentIsMutable();
            this.effectConstructorArgument_.addAll(paramEffect.effectConstructorArgument_);
          }
        }
        if (paramEffect.hasConclusionOfConditionalEffect()) {
          mergeConclusionOfConditionalEffect(paramEffect.getConclusionOfConditionalEffect());
        }
        if (paramEffect.hasKind()) {
          setKind(paramEffect.getKind());
        }
        setUnknownFields(getUnknownFields().concat(paramEffect.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 240	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 246 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 130	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_1
        //   29: aload_3
        //   30: astore_2
        //   31: goto +15 -> 46
        //   34: astore_1
        //   35: aload_1
        //   36: invokevirtual 249	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   39: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect
        //   42: astore_2
        //   43: aload_1
        //   44: athrow
        //   45: astore_1
        //   46: aload_2
        //   47: ifnull +9 -> 56
        //   50: aload_0
        //   51: aload_2
        //   52: invokevirtual 130	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Effect$Builder;
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
      
      public Builder setEffectType(ProtoBuf.Effect.EffectType paramEffectType)
      {
        if (paramEffectType != null)
        {
          this.bitField0_ |= 0x1;
          this.effectType_ = paramEffectType;
          return this;
        }
        throw null;
      }
      
      public Builder setKind(ProtoBuf.Effect.InvocationKind paramInvocationKind)
      {
        if (paramInvocationKind != null)
        {
          this.bitField0_ |= 0x8;
          this.kind_ = paramInvocationKind;
          return this;
        }
        throw null;
      }
    }
    
    public static enum EffectType
      implements Internal.EnumLite
    {
      private static Internal.EnumLiteMap<EffectType> internalValueMap = new Internal.EnumLiteMap()
      {
        public ProtoBuf.Effect.EffectType findValueByNumber(int paramAnonymousInt)
        {
          return ProtoBuf.Effect.EffectType.valueOf(paramAnonymousInt);
        }
      };
      private final int value;
      
      static
      {
        CALLS = new EffectType("CALLS", 1, 1, 1);
        EffectType localEffectType = new EffectType("RETURNS_NOT_NULL", 2, 2, 2);
        RETURNS_NOT_NULL = localEffectType;
        $VALUES = new EffectType[] { RETURNS_CONSTANT, CALLS, localEffectType };
      }
      
      private EffectType(int paramInt1, int paramInt2)
      {
        this.value = paramInt2;
      }
      
      public static EffectType valueOf(int paramInt)
      {
        if (paramInt != 0)
        {
          if (paramInt != 1)
          {
            if (paramInt != 2) {
              return null;
            }
            return RETURNS_NOT_NULL;
          }
          return CALLS;
        }
        return RETURNS_CONSTANT;
      }
      
      public final int getNumber()
      {
        return this.value;
      }
    }
    
    public static enum InvocationKind
      implements Internal.EnumLite
    {
      private static Internal.EnumLiteMap<InvocationKind> internalValueMap = new Internal.EnumLiteMap()
      {
        public ProtoBuf.Effect.InvocationKind findValueByNumber(int paramAnonymousInt)
        {
          return ProtoBuf.Effect.InvocationKind.valueOf(paramAnonymousInt);
        }
      };
      private final int value;
      
      static
      {
        InvocationKind localInvocationKind = new InvocationKind("AT_LEAST_ONCE", 2, 2, 2);
        AT_LEAST_ONCE = localInvocationKind;
        $VALUES = new InvocationKind[] { AT_MOST_ONCE, EXACTLY_ONCE, localInvocationKind };
      }
      
      private InvocationKind(int paramInt1, int paramInt2)
      {
        this.value = paramInt2;
      }
      
      public static InvocationKind valueOf(int paramInt)
      {
        if (paramInt != 0)
        {
          if (paramInt != 1)
          {
            if (paramInt != 2) {
              return null;
            }
            return AT_LEAST_ONCE;
          }
          return EXACTLY_ONCE;
        }
        return AT_MOST_ONCE;
      }
      
      public final int getNumber()
      {
        return this.value;
      }
    }
  }
  
  public static final class EnumEntry
    extends GeneratedMessageLite.ExtendableMessage<EnumEntry>
    implements ProtoBuf.EnumEntryOrBuilder
  {
    public static Parser<EnumEntry> PARSER = new AbstractParser()
    {
      public ProtoBuf.EnumEntry parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new ProtoBuf.EnumEntry(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final EnumEntry defaultInstance;
    private int bitField0_;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private int name_;
    private final ByteString unknownFields;
    
    static
    {
      EnumEntry localEnumEntry = new EnumEntry(true);
      defaultInstance = localEnumEntry;
      localEnumEntry.initFields();
    }
    
    /* Error */
    private EnumEntry(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 50	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite$ExtendableMessage:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: i2b
      //   7: putfield 52	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry:memoizedIsInitialized	B
      //   10: aload_0
      //   11: iconst_m1
      //   12: putfield 54	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry:memoizedSerializedSize	I
      //   15: aload_0
      //   16: invokespecial 43	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry:initFields	()V
      //   19: invokestatic 60	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   22: astore_3
      //   23: aload_3
      //   24: iconst_1
      //   25: invokestatic 66	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   28: astore 4
      //   30: iconst_0
      //   31: istore 5
      //   33: iload 5
      //   35: ifne +127 -> 162
      //   38: aload_1
      //   39: invokevirtual 72	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   42: istore 6
      //   44: iload 6
      //   46: ifeq +47 -> 93
      //   49: iload 6
      //   51: bipush 8
      //   53: if_icmpeq +19 -> 72
      //   56: aload_0
      //   57: aload_1
      //   58: aload 4
      //   60: aload_2
      //   61: iload 6
      //   63: invokevirtual 76	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   66: ifne -33 -> 33
      //   69: goto +24 -> 93
      //   72: aload_0
      //   73: aload_0
      //   74: getfield 78	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry:bitField0_	I
      //   77: iconst_1
      //   78: ior
      //   79: putfield 78	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry:bitField0_	I
      //   82: aload_0
      //   83: aload_1
      //   84: invokevirtual 81	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   87: putfield 83	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry:name_	I
      //   90: goto -57 -> 33
      //   93: iconst_1
      //   94: istore 5
      //   96: goto -63 -> 33
      //   99: astore_1
      //   100: goto +29 -> 129
      //   103: astore_2
      //   104: new 47	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   107: astore_1
      //   108: aload_1
      //   109: aload_2
      //   110: invokevirtual 87	java/io/IOException:getMessage	()Ljava/lang/String;
      //   113: invokespecial 90	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   116: aload_1
      //   117: aload_0
      //   118: invokevirtual 94	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   121: athrow
      //   122: astore_1
      //   123: aload_1
      //   124: aload_0
      //   125: invokevirtual 94	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   128: athrow
      //   129: aload 4
      //   131: invokevirtual 97	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   134: aload_0
      //   135: aload_3
      //   136: invokevirtual 103	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   139: putfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   142: goto +14 -> 156
      //   145: astore_1
      //   146: aload_0
      //   147: aload_3
      //   148: invokevirtual 103	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   151: putfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   154: aload_1
      //   155: athrow
      //   156: aload_0
      //   157: invokevirtual 108	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry:makeExtensionsImmutable	()V
      //   160: aload_1
      //   161: athrow
      //   162: aload 4
      //   164: invokevirtual 97	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   167: aload_0
      //   168: aload_3
      //   169: invokevirtual 103	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   172: putfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   175: goto +14 -> 189
      //   178: astore_1
      //   179: aload_0
      //   180: aload_3
      //   181: invokevirtual 103	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   184: putfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   187: aload_1
      //   188: athrow
      //   189: aload_0
      //   190: invokevirtual 108	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry:makeExtensionsImmutable	()V
      //   193: return
      //   194: astore_2
      //   195: goto -61 -> 134
      //   198: astore_1
      //   199: goto -32 -> 167
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	202	0	this	EnumEntry
      //   0	202	1	paramCodedInputStream	CodedInputStream
      //   0	202	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   22	159	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   28	135	4	localCodedOutputStream	CodedOutputStream
      //   31	64	5	i	int
      //   42	20	6	j	int
      // Exception table:
      //   from	to	target	type
      //   38	44	99	finally
      //   56	69	99	finally
      //   72	90	99	finally
      //   104	122	99	finally
      //   123	129	99	finally
      //   38	44	103	java/io/IOException
      //   56	69	103	java/io/IOException
      //   72	90	103	java/io/IOException
      //   38	44	122	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   56	69	122	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   72	90	122	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   129	134	145	finally
      //   162	167	178	finally
      //   129	134	194	java/io/IOException
      //   162	167	198	java/io/IOException
    }
    
    private EnumEntry(GeneratedMessageLite.ExtendableBuilder<EnumEntry, ?> paramExtendableBuilder)
    {
      super();
      this.unknownFields = paramExtendableBuilder.getUnknownFields();
    }
    
    private EnumEntry(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static EnumEntry getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.name_ = 0;
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$19200();
    }
    
    public static Builder newBuilder(EnumEntry paramEnumEntry)
    {
      return newBuilder().mergeFrom(paramEnumEntry);
    }
    
    public EnumEntry getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public int getName()
    {
      return this.name_;
    }
    
    public Parser<EnumEntry> getParserForType()
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
      i = i + extensionsSerializedSize() + this.unknownFields.size();
      this.memoizedSerializedSize = i;
      return i;
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
      if (!extensionsAreInitialized())
      {
        this.memoizedIsInitialized = ((byte)0);
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
      GeneratedMessageLite.ExtendableMessage.ExtensionWriter localExtensionWriter = newExtensionWriter();
      if ((this.bitField0_ & 0x1) == 1) {
        paramCodedOutputStream.writeInt32(1, this.name_);
      }
      localExtensionWriter.writeUntil(200, paramCodedOutputStream);
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Builder
      extends GeneratedMessageLite.ExtendableBuilder<ProtoBuf.EnumEntry, Builder>
      implements ProtoBuf.EnumEntryOrBuilder
    {
      private int bitField0_;
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
      
      public ProtoBuf.EnumEntry build()
      {
        ProtoBuf.EnumEntry localEnumEntry = buildPartial();
        if (localEnumEntry.isInitialized()) {
          return localEnumEntry;
        }
        throw newUninitializedMessageException(localEnumEntry);
      }
      
      public ProtoBuf.EnumEntry buildPartial()
      {
        ProtoBuf.EnumEntry localEnumEntry = new ProtoBuf.EnumEntry(this, null);
        int i = this.bitField0_;
        int j = 1;
        if ((i & 0x1) != 1) {
          j = 0;
        }
        ProtoBuf.EnumEntry.access$19402(localEnumEntry, this.name_);
        ProtoBuf.EnumEntry.access$19502(localEnumEntry, j);
        return localEnumEntry;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public ProtoBuf.EnumEntry getDefaultInstanceForType()
      {
        return ProtoBuf.EnumEntry.getDefaultInstance();
      }
      
      public final boolean isInitialized()
      {
        return extensionsAreInitialized();
      }
      
      public Builder mergeFrom(ProtoBuf.EnumEntry paramEnumEntry)
      {
        if (paramEnumEntry == ProtoBuf.EnumEntry.getDefaultInstance()) {
          return this;
        }
        if (paramEnumEntry.hasName()) {
          setName(paramEnumEntry.getName());
        }
        mergeExtensionFields(paramEnumEntry);
        setUnknownFields(getUnknownFields().concat(paramEnumEntry.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 126	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 132 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 71	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_1
        //   29: aload_3
        //   30: astore_2
        //   31: goto +15 -> 46
        //   34: astore_1
        //   35: aload_1
        //   36: invokevirtual 135	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   39: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry
        //   42: astore_2
        //   43: aload_1
        //   44: athrow
        //   45: astore_1
        //   46: aload_2
        //   47: ifnull +9 -> 56
        //   50: aload_0
        //   51: aload_2
        //   52: invokevirtual 71	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$EnumEntry$Builder;
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
      
      public Builder setName(int paramInt)
      {
        this.bitField0_ |= 0x1;
        this.name_ = paramInt;
        return this;
      }
    }
  }
  
  public static final class Expression
    extends GeneratedMessageLite
    implements ProtoBuf.ExpressionOrBuilder
  {
    public static Parser<Expression> PARSER = new AbstractParser()
    {
      public ProtoBuf.Expression parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new ProtoBuf.Expression(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final Expression defaultInstance;
    private List<Expression> andArgument_;
    private int bitField0_;
    private ConstantValue constantValue_;
    private int flags_;
    private int isInstanceTypeId_;
    private ProtoBuf.Type isInstanceType_;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private List<Expression> orArgument_;
    private final ByteString unknownFields;
    private int valueParameterReference_;
    
    static
    {
      Expression localExpression = new Expression(true);
      defaultInstance = localExpression;
      localExpression.initFields();
    }
    
    /* Error */
    private Expression(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 64	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: i2b
      //   7: putfield 66	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:memoizedIsInitialized	B
      //   10: aload_0
      //   11: iconst_m1
      //   12: putfield 68	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:memoizedSerializedSize	I
      //   15: aload_0
      //   16: invokespecial 57	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:initFields	()V
      //   19: invokestatic 74	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   22: astore_3
      //   23: aload_3
      //   24: iconst_1
      //   25: invokestatic 80	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   28: astore 4
      //   30: iconst_0
      //   31: istore 5
      //   33: iconst_0
      //   34: istore 6
      //   36: iload 5
      //   38: ifne +875 -> 913
      //   41: iload 6
      //   43: istore 7
      //   45: iload 6
      //   47: istore 8
      //   49: iload 6
      //   51: istore 9
      //   53: aload_1
      //   54: invokevirtual 86	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   57: istore 10
      //   59: iload 10
      //   61: ifeq +725 -> 786
      //   64: iload 10
      //   66: bipush 8
      //   68: if_icmpeq +673 -> 741
      //   71: iload 10
      //   73: bipush 16
      //   75: if_icmpeq +621 -> 696
      //   78: iload 10
      //   80: bipush 24
      //   82: if_icmpeq +488 -> 570
      //   85: iload 10
      //   87: bipush 34
      //   89: if_icmpeq +318 -> 407
      //   92: iload 10
      //   94: bipush 40
      //   96: if_icmpeq +265 -> 361
      //   99: iload 10
      //   101: bipush 50
      //   103: if_icmpeq +148 -> 251
      //   106: iload 10
      //   108: bipush 58
      //   110: if_icmpeq +31 -> 141
      //   113: iload 6
      //   115: istore 7
      //   117: iload 6
      //   119: istore 8
      //   121: iload 6
      //   123: istore 9
      //   125: aload_0
      //   126: aload_1
      //   127: aload 4
      //   129: aload_2
      //   130: iload 10
      //   132: invokevirtual 90	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   135: ifne -99 -> 36
      //   138: goto +648 -> 786
      //   141: iload 6
      //   143: istore 10
      //   145: iload 6
      //   147: bipush 64
      //   149: iand
      //   150: bipush 64
      //   152: if_icmpeq +62 -> 214
      //   155: iload 6
      //   157: istore 7
      //   159: iload 6
      //   161: istore 8
      //   163: iload 6
      //   165: istore 9
      //   167: new 92	java/util/ArrayList
      //   170: astore 11
      //   172: iload 6
      //   174: istore 7
      //   176: iload 6
      //   178: istore 8
      //   180: iload 6
      //   182: istore 9
      //   184: aload 11
      //   186: invokespecial 93	java/util/ArrayList:<init>	()V
      //   189: iload 6
      //   191: istore 7
      //   193: iload 6
      //   195: istore 8
      //   197: iload 6
      //   199: istore 9
      //   201: aload_0
      //   202: aload 11
      //   204: putfield 95	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:orArgument_	Ljava/util/List;
      //   207: iload 6
      //   209: bipush 64
      //   211: ior
      //   212: istore 10
      //   214: iload 10
      //   216: istore 7
      //   218: iload 10
      //   220: istore 8
      //   222: iload 10
      //   224: istore 9
      //   226: aload_0
      //   227: getfield 95	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:orArgument_	Ljava/util/List;
      //   230: aload_1
      //   231: getstatic 49	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   234: aload_2
      //   235: invokevirtual 99	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   238: invokeinterface 105 2 0
      //   243: pop
      //   244: iload 10
      //   246: istore 6
      //   248: goto -212 -> 36
      //   251: iload 6
      //   253: istore 10
      //   255: iload 6
      //   257: bipush 32
      //   259: iand
      //   260: bipush 32
      //   262: if_icmpeq +62 -> 324
      //   265: iload 6
      //   267: istore 7
      //   269: iload 6
      //   271: istore 8
      //   273: iload 6
      //   275: istore 9
      //   277: new 92	java/util/ArrayList
      //   280: astore 11
      //   282: iload 6
      //   284: istore 7
      //   286: iload 6
      //   288: istore 8
      //   290: iload 6
      //   292: istore 9
      //   294: aload 11
      //   296: invokespecial 93	java/util/ArrayList:<init>	()V
      //   299: iload 6
      //   301: istore 7
      //   303: iload 6
      //   305: istore 8
      //   307: iload 6
      //   309: istore 9
      //   311: aload_0
      //   312: aload 11
      //   314: putfield 107	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:andArgument_	Ljava/util/List;
      //   317: iload 6
      //   319: bipush 32
      //   321: ior
      //   322: istore 10
      //   324: iload 10
      //   326: istore 7
      //   328: iload 10
      //   330: istore 8
      //   332: iload 10
      //   334: istore 9
      //   336: aload_0
      //   337: getfield 107	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:andArgument_	Ljava/util/List;
      //   340: aload_1
      //   341: getstatic 49	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   344: aload_2
      //   345: invokevirtual 99	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   348: invokeinterface 105 2 0
      //   353: pop
      //   354: iload 10
      //   356: istore 6
      //   358: goto -322 -> 36
      //   361: iload 6
      //   363: istore 7
      //   365: iload 6
      //   367: istore 8
      //   369: iload 6
      //   371: istore 9
      //   373: aload_0
      //   374: aload_0
      //   375: getfield 109	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:bitField0_	I
      //   378: bipush 16
      //   380: ior
      //   381: putfield 109	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:bitField0_	I
      //   384: iload 6
      //   386: istore 7
      //   388: iload 6
      //   390: istore 8
      //   392: iload 6
      //   394: istore 9
      //   396: aload_0
      //   397: aload_1
      //   398: invokevirtual 112	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   401: putfield 114	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:isInstanceTypeId_	I
      //   404: goto -368 -> 36
      //   407: aconst_null
      //   408: astore 11
      //   410: iload 6
      //   412: istore 7
      //   414: iload 6
      //   416: istore 8
      //   418: iload 6
      //   420: istore 9
      //   422: aload_0
      //   423: getfield 109	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:bitField0_	I
      //   426: bipush 8
      //   428: iand
      //   429: bipush 8
      //   431: if_icmpne +24 -> 455
      //   434: iload 6
      //   436: istore 7
      //   438: iload 6
      //   440: istore 8
      //   442: iload 6
      //   444: istore 9
      //   446: aload_0
      //   447: getfield 116	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:isInstanceType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   450: invokevirtual 122	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   453: astore 11
      //   455: iload 6
      //   457: istore 7
      //   459: iload 6
      //   461: istore 8
      //   463: iload 6
      //   465: istore 9
      //   467: aload_1
      //   468: getstatic 123	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   471: aload_2
      //   472: invokevirtual 99	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   475: checkcast 118	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type
      //   478: astore 12
      //   480: iload 6
      //   482: istore 7
      //   484: iload 6
      //   486: istore 8
      //   488: iload 6
      //   490: istore 9
      //   492: aload_0
      //   493: aload 12
      //   495: putfield 116	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:isInstanceType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   498: aload 11
      //   500: ifnull +44 -> 544
      //   503: iload 6
      //   505: istore 7
      //   507: iload 6
      //   509: istore 8
      //   511: iload 6
      //   513: istore 9
      //   515: aload 11
      //   517: aload 12
      //   519: invokevirtual 129	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   522: pop
      //   523: iload 6
      //   525: istore 7
      //   527: iload 6
      //   529: istore 8
      //   531: iload 6
      //   533: istore 9
      //   535: aload_0
      //   536: aload 11
      //   538: invokevirtual 133	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   541: putfield 116	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:isInstanceType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   544: iload 6
      //   546: istore 7
      //   548: iload 6
      //   550: istore 8
      //   552: iload 6
      //   554: istore 9
      //   556: aload_0
      //   557: aload_0
      //   558: getfield 109	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:bitField0_	I
      //   561: bipush 8
      //   563: ior
      //   564: putfield 109	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:bitField0_	I
      //   567: goto -531 -> 36
      //   570: iload 6
      //   572: istore 7
      //   574: iload 6
      //   576: istore 8
      //   578: iload 6
      //   580: istore 9
      //   582: aload_1
      //   583: invokevirtual 136	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readEnum	()I
      //   586: istore 13
      //   588: iload 6
      //   590: istore 7
      //   592: iload 6
      //   594: istore 8
      //   596: iload 6
      //   598: istore 9
      //   600: iload 13
      //   602: invokestatic 140	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression$ConstantValue:valueOf	(I)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression$ConstantValue;
      //   605: astore 11
      //   607: aload 11
      //   609: ifnonnull +44 -> 653
      //   612: iload 6
      //   614: istore 7
      //   616: iload 6
      //   618: istore 8
      //   620: iload 6
      //   622: istore 9
      //   624: aload 4
      //   626: iload 10
      //   628: invokevirtual 144	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:writeRawVarint32	(I)V
      //   631: iload 6
      //   633: istore 7
      //   635: iload 6
      //   637: istore 8
      //   639: iload 6
      //   641: istore 9
      //   643: aload 4
      //   645: iload 13
      //   647: invokevirtual 144	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:writeRawVarint32	(I)V
      //   650: goto -614 -> 36
      //   653: iload 6
      //   655: istore 7
      //   657: iload 6
      //   659: istore 8
      //   661: iload 6
      //   663: istore 9
      //   665: aload_0
      //   666: aload_0
      //   667: getfield 109	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:bitField0_	I
      //   670: iconst_4
      //   671: ior
      //   672: putfield 109	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:bitField0_	I
      //   675: iload 6
      //   677: istore 7
      //   679: iload 6
      //   681: istore 8
      //   683: iload 6
      //   685: istore 9
      //   687: aload_0
      //   688: aload 11
      //   690: putfield 146	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:constantValue_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression$ConstantValue;
      //   693: goto -657 -> 36
      //   696: iload 6
      //   698: istore 7
      //   700: iload 6
      //   702: istore 8
      //   704: iload 6
      //   706: istore 9
      //   708: aload_0
      //   709: aload_0
      //   710: getfield 109	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:bitField0_	I
      //   713: iconst_2
      //   714: ior
      //   715: putfield 109	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:bitField0_	I
      //   718: iload 6
      //   720: istore 7
      //   722: iload 6
      //   724: istore 8
      //   726: iload 6
      //   728: istore 9
      //   730: aload_0
      //   731: aload_1
      //   732: invokevirtual 112	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   735: putfield 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:valueParameterReference_	I
      //   738: goto -702 -> 36
      //   741: iload 6
      //   743: istore 7
      //   745: iload 6
      //   747: istore 8
      //   749: iload 6
      //   751: istore 9
      //   753: aload_0
      //   754: aload_0
      //   755: getfield 109	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:bitField0_	I
      //   758: iconst_1
      //   759: ior
      //   760: putfield 109	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:bitField0_	I
      //   763: iload 6
      //   765: istore 7
      //   767: iload 6
      //   769: istore 8
      //   771: iload 6
      //   773: istore 9
      //   775: aload_0
      //   776: aload_1
      //   777: invokevirtual 112	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   780: putfield 150	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:flags_	I
      //   783: goto -747 -> 36
      //   786: iconst_1
      //   787: istore 5
      //   789: goto -753 -> 36
      //   792: astore_1
      //   793: goto +45 -> 838
      //   796: astore_2
      //   797: iload 8
      //   799: istore 7
      //   801: new 61	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   804: astore_1
      //   805: iload 8
      //   807: istore 7
      //   809: aload_1
      //   810: aload_2
      //   811: invokevirtual 154	java/io/IOException:getMessage	()Ljava/lang/String;
      //   814: invokespecial 157	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   817: iload 8
      //   819: istore 7
      //   821: aload_1
      //   822: aload_0
      //   823: invokevirtual 161	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   826: athrow
      //   827: astore_1
      //   828: iload 9
      //   830: istore 7
      //   832: aload_1
      //   833: aload_0
      //   834: invokevirtual 161	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   837: athrow
      //   838: iload 7
      //   840: bipush 32
      //   842: iand
      //   843: bipush 32
      //   845: if_icmpne +14 -> 859
      //   848: aload_0
      //   849: aload_0
      //   850: getfield 107	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:andArgument_	Ljava/util/List;
      //   853: invokestatic 167	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   856: putfield 107	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:andArgument_	Ljava/util/List;
      //   859: iload 7
      //   861: bipush 64
      //   863: iand
      //   864: bipush 64
      //   866: if_icmpne +14 -> 880
      //   869: aload_0
      //   870: aload_0
      //   871: getfield 95	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:orArgument_	Ljava/util/List;
      //   874: invokestatic 167	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   877: putfield 95	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:orArgument_	Ljava/util/List;
      //   880: aload 4
      //   882: invokevirtual 170	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   885: aload_0
      //   886: aload_3
      //   887: invokevirtual 176	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   890: putfield 178	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   893: goto +14 -> 907
      //   896: astore_1
      //   897: aload_0
      //   898: aload_3
      //   899: invokevirtual 176	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   902: putfield 178	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   905: aload_1
      //   906: athrow
      //   907: aload_0
      //   908: invokevirtual 181	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:makeExtensionsImmutable	()V
      //   911: aload_1
      //   912: athrow
      //   913: iload 6
      //   915: bipush 32
      //   917: iand
      //   918: bipush 32
      //   920: if_icmpne +14 -> 934
      //   923: aload_0
      //   924: aload_0
      //   925: getfield 107	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:andArgument_	Ljava/util/List;
      //   928: invokestatic 167	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   931: putfield 107	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:andArgument_	Ljava/util/List;
      //   934: iload 6
      //   936: bipush 64
      //   938: iand
      //   939: bipush 64
      //   941: if_icmpne +14 -> 955
      //   944: aload_0
      //   945: aload_0
      //   946: getfield 95	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:orArgument_	Ljava/util/List;
      //   949: invokestatic 167	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   952: putfield 95	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:orArgument_	Ljava/util/List;
      //   955: aload 4
      //   957: invokevirtual 170	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   960: aload_0
      //   961: aload_3
      //   962: invokevirtual 176	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   965: putfield 178	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   968: goto +14 -> 982
      //   971: astore_1
      //   972: aload_0
      //   973: aload_3
      //   974: invokevirtual 176	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   977: putfield 178	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   980: aload_1
      //   981: athrow
      //   982: aload_0
      //   983: invokevirtual 181	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:makeExtensionsImmutable	()V
      //   986: return
      //   987: astore_2
      //   988: goto -103 -> 885
      //   991: astore_1
      //   992: goto -32 -> 960
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	995	0	this	Expression
      //   0	995	1	paramCodedInputStream	CodedInputStream
      //   0	995	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   22	952	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   28	928	4	localCodedOutputStream	CodedOutputStream
      //   31	757	5	i	int
      //   34	905	6	j	int
      //   43	821	7	k	int
      //   47	771	8	m	int
      //   51	778	9	n	int
      //   57	570	10	i1	int
      //   170	519	11	localObject	Object
      //   478	40	12	localType	ProtoBuf.Type
      //   586	60	13	i2	int
      // Exception table:
      //   from	to	target	type
      //   53	59	792	finally
      //   125	138	792	finally
      //   167	172	792	finally
      //   184	189	792	finally
      //   201	207	792	finally
      //   226	244	792	finally
      //   277	282	792	finally
      //   294	299	792	finally
      //   311	317	792	finally
      //   336	354	792	finally
      //   373	384	792	finally
      //   396	404	792	finally
      //   422	434	792	finally
      //   446	455	792	finally
      //   467	480	792	finally
      //   492	498	792	finally
      //   515	523	792	finally
      //   535	544	792	finally
      //   556	567	792	finally
      //   582	588	792	finally
      //   600	607	792	finally
      //   624	631	792	finally
      //   643	650	792	finally
      //   665	675	792	finally
      //   687	693	792	finally
      //   708	718	792	finally
      //   730	738	792	finally
      //   753	763	792	finally
      //   775	783	792	finally
      //   801	805	792	finally
      //   809	817	792	finally
      //   821	827	792	finally
      //   832	838	792	finally
      //   53	59	796	java/io/IOException
      //   125	138	796	java/io/IOException
      //   167	172	796	java/io/IOException
      //   184	189	796	java/io/IOException
      //   201	207	796	java/io/IOException
      //   226	244	796	java/io/IOException
      //   277	282	796	java/io/IOException
      //   294	299	796	java/io/IOException
      //   311	317	796	java/io/IOException
      //   336	354	796	java/io/IOException
      //   373	384	796	java/io/IOException
      //   396	404	796	java/io/IOException
      //   422	434	796	java/io/IOException
      //   446	455	796	java/io/IOException
      //   467	480	796	java/io/IOException
      //   492	498	796	java/io/IOException
      //   515	523	796	java/io/IOException
      //   535	544	796	java/io/IOException
      //   556	567	796	java/io/IOException
      //   582	588	796	java/io/IOException
      //   600	607	796	java/io/IOException
      //   624	631	796	java/io/IOException
      //   643	650	796	java/io/IOException
      //   665	675	796	java/io/IOException
      //   687	693	796	java/io/IOException
      //   708	718	796	java/io/IOException
      //   730	738	796	java/io/IOException
      //   753	763	796	java/io/IOException
      //   775	783	796	java/io/IOException
      //   53	59	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   125	138	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   167	172	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   184	189	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   201	207	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   226	244	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   277	282	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   294	299	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   311	317	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   336	354	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   373	384	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   396	404	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   422	434	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   446	455	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   467	480	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   492	498	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   515	523	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   535	544	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   556	567	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   582	588	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   600	607	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   624	631	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   643	650	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   665	675	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   687	693	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   708	718	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   730	738	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   753	763	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   775	783	827	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   880	885	896	finally
      //   955	960	971	finally
      //   880	885	987	java/io/IOException
      //   955	960	991	java/io/IOException
    }
    
    private Expression(GeneratedMessageLite.Builder paramBuilder)
    {
      super();
      this.unknownFields = paramBuilder.getUnknownFields();
    }
    
    private Expression(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static Expression getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.flags_ = 0;
      this.valueParameterReference_ = 0;
      this.constantValue_ = ConstantValue.TRUE;
      this.isInstanceType_ = ProtoBuf.Type.getDefaultInstance();
      this.isInstanceTypeId_ = 0;
      this.andArgument_ = Collections.emptyList();
      this.orArgument_ = Collections.emptyList();
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$23700();
    }
    
    public static Builder newBuilder(Expression paramExpression)
    {
      return newBuilder().mergeFrom(paramExpression);
    }
    
    public Expression getAndArgument(int paramInt)
    {
      return (Expression)this.andArgument_.get(paramInt);
    }
    
    public int getAndArgumentCount()
    {
      return this.andArgument_.size();
    }
    
    public ConstantValue getConstantValue()
    {
      return this.constantValue_;
    }
    
    public Expression getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public int getFlags()
    {
      return this.flags_;
    }
    
    public ProtoBuf.Type getIsInstanceType()
    {
      return this.isInstanceType_;
    }
    
    public int getIsInstanceTypeId()
    {
      return this.isInstanceTypeId_;
    }
    
    public Expression getOrArgument(int paramInt)
    {
      return (Expression)this.orArgument_.get(paramInt);
    }
    
    public int getOrArgumentCount()
    {
      return this.orArgument_.size();
    }
    
    public Parser<Expression> getParserForType()
    {
      return PARSER;
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
        k = CodedOutputStream.computeInt32Size(1, this.flags_) + 0;
      } else {
        k = 0;
      }
      i = k;
      if ((this.bitField0_ & 0x2) == 2) {
        i = k + CodedOutputStream.computeInt32Size(2, this.valueParameterReference_);
      }
      int m = i;
      if ((this.bitField0_ & 0x4) == 4) {
        m = i + CodedOutputStream.computeEnumSize(3, this.constantValue_.getNumber());
      }
      int k = m;
      if ((this.bitField0_ & 0x8) == 8) {
        k = m + CodedOutputStream.computeMessageSize(4, this.isInstanceType_);
      }
      i = k;
      if ((this.bitField0_ & 0x10) == 16) {
        i = k + CodedOutputStream.computeInt32Size(5, this.isInstanceTypeId_);
      }
      int n;
      for (k = 0;; k++)
      {
        n = i;
        m = j;
        if (k >= this.andArgument_.size()) {
          break;
        }
        i += CodedOutputStream.computeMessageSize(6, (MessageLite)this.andArgument_.get(k));
      }
      while (m < this.orArgument_.size())
      {
        n += CodedOutputStream.computeMessageSize(7, (MessageLite)this.orArgument_.get(m));
        m++;
      }
      i = n + this.unknownFields.size();
      this.memoizedSerializedSize = i;
      return i;
    }
    
    public int getValueParameterReference()
    {
      return this.valueParameterReference_;
    }
    
    public boolean hasConstantValue()
    {
      boolean bool;
      if ((this.bitField0_ & 0x4) == 4) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasFlags()
    {
      int i = this.bitField0_;
      boolean bool = true;
      if ((i & 0x1) != 1) {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasIsInstanceType()
    {
      boolean bool;
      if ((this.bitField0_ & 0x8) == 8) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasIsInstanceTypeId()
    {
      boolean bool;
      if ((this.bitField0_ & 0x10) == 16) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasValueParameterReference()
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
      if ((hasIsInstanceType()) && (!getIsInstanceType().isInitialized()))
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      for (i = 0; i < getAndArgumentCount(); i++) {
        if (!getAndArgument(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
      }
      for (i = 0; i < getOrArgumentCount(); i++) {
        if (!getOrArgument(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
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
        paramCodedOutputStream.writeInt32(1, this.flags_);
      }
      if ((this.bitField0_ & 0x2) == 2) {
        paramCodedOutputStream.writeInt32(2, this.valueParameterReference_);
      }
      if ((this.bitField0_ & 0x4) == 4) {
        paramCodedOutputStream.writeEnum(3, this.constantValue_.getNumber());
      }
      if ((this.bitField0_ & 0x8) == 8) {
        paramCodedOutputStream.writeMessage(4, this.isInstanceType_);
      }
      if ((this.bitField0_ & 0x10) == 16) {
        paramCodedOutputStream.writeInt32(5, this.isInstanceTypeId_);
      }
      int i = 0;
      int k;
      for (int j = 0;; j++)
      {
        k = i;
        if (j >= this.andArgument_.size()) {
          break;
        }
        paramCodedOutputStream.writeMessage(6, (MessageLite)this.andArgument_.get(j));
      }
      while (k < this.orArgument_.size())
      {
        paramCodedOutputStream.writeMessage(7, (MessageLite)this.orArgument_.get(k));
        k++;
      }
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Builder
      extends GeneratedMessageLite.Builder<ProtoBuf.Expression, Builder>
      implements ProtoBuf.ExpressionOrBuilder
    {
      private List<ProtoBuf.Expression> andArgument_ = Collections.emptyList();
      private int bitField0_;
      private ProtoBuf.Expression.ConstantValue constantValue_ = ProtoBuf.Expression.ConstantValue.TRUE;
      private int flags_;
      private int isInstanceTypeId_;
      private ProtoBuf.Type isInstanceType_ = ProtoBuf.Type.getDefaultInstance();
      private List<ProtoBuf.Expression> orArgument_ = Collections.emptyList();
      private int valueParameterReference_;
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void ensureAndArgumentIsMutable()
      {
        if ((this.bitField0_ & 0x20) != 32)
        {
          this.andArgument_ = new ArrayList(this.andArgument_);
          this.bitField0_ |= 0x20;
        }
      }
      
      private void ensureOrArgumentIsMutable()
      {
        if ((this.bitField0_ & 0x40) != 64)
        {
          this.orArgument_ = new ArrayList(this.orArgument_);
          this.bitField0_ |= 0x40;
        }
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public ProtoBuf.Expression build()
      {
        ProtoBuf.Expression localExpression = buildPartial();
        if (localExpression.isInitialized()) {
          return localExpression;
        }
        throw newUninitializedMessageException(localExpression);
      }
      
      public ProtoBuf.Expression buildPartial()
      {
        ProtoBuf.Expression localExpression = new ProtoBuf.Expression(this, null);
        int i = this.bitField0_;
        int j = 1;
        if ((i & 0x1) != 1) {
          j = 0;
        }
        ProtoBuf.Expression.access$23902(localExpression, this.flags_);
        int k = j;
        if ((i & 0x2) == 2) {
          k = j | 0x2;
        }
        ProtoBuf.Expression.access$24002(localExpression, this.valueParameterReference_);
        j = k;
        if ((i & 0x4) == 4) {
          j = k | 0x4;
        }
        ProtoBuf.Expression.access$24102(localExpression, this.constantValue_);
        k = j;
        if ((i & 0x8) == 8) {
          k = j | 0x8;
        }
        ProtoBuf.Expression.access$24202(localExpression, this.isInstanceType_);
        j = k;
        if ((i & 0x10) == 16) {
          j = k | 0x10;
        }
        ProtoBuf.Expression.access$24302(localExpression, this.isInstanceTypeId_);
        if ((this.bitField0_ & 0x20) == 32)
        {
          this.andArgument_ = Collections.unmodifiableList(this.andArgument_);
          this.bitField0_ &= 0xFFFFFFDF;
        }
        ProtoBuf.Expression.access$24402(localExpression, this.andArgument_);
        if ((this.bitField0_ & 0x40) == 64)
        {
          this.orArgument_ = Collections.unmodifiableList(this.orArgument_);
          this.bitField0_ &= 0xFFFFFFBF;
        }
        ProtoBuf.Expression.access$24502(localExpression, this.orArgument_);
        ProtoBuf.Expression.access$24602(localExpression, j);
        return localExpression;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public ProtoBuf.Expression getAndArgument(int paramInt)
      {
        return (ProtoBuf.Expression)this.andArgument_.get(paramInt);
      }
      
      public int getAndArgumentCount()
      {
        return this.andArgument_.size();
      }
      
      public ProtoBuf.Expression getDefaultInstanceForType()
      {
        return ProtoBuf.Expression.getDefaultInstance();
      }
      
      public ProtoBuf.Type getIsInstanceType()
      {
        return this.isInstanceType_;
      }
      
      public ProtoBuf.Expression getOrArgument(int paramInt)
      {
        return (ProtoBuf.Expression)this.orArgument_.get(paramInt);
      }
      
      public int getOrArgumentCount()
      {
        return this.orArgument_.size();
      }
      
      public boolean hasIsInstanceType()
      {
        boolean bool;
        if ((this.bitField0_ & 0x8) == 8) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public final boolean isInitialized()
      {
        if ((hasIsInstanceType()) && (!getIsInstanceType().isInitialized())) {
          return false;
        }
        for (int i = 0; i < getAndArgumentCount(); i++) {
          if (!getAndArgument(i).isInitialized()) {
            return false;
          }
        }
        for (i = 0; i < getOrArgumentCount(); i++) {
          if (!getOrArgument(i).isInitialized()) {
            return false;
          }
        }
        return true;
      }
      
      public Builder mergeFrom(ProtoBuf.Expression paramExpression)
      {
        if (paramExpression == ProtoBuf.Expression.getDefaultInstance()) {
          return this;
        }
        if (paramExpression.hasFlags()) {
          setFlags(paramExpression.getFlags());
        }
        if (paramExpression.hasValueParameterReference()) {
          setValueParameterReference(paramExpression.getValueParameterReference());
        }
        if (paramExpression.hasConstantValue()) {
          setConstantValue(paramExpression.getConstantValue());
        }
        if (paramExpression.hasIsInstanceType()) {
          mergeIsInstanceType(paramExpression.getIsInstanceType());
        }
        if (paramExpression.hasIsInstanceTypeId()) {
          setIsInstanceTypeId(paramExpression.getIsInstanceTypeId());
        }
        if (!paramExpression.andArgument_.isEmpty()) {
          if (this.andArgument_.isEmpty())
          {
            this.andArgument_ = paramExpression.andArgument_;
            this.bitField0_ &= 0xFFFFFFDF;
          }
          else
          {
            ensureAndArgumentIsMutable();
            this.andArgument_.addAll(paramExpression.andArgument_);
          }
        }
        if (!paramExpression.orArgument_.isEmpty()) {
          if (this.orArgument_.isEmpty())
          {
            this.orArgument_ = paramExpression.orArgument_;
            this.bitField0_ &= 0xFFFFFFBF;
          }
          else
          {
            ensureOrArgumentIsMutable();
            this.orArgument_.addAll(paramExpression.orArgument_);
          }
        }
        setUnknownFields(getUnknownFields().concat(paramExpression.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 270	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 276 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 142	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_1
        //   29: aload_3
        //   30: astore_2
        //   31: goto +15 -> 46
        //   34: astore_1
        //   35: aload_1
        //   36: invokevirtual 279	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   39: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression
        //   42: astore_2
        //   43: aload_1
        //   44: athrow
        //   45: astore_1
        //   46: aload_2
        //   47: ifnull +9 -> 56
        //   50: aload_0
        //   51: aload_2
        //   52: invokevirtual 142	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Expression$Builder;
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
      
      public Builder mergeIsInstanceType(ProtoBuf.Type paramType)
      {
        if (((this.bitField0_ & 0x8) == 8) && (this.isInstanceType_ != ProtoBuf.Type.getDefaultInstance())) {
          this.isInstanceType_ = ProtoBuf.Type.newBuilder(this.isInstanceType_).mergeFrom(paramType).buildPartial();
        } else {
          this.isInstanceType_ = paramType;
        }
        this.bitField0_ |= 0x8;
        return this;
      }
      
      public Builder setConstantValue(ProtoBuf.Expression.ConstantValue paramConstantValue)
      {
        if (paramConstantValue != null)
        {
          this.bitField0_ |= 0x4;
          this.constantValue_ = paramConstantValue;
          return this;
        }
        throw null;
      }
      
      public Builder setFlags(int paramInt)
      {
        this.bitField0_ |= 0x1;
        this.flags_ = paramInt;
        return this;
      }
      
      public Builder setIsInstanceTypeId(int paramInt)
      {
        this.bitField0_ |= 0x10;
        this.isInstanceTypeId_ = paramInt;
        return this;
      }
      
      public Builder setValueParameterReference(int paramInt)
      {
        this.bitField0_ |= 0x2;
        this.valueParameterReference_ = paramInt;
        return this;
      }
    }
    
    public static enum ConstantValue
      implements Internal.EnumLite
    {
      private static Internal.EnumLiteMap<ConstantValue> internalValueMap = new Internal.EnumLiteMap()
      {
        public ProtoBuf.Expression.ConstantValue findValueByNumber(int paramAnonymousInt)
        {
          return ProtoBuf.Expression.ConstantValue.valueOf(paramAnonymousInt);
        }
      };
      private final int value;
      
      static
      {
        FALSE = new ConstantValue("FALSE", 1, 1, 1);
        ConstantValue localConstantValue = new ConstantValue("NULL", 2, 2, 2);
        NULL = localConstantValue;
        $VALUES = new ConstantValue[] { TRUE, FALSE, localConstantValue };
      }
      
      private ConstantValue(int paramInt1, int paramInt2)
      {
        this.value = paramInt2;
      }
      
      public static ConstantValue valueOf(int paramInt)
      {
        if (paramInt != 0)
        {
          if (paramInt != 1)
          {
            if (paramInt != 2) {
              return null;
            }
            return NULL;
          }
          return FALSE;
        }
        return TRUE;
      }
      
      public final int getNumber()
      {
        return this.value;
      }
    }
  }
  
  public static final class Function
    extends GeneratedMessageLite.ExtendableMessage<Function>
    implements ProtoBuf.FunctionOrBuilder
  {
    public static Parser<Function> PARSER = new AbstractParser()
    {
      public ProtoBuf.Function parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new ProtoBuf.Function(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final Function defaultInstance;
    private int bitField0_;
    private ProtoBuf.Contract contract_;
    private int flags_;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private int name_;
    private int oldFlags_;
    private int receiverTypeId_;
    private ProtoBuf.Type receiverType_;
    private int returnTypeId_;
    private ProtoBuf.Type returnType_;
    private List<ProtoBuf.TypeParameter> typeParameter_;
    private ProtoBuf.TypeTable typeTable_;
    private final ByteString unknownFields;
    private List<ProtoBuf.ValueParameter> valueParameter_;
    private List<Integer> versionRequirement_;
    
    static
    {
      Function localFunction = new Function(true);
      defaultInstance = localFunction;
      localFunction.initFields();
    }
    
    /* Error */
    private Function(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 68	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite$ExtendableMessage:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: i2b
      //   7: putfield 70	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:memoizedIsInitialized	B
      //   10: aload_0
      //   11: iconst_m1
      //   12: putfield 72	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:memoizedSerializedSize	I
      //   15: aload_0
      //   16: invokespecial 61	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:initFields	()V
      //   19: invokestatic 78	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   22: astore_3
      //   23: aload_3
      //   24: iconst_1
      //   25: invokestatic 84	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   28: astore 4
      //   30: iconst_0
      //   31: istore 5
      //   33: iconst_0
      //   34: istore 6
      //   36: iload 5
      //   38: ifne +1762 -> 1800
      //   41: iload 6
      //   43: istore 7
      //   45: iload 6
      //   47: istore 8
      //   49: iload 6
      //   51: istore 9
      //   53: aload_1
      //   54: invokevirtual 90	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   57: istore 10
      //   59: aconst_null
      //   60: astore 11
      //   62: aconst_null
      //   63: astore 12
      //   65: aconst_null
      //   66: astore 13
      //   68: aconst_null
      //   69: astore 14
      //   71: iload 10
      //   73: lookupswitch	default:+123->196, 0:+1567->1640, 8:+1522->1595, 16:+1477->1550, 26:+1313->1386, 34:+1203->1276, 42:+1039->1112, 50:+926->999, 56:+880->953, 64:+834->907, 72:+789->862, 242:+622->695, 248:+510->583, 250:+313->386, 258:+150->223
      //   196: iload 6
      //   198: istore 7
      //   200: iload 6
      //   202: istore 8
      //   204: iload 6
      //   206: istore 9
      //   208: aload_0
      //   209: aload_1
      //   210: aload 4
      //   212: aload_2
      //   213: iload 10
      //   215: invokevirtual 94	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   218: istore 15
      //   220: goto +1426 -> 1646
      //   223: iload 6
      //   225: istore 7
      //   227: iload 6
      //   229: istore 8
      //   231: iload 6
      //   233: istore 9
      //   235: aload_0
      //   236: getfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:bitField0_	I
      //   239: sipush 256
      //   242: iand
      //   243: sipush 256
      //   246: if_icmpne +24 -> 270
      //   249: iload 6
      //   251: istore 7
      //   253: iload 6
      //   255: istore 8
      //   257: iload 6
      //   259: istore 9
      //   261: aload_0
      //   262: getfield 98	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:contract_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract;
      //   265: invokevirtual 104	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract$Builder;
      //   268: astore 14
      //   270: iload 6
      //   272: istore 7
      //   274: iload 6
      //   276: istore 8
      //   278: iload 6
      //   280: istore 9
      //   282: aload_1
      //   283: getstatic 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   286: aload_2
      //   287: invokevirtual 109	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   290: checkcast 100	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract
      //   293: astore 11
      //   295: iload 6
      //   297: istore 7
      //   299: iload 6
      //   301: istore 8
      //   303: iload 6
      //   305: istore 9
      //   307: aload_0
      //   308: aload 11
      //   310: putfield 98	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:contract_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract;
      //   313: aload 14
      //   315: ifnull +44 -> 359
      //   318: iload 6
      //   320: istore 7
      //   322: iload 6
      //   324: istore 8
      //   326: iload 6
      //   328: istore 9
      //   330: aload 14
      //   332: aload 11
      //   334: invokevirtual 115	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract$Builder;
      //   337: pop
      //   338: iload 6
      //   340: istore 7
      //   342: iload 6
      //   344: istore 8
      //   346: iload 6
      //   348: istore 9
      //   350: aload_0
      //   351: aload 14
      //   353: invokevirtual 119	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract;
      //   356: putfield 98	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:contract_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Contract;
      //   359: iload 6
      //   361: istore 7
      //   363: iload 6
      //   365: istore 8
      //   367: iload 6
      //   369: istore 9
      //   371: aload_0
      //   372: aload_0
      //   373: getfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:bitField0_	I
      //   376: sipush 256
      //   379: ior
      //   380: putfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:bitField0_	I
      //   383: goto -347 -> 36
      //   386: iload 6
      //   388: istore 7
      //   390: iload 6
      //   392: istore 8
      //   394: iload 6
      //   396: istore 9
      //   398: aload_1
      //   399: aload_1
      //   400: invokevirtual 122	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readRawVarint32	()I
      //   403: invokevirtual 126	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:pushLimit	(I)I
      //   406: istore 16
      //   408: iload 6
      //   410: istore 10
      //   412: iload 6
      //   414: sipush 1024
      //   417: iand
      //   418: sipush 1024
      //   421: if_icmpeq +86 -> 507
      //   424: iload 6
      //   426: istore 10
      //   428: iload 6
      //   430: istore 7
      //   432: iload 6
      //   434: istore 8
      //   436: iload 6
      //   438: istore 9
      //   440: aload_1
      //   441: invokevirtual 129	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
      //   444: ifle +63 -> 507
      //   447: iload 6
      //   449: istore 7
      //   451: iload 6
      //   453: istore 8
      //   455: iload 6
      //   457: istore 9
      //   459: new 131	java/util/ArrayList
      //   462: astore 14
      //   464: iload 6
      //   466: istore 7
      //   468: iload 6
      //   470: istore 8
      //   472: iload 6
      //   474: istore 9
      //   476: aload 14
      //   478: invokespecial 132	java/util/ArrayList:<init>	()V
      //   481: iload 6
      //   483: istore 7
      //   485: iload 6
      //   487: istore 8
      //   489: iload 6
      //   491: istore 9
      //   493: aload_0
      //   494: aload 14
      //   496: putfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:versionRequirement_	Ljava/util/List;
      //   499: iload 6
      //   501: sipush 1024
      //   504: ior
      //   505: istore 10
      //   507: iload 10
      //   509: istore 7
      //   511: iload 10
      //   513: istore 8
      //   515: iload 10
      //   517: istore 9
      //   519: aload_1
      //   520: invokevirtual 129	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
      //   523: ifle +35 -> 558
      //   526: iload 10
      //   528: istore 7
      //   530: iload 10
      //   532: istore 8
      //   534: iload 10
      //   536: istore 9
      //   538: aload_0
      //   539: getfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:versionRequirement_	Ljava/util/List;
      //   542: aload_1
      //   543: invokevirtual 137	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   546: invokestatic 143	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   549: invokeinterface 149 2 0
      //   554: pop
      //   555: goto -48 -> 507
      //   558: iload 10
      //   560: istore 7
      //   562: iload 10
      //   564: istore 8
      //   566: iload 10
      //   568: istore 9
      //   570: aload_1
      //   571: iload 16
      //   573: invokevirtual 153	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:popLimit	(I)V
      //   576: iload 10
      //   578: istore 6
      //   580: goto -544 -> 36
      //   583: iload 6
      //   585: istore 10
      //   587: iload 6
      //   589: sipush 1024
      //   592: iand
      //   593: sipush 1024
      //   596: if_icmpeq +63 -> 659
      //   599: iload 6
      //   601: istore 7
      //   603: iload 6
      //   605: istore 8
      //   607: iload 6
      //   609: istore 9
      //   611: new 131	java/util/ArrayList
      //   614: astore 14
      //   616: iload 6
      //   618: istore 7
      //   620: iload 6
      //   622: istore 8
      //   624: iload 6
      //   626: istore 9
      //   628: aload 14
      //   630: invokespecial 132	java/util/ArrayList:<init>	()V
      //   633: iload 6
      //   635: istore 7
      //   637: iload 6
      //   639: istore 8
      //   641: iload 6
      //   643: istore 9
      //   645: aload_0
      //   646: aload 14
      //   648: putfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:versionRequirement_	Ljava/util/List;
      //   651: iload 6
      //   653: sipush 1024
      //   656: ior
      //   657: istore 10
      //   659: iload 10
      //   661: istore 7
      //   663: iload 10
      //   665: istore 8
      //   667: iload 10
      //   669: istore 9
      //   671: aload_0
      //   672: getfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:versionRequirement_	Ljava/util/List;
      //   675: aload_1
      //   676: invokevirtual 137	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   679: invokestatic 143	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   682: invokeinterface 149 2 0
      //   687: pop
      //   688: iload 10
      //   690: istore 6
      //   692: goto -656 -> 36
      //   695: aload 11
      //   697: astore 14
      //   699: iload 6
      //   701: istore 7
      //   703: iload 6
      //   705: istore 8
      //   707: iload 6
      //   709: istore 9
      //   711: aload_0
      //   712: getfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:bitField0_	I
      //   715: sipush 128
      //   718: iand
      //   719: sipush 128
      //   722: if_icmpne +24 -> 746
      //   725: iload 6
      //   727: istore 7
      //   729: iload 6
      //   731: istore 8
      //   733: iload 6
      //   735: istore 9
      //   737: aload_0
      //   738: getfield 155	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:typeTable_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable;
      //   741: invokevirtual 160	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable$Builder;
      //   744: astore 14
      //   746: iload 6
      //   748: istore 7
      //   750: iload 6
      //   752: istore 8
      //   754: iload 6
      //   756: istore 9
      //   758: aload_1
      //   759: getstatic 161	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   762: aload_2
      //   763: invokevirtual 109	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   766: checkcast 157	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable
      //   769: astore 11
      //   771: iload 6
      //   773: istore 7
      //   775: iload 6
      //   777: istore 8
      //   779: iload 6
      //   781: istore 9
      //   783: aload_0
      //   784: aload 11
      //   786: putfield 155	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:typeTable_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable;
      //   789: aload 14
      //   791: ifnull +44 -> 835
      //   794: iload 6
      //   796: istore 7
      //   798: iload 6
      //   800: istore 8
      //   802: iload 6
      //   804: istore 9
      //   806: aload 14
      //   808: aload 11
      //   810: invokevirtual 166	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable$Builder;
      //   813: pop
      //   814: iload 6
      //   816: istore 7
      //   818: iload 6
      //   820: istore 8
      //   822: iload 6
      //   824: istore 9
      //   826: aload_0
      //   827: aload 14
      //   829: invokevirtual 169	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable;
      //   832: putfield 155	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:typeTable_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable;
      //   835: iload 6
      //   837: istore 7
      //   839: iload 6
      //   841: istore 8
      //   843: iload 6
      //   845: istore 9
      //   847: aload_0
      //   848: aload_0
      //   849: getfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:bitField0_	I
      //   852: sipush 128
      //   855: ior
      //   856: putfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:bitField0_	I
      //   859: goto -823 -> 36
      //   862: iload 6
      //   864: istore 7
      //   866: iload 6
      //   868: istore 8
      //   870: iload 6
      //   872: istore 9
      //   874: aload_0
      //   875: aload_0
      //   876: getfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:bitField0_	I
      //   879: iconst_1
      //   880: ior
      //   881: putfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:bitField0_	I
      //   884: iload 6
      //   886: istore 7
      //   888: iload 6
      //   890: istore 8
      //   892: iload 6
      //   894: istore 9
      //   896: aload_0
      //   897: aload_1
      //   898: invokevirtual 137	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   901: putfield 171	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:flags_	I
      //   904: goto -868 -> 36
      //   907: iload 6
      //   909: istore 7
      //   911: iload 6
      //   913: istore 8
      //   915: iload 6
      //   917: istore 9
      //   919: aload_0
      //   920: aload_0
      //   921: getfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:bitField0_	I
      //   924: bipush 64
      //   926: ior
      //   927: putfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:bitField0_	I
      //   930: iload 6
      //   932: istore 7
      //   934: iload 6
      //   936: istore 8
      //   938: iload 6
      //   940: istore 9
      //   942: aload_0
      //   943: aload_1
      //   944: invokevirtual 137	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   947: putfield 173	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:receiverTypeId_	I
      //   950: goto -914 -> 36
      //   953: iload 6
      //   955: istore 7
      //   957: iload 6
      //   959: istore 8
      //   961: iload 6
      //   963: istore 9
      //   965: aload_0
      //   966: aload_0
      //   967: getfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:bitField0_	I
      //   970: bipush 16
      //   972: ior
      //   973: putfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:bitField0_	I
      //   976: iload 6
      //   978: istore 7
      //   980: iload 6
      //   982: istore 8
      //   984: iload 6
      //   986: istore 9
      //   988: aload_0
      //   989: aload_1
      //   990: invokevirtual 137	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   993: putfield 175	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:returnTypeId_	I
      //   996: goto -960 -> 36
      //   999: iload 6
      //   1001: istore 10
      //   1003: iload 6
      //   1005: sipush 256
      //   1008: iand
      //   1009: sipush 256
      //   1012: if_icmpeq +63 -> 1075
      //   1015: iload 6
      //   1017: istore 7
      //   1019: iload 6
      //   1021: istore 8
      //   1023: iload 6
      //   1025: istore 9
      //   1027: new 131	java/util/ArrayList
      //   1030: astore 14
      //   1032: iload 6
      //   1034: istore 7
      //   1036: iload 6
      //   1038: istore 8
      //   1040: iload 6
      //   1042: istore 9
      //   1044: aload 14
      //   1046: invokespecial 132	java/util/ArrayList:<init>	()V
      //   1049: iload 6
      //   1051: istore 7
      //   1053: iload 6
      //   1055: istore 8
      //   1057: iload 6
      //   1059: istore 9
      //   1061: aload_0
      //   1062: aload 14
      //   1064: putfield 177	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:valueParameter_	Ljava/util/List;
      //   1067: iload 6
      //   1069: sipush 256
      //   1072: ior
      //   1073: istore 10
      //   1075: iload 10
      //   1077: istore 7
      //   1079: iload 10
      //   1081: istore 8
      //   1083: iload 10
      //   1085: istore 9
      //   1087: aload_0
      //   1088: getfield 177	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:valueParameter_	Ljava/util/List;
      //   1091: aload_1
      //   1092: getstatic 180	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   1095: aload_2
      //   1096: invokevirtual 109	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   1099: invokeinterface 149 2 0
      //   1104: pop
      //   1105: iload 10
      //   1107: istore 6
      //   1109: goto -1073 -> 36
      //   1112: aload 12
      //   1114: astore 14
      //   1116: iload 6
      //   1118: istore 7
      //   1120: iload 6
      //   1122: istore 8
      //   1124: iload 6
      //   1126: istore 9
      //   1128: aload_0
      //   1129: getfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:bitField0_	I
      //   1132: bipush 32
      //   1134: iand
      //   1135: bipush 32
      //   1137: if_icmpne +24 -> 1161
      //   1140: iload 6
      //   1142: istore 7
      //   1144: iload 6
      //   1146: istore 8
      //   1148: iload 6
      //   1150: istore 9
      //   1152: aload_0
      //   1153: getfield 182	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:receiverType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   1156: invokevirtual 187	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   1159: astore 14
      //   1161: iload 6
      //   1163: istore 7
      //   1165: iload 6
      //   1167: istore 8
      //   1169: iload 6
      //   1171: istore 9
      //   1173: aload_1
      //   1174: getstatic 188	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   1177: aload_2
      //   1178: invokevirtual 109	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   1181: checkcast 184	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type
      //   1184: astore 11
      //   1186: iload 6
      //   1188: istore 7
      //   1190: iload 6
      //   1192: istore 8
      //   1194: iload 6
      //   1196: istore 9
      //   1198: aload_0
      //   1199: aload 11
      //   1201: putfield 182	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:receiverType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   1204: aload 14
      //   1206: ifnull +44 -> 1250
      //   1209: iload 6
      //   1211: istore 7
      //   1213: iload 6
      //   1215: istore 8
      //   1217: iload 6
      //   1219: istore 9
      //   1221: aload 14
      //   1223: aload 11
      //   1225: invokevirtual 193	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   1228: pop
      //   1229: iload 6
      //   1231: istore 7
      //   1233: iload 6
      //   1235: istore 8
      //   1237: iload 6
      //   1239: istore 9
      //   1241: aload_0
      //   1242: aload 14
      //   1244: invokevirtual 196	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   1247: putfield 182	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:receiverType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   1250: iload 6
      //   1252: istore 7
      //   1254: iload 6
      //   1256: istore 8
      //   1258: iload 6
      //   1260: istore 9
      //   1262: aload_0
      //   1263: aload_0
      //   1264: getfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:bitField0_	I
      //   1267: bipush 32
      //   1269: ior
      //   1270: putfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:bitField0_	I
      //   1273: goto -1237 -> 36
      //   1276: iload 6
      //   1278: istore 10
      //   1280: iload 6
      //   1282: bipush 32
      //   1284: iand
      //   1285: bipush 32
      //   1287: if_icmpeq +62 -> 1349
      //   1290: iload 6
      //   1292: istore 7
      //   1294: iload 6
      //   1296: istore 8
      //   1298: iload 6
      //   1300: istore 9
      //   1302: new 131	java/util/ArrayList
      //   1305: astore 14
      //   1307: iload 6
      //   1309: istore 7
      //   1311: iload 6
      //   1313: istore 8
      //   1315: iload 6
      //   1317: istore 9
      //   1319: aload 14
      //   1321: invokespecial 132	java/util/ArrayList:<init>	()V
      //   1324: iload 6
      //   1326: istore 7
      //   1328: iload 6
      //   1330: istore 8
      //   1332: iload 6
      //   1334: istore 9
      //   1336: aload_0
      //   1337: aload 14
      //   1339: putfield 198	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:typeParameter_	Ljava/util/List;
      //   1342: iload 6
      //   1344: bipush 32
      //   1346: ior
      //   1347: istore 10
      //   1349: iload 10
      //   1351: istore 7
      //   1353: iload 10
      //   1355: istore 8
      //   1357: iload 10
      //   1359: istore 9
      //   1361: aload_0
      //   1362: getfield 198	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:typeParameter_	Ljava/util/List;
      //   1365: aload_1
      //   1366: getstatic 201	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   1369: aload_2
      //   1370: invokevirtual 109	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   1373: invokeinterface 149 2 0
      //   1378: pop
      //   1379: iload 10
      //   1381: istore 6
      //   1383: goto -1347 -> 36
      //   1386: aload 13
      //   1388: astore 14
      //   1390: iload 6
      //   1392: istore 7
      //   1394: iload 6
      //   1396: istore 8
      //   1398: iload 6
      //   1400: istore 9
      //   1402: aload_0
      //   1403: getfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:bitField0_	I
      //   1406: bipush 8
      //   1408: iand
      //   1409: bipush 8
      //   1411: if_icmpne +24 -> 1435
      //   1414: iload 6
      //   1416: istore 7
      //   1418: iload 6
      //   1420: istore 8
      //   1422: iload 6
      //   1424: istore 9
      //   1426: aload_0
      //   1427: getfield 203	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:returnType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   1430: invokevirtual 187	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   1433: astore 14
      //   1435: iload 6
      //   1437: istore 7
      //   1439: iload 6
      //   1441: istore 8
      //   1443: iload 6
      //   1445: istore 9
      //   1447: aload_1
      //   1448: getstatic 188	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   1451: aload_2
      //   1452: invokevirtual 109	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   1455: checkcast 184	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type
      //   1458: astore 11
      //   1460: iload 6
      //   1462: istore 7
      //   1464: iload 6
      //   1466: istore 8
      //   1468: iload 6
      //   1470: istore 9
      //   1472: aload_0
      //   1473: aload 11
      //   1475: putfield 203	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:returnType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   1478: aload 14
      //   1480: ifnull +44 -> 1524
      //   1483: iload 6
      //   1485: istore 7
      //   1487: iload 6
      //   1489: istore 8
      //   1491: iload 6
      //   1493: istore 9
      //   1495: aload 14
      //   1497: aload 11
      //   1499: invokevirtual 193	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   1502: pop
      //   1503: iload 6
      //   1505: istore 7
      //   1507: iload 6
      //   1509: istore 8
      //   1511: iload 6
      //   1513: istore 9
      //   1515: aload_0
      //   1516: aload 14
      //   1518: invokevirtual 196	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   1521: putfield 203	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:returnType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   1524: iload 6
      //   1526: istore 7
      //   1528: iload 6
      //   1530: istore 8
      //   1532: iload 6
      //   1534: istore 9
      //   1536: aload_0
      //   1537: aload_0
      //   1538: getfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:bitField0_	I
      //   1541: bipush 8
      //   1543: ior
      //   1544: putfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:bitField0_	I
      //   1547: goto -1511 -> 36
      //   1550: iload 6
      //   1552: istore 7
      //   1554: iload 6
      //   1556: istore 8
      //   1558: iload 6
      //   1560: istore 9
      //   1562: aload_0
      //   1563: aload_0
      //   1564: getfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:bitField0_	I
      //   1567: iconst_4
      //   1568: ior
      //   1569: putfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:bitField0_	I
      //   1572: iload 6
      //   1574: istore 7
      //   1576: iload 6
      //   1578: istore 8
      //   1580: iload 6
      //   1582: istore 9
      //   1584: aload_0
      //   1585: aload_1
      //   1586: invokevirtual 137	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   1589: putfield 205	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:name_	I
      //   1592: goto -1556 -> 36
      //   1595: iload 6
      //   1597: istore 7
      //   1599: iload 6
      //   1601: istore 8
      //   1603: iload 6
      //   1605: istore 9
      //   1607: aload_0
      //   1608: aload_0
      //   1609: getfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:bitField0_	I
      //   1612: iconst_2
      //   1613: ior
      //   1614: putfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:bitField0_	I
      //   1617: iload 6
      //   1619: istore 7
      //   1621: iload 6
      //   1623: istore 8
      //   1625: iload 6
      //   1627: istore 9
      //   1629: aload_0
      //   1630: aload_1
      //   1631: invokevirtual 137	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   1634: putfield 207	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:oldFlags_	I
      //   1637: goto -1601 -> 36
      //   1640: iconst_1
      //   1641: istore 5
      //   1643: goto -1607 -> 36
      //   1646: iload 15
      //   1648: ifne -1612 -> 36
      //   1651: goto -11 -> 1640
      //   1654: astore_1
      //   1655: goto +45 -> 1700
      //   1658: astore_1
      //   1659: iload 8
      //   1661: istore 7
      //   1663: new 65	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1666: astore_2
      //   1667: iload 8
      //   1669: istore 7
      //   1671: aload_2
      //   1672: aload_1
      //   1673: invokevirtual 211	java/io/IOException:getMessage	()Ljava/lang/String;
      //   1676: invokespecial 214	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   1679: iload 8
      //   1681: istore 7
      //   1683: aload_2
      //   1684: aload_0
      //   1685: invokevirtual 218	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   1688: athrow
      //   1689: astore_1
      //   1690: iload 9
      //   1692: istore 7
      //   1694: aload_1
      //   1695: aload_0
      //   1696: invokevirtual 218	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   1699: athrow
      //   1700: iload 7
      //   1702: bipush 32
      //   1704: iand
      //   1705: bipush 32
      //   1707: if_icmpne +14 -> 1721
      //   1710: aload_0
      //   1711: aload_0
      //   1712: getfield 198	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:typeParameter_	Ljava/util/List;
      //   1715: invokestatic 224	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   1718: putfield 198	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:typeParameter_	Ljava/util/List;
      //   1721: iload 7
      //   1723: sipush 256
      //   1726: iand
      //   1727: sipush 256
      //   1730: if_icmpne +14 -> 1744
      //   1733: aload_0
      //   1734: aload_0
      //   1735: getfield 177	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:valueParameter_	Ljava/util/List;
      //   1738: invokestatic 224	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   1741: putfield 177	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:valueParameter_	Ljava/util/List;
      //   1744: iload 7
      //   1746: sipush 1024
      //   1749: iand
      //   1750: sipush 1024
      //   1753: if_icmpne +14 -> 1767
      //   1756: aload_0
      //   1757: aload_0
      //   1758: getfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:versionRequirement_	Ljava/util/List;
      //   1761: invokestatic 224	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   1764: putfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:versionRequirement_	Ljava/util/List;
      //   1767: aload 4
      //   1769: invokevirtual 227	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   1772: aload_0
      //   1773: aload_3
      //   1774: invokevirtual 233	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1777: putfield 235	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1780: goto +14 -> 1794
      //   1783: astore_1
      //   1784: aload_0
      //   1785: aload_3
      //   1786: invokevirtual 233	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1789: putfield 235	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1792: aload_1
      //   1793: athrow
      //   1794: aload_0
      //   1795: invokevirtual 238	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:makeExtensionsImmutable	()V
      //   1798: aload_1
      //   1799: athrow
      //   1800: iload 6
      //   1802: bipush 32
      //   1804: iand
      //   1805: bipush 32
      //   1807: if_icmpne +14 -> 1821
      //   1810: aload_0
      //   1811: aload_0
      //   1812: getfield 198	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:typeParameter_	Ljava/util/List;
      //   1815: invokestatic 224	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   1818: putfield 198	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:typeParameter_	Ljava/util/List;
      //   1821: iload 6
      //   1823: sipush 256
      //   1826: iand
      //   1827: sipush 256
      //   1830: if_icmpne +14 -> 1844
      //   1833: aload_0
      //   1834: aload_0
      //   1835: getfield 177	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:valueParameter_	Ljava/util/List;
      //   1838: invokestatic 224	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   1841: putfield 177	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:valueParameter_	Ljava/util/List;
      //   1844: iload 6
      //   1846: sipush 1024
      //   1849: iand
      //   1850: sipush 1024
      //   1853: if_icmpne +14 -> 1867
      //   1856: aload_0
      //   1857: aload_0
      //   1858: getfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:versionRequirement_	Ljava/util/List;
      //   1861: invokestatic 224	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   1864: putfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:versionRequirement_	Ljava/util/List;
      //   1867: aload 4
      //   1869: invokevirtual 227	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   1872: aload_0
      //   1873: aload_3
      //   1874: invokevirtual 233	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1877: putfield 235	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1880: goto +14 -> 1894
      //   1883: astore_1
      //   1884: aload_0
      //   1885: aload_3
      //   1886: invokevirtual 233	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1889: putfield 235	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1892: aload_1
      //   1893: athrow
      //   1894: aload_0
      //   1895: invokevirtual 238	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:makeExtensionsImmutable	()V
      //   1898: return
      //   1899: astore_2
      //   1900: goto -128 -> 1772
      //   1903: astore_1
      //   1904: goto -32 -> 1872
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	1907	0	this	Function
      //   0	1907	1	paramCodedInputStream	CodedInputStream
      //   0	1907	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   22	1864	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   28	1840	4	localCodedOutputStream	CodedOutputStream
      //   31	1611	5	i	int
      //   34	1816	6	j	int
      //   43	1707	7	k	int
      //   47	1633	8	m	int
      //   51	1640	9	n	int
      //   57	1323	10	i1	int
      //   60	1438	11	localObject1	Object
      //   63	1050	12	localObject2	Object
      //   66	1321	13	localObject3	Object
      //   69	1448	14	localObject4	Object
      //   218	1429	15	bool	boolean
      //   406	166	16	i2	int
      // Exception table:
      //   from	to	target	type
      //   53	59	1654	finally
      //   208	220	1654	finally
      //   235	249	1654	finally
      //   261	270	1654	finally
      //   282	295	1654	finally
      //   307	313	1654	finally
      //   330	338	1654	finally
      //   350	359	1654	finally
      //   371	383	1654	finally
      //   398	408	1654	finally
      //   440	447	1654	finally
      //   459	464	1654	finally
      //   476	481	1654	finally
      //   493	499	1654	finally
      //   519	526	1654	finally
      //   538	555	1654	finally
      //   570	576	1654	finally
      //   611	616	1654	finally
      //   628	633	1654	finally
      //   645	651	1654	finally
      //   671	688	1654	finally
      //   711	725	1654	finally
      //   737	746	1654	finally
      //   758	771	1654	finally
      //   783	789	1654	finally
      //   806	814	1654	finally
      //   826	835	1654	finally
      //   847	859	1654	finally
      //   874	884	1654	finally
      //   896	904	1654	finally
      //   919	930	1654	finally
      //   942	950	1654	finally
      //   965	976	1654	finally
      //   988	996	1654	finally
      //   1027	1032	1654	finally
      //   1044	1049	1654	finally
      //   1061	1067	1654	finally
      //   1087	1105	1654	finally
      //   1128	1140	1654	finally
      //   1152	1161	1654	finally
      //   1173	1186	1654	finally
      //   1198	1204	1654	finally
      //   1221	1229	1654	finally
      //   1241	1250	1654	finally
      //   1262	1273	1654	finally
      //   1302	1307	1654	finally
      //   1319	1324	1654	finally
      //   1336	1342	1654	finally
      //   1361	1379	1654	finally
      //   1402	1414	1654	finally
      //   1426	1435	1654	finally
      //   1447	1460	1654	finally
      //   1472	1478	1654	finally
      //   1495	1503	1654	finally
      //   1515	1524	1654	finally
      //   1536	1547	1654	finally
      //   1562	1572	1654	finally
      //   1584	1592	1654	finally
      //   1607	1617	1654	finally
      //   1629	1637	1654	finally
      //   1663	1667	1654	finally
      //   1671	1679	1654	finally
      //   1683	1689	1654	finally
      //   1694	1700	1654	finally
      //   53	59	1658	java/io/IOException
      //   208	220	1658	java/io/IOException
      //   235	249	1658	java/io/IOException
      //   261	270	1658	java/io/IOException
      //   282	295	1658	java/io/IOException
      //   307	313	1658	java/io/IOException
      //   330	338	1658	java/io/IOException
      //   350	359	1658	java/io/IOException
      //   371	383	1658	java/io/IOException
      //   398	408	1658	java/io/IOException
      //   440	447	1658	java/io/IOException
      //   459	464	1658	java/io/IOException
      //   476	481	1658	java/io/IOException
      //   493	499	1658	java/io/IOException
      //   519	526	1658	java/io/IOException
      //   538	555	1658	java/io/IOException
      //   570	576	1658	java/io/IOException
      //   611	616	1658	java/io/IOException
      //   628	633	1658	java/io/IOException
      //   645	651	1658	java/io/IOException
      //   671	688	1658	java/io/IOException
      //   711	725	1658	java/io/IOException
      //   737	746	1658	java/io/IOException
      //   758	771	1658	java/io/IOException
      //   783	789	1658	java/io/IOException
      //   806	814	1658	java/io/IOException
      //   826	835	1658	java/io/IOException
      //   847	859	1658	java/io/IOException
      //   874	884	1658	java/io/IOException
      //   896	904	1658	java/io/IOException
      //   919	930	1658	java/io/IOException
      //   942	950	1658	java/io/IOException
      //   965	976	1658	java/io/IOException
      //   988	996	1658	java/io/IOException
      //   1027	1032	1658	java/io/IOException
      //   1044	1049	1658	java/io/IOException
      //   1061	1067	1658	java/io/IOException
      //   1087	1105	1658	java/io/IOException
      //   1128	1140	1658	java/io/IOException
      //   1152	1161	1658	java/io/IOException
      //   1173	1186	1658	java/io/IOException
      //   1198	1204	1658	java/io/IOException
      //   1221	1229	1658	java/io/IOException
      //   1241	1250	1658	java/io/IOException
      //   1262	1273	1658	java/io/IOException
      //   1302	1307	1658	java/io/IOException
      //   1319	1324	1658	java/io/IOException
      //   1336	1342	1658	java/io/IOException
      //   1361	1379	1658	java/io/IOException
      //   1402	1414	1658	java/io/IOException
      //   1426	1435	1658	java/io/IOException
      //   1447	1460	1658	java/io/IOException
      //   1472	1478	1658	java/io/IOException
      //   1495	1503	1658	java/io/IOException
      //   1515	1524	1658	java/io/IOException
      //   1536	1547	1658	java/io/IOException
      //   1562	1572	1658	java/io/IOException
      //   1584	1592	1658	java/io/IOException
      //   1607	1617	1658	java/io/IOException
      //   1629	1637	1658	java/io/IOException
      //   53	59	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   208	220	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   235	249	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   261	270	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   282	295	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   307	313	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   330	338	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   350	359	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   371	383	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   398	408	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   440	447	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   459	464	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   476	481	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   493	499	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   519	526	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   538	555	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   570	576	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   611	616	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   628	633	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   645	651	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   671	688	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   711	725	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   737	746	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   758	771	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   783	789	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   806	814	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   826	835	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   847	859	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   874	884	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   896	904	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   919	930	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   942	950	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   965	976	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   988	996	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1027	1032	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1044	1049	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1061	1067	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1087	1105	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1128	1140	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1152	1161	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1173	1186	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1198	1204	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1221	1229	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1241	1250	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1262	1273	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1302	1307	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1319	1324	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1336	1342	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1361	1379	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1402	1414	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1426	1435	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1447	1460	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1472	1478	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1495	1503	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1515	1524	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1536	1547	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1562	1572	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1584	1592	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1607	1617	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1629	1637	1689	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1767	1772	1783	finally
      //   1867	1872	1883	finally
      //   1767	1772	1899	java/io/IOException
      //   1867	1872	1903	java/io/IOException
    }
    
    private Function(GeneratedMessageLite.ExtendableBuilder<Function, ?> paramExtendableBuilder)
    {
      super();
      this.unknownFields = paramExtendableBuilder.getUnknownFields();
    }
    
    private Function(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static Function getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.flags_ = 6;
      this.oldFlags_ = 6;
      this.name_ = 0;
      this.returnType_ = ProtoBuf.Type.getDefaultInstance();
      this.returnTypeId_ = 0;
      this.typeParameter_ = Collections.emptyList();
      this.receiverType_ = ProtoBuf.Type.getDefaultInstance();
      this.receiverTypeId_ = 0;
      this.valueParameter_ = Collections.emptyList();
      this.typeTable_ = ProtoBuf.TypeTable.getDefaultInstance();
      this.versionRequirement_ = Collections.emptyList();
      this.contract_ = ProtoBuf.Contract.getDefaultInstance();
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$13300();
    }
    
    public static Builder newBuilder(Function paramFunction)
    {
      return newBuilder().mergeFrom(paramFunction);
    }
    
    public static Function parseFrom(InputStream paramInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws IOException
    {
      return (Function)PARSER.parseFrom(paramInputStream, paramExtensionRegistryLite);
    }
    
    public ProtoBuf.Contract getContract()
    {
      return this.contract_;
    }
    
    public Function getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public int getFlags()
    {
      return this.flags_;
    }
    
    public int getName()
    {
      return this.name_;
    }
    
    public int getOldFlags()
    {
      return this.oldFlags_;
    }
    
    public Parser<Function> getParserForType()
    {
      return PARSER;
    }
    
    public ProtoBuf.Type getReceiverType()
    {
      return this.receiverType_;
    }
    
    public int getReceiverTypeId()
    {
      return this.receiverTypeId_;
    }
    
    public ProtoBuf.Type getReturnType()
    {
      return this.returnType_;
    }
    
    public int getReturnTypeId()
    {
      return this.returnTypeId_;
    }
    
    public int getSerializedSize()
    {
      int i = this.memoizedSerializedSize;
      if (i != -1) {
        return i;
      }
      i = this.bitField0_;
      int j = 0;
      if ((i & 0x2) == 2) {
        k = CodedOutputStream.computeInt32Size(1, this.oldFlags_) + 0;
      } else {
        k = 0;
      }
      i = k;
      if ((this.bitField0_ & 0x4) == 4) {
        i = k + CodedOutputStream.computeInt32Size(2, this.name_);
      }
      int k = i;
      if ((this.bitField0_ & 0x8) == 8) {
        k = i + CodedOutputStream.computeMessageSize(3, this.returnType_);
      }
      for (i = 0; i < this.typeParameter_.size(); i++) {
        k += CodedOutputStream.computeMessageSize(4, (MessageLite)this.typeParameter_.get(i));
      }
      i = k;
      if ((this.bitField0_ & 0x20) == 32) {
        i = k + CodedOutputStream.computeMessageSize(5, this.receiverType_);
      }
      for (k = 0; k < this.valueParameter_.size(); k++) {
        i += CodedOutputStream.computeMessageSize(6, (MessageLite)this.valueParameter_.get(k));
      }
      k = i;
      if ((this.bitField0_ & 0x10) == 16) {
        k = i + CodedOutputStream.computeInt32Size(7, this.returnTypeId_);
      }
      int m = k;
      if ((this.bitField0_ & 0x40) == 64) {
        m = k + CodedOutputStream.computeInt32Size(8, this.receiverTypeId_);
      }
      i = m;
      if ((this.bitField0_ & 0x1) == 1) {
        i = m + CodedOutputStream.computeInt32Size(9, this.flags_);
      }
      k = i;
      if ((this.bitField0_ & 0x80) == 128) {
        k = i + CodedOutputStream.computeMessageSize(30, this.typeTable_);
      }
      i = 0;
      for (m = j; m < this.versionRequirement_.size(); m++) {
        i += CodedOutputStream.computeInt32SizeNoTag(((Integer)this.versionRequirement_.get(m)).intValue());
      }
      k = k + i + getVersionRequirementList().size() * 2;
      i = k;
      if ((this.bitField0_ & 0x100) == 256) {
        i = k + CodedOutputStream.computeMessageSize(32, this.contract_);
      }
      i = i + extensionsSerializedSize() + this.unknownFields.size();
      this.memoizedSerializedSize = i;
      return i;
    }
    
    public ProtoBuf.TypeParameter getTypeParameter(int paramInt)
    {
      return (ProtoBuf.TypeParameter)this.typeParameter_.get(paramInt);
    }
    
    public int getTypeParameterCount()
    {
      return this.typeParameter_.size();
    }
    
    public List<ProtoBuf.TypeParameter> getTypeParameterList()
    {
      return this.typeParameter_;
    }
    
    public ProtoBuf.TypeTable getTypeTable()
    {
      return this.typeTable_;
    }
    
    public ProtoBuf.ValueParameter getValueParameter(int paramInt)
    {
      return (ProtoBuf.ValueParameter)this.valueParameter_.get(paramInt);
    }
    
    public int getValueParameterCount()
    {
      return this.valueParameter_.size();
    }
    
    public List<ProtoBuf.ValueParameter> getValueParameterList()
    {
      return this.valueParameter_;
    }
    
    public List<Integer> getVersionRequirementList()
    {
      return this.versionRequirement_;
    }
    
    public boolean hasContract()
    {
      boolean bool;
      if ((this.bitField0_ & 0x100) == 256) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasFlags()
    {
      int i = this.bitField0_;
      boolean bool = true;
      if ((i & 0x1) != 1) {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasName()
    {
      boolean bool;
      if ((this.bitField0_ & 0x4) == 4) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasOldFlags()
    {
      boolean bool;
      if ((this.bitField0_ & 0x2) == 2) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasReceiverType()
    {
      boolean bool;
      if ((this.bitField0_ & 0x20) == 32) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasReceiverTypeId()
    {
      boolean bool;
      if ((this.bitField0_ & 0x40) == 64) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasReturnType()
    {
      boolean bool;
      if ((this.bitField0_ & 0x8) == 8) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasReturnTypeId()
    {
      boolean bool;
      if ((this.bitField0_ & 0x10) == 16) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasTypeTable()
    {
      boolean bool;
      if ((this.bitField0_ & 0x80) == 128) {
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
      if (!hasName())
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      if ((hasReturnType()) && (!getReturnType().isInitialized()))
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      for (i = 0; i < getTypeParameterCount(); i++) {
        if (!getTypeParameter(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
      }
      if ((hasReceiverType()) && (!getReceiverType().isInitialized()))
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      for (i = 0; i < getValueParameterCount(); i++) {
        if (!getValueParameter(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
      }
      if ((hasTypeTable()) && (!getTypeTable().isInitialized()))
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      if ((hasContract()) && (!getContract().isInitialized()))
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      if (!extensionsAreInitialized())
      {
        this.memoizedIsInitialized = ((byte)0);
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
      GeneratedMessageLite.ExtendableMessage.ExtensionWriter localExtensionWriter = newExtensionWriter();
      if ((this.bitField0_ & 0x2) == 2) {
        paramCodedOutputStream.writeInt32(1, this.oldFlags_);
      }
      if ((this.bitField0_ & 0x4) == 4) {
        paramCodedOutputStream.writeInt32(2, this.name_);
      }
      if ((this.bitField0_ & 0x8) == 8) {
        paramCodedOutputStream.writeMessage(3, this.returnType_);
      }
      int i = 0;
      for (int j = 0; j < this.typeParameter_.size(); j++) {
        paramCodedOutputStream.writeMessage(4, (MessageLite)this.typeParameter_.get(j));
      }
      if ((this.bitField0_ & 0x20) == 32) {
        paramCodedOutputStream.writeMessage(5, this.receiverType_);
      }
      for (j = 0; j < this.valueParameter_.size(); j++) {
        paramCodedOutputStream.writeMessage(6, (MessageLite)this.valueParameter_.get(j));
      }
      if ((this.bitField0_ & 0x10) == 16) {
        paramCodedOutputStream.writeInt32(7, this.returnTypeId_);
      }
      if ((this.bitField0_ & 0x40) == 64) {
        paramCodedOutputStream.writeInt32(8, this.receiverTypeId_);
      }
      if ((this.bitField0_ & 0x1) == 1) {
        paramCodedOutputStream.writeInt32(9, this.flags_);
      }
      j = i;
      if ((this.bitField0_ & 0x80) == 128) {
        paramCodedOutputStream.writeMessage(30, this.typeTable_);
      }
      for (j = i; j < this.versionRequirement_.size(); j++) {
        paramCodedOutputStream.writeInt32(31, ((Integer)this.versionRequirement_.get(j)).intValue());
      }
      if ((this.bitField0_ & 0x100) == 256) {
        paramCodedOutputStream.writeMessage(32, this.contract_);
      }
      localExtensionWriter.writeUntil(19000, paramCodedOutputStream);
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Builder
      extends GeneratedMessageLite.ExtendableBuilder<ProtoBuf.Function, Builder>
      implements ProtoBuf.FunctionOrBuilder
    {
      private int bitField0_;
      private ProtoBuf.Contract contract_ = ProtoBuf.Contract.getDefaultInstance();
      private int flags_ = 6;
      private int name_;
      private int oldFlags_ = 6;
      private int receiverTypeId_;
      private ProtoBuf.Type receiverType_ = ProtoBuf.Type.getDefaultInstance();
      private int returnTypeId_;
      private ProtoBuf.Type returnType_ = ProtoBuf.Type.getDefaultInstance();
      private List<ProtoBuf.TypeParameter> typeParameter_ = Collections.emptyList();
      private ProtoBuf.TypeTable typeTable_ = ProtoBuf.TypeTable.getDefaultInstance();
      private List<ProtoBuf.ValueParameter> valueParameter_ = Collections.emptyList();
      private List<Integer> versionRequirement_ = Collections.emptyList();
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void ensureTypeParameterIsMutable()
      {
        if ((this.bitField0_ & 0x20) != 32)
        {
          this.typeParameter_ = new ArrayList(this.typeParameter_);
          this.bitField0_ |= 0x20;
        }
      }
      
      private void ensureValueParameterIsMutable()
      {
        if ((this.bitField0_ & 0x100) != 256)
        {
          this.valueParameter_ = new ArrayList(this.valueParameter_);
          this.bitField0_ |= 0x100;
        }
      }
      
      private void ensureVersionRequirementIsMutable()
      {
        if ((this.bitField0_ & 0x400) != 1024)
        {
          this.versionRequirement_ = new ArrayList(this.versionRequirement_);
          this.bitField0_ |= 0x400;
        }
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public ProtoBuf.Function build()
      {
        ProtoBuf.Function localFunction = buildPartial();
        if (localFunction.isInitialized()) {
          return localFunction;
        }
        throw newUninitializedMessageException(localFunction);
      }
      
      public ProtoBuf.Function buildPartial()
      {
        ProtoBuf.Function localFunction = new ProtoBuf.Function(this, null);
        int i = this.bitField0_;
        int j = 1;
        if ((i & 0x1) != 1) {
          j = 0;
        }
        ProtoBuf.Function.access$13502(localFunction, this.flags_);
        int k = j;
        if ((i & 0x2) == 2) {
          k = j | 0x2;
        }
        ProtoBuf.Function.access$13602(localFunction, this.oldFlags_);
        j = k;
        if ((i & 0x4) == 4) {
          j = k | 0x4;
        }
        ProtoBuf.Function.access$13702(localFunction, this.name_);
        k = j;
        if ((i & 0x8) == 8) {
          k = j | 0x8;
        }
        ProtoBuf.Function.access$13802(localFunction, this.returnType_);
        j = k;
        if ((i & 0x10) == 16) {
          j = k | 0x10;
        }
        ProtoBuf.Function.access$13902(localFunction, this.returnTypeId_);
        if ((this.bitField0_ & 0x20) == 32)
        {
          this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
          this.bitField0_ &= 0xFFFFFFDF;
        }
        ProtoBuf.Function.access$14002(localFunction, this.typeParameter_);
        k = j;
        if ((i & 0x40) == 64) {
          k = j | 0x20;
        }
        ProtoBuf.Function.access$14102(localFunction, this.receiverType_);
        j = k;
        if ((i & 0x80) == 128) {
          j = k | 0x40;
        }
        ProtoBuf.Function.access$14202(localFunction, this.receiverTypeId_);
        if ((this.bitField0_ & 0x100) == 256)
        {
          this.valueParameter_ = Collections.unmodifiableList(this.valueParameter_);
          this.bitField0_ &= 0xFEFF;
        }
        ProtoBuf.Function.access$14302(localFunction, this.valueParameter_);
        k = j;
        if ((i & 0x200) == 512) {
          k = j | 0x80;
        }
        ProtoBuf.Function.access$14402(localFunction, this.typeTable_);
        if ((this.bitField0_ & 0x400) == 1024)
        {
          this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
          this.bitField0_ &= 0xFBFF;
        }
        ProtoBuf.Function.access$14502(localFunction, this.versionRequirement_);
        j = k;
        if ((i & 0x800) == 2048) {
          j = k | 0x100;
        }
        ProtoBuf.Function.access$14602(localFunction, this.contract_);
        ProtoBuf.Function.access$14702(localFunction, j);
        return localFunction;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public ProtoBuf.Contract getContract()
      {
        return this.contract_;
      }
      
      public ProtoBuf.Function getDefaultInstanceForType()
      {
        return ProtoBuf.Function.getDefaultInstance();
      }
      
      public ProtoBuf.Type getReceiverType()
      {
        return this.receiverType_;
      }
      
      public ProtoBuf.Type getReturnType()
      {
        return this.returnType_;
      }
      
      public ProtoBuf.TypeParameter getTypeParameter(int paramInt)
      {
        return (ProtoBuf.TypeParameter)this.typeParameter_.get(paramInt);
      }
      
      public int getTypeParameterCount()
      {
        return this.typeParameter_.size();
      }
      
      public ProtoBuf.TypeTable getTypeTable()
      {
        return this.typeTable_;
      }
      
      public ProtoBuf.ValueParameter getValueParameter(int paramInt)
      {
        return (ProtoBuf.ValueParameter)this.valueParameter_.get(paramInt);
      }
      
      public int getValueParameterCount()
      {
        return this.valueParameter_.size();
      }
      
      public boolean hasContract()
      {
        boolean bool;
        if ((this.bitField0_ & 0x800) == 2048) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasName()
      {
        boolean bool;
        if ((this.bitField0_ & 0x4) == 4) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasReceiverType()
      {
        boolean bool;
        if ((this.bitField0_ & 0x40) == 64) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasReturnType()
      {
        boolean bool;
        if ((this.bitField0_ & 0x8) == 8) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasTypeTable()
      {
        boolean bool;
        if ((this.bitField0_ & 0x200) == 512) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public final boolean isInitialized()
      {
        if (!hasName()) {
          return false;
        }
        if ((hasReturnType()) && (!getReturnType().isInitialized())) {
          return false;
        }
        for (int i = 0; i < getTypeParameterCount(); i++) {
          if (!getTypeParameter(i).isInitialized()) {
            return false;
          }
        }
        if ((hasReceiverType()) && (!getReceiverType().isInitialized())) {
          return false;
        }
        for (i = 0; i < getValueParameterCount(); i++) {
          if (!getValueParameter(i).isInitialized()) {
            return false;
          }
        }
        if ((hasTypeTable()) && (!getTypeTable().isInitialized())) {
          return false;
        }
        if ((hasContract()) && (!getContract().isInitialized())) {
          return false;
        }
        return extensionsAreInitialized();
      }
      
      public Builder mergeContract(ProtoBuf.Contract paramContract)
      {
        if (((this.bitField0_ & 0x800) == 2048) && (this.contract_ != ProtoBuf.Contract.getDefaultInstance())) {
          this.contract_ = ProtoBuf.Contract.newBuilder(this.contract_).mergeFrom(paramContract).buildPartial();
        } else {
          this.contract_ = paramContract;
        }
        this.bitField0_ |= 0x800;
        return this;
      }
      
      public Builder mergeFrom(ProtoBuf.Function paramFunction)
      {
        if (paramFunction == ProtoBuf.Function.getDefaultInstance()) {
          return this;
        }
        if (paramFunction.hasFlags()) {
          setFlags(paramFunction.getFlags());
        }
        if (paramFunction.hasOldFlags()) {
          setOldFlags(paramFunction.getOldFlags());
        }
        if (paramFunction.hasName()) {
          setName(paramFunction.getName());
        }
        if (paramFunction.hasReturnType()) {
          mergeReturnType(paramFunction.getReturnType());
        }
        if (paramFunction.hasReturnTypeId()) {
          setReturnTypeId(paramFunction.getReturnTypeId());
        }
        if (!paramFunction.typeParameter_.isEmpty()) {
          if (this.typeParameter_.isEmpty())
          {
            this.typeParameter_ = paramFunction.typeParameter_;
            this.bitField0_ &= 0xFFFFFFDF;
          }
          else
          {
            ensureTypeParameterIsMutable();
            this.typeParameter_.addAll(paramFunction.typeParameter_);
          }
        }
        if (paramFunction.hasReceiverType()) {
          mergeReceiverType(paramFunction.getReceiverType());
        }
        if (paramFunction.hasReceiverTypeId()) {
          setReceiverTypeId(paramFunction.getReceiverTypeId());
        }
        if (!paramFunction.valueParameter_.isEmpty()) {
          if (this.valueParameter_.isEmpty())
          {
            this.valueParameter_ = paramFunction.valueParameter_;
            this.bitField0_ &= 0xFEFF;
          }
          else
          {
            ensureValueParameterIsMutable();
            this.valueParameter_.addAll(paramFunction.valueParameter_);
          }
        }
        if (paramFunction.hasTypeTable()) {
          mergeTypeTable(paramFunction.getTypeTable());
        }
        if (!paramFunction.versionRequirement_.isEmpty()) {
          if (this.versionRequirement_.isEmpty())
          {
            this.versionRequirement_ = paramFunction.versionRequirement_;
            this.bitField0_ &= 0xFBFF;
          }
          else
          {
            ensureVersionRequirementIsMutable();
            this.versionRequirement_.addAll(paramFunction.versionRequirement_);
          }
        }
        if (paramFunction.hasContract()) {
          mergeContract(paramFunction.getContract());
        }
        mergeExtensionFields(paramFunction);
        setUnknownFields(getUnknownFields().concat(paramFunction.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 385	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 391 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 182	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_1
        //   29: aload_3
        //   30: astore_2
        //   31: goto +15 -> 46
        //   34: astore_1
        //   35: aload_1
        //   36: invokevirtual 394	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   39: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function
        //   42: astore_2
        //   43: aload_1
        //   44: athrow
        //   45: astore_1
        //   46: aload_2
        //   47: ifnull +9 -> 56
        //   50: aload_0
        //   51: aload_2
        //   52: invokevirtual 182	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function$Builder;
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
      
      public Builder mergeReceiverType(ProtoBuf.Type paramType)
      {
        if (((this.bitField0_ & 0x40) == 64) && (this.receiverType_ != ProtoBuf.Type.getDefaultInstance())) {
          this.receiverType_ = ProtoBuf.Type.newBuilder(this.receiverType_).mergeFrom(paramType).buildPartial();
        } else {
          this.receiverType_ = paramType;
        }
        this.bitField0_ |= 0x40;
        return this;
      }
      
      public Builder mergeReturnType(ProtoBuf.Type paramType)
      {
        if (((this.bitField0_ & 0x8) == 8) && (this.returnType_ != ProtoBuf.Type.getDefaultInstance())) {
          this.returnType_ = ProtoBuf.Type.newBuilder(this.returnType_).mergeFrom(paramType).buildPartial();
        } else {
          this.returnType_ = paramType;
        }
        this.bitField0_ |= 0x8;
        return this;
      }
      
      public Builder mergeTypeTable(ProtoBuf.TypeTable paramTypeTable)
      {
        if (((this.bitField0_ & 0x200) == 512) && (this.typeTable_ != ProtoBuf.TypeTable.getDefaultInstance())) {
          this.typeTable_ = ProtoBuf.TypeTable.newBuilder(this.typeTable_).mergeFrom(paramTypeTable).buildPartial();
        } else {
          this.typeTable_ = paramTypeTable;
        }
        this.bitField0_ |= 0x200;
        return this;
      }
      
      public Builder setFlags(int paramInt)
      {
        this.bitField0_ |= 0x1;
        this.flags_ = paramInt;
        return this;
      }
      
      public Builder setName(int paramInt)
      {
        this.bitField0_ |= 0x4;
        this.name_ = paramInt;
        return this;
      }
      
      public Builder setOldFlags(int paramInt)
      {
        this.bitField0_ |= 0x2;
        this.oldFlags_ = paramInt;
        return this;
      }
      
      public Builder setReceiverTypeId(int paramInt)
      {
        this.bitField0_ |= 0x80;
        this.receiverTypeId_ = paramInt;
        return this;
      }
      
      public Builder setReturnTypeId(int paramInt)
      {
        this.bitField0_ |= 0x10;
        this.returnTypeId_ = paramInt;
        return this;
      }
    }
  }
  
  public static enum MemberKind
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<MemberKind> internalValueMap = new Internal.EnumLiteMap()
    {
      public ProtoBuf.MemberKind findValueByNumber(int paramAnonymousInt)
      {
        return ProtoBuf.MemberKind.valueOf(paramAnonymousInt);
      }
    };
    private final int value;
    
    static
    {
      DELEGATION = new MemberKind("DELEGATION", 2, 2, 2);
      MemberKind localMemberKind = new MemberKind("SYNTHESIZED", 3, 3, 3);
      SYNTHESIZED = localMemberKind;
      $VALUES = new MemberKind[] { DECLARATION, FAKE_OVERRIDE, DELEGATION, localMemberKind };
    }
    
    private MemberKind(int paramInt1, int paramInt2)
    {
      this.value = paramInt2;
    }
    
    public static MemberKind valueOf(int paramInt)
    {
      if (paramInt != 0)
      {
        if (paramInt != 1)
        {
          if (paramInt != 2)
          {
            if (paramInt != 3) {
              return null;
            }
            return SYNTHESIZED;
          }
          return DELEGATION;
        }
        return FAKE_OVERRIDE;
      }
      return DECLARATION;
    }
    
    public final int getNumber()
    {
      return this.value;
    }
  }
  
  public static enum Modality
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<Modality> internalValueMap = new Internal.EnumLiteMap()
    {
      public ProtoBuf.Modality findValueByNumber(int paramAnonymousInt)
      {
        return ProtoBuf.Modality.valueOf(paramAnonymousInt);
      }
    };
    private final int value;
    
    static
    {
      ABSTRACT = new Modality("ABSTRACT", 2, 2, 2);
      Modality localModality = new Modality("SEALED", 3, 3, 3);
      SEALED = localModality;
      $VALUES = new Modality[] { FINAL, OPEN, ABSTRACT, localModality };
    }
    
    private Modality(int paramInt1, int paramInt2)
    {
      this.value = paramInt2;
    }
    
    public static Modality valueOf(int paramInt)
    {
      if (paramInt != 0)
      {
        if (paramInt != 1)
        {
          if (paramInt != 2)
          {
            if (paramInt != 3) {
              return null;
            }
            return SEALED;
          }
          return ABSTRACT;
        }
        return OPEN;
      }
      return FINAL;
    }
    
    public final int getNumber()
    {
      return this.value;
    }
  }
  
  public static final class Package
    extends GeneratedMessageLite.ExtendableMessage<Package>
    implements ProtoBuf.PackageOrBuilder
  {
    public static Parser<Package> PARSER = new AbstractParser()
    {
      public ProtoBuf.Package parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new ProtoBuf.Package(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final Package defaultInstance;
    private int bitField0_;
    private List<ProtoBuf.Function> function_;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private List<ProtoBuf.Property> property_;
    private List<ProtoBuf.TypeAlias> typeAlias_;
    private ProtoBuf.TypeTable typeTable_;
    private final ByteString unknownFields;
    private ProtoBuf.VersionRequirementTable versionRequirementTable_;
    
    static
    {
      Package localPackage = new Package(true);
      defaultInstance = localPackage;
      localPackage.initFields();
    }
    
    /* Error */
    private Package(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 60	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite$ExtendableMessage:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: i2b
      //   7: putfield 62	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:memoizedIsInitialized	B
      //   10: aload_0
      //   11: iconst_m1
      //   12: putfield 64	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:memoizedSerializedSize	I
      //   15: aload_0
      //   16: invokespecial 53	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:initFields	()V
      //   19: invokestatic 70	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   22: astore_3
      //   23: aload_3
      //   24: iconst_1
      //   25: invokestatic 76	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   28: astore 4
      //   30: iconst_0
      //   31: istore 5
      //   33: iconst_0
      //   34: istore 6
      //   36: iload 5
      //   38: ifne +878 -> 916
      //   41: iload 6
      //   43: istore 7
      //   45: iload 6
      //   47: istore 8
      //   49: iload 6
      //   51: istore 9
      //   53: aload_1
      //   54: invokevirtual 82	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   57: istore 10
      //   59: iload 10
      //   61: ifeq +713 -> 774
      //   64: iload 10
      //   66: bipush 26
      //   68: if_icmpeq +599 -> 667
      //   71: iload 10
      //   73: bipush 34
      //   75: if_icmpeq +485 -> 560
      //   78: iload 10
      //   80: bipush 42
      //   82: if_icmpeq +371 -> 453
      //   85: aconst_null
      //   86: astore 11
      //   88: aconst_null
      //   89: astore 12
      //   91: iload 10
      //   93: sipush 242
      //   96: if_icmpeq +196 -> 292
      //   99: iload 10
      //   101: sipush 258
      //   104: if_icmpeq +31 -> 135
      //   107: iload 6
      //   109: istore 7
      //   111: iload 6
      //   113: istore 8
      //   115: iload 6
      //   117: istore 9
      //   119: aload_0
      //   120: aload_1
      //   121: aload 4
      //   123: aload_2
      //   124: iload 10
      //   126: invokevirtual 86	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   129: ifne -93 -> 36
      //   132: goto +642 -> 774
      //   135: iload 6
      //   137: istore 7
      //   139: iload 6
      //   141: istore 8
      //   143: iload 6
      //   145: istore 9
      //   147: aload_0
      //   148: getfield 88	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:bitField0_	I
      //   151: iconst_2
      //   152: iand
      //   153: iconst_2
      //   154: if_icmpne +24 -> 178
      //   157: iload 6
      //   159: istore 7
      //   161: iload 6
      //   163: istore 8
      //   165: iload 6
      //   167: istore 9
      //   169: aload_0
      //   170: getfield 90	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:versionRequirementTable_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable;
      //   173: invokevirtual 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable$Builder;
      //   176: astore 12
      //   178: iload 6
      //   180: istore 7
      //   182: iload 6
      //   184: istore 8
      //   186: iload 6
      //   188: istore 9
      //   190: aload_1
      //   191: getstatic 97	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   194: aload_2
      //   195: invokevirtual 101	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   198: checkcast 92	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable
      //   201: astore 11
      //   203: iload 6
      //   205: istore 7
      //   207: iload 6
      //   209: istore 8
      //   211: iload 6
      //   213: istore 9
      //   215: aload_0
      //   216: aload 11
      //   218: putfield 90	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:versionRequirementTable_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable;
      //   221: aload 12
      //   223: ifnull +44 -> 267
      //   226: iload 6
      //   228: istore 7
      //   230: iload 6
      //   232: istore 8
      //   234: iload 6
      //   236: istore 9
      //   238: aload 12
      //   240: aload 11
      //   242: invokevirtual 107	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable$Builder;
      //   245: pop
      //   246: iload 6
      //   248: istore 7
      //   250: iload 6
      //   252: istore 8
      //   254: iload 6
      //   256: istore 9
      //   258: aload_0
      //   259: aload 12
      //   261: invokevirtual 111	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable;
      //   264: putfield 90	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:versionRequirementTable_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable;
      //   267: iload 6
      //   269: istore 7
      //   271: iload 6
      //   273: istore 8
      //   275: iload 6
      //   277: istore 9
      //   279: aload_0
      //   280: aload_0
      //   281: getfield 88	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:bitField0_	I
      //   284: iconst_2
      //   285: ior
      //   286: putfield 88	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:bitField0_	I
      //   289: goto -253 -> 36
      //   292: aload 11
      //   294: astore 12
      //   296: iload 6
      //   298: istore 7
      //   300: iload 6
      //   302: istore 8
      //   304: iload 6
      //   306: istore 9
      //   308: aload_0
      //   309: getfield 88	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:bitField0_	I
      //   312: iconst_1
      //   313: iand
      //   314: iconst_1
      //   315: if_icmpne +24 -> 339
      //   318: iload 6
      //   320: istore 7
      //   322: iload 6
      //   324: istore 8
      //   326: iload 6
      //   328: istore 9
      //   330: aload_0
      //   331: getfield 113	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:typeTable_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable;
      //   334: invokevirtual 118	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable$Builder;
      //   337: astore 12
      //   339: iload 6
      //   341: istore 7
      //   343: iload 6
      //   345: istore 8
      //   347: iload 6
      //   349: istore 9
      //   351: aload_1
      //   352: getstatic 119	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   355: aload_2
      //   356: invokevirtual 101	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   359: checkcast 115	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable
      //   362: astore 11
      //   364: iload 6
      //   366: istore 7
      //   368: iload 6
      //   370: istore 8
      //   372: iload 6
      //   374: istore 9
      //   376: aload_0
      //   377: aload 11
      //   379: putfield 113	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:typeTable_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable;
      //   382: aload 12
      //   384: ifnull +44 -> 428
      //   387: iload 6
      //   389: istore 7
      //   391: iload 6
      //   393: istore 8
      //   395: iload 6
      //   397: istore 9
      //   399: aload 12
      //   401: aload 11
      //   403: invokevirtual 124	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable$Builder;
      //   406: pop
      //   407: iload 6
      //   409: istore 7
      //   411: iload 6
      //   413: istore 8
      //   415: iload 6
      //   417: istore 9
      //   419: aload_0
      //   420: aload 12
      //   422: invokevirtual 127	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable;
      //   425: putfield 113	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:typeTable_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable;
      //   428: iload 6
      //   430: istore 7
      //   432: iload 6
      //   434: istore 8
      //   436: iload 6
      //   438: istore 9
      //   440: aload_0
      //   441: aload_0
      //   442: getfield 88	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:bitField0_	I
      //   445: iconst_1
      //   446: ior
      //   447: putfield 88	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:bitField0_	I
      //   450: goto -414 -> 36
      //   453: iload 6
      //   455: istore 10
      //   457: iload 6
      //   459: iconst_4
      //   460: iand
      //   461: iconst_4
      //   462: if_icmpeq +61 -> 523
      //   465: iload 6
      //   467: istore 7
      //   469: iload 6
      //   471: istore 8
      //   473: iload 6
      //   475: istore 9
      //   477: new 129	java/util/ArrayList
      //   480: astore 12
      //   482: iload 6
      //   484: istore 7
      //   486: iload 6
      //   488: istore 8
      //   490: iload 6
      //   492: istore 9
      //   494: aload 12
      //   496: invokespecial 130	java/util/ArrayList:<init>	()V
      //   499: iload 6
      //   501: istore 7
      //   503: iload 6
      //   505: istore 8
      //   507: iload 6
      //   509: istore 9
      //   511: aload_0
      //   512: aload 12
      //   514: putfield 132	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:typeAlias_	Ljava/util/List;
      //   517: iload 6
      //   519: iconst_4
      //   520: ior
      //   521: istore 10
      //   523: iload 10
      //   525: istore 7
      //   527: iload 10
      //   529: istore 8
      //   531: iload 10
      //   533: istore 9
      //   535: aload_0
      //   536: getfield 132	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:typeAlias_	Ljava/util/List;
      //   539: aload_1
      //   540: getstatic 135	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   543: aload_2
      //   544: invokevirtual 101	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   547: invokeinterface 141 2 0
      //   552: pop
      //   553: iload 10
      //   555: istore 6
      //   557: goto -521 -> 36
      //   560: iload 6
      //   562: istore 10
      //   564: iload 6
      //   566: iconst_2
      //   567: iand
      //   568: iconst_2
      //   569: if_icmpeq +61 -> 630
      //   572: iload 6
      //   574: istore 7
      //   576: iload 6
      //   578: istore 8
      //   580: iload 6
      //   582: istore 9
      //   584: new 129	java/util/ArrayList
      //   587: astore 12
      //   589: iload 6
      //   591: istore 7
      //   593: iload 6
      //   595: istore 8
      //   597: iload 6
      //   599: istore 9
      //   601: aload 12
      //   603: invokespecial 130	java/util/ArrayList:<init>	()V
      //   606: iload 6
      //   608: istore 7
      //   610: iload 6
      //   612: istore 8
      //   614: iload 6
      //   616: istore 9
      //   618: aload_0
      //   619: aload 12
      //   621: putfield 143	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:property_	Ljava/util/List;
      //   624: iload 6
      //   626: iconst_2
      //   627: ior
      //   628: istore 10
      //   630: iload 10
      //   632: istore 7
      //   634: iload 10
      //   636: istore 8
      //   638: iload 10
      //   640: istore 9
      //   642: aload_0
      //   643: getfield 143	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:property_	Ljava/util/List;
      //   646: aload_1
      //   647: getstatic 146	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   650: aload_2
      //   651: invokevirtual 101	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   654: invokeinterface 141 2 0
      //   659: pop
      //   660: iload 10
      //   662: istore 6
      //   664: goto -628 -> 36
      //   667: iload 6
      //   669: istore 10
      //   671: iload 6
      //   673: iconst_1
      //   674: iand
      //   675: iconst_1
      //   676: if_icmpeq +61 -> 737
      //   679: iload 6
      //   681: istore 7
      //   683: iload 6
      //   685: istore 8
      //   687: iload 6
      //   689: istore 9
      //   691: new 129	java/util/ArrayList
      //   694: astore 12
      //   696: iload 6
      //   698: istore 7
      //   700: iload 6
      //   702: istore 8
      //   704: iload 6
      //   706: istore 9
      //   708: aload 12
      //   710: invokespecial 130	java/util/ArrayList:<init>	()V
      //   713: iload 6
      //   715: istore 7
      //   717: iload 6
      //   719: istore 8
      //   721: iload 6
      //   723: istore 9
      //   725: aload_0
      //   726: aload 12
      //   728: putfield 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:function_	Ljava/util/List;
      //   731: iload 6
      //   733: iconst_1
      //   734: ior
      //   735: istore 10
      //   737: iload 10
      //   739: istore 7
      //   741: iload 10
      //   743: istore 8
      //   745: iload 10
      //   747: istore 9
      //   749: aload_0
      //   750: getfield 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:function_	Ljava/util/List;
      //   753: aload_1
      //   754: getstatic 151	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Function:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   757: aload_2
      //   758: invokevirtual 101	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   761: invokeinterface 141 2 0
      //   766: pop
      //   767: iload 10
      //   769: istore 6
      //   771: goto -735 -> 36
      //   774: iconst_1
      //   775: istore 5
      //   777: goto -741 -> 36
      //   780: astore_1
      //   781: goto +45 -> 826
      //   784: astore_1
      //   785: iload 8
      //   787: istore 7
      //   789: new 57	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   792: astore_2
      //   793: iload 8
      //   795: istore 7
      //   797: aload_2
      //   798: aload_1
      //   799: invokevirtual 155	java/io/IOException:getMessage	()Ljava/lang/String;
      //   802: invokespecial 158	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   805: iload 8
      //   807: istore 7
      //   809: aload_2
      //   810: aload_0
      //   811: invokevirtual 162	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   814: athrow
      //   815: astore_1
      //   816: iload 9
      //   818: istore 7
      //   820: aload_1
      //   821: aload_0
      //   822: invokevirtual 162	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   825: athrow
      //   826: iload 7
      //   828: iconst_1
      //   829: iand
      //   830: iconst_1
      //   831: if_icmpne +14 -> 845
      //   834: aload_0
      //   835: aload_0
      //   836: getfield 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:function_	Ljava/util/List;
      //   839: invokestatic 168	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   842: putfield 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:function_	Ljava/util/List;
      //   845: iload 7
      //   847: iconst_2
      //   848: iand
      //   849: iconst_2
      //   850: if_icmpne +14 -> 864
      //   853: aload_0
      //   854: aload_0
      //   855: getfield 143	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:property_	Ljava/util/List;
      //   858: invokestatic 168	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   861: putfield 143	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:property_	Ljava/util/List;
      //   864: iload 7
      //   866: iconst_4
      //   867: iand
      //   868: iconst_4
      //   869: if_icmpne +14 -> 883
      //   872: aload_0
      //   873: aload_0
      //   874: getfield 132	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:typeAlias_	Ljava/util/List;
      //   877: invokestatic 168	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   880: putfield 132	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:typeAlias_	Ljava/util/List;
      //   883: aload 4
      //   885: invokevirtual 171	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   888: aload_0
      //   889: aload_3
      //   890: invokevirtual 177	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   893: putfield 179	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   896: goto +14 -> 910
      //   899: astore_1
      //   900: aload_0
      //   901: aload_3
      //   902: invokevirtual 177	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   905: putfield 179	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   908: aload_1
      //   909: athrow
      //   910: aload_0
      //   911: invokevirtual 182	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:makeExtensionsImmutable	()V
      //   914: aload_1
      //   915: athrow
      //   916: iload 6
      //   918: iconst_1
      //   919: iand
      //   920: iconst_1
      //   921: if_icmpne +14 -> 935
      //   924: aload_0
      //   925: aload_0
      //   926: getfield 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:function_	Ljava/util/List;
      //   929: invokestatic 168	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   932: putfield 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:function_	Ljava/util/List;
      //   935: iload 6
      //   937: iconst_2
      //   938: iand
      //   939: iconst_2
      //   940: if_icmpne +14 -> 954
      //   943: aload_0
      //   944: aload_0
      //   945: getfield 143	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:property_	Ljava/util/List;
      //   948: invokestatic 168	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   951: putfield 143	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:property_	Ljava/util/List;
      //   954: iload 6
      //   956: iconst_4
      //   957: iand
      //   958: iconst_4
      //   959: if_icmpne +14 -> 973
      //   962: aload_0
      //   963: aload_0
      //   964: getfield 132	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:typeAlias_	Ljava/util/List;
      //   967: invokestatic 168	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   970: putfield 132	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:typeAlias_	Ljava/util/List;
      //   973: aload 4
      //   975: invokevirtual 171	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   978: aload_0
      //   979: aload_3
      //   980: invokevirtual 177	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   983: putfield 179	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   986: goto +14 -> 1000
      //   989: astore_1
      //   990: aload_0
      //   991: aload_3
      //   992: invokevirtual 177	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   995: putfield 179	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   998: aload_1
      //   999: athrow
      //   1000: aload_0
      //   1001: invokevirtual 182	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:makeExtensionsImmutable	()V
      //   1004: return
      //   1005: astore_2
      //   1006: goto -118 -> 888
      //   1009: astore_1
      //   1010: goto -32 -> 978
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	1013	0	this	Package
      //   0	1013	1	paramCodedInputStream	CodedInputStream
      //   0	1013	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   22	970	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   28	946	4	localCodedOutputStream	CodedOutputStream
      //   31	745	5	i	int
      //   34	924	6	j	int
      //   43	825	7	k	int
      //   47	759	8	m	int
      //   51	766	9	n	int
      //   57	711	10	i1	int
      //   86	316	11	localObject1	Object
      //   89	638	12	localObject2	Object
      // Exception table:
      //   from	to	target	type
      //   53	59	780	finally
      //   119	132	780	finally
      //   147	157	780	finally
      //   169	178	780	finally
      //   190	203	780	finally
      //   215	221	780	finally
      //   238	246	780	finally
      //   258	267	780	finally
      //   279	289	780	finally
      //   308	318	780	finally
      //   330	339	780	finally
      //   351	364	780	finally
      //   376	382	780	finally
      //   399	407	780	finally
      //   419	428	780	finally
      //   440	450	780	finally
      //   477	482	780	finally
      //   494	499	780	finally
      //   511	517	780	finally
      //   535	553	780	finally
      //   584	589	780	finally
      //   601	606	780	finally
      //   618	624	780	finally
      //   642	660	780	finally
      //   691	696	780	finally
      //   708	713	780	finally
      //   725	731	780	finally
      //   749	767	780	finally
      //   789	793	780	finally
      //   797	805	780	finally
      //   809	815	780	finally
      //   820	826	780	finally
      //   53	59	784	java/io/IOException
      //   119	132	784	java/io/IOException
      //   147	157	784	java/io/IOException
      //   169	178	784	java/io/IOException
      //   190	203	784	java/io/IOException
      //   215	221	784	java/io/IOException
      //   238	246	784	java/io/IOException
      //   258	267	784	java/io/IOException
      //   279	289	784	java/io/IOException
      //   308	318	784	java/io/IOException
      //   330	339	784	java/io/IOException
      //   351	364	784	java/io/IOException
      //   376	382	784	java/io/IOException
      //   399	407	784	java/io/IOException
      //   419	428	784	java/io/IOException
      //   440	450	784	java/io/IOException
      //   477	482	784	java/io/IOException
      //   494	499	784	java/io/IOException
      //   511	517	784	java/io/IOException
      //   535	553	784	java/io/IOException
      //   584	589	784	java/io/IOException
      //   601	606	784	java/io/IOException
      //   618	624	784	java/io/IOException
      //   642	660	784	java/io/IOException
      //   691	696	784	java/io/IOException
      //   708	713	784	java/io/IOException
      //   725	731	784	java/io/IOException
      //   749	767	784	java/io/IOException
      //   53	59	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   119	132	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   147	157	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   169	178	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   190	203	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   215	221	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   238	246	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   258	267	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   279	289	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   308	318	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   330	339	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   351	364	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   376	382	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   399	407	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   419	428	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   440	450	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   477	482	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   494	499	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   511	517	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   535	553	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   584	589	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   601	606	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   618	624	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   642	660	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   691	696	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   708	713	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   725	731	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   749	767	815	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   883	888	899	finally
      //   973	978	989	finally
      //   883	888	1005	java/io/IOException
      //   973	978	1009	java/io/IOException
    }
    
    private Package(GeneratedMessageLite.ExtendableBuilder<Package, ?> paramExtendableBuilder)
    {
      super();
      this.unknownFields = paramExtendableBuilder.getUnknownFields();
    }
    
    private Package(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static Package getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.function_ = Collections.emptyList();
      this.property_ = Collections.emptyList();
      this.typeAlias_ = Collections.emptyList();
      this.typeTable_ = ProtoBuf.TypeTable.getDefaultInstance();
      this.versionRequirementTable_ = ProtoBuf.VersionRequirementTable.getDefaultInstance();
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$10800();
    }
    
    public static Builder newBuilder(Package paramPackage)
    {
      return newBuilder().mergeFrom(paramPackage);
    }
    
    public static Package parseFrom(InputStream paramInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws IOException
    {
      return (Package)PARSER.parseFrom(paramInputStream, paramExtensionRegistryLite);
    }
    
    public Package getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public ProtoBuf.Function getFunction(int paramInt)
    {
      return (ProtoBuf.Function)this.function_.get(paramInt);
    }
    
    public int getFunctionCount()
    {
      return this.function_.size();
    }
    
    public List<ProtoBuf.Function> getFunctionList()
    {
      return this.function_;
    }
    
    public Parser<Package> getParserForType()
    {
      return PARSER;
    }
    
    public ProtoBuf.Property getProperty(int paramInt)
    {
      return (ProtoBuf.Property)this.property_.get(paramInt);
    }
    
    public int getPropertyCount()
    {
      return this.property_.size();
    }
    
    public List<ProtoBuf.Property> getPropertyList()
    {
      return this.property_;
    }
    
    public int getSerializedSize()
    {
      int i = this.memoizedSerializedSize;
      if (i != -1) {
        return i;
      }
      int j = 0;
      i = 0;
      int k = i;
      while (i < this.function_.size())
      {
        k += CodedOutputStream.computeMessageSize(3, (MessageLite)this.function_.get(i));
        i++;
      }
      int n;
      for (int m = 0;; m++)
      {
        n = j;
        i = k;
        if (m >= this.property_.size()) {
          break;
        }
        k += CodedOutputStream.computeMessageSize(4, (MessageLite)this.property_.get(m));
      }
      while (n < this.typeAlias_.size())
      {
        i += CodedOutputStream.computeMessageSize(5, (MessageLite)this.typeAlias_.get(n));
        n++;
      }
      k = i;
      if ((this.bitField0_ & 0x1) == 1) {
        k = i + CodedOutputStream.computeMessageSize(30, this.typeTable_);
      }
      i = k;
      if ((this.bitField0_ & 0x2) == 2) {
        i = k + CodedOutputStream.computeMessageSize(32, this.versionRequirementTable_);
      }
      i = i + extensionsSerializedSize() + this.unknownFields.size();
      this.memoizedSerializedSize = i;
      return i;
    }
    
    public ProtoBuf.TypeAlias getTypeAlias(int paramInt)
    {
      return (ProtoBuf.TypeAlias)this.typeAlias_.get(paramInt);
    }
    
    public int getTypeAliasCount()
    {
      return this.typeAlias_.size();
    }
    
    public List<ProtoBuf.TypeAlias> getTypeAliasList()
    {
      return this.typeAlias_;
    }
    
    public ProtoBuf.TypeTable getTypeTable()
    {
      return this.typeTable_;
    }
    
    public ProtoBuf.VersionRequirementTable getVersionRequirementTable()
    {
      return this.versionRequirementTable_;
    }
    
    public boolean hasTypeTable()
    {
      int i = this.bitField0_;
      boolean bool = true;
      if ((i & 0x1) != 1) {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasVersionRequirementTable()
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
      for (i = 0; i < getFunctionCount(); i++) {
        if (!getFunction(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
      }
      for (i = 0; i < getPropertyCount(); i++) {
        if (!getProperty(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
      }
      for (i = 0; i < getTypeAliasCount(); i++) {
        if (!getTypeAlias(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
      }
      if ((hasTypeTable()) && (!getTypeTable().isInitialized()))
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      if (!extensionsAreInitialized())
      {
        this.memoizedIsInitialized = ((byte)0);
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
      GeneratedMessageLite.ExtendableMessage.ExtensionWriter localExtensionWriter = newExtensionWriter();
      int i = 0;
      for (int j = 0; j < this.function_.size(); j++) {
        paramCodedOutputStream.writeMessage(3, (MessageLite)this.function_.get(j));
      }
      int k;
      for (j = 0;; j++)
      {
        k = i;
        if (j >= this.property_.size()) {
          break;
        }
        paramCodedOutputStream.writeMessage(4, (MessageLite)this.property_.get(j));
      }
      while (k < this.typeAlias_.size())
      {
        paramCodedOutputStream.writeMessage(5, (MessageLite)this.typeAlias_.get(k));
        k++;
      }
      if ((this.bitField0_ & 0x1) == 1) {
        paramCodedOutputStream.writeMessage(30, this.typeTable_);
      }
      if ((this.bitField0_ & 0x2) == 2) {
        paramCodedOutputStream.writeMessage(32, this.versionRequirementTable_);
      }
      localExtensionWriter.writeUntil(200, paramCodedOutputStream);
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Builder
      extends GeneratedMessageLite.ExtendableBuilder<ProtoBuf.Package, Builder>
      implements ProtoBuf.PackageOrBuilder
    {
      private int bitField0_;
      private List<ProtoBuf.Function> function_ = Collections.emptyList();
      private List<ProtoBuf.Property> property_ = Collections.emptyList();
      private List<ProtoBuf.TypeAlias> typeAlias_ = Collections.emptyList();
      private ProtoBuf.TypeTable typeTable_ = ProtoBuf.TypeTable.getDefaultInstance();
      private ProtoBuf.VersionRequirementTable versionRequirementTable_ = ProtoBuf.VersionRequirementTable.getDefaultInstance();
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void ensureFunctionIsMutable()
      {
        if ((this.bitField0_ & 0x1) != 1)
        {
          this.function_ = new ArrayList(this.function_);
          this.bitField0_ |= 0x1;
        }
      }
      
      private void ensurePropertyIsMutable()
      {
        if ((this.bitField0_ & 0x2) != 2)
        {
          this.property_ = new ArrayList(this.property_);
          this.bitField0_ |= 0x2;
        }
      }
      
      private void ensureTypeAliasIsMutable()
      {
        if ((this.bitField0_ & 0x4) != 4)
        {
          this.typeAlias_ = new ArrayList(this.typeAlias_);
          this.bitField0_ |= 0x4;
        }
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public ProtoBuf.Package build()
      {
        ProtoBuf.Package localPackage = buildPartial();
        if (localPackage.isInitialized()) {
          return localPackage;
        }
        throw newUninitializedMessageException(localPackage);
      }
      
      public ProtoBuf.Package buildPartial()
      {
        ProtoBuf.Package localPackage = new ProtoBuf.Package(this, null);
        int i = this.bitField0_;
        int j = 1;
        if ((i & 0x1) == 1)
        {
          this.function_ = Collections.unmodifiableList(this.function_);
          this.bitField0_ &= 0xFFFFFFFE;
        }
        ProtoBuf.Package.access$11002(localPackage, this.function_);
        if ((this.bitField0_ & 0x2) == 2)
        {
          this.property_ = Collections.unmodifiableList(this.property_);
          this.bitField0_ &= 0xFFFFFFFD;
        }
        ProtoBuf.Package.access$11102(localPackage, this.property_);
        if ((this.bitField0_ & 0x4) == 4)
        {
          this.typeAlias_ = Collections.unmodifiableList(this.typeAlias_);
          this.bitField0_ &= 0xFFFFFFFB;
        }
        ProtoBuf.Package.access$11202(localPackage, this.typeAlias_);
        if ((i & 0x8) != 8) {
          j = 0;
        }
        ProtoBuf.Package.access$11302(localPackage, this.typeTable_);
        int k = j;
        if ((i & 0x10) == 16) {
          k = j | 0x2;
        }
        ProtoBuf.Package.access$11402(localPackage, this.versionRequirementTable_);
        ProtoBuf.Package.access$11502(localPackage, k);
        return localPackage;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public ProtoBuf.Package getDefaultInstanceForType()
      {
        return ProtoBuf.Package.getDefaultInstance();
      }
      
      public ProtoBuf.Function getFunction(int paramInt)
      {
        return (ProtoBuf.Function)this.function_.get(paramInt);
      }
      
      public int getFunctionCount()
      {
        return this.function_.size();
      }
      
      public ProtoBuf.Property getProperty(int paramInt)
      {
        return (ProtoBuf.Property)this.property_.get(paramInt);
      }
      
      public int getPropertyCount()
      {
        return this.property_.size();
      }
      
      public ProtoBuf.TypeAlias getTypeAlias(int paramInt)
      {
        return (ProtoBuf.TypeAlias)this.typeAlias_.get(paramInt);
      }
      
      public int getTypeAliasCount()
      {
        return this.typeAlias_.size();
      }
      
      public ProtoBuf.TypeTable getTypeTable()
      {
        return this.typeTable_;
      }
      
      public boolean hasTypeTable()
      {
        boolean bool;
        if ((this.bitField0_ & 0x8) == 8) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public final boolean isInitialized()
      {
        for (int i = 0; i < getFunctionCount(); i++) {
          if (!getFunction(i).isInitialized()) {
            return false;
          }
        }
        for (i = 0; i < getPropertyCount(); i++) {
          if (!getProperty(i).isInitialized()) {
            return false;
          }
        }
        for (i = 0; i < getTypeAliasCount(); i++) {
          if (!getTypeAlias(i).isInitialized()) {
            return false;
          }
        }
        if ((hasTypeTable()) && (!getTypeTable().isInitialized())) {
          return false;
        }
        return extensionsAreInitialized();
      }
      
      public Builder mergeFrom(ProtoBuf.Package paramPackage)
      {
        if (paramPackage == ProtoBuf.Package.getDefaultInstance()) {
          return this;
        }
        if (!paramPackage.function_.isEmpty()) {
          if (this.function_.isEmpty())
          {
            this.function_ = paramPackage.function_;
            this.bitField0_ &= 0xFFFFFFFE;
          }
          else
          {
            ensureFunctionIsMutable();
            this.function_.addAll(paramPackage.function_);
          }
        }
        if (!paramPackage.property_.isEmpty()) {
          if (this.property_.isEmpty())
          {
            this.property_ = paramPackage.property_;
            this.bitField0_ &= 0xFFFFFFFD;
          }
          else
          {
            ensurePropertyIsMutable();
            this.property_.addAll(paramPackage.property_);
          }
        }
        if (!paramPackage.typeAlias_.isEmpty()) {
          if (this.typeAlias_.isEmpty())
          {
            this.typeAlias_ = paramPackage.typeAlias_;
            this.bitField0_ &= 0xFFFFFFFB;
          }
          else
          {
            ensureTypeAliasIsMutable();
            this.typeAlias_.addAll(paramPackage.typeAlias_);
          }
        }
        if (paramPackage.hasTypeTable()) {
          mergeTypeTable(paramPackage.getTypeTable());
        }
        if (paramPackage.hasVersionRequirementTable()) {
          mergeVersionRequirementTable(paramPackage.getVersionRequirementTable());
        }
        mergeExtensionFields(paramPackage);
        setUnknownFields(getUnknownFields().concat(paramPackage.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 262	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 268 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 133	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_2
        //   29: aload_3
        //   30: astore_1
        //   31: goto +15 -> 46
        //   34: astore_2
        //   35: aload_2
        //   36: invokevirtual 271	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   39: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package
        //   42: astore_1
        //   43: aload_2
        //   44: athrow
        //   45: astore_2
        //   46: aload_1
        //   47: ifnull +9 -> 56
        //   50: aload_0
        //   51: aload_1
        //   52: invokevirtual 133	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package$Builder;
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
      
      public Builder mergeTypeTable(ProtoBuf.TypeTable paramTypeTable)
      {
        if (((this.bitField0_ & 0x8) == 8) && (this.typeTable_ != ProtoBuf.TypeTable.getDefaultInstance())) {
          this.typeTable_ = ProtoBuf.TypeTable.newBuilder(this.typeTable_).mergeFrom(paramTypeTable).buildPartial();
        } else {
          this.typeTable_ = paramTypeTable;
        }
        this.bitField0_ |= 0x8;
        return this;
      }
      
      public Builder mergeVersionRequirementTable(ProtoBuf.VersionRequirementTable paramVersionRequirementTable)
      {
        if (((this.bitField0_ & 0x10) == 16) && (this.versionRequirementTable_ != ProtoBuf.VersionRequirementTable.getDefaultInstance())) {
          this.versionRequirementTable_ = ProtoBuf.VersionRequirementTable.newBuilder(this.versionRequirementTable_).mergeFrom(paramVersionRequirementTable).buildPartial();
        } else {
          this.versionRequirementTable_ = paramVersionRequirementTable;
        }
        this.bitField0_ |= 0x10;
        return this;
      }
    }
  }
  
  public static final class PackageFragment
    extends GeneratedMessageLite.ExtendableMessage<PackageFragment>
    implements ProtoBuf.PackageFragmentOrBuilder
  {
    public static Parser<PackageFragment> PARSER = new AbstractParser()
    {
      public ProtoBuf.PackageFragment parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new ProtoBuf.PackageFragment(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final PackageFragment defaultInstance;
    private int bitField0_;
    private List<ProtoBuf.Class> class__;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private ProtoBuf.Package package_;
    private ProtoBuf.QualifiedNameTable qualifiedNames_;
    private ProtoBuf.StringTable strings_;
    private final ByteString unknownFields;
    
    static
    {
      PackageFragment localPackageFragment = new PackageFragment(true);
      defaultInstance = localPackageFragment;
      localPackageFragment.initFields();
    }
    
    /* Error */
    private PackageFragment(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 58	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite$ExtendableMessage:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: i2b
      //   7: putfield 60	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:memoizedIsInitialized	B
      //   10: aload_0
      //   11: iconst_m1
      //   12: putfield 62	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:memoizedSerializedSize	I
      //   15: aload_0
      //   16: invokespecial 51	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:initFields	()V
      //   19: invokestatic 68	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   22: astore_3
      //   23: aload_3
      //   24: iconst_1
      //   25: invokestatic 74	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   28: astore 4
      //   30: iconst_0
      //   31: istore 5
      //   33: iconst_0
      //   34: istore 6
      //   36: iload 5
      //   38: ifne +786 -> 824
      //   41: iload 6
      //   43: istore 7
      //   45: iload 6
      //   47: istore 8
      //   49: iload 6
      //   51: istore 9
      //   53: aload_1
      //   54: invokevirtual 80	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   57: istore 10
      //   59: iload 10
      //   61: ifeq +657 -> 718
      //   64: aconst_null
      //   65: astore 11
      //   67: aconst_null
      //   68: astore 12
      //   70: aconst_null
      //   71: astore 13
      //   73: iload 10
      //   75: bipush 10
      //   77: if_icmpeq +480 -> 557
      //   80: iload 10
      //   82: bipush 18
      //   84: if_icmpeq +312 -> 396
      //   87: iload 10
      //   89: bipush 26
      //   91: if_icmpeq +148 -> 239
      //   94: iload 10
      //   96: bipush 34
      //   98: if_icmpeq +31 -> 129
      //   101: iload 6
      //   103: istore 7
      //   105: iload 6
      //   107: istore 8
      //   109: iload 6
      //   111: istore 9
      //   113: aload_0
      //   114: aload_1
      //   115: aload 4
      //   117: aload_2
      //   118: iload 10
      //   120: invokevirtual 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   123: ifne -87 -> 36
      //   126: goto +592 -> 718
      //   129: iload 6
      //   131: istore 10
      //   133: iload 6
      //   135: bipush 8
      //   137: iand
      //   138: bipush 8
      //   140: if_icmpeq +62 -> 202
      //   143: iload 6
      //   145: istore 7
      //   147: iload 6
      //   149: istore 8
      //   151: iload 6
      //   153: istore 9
      //   155: new 86	java/util/ArrayList
      //   158: astore 13
      //   160: iload 6
      //   162: istore 7
      //   164: iload 6
      //   166: istore 8
      //   168: iload 6
      //   170: istore 9
      //   172: aload 13
      //   174: invokespecial 87	java/util/ArrayList:<init>	()V
      //   177: iload 6
      //   179: istore 7
      //   181: iload 6
      //   183: istore 8
      //   185: iload 6
      //   187: istore 9
      //   189: aload_0
      //   190: aload 13
      //   192: putfield 89	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:class__	Ljava/util/List;
      //   195: iload 6
      //   197: bipush 8
      //   199: ior
      //   200: istore 10
      //   202: iload 10
      //   204: istore 7
      //   206: iload 10
      //   208: istore 8
      //   210: iload 10
      //   212: istore 9
      //   214: aload_0
      //   215: getfield 89	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:class__	Ljava/util/List;
      //   218: aload_1
      //   219: getstatic 92	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Class:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   222: aload_2
      //   223: invokevirtual 96	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   226: invokeinterface 102 2 0
      //   231: pop
      //   232: iload 10
      //   234: istore 6
      //   236: goto -200 -> 36
      //   239: iload 6
      //   241: istore 7
      //   243: iload 6
      //   245: istore 8
      //   247: iload 6
      //   249: istore 9
      //   251: aload_0
      //   252: getfield 104	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:bitField0_	I
      //   255: iconst_4
      //   256: iand
      //   257: iconst_4
      //   258: if_icmpne +24 -> 282
      //   261: iload 6
      //   263: istore 7
      //   265: iload 6
      //   267: istore 8
      //   269: iload 6
      //   271: istore 9
      //   273: aload_0
      //   274: getfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:package_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package;
      //   277: invokevirtual 112	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package$Builder;
      //   280: astore 13
      //   282: iload 6
      //   284: istore 7
      //   286: iload 6
      //   288: istore 8
      //   290: iload 6
      //   292: istore 9
      //   294: aload_1
      //   295: getstatic 113	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   298: aload_2
      //   299: invokevirtual 96	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   302: checkcast 108	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package
      //   305: astore 11
      //   307: iload 6
      //   309: istore 7
      //   311: iload 6
      //   313: istore 8
      //   315: iload 6
      //   317: istore 9
      //   319: aload_0
      //   320: aload 11
      //   322: putfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:package_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package;
      //   325: aload 13
      //   327: ifnull +44 -> 371
      //   330: iload 6
      //   332: istore 7
      //   334: iload 6
      //   336: istore 8
      //   338: iload 6
      //   340: istore 9
      //   342: aload 13
      //   344: aload 11
      //   346: invokevirtual 119	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package$Builder;
      //   349: pop
      //   350: iload 6
      //   352: istore 7
      //   354: iload 6
      //   356: istore 8
      //   358: iload 6
      //   360: istore 9
      //   362: aload_0
      //   363: aload 13
      //   365: invokevirtual 123	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package;
      //   368: putfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:package_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Package;
      //   371: iload 6
      //   373: istore 7
      //   375: iload 6
      //   377: istore 8
      //   379: iload 6
      //   381: istore 9
      //   383: aload_0
      //   384: aload_0
      //   385: getfield 104	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:bitField0_	I
      //   388: iconst_4
      //   389: ior
      //   390: putfield 104	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:bitField0_	I
      //   393: goto -357 -> 36
      //   396: aload 11
      //   398: astore 13
      //   400: iload 6
      //   402: istore 7
      //   404: iload 6
      //   406: istore 8
      //   408: iload 6
      //   410: istore 9
      //   412: aload_0
      //   413: getfield 104	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:bitField0_	I
      //   416: iconst_2
      //   417: iand
      //   418: iconst_2
      //   419: if_icmpne +24 -> 443
      //   422: iload 6
      //   424: istore 7
      //   426: iload 6
      //   428: istore 8
      //   430: iload 6
      //   432: istore 9
      //   434: aload_0
      //   435: getfield 125	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:qualifiedNames_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable;
      //   438: invokevirtual 130	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$Builder;
      //   441: astore 13
      //   443: iload 6
      //   445: istore 7
      //   447: iload 6
      //   449: istore 8
      //   451: iload 6
      //   453: istore 9
      //   455: aload_1
      //   456: getstatic 131	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   459: aload_2
      //   460: invokevirtual 96	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   463: checkcast 127	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable
      //   466: astore 11
      //   468: iload 6
      //   470: istore 7
      //   472: iload 6
      //   474: istore 8
      //   476: iload 6
      //   478: istore 9
      //   480: aload_0
      //   481: aload 11
      //   483: putfield 125	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:qualifiedNames_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable;
      //   486: aload 13
      //   488: ifnull +44 -> 532
      //   491: iload 6
      //   493: istore 7
      //   495: iload 6
      //   497: istore 8
      //   499: iload 6
      //   501: istore 9
      //   503: aload 13
      //   505: aload 11
      //   507: invokevirtual 136	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$Builder;
      //   510: pop
      //   511: iload 6
      //   513: istore 7
      //   515: iload 6
      //   517: istore 8
      //   519: iload 6
      //   521: istore 9
      //   523: aload_0
      //   524: aload 13
      //   526: invokevirtual 139	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable;
      //   529: putfield 125	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:qualifiedNames_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable;
      //   532: iload 6
      //   534: istore 7
      //   536: iload 6
      //   538: istore 8
      //   540: iload 6
      //   542: istore 9
      //   544: aload_0
      //   545: aload_0
      //   546: getfield 104	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:bitField0_	I
      //   549: iconst_2
      //   550: ior
      //   551: putfield 104	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:bitField0_	I
      //   554: goto -518 -> 36
      //   557: aload 12
      //   559: astore 13
      //   561: iload 6
      //   563: istore 7
      //   565: iload 6
      //   567: istore 8
      //   569: iload 6
      //   571: istore 9
      //   573: aload_0
      //   574: getfield 104	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:bitField0_	I
      //   577: iconst_1
      //   578: iand
      //   579: iconst_1
      //   580: if_icmpne +24 -> 604
      //   583: iload 6
      //   585: istore 7
      //   587: iload 6
      //   589: istore 8
      //   591: iload 6
      //   593: istore 9
      //   595: aload_0
      //   596: getfield 141	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:strings_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable;
      //   599: invokevirtual 146	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable$Builder;
      //   602: astore 13
      //   604: iload 6
      //   606: istore 7
      //   608: iload 6
      //   610: istore 8
      //   612: iload 6
      //   614: istore 9
      //   616: aload_1
      //   617: getstatic 147	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   620: aload_2
      //   621: invokevirtual 96	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   624: checkcast 143	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable
      //   627: astore 11
      //   629: iload 6
      //   631: istore 7
      //   633: iload 6
      //   635: istore 8
      //   637: iload 6
      //   639: istore 9
      //   641: aload_0
      //   642: aload 11
      //   644: putfield 141	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:strings_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable;
      //   647: aload 13
      //   649: ifnull +44 -> 693
      //   652: iload 6
      //   654: istore 7
      //   656: iload 6
      //   658: istore 8
      //   660: iload 6
      //   662: istore 9
      //   664: aload 13
      //   666: aload 11
      //   668: invokevirtual 152	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable$Builder;
      //   671: pop
      //   672: iload 6
      //   674: istore 7
      //   676: iload 6
      //   678: istore 8
      //   680: iload 6
      //   682: istore 9
      //   684: aload_0
      //   685: aload 13
      //   687: invokevirtual 155	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable;
      //   690: putfield 141	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:strings_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable;
      //   693: iload 6
      //   695: istore 7
      //   697: iload 6
      //   699: istore 8
      //   701: iload 6
      //   703: istore 9
      //   705: aload_0
      //   706: aload_0
      //   707: getfield 104	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:bitField0_	I
      //   710: iconst_1
      //   711: ior
      //   712: putfield 104	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:bitField0_	I
      //   715: goto -679 -> 36
      //   718: iconst_1
      //   719: istore 5
      //   721: goto -685 -> 36
      //   724: astore_1
      //   725: goto +45 -> 770
      //   728: astore_2
      //   729: iload 8
      //   731: istore 7
      //   733: new 55	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   736: astore_1
      //   737: iload 8
      //   739: istore 7
      //   741: aload_1
      //   742: aload_2
      //   743: invokevirtual 159	java/io/IOException:getMessage	()Ljava/lang/String;
      //   746: invokespecial 162	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   749: iload 8
      //   751: istore 7
      //   753: aload_1
      //   754: aload_0
      //   755: invokevirtual 166	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   758: athrow
      //   759: astore_1
      //   760: iload 9
      //   762: istore 7
      //   764: aload_1
      //   765: aload_0
      //   766: invokevirtual 166	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   769: athrow
      //   770: iload 7
      //   772: bipush 8
      //   774: iand
      //   775: bipush 8
      //   777: if_icmpne +14 -> 791
      //   780: aload_0
      //   781: aload_0
      //   782: getfield 89	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:class__	Ljava/util/List;
      //   785: invokestatic 172	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   788: putfield 89	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:class__	Ljava/util/List;
      //   791: aload 4
      //   793: invokevirtual 175	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   796: aload_0
      //   797: aload_3
      //   798: invokevirtual 181	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   801: putfield 183	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   804: goto +14 -> 818
      //   807: astore_1
      //   808: aload_0
      //   809: aload_3
      //   810: invokevirtual 181	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   813: putfield 183	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   816: aload_1
      //   817: athrow
      //   818: aload_0
      //   819: invokevirtual 186	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:makeExtensionsImmutable	()V
      //   822: aload_1
      //   823: athrow
      //   824: iload 6
      //   826: bipush 8
      //   828: iand
      //   829: bipush 8
      //   831: if_icmpne +14 -> 845
      //   834: aload_0
      //   835: aload_0
      //   836: getfield 89	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:class__	Ljava/util/List;
      //   839: invokestatic 172	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   842: putfield 89	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:class__	Ljava/util/List;
      //   845: aload 4
      //   847: invokevirtual 175	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   850: aload_0
      //   851: aload_3
      //   852: invokevirtual 181	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   855: putfield 183	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   858: goto +14 -> 872
      //   861: astore_1
      //   862: aload_0
      //   863: aload_3
      //   864: invokevirtual 181	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   867: putfield 183	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   870: aload_1
      //   871: athrow
      //   872: aload_0
      //   873: invokevirtual 186	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:makeExtensionsImmutable	()V
      //   876: return
      //   877: astore_2
      //   878: goto -82 -> 796
      //   881: astore_1
      //   882: goto -32 -> 850
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	885	0	this	PackageFragment
      //   0	885	1	paramCodedInputStream	CodedInputStream
      //   0	885	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   22	842	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   28	818	4	localCodedOutputStream	CodedOutputStream
      //   31	689	5	i	int
      //   34	795	6	j	int
      //   43	732	7	k	int
      //   47	703	8	m	int
      //   51	710	9	n	int
      //   57	176	10	i1	int
      //   65	602	11	localObject1	Object
      //   68	490	12	localObject2	Object
      //   71	615	13	localObject3	Object
      // Exception table:
      //   from	to	target	type
      //   53	59	724	finally
      //   113	126	724	finally
      //   155	160	724	finally
      //   172	177	724	finally
      //   189	195	724	finally
      //   214	232	724	finally
      //   251	261	724	finally
      //   273	282	724	finally
      //   294	307	724	finally
      //   319	325	724	finally
      //   342	350	724	finally
      //   362	371	724	finally
      //   383	393	724	finally
      //   412	422	724	finally
      //   434	443	724	finally
      //   455	468	724	finally
      //   480	486	724	finally
      //   503	511	724	finally
      //   523	532	724	finally
      //   544	554	724	finally
      //   573	583	724	finally
      //   595	604	724	finally
      //   616	629	724	finally
      //   641	647	724	finally
      //   664	672	724	finally
      //   684	693	724	finally
      //   705	715	724	finally
      //   733	737	724	finally
      //   741	749	724	finally
      //   753	759	724	finally
      //   764	770	724	finally
      //   53	59	728	java/io/IOException
      //   113	126	728	java/io/IOException
      //   155	160	728	java/io/IOException
      //   172	177	728	java/io/IOException
      //   189	195	728	java/io/IOException
      //   214	232	728	java/io/IOException
      //   251	261	728	java/io/IOException
      //   273	282	728	java/io/IOException
      //   294	307	728	java/io/IOException
      //   319	325	728	java/io/IOException
      //   342	350	728	java/io/IOException
      //   362	371	728	java/io/IOException
      //   383	393	728	java/io/IOException
      //   412	422	728	java/io/IOException
      //   434	443	728	java/io/IOException
      //   455	468	728	java/io/IOException
      //   480	486	728	java/io/IOException
      //   503	511	728	java/io/IOException
      //   523	532	728	java/io/IOException
      //   544	554	728	java/io/IOException
      //   573	583	728	java/io/IOException
      //   595	604	728	java/io/IOException
      //   616	629	728	java/io/IOException
      //   641	647	728	java/io/IOException
      //   664	672	728	java/io/IOException
      //   684	693	728	java/io/IOException
      //   705	715	728	java/io/IOException
      //   53	59	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   113	126	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   155	160	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   172	177	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   189	195	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   214	232	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   251	261	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   273	282	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   294	307	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   319	325	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   342	350	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   362	371	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   383	393	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   412	422	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   434	443	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   455	468	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   480	486	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   503	511	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   523	532	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   544	554	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   573	583	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   595	604	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   616	629	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   641	647	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   664	672	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   684	693	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   705	715	759	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   791	796	807	finally
      //   845	850	861	finally
      //   791	796	877	java/io/IOException
      //   845	850	881	java/io/IOException
    }
    
    private PackageFragment(GeneratedMessageLite.ExtendableBuilder<PackageFragment, ?> paramExtendableBuilder)
    {
      super();
      this.unknownFields = paramExtendableBuilder.getUnknownFields();
    }
    
    private PackageFragment(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static PackageFragment getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.strings_ = ProtoBuf.StringTable.getDefaultInstance();
      this.qualifiedNames_ = ProtoBuf.QualifiedNameTable.getDefaultInstance();
      this.package_ = ProtoBuf.Package.getDefaultInstance();
      this.class__ = Collections.emptyList();
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$21400();
    }
    
    public static Builder newBuilder(PackageFragment paramPackageFragment)
    {
      return newBuilder().mergeFrom(paramPackageFragment);
    }
    
    public static PackageFragment parseFrom(InputStream paramInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws IOException
    {
      return (PackageFragment)PARSER.parseFrom(paramInputStream, paramExtensionRegistryLite);
    }
    
    public ProtoBuf.Class getClass_(int paramInt)
    {
      return (ProtoBuf.Class)this.class__.get(paramInt);
    }
    
    public int getClass_Count()
    {
      return this.class__.size();
    }
    
    public List<ProtoBuf.Class> getClass_List()
    {
      return this.class__;
    }
    
    public PackageFragment getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public ProtoBuf.Package getPackage()
    {
      return this.package_;
    }
    
    public Parser<PackageFragment> getParserForType()
    {
      return PARSER;
    }
    
    public ProtoBuf.QualifiedNameTable getQualifiedNames()
    {
      return this.qualifiedNames_;
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
        k = CodedOutputStream.computeMessageSize(1, this.strings_) + 0;
      } else {
        k = 0;
      }
      i = k;
      if ((this.bitField0_ & 0x2) == 2) {
        i = k + CodedOutputStream.computeMessageSize(2, this.qualifiedNames_);
      }
      int k = i;
      int m = j;
      if ((this.bitField0_ & 0x4) == 4) {
        k = i + CodedOutputStream.computeMessageSize(3, this.package_);
      }
      for (m = j; m < this.class__.size(); m++) {
        k += CodedOutputStream.computeMessageSize(4, (MessageLite)this.class__.get(m));
      }
      i = k + extensionsSerializedSize() + this.unknownFields.size();
      this.memoizedSerializedSize = i;
      return i;
    }
    
    public ProtoBuf.StringTable getStrings()
    {
      return this.strings_;
    }
    
    public boolean hasPackage()
    {
      boolean bool;
      if ((this.bitField0_ & 0x4) == 4) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasQualifiedNames()
    {
      boolean bool;
      if ((this.bitField0_ & 0x2) == 2) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasStrings()
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
      if ((hasQualifiedNames()) && (!getQualifiedNames().isInitialized()))
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      if ((hasPackage()) && (!getPackage().isInitialized()))
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      for (i = 0; i < getClass_Count(); i++) {
        if (!getClass_(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
      }
      if (!extensionsAreInitialized())
      {
        this.memoizedIsInitialized = ((byte)0);
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
      GeneratedMessageLite.ExtendableMessage.ExtensionWriter localExtensionWriter = newExtensionWriter();
      if ((this.bitField0_ & 0x1) == 1) {
        paramCodedOutputStream.writeMessage(1, this.strings_);
      }
      if ((this.bitField0_ & 0x2) == 2) {
        paramCodedOutputStream.writeMessage(2, this.qualifiedNames_);
      }
      if ((this.bitField0_ & 0x4) == 4) {
        paramCodedOutputStream.writeMessage(3, this.package_);
      }
      for (int i = 0; i < this.class__.size(); i++) {
        paramCodedOutputStream.writeMessage(4, (MessageLite)this.class__.get(i));
      }
      localExtensionWriter.writeUntil(200, paramCodedOutputStream);
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Builder
      extends GeneratedMessageLite.ExtendableBuilder<ProtoBuf.PackageFragment, Builder>
      implements ProtoBuf.PackageFragmentOrBuilder
    {
      private int bitField0_;
      private List<ProtoBuf.Class> class__ = Collections.emptyList();
      private ProtoBuf.Package package_ = ProtoBuf.Package.getDefaultInstance();
      private ProtoBuf.QualifiedNameTable qualifiedNames_ = ProtoBuf.QualifiedNameTable.getDefaultInstance();
      private ProtoBuf.StringTable strings_ = ProtoBuf.StringTable.getDefaultInstance();
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void ensureClass_IsMutable()
      {
        if ((this.bitField0_ & 0x8) != 8)
        {
          this.class__ = new ArrayList(this.class__);
          this.bitField0_ |= 0x8;
        }
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public ProtoBuf.PackageFragment build()
      {
        ProtoBuf.PackageFragment localPackageFragment = buildPartial();
        if (localPackageFragment.isInitialized()) {
          return localPackageFragment;
        }
        throw newUninitializedMessageException(localPackageFragment);
      }
      
      public ProtoBuf.PackageFragment buildPartial()
      {
        ProtoBuf.PackageFragment localPackageFragment = new ProtoBuf.PackageFragment(this, null);
        int i = this.bitField0_;
        int j = 1;
        if ((i & 0x1) != 1) {
          j = 0;
        }
        ProtoBuf.PackageFragment.access$21602(localPackageFragment, this.strings_);
        int k = j;
        if ((i & 0x2) == 2) {
          k = j | 0x2;
        }
        ProtoBuf.PackageFragment.access$21702(localPackageFragment, this.qualifiedNames_);
        j = k;
        if ((i & 0x4) == 4) {
          j = k | 0x4;
        }
        ProtoBuf.PackageFragment.access$21802(localPackageFragment, this.package_);
        if ((this.bitField0_ & 0x8) == 8)
        {
          this.class__ = Collections.unmodifiableList(this.class__);
          this.bitField0_ &= 0xFFFFFFF7;
        }
        ProtoBuf.PackageFragment.access$21902(localPackageFragment, this.class__);
        ProtoBuf.PackageFragment.access$22002(localPackageFragment, j);
        return localPackageFragment;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public ProtoBuf.Class getClass_(int paramInt)
      {
        return (ProtoBuf.Class)this.class__.get(paramInt);
      }
      
      public int getClass_Count()
      {
        return this.class__.size();
      }
      
      public ProtoBuf.PackageFragment getDefaultInstanceForType()
      {
        return ProtoBuf.PackageFragment.getDefaultInstance();
      }
      
      public ProtoBuf.Package getPackage()
      {
        return this.package_;
      }
      
      public ProtoBuf.QualifiedNameTable getQualifiedNames()
      {
        return this.qualifiedNames_;
      }
      
      public boolean hasPackage()
      {
        boolean bool;
        if ((this.bitField0_ & 0x4) == 4) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasQualifiedNames()
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
        if ((hasQualifiedNames()) && (!getQualifiedNames().isInitialized())) {
          return false;
        }
        if ((hasPackage()) && (!getPackage().isInitialized())) {
          return false;
        }
        for (int i = 0; i < getClass_Count(); i++) {
          if (!getClass_(i).isInitialized()) {
            return false;
          }
        }
        return extensionsAreInitialized();
      }
      
      public Builder mergeFrom(ProtoBuf.PackageFragment paramPackageFragment)
      {
        if (paramPackageFragment == ProtoBuf.PackageFragment.getDefaultInstance()) {
          return this;
        }
        if (paramPackageFragment.hasStrings()) {
          mergeStrings(paramPackageFragment.getStrings());
        }
        if (paramPackageFragment.hasQualifiedNames()) {
          mergeQualifiedNames(paramPackageFragment.getQualifiedNames());
        }
        if (paramPackageFragment.hasPackage()) {
          mergePackage(paramPackageFragment.getPackage());
        }
        if (!paramPackageFragment.class__.isEmpty()) {
          if (this.class__.isEmpty())
          {
            this.class__ = paramPackageFragment.class__;
            this.bitField0_ &= 0xFFFFFFF7;
          }
          else
          {
            ensureClass_IsMutable();
            this.class__.addAll(paramPackageFragment.class__);
          }
        }
        mergeExtensionFields(paramPackageFragment);
        setUnknownFields(getUnknownFields().concat(paramPackageFragment.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 242	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 248 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 130	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_1
        //   29: aload_3
        //   30: astore_2
        //   31: goto +15 -> 46
        //   34: astore_1
        //   35: aload_1
        //   36: invokevirtual 251	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   39: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment
        //   42: astore_2
        //   43: aload_1
        //   44: athrow
        //   45: astore_1
        //   46: aload_2
        //   47: ifnull +9 -> 56
        //   50: aload_0
        //   51: aload_2
        //   52: invokevirtual 130	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$PackageFragment$Builder;
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
      
      public Builder mergePackage(ProtoBuf.Package paramPackage)
      {
        if (((this.bitField0_ & 0x4) == 4) && (this.package_ != ProtoBuf.Package.getDefaultInstance())) {
          this.package_ = ProtoBuf.Package.newBuilder(this.package_).mergeFrom(paramPackage).buildPartial();
        } else {
          this.package_ = paramPackage;
        }
        this.bitField0_ |= 0x4;
        return this;
      }
      
      public Builder mergeQualifiedNames(ProtoBuf.QualifiedNameTable paramQualifiedNameTable)
      {
        if (((this.bitField0_ & 0x2) == 2) && (this.qualifiedNames_ != ProtoBuf.QualifiedNameTable.getDefaultInstance())) {
          this.qualifiedNames_ = ProtoBuf.QualifiedNameTable.newBuilder(this.qualifiedNames_).mergeFrom(paramQualifiedNameTable).buildPartial();
        } else {
          this.qualifiedNames_ = paramQualifiedNameTable;
        }
        this.bitField0_ |= 0x2;
        return this;
      }
      
      public Builder mergeStrings(ProtoBuf.StringTable paramStringTable)
      {
        if (((this.bitField0_ & 0x1) == 1) && (this.strings_ != ProtoBuf.StringTable.getDefaultInstance())) {
          this.strings_ = ProtoBuf.StringTable.newBuilder(this.strings_).mergeFrom(paramStringTable).buildPartial();
        } else {
          this.strings_ = paramStringTable;
        }
        this.bitField0_ |= 0x1;
        return this;
      }
    }
  }
  
  public static final class Property
    extends GeneratedMessageLite.ExtendableMessage<Property>
    implements ProtoBuf.PropertyOrBuilder
  {
    public static Parser<Property> PARSER = new AbstractParser()
    {
      public ProtoBuf.Property parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new ProtoBuf.Property(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final Property defaultInstance;
    private int bitField0_;
    private int flags_;
    private int getterFlags_;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private int name_;
    private int oldFlags_;
    private int receiverTypeId_;
    private ProtoBuf.Type receiverType_;
    private int returnTypeId_;
    private ProtoBuf.Type returnType_;
    private int setterFlags_;
    private ProtoBuf.ValueParameter setterValueParameter_;
    private List<ProtoBuf.TypeParameter> typeParameter_;
    private final ByteString unknownFields;
    private List<Integer> versionRequirement_;
    
    static
    {
      Property localProperty = new Property(true);
      defaultInstance = localProperty;
      localProperty.initFields();
    }
    
    /* Error */
    private Property(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 66	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite$ExtendableMessage:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: i2b
      //   7: putfield 68	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:memoizedIsInitialized	B
      //   10: aload_0
      //   11: iconst_m1
      //   12: putfield 70	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:memoizedSerializedSize	I
      //   15: aload_0
      //   16: invokespecial 59	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:initFields	()V
      //   19: invokestatic 76	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   22: astore_3
      //   23: aload_3
      //   24: iconst_1
      //   25: invokestatic 82	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   28: astore 4
      //   30: iconst_0
      //   31: istore 5
      //   33: iconst_0
      //   34: istore 6
      //   36: iload 5
      //   38: ifne +1549 -> 1587
      //   41: iload 6
      //   43: istore 7
      //   45: iload 6
      //   47: istore 8
      //   49: iload 6
      //   51: istore 9
      //   53: aload_1
      //   54: invokevirtual 88	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   57: istore 10
      //   59: aconst_null
      //   60: astore 11
      //   62: aconst_null
      //   63: astore 12
      //   65: aconst_null
      //   66: astore 13
      //   68: iload 10
      //   70: lookupswitch	default:+122->192, 0:+1380->1450, 8:+1335->1405, 16:+1290->1360, 26:+1126->1196, 34:+1016->1086, 42:+852->922, 50:+689->759, 56:+642->712, 64:+595->665, 72:+549->619, 80:+503->573, 88:+458->528, 248:+346->416, 250:+149->219
      //   192: iload 6
      //   194: istore 7
      //   196: iload 6
      //   198: istore 8
      //   200: iload 6
      //   202: istore 9
      //   204: aload_0
      //   205: aload_1
      //   206: aload 4
      //   208: aload_2
      //   209: iload 10
      //   211: invokevirtual 92	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   214: istore 14
      //   216: goto +1240 -> 1456
      //   219: iload 6
      //   221: istore 7
      //   223: iload 6
      //   225: istore 8
      //   227: iload 6
      //   229: istore 9
      //   231: aload_1
      //   232: aload_1
      //   233: invokevirtual 95	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readRawVarint32	()I
      //   236: invokevirtual 99	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:pushLimit	(I)I
      //   239: istore 15
      //   241: iload 6
      //   243: istore 10
      //   245: iload 6
      //   247: sipush 2048
      //   250: iand
      //   251: sipush 2048
      //   254: if_icmpeq +86 -> 340
      //   257: iload 6
      //   259: istore 10
      //   261: iload 6
      //   263: istore 7
      //   265: iload 6
      //   267: istore 8
      //   269: iload 6
      //   271: istore 9
      //   273: aload_1
      //   274: invokevirtual 102	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
      //   277: ifle +63 -> 340
      //   280: iload 6
      //   282: istore 7
      //   284: iload 6
      //   286: istore 8
      //   288: iload 6
      //   290: istore 9
      //   292: new 104	java/util/ArrayList
      //   295: astore 13
      //   297: iload 6
      //   299: istore 7
      //   301: iload 6
      //   303: istore 8
      //   305: iload 6
      //   307: istore 9
      //   309: aload 13
      //   311: invokespecial 105	java/util/ArrayList:<init>	()V
      //   314: iload 6
      //   316: istore 7
      //   318: iload 6
      //   320: istore 8
      //   322: iload 6
      //   324: istore 9
      //   326: aload_0
      //   327: aload 13
      //   329: putfield 107	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:versionRequirement_	Ljava/util/List;
      //   332: iload 6
      //   334: sipush 2048
      //   337: ior
      //   338: istore 10
      //   340: iload 10
      //   342: istore 7
      //   344: iload 10
      //   346: istore 8
      //   348: iload 10
      //   350: istore 9
      //   352: aload_1
      //   353: invokevirtual 102	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
      //   356: ifle +35 -> 391
      //   359: iload 10
      //   361: istore 7
      //   363: iload 10
      //   365: istore 8
      //   367: iload 10
      //   369: istore 9
      //   371: aload_0
      //   372: getfield 107	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:versionRequirement_	Ljava/util/List;
      //   375: aload_1
      //   376: invokevirtual 110	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   379: invokestatic 116	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   382: invokeinterface 122 2 0
      //   387: pop
      //   388: goto -48 -> 340
      //   391: iload 10
      //   393: istore 7
      //   395: iload 10
      //   397: istore 8
      //   399: iload 10
      //   401: istore 9
      //   403: aload_1
      //   404: iload 15
      //   406: invokevirtual 126	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:popLimit	(I)V
      //   409: iload 10
      //   411: istore 6
      //   413: goto -377 -> 36
      //   416: iload 6
      //   418: istore 10
      //   420: iload 6
      //   422: sipush 2048
      //   425: iand
      //   426: sipush 2048
      //   429: if_icmpeq +63 -> 492
      //   432: iload 6
      //   434: istore 7
      //   436: iload 6
      //   438: istore 8
      //   440: iload 6
      //   442: istore 9
      //   444: new 104	java/util/ArrayList
      //   447: astore 13
      //   449: iload 6
      //   451: istore 7
      //   453: iload 6
      //   455: istore 8
      //   457: iload 6
      //   459: istore 9
      //   461: aload 13
      //   463: invokespecial 105	java/util/ArrayList:<init>	()V
      //   466: iload 6
      //   468: istore 7
      //   470: iload 6
      //   472: istore 8
      //   474: iload 6
      //   476: istore 9
      //   478: aload_0
      //   479: aload 13
      //   481: putfield 107	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:versionRequirement_	Ljava/util/List;
      //   484: iload 6
      //   486: sipush 2048
      //   489: ior
      //   490: istore 10
      //   492: iload 10
      //   494: istore 7
      //   496: iload 10
      //   498: istore 8
      //   500: iload 10
      //   502: istore 9
      //   504: aload_0
      //   505: getfield 107	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:versionRequirement_	Ljava/util/List;
      //   508: aload_1
      //   509: invokevirtual 110	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   512: invokestatic 116	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   515: invokeinterface 122 2 0
      //   520: pop
      //   521: iload 10
      //   523: istore 6
      //   525: goto -489 -> 36
      //   528: iload 6
      //   530: istore 7
      //   532: iload 6
      //   534: istore 8
      //   536: iload 6
      //   538: istore 9
      //   540: aload_0
      //   541: aload_0
      //   542: getfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   545: iconst_1
      //   546: ior
      //   547: putfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   550: iload 6
      //   552: istore 7
      //   554: iload 6
      //   556: istore 8
      //   558: iload 6
      //   560: istore 9
      //   562: aload_0
      //   563: aload_1
      //   564: invokevirtual 110	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   567: putfield 130	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:flags_	I
      //   570: goto -534 -> 36
      //   573: iload 6
      //   575: istore 7
      //   577: iload 6
      //   579: istore 8
      //   581: iload 6
      //   583: istore 9
      //   585: aload_0
      //   586: aload_0
      //   587: getfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   590: bipush 64
      //   592: ior
      //   593: putfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   596: iload 6
      //   598: istore 7
      //   600: iload 6
      //   602: istore 8
      //   604: iload 6
      //   606: istore 9
      //   608: aload_0
      //   609: aload_1
      //   610: invokevirtual 110	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   613: putfield 132	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:receiverTypeId_	I
      //   616: goto -580 -> 36
      //   619: iload 6
      //   621: istore 7
      //   623: iload 6
      //   625: istore 8
      //   627: iload 6
      //   629: istore 9
      //   631: aload_0
      //   632: aload_0
      //   633: getfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   636: bipush 16
      //   638: ior
      //   639: putfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   642: iload 6
      //   644: istore 7
      //   646: iload 6
      //   648: istore 8
      //   650: iload 6
      //   652: istore 9
      //   654: aload_0
      //   655: aload_1
      //   656: invokevirtual 110	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   659: putfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:returnTypeId_	I
      //   662: goto -626 -> 36
      //   665: iload 6
      //   667: istore 7
      //   669: iload 6
      //   671: istore 8
      //   673: iload 6
      //   675: istore 9
      //   677: aload_0
      //   678: aload_0
      //   679: getfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   682: sipush 512
      //   685: ior
      //   686: putfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   689: iload 6
      //   691: istore 7
      //   693: iload 6
      //   695: istore 8
      //   697: iload 6
      //   699: istore 9
      //   701: aload_0
      //   702: aload_1
      //   703: invokevirtual 110	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   706: putfield 136	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:setterFlags_	I
      //   709: goto -673 -> 36
      //   712: iload 6
      //   714: istore 7
      //   716: iload 6
      //   718: istore 8
      //   720: iload 6
      //   722: istore 9
      //   724: aload_0
      //   725: aload_0
      //   726: getfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   729: sipush 256
      //   732: ior
      //   733: putfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   736: iload 6
      //   738: istore 7
      //   740: iload 6
      //   742: istore 8
      //   744: iload 6
      //   746: istore 9
      //   748: aload_0
      //   749: aload_1
      //   750: invokevirtual 110	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   753: putfield 138	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:getterFlags_	I
      //   756: goto -720 -> 36
      //   759: iload 6
      //   761: istore 7
      //   763: iload 6
      //   765: istore 8
      //   767: iload 6
      //   769: istore 9
      //   771: aload_0
      //   772: getfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   775: sipush 128
      //   778: iand
      //   779: sipush 128
      //   782: if_icmpne +24 -> 806
      //   785: iload 6
      //   787: istore 7
      //   789: iload 6
      //   791: istore 8
      //   793: iload 6
      //   795: istore 9
      //   797: aload_0
      //   798: getfield 140	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:setterValueParameter_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter;
      //   801: invokevirtual 146	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter$Builder;
      //   804: astore 13
      //   806: iload 6
      //   808: istore 7
      //   810: iload 6
      //   812: istore 8
      //   814: iload 6
      //   816: istore 9
      //   818: aload_1
      //   819: getstatic 147	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   822: aload_2
      //   823: invokevirtual 151	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   826: checkcast 142	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter
      //   829: astore 11
      //   831: iload 6
      //   833: istore 7
      //   835: iload 6
      //   837: istore 8
      //   839: iload 6
      //   841: istore 9
      //   843: aload_0
      //   844: aload 11
      //   846: putfield 140	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:setterValueParameter_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter;
      //   849: aload 13
      //   851: ifnull +44 -> 895
      //   854: iload 6
      //   856: istore 7
      //   858: iload 6
      //   860: istore 8
      //   862: iload 6
      //   864: istore 9
      //   866: aload 13
      //   868: aload 11
      //   870: invokevirtual 157	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter$Builder;
      //   873: pop
      //   874: iload 6
      //   876: istore 7
      //   878: iload 6
      //   880: istore 8
      //   882: iload 6
      //   884: istore 9
      //   886: aload_0
      //   887: aload 13
      //   889: invokevirtual 161	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter;
      //   892: putfield 140	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:setterValueParameter_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter;
      //   895: iload 6
      //   897: istore 7
      //   899: iload 6
      //   901: istore 8
      //   903: iload 6
      //   905: istore 9
      //   907: aload_0
      //   908: aload_0
      //   909: getfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   912: sipush 128
      //   915: ior
      //   916: putfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   919: goto -883 -> 36
      //   922: aload 11
      //   924: astore 13
      //   926: iload 6
      //   928: istore 7
      //   930: iload 6
      //   932: istore 8
      //   934: iload 6
      //   936: istore 9
      //   938: aload_0
      //   939: getfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   942: bipush 32
      //   944: iand
      //   945: bipush 32
      //   947: if_icmpne +24 -> 971
      //   950: iload 6
      //   952: istore 7
      //   954: iload 6
      //   956: istore 8
      //   958: iload 6
      //   960: istore 9
      //   962: aload_0
      //   963: getfield 163	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:receiverType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   966: invokevirtual 168	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   969: astore 13
      //   971: iload 6
      //   973: istore 7
      //   975: iload 6
      //   977: istore 8
      //   979: iload 6
      //   981: istore 9
      //   983: aload_1
      //   984: getstatic 169	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   987: aload_2
      //   988: invokevirtual 151	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   991: checkcast 165	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type
      //   994: astore 11
      //   996: iload 6
      //   998: istore 7
      //   1000: iload 6
      //   1002: istore 8
      //   1004: iload 6
      //   1006: istore 9
      //   1008: aload_0
      //   1009: aload 11
      //   1011: putfield 163	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:receiverType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   1014: aload 13
      //   1016: ifnull +44 -> 1060
      //   1019: iload 6
      //   1021: istore 7
      //   1023: iload 6
      //   1025: istore 8
      //   1027: iload 6
      //   1029: istore 9
      //   1031: aload 13
      //   1033: aload 11
      //   1035: invokevirtual 174	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   1038: pop
      //   1039: iload 6
      //   1041: istore 7
      //   1043: iload 6
      //   1045: istore 8
      //   1047: iload 6
      //   1049: istore 9
      //   1051: aload_0
      //   1052: aload 13
      //   1054: invokevirtual 177	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   1057: putfield 163	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:receiverType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   1060: iload 6
      //   1062: istore 7
      //   1064: iload 6
      //   1066: istore 8
      //   1068: iload 6
      //   1070: istore 9
      //   1072: aload_0
      //   1073: aload_0
      //   1074: getfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   1077: bipush 32
      //   1079: ior
      //   1080: putfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   1083: goto -1047 -> 36
      //   1086: iload 6
      //   1088: istore 10
      //   1090: iload 6
      //   1092: bipush 32
      //   1094: iand
      //   1095: bipush 32
      //   1097: if_icmpeq +62 -> 1159
      //   1100: iload 6
      //   1102: istore 7
      //   1104: iload 6
      //   1106: istore 8
      //   1108: iload 6
      //   1110: istore 9
      //   1112: new 104	java/util/ArrayList
      //   1115: astore 13
      //   1117: iload 6
      //   1119: istore 7
      //   1121: iload 6
      //   1123: istore 8
      //   1125: iload 6
      //   1127: istore 9
      //   1129: aload 13
      //   1131: invokespecial 105	java/util/ArrayList:<init>	()V
      //   1134: iload 6
      //   1136: istore 7
      //   1138: iload 6
      //   1140: istore 8
      //   1142: iload 6
      //   1144: istore 9
      //   1146: aload_0
      //   1147: aload 13
      //   1149: putfield 179	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:typeParameter_	Ljava/util/List;
      //   1152: iload 6
      //   1154: bipush 32
      //   1156: ior
      //   1157: istore 10
      //   1159: iload 10
      //   1161: istore 7
      //   1163: iload 10
      //   1165: istore 8
      //   1167: iload 10
      //   1169: istore 9
      //   1171: aload_0
      //   1172: getfield 179	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:typeParameter_	Ljava/util/List;
      //   1175: aload_1
      //   1176: getstatic 182	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   1179: aload_2
      //   1180: invokevirtual 151	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   1183: invokeinterface 122 2 0
      //   1188: pop
      //   1189: iload 10
      //   1191: istore 6
      //   1193: goto -1157 -> 36
      //   1196: aload 12
      //   1198: astore 13
      //   1200: iload 6
      //   1202: istore 7
      //   1204: iload 6
      //   1206: istore 8
      //   1208: iload 6
      //   1210: istore 9
      //   1212: aload_0
      //   1213: getfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   1216: bipush 8
      //   1218: iand
      //   1219: bipush 8
      //   1221: if_icmpne +24 -> 1245
      //   1224: iload 6
      //   1226: istore 7
      //   1228: iload 6
      //   1230: istore 8
      //   1232: iload 6
      //   1234: istore 9
      //   1236: aload_0
      //   1237: getfield 184	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:returnType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   1240: invokevirtual 168	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   1243: astore 13
      //   1245: iload 6
      //   1247: istore 7
      //   1249: iload 6
      //   1251: istore 8
      //   1253: iload 6
      //   1255: istore 9
      //   1257: aload_1
      //   1258: getstatic 169	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   1261: aload_2
      //   1262: invokevirtual 151	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   1265: checkcast 165	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type
      //   1268: astore 11
      //   1270: iload 6
      //   1272: istore 7
      //   1274: iload 6
      //   1276: istore 8
      //   1278: iload 6
      //   1280: istore 9
      //   1282: aload_0
      //   1283: aload 11
      //   1285: putfield 184	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:returnType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   1288: aload 13
      //   1290: ifnull +44 -> 1334
      //   1293: iload 6
      //   1295: istore 7
      //   1297: iload 6
      //   1299: istore 8
      //   1301: iload 6
      //   1303: istore 9
      //   1305: aload 13
      //   1307: aload 11
      //   1309: invokevirtual 174	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   1312: pop
      //   1313: iload 6
      //   1315: istore 7
      //   1317: iload 6
      //   1319: istore 8
      //   1321: iload 6
      //   1323: istore 9
      //   1325: aload_0
      //   1326: aload 13
      //   1328: invokevirtual 177	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   1331: putfield 184	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:returnType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   1334: iload 6
      //   1336: istore 7
      //   1338: iload 6
      //   1340: istore 8
      //   1342: iload 6
      //   1344: istore 9
      //   1346: aload_0
      //   1347: aload_0
      //   1348: getfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   1351: bipush 8
      //   1353: ior
      //   1354: putfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   1357: goto -1321 -> 36
      //   1360: iload 6
      //   1362: istore 7
      //   1364: iload 6
      //   1366: istore 8
      //   1368: iload 6
      //   1370: istore 9
      //   1372: aload_0
      //   1373: aload_0
      //   1374: getfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   1377: iconst_4
      //   1378: ior
      //   1379: putfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   1382: iload 6
      //   1384: istore 7
      //   1386: iload 6
      //   1388: istore 8
      //   1390: iload 6
      //   1392: istore 9
      //   1394: aload_0
      //   1395: aload_1
      //   1396: invokevirtual 110	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   1399: putfield 186	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:name_	I
      //   1402: goto -1366 -> 36
      //   1405: iload 6
      //   1407: istore 7
      //   1409: iload 6
      //   1411: istore 8
      //   1413: iload 6
      //   1415: istore 9
      //   1417: aload_0
      //   1418: aload_0
      //   1419: getfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   1422: iconst_2
      //   1423: ior
      //   1424: putfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:bitField0_	I
      //   1427: iload 6
      //   1429: istore 7
      //   1431: iload 6
      //   1433: istore 8
      //   1435: iload 6
      //   1437: istore 9
      //   1439: aload_0
      //   1440: aload_1
      //   1441: invokevirtual 110	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   1444: putfield 188	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:oldFlags_	I
      //   1447: goto -1411 -> 36
      //   1450: iconst_1
      //   1451: istore 5
      //   1453: goto -1417 -> 36
      //   1456: iload 14
      //   1458: ifne -1422 -> 36
      //   1461: goto -11 -> 1450
      //   1464: astore_1
      //   1465: goto +45 -> 1510
      //   1468: astore_1
      //   1469: iload 8
      //   1471: istore 7
      //   1473: new 63	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1476: astore_2
      //   1477: iload 8
      //   1479: istore 7
      //   1481: aload_2
      //   1482: aload_1
      //   1483: invokevirtual 192	java/io/IOException:getMessage	()Ljava/lang/String;
      //   1486: invokespecial 195	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   1489: iload 8
      //   1491: istore 7
      //   1493: aload_2
      //   1494: aload_0
      //   1495: invokevirtual 199	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   1498: athrow
      //   1499: astore_1
      //   1500: iload 9
      //   1502: istore 7
      //   1504: aload_1
      //   1505: aload_0
      //   1506: invokevirtual 199	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   1509: athrow
      //   1510: iload 7
      //   1512: bipush 32
      //   1514: iand
      //   1515: bipush 32
      //   1517: if_icmpne +14 -> 1531
      //   1520: aload_0
      //   1521: aload_0
      //   1522: getfield 179	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:typeParameter_	Ljava/util/List;
      //   1525: invokestatic 205	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   1528: putfield 179	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:typeParameter_	Ljava/util/List;
      //   1531: iload 7
      //   1533: sipush 2048
      //   1536: iand
      //   1537: sipush 2048
      //   1540: if_icmpne +14 -> 1554
      //   1543: aload_0
      //   1544: aload_0
      //   1545: getfield 107	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:versionRequirement_	Ljava/util/List;
      //   1548: invokestatic 205	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   1551: putfield 107	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:versionRequirement_	Ljava/util/List;
      //   1554: aload 4
      //   1556: invokevirtual 208	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   1559: aload_0
      //   1560: aload_3
      //   1561: invokevirtual 214	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1564: putfield 216	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1567: goto +14 -> 1581
      //   1570: astore_1
      //   1571: aload_0
      //   1572: aload_3
      //   1573: invokevirtual 214	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1576: putfield 216	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1579: aload_1
      //   1580: athrow
      //   1581: aload_0
      //   1582: invokevirtual 219	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:makeExtensionsImmutable	()V
      //   1585: aload_1
      //   1586: athrow
      //   1587: iload 6
      //   1589: bipush 32
      //   1591: iand
      //   1592: bipush 32
      //   1594: if_icmpne +14 -> 1608
      //   1597: aload_0
      //   1598: aload_0
      //   1599: getfield 179	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:typeParameter_	Ljava/util/List;
      //   1602: invokestatic 205	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   1605: putfield 179	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:typeParameter_	Ljava/util/List;
      //   1608: iload 6
      //   1610: sipush 2048
      //   1613: iand
      //   1614: sipush 2048
      //   1617: if_icmpne +14 -> 1631
      //   1620: aload_0
      //   1621: aload_0
      //   1622: getfield 107	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:versionRequirement_	Ljava/util/List;
      //   1625: invokestatic 205	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   1628: putfield 107	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:versionRequirement_	Ljava/util/List;
      //   1631: aload 4
      //   1633: invokevirtual 208	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   1636: aload_0
      //   1637: aload_3
      //   1638: invokevirtual 214	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1641: putfield 216	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1644: goto +14 -> 1658
      //   1647: astore_1
      //   1648: aload_0
      //   1649: aload_3
      //   1650: invokevirtual 214	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1653: putfield 216	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1656: aload_1
      //   1657: athrow
      //   1658: aload_0
      //   1659: invokevirtual 219	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:makeExtensionsImmutable	()V
      //   1662: return
      //   1663: astore_2
      //   1664: goto -105 -> 1559
      //   1667: astore_1
      //   1668: goto -32 -> 1636
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	1671	0	this	Property
      //   0	1671	1	paramCodedInputStream	CodedInputStream
      //   0	1671	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   22	1628	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   28	1604	4	localCodedOutputStream	CodedOutputStream
      //   31	1421	5	i	int
      //   34	1580	6	j	int
      //   43	1494	7	k	int
      //   47	1443	8	m	int
      //   51	1450	9	n	int
      //   57	1133	10	i1	int
      //   60	1248	11	localObject1	Object
      //   63	1134	12	localObject2	Object
      //   66	1261	13	localObject3	Object
      //   214	1243	14	bool	boolean
      //   239	166	15	i2	int
      // Exception table:
      //   from	to	target	type
      //   53	59	1464	finally
      //   204	216	1464	finally
      //   231	241	1464	finally
      //   273	280	1464	finally
      //   292	297	1464	finally
      //   309	314	1464	finally
      //   326	332	1464	finally
      //   352	359	1464	finally
      //   371	388	1464	finally
      //   403	409	1464	finally
      //   444	449	1464	finally
      //   461	466	1464	finally
      //   478	484	1464	finally
      //   504	521	1464	finally
      //   540	550	1464	finally
      //   562	570	1464	finally
      //   585	596	1464	finally
      //   608	616	1464	finally
      //   631	642	1464	finally
      //   654	662	1464	finally
      //   677	689	1464	finally
      //   701	709	1464	finally
      //   724	736	1464	finally
      //   748	756	1464	finally
      //   771	785	1464	finally
      //   797	806	1464	finally
      //   818	831	1464	finally
      //   843	849	1464	finally
      //   866	874	1464	finally
      //   886	895	1464	finally
      //   907	919	1464	finally
      //   938	950	1464	finally
      //   962	971	1464	finally
      //   983	996	1464	finally
      //   1008	1014	1464	finally
      //   1031	1039	1464	finally
      //   1051	1060	1464	finally
      //   1072	1083	1464	finally
      //   1112	1117	1464	finally
      //   1129	1134	1464	finally
      //   1146	1152	1464	finally
      //   1171	1189	1464	finally
      //   1212	1224	1464	finally
      //   1236	1245	1464	finally
      //   1257	1270	1464	finally
      //   1282	1288	1464	finally
      //   1305	1313	1464	finally
      //   1325	1334	1464	finally
      //   1346	1357	1464	finally
      //   1372	1382	1464	finally
      //   1394	1402	1464	finally
      //   1417	1427	1464	finally
      //   1439	1447	1464	finally
      //   1473	1477	1464	finally
      //   1481	1489	1464	finally
      //   1493	1499	1464	finally
      //   1504	1510	1464	finally
      //   53	59	1468	java/io/IOException
      //   204	216	1468	java/io/IOException
      //   231	241	1468	java/io/IOException
      //   273	280	1468	java/io/IOException
      //   292	297	1468	java/io/IOException
      //   309	314	1468	java/io/IOException
      //   326	332	1468	java/io/IOException
      //   352	359	1468	java/io/IOException
      //   371	388	1468	java/io/IOException
      //   403	409	1468	java/io/IOException
      //   444	449	1468	java/io/IOException
      //   461	466	1468	java/io/IOException
      //   478	484	1468	java/io/IOException
      //   504	521	1468	java/io/IOException
      //   540	550	1468	java/io/IOException
      //   562	570	1468	java/io/IOException
      //   585	596	1468	java/io/IOException
      //   608	616	1468	java/io/IOException
      //   631	642	1468	java/io/IOException
      //   654	662	1468	java/io/IOException
      //   677	689	1468	java/io/IOException
      //   701	709	1468	java/io/IOException
      //   724	736	1468	java/io/IOException
      //   748	756	1468	java/io/IOException
      //   771	785	1468	java/io/IOException
      //   797	806	1468	java/io/IOException
      //   818	831	1468	java/io/IOException
      //   843	849	1468	java/io/IOException
      //   866	874	1468	java/io/IOException
      //   886	895	1468	java/io/IOException
      //   907	919	1468	java/io/IOException
      //   938	950	1468	java/io/IOException
      //   962	971	1468	java/io/IOException
      //   983	996	1468	java/io/IOException
      //   1008	1014	1468	java/io/IOException
      //   1031	1039	1468	java/io/IOException
      //   1051	1060	1468	java/io/IOException
      //   1072	1083	1468	java/io/IOException
      //   1112	1117	1468	java/io/IOException
      //   1129	1134	1468	java/io/IOException
      //   1146	1152	1468	java/io/IOException
      //   1171	1189	1468	java/io/IOException
      //   1212	1224	1468	java/io/IOException
      //   1236	1245	1468	java/io/IOException
      //   1257	1270	1468	java/io/IOException
      //   1282	1288	1468	java/io/IOException
      //   1305	1313	1468	java/io/IOException
      //   1325	1334	1468	java/io/IOException
      //   1346	1357	1468	java/io/IOException
      //   1372	1382	1468	java/io/IOException
      //   1394	1402	1468	java/io/IOException
      //   1417	1427	1468	java/io/IOException
      //   1439	1447	1468	java/io/IOException
      //   53	59	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   204	216	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   231	241	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   273	280	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   292	297	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   309	314	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   326	332	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   352	359	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   371	388	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   403	409	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   444	449	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   461	466	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   478	484	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   504	521	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   540	550	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   562	570	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   585	596	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   608	616	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   631	642	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   654	662	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   677	689	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   701	709	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   724	736	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   748	756	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   771	785	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   797	806	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   818	831	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   843	849	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   866	874	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   886	895	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   907	919	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   938	950	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   962	971	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   983	996	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1008	1014	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1031	1039	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1051	1060	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1072	1083	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1112	1117	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1129	1134	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1146	1152	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1171	1189	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1212	1224	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1236	1245	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1257	1270	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1282	1288	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1305	1313	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1325	1334	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1346	1357	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1372	1382	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1394	1402	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1417	1427	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1439	1447	1499	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1554	1559	1570	finally
      //   1631	1636	1647	finally
      //   1554	1559	1663	java/io/IOException
      //   1631	1636	1667	java/io/IOException
    }
    
    private Property(GeneratedMessageLite.ExtendableBuilder<Property, ?> paramExtendableBuilder)
    {
      super();
      this.unknownFields = paramExtendableBuilder.getUnknownFields();
    }
    
    private Property(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static Property getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.flags_ = 518;
      this.oldFlags_ = 2054;
      this.name_ = 0;
      this.returnType_ = ProtoBuf.Type.getDefaultInstance();
      this.returnTypeId_ = 0;
      this.typeParameter_ = Collections.emptyList();
      this.receiverType_ = ProtoBuf.Type.getDefaultInstance();
      this.receiverTypeId_ = 0;
      this.setterValueParameter_ = ProtoBuf.ValueParameter.getDefaultInstance();
      this.getterFlags_ = 0;
      this.setterFlags_ = 0;
      this.versionRequirement_ = Collections.emptyList();
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$15000();
    }
    
    public static Builder newBuilder(Property paramProperty)
    {
      return newBuilder().mergeFrom(paramProperty);
    }
    
    public Property getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public int getFlags()
    {
      return this.flags_;
    }
    
    public int getGetterFlags()
    {
      return this.getterFlags_;
    }
    
    public int getName()
    {
      return this.name_;
    }
    
    public int getOldFlags()
    {
      return this.oldFlags_;
    }
    
    public Parser<Property> getParserForType()
    {
      return PARSER;
    }
    
    public ProtoBuf.Type getReceiverType()
    {
      return this.receiverType_;
    }
    
    public int getReceiverTypeId()
    {
      return this.receiverTypeId_;
    }
    
    public ProtoBuf.Type getReturnType()
    {
      return this.returnType_;
    }
    
    public int getReturnTypeId()
    {
      return this.returnTypeId_;
    }
    
    public int getSerializedSize()
    {
      int i = this.memoizedSerializedSize;
      if (i != -1) {
        return i;
      }
      i = this.bitField0_;
      int j = 0;
      if ((i & 0x2) == 2) {
        i = CodedOutputStream.computeInt32Size(1, this.oldFlags_) + 0;
      } else {
        i = 0;
      }
      int k = i;
      if ((this.bitField0_ & 0x4) == 4) {
        k = i + CodedOutputStream.computeInt32Size(2, this.name_);
      }
      i = k;
      if ((this.bitField0_ & 0x8) == 8) {
        i = k + CodedOutputStream.computeMessageSize(3, this.returnType_);
      }
      for (k = 0; k < this.typeParameter_.size(); k++) {
        i += CodedOutputStream.computeMessageSize(4, (MessageLite)this.typeParameter_.get(k));
      }
      k = i;
      if ((this.bitField0_ & 0x20) == 32) {
        k = i + CodedOutputStream.computeMessageSize(5, this.receiverType_);
      }
      int m = k;
      if ((this.bitField0_ & 0x80) == 128) {
        m = k + CodedOutputStream.computeMessageSize(6, this.setterValueParameter_);
      }
      i = m;
      if ((this.bitField0_ & 0x100) == 256) {
        i = m + CodedOutputStream.computeInt32Size(7, this.getterFlags_);
      }
      k = i;
      if ((this.bitField0_ & 0x200) == 512) {
        k = i + CodedOutputStream.computeInt32Size(8, this.setterFlags_);
      }
      i = k;
      if ((this.bitField0_ & 0x10) == 16) {
        i = k + CodedOutputStream.computeInt32Size(9, this.returnTypeId_);
      }
      k = i;
      if ((this.bitField0_ & 0x40) == 64) {
        k = i + CodedOutputStream.computeInt32Size(10, this.receiverTypeId_);
      }
      i = k;
      if ((this.bitField0_ & 0x1) == 1) {
        i = k + CodedOutputStream.computeInt32Size(11, this.flags_);
      }
      m = 0;
      for (k = j; k < this.versionRequirement_.size(); k++) {
        m += CodedOutputStream.computeInt32SizeNoTag(((Integer)this.versionRequirement_.get(k)).intValue());
      }
      i = i + m + getVersionRequirementList().size() * 2 + extensionsSerializedSize() + this.unknownFields.size();
      this.memoizedSerializedSize = i;
      return i;
    }
    
    public int getSetterFlags()
    {
      return this.setterFlags_;
    }
    
    public ProtoBuf.ValueParameter getSetterValueParameter()
    {
      return this.setterValueParameter_;
    }
    
    public ProtoBuf.TypeParameter getTypeParameter(int paramInt)
    {
      return (ProtoBuf.TypeParameter)this.typeParameter_.get(paramInt);
    }
    
    public int getTypeParameterCount()
    {
      return this.typeParameter_.size();
    }
    
    public List<ProtoBuf.TypeParameter> getTypeParameterList()
    {
      return this.typeParameter_;
    }
    
    public List<Integer> getVersionRequirementList()
    {
      return this.versionRequirement_;
    }
    
    public boolean hasFlags()
    {
      int i = this.bitField0_;
      boolean bool = true;
      if ((i & 0x1) != 1) {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasGetterFlags()
    {
      boolean bool;
      if ((this.bitField0_ & 0x100) == 256) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasName()
    {
      boolean bool;
      if ((this.bitField0_ & 0x4) == 4) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasOldFlags()
    {
      boolean bool;
      if ((this.bitField0_ & 0x2) == 2) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasReceiverType()
    {
      boolean bool;
      if ((this.bitField0_ & 0x20) == 32) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasReceiverTypeId()
    {
      boolean bool;
      if ((this.bitField0_ & 0x40) == 64) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasReturnType()
    {
      boolean bool;
      if ((this.bitField0_ & 0x8) == 8) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasReturnTypeId()
    {
      boolean bool;
      if ((this.bitField0_ & 0x10) == 16) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasSetterFlags()
    {
      boolean bool;
      if ((this.bitField0_ & 0x200) == 512) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasSetterValueParameter()
    {
      boolean bool;
      if ((this.bitField0_ & 0x80) == 128) {
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
      if (!hasName())
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      if ((hasReturnType()) && (!getReturnType().isInitialized()))
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      for (i = 0; i < getTypeParameterCount(); i++) {
        if (!getTypeParameter(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
      }
      if ((hasReceiverType()) && (!getReceiverType().isInitialized()))
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      if ((hasSetterValueParameter()) && (!getSetterValueParameter().isInitialized()))
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      if (!extensionsAreInitialized())
      {
        this.memoizedIsInitialized = ((byte)0);
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
      GeneratedMessageLite.ExtendableMessage.ExtensionWriter localExtensionWriter = newExtensionWriter();
      if ((this.bitField0_ & 0x2) == 2) {
        paramCodedOutputStream.writeInt32(1, this.oldFlags_);
      }
      if ((this.bitField0_ & 0x4) == 4) {
        paramCodedOutputStream.writeInt32(2, this.name_);
      }
      if ((this.bitField0_ & 0x8) == 8) {
        paramCodedOutputStream.writeMessage(3, this.returnType_);
      }
      int i = 0;
      for (int j = 0; j < this.typeParameter_.size(); j++) {
        paramCodedOutputStream.writeMessage(4, (MessageLite)this.typeParameter_.get(j));
      }
      if ((this.bitField0_ & 0x20) == 32) {
        paramCodedOutputStream.writeMessage(5, this.receiverType_);
      }
      if ((this.bitField0_ & 0x80) == 128) {
        paramCodedOutputStream.writeMessage(6, this.setterValueParameter_);
      }
      if ((this.bitField0_ & 0x100) == 256) {
        paramCodedOutputStream.writeInt32(7, this.getterFlags_);
      }
      if ((this.bitField0_ & 0x200) == 512) {
        paramCodedOutputStream.writeInt32(8, this.setterFlags_);
      }
      if ((this.bitField0_ & 0x10) == 16) {
        paramCodedOutputStream.writeInt32(9, this.returnTypeId_);
      }
      if ((this.bitField0_ & 0x40) == 64) {
        paramCodedOutputStream.writeInt32(10, this.receiverTypeId_);
      }
      j = i;
      if ((this.bitField0_ & 0x1) == 1) {
        paramCodedOutputStream.writeInt32(11, this.flags_);
      }
      for (j = i; j < this.versionRequirement_.size(); j++) {
        paramCodedOutputStream.writeInt32(31, ((Integer)this.versionRequirement_.get(j)).intValue());
      }
      localExtensionWriter.writeUntil(19000, paramCodedOutputStream);
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Builder
      extends GeneratedMessageLite.ExtendableBuilder<ProtoBuf.Property, Builder>
      implements ProtoBuf.PropertyOrBuilder
    {
      private int bitField0_;
      private int flags_ = 518;
      private int getterFlags_;
      private int name_;
      private int oldFlags_ = 2054;
      private int receiverTypeId_;
      private ProtoBuf.Type receiverType_ = ProtoBuf.Type.getDefaultInstance();
      private int returnTypeId_;
      private ProtoBuf.Type returnType_ = ProtoBuf.Type.getDefaultInstance();
      private int setterFlags_;
      private ProtoBuf.ValueParameter setterValueParameter_ = ProtoBuf.ValueParameter.getDefaultInstance();
      private List<ProtoBuf.TypeParameter> typeParameter_ = Collections.emptyList();
      private List<Integer> versionRequirement_ = Collections.emptyList();
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void ensureTypeParameterIsMutable()
      {
        if ((this.bitField0_ & 0x20) != 32)
        {
          this.typeParameter_ = new ArrayList(this.typeParameter_);
          this.bitField0_ |= 0x20;
        }
      }
      
      private void ensureVersionRequirementIsMutable()
      {
        if ((this.bitField0_ & 0x800) != 2048)
        {
          this.versionRequirement_ = new ArrayList(this.versionRequirement_);
          this.bitField0_ |= 0x800;
        }
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public ProtoBuf.Property build()
      {
        ProtoBuf.Property localProperty = buildPartial();
        if (localProperty.isInitialized()) {
          return localProperty;
        }
        throw newUninitializedMessageException(localProperty);
      }
      
      public ProtoBuf.Property buildPartial()
      {
        ProtoBuf.Property localProperty = new ProtoBuf.Property(this, null);
        int i = this.bitField0_;
        int j = 1;
        if ((i & 0x1) != 1) {
          j = 0;
        }
        ProtoBuf.Property.access$15202(localProperty, this.flags_);
        int k = j;
        if ((i & 0x2) == 2) {
          k = j | 0x2;
        }
        ProtoBuf.Property.access$15302(localProperty, this.oldFlags_);
        j = k;
        if ((i & 0x4) == 4) {
          j = k | 0x4;
        }
        ProtoBuf.Property.access$15402(localProperty, this.name_);
        k = j;
        if ((i & 0x8) == 8) {
          k = j | 0x8;
        }
        ProtoBuf.Property.access$15502(localProperty, this.returnType_);
        j = k;
        if ((i & 0x10) == 16) {
          j = k | 0x10;
        }
        ProtoBuf.Property.access$15602(localProperty, this.returnTypeId_);
        if ((this.bitField0_ & 0x20) == 32)
        {
          this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
          this.bitField0_ &= 0xFFFFFFDF;
        }
        ProtoBuf.Property.access$15702(localProperty, this.typeParameter_);
        int m = j;
        if ((i & 0x40) == 64) {
          m = j | 0x20;
        }
        ProtoBuf.Property.access$15802(localProperty, this.receiverType_);
        k = m;
        if ((i & 0x80) == 128) {
          k = m | 0x40;
        }
        ProtoBuf.Property.access$15902(localProperty, this.receiverTypeId_);
        j = k;
        if ((i & 0x100) == 256) {
          j = k | 0x80;
        }
        ProtoBuf.Property.access$16002(localProperty, this.setterValueParameter_);
        k = j;
        if ((i & 0x200) == 512) {
          k = j | 0x100;
        }
        ProtoBuf.Property.access$16102(localProperty, this.getterFlags_);
        j = k;
        if ((i & 0x400) == 1024) {
          j = k | 0x200;
        }
        ProtoBuf.Property.access$16202(localProperty, this.setterFlags_);
        if ((this.bitField0_ & 0x800) == 2048)
        {
          this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
          this.bitField0_ &= 0xF7FF;
        }
        ProtoBuf.Property.access$16302(localProperty, this.versionRequirement_);
        ProtoBuf.Property.access$16402(localProperty, j);
        return localProperty;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public ProtoBuf.Property getDefaultInstanceForType()
      {
        return ProtoBuf.Property.getDefaultInstance();
      }
      
      public ProtoBuf.Type getReceiverType()
      {
        return this.receiverType_;
      }
      
      public ProtoBuf.Type getReturnType()
      {
        return this.returnType_;
      }
      
      public ProtoBuf.ValueParameter getSetterValueParameter()
      {
        return this.setterValueParameter_;
      }
      
      public ProtoBuf.TypeParameter getTypeParameter(int paramInt)
      {
        return (ProtoBuf.TypeParameter)this.typeParameter_.get(paramInt);
      }
      
      public int getTypeParameterCount()
      {
        return this.typeParameter_.size();
      }
      
      public boolean hasName()
      {
        boolean bool;
        if ((this.bitField0_ & 0x4) == 4) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasReceiverType()
      {
        boolean bool;
        if ((this.bitField0_ & 0x40) == 64) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasReturnType()
      {
        boolean bool;
        if ((this.bitField0_ & 0x8) == 8) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasSetterValueParameter()
      {
        boolean bool;
        if ((this.bitField0_ & 0x100) == 256) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public final boolean isInitialized()
      {
        if (!hasName()) {
          return false;
        }
        if ((hasReturnType()) && (!getReturnType().isInitialized())) {
          return false;
        }
        for (int i = 0; i < getTypeParameterCount(); i++) {
          if (!getTypeParameter(i).isInitialized()) {
            return false;
          }
        }
        if ((hasReceiverType()) && (!getReceiverType().isInitialized())) {
          return false;
        }
        if ((hasSetterValueParameter()) && (!getSetterValueParameter().isInitialized())) {
          return false;
        }
        return extensionsAreInitialized();
      }
      
      public Builder mergeFrom(ProtoBuf.Property paramProperty)
      {
        if (paramProperty == ProtoBuf.Property.getDefaultInstance()) {
          return this;
        }
        if (paramProperty.hasFlags()) {
          setFlags(paramProperty.getFlags());
        }
        if (paramProperty.hasOldFlags()) {
          setOldFlags(paramProperty.getOldFlags());
        }
        if (paramProperty.hasName()) {
          setName(paramProperty.getName());
        }
        if (paramProperty.hasReturnType()) {
          mergeReturnType(paramProperty.getReturnType());
        }
        if (paramProperty.hasReturnTypeId()) {
          setReturnTypeId(paramProperty.getReturnTypeId());
        }
        if (!paramProperty.typeParameter_.isEmpty()) {
          if (this.typeParameter_.isEmpty())
          {
            this.typeParameter_ = paramProperty.typeParameter_;
            this.bitField0_ &= 0xFFFFFFDF;
          }
          else
          {
            ensureTypeParameterIsMutable();
            this.typeParameter_.addAll(paramProperty.typeParameter_);
          }
        }
        if (paramProperty.hasReceiverType()) {
          mergeReceiverType(paramProperty.getReceiverType());
        }
        if (paramProperty.hasReceiverTypeId()) {
          setReceiverTypeId(paramProperty.getReceiverTypeId());
        }
        if (paramProperty.hasSetterValueParameter()) {
          mergeSetterValueParameter(paramProperty.getSetterValueParameter());
        }
        if (paramProperty.hasGetterFlags()) {
          setGetterFlags(paramProperty.getGetterFlags());
        }
        if (paramProperty.hasSetterFlags()) {
          setSetterFlags(paramProperty.getSetterFlags());
        }
        if (!paramProperty.versionRequirement_.isEmpty()) {
          if (this.versionRequirement_.isEmpty())
          {
            this.versionRequirement_ = paramProperty.versionRequirement_;
            this.bitField0_ &= 0xF7FF;
          }
          else
          {
            ensureVersionRequirementIsMutable();
            this.versionRequirement_.addAll(paramProperty.versionRequirement_);
          }
        }
        mergeExtensionFields(paramProperty);
        setUnknownFields(getUnknownFields().concat(paramProperty.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 356	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 362 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 173	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_1
        //   29: aload_3
        //   30: astore_2
        //   31: goto +15 -> 46
        //   34: astore_1
        //   35: aload_1
        //   36: invokevirtual 365	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   39: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property
        //   42: astore_2
        //   43: aload_1
        //   44: athrow
        //   45: astore_1
        //   46: aload_2
        //   47: ifnull +9 -> 56
        //   50: aload_0
        //   51: aload_2
        //   52: invokevirtual 173	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property$Builder;
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
      
      public Builder mergeReceiverType(ProtoBuf.Type paramType)
      {
        if (((this.bitField0_ & 0x40) == 64) && (this.receiverType_ != ProtoBuf.Type.getDefaultInstance())) {
          this.receiverType_ = ProtoBuf.Type.newBuilder(this.receiverType_).mergeFrom(paramType).buildPartial();
        } else {
          this.receiverType_ = paramType;
        }
        this.bitField0_ |= 0x40;
        return this;
      }
      
      public Builder mergeReturnType(ProtoBuf.Type paramType)
      {
        if (((this.bitField0_ & 0x8) == 8) && (this.returnType_ != ProtoBuf.Type.getDefaultInstance())) {
          this.returnType_ = ProtoBuf.Type.newBuilder(this.returnType_).mergeFrom(paramType).buildPartial();
        } else {
          this.returnType_ = paramType;
        }
        this.bitField0_ |= 0x8;
        return this;
      }
      
      public Builder mergeSetterValueParameter(ProtoBuf.ValueParameter paramValueParameter)
      {
        if (((this.bitField0_ & 0x100) == 256) && (this.setterValueParameter_ != ProtoBuf.ValueParameter.getDefaultInstance())) {
          this.setterValueParameter_ = ProtoBuf.ValueParameter.newBuilder(this.setterValueParameter_).mergeFrom(paramValueParameter).buildPartial();
        } else {
          this.setterValueParameter_ = paramValueParameter;
        }
        this.bitField0_ |= 0x100;
        return this;
      }
      
      public Builder setFlags(int paramInt)
      {
        this.bitField0_ |= 0x1;
        this.flags_ = paramInt;
        return this;
      }
      
      public Builder setGetterFlags(int paramInt)
      {
        this.bitField0_ |= 0x200;
        this.getterFlags_ = paramInt;
        return this;
      }
      
      public Builder setName(int paramInt)
      {
        this.bitField0_ |= 0x4;
        this.name_ = paramInt;
        return this;
      }
      
      public Builder setOldFlags(int paramInt)
      {
        this.bitField0_ |= 0x2;
        this.oldFlags_ = paramInt;
        return this;
      }
      
      public Builder setReceiverTypeId(int paramInt)
      {
        this.bitField0_ |= 0x80;
        this.receiverTypeId_ = paramInt;
        return this;
      }
      
      public Builder setReturnTypeId(int paramInt)
      {
        this.bitField0_ |= 0x10;
        this.returnTypeId_ = paramInt;
        return this;
      }
      
      public Builder setSetterFlags(int paramInt)
      {
        this.bitField0_ |= 0x400;
        this.setterFlags_ = paramInt;
        return this;
      }
    }
  }
  
  public static final class QualifiedNameTable
    extends GeneratedMessageLite
    implements ProtoBuf.QualifiedNameTableOrBuilder
  {
    public static Parser<QualifiedNameTable> PARSER = new AbstractParser()
    {
      public ProtoBuf.QualifiedNameTable parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new ProtoBuf.QualifiedNameTable(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final QualifiedNameTable defaultInstance;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private List<QualifiedName> qualifiedName_;
    private final ByteString unknownFields;
    
    static
    {
      QualifiedNameTable localQualifiedNameTable = new QualifiedNameTable(true);
      defaultInstance = localQualifiedNameTable;
      localQualifiedNameTable.initFields();
    }
    
    /* Error */
    private QualifiedNameTable(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 62	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: i2b
      //   7: putfield 64	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable:memoizedIsInitialized	B
      //   10: aload_0
      //   11: iconst_m1
      //   12: putfield 66	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable:memoizedSerializedSize	I
      //   15: aload_0
      //   16: invokespecial 55	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable:initFields	()V
      //   19: invokestatic 72	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   22: astore_3
      //   23: aload_3
      //   24: iconst_1
      //   25: invokestatic 78	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   28: astore 4
      //   30: iconst_0
      //   31: istore 5
      //   33: iconst_0
      //   34: istore 6
      //   36: iload 5
      //   38: ifne +272 -> 310
      //   41: iload 6
      //   43: istore 7
      //   45: iload 6
      //   47: istore 8
      //   49: iload 6
      //   51: istore 9
      //   53: aload_1
      //   54: invokevirtual 84	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   57: istore 10
      //   59: iload 10
      //   61: ifeq +145 -> 206
      //   64: iload 10
      //   66: bipush 10
      //   68: if_icmpeq +31 -> 99
      //   71: iload 6
      //   73: istore 7
      //   75: iload 6
      //   77: istore 8
      //   79: iload 6
      //   81: istore 9
      //   83: aload_0
      //   84: aload_1
      //   85: aload 4
      //   87: aload_2
      //   88: iload 10
      //   90: invokevirtual 88	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   93: ifne -57 -> 36
      //   96: goto +110 -> 206
      //   99: iload 6
      //   101: istore 10
      //   103: iload 6
      //   105: iconst_1
      //   106: iand
      //   107: iconst_1
      //   108: if_icmpeq +61 -> 169
      //   111: iload 6
      //   113: istore 7
      //   115: iload 6
      //   117: istore 8
      //   119: iload 6
      //   121: istore 9
      //   123: new 90	java/util/ArrayList
      //   126: astore 11
      //   128: iload 6
      //   130: istore 7
      //   132: iload 6
      //   134: istore 8
      //   136: iload 6
      //   138: istore 9
      //   140: aload 11
      //   142: invokespecial 91	java/util/ArrayList:<init>	()V
      //   145: iload 6
      //   147: istore 7
      //   149: iload 6
      //   151: istore 8
      //   153: iload 6
      //   155: istore 9
      //   157: aload_0
      //   158: aload 11
      //   160: putfield 93	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable:qualifiedName_	Ljava/util/List;
      //   163: iload 6
      //   165: iconst_1
      //   166: ior
      //   167: istore 10
      //   169: iload 10
      //   171: istore 7
      //   173: iload 10
      //   175: istore 8
      //   177: iload 10
      //   179: istore 9
      //   181: aload_0
      //   182: getfield 93	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable:qualifiedName_	Ljava/util/List;
      //   185: aload_1
      //   186: getstatic 94	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   189: aload_2
      //   190: invokevirtual 98	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   193: invokeinterface 104 2 0
      //   198: pop
      //   199: iload 10
      //   201: istore 6
      //   203: goto -167 -> 36
      //   206: iconst_1
      //   207: istore 5
      //   209: goto -173 -> 36
      //   212: astore_1
      //   213: goto +45 -> 258
      //   216: astore_1
      //   217: iload 8
      //   219: istore 7
      //   221: new 59	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   224: astore_2
      //   225: iload 8
      //   227: istore 7
      //   229: aload_2
      //   230: aload_1
      //   231: invokevirtual 108	java/io/IOException:getMessage	()Ljava/lang/String;
      //   234: invokespecial 111	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   237: iload 8
      //   239: istore 7
      //   241: aload_2
      //   242: aload_0
      //   243: invokevirtual 115	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   246: athrow
      //   247: astore_1
      //   248: iload 9
      //   250: istore 7
      //   252: aload_1
      //   253: aload_0
      //   254: invokevirtual 115	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   257: athrow
      //   258: iload 7
      //   260: iconst_1
      //   261: iand
      //   262: iconst_1
      //   263: if_icmpne +14 -> 277
      //   266: aload_0
      //   267: aload_0
      //   268: getfield 93	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable:qualifiedName_	Ljava/util/List;
      //   271: invokestatic 121	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   274: putfield 93	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable:qualifiedName_	Ljava/util/List;
      //   277: aload 4
      //   279: invokevirtual 124	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   282: aload_0
      //   283: aload_3
      //   284: invokevirtual 130	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   287: putfield 132	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   290: goto +14 -> 304
      //   293: astore_1
      //   294: aload_0
      //   295: aload_3
      //   296: invokevirtual 130	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   299: putfield 132	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   302: aload_1
      //   303: athrow
      //   304: aload_0
      //   305: invokevirtual 135	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable:makeExtensionsImmutable	()V
      //   308: aload_1
      //   309: athrow
      //   310: iload 6
      //   312: iconst_1
      //   313: iand
      //   314: iconst_1
      //   315: if_icmpne +14 -> 329
      //   318: aload_0
      //   319: aload_0
      //   320: getfield 93	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable:qualifiedName_	Ljava/util/List;
      //   323: invokestatic 121	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   326: putfield 93	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable:qualifiedName_	Ljava/util/List;
      //   329: aload 4
      //   331: invokevirtual 124	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   334: aload_0
      //   335: aload_3
      //   336: invokevirtual 130	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   339: putfield 132	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   342: goto +14 -> 356
      //   345: astore_1
      //   346: aload_0
      //   347: aload_3
      //   348: invokevirtual 130	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   351: putfield 132	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   354: aload_1
      //   355: athrow
      //   356: aload_0
      //   357: invokevirtual 135	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable:makeExtensionsImmutable	()V
      //   360: return
      //   361: astore_2
      //   362: goto -80 -> 282
      //   365: astore_1
      //   366: goto -32 -> 334
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	369	0	this	QualifiedNameTable
      //   0	369	1	paramCodedInputStream	CodedInputStream
      //   0	369	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   22	326	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   28	302	4	localCodedOutputStream	CodedOutputStream
      //   31	177	5	i	int
      //   34	280	6	j	int
      //   43	219	7	k	int
      //   47	191	8	m	int
      //   51	198	9	n	int
      //   57	143	10	i1	int
      //   126	33	11	localArrayList	ArrayList
      // Exception table:
      //   from	to	target	type
      //   53	59	212	finally
      //   83	96	212	finally
      //   123	128	212	finally
      //   140	145	212	finally
      //   157	163	212	finally
      //   181	199	212	finally
      //   221	225	212	finally
      //   229	237	212	finally
      //   241	247	212	finally
      //   252	258	212	finally
      //   53	59	216	java/io/IOException
      //   83	96	216	java/io/IOException
      //   123	128	216	java/io/IOException
      //   140	145	216	java/io/IOException
      //   157	163	216	java/io/IOException
      //   181	199	216	java/io/IOException
      //   53	59	247	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   83	96	247	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   123	128	247	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   140	145	247	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   157	163	247	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   181	199	247	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   277	282	293	finally
      //   329	334	345	finally
      //   277	282	361	java/io/IOException
      //   329	334	365	java/io/IOException
    }
    
    private QualifiedNameTable(GeneratedMessageLite.Builder paramBuilder)
    {
      super();
      this.unknownFields = paramBuilder.getUnknownFields();
    }
    
    private QualifiedNameTable(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static QualifiedNameTable getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.qualifiedName_ = Collections.emptyList();
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$1400();
    }
    
    public static Builder newBuilder(QualifiedNameTable paramQualifiedNameTable)
    {
      return newBuilder().mergeFrom(paramQualifiedNameTable);
    }
    
    public QualifiedNameTable getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public Parser<QualifiedNameTable> getParserForType()
    {
      return PARSER;
    }
    
    public QualifiedName getQualifiedName(int paramInt)
    {
      return (QualifiedName)this.qualifiedName_.get(paramInt);
    }
    
    public int getQualifiedNameCount()
    {
      return this.qualifiedName_.size();
    }
    
    public int getSerializedSize()
    {
      int i = this.memoizedSerializedSize;
      if (i != -1) {
        return i;
      }
      int j = 0;
      i = 0;
      while (j < this.qualifiedName_.size())
      {
        i += CodedOutputStream.computeMessageSize(1, (MessageLite)this.qualifiedName_.get(j));
        j++;
      }
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
      for (i = 0; i < getQualifiedNameCount(); i++) {
        if (!getQualifiedName(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
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
      for (int i = 0; i < this.qualifiedName_.size(); i++) {
        paramCodedOutputStream.writeMessage(1, (MessageLite)this.qualifiedName_.get(i));
      }
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Builder
      extends GeneratedMessageLite.Builder<ProtoBuf.QualifiedNameTable, Builder>
      implements ProtoBuf.QualifiedNameTableOrBuilder
    {
      private int bitField0_;
      private List<ProtoBuf.QualifiedNameTable.QualifiedName> qualifiedName_ = Collections.emptyList();
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void ensureQualifiedNameIsMutable()
      {
        if ((this.bitField0_ & 0x1) != 1)
        {
          this.qualifiedName_ = new ArrayList(this.qualifiedName_);
          this.bitField0_ |= 0x1;
        }
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public ProtoBuf.QualifiedNameTable build()
      {
        ProtoBuf.QualifiedNameTable localQualifiedNameTable = buildPartial();
        if (localQualifiedNameTable.isInitialized()) {
          return localQualifiedNameTable;
        }
        throw newUninitializedMessageException(localQualifiedNameTable);
      }
      
      public ProtoBuf.QualifiedNameTable buildPartial()
      {
        ProtoBuf.QualifiedNameTable localQualifiedNameTable = new ProtoBuf.QualifiedNameTable(this, null);
        if ((this.bitField0_ & 0x1) == 1)
        {
          this.qualifiedName_ = Collections.unmodifiableList(this.qualifiedName_);
          this.bitField0_ &= 0xFFFFFFFE;
        }
        ProtoBuf.QualifiedNameTable.access$1602(localQualifiedNameTable, this.qualifiedName_);
        return localQualifiedNameTable;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public ProtoBuf.QualifiedNameTable getDefaultInstanceForType()
      {
        return ProtoBuf.QualifiedNameTable.getDefaultInstance();
      }
      
      public ProtoBuf.QualifiedNameTable.QualifiedName getQualifiedName(int paramInt)
      {
        return (ProtoBuf.QualifiedNameTable.QualifiedName)this.qualifiedName_.get(paramInt);
      }
      
      public int getQualifiedNameCount()
      {
        return this.qualifiedName_.size();
      }
      
      public final boolean isInitialized()
      {
        for (int i = 0; i < getQualifiedNameCount(); i++) {
          if (!getQualifiedName(i).isInitialized()) {
            return false;
          }
        }
        return true;
      }
      
      public Builder mergeFrom(ProtoBuf.QualifiedNameTable paramQualifiedNameTable)
      {
        if (paramQualifiedNameTable == ProtoBuf.QualifiedNameTable.getDefaultInstance()) {
          return this;
        }
        if (!paramQualifiedNameTable.qualifiedName_.isEmpty()) {
          if (this.qualifiedName_.isEmpty())
          {
            this.qualifiedName_ = paramQualifiedNameTable.qualifiedName_;
            this.bitField0_ &= 0xFFFFFFFE;
          }
          else
          {
            ensureQualifiedNameIsMutable();
            this.qualifiedName_.addAll(paramQualifiedNameTable.qualifiedName_);
          }
        }
        setUnknownFields(getUnknownFields().concat(paramQualifiedNameTable.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 155	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 161 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 86	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_1
        //   29: aload_3
        //   30: astore_2
        //   31: goto +15 -> 46
        //   34: astore_1
        //   35: aload_1
        //   36: invokevirtual 164	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   39: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable
        //   42: astore_2
        //   43: aload_1
        //   44: athrow
        //   45: astore_1
        //   46: aload_2
        //   47: ifnull +9 -> 56
        //   50: aload_0
        //   51: aload_2
        //   52: invokevirtual 86	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$Builder;
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
    }
    
    public static final class QualifiedName
      extends GeneratedMessageLite
      implements ProtoBuf.QualifiedNameTable.QualifiedNameOrBuilder
    {
      public static Parser<QualifiedName> PARSER = new AbstractParser()
      {
        public ProtoBuf.QualifiedNameTable.QualifiedName parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
          throws InvalidProtocolBufferException
        {
          return new ProtoBuf.QualifiedNameTable.QualifiedName(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
        }
      };
      private static final QualifiedName defaultInstance;
      private int bitField0_;
      private Kind kind_;
      private byte memoizedIsInitialized = (byte)-1;
      private int memoizedSerializedSize = -1;
      private int parentQualifiedName_;
      private int shortName_;
      private final ByteString unknownFields;
      
      static
      {
        QualifiedName localQualifiedName = new QualifiedName(true);
        defaultInstance = localQualifiedName;
        localQualifiedName.initFields();
      }
      
      /* Error */
      private QualifiedName(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        // Byte code:
        //   0: aload_0
        //   1: invokespecial 60	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite:<init>	()V
        //   4: aload_0
        //   5: iconst_m1
        //   6: i2b
        //   7: putfield 62	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName:memoizedIsInitialized	B
        //   10: aload_0
        //   11: iconst_m1
        //   12: putfield 64	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName:memoizedSerializedSize	I
        //   15: aload_0
        //   16: invokespecial 53	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName:initFields	()V
        //   19: invokestatic 70	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
        //   22: astore_3
        //   23: aload_3
        //   24: iconst_1
        //   25: invokestatic 76	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
        //   28: astore 4
        //   30: iconst_0
        //   31: istore 5
        //   33: iload 5
        //   35: ifne +216 -> 251
        //   38: aload_1
        //   39: invokevirtual 82	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
        //   42: istore 6
        //   44: iload 6
        //   46: ifeq +136 -> 182
        //   49: iload 6
        //   51: bipush 8
        //   53: if_icmpeq +108 -> 161
        //   56: iload 6
        //   58: bipush 16
        //   60: if_icmpeq +80 -> 140
        //   63: iload 6
        //   65: bipush 24
        //   67: if_icmpeq +19 -> 86
        //   70: aload_0
        //   71: aload_1
        //   72: aload 4
        //   74: aload_2
        //   75: iload 6
        //   77: invokevirtual 86	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
        //   80: ifne -47 -> 33
        //   83: goto +99 -> 182
        //   86: aload_1
        //   87: invokevirtual 89	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readEnum	()I
        //   90: istore 7
        //   92: iload 7
        //   94: invokestatic 93	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName$Kind:valueOf	(I)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName$Kind;
        //   97: astore 8
        //   99: aload 8
        //   101: ifnonnull +20 -> 121
        //   104: aload 4
        //   106: iload 6
        //   108: invokevirtual 97	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:writeRawVarint32	(I)V
        //   111: aload 4
        //   113: iload 7
        //   115: invokevirtual 97	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:writeRawVarint32	(I)V
        //   118: goto -85 -> 33
        //   121: aload_0
        //   122: aload_0
        //   123: getfield 99	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName:bitField0_	I
        //   126: iconst_4
        //   127: ior
        //   128: putfield 99	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName:bitField0_	I
        //   131: aload_0
        //   132: aload 8
        //   134: putfield 101	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName:kind_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName$Kind;
        //   137: goto -104 -> 33
        //   140: aload_0
        //   141: aload_0
        //   142: getfield 99	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName:bitField0_	I
        //   145: iconst_2
        //   146: ior
        //   147: putfield 99	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName:bitField0_	I
        //   150: aload_0
        //   151: aload_1
        //   152: invokevirtual 104	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
        //   155: putfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName:shortName_	I
        //   158: goto -125 -> 33
        //   161: aload_0
        //   162: aload_0
        //   163: getfield 99	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName:bitField0_	I
        //   166: iconst_1
        //   167: ior
        //   168: putfield 99	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName:bitField0_	I
        //   171: aload_0
        //   172: aload_1
        //   173: invokevirtual 104	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
        //   176: putfield 108	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName:parentQualifiedName_	I
        //   179: goto -146 -> 33
        //   182: iconst_1
        //   183: istore 5
        //   185: goto -152 -> 33
        //   188: astore_1
        //   189: goto +29 -> 218
        //   192: astore_1
        //   193: new 57	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   196: astore_2
        //   197: aload_2
        //   198: aload_1
        //   199: invokevirtual 112	java/io/IOException:getMessage	()Ljava/lang/String;
        //   202: invokespecial 115	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
        //   205: aload_2
        //   206: aload_0
        //   207: invokevirtual 119	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
        //   210: athrow
        //   211: astore_1
        //   212: aload_1
        //   213: aload_0
        //   214: invokevirtual 119	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
        //   217: athrow
        //   218: aload 4
        //   220: invokevirtual 122	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
        //   223: aload_0
        //   224: aload_3
        //   225: invokevirtual 128	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   228: putfield 130	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   231: goto +14 -> 245
        //   234: astore_1
        //   235: aload_0
        //   236: aload_3
        //   237: invokevirtual 128	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   240: putfield 130	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   243: aload_1
        //   244: athrow
        //   245: aload_0
        //   246: invokevirtual 133	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName:makeExtensionsImmutable	()V
        //   249: aload_1
        //   250: athrow
        //   251: aload 4
        //   253: invokevirtual 122	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
        //   256: aload_0
        //   257: aload_3
        //   258: invokevirtual 128	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   261: putfield 130	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   264: goto +14 -> 278
        //   267: astore_1
        //   268: aload_0
        //   269: aload_3
        //   270: invokevirtual 128	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   273: putfield 130	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   276: aload_1
        //   277: athrow
        //   278: aload_0
        //   279: invokevirtual 133	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName:makeExtensionsImmutable	()V
        //   282: return
        //   283: astore_2
        //   284: goto -61 -> 223
        //   287: astore_1
        //   288: goto -32 -> 256
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	291	0	this	QualifiedName
        //   0	291	1	paramCodedInputStream	CodedInputStream
        //   0	291	2	paramExtensionRegistryLite	ExtensionRegistryLite
        //   22	248	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
        //   28	224	4	localCodedOutputStream	CodedOutputStream
        //   31	153	5	i	int
        //   42	65	6	j	int
        //   90	24	7	k	int
        //   97	36	8	localKind	Kind
        // Exception table:
        //   from	to	target	type
        //   38	44	188	finally
        //   70	83	188	finally
        //   86	99	188	finally
        //   104	118	188	finally
        //   121	137	188	finally
        //   140	158	188	finally
        //   161	179	188	finally
        //   193	211	188	finally
        //   212	218	188	finally
        //   38	44	192	java/io/IOException
        //   70	83	192	java/io/IOException
        //   86	99	192	java/io/IOException
        //   104	118	192	java/io/IOException
        //   121	137	192	java/io/IOException
        //   140	158	192	java/io/IOException
        //   161	179	192	java/io/IOException
        //   38	44	211	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   70	83	211	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   86	99	211	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   104	118	211	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   121	137	211	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   140	158	211	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   161	179	211	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   218	223	234	finally
        //   251	256	267	finally
        //   218	223	283	java/io/IOException
        //   251	256	287	java/io/IOException
      }
      
      private QualifiedName(GeneratedMessageLite.Builder paramBuilder)
      {
        super();
        this.unknownFields = paramBuilder.getUnknownFields();
      }
      
      private QualifiedName(boolean paramBoolean)
      {
        this.unknownFields = ByteString.EMPTY;
      }
      
      public static QualifiedName getDefaultInstance()
      {
        return defaultInstance;
      }
      
      private void initFields()
      {
        this.parentQualifiedName_ = -1;
        this.shortName_ = 0;
        this.kind_ = Kind.PACKAGE;
      }
      
      public static Builder newBuilder()
      {
        return Builder.access$700();
      }
      
      public static Builder newBuilder(QualifiedName paramQualifiedName)
      {
        return newBuilder().mergeFrom(paramQualifiedName);
      }
      
      public QualifiedName getDefaultInstanceForType()
      {
        return defaultInstance;
      }
      
      public Kind getKind()
      {
        return this.kind_;
      }
      
      public int getParentQualifiedName()
      {
        return this.parentQualifiedName_;
      }
      
      public Parser<QualifiedName> getParserForType()
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
          j = 0 + CodedOutputStream.computeInt32Size(1, this.parentQualifiedName_);
        }
        i = j;
        if ((this.bitField0_ & 0x2) == 2) {
          i = j + CodedOutputStream.computeInt32Size(2, this.shortName_);
        }
        j = i;
        if ((this.bitField0_ & 0x4) == 4) {
          j = i + CodedOutputStream.computeEnumSize(3, this.kind_.getNumber());
        }
        i = j + this.unknownFields.size();
        this.memoizedSerializedSize = i;
        return i;
      }
      
      public int getShortName()
      {
        return this.shortName_;
      }
      
      public boolean hasKind()
      {
        boolean bool;
        if ((this.bitField0_ & 0x4) == 4) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasParentQualifiedName()
      {
        int i = this.bitField0_;
        boolean bool = true;
        if ((i & 0x1) != 1) {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasShortName()
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
        if (!hasShortName())
        {
          this.memoizedIsInitialized = ((byte)0);
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
          paramCodedOutputStream.writeInt32(1, this.parentQualifiedName_);
        }
        if ((this.bitField0_ & 0x2) == 2) {
          paramCodedOutputStream.writeInt32(2, this.shortName_);
        }
        if ((this.bitField0_ & 0x4) == 4) {
          paramCodedOutputStream.writeEnum(3, this.kind_.getNumber());
        }
        paramCodedOutputStream.writeRawBytes(this.unknownFields);
      }
      
      public static final class Builder
        extends GeneratedMessageLite.Builder<ProtoBuf.QualifiedNameTable.QualifiedName, Builder>
        implements ProtoBuf.QualifiedNameTable.QualifiedNameOrBuilder
      {
        private int bitField0_;
        private ProtoBuf.QualifiedNameTable.QualifiedName.Kind kind_ = ProtoBuf.QualifiedNameTable.QualifiedName.Kind.PACKAGE;
        private int parentQualifiedName_ = -1;
        private int shortName_;
        
        private Builder()
        {
          maybeForceBuilderInitialization();
        }
        
        private static Builder create()
        {
          return new Builder();
        }
        
        private void maybeForceBuilderInitialization() {}
        
        public ProtoBuf.QualifiedNameTable.QualifiedName build()
        {
          ProtoBuf.QualifiedNameTable.QualifiedName localQualifiedName = buildPartial();
          if (localQualifiedName.isInitialized()) {
            return localQualifiedName;
          }
          throw newUninitializedMessageException(localQualifiedName);
        }
        
        public ProtoBuf.QualifiedNameTable.QualifiedName buildPartial()
        {
          ProtoBuf.QualifiedNameTable.QualifiedName localQualifiedName = new ProtoBuf.QualifiedNameTable.QualifiedName(this, null);
          int i = this.bitField0_;
          int j = 1;
          if ((i & 0x1) != 1) {
            j = 0;
          }
          ProtoBuf.QualifiedNameTable.QualifiedName.access$902(localQualifiedName, this.parentQualifiedName_);
          int k = j;
          if ((i & 0x2) == 2) {
            k = j | 0x2;
          }
          ProtoBuf.QualifiedNameTable.QualifiedName.access$1002(localQualifiedName, this.shortName_);
          j = k;
          if ((i & 0x4) == 4) {
            j = k | 0x4;
          }
          ProtoBuf.QualifiedNameTable.QualifiedName.access$1102(localQualifiedName, this.kind_);
          ProtoBuf.QualifiedNameTable.QualifiedName.access$1202(localQualifiedName, j);
          return localQualifiedName;
        }
        
        public Builder clone()
        {
          return create().mergeFrom(buildPartial());
        }
        
        public ProtoBuf.QualifiedNameTable.QualifiedName getDefaultInstanceForType()
        {
          return ProtoBuf.QualifiedNameTable.QualifiedName.getDefaultInstance();
        }
        
        public boolean hasShortName()
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
          return hasShortName();
        }
        
        public Builder mergeFrom(ProtoBuf.QualifiedNameTable.QualifiedName paramQualifiedName)
        {
          if (paramQualifiedName == ProtoBuf.QualifiedNameTable.QualifiedName.getDefaultInstance()) {
            return this;
          }
          if (paramQualifiedName.hasParentQualifiedName()) {
            setParentQualifiedName(paramQualifiedName.getParentQualifiedName());
          }
          if (paramQualifiedName.hasShortName()) {
            setShortName(paramQualifiedName.getShortName());
          }
          if (paramQualifiedName.hasKind()) {
            setKind(paramQualifiedName.getKind());
          }
          setUnknownFields(getUnknownFields().concat(paramQualifiedName.unknownFields));
          return this;
        }
        
        /* Error */
        public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
          throws IOException
        {
          // Byte code:
          //   0: aconst_null
          //   1: astore_3
          //   2: getstatic 161	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
          //   5: aload_1
          //   6: aload_2
          //   7: invokeinterface 167 3 0
          //   12: checkcast 14	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName
          //   15: astore_1
          //   16: aload_1
          //   17: ifnull +9 -> 26
          //   20: aload_0
          //   21: aload_1
          //   22: invokevirtual 93	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName$Builder;
          //   25: pop
          //   26: aload_0
          //   27: areturn
          //   28: astore_2
          //   29: aload_3
          //   30: astore_1
          //   31: goto +21 -> 52
          //   34: astore_1
          //   35: aload_1
          //   36: invokevirtual 170	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
          //   39: checkcast 14	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName
          //   42: astore_2
          //   43: aload_1
          //   44: athrow
          //   45: astore_1
          //   46: aload_2
          //   47: astore_3
          //   48: aload_1
          //   49: astore_2
          //   50: aload_3
          //   51: astore_1
          //   52: aload_1
          //   53: ifnull +9 -> 62
          //   56: aload_0
          //   57: aload_1
          //   58: invokevirtual 93	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$QualifiedNameTable$QualifiedName$Builder;
          //   61: pop
          //   62: aload_2
          //   63: athrow
          // Local variable table:
          //   start	length	slot	name	signature
          //   0	64	0	this	Builder
          //   0	64	1	paramCodedInputStream	CodedInputStream
          //   0	64	2	paramExtensionRegistryLite	ExtensionRegistryLite
          //   1	50	3	localExtensionRegistryLite	ExtensionRegistryLite
          // Exception table:
          //   from	to	target	type
          //   2	16	28	finally
          //   35	43	28	finally
          //   2	16	34	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
          //   43	45	45	finally
        }
        
        public Builder setKind(ProtoBuf.QualifiedNameTable.QualifiedName.Kind paramKind)
        {
          if (paramKind != null)
          {
            this.bitField0_ |= 0x4;
            this.kind_ = paramKind;
            return this;
          }
          throw null;
        }
        
        public Builder setParentQualifiedName(int paramInt)
        {
          this.bitField0_ |= 0x1;
          this.parentQualifiedName_ = paramInt;
          return this;
        }
        
        public Builder setShortName(int paramInt)
        {
          this.bitField0_ |= 0x2;
          this.shortName_ = paramInt;
          return this;
        }
      }
      
      public static enum Kind
        implements Internal.EnumLite
      {
        private static Internal.EnumLiteMap<Kind> internalValueMap = new Internal.EnumLiteMap()
        {
          public ProtoBuf.QualifiedNameTable.QualifiedName.Kind findValueByNumber(int paramAnonymousInt)
          {
            return ProtoBuf.QualifiedNameTable.QualifiedName.Kind.valueOf(paramAnonymousInt);
          }
        };
        private final int value;
        
        static
        {
          Kind localKind = new Kind("LOCAL", 2, 2, 2);
          LOCAL = localKind;
          $VALUES = new Kind[] { CLASS, PACKAGE, localKind };
        }
        
        private Kind(int paramInt1, int paramInt2)
        {
          this.value = paramInt2;
        }
        
        public static Kind valueOf(int paramInt)
        {
          if (paramInt != 0)
          {
            if (paramInt != 1)
            {
              if (paramInt != 2) {
                return null;
              }
              return LOCAL;
            }
            return PACKAGE;
          }
          return CLASS;
        }
        
        public final int getNumber()
        {
          return this.value;
        }
      }
    }
  }
  
  public static final class StringTable
    extends GeneratedMessageLite
    implements ProtoBuf.StringTableOrBuilder
  {
    public static Parser<StringTable> PARSER = new AbstractParser()
    {
      public ProtoBuf.StringTable parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new ProtoBuf.StringTable(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final StringTable defaultInstance;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private LazyStringList string_;
    private final ByteString unknownFields;
    
    static
    {
      StringTable localStringTable = new StringTable(true);
      defaultInstance = localStringTable;
      localStringTable.initFields();
    }
    
    /* Error */
    private StringTable(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 49	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: i2b
      //   7: putfield 51	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable:memoizedIsInitialized	B
      //   10: aload_0
      //   11: iconst_m1
      //   12: putfield 53	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable:memoizedSerializedSize	I
      //   15: aload_0
      //   16: invokespecial 42	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable:initFields	()V
      //   19: invokestatic 59	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   22: astore_3
      //   23: aload_3
      //   24: iconst_1
      //   25: invokestatic 65	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   28: astore 4
      //   30: iconst_0
      //   31: istore 5
      //   33: iconst_0
      //   34: istore 6
      //   36: iload 5
      //   38: ifne +285 -> 323
      //   41: iload 6
      //   43: istore 7
      //   45: iload 6
      //   47: istore 8
      //   49: iload 6
      //   51: istore 9
      //   53: aload_1
      //   54: invokevirtual 71	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   57: istore 10
      //   59: iload 10
      //   61: ifeq +156 -> 217
      //   64: iload 10
      //   66: bipush 10
      //   68: if_icmpeq +31 -> 99
      //   71: iload 6
      //   73: istore 7
      //   75: iload 6
      //   77: istore 8
      //   79: iload 6
      //   81: istore 9
      //   83: aload_0
      //   84: aload_1
      //   85: aload 4
      //   87: aload_2
      //   88: iload 10
      //   90: invokevirtual 75	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   93: ifne -57 -> 36
      //   96: goto +121 -> 217
      //   99: iload 6
      //   101: istore 7
      //   103: iload 6
      //   105: istore 8
      //   107: iload 6
      //   109: istore 9
      //   111: aload_1
      //   112: invokevirtual 79	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readBytes	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   115: astore 11
      //   117: iload 6
      //   119: istore 10
      //   121: iload 6
      //   123: iconst_1
      //   124: iand
      //   125: iconst_1
      //   126: if_icmpeq +61 -> 187
      //   129: iload 6
      //   131: istore 7
      //   133: iload 6
      //   135: istore 8
      //   137: iload 6
      //   139: istore 9
      //   141: new 81	kotlin/reflect/jvm/internal/impl/protobuf/LazyStringArrayList
      //   144: astore 12
      //   146: iload 6
      //   148: istore 7
      //   150: iload 6
      //   152: istore 8
      //   154: iload 6
      //   156: istore 9
      //   158: aload 12
      //   160: invokespecial 82	kotlin/reflect/jvm/internal/impl/protobuf/LazyStringArrayList:<init>	()V
      //   163: iload 6
      //   165: istore 7
      //   167: iload 6
      //   169: istore 8
      //   171: iload 6
      //   173: istore 9
      //   175: aload_0
      //   176: aload 12
      //   178: putfield 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable:string_	Lkotlin/reflect/jvm/internal/impl/protobuf/LazyStringList;
      //   181: iload 6
      //   183: iconst_1
      //   184: ior
      //   185: istore 10
      //   187: iload 10
      //   189: istore 7
      //   191: iload 10
      //   193: istore 8
      //   195: iload 10
      //   197: istore 9
      //   199: aload_0
      //   200: getfield 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable:string_	Lkotlin/reflect/jvm/internal/impl/protobuf/LazyStringList;
      //   203: aload 11
      //   205: invokeinterface 90 2 0
      //   210: iload 10
      //   212: istore 6
      //   214: goto -178 -> 36
      //   217: iconst_1
      //   218: istore 5
      //   220: goto -184 -> 36
      //   223: astore_1
      //   224: goto +45 -> 269
      //   227: astore_1
      //   228: iload 8
      //   230: istore 7
      //   232: new 46	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   235: astore_2
      //   236: iload 8
      //   238: istore 7
      //   240: aload_2
      //   241: aload_1
      //   242: invokevirtual 94	java/io/IOException:getMessage	()Ljava/lang/String;
      //   245: invokespecial 97	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   248: iload 8
      //   250: istore 7
      //   252: aload_2
      //   253: aload_0
      //   254: invokevirtual 101	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   257: athrow
      //   258: astore_1
      //   259: iload 9
      //   261: istore 7
      //   263: aload_1
      //   264: aload_0
      //   265: invokevirtual 101	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   268: athrow
      //   269: iload 7
      //   271: iconst_1
      //   272: iand
      //   273: iconst_1
      //   274: if_icmpne +16 -> 290
      //   277: aload_0
      //   278: aload_0
      //   279: getfield 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable:string_	Lkotlin/reflect/jvm/internal/impl/protobuf/LazyStringList;
      //   282: invokeinterface 105 1 0
      //   287: putfield 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable:string_	Lkotlin/reflect/jvm/internal/impl/protobuf/LazyStringList;
      //   290: aload 4
      //   292: invokevirtual 108	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   295: aload_0
      //   296: aload_3
      //   297: invokevirtual 113	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   300: putfield 115	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   303: goto +14 -> 317
      //   306: astore_1
      //   307: aload_0
      //   308: aload_3
      //   309: invokevirtual 113	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   312: putfield 115	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   315: aload_1
      //   316: athrow
      //   317: aload_0
      //   318: invokevirtual 118	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable:makeExtensionsImmutable	()V
      //   321: aload_1
      //   322: athrow
      //   323: iload 6
      //   325: iconst_1
      //   326: iand
      //   327: iconst_1
      //   328: if_icmpne +16 -> 344
      //   331: aload_0
      //   332: aload_0
      //   333: getfield 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable:string_	Lkotlin/reflect/jvm/internal/impl/protobuf/LazyStringList;
      //   336: invokeinterface 105 1 0
      //   341: putfield 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable:string_	Lkotlin/reflect/jvm/internal/impl/protobuf/LazyStringList;
      //   344: aload 4
      //   346: invokevirtual 108	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   349: aload_0
      //   350: aload_3
      //   351: invokevirtual 113	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   354: putfield 115	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   357: goto +14 -> 371
      //   360: astore_1
      //   361: aload_0
      //   362: aload_3
      //   363: invokevirtual 113	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   366: putfield 115	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   369: aload_1
      //   370: athrow
      //   371: aload_0
      //   372: invokevirtual 118	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable:makeExtensionsImmutable	()V
      //   375: return
      //   376: astore_2
      //   377: goto -82 -> 295
      //   380: astore_1
      //   381: goto -32 -> 349
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	384	0	this	StringTable
      //   0	384	1	paramCodedInputStream	CodedInputStream
      //   0	384	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   22	341	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   28	317	4	localCodedOutputStream	CodedOutputStream
      //   31	188	5	i	int
      //   34	293	6	j	int
      //   43	230	7	k	int
      //   47	202	8	m	int
      //   51	209	9	n	int
      //   57	154	10	i1	int
      //   115	89	11	localByteString	ByteString
      //   144	33	12	localLazyStringArrayList	LazyStringArrayList
      // Exception table:
      //   from	to	target	type
      //   53	59	223	finally
      //   83	96	223	finally
      //   111	117	223	finally
      //   141	146	223	finally
      //   158	163	223	finally
      //   175	181	223	finally
      //   199	210	223	finally
      //   232	236	223	finally
      //   240	248	223	finally
      //   252	258	223	finally
      //   263	269	223	finally
      //   53	59	227	java/io/IOException
      //   83	96	227	java/io/IOException
      //   111	117	227	java/io/IOException
      //   141	146	227	java/io/IOException
      //   158	163	227	java/io/IOException
      //   175	181	227	java/io/IOException
      //   199	210	227	java/io/IOException
      //   53	59	258	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   83	96	258	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   111	117	258	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   141	146	258	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   158	163	258	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   175	181	258	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   199	210	258	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   290	295	306	finally
      //   344	349	360	finally
      //   290	295	376	java/io/IOException
      //   344	349	380	java/io/IOException
    }
    
    private StringTable(GeneratedMessageLite.Builder paramBuilder)
    {
      super();
      this.unknownFields = paramBuilder.getUnknownFields();
    }
    
    private StringTable(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static StringTable getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.string_ = LazyStringArrayList.EMPTY;
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$100();
    }
    
    public static Builder newBuilder(StringTable paramStringTable)
    {
      return newBuilder().mergeFrom(paramStringTable);
    }
    
    public StringTable getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public Parser<StringTable> getParserForType()
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
      i = j;
      while (j < this.string_.size())
      {
        i += CodedOutputStream.computeBytesSizeNoTag(this.string_.getByteString(j));
        j++;
      }
      i = 0 + i + getStringList().size() * 1 + this.unknownFields.size();
      this.memoizedSerializedSize = i;
      return i;
    }
    
    public String getString(int paramInt)
    {
      return (String)this.string_.get(paramInt);
    }
    
    public ProtocolStringList getStringList()
    {
      return this.string_;
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
      for (int i = 0; i < this.string_.size(); i++) {
        paramCodedOutputStream.writeBytes(1, this.string_.getByteString(i));
      }
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Builder
      extends GeneratedMessageLite.Builder<ProtoBuf.StringTable, Builder>
      implements ProtoBuf.StringTableOrBuilder
    {
      private int bitField0_;
      private LazyStringList string_ = LazyStringArrayList.EMPTY;
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void ensureStringIsMutable()
      {
        if ((this.bitField0_ & 0x1) != 1)
        {
          this.string_ = new LazyStringArrayList(this.string_);
          this.bitField0_ |= 0x1;
        }
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public ProtoBuf.StringTable build()
      {
        ProtoBuf.StringTable localStringTable = buildPartial();
        if (localStringTable.isInitialized()) {
          return localStringTable;
        }
        throw newUninitializedMessageException(localStringTable);
      }
      
      public ProtoBuf.StringTable buildPartial()
      {
        ProtoBuf.StringTable localStringTable = new ProtoBuf.StringTable(this, null);
        if ((this.bitField0_ & 0x1) == 1)
        {
          this.string_ = this.string_.getUnmodifiableView();
          this.bitField0_ &= 0xFFFFFFFE;
        }
        ProtoBuf.StringTable.access$302(localStringTable, this.string_);
        return localStringTable;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public ProtoBuf.StringTable getDefaultInstanceForType()
      {
        return ProtoBuf.StringTable.getDefaultInstance();
      }
      
      public final boolean isInitialized()
      {
        return true;
      }
      
      public Builder mergeFrom(ProtoBuf.StringTable paramStringTable)
      {
        if (paramStringTable == ProtoBuf.StringTable.getDefaultInstance()) {
          return this;
        }
        if (!paramStringTable.string_.isEmpty()) {
          if (this.string_.isEmpty())
          {
            this.string_ = paramStringTable.string_;
            this.bitField0_ &= 0xFFFFFFFE;
          }
          else
          {
            ensureStringIsMutable();
            this.string_.addAll(paramStringTable.string_);
          }
        }
        setUnknownFields(getUnknownFields().concat(paramStringTable.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 133	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 139 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable$Builder;
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
        //   39: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable
        //   42: astore_1
        //   43: aload_2
        //   44: athrow
        //   45: astore_2
        //   46: aload_1
        //   47: ifnull +9 -> 56
        //   50: aload_0
        //   51: aload_1
        //   52: invokevirtual 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$StringTable$Builder;
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
    }
  }
  
  public static final class Type
    extends GeneratedMessageLite.ExtendableMessage<Type>
    implements ProtoBuf.TypeOrBuilder
  {
    public static Parser<Type> PARSER = new AbstractParser()
    {
      public ProtoBuf.Type parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new ProtoBuf.Type(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final Type defaultInstance;
    private int abbreviatedTypeId_;
    private Type abbreviatedType_;
    private List<Argument> argument_;
    private int bitField0_;
    private int className_;
    private int flags_;
    private int flexibleTypeCapabilitiesId_;
    private int flexibleUpperBoundId_;
    private Type flexibleUpperBound_;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private boolean nullable_;
    private int outerTypeId_;
    private Type outerType_;
    private int typeAliasName_;
    private int typeParameterName_;
    private int typeParameter_;
    private final ByteString unknownFields;
    
    static
    {
      Type localType = new Type(true);
      defaultInstance = localType;
      localType.initFields();
    }
    
    /* Error */
    private Type(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 78	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite$ExtendableMessage:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: i2b
      //   7: putfield 80	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:memoizedIsInitialized	B
      //   10: aload_0
      //   11: iconst_m1
      //   12: putfield 82	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:memoizedSerializedSize	I
      //   15: aload_0
      //   16: invokespecial 71	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:initFields	()V
      //   19: invokestatic 88	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   22: astore_3
      //   23: aload_3
      //   24: iconst_1
      //   25: invokestatic 94	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   28: astore 4
      //   30: iconst_0
      //   31: istore 5
      //   33: iconst_0
      //   34: istore 6
      //   36: iload 5
      //   38: ifne +1361 -> 1399
      //   41: iload 6
      //   43: istore 7
      //   45: iload 6
      //   47: istore 8
      //   49: iload 6
      //   51: istore 9
      //   53: aload_1
      //   54: invokevirtual 100	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   57: istore 10
      //   59: aconst_null
      //   60: astore 11
      //   62: aconst_null
      //   63: astore 12
      //   65: aconst_null
      //   66: astore 13
      //   68: iload 10
      //   70: lookupswitch	default:+130->200, 0:+1217->1287, 8:+1170->1240, 18:+1063->1133, 24:+1018->1088, 32:+973->1043, 42:+812->882, 48:+766->836, 56:+720->790, 64:+674->744, 72:+628->698, 82:+461->531, 88:+414->484, 96:+367->437, 106:+204->274, 112:+157->227
      //   200: iload 6
      //   202: istore 7
      //   204: iload 6
      //   206: istore 8
      //   208: iload 6
      //   210: istore 9
      //   212: aload_0
      //   213: aload_1
      //   214: aload 4
      //   216: aload_2
      //   217: iload 10
      //   219: invokevirtual 104	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   222: istore 14
      //   224: goto +1069 -> 1293
      //   227: iload 6
      //   229: istore 7
      //   231: iload 6
      //   233: istore 8
      //   235: iload 6
      //   237: istore 9
      //   239: aload_0
      //   240: aload_0
      //   241: getfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   244: sipush 2048
      //   247: ior
      //   248: putfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   251: iload 6
      //   253: istore 7
      //   255: iload 6
      //   257: istore 8
      //   259: iload 6
      //   261: istore 9
      //   263: aload_0
      //   264: aload_1
      //   265: invokevirtual 109	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   268: putfield 111	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:abbreviatedTypeId_	I
      //   271: goto -235 -> 36
      //   274: iload 6
      //   276: istore 7
      //   278: iload 6
      //   280: istore 8
      //   282: iload 6
      //   284: istore 9
      //   286: aload_0
      //   287: getfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   290: sipush 1024
      //   293: iand
      //   294: sipush 1024
      //   297: if_icmpne +24 -> 321
      //   300: iload 6
      //   302: istore 7
      //   304: iload 6
      //   306: istore 8
      //   308: iload 6
      //   310: istore 9
      //   312: aload_0
      //   313: getfield 113	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:abbreviatedType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   316: invokevirtual 117	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   319: astore 13
      //   321: iload 6
      //   323: istore 7
      //   325: iload 6
      //   327: istore 8
      //   329: iload 6
      //   331: istore 9
      //   333: aload_1
      //   334: getstatic 63	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   337: aload_2
      //   338: invokevirtual 121	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   341: checkcast 2	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type
      //   344: astore 11
      //   346: iload 6
      //   348: istore 7
      //   350: iload 6
      //   352: istore 8
      //   354: iload 6
      //   356: istore 9
      //   358: aload_0
      //   359: aload 11
      //   361: putfield 113	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:abbreviatedType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   364: aload 13
      //   366: ifnull +44 -> 410
      //   369: iload 6
      //   371: istore 7
      //   373: iload 6
      //   375: istore 8
      //   377: iload 6
      //   379: istore 9
      //   381: aload 13
      //   383: aload 11
      //   385: invokevirtual 125	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   388: pop
      //   389: iload 6
      //   391: istore 7
      //   393: iload 6
      //   395: istore 8
      //   397: iload 6
      //   399: istore 9
      //   401: aload_0
      //   402: aload 13
      //   404: invokevirtual 129	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   407: putfield 113	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:abbreviatedType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   410: iload 6
      //   412: istore 7
      //   414: iload 6
      //   416: istore 8
      //   418: iload 6
      //   420: istore 9
      //   422: aload_0
      //   423: aload_0
      //   424: getfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   427: sipush 1024
      //   430: ior
      //   431: putfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   434: goto -398 -> 36
      //   437: iload 6
      //   439: istore 7
      //   441: iload 6
      //   443: istore 8
      //   445: iload 6
      //   447: istore 9
      //   449: aload_0
      //   450: aload_0
      //   451: getfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   454: sipush 128
      //   457: ior
      //   458: putfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   461: iload 6
      //   463: istore 7
      //   465: iload 6
      //   467: istore 8
      //   469: iload 6
      //   471: istore 9
      //   473: aload_0
      //   474: aload_1
      //   475: invokevirtual 109	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   478: putfield 131	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:typeAliasName_	I
      //   481: goto -445 -> 36
      //   484: iload 6
      //   486: istore 7
      //   488: iload 6
      //   490: istore 8
      //   492: iload 6
      //   494: istore 9
      //   496: aload_0
      //   497: aload_0
      //   498: getfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   501: sipush 512
      //   504: ior
      //   505: putfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   508: iload 6
      //   510: istore 7
      //   512: iload 6
      //   514: istore 8
      //   516: iload 6
      //   518: istore 9
      //   520: aload_0
      //   521: aload_1
      //   522: invokevirtual 109	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   525: putfield 133	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:outerTypeId_	I
      //   528: goto -492 -> 36
      //   531: aload 11
      //   533: astore 13
      //   535: iload 6
      //   537: istore 7
      //   539: iload 6
      //   541: istore 8
      //   543: iload 6
      //   545: istore 9
      //   547: aload_0
      //   548: getfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   551: sipush 256
      //   554: iand
      //   555: sipush 256
      //   558: if_icmpne +24 -> 582
      //   561: iload 6
      //   563: istore 7
      //   565: iload 6
      //   567: istore 8
      //   569: iload 6
      //   571: istore 9
      //   573: aload_0
      //   574: getfield 135	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:outerType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   577: invokevirtual 117	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   580: astore 13
      //   582: iload 6
      //   584: istore 7
      //   586: iload 6
      //   588: istore 8
      //   590: iload 6
      //   592: istore 9
      //   594: aload_1
      //   595: getstatic 63	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   598: aload_2
      //   599: invokevirtual 121	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   602: checkcast 2	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type
      //   605: astore 11
      //   607: iload 6
      //   609: istore 7
      //   611: iload 6
      //   613: istore 8
      //   615: iload 6
      //   617: istore 9
      //   619: aload_0
      //   620: aload 11
      //   622: putfield 135	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:outerType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   625: aload 13
      //   627: ifnull +44 -> 671
      //   630: iload 6
      //   632: istore 7
      //   634: iload 6
      //   636: istore 8
      //   638: iload 6
      //   640: istore 9
      //   642: aload 13
      //   644: aload 11
      //   646: invokevirtual 125	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   649: pop
      //   650: iload 6
      //   652: istore 7
      //   654: iload 6
      //   656: istore 8
      //   658: iload 6
      //   660: istore 9
      //   662: aload_0
      //   663: aload 13
      //   665: invokevirtual 129	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   668: putfield 135	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:outerType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   671: iload 6
      //   673: istore 7
      //   675: iload 6
      //   677: istore 8
      //   679: iload 6
      //   681: istore 9
      //   683: aload_0
      //   684: aload_0
      //   685: getfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   688: sipush 256
      //   691: ior
      //   692: putfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   695: goto -659 -> 36
      //   698: iload 6
      //   700: istore 7
      //   702: iload 6
      //   704: istore 8
      //   706: iload 6
      //   708: istore 9
      //   710: aload_0
      //   711: aload_0
      //   712: getfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   715: bipush 64
      //   717: ior
      //   718: putfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   721: iload 6
      //   723: istore 7
      //   725: iload 6
      //   727: istore 8
      //   729: iload 6
      //   731: istore 9
      //   733: aload_0
      //   734: aload_1
      //   735: invokevirtual 109	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   738: putfield 137	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:typeParameterName_	I
      //   741: goto -705 -> 36
      //   744: iload 6
      //   746: istore 7
      //   748: iload 6
      //   750: istore 8
      //   752: iload 6
      //   754: istore 9
      //   756: aload_0
      //   757: aload_0
      //   758: getfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   761: bipush 8
      //   763: ior
      //   764: putfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   767: iload 6
      //   769: istore 7
      //   771: iload 6
      //   773: istore 8
      //   775: iload 6
      //   777: istore 9
      //   779: aload_0
      //   780: aload_1
      //   781: invokevirtual 109	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   784: putfield 139	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:flexibleUpperBoundId_	I
      //   787: goto -751 -> 36
      //   790: iload 6
      //   792: istore 7
      //   794: iload 6
      //   796: istore 8
      //   798: iload 6
      //   800: istore 9
      //   802: aload_0
      //   803: aload_0
      //   804: getfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   807: bipush 32
      //   809: ior
      //   810: putfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   813: iload 6
      //   815: istore 7
      //   817: iload 6
      //   819: istore 8
      //   821: iload 6
      //   823: istore 9
      //   825: aload_0
      //   826: aload_1
      //   827: invokevirtual 109	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   830: putfield 141	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:typeParameter_	I
      //   833: goto -797 -> 36
      //   836: iload 6
      //   838: istore 7
      //   840: iload 6
      //   842: istore 8
      //   844: iload 6
      //   846: istore 9
      //   848: aload_0
      //   849: aload_0
      //   850: getfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   853: bipush 16
      //   855: ior
      //   856: putfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   859: iload 6
      //   861: istore 7
      //   863: iload 6
      //   865: istore 8
      //   867: iload 6
      //   869: istore 9
      //   871: aload_0
      //   872: aload_1
      //   873: invokevirtual 109	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   876: putfield 143	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:className_	I
      //   879: goto -843 -> 36
      //   882: aload 12
      //   884: astore 13
      //   886: iload 6
      //   888: istore 7
      //   890: iload 6
      //   892: istore 8
      //   894: iload 6
      //   896: istore 9
      //   898: aload_0
      //   899: getfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   902: iconst_4
      //   903: iand
      //   904: iconst_4
      //   905: if_icmpne +24 -> 929
      //   908: iload 6
      //   910: istore 7
      //   912: iload 6
      //   914: istore 8
      //   916: iload 6
      //   918: istore 9
      //   920: aload_0
      //   921: getfield 145	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:flexibleUpperBound_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   924: invokevirtual 117	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   927: astore 13
      //   929: iload 6
      //   931: istore 7
      //   933: iload 6
      //   935: istore 8
      //   937: iload 6
      //   939: istore 9
      //   941: aload_1
      //   942: getstatic 63	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   945: aload_2
      //   946: invokevirtual 121	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   949: checkcast 2	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type
      //   952: astore 11
      //   954: iload 6
      //   956: istore 7
      //   958: iload 6
      //   960: istore 8
      //   962: iload 6
      //   964: istore 9
      //   966: aload_0
      //   967: aload 11
      //   969: putfield 145	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:flexibleUpperBound_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   972: aload 13
      //   974: ifnull +44 -> 1018
      //   977: iload 6
      //   979: istore 7
      //   981: iload 6
      //   983: istore 8
      //   985: iload 6
      //   987: istore 9
      //   989: aload 13
      //   991: aload 11
      //   993: invokevirtual 125	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   996: pop
      //   997: iload 6
      //   999: istore 7
      //   1001: iload 6
      //   1003: istore 8
      //   1005: iload 6
      //   1007: istore 9
      //   1009: aload_0
      //   1010: aload 13
      //   1012: invokevirtual 129	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   1015: putfield 145	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:flexibleUpperBound_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   1018: iload 6
      //   1020: istore 7
      //   1022: iload 6
      //   1024: istore 8
      //   1026: iload 6
      //   1028: istore 9
      //   1030: aload_0
      //   1031: aload_0
      //   1032: getfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   1035: iconst_4
      //   1036: ior
      //   1037: putfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   1040: goto -1004 -> 36
      //   1043: iload 6
      //   1045: istore 7
      //   1047: iload 6
      //   1049: istore 8
      //   1051: iload 6
      //   1053: istore 9
      //   1055: aload_0
      //   1056: aload_0
      //   1057: getfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   1060: iconst_2
      //   1061: ior
      //   1062: putfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   1065: iload 6
      //   1067: istore 7
      //   1069: iload 6
      //   1071: istore 8
      //   1073: iload 6
      //   1075: istore 9
      //   1077: aload_0
      //   1078: aload_1
      //   1079: invokevirtual 109	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   1082: putfield 147	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:flexibleTypeCapabilitiesId_	I
      //   1085: goto -1049 -> 36
      //   1088: iload 6
      //   1090: istore 7
      //   1092: iload 6
      //   1094: istore 8
      //   1096: iload 6
      //   1098: istore 9
      //   1100: aload_0
      //   1101: aload_0
      //   1102: getfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   1105: iconst_1
      //   1106: ior
      //   1107: putfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   1110: iload 6
      //   1112: istore 7
      //   1114: iload 6
      //   1116: istore 8
      //   1118: iload 6
      //   1120: istore 9
      //   1122: aload_0
      //   1123: aload_1
      //   1124: invokevirtual 151	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readBool	()Z
      //   1127: putfield 153	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:nullable_	Z
      //   1130: goto -1094 -> 36
      //   1133: iload 6
      //   1135: istore 10
      //   1137: iload 6
      //   1139: iconst_1
      //   1140: iand
      //   1141: iconst_1
      //   1142: if_icmpeq +61 -> 1203
      //   1145: iload 6
      //   1147: istore 7
      //   1149: iload 6
      //   1151: istore 8
      //   1153: iload 6
      //   1155: istore 9
      //   1157: new 155	java/util/ArrayList
      //   1160: astore 13
      //   1162: iload 6
      //   1164: istore 7
      //   1166: iload 6
      //   1168: istore 8
      //   1170: iload 6
      //   1172: istore 9
      //   1174: aload 13
      //   1176: invokespecial 156	java/util/ArrayList:<init>	()V
      //   1179: iload 6
      //   1181: istore 7
      //   1183: iload 6
      //   1185: istore 8
      //   1187: iload 6
      //   1189: istore 9
      //   1191: aload_0
      //   1192: aload 13
      //   1194: putfield 158	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:argument_	Ljava/util/List;
      //   1197: iload 6
      //   1199: iconst_1
      //   1200: ior
      //   1201: istore 10
      //   1203: iload 10
      //   1205: istore 7
      //   1207: iload 10
      //   1209: istore 8
      //   1211: iload 10
      //   1213: istore 9
      //   1215: aload_0
      //   1216: getfield 158	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:argument_	Ljava/util/List;
      //   1219: aload_1
      //   1220: getstatic 159	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   1223: aload_2
      //   1224: invokevirtual 121	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   1227: invokeinterface 165 2 0
      //   1232: pop
      //   1233: iload 10
      //   1235: istore 6
      //   1237: goto -1201 -> 36
      //   1240: iload 6
      //   1242: istore 7
      //   1244: iload 6
      //   1246: istore 8
      //   1248: iload 6
      //   1250: istore 9
      //   1252: aload_0
      //   1253: aload_0
      //   1254: getfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   1257: sipush 4096
      //   1260: ior
      //   1261: putfield 106	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:bitField0_	I
      //   1264: iload 6
      //   1266: istore 7
      //   1268: iload 6
      //   1270: istore 8
      //   1272: iload 6
      //   1274: istore 9
      //   1276: aload_0
      //   1277: aload_1
      //   1278: invokevirtual 109	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   1281: putfield 167	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:flags_	I
      //   1284: goto -1248 -> 36
      //   1287: iconst_1
      //   1288: istore 5
      //   1290: goto -1254 -> 36
      //   1293: iload 14
      //   1295: ifne -1259 -> 36
      //   1298: goto -11 -> 1287
      //   1301: astore_1
      //   1302: goto +45 -> 1347
      //   1305: astore_2
      //   1306: iload 8
      //   1308: istore 7
      //   1310: new 75	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1313: astore_1
      //   1314: iload 8
      //   1316: istore 7
      //   1318: aload_1
      //   1319: aload_2
      //   1320: invokevirtual 171	java/io/IOException:getMessage	()Ljava/lang/String;
      //   1323: invokespecial 174	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   1326: iload 8
      //   1328: istore 7
      //   1330: aload_1
      //   1331: aload_0
      //   1332: invokevirtual 178	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   1335: athrow
      //   1336: astore_1
      //   1337: iload 9
      //   1339: istore 7
      //   1341: aload_1
      //   1342: aload_0
      //   1343: invokevirtual 178	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   1346: athrow
      //   1347: iload 7
      //   1349: iconst_1
      //   1350: iand
      //   1351: iconst_1
      //   1352: if_icmpne +14 -> 1366
      //   1355: aload_0
      //   1356: aload_0
      //   1357: getfield 158	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:argument_	Ljava/util/List;
      //   1360: invokestatic 184	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   1363: putfield 158	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:argument_	Ljava/util/List;
      //   1366: aload 4
      //   1368: invokevirtual 187	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   1371: aload_0
      //   1372: aload_3
      //   1373: invokevirtual 193	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1376: putfield 195	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1379: goto +14 -> 1393
      //   1382: astore_1
      //   1383: aload_0
      //   1384: aload_3
      //   1385: invokevirtual 193	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1388: putfield 195	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1391: aload_1
      //   1392: athrow
      //   1393: aload_0
      //   1394: invokevirtual 198	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:makeExtensionsImmutable	()V
      //   1397: aload_1
      //   1398: athrow
      //   1399: iload 6
      //   1401: iconst_1
      //   1402: iand
      //   1403: iconst_1
      //   1404: if_icmpne +14 -> 1418
      //   1407: aload_0
      //   1408: aload_0
      //   1409: getfield 158	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:argument_	Ljava/util/List;
      //   1412: invokestatic 184	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   1415: putfield 158	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:argument_	Ljava/util/List;
      //   1418: aload 4
      //   1420: invokevirtual 187	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   1423: aload_0
      //   1424: aload_3
      //   1425: invokevirtual 193	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1428: putfield 195	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1431: goto +14 -> 1445
      //   1434: astore_1
      //   1435: aload_0
      //   1436: aload_3
      //   1437: invokevirtual 193	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1440: putfield 195	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1443: aload_1
      //   1444: athrow
      //   1445: aload_0
      //   1446: invokevirtual 198	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:makeExtensionsImmutable	()V
      //   1449: return
      //   1450: astore_2
      //   1451: goto -80 -> 1371
      //   1454: astore_1
      //   1455: goto -32 -> 1423
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	1458	0	this	Type
      //   0	1458	1	paramCodedInputStream	CodedInputStream
      //   0	1458	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   22	1415	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   28	1391	4	localCodedOutputStream	CodedOutputStream
      //   31	1258	5	i	int
      //   34	1369	6	j	int
      //   43	1308	7	k	int
      //   47	1280	8	m	int
      //   51	1287	9	n	int
      //   57	1177	10	i1	int
      //   60	932	11	localType	Type
      //   63	820	12	localObject1	Object
      //   66	1127	13	localObject2	Object
      //   222	1072	14	bool	boolean
      // Exception table:
      //   from	to	target	type
      //   53	59	1301	finally
      //   212	224	1301	finally
      //   239	251	1301	finally
      //   263	271	1301	finally
      //   286	300	1301	finally
      //   312	321	1301	finally
      //   333	346	1301	finally
      //   358	364	1301	finally
      //   381	389	1301	finally
      //   401	410	1301	finally
      //   422	434	1301	finally
      //   449	461	1301	finally
      //   473	481	1301	finally
      //   496	508	1301	finally
      //   520	528	1301	finally
      //   547	561	1301	finally
      //   573	582	1301	finally
      //   594	607	1301	finally
      //   619	625	1301	finally
      //   642	650	1301	finally
      //   662	671	1301	finally
      //   683	695	1301	finally
      //   710	721	1301	finally
      //   733	741	1301	finally
      //   756	767	1301	finally
      //   779	787	1301	finally
      //   802	813	1301	finally
      //   825	833	1301	finally
      //   848	859	1301	finally
      //   871	879	1301	finally
      //   898	908	1301	finally
      //   920	929	1301	finally
      //   941	954	1301	finally
      //   966	972	1301	finally
      //   989	997	1301	finally
      //   1009	1018	1301	finally
      //   1030	1040	1301	finally
      //   1055	1065	1301	finally
      //   1077	1085	1301	finally
      //   1100	1110	1301	finally
      //   1122	1130	1301	finally
      //   1157	1162	1301	finally
      //   1174	1179	1301	finally
      //   1191	1197	1301	finally
      //   1215	1233	1301	finally
      //   1252	1264	1301	finally
      //   1276	1284	1301	finally
      //   1310	1314	1301	finally
      //   1318	1326	1301	finally
      //   1330	1336	1301	finally
      //   1341	1347	1301	finally
      //   53	59	1305	java/io/IOException
      //   212	224	1305	java/io/IOException
      //   239	251	1305	java/io/IOException
      //   263	271	1305	java/io/IOException
      //   286	300	1305	java/io/IOException
      //   312	321	1305	java/io/IOException
      //   333	346	1305	java/io/IOException
      //   358	364	1305	java/io/IOException
      //   381	389	1305	java/io/IOException
      //   401	410	1305	java/io/IOException
      //   422	434	1305	java/io/IOException
      //   449	461	1305	java/io/IOException
      //   473	481	1305	java/io/IOException
      //   496	508	1305	java/io/IOException
      //   520	528	1305	java/io/IOException
      //   547	561	1305	java/io/IOException
      //   573	582	1305	java/io/IOException
      //   594	607	1305	java/io/IOException
      //   619	625	1305	java/io/IOException
      //   642	650	1305	java/io/IOException
      //   662	671	1305	java/io/IOException
      //   683	695	1305	java/io/IOException
      //   710	721	1305	java/io/IOException
      //   733	741	1305	java/io/IOException
      //   756	767	1305	java/io/IOException
      //   779	787	1305	java/io/IOException
      //   802	813	1305	java/io/IOException
      //   825	833	1305	java/io/IOException
      //   848	859	1305	java/io/IOException
      //   871	879	1305	java/io/IOException
      //   898	908	1305	java/io/IOException
      //   920	929	1305	java/io/IOException
      //   941	954	1305	java/io/IOException
      //   966	972	1305	java/io/IOException
      //   989	997	1305	java/io/IOException
      //   1009	1018	1305	java/io/IOException
      //   1030	1040	1305	java/io/IOException
      //   1055	1065	1305	java/io/IOException
      //   1077	1085	1305	java/io/IOException
      //   1100	1110	1305	java/io/IOException
      //   1122	1130	1305	java/io/IOException
      //   1157	1162	1305	java/io/IOException
      //   1174	1179	1305	java/io/IOException
      //   1191	1197	1305	java/io/IOException
      //   1215	1233	1305	java/io/IOException
      //   1252	1264	1305	java/io/IOException
      //   1276	1284	1305	java/io/IOException
      //   53	59	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   212	224	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   239	251	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   263	271	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   286	300	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   312	321	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   333	346	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   358	364	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   381	389	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   401	410	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   422	434	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   449	461	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   473	481	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   496	508	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   520	528	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   547	561	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   573	582	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   594	607	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   619	625	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   642	650	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   662	671	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   683	695	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   710	721	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   733	741	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   756	767	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   779	787	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   802	813	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   825	833	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   848	859	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   871	879	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   898	908	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   920	929	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   941	954	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   966	972	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   989	997	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1009	1018	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1030	1040	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1055	1065	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1077	1085	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1100	1110	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1122	1130	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1157	1162	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1174	1179	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1191	1197	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1215	1233	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1252	1264	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1276	1284	1336	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1366	1371	1382	finally
      //   1418	1423	1434	finally
      //   1366	1371	1450	java/io/IOException
      //   1418	1423	1454	java/io/IOException
    }
    
    private Type(GeneratedMessageLite.ExtendableBuilder<Type, ?> paramExtendableBuilder)
    {
      super();
      this.unknownFields = paramExtendableBuilder.getUnknownFields();
    }
    
    private Type(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static Type getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.argument_ = Collections.emptyList();
      this.nullable_ = false;
      this.flexibleTypeCapabilitiesId_ = 0;
      this.flexibleUpperBound_ = getDefaultInstance();
      this.flexibleUpperBoundId_ = 0;
      this.className_ = 0;
      this.typeParameter_ = 0;
      this.typeParameterName_ = 0;
      this.typeAliasName_ = 0;
      this.outerType_ = getDefaultInstance();
      this.outerTypeId_ = 0;
      this.abbreviatedType_ = getDefaultInstance();
      this.abbreviatedTypeId_ = 0;
      this.flags_ = 0;
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$5700();
    }
    
    public static Builder newBuilder(Type paramType)
    {
      return newBuilder().mergeFrom(paramType);
    }
    
    public Type getAbbreviatedType()
    {
      return this.abbreviatedType_;
    }
    
    public int getAbbreviatedTypeId()
    {
      return this.abbreviatedTypeId_;
    }
    
    public Argument getArgument(int paramInt)
    {
      return (Argument)this.argument_.get(paramInt);
    }
    
    public int getArgumentCount()
    {
      return this.argument_.size();
    }
    
    public List<Argument> getArgumentList()
    {
      return this.argument_;
    }
    
    public int getClassName()
    {
      return this.className_;
    }
    
    public Type getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public int getFlags()
    {
      return this.flags_;
    }
    
    public int getFlexibleTypeCapabilitiesId()
    {
      return this.flexibleTypeCapabilitiesId_;
    }
    
    public Type getFlexibleUpperBound()
    {
      return this.flexibleUpperBound_;
    }
    
    public int getFlexibleUpperBoundId()
    {
      return this.flexibleUpperBoundId_;
    }
    
    public boolean getNullable()
    {
      return this.nullable_;
    }
    
    public Type getOuterType()
    {
      return this.outerType_;
    }
    
    public int getOuterTypeId()
    {
      return this.outerTypeId_;
    }
    
    public Parser<Type> getParserForType()
    {
      return PARSER;
    }
    
    public int getSerializedSize()
    {
      int i = this.memoizedSerializedSize;
      if (i != -1) {
        return i;
      }
      i = this.bitField0_;
      int j = 0;
      if ((i & 0x1000) == 4096) {
        i = CodedOutputStream.computeInt32Size(1, this.flags_) + 0;
      } else {
        i = 0;
      }
      while (j < this.argument_.size())
      {
        i += CodedOutputStream.computeMessageSize(2, (MessageLite)this.argument_.get(j));
        j++;
      }
      j = i;
      if ((this.bitField0_ & 0x1) == 1) {
        j = i + CodedOutputStream.computeBoolSize(3, this.nullable_);
      }
      i = j;
      if ((this.bitField0_ & 0x2) == 2) {
        i = j + CodedOutputStream.computeInt32Size(4, this.flexibleTypeCapabilitiesId_);
      }
      int k = i;
      if ((this.bitField0_ & 0x4) == 4) {
        k = i + CodedOutputStream.computeMessageSize(5, this.flexibleUpperBound_);
      }
      j = k;
      if ((this.bitField0_ & 0x10) == 16) {
        j = k + CodedOutputStream.computeInt32Size(6, this.className_);
      }
      i = j;
      if ((this.bitField0_ & 0x20) == 32) {
        i = j + CodedOutputStream.computeInt32Size(7, this.typeParameter_);
      }
      j = i;
      if ((this.bitField0_ & 0x8) == 8) {
        j = i + CodedOutputStream.computeInt32Size(8, this.flexibleUpperBoundId_);
      }
      k = j;
      if ((this.bitField0_ & 0x40) == 64) {
        k = j + CodedOutputStream.computeInt32Size(9, this.typeParameterName_);
      }
      i = k;
      if ((this.bitField0_ & 0x100) == 256) {
        i = k + CodedOutputStream.computeMessageSize(10, this.outerType_);
      }
      j = i;
      if ((this.bitField0_ & 0x200) == 512) {
        j = i + CodedOutputStream.computeInt32Size(11, this.outerTypeId_);
      }
      i = j;
      if ((this.bitField0_ & 0x80) == 128) {
        i = j + CodedOutputStream.computeInt32Size(12, this.typeAliasName_);
      }
      j = i;
      if ((this.bitField0_ & 0x400) == 1024) {
        j = i + CodedOutputStream.computeMessageSize(13, this.abbreviatedType_);
      }
      i = j;
      if ((this.bitField0_ & 0x800) == 2048) {
        i = j + CodedOutputStream.computeInt32Size(14, this.abbreviatedTypeId_);
      }
      i = i + extensionsSerializedSize() + this.unknownFields.size();
      this.memoizedSerializedSize = i;
      return i;
    }
    
    public int getTypeAliasName()
    {
      return this.typeAliasName_;
    }
    
    public int getTypeParameter()
    {
      return this.typeParameter_;
    }
    
    public int getTypeParameterName()
    {
      return this.typeParameterName_;
    }
    
    public boolean hasAbbreviatedType()
    {
      boolean bool;
      if ((this.bitField0_ & 0x400) == 1024) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasAbbreviatedTypeId()
    {
      boolean bool;
      if ((this.bitField0_ & 0x800) == 2048) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasClassName()
    {
      boolean bool;
      if ((this.bitField0_ & 0x10) == 16) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasFlags()
    {
      boolean bool;
      if ((this.bitField0_ & 0x1000) == 4096) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasFlexibleTypeCapabilitiesId()
    {
      boolean bool;
      if ((this.bitField0_ & 0x2) == 2) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasFlexibleUpperBound()
    {
      boolean bool;
      if ((this.bitField0_ & 0x4) == 4) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasFlexibleUpperBoundId()
    {
      boolean bool;
      if ((this.bitField0_ & 0x8) == 8) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasNullable()
    {
      int i = this.bitField0_;
      boolean bool = true;
      if ((i & 0x1) != 1) {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasOuterType()
    {
      boolean bool;
      if ((this.bitField0_ & 0x100) == 256) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasOuterTypeId()
    {
      boolean bool;
      if ((this.bitField0_ & 0x200) == 512) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasTypeAliasName()
    {
      boolean bool;
      if ((this.bitField0_ & 0x80) == 128) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasTypeParameter()
    {
      boolean bool;
      if ((this.bitField0_ & 0x20) == 32) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasTypeParameterName()
    {
      boolean bool;
      if ((this.bitField0_ & 0x40) == 64) {
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
      for (i = 0; i < getArgumentCount(); i++) {
        if (!getArgument(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
      }
      if ((hasFlexibleUpperBound()) && (!getFlexibleUpperBound().isInitialized()))
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      if ((hasOuterType()) && (!getOuterType().isInitialized()))
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      if ((hasAbbreviatedType()) && (!getAbbreviatedType().isInitialized()))
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      if (!extensionsAreInitialized())
      {
        this.memoizedIsInitialized = ((byte)0);
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
      GeneratedMessageLite.ExtendableMessage.ExtensionWriter localExtensionWriter = newExtensionWriter();
      if ((this.bitField0_ & 0x1000) == 4096) {
        paramCodedOutputStream.writeInt32(1, this.flags_);
      }
      for (int i = 0; i < this.argument_.size(); i++) {
        paramCodedOutputStream.writeMessage(2, (MessageLite)this.argument_.get(i));
      }
      if ((this.bitField0_ & 0x1) == 1) {
        paramCodedOutputStream.writeBool(3, this.nullable_);
      }
      if ((this.bitField0_ & 0x2) == 2) {
        paramCodedOutputStream.writeInt32(4, this.flexibleTypeCapabilitiesId_);
      }
      if ((this.bitField0_ & 0x4) == 4) {
        paramCodedOutputStream.writeMessage(5, this.flexibleUpperBound_);
      }
      if ((this.bitField0_ & 0x10) == 16) {
        paramCodedOutputStream.writeInt32(6, this.className_);
      }
      if ((this.bitField0_ & 0x20) == 32) {
        paramCodedOutputStream.writeInt32(7, this.typeParameter_);
      }
      if ((this.bitField0_ & 0x8) == 8) {
        paramCodedOutputStream.writeInt32(8, this.flexibleUpperBoundId_);
      }
      if ((this.bitField0_ & 0x40) == 64) {
        paramCodedOutputStream.writeInt32(9, this.typeParameterName_);
      }
      if ((this.bitField0_ & 0x100) == 256) {
        paramCodedOutputStream.writeMessage(10, this.outerType_);
      }
      if ((this.bitField0_ & 0x200) == 512) {
        paramCodedOutputStream.writeInt32(11, this.outerTypeId_);
      }
      if ((this.bitField0_ & 0x80) == 128) {
        paramCodedOutputStream.writeInt32(12, this.typeAliasName_);
      }
      if ((this.bitField0_ & 0x400) == 1024) {
        paramCodedOutputStream.writeMessage(13, this.abbreviatedType_);
      }
      if ((this.bitField0_ & 0x800) == 2048) {
        paramCodedOutputStream.writeInt32(14, this.abbreviatedTypeId_);
      }
      localExtensionWriter.writeUntil(200, paramCodedOutputStream);
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Argument
      extends GeneratedMessageLite
      implements ProtoBuf.Type.ArgumentOrBuilder
    {
      public static Parser<Argument> PARSER = new AbstractParser()
      {
        public ProtoBuf.Type.Argument parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
          throws InvalidProtocolBufferException
        {
          return new ProtoBuf.Type.Argument(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
        }
      };
      private static final Argument defaultInstance;
      private int bitField0_;
      private byte memoizedIsInitialized = (byte)-1;
      private int memoizedSerializedSize = -1;
      private Projection projection_;
      private int typeId_;
      private ProtoBuf.Type type_;
      private final ByteString unknownFields;
      
      static
      {
        Argument localArgument = new Argument(true);
        defaultInstance = localArgument;
        localArgument.initFields();
      }
      
      /* Error */
      private Argument(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        // Byte code:
        //   0: aload_0
        //   1: invokespecial 61	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite:<init>	()V
        //   4: aload_0
        //   5: iconst_m1
        //   6: i2b
        //   7: putfield 63	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:memoizedIsInitialized	B
        //   10: aload_0
        //   11: iconst_m1
        //   12: putfield 65	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:memoizedSerializedSize	I
        //   15: aload_0
        //   16: invokespecial 54	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:initFields	()V
        //   19: invokestatic 71	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
        //   22: astore_3
        //   23: aload_3
        //   24: iconst_1
        //   25: invokestatic 77	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
        //   28: astore 4
        //   30: iconst_0
        //   31: istore 5
        //   33: iload 5
        //   35: ifne +271 -> 306
        //   38: aload_1
        //   39: invokevirtual 83	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
        //   42: istore 6
        //   44: iload 6
        //   46: ifeq +191 -> 237
        //   49: iload 6
        //   51: bipush 8
        //   53: if_icmpeq +130 -> 183
        //   56: iload 6
        //   58: bipush 18
        //   60: if_icmpeq +47 -> 107
        //   63: iload 6
        //   65: bipush 24
        //   67: if_icmpeq +19 -> 86
        //   70: aload_0
        //   71: aload_1
        //   72: aload 4
        //   74: aload_2
        //   75: iload 6
        //   77: invokevirtual 87	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
        //   80: ifne -47 -> 33
        //   83: goto +154 -> 237
        //   86: aload_0
        //   87: aload_0
        //   88: getfield 89	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:bitField0_	I
        //   91: iconst_4
        //   92: ior
        //   93: putfield 89	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:bitField0_	I
        //   96: aload_0
        //   97: aload_1
        //   98: invokevirtual 92	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
        //   101: putfield 94	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:typeId_	I
        //   104: goto -71 -> 33
        //   107: aconst_null
        //   108: astore 7
        //   110: aload_0
        //   111: getfield 89	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:bitField0_	I
        //   114: iconst_2
        //   115: iand
        //   116: iconst_2
        //   117: if_icmpne +12 -> 129
        //   120: aload_0
        //   121: getfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:type_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
        //   124: invokevirtual 100	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
        //   127: astore 7
        //   129: aload_1
        //   130: getstatic 101	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   133: aload_2
        //   134: invokevirtual 105	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   137: checkcast 8	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type
        //   140: astore 8
        //   142: aload_0
        //   143: aload 8
        //   145: putfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:type_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
        //   148: aload 7
        //   150: ifnull +20 -> 170
        //   153: aload 7
        //   155: aload 8
        //   157: invokevirtual 111	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
        //   160: pop
        //   161: aload_0
        //   162: aload 7
        //   164: invokevirtual 115	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
        //   167: putfield 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:type_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
        //   170: aload_0
        //   171: aload_0
        //   172: getfield 89	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:bitField0_	I
        //   175: iconst_2
        //   176: ior
        //   177: putfield 89	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:bitField0_	I
        //   180: goto -147 -> 33
        //   183: aload_1
        //   184: invokevirtual 118	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readEnum	()I
        //   187: istore 9
        //   189: iload 9
        //   191: invokestatic 122	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument$Projection:valueOf	(I)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument$Projection;
        //   194: astore 7
        //   196: aload 7
        //   198: ifnonnull +20 -> 218
        //   201: aload 4
        //   203: iload 6
        //   205: invokevirtual 126	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:writeRawVarint32	(I)V
        //   208: aload 4
        //   210: iload 9
        //   212: invokevirtual 126	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:writeRawVarint32	(I)V
        //   215: goto -182 -> 33
        //   218: aload_0
        //   219: aload_0
        //   220: getfield 89	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:bitField0_	I
        //   223: iconst_1
        //   224: ior
        //   225: putfield 89	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:bitField0_	I
        //   228: aload_0
        //   229: aload 7
        //   231: putfield 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:projection_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument$Projection;
        //   234: goto -201 -> 33
        //   237: iconst_1
        //   238: istore 5
        //   240: goto -207 -> 33
        //   243: astore_1
        //   244: goto +29 -> 273
        //   247: astore_1
        //   248: new 58	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   251: astore_2
        //   252: aload_2
        //   253: aload_1
        //   254: invokevirtual 132	java/io/IOException:getMessage	()Ljava/lang/String;
        //   257: invokespecial 135	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
        //   260: aload_2
        //   261: aload_0
        //   262: invokevirtual 139	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
        //   265: athrow
        //   266: astore_1
        //   267: aload_1
        //   268: aload_0
        //   269: invokevirtual 139	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
        //   272: athrow
        //   273: aload 4
        //   275: invokevirtual 142	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
        //   278: aload_0
        //   279: aload_3
        //   280: invokevirtual 148	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   283: putfield 150	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   286: goto +14 -> 300
        //   289: astore_1
        //   290: aload_0
        //   291: aload_3
        //   292: invokevirtual 148	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   295: putfield 150	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   298: aload_1
        //   299: athrow
        //   300: aload_0
        //   301: invokevirtual 153	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:makeExtensionsImmutable	()V
        //   304: aload_1
        //   305: athrow
        //   306: aload 4
        //   308: invokevirtual 142	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
        //   311: aload_0
        //   312: aload_3
        //   313: invokevirtual 148	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   316: putfield 150	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   319: goto +14 -> 333
        //   322: astore_1
        //   323: aload_0
        //   324: aload_3
        //   325: invokevirtual 148	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   328: putfield 150	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
        //   331: aload_1
        //   332: athrow
        //   333: aload_0
        //   334: invokevirtual 153	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:makeExtensionsImmutable	()V
        //   337: return
        //   338: astore_2
        //   339: goto -61 -> 278
        //   342: astore_1
        //   343: goto -32 -> 311
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	346	0	this	Argument
        //   0	346	1	paramCodedInputStream	CodedInputStream
        //   0	346	2	paramExtensionRegistryLite	ExtensionRegistryLite
        //   22	303	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
        //   28	279	4	localCodedOutputStream	CodedOutputStream
        //   31	208	5	i	int
        //   42	162	6	j	int
        //   108	122	7	localObject	Object
        //   140	16	8	localType	ProtoBuf.Type
        //   187	24	9	k	int
        // Exception table:
        //   from	to	target	type
        //   38	44	243	finally
        //   70	83	243	finally
        //   86	104	243	finally
        //   110	129	243	finally
        //   129	148	243	finally
        //   153	170	243	finally
        //   170	180	243	finally
        //   183	196	243	finally
        //   201	215	243	finally
        //   218	234	243	finally
        //   248	266	243	finally
        //   267	273	243	finally
        //   38	44	247	java/io/IOException
        //   70	83	247	java/io/IOException
        //   86	104	247	java/io/IOException
        //   110	129	247	java/io/IOException
        //   129	148	247	java/io/IOException
        //   153	170	247	java/io/IOException
        //   170	180	247	java/io/IOException
        //   183	196	247	java/io/IOException
        //   201	215	247	java/io/IOException
        //   218	234	247	java/io/IOException
        //   38	44	266	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   70	83	266	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   86	104	266	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   110	129	266	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   129	148	266	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   153	170	266	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   170	180	266	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   183	196	266	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   201	215	266	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   218	234	266	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
        //   273	278	289	finally
        //   306	311	322	finally
        //   273	278	338	java/io/IOException
        //   306	311	342	java/io/IOException
      }
      
      private Argument(GeneratedMessageLite.Builder paramBuilder)
      {
        super();
        this.unknownFields = paramBuilder.getUnknownFields();
      }
      
      private Argument(boolean paramBoolean)
      {
        this.unknownFields = ByteString.EMPTY;
      }
      
      public static Argument getDefaultInstance()
      {
        return defaultInstance;
      }
      
      private void initFields()
      {
        this.projection_ = Projection.INV;
        this.type_ = ProtoBuf.Type.getDefaultInstance();
        this.typeId_ = 0;
      }
      
      public static Builder newBuilder()
      {
        return Builder.access$5000();
      }
      
      public static Builder newBuilder(Argument paramArgument)
      {
        return newBuilder().mergeFrom(paramArgument);
      }
      
      public Argument getDefaultInstanceForType()
      {
        return defaultInstance;
      }
      
      public Parser<Argument> getParserForType()
      {
        return PARSER;
      }
      
      public Projection getProjection()
      {
        return this.projection_;
      }
      
      public int getSerializedSize()
      {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
          return i;
        }
        int j = 0;
        if ((this.bitField0_ & 0x1) == 1) {
          j = 0 + CodedOutputStream.computeEnumSize(1, this.projection_.getNumber());
        }
        i = j;
        if ((this.bitField0_ & 0x2) == 2) {
          i = j + CodedOutputStream.computeMessageSize(2, this.type_);
        }
        j = i;
        if ((this.bitField0_ & 0x4) == 4) {
          j = i + CodedOutputStream.computeInt32Size(3, this.typeId_);
        }
        i = j + this.unknownFields.size();
        this.memoizedSerializedSize = i;
        return i;
      }
      
      public ProtoBuf.Type getType()
      {
        return this.type_;
      }
      
      public int getTypeId()
      {
        return this.typeId_;
      }
      
      public boolean hasProjection()
      {
        int i = this.bitField0_;
        boolean bool = true;
        if ((i & 0x1) != 1) {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasType()
      {
        boolean bool;
        if ((this.bitField0_ & 0x2) == 2) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasTypeId()
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
        if ((hasType()) && (!getType().isInitialized()))
        {
          this.memoizedIsInitialized = ((byte)0);
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
          paramCodedOutputStream.writeEnum(1, this.projection_.getNumber());
        }
        if ((this.bitField0_ & 0x2) == 2) {
          paramCodedOutputStream.writeMessage(2, this.type_);
        }
        if ((this.bitField0_ & 0x4) == 4) {
          paramCodedOutputStream.writeInt32(3, this.typeId_);
        }
        paramCodedOutputStream.writeRawBytes(this.unknownFields);
      }
      
      public static final class Builder
        extends GeneratedMessageLite.Builder<ProtoBuf.Type.Argument, Builder>
        implements ProtoBuf.Type.ArgumentOrBuilder
      {
        private int bitField0_;
        private ProtoBuf.Type.Argument.Projection projection_ = ProtoBuf.Type.Argument.Projection.INV;
        private int typeId_;
        private ProtoBuf.Type type_ = ProtoBuf.Type.getDefaultInstance();
        
        private Builder()
        {
          maybeForceBuilderInitialization();
        }
        
        private static Builder create()
        {
          return new Builder();
        }
        
        private void maybeForceBuilderInitialization() {}
        
        public ProtoBuf.Type.Argument build()
        {
          ProtoBuf.Type.Argument localArgument = buildPartial();
          if (localArgument.isInitialized()) {
            return localArgument;
          }
          throw newUninitializedMessageException(localArgument);
        }
        
        public ProtoBuf.Type.Argument buildPartial()
        {
          ProtoBuf.Type.Argument localArgument = new ProtoBuf.Type.Argument(this, null);
          int i = this.bitField0_;
          int j = 1;
          if ((i & 0x1) != 1) {
            j = 0;
          }
          ProtoBuf.Type.Argument.access$5202(localArgument, this.projection_);
          int k = j;
          if ((i & 0x2) == 2) {
            k = j | 0x2;
          }
          ProtoBuf.Type.Argument.access$5302(localArgument, this.type_);
          j = k;
          if ((i & 0x4) == 4) {
            j = k | 0x4;
          }
          ProtoBuf.Type.Argument.access$5402(localArgument, this.typeId_);
          ProtoBuf.Type.Argument.access$5502(localArgument, j);
          return localArgument;
        }
        
        public Builder clone()
        {
          return create().mergeFrom(buildPartial());
        }
        
        public ProtoBuf.Type.Argument getDefaultInstanceForType()
        {
          return ProtoBuf.Type.Argument.getDefaultInstance();
        }
        
        public ProtoBuf.Type getType()
        {
          return this.type_;
        }
        
        public boolean hasType()
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
          return (!hasType()) || (getType().isInitialized());
        }
        
        public Builder mergeFrom(ProtoBuf.Type.Argument paramArgument)
        {
          if (paramArgument == ProtoBuf.Type.Argument.getDefaultInstance()) {
            return this;
          }
          if (paramArgument.hasProjection()) {
            setProjection(paramArgument.getProjection());
          }
          if (paramArgument.hasType()) {
            mergeType(paramArgument.getType());
          }
          if (paramArgument.hasTypeId()) {
            setTypeId(paramArgument.getTypeId());
          }
          setUnknownFields(getUnknownFields().concat(paramArgument.unknownFields));
          return this;
        }
        
        /* Error */
        public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
          throws IOException
        {
          // Byte code:
          //   0: aconst_null
          //   1: astore_3
          //   2: getstatic 169	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
          //   5: aload_1
          //   6: aload_2
          //   7: invokeinterface 175 3 0
          //   12: checkcast 14	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument
          //   15: astore_1
          //   16: aload_1
          //   17: ifnull +9 -> 26
          //   20: aload_0
          //   21: aload_1
          //   22: invokevirtual 99	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument$Builder;
          //   25: pop
          //   26: aload_0
          //   27: areturn
          //   28: astore_2
          //   29: aload_3
          //   30: astore_1
          //   31: goto +15 -> 46
          //   34: astore_2
          //   35: aload_2
          //   36: invokevirtual 178	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
          //   39: checkcast 14	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument
          //   42: astore_1
          //   43: aload_2
          //   44: athrow
          //   45: astore_2
          //   46: aload_1
          //   47: ifnull +9 -> 56
          //   50: aload_0
          //   51: aload_1
          //   52: invokevirtual 99	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Argument$Builder;
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
        
        public Builder mergeType(ProtoBuf.Type paramType)
        {
          if (((this.bitField0_ & 0x2) == 2) && (this.type_ != ProtoBuf.Type.getDefaultInstance())) {
            this.type_ = ProtoBuf.Type.newBuilder(this.type_).mergeFrom(paramType).buildPartial();
          } else {
            this.type_ = paramType;
          }
          this.bitField0_ |= 0x2;
          return this;
        }
        
        public Builder setProjection(ProtoBuf.Type.Argument.Projection paramProjection)
        {
          if (paramProjection != null)
          {
            this.bitField0_ |= 0x1;
            this.projection_ = paramProjection;
            return this;
          }
          throw null;
        }
        
        public Builder setTypeId(int paramInt)
        {
          this.bitField0_ |= 0x4;
          this.typeId_ = paramInt;
          return this;
        }
      }
      
      public static enum Projection
        implements Internal.EnumLite
      {
        private static Internal.EnumLiteMap<Projection> internalValueMap = new Internal.EnumLiteMap()
        {
          public ProtoBuf.Type.Argument.Projection findValueByNumber(int paramAnonymousInt)
          {
            return ProtoBuf.Type.Argument.Projection.valueOf(paramAnonymousInt);
          }
        };
        private final int value;
        
        static
        {
          INV = new Projection("INV", 2, 2, 2);
          Projection localProjection = new Projection("STAR", 3, 3, 3);
          STAR = localProjection;
          $VALUES = new Projection[] { IN, OUT, INV, localProjection };
        }
        
        private Projection(int paramInt1, int paramInt2)
        {
          this.value = paramInt2;
        }
        
        public static Projection valueOf(int paramInt)
        {
          if (paramInt != 0)
          {
            if (paramInt != 1)
            {
              if (paramInt != 2)
              {
                if (paramInt != 3) {
                  return null;
                }
                return STAR;
              }
              return INV;
            }
            return OUT;
          }
          return IN;
        }
        
        public final int getNumber()
        {
          return this.value;
        }
      }
    }
    
    public static final class Builder
      extends GeneratedMessageLite.ExtendableBuilder<ProtoBuf.Type, Builder>
      implements ProtoBuf.TypeOrBuilder
    {
      private int abbreviatedTypeId_;
      private ProtoBuf.Type abbreviatedType_ = ProtoBuf.Type.getDefaultInstance();
      private List<ProtoBuf.Type.Argument> argument_ = Collections.emptyList();
      private int bitField0_;
      private int className_;
      private int flags_;
      private int flexibleTypeCapabilitiesId_;
      private int flexibleUpperBoundId_;
      private ProtoBuf.Type flexibleUpperBound_ = ProtoBuf.Type.getDefaultInstance();
      private boolean nullable_;
      private int outerTypeId_;
      private ProtoBuf.Type outerType_ = ProtoBuf.Type.getDefaultInstance();
      private int typeAliasName_;
      private int typeParameterName_;
      private int typeParameter_;
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void ensureArgumentIsMutable()
      {
        if ((this.bitField0_ & 0x1) != 1)
        {
          this.argument_ = new ArrayList(this.argument_);
          this.bitField0_ |= 0x1;
        }
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public ProtoBuf.Type build()
      {
        ProtoBuf.Type localType = buildPartial();
        if (localType.isInitialized()) {
          return localType;
        }
        throw newUninitializedMessageException(localType);
      }
      
      public ProtoBuf.Type buildPartial()
      {
        ProtoBuf.Type localType = new ProtoBuf.Type(this, null);
        int i = this.bitField0_;
        int j = 1;
        if ((i & 0x1) == 1)
        {
          this.argument_ = Collections.unmodifiableList(this.argument_);
          this.bitField0_ &= 0xFFFFFFFE;
        }
        ProtoBuf.Type.access$5902(localType, this.argument_);
        if ((i & 0x2) != 2) {
          j = 0;
        }
        ProtoBuf.Type.access$6002(localType, this.nullable_);
        int k = j;
        if ((i & 0x4) == 4) {
          k = j | 0x2;
        }
        ProtoBuf.Type.access$6102(localType, this.flexibleTypeCapabilitiesId_);
        j = k;
        if ((i & 0x8) == 8) {
          j = k | 0x4;
        }
        ProtoBuf.Type.access$6202(localType, this.flexibleUpperBound_);
        k = j;
        if ((i & 0x10) == 16) {
          k = j | 0x8;
        }
        ProtoBuf.Type.access$6302(localType, this.flexibleUpperBoundId_);
        j = k;
        if ((i & 0x20) == 32) {
          j = k | 0x10;
        }
        ProtoBuf.Type.access$6402(localType, this.className_);
        int m = j;
        if ((i & 0x40) == 64) {
          m = j | 0x20;
        }
        ProtoBuf.Type.access$6502(localType, this.typeParameter_);
        k = m;
        if ((i & 0x80) == 128) {
          k = m | 0x40;
        }
        ProtoBuf.Type.access$6602(localType, this.typeParameterName_);
        j = k;
        if ((i & 0x100) == 256) {
          j = k | 0x80;
        }
        ProtoBuf.Type.access$6702(localType, this.typeAliasName_);
        k = j;
        if ((i & 0x200) == 512) {
          k = j | 0x100;
        }
        ProtoBuf.Type.access$6802(localType, this.outerType_);
        j = k;
        if ((i & 0x400) == 1024) {
          j = k | 0x200;
        }
        ProtoBuf.Type.access$6902(localType, this.outerTypeId_);
        k = j;
        if ((i & 0x800) == 2048) {
          k = j | 0x400;
        }
        ProtoBuf.Type.access$7002(localType, this.abbreviatedType_);
        j = k;
        if ((i & 0x1000) == 4096) {
          j = k | 0x800;
        }
        ProtoBuf.Type.access$7102(localType, this.abbreviatedTypeId_);
        k = j;
        if ((i & 0x2000) == 8192) {
          k = j | 0x1000;
        }
        ProtoBuf.Type.access$7202(localType, this.flags_);
        ProtoBuf.Type.access$7302(localType, k);
        return localType;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public ProtoBuf.Type getAbbreviatedType()
      {
        return this.abbreviatedType_;
      }
      
      public ProtoBuf.Type.Argument getArgument(int paramInt)
      {
        return (ProtoBuf.Type.Argument)this.argument_.get(paramInt);
      }
      
      public int getArgumentCount()
      {
        return this.argument_.size();
      }
      
      public ProtoBuf.Type getDefaultInstanceForType()
      {
        return ProtoBuf.Type.getDefaultInstance();
      }
      
      public ProtoBuf.Type getFlexibleUpperBound()
      {
        return this.flexibleUpperBound_;
      }
      
      public ProtoBuf.Type getOuterType()
      {
        return this.outerType_;
      }
      
      public boolean hasAbbreviatedType()
      {
        boolean bool;
        if ((this.bitField0_ & 0x800) == 2048) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasFlexibleUpperBound()
      {
        boolean bool;
        if ((this.bitField0_ & 0x8) == 8) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasOuterType()
      {
        boolean bool;
        if ((this.bitField0_ & 0x200) == 512) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public final boolean isInitialized()
      {
        for (int i = 0; i < getArgumentCount(); i++) {
          if (!getArgument(i).isInitialized()) {
            return false;
          }
        }
        if ((hasFlexibleUpperBound()) && (!getFlexibleUpperBound().isInitialized())) {
          return false;
        }
        if ((hasOuterType()) && (!getOuterType().isInitialized())) {
          return false;
        }
        if ((hasAbbreviatedType()) && (!getAbbreviatedType().isInitialized())) {
          return false;
        }
        return extensionsAreInitialized();
      }
      
      public Builder mergeAbbreviatedType(ProtoBuf.Type paramType)
      {
        if (((this.bitField0_ & 0x800) == 2048) && (this.abbreviatedType_ != ProtoBuf.Type.getDefaultInstance())) {
          this.abbreviatedType_ = ProtoBuf.Type.newBuilder(this.abbreviatedType_).mergeFrom(paramType).buildPartial();
        } else {
          this.abbreviatedType_ = paramType;
        }
        this.bitField0_ |= 0x800;
        return this;
      }
      
      public Builder mergeFlexibleUpperBound(ProtoBuf.Type paramType)
      {
        if (((this.bitField0_ & 0x8) == 8) && (this.flexibleUpperBound_ != ProtoBuf.Type.getDefaultInstance())) {
          this.flexibleUpperBound_ = ProtoBuf.Type.newBuilder(this.flexibleUpperBound_).mergeFrom(paramType).buildPartial();
        } else {
          this.flexibleUpperBound_ = paramType;
        }
        this.bitField0_ |= 0x8;
        return this;
      }
      
      public Builder mergeFrom(ProtoBuf.Type paramType)
      {
        if (paramType == ProtoBuf.Type.getDefaultInstance()) {
          return this;
        }
        if (!paramType.argument_.isEmpty()) {
          if (this.argument_.isEmpty())
          {
            this.argument_ = paramType.argument_;
            this.bitField0_ &= 0xFFFFFFFE;
          }
          else
          {
            ensureArgumentIsMutable();
            this.argument_.addAll(paramType.argument_);
          }
        }
        if (paramType.hasNullable()) {
          setNullable(paramType.getNullable());
        }
        if (paramType.hasFlexibleTypeCapabilitiesId()) {
          setFlexibleTypeCapabilitiesId(paramType.getFlexibleTypeCapabilitiesId());
        }
        if (paramType.hasFlexibleUpperBound()) {
          mergeFlexibleUpperBound(paramType.getFlexibleUpperBound());
        }
        if (paramType.hasFlexibleUpperBoundId()) {
          setFlexibleUpperBoundId(paramType.getFlexibleUpperBoundId());
        }
        if (paramType.hasClassName()) {
          setClassName(paramType.getClassName());
        }
        if (paramType.hasTypeParameter()) {
          setTypeParameter(paramType.getTypeParameter());
        }
        if (paramType.hasTypeParameterName()) {
          setTypeParameterName(paramType.getTypeParameterName());
        }
        if (paramType.hasTypeAliasName()) {
          setTypeAliasName(paramType.getTypeAliasName());
        }
        if (paramType.hasOuterType()) {
          mergeOuterType(paramType.getOuterType());
        }
        if (paramType.hasOuterTypeId()) {
          setOuterTypeId(paramType.getOuterTypeId());
        }
        if (paramType.hasAbbreviatedType()) {
          mergeAbbreviatedType(paramType.getAbbreviatedType());
        }
        if (paramType.hasAbbreviatedTypeId()) {
          setAbbreviatedTypeId(paramType.getAbbreviatedTypeId());
        }
        if (paramType.hasFlags()) {
          setFlags(paramType.getFlags());
        }
        mergeExtensionFields(paramType);
        setUnknownFields(getUnknownFields().concat(paramType.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 377	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 383 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 175	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_1
        //   29: aload_3
        //   30: astore_2
        //   31: goto +15 -> 46
        //   34: astore_1
        //   35: aload_1
        //   36: invokevirtual 386	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   39: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type
        //   42: astore_2
        //   43: aload_1
        //   44: athrow
        //   45: astore_1
        //   46: aload_2
        //   47: ifnull +9 -> 56
        //   50: aload_0
        //   51: aload_2
        //   52: invokevirtual 175	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
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
      
      public Builder mergeOuterType(ProtoBuf.Type paramType)
      {
        if (((this.bitField0_ & 0x200) == 512) && (this.outerType_ != ProtoBuf.Type.getDefaultInstance())) {
          this.outerType_ = ProtoBuf.Type.newBuilder(this.outerType_).mergeFrom(paramType).buildPartial();
        } else {
          this.outerType_ = paramType;
        }
        this.bitField0_ |= 0x200;
        return this;
      }
      
      public Builder setAbbreviatedTypeId(int paramInt)
      {
        this.bitField0_ |= 0x1000;
        this.abbreviatedTypeId_ = paramInt;
        return this;
      }
      
      public Builder setClassName(int paramInt)
      {
        this.bitField0_ |= 0x20;
        this.className_ = paramInt;
        return this;
      }
      
      public Builder setFlags(int paramInt)
      {
        this.bitField0_ |= 0x2000;
        this.flags_ = paramInt;
        return this;
      }
      
      public Builder setFlexibleTypeCapabilitiesId(int paramInt)
      {
        this.bitField0_ |= 0x4;
        this.flexibleTypeCapabilitiesId_ = paramInt;
        return this;
      }
      
      public Builder setFlexibleUpperBoundId(int paramInt)
      {
        this.bitField0_ |= 0x10;
        this.flexibleUpperBoundId_ = paramInt;
        return this;
      }
      
      public Builder setNullable(boolean paramBoolean)
      {
        this.bitField0_ |= 0x2;
        this.nullable_ = paramBoolean;
        return this;
      }
      
      public Builder setOuterTypeId(int paramInt)
      {
        this.bitField0_ |= 0x400;
        this.outerTypeId_ = paramInt;
        return this;
      }
      
      public Builder setTypeAliasName(int paramInt)
      {
        this.bitField0_ |= 0x100;
        this.typeAliasName_ = paramInt;
        return this;
      }
      
      public Builder setTypeParameter(int paramInt)
      {
        this.bitField0_ |= 0x40;
        this.typeParameter_ = paramInt;
        return this;
      }
      
      public Builder setTypeParameterName(int paramInt)
      {
        this.bitField0_ |= 0x80;
        this.typeParameterName_ = paramInt;
        return this;
      }
    }
  }
  
  public static final class TypeAlias
    extends GeneratedMessageLite.ExtendableMessage<TypeAlias>
    implements ProtoBuf.TypeAliasOrBuilder
  {
    public static Parser<TypeAlias> PARSER = new AbstractParser()
    {
      public ProtoBuf.TypeAlias parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new ProtoBuf.TypeAlias(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final TypeAlias defaultInstance;
    private List<ProtoBuf.Annotation> annotation_;
    private int bitField0_;
    private int expandedTypeId_;
    private ProtoBuf.Type expandedType_;
    private int flags_;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private int name_;
    private List<ProtoBuf.TypeParameter> typeParameter_;
    private int underlyingTypeId_;
    private ProtoBuf.Type underlyingType_;
    private final ByteString unknownFields;
    private List<Integer> versionRequirement_;
    
    static
    {
      TypeAlias localTypeAlias = new TypeAlias(true);
      defaultInstance = localTypeAlias;
      localTypeAlias.initFields();
    }
    
    /* Error */
    private TypeAlias(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 63	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite$ExtendableMessage:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: i2b
      //   7: putfield 65	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:memoizedIsInitialized	B
      //   10: aload_0
      //   11: iconst_m1
      //   12: putfield 67	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:memoizedSerializedSize	I
      //   15: aload_0
      //   16: invokespecial 56	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:initFields	()V
      //   19: invokestatic 73	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   22: astore_3
      //   23: aload_3
      //   24: iconst_1
      //   25: invokestatic 79	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   28: astore 4
      //   30: iconst_0
      //   31: istore 5
      //   33: iconst_0
      //   34: istore 6
      //   36: iload 5
      //   38: ifne +1343 -> 1381
      //   41: iload 6
      //   43: istore 7
      //   45: iload 6
      //   47: istore 8
      //   49: iload 6
      //   51: istore 9
      //   53: aload_1
      //   54: invokevirtual 85	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   57: istore 10
      //   59: aconst_null
      //   60: astore 11
      //   62: aconst_null
      //   63: astore 12
      //   65: iload 10
      //   67: lookupswitch	default:+97->164, 0:+1156->1223, 8:+1111->1178, 16:+1066->1133, 26:+959->1026, 34:+798->865, 40:+752->819, 50:+592->659, 56:+546->613, 66:+433->500, 248:+321->388, 250:+124->191
      //   164: iload 6
      //   166: istore 7
      //   168: iload 6
      //   170: istore 8
      //   172: iload 6
      //   174: istore 9
      //   176: aload_0
      //   177: aload_1
      //   178: aload 4
      //   180: aload_2
      //   181: iload 10
      //   183: invokevirtual 89	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   186: istore 13
      //   188: goto +1041 -> 1229
      //   191: iload 6
      //   193: istore 7
      //   195: iload 6
      //   197: istore 8
      //   199: iload 6
      //   201: istore 9
      //   203: aload_1
      //   204: aload_1
      //   205: invokevirtual 92	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readRawVarint32	()I
      //   208: invokevirtual 96	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:pushLimit	(I)I
      //   211: istore 14
      //   213: iload 6
      //   215: istore 10
      //   217: iload 6
      //   219: sipush 256
      //   222: iand
      //   223: sipush 256
      //   226: if_icmpeq +86 -> 312
      //   229: iload 6
      //   231: istore 10
      //   233: iload 6
      //   235: istore 7
      //   237: iload 6
      //   239: istore 8
      //   241: iload 6
      //   243: istore 9
      //   245: aload_1
      //   246: invokevirtual 99	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
      //   249: ifle +63 -> 312
      //   252: iload 6
      //   254: istore 7
      //   256: iload 6
      //   258: istore 8
      //   260: iload 6
      //   262: istore 9
      //   264: new 101	java/util/ArrayList
      //   267: astore 12
      //   269: iload 6
      //   271: istore 7
      //   273: iload 6
      //   275: istore 8
      //   277: iload 6
      //   279: istore 9
      //   281: aload 12
      //   283: invokespecial 102	java/util/ArrayList:<init>	()V
      //   286: iload 6
      //   288: istore 7
      //   290: iload 6
      //   292: istore 8
      //   294: iload 6
      //   296: istore 9
      //   298: aload_0
      //   299: aload 12
      //   301: putfield 104	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:versionRequirement_	Ljava/util/List;
      //   304: iload 6
      //   306: sipush 256
      //   309: ior
      //   310: istore 10
      //   312: iload 10
      //   314: istore 7
      //   316: iload 10
      //   318: istore 8
      //   320: iload 10
      //   322: istore 9
      //   324: aload_1
      //   325: invokevirtual 99	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
      //   328: ifle +35 -> 363
      //   331: iload 10
      //   333: istore 7
      //   335: iload 10
      //   337: istore 8
      //   339: iload 10
      //   341: istore 9
      //   343: aload_0
      //   344: getfield 104	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:versionRequirement_	Ljava/util/List;
      //   347: aload_1
      //   348: invokevirtual 107	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   351: invokestatic 113	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   354: invokeinterface 119 2 0
      //   359: pop
      //   360: goto -48 -> 312
      //   363: iload 10
      //   365: istore 7
      //   367: iload 10
      //   369: istore 8
      //   371: iload 10
      //   373: istore 9
      //   375: aload_1
      //   376: iload 14
      //   378: invokevirtual 123	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:popLimit	(I)V
      //   381: iload 10
      //   383: istore 6
      //   385: goto -349 -> 36
      //   388: iload 6
      //   390: istore 10
      //   392: iload 6
      //   394: sipush 256
      //   397: iand
      //   398: sipush 256
      //   401: if_icmpeq +63 -> 464
      //   404: iload 6
      //   406: istore 7
      //   408: iload 6
      //   410: istore 8
      //   412: iload 6
      //   414: istore 9
      //   416: new 101	java/util/ArrayList
      //   419: astore 12
      //   421: iload 6
      //   423: istore 7
      //   425: iload 6
      //   427: istore 8
      //   429: iload 6
      //   431: istore 9
      //   433: aload 12
      //   435: invokespecial 102	java/util/ArrayList:<init>	()V
      //   438: iload 6
      //   440: istore 7
      //   442: iload 6
      //   444: istore 8
      //   446: iload 6
      //   448: istore 9
      //   450: aload_0
      //   451: aload 12
      //   453: putfield 104	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:versionRequirement_	Ljava/util/List;
      //   456: iload 6
      //   458: sipush 256
      //   461: ior
      //   462: istore 10
      //   464: iload 10
      //   466: istore 7
      //   468: iload 10
      //   470: istore 8
      //   472: iload 10
      //   474: istore 9
      //   476: aload_0
      //   477: getfield 104	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:versionRequirement_	Ljava/util/List;
      //   480: aload_1
      //   481: invokevirtual 107	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   484: invokestatic 113	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   487: invokeinterface 119 2 0
      //   492: pop
      //   493: iload 10
      //   495: istore 6
      //   497: goto -461 -> 36
      //   500: iload 6
      //   502: istore 10
      //   504: iload 6
      //   506: sipush 128
      //   509: iand
      //   510: sipush 128
      //   513: if_icmpeq +63 -> 576
      //   516: iload 6
      //   518: istore 7
      //   520: iload 6
      //   522: istore 8
      //   524: iload 6
      //   526: istore 9
      //   528: new 101	java/util/ArrayList
      //   531: astore 12
      //   533: iload 6
      //   535: istore 7
      //   537: iload 6
      //   539: istore 8
      //   541: iload 6
      //   543: istore 9
      //   545: aload 12
      //   547: invokespecial 102	java/util/ArrayList:<init>	()V
      //   550: iload 6
      //   552: istore 7
      //   554: iload 6
      //   556: istore 8
      //   558: iload 6
      //   560: istore 9
      //   562: aload_0
      //   563: aload 12
      //   565: putfield 125	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:annotation_	Ljava/util/List;
      //   568: iload 6
      //   570: sipush 128
      //   573: ior
      //   574: istore 10
      //   576: iload 10
      //   578: istore 7
      //   580: iload 10
      //   582: istore 8
      //   584: iload 10
      //   586: istore 9
      //   588: aload_0
      //   589: getfield 125	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:annotation_	Ljava/util/List;
      //   592: aload_1
      //   593: getstatic 128	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Annotation:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   596: aload_2
      //   597: invokevirtual 132	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   600: invokeinterface 119 2 0
      //   605: pop
      //   606: iload 10
      //   608: istore 6
      //   610: goto -574 -> 36
      //   613: iload 6
      //   615: istore 7
      //   617: iload 6
      //   619: istore 8
      //   621: iload 6
      //   623: istore 9
      //   625: aload_0
      //   626: aload_0
      //   627: getfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:bitField0_	I
      //   630: bipush 32
      //   632: ior
      //   633: putfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:bitField0_	I
      //   636: iload 6
      //   638: istore 7
      //   640: iload 6
      //   642: istore 8
      //   644: iload 6
      //   646: istore 9
      //   648: aload_0
      //   649: aload_1
      //   650: invokevirtual 107	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   653: putfield 136	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:expandedTypeId_	I
      //   656: goto -620 -> 36
      //   659: iload 6
      //   661: istore 7
      //   663: iload 6
      //   665: istore 8
      //   667: iload 6
      //   669: istore 9
      //   671: aload_0
      //   672: getfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:bitField0_	I
      //   675: bipush 16
      //   677: iand
      //   678: bipush 16
      //   680: if_icmpne +24 -> 704
      //   683: iload 6
      //   685: istore 7
      //   687: iload 6
      //   689: istore 8
      //   691: iload 6
      //   693: istore 9
      //   695: aload_0
      //   696: getfield 138	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:expandedType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   699: invokevirtual 144	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   702: astore 12
      //   704: iload 6
      //   706: istore 7
      //   708: iload 6
      //   710: istore 8
      //   712: iload 6
      //   714: istore 9
      //   716: aload_1
      //   717: getstatic 145	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   720: aload_2
      //   721: invokevirtual 132	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   724: checkcast 140	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type
      //   727: astore 11
      //   729: iload 6
      //   731: istore 7
      //   733: iload 6
      //   735: istore 8
      //   737: iload 6
      //   739: istore 9
      //   741: aload_0
      //   742: aload 11
      //   744: putfield 138	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:expandedType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   747: aload 12
      //   749: ifnull +44 -> 793
      //   752: iload 6
      //   754: istore 7
      //   756: iload 6
      //   758: istore 8
      //   760: iload 6
      //   762: istore 9
      //   764: aload 12
      //   766: aload 11
      //   768: invokevirtual 151	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   771: pop
      //   772: iload 6
      //   774: istore 7
      //   776: iload 6
      //   778: istore 8
      //   780: iload 6
      //   782: istore 9
      //   784: aload_0
      //   785: aload 12
      //   787: invokevirtual 155	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   790: putfield 138	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:expandedType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   793: iload 6
      //   795: istore 7
      //   797: iload 6
      //   799: istore 8
      //   801: iload 6
      //   803: istore 9
      //   805: aload_0
      //   806: aload_0
      //   807: getfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:bitField0_	I
      //   810: bipush 16
      //   812: ior
      //   813: putfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:bitField0_	I
      //   816: goto -780 -> 36
      //   819: iload 6
      //   821: istore 7
      //   823: iload 6
      //   825: istore 8
      //   827: iload 6
      //   829: istore 9
      //   831: aload_0
      //   832: aload_0
      //   833: getfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:bitField0_	I
      //   836: bipush 8
      //   838: ior
      //   839: putfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:bitField0_	I
      //   842: iload 6
      //   844: istore 7
      //   846: iload 6
      //   848: istore 8
      //   850: iload 6
      //   852: istore 9
      //   854: aload_0
      //   855: aload_1
      //   856: invokevirtual 107	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   859: putfield 157	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:underlyingTypeId_	I
      //   862: goto -826 -> 36
      //   865: aload 11
      //   867: astore 12
      //   869: iload 6
      //   871: istore 7
      //   873: iload 6
      //   875: istore 8
      //   877: iload 6
      //   879: istore 9
      //   881: aload_0
      //   882: getfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:bitField0_	I
      //   885: iconst_4
      //   886: iand
      //   887: iconst_4
      //   888: if_icmpne +24 -> 912
      //   891: iload 6
      //   893: istore 7
      //   895: iload 6
      //   897: istore 8
      //   899: iload 6
      //   901: istore 9
      //   903: aload_0
      //   904: getfield 159	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:underlyingType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   907: invokevirtual 144	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   910: astore 12
      //   912: iload 6
      //   914: istore 7
      //   916: iload 6
      //   918: istore 8
      //   920: iload 6
      //   922: istore 9
      //   924: aload_1
      //   925: getstatic 145	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   928: aload_2
      //   929: invokevirtual 132	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   932: checkcast 140	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type
      //   935: astore 11
      //   937: iload 6
      //   939: istore 7
      //   941: iload 6
      //   943: istore 8
      //   945: iload 6
      //   947: istore 9
      //   949: aload_0
      //   950: aload 11
      //   952: putfield 159	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:underlyingType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   955: aload 12
      //   957: ifnull +44 -> 1001
      //   960: iload 6
      //   962: istore 7
      //   964: iload 6
      //   966: istore 8
      //   968: iload 6
      //   970: istore 9
      //   972: aload 12
      //   974: aload 11
      //   976: invokevirtual 151	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   979: pop
      //   980: iload 6
      //   982: istore 7
      //   984: iload 6
      //   986: istore 8
      //   988: iload 6
      //   990: istore 9
      //   992: aload_0
      //   993: aload 12
      //   995: invokevirtual 155	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   998: putfield 159	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:underlyingType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   1001: iload 6
      //   1003: istore 7
      //   1005: iload 6
      //   1007: istore 8
      //   1009: iload 6
      //   1011: istore 9
      //   1013: aload_0
      //   1014: aload_0
      //   1015: getfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:bitField0_	I
      //   1018: iconst_4
      //   1019: ior
      //   1020: putfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:bitField0_	I
      //   1023: goto -987 -> 36
      //   1026: iload 6
      //   1028: istore 10
      //   1030: iload 6
      //   1032: iconst_4
      //   1033: iand
      //   1034: iconst_4
      //   1035: if_icmpeq +61 -> 1096
      //   1038: iload 6
      //   1040: istore 7
      //   1042: iload 6
      //   1044: istore 8
      //   1046: iload 6
      //   1048: istore 9
      //   1050: new 101	java/util/ArrayList
      //   1053: astore 12
      //   1055: iload 6
      //   1057: istore 7
      //   1059: iload 6
      //   1061: istore 8
      //   1063: iload 6
      //   1065: istore 9
      //   1067: aload 12
      //   1069: invokespecial 102	java/util/ArrayList:<init>	()V
      //   1072: iload 6
      //   1074: istore 7
      //   1076: iload 6
      //   1078: istore 8
      //   1080: iload 6
      //   1082: istore 9
      //   1084: aload_0
      //   1085: aload 12
      //   1087: putfield 161	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:typeParameter_	Ljava/util/List;
      //   1090: iload 6
      //   1092: iconst_4
      //   1093: ior
      //   1094: istore 10
      //   1096: iload 10
      //   1098: istore 7
      //   1100: iload 10
      //   1102: istore 8
      //   1104: iload 10
      //   1106: istore 9
      //   1108: aload_0
      //   1109: getfield 161	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:typeParameter_	Ljava/util/List;
      //   1112: aload_1
      //   1113: getstatic 164	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   1116: aload_2
      //   1117: invokevirtual 132	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   1120: invokeinterface 119 2 0
      //   1125: pop
      //   1126: iload 10
      //   1128: istore 6
      //   1130: goto -1094 -> 36
      //   1133: iload 6
      //   1135: istore 7
      //   1137: iload 6
      //   1139: istore 8
      //   1141: iload 6
      //   1143: istore 9
      //   1145: aload_0
      //   1146: aload_0
      //   1147: getfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:bitField0_	I
      //   1150: iconst_2
      //   1151: ior
      //   1152: putfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:bitField0_	I
      //   1155: iload 6
      //   1157: istore 7
      //   1159: iload 6
      //   1161: istore 8
      //   1163: iload 6
      //   1165: istore 9
      //   1167: aload_0
      //   1168: aload_1
      //   1169: invokevirtual 107	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   1172: putfield 166	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:name_	I
      //   1175: goto -1139 -> 36
      //   1178: iload 6
      //   1180: istore 7
      //   1182: iload 6
      //   1184: istore 8
      //   1186: iload 6
      //   1188: istore 9
      //   1190: aload_0
      //   1191: aload_0
      //   1192: getfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:bitField0_	I
      //   1195: iconst_1
      //   1196: ior
      //   1197: putfield 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:bitField0_	I
      //   1200: iload 6
      //   1202: istore 7
      //   1204: iload 6
      //   1206: istore 8
      //   1208: iload 6
      //   1210: istore 9
      //   1212: aload_0
      //   1213: aload_1
      //   1214: invokevirtual 107	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   1217: putfield 168	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:flags_	I
      //   1220: goto -1184 -> 36
      //   1223: iconst_1
      //   1224: istore 5
      //   1226: goto -1190 -> 36
      //   1229: iload 13
      //   1231: ifne -1195 -> 36
      //   1234: goto -11 -> 1223
      //   1237: astore_1
      //   1238: goto +45 -> 1283
      //   1241: astore_2
      //   1242: iload 8
      //   1244: istore 7
      //   1246: new 60	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1249: astore_1
      //   1250: iload 8
      //   1252: istore 7
      //   1254: aload_1
      //   1255: aload_2
      //   1256: invokevirtual 172	java/io/IOException:getMessage	()Ljava/lang/String;
      //   1259: invokespecial 175	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   1262: iload 8
      //   1264: istore 7
      //   1266: aload_1
      //   1267: aload_0
      //   1268: invokevirtual 179	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   1271: athrow
      //   1272: astore_1
      //   1273: iload 9
      //   1275: istore 7
      //   1277: aload_1
      //   1278: aload_0
      //   1279: invokevirtual 179	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   1282: athrow
      //   1283: iload 7
      //   1285: iconst_4
      //   1286: iand
      //   1287: iconst_4
      //   1288: if_icmpne +14 -> 1302
      //   1291: aload_0
      //   1292: aload_0
      //   1293: getfield 161	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:typeParameter_	Ljava/util/List;
      //   1296: invokestatic 185	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   1299: putfield 161	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:typeParameter_	Ljava/util/List;
      //   1302: iload 7
      //   1304: sipush 128
      //   1307: iand
      //   1308: sipush 128
      //   1311: if_icmpne +14 -> 1325
      //   1314: aload_0
      //   1315: aload_0
      //   1316: getfield 125	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:annotation_	Ljava/util/List;
      //   1319: invokestatic 185	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   1322: putfield 125	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:annotation_	Ljava/util/List;
      //   1325: iload 7
      //   1327: sipush 256
      //   1330: iand
      //   1331: sipush 256
      //   1334: if_icmpne +14 -> 1348
      //   1337: aload_0
      //   1338: aload_0
      //   1339: getfield 104	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:versionRequirement_	Ljava/util/List;
      //   1342: invokestatic 185	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   1345: putfield 104	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:versionRequirement_	Ljava/util/List;
      //   1348: aload 4
      //   1350: invokevirtual 188	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   1353: aload_0
      //   1354: aload_3
      //   1355: invokevirtual 194	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1358: putfield 196	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1361: goto +14 -> 1375
      //   1364: astore_1
      //   1365: aload_0
      //   1366: aload_3
      //   1367: invokevirtual 194	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1370: putfield 196	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1373: aload_1
      //   1374: athrow
      //   1375: aload_0
      //   1376: invokevirtual 199	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:makeExtensionsImmutable	()V
      //   1379: aload_1
      //   1380: athrow
      //   1381: iload 6
      //   1383: iconst_4
      //   1384: iand
      //   1385: iconst_4
      //   1386: if_icmpne +14 -> 1400
      //   1389: aload_0
      //   1390: aload_0
      //   1391: getfield 161	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:typeParameter_	Ljava/util/List;
      //   1394: invokestatic 185	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   1397: putfield 161	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:typeParameter_	Ljava/util/List;
      //   1400: iload 6
      //   1402: sipush 128
      //   1405: iand
      //   1406: sipush 128
      //   1409: if_icmpne +14 -> 1423
      //   1412: aload_0
      //   1413: aload_0
      //   1414: getfield 125	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:annotation_	Ljava/util/List;
      //   1417: invokestatic 185	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   1420: putfield 125	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:annotation_	Ljava/util/List;
      //   1423: iload 6
      //   1425: sipush 256
      //   1428: iand
      //   1429: sipush 256
      //   1432: if_icmpne +14 -> 1446
      //   1435: aload_0
      //   1436: aload_0
      //   1437: getfield 104	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:versionRequirement_	Ljava/util/List;
      //   1440: invokestatic 185	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   1443: putfield 104	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:versionRequirement_	Ljava/util/List;
      //   1446: aload 4
      //   1448: invokevirtual 188	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   1451: aload_0
      //   1452: aload_3
      //   1453: invokevirtual 194	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1456: putfield 196	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1459: goto +14 -> 1473
      //   1462: astore_1
      //   1463: aload_0
      //   1464: aload_3
      //   1465: invokevirtual 194	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1468: putfield 196	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1471: aload_1
      //   1472: athrow
      //   1473: aload_0
      //   1474: invokevirtual 199	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:makeExtensionsImmutable	()V
      //   1477: return
      //   1478: astore_2
      //   1479: goto -126 -> 1353
      //   1482: astore_1
      //   1483: goto -32 -> 1451
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	1486	0	this	TypeAlias
      //   0	1486	1	paramCodedInputStream	CodedInputStream
      //   0	1486	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   22	1443	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   28	1419	4	localCodedOutputStream	CodedOutputStream
      //   31	1194	5	i	int
      //   34	1395	6	j	int
      //   43	1288	7	k	int
      //   47	1216	8	m	int
      //   51	1223	9	n	int
      //   57	1070	10	i1	int
      //   60	915	11	localType	ProtoBuf.Type
      //   63	1023	12	localObject	Object
      //   186	1044	13	bool	boolean
      //   211	166	14	i2	int
      // Exception table:
      //   from	to	target	type
      //   53	59	1237	finally
      //   176	188	1237	finally
      //   203	213	1237	finally
      //   245	252	1237	finally
      //   264	269	1237	finally
      //   281	286	1237	finally
      //   298	304	1237	finally
      //   324	331	1237	finally
      //   343	360	1237	finally
      //   375	381	1237	finally
      //   416	421	1237	finally
      //   433	438	1237	finally
      //   450	456	1237	finally
      //   476	493	1237	finally
      //   528	533	1237	finally
      //   545	550	1237	finally
      //   562	568	1237	finally
      //   588	606	1237	finally
      //   625	636	1237	finally
      //   648	656	1237	finally
      //   671	683	1237	finally
      //   695	704	1237	finally
      //   716	729	1237	finally
      //   741	747	1237	finally
      //   764	772	1237	finally
      //   784	793	1237	finally
      //   805	816	1237	finally
      //   831	842	1237	finally
      //   854	862	1237	finally
      //   881	891	1237	finally
      //   903	912	1237	finally
      //   924	937	1237	finally
      //   949	955	1237	finally
      //   972	980	1237	finally
      //   992	1001	1237	finally
      //   1013	1023	1237	finally
      //   1050	1055	1237	finally
      //   1067	1072	1237	finally
      //   1084	1090	1237	finally
      //   1108	1126	1237	finally
      //   1145	1155	1237	finally
      //   1167	1175	1237	finally
      //   1190	1200	1237	finally
      //   1212	1220	1237	finally
      //   1246	1250	1237	finally
      //   1254	1262	1237	finally
      //   1266	1272	1237	finally
      //   1277	1283	1237	finally
      //   53	59	1241	java/io/IOException
      //   176	188	1241	java/io/IOException
      //   203	213	1241	java/io/IOException
      //   245	252	1241	java/io/IOException
      //   264	269	1241	java/io/IOException
      //   281	286	1241	java/io/IOException
      //   298	304	1241	java/io/IOException
      //   324	331	1241	java/io/IOException
      //   343	360	1241	java/io/IOException
      //   375	381	1241	java/io/IOException
      //   416	421	1241	java/io/IOException
      //   433	438	1241	java/io/IOException
      //   450	456	1241	java/io/IOException
      //   476	493	1241	java/io/IOException
      //   528	533	1241	java/io/IOException
      //   545	550	1241	java/io/IOException
      //   562	568	1241	java/io/IOException
      //   588	606	1241	java/io/IOException
      //   625	636	1241	java/io/IOException
      //   648	656	1241	java/io/IOException
      //   671	683	1241	java/io/IOException
      //   695	704	1241	java/io/IOException
      //   716	729	1241	java/io/IOException
      //   741	747	1241	java/io/IOException
      //   764	772	1241	java/io/IOException
      //   784	793	1241	java/io/IOException
      //   805	816	1241	java/io/IOException
      //   831	842	1241	java/io/IOException
      //   854	862	1241	java/io/IOException
      //   881	891	1241	java/io/IOException
      //   903	912	1241	java/io/IOException
      //   924	937	1241	java/io/IOException
      //   949	955	1241	java/io/IOException
      //   972	980	1241	java/io/IOException
      //   992	1001	1241	java/io/IOException
      //   1013	1023	1241	java/io/IOException
      //   1050	1055	1241	java/io/IOException
      //   1067	1072	1241	java/io/IOException
      //   1084	1090	1241	java/io/IOException
      //   1108	1126	1241	java/io/IOException
      //   1145	1155	1241	java/io/IOException
      //   1167	1175	1241	java/io/IOException
      //   1190	1200	1241	java/io/IOException
      //   1212	1220	1241	java/io/IOException
      //   53	59	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   176	188	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   203	213	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   245	252	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   264	269	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   281	286	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   298	304	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   324	331	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   343	360	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   375	381	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   416	421	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   433	438	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   450	456	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   476	493	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   528	533	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   545	550	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   562	568	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   588	606	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   625	636	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   648	656	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   671	683	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   695	704	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   716	729	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   741	747	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   764	772	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   784	793	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   805	816	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   831	842	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   854	862	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   881	891	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   903	912	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   924	937	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   949	955	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   972	980	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   992	1001	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1013	1023	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1050	1055	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1067	1072	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1084	1090	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1108	1126	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1145	1155	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1167	1175	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1190	1200	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1212	1220	1272	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   1348	1353	1364	finally
      //   1446	1451	1462	finally
      //   1348	1353	1478	java/io/IOException
      //   1446	1451	1482	java/io/IOException
    }
    
    private TypeAlias(GeneratedMessageLite.ExtendableBuilder<TypeAlias, ?> paramExtendableBuilder)
    {
      super();
      this.unknownFields = paramExtendableBuilder.getUnknownFields();
    }
    
    private TypeAlias(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static TypeAlias getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.flags_ = 6;
      this.name_ = 0;
      this.typeParameter_ = Collections.emptyList();
      this.underlyingType_ = ProtoBuf.Type.getDefaultInstance();
      this.underlyingTypeId_ = 0;
      this.expandedType_ = ProtoBuf.Type.getDefaultInstance();
      this.expandedTypeId_ = 0;
      this.annotation_ = Collections.emptyList();
      this.versionRequirement_ = Collections.emptyList();
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$17800();
    }
    
    public static Builder newBuilder(TypeAlias paramTypeAlias)
    {
      return newBuilder().mergeFrom(paramTypeAlias);
    }
    
    public static TypeAlias parseDelimitedFrom(InputStream paramInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws IOException
    {
      return (TypeAlias)PARSER.parseDelimitedFrom(paramInputStream, paramExtensionRegistryLite);
    }
    
    public ProtoBuf.Annotation getAnnotation(int paramInt)
    {
      return (ProtoBuf.Annotation)this.annotation_.get(paramInt);
    }
    
    public int getAnnotationCount()
    {
      return this.annotation_.size();
    }
    
    public List<ProtoBuf.Annotation> getAnnotationList()
    {
      return this.annotation_;
    }
    
    public TypeAlias getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public ProtoBuf.Type getExpandedType()
    {
      return this.expandedType_;
    }
    
    public int getExpandedTypeId()
    {
      return this.expandedTypeId_;
    }
    
    public int getFlags()
    {
      return this.flags_;
    }
    
    public int getName()
    {
      return this.name_;
    }
    
    public Parser<TypeAlias> getParserForType()
    {
      return PARSER;
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
        k = CodedOutputStream.computeInt32Size(1, this.flags_) + 0;
      } else {
        k = 0;
      }
      i = k;
      if ((this.bitField0_ & 0x2) == 2) {
        i = k + CodedOutputStream.computeInt32Size(2, this.name_);
      }
      for (int k = 0; k < this.typeParameter_.size(); k++) {
        i += CodedOutputStream.computeMessageSize(3, (MessageLite)this.typeParameter_.get(k));
      }
      k = i;
      if ((this.bitField0_ & 0x4) == 4) {
        k = i + CodedOutputStream.computeMessageSize(4, this.underlyingType_);
      }
      i = k;
      if ((this.bitField0_ & 0x8) == 8) {
        i = k + CodedOutputStream.computeInt32Size(5, this.underlyingTypeId_);
      }
      k = i;
      if ((this.bitField0_ & 0x10) == 16) {
        k = i + CodedOutputStream.computeMessageSize(6, this.expandedType_);
      }
      i = k;
      if ((this.bitField0_ & 0x20) == 32) {
        i = k + CodedOutputStream.computeInt32Size(7, this.expandedTypeId_);
      }
      for (k = 0; k < this.annotation_.size(); k++) {
        i += CodedOutputStream.computeMessageSize(8, (MessageLite)this.annotation_.get(k));
      }
      int m = 0;
      k = j;
      j = m;
      while (k < this.versionRequirement_.size())
      {
        j += CodedOutputStream.computeInt32SizeNoTag(((Integer)this.versionRequirement_.get(k)).intValue());
        k++;
      }
      i = i + j + getVersionRequirementList().size() * 2 + extensionsSerializedSize() + this.unknownFields.size();
      this.memoizedSerializedSize = i;
      return i;
    }
    
    public ProtoBuf.TypeParameter getTypeParameter(int paramInt)
    {
      return (ProtoBuf.TypeParameter)this.typeParameter_.get(paramInt);
    }
    
    public int getTypeParameterCount()
    {
      return this.typeParameter_.size();
    }
    
    public List<ProtoBuf.TypeParameter> getTypeParameterList()
    {
      return this.typeParameter_;
    }
    
    public ProtoBuf.Type getUnderlyingType()
    {
      return this.underlyingType_;
    }
    
    public int getUnderlyingTypeId()
    {
      return this.underlyingTypeId_;
    }
    
    public List<Integer> getVersionRequirementList()
    {
      return this.versionRequirement_;
    }
    
    public boolean hasExpandedType()
    {
      boolean bool;
      if ((this.bitField0_ & 0x10) == 16) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasExpandedTypeId()
    {
      boolean bool;
      if ((this.bitField0_ & 0x20) == 32) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasFlags()
    {
      int i = this.bitField0_;
      boolean bool = true;
      if ((i & 0x1) != 1) {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasName()
    {
      boolean bool;
      if ((this.bitField0_ & 0x2) == 2) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasUnderlyingType()
    {
      boolean bool;
      if ((this.bitField0_ & 0x4) == 4) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasUnderlyingTypeId()
    {
      boolean bool;
      if ((this.bitField0_ & 0x8) == 8) {
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
      if (!hasName())
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      for (i = 0; i < getTypeParameterCount(); i++) {
        if (!getTypeParameter(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
      }
      if ((hasUnderlyingType()) && (!getUnderlyingType().isInitialized()))
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      if ((hasExpandedType()) && (!getExpandedType().isInitialized()))
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      for (i = 0; i < getAnnotationCount(); i++) {
        if (!getAnnotation(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
      }
      if (!extensionsAreInitialized())
      {
        this.memoizedIsInitialized = ((byte)0);
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
      GeneratedMessageLite.ExtendableMessage.ExtensionWriter localExtensionWriter = newExtensionWriter();
      if ((this.bitField0_ & 0x1) == 1) {
        paramCodedOutputStream.writeInt32(1, this.flags_);
      }
      if ((this.bitField0_ & 0x2) == 2) {
        paramCodedOutputStream.writeInt32(2, this.name_);
      }
      int i = 0;
      for (int j = 0; j < this.typeParameter_.size(); j++) {
        paramCodedOutputStream.writeMessage(3, (MessageLite)this.typeParameter_.get(j));
      }
      if ((this.bitField0_ & 0x4) == 4) {
        paramCodedOutputStream.writeMessage(4, this.underlyingType_);
      }
      if ((this.bitField0_ & 0x8) == 8) {
        paramCodedOutputStream.writeInt32(5, this.underlyingTypeId_);
      }
      if ((this.bitField0_ & 0x10) == 16) {
        paramCodedOutputStream.writeMessage(6, this.expandedType_);
      }
      if ((this.bitField0_ & 0x20) == 32) {
        paramCodedOutputStream.writeInt32(7, this.expandedTypeId_);
      }
      int k;
      for (j = 0;; j++)
      {
        k = i;
        if (j >= this.annotation_.size()) {
          break;
        }
        paramCodedOutputStream.writeMessage(8, (MessageLite)this.annotation_.get(j));
      }
      while (k < this.versionRequirement_.size())
      {
        paramCodedOutputStream.writeInt32(31, ((Integer)this.versionRequirement_.get(k)).intValue());
        k++;
      }
      localExtensionWriter.writeUntil(200, paramCodedOutputStream);
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Builder
      extends GeneratedMessageLite.ExtendableBuilder<ProtoBuf.TypeAlias, Builder>
      implements ProtoBuf.TypeAliasOrBuilder
    {
      private List<ProtoBuf.Annotation> annotation_ = Collections.emptyList();
      private int bitField0_;
      private int expandedTypeId_;
      private ProtoBuf.Type expandedType_ = ProtoBuf.Type.getDefaultInstance();
      private int flags_ = 6;
      private int name_;
      private List<ProtoBuf.TypeParameter> typeParameter_ = Collections.emptyList();
      private int underlyingTypeId_;
      private ProtoBuf.Type underlyingType_ = ProtoBuf.Type.getDefaultInstance();
      private List<Integer> versionRequirement_ = Collections.emptyList();
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void ensureAnnotationIsMutable()
      {
        if ((this.bitField0_ & 0x80) != 128)
        {
          this.annotation_ = new ArrayList(this.annotation_);
          this.bitField0_ |= 0x80;
        }
      }
      
      private void ensureTypeParameterIsMutable()
      {
        if ((this.bitField0_ & 0x4) != 4)
        {
          this.typeParameter_ = new ArrayList(this.typeParameter_);
          this.bitField0_ |= 0x4;
        }
      }
      
      private void ensureVersionRequirementIsMutable()
      {
        if ((this.bitField0_ & 0x100) != 256)
        {
          this.versionRequirement_ = new ArrayList(this.versionRequirement_);
          this.bitField0_ |= 0x100;
        }
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public ProtoBuf.TypeAlias build()
      {
        ProtoBuf.TypeAlias localTypeAlias = buildPartial();
        if (localTypeAlias.isInitialized()) {
          return localTypeAlias;
        }
        throw newUninitializedMessageException(localTypeAlias);
      }
      
      public ProtoBuf.TypeAlias buildPartial()
      {
        ProtoBuf.TypeAlias localTypeAlias = new ProtoBuf.TypeAlias(this, null);
        int i = this.bitField0_;
        int j = 1;
        if ((i & 0x1) != 1) {
          j = 0;
        }
        ProtoBuf.TypeAlias.access$18002(localTypeAlias, this.flags_);
        int k = j;
        if ((i & 0x2) == 2) {
          k = j | 0x2;
        }
        ProtoBuf.TypeAlias.access$18102(localTypeAlias, this.name_);
        if ((this.bitField0_ & 0x4) == 4)
        {
          this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
          this.bitField0_ &= 0xFFFFFFFB;
        }
        ProtoBuf.TypeAlias.access$18202(localTypeAlias, this.typeParameter_);
        j = k;
        if ((i & 0x8) == 8) {
          j = k | 0x4;
        }
        ProtoBuf.TypeAlias.access$18302(localTypeAlias, this.underlyingType_);
        k = j;
        if ((i & 0x10) == 16) {
          k = j | 0x8;
        }
        ProtoBuf.TypeAlias.access$18402(localTypeAlias, this.underlyingTypeId_);
        j = k;
        if ((i & 0x20) == 32) {
          j = k | 0x10;
        }
        ProtoBuf.TypeAlias.access$18502(localTypeAlias, this.expandedType_);
        k = j;
        if ((i & 0x40) == 64) {
          k = j | 0x20;
        }
        ProtoBuf.TypeAlias.access$18602(localTypeAlias, this.expandedTypeId_);
        if ((this.bitField0_ & 0x80) == 128)
        {
          this.annotation_ = Collections.unmodifiableList(this.annotation_);
          this.bitField0_ &= 0xFF7F;
        }
        ProtoBuf.TypeAlias.access$18702(localTypeAlias, this.annotation_);
        if ((this.bitField0_ & 0x100) == 256)
        {
          this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
          this.bitField0_ &= 0xFEFF;
        }
        ProtoBuf.TypeAlias.access$18802(localTypeAlias, this.versionRequirement_);
        ProtoBuf.TypeAlias.access$18902(localTypeAlias, k);
        return localTypeAlias;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public ProtoBuf.Annotation getAnnotation(int paramInt)
      {
        return (ProtoBuf.Annotation)this.annotation_.get(paramInt);
      }
      
      public int getAnnotationCount()
      {
        return this.annotation_.size();
      }
      
      public ProtoBuf.TypeAlias getDefaultInstanceForType()
      {
        return ProtoBuf.TypeAlias.getDefaultInstance();
      }
      
      public ProtoBuf.Type getExpandedType()
      {
        return this.expandedType_;
      }
      
      public ProtoBuf.TypeParameter getTypeParameter(int paramInt)
      {
        return (ProtoBuf.TypeParameter)this.typeParameter_.get(paramInt);
      }
      
      public int getTypeParameterCount()
      {
        return this.typeParameter_.size();
      }
      
      public ProtoBuf.Type getUnderlyingType()
      {
        return this.underlyingType_;
      }
      
      public boolean hasExpandedType()
      {
        boolean bool;
        if ((this.bitField0_ & 0x20) == 32) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasName()
      {
        boolean bool;
        if ((this.bitField0_ & 0x2) == 2) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasUnderlyingType()
      {
        boolean bool;
        if ((this.bitField0_ & 0x8) == 8) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public final boolean isInitialized()
      {
        if (!hasName()) {
          return false;
        }
        for (int i = 0; i < getTypeParameterCount(); i++) {
          if (!getTypeParameter(i).isInitialized()) {
            return false;
          }
        }
        if ((hasUnderlyingType()) && (!getUnderlyingType().isInitialized())) {
          return false;
        }
        if ((hasExpandedType()) && (!getExpandedType().isInitialized())) {
          return false;
        }
        for (i = 0; i < getAnnotationCount(); i++) {
          if (!getAnnotation(i).isInitialized()) {
            return false;
          }
        }
        return extensionsAreInitialized();
      }
      
      public Builder mergeExpandedType(ProtoBuf.Type paramType)
      {
        if (((this.bitField0_ & 0x20) == 32) && (this.expandedType_ != ProtoBuf.Type.getDefaultInstance())) {
          this.expandedType_ = ProtoBuf.Type.newBuilder(this.expandedType_).mergeFrom(paramType).buildPartial();
        } else {
          this.expandedType_ = paramType;
        }
        this.bitField0_ |= 0x20;
        return this;
      }
      
      public Builder mergeFrom(ProtoBuf.TypeAlias paramTypeAlias)
      {
        if (paramTypeAlias == ProtoBuf.TypeAlias.getDefaultInstance()) {
          return this;
        }
        if (paramTypeAlias.hasFlags()) {
          setFlags(paramTypeAlias.getFlags());
        }
        if (paramTypeAlias.hasName()) {
          setName(paramTypeAlias.getName());
        }
        if (!paramTypeAlias.typeParameter_.isEmpty()) {
          if (this.typeParameter_.isEmpty())
          {
            this.typeParameter_ = paramTypeAlias.typeParameter_;
            this.bitField0_ &= 0xFFFFFFFB;
          }
          else
          {
            ensureTypeParameterIsMutable();
            this.typeParameter_.addAll(paramTypeAlias.typeParameter_);
          }
        }
        if (paramTypeAlias.hasUnderlyingType()) {
          mergeUnderlyingType(paramTypeAlias.getUnderlyingType());
        }
        if (paramTypeAlias.hasUnderlyingTypeId()) {
          setUnderlyingTypeId(paramTypeAlias.getUnderlyingTypeId());
        }
        if (paramTypeAlias.hasExpandedType()) {
          mergeExpandedType(paramTypeAlias.getExpandedType());
        }
        if (paramTypeAlias.hasExpandedTypeId()) {
          setExpandedTypeId(paramTypeAlias.getExpandedTypeId());
        }
        if (!paramTypeAlias.annotation_.isEmpty()) {
          if (this.annotation_.isEmpty())
          {
            this.annotation_ = paramTypeAlias.annotation_;
            this.bitField0_ &= 0xFF7F;
          }
          else
          {
            ensureAnnotationIsMutable();
            this.annotation_.addAll(paramTypeAlias.annotation_);
          }
        }
        if (!paramTypeAlias.versionRequirement_.isEmpty()) {
          if (this.versionRequirement_.isEmpty())
          {
            this.versionRequirement_ = paramTypeAlias.versionRequirement_;
            this.bitField0_ &= 0xFEFF;
          }
          else
          {
            ensureVersionRequirementIsMutable();
            this.versionRequirement_.addAll(paramTypeAlias.versionRequirement_);
          }
        }
        mergeExtensionFields(paramTypeAlias);
        setUnknownFields(getUnknownFields().concat(paramTypeAlias.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 318	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 324 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 150	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_2
        //   29: aload_3
        //   30: astore_1
        //   31: goto +15 -> 46
        //   34: astore_2
        //   35: aload_2
        //   36: invokevirtual 327	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   39: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias
        //   42: astore_1
        //   43: aload_2
        //   44: athrow
        //   45: astore_2
        //   46: aload_1
        //   47: ifnull +9 -> 56
        //   50: aload_0
        //   51: aload_1
        //   52: invokevirtual 150	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeAlias$Builder;
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
      
      public Builder mergeUnderlyingType(ProtoBuf.Type paramType)
      {
        if (((this.bitField0_ & 0x8) == 8) && (this.underlyingType_ != ProtoBuf.Type.getDefaultInstance())) {
          this.underlyingType_ = ProtoBuf.Type.newBuilder(this.underlyingType_).mergeFrom(paramType).buildPartial();
        } else {
          this.underlyingType_ = paramType;
        }
        this.bitField0_ |= 0x8;
        return this;
      }
      
      public Builder setExpandedTypeId(int paramInt)
      {
        this.bitField0_ |= 0x40;
        this.expandedTypeId_ = paramInt;
        return this;
      }
      
      public Builder setFlags(int paramInt)
      {
        this.bitField0_ |= 0x1;
        this.flags_ = paramInt;
        return this;
      }
      
      public Builder setName(int paramInt)
      {
        this.bitField0_ |= 0x2;
        this.name_ = paramInt;
        return this;
      }
      
      public Builder setUnderlyingTypeId(int paramInt)
      {
        this.bitField0_ |= 0x10;
        this.underlyingTypeId_ = paramInt;
        return this;
      }
    }
  }
  
  public static final class TypeParameter
    extends GeneratedMessageLite.ExtendableMessage<TypeParameter>
    implements ProtoBuf.TypeParameterOrBuilder
  {
    public static Parser<TypeParameter> PARSER = new AbstractParser()
    {
      public ProtoBuf.TypeParameter parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new ProtoBuf.TypeParameter(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final TypeParameter defaultInstance;
    private int bitField0_;
    private int id_;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private int name_;
    private boolean reified_;
    private final ByteString unknownFields;
    private int upperBoundIdMemoizedSerializedSize = -1;
    private List<Integer> upperBoundId_;
    private List<ProtoBuf.Type> upperBound_;
    private Variance variance_;
    
    static
    {
      TypeParameter localTypeParameter = new TypeParameter(true);
      defaultInstance = localTypeParameter;
      localTypeParameter.initFields();
    }
    
    /* Error */
    private TypeParameter(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 66	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite$ExtendableMessage:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: putfield 68	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:upperBoundIdMemoizedSerializedSize	I
      //   9: aload_0
      //   10: iconst_m1
      //   11: i2b
      //   12: putfield 70	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:memoizedIsInitialized	B
      //   15: aload_0
      //   16: iconst_m1
      //   17: putfield 72	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:memoizedSerializedSize	I
      //   20: aload_0
      //   21: invokespecial 59	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:initFields	()V
      //   24: invokestatic 78	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   27: astore_3
      //   28: aload_3
      //   29: iconst_1
      //   30: invokestatic 84	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   33: astore 4
      //   35: iconst_0
      //   36: istore 5
      //   38: iconst_0
      //   39: istore 6
      //   41: iload 5
      //   43: ifne +905 -> 948
      //   46: iload 6
      //   48: istore 7
      //   50: iload 6
      //   52: istore 8
      //   54: iload 6
      //   56: istore 9
      //   58: aload_1
      //   59: invokevirtual 90	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   62: istore 10
      //   64: iload 10
      //   66: ifeq +755 -> 821
      //   69: iload 10
      //   71: bipush 8
      //   73: if_icmpeq +703 -> 776
      //   76: iload 10
      //   78: bipush 16
      //   80: if_icmpeq +651 -> 731
      //   83: iload 10
      //   85: bipush 24
      //   87: if_icmpeq +599 -> 686
      //   90: iload 10
      //   92: bipush 32
      //   94: if_icmpeq +465 -> 559
      //   97: iload 10
      //   99: bipush 42
      //   101: if_icmpeq +348 -> 449
      //   104: iload 10
      //   106: bipush 48
      //   108: if_icmpeq +232 -> 340
      //   111: iload 10
      //   113: bipush 50
      //   115: if_icmpeq +31 -> 146
      //   118: iload 6
      //   120: istore 7
      //   122: iload 6
      //   124: istore 8
      //   126: iload 6
      //   128: istore 9
      //   130: aload_0
      //   131: aload_1
      //   132: aload 4
      //   134: aload_2
      //   135: iload 10
      //   137: invokevirtual 94	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   140: ifne -99 -> 41
      //   143: goto +678 -> 821
      //   146: iload 6
      //   148: istore 7
      //   150: iload 6
      //   152: istore 8
      //   154: iload 6
      //   156: istore 9
      //   158: aload_1
      //   159: aload_1
      //   160: invokevirtual 97	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readRawVarint32	()I
      //   163: invokevirtual 101	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:pushLimit	(I)I
      //   166: istore 11
      //   168: iload 6
      //   170: istore 10
      //   172: iload 6
      //   174: bipush 32
      //   176: iand
      //   177: bipush 32
      //   179: if_icmpeq +85 -> 264
      //   182: iload 6
      //   184: istore 10
      //   186: iload 6
      //   188: istore 7
      //   190: iload 6
      //   192: istore 8
      //   194: iload 6
      //   196: istore 9
      //   198: aload_1
      //   199: invokevirtual 104	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
      //   202: ifle +62 -> 264
      //   205: iload 6
      //   207: istore 7
      //   209: iload 6
      //   211: istore 8
      //   213: iload 6
      //   215: istore 9
      //   217: new 106	java/util/ArrayList
      //   220: astore 12
      //   222: iload 6
      //   224: istore 7
      //   226: iload 6
      //   228: istore 8
      //   230: iload 6
      //   232: istore 9
      //   234: aload 12
      //   236: invokespecial 107	java/util/ArrayList:<init>	()V
      //   239: iload 6
      //   241: istore 7
      //   243: iload 6
      //   245: istore 8
      //   247: iload 6
      //   249: istore 9
      //   251: aload_0
      //   252: aload 12
      //   254: putfield 109	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:upperBoundId_	Ljava/util/List;
      //   257: iload 6
      //   259: bipush 32
      //   261: ior
      //   262: istore 10
      //   264: iload 10
      //   266: istore 7
      //   268: iload 10
      //   270: istore 8
      //   272: iload 10
      //   274: istore 9
      //   276: aload_1
      //   277: invokevirtual 104	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:getBytesUntilLimit	()I
      //   280: ifle +35 -> 315
      //   283: iload 10
      //   285: istore 7
      //   287: iload 10
      //   289: istore 8
      //   291: iload 10
      //   293: istore 9
      //   295: aload_0
      //   296: getfield 109	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:upperBoundId_	Ljava/util/List;
      //   299: aload_1
      //   300: invokevirtual 112	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   303: invokestatic 118	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   306: invokeinterface 124 2 0
      //   311: pop
      //   312: goto -48 -> 264
      //   315: iload 10
      //   317: istore 7
      //   319: iload 10
      //   321: istore 8
      //   323: iload 10
      //   325: istore 9
      //   327: aload_1
      //   328: iload 11
      //   330: invokevirtual 128	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:popLimit	(I)V
      //   333: iload 10
      //   335: istore 6
      //   337: goto -296 -> 41
      //   340: iload 6
      //   342: istore 10
      //   344: iload 6
      //   346: bipush 32
      //   348: iand
      //   349: bipush 32
      //   351: if_icmpeq +62 -> 413
      //   354: iload 6
      //   356: istore 7
      //   358: iload 6
      //   360: istore 8
      //   362: iload 6
      //   364: istore 9
      //   366: new 106	java/util/ArrayList
      //   369: astore 12
      //   371: iload 6
      //   373: istore 7
      //   375: iload 6
      //   377: istore 8
      //   379: iload 6
      //   381: istore 9
      //   383: aload 12
      //   385: invokespecial 107	java/util/ArrayList:<init>	()V
      //   388: iload 6
      //   390: istore 7
      //   392: iload 6
      //   394: istore 8
      //   396: iload 6
      //   398: istore 9
      //   400: aload_0
      //   401: aload 12
      //   403: putfield 109	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:upperBoundId_	Ljava/util/List;
      //   406: iload 6
      //   408: bipush 32
      //   410: ior
      //   411: istore 10
      //   413: iload 10
      //   415: istore 7
      //   417: iload 10
      //   419: istore 8
      //   421: iload 10
      //   423: istore 9
      //   425: aload_0
      //   426: getfield 109	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:upperBoundId_	Ljava/util/List;
      //   429: aload_1
      //   430: invokevirtual 112	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   433: invokestatic 118	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   436: invokeinterface 124 2 0
      //   441: pop
      //   442: iload 10
      //   444: istore 6
      //   446: goto -405 -> 41
      //   449: iload 6
      //   451: istore 10
      //   453: iload 6
      //   455: bipush 16
      //   457: iand
      //   458: bipush 16
      //   460: if_icmpeq +62 -> 522
      //   463: iload 6
      //   465: istore 7
      //   467: iload 6
      //   469: istore 8
      //   471: iload 6
      //   473: istore 9
      //   475: new 106	java/util/ArrayList
      //   478: astore 12
      //   480: iload 6
      //   482: istore 7
      //   484: iload 6
      //   486: istore 8
      //   488: iload 6
      //   490: istore 9
      //   492: aload 12
      //   494: invokespecial 107	java/util/ArrayList:<init>	()V
      //   497: iload 6
      //   499: istore 7
      //   501: iload 6
      //   503: istore 8
      //   505: iload 6
      //   507: istore 9
      //   509: aload_0
      //   510: aload 12
      //   512: putfield 130	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:upperBound_	Ljava/util/List;
      //   515: iload 6
      //   517: bipush 16
      //   519: ior
      //   520: istore 10
      //   522: iload 10
      //   524: istore 7
      //   526: iload 10
      //   528: istore 8
      //   530: iload 10
      //   532: istore 9
      //   534: aload_0
      //   535: getfield 130	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:upperBound_	Ljava/util/List;
      //   538: aload_1
      //   539: getstatic 133	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   542: aload_2
      //   543: invokevirtual 137	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   546: invokeinterface 124 2 0
      //   551: pop
      //   552: iload 10
      //   554: istore 6
      //   556: goto -515 -> 41
      //   559: iload 6
      //   561: istore 7
      //   563: iload 6
      //   565: istore 8
      //   567: iload 6
      //   569: istore 9
      //   571: aload_1
      //   572: invokevirtual 140	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readEnum	()I
      //   575: istore 11
      //   577: iload 6
      //   579: istore 7
      //   581: iload 6
      //   583: istore 8
      //   585: iload 6
      //   587: istore 9
      //   589: iload 11
      //   591: invokestatic 143	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter$Variance:valueOf	(I)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter$Variance;
      //   594: astore 12
      //   596: aload 12
      //   598: ifnonnull +44 -> 642
      //   601: iload 6
      //   603: istore 7
      //   605: iload 6
      //   607: istore 8
      //   609: iload 6
      //   611: istore 9
      //   613: aload 4
      //   615: iload 10
      //   617: invokevirtual 146	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:writeRawVarint32	(I)V
      //   620: iload 6
      //   622: istore 7
      //   624: iload 6
      //   626: istore 8
      //   628: iload 6
      //   630: istore 9
      //   632: aload 4
      //   634: iload 11
      //   636: invokevirtual 146	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:writeRawVarint32	(I)V
      //   639: goto -598 -> 41
      //   642: iload 6
      //   644: istore 7
      //   646: iload 6
      //   648: istore 8
      //   650: iload 6
      //   652: istore 9
      //   654: aload_0
      //   655: aload_0
      //   656: getfield 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:bitField0_	I
      //   659: bipush 8
      //   661: ior
      //   662: putfield 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:bitField0_	I
      //   665: iload 6
      //   667: istore 7
      //   669: iload 6
      //   671: istore 8
      //   673: iload 6
      //   675: istore 9
      //   677: aload_0
      //   678: aload 12
      //   680: putfield 150	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:variance_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter$Variance;
      //   683: goto -642 -> 41
      //   686: iload 6
      //   688: istore 7
      //   690: iload 6
      //   692: istore 8
      //   694: iload 6
      //   696: istore 9
      //   698: aload_0
      //   699: aload_0
      //   700: getfield 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:bitField0_	I
      //   703: iconst_4
      //   704: ior
      //   705: putfield 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:bitField0_	I
      //   708: iload 6
      //   710: istore 7
      //   712: iload 6
      //   714: istore 8
      //   716: iload 6
      //   718: istore 9
      //   720: aload_0
      //   721: aload_1
      //   722: invokevirtual 154	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readBool	()Z
      //   725: putfield 156	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:reified_	Z
      //   728: goto -687 -> 41
      //   731: iload 6
      //   733: istore 7
      //   735: iload 6
      //   737: istore 8
      //   739: iload 6
      //   741: istore 9
      //   743: aload_0
      //   744: aload_0
      //   745: getfield 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:bitField0_	I
      //   748: iconst_2
      //   749: ior
      //   750: putfield 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:bitField0_	I
      //   753: iload 6
      //   755: istore 7
      //   757: iload 6
      //   759: istore 8
      //   761: iload 6
      //   763: istore 9
      //   765: aload_0
      //   766: aload_1
      //   767: invokevirtual 112	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   770: putfield 158	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:name_	I
      //   773: goto -732 -> 41
      //   776: iload 6
      //   778: istore 7
      //   780: iload 6
      //   782: istore 8
      //   784: iload 6
      //   786: istore 9
      //   788: aload_0
      //   789: aload_0
      //   790: getfield 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:bitField0_	I
      //   793: iconst_1
      //   794: ior
      //   795: putfield 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:bitField0_	I
      //   798: iload 6
      //   800: istore 7
      //   802: iload 6
      //   804: istore 8
      //   806: iload 6
      //   808: istore 9
      //   810: aload_0
      //   811: aload_1
      //   812: invokevirtual 112	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   815: putfield 160	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:id_	I
      //   818: goto -777 -> 41
      //   821: iconst_1
      //   822: istore 5
      //   824: goto -783 -> 41
      //   827: astore_1
      //   828: goto +45 -> 873
      //   831: astore_2
      //   832: iload 8
      //   834: istore 7
      //   836: new 63	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   839: astore_1
      //   840: iload 8
      //   842: istore 7
      //   844: aload_1
      //   845: aload_2
      //   846: invokevirtual 164	java/io/IOException:getMessage	()Ljava/lang/String;
      //   849: invokespecial 167	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   852: iload 8
      //   854: istore 7
      //   856: aload_1
      //   857: aload_0
      //   858: invokevirtual 171	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   861: athrow
      //   862: astore_1
      //   863: iload 9
      //   865: istore 7
      //   867: aload_1
      //   868: aload_0
      //   869: invokevirtual 171	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   872: athrow
      //   873: iload 7
      //   875: bipush 16
      //   877: iand
      //   878: bipush 16
      //   880: if_icmpne +14 -> 894
      //   883: aload_0
      //   884: aload_0
      //   885: getfield 130	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:upperBound_	Ljava/util/List;
      //   888: invokestatic 177	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   891: putfield 130	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:upperBound_	Ljava/util/List;
      //   894: iload 7
      //   896: bipush 32
      //   898: iand
      //   899: bipush 32
      //   901: if_icmpne +14 -> 915
      //   904: aload_0
      //   905: aload_0
      //   906: getfield 109	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:upperBoundId_	Ljava/util/List;
      //   909: invokestatic 177	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   912: putfield 109	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:upperBoundId_	Ljava/util/List;
      //   915: aload 4
      //   917: invokevirtual 180	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   920: aload_0
      //   921: aload_3
      //   922: invokevirtual 186	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   925: putfield 188	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   928: goto +14 -> 942
      //   931: astore_1
      //   932: aload_0
      //   933: aload_3
      //   934: invokevirtual 186	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   937: putfield 188	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   940: aload_1
      //   941: athrow
      //   942: aload_0
      //   943: invokevirtual 191	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:makeExtensionsImmutable	()V
      //   946: aload_1
      //   947: athrow
      //   948: iload 6
      //   950: bipush 16
      //   952: iand
      //   953: bipush 16
      //   955: if_icmpne +14 -> 969
      //   958: aload_0
      //   959: aload_0
      //   960: getfield 130	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:upperBound_	Ljava/util/List;
      //   963: invokestatic 177	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   966: putfield 130	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:upperBound_	Ljava/util/List;
      //   969: iload 6
      //   971: bipush 32
      //   973: iand
      //   974: bipush 32
      //   976: if_icmpne +14 -> 990
      //   979: aload_0
      //   980: aload_0
      //   981: getfield 109	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:upperBoundId_	Ljava/util/List;
      //   984: invokestatic 177	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   987: putfield 109	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:upperBoundId_	Ljava/util/List;
      //   990: aload 4
      //   992: invokevirtual 180	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   995: aload_0
      //   996: aload_3
      //   997: invokevirtual 186	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1000: putfield 188	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1003: goto +14 -> 1017
      //   1006: astore_1
      //   1007: aload_0
      //   1008: aload_3
      //   1009: invokevirtual 186	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1012: putfield 188	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   1015: aload_1
      //   1016: athrow
      //   1017: aload_0
      //   1018: invokevirtual 191	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:makeExtensionsImmutable	()V
      //   1021: return
      //   1022: astore_2
      //   1023: goto -103 -> 920
      //   1026: astore_1
      //   1027: goto -32 -> 995
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	1030	0	this	TypeParameter
      //   0	1030	1	paramCodedInputStream	CodedInputStream
      //   0	1030	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   27	982	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   33	958	4	localCodedOutputStream	CodedOutputStream
      //   36	787	5	i	int
      //   39	935	6	j	int
      //   48	851	7	k	int
      //   52	801	8	m	int
      //   56	808	9	n	int
      //   62	554	10	i1	int
      //   166	469	11	i2	int
      //   220	459	12	localObject	Object
      // Exception table:
      //   from	to	target	type
      //   58	64	827	finally
      //   130	143	827	finally
      //   158	168	827	finally
      //   198	205	827	finally
      //   217	222	827	finally
      //   234	239	827	finally
      //   251	257	827	finally
      //   276	283	827	finally
      //   295	312	827	finally
      //   327	333	827	finally
      //   366	371	827	finally
      //   383	388	827	finally
      //   400	406	827	finally
      //   425	442	827	finally
      //   475	480	827	finally
      //   492	497	827	finally
      //   509	515	827	finally
      //   534	552	827	finally
      //   571	577	827	finally
      //   589	596	827	finally
      //   613	620	827	finally
      //   632	639	827	finally
      //   654	665	827	finally
      //   677	683	827	finally
      //   698	708	827	finally
      //   720	728	827	finally
      //   743	753	827	finally
      //   765	773	827	finally
      //   788	798	827	finally
      //   810	818	827	finally
      //   836	840	827	finally
      //   844	852	827	finally
      //   856	862	827	finally
      //   867	873	827	finally
      //   58	64	831	java/io/IOException
      //   130	143	831	java/io/IOException
      //   158	168	831	java/io/IOException
      //   198	205	831	java/io/IOException
      //   217	222	831	java/io/IOException
      //   234	239	831	java/io/IOException
      //   251	257	831	java/io/IOException
      //   276	283	831	java/io/IOException
      //   295	312	831	java/io/IOException
      //   327	333	831	java/io/IOException
      //   366	371	831	java/io/IOException
      //   383	388	831	java/io/IOException
      //   400	406	831	java/io/IOException
      //   425	442	831	java/io/IOException
      //   475	480	831	java/io/IOException
      //   492	497	831	java/io/IOException
      //   509	515	831	java/io/IOException
      //   534	552	831	java/io/IOException
      //   571	577	831	java/io/IOException
      //   589	596	831	java/io/IOException
      //   613	620	831	java/io/IOException
      //   632	639	831	java/io/IOException
      //   654	665	831	java/io/IOException
      //   677	683	831	java/io/IOException
      //   698	708	831	java/io/IOException
      //   720	728	831	java/io/IOException
      //   743	753	831	java/io/IOException
      //   765	773	831	java/io/IOException
      //   788	798	831	java/io/IOException
      //   810	818	831	java/io/IOException
      //   58	64	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   130	143	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   158	168	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   198	205	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   217	222	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   234	239	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   251	257	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   276	283	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   295	312	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   327	333	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   366	371	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   383	388	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   400	406	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   425	442	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   475	480	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   492	497	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   509	515	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   534	552	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   571	577	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   589	596	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   613	620	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   632	639	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   654	665	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   677	683	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   698	708	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   720	728	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   743	753	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   765	773	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   788	798	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   810	818	862	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   915	920	931	finally
      //   990	995	1006	finally
      //   915	920	1022	java/io/IOException
      //   990	995	1026	java/io/IOException
    }
    
    private TypeParameter(GeneratedMessageLite.ExtendableBuilder<TypeParameter, ?> paramExtendableBuilder)
    {
      super();
      this.unknownFields = paramExtendableBuilder.getUnknownFields();
    }
    
    private TypeParameter(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static TypeParameter getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.id_ = 0;
      this.name_ = 0;
      this.reified_ = false;
      this.variance_ = Variance.INV;
      this.upperBound_ = Collections.emptyList();
      this.upperBoundId_ = Collections.emptyList();
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$7600();
    }
    
    public static Builder newBuilder(TypeParameter paramTypeParameter)
    {
      return newBuilder().mergeFrom(paramTypeParameter);
    }
    
    public TypeParameter getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public int getId()
    {
      return this.id_;
    }
    
    public int getName()
    {
      return this.name_;
    }
    
    public Parser<TypeParameter> getParserForType()
    {
      return PARSER;
    }
    
    public boolean getReified()
    {
      return this.reified_;
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
        k = CodedOutputStream.computeInt32Size(1, this.id_) + 0;
      } else {
        k = 0;
      }
      i = k;
      if ((this.bitField0_ & 0x2) == 2) {
        i = k + CodedOutputStream.computeInt32Size(2, this.name_);
      }
      int k = i;
      if ((this.bitField0_ & 0x4) == 4) {
        k = i + CodedOutputStream.computeBoolSize(3, this.reified_);
      }
      i = k;
      if ((this.bitField0_ & 0x8) == 8) {
        i = k + CodedOutputStream.computeEnumSize(4, this.variance_.getNumber());
      }
      for (k = 0; k < this.upperBound_.size(); k++) {
        i += CodedOutputStream.computeMessageSize(5, (MessageLite)this.upperBound_.get(k));
      }
      k = 0;
      while (j < this.upperBoundId_.size())
      {
        k += CodedOutputStream.computeInt32SizeNoTag(((Integer)this.upperBoundId_.get(j)).intValue());
        j++;
      }
      j = i + k;
      i = j;
      if (!getUpperBoundIdList().isEmpty()) {
        i = j + 1 + CodedOutputStream.computeInt32SizeNoTag(k);
      }
      this.upperBoundIdMemoizedSerializedSize = k;
      i = i + extensionsSerializedSize() + this.unknownFields.size();
      this.memoizedSerializedSize = i;
      return i;
    }
    
    public ProtoBuf.Type getUpperBound(int paramInt)
    {
      return (ProtoBuf.Type)this.upperBound_.get(paramInt);
    }
    
    public int getUpperBoundCount()
    {
      return this.upperBound_.size();
    }
    
    public List<Integer> getUpperBoundIdList()
    {
      return this.upperBoundId_;
    }
    
    public List<ProtoBuf.Type> getUpperBoundList()
    {
      return this.upperBound_;
    }
    
    public Variance getVariance()
    {
      return this.variance_;
    }
    
    public boolean hasId()
    {
      int i = this.bitField0_;
      boolean bool = true;
      if ((i & 0x1) != 1) {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasName()
    {
      boolean bool;
      if ((this.bitField0_ & 0x2) == 2) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasReified()
    {
      boolean bool;
      if ((this.bitField0_ & 0x4) == 4) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasVariance()
    {
      boolean bool;
      if ((this.bitField0_ & 0x8) == 8) {
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
      if (!hasId())
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      if (!hasName())
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      for (i = 0; i < getUpperBoundCount(); i++) {
        if (!getUpperBound(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
      }
      if (!extensionsAreInitialized())
      {
        this.memoizedIsInitialized = ((byte)0);
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
      GeneratedMessageLite.ExtendableMessage.ExtensionWriter localExtensionWriter = newExtensionWriter();
      if ((this.bitField0_ & 0x1) == 1) {
        paramCodedOutputStream.writeInt32(1, this.id_);
      }
      if ((this.bitField0_ & 0x2) == 2) {
        paramCodedOutputStream.writeInt32(2, this.name_);
      }
      if ((this.bitField0_ & 0x4) == 4) {
        paramCodedOutputStream.writeBool(3, this.reified_);
      }
      if ((this.bitField0_ & 0x8) == 8) {
        paramCodedOutputStream.writeEnum(4, this.variance_.getNumber());
      }
      int i = 0;
      for (int j = 0; j < this.upperBound_.size(); j++) {
        paramCodedOutputStream.writeMessage(5, (MessageLite)this.upperBound_.get(j));
      }
      j = i;
      if (getUpperBoundIdList().size() > 0)
      {
        paramCodedOutputStream.writeRawVarint32(50);
        paramCodedOutputStream.writeRawVarint32(this.upperBoundIdMemoizedSerializedSize);
      }
      for (j = i; j < this.upperBoundId_.size(); j++) {
        paramCodedOutputStream.writeInt32NoTag(((Integer)this.upperBoundId_.get(j)).intValue());
      }
      localExtensionWriter.writeUntil(1000, paramCodedOutputStream);
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Builder
      extends GeneratedMessageLite.ExtendableBuilder<ProtoBuf.TypeParameter, Builder>
      implements ProtoBuf.TypeParameterOrBuilder
    {
      private int bitField0_;
      private int id_;
      private int name_;
      private boolean reified_;
      private List<Integer> upperBoundId_ = Collections.emptyList();
      private List<ProtoBuf.Type> upperBound_ = Collections.emptyList();
      private ProtoBuf.TypeParameter.Variance variance_ = ProtoBuf.TypeParameter.Variance.INV;
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void ensureUpperBoundIdIsMutable()
      {
        if ((this.bitField0_ & 0x20) != 32)
        {
          this.upperBoundId_ = new ArrayList(this.upperBoundId_);
          this.bitField0_ |= 0x20;
        }
      }
      
      private void ensureUpperBoundIsMutable()
      {
        if ((this.bitField0_ & 0x10) != 16)
        {
          this.upperBound_ = new ArrayList(this.upperBound_);
          this.bitField0_ |= 0x10;
        }
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public ProtoBuf.TypeParameter build()
      {
        ProtoBuf.TypeParameter localTypeParameter = buildPartial();
        if (localTypeParameter.isInitialized()) {
          return localTypeParameter;
        }
        throw newUninitializedMessageException(localTypeParameter);
      }
      
      public ProtoBuf.TypeParameter buildPartial()
      {
        ProtoBuf.TypeParameter localTypeParameter = new ProtoBuf.TypeParameter(this, null);
        int i = this.bitField0_;
        int j = 1;
        if ((i & 0x1) != 1) {
          j = 0;
        }
        ProtoBuf.TypeParameter.access$7802(localTypeParameter, this.id_);
        int k = j;
        if ((i & 0x2) == 2) {
          k = j | 0x2;
        }
        ProtoBuf.TypeParameter.access$7902(localTypeParameter, this.name_);
        j = k;
        if ((i & 0x4) == 4) {
          j = k | 0x4;
        }
        ProtoBuf.TypeParameter.access$8002(localTypeParameter, this.reified_);
        k = j;
        if ((i & 0x8) == 8) {
          k = j | 0x8;
        }
        ProtoBuf.TypeParameter.access$8102(localTypeParameter, this.variance_);
        if ((this.bitField0_ & 0x10) == 16)
        {
          this.upperBound_ = Collections.unmodifiableList(this.upperBound_);
          this.bitField0_ &= 0xFFFFFFEF;
        }
        ProtoBuf.TypeParameter.access$8202(localTypeParameter, this.upperBound_);
        if ((this.bitField0_ & 0x20) == 32)
        {
          this.upperBoundId_ = Collections.unmodifiableList(this.upperBoundId_);
          this.bitField0_ &= 0xFFFFFFDF;
        }
        ProtoBuf.TypeParameter.access$8302(localTypeParameter, this.upperBoundId_);
        ProtoBuf.TypeParameter.access$8402(localTypeParameter, k);
        return localTypeParameter;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public ProtoBuf.TypeParameter getDefaultInstanceForType()
      {
        return ProtoBuf.TypeParameter.getDefaultInstance();
      }
      
      public ProtoBuf.Type getUpperBound(int paramInt)
      {
        return (ProtoBuf.Type)this.upperBound_.get(paramInt);
      }
      
      public int getUpperBoundCount()
      {
        return this.upperBound_.size();
      }
      
      public boolean hasId()
      {
        int i = this.bitField0_;
        boolean bool = true;
        if ((i & 0x1) != 1) {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasName()
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
        if (!hasId()) {
          return false;
        }
        if (!hasName()) {
          return false;
        }
        for (int i = 0; i < getUpperBoundCount(); i++) {
          if (!getUpperBound(i).isInitialized()) {
            return false;
          }
        }
        return extensionsAreInitialized();
      }
      
      public Builder mergeFrom(ProtoBuf.TypeParameter paramTypeParameter)
      {
        if (paramTypeParameter == ProtoBuf.TypeParameter.getDefaultInstance()) {
          return this;
        }
        if (paramTypeParameter.hasId()) {
          setId(paramTypeParameter.getId());
        }
        if (paramTypeParameter.hasName()) {
          setName(paramTypeParameter.getName());
        }
        if (paramTypeParameter.hasReified()) {
          setReified(paramTypeParameter.getReified());
        }
        if (paramTypeParameter.hasVariance()) {
          setVariance(paramTypeParameter.getVariance());
        }
        if (!paramTypeParameter.upperBound_.isEmpty()) {
          if (this.upperBound_.isEmpty())
          {
            this.upperBound_ = paramTypeParameter.upperBound_;
            this.bitField0_ &= 0xFFFFFFEF;
          }
          else
          {
            ensureUpperBoundIsMutable();
            this.upperBound_.addAll(paramTypeParameter.upperBound_);
          }
        }
        if (!paramTypeParameter.upperBoundId_.isEmpty()) {
          if (this.upperBoundId_.isEmpty())
          {
            this.upperBoundId_ = paramTypeParameter.upperBoundId_;
            this.bitField0_ &= 0xFFFFFFDF;
          }
          else
          {
            ensureUpperBoundIdIsMutable();
            this.upperBoundId_.addAll(paramTypeParameter.upperBoundId_);
          }
        }
        mergeExtensionFields(paramTypeParameter);
        setUnknownFields(getUnknownFields().concat(paramTypeParameter.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 255	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 261 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 131	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_1
        //   29: aload_3
        //   30: astore_2
        //   31: goto +15 -> 46
        //   34: astore_1
        //   35: aload_1
        //   36: invokevirtual 264	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   39: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter
        //   42: astore_2
        //   43: aload_1
        //   44: athrow
        //   45: astore_1
        //   46: aload_2
        //   47: ifnull +9 -> 56
        //   50: aload_0
        //   51: aload_2
        //   52: invokevirtual 131	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeParameter$Builder;
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
      
      public Builder setId(int paramInt)
      {
        this.bitField0_ |= 0x1;
        this.id_ = paramInt;
        return this;
      }
      
      public Builder setName(int paramInt)
      {
        this.bitField0_ |= 0x2;
        this.name_ = paramInt;
        return this;
      }
      
      public Builder setReified(boolean paramBoolean)
      {
        this.bitField0_ |= 0x4;
        this.reified_ = paramBoolean;
        return this;
      }
      
      public Builder setVariance(ProtoBuf.TypeParameter.Variance paramVariance)
      {
        if (paramVariance != null)
        {
          this.bitField0_ |= 0x8;
          this.variance_ = paramVariance;
          return this;
        }
        throw null;
      }
    }
    
    public static enum Variance
      implements Internal.EnumLite
    {
      private static Internal.EnumLiteMap<Variance> internalValueMap = new Internal.EnumLiteMap()
      {
        public ProtoBuf.TypeParameter.Variance findValueByNumber(int paramAnonymousInt)
        {
          return ProtoBuf.TypeParameter.Variance.valueOf(paramAnonymousInt);
        }
      };
      private final int value;
      
      static
      {
        Variance localVariance = new Variance("INV", 2, 2, 2);
        INV = localVariance;
        $VALUES = new Variance[] { IN, OUT, localVariance };
      }
      
      private Variance(int paramInt1, int paramInt2)
      {
        this.value = paramInt2;
      }
      
      public static Variance valueOf(int paramInt)
      {
        if (paramInt != 0)
        {
          if (paramInt != 1)
          {
            if (paramInt != 2) {
              return null;
            }
            return INV;
          }
          return OUT;
        }
        return IN;
      }
      
      public final int getNumber()
      {
        return this.value;
      }
    }
  }
  
  public static final class TypeTable
    extends GeneratedMessageLite
    implements ProtoBuf.TypeTableOrBuilder
  {
    public static Parser<TypeTable> PARSER = new AbstractParser()
    {
      public ProtoBuf.TypeTable parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new ProtoBuf.TypeTable(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final TypeTable defaultInstance;
    private int bitField0_;
    private int firstNullable_;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private List<ProtoBuf.Type> type_;
    private final ByteString unknownFields;
    
    static
    {
      TypeTable localTypeTable = new TypeTable(true);
      defaultInstance = localTypeTable;
      localTypeTable.initFields();
    }
    
    /* Error */
    private TypeTable(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 52	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: i2b
      //   7: putfield 54	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:memoizedIsInitialized	B
      //   10: aload_0
      //   11: iconst_m1
      //   12: putfield 56	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:memoizedSerializedSize	I
      //   15: aload_0
      //   16: invokespecial 45	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:initFields	()V
      //   19: invokestatic 62	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   22: astore_3
      //   23: aload_3
      //   24: iconst_1
      //   25: invokestatic 68	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   28: astore 4
      //   30: iconst_0
      //   31: istore 5
      //   33: iconst_0
      //   34: istore 6
      //   36: iload 5
      //   38: ifne +324 -> 362
      //   41: iload 6
      //   43: istore 7
      //   45: iload 6
      //   47: istore 8
      //   49: iload 6
      //   51: istore 9
      //   53: aload_1
      //   54: invokevirtual 74	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   57: istore 10
      //   59: iload 10
      //   61: ifeq +197 -> 258
      //   64: iload 10
      //   66: bipush 10
      //   68: if_icmpeq +83 -> 151
      //   71: iload 10
      //   73: bipush 16
      //   75: if_icmpeq +31 -> 106
      //   78: iload 6
      //   80: istore 7
      //   82: iload 6
      //   84: istore 8
      //   86: iload 6
      //   88: istore 9
      //   90: aload_0
      //   91: aload_1
      //   92: aload 4
      //   94: aload_2
      //   95: iload 10
      //   97: invokevirtual 78	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   100: ifne -64 -> 36
      //   103: goto +155 -> 258
      //   106: iload 6
      //   108: istore 7
      //   110: iload 6
      //   112: istore 8
      //   114: iload 6
      //   116: istore 9
      //   118: aload_0
      //   119: aload_0
      //   120: getfield 80	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:bitField0_	I
      //   123: iconst_1
      //   124: ior
      //   125: putfield 80	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:bitField0_	I
      //   128: iload 6
      //   130: istore 7
      //   132: iload 6
      //   134: istore 8
      //   136: iload 6
      //   138: istore 9
      //   140: aload_0
      //   141: aload_1
      //   142: invokevirtual 83	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   145: putfield 85	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:firstNullable_	I
      //   148: goto -112 -> 36
      //   151: iload 6
      //   153: istore 10
      //   155: iload 6
      //   157: iconst_1
      //   158: iand
      //   159: iconst_1
      //   160: if_icmpeq +61 -> 221
      //   163: iload 6
      //   165: istore 7
      //   167: iload 6
      //   169: istore 8
      //   171: iload 6
      //   173: istore 9
      //   175: new 87	java/util/ArrayList
      //   178: astore 11
      //   180: iload 6
      //   182: istore 7
      //   184: iload 6
      //   186: istore 8
      //   188: iload 6
      //   190: istore 9
      //   192: aload 11
      //   194: invokespecial 88	java/util/ArrayList:<init>	()V
      //   197: iload 6
      //   199: istore 7
      //   201: iload 6
      //   203: istore 8
      //   205: iload 6
      //   207: istore 9
      //   209: aload_0
      //   210: aload 11
      //   212: putfield 90	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:type_	Ljava/util/List;
      //   215: iload 6
      //   217: iconst_1
      //   218: ior
      //   219: istore 10
      //   221: iload 10
      //   223: istore 7
      //   225: iload 10
      //   227: istore 8
      //   229: iload 10
      //   231: istore 9
      //   233: aload_0
      //   234: getfield 90	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:type_	Ljava/util/List;
      //   237: aload_1
      //   238: getstatic 93	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   241: aload_2
      //   242: invokevirtual 97	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   245: invokeinterface 103 2 0
      //   250: pop
      //   251: iload 10
      //   253: istore 6
      //   255: goto -219 -> 36
      //   258: iconst_1
      //   259: istore 5
      //   261: goto -225 -> 36
      //   264: astore_1
      //   265: goto +45 -> 310
      //   268: astore_2
      //   269: iload 8
      //   271: istore 7
      //   273: new 49	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   276: astore_1
      //   277: iload 8
      //   279: istore 7
      //   281: aload_1
      //   282: aload_2
      //   283: invokevirtual 107	java/io/IOException:getMessage	()Ljava/lang/String;
      //   286: invokespecial 110	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   289: iload 8
      //   291: istore 7
      //   293: aload_1
      //   294: aload_0
      //   295: invokevirtual 114	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   298: athrow
      //   299: astore_1
      //   300: iload 9
      //   302: istore 7
      //   304: aload_1
      //   305: aload_0
      //   306: invokevirtual 114	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   309: athrow
      //   310: iload 7
      //   312: iconst_1
      //   313: iand
      //   314: iconst_1
      //   315: if_icmpne +14 -> 329
      //   318: aload_0
      //   319: aload_0
      //   320: getfield 90	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:type_	Ljava/util/List;
      //   323: invokestatic 120	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   326: putfield 90	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:type_	Ljava/util/List;
      //   329: aload 4
      //   331: invokevirtual 123	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   334: aload_0
      //   335: aload_3
      //   336: invokevirtual 129	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   339: putfield 131	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   342: goto +14 -> 356
      //   345: astore_1
      //   346: aload_0
      //   347: aload_3
      //   348: invokevirtual 129	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   351: putfield 131	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   354: aload_1
      //   355: athrow
      //   356: aload_0
      //   357: invokevirtual 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:makeExtensionsImmutable	()V
      //   360: aload_1
      //   361: athrow
      //   362: iload 6
      //   364: iconst_1
      //   365: iand
      //   366: iconst_1
      //   367: if_icmpne +14 -> 381
      //   370: aload_0
      //   371: aload_0
      //   372: getfield 90	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:type_	Ljava/util/List;
      //   375: invokestatic 120	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   378: putfield 90	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:type_	Ljava/util/List;
      //   381: aload 4
      //   383: invokevirtual 123	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   386: aload_0
      //   387: aload_3
      //   388: invokevirtual 129	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   391: putfield 131	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   394: goto +14 -> 408
      //   397: astore_1
      //   398: aload_0
      //   399: aload_3
      //   400: invokevirtual 129	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   403: putfield 131	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   406: aload_1
      //   407: athrow
      //   408: aload_0
      //   409: invokevirtual 134	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:makeExtensionsImmutable	()V
      //   412: return
      //   413: astore_2
      //   414: goto -80 -> 334
      //   417: astore_1
      //   418: goto -32 -> 386
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	421	0	this	TypeTable
      //   0	421	1	paramCodedInputStream	CodedInputStream
      //   0	421	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   22	378	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   28	354	4	localCodedOutputStream	CodedOutputStream
      //   31	229	5	i	int
      //   34	332	6	j	int
      //   43	271	7	k	int
      //   47	243	8	m	int
      //   51	250	9	n	int
      //   57	195	10	i1	int
      //   178	33	11	localArrayList	ArrayList
      // Exception table:
      //   from	to	target	type
      //   53	59	264	finally
      //   90	103	264	finally
      //   118	128	264	finally
      //   140	148	264	finally
      //   175	180	264	finally
      //   192	197	264	finally
      //   209	215	264	finally
      //   233	251	264	finally
      //   273	277	264	finally
      //   281	289	264	finally
      //   293	299	264	finally
      //   304	310	264	finally
      //   53	59	268	java/io/IOException
      //   90	103	268	java/io/IOException
      //   118	128	268	java/io/IOException
      //   140	148	268	java/io/IOException
      //   175	180	268	java/io/IOException
      //   192	197	268	java/io/IOException
      //   209	215	268	java/io/IOException
      //   233	251	268	java/io/IOException
      //   53	59	299	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   90	103	299	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   118	128	299	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   140	148	299	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   175	180	299	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   192	197	299	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   209	215	299	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   233	251	299	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   329	334	345	finally
      //   381	386	397	finally
      //   329	334	413	java/io/IOException
      //   381	386	417	java/io/IOException
    }
    
    private TypeTable(GeneratedMessageLite.Builder paramBuilder)
    {
      super();
      this.unknownFields = paramBuilder.getUnknownFields();
    }
    
    private TypeTable(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static TypeTable getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.type_ = Collections.emptyList();
      this.firstNullable_ = -1;
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$11800();
    }
    
    public static Builder newBuilder(TypeTable paramTypeTable)
    {
      return newBuilder().mergeFrom(paramTypeTable);
    }
    
    public TypeTable getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public int getFirstNullable()
    {
      return this.firstNullable_;
    }
    
    public Parser<TypeTable> getParserForType()
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
      i = 0;
      while (j < this.type_.size())
      {
        i += CodedOutputStream.computeMessageSize(1, (MessageLite)this.type_.get(j));
        j++;
      }
      j = i;
      if ((this.bitField0_ & 0x1) == 1) {
        j = i + CodedOutputStream.computeInt32Size(2, this.firstNullable_);
      }
      i = j + this.unknownFields.size();
      this.memoizedSerializedSize = i;
      return i;
    }
    
    public ProtoBuf.Type getType(int paramInt)
    {
      return (ProtoBuf.Type)this.type_.get(paramInt);
    }
    
    public int getTypeCount()
    {
      return this.type_.size();
    }
    
    public List<ProtoBuf.Type> getTypeList()
    {
      return this.type_;
    }
    
    public boolean hasFirstNullable()
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
      for (i = 0; i < getTypeCount(); i++) {
        if (!getType(i).isInitialized())
        {
          this.memoizedIsInitialized = ((byte)0);
          return false;
        }
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
      for (int i = 0; i < this.type_.size(); i++) {
        paramCodedOutputStream.writeMessage(1, (MessageLite)this.type_.get(i));
      }
      if ((this.bitField0_ & 0x1) == 1) {
        paramCodedOutputStream.writeInt32(2, this.firstNullable_);
      }
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Builder
      extends GeneratedMessageLite.Builder<ProtoBuf.TypeTable, Builder>
      implements ProtoBuf.TypeTableOrBuilder
    {
      private int bitField0_;
      private int firstNullable_ = -1;
      private List<ProtoBuf.Type> type_ = Collections.emptyList();
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void ensureTypeIsMutable()
      {
        if ((this.bitField0_ & 0x1) != 1)
        {
          this.type_ = new ArrayList(this.type_);
          this.bitField0_ |= 0x1;
        }
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public ProtoBuf.TypeTable build()
      {
        ProtoBuf.TypeTable localTypeTable = buildPartial();
        if (localTypeTable.isInitialized()) {
          return localTypeTable;
        }
        throw newUninitializedMessageException(localTypeTable);
      }
      
      public ProtoBuf.TypeTable buildPartial()
      {
        ProtoBuf.TypeTable localTypeTable = new ProtoBuf.TypeTable(this, null);
        int i = this.bitField0_;
        int j = 1;
        if ((i & 0x1) == 1)
        {
          this.type_ = Collections.unmodifiableList(this.type_);
          this.bitField0_ &= 0xFFFFFFFE;
        }
        ProtoBuf.TypeTable.access$12002(localTypeTable, this.type_);
        if ((i & 0x2) != 2) {
          j = 0;
        }
        ProtoBuf.TypeTable.access$12102(localTypeTable, this.firstNullable_);
        ProtoBuf.TypeTable.access$12202(localTypeTable, j);
        return localTypeTable;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public ProtoBuf.TypeTable getDefaultInstanceForType()
      {
        return ProtoBuf.TypeTable.getDefaultInstance();
      }
      
      public ProtoBuf.Type getType(int paramInt)
      {
        return (ProtoBuf.Type)this.type_.get(paramInt);
      }
      
      public int getTypeCount()
      {
        return this.type_.size();
      }
      
      public final boolean isInitialized()
      {
        for (int i = 0; i < getTypeCount(); i++) {
          if (!getType(i).isInitialized()) {
            return false;
          }
        }
        return true;
      }
      
      public Builder mergeFrom(ProtoBuf.TypeTable paramTypeTable)
      {
        if (paramTypeTable == ProtoBuf.TypeTable.getDefaultInstance()) {
          return this;
        }
        if (!paramTypeTable.type_.isEmpty()) {
          if (this.type_.isEmpty())
          {
            this.type_ = paramTypeTable.type_;
            this.bitField0_ &= 0xFFFFFFFE;
          }
          else
          {
            ensureTypeIsMutable();
            this.type_.addAll(paramTypeTable.type_);
          }
        }
        if (paramTypeTable.hasFirstNullable()) {
          setFirstNullable(paramTypeTable.getFirstNullable());
        }
        setUnknownFields(getUnknownFields().concat(paramTypeTable.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 175	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 181 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_1
        //   29: aload_3
        //   30: astore_2
        //   31: goto +15 -> 46
        //   34: astore_1
        //   35: aload_1
        //   36: invokevirtual 184	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   39: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable
        //   42: astore_2
        //   43: aload_1
        //   44: athrow
        //   45: astore_1
        //   46: aload_2
        //   47: ifnull +9 -> 56
        //   50: aload_0
        //   51: aload_2
        //   52: invokevirtual 96	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$TypeTable$Builder;
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
      
      public Builder setFirstNullable(int paramInt)
      {
        this.bitField0_ |= 0x2;
        this.firstNullable_ = paramInt;
        return this;
      }
    }
  }
  
  public static final class ValueParameter
    extends GeneratedMessageLite.ExtendableMessage<ValueParameter>
    implements ProtoBuf.ValueParameterOrBuilder
  {
    public static Parser<ValueParameter> PARSER = new AbstractParser()
    {
      public ProtoBuf.ValueParameter parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new ProtoBuf.ValueParameter(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final ValueParameter defaultInstance;
    private int bitField0_;
    private int flags_;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private int name_;
    private int typeId_;
    private ProtoBuf.Type type_;
    private final ByteString unknownFields;
    private int varargElementTypeId_;
    private ProtoBuf.Type varargElementType_;
    
    static
    {
      ValueParameter localValueParameter = new ValueParameter(true);
      defaultInstance = localValueParameter;
      localValueParameter.initFields();
    }
    
    /* Error */
    private ValueParameter(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 56	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite$ExtendableMessage:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: i2b
      //   7: putfield 58	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:memoizedIsInitialized	B
      //   10: aload_0
      //   11: iconst_m1
      //   12: putfield 60	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:memoizedSerializedSize	I
      //   15: aload_0
      //   16: invokespecial 49	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:initFields	()V
      //   19: invokestatic 66	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   22: astore_3
      //   23: aload_3
      //   24: iconst_1
      //   25: invokestatic 72	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   28: astore 4
      //   30: iconst_0
      //   31: istore 5
      //   33: iload 5
      //   35: ifne +386 -> 421
      //   38: aload_1
      //   39: invokevirtual 78	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   42: istore 6
      //   44: iload 6
      //   46: ifeq +306 -> 352
      //   49: iload 6
      //   51: bipush 8
      //   53: if_icmpeq +278 -> 331
      //   56: iload 6
      //   58: bipush 16
      //   60: if_icmpeq +250 -> 310
      //   63: aconst_null
      //   64: astore 7
      //   66: aconst_null
      //   67: astore 8
      //   69: iload 6
      //   71: bipush 26
      //   73: if_icmpeq +160 -> 233
      //   76: iload 6
      //   78: bipush 34
      //   80: if_icmpeq +77 -> 157
      //   83: iload 6
      //   85: bipush 40
      //   87: if_icmpeq +48 -> 135
      //   90: iload 6
      //   92: bipush 48
      //   94: if_icmpeq +19 -> 113
      //   97: aload_0
      //   98: aload_1
      //   99: aload 4
      //   101: aload_2
      //   102: iload 6
      //   104: invokevirtual 82	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   107: ifne -74 -> 33
      //   110: goto +242 -> 352
      //   113: aload_0
      //   114: aload_0
      //   115: getfield 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:bitField0_	I
      //   118: bipush 32
      //   120: ior
      //   121: putfield 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:bitField0_	I
      //   124: aload_0
      //   125: aload_1
      //   126: invokevirtual 87	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   129: putfield 89	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:varargElementTypeId_	I
      //   132: goto -99 -> 33
      //   135: aload_0
      //   136: aload_0
      //   137: getfield 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:bitField0_	I
      //   140: bipush 8
      //   142: ior
      //   143: putfield 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:bitField0_	I
      //   146: aload_0
      //   147: aload_1
      //   148: invokevirtual 87	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   151: putfield 91	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:typeId_	I
      //   154: goto -121 -> 33
      //   157: aload_0
      //   158: getfield 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:bitField0_	I
      //   161: bipush 16
      //   163: iand
      //   164: bipush 16
      //   166: if_icmpne +12 -> 178
      //   169: aload_0
      //   170: getfield 93	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:varargElementType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   173: invokevirtual 99	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   176: astore 8
      //   178: aload_1
      //   179: getstatic 100	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   182: aload_2
      //   183: invokevirtual 104	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   186: checkcast 95	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type
      //   189: astore 7
      //   191: aload_0
      //   192: aload 7
      //   194: putfield 93	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:varargElementType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   197: aload 8
      //   199: ifnull +20 -> 219
      //   202: aload 8
      //   204: aload 7
      //   206: invokevirtual 110	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   209: pop
      //   210: aload_0
      //   211: aload 8
      //   213: invokevirtual 114	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   216: putfield 93	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:varargElementType_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   219: aload_0
      //   220: aload_0
      //   221: getfield 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:bitField0_	I
      //   224: bipush 16
      //   226: ior
      //   227: putfield 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:bitField0_	I
      //   230: goto -197 -> 33
      //   233: aload 7
      //   235: astore 8
      //   237: aload_0
      //   238: getfield 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:bitField0_	I
      //   241: iconst_4
      //   242: iand
      //   243: iconst_4
      //   244: if_icmpne +12 -> 256
      //   247: aload_0
      //   248: getfield 116	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:type_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   251: invokevirtual 99	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:toBuilder	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   254: astore 8
      //   256: aload_1
      //   257: getstatic 100	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   260: aload_2
      //   261: invokevirtual 104	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   264: checkcast 95	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type
      //   267: astore 7
      //   269: aload_0
      //   270: aload 7
      //   272: putfield 116	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:type_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   275: aload 8
      //   277: ifnull +20 -> 297
      //   280: aload 8
      //   282: aload 7
      //   284: invokevirtual 110	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder;
      //   287: pop
      //   288: aload_0
      //   289: aload 8
      //   291: invokevirtual 114	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type$Builder:buildPartial	()Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   294: putfield 116	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:type_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Type;
      //   297: aload_0
      //   298: aload_0
      //   299: getfield 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:bitField0_	I
      //   302: iconst_4
      //   303: ior
      //   304: putfield 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:bitField0_	I
      //   307: goto -274 -> 33
      //   310: aload_0
      //   311: aload_0
      //   312: getfield 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:bitField0_	I
      //   315: iconst_2
      //   316: ior
      //   317: putfield 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:bitField0_	I
      //   320: aload_0
      //   321: aload_1
      //   322: invokevirtual 87	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   325: putfield 118	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:name_	I
      //   328: goto -295 -> 33
      //   331: aload_0
      //   332: aload_0
      //   333: getfield 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:bitField0_	I
      //   336: iconst_1
      //   337: ior
      //   338: putfield 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:bitField0_	I
      //   341: aload_0
      //   342: aload_1
      //   343: invokevirtual 87	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   346: putfield 120	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:flags_	I
      //   349: goto -316 -> 33
      //   352: iconst_1
      //   353: istore 5
      //   355: goto -322 -> 33
      //   358: astore_1
      //   359: goto +29 -> 388
      //   362: astore_2
      //   363: new 53	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   366: astore_1
      //   367: aload_1
      //   368: aload_2
      //   369: invokevirtual 124	java/io/IOException:getMessage	()Ljava/lang/String;
      //   372: invokespecial 127	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   375: aload_1
      //   376: aload_0
      //   377: invokevirtual 131	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   380: athrow
      //   381: astore_1
      //   382: aload_1
      //   383: aload_0
      //   384: invokevirtual 131	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   387: athrow
      //   388: aload 4
      //   390: invokevirtual 134	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   393: aload_0
      //   394: aload_3
      //   395: invokevirtual 140	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   398: putfield 142	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   401: goto +14 -> 415
      //   404: astore_1
      //   405: aload_0
      //   406: aload_3
      //   407: invokevirtual 140	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   410: putfield 142	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   413: aload_1
      //   414: athrow
      //   415: aload_0
      //   416: invokevirtual 145	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:makeExtensionsImmutable	()V
      //   419: aload_1
      //   420: athrow
      //   421: aload 4
      //   423: invokevirtual 134	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   426: aload_0
      //   427: aload_3
      //   428: invokevirtual 140	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   431: putfield 142	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   434: goto +14 -> 448
      //   437: astore_1
      //   438: aload_0
      //   439: aload_3
      //   440: invokevirtual 140	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   443: putfield 142	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   446: aload_1
      //   447: athrow
      //   448: aload_0
      //   449: invokevirtual 145	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:makeExtensionsImmutable	()V
      //   452: return
      //   453: astore_2
      //   454: goto -61 -> 393
      //   457: astore_1
      //   458: goto -32 -> 426
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	461	0	this	ValueParameter
      //   0	461	1	paramCodedInputStream	CodedInputStream
      //   0	461	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   22	418	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   28	394	4	localCodedOutputStream	CodedOutputStream
      //   31	323	5	i	int
      //   42	61	6	j	int
      //   64	219	7	localType	ProtoBuf.Type
      //   67	223	8	localObject	Object
      // Exception table:
      //   from	to	target	type
      //   38	44	358	finally
      //   97	110	358	finally
      //   113	132	358	finally
      //   135	154	358	finally
      //   157	178	358	finally
      //   178	197	358	finally
      //   202	219	358	finally
      //   219	230	358	finally
      //   237	256	358	finally
      //   256	275	358	finally
      //   280	297	358	finally
      //   297	307	358	finally
      //   310	328	358	finally
      //   331	349	358	finally
      //   363	381	358	finally
      //   382	388	358	finally
      //   38	44	362	java/io/IOException
      //   97	110	362	java/io/IOException
      //   113	132	362	java/io/IOException
      //   135	154	362	java/io/IOException
      //   157	178	362	java/io/IOException
      //   178	197	362	java/io/IOException
      //   202	219	362	java/io/IOException
      //   219	230	362	java/io/IOException
      //   237	256	362	java/io/IOException
      //   256	275	362	java/io/IOException
      //   280	297	362	java/io/IOException
      //   297	307	362	java/io/IOException
      //   310	328	362	java/io/IOException
      //   331	349	362	java/io/IOException
      //   38	44	381	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   97	110	381	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   113	132	381	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   135	154	381	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   157	178	381	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   178	197	381	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   202	219	381	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   219	230	381	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   237	256	381	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   256	275	381	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   280	297	381	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   297	307	381	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   310	328	381	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   331	349	381	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   388	393	404	finally
      //   421	426	437	finally
      //   388	393	453	java/io/IOException
      //   421	426	457	java/io/IOException
    }
    
    private ValueParameter(GeneratedMessageLite.ExtendableBuilder<ValueParameter, ?> paramExtendableBuilder)
    {
      super();
      this.unknownFields = paramExtendableBuilder.getUnknownFields();
    }
    
    private ValueParameter(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static ValueParameter getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.flags_ = 0;
      this.name_ = 0;
      this.type_ = ProtoBuf.Type.getDefaultInstance();
      this.typeId_ = 0;
      this.varargElementType_ = ProtoBuf.Type.getDefaultInstance();
      this.varargElementTypeId_ = 0;
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$16700();
    }
    
    public static Builder newBuilder(ValueParameter paramValueParameter)
    {
      return newBuilder().mergeFrom(paramValueParameter);
    }
    
    public ValueParameter getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public int getFlags()
    {
      return this.flags_;
    }
    
    public int getName()
    {
      return this.name_;
    }
    
    public Parser<ValueParameter> getParserForType()
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
        j = 0 + CodedOutputStream.computeInt32Size(1, this.flags_);
      }
      i = j;
      if ((this.bitField0_ & 0x2) == 2) {
        i = j + CodedOutputStream.computeInt32Size(2, this.name_);
      }
      j = i;
      if ((this.bitField0_ & 0x4) == 4) {
        j = i + CodedOutputStream.computeMessageSize(3, this.type_);
      }
      int k = j;
      if ((this.bitField0_ & 0x10) == 16) {
        k = j + CodedOutputStream.computeMessageSize(4, this.varargElementType_);
      }
      i = k;
      if ((this.bitField0_ & 0x8) == 8) {
        i = k + CodedOutputStream.computeInt32Size(5, this.typeId_);
      }
      j = i;
      if ((this.bitField0_ & 0x20) == 32) {
        j = i + CodedOutputStream.computeInt32Size(6, this.varargElementTypeId_);
      }
      i = j + extensionsSerializedSize() + this.unknownFields.size();
      this.memoizedSerializedSize = i;
      return i;
    }
    
    public ProtoBuf.Type getType()
    {
      return this.type_;
    }
    
    public int getTypeId()
    {
      return this.typeId_;
    }
    
    public ProtoBuf.Type getVarargElementType()
    {
      return this.varargElementType_;
    }
    
    public int getVarargElementTypeId()
    {
      return this.varargElementTypeId_;
    }
    
    public boolean hasFlags()
    {
      int i = this.bitField0_;
      boolean bool = true;
      if ((i & 0x1) != 1) {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasName()
    {
      boolean bool;
      if ((this.bitField0_ & 0x2) == 2) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasType()
    {
      boolean bool;
      if ((this.bitField0_ & 0x4) == 4) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasTypeId()
    {
      boolean bool;
      if ((this.bitField0_ & 0x8) == 8) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasVarargElementType()
    {
      boolean bool;
      if ((this.bitField0_ & 0x10) == 16) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasVarargElementTypeId()
    {
      boolean bool;
      if ((this.bitField0_ & 0x20) == 32) {
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
      if (!hasName())
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      if ((hasType()) && (!getType().isInitialized()))
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      if ((hasVarargElementType()) && (!getVarargElementType().isInitialized()))
      {
        this.memoizedIsInitialized = ((byte)0);
        return false;
      }
      if (!extensionsAreInitialized())
      {
        this.memoizedIsInitialized = ((byte)0);
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
      GeneratedMessageLite.ExtendableMessage.ExtensionWriter localExtensionWriter = newExtensionWriter();
      if ((this.bitField0_ & 0x1) == 1) {
        paramCodedOutputStream.writeInt32(1, this.flags_);
      }
      if ((this.bitField0_ & 0x2) == 2) {
        paramCodedOutputStream.writeInt32(2, this.name_);
      }
      if ((this.bitField0_ & 0x4) == 4) {
        paramCodedOutputStream.writeMessage(3, this.type_);
      }
      if ((this.bitField0_ & 0x10) == 16) {
        paramCodedOutputStream.writeMessage(4, this.varargElementType_);
      }
      if ((this.bitField0_ & 0x8) == 8) {
        paramCodedOutputStream.writeInt32(5, this.typeId_);
      }
      if ((this.bitField0_ & 0x20) == 32) {
        paramCodedOutputStream.writeInt32(6, this.varargElementTypeId_);
      }
      localExtensionWriter.writeUntil(200, paramCodedOutputStream);
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Builder
      extends GeneratedMessageLite.ExtendableBuilder<ProtoBuf.ValueParameter, Builder>
      implements ProtoBuf.ValueParameterOrBuilder
    {
      private int bitField0_;
      private int flags_;
      private int name_;
      private int typeId_;
      private ProtoBuf.Type type_ = ProtoBuf.Type.getDefaultInstance();
      private int varargElementTypeId_;
      private ProtoBuf.Type varargElementType_ = ProtoBuf.Type.getDefaultInstance();
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public ProtoBuf.ValueParameter build()
      {
        ProtoBuf.ValueParameter localValueParameter = buildPartial();
        if (localValueParameter.isInitialized()) {
          return localValueParameter;
        }
        throw newUninitializedMessageException(localValueParameter);
      }
      
      public ProtoBuf.ValueParameter buildPartial()
      {
        ProtoBuf.ValueParameter localValueParameter = new ProtoBuf.ValueParameter(this, null);
        int i = this.bitField0_;
        int j = 1;
        if ((i & 0x1) != 1) {
          j = 0;
        }
        ProtoBuf.ValueParameter.access$16902(localValueParameter, this.flags_);
        int k = j;
        if ((i & 0x2) == 2) {
          k = j | 0x2;
        }
        ProtoBuf.ValueParameter.access$17002(localValueParameter, this.name_);
        int m = k;
        if ((i & 0x4) == 4) {
          m = k | 0x4;
        }
        ProtoBuf.ValueParameter.access$17102(localValueParameter, this.type_);
        j = m;
        if ((i & 0x8) == 8) {
          j = m | 0x8;
        }
        ProtoBuf.ValueParameter.access$17202(localValueParameter, this.typeId_);
        k = j;
        if ((i & 0x10) == 16) {
          k = j | 0x10;
        }
        ProtoBuf.ValueParameter.access$17302(localValueParameter, this.varargElementType_);
        j = k;
        if ((i & 0x20) == 32) {
          j = k | 0x20;
        }
        ProtoBuf.ValueParameter.access$17402(localValueParameter, this.varargElementTypeId_);
        ProtoBuf.ValueParameter.access$17502(localValueParameter, j);
        return localValueParameter;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public ProtoBuf.ValueParameter getDefaultInstanceForType()
      {
        return ProtoBuf.ValueParameter.getDefaultInstance();
      }
      
      public ProtoBuf.Type getType()
      {
        return this.type_;
      }
      
      public ProtoBuf.Type getVarargElementType()
      {
        return this.varargElementType_;
      }
      
      public boolean hasName()
      {
        boolean bool;
        if ((this.bitField0_ & 0x2) == 2) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasType()
      {
        boolean bool;
        if ((this.bitField0_ & 0x4) == 4) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public boolean hasVarargElementType()
      {
        boolean bool;
        if ((this.bitField0_ & 0x10) == 16) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public final boolean isInitialized()
      {
        if (!hasName()) {
          return false;
        }
        if ((hasType()) && (!getType().isInitialized())) {
          return false;
        }
        if ((hasVarargElementType()) && (!getVarargElementType().isInitialized())) {
          return false;
        }
        return extensionsAreInitialized();
      }
      
      public Builder mergeFrom(ProtoBuf.ValueParameter paramValueParameter)
      {
        if (paramValueParameter == ProtoBuf.ValueParameter.getDefaultInstance()) {
          return this;
        }
        if (paramValueParameter.hasFlags()) {
          setFlags(paramValueParameter.getFlags());
        }
        if (paramValueParameter.hasName()) {
          setName(paramValueParameter.getName());
        }
        if (paramValueParameter.hasType()) {
          mergeType(paramValueParameter.getType());
        }
        if (paramValueParameter.hasTypeId()) {
          setTypeId(paramValueParameter.getTypeId());
        }
        if (paramValueParameter.hasVarargElementType()) {
          mergeVarargElementType(paramValueParameter.getVarargElementType());
        }
        if (paramValueParameter.hasVarargElementTypeId()) {
          setVarargElementTypeId(paramValueParameter.getVarargElementTypeId());
        }
        mergeExtensionFields(paramValueParameter);
        setUnknownFields(getUnknownFields().concat(paramValueParameter.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 215	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 221 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 109	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_2
        //   29: aload_3
        //   30: astore_1
        //   31: goto +15 -> 46
        //   34: astore_2
        //   35: aload_2
        //   36: invokevirtual 224	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   39: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter
        //   42: astore_1
        //   43: aload_2
        //   44: athrow
        //   45: astore_2
        //   46: aload_1
        //   47: ifnull +9 -> 56
        //   50: aload_0
        //   51: aload_1
        //   52: invokevirtual 109	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$ValueParameter$Builder;
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
      
      public Builder mergeType(ProtoBuf.Type paramType)
      {
        if (((this.bitField0_ & 0x4) == 4) && (this.type_ != ProtoBuf.Type.getDefaultInstance())) {
          this.type_ = ProtoBuf.Type.newBuilder(this.type_).mergeFrom(paramType).buildPartial();
        } else {
          this.type_ = paramType;
        }
        this.bitField0_ |= 0x4;
        return this;
      }
      
      public Builder mergeVarargElementType(ProtoBuf.Type paramType)
      {
        if (((this.bitField0_ & 0x10) == 16) && (this.varargElementType_ != ProtoBuf.Type.getDefaultInstance())) {
          this.varargElementType_ = ProtoBuf.Type.newBuilder(this.varargElementType_).mergeFrom(paramType).buildPartial();
        } else {
          this.varargElementType_ = paramType;
        }
        this.bitField0_ |= 0x10;
        return this;
      }
      
      public Builder setFlags(int paramInt)
      {
        this.bitField0_ |= 0x1;
        this.flags_ = paramInt;
        return this;
      }
      
      public Builder setName(int paramInt)
      {
        this.bitField0_ |= 0x2;
        this.name_ = paramInt;
        return this;
      }
      
      public Builder setTypeId(int paramInt)
      {
        this.bitField0_ |= 0x8;
        this.typeId_ = paramInt;
        return this;
      }
      
      public Builder setVarargElementTypeId(int paramInt)
      {
        this.bitField0_ |= 0x20;
        this.varargElementTypeId_ = paramInt;
        return this;
      }
    }
  }
  
  public static final class VersionRequirement
    extends GeneratedMessageLite
    implements ProtoBuf.VersionRequirementOrBuilder
  {
    public static Parser<VersionRequirement> PARSER = new AbstractParser()
    {
      public ProtoBuf.VersionRequirement parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new ProtoBuf.VersionRequirement(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final VersionRequirement defaultInstance;
    private int bitField0_;
    private int errorCode_;
    private Level level_;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private int message_;
    private final ByteString unknownFields;
    private int versionFull_;
    private VersionKind versionKind_;
    private int version_;
    
    static
    {
      VersionRequirement localVersionRequirement = new VersionRequirement(true);
      defaultInstance = localVersionRequirement;
      localVersionRequirement.initFields();
    }
    
    /* Error */
    private VersionRequirement(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 66	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: i2b
      //   7: putfield 68	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:memoizedIsInitialized	B
      //   10: aload_0
      //   11: iconst_m1
      //   12: putfield 70	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:memoizedSerializedSize	I
      //   15: aload_0
      //   16: invokespecial 59	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:initFields	()V
      //   19: invokestatic 76	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   22: astore_3
      //   23: aload_3
      //   24: iconst_1
      //   25: invokestatic 82	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   28: astore 4
      //   30: iconst_0
      //   31: istore 5
      //   33: iload 5
      //   35: ifne +336 -> 371
      //   38: aload_1
      //   39: invokevirtual 88	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   42: istore 6
      //   44: iload 6
      //   46: ifeq +256 -> 302
      //   49: iload 6
      //   51: bipush 8
      //   53: if_icmpeq +228 -> 281
      //   56: iload 6
      //   58: bipush 16
      //   60: if_icmpeq +200 -> 260
      //   63: iload 6
      //   65: bipush 24
      //   67: if_icmpeq +139 -> 206
      //   70: iload 6
      //   72: bipush 32
      //   74: if_icmpeq +110 -> 184
      //   77: iload 6
      //   79: bipush 40
      //   81: if_icmpeq +81 -> 162
      //   84: iload 6
      //   86: bipush 48
      //   88: if_icmpeq +19 -> 107
      //   91: aload_0
      //   92: aload_1
      //   93: aload 4
      //   95: aload_2
      //   96: iload 6
      //   98: invokevirtual 92	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   101: ifne -68 -> 33
      //   104: goto +198 -> 302
      //   107: aload_1
      //   108: invokevirtual 95	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readEnum	()I
      //   111: istore 7
      //   113: iload 7
      //   115: invokestatic 99	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement$VersionKind:valueOf	(I)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement$VersionKind;
      //   118: astore 8
      //   120: aload 8
      //   122: ifnonnull +20 -> 142
      //   125: aload 4
      //   127: iload 6
      //   129: invokevirtual 103	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:writeRawVarint32	(I)V
      //   132: aload 4
      //   134: iload 7
      //   136: invokevirtual 103	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:writeRawVarint32	(I)V
      //   139: goto -106 -> 33
      //   142: aload_0
      //   143: aload_0
      //   144: getfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:bitField0_	I
      //   147: bipush 32
      //   149: ior
      //   150: putfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:bitField0_	I
      //   153: aload_0
      //   154: aload 8
      //   156: putfield 107	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:versionKind_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement$VersionKind;
      //   159: goto -126 -> 33
      //   162: aload_0
      //   163: aload_0
      //   164: getfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:bitField0_	I
      //   167: bipush 16
      //   169: ior
      //   170: putfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:bitField0_	I
      //   173: aload_0
      //   174: aload_1
      //   175: invokevirtual 110	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   178: putfield 112	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:message_	I
      //   181: goto -148 -> 33
      //   184: aload_0
      //   185: aload_0
      //   186: getfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:bitField0_	I
      //   189: bipush 8
      //   191: ior
      //   192: putfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:bitField0_	I
      //   195: aload_0
      //   196: aload_1
      //   197: invokevirtual 110	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   200: putfield 114	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:errorCode_	I
      //   203: goto -170 -> 33
      //   206: aload_1
      //   207: invokevirtual 95	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readEnum	()I
      //   210: istore 7
      //   212: iload 7
      //   214: invokestatic 117	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement$Level:valueOf	(I)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement$Level;
      //   217: astore 8
      //   219: aload 8
      //   221: ifnonnull +20 -> 241
      //   224: aload 4
      //   226: iload 6
      //   228: invokevirtual 103	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:writeRawVarint32	(I)V
      //   231: aload 4
      //   233: iload 7
      //   235: invokevirtual 103	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:writeRawVarint32	(I)V
      //   238: goto -205 -> 33
      //   241: aload_0
      //   242: aload_0
      //   243: getfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:bitField0_	I
      //   246: iconst_4
      //   247: ior
      //   248: putfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:bitField0_	I
      //   251: aload_0
      //   252: aload 8
      //   254: putfield 119	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:level_	Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement$Level;
      //   257: goto -224 -> 33
      //   260: aload_0
      //   261: aload_0
      //   262: getfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:bitField0_	I
      //   265: iconst_2
      //   266: ior
      //   267: putfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:bitField0_	I
      //   270: aload_0
      //   271: aload_1
      //   272: invokevirtual 110	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   275: putfield 121	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:versionFull_	I
      //   278: goto -245 -> 33
      //   281: aload_0
      //   282: aload_0
      //   283: getfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:bitField0_	I
      //   286: iconst_1
      //   287: ior
      //   288: putfield 105	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:bitField0_	I
      //   291: aload_0
      //   292: aload_1
      //   293: invokevirtual 110	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readInt32	()I
      //   296: putfield 123	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:version_	I
      //   299: goto -266 -> 33
      //   302: iconst_1
      //   303: istore 5
      //   305: goto -272 -> 33
      //   308: astore_1
      //   309: goto +29 -> 338
      //   312: astore_2
      //   313: new 63	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   316: astore_1
      //   317: aload_1
      //   318: aload_2
      //   319: invokevirtual 127	java/io/IOException:getMessage	()Ljava/lang/String;
      //   322: invokespecial 130	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   325: aload_1
      //   326: aload_0
      //   327: invokevirtual 134	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   330: athrow
      //   331: astore_1
      //   332: aload_1
      //   333: aload_0
      //   334: invokevirtual 134	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   337: athrow
      //   338: aload 4
      //   340: invokevirtual 137	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   343: aload_0
      //   344: aload_3
      //   345: invokevirtual 143	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   348: putfield 145	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   351: goto +14 -> 365
      //   354: astore_1
      //   355: aload_0
      //   356: aload_3
      //   357: invokevirtual 143	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   360: putfield 145	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   363: aload_1
      //   364: athrow
      //   365: aload_0
      //   366: invokevirtual 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:makeExtensionsImmutable	()V
      //   369: aload_1
      //   370: athrow
      //   371: aload 4
      //   373: invokevirtual 137	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   376: aload_0
      //   377: aload_3
      //   378: invokevirtual 143	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   381: putfield 145	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   384: goto +14 -> 398
      //   387: astore_1
      //   388: aload_0
      //   389: aload_3
      //   390: invokevirtual 143	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   393: putfield 145	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   396: aload_1
      //   397: athrow
      //   398: aload_0
      //   399: invokevirtual 148	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:makeExtensionsImmutable	()V
      //   402: return
      //   403: astore_2
      //   404: goto -61 -> 343
      //   407: astore_1
      //   408: goto -32 -> 376
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	411	0	this	VersionRequirement
      //   0	411	1	paramCodedInputStream	CodedInputStream
      //   0	411	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   22	368	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   28	344	4	localCodedOutputStream	CodedOutputStream
      //   31	273	5	i	int
      //   42	185	6	j	int
      //   111	123	7	k	int
      //   118	135	8	localObject	Object
      // Exception table:
      //   from	to	target	type
      //   38	44	308	finally
      //   91	104	308	finally
      //   107	120	308	finally
      //   125	139	308	finally
      //   142	159	308	finally
      //   162	181	308	finally
      //   184	203	308	finally
      //   206	219	308	finally
      //   224	238	308	finally
      //   241	257	308	finally
      //   260	278	308	finally
      //   281	299	308	finally
      //   313	331	308	finally
      //   332	338	308	finally
      //   38	44	312	java/io/IOException
      //   91	104	312	java/io/IOException
      //   107	120	312	java/io/IOException
      //   125	139	312	java/io/IOException
      //   142	159	312	java/io/IOException
      //   162	181	312	java/io/IOException
      //   184	203	312	java/io/IOException
      //   206	219	312	java/io/IOException
      //   224	238	312	java/io/IOException
      //   241	257	312	java/io/IOException
      //   260	278	312	java/io/IOException
      //   281	299	312	java/io/IOException
      //   38	44	331	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   91	104	331	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   107	120	331	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   125	139	331	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   142	159	331	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   162	181	331	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   184	203	331	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   206	219	331	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   224	238	331	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   241	257	331	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   260	278	331	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   281	299	331	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   338	343	354	finally
      //   371	376	387	finally
      //   338	343	403	java/io/IOException
      //   371	376	407	java/io/IOException
    }
    
    private VersionRequirement(GeneratedMessageLite.Builder paramBuilder)
    {
      super();
      this.unknownFields = paramBuilder.getUnknownFields();
    }
    
    private VersionRequirement(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static VersionRequirement getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.version_ = 0;
      this.versionFull_ = 0;
      this.level_ = Level.ERROR;
      this.errorCode_ = 0;
      this.message_ = 0;
      this.versionKind_ = VersionKind.LANGUAGE_VERSION;
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$19800();
    }
    
    public static Builder newBuilder(VersionRequirement paramVersionRequirement)
    {
      return newBuilder().mergeFrom(paramVersionRequirement);
    }
    
    public VersionRequirement getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public int getErrorCode()
    {
      return this.errorCode_;
    }
    
    public Level getLevel()
    {
      return this.level_;
    }
    
    public int getMessage()
    {
      return this.message_;
    }
    
    public Parser<VersionRequirement> getParserForType()
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
        j = 0 + CodedOutputStream.computeInt32Size(1, this.version_);
      }
      i = j;
      if ((this.bitField0_ & 0x2) == 2) {
        i = j + CodedOutputStream.computeInt32Size(2, this.versionFull_);
      }
      int k = i;
      if ((this.bitField0_ & 0x4) == 4) {
        k = i + CodedOutputStream.computeEnumSize(3, this.level_.getNumber());
      }
      j = k;
      if ((this.bitField0_ & 0x8) == 8) {
        j = k + CodedOutputStream.computeInt32Size(4, this.errorCode_);
      }
      i = j;
      if ((this.bitField0_ & 0x10) == 16) {
        i = j + CodedOutputStream.computeInt32Size(5, this.message_);
      }
      j = i;
      if ((this.bitField0_ & 0x20) == 32) {
        j = i + CodedOutputStream.computeEnumSize(6, this.versionKind_.getNumber());
      }
      i = j + this.unknownFields.size();
      this.memoizedSerializedSize = i;
      return i;
    }
    
    public int getVersion()
    {
      return this.version_;
    }
    
    public int getVersionFull()
    {
      return this.versionFull_;
    }
    
    public VersionKind getVersionKind()
    {
      return this.versionKind_;
    }
    
    public boolean hasErrorCode()
    {
      boolean bool;
      if ((this.bitField0_ & 0x8) == 8) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasLevel()
    {
      boolean bool;
      if ((this.bitField0_ & 0x4) == 4) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasMessage()
    {
      boolean bool;
      if ((this.bitField0_ & 0x10) == 16) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasVersion()
    {
      int i = this.bitField0_;
      boolean bool = true;
      if ((i & 0x1) != 1) {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasVersionFull()
    {
      boolean bool;
      if ((this.bitField0_ & 0x2) == 2) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasVersionKind()
    {
      boolean bool;
      if ((this.bitField0_ & 0x20) == 32) {
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
        paramCodedOutputStream.writeInt32(1, this.version_);
      }
      if ((this.bitField0_ & 0x2) == 2) {
        paramCodedOutputStream.writeInt32(2, this.versionFull_);
      }
      if ((this.bitField0_ & 0x4) == 4) {
        paramCodedOutputStream.writeEnum(3, this.level_.getNumber());
      }
      if ((this.bitField0_ & 0x8) == 8) {
        paramCodedOutputStream.writeInt32(4, this.errorCode_);
      }
      if ((this.bitField0_ & 0x10) == 16) {
        paramCodedOutputStream.writeInt32(5, this.message_);
      }
      if ((this.bitField0_ & 0x20) == 32) {
        paramCodedOutputStream.writeEnum(6, this.versionKind_.getNumber());
      }
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Builder
      extends GeneratedMessageLite.Builder<ProtoBuf.VersionRequirement, Builder>
      implements ProtoBuf.VersionRequirementOrBuilder
    {
      private int bitField0_;
      private int errorCode_;
      private ProtoBuf.VersionRequirement.Level level_ = ProtoBuf.VersionRequirement.Level.ERROR;
      private int message_;
      private int versionFull_;
      private ProtoBuf.VersionRequirement.VersionKind versionKind_ = ProtoBuf.VersionRequirement.VersionKind.LANGUAGE_VERSION;
      private int version_;
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public ProtoBuf.VersionRequirement build()
      {
        ProtoBuf.VersionRequirement localVersionRequirement = buildPartial();
        if (localVersionRequirement.isInitialized()) {
          return localVersionRequirement;
        }
        throw newUninitializedMessageException(localVersionRequirement);
      }
      
      public ProtoBuf.VersionRequirement buildPartial()
      {
        ProtoBuf.VersionRequirement localVersionRequirement = new ProtoBuf.VersionRequirement(this, null);
        int i = this.bitField0_;
        int j = 1;
        if ((i & 0x1) != 1) {
          j = 0;
        }
        ProtoBuf.VersionRequirement.access$20002(localVersionRequirement, this.version_);
        int k = j;
        if ((i & 0x2) == 2) {
          k = j | 0x2;
        }
        ProtoBuf.VersionRequirement.access$20102(localVersionRequirement, this.versionFull_);
        j = k;
        if ((i & 0x4) == 4) {
          j = k | 0x4;
        }
        ProtoBuf.VersionRequirement.access$20202(localVersionRequirement, this.level_);
        k = j;
        if ((i & 0x8) == 8) {
          k = j | 0x8;
        }
        ProtoBuf.VersionRequirement.access$20302(localVersionRequirement, this.errorCode_);
        j = k;
        if ((i & 0x10) == 16) {
          j = k | 0x10;
        }
        ProtoBuf.VersionRequirement.access$20402(localVersionRequirement, this.message_);
        k = j;
        if ((i & 0x20) == 32) {
          k = j | 0x20;
        }
        ProtoBuf.VersionRequirement.access$20502(localVersionRequirement, this.versionKind_);
        ProtoBuf.VersionRequirement.access$20602(localVersionRequirement, k);
        return localVersionRequirement;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public ProtoBuf.VersionRequirement getDefaultInstanceForType()
      {
        return ProtoBuf.VersionRequirement.getDefaultInstance();
      }
      
      public final boolean isInitialized()
      {
        return true;
      }
      
      public Builder mergeFrom(ProtoBuf.VersionRequirement paramVersionRequirement)
      {
        if (paramVersionRequirement == ProtoBuf.VersionRequirement.getDefaultInstance()) {
          return this;
        }
        if (paramVersionRequirement.hasVersion()) {
          setVersion(paramVersionRequirement.getVersion());
        }
        if (paramVersionRequirement.hasVersionFull()) {
          setVersionFull(paramVersionRequirement.getVersionFull());
        }
        if (paramVersionRequirement.hasLevel()) {
          setLevel(paramVersionRequirement.getLevel());
        }
        if (paramVersionRequirement.hasErrorCode()) {
          setErrorCode(paramVersionRequirement.getErrorCode());
        }
        if (paramVersionRequirement.hasMessage()) {
          setMessage(paramVersionRequirement.getMessage());
        }
        if (paramVersionRequirement.hasVersionKind()) {
          setVersionKind(paramVersionRequirement.getVersionKind());
        }
        setUnknownFields(getUnknownFields().concat(paramVersionRequirement.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 211	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 217 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 115	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_2
        //   29: aload_3
        //   30: astore_1
        //   31: goto +15 -> 46
        //   34: astore_2
        //   35: aload_2
        //   36: invokevirtual 220	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   39: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement
        //   42: astore_1
        //   43: aload_2
        //   44: athrow
        //   45: astore_2
        //   46: aload_1
        //   47: ifnull +9 -> 56
        //   50: aload_0
        //   51: aload_1
        //   52: invokevirtual 115	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement$Builder;
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
      
      public Builder setErrorCode(int paramInt)
      {
        this.bitField0_ |= 0x8;
        this.errorCode_ = paramInt;
        return this;
      }
      
      public Builder setLevel(ProtoBuf.VersionRequirement.Level paramLevel)
      {
        if (paramLevel != null)
        {
          this.bitField0_ |= 0x4;
          this.level_ = paramLevel;
          return this;
        }
        throw null;
      }
      
      public Builder setMessage(int paramInt)
      {
        this.bitField0_ |= 0x10;
        this.message_ = paramInt;
        return this;
      }
      
      public Builder setVersion(int paramInt)
      {
        this.bitField0_ |= 0x1;
        this.version_ = paramInt;
        return this;
      }
      
      public Builder setVersionFull(int paramInt)
      {
        this.bitField0_ |= 0x2;
        this.versionFull_ = paramInt;
        return this;
      }
      
      public Builder setVersionKind(ProtoBuf.VersionRequirement.VersionKind paramVersionKind)
      {
        if (paramVersionKind != null)
        {
          this.bitField0_ |= 0x20;
          this.versionKind_ = paramVersionKind;
          return this;
        }
        throw null;
      }
    }
    
    public static enum Level
      implements Internal.EnumLite
    {
      private static Internal.EnumLiteMap<Level> internalValueMap = new Internal.EnumLiteMap()
      {
        public ProtoBuf.VersionRequirement.Level findValueByNumber(int paramAnonymousInt)
        {
          return ProtoBuf.VersionRequirement.Level.valueOf(paramAnonymousInt);
        }
      };
      private final int value;
      
      static
      {
        ERROR = new Level("ERROR", 1, 1, 1);
        Level localLevel = new Level("HIDDEN", 2, 2, 2);
        HIDDEN = localLevel;
        $VALUES = new Level[] { WARNING, ERROR, localLevel };
      }
      
      private Level(int paramInt1, int paramInt2)
      {
        this.value = paramInt2;
      }
      
      public static Level valueOf(int paramInt)
      {
        if (paramInt != 0)
        {
          if (paramInt != 1)
          {
            if (paramInt != 2) {
              return null;
            }
            return HIDDEN;
          }
          return ERROR;
        }
        return WARNING;
      }
      
      public final int getNumber()
      {
        return this.value;
      }
    }
    
    public static enum VersionKind
      implements Internal.EnumLite
    {
      private static Internal.EnumLiteMap<VersionKind> internalValueMap = new Internal.EnumLiteMap()
      {
        public ProtoBuf.VersionRequirement.VersionKind findValueByNumber(int paramAnonymousInt)
        {
          return ProtoBuf.VersionRequirement.VersionKind.valueOf(paramAnonymousInt);
        }
      };
      private final int value;
      
      static
      {
        COMPILER_VERSION = new VersionKind("COMPILER_VERSION", 1, 1, 1);
        VersionKind localVersionKind = new VersionKind("API_VERSION", 2, 2, 2);
        API_VERSION = localVersionKind;
        $VALUES = new VersionKind[] { LANGUAGE_VERSION, COMPILER_VERSION, localVersionKind };
      }
      
      private VersionKind(int paramInt1, int paramInt2)
      {
        this.value = paramInt2;
      }
      
      public static VersionKind valueOf(int paramInt)
      {
        if (paramInt != 0)
        {
          if (paramInt != 1)
          {
            if (paramInt != 2) {
              return null;
            }
            return API_VERSION;
          }
          return COMPILER_VERSION;
        }
        return LANGUAGE_VERSION;
      }
      
      public final int getNumber()
      {
        return this.value;
      }
    }
  }
  
  public static final class VersionRequirementTable
    extends GeneratedMessageLite
    implements ProtoBuf.VersionRequirementTableOrBuilder
  {
    public static Parser<VersionRequirementTable> PARSER = new AbstractParser()
    {
      public ProtoBuf.VersionRequirementTable parsePartialFrom(CodedInputStream paramAnonymousCodedInputStream, ExtensionRegistryLite paramAnonymousExtensionRegistryLite)
        throws InvalidProtocolBufferException
      {
        return new ProtoBuf.VersionRequirementTable(paramAnonymousCodedInputStream, paramAnonymousExtensionRegistryLite, null);
      }
    };
    private static final VersionRequirementTable defaultInstance;
    private byte memoizedIsInitialized = (byte)-1;
    private int memoizedSerializedSize = -1;
    private List<ProtoBuf.VersionRequirement> requirement_;
    private final ByteString unknownFields;
    
    static
    {
      VersionRequirementTable localVersionRequirementTable = new VersionRequirementTable(true);
      defaultInstance = localVersionRequirementTable;
      localVersionRequirementTable.initFields();
    }
    
    /* Error */
    private VersionRequirementTable(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws InvalidProtocolBufferException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 50	kotlin/reflect/jvm/internal/impl/protobuf/GeneratedMessageLite:<init>	()V
      //   4: aload_0
      //   5: iconst_m1
      //   6: i2b
      //   7: putfield 52	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable:memoizedIsInitialized	B
      //   10: aload_0
      //   11: iconst_m1
      //   12: putfield 54	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable:memoizedSerializedSize	I
      //   15: aload_0
      //   16: invokespecial 43	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable:initFields	()V
      //   19: invokestatic 60	kotlin/reflect/jvm/internal/impl/protobuf/ByteString:newOutput	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output;
      //   22: astore_3
      //   23: aload_3
      //   24: iconst_1
      //   25: invokestatic 66	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:newInstance	(Ljava/io/OutputStream;I)Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;
      //   28: astore 4
      //   30: iconst_0
      //   31: istore 5
      //   33: iconst_0
      //   34: istore 6
      //   36: iload 5
      //   38: ifne +272 -> 310
      //   41: iload 6
      //   43: istore 7
      //   45: iload 6
      //   47: istore 8
      //   49: iload 6
      //   51: istore 9
      //   53: aload_1
      //   54: invokevirtual 72	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readTag	()I
      //   57: istore 10
      //   59: iload 10
      //   61: ifeq +145 -> 206
      //   64: iload 10
      //   66: bipush 10
      //   68: if_icmpeq +31 -> 99
      //   71: iload 6
      //   73: istore 7
      //   75: iload 6
      //   77: istore 8
      //   79: iload 6
      //   81: istore 9
      //   83: aload_0
      //   84: aload_1
      //   85: aload 4
      //   87: aload_2
      //   88: iload 10
      //   90: invokevirtual 76	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable:parseUnknownField	(Lkotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;I)Z
      //   93: ifne -57 -> 36
      //   96: goto +110 -> 206
      //   99: iload 6
      //   101: istore 10
      //   103: iload 6
      //   105: iconst_1
      //   106: iand
      //   107: iconst_1
      //   108: if_icmpeq +61 -> 169
      //   111: iload 6
      //   113: istore 7
      //   115: iload 6
      //   117: istore 8
      //   119: iload 6
      //   121: istore 9
      //   123: new 78	java/util/ArrayList
      //   126: astore 11
      //   128: iload 6
      //   130: istore 7
      //   132: iload 6
      //   134: istore 8
      //   136: iload 6
      //   138: istore 9
      //   140: aload 11
      //   142: invokespecial 79	java/util/ArrayList:<init>	()V
      //   145: iload 6
      //   147: istore 7
      //   149: iload 6
      //   151: istore 8
      //   153: iload 6
      //   155: istore 9
      //   157: aload_0
      //   158: aload 11
      //   160: putfield 81	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable:requirement_	Ljava/util/List;
      //   163: iload 6
      //   165: iconst_1
      //   166: ior
      //   167: istore 10
      //   169: iload 10
      //   171: istore 7
      //   173: iload 10
      //   175: istore 8
      //   177: iload 10
      //   179: istore 9
      //   181: aload_0
      //   182: getfield 81	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable:requirement_	Ljava/util/List;
      //   185: aload_1
      //   186: getstatic 84	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirement:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
      //   189: aload_2
      //   190: invokevirtual 88	kotlin/reflect/jvm/internal/impl/protobuf/CodedInputStream:readMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;Lkotlin/reflect/jvm/internal/impl/protobuf/ExtensionRegistryLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
      //   193: invokeinterface 94 2 0
      //   198: pop
      //   199: iload 10
      //   201: istore 6
      //   203: goto -167 -> 36
      //   206: iconst_1
      //   207: istore 5
      //   209: goto -173 -> 36
      //   212: astore_1
      //   213: goto +45 -> 258
      //   216: astore_2
      //   217: iload 8
      //   219: istore 7
      //   221: new 47	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   224: astore_1
      //   225: iload 8
      //   227: istore 7
      //   229: aload_1
      //   230: aload_2
      //   231: invokevirtual 98	java/io/IOException:getMessage	()Ljava/lang/String;
      //   234: invokespecial 101	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:<init>	(Ljava/lang/String;)V
      //   237: iload 8
      //   239: istore 7
      //   241: aload_1
      //   242: aload_0
      //   243: invokevirtual 105	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   246: athrow
      //   247: astore_1
      //   248: iload 9
      //   250: istore 7
      //   252: aload_1
      //   253: aload_0
      //   254: invokevirtual 105	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:setUnfinishedMessage	(Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;)Lkotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException;
      //   257: athrow
      //   258: iload 7
      //   260: iconst_1
      //   261: iand
      //   262: iconst_1
      //   263: if_icmpne +14 -> 277
      //   266: aload_0
      //   267: aload_0
      //   268: getfield 81	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable:requirement_	Ljava/util/List;
      //   271: invokestatic 111	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   274: putfield 81	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable:requirement_	Ljava/util/List;
      //   277: aload 4
      //   279: invokevirtual 114	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   282: aload_0
      //   283: aload_3
      //   284: invokevirtual 120	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   287: putfield 122	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   290: goto +14 -> 304
      //   293: astore_1
      //   294: aload_0
      //   295: aload_3
      //   296: invokevirtual 120	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   299: putfield 122	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   302: aload_1
      //   303: athrow
      //   304: aload_0
      //   305: invokevirtual 125	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable:makeExtensionsImmutable	()V
      //   308: aload_1
      //   309: athrow
      //   310: iload 6
      //   312: iconst_1
      //   313: iand
      //   314: iconst_1
      //   315: if_icmpne +14 -> 329
      //   318: aload_0
      //   319: aload_0
      //   320: getfield 81	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable:requirement_	Ljava/util/List;
      //   323: invokestatic 111	java/util/Collections:unmodifiableList	(Ljava/util/List;)Ljava/util/List;
      //   326: putfield 81	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable:requirement_	Ljava/util/List;
      //   329: aload 4
      //   331: invokevirtual 114	kotlin/reflect/jvm/internal/impl/protobuf/CodedOutputStream:flush	()V
      //   334: aload_0
      //   335: aload_3
      //   336: invokevirtual 120	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   339: putfield 122	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   342: goto +14 -> 356
      //   345: astore_1
      //   346: aload_0
      //   347: aload_3
      //   348: invokevirtual 120	kotlin/reflect/jvm/internal/impl/protobuf/ByteString$Output:toByteString	()Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   351: putfield 122	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable:unknownFields	Lkotlin/reflect/jvm/internal/impl/protobuf/ByteString;
      //   354: aload_1
      //   355: athrow
      //   356: aload_0
      //   357: invokevirtual 125	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable:makeExtensionsImmutable	()V
      //   360: return
      //   361: astore_2
      //   362: goto -80 -> 282
      //   365: astore_1
      //   366: goto -32 -> 334
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	369	0	this	VersionRequirementTable
      //   0	369	1	paramCodedInputStream	CodedInputStream
      //   0	369	2	paramExtensionRegistryLite	ExtensionRegistryLite
      //   22	326	3	localOutput	kotlin.reflect.jvm.internal.impl.protobuf.ByteString.Output
      //   28	302	4	localCodedOutputStream	CodedOutputStream
      //   31	177	5	i	int
      //   34	280	6	j	int
      //   43	219	7	k	int
      //   47	191	8	m	int
      //   51	198	9	n	int
      //   57	143	10	i1	int
      //   126	33	11	localArrayList	ArrayList
      // Exception table:
      //   from	to	target	type
      //   53	59	212	finally
      //   83	96	212	finally
      //   123	128	212	finally
      //   140	145	212	finally
      //   157	163	212	finally
      //   181	199	212	finally
      //   221	225	212	finally
      //   229	237	212	finally
      //   241	247	212	finally
      //   252	258	212	finally
      //   53	59	216	java/io/IOException
      //   83	96	216	java/io/IOException
      //   123	128	216	java/io/IOException
      //   140	145	216	java/io/IOException
      //   157	163	216	java/io/IOException
      //   181	199	216	java/io/IOException
      //   53	59	247	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   83	96	247	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   123	128	247	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   140	145	247	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   157	163	247	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   181	199	247	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException
      //   277	282	293	finally
      //   329	334	345	finally
      //   277	282	361	java/io/IOException
      //   329	334	365	java/io/IOException
    }
    
    private VersionRequirementTable(GeneratedMessageLite.Builder paramBuilder)
    {
      super();
      this.unknownFields = paramBuilder.getUnknownFields();
    }
    
    private VersionRequirementTable(boolean paramBoolean)
    {
      this.unknownFields = ByteString.EMPTY;
    }
    
    public static VersionRequirementTable getDefaultInstance()
    {
      return defaultInstance;
    }
    
    private void initFields()
    {
      this.requirement_ = Collections.emptyList();
    }
    
    public static Builder newBuilder()
    {
      return Builder.access$20900();
    }
    
    public static Builder newBuilder(VersionRequirementTable paramVersionRequirementTable)
    {
      return newBuilder().mergeFrom(paramVersionRequirementTable);
    }
    
    public VersionRequirementTable getDefaultInstanceForType()
    {
      return defaultInstance;
    }
    
    public Parser<VersionRequirementTable> getParserForType()
    {
      return PARSER;
    }
    
    public int getRequirementCount()
    {
      return this.requirement_.size();
    }
    
    public List<ProtoBuf.VersionRequirement> getRequirementList()
    {
      return this.requirement_;
    }
    
    public int getSerializedSize()
    {
      int i = this.memoizedSerializedSize;
      if (i != -1) {
        return i;
      }
      i = 0;
      int j = 0;
      while (i < this.requirement_.size())
      {
        j += CodedOutputStream.computeMessageSize(1, (MessageLite)this.requirement_.get(i));
        i++;
      }
      i = j + this.unknownFields.size();
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
      for (int i = 0; i < this.requirement_.size(); i++) {
        paramCodedOutputStream.writeMessage(1, (MessageLite)this.requirement_.get(i));
      }
      paramCodedOutputStream.writeRawBytes(this.unknownFields);
    }
    
    public static final class Builder
      extends GeneratedMessageLite.Builder<ProtoBuf.VersionRequirementTable, Builder>
      implements ProtoBuf.VersionRequirementTableOrBuilder
    {
      private int bitField0_;
      private List<ProtoBuf.VersionRequirement> requirement_ = Collections.emptyList();
      
      private Builder()
      {
        maybeForceBuilderInitialization();
      }
      
      private static Builder create()
      {
        return new Builder();
      }
      
      private void ensureRequirementIsMutable()
      {
        if ((this.bitField0_ & 0x1) != 1)
        {
          this.requirement_ = new ArrayList(this.requirement_);
          this.bitField0_ |= 0x1;
        }
      }
      
      private void maybeForceBuilderInitialization() {}
      
      public ProtoBuf.VersionRequirementTable build()
      {
        ProtoBuf.VersionRequirementTable localVersionRequirementTable = buildPartial();
        if (localVersionRequirementTable.isInitialized()) {
          return localVersionRequirementTable;
        }
        throw newUninitializedMessageException(localVersionRequirementTable);
      }
      
      public ProtoBuf.VersionRequirementTable buildPartial()
      {
        ProtoBuf.VersionRequirementTable localVersionRequirementTable = new ProtoBuf.VersionRequirementTable(this, null);
        if ((this.bitField0_ & 0x1) == 1)
        {
          this.requirement_ = Collections.unmodifiableList(this.requirement_);
          this.bitField0_ &= 0xFFFFFFFE;
        }
        ProtoBuf.VersionRequirementTable.access$21102(localVersionRequirementTable, this.requirement_);
        return localVersionRequirementTable;
      }
      
      public Builder clone()
      {
        return create().mergeFrom(buildPartial());
      }
      
      public ProtoBuf.VersionRequirementTable getDefaultInstanceForType()
      {
        return ProtoBuf.VersionRequirementTable.getDefaultInstance();
      }
      
      public final boolean isInitialized()
      {
        return true;
      }
      
      public Builder mergeFrom(ProtoBuf.VersionRequirementTable paramVersionRequirementTable)
      {
        if (paramVersionRequirementTable == ProtoBuf.VersionRequirementTable.getDefaultInstance()) {
          return this;
        }
        if (!paramVersionRequirementTable.requirement_.isEmpty()) {
          if (this.requirement_.isEmpty())
          {
            this.requirement_ = paramVersionRequirementTable.requirement_;
            this.bitField0_ &= 0xFFFFFFFE;
          }
          else
          {
            ensureRequirementIsMutable();
            this.requirement_.addAll(paramVersionRequirementTable.requirement_);
          }
        }
        setUnknownFields(getUnknownFields().concat(paramVersionRequirementTable.unknownFields));
        return this;
      }
      
      /* Error */
      public Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_3
        //   2: getstatic 137	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable:PARSER	Lkotlin/reflect/jvm/internal/impl/protobuf/Parser;
        //   5: aload_1
        //   6: aload_2
        //   7: invokeinterface 143 3 0
        //   12: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable
        //   15: astore_1
        //   16: aload_1
        //   17: ifnull +9 -> 26
        //   20: aload_0
        //   21: aload_1
        //   22: invokevirtual 86	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable$Builder;
        //   25: pop
        //   26: aload_0
        //   27: areturn
        //   28: astore_1
        //   29: aload_3
        //   30: astore_2
        //   31: goto +15 -> 46
        //   34: astore_1
        //   35: aload_1
        //   36: invokevirtual 146	kotlin/reflect/jvm/internal/impl/protobuf/InvalidProtocolBufferException:getUnfinishedMessage	()Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;
        //   39: checkcast 9	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable
        //   42: astore_2
        //   43: aload_1
        //   44: athrow
        //   45: astore_1
        //   46: aload_2
        //   47: ifnull +9 -> 56
        //   50: aload_0
        //   51: aload_2
        //   52: invokevirtual 86	kotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable$Builder:mergeFrom	(Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable;)Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$VersionRequirementTable$Builder;
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
    }
  }
  
  public static enum Visibility
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<Visibility> internalValueMap = new Internal.EnumLiteMap()
    {
      public ProtoBuf.Visibility findValueByNumber(int paramAnonymousInt)
      {
        return ProtoBuf.Visibility.valueOf(paramAnonymousInt);
      }
    };
    private final int value;
    
    static
    {
      PRIVATE_TO_THIS = new Visibility("PRIVATE_TO_THIS", 4, 4, 4);
      Visibility localVisibility = new Visibility("LOCAL", 5, 5, 5);
      LOCAL = localVisibility;
      $VALUES = new Visibility[] { INTERNAL, PRIVATE, PROTECTED, PUBLIC, PRIVATE_TO_THIS, localVisibility };
    }
    
    private Visibility(int paramInt1, int paramInt2)
    {
      this.value = paramInt2;
    }
    
    public static Visibility valueOf(int paramInt)
    {
      if (paramInt != 0)
      {
        if (paramInt != 1)
        {
          if (paramInt != 2)
          {
            if (paramInt != 3)
            {
              if (paramInt != 4)
              {
                if (paramInt != 5) {
                  return null;
                }
                return LOCAL;
              }
              return PRIVATE_TO_THIS;
            }
            return PUBLIC;
          }
          return PROTECTED;
        }
        return PRIVATE;
      }
      return INTERNAL;
    }
    
    public final int getNumber()
    {
      return this.value;
    }
  }
}

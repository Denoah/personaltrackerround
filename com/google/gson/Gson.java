package com.google.gson;

import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.Streams;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.internal.bind.JsonTreeWriter;
import com.google.gson.internal.bind.MapTypeAdapterFactory;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.internal.bind.SqlDateTypeAdapter;
import com.google.gson.internal.bind.TimeTypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

public final class Gson
{
  static final boolean DEFAULT_COMPLEX_MAP_KEYS = false;
  static final boolean DEFAULT_ESCAPE_HTML = true;
  static final boolean DEFAULT_JSON_NON_EXECUTABLE = false;
  static final boolean DEFAULT_LENIENT = false;
  static final boolean DEFAULT_PRETTY_PRINT = false;
  static final boolean DEFAULT_SERIALIZE_NULLS = false;
  static final boolean DEFAULT_SPECIALIZE_FLOAT_VALUES = false;
  private static final String JSON_NON_EXECUTABLE_PREFIX = ")]}'\n";
  private static final TypeToken<?> NULL_KEY_SURROGATE = TypeToken.get(Object.class);
  final List<TypeAdapterFactory> builderFactories;
  final List<TypeAdapterFactory> builderHierarchyFactories;
  private final ThreadLocal<Map<TypeToken<?>, FutureTypeAdapter<?>>> calls = new ThreadLocal();
  final boolean complexMapKeySerialization;
  private final ConstructorConstructor constructorConstructor;
  final String datePattern;
  final int dateStyle;
  final Excluder excluder;
  final List<TypeAdapterFactory> factories;
  final FieldNamingStrategy fieldNamingStrategy;
  final boolean generateNonExecutableJson;
  final boolean htmlSafe;
  final Map<Type, InstanceCreator<?>> instanceCreators;
  private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
  final boolean lenient;
  final LongSerializationPolicy longSerializationPolicy;
  final boolean prettyPrinting;
  final boolean serializeNulls;
  final boolean serializeSpecialFloatingPointValues;
  final int timeStyle;
  private final Map<TypeToken<?>, TypeAdapter<?>> typeTokenCache = new ConcurrentHashMap();
  
  public Gson()
  {
    this(Excluder.DEFAULT, FieldNamingPolicy.IDENTITY, Collections.emptyMap(), false, false, false, true, false, false, false, LongSerializationPolicy.DEFAULT, null, 2, 2, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
  }
  
  Gson(Excluder paramExcluder, FieldNamingStrategy paramFieldNamingStrategy, Map<Type, InstanceCreator<?>> paramMap, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7, LongSerializationPolicy paramLongSerializationPolicy, String paramString, int paramInt1, int paramInt2, List<TypeAdapterFactory> paramList1, List<TypeAdapterFactory> paramList2, List<TypeAdapterFactory> paramList3)
  {
    this.excluder = paramExcluder;
    this.fieldNamingStrategy = paramFieldNamingStrategy;
    this.instanceCreators = paramMap;
    this.constructorConstructor = new ConstructorConstructor(paramMap);
    this.serializeNulls = paramBoolean1;
    this.complexMapKeySerialization = paramBoolean2;
    this.generateNonExecutableJson = paramBoolean3;
    this.htmlSafe = paramBoolean4;
    this.prettyPrinting = paramBoolean5;
    this.lenient = paramBoolean6;
    this.serializeSpecialFloatingPointValues = paramBoolean7;
    this.longSerializationPolicy = paramLongSerializationPolicy;
    this.datePattern = paramString;
    this.dateStyle = paramInt1;
    this.timeStyle = paramInt2;
    this.builderFactories = paramList1;
    this.builderHierarchyFactories = paramList2;
    paramMap = new ArrayList();
    paramMap.add(TypeAdapters.JSON_ELEMENT_FACTORY);
    paramMap.add(ObjectTypeAdapter.FACTORY);
    paramMap.add(paramExcluder);
    paramMap.addAll(paramList3);
    paramMap.add(TypeAdapters.STRING_FACTORY);
    paramMap.add(TypeAdapters.INTEGER_FACTORY);
    paramMap.add(TypeAdapters.BOOLEAN_FACTORY);
    paramMap.add(TypeAdapters.BYTE_FACTORY);
    paramMap.add(TypeAdapters.SHORT_FACTORY);
    paramLongSerializationPolicy = longAdapter(paramLongSerializationPolicy);
    paramMap.add(TypeAdapters.newFactory(Long.TYPE, Long.class, paramLongSerializationPolicy));
    paramMap.add(TypeAdapters.newFactory(Double.TYPE, Double.class, doubleAdapter(paramBoolean7)));
    paramMap.add(TypeAdapters.newFactory(Float.TYPE, Float.class, floatAdapter(paramBoolean7)));
    paramMap.add(TypeAdapters.NUMBER_FACTORY);
    paramMap.add(TypeAdapters.ATOMIC_INTEGER_FACTORY);
    paramMap.add(TypeAdapters.ATOMIC_BOOLEAN_FACTORY);
    paramMap.add(TypeAdapters.newFactory(AtomicLong.class, atomicLongAdapter(paramLongSerializationPolicy)));
    paramMap.add(TypeAdapters.newFactory(AtomicLongArray.class, atomicLongArrayAdapter(paramLongSerializationPolicy)));
    paramMap.add(TypeAdapters.ATOMIC_INTEGER_ARRAY_FACTORY);
    paramMap.add(TypeAdapters.CHARACTER_FACTORY);
    paramMap.add(TypeAdapters.STRING_BUILDER_FACTORY);
    paramMap.add(TypeAdapters.STRING_BUFFER_FACTORY);
    paramMap.add(TypeAdapters.newFactory(BigDecimal.class, TypeAdapters.BIG_DECIMAL));
    paramMap.add(TypeAdapters.newFactory(BigInteger.class, TypeAdapters.BIG_INTEGER));
    paramMap.add(TypeAdapters.URL_FACTORY);
    paramMap.add(TypeAdapters.URI_FACTORY);
    paramMap.add(TypeAdapters.UUID_FACTORY);
    paramMap.add(TypeAdapters.CURRENCY_FACTORY);
    paramMap.add(TypeAdapters.LOCALE_FACTORY);
    paramMap.add(TypeAdapters.INET_ADDRESS_FACTORY);
    paramMap.add(TypeAdapters.BIT_SET_FACTORY);
    paramMap.add(DateTypeAdapter.FACTORY);
    paramMap.add(TypeAdapters.CALENDAR_FACTORY);
    paramMap.add(TimeTypeAdapter.FACTORY);
    paramMap.add(SqlDateTypeAdapter.FACTORY);
    paramMap.add(TypeAdapters.TIMESTAMP_FACTORY);
    paramMap.add(ArrayTypeAdapter.FACTORY);
    paramMap.add(TypeAdapters.CLASS_FACTORY);
    paramMap.add(new CollectionTypeAdapterFactory(this.constructorConstructor));
    paramMap.add(new MapTypeAdapterFactory(this.constructorConstructor, paramBoolean2));
    paramLongSerializationPolicy = new JsonAdapterAnnotationTypeAdapterFactory(this.constructorConstructor);
    this.jsonAdapterFactory = paramLongSerializationPolicy;
    paramMap.add(paramLongSerializationPolicy);
    paramMap.add(TypeAdapters.ENUM_FACTORY);
    paramMap.add(new ReflectiveTypeAdapterFactory(this.constructorConstructor, paramFieldNamingStrategy, paramExcluder, this.jsonAdapterFactory));
    this.factories = Collections.unmodifiableList(paramMap);
  }
  
  private static void assertFullConsumption(Object paramObject, JsonReader paramJsonReader)
  {
    if (paramObject != null) {
      try
      {
        if (paramJsonReader.peek() != JsonToken.END_DOCUMENT)
        {
          paramObject = new com/google/gson/JsonIOException;
          paramObject.<init>("JSON document was not fully consumed.");
          throw paramObject;
        }
      }
      catch (IOException paramObject)
      {
        throw new JsonIOException(paramObject);
      }
      catch (MalformedJsonException paramObject)
      {
        throw new JsonSyntaxException(paramObject);
      }
    }
  }
  
  private static TypeAdapter<AtomicLong> atomicLongAdapter(TypeAdapter<Number> paramTypeAdapter)
  {
    new TypeAdapter()
    {
      public AtomicLong read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        return new AtomicLong(((Number)Gson.this.read(paramAnonymousJsonReader)).longValue());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, AtomicLong paramAnonymousAtomicLong)
        throws IOException
      {
        Gson.this.write(paramAnonymousJsonWriter, Long.valueOf(paramAnonymousAtomicLong.get()));
      }
    }.nullSafe();
  }
  
  private static TypeAdapter<AtomicLongArray> atomicLongArrayAdapter(TypeAdapter<Number> paramTypeAdapter)
  {
    new TypeAdapter()
    {
      public AtomicLongArray read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        ArrayList localArrayList = new ArrayList();
        paramAnonymousJsonReader.beginArray();
        while (paramAnonymousJsonReader.hasNext()) {
          localArrayList.add(Long.valueOf(((Number)Gson.this.read(paramAnonymousJsonReader)).longValue()));
        }
        paramAnonymousJsonReader.endArray();
        int i = localArrayList.size();
        paramAnonymousJsonReader = new AtomicLongArray(i);
        for (int j = 0; j < i; j++) {
          paramAnonymousJsonReader.set(j, ((Long)localArrayList.get(j)).longValue());
        }
        return paramAnonymousJsonReader;
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, AtomicLongArray paramAnonymousAtomicLongArray)
        throws IOException
      {
        paramAnonymousJsonWriter.beginArray();
        int i = paramAnonymousAtomicLongArray.length();
        for (int j = 0; j < i; j++) {
          Gson.this.write(paramAnonymousJsonWriter, Long.valueOf(paramAnonymousAtomicLongArray.get(j)));
        }
        paramAnonymousJsonWriter.endArray();
      }
    }.nullSafe();
  }
  
  static void checkValidFloatingPoint(double paramDouble)
  {
    if ((!Double.isNaN(paramDouble)) && (!Double.isInfinite(paramDouble))) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramDouble);
    localStringBuilder.append(" is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  private TypeAdapter<Number> doubleAdapter(boolean paramBoolean)
  {
    if (paramBoolean) {
      return TypeAdapters.DOUBLE;
    }
    new TypeAdapter()
    {
      public Double read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return Double.valueOf(paramAnonymousJsonReader.nextDouble());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
        throws IOException
      {
        if (paramAnonymousNumber == null)
        {
          paramAnonymousJsonWriter.nullValue();
          return;
        }
        Gson.checkValidFloatingPoint(paramAnonymousNumber.doubleValue());
        paramAnonymousJsonWriter.value(paramAnonymousNumber);
      }
    };
  }
  
  private TypeAdapter<Number> floatAdapter(boolean paramBoolean)
  {
    if (paramBoolean) {
      return TypeAdapters.FLOAT;
    }
    new TypeAdapter()
    {
      public Float read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return Float.valueOf((float)paramAnonymousJsonReader.nextDouble());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
        throws IOException
      {
        if (paramAnonymousNumber == null)
        {
          paramAnonymousJsonWriter.nullValue();
          return;
        }
        Gson.checkValidFloatingPoint(paramAnonymousNumber.floatValue());
        paramAnonymousJsonWriter.value(paramAnonymousNumber);
      }
    };
  }
  
  private static TypeAdapter<Number> longAdapter(LongSerializationPolicy paramLongSerializationPolicy)
  {
    if (paramLongSerializationPolicy == LongSerializationPolicy.DEFAULT) {
      return TypeAdapters.LONG;
    }
    new TypeAdapter()
    {
      public Number read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return Long.valueOf(paramAnonymousJsonReader.nextLong());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
        throws IOException
      {
        if (paramAnonymousNumber == null)
        {
          paramAnonymousJsonWriter.nullValue();
          return;
        }
        paramAnonymousJsonWriter.value(paramAnonymousNumber.toString());
      }
    };
  }
  
  public Excluder excluder()
  {
    return this.excluder;
  }
  
  public FieldNamingStrategy fieldNamingStrategy()
  {
    return this.fieldNamingStrategy;
  }
  
  public <T> T fromJson(JsonElement paramJsonElement, Class<T> paramClass)
    throws JsonSyntaxException
  {
    paramJsonElement = fromJson(paramJsonElement, paramClass);
    return Primitives.wrap(paramClass).cast(paramJsonElement);
  }
  
  public <T> T fromJson(JsonElement paramJsonElement, Type paramType)
    throws JsonSyntaxException
  {
    if (paramJsonElement == null) {
      return null;
    }
    return fromJson(new JsonTreeReader(paramJsonElement), paramType);
  }
  
  /* Error */
  public <T> T fromJson(JsonReader paramJsonReader, Type paramType)
    throws JsonIOException, JsonSyntaxException
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 470	com/google/gson/stream/JsonReader:isLenient	()Z
    //   4: istore_3
    //   5: iconst_1
    //   6: istore 4
    //   8: aload_1
    //   9: iconst_1
    //   10: invokevirtual 474	com/google/gson/stream/JsonReader:setLenient	(Z)V
    //   13: aload_1
    //   14: invokevirtual 356	com/google/gson/stream/JsonReader:peek	()Lcom/google/gson/stream/JsonToken;
    //   17: pop
    //   18: iconst_0
    //   19: istore 4
    //   21: aload_0
    //   22: aload_2
    //   23: invokestatic 477	com/google/gson/reflect/TypeToken:get	(Ljava/lang/reflect/Type;)Lcom/google/gson/reflect/TypeToken;
    //   26: invokevirtual 481	com/google/gson/Gson:getAdapter	(Lcom/google/gson/reflect/TypeToken;)Lcom/google/gson/TypeAdapter;
    //   29: aload_1
    //   30: invokevirtual 487	com/google/gson/TypeAdapter:read	(Lcom/google/gson/stream/JsonReader;)Ljava/lang/Object;
    //   33: astore_2
    //   34: aload_1
    //   35: iload_3
    //   36: invokevirtual 474	com/google/gson/stream/JsonReader:setLenient	(Z)V
    //   39: aload_2
    //   40: areturn
    //   41: astore_2
    //   42: goto +112 -> 154
    //   45: astore 5
    //   47: new 466	java/lang/AssertionError
    //   50: astore 6
    //   52: new 397	java/lang/StringBuilder
    //   55: astore_2
    //   56: aload_2
    //   57: invokespecial 398	java/lang/StringBuilder:<init>	()V
    //   60: aload_2
    //   61: ldc_w 489
    //   64: invokevirtual 407	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   67: pop
    //   68: aload_2
    //   69: aload 5
    //   71: invokevirtual 492	java/lang/AssertionError:getMessage	()Ljava/lang/String;
    //   74: invokevirtual 407	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: pop
    //   78: aload 6
    //   80: aload_2
    //   81: invokevirtual 413	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   84: invokespecial 495	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   87: aload 6
    //   89: aload 5
    //   91: invokevirtual 499	java/lang/AssertionError:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   94: pop
    //   95: aload 6
    //   97: athrow
    //   98: astore_2
    //   99: new 374	com/google/gson/JsonSyntaxException
    //   102: astore 6
    //   104: aload 6
    //   106: aload_2
    //   107: invokespecial 375	com/google/gson/JsonSyntaxException:<init>	(Ljava/lang/Throwable;)V
    //   110: aload 6
    //   112: athrow
    //   113: astore 6
    //   115: new 374	com/google/gson/JsonSyntaxException
    //   118: astore_2
    //   119: aload_2
    //   120: aload 6
    //   122: invokespecial 375	com/google/gson/JsonSyntaxException:<init>	(Ljava/lang/Throwable;)V
    //   125: aload_2
    //   126: athrow
    //   127: astore_2
    //   128: iload 4
    //   130: ifeq +10 -> 140
    //   133: aload_1
    //   134: iload_3
    //   135: invokevirtual 474	com/google/gson/stream/JsonReader:setLenient	(Z)V
    //   138: aconst_null
    //   139: areturn
    //   140: new 374	com/google/gson/JsonSyntaxException
    //   143: astore 6
    //   145: aload 6
    //   147: aload_2
    //   148: invokespecial 375	com/google/gson/JsonSyntaxException:<init>	(Ljava/lang/Throwable;)V
    //   151: aload 6
    //   153: athrow
    //   154: aload_1
    //   155: iload_3
    //   156: invokevirtual 474	com/google/gson/stream/JsonReader:setLenient	(Z)V
    //   159: aload_2
    //   160: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	161	0	this	Gson
    //   0	161	1	paramJsonReader	JsonReader
    //   0	161	2	paramType	Type
    //   4	152	3	bool	boolean
    //   6	123	4	i	int
    //   45	45	5	localAssertionError	AssertionError
    //   50	61	6	localObject	Object
    //   113	8	6	localIllegalStateException	IllegalStateException
    //   143	9	6	localJsonSyntaxException	JsonSyntaxException
    // Exception table:
    //   from	to	target	type
    //   13	18	41	finally
    //   21	34	41	finally
    //   47	98	41	finally
    //   99	113	41	finally
    //   115	127	41	finally
    //   140	154	41	finally
    //   13	18	45	java/lang/AssertionError
    //   21	34	45	java/lang/AssertionError
    //   13	18	98	java/io/IOException
    //   21	34	98	java/io/IOException
    //   13	18	113	java/lang/IllegalStateException
    //   21	34	113	java/lang/IllegalStateException
    //   13	18	127	java/io/EOFException
    //   21	34	127	java/io/EOFException
  }
  
  public <T> T fromJson(Reader paramReader, Class<T> paramClass)
    throws JsonSyntaxException, JsonIOException
  {
    paramReader = newJsonReader(paramReader);
    Object localObject = fromJson(paramReader, paramClass);
    assertFullConsumption(localObject, paramReader);
    return Primitives.wrap(paramClass).cast(localObject);
  }
  
  public <T> T fromJson(Reader paramReader, Type paramType)
    throws JsonIOException, JsonSyntaxException
  {
    paramReader = newJsonReader(paramReader);
    paramType = fromJson(paramReader, paramType);
    assertFullConsumption(paramType, paramReader);
    return paramType;
  }
  
  public <T> T fromJson(String paramString, Class<T> paramClass)
    throws JsonSyntaxException
  {
    paramString = fromJson(paramString, paramClass);
    return Primitives.wrap(paramClass).cast(paramString);
  }
  
  public <T> T fromJson(String paramString, Type paramType)
    throws JsonSyntaxException
  {
    if (paramString == null) {
      return null;
    }
    return fromJson(new StringReader(paramString), paramType);
  }
  
  public <T> TypeAdapter<T> getAdapter(TypeToken<T> paramTypeToken)
  {
    Object localObject1 = this.typeTokenCache;
    if (paramTypeToken == null) {
      localObject3 = NULL_KEY_SURROGATE;
    } else {
      localObject3 = paramTypeToken;
    }
    Object localObject3 = (TypeAdapter)((Map)localObject1).get(localObject3);
    if (localObject3 != null) {
      return localObject3;
    }
    localObject1 = (Map)this.calls.get();
    int i = 0;
    localObject3 = localObject1;
    if (localObject1 == null)
    {
      localObject3 = new HashMap();
      this.calls.set(localObject3);
      i = 1;
    }
    localObject1 = (FutureTypeAdapter)((Map)localObject3).get(paramTypeToken);
    if (localObject1 != null) {
      return localObject1;
    }
    try
    {
      FutureTypeAdapter localFutureTypeAdapter = new com/google/gson/Gson$FutureTypeAdapter;
      localFutureTypeAdapter.<init>();
      ((Map)localObject3).put(paramTypeToken, localFutureTypeAdapter);
      localObject1 = this.factories.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject4 = ((TypeAdapterFactory)((Iterator)localObject1).next()).create(this, paramTypeToken);
        if (localObject4 != null)
        {
          localFutureTypeAdapter.setDelegate((TypeAdapter)localObject4);
          this.typeTokenCache.put(paramTypeToken, localObject4);
          return localObject4;
        }
      }
      Object localObject4 = new java/lang/IllegalArgumentException;
      localObject1 = new java/lang/StringBuilder;
      ((StringBuilder)localObject1).<init>();
      ((StringBuilder)localObject1).append("GSON (2.8.6) cannot handle ");
      ((StringBuilder)localObject1).append(paramTypeToken);
      ((IllegalArgumentException)localObject4).<init>(((StringBuilder)localObject1).toString());
      throw ((Throwable)localObject4);
    }
    finally
    {
      ((Map)localObject3).remove(paramTypeToken);
      if (i != 0) {
        this.calls.remove();
      }
    }
  }
  
  public <T> TypeAdapter<T> getAdapter(Class<T> paramClass)
  {
    return getAdapter(TypeToken.get(paramClass));
  }
  
  public <T> TypeAdapter<T> getDelegateAdapter(TypeAdapterFactory paramTypeAdapterFactory, TypeToken<T> paramTypeToken)
  {
    Object localObject1 = paramTypeAdapterFactory;
    if (!this.factories.contains(paramTypeAdapterFactory)) {
      localObject1 = this.jsonAdapterFactory;
    }
    int i = 0;
    paramTypeAdapterFactory = this.factories.iterator();
    while (paramTypeAdapterFactory.hasNext())
    {
      Object localObject2 = (TypeAdapterFactory)paramTypeAdapterFactory.next();
      if (i == 0)
      {
        if (localObject2 == localObject1) {
          i = 1;
        }
      }
      else
      {
        localObject2 = ((TypeAdapterFactory)localObject2).create(this, paramTypeToken);
        if (localObject2 != null) {
          return localObject2;
        }
      }
    }
    paramTypeAdapterFactory = new StringBuilder();
    paramTypeAdapterFactory.append("GSON cannot serialize ");
    paramTypeAdapterFactory.append(paramTypeToken);
    throw new IllegalArgumentException(paramTypeAdapterFactory.toString());
  }
  
  public boolean htmlSafe()
  {
    return this.htmlSafe;
  }
  
  public GsonBuilder newBuilder()
  {
    return new GsonBuilder(this);
  }
  
  public JsonReader newJsonReader(Reader paramReader)
  {
    paramReader = new JsonReader(paramReader);
    paramReader.setLenient(this.lenient);
    return paramReader;
  }
  
  public JsonWriter newJsonWriter(Writer paramWriter)
    throws IOException
  {
    if (this.generateNonExecutableJson) {
      paramWriter.write(")]}'\n");
    }
    paramWriter = new JsonWriter(paramWriter);
    if (this.prettyPrinting) {
      paramWriter.setIndent("  ");
    }
    paramWriter.setSerializeNulls(this.serializeNulls);
    return paramWriter;
  }
  
  public boolean serializeNulls()
  {
    return this.serializeNulls;
  }
  
  public String toJson(JsonElement paramJsonElement)
  {
    StringWriter localStringWriter = new StringWriter();
    toJson(paramJsonElement, localStringWriter);
    return localStringWriter.toString();
  }
  
  public String toJson(Object paramObject)
  {
    if (paramObject == null) {
      return toJson(JsonNull.INSTANCE);
    }
    return toJson(paramObject, paramObject.getClass());
  }
  
  public String toJson(Object paramObject, Type paramType)
  {
    StringWriter localStringWriter = new StringWriter();
    toJson(paramObject, paramType, localStringWriter);
    return localStringWriter.toString();
  }
  
  /* Error */
  public void toJson(JsonElement paramJsonElement, JsonWriter paramJsonWriter)
    throws JsonIOException
  {
    // Byte code:
    //   0: aload_2
    //   1: invokevirtual 639	com/google/gson/stream/JsonWriter:isLenient	()Z
    //   4: istore_3
    //   5: aload_2
    //   6: iconst_1
    //   7: invokevirtual 640	com/google/gson/stream/JsonWriter:setLenient	(Z)V
    //   10: aload_2
    //   11: invokevirtual 643	com/google/gson/stream/JsonWriter:isHtmlSafe	()Z
    //   14: istore 4
    //   16: aload_2
    //   17: aload_0
    //   18: getfield 141	com/google/gson/Gson:htmlSafe	Z
    //   21: invokevirtual 646	com/google/gson/stream/JsonWriter:setHtmlSafe	(Z)V
    //   24: aload_2
    //   25: invokevirtual 649	com/google/gson/stream/JsonWriter:getSerializeNulls	()Z
    //   28: istore 5
    //   30: aload_2
    //   31: aload_0
    //   32: getfield 135	com/google/gson/Gson:serializeNulls	Z
    //   35: invokevirtual 609	com/google/gson/stream/JsonWriter:setSerializeNulls	(Z)V
    //   38: aload_1
    //   39: aload_2
    //   40: invokestatic 653	com/google/gson/internal/Streams:write	(Lcom/google/gson/JsonElement;Lcom/google/gson/stream/JsonWriter;)V
    //   43: aload_2
    //   44: iload_3
    //   45: invokevirtual 640	com/google/gson/stream/JsonWriter:setLenient	(Z)V
    //   48: aload_2
    //   49: iload 4
    //   51: invokevirtual 646	com/google/gson/stream/JsonWriter:setHtmlSafe	(Z)V
    //   54: aload_2
    //   55: iload 5
    //   57: invokevirtual 609	com/google/gson/stream/JsonWriter:setSerializeNulls	(Z)V
    //   60: return
    //   61: astore_1
    //   62: goto +72 -> 134
    //   65: astore 6
    //   67: new 466	java/lang/AssertionError
    //   70: astore_1
    //   71: new 397	java/lang/StringBuilder
    //   74: astore 7
    //   76: aload 7
    //   78: invokespecial 398	java/lang/StringBuilder:<init>	()V
    //   81: aload 7
    //   83: ldc_w 489
    //   86: invokevirtual 407	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   89: pop
    //   90: aload 7
    //   92: aload 6
    //   94: invokevirtual 492	java/lang/AssertionError:getMessage	()Ljava/lang/String;
    //   97: invokevirtual 407	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   100: pop
    //   101: aload_1
    //   102: aload 7
    //   104: invokevirtual 413	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   107: invokespecial 495	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   110: aload_1
    //   111: aload 6
    //   113: invokevirtual 499	java/lang/AssertionError:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   116: pop
    //   117: aload_1
    //   118: athrow
    //   119: astore_1
    //   120: new 364	com/google/gson/JsonIOException
    //   123: astore 7
    //   125: aload 7
    //   127: aload_1
    //   128: invokespecial 372	com/google/gson/JsonIOException:<init>	(Ljava/lang/Throwable;)V
    //   131: aload 7
    //   133: athrow
    //   134: aload_2
    //   135: iload_3
    //   136: invokevirtual 640	com/google/gson/stream/JsonWriter:setLenient	(Z)V
    //   139: aload_2
    //   140: iload 4
    //   142: invokevirtual 646	com/google/gson/stream/JsonWriter:setHtmlSafe	(Z)V
    //   145: aload_2
    //   146: iload 5
    //   148: invokevirtual 609	com/google/gson/stream/JsonWriter:setSerializeNulls	(Z)V
    //   151: aload_1
    //   152: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	Gson
    //   0	153	1	paramJsonElement	JsonElement
    //   0	153	2	paramJsonWriter	JsonWriter
    //   4	132	3	bool1	boolean
    //   14	127	4	bool2	boolean
    //   28	119	5	bool3	boolean
    //   65	47	6	localAssertionError	AssertionError
    //   74	58	7	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   38	43	61	finally
    //   67	119	61	finally
    //   120	134	61	finally
    //   38	43	65	java/lang/AssertionError
    //   38	43	119	java/io/IOException
  }
  
  public void toJson(JsonElement paramJsonElement, Appendable paramAppendable)
    throws JsonIOException
  {
    try
    {
      toJson(paramJsonElement, newJsonWriter(Streams.writerForAppendable(paramAppendable)));
      return;
    }
    catch (IOException paramJsonElement)
    {
      throw new JsonIOException(paramJsonElement);
    }
  }
  
  public void toJson(Object paramObject, Appendable paramAppendable)
    throws JsonIOException
  {
    if (paramObject != null) {
      toJson(paramObject, paramObject.getClass(), paramAppendable);
    } else {
      toJson(JsonNull.INSTANCE, paramAppendable);
    }
  }
  
  /* Error */
  public void toJson(Object paramObject, Type paramType, JsonWriter paramJsonWriter)
    throws JsonIOException
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_2
    //   2: invokestatic 477	com/google/gson/reflect/TypeToken:get	(Ljava/lang/reflect/Type;)Lcom/google/gson/reflect/TypeToken;
    //   5: invokevirtual 481	com/google/gson/Gson:getAdapter	(Lcom/google/gson/reflect/TypeToken;)Lcom/google/gson/TypeAdapter;
    //   8: astore_2
    //   9: aload_3
    //   10: invokevirtual 639	com/google/gson/stream/JsonWriter:isLenient	()Z
    //   13: istore 4
    //   15: aload_3
    //   16: iconst_1
    //   17: invokevirtual 640	com/google/gson/stream/JsonWriter:setLenient	(Z)V
    //   20: aload_3
    //   21: invokevirtual 643	com/google/gson/stream/JsonWriter:isHtmlSafe	()Z
    //   24: istore 5
    //   26: aload_3
    //   27: aload_0
    //   28: getfield 141	com/google/gson/Gson:htmlSafe	Z
    //   31: invokevirtual 646	com/google/gson/stream/JsonWriter:setHtmlSafe	(Z)V
    //   34: aload_3
    //   35: invokevirtual 649	com/google/gson/stream/JsonWriter:getSerializeNulls	()Z
    //   38: istore 6
    //   40: aload_3
    //   41: aload_0
    //   42: getfield 135	com/google/gson/Gson:serializeNulls	Z
    //   45: invokevirtual 609	com/google/gson/stream/JsonWriter:setSerializeNulls	(Z)V
    //   48: aload_2
    //   49: aload_3
    //   50: aload_1
    //   51: invokevirtual 666	com/google/gson/TypeAdapter:write	(Lcom/google/gson/stream/JsonWriter;Ljava/lang/Object;)V
    //   54: aload_3
    //   55: iload 4
    //   57: invokevirtual 640	com/google/gson/stream/JsonWriter:setLenient	(Z)V
    //   60: aload_3
    //   61: iload 5
    //   63: invokevirtual 646	com/google/gson/stream/JsonWriter:setHtmlSafe	(Z)V
    //   66: aload_3
    //   67: iload 6
    //   69: invokevirtual 609	com/google/gson/stream/JsonWriter:setSerializeNulls	(Z)V
    //   72: return
    //   73: astore_1
    //   74: goto +66 -> 140
    //   77: astore_2
    //   78: new 466	java/lang/AssertionError
    //   81: astore_1
    //   82: new 397	java/lang/StringBuilder
    //   85: astore 7
    //   87: aload 7
    //   89: invokespecial 398	java/lang/StringBuilder:<init>	()V
    //   92: aload 7
    //   94: ldc_w 489
    //   97: invokevirtual 407	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   100: pop
    //   101: aload 7
    //   103: aload_2
    //   104: invokevirtual 492	java/lang/AssertionError:getMessage	()Ljava/lang/String;
    //   107: invokevirtual 407	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   110: pop
    //   111: aload_1
    //   112: aload 7
    //   114: invokevirtual 413	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   117: invokespecial 495	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   120: aload_1
    //   121: aload_2
    //   122: invokevirtual 499	java/lang/AssertionError:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   125: pop
    //   126: aload_1
    //   127: athrow
    //   128: astore_1
    //   129: new 364	com/google/gson/JsonIOException
    //   132: astore_2
    //   133: aload_2
    //   134: aload_1
    //   135: invokespecial 372	com/google/gson/JsonIOException:<init>	(Ljava/lang/Throwable;)V
    //   138: aload_2
    //   139: athrow
    //   140: aload_3
    //   141: iload 4
    //   143: invokevirtual 640	com/google/gson/stream/JsonWriter:setLenient	(Z)V
    //   146: aload_3
    //   147: iload 5
    //   149: invokevirtual 646	com/google/gson/stream/JsonWriter:setHtmlSafe	(Z)V
    //   152: aload_3
    //   153: iload 6
    //   155: invokevirtual 609	com/google/gson/stream/JsonWriter:setSerializeNulls	(Z)V
    //   158: aload_1
    //   159: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	160	0	this	Gson
    //   0	160	1	paramObject	Object
    //   0	160	2	paramType	Type
    //   0	160	3	paramJsonWriter	JsonWriter
    //   13	129	4	bool1	boolean
    //   24	124	5	bool2	boolean
    //   38	116	6	bool3	boolean
    //   85	28	7	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   48	54	73	finally
    //   78	128	73	finally
    //   129	140	73	finally
    //   48	54	77	java/lang/AssertionError
    //   48	54	128	java/io/IOException
  }
  
  public void toJson(Object paramObject, Type paramType, Appendable paramAppendable)
    throws JsonIOException
  {
    try
    {
      toJson(paramObject, paramType, newJsonWriter(Streams.writerForAppendable(paramAppendable)));
      return;
    }
    catch (IOException paramObject)
    {
      throw new JsonIOException(paramObject);
    }
  }
  
  public JsonElement toJsonTree(Object paramObject)
  {
    if (paramObject == null) {
      return JsonNull.INSTANCE;
    }
    return toJsonTree(paramObject, paramObject.getClass());
  }
  
  public JsonElement toJsonTree(Object paramObject, Type paramType)
  {
    JsonTreeWriter localJsonTreeWriter = new JsonTreeWriter();
    toJson(paramObject, paramType, localJsonTreeWriter);
    return localJsonTreeWriter.get();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("{serializeNulls:");
    localStringBuilder.append(this.serializeNulls);
    localStringBuilder.append(",factories:");
    localStringBuilder.append(this.factories);
    localStringBuilder.append(",instanceCreators:");
    localStringBuilder.append(this.constructorConstructor);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
  
  static class FutureTypeAdapter<T>
    extends TypeAdapter<T>
  {
    private TypeAdapter<T> delegate;
    
    FutureTypeAdapter() {}
    
    public T read(JsonReader paramJsonReader)
      throws IOException
    {
      TypeAdapter localTypeAdapter = this.delegate;
      if (localTypeAdapter != null) {
        return localTypeAdapter.read(paramJsonReader);
      }
      throw new IllegalStateException();
    }
    
    public void setDelegate(TypeAdapter<T> paramTypeAdapter)
    {
      if (this.delegate == null)
      {
        this.delegate = paramTypeAdapter;
        return;
      }
      throw new AssertionError();
    }
    
    public void write(JsonWriter paramJsonWriter, T paramT)
      throws IOException
    {
      TypeAdapter localTypeAdapter = this.delegate;
      if (localTypeAdapter != null)
      {
        localTypeAdapter.write(paramJsonWriter, paramT);
        return;
      }
      throw new IllegalStateException();
    }
  }
}

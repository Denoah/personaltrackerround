package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal..Gson.Preconditions;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public final class TreeTypeAdapter<T>
  extends TypeAdapter<T>
{
  private final TreeTypeAdapter<T>.GsonContextImpl context = new GsonContextImpl(null);
  private TypeAdapter<T> delegate;
  private final JsonDeserializer<T> deserializer;
  final Gson gson;
  private final JsonSerializer<T> serializer;
  private final TypeAdapterFactory skipPast;
  private final TypeToken<T> typeToken;
  
  public TreeTypeAdapter(JsonSerializer<T> paramJsonSerializer, JsonDeserializer<T> paramJsonDeserializer, Gson paramGson, TypeToken<T> paramTypeToken, TypeAdapterFactory paramTypeAdapterFactory)
  {
    this.serializer = paramJsonSerializer;
    this.deserializer = paramJsonDeserializer;
    this.gson = paramGson;
    this.typeToken = paramTypeToken;
    this.skipPast = paramTypeAdapterFactory;
  }
  
  private TypeAdapter<T> delegate()
  {
    TypeAdapter localTypeAdapter = this.delegate;
    if (localTypeAdapter == null)
    {
      localTypeAdapter = this.gson.getDelegateAdapter(this.skipPast, this.typeToken);
      this.delegate = localTypeAdapter;
    }
    return localTypeAdapter;
  }
  
  public static TypeAdapterFactory newFactory(TypeToken<?> paramTypeToken, Object paramObject)
  {
    return new SingleTypeFactory(paramObject, paramTypeToken, false, null);
  }
  
  public static TypeAdapterFactory newFactoryWithMatchRawType(TypeToken<?> paramTypeToken, Object paramObject)
  {
    boolean bool;
    if (paramTypeToken.getType() == paramTypeToken.getRawType()) {
      bool = true;
    } else {
      bool = false;
    }
    return new SingleTypeFactory(paramObject, paramTypeToken, bool, null);
  }
  
  public static TypeAdapterFactory newTypeHierarchyFactory(Class<?> paramClass, Object paramObject)
  {
    return new SingleTypeFactory(paramObject, null, false, paramClass);
  }
  
  public T read(JsonReader paramJsonReader)
    throws IOException
  {
    if (this.deserializer == null) {
      return delegate().read(paramJsonReader);
    }
    paramJsonReader = Streams.parse(paramJsonReader);
    if (paramJsonReader.isJsonNull()) {
      return null;
    }
    return this.deserializer.deserialize(paramJsonReader, this.typeToken.getType(), this.context);
  }
  
  public void write(JsonWriter paramJsonWriter, T paramT)
    throws IOException
  {
    JsonSerializer localJsonSerializer = this.serializer;
    if (localJsonSerializer == null)
    {
      delegate().write(paramJsonWriter, paramT);
      return;
    }
    if (paramT == null)
    {
      paramJsonWriter.nullValue();
      return;
    }
    Streams.write(localJsonSerializer.serialize(paramT, this.typeToken.getType(), this.context), paramJsonWriter);
  }
  
  private final class GsonContextImpl
    implements JsonSerializationContext, JsonDeserializationContext
  {
    private GsonContextImpl() {}
    
    public <R> R deserialize(JsonElement paramJsonElement, Type paramType)
      throws JsonParseException
    {
      return TreeTypeAdapter.this.gson.fromJson(paramJsonElement, paramType);
    }
    
    public JsonElement serialize(Object paramObject)
    {
      return TreeTypeAdapter.this.gson.toJsonTree(paramObject);
    }
    
    public JsonElement serialize(Object paramObject, Type paramType)
    {
      return TreeTypeAdapter.this.gson.toJsonTree(paramObject, paramType);
    }
  }
  
  private static final class SingleTypeFactory
    implements TypeAdapterFactory
  {
    private final JsonDeserializer<?> deserializer;
    private final TypeToken<?> exactType;
    private final Class<?> hierarchyType;
    private final boolean matchRawType;
    private final JsonSerializer<?> serializer;
    
    SingleTypeFactory(Object paramObject, TypeToken<?> paramTypeToken, boolean paramBoolean, Class<?> paramClass)
    {
      boolean bool = paramObject instanceof JsonSerializer;
      Object localObject1 = null;
      if (bool) {
        localObject2 = (JsonSerializer)paramObject;
      } else {
        localObject2 = null;
      }
      this.serializer = ((JsonSerializer)localObject2);
      Object localObject2 = localObject1;
      if ((paramObject instanceof JsonDeserializer)) {
        localObject2 = (JsonDeserializer)paramObject;
      }
      this.deserializer = ((JsonDeserializer)localObject2);
      if ((this.serializer == null) && (localObject2 == null)) {
        bool = false;
      } else {
        bool = true;
      }
      .Gson.Preconditions.checkArgument(bool);
      this.exactType = paramTypeToken;
      this.matchRawType = paramBoolean;
      this.hierarchyType = paramClass;
    }
    
    public <T> TypeAdapter<T> create(Gson paramGson, TypeToken<T> paramTypeToken)
    {
      TypeToken localTypeToken = this.exactType;
      boolean bool;
      if (localTypeToken != null)
      {
        if ((!localTypeToken.equals(paramTypeToken)) && ((!this.matchRawType) || (this.exactType.getType() != paramTypeToken.getRawType()))) {
          bool = false;
        } else {
          bool = true;
        }
      }
      else {
        bool = this.hierarchyType.isAssignableFrom(paramTypeToken.getRawType());
      }
      if (bool) {
        paramGson = new TreeTypeAdapter(this.serializer, this.deserializer, paramGson, paramTypeToken, this);
      } else {
        paramGson = null;
      }
      return paramGson;
    }
  }
}

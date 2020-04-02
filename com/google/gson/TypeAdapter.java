package com.google.gson;

import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.internal.bind.JsonTreeWriter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public abstract class TypeAdapter<T>
{
  public TypeAdapter() {}
  
  public final T fromJson(Reader paramReader)
    throws IOException
  {
    return read(new JsonReader(paramReader));
  }
  
  public final T fromJson(String paramString)
    throws IOException
  {
    return fromJson(new StringReader(paramString));
  }
  
  public final T fromJsonTree(JsonElement paramJsonElement)
  {
    try
    {
      JsonTreeReader localJsonTreeReader = new com/google/gson/internal/bind/JsonTreeReader;
      localJsonTreeReader.<init>(paramJsonElement);
      paramJsonElement = read(localJsonTreeReader);
      return paramJsonElement;
    }
    catch (IOException paramJsonElement)
    {
      throw new JsonIOException(paramJsonElement);
    }
  }
  
  public final TypeAdapter<T> nullSafe()
  {
    new TypeAdapter()
    {
      public T read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return TypeAdapter.this.read(paramAnonymousJsonReader);
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, T paramAnonymousT)
        throws IOException
      {
        if (paramAnonymousT == null) {
          paramAnonymousJsonWriter.nullValue();
        } else {
          TypeAdapter.this.write(paramAnonymousJsonWriter, paramAnonymousT);
        }
      }
    };
  }
  
  public abstract T read(JsonReader paramJsonReader)
    throws IOException;
  
  public final String toJson(T paramT)
  {
    StringWriter localStringWriter = new StringWriter();
    try
    {
      toJson(localStringWriter, paramT);
      return localStringWriter.toString();
    }
    catch (IOException paramT)
    {
      throw new AssertionError(paramT);
    }
  }
  
  public final void toJson(Writer paramWriter, T paramT)
    throws IOException
  {
    write(new JsonWriter(paramWriter), paramT);
  }
  
  public final JsonElement toJsonTree(T paramT)
  {
    try
    {
      JsonTreeWriter localJsonTreeWriter = new com/google/gson/internal/bind/JsonTreeWriter;
      localJsonTreeWriter.<init>();
      write(localJsonTreeWriter, paramT);
      paramT = localJsonTreeWriter.get();
      return paramT;
    }
    catch (IOException paramT)
    {
      throw new JsonIOException(paramT);
    }
  }
  
  public abstract void write(JsonWriter paramJsonWriter, T paramT)
    throws IOException;
}

package com.google.gson.internal.bind;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public final class JsonTreeReader
  extends JsonReader
{
  private static final Object SENTINEL_CLOSED = new Object();
  private static final Reader UNREADABLE_READER = new Reader()
  {
    public void close()
      throws IOException
    {
      throw new AssertionError();
    }
    
    public int read(char[] paramAnonymousArrayOfChar, int paramAnonymousInt1, int paramAnonymousInt2)
      throws IOException
    {
      throw new AssertionError();
    }
  };
  private int[] pathIndices = new int[32];
  private String[] pathNames = new String[32];
  private Object[] stack = new Object[32];
  private int stackSize = 0;
  
  public JsonTreeReader(JsonElement paramJsonElement)
  {
    super(UNREADABLE_READER);
    push(paramJsonElement);
  }
  
  private void expect(JsonToken paramJsonToken)
    throws IOException
  {
    if (peek() == paramJsonToken) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Expected ");
    localStringBuilder.append(paramJsonToken);
    localStringBuilder.append(" but was ");
    localStringBuilder.append(peek());
    localStringBuilder.append(locationString());
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  private String locationString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(" at path ");
    localStringBuilder.append(getPath());
    return localStringBuilder.toString();
  }
  
  private Object peekStack()
  {
    return this.stack[(this.stackSize - 1)];
  }
  
  private Object popStack()
  {
    Object[] arrayOfObject = this.stack;
    int i = this.stackSize - 1;
    this.stackSize = i;
    Object localObject = arrayOfObject[i];
    arrayOfObject[i] = null;
    return localObject;
  }
  
  private void push(Object paramObject)
  {
    int i = this.stackSize;
    Object[] arrayOfObject = this.stack;
    if (i == arrayOfObject.length)
    {
      i *= 2;
      this.stack = Arrays.copyOf(arrayOfObject, i);
      this.pathIndices = Arrays.copyOf(this.pathIndices, i);
      this.pathNames = ((String[])Arrays.copyOf(this.pathNames, i));
    }
    arrayOfObject = this.stack;
    i = this.stackSize;
    this.stackSize = (i + 1);
    arrayOfObject[i] = paramObject;
  }
  
  public void beginArray()
    throws IOException
  {
    expect(JsonToken.BEGIN_ARRAY);
    push(((JsonArray)peekStack()).iterator());
    this.pathIndices[(this.stackSize - 1)] = 0;
  }
  
  public void beginObject()
    throws IOException
  {
    expect(JsonToken.BEGIN_OBJECT);
    push(((JsonObject)peekStack()).entrySet().iterator());
  }
  
  public void close()
    throws IOException
  {
    this.stack = new Object[] { SENTINEL_CLOSED };
    this.stackSize = 1;
  }
  
  public void endArray()
    throws IOException
  {
    expect(JsonToken.END_ARRAY);
    popStack();
    popStack();
    int i = this.stackSize;
    if (i > 0)
    {
      int[] arrayOfInt = this.pathIndices;
      i--;
      arrayOfInt[i] += 1;
    }
  }
  
  public void endObject()
    throws IOException
  {
    expect(JsonToken.END_OBJECT);
    popStack();
    popStack();
    int i = this.stackSize;
    if (i > 0)
    {
      int[] arrayOfInt = this.pathIndices;
      i--;
      arrayOfInt[i] += 1;
    }
  }
  
  public String getPath()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append('$');
    int j;
    for (int i = 0; i < this.stackSize; i = j + 1)
    {
      Object localObject = this.stack;
      if ((localObject[i] instanceof JsonArray))
      {
        i++;
        j = i;
        if ((localObject[i] instanceof Iterator))
        {
          localStringBuilder.append('[');
          localStringBuilder.append(this.pathIndices[i]);
          localStringBuilder.append(']');
          j = i;
        }
      }
      else
      {
        j = i;
        if ((localObject[i] instanceof JsonObject))
        {
          i++;
          j = i;
          if ((localObject[i] instanceof Iterator))
          {
            localStringBuilder.append('.');
            localObject = this.pathNames;
            j = i;
            if (localObject[i] != null)
            {
              localStringBuilder.append(localObject[i]);
              j = i;
            }
          }
        }
      }
    }
    return localStringBuilder.toString();
  }
  
  public boolean hasNext()
    throws IOException
  {
    JsonToken localJsonToken = peek();
    boolean bool;
    if ((localJsonToken != JsonToken.END_OBJECT) && (localJsonToken != JsonToken.END_ARRAY)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean nextBoolean()
    throws IOException
  {
    expect(JsonToken.BOOLEAN);
    boolean bool = ((JsonPrimitive)popStack()).getAsBoolean();
    int i = this.stackSize;
    if (i > 0)
    {
      int[] arrayOfInt = this.pathIndices;
      i--;
      arrayOfInt[i] += 1;
    }
    return bool;
  }
  
  public double nextDouble()
    throws IOException
  {
    JsonToken localJsonToken = peek();
    Object localObject;
    if ((localJsonToken != JsonToken.NUMBER) && (localJsonToken != JsonToken.STRING))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Expected ");
      ((StringBuilder)localObject).append(JsonToken.NUMBER);
      ((StringBuilder)localObject).append(" but was ");
      ((StringBuilder)localObject).append(localJsonToken);
      ((StringBuilder)localObject).append(locationString());
      throw new IllegalStateException(((StringBuilder)localObject).toString());
    }
    double d = ((JsonPrimitive)peekStack()).getAsDouble();
    if ((!isLenient()) && ((Double.isNaN(d)) || (Double.isInfinite(d))))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("JSON forbids NaN and infinities: ");
      ((StringBuilder)localObject).append(d);
      throw new NumberFormatException(((StringBuilder)localObject).toString());
    }
    popStack();
    int i = this.stackSize;
    if (i > 0)
    {
      localObject = this.pathIndices;
      i--;
      localObject[i] += 1;
    }
    return d;
  }
  
  public int nextInt()
    throws IOException
  {
    JsonToken localJsonToken = peek();
    Object localObject;
    if ((localJsonToken != JsonToken.NUMBER) && (localJsonToken != JsonToken.STRING))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Expected ");
      ((StringBuilder)localObject).append(JsonToken.NUMBER);
      ((StringBuilder)localObject).append(" but was ");
      ((StringBuilder)localObject).append(localJsonToken);
      ((StringBuilder)localObject).append(locationString());
      throw new IllegalStateException(((StringBuilder)localObject).toString());
    }
    int i = ((JsonPrimitive)peekStack()).getAsInt();
    popStack();
    int j = this.stackSize;
    if (j > 0)
    {
      localObject = this.pathIndices;
      j--;
      localObject[j] += 1;
    }
    return i;
  }
  
  public long nextLong()
    throws IOException
  {
    JsonToken localJsonToken = peek();
    Object localObject;
    if ((localJsonToken != JsonToken.NUMBER) && (localJsonToken != JsonToken.STRING))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Expected ");
      ((StringBuilder)localObject).append(JsonToken.NUMBER);
      ((StringBuilder)localObject).append(" but was ");
      ((StringBuilder)localObject).append(localJsonToken);
      ((StringBuilder)localObject).append(locationString());
      throw new IllegalStateException(((StringBuilder)localObject).toString());
    }
    long l = ((JsonPrimitive)peekStack()).getAsLong();
    popStack();
    int i = this.stackSize;
    if (i > 0)
    {
      localObject = this.pathIndices;
      i--;
      localObject[i] += 1;
    }
    return l;
  }
  
  public String nextName()
    throws IOException
  {
    expect(JsonToken.NAME);
    Map.Entry localEntry = (Map.Entry)((Iterator)peekStack()).next();
    String str = (String)localEntry.getKey();
    this.pathNames[(this.stackSize - 1)] = str;
    push(localEntry.getValue());
    return str;
  }
  
  public void nextNull()
    throws IOException
  {
    expect(JsonToken.NULL);
    popStack();
    int i = this.stackSize;
    if (i > 0)
    {
      int[] arrayOfInt = this.pathIndices;
      i--;
      arrayOfInt[i] += 1;
    }
  }
  
  public String nextString()
    throws IOException
  {
    Object localObject1 = peek();
    Object localObject2;
    if ((localObject1 != JsonToken.STRING) && (localObject1 != JsonToken.NUMBER))
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Expected ");
      ((StringBuilder)localObject2).append(JsonToken.STRING);
      ((StringBuilder)localObject2).append(" but was ");
      ((StringBuilder)localObject2).append(localObject1);
      ((StringBuilder)localObject2).append(locationString());
      throw new IllegalStateException(((StringBuilder)localObject2).toString());
    }
    localObject1 = ((JsonPrimitive)popStack()).getAsString();
    int i = this.stackSize;
    if (i > 0)
    {
      localObject2 = this.pathIndices;
      i--;
      localObject2[i] += 1;
    }
    return localObject1;
  }
  
  public JsonToken peek()
    throws IOException
  {
    if (this.stackSize == 0) {
      return JsonToken.END_DOCUMENT;
    }
    Object localObject = peekStack();
    if ((localObject instanceof Iterator))
    {
      boolean bool = this.stack[(this.stackSize - 2)] instanceof JsonObject;
      localObject = (Iterator)localObject;
      if (((Iterator)localObject).hasNext())
      {
        if (bool) {
          return JsonToken.NAME;
        }
        push(((Iterator)localObject).next());
        return peek();
      }
      if (bool) {
        localObject = JsonToken.END_OBJECT;
      } else {
        localObject = JsonToken.END_ARRAY;
      }
      return localObject;
    }
    if ((localObject instanceof JsonObject)) {
      return JsonToken.BEGIN_OBJECT;
    }
    if ((localObject instanceof JsonArray)) {
      return JsonToken.BEGIN_ARRAY;
    }
    if ((localObject instanceof JsonPrimitive))
    {
      localObject = (JsonPrimitive)localObject;
      if (((JsonPrimitive)localObject).isString()) {
        return JsonToken.STRING;
      }
      if (((JsonPrimitive)localObject).isBoolean()) {
        return JsonToken.BOOLEAN;
      }
      if (((JsonPrimitive)localObject).isNumber()) {
        return JsonToken.NUMBER;
      }
      throw new AssertionError();
    }
    if ((localObject instanceof JsonNull)) {
      return JsonToken.NULL;
    }
    if (localObject == SENTINEL_CLOSED) {
      throw new IllegalStateException("JsonReader is closed");
    }
    throw new AssertionError();
  }
  
  public void promoteNameToValue()
    throws IOException
  {
    expect(JsonToken.NAME);
    Map.Entry localEntry = (Map.Entry)((Iterator)peekStack()).next();
    push(localEntry.getValue());
    push(new JsonPrimitive((String)localEntry.getKey()));
  }
  
  public void skipValue()
    throws IOException
  {
    if (peek() == JsonToken.NAME)
    {
      nextName();
      this.pathNames[(this.stackSize - 2)] = "null";
    }
    else
    {
      popStack();
      i = this.stackSize;
      if (i > 0) {
        this.pathNames[(i - 1)] = "null";
      }
    }
    int i = this.stackSize;
    if (i > 0)
    {
      int[] arrayOfInt = this.pathIndices;
      i--;
      arrayOfInt[i] += 1;
    }
  }
  
  public String toString()
  {
    return getClass().getSimpleName();
  }
}

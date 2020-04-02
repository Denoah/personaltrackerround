package com.google.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public final class JsonParser
{
  @Deprecated
  public JsonParser() {}
  
  /* Error */
  public static JsonElement parseReader(JsonReader paramJsonReader)
    throws JsonIOException, JsonSyntaxException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 27	com/google/gson/stream/JsonReader:isLenient	()Z
    //   4: istore_1
    //   5: aload_0
    //   6: iconst_1
    //   7: invokevirtual 31	com/google/gson/stream/JsonReader:setLenient	(Z)V
    //   10: aload_0
    //   11: invokestatic 36	com/google/gson/internal/Streams:parse	(Lcom/google/gson/stream/JsonReader;)Lcom/google/gson/JsonElement;
    //   14: astore_2
    //   15: aload_0
    //   16: iload_1
    //   17: invokevirtual 31	com/google/gson/stream/JsonReader:setLenient	(Z)V
    //   20: aload_2
    //   21: areturn
    //   22: astore_2
    //   23: goto +100 -> 123
    //   26: astore_2
    //   27: new 38	com/google/gson/JsonParseException
    //   30: astore_3
    //   31: new 40	java/lang/StringBuilder
    //   34: astore 4
    //   36: aload 4
    //   38: invokespecial 41	java/lang/StringBuilder:<init>	()V
    //   41: aload 4
    //   43: ldc 43
    //   45: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   48: pop
    //   49: aload 4
    //   51: aload_0
    //   52: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   55: pop
    //   56: aload 4
    //   58: ldc 52
    //   60: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   63: pop
    //   64: aload_3
    //   65: aload 4
    //   67: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   70: aload_2
    //   71: invokespecial 59	com/google/gson/JsonParseException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   74: aload_3
    //   75: athrow
    //   76: astore_2
    //   77: new 38	com/google/gson/JsonParseException
    //   80: astore 4
    //   82: new 40	java/lang/StringBuilder
    //   85: astore_3
    //   86: aload_3
    //   87: invokespecial 41	java/lang/StringBuilder:<init>	()V
    //   90: aload_3
    //   91: ldc 43
    //   93: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   96: pop
    //   97: aload_3
    //   98: aload_0
    //   99: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   102: pop
    //   103: aload_3
    //   104: ldc 52
    //   106: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   109: pop
    //   110: aload 4
    //   112: aload_3
    //   113: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   116: aload_2
    //   117: invokespecial 59	com/google/gson/JsonParseException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   120: aload 4
    //   122: athrow
    //   123: aload_0
    //   124: iload_1
    //   125: invokevirtual 31	com/google/gson/stream/JsonReader:setLenient	(Z)V
    //   128: aload_2
    //   129: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	130	0	paramJsonReader	JsonReader
    //   4	121	1	bool	boolean
    //   14	7	2	localJsonElement	JsonElement
    //   22	1	2	localObject1	Object
    //   26	45	2	localOutOfMemoryError	OutOfMemoryError
    //   76	53	2	localStackOverflowError	StackOverflowError
    //   30	83	3	localObject2	Object
    //   34	87	4	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   10	15	22	finally
    //   27	76	22	finally
    //   77	123	22	finally
    //   10	15	26	java/lang/OutOfMemoryError
    //   10	15	76	java/lang/StackOverflowError
  }
  
  public static JsonElement parseReader(Reader paramReader)
    throws JsonIOException, JsonSyntaxException
  {
    try
    {
      JsonReader localJsonReader = new com/google/gson/stream/JsonReader;
      localJsonReader.<init>(paramReader);
      paramReader = parseReader(localJsonReader);
      if ((!paramReader.isJsonNull()) && (localJsonReader.peek() != JsonToken.END_DOCUMENT))
      {
        paramReader = new com/google/gson/JsonSyntaxException;
        paramReader.<init>("Did not consume the entire document.");
        throw paramReader;
      }
      return paramReader;
    }
    catch (NumberFormatException paramReader)
    {
      throw new JsonSyntaxException(paramReader);
    }
    catch (IOException paramReader)
    {
      throw new JsonIOException(paramReader);
    }
    catch (MalformedJsonException paramReader)
    {
      throw new JsonSyntaxException(paramReader);
    }
  }
  
  public static JsonElement parseString(String paramString)
    throws JsonSyntaxException
  {
    return parseReader(new StringReader(paramString));
  }
  
  @Deprecated
  public JsonElement parse(JsonReader paramJsonReader)
    throws JsonIOException, JsonSyntaxException
  {
    return parseReader(paramJsonReader);
  }
  
  @Deprecated
  public JsonElement parse(Reader paramReader)
    throws JsonIOException, JsonSyntaxException
  {
    return parseReader(paramReader);
  }
  
  @Deprecated
  public JsonElement parse(String paramString)
    throws JsonSyntaxException
  {
    return parseString(paramString);
  }
}

package com.google.gson;

import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import java.io.EOFException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class JsonStreamParser
  implements Iterator<JsonElement>
{
  private final Object lock;
  private final JsonReader parser;
  
  public JsonStreamParser(Reader paramReader)
  {
    paramReader = new JsonReader(paramReader);
    this.parser = paramReader;
    paramReader.setLenient(true);
    this.lock = new Object();
  }
  
  public JsonStreamParser(String paramString)
  {
    this(new StringReader(paramString));
  }
  
  /* Error */
  public boolean hasNext()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 28	com/google/gson/JsonStreamParser:lock	Ljava/lang/Object;
    //   4: astore_1
    //   5: aload_1
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 22	com/google/gson/JsonStreamParser:parser	Lcom/google/gson/stream/JsonReader;
    //   11: invokevirtual 45	com/google/gson/stream/JsonReader:peek	()Lcom/google/gson/stream/JsonToken;
    //   14: astore_2
    //   15: getstatic 51	com/google/gson/stream/JsonToken:END_DOCUMENT	Lcom/google/gson/stream/JsonToken;
    //   18: astore_3
    //   19: aload_2
    //   20: aload_3
    //   21: if_acmpeq +9 -> 30
    //   24: iconst_1
    //   25: istore 4
    //   27: goto +6 -> 33
    //   30: iconst_0
    //   31: istore 4
    //   33: aload_1
    //   34: monitorexit
    //   35: iload 4
    //   37: ireturn
    //   38: astore_3
    //   39: goto +27 -> 66
    //   42: astore_3
    //   43: new 53	com/google/gson/JsonIOException
    //   46: astore_2
    //   47: aload_2
    //   48: aload_3
    //   49: invokespecial 56	com/google/gson/JsonIOException:<init>	(Ljava/lang/Throwable;)V
    //   52: aload_2
    //   53: athrow
    //   54: astore_2
    //   55: new 58	com/google/gson/JsonSyntaxException
    //   58: astore_3
    //   59: aload_3
    //   60: aload_2
    //   61: invokespecial 59	com/google/gson/JsonSyntaxException:<init>	(Ljava/lang/Throwable;)V
    //   64: aload_3
    //   65: athrow
    //   66: aload_1
    //   67: monitorexit
    //   68: aload_3
    //   69: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	70	0	this	JsonStreamParser
    //   4	63	1	localObject1	Object
    //   14	39	2	localObject2	Object
    //   54	7	2	localMalformedJsonException	com.google.gson.stream.MalformedJsonException
    //   18	3	3	localJsonToken	com.google.gson.stream.JsonToken
    //   38	1	3	localObject3	Object
    //   42	7	3	localIOException	java.io.IOException
    //   58	11	3	localJsonSyntaxException	JsonSyntaxException
    //   25	11	4	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   7	19	38	finally
    //   33	35	38	finally
    //   43	54	38	finally
    //   55	66	38	finally
    //   66	68	38	finally
    //   7	19	42	java/io/IOException
    //   7	19	54	com/google/gson/stream/MalformedJsonException
  }
  
  public JsonElement next()
    throws JsonParseException
  {
    if (hasNext()) {
      try
      {
        JsonElement localJsonElement = Streams.parse(this.parser);
        return localJsonElement;
      }
      catch (JsonParseException localJsonParseException)
      {
        Object localObject = localJsonParseException;
        if ((localJsonParseException.getCause() instanceof EOFException)) {
          localObject = new NoSuchElementException();
        }
        throw ((Throwable)localObject);
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        throw new JsonParseException("Failed parsing JSON source to Json", localOutOfMemoryError);
      }
      catch (StackOverflowError localStackOverflowError)
      {
        throw new JsonParseException("Failed parsing JSON source to Json", localStackOverflowError);
      }
    }
    throw new NoSuchElementException();
  }
  
  public void remove()
  {
    throw new UnsupportedOperationException();
  }
}

package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class SqlDateTypeAdapter
  extends TypeAdapter<Date>
{
  public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory()
  {
    public <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
    {
      if (paramAnonymousTypeToken.getRawType() == Date.class) {
        paramAnonymousGson = new SqlDateTypeAdapter();
      } else {
        paramAnonymousGson = null;
      }
      return paramAnonymousGson;
    }
  };
  private final DateFormat format = new SimpleDateFormat("MMM d, yyyy");
  
  public SqlDateTypeAdapter() {}
  
  /* Error */
  public Date read(com.google.gson.stream.JsonReader paramJsonReader)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: invokevirtual 45	com/google/gson/stream/JsonReader:peek	()Lcom/google/gson/stream/JsonToken;
    //   6: getstatic 51	com/google/gson/stream/JsonToken:NULL	Lcom/google/gson/stream/JsonToken;
    //   9: if_acmpne +11 -> 20
    //   12: aload_1
    //   13: invokevirtual 54	com/google/gson/stream/JsonReader:nextNull	()V
    //   16: aload_0
    //   17: monitorexit
    //   18: aconst_null
    //   19: areturn
    //   20: new 56	java/sql/Date
    //   23: dup
    //   24: aload_0
    //   25: getfield 29	com/google/gson/internal/bind/SqlDateTypeAdapter:format	Ljava/text/DateFormat;
    //   28: aload_1
    //   29: invokevirtual 60	com/google/gson/stream/JsonReader:nextString	()Ljava/lang/String;
    //   32: invokevirtual 66	java/text/DateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
    //   35: invokevirtual 72	java/util/Date:getTime	()J
    //   38: invokespecial 75	java/sql/Date:<init>	(J)V
    //   41: astore_1
    //   42: aload_0
    //   43: monitorexit
    //   44: aload_1
    //   45: areturn
    //   46: astore_1
    //   47: new 77	com/google/gson/JsonSyntaxException
    //   50: astore_2
    //   51: aload_2
    //   52: aload_1
    //   53: invokespecial 80	com/google/gson/JsonSyntaxException:<init>	(Ljava/lang/Throwable;)V
    //   56: aload_2
    //   57: athrow
    //   58: astore_1
    //   59: aload_0
    //   60: monitorexit
    //   61: aload_1
    //   62: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	63	0	this	SqlDateTypeAdapter
    //   0	63	1	paramJsonReader	com.google.gson.stream.JsonReader
    //   50	7	2	localJsonSyntaxException	com.google.gson.JsonSyntaxException
    // Exception table:
    //   from	to	target	type
    //   20	42	46	java/text/ParseException
    //   2	16	58	finally
    //   20	42	58	finally
    //   47	58	58	finally
  }
  
  public void write(JsonWriter paramJsonWriter, Date paramDate)
    throws IOException
  {
    if (paramDate == null) {
      paramDate = null;
    }
    try
    {
      paramDate = this.format.format(paramDate);
      paramJsonWriter.value(paramDate);
      return;
    }
    finally {}
  }
}

package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.JavaVersion;
import com.google.gson.internal.PreJava9DateFormatProvider;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class DateTypeAdapter
  extends TypeAdapter<Date>
{
  public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory()
  {
    public <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
    {
      if (paramAnonymousTypeToken.getRawType() == Date.class) {
        paramAnonymousGson = new DateTypeAdapter();
      } else {
        paramAnonymousGson = null;
      }
      return paramAnonymousGson;
    }
  };
  private final List<DateFormat> dateFormats;
  
  public DateTypeAdapter()
  {
    ArrayList localArrayList = new ArrayList();
    this.dateFormats = localArrayList;
    localArrayList.add(DateFormat.getDateTimeInstance(2, 2, Locale.US));
    if (!Locale.getDefault().equals(Locale.US)) {
      this.dateFormats.add(DateFormat.getDateTimeInstance(2, 2));
    }
    if (JavaVersion.isJava9OrLater()) {
      this.dateFormats.add(PreJava9DateFormatProvider.getUSDateTimeFormat(2, 2));
    }
  }
  
  /* Error */
  private Date deserializeToDate(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 26	com/google/gson/internal/bind/DateTypeAdapter:dateFormats	Ljava/util/List;
    //   6: invokeinterface 73 1 0
    //   11: astore_2
    //   12: aload_2
    //   13: invokeinterface 78 1 0
    //   18: ifeq +23 -> 41
    //   21: aload_2
    //   22: invokeinterface 82 1 0
    //   27: checkcast 34	java/text/DateFormat
    //   30: astore_3
    //   31: aload_3
    //   32: aload_1
    //   33: invokevirtual 85	java/text/DateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
    //   36: astore_3
    //   37: aload_0
    //   38: monitorexit
    //   39: aload_3
    //   40: areturn
    //   41: new 87	java/text/ParsePosition
    //   44: astore_2
    //   45: aload_2
    //   46: iconst_0
    //   47: invokespecial 90	java/text/ParsePosition:<init>	(I)V
    //   50: aload_1
    //   51: aload_2
    //   52: invokestatic 95	com/google/gson/internal/bind/util/ISO8601Utils:parse	(Ljava/lang/String;Ljava/text/ParsePosition;)Ljava/util/Date;
    //   55: astore_2
    //   56: aload_0
    //   57: monitorexit
    //   58: aload_2
    //   59: areturn
    //   60: astore_3
    //   61: new 97	com/google/gson/JsonSyntaxException
    //   64: astore_2
    //   65: aload_2
    //   66: aload_1
    //   67: aload_3
    //   68: invokespecial 100	com/google/gson/JsonSyntaxException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   71: aload_2
    //   72: athrow
    //   73: astore_1
    //   74: aload_0
    //   75: monitorexit
    //   76: aload_1
    //   77: athrow
    //   78: astore_3
    //   79: goto -67 -> 12
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	82	0	this	DateTypeAdapter
    //   0	82	1	paramString	String
    //   11	61	2	localObject1	Object
    //   30	10	3	localObject2	Object
    //   60	8	3	localParseException1	java.text.ParseException
    //   78	1	3	localParseException2	java.text.ParseException
    // Exception table:
    //   from	to	target	type
    //   41	56	60	java/text/ParseException
    //   2	12	73	finally
    //   12	31	73	finally
    //   31	37	73	finally
    //   41	56	73	finally
    //   61	73	73	finally
    //   31	37	78	java/text/ParseException
  }
  
  public Date read(JsonReader paramJsonReader)
    throws IOException
  {
    if (paramJsonReader.peek() == JsonToken.NULL)
    {
      paramJsonReader.nextNull();
      return null;
    }
    return deserializeToDate(paramJsonReader.nextString());
  }
  
  public void write(JsonWriter paramJsonWriter, Date paramDate)
    throws IOException
  {
    if (paramDate == null) {}
    try
    {
      paramJsonWriter.nullValue();
      return;
    }
    finally {}
    paramJsonWriter.value(((DateFormat)this.dateFormats.get(0)).format(paramDate));
  }
}

package com.askgps.personaltrackercore.database.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\002\030\002\n\002\020\t\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\030\000 \0132\b\022\004\022\0020\0020\001:\001\013B\005?\006\002\020\003J\"\020\004\032\0020\0052\006\020\006\032\0020\0022\b\020\007\032\004\030\0010\b2\006\020\t\032\0020\nH\026?\006\f"}, d2={"Lcom/askgps/personaltrackercore/database/model/DateSerializer;", "Lcom/google/gson/JsonSerializer;", "", "()V", "serialize", "Lcom/google/gson/JsonElement;", "src", "typeOfSrc", "Ljava/lang/reflect/Type;", "context", "Lcom/google/gson/JsonSerializationContext;", "Companion", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class DateSerializer
  implements JsonSerializer<Long>
{
  public static final Companion Companion = new Companion(null);
  private static final SimpleDateFormat sdf;
  
  static
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    sdf = localSimpleDateFormat;
  }
  
  public DateSerializer() {}
  
  public JsonElement serialize(long paramLong, Type paramType, JsonSerializationContext paramJsonSerializationContext)
  {
    Intrinsics.checkParameterIsNotNull(paramJsonSerializationContext, "context");
    paramType = new Date();
    paramType.setTime(paramLong);
    paramType = paramJsonSerializationContext.serialize(sdf.format(paramType));
    Intrinsics.checkExpressionValueIsNotNull(paramType, "context.serialize(sdf.format(date))");
    return paramType;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\021\020\003\032\0020\004?\006\b\n\000\032\004\b\005\020\006?\006\007"}, d2={"Lcom/askgps/personaltrackercore/database/model/DateSerializer$Companion;", "", "()V", "sdf", "Ljava/text/SimpleDateFormat;", "getSdf", "()Ljava/text/SimpleDateFormat;", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final SimpleDateFormat getSdf()
    {
      return DateSerializer.access$getSdf$cp();
    }
  }
}

package com.google.gson;

public final class JsonNull
  extends JsonElement
{
  public static final JsonNull INSTANCE = new JsonNull();
  
  @Deprecated
  public JsonNull() {}
  
  public JsonNull deepCopy()
  {
    return INSTANCE;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if ((this != paramObject) && (!(paramObject instanceof JsonNull))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public int hashCode()
  {
    return JsonNull.class.hashCode();
  }
}

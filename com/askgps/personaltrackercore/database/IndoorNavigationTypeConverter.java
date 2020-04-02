package com.askgps.personaltrackercore.database;

import com.askgps.personaltrackercore.database.model.IndoorNavigation;
import com.google.gson.Gson;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\000\n\002\030\002\n\002\b\002\030\0002\0020\001B\005?\006\002\020\002J\024\020\003\032\004\030\0010\0042\b\020\005\032\004\030\0010\006H\007J\024\020\007\032\004\030\0010\0062\b\020\005\032\004\030\0010\004H\007?\006\b"}, d2={"Lcom/askgps/personaltrackercore/database/IndoorNavigationTypeConverter;", "", "()V", "fromIndoorNavigation", "", "indoorNavigation", "Lcom/askgps/personaltrackercore/database/model/IndoorNavigation;", "toIndoorNavigation", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class IndoorNavigationTypeConverter
{
  public IndoorNavigationTypeConverter() {}
  
  public final String fromIndoorNavigation(IndoorNavigation paramIndoorNavigation)
  {
    if (paramIndoorNavigation == null) {
      return null;
    }
    return new Gson().toJson(paramIndoorNavigation);
  }
  
  public final IndoorNavigation toIndoorNavigation(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    return (IndoorNavigation)new Gson().fromJson(paramString, IndoorNavigation.class);
  }
}

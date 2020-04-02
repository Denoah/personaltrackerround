package com.askgps.personaltrackercore.database;

import com.askgps.personaltrackercore.database.model.Location;
import java.util.List;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\034\n\002\030\002\n\002\020\000\n\000\n\002\020 \n\002\030\002\n\000\n\002\020\002\n\002\b\003\bg\030\0002\0020\001J\016\020\002\032\b\022\004\022\0020\0040\003H'J\026\020\005\032\0020\0062\f\020\007\032\b\022\004\022\0020\0040\003H'J\026\020\b\032\0020\0062\f\020\007\032\b\022\004\022\0020\0040\003H'?\006\t"}, d2={"Lcom/askgps/personaltrackercore/database/LocationDao;", "", "getLocations", "", "Lcom/askgps/personaltrackercore/database/model/Location;", "insertLocations", "", "items", "removeLocation", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public abstract interface LocationDao
{
  public abstract List<Location> getLocations();
  
  public abstract void insertLocations(List<Location> paramList);
  
  public abstract void removeLocation(List<Location> paramList);
}

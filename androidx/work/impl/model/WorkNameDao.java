package androidx.work.impl.model;

import java.util.List;

public abstract interface WorkNameDao
{
  public abstract List<String> getWorkSpecIdsWithName(String paramString);
  
  public abstract void insert(WorkName paramWorkName);
}

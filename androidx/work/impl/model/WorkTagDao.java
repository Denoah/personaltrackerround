package androidx.work.impl.model;

import java.util.List;

public abstract interface WorkTagDao
{
  public abstract List<String> getTagsForWorkSpecId(String paramString);
  
  public abstract List<String> getWorkSpecIdsWithTag(String paramString);
  
  public abstract void insert(WorkTag paramWorkTag);
}

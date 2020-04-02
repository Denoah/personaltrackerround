package androidx.work.impl.model;

import androidx.work.Data;
import java.util.List;

public abstract interface WorkProgressDao
{
  public abstract void delete(String paramString);
  
  public abstract void deleteAll();
  
  public abstract Data getProgressForWorkSpecId(String paramString);
  
  public abstract List<Data> getProgressForWorkSpecIds(List<String> paramList);
  
  public abstract void insert(WorkProgress paramWorkProgress);
}

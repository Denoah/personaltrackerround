package androidx.work.impl.model;

public abstract interface SystemIdInfoDao
{
  public abstract SystemIdInfo getSystemIdInfo(String paramString);
  
  public abstract void insertSystemIdInfo(SystemIdInfo paramSystemIdInfo);
  
  public abstract void removeSystemIdInfo(String paramString);
}

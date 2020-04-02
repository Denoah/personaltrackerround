package androidx.work.impl.model;

public class Dependency
{
  public final String prerequisiteId;
  public final String workSpecId;
  
  public Dependency(String paramString1, String paramString2)
  {
    this.workSpecId = paramString1;
    this.prerequisiteId = paramString2;
  }
}

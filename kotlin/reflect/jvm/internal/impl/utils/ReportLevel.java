package kotlin.reflect.jvm.internal.impl.utils;

public enum ReportLevel
{
  public static final Companion Companion = new Companion(null);
  private final String description;
  
  static
  {
    ReportLevel localReportLevel1 = new ReportLevel("IGNORE", 0, "ignore");
    IGNORE = localReportLevel1;
    ReportLevel localReportLevel2 = new ReportLevel("WARN", 1, "warn");
    WARN = localReportLevel2;
    ReportLevel localReportLevel3 = new ReportLevel("STRICT", 2, "strict");
    STRICT = localReportLevel3;
    $VALUES = new ReportLevel[] { localReportLevel1, localReportLevel2, localReportLevel3 };
  }
  
  private ReportLevel(String paramString)
  {
    this.description = paramString;
  }
  
  public final String getDescription()
  {
    return this.description;
  }
  
  public final boolean isIgnore()
  {
    boolean bool;
    if ((ReportLevel)this == IGNORE) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final boolean isWarning()
  {
    boolean bool;
    if ((ReportLevel)this == WARN) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final class Companion
  {
    private Companion() {}
  }
}

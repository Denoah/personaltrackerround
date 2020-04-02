package androidx.camera.extensions;

public class VersionName
{
  private static final VersionName CURRENT = new VersionName("1.1.0");
  private final Version mVersion;
  
  VersionName(int paramInt1, int paramInt2, int paramInt3, String paramString)
  {
    this.mVersion = Version.create(paramInt1, paramInt2, paramInt3, paramString);
  }
  
  VersionName(String paramString)
  {
    this.mVersion = Version.parse(paramString);
  }
  
  static VersionName getCurrentVersion()
  {
    return CURRENT;
  }
  
  public Version getVersion()
  {
    return this.mVersion;
  }
  
  public String toVersionString()
  {
    return this.mVersion.toString();
  }
}

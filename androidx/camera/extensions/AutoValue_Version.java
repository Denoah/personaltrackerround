package androidx.camera.extensions;

final class AutoValue_Version
  extends Version
{
  private final String description;
  private final int major;
  private final int minor;
  private final int patch;
  
  AutoValue_Version(int paramInt1, int paramInt2, int paramInt3, String paramString)
  {
    this.major = paramInt1;
    this.minor = paramInt2;
    this.patch = paramInt3;
    if (paramString != null)
    {
      this.description = paramString;
      return;
    }
    throw new NullPointerException("Null description");
  }
  
  String getDescription()
  {
    return this.description;
  }
  
  int getMajor()
  {
    return this.major;
  }
  
  int getMinor()
  {
    return this.minor;
  }
  
  int getPatch()
  {
    return this.patch;
  }
}

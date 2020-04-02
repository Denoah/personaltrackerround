package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

public abstract interface DeserializationConfiguration
{
  public abstract boolean getReleaseCoroutines();
  
  public abstract boolean getReportErrorsOnPreReleaseDependencies();
  
  public abstract boolean getSkipMetadataVersionCheck();
  
  public abstract boolean getTypeAliasesAllowed();
  
  public static final class Default
    implements DeserializationConfiguration
  {
    public static final Default INSTANCE = new Default();
    
    private Default() {}
    
    public boolean getReleaseCoroutines()
    {
      return DeserializationConfiguration.DefaultImpls.getReleaseCoroutines(this);
    }
    
    public boolean getReportErrorsOnPreReleaseDependencies()
    {
      return DeserializationConfiguration.DefaultImpls.getReportErrorsOnPreReleaseDependencies(this);
    }
    
    public boolean getSkipMetadataVersionCheck()
    {
      return DeserializationConfiguration.DefaultImpls.getSkipMetadataVersionCheck(this);
    }
    
    public boolean getTypeAliasesAllowed()
    {
      return DeserializationConfiguration.DefaultImpls.getTypeAliasesAllowed(this);
    }
  }
  
  public static final class DefaultImpls
  {
    public static boolean getReleaseCoroutines(DeserializationConfiguration paramDeserializationConfiguration)
    {
      return false;
    }
    
    public static boolean getReportErrorsOnPreReleaseDependencies(DeserializationConfiguration paramDeserializationConfiguration)
    {
      return false;
    }
    
    public static boolean getSkipMetadataVersionCheck(DeserializationConfiguration paramDeserializationConfiguration)
    {
      return false;
    }
    
    public static boolean getTypeAliasesAllowed(DeserializationConfiguration paramDeserializationConfiguration)
    {
      return true;
    }
  }
}

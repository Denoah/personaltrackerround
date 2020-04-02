package kotlin.reflect.jvm.internal.impl.descriptors;

public abstract interface SourceElement
{
  public static final SourceElement NO_SOURCE = new SourceElement()
  {
    public SourceFile getContainingFile()
    {
      SourceFile localSourceFile = SourceFile.NO_SOURCE_FILE;
      if (localSourceFile == null) {
        $$$reportNull$$$0(0);
      }
      return localSourceFile;
    }
    
    public String toString()
    {
      return "NO_SOURCE";
    }
  };
  
  public abstract SourceFile getContainingFile();
}

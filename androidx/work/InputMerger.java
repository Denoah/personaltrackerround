package androidx.work;

import java.util.List;

public abstract class InputMerger
{
  private static final String TAG = Logger.tagWithPrefix("InputMerger");
  
  public InputMerger() {}
  
  public static InputMerger fromClassName(String paramString)
  {
    try
    {
      localObject = (InputMerger)Class.forName(paramString).newInstance();
      return localObject;
    }
    catch (Exception localException)
    {
      Logger localLogger = Logger.get();
      String str = TAG;
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Trouble instantiating + ");
      ((StringBuilder)localObject).append(paramString);
      localLogger.error(str, ((StringBuilder)localObject).toString(), new Throwable[] { localException });
    }
    return null;
  }
  
  public abstract Data merge(List<Data> paramList);
}

package androidx.work;

public abstract class InputMergerFactory
{
  public InputMergerFactory() {}
  
  public static InputMergerFactory getDefaultInputMergerFactory()
  {
    new InputMergerFactory()
    {
      public InputMerger createInputMerger(String paramAnonymousString)
      {
        return null;
      }
    };
  }
  
  public abstract InputMerger createInputMerger(String paramString);
  
  public final InputMerger createInputMergerWithDefaultFallback(String paramString)
  {
    InputMerger localInputMerger1 = createInputMerger(paramString);
    InputMerger localInputMerger2 = localInputMerger1;
    if (localInputMerger1 == null) {
      localInputMerger2 = InputMerger.fromClassName(paramString);
    }
    return localInputMerger2;
  }
}

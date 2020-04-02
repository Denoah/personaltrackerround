package androidx.room;

public class FtsOptions
{
  public static final String TOKENIZER_ICU = "icu";
  public static final String TOKENIZER_PORTER = "porter";
  public static final String TOKENIZER_SIMPLE = "simple";
  public static final String TOKENIZER_UNICODE61 = "unicode61";
  
  private FtsOptions() {}
  
  public static enum MatchInfo
  {
    static
    {
      MatchInfo localMatchInfo = new MatchInfo("FTS4", 1);
      FTS4 = localMatchInfo;
      $VALUES = new MatchInfo[] { FTS3, localMatchInfo };
    }
    
    private MatchInfo() {}
  }
  
  public static enum Order
  {
    static
    {
      Order localOrder = new Order("DESC", 1);
      DESC = localOrder;
      $VALUES = new Order[] { ASC, localOrder };
    }
    
    private Order() {}
  }
}

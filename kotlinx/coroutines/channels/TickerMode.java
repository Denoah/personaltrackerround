package kotlinx.coroutines.channels;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\f\n\002\030\002\n\002\020\020\n\002\b\004\b?\001\030\0002\b\022\004\022\0020\0000\001B\007\b\002?\006\002\020\002j\002\b\003j\002\b\004?\006\005"}, d2={"Lkotlinx/coroutines/channels/TickerMode;", "", "(Ljava/lang/String;I)V", "FIXED_PERIOD", "FIXED_DELAY", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public enum TickerMode
{
  static
  {
    TickerMode localTickerMode1 = new TickerMode("FIXED_PERIOD", 0);
    FIXED_PERIOD = localTickerMode1;
    TickerMode localTickerMode2 = new TickerMode("FIXED_DELAY", 1);
    FIXED_DELAY = localTickerMode2;
    $VALUES = new TickerMode[] { localTickerMode1, localTickerMode2 };
  }
  
  private TickerMode() {}
}

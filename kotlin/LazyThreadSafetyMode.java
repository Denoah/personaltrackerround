package kotlin;

@Metadata(bv={1, 0, 3}, d1={"\000\f\n\002\030\002\n\002\020\020\n\002\b\005\b?\001\030\0002\b\022\004\022\0020\0000\001B\007\b\002?\006\002\020\002j\002\b\003j\002\b\004j\002\b\005?\006\006"}, d2={"Lkotlin/LazyThreadSafetyMode;", "", "(Ljava/lang/String;I)V", "SYNCHRONIZED", "PUBLICATION", "NONE", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public enum LazyThreadSafetyMode
{
  static
  {
    LazyThreadSafetyMode localLazyThreadSafetyMode1 = new LazyThreadSafetyMode("SYNCHRONIZED", 0);
    SYNCHRONIZED = localLazyThreadSafetyMode1;
    LazyThreadSafetyMode localLazyThreadSafetyMode2 = new LazyThreadSafetyMode("PUBLICATION", 1);
    PUBLICATION = localLazyThreadSafetyMode2;
    LazyThreadSafetyMode localLazyThreadSafetyMode3 = new LazyThreadSafetyMode("NONE", 2);
    NONE = localLazyThreadSafetyMode3;
    $VALUES = new LazyThreadSafetyMode[] { localLazyThreadSafetyMode1, localLazyThreadSafetyMode2, localLazyThreadSafetyMode3 };
  }
  
  private LazyThreadSafetyMode() {}
}

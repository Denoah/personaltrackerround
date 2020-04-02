package kotlin;

@Metadata(bv={1, 0, 3}, d1={"\000\f\n\002\030\002\n\002\020\020\n\002\b\005\b?\001\030\0002\b\022\004\022\0020\0000\001B\007\b\002?\006\002\020\002j\002\b\003j\002\b\004j\002\b\005?\006\006"}, d2={"Lkotlin/DeprecationLevel;", "", "(Ljava/lang/String;I)V", "WARNING", "ERROR", "HIDDEN", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public enum DeprecationLevel
{
  static
  {
    DeprecationLevel localDeprecationLevel1 = new DeprecationLevel("WARNING", 0);
    WARNING = localDeprecationLevel1;
    DeprecationLevel localDeprecationLevel2 = new DeprecationLevel("ERROR", 1);
    ERROR = localDeprecationLevel2;
    DeprecationLevel localDeprecationLevel3 = new DeprecationLevel("HIDDEN", 2);
    HIDDEN = localDeprecationLevel3;
    $VALUES = new DeprecationLevel[] { localDeprecationLevel1, localDeprecationLevel2, localDeprecationLevel3 };
  }
  
  private DeprecationLevel() {}
}

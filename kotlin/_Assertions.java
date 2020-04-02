package kotlin;

@Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\013\n\002\b\002\b?\002\030\0002\0020\001B\007\b\002?\006\002\020\002R\026\020\003\032\0020\0048\000X?\004?\006\b\n\000\022\004\b\005\020\002?\006\006"}, d2={"Lkotlin/_Assertions;", "", "()V", "ENABLED", "", "ENABLED$annotations", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class _Assertions
{
  public static final boolean ENABLED;
  public static final _Assertions INSTANCE;
  
  static
  {
    _Assertions local_Assertions = new _Assertions();
    INSTANCE = local_Assertions;
    ENABLED = local_Assertions.getClass().desiredAssertionStatus();
  }
  
  private _Assertions() {}
}

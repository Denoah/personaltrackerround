package dagger.internal;

import dagger.MembersInjector;

public final class MembersInjectors
{
  private MembersInjectors() {}
  
  public static <T> MembersInjector<T> noOp()
  {
    return NoOpMembersInjector.INSTANCE;
  }
  
  private static enum NoOpMembersInjector
    implements MembersInjector<Object>
  {
    static
    {
      NoOpMembersInjector localNoOpMembersInjector = new NoOpMembersInjector("INSTANCE", 0);
      INSTANCE = localNoOpMembersInjector;
      $VALUES = new NoOpMembersInjector[] { localNoOpMembersInjector };
    }
    
    private NoOpMembersInjector() {}
    
    public void injectMembers(Object paramObject)
    {
      Preconditions.checkNotNull(paramObject, "Cannot inject members into a null reference");
    }
  }
}

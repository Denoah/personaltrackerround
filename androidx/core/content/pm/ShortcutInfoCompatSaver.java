package androidx.core.content.pm;

import java.util.ArrayList;
import java.util.List;

public abstract class ShortcutInfoCompatSaver<T>
{
  public ShortcutInfoCompatSaver() {}
  
  public abstract T addShortcuts(List<ShortcutInfoCompat> paramList);
  
  public List<ShortcutInfoCompat> getShortcuts()
    throws Exception
  {
    return new ArrayList();
  }
  
  public abstract T removeAllShortcuts();
  
  public abstract T removeShortcuts(List<String> paramList);
  
  public static class NoopImpl
    extends ShortcutInfoCompatSaver<Void>
  {
    public NoopImpl() {}
    
    public Void addShortcuts(List<ShortcutInfoCompat> paramList)
    {
      return null;
    }
    
    public Void removeAllShortcuts()
    {
      return null;
    }
    
    public Void removeShortcuts(List<String> paramList)
    {
      return null;
    }
  }
}

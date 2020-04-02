package androidx.core.content;

import android.content.SharedPreferences.Editor;

@Deprecated
public final class SharedPreferencesCompat
{
  private SharedPreferencesCompat() {}
  
  @Deprecated
  public static final class EditorCompat
  {
    private static EditorCompat sInstance;
    private final Helper mHelper = new Helper();
    
    private EditorCompat() {}
    
    @Deprecated
    public static EditorCompat getInstance()
    {
      if (sInstance == null) {
        sInstance = new EditorCompat();
      }
      return sInstance;
    }
    
    @Deprecated
    public void apply(SharedPreferences.Editor paramEditor)
    {
      this.mHelper.apply(paramEditor);
    }
    
    private static class Helper
    {
      Helper() {}
      
      public void apply(SharedPreferences.Editor paramEditor)
      {
        try
        {
          paramEditor.apply();
        }
        catch (AbstractMethodError localAbstractMethodError)
        {
          paramEditor.commit();
        }
      }
    }
  }
}

package androidx.room.util;

import android.database.Cursor;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class ViewInfo
{
  public final String name;
  public final String sql;
  
  public ViewInfo(String paramString1, String paramString2)
  {
    this.name = paramString1;
    this.sql = paramString2;
  }
  
  public static ViewInfo read(SupportSQLiteDatabase paramSupportSQLiteDatabase, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("SELECT name, sql FROM sqlite_master WHERE type = 'view' AND name = '");
    localStringBuilder.append(paramString);
    localStringBuilder.append("'");
    paramSupportSQLiteDatabase = paramSupportSQLiteDatabase.query(localStringBuilder.toString());
    try
    {
      if (paramSupportSQLiteDatabase.moveToFirst())
      {
        paramString = new ViewInfo(paramSupportSQLiteDatabase.getString(0), paramSupportSQLiteDatabase.getString(1));
        return paramString;
      }
      paramString = new ViewInfo(paramString, null);
      return paramString;
    }
    finally
    {
      paramSupportSQLiteDatabase.close();
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {
      return true;
    }
    if ((paramObject != null) && (getClass() == paramObject.getClass()))
    {
      paramObject = (ViewInfo)paramObject;
      String str = this.name;
      if (str != null ? str.equals(paramObject.name) : paramObject.name == null)
      {
        str = this.sql;
        if (str != null ? str.equals(paramObject.sql) : paramObject.sql == null) {}
      }
      else
      {
        bool = false;
      }
      return bool;
    }
    return false;
  }
  
  public int hashCode()
  {
    String str = this.name;
    int i = 0;
    int j;
    if (str != null) {
      j = str.hashCode();
    } else {
      j = 0;
    }
    str = this.sql;
    if (str != null) {
      i = str.hashCode();
    }
    return j * 31 + i;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ViewInfo{name='");
    localStringBuilder.append(this.name);
    localStringBuilder.append('\'');
    localStringBuilder.append(", sql='");
    localStringBuilder.append(this.sql);
    localStringBuilder.append('\'');
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
}

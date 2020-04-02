package io.fabric.sdk.android;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import java.io.File;

class FabricContext
  extends ContextWrapper
{
  private final String componentName;
  private final String componentPath;
  
  public FabricContext(Context paramContext, String paramString1, String paramString2)
  {
    super(paramContext);
    this.componentName = paramString1;
    this.componentPath = paramString2;
  }
  
  public File getCacheDir()
  {
    return new File(super.getCacheDir(), this.componentPath);
  }
  
  public File getDatabasePath(String paramString)
  {
    File localFile = new File(super.getDatabasePath(paramString).getParentFile(), this.componentPath);
    localFile.mkdirs();
    return new File(localFile, paramString);
  }
  
  public File getExternalCacheDir()
  {
    return new File(super.getExternalCacheDir(), this.componentPath);
  }
  
  public File getExternalFilesDir(String paramString)
  {
    return new File(super.getExternalFilesDir(paramString), this.componentPath);
  }
  
  public File getFilesDir()
  {
    return new File(super.getFilesDir(), this.componentPath);
  }
  
  public SharedPreferences getSharedPreferences(String paramString, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.componentName);
    localStringBuilder.append(":");
    localStringBuilder.append(paramString);
    return super.getSharedPreferences(localStringBuilder.toString(), paramInt);
  }
  
  public SQLiteDatabase openOrCreateDatabase(String paramString, int paramInt, SQLiteDatabase.CursorFactory paramCursorFactory)
  {
    return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(paramString), paramCursorFactory);
  }
  
  public SQLiteDatabase openOrCreateDatabase(String paramString, int paramInt, SQLiteDatabase.CursorFactory paramCursorFactory, DatabaseErrorHandler paramDatabaseErrorHandler)
  {
    return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(paramString).getPath(), paramCursorFactory, paramDatabaseErrorHandler);
  }
}

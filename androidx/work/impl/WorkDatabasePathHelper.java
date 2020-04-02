package androidx.work.impl;

import android.content.Context;
import android.os.Build.VERSION;
import androidx.work.Logger;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class WorkDatabasePathHelper
{
  private static final String[] DATABASE_EXTRA_FILES = { "-journal", "-shm", "-wal" };
  private static final String TAG = Logger.tagWithPrefix("WrkDbPathHelper");
  private static final String WORK_DATABASE_NAME = "androidx.work.workdb";
  
  private WorkDatabasePathHelper() {}
  
  public static File getDatabasePath(Context paramContext)
  {
    if (Build.VERSION.SDK_INT < 23) {
      return getDefaultDatabasePath(paramContext);
    }
    return getNoBackupPath(paramContext, "androidx.work.workdb");
  }
  
  public static File getDefaultDatabasePath(Context paramContext)
  {
    return paramContext.getDatabasePath("androidx.work.workdb");
  }
  
  private static File getNoBackupPath(Context paramContext, String paramString)
  {
    return new File(paramContext.getNoBackupFilesDir(), paramString);
  }
  
  public static String getWorkDatabaseName()
  {
    return "androidx.work.workdb";
  }
  
  public static void migrateDatabase(Context paramContext)
  {
    Object localObject = getDefaultDatabasePath(paramContext);
    if ((Build.VERSION.SDK_INT >= 23) && (((File)localObject).exists()))
    {
      Logger.get().debug(TAG, "Migrating WorkDatabase to the no-backup directory", new Throwable[0]);
      localObject = migrationPaths(paramContext);
      Iterator localIterator = ((Map)localObject).keySet().iterator();
      while (localIterator.hasNext())
      {
        paramContext = (File)localIterator.next();
        File localFile = (File)((Map)localObject).get(paramContext);
        if ((paramContext.exists()) && (localFile != null))
        {
          if (localFile.exists())
          {
            String str = String.format("Over-writing contents of %s", new Object[] { localFile });
            Logger.get().warning(TAG, str, new Throwable[0]);
          }
          if (paramContext.renameTo(localFile)) {
            paramContext = String.format("Migrated %s to %s", new Object[] { paramContext, localFile });
          } else {
            paramContext = String.format("Renaming %s to %s failed", new Object[] { paramContext, localFile });
          }
          Logger.get().debug(TAG, paramContext, new Throwable[0]);
        }
      }
    }
  }
  
  public static Map<File, File> migrationPaths(Context paramContext)
  {
    HashMap localHashMap = new HashMap();
    if (Build.VERSION.SDK_INT >= 23)
    {
      File localFile1 = getDefaultDatabasePath(paramContext);
      File localFile2 = getDatabasePath(paramContext);
      localHashMap.put(localFile1, localFile2);
      for (String str : DATABASE_EXTRA_FILES)
      {
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append(localFile1.getPath());
        ((StringBuilder)localObject).append(str);
        localObject = new File(((StringBuilder)localObject).toString());
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(localFile2.getPath());
        localStringBuilder.append(str);
        localHashMap.put(localObject, new File(localStringBuilder.toString()));
      }
    }
    return localHashMap;
  }
}

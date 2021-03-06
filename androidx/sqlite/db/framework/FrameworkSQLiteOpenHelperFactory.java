package androidx.sqlite.db.framework;

import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Factory;

public final class FrameworkSQLiteOpenHelperFactory
  implements SupportSQLiteOpenHelper.Factory
{
  public FrameworkSQLiteOpenHelperFactory() {}
  
  public SupportSQLiteOpenHelper create(SupportSQLiteOpenHelper.Configuration paramConfiguration)
  {
    return new FrameworkSQLiteOpenHelper(paramConfiguration.context, paramConfiguration.name, paramConfiguration.callback, paramConfiguration.useNoBackupDirectory);
  }
}

package androidx.work.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.work.impl.utils.IdGenerator;
import androidx.work.impl.utils.PreferenceUtils;

public class WorkDatabaseMigrations
{
  private static final String CREATE_INDEX_PERIOD_START_TIME = "CREATE INDEX IF NOT EXISTS `index_WorkSpec_period_start_time` ON `workspec` (`period_start_time`)";
  private static final String CREATE_PREFERENCE = "CREATE TABLE IF NOT EXISTS `Preference` (`key` TEXT NOT NULL, `long_value` INTEGER, PRIMARY KEY(`key`))";
  private static final String CREATE_RUN_IN_FOREGROUND = "ALTER TABLE workspec ADD COLUMN `run_in_foreground` INTEGER NOT NULL DEFAULT 0";
  private static final String CREATE_SYSTEM_ID_INFO = "CREATE TABLE IF NOT EXISTS `SystemIdInfo` (`work_spec_id` TEXT NOT NULL, `system_id` INTEGER NOT NULL, PRIMARY KEY(`work_spec_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )";
  private static final String CREATE_WORK_PROGRESS = "CREATE TABLE IF NOT EXISTS `WorkProgress` (`work_spec_id` TEXT NOT NULL, `progress` BLOB NOT NULL, PRIMARY KEY(`work_spec_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )";
  public static final String INSERT_PREFERENCE = "INSERT OR REPLACE INTO `Preference` (`key`, `long_value`) VALUES (@key, @long_value)";
  private static final String MIGRATE_ALARM_INFO_TO_SYSTEM_ID_INFO = "INSERT INTO SystemIdInfo(work_spec_id, system_id) SELECT work_spec_id, alarm_id AS system_id FROM alarmInfo";
  public static Migration MIGRATION_1_2 = new Migration(1, 2)
  {
    public void migrate(SupportSQLiteDatabase paramAnonymousSupportSQLiteDatabase)
    {
      paramAnonymousSupportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `SystemIdInfo` (`work_spec_id` TEXT NOT NULL, `system_id` INTEGER NOT NULL, PRIMARY KEY(`work_spec_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
      paramAnonymousSupportSQLiteDatabase.execSQL("INSERT INTO SystemIdInfo(work_spec_id, system_id) SELECT work_spec_id, alarm_id AS system_id FROM alarmInfo");
      paramAnonymousSupportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS alarmInfo");
      paramAnonymousSupportSQLiteDatabase.execSQL("INSERT OR IGNORE INTO worktag(tag, work_spec_id) SELECT worker_class_name AS tag, id AS work_spec_id FROM workspec");
    }
  };
  public static Migration MIGRATION_3_4 = new Migration(3, 4)
  {
    public void migrate(SupportSQLiteDatabase paramAnonymousSupportSQLiteDatabase)
    {
      if (Build.VERSION.SDK_INT >= 23) {
        paramAnonymousSupportSQLiteDatabase.execSQL("UPDATE workspec SET schedule_requested_at=0 WHERE state NOT IN (2, 3, 5) AND schedule_requested_at=-1 AND interval_duration<>0");
      }
    }
  };
  public static Migration MIGRATION_4_5 = new Migration(4, 5)
  {
    public void migrate(SupportSQLiteDatabase paramAnonymousSupportSQLiteDatabase)
    {
      paramAnonymousSupportSQLiteDatabase.execSQL("ALTER TABLE workspec ADD COLUMN `trigger_content_update_delay` INTEGER NOT NULL DEFAULT -1");
      paramAnonymousSupportSQLiteDatabase.execSQL("ALTER TABLE workspec ADD COLUMN `trigger_max_content_delay` INTEGER NOT NULL DEFAULT -1");
    }
  };
  public static Migration MIGRATION_6_7 = new Migration(6, 7)
  {
    public void migrate(SupportSQLiteDatabase paramAnonymousSupportSQLiteDatabase)
    {
      paramAnonymousSupportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `WorkProgress` (`work_spec_id` TEXT NOT NULL, `progress` BLOB NOT NULL, PRIMARY KEY(`work_spec_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
    }
  };
  public static Migration MIGRATION_7_8 = new Migration(7, 8)
  {
    public void migrate(SupportSQLiteDatabase paramAnonymousSupportSQLiteDatabase)
    {
      paramAnonymousSupportSQLiteDatabase.execSQL("CREATE INDEX IF NOT EXISTS `index_WorkSpec_period_start_time` ON `workspec` (`period_start_time`)");
    }
  };
  public static Migration MIGRATION_8_9 = new Migration(8, 9)
  {
    public void migrate(SupportSQLiteDatabase paramAnonymousSupportSQLiteDatabase)
    {
      paramAnonymousSupportSQLiteDatabase.execSQL("ALTER TABLE workspec ADD COLUMN `run_in_foreground` INTEGER NOT NULL DEFAULT 0");
    }
  };
  private static final String PERIODIC_WORK_SET_SCHEDULE_REQUESTED_AT = "UPDATE workspec SET schedule_requested_at=0 WHERE state NOT IN (2, 3, 5) AND schedule_requested_at=-1 AND interval_duration<>0";
  private static final String REMOVE_ALARM_INFO = "DROP TABLE IF EXISTS alarmInfo";
  public static final int VERSION_1 = 1;
  public static final int VERSION_10 = 10;
  public static final int VERSION_2 = 2;
  public static final int VERSION_3 = 3;
  public static final int VERSION_4 = 4;
  public static final int VERSION_5 = 5;
  public static final int VERSION_6 = 6;
  public static final int VERSION_7 = 7;
  public static final int VERSION_8 = 8;
  public static final int VERSION_9 = 9;
  private static final String WORKSPEC_ADD_TRIGGER_MAX_CONTENT_DELAY = "ALTER TABLE workspec ADD COLUMN `trigger_max_content_delay` INTEGER NOT NULL DEFAULT -1";
  private static final String WORKSPEC_ADD_TRIGGER_UPDATE_DELAY = "ALTER TABLE workspec ADD COLUMN `trigger_content_update_delay` INTEGER NOT NULL DEFAULT -1";
  
  private WorkDatabaseMigrations() {}
  
  public static class RescheduleMigration
    extends Migration
  {
    final Context mContext;
    
    public RescheduleMigration(Context paramContext, int paramInt1, int paramInt2)
    {
      super(paramInt2);
      this.mContext = paramContext;
    }
    
    public void migrate(SupportSQLiteDatabase paramSupportSQLiteDatabase)
    {
      if (this.endVersion >= 10) {
        paramSupportSQLiteDatabase.execSQL("INSERT OR REPLACE INTO `Preference` (`key`, `long_value`) VALUES (@key, @long_value)", new Object[] { "reschedule_needed", Integer.valueOf(1) });
      } else {
        this.mContext.getSharedPreferences("androidx.work.util.preferences", 0).edit().putBoolean("reschedule_needed", true).apply();
      }
    }
  }
  
  public static class WorkMigration9To10
    extends Migration
  {
    final Context mContext;
    
    public WorkMigration9To10(Context paramContext)
    {
      super(10);
      this.mContext = paramContext;
    }
    
    public void migrate(SupportSQLiteDatabase paramSupportSQLiteDatabase)
    {
      paramSupportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `Preference` (`key` TEXT NOT NULL, `long_value` INTEGER, PRIMARY KEY(`key`))");
      PreferenceUtils.migrateLegacyPreferences(this.mContext, paramSupportSQLiteDatabase);
      IdGenerator.migrateLegacyIdGenerator(this.mContext, paramSupportSQLiteDatabase);
    }
  }
}

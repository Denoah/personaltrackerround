package com.askgps.personaltrackercore.database;

import android.database.Cursor;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase.Callback;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration.Builder;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Factory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public final class DatabaseHelper_Impl
  extends DatabaseHelper
{
  private volatile LocationDao _locationDao;
  private volatile PhoneBookDao _phoneBookDao;
  private volatile SettingsDao _settingsDao;
  private volatile StepSensorDao _stepSensorDao;
  
  public DatabaseHelper_Impl() {}
  
  public void clearAllTables()
  {
    super.assertNotMainThread();
    SupportSQLiteDatabase localSupportSQLiteDatabase = super.getOpenHelper().getWritableDatabase();
    try
    {
      super.beginTransaction();
      localSupportSQLiteDatabase.execSQL("DELETE FROM `Location`");
      localSupportSQLiteDatabase.execSQL("DELETE FROM `StepSensor`");
      localSupportSQLiteDatabase.execSQL("DELETE FROM `Settings`");
      localSupportSQLiteDatabase.execSQL("DELETE FROM `PhoneNumber`");
      super.setTransactionSuccessful();
      return;
    }
    finally
    {
      super.endTransaction();
      localSupportSQLiteDatabase.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!localSupportSQLiteDatabase.inTransaction()) {
        localSupportSQLiteDatabase.execSQL("VACUUM");
      }
    }
  }
  
  protected InvalidationTracker createInvalidationTracker()
  {
    return new InvalidationTracker(this, new HashMap(0), new HashMap(0), new String[] { "Location", "StepSensor", "Settings", "PhoneNumber" });
  }
  
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration paramDatabaseConfiguration)
  {
    Object localObject = new RoomOpenHelper(paramDatabaseConfiguration, new RoomOpenHelper.Delegate(9)
    {
      public void createAllTables(SupportSQLiteDatabase paramAnonymousSupportSQLiteDatabase)
      {
        paramAnonymousSupportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `Location` (`imei` TEXT NOT NULL, `datetime` INTEGER NOT NULL, `locationDatetime` INTEGER, `lat` REAL, `lon` REAL, `altitude` REAL, `accuracy` INTEGER, `bearing` INTEGER, `speed` REAL, `provider` TEXT, `stepCount` INTEGER, `isLeaveHand` INTEGER, `batteryLevel` INTEGER, `isFall` INTEGER, `powerOn` INTEGER, `sos` INTEGER, `version` INTEGER, `workShift` INTEGER, `isValid` INTEGER, `indoorNavigation` TEXT, PRIMARY KEY(`datetime`))");
        paramAnonymousSupportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `StepSensor` (`lastSensorValue` INTEGER NOT NULL, `stepsTimeStamp` INTEGER NOT NULL, `stepsAtStartDay` INTEGER NOT NULL, `stepCount` INTEGER NOT NULL, `id` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        paramAnonymousSupportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `Settings` (`workIsStart` INTEGER NOT NULL, `gaskarAddress` TEXT NOT NULL, `locationInterval` INTEGER NOT NULL, `sendTelemetryInterval` INTEGER NOT NULL, `removalFromHandJobInterval` INTEGER NOT NULL, `id` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        paramAnonymousSupportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `PhoneNumber` (`name` TEXT NOT NULL, `number` TEXT NOT NULL, PRIMARY KEY(`number`))");
        paramAnonymousSupportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        paramAnonymousSupportSQLiteDatabase.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'a8a3ecc5e4c839f2e534595364f51c26')");
      }
      
      public void dropAllTables(SupportSQLiteDatabase paramAnonymousSupportSQLiteDatabase)
      {
        paramAnonymousSupportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `Location`");
        paramAnonymousSupportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `StepSensor`");
        paramAnonymousSupportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `Settings`");
        paramAnonymousSupportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `PhoneNumber`");
        if (DatabaseHelper_Impl.this.mCallbacks != null)
        {
          int i = 0;
          int j = DatabaseHelper_Impl.this.mCallbacks.size();
          while (i < j)
          {
            ((RoomDatabase.Callback)DatabaseHelper_Impl.this.mCallbacks.get(i)).onDestructiveMigration(paramAnonymousSupportSQLiteDatabase);
            i++;
          }
        }
      }
      
      protected void onCreate(SupportSQLiteDatabase paramAnonymousSupportSQLiteDatabase)
      {
        if (DatabaseHelper_Impl.this.mCallbacks != null)
        {
          int i = 0;
          int j = DatabaseHelper_Impl.this.mCallbacks.size();
          while (i < j)
          {
            ((RoomDatabase.Callback)DatabaseHelper_Impl.this.mCallbacks.get(i)).onCreate(paramAnonymousSupportSQLiteDatabase);
            i++;
          }
        }
      }
      
      public void onOpen(SupportSQLiteDatabase paramAnonymousSupportSQLiteDatabase)
      {
        DatabaseHelper_Impl.access$602(DatabaseHelper_Impl.this, paramAnonymousSupportSQLiteDatabase);
        DatabaseHelper_Impl.this.internalInitInvalidationTracker(paramAnonymousSupportSQLiteDatabase);
        if (DatabaseHelper_Impl.this.mCallbacks != null)
        {
          int i = 0;
          int j = DatabaseHelper_Impl.this.mCallbacks.size();
          while (i < j)
          {
            ((RoomDatabase.Callback)DatabaseHelper_Impl.this.mCallbacks.get(i)).onOpen(paramAnonymousSupportSQLiteDatabase);
            i++;
          }
        }
      }
      
      public void onPostMigrate(SupportSQLiteDatabase paramAnonymousSupportSQLiteDatabase) {}
      
      public void onPreMigrate(SupportSQLiteDatabase paramAnonymousSupportSQLiteDatabase)
      {
        DBUtil.dropFtsSyncTriggers(paramAnonymousSupportSQLiteDatabase);
      }
      
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase paramAnonymousSupportSQLiteDatabase)
      {
        Object localObject1 = new HashMap(20);
        ((HashMap)localObject1).put("imei", new TableInfo.Column("imei", "TEXT", true, 0, null, 1));
        ((HashMap)localObject1).put("datetime", new TableInfo.Column("datetime", "INTEGER", true, 1, null, 1));
        ((HashMap)localObject1).put("locationDatetime", new TableInfo.Column("locationDatetime", "INTEGER", false, 0, null, 1));
        ((HashMap)localObject1).put("lat", new TableInfo.Column("lat", "REAL", false, 0, null, 1));
        ((HashMap)localObject1).put("lon", new TableInfo.Column("lon", "REAL", false, 0, null, 1));
        ((HashMap)localObject1).put("altitude", new TableInfo.Column("altitude", "REAL", false, 0, null, 1));
        ((HashMap)localObject1).put("accuracy", new TableInfo.Column("accuracy", "INTEGER", false, 0, null, 1));
        ((HashMap)localObject1).put("bearing", new TableInfo.Column("bearing", "INTEGER", false, 0, null, 1));
        ((HashMap)localObject1).put("speed", new TableInfo.Column("speed", "REAL", false, 0, null, 1));
        ((HashMap)localObject1).put("provider", new TableInfo.Column("provider", "TEXT", false, 0, null, 1));
        ((HashMap)localObject1).put("stepCount", new TableInfo.Column("stepCount", "INTEGER", false, 0, null, 1));
        ((HashMap)localObject1).put("isLeaveHand", new TableInfo.Column("isLeaveHand", "INTEGER", false, 0, null, 1));
        ((HashMap)localObject1).put("batteryLevel", new TableInfo.Column("batteryLevel", "INTEGER", false, 0, null, 1));
        ((HashMap)localObject1).put("isFall", new TableInfo.Column("isFall", "INTEGER", false, 0, null, 1));
        ((HashMap)localObject1).put("powerOn", new TableInfo.Column("powerOn", "INTEGER", false, 0, null, 1));
        ((HashMap)localObject1).put("sos", new TableInfo.Column("sos", "INTEGER", false, 0, null, 1));
        ((HashMap)localObject1).put("version", new TableInfo.Column("version", "INTEGER", false, 0, null, 1));
        ((HashMap)localObject1).put("workShift", new TableInfo.Column("workShift", "INTEGER", false, 0, null, 1));
        ((HashMap)localObject1).put("isValid", new TableInfo.Column("isValid", "INTEGER", false, 0, null, 1));
        ((HashMap)localObject1).put("indoorNavigation", new TableInfo.Column("indoorNavigation", "TEXT", false, 0, null, 1));
        localObject1 = new TableInfo("Location", (Map)localObject1, new HashSet(0), new HashSet(0));
        Object localObject2 = TableInfo.read(paramAnonymousSupportSQLiteDatabase, "Location");
        if (!((TableInfo)localObject1).equals(localObject2))
        {
          paramAnonymousSupportSQLiteDatabase = new StringBuilder();
          paramAnonymousSupportSQLiteDatabase.append("Location(com.askgps.personaltrackercore.database.model.Location).\n Expected:\n");
          paramAnonymousSupportSQLiteDatabase.append(localObject1);
          paramAnonymousSupportSQLiteDatabase.append("\n Found:\n");
          paramAnonymousSupportSQLiteDatabase.append(localObject2);
          return new RoomOpenHelper.ValidationResult(false, paramAnonymousSupportSQLiteDatabase.toString());
        }
        localObject1 = new HashMap(5);
        ((HashMap)localObject1).put("lastSensorValue", new TableInfo.Column("lastSensorValue", "INTEGER", true, 0, null, 1));
        ((HashMap)localObject1).put("stepsTimeStamp", new TableInfo.Column("stepsTimeStamp", "INTEGER", true, 0, null, 1));
        ((HashMap)localObject1).put("stepsAtStartDay", new TableInfo.Column("stepsAtStartDay", "INTEGER", true, 0, null, 1));
        ((HashMap)localObject1).put("stepCount", new TableInfo.Column("stepCount", "INTEGER", true, 0, null, 1));
        ((HashMap)localObject1).put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, 1));
        localObject1 = new TableInfo("StepSensor", (Map)localObject1, new HashSet(0), new HashSet(0));
        localObject2 = TableInfo.read(paramAnonymousSupportSQLiteDatabase, "StepSensor");
        if (!((TableInfo)localObject1).equals(localObject2))
        {
          paramAnonymousSupportSQLiteDatabase = new StringBuilder();
          paramAnonymousSupportSQLiteDatabase.append("StepSensor(com.askgps.personaltrackercore.database.model.StepSensor).\n Expected:\n");
          paramAnonymousSupportSQLiteDatabase.append(localObject1);
          paramAnonymousSupportSQLiteDatabase.append("\n Found:\n");
          paramAnonymousSupportSQLiteDatabase.append(localObject2);
          return new RoomOpenHelper.ValidationResult(false, paramAnonymousSupportSQLiteDatabase.toString());
        }
        localObject1 = new HashMap(6);
        ((HashMap)localObject1).put("workIsStart", new TableInfo.Column("workIsStart", "INTEGER", true, 0, null, 1));
        ((HashMap)localObject1).put("gaskarAddress", new TableInfo.Column("gaskarAddress", "TEXT", true, 0, null, 1));
        ((HashMap)localObject1).put("locationInterval", new TableInfo.Column("locationInterval", "INTEGER", true, 0, null, 1));
        ((HashMap)localObject1).put("sendTelemetryInterval", new TableInfo.Column("sendTelemetryInterval", "INTEGER", true, 0, null, 1));
        ((HashMap)localObject1).put("removalFromHandJobInterval", new TableInfo.Column("removalFromHandJobInterval", "INTEGER", true, 0, null, 1));
        ((HashMap)localObject1).put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, 1));
        localObject1 = new TableInfo("Settings", (Map)localObject1, new HashSet(0), new HashSet(0));
        localObject2 = TableInfo.read(paramAnonymousSupportSQLiteDatabase, "Settings");
        if (!((TableInfo)localObject1).equals(localObject2))
        {
          paramAnonymousSupportSQLiteDatabase = new StringBuilder();
          paramAnonymousSupportSQLiteDatabase.append("Settings(com.askgps.personaltrackercore.database.model.Settings).\n Expected:\n");
          paramAnonymousSupportSQLiteDatabase.append(localObject1);
          paramAnonymousSupportSQLiteDatabase.append("\n Found:\n");
          paramAnonymousSupportSQLiteDatabase.append(localObject2);
          return new RoomOpenHelper.ValidationResult(false, paramAnonymousSupportSQLiteDatabase.toString());
        }
        localObject1 = new HashMap(2);
        ((HashMap)localObject1).put("name", new TableInfo.Column("name", "TEXT", true, 0, null, 1));
        ((HashMap)localObject1).put("number", new TableInfo.Column("number", "TEXT", true, 1, null, 1));
        localObject1 = new TableInfo("PhoneNumber", (Map)localObject1, new HashSet(0), new HashSet(0));
        paramAnonymousSupportSQLiteDatabase = TableInfo.read(paramAnonymousSupportSQLiteDatabase, "PhoneNumber");
        if (!((TableInfo)localObject1).equals(paramAnonymousSupportSQLiteDatabase))
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("PhoneNumber(com.askgps.personaltrackercore.database.model.PhoneNumber).\n Expected:\n");
          ((StringBuilder)localObject2).append(localObject1);
          ((StringBuilder)localObject2).append("\n Found:\n");
          ((StringBuilder)localObject2).append(paramAnonymousSupportSQLiteDatabase);
          return new RoomOpenHelper.ValidationResult(false, ((StringBuilder)localObject2).toString());
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "a8a3ecc5e4c839f2e534595364f51c26", "93fa3e983163700f1032b3945bd8bd81");
    localObject = SupportSQLiteOpenHelper.Configuration.builder(paramDatabaseConfiguration.context).name(paramDatabaseConfiguration.name).callback((SupportSQLiteOpenHelper.Callback)localObject).build();
    return paramDatabaseConfiguration.sqliteOpenHelperFactory.create((SupportSQLiteOpenHelper.Configuration)localObject);
  }
  
  public LocationDao locationDao()
  {
    if (this._locationDao != null) {
      return this._locationDao;
    }
    try
    {
      if (this._locationDao == null)
      {
        localObject1 = new com/askgps/personaltrackercore/database/LocationDao_Impl;
        ((LocationDao_Impl)localObject1).<init>(this);
        this._locationDao = ((LocationDao)localObject1);
      }
      Object localObject1 = this._locationDao;
      return localObject1;
    }
    finally {}
  }
  
  public PhoneBookDao phoneBookDao()
  {
    if (this._phoneBookDao != null) {
      return this._phoneBookDao;
    }
    try
    {
      if (this._phoneBookDao == null)
      {
        localObject1 = new com/askgps/personaltrackercore/database/PhoneBookDao_Impl;
        ((PhoneBookDao_Impl)localObject1).<init>(this);
        this._phoneBookDao = ((PhoneBookDao)localObject1);
      }
      Object localObject1 = this._phoneBookDao;
      return localObject1;
    }
    finally {}
  }
  
  public SettingsDao settingsDao()
  {
    if (this._settingsDao != null) {
      return this._settingsDao;
    }
    try
    {
      if (this._settingsDao == null)
      {
        localObject1 = new com/askgps/personaltrackercore/database/SettingsDao_Impl;
        ((SettingsDao_Impl)localObject1).<init>(this);
        this._settingsDao = ((SettingsDao)localObject1);
      }
      Object localObject1 = this._settingsDao;
      return localObject1;
    }
    finally {}
  }
  
  public StepSensorDao stepSensorDao()
  {
    if (this._stepSensorDao != null) {
      return this._stepSensorDao;
    }
    try
    {
      if (this._stepSensorDao == null)
      {
        localObject1 = new com/askgps/personaltrackercore/database/StepSensorDao_Impl;
        ((StepSensorDao_Impl)localObject1).<init>(this);
        this._stepSensorDao = ((StepSensorDao)localObject1);
      }
      Object localObject1 = this._stepSensorDao;
      return localObject1;
    }
    finally {}
  }
}

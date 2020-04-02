package com.askgps.personaltrackercore.database;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.askgps.personaltrackercore.database.model.Settings;
import java.util.concurrent.Callable;

public final class SettingsDao_Impl
  implements SettingsDao
{
  private final RoomDatabase __db;
  private final EntityInsertionAdapter<Settings> __insertionAdapterOfSettings;
  private final SharedSQLiteStatement __preparedStmtOfSetAddress;
  private final SharedSQLiteStatement __preparedStmtOfSetLocationInterval;
  private final SharedSQLiteStatement __preparedStmtOfSetRemovalFromHandInterval;
  private final SharedSQLiteStatement __preparedStmtOfSetSendTelemetryInterval;
  private final SharedSQLiteStatement __preparedStmtOfSetWorkIsStart;
  
  public SettingsDao_Impl(RoomDatabase paramRoomDatabase)
  {
    this.__db = paramRoomDatabase;
    this.__insertionAdapterOfSettings = new EntityInsertionAdapter(paramRoomDatabase)
    {
      public void bind(SupportSQLiteStatement paramAnonymousSupportSQLiteStatement, Settings paramAnonymousSettings)
      {
        paramAnonymousSupportSQLiteStatement.bindLong(1, paramAnonymousSettings.getWorkIsStart());
        if (paramAnonymousSettings.getGaskarAddress() == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(2);
        } else {
          paramAnonymousSupportSQLiteStatement.bindString(2, paramAnonymousSettings.getGaskarAddress());
        }
        paramAnonymousSupportSQLiteStatement.bindLong(3, paramAnonymousSettings.getLocationInterval());
        paramAnonymousSupportSQLiteStatement.bindLong(4, paramAnonymousSettings.getSendTelemetryInterval());
        paramAnonymousSupportSQLiteStatement.bindLong(5, paramAnonymousSettings.getRemovalFromHandJobInterval());
        paramAnonymousSupportSQLiteStatement.bindLong(6, paramAnonymousSettings.getId());
      }
      
      public String createQuery()
      {
        return "INSERT OR IGNORE INTO `Settings` (`workIsStart`,`gaskarAddress`,`locationInterval`,`sendTelemetryInterval`,`removalFromHandJobInterval`,`id`) VALUES (?,?,?,?,?,?)";
      }
    };
    this.__preparedStmtOfSetWorkIsStart = new SharedSQLiteStatement(paramRoomDatabase)
    {
      public String createQuery()
      {
        return "UPDATE Settings SET workIsStart=? WHERE id = 1";
      }
    };
    this.__preparedStmtOfSetAddress = new SharedSQLiteStatement(paramRoomDatabase)
    {
      public String createQuery()
      {
        return "UPDATE Settings SET gaskarAddress=? WHERE id = 1";
      }
    };
    this.__preparedStmtOfSetLocationInterval = new SharedSQLiteStatement(paramRoomDatabase)
    {
      public String createQuery()
      {
        return "UPDATE Settings SET locationInterval=? WHERE id = 1";
      }
    };
    this.__preparedStmtOfSetSendTelemetryInterval = new SharedSQLiteStatement(paramRoomDatabase)
    {
      public String createQuery()
      {
        return "UPDATE Settings SET sendTelemetryInterval=? WHERE id = 1";
      }
    };
    this.__preparedStmtOfSetRemovalFromHandInterval = new SharedSQLiteStatement(paramRoomDatabase)
    {
      public String createQuery()
      {
        return "UPDATE Settings SET removalFromHandJobInterval=? WHERE id = 1";
      }
    };
  }
  
  public void createSettings(Settings paramSettings)
  {
    this.__db.assertNotSuspendingTransaction();
    this.__db.beginTransaction();
    try
    {
      this.__insertionAdapterOfSettings.insert(paramSettings);
      this.__db.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.__db.endTransaction();
    }
  }
  
  public LiveData<Settings> getSettings()
  {
    Object localObject = RoomSQLiteQuery.acquire("SELECT * FROM Settings WHERE id = 1", 0);
    InvalidationTracker localInvalidationTracker = this.__db.getInvalidationTracker();
    localObject = new Callable()
    {
      public Settings call()
        throws Exception
      {
        RoomDatabase localRoomDatabase = SettingsDao_Impl.this.__db;
        Object localObject1 = this.val$_statement;
        Settings localSettings = null;
        boolean bool = false;
        localObject1 = DBUtil.query(localRoomDatabase, (SupportSQLiteQuery)localObject1, false, null);
        try
        {
          int i = CursorUtil.getColumnIndexOrThrow((Cursor)localObject1, "workIsStart");
          int j = CursorUtil.getColumnIndexOrThrow((Cursor)localObject1, "gaskarAddress");
          int k = CursorUtil.getColumnIndexOrThrow((Cursor)localObject1, "locationInterval");
          int m = CursorUtil.getColumnIndexOrThrow((Cursor)localObject1, "sendTelemetryInterval");
          int n = CursorUtil.getColumnIndexOrThrow((Cursor)localObject1, "removalFromHandJobInterval");
          int i1 = CursorUtil.getColumnIndexOrThrow((Cursor)localObject1, "id");
          if (((Cursor)localObject1).moveToFirst())
          {
            if (((Cursor)localObject1).getInt(i) != 0) {
              bool = true;
            }
            localSettings = new Settings(bool, ((Cursor)localObject1).getString(j), ((Cursor)localObject1).getLong(k), ((Cursor)localObject1).getLong(m), ((Cursor)localObject1).getLong(n), ((Cursor)localObject1).getInt(i1));
          }
          return localSettings;
        }
        finally
        {
          ((Cursor)localObject1).close();
        }
      }
      
      protected void finalize()
      {
        this.val$_statement.release();
      }
    };
    return localInvalidationTracker.createLiveData(new String[] { "Settings" }, false, (Callable)localObject);
  }
  
  public void setAddress(String paramString)
  {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement localSupportSQLiteStatement = this.__preparedStmtOfSetAddress.acquire();
    if (paramString == null) {
      localSupportSQLiteStatement.bindNull(1);
    } else {
      localSupportSQLiteStatement.bindString(1, paramString);
    }
    this.__db.beginTransaction();
    try
    {
      localSupportSQLiteStatement.executeUpdateDelete();
      this.__db.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.__db.endTransaction();
      this.__preparedStmtOfSetAddress.release(localSupportSQLiteStatement);
    }
  }
  
  public void setLocationInterval(long paramLong)
  {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement localSupportSQLiteStatement = this.__preparedStmtOfSetLocationInterval.acquire();
    localSupportSQLiteStatement.bindLong(1, paramLong);
    this.__db.beginTransaction();
    try
    {
      localSupportSQLiteStatement.executeUpdateDelete();
      this.__db.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.__db.endTransaction();
      this.__preparedStmtOfSetLocationInterval.release(localSupportSQLiteStatement);
    }
  }
  
  public void setRemovalFromHandInterval(long paramLong)
  {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement localSupportSQLiteStatement = this.__preparedStmtOfSetRemovalFromHandInterval.acquire();
    localSupportSQLiteStatement.bindLong(1, paramLong);
    this.__db.beginTransaction();
    try
    {
      localSupportSQLiteStatement.executeUpdateDelete();
      this.__db.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.__db.endTransaction();
      this.__preparedStmtOfSetRemovalFromHandInterval.release(localSupportSQLiteStatement);
    }
  }
  
  public void setSendTelemetryInterval(long paramLong)
  {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement localSupportSQLiteStatement = this.__preparedStmtOfSetSendTelemetryInterval.acquire();
    localSupportSQLiteStatement.bindLong(1, paramLong);
    this.__db.beginTransaction();
    try
    {
      localSupportSQLiteStatement.executeUpdateDelete();
      this.__db.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.__db.endTransaction();
      this.__preparedStmtOfSetSendTelemetryInterval.release(localSupportSQLiteStatement);
    }
  }
  
  public void setWorkIsStart(boolean paramBoolean)
  {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement localSupportSQLiteStatement = this.__preparedStmtOfSetWorkIsStart.acquire();
    localSupportSQLiteStatement.bindLong(1, paramBoolean);
    this.__db.beginTransaction();
    try
    {
      localSupportSQLiteStatement.executeUpdateDelete();
      this.__db.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.__db.endTransaction();
      this.__preparedStmtOfSetWorkIsStart.release(localSupportSQLiteStatement);
    }
  }
  
  public void updateSettings(String paramString, Long paramLong1, Long paramLong2, Long paramLong3)
  {
    this.__db.beginTransaction();
    try
    {
      SettingsDao.DefaultImpls.updateSettings(this, paramString, paramLong1, paramLong2, paramLong3);
      this.__db.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.__db.endTransaction();
    }
  }
}

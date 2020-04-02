package androidx.work.impl.model;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;

public final class SystemIdInfoDao_Impl
  implements SystemIdInfoDao
{
  private final RoomDatabase __db;
  private final EntityInsertionAdapter<SystemIdInfo> __insertionAdapterOfSystemIdInfo;
  private final SharedSQLiteStatement __preparedStmtOfRemoveSystemIdInfo;
  
  public SystemIdInfoDao_Impl(RoomDatabase paramRoomDatabase)
  {
    this.__db = paramRoomDatabase;
    this.__insertionAdapterOfSystemIdInfo = new EntityInsertionAdapter(paramRoomDatabase)
    {
      public void bind(SupportSQLiteStatement paramAnonymousSupportSQLiteStatement, SystemIdInfo paramAnonymousSystemIdInfo)
      {
        if (paramAnonymousSystemIdInfo.workSpecId == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(1);
        } else {
          paramAnonymousSupportSQLiteStatement.bindString(1, paramAnonymousSystemIdInfo.workSpecId);
        }
        paramAnonymousSupportSQLiteStatement.bindLong(2, paramAnonymousSystemIdInfo.systemId);
      }
      
      public String createQuery()
      {
        return "INSERT OR REPLACE INTO `SystemIdInfo` (`work_spec_id`,`system_id`) VALUES (?,?)";
      }
    };
    this.__preparedStmtOfRemoveSystemIdInfo = new SharedSQLiteStatement(paramRoomDatabase)
    {
      public String createQuery()
      {
        return "DELETE FROM SystemIdInfo where work_spec_id=?";
      }
    };
  }
  
  public SystemIdInfo getSystemIdInfo(String paramString)
  {
    RoomSQLiteQuery localRoomSQLiteQuery = RoomSQLiteQuery.acquire("SELECT `SystemIdInfo`.`work_spec_id` AS `work_spec_id`, `SystemIdInfo`.`system_id` AS `system_id` FROM SystemIdInfo WHERE work_spec_id=?", 1);
    if (paramString == null) {
      localRoomSQLiteQuery.bindNull(1);
    } else {
      localRoomSQLiteQuery.bindString(1, paramString);
    }
    this.__db.assertNotSuspendingTransaction();
    Object localObject = this.__db;
    paramString = null;
    localObject = DBUtil.query((RoomDatabase)localObject, localRoomSQLiteQuery, false, null);
    try
    {
      int i = CursorUtil.getColumnIndexOrThrow((Cursor)localObject, "work_spec_id");
      int j = CursorUtil.getColumnIndexOrThrow((Cursor)localObject, "system_id");
      if (((Cursor)localObject).moveToFirst()) {
        paramString = new SystemIdInfo(((Cursor)localObject).getString(i), ((Cursor)localObject).getInt(j));
      }
      return paramString;
    }
    finally
    {
      ((Cursor)localObject).close();
      localRoomSQLiteQuery.release();
    }
  }
  
  public void insertSystemIdInfo(SystemIdInfo paramSystemIdInfo)
  {
    this.__db.assertNotSuspendingTransaction();
    this.__db.beginTransaction();
    try
    {
      this.__insertionAdapterOfSystemIdInfo.insert(paramSystemIdInfo);
      this.__db.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.__db.endTransaction();
    }
  }
  
  public void removeSystemIdInfo(String paramString)
  {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement localSupportSQLiteStatement = this.__preparedStmtOfRemoveSystemIdInfo.acquire();
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
      this.__preparedStmtOfRemoveSystemIdInfo.release(localSupportSQLiteStatement);
    }
  }
}

package androidx.work.impl.model;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.ArrayList;
import java.util.List;

public final class WorkNameDao_Impl
  implements WorkNameDao
{
  private final RoomDatabase __db;
  private final EntityInsertionAdapter<WorkName> __insertionAdapterOfWorkName;
  
  public WorkNameDao_Impl(RoomDatabase paramRoomDatabase)
  {
    this.__db = paramRoomDatabase;
    this.__insertionAdapterOfWorkName = new EntityInsertionAdapter(paramRoomDatabase)
    {
      public void bind(SupportSQLiteStatement paramAnonymousSupportSQLiteStatement, WorkName paramAnonymousWorkName)
      {
        if (paramAnonymousWorkName.name == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(1);
        } else {
          paramAnonymousSupportSQLiteStatement.bindString(1, paramAnonymousWorkName.name);
        }
        if (paramAnonymousWorkName.workSpecId == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(2);
        } else {
          paramAnonymousSupportSQLiteStatement.bindString(2, paramAnonymousWorkName.workSpecId);
        }
      }
      
      public String createQuery()
      {
        return "INSERT OR IGNORE INTO `WorkName` (`name`,`work_spec_id`) VALUES (?,?)";
      }
    };
  }
  
  public List<String> getWorkSpecIdsWithName(String paramString)
  {
    RoomSQLiteQuery localRoomSQLiteQuery = RoomSQLiteQuery.acquire("SELECT work_spec_id FROM workname WHERE name=?", 1);
    if (paramString == null) {
      localRoomSQLiteQuery.bindNull(1);
    } else {
      localRoomSQLiteQuery.bindString(1, paramString);
    }
    this.__db.assertNotSuspendingTransaction();
    paramString = DBUtil.query(this.__db, localRoomSQLiteQuery, false, null);
    try
    {
      ArrayList localArrayList = new java/util/ArrayList;
      localArrayList.<init>(paramString.getCount());
      while (paramString.moveToNext()) {
        localArrayList.add(paramString.getString(0));
      }
      return localArrayList;
    }
    finally
    {
      paramString.close();
      localRoomSQLiteQuery.release();
    }
  }
  
  public void insert(WorkName paramWorkName)
  {
    this.__db.assertNotSuspendingTransaction();
    this.__db.beginTransaction();
    try
    {
      this.__insertionAdapterOfWorkName.insert(paramWorkName);
      this.__db.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.__db.endTransaction();
    }
  }
}

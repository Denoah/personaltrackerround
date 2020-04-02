package androidx.work.impl.model;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.concurrent.Callable;

public final class PreferenceDao_Impl
  implements PreferenceDao
{
  private final RoomDatabase __db;
  private final EntityInsertionAdapter<Preference> __insertionAdapterOfPreference;
  
  public PreferenceDao_Impl(RoomDatabase paramRoomDatabase)
  {
    this.__db = paramRoomDatabase;
    this.__insertionAdapterOfPreference = new EntityInsertionAdapter(paramRoomDatabase)
    {
      public void bind(SupportSQLiteStatement paramAnonymousSupportSQLiteStatement, Preference paramAnonymousPreference)
      {
        if (paramAnonymousPreference.mKey == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(1);
        } else {
          paramAnonymousSupportSQLiteStatement.bindString(1, paramAnonymousPreference.mKey);
        }
        if (paramAnonymousPreference.mValue == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(2);
        } else {
          paramAnonymousSupportSQLiteStatement.bindLong(2, paramAnonymousPreference.mValue.longValue());
        }
      }
      
      public String createQuery()
      {
        return "INSERT OR REPLACE INTO `Preference` (`key`,`long_value`) VALUES (?,?)";
      }
    };
  }
  
  public Long getLongValue(String paramString)
  {
    RoomSQLiteQuery localRoomSQLiteQuery = RoomSQLiteQuery.acquire("SELECT long_value FROM Preference where `key`=?", 1);
    if (paramString == null) {
      localRoomSQLiteQuery.bindNull(1);
    } else {
      localRoomSQLiteQuery.bindString(1, paramString);
    }
    this.__db.assertNotSuspendingTransaction();
    paramString = this.__db;
    Object localObject = null;
    Cursor localCursor = DBUtil.query(paramString, localRoomSQLiteQuery, false, null);
    paramString = localObject;
    try
    {
      if (localCursor.moveToFirst()) {
        if (localCursor.isNull(0)) {
          paramString = localObject;
        } else {
          paramString = Long.valueOf(localCursor.getLong(0));
        }
      }
      return paramString;
    }
    finally
    {
      localCursor.close();
      localRoomSQLiteQuery.release();
    }
  }
  
  public LiveData<Long> getObservableLongValue(String paramString)
  {
    Object localObject = RoomSQLiteQuery.acquire("SELECT long_value FROM Preference where `key`=?", 1);
    if (paramString == null) {
      ((RoomSQLiteQuery)localObject).bindNull(1);
    } else {
      ((RoomSQLiteQuery)localObject).bindString(1, paramString);
    }
    paramString = this.__db.getInvalidationTracker();
    localObject = new Callable()
    {
      public Long call()
        throws Exception
      {
        Object localObject1 = PreferenceDao_Impl.this.__db;
        Object localObject3 = this.val$_statement;
        Object localObject4 = null;
        localObject3 = DBUtil.query((RoomDatabase)localObject1, (SupportSQLiteQuery)localObject3, false, null);
        localObject1 = localObject4;
        try
        {
          if (((Cursor)localObject3).moveToFirst()) {
            if (((Cursor)localObject3).isNull(0)) {
              localObject1 = localObject4;
            } else {
              localObject1 = Long.valueOf(((Cursor)localObject3).getLong(0));
            }
          }
          return localObject1;
        }
        finally
        {
          ((Cursor)localObject3).close();
        }
      }
      
      protected void finalize()
      {
        this.val$_statement.release();
      }
    };
    return paramString.createLiveData(new String[] { "Preference" }, false, (Callable)localObject);
  }
  
  public void insertPreference(Preference paramPreference)
  {
    this.__db.assertNotSuspendingTransaction();
    this.__db.beginTransaction();
    try
    {
      this.__insertionAdapterOfPreference.insert(paramPreference);
      this.__db.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.__db.endTransaction();
    }
  }
}

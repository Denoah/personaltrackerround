package com.askgps.personaltrackercore.database;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.askgps.personaltrackercore.database.model.PhoneNumber;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public final class PhoneBookDao_Impl
  implements PhoneBookDao
{
  private final RoomDatabase __db;
  private final EntityDeletionOrUpdateAdapter<PhoneNumber> __deletionAdapterOfPhoneNumber;
  private final EntityInsertionAdapter<PhoneNumber> __insertionAdapterOfPhoneNumber;
  
  public PhoneBookDao_Impl(RoomDatabase paramRoomDatabase)
  {
    this.__db = paramRoomDatabase;
    this.__insertionAdapterOfPhoneNumber = new EntityInsertionAdapter(paramRoomDatabase)
    {
      public void bind(SupportSQLiteStatement paramAnonymousSupportSQLiteStatement, PhoneNumber paramAnonymousPhoneNumber)
      {
        if (paramAnonymousPhoneNumber.getName() == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(1);
        } else {
          paramAnonymousSupportSQLiteStatement.bindString(1, paramAnonymousPhoneNumber.getName());
        }
        if (paramAnonymousPhoneNumber.getNumber() == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(2);
        } else {
          paramAnonymousSupportSQLiteStatement.bindString(2, paramAnonymousPhoneNumber.getNumber());
        }
      }
      
      public String createQuery()
      {
        return "INSERT OR REPLACE INTO `PhoneNumber` (`name`,`number`) VALUES (?,?)";
      }
    };
    this.__deletionAdapterOfPhoneNumber = new EntityDeletionOrUpdateAdapter(paramRoomDatabase)
    {
      public void bind(SupportSQLiteStatement paramAnonymousSupportSQLiteStatement, PhoneNumber paramAnonymousPhoneNumber)
      {
        if (paramAnonymousPhoneNumber.getNumber() == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(1);
        } else {
          paramAnonymousSupportSQLiteStatement.bindString(1, paramAnonymousPhoneNumber.getNumber());
        }
      }
      
      public String createQuery()
      {
        return "DELETE FROM `PhoneNumber` WHERE `number` = ?";
      }
    };
  }
  
  public void addNumber(PhoneNumber paramPhoneNumber)
  {
    this.__db.assertNotSuspendingTransaction();
    this.__db.beginTransaction();
    try
    {
      this.__insertionAdapterOfPhoneNumber.insert(paramPhoneNumber);
      this.__db.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.__db.endTransaction();
    }
  }
  
  public void deleteNumber(PhoneNumber paramPhoneNumber)
  {
    this.__db.assertNotSuspendingTransaction();
    this.__db.beginTransaction();
    try
    {
      this.__deletionAdapterOfPhoneNumber.handle(paramPhoneNumber);
      this.__db.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.__db.endTransaction();
    }
  }
  
  public LiveData<List<PhoneNumber>> getNumbers()
  {
    Object localObject = RoomSQLiteQuery.acquire("SELECT * FROM PhoneNumber LIMIT 3", 0);
    InvalidationTracker localInvalidationTracker = this.__db.getInvalidationTracker();
    localObject = new Callable()
    {
      public List<PhoneNumber> call()
        throws Exception
      {
        Cursor localCursor = DBUtil.query(PhoneBookDao_Impl.this.__db, this.val$_statement, false, null);
        try
        {
          int i = CursorUtil.getColumnIndexOrThrow(localCursor, "name");
          int j = CursorUtil.getColumnIndexOrThrow(localCursor, "number");
          ArrayList localArrayList = new java/util/ArrayList;
          localArrayList.<init>(localCursor.getCount());
          while (localCursor.moveToNext())
          {
            String str1 = localCursor.getString(i);
            String str2 = localCursor.getString(j);
            PhoneNumber localPhoneNumber = new com/askgps/personaltrackercore/database/model/PhoneNumber;
            localPhoneNumber.<init>(str1, str2);
            localArrayList.add(localPhoneNumber);
          }
          return localArrayList;
        }
        finally
        {
          localCursor.close();
        }
      }
      
      protected void finalize()
      {
        this.val$_statement.release();
      }
    };
    return localInvalidationTracker.createLiveData(new String[] { "PhoneNumber" }, false, (Callable)localObject);
  }
}

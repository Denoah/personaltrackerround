package com.askgps.personaltrackercore.database;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.askgps.personaltrackercore.database.model.StepSensor;

public final class StepSensorDao_Impl
  implements StepSensorDao
{
  private final RoomDatabase __db;
  private final EntityInsertionAdapter<StepSensor> __insertionAdapterOfStepSensor;
  
  public StepSensorDao_Impl(RoomDatabase paramRoomDatabase)
  {
    this.__db = paramRoomDatabase;
    this.__insertionAdapterOfStepSensor = new EntityInsertionAdapter(paramRoomDatabase)
    {
      public void bind(SupportSQLiteStatement paramAnonymousSupportSQLiteStatement, StepSensor paramAnonymousStepSensor)
      {
        paramAnonymousSupportSQLiteStatement.bindLong(1, paramAnonymousStepSensor.getLastSensorValue());
        paramAnonymousSupportSQLiteStatement.bindLong(2, paramAnonymousStepSensor.getStepsTimeStamp());
        paramAnonymousSupportSQLiteStatement.bindLong(3, paramAnonymousStepSensor.getStepsAtStartDay());
        paramAnonymousSupportSQLiteStatement.bindLong(4, paramAnonymousStepSensor.getStepCount());
        paramAnonymousSupportSQLiteStatement.bindLong(5, paramAnonymousStepSensor.getId());
      }
      
      public String createQuery()
      {
        return "INSERT OR REPLACE INTO `StepSensor` (`lastSensorValue`,`stepsTimeStamp`,`stepsAtStartDay`,`stepCount`,`id`) VALUES (?,?,?,?,?)";
      }
    };
  }
  
  public StepSensor getStepInfo()
  {
    RoomSQLiteQuery localRoomSQLiteQuery = RoomSQLiteQuery.acquire("SELECT * FROM StepSensor WHERE id = 1", 0);
    this.__db.assertNotSuspendingTransaction();
    Object localObject1 = this.__db;
    StepSensor localStepSensor = null;
    localObject1 = DBUtil.query((RoomDatabase)localObject1, localRoomSQLiteQuery, false, null);
    try
    {
      int i = CursorUtil.getColumnIndexOrThrow((Cursor)localObject1, "lastSensorValue");
      int j = CursorUtil.getColumnIndexOrThrow((Cursor)localObject1, "stepsTimeStamp");
      int k = CursorUtil.getColumnIndexOrThrow((Cursor)localObject1, "stepsAtStartDay");
      int m = CursorUtil.getColumnIndexOrThrow((Cursor)localObject1, "stepCount");
      int n = CursorUtil.getColumnIndexOrThrow((Cursor)localObject1, "id");
      if (((Cursor)localObject1).moveToFirst()) {
        localStepSensor = new StepSensor(((Cursor)localObject1).getInt(i), ((Cursor)localObject1).getLong(j), ((Cursor)localObject1).getInt(k), ((Cursor)localObject1).getInt(m), ((Cursor)localObject1).getInt(n));
      }
      return localStepSensor;
    }
    finally
    {
      ((Cursor)localObject1).close();
      localRoomSQLiteQuery.release();
    }
  }
  
  public void updateRow(StepSensor paramStepSensor)
  {
    this.__db.assertNotSuspendingTransaction();
    this.__db.beginTransaction();
    try
    {
      this.__insertionAdapterOfStepSensor.insert(paramStepSensor);
      this.__db.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.__db.endTransaction();
    }
  }
}

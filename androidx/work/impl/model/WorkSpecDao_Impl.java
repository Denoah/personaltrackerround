package androidx.work.impl.model;

import android.database.Cursor;
import androidx.collection.ArrayMap;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.WorkInfo.State;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

public final class WorkSpecDao_Impl
  implements WorkSpecDao
{
  private final RoomDatabase __db;
  private final EntityInsertionAdapter<WorkSpec> __insertionAdapterOfWorkSpec;
  private final SharedSQLiteStatement __preparedStmtOfDelete;
  private final SharedSQLiteStatement __preparedStmtOfIncrementWorkSpecRunAttemptCount;
  private final SharedSQLiteStatement __preparedStmtOfMarkWorkSpecScheduled;
  private final SharedSQLiteStatement __preparedStmtOfPruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast;
  private final SharedSQLiteStatement __preparedStmtOfResetScheduledState;
  private final SharedSQLiteStatement __preparedStmtOfResetWorkSpecRunAttemptCount;
  private final SharedSQLiteStatement __preparedStmtOfSetOutput;
  private final SharedSQLiteStatement __preparedStmtOfSetPeriodStartTime;
  
  public WorkSpecDao_Impl(RoomDatabase paramRoomDatabase)
  {
    this.__db = paramRoomDatabase;
    this.__insertionAdapterOfWorkSpec = new EntityInsertionAdapter(paramRoomDatabase)
    {
      public void bind(SupportSQLiteStatement paramAnonymousSupportSQLiteStatement, WorkSpec paramAnonymousWorkSpec)
      {
        if (paramAnonymousWorkSpec.id == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(1);
        } else {
          paramAnonymousSupportSQLiteStatement.bindString(1, paramAnonymousWorkSpec.id);
        }
        paramAnonymousSupportSQLiteStatement.bindLong(2, WorkTypeConverters.stateToInt(paramAnonymousWorkSpec.state));
        if (paramAnonymousWorkSpec.workerClassName == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(3);
        } else {
          paramAnonymousSupportSQLiteStatement.bindString(3, paramAnonymousWorkSpec.workerClassName);
        }
        if (paramAnonymousWorkSpec.inputMergerClassName == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(4);
        } else {
          paramAnonymousSupportSQLiteStatement.bindString(4, paramAnonymousWorkSpec.inputMergerClassName);
        }
        byte[] arrayOfByte = Data.toByteArray(paramAnonymousWorkSpec.input);
        if (arrayOfByte == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(5);
        } else {
          paramAnonymousSupportSQLiteStatement.bindBlob(5, arrayOfByte);
        }
        arrayOfByte = Data.toByteArray(paramAnonymousWorkSpec.output);
        if (arrayOfByte == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(6);
        } else {
          paramAnonymousSupportSQLiteStatement.bindBlob(6, arrayOfByte);
        }
        paramAnonymousSupportSQLiteStatement.bindLong(7, paramAnonymousWorkSpec.initialDelay);
        paramAnonymousSupportSQLiteStatement.bindLong(8, paramAnonymousWorkSpec.intervalDuration);
        paramAnonymousSupportSQLiteStatement.bindLong(9, paramAnonymousWorkSpec.flexDuration);
        paramAnonymousSupportSQLiteStatement.bindLong(10, paramAnonymousWorkSpec.runAttemptCount);
        paramAnonymousSupportSQLiteStatement.bindLong(11, WorkTypeConverters.backoffPolicyToInt(paramAnonymousWorkSpec.backoffPolicy));
        paramAnonymousSupportSQLiteStatement.bindLong(12, paramAnonymousWorkSpec.backoffDelayDuration);
        paramAnonymousSupportSQLiteStatement.bindLong(13, paramAnonymousWorkSpec.periodStartTime);
        paramAnonymousSupportSQLiteStatement.bindLong(14, paramAnonymousWorkSpec.minimumRetentionDuration);
        paramAnonymousSupportSQLiteStatement.bindLong(15, paramAnonymousWorkSpec.scheduleRequestedAt);
        paramAnonymousSupportSQLiteStatement.bindLong(16, paramAnonymousWorkSpec.runInForeground);
        paramAnonymousWorkSpec = paramAnonymousWorkSpec.constraints;
        if (paramAnonymousWorkSpec != null)
        {
          paramAnonymousSupportSQLiteStatement.bindLong(17, WorkTypeConverters.networkTypeToInt(paramAnonymousWorkSpec.getRequiredNetworkType()));
          paramAnonymousSupportSQLiteStatement.bindLong(18, paramAnonymousWorkSpec.requiresCharging());
          paramAnonymousSupportSQLiteStatement.bindLong(19, paramAnonymousWorkSpec.requiresDeviceIdle());
          paramAnonymousSupportSQLiteStatement.bindLong(20, paramAnonymousWorkSpec.requiresBatteryNotLow());
          paramAnonymousSupportSQLiteStatement.bindLong(21, paramAnonymousWorkSpec.requiresStorageNotLow());
          paramAnonymousSupportSQLiteStatement.bindLong(22, paramAnonymousWorkSpec.getTriggerContentUpdateDelay());
          paramAnonymousSupportSQLiteStatement.bindLong(23, paramAnonymousWorkSpec.getTriggerMaxContentDelay());
          paramAnonymousWorkSpec = WorkTypeConverters.contentUriTriggersToByteArray(paramAnonymousWorkSpec.getContentUriTriggers());
          if (paramAnonymousWorkSpec == null) {
            paramAnonymousSupportSQLiteStatement.bindNull(24);
          } else {
            paramAnonymousSupportSQLiteStatement.bindBlob(24, paramAnonymousWorkSpec);
          }
        }
        else
        {
          paramAnonymousSupportSQLiteStatement.bindNull(17);
          paramAnonymousSupportSQLiteStatement.bindNull(18);
          paramAnonymousSupportSQLiteStatement.bindNull(19);
          paramAnonymousSupportSQLiteStatement.bindNull(20);
          paramAnonymousSupportSQLiteStatement.bindNull(21);
          paramAnonymousSupportSQLiteStatement.bindNull(22);
          paramAnonymousSupportSQLiteStatement.bindNull(23);
          paramAnonymousSupportSQLiteStatement.bindNull(24);
        }
      }
      
      public String createQuery()
      {
        return "INSERT OR IGNORE INTO `WorkSpec` (`id`,`state`,`worker_class_name`,`input_merger_class_name`,`input`,`output`,`initial_delay`,`interval_duration`,`flex_duration`,`run_attempt_count`,`backoff_policy`,`backoff_delay_duration`,`period_start_time`,`minimum_retention_duration`,`schedule_requested_at`,`run_in_foreground`,`required_network_type`,`requires_charging`,`requires_device_idle`,`requires_battery_not_low`,`requires_storage_not_low`,`trigger_content_update_delay`,`trigger_max_content_delay`,`content_uri_triggers`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(paramRoomDatabase)
    {
      public String createQuery()
      {
        return "DELETE FROM workspec WHERE id=?";
      }
    };
    this.__preparedStmtOfSetOutput = new SharedSQLiteStatement(paramRoomDatabase)
    {
      public String createQuery()
      {
        return "UPDATE workspec SET output=? WHERE id=?";
      }
    };
    this.__preparedStmtOfSetPeriodStartTime = new SharedSQLiteStatement(paramRoomDatabase)
    {
      public String createQuery()
      {
        return "UPDATE workspec SET period_start_time=? WHERE id=?";
      }
    };
    this.__preparedStmtOfIncrementWorkSpecRunAttemptCount = new SharedSQLiteStatement(paramRoomDatabase)
    {
      public String createQuery()
      {
        return "UPDATE workspec SET run_attempt_count=run_attempt_count+1 WHERE id=?";
      }
    };
    this.__preparedStmtOfResetWorkSpecRunAttemptCount = new SharedSQLiteStatement(paramRoomDatabase)
    {
      public String createQuery()
      {
        return "UPDATE workspec SET run_attempt_count=0 WHERE id=?";
      }
    };
    this.__preparedStmtOfMarkWorkSpecScheduled = new SharedSQLiteStatement(paramRoomDatabase)
    {
      public String createQuery()
      {
        return "UPDATE workspec SET schedule_requested_at=? WHERE id=?";
      }
    };
    this.__preparedStmtOfResetScheduledState = new SharedSQLiteStatement(paramRoomDatabase)
    {
      public String createQuery()
      {
        return "UPDATE workspec SET schedule_requested_at=-1 WHERE state NOT IN (2, 3, 5)";
      }
    };
    this.__preparedStmtOfPruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast = new SharedSQLiteStatement(paramRoomDatabase)
    {
      public String createQuery()
      {
        return "DELETE FROM workspec WHERE state IN (2, 3, 5) AND (SELECT COUNT(*)=0 FROM dependency WHERE     prerequisite_id=id AND     work_spec_id NOT IN         (SELECT id FROM workspec WHERE state IN (2, 3, 5)))";
      }
    };
  }
  
  private void __fetchRelationshipWorkProgressAsandroidxWorkData(ArrayMap<String, ArrayList<Data>> paramArrayMap)
  {
    Object localObject1 = paramArrayMap.keySet();
    if (((Set)localObject1).isEmpty()) {
      return;
    }
    if (paramArrayMap.size() > 999)
    {
      localObject2 = new ArrayMap(999);
      int i = paramArrayMap.size();
      int j = 0;
      k = j;
      while (j < i)
      {
        ((ArrayMap)localObject2).put(paramArrayMap.keyAt(j), paramArrayMap.valueAt(j));
        int m = j + 1;
        int n = k + 1;
        j = m;
        k = n;
        if (n == 999)
        {
          __fetchRelationshipWorkProgressAsandroidxWorkData((ArrayMap)localObject2);
          localObject2 = new ArrayMap(999);
          k = 0;
          j = m;
        }
      }
      if (k > 0) {
        __fetchRelationshipWorkProgressAsandroidxWorkData((ArrayMap)localObject2);
      }
      return;
    }
    Object localObject2 = StringUtil.newStringBuilder();
    ((StringBuilder)localObject2).append("SELECT `progress`,`work_spec_id` FROM `WorkProgress` WHERE `work_spec_id` IN (");
    int k = ((Set)localObject1).size();
    StringUtil.appendPlaceholders((StringBuilder)localObject2, k);
    ((StringBuilder)localObject2).append(")");
    localObject2 = RoomSQLiteQuery.acquire(((StringBuilder)localObject2).toString(), k + 0);
    Iterator localIterator = ((Set)localObject1).iterator();
    for (k = 1; localIterator.hasNext(); k++)
    {
      localObject1 = (String)localIterator.next();
      if (localObject1 == null) {
        ((RoomSQLiteQuery)localObject2).bindNull(k);
      } else {
        ((RoomSQLiteQuery)localObject2).bindString(k, (String)localObject1);
      }
    }
    localObject2 = DBUtil.query(this.__db, (SupportSQLiteQuery)localObject2, false, null);
    try
    {
      k = CursorUtil.getColumnIndex((Cursor)localObject2, "work_spec_id");
      if (k == -1) {
        return;
      }
      while (((Cursor)localObject2).moveToNext()) {
        if (!((Cursor)localObject2).isNull(k))
        {
          localObject1 = (ArrayList)paramArrayMap.get(((Cursor)localObject2).getString(k));
          if (localObject1 != null) {
            ((ArrayList)localObject1).add(Data.fromByteArray(((Cursor)localObject2).getBlob(0)));
          }
        }
      }
      return;
    }
    finally
    {
      ((Cursor)localObject2).close();
    }
  }
  
  private void __fetchRelationshipWorkTagAsjavaLangString(ArrayMap<String, ArrayList<String>> paramArrayMap)
  {
    Object localObject1 = paramArrayMap.keySet();
    if (((Set)localObject1).isEmpty()) {
      return;
    }
    if (paramArrayMap.size() > 999)
    {
      localObject2 = new ArrayMap(999);
      int i = paramArrayMap.size();
      int j = 0;
      k = j;
      while (j < i)
      {
        ((ArrayMap)localObject2).put(paramArrayMap.keyAt(j), paramArrayMap.valueAt(j));
        int m = j + 1;
        int n = k + 1;
        j = m;
        k = n;
        if (n == 999)
        {
          __fetchRelationshipWorkTagAsjavaLangString((ArrayMap)localObject2);
          localObject2 = new ArrayMap(999);
          k = 0;
          j = m;
        }
      }
      if (k > 0) {
        __fetchRelationshipWorkTagAsjavaLangString((ArrayMap)localObject2);
      }
      return;
    }
    Object localObject2 = StringUtil.newStringBuilder();
    ((StringBuilder)localObject2).append("SELECT `tag`,`work_spec_id` FROM `WorkTag` WHERE `work_spec_id` IN (");
    int k = ((Set)localObject1).size();
    StringUtil.appendPlaceholders((StringBuilder)localObject2, k);
    ((StringBuilder)localObject2).append(")");
    localObject2 = RoomSQLiteQuery.acquire(((StringBuilder)localObject2).toString(), k + 0);
    localObject1 = ((Set)localObject1).iterator();
    for (k = 1; ((Iterator)localObject1).hasNext(); k++)
    {
      String str = (String)((Iterator)localObject1).next();
      if (str == null) {
        ((RoomSQLiteQuery)localObject2).bindNull(k);
      } else {
        ((RoomSQLiteQuery)localObject2).bindString(k, str);
      }
    }
    localObject2 = DBUtil.query(this.__db, (SupportSQLiteQuery)localObject2, false, null);
    try
    {
      k = CursorUtil.getColumnIndex((Cursor)localObject2, "work_spec_id");
      if (k == -1) {
        return;
      }
      while (((Cursor)localObject2).moveToNext()) {
        if (!((Cursor)localObject2).isNull(k))
        {
          localObject1 = (ArrayList)paramArrayMap.get(((Cursor)localObject2).getString(k));
          if (localObject1 != null) {
            ((ArrayList)localObject1).add(((Cursor)localObject2).getString(0));
          }
        }
      }
      return;
    }
    finally
    {
      ((Cursor)localObject2).close();
    }
  }
  
  public void delete(String paramString)
  {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement localSupportSQLiteStatement = this.__preparedStmtOfDelete.acquire();
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
      this.__preparedStmtOfDelete.release(localSupportSQLiteStatement);
    }
  }
  
  public List<String> getAllUnfinishedWork()
  {
    RoomSQLiteQuery localRoomSQLiteQuery = RoomSQLiteQuery.acquire("SELECT id FROM workspec WHERE state NOT IN (2, 3, 5)", 0);
    this.__db.assertNotSuspendingTransaction();
    Cursor localCursor = DBUtil.query(this.__db, localRoomSQLiteQuery, false, null);
    try
    {
      ArrayList localArrayList = new java/util/ArrayList;
      localArrayList.<init>(localCursor.getCount());
      while (localCursor.moveToNext()) {
        localArrayList.add(localCursor.getString(0));
      }
      return localArrayList;
    }
    finally
    {
      localCursor.close();
      localRoomSQLiteQuery.release();
    }
  }
  
  public List<String> getAllWorkSpecIds()
  {
    RoomSQLiteQuery localRoomSQLiteQuery = RoomSQLiteQuery.acquire("SELECT id FROM workspec", 0);
    this.__db.assertNotSuspendingTransaction();
    Cursor localCursor = DBUtil.query(this.__db, localRoomSQLiteQuery, false, null);
    try
    {
      ArrayList localArrayList = new java/util/ArrayList;
      localArrayList.<init>(localCursor.getCount());
      while (localCursor.moveToNext()) {
        localArrayList.add(localCursor.getString(0));
      }
      return localArrayList;
    }
    finally
    {
      localCursor.close();
      localRoomSQLiteQuery.release();
    }
  }
  
  /* Error */
  public List<WorkSpec> getEligibleWorkForScheduling(int paramInt)
  {
    // Byte code:
    //   0: ldc_w 284
    //   3: iconst_1
    //   4: invokestatic 146	androidx/room/RoomSQLiteQuery:acquire	(Ljava/lang/String;I)Landroidx/room/RoomSQLiteQuery;
    //   7: astore_2
    //   8: aload_2
    //   9: iconst_1
    //   10: iload_1
    //   11: i2l
    //   12: invokevirtual 288	androidx/room/RoomSQLiteQuery:bindLong	(IJ)V
    //   15: aload_0
    //   16: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   19: invokevirtual 238	androidx/room/RoomDatabase:assertNotSuspendingTransaction	()V
    //   22: aload_0
    //   23: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   26: aload_2
    //   27: iconst_0
    //   28: aconst_null
    //   29: invokestatic 174	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   32: astore_3
    //   33: aload_3
    //   34: ldc_w 290
    //   37: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   40: istore 4
    //   42: aload_3
    //   43: ldc_w 295
    //   46: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   49: istore 5
    //   51: aload_3
    //   52: ldc_w 297
    //   55: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   58: istore 6
    //   60: aload_3
    //   61: ldc_w 299
    //   64: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   67: istore 7
    //   69: aload_3
    //   70: ldc_w 301
    //   73: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   76: istore 8
    //   78: aload_3
    //   79: ldc_w 303
    //   82: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   85: istore 9
    //   87: aload_3
    //   88: ldc_w 305
    //   91: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   94: istore 10
    //   96: aload_3
    //   97: ldc_w 307
    //   100: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   103: istore 11
    //   105: aload_3
    //   106: ldc_w 309
    //   109: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   112: istore 12
    //   114: aload_3
    //   115: ldc_w 311
    //   118: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   121: istore 13
    //   123: aload_3
    //   124: ldc_w 313
    //   127: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   130: istore 14
    //   132: aload_3
    //   133: ldc_w 315
    //   136: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   139: istore 15
    //   141: aload_3
    //   142: ldc_w 317
    //   145: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   148: istore 16
    //   150: aload_3
    //   151: ldc_w 319
    //   154: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   157: istore 17
    //   159: aload_3
    //   160: ldc_w 321
    //   163: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   166: istore 18
    //   168: aload_3
    //   169: ldc_w 323
    //   172: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   175: istore 19
    //   177: aload_3
    //   178: ldc_w 325
    //   181: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   184: istore 20
    //   186: aload_3
    //   187: ldc_w 327
    //   190: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   193: istore 21
    //   195: aload_3
    //   196: ldc_w 329
    //   199: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   202: istore_1
    //   203: aload_3
    //   204: ldc_w 331
    //   207: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   210: istore 22
    //   212: aload_3
    //   213: ldc_w 333
    //   216: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   219: istore 23
    //   221: aload_3
    //   222: ldc_w 335
    //   225: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   228: istore 24
    //   230: aload_3
    //   231: ldc_w 337
    //   234: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   237: istore 25
    //   239: aload_3
    //   240: ldc_w 339
    //   243: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   246: istore 26
    //   248: new 204	java/util/ArrayList
    //   251: astore 27
    //   253: aload 27
    //   255: aload_3
    //   256: invokeinterface 270 1 0
    //   261: invokespecial 271	java/util/ArrayList:<init>	(I)V
    //   264: aload_3
    //   265: invokeinterface 190 1 0
    //   270: ifeq +444 -> 714
    //   273: aload_3
    //   274: iload 12
    //   276: invokeinterface 198 2 0
    //   281: astore 28
    //   283: aload_3
    //   284: iload 14
    //   286: invokeinterface 198 2 0
    //   291: astore 29
    //   293: new 341	androidx/work/Constraints
    //   296: astore 30
    //   298: aload 30
    //   300: invokespecial 342	androidx/work/Constraints:<init>	()V
    //   303: aload 30
    //   305: aload_3
    //   306: iload 4
    //   308: invokeinterface 346 2 0
    //   313: invokestatic 352	androidx/work/impl/model/WorkTypeConverters:intToNetworkType	(I)Landroidx/work/NetworkType;
    //   316: invokevirtual 356	androidx/work/Constraints:setRequiredNetworkType	(Landroidx/work/NetworkType;)V
    //   319: aload_3
    //   320: iload 5
    //   322: invokeinterface 346 2 0
    //   327: ifeq +9 -> 336
    //   330: iconst_1
    //   331: istore 31
    //   333: goto +6 -> 339
    //   336: iconst_0
    //   337: istore 31
    //   339: aload 30
    //   341: iload 31
    //   343: invokevirtual 360	androidx/work/Constraints:setRequiresCharging	(Z)V
    //   346: aload_3
    //   347: iload 6
    //   349: invokeinterface 346 2 0
    //   354: ifeq +9 -> 363
    //   357: iconst_1
    //   358: istore 31
    //   360: goto +6 -> 366
    //   363: iconst_0
    //   364: istore 31
    //   366: aload 30
    //   368: iload 31
    //   370: invokevirtual 363	androidx/work/Constraints:setRequiresDeviceIdle	(Z)V
    //   373: aload_3
    //   374: iload 7
    //   376: invokeinterface 346 2 0
    //   381: ifeq +9 -> 390
    //   384: iconst_1
    //   385: istore 31
    //   387: goto +6 -> 393
    //   390: iconst_0
    //   391: istore 31
    //   393: aload 30
    //   395: iload 31
    //   397: invokevirtual 366	androidx/work/Constraints:setRequiresBatteryNotLow	(Z)V
    //   400: aload_3
    //   401: iload 8
    //   403: invokeinterface 346 2 0
    //   408: ifeq +9 -> 417
    //   411: iconst_1
    //   412: istore 31
    //   414: goto +6 -> 420
    //   417: iconst_0
    //   418: istore 31
    //   420: aload 30
    //   422: iload 31
    //   424: invokevirtual 369	androidx/work/Constraints:setRequiresStorageNotLow	(Z)V
    //   427: aload 30
    //   429: aload_3
    //   430: iload 9
    //   432: invokeinterface 373 2 0
    //   437: invokevirtual 377	androidx/work/Constraints:setTriggerContentUpdateDelay	(J)V
    //   440: aload 30
    //   442: aload_3
    //   443: iload 10
    //   445: invokeinterface 373 2 0
    //   450: invokevirtual 380	androidx/work/Constraints:setTriggerMaxContentDelay	(J)V
    //   453: aload 30
    //   455: aload_3
    //   456: iload 11
    //   458: invokeinterface 208 2 0
    //   463: invokestatic 384	androidx/work/impl/model/WorkTypeConverters:byteArrayToContentUriTriggers	([B)Landroidx/work/ContentUriTriggers;
    //   466: invokevirtual 388	androidx/work/Constraints:setContentUriTriggers	(Landroidx/work/ContentUriTriggers;)V
    //   469: new 390	androidx/work/impl/model/WorkSpec
    //   472: astore 32
    //   474: aload 32
    //   476: aload 28
    //   478: aload 29
    //   480: invokespecial 393	androidx/work/impl/model/WorkSpec:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   483: aload 32
    //   485: aload_3
    //   486: iload 13
    //   488: invokeinterface 346 2 0
    //   493: invokestatic 397	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
    //   496: putfield 400	androidx/work/impl/model/WorkSpec:state	Landroidx/work/WorkInfo$State;
    //   499: aload 32
    //   501: aload_3
    //   502: iload 15
    //   504: invokeinterface 198 2 0
    //   509: putfield 404	androidx/work/impl/model/WorkSpec:inputMergerClassName	Ljava/lang/String;
    //   512: aload 32
    //   514: aload_3
    //   515: iload 16
    //   517: invokeinterface 208 2 0
    //   522: invokestatic 214	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   525: putfield 407	androidx/work/impl/model/WorkSpec:input	Landroidx/work/Data;
    //   528: aload 32
    //   530: aload_3
    //   531: iload 17
    //   533: invokeinterface 208 2 0
    //   538: invokestatic 214	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   541: putfield 409	androidx/work/impl/model/WorkSpec:output	Landroidx/work/Data;
    //   544: aload 32
    //   546: aload_3
    //   547: iload 18
    //   549: invokeinterface 373 2 0
    //   554: putfield 413	androidx/work/impl/model/WorkSpec:initialDelay	J
    //   557: aload 32
    //   559: aload_3
    //   560: iload 19
    //   562: invokeinterface 373 2 0
    //   567: putfield 416	androidx/work/impl/model/WorkSpec:intervalDuration	J
    //   570: aload 32
    //   572: aload_3
    //   573: iload 20
    //   575: invokeinterface 373 2 0
    //   580: putfield 419	androidx/work/impl/model/WorkSpec:flexDuration	J
    //   583: aload 32
    //   585: aload_3
    //   586: iload 21
    //   588: invokeinterface 346 2 0
    //   593: putfield 423	androidx/work/impl/model/WorkSpec:runAttemptCount	I
    //   596: aload_3
    //   597: iload_1
    //   598: invokeinterface 346 2 0
    //   603: istore 33
    //   605: aload 32
    //   607: iload 33
    //   609: invokestatic 427	androidx/work/impl/model/WorkTypeConverters:intToBackoffPolicy	(I)Landroidx/work/BackoffPolicy;
    //   612: putfield 431	androidx/work/impl/model/WorkSpec:backoffPolicy	Landroidx/work/BackoffPolicy;
    //   615: aload 32
    //   617: aload_3
    //   618: iload 22
    //   620: invokeinterface 373 2 0
    //   625: putfield 434	androidx/work/impl/model/WorkSpec:backoffDelayDuration	J
    //   628: aload 32
    //   630: aload_3
    //   631: iload 23
    //   633: invokeinterface 373 2 0
    //   638: putfield 437	androidx/work/impl/model/WorkSpec:periodStartTime	J
    //   641: aload 32
    //   643: aload_3
    //   644: iload 24
    //   646: invokeinterface 373 2 0
    //   651: putfield 440	androidx/work/impl/model/WorkSpec:minimumRetentionDuration	J
    //   654: aload 32
    //   656: aload_3
    //   657: iload 25
    //   659: invokeinterface 373 2 0
    //   664: putfield 443	androidx/work/impl/model/WorkSpec:scheduleRequestedAt	J
    //   667: aload_3
    //   668: iload 26
    //   670: invokeinterface 346 2 0
    //   675: ifeq +9 -> 684
    //   678: iconst_1
    //   679: istore 31
    //   681: goto +6 -> 687
    //   684: iconst_0
    //   685: istore 31
    //   687: aload 32
    //   689: iload 31
    //   691: putfield 447	androidx/work/impl/model/WorkSpec:runInForeground	Z
    //   694: aload 32
    //   696: aload 30
    //   698: putfield 451	androidx/work/impl/model/WorkSpec:constraints	Landroidx/work/Constraints;
    //   701: aload 27
    //   703: aload 32
    //   705: invokeinterface 274 2 0
    //   710: pop
    //   711: goto -447 -> 264
    //   714: aload_3
    //   715: invokeinterface 187 1 0
    //   720: aload_2
    //   721: invokevirtual 276	androidx/room/RoomSQLiteQuery:release	()V
    //   724: aload 27
    //   726: areturn
    //   727: astore 27
    //   729: goto +5 -> 734
    //   732: astore 27
    //   734: aload_3
    //   735: invokeinterface 187 1 0
    //   740: aload_2
    //   741: invokevirtual 276	androidx/room/RoomSQLiteQuery:release	()V
    //   744: aload 27
    //   746: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	747	0	this	WorkSpecDao_Impl
    //   0	747	1	paramInt	int
    //   7	734	2	localRoomSQLiteQuery	RoomSQLiteQuery
    //   32	703	3	localCursor	Cursor
    //   40	267	4	i	int
    //   49	272	5	j	int
    //   58	290	6	k	int
    //   67	308	7	m	int
    //   76	326	8	n	int
    //   85	346	9	i1	int
    //   94	350	10	i2	int
    //   103	354	11	i3	int
    //   112	163	12	i4	int
    //   121	366	13	i5	int
    //   130	155	14	i6	int
    //   139	364	15	i7	int
    //   148	368	16	i8	int
    //   157	375	17	i9	int
    //   166	382	18	i10	int
    //   175	386	19	i11	int
    //   184	390	20	i12	int
    //   193	394	21	i13	int
    //   210	409	22	i14	int
    //   219	413	23	i15	int
    //   228	417	24	i16	int
    //   237	421	25	i17	int
    //   246	423	26	i18	int
    //   251	474	27	localArrayList	ArrayList
    //   727	1	27	localObject1	Object
    //   732	13	27	localObject2	Object
    //   281	196	28	str1	String
    //   291	188	29	str2	String
    //   296	401	30	localConstraints	Constraints
    //   331	359	31	bool	boolean
    //   472	232	32	localWorkSpec	WorkSpec
    //   603	5	33	i19	int
    // Exception table:
    //   from	to	target	type
    //   159	253	727	finally
    //   253	264	727	finally
    //   264	330	727	finally
    //   339	357	727	finally
    //   366	384	727	finally
    //   393	411	727	finally
    //   420	544	727	finally
    //   544	557	727	finally
    //   557	570	727	finally
    //   570	605	727	finally
    //   605	615	727	finally
    //   615	628	727	finally
    //   628	641	727	finally
    //   641	654	727	finally
    //   654	678	727	finally
    //   687	711	727	finally
    //   33	159	732	finally
  }
  
  public List<Data> getInputsFromPrerequisites(String paramString)
  {
    RoomSQLiteQuery localRoomSQLiteQuery = RoomSQLiteQuery.acquire("SELECT output FROM workspec WHERE id IN (SELECT prerequisite_id FROM dependency WHERE work_spec_id=?)", 1);
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
        localArrayList.add(Data.fromByteArray(paramString.getBlob(0)));
      }
      return localArrayList;
    }
    finally
    {
      paramString.close();
      localRoomSQLiteQuery.release();
    }
  }
  
  /* Error */
  public List<WorkSpec> getRunningWork()
  {
    // Byte code:
    //   0: ldc_w 460
    //   3: iconst_0
    //   4: invokestatic 146	androidx/room/RoomSQLiteQuery:acquire	(Ljava/lang/String;I)Landroidx/room/RoomSQLiteQuery;
    //   7: astore_1
    //   8: aload_0
    //   9: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   12: invokevirtual 238	androidx/room/RoomDatabase:assertNotSuspendingTransaction	()V
    //   15: aload_0
    //   16: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   19: aload_1
    //   20: iconst_0
    //   21: aconst_null
    //   22: invokestatic 174	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   25: astore_2
    //   26: aload_2
    //   27: ldc_w 290
    //   30: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   33: istore_3
    //   34: aload_2
    //   35: ldc_w 295
    //   38: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   41: istore 4
    //   43: aload_2
    //   44: ldc_w 297
    //   47: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   50: istore 5
    //   52: aload_2
    //   53: ldc_w 299
    //   56: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   59: istore 6
    //   61: aload_2
    //   62: ldc_w 301
    //   65: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   68: istore 7
    //   70: aload_2
    //   71: ldc_w 303
    //   74: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   77: istore 8
    //   79: aload_2
    //   80: ldc_w 305
    //   83: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   86: istore 9
    //   88: aload_2
    //   89: ldc_w 307
    //   92: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   95: istore 10
    //   97: aload_2
    //   98: ldc_w 309
    //   101: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   104: istore 11
    //   106: aload_2
    //   107: ldc_w 311
    //   110: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   113: istore 12
    //   115: aload_2
    //   116: ldc_w 313
    //   119: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   122: istore 13
    //   124: aload_2
    //   125: ldc_w 315
    //   128: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   131: istore 14
    //   133: aload_2
    //   134: ldc_w 317
    //   137: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   140: istore 15
    //   142: aload_2
    //   143: ldc_w 319
    //   146: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   149: istore 16
    //   151: aload_2
    //   152: ldc_w 321
    //   155: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   158: istore 17
    //   160: aload_2
    //   161: ldc_w 323
    //   164: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   167: istore 18
    //   169: aload_2
    //   170: ldc_w 325
    //   173: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   176: istore 19
    //   178: aload_2
    //   179: ldc_w 327
    //   182: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   185: istore 20
    //   187: aload_2
    //   188: ldc_w 329
    //   191: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   194: istore 21
    //   196: aload_2
    //   197: ldc_w 331
    //   200: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   203: istore 22
    //   205: aload_2
    //   206: ldc_w 333
    //   209: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   212: istore 23
    //   214: aload_2
    //   215: ldc_w 335
    //   218: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   221: istore 24
    //   223: aload_2
    //   224: ldc_w 337
    //   227: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   230: istore 25
    //   232: aload_2
    //   233: ldc_w 339
    //   236: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   239: istore 26
    //   241: new 204	java/util/ArrayList
    //   244: astore 27
    //   246: aload 27
    //   248: aload_2
    //   249: invokeinterface 270 1 0
    //   254: invokespecial 271	java/util/ArrayList:<init>	(I)V
    //   257: aload_2
    //   258: invokeinterface 190 1 0
    //   263: ifeq +444 -> 707
    //   266: aload_2
    //   267: iload 11
    //   269: invokeinterface 198 2 0
    //   274: astore 28
    //   276: aload_2
    //   277: iload 13
    //   279: invokeinterface 198 2 0
    //   284: astore 29
    //   286: new 341	androidx/work/Constraints
    //   289: astore 30
    //   291: aload 30
    //   293: invokespecial 342	androidx/work/Constraints:<init>	()V
    //   296: aload 30
    //   298: aload_2
    //   299: iload_3
    //   300: invokeinterface 346 2 0
    //   305: invokestatic 352	androidx/work/impl/model/WorkTypeConverters:intToNetworkType	(I)Landroidx/work/NetworkType;
    //   308: invokevirtual 356	androidx/work/Constraints:setRequiredNetworkType	(Landroidx/work/NetworkType;)V
    //   311: aload_2
    //   312: iload 4
    //   314: invokeinterface 346 2 0
    //   319: ifeq +9 -> 328
    //   322: iconst_1
    //   323: istore 31
    //   325: goto +6 -> 331
    //   328: iconst_0
    //   329: istore 31
    //   331: aload 30
    //   333: iload 31
    //   335: invokevirtual 360	androidx/work/Constraints:setRequiresCharging	(Z)V
    //   338: aload_2
    //   339: iload 5
    //   341: invokeinterface 346 2 0
    //   346: ifeq +9 -> 355
    //   349: iconst_1
    //   350: istore 31
    //   352: goto +6 -> 358
    //   355: iconst_0
    //   356: istore 31
    //   358: aload 30
    //   360: iload 31
    //   362: invokevirtual 363	androidx/work/Constraints:setRequiresDeviceIdle	(Z)V
    //   365: aload_2
    //   366: iload 6
    //   368: invokeinterface 346 2 0
    //   373: ifeq +9 -> 382
    //   376: iconst_1
    //   377: istore 31
    //   379: goto +6 -> 385
    //   382: iconst_0
    //   383: istore 31
    //   385: aload 30
    //   387: iload 31
    //   389: invokevirtual 366	androidx/work/Constraints:setRequiresBatteryNotLow	(Z)V
    //   392: aload_2
    //   393: iload 7
    //   395: invokeinterface 346 2 0
    //   400: ifeq +9 -> 409
    //   403: iconst_1
    //   404: istore 31
    //   406: goto +6 -> 412
    //   409: iconst_0
    //   410: istore 31
    //   412: aload 30
    //   414: iload 31
    //   416: invokevirtual 369	androidx/work/Constraints:setRequiresStorageNotLow	(Z)V
    //   419: aload 30
    //   421: aload_2
    //   422: iload 8
    //   424: invokeinterface 373 2 0
    //   429: invokevirtual 377	androidx/work/Constraints:setTriggerContentUpdateDelay	(J)V
    //   432: aload 30
    //   434: aload_2
    //   435: iload 9
    //   437: invokeinterface 373 2 0
    //   442: invokevirtual 380	androidx/work/Constraints:setTriggerMaxContentDelay	(J)V
    //   445: aload 30
    //   447: aload_2
    //   448: iload 10
    //   450: invokeinterface 208 2 0
    //   455: invokestatic 384	androidx/work/impl/model/WorkTypeConverters:byteArrayToContentUriTriggers	([B)Landroidx/work/ContentUriTriggers;
    //   458: invokevirtual 388	androidx/work/Constraints:setContentUriTriggers	(Landroidx/work/ContentUriTriggers;)V
    //   461: new 390	androidx/work/impl/model/WorkSpec
    //   464: astore 32
    //   466: aload 32
    //   468: aload 28
    //   470: aload 29
    //   472: invokespecial 393	androidx/work/impl/model/WorkSpec:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   475: aload 32
    //   477: aload_2
    //   478: iload 12
    //   480: invokeinterface 346 2 0
    //   485: invokestatic 397	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
    //   488: putfield 400	androidx/work/impl/model/WorkSpec:state	Landroidx/work/WorkInfo$State;
    //   491: aload 32
    //   493: aload_2
    //   494: iload 14
    //   496: invokeinterface 198 2 0
    //   501: putfield 404	androidx/work/impl/model/WorkSpec:inputMergerClassName	Ljava/lang/String;
    //   504: aload 32
    //   506: aload_2
    //   507: iload 15
    //   509: invokeinterface 208 2 0
    //   514: invokestatic 214	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   517: putfield 407	androidx/work/impl/model/WorkSpec:input	Landroidx/work/Data;
    //   520: aload 32
    //   522: aload_2
    //   523: iload 16
    //   525: invokeinterface 208 2 0
    //   530: invokestatic 214	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   533: putfield 409	androidx/work/impl/model/WorkSpec:output	Landroidx/work/Data;
    //   536: aload 32
    //   538: aload_2
    //   539: iload 17
    //   541: invokeinterface 373 2 0
    //   546: putfield 413	androidx/work/impl/model/WorkSpec:initialDelay	J
    //   549: aload 32
    //   551: aload_2
    //   552: iload 18
    //   554: invokeinterface 373 2 0
    //   559: putfield 416	androidx/work/impl/model/WorkSpec:intervalDuration	J
    //   562: aload 32
    //   564: aload_2
    //   565: iload 19
    //   567: invokeinterface 373 2 0
    //   572: putfield 419	androidx/work/impl/model/WorkSpec:flexDuration	J
    //   575: aload 32
    //   577: aload_2
    //   578: iload 20
    //   580: invokeinterface 346 2 0
    //   585: putfield 423	androidx/work/impl/model/WorkSpec:runAttemptCount	I
    //   588: aload_2
    //   589: iload 21
    //   591: invokeinterface 346 2 0
    //   596: istore 33
    //   598: aload 32
    //   600: iload 33
    //   602: invokestatic 427	androidx/work/impl/model/WorkTypeConverters:intToBackoffPolicy	(I)Landroidx/work/BackoffPolicy;
    //   605: putfield 431	androidx/work/impl/model/WorkSpec:backoffPolicy	Landroidx/work/BackoffPolicy;
    //   608: aload 32
    //   610: aload_2
    //   611: iload 22
    //   613: invokeinterface 373 2 0
    //   618: putfield 434	androidx/work/impl/model/WorkSpec:backoffDelayDuration	J
    //   621: aload 32
    //   623: aload_2
    //   624: iload 23
    //   626: invokeinterface 373 2 0
    //   631: putfield 437	androidx/work/impl/model/WorkSpec:periodStartTime	J
    //   634: aload 32
    //   636: aload_2
    //   637: iload 24
    //   639: invokeinterface 373 2 0
    //   644: putfield 440	androidx/work/impl/model/WorkSpec:minimumRetentionDuration	J
    //   647: aload 32
    //   649: aload_2
    //   650: iload 25
    //   652: invokeinterface 373 2 0
    //   657: putfield 443	androidx/work/impl/model/WorkSpec:scheduleRequestedAt	J
    //   660: aload_2
    //   661: iload 26
    //   663: invokeinterface 346 2 0
    //   668: ifeq +9 -> 677
    //   671: iconst_1
    //   672: istore 31
    //   674: goto +6 -> 680
    //   677: iconst_0
    //   678: istore 31
    //   680: aload 32
    //   682: iload 31
    //   684: putfield 447	androidx/work/impl/model/WorkSpec:runInForeground	Z
    //   687: aload 32
    //   689: aload 30
    //   691: putfield 451	androidx/work/impl/model/WorkSpec:constraints	Landroidx/work/Constraints;
    //   694: aload 27
    //   696: aload 32
    //   698: invokeinterface 274 2 0
    //   703: pop
    //   704: goto -447 -> 257
    //   707: aload_2
    //   708: invokeinterface 187 1 0
    //   713: aload_1
    //   714: invokevirtual 276	androidx/room/RoomSQLiteQuery:release	()V
    //   717: aload 27
    //   719: areturn
    //   720: astore 27
    //   722: goto +5 -> 727
    //   725: astore 27
    //   727: aload_2
    //   728: invokeinterface 187 1 0
    //   733: aload_1
    //   734: invokevirtual 276	androidx/room/RoomSQLiteQuery:release	()V
    //   737: aload 27
    //   739: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	740	0	this	WorkSpecDao_Impl
    //   7	727	1	localRoomSQLiteQuery	RoomSQLiteQuery
    //   25	703	2	localCursor	Cursor
    //   33	267	3	i	int
    //   41	272	4	j	int
    //   50	290	5	k	int
    //   59	308	6	m	int
    //   68	326	7	n	int
    //   77	346	8	i1	int
    //   86	350	9	i2	int
    //   95	354	10	i3	int
    //   104	164	11	i4	int
    //   113	366	12	i5	int
    //   122	156	13	i6	int
    //   131	364	14	i7	int
    //   140	368	15	i8	int
    //   149	375	16	i9	int
    //   158	382	17	i10	int
    //   167	386	18	i11	int
    //   176	390	19	i12	int
    //   185	394	20	i13	int
    //   194	396	21	i14	int
    //   203	409	22	i15	int
    //   212	413	23	i16	int
    //   221	417	24	i17	int
    //   230	421	25	i18	int
    //   239	423	26	i19	int
    //   244	474	27	localArrayList	ArrayList
    //   720	1	27	localObject1	Object
    //   725	13	27	localObject2	Object
    //   274	195	28	str1	String
    //   284	187	29	str2	String
    //   289	401	30	localConstraints	Constraints
    //   323	360	31	bool	boolean
    //   464	233	32	localWorkSpec	WorkSpec
    //   596	5	33	i20	int
    // Exception table:
    //   from	to	target	type
    //   151	246	720	finally
    //   246	257	720	finally
    //   257	322	720	finally
    //   331	349	720	finally
    //   358	376	720	finally
    //   385	403	720	finally
    //   412	536	720	finally
    //   536	549	720	finally
    //   549	562	720	finally
    //   562	598	720	finally
    //   598	608	720	finally
    //   608	621	720	finally
    //   621	634	720	finally
    //   634	647	720	finally
    //   647	671	720	finally
    //   680	704	720	finally
    //   26	151	725	finally
  }
  
  /* Error */
  public List<WorkSpec> getScheduledWork()
  {
    // Byte code:
    //   0: ldc_w 464
    //   3: iconst_0
    //   4: invokestatic 146	androidx/room/RoomSQLiteQuery:acquire	(Ljava/lang/String;I)Landroidx/room/RoomSQLiteQuery;
    //   7: astore_1
    //   8: aload_0
    //   9: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   12: invokevirtual 238	androidx/room/RoomDatabase:assertNotSuspendingTransaction	()V
    //   15: aload_0
    //   16: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   19: aload_1
    //   20: iconst_0
    //   21: aconst_null
    //   22: invokestatic 174	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   25: astore_2
    //   26: aload_2
    //   27: ldc_w 290
    //   30: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   33: istore_3
    //   34: aload_2
    //   35: ldc_w 295
    //   38: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   41: istore 4
    //   43: aload_2
    //   44: ldc_w 297
    //   47: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   50: istore 5
    //   52: aload_2
    //   53: ldc_w 299
    //   56: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   59: istore 6
    //   61: aload_2
    //   62: ldc_w 301
    //   65: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   68: istore 7
    //   70: aload_2
    //   71: ldc_w 303
    //   74: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   77: istore 8
    //   79: aload_2
    //   80: ldc_w 305
    //   83: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   86: istore 9
    //   88: aload_2
    //   89: ldc_w 307
    //   92: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   95: istore 10
    //   97: aload_2
    //   98: ldc_w 309
    //   101: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   104: istore 11
    //   106: aload_2
    //   107: ldc_w 311
    //   110: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   113: istore 12
    //   115: aload_2
    //   116: ldc_w 313
    //   119: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   122: istore 13
    //   124: aload_2
    //   125: ldc_w 315
    //   128: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   131: istore 14
    //   133: aload_2
    //   134: ldc_w 317
    //   137: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   140: istore 15
    //   142: aload_2
    //   143: ldc_w 319
    //   146: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   149: istore 16
    //   151: aload_2
    //   152: ldc_w 321
    //   155: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   158: istore 17
    //   160: aload_2
    //   161: ldc_w 323
    //   164: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   167: istore 18
    //   169: aload_2
    //   170: ldc_w 325
    //   173: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   176: istore 19
    //   178: aload_2
    //   179: ldc_w 327
    //   182: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   185: istore 20
    //   187: aload_2
    //   188: ldc_w 329
    //   191: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   194: istore 21
    //   196: aload_2
    //   197: ldc_w 331
    //   200: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   203: istore 22
    //   205: aload_2
    //   206: ldc_w 333
    //   209: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   212: istore 23
    //   214: aload_2
    //   215: ldc_w 335
    //   218: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   221: istore 24
    //   223: aload_2
    //   224: ldc_w 337
    //   227: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   230: istore 25
    //   232: aload_2
    //   233: ldc_w 339
    //   236: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   239: istore 26
    //   241: new 204	java/util/ArrayList
    //   244: astore 27
    //   246: aload 27
    //   248: aload_2
    //   249: invokeinterface 270 1 0
    //   254: invokespecial 271	java/util/ArrayList:<init>	(I)V
    //   257: aload_2
    //   258: invokeinterface 190 1 0
    //   263: ifeq +444 -> 707
    //   266: aload_2
    //   267: iload 11
    //   269: invokeinterface 198 2 0
    //   274: astore 28
    //   276: aload_2
    //   277: iload 13
    //   279: invokeinterface 198 2 0
    //   284: astore 29
    //   286: new 341	androidx/work/Constraints
    //   289: astore 30
    //   291: aload 30
    //   293: invokespecial 342	androidx/work/Constraints:<init>	()V
    //   296: aload 30
    //   298: aload_2
    //   299: iload_3
    //   300: invokeinterface 346 2 0
    //   305: invokestatic 352	androidx/work/impl/model/WorkTypeConverters:intToNetworkType	(I)Landroidx/work/NetworkType;
    //   308: invokevirtual 356	androidx/work/Constraints:setRequiredNetworkType	(Landroidx/work/NetworkType;)V
    //   311: aload_2
    //   312: iload 4
    //   314: invokeinterface 346 2 0
    //   319: ifeq +9 -> 328
    //   322: iconst_1
    //   323: istore 31
    //   325: goto +6 -> 331
    //   328: iconst_0
    //   329: istore 31
    //   331: aload 30
    //   333: iload 31
    //   335: invokevirtual 360	androidx/work/Constraints:setRequiresCharging	(Z)V
    //   338: aload_2
    //   339: iload 5
    //   341: invokeinterface 346 2 0
    //   346: ifeq +9 -> 355
    //   349: iconst_1
    //   350: istore 31
    //   352: goto +6 -> 358
    //   355: iconst_0
    //   356: istore 31
    //   358: aload 30
    //   360: iload 31
    //   362: invokevirtual 363	androidx/work/Constraints:setRequiresDeviceIdle	(Z)V
    //   365: aload_2
    //   366: iload 6
    //   368: invokeinterface 346 2 0
    //   373: ifeq +9 -> 382
    //   376: iconst_1
    //   377: istore 31
    //   379: goto +6 -> 385
    //   382: iconst_0
    //   383: istore 31
    //   385: aload 30
    //   387: iload 31
    //   389: invokevirtual 366	androidx/work/Constraints:setRequiresBatteryNotLow	(Z)V
    //   392: aload_2
    //   393: iload 7
    //   395: invokeinterface 346 2 0
    //   400: ifeq +9 -> 409
    //   403: iconst_1
    //   404: istore 31
    //   406: goto +6 -> 412
    //   409: iconst_0
    //   410: istore 31
    //   412: aload 30
    //   414: iload 31
    //   416: invokevirtual 369	androidx/work/Constraints:setRequiresStorageNotLow	(Z)V
    //   419: aload 30
    //   421: aload_2
    //   422: iload 8
    //   424: invokeinterface 373 2 0
    //   429: invokevirtual 377	androidx/work/Constraints:setTriggerContentUpdateDelay	(J)V
    //   432: aload 30
    //   434: aload_2
    //   435: iload 9
    //   437: invokeinterface 373 2 0
    //   442: invokevirtual 380	androidx/work/Constraints:setTriggerMaxContentDelay	(J)V
    //   445: aload 30
    //   447: aload_2
    //   448: iload 10
    //   450: invokeinterface 208 2 0
    //   455: invokestatic 384	androidx/work/impl/model/WorkTypeConverters:byteArrayToContentUriTriggers	([B)Landroidx/work/ContentUriTriggers;
    //   458: invokevirtual 388	androidx/work/Constraints:setContentUriTriggers	(Landroidx/work/ContentUriTriggers;)V
    //   461: new 390	androidx/work/impl/model/WorkSpec
    //   464: astore 32
    //   466: aload 32
    //   468: aload 28
    //   470: aload 29
    //   472: invokespecial 393	androidx/work/impl/model/WorkSpec:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   475: aload 32
    //   477: aload_2
    //   478: iload 12
    //   480: invokeinterface 346 2 0
    //   485: invokestatic 397	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
    //   488: putfield 400	androidx/work/impl/model/WorkSpec:state	Landroidx/work/WorkInfo$State;
    //   491: aload 32
    //   493: aload_2
    //   494: iload 14
    //   496: invokeinterface 198 2 0
    //   501: putfield 404	androidx/work/impl/model/WorkSpec:inputMergerClassName	Ljava/lang/String;
    //   504: aload 32
    //   506: aload_2
    //   507: iload 15
    //   509: invokeinterface 208 2 0
    //   514: invokestatic 214	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   517: putfield 407	androidx/work/impl/model/WorkSpec:input	Landroidx/work/Data;
    //   520: aload 32
    //   522: aload_2
    //   523: iload 16
    //   525: invokeinterface 208 2 0
    //   530: invokestatic 214	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   533: putfield 409	androidx/work/impl/model/WorkSpec:output	Landroidx/work/Data;
    //   536: aload 32
    //   538: aload_2
    //   539: iload 17
    //   541: invokeinterface 373 2 0
    //   546: putfield 413	androidx/work/impl/model/WorkSpec:initialDelay	J
    //   549: aload 32
    //   551: aload_2
    //   552: iload 18
    //   554: invokeinterface 373 2 0
    //   559: putfield 416	androidx/work/impl/model/WorkSpec:intervalDuration	J
    //   562: aload 32
    //   564: aload_2
    //   565: iload 19
    //   567: invokeinterface 373 2 0
    //   572: putfield 419	androidx/work/impl/model/WorkSpec:flexDuration	J
    //   575: aload 32
    //   577: aload_2
    //   578: iload 20
    //   580: invokeinterface 346 2 0
    //   585: putfield 423	androidx/work/impl/model/WorkSpec:runAttemptCount	I
    //   588: aload_2
    //   589: iload 21
    //   591: invokeinterface 346 2 0
    //   596: istore 33
    //   598: aload 32
    //   600: iload 33
    //   602: invokestatic 427	androidx/work/impl/model/WorkTypeConverters:intToBackoffPolicy	(I)Landroidx/work/BackoffPolicy;
    //   605: putfield 431	androidx/work/impl/model/WorkSpec:backoffPolicy	Landroidx/work/BackoffPolicy;
    //   608: aload 32
    //   610: aload_2
    //   611: iload 22
    //   613: invokeinterface 373 2 0
    //   618: putfield 434	androidx/work/impl/model/WorkSpec:backoffDelayDuration	J
    //   621: aload 32
    //   623: aload_2
    //   624: iload 23
    //   626: invokeinterface 373 2 0
    //   631: putfield 437	androidx/work/impl/model/WorkSpec:periodStartTime	J
    //   634: aload 32
    //   636: aload_2
    //   637: iload 24
    //   639: invokeinterface 373 2 0
    //   644: putfield 440	androidx/work/impl/model/WorkSpec:minimumRetentionDuration	J
    //   647: aload 32
    //   649: aload_2
    //   650: iload 25
    //   652: invokeinterface 373 2 0
    //   657: putfield 443	androidx/work/impl/model/WorkSpec:scheduleRequestedAt	J
    //   660: aload_2
    //   661: iload 26
    //   663: invokeinterface 346 2 0
    //   668: ifeq +9 -> 677
    //   671: iconst_1
    //   672: istore 31
    //   674: goto +6 -> 680
    //   677: iconst_0
    //   678: istore 31
    //   680: aload 32
    //   682: iload 31
    //   684: putfield 447	androidx/work/impl/model/WorkSpec:runInForeground	Z
    //   687: aload 32
    //   689: aload 30
    //   691: putfield 451	androidx/work/impl/model/WorkSpec:constraints	Landroidx/work/Constraints;
    //   694: aload 27
    //   696: aload 32
    //   698: invokeinterface 274 2 0
    //   703: pop
    //   704: goto -447 -> 257
    //   707: aload_2
    //   708: invokeinterface 187 1 0
    //   713: aload_1
    //   714: invokevirtual 276	androidx/room/RoomSQLiteQuery:release	()V
    //   717: aload 27
    //   719: areturn
    //   720: astore 28
    //   722: goto +5 -> 727
    //   725: astore 28
    //   727: aload_2
    //   728: invokeinterface 187 1 0
    //   733: aload_1
    //   734: invokevirtual 276	androidx/room/RoomSQLiteQuery:release	()V
    //   737: aload 28
    //   739: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	740	0	this	WorkSpecDao_Impl
    //   7	727	1	localRoomSQLiteQuery	RoomSQLiteQuery
    //   25	703	2	localCursor	Cursor
    //   33	267	3	i	int
    //   41	272	4	j	int
    //   50	290	5	k	int
    //   59	308	6	m	int
    //   68	326	7	n	int
    //   77	346	8	i1	int
    //   86	350	9	i2	int
    //   95	354	10	i3	int
    //   104	164	11	i4	int
    //   113	366	12	i5	int
    //   122	156	13	i6	int
    //   131	364	14	i7	int
    //   140	368	15	i8	int
    //   149	375	16	i9	int
    //   158	382	17	i10	int
    //   167	386	18	i11	int
    //   176	390	19	i12	int
    //   185	394	20	i13	int
    //   194	396	21	i14	int
    //   203	409	22	i15	int
    //   212	413	23	i16	int
    //   221	417	24	i17	int
    //   230	421	25	i18	int
    //   239	423	26	i19	int
    //   244	474	27	localArrayList	ArrayList
    //   274	195	28	str1	String
    //   720	1	28	localObject1	Object
    //   725	13	28	localObject2	Object
    //   284	187	29	str2	String
    //   289	401	30	localConstraints	Constraints
    //   323	360	31	bool	boolean
    //   464	233	32	localWorkSpec	WorkSpec
    //   596	5	33	i20	int
    // Exception table:
    //   from	to	target	type
    //   151	246	720	finally
    //   246	257	720	finally
    //   257	322	720	finally
    //   331	349	720	finally
    //   358	376	720	finally
    //   385	403	720	finally
    //   412	536	720	finally
    //   536	549	720	finally
    //   549	562	720	finally
    //   562	598	720	finally
    //   598	608	720	finally
    //   608	621	720	finally
    //   621	634	720	finally
    //   634	647	720	finally
    //   647	671	720	finally
    //   680	704	720	finally
    //   26	151	725	finally
  }
  
  public WorkInfo.State getState(String paramString)
  {
    RoomSQLiteQuery localRoomSQLiteQuery = RoomSQLiteQuery.acquire("SELECT state FROM workspec WHERE id=?", 1);
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
      if (((Cursor)localObject).moveToFirst()) {
        paramString = WorkTypeConverters.intToState(((Cursor)localObject).getInt(0));
      }
      return paramString;
    }
    finally
    {
      ((Cursor)localObject).close();
      localRoomSQLiteQuery.release();
    }
  }
  
  public List<String> getUnfinishedWorkWithName(String paramString)
  {
    RoomSQLiteQuery localRoomSQLiteQuery = RoomSQLiteQuery.acquire("SELECT id FROM workspec WHERE state NOT IN (2, 3, 5) AND id IN (SELECT work_spec_id FROM workname WHERE name=?)", 1);
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
  
  public List<String> getUnfinishedWorkWithTag(String paramString)
  {
    RoomSQLiteQuery localRoomSQLiteQuery = RoomSQLiteQuery.acquire("SELECT id FROM workspec WHERE state NOT IN (2, 3, 5) AND id IN (SELECT work_spec_id FROM worktag WHERE tag=?)", 1);
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
  
  /* Error */
  public WorkSpec getWorkSpec(String paramString)
  {
    // Byte code:
    //   0: ldc_w 482
    //   3: iconst_1
    //   4: invokestatic 146	androidx/room/RoomSQLiteQuery:acquire	(Ljava/lang/String;I)Landroidx/room/RoomSQLiteQuery;
    //   7: astore_2
    //   8: aload_1
    //   9: ifnonnull +11 -> 20
    //   12: aload_2
    //   13: iconst_1
    //   14: invokevirtual 164	androidx/room/RoomSQLiteQuery:bindNull	(I)V
    //   17: goto +9 -> 26
    //   20: aload_2
    //   21: iconst_1
    //   22: aload_1
    //   23: invokevirtual 168	androidx/room/RoomSQLiteQuery:bindString	(ILjava/lang/String;)V
    //   26: aload_0
    //   27: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   30: invokevirtual 238	androidx/room/RoomDatabase:assertNotSuspendingTransaction	()V
    //   33: aload_0
    //   34: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   37: aload_2
    //   38: iconst_0
    //   39: aconst_null
    //   40: invokestatic 174	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   43: astore_3
    //   44: aload_3
    //   45: ldc_w 290
    //   48: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   51: istore 4
    //   53: aload_3
    //   54: ldc_w 295
    //   57: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   60: istore 5
    //   62: aload_3
    //   63: ldc_w 297
    //   66: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   69: istore 6
    //   71: aload_3
    //   72: ldc_w 299
    //   75: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   78: istore 7
    //   80: aload_3
    //   81: ldc_w 301
    //   84: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   87: istore 8
    //   89: aload_3
    //   90: ldc_w 303
    //   93: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   96: istore 9
    //   98: aload_3
    //   99: ldc_w 305
    //   102: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   105: istore 10
    //   107: aload_3
    //   108: ldc_w 307
    //   111: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   114: istore 11
    //   116: aload_3
    //   117: ldc_w 309
    //   120: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   123: istore 12
    //   125: aload_3
    //   126: ldc_w 311
    //   129: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   132: istore 13
    //   134: aload_3
    //   135: ldc_w 313
    //   138: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   141: istore 14
    //   143: aload_3
    //   144: ldc_w 315
    //   147: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   150: istore 15
    //   152: aload_3
    //   153: ldc_w 317
    //   156: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   159: istore 16
    //   161: aload_3
    //   162: ldc_w 319
    //   165: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   168: istore 17
    //   170: aload_3
    //   171: ldc_w 321
    //   174: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   177: istore 18
    //   179: aload_3
    //   180: ldc_w 323
    //   183: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   186: istore 19
    //   188: aload_3
    //   189: ldc_w 325
    //   192: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   195: istore 20
    //   197: aload_3
    //   198: ldc_w 327
    //   201: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   204: istore 21
    //   206: aload_3
    //   207: ldc_w 329
    //   210: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   213: istore 22
    //   215: aload_3
    //   216: ldc_w 331
    //   219: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   222: istore 23
    //   224: aload_3
    //   225: ldc_w 333
    //   228: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   231: istore 24
    //   233: aload_3
    //   234: ldc_w 335
    //   237: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   240: istore 25
    //   242: aload_3
    //   243: ldc_w 337
    //   246: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   249: istore 26
    //   251: aload_3
    //   252: ldc_w 339
    //   255: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   258: istore 27
    //   260: aload_3
    //   261: invokeinterface 471 1 0
    //   266: ifeq +414 -> 680
    //   269: aload_3
    //   270: iload 12
    //   272: invokeinterface 198 2 0
    //   277: astore 28
    //   279: aload_3
    //   280: iload 14
    //   282: invokeinterface 198 2 0
    //   287: astore 29
    //   289: new 341	androidx/work/Constraints
    //   292: astore 30
    //   294: aload 30
    //   296: invokespecial 342	androidx/work/Constraints:<init>	()V
    //   299: aload 30
    //   301: aload_3
    //   302: iload 4
    //   304: invokeinterface 346 2 0
    //   309: invokestatic 352	androidx/work/impl/model/WorkTypeConverters:intToNetworkType	(I)Landroidx/work/NetworkType;
    //   312: invokevirtual 356	androidx/work/Constraints:setRequiredNetworkType	(Landroidx/work/NetworkType;)V
    //   315: aload_3
    //   316: iload 5
    //   318: invokeinterface 346 2 0
    //   323: ifeq +9 -> 332
    //   326: iconst_1
    //   327: istore 31
    //   329: goto +6 -> 335
    //   332: iconst_0
    //   333: istore 31
    //   335: aload 30
    //   337: iload 31
    //   339: invokevirtual 360	androidx/work/Constraints:setRequiresCharging	(Z)V
    //   342: aload_3
    //   343: iload 6
    //   345: invokeinterface 346 2 0
    //   350: ifeq +9 -> 359
    //   353: iconst_1
    //   354: istore 31
    //   356: goto +6 -> 362
    //   359: iconst_0
    //   360: istore 31
    //   362: aload 30
    //   364: iload 31
    //   366: invokevirtual 363	androidx/work/Constraints:setRequiresDeviceIdle	(Z)V
    //   369: aload_3
    //   370: iload 7
    //   372: invokeinterface 346 2 0
    //   377: ifeq +9 -> 386
    //   380: iconst_1
    //   381: istore 31
    //   383: goto +6 -> 389
    //   386: iconst_0
    //   387: istore 31
    //   389: aload 30
    //   391: iload 31
    //   393: invokevirtual 366	androidx/work/Constraints:setRequiresBatteryNotLow	(Z)V
    //   396: aload_3
    //   397: iload 8
    //   399: invokeinterface 346 2 0
    //   404: ifeq +9 -> 413
    //   407: iconst_1
    //   408: istore 31
    //   410: goto +6 -> 416
    //   413: iconst_0
    //   414: istore 31
    //   416: aload 30
    //   418: iload 31
    //   420: invokevirtual 369	androidx/work/Constraints:setRequiresStorageNotLow	(Z)V
    //   423: aload 30
    //   425: aload_3
    //   426: iload 9
    //   428: invokeinterface 373 2 0
    //   433: invokevirtual 377	androidx/work/Constraints:setTriggerContentUpdateDelay	(J)V
    //   436: aload 30
    //   438: aload_3
    //   439: iload 10
    //   441: invokeinterface 373 2 0
    //   446: invokevirtual 380	androidx/work/Constraints:setTriggerMaxContentDelay	(J)V
    //   449: aload 30
    //   451: aload_3
    //   452: iload 11
    //   454: invokeinterface 208 2 0
    //   459: invokestatic 384	androidx/work/impl/model/WorkTypeConverters:byteArrayToContentUriTriggers	([B)Landroidx/work/ContentUriTriggers;
    //   462: invokevirtual 388	androidx/work/Constraints:setContentUriTriggers	(Landroidx/work/ContentUriTriggers;)V
    //   465: new 390	androidx/work/impl/model/WorkSpec
    //   468: astore_1
    //   469: aload_1
    //   470: aload 28
    //   472: aload 29
    //   474: invokespecial 393	androidx/work/impl/model/WorkSpec:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   477: aload_1
    //   478: aload_3
    //   479: iload 13
    //   481: invokeinterface 346 2 0
    //   486: invokestatic 397	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
    //   489: putfield 400	androidx/work/impl/model/WorkSpec:state	Landroidx/work/WorkInfo$State;
    //   492: aload_1
    //   493: aload_3
    //   494: iload 15
    //   496: invokeinterface 198 2 0
    //   501: putfield 404	androidx/work/impl/model/WorkSpec:inputMergerClassName	Ljava/lang/String;
    //   504: aload_1
    //   505: aload_3
    //   506: iload 16
    //   508: invokeinterface 208 2 0
    //   513: invokestatic 214	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   516: putfield 407	androidx/work/impl/model/WorkSpec:input	Landroidx/work/Data;
    //   519: aload_1
    //   520: aload_3
    //   521: iload 17
    //   523: invokeinterface 208 2 0
    //   528: invokestatic 214	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   531: putfield 409	androidx/work/impl/model/WorkSpec:output	Landroidx/work/Data;
    //   534: aload_1
    //   535: aload_3
    //   536: iload 18
    //   538: invokeinterface 373 2 0
    //   543: putfield 413	androidx/work/impl/model/WorkSpec:initialDelay	J
    //   546: aload_1
    //   547: aload_3
    //   548: iload 19
    //   550: invokeinterface 373 2 0
    //   555: putfield 416	androidx/work/impl/model/WorkSpec:intervalDuration	J
    //   558: aload_1
    //   559: aload_3
    //   560: iload 20
    //   562: invokeinterface 373 2 0
    //   567: putfield 419	androidx/work/impl/model/WorkSpec:flexDuration	J
    //   570: aload_1
    //   571: aload_3
    //   572: iload 21
    //   574: invokeinterface 346 2 0
    //   579: putfield 423	androidx/work/impl/model/WorkSpec:runAttemptCount	I
    //   582: aload_1
    //   583: aload_3
    //   584: iload 22
    //   586: invokeinterface 346 2 0
    //   591: invokestatic 427	androidx/work/impl/model/WorkTypeConverters:intToBackoffPolicy	(I)Landroidx/work/BackoffPolicy;
    //   594: putfield 431	androidx/work/impl/model/WorkSpec:backoffPolicy	Landroidx/work/BackoffPolicy;
    //   597: aload_1
    //   598: aload_3
    //   599: iload 23
    //   601: invokeinterface 373 2 0
    //   606: putfield 434	androidx/work/impl/model/WorkSpec:backoffDelayDuration	J
    //   609: aload_1
    //   610: aload_3
    //   611: iload 24
    //   613: invokeinterface 373 2 0
    //   618: putfield 437	androidx/work/impl/model/WorkSpec:periodStartTime	J
    //   621: aload_1
    //   622: aload_3
    //   623: iload 25
    //   625: invokeinterface 373 2 0
    //   630: putfield 440	androidx/work/impl/model/WorkSpec:minimumRetentionDuration	J
    //   633: aload_1
    //   634: aload_3
    //   635: iload 26
    //   637: invokeinterface 373 2 0
    //   642: putfield 443	androidx/work/impl/model/WorkSpec:scheduleRequestedAt	J
    //   645: aload_3
    //   646: iload 27
    //   648: invokeinterface 346 2 0
    //   653: ifeq +9 -> 662
    //   656: iconst_1
    //   657: istore 31
    //   659: goto +6 -> 665
    //   662: iconst_0
    //   663: istore 31
    //   665: aload_1
    //   666: iload 31
    //   668: putfield 447	androidx/work/impl/model/WorkSpec:runInForeground	Z
    //   671: aload_1
    //   672: aload 30
    //   674: putfield 451	androidx/work/impl/model/WorkSpec:constraints	Landroidx/work/Constraints;
    //   677: goto +5 -> 682
    //   680: aconst_null
    //   681: astore_1
    //   682: aload_3
    //   683: invokeinterface 187 1 0
    //   688: aload_2
    //   689: invokevirtual 276	androidx/room/RoomSQLiteQuery:release	()V
    //   692: aload_1
    //   693: areturn
    //   694: astore_1
    //   695: goto +4 -> 699
    //   698: astore_1
    //   699: aload_3
    //   700: invokeinterface 187 1 0
    //   705: aload_2
    //   706: invokevirtual 276	androidx/room/RoomSQLiteQuery:release	()V
    //   709: aload_1
    //   710: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	711	0	this	WorkSpecDao_Impl
    //   0	711	1	paramString	String
    //   7	699	2	localRoomSQLiteQuery	RoomSQLiteQuery
    //   43	657	3	localCursor	Cursor
    //   51	252	4	i	int
    //   60	257	5	j	int
    //   69	275	6	k	int
    //   78	293	7	m	int
    //   87	311	8	n	int
    //   96	331	9	i1	int
    //   105	335	10	i2	int
    //   114	339	11	i3	int
    //   123	148	12	i4	int
    //   132	348	13	i5	int
    //   141	140	14	i6	int
    //   150	345	15	i7	int
    //   159	348	16	i8	int
    //   168	354	17	i9	int
    //   177	360	18	i10	int
    //   186	363	19	i11	int
    //   195	366	20	i12	int
    //   204	369	21	i13	int
    //   213	372	22	i14	int
    //   222	378	23	i15	int
    //   231	381	24	i16	int
    //   240	384	25	i17	int
    //   249	387	26	i18	int
    //   258	389	27	i19	int
    //   277	194	28	str1	String
    //   287	186	29	str2	String
    //   292	381	30	localConstraints	Constraints
    //   327	340	31	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   170	326	694	finally
    //   335	353	694	finally
    //   362	380	694	finally
    //   389	407	694	finally
    //   416	656	694	finally
    //   665	677	694	finally
    //   44	170	698	finally
  }
  
  public List<WorkSpec.IdAndState> getWorkSpecIdAndStatesForName(String paramString)
  {
    RoomSQLiteQuery localRoomSQLiteQuery = RoomSQLiteQuery.acquire("SELECT id, state FROM workspec WHERE id IN (SELECT work_spec_id FROM workname WHERE name=?)", 1);
    if (paramString == null) {
      localRoomSQLiteQuery.bindNull(1);
    } else {
      localRoomSQLiteQuery.bindString(1, paramString);
    }
    this.__db.assertNotSuspendingTransaction();
    paramString = DBUtil.query(this.__db, localRoomSQLiteQuery, false, null);
    try
    {
      int i = CursorUtil.getColumnIndexOrThrow(paramString, "id");
      int j = CursorUtil.getColumnIndexOrThrow(paramString, "state");
      ArrayList localArrayList = new java/util/ArrayList;
      localArrayList.<init>(paramString.getCount());
      while (paramString.moveToNext())
      {
        WorkSpec.IdAndState localIdAndState = new androidx/work/impl/model/WorkSpec$IdAndState;
        localIdAndState.<init>();
        localIdAndState.id = paramString.getString(i);
        localIdAndState.state = WorkTypeConverters.intToState(paramString.getInt(j));
        localArrayList.add(localIdAndState);
      }
      return localArrayList;
    }
    finally
    {
      paramString.close();
      localRoomSQLiteQuery.release();
    }
  }
  
  /* Error */
  public WorkSpec[] getWorkSpecs(List<String> paramList)
  {
    // Byte code:
    //   0: invokestatic 121	androidx/room/util/StringUtil:newStringBuilder	()Ljava/lang/StringBuilder;
    //   3: astore_2
    //   4: aload_2
    //   5: ldc_w 496
    //   8: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   11: pop
    //   12: aload_2
    //   13: ldc_w 498
    //   16: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   19: pop
    //   20: aload_2
    //   21: ldc_w 500
    //   24: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   27: pop
    //   28: aload_1
    //   29: invokeinterface 501 1 0
    //   34: istore_3
    //   35: aload_2
    //   36: iload_3
    //   37: invokestatic 134	androidx/room/util/StringUtil:appendPlaceholders	(Ljava/lang/StringBuilder;I)V
    //   40: aload_2
    //   41: ldc -120
    //   43: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   46: pop
    //   47: aload_2
    //   48: invokevirtual 140	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   51: iload_3
    //   52: iconst_0
    //   53: iadd
    //   54: invokestatic 146	androidx/room/RoomSQLiteQuery:acquire	(Ljava/lang/String;I)Landroidx/room/RoomSQLiteQuery;
    //   57: astore_2
    //   58: aload_1
    //   59: invokeinterface 502 1 0
    //   64: astore_1
    //   65: iconst_1
    //   66: istore_3
    //   67: aload_1
    //   68: invokeinterface 155 1 0
    //   73: ifeq +40 -> 113
    //   76: aload_1
    //   77: invokeinterface 159 1 0
    //   82: checkcast 161	java/lang/String
    //   85: astore 4
    //   87: aload 4
    //   89: ifnonnull +11 -> 100
    //   92: aload_2
    //   93: iload_3
    //   94: invokevirtual 164	androidx/room/RoomSQLiteQuery:bindNull	(I)V
    //   97: goto +10 -> 107
    //   100: aload_2
    //   101: iload_3
    //   102: aload 4
    //   104: invokevirtual 168	androidx/room/RoomSQLiteQuery:bindString	(ILjava/lang/String;)V
    //   107: iinc 3 1
    //   110: goto -43 -> 67
    //   113: aload_0
    //   114: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   117: invokevirtual 238	androidx/room/RoomDatabase:assertNotSuspendingTransaction	()V
    //   120: aload_0
    //   121: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   124: aload_2
    //   125: iconst_0
    //   126: aconst_null
    //   127: invokestatic 174	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   130: astore 4
    //   132: aload 4
    //   134: ldc_w 290
    //   137: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   140: istore 5
    //   142: aload 4
    //   144: ldc_w 295
    //   147: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   150: istore 6
    //   152: aload 4
    //   154: ldc_w 297
    //   157: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   160: istore 7
    //   162: aload 4
    //   164: ldc_w 299
    //   167: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   170: istore 8
    //   172: aload 4
    //   174: ldc_w 301
    //   177: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   180: istore 9
    //   182: aload 4
    //   184: ldc_w 303
    //   187: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   190: istore 10
    //   192: aload 4
    //   194: ldc_w 305
    //   197: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   200: istore 11
    //   202: aload 4
    //   204: ldc_w 307
    //   207: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   210: istore 12
    //   212: aload 4
    //   214: ldc_w 309
    //   217: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   220: istore 13
    //   222: aload 4
    //   224: ldc_w 311
    //   227: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   230: istore 14
    //   232: aload 4
    //   234: ldc_w 313
    //   237: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   240: istore 15
    //   242: aload 4
    //   244: ldc_w 315
    //   247: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   250: istore 16
    //   252: aload 4
    //   254: ldc_w 317
    //   257: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   260: istore 17
    //   262: aload 4
    //   264: ldc_w 319
    //   267: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   270: istore 18
    //   272: aload 4
    //   274: ldc_w 321
    //   277: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   280: istore 19
    //   282: aload 4
    //   284: ldc_w 323
    //   287: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   290: istore 20
    //   292: aload 4
    //   294: ldc_w 325
    //   297: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   300: istore 21
    //   302: aload 4
    //   304: ldc_w 327
    //   307: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   310: istore 22
    //   312: aload 4
    //   314: ldc_w 329
    //   317: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   320: istore_3
    //   321: aload 4
    //   323: ldc_w 331
    //   326: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   329: istore 23
    //   331: aload 4
    //   333: ldc_w 333
    //   336: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   339: istore 24
    //   341: aload 4
    //   343: ldc_w 335
    //   346: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   349: istore 25
    //   351: aload 4
    //   353: ldc_w 337
    //   356: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   359: istore 26
    //   361: aload 4
    //   363: ldc_w 339
    //   366: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   369: istore 27
    //   371: aload 4
    //   373: invokeinterface 270 1 0
    //   378: anewarray 390	androidx/work/impl/model/WorkSpec
    //   381: astore_1
    //   382: iconst_0
    //   383: istore 28
    //   385: aload 4
    //   387: invokeinterface 190 1 0
    //   392: ifeq +463 -> 855
    //   395: aload 4
    //   397: iload 13
    //   399: invokeinterface 198 2 0
    //   404: astore 29
    //   406: aload 4
    //   408: iload 15
    //   410: invokeinterface 198 2 0
    //   415: astore 30
    //   417: new 341	androidx/work/Constraints
    //   420: astore 31
    //   422: aload 31
    //   424: invokespecial 342	androidx/work/Constraints:<init>	()V
    //   427: aload 31
    //   429: aload 4
    //   431: iload 5
    //   433: invokeinterface 346 2 0
    //   438: invokestatic 352	androidx/work/impl/model/WorkTypeConverters:intToNetworkType	(I)Landroidx/work/NetworkType;
    //   441: invokevirtual 356	androidx/work/Constraints:setRequiredNetworkType	(Landroidx/work/NetworkType;)V
    //   444: aload 4
    //   446: iload 6
    //   448: invokeinterface 346 2 0
    //   453: ifeq +9 -> 462
    //   456: iconst_1
    //   457: istore 32
    //   459: goto +6 -> 465
    //   462: iconst_0
    //   463: istore 32
    //   465: aload 31
    //   467: iload 32
    //   469: invokevirtual 360	androidx/work/Constraints:setRequiresCharging	(Z)V
    //   472: aload 4
    //   474: iload 7
    //   476: invokeinterface 346 2 0
    //   481: ifeq +9 -> 490
    //   484: iconst_1
    //   485: istore 32
    //   487: goto +6 -> 493
    //   490: iconst_0
    //   491: istore 32
    //   493: aload 31
    //   495: iload 32
    //   497: invokevirtual 363	androidx/work/Constraints:setRequiresDeviceIdle	(Z)V
    //   500: aload 4
    //   502: iload 8
    //   504: invokeinterface 346 2 0
    //   509: ifeq +9 -> 518
    //   512: iconst_1
    //   513: istore 32
    //   515: goto +6 -> 521
    //   518: iconst_0
    //   519: istore 32
    //   521: aload 31
    //   523: iload 32
    //   525: invokevirtual 366	androidx/work/Constraints:setRequiresBatteryNotLow	(Z)V
    //   528: aload 4
    //   530: iload 9
    //   532: invokeinterface 346 2 0
    //   537: ifeq +9 -> 546
    //   540: iconst_1
    //   541: istore 32
    //   543: goto +6 -> 549
    //   546: iconst_0
    //   547: istore 32
    //   549: aload 31
    //   551: iload 32
    //   553: invokevirtual 369	androidx/work/Constraints:setRequiresStorageNotLow	(Z)V
    //   556: aload 31
    //   558: aload 4
    //   560: iload 10
    //   562: invokeinterface 373 2 0
    //   567: invokevirtual 377	androidx/work/Constraints:setTriggerContentUpdateDelay	(J)V
    //   570: aload 31
    //   572: aload 4
    //   574: iload 11
    //   576: invokeinterface 373 2 0
    //   581: invokevirtual 380	androidx/work/Constraints:setTriggerMaxContentDelay	(J)V
    //   584: aload 31
    //   586: aload 4
    //   588: iload 12
    //   590: invokeinterface 208 2 0
    //   595: invokestatic 384	androidx/work/impl/model/WorkTypeConverters:byteArrayToContentUriTriggers	([B)Landroidx/work/ContentUriTriggers;
    //   598: invokevirtual 388	androidx/work/Constraints:setContentUriTriggers	(Landroidx/work/ContentUriTriggers;)V
    //   601: new 390	androidx/work/impl/model/WorkSpec
    //   604: astore 33
    //   606: aload 33
    //   608: aload 29
    //   610: aload 30
    //   612: invokespecial 393	androidx/work/impl/model/WorkSpec:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   615: aload 33
    //   617: aload 4
    //   619: iload 14
    //   621: invokeinterface 346 2 0
    //   626: invokestatic 397	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
    //   629: putfield 400	androidx/work/impl/model/WorkSpec:state	Landroidx/work/WorkInfo$State;
    //   632: aload 33
    //   634: aload 4
    //   636: iload 16
    //   638: invokeinterface 198 2 0
    //   643: putfield 404	androidx/work/impl/model/WorkSpec:inputMergerClassName	Ljava/lang/String;
    //   646: aload 33
    //   648: aload 4
    //   650: iload 17
    //   652: invokeinterface 208 2 0
    //   657: invokestatic 214	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   660: putfield 407	androidx/work/impl/model/WorkSpec:input	Landroidx/work/Data;
    //   663: aload 33
    //   665: aload 4
    //   667: iload 18
    //   669: invokeinterface 208 2 0
    //   674: invokestatic 214	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   677: putfield 409	androidx/work/impl/model/WorkSpec:output	Landroidx/work/Data;
    //   680: aload 33
    //   682: aload 4
    //   684: iload 19
    //   686: invokeinterface 373 2 0
    //   691: putfield 413	androidx/work/impl/model/WorkSpec:initialDelay	J
    //   694: aload 33
    //   696: aload 4
    //   698: iload 20
    //   700: invokeinterface 373 2 0
    //   705: putfield 416	androidx/work/impl/model/WorkSpec:intervalDuration	J
    //   708: aload 33
    //   710: aload 4
    //   712: iload 21
    //   714: invokeinterface 373 2 0
    //   719: putfield 419	androidx/work/impl/model/WorkSpec:flexDuration	J
    //   722: aload 33
    //   724: aload 4
    //   726: iload 22
    //   728: invokeinterface 346 2 0
    //   733: putfield 423	androidx/work/impl/model/WorkSpec:runAttemptCount	I
    //   736: aload 33
    //   738: aload 4
    //   740: iload_3
    //   741: invokeinterface 346 2 0
    //   746: invokestatic 427	androidx/work/impl/model/WorkTypeConverters:intToBackoffPolicy	(I)Landroidx/work/BackoffPolicy;
    //   749: putfield 431	androidx/work/impl/model/WorkSpec:backoffPolicy	Landroidx/work/BackoffPolicy;
    //   752: aload 33
    //   754: aload 4
    //   756: iload 23
    //   758: invokeinterface 373 2 0
    //   763: putfield 434	androidx/work/impl/model/WorkSpec:backoffDelayDuration	J
    //   766: aload 33
    //   768: aload 4
    //   770: iload 24
    //   772: invokeinterface 373 2 0
    //   777: putfield 437	androidx/work/impl/model/WorkSpec:periodStartTime	J
    //   780: aload 33
    //   782: aload 4
    //   784: iload 25
    //   786: invokeinterface 373 2 0
    //   791: putfield 440	androidx/work/impl/model/WorkSpec:minimumRetentionDuration	J
    //   794: aload 33
    //   796: aload 4
    //   798: iload 26
    //   800: invokeinterface 373 2 0
    //   805: putfield 443	androidx/work/impl/model/WorkSpec:scheduleRequestedAt	J
    //   808: aload 4
    //   810: iload 27
    //   812: invokeinterface 346 2 0
    //   817: ifeq +9 -> 826
    //   820: iconst_1
    //   821: istore 32
    //   823: goto +6 -> 829
    //   826: iconst_0
    //   827: istore 32
    //   829: aload 33
    //   831: iload 32
    //   833: putfield 447	androidx/work/impl/model/WorkSpec:runInForeground	Z
    //   836: aload 33
    //   838: aload 31
    //   840: putfield 451	androidx/work/impl/model/WorkSpec:constraints	Landroidx/work/Constraints;
    //   843: aload_1
    //   844: iload 28
    //   846: aload 33
    //   848: aastore
    //   849: iinc 28 1
    //   852: goto -467 -> 385
    //   855: aload 4
    //   857: invokeinterface 187 1 0
    //   862: aload_2
    //   863: invokevirtual 276	androidx/room/RoomSQLiteQuery:release	()V
    //   866: aload_1
    //   867: areturn
    //   868: astore_1
    //   869: goto +4 -> 873
    //   872: astore_1
    //   873: aload 4
    //   875: invokeinterface 187 1 0
    //   880: aload_2
    //   881: invokevirtual 276	androidx/room/RoomSQLiteQuery:release	()V
    //   884: aload_1
    //   885: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	886	0	this	WorkSpecDao_Impl
    //   0	886	1	paramList	List<String>
    //   3	878	2	localObject1	Object
    //   34	707	3	i	int
    //   85	789	4	localObject2	Object
    //   140	292	5	j	int
    //   150	297	6	k	int
    //   160	315	7	m	int
    //   170	333	8	n	int
    //   180	351	9	i1	int
    //   190	371	10	i2	int
    //   200	375	11	i3	int
    //   210	379	12	i4	int
    //   220	178	13	i5	int
    //   230	390	14	i6	int
    //   240	169	15	i7	int
    //   250	387	16	i8	int
    //   260	391	17	i9	int
    //   270	398	18	i10	int
    //   280	405	19	i11	int
    //   290	409	20	i12	int
    //   300	413	21	i13	int
    //   310	417	22	i14	int
    //   329	428	23	i15	int
    //   339	432	24	i16	int
    //   349	436	25	i17	int
    //   359	440	26	i18	int
    //   369	442	27	i19	int
    //   383	467	28	i20	int
    //   404	205	29	str1	String
    //   415	196	30	str2	String
    //   420	419	31	localConstraints	Constraints
    //   457	375	32	bool	boolean
    //   604	243	33	localWorkSpec	WorkSpec
    // Exception table:
    //   from	to	target	type
    //   272	382	868	finally
    //   385	456	868	finally
    //   465	484	868	finally
    //   493	512	868	finally
    //   521	540	868	finally
    //   549	694	868	finally
    //   694	752	868	finally
    //   752	780	868	finally
    //   780	794	868	finally
    //   794	820	868	finally
    //   829	843	868	finally
    //   132	272	872	finally
  }
  
  /* Error */
  public WorkSpec.WorkInfoPojo getWorkStatusPojoForId(String paramString)
  {
    // Byte code:
    //   0: ldc_w 507
    //   3: iconst_1
    //   4: invokestatic 146	androidx/room/RoomSQLiteQuery:acquire	(Ljava/lang/String;I)Landroidx/room/RoomSQLiteQuery;
    //   7: astore_2
    //   8: aload_1
    //   9: ifnonnull +11 -> 20
    //   12: aload_2
    //   13: iconst_1
    //   14: invokevirtual 164	androidx/room/RoomSQLiteQuery:bindNull	(I)V
    //   17: goto +9 -> 26
    //   20: aload_2
    //   21: iconst_1
    //   22: aload_1
    //   23: invokevirtual 168	androidx/room/RoomSQLiteQuery:bindString	(ILjava/lang/String;)V
    //   26: aload_0
    //   27: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   30: invokevirtual 238	androidx/room/RoomDatabase:assertNotSuspendingTransaction	()V
    //   33: aload_0
    //   34: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   37: invokevirtual 250	androidx/room/RoomDatabase:beginTransaction	()V
    //   40: aload_0
    //   41: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   44: astore_3
    //   45: aconst_null
    //   46: astore_1
    //   47: aconst_null
    //   48: astore 4
    //   50: aload_3
    //   51: aload_2
    //   52: iconst_1
    //   53: aconst_null
    //   54: invokestatic 174	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   57: astore 5
    //   59: aload 5
    //   61: ldc_w 309
    //   64: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   67: istore 6
    //   69: aload 5
    //   71: ldc_w 311
    //   74: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   77: istore 7
    //   79: aload 5
    //   81: ldc_w 319
    //   84: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   87: istore 8
    //   89: aload 5
    //   91: ldc_w 327
    //   94: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   97: istore 9
    //   99: new 85	androidx/collection/ArrayMap
    //   102: astore_3
    //   103: aload_3
    //   104: invokespecial 508	androidx/collection/ArrayMap:<init>	()V
    //   107: new 85	androidx/collection/ArrayMap
    //   110: astore 10
    //   112: aload 10
    //   114: invokespecial 508	androidx/collection/ArrayMap:<init>	()V
    //   117: aload 5
    //   119: invokeinterface 190 1 0
    //   124: ifeq +116 -> 240
    //   127: aload 5
    //   129: iload 6
    //   131: invokeinterface 194 2 0
    //   136: ifne +45 -> 181
    //   139: aload 5
    //   141: iload 6
    //   143: invokeinterface 198 2 0
    //   148: astore 11
    //   150: aload_3
    //   151: aload 11
    //   153: invokevirtual 202	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   156: checkcast 204	java/util/ArrayList
    //   159: ifnonnull +22 -> 181
    //   162: new 204	java/util/ArrayList
    //   165: astore 12
    //   167: aload 12
    //   169: invokespecial 509	java/util/ArrayList:<init>	()V
    //   172: aload_3
    //   173: aload 11
    //   175: aload 12
    //   177: invokevirtual 113	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   180: pop
    //   181: aload 5
    //   183: iload 6
    //   185: invokeinterface 194 2 0
    //   190: ifne -73 -> 117
    //   193: aload 5
    //   195: iload 6
    //   197: invokeinterface 198 2 0
    //   202: astore 12
    //   204: aload 10
    //   206: aload 12
    //   208: invokevirtual 202	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   211: checkcast 204	java/util/ArrayList
    //   214: ifnonnull -97 -> 117
    //   217: new 204	java/util/ArrayList
    //   220: astore 11
    //   222: aload 11
    //   224: invokespecial 509	java/util/ArrayList:<init>	()V
    //   227: aload 10
    //   229: aload 12
    //   231: aload 11
    //   233: invokevirtual 113	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   236: pop
    //   237: goto -120 -> 117
    //   240: aload 5
    //   242: iconst_m1
    //   243: invokeinterface 512 2 0
    //   248: pop
    //   249: aload_0
    //   250: aload_3
    //   251: invokespecial 223	androidx/work/impl/model/WorkSpecDao_Impl:__fetchRelationshipWorkTagAsjavaLangString	(Landroidx/collection/ArrayMap;)V
    //   254: aload_0
    //   255: aload 10
    //   257: invokespecial 115	androidx/work/impl/model/WorkSpecDao_Impl:__fetchRelationshipWorkProgressAsandroidxWorkData	(Landroidx/collection/ArrayMap;)V
    //   260: aload 5
    //   262: invokeinterface 471 1 0
    //   267: ifeq +178 -> 445
    //   270: aload 5
    //   272: iload 6
    //   274: invokeinterface 194 2 0
    //   279: ifne +23 -> 302
    //   282: aload_3
    //   283: aload 5
    //   285: iload 6
    //   287: invokeinterface 198 2 0
    //   292: invokevirtual 202	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   295: checkcast 204	java/util/ArrayList
    //   298: astore_1
    //   299: goto +5 -> 304
    //   302: aconst_null
    //   303: astore_1
    //   304: aload_1
    //   305: astore_3
    //   306: aload_1
    //   307: ifnonnull +11 -> 318
    //   310: new 204	java/util/ArrayList
    //   313: astore_3
    //   314: aload_3
    //   315: invokespecial 509	java/util/ArrayList:<init>	()V
    //   318: aload 4
    //   320: astore_1
    //   321: aload 5
    //   323: iload 6
    //   325: invokeinterface 194 2 0
    //   330: ifne +21 -> 351
    //   333: aload 10
    //   335: aload 5
    //   337: iload 6
    //   339: invokeinterface 198 2 0
    //   344: invokevirtual 202	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   347: checkcast 204	java/util/ArrayList
    //   350: astore_1
    //   351: aload_1
    //   352: astore 4
    //   354: aload_1
    //   355: ifnonnull +13 -> 368
    //   358: new 204	java/util/ArrayList
    //   361: astore 4
    //   363: aload 4
    //   365: invokespecial 509	java/util/ArrayList:<init>	()V
    //   368: new 514	androidx/work/impl/model/WorkSpec$WorkInfoPojo
    //   371: astore_1
    //   372: aload_1
    //   373: invokespecial 515	androidx/work/impl/model/WorkSpec$WorkInfoPojo:<init>	()V
    //   376: aload_1
    //   377: aload 5
    //   379: iload 6
    //   381: invokeinterface 198 2 0
    //   386: putfield 516	androidx/work/impl/model/WorkSpec$WorkInfoPojo:id	Ljava/lang/String;
    //   389: aload_1
    //   390: aload 5
    //   392: iload 7
    //   394: invokeinterface 346 2 0
    //   399: invokestatic 397	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
    //   402: putfield 517	androidx/work/impl/model/WorkSpec$WorkInfoPojo:state	Landroidx/work/WorkInfo$State;
    //   405: aload_1
    //   406: aload 5
    //   408: iload 8
    //   410: invokeinterface 208 2 0
    //   415: invokestatic 214	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   418: putfield 518	androidx/work/impl/model/WorkSpec$WorkInfoPojo:output	Landroidx/work/Data;
    //   421: aload_1
    //   422: aload 5
    //   424: iload 9
    //   426: invokeinterface 346 2 0
    //   431: putfield 519	androidx/work/impl/model/WorkSpec$WorkInfoPojo:runAttemptCount	I
    //   434: aload_1
    //   435: aload_3
    //   436: putfield 523	androidx/work/impl/model/WorkSpec$WorkInfoPojo:tags	Ljava/util/List;
    //   439: aload_1
    //   440: aload 4
    //   442: putfield 526	androidx/work/impl/model/WorkSpec$WorkInfoPojo:progress	Ljava/util/List;
    //   445: aload_0
    //   446: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   449: invokevirtual 256	androidx/room/RoomDatabase:setTransactionSuccessful	()V
    //   452: aload 5
    //   454: invokeinterface 187 1 0
    //   459: aload_2
    //   460: invokevirtual 276	androidx/room/RoomSQLiteQuery:release	()V
    //   463: aload_0
    //   464: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   467: invokevirtual 259	androidx/room/RoomDatabase:endTransaction	()V
    //   470: aload_1
    //   471: areturn
    //   472: astore_1
    //   473: aload 5
    //   475: invokeinterface 187 1 0
    //   480: aload_2
    //   481: invokevirtual 276	androidx/room/RoomSQLiteQuery:release	()V
    //   484: aload_1
    //   485: athrow
    //   486: astore_1
    //   487: aload_0
    //   488: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   491: invokevirtual 259	androidx/room/RoomDatabase:endTransaction	()V
    //   494: aload_1
    //   495: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	496	0	this	WorkSpecDao_Impl
    //   0	496	1	paramString	String
    //   7	474	2	localRoomSQLiteQuery	RoomSQLiteQuery
    //   44	392	3	localObject1	Object
    //   48	393	4	localObject2	Object
    //   57	417	5	localCursor	Cursor
    //   67	313	6	i	int
    //   77	316	7	j	int
    //   87	322	8	k	int
    //   97	328	9	m	int
    //   110	224	10	localArrayMap	ArrayMap
    //   148	84	11	localObject3	Object
    //   165	65	12	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   59	117	472	finally
    //   117	181	472	finally
    //   181	237	472	finally
    //   240	260	472	finally
    //   260	299	472	finally
    //   310	318	472	finally
    //   321	351	472	finally
    //   358	368	472	finally
    //   368	445	472	finally
    //   445	452	472	finally
    //   40	45	486	finally
    //   50	59	486	finally
    //   452	463	486	finally
    //   473	486	486	finally
  }
  
  /* Error */
  public List<WorkSpec.WorkInfoPojo> getWorkStatusPojoForIds(List<String> paramList)
  {
    // Byte code:
    //   0: invokestatic 121	androidx/room/util/StringUtil:newStringBuilder	()Ljava/lang/StringBuilder;
    //   3: astore_2
    //   4: aload_2
    //   5: ldc_w 530
    //   8: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   11: pop
    //   12: aload_1
    //   13: invokeinterface 501 1 0
    //   18: istore_3
    //   19: aload_2
    //   20: iload_3
    //   21: invokestatic 134	androidx/room/util/StringUtil:appendPlaceholders	(Ljava/lang/StringBuilder;I)V
    //   24: aload_2
    //   25: ldc -120
    //   27: invokevirtual 129	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   30: pop
    //   31: aload_2
    //   32: invokevirtual 140	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   35: iload_3
    //   36: iconst_0
    //   37: iadd
    //   38: invokestatic 146	androidx/room/RoomSQLiteQuery:acquire	(Ljava/lang/String;I)Landroidx/room/RoomSQLiteQuery;
    //   41: astore 4
    //   43: aload_1
    //   44: invokeinterface 502 1 0
    //   49: astore_1
    //   50: iconst_1
    //   51: istore_3
    //   52: aload_1
    //   53: invokeinterface 155 1 0
    //   58: ifeq +39 -> 97
    //   61: aload_1
    //   62: invokeinterface 159 1 0
    //   67: checkcast 161	java/lang/String
    //   70: astore_2
    //   71: aload_2
    //   72: ifnonnull +12 -> 84
    //   75: aload 4
    //   77: iload_3
    //   78: invokevirtual 164	androidx/room/RoomSQLiteQuery:bindNull	(I)V
    //   81: goto +10 -> 91
    //   84: aload 4
    //   86: iload_3
    //   87: aload_2
    //   88: invokevirtual 168	androidx/room/RoomSQLiteQuery:bindString	(ILjava/lang/String;)V
    //   91: iinc 3 1
    //   94: goto -42 -> 52
    //   97: aload_0
    //   98: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   101: invokevirtual 238	androidx/room/RoomDatabase:assertNotSuspendingTransaction	()V
    //   104: aload_0
    //   105: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   108: invokevirtual 250	androidx/room/RoomDatabase:beginTransaction	()V
    //   111: aload_0
    //   112: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   115: aload 4
    //   117: iconst_1
    //   118: aconst_null
    //   119: invokestatic 174	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   122: astore 5
    //   124: aload 5
    //   126: ldc_w 309
    //   129: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   132: istore 6
    //   134: aload 5
    //   136: ldc_w 311
    //   139: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   142: istore_3
    //   143: aload 5
    //   145: ldc_w 319
    //   148: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   151: istore 7
    //   153: aload 5
    //   155: ldc_w 327
    //   158: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   161: istore 8
    //   163: new 85	androidx/collection/ArrayMap
    //   166: astore 9
    //   168: aload 9
    //   170: invokespecial 508	androidx/collection/ArrayMap:<init>	()V
    //   173: new 85	androidx/collection/ArrayMap
    //   176: astore 10
    //   178: aload 10
    //   180: invokespecial 508	androidx/collection/ArrayMap:<init>	()V
    //   183: aload 5
    //   185: invokeinterface 190 1 0
    //   190: ifeq +106 -> 296
    //   193: aload 5
    //   195: iload 6
    //   197: invokeinterface 194 2 0
    //   202: ifne +41 -> 243
    //   205: aload 5
    //   207: iload 6
    //   209: invokeinterface 198 2 0
    //   214: astore_2
    //   215: aload 9
    //   217: aload_2
    //   218: invokevirtual 202	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   221: checkcast 204	java/util/ArrayList
    //   224: ifnonnull +19 -> 243
    //   227: new 204	java/util/ArrayList
    //   230: astore_1
    //   231: aload_1
    //   232: invokespecial 509	java/util/ArrayList:<init>	()V
    //   235: aload 9
    //   237: aload_2
    //   238: aload_1
    //   239: invokevirtual 113	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   242: pop
    //   243: aload 5
    //   245: iload 6
    //   247: invokeinterface 194 2 0
    //   252: ifne -69 -> 183
    //   255: aload 5
    //   257: iload 6
    //   259: invokeinterface 198 2 0
    //   264: astore_2
    //   265: aload 10
    //   267: aload_2
    //   268: invokevirtual 202	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   271: checkcast 204	java/util/ArrayList
    //   274: ifnonnull -91 -> 183
    //   277: new 204	java/util/ArrayList
    //   280: astore_1
    //   281: aload_1
    //   282: invokespecial 509	java/util/ArrayList:<init>	()V
    //   285: aload 10
    //   287: aload_2
    //   288: aload_1
    //   289: invokevirtual 113	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   292: pop
    //   293: goto -110 -> 183
    //   296: aload 5
    //   298: iconst_m1
    //   299: invokeinterface 512 2 0
    //   304: pop
    //   305: aload_0
    //   306: aload 9
    //   308: invokespecial 223	androidx/work/impl/model/WorkSpecDao_Impl:__fetchRelationshipWorkTagAsjavaLangString	(Landroidx/collection/ArrayMap;)V
    //   311: aload_0
    //   312: aload 10
    //   314: invokespecial 115	androidx/work/impl/model/WorkSpecDao_Impl:__fetchRelationshipWorkProgressAsandroidxWorkData	(Landroidx/collection/ArrayMap;)V
    //   317: new 204	java/util/ArrayList
    //   320: astore 11
    //   322: aload 11
    //   324: aload 5
    //   326: invokeinterface 270 1 0
    //   331: invokespecial 271	java/util/ArrayList:<init>	(I)V
    //   334: aload 5
    //   336: invokeinterface 190 1 0
    //   341: ifeq +192 -> 533
    //   344: aload 5
    //   346: iload 6
    //   348: invokeinterface 194 2 0
    //   353: ifne +24 -> 377
    //   356: aload 9
    //   358: aload 5
    //   360: iload 6
    //   362: invokeinterface 198 2 0
    //   367: invokevirtual 202	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   370: checkcast 204	java/util/ArrayList
    //   373: astore_1
    //   374: goto +5 -> 379
    //   377: aconst_null
    //   378: astore_1
    //   379: aload_1
    //   380: astore_2
    //   381: aload_1
    //   382: ifnonnull +11 -> 393
    //   385: new 204	java/util/ArrayList
    //   388: astore_2
    //   389: aload_2
    //   390: invokespecial 509	java/util/ArrayList:<init>	()V
    //   393: aload 5
    //   395: iload 6
    //   397: invokeinterface 194 2 0
    //   402: ifne +24 -> 426
    //   405: aload 10
    //   407: aload 5
    //   409: iload 6
    //   411: invokeinterface 198 2 0
    //   416: invokevirtual 202	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   419: checkcast 204	java/util/ArrayList
    //   422: astore_1
    //   423: goto +5 -> 428
    //   426: aconst_null
    //   427: astore_1
    //   428: aload_1
    //   429: astore 12
    //   431: aload_1
    //   432: ifnonnull +13 -> 445
    //   435: new 204	java/util/ArrayList
    //   438: astore 12
    //   440: aload 12
    //   442: invokespecial 509	java/util/ArrayList:<init>	()V
    //   445: new 514	androidx/work/impl/model/WorkSpec$WorkInfoPojo
    //   448: astore_1
    //   449: aload_1
    //   450: invokespecial 515	androidx/work/impl/model/WorkSpec$WorkInfoPojo:<init>	()V
    //   453: aload_1
    //   454: aload 5
    //   456: iload 6
    //   458: invokeinterface 198 2 0
    //   463: putfield 516	androidx/work/impl/model/WorkSpec$WorkInfoPojo:id	Ljava/lang/String;
    //   466: aload_1
    //   467: aload 5
    //   469: iload_3
    //   470: invokeinterface 346 2 0
    //   475: invokestatic 397	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
    //   478: putfield 517	androidx/work/impl/model/WorkSpec$WorkInfoPojo:state	Landroidx/work/WorkInfo$State;
    //   481: aload_1
    //   482: aload 5
    //   484: iload 7
    //   486: invokeinterface 208 2 0
    //   491: invokestatic 214	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   494: putfield 518	androidx/work/impl/model/WorkSpec$WorkInfoPojo:output	Landroidx/work/Data;
    //   497: aload_1
    //   498: aload 5
    //   500: iload 8
    //   502: invokeinterface 346 2 0
    //   507: putfield 519	androidx/work/impl/model/WorkSpec$WorkInfoPojo:runAttemptCount	I
    //   510: aload_1
    //   511: aload_2
    //   512: putfield 523	androidx/work/impl/model/WorkSpec$WorkInfoPojo:tags	Ljava/util/List;
    //   515: aload_1
    //   516: aload 12
    //   518: putfield 526	androidx/work/impl/model/WorkSpec$WorkInfoPojo:progress	Ljava/util/List;
    //   521: aload 11
    //   523: aload_1
    //   524: invokeinterface 274 2 0
    //   529: pop
    //   530: goto -196 -> 334
    //   533: aload_0
    //   534: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   537: invokevirtual 256	androidx/room/RoomDatabase:setTransactionSuccessful	()V
    //   540: aload 5
    //   542: invokeinterface 187 1 0
    //   547: aload 4
    //   549: invokevirtual 276	androidx/room/RoomSQLiteQuery:release	()V
    //   552: aload_0
    //   553: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   556: invokevirtual 259	androidx/room/RoomDatabase:endTransaction	()V
    //   559: aload 11
    //   561: areturn
    //   562: astore_1
    //   563: aload 5
    //   565: invokeinterface 187 1 0
    //   570: aload 4
    //   572: invokevirtual 276	androidx/room/RoomSQLiteQuery:release	()V
    //   575: aload_1
    //   576: athrow
    //   577: astore_1
    //   578: aload_0
    //   579: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   582: invokevirtual 259	androidx/room/RoomDatabase:endTransaction	()V
    //   585: aload_1
    //   586: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	587	0	this	WorkSpecDao_Impl
    //   0	587	1	paramList	List<String>
    //   3	509	2	localObject1	Object
    //   18	452	3	i	int
    //   41	530	4	localRoomSQLiteQuery	RoomSQLiteQuery
    //   122	442	5	localCursor	Cursor
    //   132	325	6	j	int
    //   151	334	7	k	int
    //   161	340	8	m	int
    //   166	191	9	localArrayMap1	ArrayMap
    //   176	230	10	localArrayMap2	ArrayMap
    //   320	240	11	localArrayList	ArrayList
    //   429	88	12	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   124	183	562	finally
    //   183	243	562	finally
    //   243	293	562	finally
    //   296	334	562	finally
    //   334	374	562	finally
    //   385	393	562	finally
    //   393	423	562	finally
    //   435	445	562	finally
    //   445	530	562	finally
    //   533	540	562	finally
    //   111	124	577	finally
    //   540	552	577	finally
    //   563	577	577	finally
  }
  
  /* Error */
  public List<WorkSpec.WorkInfoPojo> getWorkStatusPojoForName(String paramString)
  {
    // Byte code:
    //   0: ldc_w 534
    //   3: iconst_1
    //   4: invokestatic 146	androidx/room/RoomSQLiteQuery:acquire	(Ljava/lang/String;I)Landroidx/room/RoomSQLiteQuery;
    //   7: astore_2
    //   8: aload_1
    //   9: ifnonnull +11 -> 20
    //   12: aload_2
    //   13: iconst_1
    //   14: invokevirtual 164	androidx/room/RoomSQLiteQuery:bindNull	(I)V
    //   17: goto +9 -> 26
    //   20: aload_2
    //   21: iconst_1
    //   22: aload_1
    //   23: invokevirtual 168	androidx/room/RoomSQLiteQuery:bindString	(ILjava/lang/String;)V
    //   26: aload_0
    //   27: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   30: invokevirtual 238	androidx/room/RoomDatabase:assertNotSuspendingTransaction	()V
    //   33: aload_0
    //   34: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   37: invokevirtual 250	androidx/room/RoomDatabase:beginTransaction	()V
    //   40: aload_0
    //   41: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   44: aload_2
    //   45: iconst_1
    //   46: aconst_null
    //   47: invokestatic 174	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   50: astore_3
    //   51: aload_3
    //   52: ldc_w 309
    //   55: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   58: istore 4
    //   60: aload_3
    //   61: ldc_w 311
    //   64: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   67: istore 5
    //   69: aload_3
    //   70: ldc_w 319
    //   73: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   76: istore 6
    //   78: aload_3
    //   79: ldc_w 327
    //   82: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   85: istore 7
    //   87: new 85	androidx/collection/ArrayMap
    //   90: astore 8
    //   92: aload 8
    //   94: invokespecial 508	androidx/collection/ArrayMap:<init>	()V
    //   97: new 85	androidx/collection/ArrayMap
    //   100: astore 9
    //   102: aload 9
    //   104: invokespecial 508	androidx/collection/ArrayMap:<init>	()V
    //   107: aload_3
    //   108: invokeinterface 190 1 0
    //   113: ifeq +108 -> 221
    //   116: aload_3
    //   117: iload 4
    //   119: invokeinterface 194 2 0
    //   124: ifne +43 -> 167
    //   127: aload_3
    //   128: iload 4
    //   130: invokeinterface 198 2 0
    //   135: astore_1
    //   136: aload 8
    //   138: aload_1
    //   139: invokevirtual 202	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   142: checkcast 204	java/util/ArrayList
    //   145: ifnonnull +22 -> 167
    //   148: new 204	java/util/ArrayList
    //   151: astore 10
    //   153: aload 10
    //   155: invokespecial 509	java/util/ArrayList:<init>	()V
    //   158: aload 8
    //   160: aload_1
    //   161: aload 10
    //   163: invokevirtual 113	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   166: pop
    //   167: aload_3
    //   168: iload 4
    //   170: invokeinterface 194 2 0
    //   175: ifne -68 -> 107
    //   178: aload_3
    //   179: iload 4
    //   181: invokeinterface 198 2 0
    //   186: astore_1
    //   187: aload 9
    //   189: aload_1
    //   190: invokevirtual 202	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   193: checkcast 204	java/util/ArrayList
    //   196: ifnonnull -89 -> 107
    //   199: new 204	java/util/ArrayList
    //   202: astore 10
    //   204: aload 10
    //   206: invokespecial 509	java/util/ArrayList:<init>	()V
    //   209: aload 9
    //   211: aload_1
    //   212: aload 10
    //   214: invokevirtual 113	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   217: pop
    //   218: goto -111 -> 107
    //   221: aload_3
    //   222: iconst_m1
    //   223: invokeinterface 512 2 0
    //   228: pop
    //   229: aload_0
    //   230: aload 8
    //   232: invokespecial 223	androidx/work/impl/model/WorkSpecDao_Impl:__fetchRelationshipWorkTagAsjavaLangString	(Landroidx/collection/ArrayMap;)V
    //   235: aload_0
    //   236: aload 9
    //   238: invokespecial 115	androidx/work/impl/model/WorkSpecDao_Impl:__fetchRelationshipWorkProgressAsandroidxWorkData	(Landroidx/collection/ArrayMap;)V
    //   241: new 204	java/util/ArrayList
    //   244: astore 11
    //   246: aload 11
    //   248: aload_3
    //   249: invokeinterface 270 1 0
    //   254: invokespecial 271	java/util/ArrayList:<init>	(I)V
    //   257: aload_3
    //   258: invokeinterface 190 1 0
    //   263: ifeq +189 -> 452
    //   266: aload_3
    //   267: iload 4
    //   269: invokeinterface 194 2 0
    //   274: ifne +23 -> 297
    //   277: aload 8
    //   279: aload_3
    //   280: iload 4
    //   282: invokeinterface 198 2 0
    //   287: invokevirtual 202	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   290: checkcast 204	java/util/ArrayList
    //   293: astore_1
    //   294: goto +5 -> 299
    //   297: aconst_null
    //   298: astore_1
    //   299: aload_1
    //   300: astore 10
    //   302: aload_1
    //   303: ifnonnull +13 -> 316
    //   306: new 204	java/util/ArrayList
    //   309: astore 10
    //   311: aload 10
    //   313: invokespecial 509	java/util/ArrayList:<init>	()V
    //   316: aload_3
    //   317: iload 4
    //   319: invokeinterface 194 2 0
    //   324: ifne +23 -> 347
    //   327: aload 9
    //   329: aload_3
    //   330: iload 4
    //   332: invokeinterface 198 2 0
    //   337: invokevirtual 202	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   340: checkcast 204	java/util/ArrayList
    //   343: astore_1
    //   344: goto +5 -> 349
    //   347: aconst_null
    //   348: astore_1
    //   349: aload_1
    //   350: astore 12
    //   352: aload_1
    //   353: ifnonnull +13 -> 366
    //   356: new 204	java/util/ArrayList
    //   359: astore 12
    //   361: aload 12
    //   363: invokespecial 509	java/util/ArrayList:<init>	()V
    //   366: new 514	androidx/work/impl/model/WorkSpec$WorkInfoPojo
    //   369: astore_1
    //   370: aload_1
    //   371: invokespecial 515	androidx/work/impl/model/WorkSpec$WorkInfoPojo:<init>	()V
    //   374: aload_1
    //   375: aload_3
    //   376: iload 4
    //   378: invokeinterface 198 2 0
    //   383: putfield 516	androidx/work/impl/model/WorkSpec$WorkInfoPojo:id	Ljava/lang/String;
    //   386: aload_1
    //   387: aload_3
    //   388: iload 5
    //   390: invokeinterface 346 2 0
    //   395: invokestatic 397	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
    //   398: putfield 517	androidx/work/impl/model/WorkSpec$WorkInfoPojo:state	Landroidx/work/WorkInfo$State;
    //   401: aload_1
    //   402: aload_3
    //   403: iload 6
    //   405: invokeinterface 208 2 0
    //   410: invokestatic 214	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   413: putfield 518	androidx/work/impl/model/WorkSpec$WorkInfoPojo:output	Landroidx/work/Data;
    //   416: aload_1
    //   417: aload_3
    //   418: iload 7
    //   420: invokeinterface 346 2 0
    //   425: putfield 519	androidx/work/impl/model/WorkSpec$WorkInfoPojo:runAttemptCount	I
    //   428: aload_1
    //   429: aload 10
    //   431: putfield 523	androidx/work/impl/model/WorkSpec$WorkInfoPojo:tags	Ljava/util/List;
    //   434: aload_1
    //   435: aload 12
    //   437: putfield 526	androidx/work/impl/model/WorkSpec$WorkInfoPojo:progress	Ljava/util/List;
    //   440: aload 11
    //   442: aload_1
    //   443: invokeinterface 274 2 0
    //   448: pop
    //   449: goto -192 -> 257
    //   452: aload_0
    //   453: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   456: invokevirtual 256	androidx/room/RoomDatabase:setTransactionSuccessful	()V
    //   459: aload_3
    //   460: invokeinterface 187 1 0
    //   465: aload_2
    //   466: invokevirtual 276	androidx/room/RoomSQLiteQuery:release	()V
    //   469: aload_0
    //   470: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   473: invokevirtual 259	androidx/room/RoomDatabase:endTransaction	()V
    //   476: aload 11
    //   478: areturn
    //   479: astore_1
    //   480: aload_3
    //   481: invokeinterface 187 1 0
    //   486: aload_2
    //   487: invokevirtual 276	androidx/room/RoomSQLiteQuery:release	()V
    //   490: aload_1
    //   491: athrow
    //   492: astore_1
    //   493: aload_0
    //   494: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   497: invokevirtual 259	androidx/room/RoomDatabase:endTransaction	()V
    //   500: aload_1
    //   501: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	502	0	this	WorkSpecDao_Impl
    //   0	502	1	paramString	String
    //   7	480	2	localRoomSQLiteQuery	RoomSQLiteQuery
    //   50	431	3	localCursor	Cursor
    //   58	319	4	i	int
    //   67	322	5	j	int
    //   76	328	6	k	int
    //   85	334	7	m	int
    //   90	188	8	localArrayMap1	ArrayMap
    //   100	228	9	localArrayMap2	ArrayMap
    //   151	279	10	localObject1	Object
    //   244	233	11	localArrayList	ArrayList
    //   350	86	12	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   51	107	479	finally
    //   107	167	479	finally
    //   167	218	479	finally
    //   221	257	479	finally
    //   257	294	479	finally
    //   306	316	479	finally
    //   316	344	479	finally
    //   356	366	479	finally
    //   366	449	479	finally
    //   452	459	479	finally
    //   40	51	492	finally
    //   459	469	492	finally
    //   480	492	492	finally
  }
  
  /* Error */
  public List<WorkSpec.WorkInfoPojo> getWorkStatusPojoForTag(String paramString)
  {
    // Byte code:
    //   0: ldc_w 538
    //   3: iconst_1
    //   4: invokestatic 146	androidx/room/RoomSQLiteQuery:acquire	(Ljava/lang/String;I)Landroidx/room/RoomSQLiteQuery;
    //   7: astore_2
    //   8: aload_1
    //   9: ifnonnull +11 -> 20
    //   12: aload_2
    //   13: iconst_1
    //   14: invokevirtual 164	androidx/room/RoomSQLiteQuery:bindNull	(I)V
    //   17: goto +9 -> 26
    //   20: aload_2
    //   21: iconst_1
    //   22: aload_1
    //   23: invokevirtual 168	androidx/room/RoomSQLiteQuery:bindString	(ILjava/lang/String;)V
    //   26: aload_0
    //   27: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   30: invokevirtual 238	androidx/room/RoomDatabase:assertNotSuspendingTransaction	()V
    //   33: aload_0
    //   34: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   37: invokevirtual 250	androidx/room/RoomDatabase:beginTransaction	()V
    //   40: aload_0
    //   41: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   44: aload_2
    //   45: iconst_1
    //   46: aconst_null
    //   47: invokestatic 174	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   50: astore_3
    //   51: aload_3
    //   52: ldc_w 309
    //   55: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   58: istore 4
    //   60: aload_3
    //   61: ldc_w 311
    //   64: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   67: istore 5
    //   69: aload_3
    //   70: ldc_w 319
    //   73: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   76: istore 6
    //   78: aload_3
    //   79: ldc_w 327
    //   82: invokestatic 293	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   85: istore 7
    //   87: new 85	androidx/collection/ArrayMap
    //   90: astore 8
    //   92: aload 8
    //   94: invokespecial 508	androidx/collection/ArrayMap:<init>	()V
    //   97: new 85	androidx/collection/ArrayMap
    //   100: astore 9
    //   102: aload 9
    //   104: invokespecial 508	androidx/collection/ArrayMap:<init>	()V
    //   107: aload_3
    //   108: invokeinterface 190 1 0
    //   113: ifeq +108 -> 221
    //   116: aload_3
    //   117: iload 4
    //   119: invokeinterface 194 2 0
    //   124: ifne +43 -> 167
    //   127: aload_3
    //   128: iload 4
    //   130: invokeinterface 198 2 0
    //   135: astore_1
    //   136: aload 8
    //   138: aload_1
    //   139: invokevirtual 202	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   142: checkcast 204	java/util/ArrayList
    //   145: ifnonnull +22 -> 167
    //   148: new 204	java/util/ArrayList
    //   151: astore 10
    //   153: aload 10
    //   155: invokespecial 509	java/util/ArrayList:<init>	()V
    //   158: aload 8
    //   160: aload_1
    //   161: aload 10
    //   163: invokevirtual 113	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   166: pop
    //   167: aload_3
    //   168: iload 4
    //   170: invokeinterface 194 2 0
    //   175: ifne -68 -> 107
    //   178: aload_3
    //   179: iload 4
    //   181: invokeinterface 198 2 0
    //   186: astore_1
    //   187: aload 9
    //   189: aload_1
    //   190: invokevirtual 202	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   193: checkcast 204	java/util/ArrayList
    //   196: ifnonnull -89 -> 107
    //   199: new 204	java/util/ArrayList
    //   202: astore 10
    //   204: aload 10
    //   206: invokespecial 509	java/util/ArrayList:<init>	()V
    //   209: aload 9
    //   211: aload_1
    //   212: aload 10
    //   214: invokevirtual 113	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   217: pop
    //   218: goto -111 -> 107
    //   221: aload_3
    //   222: iconst_m1
    //   223: invokeinterface 512 2 0
    //   228: pop
    //   229: aload_0
    //   230: aload 8
    //   232: invokespecial 223	androidx/work/impl/model/WorkSpecDao_Impl:__fetchRelationshipWorkTagAsjavaLangString	(Landroidx/collection/ArrayMap;)V
    //   235: aload_0
    //   236: aload 9
    //   238: invokespecial 115	androidx/work/impl/model/WorkSpecDao_Impl:__fetchRelationshipWorkProgressAsandroidxWorkData	(Landroidx/collection/ArrayMap;)V
    //   241: new 204	java/util/ArrayList
    //   244: astore 11
    //   246: aload 11
    //   248: aload_3
    //   249: invokeinterface 270 1 0
    //   254: invokespecial 271	java/util/ArrayList:<init>	(I)V
    //   257: aload_3
    //   258: invokeinterface 190 1 0
    //   263: ifeq +189 -> 452
    //   266: aload_3
    //   267: iload 4
    //   269: invokeinterface 194 2 0
    //   274: ifne +23 -> 297
    //   277: aload 8
    //   279: aload_3
    //   280: iload 4
    //   282: invokeinterface 198 2 0
    //   287: invokevirtual 202	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   290: checkcast 204	java/util/ArrayList
    //   293: astore_1
    //   294: goto +5 -> 299
    //   297: aconst_null
    //   298: astore_1
    //   299: aload_1
    //   300: astore 10
    //   302: aload_1
    //   303: ifnonnull +13 -> 316
    //   306: new 204	java/util/ArrayList
    //   309: astore 10
    //   311: aload 10
    //   313: invokespecial 509	java/util/ArrayList:<init>	()V
    //   316: aload_3
    //   317: iload 4
    //   319: invokeinterface 194 2 0
    //   324: ifne +23 -> 347
    //   327: aload 9
    //   329: aload_3
    //   330: iload 4
    //   332: invokeinterface 198 2 0
    //   337: invokevirtual 202	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   340: checkcast 204	java/util/ArrayList
    //   343: astore_1
    //   344: goto +5 -> 349
    //   347: aconst_null
    //   348: astore_1
    //   349: aload_1
    //   350: astore 12
    //   352: aload_1
    //   353: ifnonnull +13 -> 366
    //   356: new 204	java/util/ArrayList
    //   359: astore 12
    //   361: aload 12
    //   363: invokespecial 509	java/util/ArrayList:<init>	()V
    //   366: new 514	androidx/work/impl/model/WorkSpec$WorkInfoPojo
    //   369: astore_1
    //   370: aload_1
    //   371: invokespecial 515	androidx/work/impl/model/WorkSpec$WorkInfoPojo:<init>	()V
    //   374: aload_1
    //   375: aload_3
    //   376: iload 4
    //   378: invokeinterface 198 2 0
    //   383: putfield 516	androidx/work/impl/model/WorkSpec$WorkInfoPojo:id	Ljava/lang/String;
    //   386: aload_1
    //   387: aload_3
    //   388: iload 5
    //   390: invokeinterface 346 2 0
    //   395: invokestatic 397	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
    //   398: putfield 517	androidx/work/impl/model/WorkSpec$WorkInfoPojo:state	Landroidx/work/WorkInfo$State;
    //   401: aload_1
    //   402: aload_3
    //   403: iload 6
    //   405: invokeinterface 208 2 0
    //   410: invokestatic 214	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
    //   413: putfield 518	androidx/work/impl/model/WorkSpec$WorkInfoPojo:output	Landroidx/work/Data;
    //   416: aload_1
    //   417: aload_3
    //   418: iload 7
    //   420: invokeinterface 346 2 0
    //   425: putfield 519	androidx/work/impl/model/WorkSpec$WorkInfoPojo:runAttemptCount	I
    //   428: aload_1
    //   429: aload 10
    //   431: putfield 523	androidx/work/impl/model/WorkSpec$WorkInfoPojo:tags	Ljava/util/List;
    //   434: aload_1
    //   435: aload 12
    //   437: putfield 526	androidx/work/impl/model/WorkSpec$WorkInfoPojo:progress	Ljava/util/List;
    //   440: aload 11
    //   442: aload_1
    //   443: invokeinterface 274 2 0
    //   448: pop
    //   449: goto -192 -> 257
    //   452: aload_0
    //   453: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   456: invokevirtual 256	androidx/room/RoomDatabase:setTransactionSuccessful	()V
    //   459: aload_3
    //   460: invokeinterface 187 1 0
    //   465: aload_2
    //   466: invokevirtual 276	androidx/room/RoomSQLiteQuery:release	()V
    //   469: aload_0
    //   470: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   473: invokevirtual 259	androidx/room/RoomDatabase:endTransaction	()V
    //   476: aload 11
    //   478: areturn
    //   479: astore_1
    //   480: aload_3
    //   481: invokeinterface 187 1 0
    //   486: aload_2
    //   487: invokevirtual 276	androidx/room/RoomSQLiteQuery:release	()V
    //   490: aload_1
    //   491: athrow
    //   492: astore_1
    //   493: aload_0
    //   494: getfield 51	androidx/work/impl/model/WorkSpecDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   497: invokevirtual 259	androidx/room/RoomDatabase:endTransaction	()V
    //   500: aload_1
    //   501: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	502	0	this	WorkSpecDao_Impl
    //   0	502	1	paramString	String
    //   7	480	2	localRoomSQLiteQuery	RoomSQLiteQuery
    //   50	431	3	localCursor	Cursor
    //   58	319	4	i	int
    //   67	322	5	j	int
    //   76	328	6	k	int
    //   85	334	7	m	int
    //   90	188	8	localArrayMap1	ArrayMap
    //   100	228	9	localArrayMap2	ArrayMap
    //   151	279	10	localObject1	Object
    //   244	233	11	localArrayList	ArrayList
    //   350	86	12	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   51	107	479	finally
    //   107	167	479	finally
    //   167	218	479	finally
    //   221	257	479	finally
    //   257	294	479	finally
    //   306	316	479	finally
    //   316	344	479	finally
    //   356	366	479	finally
    //   366	449	479	finally
    //   452	459	479	finally
    //   40	51	492	finally
    //   459	469	492	finally
    //   480	492	492	finally
  }
  
  public LiveData<List<WorkSpec.WorkInfoPojo>> getWorkStatusPojoLiveDataForIds(List<String> paramList)
  {
    Object localObject = StringUtil.newStringBuilder();
    ((StringBuilder)localObject).append("SELECT id, state, output, run_attempt_count FROM workspec WHERE id IN (");
    int i = paramList.size();
    StringUtil.appendPlaceholders((StringBuilder)localObject, i);
    ((StringBuilder)localObject).append(")");
    localObject = RoomSQLiteQuery.acquire(((StringBuilder)localObject).toString(), i + 0);
    paramList = paramList.iterator();
    for (i = 1; paramList.hasNext(); i++)
    {
      String str = (String)paramList.next();
      if (str == null) {
        ((RoomSQLiteQuery)localObject).bindNull(i);
      } else {
        ((RoomSQLiteQuery)localObject).bindString(i, str);
      }
    }
    paramList = this.__db.getInvalidationTracker();
    localObject = new Callable()
    {
      /* Error */
      public List<WorkSpec.WorkInfoPojo> call()
        throws java.lang.Exception
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$10:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   4: invokestatic 38	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   7: invokevirtual 43	androidx/room/RoomDatabase:beginTransaction	()V
        //   10: aload_0
        //   11: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$10:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   14: invokestatic 38	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   17: aload_0
        //   18: getfield 22	androidx/work/impl/model/WorkSpecDao_Impl$10:val$_statement	Landroidx/room/RoomSQLiteQuery;
        //   21: iconst_1
        //   22: aconst_null
        //   23: invokestatic 49	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
        //   26: astore_1
        //   27: aload_1
        //   28: ldc 51
        //   30: invokestatic 57	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   33: istore_2
        //   34: aload_1
        //   35: ldc 59
        //   37: invokestatic 57	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   40: istore_3
        //   41: aload_1
        //   42: ldc 61
        //   44: invokestatic 57	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   47: istore 4
        //   49: aload_1
        //   50: ldc 63
        //   52: invokestatic 57	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   55: istore 5
        //   57: new 65	androidx/collection/ArrayMap
        //   60: astore 6
        //   62: aload 6
        //   64: invokespecial 66	androidx/collection/ArrayMap:<init>	()V
        //   67: new 65	androidx/collection/ArrayMap
        //   70: astore 7
        //   72: aload 7
        //   74: invokespecial 66	androidx/collection/ArrayMap:<init>	()V
        //   77: aload_1
        //   78: invokeinterface 72 1 0
        //   83: ifeq +110 -> 193
        //   86: aload_1
        //   87: iload_2
        //   88: invokeinterface 76 2 0
        //   93: ifne +45 -> 138
        //   96: aload_1
        //   97: iload_2
        //   98: invokeinterface 80 2 0
        //   103: astore 8
        //   105: aload 6
        //   107: aload 8
        //   109: invokevirtual 84	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   112: checkcast 86	java/util/ArrayList
        //   115: ifnonnull +23 -> 138
        //   118: new 86	java/util/ArrayList
        //   121: astore 9
        //   123: aload 9
        //   125: invokespecial 87	java/util/ArrayList:<init>	()V
        //   128: aload 6
        //   130: aload 8
        //   132: aload 9
        //   134: invokevirtual 91	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   137: pop
        //   138: aload_1
        //   139: iload_2
        //   140: invokeinterface 76 2 0
        //   145: ifne -68 -> 77
        //   148: aload_1
        //   149: iload_2
        //   150: invokeinterface 80 2 0
        //   155: astore 8
        //   157: aload 7
        //   159: aload 8
        //   161: invokevirtual 84	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   164: checkcast 86	java/util/ArrayList
        //   167: ifnonnull -90 -> 77
        //   170: new 86	java/util/ArrayList
        //   173: astore 9
        //   175: aload 9
        //   177: invokespecial 87	java/util/ArrayList:<init>	()V
        //   180: aload 7
        //   182: aload 8
        //   184: aload 9
        //   186: invokevirtual 91	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   189: pop
        //   190: goto -113 -> 77
        //   193: aload_1
        //   194: iconst_m1
        //   195: invokeinterface 94 2 0
        //   200: pop
        //   201: aload_0
        //   202: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$10:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   205: aload 6
        //   207: invokestatic 98	androidx/work/impl/model/WorkSpecDao_Impl:access$100	(Landroidx/work/impl/model/WorkSpecDao_Impl;Landroidx/collection/ArrayMap;)V
        //   210: aload_0
        //   211: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$10:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   214: aload 7
        //   216: invokestatic 101	androidx/work/impl/model/WorkSpecDao_Impl:access$200	(Landroidx/work/impl/model/WorkSpecDao_Impl;Landroidx/collection/ArrayMap;)V
        //   219: new 86	java/util/ArrayList
        //   222: astore 10
        //   224: aload 10
        //   226: aload_1
        //   227: invokeinterface 105 1 0
        //   232: invokespecial 108	java/util/ArrayList:<init>	(I)V
        //   235: aload_1
        //   236: invokeinterface 72 1 0
        //   241: ifeq +200 -> 441
        //   244: aload_1
        //   245: iload_2
        //   246: invokeinterface 76 2 0
        //   251: ifne +23 -> 274
        //   254: aload 6
        //   256: aload_1
        //   257: iload_2
        //   258: invokeinterface 80 2 0
        //   263: invokevirtual 84	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   266: checkcast 86	java/util/ArrayList
        //   269: astore 8
        //   271: goto +6 -> 277
        //   274: aconst_null
        //   275: astore 8
        //   277: aload 8
        //   279: astore 9
        //   281: aload 8
        //   283: ifnonnull +13 -> 296
        //   286: new 86	java/util/ArrayList
        //   289: astore 9
        //   291: aload 9
        //   293: invokespecial 87	java/util/ArrayList:<init>	()V
        //   296: aload_1
        //   297: iload_2
        //   298: invokeinterface 76 2 0
        //   303: ifne +23 -> 326
        //   306: aload 7
        //   308: aload_1
        //   309: iload_2
        //   310: invokeinterface 80 2 0
        //   315: invokevirtual 84	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   318: checkcast 86	java/util/ArrayList
        //   321: astore 8
        //   323: goto +6 -> 329
        //   326: aconst_null
        //   327: astore 8
        //   329: aload 8
        //   331: astore 11
        //   333: aload 8
        //   335: ifnonnull +13 -> 348
        //   338: new 86	java/util/ArrayList
        //   341: astore 11
        //   343: aload 11
        //   345: invokespecial 87	java/util/ArrayList:<init>	()V
        //   348: new 110	androidx/work/impl/model/WorkSpec$WorkInfoPojo
        //   351: astore 8
        //   353: aload 8
        //   355: invokespecial 111	androidx/work/impl/model/WorkSpec$WorkInfoPojo:<init>	()V
        //   358: aload 8
        //   360: aload_1
        //   361: iload_2
        //   362: invokeinterface 80 2 0
        //   367: putfield 114	androidx/work/impl/model/WorkSpec$WorkInfoPojo:id	Ljava/lang/String;
        //   370: aload 8
        //   372: aload_1
        //   373: iload_3
        //   374: invokeinterface 118 2 0
        //   379: invokestatic 124	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
        //   382: putfield 127	androidx/work/impl/model/WorkSpec$WorkInfoPojo:state	Landroidx/work/WorkInfo$State;
        //   385: aload 8
        //   387: aload_1
        //   388: iload 4
        //   390: invokeinterface 131 2 0
        //   395: invokestatic 137	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
        //   398: putfield 140	androidx/work/impl/model/WorkSpec$WorkInfoPojo:output	Landroidx/work/Data;
        //   401: aload 8
        //   403: aload_1
        //   404: iload 5
        //   406: invokeinterface 118 2 0
        //   411: putfield 144	androidx/work/impl/model/WorkSpec$WorkInfoPojo:runAttemptCount	I
        //   414: aload 8
        //   416: aload 9
        //   418: putfield 148	androidx/work/impl/model/WorkSpec$WorkInfoPojo:tags	Ljava/util/List;
        //   421: aload 8
        //   423: aload 11
        //   425: putfield 151	androidx/work/impl/model/WorkSpec$WorkInfoPojo:progress	Ljava/util/List;
        //   428: aload 10
        //   430: aload 8
        //   432: invokeinterface 157 2 0
        //   437: pop
        //   438: goto -203 -> 235
        //   441: aload_0
        //   442: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$10:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   445: invokestatic 38	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   448: invokevirtual 160	androidx/room/RoomDatabase:setTransactionSuccessful	()V
        //   451: aload_1
        //   452: invokeinterface 163 1 0
        //   457: aload_0
        //   458: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$10:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   461: invokestatic 38	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   464: invokevirtual 166	androidx/room/RoomDatabase:endTransaction	()V
        //   467: aload 10
        //   469: areturn
        //   470: astore 8
        //   472: aload_1
        //   473: invokeinterface 163 1 0
        //   478: aload 8
        //   480: athrow
        //   481: astore 8
        //   483: aload_0
        //   484: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$10:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   487: invokestatic 38	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   490: invokevirtual 166	androidx/room/RoomDatabase:endTransaction	()V
        //   493: aload 8
        //   495: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	496	0	this	10
        //   26	447	1	localCursor	Cursor
        //   33	329	2	i	int
        //   40	334	3	j	int
        //   47	342	4	k	int
        //   55	350	5	m	int
        //   60	195	6	localArrayMap1	ArrayMap
        //   70	237	7	localArrayMap2	ArrayMap
        //   103	328	8	localObject1	Object
        //   470	9	8	localObject2	Object
        //   481	13	8	localObject3	Object
        //   121	296	9	localObject4	Object
        //   222	246	10	localArrayList	ArrayList
        //   331	93	11	localObject5	Object
        // Exception table:
        //   from	to	target	type
        //   27	77	470	finally
        //   77	138	470	finally
        //   138	190	470	finally
        //   193	235	470	finally
        //   235	271	470	finally
        //   286	296	470	finally
        //   296	323	470	finally
        //   338	348	470	finally
        //   348	438	470	finally
        //   441	451	470	finally
        //   10	27	481	finally
        //   451	457	481	finally
        //   472	481	481	finally
      }
      
      protected void finalize()
      {
        this.val$_statement.release();
      }
    };
    return paramList.createLiveData(new String[] { "WorkTag", "WorkProgress", "workspec" }, true, (Callable)localObject);
  }
  
  public LiveData<List<WorkSpec.WorkInfoPojo>> getWorkStatusPojoLiveDataForName(String paramString)
  {
    Object localObject = RoomSQLiteQuery.acquire("SELECT id, state, output, run_attempt_count FROM workspec WHERE id IN (SELECT work_spec_id FROM workname WHERE name=?)", 1);
    if (paramString == null) {
      ((RoomSQLiteQuery)localObject).bindNull(1);
    } else {
      ((RoomSQLiteQuery)localObject).bindString(1, paramString);
    }
    paramString = this.__db.getInvalidationTracker();
    localObject = new Callable()
    {
      /* Error */
      public List<WorkSpec.WorkInfoPojo> call()
        throws java.lang.Exception
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$12:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   4: invokestatic 38	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   7: invokevirtual 43	androidx/room/RoomDatabase:beginTransaction	()V
        //   10: aload_0
        //   11: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$12:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   14: invokestatic 38	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   17: aload_0
        //   18: getfield 22	androidx/work/impl/model/WorkSpecDao_Impl$12:val$_statement	Landroidx/room/RoomSQLiteQuery;
        //   21: iconst_1
        //   22: aconst_null
        //   23: invokestatic 49	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
        //   26: astore_1
        //   27: aload_1
        //   28: ldc 51
        //   30: invokestatic 57	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   33: istore_2
        //   34: aload_1
        //   35: ldc 59
        //   37: invokestatic 57	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   40: istore_3
        //   41: aload_1
        //   42: ldc 61
        //   44: invokestatic 57	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   47: istore 4
        //   49: aload_1
        //   50: ldc 63
        //   52: invokestatic 57	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   55: istore 5
        //   57: new 65	androidx/collection/ArrayMap
        //   60: astore 6
        //   62: aload 6
        //   64: invokespecial 66	androidx/collection/ArrayMap:<init>	()V
        //   67: new 65	androidx/collection/ArrayMap
        //   70: astore 7
        //   72: aload 7
        //   74: invokespecial 66	androidx/collection/ArrayMap:<init>	()V
        //   77: aload_1
        //   78: invokeinterface 72 1 0
        //   83: ifeq +110 -> 193
        //   86: aload_1
        //   87: iload_2
        //   88: invokeinterface 76 2 0
        //   93: ifne +45 -> 138
        //   96: aload_1
        //   97: iload_2
        //   98: invokeinterface 80 2 0
        //   103: astore 8
        //   105: aload 6
        //   107: aload 8
        //   109: invokevirtual 84	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   112: checkcast 86	java/util/ArrayList
        //   115: ifnonnull +23 -> 138
        //   118: new 86	java/util/ArrayList
        //   121: astore 9
        //   123: aload 9
        //   125: invokespecial 87	java/util/ArrayList:<init>	()V
        //   128: aload 6
        //   130: aload 8
        //   132: aload 9
        //   134: invokevirtual 91	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   137: pop
        //   138: aload_1
        //   139: iload_2
        //   140: invokeinterface 76 2 0
        //   145: ifne -68 -> 77
        //   148: aload_1
        //   149: iload_2
        //   150: invokeinterface 80 2 0
        //   155: astore 9
        //   157: aload 7
        //   159: aload 9
        //   161: invokevirtual 84	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   164: checkcast 86	java/util/ArrayList
        //   167: ifnonnull -90 -> 77
        //   170: new 86	java/util/ArrayList
        //   173: astore 8
        //   175: aload 8
        //   177: invokespecial 87	java/util/ArrayList:<init>	()V
        //   180: aload 7
        //   182: aload 9
        //   184: aload 8
        //   186: invokevirtual 91	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   189: pop
        //   190: goto -113 -> 77
        //   193: aload_1
        //   194: iconst_m1
        //   195: invokeinterface 94 2 0
        //   200: pop
        //   201: aload_0
        //   202: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$12:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   205: aload 6
        //   207: invokestatic 98	androidx/work/impl/model/WorkSpecDao_Impl:access$100	(Landroidx/work/impl/model/WorkSpecDao_Impl;Landroidx/collection/ArrayMap;)V
        //   210: aload_0
        //   211: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$12:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   214: aload 7
        //   216: invokestatic 101	androidx/work/impl/model/WorkSpecDao_Impl:access$200	(Landroidx/work/impl/model/WorkSpecDao_Impl;Landroidx/collection/ArrayMap;)V
        //   219: new 86	java/util/ArrayList
        //   222: astore 10
        //   224: aload 10
        //   226: aload_1
        //   227: invokeinterface 105 1 0
        //   232: invokespecial 108	java/util/ArrayList:<init>	(I)V
        //   235: aload_1
        //   236: invokeinterface 72 1 0
        //   241: ifeq +200 -> 441
        //   244: aload_1
        //   245: iload_2
        //   246: invokeinterface 76 2 0
        //   251: ifne +23 -> 274
        //   254: aload 6
        //   256: aload_1
        //   257: iload_2
        //   258: invokeinterface 80 2 0
        //   263: invokevirtual 84	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   266: checkcast 86	java/util/ArrayList
        //   269: astore 9
        //   271: goto +6 -> 277
        //   274: aconst_null
        //   275: astore 9
        //   277: aload 9
        //   279: astore 8
        //   281: aload 9
        //   283: ifnonnull +13 -> 296
        //   286: new 86	java/util/ArrayList
        //   289: astore 8
        //   291: aload 8
        //   293: invokespecial 87	java/util/ArrayList:<init>	()V
        //   296: aload_1
        //   297: iload_2
        //   298: invokeinterface 76 2 0
        //   303: ifne +23 -> 326
        //   306: aload 7
        //   308: aload_1
        //   309: iload_2
        //   310: invokeinterface 80 2 0
        //   315: invokevirtual 84	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   318: checkcast 86	java/util/ArrayList
        //   321: astore 9
        //   323: goto +6 -> 329
        //   326: aconst_null
        //   327: astore 9
        //   329: aload 9
        //   331: astore 11
        //   333: aload 9
        //   335: ifnonnull +13 -> 348
        //   338: new 86	java/util/ArrayList
        //   341: astore 11
        //   343: aload 11
        //   345: invokespecial 87	java/util/ArrayList:<init>	()V
        //   348: new 110	androidx/work/impl/model/WorkSpec$WorkInfoPojo
        //   351: astore 9
        //   353: aload 9
        //   355: invokespecial 111	androidx/work/impl/model/WorkSpec$WorkInfoPojo:<init>	()V
        //   358: aload 9
        //   360: aload_1
        //   361: iload_2
        //   362: invokeinterface 80 2 0
        //   367: putfield 114	androidx/work/impl/model/WorkSpec$WorkInfoPojo:id	Ljava/lang/String;
        //   370: aload 9
        //   372: aload_1
        //   373: iload_3
        //   374: invokeinterface 118 2 0
        //   379: invokestatic 124	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
        //   382: putfield 127	androidx/work/impl/model/WorkSpec$WorkInfoPojo:state	Landroidx/work/WorkInfo$State;
        //   385: aload 9
        //   387: aload_1
        //   388: iload 4
        //   390: invokeinterface 131 2 0
        //   395: invokestatic 137	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
        //   398: putfield 140	androidx/work/impl/model/WorkSpec$WorkInfoPojo:output	Landroidx/work/Data;
        //   401: aload 9
        //   403: aload_1
        //   404: iload 5
        //   406: invokeinterface 118 2 0
        //   411: putfield 144	androidx/work/impl/model/WorkSpec$WorkInfoPojo:runAttemptCount	I
        //   414: aload 9
        //   416: aload 8
        //   418: putfield 148	androidx/work/impl/model/WorkSpec$WorkInfoPojo:tags	Ljava/util/List;
        //   421: aload 9
        //   423: aload 11
        //   425: putfield 151	androidx/work/impl/model/WorkSpec$WorkInfoPojo:progress	Ljava/util/List;
        //   428: aload 10
        //   430: aload 9
        //   432: invokeinterface 157 2 0
        //   437: pop
        //   438: goto -203 -> 235
        //   441: aload_0
        //   442: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$12:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   445: invokestatic 38	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   448: invokevirtual 160	androidx/room/RoomDatabase:setTransactionSuccessful	()V
        //   451: aload_1
        //   452: invokeinterface 163 1 0
        //   457: aload_0
        //   458: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$12:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   461: invokestatic 38	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   464: invokevirtual 166	androidx/room/RoomDatabase:endTransaction	()V
        //   467: aload 10
        //   469: areturn
        //   470: astore 9
        //   472: aload_1
        //   473: invokeinterface 163 1 0
        //   478: aload 9
        //   480: athrow
        //   481: astore 9
        //   483: aload_0
        //   484: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$12:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   487: invokestatic 38	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   490: invokevirtual 166	androidx/room/RoomDatabase:endTransaction	()V
        //   493: aload 9
        //   495: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	496	0	this	12
        //   26	447	1	localCursor	Cursor
        //   33	329	2	i	int
        //   40	334	3	j	int
        //   47	342	4	k	int
        //   55	350	5	m	int
        //   60	195	6	localArrayMap1	ArrayMap
        //   70	237	7	localArrayMap2	ArrayMap
        //   103	314	8	localObject1	Object
        //   121	310	9	localObject2	Object
        //   470	9	9	localObject3	Object
        //   481	13	9	localObject4	Object
        //   222	246	10	localArrayList	ArrayList
        //   331	93	11	localObject5	Object
        // Exception table:
        //   from	to	target	type
        //   27	77	470	finally
        //   77	138	470	finally
        //   138	190	470	finally
        //   193	235	470	finally
        //   235	271	470	finally
        //   286	296	470	finally
        //   296	323	470	finally
        //   338	348	470	finally
        //   348	438	470	finally
        //   441	451	470	finally
        //   10	27	481	finally
        //   451	457	481	finally
        //   472	481	481	finally
      }
      
      protected void finalize()
      {
        this.val$_statement.release();
      }
    };
    return paramString.createLiveData(new String[] { "WorkTag", "WorkProgress", "workspec", "workname" }, true, (Callable)localObject);
  }
  
  public LiveData<List<WorkSpec.WorkInfoPojo>> getWorkStatusPojoLiveDataForTag(String paramString)
  {
    Object localObject = RoomSQLiteQuery.acquire("SELECT id, state, output, run_attempt_count FROM workspec WHERE id IN (SELECT work_spec_id FROM worktag WHERE tag=?)", 1);
    if (paramString == null) {
      ((RoomSQLiteQuery)localObject).bindNull(1);
    } else {
      ((RoomSQLiteQuery)localObject).bindString(1, paramString);
    }
    paramString = this.__db.getInvalidationTracker();
    localObject = new Callable()
    {
      /* Error */
      public List<WorkSpec.WorkInfoPojo> call()
        throws java.lang.Exception
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$11:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   4: invokestatic 38	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   7: invokevirtual 43	androidx/room/RoomDatabase:beginTransaction	()V
        //   10: aload_0
        //   11: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$11:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   14: invokestatic 38	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   17: aload_0
        //   18: getfield 22	androidx/work/impl/model/WorkSpecDao_Impl$11:val$_statement	Landroidx/room/RoomSQLiteQuery;
        //   21: iconst_1
        //   22: aconst_null
        //   23: invokestatic 49	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
        //   26: astore_1
        //   27: aload_1
        //   28: ldc 51
        //   30: invokestatic 57	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   33: istore_2
        //   34: aload_1
        //   35: ldc 59
        //   37: invokestatic 57	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   40: istore_3
        //   41: aload_1
        //   42: ldc 61
        //   44: invokestatic 57	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   47: istore 4
        //   49: aload_1
        //   50: ldc 63
        //   52: invokestatic 57	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
        //   55: istore 5
        //   57: new 65	androidx/collection/ArrayMap
        //   60: astore 6
        //   62: aload 6
        //   64: invokespecial 66	androidx/collection/ArrayMap:<init>	()V
        //   67: new 65	androidx/collection/ArrayMap
        //   70: astore 7
        //   72: aload 7
        //   74: invokespecial 66	androidx/collection/ArrayMap:<init>	()V
        //   77: aload_1
        //   78: invokeinterface 72 1 0
        //   83: ifeq +110 -> 193
        //   86: aload_1
        //   87: iload_2
        //   88: invokeinterface 76 2 0
        //   93: ifne +45 -> 138
        //   96: aload_1
        //   97: iload_2
        //   98: invokeinterface 80 2 0
        //   103: astore 8
        //   105: aload 6
        //   107: aload 8
        //   109: invokevirtual 84	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   112: checkcast 86	java/util/ArrayList
        //   115: ifnonnull +23 -> 138
        //   118: new 86	java/util/ArrayList
        //   121: astore 9
        //   123: aload 9
        //   125: invokespecial 87	java/util/ArrayList:<init>	()V
        //   128: aload 6
        //   130: aload 8
        //   132: aload 9
        //   134: invokevirtual 91	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   137: pop
        //   138: aload_1
        //   139: iload_2
        //   140: invokeinterface 76 2 0
        //   145: ifne -68 -> 77
        //   148: aload_1
        //   149: iload_2
        //   150: invokeinterface 80 2 0
        //   155: astore 8
        //   157: aload 7
        //   159: aload 8
        //   161: invokevirtual 84	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   164: checkcast 86	java/util/ArrayList
        //   167: ifnonnull -90 -> 77
        //   170: new 86	java/util/ArrayList
        //   173: astore 9
        //   175: aload 9
        //   177: invokespecial 87	java/util/ArrayList:<init>	()V
        //   180: aload 7
        //   182: aload 8
        //   184: aload 9
        //   186: invokevirtual 91	androidx/collection/ArrayMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   189: pop
        //   190: goto -113 -> 77
        //   193: aload_1
        //   194: iconst_m1
        //   195: invokeinterface 94 2 0
        //   200: pop
        //   201: aload_0
        //   202: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$11:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   205: aload 6
        //   207: invokestatic 98	androidx/work/impl/model/WorkSpecDao_Impl:access$100	(Landroidx/work/impl/model/WorkSpecDao_Impl;Landroidx/collection/ArrayMap;)V
        //   210: aload_0
        //   211: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$11:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   214: aload 7
        //   216: invokestatic 101	androidx/work/impl/model/WorkSpecDao_Impl:access$200	(Landroidx/work/impl/model/WorkSpecDao_Impl;Landroidx/collection/ArrayMap;)V
        //   219: new 86	java/util/ArrayList
        //   222: astore 10
        //   224: aload 10
        //   226: aload_1
        //   227: invokeinterface 105 1 0
        //   232: invokespecial 108	java/util/ArrayList:<init>	(I)V
        //   235: aload_1
        //   236: invokeinterface 72 1 0
        //   241: ifeq +200 -> 441
        //   244: aload_1
        //   245: iload_2
        //   246: invokeinterface 76 2 0
        //   251: ifne +23 -> 274
        //   254: aload 6
        //   256: aload_1
        //   257: iload_2
        //   258: invokeinterface 80 2 0
        //   263: invokevirtual 84	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   266: checkcast 86	java/util/ArrayList
        //   269: astore 8
        //   271: goto +6 -> 277
        //   274: aconst_null
        //   275: astore 8
        //   277: aload 8
        //   279: astore 9
        //   281: aload 8
        //   283: ifnonnull +13 -> 296
        //   286: new 86	java/util/ArrayList
        //   289: astore 9
        //   291: aload 9
        //   293: invokespecial 87	java/util/ArrayList:<init>	()V
        //   296: aload_1
        //   297: iload_2
        //   298: invokeinterface 76 2 0
        //   303: ifne +23 -> 326
        //   306: aload 7
        //   308: aload_1
        //   309: iload_2
        //   310: invokeinterface 80 2 0
        //   315: invokevirtual 84	androidx/collection/ArrayMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
        //   318: checkcast 86	java/util/ArrayList
        //   321: astore 8
        //   323: goto +6 -> 329
        //   326: aconst_null
        //   327: astore 8
        //   329: aload 8
        //   331: astore 11
        //   333: aload 8
        //   335: ifnonnull +13 -> 348
        //   338: new 86	java/util/ArrayList
        //   341: astore 11
        //   343: aload 11
        //   345: invokespecial 87	java/util/ArrayList:<init>	()V
        //   348: new 110	androidx/work/impl/model/WorkSpec$WorkInfoPojo
        //   351: astore 8
        //   353: aload 8
        //   355: invokespecial 111	androidx/work/impl/model/WorkSpec$WorkInfoPojo:<init>	()V
        //   358: aload 8
        //   360: aload_1
        //   361: iload_2
        //   362: invokeinterface 80 2 0
        //   367: putfield 114	androidx/work/impl/model/WorkSpec$WorkInfoPojo:id	Ljava/lang/String;
        //   370: aload 8
        //   372: aload_1
        //   373: iload_3
        //   374: invokeinterface 118 2 0
        //   379: invokestatic 124	androidx/work/impl/model/WorkTypeConverters:intToState	(I)Landroidx/work/WorkInfo$State;
        //   382: putfield 127	androidx/work/impl/model/WorkSpec$WorkInfoPojo:state	Landroidx/work/WorkInfo$State;
        //   385: aload 8
        //   387: aload_1
        //   388: iload 4
        //   390: invokeinterface 131 2 0
        //   395: invokestatic 137	androidx/work/Data:fromByteArray	([B)Landroidx/work/Data;
        //   398: putfield 140	androidx/work/impl/model/WorkSpec$WorkInfoPojo:output	Landroidx/work/Data;
        //   401: aload 8
        //   403: aload_1
        //   404: iload 5
        //   406: invokeinterface 118 2 0
        //   411: putfield 144	androidx/work/impl/model/WorkSpec$WorkInfoPojo:runAttemptCount	I
        //   414: aload 8
        //   416: aload 9
        //   418: putfield 148	androidx/work/impl/model/WorkSpec$WorkInfoPojo:tags	Ljava/util/List;
        //   421: aload 8
        //   423: aload 11
        //   425: putfield 151	androidx/work/impl/model/WorkSpec$WorkInfoPojo:progress	Ljava/util/List;
        //   428: aload 10
        //   430: aload 8
        //   432: invokeinterface 157 2 0
        //   437: pop
        //   438: goto -203 -> 235
        //   441: aload_0
        //   442: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$11:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   445: invokestatic 38	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   448: invokevirtual 160	androidx/room/RoomDatabase:setTransactionSuccessful	()V
        //   451: aload_1
        //   452: invokeinterface 163 1 0
        //   457: aload_0
        //   458: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$11:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   461: invokestatic 38	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   464: invokevirtual 166	androidx/room/RoomDatabase:endTransaction	()V
        //   467: aload 10
        //   469: areturn
        //   470: astore 8
        //   472: aload_1
        //   473: invokeinterface 163 1 0
        //   478: aload 8
        //   480: athrow
        //   481: astore 8
        //   483: aload_0
        //   484: getfield 20	androidx/work/impl/model/WorkSpecDao_Impl$11:this$0	Landroidx/work/impl/model/WorkSpecDao_Impl;
        //   487: invokestatic 38	androidx/work/impl/model/WorkSpecDao_Impl:access$000	(Landroidx/work/impl/model/WorkSpecDao_Impl;)Landroidx/room/RoomDatabase;
        //   490: invokevirtual 166	androidx/room/RoomDatabase:endTransaction	()V
        //   493: aload 8
        //   495: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	496	0	this	11
        //   26	447	1	localCursor	Cursor
        //   33	329	2	i	int
        //   40	334	3	j	int
        //   47	342	4	k	int
        //   55	350	5	m	int
        //   60	195	6	localArrayMap1	ArrayMap
        //   70	237	7	localArrayMap2	ArrayMap
        //   103	328	8	localObject1	Object
        //   470	9	8	localObject2	Object
        //   481	13	8	localObject3	Object
        //   121	296	9	localObject4	Object
        //   222	246	10	localArrayList	ArrayList
        //   331	93	11	localObject5	Object
        // Exception table:
        //   from	to	target	type
        //   27	77	470	finally
        //   77	138	470	finally
        //   138	190	470	finally
        //   193	235	470	finally
        //   235	271	470	finally
        //   286	296	470	finally
        //   296	323	470	finally
        //   338	348	470	finally
        //   348	438	470	finally
        //   441	451	470	finally
        //   10	27	481	finally
        //   451	457	481	finally
        //   472	481	481	finally
      }
      
      protected void finalize()
      {
        this.val$_statement.release();
      }
    };
    return paramString.createLiveData(new String[] { "WorkTag", "WorkProgress", "workspec", "worktag" }, true, (Callable)localObject);
  }
  
  public int incrementWorkSpecRunAttemptCount(String paramString)
  {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement localSupportSQLiteStatement = this.__preparedStmtOfIncrementWorkSpecRunAttemptCount.acquire();
    if (paramString == null) {
      localSupportSQLiteStatement.bindNull(1);
    } else {
      localSupportSQLiteStatement.bindString(1, paramString);
    }
    this.__db.beginTransaction();
    try
    {
      int i = localSupportSQLiteStatement.executeUpdateDelete();
      this.__db.setTransactionSuccessful();
      return i;
    }
    finally
    {
      this.__db.endTransaction();
      this.__preparedStmtOfIncrementWorkSpecRunAttemptCount.release(localSupportSQLiteStatement);
    }
  }
  
  public void insertWorkSpec(WorkSpec paramWorkSpec)
  {
    this.__db.assertNotSuspendingTransaction();
    this.__db.beginTransaction();
    try
    {
      this.__insertionAdapterOfWorkSpec.insert(paramWorkSpec);
      this.__db.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.__db.endTransaction();
    }
  }
  
  public int markWorkSpecScheduled(String paramString, long paramLong)
  {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement localSupportSQLiteStatement = this.__preparedStmtOfMarkWorkSpecScheduled.acquire();
    localSupportSQLiteStatement.bindLong(1, paramLong);
    if (paramString == null) {
      localSupportSQLiteStatement.bindNull(2);
    } else {
      localSupportSQLiteStatement.bindString(2, paramString);
    }
    this.__db.beginTransaction();
    try
    {
      int i = localSupportSQLiteStatement.executeUpdateDelete();
      this.__db.setTransactionSuccessful();
      return i;
    }
    finally
    {
      this.__db.endTransaction();
      this.__preparedStmtOfMarkWorkSpecScheduled.release(localSupportSQLiteStatement);
    }
  }
  
  public void pruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast()
  {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement localSupportSQLiteStatement = this.__preparedStmtOfPruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast.acquire();
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
      this.__preparedStmtOfPruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast.release(localSupportSQLiteStatement);
    }
  }
  
  public int resetScheduledState()
  {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement localSupportSQLiteStatement = this.__preparedStmtOfResetScheduledState.acquire();
    this.__db.beginTransaction();
    try
    {
      int i = localSupportSQLiteStatement.executeUpdateDelete();
      this.__db.setTransactionSuccessful();
      return i;
    }
    finally
    {
      this.__db.endTransaction();
      this.__preparedStmtOfResetScheduledState.release(localSupportSQLiteStatement);
    }
  }
  
  public int resetWorkSpecRunAttemptCount(String paramString)
  {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement localSupportSQLiteStatement = this.__preparedStmtOfResetWorkSpecRunAttemptCount.acquire();
    if (paramString == null) {
      localSupportSQLiteStatement.bindNull(1);
    } else {
      localSupportSQLiteStatement.bindString(1, paramString);
    }
    this.__db.beginTransaction();
    try
    {
      int i = localSupportSQLiteStatement.executeUpdateDelete();
      this.__db.setTransactionSuccessful();
      return i;
    }
    finally
    {
      this.__db.endTransaction();
      this.__preparedStmtOfResetWorkSpecRunAttemptCount.release(localSupportSQLiteStatement);
    }
  }
  
  public void setOutput(String paramString, Data paramData)
  {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement localSupportSQLiteStatement = this.__preparedStmtOfSetOutput.acquire();
    paramData = Data.toByteArray(paramData);
    if (paramData == null) {
      localSupportSQLiteStatement.bindNull(1);
    } else {
      localSupportSQLiteStatement.bindBlob(1, paramData);
    }
    if (paramString == null) {
      localSupportSQLiteStatement.bindNull(2);
    } else {
      localSupportSQLiteStatement.bindString(2, paramString);
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
      this.__preparedStmtOfSetOutput.release(localSupportSQLiteStatement);
    }
  }
  
  public void setPeriodStartTime(String paramString, long paramLong)
  {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement localSupportSQLiteStatement = this.__preparedStmtOfSetPeriodStartTime.acquire();
    localSupportSQLiteStatement.bindLong(1, paramLong);
    if (paramString == null) {
      localSupportSQLiteStatement.bindNull(2);
    } else {
      localSupportSQLiteStatement.bindString(2, paramString);
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
      this.__preparedStmtOfSetPeriodStartTime.release(localSupportSQLiteStatement);
    }
  }
  
  public int setState(WorkInfo.State paramState, String... paramVarArgs)
  {
    this.__db.assertNotSuspendingTransaction();
    Object localObject = StringUtil.newStringBuilder();
    ((StringBuilder)localObject).append("UPDATE workspec SET state=");
    ((StringBuilder)localObject).append("?");
    ((StringBuilder)localObject).append(" WHERE id IN (");
    StringUtil.appendPlaceholders((StringBuilder)localObject, paramVarArgs.length);
    ((StringBuilder)localObject).append(")");
    localObject = ((StringBuilder)localObject).toString();
    localObject = this.__db.compileStatement((String)localObject);
    ((SupportSQLiteStatement)localObject).bindLong(1, WorkTypeConverters.stateToInt(paramState));
    int i = paramVarArgs.length;
    int j = 2;
    for (int k = 0; k < i; k++)
    {
      paramState = paramVarArgs[k];
      if (paramState == null) {
        ((SupportSQLiteStatement)localObject).bindNull(j);
      } else {
        ((SupportSQLiteStatement)localObject).bindString(j, paramState);
      }
      j++;
    }
    this.__db.beginTransaction();
    try
    {
      k = ((SupportSQLiteStatement)localObject).executeUpdateDelete();
      this.__db.setTransactionSuccessful();
      return k;
    }
    finally
    {
      this.__db.endTransaction();
    }
  }
}

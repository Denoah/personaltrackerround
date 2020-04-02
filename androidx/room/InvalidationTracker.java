package androidx.room;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

public class InvalidationTracker
{
  private static final String CREATE_TRACKING_TABLE_SQL = "CREATE TEMP TABLE room_table_modification_log(table_id INTEGER PRIMARY KEY, invalidated INTEGER NOT NULL DEFAULT 0)";
  private static final String INVALIDATED_COLUMN_NAME = "invalidated";
  static final String RESET_UPDATED_TABLES_SQL = "UPDATE room_table_modification_log SET invalidated = 0 WHERE invalidated = 1 ";
  static final String SELECT_UPDATED_TABLES_SQL = "SELECT * FROM room_table_modification_log WHERE invalidated = 1;";
  private static final String TABLE_ID_COLUMN_NAME = "table_id";
  private static final String[] TRIGGERS = { "UPDATE", "DELETE", "INSERT" };
  private static final String UPDATE_TABLE_NAME = "room_table_modification_log";
  volatile SupportSQLiteStatement mCleanupStatement;
  final RoomDatabase mDatabase;
  private volatile boolean mInitialized;
  private final InvalidationLiveDataContainer mInvalidationLiveDataContainer;
  private MultiInstanceInvalidationClient mMultiInstanceInvalidationClient;
  private ObservedTableTracker mObservedTableTracker;
  final SafeIterableMap<Observer, ObserverWrapper> mObserverMap;
  AtomicBoolean mPendingRefresh;
  Runnable mRefreshRunnable;
  final HashMap<String, Integer> mTableIdLookup;
  final String[] mTableNames;
  private Map<String, Set<String>> mViewTables;
  
  public InvalidationTracker(RoomDatabase paramRoomDatabase, Map<String, String> paramMap, Map<String, Set<String>> paramMap1, String... paramVarArgs)
  {
    int i = 0;
    this.mPendingRefresh = new AtomicBoolean(false);
    this.mInitialized = false;
    this.mObserverMap = new SafeIterableMap();
    this.mRefreshRunnable = new Runnable()
    {
      private Set<Integer> checkUpdatedTable()
      {
        HashSet localHashSet = new HashSet();
        Cursor localCursor = InvalidationTracker.this.mDatabase.query(new SimpleSQLiteQuery("SELECT * FROM room_table_modification_log WHERE invalidated = 1;"));
        try
        {
          while (localCursor.moveToNext()) {
            localHashSet.add(Integer.valueOf(localCursor.getInt(0)));
          }
          localCursor.close();
          if (!localHashSet.isEmpty()) {
            InvalidationTracker.this.mCleanupStatement.executeUpdateDelete();
          }
          return localHashSet;
        }
        finally
        {
          localCursor.close();
        }
      }
      
      /* Error */
      public void run()
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 14	androidx/room/InvalidationTracker$1:this$0	Landroidx/room/InvalidationTracker;
        //   4: getfield 27	androidx/room/InvalidationTracker:mDatabase	Landroidx/room/RoomDatabase;
        //   7: invokevirtual 87	androidx/room/RoomDatabase:getCloseLock	()Ljava/util/concurrent/locks/Lock;
        //   10: astore_1
        //   11: aconst_null
        //   12: astore_2
        //   13: aconst_null
        //   14: astore_3
        //   15: aconst_null
        //   16: astore 4
        //   18: aload_2
        //   19: astore 5
        //   21: aload_3
        //   22: astore 6
        //   24: aload_1
        //   25: invokeinterface 92 1 0
        //   30: aload_2
        //   31: astore 5
        //   33: aload_3
        //   34: astore 6
        //   36: aload_0
        //   37: getfield 14	androidx/room/InvalidationTracker$1:this$0	Landroidx/room/InvalidationTracker;
        //   40: invokevirtual 95	androidx/room/InvalidationTracker:ensureInitialization	()Z
        //   43: istore 7
        //   45: iload 7
        //   47: ifne +10 -> 57
        //   50: aload_1
        //   51: invokeinterface 98 1 0
        //   56: return
        //   57: aload_2
        //   58: astore 5
        //   60: aload_3
        //   61: astore 6
        //   63: aload_0
        //   64: getfield 14	androidx/room/InvalidationTracker$1:this$0	Landroidx/room/InvalidationTracker;
        //   67: getfield 102	androidx/room/InvalidationTracker:mPendingRefresh	Ljava/util/concurrent/atomic/AtomicBoolean;
        //   70: iconst_1
        //   71: iconst_0
        //   72: invokevirtual 108	java/util/concurrent/atomic/AtomicBoolean:compareAndSet	(ZZ)Z
        //   75: istore 7
        //   77: iload 7
        //   79: ifne +10 -> 89
        //   82: aload_1
        //   83: invokeinterface 98 1 0
        //   88: return
        //   89: aload_2
        //   90: astore 5
        //   92: aload_3
        //   93: astore 6
        //   95: aload_0
        //   96: getfield 14	androidx/room/InvalidationTracker$1:this$0	Landroidx/room/InvalidationTracker;
        //   99: getfield 27	androidx/room/InvalidationTracker:mDatabase	Landroidx/room/RoomDatabase;
        //   102: invokevirtual 111	androidx/room/RoomDatabase:inTransaction	()Z
        //   105: istore 7
        //   107: iload 7
        //   109: ifeq +10 -> 119
        //   112: aload_1
        //   113: invokeinterface 98 1 0
        //   118: return
        //   119: aload_2
        //   120: astore 5
        //   122: aload_3
        //   123: astore 6
        //   125: aload_0
        //   126: getfield 14	androidx/room/InvalidationTracker$1:this$0	Landroidx/room/InvalidationTracker;
        //   129: getfield 27	androidx/room/InvalidationTracker:mDatabase	Landroidx/room/RoomDatabase;
        //   132: getfield 115	androidx/room/RoomDatabase:mWriteAheadLoggingEnabled	Z
        //   135: ifeq +99 -> 234
        //   138: aload_2
        //   139: astore 5
        //   141: aload_3
        //   142: astore 6
        //   144: aload_0
        //   145: getfield 14	androidx/room/InvalidationTracker$1:this$0	Landroidx/room/InvalidationTracker;
        //   148: getfield 27	androidx/room/InvalidationTracker:mDatabase	Landroidx/room/RoomDatabase;
        //   151: invokevirtual 119	androidx/room/RoomDatabase:getOpenHelper	()Landroidx/sqlite/db/SupportSQLiteOpenHelper;
        //   154: invokeinterface 125 1 0
        //   159: astore 8
        //   161: aload_2
        //   162: astore 5
        //   164: aload_3
        //   165: astore 6
        //   167: aload 8
        //   169: invokeinterface 130 1 0
        //   174: aload_0
        //   175: invokespecial 132	androidx/room/InvalidationTracker$1:checkUpdatedTable	()Ljava/util/Set;
        //   178: astore_2
        //   179: aload_2
        //   180: astore 4
        //   182: aload 8
        //   184: invokeinterface 135 1 0
        //   189: aload_2
        //   190: astore 5
        //   192: aload_2
        //   193: astore 6
        //   195: aload 8
        //   197: invokeinterface 138 1 0
        //   202: aload_2
        //   203: astore 6
        //   205: goto +74 -> 279
        //   208: astore_2
        //   209: aload 4
        //   211: astore 5
        //   213: aload 4
        //   215: astore 6
        //   217: aload 8
        //   219: invokeinterface 138 1 0
        //   224: aload 4
        //   226: astore 5
        //   228: aload 4
        //   230: astore 6
        //   232: aload_2
        //   233: athrow
        //   234: aload_2
        //   235: astore 5
        //   237: aload_3
        //   238: astore 6
        //   240: aload_0
        //   241: invokespecial 132	androidx/room/InvalidationTracker$1:checkUpdatedTable	()Ljava/util/Set;
        //   244: astore 4
        //   246: aload 4
        //   248: astore 6
        //   250: goto +29 -> 279
        //   253: astore 6
        //   255: goto +120 -> 375
        //   258: astore 4
        //   260: aload 5
        //   262: astore 6
        //   264: goto +5 -> 269
        //   267: astore 4
        //   269: ldc -116
        //   271: ldc -114
        //   273: aload 4
        //   275: invokestatic 148	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
        //   278: pop
        //   279: aload_1
        //   280: invokeinterface 98 1 0
        //   285: aload 6
        //   287: ifnull +87 -> 374
        //   290: aload 6
        //   292: invokeinterface 151 1 0
        //   297: ifne +77 -> 374
        //   300: aload_0
        //   301: getfield 14	androidx/room/InvalidationTracker$1:this$0	Landroidx/room/InvalidationTracker;
        //   304: getfield 155	androidx/room/InvalidationTracker:mObserverMap	Landroidx/arch/core/internal/SafeIterableMap;
        //   307: astore 5
        //   309: aload 5
        //   311: monitorenter
        //   312: aload_0
        //   313: getfield 14	androidx/room/InvalidationTracker$1:this$0	Landroidx/room/InvalidationTracker;
        //   316: getfield 155	androidx/room/InvalidationTracker:mObserverMap	Landroidx/arch/core/internal/SafeIterableMap;
        //   319: invokevirtual 161	androidx/arch/core/internal/SafeIterableMap:iterator	()Ljava/util/Iterator;
        //   322: astore 4
        //   324: aload 4
        //   326: invokeinterface 166 1 0
        //   331: ifeq +29 -> 360
        //   334: aload 4
        //   336: invokeinterface 170 1 0
        //   341: checkcast 172	java/util/Map$Entry
        //   344: invokeinterface 175 1 0
        //   349: checkcast 177	androidx/room/InvalidationTracker$ObserverWrapper
        //   352: aload 6
        //   354: invokevirtual 181	androidx/room/InvalidationTracker$ObserverWrapper:notifyByTableInvalidStatus	(Ljava/util/Set;)V
        //   357: goto -33 -> 324
        //   360: aload 5
        //   362: monitorexit
        //   363: goto +11 -> 374
        //   366: astore 6
        //   368: aload 5
        //   370: monitorexit
        //   371: aload 6
        //   373: athrow
        //   374: return
        //   375: aload_1
        //   376: invokeinterface 98 1 0
        //   381: aload 6
        //   383: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	384	0	this	1
        //   10	366	1	localLock	Lock
        //   12	191	2	localSet1	Set
        //   208	27	2	localObject1	Object
        //   14	224	3	localObject2	Object
        //   16	231	4	localSet2	Set
        //   258	1	4	localSQLiteException	SQLiteException
        //   267	7	4	localIllegalStateException	IllegalStateException
        //   322	13	4	localIterator	Iterator
        //   19	350	5	localObject3	Object
        //   22	227	6	localObject4	Object
        //   253	1	6	localObject5	Object
        //   262	91	6	localObject6	Object
        //   366	16	6	localObject7	Object
        //   43	65	7	bool	boolean
        //   159	59	8	localSupportSQLiteDatabase	SupportSQLiteDatabase
        // Exception table:
        //   from	to	target	type
        //   174	179	208	finally
        //   182	189	208	finally
        //   24	30	253	finally
        //   36	45	253	finally
        //   63	77	253	finally
        //   95	107	253	finally
        //   125	138	253	finally
        //   144	161	253	finally
        //   167	174	253	finally
        //   195	202	253	finally
        //   217	224	253	finally
        //   232	234	253	finally
        //   240	246	253	finally
        //   269	279	253	finally
        //   24	30	258	android/database/sqlite/SQLiteException
        //   36	45	258	android/database/sqlite/SQLiteException
        //   63	77	258	android/database/sqlite/SQLiteException
        //   95	107	258	android/database/sqlite/SQLiteException
        //   125	138	258	android/database/sqlite/SQLiteException
        //   144	161	258	android/database/sqlite/SQLiteException
        //   167	174	258	android/database/sqlite/SQLiteException
        //   195	202	258	android/database/sqlite/SQLiteException
        //   217	224	258	android/database/sqlite/SQLiteException
        //   232	234	258	android/database/sqlite/SQLiteException
        //   240	246	258	android/database/sqlite/SQLiteException
        //   24	30	267	java/lang/IllegalStateException
        //   36	45	267	java/lang/IllegalStateException
        //   63	77	267	java/lang/IllegalStateException
        //   95	107	267	java/lang/IllegalStateException
        //   125	138	267	java/lang/IllegalStateException
        //   144	161	267	java/lang/IllegalStateException
        //   167	174	267	java/lang/IllegalStateException
        //   195	202	267	java/lang/IllegalStateException
        //   217	224	267	java/lang/IllegalStateException
        //   232	234	267	java/lang/IllegalStateException
        //   240	246	267	java/lang/IllegalStateException
        //   312	324	366	finally
        //   324	357	366	finally
        //   360	363	366	finally
        //   368	371	366	finally
      }
    };
    this.mDatabase = paramRoomDatabase;
    this.mObservedTableTracker = new ObservedTableTracker(paramVarArgs.length);
    this.mTableIdLookup = new HashMap();
    this.mViewTables = paramMap1;
    this.mInvalidationLiveDataContainer = new InvalidationLiveDataContainer(this.mDatabase);
    int j = paramVarArgs.length;
    this.mTableNames = new String[j];
    while (i < j)
    {
      paramRoomDatabase = paramVarArgs[i].toLowerCase(Locale.US);
      this.mTableIdLookup.put(paramRoomDatabase, Integer.valueOf(i));
      paramMap1 = (String)paramMap.get(paramVarArgs[i]);
      if (paramMap1 != null) {
        this.mTableNames[i] = paramMap1.toLowerCase(Locale.US);
      } else {
        this.mTableNames[i] = paramRoomDatabase;
      }
      i++;
    }
    paramRoomDatabase = paramMap.entrySet().iterator();
    while (paramRoomDatabase.hasNext())
    {
      paramMap1 = (Map.Entry)paramRoomDatabase.next();
      paramMap = ((String)paramMap1.getValue()).toLowerCase(Locale.US);
      if (this.mTableIdLookup.containsKey(paramMap))
      {
        paramVarArgs = ((String)paramMap1.getKey()).toLowerCase(Locale.US);
        paramMap1 = this.mTableIdLookup;
        paramMap1.put(paramVarArgs, paramMap1.get(paramMap));
      }
    }
  }
  
  public InvalidationTracker(RoomDatabase paramRoomDatabase, String... paramVarArgs)
  {
    this(paramRoomDatabase, new HashMap(), Collections.emptyMap(), paramVarArgs);
  }
  
  private static void appendTriggerName(StringBuilder paramStringBuilder, String paramString1, String paramString2)
  {
    paramStringBuilder.append("`");
    paramStringBuilder.append("room_table_modification_trigger_");
    paramStringBuilder.append(paramString1);
    paramStringBuilder.append("_");
    paramStringBuilder.append(paramString2);
    paramStringBuilder.append("`");
  }
  
  private String[] resolveViews(String[] paramArrayOfString)
  {
    HashSet localHashSet = new HashSet();
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++)
    {
      String str1 = paramArrayOfString[j];
      String str2 = str1.toLowerCase(Locale.US);
      if (this.mViewTables.containsKey(str2)) {
        localHashSet.addAll((Collection)this.mViewTables.get(str2));
      } else {
        localHashSet.add(str1);
      }
    }
    return (String[])localHashSet.toArray(new String[localHashSet.size()]);
  }
  
  private void startTrackingTable(SupportSQLiteDatabase paramSupportSQLiteDatabase, int paramInt)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("INSERT OR IGNORE INTO room_table_modification_log VALUES(");
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).append(", 0)");
    paramSupportSQLiteDatabase.execSQL(((StringBuilder)localObject).toString());
    localObject = this.mTableNames[paramInt];
    StringBuilder localStringBuilder = new StringBuilder();
    for (String str : TRIGGERS)
    {
      localStringBuilder.setLength(0);
      localStringBuilder.append("CREATE TEMP TRIGGER IF NOT EXISTS ");
      appendTriggerName(localStringBuilder, (String)localObject, str);
      localStringBuilder.append(" AFTER ");
      localStringBuilder.append(str);
      localStringBuilder.append(" ON `");
      localStringBuilder.append((String)localObject);
      localStringBuilder.append("` BEGIN UPDATE ");
      localStringBuilder.append("room_table_modification_log");
      localStringBuilder.append(" SET ");
      localStringBuilder.append("invalidated");
      localStringBuilder.append(" = 1");
      localStringBuilder.append(" WHERE ");
      localStringBuilder.append("table_id");
      localStringBuilder.append(" = ");
      localStringBuilder.append(paramInt);
      localStringBuilder.append(" AND ");
      localStringBuilder.append("invalidated");
      localStringBuilder.append(" = 0");
      localStringBuilder.append("; END");
      paramSupportSQLiteDatabase.execSQL(localStringBuilder.toString());
    }
  }
  
  private void stopTrackingTable(SupportSQLiteDatabase paramSupportSQLiteDatabase, int paramInt)
  {
    String str1 = this.mTableNames[paramInt];
    StringBuilder localStringBuilder = new StringBuilder();
    for (String str2 : TRIGGERS)
    {
      localStringBuilder.setLength(0);
      localStringBuilder.append("DROP TRIGGER IF EXISTS ");
      appendTriggerName(localStringBuilder, str1, str2);
      paramSupportSQLiteDatabase.execSQL(localStringBuilder.toString());
    }
  }
  
  private String[] validateAndResolveTableNames(String[] paramArrayOfString)
  {
    Object localObject = resolveViews(paramArrayOfString);
    int i = localObject.length;
    int j = 0;
    while (j < i)
    {
      paramArrayOfString = localObject[j];
      if (this.mTableIdLookup.containsKey(paramArrayOfString.toLowerCase(Locale.US)))
      {
        j++;
      }
      else
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("There is no table with name ");
        ((StringBuilder)localObject).append(paramArrayOfString);
        throw new IllegalArgumentException(((StringBuilder)localObject).toString());
      }
    }
    return localObject;
  }
  
  public void addObserver(Observer paramObserver)
  {
    ??? = resolveViews(paramObserver.mTables);
    int[] arrayOfInt = new int[???.length];
    int i = ???.length;
    int j = 0;
    while (j < i)
    {
      localObject2 = (Integer)this.mTableIdLookup.get(???[j].toLowerCase(Locale.US));
      if (localObject2 != null)
      {
        arrayOfInt[j] = ((Integer)localObject2).intValue();
        j++;
      }
      else
      {
        paramObserver = new StringBuilder();
        paramObserver.append("There is no table with name ");
        paramObserver.append(???[j]);
        throw new IllegalArgumentException(paramObserver.toString());
      }
    }
    Object localObject2 = new ObserverWrapper(paramObserver, arrayOfInt, (String[])???);
    synchronized (this.mObserverMap)
    {
      paramObserver = (ObserverWrapper)this.mObserverMap.putIfAbsent(paramObserver, localObject2);
      if ((paramObserver == null) && (this.mObservedTableTracker.onAdded(arrayOfInt))) {
        syncTriggers();
      }
      return;
    }
  }
  
  public void addWeakObserver(Observer paramObserver)
  {
    addObserver(new WeakObserver(this, paramObserver));
  }
  
  @Deprecated
  public <T> LiveData<T> createLiveData(String[] paramArrayOfString, Callable<T> paramCallable)
  {
    return createLiveData(paramArrayOfString, false, paramCallable);
  }
  
  public <T> LiveData<T> createLiveData(String[] paramArrayOfString, boolean paramBoolean, Callable<T> paramCallable)
  {
    return this.mInvalidationLiveDataContainer.create(validateAndResolveTableNames(paramArrayOfString), paramBoolean, paramCallable);
  }
  
  boolean ensureInitialization()
  {
    if (!this.mDatabase.isOpen()) {
      return false;
    }
    if (!this.mInitialized) {
      this.mDatabase.getOpenHelper().getWritableDatabase();
    }
    if (!this.mInitialized)
    {
      Log.e("ROOM", "database is not initialized even though it is open");
      return false;
    }
    return true;
  }
  
  void internalInit(SupportSQLiteDatabase paramSupportSQLiteDatabase)
  {
    try
    {
      if (this.mInitialized)
      {
        Log.e("ROOM", "Invalidation tracker is initialized twice :/.");
        return;
      }
      paramSupportSQLiteDatabase.execSQL("PRAGMA temp_store = MEMORY;");
      paramSupportSQLiteDatabase.execSQL("PRAGMA recursive_triggers='ON';");
      paramSupportSQLiteDatabase.execSQL("CREATE TEMP TABLE room_table_modification_log(table_id INTEGER PRIMARY KEY, invalidated INTEGER NOT NULL DEFAULT 0)");
      syncTriggers(paramSupportSQLiteDatabase);
      this.mCleanupStatement = paramSupportSQLiteDatabase.compileStatement("UPDATE room_table_modification_log SET invalidated = 0 WHERE invalidated = 1 ");
      this.mInitialized = true;
      return;
    }
    finally {}
  }
  
  public void notifyObserversByTableNames(String... paramVarArgs)
  {
    synchronized (this.mObserverMap)
    {
      Iterator localIterator = this.mObserverMap.iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        if (!((Observer)localEntry.getKey()).isRemote()) {
          ((ObserverWrapper)localEntry.getValue()).notifyByTableNames(paramVarArgs);
        }
      }
      return;
    }
  }
  
  public void refreshVersionsAsync()
  {
    if (this.mPendingRefresh.compareAndSet(false, true)) {
      this.mDatabase.getQueryExecutor().execute(this.mRefreshRunnable);
    }
  }
  
  public void refreshVersionsSync()
  {
    syncTriggers();
    this.mRefreshRunnable.run();
  }
  
  public void removeObserver(Observer paramObserver)
  {
    synchronized (this.mObserverMap)
    {
      paramObserver = (ObserverWrapper)this.mObserverMap.remove(paramObserver);
      if ((paramObserver != null) && (this.mObservedTableTracker.onRemoved(paramObserver.mTableIds))) {
        syncTriggers();
      }
      return;
    }
  }
  
  void startMultiInstanceInvalidation(Context paramContext, String paramString)
  {
    this.mMultiInstanceInvalidationClient = new MultiInstanceInvalidationClient(paramContext, paramString, this, this.mDatabase.getQueryExecutor());
  }
  
  void stopMultiInstanceInvalidation()
  {
    MultiInstanceInvalidationClient localMultiInstanceInvalidationClient = this.mMultiInstanceInvalidationClient;
    if (localMultiInstanceInvalidationClient != null)
    {
      localMultiInstanceInvalidationClient.stop();
      this.mMultiInstanceInvalidationClient = null;
    }
  }
  
  void syncTriggers()
  {
    if (!this.mDatabase.isOpen()) {
      return;
    }
    syncTriggers(this.mDatabase.getOpenHelper().getWritableDatabase());
  }
  
  void syncTriggers(SupportSQLiteDatabase paramSupportSQLiteDatabase)
  {
    if (paramSupportSQLiteDatabase.inTransaction()) {
      return;
    }
    try
    {
      for (;;)
      {
        Lock localLock = this.mDatabase.getCloseLock();
        localLock.lock();
        try
        {
          int[] arrayOfInt = this.mObservedTableTracker.getTablesToSync();
          if (arrayOfInt == null) {
            return;
          }
          int i = arrayOfInt.length;
          paramSupportSQLiteDatabase.beginTransaction();
          int j = 0;
          for (;;)
          {
            if (j < i)
            {
              int k = arrayOfInt[j];
              if ((k != 1) && (k != 2)) {}
            }
            try
            {
              stopTrackingTable(paramSupportSQLiteDatabase, j);
              break label101;
              startTrackingTable(paramSupportSQLiteDatabase, j);
              label101:
              j++;
            }
            finally {}
          }
          paramSupportSQLiteDatabase.setTransactionSuccessful();
          paramSupportSQLiteDatabase.endTransaction();
          this.mObservedTableTracker.onSyncCompleted();
          localLock.unlock();
        }
        finally
        {
          localLock.unlock();
        }
      }
      Log.e("ROOM", "Cannot run invalidation tracker. Is the db closed?", paramSupportSQLiteDatabase);
    }
    catch (SQLiteException paramSupportSQLiteDatabase) {}catch (IllegalStateException paramSupportSQLiteDatabase) {}
  }
  
  static class ObservedTableTracker
  {
    static final int ADD = 1;
    static final int NO_OP = 0;
    static final int REMOVE = 2;
    boolean mNeedsSync;
    boolean mPendingSync;
    final long[] mTableObservers;
    final int[] mTriggerStateChanges;
    final boolean[] mTriggerStates;
    
    ObservedTableTracker(int paramInt)
    {
      long[] arrayOfLong = new long[paramInt];
      this.mTableObservers = arrayOfLong;
      this.mTriggerStates = new boolean[paramInt];
      this.mTriggerStateChanges = new int[paramInt];
      Arrays.fill(arrayOfLong, 0L);
      Arrays.fill(this.mTriggerStates, false);
    }
    
    int[] getTablesToSync()
    {
      try
      {
        if ((this.mNeedsSync) && (!this.mPendingSync))
        {
          int i = this.mTableObservers.length;
          for (int j = 0;; j++)
          {
            int k = 1;
            if (j >= i) {
              break;
            }
            int m;
            if (this.mTableObservers[j] > 0L) {
              m = 1;
            } else {
              m = 0;
            }
            if (m != this.mTriggerStates[j])
            {
              arrayOfInt = this.mTriggerStateChanges;
              if (m == 0) {
                k = 2;
              }
              arrayOfInt[j] = k;
            }
            else
            {
              this.mTriggerStateChanges[j] = 0;
            }
            this.mTriggerStates[j] = m;
          }
          this.mPendingSync = true;
          this.mNeedsSync = false;
          int[] arrayOfInt = this.mTriggerStateChanges;
          return arrayOfInt;
        }
        return null;
      }
      finally {}
    }
    
    boolean onAdded(int... paramVarArgs)
    {
      try
      {
        int i = paramVarArgs.length;
        int j = 0;
        boolean bool = false;
        while (j < i)
        {
          int k = paramVarArgs[j];
          long l = this.mTableObservers[k];
          this.mTableObservers[k] = (1L + l);
          if (l == 0L)
          {
            this.mNeedsSync = true;
            bool = true;
          }
          j++;
        }
        return bool;
      }
      finally {}
    }
    
    boolean onRemoved(int... paramVarArgs)
    {
      try
      {
        int i = paramVarArgs.length;
        int j = 0;
        boolean bool = false;
        while (j < i)
        {
          int k = paramVarArgs[j];
          long l = this.mTableObservers[k];
          this.mTableObservers[k] = (l - 1L);
          if (l == 1L)
          {
            this.mNeedsSync = true;
            bool = true;
          }
          j++;
        }
        return bool;
      }
      finally {}
    }
    
    void onSyncCompleted()
    {
      try
      {
        this.mPendingSync = false;
        return;
      }
      finally {}
    }
  }
  
  public static abstract class Observer
  {
    final String[] mTables;
    
    protected Observer(String paramString, String... paramVarArgs)
    {
      String[] arrayOfString = (String[])Arrays.copyOf(paramVarArgs, paramVarArgs.length + 1);
      this.mTables = arrayOfString;
      arrayOfString[paramVarArgs.length] = paramString;
    }
    
    public Observer(String[] paramArrayOfString)
    {
      this.mTables = ((String[])Arrays.copyOf(paramArrayOfString, paramArrayOfString.length));
    }
    
    boolean isRemote()
    {
      return false;
    }
    
    public abstract void onInvalidated(Set<String> paramSet);
  }
  
  static class ObserverWrapper
  {
    final InvalidationTracker.Observer mObserver;
    private final Set<String> mSingleTableSet;
    final int[] mTableIds;
    private final String[] mTableNames;
    
    ObserverWrapper(InvalidationTracker.Observer paramObserver, int[] paramArrayOfInt, String[] paramArrayOfString)
    {
      this.mObserver = paramObserver;
      this.mTableIds = paramArrayOfInt;
      this.mTableNames = paramArrayOfString;
      if (paramArrayOfInt.length == 1)
      {
        paramObserver = new HashSet();
        paramObserver.add(this.mTableNames[0]);
        this.mSingleTableSet = Collections.unmodifiableSet(paramObserver);
      }
      else
      {
        this.mSingleTableSet = null;
      }
    }
    
    void notifyByTableInvalidStatus(Set<Integer> paramSet)
    {
      int i = this.mTableIds.length;
      Object localObject1 = null;
      int j = 0;
      while (j < i)
      {
        Object localObject2 = localObject1;
        if (paramSet.contains(Integer.valueOf(this.mTableIds[j]))) {
          if (i == 1)
          {
            localObject2 = this.mSingleTableSet;
          }
          else
          {
            localObject2 = localObject1;
            if (localObject1 == null) {
              localObject2 = new HashSet(i);
            }
            ((Set)localObject2).add(this.mTableNames[j]);
          }
        }
        j++;
        localObject1 = localObject2;
      }
      if (localObject1 != null) {
        this.mObserver.onInvalidated(localObject1);
      }
    }
    
    void notifyByTableNames(String[] paramArrayOfString)
    {
      int i = this.mTableNames.length;
      Object localObject1 = null;
      int j;
      if (i == 1)
      {
        j = paramArrayOfString.length;
        for (i = 0;; i++)
        {
          localObject2 = localObject1;
          if (i >= j) {
            break;
          }
          if (paramArrayOfString[i].equalsIgnoreCase(this.mTableNames[0]))
          {
            localObject2 = this.mSingleTableSet;
            break;
          }
        }
      }
      HashSet localHashSet = new HashSet();
      int k = paramArrayOfString.length;
      for (i = 0; i < k; i++)
      {
        localObject2 = paramArrayOfString[i];
        for (String str : this.mTableNames) {
          if (str.equalsIgnoreCase((String)localObject2))
          {
            localHashSet.add(str);
            break;
          }
        }
      }
      Object localObject2 = localObject1;
      if (localHashSet.size() > 0) {
        localObject2 = localHashSet;
      }
      if (localObject2 != null) {
        this.mObserver.onInvalidated((Set)localObject2);
      }
    }
  }
  
  static class WeakObserver
    extends InvalidationTracker.Observer
  {
    final WeakReference<InvalidationTracker.Observer> mDelegateRef;
    final InvalidationTracker mTracker;
    
    WeakObserver(InvalidationTracker paramInvalidationTracker, InvalidationTracker.Observer paramObserver)
    {
      super();
      this.mTracker = paramInvalidationTracker;
      this.mDelegateRef = new WeakReference(paramObserver);
    }
    
    public void onInvalidated(Set<String> paramSet)
    {
      InvalidationTracker.Observer localObserver = (InvalidationTracker.Observer)this.mDelegateRef.get();
      if (localObserver == null) {
        this.mTracker.removeObserver(this);
      } else {
        localObserver.onInvalidated(paramSet);
      }
    }
  }
}

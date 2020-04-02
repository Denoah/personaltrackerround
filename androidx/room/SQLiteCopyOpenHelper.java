package androidx.room;

import android.content.Context;
import android.content.res.AssetManager;
import androidx.room.util.FileUtil;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

class SQLiteCopyOpenHelper
  implements SupportSQLiteOpenHelper
{
  private final Context mContext;
  private final String mCopyFromAssetPath;
  private final File mCopyFromFile;
  private DatabaseConfiguration mDatabaseConfiguration;
  private final int mDatabaseVersion;
  private final SupportSQLiteOpenHelper mDelegate;
  private boolean mVerified;
  
  SQLiteCopyOpenHelper(Context paramContext, String paramString, File paramFile, int paramInt, SupportSQLiteOpenHelper paramSupportSQLiteOpenHelper)
  {
    this.mContext = paramContext;
    this.mCopyFromAssetPath = paramString;
    this.mCopyFromFile = paramFile;
    this.mDatabaseVersion = paramInt;
    this.mDelegate = paramSupportSQLiteOpenHelper;
  }
  
  private void copyDatabaseFile(File paramFile)
    throws IOException
  {
    if (this.mCopyFromAssetPath != null)
    {
      localObject = Channels.newChannel(this.mContext.getAssets().open(this.mCopyFromAssetPath));
    }
    else
    {
      if (this.mCopyFromFile == null) {
        break label214;
      }
      localObject = new FileInputStream(this.mCopyFromFile).getChannel();
    }
    File localFile = File.createTempFile("room-copy-helper", ".tmp", this.mContext.getCacheDir());
    localFile.deleteOnExit();
    FileUtil.copy((ReadableByteChannel)localObject, new FileOutputStream(localFile).getChannel());
    Object localObject = paramFile.getParentFile();
    if ((localObject != null) && (!((File)localObject).exists()) && (!((File)localObject).mkdirs()))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Failed to create directories for ");
      ((StringBuilder)localObject).append(paramFile.getAbsolutePath());
      throw new IOException(((StringBuilder)localObject).toString());
    }
    if (localFile.renameTo(paramFile)) {
      return;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Failed to move intermediate file (");
    ((StringBuilder)localObject).append(localFile.getAbsolutePath());
    ((StringBuilder)localObject).append(") to destination (");
    ((StringBuilder)localObject).append(paramFile.getAbsolutePath());
    ((StringBuilder)localObject).append(").");
    throw new IOException(((StringBuilder)localObject).toString());
    label214:
    throw new IllegalStateException("copyFromAssetPath and copyFromFile == null!");
  }
  
  /* Error */
  private void verifyDatabaseFile()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 142	androidx/room/SQLiteCopyOpenHelper:getDatabaseName	()Ljava/lang/String;
    //   4: astore_1
    //   5: aload_0
    //   6: getfield 27	androidx/room/SQLiteCopyOpenHelper:mContext	Landroid/content/Context;
    //   9: aload_1
    //   10: invokevirtual 146	android/content/Context:getDatabasePath	(Ljava/lang/String;)Ljava/io/File;
    //   13: astore_2
    //   14: aload_0
    //   15: getfield 148	androidx/room/SQLiteCopyOpenHelper:mDatabaseConfiguration	Landroidx/room/DatabaseConfiguration;
    //   18: astore_3
    //   19: aload_3
    //   20: ifnull +19 -> 39
    //   23: aload_3
    //   24: getfield 153	androidx/room/DatabaseConfiguration:multiInstanceInvalidation	Z
    //   27: ifeq +6 -> 33
    //   30: goto +9 -> 39
    //   33: iconst_0
    //   34: istore 4
    //   36: goto +6 -> 42
    //   39: iconst_1
    //   40: istore 4
    //   42: new 155	androidx/room/util/CopyLock
    //   45: dup
    //   46: aload_1
    //   47: aload_0
    //   48: getfield 27	androidx/room/SQLiteCopyOpenHelper:mContext	Landroid/content/Context;
    //   51: invokevirtual 158	android/content/Context:getFilesDir	()Ljava/io/File;
    //   54: iload 4
    //   56: invokespecial 161	androidx/room/util/CopyLock:<init>	(Ljava/lang/String;Ljava/io/File;Z)V
    //   59: astore_3
    //   60: aload_3
    //   61: invokevirtual 164	androidx/room/util/CopyLock:lock	()V
    //   64: aload_2
    //   65: invokevirtual 100	java/io/File:exists	()Z
    //   68: istore 4
    //   70: iload 4
    //   72: ifne +27 -> 99
    //   75: aload_0
    //   76: aload_2
    //   77: invokespecial 166	androidx/room/SQLiteCopyOpenHelper:copyDatabaseFile	(Ljava/io/File;)V
    //   80: aload_3
    //   81: invokevirtual 169	androidx/room/util/CopyLock:unlock	()V
    //   84: return
    //   85: astore_1
    //   86: new 171	java/lang/RuntimeException
    //   89: astore_2
    //   90: aload_2
    //   91: ldc -83
    //   93: aload_1
    //   94: invokespecial 176	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   97: aload_2
    //   98: athrow
    //   99: aload_0
    //   100: getfield 148	androidx/room/SQLiteCopyOpenHelper:mDatabaseConfiguration	Landroidx/room/DatabaseConfiguration;
    //   103: astore 5
    //   105: aload 5
    //   107: ifnonnull +8 -> 115
    //   110: aload_3
    //   111: invokevirtual 169	androidx/room/util/CopyLock:unlock	()V
    //   114: return
    //   115: aload_2
    //   116: invokestatic 182	androidx/room/util/DBUtil:readVersion	(Ljava/io/File;)I
    //   119: istore 6
    //   121: aload_0
    //   122: getfield 33	androidx/room/SQLiteCopyOpenHelper:mDatabaseVersion	I
    //   125: istore 7
    //   127: iload 6
    //   129: iload 7
    //   131: if_icmpne +8 -> 139
    //   134: aload_3
    //   135: invokevirtual 169	androidx/room/util/CopyLock:unlock	()V
    //   138: return
    //   139: aload_0
    //   140: getfield 148	androidx/room/SQLiteCopyOpenHelper:mDatabaseConfiguration	Landroidx/room/DatabaseConfiguration;
    //   143: iload 6
    //   145: aload_0
    //   146: getfield 33	androidx/room/SQLiteCopyOpenHelper:mDatabaseVersion	I
    //   149: invokevirtual 186	androidx/room/DatabaseConfiguration:isMigrationRequired	(II)Z
    //   152: istore 4
    //   154: iload 4
    //   156: ifeq +8 -> 164
    //   159: aload_3
    //   160: invokevirtual 169	androidx/room/util/CopyLock:unlock	()V
    //   163: return
    //   164: aload_0
    //   165: getfield 27	androidx/room/SQLiteCopyOpenHelper:mContext	Landroid/content/Context;
    //   168: aload_1
    //   169: invokevirtual 190	android/content/Context:deleteDatabase	(Ljava/lang/String;)Z
    //   172: istore 4
    //   174: iload 4
    //   176: ifeq +24 -> 200
    //   179: aload_0
    //   180: aload_2
    //   181: invokespecial 166	androidx/room/SQLiteCopyOpenHelper:copyDatabaseFile	(Ljava/io/File;)V
    //   184: goto +54 -> 238
    //   187: astore_1
    //   188: ldc -64
    //   190: ldc -83
    //   192: aload_1
    //   193: invokestatic 198	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   196: pop
    //   197: goto +41 -> 238
    //   200: new 105	java/lang/StringBuilder
    //   203: astore_2
    //   204: aload_2
    //   205: invokespecial 106	java/lang/StringBuilder:<init>	()V
    //   208: aload_2
    //   209: ldc -56
    //   211: invokevirtual 112	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   214: pop
    //   215: aload_2
    //   216: aload_1
    //   217: invokevirtual 112	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   220: pop
    //   221: aload_2
    //   222: ldc -54
    //   224: invokevirtual 112	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   227: pop
    //   228: ldc -64
    //   230: aload_2
    //   231: invokevirtual 119	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   234: invokestatic 205	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   237: pop
    //   238: aload_3
    //   239: invokevirtual 169	androidx/room/util/CopyLock:unlock	()V
    //   242: return
    //   243: astore_1
    //   244: ldc -64
    //   246: ldc -49
    //   248: aload_1
    //   249: invokestatic 198	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   252: pop
    //   253: aload_3
    //   254: invokevirtual 169	androidx/room/util/CopyLock:unlock	()V
    //   257: return
    //   258: astore_1
    //   259: aload_3
    //   260: invokevirtual 169	androidx/room/util/CopyLock:unlock	()V
    //   263: aload_1
    //   264: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	265	0	this	SQLiteCopyOpenHelper
    //   4	43	1	str	String
    //   85	84	1	localIOException1	IOException
    //   187	30	1	localIOException2	IOException
    //   243	6	1	localIOException3	IOException
    //   258	6	1	localObject1	Object
    //   13	218	2	localObject2	Object
    //   18	242	3	localObject3	Object
    //   34	141	4	bool	boolean
    //   103	3	5	localDatabaseConfiguration	DatabaseConfiguration
    //   119	25	6	i	int
    //   125	7	7	j	int
    // Exception table:
    //   from	to	target	type
    //   75	80	85	java/io/IOException
    //   179	184	187	java/io/IOException
    //   115	121	243	java/io/IOException
    //   60	70	258	finally
    //   75	80	258	finally
    //   86	99	258	finally
    //   99	105	258	finally
    //   115	121	258	finally
    //   121	127	258	finally
    //   139	154	258	finally
    //   164	174	258	finally
    //   179	184	258	finally
    //   188	197	258	finally
    //   200	238	258	finally
    //   244	253	258	finally
  }
  
  public void close()
  {
    try
    {
      this.mDelegate.close();
      this.mVerified = false;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public String getDatabaseName()
  {
    return this.mDelegate.getDatabaseName();
  }
  
  public SupportSQLiteDatabase getReadableDatabase()
  {
    try
    {
      if (!this.mVerified)
      {
        verifyDatabaseFile();
        this.mVerified = true;
      }
      SupportSQLiteDatabase localSupportSQLiteDatabase = this.mDelegate.getReadableDatabase();
      return localSupportSQLiteDatabase;
    }
    finally {}
  }
  
  public SupportSQLiteDatabase getWritableDatabase()
  {
    try
    {
      if (!this.mVerified)
      {
        verifyDatabaseFile();
        this.mVerified = true;
      }
      SupportSQLiteDatabase localSupportSQLiteDatabase = this.mDelegate.getWritableDatabase();
      return localSupportSQLiteDatabase;
    }
    finally {}
  }
  
  void setDatabaseConfiguration(DatabaseConfiguration paramDatabaseConfiguration)
  {
    this.mDatabaseConfiguration = paramDatabaseConfiguration;
  }
  
  public void setWriteAheadLoggingEnabled(boolean paramBoolean)
  {
    this.mDelegate.setWriteAheadLoggingEnabled(paramBoolean);
  }
}

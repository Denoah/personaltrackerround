package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.os.Process;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class TypefaceCompatUtil
{
  private static final String CACHE_FILE_PREFIX = ".font";
  private static final String TAG = "TypefaceCompatUtil";
  
  private TypefaceCompatUtil() {}
  
  public static void closeQuietly(Closeable paramCloseable)
  {
    if (paramCloseable != null) {}
    try
    {
      paramCloseable.close();
      return;
    }
    catch (IOException paramCloseable)
    {
      for (;;) {}
    }
  }
  
  public static ByteBuffer copyToDirectBuffer(Context paramContext, Resources paramResources, int paramInt)
  {
    paramContext = getTempFile(paramContext);
    if (paramContext == null) {
      return null;
    }
    try
    {
      boolean bool = copyToFile(paramContext, paramResources, paramInt);
      if (!bool) {
        return null;
      }
      paramResources = mmap(paramContext);
      return paramResources;
    }
    finally
    {
      paramContext.delete();
    }
  }
  
  /* Error */
  public static boolean copyToFile(File paramFile, Resources paramResources, int paramInt)
  {
    // Byte code:
    //   0: aload_1
    //   1: iload_2
    //   2: invokevirtual 51	android/content/res/Resources:openRawResource	(I)Ljava/io/InputStream;
    //   5: astore_1
    //   6: aload_0
    //   7: aload_1
    //   8: invokestatic 54	androidx/core/graphics/TypefaceCompatUtil:copyToFile	(Ljava/io/File;Ljava/io/InputStream;)Z
    //   11: istore_3
    //   12: aload_1
    //   13: invokestatic 56	androidx/core/graphics/TypefaceCompatUtil:closeQuietly	(Ljava/io/Closeable;)V
    //   16: iload_3
    //   17: ireturn
    //   18: astore_0
    //   19: goto +6 -> 25
    //   22: astore_0
    //   23: aconst_null
    //   24: astore_1
    //   25: aload_1
    //   26: invokestatic 56	androidx/core/graphics/TypefaceCompatUtil:closeQuietly	(Ljava/io/Closeable;)V
    //   29: aload_0
    //   30: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	31	0	paramFile	File
    //   0	31	1	paramResources	Resources
    //   0	31	2	paramInt	int
    //   11	6	3	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   6	12	18	finally
    //   0	6	22	finally
  }
  
  /* Error */
  public static boolean copyToFile(File paramFile, java.io.InputStream paramInputStream)
  {
    // Byte code:
    //   0: invokestatic 62	android/os/StrictMode:allowThreadDiskWrites	()Landroid/os/StrictMode$ThreadPolicy;
    //   3: astore_2
    //   4: aconst_null
    //   5: astore_3
    //   6: aconst_null
    //   7: astore 4
    //   9: aload 4
    //   11: astore 5
    //   13: new 64	java/io/FileOutputStream
    //   16: astore 6
    //   18: aload 4
    //   20: astore 5
    //   22: aload 6
    //   24: aload_0
    //   25: iconst_0
    //   26: invokespecial 67	java/io/FileOutputStream:<init>	(Ljava/io/File;Z)V
    //   29: sipush 1024
    //   32: newarray byte
    //   34: astore_0
    //   35: aload_1
    //   36: aload_0
    //   37: invokevirtual 73	java/io/InputStream:read	([B)I
    //   40: istore 7
    //   42: iload 7
    //   44: iconst_m1
    //   45: if_icmpeq +15 -> 60
    //   48: aload 6
    //   50: aload_0
    //   51: iconst_0
    //   52: iload 7
    //   54: invokevirtual 77	java/io/FileOutputStream:write	([BII)V
    //   57: goto -22 -> 35
    //   60: aload 6
    //   62: invokestatic 56	androidx/core/graphics/TypefaceCompatUtil:closeQuietly	(Ljava/io/Closeable;)V
    //   65: aload_2
    //   66: invokestatic 81	android/os/StrictMode:setThreadPolicy	(Landroid/os/StrictMode$ThreadPolicy;)V
    //   69: iconst_1
    //   70: ireturn
    //   71: astore_0
    //   72: aload 6
    //   74: astore 5
    //   76: goto +81 -> 157
    //   79: astore_1
    //   80: aload 6
    //   82: astore_0
    //   83: goto +10 -> 93
    //   86: astore_0
    //   87: goto +70 -> 157
    //   90: astore_1
    //   91: aload_3
    //   92: astore_0
    //   93: aload_0
    //   94: astore 5
    //   96: new 83	java/lang/StringBuilder
    //   99: astore 6
    //   101: aload_0
    //   102: astore 5
    //   104: aload 6
    //   106: invokespecial 84	java/lang/StringBuilder:<init>	()V
    //   109: aload_0
    //   110: astore 5
    //   112: aload 6
    //   114: ldc 86
    //   116: invokevirtual 90	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   119: pop
    //   120: aload_0
    //   121: astore 5
    //   123: aload 6
    //   125: aload_1
    //   126: invokevirtual 94	java/io/IOException:getMessage	()Ljava/lang/String;
    //   129: invokevirtual 90	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   132: pop
    //   133: aload_0
    //   134: astore 5
    //   136: ldc 11
    //   138: aload 6
    //   140: invokevirtual 97	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   143: invokestatic 103	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   146: pop
    //   147: aload_0
    //   148: invokestatic 56	androidx/core/graphics/TypefaceCompatUtil:closeQuietly	(Ljava/io/Closeable;)V
    //   151: aload_2
    //   152: invokestatic 81	android/os/StrictMode:setThreadPolicy	(Landroid/os/StrictMode$ThreadPolicy;)V
    //   155: iconst_0
    //   156: ireturn
    //   157: aload 5
    //   159: invokestatic 56	androidx/core/graphics/TypefaceCompatUtil:closeQuietly	(Ljava/io/Closeable;)V
    //   162: aload_2
    //   163: invokestatic 81	android/os/StrictMode:setThreadPolicy	(Landroid/os/StrictMode$ThreadPolicy;)V
    //   166: aload_0
    //   167: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	168	0	paramFile	File
    //   0	168	1	paramInputStream	java.io.InputStream
    //   3	160	2	localThreadPolicy	android.os.StrictMode.ThreadPolicy
    //   5	87	3	localObject1	Object
    //   7	12	4	localObject2	Object
    //   11	147	5	localObject3	Object
    //   16	123	6	localObject4	Object
    //   40	13	7	i	int
    // Exception table:
    //   from	to	target	type
    //   29	35	71	finally
    //   35	42	71	finally
    //   48	57	71	finally
    //   29	35	79	java/io/IOException
    //   35	42	79	java/io/IOException
    //   48	57	79	java/io/IOException
    //   13	18	86	finally
    //   22	29	86	finally
    //   96	101	86	finally
    //   104	109	86	finally
    //   112	120	86	finally
    //   123	133	86	finally
    //   136	147	86	finally
    //   13	18	90	java/io/IOException
    //   22	29	90	java/io/IOException
  }
  
  public static File getTempFile(Context paramContext)
  {
    paramContext = paramContext.getCacheDir();
    if (paramContext == null) {
      return null;
    }
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(".font");
    ((StringBuilder)localObject1).append(Process.myPid());
    ((StringBuilder)localObject1).append("-");
    ((StringBuilder)localObject1).append(Process.myTid());
    ((StringBuilder)localObject1).append("-");
    localObject1 = ((StringBuilder)localObject1).toString();
    for (int i = 0; i < 100; i++)
    {
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append(i);
      localObject2 = new File(paramContext, ((StringBuilder)localObject2).toString());
      try
      {
        boolean bool = ((File)localObject2).createNewFile();
        if (bool) {
          return localObject2;
        }
      }
      catch (IOException localIOException)
      {
        for (;;) {}
      }
    }
    return null;
  }
  
  /* Error */
  public static ByteBuffer mmap(Context paramContext, android.os.CancellationSignal paramCancellationSignal, android.net.Uri paramUri)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 134	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   4: astore_0
    //   5: aload_0
    //   6: aload_2
    //   7: ldc -120
    //   9: aload_1
    //   10: invokevirtual 142	android/content/ContentResolver:openFileDescriptor	(Landroid/net/Uri;Ljava/lang/String;Landroid/os/CancellationSignal;)Landroid/os/ParcelFileDescriptor;
    //   13: astore_0
    //   14: aload_0
    //   15: ifnonnull +13 -> 28
    //   18: aload_0
    //   19: ifnull +7 -> 26
    //   22: aload_0
    //   23: invokevirtual 145	android/os/ParcelFileDescriptor:close	()V
    //   26: aconst_null
    //   27: areturn
    //   28: new 147	java/io/FileInputStream
    //   31: astore_1
    //   32: aload_1
    //   33: aload_0
    //   34: invokevirtual 151	android/os/ParcelFileDescriptor:getFileDescriptor	()Ljava/io/FileDescriptor;
    //   37: invokespecial 154	java/io/FileInputStream:<init>	(Ljava/io/FileDescriptor;)V
    //   40: aload_1
    //   41: invokevirtual 158	java/io/FileInputStream:getChannel	()Ljava/nio/channels/FileChannel;
    //   44: astore_2
    //   45: aload_2
    //   46: invokevirtual 164	java/nio/channels/FileChannel:size	()J
    //   49: lstore_3
    //   50: aload_2
    //   51: getstatic 170	java/nio/channels/FileChannel$MapMode:READ_ONLY	Ljava/nio/channels/FileChannel$MapMode;
    //   54: lconst_0
    //   55: lload_3
    //   56: invokevirtual 174	java/nio/channels/FileChannel:map	(Ljava/nio/channels/FileChannel$MapMode;JJ)Ljava/nio/MappedByteBuffer;
    //   59: astore_2
    //   60: aload_1
    //   61: invokevirtual 175	java/io/FileInputStream:close	()V
    //   64: aload_0
    //   65: ifnull +7 -> 72
    //   68: aload_0
    //   69: invokevirtual 145	android/os/ParcelFileDescriptor:close	()V
    //   72: aload_2
    //   73: areturn
    //   74: astore_2
    //   75: aload_2
    //   76: athrow
    //   77: astore 5
    //   79: aload_1
    //   80: invokevirtual 175	java/io/FileInputStream:close	()V
    //   83: goto +9 -> 92
    //   86: astore_1
    //   87: aload_2
    //   88: aload_1
    //   89: invokevirtual 181	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   92: aload 5
    //   94: athrow
    //   95: astore_1
    //   96: aload_1
    //   97: athrow
    //   98: astore_2
    //   99: aload_0
    //   100: ifnull +16 -> 116
    //   103: aload_0
    //   104: invokevirtual 145	android/os/ParcelFileDescriptor:close	()V
    //   107: goto +9 -> 116
    //   110: astore_0
    //   111: aload_1
    //   112: aload_0
    //   113: invokevirtual 181	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   116: aload_2
    //   117: athrow
    //   118: astore_0
    //   119: aconst_null
    //   120: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	121	0	paramContext	Context
    //   0	121	1	paramCancellationSignal	android.os.CancellationSignal
    //   0	121	2	paramUri	android.net.Uri
    //   49	7	3	l	long
    //   77	16	5	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   40	60	74	finally
    //   75	77	77	finally
    //   79	83	86	finally
    //   28	40	95	finally
    //   60	64	95	finally
    //   87	92	95	finally
    //   92	95	95	finally
    //   96	98	98	finally
    //   103	107	110	finally
    //   5	14	118	java/io/IOException
    //   22	26	118	java/io/IOException
    //   68	72	118	java/io/IOException
    //   111	116	118	java/io/IOException
    //   116	118	118	java/io/IOException
  }
  
  /* Error */
  private static ByteBuffer mmap(File paramFile)
  {
    // Byte code:
    //   0: new 147	java/io/FileInputStream
    //   3: astore_1
    //   4: aload_1
    //   5: aload_0
    //   6: invokespecial 184	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   9: aload_1
    //   10: invokevirtual 158	java/io/FileInputStream:getChannel	()Ljava/nio/channels/FileChannel;
    //   13: astore_0
    //   14: aload_0
    //   15: invokevirtual 164	java/nio/channels/FileChannel:size	()J
    //   18: lstore_2
    //   19: aload_0
    //   20: getstatic 170	java/nio/channels/FileChannel$MapMode:READ_ONLY	Ljava/nio/channels/FileChannel$MapMode;
    //   23: lconst_0
    //   24: lload_2
    //   25: invokevirtual 174	java/nio/channels/FileChannel:map	(Ljava/nio/channels/FileChannel$MapMode;JJ)Ljava/nio/MappedByteBuffer;
    //   28: astore_0
    //   29: aload_1
    //   30: invokevirtual 175	java/io/FileInputStream:close	()V
    //   33: aload_0
    //   34: areturn
    //   35: astore 4
    //   37: aload 4
    //   39: athrow
    //   40: astore_0
    //   41: aload_1
    //   42: invokevirtual 175	java/io/FileInputStream:close	()V
    //   45: goto +10 -> 55
    //   48: astore_1
    //   49: aload 4
    //   51: aload_1
    //   52: invokevirtual 181	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   55: aload_0
    //   56: athrow
    //   57: astore_0
    //   58: aconst_null
    //   59: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	60	0	paramFile	File
    //   3	39	1	localFileInputStream	java.io.FileInputStream
    //   48	4	1	localThrowable	Throwable
    //   18	7	2	l	long
    //   35	15	4	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   9	29	35	finally
    //   37	40	40	finally
    //   41	45	48	finally
    //   0	9	57	java/io/IOException
    //   29	33	57	java/io/IOException
    //   49	55	57	java/io/IOException
    //   55	57	57	java/io/IOException
  }
}

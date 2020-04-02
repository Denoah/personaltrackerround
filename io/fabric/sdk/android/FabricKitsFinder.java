package io.fabric.sdk.android;

import android.os.SystemClock;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

class FabricKitsFinder
  implements Callable<Map<String, KitInfo>>
{
  private static final String FABRIC_BUILD_TYPE_KEY = "fabric-build-type";
  static final String FABRIC_DIR = "fabric/";
  private static final String FABRIC_IDENTIFIER_KEY = "fabric-identifier";
  private static final String FABRIC_VERSION_KEY = "fabric-version";
  final String apkFileName;
  
  FabricKitsFinder(String paramString)
  {
    this.apkFileName = paramString;
  }
  
  private Map<String, KitInfo> findImplicitKits()
  {
    HashMap localHashMap = new HashMap();
    try
    {
      Class.forName("com.google.android.gms.ads.AdView");
      KitInfo localKitInfo = new io/fabric/sdk/android/KitInfo;
      localKitInfo.<init>("com.google.firebase.firebase-ads", "0.0.0", "binary");
      localHashMap.put(localKitInfo.getIdentifier(), localKitInfo);
      Fabric.getLogger().v("Fabric", "Found kit: com.google.firebase.firebase-ads");
      return localHashMap;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
  }
  
  private Map<String, KitInfo> findRegisteredKits()
    throws Exception
  {
    HashMap localHashMap = new HashMap();
    ZipFile localZipFile = loadApkFile();
    Enumeration localEnumeration = localZipFile.entries();
    while (localEnumeration.hasMoreElements())
    {
      Object localObject = (ZipEntry)localEnumeration.nextElement();
      if ((((ZipEntry)localObject).getName().startsWith("fabric/")) && (((ZipEntry)localObject).getName().length() > 7))
      {
        localObject = loadKitInfo((ZipEntry)localObject, localZipFile);
        if (localObject != null)
        {
          localHashMap.put(((KitInfo)localObject).getIdentifier(), localObject);
          Fabric.getLogger().v("Fabric", String.format("Found kit:[%s] version:[%s]", new Object[] { ((KitInfo)localObject).getIdentifier(), ((KitInfo)localObject).getVersion() }));
        }
      }
    }
    if (localZipFile != null) {}
    try
    {
      localZipFile.close();
      return localHashMap;
    }
    catch (IOException localIOException)
    {
      for (;;) {}
    }
  }
  
  /* Error */
  private KitInfo loadKitInfo(ZipEntry paramZipEntry, ZipFile paramZipFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aload_2
    //   3: aload_1
    //   4: invokevirtual 142	java/util/zip/ZipFile:getInputStream	(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;
    //   7: astore 4
    //   9: aload 4
    //   11: astore_2
    //   12: new 144	java/util/Properties
    //   15: astore 5
    //   17: aload 4
    //   19: astore_2
    //   20: aload 5
    //   22: invokespecial 145	java/util/Properties:<init>	()V
    //   25: aload 4
    //   27: astore_2
    //   28: aload 5
    //   30: aload 4
    //   32: invokevirtual 149	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   35: aload 4
    //   37: astore_2
    //   38: aload 5
    //   40: ldc 17
    //   42: invokevirtual 153	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   45: astore 6
    //   47: aload 4
    //   49: astore_2
    //   50: aload 5
    //   52: ldc 20
    //   54: invokevirtual 153	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   57: astore_3
    //   58: aload 4
    //   60: astore_2
    //   61: aload 5
    //   63: ldc 11
    //   65: invokevirtual 153	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   68: astore 5
    //   70: aload 4
    //   72: astore_2
    //   73: aload 6
    //   75: invokestatic 159	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   78: ifne +36 -> 114
    //   81: aload 4
    //   83: astore_2
    //   84: aload_3
    //   85: invokestatic 159	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   88: ifne +26 -> 114
    //   91: aload 4
    //   93: astore_2
    //   94: new 46	io/fabric/sdk/android/KitInfo
    //   97: dup
    //   98: aload 6
    //   100: aload_3
    //   101: aload 5
    //   103: invokespecial 55	io/fabric/sdk/android/KitInfo:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   106: astore_3
    //   107: aload 4
    //   109: invokestatic 165	io/fabric/sdk/android/services/common/CommonUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   112: aload_3
    //   113: areturn
    //   114: aload 4
    //   116: astore_2
    //   117: new 167	java/lang/IllegalStateException
    //   120: astore_3
    //   121: aload 4
    //   123: astore_2
    //   124: new 169	java/lang/StringBuilder
    //   127: astore 6
    //   129: aload 4
    //   131: astore_2
    //   132: aload 6
    //   134: invokespecial 170	java/lang/StringBuilder:<init>	()V
    //   137: aload 4
    //   139: astore_2
    //   140: aload 6
    //   142: ldc -84
    //   144: invokevirtual 176	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   147: pop
    //   148: aload 4
    //   150: astore_2
    //   151: aload 6
    //   153: aload_1
    //   154: invokevirtual 111	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   157: invokevirtual 176	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   160: pop
    //   161: aload 4
    //   163: astore_2
    //   164: aload_3
    //   165: aload 6
    //   167: invokevirtual 179	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   170: invokespecial 181	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   173: aload 4
    //   175: astore_2
    //   176: aload_3
    //   177: athrow
    //   178: astore 4
    //   180: aload_2
    //   181: astore_1
    //   182: aload 4
    //   184: astore_2
    //   185: goto +90 -> 275
    //   188: astore_3
    //   189: goto +13 -> 202
    //   192: astore_2
    //   193: aload_3
    //   194: astore_1
    //   195: goto +80 -> 275
    //   198: astore_3
    //   199: aconst_null
    //   200: astore 4
    //   202: aload 4
    //   204: astore_2
    //   205: invokestatic 71	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   208: astore 6
    //   210: aload 4
    //   212: astore_2
    //   213: new 169	java/lang/StringBuilder
    //   216: astore 5
    //   218: aload 4
    //   220: astore_2
    //   221: aload 5
    //   223: invokespecial 170	java/lang/StringBuilder:<init>	()V
    //   226: aload 4
    //   228: astore_2
    //   229: aload 5
    //   231: ldc -73
    //   233: invokevirtual 176	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   236: pop
    //   237: aload 4
    //   239: astore_2
    //   240: aload 5
    //   242: aload_1
    //   243: invokevirtual 111	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   246: invokevirtual 176	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   249: pop
    //   250: aload 4
    //   252: astore_2
    //   253: aload 6
    //   255: ldc 73
    //   257: aload 5
    //   259: invokevirtual 179	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   262: aload_3
    //   263: invokeinterface 187 4 0
    //   268: aload 4
    //   270: invokestatic 165	io/fabric/sdk/android/services/common/CommonUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   273: aconst_null
    //   274: areturn
    //   275: aload_1
    //   276: invokestatic 165	io/fabric/sdk/android/services/common/CommonUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   279: aload_2
    //   280: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	281	0	this	FabricKitsFinder
    //   0	281	1	paramZipEntry	ZipEntry
    //   0	281	2	paramZipFile	ZipFile
    //   1	176	3	localObject1	Object
    //   188	6	3	localIOException1	IOException
    //   198	65	3	localIOException2	IOException
    //   7	167	4	localInputStream	java.io.InputStream
    //   178	5	4	localObject2	Object
    //   200	69	4	localCloseable	java.io.Closeable
    //   15	243	5	localObject3	Object
    //   45	209	6	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   12	17	178	finally
    //   20	25	178	finally
    //   28	35	178	finally
    //   38	47	178	finally
    //   50	58	178	finally
    //   61	70	178	finally
    //   73	81	178	finally
    //   84	91	178	finally
    //   94	107	178	finally
    //   117	121	178	finally
    //   124	129	178	finally
    //   132	137	178	finally
    //   140	148	178	finally
    //   151	161	178	finally
    //   164	173	178	finally
    //   176	178	178	finally
    //   205	210	178	finally
    //   213	218	178	finally
    //   221	226	178	finally
    //   229	237	178	finally
    //   240	250	178	finally
    //   253	268	178	finally
    //   12	17	188	java/io/IOException
    //   20	25	188	java/io/IOException
    //   28	35	188	java/io/IOException
    //   38	47	188	java/io/IOException
    //   50	58	188	java/io/IOException
    //   61	70	188	java/io/IOException
    //   73	81	188	java/io/IOException
    //   84	91	188	java/io/IOException
    //   94	107	188	java/io/IOException
    //   117	121	188	java/io/IOException
    //   124	129	188	java/io/IOException
    //   132	137	188	java/io/IOException
    //   140	148	188	java/io/IOException
    //   151	161	188	java/io/IOException
    //   164	173	188	java/io/IOException
    //   176	178	188	java/io/IOException
    //   2	9	192	finally
    //   2	9	198	java/io/IOException
  }
  
  public Map<String, KitInfo> call()
    throws Exception
  {
    HashMap localHashMap = new HashMap();
    long l = SystemClock.elapsedRealtime();
    localHashMap.putAll(findImplicitKits());
    localHashMap.putAll(findRegisteredKits());
    Logger localLogger = Fabric.getLogger();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("finish scanning in ");
    localStringBuilder.append(SystemClock.elapsedRealtime() - l);
    localLogger.v("Fabric", localStringBuilder.toString());
    return localHashMap;
  }
  
  protected ZipFile loadApkFile()
    throws IOException
  {
    return new ZipFile(this.apkFileName);
  }
}

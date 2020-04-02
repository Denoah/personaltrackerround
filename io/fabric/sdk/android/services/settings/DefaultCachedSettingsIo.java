package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.Kit;

class DefaultCachedSettingsIo
  implements CachedSettingsIo
{
  private final Kit kit;
  
  public DefaultCachedSettingsIo(Kit paramKit)
  {
    this.kit = paramKit;
  }
  
  /* Error */
  public org.json.JSONObject readCachedSettings()
  {
    // Byte code:
    //   0: invokestatic 26	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   3: ldc 28
    //   5: ldc 30
    //   7: invokeinterface 36 3 0
    //   12: aconst_null
    //   13: astore_1
    //   14: aconst_null
    //   15: astore_2
    //   16: new 38	java/io/File
    //   19: astore_3
    //   20: new 40	io/fabric/sdk/android/services/persistence/FileStoreImpl
    //   23: astore 4
    //   25: aload 4
    //   27: aload_0
    //   28: getfield 15	io/fabric/sdk/android/services/settings/DefaultCachedSettingsIo:kit	Lio/fabric/sdk/android/Kit;
    //   31: invokespecial 42	io/fabric/sdk/android/services/persistence/FileStoreImpl:<init>	(Lio/fabric/sdk/android/Kit;)V
    //   34: aload_3
    //   35: aload 4
    //   37: invokevirtual 46	io/fabric/sdk/android/services/persistence/FileStoreImpl:getFilesDir	()Ljava/io/File;
    //   40: ldc 48
    //   42: invokespecial 51	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   45: aload_3
    //   46: invokevirtual 55	java/io/File:exists	()Z
    //   49: ifeq +47 -> 96
    //   52: new 57	java/io/FileInputStream
    //   55: astore_2
    //   56: aload_2
    //   57: aload_3
    //   58: invokespecial 60	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   61: aload_2
    //   62: astore_3
    //   63: aload_2
    //   64: invokestatic 66	io/fabric/sdk/android/services/common/CommonUtils:streamToString	(Ljava/io/InputStream;)Ljava/lang/String;
    //   67: astore 5
    //   69: aload_2
    //   70: astore_3
    //   71: new 68	org/json/JSONObject
    //   74: astore 4
    //   76: aload_2
    //   77: astore_3
    //   78: aload 4
    //   80: aload 5
    //   82: invokespecial 71	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   85: aload 4
    //   87: astore_3
    //   88: goto +22 -> 110
    //   91: astore 4
    //   93: goto +36 -> 129
    //   96: invokestatic 26	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   99: ldc 28
    //   101: ldc 73
    //   103: invokeinterface 36 3 0
    //   108: aconst_null
    //   109: astore_3
    //   110: aload_2
    //   111: ldc 75
    //   113: invokestatic 79	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   116: goto +37 -> 153
    //   119: astore_2
    //   120: aconst_null
    //   121: astore_3
    //   122: goto +34 -> 156
    //   125: astore 4
    //   127: aconst_null
    //   128: astore_2
    //   129: aload_2
    //   130: astore_3
    //   131: invokestatic 26	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   134: ldc 28
    //   136: ldc 81
    //   138: aload 4
    //   140: invokeinterface 85 4 0
    //   145: aload_2
    //   146: ldc 75
    //   148: invokestatic 79	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   151: aload_1
    //   152: astore_3
    //   153: aload_3
    //   154: areturn
    //   155: astore_2
    //   156: aload_3
    //   157: ldc 75
    //   159: invokestatic 79	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   162: aload_2
    //   163: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	164	0	this	DefaultCachedSettingsIo
    //   13	139	1	localObject1	Object
    //   15	96	2	localFileInputStream	java.io.FileInputStream
    //   119	1	2	localObject2	Object
    //   128	18	2	localCloseable	java.io.Closeable
    //   155	8	2	localObject3	Object
    //   19	138	3	localObject4	Object
    //   23	63	4	localObject5	Object
    //   91	1	4	localException1	Exception
    //   125	14	4	localException2	Exception
    //   67	14	5	str	String
    // Exception table:
    //   from	to	target	type
    //   63	69	91	java/lang/Exception
    //   71	76	91	java/lang/Exception
    //   78	85	91	java/lang/Exception
    //   16	61	119	finally
    //   96	108	119	finally
    //   16	61	125	java/lang/Exception
    //   96	108	125	java/lang/Exception
    //   63	69	155	finally
    //   71	76	155	finally
    //   78	85	155	finally
    //   131	145	155	finally
  }
  
  /* Error */
  public void writeCachedSettings(long paramLong, org.json.JSONObject paramJSONObject)
  {
    // Byte code:
    //   0: invokestatic 26	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   3: ldc 28
    //   5: ldc 89
    //   7: invokeinterface 36 3 0
    //   12: aload_3
    //   13: ifnull +176 -> 189
    //   16: aconst_null
    //   17: astore 4
    //   19: aconst_null
    //   20: astore 5
    //   22: aload 5
    //   24: astore 6
    //   26: aload_3
    //   27: ldc 91
    //   29: lload_1
    //   30: invokevirtual 95	org/json/JSONObject:put	(Ljava/lang/String;J)Lorg/json/JSONObject;
    //   33: pop
    //   34: aload 5
    //   36: astore 6
    //   38: new 97	java/io/FileWriter
    //   41: astore 7
    //   43: aload 5
    //   45: astore 6
    //   47: new 38	java/io/File
    //   50: astore 8
    //   52: aload 5
    //   54: astore 6
    //   56: new 40	io/fabric/sdk/android/services/persistence/FileStoreImpl
    //   59: astore 9
    //   61: aload 5
    //   63: astore 6
    //   65: aload 9
    //   67: aload_0
    //   68: getfield 15	io/fabric/sdk/android/services/settings/DefaultCachedSettingsIo:kit	Lio/fabric/sdk/android/Kit;
    //   71: invokespecial 42	io/fabric/sdk/android/services/persistence/FileStoreImpl:<init>	(Lio/fabric/sdk/android/Kit;)V
    //   74: aload 5
    //   76: astore 6
    //   78: aload 8
    //   80: aload 9
    //   82: invokevirtual 46	io/fabric/sdk/android/services/persistence/FileStoreImpl:getFilesDir	()Ljava/io/File;
    //   85: ldc 48
    //   87: invokespecial 51	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   90: aload 5
    //   92: astore 6
    //   94: aload 7
    //   96: aload 8
    //   98: invokespecial 98	java/io/FileWriter:<init>	(Ljava/io/File;)V
    //   101: aload 7
    //   103: aload_3
    //   104: invokevirtual 102	org/json/JSONObject:toString	()Ljava/lang/String;
    //   107: invokevirtual 105	java/io/FileWriter:write	(Ljava/lang/String;)V
    //   110: aload 7
    //   112: invokevirtual 108	java/io/FileWriter:flush	()V
    //   115: aload 7
    //   117: ldc 110
    //   119: invokestatic 79	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   122: goto +67 -> 189
    //   125: astore_3
    //   126: aload 7
    //   128: astore 6
    //   130: goto +50 -> 180
    //   133: astore 6
    //   135: aload 7
    //   137: astore_3
    //   138: aload 6
    //   140: astore 7
    //   142: goto +12 -> 154
    //   145: astore_3
    //   146: goto +34 -> 180
    //   149: astore 7
    //   151: aload 4
    //   153: astore_3
    //   154: aload_3
    //   155: astore 6
    //   157: invokestatic 26	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   160: ldc 28
    //   162: ldc 112
    //   164: aload 7
    //   166: invokeinterface 85 4 0
    //   171: aload_3
    //   172: ldc 110
    //   174: invokestatic 79	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   177: goto +12 -> 189
    //   180: aload 6
    //   182: ldc 110
    //   184: invokestatic 79	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   187: aload_3
    //   188: athrow
    //   189: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	190	0	this	DefaultCachedSettingsIo
    //   0	190	1	paramLong	long
    //   0	190	3	paramJSONObject	org.json.JSONObject
    //   17	135	4	localObject1	Object
    //   20	71	5	localObject2	Object
    //   24	105	6	localObject3	Object
    //   133	6	6	localException1	Exception
    //   155	26	6	localJSONObject	org.json.JSONObject
    //   41	100	7	localObject4	Object
    //   149	16	7	localException2	Exception
    //   50	47	8	localFile	java.io.File
    //   59	22	9	localFileStoreImpl	io.fabric.sdk.android.services.persistence.FileStoreImpl
    // Exception table:
    //   from	to	target	type
    //   101	115	125	finally
    //   101	115	133	java/lang/Exception
    //   26	34	145	finally
    //   38	43	145	finally
    //   47	52	145	finally
    //   56	61	145	finally
    //   65	74	145	finally
    //   78	90	145	finally
    //   94	101	145	finally
    //   157	171	145	finally
    //   26	34	149	java/lang/Exception
    //   38	43	149	java/lang/Exception
    //   47	52	149	java/lang/Exception
    //   56	61	149	java/lang/Exception
    //   65	74	149	java/lang/Exception
    //   78	90	149	java/lang/Exception
    //   94	101	149	java/lang/Exception
  }
}

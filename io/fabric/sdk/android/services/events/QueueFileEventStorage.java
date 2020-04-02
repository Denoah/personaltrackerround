package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.QueueFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class QueueFileEventStorage
  implements EventsStorage
{
  private final Context context;
  private QueueFile queueFile;
  private File targetDirectory;
  private final String targetDirectoryName;
  private final File workingDirectory;
  private final File workingFile;
  
  public QueueFileEventStorage(Context paramContext, File paramFile, String paramString1, String paramString2)
    throws IOException
  {
    this.context = paramContext;
    this.workingDirectory = paramFile;
    this.targetDirectoryName = paramString2;
    this.workingFile = new File(this.workingDirectory, paramString1);
    this.queueFile = new QueueFile(this.workingFile);
    createTargetDirectory();
  }
  
  private void createTargetDirectory()
  {
    File localFile = new File(this.workingDirectory, this.targetDirectoryName);
    this.targetDirectory = localFile;
    if (!localFile.exists()) {
      this.targetDirectory.mkdirs();
    }
  }
  
  /* Error */
  private void move(File paramFile1, File paramFile2)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aconst_null
    //   3: astore 4
    //   5: new 61	java/io/FileInputStream
    //   8: astore 5
    //   10: aload 5
    //   12: aload_1
    //   13: invokespecial 62	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   16: aload_0
    //   17: aload_2
    //   18: invokevirtual 66	io/fabric/sdk/android/services/events/QueueFileEventStorage:getMoveOutputStream	(Ljava/io/File;)Ljava/io/OutputStream;
    //   21: astore_2
    //   22: aload_2
    //   23: astore 4
    //   25: aload 5
    //   27: aload_2
    //   28: sipush 1024
    //   31: newarray byte
    //   33: invokestatic 72	io/fabric/sdk/android/services/common/CommonUtils:copyStream	(Ljava/io/InputStream;Ljava/io/OutputStream;[B)V
    //   36: aload 5
    //   38: ldc 74
    //   40: invokestatic 78	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   43: aload_2
    //   44: ldc 80
    //   46: invokestatic 78	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   49: aload_1
    //   50: invokevirtual 83	java/io/File:delete	()Z
    //   53: pop
    //   54: return
    //   55: astore_2
    //   56: goto +10 -> 66
    //   59: astore_2
    //   60: aconst_null
    //   61: astore 4
    //   63: aload_3
    //   64: astore 5
    //   66: aload 5
    //   68: ldc 74
    //   70: invokestatic 78	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   73: aload 4
    //   75: ldc 80
    //   77: invokestatic 78	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   80: aload_1
    //   81: invokevirtual 83	java/io/File:delete	()Z
    //   84: pop
    //   85: aload_2
    //   86: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	87	0	this	QueueFileEventStorage
    //   0	87	1	paramFile1	File
    //   0	87	2	paramFile2	File
    //   1	63	3	localObject1	Object
    //   3	71	4	localFile	File
    //   8	59	5	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   16	22	55	finally
    //   25	36	55	finally
    //   5	16	59	finally
  }
  
  public void add(byte[] paramArrayOfByte)
    throws IOException
  {
    this.queueFile.add(paramArrayOfByte);
  }
  
  public boolean canWorkingFileStore(int paramInt1, int paramInt2)
  {
    return this.queueFile.hasSpaceFor(paramInt1, paramInt2);
  }
  
  public void deleteFilesInRollOverDirectory(List<File> paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      paramList = (File)localIterator.next();
      CommonUtils.logControlled(this.context, String.format("deleting sent analytics file %s", new Object[] { paramList.getName() }));
      paramList.delete();
    }
  }
  
  public void deleteWorkingFile()
  {
    try
    {
      this.queueFile.close();
      this.workingFile.delete();
      return;
    }
    catch (IOException localIOException)
    {
      for (;;) {}
    }
  }
  
  public List<File> getAllFilesInRollOverDirectory()
  {
    return Arrays.asList(this.targetDirectory.listFiles());
  }
  
  public List<File> getBatchOfFilesToSend(int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    File[] arrayOfFile = this.targetDirectory.listFiles();
    int i = arrayOfFile.length;
    for (int j = 0; j < i; j++)
    {
      localArrayList.add(arrayOfFile[j]);
      if (localArrayList.size() >= paramInt) {
        break;
      }
    }
    return localArrayList;
  }
  
  public OutputStream getMoveOutputStream(File paramFile)
    throws IOException
  {
    return new FileOutputStream(paramFile);
  }
  
  public File getRollOverDirectory()
  {
    return this.targetDirectory;
  }
  
  public File getWorkingDirectory()
  {
    return this.workingDirectory;
  }
  
  public int getWorkingFileUsedSizeInBytes()
  {
    return this.queueFile.usedBytes();
  }
  
  public boolean isWorkingFileEmpty()
  {
    return this.queueFile.isEmpty();
  }
  
  public void rollOver(String paramString)
    throws IOException
  {
    this.queueFile.close();
    move(this.workingFile, new File(this.targetDirectory, paramString));
    this.queueFile = new QueueFile(this.workingFile);
  }
}

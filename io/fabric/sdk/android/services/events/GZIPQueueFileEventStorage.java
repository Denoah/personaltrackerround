package io.fabric.sdk.android.services.events;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPQueueFileEventStorage
  extends QueueFileEventStorage
{
  public GZIPQueueFileEventStorage(Context paramContext, File paramFile, String paramString1, String paramString2)
    throws IOException
  {
    super(paramContext, paramFile, paramString1, paramString2);
  }
  
  public OutputStream getMoveOutputStream(File paramFile)
    throws IOException
  {
    return new GZIPOutputStream(new FileOutputStream(paramFile));
  }
}

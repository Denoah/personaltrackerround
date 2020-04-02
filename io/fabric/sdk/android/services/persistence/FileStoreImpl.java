package io.fabric.sdk.android.services.persistence;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Environment;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import java.io.File;

public class FileStoreImpl
  implements FileStore
{
  private final String contentPath;
  private final Context context;
  private final String legacySupport;
  
  public FileStoreImpl(Kit paramKit)
  {
    if (paramKit.getContext() != null)
    {
      this.context = paramKit.getContext();
      this.contentPath = paramKit.getPath();
      paramKit = new StringBuilder();
      paramKit.append("Android/");
      paramKit.append(this.context.getPackageName());
      this.legacySupport = paramKit.toString();
      return;
    }
    throw new IllegalStateException("Cannot get directory before context has been set. Call Fabric.with() first");
  }
  
  public File getCacheDir()
  {
    return prepare(this.context.getCacheDir());
  }
  
  public File getExternalCacheDir()
  {
    Object localObject;
    if (isExternalStorageAvailable())
    {
      if (Build.VERSION.SDK_INT >= 8)
      {
        localObject = this.context.getExternalCacheDir();
      }
      else
      {
        File localFile = Environment.getExternalStorageDirectory();
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(this.legacySupport);
        ((StringBuilder)localObject).append("/cache/");
        ((StringBuilder)localObject).append(this.contentPath);
        localObject = new File(localFile, ((StringBuilder)localObject).toString());
      }
    }
    else {
      localObject = null;
    }
    return prepare((File)localObject);
  }
  
  public File getExternalFilesDir()
  {
    boolean bool = isExternalStorageAvailable();
    Object localObject = null;
    if (bool) {
      if (Build.VERSION.SDK_INT >= 8)
      {
        localObject = this.context.getExternalFilesDir(null);
      }
      else
      {
        File localFile = Environment.getExternalStorageDirectory();
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(this.legacySupport);
        ((StringBuilder)localObject).append("/files/");
        ((StringBuilder)localObject).append(this.contentPath);
        localObject = new File(localFile, ((StringBuilder)localObject).toString());
      }
    }
    return prepare((File)localObject);
  }
  
  public File getFilesDir()
  {
    return prepare(this.context.getFilesDir());
  }
  
  boolean isExternalStorageAvailable()
  {
    if (!"mounted".equals(Environment.getExternalStorageState()))
    {
      Fabric.getLogger().w("Fabric", "External Storage is not mounted and/or writable\nHave you declared android.permission.WRITE_EXTERNAL_STORAGE in the manifest?");
      return false;
    }
    return true;
  }
  
  File prepare(File paramFile)
  {
    if (paramFile != null)
    {
      if ((!paramFile.exists()) && (!paramFile.mkdirs())) {
        Fabric.getLogger().w("Fabric", "Couldn't create file");
      } else {
        return paramFile;
      }
    }
    else {
      Fabric.getLogger().d("Fabric", "Null File");
    }
    return null;
  }
}

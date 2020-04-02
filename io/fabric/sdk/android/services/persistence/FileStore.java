package io.fabric.sdk.android.services.persistence;

import java.io.File;

public abstract interface FileStore
{
  public abstract File getCacheDir();
  
  public abstract File getExternalCacheDir();
  
  public abstract File getExternalFilesDir();
  
  public abstract File getFilesDir();
}

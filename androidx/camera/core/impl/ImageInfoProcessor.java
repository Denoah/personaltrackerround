package androidx.camera.core.impl;

import androidx.camera.core.ImageInfo;

public abstract interface ImageInfoProcessor
{
  public abstract CaptureStage getCaptureStage();
  
  public abstract boolean process(ImageInfo paramImageInfo);
}

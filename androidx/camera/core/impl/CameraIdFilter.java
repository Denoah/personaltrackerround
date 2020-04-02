package androidx.camera.core.impl;

import java.util.Set;

public abstract interface CameraIdFilter
{
  public abstract Set<String> filter(Set<String> paramSet);
}

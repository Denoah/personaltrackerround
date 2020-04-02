package io.fabric.sdk.android.services.events;

import java.io.File;
import java.util.List;

public abstract interface FilesSender
{
  public abstract boolean send(List<File> paramList);
}

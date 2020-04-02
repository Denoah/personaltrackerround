package androidx.work.impl;

public abstract interface ExecutionListener
{
  public abstract void onExecuted(String paramString, boolean paramBoolean);
}

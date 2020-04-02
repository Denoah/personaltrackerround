package io.reactivex.functions;

public abstract interface BooleanSupplier
{
  public abstract boolean getAsBoolean()
    throws Exception;
}

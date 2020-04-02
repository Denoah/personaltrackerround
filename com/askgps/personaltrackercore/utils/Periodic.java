package com.askgps.personaltrackercore.utils;

import kotlin.Metadata;
import kotlinx.coroutines.Deferred;

@Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\000\n\002\020\000\n\000\n\002\030\002\n\000\n\002\020\t\n\000\bf\030\000*\004\b\000\020\0012\0020\002J\026\020\003\032\b\022\004\022\0028\0000\0042\006\020\005\032\0020\006H&?\006\007"}, d2={"Lcom/askgps/personaltrackercore/utils/Periodic;", "T", "", "periodicResultAsync", "Lkotlinx/coroutines/Deferred;", "period", "", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public abstract interface Periodic<T>
{
  public abstract Deferred<T> periodicResultAsync(long paramLong);
}

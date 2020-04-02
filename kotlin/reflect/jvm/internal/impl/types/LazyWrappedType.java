package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

public final class LazyWrappedType
  extends WrappedType
{
  private final Function0<KotlinType> computation;
  private final NotNullLazyValue<KotlinType> lazyValue;
  private final StorageManager storageManager;
  
  public LazyWrappedType(StorageManager paramStorageManager, Function0<? extends KotlinType> paramFunction0)
  {
    this.storageManager = paramStorageManager;
    this.computation = paramFunction0;
    this.lazyValue = paramStorageManager.createLazyValue(paramFunction0);
  }
  
  protected KotlinType getDelegate()
  {
    return (KotlinType)this.lazyValue.invoke();
  }
  
  public boolean isComputed()
  {
    return this.lazyValue.isComputed();
  }
  
  public LazyWrappedType refine(final KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeRefiner, "kotlinTypeRefiner");
    new LazyWrappedType(this.storageManager, (Function0)new Lambda(paramKotlinTypeRefiner)
    {
      public final KotlinType invoke()
      {
        return paramKotlinTypeRefiner.refineType((KotlinType)LazyWrappedType.access$getComputation$p(this.this$0).invoke());
      }
    });
  }
}

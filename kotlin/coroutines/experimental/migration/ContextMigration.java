package kotlin.coroutines.experimental.migration;

import kotlin.Metadata;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.experimental.CoroutineContext;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\005\b\002\030\000 \0072\0020\001:\001\007B\r\022\006\020\002\032\0020\003?\006\002\020\004R\021\020\002\032\0020\003?\006\b\n\000\032\004\b\005\020\006?\006\b"}, d2={"Lkotlin/coroutines/experimental/migration/ContextMigration;", "Lkotlin/coroutines/AbstractCoroutineContextElement;", "context", "Lkotlin/coroutines/experimental/CoroutineContext;", "(Lkotlin/coroutines/experimental/CoroutineContext;)V", "getContext", "()Lkotlin/coroutines/experimental/CoroutineContext;", "Key", "kotlin-stdlib-coroutines"}, k=1, mv={1, 1, 16})
final class ContextMigration
  extends AbstractCoroutineContextElement
{
  public static final Key Key = new Key(null);
  private final CoroutineContext context;
  
  public ContextMigration(CoroutineContext paramCoroutineContext)
  {
    super((CoroutineContext.Key)Key);
    this.context = paramCoroutineContext;
  }
  
  public final CoroutineContext getContext()
  {
    return this.context;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\020\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\b?\003\030\0002\b\022\004\022\0020\0020\001B\007\b\002?\006\002\020\003?\006\004"}, d2={"Lkotlin/coroutines/experimental/migration/ContextMigration$Key;", "Lkotlin/coroutines/CoroutineContext$Key;", "Lkotlin/coroutines/experimental/migration/ContextMigration;", "()V", "kotlin-stdlib-coroutines"}, k=1, mv={1, 1, 16})
  public static final class Key
    implements CoroutineContext.Key<ContextMigration>
  {
    private Key() {}
  }
}

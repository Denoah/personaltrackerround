package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;

public abstract interface SupertypeLoopChecker
{
  public abstract Collection<KotlinType> findLoopsInSupertypesAndDisconnect(TypeConstructor paramTypeConstructor, Collection<? extends KotlinType> paramCollection, Function1<? super TypeConstructor, ? extends Iterable<? extends KotlinType>> paramFunction1, Function1<? super KotlinType, Unit> paramFunction11);
  
  public static final class EMPTY
    implements SupertypeLoopChecker
  {
    public static final EMPTY INSTANCE = new EMPTY();
    
    private EMPTY() {}
    
    public Collection<KotlinType> findLoopsInSupertypesAndDisconnect(TypeConstructor paramTypeConstructor, Collection<? extends KotlinType> paramCollection, Function1<? super TypeConstructor, ? extends Iterable<? extends KotlinType>> paramFunction1, Function1<? super KotlinType, Unit> paramFunction11)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeConstructor, "currentTypeConstructor");
      Intrinsics.checkParameterIsNotNull(paramCollection, "superTypes");
      Intrinsics.checkParameterIsNotNull(paramFunction1, "neighbors");
      Intrinsics.checkParameterIsNotNull(paramFunction11, "reportLoop");
      return paramCollection;
    }
  }
}

package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.checker.StrictEqualityTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;

public abstract class KotlinType
  implements Annotated, KotlinTypeMarker
{
  private int cachedHashCode;
  
  private KotlinType() {}
  
  private final int computeHashCode()
  {
    if (KotlinTypeKt.isError(this)) {
      return super.hashCode();
    }
    return (getConstructor().hashCode() * 31 + getArguments().hashCode()) * 31 + isMarkedNullable();
  }
  
  public final boolean equals(Object paramObject)
  {
    KotlinType localKotlinType = (KotlinType)this;
    boolean bool1 = true;
    if (localKotlinType == paramObject) {
      return true;
    }
    if (!(paramObject instanceof KotlinType)) {
      return false;
    }
    boolean bool2 = isMarkedNullable();
    paramObject = (KotlinType)paramObject;
    if ((bool2 != paramObject.isMarkedNullable()) || (!StrictEqualityTypeChecker.INSTANCE.strictEqualTypes(unwrap(), paramObject.unwrap()))) {
      bool1 = false;
    }
    return bool1;
  }
  
  public abstract List<TypeProjection> getArguments();
  
  public abstract TypeConstructor getConstructor();
  
  public abstract MemberScope getMemberScope();
  
  public final int hashCode()
  {
    int i = this.cachedHashCode;
    if (i != 0) {
      return i;
    }
    i = computeHashCode();
    this.cachedHashCode = i;
    return i;
  }
  
  public abstract boolean isMarkedNullable();
  
  public abstract KotlinType refine(KotlinTypeRefiner paramKotlinTypeRefiner);
  
  public abstract UnwrappedType unwrap();
}

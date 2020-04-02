package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.TypeCastException;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;

public abstract class WrappedType
  extends KotlinType
{
  public WrappedType()
  {
    super(null);
  }
  
  public Annotations getAnnotations()
  {
    return getDelegate().getAnnotations();
  }
  
  public List<TypeProjection> getArguments()
  {
    return getDelegate().getArguments();
  }
  
  public TypeConstructor getConstructor()
  {
    return getDelegate().getConstructor();
  }
  
  protected abstract KotlinType getDelegate();
  
  public MemberScope getMemberScope()
  {
    return getDelegate().getMemberScope();
  }
  
  public boolean isComputed()
  {
    return true;
  }
  
  public boolean isMarkedNullable()
  {
    return getDelegate().isMarkedNullable();
  }
  
  public String toString()
  {
    String str;
    if (isComputed()) {
      str = getDelegate().toString();
    } else {
      str = "<Not computed yet>";
    }
    return str;
  }
  
  public final UnwrappedType unwrap()
  {
    for (KotlinType localKotlinType = getDelegate(); (localKotlinType instanceof WrappedType); localKotlinType = ((WrappedType)localKotlinType).getDelegate()) {}
    if (localKotlinType != null) {
      return (UnwrappedType)localKotlinType;
    }
    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.UnwrappedType");
  }
}

package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

public abstract class DelegatingSimpleType
  extends SimpleType
{
  public DelegatingSimpleType() {}
  
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
  
  protected abstract SimpleType getDelegate();
  
  public MemberScope getMemberScope()
  {
    return getDelegate().getMemberScope();
  }
  
  public boolean isMarkedNullable()
  {
    return getDelegate().isMarkedNullable();
  }
  
  public SimpleType refine(KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeRefiner, "kotlinTypeRefiner");
    paramKotlinTypeRefiner = paramKotlinTypeRefiner.refineType((KotlinType)getDelegate());
    if (paramKotlinTypeRefiner != null) {
      return (SimpleType)replaceDelegate((SimpleType)paramKotlinTypeRefiner);
    }
    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
  }
  
  public abstract DelegatingSimpleType replaceDelegate(SimpleType paramSimpleType);
}

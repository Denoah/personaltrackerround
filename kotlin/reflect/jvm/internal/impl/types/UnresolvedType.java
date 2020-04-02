package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

public final class UnresolvedType
  extends ErrorType
{
  private final String presentableName;
  
  public UnresolvedType(String paramString, TypeConstructor paramTypeConstructor, MemberScope paramMemberScope, List<? extends TypeProjection> paramList, boolean paramBoolean)
  {
    super(paramTypeConstructor, paramMemberScope, paramList, paramBoolean);
    this.presentableName = paramString;
  }
  
  public final String getPresentableName()
  {
    return this.presentableName;
  }
  
  public SimpleType makeNullableAsSpecified(boolean paramBoolean)
  {
    return (SimpleType)new UnresolvedType(this.presentableName, getConstructor(), getMemberScope(), getArguments(), paramBoolean);
  }
  
  public UnresolvedType refine(KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeRefiner, "kotlinTypeRefiner");
    return this;
  }
}

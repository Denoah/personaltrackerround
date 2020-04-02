package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

public class ErrorType
  extends SimpleType
{
  private final List<TypeProjection> arguments;
  private final TypeConstructor constructor;
  private final boolean isMarkedNullable;
  private final MemberScope memberScope;
  
  public ErrorType(TypeConstructor paramTypeConstructor, MemberScope paramMemberScope)
  {
    this(paramTypeConstructor, paramMemberScope, null, false, 12, null);
  }
  
  public ErrorType(TypeConstructor paramTypeConstructor, MemberScope paramMemberScope, List<? extends TypeProjection> paramList, boolean paramBoolean)
  {
    this.constructor = paramTypeConstructor;
    this.memberScope = paramMemberScope;
    this.arguments = paramList;
    this.isMarkedNullable = paramBoolean;
  }
  
  public Annotations getAnnotations()
  {
    return Annotations.Companion.getEMPTY();
  }
  
  public List<TypeProjection> getArguments()
  {
    return this.arguments;
  }
  
  public TypeConstructor getConstructor()
  {
    return this.constructor;
  }
  
  public MemberScope getMemberScope()
  {
    return this.memberScope;
  }
  
  public boolean isMarkedNullable()
  {
    return this.isMarkedNullable;
  }
  
  public SimpleType makeNullableAsSpecified(boolean paramBoolean)
  {
    return (SimpleType)new ErrorType(getConstructor(), getMemberScope(), getArguments(), paramBoolean);
  }
  
  public ErrorType refine(KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeRefiner, "kotlinTypeRefiner");
    return this;
  }
  
  public SimpleType replaceAnnotations(Annotations paramAnnotations)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "newAnnotations");
    return (SimpleType)this;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getConstructor().toString());
    String str;
    if (getArguments().isEmpty()) {
      str = "";
    } else {
      str = CollectionsKt.joinToString((Iterable)getArguments(), (CharSequence)", ", (CharSequence)"<", (CharSequence)">", -1, (CharSequence)"...", null);
    }
    localStringBuilder.append(str);
    return localStringBuilder.toString();
  }
}

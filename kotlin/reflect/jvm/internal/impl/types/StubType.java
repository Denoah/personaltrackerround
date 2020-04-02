package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.model.StubTypeMarker;

public final class StubType
  extends SimpleType
  implements StubTypeMarker
{
  private final TypeConstructor constructor;
  private final boolean isMarkedNullable;
  private final MemberScope memberScope;
  private final TypeConstructor originalTypeVariable;
  
  public StubType(TypeConstructor paramTypeConstructor1, boolean paramBoolean, TypeConstructor paramTypeConstructor2, MemberScope paramMemberScope)
  {
    this.originalTypeVariable = paramTypeConstructor1;
    this.isMarkedNullable = paramBoolean;
    this.constructor = paramTypeConstructor2;
    this.memberScope = paramMemberScope;
  }
  
  public Annotations getAnnotations()
  {
    return Annotations.Companion.getEMPTY();
  }
  
  public List<TypeProjection> getArguments()
  {
    return CollectionsKt.emptyList();
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
    SimpleType localSimpleType;
    if (paramBoolean == isMarkedNullable()) {
      localSimpleType = (SimpleType)this;
    } else {
      localSimpleType = (SimpleType)new StubType(this.originalTypeVariable, paramBoolean, getConstructor(), getMemberScope());
    }
    return localSimpleType;
  }
  
  public StubType refine(KotlinTypeRefiner paramKotlinTypeRefiner)
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
    localStringBuilder.append("NonFixed: ");
    localStringBuilder.append(this.originalTypeVariable);
    return localStringBuilder.toString();
  }
}

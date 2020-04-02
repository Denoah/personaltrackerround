package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

final class SimpleTypeImpl
  extends SimpleType
{
  private final List<TypeProjection> arguments;
  private final TypeConstructor constructor;
  private final boolean isMarkedNullable;
  private final MemberScope memberScope;
  private final Function1<KotlinTypeRefiner, SimpleType> refinedTypeFactory;
  
  public SimpleTypeImpl(TypeConstructor paramTypeConstructor, List<? extends TypeProjection> paramList, boolean paramBoolean, MemberScope paramMemberScope, Function1<? super KotlinTypeRefiner, ? extends SimpleType> paramFunction1)
  {
    this.constructor = paramTypeConstructor;
    this.arguments = paramList;
    this.isMarkedNullable = paramBoolean;
    this.memberScope = paramMemberScope;
    this.refinedTypeFactory = paramFunction1;
    if (!(getMemberScope() instanceof ErrorUtils.ErrorScope)) {
      return;
    }
    paramTypeConstructor = new StringBuilder();
    paramTypeConstructor.append("SimpleTypeImpl should not be created for error type: ");
    paramTypeConstructor.append(getMemberScope());
    paramTypeConstructor.append('\n');
    paramTypeConstructor.append(getConstructor());
    throw ((Throwable)new IllegalStateException(paramTypeConstructor.toString()));
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
    SimpleType localSimpleType;
    if (paramBoolean == isMarkedNullable()) {
      localSimpleType = (SimpleType)this;
    } else if (paramBoolean) {
      localSimpleType = (SimpleType)new NullableSimpleType((SimpleType)this);
    } else {
      localSimpleType = (SimpleType)new NotNullSimpleType((SimpleType)this);
    }
    return localSimpleType;
  }
  
  public SimpleType refine(KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeRefiner, "kotlinTypeRefiner");
    paramKotlinTypeRefiner = (SimpleType)this.refinedTypeFactory.invoke(paramKotlinTypeRefiner);
    if (paramKotlinTypeRefiner == null) {
      paramKotlinTypeRefiner = (SimpleType)this;
    }
    return paramKotlinTypeRefiner;
  }
  
  public SimpleType replaceAnnotations(Annotations paramAnnotations)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "newAnnotations");
    if (paramAnnotations.isEmpty()) {
      paramAnnotations = (SimpleType)this;
    } else {
      paramAnnotations = (SimpleType)new AnnotatedSimpleType((SimpleType)this, paramAnnotations);
    }
    return paramAnnotations;
  }
}

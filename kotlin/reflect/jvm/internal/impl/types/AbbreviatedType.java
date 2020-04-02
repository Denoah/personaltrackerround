package kotlin.reflect.jvm.internal.impl.types;

import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

public final class AbbreviatedType
  extends DelegatingSimpleType
{
  private final SimpleType abbreviation;
  private final SimpleType delegate;
  
  public AbbreviatedType(SimpleType paramSimpleType1, SimpleType paramSimpleType2)
  {
    this.delegate = paramSimpleType1;
    this.abbreviation = paramSimpleType2;
  }
  
  public final SimpleType getAbbreviation()
  {
    return this.abbreviation;
  }
  
  protected SimpleType getDelegate()
  {
    return this.delegate;
  }
  
  public final SimpleType getExpandedType()
  {
    return getDelegate();
  }
  
  public AbbreviatedType makeNullableAsSpecified(boolean paramBoolean)
  {
    return new AbbreviatedType(getDelegate().makeNullableAsSpecified(paramBoolean), this.abbreviation.makeNullableAsSpecified(paramBoolean));
  }
  
  public AbbreviatedType refine(KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeRefiner, "kotlinTypeRefiner");
    Object localObject = paramKotlinTypeRefiner.refineType((KotlinType)getDelegate());
    if (localObject != null)
    {
      localObject = (SimpleType)localObject;
      paramKotlinTypeRefiner = paramKotlinTypeRefiner.refineType((KotlinType)this.abbreviation);
      if (paramKotlinTypeRefiner != null) {
        return new AbbreviatedType((SimpleType)localObject, (SimpleType)paramKotlinTypeRefiner);
      }
      throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
    }
    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
  }
  
  public AbbreviatedType replaceAnnotations(Annotations paramAnnotations)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "newAnnotations");
    return new AbbreviatedType(getDelegate().replaceAnnotations(paramAnnotations), this.abbreviation);
  }
  
  public AbbreviatedType replaceDelegate(SimpleType paramSimpleType)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleType, "delegate");
    return new AbbreviatedType(paramSimpleType, this.abbreviation);
  }
}

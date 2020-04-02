package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Set;
import kotlin._Assertions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentListMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext.DefaultImpls;
import kotlin.reflect.jvm.internal.impl.utils.SmartSet;
import kotlin.reflect.jvm.internal.impl.utils.SmartSet.Companion;

public abstract class AbstractTypeCheckerContext
  implements TypeSystemContext
{
  private int argumentsDepth;
  private ArrayDeque<SimpleTypeMarker> supertypesDeque;
  private boolean supertypesLocked;
  private Set<SimpleTypeMarker> supertypesSet;
  
  public AbstractTypeCheckerContext() {}
  
  public Boolean addSubtypeConstraint(KotlinTypeMarker paramKotlinTypeMarker1, KotlinTypeMarker paramKotlinTypeMarker2)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker1, "subType");
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker2, "superType");
    return null;
  }
  
  public abstract boolean areEqualTypeConstructors(TypeConstructorMarker paramTypeConstructorMarker1, TypeConstructorMarker paramTypeConstructorMarker2);
  
  public final void clear()
  {
    Object localObject = this.supertypesDeque;
    if (localObject == null) {
      Intrinsics.throwNpe();
    }
    ((ArrayDeque)localObject).clear();
    localObject = this.supertypesSet;
    if (localObject == null) {
      Intrinsics.throwNpe();
    }
    ((Set)localObject).clear();
    this.supertypesLocked = false;
  }
  
  public List<SimpleTypeMarker> fastCorrespondingSupertypes(SimpleTypeMarker paramSimpleTypeMarker, TypeConstructorMarker paramTypeConstructorMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$fastCorrespondingSupertypes");
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "constructor");
    return TypeSystemContext.DefaultImpls.fastCorrespondingSupertypes(this, paramSimpleTypeMarker, paramTypeConstructorMarker);
  }
  
  public TypeArgumentMarker get(TypeArgumentListMarker paramTypeArgumentListMarker, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeArgumentListMarker, "$this$get");
    return TypeSystemContext.DefaultImpls.get(this, paramTypeArgumentListMarker, paramInt);
  }
  
  public TypeArgumentMarker getArgumentOrNull(SimpleTypeMarker paramSimpleTypeMarker, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$getArgumentOrNull");
    return TypeSystemContext.DefaultImpls.getArgumentOrNull(this, paramSimpleTypeMarker, paramInt);
  }
  
  public LowerCapturedTypePolicy getLowerCapturedTypePolicy(SimpleTypeMarker paramSimpleTypeMarker, CapturedTypeMarker paramCapturedTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "subType");
    Intrinsics.checkParameterIsNotNull(paramCapturedTypeMarker, "superType");
    return LowerCapturedTypePolicy.CHECK_SUBTYPE_AND_LOWER;
  }
  
  public SeveralSupertypesWithSameConstructorPolicy getSameConstructorPolicy()
  {
    return SeveralSupertypesWithSameConstructorPolicy.INTERSECT_ARGUMENTS_AND_CHECK_AGAIN;
  }
  
  public final ArrayDeque<SimpleTypeMarker> getSupertypesDeque()
  {
    return this.supertypesDeque;
  }
  
  public final Set<SimpleTypeMarker> getSupertypesSet()
  {
    return this.supertypesSet;
  }
  
  public boolean hasFlexibleNullability(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$hasFlexibleNullability");
    return TypeSystemContext.DefaultImpls.hasFlexibleNullability(this, paramKotlinTypeMarker);
  }
  
  public boolean identicalArguments(SimpleTypeMarker paramSimpleTypeMarker1, SimpleTypeMarker paramSimpleTypeMarker2)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker1, "a");
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker2, "b");
    return TypeSystemContext.DefaultImpls.identicalArguments(this, paramSimpleTypeMarker1, paramSimpleTypeMarker2);
  }
  
  public final void initialize()
  {
    boolean bool = this.supertypesLocked;
    if ((_Assertions.ENABLED) && (!(bool ^ true))) {
      throw ((Throwable)new AssertionError("Assertion failed"));
    }
    this.supertypesLocked = true;
    if (this.supertypesDeque == null) {
      this.supertypesDeque = new ArrayDeque(4);
    }
    if (this.supertypesSet == null) {
      this.supertypesSet = ((Set)SmartSet.Companion.create());
    }
  }
  
  public abstract boolean isAllowedTypeVariable(KotlinTypeMarker paramKotlinTypeMarker);
  
  public boolean isClassType(SimpleTypeMarker paramSimpleTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$isClassType");
    return TypeSystemContext.DefaultImpls.isClassType(this, paramSimpleTypeMarker);
  }
  
  public boolean isDefinitelyNotNullType(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isDefinitelyNotNullType");
    return TypeSystemContext.DefaultImpls.isDefinitelyNotNullType(this, paramKotlinTypeMarker);
  }
  
  public boolean isDynamic(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isDynamic");
    return TypeSystemContext.DefaultImpls.isDynamic(this, paramKotlinTypeMarker);
  }
  
  public abstract boolean isErrorTypeEqualsToAnything();
  
  public boolean isIntegerLiteralType(SimpleTypeMarker paramSimpleTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$isIntegerLiteralType");
    return TypeSystemContext.DefaultImpls.isIntegerLiteralType(this, paramSimpleTypeMarker);
  }
  
  public boolean isNothing(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isNothing");
    return TypeSystemContext.DefaultImpls.isNothing(this, paramKotlinTypeMarker);
  }
  
  public SimpleTypeMarker lowerBoundIfFlexible(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$lowerBoundIfFlexible");
    return TypeSystemContext.DefaultImpls.lowerBoundIfFlexible(this, paramKotlinTypeMarker);
  }
  
  public KotlinTypeMarker prepareType(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "type");
    return paramKotlinTypeMarker;
  }
  
  public KotlinTypeMarker refineType(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "type");
    return paramKotlinTypeMarker;
  }
  
  public int size(TypeArgumentListMarker paramTypeArgumentListMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeArgumentListMarker, "$this$size");
    return TypeSystemContext.DefaultImpls.size(this, paramTypeArgumentListMarker);
  }
  
  public abstract SupertypesPolicy substitutionSupertypePolicy(SimpleTypeMarker paramSimpleTypeMarker);
  
  public TypeConstructorMarker typeConstructor(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$typeConstructor");
    return TypeSystemContext.DefaultImpls.typeConstructor(this, paramKotlinTypeMarker);
  }
  
  public SimpleTypeMarker upperBoundIfFlexible(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$upperBoundIfFlexible");
    return TypeSystemContext.DefaultImpls.upperBoundIfFlexible(this, paramKotlinTypeMarker);
  }
  
  public static enum LowerCapturedTypePolicy
  {
    static
    {
      LowerCapturedTypePolicy localLowerCapturedTypePolicy1 = new LowerCapturedTypePolicy("CHECK_ONLY_LOWER", 0);
      CHECK_ONLY_LOWER = localLowerCapturedTypePolicy1;
      LowerCapturedTypePolicy localLowerCapturedTypePolicy2 = new LowerCapturedTypePolicy("CHECK_SUBTYPE_AND_LOWER", 1);
      CHECK_SUBTYPE_AND_LOWER = localLowerCapturedTypePolicy2;
      LowerCapturedTypePolicy localLowerCapturedTypePolicy3 = new LowerCapturedTypePolicy("SKIP_LOWER", 2);
      SKIP_LOWER = localLowerCapturedTypePolicy3;
      $VALUES = new LowerCapturedTypePolicy[] { localLowerCapturedTypePolicy1, localLowerCapturedTypePolicy2, localLowerCapturedTypePolicy3 };
    }
    
    private LowerCapturedTypePolicy() {}
  }
  
  public static enum SeveralSupertypesWithSameConstructorPolicy
  {
    static
    {
      SeveralSupertypesWithSameConstructorPolicy localSeveralSupertypesWithSameConstructorPolicy1 = new SeveralSupertypesWithSameConstructorPolicy("TAKE_FIRST_FOR_SUBTYPING", 0);
      TAKE_FIRST_FOR_SUBTYPING = localSeveralSupertypesWithSameConstructorPolicy1;
      SeveralSupertypesWithSameConstructorPolicy localSeveralSupertypesWithSameConstructorPolicy2 = new SeveralSupertypesWithSameConstructorPolicy("FORCE_NOT_SUBTYPE", 1);
      FORCE_NOT_SUBTYPE = localSeveralSupertypesWithSameConstructorPolicy2;
      SeveralSupertypesWithSameConstructorPolicy localSeveralSupertypesWithSameConstructorPolicy3 = new SeveralSupertypesWithSameConstructorPolicy("CHECK_ANY_OF_THEM", 2);
      CHECK_ANY_OF_THEM = localSeveralSupertypesWithSameConstructorPolicy3;
      SeveralSupertypesWithSameConstructorPolicy localSeveralSupertypesWithSameConstructorPolicy4 = new SeveralSupertypesWithSameConstructorPolicy("INTERSECT_ARGUMENTS_AND_CHECK_AGAIN", 3);
      INTERSECT_ARGUMENTS_AND_CHECK_AGAIN = localSeveralSupertypesWithSameConstructorPolicy4;
      $VALUES = new SeveralSupertypesWithSameConstructorPolicy[] { localSeveralSupertypesWithSameConstructorPolicy1, localSeveralSupertypesWithSameConstructorPolicy2, localSeveralSupertypesWithSameConstructorPolicy3, localSeveralSupertypesWithSameConstructorPolicy4 };
    }
    
    private SeveralSupertypesWithSameConstructorPolicy() {}
  }
  
  public static abstract class SupertypesPolicy
  {
    private SupertypesPolicy() {}
    
    public abstract SimpleTypeMarker transformType(AbstractTypeCheckerContext paramAbstractTypeCheckerContext, KotlinTypeMarker paramKotlinTypeMarker);
    
    public static abstract class DoCustomTransform
      extends AbstractTypeCheckerContext.SupertypesPolicy
    {
      public DoCustomTransform()
      {
        super();
      }
    }
    
    public static final class LowerIfFlexible
      extends AbstractTypeCheckerContext.SupertypesPolicy
    {
      public static final LowerIfFlexible INSTANCE = new LowerIfFlexible();
      
      private LowerIfFlexible()
      {
        super();
      }
      
      public SimpleTypeMarker transformType(AbstractTypeCheckerContext paramAbstractTypeCheckerContext, KotlinTypeMarker paramKotlinTypeMarker)
      {
        Intrinsics.checkParameterIsNotNull(paramAbstractTypeCheckerContext, "context");
        Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "type");
        return paramAbstractTypeCheckerContext.lowerBoundIfFlexible(paramKotlinTypeMarker);
      }
    }
    
    public static final class None
      extends AbstractTypeCheckerContext.SupertypesPolicy
    {
      public static final None INSTANCE = new None();
      
      private None()
      {
        super();
      }
      
      public Void transformType(AbstractTypeCheckerContext paramAbstractTypeCheckerContext, KotlinTypeMarker paramKotlinTypeMarker)
      {
        Intrinsics.checkParameterIsNotNull(paramAbstractTypeCheckerContext, "context");
        Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "type");
        throw ((Throwable)new UnsupportedOperationException("Should not be called"));
      }
    }
    
    public static final class UpperIfFlexible
      extends AbstractTypeCheckerContext.SupertypesPolicy
    {
      public static final UpperIfFlexible INSTANCE = new UpperIfFlexible();
      
      private UpperIfFlexible()
      {
        super();
      }
      
      public SimpleTypeMarker transformType(AbstractTypeCheckerContext paramAbstractTypeCheckerContext, KotlinTypeMarker paramKotlinTypeMarker)
      {
        Intrinsics.checkParameterIsNotNull(paramAbstractTypeCheckerContext, "context");
        Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "type");
        return paramAbstractTypeCheckerContext.upperBoundIfFlexible(paramKotlinTypeMarker);
      }
    }
  }
}

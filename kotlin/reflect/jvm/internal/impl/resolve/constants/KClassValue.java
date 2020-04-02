package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns.FqNames;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

public final class KClassValue
  extends ConstantValue<Value>
{
  public static final Companion Companion = new Companion(null);
  
  public KClassValue(ClassId paramClassId, int paramInt)
  {
    this(new ClassLiteralValue(paramClassId, paramInt));
  }
  
  public KClassValue(ClassLiteralValue paramClassLiteralValue)
  {
    this((Value)new KClassValue.Value.NormalClass(paramClassLiteralValue));
  }
  
  public KClassValue(Value paramValue)
  {
    super(paramValue);
  }
  
  public final KotlinType getArgumentType(ModuleDescriptor paramModuleDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "module");
    Object localObject1 = (Value)getValue();
    if ((localObject1 instanceof KClassValue.Value.LocalClass)) {
      return ((KClassValue.Value.LocalClass)getValue()).getType();
    }
    if ((localObject1 instanceof KClassValue.Value.NormalClass))
    {
      Object localObject2 = ((KClassValue.Value.NormalClass)getValue()).getValue();
      localObject1 = ((ClassLiteralValue)localObject2).component1();
      int i = ((ClassLiteralValue)localObject2).component2();
      localObject2 = FindClassInModuleKt.findClassAcrossModuleDependencies(paramModuleDescriptor, (ClassId)localObject1);
      if (localObject2 != null)
      {
        localObject1 = ((ClassDescriptor)localObject2).getDefaultType();
        Intrinsics.checkExpressionValueIsNotNull(localObject1, "descriptor.defaultType");
        localObject1 = TypeUtilsKt.replaceArgumentsWithStarProjections((KotlinType)localObject1);
        for (int j = 0; j < i; j++)
        {
          localObject1 = paramModuleDescriptor.getBuiltIns().getArrayType(Variance.INVARIANT, (KotlinType)localObject1);
          Intrinsics.checkExpressionValueIsNotNull(localObject1, "module.builtIns.getArray…Variance.INVARIANT, type)");
          localObject1 = (KotlinType)localObject1;
        }
        return localObject1;
      }
      paramModuleDescriptor = new StringBuilder();
      paramModuleDescriptor.append("Unresolved type: ");
      paramModuleDescriptor.append(localObject1);
      paramModuleDescriptor.append(" (arrayDimensions=");
      paramModuleDescriptor.append(i);
      paramModuleDescriptor.append(')');
      paramModuleDescriptor = ErrorUtils.createErrorType(paramModuleDescriptor.toString());
      Intrinsics.checkExpressionValueIsNotNull(paramModuleDescriptor, "ErrorUtils.createErrorTy…sions=$arrayDimensions)\")");
      return (KotlinType)paramModuleDescriptor;
    }
    throw new NoWhenBranchMatchedException();
  }
  
  public KotlinType getType(ModuleDescriptor paramModuleDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "module");
    Annotations localAnnotations = Annotations.Companion.getEMPTY();
    ClassDescriptor localClassDescriptor = paramModuleDescriptor.getBuiltIns().getKClass();
    Intrinsics.checkExpressionValueIsNotNull(localClassDescriptor, "module.builtIns.kClass");
    return (KotlinType)KotlinTypeFactory.simpleNotNullType(localAnnotations, localClassDescriptor, CollectionsKt.listOf(new TypeProjectionImpl(getArgumentType(paramModuleDescriptor))));
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    public final ConstantValue<?> create(KotlinType paramKotlinType)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinType, "argumentType");
      boolean bool = KotlinTypeKt.isError(paramKotlinType);
      Object localObject1 = null;
      if (bool) {
        return null;
      }
      Object localObject2 = paramKotlinType;
      for (int i = 0; KotlinBuiltIns.isArray((KotlinType)localObject2); i++)
      {
        localObject2 = ((TypeProjection)CollectionsKt.single(((KotlinType)localObject2).getArguments())).getType();
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "type.arguments.single().type");
      }
      localObject2 = ((KotlinType)localObject2).getConstructor().getDeclarationDescriptor();
      if ((localObject2 instanceof ClassDescriptor))
      {
        localObject2 = DescriptorUtilsKt.getClassId((ClassifierDescriptor)localObject2);
        if (localObject2 != null) {
          paramKotlinType = (ConstantValue)new KClassValue((ClassId)localObject2, i);
        } else {
          return (ConstantValue)new KClassValue((KClassValue.Value)new KClassValue.Value.LocalClass(paramKotlinType));
        }
      }
      else
      {
        paramKotlinType = localObject1;
        if ((localObject2 instanceof TypeParameterDescriptor))
        {
          paramKotlinType = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.any.toSafe());
          Intrinsics.checkExpressionValueIsNotNull(paramKotlinType, "ClassId.topLevel(KotlinB…ns.FQ_NAMES.any.toSafe())");
          paramKotlinType = (ConstantValue)new KClassValue(paramKotlinType, 0);
        }
      }
      return paramKotlinType;
    }
  }
  
  public static abstract class Value
  {
    private Value() {}
    
    public static final class LocalClass
      extends KClassValue.Value
    {
      private final KotlinType type;
      
      public LocalClass(KotlinType paramKotlinType)
      {
        super();
        this.type = paramKotlinType;
      }
      
      public boolean equals(Object paramObject)
      {
        if (this != paramObject) {
          if ((paramObject instanceof LocalClass))
          {
            paramObject = (LocalClass)paramObject;
            if (Intrinsics.areEqual(this.type, paramObject.type)) {}
          }
          else
          {
            return false;
          }
        }
        return true;
      }
      
      public final KotlinType getType()
      {
        return this.type;
      }
      
      public int hashCode()
      {
        KotlinType localKotlinType = this.type;
        int i;
        if (localKotlinType != null) {
          i = localKotlinType.hashCode();
        } else {
          i = 0;
        }
        return i;
      }
      
      public String toString()
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("LocalClass(type=");
        localStringBuilder.append(this.type);
        localStringBuilder.append(")");
        return localStringBuilder.toString();
      }
    }
    
    public static final class NormalClass
      extends KClassValue.Value
    {
      private final ClassLiteralValue value;
      
      public NormalClass(ClassLiteralValue paramClassLiteralValue)
      {
        super();
        this.value = paramClassLiteralValue;
      }
      
      public boolean equals(Object paramObject)
      {
        if (this != paramObject) {
          if ((paramObject instanceof NormalClass))
          {
            paramObject = (NormalClass)paramObject;
            if (Intrinsics.areEqual(this.value, paramObject.value)) {}
          }
          else
          {
            return false;
          }
        }
        return true;
      }
      
      public final int getArrayDimensions()
      {
        return this.value.getArrayNestedness();
      }
      
      public final ClassId getClassId()
      {
        return this.value.getClassId();
      }
      
      public final ClassLiteralValue getValue()
      {
        return this.value;
      }
      
      public int hashCode()
      {
        ClassLiteralValue localClassLiteralValue = this.value;
        int i;
        if (localClassLiteralValue != null) {
          i = localClassLiteralValue.hashCode();
        } else {
          i = 0;
        }
        return i;
      }
      
      public String toString()
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("NormalClass(value=");
        localStringBuilder.append(this.value);
        localStringBuilder.append(")");
        return localStringBuilder.toString();
      }
    }
  }
}

package kotlin.reflect.jvm.internal.calls;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;

@Metadata(bv={1, 0, 3}, d1={"\000:\n\000\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\000\n\002\030\002\n\002\030\002\n\002\b\003\n\002\030\002\n\000\032\030\020\005\032\004\030\0010\006*\004\030\0010\0062\006\020\007\032\0020\002H\000\0326\020\b\032\b\022\004\022\002H\n0\t\"\n\b\000\020\n*\004\030\0010\013*\b\022\004\022\002H\n0\t2\006\020\007\032\0020\0022\b\b\002\020\f\032\0020\rH\000\032\030\020\016\032\0020\017*\006\022\002\b\0030\0202\006\020\007\032\0020\002H\000\032\030\020\021\032\0020\017*\006\022\002\b\0030\0202\006\020\007\032\0020\002H\000\032\f\020\022\032\0020\r*\0020\002H\002\032\024\020\023\032\b\022\002\b\003\030\0010\020*\004\030\0010\024H\000\032\022\020\023\032\b\022\002\b\003\030\0010\020*\0020\001H\000\"\032\020\000\032\004\030\0010\001*\0020\0028BX?\004?\006\006\032\004\b\003\020\004?\006\025"}, d2={"expectedReceiverType", "Lkotlin/reflect/jvm/internal/impl/types/KotlinType;", "Lkotlin/reflect/jvm/internal/impl/descriptors/CallableMemberDescriptor;", "getExpectedReceiverType", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;)Lorg/jetbrains/kotlin/types/KotlinType;", "coerceToExpectedReceiverType", "", "descriptor", "createInlineClassAwareCallerIfNeeded", "Lkotlin/reflect/jvm/internal/calls/Caller;", "M", "Ljava/lang/reflect/Member;", "isDefault", "", "getBoxMethod", "Ljava/lang/reflect/Method;", "Ljava/lang/Class;", "getUnboxMethod", "hasInlineClassReceiver", "toInlineClass", "Lkotlin/reflect/jvm/internal/impl/descriptors/DeclarationDescriptor;", "kotlin-reflection"}, k=2, mv={1, 1, 15})
public final class InlineClassAwareCallerKt
{
  public static final Object coerceToExpectedReceiverType(Object paramObject, CallableMemberDescriptor paramCallableMemberDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramCallableMemberDescriptor, "descriptor");
    if (((paramCallableMemberDescriptor instanceof PropertyDescriptor)) && (InlineClassesUtilsKt.isUnderlyingPropertyOfInlineClass((VariableDescriptor)paramCallableMemberDescriptor))) {
      return paramObject;
    }
    Object localObject1 = getExpectedReceiverType(paramCallableMemberDescriptor);
    Object localObject2 = paramObject;
    if (localObject1 != null)
    {
      localObject1 = toInlineClass((KotlinType)localObject1);
      localObject2 = paramObject;
      if (localObject1 != null)
      {
        paramCallableMemberDescriptor = getUnboxMethod((Class)localObject1, paramCallableMemberDescriptor);
        localObject2 = paramObject;
        if (paramCallableMemberDescriptor != null) {
          localObject2 = paramCallableMemberDescriptor.invoke(paramObject, new Object[0]);
        }
      }
    }
    return localObject2;
  }
  
  public static final <M extends Member> Caller<M> createInlineClassAwareCallerIfNeeded(Caller<? extends M> paramCaller, CallableMemberDescriptor paramCallableMemberDescriptor, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramCaller, "$this$createInlineClassAwareCallerIfNeeded");
    Intrinsics.checkParameterIsNotNull(paramCallableMemberDescriptor, "descriptor");
    boolean bool = InlineClassesUtilsKt.isGetterOfUnderlyingPropertyOfInlineClass((CallableDescriptor)paramCallableMemberDescriptor);
    int i = 0;
    if (!bool)
    {
      localObject1 = paramCallableMemberDescriptor.getValueParameters();
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "descriptor.valueParameters");
      localObject1 = (Iterable)localObject1;
      if (((localObject1 instanceof Collection)) && (((Collection)localObject1).isEmpty())) {}
      Object localObject2;
      do
      {
        while (!((Iterator)localObject1).hasNext())
        {
          j = 0;
          break;
          localObject1 = ((Iterable)localObject1).iterator();
        }
        localObject2 = (ValueParameterDescriptor)((Iterator)localObject1).next();
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "it");
        localObject2 = ((ValueParameterDescriptor)localObject2).getType();
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "it.type");
      } while (!InlineClassesUtilsKt.isInlineClassType((KotlinType)localObject2));
      j = 1;
      if (j == 0)
      {
        localObject1 = paramCallableMemberDescriptor.getReturnType();
        if ((localObject1 == null) || (InlineClassesUtilsKt.isInlineClassType((KotlinType)localObject1) != true))
        {
          j = i;
          if ((paramCaller instanceof BoundCaller)) {
            break label193;
          }
          j = i;
          if (!hasInlineClassReceiver(paramCallableMemberDescriptor)) {
            break label193;
          }
        }
      }
    }
    int j = 1;
    label193:
    Object localObject1 = paramCaller;
    if (j != 0) {
      localObject1 = (Caller)new InlineClassAwareCaller(paramCallableMemberDescriptor, paramCaller, paramBoolean);
    }
    return localObject1;
  }
  
  public static final Method getBoxMethod(Class<?> paramClass, CallableMemberDescriptor paramCallableMemberDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramClass, "$this$getBoxMethod");
    Intrinsics.checkParameterIsNotNull(paramCallableMemberDescriptor, "descriptor");
    try
    {
      Method localMethod = paramClass.getDeclaredMethod("box-impl", new Class[] { getUnboxMethod(paramClass, paramCallableMemberDescriptor).getReturnType() });
      Intrinsics.checkExpressionValueIsNotNull(localMethod, "getDeclaredMethod(\"box\" …d(descriptor).returnType)");
      return localMethod;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("No box method found in inline class: ");
      localStringBuilder.append(paramClass);
      localStringBuilder.append(" (calling ");
      localStringBuilder.append(paramCallableMemberDescriptor);
      localStringBuilder.append(')');
      throw ((Throwable)new KotlinReflectionInternalError(localStringBuilder.toString()));
    }
  }
  
  private static final KotlinType getExpectedReceiverType(CallableMemberDescriptor paramCallableMemberDescriptor)
  {
    ReceiverParameterDescriptor localReceiverParameterDescriptor1 = paramCallableMemberDescriptor.getExtensionReceiverParameter();
    ReceiverParameterDescriptor localReceiverParameterDescriptor2 = paramCallableMemberDescriptor.getDispatchReceiverParameter();
    Object localObject1 = null;
    Object localObject2 = null;
    if (localReceiverParameterDescriptor1 != null)
    {
      paramCallableMemberDescriptor = localReceiverParameterDescriptor1.getType();
    }
    else if (localReceiverParameterDescriptor2 == null)
    {
      paramCallableMemberDescriptor = (CallableMemberDescriptor)localObject1;
    }
    else if ((paramCallableMemberDescriptor instanceof ConstructorDescriptor))
    {
      paramCallableMemberDescriptor = localReceiverParameterDescriptor2.getType();
    }
    else
    {
      localObject1 = paramCallableMemberDescriptor.getContainingDeclaration();
      paramCallableMemberDescriptor = (CallableMemberDescriptor)localObject1;
      if (!(localObject1 instanceof ClassDescriptor)) {
        paramCallableMemberDescriptor = null;
      }
      localObject1 = (ClassDescriptor)paramCallableMemberDescriptor;
      paramCallableMemberDescriptor = localObject2;
      if (localObject1 != null) {
        paramCallableMemberDescriptor = ((ClassDescriptor)localObject1).getDefaultType();
      }
      paramCallableMemberDescriptor = (KotlinType)paramCallableMemberDescriptor;
    }
    return paramCallableMemberDescriptor;
  }
  
  public static final Method getUnboxMethod(Class<?> paramClass, CallableMemberDescriptor paramCallableMemberDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramClass, "$this$getUnboxMethod");
    Intrinsics.checkParameterIsNotNull(paramCallableMemberDescriptor, "descriptor");
    try
    {
      Method localMethod = paramClass.getDeclaredMethod("unbox-impl", new Class[0]);
      Intrinsics.checkExpressionValueIsNotNull(localMethod, "getDeclaredMethod(\"unbox…FOR_INLINE_CLASS_MEMBERS)");
      return localMethod;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("No unbox method found in inline class: ");
      localStringBuilder.append(paramClass);
      localStringBuilder.append(" (calling ");
      localStringBuilder.append(paramCallableMemberDescriptor);
      localStringBuilder.append(')');
      throw ((Throwable)new KotlinReflectionInternalError(localStringBuilder.toString()));
    }
  }
  
  private static final boolean hasInlineClassReceiver(CallableMemberDescriptor paramCallableMemberDescriptor)
  {
    paramCallableMemberDescriptor = getExpectedReceiverType(paramCallableMemberDescriptor);
    boolean bool = true;
    if ((paramCallableMemberDescriptor == null) || (InlineClassesUtilsKt.isInlineClassType(paramCallableMemberDescriptor) != true)) {
      bool = false;
    }
    return bool;
  }
  
  public static final Class<?> toInlineClass(DeclarationDescriptor paramDeclarationDescriptor)
  {
    if ((paramDeclarationDescriptor instanceof ClassDescriptor))
    {
      ClassDescriptor localClassDescriptor = (ClassDescriptor)paramDeclarationDescriptor;
      if (localClassDescriptor.isInline())
      {
        Object localObject = UtilKt.toJavaClass(localClassDescriptor);
        if (localObject != null) {
          return (DeclarationDescriptor)localObject;
        }
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Class object for the class ");
        ((StringBuilder)localObject).append(localClassDescriptor.getName());
        ((StringBuilder)localObject).append(" cannot be found (classId=");
        ((StringBuilder)localObject).append(DescriptorUtilsKt.getClassId((ClassifierDescriptor)paramDeclarationDescriptor));
        ((StringBuilder)localObject).append(')');
        throw ((Throwable)new KotlinReflectionInternalError(((StringBuilder)localObject).toString()));
      }
    }
    paramDeclarationDescriptor = null;
    return paramDeclarationDescriptor;
  }
  
  public static final Class<?> toInlineClass(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$toInlineClass");
    return toInlineClass((DeclarationDescriptor)paramKotlinType.getConstructor().getDeclarationDescriptor());
  }
}

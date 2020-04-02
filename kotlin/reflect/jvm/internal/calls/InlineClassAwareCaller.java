package kotlin.reflect.jvm.internal.calls;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.IntRange.Companion;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

@Metadata(bv={1, 0, 3}, d1={"\000B\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\002\n\002\030\002\n\002\b\004\n\002\020 \n\002\030\002\n\002\b\006\n\002\020\000\n\000\n\002\020\021\n\002\b\003\b\000\030\000*\f\b\000\020\001 \001*\004\030\0010\0022\b\022\004\022\002H\0010\003:\001\034B#\022\006\020\004\032\0020\005\022\f\020\006\032\b\022\004\022\0028\0000\003\022\006\020\007\032\0020\b?\006\002\020\tJ\033\020\027\032\004\030\0010\0302\n\020\031\032\006\022\002\b\0030\032H\026?\006\002\020\033R\024\020\006\032\b\022\004\022\0028\0000\003X?\004?\006\002\n\000R\016\020\n\032\0020\013X?\004?\006\002\n\000R\016\020\007\032\0020\bX?\004?\006\002\n\000R\024\020\f\032\0028\0008VX?\004?\006\006\032\004\b\r\020\016R\032\020\017\032\b\022\004\022\0020\0210\0208VX?\004?\006\006\032\004\b\022\020\023R\024\020\024\032\0020\0218VX?\004?\006\006\032\004\b\025\020\026?\006\035"}, d2={"Lkotlin/reflect/jvm/internal/calls/InlineClassAwareCaller;", "M", "Ljava/lang/reflect/Member;", "Lkotlin/reflect/jvm/internal/calls/Caller;", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/CallableMemberDescriptor;", "caller", "isDefault", "", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;Lkotlin/reflect/jvm/internal/calls/Caller;Z)V", "data", "Lkotlin/reflect/jvm/internal/calls/InlineClassAwareCaller$BoxUnboxData;", "member", "getMember", "()Ljava/lang/reflect/Member;", "parameterTypes", "", "Ljava/lang/reflect/Type;", "getParameterTypes", "()Ljava/util/List;", "returnType", "getReturnType", "()Ljava/lang/reflect/Type;", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "BoxUnboxData", "kotlin-reflection"}, k=1, mv={1, 1, 15})
public final class InlineClassAwareCaller<M extends Member>
  implements Caller<M>
{
  private final Caller<M> caller;
  private final BoxUnboxData data;
  private final boolean isDefault;
  
  public InlineClassAwareCaller(CallableMemberDescriptor paramCallableMemberDescriptor, Caller<? extends M> paramCaller, boolean paramBoolean)
  {
    this.caller = paramCaller;
    this.isDefault = paramBoolean;
    Object localObject1 = (InlineClassAwareCaller)this;
    paramCaller = paramCallableMemberDescriptor.getReturnType();
    if (paramCaller == null) {
      Intrinsics.throwNpe();
    }
    Intrinsics.checkExpressionValueIsNotNull(paramCaller, "descriptor.returnType!!");
    paramCaller = InlineClassAwareCallerKt.toInlineClass(paramCaller);
    if (paramCaller != null) {
      paramCaller = InlineClassAwareCallerKt.getBoxMethod(paramCaller, paramCallableMemberDescriptor);
    } else {
      paramCaller = null;
    }
    paramBoolean = InlineClassesUtilsKt.isGetterOfUnderlyingPropertyOfInlineClass((CallableDescriptor)paramCallableMemberDescriptor);
    int i = 0;
    Object localObject2;
    label205:
    int m;
    if (paramBoolean)
    {
      paramCallableMemberDescriptor = new BoxUnboxData(IntRange.Companion.getEMPTY(), new Method[0], paramCaller);
    }
    else
    {
      localObject2 = ((InlineClassAwareCaller)localObject1).caller;
      paramBoolean = localObject2 instanceof CallerImpl.Method.BoundStatic;
      int j = -1;
      if (!paramBoolean)
      {
        if ((paramCallableMemberDescriptor instanceof ConstructorDescriptor))
        {
          if ((localObject2 instanceof BoundCaller)) {
            break label205;
          }
        }
        else if ((paramCallableMemberDescriptor.getDispatchReceiverParameter() != null) && (!(((InlineClassAwareCaller)localObject1).caller instanceof BoundCaller)))
        {
          localObject2 = paramCallableMemberDescriptor.getContainingDeclaration();
          Intrinsics.checkExpressionValueIsNotNull(localObject2, "descriptor.containingDeclaration");
          if (!InlineClassesUtilsKt.isInlineClass((DeclarationDescriptor)localObject2))
          {
            j = 1;
            break label205;
          }
        }
        j = 0;
      }
      if (((InlineClassAwareCaller)localObject1).isDefault) {
        k = 2;
      } else {
        k = 0;
      }
      Object localObject3 = new ArrayList();
      localObject2 = paramCallableMemberDescriptor.getExtensionReceiverParameter();
      if (localObject2 != null) {
        localObject2 = ((ReceiverParameterDescriptor)localObject2).getType();
      } else {
        localObject2 = null;
      }
      if (localObject2 != null)
      {
        ((ArrayList)localObject3).add(localObject2);
      }
      else if ((paramCallableMemberDescriptor instanceof ConstructorDescriptor))
      {
        localObject2 = ((ConstructorDescriptor)paramCallableMemberDescriptor).getConstructedClass();
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "descriptor.constructedClass");
        if (((ClassDescriptor)localObject2).isInner())
        {
          localObject2 = ((ClassDescriptor)localObject2).getContainingDeclaration();
          if (localObject2 != null) {
            ((ArrayList)localObject3).add(((ClassDescriptor)localObject2).getDefaultType());
          } else {
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
          }
        }
      }
      else
      {
        localObject2 = paramCallableMemberDescriptor.getContainingDeclaration();
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "descriptor.containingDeclaration");
        if ((localObject2 instanceof ClassDescriptor))
        {
          localObject2 = (ClassDescriptor)localObject2;
          if (((ClassDescriptor)localObject2).isInline()) {
            ((ArrayList)localObject3).add(((ClassDescriptor)localObject2).getDefaultType());
          }
        }
      }
      localObject2 = paramCallableMemberDescriptor.getValueParameters();
      Intrinsics.checkExpressionValueIsNotNull(localObject2, "descriptor.valueParameters");
      Object localObject4 = ((Iterable)localObject2).iterator();
      while (((Iterator)localObject4).hasNext())
      {
        localObject2 = ((Iterator)localObject4).next();
        ((Collection)localObject3).add(((ValueParameterDescriptor)localObject2).getType());
      }
      localObject2 = (Collection)localObject3;
      localObject3 = (List)localObject3;
      m = ((List)localObject3).size() + j + k;
      localObject2 = (Caller)localObject1;
      if (CallerKt.getArity((Caller)localObject2) != m) {
        break label642;
      }
      localObject4 = RangesKt.until(Math.max(j, 0), ((List)localObject3).size() + j);
      localObject1 = new Method[m];
      for (int k = i; k < m; k++)
      {
        if (((IntRange)localObject4).contains(k))
        {
          localObject2 = InlineClassAwareCallerKt.toInlineClass((KotlinType)((List)localObject3).get(k - j));
          if (localObject2 != null)
          {
            localObject2 = InlineClassAwareCallerKt.getUnboxMethod((Class)localObject2, paramCallableMemberDescriptor);
            break label610;
          }
        }
        localObject2 = null;
        label610:
        localObject1[k] = localObject2;
      }
      paramCallableMemberDescriptor = new BoxUnboxData((IntRange)localObject4, (Method[])localObject1, paramCaller);
    }
    this.data = paramCallableMemberDescriptor;
    return;
    label642:
    paramCaller = new StringBuilder();
    paramCaller.append("Inconsistent number of parameters in the descriptor and Java reflection object: ");
    paramCaller.append(CallerKt.getArity((Caller)localObject2));
    paramCaller.append(" != ");
    paramCaller.append(m);
    paramCaller.append('\n');
    paramCaller.append("Calling: ");
    paramCaller.append(paramCallableMemberDescriptor);
    paramCaller.append('\n');
    paramCaller.append("Parameter types: ");
    paramCaller.append(((InlineClassAwareCaller)localObject1).getParameterTypes());
    paramCaller.append(")\n");
    paramCaller.append("Default: ");
    paramCaller.append(((InlineClassAwareCaller)localObject1).isDefault);
    throw ((Throwable)new KotlinReflectionInternalError(paramCaller.toString()));
  }
  
  public Object call(Object[] paramArrayOfObject)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
    Object localObject1 = this.data;
    Object localObject2 = ((BoxUnboxData)localObject1).component1();
    Method[] arrayOfMethod = ((BoxUnboxData)localObject1).component2();
    Method localMethod1 = ((BoxUnboxData)localObject1).component3();
    Object[] arrayOfObject = Arrays.copyOf(paramArrayOfObject, paramArrayOfObject.length);
    Intrinsics.checkExpressionValueIsNotNull(arrayOfObject, "java.util.Arrays.copyOf(this, size)");
    if (arrayOfObject != null)
    {
      int i = ((IntRange)localObject2).getFirst();
      int j = ((IntRange)localObject2).getLast();
      if (i <= j) {
        for (;;)
        {
          Method localMethod2 = arrayOfMethod[i];
          localObject1 = paramArrayOfObject[i];
          localObject2 = localObject1;
          if (localMethod2 != null)
          {
            localObject2 = localObject1;
            if (localObject1 != null) {
              localObject2 = localMethod2.invoke(localObject1, new Object[0]);
            }
          }
          arrayOfObject[i] = localObject2;
          if (i == j) {
            break;
          }
          i++;
        }
      }
      localObject2 = this.caller.call(arrayOfObject);
      paramArrayOfObject = (Object[])localObject2;
      if (localMethod1 != null)
      {
        localObject1 = localMethod1.invoke(null, new Object[] { localObject2 });
        paramArrayOfObject = (Object[])localObject2;
        if (localObject1 != null) {
          paramArrayOfObject = (Object[])localObject1;
        }
      }
      return paramArrayOfObject;
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
  }
  
  public M getMember()
  {
    return this.caller.getMember();
  }
  
  public List<Type> getParameterTypes()
  {
    return this.caller.getParameterTypes();
  }
  
  public Type getReturnType()
  {
    return this.caller.getReturnType();
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\034\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\020\021\n\002\030\002\n\002\b\r\b\002\030\0002\0020\001B'\022\006\020\002\032\0020\003\022\016\020\004\032\n\022\006\022\004\030\0010\0060\005\022\b\020\007\032\004\030\0010\006?\006\002\020\bJ\t\020\020\032\0020\003H?\002J\026\020\021\032\n\022\006\022\004\030\0010\0060\005H?\002?\006\002\020\016J\013\020\022\032\004\030\0010\006H?\002R\021\020\002\032\0020\003?\006\b\n\000\032\004\b\t\020\nR\023\020\007\032\004\030\0010\006?\006\b\n\000\032\004\b\013\020\fR\033\020\004\032\n\022\006\022\004\030\0010\0060\005?\006\n\n\002\020\017\032\004\b\r\020\016?\006\023"}, d2={"Lkotlin/reflect/jvm/internal/calls/InlineClassAwareCaller$BoxUnboxData;", "", "argumentRange", "Lkotlin/ranges/IntRange;", "unbox", "", "Ljava/lang/reflect/Method;", "box", "(Lkotlin/ranges/IntRange;[Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)V", "getArgumentRange", "()Lkotlin/ranges/IntRange;", "getBox", "()Ljava/lang/reflect/Method;", "getUnbox", "()[Ljava/lang/reflect/Method;", "[Ljava/lang/reflect/Method;", "component1", "component2", "component3", "kotlin-reflection"}, k=1, mv={1, 1, 15})
  private static final class BoxUnboxData
  {
    private final IntRange argumentRange;
    private final Method box;
    private final Method[] unbox;
    
    public BoxUnboxData(IntRange paramIntRange, Method[] paramArrayOfMethod, Method paramMethod)
    {
      this.argumentRange = paramIntRange;
      this.unbox = paramArrayOfMethod;
      this.box = paramMethod;
    }
    
    public final IntRange component1()
    {
      return this.argumentRange;
    }
    
    public final Method[] component2()
    {
      return this.unbox;
    }
    
    public final Method component3()
    {
      return this.box;
    }
  }
}

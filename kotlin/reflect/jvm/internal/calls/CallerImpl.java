package kotlin.reflect.jvm.internal.calls;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;

@Metadata(bv={1, 0, 3}, d1={"\000Z\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\021\n\002\b\007\n\002\020 \n\002\b\005\n\002\020\002\n\000\n\002\020\000\n\002\b\b\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\000\b0\030\000 \036*\n\b\000\020\001 \001*\0020\0022\b\022\004\022\002H\0010\003:\b\033\034\035\036\037 !\"B3\b\002\022\006\020\004\032\0028\000\022\006\020\005\032\0020\006\022\f\020\007\032\b\022\002\b\003\030\0010\b\022\f\020\t\032\b\022\004\022\0020\0060\n?\006\002\020\013J\022\020\027\032\0020\0302\b\020\031\032\004\030\0010\032H\004R\027\020\007\032\b\022\002\b\003\030\0010\b?\006\b\n\000\032\004\b\f\020\rR\023\020\004\032\0028\000?\006\n\n\002\020\020\032\004\b\016\020\017R\032\020\021\032\b\022\004\022\0020\0060\022X?\004?\006\b\n\000\032\004\b\023\020\024R\021\020\005\032\0020\006?\006\b\n\000\032\004\b\025\020\026?\001\007#$%&'()?\006*"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl;", "M", "Ljava/lang/reflect/Member;", "Lkotlin/reflect/jvm/internal/calls/Caller;", "member", "returnType", "Ljava/lang/reflect/Type;", "instanceClass", "Ljava/lang/Class;", "valueParameterTypes", "", "(Ljava/lang/reflect/Member;Ljava/lang/reflect/Type;Ljava/lang/Class;[Ljava/lang/reflect/Type;)V", "getInstanceClass", "()Ljava/lang/Class;", "getMember", "()Ljava/lang/reflect/Member;", "Ljava/lang/reflect/Member;", "parameterTypes", "", "getParameterTypes", "()Ljava/util/List;", "getReturnType", "()Ljava/lang/reflect/Type;", "checkObjectInstance", "", "obj", "", "AccessorForHiddenBoundConstructor", "AccessorForHiddenConstructor", "BoundConstructor", "Companion", "Constructor", "FieldGetter", "FieldSetter", "Method", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Constructor;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$BoundConstructor;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$AccessorForHiddenConstructor;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$AccessorForHiddenBoundConstructor;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
public abstract class CallerImpl<M extends Member>
  implements Caller<M>
{
  public static final Companion Companion = new Companion(null);
  private final Class<?> instanceClass;
  private final M member;
  private final List<Type> parameterTypes;
  private final Type returnType;
  
  private CallerImpl(M paramM, Type paramType, Class<?> paramClass, Type[] paramArrayOfType)
  {
    this.member = paramM;
    this.returnType = paramType;
    this.instanceClass = paramClass;
    if (paramClass != null)
    {
      paramM = new SpreadBuilder(2);
      paramM.add((Type)paramClass);
      paramM.addSpread(paramArrayOfType);
      paramM = CollectionsKt.listOf((Type[])paramM.toArray(new Type[paramM.size()]));
      if (paramM != null) {}
    }
    else
    {
      paramM = ArraysKt.toList(paramArrayOfType);
    }
    this.parameterTypes = paramM;
  }
  
  public void checkArguments(Object[] paramArrayOfObject)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
    Caller.DefaultImpls.checkArguments(this, paramArrayOfObject);
  }
  
  protected final void checkObjectInstance(Object paramObject)
  {
    if ((paramObject != null) && (this.member.getDeclaringClass().isInstance(paramObject))) {
      return;
    }
    throw ((Throwable)new IllegalArgumentException("An object member requires the object instance passed as the first argument."));
  }
  
  public final Class<?> getInstanceClass()
  {
    return this.instanceClass;
  }
  
  public final M getMember()
  {
    return this.member;
  }
  
  public List<Type> getParameterTypes()
  {
    return this.parameterTypes;
  }
  
  public final Type getReturnType()
  {
    return this.returnType;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\000\n\002\b\003\n\002\020\021\n\002\b\002\030\0002\f\022\b\022\006\022\002\b\0030\0020\0012\0020\003B\033\022\n\020\004\032\006\022\002\b\0030\002\022\b\020\005\032\004\030\0010\006?\006\002\020\007J\033\020\b\032\004\030\0010\0062\n\020\t\032\006\022\002\b\0030\nH\026?\006\002\020\013R\020\020\005\032\004\030\0010\006X?\004?\006\002\n\000?\006\f"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$AccessorForHiddenBoundConstructor;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl;", "Ljava/lang/reflect/Constructor;", "Lkotlin/reflect/jvm/internal/calls/BoundCaller;", "constructor", "boundReceiver", "", "(Ljava/lang/reflect/Constructor;Ljava/lang/Object;)V", "call", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
  public static final class AccessorForHiddenBoundConstructor
    extends CallerImpl<Constructor<?>>
    implements BoundCaller
  {
    private final Object boundReceiver;
    
    public AccessorForHiddenBoundConstructor(Constructor<?> paramConstructor, Object paramObject)
    {
      super((Type)localObject, null, (Type[])paramConstructor, null);
      this.boundReceiver = paramObject;
      return;
      label94:
      throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }
    
    public Object call(Object[] paramArrayOfObject)
    {
      Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
      checkArguments(paramArrayOfObject);
      Constructor localConstructor = (Constructor)getMember();
      SpreadBuilder localSpreadBuilder = new SpreadBuilder(3);
      localSpreadBuilder.add(this.boundReceiver);
      localSpreadBuilder.addSpread(paramArrayOfObject);
      localSpreadBuilder.add(null);
      return localConstructor.newInstance(localSpreadBuilder.toArray(new Object[localSpreadBuilder.size()]));
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\000\n\000\n\002\020\021\n\002\b\002\030\0002\f\022\b\022\006\022\002\b\0030\0020\001B\021\022\n\020\003\032\006\022\002\b\0030\002?\006\002\020\004J\033\020\005\032\004\030\0010\0062\n\020\007\032\006\022\002\b\0030\bH\026?\006\002\020\t?\006\n"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$AccessorForHiddenConstructor;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl;", "Ljava/lang/reflect/Constructor;", "constructor", "(Ljava/lang/reflect/Constructor;)V", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
  public static final class AccessorForHiddenConstructor
    extends CallerImpl<Constructor<?>>
  {
    public AccessorForHiddenConstructor(Constructor<?> paramConstructor)
    {
      super((Type)localObject, null, (Type[])paramConstructor, null);
      return;
      label84:
      throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }
    
    public Object call(Object[] paramArrayOfObject)
    {
      Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
      checkArguments(paramArrayOfObject);
      Constructor localConstructor = (Constructor)getMember();
      SpreadBuilder localSpreadBuilder = new SpreadBuilder(2);
      localSpreadBuilder.addSpread(paramArrayOfObject);
      localSpreadBuilder.add(null);
      return localConstructor.newInstance(localSpreadBuilder.toArray(new Object[localSpreadBuilder.size()]));
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\000\n\002\b\003\n\002\020\021\n\002\b\002\030\0002\0020\0012\f\022\b\022\006\022\002\b\0030\0030\002B\033\022\n\020\004\032\006\022\002\b\0030\003\022\b\020\005\032\004\030\0010\006?\006\002\020\007J\033\020\b\032\004\030\0010\0062\n\020\t\032\006\022\002\b\0030\nH\026?\006\002\020\013R\020\020\005\032\004\030\0010\006X?\004?\006\002\n\000?\006\f"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$BoundConstructor;", "Lkotlin/reflect/jvm/internal/calls/BoundCaller;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl;", "Ljava/lang/reflect/Constructor;", "constructor", "boundReceiver", "", "(Ljava/lang/reflect/Constructor;Ljava/lang/Object;)V", "call", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
  public static final class BoundConstructor
    extends CallerImpl<Constructor<?>>
    implements BoundCaller
  {
    private final Object boundReceiver;
    
    public BoundConstructor(Constructor<?> paramConstructor, Object paramObject)
    {
      super((Type)localObject, null, paramConstructor, null);
      this.boundReceiver = paramObject;
    }
    
    public Object call(Object[] paramArrayOfObject)
    {
      Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
      checkArguments(paramArrayOfObject);
      Constructor localConstructor = (Constructor)getMember();
      SpreadBuilder localSpreadBuilder = new SpreadBuilder(2);
      localSpreadBuilder.add(this.boundReceiver);
      localSpreadBuilder.addSpread(paramArrayOfObject);
      return localConstructor.newInstance(localSpreadBuilder.toArray(new Object[localSpreadBuilder.size()]));
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\021\n\002\b\005\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J(\020\003\032\b\022\004\022\002H\0050\004\"\006\b\001\020\005\030\001*\n\022\006\b\001\022\002H\0050\004H?\b?\006\002\020\006J(\020\007\032\b\022\004\022\002H\0050\004\"\006\b\001\020\005\030\001*\n\022\006\b\001\022\002H\0050\004H?\b?\006\002\020\006J(\020\b\032\b\022\004\022\002H\0050\004\"\006\b\001\020\005\030\001*\n\022\006\b\001\022\002H\0050\004H?\b?\006\002\020\006?\006\t"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$Companion;", "", "()V", "dropFirst", "", "T", "([Ljava/lang/Object;)[Ljava/lang/Object;", "dropFirstAndLast", "dropLast", "kotlin-reflection"}, k=1, mv={1, 1, 15})
  public static final class Companion
  {
    private Companion() {}
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\000\n\000\n\002\020\021\n\002\b\002\030\0002\f\022\b\022\006\022\002\b\0030\0020\001B\021\022\n\020\003\032\006\022\002\b\0030\002?\006\002\020\004J\033\020\005\032\004\030\0010\0062\n\020\007\032\006\022\002\b\0030\bH\026?\006\002\020\t?\006\n"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$Constructor;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl;", "Ljava/lang/reflect/Constructor;", "constructor", "(Ljava/lang/reflect/Constructor;)V", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
  public static final class Constructor
    extends CallerImpl<Constructor<?>>
  {
    public Constructor(Constructor<?> paramConstructor)
    {
      super(localType, localClass1, paramConstructor, null);
    }
    
    public Object call(Object[] paramArrayOfObject)
    {
      Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
      checkArguments(paramArrayOfObject);
      return ((Constructor)getMember()).newInstance(Arrays.copyOf(paramArrayOfObject, paramArrayOfObject.length));
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000<\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\002\n\002\020\000\n\000\n\002\020\021\n\002\b\006\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\000\b6\030\0002\b\022\004\022\0020\0020\001:\005\f\r\016\017\020B\027\b\002\022\006\020\003\032\0020\002\022\006\020\004\032\0020\005?\006\002\020\006J\033\020\007\032\004\030\0010\b2\n\020\t\032\006\022\002\b\0030\nH\026?\006\002\020\013?\001\005\021\022\023\024\025?\006\026"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl;", "Ljava/lang/reflect/Field;", "field", "requiresInstance", "", "(Ljava/lang/reflect/Field;Z)V", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "BoundInstance", "BoundJvmStaticInObject", "Instance", "JvmStaticInObject", "Static", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter$Static;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter$Instance;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter$JvmStaticInObject;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter$BoundInstance;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter$BoundJvmStaticInObject;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
  public static abstract class FieldGetter
    extends CallerImpl<Field>
  {
    private FieldGetter(Field paramField, boolean paramBoolean)
    {
      super(localType, paramField, new Type[0], null);
    }
    
    public Object call(Object[] paramArrayOfObject)
    {
      Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
      checkArguments(paramArrayOfObject);
      Field localField = (Field)getMember();
      if (getInstanceClass() != null) {
        paramArrayOfObject = ArraysKt.first(paramArrayOfObject);
      } else {
        paramArrayOfObject = null;
      }
      return localField.get(paramArrayOfObject);
    }
    
    @Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\000\n\002\b\003\n\002\020\021\n\002\b\002\030\0002\0020\0012\0020\002B\027\022\006\020\003\032\0020\004\022\b\020\005\032\004\030\0010\006?\006\002\020\007J\033\020\b\032\004\030\0010\0062\n\020\t\032\006\022\002\b\0030\nH\026?\006\002\020\013R\020\020\005\032\004\030\0010\006X?\004?\006\002\n\000?\006\f"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter$BoundInstance;", "Lkotlin/reflect/jvm/internal/calls/BoundCaller;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter;", "field", "Ljava/lang/reflect/Field;", "boundReceiver", "", "(Ljava/lang/reflect/Field;Ljava/lang/Object;)V", "call", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
    public static final class BoundInstance
      extends CallerImpl.FieldGetter
      implements BoundCaller
    {
      private final Object boundReceiver;
      
      public BoundInstance(Field paramField, Object paramObject)
      {
        super(false, null);
        this.boundReceiver = paramObject;
      }
      
      public Object call(Object[] paramArrayOfObject)
      {
        Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
        checkArguments(paramArrayOfObject);
        return ((Field)getMember()).get(this.boundReceiver);
      }
    }
    
    @Metadata(bv={1, 0, 3}, d1={"\000\026\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\030\0002\0020\0012\0020\002B\r\022\006\020\003\032\0020\004?\006\002\020\005?\006\006"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter$BoundJvmStaticInObject;", "Lkotlin/reflect/jvm/internal/calls/BoundCaller;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter;", "field", "Ljava/lang/reflect/Field;", "(Ljava/lang/reflect/Field;)V", "kotlin-reflection"}, k=1, mv={1, 1, 15})
    public static final class BoundJvmStaticInObject
      extends CallerImpl.FieldGetter
      implements BoundCaller
    {
      public BoundJvmStaticInObject(Field paramField)
      {
        super(false, null);
      }
    }
    
    @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004?\006\005"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter$Instance;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter;", "field", "Ljava/lang/reflect/Field;", "(Ljava/lang/reflect/Field;)V", "kotlin-reflection"}, k=1, mv={1, 1, 15})
    public static final class Instance
      extends CallerImpl.FieldGetter
    {
      public Instance(Field paramField)
      {
        super(true, null);
      }
    }
    
    @Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\021\n\002\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\031\020\005\032\0020\0062\n\020\007\032\006\022\002\b\0030\bH\026?\006\002\020\t?\006\n"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter$JvmStaticInObject;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter;", "field", "Ljava/lang/reflect/Field;", "(Ljava/lang/reflect/Field;)V", "checkArguments", "", "args", "", "([Ljava/lang/Object;)V", "kotlin-reflection"}, k=1, mv={1, 1, 15})
    public static final class JvmStaticInObject
      extends CallerImpl.FieldGetter
    {
      public JvmStaticInObject(Field paramField)
      {
        super(true, null);
      }
      
      public void checkArguments(Object[] paramArrayOfObject)
      {
        Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
        super.checkArguments(paramArrayOfObject);
        checkObjectInstance(ArraysKt.firstOrNull(paramArrayOfObject));
      }
    }
    
    @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004?\006\005"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter$Static;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldGetter;", "field", "Ljava/lang/reflect/Field;", "(Ljava/lang/reflect/Field;)V", "kotlin-reflection"}, k=1, mv={1, 1, 15})
    public static final class Static
      extends CallerImpl.FieldGetter
    {
      public Static(Field paramField)
      {
        super(false, null);
      }
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000D\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\003\n\002\020\000\n\000\n\002\020\021\n\002\b\002\n\002\020\002\n\002\b\006\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\000\b6\030\0002\b\022\004\022\0020\0020\001:\005\020\021\022\023\024B\037\b\002\022\006\020\003\032\0020\002\022\006\020\004\032\0020\005\022\006\020\006\032\0020\005?\006\002\020\007J\033\020\b\032\004\030\0010\t2\n\020\n\032\006\022\002\b\0030\013H\026?\006\002\020\fJ\031\020\r\032\0020\0162\n\020\n\032\006\022\002\b\0030\013H\026?\006\002\020\017R\016\020\004\032\0020\005X?\004?\006\002\n\000?\001\005\025\026\027\030\031?\006\032"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl;", "Ljava/lang/reflect/Field;", "field", "notNull", "", "requiresInstance", "(Ljava/lang/reflect/Field;ZZ)V", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "checkArguments", "", "([Ljava/lang/Object;)V", "BoundInstance", "BoundJvmStaticInObject", "Instance", "JvmStaticInObject", "Static", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter$Static;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter$Instance;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter$JvmStaticInObject;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter$BoundInstance;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter$BoundJvmStaticInObject;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
  public static abstract class FieldSetter
    extends CallerImpl<Field>
  {
    private final boolean notNull;
    
    private FieldSetter(Field paramField, boolean paramBoolean1, boolean paramBoolean2)
    {
      super(localType, localClass, new Type[] { paramField }, null);
      this.notNull = paramBoolean1;
    }
    
    public Object call(Object[] paramArrayOfObject)
    {
      Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
      checkArguments(paramArrayOfObject);
      Field localField = (Field)getMember();
      Object localObject;
      if (getInstanceClass() != null) {
        localObject = ArraysKt.first(paramArrayOfObject);
      } else {
        localObject = null;
      }
      localField.set(localObject, ArraysKt.last(paramArrayOfObject));
      return Unit.INSTANCE;
    }
    
    public void checkArguments(Object[] paramArrayOfObject)
    {
      Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
      super.checkArguments(paramArrayOfObject);
      if ((this.notNull) && (ArraysKt.last(paramArrayOfObject) == null)) {
        throw ((Throwable)new IllegalArgumentException("null is not allowed as a value for this property."));
      }
    }
    
    @Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\000\n\002\020\000\n\002\b\003\n\002\020\021\n\002\b\002\030\0002\0020\0012\0020\002B\037\022\006\020\003\032\0020\004\022\006\020\005\032\0020\006\022\b\020\007\032\004\030\0010\b?\006\002\020\tJ\033\020\n\032\004\030\0010\b2\n\020\013\032\006\022\002\b\0030\fH\026?\006\002\020\rR\020\020\007\032\004\030\0010\bX?\004?\006\002\n\000?\006\016"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter$BoundInstance;", "Lkotlin/reflect/jvm/internal/calls/BoundCaller;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter;", "field", "Ljava/lang/reflect/Field;", "notNull", "", "boundReceiver", "", "(Ljava/lang/reflect/Field;ZLjava/lang/Object;)V", "call", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
    public static final class BoundInstance
      extends CallerImpl.FieldSetter
      implements BoundCaller
    {
      private final Object boundReceiver;
      
      public BoundInstance(Field paramField, boolean paramBoolean, Object paramObject)
      {
        super(paramBoolean, false, null);
        this.boundReceiver = paramObject;
      }
      
      public Object call(Object[] paramArrayOfObject)
      {
        Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
        checkArguments(paramArrayOfObject);
        ((Field)getMember()).set(this.boundReceiver, ArraysKt.first(paramArrayOfObject));
        return Unit.INSTANCE;
      }
    }
    
    @Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\020\000\n\000\n\002\020\021\n\002\b\002\030\0002\0020\0012\0020\002B\025\022\006\020\003\032\0020\004\022\006\020\005\032\0020\006?\006\002\020\007J\033\020\b\032\004\030\0010\t2\n\020\n\032\006\022\002\b\0030\013H\026?\006\002\020\f?\006\r"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter$BoundJvmStaticInObject;", "Lkotlin/reflect/jvm/internal/calls/BoundCaller;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter;", "field", "Ljava/lang/reflect/Field;", "notNull", "", "(Ljava/lang/reflect/Field;Z)V", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
    public static final class BoundJvmStaticInObject
      extends CallerImpl.FieldSetter
      implements BoundCaller
    {
      public BoundJvmStaticInObject(Field paramField, boolean paramBoolean)
      {
        super(paramBoolean, false, null);
      }
      
      public Object call(Object[] paramArrayOfObject)
      {
        Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
        checkArguments(paramArrayOfObject);
        ((Field)getMember()).set(null, ArraysKt.last(paramArrayOfObject));
        return Unit.INSTANCE;
      }
    }
    
    @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\002\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005?\006\002\020\006?\006\007"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter$Instance;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter;", "field", "Ljava/lang/reflect/Field;", "notNull", "", "(Ljava/lang/reflect/Field;Z)V", "kotlin-reflection"}, k=1, mv={1, 1, 15})
    public static final class Instance
      extends CallerImpl.FieldSetter
    {
      public Instance(Field paramField, boolean paramBoolean)
      {
        super(paramBoolean, true, null);
      }
    }
    
    @Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\020\002\n\000\n\002\020\021\n\002\b\002\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005?\006\002\020\006J\031\020\007\032\0020\b2\n\020\t\032\006\022\002\b\0030\nH\026?\006\002\020\013?\006\f"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter$JvmStaticInObject;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter;", "field", "Ljava/lang/reflect/Field;", "notNull", "", "(Ljava/lang/reflect/Field;Z)V", "checkArguments", "", "args", "", "([Ljava/lang/Object;)V", "kotlin-reflection"}, k=1, mv={1, 1, 15})
    public static final class JvmStaticInObject
      extends CallerImpl.FieldSetter
    {
      public JvmStaticInObject(Field paramField, boolean paramBoolean)
      {
        super(paramBoolean, true, null);
      }
      
      public void checkArguments(Object[] paramArrayOfObject)
      {
        Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
        super.checkArguments(paramArrayOfObject);
        checkObjectInstance(ArraysKt.firstOrNull(paramArrayOfObject));
      }
    }
    
    @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\002\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005?\006\002\020\006?\006\007"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter$Static;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$FieldSetter;", "field", "Ljava/lang/reflect/Field;", "notNull", "", "(Ljava/lang/reflect/Field;Z)V", "kotlin-reflection"}, k=1, mv={1, 1, 15})
    public static final class Static
      extends CallerImpl.FieldSetter
    {
      public Static(Field paramField, boolean paramBoolean)
      {
        super(paramBoolean, false, null);
      }
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000D\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\013\n\000\n\002\020\021\n\002\030\002\n\002\b\003\n\002\020\000\n\002\b\t\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\000\b6\030\0002\b\022\004\022\0020\0020\001:\006\020\021\022\023\024\025B)\b\002\022\006\020\003\032\0020\002\022\b\b\002\020\004\032\0020\005\022\016\b\002\020\006\032\b\022\004\022\0020\b0\007?\006\002\020\tJ%\020\013\032\004\030\0010\f2\b\020\r\032\004\030\0010\f2\n\020\016\032\006\022\002\b\0030\007H\004?\006\002\020\017R\016\020\n\032\0020\005X?\004?\006\002\n\000?\001\006\026\027\030\031\032\033?\006\034"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl;", "Ljava/lang/reflect/Method;", "method", "requiresInstance", "", "parameterTypes", "", "Ljava/lang/reflect/Type;", "(Ljava/lang/reflect/Method;Z[Ljava/lang/reflect/Type;)V", "isVoidMethod", "callMethod", "", "instance", "args", "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", "BoundInstance", "BoundJvmStaticInObject", "BoundStatic", "Instance", "JvmStaticInObject", "Static", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$Static;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$Instance;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$JvmStaticInObject;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$BoundStatic;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$BoundInstance;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$BoundJvmStaticInObject;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
  public static abstract class Method
    extends CallerImpl<Method>
  {
    private final boolean isVoidMethod = Intrinsics.areEqual(getReturnType(), Void.TYPE);
    
    private Method(Method paramMethod, boolean paramBoolean, Type[] paramArrayOfType)
    {
      super(localType, paramMethod, paramArrayOfType, null);
    }
    
    protected final Object callMethod(Object paramObject, Object[] paramArrayOfObject)
    {
      Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
      paramObject = ((Method)getMember()).invoke(paramObject, Arrays.copyOf(paramArrayOfObject, paramArrayOfObject.length));
      if (this.isVoidMethod) {
        paramObject = Unit.INSTANCE;
      }
      return paramObject;
    }
    
    @Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\000\n\002\b\003\n\002\020\021\n\002\b\002\030\0002\0020\0012\0020\002B\027\022\006\020\003\032\0020\004\022\b\020\005\032\004\030\0010\006?\006\002\020\007J\033\020\b\032\004\030\0010\0062\n\020\t\032\006\022\002\b\0030\nH\026?\006\002\020\013R\020\020\005\032\004\030\0010\006X?\004?\006\002\n\000?\006\f"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$BoundInstance;", "Lkotlin/reflect/jvm/internal/calls/BoundCaller;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method;", "method", "Ljava/lang/reflect/Method;", "boundReceiver", "", "(Ljava/lang/reflect/Method;Ljava/lang/Object;)V", "call", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
    public static final class BoundInstance
      extends CallerImpl.Method
      implements BoundCaller
    {
      private final Object boundReceiver;
      
      public BoundInstance(Method paramMethod, Object paramObject)
      {
        super(false, null, 4, null);
        this.boundReceiver = paramObject;
      }
      
      public Object call(Object[] paramArrayOfObject)
      {
        Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
        checkArguments(paramArrayOfObject);
        return callMethod(this.boundReceiver, paramArrayOfObject);
      }
    }
    
    @Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\000\n\000\n\002\020\021\n\002\b\002\030\0002\0020\0012\0020\002B\r\022\006\020\003\032\0020\004?\006\002\020\005J\033\020\006\032\004\030\0010\0072\n\020\b\032\006\022\002\b\0030\tH\026?\006\002\020\n?\006\013"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$BoundJvmStaticInObject;", "Lkotlin/reflect/jvm/internal/calls/BoundCaller;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method;", "method", "Ljava/lang/reflect/Method;", "(Ljava/lang/reflect/Method;)V", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
    public static final class BoundJvmStaticInObject
      extends CallerImpl.Method
      implements BoundCaller
    {
      public BoundJvmStaticInObject(Method paramMethod)
      {
        super(false, null, 4, null);
      }
      
      public Object call(Object[] paramArrayOfObject)
      {
        Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
        checkArguments(paramArrayOfObject);
        return callMethod(null, paramArrayOfObject);
      }
    }
    
    @Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\000\n\002\b\003\n\002\020\021\n\002\b\002\030\0002\0020\0012\0020\002B\027\022\006\020\003\032\0020\004\022\b\020\005\032\004\030\0010\006?\006\002\020\007J\033\020\b\032\004\030\0010\0062\n\020\t\032\006\022\002\b\0030\nH\026?\006\002\020\013R\020\020\005\032\004\030\0010\006X?\004?\006\002\n\000?\006\f"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$BoundStatic;", "Lkotlin/reflect/jvm/internal/calls/BoundCaller;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method;", "method", "Ljava/lang/reflect/Method;", "boundReceiver", "", "(Ljava/lang/reflect/Method;Ljava/lang/Object;)V", "call", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
    public static final class BoundStatic
      extends CallerImpl.Method
      implements BoundCaller
    {
      private final Object boundReceiver;
      
      public BoundStatic(Method paramMethod, Object paramObject)
      {
        super(false, (Type[])localObject, null);
        this.boundReceiver = paramObject;
        return;
        label64:
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      }
      
      public Object call(Object[] paramArrayOfObject)
      {
        Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
        checkArguments(paramArrayOfObject);
        SpreadBuilder localSpreadBuilder = new SpreadBuilder(2);
        localSpreadBuilder.add(this.boundReceiver);
        localSpreadBuilder.addSpread(paramArrayOfObject);
        return callMethod(null, localSpreadBuilder.toArray(new Object[localSpreadBuilder.size()]));
      }
    }
    
    @Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\000\n\000\n\002\020\021\n\002\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\033\020\005\032\004\030\0010\0062\n\020\007\032\006\022\002\b\0030\bH\026?\006\002\020\t?\006\n"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$Instance;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method;", "method", "Ljava/lang/reflect/Method;", "(Ljava/lang/reflect/Method;)V", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
    public static final class Instance
      extends CallerImpl.Method
    {
      public Instance(Method paramMethod)
      {
        super(false, null, 6, null);
      }
      
      public Object call(Object[] paramArrayOfObject)
      {
        Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
        checkArguments(paramArrayOfObject);
        Object localObject = paramArrayOfObject[0];
        CallerImpl.Companion localCompanion = CallerImpl.Companion;
        if (paramArrayOfObject.length <= 1)
        {
          paramArrayOfObject = new Object[0];
        }
        else
        {
          paramArrayOfObject = ArraysKt.copyOfRange(paramArrayOfObject, 1, paramArrayOfObject.length);
          if (paramArrayOfObject == null) {
            break label52;
          }
        }
        return callMethod(localObject, paramArrayOfObject);
        label52:
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      }
    }
    
    @Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\000\n\000\n\002\020\021\n\002\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\033\020\005\032\004\030\0010\0062\n\020\007\032\006\022\002\b\0030\bH\026?\006\002\020\t?\006\n"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$JvmStaticInObject;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method;", "method", "Ljava/lang/reflect/Method;", "(Ljava/lang/reflect/Method;)V", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
    public static final class JvmStaticInObject
      extends CallerImpl.Method
    {
      public JvmStaticInObject(Method paramMethod)
      {
        super(true, null, 4, null);
      }
      
      public Object call(Object[] paramArrayOfObject)
      {
        Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
        checkArguments(paramArrayOfObject);
        checkObjectInstance(ArraysKt.firstOrNull(paramArrayOfObject));
        CallerImpl.Companion localCompanion = CallerImpl.Companion;
        if (paramArrayOfObject.length <= 1)
        {
          paramArrayOfObject = new Object[0];
        }
        else
        {
          paramArrayOfObject = ArraysKt.copyOfRange(paramArrayOfObject, 1, paramArrayOfObject.length);
          if (paramArrayOfObject == null) {
            break label56;
          }
        }
        return callMethod(null, paramArrayOfObject);
        label56:
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      }
    }
    
    @Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\000\n\000\n\002\020\021\n\002\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\033\020\005\032\004\030\0010\0062\n\020\007\032\006\022\002\b\0030\bH\026?\006\002\020\t?\006\n"}, d2={"Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method$Static;", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method;", "method", "Ljava/lang/reflect/Method;", "(Ljava/lang/reflect/Method;)V", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
    public static final class Static
      extends CallerImpl.Method
    {
      public Static(Method paramMethod)
      {
        super(false, null, 6, null);
      }
      
      public Object call(Object[] paramArrayOfObject)
      {
        Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
        checkArguments(paramArrayOfObject);
        return callMethod(null, paramArrayOfObject);
      }
    }
  }
}

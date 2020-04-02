package kotlin.reflect.jvm;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KCallable;
import kotlin.reflect.KCallable<*>;
import kotlin.reflect.KFunction;
import kotlin.reflect.KMutableProperty;
import kotlin.reflect.KMutableProperty.Setter;
import kotlin.reflect.KProperty;
import kotlin.reflect.KProperty.Getter;
import kotlin.reflect.jvm.internal.KCallableImpl;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.calls.Caller;

@Metadata(bv={1, 0, 3}, d1={"\000\020\n\000\n\002\020\013\n\000\n\002\030\002\n\002\b\004\",\020\002\032\0020\001*\006\022\002\b\0030\0032\006\020\000\032\0020\0018F@FX?\016?\006\f\032\004\b\002\020\004\"\004\b\005\020\006?\006\007"}, d2={"value", "", "isAccessible", "Lkotlin/reflect/KCallable;", "(Lkotlin/reflect/KCallable;)Z", "setAccessible", "(Lkotlin/reflect/KCallable;Z)V", "kotlin-reflection"}, k=2, mv={1, 1, 15})
public final class KCallablesJvm
{
  public static final boolean isAccessible(KCallable<?> paramKCallable)
  {
    Intrinsics.checkParameterIsNotNull(paramKCallable, "$this$isAccessible");
    boolean bool1 = paramKCallable instanceof KMutableProperty;
    boolean bool2 = false;
    Object localObject1;
    boolean bool3;
    if (bool1)
    {
      localObject1 = (KProperty)paramKCallable;
      localObject2 = ReflectJvmMapping.getJavaField((KProperty)localObject1);
      if (localObject2 != null) {
        bool3 = ((Field)localObject2).isAccessible();
      } else {
        bool3 = true;
      }
      bool1 = bool2;
      if (!bool3) {
        break label502;
      }
      localObject2 = ReflectJvmMapping.getJavaGetter((KProperty)localObject1);
      if (localObject2 != null) {
        bool3 = ((Method)localObject2).isAccessible();
      } else {
        bool3 = true;
      }
      bool1 = bool2;
      if (!bool3) {
        break label502;
      }
      paramKCallable = ReflectJvmMapping.getJavaSetter((KMutableProperty)paramKCallable);
      if (paramKCallable != null) {
        bool3 = paramKCallable.isAccessible();
      } else {
        bool3 = true;
      }
      bool1 = bool2;
      if (!bool3) {
        break label502;
      }
    }
    else
    {
      if (!(paramKCallable instanceof KProperty)) {
        break label194;
      }
      localObject2 = (KProperty)paramKCallable;
      paramKCallable = ReflectJvmMapping.getJavaField((KProperty)localObject2);
      if (paramKCallable != null) {
        bool3 = paramKCallable.isAccessible();
      } else {
        bool3 = true;
      }
      bool1 = bool2;
      if (!bool3) {
        break label502;
      }
      paramKCallable = ReflectJvmMapping.getJavaGetter((KProperty)localObject2);
      if (paramKCallable != null) {
        bool3 = paramKCallable.isAccessible();
      } else {
        bool3 = true;
      }
      bool1 = bool2;
      if (!bool3) {
        break label502;
      }
    }
    label194:
    label430:
    do
    {
      for (;;)
      {
        bool1 = true;
        break label502;
        if ((paramKCallable instanceof KProperty.Getter))
        {
          localObject2 = ReflectJvmMapping.getJavaField(((KProperty.Getter)paramKCallable).getProperty());
          if (localObject2 != null) {
            bool3 = ((Field)localObject2).isAccessible();
          } else {
            bool3 = true;
          }
          bool1 = bool2;
          if (!bool3) {
            break label502;
          }
          paramKCallable = ReflectJvmMapping.getJavaMethod((KFunction)paramKCallable);
          if (paramKCallable != null) {
            bool3 = paramKCallable.isAccessible();
          } else {
            bool3 = true;
          }
          bool1 = bool2;
          if (!bool3) {
            break label502;
          }
        }
        else
        {
          if (!(paramKCallable instanceof KMutableProperty.Setter)) {
            break;
          }
          localObject2 = ReflectJvmMapping.getJavaField(((KMutableProperty.Setter)paramKCallable).getProperty());
          if (localObject2 != null) {
            bool3 = ((Field)localObject2).isAccessible();
          } else {
            bool3 = true;
          }
          bool1 = bool2;
          if (!bool3) {
            break label502;
          }
          paramKCallable = ReflectJvmMapping.getJavaMethod((KFunction)paramKCallable);
          if (paramKCallable != null) {
            bool3 = paramKCallable.isAccessible();
          } else {
            bool3 = true;
          }
          bool1 = bool2;
          if (!bool3) {
            break label502;
          }
        }
      }
      if (!(paramKCallable instanceof KFunction)) {
        break label504;
      }
      localObject1 = (KFunction)paramKCallable;
      localObject2 = ReflectJvmMapping.getJavaMethod((KFunction)localObject1);
      if (localObject2 != null) {
        bool3 = ((Method)localObject2).isAccessible();
      } else {
        bool3 = true;
      }
      bool1 = bool2;
      if (!bool3) {
        break;
      }
      paramKCallable = UtilKt.asKCallableImpl(paramKCallable);
      localObject2 = null;
      if (paramKCallable != null)
      {
        paramKCallable = paramKCallable.getDefaultCaller();
        if (paramKCallable != null)
        {
          paramKCallable = paramKCallable.getMember();
          break label430;
        }
      }
      paramKCallable = null;
      if (!(paramKCallable instanceof AccessibleObject)) {
        paramKCallable = (KCallable<?>)localObject2;
      }
      paramKCallable = (AccessibleObject)paramKCallable;
      if (paramKCallable != null) {
        bool3 = paramKCallable.isAccessible();
      } else {
        bool3 = true;
      }
      bool1 = bool2;
      if (!bool3) {
        break;
      }
      paramKCallable = ReflectJvmMapping.getJavaConstructor((KFunction)localObject1);
      if (paramKCallable != null) {
        bool3 = paramKCallable.isAccessible();
      } else {
        bool3 = true;
      }
      bool1 = bool2;
    } while (bool3);
    label502:
    return bool1;
    label504:
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("Unknown callable: ");
    ((StringBuilder)localObject2).append(paramKCallable);
    ((StringBuilder)localObject2).append(" (");
    ((StringBuilder)localObject2).append(paramKCallable.getClass());
    ((StringBuilder)localObject2).append(')');
    throw ((Throwable)new UnsupportedOperationException(((StringBuilder)localObject2).toString()));
  }
  
  public static final void setAccessible(KCallable<?> paramKCallable, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramKCallable, "$this$isAccessible");
    Object localObject2;
    if ((paramKCallable instanceof KMutableProperty))
    {
      localObject1 = (KProperty)paramKCallable;
      localObject2 = ReflectJvmMapping.getJavaField((KProperty)localObject1);
      if (localObject2 != null) {
        ((Field)localObject2).setAccessible(paramBoolean);
      }
      localObject1 = ReflectJvmMapping.getJavaGetter((KProperty)localObject1);
      if (localObject1 != null) {
        ((Method)localObject1).setAccessible(paramBoolean);
      }
      paramKCallable = ReflectJvmMapping.getJavaSetter((KMutableProperty)paramKCallable);
      if (paramKCallable != null) {
        paramKCallable.setAccessible(paramBoolean);
      }
    }
    else if ((paramKCallable instanceof KProperty))
    {
      localObject1 = (KProperty)paramKCallable;
      paramKCallable = ReflectJvmMapping.getJavaField((KProperty)localObject1);
      if (paramKCallable != null) {
        paramKCallable.setAccessible(paramBoolean);
      }
      paramKCallable = ReflectJvmMapping.getJavaGetter((KProperty)localObject1);
      if (paramKCallable != null) {
        paramKCallable.setAccessible(paramBoolean);
      }
    }
    else if ((paramKCallable instanceof KProperty.Getter))
    {
      localObject1 = ReflectJvmMapping.getJavaField(((KProperty.Getter)paramKCallable).getProperty());
      if (localObject1 != null) {
        ((Field)localObject1).setAccessible(paramBoolean);
      }
      paramKCallable = ReflectJvmMapping.getJavaMethod((KFunction)paramKCallable);
      if (paramKCallable != null) {
        paramKCallable.setAccessible(paramBoolean);
      }
    }
    else if ((paramKCallable instanceof KMutableProperty.Setter))
    {
      localObject1 = ReflectJvmMapping.getJavaField(((KMutableProperty.Setter)paramKCallable).getProperty());
      if (localObject1 != null) {
        ((Field)localObject1).setAccessible(paramBoolean);
      }
      paramKCallable = ReflectJvmMapping.getJavaMethod((KFunction)paramKCallable);
      if (paramKCallable != null) {
        paramKCallable.setAccessible(paramBoolean);
      }
    }
    else
    {
      if (!(paramKCallable instanceof KFunction)) {
        break label306;
      }
      localObject2 = (KFunction)paramKCallable;
      localObject1 = ReflectJvmMapping.getJavaMethod((KFunction)localObject2);
      if (localObject1 != null) {
        ((Method)localObject1).setAccessible(paramBoolean);
      }
      paramKCallable = UtilKt.asKCallableImpl(paramKCallable);
      localObject1 = null;
      if (paramKCallable != null)
      {
        paramKCallable = paramKCallable.getDefaultCaller();
        if (paramKCallable != null)
        {
          paramKCallable = paramKCallable.getMember();
          break label265;
        }
      }
      paramKCallable = null;
      label265:
      if (!(paramKCallable instanceof AccessibleObject)) {
        paramKCallable = (KCallable<?>)localObject1;
      }
      paramKCallable = (AccessibleObject)paramKCallable;
      if (paramKCallable != null) {
        paramKCallable.setAccessible(true);
      }
      paramKCallable = ReflectJvmMapping.getJavaConstructor((KFunction)localObject2);
      if (paramKCallable != null) {
        paramKCallable.setAccessible(paramBoolean);
      }
    }
    return;
    label306:
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("Unknown callable: ");
    ((StringBuilder)localObject1).append(paramKCallable);
    ((StringBuilder)localObject1).append(" (");
    ((StringBuilder)localObject1).append(paramKCallable.getClass());
    ((StringBuilder)localObject1).append(')');
    throw ((Throwable)new UnsupportedOperationException(((StringBuilder)localObject1).toString()));
  }
}

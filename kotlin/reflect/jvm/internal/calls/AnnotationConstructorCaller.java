package kotlin.reflect.jvm.internal.calls;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;

@Metadata(bv={1, 0, 3}, d1={"\000J\n\002\030\002\n\002\030\002\n\002\020\001\n\000\n\002\030\002\n\000\n\002\020 \n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\000\n\002\b\005\n\002\030\002\n\002\b\007\n\002\020\021\n\002\b\004\b\000\030\0002\n\022\006\022\004\030\0010\0020\001:\002 !B?\022\n\020\003\032\006\022\002\b\0030\004\022\f\020\005\032\b\022\004\022\0020\0070\006\022\006\020\b\032\0020\t\022\006\020\n\032\0020\013\022\016\b\002\020\f\032\b\022\004\022\0020\r0\006?\006\002\020\016J\033\020\034\032\004\030\0010\0202\n\020\035\032\006\022\002\b\0030\036H\026?\006\002\020\037R\016\020\b\032\0020\tX?\004?\006\002\n\000R\026\020\017\032\n\022\006\022\004\030\0010\0200\006X?\004?\006\002\n\000R\030\020\021\032\f\022\b\022\006\022\002\b\0030\0040\006X?\004?\006\002\n\000R\022\020\003\032\006\022\002\b\0030\004X?\004?\006\002\n\000R\026\020\022\032\004\030\0010\0028VX?\004?\006\006\032\004\b\023\020\024R\024\020\f\032\b\022\004\022\0020\r0\006X?\004?\006\002\n\000R\024\020\005\032\b\022\004\022\0020\0070\006X?\004?\006\002\n\000R\032\020\025\032\b\022\004\022\0020\0260\006X?\004?\006\b\n\000\032\004\b\027\020\030R\024\020\031\032\0020\0268VX?\004?\006\006\032\004\b\032\020\033?\006\""}, d2={"Lkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller;", "Lkotlin/reflect/jvm/internal/calls/Caller;", "", "jClass", "Ljava/lang/Class;", "parameterNames", "", "", "callMode", "Lkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller$CallMode;", "origin", "Lkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller$Origin;", "methods", "Ljava/lang/reflect/Method;", "(Ljava/lang/Class;Ljava/util/List;Lkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller$CallMode;Lkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller$Origin;Ljava/util/List;)V", "defaultValues", "", "erasedParameterTypes", "member", "getMember", "()Ljava/lang/Void;", "parameterTypes", "Ljava/lang/reflect/Type;", "getParameterTypes", "()Ljava/util/List;", "returnType", "getReturnType", "()Ljava/lang/reflect/Type;", "call", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "CallMode", "Origin", "kotlin-reflection"}, k=1, mv={1, 1, 15})
public final class AnnotationConstructorCaller
  implements Caller
{
  private final CallMode callMode;
  private final List<Object> defaultValues;
  private final List<Class<?>> erasedParameterTypes;
  private final Class<?> jClass;
  private final List<Method> methods;
  private final List<String> parameterNames;
  private final List<Type> parameterTypes;
  
  public AnnotationConstructorCaller(Class<?> paramClass, List<String> paramList, CallMode paramCallMode, Origin paramOrigin, List<Method> paramList1)
  {
    this.jClass = paramClass;
    this.parameterNames = paramList;
    this.callMode = paramCallMode;
    this.methods = paramList1;
    paramList = (Iterable)paramList1;
    paramClass = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault(paramList, 10));
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      paramClass.add(((Method)paramList.next()).getGenericReturnType());
    }
    this.parameterTypes = ((List)paramClass);
    paramClass = (Iterable)this.methods;
    paramCallMode = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault(paramClass, 10));
    paramList1 = paramClass.iterator();
    while (paramList1.hasNext())
    {
      paramClass = ((Method)paramList1.next()).getReturnType();
      Intrinsics.checkExpressionValueIsNotNull(paramClass, "it");
      paramList = ReflectClassUtilKt.getWrapperByPrimitive(paramClass);
      if (paramList != null) {
        paramClass = paramList;
      }
      paramCallMode.add(paramClass);
    }
    this.erasedParameterTypes = ((List)paramCallMode);
    paramList = (Iterable)this.methods;
    paramClass = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault(paramList, 10));
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      paramClass.add(((Method)paramList.next()).getDefaultValue());
    }
    this.defaultValues = ((List)paramClass);
    if ((this.callMode == CallMode.POSITIONAL_CALL) && (paramOrigin == Origin.JAVA) && ((((Collection)CollectionsKt.minus((Iterable)this.parameterNames, "value")).isEmpty() ^ true))) {
      throw ((Throwable)new UnsupportedOperationException("Positional call of a Java annotation constructor is allowed only if there are no parameters or one parameter named \"value\". This restriction exists because Java annotations (in contrast to Kotlin)do not impose any order on their arguments. Use KCallable#callBy instead."));
    }
  }
  
  public Object call(Object[] paramArrayOfObject)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
    checkArguments(paramArrayOfObject);
    Collection localCollection = (Collection)new ArrayList(paramArrayOfObject.length);
    int i = paramArrayOfObject.length;
    int j = 0;
    int k = 0;
    while (j < i)
    {
      Object localObject = paramArrayOfObject[j];
      if ((localObject == null) && (this.callMode == CallMode.CALL_BY_NAME)) {
        localObject = this.defaultValues.get(k);
      } else {
        localObject = AnnotationConstructorCallerKt.access$transformKotlinToJvm(localObject, (Class)this.erasedParameterTypes.get(k));
      }
      if (localObject != null)
      {
        localCollection.add(localObject);
        j++;
        k++;
      }
      else
      {
        AnnotationConstructorCallerKt.access$throwIllegalArgumentType(k, (String)this.parameterNames.get(k), (Class)this.erasedParameterTypes.get(k));
        throw null;
      }
    }
    paramArrayOfObject = (List)localCollection;
    return AnnotationConstructorCallerKt.createAnnotationInstance(this.jClass, MapsKt.toMap((Iterable)CollectionsKt.zip((Iterable)this.parameterNames, (Iterable)paramArrayOfObject)), this.methods);
  }
  
  public void checkArguments(Object[] paramArrayOfObject)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
    Caller.DefaultImpls.checkArguments(this, paramArrayOfObject);
  }
  
  public Void getMember()
  {
    return null;
  }
  
  public List<Type> getParameterTypes()
  {
    return this.parameterTypes;
  }
  
  public Type getReturnType()
  {
    return (Type)this.jClass;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\f\n\002\030\002\n\002\020\020\n\002\b\004\b?\001\030\0002\b\022\004\022\0020\0000\001B\007\b\002?\006\002\020\002j\002\b\003j\002\b\004?\006\005"}, d2={"Lkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller$CallMode;", "", "(Ljava/lang/String;I)V", "CALL_BY_NAME", "POSITIONAL_CALL", "kotlin-reflection"}, k=1, mv={1, 1, 15})
  public static enum CallMode
  {
    static
    {
      CallMode localCallMode1 = new CallMode("CALL_BY_NAME", 0);
      CALL_BY_NAME = localCallMode1;
      CallMode localCallMode2 = new CallMode("POSITIONAL_CALL", 1);
      POSITIONAL_CALL = localCallMode2;
      $VALUES = new CallMode[] { localCallMode1, localCallMode2 };
    }
    
    private CallMode() {}
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\f\n\002\030\002\n\002\020\020\n\002\b\004\b?\001\030\0002\b\022\004\022\0020\0000\001B\007\b\002?\006\002\020\002j\002\b\003j\002\b\004?\006\005"}, d2={"Lkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller$Origin;", "", "(Ljava/lang/String;I)V", "JAVA", "KOTLIN", "kotlin-reflection"}, k=1, mv={1, 1, 15})
  public static enum Origin
  {
    static
    {
      Origin localOrigin1 = new Origin("JAVA", 0);
      JAVA = localOrigin1;
      Origin localOrigin2 = new Origin("KOTLIN", 1);
      KOTLIN = localOrigin2;
      $VALUES = new Origin[] { localOrigin1, localOrigin2 };
    }
    
    private Origin() {}
  }
}

package kotlin.reflect.full;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.reflect.KCallable;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.KParameter.Kind;
import kotlin.reflect.KType;
import kotlin.reflect.jvm.internal.KCallableImpl;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.UtilKt;

@Metadata(bv={1, 0, 3}, d1={"\0000\n\000\n\002\030\002\n\002\030\002\n\002\b\b\n\002\020 \n\002\b\006\n\002\020\021\n\002\020\000\n\002\b\002\n\002\020$\n\002\b\003\n\002\020\016\n\000\0329\020\017\032\002H\020\"\004\b\000\020\020*\b\022\004\022\002H\0200\0022\026\020\021\032\f\022\b\b\001\022\004\030\0010\0230\022\"\004\030\0010\023H?@?\001\000?\006\002\020\024\0327\020\025\032\002H\020\"\004\b\000\020\020*\b\022\004\022\002H\0200\0022\024\020\021\032\020\022\004\022\0020\001\022\006\022\004\030\0010\0230\026H?@?\001\000?\006\002\020\027\032\032\020\030\032\004\030\0010\001*\006\022\002\b\0030\0022\006\020\031\032\0020\032H\007\"$\020\000\032\004\030\0010\001*\006\022\002\b\0030\0028FX?\004?\006\f\022\004\b\003\020\004\032\004\b\005\020\006\"$\020\007\032\004\030\0010\001*\006\022\002\b\0030\0028FX?\004?\006\f\022\004\b\b\020\004\032\004\b\t\020\006\"(\020\n\032\b\022\004\022\0020\0010\013*\006\022\002\b\0030\0028FX?\004?\006\f\022\004\b\f\020\004\032\004\b\r\020\016?\002\004\n\002\b\031?\006\033"}, d2={"extensionReceiverParameter", "Lkotlin/reflect/KParameter;", "Lkotlin/reflect/KCallable;", "extensionReceiverParameter$annotations", "(Lkotlin/reflect/KCallable;)V", "getExtensionReceiverParameter", "(Lkotlin/reflect/KCallable;)Lkotlin/reflect/KParameter;", "instanceParameter", "instanceParameter$annotations", "getInstanceParameter", "valueParameters", "", "valueParameters$annotations", "getValueParameters", "(Lkotlin/reflect/KCallable;)Ljava/util/List;", "callSuspend", "R", "args", "", "", "(Lkotlin/reflect/KCallable;[Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "callSuspendBy", "", "(Lkotlin/reflect/KCallable;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "findParameterByName", "name", "", "kotlin-reflection"}, k=2, mv={1, 1, 15})
public final class KCallables
{
  public static final <R> Object callSuspend(KCallable<? extends R> paramKCallable, Object[] paramArrayOfObject, Continuation<? super R> paramContinuation)
  {
    if ((paramContinuation instanceof callSuspend.1))
    {
      localObject1 = (callSuspend.1)paramContinuation;
      if ((((callSuspend.1)localObject1).label & 0x80000000) != 0)
      {
        ((callSuspend.1)localObject1).label += Integer.MIN_VALUE;
        paramContinuation = (Continuation<? super R>)localObject1;
        break label47;
      }
    }
    paramContinuation = new ContinuationImpl(paramContinuation)
    {
      Object L$0;
      Object L$1;
      int label;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return KCallables.callSuspend(null, null, this);
      }
    };
    label47:
    Object localObject1 = paramContinuation.result;
    Object localObject2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    int i = paramContinuation.label;
    if (i != 0)
    {
      if (i == 1)
      {
        paramKCallable = (Object[])paramContinuation.L$1;
        paramKCallable = (KCallable)paramContinuation.L$0;
        ResultKt.throwOnFailure(localObject1);
        paramArrayOfObject = (Object[])localObject1;
      }
      else
      {
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }
    }
    else
    {
      ResultKt.throwOnFailure(localObject1);
      if (!paramKCallable.isSuspend()) {
        return paramKCallable.call(Arrays.copyOf(paramArrayOfObject, paramArrayOfObject.length));
      }
      if (!(paramKCallable instanceof KFunction)) {
        break label266;
      }
      paramContinuation.L$0 = paramKCallable;
      paramContinuation.L$1 = paramArrayOfObject;
      paramContinuation.label = 1;
      localObject1 = (Continuation)paramContinuation;
      paramContinuation = new SpreadBuilder(2);
      paramContinuation.addSpread(paramArrayOfObject);
      paramContinuation.add(localObject1);
      paramContinuation = paramKCallable.call(paramContinuation.toArray(new Object[paramContinuation.size()]));
      if (paramContinuation == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        DebugProbesKt.probeCoroutineSuspended((Continuation)localObject1);
      }
      paramArrayOfObject = paramContinuation;
      if (paramContinuation == localObject2) {
        return localObject2;
      }
    }
    if ((Intrinsics.areEqual(paramKCallable.getReturnType().getClassifier(), Reflection.getOrCreateKotlinClass(Unit.class))) && (!paramKCallable.getReturnType().isMarkedNullable())) {
      return (Object)Unit.INSTANCE;
    }
    return paramArrayOfObject;
    label266:
    paramArrayOfObject = new StringBuilder();
    paramArrayOfObject.append("Cannot callSuspend on a property ");
    paramArrayOfObject.append(paramKCallable);
    paramArrayOfObject.append(": suspend properties are not supported yet");
    throw ((Throwable)new IllegalArgumentException(paramArrayOfObject.toString()));
  }
  
  public static final <R> Object callSuspendBy(KCallable<? extends R> paramKCallable, Map<KParameter, ? extends Object> paramMap, Continuation<? super R> paramContinuation)
  {
    if ((paramContinuation instanceof callSuspendBy.1))
    {
      localObject1 = (callSuspendBy.1)paramContinuation;
      if ((((callSuspendBy.1)localObject1).label & 0x80000000) != 0)
      {
        ((callSuspendBy.1)localObject1).label += Integer.MIN_VALUE;
        paramContinuation = (Continuation<? super R>)localObject1;
        break label47;
      }
    }
    paramContinuation = new ContinuationImpl(paramContinuation)
    {
      Object L$0;
      Object L$1;
      Object L$2;
      int label;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return KCallables.callSuspendBy(null, null, this);
      }
    };
    label47:
    Object localObject1 = paramContinuation.result;
    Object localObject2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    int i = paramContinuation.label;
    if (i != 0)
    {
      if (i == 1)
      {
        paramKCallable = (KCallableImpl)paramContinuation.L$2;
        paramKCallable = (Map)paramContinuation.L$1;
        paramKCallable = (KCallable)paramContinuation.L$0;
        ResultKt.throwOnFailure(localObject1);
        paramMap = (Map<KParameter, ? extends Object>)localObject1;
      }
      else
      {
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }
    }
    else
    {
      ResultKt.throwOnFailure(localObject1);
      if (!paramKCallable.isSuspend()) {
        return paramKCallable.callBy(paramMap);
      }
      if (!(paramKCallable instanceof KFunction)) {
        break label293;
      }
      KCallableImpl localKCallableImpl = UtilKt.asKCallableImpl(paramKCallable);
      if (localKCallableImpl == null) {
        break label257;
      }
      paramContinuation.L$0 = paramKCallable;
      paramContinuation.L$1 = paramMap;
      paramContinuation.L$2 = localKCallableImpl;
      paramContinuation.label = 1;
      localObject1 = (Continuation)paramContinuation;
      paramContinuation = localKCallableImpl.callDefaultMethod$kotlin_reflection(paramMap, (Continuation)localObject1);
      if (paramContinuation == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        DebugProbesKt.probeCoroutineSuspended((Continuation)localObject1);
      }
      paramMap = paramContinuation;
      if (paramContinuation == localObject2) {
        return localObject2;
      }
    }
    if ((Intrinsics.areEqual(paramKCallable.getReturnType().getClassifier(), Reflection.getOrCreateKotlinClass(Unit.class))) && (!paramKCallable.getReturnType().isMarkedNullable())) {
      return (Object)Unit.INSTANCE;
    }
    return paramMap;
    label257:
    paramMap = new StringBuilder();
    paramMap.append("This callable does not support a default call: ");
    paramMap.append(paramKCallable);
    throw ((Throwable)new KotlinReflectionInternalError(paramMap.toString()));
    label293:
    paramMap = new StringBuilder();
    paramMap.append("Cannot callSuspendBy on a property ");
    paramMap.append(paramKCallable);
    paramMap.append(": suspend properties are not supported yet");
    throw ((Throwable)new IllegalArgumentException(paramMap.toString()));
  }
  
  public static final KParameter findParameterByName(KCallable<?> paramKCallable, String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramKCallable, "$this$findParameterByName");
    Intrinsics.checkParameterIsNotNull(paramString, "name");
    Iterator localIterator = ((Iterable)paramKCallable.getParameters()).iterator();
    Object localObject1 = null;
    int i = 0;
    paramKCallable = null;
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      if (Intrinsics.areEqual(((KParameter)localObject2).getName(), paramString))
      {
        if (i != 0)
        {
          paramKCallable = localObject1;
          break label97;
        }
        i = 1;
        paramKCallable = localObject2;
      }
    }
    if (i == 0) {
      paramKCallable = localObject1;
    }
    label97:
    return (KParameter)paramKCallable;
  }
  
  public static final KParameter getExtensionReceiverParameter(KCallable<?> paramKCallable)
  {
    Intrinsics.checkParameterIsNotNull(paramKCallable, "$this$extensionReceiverParameter");
    Iterator localIterator = ((Iterable)paramKCallable.getParameters()).iterator();
    Object localObject1 = null;
    int i = 0;
    paramKCallable = null;
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      int j;
      if (((KParameter)localObject2).getKind() == KParameter.Kind.EXTENSION_RECEIVER) {
        j = 1;
      } else {
        j = 0;
      }
      if (j != 0)
      {
        if (i != 0)
        {
          paramKCallable = localObject1;
          break label100;
        }
        paramKCallable = localObject2;
        i = 1;
      }
    }
    if (i == 0) {
      paramKCallable = localObject1;
    }
    label100:
    return (KParameter)paramKCallable;
  }
  
  public static final KParameter getInstanceParameter(KCallable<?> paramKCallable)
  {
    Intrinsics.checkParameterIsNotNull(paramKCallable, "$this$instanceParameter");
    Iterator localIterator = ((Iterable)paramKCallable.getParameters()).iterator();
    Object localObject1 = null;
    int i = 0;
    paramKCallable = null;
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      int j;
      if (((KParameter)localObject2).getKind() == KParameter.Kind.INSTANCE) {
        j = 1;
      } else {
        j = 0;
      }
      if (j != 0)
      {
        if (i != 0)
        {
          paramKCallable = localObject1;
          break label101;
        }
        paramKCallable = localObject2;
        i = 1;
      }
    }
    if (i == 0) {
      paramKCallable = localObject1;
    }
    label101:
    return (KParameter)paramKCallable;
  }
  
  public static final List<KParameter> getValueParameters(KCallable<?> paramKCallable)
  {
    Intrinsics.checkParameterIsNotNull(paramKCallable, "$this$valueParameters");
    Object localObject = (Iterable)paramKCallable.getParameters();
    paramKCallable = (Collection)new ArrayList();
    Iterator localIterator = ((Iterable)localObject).iterator();
    while (localIterator.hasNext())
    {
      localObject = localIterator.next();
      int i;
      if (((KParameter)localObject).getKind() == KParameter.Kind.VALUE) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        paramKCallable.add(localObject);
      }
    }
    return (List)paramKCallable;
  }
}

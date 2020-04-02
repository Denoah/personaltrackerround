package kotlinx.coroutines.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(bv={1, 0, 3}, d1={"\000.\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\020\003\n\002\030\002\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\002\b\f\032*\020\n\032\030\022\004\022\0020\005\022\006\022\004\030\0010\005\030\0010\006j\004\030\001`\0072\n\020\013\032\006\022\002\b\0030\fH\002\0321\020\r\032\024\022\004\022\0020\005\022\006\022\004\030\0010\0050\006j\002`\0072\024\b\004\020\016\032\016\022\004\022\0020\005\022\004\022\0020\0050\006H?\b\032!\020\017\032\004\030\001H\020\"\b\b\000\020\020*\0020\0052\006\020\021\032\002H\020H\000?\006\002\020\022\032\033\020\023\032\0020\t*\006\022\002\b\0030\0042\b\b\002\020\024\032\0020\tH?\020\032\030\020\025\032\0020\t*\006\022\002\b\0030\0042\006\020\026\032\0020\tH\002\"\016\020\000\032\0020\001X?\004?\006\002\n\000\"4\020\002\032(\022\f\022\n\022\006\b\001\022\0020\0050\004\022\026\022\024\022\004\022\0020\005\022\006\022\004\030\0010\0050\006j\002`\0070\003X?\004?\006\002\n\000\"\016\020\b\032\0020\tX?\004?\006\002\n\000*(\b\002\020\027\"\020\022\004\022\0020\005\022\006\022\004\030\0010\0050\0062\020\022\004\022\0020\005\022\006\022\004\030\0010\0050\006?\006\030"}, d2={"cacheLock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "exceptionCtors", "Ljava/util/WeakHashMap;", "Ljava/lang/Class;", "", "Lkotlin/Function1;", "Lkotlinx/coroutines/internal/Ctor;", "throwableFields", "", "createConstructor", "constructor", "Ljava/lang/reflect/Constructor;", "safeCtor", "block", "tryCopyException", "E", "exception", "(Ljava/lang/Throwable;)Ljava/lang/Throwable;", "fieldsCount", "accumulator", "fieldsCountOrDefault", "defaultValue", "Ctor", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class ExceptionsConstuctorKt
{
  private static final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
  private static final WeakHashMap<Class<? extends Throwable>, Function1<Throwable, Throwable>> exceptionCtors = new WeakHashMap();
  private static final int throwableFields = fieldsCountOrDefault(Throwable.class, -1);
  
  private static final Function1<Throwable, Throwable> createConstructor(Constructor<?> paramConstructor)
  {
    Object localObject1 = paramConstructor.getParameterTypes();
    int i = localObject1.length;
    Object localObject2 = null;
    Object localObject3;
    if (i != 0)
    {
      if (i != 1)
      {
        if (i != 2)
        {
          localObject3 = localObject2;
        }
        else
        {
          localObject3 = localObject2;
          if (Intrinsics.areEqual(localObject1[0], String.class))
          {
            localObject3 = localObject2;
            if (Intrinsics.areEqual(localObject1[1], Throwable.class)) {
              localObject3 = (Function1)new Lambda(paramConstructor)
              {
                public final Throwable invoke(Throwable paramAnonymousThrowable)
                {
                  Intrinsics.checkParameterIsNotNull(paramAnonymousThrowable, "e");
                  try
                  {
                    Result.Companion localCompanion = Result.Companion;
                    paramAnonymousThrowable = this.$constructor$inlined.newInstance(new Object[] { paramAnonymousThrowable.getMessage(), paramAnonymousThrowable });
                    if (paramAnonymousThrowable != null)
                    {
                      paramAnonymousThrowable = Result.constructor-impl((Throwable)paramAnonymousThrowable);
                    }
                    else
                    {
                      paramAnonymousThrowable = new kotlin/TypeCastException;
                      paramAnonymousThrowable.<init>("null cannot be cast to non-null type kotlin.Throwable");
                      throw paramAnonymousThrowable;
                    }
                  }
                  finally
                  {
                    paramAnonymousThrowable = Result.Companion;
                    paramAnonymousThrowable = Result.constructor-impl(ResultKt.createFailure(localThrowable1));
                    Throwable localThrowable2 = paramAnonymousThrowable;
                    if (Result.isFailure-impl(paramAnonymousThrowable)) {
                      localThrowable2 = null;
                    }
                    return (Throwable)localThrowable2;
                  }
                }
              };
            }
          }
        }
      }
      else
      {
        localObject1 = localObject1[0];
        if (Intrinsics.areEqual(localObject1, Throwable.class))
        {
          localObject3 = (Function1)new Lambda(paramConstructor)
          {
            public final Throwable invoke(Throwable paramAnonymousThrowable)
            {
              Intrinsics.checkParameterIsNotNull(paramAnonymousThrowable, "e");
              try
              {
                Result.Companion localCompanion = Result.Companion;
                paramAnonymousThrowable = this.$constructor$inlined.newInstance(new Object[] { paramAnonymousThrowable });
                if (paramAnonymousThrowable != null)
                {
                  paramAnonymousThrowable = Result.constructor-impl((Throwable)paramAnonymousThrowable);
                }
                else
                {
                  paramAnonymousThrowable = new kotlin/TypeCastException;
                  paramAnonymousThrowable.<init>("null cannot be cast to non-null type kotlin.Throwable");
                  throw paramAnonymousThrowable;
                }
              }
              finally
              {
                paramAnonymousThrowable = Result.Companion;
                paramAnonymousThrowable = Result.constructor-impl(ResultKt.createFailure(localThrowable1));
                Throwable localThrowable2 = paramAnonymousThrowable;
                if (Result.isFailure-impl(paramAnonymousThrowable)) {
                  localThrowable2 = null;
                }
                return (Throwable)localThrowable2;
              }
            }
          };
        }
        else
        {
          localObject3 = localObject2;
          if (Intrinsics.areEqual(localObject1, String.class)) {
            localObject3 = (Function1)new Lambda(paramConstructor)
            {
              public final Throwable invoke(Throwable paramAnonymousThrowable)
              {
                Intrinsics.checkParameterIsNotNull(paramAnonymousThrowable, "e");
                try
                {
                  Object localObject = Result.Companion;
                  localObject = this.$constructor$inlined.newInstance(new Object[] { paramAnonymousThrowable.getMessage() });
                  if (localObject != null)
                  {
                    localObject = (Throwable)localObject;
                    ((Throwable)localObject).initCause(paramAnonymousThrowable);
                    paramAnonymousThrowable = Result.constructor-impl(localObject);
                  }
                  else
                  {
                    paramAnonymousThrowable = new kotlin/TypeCastException;
                    paramAnonymousThrowable.<init>("null cannot be cast to non-null type kotlin.Throwable");
                    throw paramAnonymousThrowable;
                  }
                }
                finally
                {
                  paramAnonymousThrowable = Result.Companion;
                  paramAnonymousThrowable = Result.constructor-impl(ResultKt.createFailure(localThrowable1));
                  Throwable localThrowable2 = paramAnonymousThrowable;
                  if (Result.isFailure-impl(paramAnonymousThrowable)) {
                    localThrowable2 = null;
                  }
                  return (Throwable)localThrowable2;
                }
              }
            };
          }
        }
      }
    }
    else {
      localObject3 = (Function1)new Lambda(paramConstructor)
      {
        public final Throwable invoke(Throwable paramAnonymousThrowable)
        {
          Intrinsics.checkParameterIsNotNull(paramAnonymousThrowable, "e");
          Object localObject;
          try
          {
            localObject = Result.Companion;
            localObject = this.$constructor$inlined.newInstance(new Object[0]);
            if (localObject != null)
            {
              localObject = (Throwable)localObject;
              ((Throwable)localObject).initCause(paramAnonymousThrowable);
              paramAnonymousThrowable = Result.constructor-impl(localObject);
            }
            else
            {
              paramAnonymousThrowable = new kotlin/TypeCastException;
              paramAnonymousThrowable.<init>("null cannot be cast to non-null type kotlin.Throwable");
              throw paramAnonymousThrowable;
            }
          }
          finally
          {
            localObject = Result.Companion;
            paramAnonymousThrowable = Result.constructor-impl(ResultKt.createFailure(paramAnonymousThrowable));
            localObject = paramAnonymousThrowable;
            if (Result.isFailure-impl(paramAnonymousThrowable)) {
              localObject = null;
            }
          }
          return (Throwable)localObject;
        }
      };
    }
    return localObject3;
  }
  
  private static final int fieldsCount(Class<?> paramClass, int paramInt)
  {
    do
    {
      Field[] arrayOfField = paramClass.getDeclaredFields();
      Intrinsics.checkExpressionValueIsNotNull(arrayOfField, "declaredFields");
      int i = arrayOfField.length;
      int j = 0;
      int m;
      for (int k = 0; j < i; k = m)
      {
        Field localField = arrayOfField[j];
        Intrinsics.checkExpressionValueIsNotNull(localField, "it");
        m = k;
        if ((Modifier.isStatic(localField.getModifiers()) ^ true)) {
          m = k + 1;
        }
        j++;
      }
      paramInt += k;
      paramClass = paramClass.getSuperclass();
    } while (paramClass != null);
    return paramInt;
  }
  
  /* Error */
  private static final int fieldsCountOrDefault(Class<?> paramClass, int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 146	kotlin/jvm/JvmClassMappingKt:getKotlinClass	(Ljava/lang/Class;)Lkotlin/reflect/KClass;
    //   4: pop
    //   5: getstatic 152	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   8: astore_2
    //   9: aload_0
    //   10: iconst_0
    //   11: iconst_1
    //   12: aconst_null
    //   13: invokestatic 154	kotlinx/coroutines/internal/ExceptionsConstuctorKt:fieldsCount$default	(Ljava/lang/Class;IILjava/lang/Object;)I
    //   16: invokestatic 160	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   19: invokestatic 164	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   22: astore_0
    //   23: goto +16 -> 39
    //   26: astore_0
    //   27: getstatic 152	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   30: astore_2
    //   31: aload_0
    //   32: invokestatic 170	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   35: invokestatic 164	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   38: astore_0
    //   39: aload_0
    //   40: astore_2
    //   41: aload_0
    //   42: invokestatic 174	kotlin/Result:isFailure-impl	(Ljava/lang/Object;)Z
    //   45: ifeq +8 -> 53
    //   48: iload_1
    //   49: invokestatic 160	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   52: astore_2
    //   53: aload_2
    //   54: checkcast 176	java/lang/Number
    //   57: invokevirtual 179	java/lang/Number:intValue	()I
    //   60: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	61	0	paramClass	Class<?>
    //   0	61	1	paramInt	int
    //   8	46	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   5	23	26	finally
  }
  
  private static final Function1<Throwable, Throwable> safeCtor(Function1<? super Throwable, ? extends Throwable> paramFunction1)
  {
    (Function1)new Lambda(paramFunction1)
    {
      /* Error */
      public final Throwable invoke(Throwable paramAnonymousThrowable)
      {
        // Byte code:
        //   0: aload_1
        //   1: ldc 44
        //   3: invokestatic 50	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
        //   6: getstatic 56	kotlin/Result:Companion	Lkotlin/Result$Companion;
        //   9: astore_2
        //   10: aload_0
        //   11: getfield 33	kotlinx/coroutines/internal/ExceptionsConstuctorKt$safeCtor$1:$block	Lkotlin/jvm/functions/Function1;
        //   14: aload_1
        //   15: invokeinterface 58 2 0
        //   20: checkcast 40	java/lang/Throwable
        //   23: invokestatic 61	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
        //   26: astore_1
        //   27: goto +16 -> 43
        //   30: astore_2
        //   31: getstatic 56	kotlin/Result:Companion	Lkotlin/Result$Companion;
        //   34: astore_1
        //   35: aload_2
        //   36: invokestatic 67	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
        //   39: invokestatic 61	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
        //   42: astore_1
        //   43: aload_1
        //   44: astore_2
        //   45: aload_1
        //   46: invokestatic 71	kotlin/Result:isFailure-impl	(Ljava/lang/Object;)Z
        //   49: ifeq +5 -> 54
        //   52: aconst_null
        //   53: astore_2
        //   54: aload_2
        //   55: checkcast 40	java/lang/Throwable
        //   58: areturn
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	59	0	this	1
        //   0	59	1	paramAnonymousThrowable	Throwable
        //   9	1	2	localCompanion	Result.Companion
        //   30	6	2	localThrowable1	Throwable
        //   44	11	2	localThrowable2	Throwable
        // Exception table:
        //   from	to	target	type
        //   6	27	30	finally
      }
    };
  }
  
  /* Error */
  public static final <E extends Throwable> E tryCopyException(E paramE)
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc -71
    //   3: invokestatic 188	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_0
    //   7: instanceof 190
    //   10: istore_1
    //   11: aconst_null
    //   12: astore_2
    //   13: aconst_null
    //   14: astore_3
    //   15: iload_1
    //   16: ifeq +55 -> 71
    //   19: getstatic 152	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   22: astore 4
    //   24: aload_0
    //   25: checkcast 190	kotlinx/coroutines/CopyableThrowable
    //   28: invokeinterface 194 1 0
    //   33: invokestatic 164	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   36: astore_0
    //   37: goto +17 -> 54
    //   40: astore_0
    //   41: getstatic 152	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   44: astore 4
    //   46: aload_0
    //   47: invokestatic 170	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   50: invokestatic 164	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   53: astore_0
    //   54: aload_0
    //   55: invokestatic 174	kotlin/Result:isFailure-impl	(Ljava/lang/Object;)Z
    //   58: ifeq +8 -> 66
    //   61: aload_3
    //   62: astore_0
    //   63: goto +3 -> 66
    //   66: aload_0
    //   67: checkcast 62	java/lang/Throwable
    //   70: areturn
    //   71: getstatic 74	kotlinx/coroutines/internal/ExceptionsConstuctorKt:cacheLock	Ljava/util/concurrent/locks/ReentrantReadWriteLock;
    //   74: invokevirtual 198	java/util/concurrent/locks/ReentrantReadWriteLock:readLock	()Ljava/util/concurrent/locks/ReentrantReadWriteLock$ReadLock;
    //   77: astore_3
    //   78: aload_3
    //   79: invokevirtual 203	java/util/concurrent/locks/ReentrantReadWriteLock$ReadLock:lock	()V
    //   82: getstatic 79	kotlinx/coroutines/internal/ExceptionsConstuctorKt:exceptionCtors	Ljava/util/WeakHashMap;
    //   85: aload_0
    //   86: invokevirtual 206	java/lang/Object:getClass	()Ljava/lang/Class;
    //   89: invokevirtual 209	java/util/WeakHashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   92: checkcast 100	kotlin/jvm/functions/Function1
    //   95: astore 4
    //   97: aload_3
    //   98: invokevirtual 212	java/util/concurrent/locks/ReentrantReadWriteLock$ReadLock:unlock	()V
    //   101: aload 4
    //   103: ifnull +15 -> 118
    //   106: aload 4
    //   108: aload_0
    //   109: invokeinterface 215 2 0
    //   114: checkcast 62	java/lang/Throwable
    //   117: areturn
    //   118: getstatic 67	kotlinx/coroutines/internal/ExceptionsConstuctorKt:throwableFields	I
    //   121: istore 5
    //   123: aload_0
    //   124: invokevirtual 206	java/lang/Object:getClass	()Ljava/lang/Class;
    //   127: astore_3
    //   128: iconst_0
    //   129: istore 6
    //   131: iconst_0
    //   132: istore 7
    //   134: iconst_0
    //   135: istore 8
    //   137: iconst_0
    //   138: istore 9
    //   140: iload 5
    //   142: aload_3
    //   143: iconst_0
    //   144: invokestatic 65	kotlinx/coroutines/internal/ExceptionsConstuctorKt:fieldsCountOrDefault	(Ljava/lang/Class;I)I
    //   147: if_icmpeq +147 -> 294
    //   150: getstatic 74	kotlinx/coroutines/internal/ExceptionsConstuctorKt:cacheLock	Ljava/util/concurrent/locks/ReentrantReadWriteLock;
    //   153: astore 4
    //   155: aload 4
    //   157: invokevirtual 198	java/util/concurrent/locks/ReentrantReadWriteLock:readLock	()Ljava/util/concurrent/locks/ReentrantReadWriteLock$ReadLock;
    //   160: astore_3
    //   161: aload 4
    //   163: invokevirtual 218	java/util/concurrent/locks/ReentrantReadWriteLock:getWriteHoldCount	()I
    //   166: ifne +13 -> 179
    //   169: aload 4
    //   171: invokevirtual 221	java/util/concurrent/locks/ReentrantReadWriteLock:getReadHoldCount	()I
    //   174: istore 5
    //   176: goto +6 -> 182
    //   179: iconst_0
    //   180: istore 5
    //   182: iconst_0
    //   183: istore 10
    //   185: iload 10
    //   187: iload 5
    //   189: if_icmpge +13 -> 202
    //   192: aload_3
    //   193: invokevirtual 212	java/util/concurrent/locks/ReentrantReadWriteLock$ReadLock:unlock	()V
    //   196: iinc 10 1
    //   199: goto -14 -> 185
    //   202: aload 4
    //   204: invokevirtual 225	java/util/concurrent/locks/ReentrantReadWriteLock:writeLock	()Ljava/util/concurrent/locks/ReentrantReadWriteLock$WriteLock;
    //   207: astore 4
    //   209: aload 4
    //   211: invokevirtual 228	java/util/concurrent/locks/ReentrantReadWriteLock$WriteLock:lock	()V
    //   214: getstatic 79	kotlinx/coroutines/internal/ExceptionsConstuctorKt:exceptionCtors	Ljava/util/WeakHashMap;
    //   217: checkcast 230	java/util/Map
    //   220: aload_0
    //   221: invokevirtual 206	java/lang/Object:getClass	()Ljava/lang/Class;
    //   224: getstatic 234	kotlinx/coroutines/internal/ExceptionsConstuctorKt$tryCopyException$4$1:INSTANCE	Lkotlinx/coroutines/internal/ExceptionsConstuctorKt$tryCopyException$4$1;
    //   227: invokeinterface 238 3 0
    //   232: pop
    //   233: getstatic 243	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   236: astore_0
    //   237: iload 9
    //   239: istore 10
    //   241: iload 10
    //   243: iload 5
    //   245: if_icmpge +13 -> 258
    //   248: aload_3
    //   249: invokevirtual 203	java/util/concurrent/locks/ReentrantReadWriteLock$ReadLock:lock	()V
    //   252: iinc 10 1
    //   255: goto -14 -> 241
    //   258: aload 4
    //   260: invokevirtual 244	java/util/concurrent/locks/ReentrantReadWriteLock$WriteLock:unlock	()V
    //   263: aconst_null
    //   264: areturn
    //   265: astore_0
    //   266: iload 6
    //   268: istore 10
    //   270: iload 10
    //   272: iload 5
    //   274: if_icmpge +13 -> 287
    //   277: aload_3
    //   278: invokevirtual 203	java/util/concurrent/locks/ReentrantReadWriteLock$ReadLock:lock	()V
    //   281: iinc 10 1
    //   284: goto -14 -> 270
    //   287: aload 4
    //   289: invokevirtual 244	java/util/concurrent/locks/ReentrantReadWriteLock$WriteLock:unlock	()V
    //   292: aload_0
    //   293: athrow
    //   294: aconst_null
    //   295: checkcast 100	kotlin/jvm/functions/Function1
    //   298: astore_3
    //   299: aload_0
    //   300: invokevirtual 206	java/lang/Object:getClass	()Ljava/lang/Class;
    //   303: invokevirtual 248	java/lang/Class:getConstructors	()[Ljava/lang/reflect/Constructor;
    //   306: astore 4
    //   308: aload 4
    //   310: ldc -6
    //   312: invokestatic 117	kotlin/jvm/internal/Intrinsics:checkExpressionValueIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   315: aload 4
    //   317: new 16	kotlinx/coroutines/internal/ExceptionsConstuctorKt$tryCopyException$$inlined$sortedByDescending$1
    //   320: dup
    //   321: invokespecial 251	kotlinx/coroutines/internal/ExceptionsConstuctorKt$tryCopyException$$inlined$sortedByDescending$1:<init>	()V
    //   324: checkcast 253	java/util/Comparator
    //   327: invokestatic 259	kotlin/collections/ArraysKt:sortedWith	([Ljava/lang/Object;Ljava/util/Comparator;)Ljava/util/List;
    //   330: invokeinterface 265 1 0
    //   335: astore 11
    //   337: aload 11
    //   339: invokeinterface 271 1 0
    //   344: ifeq +38 -> 382
    //   347: aload 11
    //   349: invokeinterface 275 1 0
    //   354: checkcast 83	java/lang/reflect/Constructor
    //   357: astore_3
    //   358: aload_3
    //   359: ldc_w 276
    //   362: invokestatic 117	kotlin/jvm/internal/Intrinsics:checkExpressionValueIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   365: aload_3
    //   366: invokestatic 278	kotlinx/coroutines/internal/ExceptionsConstuctorKt:createConstructor	(Ljava/lang/reflect/Constructor;)Lkotlin/jvm/functions/Function1;
    //   369: astore 4
    //   371: aload 4
    //   373: astore_3
    //   374: aload 4
    //   376: ifnull -39 -> 337
    //   379: aload 4
    //   381: astore_3
    //   382: getstatic 74	kotlinx/coroutines/internal/ExceptionsConstuctorKt:cacheLock	Ljava/util/concurrent/locks/ReentrantReadWriteLock;
    //   385: astore 4
    //   387: aload 4
    //   389: invokevirtual 198	java/util/concurrent/locks/ReentrantReadWriteLock:readLock	()Ljava/util/concurrent/locks/ReentrantReadWriteLock$ReadLock;
    //   392: astore 11
    //   394: aload 4
    //   396: invokevirtual 218	java/util/concurrent/locks/ReentrantReadWriteLock:getWriteHoldCount	()I
    //   399: ifne +13 -> 412
    //   402: aload 4
    //   404: invokevirtual 221	java/util/concurrent/locks/ReentrantReadWriteLock:getReadHoldCount	()I
    //   407: istore 5
    //   409: goto +6 -> 415
    //   412: iconst_0
    //   413: istore 5
    //   415: iconst_0
    //   416: istore 10
    //   418: iload 10
    //   420: iload 5
    //   422: if_icmpge +14 -> 436
    //   425: aload 11
    //   427: invokevirtual 212	java/util/concurrent/locks/ReentrantReadWriteLock$ReadLock:unlock	()V
    //   430: iinc 10 1
    //   433: goto -15 -> 418
    //   436: aload 4
    //   438: invokevirtual 225	java/util/concurrent/locks/ReentrantReadWriteLock:writeLock	()Ljava/util/concurrent/locks/ReentrantReadWriteLock$WriteLock;
    //   441: astore 12
    //   443: aload 12
    //   445: invokevirtual 228	java/util/concurrent/locks/ReentrantReadWriteLock$WriteLock:lock	()V
    //   448: getstatic 79	kotlinx/coroutines/internal/ExceptionsConstuctorKt:exceptionCtors	Ljava/util/WeakHashMap;
    //   451: checkcast 230	java/util/Map
    //   454: astore 13
    //   456: aload_0
    //   457: invokevirtual 206	java/lang/Object:getClass	()Ljava/lang/Class;
    //   460: astore 14
    //   462: aload_3
    //   463: ifnull +9 -> 472
    //   466: aload_3
    //   467: astore 4
    //   469: goto +11 -> 480
    //   472: getstatic 281	kotlinx/coroutines/internal/ExceptionsConstuctorKt$tryCopyException$5$1:INSTANCE	Lkotlinx/coroutines/internal/ExceptionsConstuctorKt$tryCopyException$5$1;
    //   475: checkcast 100	kotlin/jvm/functions/Function1
    //   478: astore 4
    //   480: aload 13
    //   482: aload 14
    //   484: aload 4
    //   486: invokeinterface 238 3 0
    //   491: pop
    //   492: getstatic 243	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   495: astore 4
    //   497: iload 7
    //   499: istore 10
    //   501: iload 10
    //   503: iload 5
    //   505: if_icmpge +14 -> 519
    //   508: aload 11
    //   510: invokevirtual 203	java/util/concurrent/locks/ReentrantReadWriteLock$ReadLock:lock	()V
    //   513: iinc 10 1
    //   516: goto -15 -> 501
    //   519: aload 12
    //   521: invokevirtual 244	java/util/concurrent/locks/ReentrantReadWriteLock$WriteLock:unlock	()V
    //   524: aload_2
    //   525: astore 4
    //   527: aload_3
    //   528: ifnull +15 -> 543
    //   531: aload_3
    //   532: aload_0
    //   533: invokeinterface 215 2 0
    //   538: checkcast 62	java/lang/Throwable
    //   541: astore 4
    //   543: aload 4
    //   545: areturn
    //   546: astore_0
    //   547: iload 8
    //   549: istore 10
    //   551: iload 10
    //   553: iload 5
    //   555: if_icmpge +14 -> 569
    //   558: aload 11
    //   560: invokevirtual 203	java/util/concurrent/locks/ReentrantReadWriteLock$ReadLock:lock	()V
    //   563: iinc 10 1
    //   566: goto -15 -> 551
    //   569: aload 12
    //   571: invokevirtual 244	java/util/concurrent/locks/ReentrantReadWriteLock$WriteLock:unlock	()V
    //   574: aload_0
    //   575: athrow
    //   576: astore_0
    //   577: aload_3
    //   578: invokevirtual 212	java/util/concurrent/locks/ReentrantReadWriteLock$ReadLock:unlock	()V
    //   581: aload_0
    //   582: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	583	0	paramE	E
    //   10	6	1	bool	boolean
    //   12	513	2	localObject1	Object
    //   14	564	3	localObject2	Object
    //   22	522	4	localObject3	Object
    //   121	435	5	i	int
    //   129	138	6	j	int
    //   132	366	7	k	int
    //   135	413	8	m	int
    //   138	100	9	n	int
    //   183	381	10	i1	int
    //   335	224	11	localObject4	Object
    //   441	129	12	localWriteLock	java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock
    //   454	27	13	localMap	java.util.Map
    //   460	23	14	localClass	Class
    // Exception table:
    //   from	to	target	type
    //   19	37	40	finally
    //   214	237	265	finally
    //   448	462	546	finally
    //   472	480	546	finally
    //   480	497	546	finally
    //   82	97	576	finally
  }
}

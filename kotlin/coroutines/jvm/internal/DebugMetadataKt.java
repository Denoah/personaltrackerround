package kotlin.coroutines.jvm.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0000\n\000\n\002\020\b\n\000\n\002\020\002\n\002\b\003\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\021\n\002\020\016\n\002\b\002\n\002\030\002\n\002\b\002\032\030\020\002\032\0020\0032\006\020\004\032\0020\0012\006\020\005\032\0020\001H\002\032\016\020\006\032\004\030\0010\007*\0020\bH\002\032\f\020\t\032\0020\001*\0020\bH\002\032\031\020\n\032\n\022\004\022\0020\f\030\0010\013*\0020\bH\001?\006\002\020\r\032\023\020\016\032\004\030\0010\017*\0020\bH\001?\006\002\b\020\"\016\020\000\032\0020\001X?T?\006\002\n\000?\006\021"}, d2={"COROUTINES_DEBUG_METADATA_VERSION", "", "checkDebugMetadataVersion", "", "expected", "actual", "getDebugMetadataAnnotation", "Lkotlin/coroutines/jvm/internal/DebugMetadata;", "Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;", "getLabel", "getSpilledVariableFieldMapping", "", "", "(Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;)[Ljava/lang/String;", "getStackTraceElementImpl", "Ljava/lang/StackTraceElement;", "getStackTraceElement", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class DebugMetadataKt
{
  private static final int COROUTINES_DEBUG_METADATA_VERSION = 1;
  
  private static final void checkDebugMetadataVersion(int paramInt1, int paramInt2)
  {
    if (paramInt2 <= paramInt1) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Debug metadata version mismatch. Expected: ");
    localStringBuilder.append(paramInt1);
    localStringBuilder.append(", got ");
    localStringBuilder.append(paramInt2);
    localStringBuilder.append(". Please update the Kotlin standard library.");
    throw ((Throwable)new IllegalStateException(localStringBuilder.toString().toString()));
  }
  
  private static final DebugMetadata getDebugMetadataAnnotation(BaseContinuationImpl paramBaseContinuationImpl)
  {
    return (DebugMetadata)paramBaseContinuationImpl.getClass().getAnnotation(DebugMetadata.class);
  }
  
  private static final int getLabel(BaseContinuationImpl paramBaseContinuationImpl)
  {
    int i;
    try
    {
      Object localObject = paramBaseContinuationImpl.getClass().getDeclaredField("label");
      Intrinsics.checkExpressionValueIsNotNull(localObject, "field");
      ((Field)localObject).setAccessible(true);
      localObject = ((Field)localObject).get(paramBaseContinuationImpl);
      paramBaseContinuationImpl = (BaseContinuationImpl)localObject;
      if (!(localObject instanceof Integer)) {
        paramBaseContinuationImpl = null;
      }
      paramBaseContinuationImpl = (Integer)paramBaseContinuationImpl;
      if (paramBaseContinuationImpl != null) {
        i = paramBaseContinuationImpl.intValue();
      } else {
        i = 0;
      }
      i--;
    }
    catch (Exception paramBaseContinuationImpl)
    {
      i = -1;
    }
    return i;
  }
  
  public static final String[] getSpilledVariableFieldMapping(BaseContinuationImpl paramBaseContinuationImpl)
  {
    Intrinsics.checkParameterIsNotNull(paramBaseContinuationImpl, "$this$getSpilledVariableFieldMapping");
    DebugMetadata localDebugMetadata = getDebugMetadataAnnotation(paramBaseContinuationImpl);
    if (localDebugMetadata != null)
    {
      checkDebugMetadataVersion(1, localDebugMetadata.v());
      ArrayList localArrayList = new ArrayList();
      int i = getLabel(paramBaseContinuationImpl);
      paramBaseContinuationImpl = localDebugMetadata.i();
      int j = paramBaseContinuationImpl.length;
      for (int k = 0; k < j; k++) {
        if (paramBaseContinuationImpl[k] == i)
        {
          localArrayList.add(localDebugMetadata.s()[k]);
          localArrayList.add(localDebugMetadata.n()[k]);
        }
      }
      paramBaseContinuationImpl = ((Collection)localArrayList).toArray(new String[0]);
      if (paramBaseContinuationImpl != null) {
        return (String[])paramBaseContinuationImpl;
      }
      throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }
    return null;
  }
  
  public static final StackTraceElement getStackTraceElement(BaseContinuationImpl paramBaseContinuationImpl)
  {
    Intrinsics.checkParameterIsNotNull(paramBaseContinuationImpl, "$this$getStackTraceElementImpl");
    DebugMetadata localDebugMetadata = getDebugMetadataAnnotation(paramBaseContinuationImpl);
    if (localDebugMetadata != null)
    {
      checkDebugMetadataVersion(1, localDebugMetadata.v());
      int i = getLabel(paramBaseContinuationImpl);
      if (i < 0) {
        i = -1;
      } else {
        i = localDebugMetadata.l()[i];
      }
      String str = ModuleNameRetriever.INSTANCE.getModuleName(paramBaseContinuationImpl);
      if (str == null)
      {
        paramBaseContinuationImpl = localDebugMetadata.c();
      }
      else
      {
        paramBaseContinuationImpl = new StringBuilder();
        paramBaseContinuationImpl.append(str);
        paramBaseContinuationImpl.append('/');
        paramBaseContinuationImpl.append(localDebugMetadata.c());
        paramBaseContinuationImpl = paramBaseContinuationImpl.toString();
      }
      return new StackTraceElement(paramBaseContinuationImpl, localDebugMetadata.m(), localDebugMetadata.f(), i);
    }
    return null;
  }
}

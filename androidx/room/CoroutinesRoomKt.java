package androidx.room;

import java.util.Map;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.ExecutorsKt;

@Metadata(bv={1, 0, 3}, d1={"\000\016\n\000\n\002\030\002\n\002\030\002\n\002\b\005\"\030\020\000\032\0020\001*\0020\0028@X?\004?\006\006\032\004\b\003\020\004\"\030\020\005\032\0020\001*\0020\0028@X?\004?\006\006\032\004\b\006\020\004?\006\007"}, d2={"queryDispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "Landroidx/room/RoomDatabase;", "getQueryDispatcher", "(Landroidx/room/RoomDatabase;)Lkotlinx/coroutines/CoroutineDispatcher;", "transactionDispatcher", "getTransactionDispatcher", "room-ktx_release"}, k=2, mv={1, 1, 15})
public final class CoroutinesRoomKt
{
  public static final CoroutineDispatcher getQueryDispatcher(RoomDatabase paramRoomDatabase)
  {
    Intrinsics.checkParameterIsNotNull(paramRoomDatabase, "$this$queryDispatcher");
    Map localMap = paramRoomDatabase.getBackingFieldMap();
    Intrinsics.checkExpressionValueIsNotNull(localMap, "backingFieldMap");
    Object localObject1 = localMap.get("QueryDispatcher");
    Object localObject2 = localObject1;
    if (localObject1 == null)
    {
      paramRoomDatabase = paramRoomDatabase.getQueryExecutor();
      Intrinsics.checkExpressionValueIsNotNull(paramRoomDatabase, "queryExecutor");
      localObject2 = ExecutorsKt.from(paramRoomDatabase);
      localMap.put("QueryDispatcher", localObject2);
    }
    if (localObject2 != null) {
      return (CoroutineDispatcher)localObject2;
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.CoroutineDispatcher");
  }
  
  public static final CoroutineDispatcher getTransactionDispatcher(RoomDatabase paramRoomDatabase)
  {
    Intrinsics.checkParameterIsNotNull(paramRoomDatabase, "$this$transactionDispatcher");
    Map localMap = paramRoomDatabase.getBackingFieldMap();
    Intrinsics.checkExpressionValueIsNotNull(localMap, "backingFieldMap");
    Object localObject1 = localMap.get("TransactionDispatcher");
    Object localObject2 = localObject1;
    if (localObject1 == null)
    {
      paramRoomDatabase = paramRoomDatabase.getTransactionExecutor();
      Intrinsics.checkExpressionValueIsNotNull(paramRoomDatabase, "transactionExecutor");
      localObject2 = ExecutorsKt.from(paramRoomDatabase);
      localMap.put("TransactionDispatcher", localObject2);
    }
    if (localObject2 != null) {
      return (CoroutineDispatcher)localObject2;
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.CoroutineDispatcher");
  }
}

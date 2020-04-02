package androidx.core.database.sqlite;

import android.database.sqlite.SQLiteDatabase;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\034\n\002\b\002\n\002\030\002\n\000\n\002\020\013\n\000\n\002\030\002\n\002\030\002\n\002\b\002\032;\020\000\032\002H\001\"\004\b\000\020\001*\0020\0022\b\b\002\020\003\032\0020\0042\027\020\005\032\023\022\004\022\0020\002\022\004\022\002H\0010\006?\006\002\b\007H?\b?\006\002\020\b?\006\t"}, d2={"transaction", "T", "Landroid/database/sqlite/SQLiteDatabase;", "exclusive", "", "body", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Landroid/database/sqlite/SQLiteDatabase;ZLkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class SQLiteDatabaseKt
{
  public static final <T> T transaction(SQLiteDatabase paramSQLiteDatabase, boolean paramBoolean, Function1<? super SQLiteDatabase, ? extends T> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSQLiteDatabase, "$this$transaction");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "body");
    if (paramBoolean) {
      paramSQLiteDatabase.beginTransaction();
    } else {
      paramSQLiteDatabase.beginTransactionNonExclusive();
    }
    try
    {
      paramFunction1 = paramFunction1.invoke(paramSQLiteDatabase);
      paramSQLiteDatabase.setTransactionSuccessful();
      return paramFunction1;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramSQLiteDatabase.endTransaction();
      InlineMarker.finallyEnd(1);
    }
  }
}

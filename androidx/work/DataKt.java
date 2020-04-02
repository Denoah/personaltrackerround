package androidx.work;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000$\n\000\n\002\030\002\n\000\n\002\020\021\n\002\030\002\n\002\020\016\n\002\020\000\n\002\b\002\n\002\020\013\n\002\b\003\032>\020\000\032\0020\0012.\020\002\032\030\022\024\b\001\022\020\022\004\022\0020\005\022\006\022\004\030\0010\0060\0040\003\"\020\022\004\022\0020\005\022\006\022\004\030\0010\0060\004H?\b?\006\002\020\007\032!\020\b\032\0020\t\"\n\b\000\020\n\030\001*\0020\006*\0020\0012\006\020\013\032\0020\005H?\b?\006\f"}, d2={"workDataOf", "Landroidx/work/Data;", "pairs", "", "Lkotlin/Pair;", "", "", "([Lkotlin/Pair;)Landroidx/work/Data;", "hasKeyWithValueOfType", "", "T", "key", "work-runtime-ktx_release"}, k=2, mv={1, 1, 16})
public final class DataKt
{
  public static final Data workDataOf(Pair<String, ? extends Object>... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "pairs");
    Data.Builder localBuilder = new Data.Builder();
    int i = paramVarArgs.length;
    for (int j = 0; j < i; j++)
    {
      Pair<String, ? extends Object> localPair = paramVarArgs[j];
      localBuilder.put((String)localPair.getFirst(), localPair.getSecond());
    }
    paramVarArgs = localBuilder.build();
    Intrinsics.checkExpressionValueIsNotNull(paramVarArgs, "dataBuilder.build()");
    return paramVarArgs;
  }
}

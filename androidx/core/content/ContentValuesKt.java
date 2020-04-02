package androidx.core.content;

import android.content.ContentValues;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\034\n\000\n\002\030\002\n\000\n\002\020\021\n\002\030\002\n\002\020\016\n\002\020\000\n\002\b\002\032;\020\000\032\0020\0012.\020\002\032\030\022\024\b\001\022\020\022\004\022\0020\005\022\006\022\004\030\0010\0060\0040\003\"\020\022\004\022\0020\005\022\006\022\004\030\0010\0060\004?\006\002\020\007?\006\b"}, d2={"contentValuesOf", "Landroid/content/ContentValues;", "pairs", "", "Lkotlin/Pair;", "", "", "([Lkotlin/Pair;)Landroid/content/ContentValues;", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class ContentValuesKt
{
  public static final ContentValues contentValuesOf(Pair<String, ? extends Object>... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "pairs");
    Object localObject1 = new ContentValues(paramVarArgs.length);
    int i = paramVarArgs.length;
    int j = 0;
    while (j < i)
    {
      Object localObject2 = paramVarArgs[j];
      String str = (String)((Pair)localObject2).component1();
      localObject2 = ((Pair)localObject2).component2();
      if (localObject2 == null)
      {
        ((ContentValues)localObject1).putNull(str);
      }
      else if ((localObject2 instanceof String))
      {
        ((ContentValues)localObject1).put(str, (String)localObject2);
      }
      else if ((localObject2 instanceof Integer))
      {
        ((ContentValues)localObject1).put(str, (Integer)localObject2);
      }
      else if ((localObject2 instanceof Long))
      {
        ((ContentValues)localObject1).put(str, (Long)localObject2);
      }
      else if ((localObject2 instanceof Boolean))
      {
        ((ContentValues)localObject1).put(str, (Boolean)localObject2);
      }
      else if ((localObject2 instanceof Float))
      {
        ((ContentValues)localObject1).put(str, (Float)localObject2);
      }
      else if ((localObject2 instanceof Double))
      {
        ((ContentValues)localObject1).put(str, (Double)localObject2);
      }
      else if ((localObject2 instanceof byte[]))
      {
        ((ContentValues)localObject1).put(str, (byte[])localObject2);
      }
      else if ((localObject2 instanceof Byte))
      {
        ((ContentValues)localObject1).put(str, (Byte)localObject2);
      }
      else
      {
        if (!(localObject2 instanceof Short)) {
          break label263;
        }
        ((ContentValues)localObject1).put(str, (Short)localObject2);
      }
      j++;
      continue;
      label263:
      paramVarArgs = localObject2.getClass().getCanonicalName();
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Illegal value type ");
      ((StringBuilder)localObject1).append(paramVarArgs);
      ((StringBuilder)localObject1).append(" for key \"");
      ((StringBuilder)localObject1).append(str);
      ((StringBuilder)localObject1).append('"');
      throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject1).toString()));
    }
    return localObject1;
  }
}

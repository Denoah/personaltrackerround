package androidx.core.os;

import android.os.Build.VERSION;
import android.os.PersistableBundle;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\034\n\000\n\002\030\002\n\000\n\002\020\021\n\002\030\002\n\002\020\016\n\002\020\000\n\002\b\002\032=\020\000\032\0020\0012.\020\002\032\030\022\024\b\001\022\020\022\004\022\0020\005\022\006\022\004\030\0010\0060\0040\003\"\020\022\004\022\0020\005\022\006\022\004\030\0010\0060\004H\007?\006\002\020\007?\006\b"}, d2={"persistableBundleOf", "Landroid/os/PersistableBundle;", "pairs", "", "Lkotlin/Pair;", "", "", "([Lkotlin/Pair;)Landroid/os/PersistableBundle;", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class PersistableBundleKt
{
  public static final PersistableBundle persistableBundleOf(Pair<String, ? extends Object>... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "pairs");
    Object localObject1 = new PersistableBundle(paramVarArgs.length);
    int i = paramVarArgs.length;
    int j = 0;
    while (j < i)
    {
      Object localObject2 = paramVarArgs[j];
      String str = (String)((Pair)localObject2).component1();
      Object localObject3 = ((Pair)localObject2).component2();
      if (localObject3 == null)
      {
        ((PersistableBundle)localObject1).putString(str, null);
      }
      else if ((localObject3 instanceof Boolean))
      {
        if (Build.VERSION.SDK_INT >= 22)
        {
          ((PersistableBundle)localObject1).putBoolean(str, ((Boolean)localObject3).booleanValue());
        }
        else
        {
          paramVarArgs = new StringBuilder();
          paramVarArgs.append("Illegal value type boolean for key \"");
          paramVarArgs.append(str);
          paramVarArgs.append('"');
          throw ((Throwable)new IllegalArgumentException(paramVarArgs.toString()));
        }
      }
      else if ((localObject3 instanceof Double))
      {
        ((PersistableBundle)localObject1).putDouble(str, ((Number)localObject3).doubleValue());
      }
      else if ((localObject3 instanceof Integer))
      {
        ((PersistableBundle)localObject1).putInt(str, ((Number)localObject3).intValue());
      }
      else if ((localObject3 instanceof Long))
      {
        ((PersistableBundle)localObject1).putLong(str, ((Number)localObject3).longValue());
      }
      else if ((localObject3 instanceof String))
      {
        ((PersistableBundle)localObject1).putString(str, (String)localObject3);
      }
      else if ((localObject3 instanceof boolean[]))
      {
        if (Build.VERSION.SDK_INT >= 22)
        {
          ((PersistableBundle)localObject1).putBooleanArray(str, (boolean[])localObject3);
        }
        else
        {
          paramVarArgs = new StringBuilder();
          paramVarArgs.append("Illegal value type boolean[] for key \"");
          paramVarArgs.append(str);
          paramVarArgs.append('"');
          throw ((Throwable)new IllegalArgumentException(paramVarArgs.toString()));
        }
      }
      else if ((localObject3 instanceof double[]))
      {
        ((PersistableBundle)localObject1).putDoubleArray(str, (double[])localObject3);
      }
      else if ((localObject3 instanceof int[]))
      {
        ((PersistableBundle)localObject1).putIntArray(str, (int[])localObject3);
      }
      else if ((localObject3 instanceof long[]))
      {
        ((PersistableBundle)localObject1).putLongArray(str, (long[])localObject3);
      }
      else
      {
        if (!(localObject3 instanceof Object[])) {
          break label515;
        }
        localObject2 = localObject3.getClass().getComponentType();
        if (localObject2 == null) {
          Intrinsics.throwNpe();
        }
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "value::class.java.componentType!!");
        if (!String.class.isAssignableFrom((Class)localObject2)) {
          break label452;
        }
        if (localObject3 == null) {
          break label442;
        }
        ((PersistableBundle)localObject1).putStringArray(str, (String[])localObject3);
      }
      j++;
      continue;
      label442:
      throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
      label452:
      localObject1 = ((Class)localObject2).getCanonicalName();
      paramVarArgs = new StringBuilder();
      paramVarArgs.append("Illegal value array type ");
      paramVarArgs.append((String)localObject1);
      paramVarArgs.append(" for key \"");
      paramVarArgs.append(str);
      paramVarArgs.append('"');
      throw ((Throwable)new IllegalArgumentException(paramVarArgs.toString()));
      label515:
      paramVarArgs = localObject3.getClass().getCanonicalName();
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

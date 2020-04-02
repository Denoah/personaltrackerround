package androidx.core.os;

import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\034\n\000\n\002\030\002\n\000\n\002\020\021\n\002\030\002\n\002\020\016\n\002\020\000\n\002\b\002\032;\020\000\032\0020\0012.\020\002\032\030\022\024\b\001\022\020\022\004\022\0020\005\022\006\022\004\030\0010\0060\0040\003\"\020\022\004\022\0020\005\022\006\022\004\030\0010\0060\004?\006\002\020\007?\006\b"}, d2={"bundleOf", "Landroid/os/Bundle;", "pairs", "", "Lkotlin/Pair;", "", "", "([Lkotlin/Pair;)Landroid/os/Bundle;", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class BundleKt
{
  public static final Bundle bundleOf(Pair<String, ? extends Object>... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "pairs");
    Object localObject1 = new Bundle(paramVarArgs.length);
    int i = paramVarArgs.length;
    int j = 0;
    while (j < i)
    {
      Object localObject2 = paramVarArgs[j];
      String str = (String)((Pair)localObject2).component1();
      Object localObject3 = ((Pair)localObject2).component2();
      if (localObject3 == null)
      {
        ((Bundle)localObject1).putString(str, null);
      }
      else if ((localObject3 instanceof Boolean))
      {
        ((Bundle)localObject1).putBoolean(str, ((Boolean)localObject3).booleanValue());
      }
      else if ((localObject3 instanceof Byte))
      {
        ((Bundle)localObject1).putByte(str, ((Number)localObject3).byteValue());
      }
      else if ((localObject3 instanceof Character))
      {
        ((Bundle)localObject1).putChar(str, ((Character)localObject3).charValue());
      }
      else if ((localObject3 instanceof Double))
      {
        ((Bundle)localObject1).putDouble(str, ((Number)localObject3).doubleValue());
      }
      else if ((localObject3 instanceof Float))
      {
        ((Bundle)localObject1).putFloat(str, ((Number)localObject3).floatValue());
      }
      else if ((localObject3 instanceof Integer))
      {
        ((Bundle)localObject1).putInt(str, ((Number)localObject3).intValue());
      }
      else if ((localObject3 instanceof Long))
      {
        ((Bundle)localObject1).putLong(str, ((Number)localObject3).longValue());
      }
      else if ((localObject3 instanceof Short))
      {
        ((Bundle)localObject1).putShort(str, ((Number)localObject3).shortValue());
      }
      else if ((localObject3 instanceof Bundle))
      {
        ((Bundle)localObject1).putBundle(str, (Bundle)localObject3);
      }
      else if ((localObject3 instanceof CharSequence))
      {
        ((Bundle)localObject1).putCharSequence(str, (CharSequence)localObject3);
      }
      else if ((localObject3 instanceof Parcelable))
      {
        ((Bundle)localObject1).putParcelable(str, (Parcelable)localObject3);
      }
      else if ((localObject3 instanceof boolean[]))
      {
        ((Bundle)localObject1).putBooleanArray(str, (boolean[])localObject3);
      }
      else if ((localObject3 instanceof byte[]))
      {
        ((Bundle)localObject1).putByteArray(str, (byte[])localObject3);
      }
      else if ((localObject3 instanceof char[]))
      {
        ((Bundle)localObject1).putCharArray(str, (char[])localObject3);
      }
      else if ((localObject3 instanceof double[]))
      {
        ((Bundle)localObject1).putDoubleArray(str, (double[])localObject3);
      }
      else if ((localObject3 instanceof float[]))
      {
        ((Bundle)localObject1).putFloatArray(str, (float[])localObject3);
      }
      else if ((localObject3 instanceof int[]))
      {
        ((Bundle)localObject1).putIntArray(str, (int[])localObject3);
      }
      else if ((localObject3 instanceof long[]))
      {
        ((Bundle)localObject1).putLongArray(str, (long[])localObject3);
      }
      else if ((localObject3 instanceof short[]))
      {
        ((Bundle)localObject1).putShortArray(str, (short[])localObject3);
      }
      else if ((localObject3 instanceof Object[]))
      {
        localObject2 = localObject3.getClass().getComponentType();
        if (localObject2 == null) {
          Intrinsics.throwNpe();
        }
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "value::class.java.componentType!!");
        if (Parcelable.class.isAssignableFrom((Class)localObject2))
        {
          if (localObject3 != null) {
            ((Bundle)localObject1).putParcelableArray(str, (Parcelable[])localObject3);
          } else {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<android.os.Parcelable>");
          }
        }
        else if (String.class.isAssignableFrom((Class)localObject2))
        {
          if (localObject3 != null) {
            ((Bundle)localObject1).putStringArray(str, (String[])localObject3);
          } else {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
          }
        }
        else if (CharSequence.class.isAssignableFrom((Class)localObject2))
        {
          if (localObject3 != null) {
            ((Bundle)localObject1).putCharSequenceArray(str, (CharSequence[])localObject3);
          } else {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.CharSequence>");
          }
        }
        else if (Serializable.class.isAssignableFrom((Class)localObject2))
        {
          ((Bundle)localObject1).putSerializable(str, (Serializable)localObject3);
        }
        else
        {
          paramVarArgs = ((Class)localObject2).getCanonicalName();
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("Illegal value array type ");
          ((StringBuilder)localObject1).append(paramVarArgs);
          ((StringBuilder)localObject1).append(" for key \"");
          ((StringBuilder)localObject1).append(str);
          ((StringBuilder)localObject1).append('"');
          throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject1).toString()));
        }
      }
      else if ((localObject3 instanceof Serializable))
      {
        ((Bundle)localObject1).putSerializable(str, (Serializable)localObject3);
      }
      else if ((Build.VERSION.SDK_INT >= 18) && ((localObject3 instanceof Binder)))
      {
        ((Bundle)localObject1).putBinder(str, (IBinder)localObject3);
      }
      else if ((Build.VERSION.SDK_INT >= 21) && ((localObject3 instanceof Size)))
      {
        ((Bundle)localObject1).putSize(str, (Size)localObject3);
      }
      else
      {
        if ((Build.VERSION.SDK_INT < 21) || (!(localObject3 instanceof SizeF))) {
          break label859;
        }
        ((Bundle)localObject1).putSizeF(str, (SizeF)localObject3);
      }
      j++;
      continue;
      label859:
      localObject1 = localObject3.getClass().getCanonicalName();
      paramVarArgs = new StringBuilder();
      paramVarArgs.append("Illegal value type ");
      paramVarArgs.append((String)localObject1);
      paramVarArgs.append(" for key \"");
      paramVarArgs.append(str);
      paramVarArgs.append('"');
      throw ((Throwable)new IllegalArgumentException(paramVarArgs.toString()));
    }
    return localObject1;
  }
}

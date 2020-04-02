package androidx.core.content;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\000\n\002\020\002\n\002\030\002\n\000\n\002\020\013\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\000\0320\020\000\032\0020\001*\0020\0022\b\b\002\020\003\032\0020\0042\027\020\005\032\023\022\004\022\0020\007\022\004\022\0020\0010\006?\006\002\b\bH?\b?\006\t"}, d2={"edit", "", "Landroid/content/SharedPreferences;", "commit", "", "action", "Lkotlin/Function1;", "Landroid/content/SharedPreferences$Editor;", "Lkotlin/ExtensionFunctionType;", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class SharedPreferencesKt
{
  public static final void edit(SharedPreferences paramSharedPreferences, boolean paramBoolean, Function1<? super SharedPreferences.Editor, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramSharedPreferences, "$this$edit");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    paramSharedPreferences = paramSharedPreferences.edit();
    Intrinsics.checkExpressionValueIsNotNull(paramSharedPreferences, "editor");
    paramFunction1.invoke(paramSharedPreferences);
    if (paramBoolean) {
      paramSharedPreferences.commit();
    } else {
      paramSharedPreferences.apply();
    }
  }
}

package androidx.work;

import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

@Metadata(bv={1, 0, 3}, d1={"\000\032\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\000\032\025\020\000\032\0020\001\"\n\b\000\020\002\030\001*\0020\003H?\b\032\037\020\004\032\0020\001*\0020\0012\020\b\001\020\005\032\n\022\006\b\001\022\0020\0070\006H?\b?\006\b"}, d2={"OneTimeWorkRequestBuilder", "Landroidx/work/OneTimeWorkRequest$Builder;", "W", "Landroidx/work/ListenableWorker;", "setInputMerger", "inputMerger", "Lkotlin/reflect/KClass;", "Landroidx/work/InputMerger;", "work-runtime-ktx_release"}, k=2, mv={1, 1, 16})
public final class OneTimeWorkRequestKt
{
  public static final OneTimeWorkRequest.Builder setInputMerger(OneTimeWorkRequest.Builder paramBuilder, KClass<? extends InputMerger> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramBuilder, "$this$setInputMerger");
    Intrinsics.checkParameterIsNotNull(paramKClass, "inputMerger");
    paramBuilder = paramBuilder.setInputMerger(JvmClassMappingKt.getJavaClass(paramKClass));
    Intrinsics.checkExpressionValueIsNotNull(paramBuilder, "setInputMerger(inputMerger.java)");
    return paramBuilder;
  }
}

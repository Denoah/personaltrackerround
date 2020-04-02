package kotlinx.coroutines.android;

import android.os.Looper;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.MainDispatcherFactory;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\003\n\002\030\002\n\000\n\002\020 \n\000\n\002\020\016\n\000\b\000\030\0002\0020\001B\005?\006\002\020\002J\026\020\007\032\0020\b2\f\020\t\032\b\022\004\022\0020\0010\nH\026J\n\020\013\032\004\030\0010\fH\026R\024\020\003\032\0020\0048VX?\004?\006\006\032\004\b\005\020\006?\006\r"}, d2={"Lkotlinx/coroutines/android/AndroidDispatcherFactory;", "Lkotlinx/coroutines/internal/MainDispatcherFactory;", "()V", "loadPriority", "", "getLoadPriority", "()I", "createDispatcher", "Lkotlinx/coroutines/android/HandlerContext;", "allFactories", "", "hintOnError", "", "kotlinx-coroutines-android"}, k=1, mv={1, 1, 16})
public final class AndroidDispatcherFactory
  implements MainDispatcherFactory
{
  public AndroidDispatcherFactory() {}
  
  public HandlerContext createDispatcher(List<? extends MainDispatcherFactory> paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "allFactories");
    paramList = Looper.getMainLooper();
    Intrinsics.checkExpressionValueIsNotNull(paramList, "Looper.getMainLooper()");
    return new HandlerContext(HandlerDispatcherKt.asHandler(paramList, true), "Main");
  }
  
  public int getLoadPriority()
  {
    return 1073741823;
  }
  
  public String hintOnError()
  {
    return "For tests Dispatchers.setMain from kotlinx-coroutines-test module can be used";
  }
}

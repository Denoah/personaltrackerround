package androidx.activity;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;
import androidx.lifecycle.LifecycleEventObserver;
import java.lang.reflect.Field;

final class ImmLeaksCleaner
  implements LifecycleEventObserver
{
  private static final int INIT_FAILED = 2;
  private static final int INIT_SUCCESS = 1;
  private static final int NOT_INITIALIAZED = 0;
  private static Field sHField;
  private static Field sNextServedViewField;
  private static int sReflectedFieldsInitialized;
  private static Field sServedViewField;
  private Activity mActivity;
  
  ImmLeaksCleaner(Activity paramActivity)
  {
    this.mActivity = paramActivity;
  }
  
  private static void initializeReflectiveFields()
  {
    try
    {
      sReflectedFieldsInitialized = 2;
      Field localField = InputMethodManager.class.getDeclaredField("mServedView");
      sServedViewField = localField;
      localField.setAccessible(true);
      localField = InputMethodManager.class.getDeclaredField("mNextServedView");
      sNextServedViewField = localField;
      localField.setAccessible(true);
      localField = InputMethodManager.class.getDeclaredField("mH");
      sHField = localField;
      localField.setAccessible(true);
      sReflectedFieldsInitialized = 1;
      return;
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      for (;;) {}
    }
  }
  
  /* Error */
  public void onStateChanged(androidx.lifecycle.LifecycleOwner paramLifecycleOwner, androidx.lifecycle.Lifecycle.Event paramEvent)
  {
    // Byte code:
    //   0: aload_2
    //   1: getstatic 72	androidx/lifecycle/Lifecycle$Event:ON_DESTROY	Landroidx/lifecycle/Lifecycle$Event;
    //   4: if_acmpeq +4 -> 8
    //   7: return
    //   8: getstatic 34	androidx/activity/ImmLeaksCleaner:sReflectedFieldsInitialized	I
    //   11: ifne +6 -> 17
    //   14: invokestatic 74	androidx/activity/ImmLeaksCleaner:initializeReflectiveFields	()V
    //   17: getstatic 34	androidx/activity/ImmLeaksCleaner:sReflectedFieldsInitialized	I
    //   20: iconst_1
    //   21: if_icmpne +97 -> 118
    //   24: aload_0
    //   25: getfield 29	androidx/activity/ImmLeaksCleaner:mActivity	Landroid/app/Activity;
    //   28: ldc 76
    //   30: invokevirtual 82	android/app/Activity:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   33: checkcast 36	android/view/inputmethod/InputMethodManager
    //   36: astore_2
    //   37: getstatic 60	androidx/activity/ImmLeaksCleaner:sHField	Ljava/lang/reflect/Field;
    //   40: aload_2
    //   41: invokevirtual 86	java/lang/reflect/Field:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   44: astore_1
    //   45: aload_1
    //   46: ifnonnull +4 -> 50
    //   49: return
    //   50: aload_1
    //   51: monitorenter
    //   52: getstatic 46	androidx/activity/ImmLeaksCleaner:sServedViewField	Ljava/lang/reflect/Field;
    //   55: aload_2
    //   56: invokevirtual 86	java/lang/reflect/Field:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   59: checkcast 88	android/view/View
    //   62: astore_3
    //   63: aload_3
    //   64: ifnonnull +6 -> 70
    //   67: aload_1
    //   68: monitorexit
    //   69: return
    //   70: aload_3
    //   71: invokevirtual 92	android/view/View:isAttachedToWindow	()Z
    //   74: ifeq +6 -> 80
    //   77: aload_1
    //   78: monitorexit
    //   79: return
    //   80: getstatic 56	androidx/activity/ImmLeaksCleaner:sNextServedViewField	Ljava/lang/reflect/Field;
    //   83: aload_2
    //   84: aconst_null
    //   85: invokevirtual 96	java/lang/reflect/Field:set	(Ljava/lang/Object;Ljava/lang/Object;)V
    //   88: aload_1
    //   89: monitorexit
    //   90: aload_2
    //   91: invokevirtual 99	android/view/inputmethod/InputMethodManager:isActive	()Z
    //   94: pop
    //   95: goto +23 -> 118
    //   98: astore_2
    //   99: aload_1
    //   100: monitorexit
    //   101: return
    //   102: astore_2
    //   103: goto +11 -> 114
    //   106: astore_2
    //   107: aload_1
    //   108: monitorexit
    //   109: return
    //   110: astore_2
    //   111: aload_1
    //   112: monitorexit
    //   113: return
    //   114: aload_1
    //   115: monitorexit
    //   116: aload_2
    //   117: athrow
    //   118: return
    //   119: astore_1
    //   120: goto -2 -> 118
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	123	0	this	ImmLeaksCleaner
    //   0	123	1	paramLifecycleOwner	androidx.lifecycle.LifecycleOwner
    //   0	123	2	paramEvent	androidx.lifecycle.Lifecycle.Event
    //   62	9	3	localView	android.view.View
    // Exception table:
    //   from	to	target	type
    //   80	88	98	java/lang/IllegalAccessException
    //   52	63	102	finally
    //   67	69	102	finally
    //   70	79	102	finally
    //   80	88	102	finally
    //   88	90	102	finally
    //   99	101	102	finally
    //   107	109	102	finally
    //   111	113	102	finally
    //   114	116	102	finally
    //   52	63	106	java/lang/ClassCastException
    //   52	63	110	java/lang/IllegalAccessException
    //   37	45	119	java/lang/IllegalAccessException
  }
}

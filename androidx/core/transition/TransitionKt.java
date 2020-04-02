package androidx.core.transition;

import android.transition.Transition;
import android.transition.Transition.TransitionListener;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\013\032?\001\020\000\032\0020\001*\0020\0022#\b\006\020\003\032\035\022\023\022\0210\002?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\0042#\b\006\020\t\032\035\022\023\022\0210\002?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\0042#\b\006\020\n\032\035\022\023\022\0210\002?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\0042#\b\006\020\013\032\035\022\023\022\0210\002?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\0042#\b\006\020\f\032\035\022\023\022\0210\002?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H?\b\0322\020\r\032\0020\001*\0020\0022#\b\004\020\016\032\035\022\023\022\0210\002?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H?\b\0322\020\017\032\0020\001*\0020\0022#\b\004\020\016\032\035\022\023\022\0210\002?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H?\b\0322\020\020\032\0020\001*\0020\0022#\b\004\020\016\032\035\022\023\022\0210\002?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H?\b\0322\020\021\032\0020\001*\0020\0022#\b\004\020\016\032\035\022\023\022\0210\002?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H?\b\0322\020\022\032\0020\001*\0020\0022#\b\004\020\016\032\035\022\023\022\0210\002?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H?\b?\006\023"}, d2={"addListener", "Landroid/transition/Transition$TransitionListener;", "Landroid/transition/Transition;", "onEnd", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "transition", "", "onStart", "onCancel", "onResume", "onPause", "doOnCancel", "action", "doOnEnd", "doOnPause", "doOnResume", "doOnStart", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class TransitionKt
{
  public static final Transition.TransitionListener addListener(Transition paramTransition, Function1<? super Transition, Unit> paramFunction11, final Function1<? super Transition, Unit> paramFunction12, final Function1<? super Transition, Unit> paramFunction13, final Function1<? super Transition, Unit> paramFunction14, final Function1<? super Transition, Unit> paramFunction15)
  {
    Intrinsics.checkParameterIsNotNull(paramTransition, "$this$addListener");
    Intrinsics.checkParameterIsNotNull(paramFunction11, "onEnd");
    Intrinsics.checkParameterIsNotNull(paramFunction12, "onStart");
    Intrinsics.checkParameterIsNotNull(paramFunction13, "onCancel");
    Intrinsics.checkParameterIsNotNull(paramFunction14, "onResume");
    Intrinsics.checkParameterIsNotNull(paramFunction15, "onPause");
    paramFunction11 = (Transition.TransitionListener)new Transition.TransitionListener()
    {
      public void onTransitionCancel(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
        paramFunction13.invoke(paramAnonymousTransition);
      }
      
      public void onTransitionEnd(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
        this.$onEnd.invoke(paramAnonymousTransition);
      }
      
      public void onTransitionPause(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
        paramFunction15.invoke(paramAnonymousTransition);
      }
      
      public void onTransitionResume(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
        paramFunction14.invoke(paramAnonymousTransition);
      }
      
      public void onTransitionStart(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
        paramFunction12.invoke(paramAnonymousTransition);
      }
    };
    paramTransition.addListener(paramFunction11);
    return paramFunction11;
  }
  
  public static final Transition.TransitionListener doOnCancel(Transition paramTransition, Function1<? super Transition, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramTransition, "$this$doOnCancel");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    paramFunction1 = (Transition.TransitionListener)new Transition.TransitionListener()
    {
      public void onTransitionCancel(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
        this.$onCancel.invoke(paramAnonymousTransition);
      }
      
      public void onTransitionEnd(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionPause(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionResume(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionStart(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
      }
    };
    paramTransition.addListener(paramFunction1);
    return paramFunction1;
  }
  
  public static final Transition.TransitionListener doOnEnd(Transition paramTransition, Function1<? super Transition, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramTransition, "$this$doOnEnd");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    paramFunction1 = (Transition.TransitionListener)new Transition.TransitionListener()
    {
      public void onTransitionCancel(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionEnd(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
        this.$onEnd.invoke(paramAnonymousTransition);
      }
      
      public void onTransitionPause(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionResume(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionStart(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
      }
    };
    paramTransition.addListener(paramFunction1);
    return paramFunction1;
  }
  
  public static final Transition.TransitionListener doOnPause(Transition paramTransition, Function1<? super Transition, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramTransition, "$this$doOnPause");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    paramFunction1 = (Transition.TransitionListener)new Transition.TransitionListener()
    {
      public void onTransitionCancel(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionEnd(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionPause(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
        this.$onPause.invoke(paramAnonymousTransition);
      }
      
      public void onTransitionResume(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionStart(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
      }
    };
    paramTransition.addListener(paramFunction1);
    return paramFunction1;
  }
  
  public static final Transition.TransitionListener doOnResume(Transition paramTransition, Function1<? super Transition, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramTransition, "$this$doOnResume");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    paramFunction1 = (Transition.TransitionListener)new Transition.TransitionListener()
    {
      public void onTransitionCancel(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionEnd(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionPause(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionResume(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
        this.$onResume.invoke(paramAnonymousTransition);
      }
      
      public void onTransitionStart(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
      }
    };
    paramTransition.addListener(paramFunction1);
    return paramFunction1;
  }
  
  public static final Transition.TransitionListener doOnStart(Transition paramTransition, Function1<? super Transition, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramTransition, "$this$doOnStart");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    paramFunction1 = (Transition.TransitionListener)new Transition.TransitionListener()
    {
      public void onTransitionCancel(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionEnd(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionPause(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionResume(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionStart(Transition paramAnonymousTransition)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousTransition, "transition");
        this.$onStart.invoke(paramAnonymousTransition);
      }
    };
    paramTransition.addListener(paramFunction1);
    return paramFunction1;
  }
}

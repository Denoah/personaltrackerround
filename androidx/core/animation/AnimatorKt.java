package androidx.core.animation;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.Animator.AnimatorPauseListener;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(bv={1, 0, 3}, d1={"\000(\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\004\n\002\030\002\n\002\b\n\032?\001\020\000\032\0020\001*\0020\0022#\b\006\020\003\032\035\022\023\022\0210\002?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\0042#\b\006\020\t\032\035\022\023\022\0210\002?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\0042#\b\006\020\n\032\035\022\023\022\0210\002?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\0042#\b\006\020\013\032\035\022\023\022\0210\002?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H?\b\032W\020\f\032\0020\r*\0020\0022#\b\006\020\016\032\035\022\023\022\0210\002?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\0042#\b\006\020\017\032\035\022\023\022\0210\002?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H?\b\0322\020\020\032\0020\001*\0020\0022#\b\004\020\021\032\035\022\023\022\0210\002?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H?\b\0322\020\022\032\0020\001*\0020\0022#\b\004\020\021\032\035\022\023\022\0210\002?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H?\b\0322\020\023\032\0020\r*\0020\0022#\b\004\020\021\032\035\022\023\022\0210\002?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H?\b\0322\020\024\032\0020\001*\0020\0022#\b\004\020\021\032\035\022\023\022\0210\002?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H?\b\0322\020\025\032\0020\r*\0020\0022#\b\004\020\021\032\035\022\023\022\0210\002?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H?\b\0322\020\026\032\0020\001*\0020\0022#\b\004\020\021\032\035\022\023\022\0210\002?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H?\b?\006\027"}, d2={"addListener", "Landroid/animation/Animator$AnimatorListener;", "Landroid/animation/Animator;", "onEnd", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "animator", "", "onStart", "onCancel", "onRepeat", "addPauseListener", "Landroid/animation/Animator$AnimatorPauseListener;", "onResume", "onPause", "doOnCancel", "action", "doOnEnd", "doOnPause", "doOnRepeat", "doOnResume", "doOnStart", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class AnimatorKt
{
  public static final Animator.AnimatorListener addListener(Animator paramAnimator, final Function1<? super Animator, Unit> paramFunction11, final Function1<? super Animator, Unit> paramFunction12, final Function1<? super Animator, Unit> paramFunction13, Function1<? super Animator, Unit> paramFunction14)
  {
    Intrinsics.checkParameterIsNotNull(paramAnimator, "$this$addListener");
    Intrinsics.checkParameterIsNotNull(paramFunction11, "onEnd");
    Intrinsics.checkParameterIsNotNull(paramFunction12, "onStart");
    Intrinsics.checkParameterIsNotNull(paramFunction13, "onCancel");
    Intrinsics.checkParameterIsNotNull(paramFunction14, "onRepeat");
    paramFunction11 = (Animator.AnimatorListener)new Animator.AnimatorListener()
    {
      public void onAnimationCancel(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
        paramFunction13.invoke(paramAnonymousAnimator);
      }
      
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
        paramFunction11.invoke(paramAnonymousAnimator);
      }
      
      public void onAnimationRepeat(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
        this.$onRepeat.invoke(paramAnonymousAnimator);
      }
      
      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
        paramFunction12.invoke(paramAnonymousAnimator);
      }
    };
    paramAnimator.addListener(paramFunction11);
    return paramFunction11;
  }
  
  public static final Animator.AnimatorPauseListener addPauseListener(Animator paramAnimator, final Function1<? super Animator, Unit> paramFunction11, Function1<? super Animator, Unit> paramFunction12)
  {
    Intrinsics.checkParameterIsNotNull(paramAnimator, "$this$addPauseListener");
    Intrinsics.checkParameterIsNotNull(paramFunction11, "onResume");
    Intrinsics.checkParameterIsNotNull(paramFunction12, "onPause");
    paramFunction11 = (Animator.AnimatorPauseListener)new Animator.AnimatorPauseListener()
    {
      public void onAnimationPause(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
        this.$onPause.invoke(paramAnonymousAnimator);
      }
      
      public void onAnimationResume(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
        paramFunction11.invoke(paramAnonymousAnimator);
      }
    };
    paramAnimator.addPauseListener(paramFunction11);
    return paramFunction11;
  }
  
  public static final Animator.AnimatorListener doOnCancel(Animator paramAnimator, Function1<? super Animator, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramAnimator, "$this$doOnCancel");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    paramFunction1 = (Animator.AnimatorListener)new Animator.AnimatorListener()
    {
      public void onAnimationCancel(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
        this.$onCancel.invoke(paramAnonymousAnimator);
      }
      
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
      }
      
      public void onAnimationRepeat(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
      }
      
      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
      }
    };
    paramAnimator.addListener(paramFunction1);
    return paramFunction1;
  }
  
  public static final Animator.AnimatorListener doOnEnd(Animator paramAnimator, Function1<? super Animator, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramAnimator, "$this$doOnEnd");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    paramFunction1 = (Animator.AnimatorListener)new Animator.AnimatorListener()
    {
      public void onAnimationCancel(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
      }
      
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
        this.$onEnd.invoke(paramAnonymousAnimator);
      }
      
      public void onAnimationRepeat(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
      }
      
      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
      }
    };
    paramAnimator.addListener(paramFunction1);
    return paramFunction1;
  }
  
  public static final Animator.AnimatorPauseListener doOnPause(Animator paramAnimator, Function1<? super Animator, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramAnimator, "$this$doOnPause");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    paramFunction1 = (Animator.AnimatorPauseListener)new Animator.AnimatorPauseListener()
    {
      public void onAnimationPause(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
        this.$onPause.invoke(paramAnonymousAnimator);
      }
      
      public void onAnimationResume(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
      }
    };
    paramAnimator.addPauseListener(paramFunction1);
    return paramFunction1;
  }
  
  public static final Animator.AnimatorListener doOnRepeat(Animator paramAnimator, Function1<? super Animator, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramAnimator, "$this$doOnRepeat");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    paramFunction1 = (Animator.AnimatorListener)new Animator.AnimatorListener()
    {
      public void onAnimationCancel(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
      }
      
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
      }
      
      public void onAnimationRepeat(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
        this.$onRepeat.invoke(paramAnonymousAnimator);
      }
      
      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
      }
    };
    paramAnimator.addListener(paramFunction1);
    return paramFunction1;
  }
  
  public static final Animator.AnimatorPauseListener doOnResume(Animator paramAnimator, Function1<? super Animator, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramAnimator, "$this$doOnResume");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    paramFunction1 = (Animator.AnimatorPauseListener)new Animator.AnimatorPauseListener()
    {
      public void onAnimationPause(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
      }
      
      public void onAnimationResume(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
        this.$onResume.invoke(paramAnonymousAnimator);
      }
    };
    paramAnimator.addPauseListener(paramFunction1);
    return paramFunction1;
  }
  
  public static final Animator.AnimatorListener doOnStart(Animator paramAnimator, Function1<? super Animator, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramAnimator, "$this$doOnStart");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    paramFunction1 = (Animator.AnimatorListener)new Animator.AnimatorListener()
    {
      public void onAnimationCancel(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
      }
      
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
      }
      
      public void onAnimationRepeat(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
      }
      
      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnimator, "animator");
        this.$onStart.invoke(paramAnonymousAnimator);
      }
    };
    paramAnimator.addListener(paramFunction1);
    return paramFunction1;
  }
}

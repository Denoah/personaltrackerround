package androidx.core.view;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\\\n\000\n\002\020\013\n\000\n\002\030\002\n\002\b\b\n\002\020\b\n\002\b\r\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\002\b\006\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\t\n\002\030\002\n\002\b\005\n\002\030\002\n\000\n\002\030\002\n\002\b\n\0322\020\031\032\0020\032*\0020\0032#\b\004\020\033\032\035\022\023\022\0210\003?\006\f\b\035\022\b\b\036\022\004\b\b(\037\022\004\022\0020\0320\034H?\b\0322\020 \032\0020\032*\0020\0032#\b\004\020\033\032\035\022\023\022\0210\003?\006\f\b\035\022\b\b\036\022\004\b\b(\037\022\004\022\0020\0320\034H?\b\0322\020!\032\0020\032*\0020\0032#\b\004\020\033\032\035\022\023\022\0210\003?\006\f\b\035\022\b\b\036\022\004\b\b(\037\022\004\022\0020\0320\034H?\b\0322\020\"\032\0020\032*\0020\0032#\b\004\020\033\032\035\022\023\022\0210\003?\006\f\b\035\022\b\b\036\022\004\b\b(\037\022\004\022\0020\0320\034H?\b\0322\020#\032\0020$*\0020\0032#\b\004\020\033\032\035\022\023\022\0210\003?\006\f\b\035\022\b\b\036\022\004\b\b(\037\022\004\022\0020\0320\034H?\b\032\024\020%\032\0020&*\0020\0032\b\b\002\020'\032\0020(\032%\020)\032\0020**\0020\0032\006\020+\032\0020,2\016\b\004\020\033\032\b\022\004\022\0020\0320-H?\b\032%\020.\032\0020**\0020\0032\006\020+\032\0020,2\016\b\004\020\033\032\b\022\004\022\0020\0320-H?\b\032\027\020/\032\0020\032*\0020\0032\b\b\001\0200\032\0020\fH?\b\0327\0201\032\0020\032\"\n\b\000\0202\030\001*\00203*\0020\0032\027\0204\032\023\022\004\022\002H2\022\004\022\0020\0320\034?\006\002\b5H?\b?\006\002\b6\032&\0201\032\0020\032*\0020\0032\027\0204\032\023\022\004\022\00203\022\004\022\0020\0320\034?\006\002\b5H?\b\0325\0207\032\0020\032*\0020\0032\b\b\003\0208\032\0020\f2\b\b\003\0209\032\0020\f2\b\b\003\020:\032\0020\f2\b\b\003\020;\032\0020\fH?\b\0325\020<\032\0020\032*\0020\0032\b\b\003\020=\032\0020\f2\b\b\003\0209\032\0020\f2\b\b\003\020>\032\0020\f2\b\b\003\020;\032\0020\fH?\b\"*\020\002\032\0020\001*\0020\0032\006\020\000\032\0020\0018?\002@?\002X?\016?\006\f\032\004\b\002\020\004\"\004\b\005\020\006\"*\020\007\032\0020\001*\0020\0032\006\020\000\032\0020\0018?\002@?\002X?\016?\006\f\032\004\b\007\020\004\"\004\b\b\020\006\"*\020\t\032\0020\001*\0020\0032\006\020\000\032\0020\0018?\002@?\002X?\016?\006\f\032\004\b\t\020\004\"\004\b\n\020\006\"\026\020\013\032\0020\f*\0020\0038?\002?\006\006\032\004\b\r\020\016\"\026\020\017\032\0020\f*\0020\0038?\002?\006\006\032\004\b\020\020\016\"\026\020\021\032\0020\f*\0020\0038?\002?\006\006\032\004\b\022\020\016\"\026\020\023\032\0020\f*\0020\0038?\002?\006\006\032\004\b\024\020\016\"\026\020\025\032\0020\f*\0020\0038?\002?\006\006\032\004\b\026\020\016\"\026\020\027\032\0020\f*\0020\0038?\002?\006\006\032\004\b\030\020\016?\006?"}, d2={"value", "", "isGone", "Landroid/view/View;", "(Landroid/view/View;)Z", "setGone", "(Landroid/view/View;Z)V", "isInvisible", "setInvisible", "isVisible", "setVisible", "marginBottom", "", "getMarginBottom", "(Landroid/view/View;)I", "marginEnd", "getMarginEnd", "marginLeft", "getMarginLeft", "marginRight", "getMarginRight", "marginStart", "getMarginStart", "marginTop", "getMarginTop", "doOnAttach", "", "action", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "view", "doOnDetach", "doOnLayout", "doOnNextLayout", "doOnPreDraw", "Landroidx/core/view/OneShotPreDrawListener;", "drawToBitmap", "Landroid/graphics/Bitmap;", "config", "Landroid/graphics/Bitmap$Config;", "postDelayed", "Ljava/lang/Runnable;", "delayInMillis", "", "Lkotlin/Function0;", "postOnAnimationDelayed", "setPadding", "size", "updateLayoutParams", "T", "Landroid/view/ViewGroup$LayoutParams;", "block", "Lkotlin/ExtensionFunctionType;", "updateLayoutParamsTyped", "updatePadding", "left", "top", "right", "bottom", "updatePaddingRelative", "start", "end", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class ViewKt
{
  public static final void doOnAttach(View paramView, final Function1<? super View, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$doOnAttach");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    if (ViewCompat.isAttachedToWindow(paramView)) {
      paramFunction1.invoke(paramView);
    } else {
      paramView.addOnAttachStateChangeListener((View.OnAttachStateChangeListener)new View.OnAttachStateChangeListener()
      {
        public void onViewAttachedToWindow(View paramAnonymousView)
        {
          Intrinsics.checkParameterIsNotNull(paramAnonymousView, "view");
          this.$this_doOnAttach.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
          paramFunction1.invoke(paramAnonymousView);
        }
        
        public void onViewDetachedFromWindow(View paramAnonymousView)
        {
          Intrinsics.checkParameterIsNotNull(paramAnonymousView, "view");
        }
      });
    }
  }
  
  public static final void doOnDetach(View paramView, final Function1<? super View, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$doOnDetach");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    if (!ViewCompat.isAttachedToWindow(paramView)) {
      paramFunction1.invoke(paramView);
    } else {
      paramView.addOnAttachStateChangeListener((View.OnAttachStateChangeListener)new View.OnAttachStateChangeListener()
      {
        public void onViewAttachedToWindow(View paramAnonymousView)
        {
          Intrinsics.checkParameterIsNotNull(paramAnonymousView, "view");
        }
        
        public void onViewDetachedFromWindow(View paramAnonymousView)
        {
          Intrinsics.checkParameterIsNotNull(paramAnonymousView, "view");
          this.$this_doOnDetach.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
          paramFunction1.invoke(paramAnonymousView);
        }
      });
    }
  }
  
  public static final void doOnLayout(View paramView, Function1<? super View, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$doOnLayout");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    if ((ViewCompat.isLaidOut(paramView)) && (!paramView.isLayoutRequested())) {
      paramFunction1.invoke(paramView);
    } else {
      paramView.addOnLayoutChangeListener((View.OnLayoutChangeListener)new View.OnLayoutChangeListener()
      {
        public void onLayoutChange(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5, int paramAnonymousInt6, int paramAnonymousInt7, int paramAnonymousInt8)
        {
          Intrinsics.checkParameterIsNotNull(paramAnonymousView, "view");
          paramAnonymousView.removeOnLayoutChangeListener((View.OnLayoutChangeListener)this);
          this.$action$inlined.invoke(paramAnonymousView);
        }
      });
    }
  }
  
  public static final void doOnNextLayout(View paramView, Function1<? super View, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$doOnNextLayout");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    paramView.addOnLayoutChangeListener((View.OnLayoutChangeListener)new View.OnLayoutChangeListener()
    {
      public void onLayoutChange(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5, int paramAnonymousInt6, int paramAnonymousInt7, int paramAnonymousInt8)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousView, "view");
        paramAnonymousView.removeOnLayoutChangeListener((View.OnLayoutChangeListener)this);
        this.$action.invoke(paramAnonymousView);
      }
    });
  }
  
  public static final OneShotPreDrawListener doOnPreDraw(View paramView, final Function1<? super View, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$doOnPreDraw");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    paramView = OneShotPreDrawListener.add(paramView, (Runnable)new Runnable()
    {
      public final void run()
      {
        paramFunction1.invoke(this.$this_doOnPreDraw);
      }
    });
    Intrinsics.checkExpressionValueIsNotNull(paramView, "OneShotPreDrawListener.add(this) { action(this) }");
    return paramView;
  }
  
  public static final Bitmap drawToBitmap(View paramView, Bitmap.Config paramConfig)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$drawToBitmap");
    Intrinsics.checkParameterIsNotNull(paramConfig, "config");
    if (ViewCompat.isLaidOut(paramView))
    {
      paramConfig = Bitmap.createBitmap(paramView.getWidth(), paramView.getHeight(), paramConfig);
      Intrinsics.checkExpressionValueIsNotNull(paramConfig, "Bitmap.createBitmap(width, height, config)");
      Canvas localCanvas = new Canvas(paramConfig);
      localCanvas.translate(-paramView.getScrollX(), -paramView.getScrollY());
      paramView.draw(localCanvas);
      return paramConfig;
    }
    throw ((Throwable)new IllegalStateException("View needs to be laid out before calling drawToBitmap()"));
  }
  
  public static final int getMarginBottom(View paramView)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$marginBottom");
    ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
    paramView = localLayoutParams;
    if (!(localLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
      paramView = null;
    }
    paramView = (ViewGroup.MarginLayoutParams)paramView;
    int i;
    if (paramView != null) {
      i = paramView.bottomMargin;
    } else {
      i = 0;
    }
    return i;
  }
  
  public static final int getMarginEnd(View paramView)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$marginEnd");
    paramView = paramView.getLayoutParams();
    int i;
    if ((paramView instanceof ViewGroup.MarginLayoutParams)) {
      i = MarginLayoutParamsCompat.getMarginEnd((ViewGroup.MarginLayoutParams)paramView);
    } else {
      i = 0;
    }
    return i;
  }
  
  public static final int getMarginLeft(View paramView)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$marginLeft");
    ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
    paramView = localLayoutParams;
    if (!(localLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
      paramView = null;
    }
    paramView = (ViewGroup.MarginLayoutParams)paramView;
    int i;
    if (paramView != null) {
      i = paramView.leftMargin;
    } else {
      i = 0;
    }
    return i;
  }
  
  public static final int getMarginRight(View paramView)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$marginRight");
    ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
    paramView = localLayoutParams;
    if (!(localLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
      paramView = null;
    }
    paramView = (ViewGroup.MarginLayoutParams)paramView;
    int i;
    if (paramView != null) {
      i = paramView.rightMargin;
    } else {
      i = 0;
    }
    return i;
  }
  
  public static final int getMarginStart(View paramView)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$marginStart");
    paramView = paramView.getLayoutParams();
    int i;
    if ((paramView instanceof ViewGroup.MarginLayoutParams)) {
      i = MarginLayoutParamsCompat.getMarginStart((ViewGroup.MarginLayoutParams)paramView);
    } else {
      i = 0;
    }
    return i;
  }
  
  public static final int getMarginTop(View paramView)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$marginTop");
    ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
    paramView = localLayoutParams;
    if (!(localLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
      paramView = null;
    }
    paramView = (ViewGroup.MarginLayoutParams)paramView;
    int i;
    if (paramView != null) {
      i = paramView.topMargin;
    } else {
      i = 0;
    }
    return i;
  }
  
  public static final boolean isGone(View paramView)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$isGone");
    boolean bool;
    if (paramView.getVisibility() == 8) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean isInvisible(View paramView)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$isInvisible");
    boolean bool;
    if (paramView.getVisibility() == 4) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean isVisible(View paramView)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$isVisible");
    boolean bool;
    if (paramView.getVisibility() == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final Runnable postDelayed(View paramView, long paramLong, Function0<Unit> paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$postDelayed");
    Intrinsics.checkParameterIsNotNull(paramFunction0, "action");
    paramFunction0 = (Runnable)new Runnable()
    {
      public final void run()
      {
        this.$action.invoke();
      }
    };
    paramView.postDelayed(paramFunction0, paramLong);
    return paramFunction0;
  }
  
  public static final Runnable postOnAnimationDelayed(View paramView, long paramLong, Function0<Unit> paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$postOnAnimationDelayed");
    Intrinsics.checkParameterIsNotNull(paramFunction0, "action");
    paramFunction0 = (Runnable)new Runnable()
    {
      public final void run()
      {
        this.$action.invoke();
      }
    };
    paramView.postOnAnimationDelayed(paramFunction0, paramLong);
    return paramFunction0;
  }
  
  public static final void setGone(View paramView, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$isGone");
    int i;
    if (paramBoolean) {
      i = 8;
    } else {
      i = 0;
    }
    paramView.setVisibility(i);
  }
  
  public static final void setInvisible(View paramView, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$isInvisible");
    int i;
    if (paramBoolean) {
      i = 4;
    } else {
      i = 0;
    }
    paramView.setVisibility(i);
  }
  
  public static final void setPadding(View paramView, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$setPadding");
    paramView.setPadding(paramInt, paramInt, paramInt, paramInt);
  }
  
  public static final void setVisible(View paramView, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$isVisible");
    int i;
    if (paramBoolean) {
      i = 0;
    } else {
      i = 8;
    }
    paramView.setVisibility(i);
  }
  
  public static final void updateLayoutParams(View paramView, Function1<? super ViewGroup.LayoutParams, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$updateLayoutParams");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "block");
    ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
    if (localLayoutParams != null)
    {
      paramFunction1.invoke(localLayoutParams);
      paramView.setLayoutParams(localLayoutParams);
      return;
    }
    throw new TypeCastException("null cannot be cast to non-null type android.view.ViewGroup.LayoutParams");
  }
  
  public static final void updatePadding(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$updatePadding");
    paramView.setPadding(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public static final void updatePaddingRelative(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "$this$updatePaddingRelative");
    paramView.setPaddingRelative(paramInt1, paramInt2, paramInt3, paramInt4);
  }
}

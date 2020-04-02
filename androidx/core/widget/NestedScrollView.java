package androidx.core.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AnimationUtils;
import android.widget.EdgeEffect;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.OverScroller;
import android.widget.ScrollView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.NestedScrollingChild3;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ScrollingView;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;
import androidx.core.view.accessibility.AccessibilityRecordCompat;
import java.util.ArrayList;
import java.util.List;

public class NestedScrollView
  extends FrameLayout
  implements NestedScrollingParent3, NestedScrollingChild3, ScrollingView
{
  private static final AccessibilityDelegate ACCESSIBILITY_DELEGATE = new AccessibilityDelegate();
  static final int ANIMATED_SCROLL_GAP = 250;
  private static final int INVALID_POINTER = -1;
  static final float MAX_SCROLL_FACTOR = 0.5F;
  private static final int[] SCROLLVIEW_STYLEABLE = { 16843130 };
  private static final String TAG = "NestedScrollView";
  private int mActivePointerId = -1;
  private final NestedScrollingChildHelper mChildHelper;
  private View mChildToScrollTo = null;
  private EdgeEffect mEdgeGlowBottom;
  private EdgeEffect mEdgeGlowTop;
  private boolean mFillViewport;
  private boolean mIsBeingDragged = false;
  private boolean mIsLaidOut = false;
  private boolean mIsLayoutDirty = true;
  private int mLastMotionY;
  private long mLastScroll;
  private int mLastScrollerY;
  private int mMaximumVelocity;
  private int mMinimumVelocity;
  private int mNestedYOffset;
  private OnScrollChangeListener mOnScrollChangeListener;
  private final NestedScrollingParentHelper mParentHelper;
  private SavedState mSavedState;
  private final int[] mScrollConsumed = new int[2];
  private final int[] mScrollOffset = new int[2];
  private OverScroller mScroller;
  private boolean mSmoothScrollingEnabled = true;
  private final Rect mTempRect = new Rect();
  private int mTouchSlop;
  private VelocityTracker mVelocityTracker;
  private float mVerticalScrollFactor;
  
  public NestedScrollView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public NestedScrollView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public NestedScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initScrollView();
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, SCROLLVIEW_STYLEABLE, paramInt, 0);
    setFillViewport(paramContext.getBoolean(0, false));
    paramContext.recycle();
    this.mParentHelper = new NestedScrollingParentHelper(this);
    this.mChildHelper = new NestedScrollingChildHelper(this);
    setNestedScrollingEnabled(true);
    ViewCompat.setAccessibilityDelegate(this, ACCESSIBILITY_DELEGATE);
  }
  
  private void abortAnimatedScroll()
  {
    this.mScroller.abortAnimation();
    stopNestedScroll(1);
  }
  
  private boolean canScroll()
  {
    int i = getChildCount();
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (i > 0)
    {
      View localView = getChildAt(0);
      FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)localView.getLayoutParams();
      bool2 = bool1;
      if (localView.getHeight() + localLayoutParams.topMargin + localLayoutParams.bottomMargin > getHeight() - getPaddingTop() - getPaddingBottom()) {
        bool2 = true;
      }
    }
    return bool2;
  }
  
  private static int clamp(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt2 < paramInt3) && (paramInt1 >= 0))
    {
      if (paramInt2 + paramInt1 > paramInt3) {
        return paramInt3 - paramInt2;
      }
      return paramInt1;
    }
    return 0;
  }
  
  private void doScrollY(int paramInt)
  {
    if (paramInt != 0) {
      if (this.mSmoothScrollingEnabled) {
        smoothScrollBy(0, paramInt);
      } else {
        scrollBy(0, paramInt);
      }
    }
  }
  
  private void endDrag()
  {
    this.mIsBeingDragged = false;
    recycleVelocityTracker();
    stopNestedScroll(0);
    EdgeEffect localEdgeEffect = this.mEdgeGlowTop;
    if (localEdgeEffect != null)
    {
      localEdgeEffect.onRelease();
      this.mEdgeGlowBottom.onRelease();
    }
  }
  
  private void ensureGlows()
  {
    if (getOverScrollMode() != 2)
    {
      if (this.mEdgeGlowTop == null)
      {
        Context localContext = getContext();
        this.mEdgeGlowTop = new EdgeEffect(localContext);
        this.mEdgeGlowBottom = new EdgeEffect(localContext);
      }
    }
    else
    {
      this.mEdgeGlowTop = null;
      this.mEdgeGlowBottom = null;
    }
  }
  
  private View findFocusableViewInBounds(boolean paramBoolean, int paramInt1, int paramInt2)
  {
    ArrayList localArrayList = getFocusables(2);
    int i = localArrayList.size();
    Object localObject1 = null;
    int j = 0;
    int i1;
    for (int k = j; j < i; k = i1)
    {
      View localView = (View)localArrayList.get(j);
      int m = localView.getTop();
      int n = localView.getBottom();
      Object localObject2 = localObject1;
      i1 = k;
      if (paramInt1 < n)
      {
        localObject2 = localObject1;
        i1 = k;
        if (m < paramInt2)
        {
          int i2;
          if ((paramInt1 < m) && (n < paramInt2)) {
            i2 = 1;
          } else {
            i2 = 0;
          }
          if (localObject1 == null)
          {
            localObject2 = localView;
            i1 = i2;
          }
          else
          {
            if (((paramBoolean) && (m < localObject1.getTop())) || ((!paramBoolean) && (n > localObject1.getBottom()))) {
              n = 1;
            } else {
              n = 0;
            }
            if (k != 0)
            {
              localObject2 = localObject1;
              i1 = k;
              if (i2 == 0) {
                break label233;
              }
              localObject2 = localObject1;
              i1 = k;
              if (n == 0) {
                break label233;
              }
            }
            else
            {
              if (i2 != 0)
              {
                localObject2 = localView;
                i1 = 1;
                break label233;
              }
              localObject2 = localObject1;
              i1 = k;
              if (n == 0) {
                break label233;
              }
            }
            localObject2 = localView;
            i1 = k;
          }
        }
      }
      label233:
      j++;
      localObject1 = localObject2;
    }
    return localObject1;
  }
  
  private float getVerticalScrollFactorCompat()
  {
    if (this.mVerticalScrollFactor == 0.0F)
    {
      TypedValue localTypedValue = new TypedValue();
      Context localContext = getContext();
      if (localContext.getTheme().resolveAttribute(16842829, localTypedValue, true)) {
        this.mVerticalScrollFactor = localTypedValue.getDimension(localContext.getResources().getDisplayMetrics());
      } else {
        throw new IllegalStateException("Expected theme to define listPreferredItemHeight.");
      }
    }
    return this.mVerticalScrollFactor;
  }
  
  private boolean inChild(int paramInt1, int paramInt2)
  {
    int i = getChildCount();
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (i > 0)
    {
      i = getScrollY();
      View localView = getChildAt(0);
      bool2 = bool1;
      if (paramInt2 >= localView.getTop() - i)
      {
        bool2 = bool1;
        if (paramInt2 < localView.getBottom() - i)
        {
          bool2 = bool1;
          if (paramInt1 >= localView.getLeft())
          {
            bool2 = bool1;
            if (paramInt1 < localView.getRight()) {
              bool2 = true;
            }
          }
        }
      }
    }
    return bool2;
  }
  
  private void initOrResetVelocityTracker()
  {
    VelocityTracker localVelocityTracker = this.mVelocityTracker;
    if (localVelocityTracker == null) {
      this.mVelocityTracker = VelocityTracker.obtain();
    } else {
      localVelocityTracker.clear();
    }
  }
  
  private void initScrollView()
  {
    this.mScroller = new OverScroller(getContext());
    setFocusable(true);
    setDescendantFocusability(262144);
    setWillNotDraw(false);
    ViewConfiguration localViewConfiguration = ViewConfiguration.get(getContext());
    this.mTouchSlop = localViewConfiguration.getScaledTouchSlop();
    this.mMinimumVelocity = localViewConfiguration.getScaledMinimumFlingVelocity();
    this.mMaximumVelocity = localViewConfiguration.getScaledMaximumFlingVelocity();
  }
  
  private void initVelocityTrackerIfNotExists()
  {
    if (this.mVelocityTracker == null) {
      this.mVelocityTracker = VelocityTracker.obtain();
    }
  }
  
  private boolean isOffScreen(View paramView)
  {
    return isWithinDeltaOfScreen(paramView, 0, getHeight()) ^ true;
  }
  
  private static boolean isViewDescendantOf(View paramView1, View paramView2)
  {
    boolean bool = true;
    if (paramView1 == paramView2) {
      return true;
    }
    paramView1 = paramView1.getParent();
    if ((!(paramView1 instanceof ViewGroup)) || (!isViewDescendantOf((View)paramView1, paramView2))) {
      bool = false;
    }
    return bool;
  }
  
  private boolean isWithinDeltaOfScreen(View paramView, int paramInt1, int paramInt2)
  {
    paramView.getDrawingRect(this.mTempRect);
    offsetDescendantRectToMyCoords(paramView, this.mTempRect);
    boolean bool;
    if ((this.mTempRect.bottom + paramInt1 >= getScrollY()) && (this.mTempRect.top - paramInt1 <= getScrollY() + paramInt2)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void onNestedScrollInternal(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    int i = getScrollY();
    scrollBy(0, paramInt1);
    i = getScrollY() - i;
    if (paramArrayOfInt != null) {
      paramArrayOfInt[1] += i;
    }
    this.mChildHelper.dispatchNestedScroll(0, i, 0, paramInt1 - i, null, paramInt2, paramArrayOfInt);
  }
  
  private void onSecondaryPointerUp(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getActionIndex();
    if (paramMotionEvent.getPointerId(i) == this.mActivePointerId)
    {
      if (i == 0) {
        i = 1;
      } else {
        i = 0;
      }
      this.mLastMotionY = ((int)paramMotionEvent.getY(i));
      this.mActivePointerId = paramMotionEvent.getPointerId(i);
      paramMotionEvent = this.mVelocityTracker;
      if (paramMotionEvent != null) {
        paramMotionEvent.clear();
      }
    }
  }
  
  private void recycleVelocityTracker()
  {
    VelocityTracker localVelocityTracker = this.mVelocityTracker;
    if (localVelocityTracker != null)
    {
      localVelocityTracker.recycle();
      this.mVelocityTracker = null;
    }
  }
  
  private void runAnimatedScroll(boolean paramBoolean)
  {
    if (paramBoolean) {
      startNestedScroll(2, 1);
    } else {
      stopNestedScroll(1);
    }
    this.mLastScrollerY = getScrollY();
    ViewCompat.postInvalidateOnAnimation(this);
  }
  
  private boolean scrollAndFocus(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = getHeight();
    int j = getScrollY();
    i += j;
    boolean bool1 = false;
    boolean bool2;
    if (paramInt1 == 33) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    View localView = findFocusableViewInBounds(bool2, paramInt2, paramInt3);
    Object localObject = localView;
    if (localView == null) {
      localObject = this;
    }
    if ((paramInt2 >= j) && (paramInt3 <= i))
    {
      bool2 = bool1;
    }
    else
    {
      if (bool2) {
        paramInt2 -= j;
      } else {
        paramInt2 = paramInt3 - i;
      }
      doScrollY(paramInt2);
      bool2 = true;
    }
    if (localObject != findFocus()) {
      ((View)localObject).requestFocus(paramInt1);
    }
    return bool2;
  }
  
  private void scrollToChild(View paramView)
  {
    paramView.getDrawingRect(this.mTempRect);
    offsetDescendantRectToMyCoords(paramView, this.mTempRect);
    int i = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
    if (i != 0) {
      scrollBy(0, i);
    }
  }
  
  private boolean scrollToChildRect(Rect paramRect, boolean paramBoolean)
  {
    int i = computeScrollDeltaToGetChildRectOnScreen(paramRect);
    boolean bool;
    if (i != 0) {
      bool = true;
    } else {
      bool = false;
    }
    if (bool) {
      if (paramBoolean) {
        scrollBy(0, i);
      } else {
        smoothScrollBy(0, i);
      }
    }
    return bool;
  }
  
  private void smoothScrollBy(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (getChildCount() == 0) {
      return;
    }
    if (AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll > 250L)
    {
      View localView = getChildAt(0);
      FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)localView.getLayoutParams();
      int i = localView.getHeight();
      int j = localLayoutParams.topMargin;
      int k = localLayoutParams.bottomMargin;
      int m = getHeight();
      int n = getPaddingTop();
      int i1 = getPaddingBottom();
      paramInt1 = getScrollY();
      paramInt2 = Math.max(0, Math.min(paramInt2 + paramInt1, Math.max(0, i + j + k - (m - n - i1))));
      this.mScroller.startScroll(getScrollX(), paramInt1, 0, paramInt2 - paramInt1);
      runAnimatedScroll(paramBoolean);
    }
    else
    {
      if (!this.mScroller.isFinished()) {
        abortAnimatedScroll();
      }
      scrollBy(paramInt1, paramInt2);
    }
    this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
  }
  
  public void addView(View paramView)
  {
    if (getChildCount() <= 0)
    {
      super.addView(paramView);
      return;
    }
    throw new IllegalStateException("ScrollView can host only one direct child");
  }
  
  public void addView(View paramView, int paramInt)
  {
    if (getChildCount() <= 0)
    {
      super.addView(paramView, paramInt);
      return;
    }
    throw new IllegalStateException("ScrollView can host only one direct child");
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    if (getChildCount() <= 0)
    {
      super.addView(paramView, paramInt, paramLayoutParams);
      return;
    }
    throw new IllegalStateException("ScrollView can host only one direct child");
  }
  
  public void addView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    if (getChildCount() <= 0)
    {
      super.addView(paramView, paramLayoutParams);
      return;
    }
    throw new IllegalStateException("ScrollView can host only one direct child");
  }
  
  public boolean arrowScroll(int paramInt)
  {
    Object localObject1 = findFocus();
    Object localObject2 = localObject1;
    if (localObject1 == this) {
      localObject2 = null;
    }
    localObject1 = FocusFinder.getInstance().findNextFocus(this, localObject2, paramInt);
    int i = getMaxScrollAmount();
    if ((localObject1 != null) && (isWithinDeltaOfScreen((View)localObject1, i, getHeight())))
    {
      ((View)localObject1).getDrawingRect(this.mTempRect);
      offsetDescendantRectToMyCoords((View)localObject1, this.mTempRect);
      doScrollY(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
      ((View)localObject1).requestFocus(paramInt);
    }
    else
    {
      int j;
      if ((paramInt == 33) && (getScrollY() < i))
      {
        j = getScrollY();
      }
      else
      {
        j = i;
        if (paramInt == 130)
        {
          j = i;
          if (getChildCount() > 0)
          {
            View localView = getChildAt(0);
            localObject1 = (FrameLayout.LayoutParams)localView.getLayoutParams();
            j = Math.min(localView.getBottom() + ((FrameLayout.LayoutParams)localObject1).bottomMargin - (getScrollY() + getHeight() - getPaddingBottom()), i);
          }
        }
      }
      if (j == 0) {
        return false;
      }
      if (paramInt != 130) {
        j = -j;
      }
      doScrollY(j);
    }
    if ((localObject2 != null) && (localObject2.isFocused()) && (isOffScreen(localObject2)))
    {
      paramInt = getDescendantFocusability();
      setDescendantFocusability(131072);
      requestFocus();
      setDescendantFocusability(paramInt);
    }
    return true;
  }
  
  public int computeHorizontalScrollExtent()
  {
    return super.computeHorizontalScrollExtent();
  }
  
  public int computeHorizontalScrollOffset()
  {
    return super.computeHorizontalScrollOffset();
  }
  
  public int computeHorizontalScrollRange()
  {
    return super.computeHorizontalScrollRange();
  }
  
  public void computeScroll()
  {
    if (this.mScroller.isFinished()) {
      return;
    }
    this.mScroller.computeScrollOffset();
    int i = this.mScroller.getCurrY();
    int j = i - this.mLastScrollerY;
    this.mLastScrollerY = i;
    int[] arrayOfInt = this.mScrollConsumed;
    int k = 0;
    arrayOfInt[1] = 0;
    dispatchNestedPreScroll(0, j, arrayOfInt, null, 1);
    i = j - this.mScrollConsumed[1];
    int m = getScrollRange();
    j = i;
    if (i != 0)
    {
      j = getScrollY();
      overScrollByCompat(0, i, getScrollX(), j, 0, m, 0, 0, false);
      j = getScrollY() - j;
      i -= j;
      arrayOfInt = this.mScrollConsumed;
      arrayOfInt[1] = 0;
      dispatchNestedScroll(0, j, 0, i, this.mScrollOffset, 1, arrayOfInt);
      j = i - this.mScrollConsumed[1];
    }
    if (j != 0)
    {
      int n = getOverScrollMode();
      if (n != 0)
      {
        i = k;
        if (n == 1)
        {
          i = k;
          if (m <= 0) {}
        }
      }
      else
      {
        i = 1;
      }
      if (i != 0)
      {
        ensureGlows();
        if (j < 0)
        {
          if (this.mEdgeGlowTop.isFinished()) {
            this.mEdgeGlowTop.onAbsorb((int)this.mScroller.getCurrVelocity());
          }
        }
        else if (this.mEdgeGlowBottom.isFinished()) {
          this.mEdgeGlowBottom.onAbsorb((int)this.mScroller.getCurrVelocity());
        }
      }
      abortAnimatedScroll();
    }
    if (!this.mScroller.isFinished()) {
      ViewCompat.postInvalidateOnAnimation(this);
    } else {
      stopNestedScroll(1);
    }
  }
  
  protected int computeScrollDeltaToGetChildRectOnScreen(Rect paramRect)
  {
    int i = getChildCount();
    int j = 0;
    if (i == 0) {
      return 0;
    }
    int k = getHeight();
    i = getScrollY();
    int m = i + k;
    int n = getVerticalFadingEdgeLength();
    int i1 = i;
    if (paramRect.top > 0) {
      i1 = i + n;
    }
    View localView = getChildAt(0);
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)localView.getLayoutParams();
    if (paramRect.bottom < localView.getHeight() + localLayoutParams.topMargin + localLayoutParams.bottomMargin) {
      n = m - n;
    } else {
      n = m;
    }
    if ((paramRect.bottom > n) && (paramRect.top > i1))
    {
      if (paramRect.height() > k) {
        i = paramRect.top - i1;
      } else {
        i = paramRect.bottom - n;
      }
      i = Math.min(i + 0, localView.getBottom() + localLayoutParams.bottomMargin - m);
    }
    else
    {
      i = j;
      if (paramRect.top < i1)
      {
        i = j;
        if (paramRect.bottom < n)
        {
          if (paramRect.height() > k) {
            i = 0 - (n - paramRect.bottom);
          } else {
            i = 0 - (i1 - paramRect.top);
          }
          i = Math.max(i, -getScrollY());
        }
      }
    }
    return i;
  }
  
  public int computeVerticalScrollExtent()
  {
    return super.computeVerticalScrollExtent();
  }
  
  public int computeVerticalScrollOffset()
  {
    return Math.max(0, super.computeVerticalScrollOffset());
  }
  
  public int computeVerticalScrollRange()
  {
    int i = getChildCount();
    int j = getHeight() - getPaddingBottom() - getPaddingTop();
    if (i == 0) {
      return j;
    }
    View localView = getChildAt(0);
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)localView.getLayoutParams();
    i = localView.getBottom() + localLayoutParams.bottomMargin;
    int k = getScrollY();
    int m = Math.max(0, i - j);
    if (k < 0)
    {
      j = i - k;
    }
    else
    {
      j = i;
      if (k > m) {
        j = i + (k - m);
      }
    }
    return j;
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    boolean bool;
    if ((!super.dispatchKeyEvent(paramKeyEvent)) && (!executeKeyEvent(paramKeyEvent))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean dispatchNestedFling(float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    return this.mChildHelper.dispatchNestedFling(paramFloat1, paramFloat2, paramBoolean);
  }
  
  public boolean dispatchNestedPreFling(float paramFloat1, float paramFloat2)
  {
    return this.mChildHelper.dispatchNestedPreFling(paramFloat1, paramFloat2);
  }
  
  public boolean dispatchNestedPreScroll(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2, 0);
  }
  
  public boolean dispatchNestedPreScroll(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt3)
  {
    return this.mChildHelper.dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2, paramInt3);
  }
  
  public void dispatchNestedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt1, int paramInt5, int[] paramArrayOfInt2)
  {
    this.mChildHelper.dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt1, paramInt5, paramArrayOfInt2);
  }
  
  public boolean dispatchNestedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt)
  {
    return this.mChildHelper.dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt);
  }
  
  public boolean dispatchNestedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt, int paramInt5)
  {
    return this.mChildHelper.dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt, paramInt5);
  }
  
  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    if (this.mEdgeGlowTop != null)
    {
      int i = getScrollY();
      boolean bool = this.mEdgeGlowTop.isFinished();
      int j = 0;
      int k;
      int m;
      int n;
      int i1;
      int i2;
      int i3;
      int i4;
      if (!bool)
      {
        k = paramCanvas.save();
        m = getWidth();
        n = getHeight();
        i1 = Math.min(0, i);
        if ((Build.VERSION.SDK_INT >= 21) && (!getClipToPadding()))
        {
          i2 = 0;
        }
        else
        {
          m -= getPaddingLeft() + getPaddingRight();
          i2 = getPaddingLeft() + 0;
        }
        i3 = n;
        i4 = i1;
        if (Build.VERSION.SDK_INT >= 21)
        {
          i3 = n;
          i4 = i1;
          if (getClipToPadding())
          {
            i3 = n - (getPaddingTop() + getPaddingBottom());
            i4 = i1 + getPaddingTop();
          }
        }
        paramCanvas.translate(i2, i4);
        this.mEdgeGlowTop.setSize(m, i3);
        if (this.mEdgeGlowTop.draw(paramCanvas)) {
          ViewCompat.postInvalidateOnAnimation(this);
        }
        paramCanvas.restoreToCount(k);
      }
      if (!this.mEdgeGlowBottom.isFinished())
      {
        k = paramCanvas.save();
        i4 = getWidth();
        n = getHeight();
        i1 = Math.max(getScrollRange(), i) + n;
        if (Build.VERSION.SDK_INT >= 21)
        {
          m = j;
          i2 = i4;
          if (!getClipToPadding()) {}
        }
        else
        {
          i2 = i4 - (getPaddingLeft() + getPaddingRight());
          m = 0 + getPaddingLeft();
        }
        i3 = i1;
        i4 = n;
        if (Build.VERSION.SDK_INT >= 21)
        {
          i3 = i1;
          i4 = n;
          if (getClipToPadding())
          {
            i4 = n - (getPaddingTop() + getPaddingBottom());
            i3 = i1 - getPaddingBottom();
          }
        }
        paramCanvas.translate(m - i2, i3);
        paramCanvas.rotate(180.0F, i2, 0.0F);
        this.mEdgeGlowBottom.setSize(i2, i4);
        if (this.mEdgeGlowBottom.draw(paramCanvas)) {
          ViewCompat.postInvalidateOnAnimation(this);
        }
        paramCanvas.restoreToCount(k);
      }
    }
  }
  
  public boolean executeKeyEvent(KeyEvent paramKeyEvent)
  {
    this.mTempRect.setEmpty();
    boolean bool1 = canScroll();
    boolean bool2 = false;
    boolean bool3 = false;
    int i = 130;
    if (!bool1)
    {
      bool1 = bool3;
      if (isFocused())
      {
        bool1 = bool3;
        if (paramKeyEvent.getKeyCode() != 4)
        {
          View localView = findFocus();
          paramKeyEvent = localView;
          if (localView == this) {
            paramKeyEvent = null;
          }
          paramKeyEvent = FocusFinder.getInstance().findNextFocus(this, paramKeyEvent, 130);
          bool1 = bool3;
          if (paramKeyEvent != null)
          {
            bool1 = bool3;
            if (paramKeyEvent != this)
            {
              bool1 = bool3;
              if (paramKeyEvent.requestFocus(130)) {
                bool1 = true;
              }
            }
          }
        }
      }
      return bool1;
    }
    bool1 = bool2;
    if (paramKeyEvent.getAction() == 0)
    {
      int j = paramKeyEvent.getKeyCode();
      if (j != 19)
      {
        if (j != 20)
        {
          if (j != 62)
          {
            bool1 = bool2;
          }
          else
          {
            if (paramKeyEvent.isShiftPressed()) {
              i = 33;
            }
            pageScroll(i);
            bool1 = bool2;
          }
        }
        else if (!paramKeyEvent.isAltPressed()) {
          bool1 = arrowScroll(130);
        } else {
          bool1 = fullScroll(130);
        }
      }
      else if (!paramKeyEvent.isAltPressed()) {
        bool1 = arrowScroll(33);
      } else {
        bool1 = fullScroll(33);
      }
    }
    return bool1;
  }
  
  public void fling(int paramInt)
  {
    if (getChildCount() > 0)
    {
      this.mScroller.fling(getScrollX(), getScrollY(), 0, paramInt, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
      runAnimatedScroll(true);
    }
  }
  
  public boolean fullScroll(int paramInt)
  {
    int i;
    if (paramInt == 130) {
      i = 1;
    } else {
      i = 0;
    }
    int j = getHeight();
    this.mTempRect.top = 0;
    this.mTempRect.bottom = j;
    if (i != 0)
    {
      i = getChildCount();
      if (i > 0)
      {
        Object localObject = getChildAt(i - 1);
        FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)((View)localObject).getLayoutParams();
        this.mTempRect.bottom = (((View)localObject).getBottom() + localLayoutParams.bottomMargin + getPaddingBottom());
        localObject = this.mTempRect;
        ((Rect)localObject).top = (((Rect)localObject).bottom - j);
      }
    }
    return scrollAndFocus(paramInt, this.mTempRect.top, this.mTempRect.bottom);
  }
  
  protected float getBottomFadingEdgeStrength()
  {
    if (getChildCount() == 0) {
      return 0.0F;
    }
    View localView = getChildAt(0);
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)localView.getLayoutParams();
    int i = getVerticalFadingEdgeLength();
    int j = getHeight();
    int k = getPaddingBottom();
    j = localView.getBottom() + localLayoutParams.bottomMargin - getScrollY() - (j - k);
    if (j < i) {
      return j / i;
    }
    return 1.0F;
  }
  
  public int getMaxScrollAmount()
  {
    return (int)(getHeight() * 0.5F);
  }
  
  public int getNestedScrollAxes()
  {
    return this.mParentHelper.getNestedScrollAxes();
  }
  
  int getScrollRange()
  {
    int i = getChildCount();
    int j = 0;
    if (i > 0)
    {
      View localView = getChildAt(0);
      FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)localView.getLayoutParams();
      j = Math.max(0, localView.getHeight() + localLayoutParams.topMargin + localLayoutParams.bottomMargin - (getHeight() - getPaddingTop() - getPaddingBottom()));
    }
    return j;
  }
  
  protected float getTopFadingEdgeStrength()
  {
    if (getChildCount() == 0) {
      return 0.0F;
    }
    int i = getVerticalFadingEdgeLength();
    int j = getScrollY();
    if (j < i) {
      return j / i;
    }
    return 1.0F;
  }
  
  public boolean hasNestedScrollingParent()
  {
    return hasNestedScrollingParent(0);
  }
  
  public boolean hasNestedScrollingParent(int paramInt)
  {
    return this.mChildHelper.hasNestedScrollingParent(paramInt);
  }
  
  public boolean isFillViewport()
  {
    return this.mFillViewport;
  }
  
  public boolean isNestedScrollingEnabled()
  {
    return this.mChildHelper.isNestedScrollingEnabled();
  }
  
  public boolean isSmoothScrollingEnabled()
  {
    return this.mSmoothScrollingEnabled;
  }
  
  protected void measureChild(View paramView, int paramInt1, int paramInt2)
  {
    ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
    paramView.measure(getChildMeasureSpec(paramInt1, getPaddingLeft() + getPaddingRight(), localLayoutParams.width), View.MeasureSpec.makeMeasureSpec(0, 0));
  }
  
  protected void measureChildWithMargins(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)paramView.getLayoutParams();
    paramView.measure(getChildMeasureSpec(paramInt1, getPaddingLeft() + getPaddingRight() + localMarginLayoutParams.leftMargin + localMarginLayoutParams.rightMargin + paramInt2, localMarginLayoutParams.width), View.MeasureSpec.makeMeasureSpec(localMarginLayoutParams.topMargin + localMarginLayoutParams.bottomMargin, 0));
  }
  
  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mIsLaidOut = false;
  }
  
  public boolean onGenericMotionEvent(MotionEvent paramMotionEvent)
  {
    if (((paramMotionEvent.getSource() & 0x2) != 0) && (paramMotionEvent.getAction() == 8) && (!this.mIsBeingDragged))
    {
      float f = paramMotionEvent.getAxisValue(9);
      if (f != 0.0F)
      {
        int i = (int)(f * getVerticalScrollFactorCompat());
        int j = getScrollRange();
        int k = getScrollY();
        i = k - i;
        if (i < 0) {
          j = 0;
        } else if (i <= j) {
          j = i;
        }
        if (j != k)
        {
          super.scrollTo(getScrollX(), j);
          return true;
        }
      }
    }
    return false;
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getAction();
    if ((i == 2) && (this.mIsBeingDragged)) {
      return true;
    }
    i &= 0xFF;
    if (i != 0)
    {
      if (i != 1) {
        if (i != 2)
        {
          if (i != 3)
          {
            if (i != 6) {
              break label342;
            }
            onSecondaryPointerUp(paramMotionEvent);
            break label342;
          }
        }
        else
        {
          int j = this.mActivePointerId;
          if (j == -1) {
            break label342;
          }
          i = paramMotionEvent.findPointerIndex(j);
          if (i == -1)
          {
            paramMotionEvent = new StringBuilder();
            paramMotionEvent.append("Invalid pointerId=");
            paramMotionEvent.append(j);
            paramMotionEvent.append(" in onInterceptTouchEvent");
            Log.e("NestedScrollView", paramMotionEvent.toString());
            break label342;
          }
          i = (int)paramMotionEvent.getY(i);
          if ((Math.abs(i - this.mLastMotionY) <= this.mTouchSlop) || ((0x2 & getNestedScrollAxes()) != 0)) {
            break label342;
          }
          this.mIsBeingDragged = true;
          this.mLastMotionY = i;
          initVelocityTrackerIfNotExists();
          this.mVelocityTracker.addMovement(paramMotionEvent);
          this.mNestedYOffset = 0;
          paramMotionEvent = getParent();
          if (paramMotionEvent == null) {
            break label342;
          }
          paramMotionEvent.requestDisallowInterceptTouchEvent(true);
          break label342;
        }
      }
      this.mIsBeingDragged = false;
      this.mActivePointerId = -1;
      recycleVelocityTracker();
      if (this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
        ViewCompat.postInvalidateOnAnimation(this);
      }
      stopNestedScroll(0);
    }
    else
    {
      i = (int)paramMotionEvent.getY();
      if (!inChild((int)paramMotionEvent.getX(), i))
      {
        this.mIsBeingDragged = false;
        recycleVelocityTracker();
      }
      else
      {
        this.mLastMotionY = i;
        this.mActivePointerId = paramMotionEvent.getPointerId(0);
        initOrResetVelocityTracker();
        this.mVelocityTracker.addMovement(paramMotionEvent);
        this.mScroller.computeScrollOffset();
        this.mIsBeingDragged = (this.mScroller.isFinished() ^ true);
        startNestedScroll(2, 0);
      }
    }
    label342:
    return this.mIsBeingDragged;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    paramInt1 = 0;
    this.mIsLayoutDirty = false;
    Object localObject = this.mChildToScrollTo;
    if ((localObject != null) && (isViewDescendantOf((View)localObject, this))) {
      scrollToChild(this.mChildToScrollTo);
    }
    this.mChildToScrollTo = null;
    if (!this.mIsLaidOut)
    {
      if (this.mSavedState != null)
      {
        scrollTo(getScrollX(), this.mSavedState.scrollPosition);
        this.mSavedState = null;
      }
      if (getChildCount() > 0)
      {
        View localView = getChildAt(0);
        localObject = (FrameLayout.LayoutParams)localView.getLayoutParams();
        paramInt1 = localView.getMeasuredHeight() + ((FrameLayout.LayoutParams)localObject).topMargin + ((FrameLayout.LayoutParams)localObject).bottomMargin;
      }
      int i = getPaddingTop();
      int j = getPaddingBottom();
      paramInt3 = getScrollY();
      paramInt1 = clamp(paramInt3, paramInt4 - paramInt2 - i - j, paramInt1);
      if (paramInt1 != paramInt3) {
        scrollTo(getScrollX(), paramInt1);
      }
    }
    scrollTo(getScrollX(), getScrollY());
    this.mIsLaidOut = true;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    if (!this.mFillViewport) {
      return;
    }
    if (View.MeasureSpec.getMode(paramInt2) == 0) {
      return;
    }
    if (getChildCount() > 0)
    {
      View localView = getChildAt(0);
      FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)localView.getLayoutParams();
      int i = localView.getMeasuredHeight();
      paramInt2 = getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - localLayoutParams.topMargin - localLayoutParams.bottomMargin;
      if (i < paramInt2) {
        localView.measure(getChildMeasureSpec(paramInt1, getPaddingLeft() + getPaddingRight() + localLayoutParams.leftMargin + localLayoutParams.rightMargin, localLayoutParams.width), View.MeasureSpec.makeMeasureSpec(paramInt2, 1073741824));
      }
    }
  }
  
  public boolean onNestedFling(View paramView, float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      dispatchNestedFling(0.0F, paramFloat2, true);
      fling((int)paramFloat2);
      return true;
    }
    return false;
  }
  
  public boolean onNestedPreFling(View paramView, float paramFloat1, float paramFloat2)
  {
    return dispatchNestedPreFling(paramFloat1, paramFloat2);
  }
  
  public void onNestedPreScroll(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    onNestedPreScroll(paramView, paramInt1, paramInt2, paramArrayOfInt, 0);
  }
  
  public void onNestedPreScroll(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3)
  {
    dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfInt, null, paramInt3);
  }
  
  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    onNestedScrollInternal(paramInt4, 0, null);
  }
  
  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    onNestedScrollInternal(paramInt4, paramInt5, null);
  }
  
  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt)
  {
    onNestedScrollInternal(paramInt4, paramInt5, paramArrayOfInt);
  }
  
  public void onNestedScrollAccepted(View paramView1, View paramView2, int paramInt)
  {
    onNestedScrollAccepted(paramView1, paramView2, paramInt, 0);
  }
  
  public void onNestedScrollAccepted(View paramView1, View paramView2, int paramInt1, int paramInt2)
  {
    this.mParentHelper.onNestedScrollAccepted(paramView1, paramView2, paramInt1, paramInt2);
    startNestedScroll(2, paramInt2);
  }
  
  protected void onOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    super.scrollTo(paramInt1, paramInt2);
  }
  
  protected boolean onRequestFocusInDescendants(int paramInt, Rect paramRect)
  {
    int i;
    if (paramInt == 2)
    {
      i = 130;
    }
    else
    {
      i = paramInt;
      if (paramInt == 1) {
        i = 33;
      }
    }
    View localView;
    if (paramRect == null) {
      localView = FocusFinder.getInstance().findNextFocus(this, null, i);
    } else {
      localView = FocusFinder.getInstance().findNextFocusFromRect(this, paramRect, i);
    }
    if (localView == null) {
      return false;
    }
    if (isOffScreen(localView)) {
      return false;
    }
    return localView.requestFocus(i, paramRect);
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof SavedState))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    paramParcelable = (SavedState)paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    this.mSavedState = paramParcelable;
    requestLayout();
  }
  
  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    localSavedState.scrollPosition = getScrollY();
    return localSavedState;
  }
  
  protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    OnScrollChangeListener localOnScrollChangeListener = this.mOnScrollChangeListener;
    if (localOnScrollChangeListener != null) {
      localOnScrollChangeListener.onScrollChange(this, paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    View localView = findFocus();
    if ((localView != null) && (this != localView) && (isWithinDeltaOfScreen(localView, 0, paramInt4)))
    {
      localView.getDrawingRect(this.mTempRect);
      offsetDescendantRectToMyCoords(localView, this.mTempRect);
      doScrollY(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
    }
  }
  
  public boolean onStartNestedScroll(View paramView1, View paramView2, int paramInt)
  {
    return onStartNestedScroll(paramView1, paramView2, paramInt, 0);
  }
  
  public boolean onStartNestedScroll(View paramView1, View paramView2, int paramInt1, int paramInt2)
  {
    boolean bool;
    if ((paramInt1 & 0x2) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void onStopNestedScroll(View paramView)
  {
    onStopNestedScroll(paramView, 0);
  }
  
  public void onStopNestedScroll(View paramView, int paramInt)
  {
    this.mParentHelper.onStopNestedScroll(paramView, paramInt);
    stopNestedScroll(paramInt);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    initVelocityTrackerIfNotExists();
    int i = paramMotionEvent.getActionMasked();
    if (i == 0) {
      this.mNestedYOffset = 0;
    }
    MotionEvent localMotionEvent = MotionEvent.obtain(paramMotionEvent);
    localMotionEvent.offsetLocation(0.0F, this.mNestedYOffset);
    Object localObject;
    if (i != 0)
    {
      if (i != 1)
      {
        if (i != 2)
        {
          if (i != 3)
          {
            if (i != 5)
            {
              if (i == 6)
              {
                onSecondaryPointerUp(paramMotionEvent);
                this.mLastMotionY = ((int)paramMotionEvent.getY(paramMotionEvent.findPointerIndex(this.mActivePointerId)));
              }
            }
            else
            {
              i = paramMotionEvent.getActionIndex();
              this.mLastMotionY = ((int)paramMotionEvent.getY(i));
              this.mActivePointerId = paramMotionEvent.getPointerId(i);
            }
          }
          else
          {
            if ((this.mIsBeingDragged) && (getChildCount() > 0) && (this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange()))) {
              ViewCompat.postInvalidateOnAnimation(this);
            }
            this.mActivePointerId = -1;
            endDrag();
          }
        }
        else
        {
          int j = paramMotionEvent.findPointerIndex(this.mActivePointerId);
          if (j == -1)
          {
            paramMotionEvent = new StringBuilder();
            paramMotionEvent.append("Invalid pointerId=");
            paramMotionEvent.append(this.mActivePointerId);
            paramMotionEvent.append(" in onTouchEvent");
            Log.e("NestedScrollView", paramMotionEvent.toString());
          }
          else
          {
            int k = (int)paramMotionEvent.getY(j);
            int m = this.mLastMotionY - k;
            i = m;
            if (!this.mIsBeingDragged)
            {
              i = m;
              if (Math.abs(m) > this.mTouchSlop)
              {
                localObject = getParent();
                if (localObject != null) {
                  ((ViewParent)localObject).requestDisallowInterceptTouchEvent(true);
                }
                this.mIsBeingDragged = true;
                if (m > 0) {
                  i = m - this.mTouchSlop;
                } else {
                  i = m + this.mTouchSlop;
                }
              }
            }
            m = i;
            if (this.mIsBeingDragged)
            {
              i = m;
              if (dispatchNestedPreScroll(0, m, this.mScrollConsumed, this.mScrollOffset, 0))
              {
                i = m - this.mScrollConsumed[1];
                this.mNestedYOffset += this.mScrollOffset[1];
              }
              this.mLastMotionY = (k - this.mScrollOffset[1]);
              int n = getScrollY();
              k = getScrollRange();
              m = getOverScrollMode();
              if ((m != 0) && ((m != 1) || (k <= 0))) {
                m = 0;
              } else {
                m = 1;
              }
              if ((overScrollByCompat(0, i, 0, getScrollY(), 0, k, 0, 0, true)) && (!hasNestedScrollingParent(0))) {
                this.mVelocityTracker.clear();
              }
              int i1 = getScrollY() - n;
              localObject = this.mScrollConsumed;
              localObject[1] = 0;
              dispatchNestedScroll(0, i1, 0, i - i1, this.mScrollOffset, 0, (int[])localObject);
              i1 = this.mLastMotionY;
              localObject = this.mScrollOffset;
              this.mLastMotionY = (i1 - localObject[1]);
              this.mNestedYOffset += localObject[1];
              if (m != 0)
              {
                i -= this.mScrollConsumed[1];
                ensureGlows();
                m = n + i;
                if (m < 0)
                {
                  EdgeEffectCompat.onPull(this.mEdgeGlowTop, i / getHeight(), paramMotionEvent.getX(j) / getWidth());
                  if (!this.mEdgeGlowBottom.isFinished()) {
                    this.mEdgeGlowBottom.onRelease();
                  }
                }
                else if (m > k)
                {
                  EdgeEffectCompat.onPull(this.mEdgeGlowBottom, i / getHeight(), 1.0F - paramMotionEvent.getX(j) / getWidth());
                  if (!this.mEdgeGlowTop.isFinished()) {
                    this.mEdgeGlowTop.onRelease();
                  }
                }
                paramMotionEvent = this.mEdgeGlowTop;
                if ((paramMotionEvent != null) && ((!paramMotionEvent.isFinished()) || (!this.mEdgeGlowBottom.isFinished()))) {
                  ViewCompat.postInvalidateOnAnimation(this);
                }
              }
            }
          }
        }
      }
      else
      {
        paramMotionEvent = this.mVelocityTracker;
        paramMotionEvent.computeCurrentVelocity(1000, this.mMaximumVelocity);
        i = (int)paramMotionEvent.getYVelocity(this.mActivePointerId);
        if (Math.abs(i) >= this.mMinimumVelocity)
        {
          i = -i;
          float f = i;
          if (!dispatchNestedPreFling(0.0F, f))
          {
            dispatchNestedFling(0.0F, f, true);
            fling(i);
          }
        }
        else if (this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange()))
        {
          ViewCompat.postInvalidateOnAnimation(this);
        }
        this.mActivePointerId = -1;
        endDrag();
      }
    }
    else
    {
      if (getChildCount() == 0) {
        return false;
      }
      boolean bool = this.mScroller.isFinished() ^ true;
      this.mIsBeingDragged = bool;
      if (bool)
      {
        localObject = getParent();
        if (localObject != null) {
          ((ViewParent)localObject).requestDisallowInterceptTouchEvent(true);
        }
      }
      if (!this.mScroller.isFinished()) {
        abortAnimatedScroll();
      }
      this.mLastMotionY = ((int)paramMotionEvent.getY());
      this.mActivePointerId = paramMotionEvent.getPointerId(0);
      startNestedScroll(2, 0);
    }
    paramMotionEvent = this.mVelocityTracker;
    if (paramMotionEvent != null) {
      paramMotionEvent.addMovement(localMotionEvent);
    }
    localMotionEvent.recycle();
    return true;
  }
  
  boolean overScrollByCompat(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean)
  {
    int i = getOverScrollMode();
    int j = computeHorizontalScrollRange();
    int k = computeHorizontalScrollExtent();
    boolean bool1 = false;
    if (j > k) {
      j = 1;
    } else {
      j = 0;
    }
    if (computeVerticalScrollRange() > computeVerticalScrollExtent()) {
      k = 1;
    } else {
      k = 0;
    }
    if ((i != 0) && ((i != 1) || (j == 0))) {
      j = 0;
    } else {
      j = 1;
    }
    if ((i != 0) && ((i != 1) || (k == 0))) {
      k = 0;
    } else {
      k = 1;
    }
    paramInt3 += paramInt1;
    if (j == 0) {
      paramInt1 = 0;
    } else {
      paramInt1 = paramInt7;
    }
    paramInt4 += paramInt2;
    if (k == 0) {
      paramInt2 = 0;
    } else {
      paramInt2 = paramInt8;
    }
    paramInt7 = -paramInt1;
    paramInt1 += paramInt5;
    paramInt5 = -paramInt2;
    paramInt2 += paramInt6;
    if (paramInt3 > paramInt1)
    {
      paramBoolean = true;
    }
    else if (paramInt3 < paramInt7)
    {
      paramBoolean = true;
      paramInt1 = paramInt7;
    }
    else
    {
      paramBoolean = false;
      paramInt1 = paramInt3;
    }
    boolean bool2;
    if (paramInt4 > paramInt2)
    {
      bool2 = true;
    }
    else if (paramInt4 < paramInt5)
    {
      bool2 = true;
      paramInt2 = paramInt5;
    }
    else
    {
      bool2 = false;
      paramInt2 = paramInt4;
    }
    if ((bool2) && (!hasNestedScrollingParent(1))) {
      this.mScroller.springBack(paramInt1, paramInt2, 0, 0, 0, getScrollRange());
    }
    onOverScrolled(paramInt1, paramInt2, paramBoolean, bool2);
    if (!paramBoolean)
    {
      paramBoolean = bool1;
      if (!bool2) {}
    }
    else
    {
      paramBoolean = true;
    }
    return paramBoolean;
  }
  
  public boolean pageScroll(int paramInt)
  {
    int i;
    if (paramInt == 130) {
      i = 1;
    } else {
      i = 0;
    }
    int j = getHeight();
    if (i != 0)
    {
      this.mTempRect.top = (getScrollY() + j);
      i = getChildCount();
      if (i > 0)
      {
        View localView = getChildAt(i - 1);
        localObject = (FrameLayout.LayoutParams)localView.getLayoutParams();
        i = localView.getBottom() + ((FrameLayout.LayoutParams)localObject).bottomMargin + getPaddingBottom();
        if (this.mTempRect.top + j > i) {
          this.mTempRect.top = (i - j);
        }
      }
    }
    else
    {
      this.mTempRect.top = (getScrollY() - j);
      if (this.mTempRect.top < 0) {
        this.mTempRect.top = 0;
      }
    }
    Object localObject = this.mTempRect;
    ((Rect)localObject).bottom = (((Rect)localObject).top + j);
    return scrollAndFocus(paramInt, this.mTempRect.top, this.mTempRect.bottom);
  }
  
  public void requestChildFocus(View paramView1, View paramView2)
  {
    if (!this.mIsLayoutDirty) {
      scrollToChild(paramView2);
    } else {
      this.mChildToScrollTo = paramView2;
    }
    super.requestChildFocus(paramView1, paramView2);
  }
  
  public boolean requestChildRectangleOnScreen(View paramView, Rect paramRect, boolean paramBoolean)
  {
    paramRect.offset(paramView.getLeft() - paramView.getScrollX(), paramView.getTop() - paramView.getScrollY());
    return scrollToChildRect(paramRect, paramBoolean);
  }
  
  public void requestDisallowInterceptTouchEvent(boolean paramBoolean)
  {
    if (paramBoolean) {
      recycleVelocityTracker();
    }
    super.requestDisallowInterceptTouchEvent(paramBoolean);
  }
  
  public void requestLayout()
  {
    this.mIsLayoutDirty = true;
    super.requestLayout();
  }
  
  public void scrollTo(int paramInt1, int paramInt2)
  {
    if (getChildCount() > 0)
    {
      View localView = getChildAt(0);
      FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)localView.getLayoutParams();
      int i = getWidth();
      int j = getPaddingLeft();
      int k = getPaddingRight();
      int m = localView.getWidth();
      int n = localLayoutParams.leftMargin;
      int i1 = localLayoutParams.rightMargin;
      int i2 = getHeight();
      int i3 = getPaddingTop();
      int i4 = getPaddingBottom();
      int i5 = localView.getHeight();
      int i6 = localLayoutParams.topMargin;
      int i7 = localLayoutParams.bottomMargin;
      paramInt1 = clamp(paramInt1, i - j - k, m + n + i1);
      paramInt2 = clamp(paramInt2, i2 - i3 - i4, i5 + i6 + i7);
      if ((paramInt1 != getScrollX()) || (paramInt2 != getScrollY())) {
        super.scrollTo(paramInt1, paramInt2);
      }
    }
  }
  
  public void setFillViewport(boolean paramBoolean)
  {
    if (paramBoolean != this.mFillViewport)
    {
      this.mFillViewport = paramBoolean;
      requestLayout();
    }
  }
  
  public void setNestedScrollingEnabled(boolean paramBoolean)
  {
    this.mChildHelper.setNestedScrollingEnabled(paramBoolean);
  }
  
  public void setOnScrollChangeListener(OnScrollChangeListener paramOnScrollChangeListener)
  {
    this.mOnScrollChangeListener = paramOnScrollChangeListener;
  }
  
  public void setSmoothScrollingEnabled(boolean paramBoolean)
  {
    this.mSmoothScrollingEnabled = paramBoolean;
  }
  
  public boolean shouldDelayChildPressedState()
  {
    return true;
  }
  
  public final void smoothScrollBy(int paramInt1, int paramInt2)
  {
    smoothScrollBy(paramInt1, paramInt2, false);
  }
  
  public final void smoothScrollTo(int paramInt1, int paramInt2)
  {
    smoothScrollTo(paramInt1, paramInt2, false);
  }
  
  void smoothScrollTo(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    smoothScrollBy(paramInt1 - getScrollX(), paramInt2 - getScrollY(), paramBoolean);
  }
  
  public boolean startNestedScroll(int paramInt)
  {
    return startNestedScroll(paramInt, 0);
  }
  
  public boolean startNestedScroll(int paramInt1, int paramInt2)
  {
    return this.mChildHelper.startNestedScroll(paramInt1, paramInt2);
  }
  
  public void stopNestedScroll()
  {
    stopNestedScroll(0);
  }
  
  public void stopNestedScroll(int paramInt)
  {
    this.mChildHelper.stopNestedScroll(paramInt);
  }
  
  static class AccessibilityDelegate
    extends AccessibilityDelegateCompat
  {
    AccessibilityDelegate() {}
    
    public void onInitializeAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent)
    {
      super.onInitializeAccessibilityEvent(paramView, paramAccessibilityEvent);
      paramView = (NestedScrollView)paramView;
      paramAccessibilityEvent.setClassName(ScrollView.class.getName());
      boolean bool;
      if (paramView.getScrollRange() > 0) {
        bool = true;
      } else {
        bool = false;
      }
      paramAccessibilityEvent.setScrollable(bool);
      paramAccessibilityEvent.setScrollX(paramView.getScrollX());
      paramAccessibilityEvent.setScrollY(paramView.getScrollY());
      AccessibilityRecordCompat.setMaxScrollX(paramAccessibilityEvent, paramView.getScrollX());
      AccessibilityRecordCompat.setMaxScrollY(paramAccessibilityEvent, paramView.getScrollRange());
    }
    
    public void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      super.onInitializeAccessibilityNodeInfo(paramView, paramAccessibilityNodeInfoCompat);
      paramView = (NestedScrollView)paramView;
      paramAccessibilityNodeInfoCompat.setClassName(ScrollView.class.getName());
      if (paramView.isEnabled())
      {
        int i = paramView.getScrollRange();
        if (i > 0)
        {
          paramAccessibilityNodeInfoCompat.setScrollable(true);
          if (paramView.getScrollY() > 0)
          {
            paramAccessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD);
            paramAccessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP);
          }
          if (paramView.getScrollY() < i)
          {
            paramAccessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD);
            paramAccessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN);
          }
        }
      }
    }
    
    public boolean performAccessibilityAction(View paramView, int paramInt, Bundle paramBundle)
    {
      if (super.performAccessibilityAction(paramView, paramInt, paramBundle)) {
        return true;
      }
      paramView = (NestedScrollView)paramView;
      if (!paramView.isEnabled()) {
        return false;
      }
      if (paramInt != 4096) {
        if ((paramInt != 8192) && (paramInt != 16908344))
        {
          if (paramInt != 16908346) {
            return false;
          }
        }
        else
        {
          i = paramView.getHeight();
          j = paramView.getPaddingBottom();
          paramInt = paramView.getPaddingTop();
          paramInt = Math.max(paramView.getScrollY() - (i - j - paramInt), 0);
          if (paramInt != paramView.getScrollY())
          {
            paramView.smoothScrollTo(0, paramInt, true);
            return true;
          }
          return false;
        }
      }
      int i = paramView.getHeight();
      paramInt = paramView.getPaddingBottom();
      int j = paramView.getPaddingTop();
      paramInt = Math.min(paramView.getScrollY() + (i - paramInt - j), paramView.getScrollRange());
      if (paramInt != paramView.getScrollY())
      {
        paramView.smoothScrollTo(0, paramInt, true);
        return true;
      }
      return false;
    }
  }
  
  public static abstract interface OnScrollChangeListener
  {
    public abstract void onScrollChange(NestedScrollView paramNestedScrollView, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  }
  
  static class SavedState
    extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public NestedScrollView.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new NestedScrollView.SavedState(paramAnonymousParcel);
      }
      
      public NestedScrollView.SavedState[] newArray(int paramAnonymousInt)
      {
        return new NestedScrollView.SavedState[paramAnonymousInt];
      }
    };
    public int scrollPosition;
    
    SavedState(Parcel paramParcel)
    {
      super();
      this.scrollPosition = paramParcel.readInt();
    }
    
    SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("HorizontalScrollView.SavedState{");
      localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
      localStringBuilder.append(" scrollPosition=");
      localStringBuilder.append(this.scrollPosition);
      localStringBuilder.append("}");
      return localStringBuilder.toString();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.scrollPosition);
    }
  }
}

package androidx.core.view;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public final class GestureDetectorCompat
{
  private final GestureDetectorCompatImpl mImpl;
  
  public GestureDetectorCompat(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener)
  {
    this(paramContext, paramOnGestureListener, null);
  }
  
  public GestureDetectorCompat(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener, Handler paramHandler)
  {
    if (Build.VERSION.SDK_INT > 17) {
      this.mImpl = new GestureDetectorCompatImplJellybeanMr2(paramContext, paramOnGestureListener, paramHandler);
    } else {
      this.mImpl = new GestureDetectorCompatImplBase(paramContext, paramOnGestureListener, paramHandler);
    }
  }
  
  public boolean isLongpressEnabled()
  {
    return this.mImpl.isLongpressEnabled();
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    return this.mImpl.onTouchEvent(paramMotionEvent);
  }
  
  public void setIsLongpressEnabled(boolean paramBoolean)
  {
    this.mImpl.setIsLongpressEnabled(paramBoolean);
  }
  
  public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener)
  {
    this.mImpl.setOnDoubleTapListener(paramOnDoubleTapListener);
  }
  
  static abstract interface GestureDetectorCompatImpl
  {
    public abstract boolean isLongpressEnabled();
    
    public abstract boolean onTouchEvent(MotionEvent paramMotionEvent);
    
    public abstract void setIsLongpressEnabled(boolean paramBoolean);
    
    public abstract void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener);
  }
  
  static class GestureDetectorCompatImplBase
    implements GestureDetectorCompat.GestureDetectorCompatImpl
  {
    private static final int DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
    private static final int LONGPRESS_TIMEOUT = ;
    private static final int LONG_PRESS = 2;
    private static final int SHOW_PRESS = 1;
    private static final int TAP = 3;
    private static final int TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
    private boolean mAlwaysInBiggerTapRegion;
    private boolean mAlwaysInTapRegion;
    MotionEvent mCurrentDownEvent;
    boolean mDeferConfirmSingleTap;
    GestureDetector.OnDoubleTapListener mDoubleTapListener;
    private int mDoubleTapSlopSquare;
    private float mDownFocusX;
    private float mDownFocusY;
    private final Handler mHandler;
    private boolean mInLongPress;
    private boolean mIsDoubleTapping;
    private boolean mIsLongpressEnabled;
    private float mLastFocusX;
    private float mLastFocusY;
    final GestureDetector.OnGestureListener mListener;
    private int mMaximumFlingVelocity;
    private int mMinimumFlingVelocity;
    private MotionEvent mPreviousUpEvent;
    boolean mStillDown;
    private int mTouchSlopSquare;
    private VelocityTracker mVelocityTracker;
    
    GestureDetectorCompatImplBase(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener, Handler paramHandler)
    {
      if (paramHandler != null) {
        this.mHandler = new GestureHandler(paramHandler);
      } else {
        this.mHandler = new GestureHandler();
      }
      this.mListener = paramOnGestureListener;
      if ((paramOnGestureListener instanceof GestureDetector.OnDoubleTapListener)) {
        setOnDoubleTapListener((GestureDetector.OnDoubleTapListener)paramOnGestureListener);
      }
      init(paramContext);
    }
    
    private void cancel()
    {
      this.mHandler.removeMessages(1);
      this.mHandler.removeMessages(2);
      this.mHandler.removeMessages(3);
      this.mVelocityTracker.recycle();
      this.mVelocityTracker = null;
      this.mIsDoubleTapping = false;
      this.mStillDown = false;
      this.mAlwaysInTapRegion = false;
      this.mAlwaysInBiggerTapRegion = false;
      this.mDeferConfirmSingleTap = false;
      if (this.mInLongPress) {
        this.mInLongPress = false;
      }
    }
    
    private void cancelTaps()
    {
      this.mHandler.removeMessages(1);
      this.mHandler.removeMessages(2);
      this.mHandler.removeMessages(3);
      this.mIsDoubleTapping = false;
      this.mAlwaysInTapRegion = false;
      this.mAlwaysInBiggerTapRegion = false;
      this.mDeferConfirmSingleTap = false;
      if (this.mInLongPress) {
        this.mInLongPress = false;
      }
    }
    
    private void init(Context paramContext)
    {
      if (paramContext != null)
      {
        if (this.mListener != null)
        {
          this.mIsLongpressEnabled = true;
          paramContext = ViewConfiguration.get(paramContext);
          int i = paramContext.getScaledTouchSlop();
          int j = paramContext.getScaledDoubleTapSlop();
          this.mMinimumFlingVelocity = paramContext.getScaledMinimumFlingVelocity();
          this.mMaximumFlingVelocity = paramContext.getScaledMaximumFlingVelocity();
          this.mTouchSlopSquare = (i * i);
          this.mDoubleTapSlopSquare = (j * j);
          return;
        }
        throw new IllegalArgumentException("OnGestureListener must not be null");
      }
      throw new IllegalArgumentException("Context must not be null");
    }
    
    private boolean isConsideredDoubleTap(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, MotionEvent paramMotionEvent3)
    {
      boolean bool1 = this.mAlwaysInBiggerTapRegion;
      boolean bool2 = false;
      if (!bool1) {
        return false;
      }
      if (paramMotionEvent3.getEventTime() - paramMotionEvent2.getEventTime() > DOUBLE_TAP_TIMEOUT) {
        return false;
      }
      int i = (int)paramMotionEvent1.getX() - (int)paramMotionEvent3.getX();
      int j = (int)paramMotionEvent1.getY() - (int)paramMotionEvent3.getY();
      if (i * i + j * j < this.mDoubleTapSlopSquare) {
        bool2 = true;
      }
      return bool2;
    }
    
    void dispatchLongPress()
    {
      this.mHandler.removeMessages(3);
      this.mDeferConfirmSingleTap = false;
      this.mInLongPress = true;
      this.mListener.onLongPress(this.mCurrentDownEvent);
    }
    
    public boolean isLongpressEnabled()
    {
      return this.mIsLongpressEnabled;
    }
    
    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      int i = paramMotionEvent.getAction();
      if (this.mVelocityTracker == null) {
        this.mVelocityTracker = VelocityTracker.obtain();
      }
      this.mVelocityTracker.addMovement(paramMotionEvent);
      int j = i & 0xFF;
      boolean bool2 = false;
      if (j == 6) {
        i = 1;
      } else {
        i = 0;
      }
      int k;
      if (i != 0) {
        k = paramMotionEvent.getActionIndex();
      } else {
        k = -1;
      }
      int m = paramMotionEvent.getPointerCount();
      int n = 0;
      float f1 = 0.0F;
      float f2 = f1;
      while (n < m)
      {
        if (k != n)
        {
          f1 += paramMotionEvent.getX(n);
          f2 += paramMotionEvent.getY(n);
        }
        n++;
      }
      if (i != 0) {
        i = m - 1;
      } else {
        i = m;
      }
      float f3 = i;
      f1 /= f3;
      f2 /= f3;
      boolean bool3;
      MotionEvent localMotionEvent;
      Object localObject;
      if (j != 0)
      {
        if (j != 1)
        {
          if (j != 2)
          {
            if (j != 3)
            {
              if (j != 5)
              {
                if (j != 6)
                {
                  bool3 = bool2;
                  break label1181;
                }
                this.mLastFocusX = f1;
                this.mDownFocusX = f1;
                this.mLastFocusY = f2;
                this.mDownFocusY = f2;
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
                k = paramMotionEvent.getActionIndex();
                i = paramMotionEvent.getPointerId(k);
                f1 = this.mVelocityTracker.getXVelocity(i);
                f2 = this.mVelocityTracker.getYVelocity(i);
                for (i = 0;; i++)
                {
                  bool3 = bool2;
                  if (i >= m) {
                    break;
                  }
                  if (i != k)
                  {
                    n = paramMotionEvent.getPointerId(i);
                    if (this.mVelocityTracker.getXVelocity(n) * f1 + this.mVelocityTracker.getYVelocity(n) * f2 < 0.0F)
                    {
                      this.mVelocityTracker.clear();
                      bool3 = bool2;
                      break;
                    }
                  }
                }
              }
              this.mLastFocusX = f1;
              this.mDownFocusX = f1;
              this.mLastFocusY = f2;
              this.mDownFocusY = f2;
              cancelTaps();
              bool3 = bool2;
              break label1181;
            }
            cancel();
            bool3 = bool2;
            break label1181;
          }
          if (this.mInLongPress)
          {
            bool3 = bool2;
            break label1181;
          }
          float f4 = this.mLastFocusX - f1;
          f3 = this.mLastFocusY - f2;
          if (this.mIsDoubleTapping)
          {
            bool3 = false | this.mDoubleTapListener.onDoubleTapEvent(paramMotionEvent);
            break label1181;
          }
          if (this.mAlwaysInTapRegion)
          {
            i = (int)(f1 - this.mDownFocusX);
            k = (int)(f2 - this.mDownFocusY);
            i = i * i + k * k;
            if (i > this.mTouchSlopSquare)
            {
              bool2 = this.mListener.onScroll(this.mCurrentDownEvent, paramMotionEvent, f4, f3);
              this.mLastFocusX = f1;
              this.mLastFocusY = f2;
              this.mAlwaysInTapRegion = false;
              this.mHandler.removeMessages(3);
              this.mHandler.removeMessages(1);
              this.mHandler.removeMessages(2);
            }
            else
            {
              bool2 = false;
            }
            bool3 = bool2;
            if (i > this.mTouchSlopSquare)
            {
              this.mAlwaysInBiggerTapRegion = false;
              bool3 = bool2;
            }
          }
          else
          {
            if (Math.abs(f4) < 1.0F)
            {
              bool3 = bool2;
              if (Math.abs(f3) < 1.0F) {
                break label1181;
              }
            }
            bool3 = this.mListener.onScroll(this.mCurrentDownEvent, paramMotionEvent, f4, f3);
            this.mLastFocusX = f1;
            this.mLastFocusY = f2;
            break label1181;
          }
        }
        else
        {
          this.mStillDown = false;
          localMotionEvent = MotionEvent.obtain(paramMotionEvent);
          if (this.mIsDoubleTapping)
          {
            bool3 = this.mDoubleTapListener.onDoubleTapEvent(paramMotionEvent) | false;
          }
          else
          {
            if (this.mInLongPress)
            {
              this.mHandler.removeMessages(3);
              this.mInLongPress = false;
            }
            else
            {
              if (this.mAlwaysInTapRegion)
              {
                bool3 = this.mListener.onSingleTapUp(paramMotionEvent);
                if (this.mDeferConfirmSingleTap)
                {
                  localObject = this.mDoubleTapListener;
                  if (localObject != null) {
                    ((GestureDetector.OnDoubleTapListener)localObject).onSingleTapConfirmed(paramMotionEvent);
                  }
                }
                break label847;
              }
              localObject = this.mVelocityTracker;
              i = paramMotionEvent.getPointerId(0);
              ((VelocityTracker)localObject).computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
              f2 = ((VelocityTracker)localObject).getYVelocity(i);
              f1 = ((VelocityTracker)localObject).getXVelocity(i);
              if ((Math.abs(f2) > this.mMinimumFlingVelocity) || (Math.abs(f1) > this.mMinimumFlingVelocity)) {
                break label827;
              }
            }
            bool3 = false;
            break label847;
            label827:
            bool3 = this.mListener.onFling(this.mCurrentDownEvent, paramMotionEvent, f1, f2);
          }
          label847:
          paramMotionEvent = this.mPreviousUpEvent;
          if (paramMotionEvent != null) {
            paramMotionEvent.recycle();
          }
          this.mPreviousUpEvent = localMotionEvent;
          paramMotionEvent = this.mVelocityTracker;
          if (paramMotionEvent != null)
          {
            paramMotionEvent.recycle();
            this.mVelocityTracker = null;
          }
          this.mIsDoubleTapping = false;
          this.mDeferConfirmSingleTap = false;
          this.mHandler.removeMessages(1);
          this.mHandler.removeMessages(2);
        }
      }
      else
      {
        boolean bool1;
        if (this.mDoubleTapListener != null)
        {
          bool3 = this.mHandler.hasMessages(3);
          if (bool3) {
            this.mHandler.removeMessages(3);
          }
          localObject = this.mCurrentDownEvent;
          if (localObject != null)
          {
            localMotionEvent = this.mPreviousUpEvent;
            if ((localMotionEvent != null) && (bool3) && (isConsideredDoubleTap((MotionEvent)localObject, localMotionEvent, paramMotionEvent)))
            {
              this.mIsDoubleTapping = true;
              bool1 = this.mDoubleTapListener.onDoubleTap(this.mCurrentDownEvent) | false | this.mDoubleTapListener.onDoubleTapEvent(paramMotionEvent);
              break label1032;
            }
          }
          this.mHandler.sendEmptyMessageDelayed(3, DOUBLE_TAP_TIMEOUT);
        }
        else
        {
          bool1 = false;
        }
        label1032:
        this.mLastFocusX = f1;
        this.mDownFocusX = f1;
        this.mLastFocusY = f2;
        this.mDownFocusY = f2;
        localMotionEvent = this.mCurrentDownEvent;
        if (localMotionEvent != null) {
          localMotionEvent.recycle();
        }
        this.mCurrentDownEvent = MotionEvent.obtain(paramMotionEvent);
        this.mAlwaysInTapRegion = true;
        this.mAlwaysInBiggerTapRegion = true;
        this.mStillDown = true;
        this.mInLongPress = false;
        this.mDeferConfirmSingleTap = false;
        if (this.mIsLongpressEnabled)
        {
          this.mHandler.removeMessages(2);
          this.mHandler.sendEmptyMessageAtTime(2, this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT + LONGPRESS_TIMEOUT);
        }
        this.mHandler.sendEmptyMessageAtTime(1, this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT);
        bool3 = bool1 | this.mListener.onDown(paramMotionEvent);
      }
      label1181:
      return bool3;
    }
    
    public void setIsLongpressEnabled(boolean paramBoolean)
    {
      this.mIsLongpressEnabled = paramBoolean;
    }
    
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener)
    {
      this.mDoubleTapListener = paramOnDoubleTapListener;
    }
    
    private class GestureHandler
      extends Handler
    {
      GestureHandler() {}
      
      GestureHandler(Handler paramHandler)
      {
        super();
      }
      
      public void handleMessage(Message paramMessage)
      {
        int i = paramMessage.what;
        if (i != 1)
        {
          if (i != 2)
          {
            if (i == 3)
            {
              if (GestureDetectorCompat.GestureDetectorCompatImplBase.this.mDoubleTapListener != null) {
                if (!GestureDetectorCompat.GestureDetectorCompatImplBase.this.mStillDown) {
                  GestureDetectorCompat.GestureDetectorCompatImplBase.this.mDoubleTapListener.onSingleTapConfirmed(GestureDetectorCompat.GestureDetectorCompatImplBase.this.mCurrentDownEvent);
                } else {
                  GestureDetectorCompat.GestureDetectorCompatImplBase.this.mDeferConfirmSingleTap = true;
                }
              }
            }
            else
            {
              StringBuilder localStringBuilder = new StringBuilder();
              localStringBuilder.append("Unknown message ");
              localStringBuilder.append(paramMessage);
              throw new RuntimeException(localStringBuilder.toString());
            }
          }
          else {
            GestureDetectorCompat.GestureDetectorCompatImplBase.this.dispatchLongPress();
          }
        }
        else {
          GestureDetectorCompat.GestureDetectorCompatImplBase.this.mListener.onShowPress(GestureDetectorCompat.GestureDetectorCompatImplBase.this.mCurrentDownEvent);
        }
      }
    }
  }
  
  static class GestureDetectorCompatImplJellybeanMr2
    implements GestureDetectorCompat.GestureDetectorCompatImpl
  {
    private final GestureDetector mDetector;
    
    GestureDetectorCompatImplJellybeanMr2(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener, Handler paramHandler)
    {
      this.mDetector = new GestureDetector(paramContext, paramOnGestureListener, paramHandler);
    }
    
    public boolean isLongpressEnabled()
    {
      return this.mDetector.isLongpressEnabled();
    }
    
    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      return this.mDetector.onTouchEvent(paramMotionEvent);
    }
    
    public void setIsLongpressEnabled(boolean paramBoolean)
    {
      this.mDetector.setIsLongpressEnabled(paramBoolean);
    }
    
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener)
    {
      this.mDetector.setOnDoubleTapListener(paramOnDoubleTapListener);
    }
  }
}

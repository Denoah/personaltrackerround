package androidx.core.widget;

import android.os.Build.VERSION;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListPopupWindow;

public final class ListPopupWindowCompat
{
  private ListPopupWindowCompat() {}
  
  public static View.OnTouchListener createDragToOpenListener(ListPopupWindow paramListPopupWindow, View paramView)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return paramListPopupWindow.createDragToOpenListener(paramView);
    }
    return null;
  }
  
  @Deprecated
  public static View.OnTouchListener createDragToOpenListener(Object paramObject, View paramView)
  {
    return createDragToOpenListener((ListPopupWindow)paramObject, paramView);
  }
}

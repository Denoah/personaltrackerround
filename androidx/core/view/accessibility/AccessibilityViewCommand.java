package androidx.core.view.accessibility;

import android.os.Bundle;
import android.view.View;

public abstract interface AccessibilityViewCommand
{
  public abstract boolean perform(View paramView, CommandArguments paramCommandArguments);
  
  public static abstract class CommandArguments
  {
    Bundle mBundle;
    
    public CommandArguments() {}
    
    public void setBundle(Bundle paramBundle)
    {
      this.mBundle = paramBundle;
    }
  }
  
  public static final class MoveAtGranularityArguments
    extends AccessibilityViewCommand.CommandArguments
  {
    public MoveAtGranularityArguments() {}
    
    public boolean getExtendSelection()
    {
      return this.mBundle.getBoolean("ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN");
    }
    
    public int getGranularity()
    {
      return this.mBundle.getInt("ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT");
    }
  }
  
  public static final class MoveHtmlArguments
    extends AccessibilityViewCommand.CommandArguments
  {
    public MoveHtmlArguments() {}
    
    public String getHTMLElement()
    {
      return this.mBundle.getString("ACTION_ARGUMENT_HTML_ELEMENT_STRING");
    }
  }
  
  public static final class MoveWindowArguments
    extends AccessibilityViewCommand.CommandArguments
  {
    public MoveWindowArguments() {}
    
    public int getX()
    {
      return this.mBundle.getInt("ACTION_ARGUMENT_MOVE_WINDOW_X");
    }
    
    public int getY()
    {
      return this.mBundle.getInt("ACTION_ARGUMENT_MOVE_WINDOW_Y");
    }
  }
  
  public static final class ScrollToPositionArguments
    extends AccessibilityViewCommand.CommandArguments
  {
    public ScrollToPositionArguments() {}
    
    public int getColumn()
    {
      return this.mBundle.getInt("android.view.accessibility.action.ARGUMENT_COLUMN_INT");
    }
    
    public int getRow()
    {
      return this.mBundle.getInt("android.view.accessibility.action.ARGUMENT_ROW_INT");
    }
  }
  
  public static final class SetProgressArguments
    extends AccessibilityViewCommand.CommandArguments
  {
    public SetProgressArguments() {}
    
    public float getProgress()
    {
      return this.mBundle.getFloat("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE");
    }
  }
  
  public static final class SetSelectionArguments
    extends AccessibilityViewCommand.CommandArguments
  {
    public SetSelectionArguments() {}
    
    public int getEnd()
    {
      return this.mBundle.getInt("ACTION_ARGUMENT_SELECTION_END_INT");
    }
    
    public int getStart()
    {
      return this.mBundle.getInt("ACTION_ARGUMENT_SELECTION_START_INT");
    }
  }
  
  public static final class SetTextArguments
    extends AccessibilityViewCommand.CommandArguments
  {
    public SetTextArguments() {}
    
    public CharSequence getText()
    {
      return this.mBundle.getCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE");
    }
  }
}

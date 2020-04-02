package kotlin.reflect.jvm.internal.impl.renderer;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

public enum RenderingFormat
{
  static
  {
    PLAIN localPLAIN = new PLAIN("PLAIN", 0);
    PLAIN = localPLAIN;
    HTML localHTML = new HTML("HTML", 1);
    HTML = localHTML;
    $VALUES = new RenderingFormat[] { localPLAIN, localHTML };
  }
  
  private RenderingFormat() {}
  
  public abstract String escape(String paramString);
  
  static final class HTML
    extends RenderingFormat
  {
    HTML()
    {
      super(i, null);
    }
    
    public String escape(String paramString)
    {
      Intrinsics.checkParameterIsNotNull(paramString, "string");
      return StringsKt.replace$default(StringsKt.replace$default(paramString, "<", "&lt;", false, 4, null), ">", "&gt;", false, 4, null);
    }
  }
  
  static final class PLAIN
    extends RenderingFormat
  {
    PLAIN()
    {
      super(i, null);
    }
    
    public String escape(String paramString)
    {
      Intrinsics.checkParameterIsNotNull(paramString, "string");
      return paramString;
    }
  }
}

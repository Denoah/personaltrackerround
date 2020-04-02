package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

public enum AnnotationUseSiteTarget
{
  private final String renderName;
  
  static
  {
    AnnotationUseSiteTarget localAnnotationUseSiteTarget1 = new AnnotationUseSiteTarget("FIELD", 0, null, 1, null);
    FIELD = localAnnotationUseSiteTarget1;
    AnnotationUseSiteTarget localAnnotationUseSiteTarget2 = new AnnotationUseSiteTarget("FILE", 1, null, 1, null);
    FILE = localAnnotationUseSiteTarget2;
    AnnotationUseSiteTarget localAnnotationUseSiteTarget3 = new AnnotationUseSiteTarget("PROPERTY", 2, null, 1, null);
    PROPERTY = localAnnotationUseSiteTarget3;
    AnnotationUseSiteTarget localAnnotationUseSiteTarget4 = new AnnotationUseSiteTarget("PROPERTY_GETTER", 3, "get");
    PROPERTY_GETTER = localAnnotationUseSiteTarget4;
    AnnotationUseSiteTarget localAnnotationUseSiteTarget5 = new AnnotationUseSiteTarget("PROPERTY_SETTER", 4, "set");
    PROPERTY_SETTER = localAnnotationUseSiteTarget5;
    AnnotationUseSiteTarget localAnnotationUseSiteTarget6 = new AnnotationUseSiteTarget("RECEIVER", 5, null, 1, null);
    RECEIVER = localAnnotationUseSiteTarget6;
    AnnotationUseSiteTarget localAnnotationUseSiteTarget7 = new AnnotationUseSiteTarget("CONSTRUCTOR_PARAMETER", 6, "param");
    CONSTRUCTOR_PARAMETER = localAnnotationUseSiteTarget7;
    AnnotationUseSiteTarget localAnnotationUseSiteTarget8 = new AnnotationUseSiteTarget("SETTER_PARAMETER", 7, "setparam");
    SETTER_PARAMETER = localAnnotationUseSiteTarget8;
    AnnotationUseSiteTarget localAnnotationUseSiteTarget9 = new AnnotationUseSiteTarget("PROPERTY_DELEGATE_FIELD", 8, "delegate");
    PROPERTY_DELEGATE_FIELD = localAnnotationUseSiteTarget9;
    $VALUES = new AnnotationUseSiteTarget[] { localAnnotationUseSiteTarget1, localAnnotationUseSiteTarget2, localAnnotationUseSiteTarget3, localAnnotationUseSiteTarget4, localAnnotationUseSiteTarget5, localAnnotationUseSiteTarget6, localAnnotationUseSiteTarget7, localAnnotationUseSiteTarget8, localAnnotationUseSiteTarget9 };
  }
  
  private AnnotationUseSiteTarget(String paramString)
  {
    if (paramString == null)
    {
      ??? = name();
      if (??? != null)
      {
        paramString = ???.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(paramString, "(this as java.lang.String).toLowerCase()");
      }
    }
    else
    {
      this.renderName = paramString;
      return;
    }
    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
  }
  
  public final String getRenderName()
  {
    return this.renderName;
  }
}

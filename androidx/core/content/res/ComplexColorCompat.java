package androidx.core.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.XmlResourceParser;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class ComplexColorCompat
{
  private static final String LOG_TAG = "ComplexColorCompat";
  private int mColor;
  private final ColorStateList mColorStateList;
  private final Shader mShader;
  
  private ComplexColorCompat(Shader paramShader, ColorStateList paramColorStateList, int paramInt)
  {
    this.mShader = paramShader;
    this.mColorStateList = paramColorStateList;
    this.mColor = paramInt;
  }
  
  private static ComplexColorCompat createFromXml(Resources paramResources, int paramInt, Resources.Theme paramTheme)
    throws IOException, XmlPullParserException
  {
    XmlResourceParser localXmlResourceParser = paramResources.getXml(paramInt);
    AttributeSet localAttributeSet = Xml.asAttributeSet(localXmlResourceParser);
    do
    {
      paramInt = localXmlResourceParser.next();
    } while ((paramInt != 2) && (paramInt != 1));
    if (paramInt == 2)
    {
      String str = localXmlResourceParser.getName();
      paramInt = -1;
      int i = str.hashCode();
      if (i != 89650992)
      {
        if ((i == 1191572447) && (str.equals("selector"))) {
          paramInt = 0;
        }
      }
      else if (str.equals("gradient")) {
        paramInt = 1;
      }
      if (paramInt != 0)
      {
        if (paramInt == 1) {
          return from(GradientColorInflaterCompat.createFromXmlInner(paramResources, localXmlResourceParser, localAttributeSet, paramTheme));
        }
        paramResources = new StringBuilder();
        paramResources.append(localXmlResourceParser.getPositionDescription());
        paramResources.append(": unsupported complex color tag ");
        paramResources.append(str);
        throw new XmlPullParserException(paramResources.toString());
      }
      return from(ColorStateListInflaterCompat.createFromXmlInner(paramResources, localXmlResourceParser, localAttributeSet, paramTheme));
    }
    throw new XmlPullParserException("No start tag found");
  }
  
  static ComplexColorCompat from(int paramInt)
  {
    return new ComplexColorCompat(null, null, paramInt);
  }
  
  static ComplexColorCompat from(ColorStateList paramColorStateList)
  {
    return new ComplexColorCompat(null, paramColorStateList, paramColorStateList.getDefaultColor());
  }
  
  static ComplexColorCompat from(Shader paramShader)
  {
    return new ComplexColorCompat(paramShader, null, 0);
  }
  
  public static ComplexColorCompat inflate(Resources paramResources, int paramInt, Resources.Theme paramTheme)
  {
    try
    {
      paramResources = createFromXml(paramResources, paramInt, paramTheme);
      return paramResources;
    }
    catch (Exception paramResources)
    {
      Log.e("ComplexColorCompat", "Failed to inflate ComplexColor.", paramResources);
    }
    return null;
  }
  
  public int getColor()
  {
    return this.mColor;
  }
  
  public Shader getShader()
  {
    return this.mShader;
  }
  
  public boolean isGradient()
  {
    boolean bool;
    if (this.mShader != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isStateful()
  {
    if (this.mShader == null)
    {
      ColorStateList localColorStateList = this.mColorStateList;
      if ((localColorStateList != null) && (localColorStateList.isStateful())) {
        return true;
      }
    }
    boolean bool = false;
    return bool;
  }
  
  public boolean onStateChanged(int[] paramArrayOfInt)
  {
    if (isStateful())
    {
      ColorStateList localColorStateList = this.mColorStateList;
      int i = localColorStateList.getColorForState(paramArrayOfInt, localColorStateList.getDefaultColor());
      if (i != this.mColor)
      {
        bool = true;
        this.mColor = i;
        break label44;
      }
    }
    boolean bool = false;
    label44:
    return bool;
  }
  
  public void setColor(int paramInt)
  {
    this.mColor = paramInt;
  }
  
  public boolean willDraw()
  {
    boolean bool;
    if ((!isGradient()) && (this.mColor == 0)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
}

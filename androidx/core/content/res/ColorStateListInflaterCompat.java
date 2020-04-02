package androidx.core.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.StateSet;
import android.util.Xml;
import androidx.core.R.attr;
import androidx.core.R.styleable;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class ColorStateListInflaterCompat
{
  private ColorStateListInflaterCompat() {}
  
  public static ColorStateList createFromXml(Resources paramResources, XmlPullParser paramXmlPullParser, Resources.Theme paramTheme)
    throws XmlPullParserException, IOException
  {
    AttributeSet localAttributeSet = Xml.asAttributeSet(paramXmlPullParser);
    int i;
    do
    {
      i = paramXmlPullParser.next();
    } while ((i != 2) && (i != 1));
    if (i == 2) {
      return createFromXmlInner(paramResources, paramXmlPullParser, localAttributeSet, paramTheme);
    }
    throw new XmlPullParserException("No start tag found");
  }
  
  public static ColorStateList createFromXmlInner(Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
    throws XmlPullParserException, IOException
  {
    String str = paramXmlPullParser.getName();
    if (str.equals("selector")) {
      return inflate(paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
    }
    paramResources = new StringBuilder();
    paramResources.append(paramXmlPullParser.getPositionDescription());
    paramResources.append(": invalid color state list tag ");
    paramResources.append(str);
    throw new XmlPullParserException(paramResources.toString());
  }
  
  public static ColorStateList inflate(Resources paramResources, int paramInt, Resources.Theme paramTheme)
  {
    try
    {
      paramResources = createFromXml(paramResources, paramResources.getXml(paramInt), paramTheme);
      return paramResources;
    }
    catch (Exception paramResources)
    {
      Log.e("CSLCompat", "Failed to inflate ColorStateList.", paramResources);
    }
    return null;
  }
  
  private static ColorStateList inflate(Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
    throws XmlPullParserException, IOException
  {
    int i = paramXmlPullParser.getDepth() + 1;
    Object localObject1 = new int[20][];
    int[] arrayOfInt = new int[20];
    int j = 0;
    for (;;)
    {
      int k = paramXmlPullParser.next();
      if (k == 1) {
        break;
      }
      int m = paramXmlPullParser.getDepth();
      if ((m < i) && (k == 3)) {
        break;
      }
      if ((k == 2) && (m <= i) && (paramXmlPullParser.getName().equals("item")))
      {
        Object localObject2 = obtainAttributes(paramResources, paramTheme, paramAttributeSet, R.styleable.ColorStateListItem);
        int n = ((TypedArray)localObject2).getColor(R.styleable.ColorStateListItem_android_color, -65281);
        float f = 1.0F;
        if (((TypedArray)localObject2).hasValue(R.styleable.ColorStateListItem_android_alpha)) {
          f = ((TypedArray)localObject2).getFloat(R.styleable.ColorStateListItem_android_alpha, 1.0F);
        } else if (((TypedArray)localObject2).hasValue(R.styleable.ColorStateListItem_alpha)) {
          f = ((TypedArray)localObject2).getFloat(R.styleable.ColorStateListItem_alpha, 1.0F);
        }
        ((TypedArray)localObject2).recycle();
        int i1 = paramAttributeSet.getAttributeCount();
        localObject2 = new int[i1];
        m = 0;
        int i3;
        for (k = m; m < i1; k = i3)
        {
          int i2 = paramAttributeSet.getAttributeNameResource(m);
          i3 = k;
          if (i2 != 16843173)
          {
            i3 = k;
            if (i2 != 16843551)
            {
              i3 = k;
              if (i2 != R.attr.alpha)
              {
                if (paramAttributeSet.getAttributeBooleanValue(m, false)) {
                  i3 = i2;
                } else {
                  i3 = -i2;
                }
                localObject2[k] = i3;
                i3 = k + 1;
              }
            }
          }
          m++;
        }
        localObject2 = StateSet.trimStateSet((int[])localObject2, k);
        arrayOfInt = GrowingArrayUtils.append(arrayOfInt, j, modulateColorAlpha(n, f));
        localObject1 = (int[][])GrowingArrayUtils.append((Object[])localObject1, j, localObject2);
        j++;
      }
    }
    paramResources = new int[j];
    paramXmlPullParser = new int[j][];
    System.arraycopy(arrayOfInt, 0, paramResources, 0, j);
    System.arraycopy(localObject1, 0, paramXmlPullParser, 0, j);
    return new ColorStateList(paramXmlPullParser, paramResources);
  }
  
  private static int modulateColorAlpha(int paramInt, float paramFloat)
  {
    return paramInt & 0xFFFFFF | Math.round(Color.alpha(paramInt) * paramFloat) << 24;
  }
  
  private static TypedArray obtainAttributes(Resources paramResources, Resources.Theme paramTheme, AttributeSet paramAttributeSet, int[] paramArrayOfInt)
  {
    if (paramTheme == null) {
      paramResources = paramResources.obtainAttributes(paramAttributeSet, paramArrayOfInt);
    } else {
      paramResources = paramTheme.obtainStyledAttributes(paramAttributeSet, paramArrayOfInt, 0, 0);
    }
    return paramResources;
  }
}

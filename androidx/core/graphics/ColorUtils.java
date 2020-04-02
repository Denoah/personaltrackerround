package androidx.core.graphics;

import android.graphics.Color;
import java.util.Objects;

public final class ColorUtils
{
  private static final int MIN_ALPHA_SEARCH_MAX_ITERATIONS = 10;
  private static final int MIN_ALPHA_SEARCH_PRECISION = 1;
  private static final ThreadLocal<double[]> TEMP_ARRAY = new ThreadLocal();
  private static final double XYZ_EPSILON = 0.008856D;
  private static final double XYZ_KAPPA = 903.3D;
  private static final double XYZ_WHITE_REFERENCE_X = 95.047D;
  private static final double XYZ_WHITE_REFERENCE_Y = 100.0D;
  private static final double XYZ_WHITE_REFERENCE_Z = 108.883D;
  
  private ColorUtils() {}
  
  public static int HSLToColor(float[] paramArrayOfFloat)
  {
    float f1 = paramArrayOfFloat[0];
    float f2 = paramArrayOfFloat[1];
    float f3 = paramArrayOfFloat[2];
    f2 = (1.0F - Math.abs(f3 * 2.0F - 1.0F)) * f2;
    f3 -= 0.5F * f2;
    float f4 = (1.0F - Math.abs(f1 / 60.0F % 2.0F - 1.0F)) * f2;
    int i;
    int j;
    int k;
    switch ((int)f1 / 60)
    {
    default: 
      i = 0;
      j = i;
      k = j;
      break;
    case 5: 
    case 6: 
      j = Math.round((f2 + f3) * 255.0F);
      k = Math.round(f3 * 255.0F);
      i = Math.round((f4 + f3) * 255.0F);
      break;
    case 4: 
      j = Math.round((f4 + f3) * 255.0F);
      k = Math.round(f3 * 255.0F);
      i = Math.round((f2 + f3) * 255.0F);
      break;
    case 3: 
      j = Math.round(f3 * 255.0F);
      k = Math.round((f4 + f3) * 255.0F);
      i = Math.round((f2 + f3) * 255.0F);
      break;
    case 2: 
      j = Math.round(f3 * 255.0F);
      k = Math.round((f2 + f3) * 255.0F);
      i = Math.round((f4 + f3) * 255.0F);
      break;
    case 1: 
      j = Math.round((f4 + f3) * 255.0F);
      k = Math.round((f2 + f3) * 255.0F);
      i = Math.round(f3 * 255.0F);
      break;
    case 0: 
      j = Math.round((f2 + f3) * 255.0F);
      k = Math.round((f4 + f3) * 255.0F);
      i = Math.round(f3 * 255.0F);
    }
    return Color.rgb(constrain(j, 0, 255), constrain(k, 0, 255), constrain(i, 0, 255));
  }
  
  public static int LABToColor(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    double[] arrayOfDouble = getTempDouble3Array();
    LABToXYZ(paramDouble1, paramDouble2, paramDouble3, arrayOfDouble);
    return XYZToColor(arrayOfDouble[0], arrayOfDouble[1], arrayOfDouble[2]);
  }
  
  public static void LABToXYZ(double paramDouble1, double paramDouble2, double paramDouble3, double[] paramArrayOfDouble)
  {
    double d1 = (paramDouble1 + 16.0D) / 116.0D;
    double d2 = paramDouble2 / 500.0D + d1;
    double d3 = d1 - paramDouble3 / 200.0D;
    paramDouble2 = Math.pow(d2, 3.0D);
    if (paramDouble2 <= 0.008856D) {
      paramDouble2 = (d2 * 116.0D - 16.0D) / 903.3D;
    }
    if (paramDouble1 > 7.9996247999999985D) {
      paramDouble1 = Math.pow(d1, 3.0D);
    } else {
      paramDouble1 /= 903.3D;
    }
    paramDouble3 = Math.pow(d3, 3.0D);
    if (paramDouble3 <= 0.008856D) {
      paramDouble3 = (d3 * 116.0D - 16.0D) / 903.3D;
    }
    paramArrayOfDouble[0] = (paramDouble2 * 95.047D);
    paramArrayOfDouble[1] = (paramDouble1 * 100.0D);
    paramArrayOfDouble[2] = (paramDouble3 * 108.883D);
  }
  
  public static void RGBToHSL(int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOfFloat)
  {
    float f1 = paramInt1 / 255.0F;
    float f2 = paramInt2 / 255.0F;
    float f3 = paramInt3 / 255.0F;
    float f4 = Math.max(f1, Math.max(f2, f3));
    float f5 = Math.min(f1, Math.min(f2, f3));
    float f6 = f4 - f5;
    float f7 = (f4 + f5) / 2.0F;
    if (f4 == f5)
    {
      f6 = 0.0F;
      f4 = f6;
    }
    else
    {
      if (f4 == f1) {
        f4 = (f2 - f3) / f6 % 6.0F;
      } else if (f4 == f2) {
        f4 = (f3 - f1) / f6 + 2.0F;
      } else {
        f4 = 4.0F + (f1 - f2) / f6;
      }
      f2 = f6 / (1.0F - Math.abs(2.0F * f7 - 1.0F));
      f6 = f4;
      f4 = f2;
    }
    f2 = f6 * 60.0F % 360.0F;
    f6 = f2;
    if (f2 < 0.0F) {
      f6 = f2 + 360.0F;
    }
    paramArrayOfFloat[0] = constrain(f6, 0.0F, 360.0F);
    paramArrayOfFloat[1] = constrain(f4, 0.0F, 1.0F);
    paramArrayOfFloat[2] = constrain(f7, 0.0F, 1.0F);
  }
  
  public static void RGBToLAB(int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfDouble)
  {
    RGBToXYZ(paramInt1, paramInt2, paramInt3, paramArrayOfDouble);
    XYZToLAB(paramArrayOfDouble[0], paramArrayOfDouble[1], paramArrayOfDouble[2], paramArrayOfDouble);
  }
  
  public static void RGBToXYZ(int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfDouble)
  {
    if (paramArrayOfDouble.length == 3)
    {
      double d1 = paramInt1 / 255.0D;
      if (d1 < 0.04045D) {
        d1 /= 12.92D;
      } else {
        d1 = Math.pow((d1 + 0.055D) / 1.055D, 2.4D);
      }
      double d2 = paramInt2 / 255.0D;
      if (d2 < 0.04045D) {
        d2 /= 12.92D;
      } else {
        d2 = Math.pow((d2 + 0.055D) / 1.055D, 2.4D);
      }
      double d3 = paramInt3 / 255.0D;
      if (d3 < 0.04045D) {
        d3 /= 12.92D;
      } else {
        d3 = Math.pow((d3 + 0.055D) / 1.055D, 2.4D);
      }
      paramArrayOfDouble[0] = ((0.4124D * d1 + 0.3576D * d2 + 0.1805D * d3) * 100.0D);
      paramArrayOfDouble[1] = ((0.2126D * d1 + 0.7152D * d2 + 0.0722D * d3) * 100.0D);
      paramArrayOfDouble[2] = ((d1 * 0.0193D + d2 * 0.1192D + d3 * 0.9505D) * 100.0D);
      return;
    }
    throw new IllegalArgumentException("outXyz must have a length of 3.");
  }
  
  public static int XYZToColor(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    double d1 = (3.2406D * paramDouble1 + -1.5372D * paramDouble2 + -0.4986D * paramDouble3) / 100.0D;
    double d2 = (-0.9689D * paramDouble1 + 1.8758D * paramDouble2 + 0.0415D * paramDouble3) / 100.0D;
    paramDouble3 = (0.0557D * paramDouble1 + -0.204D * paramDouble2 + 1.057D * paramDouble3) / 100.0D;
    if (d1 > 0.0031308D) {
      paramDouble1 = Math.pow(d1, 0.4166666666666667D) * 1.055D - 0.055D;
    } else {
      paramDouble1 = d1 * 12.92D;
    }
    if (d2 > 0.0031308D) {
      paramDouble2 = Math.pow(d2, 0.4166666666666667D) * 1.055D - 0.055D;
    } else {
      paramDouble2 = d2 * 12.92D;
    }
    if (paramDouble3 > 0.0031308D) {
      paramDouble3 = Math.pow(paramDouble3, 0.4166666666666667D) * 1.055D - 0.055D;
    } else {
      paramDouble3 *= 12.92D;
    }
    return Color.rgb(constrain((int)Math.round(paramDouble1 * 255.0D), 0, 255), constrain((int)Math.round(paramDouble2 * 255.0D), 0, 255), constrain((int)Math.round(paramDouble3 * 255.0D), 0, 255));
  }
  
  public static void XYZToLAB(double paramDouble1, double paramDouble2, double paramDouble3, double[] paramArrayOfDouble)
  {
    if (paramArrayOfDouble.length == 3)
    {
      paramDouble1 = pivotXyzComponent(paramDouble1 / 95.047D);
      paramDouble2 = pivotXyzComponent(paramDouble2 / 100.0D);
      paramDouble3 = pivotXyzComponent(paramDouble3 / 108.883D);
      paramArrayOfDouble[0] = Math.max(0.0D, 116.0D * paramDouble2 - 16.0D);
      paramArrayOfDouble[1] = ((paramDouble1 - paramDouble2) * 500.0D);
      paramArrayOfDouble[2] = ((paramDouble2 - paramDouble3) * 200.0D);
      return;
    }
    throw new IllegalArgumentException("outLab must have a length of 3.");
  }
  
  public static int blendARGB(int paramInt1, int paramInt2, float paramFloat)
  {
    float f1 = 1.0F - paramFloat;
    float f2 = Color.alpha(paramInt1);
    float f3 = Color.alpha(paramInt2);
    float f4 = Color.red(paramInt1);
    float f5 = Color.red(paramInt2);
    float f6 = Color.green(paramInt1);
    float f7 = Color.green(paramInt2);
    float f8 = Color.blue(paramInt1);
    float f9 = Color.blue(paramInt2);
    return Color.argb((int)(f2 * f1 + f3 * paramFloat), (int)(f4 * f1 + f5 * paramFloat), (int)(f6 * f1 + f7 * paramFloat), (int)(f8 * f1 + f9 * paramFloat));
  }
  
  public static void blendHSL(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, float paramFloat, float[] paramArrayOfFloat3)
  {
    if (paramArrayOfFloat3.length == 3)
    {
      float f = 1.0F - paramFloat;
      paramArrayOfFloat3[0] = circularInterpolate(paramArrayOfFloat1[0], paramArrayOfFloat2[0], paramFloat);
      paramArrayOfFloat3[1] = (paramArrayOfFloat1[1] * f + paramArrayOfFloat2[1] * paramFloat);
      paramArrayOfFloat3[2] = (paramArrayOfFloat1[2] * f + paramArrayOfFloat2[2] * paramFloat);
      return;
    }
    throw new IllegalArgumentException("result must have a length of 3.");
  }
  
  public static void blendLAB(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, double paramDouble, double[] paramArrayOfDouble3)
  {
    if (paramArrayOfDouble3.length == 3)
    {
      double d = 1.0D - paramDouble;
      paramArrayOfDouble3[0] = (paramArrayOfDouble1[0] * d + paramArrayOfDouble2[0] * paramDouble);
      paramArrayOfDouble3[1] = (paramArrayOfDouble1[1] * d + paramArrayOfDouble2[1] * paramDouble);
      paramArrayOfDouble3[2] = (paramArrayOfDouble1[2] * d + paramArrayOfDouble2[2] * paramDouble);
      return;
    }
    throw new IllegalArgumentException("outResult must have a length of 3.");
  }
  
  public static double calculateContrast(int paramInt1, int paramInt2)
  {
    if (Color.alpha(paramInt2) == 255)
    {
      int i = paramInt1;
      if (Color.alpha(paramInt1) < 255) {
        i = compositeColors(paramInt1, paramInt2);
      }
      double d1 = calculateLuminance(i) + 0.05D;
      double d2 = calculateLuminance(paramInt2) + 0.05D;
      return Math.max(d1, d2) / Math.min(d1, d2);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("background can not be translucent: #");
    localStringBuilder.append(Integer.toHexString(paramInt2));
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public static double calculateLuminance(int paramInt)
  {
    double[] arrayOfDouble = getTempDouble3Array();
    colorToXYZ(paramInt, arrayOfDouble);
    return arrayOfDouble[1] / 100.0D;
  }
  
  public static int calculateMinimumAlpha(int paramInt1, int paramInt2, float paramFloat)
  {
    int i = Color.alpha(paramInt2);
    int j = 255;
    if (i == 255)
    {
      double d1 = calculateContrast(setAlphaComponent(paramInt1, 255), paramInt2);
      double d2 = paramFloat;
      if (d1 < d2) {
        return -1;
      }
      i = 0;
      int k = 0;
      while ((i <= 10) && (j - k > 1))
      {
        int m = (k + j) / 2;
        if (calculateContrast(setAlphaComponent(paramInt1, m), paramInt2) < d2) {
          k = m;
        } else {
          j = m;
        }
        i++;
      }
      return j;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("background can not be translucent: #");
    localStringBuilder.append(Integer.toHexString(paramInt2));
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  static float circularInterpolate(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f1 = paramFloat1;
    float f2 = paramFloat2;
    if (Math.abs(paramFloat2 - paramFloat1) > 180.0F) {
      if (paramFloat2 > paramFloat1)
      {
        f1 = paramFloat1 + 360.0F;
        f2 = paramFloat2;
      }
      else
      {
        f2 = paramFloat2 + 360.0F;
        f1 = paramFloat1;
      }
    }
    return (f1 + (f2 - f1) * paramFloat3) % 360.0F;
  }
  
  public static void colorToHSL(int paramInt, float[] paramArrayOfFloat)
  {
    RGBToHSL(Color.red(paramInt), Color.green(paramInt), Color.blue(paramInt), paramArrayOfFloat);
  }
  
  public static void colorToLAB(int paramInt, double[] paramArrayOfDouble)
  {
    RGBToLAB(Color.red(paramInt), Color.green(paramInt), Color.blue(paramInt), paramArrayOfDouble);
  }
  
  public static void colorToXYZ(int paramInt, double[] paramArrayOfDouble)
  {
    RGBToXYZ(Color.red(paramInt), Color.green(paramInt), Color.blue(paramInt), paramArrayOfDouble);
  }
  
  private static int compositeAlpha(int paramInt1, int paramInt2)
  {
    return 255 - (255 - paramInt2) * (255 - paramInt1) / 255;
  }
  
  public static int compositeColors(int paramInt1, int paramInt2)
  {
    int i = Color.alpha(paramInt2);
    int j = Color.alpha(paramInt1);
    int k = compositeAlpha(j, i);
    return Color.argb(k, compositeComponent(Color.red(paramInt1), j, Color.red(paramInt2), i, k), compositeComponent(Color.green(paramInt1), j, Color.green(paramInt2), i, k), compositeComponent(Color.blue(paramInt1), j, Color.blue(paramInt2), i, k));
  }
  
  public static Color compositeColors(Color paramColor1, Color paramColor2)
  {
    if (Objects.equals(paramColor1.getModel(), paramColor2.getModel()))
    {
      if (!Objects.equals(paramColor2.getColorSpace(), paramColor1.getColorSpace())) {
        paramColor1 = paramColor1.convert(paramColor2.getColorSpace());
      }
      localObject = paramColor1.getComponents();
      float[] arrayOfFloat = paramColor2.getComponents();
      float f1 = paramColor1.alpha();
      float f2 = paramColor2.alpha() * (1.0F - f1);
      int i = paramColor2.getComponentCount() - 1;
      arrayOfFloat[i] = (f1 + f2);
      float f3 = f2;
      float f4 = f1;
      if (arrayOfFloat[i] > 0.0F)
      {
        f4 = f1 / arrayOfFloat[i];
        f3 = f2 / arrayOfFloat[i];
      }
      for (int j = 0; j < i; j++) {
        arrayOfFloat[j] = (localObject[j] * f4 + arrayOfFloat[j] * f3);
      }
      return Color.valueOf(arrayOfFloat, paramColor2.getColorSpace());
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Color models must match (");
    ((StringBuilder)localObject).append(paramColor1.getModel());
    ((StringBuilder)localObject).append(" vs. ");
    ((StringBuilder)localObject).append(paramColor2.getModel());
    ((StringBuilder)localObject).append(")");
    throw new IllegalArgumentException(((StringBuilder)localObject).toString());
  }
  
  private static int compositeComponent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if (paramInt5 == 0) {
      return 0;
    }
    return (paramInt1 * 255 * paramInt2 + paramInt3 * paramInt4 * (255 - paramInt2)) / (paramInt5 * 255);
  }
  
  private static float constrain(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramFloat1 >= paramFloat2)
    {
      paramFloat2 = paramFloat1;
      if (paramFloat1 > paramFloat3) {
        paramFloat2 = paramFloat3;
      }
    }
    return paramFloat2;
  }
  
  private static int constrain(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 >= paramInt2)
    {
      paramInt2 = paramInt1;
      if (paramInt1 > paramInt3) {
        paramInt2 = paramInt3;
      }
    }
    return paramInt2;
  }
  
  public static double distanceEuclidean(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2)
  {
    return Math.sqrt(Math.pow(paramArrayOfDouble1[0] - paramArrayOfDouble2[0], 2.0D) + Math.pow(paramArrayOfDouble1[1] - paramArrayOfDouble2[1], 2.0D) + Math.pow(paramArrayOfDouble1[2] - paramArrayOfDouble2[2], 2.0D));
  }
  
  private static double[] getTempDouble3Array()
  {
    double[] arrayOfDouble1 = (double[])TEMP_ARRAY.get();
    double[] arrayOfDouble2 = arrayOfDouble1;
    if (arrayOfDouble1 == null)
    {
      arrayOfDouble2 = new double[3];
      TEMP_ARRAY.set(arrayOfDouble2);
    }
    return arrayOfDouble2;
  }
  
  private static double pivotXyzComponent(double paramDouble)
  {
    if (paramDouble > 0.008856D) {
      paramDouble = Math.pow(paramDouble, 0.3333333333333333D);
    } else {
      paramDouble = (paramDouble * 903.3D + 16.0D) / 116.0D;
    }
    return paramDouble;
  }
  
  public static int setAlphaComponent(int paramInt1, int paramInt2)
  {
    if ((paramInt2 >= 0) && (paramInt2 <= 255)) {
      return paramInt1 & 0xFFFFFF | paramInt2 << 24;
    }
    throw new IllegalArgumentException("alpha must be between 0 and 255.");
  }
}

package androidx.core.graphics;

import android.graphics.Path;
import android.util.Log;
import java.util.ArrayList;

public class PathParser
{
  private static final String LOGTAG = "PathParser";
  
  private PathParser() {}
  
  private static void addNode(ArrayList<PathDataNode> paramArrayList, char paramChar, float[] paramArrayOfFloat)
  {
    paramArrayList.add(new PathDataNode(paramChar, paramArrayOfFloat));
  }
  
  public static boolean canMorph(PathDataNode[] paramArrayOfPathDataNode1, PathDataNode[] paramArrayOfPathDataNode2)
  {
    if ((paramArrayOfPathDataNode1 != null) && (paramArrayOfPathDataNode2 != null))
    {
      if (paramArrayOfPathDataNode1.length != paramArrayOfPathDataNode2.length) {
        return false;
      }
      int i = 0;
      while (i < paramArrayOfPathDataNode1.length) {
        if ((paramArrayOfPathDataNode1[i].mType == paramArrayOfPathDataNode2[i].mType) && (paramArrayOfPathDataNode1[i].mParams.length == paramArrayOfPathDataNode2[i].mParams.length)) {
          i++;
        } else {
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  static float[] copyOfRange(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
  {
    if (paramInt1 <= paramInt2)
    {
      int i = paramArrayOfFloat.length;
      if ((paramInt1 >= 0) && (paramInt1 <= i))
      {
        paramInt2 -= paramInt1;
        i = Math.min(paramInt2, i - paramInt1);
        float[] arrayOfFloat = new float[paramInt2];
        System.arraycopy(paramArrayOfFloat, paramInt1, arrayOfFloat, 0, i);
        return arrayOfFloat;
      }
      throw new ArrayIndexOutOfBoundsException();
    }
    throw new IllegalArgumentException();
  }
  
  public static PathDataNode[] createNodesFromPathData(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    int i = 1;
    int j = 0;
    while (i < paramString.length())
    {
      i = nextStart(paramString, i);
      String str = paramString.substring(j, i).trim();
      if (str.length() > 0)
      {
        float[] arrayOfFloat = getFloats(str);
        addNode(localArrayList, str.charAt(0), arrayOfFloat);
      }
      j = i;
      i++;
    }
    if ((i - j == 1) && (j < paramString.length())) {
      addNode(localArrayList, paramString.charAt(j), new float[0]);
    }
    return (PathDataNode[])localArrayList.toArray(new PathDataNode[localArrayList.size()]);
  }
  
  public static Path createPathFromPathData(String paramString)
  {
    Object localObject = new Path();
    PathDataNode[] arrayOfPathDataNode = createNodesFromPathData(paramString);
    if (arrayOfPathDataNode != null) {
      try
      {
        PathDataNode.nodesToPath(arrayOfPathDataNode, (Path)localObject);
        return localObject;
      }
      catch (RuntimeException localRuntimeException)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Error in parsing ");
        ((StringBuilder)localObject).append(paramString);
        throw new RuntimeException(((StringBuilder)localObject).toString(), localRuntimeException);
      }
    }
    return null;
  }
  
  public static PathDataNode[] deepCopyNodes(PathDataNode[] paramArrayOfPathDataNode)
  {
    if (paramArrayOfPathDataNode == null) {
      return null;
    }
    PathDataNode[] arrayOfPathDataNode = new PathDataNode[paramArrayOfPathDataNode.length];
    for (int i = 0; i < paramArrayOfPathDataNode.length; i++) {
      arrayOfPathDataNode[i] = new PathDataNode(paramArrayOfPathDataNode[i]);
    }
    return arrayOfPathDataNode;
  }
  
  private static void extract(String paramString, int paramInt, ExtractFloatResult paramExtractFloatResult)
  {
    paramExtractFloatResult.mEndWithNegOrDot = false;
    int i = paramInt;
    int j = 0;
    int k = j;
    int m = k;
    int n = k;
    k = j;
    while (i < paramString.length())
    {
      j = paramString.charAt(i);
      if (j != 32)
      {
        if ((j != 69) && (j != 101)) {}
        switch (j)
        {
        default: 
          break;
        case 46: 
          if (n == 0)
          {
            k = 0;
            n = 1;
            break label153;
          }
          paramExtractFloatResult.mEndWithNegOrDot = true;
          break;
        case 45: 
          if ((i != paramInt) && (k == 0))
          {
            paramExtractFloatResult.mEndWithNegOrDot = true;
          }
          else
          {
            k = 0;
            break label153;
            k = 1;
          }
          break;
        }
      }
      k = 0;
      m = 1;
      label153:
      if (m != 0) {
        break;
      }
      i++;
    }
    paramExtractFloatResult.mEndPosition = i;
  }
  
  private static float[] getFloats(String paramString)
  {
    if ((paramString.charAt(0) != 'z') && (paramString.charAt(0) != 'Z')) {
      try
      {
        localObject1 = new float[paramString.length()];
        Object localObject2 = new androidx/core/graphics/PathParser$ExtractFloatResult;
        ((ExtractFloatResult)localObject2).<init>();
        int i = paramString.length();
        int j = 1;
        int k = 0;
        while (j < i)
        {
          extract(paramString, j, (ExtractFloatResult)localObject2);
          int m = ((ExtractFloatResult)localObject2).mEndPosition;
          int n = k;
          if (j < m)
          {
            localObject1[k] = Float.parseFloat(paramString.substring(j, m));
            n = k + 1;
          }
          if (((ExtractFloatResult)localObject2).mEndWithNegOrDot)
          {
            j = m;
            k = n;
          }
          else
          {
            j = m + 1;
            k = n;
          }
        }
        localObject2 = copyOfRange((float[])localObject1, 0, k);
        return localObject2;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        Object localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("error in parsing \"");
        ((StringBuilder)localObject1).append(paramString);
        ((StringBuilder)localObject1).append("\"");
        throw new RuntimeException(((StringBuilder)localObject1).toString(), localNumberFormatException);
      }
    }
    return new float[0];
  }
  
  public static boolean interpolatePathDataNodes(PathDataNode[] paramArrayOfPathDataNode1, PathDataNode[] paramArrayOfPathDataNode2, PathDataNode[] paramArrayOfPathDataNode3, float paramFloat)
  {
    if ((paramArrayOfPathDataNode1 != null) && (paramArrayOfPathDataNode2 != null) && (paramArrayOfPathDataNode3 != null))
    {
      if ((paramArrayOfPathDataNode1.length == paramArrayOfPathDataNode2.length) && (paramArrayOfPathDataNode2.length == paramArrayOfPathDataNode3.length))
      {
        boolean bool = canMorph(paramArrayOfPathDataNode2, paramArrayOfPathDataNode3);
        int i = 0;
        if (!bool) {
          return false;
        }
        while (i < paramArrayOfPathDataNode1.length)
        {
          paramArrayOfPathDataNode1[i].interpolatePathDataNode(paramArrayOfPathDataNode2[i], paramArrayOfPathDataNode3[i], paramFloat);
          i++;
        }
        return true;
      }
      throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes must have the same length");
    }
    throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes cannot be null");
  }
  
  private static int nextStart(String paramString, int paramInt)
  {
    while (paramInt < paramString.length())
    {
      int i = paramString.charAt(paramInt);
      if ((((i - 65) * (i - 90) <= 0) || ((i - 97) * (i - 122) <= 0)) && (i != 101) && (i != 69)) {
        return paramInt;
      }
      paramInt++;
    }
    return paramInt;
  }
  
  public static void updateNodes(PathDataNode[] paramArrayOfPathDataNode1, PathDataNode[] paramArrayOfPathDataNode2)
  {
    for (int i = 0; i < paramArrayOfPathDataNode2.length; i++)
    {
      paramArrayOfPathDataNode1[i].mType = ((char)paramArrayOfPathDataNode2[i].mType);
      for (int j = 0; j < paramArrayOfPathDataNode2[i].mParams.length; j++) {
        paramArrayOfPathDataNode1[i].mParams[j] = paramArrayOfPathDataNode2[i].mParams[j];
      }
    }
  }
  
  private static class ExtractFloatResult
  {
    int mEndPosition;
    boolean mEndWithNegOrDot;
    
    ExtractFloatResult() {}
  }
  
  public static class PathDataNode
  {
    public float[] mParams;
    public char mType;
    
    PathDataNode(char paramChar, float[] paramArrayOfFloat)
    {
      this.mType = ((char)paramChar);
      this.mParams = paramArrayOfFloat;
    }
    
    PathDataNode(PathDataNode paramPathDataNode)
    {
      this.mType = ((char)paramPathDataNode.mType);
      paramPathDataNode = paramPathDataNode.mParams;
      this.mParams = PathParser.copyOfRange(paramPathDataNode, 0, paramPathDataNode.length);
    }
    
    private static void addCommand(Path paramPath, float[] paramArrayOfFloat1, char paramChar1, char paramChar2, float[] paramArrayOfFloat2)
    {
      float f1 = paramArrayOfFloat1[0];
      float f2 = paramArrayOfFloat1[1];
      float f3 = paramArrayOfFloat1[2];
      float f4 = paramArrayOfFloat1[3];
      float f5 = paramArrayOfFloat1[4];
      float f6 = paramArrayOfFloat1[5];
      float f7 = f1;
      float f8 = f2;
      float f9 = f3;
      float f10 = f4;
      char c1;
      switch (paramChar2)
      {
      default: 
        f10 = f4;
        f9 = f3;
        f8 = f2;
        f7 = f1;
      case 'L': 
      case 'M': 
      case 'T': 
      case 'l': 
      case 'm': 
      case 't': 
      case 'Z': 
      case 'z': 
        for (;;)
        {
          c1 = '\002';
          break;
          paramPath.close();
          paramPath.moveTo(f5, f6);
          f7 = f5;
          f9 = f7;
          f8 = f6;
          f10 = f8;
        }
      case 'Q': 
      case 'S': 
      case 'q': 
      case 's': 
        c1 = '\004';
        f7 = f1;
        f8 = f2;
        f9 = f3;
        f10 = f4;
        break;
      case 'H': 
      case 'V': 
      case 'h': 
      case 'v': 
        c1 = '\001';
        f7 = f1;
        f8 = f2;
        f9 = f3;
        f10 = f4;
        break;
      case 'C': 
      case 'c': 
        c1 = '\006';
        break;
      case 'A': 
      case 'a': 
        c1 = '\007';
      }
      f10 = f4;
      f9 = f3;
      f8 = f2;
      f7 = f1;
      f4 = f7;
      f7 = f8;
      char c2 = '\000';
      int i = paramChar1;
      f1 = f6;
      f3 = f5;
      f8 = f4;
      paramChar1 = c2;
      for (;;)
      {
        c2 = paramChar2;
        if (paramChar1 >= paramArrayOfFloat2.length) {
          break;
        }
        int k;
        label1235:
        label1561:
        label1572:
        boolean bool1;
        boolean bool2;
        if (c2 != 'A')
        {
          int j;
          if (c2 != 'C')
          {
            if (c2 != 'H')
            {
              if (c2 != 'Q')
              {
                if (c2 != 'V')
                {
                  if (c2 != 'a')
                  {
                    if (c2 != 'c') {
                      if (c2 != 'h') {
                        if (c2 != 'q') {
                          if (c2 != 'v') {
                            if (c2 != 'L') {
                              if (c2 != 'M') {
                                if (c2 != 'S') {
                                  if (c2 != 'T') {
                                    if (c2 != 'l') {
                                      if (c2 != 'm') {
                                        if (c2 != 's') {
                                          if (c2 != 't') {
                                            break label2126;
                                          }
                                        }
                                      }
                                    }
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                    for (;;)
                    {
                      if ((i != 113) && (i != 116) && (i != 81) && (i != 84))
                      {
                        f10 = 0.0F;
                        f9 = 0.0F;
                      }
                      else
                      {
                        f9 = f8 - f9;
                        f10 = f7 - f10;
                      }
                      c2 = paramChar1 + '\000';
                      f6 = paramArrayOfFloat2[c2];
                      i = paramChar1 + '\001';
                      paramPath.rQuadTo(f9, f10, f6, paramArrayOfFloat2[i]);
                      f6 = f8 + paramArrayOfFloat2[c2];
                      f5 = f7 + paramArrayOfFloat2[i];
                      f10 += f7;
                      f9 += f8;
                      f7 = f5;
                      f8 = f6;
                      continue;
                      if ((i != 99) && (i != 115) && (i != 67) && (i != 83))
                      {
                        f9 = 0.0F;
                        f10 = 0.0F;
                      }
                      else
                      {
                        f10 = f7 - f10;
                        f9 = f8 - f9;
                      }
                      j = paramChar1 + '\000';
                      f5 = paramArrayOfFloat2[j];
                      c2 = paramChar1 + '\001';
                      f4 = paramArrayOfFloat2[c2];
                      k = paramChar1 + '\002';
                      f6 = paramArrayOfFloat2[k];
                      i = paramChar1 + '\003';
                      paramPath.rCubicTo(f9, f10, f5, f4, f6, paramArrayOfFloat2[i]);
                      f5 = paramArrayOfFloat2[j] + f8;
                      f9 = paramArrayOfFloat2[c2] + f7;
                      f10 = f8 + paramArrayOfFloat2[k];
                      f6 = paramArrayOfFloat2[i];
                      f8 = f5;
                      break label1561;
                      i = paramChar1 + '\000';
                      f8 += paramArrayOfFloat2[i];
                      c2 = paramChar1 + '\001';
                      f7 += paramArrayOfFloat2[c2];
                      if (paramChar1 > 0)
                      {
                        paramPath.rLineTo(paramArrayOfFloat2[i], paramArrayOfFloat2[c2]);
                      }
                      else
                      {
                        paramPath.rMoveTo(paramArrayOfFloat2[i], paramArrayOfFloat2[c2]);
                        break label1235;
                        i = paramChar1 + '\000';
                        f6 = paramArrayOfFloat2[i];
                        c2 = paramChar1 + '\001';
                        paramPath.rLineTo(f6, paramArrayOfFloat2[c2]);
                        f8 += paramArrayOfFloat2[i];
                        for (f6 = paramArrayOfFloat2[c2];; f6 = paramArrayOfFloat2[i])
                        {
                          f7 += f6;
                          break;
                          if ((i != 113) && (i != 116) && (i != 81))
                          {
                            f5 = f7;
                            f6 = f8;
                            if (i != 84) {}
                          }
                          else
                          {
                            f6 = f8 * 2.0F - f9;
                            f5 = f7 * 2.0F - f10;
                          }
                          i = paramChar1 + '\000';
                          f8 = paramArrayOfFloat2[i];
                          c2 = paramChar1 + '\001';
                          paramPath.quadTo(f6, f5, f8, paramArrayOfFloat2[c2]);
                          f8 = paramArrayOfFloat2[i];
                          f7 = paramArrayOfFloat2[c2];
                          f10 = f5;
                          f9 = f6;
                          break;
                          if ((i != 99) && (i != 115) && (i != 67))
                          {
                            f5 = f7;
                            f6 = f8;
                            if (i != 83) {}
                          }
                          else
                          {
                            f6 = f8 * 2.0F - f9;
                            f5 = f7 * 2.0F - f10;
                          }
                          i = paramChar1 + '\000';
                          f7 = paramArrayOfFloat2[i];
                          k = paramChar1 + '\001';
                          f9 = paramArrayOfFloat2[k];
                          c2 = paramChar1 + '\002';
                          f8 = paramArrayOfFloat2[c2];
                          j = paramChar1 + '\003';
                          paramPath.cubicTo(f6, f5, f7, f9, f8, paramArrayOfFloat2[j]);
                          f8 = paramArrayOfFloat2[i];
                          f9 = paramArrayOfFloat2[k];
                          f6 = paramArrayOfFloat2[c2];
                          f7 = paramArrayOfFloat2[j];
                          break label1572;
                          i = paramChar1 + '\000';
                          f8 = paramArrayOfFloat2[i];
                          c2 = paramChar1 + '\001';
                          f7 = paramArrayOfFloat2[c2];
                          if (paramChar1 > 0)
                          {
                            paramPath.lineTo(paramArrayOfFloat2[i], paramArrayOfFloat2[c2]);
                            break;
                          }
                          paramPath.moveTo(paramArrayOfFloat2[i], paramArrayOfFloat2[c2]);
                          f1 = f7;
                          f3 = f8;
                          break;
                          c2 = paramChar1 + '\000';
                          f8 = paramArrayOfFloat2[c2];
                          i = paramChar1 + '\001';
                          paramPath.lineTo(f8, paramArrayOfFloat2[i]);
                          f8 = paramArrayOfFloat2[c2];
                          f7 = paramArrayOfFloat2[i];
                          break;
                          i = paramChar1 + '\000';
                          paramPath.rLineTo(0.0F, paramArrayOfFloat2[i]);
                        }
                        j = paramChar1 + '\000';
                        f6 = paramArrayOfFloat2[j];
                        i = paramChar1 + '\001';
                        f10 = paramArrayOfFloat2[i];
                        c2 = paramChar1 + '\002';
                        f9 = paramArrayOfFloat2[c2];
                        k = paramChar1 + '\003';
                        paramPath.rQuadTo(f6, f10, f9, paramArrayOfFloat2[k]);
                        f5 = paramArrayOfFloat2[j] + f8;
                        f9 = paramArrayOfFloat2[i] + f7;
                        f10 = f8 + paramArrayOfFloat2[c2];
                        f6 = paramArrayOfFloat2[k];
                        f8 = f5;
                        break label1561;
                        i = paramChar1 + '\000';
                        paramPath.rLineTo(paramArrayOfFloat2[i], 0.0F);
                        f8 += paramArrayOfFloat2[i];
                        continue;
                        f10 = paramArrayOfFloat2[(paramChar1 + '\000')];
                        f5 = paramArrayOfFloat2[(paramChar1 + '\001')];
                        c2 = paramChar1 + '\002';
                        f6 = paramArrayOfFloat2[c2];
                        i = paramChar1 + '\003';
                        f4 = paramArrayOfFloat2[i];
                        k = paramChar1 + '\004';
                        f9 = paramArrayOfFloat2[k];
                        j = paramChar1 + '\005';
                        paramPath.rCubicTo(f10, f5, f6, f4, f9, paramArrayOfFloat2[j]);
                        f5 = paramArrayOfFloat2[c2] + f8;
                        f9 = paramArrayOfFloat2[i] + f7;
                        f10 = f8 + paramArrayOfFloat2[k];
                        f6 = paramArrayOfFloat2[j];
                        f8 = f5;
                        f7 += f6;
                        f6 = f10;
                        f10 = f9;
                        f9 = f8;
                        f8 = f6;
                      }
                    }
                  }
                  i = paramChar1 + '\005';
                  f10 = paramArrayOfFloat2[i];
                  c2 = paramChar1 + '\006';
                  f5 = paramArrayOfFloat2[c2];
                  f6 = paramArrayOfFloat2[(paramChar1 + '\000')];
                  f9 = paramArrayOfFloat2[(paramChar1 + '\001')];
                  f4 = paramArrayOfFloat2[(paramChar1 + '\002')];
                  if (paramArrayOfFloat2[(paramChar1 + '\003')] != 0.0F) {
                    bool1 = true;
                  } else {
                    bool1 = false;
                  }
                  if (paramArrayOfFloat2[(paramChar1 + '\004')] != 0.0F) {
                    bool2 = true;
                  } else {
                    bool2 = false;
                  }
                  drawArc(paramPath, f8, f7, f10 + f8, f5 + f7, f6, f9, f4, bool1, bool2);
                  f8 += paramArrayOfFloat2[i];
                  f7 += paramArrayOfFloat2[c2];
                }
                else
                {
                  i = paramChar1 + '\000';
                  paramPath.lineTo(f8, paramArrayOfFloat2[i]);
                  f7 = paramArrayOfFloat2[i];
                  break label2126;
                }
              }
              else
              {
                i = paramChar1;
                j = i + 0;
                f7 = paramArrayOfFloat2[j];
                k = i + 1;
                f8 = paramArrayOfFloat2[k];
                c2 = i + 2;
                f9 = paramArrayOfFloat2[c2];
                i += 3;
                paramPath.quadTo(f7, f8, f9, paramArrayOfFloat2[i]);
                f9 = paramArrayOfFloat2[j];
                f10 = paramArrayOfFloat2[k];
                f8 = paramArrayOfFloat2[c2];
                f7 = paramArrayOfFloat2[i];
                break label2126;
              }
            }
            else
            {
              i = paramChar1 + '\000';
              paramPath.lineTo(paramArrayOfFloat2[i], f7);
              f8 = paramArrayOfFloat2[i];
              break label2126;
            }
          }
          else
          {
            i = paramChar1;
            f7 = paramArrayOfFloat2[(i + 0)];
            f8 = paramArrayOfFloat2[(i + 1)];
            k = i + 2;
            f9 = paramArrayOfFloat2[k];
            j = i + 3;
            f10 = paramArrayOfFloat2[j];
            c2 = i + 4;
            f6 = paramArrayOfFloat2[c2];
            i += 5;
            paramPath.cubicTo(f7, f8, f9, f10, f6, paramArrayOfFloat2[i]);
            f8 = paramArrayOfFloat2[c2];
            f7 = paramArrayOfFloat2[i];
            f9 = paramArrayOfFloat2[k];
            f10 = paramArrayOfFloat2[j];
            break label2126;
          }
        }
        else
        {
          i = paramChar1;
          c2 = i + 5;
          f9 = paramArrayOfFloat2[c2];
          k = i + 6;
          f4 = paramArrayOfFloat2[k];
          f10 = paramArrayOfFloat2[(i + 0)];
          f6 = paramArrayOfFloat2[(i + 1)];
          f5 = paramArrayOfFloat2[(i + 2)];
          if (paramArrayOfFloat2[(i + 3)] != 0.0F) {
            bool1 = true;
          } else {
            bool1 = false;
          }
          if (paramArrayOfFloat2[(i + 4)] != 0.0F) {
            bool2 = true;
          } else {
            bool2 = false;
          }
          drawArc(paramPath, f8, f7, f9, f4, f10, f6, f5, bool1, bool2);
          f8 = paramArrayOfFloat2[c2];
          f7 = paramArrayOfFloat2[k];
        }
        f10 = f7;
        f9 = f8;
        label2126:
        paramChar1 += c1;
        i = paramChar2;
      }
      paramArrayOfFloat1[0] = f8;
      paramArrayOfFloat1[1] = f7;
      paramArrayOfFloat1[2] = f9;
      paramArrayOfFloat1[3] = f10;
      paramArrayOfFloat1[4] = f3;
      paramArrayOfFloat1[5] = f1;
    }
    
    private static void arcToBezier(Path paramPath, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9)
    {
      int i = (int)Math.ceil(Math.abs(paramDouble9 * 4.0D / 3.141592653589793D));
      double d1 = Math.cos(paramDouble7);
      double d2 = Math.sin(paramDouble7);
      double d3 = Math.cos(paramDouble8);
      paramDouble7 = Math.sin(paramDouble8);
      double d4 = -paramDouble3;
      double d5 = d4 * d1;
      double d6 = paramDouble4 * d2;
      double d7 = d4 * d2;
      double d8 = paramDouble4 * d1;
      paramDouble9 /= i;
      paramDouble4 = paramDouble7 * d7 + d3 * d8;
      d3 = d5 * paramDouble7 - d6 * d3;
      int j = 0;
      paramDouble7 = paramDouble5;
      paramDouble5 = paramDouble4;
      d4 = paramDouble8;
      paramDouble8 = paramDouble6;
      paramDouble4 = d7;
      paramDouble6 = d2;
      for (;;)
      {
        d2 = paramDouble3;
        if (j >= i) {
          break;
        }
        double d9 = d4 + paramDouble9;
        double d10 = Math.sin(d9);
        double d11 = Math.cos(d9);
        double d12 = paramDouble1 + d2 * d1 * d11 - d6 * d10;
        d2 = paramDouble2 + d2 * paramDouble6 * d11 + d8 * d10;
        d7 = d5 * d10 - d6 * d11;
        d10 = d10 * paramDouble4 + d11 * d8;
        d11 = d9 - d4;
        d4 = Math.tan(d11 / 2.0D);
        d4 = Math.sin(d11) * (Math.sqrt(d4 * 3.0D * d4 + 4.0D) - 1.0D) / 3.0D;
        paramPath.rLineTo(0.0F, 0.0F);
        paramPath.cubicTo((float)(paramDouble7 + d3 * d4), (float)(paramDouble8 + paramDouble5 * d4), (float)(d12 - d4 * d7), (float)(d2 - d4 * d10), (float)d12, (float)d2);
        j++;
        paramDouble7 = d12;
        d4 = d9;
        paramDouble5 = d10;
        d3 = d7;
        paramDouble8 = d2;
      }
    }
    
    private static void drawArc(Path paramPath, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, boolean paramBoolean1, boolean paramBoolean2)
    {
      double d1 = Math.toRadians(paramFloat7);
      double d2 = Math.cos(d1);
      double d3 = Math.sin(d1);
      double d4 = paramFloat1;
      double d5 = paramFloat2;
      double d6 = paramFloat5;
      double d7 = (d4 * d2 + d5 * d3) / d6;
      double d8 = -paramFloat1;
      double d9 = paramFloat6;
      double d10 = (d8 * d3 + d5 * d2) / d9;
      double d11 = paramFloat3;
      d8 = paramFloat4;
      double d12 = (d11 * d2 + d8 * d3) / d6;
      double d13 = (-paramFloat3 * d3 + d8 * d2) / d9;
      double d14 = d7 - d12;
      double d15 = d10 - d13;
      d11 = (d7 + d12) / 2.0D;
      d8 = (d10 + d13) / 2.0D;
      double d16 = d14 * d14 + d15 * d15;
      if (d16 == 0.0D)
      {
        Log.w("PathParser", " Points are coincident");
        return;
      }
      double d17 = 1.0D / d16 - 0.25D;
      if (d17 < 0.0D)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Points are too far apart ");
        localStringBuilder.append(d16);
        Log.w("PathParser", localStringBuilder.toString());
        float f = (float)(Math.sqrt(d16) / 1.99999D);
        drawArc(paramPath, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5 * f, paramFloat6 * f, paramFloat7, paramBoolean1, paramBoolean2);
        return;
      }
      d17 = Math.sqrt(d17);
      d14 *= d17;
      d15 = d17 * d15;
      if (paramBoolean1 == paramBoolean2)
      {
        d11 -= d15;
        d8 += d14;
      }
      else
      {
        d11 += d15;
        d8 -= d14;
      }
      d15 = Math.atan2(d10 - d8, d7 - d11);
      d10 = Math.atan2(d13 - d8, d12 - d11) - d15;
      boolean bool = d10 < 0.0D;
      if (!bool) {
        paramBoolean1 = true;
      } else {
        paramBoolean1 = false;
      }
      d7 = d10;
      if (paramBoolean2 != paramBoolean1) {
        if (bool) {
          d7 = d10 - 6.283185307179586D;
        } else {
          d7 = d10 + 6.283185307179586D;
        }
      }
      d11 *= d6;
      d8 *= d9;
      arcToBezier(paramPath, d11 * d2 - d8 * d3, d11 * d3 + d8 * d2, d6, d9, d4, d5, d1, d15, d7);
    }
    
    public static void nodesToPath(PathDataNode[] paramArrayOfPathDataNode, Path paramPath)
    {
      float[] arrayOfFloat = new float[6];
      char c1 = 'm';
      int i = 0;
      for (char c2 = c1; i < paramArrayOfPathDataNode.length; c2 = c1)
      {
        addCommand(paramPath, arrayOfFloat, c2, paramArrayOfPathDataNode[i].mType, paramArrayOfPathDataNode[i].mParams);
        c1 = paramArrayOfPathDataNode[i].mType;
        i++;
      }
    }
    
    public void interpolatePathDataNode(PathDataNode paramPathDataNode1, PathDataNode paramPathDataNode2, float paramFloat)
    {
      this.mType = ((char)paramPathDataNode1.mType);
      for (int i = 0;; i++)
      {
        float[] arrayOfFloat = paramPathDataNode1.mParams;
        if (i >= arrayOfFloat.length) {
          break;
        }
        this.mParams[i] = (arrayOfFloat[i] * (1.0F - paramFloat) + paramPathDataNode2.mParams[i] * paramFloat);
      }
    }
  }
}

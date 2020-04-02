package androidx.core.graphics;

import android.graphics.Path;
import android.graphics.PointF;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class PathUtils
{
  private PathUtils() {}
  
  public static Collection<PathSegment> flatten(Path paramPath)
  {
    return flatten(paramPath, 0.5F);
  }
  
  public static Collection<PathSegment> flatten(Path paramPath, float paramFloat)
  {
    paramPath = paramPath.approximate(paramFloat);
    int i = paramPath.length / 3;
    ArrayList localArrayList = new ArrayList(i);
    for (int j = 1; j < i; j++)
    {
      int k = j * 3;
      int m = (j - 1) * 3;
      float f1 = paramPath[k];
      float f2 = paramPath[(k + 1)];
      float f3 = paramPath[(k + 2)];
      float f4 = paramPath[m];
      paramFloat = paramPath[(m + 1)];
      float f5 = paramPath[(m + 2)];
      if ((f1 != f4) && ((f2 != paramFloat) || (f3 != f5))) {
        localArrayList.add(new PathSegment(new PointF(paramFloat, f5), f4, new PointF(f2, f3), f1));
      }
    }
    return localArrayList;
  }
}

package androidx.camera.core.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class SurfaceCombination
{
  private final List<SurfaceConfig> mSurfaceConfigList = new ArrayList();
  
  public SurfaceCombination() {}
  
  private static void generateArrangements(List<int[]> paramList, int paramInt1, int[] paramArrayOfInt, int paramInt2)
  {
    if (paramInt2 >= paramArrayOfInt.length)
    {
      paramList.add(paramArrayOfInt.clone());
      return;
    }
    for (int i = 0; i < paramInt1; i++)
    {
      for (int j = 0; j < paramInt2; j++) {
        if (i == paramArrayOfInt[j])
        {
          j = 1;
          break label60;
        }
      }
      j = 0;
      label60:
      if (j == 0)
      {
        paramArrayOfInt[paramInt2] = i;
        generateArrangements(paramList, paramInt1, paramArrayOfInt, paramInt2 + 1);
      }
    }
  }
  
  private List<int[]> getElementsArrangements(int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    generateArrangements(localArrayList, paramInt, new int[paramInt], 0);
    return localArrayList;
  }
  
  public boolean addSurfaceConfig(SurfaceConfig paramSurfaceConfig)
  {
    return this.mSurfaceConfigList.add(paramSurfaceConfig);
  }
  
  public List<SurfaceConfig> getSurfaceConfigList()
  {
    return this.mSurfaceConfigList;
  }
  
  public boolean isSupported(List<SurfaceConfig> paramList)
  {
    boolean bool1 = paramList.isEmpty();
    boolean bool2 = true;
    if (bool1) {
      return true;
    }
    if (paramList.size() > this.mSurfaceConfigList.size()) {
      return false;
    }
    Iterator localIterator = getElementsArrangements(this.mSurfaceConfigList.size()).iterator();
    while (localIterator.hasNext())
    {
      int[] arrayOfInt = (int[])localIterator.next();
      boolean bool3 = true;
      int i = 0;
      boolean bool4;
      for (;;)
      {
        bool4 = bool3;
        if (i >= this.mSurfaceConfigList.size()) {
          break;
        }
        bool4 = bool3;
        if (arrayOfInt[i] < paramList.size())
        {
          bool3 &= ((SurfaceConfig)this.mSurfaceConfigList.get(i)).isSupported((SurfaceConfig)paramList.get(arrayOfInt[i]));
          bool4 = bool3;
          if (!bool3)
          {
            bool4 = bool3;
            break;
          }
        }
        i++;
        bool3 = bool4;
      }
      if (bool4) {
        return bool2;
      }
    }
    bool2 = false;
    return bool2;
  }
  
  public boolean removeSurfaceConfig(SurfaceConfig paramSurfaceConfig)
  {
    return this.mSurfaceConfigList.remove(paramSurfaceConfig);
  }
}

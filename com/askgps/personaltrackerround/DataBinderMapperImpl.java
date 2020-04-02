package com.askgps.personaltrackerround;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl
  extends DataBinderMapper
{
  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(0);
  
  public DataBinderMapperImpl() {}
  
  public List<DataBinderMapper> collectDependencies()
  {
    ArrayList localArrayList = new ArrayList(2);
    localArrayList.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    localArrayList.add(new com.askgps.personaltrackercore.DataBinderMapperImpl());
    return localArrayList;
  }
  
  public String convertBrIdToString(int paramInt)
  {
    return (String)InnerBrLookup.sKeys.get(paramInt);
  }
  
  public ViewDataBinding getDataBinder(DataBindingComponent paramDataBindingComponent, View paramView, int paramInt)
  {
    if ((INTERNAL_LAYOUT_ID_LOOKUP.get(paramInt) > 0) && (paramView.getTag() == null)) {
      throw new RuntimeException("view must have a tag");
    }
    return null;
  }
  
  public ViewDataBinding getDataBinder(DataBindingComponent paramDataBindingComponent, View[] paramArrayOfView, int paramInt)
  {
    if ((paramArrayOfView != null) && (paramArrayOfView.length != 0) && (INTERNAL_LAYOUT_ID_LOOKUP.get(paramInt) > 0) && (paramArrayOfView[0].getTag() == null)) {
      throw new RuntimeException("view must have a tag");
    }
    return null;
  }
  
  public int getLayoutId(String paramString)
  {
    int i = 0;
    if (paramString == null) {
      return 0;
    }
    paramString = (Integer)InnerLayoutIdLookup.sKeys.get(paramString);
    if (paramString != null) {
      i = paramString.intValue();
    }
    return i;
  }
  
  private static class InnerBrLookup
  {
    static final SparseArray<String> sKeys;
    
    static
    {
      SparseArray localSparseArray = new SparseArray(4);
      sKeys = localSparseArray;
      localSparseArray.put(0, "_all");
      sKeys.put(1, "phoneNumber");
      sKeys.put(2, "qr");
      sKeys.put(3, "vm");
    }
    
    private InnerBrLookup() {}
  }
  
  private static class InnerLayoutIdLookup
  {
    static final HashMap<String, Integer> sKeys = new HashMap(0);
    
    private InnerLayoutIdLookup() {}
  }
}

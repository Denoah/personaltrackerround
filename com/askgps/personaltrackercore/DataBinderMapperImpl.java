package com.askgps.personaltrackercore;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.askgps.personaltrackercore.databinding.PhoneBookFragmentBindingImpl;
import com.askgps.personaltrackercore.databinding.PhoneNumberBindingImpl;
import com.askgps.personaltrackercore.databinding.QrDialogFragmentBindingImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl
  extends DataBinderMapper
{
  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP;
  private static final int LAYOUT_PHONEBOOKFRAGMENT = 1;
  private static final int LAYOUT_PHONENUMBER = 2;
  private static final int LAYOUT_QRDIALOGFRAGMENT = 3;
  
  static
  {
    SparseIntArray localSparseIntArray = new SparseIntArray(3);
    INTERNAL_LAYOUT_ID_LOOKUP = localSparseIntArray;
    localSparseIntArray.put(R.layout.phone_book_fragment, 1);
    INTERNAL_LAYOUT_ID_LOOKUP.put(R.layout.phone_number, 2);
    INTERNAL_LAYOUT_ID_LOOKUP.put(R.layout.qr_dialog_fragment, 3);
  }
  
  public DataBinderMapperImpl() {}
  
  public List<DataBinderMapper> collectDependencies()
  {
    ArrayList localArrayList = new ArrayList(1);
    localArrayList.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return localArrayList;
  }
  
  public String convertBrIdToString(int paramInt)
  {
    return (String)InnerBrLookup.sKeys.get(paramInt);
  }
  
  public ViewDataBinding getDataBinder(DataBindingComponent paramDataBindingComponent, View paramView, int paramInt)
  {
    paramInt = INTERNAL_LAYOUT_ID_LOOKUP.get(paramInt);
    if (paramInt > 0)
    {
      Object localObject = paramView.getTag();
      if (localObject != null)
      {
        if (paramInt != 1)
        {
          if (paramInt != 2)
          {
            if (paramInt == 3)
            {
              if ("layout/qr_dialog_fragment_0".equals(localObject)) {
                return new QrDialogFragmentBindingImpl(paramDataBindingComponent, paramView);
              }
              paramDataBindingComponent = new StringBuilder();
              paramDataBindingComponent.append("The tag for qr_dialog_fragment is invalid. Received: ");
              paramDataBindingComponent.append(localObject);
              throw new IllegalArgumentException(paramDataBindingComponent.toString());
            }
          }
          else
          {
            if ("layout/phone_number_0".equals(localObject)) {
              return new PhoneNumberBindingImpl(paramDataBindingComponent, paramView);
            }
            paramDataBindingComponent = new StringBuilder();
            paramDataBindingComponent.append("The tag for phone_number is invalid. Received: ");
            paramDataBindingComponent.append(localObject);
            throw new IllegalArgumentException(paramDataBindingComponent.toString());
          }
        }
        else
        {
          if ("layout/phone_book_fragment_0".equals(localObject)) {
            return new PhoneBookFragmentBindingImpl(paramDataBindingComponent, paramView);
          }
          paramDataBindingComponent = new StringBuilder();
          paramDataBindingComponent.append("The tag for phone_book_fragment is invalid. Received: ");
          paramDataBindingComponent.append(localObject);
          throw new IllegalArgumentException(paramDataBindingComponent.toString());
        }
      }
      else {
        throw new RuntimeException("view must have a tag");
      }
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
    static final HashMap<String, Integer> sKeys;
    
    static
    {
      HashMap localHashMap = new HashMap(3);
      sKeys = localHashMap;
      localHashMap.put("layout/phone_book_fragment_0", Integer.valueOf(R.layout.phone_book_fragment));
      sKeys.put("layout/phone_number_0", Integer.valueOf(R.layout.phone_number));
      sKeys.put("layout/qr_dialog_fragment_0", Integer.valueOf(R.layout.qr_dialog_fragment));
    }
    
    private InnerLayoutIdLookup() {}
  }
}

package androidx.camera.camera2.impl;

import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.MultiValueSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class CameraEventCallbacks
  extends MultiValueSet<CameraEventCallback>
{
  public CameraEventCallbacks(CameraEventCallback... paramVarArgs)
  {
    addAll(Arrays.asList(paramVarArgs));
  }
  
  public static CameraEventCallbacks createEmptyCallback()
  {
    return new CameraEventCallbacks(new CameraEventCallback[0]);
  }
  
  public MultiValueSet<CameraEventCallback> clone()
  {
    CameraEventCallbacks localCameraEventCallbacks = createEmptyCallback();
    localCameraEventCallbacks.addAll(getAllItems());
    return localCameraEventCallbacks;
  }
  
  public ComboCameraEventCallback createComboCallback()
  {
    return new ComboCameraEventCallback(getAllItems());
  }
  
  public static final class ComboCameraEventCallback
  {
    private final List<CameraEventCallback> mCallbacks = new ArrayList();
    
    ComboCameraEventCallback(List<CameraEventCallback> paramList)
    {
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        paramList = (CameraEventCallback)localIterator.next();
        this.mCallbacks.add(paramList);
      }
    }
    
    public List<CameraEventCallback> getCallbacks()
    {
      return this.mCallbacks;
    }
    
    public List<CaptureConfig> onDisableSession()
    {
      LinkedList localLinkedList = new LinkedList();
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext())
      {
        CaptureConfig localCaptureConfig = ((CameraEventCallback)localIterator.next()).onDisableSession();
        if (localCaptureConfig != null) {
          localLinkedList.add(localCaptureConfig);
        }
      }
      return localLinkedList;
    }
    
    public List<CaptureConfig> onEnableSession()
    {
      LinkedList localLinkedList = new LinkedList();
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext())
      {
        CaptureConfig localCaptureConfig = ((CameraEventCallback)localIterator.next()).onEnableSession();
        if (localCaptureConfig != null) {
          localLinkedList.add(localCaptureConfig);
        }
      }
      return localLinkedList;
    }
    
    public List<CaptureConfig> onPresetSession()
    {
      LinkedList localLinkedList = new LinkedList();
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext())
      {
        CaptureConfig localCaptureConfig = ((CameraEventCallback)localIterator.next()).onPresetSession();
        if (localCaptureConfig != null) {
          localLinkedList.add(localCaptureConfig);
        }
      }
      return localLinkedList;
    }
    
    public List<CaptureConfig> onRepeating()
    {
      LinkedList localLinkedList = new LinkedList();
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext())
      {
        CaptureConfig localCaptureConfig = ((CameraEventCallback)localIterator.next()).onRepeating();
        if (localCaptureConfig != null) {
          localLinkedList.add(localCaptureConfig);
        }
      }
      return localLinkedList;
    }
  }
}

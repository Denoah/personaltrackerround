package androidx.camera.core.impl;

import android.hardware.camera2.CameraCaptureSession.StateCallback;
import android.hardware.camera2.CameraDevice.StateCallback;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class SessionConfig
{
  private final List<CameraDevice.StateCallback> mDeviceStateCallbacks;
  private final List<ErrorListener> mErrorListeners;
  private final CaptureConfig mRepeatingCaptureConfig;
  private final List<CameraCaptureSession.StateCallback> mSessionStateCallbacks;
  private final List<CameraCaptureCallback> mSingleCameraCaptureCallbacks;
  private final List<DeferrableSurface> mSurfaces;
  
  SessionConfig(List<DeferrableSurface> paramList, List<CameraDevice.StateCallback> paramList1, List<CameraCaptureSession.StateCallback> paramList2, List<CameraCaptureCallback> paramList3, List<ErrorListener> paramList4, CaptureConfig paramCaptureConfig)
  {
    this.mSurfaces = paramList;
    this.mDeviceStateCallbacks = Collections.unmodifiableList(paramList1);
    this.mSessionStateCallbacks = Collections.unmodifiableList(paramList2);
    this.mSingleCameraCaptureCallbacks = Collections.unmodifiableList(paramList3);
    this.mErrorListeners = Collections.unmodifiableList(paramList4);
    this.mRepeatingCaptureConfig = paramCaptureConfig;
  }
  
  public static SessionConfig defaultEmptySessionConfig()
  {
    return new SessionConfig(new ArrayList(), new ArrayList(0), new ArrayList(0), new ArrayList(0), new ArrayList(0), new CaptureConfig.Builder().build());
  }
  
  public List<CameraDevice.StateCallback> getDeviceStateCallbacks()
  {
    return this.mDeviceStateCallbacks;
  }
  
  public List<ErrorListener> getErrorListeners()
  {
    return this.mErrorListeners;
  }
  
  public Config getImplementationOptions()
  {
    return this.mRepeatingCaptureConfig.getImplementationOptions();
  }
  
  public List<CameraCaptureCallback> getRepeatingCameraCaptureCallbacks()
  {
    return this.mRepeatingCaptureConfig.getCameraCaptureCallbacks();
  }
  
  public CaptureConfig getRepeatingCaptureConfig()
  {
    return this.mRepeatingCaptureConfig;
  }
  
  public List<CameraCaptureSession.StateCallback> getSessionStateCallbacks()
  {
    return this.mSessionStateCallbacks;
  }
  
  public List<CameraCaptureCallback> getSingleCameraCaptureCallbacks()
  {
    return this.mSingleCameraCaptureCallbacks;
  }
  
  public List<DeferrableSurface> getSurfaces()
  {
    return Collections.unmodifiableList(this.mSurfaces);
  }
  
  public int getTemplateType()
  {
    return this.mRepeatingCaptureConfig.getTemplateType();
  }
  
  static class BaseBuilder
  {
    final CaptureConfig.Builder mCaptureConfigBuilder = new CaptureConfig.Builder();
    final List<CameraDevice.StateCallback> mDeviceStateCallbacks = new ArrayList();
    final List<SessionConfig.ErrorListener> mErrorListeners = new ArrayList();
    final List<CameraCaptureSession.StateCallback> mSessionStateCallbacks = new ArrayList();
    final List<CameraCaptureCallback> mSingleCameraCaptureCallbacks = new ArrayList();
    final Set<DeferrableSurface> mSurfaces = new HashSet();
    
    BaseBuilder() {}
  }
  
  public static class Builder
    extends SessionConfig.BaseBuilder
  {
    public Builder() {}
    
    public static Builder createFrom(UseCaseConfig<?> paramUseCaseConfig)
    {
      SessionConfig.OptionUnpacker localOptionUnpacker = paramUseCaseConfig.getSessionOptionUnpacker(null);
      if (localOptionUnpacker != null)
      {
        localObject = new Builder();
        localOptionUnpacker.unpack(paramUseCaseConfig, (Builder)localObject);
        return localObject;
      }
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Implementation is missing option unpacker for ");
      ((StringBuilder)localObject).append(paramUseCaseConfig.getTargetName(paramUseCaseConfig.toString()));
      throw new IllegalStateException(((StringBuilder)localObject).toString());
    }
    
    public void addAllCameraCaptureCallbacks(Collection<CameraCaptureCallback> paramCollection)
    {
      this.mCaptureConfigBuilder.addAllCameraCaptureCallbacks(paramCollection);
      this.mSingleCameraCaptureCallbacks.addAll(paramCollection);
    }
    
    public void addAllDeviceStateCallbacks(Collection<CameraDevice.StateCallback> paramCollection)
    {
      paramCollection = paramCollection.iterator();
      while (paramCollection.hasNext()) {
        addDeviceStateCallback((CameraDevice.StateCallback)paramCollection.next());
      }
    }
    
    public void addAllRepeatingCameraCaptureCallbacks(Collection<CameraCaptureCallback> paramCollection)
    {
      this.mCaptureConfigBuilder.addAllCameraCaptureCallbacks(paramCollection);
    }
    
    public void addAllSessionStateCallbacks(List<CameraCaptureSession.StateCallback> paramList)
    {
      paramList = paramList.iterator();
      while (paramList.hasNext()) {
        addSessionStateCallback((CameraCaptureSession.StateCallback)paramList.next());
      }
    }
    
    public void addCameraCaptureCallback(CameraCaptureCallback paramCameraCaptureCallback)
    {
      this.mCaptureConfigBuilder.addCameraCaptureCallback(paramCameraCaptureCallback);
      this.mSingleCameraCaptureCallbacks.add(paramCameraCaptureCallback);
    }
    
    public void addDeviceStateCallback(CameraDevice.StateCallback paramStateCallback)
    {
      if (!this.mDeviceStateCallbacks.contains(paramStateCallback))
      {
        this.mDeviceStateCallbacks.add(paramStateCallback);
        return;
      }
      throw new IllegalArgumentException("Duplicate device state callback.");
    }
    
    public void addErrorListener(SessionConfig.ErrorListener paramErrorListener)
    {
      this.mErrorListeners.add(paramErrorListener);
    }
    
    public void addImplementationOptions(Config paramConfig)
    {
      this.mCaptureConfigBuilder.addImplementationOptions(paramConfig);
    }
    
    public void addNonRepeatingSurface(DeferrableSurface paramDeferrableSurface)
    {
      this.mSurfaces.add(paramDeferrableSurface);
    }
    
    public void addRepeatingCameraCaptureCallback(CameraCaptureCallback paramCameraCaptureCallback)
    {
      this.mCaptureConfigBuilder.addCameraCaptureCallback(paramCameraCaptureCallback);
    }
    
    public void addSessionStateCallback(CameraCaptureSession.StateCallback paramStateCallback)
    {
      if (!this.mSessionStateCallbacks.contains(paramStateCallback))
      {
        this.mSessionStateCallbacks.add(paramStateCallback);
        return;
      }
      throw new IllegalArgumentException("Duplicate session state callback.");
    }
    
    public void addSurface(DeferrableSurface paramDeferrableSurface)
    {
      this.mSurfaces.add(paramDeferrableSurface);
      this.mCaptureConfigBuilder.addSurface(paramDeferrableSurface);
    }
    
    public SessionConfig build()
    {
      return new SessionConfig(new ArrayList(this.mSurfaces), this.mDeviceStateCallbacks, this.mSessionStateCallbacks, this.mSingleCameraCaptureCallbacks, this.mErrorListeners, this.mCaptureConfigBuilder.build());
    }
    
    public void clearSurfaces()
    {
      this.mSurfaces.clear();
      this.mCaptureConfigBuilder.clearSurfaces();
    }
    
    public List<CameraCaptureCallback> getSingleCameraCaptureCallbacks()
    {
      return Collections.unmodifiableList(this.mSingleCameraCaptureCallbacks);
    }
    
    public void removeSurface(DeferrableSurface paramDeferrableSurface)
    {
      this.mSurfaces.remove(paramDeferrableSurface);
      this.mCaptureConfigBuilder.removeSurface(paramDeferrableSurface);
    }
    
    public void setImplementationOptions(Config paramConfig)
    {
      this.mCaptureConfigBuilder.setImplementationOptions(paramConfig);
    }
    
    public void setTag(Object paramObject)
    {
      this.mCaptureConfigBuilder.setTag(paramObject);
    }
    
    public void setTemplateType(int paramInt)
    {
      this.mCaptureConfigBuilder.setTemplateType(paramInt);
    }
  }
  
  public static abstract interface ErrorListener
  {
    public abstract void onError(SessionConfig paramSessionConfig, SessionConfig.SessionError paramSessionError);
  }
  
  public static abstract interface OptionUnpacker
  {
    public abstract void unpack(UseCaseConfig<?> paramUseCaseConfig, SessionConfig.Builder paramBuilder);
  }
  
  public static enum SessionError
  {
    static
    {
      SessionError localSessionError = new SessionError("SESSION_ERROR_UNKNOWN", 1);
      SESSION_ERROR_UNKNOWN = localSessionError;
      $VALUES = new SessionError[] { SESSION_ERROR_SURFACE_NEEDS_RESET, localSessionError };
    }
    
    private SessionError() {}
  }
  
  public static final class ValidatingBuilder
    extends SessionConfig.BaseBuilder
  {
    private static final String TAG = "ValidatingBuilder";
    private boolean mTemplateSet = false;
    private boolean mValid = true;
    
    public ValidatingBuilder() {}
    
    public void add(SessionConfig paramSessionConfig)
    {
      Object localObject1 = paramSessionConfig.getRepeatingCaptureConfig();
      if (!this.mTemplateSet)
      {
        this.mCaptureConfigBuilder.setTemplateType(((CaptureConfig)localObject1).getTemplateType());
        this.mTemplateSet = true;
      }
      else if (this.mCaptureConfigBuilder.getTemplateType() != ((CaptureConfig)localObject1).getTemplateType())
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("Invalid configuration due to template type: ");
        ((StringBuilder)localObject2).append(this.mCaptureConfigBuilder.getTemplateType());
        ((StringBuilder)localObject2).append(" != ");
        ((StringBuilder)localObject2).append(((CaptureConfig)localObject1).getTemplateType());
        Log.d("ValidatingBuilder", ((StringBuilder)localObject2).toString());
        this.mValid = false;
      }
      Object localObject2 = paramSessionConfig.getRepeatingCaptureConfig().getTag();
      if (localObject2 != null) {
        this.mCaptureConfigBuilder.setTag(localObject2);
      }
      this.mDeviceStateCallbacks.addAll(paramSessionConfig.getDeviceStateCallbacks());
      this.mSessionStateCallbacks.addAll(paramSessionConfig.getSessionStateCallbacks());
      this.mCaptureConfigBuilder.addAllCameraCaptureCallbacks(paramSessionConfig.getRepeatingCameraCaptureCallbacks());
      this.mSingleCameraCaptureCallbacks.addAll(paramSessionConfig.getSingleCameraCaptureCallbacks());
      this.mErrorListeners.addAll(paramSessionConfig.getErrorListeners());
      this.mSurfaces.addAll(paramSessionConfig.getSurfaces());
      this.mCaptureConfigBuilder.getSurfaces().addAll(((CaptureConfig)localObject1).getSurfaces());
      if (!this.mSurfaces.containsAll(this.mCaptureConfigBuilder.getSurfaces()))
      {
        Log.d("ValidatingBuilder", "Invalid configuration due to capture request surfaces are not a subset of surfaces");
        this.mValid = false;
      }
      localObject2 = ((CaptureConfig)localObject1).getImplementationOptions();
      Config localConfig = this.mCaptureConfigBuilder.getImplementationOptions();
      MutableOptionsBundle localMutableOptionsBundle = MutableOptionsBundle.create();
      Iterator localIterator = ((Config)localObject2).listOptions().iterator();
      while (localIterator.hasNext())
      {
        paramSessionConfig = (Config.Option)localIterator.next();
        Object localObject3 = ((Config)localObject2).retrieveOption(paramSessionConfig, null);
        if ((!(localObject3 instanceof MultiValueSet)) && (localConfig.containsOption(paramSessionConfig)))
        {
          Object localObject4 = localConfig.retrieveOption(paramSessionConfig, null);
          if (!Objects.equals(localObject3, localObject4))
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("Invalid configuration due to conflicting option: ");
            ((StringBuilder)localObject1).append(paramSessionConfig.getId());
            ((StringBuilder)localObject1).append(" : ");
            ((StringBuilder)localObject1).append(localObject3);
            ((StringBuilder)localObject1).append(" != ");
            ((StringBuilder)localObject1).append(localObject4);
            Log.d("ValidatingBuilder", ((StringBuilder)localObject1).toString());
            this.mValid = false;
          }
        }
        else
        {
          localMutableOptionsBundle.insertOption(paramSessionConfig, ((Config)localObject2).retrieveOption(paramSessionConfig));
        }
      }
      this.mCaptureConfigBuilder.addImplementationOptions(localMutableOptionsBundle);
    }
    
    public SessionConfig build()
    {
      if (this.mValid) {
        return new SessionConfig(new ArrayList(this.mSurfaces), this.mDeviceStateCallbacks, this.mSessionStateCallbacks, this.mSingleCameraCaptureCallbacks, this.mErrorListeners, this.mCaptureConfigBuilder.build());
      }
      throw new IllegalArgumentException("Unsupported session configuration combination");
    }
    
    public boolean isValid()
    {
      boolean bool;
      if ((this.mTemplateSet) && (this.mValid)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
}

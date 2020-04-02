package androidx.camera.camera2.internal.compat.params;

import android.hardware.camera2.CameraCaptureSession.StateCallback;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.InputConfiguration;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.SessionConfiguration;
import android.os.Build.VERSION;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

public final class SessionConfigurationCompat
{
  public static final int SESSION_HIGH_SPEED = 1;
  public static final int SESSION_REGULAR = 0;
  private final SessionConfigurationCompatImpl mImpl;
  
  public SessionConfigurationCompat(int paramInt, List<OutputConfigurationCompat> paramList, Executor paramExecutor, CameraCaptureSession.StateCallback paramStateCallback)
  {
    if (Build.VERSION.SDK_INT < 28) {
      this.mImpl = new SessionConfigurationCompatBaseImpl(paramInt, paramList, paramExecutor, paramStateCallback);
    } else {
      this.mImpl = new SessionConfigurationCompatApi28Impl(paramInt, paramList, paramExecutor, paramStateCallback);
    }
  }
  
  private SessionConfigurationCompat(SessionConfigurationCompatImpl paramSessionConfigurationCompatImpl)
  {
    this.mImpl = paramSessionConfigurationCompatImpl;
  }
  
  public static List<OutputConfiguration> transformFromCompat(List<OutputConfigurationCompat> paramList)
  {
    ArrayList localArrayList = new ArrayList(paramList.size());
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      localArrayList.add((OutputConfiguration)((OutputConfigurationCompat)paramList.next()).unwrap());
    }
    return localArrayList;
  }
  
  static List<OutputConfigurationCompat> transformToCompat(List<OutputConfiguration> paramList)
  {
    ArrayList localArrayList = new ArrayList(paramList.size());
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      localArrayList.add(OutputConfigurationCompat.wrap((OutputConfiguration)paramList.next()));
    }
    return localArrayList;
  }
  
  public static SessionConfigurationCompat wrap(Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    if (Build.VERSION.SDK_INT < 28) {
      return null;
    }
    return new SessionConfigurationCompat(new SessionConfigurationCompatApi28Impl(paramObject));
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof SessionConfigurationCompat)) {
      return false;
    }
    return this.mImpl.equals(((SessionConfigurationCompat)paramObject).mImpl);
  }
  
  public Executor getExecutor()
  {
    return this.mImpl.getExecutor();
  }
  
  public InputConfigurationCompat getInputConfiguration()
  {
    return this.mImpl.getInputConfiguration();
  }
  
  public List<OutputConfigurationCompat> getOutputConfigurations()
  {
    return this.mImpl.getOutputConfigurations();
  }
  
  public CaptureRequest getSessionParameters()
  {
    return this.mImpl.getSessionParameters();
  }
  
  public int getSessionType()
  {
    return this.mImpl.getSessionType();
  }
  
  public CameraCaptureSession.StateCallback getStateCallback()
  {
    return this.mImpl.getStateCallback();
  }
  
  public void setInputConfiguration(InputConfigurationCompat paramInputConfigurationCompat)
  {
    this.mImpl.setInputConfiguration(paramInputConfigurationCompat);
  }
  
  public void setSessionParameters(CaptureRequest paramCaptureRequest)
  {
    this.mImpl.setSessionParameters(paramCaptureRequest);
  }
  
  public Object unwrap()
  {
    return this.mImpl.getSessionConfiguration();
  }
  
  private static final class SessionConfigurationCompatApi28Impl
    implements SessionConfigurationCompat.SessionConfigurationCompatImpl
  {
    private final SessionConfiguration mObject;
    private final List<OutputConfigurationCompat> mOutputConfigurations;
    
    SessionConfigurationCompatApi28Impl(int paramInt, List<OutputConfigurationCompat> paramList, Executor paramExecutor, CameraCaptureSession.StateCallback paramStateCallback)
    {
      this(new SessionConfiguration(paramInt, SessionConfigurationCompat.transformFromCompat(paramList), paramExecutor, paramStateCallback));
    }
    
    SessionConfigurationCompatApi28Impl(Object paramObject)
    {
      paramObject = (SessionConfiguration)paramObject;
      this.mObject = paramObject;
      this.mOutputConfigurations = Collections.unmodifiableList(SessionConfigurationCompat.transformToCompat(paramObject.getOutputConfigurations()));
    }
    
    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof SessionConfigurationCompatApi28Impl)) {
        return false;
      }
      return Objects.equals(this.mObject, ((SessionConfigurationCompatApi28Impl)paramObject).mObject);
    }
    
    public Executor getExecutor()
    {
      return this.mObject.getExecutor();
    }
    
    public InputConfigurationCompat getInputConfiguration()
    {
      return InputConfigurationCompat.wrap(this.mObject.getInputConfiguration());
    }
    
    public List<OutputConfigurationCompat> getOutputConfigurations()
    {
      return this.mOutputConfigurations;
    }
    
    public Object getSessionConfiguration()
    {
      return this.mObject;
    }
    
    public CaptureRequest getSessionParameters()
    {
      return this.mObject.getSessionParameters();
    }
    
    public int getSessionType()
    {
      return this.mObject.getSessionType();
    }
    
    public CameraCaptureSession.StateCallback getStateCallback()
    {
      return this.mObject.getStateCallback();
    }
    
    public int hashCode()
    {
      return this.mObject.hashCode();
    }
    
    public void setInputConfiguration(InputConfigurationCompat paramInputConfigurationCompat)
    {
      this.mObject.setInputConfiguration((InputConfiguration)paramInputConfigurationCompat.unwrap());
    }
    
    public void setSessionParameters(CaptureRequest paramCaptureRequest)
    {
      this.mObject.setSessionParameters(paramCaptureRequest);
    }
  }
  
  private static final class SessionConfigurationCompatBaseImpl
    implements SessionConfigurationCompat.SessionConfigurationCompatImpl
  {
    private final Executor mExecutor;
    private InputConfigurationCompat mInputConfig = null;
    private final List<OutputConfigurationCompat> mOutputConfigurations;
    private CaptureRequest mSessionParameters = null;
    private int mSessionType;
    private final CameraCaptureSession.StateCallback mStateCallback;
    
    SessionConfigurationCompatBaseImpl(int paramInt, List<OutputConfigurationCompat> paramList, Executor paramExecutor, CameraCaptureSession.StateCallback paramStateCallback)
    {
      this.mSessionType = paramInt;
      this.mOutputConfigurations = Collections.unmodifiableList(new ArrayList(paramList));
      this.mStateCallback = paramStateCallback;
      this.mExecutor = paramExecutor;
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {
        return true;
      }
      if ((paramObject instanceof SessionConfigurationCompatBaseImpl))
      {
        paramObject = (SessionConfigurationCompatBaseImpl)paramObject;
        if ((this.mInputConfig == paramObject.mInputConfig) && (this.mSessionType == paramObject.mSessionType) && (this.mOutputConfigurations.size() == paramObject.mOutputConfigurations.size()))
        {
          for (int i = 0; i < this.mOutputConfigurations.size(); i++) {
            if (!((OutputConfigurationCompat)this.mOutputConfigurations.get(i)).equals(paramObject.mOutputConfigurations.get(i))) {
              return false;
            }
          }
          return true;
        }
      }
      return false;
    }
    
    public Executor getExecutor()
    {
      return this.mExecutor;
    }
    
    public InputConfigurationCompat getInputConfiguration()
    {
      return this.mInputConfig;
    }
    
    public List<OutputConfigurationCompat> getOutputConfigurations()
    {
      return this.mOutputConfigurations;
    }
    
    public Object getSessionConfiguration()
    {
      return null;
    }
    
    public CaptureRequest getSessionParameters()
    {
      return this.mSessionParameters;
    }
    
    public int getSessionType()
    {
      return this.mSessionType;
    }
    
    public CameraCaptureSession.StateCallback getStateCallback()
    {
      return this.mStateCallback;
    }
    
    public int hashCode()
    {
      int i = this.mOutputConfigurations.hashCode() ^ 0x1F;
      InputConfigurationCompat localInputConfigurationCompat = this.mInputConfig;
      int j;
      if (localInputConfigurationCompat == null) {
        j = 0;
      } else {
        j = localInputConfigurationCompat.hashCode();
      }
      j ^= (i << 5) - i;
      return this.mSessionType ^ (j << 5) - j;
    }
    
    public void setInputConfiguration(InputConfigurationCompat paramInputConfigurationCompat)
    {
      if (this.mSessionType != 1)
      {
        this.mInputConfig = paramInputConfigurationCompat;
        return;
      }
      throw new UnsupportedOperationException("Method not supported for high speed session types");
    }
    
    public void setSessionParameters(CaptureRequest paramCaptureRequest)
    {
      this.mSessionParameters = paramCaptureRequest;
    }
  }
  
  private static abstract interface SessionConfigurationCompatImpl
  {
    public abstract Executor getExecutor();
    
    public abstract InputConfigurationCompat getInputConfiguration();
    
    public abstract List<OutputConfigurationCompat> getOutputConfigurations();
    
    public abstract Object getSessionConfiguration();
    
    public abstract CaptureRequest getSessionParameters();
    
    public abstract int getSessionType();
    
    public abstract CameraCaptureSession.StateCallback getStateCallback();
    
    public abstract void setInputConfiguration(InputConfigurationCompat paramInputConfigurationCompat);
    
    public abstract void setSessionParameters(CaptureRequest paramCaptureRequest);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface SessionMode {}
}

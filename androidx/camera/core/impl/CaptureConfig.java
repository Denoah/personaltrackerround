package androidx.camera.core.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class CaptureConfig
{
  public static final Config.Option<Integer> OPTION_ROTATION = Config.Option.create("camerax.core.captureConfig.rotation", Integer.TYPE);
  final List<CameraCaptureCallback> mCameraCaptureCallbacks;
  final Config mImplementationOptions;
  final List<DeferrableSurface> mSurfaces;
  private final Object mTag;
  final int mTemplateType;
  private final boolean mUseRepeatingSurface;
  
  CaptureConfig(List<DeferrableSurface> paramList, Config paramConfig, int paramInt, List<CameraCaptureCallback> paramList1, boolean paramBoolean, Object paramObject)
  {
    this.mSurfaces = paramList;
    this.mImplementationOptions = paramConfig;
    this.mTemplateType = paramInt;
    this.mCameraCaptureCallbacks = Collections.unmodifiableList(paramList1);
    this.mUseRepeatingSurface = paramBoolean;
    this.mTag = paramObject;
  }
  
  public static CaptureConfig defaultEmptyCaptureConfig()
  {
    return new Builder().build();
  }
  
  public List<CameraCaptureCallback> getCameraCaptureCallbacks()
  {
    return this.mCameraCaptureCallbacks;
  }
  
  public Config getImplementationOptions()
  {
    return this.mImplementationOptions;
  }
  
  public List<DeferrableSurface> getSurfaces()
  {
    return Collections.unmodifiableList(this.mSurfaces);
  }
  
  public Object getTag()
  {
    return this.mTag;
  }
  
  public int getTemplateType()
  {
    return this.mTemplateType;
  }
  
  public boolean isUseRepeatingSurface()
  {
    return this.mUseRepeatingSurface;
  }
  
  public static final class Builder
  {
    private List<CameraCaptureCallback> mCameraCaptureCallbacks = new ArrayList();
    private MutableConfig mImplementationOptions = MutableOptionsBundle.create();
    private final Set<DeferrableSurface> mSurfaces = new HashSet();
    private Object mTag = null;
    private int mTemplateType = -1;
    private boolean mUseRepeatingSurface = false;
    
    public Builder() {}
    
    private Builder(CaptureConfig paramCaptureConfig)
    {
      this.mSurfaces.addAll(paramCaptureConfig.mSurfaces);
      this.mImplementationOptions = MutableOptionsBundle.from(paramCaptureConfig.mImplementationOptions);
      this.mTemplateType = paramCaptureConfig.mTemplateType;
      this.mCameraCaptureCallbacks.addAll(paramCaptureConfig.getCameraCaptureCallbacks());
      this.mUseRepeatingSurface = paramCaptureConfig.isUseRepeatingSurface();
      this.mTag = paramCaptureConfig.getTag();
    }
    
    public static Builder createFrom(UseCaseConfig<?> paramUseCaseConfig)
    {
      Object localObject = paramUseCaseConfig.getCaptureOptionUnpacker(null);
      if (localObject != null)
      {
        Builder localBuilder = new Builder();
        ((CaptureConfig.OptionUnpacker)localObject).unpack(paramUseCaseConfig, localBuilder);
        return localBuilder;
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Implementation is missing option unpacker for ");
      ((StringBuilder)localObject).append(paramUseCaseConfig.getTargetName(paramUseCaseConfig.toString()));
      throw new IllegalStateException(((StringBuilder)localObject).toString());
    }
    
    public static Builder from(CaptureConfig paramCaptureConfig)
    {
      return new Builder(paramCaptureConfig);
    }
    
    public void addAllCameraCaptureCallbacks(Collection<CameraCaptureCallback> paramCollection)
    {
      paramCollection = paramCollection.iterator();
      while (paramCollection.hasNext()) {
        addCameraCaptureCallback((CameraCaptureCallback)paramCollection.next());
      }
    }
    
    public void addCameraCaptureCallback(CameraCaptureCallback paramCameraCaptureCallback)
    {
      if (!this.mCameraCaptureCallbacks.contains(paramCameraCaptureCallback))
      {
        this.mCameraCaptureCallbacks.add(paramCameraCaptureCallback);
        return;
      }
      throw new IllegalArgumentException("duplicate camera capture callback");
    }
    
    public <T> void addImplementationOption(Config.Option<T> paramOption, T paramT)
    {
      this.mImplementationOptions.insertOption(paramOption, paramT);
    }
    
    public void addImplementationOptions(Config paramConfig)
    {
      Iterator localIterator = paramConfig.listOptions().iterator();
      while (localIterator.hasNext())
      {
        Config.Option localOption = (Config.Option)localIterator.next();
        Object localObject1 = this.mImplementationOptions.retrieveOption(localOption, null);
        Object localObject2 = paramConfig.retrieveOption(localOption);
        if ((localObject1 instanceof MultiValueSet))
        {
          ((MultiValueSet)localObject1).addAll(((MultiValueSet)localObject2).getAllItems());
        }
        else
        {
          localObject1 = localObject2;
          if ((localObject2 instanceof MultiValueSet)) {
            localObject1 = ((MultiValueSet)localObject2).clone();
          }
          this.mImplementationOptions.insertOption(localOption, localObject1);
        }
      }
    }
    
    public void addSurface(DeferrableSurface paramDeferrableSurface)
    {
      this.mSurfaces.add(paramDeferrableSurface);
    }
    
    public CaptureConfig build()
    {
      return new CaptureConfig(new ArrayList(this.mSurfaces), OptionsBundle.from(this.mImplementationOptions), this.mTemplateType, this.mCameraCaptureCallbacks, this.mUseRepeatingSurface, this.mTag);
    }
    
    public void clearSurfaces()
    {
      this.mSurfaces.clear();
    }
    
    public Config getImplementationOptions()
    {
      return this.mImplementationOptions;
    }
    
    public Set<DeferrableSurface> getSurfaces()
    {
      return this.mSurfaces;
    }
    
    public int getTemplateType()
    {
      return this.mTemplateType;
    }
    
    boolean isUseRepeatingSurface()
    {
      return this.mUseRepeatingSurface;
    }
    
    public void removeSurface(DeferrableSurface paramDeferrableSurface)
    {
      this.mSurfaces.remove(paramDeferrableSurface);
    }
    
    public void setImplementationOptions(Config paramConfig)
    {
      this.mImplementationOptions = MutableOptionsBundle.from(paramConfig);
    }
    
    public void setTag(Object paramObject)
    {
      this.mTag = paramObject;
    }
    
    public void setTemplateType(int paramInt)
    {
      this.mTemplateType = paramInt;
    }
    
    public void setUseRepeatingSurface(boolean paramBoolean)
    {
      this.mUseRepeatingSurface = paramBoolean;
    }
  }
  
  public static abstract interface OptionUnpacker
  {
    public abstract void unpack(UseCaseConfig<?> paramUseCaseConfig, CaptureConfig.Builder paramBuilder);
  }
}

package androidx.camera.core.impl;

import android.util.Log;
import androidx.camera.core.UseCase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class UseCaseAttachState
{
  private static final String TAG = "UseCaseAttachState";
  private final Map<UseCase, UseCaseAttachInfo> mAttachedUseCasesToInfoMap = new HashMap();
  private final String mCameraId;
  
  public UseCaseAttachState(String paramString)
  {
    this.mCameraId = paramString;
  }
  
  private UseCaseAttachInfo getOrCreateUseCaseAttachInfo(UseCase paramUseCase)
  {
    UseCaseAttachInfo localUseCaseAttachInfo1 = (UseCaseAttachInfo)this.mAttachedUseCasesToInfoMap.get(paramUseCase);
    UseCaseAttachInfo localUseCaseAttachInfo2 = localUseCaseAttachInfo1;
    if (localUseCaseAttachInfo1 == null)
    {
      localUseCaseAttachInfo2 = new UseCaseAttachInfo(paramUseCase.getSessionConfig(this.mCameraId));
      this.mAttachedUseCasesToInfoMap.put(paramUseCase, localUseCaseAttachInfo2);
    }
    return localUseCaseAttachInfo2;
  }
  
  private Collection<UseCase> getUseCases(AttachStateFilter paramAttachStateFilter)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.mAttachedUseCasesToInfoMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if ((paramAttachStateFilter == null) || (paramAttachStateFilter.filter((UseCaseAttachInfo)localEntry.getValue()))) {
        localArrayList.add(localEntry.getKey());
      }
    }
    return localArrayList;
  }
  
  public SessionConfig.ValidatingBuilder getActiveAndOnlineBuilder()
  {
    SessionConfig.ValidatingBuilder localValidatingBuilder = new SessionConfig.ValidatingBuilder();
    ArrayList localArrayList = new ArrayList();
    Object localObject1 = this.mAttachedUseCasesToInfoMap.entrySet().iterator();
    while (((Iterator)localObject1).hasNext())
    {
      Object localObject2 = (Map.Entry)((Iterator)localObject1).next();
      UseCaseAttachInfo localUseCaseAttachInfo = (UseCaseAttachInfo)((Map.Entry)localObject2).getValue();
      if ((localUseCaseAttachInfo.getActive()) && (localUseCaseAttachInfo.getOnline()))
      {
        localObject2 = (UseCase)((Map.Entry)localObject2).getKey();
        localValidatingBuilder.add(localUseCaseAttachInfo.getSessionConfig());
        localArrayList.add(((UseCase)localObject2).getName());
      }
    }
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("Active and online use case: ");
    ((StringBuilder)localObject1).append(localArrayList);
    ((StringBuilder)localObject1).append(" for camera: ");
    ((StringBuilder)localObject1).append(this.mCameraId);
    Log.d("UseCaseAttachState", ((StringBuilder)localObject1).toString());
    return localValidatingBuilder;
  }
  
  public Collection<UseCase> getActiveAndOnlineUseCases()
  {
    Collections.unmodifiableCollection(getUseCases(new AttachStateFilter()
    {
      public boolean filter(UseCaseAttachState.UseCaseAttachInfo paramAnonymousUseCaseAttachInfo)
      {
        boolean bool;
        if ((paramAnonymousUseCaseAttachInfo.getActive()) && (paramAnonymousUseCaseAttachInfo.getOnline())) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
    }));
  }
  
  public SessionConfig.ValidatingBuilder getOnlineBuilder()
  {
    SessionConfig.ValidatingBuilder localValidatingBuilder = new SessionConfig.ValidatingBuilder();
    ArrayList localArrayList = new ArrayList();
    Object localObject = this.mAttachedUseCasesToInfoMap.entrySet().iterator();
    while (((Iterator)localObject).hasNext())
    {
      Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
      UseCaseAttachInfo localUseCaseAttachInfo = (UseCaseAttachInfo)localEntry.getValue();
      if (localUseCaseAttachInfo.getOnline())
      {
        localValidatingBuilder.add(localUseCaseAttachInfo.getSessionConfig());
        localArrayList.add(((UseCase)localEntry.getKey()).getName());
      }
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("All use case: ");
    ((StringBuilder)localObject).append(localArrayList);
    ((StringBuilder)localObject).append(" for camera: ");
    ((StringBuilder)localObject).append(this.mCameraId);
    Log.d("UseCaseAttachState", ((StringBuilder)localObject).toString());
    return localValidatingBuilder;
  }
  
  public Collection<UseCase> getOnlineUseCases()
  {
    Collections.unmodifiableCollection(getUseCases(new AttachStateFilter()
    {
      public boolean filter(UseCaseAttachState.UseCaseAttachInfo paramAnonymousUseCaseAttachInfo)
      {
        return paramAnonymousUseCaseAttachInfo.getOnline();
      }
    }));
  }
  
  public SessionConfig getUseCaseSessionConfig(UseCase paramUseCase)
  {
    if (!this.mAttachedUseCasesToInfoMap.containsKey(paramUseCase)) {
      return SessionConfig.defaultEmptySessionConfig();
    }
    return ((UseCaseAttachInfo)this.mAttachedUseCasesToInfoMap.get(paramUseCase)).getSessionConfig();
  }
  
  public boolean isUseCaseOnline(UseCase paramUseCase)
  {
    if (!this.mAttachedUseCasesToInfoMap.containsKey(paramUseCase)) {
      return false;
    }
    return ((UseCaseAttachInfo)this.mAttachedUseCasesToInfoMap.get(paramUseCase)).getOnline();
  }
  
  public void setUseCaseActive(UseCase paramUseCase)
  {
    getOrCreateUseCaseAttachInfo(paramUseCase).setActive(true);
  }
  
  public void setUseCaseInactive(UseCase paramUseCase)
  {
    if (!this.mAttachedUseCasesToInfoMap.containsKey(paramUseCase)) {
      return;
    }
    UseCaseAttachInfo localUseCaseAttachInfo = (UseCaseAttachInfo)this.mAttachedUseCasesToInfoMap.get(paramUseCase);
    localUseCaseAttachInfo.setActive(false);
    if (!localUseCaseAttachInfo.getOnline()) {
      this.mAttachedUseCasesToInfoMap.remove(paramUseCase);
    }
  }
  
  public void setUseCaseOffline(UseCase paramUseCase)
  {
    if (!this.mAttachedUseCasesToInfoMap.containsKey(paramUseCase)) {
      return;
    }
    UseCaseAttachInfo localUseCaseAttachInfo = (UseCaseAttachInfo)this.mAttachedUseCasesToInfoMap.get(paramUseCase);
    localUseCaseAttachInfo.setOnline(false);
    if (!localUseCaseAttachInfo.getActive()) {
      this.mAttachedUseCasesToInfoMap.remove(paramUseCase);
    }
  }
  
  public void setUseCaseOnline(UseCase paramUseCase)
  {
    getOrCreateUseCaseAttachInfo(paramUseCase).setOnline(true);
  }
  
  public void updateUseCase(UseCase paramUseCase)
  {
    if (!this.mAttachedUseCasesToInfoMap.containsKey(paramUseCase)) {
      return;
    }
    UseCaseAttachInfo localUseCaseAttachInfo1 = new UseCaseAttachInfo(paramUseCase.getSessionConfig(this.mCameraId));
    UseCaseAttachInfo localUseCaseAttachInfo2 = (UseCaseAttachInfo)this.mAttachedUseCasesToInfoMap.get(paramUseCase);
    localUseCaseAttachInfo1.setOnline(localUseCaseAttachInfo2.getOnline());
    localUseCaseAttachInfo1.setActive(localUseCaseAttachInfo2.getActive());
    this.mAttachedUseCasesToInfoMap.put(paramUseCase, localUseCaseAttachInfo1);
  }
  
  private static abstract interface AttachStateFilter
  {
    public abstract boolean filter(UseCaseAttachState.UseCaseAttachInfo paramUseCaseAttachInfo);
  }
  
  private static final class UseCaseAttachInfo
  {
    private boolean mActive = false;
    private boolean mOnline = false;
    private final SessionConfig mSessionConfig;
    
    UseCaseAttachInfo(SessionConfig paramSessionConfig)
    {
      this.mSessionConfig = paramSessionConfig;
    }
    
    boolean getActive()
    {
      return this.mActive;
    }
    
    boolean getOnline()
    {
      return this.mOnline;
    }
    
    SessionConfig getSessionConfig()
    {
      return this.mSessionConfig;
    }
    
    void setActive(boolean paramBoolean)
    {
      this.mActive = paramBoolean;
    }
    
    void setOnline(boolean paramBoolean)
    {
      this.mOnline = paramBoolean;
    }
  }
}

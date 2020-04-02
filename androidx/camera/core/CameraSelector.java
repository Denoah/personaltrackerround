package androidx.camera.core;

import androidx.camera.core.impl.CameraIdFilter;
import androidx.camera.core.impl.LensFacingCameraIdFilter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public final class CameraSelector
{
  public static final CameraSelector DEFAULT_BACK_CAMERA = new Builder().requireLensFacing(1).build();
  public static final CameraSelector DEFAULT_FRONT_CAMERA = new Builder().requireLensFacing(0).build();
  public static final int LENS_FACING_BACK = 1;
  public static final int LENS_FACING_FRONT = 0;
  private LinkedHashSet<CameraIdFilter> mCameraFilterSet;
  
  CameraSelector(LinkedHashSet<CameraIdFilter> paramLinkedHashSet)
  {
    this.mCameraFilterSet = paramLinkedHashSet;
  }
  
  public LinkedHashSet<CameraIdFilter> getCameraFilterSet()
  {
    return this.mCameraFilterSet;
  }
  
  public Integer getLensFacing()
  {
    Iterator localIterator = this.mCameraFilterSet.iterator();
    Object localObject1 = null;
    while (localIterator.hasNext())
    {
      Object localObject2 = (CameraIdFilter)localIterator.next();
      if ((localObject2 instanceof LensFacingCameraIdFilter))
      {
        localObject2 = Integer.valueOf(((LensFacingCameraIdFilter)localObject2).getLensFacing());
        if (localObject1 == null) {
          localObject1 = localObject2;
        } else if (!localObject1.equals(localObject2)) {
          throw new IllegalStateException("Multiple conflicting lens facing requirements exist.");
        }
      }
    }
    return localObject1;
  }
  
  public String select(Set<String> paramSet)
  {
    Object localObject = new LinkedHashSet();
    Iterator localIterator = this.mCameraFilterSet.iterator();
    while (localIterator.hasNext())
    {
      localObject = ((CameraIdFilter)localIterator.next()).filter(paramSet);
      if (!((Set)localObject).isEmpty())
      {
        if (paramSet.containsAll((Collection)localObject)) {
          paramSet = (Set<String>)localObject;
        } else {
          throw new IllegalArgumentException("The output isn't contained in the input.");
        }
      }
      else {
        throw new IllegalArgumentException("No available camera can be found.");
      }
    }
    return (String)((Set)localObject).iterator().next();
  }
  
  public static final class Builder
  {
    private final LinkedHashSet<CameraIdFilter> mCameraFilterSet;
    
    public Builder()
    {
      this.mCameraFilterSet = new LinkedHashSet();
    }
    
    private Builder(LinkedHashSet<CameraIdFilter> paramLinkedHashSet)
    {
      this.mCameraFilterSet = new LinkedHashSet(paramLinkedHashSet);
    }
    
    public static Builder fromSelector(CameraSelector paramCameraSelector)
    {
      return new Builder(paramCameraSelector.getCameraFilterSet());
    }
    
    public Builder appendFilter(CameraIdFilter paramCameraIdFilter)
    {
      this.mCameraFilterSet.add(paramCameraIdFilter);
      return this;
    }
    
    public CameraSelector build()
    {
      return new CameraSelector(this.mCameraFilterSet);
    }
    
    public Builder requireLensFacing(int paramInt)
    {
      this.mCameraFilterSet.add(new LensFacingCameraIdFilter(paramInt));
      return this;
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface LensFacing {}
}

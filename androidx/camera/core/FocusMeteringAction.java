package androidx.camera.core;

import androidx.core.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class FocusMeteringAction
{
  static final long DEFAULT_AUTOCANCEL_DURATION = 5000L;
  static final int DEFAULT_METERING_MODE = 7;
  public static final int FLAG_AE = 2;
  public static final int FLAG_AF = 1;
  public static final int FLAG_AWB = 4;
  private final long mAutoCancelDurationInMillis;
  private final List<MeteringPoint> mMeteringPointsAe;
  private final List<MeteringPoint> mMeteringPointsAf;
  private final List<MeteringPoint> mMeteringPointsAwb;
  
  FocusMeteringAction(Builder paramBuilder)
  {
    this.mMeteringPointsAf = Collections.unmodifiableList(paramBuilder.mMeteringPointsAf);
    this.mMeteringPointsAe = Collections.unmodifiableList(paramBuilder.mMeteringPointsAe);
    this.mMeteringPointsAwb = Collections.unmodifiableList(paramBuilder.mMeteringPointsAwb);
    this.mAutoCancelDurationInMillis = paramBuilder.mAutoCancelDurationInMillis;
  }
  
  public long getAutoCancelDurationInMillis()
  {
    return this.mAutoCancelDurationInMillis;
  }
  
  public List<MeteringPoint> getMeteringPointsAe()
  {
    return this.mMeteringPointsAe;
  }
  
  public List<MeteringPoint> getMeteringPointsAf()
  {
    return this.mMeteringPointsAf;
  }
  
  public List<MeteringPoint> getMeteringPointsAwb()
  {
    return this.mMeteringPointsAwb;
  }
  
  public boolean isAutoCancelEnabled()
  {
    boolean bool;
    if (this.mAutoCancelDurationInMillis > 0L) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static class Builder
  {
    long mAutoCancelDurationInMillis = 5000L;
    final List<MeteringPoint> mMeteringPointsAe = new ArrayList();
    final List<MeteringPoint> mMeteringPointsAf = new ArrayList();
    final List<MeteringPoint> mMeteringPointsAwb = new ArrayList();
    
    public Builder(MeteringPoint paramMeteringPoint)
    {
      this(paramMeteringPoint, 7);
    }
    
    public Builder(MeteringPoint paramMeteringPoint, int paramInt)
    {
      addPoint(paramMeteringPoint, paramInt);
    }
    
    public Builder addPoint(MeteringPoint paramMeteringPoint)
    {
      return addPoint(paramMeteringPoint, 7);
    }
    
    public Builder addPoint(MeteringPoint paramMeteringPoint, int paramInt)
    {
      boolean bool1 = false;
      if (paramMeteringPoint != null) {
        bool2 = true;
      } else {
        bool2 = false;
      }
      Preconditions.checkArgument(bool2, "Point cannot be null.");
      boolean bool2 = bool1;
      if (paramInt >= 1)
      {
        bool2 = bool1;
        if (paramInt <= 7) {
          bool2 = true;
        }
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Invalid metering mode ");
      localStringBuilder.append(paramInt);
      Preconditions.checkArgument(bool2, localStringBuilder.toString());
      if ((paramInt & 0x1) != 0) {
        this.mMeteringPointsAf.add(paramMeteringPoint);
      }
      if ((paramInt & 0x2) != 0) {
        this.mMeteringPointsAe.add(paramMeteringPoint);
      }
      if ((paramInt & 0x4) != 0) {
        this.mMeteringPointsAwb.add(paramMeteringPoint);
      }
      return this;
    }
    
    public FocusMeteringAction build()
    {
      return new FocusMeteringAction(this);
    }
    
    public Builder disableAutoCancel()
    {
      this.mAutoCancelDurationInMillis = 0L;
      return this;
    }
    
    public Builder setAutoCancelDuration(long paramLong, TimeUnit paramTimeUnit)
    {
      boolean bool;
      if (paramLong >= 1L) {
        bool = true;
      } else {
        bool = false;
      }
      Preconditions.checkArgument(bool, "autoCancelDuration must be at least 1");
      this.mAutoCancelDurationInMillis = paramTimeUnit.toMillis(paramLong);
      return this;
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface MeteringMode {}
}

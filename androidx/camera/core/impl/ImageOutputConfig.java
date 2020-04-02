package androidx.camera.core.impl;

import android.util.Pair;
import android.util.Rational;
import android.util.Size;
import androidx.camera.core.AspectRatio;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public abstract interface ImageOutputConfig
{
  public static final Rational DEFAULT_ASPECT_RATIO_LANDSCAPE = new Rational(4, 3);
  public static final Rational DEFAULT_ASPECT_RATIO_PORTRAIT = new Rational(3, 4);
  public static final int INVALID_ROTATION = -1;
  public static final Config.Option<Size> OPTION_DEFAULT_RESOLUTION = Config.Option.create("camerax.core.imageOutput.defaultResolution", Size.class);
  public static final Config.Option<Size> OPTION_MAX_RESOLUTION = Config.Option.create("camerax.core.imageOutput.maxResolution", Size.class);
  public static final Config.Option<List<Pair<Integer, Size[]>>> OPTION_SUPPORTED_RESOLUTIONS = Config.Option.create("camerax.core.imageOutput.supportedResolutions", List.class);
  public static final Config.Option<Integer> OPTION_TARGET_ASPECT_RATIO;
  public static final Config.Option<Rational> OPTION_TARGET_ASPECT_RATIO_CUSTOM = Config.Option.create("camerax.core.imageOutput.targetAspectRatioCustom", Rational.class);
  public static final Config.Option<Size> OPTION_TARGET_RESOLUTION;
  public static final Config.Option<Integer> OPTION_TARGET_ROTATION;
  
  static
  {
    OPTION_TARGET_ASPECT_RATIO = Config.Option.create("camerax.core.imageOutput.targetAspectRatio", AspectRatio.class);
    OPTION_TARGET_ROTATION = Config.Option.create("camerax.core.imageOutput.targetRotation", Integer.TYPE);
    OPTION_TARGET_RESOLUTION = Config.Option.create("camerax.core.imageOutput.targetResolution", Size.class);
  }
  
  public abstract Size getDefaultResolution();
  
  public abstract Size getDefaultResolution(Size paramSize);
  
  public abstract Size getMaxResolution();
  
  public abstract Size getMaxResolution(Size paramSize);
  
  public abstract List<Pair<Integer, Size[]>> getSupportedResolutions();
  
  public abstract List<Pair<Integer, Size[]>> getSupportedResolutions(List<Pair<Integer, Size[]>> paramList);
  
  public abstract int getTargetAspectRatio();
  
  public abstract Rational getTargetAspectRatioCustom();
  
  public abstract Rational getTargetAspectRatioCustom(Rational paramRational);
  
  public abstract Size getTargetResolution();
  
  public abstract Size getTargetResolution(Size paramSize);
  
  public abstract int getTargetRotation();
  
  public abstract int getTargetRotation(int paramInt);
  
  public abstract boolean hasTargetAspectRatio();
  
  public static abstract interface Builder<B>
  {
    public abstract B setDefaultResolution(Size paramSize);
    
    public abstract B setMaxResolution(Size paramSize);
    
    public abstract B setSupportedResolutions(List<Pair<Integer, Size[]>> paramList);
    
    public abstract B setTargetAspectRatio(int paramInt);
    
    public abstract B setTargetAspectRatioCustom(Rational paramRational);
    
    public abstract B setTargetResolution(Size paramSize);
    
    public abstract B setTargetRotation(int paramInt);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface RotationValue {}
}

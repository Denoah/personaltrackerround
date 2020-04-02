package androidx.camera.view;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManager.DisplayListener;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.camera.core.Preview.SurfaceProvider;

public class PreviewView
  extends FrameLayout
{
  private final DisplayManager.DisplayListener mDisplayListener = new DisplayManager.DisplayListener()
  {
    public void onDisplayAdded(int paramAnonymousInt) {}
    
    public void onDisplayChanged(int paramAnonymousInt)
    {
      PreviewView.this.mImplementation.onDisplayChanged();
    }
    
    public void onDisplayRemoved(int paramAnonymousInt) {}
  };
  Implementation mImplementation;
  private ImplementationMode mImplementationMode;
  
  public PreviewView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PreviewView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public PreviewView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, 0);
  }
  
  public PreviewView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    TypedArray localTypedArray = paramContext.getTheme().obtainStyledAttributes(paramAttributeSet, R.styleable.PreviewView, paramInt1, paramInt2);
    if (Build.VERSION.SDK_INT >= 29) {
      saveAttributeDataForStyleable(paramContext, R.styleable.PreviewView, paramAttributeSet, localTypedArray, paramInt1, paramInt2);
    }
    try
    {
      this.mImplementationMode = ImplementationMode.fromId(localTypedArray.getInteger(R.styleable.PreviewView_implementationMode, ImplementationMode.TEXTURE_VIEW.getId()));
      localTypedArray.recycle();
      setUp();
      return;
    }
    finally
    {
      localTypedArray.recycle();
    }
  }
  
  private void setUp()
  {
    removeAllViews();
    int i = 2.$SwitchMap$androidx$camera$view$PreviewView$ImplementationMode[this.mImplementationMode.ordinal()];
    if (i != 1)
    {
      if (i == 2)
      {
        this.mImplementation = new TextureViewImplementation();
      }
      else
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Unsupported implementation mode ");
        localStringBuilder.append(this.mImplementationMode);
        throw new IllegalStateException(localStringBuilder.toString());
      }
    }
    else {
      this.mImplementation = new SurfaceViewImplementation();
    }
    this.mImplementation.init(this);
  }
  
  public ImplementationMode getImplementationMode()
  {
    return this.mImplementationMode;
  }
  
  public Preview.SurfaceProvider getPreviewSurfaceProvider()
  {
    return this.mImplementation.getSurfaceProvider();
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    DisplayManager localDisplayManager = (DisplayManager)getContext().getSystemService("display");
    if (localDisplayManager != null) {
      localDisplayManager.registerDisplayListener(this.mDisplayListener, getHandler());
    }
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    DisplayManager localDisplayManager = (DisplayManager)getContext().getSystemService("display");
    if (localDisplayManager != null) {
      localDisplayManager.unregisterDisplayListener(this.mDisplayListener);
    }
  }
  
  public void setImplementationMode(ImplementationMode paramImplementationMode)
  {
    this.mImplementationMode = paramImplementationMode;
    setUp();
  }
  
  static abstract interface Implementation
  {
    public abstract Preview.SurfaceProvider getSurfaceProvider();
    
    public abstract void init(FrameLayout paramFrameLayout);
    
    public abstract void onDisplayChanged();
  }
  
  public static enum ImplementationMode
  {
    private final int mId;
    
    static
    {
      ImplementationMode localImplementationMode = new ImplementationMode("TEXTURE_VIEW", 1, 1);
      TEXTURE_VIEW = localImplementationMode;
      $VALUES = new ImplementationMode[] { SURFACE_VIEW, localImplementationMode };
    }
    
    private ImplementationMode(int paramInt)
    {
      this.mId = paramInt;
    }
    
    static ImplementationMode fromId(int paramInt)
    {
      for (localObject : ) {
        if (((ImplementationMode)localObject).mId == paramInt) {
          return localObject;
        }
      }
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Unsupported implementation mode ");
      ((StringBuilder)localObject).append(paramInt);
      throw new IllegalArgumentException(((StringBuilder)localObject).toString());
    }
    
    public int getId()
    {
      return this.mId;
    }
  }
  
  public static enum ScaleType
  {
    static
    {
      FILL_CENTER = new ScaleType("FILL_CENTER", 1);
      FILL_END = new ScaleType("FILL_END", 2);
      FIT_START = new ScaleType("FIT_START", 3);
      FIT_CENTER = new ScaleType("FIT_CENTER", 4);
      ScaleType localScaleType = new ScaleType("FIT_END", 5);
      FIT_END = localScaleType;
      $VALUES = new ScaleType[] { FILL_START, FILL_CENTER, FILL_END, FIT_START, FIT_CENTER, localScaleType };
    }
    
    private ScaleType() {}
  }
}

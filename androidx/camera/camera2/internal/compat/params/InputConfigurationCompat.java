package androidx.camera.camera2.internal.compat.params;

import android.hardware.camera2.params.InputConfiguration;
import android.os.Build.VERSION;
import java.util.Objects;

public final class InputConfigurationCompat
{
  private final InputConfigurationCompatImpl mImpl;
  
  public InputConfigurationCompat(int paramInt1, int paramInt2, int paramInt3)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      this.mImpl = new InputConfigurationCompatApi23Impl(paramInt1, paramInt2, paramInt3);
    } else {
      this.mImpl = new InputConfigurationCompatBaseImpl(paramInt1, paramInt2, paramInt3);
    }
  }
  
  private InputConfigurationCompat(InputConfigurationCompatImpl paramInputConfigurationCompatImpl)
  {
    this.mImpl = paramInputConfigurationCompatImpl;
  }
  
  public static InputConfigurationCompat wrap(Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    if (Build.VERSION.SDK_INT < 23) {
      return null;
    }
    return new InputConfigurationCompat(new InputConfigurationCompatApi23Impl(paramObject));
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof InputConfigurationCompat)) {
      return false;
    }
    return this.mImpl.equals(((InputConfigurationCompat)paramObject).mImpl);
  }
  
  public int getFormat()
  {
    return this.mImpl.getFormat();
  }
  
  public int getHeight()
  {
    return this.mImpl.getHeight();
  }
  
  public int getWidth()
  {
    return this.mImpl.getWidth();
  }
  
  public int hashCode()
  {
    return this.mImpl.hashCode();
  }
  
  public String toString()
  {
    return this.mImpl.toString();
  }
  
  public Object unwrap()
  {
    return this.mImpl.getInputConfiguration();
  }
  
  private static final class InputConfigurationCompatApi23Impl
    implements InputConfigurationCompat.InputConfigurationCompatImpl
  {
    private final InputConfiguration mObject;
    
    InputConfigurationCompatApi23Impl(int paramInt1, int paramInt2, int paramInt3)
    {
      this(new InputConfiguration(paramInt1, paramInt2, paramInt3));
    }
    
    InputConfigurationCompatApi23Impl(Object paramObject)
    {
      this.mObject = ((InputConfiguration)paramObject);
    }
    
    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof InputConfigurationCompat.InputConfigurationCompatImpl)) {
        return false;
      }
      return Objects.equals(this.mObject, ((InputConfigurationCompat.InputConfigurationCompatImpl)paramObject).getInputConfiguration());
    }
    
    public int getFormat()
    {
      return this.mObject.getFormat();
    }
    
    public int getHeight()
    {
      return this.mObject.getHeight();
    }
    
    public Object getInputConfiguration()
    {
      return this.mObject;
    }
    
    public int getWidth()
    {
      return this.mObject.getWidth();
    }
    
    public int hashCode()
    {
      return this.mObject.hashCode();
    }
    
    public String toString()
    {
      return this.mObject.toString();
    }
  }
  
  static final class InputConfigurationCompatBaseImpl
    implements InputConfigurationCompat.InputConfigurationCompatImpl
  {
    private final int mFormat;
    private final int mHeight;
    private final int mWidth;
    
    InputConfigurationCompatBaseImpl(int paramInt1, int paramInt2, int paramInt3)
    {
      this.mWidth = paramInt1;
      this.mHeight = paramInt2;
      this.mFormat = paramInt3;
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool1 = paramObject instanceof InputConfigurationCompatBaseImpl;
      boolean bool2 = false;
      if (!bool1) {
        return false;
      }
      paramObject = (InputConfigurationCompatBaseImpl)paramObject;
      bool1 = bool2;
      if (paramObject.getWidth() == this.mWidth)
      {
        bool1 = bool2;
        if (paramObject.getHeight() == this.mHeight)
        {
          bool1 = bool2;
          if (paramObject.getFormat() == this.mFormat) {
            bool1 = true;
          }
        }
      }
      return bool1;
    }
    
    public int getFormat()
    {
      return this.mFormat;
    }
    
    public int getHeight()
    {
      return this.mHeight;
    }
    
    public Object getInputConfiguration()
    {
      return null;
    }
    
    public int getWidth()
    {
      return this.mWidth;
    }
    
    public int hashCode()
    {
      int i = this.mWidth ^ 0x1F;
      i = this.mHeight ^ (i << 5) - i;
      return this.mFormat ^ (i << 5) - i;
    }
    
    public String toString()
    {
      return String.format("InputConfiguration(w:%d, h:%d, format:%d)", new Object[] { Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight), Integer.valueOf(this.mFormat) });
    }
  }
  
  private static abstract interface InputConfigurationCompatImpl
  {
    public abstract int getFormat();
    
    public abstract int getHeight();
    
    public abstract Object getInputConfiguration();
    
    public abstract int getWidth();
  }
}

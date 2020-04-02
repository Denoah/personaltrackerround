package androidx.core.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.Log;
import androidx.core.graphics.BitmapCompat;
import androidx.core.view.GravityCompat;
import java.io.InputStream;

public final class RoundedBitmapDrawableFactory
{
  private static final String TAG = "RoundedBitmapDrawableFa";
  
  private RoundedBitmapDrawableFactory() {}
  
  public static RoundedBitmapDrawable create(Resources paramResources, Bitmap paramBitmap)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return new RoundedBitmapDrawable21(paramResources, paramBitmap);
    }
    return new DefaultRoundedBitmapDrawable(paramResources, paramBitmap);
  }
  
  public static RoundedBitmapDrawable create(Resources paramResources, InputStream paramInputStream)
  {
    RoundedBitmapDrawable localRoundedBitmapDrawable = create(paramResources, BitmapFactory.decodeStream(paramInputStream));
    if (localRoundedBitmapDrawable.getBitmap() == null)
    {
      paramResources = new StringBuilder();
      paramResources.append("RoundedBitmapDrawable cannot decode ");
      paramResources.append(paramInputStream);
      Log.w("RoundedBitmapDrawableFa", paramResources.toString());
    }
    return localRoundedBitmapDrawable;
  }
  
  public static RoundedBitmapDrawable create(Resources paramResources, String paramString)
  {
    RoundedBitmapDrawable localRoundedBitmapDrawable = create(paramResources, BitmapFactory.decodeFile(paramString));
    if (localRoundedBitmapDrawable.getBitmap() == null)
    {
      paramResources = new StringBuilder();
      paramResources.append("RoundedBitmapDrawable cannot decode ");
      paramResources.append(paramString);
      Log.w("RoundedBitmapDrawableFa", paramResources.toString());
    }
    return localRoundedBitmapDrawable;
  }
  
  private static class DefaultRoundedBitmapDrawable
    extends RoundedBitmapDrawable
  {
    DefaultRoundedBitmapDrawable(Resources paramResources, Bitmap paramBitmap)
    {
      super(paramBitmap);
    }
    
    void gravityCompatApply(int paramInt1, int paramInt2, int paramInt3, Rect paramRect1, Rect paramRect2)
    {
      GravityCompat.apply(paramInt1, paramInt2, paramInt3, paramRect1, paramRect2, 0);
    }
    
    public boolean hasMipMap()
    {
      boolean bool;
      if ((this.mBitmap != null) && (BitmapCompat.hasMipMap(this.mBitmap))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void setMipMap(boolean paramBoolean)
    {
      if (this.mBitmap != null)
      {
        BitmapCompat.setHasMipMap(this.mBitmap, paramBoolean);
        invalidateSelf();
      }
    }
  }
}

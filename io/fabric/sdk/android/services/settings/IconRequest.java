package io.fabric.sdk.android.services.settings;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;

public class IconRequest
{
  public final String hash;
  public final int height;
  public final int iconResourceId;
  public final int width;
  
  public IconRequest(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    this.hash = paramString;
    this.iconResourceId = paramInt1;
    this.width = paramInt2;
    this.height = paramInt3;
  }
  
  public static IconRequest build(Context paramContext, String paramString)
  {
    if (paramString != null) {
      try
      {
        int i = CommonUtils.getAppIconResourceId(paramContext);
        Object localObject = Fabric.getLogger();
        StringBuilder localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localStringBuilder.append("App icon resource ID is ");
        localStringBuilder.append(i);
        ((Logger)localObject).d("Fabric", localStringBuilder.toString());
        localObject = new android/graphics/BitmapFactory$Options;
        ((BitmapFactory.Options)localObject).<init>();
        ((BitmapFactory.Options)localObject).inJustDecodeBounds = true;
        BitmapFactory.decodeResource(paramContext.getResources(), i, (BitmapFactory.Options)localObject);
        paramContext = new io/fabric/sdk/android/services/settings/IconRequest;
        paramContext.<init>(paramString, i, ((BitmapFactory.Options)localObject).outWidth, ((BitmapFactory.Options)localObject).outHeight);
      }
      catch (Exception paramContext)
      {
        Fabric.getLogger().e("Fabric", "Failed to load icon", paramContext);
      }
    } else {
      paramContext = null;
    }
    return paramContext;
  }
}

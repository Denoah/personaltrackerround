package androidx.media;

import android.content.Context;
import android.media.browse.MediaBrowser.MediaItem;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.service.media.MediaBrowserService;
import android.service.media.MediaBrowserService.Result;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class MediaBrowserServiceCompatApi26
{
  private static final String TAG = "MBSCompatApi26";
  static Field sResultFlags;
  
  static
  {
    try
    {
      Field localField = MediaBrowserService.Result.class.getDeclaredField("mFlags");
      sResultFlags = localField;
      localField.setAccessible(true);
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      Log.w("MBSCompatApi26", localNoSuchFieldException);
    }
  }
  
  private MediaBrowserServiceCompatApi26() {}
  
  public static Object createService(Context paramContext, ServiceCompatProxy paramServiceCompatProxy)
  {
    return new MediaBrowserServiceAdaptor(paramContext, paramServiceCompatProxy);
  }
  
  public static Bundle getBrowserRootHints(Object paramObject)
  {
    return ((MediaBrowserService)paramObject).getBrowserRootHints();
  }
  
  public static void notifyChildrenChanged(Object paramObject, String paramString, Bundle paramBundle)
  {
    ((MediaBrowserService)paramObject).notifyChildrenChanged(paramString, paramBundle);
  }
  
  static class MediaBrowserServiceAdaptor
    extends MediaBrowserServiceCompatApi23.MediaBrowserServiceAdaptor
  {
    MediaBrowserServiceAdaptor(Context paramContext, MediaBrowserServiceCompatApi26.ServiceCompatProxy paramServiceCompatProxy)
    {
      super(paramServiceCompatProxy);
    }
    
    public void onLoadChildren(String paramString, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> paramResult, Bundle paramBundle)
    {
      MediaSessionCompat.ensureClassLoader(paramBundle);
      ((MediaBrowserServiceCompatApi26.ServiceCompatProxy)this.mServiceProxy).onLoadChildren(paramString, new MediaBrowserServiceCompatApi26.ResultWrapper(paramResult), paramBundle);
    }
  }
  
  static class ResultWrapper
  {
    MediaBrowserService.Result mResultObj;
    
    ResultWrapper(MediaBrowserService.Result paramResult)
    {
      this.mResultObj = paramResult;
    }
    
    public void detach()
    {
      this.mResultObj.detach();
    }
    
    List<MediaBrowser.MediaItem> parcelListToItemList(List<Parcel> paramList)
    {
      if (paramList == null) {
        return null;
      }
      ArrayList localArrayList = new ArrayList();
      paramList = paramList.iterator();
      while (paramList.hasNext())
      {
        Parcel localParcel = (Parcel)paramList.next();
        localParcel.setDataPosition(0);
        localArrayList.add(MediaBrowser.MediaItem.CREATOR.createFromParcel(localParcel));
        localParcel.recycle();
      }
      return localArrayList;
    }
    
    public void sendResult(List<Parcel> paramList, int paramInt)
    {
      try
      {
        MediaBrowserServiceCompatApi26.sResultFlags.setInt(this.mResultObj, paramInt);
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        Log.w("MBSCompatApi26", localIllegalAccessException);
      }
      this.mResultObj.sendResult(parcelListToItemList(paramList));
    }
  }
  
  public static abstract interface ServiceCompatProxy
    extends MediaBrowserServiceCompatApi23.ServiceCompatProxy
  {
    public abstract void onLoadChildren(String paramString, MediaBrowserServiceCompatApi26.ResultWrapper paramResultWrapper, Bundle paramBundle);
  }
}

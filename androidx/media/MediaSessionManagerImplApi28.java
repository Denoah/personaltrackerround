package androidx.media;

import android.content.Context;
import android.media.session.MediaSessionManager;
import android.media.session.MediaSessionManager.RemoteUserInfo;
import androidx.core.util.ObjectsCompat;

class MediaSessionManagerImplApi28
  extends MediaSessionManagerImplApi21
{
  MediaSessionManager mObject;
  
  MediaSessionManagerImplApi28(Context paramContext)
  {
    super(paramContext);
    this.mObject = ((MediaSessionManager)paramContext.getSystemService("media_session"));
  }
  
  public boolean isTrustedForMediaControl(MediaSessionManager.RemoteUserInfoImpl paramRemoteUserInfoImpl)
  {
    if ((paramRemoteUserInfoImpl instanceof RemoteUserInfoImplApi28)) {
      return this.mObject.isTrustedForMediaControl(((RemoteUserInfoImplApi28)paramRemoteUserInfoImpl).mObject);
    }
    return false;
  }
  
  static final class RemoteUserInfoImplApi28
    implements MediaSessionManager.RemoteUserInfoImpl
  {
    final MediaSessionManager.RemoteUserInfo mObject;
    
    RemoteUserInfoImplApi28(MediaSessionManager.RemoteUserInfo paramRemoteUserInfo)
    {
      this.mObject = paramRemoteUserInfo;
    }
    
    RemoteUserInfoImplApi28(String paramString, int paramInt1, int paramInt2)
    {
      this.mObject = new MediaSessionManager.RemoteUserInfo(paramString, paramInt1, paramInt2);
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {
        return true;
      }
      if (!(paramObject instanceof RemoteUserInfoImplApi28)) {
        return false;
      }
      paramObject = (RemoteUserInfoImplApi28)paramObject;
      return this.mObject.equals(paramObject.mObject);
    }
    
    public String getPackageName()
    {
      return this.mObject.getPackageName();
    }
    
    public int getPid()
    {
      return this.mObject.getPid();
    }
    
    public int getUid()
    {
      return this.mObject.getUid();
    }
    
    public int hashCode()
    {
      return ObjectsCompat.hash(new Object[] { this.mObject });
    }
  }
}

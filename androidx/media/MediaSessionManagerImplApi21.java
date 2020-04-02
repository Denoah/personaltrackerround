package androidx.media;

import android.content.Context;

class MediaSessionManagerImplApi21
  extends MediaSessionManagerImplBase
{
  MediaSessionManagerImplApi21(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
  }
  
  private boolean hasMediaControlPermission(MediaSessionManager.RemoteUserInfoImpl paramRemoteUserInfoImpl)
  {
    boolean bool;
    if (getContext().checkPermission("android.permission.MEDIA_CONTENT_CONTROL", paramRemoteUserInfoImpl.getPid(), paramRemoteUserInfoImpl.getUid()) == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isTrustedForMediaControl(MediaSessionManager.RemoteUserInfoImpl paramRemoteUserInfoImpl)
  {
    boolean bool;
    if ((!hasMediaControlPermission(paramRemoteUserInfoImpl)) && (!super.isTrustedForMediaControl(paramRemoteUserInfoImpl))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
}

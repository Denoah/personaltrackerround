package android.support.v4.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;

public final class MediaDescriptionCompat
  implements Parcelable
{
  public static final long BT_FOLDER_TYPE_ALBUMS = 2L;
  public static final long BT_FOLDER_TYPE_ARTISTS = 3L;
  public static final long BT_FOLDER_TYPE_GENRES = 4L;
  public static final long BT_FOLDER_TYPE_MIXED = 0L;
  public static final long BT_FOLDER_TYPE_PLAYLISTS = 5L;
  public static final long BT_FOLDER_TYPE_TITLES = 1L;
  public static final long BT_FOLDER_TYPE_YEARS = 6L;
  public static final Parcelable.Creator<MediaDescriptionCompat> CREATOR = new Parcelable.Creator()
  {
    public MediaDescriptionCompat createFromParcel(Parcel paramAnonymousParcel)
    {
      if (Build.VERSION.SDK_INT < 21) {
        return new MediaDescriptionCompat(paramAnonymousParcel);
      }
      return MediaDescriptionCompat.fromMediaDescription(MediaDescriptionCompatApi21.fromParcel(paramAnonymousParcel));
    }
    
    public MediaDescriptionCompat[] newArray(int paramAnonymousInt)
    {
      return new MediaDescriptionCompat[paramAnonymousInt];
    }
  };
  public static final String DESCRIPTION_KEY_MEDIA_URI = "android.support.v4.media.description.MEDIA_URI";
  public static final String DESCRIPTION_KEY_NULL_BUNDLE_FLAG = "android.support.v4.media.description.NULL_BUNDLE_FLAG";
  public static final String EXTRA_BT_FOLDER_TYPE = "android.media.extra.BT_FOLDER_TYPE";
  public static final String EXTRA_DOWNLOAD_STATUS = "android.media.extra.DOWNLOAD_STATUS";
  public static final long STATUS_DOWNLOADED = 2L;
  public static final long STATUS_DOWNLOADING = 1L;
  public static final long STATUS_NOT_DOWNLOADED = 0L;
  private final CharSequence mDescription;
  private Object mDescriptionObj;
  private final Bundle mExtras;
  private final Bitmap mIcon;
  private final Uri mIconUri;
  private final String mMediaId;
  private final Uri mMediaUri;
  private final CharSequence mSubtitle;
  private final CharSequence mTitle;
  
  MediaDescriptionCompat(Parcel paramParcel)
  {
    this.mMediaId = paramParcel.readString();
    this.mTitle = ((CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(paramParcel));
    this.mSubtitle = ((CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(paramParcel));
    this.mDescription = ((CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(paramParcel));
    ClassLoader localClassLoader = getClass().getClassLoader();
    this.mIcon = ((Bitmap)paramParcel.readParcelable(localClassLoader));
    this.mIconUri = ((Uri)paramParcel.readParcelable(localClassLoader));
    this.mExtras = paramParcel.readBundle(localClassLoader);
    this.mMediaUri = ((Uri)paramParcel.readParcelable(localClassLoader));
  }
  
  MediaDescriptionCompat(String paramString, CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3, Bitmap paramBitmap, Uri paramUri1, Bundle paramBundle, Uri paramUri2)
  {
    this.mMediaId = paramString;
    this.mTitle = paramCharSequence1;
    this.mSubtitle = paramCharSequence2;
    this.mDescription = paramCharSequence3;
    this.mIcon = paramBitmap;
    this.mIconUri = paramUri1;
    this.mExtras = paramBundle;
    this.mMediaUri = paramUri2;
  }
  
  public static MediaDescriptionCompat fromMediaDescription(Object paramObject)
  {
    Bundle localBundle1 = null;
    Bundle localBundle2 = null;
    Object localObject = localBundle1;
    if (paramObject != null)
    {
      localObject = localBundle1;
      if (Build.VERSION.SDK_INT >= 21)
      {
        Builder localBuilder = new Builder();
        localBuilder.setMediaId(MediaDescriptionCompatApi21.getMediaId(paramObject));
        localBuilder.setTitle(MediaDescriptionCompatApi21.getTitle(paramObject));
        localBuilder.setSubtitle(MediaDescriptionCompatApi21.getSubtitle(paramObject));
        localBuilder.setDescription(MediaDescriptionCompatApi21.getDescription(paramObject));
        localBuilder.setIconBitmap(MediaDescriptionCompatApi21.getIconBitmap(paramObject));
        localBuilder.setIconUri(MediaDescriptionCompatApi21.getIconUri(paramObject));
        localBundle1 = MediaDescriptionCompatApi21.getExtras(paramObject);
        if (localBundle1 != null)
        {
          MediaSessionCompat.ensureClassLoader(localBundle1);
          localObject = (Uri)localBundle1.getParcelable("android.support.v4.media.description.MEDIA_URI");
        }
        else
        {
          localObject = null;
        }
        if (localObject != null)
        {
          if ((!localBundle1.containsKey("android.support.v4.media.description.NULL_BUNDLE_FLAG")) || (localBundle1.size() != 2))
          {
            localBundle1.remove("android.support.v4.media.description.MEDIA_URI");
            localBundle1.remove("android.support.v4.media.description.NULL_BUNDLE_FLAG");
          }
        }
        else {
          localBundle2 = localBundle1;
        }
        localBuilder.setExtras(localBundle2);
        if (localObject != null) {
          localBuilder.setMediaUri((Uri)localObject);
        } else if (Build.VERSION.SDK_INT >= 23) {
          localBuilder.setMediaUri(MediaDescriptionCompatApi23.getMediaUri(paramObject));
        }
        localObject = localBuilder.build();
        ((MediaDescriptionCompat)localObject).mDescriptionObj = paramObject;
      }
    }
    return localObject;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public CharSequence getDescription()
  {
    return this.mDescription;
  }
  
  public Bundle getExtras()
  {
    return this.mExtras;
  }
  
  public Bitmap getIconBitmap()
  {
    return this.mIcon;
  }
  
  public Uri getIconUri()
  {
    return this.mIconUri;
  }
  
  public Object getMediaDescription()
  {
    if ((this.mDescriptionObj == null) && (Build.VERSION.SDK_INT >= 21))
    {
      Object localObject1 = MediaDescriptionCompatApi21.Builder.newInstance();
      MediaDescriptionCompatApi21.Builder.setMediaId(localObject1, this.mMediaId);
      MediaDescriptionCompatApi21.Builder.setTitle(localObject1, this.mTitle);
      MediaDescriptionCompatApi21.Builder.setSubtitle(localObject1, this.mSubtitle);
      MediaDescriptionCompatApi21.Builder.setDescription(localObject1, this.mDescription);
      MediaDescriptionCompatApi21.Builder.setIconBitmap(localObject1, this.mIcon);
      MediaDescriptionCompatApi21.Builder.setIconUri(localObject1, this.mIconUri);
      Bundle localBundle = this.mExtras;
      Object localObject2 = localBundle;
      if (Build.VERSION.SDK_INT < 23)
      {
        localObject2 = localBundle;
        if (this.mMediaUri != null)
        {
          localObject2 = localBundle;
          if (localBundle == null)
          {
            localObject2 = new Bundle();
            ((Bundle)localObject2).putBoolean("android.support.v4.media.description.NULL_BUNDLE_FLAG", true);
          }
          ((Bundle)localObject2).putParcelable("android.support.v4.media.description.MEDIA_URI", this.mMediaUri);
        }
      }
      MediaDescriptionCompatApi21.Builder.setExtras(localObject1, (Bundle)localObject2);
      if (Build.VERSION.SDK_INT >= 23) {
        MediaDescriptionCompatApi23.Builder.setMediaUri(localObject1, this.mMediaUri);
      }
      localObject2 = MediaDescriptionCompatApi21.Builder.build(localObject1);
      this.mDescriptionObj = localObject2;
      return localObject2;
    }
    return this.mDescriptionObj;
  }
  
  public String getMediaId()
  {
    return this.mMediaId;
  }
  
  public Uri getMediaUri()
  {
    return this.mMediaUri;
  }
  
  public CharSequence getSubtitle()
  {
    return this.mSubtitle;
  }
  
  public CharSequence getTitle()
  {
    return this.mTitle;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.mTitle);
    localStringBuilder.append(", ");
    localStringBuilder.append(this.mSubtitle);
    localStringBuilder.append(", ");
    localStringBuilder.append(this.mDescription);
    return localStringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    if (Build.VERSION.SDK_INT < 21)
    {
      paramParcel.writeString(this.mMediaId);
      TextUtils.writeToParcel(this.mTitle, paramParcel, paramInt);
      TextUtils.writeToParcel(this.mSubtitle, paramParcel, paramInt);
      TextUtils.writeToParcel(this.mDescription, paramParcel, paramInt);
      paramParcel.writeParcelable(this.mIcon, paramInt);
      paramParcel.writeParcelable(this.mIconUri, paramInt);
      paramParcel.writeBundle(this.mExtras);
      paramParcel.writeParcelable(this.mMediaUri, paramInt);
    }
    else
    {
      MediaDescriptionCompatApi21.writeToParcel(getMediaDescription(), paramParcel, paramInt);
    }
  }
  
  public static final class Builder
  {
    private CharSequence mDescription;
    private Bundle mExtras;
    private Bitmap mIcon;
    private Uri mIconUri;
    private String mMediaId;
    private Uri mMediaUri;
    private CharSequence mSubtitle;
    private CharSequence mTitle;
    
    public Builder() {}
    
    public MediaDescriptionCompat build()
    {
      return new MediaDescriptionCompat(this.mMediaId, this.mTitle, this.mSubtitle, this.mDescription, this.mIcon, this.mIconUri, this.mExtras, this.mMediaUri);
    }
    
    public Builder setDescription(CharSequence paramCharSequence)
    {
      this.mDescription = paramCharSequence;
      return this;
    }
    
    public Builder setExtras(Bundle paramBundle)
    {
      this.mExtras = paramBundle;
      return this;
    }
    
    public Builder setIconBitmap(Bitmap paramBitmap)
    {
      this.mIcon = paramBitmap;
      return this;
    }
    
    public Builder setIconUri(Uri paramUri)
    {
      this.mIconUri = paramUri;
      return this;
    }
    
    public Builder setMediaId(String paramString)
    {
      this.mMediaId = paramString;
      return this;
    }
    
    public Builder setMediaUri(Uri paramUri)
    {
      this.mMediaUri = paramUri;
      return this;
    }
    
    public Builder setSubtitle(CharSequence paramCharSequence)
    {
      this.mSubtitle = paramCharSequence;
      return this;
    }
    
    public Builder setTitle(CharSequence paramCharSequence)
    {
      this.mTitle = paramCharSequence;
      return this;
    }
  }
}

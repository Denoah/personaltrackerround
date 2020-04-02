package androidx.media;

import android.os.Bundle;
import java.util.Arrays;

class AudioAttributesImplBase
  implements AudioAttributesImpl
{
  int mContentType = 0;
  int mFlags = 0;
  int mLegacyStream = -1;
  int mUsage = 0;
  
  AudioAttributesImplBase() {}
  
  AudioAttributesImplBase(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mContentType = paramInt1;
    this.mFlags = paramInt2;
    this.mUsage = paramInt3;
    this.mLegacyStream = paramInt4;
  }
  
  public static AudioAttributesImpl fromBundle(Bundle paramBundle)
  {
    if (paramBundle == null) {
      return null;
    }
    int i = paramBundle.getInt("androidx.media.audio_attrs.USAGE", 0);
    return new AudioAttributesImplBase(paramBundle.getInt("androidx.media.audio_attrs.CONTENT_TYPE", 0), paramBundle.getInt("androidx.media.audio_attrs.FLAGS", 0), i, paramBundle.getInt("androidx.media.audio_attrs.LEGACY_STREAM_TYPE", -1));
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof AudioAttributesImplBase;
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    paramObject = (AudioAttributesImplBase)paramObject;
    bool1 = bool2;
    if (this.mContentType == paramObject.getContentType())
    {
      bool1 = bool2;
      if (this.mFlags == paramObject.getFlags())
      {
        bool1 = bool2;
        if (this.mUsage == paramObject.getUsage())
        {
          bool1 = bool2;
          if (this.mLegacyStream == paramObject.mLegacyStream) {
            bool1 = true;
          }
        }
      }
    }
    return bool1;
  }
  
  public Object getAudioAttributes()
  {
    return null;
  }
  
  public int getContentType()
  {
    return this.mContentType;
  }
  
  public int getFlags()
  {
    int i = this.mFlags;
    int j = getLegacyStreamType();
    int k;
    if (j == 6)
    {
      k = i | 0x4;
    }
    else
    {
      k = i;
      if (j == 7) {
        k = i | 0x1;
      }
    }
    return k & 0x111;
  }
  
  public int getLegacyStreamType()
  {
    int i = this.mLegacyStream;
    if (i != -1) {
      return i;
    }
    return AudioAttributesCompat.toVolumeStreamType(false, this.mFlags, this.mUsage);
  }
  
  public int getRawLegacyStreamType()
  {
    return this.mLegacyStream;
  }
  
  public int getUsage()
  {
    return this.mUsage;
  }
  
  public int getVolumeControlStream()
  {
    return AudioAttributesCompat.toVolumeStreamType(true, this.mFlags, this.mUsage);
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(new Object[] { Integer.valueOf(this.mContentType), Integer.valueOf(this.mFlags), Integer.valueOf(this.mUsage), Integer.valueOf(this.mLegacyStream) });
  }
  
  public Bundle toBundle()
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("androidx.media.audio_attrs.USAGE", this.mUsage);
    localBundle.putInt("androidx.media.audio_attrs.CONTENT_TYPE", this.mContentType);
    localBundle.putInt("androidx.media.audio_attrs.FLAGS", this.mFlags);
    int i = this.mLegacyStream;
    if (i != -1) {
      localBundle.putInt("androidx.media.audio_attrs.LEGACY_STREAM_TYPE", i);
    }
    return localBundle;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("AudioAttributesCompat:");
    if (this.mLegacyStream != -1)
    {
      localStringBuilder.append(" stream=");
      localStringBuilder.append(this.mLegacyStream);
      localStringBuilder.append(" derived");
    }
    localStringBuilder.append(" usage=");
    localStringBuilder.append(AudioAttributesCompat.usageToString(this.mUsage));
    localStringBuilder.append(" content=");
    localStringBuilder.append(this.mContentType);
    localStringBuilder.append(" flags=0x");
    localStringBuilder.append(Integer.toHexString(this.mFlags).toUpperCase());
    return localStringBuilder.toString();
  }
}

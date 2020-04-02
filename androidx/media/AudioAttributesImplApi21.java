package androidx.media;

import android.media.AudioAttributes;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class AudioAttributesImplApi21
  implements AudioAttributesImpl
{
  private static final String TAG = "AudioAttributesCompat21";
  static Method sAudioAttributesToLegacyStreamType;
  AudioAttributes mAudioAttributes;
  int mLegacyStreamType = -1;
  
  AudioAttributesImplApi21() {}
  
  AudioAttributesImplApi21(AudioAttributes paramAudioAttributes)
  {
    this(paramAudioAttributes, -1);
  }
  
  AudioAttributesImplApi21(AudioAttributes paramAudioAttributes, int paramInt)
  {
    this.mAudioAttributes = paramAudioAttributes;
    this.mLegacyStreamType = paramInt;
  }
  
  public static AudioAttributesImpl fromBundle(Bundle paramBundle)
  {
    if (paramBundle == null) {
      return null;
    }
    AudioAttributes localAudioAttributes = (AudioAttributes)paramBundle.getParcelable("androidx.media.audio_attrs.FRAMEWORKS");
    if (localAudioAttributes == null) {
      return null;
    }
    return new AudioAttributesImplApi21(localAudioAttributes, paramBundle.getInt("androidx.media.audio_attrs.LEGACY_STREAM_TYPE", -1));
  }
  
  static Method getAudioAttributesToLegacyStreamTypeMethod()
  {
    try
    {
      if (sAudioAttributesToLegacyStreamType == null) {
        sAudioAttributesToLegacyStreamType = AudioAttributes.class.getMethod("toLegacyStreamType", new Class[] { AudioAttributes.class });
      }
      return sAudioAttributesToLegacyStreamType;
    }
    catch (NoSuchMethodException localNoSuchMethodException) {}
    return null;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof AudioAttributesImplApi21)) {
      return false;
    }
    paramObject = (AudioAttributesImplApi21)paramObject;
    return this.mAudioAttributes.equals(paramObject.mAudioAttributes);
  }
  
  public Object getAudioAttributes()
  {
    return this.mAudioAttributes;
  }
  
  public int getContentType()
  {
    return this.mAudioAttributes.getContentType();
  }
  
  public int getFlags()
  {
    return this.mAudioAttributes.getFlags();
  }
  
  public int getLegacyStreamType()
  {
    int i = this.mLegacyStreamType;
    if (i != -1) {
      return i;
    }
    Object localObject = getAudioAttributesToLegacyStreamTypeMethod();
    if (localObject == null)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("No AudioAttributes#toLegacyStreamType() on API: ");
      ((StringBuilder)localObject).append(Build.VERSION.SDK_INT);
      Log.w("AudioAttributesCompat21", ((StringBuilder)localObject).toString());
      return -1;
    }
    try
    {
      i = ((Integer)((Method)localObject).invoke(null, new Object[] { this.mAudioAttributes })).intValue();
      return i;
    }
    catch (IllegalAccessException localIllegalAccessException) {}catch (InvocationTargetException localInvocationTargetException) {}
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getLegacyStreamType() failed on API: ");
    localStringBuilder.append(Build.VERSION.SDK_INT);
    Log.w("AudioAttributesCompat21", localStringBuilder.toString(), localInvocationTargetException);
    return -1;
  }
  
  public int getRawLegacyStreamType()
  {
    return this.mLegacyStreamType;
  }
  
  public int getUsage()
  {
    return this.mAudioAttributes.getUsage();
  }
  
  public int getVolumeControlStream()
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return this.mAudioAttributes.getVolumeControlStream();
    }
    return AudioAttributesCompat.toVolumeStreamType(true, getFlags(), getUsage());
  }
  
  public int hashCode()
  {
    return this.mAudioAttributes.hashCode();
  }
  
  public Bundle toBundle()
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("androidx.media.audio_attrs.FRAMEWORKS", this.mAudioAttributes);
    int i = this.mLegacyStreamType;
    if (i != -1) {
      localBundle.putInt("androidx.media.audio_attrs.LEGACY_STREAM_TYPE", i);
    }
    return localBundle;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("AudioAttributesCompat: audioattributes=");
    localStringBuilder.append(this.mAudioAttributes);
    return localStringBuilder.toString();
  }
}

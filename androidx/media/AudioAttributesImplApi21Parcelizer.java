package androidx.media;

import android.media.AudioAttributes;
import androidx.versionedparcelable.VersionedParcel;

public final class AudioAttributesImplApi21Parcelizer
{
  public AudioAttributesImplApi21Parcelizer() {}
  
  public static AudioAttributesImplApi21 read(VersionedParcel paramVersionedParcel)
  {
    AudioAttributesImplApi21 localAudioAttributesImplApi21 = new AudioAttributesImplApi21();
    localAudioAttributesImplApi21.mAudioAttributes = ((AudioAttributes)paramVersionedParcel.readParcelable(localAudioAttributesImplApi21.mAudioAttributes, 1));
    localAudioAttributesImplApi21.mLegacyStreamType = paramVersionedParcel.readInt(localAudioAttributesImplApi21.mLegacyStreamType, 2);
    return localAudioAttributesImplApi21;
  }
  
  public static void write(AudioAttributesImplApi21 paramAudioAttributesImplApi21, VersionedParcel paramVersionedParcel)
  {
    paramVersionedParcel.setSerializationFlags(false, false);
    paramVersionedParcel.writeParcelable(paramAudioAttributesImplApi21.mAudioAttributes, 1);
    paramVersionedParcel.writeInt(paramAudioAttributesImplApi21.mLegacyStreamType, 2);
  }
}

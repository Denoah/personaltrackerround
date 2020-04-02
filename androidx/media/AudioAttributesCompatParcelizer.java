package androidx.media;

import androidx.versionedparcelable.VersionedParcel;

public final class AudioAttributesCompatParcelizer
{
  public AudioAttributesCompatParcelizer() {}
  
  public static AudioAttributesCompat read(VersionedParcel paramVersionedParcel)
  {
    AudioAttributesCompat localAudioAttributesCompat = new AudioAttributesCompat();
    localAudioAttributesCompat.mImpl = ((AudioAttributesImpl)paramVersionedParcel.readVersionedParcelable(localAudioAttributesCompat.mImpl, 1));
    return localAudioAttributesCompat;
  }
  
  public static void write(AudioAttributesCompat paramAudioAttributesCompat, VersionedParcel paramVersionedParcel)
  {
    paramVersionedParcel.setSerializationFlags(false, false);
    paramVersionedParcel.writeVersionedParcelable(paramAudioAttributesCompat.mImpl, 1);
  }
}

package androidx.media;

import androidx.versionedparcelable.VersionedParcel;

public final class AudioAttributesImplBaseParcelizer
{
  public AudioAttributesImplBaseParcelizer() {}
  
  public static AudioAttributesImplBase read(VersionedParcel paramVersionedParcel)
  {
    AudioAttributesImplBase localAudioAttributesImplBase = new AudioAttributesImplBase();
    localAudioAttributesImplBase.mUsage = paramVersionedParcel.readInt(localAudioAttributesImplBase.mUsage, 1);
    localAudioAttributesImplBase.mContentType = paramVersionedParcel.readInt(localAudioAttributesImplBase.mContentType, 2);
    localAudioAttributesImplBase.mFlags = paramVersionedParcel.readInt(localAudioAttributesImplBase.mFlags, 3);
    localAudioAttributesImplBase.mLegacyStream = paramVersionedParcel.readInt(localAudioAttributesImplBase.mLegacyStream, 4);
    return localAudioAttributesImplBase;
  }
  
  public static void write(AudioAttributesImplBase paramAudioAttributesImplBase, VersionedParcel paramVersionedParcel)
  {
    paramVersionedParcel.setSerializationFlags(false, false);
    paramVersionedParcel.writeInt(paramAudioAttributesImplBase.mUsage, 1);
    paramVersionedParcel.writeInt(paramAudioAttributesImplBase.mContentType, 2);
    paramVersionedParcel.writeInt(paramAudioAttributesImplBase.mFlags, 3);
    paramVersionedParcel.writeInt(paramAudioAttributesImplBase.mLegacyStream, 4);
  }
}

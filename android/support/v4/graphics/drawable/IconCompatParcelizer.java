package android.support.v4.graphics.drawable;

import androidx.core.graphics.drawable.IconCompat;
import androidx.versionedparcelable.VersionedParcel;

public final class IconCompatParcelizer
  extends androidx.core.graphics.drawable.IconCompatParcelizer
{
  public IconCompatParcelizer() {}
  
  public static IconCompat read(VersionedParcel paramVersionedParcel)
  {
    return androidx.core.graphics.drawable.IconCompatParcelizer.read(paramVersionedParcel);
  }
  
  public static void write(IconCompat paramIconCompat, VersionedParcel paramVersionedParcel)
  {
    androidx.core.graphics.drawable.IconCompatParcelizer.write(paramIconCompat, paramVersionedParcel);
  }
}

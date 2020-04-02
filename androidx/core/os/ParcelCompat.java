package androidx.core.os;

import android.os.Parcel;

public final class ParcelCompat
{
  private ParcelCompat() {}
  
  public static boolean readBoolean(Parcel paramParcel)
  {
    boolean bool;
    if (paramParcel.readInt() != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static void writeBoolean(Parcel paramParcel, boolean paramBoolean)
  {
    paramParcel.writeInt(paramBoolean);
  }
}

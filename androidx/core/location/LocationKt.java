package androidx.core.location;

import android.location.Location;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\016\n\000\n\002\020\006\n\002\030\002\n\002\b\002\032\r\020\000\032\0020\001*\0020\002H?\n\032\r\020\003\032\0020\001*\0020\002H?\n?\006\004"}, d2={"component1", "", "Landroid/location/Location;", "component2", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class LocationKt
{
  public static final double component1(Location paramLocation)
  {
    Intrinsics.checkParameterIsNotNull(paramLocation, "$this$component1");
    return paramLocation.getLatitude();
  }
  
  public static final double component2(Location paramLocation)
  {
    Intrinsics.checkParameterIsNotNull(paramLocation, "$this$component2");
    return paramLocation.getLongitude();
  }
}

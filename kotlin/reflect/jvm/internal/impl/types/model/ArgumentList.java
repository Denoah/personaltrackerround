package kotlin.reflect.jvm.internal.impl.types.model;

import java.util.ArrayList;

public final class ArgumentList
  extends ArrayList<TypeArgumentMarker>
  implements TypeArgumentListMarker
{
  public ArgumentList(int paramInt)
  {
    super(paramInt);
  }
}

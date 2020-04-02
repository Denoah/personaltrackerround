package kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins;

import java.io.InputStream;
import kotlin.jvm.internal.Intrinsics;

public final class BuiltInsResourceLoader
{
  public BuiltInsResourceLoader() {}
  
  public final InputStream loadResource(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "path");
    Object localObject = getClass().getClassLoader();
    if (localObject != null)
    {
      localObject = ((ClassLoader)localObject).getResourceAsStream(paramString);
      if (localObject != null) {
        return (String)localObject;
      }
    }
    paramString = ClassLoader.getSystemResourceAsStream(paramString);
    return paramString;
  }
}

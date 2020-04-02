package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.io.InputStream;
import kotlin.reflect.jvm.internal.impl.name.FqName;

public abstract interface KotlinMetadataFinder
{
  public abstract InputStream findBuiltInsData(FqName paramFqName);
}

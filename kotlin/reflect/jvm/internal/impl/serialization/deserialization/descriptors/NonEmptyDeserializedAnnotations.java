package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;

public final class NonEmptyDeserializedAnnotations
  extends DeserializedAnnotations
{
  public NonEmptyDeserializedAnnotations(StorageManager paramStorageManager, Function0<? extends List<? extends AnnotationDescriptor>> paramFunction0)
  {
    super(paramStorageManager, paramFunction0);
  }
  
  public boolean isEmpty()
  {
    return false;
  }
}

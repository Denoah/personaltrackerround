package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.reflect.jvm.internal.impl.name.ClassId;

public abstract interface ClassDataFinder
{
  public abstract ClassData findClassData(ClassId paramClassId);
}

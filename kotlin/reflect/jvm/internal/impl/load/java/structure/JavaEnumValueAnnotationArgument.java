package kotlin.reflect.jvm.internal.impl.load.java.structure;

import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;

public abstract interface JavaEnumValueAnnotationArgument
  extends JavaAnnotationArgument
{
  public abstract Name getEntryName();
  
  public abstract ClassId getEnumClassId();
}

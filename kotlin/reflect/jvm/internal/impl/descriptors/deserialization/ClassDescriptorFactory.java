package kotlin.reflect.jvm.internal.impl.descriptors.deserialization;

import java.util.Collection;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;

public abstract interface ClassDescriptorFactory
{
  public abstract ClassDescriptor createClass(ClassId paramClassId);
  
  public abstract Collection<ClassDescriptor> getAllContributedClassesIfPossible(FqName paramFqName);
  
  public abstract boolean shouldCreateClass(FqName paramFqName, Name paramName);
}

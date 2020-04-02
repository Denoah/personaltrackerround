package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.DefaultImpls;
import kotlin.reflect.jvm.internal.impl.name.FqName;

final class EnhancedTypeAnnotations
  implements Annotations
{
  private final FqName fqNameToMatch;
  
  public EnhancedTypeAnnotations(FqName paramFqName)
  {
    this.fqNameToMatch = paramFqName;
  }
  
  public EnhancedTypeAnnotationDescriptor findAnnotation(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    if (Intrinsics.areEqual(paramFqName, this.fqNameToMatch)) {
      paramFqName = EnhancedTypeAnnotationDescriptor.INSTANCE;
    } else {
      paramFqName = null;
    }
    return paramFqName;
  }
  
  public boolean hasAnnotation(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    return Annotations.DefaultImpls.hasAnnotation(this, paramFqName);
  }
  
  public boolean isEmpty()
  {
    return false;
  }
  
  public Iterator<AnnotationDescriptor> iterator()
  {
    return CollectionsKt.emptyList().iterator();
  }
}

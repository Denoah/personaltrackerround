package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;

public final class ReflectJavaAnnotationOwnerKt
{
  public static final ReflectJavaAnnotation findAnnotation(Annotation[] paramArrayOfAnnotation, FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfAnnotation, "$this$findAnnotation");
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    int i = paramArrayOfAnnotation.length;
    Object localObject;
    for (int j = 0;; j++)
    {
      localObject = null;
      if (j >= i) {
        break;
      }
      Annotation localAnnotation = paramArrayOfAnnotation[j];
      if (Intrinsics.areEqual(ReflectClassUtilKt.getClassId(JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(localAnnotation))).asSingleFqName(), paramFqName))
      {
        paramArrayOfAnnotation = localAnnotation;
        break label65;
      }
    }
    paramArrayOfAnnotation = null;
    label65:
    paramFqName = localObject;
    if (paramArrayOfAnnotation != null) {
      paramFqName = new ReflectJavaAnnotation(paramArrayOfAnnotation);
    }
    return paramFqName;
  }
  
  public static final List<ReflectJavaAnnotation> getAnnotations(Annotation[] paramArrayOfAnnotation)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfAnnotation, "$this$getAnnotations");
    Collection localCollection = (Collection)new ArrayList(paramArrayOfAnnotation.length);
    int i = paramArrayOfAnnotation.length;
    for (int j = 0; j < i; j++) {
      localCollection.add(new ReflectJavaAnnotation(paramArrayOfAnnotation[j]));
    }
    return (List)localCollection;
  }
}

package kotlin.reflect.jvm;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.jvm.internal.KTypeImpl;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;

@Metadata(bv={1, 0, 3}, d1={"\000\026\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\"\034\020\000\032\006\022\002\b\0030\001*\0020\0028@X?\004?\006\006\032\004\b\003\020\004\"\"\020\000\032\006\022\002\b\0030\001*\0020\0058FX?\004?\006\f\022\004\b\006\020\007\032\004\b\003\020\b?\006\t"}, d2={"jvmErasure", "Lkotlin/reflect/KClass;", "Lkotlin/reflect/KClassifier;", "getJvmErasure", "(Lkotlin/reflect/KClassifier;)Lkotlin/reflect/KClass;", "Lkotlin/reflect/KType;", "jvmErasure$annotations", "(Lkotlin/reflect/KType;)V", "(Lkotlin/reflect/KType;)Lkotlin/reflect/KClass;", "kotlin-reflection"}, k=2, mv={1, 1, 15})
public final class KTypesJvm
{
  public static final KClass<?> getJvmErasure(KClassifier paramKClassifier)
  {
    Intrinsics.checkParameterIsNotNull(paramKClassifier, "$this$jvmErasure");
    if ((paramKClassifier instanceof KClass))
    {
      paramKClassifier = (KClass)paramKClassifier;
    }
    else
    {
      if (!(paramKClassifier instanceof KTypeParameter)) {
        break label218;
      }
      List localList = ((KTypeParameter)paramKClassifier).getUpperBounds();
      Iterator localIterator = ((Iterable)localList).iterator();
      int i;
      do
      {
        boolean bool = localIterator.hasNext();
        paramKClassifier = null;
        Object localObject1 = null;
        if (!bool) {
          break label174;
        }
        localObject2 = localIterator.next();
        paramKClassifier = (KType)localObject2;
        if (paramKClassifier == null) {
          break;
        }
        paramKClassifier = ((KTypeImpl)paramKClassifier).getType().getConstructor().getDeclarationDescriptor();
        if (!(paramKClassifier instanceof ClassDescriptor)) {
          paramKClassifier = localObject1;
        }
        paramKClassifier = (ClassDescriptor)paramKClassifier;
        if ((paramKClassifier != null) && (paramKClassifier.getKind() != ClassKind.INTERFACE) && (paramKClassifier.getKind() != ClassKind.ANNOTATION_CLASS)) {
          i = 1;
        } else {
          i = 0;
        }
      } while (i == 0);
      paramKClassifier = (KClassifier)localObject2;
      break label174;
      throw new TypeCastException("null cannot be cast to non-null type kotlin.reflect.jvm.internal.KTypeImpl");
      label174:
      paramKClassifier = (KType)paramKClassifier;
      if (paramKClassifier == null) {
        paramKClassifier = (KType)CollectionsKt.firstOrNull(localList);
      }
      if (paramKClassifier != null)
      {
        paramKClassifier = getJvmErasure(paramKClassifier);
        if (paramKClassifier != null) {}
      }
      else
      {
        paramKClassifier = Reflection.getOrCreateKotlinClass(Object.class);
      }
    }
    return paramKClassifier;
    label218:
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("Cannot calculate JVM erasure for type: ");
    ((StringBuilder)localObject2).append(paramKClassifier);
    throw ((Throwable)new KotlinReflectionInternalError(((StringBuilder)localObject2).toString()));
  }
  
  public static final KClass<?> getJvmErasure(KType paramKType)
  {
    Intrinsics.checkParameterIsNotNull(paramKType, "$this$jvmErasure");
    Object localObject = paramKType.getClassifier();
    if (localObject != null)
    {
      localObject = getJvmErasure((KClassifier)localObject);
      if (localObject != null) {
        return localObject;
      }
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Cannot calculate JVM erasure for type: ");
    ((StringBuilder)localObject).append(paramKType);
    throw ((Throwable)new KotlinReflectionInternalError(((StringBuilder)localObject).toString()));
  }
}

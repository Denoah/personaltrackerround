package kotlin.reflect.full;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeProjection;
import kotlin.reflect.KTypeProjection.Companion;
import kotlin.reflect.jvm.internal.KClassifierImpl;
import kotlin.reflect.jvm.internal.KTypeImpl;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionBase;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.Variance;

@Metadata(bv={1, 0, 3}, d1={"\0008\n\000\n\002\030\002\n\002\030\002\n\002\b\005\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020 \n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\020\033\n\000\032.\020\007\032\0020\b2\006\020\t\032\0020\n2\006\020\013\032\0020\f2\f\020\r\032\b\022\004\022\0020\0170\0162\006\020\020\032\0020\021H\002\0326\020\022\032\0020\001*\0020\0022\016\b\002\020\r\032\b\022\004\022\0020\0170\0162\b\b\002\020\020\032\0020\0212\016\b\002\020\023\032\b\022\004\022\0020\0240\016H\007\"\036\020\000\032\0020\001*\0020\0028FX?\004?\006\f\022\004\b\003\020\004\032\004\b\005\020\006?\006\025"}, d2={"starProjectedType", "Lkotlin/reflect/KType;", "Lkotlin/reflect/KClassifier;", "starProjectedType$annotations", "(Lkotlin/reflect/KClassifier;)V", "getStarProjectedType", "(Lkotlin/reflect/KClassifier;)Lkotlin/reflect/KType;", "createKotlinType", "Lkotlin/reflect/jvm/internal/impl/types/SimpleType;", "typeAnnotations", "Lkotlin/reflect/jvm/internal/impl/descriptors/annotations/Annotations;", "typeConstructor", "Lkotlin/reflect/jvm/internal/impl/types/TypeConstructor;", "arguments", "", "Lkotlin/reflect/KTypeProjection;", "nullable", "", "createType", "annotations", "", "kotlin-reflection"}, k=2, mv={1, 1, 15})
public final class KClassifiers
{
  private static final SimpleType createKotlinType(Annotations paramAnnotations, TypeConstructor paramTypeConstructor, List<KTypeProjection> paramList, boolean paramBoolean)
  {
    List localList = paramTypeConstructor.getParameters();
    Intrinsics.checkExpressionValueIsNotNull(localList, "typeConstructor.parameters");
    paramList = (Iterable)paramList;
    Collection localCollection = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault(paramList, 10));
    Iterator localIterator = paramList.iterator();
    for (int i = 0; localIterator.hasNext(); i++)
    {
      paramList = localIterator.next();
      if (i < 0) {
        CollectionsKt.throwIndexOverflow();
      }
      Object localObject = (KTypeProjection)paramList;
      paramList = (KTypeImpl)((KTypeProjection)localObject).getType();
      if (paramList != null) {
        paramList = paramList.getType();
      } else {
        paramList = null;
      }
      localObject = ((KTypeProjection)localObject).getVariance();
      if (localObject == null)
      {
        paramList = localList.get(i);
        Intrinsics.checkExpressionValueIsNotNull(paramList, "parameters[index]");
        paramList = (TypeProjectionBase)new StarProjectionImpl((TypeParameterDescriptor)paramList);
      }
      else
      {
        int j = KClassifiers.WhenMappings.$EnumSwitchMapping$0[localObject.ordinal()];
        if (j != 1)
        {
          if (j != 2)
          {
            if (j == 3)
            {
              localObject = Variance.OUT_VARIANCE;
              if (paramList == null) {
                Intrinsics.throwNpe();
              }
              paramList = (TypeProjectionBase)new TypeProjectionImpl((Variance)localObject, paramList);
            }
            else
            {
              throw new NoWhenBranchMatchedException();
            }
          }
          else
          {
            localObject = Variance.IN_VARIANCE;
            if (paramList == null) {
              Intrinsics.throwNpe();
            }
            paramList = (TypeProjectionBase)new TypeProjectionImpl((Variance)localObject, paramList);
          }
        }
        else
        {
          localObject = Variance.INVARIANT;
          if (paramList == null) {
            Intrinsics.throwNpe();
          }
          paramList = (TypeProjectionBase)new TypeProjectionImpl((Variance)localObject, paramList);
        }
      }
      localCollection.add(paramList);
    }
    return KotlinTypeFactory.simpleType$default(paramAnnotations, paramTypeConstructor, (List)localCollection, paramBoolean, null, 16, null);
  }
  
  public static final KType createType(KClassifier paramKClassifier, List<KTypeProjection> paramList, boolean paramBoolean, List<? extends Annotation> paramList1)
  {
    Intrinsics.checkParameterIsNotNull(paramKClassifier, "$this$createType");
    Intrinsics.checkParameterIsNotNull(paramList, "arguments");
    Intrinsics.checkParameterIsNotNull(paramList1, "annotations");
    if (!(paramKClassifier instanceof KClassifierImpl)) {
      localObject = null;
    } else {
      localObject = paramKClassifier;
    }
    Object localObject = (KClassifierImpl)localObject;
    if (localObject != null)
    {
      localObject = ((KClassifierImpl)localObject).getDescriptor();
      if (localObject != null)
      {
        localObject = ((ClassifierDescriptor)localObject).getTypeConstructor();
        Intrinsics.checkExpressionValueIsNotNull(localObject, "descriptor.typeConstructor");
        List localList = ((TypeConstructor)localObject).getParameters();
        Intrinsics.checkExpressionValueIsNotNull(localList, "typeConstructor.parameters");
        if (localList.size() == paramList.size())
        {
          if (paramList1.isEmpty()) {
            paramList1 = Annotations.Companion.getEMPTY();
          } else {
            paramList1 = Annotations.Companion.getEMPTY();
          }
          (KType)new KTypeImpl((KotlinType)createKotlinType(paramList1, (TypeConstructor)localObject, paramList, paramBoolean), (Function0)new Lambda(paramKClassifier)
          {
            public final Void invoke()
            {
              StringBuilder localStringBuilder = new StringBuilder();
              localStringBuilder.append("Java type is not yet supported for types created with createType (classifier = ");
              localStringBuilder.append(this.$this_createType);
              localStringBuilder.append(')');
              String str = localStringBuilder.toString();
              localStringBuilder = new StringBuilder();
              localStringBuilder.append("An operation is not implemented: ");
              localStringBuilder.append(str);
              throw ((Throwable)new NotImplementedError(localStringBuilder.toString()));
            }
          });
        }
        paramKClassifier = new StringBuilder();
        paramKClassifier.append("Class declares ");
        paramKClassifier.append(localList.size());
        paramKClassifier.append(" type parameters, but ");
        paramKClassifier.append(paramList.size());
        paramKClassifier.append(" were provided.");
        throw ((Throwable)new IllegalArgumentException(paramKClassifier.toString()));
      }
    }
    paramList = new StringBuilder();
    paramList.append("Cannot create type for an unsupported classifier: ");
    paramList.append(paramKClassifier);
    paramList.append(" (");
    paramList.append(paramKClassifier.getClass());
    paramList.append(')');
    throw ((Throwable)new KotlinReflectionInternalError(paramList.toString()));
  }
  
  public static final KType getStarProjectedType(KClassifier paramKClassifier)
  {
    Intrinsics.checkParameterIsNotNull(paramKClassifier, "$this$starProjectedType");
    if (!(paramKClassifier instanceof KClassifierImpl)) {
      localObject1 = null;
    } else {
      localObject1 = paramKClassifier;
    }
    Object localObject1 = (KClassifierImpl)localObject1;
    if (localObject1 != null)
    {
      localObject1 = ((KClassifierImpl)localObject1).getDescriptor();
      if (localObject1 != null)
      {
        localObject1 = ((ClassifierDescriptor)localObject1).getTypeConstructor();
        Intrinsics.checkExpressionValueIsNotNull(localObject1, "descriptor.typeConstructor");
        localObject1 = ((TypeConstructor)localObject1).getParameters();
        Intrinsics.checkExpressionValueIsNotNull(localObject1, "descriptor.typeConstructor.parameters");
        if (((List)localObject1).isEmpty()) {
          return createType$default(paramKClassifier, null, false, null, 7, null);
        }
        Object localObject2 = (Iterable)localObject1;
        localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
        Iterator localIterator = ((Iterable)localObject2).iterator();
        while (localIterator.hasNext())
        {
          localObject2 = (TypeParameterDescriptor)localIterator.next();
          ((Collection)localObject1).add(KTypeProjection.Companion.getSTAR());
        }
        return createType$default(paramKClassifier, (List)localObject1, false, null, 6, null);
      }
    }
    return createType$default(paramKClassifier, null, false, null, 7, null);
  }
}

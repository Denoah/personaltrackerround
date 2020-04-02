package kotlin.reflect.jvm.internal.impl.resolve.calls.inference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager;
import kotlin.reflect.jvm.internal.impl.types.DelegatedTypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.IndexedParametersSubstitution;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.LazyWrappedType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.Variance;

public final class CapturedTypeConstructorKt
{
  private static final TypeProjection createCapturedIfNeeded(TypeProjection paramTypeProjection, TypeParameterDescriptor paramTypeParameterDescriptor)
  {
    if ((paramTypeParameterDescriptor != null) && (paramTypeProjection.getProjectionKind() != Variance.INVARIANT))
    {
      if (paramTypeParameterDescriptor.getVariance() == paramTypeProjection.getProjectionKind())
      {
        if (paramTypeProjection.isStarProjection())
        {
          paramTypeParameterDescriptor = LockBasedStorageManager.NO_LOCKS;
          Intrinsics.checkExpressionValueIsNotNull(paramTypeParameterDescriptor, "LockBasedStorageManager.NO_LOCKS");
          paramTypeProjection = (TypeProjection)new TypeProjectionImpl((KotlinType)new LazyWrappedType(paramTypeParameterDescriptor, (Function0)new Lambda(paramTypeProjection)
          {
            public final KotlinType invoke()
            {
              KotlinType localKotlinType = this.$this_createCapturedIfNeeded.getType();
              Intrinsics.checkExpressionValueIsNotNull(localKotlinType, "this@createCapturedIfNeeded.type");
              return localKotlinType;
            }
          }));
        }
        else
        {
          paramTypeProjection = (TypeProjection)new TypeProjectionImpl(paramTypeProjection.getType());
        }
        return paramTypeProjection;
      }
      return (TypeProjection)new TypeProjectionImpl(createCapturedType(paramTypeProjection));
    }
    return paramTypeProjection;
  }
  
  public static final KotlinType createCapturedType(TypeProjection paramTypeProjection)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeProjection, "typeProjection");
    return (KotlinType)new CapturedType(paramTypeProjection, null, false, null, 14, null);
  }
  
  public static final boolean isCaptured(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$isCaptured");
    return paramKotlinType.getConstructor() instanceof CapturedTypeConstructor;
  }
  
  public static final TypeSubstitution wrapWithCapturingSubstitution(TypeSubstitution paramTypeSubstitution, final boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeSubstitution, "$this$wrapWithCapturingSubstitution");
    if ((paramTypeSubstitution instanceof IndexedParametersSubstitution))
    {
      Object localObject1 = (IndexedParametersSubstitution)paramTypeSubstitution;
      paramTypeSubstitution = ((IndexedParametersSubstitution)localObject1).getParameters();
      Object localObject2 = (Iterable)ArraysKt.zip(((IndexedParametersSubstitution)localObject1).getArguments(), ((IndexedParametersSubstitution)localObject1).getParameters());
      localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
      Iterator localIterator = ((Iterable)localObject2).iterator();
      while (localIterator.hasNext())
      {
        localObject2 = (Pair)localIterator.next();
        ((Collection)localObject1).add(createCapturedIfNeeded((TypeProjection)((Pair)localObject2).getFirst(), (TypeParameterDescriptor)((Pair)localObject2).getSecond()));
      }
      localObject1 = ((Collection)localObject1).toArray(new TypeProjection[0]);
      if (localObject1 != null) {
        paramTypeSubstitution = (TypeSubstitution)new IndexedParametersSubstitution(paramTypeSubstitution, (TypeProjection[])localObject1, paramBoolean);
      } else {
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      }
    }
    else
    {
      paramTypeSubstitution = (TypeSubstitution)new DelegatedTypeSubstitution(paramTypeSubstitution)
      {
        public boolean approximateContravariantCapturedTypes()
        {
          return paramBoolean;
        }
        
        public TypeProjection get(KotlinType paramAnonymousKotlinType)
        {
          Intrinsics.checkParameterIsNotNull(paramAnonymousKotlinType, "key");
          TypeProjection localTypeProjection1 = super.get(paramAnonymousKotlinType);
          TypeProjection localTypeProjection2 = null;
          Object localObject = null;
          if (localTypeProjection1 != null)
          {
            paramAnonymousKotlinType = paramAnonymousKotlinType.getConstructor().getDeclarationDescriptor();
            if (!(paramAnonymousKotlinType instanceof TypeParameterDescriptor)) {
              paramAnonymousKotlinType = localObject;
            }
            localTypeProjection2 = CapturedTypeConstructorKt.access$createCapturedIfNeeded(localTypeProjection1, (TypeParameterDescriptor)paramAnonymousKotlinType);
          }
          return localTypeProjection2;
        }
      };
    }
    return paramTypeSubstitution;
  }
}

package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;

public abstract class TypeConstructorSubstitution
  extends TypeSubstitution
{
  public static final Companion Companion = new Companion(null);
  
  public TypeConstructorSubstitution() {}
  
  @JvmStatic
  public static final TypeSubstitution create(TypeConstructor paramTypeConstructor, List<? extends TypeProjection> paramList)
  {
    return Companion.create(paramTypeConstructor, paramList);
  }
  
  @JvmStatic
  public static final TypeConstructorSubstitution createByConstructorsMap(Map<TypeConstructor, ? extends TypeProjection> paramMap)
  {
    return Companion.createByConstructorsMap$default(Companion, paramMap, false, 2, null);
  }
  
  public TypeProjection get(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "key");
    return get(paramKotlinType.getConstructor());
  }
  
  public abstract TypeProjection get(TypeConstructor paramTypeConstructor);
  
  public static final class Companion
  {
    private Companion() {}
    
    @JvmStatic
    public final TypeSubstitution create(KotlinType paramKotlinType)
    {
      Intrinsics.checkParameterIsNotNull(paramKotlinType, "kotlinType");
      return ((Companion)this).create(paramKotlinType.getConstructor(), paramKotlinType.getArguments());
    }
    
    @JvmStatic
    public final TypeSubstitution create(TypeConstructor paramTypeConstructor, List<? extends TypeProjection> paramList)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeConstructor, "typeConstructor");
      Intrinsics.checkParameterIsNotNull(paramList, "arguments");
      Object localObject1 = paramTypeConstructor.getParameters();
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "typeConstructor.parameters");
      Object localObject2 = (TypeParameterDescriptor)CollectionsKt.lastOrNull((List)localObject1);
      boolean bool;
      if (localObject2 != null) {
        bool = ((TypeParameterDescriptor)localObject2).isCapturedFromOuterDeclaration();
      } else {
        bool = false;
      }
      if (bool)
      {
        localObject1 = (Companion)this;
        paramTypeConstructor = paramTypeConstructor.getParameters();
        Intrinsics.checkExpressionValueIsNotNull(paramTypeConstructor, "typeConstructor.parameters");
        localObject2 = (Iterable)paramTypeConstructor;
        paramTypeConstructor = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
        localObject2 = ((Iterable)localObject2).iterator();
        while (((Iterator)localObject2).hasNext())
        {
          TypeParameterDescriptor localTypeParameterDescriptor = (TypeParameterDescriptor)((Iterator)localObject2).next();
          Intrinsics.checkExpressionValueIsNotNull(localTypeParameterDescriptor, "it");
          paramTypeConstructor.add(localTypeParameterDescriptor.getTypeConstructor());
        }
        return (TypeSubstitution)createByConstructorsMap$default((Companion)localObject1, MapsKt.toMap((Iterable)CollectionsKt.zip((Iterable)paramTypeConstructor, (Iterable)paramList)), false, 2, null);
      }
      return (TypeSubstitution)new IndexedParametersSubstitution((List)localObject1, paramList);
    }
    
    @JvmStatic
    public final TypeConstructorSubstitution createByConstructorsMap(Map<TypeConstructor, ? extends TypeProjection> paramMap, final boolean paramBoolean)
    {
      Intrinsics.checkParameterIsNotNull(paramMap, "map");
      (TypeConstructorSubstitution)new TypeConstructorSubstitution()
      {
        public boolean approximateCapturedTypes()
        {
          return paramBoolean;
        }
        
        public TypeProjection get(TypeConstructor paramAnonymousTypeConstructor)
        {
          Intrinsics.checkParameterIsNotNull(paramAnonymousTypeConstructor, "key");
          return (TypeProjection)this.$map.get(paramAnonymousTypeConstructor);
        }
        
        public boolean isEmpty()
        {
          return this.$map.isEmpty();
        }
      };
    }
  }
}

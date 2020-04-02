package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;

public final class TypeAliasExpansion
{
  public static final Companion Companion = new Companion(null);
  private final List<TypeProjection> arguments;
  private final TypeAliasDescriptor descriptor;
  private final Map<TypeParameterDescriptor, TypeProjection> mapping;
  private final TypeAliasExpansion parent;
  
  private TypeAliasExpansion(TypeAliasExpansion paramTypeAliasExpansion, TypeAliasDescriptor paramTypeAliasDescriptor, List<? extends TypeProjection> paramList, Map<TypeParameterDescriptor, ? extends TypeProjection> paramMap)
  {
    this.parent = paramTypeAliasExpansion;
    this.descriptor = paramTypeAliasDescriptor;
    this.arguments = paramList;
    this.mapping = paramMap;
  }
  
  public final List<TypeProjection> getArguments()
  {
    return this.arguments;
  }
  
  public final TypeAliasDescriptor getDescriptor()
  {
    return this.descriptor;
  }
  
  public final TypeProjection getReplacement(TypeConstructor paramTypeConstructor)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructor, "constructor");
    paramTypeConstructor = paramTypeConstructor.getDeclarationDescriptor();
    if ((paramTypeConstructor instanceof TypeParameterDescriptor)) {
      paramTypeConstructor = (TypeProjection)this.mapping.get(paramTypeConstructor);
    } else {
      paramTypeConstructor = null;
    }
    return paramTypeConstructor;
  }
  
  public final boolean isRecursion(TypeAliasDescriptor paramTypeAliasDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeAliasDescriptor, "descriptor");
    boolean bool1 = Intrinsics.areEqual(this.descriptor, paramTypeAliasDescriptor);
    boolean bool2 = false;
    if (!bool1)
    {
      TypeAliasExpansion localTypeAliasExpansion = this.parent;
      if (localTypeAliasExpansion != null) {
        bool1 = localTypeAliasExpansion.isRecursion(paramTypeAliasDescriptor);
      } else {
        bool1 = false;
      }
      if (!bool1) {}
    }
    else
    {
      bool2 = true;
    }
    return bool2;
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    public final TypeAliasExpansion create(TypeAliasExpansion paramTypeAliasExpansion, TypeAliasDescriptor paramTypeAliasDescriptor, List<? extends TypeProjection> paramList)
    {
      Intrinsics.checkParameterIsNotNull(paramTypeAliasDescriptor, "typeAliasDescriptor");
      Intrinsics.checkParameterIsNotNull(paramList, "arguments");
      Object localObject1 = paramTypeAliasDescriptor.getTypeConstructor();
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "typeAliasDescriptor.typeConstructor");
      localObject1 = ((TypeConstructor)localObject1).getParameters();
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "typeAliasDescriptor.typeConstructor.parameters");
      Object localObject2 = (Iterable)localObject1;
      localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
      Iterator localIterator = ((Iterable)localObject2).iterator();
      while (localIterator.hasNext())
      {
        localObject2 = (TypeParameterDescriptor)localIterator.next();
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "it");
        ((Collection)localObject1).add(((TypeParameterDescriptor)localObject2).getOriginal());
      }
      return new TypeAliasExpansion(paramTypeAliasExpansion, paramTypeAliasDescriptor, paramList, MapsKt.toMap((Iterable)CollectionsKt.zip((Iterable)localObject1, (Iterable)paramList)), null);
    }
  }
}

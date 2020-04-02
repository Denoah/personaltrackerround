package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

public final class TypeParameterUtilsKt
{
  public static final PossiblyInnerType buildPossiblyInnerType(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$buildPossiblyInnerType");
    ClassifierDescriptor localClassifierDescriptor1 = paramKotlinType.getConstructor().getDeclarationDescriptor();
    ClassifierDescriptor localClassifierDescriptor2 = localClassifierDescriptor1;
    if (!(localClassifierDescriptor1 instanceof ClassifierDescriptorWithTypeParameters)) {
      localClassifierDescriptor2 = null;
    }
    return buildPossiblyInnerType(paramKotlinType, (ClassifierDescriptorWithTypeParameters)localClassifierDescriptor2, 0);
  }
  
  private static final PossiblyInnerType buildPossiblyInnerType(KotlinType paramKotlinType, ClassifierDescriptorWithTypeParameters paramClassifierDescriptorWithTypeParameters, int paramInt)
  {
    Object localObject = null;
    if (paramClassifierDescriptorWithTypeParameters != null)
    {
      DeclarationDescriptor localDeclarationDescriptor = (DeclarationDescriptor)paramClassifierDescriptorWithTypeParameters;
      if (!ErrorUtils.isError(localDeclarationDescriptor))
      {
        int i = paramClassifierDescriptorWithTypeParameters.getDeclaredTypeParameters().size() + paramInt;
        if (!paramClassifierDescriptorWithTypeParameters.isInner())
        {
          int j;
          if ((i != paramKotlinType.getArguments().size()) && (!DescriptorUtils.isLocal(localDeclarationDescriptor))) {
            j = 0;
          } else {
            j = 1;
          }
          if ((_Assertions.ENABLED) && (j == 0))
          {
            paramClassifierDescriptorWithTypeParameters = new StringBuilder();
            paramClassifierDescriptorWithTypeParameters.append(paramKotlinType.getArguments().size() - i);
            paramClassifierDescriptorWithTypeParameters.append(" trailing arguments were found in ");
            paramClassifierDescriptorWithTypeParameters.append(paramKotlinType);
            paramClassifierDescriptorWithTypeParameters.append(" type");
            throw ((Throwable)new AssertionError(paramClassifierDescriptorWithTypeParameters.toString()));
          }
          return new PossiblyInnerType(paramClassifierDescriptorWithTypeParameters, paramKotlinType.getArguments().subList(paramInt, paramKotlinType.getArguments().size()), null);
        }
        List localList = paramKotlinType.getArguments().subList(paramInt, i);
        localDeclarationDescriptor = paramClassifierDescriptorWithTypeParameters.getContainingDeclaration();
        if ((localDeclarationDescriptor instanceof ClassifierDescriptorWithTypeParameters)) {
          localObject = localDeclarationDescriptor;
        }
        return new PossiblyInnerType(paramClassifierDescriptorWithTypeParameters, localList, buildPossiblyInnerType(paramKotlinType, (ClassifierDescriptorWithTypeParameters)localObject, i));
      }
    }
    return null;
  }
  
  private static final CapturedTypeParameterDescriptor capturedCopyForInnerDeclaration(TypeParameterDescriptor paramTypeParameterDescriptor, DeclarationDescriptor paramDeclarationDescriptor, int paramInt)
  {
    return new CapturedTypeParameterDescriptor(paramTypeParameterDescriptor, paramDeclarationDescriptor, paramInt);
  }
  
  public static final List<TypeParameterDescriptor> computeConstructorTypeParameters(ClassifierDescriptorWithTypeParameters paramClassifierDescriptorWithTypeParameters)
  {
    Intrinsics.checkParameterIsNotNull(paramClassifierDescriptorWithTypeParameters, "$this$computeConstructorTypeParameters");
    List localList1 = paramClassifierDescriptorWithTypeParameters.getDeclaredTypeParameters();
    Intrinsics.checkExpressionValueIsNotNull(localList1, "declaredTypeParameters");
    if ((!paramClassifierDescriptorWithTypeParameters.isInner()) && (!(paramClassifierDescriptorWithTypeParameters.getContainingDeclaration() instanceof CallableDescriptor))) {
      return localList1;
    }
    DeclarationDescriptor localDeclarationDescriptor = (DeclarationDescriptor)paramClassifierDescriptorWithTypeParameters;
    List localList2 = SequencesKt.toList(SequencesKt.flatMap(SequencesKt.filter(SequencesKt.takeWhile(DescriptorUtilsKt.getParents(localDeclarationDescriptor), (Function1)computeConstructorTypeParameters.parametersFromContainingFunctions.1.INSTANCE), (Function1)computeConstructorTypeParameters.parametersFromContainingFunctions.2.INSTANCE), (Function1)computeConstructorTypeParameters.parametersFromContainingFunctions.3.INSTANCE));
    Object localObject1 = DescriptorUtilsKt.getParents(localDeclarationDescriptor).iterator();
    do
    {
      boolean bool = ((Iterator)localObject1).hasNext();
      localIterator = null;
      if (!bool) {
        break;
      }
      localObject2 = ((Iterator)localObject1).next();
    } while (!(localObject2 instanceof ClassDescriptor));
    break label133;
    Object localObject2 = null;
    label133:
    localObject1 = (ClassDescriptor)localObject2;
    localObject2 = localIterator;
    if (localObject1 != null)
    {
      localObject1 = ((ClassDescriptor)localObject1).getTypeConstructor();
      localObject2 = localIterator;
      if (localObject1 != null) {
        localObject2 = ((TypeConstructor)localObject1).getParameters();
      }
    }
    if (localObject2 == null) {
      localObject2 = CollectionsKt.emptyList();
    }
    if ((localList2.isEmpty()) && (((List)localObject2).isEmpty()))
    {
      paramClassifierDescriptorWithTypeParameters = paramClassifierDescriptorWithTypeParameters.getDeclaredTypeParameters();
      Intrinsics.checkExpressionValueIsNotNull(paramClassifierDescriptorWithTypeParameters, "declaredTypeParameters");
      return paramClassifierDescriptorWithTypeParameters;
    }
    localObject2 = (Iterable)CollectionsKt.plus((Collection)localList2, (Iterable)localObject2);
    paramClassifierDescriptorWithTypeParameters = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
    Iterator localIterator = ((Iterable)localObject2).iterator();
    while (localIterator.hasNext())
    {
      localObject2 = (TypeParameterDescriptor)localIterator.next();
      Intrinsics.checkExpressionValueIsNotNull(localObject2, "it");
      paramClassifierDescriptorWithTypeParameters.add(capturedCopyForInnerDeclaration((TypeParameterDescriptor)localObject2, localDeclarationDescriptor, localList1.size()));
    }
    paramClassifierDescriptorWithTypeParameters = (List)paramClassifierDescriptorWithTypeParameters;
    return CollectionsKt.plus((Collection)localList1, (Iterable)paramClassifierDescriptorWithTypeParameters);
  }
}

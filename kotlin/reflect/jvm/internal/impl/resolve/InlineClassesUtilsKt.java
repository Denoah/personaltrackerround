package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;

public final class InlineClassesUtilsKt
{
  public static final boolean isGetterOfUnderlyingPropertyOfInlineClass(CallableDescriptor paramCallableDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramCallableDescriptor, "$this$isGetterOfUnderlyingPropertyOfInlineClass");
    if ((paramCallableDescriptor instanceof PropertyGetterDescriptor))
    {
      paramCallableDescriptor = ((PropertyGetterDescriptor)paramCallableDescriptor).getCorrespondingProperty();
      Intrinsics.checkExpressionValueIsNotNull(paramCallableDescriptor, "correspondingProperty");
      if (isUnderlyingPropertyOfInlineClass((VariableDescriptor)paramCallableDescriptor)) {
        return true;
      }
    }
    boolean bool = false;
    return bool;
  }
  
  public static final boolean isInlineClass(DeclarationDescriptor paramDeclarationDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptor, "$this$isInlineClass");
    boolean bool;
    if (((paramDeclarationDescriptor instanceof ClassDescriptor)) && (((ClassDescriptor)paramDeclarationDescriptor).isInline())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean isInlineClassType(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$isInlineClassType");
    paramKotlinType = paramKotlinType.getConstructor().getDeclarationDescriptor();
    boolean bool;
    if (paramKotlinType != null) {
      bool = isInlineClass((DeclarationDescriptor)paramKotlinType);
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean isUnderlyingPropertyOfInlineClass(VariableDescriptor paramVariableDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramVariableDescriptor, "$this$isUnderlyingPropertyOfInlineClass");
    Object localObject = paramVariableDescriptor.getContainingDeclaration();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "this.containingDeclaration");
    if (!isInlineClass((DeclarationDescriptor)localObject)) {
      return false;
    }
    if (localObject != null)
    {
      localObject = underlyingRepresentation((ClassDescriptor)localObject);
      if (localObject != null) {
        localObject = ((ValueParameterDescriptor)localObject).getName();
      } else {
        localObject = null;
      }
      return Intrinsics.areEqual(localObject, paramVariableDescriptor.getName());
    }
    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
  }
  
  public static final KotlinType substitutedUnderlyingType(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$substitutedUnderlyingType");
    ValueParameterDescriptor localValueParameterDescriptor = unsubstitutedUnderlyingParameter(paramKotlinType);
    Object localObject1 = null;
    Object localObject2 = localObject1;
    if (localValueParameterDescriptor != null)
    {
      paramKotlinType = paramKotlinType.getMemberScope();
      localObject2 = localValueParameterDescriptor.getName();
      Intrinsics.checkExpressionValueIsNotNull(localObject2, "parameter.name");
      paramKotlinType = (PropertyDescriptor)CollectionsKt.singleOrNull((Iterable)paramKotlinType.getContributedVariables((Name)localObject2, (LookupLocation)NoLookupLocation.FOR_ALREADY_TRACKED));
      localObject2 = localObject1;
      if (paramKotlinType != null) {
        localObject2 = paramKotlinType.getType();
      }
    }
    return localObject2;
  }
  
  public static final ValueParameterDescriptor underlyingRepresentation(ClassDescriptor paramClassDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "$this$underlyingRepresentation");
    boolean bool = paramClassDescriptor.isInline();
    Object localObject1 = null;
    if (!bool) {
      return null;
    }
    Object localObject2 = paramClassDescriptor.getUnsubstitutedPrimaryConstructor();
    paramClassDescriptor = localObject1;
    if (localObject2 != null)
    {
      localObject2 = ((ClassConstructorDescriptor)localObject2).getValueParameters();
      paramClassDescriptor = localObject1;
      if (localObject2 != null) {
        paramClassDescriptor = (ValueParameterDescriptor)CollectionsKt.singleOrNull((List)localObject2);
      }
    }
    return paramClassDescriptor;
  }
  
  public static final ValueParameterDescriptor unsubstitutedUnderlyingParameter(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$unsubstitutedUnderlyingParameter");
    paramKotlinType = paramKotlinType.getConstructor().getDeclarationDescriptor();
    boolean bool = paramKotlinType instanceof ClassDescriptor;
    Object localObject = null;
    if (!bool) {
      paramKotlinType = null;
    }
    ClassDescriptor localClassDescriptor = (ClassDescriptor)paramKotlinType;
    paramKotlinType = localObject;
    if (localClassDescriptor != null) {
      paramKotlinType = underlyingRepresentation(localClassDescriptor);
    }
    return paramKotlinType;
  }
}

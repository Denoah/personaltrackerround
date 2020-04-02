package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin._Assertions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;

public final class IndexedParametersSubstitution
  extends TypeSubstitution
{
  private final boolean approximateCapturedTypes;
  private final TypeProjection[] arguments;
  private final TypeParameterDescriptor[] parameters;
  
  public IndexedParametersSubstitution(List<? extends TypeParameterDescriptor> paramList, List<? extends TypeProjection> paramList1) {}
  
  public IndexedParametersSubstitution(TypeParameterDescriptor[] paramArrayOfTypeParameterDescriptor, TypeProjection[] paramArrayOfTypeProjection, boolean paramBoolean)
  {
    this.parameters = paramArrayOfTypeParameterDescriptor;
    this.arguments = paramArrayOfTypeProjection;
    this.approximateCapturedTypes = paramBoolean;
    int i;
    if (paramArrayOfTypeParameterDescriptor.length <= paramArrayOfTypeProjection.length) {
      i = 1;
    } else {
      i = 0;
    }
    if ((_Assertions.ENABLED) && (i == 0))
    {
      paramArrayOfTypeParameterDescriptor = new StringBuilder();
      paramArrayOfTypeParameterDescriptor.append("Number of arguments should not be less then number of parameters, but: parameters=");
      paramArrayOfTypeParameterDescriptor.append(this.parameters.length);
      paramArrayOfTypeParameterDescriptor.append(", args=");
      paramArrayOfTypeParameterDescriptor.append(this.arguments.length);
      throw ((Throwable)new AssertionError(paramArrayOfTypeParameterDescriptor.toString()));
    }
  }
  
  public boolean approximateContravariantCapturedTypes()
  {
    return this.approximateCapturedTypes;
  }
  
  public TypeProjection get(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "key");
    Object localObject = paramKotlinType.getConstructor().getDeclarationDescriptor();
    paramKotlinType = (KotlinType)localObject;
    if (!(localObject instanceof TypeParameterDescriptor)) {
      paramKotlinType = null;
    }
    paramKotlinType = (TypeParameterDescriptor)paramKotlinType;
    if (paramKotlinType != null)
    {
      int i = paramKotlinType.getIndex();
      localObject = this.parameters;
      if ((i < localObject.length) && (Intrinsics.areEqual(localObject[i].getTypeConstructor(), paramKotlinType.getTypeConstructor()))) {
        return this.arguments[i];
      }
    }
    return null;
  }
  
  public final TypeProjection[] getArguments()
  {
    return this.arguments;
  }
  
  public final TypeParameterDescriptor[] getParameters()
  {
    return this.parameters;
  }
  
  public boolean isEmpty()
  {
    boolean bool;
    if (this.arguments.length == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}

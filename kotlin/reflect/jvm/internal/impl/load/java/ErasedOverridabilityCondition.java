package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.Collection;
import java.util.Iterator;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor.CopyBuilder;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawSubstitution;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawTypeImpl;
import kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition;
import kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition.Contract;
import kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition.Result;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil.OverrideCompatibilityInfo;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

public final class ErasedOverridabilityCondition
  implements ExternalOverridabilityCondition
{
  public ErasedOverridabilityCondition() {}
  
  public ExternalOverridabilityCondition.Contract getContract()
  {
    return ExternalOverridabilityCondition.Contract.SUCCESS_ONLY;
  }
  
  public ExternalOverridabilityCondition.Result isOverridable(CallableDescriptor paramCallableDescriptor1, CallableDescriptor paramCallableDescriptor2, ClassDescriptor paramClassDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramCallableDescriptor1, "superDescriptor");
    Intrinsics.checkParameterIsNotNull(paramCallableDescriptor2, "subDescriptor");
    if ((paramCallableDescriptor2 instanceof JavaMethodDescriptor))
    {
      Object localObject1 = (JavaMethodDescriptor)paramCallableDescriptor2;
      paramClassDescriptor = ((JavaMethodDescriptor)localObject1).getTypeParameters();
      Intrinsics.checkExpressionValueIsNotNull(paramClassDescriptor, "subDescriptor.typeParameters");
      if (!(((Collection)paramClassDescriptor).isEmpty() ^ true))
      {
        paramClassDescriptor = OverridingUtil.getBasicOverridabilityProblem(paramCallableDescriptor1, paramCallableDescriptor2);
        Object localObject2 = null;
        if (paramClassDescriptor != null) {
          paramClassDescriptor = paramClassDescriptor.getResult();
        } else {
          paramClassDescriptor = null;
        }
        if (paramClassDescriptor != null) {
          return ExternalOverridabilityCondition.Result.UNKNOWN;
        }
        paramClassDescriptor = ((JavaMethodDescriptor)localObject1).getValueParameters();
        Intrinsics.checkExpressionValueIsNotNull(paramClassDescriptor, "subDescriptor.valueParameters");
        Sequence localSequence = SequencesKt.map(CollectionsKt.asSequence((Iterable)paramClassDescriptor), (Function1)isOverridable.signatureTypes.1.INSTANCE);
        paramClassDescriptor = ((JavaMethodDescriptor)localObject1).getReturnType();
        if (paramClassDescriptor == null) {
          Intrinsics.throwNpe();
        }
        localSequence = SequencesKt.plus(localSequence, paramClassDescriptor);
        localObject1 = ((JavaMethodDescriptor)localObject1).getExtensionReceiverParameter();
        paramClassDescriptor = (ClassDescriptor)localObject2;
        if (localObject1 != null) {
          paramClassDescriptor = ((ReceiverParameterDescriptor)localObject1).getType();
        }
        paramClassDescriptor = SequencesKt.plus(localSequence, (Iterable)CollectionsKt.listOfNotNull(paramClassDescriptor)).iterator();
        while (paramClassDescriptor.hasNext())
        {
          localObject2 = (KotlinType)paramClassDescriptor.next();
          if (((((Collection)((KotlinType)localObject2).getArguments()).isEmpty() ^ true)) && (!(((KotlinType)localObject2).unwrap() instanceof RawTypeImpl))) {
            i = 1;
          } else {
            i = 0;
          }
          if (i != 0)
          {
            i = 1;
            break label249;
          }
        }
        int i = 0;
        label249:
        if (i != 0) {
          return ExternalOverridabilityCondition.Result.UNKNOWN;
        }
        paramClassDescriptor = (CallableDescriptor)paramCallableDescriptor1.substitute(RawSubstitution.INSTANCE.buildSubstitutor());
        if (paramClassDescriptor != null)
        {
          paramCallableDescriptor1 = paramClassDescriptor;
          if ((paramClassDescriptor instanceof SimpleFunctionDescriptor))
          {
            localObject2 = (SimpleFunctionDescriptor)paramClassDescriptor;
            localObject1 = ((SimpleFunctionDescriptor)localObject2).getTypeParameters();
            Intrinsics.checkExpressionValueIsNotNull(localObject1, "erasedSuper.typeParameters");
            paramCallableDescriptor1 = paramClassDescriptor;
            if ((((Collection)localObject1).isEmpty() ^ true))
            {
              paramCallableDescriptor1 = ((SimpleFunctionDescriptor)localObject2).newCopyBuilder().setTypeParameters(CollectionsKt.emptyList()).build();
              if (paramCallableDescriptor1 == null) {
                Intrinsics.throwNpe();
              }
              paramCallableDescriptor1 = (CallableDescriptor)paramCallableDescriptor1;
            }
          }
          paramCallableDescriptor1 = OverridingUtil.DEFAULT.isOverridableByWithoutExternalConditions(paramCallableDescriptor1, paramCallableDescriptor2, false);
          Intrinsics.checkExpressionValueIsNotNull(paramCallableDescriptor1, "OverridingUtil.DEFAULT.i…er, subDescriptor, false)");
          paramCallableDescriptor1 = paramCallableDescriptor1.getResult();
          Intrinsics.checkExpressionValueIsNotNull(paramCallableDescriptor1, "OverridingUtil.DEFAULT.i…Descriptor, false).result");
          if (ErasedOverridabilityCondition.WhenMappings.$EnumSwitchMapping$0[paramCallableDescriptor1.ordinal()] != 1) {
            paramCallableDescriptor1 = ExternalOverridabilityCondition.Result.UNKNOWN;
          } else {
            paramCallableDescriptor1 = ExternalOverridabilityCondition.Result.OVERRIDABLE;
          }
          return paramCallableDescriptor1;
        }
        return ExternalOverridabilityCondition.Result.UNKNOWN;
      }
    }
    return ExternalOverridabilityCondition.Result.UNKNOWN;
  }
}

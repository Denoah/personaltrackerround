package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Class.Kind;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.MemberKind;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Modality;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type.Argument.Projection;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeParameter.Variance;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Visibility;
import kotlin.reflect.jvm.internal.impl.types.Variance;

public final class ProtoEnumFlags
{
  public static final ProtoEnumFlags INSTANCE = new ProtoEnumFlags();
  
  private ProtoEnumFlags() {}
  
  public final ClassKind classKind(ProtoBuf.Class.Kind paramKind)
  {
    if (paramKind != null) {
      switch (ProtoEnumFlags.WhenMappings.$EnumSwitchMapping$5[paramKind.ordinal()])
      {
      default: 
        break;
      case 6: 
      case 7: 
        paramKind = ClassKind.OBJECT;
        break;
      case 5: 
        paramKind = ClassKind.ANNOTATION_CLASS;
        break;
      case 4: 
        paramKind = ClassKind.ENUM_ENTRY;
        break;
      case 3: 
        paramKind = ClassKind.ENUM_CLASS;
        break;
      case 2: 
        paramKind = ClassKind.INTERFACE;
        break;
      case 1: 
        paramKind = ClassKind.CLASS;
        break;
      }
    }
    paramKind = ClassKind.CLASS;
    return paramKind;
  }
  
  public final CallableMemberDescriptor.Kind memberKind(ProtoBuf.MemberKind paramMemberKind)
  {
    if (paramMemberKind != null)
    {
      int i = ProtoEnumFlags.WhenMappings.$EnumSwitchMapping$0[paramMemberKind.ordinal()];
      if (i == 1) {
        break label64;
      }
      if (i == 2) {
        break label57;
      }
      if (i == 3) {
        break label50;
      }
      if (i == 4) {}
    }
    else
    {
      return CallableMemberDescriptor.Kind.DECLARATION;
    }
    return CallableMemberDescriptor.Kind.SYNTHESIZED;
    label50:
    return CallableMemberDescriptor.Kind.DELEGATION;
    label57:
    return CallableMemberDescriptor.Kind.FAKE_OVERRIDE;
    label64:
    paramMemberKind = CallableMemberDescriptor.Kind.DECLARATION;
    return paramMemberKind;
  }
  
  public final Modality modality(ProtoBuf.Modality paramModality)
  {
    if (paramModality != null)
    {
      int i = ProtoEnumFlags.WhenMappings.$EnumSwitchMapping$2[paramModality.ordinal()];
      if (i == 1) {
        break label64;
      }
      if (i == 2) {
        break label57;
      }
      if (i == 3) {
        break label50;
      }
      if (i == 4) {}
    }
    else
    {
      return Modality.FINAL;
    }
    return Modality.SEALED;
    label50:
    return Modality.ABSTRACT;
    label57:
    return Modality.OPEN;
    label64:
    paramModality = Modality.FINAL;
    return paramModality;
  }
  
  public final Variance variance(ProtoBuf.Type.Argument.Projection paramProjection)
  {
    Intrinsics.checkParameterIsNotNull(paramProjection, "projection");
    int i = ProtoEnumFlags.WhenMappings.$EnumSwitchMapping$8[paramProjection.ordinal()];
    if (i != 1)
    {
      if (i != 2)
      {
        if (i != 3)
        {
          if (i != 4) {
            throw new NoWhenBranchMatchedException();
          }
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Only IN, OUT and INV are supported. Actual argument: ");
          localStringBuilder.append(paramProjection);
          throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString()));
        }
        paramProjection = Variance.INVARIANT;
      }
      else
      {
        paramProjection = Variance.OUT_VARIANCE;
      }
    }
    else {
      paramProjection = Variance.IN_VARIANCE;
    }
    return paramProjection;
  }
  
  public final Variance variance(ProtoBuf.TypeParameter.Variance paramVariance)
  {
    Intrinsics.checkParameterIsNotNull(paramVariance, "variance");
    int i = ProtoEnumFlags.WhenMappings.$EnumSwitchMapping$7[paramVariance.ordinal()];
    if (i != 1)
    {
      if (i != 2)
      {
        if (i == 3) {
          paramVariance = Variance.INVARIANT;
        } else {
          throw new NoWhenBranchMatchedException();
        }
      }
      else {
        paramVariance = Variance.OUT_VARIANCE;
      }
    }
    else {
      paramVariance = Variance.IN_VARIANCE;
    }
    return paramVariance;
  }
  
  public final Visibility visibility(ProtoBuf.Visibility paramVisibility)
  {
    if (paramVisibility != null) {
      switch (ProtoEnumFlags.WhenMappings.$EnumSwitchMapping$4[paramVisibility.ordinal()])
      {
      default: 
        break;
      case 6: 
        paramVisibility = Visibilities.LOCAL;
        break;
      case 5: 
        paramVisibility = Visibilities.PUBLIC;
        break;
      case 4: 
        paramVisibility = Visibilities.PROTECTED;
        break;
      case 3: 
        paramVisibility = Visibilities.PRIVATE_TO_THIS;
        break;
      case 2: 
        paramVisibility = Visibilities.PRIVATE;
        break;
      case 1: 
        paramVisibility = Visibilities.INTERNAL;
        break;
      }
    }
    paramVisibility = Visibilities.PRIVATE;
    Intrinsics.checkExpressionValueIsNotNull(paramVisibility, "when (visibility) {\n    …isibilities.PRIVATE\n    }");
    return paramVisibility;
  }
}

package kotlin.reflect.jvm.internal;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.Variance;

@Metadata(bv={1, 0, 3}, d1={"\000X\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\b?\002\030\0002\0020\001B\007\b\002?\006\002\020\002J\020\020\005\032\0020\0062\006\020\007\032\0020\bH\002J\016\020\t\032\0020\0062\006\020\007\032\0020\nJ\016\020\013\032\0020\0062\006\020\f\032\0020\nJ\016\020\r\032\0020\0062\006\020\016\032\0020\017J\016\020\020\032\0020\0062\006\020\007\032\0020\021J\016\020\022\032\0020\0062\006\020\023\032\0020\024J\016\020\025\032\0020\0062\006\020\026\032\0020\027J\032\020\030\032\0020\031*\0060\032j\002`\0332\b\020\034\032\004\030\0010\035H\002J\030\020\036\032\0020\031*\0060\032j\002`\0332\006\020\037\032\0020\bH\002R\016\020\003\032\0020\004X?\004?\006\002\n\000?\006 "}, d2={"Lkotlin/reflect/jvm/internal/ReflectionObjectRenderer;", "", "()V", "renderer", "Lkotlin/reflect/jvm/internal/impl/renderer/DescriptorRenderer;", "renderCallable", "", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/CallableDescriptor;", "renderFunction", "Lkotlin/reflect/jvm/internal/impl/descriptors/FunctionDescriptor;", "renderLambda", "invoke", "renderParameter", "parameter", "Lkotlin/reflect/jvm/internal/KParameterImpl;", "renderProperty", "Lkotlin/reflect/jvm/internal/impl/descriptors/PropertyDescriptor;", "renderType", "type", "Lkotlin/reflect/jvm/internal/impl/types/KotlinType;", "renderTypeParameter", "typeParameter", "Lkotlin/reflect/jvm/internal/impl/descriptors/TypeParameterDescriptor;", "appendReceiverType", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "receiver", "Lkotlin/reflect/jvm/internal/impl/descriptors/ReceiverParameterDescriptor;", "appendReceivers", "callable", "kotlin-reflection"}, k=1, mv={1, 1, 15})
public final class ReflectionObjectRenderer
{
  public static final ReflectionObjectRenderer INSTANCE = new ReflectionObjectRenderer();
  private static final DescriptorRenderer renderer = DescriptorRenderer.FQ_NAMES_IN_TYPES;
  
  private ReflectionObjectRenderer() {}
  
  private final void appendReceiverType(StringBuilder paramStringBuilder, ReceiverParameterDescriptor paramReceiverParameterDescriptor)
  {
    if (paramReceiverParameterDescriptor != null)
    {
      paramReceiverParameterDescriptor = paramReceiverParameterDescriptor.getType();
      Intrinsics.checkExpressionValueIsNotNull(paramReceiverParameterDescriptor, "receiver.type");
      paramStringBuilder.append(renderType(paramReceiverParameterDescriptor));
      paramStringBuilder.append(".");
    }
  }
  
  private final void appendReceivers(StringBuilder paramStringBuilder, CallableDescriptor paramCallableDescriptor)
  {
    ReceiverParameterDescriptor localReceiverParameterDescriptor = UtilKt.getInstanceReceiverParameter(paramCallableDescriptor);
    paramCallableDescriptor = paramCallableDescriptor.getExtensionReceiverParameter();
    appendReceiverType(paramStringBuilder, localReceiverParameterDescriptor);
    int i;
    if ((localReceiverParameterDescriptor != null) && (paramCallableDescriptor != null)) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      paramStringBuilder.append("(");
    }
    appendReceiverType(paramStringBuilder, paramCallableDescriptor);
    if (i != 0) {
      paramStringBuilder.append(")");
    }
  }
  
  private final String renderCallable(CallableDescriptor paramCallableDescriptor)
  {
    if ((paramCallableDescriptor instanceof PropertyDescriptor))
    {
      paramCallableDescriptor = renderProperty((PropertyDescriptor)paramCallableDescriptor);
    }
    else
    {
      if (!(paramCallableDescriptor instanceof FunctionDescriptor)) {
        break label37;
      }
      paramCallableDescriptor = renderFunction((FunctionDescriptor)paramCallableDescriptor);
    }
    return paramCallableDescriptor;
    label37:
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Illegal callable: ");
    localStringBuilder.append(paramCallableDescriptor);
    throw ((Throwable)new IllegalStateException(localStringBuilder.toString().toString()));
  }
  
  public final String renderFunction(FunctionDescriptor paramFunctionDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramFunctionDescriptor, "descriptor");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("fun ");
    INSTANCE.appendReceivers(localStringBuilder, (CallableDescriptor)paramFunctionDescriptor);
    DescriptorRenderer localDescriptorRenderer = renderer;
    Object localObject = paramFunctionDescriptor.getName();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "descriptor.name");
    localStringBuilder.append(localDescriptorRenderer.renderName((Name)localObject, true));
    localObject = paramFunctionDescriptor.getValueParameters();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "descriptor.valueParameters");
    CollectionsKt.joinTo$default((Iterable)localObject, (Appendable)localStringBuilder, (CharSequence)", ", (CharSequence)"(", (CharSequence)")", 0, null, (Function1)renderFunction.1.1.INSTANCE, 48, null);
    localStringBuilder.append(": ");
    localObject = INSTANCE;
    paramFunctionDescriptor = paramFunctionDescriptor.getReturnType();
    if (paramFunctionDescriptor == null) {
      Intrinsics.throwNpe();
    }
    Intrinsics.checkExpressionValueIsNotNull(paramFunctionDescriptor, "descriptor.returnType!!");
    localStringBuilder.append(((ReflectionObjectRenderer)localObject).renderType(paramFunctionDescriptor));
    paramFunctionDescriptor = localStringBuilder.toString();
    Intrinsics.checkExpressionValueIsNotNull(paramFunctionDescriptor, "StringBuilder().apply(builderAction).toString()");
    return paramFunctionDescriptor;
  }
  
  public final String renderLambda(FunctionDescriptor paramFunctionDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramFunctionDescriptor, "invoke");
    StringBuilder localStringBuilder = new StringBuilder();
    INSTANCE.appendReceivers(localStringBuilder, (CallableDescriptor)paramFunctionDescriptor);
    Object localObject = paramFunctionDescriptor.getValueParameters();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "invoke.valueParameters");
    CollectionsKt.joinTo$default((Iterable)localObject, (Appendable)localStringBuilder, (CharSequence)", ", (CharSequence)"(", (CharSequence)")", 0, null, (Function1)renderLambda.1.1.INSTANCE, 48, null);
    localStringBuilder.append(" -> ");
    localObject = INSTANCE;
    paramFunctionDescriptor = paramFunctionDescriptor.getReturnType();
    if (paramFunctionDescriptor == null) {
      Intrinsics.throwNpe();
    }
    Intrinsics.checkExpressionValueIsNotNull(paramFunctionDescriptor, "invoke.returnType!!");
    localStringBuilder.append(((ReflectionObjectRenderer)localObject).renderType(paramFunctionDescriptor));
    paramFunctionDescriptor = localStringBuilder.toString();
    Intrinsics.checkExpressionValueIsNotNull(paramFunctionDescriptor, "StringBuilder().apply(builderAction).toString()");
    return paramFunctionDescriptor;
  }
  
  public final String renderParameter(KParameterImpl paramKParameterImpl)
  {
    Intrinsics.checkParameterIsNotNull(paramKParameterImpl, "parameter");
    StringBuilder localStringBuilder = new StringBuilder();
    Object localObject = paramKParameterImpl.getKind();
    int i = ReflectionObjectRenderer.WhenMappings.$EnumSwitchMapping$0[localObject.ordinal()];
    if (i != 1)
    {
      if (i != 2)
      {
        if (i == 3)
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("parameter #");
          ((StringBuilder)localObject).append(paramKParameterImpl.getIndex());
          ((StringBuilder)localObject).append(' ');
          ((StringBuilder)localObject).append(paramKParameterImpl.getName());
          localStringBuilder.append(((StringBuilder)localObject).toString());
        }
      }
      else {
        localStringBuilder.append("instance parameter");
      }
    }
    else {
      localStringBuilder.append("extension receiver parameter");
    }
    localStringBuilder.append(" of ");
    localStringBuilder.append(INSTANCE.renderCallable((CallableDescriptor)paramKParameterImpl.getCallable().getDescriptor()));
    paramKParameterImpl = localStringBuilder.toString();
    Intrinsics.checkExpressionValueIsNotNull(paramKParameterImpl, "StringBuilder().apply(builderAction).toString()");
    return paramKParameterImpl;
  }
  
  public final String renderProperty(PropertyDescriptor paramPropertyDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramPropertyDescriptor, "descriptor");
    StringBuilder localStringBuilder = new StringBuilder();
    if (paramPropertyDescriptor.isVar()) {
      localObject = "var ";
    } else {
      localObject = "val ";
    }
    localStringBuilder.append((String)localObject);
    INSTANCE.appendReceivers(localStringBuilder, (CallableDescriptor)paramPropertyDescriptor);
    DescriptorRenderer localDescriptorRenderer = renderer;
    Object localObject = paramPropertyDescriptor.getName();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "descriptor.name");
    localStringBuilder.append(localDescriptorRenderer.renderName((Name)localObject, true));
    localStringBuilder.append(": ");
    localObject = INSTANCE;
    paramPropertyDescriptor = paramPropertyDescriptor.getType();
    Intrinsics.checkExpressionValueIsNotNull(paramPropertyDescriptor, "descriptor.type");
    localStringBuilder.append(((ReflectionObjectRenderer)localObject).renderType(paramPropertyDescriptor));
    paramPropertyDescriptor = localStringBuilder.toString();
    Intrinsics.checkExpressionValueIsNotNull(paramPropertyDescriptor, "StringBuilder().apply(builderAction).toString()");
    return paramPropertyDescriptor;
  }
  
  public final String renderType(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "type");
    return renderer.renderType(paramKotlinType);
  }
  
  public final String renderTypeParameter(TypeParameterDescriptor paramTypeParameterDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeParameterDescriptor, "typeParameter");
    StringBuilder localStringBuilder = new StringBuilder();
    Variance localVariance = paramTypeParameterDescriptor.getVariance();
    int i = ReflectionObjectRenderer.WhenMappings.$EnumSwitchMapping$1[localVariance.ordinal()];
    if (i != 2)
    {
      if (i == 3) {
        localStringBuilder.append("out ");
      }
    }
    else {
      localStringBuilder.append("in ");
    }
    localStringBuilder.append(paramTypeParameterDescriptor.getName());
    paramTypeParameterDescriptor = localStringBuilder.toString();
    Intrinsics.checkExpressionValueIsNotNull(paramTypeParameterDescriptor, "StringBuilder().apply(builderAction).toString()");
    return paramTypeParameterDescriptor;
  }
}

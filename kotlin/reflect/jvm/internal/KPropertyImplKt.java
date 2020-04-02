package kotlin.reflect.jvm.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin._Assertions;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.calls.Caller;
import kotlin.reflect.jvm.internal.calls.CallerImpl;
import kotlin.reflect.jvm.internal.calls.CallerImpl.FieldGetter;
import kotlin.reflect.jvm.internal.calls.CallerImpl.FieldGetter.BoundInstance;
import kotlin.reflect.jvm.internal.calls.CallerImpl.FieldGetter.BoundJvmStaticInObject;
import kotlin.reflect.jvm.internal.calls.CallerImpl.FieldGetter.Instance;
import kotlin.reflect.jvm.internal.calls.CallerImpl.FieldGetter.JvmStaticInObject;
import kotlin.reflect.jvm.internal.calls.CallerImpl.FieldGetter.Static;
import kotlin.reflect.jvm.internal.calls.CallerImpl.FieldSetter;
import kotlin.reflect.jvm.internal.calls.CallerImpl.FieldSetter.BoundInstance;
import kotlin.reflect.jvm.internal.calls.CallerImpl.FieldSetter.BoundJvmStaticInObject;
import kotlin.reflect.jvm.internal.calls.CallerImpl.FieldSetter.Instance;
import kotlin.reflect.jvm.internal.calls.CallerImpl.FieldSetter.JvmStaticInObject;
import kotlin.reflect.jvm.internal.calls.CallerImpl.FieldSetter.Static;
import kotlin.reflect.jvm.internal.calls.CallerImpl.Method;
import kotlin.reflect.jvm.internal.calls.CallerImpl.Method.BoundInstance;
import kotlin.reflect.jvm.internal.calls.CallerImpl.Method.BoundJvmStaticInObject;
import kotlin.reflect.jvm.internal.calls.CallerImpl.Method.BoundStatic;
import kotlin.reflect.jvm.internal.calls.CallerImpl.Method.Instance;
import kotlin.reflect.jvm.internal.calls.CallerImpl.Method.JvmStaticInObject;
import kotlin.reflect.jvm.internal.calls.CallerImpl.Method.Static;
import kotlin.reflect.jvm.internal.calls.InlineClassAwareCallerKt;
import kotlin.reflect.jvm.internal.calls.InternalUnderlyingValOfInlineClass;
import kotlin.reflect.jvm.internal.calls.InternalUnderlyingValOfInlineClass.Bound;
import kotlin.reflect.jvm.internal.calls.InternalUnderlyingValOfInlineClass.Unbound;
import kotlin.reflect.jvm.internal.calls.ThrowingCaller;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf.JvmMethodSignature;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf.JvmPropertySignature;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmProtoBufUtil;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedPropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.text.Regex;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\000\n\002\020\000\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\013\n\000\n\002\030\002\n\000\032 \020\005\032\006\022\002\b\0030\006*\n\022\002\b\003\022\002\b\0030\0022\006\020\007\032\0020\bH\002\032\f\020\t\032\0020\b*\0020\nH\002\"\"\020\000\032\004\030\0010\001*\n\022\002\b\003\022\002\b\0030\0028@X?\004?\006\006\032\004\b\003\020\004?\006\013"}, d2={"boundReceiver", "", "Lkotlin/reflect/jvm/internal/KPropertyImpl$Accessor;", "getBoundReceiver", "(Lkotlin/reflect/jvm/internal/KPropertyImpl$Accessor;)Ljava/lang/Object;", "computeCallerForAccessor", "Lkotlin/reflect/jvm/internal/calls/Caller;", "isGetter", "", "isJvmFieldPropertyInCompanionObject", "Lkotlin/reflect/jvm/internal/impl/descriptors/PropertyDescriptor;", "kotlin-reflection"}, k=2, mv={1, 1, 15})
public final class KPropertyImplKt
{
  private static final Caller<?> computeCallerForAccessor(KPropertyImpl.Accessor<?, ?> paramAccessor, final boolean paramBoolean)
  {
    if (KDeclarationContainerImpl.Companion.getLOCAL_PROPERTY_SIGNATURE$kotlin_reflection().matches((CharSequence)paramAccessor.getProperty().getSignature())) {
      return (Caller)ThrowingCaller.INSTANCE;
    }
    final Lambda local1 = new Lambda(paramAccessor)
    {
      public final boolean invoke()
      {
        return this.$this_computeCallerForAccessor.getProperty().getDescriptor().getAnnotations().hasAnnotation(UtilKt.getJVM_STATIC());
      }
    };
    Object localObject1 = new Lambda(paramAccessor)
    {
      public final boolean invoke()
      {
        return TypeUtils.isNullableType(this.$this_computeCallerForAccessor.getProperty().getDescriptor().getType()) ^ true;
      }
    }
    {
      public final CallerImpl<Field> invoke(Field paramAnonymousField)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousField, "field");
        if ((!KPropertyImplKt.access$isJvmFieldPropertyInCompanionObject(this.$this_computeCallerForAccessor.getProperty().getDescriptor())) && (Modifier.isStatic(paramAnonymousField.getModifiers())))
        {
          if (local1.invoke())
          {
            if (paramBoolean)
            {
              if (this.$this_computeCallerForAccessor.isBound()) {
                paramAnonymousField = (CallerImpl.FieldGetter)new CallerImpl.FieldGetter.BoundJvmStaticInObject(paramAnonymousField);
              } else {
                paramAnonymousField = (CallerImpl.FieldGetter)new CallerImpl.FieldGetter.JvmStaticInObject(paramAnonymousField);
              }
              paramAnonymousField = (CallerImpl)paramAnonymousField;
            }
            else
            {
              if (this.$this_computeCallerForAccessor.isBound()) {
                paramAnonymousField = (CallerImpl.FieldSetter)new CallerImpl.FieldSetter.BoundJvmStaticInObject(paramAnonymousField, this.$isNotNullProperty$2.invoke());
              } else {
                paramAnonymousField = (CallerImpl.FieldSetter)new CallerImpl.FieldSetter.JvmStaticInObject(paramAnonymousField, this.$isNotNullProperty$2.invoke());
              }
              paramAnonymousField = (CallerImpl)paramAnonymousField;
            }
          }
          else if (paramBoolean) {
            paramAnonymousField = (CallerImpl)new CallerImpl.FieldGetter.Static(paramAnonymousField);
          } else {
            paramAnonymousField = (CallerImpl)new CallerImpl.FieldSetter.Static(paramAnonymousField, this.$isNotNullProperty$2.invoke());
          }
        }
        else if (paramBoolean)
        {
          if (this.$this_computeCallerForAccessor.isBound()) {
            paramAnonymousField = (CallerImpl.FieldGetter)new CallerImpl.FieldGetter.BoundInstance(paramAnonymousField, KPropertyImplKt.getBoundReceiver(this.$this_computeCallerForAccessor));
          } else {
            paramAnonymousField = (CallerImpl.FieldGetter)new CallerImpl.FieldGetter.Instance(paramAnonymousField);
          }
          paramAnonymousField = (CallerImpl)paramAnonymousField;
        }
        else
        {
          if (this.$this_computeCallerForAccessor.isBound()) {
            paramAnonymousField = (CallerImpl.FieldSetter)new CallerImpl.FieldSetter.BoundInstance(paramAnonymousField, this.$isNotNullProperty$2.invoke(), KPropertyImplKt.getBoundReceiver(this.$this_computeCallerForAccessor));
          } else {
            paramAnonymousField = (CallerImpl.FieldSetter)new CallerImpl.FieldSetter.Instance(paramAnonymousField, this.$isNotNullProperty$2.invoke());
          }
          paramAnonymousField = (CallerImpl)paramAnonymousField;
        }
        return paramAnonymousField;
      }
    };
    Object localObject2 = RuntimeTypeMapper.INSTANCE.mapPropertySignature(paramAccessor.getProperty().getDescriptor());
    if ((localObject2 instanceof JvmPropertySignature.KotlinProperty))
    {
      JvmPropertySignature.KotlinProperty localKotlinProperty = (JvmPropertySignature.KotlinProperty)localObject2;
      localObject2 = localKotlinProperty.getSignature();
      if (paramBoolean)
      {
        if (((JvmProtoBuf.JvmPropertySignature)localObject2).hasGetter())
        {
          localObject2 = ((JvmProtoBuf.JvmPropertySignature)localObject2).getGetter();
          break label137;
        }
      }
      else if (((JvmProtoBuf.JvmPropertySignature)localObject2).hasSetter())
      {
        localObject2 = ((JvmProtoBuf.JvmPropertySignature)localObject2).getSetter();
        break label137;
      }
      localObject2 = null;
      label137:
      if (localObject2 != null) {
        localObject2 = paramAccessor.getProperty().getContainer().findMethodBySignature(localKotlinProperty.getNameResolver().getString(((JvmProtoBuf.JvmMethodSignature)localObject2).getName()), localKotlinProperty.getNameResolver().getString(((JvmProtoBuf.JvmMethodSignature)localObject2).getDesc()));
      } else {
        localObject2 = null;
      }
      if (localObject2 == null)
      {
        if ((InlineClassesUtilsKt.isUnderlyingPropertyOfInlineClass((VariableDescriptor)paramAccessor.getProperty().getDescriptor())) && (Intrinsics.areEqual(paramAccessor.getProperty().getDescriptor().getVisibility(), Visibilities.INTERNAL)))
        {
          localObject2 = InlineClassAwareCallerKt.toInlineClass(paramAccessor.getProperty().getDescriptor().getContainingDeclaration());
          if (localObject2 != null)
          {
            localObject2 = InlineClassAwareCallerKt.getUnboxMethod((Class)localObject2, (CallableMemberDescriptor)paramAccessor.getProperty().getDescriptor());
            if (localObject2 != null)
            {
              if (paramAccessor.isBound()) {
                localObject2 = (InternalUnderlyingValOfInlineClass)new InternalUnderlyingValOfInlineClass.Bound((Method)localObject2, getBoundReceiver(paramAccessor));
              } else {
                localObject2 = (InternalUnderlyingValOfInlineClass)new InternalUnderlyingValOfInlineClass.Unbound((Method)localObject2);
              }
              localObject2 = (Caller)localObject2;
              break label739;
            }
          }
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("Underlying property of inline class ");
          ((StringBuilder)localObject2).append(paramAccessor.getProperty());
          ((StringBuilder)localObject2).append(" should have a field");
          throw ((Throwable)new KotlinReflectionInternalError(((StringBuilder)localObject2).toString()));
        }
        else
        {
          localObject2 = paramAccessor.getProperty().getJavaField();
          if (localObject2 != null)
          {
            localObject2 = (Caller)((computeCallerForAccessor.3)localObject1).invoke((Field)localObject2);
          }
          else
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("No accessors or field is found for property ");
            ((StringBuilder)localObject2).append(paramAccessor.getProperty());
            throw ((Throwable)new KotlinReflectionInternalError(((StringBuilder)localObject2).toString()));
          }
        }
      }
      else if (!Modifier.isStatic(((Method)localObject2).getModifiers()))
      {
        if (paramAccessor.isBound()) {
          localObject2 = (CallerImpl.Method)new CallerImpl.Method.BoundInstance((Method)localObject2, getBoundReceiver(paramAccessor));
        } else {
          localObject2 = (CallerImpl.Method)new CallerImpl.Method.Instance((Method)localObject2);
        }
        localObject2 = (Caller)localObject2;
      }
      else if (local1.invoke())
      {
        if (paramAccessor.isBound()) {
          localObject2 = (CallerImpl.Method)new CallerImpl.Method.BoundJvmStaticInObject((Method)localObject2);
        } else {
          localObject2 = (CallerImpl.Method)new CallerImpl.Method.JvmStaticInObject((Method)localObject2);
        }
        localObject2 = (Caller)localObject2;
      }
      else
      {
        if (paramAccessor.isBound()) {
          localObject2 = (CallerImpl.Method)new CallerImpl.Method.BoundStatic((Method)localObject2, getBoundReceiver(paramAccessor));
        } else {
          localObject2 = (CallerImpl.Method)new CallerImpl.Method.Static((Method)localObject2);
        }
        localObject2 = (Caller)localObject2;
      }
    }
    else if ((localObject2 instanceof JvmPropertySignature.JavaField))
    {
      localObject2 = (Caller)((computeCallerForAccessor.3)localObject1).invoke(((JvmPropertySignature.JavaField)localObject2).getField());
    }
    else
    {
      if (!(localObject2 instanceof JvmPropertySignature.JavaMethodProperty)) {
        break label795;
      }
      if (paramBoolean)
      {
        localObject2 = ((JvmPropertySignature.JavaMethodProperty)localObject2).getGetterMethod();
      }
      else
      {
        localObject1 = (JvmPropertySignature.JavaMethodProperty)localObject2;
        localObject2 = ((JvmPropertySignature.JavaMethodProperty)localObject1).getSetterMethod();
        if (localObject2 == null) {
          break label755;
        }
      }
      if (paramAccessor.isBound()) {
        localObject2 = (CallerImpl.Method)new CallerImpl.Method.BoundInstance((Method)localObject2, getBoundReceiver(paramAccessor));
      } else {
        localObject2 = (CallerImpl.Method)new CallerImpl.Method.Instance((Method)localObject2);
      }
      localObject2 = (Caller)localObject2;
    }
    label739:
    return InlineClassAwareCallerKt.createInlineClassAwareCallerIfNeeded$default((Caller)localObject2, (CallableMemberDescriptor)paramAccessor.getDescriptor(), false, 2, null);
    label755:
    paramAccessor = new StringBuilder();
    paramAccessor.append("No source found for setter of Java method property: ");
    paramAccessor.append(((JvmPropertySignature.JavaMethodProperty)localObject1).getGetterMethod());
    throw ((Throwable)new KotlinReflectionInternalError(paramAccessor.toString()));
    label795:
    if ((localObject2 instanceof JvmPropertySignature.MappedKotlinProperty))
    {
      if (paramBoolean)
      {
        localObject2 = ((JvmPropertySignature.MappedKotlinProperty)localObject2).getGetterSignature();
      }
      else
      {
        localObject2 = ((JvmPropertySignature.MappedKotlinProperty)localObject2).getSetterSignature();
        if (localObject2 == null) {
          break label1016;
        }
      }
      localObject2 = paramAccessor.getProperty().getContainer().findMethodBySignature(((JvmFunctionSignature.KotlinFunction)localObject2).getMethodName(), ((JvmFunctionSignature.KotlinFunction)localObject2).getMethodDesc());
      if (localObject2 != null)
      {
        paramBoolean = Modifier.isStatic(((Method)localObject2).getModifiers());
        if ((_Assertions.ENABLED) && (!(paramBoolean ^ true)))
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("Mapped property cannot have a static accessor: ");
          ((StringBuilder)localObject2).append(paramAccessor.getProperty());
          throw ((Throwable)new AssertionError(((StringBuilder)localObject2).toString()));
        }
        if (paramAccessor.isBound()) {
          paramAccessor = (Caller)new CallerImpl.Method.BoundInstance((Method)localObject2, getBoundReceiver(paramAccessor));
        } else {
          paramAccessor = (Caller)new CallerImpl.Method.Instance((Method)localObject2);
        }
        return paramAccessor;
      }
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("No accessor found for property ");
      ((StringBuilder)localObject2).append(paramAccessor.getProperty());
      throw ((Throwable)new KotlinReflectionInternalError(((StringBuilder)localObject2).toString()));
      label1016:
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("No setter found for property ");
      ((StringBuilder)localObject2).append(paramAccessor.getProperty());
      throw ((Throwable)new KotlinReflectionInternalError(((StringBuilder)localObject2).toString()));
    }
    throw new NoWhenBranchMatchedException();
  }
  
  public static final Object getBoundReceiver(KPropertyImpl.Accessor<?, ?> paramAccessor)
  {
    Intrinsics.checkParameterIsNotNull(paramAccessor, "$this$boundReceiver");
    return paramAccessor.getProperty().getBoundReceiver();
  }
  
  private static final boolean isJvmFieldPropertyInCompanionObject(PropertyDescriptor paramPropertyDescriptor)
  {
    DeclarationDescriptor localDeclarationDescriptor = paramPropertyDescriptor.getContainingDeclaration();
    Intrinsics.checkExpressionValueIsNotNull(localDeclarationDescriptor, "containingDeclaration");
    boolean bool1 = DescriptorUtils.isCompanionObject(localDeclarationDescriptor);
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    localDeclarationDescriptor = localDeclarationDescriptor.getContainingDeclaration();
    if ((!DescriptorUtils.isInterface(localDeclarationDescriptor)) && (!DescriptorUtils.isAnnotationClass(localDeclarationDescriptor))) {}
    do
    {
      bool1 = true;
      break;
      bool1 = bool2;
      if (!(paramPropertyDescriptor instanceof DeserializedPropertyDescriptor)) {
        break;
      }
      bool1 = bool2;
    } while (JvmProtoBufUtil.isMovedFromInterfaceCompanion(((DeserializedPropertyDescriptor)paramPropertyDescriptor).getProto()));
    return bool1;
  }
}

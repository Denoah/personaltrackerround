package kotlin.reflect.jvm.internal;

import java.lang.reflect.Method;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns.FqNames;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.CloneableClassScope;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.CloneableClassScope.Companion;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaClass;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaConstructor;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaField;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaMethod;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAbi;
import kotlin.reflect.jvm.internal.impl.load.java.SpecialBuiltinMembers;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaPropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.sources.JavaSourceElement;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Constructor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Function;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Property;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoBufUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf.JvmPropertySignature;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature.Method;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmProtoBufUtil;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.ExtendableMessage;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.GeneratedExtension;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedCallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedPropertyDescriptor;

@Metadata(bv={1, 0, 3}, d1={"\000P\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\013\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\016\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\b?\002\030\0002\0020\001B\007\b\002?\006\002\020\002J\020\020\n\032\0020\0132\006\020\f\032\0020\rH\002J\022\020\016\032\0020\0042\n\020\017\032\006\022\002\b\0030\007J\020\020\020\032\0020\0212\006\020\f\032\0020\rH\002J\020\020\022\032\0020\0232\006\020\f\032\0020\024H\002J\016\020\025\032\0020\0262\006\020\027\032\0020\030J\016\020\031\032\0020\0322\006\020\033\032\0020\rR\016\020\003\032\0020\004X?\004?\006\002\n\000R\036\020\005\032\004\030\0010\006*\006\022\002\b\0030\0078BX?\004?\006\006\032\004\b\b\020\t?\006\034"}, d2={"Lkotlin/reflect/jvm/internal/RuntimeTypeMapper;", "", "()V", "JAVA_LANG_VOID", "Lkotlin/reflect/jvm/internal/impl/name/ClassId;", "primitiveType", "Lkotlin/reflect/jvm/internal/impl/builtins/PrimitiveType;", "Ljava/lang/Class;", "getPrimitiveType", "(Ljava/lang/Class;)Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "isKnownBuiltInFunction", "", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/FunctionDescriptor;", "mapJvmClassToKotlinClassId", "klass", "mapJvmFunctionSignature", "Lkotlin/reflect/jvm/internal/JvmFunctionSignature$KotlinFunction;", "mapName", "", "Lkotlin/reflect/jvm/internal/impl/descriptors/CallableMemberDescriptor;", "mapPropertySignature", "Lkotlin/reflect/jvm/internal/JvmPropertySignature;", "possiblyOverriddenProperty", "Lkotlin/reflect/jvm/internal/impl/descriptors/PropertyDescriptor;", "mapSignature", "Lkotlin/reflect/jvm/internal/JvmFunctionSignature;", "possiblySubstitutedFunction", "kotlin-reflection"}, k=1, mv={1, 1, 15})
public final class RuntimeTypeMapper
{
  public static final RuntimeTypeMapper INSTANCE = new RuntimeTypeMapper();
  private static final ClassId JAVA_LANG_VOID;
  
  static
  {
    ClassId localClassId = ClassId.topLevel(new FqName("java.lang.Void"));
    Intrinsics.checkExpressionValueIsNotNull(localClassId, "ClassId.topLevel(FqName(\"java.lang.Void\"))");
    JAVA_LANG_VOID = localClassId;
  }
  
  private RuntimeTypeMapper() {}
  
  private final PrimitiveType getPrimitiveType(Class<?> paramClass)
  {
    if (paramClass.isPrimitive())
    {
      paramClass = JvmPrimitiveType.get(paramClass.getSimpleName());
      Intrinsics.checkExpressionValueIsNotNull(paramClass, "JvmPrimitiveType.get(simpleName)");
      paramClass = paramClass.getPrimitiveType();
    }
    else
    {
      paramClass = null;
    }
    return paramClass;
  }
  
  private final boolean isKnownBuiltInFunction(FunctionDescriptor paramFunctionDescriptor)
  {
    if ((!DescriptorFactory.isEnumValueOfMethod(paramFunctionDescriptor)) && (!DescriptorFactory.isEnumValuesMethod(paramFunctionDescriptor))) {
      return (Intrinsics.areEqual(paramFunctionDescriptor.getName(), CloneableClassScope.Companion.getCLONE_NAME())) && (paramFunctionDescriptor.getValueParameters().isEmpty());
    }
    return true;
  }
  
  private final JvmFunctionSignature.KotlinFunction mapJvmFunctionSignature(FunctionDescriptor paramFunctionDescriptor)
  {
    return new JvmFunctionSignature.KotlinFunction(new JvmMemberSignature.Method(mapName((CallableMemberDescriptor)paramFunctionDescriptor), MethodSignatureMappingKt.computeJvmDescriptor$default(paramFunctionDescriptor, false, false, 1, null)));
  }
  
  private final String mapName(CallableMemberDescriptor paramCallableMemberDescriptor)
  {
    String str = SpecialBuiltinMembers.getJvmMethodNameIfSpecial(paramCallableMemberDescriptor);
    if (str != null)
    {
      paramCallableMemberDescriptor = str;
    }
    else
    {
      if ((paramCallableMemberDescriptor instanceof PropertyGetterDescriptor)) {
        paramCallableMemberDescriptor = JvmAbi.getterName(DescriptorUtilsKt.getPropertyIfAccessor(paramCallableMemberDescriptor).getName().asString());
      }
      for (;;)
      {
        break;
        if ((paramCallableMemberDescriptor instanceof PropertySetterDescriptor)) {
          paramCallableMemberDescriptor = JvmAbi.setterName(DescriptorUtilsKt.getPropertyIfAccessor(paramCallableMemberDescriptor).getName().asString());
        } else {
          paramCallableMemberDescriptor = paramCallableMemberDescriptor.getName().asString();
        }
      }
      Intrinsics.checkExpressionValueIsNotNull(paramCallableMemberDescriptor, "when (descriptor) {\n    …name.asString()\n        }");
    }
    return paramCallableMemberDescriptor;
  }
  
  public final ClassId mapJvmClassToKotlinClassId(Class<?> paramClass)
  {
    Intrinsics.checkParameterIsNotNull(paramClass, "klass");
    if (paramClass.isArray())
    {
      paramClass = paramClass.getComponentType();
      Intrinsics.checkExpressionValueIsNotNull(paramClass, "klass.componentType");
      paramClass = getPrimitiveType(paramClass);
      if (paramClass != null) {
        return new ClassId(KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME, paramClass.getArrayTypeName());
      }
      paramClass = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.array.toSafe());
      Intrinsics.checkExpressionValueIsNotNull(paramClass, "ClassId.topLevel(KotlinB….FQ_NAMES.array.toSafe())");
      return paramClass;
    }
    if (Intrinsics.areEqual(paramClass, Void.TYPE)) {
      return JAVA_LANG_VOID;
    }
    Object localObject = getPrimitiveType(paramClass);
    if (localObject != null) {
      return new ClassId(KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME, ((PrimitiveType)localObject).getTypeName());
    }
    paramClass = ReflectClassUtilKt.getClassId(paramClass);
    if (!paramClass.isLocal())
    {
      JavaToKotlinClassMap localJavaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
      localObject = paramClass.asSingleFqName();
      Intrinsics.checkExpressionValueIsNotNull(localObject, "classId.asSingleFqName()");
      localObject = localJavaToKotlinClassMap.mapJavaToKotlin((FqName)localObject);
      if (localObject != null) {
        return localObject;
      }
    }
    return paramClass;
  }
  
  public final JvmPropertySignature mapPropertySignature(PropertyDescriptor paramPropertyDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramPropertyDescriptor, "possiblyOverriddenProperty");
    paramPropertyDescriptor = DescriptorUtils.unwrapFakeOverride((CallableMemberDescriptor)paramPropertyDescriptor);
    Intrinsics.checkExpressionValueIsNotNull(paramPropertyDescriptor, "DescriptorUtils.unwrapFa…ssiblyOverriddenProperty)");
    Object localObject1 = ((PropertyDescriptor)paramPropertyDescriptor).getOriginal();
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "DescriptorUtils.unwrapFa…rriddenProperty).original");
    boolean bool = localObject1 instanceof DeserializedPropertyDescriptor;
    paramPropertyDescriptor = null;
    Object localObject2 = null;
    Object localObject4;
    if (bool)
    {
      localObject3 = (DeserializedPropertyDescriptor)localObject1;
      localObject2 = ((DeserializedPropertyDescriptor)localObject3).getProto();
      GeneratedMessageLite.ExtendableMessage localExtendableMessage = (GeneratedMessageLite.ExtendableMessage)localObject2;
      localObject4 = JvmProtoBuf.propertySignature;
      Intrinsics.checkExpressionValueIsNotNull(localObject4, "JvmProtoBuf.propertySignature");
      localObject4 = (JvmProtoBuf.JvmPropertySignature)ProtoBufUtilKt.getExtensionOrNull(localExtendableMessage, (GeneratedMessageLite.GeneratedExtension)localObject4);
      if (localObject4 != null) {
        return (JvmPropertySignature)new JvmPropertySignature.KotlinProperty((PropertyDescriptor)localObject1, (ProtoBuf.Property)localObject2, (JvmProtoBuf.JvmPropertySignature)localObject4, ((DeserializedPropertyDescriptor)localObject3).getNameResolver(), ((DeserializedPropertyDescriptor)localObject3).getTypeTable());
      }
    }
    else if ((localObject1 instanceof JavaPropertyDescriptor))
    {
      localObject3 = ((JavaPropertyDescriptor)localObject1).getSource();
      paramPropertyDescriptor = (PropertyDescriptor)localObject3;
      if (!(localObject3 instanceof JavaSourceElement)) {
        paramPropertyDescriptor = null;
      }
      paramPropertyDescriptor = (JavaSourceElement)paramPropertyDescriptor;
      if (paramPropertyDescriptor != null) {
        paramPropertyDescriptor = paramPropertyDescriptor.getJavaElement();
      } else {
        paramPropertyDescriptor = null;
      }
      if ((paramPropertyDescriptor instanceof ReflectJavaField))
      {
        paramPropertyDescriptor = (JvmPropertySignature)new JvmPropertySignature.JavaField(((ReflectJavaField)paramPropertyDescriptor).getMember());
      }
      else
      {
        if (!(paramPropertyDescriptor instanceof ReflectJavaMethod)) {
          break label331;
        }
        localObject4 = ((ReflectJavaMethod)paramPropertyDescriptor).getMember();
        paramPropertyDescriptor = ((PropertyDescriptor)localObject1).getSetter();
        if (paramPropertyDescriptor != null) {
          paramPropertyDescriptor = paramPropertyDescriptor.getSource();
        } else {
          paramPropertyDescriptor = null;
        }
        localObject3 = paramPropertyDescriptor;
        if (!(paramPropertyDescriptor instanceof JavaSourceElement)) {
          localObject3 = null;
        }
        paramPropertyDescriptor = (JavaSourceElement)localObject3;
        if (paramPropertyDescriptor != null) {
          paramPropertyDescriptor = paramPropertyDescriptor.getJavaElement();
        } else {
          paramPropertyDescriptor = null;
        }
        localObject3 = paramPropertyDescriptor;
        if (!(paramPropertyDescriptor instanceof ReflectJavaMethod)) {
          localObject3 = null;
        }
        localObject3 = (ReflectJavaMethod)localObject3;
        paramPropertyDescriptor = (PropertyDescriptor)localObject2;
        if (localObject3 != null) {
          paramPropertyDescriptor = ((ReflectJavaMethod)localObject3).getMember();
        }
        paramPropertyDescriptor = (JvmPropertySignature)new JvmPropertySignature.JavaMethodProperty((Method)localObject4, paramPropertyDescriptor);
      }
      return paramPropertyDescriptor;
      label331:
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("Incorrect resolution sequence for Java field ");
      ((StringBuilder)localObject3).append(localObject1);
      ((StringBuilder)localObject3).append(" (source = ");
      ((StringBuilder)localObject3).append(paramPropertyDescriptor);
      ((StringBuilder)localObject3).append(')');
      throw ((Throwable)new KotlinReflectionInternalError(((StringBuilder)localObject3).toString()));
    }
    Object localObject3 = ((PropertyDescriptor)localObject1).getGetter();
    if (localObject3 == null) {
      Intrinsics.throwNpe();
    }
    localObject3 = (FunctionDescriptor)localObject3;
    localObject2 = (RuntimeTypeMapper)this;
    localObject3 = ((RuntimeTypeMapper)localObject2).mapJvmFunctionSignature((FunctionDescriptor)localObject3);
    localObject1 = ((PropertyDescriptor)localObject1).getSetter();
    if (localObject1 != null) {
      paramPropertyDescriptor = ((RuntimeTypeMapper)localObject2).mapJvmFunctionSignature((FunctionDescriptor)localObject1);
    }
    return (JvmPropertySignature)new JvmPropertySignature.MappedKotlinProperty((JvmFunctionSignature.KotlinFunction)localObject3, paramPropertyDescriptor);
  }
  
  public final JvmFunctionSignature mapSignature(FunctionDescriptor paramFunctionDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramFunctionDescriptor, "possiblySubstitutedFunction");
    Object localObject1 = DescriptorUtils.unwrapFakeOverride((CallableMemberDescriptor)paramFunctionDescriptor);
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "DescriptorUtils.unwrapFa…siblySubstitutedFunction)");
    FunctionDescriptor localFunctionDescriptor = ((FunctionDescriptor)localObject1).getOriginal();
    Intrinsics.checkExpressionValueIsNotNull(localFunctionDescriptor, "DescriptorUtils.unwrapFa…titutedFunction).original");
    if ((localFunctionDescriptor instanceof DeserializedCallableMemberDescriptor))
    {
      localObject1 = (DeserializedCallableMemberDescriptor)localFunctionDescriptor;
      localObject2 = ((DeserializedCallableMemberDescriptor)localObject1).getProto();
      if ((localObject2 instanceof ProtoBuf.Function))
      {
        JvmMemberSignature.Method localMethod = JvmProtoBufUtil.INSTANCE.getJvmMethodSignature((ProtoBuf.Function)localObject2, ((DeserializedCallableMemberDescriptor)localObject1).getNameResolver(), ((DeserializedCallableMemberDescriptor)localObject1).getTypeTable());
        if (localMethod != null) {
          return (JvmFunctionSignature)new JvmFunctionSignature.KotlinFunction(localMethod);
        }
      }
      if ((localObject2 instanceof ProtoBuf.Constructor))
      {
        localObject1 = JvmProtoBufUtil.INSTANCE.getJvmConstructorSignature((ProtoBuf.Constructor)localObject2, ((DeserializedCallableMemberDescriptor)localObject1).getNameResolver(), ((DeserializedCallableMemberDescriptor)localObject1).getTypeTable());
        if (localObject1 != null)
        {
          paramFunctionDescriptor = paramFunctionDescriptor.getContainingDeclaration();
          Intrinsics.checkExpressionValueIsNotNull(paramFunctionDescriptor, "possiblySubstitutedFunction.containingDeclaration");
          if (InlineClassesUtilsKt.isInlineClass(paramFunctionDescriptor)) {
            paramFunctionDescriptor = (JvmFunctionSignature)new JvmFunctionSignature.KotlinFunction((JvmMemberSignature.Method)localObject1);
          } else {
            paramFunctionDescriptor = (JvmFunctionSignature)new JvmFunctionSignature.KotlinConstructor((JvmMemberSignature.Method)localObject1);
          }
          return paramFunctionDescriptor;
        }
      }
      return (JvmFunctionSignature)mapJvmFunctionSignature(localFunctionDescriptor);
    }
    boolean bool = localFunctionDescriptor instanceof JavaMethodDescriptor;
    Object localObject2 = null;
    localObject1 = null;
    if (bool)
    {
      localObject2 = ((JavaMethodDescriptor)localFunctionDescriptor).getSource();
      paramFunctionDescriptor = (FunctionDescriptor)localObject2;
      if (!(localObject2 instanceof JavaSourceElement)) {
        paramFunctionDescriptor = null;
      }
      paramFunctionDescriptor = (JavaSourceElement)paramFunctionDescriptor;
      if (paramFunctionDescriptor != null) {
        paramFunctionDescriptor = paramFunctionDescriptor.getJavaElement();
      } else {
        paramFunctionDescriptor = null;
      }
      if (!(paramFunctionDescriptor instanceof ReflectJavaMethod)) {
        paramFunctionDescriptor = (FunctionDescriptor)localObject1;
      }
      paramFunctionDescriptor = (ReflectJavaMethod)paramFunctionDescriptor;
      if (paramFunctionDescriptor != null)
      {
        paramFunctionDescriptor = paramFunctionDescriptor.getMember();
        if (paramFunctionDescriptor != null) {
          return (JvmFunctionSignature)new JvmFunctionSignature.JavaMethod(paramFunctionDescriptor);
        }
      }
      paramFunctionDescriptor = new StringBuilder();
      paramFunctionDescriptor.append("Incorrect resolution sequence for Java method ");
      paramFunctionDescriptor.append(localFunctionDescriptor);
      throw ((Throwable)new KotlinReflectionInternalError(paramFunctionDescriptor.toString()));
    }
    if ((localFunctionDescriptor instanceof JavaClassConstructorDescriptor))
    {
      localObject1 = ((JavaClassConstructorDescriptor)localFunctionDescriptor).getSource();
      paramFunctionDescriptor = (FunctionDescriptor)localObject1;
      if (!(localObject1 instanceof JavaSourceElement)) {
        paramFunctionDescriptor = null;
      }
      localObject1 = (JavaSourceElement)paramFunctionDescriptor;
      paramFunctionDescriptor = (FunctionDescriptor)localObject2;
      if (localObject1 != null) {
        paramFunctionDescriptor = ((JavaSourceElement)localObject1).getJavaElement();
      }
      if ((paramFunctionDescriptor instanceof ReflectJavaConstructor))
      {
        paramFunctionDescriptor = (JvmFunctionSignature)new JvmFunctionSignature.JavaConstructor(((ReflectJavaConstructor)paramFunctionDescriptor).getMember());
      }
      else
      {
        if (!(paramFunctionDescriptor instanceof ReflectJavaClass)) {
          break label452;
        }
        localObject1 = (ReflectJavaClass)paramFunctionDescriptor;
        if (!((ReflectJavaClass)localObject1).isAnnotationType()) {
          break label452;
        }
        paramFunctionDescriptor = (JvmFunctionSignature)new JvmFunctionSignature.FakeJavaAnnotationConstructor(((ReflectJavaClass)localObject1).getElement());
      }
      return paramFunctionDescriptor;
      label452:
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Incorrect resolution sequence for Java constructor ");
      ((StringBuilder)localObject1).append(localFunctionDescriptor);
      ((StringBuilder)localObject1).append(" (");
      ((StringBuilder)localObject1).append(paramFunctionDescriptor);
      ((StringBuilder)localObject1).append(')');
      throw ((Throwable)new KotlinReflectionInternalError(((StringBuilder)localObject1).toString()));
    }
    if (isKnownBuiltInFunction(localFunctionDescriptor)) {
      return (JvmFunctionSignature)mapJvmFunctionSignature(localFunctionDescriptor);
    }
    paramFunctionDescriptor = new StringBuilder();
    paramFunctionDescriptor.append("Unknown origin of ");
    paramFunctionDescriptor.append(localFunctionDescriptor);
    paramFunctionDescriptor.append(" (");
    paramFunctionDescriptor.append(localFunctionDescriptor.getClass());
    paramFunctionDescriptor.append(')');
    throw ((Throwable)new KotlinReflectionInternalError(paramFunctionDescriptor.toString()));
  }
}

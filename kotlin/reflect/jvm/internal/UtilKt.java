package kotlin.reflect.jvm.internal;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference;
import kotlin.reflect.KVisibility;
import kotlin.reflect.jvm.internal.calls.AnnotationConstructorCallerKt;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectAnnotationSource;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectJavaClassFinderKt;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectKotlinClass;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.RuntimeModuleData;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.RuntimeSourceElementFactory.RuntimeSourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaAnnotation;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaClass;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinarySourceElement;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Function;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Property;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable.Companion;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.resolve.constants.AnnotationValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ArrayValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue<*>;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ErrorValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue.Value;
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue.Value.LocalClass;
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue.Value.NormalClass;
import kotlin.reflect.jvm.internal.impl.resolve.constants.NullValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationComponents;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationContext;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.text.StringsKt;

@Metadata(bv={1, 0, 3}, d1={"\000?\001\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\002\b\005\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\016\n\002\b\002\n\002\020\b\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020 \n\002\020\033\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\032n\020\t\032\004\030\001H\n\"\b\b\000\020\013*\0020\f\"\b\b\001\020\n*\0020\0062\n\020\r\032\006\022\002\b\0030\0162\006\020\017\032\002H\0132\006\020\020\032\0020\0212\006\020\022\032\0020\0232\006\020\024\032\0020\0252\035\020\026\032\031\022\004\022\0020\030\022\004\022\002H\013\022\004\022\002H\n0\027?\006\002\b\031H\000?\006\002\020\032\032.\020\033\032\b\022\002\b\003\030\0010\0162\006\020\034\032\0020\0352\006\020\036\032\0020\0372\006\020 \032\0020\0372\006\020!\032\0020\"H\002\032(\020\033\032\b\022\002\b\003\030\0010\0162\006\020\034\032\0020\0352\006\020#\032\0020$2\b\b\002\020!\032\0020\"H\002\032\"\020%\032\002H&\"\004\b\000\020&2\f\020'\032\b\022\004\022\002H&0(H?\b?\006\002\020)\032\024\020*\032\b\022\002\b\003\030\0010+*\004\030\0010,H\000\032\020\020-\032\004\030\0010.*\004\030\0010,H\000\032\024\020/\032\b\022\002\b\003\030\00100*\004\030\0010,H\000\032\022\0201\032\b\022\004\022\0020302*\00204H\000\032\016\0205\032\004\030\00103*\00206H\002\032\022\0207\032\b\022\002\b\003\030\0010\016*\00208H\000\032\016\0209\032\004\030\0010:*\0020;H\000\032\032\020<\032\004\030\0010,*\006\022\002\b\0030=2\006\020\034\032\0020\035H\002\"\024\020\000\032\0020\001X?\004?\006\b\n\000\032\004\b\002\020\003\"\032\020\004\032\004\030\0010\005*\0020\0068@X?\004?\006\006\032\004\b\007\020\b?\006>"}, d2={"JVM_STATIC", "Lkotlin/reflect/jvm/internal/impl/name/FqName;", "getJVM_STATIC", "()Lorg/jetbrains/kotlin/name/FqName;", "instanceReceiverParameter", "Lkotlin/reflect/jvm/internal/impl/descriptors/ReceiverParameterDescriptor;", "Lkotlin/reflect/jvm/internal/impl/descriptors/CallableDescriptor;", "getInstanceReceiverParameter", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "deserializeToDescriptor", "D", "M", "Lkotlin/reflect/jvm/internal/impl/protobuf/MessageLite;", "moduleAnchor", "Ljava/lang/Class;", "proto", "nameResolver", "Lkotlin/reflect/jvm/internal/impl/metadata/deserialization/NameResolver;", "typeTable", "Lkotlin/reflect/jvm/internal/impl/metadata/deserialization/TypeTable;", "metadataVersion", "Lkotlin/reflect/jvm/internal/impl/metadata/deserialization/BinaryVersion;", "createDescriptor", "Lkotlin/Function2;", "Lkotlin/reflect/jvm/internal/impl/serialization/deserialization/MemberDeserializer;", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/Class;Lorg/jetbrains/kotlin/protobuf/MessageLite;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "loadClass", "classLoader", "Ljava/lang/ClassLoader;", "packageName", "", "className", "arrayDimensions", "", "kotlinClassId", "Lkotlin/reflect/jvm/internal/impl/name/ClassId;", "reflectionCall", "R", "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "asKCallableImpl", "Lkotlin/reflect/jvm/internal/KCallableImpl;", "", "asKFunctionImpl", "Lkotlin/reflect/jvm/internal/KFunctionImpl;", "asKPropertyImpl", "Lkotlin/reflect/jvm/internal/KPropertyImpl;", "computeAnnotations", "", "", "Lkotlin/reflect/jvm/internal/impl/descriptors/annotations/Annotated;", "toAnnotationInstance", "Lkotlin/reflect/jvm/internal/impl/descriptors/annotations/AnnotationDescriptor;", "toJavaClass", "Lkotlin/reflect/jvm/internal/impl/descriptors/ClassDescriptor;", "toKVisibility", "Lkotlin/reflect/KVisibility;", "Lkotlin/reflect/jvm/internal/impl/descriptors/Visibility;", "toRuntimeValue", "Lkotlin/reflect/jvm/internal/impl/resolve/constants/ConstantValue;", "kotlin-reflection"}, k=2, mv={1, 1, 15})
public final class UtilKt
{
  private static final FqName JVM_STATIC = new FqName("kotlin.jvm.JvmStatic");
  
  public static final KCallableImpl<?> asKCallableImpl(Object paramObject)
  {
    if (!(paramObject instanceof KCallableImpl)) {
      localObject = null;
    } else {
      localObject = paramObject;
    }
    Object localObject = (KCallableImpl)localObject;
    if (localObject == null) {
      localObject = (KCallableImpl)asKFunctionImpl(paramObject);
    }
    if (localObject == null) {
      localObject = (KCallableImpl)asKPropertyImpl(paramObject);
    }
    return localObject;
  }
  
  public static final KFunctionImpl asKFunctionImpl(Object paramObject)
  {
    boolean bool = paramObject instanceof KFunctionImpl;
    Object localObject1 = null;
    if (!bool) {
      localObject2 = null;
    } else {
      localObject2 = paramObject;
    }
    Object localObject2 = (KFunctionImpl)localObject2;
    if (localObject2 != null)
    {
      paramObject = localObject2;
    }
    else
    {
      localObject2 = paramObject;
      if (!(paramObject instanceof FunctionReference)) {
        localObject2 = null;
      }
      paramObject = (FunctionReference)localObject2;
      if (paramObject != null) {
        paramObject = paramObject.compute();
      } else {
        paramObject = null;
      }
      if (!(paramObject instanceof KFunctionImpl)) {
        paramObject = localObject1;
      }
      paramObject = (KFunctionImpl)paramObject;
    }
    return paramObject;
  }
  
  public static final KPropertyImpl<?> asKPropertyImpl(Object paramObject)
  {
    boolean bool = paramObject instanceof KPropertyImpl;
    Object localObject1 = null;
    if (!bool) {
      localObject2 = null;
    } else {
      localObject2 = paramObject;
    }
    Object localObject2 = (KPropertyImpl)localObject2;
    if (localObject2 != null)
    {
      paramObject = localObject2;
    }
    else
    {
      localObject2 = paramObject;
      if (!(paramObject instanceof PropertyReference)) {
        localObject2 = null;
      }
      paramObject = (PropertyReference)localObject2;
      if (paramObject != null) {
        paramObject = paramObject.compute();
      } else {
        paramObject = null;
      }
      if (!(paramObject instanceof KPropertyImpl)) {
        paramObject = localObject1;
      }
      paramObject = (KPropertyImpl)paramObject;
    }
    return paramObject;
  }
  
  public static final List<Annotation> computeAnnotations(Annotated paramAnnotated)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotated, "$this$computeAnnotations");
    paramAnnotated = (Iterable)paramAnnotated.getAnnotations();
    Collection localCollection = (Collection)new ArrayList();
    Iterator localIterator = paramAnnotated.iterator();
    while (localIterator.hasNext())
    {
      Object localObject1 = (AnnotationDescriptor)localIterator.next();
      paramAnnotated = ((AnnotationDescriptor)localObject1).getSource();
      boolean bool = paramAnnotated instanceof ReflectAnnotationSource;
      Object localObject2 = null;
      if (bool)
      {
        paramAnnotated = ((ReflectAnnotationSource)paramAnnotated).getAnnotation();
      }
      else if ((paramAnnotated instanceof RuntimeSourceElementFactory.RuntimeSourceElement))
      {
        localObject1 = ((RuntimeSourceElementFactory.RuntimeSourceElement)paramAnnotated).getJavaElement();
        paramAnnotated = (Annotated)localObject1;
        if (!(localObject1 instanceof ReflectJavaAnnotation)) {
          paramAnnotated = null;
        }
        localObject1 = (ReflectJavaAnnotation)paramAnnotated;
        paramAnnotated = localObject2;
        if (localObject1 != null) {
          paramAnnotated = ((ReflectJavaAnnotation)localObject1).getAnnotation();
        }
      }
      else
      {
        paramAnnotated = toAnnotationInstance((AnnotationDescriptor)localObject1);
      }
      if (paramAnnotated != null) {
        localCollection.add(paramAnnotated);
      }
    }
    return (List)localCollection;
  }
  
  public static final <M extends MessageLite, D extends CallableDescriptor> D deserializeToDescriptor(Class<?> paramClass, M paramM, NameResolver paramNameResolver, TypeTable paramTypeTable, BinaryVersion paramBinaryVersion, Function2<? super MemberDeserializer, ? super M, ? extends D> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramClass, "moduleAnchor");
    Intrinsics.checkParameterIsNotNull(paramM, "proto");
    Intrinsics.checkParameterIsNotNull(paramNameResolver, "nameResolver");
    Intrinsics.checkParameterIsNotNull(paramTypeTable, "typeTable");
    Intrinsics.checkParameterIsNotNull(paramBinaryVersion, "metadataVersion");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "createDescriptor");
    Object localObject = ModuleByClassLoaderKt.getOrCreateModule(paramClass);
    if ((paramM instanceof ProtoBuf.Function)) {}
    for (paramClass = ((ProtoBuf.Function)paramM).getTypeParameterList();; paramClass = ((ProtoBuf.Property)paramM).getTypeParameterList())
    {
      break;
      if (!(paramM instanceof ProtoBuf.Property)) {
        break label150;
      }
    }
    DeserializationComponents localDeserializationComponents = ((RuntimeModuleData)localObject).getDeserialization();
    localObject = (DeclarationDescriptor)((RuntimeModuleData)localObject).getModule();
    VersionRequirementTable localVersionRequirementTable = VersionRequirementTable.Companion.getEMPTY();
    Intrinsics.checkExpressionValueIsNotNull(paramClass, "typeParameters");
    return (CallableDescriptor)paramFunction2.invoke(new MemberDeserializer(new DeserializationContext(localDeserializationComponents, paramNameResolver, (DeclarationDescriptor)localObject, paramTypeTable, localVersionRequirementTable, paramBinaryVersion, null, null, paramClass)), paramM);
    label150:
    paramClass = new StringBuilder();
    paramClass.append("Unsupported message: ");
    paramClass.append(paramM);
    throw ((Throwable)new IllegalStateException(paramClass.toString().toString()));
  }
  
  public static final ReceiverParameterDescriptor getInstanceReceiverParameter(CallableDescriptor paramCallableDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramCallableDescriptor, "$this$instanceReceiverParameter");
    if (paramCallableDescriptor.getDispatchReceiverParameter() != null)
    {
      paramCallableDescriptor = paramCallableDescriptor.getContainingDeclaration();
      if (paramCallableDescriptor != null) {
        paramCallableDescriptor = ((ClassDescriptor)paramCallableDescriptor).getThisAsReceiverParameter();
      } else {
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
      }
    }
    else
    {
      paramCallableDescriptor = null;
    }
    return paramCallableDescriptor;
  }
  
  public static final FqName getJVM_STATIC()
  {
    return JVM_STATIC;
  }
  
  private static final Class<?> loadClass(ClassLoader paramClassLoader, String paramString1, String paramString2, int paramInt)
  {
    if (Intrinsics.areEqual(paramString1, "kotlin")) {
      switch (paramString2.hashCode())
      {
      default: 
        break;
      case 2104330525: 
        if (paramString2.equals("LongArray")) {
          return [J.class;
        }
        break;
      case 948852093: 
        if (paramString2.equals("FloatArray")) {
          return [F.class;
        }
        break;
      case 601811914: 
        if (paramString2.equals("IntArray")) {
          return [I.class;
        }
        break;
      case 63537721: 
        if (paramString2.equals("Array")) {
          return [Ljava.lang.Object.class;
        }
        break;
      case 22374632: 
        if (paramString2.equals("DoubleArray")) {
          return [D.class;
        }
        break;
      case -74930671: 
        if (paramString2.equals("ByteArray")) {
          return [B.class;
        }
        break;
      case -755911549: 
        if (paramString2.equals("CharArray")) {
          return [C.class;
        }
        break;
      case -763279523: 
        if (paramString2.equals("ShortArray")) {
          return [S.class;
        }
        break;
      case -901856463: 
        if (paramString2.equals("BooleanArray")) {
          return [Z.class;
        }
        break;
      }
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString1);
    localStringBuilder.append('.');
    localStringBuilder.append(StringsKt.replace$default(paramString2, '.', '$', false, 4, null));
    paramString2 = localStringBuilder.toString();
    paramString1 = paramString2;
    if (paramInt > 0)
    {
      paramString1 = new StringBuilder();
      paramString1.append(StringsKt.repeat((CharSequence)"[", paramInt));
      paramString1.append('L');
      paramString1.append(paramString2);
      paramString1.append(';');
      paramString1 = paramString1.toString();
    }
    return ReflectJavaClassFinderKt.tryLoadClass(paramClassLoader, paramString1);
  }
  
  private static final Class<?> loadClass(ClassLoader paramClassLoader, ClassId paramClassId, int paramInt)
  {
    Object localObject = JavaToKotlinClassMap.INSTANCE;
    FqNameUnsafe localFqNameUnsafe = paramClassId.asSingleFqName().toUnsafe();
    Intrinsics.checkExpressionValueIsNotNull(localFqNameUnsafe, "kotlinClassId.asSingleFqName().toUnsafe()");
    localObject = ((JavaToKotlinClassMap)localObject).mapKotlinToJava(localFqNameUnsafe);
    if (localObject != null) {
      paramClassId = (ClassId)localObject;
    }
    localObject = paramClassId.getPackageFqName().asString();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "javaClassId.packageFqName.asString()");
    paramClassId = paramClassId.getRelativeClassName().asString();
    Intrinsics.checkExpressionValueIsNotNull(paramClassId, "javaClassId.relativeClassName.asString()");
    return loadClass(paramClassLoader, (String)localObject, paramClassId, paramInt);
  }
  
  private static final Annotation toAnnotationInstance(AnnotationDescriptor paramAnnotationDescriptor)
  {
    Object localObject1 = DescriptorUtilsKt.getAnnotationClass(paramAnnotationDescriptor);
    if (localObject1 != null) {
      localObject1 = toJavaClass((ClassDescriptor)localObject1);
    } else {
      localObject1 = null;
    }
    Object localObject2 = localObject1;
    if (!(localObject1 instanceof Class)) {
      localObject2 = null;
    }
    if (localObject2 != null)
    {
      paramAnnotationDescriptor = (Iterable)paramAnnotationDescriptor.getAllValueArguments().entrySet();
      localObject1 = (Collection)new ArrayList();
      Iterator localIterator = paramAnnotationDescriptor.iterator();
      while (localIterator.hasNext())
      {
        Object localObject3 = (Map.Entry)localIterator.next();
        paramAnnotationDescriptor = (Name)((Map.Entry)localObject3).getKey();
        localObject3 = (ConstantValue)((Map.Entry)localObject3).getValue();
        ClassLoader localClassLoader = localObject2.getClassLoader();
        Intrinsics.checkExpressionValueIsNotNull(localClassLoader, "annotationClass.classLoader");
        localObject3 = toRuntimeValue((ConstantValue)localObject3, localClassLoader);
        if (localObject3 != null) {
          paramAnnotationDescriptor = TuplesKt.to(paramAnnotationDescriptor.asString(), localObject3);
        } else {
          paramAnnotationDescriptor = null;
        }
        if (paramAnnotationDescriptor != null) {
          ((Collection)localObject1).add(paramAnnotationDescriptor);
        }
      }
      return (Annotation)AnnotationConstructorCallerKt.createAnnotationInstance$default(localObject2, MapsKt.toMap((Iterable)localObject1), null, 4, null);
    }
    return null;
  }
  
  public static final Class<?> toJavaClass(ClassDescriptor paramClassDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "$this$toJavaClass");
    Object localObject = paramClassDescriptor.getSource();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "source");
    if ((localObject instanceof KotlinJvmBinarySourceElement))
    {
      paramClassDescriptor = ((KotlinJvmBinarySourceElement)localObject).getBinaryClass();
      if (paramClassDescriptor != null) {
        paramClassDescriptor = ((ReflectKotlinClass)paramClassDescriptor).getKlass();
      } else {
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.runtime.components.ReflectKotlinClass");
      }
    }
    else if ((localObject instanceof RuntimeSourceElementFactory.RuntimeSourceElement))
    {
      paramClassDescriptor = ((RuntimeSourceElementFactory.RuntimeSourceElement)localObject).getJavaElement();
      if (paramClassDescriptor != null) {
        paramClassDescriptor = ((ReflectJavaClass)paramClassDescriptor).getElement();
      } else {
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.runtime.structure.ReflectJavaClass");
      }
    }
    else
    {
      localObject = DescriptorUtilsKt.getClassId((ClassifierDescriptor)paramClassDescriptor);
      if (localObject == null) {
        break label130;
      }
      paramClassDescriptor = loadClass(ReflectClassUtilKt.getSafeClassLoader(paramClassDescriptor.getClass()), (ClassId)localObject, 0);
    }
    return paramClassDescriptor;
    label130:
    return null;
  }
  
  public static final KVisibility toKVisibility(Visibility paramVisibility)
  {
    Intrinsics.checkParameterIsNotNull(paramVisibility, "$this$toKVisibility");
    if (Intrinsics.areEqual(paramVisibility, Visibilities.PUBLIC)) {
      paramVisibility = KVisibility.PUBLIC;
    } else if (Intrinsics.areEqual(paramVisibility, Visibilities.PROTECTED)) {
      paramVisibility = KVisibility.PROTECTED;
    } else if (Intrinsics.areEqual(paramVisibility, Visibilities.INTERNAL)) {
      paramVisibility = KVisibility.INTERNAL;
    } else if ((Intrinsics.areEqual(paramVisibility, Visibilities.PRIVATE)) || (Intrinsics.areEqual(paramVisibility, Visibilities.PRIVATE_TO_THIS))) {
      paramVisibility = KVisibility.PRIVATE;
    } else {
      paramVisibility = null;
    }
    return paramVisibility;
  }
  
  private static final Object toRuntimeValue(ConstantValue<?> paramConstantValue, ClassLoader paramClassLoader)
  {
    boolean bool = paramConstantValue instanceof AnnotationValue;
    Object localObject1 = null;
    if (bool)
    {
      paramConstantValue = toAnnotationInstance((AnnotationDescriptor)((AnnotationValue)paramConstantValue).getValue());
    }
    else if ((paramConstantValue instanceof ArrayValue))
    {
      localObject1 = (Iterable)((ArrayValue)paramConstantValue).getValue();
      paramConstantValue = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject1, 10));
      localObject1 = ((Iterable)localObject1).iterator();
      while (((Iterator)localObject1).hasNext()) {
        paramConstantValue.add(toRuntimeValue((ConstantValue)((Iterator)localObject1).next(), paramClassLoader));
      }
      paramConstantValue = ((Collection)paramConstantValue).toArray(new Object[0]);
      if (paramConstantValue == null) {
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      }
    }
    else if ((paramConstantValue instanceof EnumValue))
    {
      Object localObject2 = (Pair)((EnumValue)paramConstantValue).getValue();
      paramConstantValue = (ClassId)((Pair)localObject2).component1();
      localObject2 = (Name)((Pair)localObject2).component2();
      paramClassLoader = loadClass$default(paramClassLoader, paramConstantValue, 0, 4, null);
      paramConstantValue = (ConstantValue<?>)localObject1;
      if (paramClassLoader != null) {
        if (paramClassLoader != null) {
          paramConstantValue = Util.getEnumConstantByName(paramClassLoader, ((Name)localObject2).asString());
        } else {
          throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<out kotlin.Enum<*>>");
        }
      }
    }
    else if ((paramConstantValue instanceof KClassValue))
    {
      paramConstantValue = (KClassValue.Value)((KClassValue)paramConstantValue).getValue();
      if ((paramConstantValue instanceof KClassValue.Value.NormalClass))
      {
        paramConstantValue = (KClassValue.Value.NormalClass)paramConstantValue;
        paramConstantValue = loadClass(paramClassLoader, paramConstantValue.getClassId(), paramConstantValue.getArrayDimensions());
      }
      else if ((paramConstantValue instanceof KClassValue.Value.LocalClass))
      {
        paramClassLoader = ((KClassValue.Value.LocalClass)paramConstantValue).getType().getConstructor().getDeclarationDescriptor();
        paramConstantValue = paramClassLoader;
        if (!(paramClassLoader instanceof ClassDescriptor)) {
          paramConstantValue = null;
        }
        paramClassLoader = (ClassDescriptor)paramConstantValue;
        paramConstantValue = (ConstantValue<?>)localObject1;
        if (paramClassLoader != null) {
          paramConstantValue = toJavaClass(paramClassLoader);
        }
      }
      else
      {
        throw new NoWhenBranchMatchedException();
      }
    }
    else if ((paramConstantValue instanceof ErrorValue))
    {
      paramConstantValue = (ConstantValue<?>)localObject1;
    }
    else if ((paramConstantValue instanceof NullValue))
    {
      paramConstantValue = (ConstantValue<?>)localObject1;
    }
    else
    {
      paramConstantValue = paramConstantValue.getValue();
    }
    return paramConstantValue;
  }
}

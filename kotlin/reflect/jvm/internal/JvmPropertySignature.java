package kotlin.reflect.jvm.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAbi;
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmPackagePartSource;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Property;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoBufUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf.JvmMethodSignature;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf.JvmPropertySignature;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature.Field;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmProtoBufUtil;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.name.NameUtils;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.ExtendableMessage;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.GeneratedExtension;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedPropertyDescriptor;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\002\b\004\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\000\b0\030\0002\0020\001:\004\005\006\007\bB\007\b\002?\006\002\020\002J\b\020\003\032\0020\004H&?\001\004\t\n\013\f?\006\r"}, d2={"Lkotlin/reflect/jvm/internal/JvmPropertySignature;", "", "()V", "asString", "", "JavaField", "JavaMethodProperty", "KotlinProperty", "MappedKotlinProperty", "Lkotlin/reflect/jvm/internal/JvmPropertySignature$KotlinProperty;", "Lkotlin/reflect/jvm/internal/JvmPropertySignature$JavaMethodProperty;", "Lkotlin/reflect/jvm/internal/JvmPropertySignature$JavaField;", "Lkotlin/reflect/jvm/internal/JvmPropertySignature$MappedKotlinProperty;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
public abstract class JvmPropertySignature
{
  private JvmPropertySignature() {}
  
  public abstract String asString();
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\020\016\n\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\b\020\007\032\0020\bH\026R\021\020\002\032\0020\003?\006\b\n\000\032\004\b\005\020\006?\006\t"}, d2={"Lkotlin/reflect/jvm/internal/JvmPropertySignature$JavaField;", "Lkotlin/reflect/jvm/internal/JvmPropertySignature;", "field", "Ljava/lang/reflect/Field;", "(Ljava/lang/reflect/Field;)V", "getField", "()Ljava/lang/reflect/Field;", "asString", "", "kotlin-reflection"}, k=1, mv={1, 1, 15})
  public static final class JavaField
    extends JvmPropertySignature
  {
    private final Field field;
    
    public JavaField(Field paramField)
    {
      super();
      this.field = paramField;
    }
    
    public String asString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(JvmAbi.getterName(this.field.getName()));
      localStringBuilder.append("()");
      Class localClass = this.field.getType();
      Intrinsics.checkExpressionValueIsNotNull(localClass, "field.type");
      localStringBuilder.append(ReflectClassUtilKt.getDesc(localClass));
      return localStringBuilder.toString();
    }
    
    public final Field getField()
    {
      return this.field;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\006\n\002\020\016\n\000\030\0002\0020\001B\027\022\006\020\002\032\0020\003\022\b\020\004\032\004\030\0010\003?\006\002\020\005J\b\020\t\032\0020\nH\026R\021\020\002\032\0020\003?\006\b\n\000\032\004\b\006\020\007R\023\020\004\032\004\030\0010\003?\006\b\n\000\032\004\b\b\020\007?\006\013"}, d2={"Lkotlin/reflect/jvm/internal/JvmPropertySignature$JavaMethodProperty;", "Lkotlin/reflect/jvm/internal/JvmPropertySignature;", "getterMethod", "Ljava/lang/reflect/Method;", "setterMethod", "(Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)V", "getGetterMethod", "()Ljava/lang/reflect/Method;", "getSetterMethod", "asString", "", "kotlin-reflection"}, k=1, mv={1, 1, 15})
  public static final class JavaMethodProperty
    extends JvmPropertySignature
  {
    private final Method getterMethod;
    private final Method setterMethod;
    
    public JavaMethodProperty(Method paramMethod1, Method paramMethod2)
    {
      super();
      this.getterMethod = paramMethod1;
      this.setterMethod = paramMethod2;
    }
    
    public String asString()
    {
      return RuntimeTypeMapperKt.access$getSignature$p(this.getterMethod);
    }
    
    public final Method getGetterMethod()
    {
      return this.getterMethod;
    }
    
    public final Method getSetterMethod()
    {
      return this.setterMethod;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\n\n\002\020\016\n\002\b\005\030\0002\0020\001B-\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007\022\006\020\b\032\0020\t\022\006\020\n\032\0020\013?\006\002\020\fJ\b\020\031\032\0020\026H\026J\b\020\032\032\0020\026H\002R\021\020\002\032\0020\003?\006\b\n\000\032\004\b\r\020\016R\021\020\b\032\0020\t?\006\b\n\000\032\004\b\017\020\020R\021\020\004\032\0020\005?\006\b\n\000\032\004\b\021\020\022R\021\020\006\032\0020\007?\006\b\n\000\032\004\b\023\020\024R\016\020\025\032\0020\026X?\004?\006\002\n\000R\021\020\n\032\0020\013?\006\b\n\000\032\004\b\027\020\030?\006\033"}, d2={"Lkotlin/reflect/jvm/internal/JvmPropertySignature$KotlinProperty;", "Lkotlin/reflect/jvm/internal/JvmPropertySignature;", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/PropertyDescriptor;", "proto", "Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property;", "signature", "Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature;", "nameResolver", "Lkotlin/reflect/jvm/internal/impl/metadata/deserialization/NameResolver;", "typeTable", "Lkotlin/reflect/jvm/internal/impl/metadata/deserialization/TypeTable;", "(Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;Lorg/jetbrains/kotlin/metadata/jvm/JvmProtoBuf$JvmPropertySignature;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getNameResolver", "()Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "getProto", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "getSignature", "()Lorg/jetbrains/kotlin/metadata/jvm/JvmProtoBuf$JvmPropertySignature;", "string", "", "getTypeTable", "()Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "asString", "getManglingSuffix", "kotlin-reflection"}, k=1, mv={1, 1, 15})
  public static final class KotlinProperty
    extends JvmPropertySignature
  {
    private final PropertyDescriptor descriptor;
    private final NameResolver nameResolver;
    private final ProtoBuf.Property proto;
    private final JvmProtoBuf.JvmPropertySignature signature;
    private final String string;
    private final TypeTable typeTable;
    
    public KotlinProperty(PropertyDescriptor paramPropertyDescriptor, ProtoBuf.Property paramProperty, JvmProtoBuf.JvmPropertySignature paramJvmPropertySignature, NameResolver paramNameResolver, TypeTable paramTypeTable)
    {
      super();
      this.descriptor = paramPropertyDescriptor;
      this.proto = paramProperty;
      this.signature = paramJvmPropertySignature;
      this.nameResolver = paramNameResolver;
      this.typeTable = paramTypeTable;
      if (paramJvmPropertySignature.hasGetter())
      {
        paramPropertyDescriptor = new StringBuilder();
        paramJvmPropertySignature = this.nameResolver;
        paramProperty = this.signature.getGetter();
        Intrinsics.checkExpressionValueIsNotNull(paramProperty, "signature.getter");
        paramPropertyDescriptor.append(paramJvmPropertySignature.getString(paramProperty.getName()));
        paramJvmPropertySignature = this.nameResolver;
        paramProperty = this.signature.getGetter();
        Intrinsics.checkExpressionValueIsNotNull(paramProperty, "signature.getter");
        paramPropertyDescriptor.append(paramJvmPropertySignature.getString(paramProperty.getDesc()));
        paramPropertyDescriptor = paramPropertyDescriptor.toString();
      }
      else
      {
        paramProperty = JvmProtoBufUtil.getJvmFieldSignature$default(JvmProtoBufUtil.INSTANCE, this.proto, this.nameResolver, this.typeTable, false, 8, null);
        if (paramProperty == null) {
          break label242;
        }
        paramPropertyDescriptor = paramProperty.component1();
        paramProperty = paramProperty.component2();
        paramJvmPropertySignature = new StringBuilder();
        paramJvmPropertySignature.append(JvmAbi.getterName(paramPropertyDescriptor));
        paramJvmPropertySignature.append(getManglingSuffix());
        paramJvmPropertySignature.append("()");
        paramJvmPropertySignature.append(paramProperty);
        paramPropertyDescriptor = paramJvmPropertySignature.toString();
      }
      this.string = paramPropertyDescriptor;
      return;
      label242:
      paramPropertyDescriptor = new StringBuilder();
      paramPropertyDescriptor.append("No field signature for property: ");
      paramPropertyDescriptor.append(this.descriptor);
      throw ((Throwable)new KotlinReflectionInternalError(paramPropertyDescriptor.toString()));
    }
    
    private final String getManglingSuffix()
    {
      Object localObject1 = this.descriptor.getContainingDeclaration();
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "descriptor.containingDeclaration");
      Object localObject2;
      if ((Intrinsics.areEqual(this.descriptor.getVisibility(), Visibilities.INTERNAL)) && ((localObject1 instanceof DeserializedClassDescriptor)))
      {
        localObject2 = (GeneratedMessageLite.ExtendableMessage)((DeserializedClassDescriptor)localObject1).getClassProto();
        localObject1 = JvmProtoBuf.classModuleName;
        Intrinsics.checkExpressionValueIsNotNull(localObject1, "JvmProtoBuf.classModuleName");
        localObject1 = (Integer)ProtoBufUtilKt.getExtensionOrNull((GeneratedMessageLite.ExtendableMessage)localObject2, (GeneratedMessageLite.GeneratedExtension)localObject1);
        if (localObject1 != null)
        {
          localObject1 = this.nameResolver.getString(((Number)localObject1).intValue());
          if (localObject1 != null) {}
        }
        else
        {
          localObject1 = "main";
        }
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("$");
        ((StringBuilder)localObject2).append(NameUtils.sanitizeAsJavaIdentifier((String)localObject1));
        return ((StringBuilder)localObject2).toString();
      }
      if ((Intrinsics.areEqual(this.descriptor.getVisibility(), Visibilities.PRIVATE)) && ((localObject1 instanceof PackageFragmentDescriptor)))
      {
        localObject1 = this.descriptor;
        if (localObject1 != null)
        {
          localObject1 = ((DeserializedPropertyDescriptor)localObject1).getContainerSource();
          if ((localObject1 instanceof JvmPackagePartSource))
          {
            localObject1 = (JvmPackagePartSource)localObject1;
            if (((JvmPackagePartSource)localObject1).getFacadeClassName() != null)
            {
              localObject2 = new StringBuilder();
              ((StringBuilder)localObject2).append("$");
              ((StringBuilder)localObject2).append(((JvmPackagePartSource)localObject1).getSimpleName().asString());
              return ((StringBuilder)localObject2).toString();
            }
          }
        }
        else
        {
          throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedPropertyDescriptor");
        }
      }
      return "";
    }
    
    public String asString()
    {
      return this.string;
    }
    
    public final PropertyDescriptor getDescriptor()
    {
      return this.descriptor;
    }
    
    public final NameResolver getNameResolver()
    {
      return this.nameResolver;
    }
    
    public final ProtoBuf.Property getProto()
    {
      return this.proto;
    }
    
    public final JvmProtoBuf.JvmPropertySignature getSignature()
    {
      return this.signature;
    }
    
    public final TypeTable getTypeTable()
    {
      return this.typeTable;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\006\n\002\020\016\n\000\030\0002\0020\001B\027\022\006\020\002\032\0020\003\022\b\020\004\032\004\030\0010\003?\006\002\020\005J\b\020\t\032\0020\nH\026R\021\020\002\032\0020\003?\006\b\n\000\032\004\b\006\020\007R\023\020\004\032\004\030\0010\003?\006\b\n\000\032\004\b\b\020\007?\006\013"}, d2={"Lkotlin/reflect/jvm/internal/JvmPropertySignature$MappedKotlinProperty;", "Lkotlin/reflect/jvm/internal/JvmPropertySignature;", "getterSignature", "Lkotlin/reflect/jvm/internal/JvmFunctionSignature$KotlinFunction;", "setterSignature", "(Lkotlin/reflect/jvm/internal/JvmFunctionSignature$KotlinFunction;Lkotlin/reflect/jvm/internal/JvmFunctionSignature$KotlinFunction;)V", "getGetterSignature", "()Lkotlin/reflect/jvm/internal/JvmFunctionSignature$KotlinFunction;", "getSetterSignature", "asString", "", "kotlin-reflection"}, k=1, mv={1, 1, 15})
  public static final class MappedKotlinProperty
    extends JvmPropertySignature
  {
    private final JvmFunctionSignature.KotlinFunction getterSignature;
    private final JvmFunctionSignature.KotlinFunction setterSignature;
    
    public MappedKotlinProperty(JvmFunctionSignature.KotlinFunction paramKotlinFunction1, JvmFunctionSignature.KotlinFunction paramKotlinFunction2)
    {
      super();
      this.getterSignature = paramKotlinFunction1;
      this.setterSignature = paramKotlinFunction2;
    }
    
    public String asString()
    {
      return this.getterSignature.asString();
    }
    
    public final JvmFunctionSignature.KotlinFunction getGetterSignature()
    {
      return this.getterSignature;
    }
    
    public final JvmFunctionSignature.KotlinFunction getSetterSignature()
    {
      return this.setterSignature;
    }
  }
}

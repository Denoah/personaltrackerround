package kotlin.reflect.jvm.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature.Method;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\002\b\005\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\000\b0\030\0002\0020\001:\005\005\006\007\b\tB\007\b\002?\006\002\020\002J\b\020\003\032\0020\004H&?\001\005\n\013\f\r\016?\006\017"}, d2={"Lkotlin/reflect/jvm/internal/JvmFunctionSignature;", "", "()V", "asString", "", "FakeJavaAnnotationConstructor", "JavaConstructor", "JavaMethod", "KotlinConstructor", "KotlinFunction", "Lkotlin/reflect/jvm/internal/JvmFunctionSignature$KotlinFunction;", "Lkotlin/reflect/jvm/internal/JvmFunctionSignature$KotlinConstructor;", "Lkotlin/reflect/jvm/internal/JvmFunctionSignature$JavaMethod;", "Lkotlin/reflect/jvm/internal/JvmFunctionSignature$JavaConstructor;", "Lkotlin/reflect/jvm/internal/JvmFunctionSignature$FakeJavaAnnotationConstructor;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
public abstract class JvmFunctionSignature
{
  private JvmFunctionSignature() {}
  
  public abstract String asString();
  
  @Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\020 \n\002\030\002\n\002\b\004\n\002\020\016\n\000\030\0002\0020\001B\021\022\n\020\002\032\006\022\002\b\0030\003?\006\002\020\004J\b\020\r\032\0020\016H\026R\025\020\002\032\006\022\002\b\0030\003?\006\b\n\000\032\004\b\005\020\006R\037\020\007\032\020\022\f\022\n \n*\004\030\0010\t0\t0\b?\006\b\n\000\032\004\b\013\020\f?\006\017"}, d2={"Lkotlin/reflect/jvm/internal/JvmFunctionSignature$FakeJavaAnnotationConstructor;", "Lkotlin/reflect/jvm/internal/JvmFunctionSignature;", "jClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)V", "getJClass", "()Ljava/lang/Class;", "methods", "", "Ljava/lang/reflect/Method;", "kotlin.jvm.PlatformType", "getMethods", "()Ljava/util/List;", "asString", "", "kotlin-reflection"}, k=1, mv={1, 1, 15})
  public static final class FakeJavaAnnotationConstructor
    extends JvmFunctionSignature
  {
    private final Class<?> jClass;
    private final List<Method> methods;
    
    public FakeJavaAnnotationConstructor(Class<?> paramClass)
    {
      super();
      this.jClass = paramClass;
      paramClass = paramClass.getDeclaredMethods();
      Intrinsics.checkExpressionValueIsNotNull(paramClass, "jClass.declaredMethods");
      this.methods = ArraysKt.sortedWith(paramClass, (Comparator)new Comparator()
      {
        public final int compare(T paramAnonymousT1, T paramAnonymousT2)
        {
          paramAnonymousT1 = (Method)paramAnonymousT1;
          Intrinsics.checkExpressionValueIsNotNull(paramAnonymousT1, "it");
          paramAnonymousT1 = (Comparable)paramAnonymousT1.getName();
          paramAnonymousT2 = (Method)paramAnonymousT2;
          Intrinsics.checkExpressionValueIsNotNull(paramAnonymousT2, "it");
          return ComparisonsKt.compareValues(paramAnonymousT1, (Comparable)paramAnonymousT2.getName());
        }
      });
    }
    
    public String asString()
    {
      return CollectionsKt.joinToString$default((Iterable)this.methods, (CharSequence)"", (CharSequence)"<init>(", (CharSequence)")V", 0, null, (Function1)asString.1.INSTANCE, 24, null);
    }
    
    public final List<Method> getMethods()
    {
      return this.methods;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\020\016\n\000\030\0002\0020\001B\021\022\n\020\002\032\006\022\002\b\0030\003?\006\002\020\004J\b\020\007\032\0020\bH\026R\025\020\002\032\006\022\002\b\0030\003?\006\b\n\000\032\004\b\005\020\006?\006\t"}, d2={"Lkotlin/reflect/jvm/internal/JvmFunctionSignature$JavaConstructor;", "Lkotlin/reflect/jvm/internal/JvmFunctionSignature;", "constructor", "Ljava/lang/reflect/Constructor;", "(Ljava/lang/reflect/Constructor;)V", "getConstructor", "()Ljava/lang/reflect/Constructor;", "asString", "", "kotlin-reflection"}, k=1, mv={1, 1, 15})
  public static final class JavaConstructor
    extends JvmFunctionSignature
  {
    private final Constructor<?> constructor;
    
    public JavaConstructor(Constructor<?> paramConstructor)
    {
      super();
      this.constructor = paramConstructor;
    }
    
    public String asString()
    {
      Class[] arrayOfClass = this.constructor.getParameterTypes();
      Intrinsics.checkExpressionValueIsNotNull(arrayOfClass, "constructor.parameterTypes");
      return ArraysKt.joinToString$default(arrayOfClass, (CharSequence)"", (CharSequence)"<init>(", (CharSequence)")V", 0, null, (Function1)asString.1.INSTANCE, 24, null);
    }
    
    public final Constructor<?> getConstructor()
    {
      return this.constructor;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\020\016\n\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\b\020\007\032\0020\bH\026R\021\020\002\032\0020\003?\006\b\n\000\032\004\b\005\020\006?\006\t"}, d2={"Lkotlin/reflect/jvm/internal/JvmFunctionSignature$JavaMethod;", "Lkotlin/reflect/jvm/internal/JvmFunctionSignature;", "method", "Ljava/lang/reflect/Method;", "(Ljava/lang/reflect/Method;)V", "getMethod", "()Ljava/lang/reflect/Method;", "asString", "", "kotlin-reflection"}, k=1, mv={1, 1, 15})
  public static final class JavaMethod
    extends JvmFunctionSignature
  {
    private final Method method;
    
    public JavaMethod(Method paramMethod)
    {
      super();
      this.method = paramMethod;
    }
    
    public String asString()
    {
      return RuntimeTypeMapperKt.access$getSignature$p(this.method);
    }
    
    public final Method getMethod()
    {
      return this.method;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\016\n\002\b\007\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\b\020\f\032\0020\006H\026R\016\020\005\032\0020\006X?\004?\006\002\n\000R\021\020\007\032\0020\0068F?\006\006\032\004\b\b\020\tR\021\020\002\032\0020\003?\006\b\n\000\032\004\b\n\020\013?\006\r"}, d2={"Lkotlin/reflect/jvm/internal/JvmFunctionSignature$KotlinConstructor;", "Lkotlin/reflect/jvm/internal/JvmFunctionSignature;", "signature", "Lkotlin/reflect/jvm/internal/impl/metadata/jvm/deserialization/JvmMemberSignature$Method;", "(Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMemberSignature$Method;)V", "_signature", "", "constructorDesc", "getConstructorDesc", "()Ljava/lang/String;", "getSignature", "()Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMemberSignature$Method;", "asString", "kotlin-reflection"}, k=1, mv={1, 1, 15})
  public static final class KotlinConstructor
    extends JvmFunctionSignature
  {
    private final String _signature;
    private final JvmMemberSignature.Method signature;
    
    public KotlinConstructor(JvmMemberSignature.Method paramMethod)
    {
      super();
      this.signature = paramMethod;
      this._signature = paramMethod.asString();
    }
    
    public String asString()
    {
      return this._signature;
    }
    
    public final String getConstructorDesc()
    {
      return this.signature.getDesc();
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\016\n\002\b\t\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\b\020\016\032\0020\006H\026R\016\020\005\032\0020\006X?\004?\006\002\n\000R\021\020\007\032\0020\0068F?\006\006\032\004\b\b\020\tR\021\020\n\032\0020\0068F?\006\006\032\004\b\013\020\tR\021\020\002\032\0020\003?\006\b\n\000\032\004\b\f\020\r?\006\017"}, d2={"Lkotlin/reflect/jvm/internal/JvmFunctionSignature$KotlinFunction;", "Lkotlin/reflect/jvm/internal/JvmFunctionSignature;", "signature", "Lkotlin/reflect/jvm/internal/impl/metadata/jvm/deserialization/JvmMemberSignature$Method;", "(Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMemberSignature$Method;)V", "_signature", "", "methodDesc", "getMethodDesc", "()Ljava/lang/String;", "methodName", "getMethodName", "getSignature", "()Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMemberSignature$Method;", "asString", "kotlin-reflection"}, k=1, mv={1, 1, 15})
  public static final class KotlinFunction
    extends JvmFunctionSignature
  {
    private final String _signature;
    private final JvmMemberSignature.Method signature;
    
    public KotlinFunction(JvmMemberSignature.Method paramMethod)
    {
      super();
      this.signature = paramMethod;
      this._signature = paramMethod.asString();
    }
    
    public String asString()
    {
      return this._signature;
    }
    
    public final String getMethodDesc()
    {
      return this.signature.getDesc();
    }
    
    public final String getMethodName()
    {
      return this.signature.getName();
    }
  }
}

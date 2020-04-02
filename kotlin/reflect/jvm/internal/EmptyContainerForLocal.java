package kotlin.reflect.jvm.internal;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KCallable;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.name.Name;

@Metadata(bv={1, 0, 3}, d1={"\000H\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\036\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\020\001\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\002\b?\002\030\0002\0020\001B\007\b\002?\006\002\020\002J\b\020\017\032\0020\020H\002J\026\020\021\032\b\022\004\022\0020\0220\0042\006\020\023\032\0020\024H\026J\022\020\025\032\004\030\0010\0262\006\020\027\032\0020\030H\026J\026\020\031\032\b\022\004\022\0020\0260\0042\006\020\023\032\0020\024H\026R\032\020\003\032\b\022\004\022\0020\0050\0048VX?\004?\006\006\032\004\b\006\020\007R\030\020\b\032\006\022\002\b\0030\t8VX?\004?\006\006\032\004\b\n\020\013R\036\020\f\032\f\022\b\022\006\022\002\b\0030\r0\0048VX?\004?\006\006\032\004\b\016\020\007?\006\032"}, d2={"Lkotlin/reflect/jvm/internal/EmptyContainerForLocal;", "Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;", "()V", "constructorDescriptors", "", "Lkotlin/reflect/jvm/internal/impl/descriptors/ConstructorDescriptor;", "getConstructorDescriptors", "()Ljava/util/Collection;", "jClass", "Ljava/lang/Class;", "getJClass", "()Ljava/lang/Class;", "members", "Lkotlin/reflect/KCallable;", "getMembers", "fail", "", "getFunctions", "Lkotlin/reflect/jvm/internal/impl/descriptors/FunctionDescriptor;", "name", "Lkotlin/reflect/jvm/internal/impl/name/Name;", "getLocalProperty", "Lkotlin/reflect/jvm/internal/impl/descriptors/PropertyDescriptor;", "index", "", "getProperties", "kotlin-reflection"}, k=1, mv={1, 1, 15})
public final class EmptyContainerForLocal
  extends KDeclarationContainerImpl
{
  public static final EmptyContainerForLocal INSTANCE = new EmptyContainerForLocal();
  
  private EmptyContainerForLocal() {}
  
  private final Void fail()
  {
    throw ((Throwable)new KotlinReflectionInternalError("Introspecting local functions, lambdas, anonymous functions and local variables is not yet fully supported in Kotlin reflection"));
  }
  
  public Collection<ConstructorDescriptor> getConstructorDescriptors()
  {
    fail();
    throw null;
  }
  
  public Collection<FunctionDescriptor> getFunctions(Name paramName)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    fail();
    throw null;
  }
  
  public Class<?> getJClass()
  {
    fail();
    throw null;
  }
  
  public PropertyDescriptor getLocalProperty(int paramInt)
  {
    return null;
  }
  
  public Collection<KCallable<?>> getMembers()
  {
    fail();
    throw null;
  }
  
  public Collection<PropertyDescriptor> getProperties(Name paramName)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    fail();
    throw null;
  }
}

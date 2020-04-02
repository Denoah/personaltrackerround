package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMethod;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public abstract class LazyJavaStaticScope
  extends LazyJavaScope
{
  public LazyJavaStaticScope(LazyJavaResolverContext paramLazyJavaResolverContext)
  {
    super(paramLazyJavaResolverContext, null, 2, null);
  }
  
  protected void computeNonDeclaredProperties(Name paramName, Collection<PropertyDescriptor> paramCollection)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    Intrinsics.checkParameterIsNotNull(paramCollection, "result");
  }
  
  protected ReceiverParameterDescriptor getDispatchReceiverParameter()
  {
    return null;
  }
  
  protected LazyJavaScope.MethodSignatureData resolveMethodSignature(JavaMethod paramJavaMethod, List<? extends TypeParameterDescriptor> paramList, KotlinType paramKotlinType, List<? extends ValueParameterDescriptor> paramList1)
  {
    Intrinsics.checkParameterIsNotNull(paramJavaMethod, "method");
    Intrinsics.checkParameterIsNotNull(paramList, "methodTypeParameters");
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "returnType");
    Intrinsics.checkParameterIsNotNull(paramList1, "valueParameters");
    return new LazyJavaScope.MethodSignatureData(paramKotlinType, null, paramList1, paramList, false, CollectionsKt.emptyList());
  }
}

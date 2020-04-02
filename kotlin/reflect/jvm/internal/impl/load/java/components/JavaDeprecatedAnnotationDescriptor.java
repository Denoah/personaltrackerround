package kotlin.reflect.jvm.internal.impl.load.java.components;

import java.util.Map;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotation;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;

public final class JavaDeprecatedAnnotationDescriptor
  extends JavaAnnotationDescriptor
{
  private final NotNullLazyValue allValueArguments$delegate;
  
  public JavaDeprecatedAnnotationDescriptor(JavaAnnotation paramJavaAnnotation, LazyJavaResolverContext paramLazyJavaResolverContext)
  {
    super(paramLazyJavaResolverContext, paramJavaAnnotation, localFqName);
    this.allValueArguments$delegate = paramLazyJavaResolverContext.getStorageManager().createLazyValue((Function0)allValueArguments.2.INSTANCE);
  }
  
  public Map<Name, ConstantValue<?>> getAllValueArguments()
  {
    return (Map)StorageKt.getValue(this.allValueArguments$delegate, this, $$delegatedProperties[0]);
  }
}

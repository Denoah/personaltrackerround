package kotlin.reflect.jvm.internal.impl.load.java.components;

import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.PossiblyExternalAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.JavaResolverComponents;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.sources.JavaSourceElementFactory;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotation;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaElement;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;

public class JavaAnnotationDescriptor
  implements AnnotationDescriptor, PossiblyExternalAnnotationDescriptor
{
  private final JavaAnnotationArgument firstArgument;
  private final FqName fqName;
  private final boolean isIdeExternalAnnotation;
  private final SourceElement source;
  private final NotNullLazyValue type$delegate;
  
  public JavaAnnotationDescriptor(final LazyJavaResolverContext paramLazyJavaResolverContext, JavaAnnotation paramJavaAnnotation, FqName paramFqName)
  {
    this.fqName = paramFqName;
    if (paramJavaAnnotation != null)
    {
      paramFqName = paramLazyJavaResolverContext.getComponents().getSourceElementFactory().source((JavaElement)paramJavaAnnotation);
      if (paramFqName != null)
      {
        paramFqName = (SourceElement)paramFqName;
        break label64;
      }
    }
    paramFqName = SourceElement.NO_SOURCE;
    Intrinsics.checkExpressionValueIsNotNull(paramFqName, "SourceElement.NO_SOURCE");
    label64:
    this.source = paramFqName;
    this.type$delegate = paramLazyJavaResolverContext.getStorageManager().createLazyValue((Function0)new Lambda(paramLazyJavaResolverContext)
    {
      public final SimpleType invoke()
      {
        ClassDescriptor localClassDescriptor = paramLazyJavaResolverContext.getModule().getBuiltIns().getBuiltInClassByFqName(this.this$0.getFqName());
        Intrinsics.checkExpressionValueIsNotNull(localClassDescriptor, "c.module.builtIns.getBuiltInClassByFqName(fqName)");
        return localClassDescriptor.getDefaultType();
      }
    });
    if (paramJavaAnnotation != null)
    {
      paramLazyJavaResolverContext = paramJavaAnnotation.getArguments();
      if (paramLazyJavaResolverContext != null)
      {
        paramLazyJavaResolverContext = (JavaAnnotationArgument)CollectionsKt.firstOrNull((Iterable)paramLazyJavaResolverContext);
        break label125;
      }
    }
    paramLazyJavaResolverContext = null;
    label125:
    this.firstArgument = paramLazyJavaResolverContext;
    boolean bool = true;
    if ((paramJavaAnnotation == null) || (paramJavaAnnotation.isIdeExternalAnnotation() != true)) {
      bool = false;
    }
    this.isIdeExternalAnnotation = bool;
  }
  
  public Map<Name, ConstantValue<?>> getAllValueArguments()
  {
    return MapsKt.emptyMap();
  }
  
  protected final JavaAnnotationArgument getFirstArgument()
  {
    return this.firstArgument;
  }
  
  public FqName getFqName()
  {
    return this.fqName;
  }
  
  public SourceElement getSource()
  {
    return this.source;
  }
  
  public SimpleType getType()
  {
    return (SimpleType)StorageKt.getValue(this.type$delegate, this, $$delegatedProperties[0]);
  }
  
  public boolean isIdeExternalAnnotation()
  {
    return this.isIdeExternalAnnotation;
  }
}

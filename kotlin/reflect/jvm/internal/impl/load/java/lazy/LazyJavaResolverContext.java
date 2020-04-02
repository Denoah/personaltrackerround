package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import kotlin.Lazy;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolver;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;

public final class LazyJavaResolverContext
{
  private final JavaResolverComponents components;
  private final Lazy defaultTypeQualifiers$delegate;
  private final Lazy<JavaTypeQualifiersByElementType> delegateForDefaultTypeQualifiers;
  private final TypeParameterResolver typeParameterResolver;
  private final JavaTypeResolver typeResolver;
  
  public LazyJavaResolverContext(JavaResolverComponents paramJavaResolverComponents, TypeParameterResolver paramTypeParameterResolver, Lazy<JavaTypeQualifiersByElementType> paramLazy)
  {
    this.components = paramJavaResolverComponents;
    this.typeParameterResolver = paramTypeParameterResolver;
    this.delegateForDefaultTypeQualifiers = paramLazy;
    this.defaultTypeQualifiers$delegate = paramLazy;
    this.typeResolver = new JavaTypeResolver(this, paramTypeParameterResolver);
  }
  
  public final JavaResolverComponents getComponents()
  {
    return this.components;
  }
  
  public final JavaTypeQualifiersByElementType getDefaultTypeQualifiers()
  {
    Lazy localLazy = this.defaultTypeQualifiers$delegate;
    KProperty localKProperty = $$delegatedProperties[0];
    return (JavaTypeQualifiersByElementType)localLazy.getValue();
  }
  
  public final Lazy<JavaTypeQualifiersByElementType> getDelegateForDefaultTypeQualifiers$descriptors_jvm()
  {
    return this.delegateForDefaultTypeQualifiers;
  }
  
  public final ModuleDescriptor getModule()
  {
    return this.components.getModule();
  }
  
  public final StorageManager getStorageManager()
  {
    return this.components.getStorageManager();
  }
  
  public final TypeParameterResolver getTypeParameterResolver()
  {
    return this.typeParameterResolver;
  }
  
  public final JavaTypeResolver getTypeResolver()
  {
    return this.typeResolver;
  }
}

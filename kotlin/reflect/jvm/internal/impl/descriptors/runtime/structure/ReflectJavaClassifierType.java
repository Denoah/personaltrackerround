package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotation;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifier;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifierType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaType;
import kotlin.reflect.jvm.internal.impl.name.FqName;

public final class ReflectJavaClassifierType
  extends ReflectJavaType
  implements JavaClassifierType
{
  private final JavaClassifier classifier;
  private final Type reflectType;
  
  public ReflectJavaClassifierType(Type paramType)
  {
    this.reflectType = paramType;
    paramType = ((ReflectJavaClassifierType)this).getReflectType();
    if ((paramType instanceof Class))
    {
      paramType = (JavaClassifier)new ReflectJavaClass((Class)paramType);
    }
    else if ((paramType instanceof TypeVariable))
    {
      paramType = (JavaClassifier)new ReflectJavaTypeParameter((TypeVariable)paramType);
    }
    else
    {
      if (!(paramType instanceof ParameterizedType)) {
        break label125;
      }
      paramType = ((ParameterizedType)paramType).getRawType();
      if (paramType == null) {
        break label115;
      }
      paramType = (JavaClassifier)new ReflectJavaClass((Class)paramType);
    }
    this.classifier = paramType;
    return;
    label115:
    throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<*>");
    label125:
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Not a classifier type (");
    localStringBuilder.append(paramType.getClass());
    localStringBuilder.append("): ");
    localStringBuilder.append(paramType);
    throw ((Throwable)new IllegalStateException(localStringBuilder.toString()));
  }
  
  public JavaAnnotation findAnnotation(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    return null;
  }
  
  public Collection<JavaAnnotation> getAnnotations()
  {
    return (Collection)CollectionsKt.emptyList();
  }
  
  public JavaClassifier getClassifier()
  {
    return this.classifier;
  }
  
  public String getClassifierQualifiedName()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Type not found: ");
    localStringBuilder.append(getReflectType());
    throw ((Throwable)new UnsupportedOperationException(localStringBuilder.toString()));
  }
  
  public String getPresentableText()
  {
    return getReflectType().toString();
  }
  
  public Type getReflectType()
  {
    return this.reflectType;
  }
  
  public List<JavaType> getTypeArguments()
  {
    Object localObject = (Iterable)ReflectClassUtilKt.getParameterizedTypeArguments(getReflectType());
    ReflectJavaType.Factory localFactory = ReflectJavaType.Factory;
    Collection localCollection = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject, 10));
    localObject = ((Iterable)localObject).iterator();
    while (((Iterator)localObject).hasNext()) {
      localCollection.add(localFactory.create((Type)((Iterator)localObject).next()));
    }
    return (List)localCollection;
  }
  
  public boolean isDeprecatedInJavaDoc()
  {
    return false;
  }
  
  public boolean isRaw()
  {
    Object localObject = getReflectType();
    boolean bool1 = localObject instanceof Class;
    boolean bool2 = false;
    boolean bool3 = bool2;
    if (bool1)
    {
      localObject = ((Class)localObject).getTypeParameters();
      Intrinsics.checkExpressionValueIsNotNull(localObject, "getTypeParameters()");
      int i;
      if (localObject.length == 0) {
        i = 1;
      } else {
        i = 0;
      }
      bool3 = bool2;
      if ((i ^ 0x1) != 0) {
        bool3 = true;
      }
    }
    return bool3;
  }
}

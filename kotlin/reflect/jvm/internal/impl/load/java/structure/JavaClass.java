package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.Collection;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;

public abstract interface JavaClass
  extends JavaClassifier, JavaModifierListOwner, JavaTypeParameterListOwner
{
  public abstract Collection<JavaConstructor> getConstructors();
  
  public abstract Collection<JavaField> getFields();
  
  public abstract FqName getFqName();
  
  public abstract Collection<Name> getInnerClassNames();
  
  public abstract LightClassOriginKind getLightClassOriginKind();
  
  public abstract Collection<JavaMethod> getMethods();
  
  public abstract JavaClass getOuterClass();
  
  public abstract Collection<JavaClassifierType> getSupertypes();
  
  public abstract boolean isAnnotationType();
  
  public abstract boolean isEnum();
  
  public abstract boolean isInterface();
}

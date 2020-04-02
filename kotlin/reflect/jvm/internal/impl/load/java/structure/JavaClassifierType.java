package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.List;

public abstract interface JavaClassifierType
  extends JavaAnnotationOwner, JavaType
{
  public abstract JavaClassifier getClassifier();
  
  public abstract String getClassifierQualifiedName();
  
  public abstract String getPresentableText();
  
  public abstract List<JavaType> getTypeArguments();
  
  public abstract boolean isRaw();
}

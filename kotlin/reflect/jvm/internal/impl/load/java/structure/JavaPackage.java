package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.Collection;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;

public abstract interface JavaPackage
  extends JavaAnnotationOwner, JavaElement
{
  public abstract Collection<JavaClass> getClasses(Function1<? super Name, Boolean> paramFunction1);
  
  public abstract FqName getFqName();
  
  public abstract Collection<JavaPackage> getSubPackages();
}

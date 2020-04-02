package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaField;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMethod;
import kotlin.reflect.jvm.internal.impl.name.Name;

public abstract interface DeclaredMemberIndex
{
  public abstract JavaField findFieldByName(Name paramName);
  
  public abstract Collection<JavaMethod> findMethodsByName(Name paramName);
  
  public abstract Set<Name> getFieldNames();
  
  public abstract Set<Name> getMethodNames();
  
  public static final class Empty
    implements DeclaredMemberIndex
  {
    public static final Empty INSTANCE = new Empty();
    
    private Empty() {}
    
    public JavaField findFieldByName(Name paramName)
    {
      Intrinsics.checkParameterIsNotNull(paramName, "name");
      return null;
    }
    
    public List<JavaMethod> findMethodsByName(Name paramName)
    {
      Intrinsics.checkParameterIsNotNull(paramName, "name");
      return CollectionsKt.emptyList();
    }
    
    public Set<Name> getFieldNames()
    {
      return SetsKt.emptySet();
    }
    
    public Set<Name> getMethodNames()
    {
      return SetsKt.emptySet();
    }
  }
}

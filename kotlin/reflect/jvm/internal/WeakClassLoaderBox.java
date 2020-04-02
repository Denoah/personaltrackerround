package kotlin.reflect.jvm.internal;

import java.lang.ref.WeakReference;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\0000\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\003\n\002\030\002\n\002\b\007\n\002\020\013\n\002\b\003\n\002\020\016\n\000\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\023\020\021\032\0020\0222\b\020\023\032\004\030\0010\001H?\002J\b\020\024\032\0020\006H\026J\b\020\025\032\0020\026H\026R\021\020\005\032\0020\006?\006\b\n\000\032\004\b\007\020\bR\027\020\t\032\b\022\004\022\0020\0030\n?\006\b\n\000\032\004\b\013\020\fR\034\020\r\032\004\030\0010\003X?\016?\006\016\n\000\032\004\b\016\020\017\"\004\b\020\020\004?\006\027"}, d2={"Lkotlin/reflect/jvm/internal/WeakClassLoaderBox;", "", "classLoader", "Ljava/lang/ClassLoader;", "(Ljava/lang/ClassLoader;)V", "identityHashCode", "", "getIdentityHashCode", "()I", "ref", "Ljava/lang/ref/WeakReference;", "getRef", "()Ljava/lang/ref/WeakReference;", "temporaryStrongRef", "getTemporaryStrongRef", "()Ljava/lang/ClassLoader;", "setTemporaryStrongRef", "equals", "", "other", "hashCode", "toString", "", "kotlin-reflection"}, k=1, mv={1, 1, 15})
final class WeakClassLoaderBox
{
  private final int identityHashCode;
  private final WeakReference<ClassLoader> ref;
  private ClassLoader temporaryStrongRef;
  
  public WeakClassLoaderBox(ClassLoader paramClassLoader)
  {
    this.ref = new WeakReference(paramClassLoader);
    this.identityHashCode = System.identityHashCode(paramClassLoader);
    this.temporaryStrongRef = paramClassLoader;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof WeakClassLoaderBox)) && ((ClassLoader)this.ref.get() == (ClassLoader)((WeakClassLoaderBox)paramObject).ref.get())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public int hashCode()
  {
    return this.identityHashCode;
  }
  
  public final void setTemporaryStrongRef(ClassLoader paramClassLoader)
  {
    this.temporaryStrongRef = paramClassLoader;
  }
  
  public String toString()
  {
    Object localObject = (ClassLoader)this.ref.get();
    if (localObject != null)
    {
      localObject = ((ClassLoader)localObject).toString();
      if (localObject != null) {}
    }
    else
    {
      localObject = "<null>";
    }
    return localObject;
  }
}

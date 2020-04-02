package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.reflect.jvm.internal.impl.name.FqName;

public abstract interface Annotations
  extends Iterable<AnnotationDescriptor>, KMappedMarker
{
  public static final Companion Companion = Companion.$$INSTANCE;
  
  public abstract AnnotationDescriptor findAnnotation(FqName paramFqName);
  
  public abstract boolean hasAnnotation(FqName paramFqName);
  
  public abstract boolean isEmpty();
  
  public static final class Companion
  {
    private static final Annotations EMPTY = (Annotations)new Annotations()
    {
      public Void findAnnotation(FqName paramAnonymousFqName)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFqName, "fqName");
        return null;
      }
      
      public boolean hasAnnotation(FqName paramAnonymousFqName)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFqName, "fqName");
        return Annotations.DefaultImpls.hasAnnotation(this, paramAnonymousFqName);
      }
      
      public boolean isEmpty()
      {
        return true;
      }
      
      public Iterator<AnnotationDescriptor> iterator()
      {
        return CollectionsKt.emptyList().iterator();
      }
      
      public String toString()
      {
        return "EMPTY";
      }
    };
    
    private Companion() {}
    
    public final Annotations create(List<? extends AnnotationDescriptor> paramList)
    {
      Intrinsics.checkParameterIsNotNull(paramList, "annotations");
      if (paramList.isEmpty()) {
        paramList = EMPTY;
      } else {
        paramList = (Annotations)new AnnotationsImpl(paramList);
      }
      return paramList;
    }
    
    public final Annotations getEMPTY()
    {
      return EMPTY;
    }
  }
  
  public static final class DefaultImpls
  {
    public static AnnotationDescriptor findAnnotation(Annotations paramAnnotations, FqName paramFqName)
    {
      Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
      Iterator localIterator = ((Iterable)paramAnnotations).iterator();
      while (localIterator.hasNext())
      {
        paramAnnotations = localIterator.next();
        if (Intrinsics.areEqual(((AnnotationDescriptor)paramAnnotations).getFqName(), paramFqName)) {
          break label53;
        }
      }
      paramAnnotations = null;
      label53:
      return (AnnotationDescriptor)paramAnnotations;
    }
    
    public static boolean hasAnnotation(Annotations paramAnnotations, FqName paramFqName)
    {
      Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
      boolean bool;
      if (paramAnnotations.findAnnotation(paramFqName) != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
}

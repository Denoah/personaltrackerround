package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.FqName;

public final class FilteredAnnotations
  implements Annotations
{
  private final Annotations delegate;
  private final Function1<FqName, Boolean> fqNameFilter;
  
  public FilteredAnnotations(Annotations paramAnnotations, Function1<? super FqName, Boolean> paramFunction1)
  {
    this.delegate = paramAnnotations;
    this.fqNameFilter = paramFunction1;
  }
  
  private final boolean shouldBeReturned(AnnotationDescriptor paramAnnotationDescriptor)
  {
    paramAnnotationDescriptor = paramAnnotationDescriptor.getFqName();
    boolean bool;
    if ((paramAnnotationDescriptor != null) && (((Boolean)this.fqNameFilter.invoke(paramAnnotationDescriptor)).booleanValue())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public AnnotationDescriptor findAnnotation(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    if (((Boolean)this.fqNameFilter.invoke(paramFqName)).booleanValue()) {
      paramFqName = this.delegate.findAnnotation(paramFqName);
    } else {
      paramFqName = null;
    }
    return paramFqName;
  }
  
  public boolean hasAnnotation(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    boolean bool;
    if (((Boolean)this.fqNameFilter.invoke(paramFqName)).booleanValue()) {
      bool = this.delegate.hasAnnotation(paramFqName);
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isEmpty()
  {
    Object localObject = (Iterable)this.delegate;
    boolean bool1 = localObject instanceof Collection;
    boolean bool2 = false;
    if ((bool1) && (((Collection)localObject).isEmpty()))
    {
      bool1 = bool2;
    }
    else
    {
      localObject = ((Iterable)localObject).iterator();
      AnnotationDescriptor localAnnotationDescriptor;
      do
      {
        bool1 = bool2;
        if (!((Iterator)localObject).hasNext()) {
          break;
        }
        localAnnotationDescriptor = (AnnotationDescriptor)((Iterator)localObject).next();
      } while (!((FilteredAnnotations)this).shouldBeReturned(localAnnotationDescriptor));
      bool1 = true;
    }
    return bool1;
  }
  
  public Iterator<AnnotationDescriptor> iterator()
  {
    Object localObject1 = (Iterable)this.delegate;
    Collection localCollection = (Collection)new ArrayList();
    localObject1 = ((Iterable)localObject1).iterator();
    while (((Iterator)localObject1).hasNext())
    {
      Object localObject2 = ((Iterator)localObject1).next();
      AnnotationDescriptor localAnnotationDescriptor = (AnnotationDescriptor)localObject2;
      if (((FilteredAnnotations)this).shouldBeReturned(localAnnotationDescriptor)) {
        localCollection.add(localObject2);
      }
    }
    return ((List)localCollection).iterator();
  }
}

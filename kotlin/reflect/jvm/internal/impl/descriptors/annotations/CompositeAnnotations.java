package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

public final class CompositeAnnotations
  implements Annotations
{
  private final List<Annotations> delegates;
  
  public CompositeAnnotations(List<? extends Annotations> paramList)
  {
    this.delegates = paramList;
  }
  
  public CompositeAnnotations(Annotations... paramVarArgs)
  {
    this(ArraysKt.toList(paramVarArgs));
  }
  
  public AnnotationDescriptor findAnnotation(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    (AnnotationDescriptor)SequencesKt.firstOrNull(SequencesKt.mapNotNull(CollectionsKt.asSequence((Iterable)this.delegates), (Function1)new Lambda(paramFqName)
    {
      public final AnnotationDescriptor invoke(Annotations paramAnonymousAnnotations)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousAnnotations, "it");
        return paramAnonymousAnnotations.findAnnotation(this.$fqName);
      }
    }));
  }
  
  public boolean hasAnnotation(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    Iterator localIterator = CollectionsKt.asSequence((Iterable)this.delegates).iterator();
    while (localIterator.hasNext()) {
      if (((Annotations)localIterator.next()).hasAnnotation(paramFqName)) {
        return true;
      }
    }
    boolean bool = false;
    return bool;
  }
  
  public boolean isEmpty()
  {
    Object localObject = (Iterable)this.delegates;
    boolean bool1 = localObject instanceof Collection;
    boolean bool2 = true;
    if ((bool1) && (((Collection)localObject).isEmpty()))
    {
      bool1 = bool2;
    }
    else
    {
      localObject = ((Iterable)localObject).iterator();
      do
      {
        bool1 = bool2;
        if (!((Iterator)localObject).hasNext()) {
          break;
        }
      } while (((Annotations)((Iterator)localObject).next()).isEmpty());
      bool1 = false;
    }
    return bool1;
  }
  
  public Iterator<AnnotationDescriptor> iterator()
  {
    return SequencesKt.flatMap(CollectionsKt.asSequence((Iterable)this.delegates), (Function1)iterator.1.INSTANCE).iterator();
  }
}

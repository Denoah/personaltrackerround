package kotlin.reflect.jvm.internal.impl.types;

import java.util.Collection;
import java.util.Iterator;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentListMarker;
import kotlin.text.StringsKt;

public abstract class SimpleType
  extends UnwrappedType
  implements SimpleTypeMarker, TypeArgumentListMarker
{
  public SimpleType()
  {
    super(null);
  }
  
  public abstract SimpleType makeNullableAsSpecified(boolean paramBoolean);
  
  public abstract SimpleType replaceAnnotations(Annotations paramAnnotations);
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Object localObject = getAnnotations().iterator();
    while (((Iterator)localObject).hasNext())
    {
      AnnotationDescriptor localAnnotationDescriptor = (AnnotationDescriptor)((Iterator)localObject).next();
      StringsKt.append(localStringBuilder, new String[] { "[", DescriptorRenderer.renderAnnotation$default(DescriptorRenderer.DEBUG_TEXT, localAnnotationDescriptor, null, 2, null), "] " });
    }
    localStringBuilder.append(getConstructor());
    if ((((Collection)getArguments()).isEmpty() ^ true)) {
      CollectionsKt.joinTo$default((Iterable)getArguments(), (Appendable)localStringBuilder, (CharSequence)", ", (CharSequence)"<", (CharSequence)">", 0, null, null, 112, null);
    }
    if (isMarkedNullable()) {
      localStringBuilder.append("?");
    }
    localObject = localStringBuilder.toString();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "StringBuilder().apply(builderAction).toString()");
    return localObject;
  }
}

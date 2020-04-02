package kotlin.reflect.jvm.internal.impl.renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;

public enum DescriptorRendererModifier
{
  public static final Set<DescriptorRendererModifier> ALL = ArraysKt.toSet(values());
  public static final Companion Companion;
  public static final Set<DescriptorRendererModifier> DEFAULTS;
  private final boolean includeByDefault;
  
  static
  {
    int i = 0;
    DescriptorRendererModifier localDescriptorRendererModifier1 = new DescriptorRendererModifier("VISIBILITY", 0, true);
    VISIBILITY = localDescriptorRendererModifier1;
    DescriptorRendererModifier localDescriptorRendererModifier2 = new DescriptorRendererModifier("MODALITY", 1, true);
    MODALITY = localDescriptorRendererModifier2;
    DescriptorRendererModifier localDescriptorRendererModifier3 = new DescriptorRendererModifier("OVERRIDE", 2, true);
    OVERRIDE = localDescriptorRendererModifier3;
    DescriptorRendererModifier localDescriptorRendererModifier4 = new DescriptorRendererModifier("ANNOTATIONS", 3, false);
    ANNOTATIONS = localDescriptorRendererModifier4;
    DescriptorRendererModifier localDescriptorRendererModifier5 = new DescriptorRendererModifier("INNER", 4, true);
    INNER = localDescriptorRendererModifier5;
    Object localObject1 = new DescriptorRendererModifier("MEMBER_KIND", 5, true);
    MEMBER_KIND = (DescriptorRendererModifier)localObject1;
    DescriptorRendererModifier localDescriptorRendererModifier6 = new DescriptorRendererModifier("DATA", 6, true);
    DATA = localDescriptorRendererModifier6;
    DescriptorRendererModifier localDescriptorRendererModifier7 = new DescriptorRendererModifier("INLINE", 7, true);
    INLINE = localDescriptorRendererModifier7;
    DescriptorRendererModifier localDescriptorRendererModifier8 = new DescriptorRendererModifier("EXPECT", 8, true);
    EXPECT = localDescriptorRendererModifier8;
    DescriptorRendererModifier localDescriptorRendererModifier9 = new DescriptorRendererModifier("ACTUAL", 9, true);
    ACTUAL = localDescriptorRendererModifier9;
    DescriptorRendererModifier localDescriptorRendererModifier10 = new DescriptorRendererModifier("CONST", 10, true);
    CONST = localDescriptorRendererModifier10;
    Object localObject2 = new DescriptorRendererModifier("LATEINIT", 11, true);
    LATEINIT = (DescriptorRendererModifier)localObject2;
    $VALUES = new DescriptorRendererModifier[] { localDescriptorRendererModifier1, localDescriptorRendererModifier2, localDescriptorRendererModifier3, localDescriptorRendererModifier4, localDescriptorRendererModifier5, localObject1, localDescriptorRendererModifier6, localDescriptorRendererModifier7, localDescriptorRendererModifier8, localDescriptorRendererModifier9, localDescriptorRendererModifier10, localObject2 };
    Companion = new Companion(null);
    localObject1 = values();
    localObject2 = (Collection)new ArrayList();
    int j = localObject1.length;
    while (i < j)
    {
      localDescriptorRendererModifier10 = localObject1[i];
      if (localDescriptorRendererModifier10.includeByDefault) {
        ((Collection)localObject2).add(localDescriptorRendererModifier10);
      }
      i++;
    }
    DEFAULTS = CollectionsKt.toSet((Iterable)localObject2);
  }
  
  private DescriptorRendererModifier(boolean paramBoolean)
  {
    this.includeByDefault = paramBoolean;
  }
  
  public static final class Companion
  {
    private Companion() {}
  }
}

package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

public final class DescriptorKindFilter
{
  public static final DescriptorKindFilter ALL;
  private static final int ALL_KINDS_MASK;
  public static final DescriptorKindFilter CALLABLES;
  private static final int CALLABLES_MASK;
  public static final DescriptorKindFilter CLASSIFIERS;
  private static final int CLASSIFIERS_MASK;
  public static final Companion Companion;
  private static final List<DescriptorKindFilter.Companion.MaskToName> DEBUG_MASK_BIT_NAMES;
  private static final List<DescriptorKindFilter.Companion.MaskToName> DEBUG_PREDEFINED_FILTERS_MASK_NAMES;
  public static final DescriptorKindFilter FUNCTIONS;
  private static final int FUNCTIONS_MASK;
  public static final DescriptorKindFilter NON_SINGLETON_CLASSIFIERS;
  private static final int NON_SINGLETON_CLASSIFIERS_MASK;
  public static final DescriptorKindFilter PACKAGES;
  private static final int PACKAGES_MASK;
  public static final DescriptorKindFilter SINGLETON_CLASSIFIERS;
  private static final int SINGLETON_CLASSIFIERS_MASK;
  public static final DescriptorKindFilter TYPE_ALIASES;
  private static final int TYPE_ALIASES_MASK;
  public static final DescriptorKindFilter VALUES;
  private static final int VALUES_MASK;
  public static final DescriptorKindFilter VARIABLES;
  private static final int VARIABLES_MASK;
  private static int nextMaskValue;
  private final List<DescriptorKindExclude> excludes;
  private final int kindMask;
  
  static
  {
    Object localObject1 = new Companion(null);
    Companion = (Companion)localObject1;
    nextMaskValue = 1;
    NON_SINGLETON_CLASSIFIERS_MASK = Companion.access$nextMask((Companion)localObject1);
    SINGLETON_CLASSIFIERS_MASK = Companion.access$nextMask(Companion);
    TYPE_ALIASES_MASK = Companion.access$nextMask(Companion);
    PACKAGES_MASK = Companion.access$nextMask(Companion);
    FUNCTIONS_MASK = Companion.access$nextMask(Companion);
    VARIABLES_MASK = Companion.access$nextMask(Companion);
    ALL_KINDS_MASK = Companion.access$nextMask(Companion) - 1;
    int i = NON_SINGLETON_CLASSIFIERS_MASK;
    int j = SINGLETON_CLASSIFIERS_MASK;
    CLASSIFIERS_MASK = i | j | TYPE_ALIASES_MASK;
    i = FUNCTIONS_MASK;
    int k = VARIABLES_MASK;
    VALUES_MASK = j | i | k;
    CALLABLES_MASK = i | k;
    ALL = new DescriptorKindFilter(ALL_KINDS_MASK, null, 2, null);
    CALLABLES = new DescriptorKindFilter(CALLABLES_MASK, null, 2, null);
    NON_SINGLETON_CLASSIFIERS = new DescriptorKindFilter(NON_SINGLETON_CLASSIFIERS_MASK, null, 2, null);
    SINGLETON_CLASSIFIERS = new DescriptorKindFilter(SINGLETON_CLASSIFIERS_MASK, null, 2, null);
    TYPE_ALIASES = new DescriptorKindFilter(TYPE_ALIASES_MASK, null, 2, null);
    CLASSIFIERS = new DescriptorKindFilter(CLASSIFIERS_MASK, null, 2, null);
    PACKAGES = new DescriptorKindFilter(PACKAGES_MASK, null, 2, null);
    FUNCTIONS = new DescriptorKindFilter(FUNCTIONS_MASK, null, 2, null);
    VARIABLES = new DescriptorKindFilter(VARIABLES_MASK, null, 2, null);
    VALUES = new DescriptorKindFilter(VALUES_MASK, null, 2, null);
    Object localObject2 = DescriptorKindFilter.class.getFields();
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "T::class.java.fields");
    Object localObject3 = (Collection)new ArrayList();
    i = localObject2.length;
    for (j = 0; j < i; j++)
    {
      localObject1 = localObject2[j];
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "it");
      if (Modifier.isStatic(((Field)localObject1).getModifiers())) {
        ((Collection)localObject3).add(localObject1);
      }
    }
    localObject1 = (Iterable)localObject3;
    localObject2 = (Collection)new ArrayList();
    Object localObject4 = ((Iterable)localObject1).iterator();
    while (((Iterator)localObject4).hasNext())
    {
      Field localField = (Field)((Iterator)localObject4).next();
      localObject3 = localField.get(null);
      localObject1 = localObject3;
      if (!(localObject3 instanceof DescriptorKindFilter)) {
        localObject1 = null;
      }
      localObject1 = (DescriptorKindFilter)localObject1;
      if (localObject1 != null)
      {
        j = ((DescriptorKindFilter)localObject1).kindMask;
        Intrinsics.checkExpressionValueIsNotNull(localField, "field");
        localObject1 = localField.getName();
        Intrinsics.checkExpressionValueIsNotNull(localObject1, "field.name");
        localObject1 = new DescriptorKindFilter.Companion.MaskToName(j, (String)localObject1);
      }
      else
      {
        localObject1 = null;
      }
      if (localObject1 != null) {
        ((Collection)localObject2).add(localObject1);
      }
    }
    DEBUG_PREDEFINED_FILTERS_MASK_NAMES = CollectionsKt.toList((Iterable)localObject2);
    localObject2 = DescriptorKindFilter.class.getFields();
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "T::class.java.fields");
    localObject3 = (Collection)new ArrayList();
    i = localObject2.length;
    for (j = 0; j < i; j++)
    {
      localObject1 = localObject2[j];
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "it");
      if (Modifier.isStatic(((Field)localObject1).getModifiers())) {
        ((Collection)localObject3).add(localObject1);
      }
    }
    localObject3 = (Iterable)localObject3;
    localObject1 = (Collection)new ArrayList();
    localObject2 = ((Iterable)localObject3).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject3 = ((Iterator)localObject2).next();
      localObject4 = (Field)localObject3;
      Intrinsics.checkExpressionValueIsNotNull(localObject4, "it");
      if (Intrinsics.areEqual(((Field)localObject4).getType(), Integer.TYPE)) {
        ((Collection)localObject1).add(localObject3);
      }
    }
    localObject1 = (Iterable)localObject1;
    localObject3 = (Collection)new ArrayList();
    localObject2 = ((Iterable)localObject1).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject1 = (Field)((Iterator)localObject2).next();
      localObject4 = ((Field)localObject1).get(null);
      if (localObject4 != null)
      {
        i = ((Integer)localObject4).intValue();
        if (i == (-i & i)) {
          j = 1;
        } else {
          j = 0;
        }
        if (j != 0)
        {
          Intrinsics.checkExpressionValueIsNotNull(localObject1, "field");
          localObject1 = ((Field)localObject1).getName();
          Intrinsics.checkExpressionValueIsNotNull(localObject1, "field.name");
          localObject1 = new DescriptorKindFilter.Companion.MaskToName(i, (String)localObject1);
        }
        else
        {
          localObject1 = null;
        }
        if (localObject1 != null) {
          ((Collection)localObject3).add(localObject1);
        }
      }
      else
      {
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
      }
    }
    DEBUG_MASK_BIT_NAMES = CollectionsKt.toList((Iterable)localObject3);
  }
  
  public DescriptorKindFilter(int paramInt, List<? extends DescriptorKindExclude> paramList)
  {
    this.excludes = paramList;
    paramList = ((Iterable)paramList).iterator();
    while (paramList.hasNext()) {
      paramInt &= ((DescriptorKindExclude)paramList.next()).getFullyExcludedDescriptorKinds();
    }
    this.kindMask = paramInt;
  }
  
  public final boolean acceptsKinds(int paramInt)
  {
    boolean bool;
    if ((paramInt & this.kindMask) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final List<DescriptorKindExclude> getExcludes()
  {
    return this.excludes;
  }
  
  public final int getKindMask()
  {
    return this.kindMask;
  }
  
  public final DescriptorKindFilter restrictedToKindsOrNull(int paramInt)
  {
    paramInt &= this.kindMask;
    if (paramInt == 0) {
      return null;
    }
    return new DescriptorKindFilter(paramInt, this.excludes);
  }
  
  public String toString()
  {
    Object localObject1 = ((Iterable)DEBUG_PREDEFINED_FILTERS_MASK_NAMES).iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = ((Iterator)localObject1).next();
      int i;
      if (((DescriptorKindFilter.Companion.MaskToName)localObject2).getMask() == this.kindMask) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        break label58;
      }
    }
    Object localObject2 = null;
    label58:
    localObject2 = (DescriptorKindFilter.Companion.MaskToName)localObject2;
    if (localObject2 != null) {
      localObject2 = ((DescriptorKindFilter.Companion.MaskToName)localObject2).getName();
    } else {
      localObject2 = null;
    }
    if (localObject2 == null)
    {
      localObject2 = (Iterable)DEBUG_MASK_BIT_NAMES;
      localObject1 = (Collection)new ArrayList();
      Iterator localIterator = ((Iterable)localObject2).iterator();
      while (localIterator.hasNext())
      {
        localObject2 = (DescriptorKindFilter.Companion.MaskToName)localIterator.next();
        if (acceptsKinds(((DescriptorKindFilter.Companion.MaskToName)localObject2).getMask())) {
          localObject2 = ((DescriptorKindFilter.Companion.MaskToName)localObject2).getName();
        } else {
          localObject2 = null;
        }
        if (localObject2 != null) {
          ((Collection)localObject1).add(localObject2);
        }
      }
      localObject2 = CollectionsKt.joinToString$default((Iterable)localObject1, (CharSequence)" | ", null, null, 0, null, null, 62, null);
    }
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("DescriptorKindFilter(");
    ((StringBuilder)localObject1).append((String)localObject2);
    ((StringBuilder)localObject1).append(", ");
    ((StringBuilder)localObject1).append(this.excludes);
    ((StringBuilder)localObject1).append(')');
    return ((StringBuilder)localObject1).toString();
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    private final int nextMask()
    {
      int i = DescriptorKindFilter.access$getNextMaskValue$cp();
      DescriptorKindFilter.access$setNextMaskValue$cp(DescriptorKindFilter.access$getNextMaskValue$cp() << 1);
      return i;
    }
    
    public final int getALL_KINDS_MASK()
    {
      return DescriptorKindFilter.access$getALL_KINDS_MASK$cp();
    }
    
    public final int getCLASSIFIERS_MASK()
    {
      return DescriptorKindFilter.access$getCLASSIFIERS_MASK$cp();
    }
    
    public final int getFUNCTIONS_MASK()
    {
      return DescriptorKindFilter.access$getFUNCTIONS_MASK$cp();
    }
    
    public final int getNON_SINGLETON_CLASSIFIERS_MASK()
    {
      return DescriptorKindFilter.access$getNON_SINGLETON_CLASSIFIERS_MASK$cp();
    }
    
    public final int getPACKAGES_MASK()
    {
      return DescriptorKindFilter.access$getPACKAGES_MASK$cp();
    }
    
    public final int getSINGLETON_CLASSIFIERS_MASK()
    {
      return DescriptorKindFilter.access$getSINGLETON_CLASSIFIERS_MASK$cp();
    }
    
    public final int getTYPE_ALIASES_MASK()
    {
      return DescriptorKindFilter.access$getTYPE_ALIASES_MASK$cp();
    }
    
    public final int getVARIABLES_MASK()
    {
      return DescriptorKindFilter.access$getVARIABLES_MASK$cp();
    }
    
    private static final class MaskToName
    {
      private final int mask;
      private final String name;
      
      public MaskToName(int paramInt, String paramString)
      {
        this.mask = paramInt;
        this.name = paramString;
      }
      
      public final int getMask()
      {
        return this.mask;
      }
      
      public final String getName()
      {
        return this.name;
      }
    }
  }
}

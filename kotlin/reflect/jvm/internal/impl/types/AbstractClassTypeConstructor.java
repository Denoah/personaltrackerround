package kotlin.reflect.jvm.internal.impl.types;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.utils.SmartList;

public abstract class AbstractClassTypeConstructor
  extends AbstractTypeConstructor
  implements TypeConstructor
{
  private int hashCode = 0;
  
  public AbstractClassTypeConstructor(StorageManager paramStorageManager)
  {
    super(paramStorageManager);
  }
  
  private static boolean areFqNamesEqual(ClassDescriptor paramClassDescriptor1, ClassDescriptor paramClassDescriptor2)
  {
    boolean bool1 = paramClassDescriptor1.getName().equals(paramClassDescriptor2.getName());
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    DeclarationDescriptor localDeclarationDescriptor = paramClassDescriptor1.getContainingDeclaration();
    paramClassDescriptor1 = paramClassDescriptor2.getContainingDeclaration();
    paramClassDescriptor2 = localDeclarationDescriptor;
    while ((paramClassDescriptor2 != null) && (paramClassDescriptor1 != null))
    {
      if ((paramClassDescriptor2 instanceof ModuleDescriptor)) {
        return paramClassDescriptor1 instanceof ModuleDescriptor;
      }
      if ((paramClassDescriptor1 instanceof ModuleDescriptor)) {
        return false;
      }
      if ((paramClassDescriptor2 instanceof PackageFragmentDescriptor))
      {
        bool1 = bool2;
        if ((paramClassDescriptor1 instanceof PackageFragmentDescriptor))
        {
          bool1 = bool2;
          if (((PackageFragmentDescriptor)paramClassDescriptor2).getFqName().equals(((PackageFragmentDescriptor)paramClassDescriptor1).getFqName())) {
            bool1 = true;
          }
        }
        return bool1;
      }
      if ((paramClassDescriptor1 instanceof PackageFragmentDescriptor)) {
        return false;
      }
      if (!paramClassDescriptor2.getName().equals(paramClassDescriptor1.getName())) {
        return false;
      }
      paramClassDescriptor2 = paramClassDescriptor2.getContainingDeclaration();
      paramClassDescriptor1 = paramClassDescriptor1.getContainingDeclaration();
    }
    return true;
  }
  
  private static boolean hasMeaningfulFqName(ClassifierDescriptor paramClassifierDescriptor)
  {
    if (paramClassifierDescriptor == null) {
      $$$reportNull$$$0(2);
    }
    boolean bool;
    if ((!ErrorUtils.isError(paramClassifierDescriptor)) && (!DescriptorUtils.isLocal(paramClassifierDescriptor))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected KotlinType defaultSupertypeIfEmpty()
  {
    if (KotlinBuiltIns.isSpecialClassWithNoSupertypes(getDeclarationDescriptor())) {
      return null;
    }
    return getBuiltIns().getAnyType();
  }
  
  public final boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof TypeConstructor)) {
      return false;
    }
    if (paramObject.hashCode() != hashCode()) {
      return false;
    }
    Object localObject = (TypeConstructor)paramObject;
    if (((TypeConstructor)localObject).getParameters().size() != getParameters().size()) {
      return false;
    }
    paramObject = getDeclarationDescriptor();
    localObject = ((TypeConstructor)localObject).getDeclarationDescriptor();
    if ((hasMeaningfulFqName(paramObject)) && ((localObject == null) || (hasMeaningfulFqName((ClassifierDescriptor)localObject))) && ((localObject instanceof ClassDescriptor))) {
      return areFqNamesEqual((ClassDescriptor)paramObject, (ClassDescriptor)localObject);
    }
    return false;
  }
  
  protected Collection<KotlinType> getAdditionalNeighboursInSupertypeGraph(boolean paramBoolean)
  {
    Object localObject1 = getDeclarationDescriptor().getContainingDeclaration();
    if (!(localObject1 instanceof ClassDescriptor))
    {
      localObject2 = Collections.emptyList();
      if (localObject2 == null) {
        $$$reportNull$$$0(3);
      }
      return localObject2;
    }
    Object localObject2 = new SmartList();
    localObject1 = (ClassDescriptor)localObject1;
    ((Collection)localObject2).add(((ClassDescriptor)localObject1).getDefaultType());
    localObject1 = ((ClassDescriptor)localObject1).getCompanionObjectDescriptor();
    if ((paramBoolean) && (localObject1 != null)) {
      ((Collection)localObject2).add(((ClassDescriptor)localObject1).getDefaultType());
    }
    return localObject2;
  }
  
  public KotlinBuiltIns getBuiltIns()
  {
    KotlinBuiltIns localKotlinBuiltIns = DescriptorUtilsKt.getBuiltIns(getDeclarationDescriptor());
    if (localKotlinBuiltIns == null) {
      $$$reportNull$$$0(1);
    }
    return localKotlinBuiltIns;
  }
  
  public abstract ClassDescriptor getDeclarationDescriptor();
  
  public final int hashCode()
  {
    int i = this.hashCode;
    if (i != 0) {
      return i;
    }
    ClassDescriptor localClassDescriptor = getDeclarationDescriptor();
    if (hasMeaningfulFqName(localClassDescriptor)) {
      i = DescriptorUtils.getFqName(localClassDescriptor).hashCode();
    } else {
      i = System.identityHashCode(this);
    }
    this.hashCode = i;
    return i;
  }
}

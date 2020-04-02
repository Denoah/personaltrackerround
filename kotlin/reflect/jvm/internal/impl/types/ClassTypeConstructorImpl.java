package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SupertypeLoopChecker;
import kotlin.reflect.jvm.internal.impl.descriptors.SupertypeLoopChecker.EMPTY;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;

public class ClassTypeConstructorImpl
  extends AbstractClassTypeConstructor
  implements TypeConstructor
{
  private final ClassDescriptor classDescriptor;
  private final List<TypeParameterDescriptor> parameters;
  private final Collection<KotlinType> supertypes;
  
  public ClassTypeConstructorImpl(ClassDescriptor paramClassDescriptor, List<? extends TypeParameterDescriptor> paramList, Collection<KotlinType> paramCollection, StorageManager paramStorageManager)
  {
    super(paramStorageManager);
    this.classDescriptor = paramClassDescriptor;
    this.parameters = Collections.unmodifiableList(new ArrayList(paramList));
    this.supertypes = Collections.unmodifiableCollection(paramCollection);
  }
  
  protected Collection<KotlinType> computeSupertypes()
  {
    Collection localCollection = this.supertypes;
    if (localCollection == null) {
      $$$reportNull$$$0(6);
    }
    return localCollection;
  }
  
  public ClassDescriptor getDeclarationDescriptor()
  {
    ClassDescriptor localClassDescriptor = this.classDescriptor;
    if (localClassDescriptor == null) {
      $$$reportNull$$$0(5);
    }
    return localClassDescriptor;
  }
  
  public List<TypeParameterDescriptor> getParameters()
  {
    List localList = this.parameters;
    if (localList == null) {
      $$$reportNull$$$0(4);
    }
    return localList;
  }
  
  protected SupertypeLoopChecker getSupertypeLoopChecker()
  {
    SupertypeLoopChecker.EMPTY localEMPTY = SupertypeLoopChecker.EMPTY.INSTANCE;
    if (localEMPTY == null) {
      $$$reportNull$$$0(7);
    }
    return localEMPTY;
  }
  
  public boolean isDenotable()
  {
    return true;
  }
  
  public String toString()
  {
    return DescriptorUtils.getFqName(this.classDescriptor).asString();
  }
}

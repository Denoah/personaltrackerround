package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.builtins.BuiltInsPackageFragment;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns.FqNames;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;

public final class JvmBuiltInClassDescriptorFactory
  implements ClassDescriptorFactory
{
  private static final ClassId CLONEABLE_CLASS_ID;
  private static final Name CLONEABLE_NAME;
  public static final Companion Companion = new Companion(null);
  private static final FqName KOTLIN_FQ_NAME = KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME;
  private final NotNullLazyValue cloneable$delegate;
  private final Function1<ModuleDescriptor, DeclarationDescriptor> computeContainingDeclaration;
  private final ModuleDescriptor moduleDescriptor;
  
  static
  {
    Object localObject = KotlinBuiltIns.FQ_NAMES.cloneable.shortName();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "KotlinBuiltIns.FQ_NAMES.cloneable.shortName()");
    CLONEABLE_NAME = (Name)localObject;
    localObject = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.cloneable.toSafe());
    Intrinsics.checkExpressionValueIsNotNull(localObject, "ClassId.topLevel(KotlinB…NAMES.cloneable.toSafe())");
    CLONEABLE_CLASS_ID = (ClassId)localObject;
  }
  
  public JvmBuiltInClassDescriptorFactory(final StorageManager paramStorageManager, ModuleDescriptor paramModuleDescriptor, Function1<? super ModuleDescriptor, ? extends DeclarationDescriptor> paramFunction1)
  {
    this.moduleDescriptor = paramModuleDescriptor;
    this.computeContainingDeclaration = paramFunction1;
    this.cloneable$delegate = paramStorageManager.createLazyValue((Function0)new Lambda(paramStorageManager)
    {
      public final ClassDescriptorImpl invoke()
      {
        ClassDescriptorImpl localClassDescriptorImpl = new ClassDescriptorImpl((DeclarationDescriptor)JvmBuiltInClassDescriptorFactory.access$getComputeContainingDeclaration$p(this.this$0).invoke(JvmBuiltInClassDescriptorFactory.access$getModuleDescriptor$p(this.this$0)), JvmBuiltInClassDescriptorFactory.access$getCLONEABLE_NAME$cp(), Modality.ABSTRACT, ClassKind.INTERFACE, (Collection)CollectionsKt.listOf(JvmBuiltInClassDescriptorFactory.access$getModuleDescriptor$p(this.this$0).getBuiltIns().getAnyType()), SourceElement.NO_SOURCE, false, paramStorageManager);
        localClassDescriptorImpl.initialize((MemberScope)new CloneableClassScope(paramStorageManager, (ClassDescriptor)localClassDescriptorImpl), SetsKt.emptySet(), null);
        return localClassDescriptorImpl;
      }
    });
  }
  
  private final ClassDescriptorImpl getCloneable()
  {
    return (ClassDescriptorImpl)StorageKt.getValue(this.cloneable$delegate, this, $$delegatedProperties[0]);
  }
  
  public ClassDescriptor createClass(ClassId paramClassId)
  {
    Intrinsics.checkParameterIsNotNull(paramClassId, "classId");
    if (Intrinsics.areEqual(paramClassId, CLONEABLE_CLASS_ID)) {
      paramClassId = (ClassDescriptor)getCloneable();
    } else {
      paramClassId = null;
    }
    return paramClassId;
  }
  
  public Collection<ClassDescriptor> getAllContributedClassesIfPossible(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "packageFqName");
    if (Intrinsics.areEqual(paramFqName, KOTLIN_FQ_NAME)) {
      paramFqName = (Collection)SetsKt.setOf(getCloneable());
    } else {
      paramFqName = (Collection)SetsKt.emptySet();
    }
    return paramFqName;
  }
  
  public boolean shouldCreateClass(FqName paramFqName, Name paramName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "packageFqName");
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    boolean bool;
    if ((Intrinsics.areEqual(paramName, CLONEABLE_NAME)) && (Intrinsics.areEqual(paramFqName, KOTLIN_FQ_NAME))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    public final ClassId getCLONEABLE_CLASS_ID()
    {
      return JvmBuiltInClassDescriptorFactory.access$getCLONEABLE_CLASS_ID$cp();
    }
  }
}

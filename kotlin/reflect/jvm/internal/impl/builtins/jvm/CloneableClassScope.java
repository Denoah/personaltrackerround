package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.SimpleFunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.GivenFunctionsMemberScope;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public final class CloneableClassScope
  extends GivenFunctionsMemberScope
{
  private static final Name CLONE_NAME;
  public static final Companion Companion = new Companion(null);
  
  static
  {
    Name localName = Name.identifier("clone");
    Intrinsics.checkExpressionValueIsNotNull(localName, "Name.identifier(\"clone\")");
    CLONE_NAME = localName;
  }
  
  public CloneableClassScope(StorageManager paramStorageManager, ClassDescriptor paramClassDescriptor)
  {
    super(paramStorageManager, paramClassDescriptor);
  }
  
  protected List<FunctionDescriptor> computeDeclaredFunctions()
  {
    SimpleFunctionDescriptorImpl localSimpleFunctionDescriptorImpl = SimpleFunctionDescriptorImpl.create((DeclarationDescriptor)getContainingClass(), Annotations.Companion.getEMPTY(), CLONE_NAME, CallableMemberDescriptor.Kind.DECLARATION, SourceElement.NO_SOURCE);
    localSimpleFunctionDescriptorImpl.initialize(null, getContainingClass().getThisAsReceiverParameter(), CollectionsKt.emptyList(), CollectionsKt.emptyList(), (KotlinType)DescriptorUtilsKt.getBuiltIns((DeclarationDescriptor)getContainingClass()).getAnyType(), Modality.OPEN, Visibilities.PROTECTED);
    return CollectionsKt.listOf(localSimpleFunctionDescriptorImpl);
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    public final Name getCLONE_NAME()
    {
      return CloneableClassScope.access$getCLONE_NAME$cp();
    }
  }
}

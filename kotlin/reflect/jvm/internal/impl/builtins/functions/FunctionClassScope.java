package kotlin.reflect.jvm.internal.impl.builtins.functions;

import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.GivenFunctionsMemberScope;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;

public final class FunctionClassScope
  extends GivenFunctionsMemberScope
{
  public FunctionClassScope(StorageManager paramStorageManager, FunctionClassDescriptor paramFunctionClassDescriptor)
  {
    super(paramStorageManager, (ClassDescriptor)paramFunctionClassDescriptor);
  }
  
  protected List<FunctionDescriptor> computeDeclaredFunctions()
  {
    Object localObject = getContainingClass();
    if (localObject != null)
    {
      localObject = ((FunctionClassDescriptor)localObject).getFunctionKind();
      int i = FunctionClassScope.WhenMappings.$EnumSwitchMapping$0[localObject.ordinal()];
      if (i != 1)
      {
        if (i != 2) {
          localObject = CollectionsKt.emptyList();
        } else {
          localObject = CollectionsKt.listOf(FunctionInvokeDescriptor.Factory.create((FunctionClassDescriptor)getContainingClass(), true));
        }
      }
      else {
        localObject = CollectionsKt.listOf(FunctionInvokeDescriptor.Factory.create((FunctionClassDescriptor)getContainingClass(), false));
      }
      return localObject;
    }
    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.builtins.functions.FunctionClassDescriptor");
  }
}

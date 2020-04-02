package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor.UserDataKey;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Function;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;

public abstract interface ContractDeserializer
{
  public static final Companion Companion = Companion.$$INSTANCE;
  
  public abstract Pair<CallableDescriptor.UserDataKey<?>, Object> deserializeContractFromFunction(ProtoBuf.Function paramFunction, FunctionDescriptor paramFunctionDescriptor, TypeTable paramTypeTable, TypeDeserializer paramTypeDeserializer);
  
  public static final class Companion
  {
    private static final ContractDeserializer DEFAULT = (ContractDeserializer)new ContractDeserializer()
    {
      public Pair deserializeContractFromFunction(ProtoBuf.Function paramAnonymousFunction, FunctionDescriptor paramAnonymousFunctionDescriptor, TypeTable paramAnonymousTypeTable, TypeDeserializer paramAnonymousTypeDeserializer)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunction, "proto");
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionDescriptor, "ownerFunction");
        Intrinsics.checkParameterIsNotNull(paramAnonymousTypeTable, "typeTable");
        Intrinsics.checkParameterIsNotNull(paramAnonymousTypeDeserializer, "typeDeserializer");
        return null;
      }
    };
    
    private Companion() {}
    
    public final ContractDeserializer getDEFAULT()
    {
      return DEFAULT;
    }
  }
}

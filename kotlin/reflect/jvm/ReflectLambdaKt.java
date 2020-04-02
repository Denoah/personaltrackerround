package kotlin.reflect.jvm;

import kotlin.Function;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KFunction;
import kotlin.reflect.jvm.internal.EmptyContainerForLocal;
import kotlin.reflect.jvm.internal.KDeclarationContainerImpl;
import kotlin.reflect.jvm.internal.KFunctionImpl;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Function;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMetadataVersion;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmNameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmProtoBufUtil;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer;

@Metadata(bv={1, 0, 3}, d1={"\000\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\032\036\020\000\032\n\022\004\022\002H\002\030\0010\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\003?\006\004"}, d2={"reflect", "Lkotlin/reflect/KFunction;", "R", "Lkotlin/Function;", "kotlin-reflection"}, k=2, mv={1, 1, 15})
public final class ReflectLambdaKt
{
  public static final <R> KFunction<R> reflect(Function<? extends R> paramFunction)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction, "$this$reflect");
    Object localObject1 = (Metadata)paramFunction.getClass().getAnnotation(Metadata.class);
    if (localObject1 != null)
    {
      Object localObject2 = ((Metadata)localObject1).d1();
      int i = localObject2.length;
      boolean bool = true;
      if (i == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        localObject2 = null;
      }
      if (localObject2 != null)
      {
        localObject2 = JvmProtoBufUtil.readFunctionDataFrom((String[])localObject2, ((Metadata)localObject1).d2());
        Object localObject3 = (JvmNameResolver)((Pair)localObject2).component1();
        localObject2 = (ProtoBuf.Function)((Pair)localObject2).component2();
        Object localObject4 = ((Metadata)localObject1).mv();
        if ((((Metadata)localObject1).xi() & 0x8) == 0) {
          bool = false;
        }
        localObject1 = new JvmMetadataVersion((int[])localObject4, bool);
        paramFunction = paramFunction.getClass();
        localObject4 = (MessageLite)localObject2;
        localObject3 = (NameResolver)localObject3;
        localObject2 = ((ProtoBuf.Function)localObject2).getTypeTable();
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "proto.typeTable");
        paramFunction = (SimpleFunctionDescriptor)UtilKt.deserializeToDescriptor(paramFunction, (MessageLite)localObject4, (NameResolver)localObject3, new TypeTable((ProtoBuf.TypeTable)localObject2), (BinaryVersion)localObject1, (Function2)reflect.descriptor.1.INSTANCE);
        if (paramFunction != null) {
          return (KFunction)new KFunctionImpl((KDeclarationContainerImpl)EmptyContainerForLocal.INSTANCE, (FunctionDescriptor)paramFunction);
        }
      }
    }
    return null;
  }
}

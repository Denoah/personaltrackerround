package kotlin.reflect.jvm.internal.impl.resolve.constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;

public final class ConstantValueFactory
{
  public static final ConstantValueFactory INSTANCE = new ConstantValueFactory();
  
  private ConstantValueFactory() {}
  
  private final ArrayValue createArrayValue(List<?> paramList, PrimitiveType paramPrimitiveType)
  {
    Object localObject1 = (Iterable)CollectionsKt.toList((Iterable)paramList);
    paramList = (Collection)new ArrayList();
    localObject1 = ((Iterable)localObject1).iterator();
    while (((Iterator)localObject1).hasNext())
    {
      Object localObject2 = ((Iterator)localObject1).next();
      localObject2 = ((ConstantValueFactory)this).createConstantValue(localObject2);
      if (localObject2 != null) {
        paramList.add(localObject2);
      }
    }
    new ArrayValue((List)paramList, (Function1)new Lambda(paramPrimitiveType)
    {
      public final SimpleType invoke(ModuleDescriptor paramAnonymousModuleDescriptor)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousModuleDescriptor, "module");
        paramAnonymousModuleDescriptor = paramAnonymousModuleDescriptor.getBuiltIns().getPrimitiveArrayKotlinType(this.$componentType);
        Intrinsics.checkExpressionValueIsNotNull(paramAnonymousModuleDescriptor, "module.builtIns.getPrimi…KotlinType(componentType)");
        return paramAnonymousModuleDescriptor;
      }
    });
  }
  
  public final ArrayValue createArrayValue(List<? extends ConstantValue<?>> paramList, KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "value");
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "type");
    new ArrayValue(paramList, (Function1)new Lambda(paramKotlinType)
    {
      public final KotlinType invoke(ModuleDescriptor paramAnonymousModuleDescriptor)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousModuleDescriptor, "it");
        return this.$type;
      }
    });
  }
  
  public final ConstantValue<?> createConstantValue(Object paramObject)
  {
    if ((paramObject instanceof Byte)) {
      paramObject = (ConstantValue)new ByteValue(((Number)paramObject).byteValue());
    } else if ((paramObject instanceof Short)) {
      paramObject = (ConstantValue)new ShortValue(((Number)paramObject).shortValue());
    } else if ((paramObject instanceof Integer)) {
      paramObject = (ConstantValue)new IntValue(((Number)paramObject).intValue());
    } else if ((paramObject instanceof Long)) {
      paramObject = (ConstantValue)new LongValue(((Number)paramObject).longValue());
    } else if ((paramObject instanceof Character)) {
      paramObject = (ConstantValue)new CharValue(((Character)paramObject).charValue());
    } else if ((paramObject instanceof Float)) {
      paramObject = (ConstantValue)new FloatValue(((Number)paramObject).floatValue());
    } else if ((paramObject instanceof Double)) {
      paramObject = (ConstantValue)new DoubleValue(((Number)paramObject).doubleValue());
    } else if ((paramObject instanceof Boolean)) {
      paramObject = (ConstantValue)new BooleanValue(((Boolean)paramObject).booleanValue());
    } else if ((paramObject instanceof String)) {
      paramObject = (ConstantValue)new StringValue((String)paramObject);
    } else if ((paramObject instanceof byte[])) {
      paramObject = (ConstantValue)createArrayValue(ArraysKt.toList((byte[])paramObject), PrimitiveType.BYTE);
    } else if ((paramObject instanceof short[])) {
      paramObject = (ConstantValue)createArrayValue(ArraysKt.toList((short[])paramObject), PrimitiveType.SHORT);
    } else if ((paramObject instanceof int[])) {
      paramObject = (ConstantValue)createArrayValue(ArraysKt.toList((int[])paramObject), PrimitiveType.INT);
    } else if ((paramObject instanceof long[])) {
      paramObject = (ConstantValue)createArrayValue(ArraysKt.toList((long[])paramObject), PrimitiveType.LONG);
    } else if ((paramObject instanceof char[])) {
      paramObject = (ConstantValue)createArrayValue(ArraysKt.toList((char[])paramObject), PrimitiveType.CHAR);
    } else if ((paramObject instanceof float[])) {
      paramObject = (ConstantValue)createArrayValue(ArraysKt.toList((float[])paramObject), PrimitiveType.FLOAT);
    } else if ((paramObject instanceof double[])) {
      paramObject = (ConstantValue)createArrayValue(ArraysKt.toList((double[])paramObject), PrimitiveType.DOUBLE);
    } else if ((paramObject instanceof boolean[])) {
      paramObject = (ConstantValue)createArrayValue(ArraysKt.toList((boolean[])paramObject), PrimitiveType.BOOLEAN);
    } else if (paramObject == null) {
      paramObject = (ConstantValue)new NullValue();
    } else {
      paramObject = null;
    }
    return paramObject;
  }
}

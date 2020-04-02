package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;

public abstract interface FlexibleTypeDeserializer
{
  public abstract KotlinType create(ProtoBuf.Type paramType, String paramString, SimpleType paramSimpleType1, SimpleType paramSimpleType2);
  
  public static final class ThrowException
    implements FlexibleTypeDeserializer
  {
    public static final ThrowException INSTANCE = new ThrowException();
    
    private ThrowException() {}
    
    public KotlinType create(ProtoBuf.Type paramType, String paramString, SimpleType paramSimpleType1, SimpleType paramSimpleType2)
    {
      Intrinsics.checkParameterIsNotNull(paramType, "proto");
      Intrinsics.checkParameterIsNotNull(paramString, "flexibleId");
      Intrinsics.checkParameterIsNotNull(paramSimpleType1, "lowerBound");
      Intrinsics.checkParameterIsNotNull(paramSimpleType2, "upperBound");
      throw ((Throwable)new IllegalArgumentException("This method should not be used."));
    }
  }
}

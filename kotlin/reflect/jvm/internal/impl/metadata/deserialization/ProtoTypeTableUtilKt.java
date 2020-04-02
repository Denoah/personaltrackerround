package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Class;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Function;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Property;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type.Argument;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeAlias;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeParameter;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.ValueParameter;

public final class ProtoTypeTableUtilKt
{
  public static final ProtoBuf.Type abbreviatedType(ProtoBuf.Type paramType, TypeTable paramTypeTable)
  {
    Intrinsics.checkParameterIsNotNull(paramType, "$this$abbreviatedType");
    Intrinsics.checkParameterIsNotNull(paramTypeTable, "typeTable");
    if (paramType.hasAbbreviatedType()) {
      paramType = paramType.getAbbreviatedType();
    } else if (paramType.hasAbbreviatedTypeId()) {
      paramType = paramTypeTable.get(paramType.getAbbreviatedTypeId());
    } else {
      paramType = null;
    }
    return paramType;
  }
  
  public static final ProtoBuf.Type expandedType(ProtoBuf.TypeAlias paramTypeAlias, TypeTable paramTypeTable)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeAlias, "$this$expandedType");
    Intrinsics.checkParameterIsNotNull(paramTypeTable, "typeTable");
    if (paramTypeAlias.hasExpandedType())
    {
      paramTypeAlias = paramTypeAlias.getExpandedType();
      Intrinsics.checkExpressionValueIsNotNull(paramTypeAlias, "expandedType");
    }
    else
    {
      if (!paramTypeAlias.hasExpandedTypeId()) {
        break label51;
      }
      paramTypeAlias = paramTypeTable.get(paramTypeAlias.getExpandedTypeId());
    }
    return paramTypeAlias;
    label51:
    throw ((Throwable)new IllegalStateException("No expandedType in ProtoBuf.TypeAlias".toString()));
  }
  
  public static final ProtoBuf.Type flexibleUpperBound(ProtoBuf.Type paramType, TypeTable paramTypeTable)
  {
    Intrinsics.checkParameterIsNotNull(paramType, "$this$flexibleUpperBound");
    Intrinsics.checkParameterIsNotNull(paramTypeTable, "typeTable");
    if (paramType.hasFlexibleUpperBound()) {
      paramType = paramType.getFlexibleUpperBound();
    } else if (paramType.hasFlexibleUpperBoundId()) {
      paramType = paramTypeTable.get(paramType.getFlexibleUpperBoundId());
    } else {
      paramType = null;
    }
    return paramType;
  }
  
  public static final boolean hasReceiver(ProtoBuf.Function paramFunction)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction, "$this$hasReceiver");
    boolean bool;
    if ((!paramFunction.hasReceiverType()) && (!paramFunction.hasReceiverTypeId())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static final boolean hasReceiver(ProtoBuf.Property paramProperty)
  {
    Intrinsics.checkParameterIsNotNull(paramProperty, "$this$hasReceiver");
    boolean bool;
    if ((!paramProperty.hasReceiverType()) && (!paramProperty.hasReceiverTypeId())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static final ProtoBuf.Type outerType(ProtoBuf.Type paramType, TypeTable paramTypeTable)
  {
    Intrinsics.checkParameterIsNotNull(paramType, "$this$outerType");
    Intrinsics.checkParameterIsNotNull(paramTypeTable, "typeTable");
    if (paramType.hasOuterType()) {
      paramType = paramType.getOuterType();
    } else if (paramType.hasOuterTypeId()) {
      paramType = paramTypeTable.get(paramType.getOuterTypeId());
    } else {
      paramType = null;
    }
    return paramType;
  }
  
  public static final ProtoBuf.Type receiverType(ProtoBuf.Function paramFunction, TypeTable paramTypeTable)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction, "$this$receiverType");
    Intrinsics.checkParameterIsNotNull(paramTypeTable, "typeTable");
    if (paramFunction.hasReceiverType()) {
      paramFunction = paramFunction.getReceiverType();
    } else if (paramFunction.hasReceiverTypeId()) {
      paramFunction = paramTypeTable.get(paramFunction.getReceiverTypeId());
    } else {
      paramFunction = null;
    }
    return paramFunction;
  }
  
  public static final ProtoBuf.Type receiverType(ProtoBuf.Property paramProperty, TypeTable paramTypeTable)
  {
    Intrinsics.checkParameterIsNotNull(paramProperty, "$this$receiverType");
    Intrinsics.checkParameterIsNotNull(paramTypeTable, "typeTable");
    if (paramProperty.hasReceiverType()) {
      paramProperty = paramProperty.getReceiverType();
    } else if (paramProperty.hasReceiverTypeId()) {
      paramProperty = paramTypeTable.get(paramProperty.getReceiverTypeId());
    } else {
      paramProperty = null;
    }
    return paramProperty;
  }
  
  public static final ProtoBuf.Type returnType(ProtoBuf.Function paramFunction, TypeTable paramTypeTable)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction, "$this$returnType");
    Intrinsics.checkParameterIsNotNull(paramTypeTable, "typeTable");
    if (paramFunction.hasReturnType())
    {
      paramFunction = paramFunction.getReturnType();
      Intrinsics.checkExpressionValueIsNotNull(paramFunction, "returnType");
    }
    else
    {
      if (!paramFunction.hasReturnTypeId()) {
        break label51;
      }
      paramFunction = paramTypeTable.get(paramFunction.getReturnTypeId());
    }
    return paramFunction;
    label51:
    throw ((Throwable)new IllegalStateException("No returnType in ProtoBuf.Function".toString()));
  }
  
  public static final ProtoBuf.Type returnType(ProtoBuf.Property paramProperty, TypeTable paramTypeTable)
  {
    Intrinsics.checkParameterIsNotNull(paramProperty, "$this$returnType");
    Intrinsics.checkParameterIsNotNull(paramTypeTable, "typeTable");
    if (paramProperty.hasReturnType())
    {
      paramProperty = paramProperty.getReturnType();
      Intrinsics.checkExpressionValueIsNotNull(paramProperty, "returnType");
    }
    else
    {
      if (!paramProperty.hasReturnTypeId()) {
        break label51;
      }
      paramProperty = paramTypeTable.get(paramProperty.getReturnTypeId());
    }
    return paramProperty;
    label51:
    throw ((Throwable)new IllegalStateException("No returnType in ProtoBuf.Property".toString()));
  }
  
  public static final List<ProtoBuf.Type> supertypes(ProtoBuf.Class paramClass, TypeTable paramTypeTable)
  {
    Intrinsics.checkParameterIsNotNull(paramClass, "$this$supertypes");
    Intrinsics.checkParameterIsNotNull(paramTypeTable, "typeTable");
    Object localObject = paramClass.getSupertypeList();
    if (!(((Collection)localObject).isEmpty() ^ true)) {
      localObject = null;
    }
    if (localObject == null)
    {
      paramClass = paramClass.getSupertypeIdList();
      Intrinsics.checkExpressionValueIsNotNull(paramClass, "supertypeIdList");
      localObject = (Iterable)paramClass;
      paramClass = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject, 10));
      localObject = ((Iterable)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        Integer localInteger = (Integer)((Iterator)localObject).next();
        Intrinsics.checkExpressionValueIsNotNull(localInteger, "it");
        paramClass.add(paramTypeTable.get(localInteger.intValue()));
      }
      localObject = (List)paramClass;
    }
    return localObject;
  }
  
  public static final ProtoBuf.Type type(ProtoBuf.Type.Argument paramArgument, TypeTable paramTypeTable)
  {
    Intrinsics.checkParameterIsNotNull(paramArgument, "$this$type");
    Intrinsics.checkParameterIsNotNull(paramTypeTable, "typeTable");
    if (paramArgument.hasType()) {
      paramArgument = paramArgument.getType();
    } else if (paramArgument.hasTypeId()) {
      paramArgument = paramTypeTable.get(paramArgument.getTypeId());
    } else {
      paramArgument = null;
    }
    return paramArgument;
  }
  
  public static final ProtoBuf.Type type(ProtoBuf.ValueParameter paramValueParameter, TypeTable paramTypeTable)
  {
    Intrinsics.checkParameterIsNotNull(paramValueParameter, "$this$type");
    Intrinsics.checkParameterIsNotNull(paramTypeTable, "typeTable");
    if (paramValueParameter.hasType())
    {
      paramValueParameter = paramValueParameter.getType();
      Intrinsics.checkExpressionValueIsNotNull(paramValueParameter, "type");
    }
    else
    {
      if (!paramValueParameter.hasTypeId()) {
        break label51;
      }
      paramValueParameter = paramTypeTable.get(paramValueParameter.getTypeId());
    }
    return paramValueParameter;
    label51:
    throw ((Throwable)new IllegalStateException("No type in ProtoBuf.ValueParameter".toString()));
  }
  
  public static final ProtoBuf.Type underlyingType(ProtoBuf.TypeAlias paramTypeAlias, TypeTable paramTypeTable)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeAlias, "$this$underlyingType");
    Intrinsics.checkParameterIsNotNull(paramTypeTable, "typeTable");
    if (paramTypeAlias.hasUnderlyingType())
    {
      paramTypeAlias = paramTypeAlias.getUnderlyingType();
      Intrinsics.checkExpressionValueIsNotNull(paramTypeAlias, "underlyingType");
    }
    else
    {
      if (!paramTypeAlias.hasUnderlyingTypeId()) {
        break label52;
      }
      paramTypeAlias = paramTypeTable.get(paramTypeAlias.getUnderlyingTypeId());
    }
    return paramTypeAlias;
    label52:
    throw ((Throwable)new IllegalStateException("No underlyingType in ProtoBuf.TypeAlias".toString()));
  }
  
  public static final List<ProtoBuf.Type> upperBounds(ProtoBuf.TypeParameter paramTypeParameter, TypeTable paramTypeTable)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeParameter, "$this$upperBounds");
    Intrinsics.checkParameterIsNotNull(paramTypeTable, "typeTable");
    Object localObject = paramTypeParameter.getUpperBoundList();
    if (!(((Collection)localObject).isEmpty() ^ true)) {
      localObject = null;
    }
    if (localObject == null)
    {
      paramTypeParameter = paramTypeParameter.getUpperBoundIdList();
      Intrinsics.checkExpressionValueIsNotNull(paramTypeParameter, "upperBoundIdList");
      localObject = (Iterable)paramTypeParameter;
      paramTypeParameter = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject, 10));
      Iterator localIterator = ((Iterable)localObject).iterator();
      while (localIterator.hasNext())
      {
        localObject = (Integer)localIterator.next();
        Intrinsics.checkExpressionValueIsNotNull(localObject, "it");
        paramTypeParameter.add(paramTypeTable.get(((Integer)localObject).intValue()));
      }
      localObject = (List)paramTypeParameter;
    }
    return localObject;
  }
  
  public static final ProtoBuf.Type varargElementType(ProtoBuf.ValueParameter paramValueParameter, TypeTable paramTypeTable)
  {
    Intrinsics.checkParameterIsNotNull(paramValueParameter, "$this$varargElementType");
    Intrinsics.checkParameterIsNotNull(paramTypeTable, "typeTable");
    if (paramValueParameter.hasVarargElementType()) {
      paramValueParameter = paramValueParameter.getVarargElementType();
    } else if (paramValueParameter.hasVarargElementTypeId()) {
      paramValueParameter = paramTypeTable.get(paramValueParameter.getVarargElementTypeId());
    } else {
      paramValueParameter = null;
    }
    return paramValueParameter;
  }
}

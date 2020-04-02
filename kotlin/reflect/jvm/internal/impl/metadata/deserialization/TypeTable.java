package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type.Builder;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeTable;

public final class TypeTable
{
  private final List<ProtoBuf.Type> types;
  
  public TypeTable(ProtoBuf.TypeTable paramTypeTable)
  {
    Object localObject = (TypeTable)this;
    localObject = paramTypeTable.getTypeList();
    if (paramTypeTable.hasFirstNullable())
    {
      int i = paramTypeTable.getFirstNullable();
      paramTypeTable = paramTypeTable.getTypeList();
      Intrinsics.checkExpressionValueIsNotNull(paramTypeTable, "typeTable.typeList");
      paramTypeTable = (Iterable)paramTypeTable;
      Collection localCollection = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault(paramTypeTable, 10));
      int j = 0;
      Iterator localIterator = paramTypeTable.iterator();
      while (localIterator.hasNext())
      {
        paramTypeTable = localIterator.next();
        if (j < 0) {
          CollectionsKt.throwIndexOverflow();
        }
        localObject = (ProtoBuf.Type)paramTypeTable;
        paramTypeTable = (ProtoBuf.TypeTable)localObject;
        if (j >= i) {
          paramTypeTable = ((ProtoBuf.Type)localObject).toBuilder().setNullable(true).build();
        }
        localCollection.add(paramTypeTable);
        j++;
      }
      paramTypeTable = (List)localCollection;
    }
    else
    {
      Intrinsics.checkExpressionValueIsNotNull(localObject, "originalTypes");
      paramTypeTable = (ProtoBuf.TypeTable)localObject;
    }
    this.types = paramTypeTable;
  }
  
  public final ProtoBuf.Type get(int paramInt)
  {
    return (ProtoBuf.Type)this.types.get(paramInt);
  }
}

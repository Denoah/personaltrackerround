package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

final class FieldSet<FieldDescriptorType extends FieldDescriptorLite<FieldDescriptorType>>
{
  private static final FieldSet DEFAULT_INSTANCE = new FieldSet(true);
  private final SmallSortedMap<FieldDescriptorType, Object> fields;
  private boolean hasLazyField = false;
  private boolean isImmutable;
  
  private FieldSet()
  {
    this.fields = SmallSortedMap.newFieldMap(16);
  }
  
  private FieldSet(boolean paramBoolean)
  {
    this.fields = SmallSortedMap.newFieldMap(0);
    makeImmutable();
  }
  
  private Object cloneIfMutable(Object paramObject)
  {
    if ((paramObject instanceof byte[]))
    {
      byte[] arrayOfByte = (byte[])paramObject;
      paramObject = new byte[arrayOfByte.length];
      System.arraycopy(arrayOfByte, 0, paramObject, 0, arrayOfByte.length);
      return paramObject;
    }
    return paramObject;
  }
  
  private static int computeElementSize(WireFormat.FieldType paramFieldType, int paramInt, Object paramObject)
  {
    int i = CodedOutputStream.computeTagSize(paramInt);
    paramInt = i;
    if (paramFieldType == WireFormat.FieldType.GROUP) {
      paramInt = i * 2;
    }
    return paramInt + computeElementSizeNoTag(paramFieldType, paramObject);
  }
  
  private static int computeElementSizeNoTag(WireFormat.FieldType paramFieldType, Object paramObject)
  {
    switch (1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[paramFieldType.ordinal()])
    {
    default: 
      throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
    case 18: 
      if ((paramObject instanceof Internal.EnumLite)) {
        return CodedOutputStream.computeEnumSizeNoTag(((Internal.EnumLite)paramObject).getNumber());
      }
      return CodedOutputStream.computeEnumSizeNoTag(((Integer)paramObject).intValue());
    case 17: 
      if ((paramObject instanceof LazyField)) {
        return CodedOutputStream.computeLazyFieldSizeNoTag((LazyField)paramObject);
      }
      return CodedOutputStream.computeMessageSizeNoTag((MessageLite)paramObject);
    case 16: 
      return CodedOutputStream.computeGroupSizeNoTag((MessageLite)paramObject);
    case 15: 
      return CodedOutputStream.computeSInt64SizeNoTag(((Long)paramObject).longValue());
    case 14: 
      return CodedOutputStream.computeSInt32SizeNoTag(((Integer)paramObject).intValue());
    case 13: 
      return CodedOutputStream.computeSFixed64SizeNoTag(((Long)paramObject).longValue());
    case 12: 
      return CodedOutputStream.computeSFixed32SizeNoTag(((Integer)paramObject).intValue());
    case 11: 
      return CodedOutputStream.computeUInt32SizeNoTag(((Integer)paramObject).intValue());
    case 10: 
      if ((paramObject instanceof ByteString)) {
        return CodedOutputStream.computeBytesSizeNoTag((ByteString)paramObject);
      }
      return CodedOutputStream.computeByteArraySizeNoTag((byte[])paramObject);
    case 9: 
      return CodedOutputStream.computeStringSizeNoTag((String)paramObject);
    case 8: 
      return CodedOutputStream.computeBoolSizeNoTag(((Boolean)paramObject).booleanValue());
    case 7: 
      return CodedOutputStream.computeFixed32SizeNoTag(((Integer)paramObject).intValue());
    case 6: 
      return CodedOutputStream.computeFixed64SizeNoTag(((Long)paramObject).longValue());
    case 5: 
      return CodedOutputStream.computeInt32SizeNoTag(((Integer)paramObject).intValue());
    case 4: 
      return CodedOutputStream.computeUInt64SizeNoTag(((Long)paramObject).longValue());
    case 3: 
      return CodedOutputStream.computeInt64SizeNoTag(((Long)paramObject).longValue());
    case 2: 
      return CodedOutputStream.computeFloatSizeNoTag(((Float)paramObject).floatValue());
    }
    return CodedOutputStream.computeDoubleSizeNoTag(((Double)paramObject).doubleValue());
  }
  
  public static int computeFieldSize(FieldDescriptorLite<?> paramFieldDescriptorLite, Object paramObject)
  {
    WireFormat.FieldType localFieldType = paramFieldDescriptorLite.getLiteType();
    int i = paramFieldDescriptorLite.getNumber();
    if (paramFieldDescriptorLite.isRepeated())
    {
      boolean bool = paramFieldDescriptorLite.isPacked();
      int j = 0;
      int k = 0;
      if (bool)
      {
        paramFieldDescriptorLite = ((List)paramObject).iterator();
        while (paramFieldDescriptorLite.hasNext()) {
          k += computeElementSizeNoTag(localFieldType, paramFieldDescriptorLite.next());
        }
        return CodedOutputStream.computeTagSize(i) + k + CodedOutputStream.computeRawVarint32Size(k);
      }
      paramFieldDescriptorLite = ((List)paramObject).iterator();
      k = j;
      while (paramFieldDescriptorLite.hasNext()) {
        k += computeElementSize(localFieldType, i, paramFieldDescriptorLite.next());
      }
      return k;
    }
    return computeElementSize(localFieldType, i, paramObject);
  }
  
  public static <T extends FieldDescriptorLite<T>> FieldSet<T> emptySet()
  {
    return DEFAULT_INSTANCE;
  }
  
  static int getWireFormatForFieldType(WireFormat.FieldType paramFieldType, boolean paramBoolean)
  {
    if (paramBoolean) {
      return 2;
    }
    return paramFieldType.getWireType();
  }
  
  private boolean isInitialized(Map.Entry<FieldDescriptorType, Object> paramEntry)
  {
    FieldDescriptorLite localFieldDescriptorLite = (FieldDescriptorLite)paramEntry.getKey();
    if (localFieldDescriptorLite.getLiteJavaType() == WireFormat.JavaType.MESSAGE)
    {
      if (localFieldDescriptorLite.isRepeated())
      {
        paramEntry = ((List)paramEntry.getValue()).iterator();
        do
        {
          if (!paramEntry.hasNext()) {
            break;
          }
        } while (((MessageLite)paramEntry.next()).isInitialized());
        return false;
      }
      paramEntry = paramEntry.getValue();
      if ((paramEntry instanceof MessageLite))
      {
        if (!((MessageLite)paramEntry).isInitialized()) {
          return false;
        }
      }
      else
      {
        if ((paramEntry instanceof LazyField)) {
          return true;
        }
        throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
      }
    }
    return true;
  }
  
  private void mergeFromField(Map.Entry<FieldDescriptorType, Object> paramEntry)
  {
    FieldDescriptorLite localFieldDescriptorLite = (FieldDescriptorLite)paramEntry.getKey();
    Object localObject1 = paramEntry.getValue();
    paramEntry = (Map.Entry<FieldDescriptorType, Object>)localObject1;
    if ((localObject1 instanceof LazyField)) {
      paramEntry = ((LazyField)localObject1).getValue();
    }
    if (localFieldDescriptorLite.isRepeated())
    {
      Object localObject2 = getField(localFieldDescriptorLite);
      localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = new ArrayList();
      }
      paramEntry = ((List)paramEntry).iterator();
      while (paramEntry.hasNext())
      {
        localObject2 = paramEntry.next();
        ((List)localObject1).add(cloneIfMutable(localObject2));
      }
      this.fields.put(localFieldDescriptorLite, localObject1);
    }
    else if (localFieldDescriptorLite.getLiteJavaType() == WireFormat.JavaType.MESSAGE)
    {
      localObject1 = getField(localFieldDescriptorLite);
      if (localObject1 == null)
      {
        this.fields.put(localFieldDescriptorLite, cloneIfMutable(paramEntry));
      }
      else
      {
        paramEntry = localFieldDescriptorLite.internalMergeFrom(((MessageLite)localObject1).toBuilder(), (MessageLite)paramEntry).build();
        this.fields.put(localFieldDescriptorLite, paramEntry);
      }
    }
    else
    {
      this.fields.put(localFieldDescriptorLite, cloneIfMutable(paramEntry));
    }
  }
  
  public static <T extends FieldDescriptorLite<T>> FieldSet<T> newFieldSet()
  {
    return new FieldSet();
  }
  
  public static Object readPrimitiveField(CodedInputStream paramCodedInputStream, WireFormat.FieldType paramFieldType, boolean paramBoolean)
    throws IOException
  {
    switch (1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[paramFieldType.ordinal()])
    {
    default: 
      throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
    case 18: 
      throw new IllegalArgumentException("readPrimitiveField() cannot handle enums.");
    case 17: 
      throw new IllegalArgumentException("readPrimitiveField() cannot handle embedded messages.");
    case 16: 
      throw new IllegalArgumentException("readPrimitiveField() cannot handle nested groups.");
    case 15: 
      return Long.valueOf(paramCodedInputStream.readSInt64());
    case 14: 
      return Integer.valueOf(paramCodedInputStream.readSInt32());
    case 13: 
      return Long.valueOf(paramCodedInputStream.readSFixed64());
    case 12: 
      return Integer.valueOf(paramCodedInputStream.readSFixed32());
    case 11: 
      return Integer.valueOf(paramCodedInputStream.readUInt32());
    case 10: 
      return paramCodedInputStream.readBytes();
    case 9: 
      if (paramBoolean) {
        return paramCodedInputStream.readStringRequireUtf8();
      }
      return paramCodedInputStream.readString();
    case 8: 
      return Boolean.valueOf(paramCodedInputStream.readBool());
    case 7: 
      return Integer.valueOf(paramCodedInputStream.readFixed32());
    case 6: 
      return Long.valueOf(paramCodedInputStream.readFixed64());
    case 5: 
      return Integer.valueOf(paramCodedInputStream.readInt32());
    case 4: 
      return Long.valueOf(paramCodedInputStream.readUInt64());
    case 3: 
      return Long.valueOf(paramCodedInputStream.readInt64());
    case 2: 
      return Float.valueOf(paramCodedInputStream.readFloat());
    }
    return Double.valueOf(paramCodedInputStream.readDouble());
  }
  
  private static void verifyType(WireFormat.FieldType paramFieldType, Object paramObject)
  {
    if (paramObject != null)
    {
      int i = 1.$SwitchMap$com$google$protobuf$WireFormat$JavaType[paramFieldType.getJavaType().ordinal()];
      boolean bool1 = true;
      boolean bool2 = false;
      switch (i)
      {
      default: 
        break;
      case 9: 
        bool2 = bool1;
        if (!(paramObject instanceof MessageLite)) {
          if ((paramObject instanceof LazyField)) {
            bool2 = bool1;
          }
        }
        break;
      case 8: 
        bool2 = bool1;
        if (!(paramObject instanceof Integer)) {
          if ((paramObject instanceof Internal.EnumLite)) {
            bool2 = bool1;
          }
        }
        break;
      case 7: 
        bool2 = bool1;
        if (!(paramObject instanceof ByteString)) {
          if ((paramObject instanceof byte[])) {
            bool2 = bool1;
          } else {
            bool2 = false;
          }
        }
        break;
      case 6: 
        bool2 = paramObject instanceof String;
        break;
      case 5: 
        bool2 = paramObject instanceof Boolean;
        break;
      case 4: 
        bool2 = paramObject instanceof Double;
        break;
      case 3: 
        bool2 = paramObject instanceof Float;
        break;
      case 2: 
        bool2 = paramObject instanceof Long;
        break;
      }
      bool2 = paramObject instanceof Integer;
      if (bool2) {
        return;
      }
      throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
    }
    throw null;
  }
  
  private static void writeElement(CodedOutputStream paramCodedOutputStream, WireFormat.FieldType paramFieldType, int paramInt, Object paramObject)
    throws IOException
  {
    if (paramFieldType == WireFormat.FieldType.GROUP)
    {
      paramCodedOutputStream.writeGroup(paramInt, (MessageLite)paramObject);
    }
    else
    {
      paramCodedOutputStream.writeTag(paramInt, getWireFormatForFieldType(paramFieldType, false));
      writeElementNoTag(paramCodedOutputStream, paramFieldType, paramObject);
    }
  }
  
  private static void writeElementNoTag(CodedOutputStream paramCodedOutputStream, WireFormat.FieldType paramFieldType, Object paramObject)
    throws IOException
  {
    switch (1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[paramFieldType.ordinal()])
    {
    default: 
      break;
    case 18: 
      if ((paramObject instanceof Internal.EnumLite)) {
        paramCodedOutputStream.writeEnumNoTag(((Internal.EnumLite)paramObject).getNumber());
      } else {
        paramCodedOutputStream.writeEnumNoTag(((Integer)paramObject).intValue());
      }
      break;
    case 17: 
      paramCodedOutputStream.writeMessageNoTag((MessageLite)paramObject);
      break;
    case 16: 
      paramCodedOutputStream.writeGroupNoTag((MessageLite)paramObject);
      break;
    case 15: 
      paramCodedOutputStream.writeSInt64NoTag(((Long)paramObject).longValue());
      break;
    case 14: 
      paramCodedOutputStream.writeSInt32NoTag(((Integer)paramObject).intValue());
      break;
    case 13: 
      paramCodedOutputStream.writeSFixed64NoTag(((Long)paramObject).longValue());
      break;
    case 12: 
      paramCodedOutputStream.writeSFixed32NoTag(((Integer)paramObject).intValue());
      break;
    case 11: 
      paramCodedOutputStream.writeUInt32NoTag(((Integer)paramObject).intValue());
      break;
    case 10: 
      if ((paramObject instanceof ByteString)) {
        paramCodedOutputStream.writeBytesNoTag((ByteString)paramObject);
      } else {
        paramCodedOutputStream.writeByteArrayNoTag((byte[])paramObject);
      }
      break;
    case 9: 
      paramCodedOutputStream.writeStringNoTag((String)paramObject);
      break;
    case 8: 
      paramCodedOutputStream.writeBoolNoTag(((Boolean)paramObject).booleanValue());
      break;
    case 7: 
      paramCodedOutputStream.writeFixed32NoTag(((Integer)paramObject).intValue());
      break;
    case 6: 
      paramCodedOutputStream.writeFixed64NoTag(((Long)paramObject).longValue());
      break;
    case 5: 
      paramCodedOutputStream.writeInt32NoTag(((Integer)paramObject).intValue());
      break;
    case 4: 
      paramCodedOutputStream.writeUInt64NoTag(((Long)paramObject).longValue());
      break;
    case 3: 
      paramCodedOutputStream.writeInt64NoTag(((Long)paramObject).longValue());
      break;
    case 2: 
      paramCodedOutputStream.writeFloatNoTag(((Float)paramObject).floatValue());
      break;
    case 1: 
      paramCodedOutputStream.writeDoubleNoTag(((Double)paramObject).doubleValue());
    }
  }
  
  public static void writeField(FieldDescriptorLite<?> paramFieldDescriptorLite, Object paramObject, CodedOutputStream paramCodedOutputStream)
    throws IOException
  {
    WireFormat.FieldType localFieldType = paramFieldDescriptorLite.getLiteType();
    int i = paramFieldDescriptorLite.getNumber();
    if (paramFieldDescriptorLite.isRepeated())
    {
      paramObject = (List)paramObject;
      if (paramFieldDescriptorLite.isPacked())
      {
        paramCodedOutputStream.writeTag(i, 2);
        i = 0;
        paramFieldDescriptorLite = paramObject.iterator();
        while (paramFieldDescriptorLite.hasNext()) {
          i += computeElementSizeNoTag(localFieldType, paramFieldDescriptorLite.next());
        }
        paramCodedOutputStream.writeRawVarint32(i);
        paramFieldDescriptorLite = paramObject.iterator();
        while (paramFieldDescriptorLite.hasNext()) {
          writeElementNoTag(paramCodedOutputStream, localFieldType, paramFieldDescriptorLite.next());
        }
      }
      paramFieldDescriptorLite = paramObject.iterator();
      while (paramFieldDescriptorLite.hasNext()) {
        writeElement(paramCodedOutputStream, localFieldType, i, paramFieldDescriptorLite.next());
      }
    }
    if ((paramObject instanceof LazyField)) {
      writeElement(paramCodedOutputStream, localFieldType, i, ((LazyField)paramObject).getValue());
    } else {
      writeElement(paramCodedOutputStream, localFieldType, i, paramObject);
    }
  }
  
  public void addRepeatedField(FieldDescriptorType paramFieldDescriptorType, Object paramObject)
  {
    if (paramFieldDescriptorType.isRepeated())
    {
      verifyType(paramFieldDescriptorType.getLiteType(), paramObject);
      Object localObject = getField(paramFieldDescriptorType);
      if (localObject == null)
      {
        localObject = new ArrayList();
        this.fields.put(paramFieldDescriptorType, localObject);
        paramFieldDescriptorType = (TFieldDescriptorType)localObject;
      }
      else
      {
        paramFieldDescriptorType = (List)localObject;
      }
      paramFieldDescriptorType.add(paramObject);
      return;
    }
    throw new IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
  }
  
  public FieldSet<FieldDescriptorType> clone()
  {
    FieldSet localFieldSet = newFieldSet();
    Map.Entry localEntry;
    for (int i = 0; i < this.fields.getNumArrayEntries(); i++)
    {
      localEntry = this.fields.getArrayEntryAt(i);
      localFieldSet.setField((FieldDescriptorLite)localEntry.getKey(), localEntry.getValue());
    }
    Iterator localIterator = this.fields.getOverflowEntries().iterator();
    while (localIterator.hasNext())
    {
      localEntry = (Map.Entry)localIterator.next();
      localFieldSet.setField((FieldDescriptorLite)localEntry.getKey(), localEntry.getValue());
    }
    localFieldSet.hasLazyField = this.hasLazyField;
    return localFieldSet;
  }
  
  public Object getField(FieldDescriptorType paramFieldDescriptorType)
  {
    Object localObject = this.fields.get(paramFieldDescriptorType);
    paramFieldDescriptorType = localObject;
    if ((localObject instanceof LazyField)) {
      paramFieldDescriptorType = ((LazyField)localObject).getValue();
    }
    return paramFieldDescriptorType;
  }
  
  public Object getRepeatedField(FieldDescriptorType paramFieldDescriptorType, int paramInt)
  {
    if (paramFieldDescriptorType.isRepeated())
    {
      paramFieldDescriptorType = getField(paramFieldDescriptorType);
      if (paramFieldDescriptorType != null) {
        return ((List)paramFieldDescriptorType).get(paramInt);
      }
      throw new IndexOutOfBoundsException();
    }
    throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
  }
  
  public int getRepeatedFieldCount(FieldDescriptorType paramFieldDescriptorType)
  {
    if (paramFieldDescriptorType.isRepeated())
    {
      paramFieldDescriptorType = getField(paramFieldDescriptorType);
      if (paramFieldDescriptorType == null) {
        return 0;
      }
      return ((List)paramFieldDescriptorType).size();
    }
    throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
  }
  
  public int getSerializedSize()
  {
    int i = 0;
    int j = 0;
    Map.Entry localEntry;
    while (i < this.fields.getNumArrayEntries())
    {
      localEntry = this.fields.getArrayEntryAt(i);
      j += computeFieldSize((FieldDescriptorLite)localEntry.getKey(), localEntry.getValue());
      i++;
    }
    Iterator localIterator = this.fields.getOverflowEntries().iterator();
    while (localIterator.hasNext())
    {
      localEntry = (Map.Entry)localIterator.next();
      j += computeFieldSize((FieldDescriptorLite)localEntry.getKey(), localEntry.getValue());
    }
    return j;
  }
  
  public boolean hasField(FieldDescriptorType paramFieldDescriptorType)
  {
    if (!paramFieldDescriptorType.isRepeated())
    {
      boolean bool;
      if (this.fields.get(paramFieldDescriptorType) != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    throw new IllegalArgumentException("hasField() can only be called on non-repeated fields.");
  }
  
  public boolean isInitialized()
  {
    for (int i = 0; i < this.fields.getNumArrayEntries(); i++) {
      if (!isInitialized(this.fields.getArrayEntryAt(i))) {
        return false;
      }
    }
    Iterator localIterator = this.fields.getOverflowEntries().iterator();
    while (localIterator.hasNext()) {
      if (!isInitialized((Map.Entry)localIterator.next())) {
        return false;
      }
    }
    return true;
  }
  
  public Iterator<Map.Entry<FieldDescriptorType, Object>> iterator()
  {
    if (this.hasLazyField) {
      return new LazyField.LazyIterator(this.fields.entrySet().iterator());
    }
    return this.fields.entrySet().iterator();
  }
  
  public void makeImmutable()
  {
    if (this.isImmutable) {
      return;
    }
    this.fields.makeImmutable();
    this.isImmutable = true;
  }
  
  public void mergeFrom(FieldSet<FieldDescriptorType> paramFieldSet)
  {
    for (int i = 0; i < paramFieldSet.fields.getNumArrayEntries(); i++) {
      mergeFromField(paramFieldSet.fields.getArrayEntryAt(i));
    }
    paramFieldSet = paramFieldSet.fields.getOverflowEntries().iterator();
    while (paramFieldSet.hasNext()) {
      mergeFromField((Map.Entry)paramFieldSet.next());
    }
  }
  
  public void setField(FieldDescriptorType paramFieldDescriptorType, Object paramObject)
  {
    if (paramFieldDescriptorType.isRepeated())
    {
      if ((paramObject instanceof List))
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.addAll((List)paramObject);
        Iterator localIterator = localArrayList.iterator();
        while (localIterator.hasNext())
        {
          paramObject = localIterator.next();
          verifyType(paramFieldDescriptorType.getLiteType(), paramObject);
        }
        paramObject = localArrayList;
      }
      else
      {
        throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
      }
    }
    else {
      verifyType(paramFieldDescriptorType.getLiteType(), paramObject);
    }
    if ((paramObject instanceof LazyField)) {
      this.hasLazyField = true;
    }
    this.fields.put(paramFieldDescriptorType, paramObject);
  }
  
  public static abstract interface FieldDescriptorLite<T extends FieldDescriptorLite<T>>
    extends Comparable<T>
  {
    public abstract WireFormat.JavaType getLiteJavaType();
    
    public abstract WireFormat.FieldType getLiteType();
    
    public abstract int getNumber();
    
    public abstract MessageLite.Builder internalMergeFrom(MessageLite.Builder paramBuilder, MessageLite paramMessageLite);
    
    public abstract boolean isPacked();
    
    public abstract boolean isRepeated();
  }
}

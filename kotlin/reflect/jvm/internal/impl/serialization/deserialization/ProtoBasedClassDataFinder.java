package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Class;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.PackageFragment;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.name.ClassId;

public final class ProtoBasedClassDataFinder
  implements ClassDataFinder
{
  private final Map<ClassId, ProtoBuf.Class> classIdToProto;
  private final Function1<ClassId, SourceElement> classSource;
  private final BinaryVersion metadataVersion;
  private final NameResolver nameResolver;
  
  public ProtoBasedClassDataFinder(ProtoBuf.PackageFragment paramPackageFragment, NameResolver paramNameResolver, BinaryVersion paramBinaryVersion, Function1<? super ClassId, ? extends SourceElement> paramFunction1)
  {
    this.nameResolver = paramNameResolver;
    this.metadataVersion = paramBinaryVersion;
    this.classSource = paramFunction1;
    paramPackageFragment = paramPackageFragment.getClass_List();
    Intrinsics.checkExpressionValueIsNotNull(paramPackageFragment, "proto.class_List");
    paramNameResolver = (Iterable)paramPackageFragment;
    paramPackageFragment = (Map)new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(paramNameResolver, 10)), 16));
    paramFunction1 = paramNameResolver.iterator();
    while (paramFunction1.hasNext())
    {
      paramNameResolver = paramFunction1.next();
      ProtoBuf.Class localClass = (ProtoBuf.Class)paramNameResolver;
      paramBinaryVersion = this.nameResolver;
      Intrinsics.checkExpressionValueIsNotNull(localClass, "klass");
      paramPackageFragment.put(NameResolverUtilKt.getClassId(paramBinaryVersion, localClass.getFqName()), paramNameResolver);
    }
    this.classIdToProto = paramPackageFragment;
  }
  
  public ClassData findClassData(ClassId paramClassId)
  {
    Intrinsics.checkParameterIsNotNull(paramClassId, "classId");
    ProtoBuf.Class localClass = (ProtoBuf.Class)this.classIdToProto.get(paramClassId);
    if (localClass != null) {
      return new ClassData(this.nameResolver, localClass, this.metadataVersion, (SourceElement)this.classSource.invoke(paramClassId));
    }
    return null;
  }
  
  public final Collection<ClassId> getAllClassIds()
  {
    return (Collection)this.classIdToProto.keySet();
  }
}

package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;

public final class DeserializedClassDataFinder
  implements ClassDataFinder
{
  private final PackageFragmentProvider packageFragmentProvider;
  
  public DeserializedClassDataFinder(PackageFragmentProvider paramPackageFragmentProvider)
  {
    this.packageFragmentProvider = paramPackageFragmentProvider;
  }
  
  public ClassData findClassData(ClassId paramClassId)
  {
    Intrinsics.checkParameterIsNotNull(paramClassId, "classId");
    Object localObject1 = this.packageFragmentProvider;
    Object localObject2 = paramClassId.getPackageFqName();
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "classId.packageFqName");
    localObject1 = ((PackageFragmentProvider)localObject1).getPackageFragments((FqName)localObject2).iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (PackageFragmentDescriptor)((Iterator)localObject1).next();
      if ((localObject2 instanceof DeserializedPackageFragment))
      {
        localObject2 = ((DeserializedPackageFragment)localObject2).getClassDataFinder().findClassData(paramClassId);
        if (localObject2 != null) {
          return localObject2;
        }
      }
    }
    return null;
  }
}

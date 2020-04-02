package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.text.StringsKt;

public final class ReflectKotlinClassFinderKt
{
  private static final String toRuntimeFqName(ClassId paramClassId)
  {
    String str = paramClassId.getRelativeClassName().asString();
    Intrinsics.checkExpressionValueIsNotNull(str, "relativeClassName.asString()");
    str = StringsKt.replace$default(str, '.', '$', false, 4, null);
    Object localObject = paramClassId.getPackageFqName();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "packageFqName");
    if (((FqName)localObject).isRoot())
    {
      paramClassId = str;
    }
    else
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramClassId.getPackageFqName());
      ((StringBuilder)localObject).append('.');
      ((StringBuilder)localObject).append(str);
      paramClassId = ((StringBuilder)localObject).toString();
    }
    return paramClassId;
  }
}

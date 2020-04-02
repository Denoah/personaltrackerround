package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import java.util.List;
import kotlin.Pair;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor.UserDataKey;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public abstract interface JavaCallableMemberDescriptor
  extends CallableMemberDescriptor
{
  public abstract JavaCallableMemberDescriptor enhance(KotlinType paramKotlinType1, List<ValueParameterData> paramList, KotlinType paramKotlinType2, Pair<CallableDescriptor.UserDataKey<?>, ?> paramPair);
}

package kotlin.reflect.jvm.internal.impl.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

public final class OperatorChecks
  extends AbstractModifierChecks
{
  public static final OperatorChecks INSTANCE = new OperatorChecks();
  private static final List<Checks> checks;
  
  static
  {
    Checks localChecks1 = new Checks(OperatorNameConventions.GET, new Check[] { (Check)MemberKindCheck.MemberOrExtension.INSTANCE, (Check)new ValueParameterCountCheck.AtLeast(1) }, null, 4, null);
    Object localObject1 = OperatorNameConventions.SET;
    Object localObject2 = (Check)MemberKindCheck.MemberOrExtension.INSTANCE;
    Object localObject3 = (Check)new ValueParameterCountCheck.AtLeast(2);
    Object localObject4 = (Function1)checks.1.INSTANCE;
    localObject3 = new Checks((Name)localObject1, new Check[] { localObject2, localObject3 }, (Function1)localObject4);
    Checks localChecks2 = new Checks(OperatorNameConventions.GET_VALUE, new Check[] { (Check)MemberKindCheck.MemberOrExtension.INSTANCE, (Check)NoDefaultAndVarargsCheck.INSTANCE, (Check)new ValueParameterCountCheck.AtLeast(2), (Check)IsKPropertyCheck.INSTANCE }, null, 4, null);
    Checks localChecks3 = new Checks(OperatorNameConventions.SET_VALUE, new Check[] { (Check)MemberKindCheck.MemberOrExtension.INSTANCE, (Check)NoDefaultAndVarargsCheck.INSTANCE, (Check)new ValueParameterCountCheck.AtLeast(3), (Check)IsKPropertyCheck.INSTANCE }, null, 4, null);
    Checks localChecks4 = new Checks(OperatorNameConventions.PROVIDE_DELEGATE, new Check[] { (Check)MemberKindCheck.MemberOrExtension.INSTANCE, (Check)NoDefaultAndVarargsCheck.INSTANCE, (Check)new ValueParameterCountCheck.Equals(2), (Check)IsKPropertyCheck.INSTANCE }, null, 4, null);
    Checks localChecks5 = new Checks(OperatorNameConventions.INVOKE, new Check[] { (Check)MemberKindCheck.MemberOrExtension.INSTANCE }, null, 4, null);
    Checks localChecks6 = new Checks(OperatorNameConventions.CONTAINS, new Check[] { (Check)MemberKindCheck.MemberOrExtension.INSTANCE, (Check)ValueParameterCountCheck.SingleValueParameter.INSTANCE, (Check)NoDefaultAndVarargsCheck.INSTANCE, (Check)ReturnsCheck.ReturnsBoolean.INSTANCE }, null, 4, null);
    localObject2 = new Checks(OperatorNameConventions.ITERATOR, new Check[] { (Check)MemberKindCheck.MemberOrExtension.INSTANCE, (Check)ValueParameterCountCheck.NoValueParameters.INSTANCE }, null, 4, null);
    localObject1 = new Checks(OperatorNameConventions.NEXT, new Check[] { (Check)MemberKindCheck.MemberOrExtension.INSTANCE, (Check)ValueParameterCountCheck.NoValueParameters.INSTANCE }, null, 4, null);
    localObject4 = new Checks(OperatorNameConventions.HAS_NEXT, new Check[] { (Check)MemberKindCheck.MemberOrExtension.INSTANCE, (Check)ValueParameterCountCheck.NoValueParameters.INSTANCE, (Check)ReturnsCheck.ReturnsBoolean.INSTANCE }, null, 4, null);
    Checks localChecks7 = new Checks(OperatorNameConventions.RANGE_TO, new Check[] { (Check)MemberKindCheck.MemberOrExtension.INSTANCE, (Check)ValueParameterCountCheck.SingleValueParameter.INSTANCE, (Check)NoDefaultAndVarargsCheck.INSTANCE }, null, 4, null);
    Object localObject5 = OperatorNameConventions.EQUALS;
    Object localObject6 = (Check)MemberKindCheck.Member.INSTANCE;
    Object localObject7 = (Function1)checks.2.INSTANCE;
    Checks localChecks8 = new Checks((Name)localObject5, new Check[] { localObject6 }, (Function1)localObject7);
    localObject7 = new Checks(OperatorNameConventions.COMPARE_TO, new Check[] { (Check)MemberKindCheck.MemberOrExtension.INSTANCE, (Check)ReturnsCheck.ReturnsInt.INSTANCE, (Check)ValueParameterCountCheck.SingleValueParameter.INSTANCE, (Check)NoDefaultAndVarargsCheck.INSTANCE }, null, 4, null);
    Checks localChecks9 = new Checks((Collection)OperatorNameConventions.BINARY_OPERATION_NAMES, new Check[] { (Check)MemberKindCheck.MemberOrExtension.INSTANCE, (Check)ValueParameterCountCheck.SingleValueParameter.INSTANCE, (Check)NoDefaultAndVarargsCheck.INSTANCE }, null, 4, null);
    localObject6 = new Checks((Collection)OperatorNameConventions.SIMPLE_UNARY_OPERATION_NAMES, new Check[] { (Check)MemberKindCheck.MemberOrExtension.INSTANCE, (Check)ValueParameterCountCheck.NoValueParameters.INSTANCE }, null, 4, null);
    localObject5 = (Collection)CollectionsKt.listOf(new Name[] { OperatorNameConventions.INC, OperatorNameConventions.DEC });
    Check localCheck = (Check)MemberKindCheck.MemberOrExtension.INSTANCE;
    Function1 localFunction1 = (Function1)checks.3.INSTANCE;
    checks = CollectionsKt.listOf(new Checks[] { localChecks1, localObject3, localChecks2, localChecks3, localChecks4, localChecks5, localChecks6, localObject2, localObject1, localObject4, localChecks7, localChecks8, localObject7, localChecks9, localObject6, new Checks((Collection)localObject5, new Check[] { localCheck }, localFunction1), new Checks((Collection)OperatorNameConventions.ASSIGNMENT_OPERATIONS, new Check[] { (Check)MemberKindCheck.MemberOrExtension.INSTANCE, (Check)ReturnsCheck.ReturnsUnit.INSTANCE, (Check)ValueParameterCountCheck.SingleValueParameter.INSTANCE, (Check)NoDefaultAndVarargsCheck.INSTANCE }, null, 4, null), new Checks(OperatorNameConventions.COMPONENT_REGEX, new Check[] { (Check)MemberKindCheck.MemberOrExtension.INSTANCE, (Check)ValueParameterCountCheck.NoValueParameters.INSTANCE }, null, 4, null) });
  }
  
  private OperatorChecks() {}
  
  public List<Checks> getChecks$descriptors()
  {
    return checks;
  }
}

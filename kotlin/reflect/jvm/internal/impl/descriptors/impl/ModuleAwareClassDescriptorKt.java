package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

public final class ModuleAwareClassDescriptorKt
{
  public static final MemberScope getRefinedMemberScopeIfPossible(ClassDescriptor paramClassDescriptor, TypeSubstitution paramTypeSubstitution, KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "$this$getRefinedMemberScopeIfPossible");
    Intrinsics.checkParameterIsNotNull(paramTypeSubstitution, "typeSubstitution");
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeRefiner, "kotlinTypeRefiner");
    return ModuleAwareClassDescriptor.Companion.getRefinedMemberScopeIfPossible$descriptors(paramClassDescriptor, paramTypeSubstitution, paramKotlinTypeRefiner);
  }
  
  public static final MemberScope getRefinedUnsubstitutedMemberScopeIfPossible(ClassDescriptor paramClassDescriptor, KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "$this$getRefinedUnsubstitutedMemberScopeIfPossible");
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeRefiner, "kotlinTypeRefiner");
    return ModuleAwareClassDescriptor.Companion.getRefinedUnsubstitutedMemberScopeIfPossible$descriptors(paramClassDescriptor, paramKotlinTypeRefiner);
  }
}

package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

public abstract class ModuleAwareClassDescriptor
  implements ClassDescriptor
{
  public static final Companion Companion = new Companion(null);
  
  public ModuleAwareClassDescriptor() {}
  
  protected abstract MemberScope getMemberScope(TypeSubstitution paramTypeSubstitution, KotlinTypeRefiner paramKotlinTypeRefiner);
  
  protected abstract MemberScope getUnsubstitutedMemberScope(KotlinTypeRefiner paramKotlinTypeRefiner);
  
  public static final class Companion
  {
    private Companion() {}
    
    public final MemberScope getRefinedMemberScopeIfPossible$descriptors(ClassDescriptor paramClassDescriptor, TypeSubstitution paramTypeSubstitution, KotlinTypeRefiner paramKotlinTypeRefiner)
    {
      Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "$this$getRefinedMemberScopeIfPossible");
      Intrinsics.checkParameterIsNotNull(paramTypeSubstitution, "typeSubstitution");
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeRefiner, "kotlinTypeRefiner");
      if (!(paramClassDescriptor instanceof ModuleAwareClassDescriptor)) {
        localObject = null;
      } else {
        localObject = paramClassDescriptor;
      }
      Object localObject = (ModuleAwareClassDescriptor)localObject;
      if (localObject != null)
      {
        paramKotlinTypeRefiner = ((ModuleAwareClassDescriptor)localObject).getMemberScope(paramTypeSubstitution, paramKotlinTypeRefiner);
        if (paramKotlinTypeRefiner != null) {
          return paramKotlinTypeRefiner;
        }
      }
      paramClassDescriptor = paramClassDescriptor.getMemberScope(paramTypeSubstitution);
      Intrinsics.checkExpressionValueIsNotNull(paramClassDescriptor, "this.getMemberScope(\n   …ubstitution\n            )");
      return paramClassDescriptor;
    }
    
    public final MemberScope getRefinedUnsubstitutedMemberScopeIfPossible$descriptors(ClassDescriptor paramClassDescriptor, KotlinTypeRefiner paramKotlinTypeRefiner)
    {
      Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "$this$getRefinedUnsubstitutedMemberScopeIfPossible");
      Intrinsics.checkParameterIsNotNull(paramKotlinTypeRefiner, "kotlinTypeRefiner");
      if (!(paramClassDescriptor instanceof ModuleAwareClassDescriptor)) {
        localObject = null;
      } else {
        localObject = paramClassDescriptor;
      }
      Object localObject = (ModuleAwareClassDescriptor)localObject;
      if (localObject != null)
      {
        paramKotlinTypeRefiner = ((ModuleAwareClassDescriptor)localObject).getUnsubstitutedMemberScope(paramKotlinTypeRefiner);
        if (paramKotlinTypeRefiner != null) {
          return paramKotlinTypeRefiner;
        }
      }
      paramClassDescriptor = paramClassDescriptor.getUnsubstitutedMemberScope();
      Intrinsics.checkExpressionValueIsNotNull(paramClassDescriptor, "this.unsubstitutedMemberScope");
      return paramClassDescriptor;
    }
  }
}

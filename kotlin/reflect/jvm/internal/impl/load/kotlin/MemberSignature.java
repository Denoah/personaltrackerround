package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf.JvmMethodSignature;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature.Field;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature.Method;

public final class MemberSignature
{
  public static final Companion Companion = new Companion(null);
  private final String signature;
  
  private MemberSignature(String paramString)
  {
    this.signature = paramString;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof MemberSignature))
      {
        paramObject = (MemberSignature)paramObject;
        if (Intrinsics.areEqual(this.signature, paramObject.signature)) {}
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  public final String getSignature$descriptors_jvm()
  {
    return this.signature;
  }
  
  public int hashCode()
  {
    String str = this.signature;
    int i;
    if (str != null) {
      i = str.hashCode();
    } else {
      i = 0;
    }
    return i;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("MemberSignature(signature=");
    localStringBuilder.append(this.signature);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    @JvmStatic
    public final MemberSignature fromFieldNameAndDesc(String paramString1, String paramString2)
    {
      Intrinsics.checkParameterIsNotNull(paramString1, "name");
      Intrinsics.checkParameterIsNotNull(paramString2, "desc");
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString1);
      localStringBuilder.append('#');
      localStringBuilder.append(paramString2);
      return new MemberSignature(localStringBuilder.toString(), null);
    }
    
    @JvmStatic
    public final MemberSignature fromJvmMemberSignature(JvmMemberSignature paramJvmMemberSignature)
    {
      Intrinsics.checkParameterIsNotNull(paramJvmMemberSignature, "signature");
      if ((paramJvmMemberSignature instanceof JvmMemberSignature.Method))
      {
        paramJvmMemberSignature = ((Companion)this).fromMethodNameAndDesc(paramJvmMemberSignature.getName(), paramJvmMemberSignature.getDesc());
      }
      else
      {
        if (!(paramJvmMemberSignature instanceof JvmMemberSignature.Field)) {
          break label57;
        }
        paramJvmMemberSignature = ((Companion)this).fromFieldNameAndDesc(paramJvmMemberSignature.getName(), paramJvmMemberSignature.getDesc());
      }
      return paramJvmMemberSignature;
      label57:
      throw new NoWhenBranchMatchedException();
    }
    
    @JvmStatic
    public final MemberSignature fromMethod(NameResolver paramNameResolver, JvmProtoBuf.JvmMethodSignature paramJvmMethodSignature)
    {
      Intrinsics.checkParameterIsNotNull(paramNameResolver, "nameResolver");
      Intrinsics.checkParameterIsNotNull(paramJvmMethodSignature, "signature");
      return ((Companion)this).fromMethodNameAndDesc(paramNameResolver.getString(paramJvmMethodSignature.getName()), paramNameResolver.getString(paramJvmMethodSignature.getDesc()));
    }
    
    @JvmStatic
    public final MemberSignature fromMethodNameAndDesc(String paramString1, String paramString2)
    {
      Intrinsics.checkParameterIsNotNull(paramString1, "name");
      Intrinsics.checkParameterIsNotNull(paramString2, "desc");
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString1);
      localStringBuilder.append(paramString2);
      return new MemberSignature(localStringBuilder.toString(), null);
    }
    
    @JvmStatic
    public final MemberSignature fromMethodSignatureAndParameterIndex(MemberSignature paramMemberSignature, int paramInt)
    {
      Intrinsics.checkParameterIsNotNull(paramMemberSignature, "signature");
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramMemberSignature.getSignature$descriptors_jvm());
      localStringBuilder.append('@');
      localStringBuilder.append(paramInt);
      return new MemberSignature(localStringBuilder.toString(), null);
    }
  }
}

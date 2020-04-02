package kotlin.reflect.jvm.internal.impl.load.java.components;

import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMethod;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public abstract interface SignaturePropagator
{
  public static final SignaturePropagator DO_NOTHING = new SignaturePropagator()
  {
    public void reportSignatureErrors(CallableMemberDescriptor paramAnonymousCallableMemberDescriptor, List<String> paramAnonymousList)
    {
      if (paramAnonymousCallableMemberDescriptor == null) {
        $$$reportNull$$$0(5);
      }
      if (paramAnonymousList == null) {
        $$$reportNull$$$0(6);
      }
      throw new UnsupportedOperationException("Should not be called");
    }
    
    public SignaturePropagator.PropagatedSignature resolvePropagatedSignature(JavaMethod paramAnonymousJavaMethod, ClassDescriptor paramAnonymousClassDescriptor, KotlinType paramAnonymousKotlinType1, KotlinType paramAnonymousKotlinType2, List<ValueParameterDescriptor> paramAnonymousList, List<TypeParameterDescriptor> paramAnonymousList1)
    {
      if (paramAnonymousJavaMethod == null) {
        $$$reportNull$$$0(0);
      }
      if (paramAnonymousClassDescriptor == null) {
        $$$reportNull$$$0(1);
      }
      if (paramAnonymousKotlinType1 == null) {
        $$$reportNull$$$0(2);
      }
      if (paramAnonymousList == null) {
        $$$reportNull$$$0(3);
      }
      if (paramAnonymousList1 == null) {
        $$$reportNull$$$0(4);
      }
      return new SignaturePropagator.PropagatedSignature(paramAnonymousKotlinType1, paramAnonymousKotlinType2, paramAnonymousList, paramAnonymousList1, Collections.emptyList(), false);
    }
  };
  
  public abstract void reportSignatureErrors(CallableMemberDescriptor paramCallableMemberDescriptor, List<String> paramList);
  
  public abstract PropagatedSignature resolvePropagatedSignature(JavaMethod paramJavaMethod, ClassDescriptor paramClassDescriptor, KotlinType paramKotlinType1, KotlinType paramKotlinType2, List<ValueParameterDescriptor> paramList, List<TypeParameterDescriptor> paramList1);
  
  public static class PropagatedSignature
  {
    private final boolean hasStableParameterNames;
    private final KotlinType receiverType;
    private final KotlinType returnType;
    private final List<String> signatureErrors;
    private final List<TypeParameterDescriptor> typeParameters;
    private final List<ValueParameterDescriptor> valueParameters;
    
    public PropagatedSignature(KotlinType paramKotlinType1, KotlinType paramKotlinType2, List<ValueParameterDescriptor> paramList, List<TypeParameterDescriptor> paramList1, List<String> paramList2, boolean paramBoolean)
    {
      this.returnType = paramKotlinType1;
      this.receiverType = paramKotlinType2;
      this.valueParameters = paramList;
      this.typeParameters = paramList1;
      this.signatureErrors = paramList2;
      this.hasStableParameterNames = paramBoolean;
    }
    
    public List<String> getErrors()
    {
      List localList = this.signatureErrors;
      if (localList == null) {
        $$$reportNull$$$0(7);
      }
      return localList;
    }
    
    public KotlinType getReceiverType()
    {
      return this.receiverType;
    }
    
    public KotlinType getReturnType()
    {
      KotlinType localKotlinType = this.returnType;
      if (localKotlinType == null) {
        $$$reportNull$$$0(4);
      }
      return localKotlinType;
    }
    
    public List<TypeParameterDescriptor> getTypeParameters()
    {
      List localList = this.typeParameters;
      if (localList == null) {
        $$$reportNull$$$0(6);
      }
      return localList;
    }
    
    public List<ValueParameterDescriptor> getValueParameters()
    {
      List localList = this.valueParameters;
      if (localList == null) {
        $$$reportNull$$$0(5);
      }
      return localList;
    }
    
    public boolean hasStableParameterNames()
    {
      return this.hasStableParameterNames;
    }
  }
}

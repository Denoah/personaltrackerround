package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.List;
import kotlin.TypeCastException;
import kotlin._Assertions;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor.CopyBuilder;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import kotlin.reflect.jvm.internal.impl.storage.NullableLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SpecialTypesKt;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.Variance;

public final class TypeAliasConstructorDescriptorImpl
  extends FunctionDescriptorImpl
  implements TypeAliasConstructorDescriptor
{
  public static final Companion Companion = new Companion(null);
  private final StorageManager storageManager;
  private final TypeAliasDescriptor typeAliasDescriptor;
  private ClassConstructorDescriptor underlyingConstructorDescriptor;
  private final NullableLazyValue withDispatchReceiver$delegate;
  
  private TypeAliasConstructorDescriptorImpl(StorageManager paramStorageManager, TypeAliasDescriptor paramTypeAliasDescriptor, final ClassConstructorDescriptor paramClassConstructorDescriptor, TypeAliasConstructorDescriptor paramTypeAliasConstructorDescriptor, Annotations paramAnnotations, CallableMemberDescriptor.Kind paramKind, SourceElement paramSourceElement)
  {
    super((DeclarationDescriptor)paramTypeAliasDescriptor, (FunctionDescriptor)paramTypeAliasConstructorDescriptor, paramAnnotations, Name.special("<init>"), paramKind, paramSourceElement);
    this.storageManager = paramStorageManager;
    this.typeAliasDescriptor = paramTypeAliasDescriptor;
    setActual(getTypeAliasDescriptor().isActual());
    this.withDispatchReceiver$delegate = this.storageManager.createNullableLazyValue((Function0)new Lambda(paramClassConstructorDescriptor)
    {
      public final TypeAliasConstructorDescriptorImpl invoke()
      {
        StorageManager localStorageManager = this.this$0.getStorageManager();
        TypeAliasDescriptor localTypeAliasDescriptor = this.this$0.getTypeAliasDescriptor();
        Object localObject1 = paramClassConstructorDescriptor;
        Object localObject2 = (TypeAliasConstructorDescriptor)this.this$0;
        Annotations localAnnotations = ((ClassConstructorDescriptor)localObject1).getAnnotations();
        Object localObject3 = paramClassConstructorDescriptor.getKind();
        Intrinsics.checkExpressionValueIsNotNull(localObject3, "underlyingConstructorDescriptor.kind");
        SourceElement localSourceElement = this.this$0.getTypeAliasDescriptor().getSource();
        Intrinsics.checkExpressionValueIsNotNull(localSourceElement, "typeAliasDescriptor.source");
        localObject1 = new TypeAliasConstructorDescriptorImpl(localStorageManager, localTypeAliasDescriptor, (ClassConstructorDescriptor)localObject1, (TypeAliasConstructorDescriptor)localObject2, localAnnotations, (CallableMemberDescriptor.Kind)localObject3, localSourceElement, null);
        localObject3 = TypeAliasConstructorDescriptorImpl.Companion.access$getTypeSubstitutorForUnderlyingClass(TypeAliasConstructorDescriptorImpl.Companion, this.this$0.getTypeAliasDescriptor());
        if (localObject3 != null)
        {
          localObject2 = paramClassConstructorDescriptor.getDispatchReceiverParameter();
          if (localObject2 != null) {
            localObject3 = ((ReceiverParameterDescriptor)localObject2).substitute((TypeSubstitutor)localObject3);
          } else {
            localObject3 = null;
          }
          ((TypeAliasConstructorDescriptorImpl)localObject1).initialize(null, (ReceiverParameterDescriptor)localObject3, this.this$0.getTypeAliasDescriptor().getDeclaredTypeParameters(), this.this$0.getValueParameters(), this.this$0.getReturnType(), Modality.FINAL, this.this$0.getTypeAliasDescriptor().getVisibility());
          return localObject1;
        }
        return null;
      }
    });
    this.underlyingConstructorDescriptor = paramClassConstructorDescriptor;
  }
  
  public TypeAliasConstructorDescriptor copy(DeclarationDescriptor paramDeclarationDescriptor, Modality paramModality, Visibility paramVisibility, CallableMemberDescriptor.Kind paramKind, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptor, "newOwner");
    Intrinsics.checkParameterIsNotNull(paramModality, "modality");
    Intrinsics.checkParameterIsNotNull(paramVisibility, "visibility");
    Intrinsics.checkParameterIsNotNull(paramKind, "kind");
    paramDeclarationDescriptor = newCopyBuilder().setOwner(paramDeclarationDescriptor).setModality(paramModality).setVisibility(paramVisibility).setKind(paramKind).setCopyOverrides(paramBoolean).build();
    if (paramDeclarationDescriptor != null) {
      return (TypeAliasConstructorDescriptor)paramDeclarationDescriptor;
    }
    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor");
  }
  
  protected TypeAliasConstructorDescriptorImpl createSubstitutedCopy(DeclarationDescriptor paramDeclarationDescriptor, FunctionDescriptor paramFunctionDescriptor, CallableMemberDescriptor.Kind paramKind, Name paramName, Annotations paramAnnotations, SourceElement paramSourceElement)
  {
    Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptor, "newOwner");
    Intrinsics.checkParameterIsNotNull(paramKind, "kind");
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "annotations");
    Intrinsics.checkParameterIsNotNull(paramSourceElement, "source");
    paramFunctionDescriptor = CallableMemberDescriptor.Kind.DECLARATION;
    int i = 0;
    if ((paramKind != paramFunctionDescriptor) && (paramKind != CallableMemberDescriptor.Kind.SYNTHESIZED)) {
      j = 0;
    } else {
      j = 1;
    }
    if ((_Assertions.ENABLED) && (j == 0))
    {
      paramFunctionDescriptor = new StringBuilder();
      paramFunctionDescriptor.append("Creating a type alias constructor that is not a declaration: \ncopy from: ");
      paramFunctionDescriptor.append(this);
      paramFunctionDescriptor.append("\nnewOwner: ");
      paramFunctionDescriptor.append(paramDeclarationDescriptor);
      paramFunctionDescriptor.append("\nkind: ");
      paramFunctionDescriptor.append(paramKind);
      throw ((Throwable)new AssertionError(paramFunctionDescriptor.toString()));
    }
    int j = i;
    if (paramName == null) {
      j = 1;
    }
    if ((_Assertions.ENABLED) && (j == 0))
    {
      paramDeclarationDescriptor = new StringBuilder();
      paramDeclarationDescriptor.append("Renaming type alias constructor: ");
      paramDeclarationDescriptor.append(this);
      throw ((Throwable)new AssertionError(paramDeclarationDescriptor.toString()));
    }
    return new TypeAliasConstructorDescriptorImpl(this.storageManager, getTypeAliasDescriptor(), getUnderlyingConstructorDescriptor(), (TypeAliasConstructorDescriptor)this, paramAnnotations, CallableMemberDescriptor.Kind.DECLARATION, paramSourceElement);
  }
  
  public ClassDescriptor getConstructedClass()
  {
    ClassDescriptor localClassDescriptor = getUnderlyingConstructorDescriptor().getConstructedClass();
    Intrinsics.checkExpressionValueIsNotNull(localClassDescriptor, "underlyingConstructorDescriptor.constructedClass");
    return localClassDescriptor;
  }
  
  public TypeAliasDescriptor getContainingDeclaration()
  {
    return getTypeAliasDescriptor();
  }
  
  public TypeAliasConstructorDescriptor getOriginal()
  {
    FunctionDescriptor localFunctionDescriptor = super.getOriginal();
    if (localFunctionDescriptor != null) {
      return (TypeAliasConstructorDescriptor)localFunctionDescriptor;
    }
    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor");
  }
  
  public KotlinType getReturnType()
  {
    KotlinType localKotlinType = super.getReturnType();
    if (localKotlinType == null) {
      Intrinsics.throwNpe();
    }
    return localKotlinType;
  }
  
  public final StorageManager getStorageManager()
  {
    return this.storageManager;
  }
  
  public TypeAliasDescriptor getTypeAliasDescriptor()
  {
    return this.typeAliasDescriptor;
  }
  
  public ClassConstructorDescriptor getUnderlyingConstructorDescriptor()
  {
    return this.underlyingConstructorDescriptor;
  }
  
  public boolean isPrimary()
  {
    return getUnderlyingConstructorDescriptor().isPrimary();
  }
  
  public TypeAliasConstructorDescriptor substitute(TypeSubstitutor paramTypeSubstitutor)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeSubstitutor, "substitutor");
    paramTypeSubstitutor = super.substitute(paramTypeSubstitutor);
    if (paramTypeSubstitutor != null)
    {
      paramTypeSubstitutor = (TypeAliasConstructorDescriptorImpl)paramTypeSubstitutor;
      Object localObject = TypeSubstitutor.create(paramTypeSubstitutor.getReturnType());
      Intrinsics.checkExpressionValueIsNotNull(localObject, "TypeSubstitutor.create(s…asConstructor.returnType)");
      localObject = getUnderlyingConstructorDescriptor().getOriginal().substitute((TypeSubstitutor)localObject);
      if (localObject != null)
      {
        paramTypeSubstitutor.underlyingConstructorDescriptor = ((ClassConstructorDescriptor)localObject);
        return (TypeAliasConstructorDescriptor)paramTypeSubstitutor;
      }
      return null;
    }
    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptorImpl");
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    private final TypeSubstitutor getTypeSubstitutorForUnderlyingClass(TypeAliasDescriptor paramTypeAliasDescriptor)
    {
      if (paramTypeAliasDescriptor.getClassDescriptor() == null) {
        return null;
      }
      return TypeSubstitutor.create((KotlinType)paramTypeAliasDescriptor.getExpandedType());
    }
    
    public final TypeAliasConstructorDescriptor createIfAvailable(StorageManager paramStorageManager, TypeAliasDescriptor paramTypeAliasDescriptor, ClassConstructorDescriptor paramClassConstructorDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramStorageManager, "storageManager");
      Intrinsics.checkParameterIsNotNull(paramTypeAliasDescriptor, "typeAliasDescriptor");
      Intrinsics.checkParameterIsNotNull(paramClassConstructorDescriptor, "constructor");
      TypeSubstitutor localTypeSubstitutor = ((Companion)this).getTypeSubstitutorForUnderlyingClass(paramTypeAliasDescriptor);
      Object localObject1 = null;
      if (localTypeSubstitutor != null)
      {
        Object localObject2 = paramClassConstructorDescriptor.substitute(localTypeSubstitutor);
        if (localObject2 != null)
        {
          Object localObject3 = paramClassConstructorDescriptor.getAnnotations();
          Object localObject4 = paramClassConstructorDescriptor.getKind();
          Intrinsics.checkExpressionValueIsNotNull(localObject4, "constructor.kind");
          SourceElement localSourceElement = paramTypeAliasDescriptor.getSource();
          Intrinsics.checkExpressionValueIsNotNull(localSourceElement, "typeAliasDescriptor.source");
          localObject3 = new TypeAliasConstructorDescriptorImpl(paramStorageManager, paramTypeAliasDescriptor, (ClassConstructorDescriptor)localObject2, null, (Annotations)localObject3, (CallableMemberDescriptor.Kind)localObject4, localSourceElement, null);
          localObject4 = FunctionDescriptorImpl.getSubstitutedValueParameters((FunctionDescriptor)localObject3, paramClassConstructorDescriptor.getValueParameters(), localTypeSubstitutor);
          if (localObject4 != null)
          {
            Intrinsics.checkExpressionValueIsNotNull(localObject4, "FunctionDescriptorImpl.g…         ) ?: return null");
            localObject2 = FlexibleTypesKt.lowerIfFlexible((KotlinType)((ClassConstructorDescriptor)localObject2).getReturnType().unwrap());
            paramStorageManager = paramTypeAliasDescriptor.getDefaultType();
            Intrinsics.checkExpressionValueIsNotNull(paramStorageManager, "typeAliasDescriptor.defaultType");
            localObject2 = SpecialTypesKt.withAbbreviation((SimpleType)localObject2, paramStorageManager);
            paramClassConstructorDescriptor = paramClassConstructorDescriptor.getDispatchReceiverParameter();
            paramStorageManager = localObject1;
            if (paramClassConstructorDescriptor != null)
            {
              paramStorageManager = (CallableDescriptor)localObject3;
              Intrinsics.checkExpressionValueIsNotNull(paramClassConstructorDescriptor, "it");
              paramStorageManager = DescriptorFactory.createExtensionReceiverParameterForCallable(paramStorageManager, localTypeSubstitutor.safeSubstitute(paramClassConstructorDescriptor.getType(), Variance.INVARIANT), Annotations.Companion.getEMPTY());
            }
            ((TypeAliasConstructorDescriptorImpl)localObject3).initialize(paramStorageManager, null, paramTypeAliasDescriptor.getDeclaredTypeParameters(), (List)localObject4, (KotlinType)localObject2, Modality.FINAL, paramTypeAliasDescriptor.getVisibility());
            return (TypeAliasConstructorDescriptor)localObject3;
          }
        }
      }
      return null;
    }
  }
}

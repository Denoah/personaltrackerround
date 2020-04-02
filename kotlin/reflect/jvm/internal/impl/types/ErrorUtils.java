package kotlin.reflect.jvm.internal.impl.types;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.builtins.DefaultBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor.Capability;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassConstructorDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.error.ErrorSimpleFunctionDescriptorImpl;

public class ErrorUtils
{
  private static final ErrorClassDescriptor ERROR_CLASS;
  private static final ModuleDescriptor ERROR_MODULE = new ModuleDescriptor()
  {
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> paramAnonymousDeclarationDescriptorVisitor, D paramAnonymousD)
    {
      if (paramAnonymousDeclarationDescriptorVisitor == null) {
        $$$reportNull$$$0(10);
      }
      return null;
    }
    
    public Annotations getAnnotations()
    {
      Annotations localAnnotations = Annotations.Companion.getEMPTY();
      if (localAnnotations == null) {
        $$$reportNull$$$0(1);
      }
      return localAnnotations;
    }
    
    public KotlinBuiltIns getBuiltIns()
    {
      DefaultBuiltIns localDefaultBuiltIns = DefaultBuiltIns.getInstance();
      if (localDefaultBuiltIns == null) {
        $$$reportNull$$$0(13);
      }
      return localDefaultBuiltIns;
    }
    
    public <T> T getCapability(ModuleDescriptor.Capability<T> paramAnonymousCapability)
    {
      if (paramAnonymousCapability == null) {
        $$$reportNull$$$0(0);
      }
      return null;
    }
    
    public DeclarationDescriptor getContainingDeclaration()
    {
      return null;
    }
    
    public List<ModuleDescriptor> getExpectedByModules()
    {
      List localList = CollectionsKt.emptyList();
      if (localList == null) {
        $$$reportNull$$$0(9);
      }
      return localList;
    }
    
    public Name getName()
    {
      Name localName = Name.special("<ERROR MODULE>");
      if (localName == null) {
        $$$reportNull$$$0(5);
      }
      return localName;
    }
    
    public DeclarationDescriptor getOriginal()
    {
      return this;
    }
    
    public PackageViewDescriptor getPackage(FqName paramAnonymousFqName)
    {
      if (paramAnonymousFqName == null) {
        $$$reportNull$$$0(7);
      }
      throw new IllegalStateException("Should not be called!");
    }
    
    public Collection<FqName> getSubPackagesOf(FqName paramAnonymousFqName, Function1<? super Name, Boolean> paramAnonymousFunction1)
    {
      if (paramAnonymousFqName == null) {
        $$$reportNull$$$0(2);
      }
      if (paramAnonymousFunction1 == null) {
        $$$reportNull$$$0(3);
      }
      paramAnonymousFqName = CollectionsKt.emptyList();
      if (paramAnonymousFqName == null) {
        $$$reportNull$$$0(4);
      }
      return paramAnonymousFqName;
    }
    
    public boolean shouldSeeInternalsOf(ModuleDescriptor paramAnonymousModuleDescriptor)
    {
      if (paramAnonymousModuleDescriptor == null) {
        $$$reportNull$$$0(11);
      }
      return false;
    }
  };
  private static final PropertyDescriptor ERROR_PROPERTY;
  private static final Set<PropertyDescriptor> ERROR_PROPERTY_GROUP;
  private static final KotlinType ERROR_PROPERTY_TYPE;
  public static final SimpleType ERROR_TYPE_FOR_LOOP_IN_SUPERTYPES;
  
  static
  {
    ERROR_CLASS = new ErrorClassDescriptor(Name.special("<ERROR CLASS>"));
    ERROR_TYPE_FOR_LOOP_IN_SUPERTYPES = createErrorType("<LOOP IN SUPERTYPES>");
    ERROR_PROPERTY_TYPE = createErrorType("<ERROR PROPERTY TYPE>");
    PropertyDescriptorImpl localPropertyDescriptorImpl = createErrorProperty();
    ERROR_PROPERTY = localPropertyDescriptorImpl;
    ERROR_PROPERTY_GROUP = Collections.singleton(localPropertyDescriptorImpl);
  }
  
  public static ClassDescriptor createErrorClass(String paramString)
  {
    if (paramString == null) {
      $$$reportNull$$$0(1);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("<ERROR CLASS: ");
    localStringBuilder.append(paramString);
    localStringBuilder.append(">");
    return new ErrorClassDescriptor(Name.special(localStringBuilder.toString()));
  }
  
  private static SimpleFunctionDescriptor createErrorFunction(ErrorScope paramErrorScope)
  {
    if (paramErrorScope == null) {
      $$$reportNull$$$0(5);
    }
    paramErrorScope = new ErrorSimpleFunctionDescriptorImpl(ERROR_CLASS, paramErrorScope);
    paramErrorScope.initialize(null, null, Collections.emptyList(), Collections.emptyList(), createErrorType("<ERROR FUNCTION RETURN TYPE>"), Modality.OPEN, Visibilities.PUBLIC);
    return paramErrorScope;
  }
  
  private static PropertyDescriptorImpl createErrorProperty()
  {
    PropertyDescriptorImpl localPropertyDescriptorImpl = PropertyDescriptorImpl.create(ERROR_CLASS, Annotations.Companion.getEMPTY(), Modality.OPEN, Visibilities.PUBLIC, true, Name.special("<ERROR PROPERTY>"), CallableMemberDescriptor.Kind.DECLARATION, SourceElement.NO_SOURCE, false, false, false, false, false, false);
    localPropertyDescriptorImpl.setType(ERROR_PROPERTY_TYPE, Collections.emptyList(), null, null);
    if (localPropertyDescriptorImpl == null) {
      $$$reportNull$$$0(4);
    }
    return localPropertyDescriptorImpl;
  }
  
  public static MemberScope createErrorScope(String paramString)
  {
    if (paramString == null) {
      $$$reportNull$$$0(2);
    }
    return createErrorScope(paramString, false);
  }
  
  public static MemberScope createErrorScope(String paramString, boolean paramBoolean)
  {
    if (paramString == null) {
      $$$reportNull$$$0(3);
    }
    if (paramBoolean) {
      return new ThrowingScope(paramString, null);
    }
    return new ErrorScope(paramString, null);
  }
  
  public static SimpleType createErrorType(String paramString)
  {
    if (paramString == null) {
      $$$reportNull$$$0(7);
    }
    return createErrorTypeWithArguments(paramString, Collections.emptyList());
  }
  
  public static TypeConstructor createErrorTypeConstructor(String paramString)
  {
    if (paramString == null) {
      $$$reportNull$$$0(15);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[ERROR : ");
    localStringBuilder.append(paramString);
    localStringBuilder.append("]");
    return createErrorTypeConstructorWithCustomDebugName(localStringBuilder.toString(), ERROR_CLASS);
  }
  
  public static TypeConstructor createErrorTypeConstructorWithCustomDebugName(String paramString)
  {
    if (paramString == null) {
      $$$reportNull$$$0(16);
    }
    return createErrorTypeConstructorWithCustomDebugName(paramString, ERROR_CLASS);
  }
  
  private static TypeConstructor createErrorTypeConstructorWithCustomDebugName(final String paramString, ErrorClassDescriptor paramErrorClassDescriptor)
  {
    if (paramString == null) {
      $$$reportNull$$$0(17);
    }
    if (paramErrorClassDescriptor == null) {
      $$$reportNull$$$0(18);
    }
    new TypeConstructor()
    {
      public KotlinBuiltIns getBuiltIns()
      {
        DefaultBuiltIns localDefaultBuiltIns = DefaultBuiltIns.getInstance();
        if (localDefaultBuiltIns == null) {
          $$$reportNull$$$0(2);
        }
        return localDefaultBuiltIns;
      }
      
      public ClassifierDescriptor getDeclarationDescriptor()
      {
        return this.val$errorClass;
      }
      
      public List<TypeParameterDescriptor> getParameters()
      {
        List localList = CollectionsKt.emptyList();
        if (localList == null) {
          $$$reportNull$$$0(0);
        }
        return localList;
      }
      
      public Collection<KotlinType> getSupertypes()
      {
        List localList = CollectionsKt.emptyList();
        if (localList == null) {
          $$$reportNull$$$0(1);
        }
        return localList;
      }
      
      public boolean isDenotable()
      {
        return false;
      }
      
      public TypeConstructor refine(KotlinTypeRefiner paramAnonymousKotlinTypeRefiner)
      {
        if (paramAnonymousKotlinTypeRefiner == null) {
          $$$reportNull$$$0(3);
        }
        return this;
      }
      
      public String toString()
      {
        return paramString;
      }
    };
  }
  
  public static SimpleType createErrorTypeWithArguments(String paramString, List<TypeProjection> paramList)
  {
    if (paramString == null) {
      $$$reportNull$$$0(11);
    }
    if (paramList == null) {
      $$$reportNull$$$0(12);
    }
    return new ErrorType(createErrorTypeConstructor(paramString), createErrorScope(paramString), paramList, false);
  }
  
  public static SimpleType createErrorTypeWithCustomConstructor(String paramString, TypeConstructor paramTypeConstructor)
  {
    if (paramString == null) {
      $$$reportNull$$$0(9);
    }
    if (paramTypeConstructor == null) {
      $$$reportNull$$$0(10);
    }
    return new ErrorType(paramTypeConstructor, createErrorScope(paramString));
  }
  
  public static SimpleType createErrorTypeWithCustomDebugName(String paramString)
  {
    if (paramString == null) {
      $$$reportNull$$$0(8);
    }
    return createErrorTypeWithCustomConstructor(paramString, createErrorTypeConstructorWithCustomDebugName(paramString));
  }
  
  public static ModuleDescriptor getErrorModule()
  {
    ModuleDescriptor localModuleDescriptor = ERROR_MODULE;
    if (localModuleDescriptor == null) {
      $$$reportNull$$$0(19);
    }
    return localModuleDescriptor;
  }
  
  public static boolean isError(DeclarationDescriptor paramDeclarationDescriptor)
  {
    boolean bool = false;
    if (paramDeclarationDescriptor == null) {
      return false;
    }
    if ((isErrorClass(paramDeclarationDescriptor)) || (isErrorClass(paramDeclarationDescriptor.getContainingDeclaration())) || (paramDeclarationDescriptor == ERROR_MODULE)) {
      bool = true;
    }
    return bool;
  }
  
  private static boolean isErrorClass(DeclarationDescriptor paramDeclarationDescriptor)
  {
    return paramDeclarationDescriptor instanceof ErrorClassDescriptor;
  }
  
  public static boolean isUninferredParameter(KotlinType paramKotlinType)
  {
    boolean bool;
    if ((paramKotlinType != null) && ((paramKotlinType.getConstructor() instanceof UninferredParameterTypeConstructor))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static class ErrorClassDescriptor
    extends ClassDescriptorImpl
  {
    public ErrorClassDescriptor(Name paramName)
    {
      super(paramName, Modality.OPEN, ClassKind.CLASS, Collections.emptyList(), SourceElement.NO_SOURCE, false, LockBasedStorageManager.NO_LOCKS);
      ClassConstructorDescriptorImpl localClassConstructorDescriptorImpl = ClassConstructorDescriptorImpl.create(this, Annotations.Companion.getEMPTY(), true, SourceElement.NO_SOURCE);
      localClassConstructorDescriptorImpl.initialize(Collections.emptyList(), Visibilities.INTERNAL);
      paramName = ErrorUtils.createErrorScope(getName().asString());
      localClassConstructorDescriptorImpl.setReturnType(new ErrorType(ErrorUtils.createErrorTypeConstructorWithCustomDebugName("<ERROR>", this), paramName));
      initialize(paramName, Collections.singleton(localClassConstructorDescriptorImpl), localClassConstructorDescriptorImpl);
    }
    
    public MemberScope getMemberScope(TypeSubstitution paramTypeSubstitution, KotlinTypeRefiner paramKotlinTypeRefiner)
    {
      if (paramTypeSubstitution == null) {
        $$$reportNull$$$0(6);
      }
      if (paramKotlinTypeRefiner == null) {
        $$$reportNull$$$0(7);
      }
      paramKotlinTypeRefiner = new StringBuilder();
      paramKotlinTypeRefiner.append("Error scope for class ");
      paramKotlinTypeRefiner.append(getName());
      paramKotlinTypeRefiner.append(" with arguments: ");
      paramKotlinTypeRefiner.append(paramTypeSubstitution);
      paramTypeSubstitution = ErrorUtils.createErrorScope(paramKotlinTypeRefiner.toString());
      if (paramTypeSubstitution == null) {
        $$$reportNull$$$0(8);
      }
      return paramTypeSubstitution;
    }
    
    public ClassDescriptor substitute(TypeSubstitutor paramTypeSubstitutor)
    {
      if (paramTypeSubstitutor == null) {
        $$$reportNull$$$0(1);
      }
      return this;
    }
    
    public String toString()
    {
      return getName().asString();
    }
  }
  
  public static class ErrorScope
    implements MemberScope
  {
    private final String debugMessage;
    
    private ErrorScope(String paramString)
    {
      this.debugMessage = paramString;
    }
    
    public ClassifierDescriptor getContributedClassifier(Name paramName, LookupLocation paramLookupLocation)
    {
      if (paramName == null) {
        $$$reportNull$$$0(1);
      }
      if (paramLookupLocation == null) {
        $$$reportNull$$$0(2);
      }
      return ErrorUtils.createErrorClass(paramName.asString());
    }
    
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter paramDescriptorKindFilter, Function1<? super Name, Boolean> paramFunction1)
    {
      if (paramDescriptorKindFilter == null) {
        $$$reportNull$$$0(16);
      }
      if (paramFunction1 == null) {
        $$$reportNull$$$0(17);
      }
      paramDescriptorKindFilter = Collections.emptyList();
      if (paramDescriptorKindFilter == null) {
        $$$reportNull$$$0(18);
      }
      return paramDescriptorKindFilter;
    }
    
    public Set<? extends SimpleFunctionDescriptor> getContributedFunctions(Name paramName, LookupLocation paramLookupLocation)
    {
      if (paramName == null) {
        $$$reportNull$$$0(8);
      }
      if (paramLookupLocation == null) {
        $$$reportNull$$$0(9);
      }
      paramName = Collections.singleton(ErrorUtils.createErrorFunction(this));
      if (paramName == null) {
        $$$reportNull$$$0(10);
      }
      return paramName;
    }
    
    public Set<? extends PropertyDescriptor> getContributedVariables(Name paramName, LookupLocation paramLookupLocation)
    {
      if (paramName == null) {
        $$$reportNull$$$0(5);
      }
      if (paramLookupLocation == null) {
        $$$reportNull$$$0(6);
      }
      paramName = ErrorUtils.ERROR_PROPERTY_GROUP;
      if (paramName == null) {
        $$$reportNull$$$0(7);
      }
      return paramName;
    }
    
    public Set<Name> getFunctionNames()
    {
      Set localSet = Collections.emptySet();
      if (localSet == null) {
        $$$reportNull$$$0(11);
      }
      return localSet;
    }
    
    public Set<Name> getVariableNames()
    {
      Set localSet = Collections.emptySet();
      if (localSet == null) {
        $$$reportNull$$$0(12);
      }
      return localSet;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("ErrorScope{");
      localStringBuilder.append(this.debugMessage);
      localStringBuilder.append('}');
      return localStringBuilder.toString();
    }
  }
  
  private static class ThrowingScope
    implements MemberScope
  {
    private final String debugMessage;
    
    private ThrowingScope(String paramString)
    {
      this.debugMessage = paramString;
    }
    
    public ClassifierDescriptor getContributedClassifier(Name paramName, LookupLocation paramLookupLocation)
    {
      if (paramName == null) {
        $$$reportNull$$$0(1);
      }
      if (paramLookupLocation == null) {
        $$$reportNull$$$0(2);
      }
      paramLookupLocation = new StringBuilder();
      paramLookupLocation.append(this.debugMessage);
      paramLookupLocation.append(", required name: ");
      paramLookupLocation.append(paramName);
      throw new IllegalStateException(paramLookupLocation.toString());
    }
    
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter paramDescriptorKindFilter, Function1<? super Name, Boolean> paramFunction1)
    {
      if (paramDescriptorKindFilter == null) {
        $$$reportNull$$$0(9);
      }
      if (paramFunction1 == null) {
        $$$reportNull$$$0(10);
      }
      throw new IllegalStateException(this.debugMessage);
    }
    
    public Collection<? extends SimpleFunctionDescriptor> getContributedFunctions(Name paramName, LookupLocation paramLookupLocation)
    {
      if (paramName == null) {
        $$$reportNull$$$0(7);
      }
      if (paramLookupLocation == null) {
        $$$reportNull$$$0(8);
      }
      paramLookupLocation = new StringBuilder();
      paramLookupLocation.append(this.debugMessage);
      paramLookupLocation.append(", required name: ");
      paramLookupLocation.append(paramName);
      throw new IllegalStateException(paramLookupLocation.toString());
    }
    
    public Collection<? extends PropertyDescriptor> getContributedVariables(Name paramName, LookupLocation paramLookupLocation)
    {
      if (paramName == null) {
        $$$reportNull$$$0(5);
      }
      if (paramLookupLocation == null) {
        $$$reportNull$$$0(6);
      }
      paramLookupLocation = new StringBuilder();
      paramLookupLocation.append(this.debugMessage);
      paramLookupLocation.append(", required name: ");
      paramLookupLocation.append(paramName);
      throw new IllegalStateException(paramLookupLocation.toString());
    }
    
    public Set<Name> getFunctionNames()
    {
      throw new IllegalStateException();
    }
    
    public Set<Name> getVariableNames()
    {
      throw new IllegalStateException();
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("ThrowingScope{");
      localStringBuilder.append(this.debugMessage);
      localStringBuilder.append('}');
      return localStringBuilder.toString();
    }
  }
  
  public static class UninferredParameterTypeConstructor
    implements TypeConstructor
  {
    private final TypeConstructor errorTypeConstructor;
    private final TypeParameterDescriptor typeParameterDescriptor;
    
    public KotlinBuiltIns getBuiltIns()
    {
      KotlinBuiltIns localKotlinBuiltIns = DescriptorUtilsKt.getBuiltIns(this.typeParameterDescriptor);
      if (localKotlinBuiltIns == null) {
        $$$reportNull$$$0(4);
      }
      return localKotlinBuiltIns;
    }
    
    public ClassifierDescriptor getDeclarationDescriptor()
    {
      return this.errorTypeConstructor.getDeclarationDescriptor();
    }
    
    public List<TypeParameterDescriptor> getParameters()
    {
      List localList = this.errorTypeConstructor.getParameters();
      if (localList == null) {
        $$$reportNull$$$0(2);
      }
      return localList;
    }
    
    public Collection<KotlinType> getSupertypes()
    {
      Collection localCollection = this.errorTypeConstructor.getSupertypes();
      if (localCollection == null) {
        $$$reportNull$$$0(3);
      }
      return localCollection;
    }
    
    public TypeParameterDescriptor getTypeParameterDescriptor()
    {
      TypeParameterDescriptor localTypeParameterDescriptor = this.typeParameterDescriptor;
      if (localTypeParameterDescriptor == null) {
        $$$reportNull$$$0(1);
      }
      return localTypeParameterDescriptor;
    }
    
    public boolean isDenotable()
    {
      return this.errorTypeConstructor.isDenotable();
    }
    
    public TypeConstructor refine(KotlinTypeRefiner paramKotlinTypeRefiner)
    {
      if (paramKotlinTypeRefiner == null) {
        $$$reportNull$$$0(5);
      }
      return this;
    }
  }
}

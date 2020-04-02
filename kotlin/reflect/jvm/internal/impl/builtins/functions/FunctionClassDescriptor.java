package kotlin.reflect.jvm.internal.impl.builtins.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.IntRange;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.ReflectionTypesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.SupertypeLoopChecker;
import kotlin.reflect.jvm.internal.impl.descriptors.SupertypeLoopChecker.EMPTY;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.AbstractClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope.Empty;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.AbstractClassTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.text.StringsKt;

public final class FunctionClassDescriptor
  extends AbstractClassDescriptor
{
  public static final Companion Companion = new Companion(null);
  private static final ClassId functionClassId = new ClassId(KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME, Name.identifier("Function"));
  private static final ClassId kFunctionClassId = new ClassId(ReflectionTypesKt.getKOTLIN_REFLECT_FQ_NAME(), Name.identifier("KFunction"));
  private final int arity;
  private final PackageFragmentDescriptor containingDeclaration;
  private final Kind functionKind;
  private final FunctionClassScope memberScope;
  private final List<TypeParameterDescriptor> parameters;
  private final StorageManager storageManager;
  private final FunctionTypeConstructor typeConstructor;
  
  public FunctionClassDescriptor(final StorageManager paramStorageManager, PackageFragmentDescriptor paramPackageFragmentDescriptor, Kind paramKind, int paramInt)
  {
    super(paramStorageManager, paramKind.numberedClassName(paramInt));
    this.storageManager = paramStorageManager;
    this.containingDeclaration = paramPackageFragmentDescriptor;
    this.functionKind = paramKind;
    this.arity = paramInt;
    this.typeConstructor = new FunctionTypeConstructor();
    this.memberScope = new FunctionClassScope(this.storageManager, this);
    paramStorageManager = new ArrayList();
    paramPackageFragmentDescriptor = new Lambda(paramStorageManager)
    {
      public final void invoke(Variance paramAnonymousVariance, String paramAnonymousString)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousVariance, "variance");
        Intrinsics.checkParameterIsNotNull(paramAnonymousString, "name");
        paramStorageManager.add(TypeParameterDescriptorImpl.createWithDefaultBound((DeclarationDescriptor)this.this$0, Annotations.Companion.getEMPTY(), false, paramAnonymousVariance, Name.identifier(paramAnonymousString), paramStorageManager.size()));
      }
    };
    Object localObject = (Iterable)new IntRange(1, this.arity);
    paramKind = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject, 10));
    localObject = ((Iterable)localObject).iterator();
    while (((Iterator)localObject).hasNext())
    {
      paramInt = ((IntIterator)localObject).nextInt();
      Variance localVariance = Variance.IN_VARIANCE;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append('P');
      localStringBuilder.append(paramInt);
      paramPackageFragmentDescriptor.invoke(localVariance, localStringBuilder.toString());
      paramKind.add(Unit.INSTANCE);
    }
    paramKind = (List)paramKind;
    paramPackageFragmentDescriptor.invoke(Variance.OUT_VARIANCE, "R");
    this.parameters = CollectionsKt.toList((Iterable)paramStorageManager);
  }
  
  public Annotations getAnnotations()
  {
    return Annotations.Companion.getEMPTY();
  }
  
  public final int getArity()
  {
    return this.arity;
  }
  
  public Void getCompanionObjectDescriptor()
  {
    return null;
  }
  
  public List<ClassConstructorDescriptor> getConstructors()
  {
    return CollectionsKt.emptyList();
  }
  
  public PackageFragmentDescriptor getContainingDeclaration()
  {
    return this.containingDeclaration;
  }
  
  public List<TypeParameterDescriptor> getDeclaredTypeParameters()
  {
    return this.parameters;
  }
  
  public final Kind getFunctionKind()
  {
    return this.functionKind;
  }
  
  public ClassKind getKind()
  {
    return ClassKind.INTERFACE;
  }
  
  public Modality getModality()
  {
    return Modality.ABSTRACT;
  }
  
  public List<ClassDescriptor> getSealedSubclasses()
  {
    return CollectionsKt.emptyList();
  }
  
  public SourceElement getSource()
  {
    SourceElement localSourceElement = SourceElement.NO_SOURCE;
    Intrinsics.checkExpressionValueIsNotNull(localSourceElement, "SourceElement.NO_SOURCE");
    return localSourceElement;
  }
  
  public MemberScope.Empty getStaticScope()
  {
    return MemberScope.Empty.INSTANCE;
  }
  
  public TypeConstructor getTypeConstructor()
  {
    return (TypeConstructor)this.typeConstructor;
  }
  
  protected FunctionClassScope getUnsubstitutedMemberScope(KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeRefiner, "kotlinTypeRefiner");
    return this.memberScope;
  }
  
  public Void getUnsubstitutedPrimaryConstructor()
  {
    return null;
  }
  
  public Visibility getVisibility()
  {
    Visibility localVisibility = Visibilities.PUBLIC;
    Intrinsics.checkExpressionValueIsNotNull(localVisibility, "Visibilities.PUBLIC");
    return localVisibility;
  }
  
  public boolean isActual()
  {
    return false;
  }
  
  public boolean isCompanionObject()
  {
    return false;
  }
  
  public boolean isData()
  {
    return false;
  }
  
  public boolean isExpect()
  {
    return false;
  }
  
  public boolean isExternal()
  {
    return false;
  }
  
  public boolean isInline()
  {
    return false;
  }
  
  public boolean isInner()
  {
    return false;
  }
  
  public String toString()
  {
    String str = getName().asString();
    Intrinsics.checkExpressionValueIsNotNull(str, "name.asString()");
    return str;
  }
  
  public static final class Companion
  {
    private Companion() {}
  }
  
  private final class FunctionTypeConstructor
    extends AbstractClassTypeConstructor
  {
    public FunctionTypeConstructor()
    {
      super();
    }
    
    protected Collection<KotlinType> computeSupertypes()
    {
      Object localObject1 = this.this$0.getFunctionKind();
      int i = FunctionClassDescriptor.FunctionTypeConstructor.WhenMappings.$EnumSwitchMapping$0[localObject1.ordinal()];
      if (i != 1)
      {
        if (i != 2)
        {
          if (i != 3)
          {
            if (i == 4) {
              localObject1 = CollectionsKt.listOf(new ClassId[] { FunctionClassDescriptor.access$getKFunctionClassId$cp(), new ClassId(DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_RELEASE, FunctionClassDescriptor.Kind.SuspendFunction.numberedClassName(this.this$0.getArity())) });
            } else {
              throw new NoWhenBranchMatchedException();
            }
          }
          else {
            localObject1 = CollectionsKt.listOf(FunctionClassDescriptor.access$getFunctionClassId$cp());
          }
        }
        else {
          localObject1 = CollectionsKt.listOf(new ClassId[] { FunctionClassDescriptor.access$getKFunctionClassId$cp(), new ClassId(KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME, FunctionClassDescriptor.Kind.Function.numberedClassName(this.this$0.getArity())) });
        }
      }
      else {
        localObject1 = CollectionsKt.listOf(FunctionClassDescriptor.access$getFunctionClassId$cp());
      }
      ModuleDescriptor localModuleDescriptor = FunctionClassDescriptor.access$getContainingDeclaration$p(this.this$0).getContainingDeclaration();
      Object localObject2 = (Iterable)localObject1;
      localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
      Iterator localIterator = ((Iterable)localObject2).iterator();
      while (localIterator.hasNext())
      {
        Object localObject3 = (ClassId)localIterator.next();
        localObject2 = FindClassInModuleKt.findClassAcrossModuleDependencies(localModuleDescriptor, (ClassId)localObject3);
        if (localObject2 != null)
        {
          localObject3 = getParameters();
          Object localObject4 = ((ClassDescriptor)localObject2).getTypeConstructor();
          Intrinsics.checkExpressionValueIsNotNull(localObject4, "descriptor.typeConstructor");
          localObject4 = (Iterable)CollectionsKt.takeLast((List)localObject3, ((TypeConstructor)localObject4).getParameters().size());
          localObject3 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject4, 10));
          localObject4 = ((Iterable)localObject4).iterator();
          while (((Iterator)localObject4).hasNext()) {
            ((Collection)localObject3).add(new TypeProjectionImpl((KotlinType)((TypeParameterDescriptor)((Iterator)localObject4).next()).getDefaultType()));
          }
          localObject3 = (List)localObject3;
          ((Collection)localObject1).add(KotlinTypeFactory.simpleNotNullType(Annotations.Companion.getEMPTY(), (ClassDescriptor)localObject2, (List)localObject3));
        }
        else
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("Built-in class ");
          ((StringBuilder)localObject1).append(localObject3);
          ((StringBuilder)localObject1).append(" not found");
          throw ((Throwable)new IllegalStateException(((StringBuilder)localObject1).toString().toString()));
        }
      }
      return (Collection)CollectionsKt.toList((Iterable)localObject1);
    }
    
    public FunctionClassDescriptor getDeclarationDescriptor()
    {
      return this.this$0;
    }
    
    public List<TypeParameterDescriptor> getParameters()
    {
      return FunctionClassDescriptor.access$getParameters$p(this.this$0);
    }
    
    protected SupertypeLoopChecker getSupertypeLoopChecker()
    {
      return (SupertypeLoopChecker)SupertypeLoopChecker.EMPTY.INSTANCE;
    }
    
    public boolean isDenotable()
    {
      return true;
    }
    
    public String toString()
    {
      return getDeclarationDescriptor().toString();
    }
  }
  
  public static enum Kind
  {
    public static final Companion Companion = new Companion(null);
    private final String classNamePrefix;
    private final FqName packageFqName;
    
    static
    {
      Object localObject1 = KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME;
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "BUILT_INS_PACKAGE_FQ_NAME");
      localObject1 = new Kind("Function", 0, (FqName)localObject1, "Function");
      Function = (Kind)localObject1;
      Object localObject2 = DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_RELEASE;
      Intrinsics.checkExpressionValueIsNotNull(localObject2, "COROUTINES_PACKAGE_FQ_NAME_RELEASE");
      Kind localKind1 = new Kind("SuspendFunction", 1, (FqName)localObject2, "SuspendFunction");
      SuspendFunction = localKind1;
      localObject2 = new Kind("KFunction", 2, ReflectionTypesKt.getKOTLIN_REFLECT_FQ_NAME(), "KFunction");
      KFunction = (Kind)localObject2;
      Kind localKind2 = new Kind("KSuspendFunction", 3, ReflectionTypesKt.getKOTLIN_REFLECT_FQ_NAME(), "KSuspendFunction");
      KSuspendFunction = localKind2;
      $VALUES = new Kind[] { localObject1, localKind1, localObject2, localKind2 };
    }
    
    private Kind(FqName paramFqName, String paramString)
    {
      this.packageFqName = paramFqName;
      this.classNamePrefix = paramString;
    }
    
    public final String getClassNamePrefix()
    {
      return this.classNamePrefix;
    }
    
    public final FqName getPackageFqName()
    {
      return this.packageFqName;
    }
    
    public final Name numberedClassName(int paramInt)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(this.classNamePrefix);
      ((StringBuilder)localObject).append(paramInt);
      localObject = Name.identifier(((StringBuilder)localObject).toString());
      Intrinsics.checkExpressionValueIsNotNull(localObject, "Name.identifier(\"$classNamePrefix$arity\")");
      return localObject;
    }
    
    public static final class Companion
    {
      private Companion() {}
      
      public final FunctionClassDescriptor.Kind byClassNamePrefix(FqName paramFqName, String paramString)
      {
        Intrinsics.checkParameterIsNotNull(paramFqName, "packageFqName");
        Intrinsics.checkParameterIsNotNull(paramString, "className");
        FunctionClassDescriptor.Kind[] arrayOfKind = FunctionClassDescriptor.Kind.values();
        int i = arrayOfKind.length;
        FunctionClassDescriptor.Kind localKind;
        for (int j = 0;; j++)
        {
          localKind = null;
          if (j >= i) {
            break;
          }
          localKind = arrayOfKind[j];
          int k;
          if ((Intrinsics.areEqual(localKind.getPackageFqName(), paramFqName)) && (StringsKt.startsWith$default(paramString, localKind.getClassNamePrefix(), false, 2, null))) {
            k = 1;
          } else {
            k = 0;
          }
          if (k != 0) {
            break;
          }
        }
        return localKind;
      }
    }
  }
}

package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.load.java.JavaClassFinder;
import kotlin.reflect.jvm.internal.impl.load.java.JavaClassFinder.Request;
import kotlin.reflect.jvm.internal.impl.load.java.JavaClassesTracker;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaClassDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.JavaResolverComponents;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaPackage;
import kotlin.reflect.jvm.internal.impl.load.java.structure.LightClassOriginKind;
import kotlin.reflect.jvm.internal.impl.load.kotlin.DeserializedDescriptorResolver;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinder;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinder.Result;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinder.Result.ClassFileContent;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinderKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.KotlinClassHeader;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.KotlinClassHeader.Kind;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.name.SpecialNames;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter.Companion;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import kotlin.reflect.jvm.internal.impl.storage.NullableLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.utils.FunctionsKt;

public final class LazyJavaPackageScope
  extends LazyJavaStaticScope
{
  private final MemoizedFunctionToNullable<FindClassRequest, ClassDescriptor> classes;
  private final JavaPackage jPackage;
  private final NullableLazyValue<Set<String>> knownClassNamesInPackage;
  private final LazyJavaPackageFragment ownerDescriptor;
  
  public LazyJavaPackageScope(final LazyJavaResolverContext paramLazyJavaResolverContext, JavaPackage paramJavaPackage, LazyJavaPackageFragment paramLazyJavaPackageFragment)
  {
    super(paramLazyJavaResolverContext);
    this.jPackage = paramJavaPackage;
    this.ownerDescriptor = paramLazyJavaPackageFragment;
    this.knownClassNamesInPackage = paramLazyJavaResolverContext.getStorageManager().createNullableLazyValue((Function0)new Lambda(paramLazyJavaResolverContext)
    {
      public final Set<String> invoke()
      {
        return paramLazyJavaResolverContext.getComponents().getFinder().knownClassNamesInPackage(this.this$0.getOwnerDescriptor().getFqName());
      }
    });
    this.classes = paramLazyJavaResolverContext.getStorageManager().createMemoizedFunctionWithNullableValues((Function1)new Lambda(paramLazyJavaResolverContext)
    {
      public final ClassDescriptor invoke(LazyJavaPackageScope.FindClassRequest paramAnonymousFindClassRequest)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFindClassRequest, "request");
        ClassId localClassId1 = new ClassId(this.this$0.getOwnerDescriptor().getFqName(), paramAnonymousFindClassRequest.getName());
        if (paramAnonymousFindClassRequest.getJavaClass() != null) {
          localObject1 = paramLazyJavaResolverContext.getComponents().getKotlinClassFinder().findKotlinClassOrContent(paramAnonymousFindClassRequest.getJavaClass());
        } else {
          localObject1 = paramLazyJavaResolverContext.getComponents().getKotlinClassFinder().findKotlinClassOrContent(localClassId1);
        }
        Object localObject2 = null;
        Object localObject3 = null;
        if (localObject1 != null) {
          localObject4 = ((KotlinClassFinder.Result)localObject1).toKotlinJvmBinaryClass();
        } else {
          localObject4 = null;
        }
        ClassId localClassId2;
        if (localObject4 != null) {
          localClassId2 = ((KotlinJvmBinaryClass)localObject4).getClassId();
        } else {
          localClassId2 = null;
        }
        if ((localClassId2 != null) && ((localClassId2.isNestedClass()) || (localClassId2.isLocal()))) {
          return null;
        }
        Object localObject4 = LazyJavaPackageScope.access$resolveKotlinBinaryClass(this.this$0, (KotlinJvmBinaryClass)localObject4);
        if ((localObject4 instanceof LazyJavaPackageScope.KotlinClassLookupResult.Found))
        {
          paramAnonymousFindClassRequest = ((LazyJavaPackageScope.KotlinClassLookupResult.Found)localObject4).getDescriptor();
        }
        else if ((localObject4 instanceof LazyJavaPackageScope.KotlinClassLookupResult.SyntheticClass))
        {
          paramAnonymousFindClassRequest = localObject2;
        }
        else
        {
          if (!(localObject4 instanceof LazyJavaPackageScope.KotlinClassLookupResult.NotFound)) {
            break label552;
          }
          paramAnonymousFindClassRequest = paramAnonymousFindClassRequest.getJavaClass();
          if (paramAnonymousFindClassRequest == null) {
            for (;;)
            {
              localObject4 = paramLazyJavaResolverContext.getComponents().getFinder();
              if (localObject1 != null)
              {
                paramAnonymousFindClassRequest = (LazyJavaPackageScope.FindClassRequest)localObject1;
                if (!(localObject1 instanceof KotlinClassFinder.Result.ClassFileContent)) {
                  paramAnonymousFindClassRequest = null;
                }
                paramAnonymousFindClassRequest = (KotlinClassFinder.Result.ClassFileContent)paramAnonymousFindClassRequest;
                if (paramAnonymousFindClassRequest != null)
                {
                  paramAnonymousFindClassRequest = paramAnonymousFindClassRequest.getContent();
                  break label251;
                }
              }
              paramAnonymousFindClassRequest = null;
              label251:
              paramAnonymousFindClassRequest = ((JavaClassFinder)localObject4).findClass(new JavaClassFinder.Request(localClassId1, paramAnonymousFindClassRequest, null, 4, null));
            }
          }
          if (paramAnonymousFindClassRequest != null) {
            localObject1 = paramAnonymousFindClassRequest.getLightClassOriginKind();
          } else {
            localObject1 = null;
          }
          if (localObject1 == LightClassOriginKind.BINARY) {
            break label416;
          }
          if (paramAnonymousFindClassRequest != null) {
            localObject4 = paramAnonymousFindClassRequest.getFqName();
          } else {
            localObject4 = null;
          }
          localObject1 = localObject3;
          if (localObject4 != null)
          {
            localObject1 = localObject3;
            if (!((FqName)localObject4).isRoot()) {
              if ((Intrinsics.areEqual(((FqName)localObject4).parent(), this.this$0.getOwnerDescriptor().getFqName()) ^ true))
              {
                localObject1 = localObject3;
              }
              else
              {
                localObject1 = new LazyJavaClassDescriptor(paramLazyJavaResolverContext, (DeclarationDescriptor)this.this$0.getOwnerDescriptor(), paramAnonymousFindClassRequest, null, 8, null);
                paramLazyJavaResolverContext.getComponents().getJavaClassesTracker().reportClass((JavaClassDescriptor)localObject1);
              }
            }
          }
          paramAnonymousFindClassRequest = (ClassDescriptor)localObject1;
        }
        return paramAnonymousFindClassRequest;
        label416:
        Object localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("Couldn't find kotlin binary class for light class created by kotlin binary file\n");
        ((StringBuilder)localObject1).append("JavaClass: ");
        ((StringBuilder)localObject1).append(paramAnonymousFindClassRequest);
        ((StringBuilder)localObject1).append('\n');
        ((StringBuilder)localObject1).append("ClassId: ");
        ((StringBuilder)localObject1).append(localClassId1);
        ((StringBuilder)localObject1).append('\n');
        ((StringBuilder)localObject1).append("findKotlinClass(JavaClass) = ");
        ((StringBuilder)localObject1).append(KotlinClassFinderKt.findKotlinClass(paramLazyJavaResolverContext.getComponents().getKotlinClassFinder(), paramAnonymousFindClassRequest));
        ((StringBuilder)localObject1).append('\n');
        ((StringBuilder)localObject1).append("findKotlinClass(ClassId) = ");
        ((StringBuilder)localObject1).append(KotlinClassFinderKt.findKotlinClass(paramLazyJavaResolverContext.getComponents().getKotlinClassFinder(), localClassId1));
        ((StringBuilder)localObject1).append('\n');
        throw ((Throwable)new IllegalStateException(((StringBuilder)localObject1).toString()));
        label552:
        throw new NoWhenBranchMatchedException();
      }
    });
  }
  
  private final ClassDescriptor findClassifier(Name paramName, JavaClass paramJavaClass)
  {
    if (!SpecialNames.isSafeIdentifier(paramName)) {
      return null;
    }
    Set localSet = (Set)this.knownClassNamesInPackage.invoke();
    if ((paramJavaClass == null) && (localSet != null) && (!localSet.contains(paramName.asString()))) {
      return null;
    }
    return (ClassDescriptor)this.classes.invoke(new FindClassRequest(paramName, paramJavaClass));
  }
  
  private final KotlinClassLookupResult resolveKotlinBinaryClass(KotlinJvmBinaryClass paramKotlinJvmBinaryClass)
  {
    if (paramKotlinJvmBinaryClass == null)
    {
      paramKotlinJvmBinaryClass = (KotlinClassLookupResult)LazyJavaPackageScope.KotlinClassLookupResult.NotFound.INSTANCE;
    }
    else if (paramKotlinJvmBinaryClass.getClassHeader().getKind() == KotlinClassHeader.Kind.CLASS)
    {
      paramKotlinJvmBinaryClass = getC().getComponents().getDeserializedDescriptorResolver().resolveClass(paramKotlinJvmBinaryClass);
      if (paramKotlinJvmBinaryClass != null) {
        paramKotlinJvmBinaryClass = (KotlinClassLookupResult)new LazyJavaPackageScope.KotlinClassLookupResult.Found(paramKotlinJvmBinaryClass);
      } else {
        paramKotlinJvmBinaryClass = (KotlinClassLookupResult)LazyJavaPackageScope.KotlinClassLookupResult.NotFound.INSTANCE;
      }
    }
    else
    {
      paramKotlinJvmBinaryClass = (KotlinClassLookupResult)LazyJavaPackageScope.KotlinClassLookupResult.SyntheticClass.INSTANCE;
    }
    return paramKotlinJvmBinaryClass;
  }
  
  protected Set<Name> computeClassNames(DescriptorKindFilter paramDescriptorKindFilter, Function1<? super Name, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramDescriptorKindFilter, "kindFilter");
    if (!paramDescriptorKindFilter.acceptsKinds(DescriptorKindFilter.Companion.getNON_SINGLETON_CLASSIFIERS_MASK())) {
      return SetsKt.emptySet();
    }
    paramDescriptorKindFilter = (Set)this.knownClassNamesInPackage.invoke();
    if (paramDescriptorKindFilter != null)
    {
      paramFunction1 = (Iterable)paramDescriptorKindFilter;
      paramDescriptorKindFilter = (Collection)new HashSet();
      paramFunction1 = paramFunction1.iterator();
      while (paramFunction1.hasNext()) {
        paramDescriptorKindFilter.add(Name.identifier((String)paramFunction1.next()));
      }
      return (Set)paramDescriptorKindFilter;
    }
    paramDescriptorKindFilter = this.jPackage;
    if (paramFunction1 == null) {
      paramFunction1 = FunctionsKt.alwaysTrue();
    }
    paramDescriptorKindFilter = (Iterable)paramDescriptorKindFilter.getClasses(paramFunction1);
    paramFunction1 = (Collection)new LinkedHashSet();
    Iterator localIterator = paramDescriptorKindFilter.iterator();
    while (localIterator.hasNext())
    {
      paramDescriptorKindFilter = (JavaClass)localIterator.next();
      if (paramDescriptorKindFilter.getLightClassOriginKind() == LightClassOriginKind.SOURCE) {
        paramDescriptorKindFilter = null;
      } else {
        paramDescriptorKindFilter = paramDescriptorKindFilter.getName();
      }
      if (paramDescriptorKindFilter != null) {
        paramFunction1.add(paramDescriptorKindFilter);
      }
    }
    return (Set)paramFunction1;
  }
  
  protected Set<Name> computeFunctionNames(DescriptorKindFilter paramDescriptorKindFilter, Function1<? super Name, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramDescriptorKindFilter, "kindFilter");
    return SetsKt.emptySet();
  }
  
  protected DeclaredMemberIndex computeMemberIndex()
  {
    return (DeclaredMemberIndex)DeclaredMemberIndex.Empty.INSTANCE;
  }
  
  protected void computeNonDeclaredFunctions(Collection<SimpleFunctionDescriptor> paramCollection, Name paramName)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "result");
    Intrinsics.checkParameterIsNotNull(paramName, "name");
  }
  
  protected Set<Name> computePropertyNames(DescriptorKindFilter paramDescriptorKindFilter, Function1<? super Name, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramDescriptorKindFilter, "kindFilter");
    return SetsKt.emptySet();
  }
  
  public final ClassDescriptor findClassifierByJavaClass$descriptors_jvm(JavaClass paramJavaClass)
  {
    Intrinsics.checkParameterIsNotNull(paramJavaClass, "javaClass");
    return findClassifier(paramJavaClass.getName(), paramJavaClass);
  }
  
  public ClassDescriptor getContributedClassifier(Name paramName, LookupLocation paramLookupLocation)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    Intrinsics.checkParameterIsNotNull(paramLookupLocation, "location");
    return findClassifier(paramName, null);
  }
  
  public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter paramDescriptorKindFilter, Function1<? super Name, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramDescriptorKindFilter, "kindFilter");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "nameFilter");
    return (Collection)computeDescriptors(paramDescriptorKindFilter, paramFunction1);
  }
  
  public Collection<PropertyDescriptor> getContributedVariables(Name paramName, LookupLocation paramLookupLocation)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    Intrinsics.checkParameterIsNotNull(paramLookupLocation, "location");
    return (Collection)CollectionsKt.emptyList();
  }
  
  protected LazyJavaPackageFragment getOwnerDescriptor()
  {
    return this.ownerDescriptor;
  }
  
  private static final class FindClassRequest
  {
    private final JavaClass javaClass;
    private final Name name;
    
    public FindClassRequest(Name paramName, JavaClass paramJavaClass)
    {
      this.name = paramName;
      this.javaClass = paramJavaClass;
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool;
      if (((paramObject instanceof FindClassRequest)) && (Intrinsics.areEqual(this.name, ((FindClassRequest)paramObject).name))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public final JavaClass getJavaClass()
    {
      return this.javaClass;
    }
    
    public final Name getName()
    {
      return this.name;
    }
    
    public int hashCode()
    {
      return this.name.hashCode();
    }
  }
  
  private static abstract class KotlinClassLookupResult
  {
    private KotlinClassLookupResult() {}
    
    public static final class Found
      extends LazyJavaPackageScope.KotlinClassLookupResult
    {
      private final ClassDescriptor descriptor;
      
      public Found(ClassDescriptor paramClassDescriptor)
      {
        super();
        this.descriptor = paramClassDescriptor;
      }
      
      public final ClassDescriptor getDescriptor()
      {
        return this.descriptor;
      }
    }
    
    public static final class NotFound
      extends LazyJavaPackageScope.KotlinClassLookupResult
    {
      public static final NotFound INSTANCE = new NotFound();
      
      private NotFound()
      {
        super();
      }
    }
    
    public static final class SyntheticClass
      extends LazyJavaPackageScope.KotlinClassLookupResult
    {
      public static final SyntheticClass INSTANCE = new SyntheticClass();
      
      private SyntheticClass()
      {
        super();
      }
    }
  }
}

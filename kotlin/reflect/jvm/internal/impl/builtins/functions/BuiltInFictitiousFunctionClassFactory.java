package kotlin.reflect.jvm.internal.impl.builtins.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.BuiltInsPackageFragment;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionInterfacePackageFragment;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.text.StringsKt;

public final class BuiltInFictitiousFunctionClassFactory
  implements ClassDescriptorFactory
{
  public static final Companion Companion = new Companion(null);
  private final ModuleDescriptor module;
  private final StorageManager storageManager;
  
  public BuiltInFictitiousFunctionClassFactory(StorageManager paramStorageManager, ModuleDescriptor paramModuleDescriptor)
  {
    this.storageManager = paramStorageManager;
    this.module = paramModuleDescriptor;
  }
  
  public ClassDescriptor createClass(ClassId paramClassId)
  {
    Intrinsics.checkParameterIsNotNull(paramClassId, "classId");
    if ((!paramClassId.isLocal()) && (!paramClassId.isNestedClass()))
    {
      Object localObject1 = paramClassId.getRelativeClassName().asString();
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "classId.relativeClassName.asString()");
      if (!StringsKt.contains$default((CharSequence)localObject1, (CharSequence)"Function", false, 2, null)) {
        return null;
      }
      paramClassId = paramClassId.getPackageFqName();
      Intrinsics.checkExpressionValueIsNotNull(paramClassId, "classId.packageFqName");
      Object localObject2 = Companion.access$parseClassName(Companion, (String)localObject1, paramClassId);
      if (localObject2 != null)
      {
        localObject1 = ((KindWithArity)localObject2).component1();
        int i = ((KindWithArity)localObject2).component2();
        localObject2 = (Iterable)this.module.getPackage(paramClassId).getFragments();
        paramClassId = (Collection)new ArrayList();
        localObject2 = ((Iterable)localObject2).iterator();
        while (((Iterator)localObject2).hasNext())
        {
          localObject3 = ((Iterator)localObject2).next();
          if ((localObject3 instanceof BuiltInsPackageFragment)) {
            paramClassId.add(localObject3);
          }
        }
        localObject2 = (List)paramClassId;
        Object localObject3 = (Iterable)localObject2;
        paramClassId = (Collection)new ArrayList();
        localObject3 = ((Iterable)localObject3).iterator();
        while (((Iterator)localObject3).hasNext())
        {
          Object localObject4 = ((Iterator)localObject3).next();
          if ((localObject4 instanceof FunctionInterfacePackageFragment)) {
            paramClassId.add(localObject4);
          }
        }
        paramClassId = (FunctionInterfacePackageFragment)CollectionsKt.firstOrNull((List)paramClassId);
        if (paramClassId == null) {
          paramClassId = CollectionsKt.first((List)localObject2);
        }
        paramClassId = (BuiltInsPackageFragment)paramClassId;
        return (ClassDescriptor)new FunctionClassDescriptor(this.storageManager, (PackageFragmentDescriptor)paramClassId, (FunctionClassDescriptor.Kind)localObject1, i);
      }
    }
    return null;
  }
  
  public Collection<ClassDescriptor> getAllContributedClassesIfPossible(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "packageFqName");
    return (Collection)SetsKt.emptySet();
  }
  
  public boolean shouldCreateClass(FqName paramFqName, Name paramName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "packageFqName");
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    paramName = paramName.asString();
    Intrinsics.checkExpressionValueIsNotNull(paramName, "name.asString()");
    boolean bool1 = false;
    boolean bool2;
    if ((!StringsKt.startsWith$default(paramName, "Function", false, 2, null)) && (!StringsKt.startsWith$default(paramName, "KFunction", false, 2, null)) && (!StringsKt.startsWith$default(paramName, "SuspendFunction", false, 2, null)))
    {
      bool2 = bool1;
      if (!StringsKt.startsWith$default(paramName, "KSuspendFunction", false, 2, null)) {}
    }
    else
    {
      bool2 = bool1;
      if (Companion.access$parseClassName(Companion, paramName, paramFqName) != null) {
        bool2 = true;
      }
    }
    return bool2;
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    private final BuiltInFictitiousFunctionClassFactory.KindWithArity parseClassName(String paramString, FqName paramFqName)
    {
      FunctionClassDescriptor.Kind localKind = FunctionClassDescriptor.Kind.Companion.byClassNamePrefix(paramFqName, paramString);
      paramFqName = null;
      if (localKind != null)
      {
        String str = localKind.getClassNamePrefix();
        Object localObject = (Companion)this;
        int i = str.length();
        if (paramString != null)
        {
          paramString = paramString.substring(i);
          Intrinsics.checkExpressionValueIsNotNull(paramString, "(this as java.lang.String).substring(startIndex)");
          localObject = ((Companion)localObject).toInt(paramString);
          paramString = paramFqName;
          if (localObject != null) {
            paramString = new BuiltInFictitiousFunctionClassFactory.KindWithArity(localKind, ((Integer)localObject).intValue());
          }
          return paramString;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      }
      return null;
    }
    
    private final Integer toInt(String paramString)
    {
      int i = ((CharSequence)paramString).length();
      int j = 0;
      if (i == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        return null;
      }
      int k = paramString.length();
      int m = 0;
      i = j;
      while (i < k)
      {
        j = paramString.charAt(i) - '0';
        if ((j >= 0) && (9 >= j))
        {
          m = m * 10 + j;
          i++;
        }
        else
        {
          return null;
        }
      }
      return Integer.valueOf(m);
    }
    
    @JvmStatic
    public final FunctionClassDescriptor.Kind getFunctionalClassKind(String paramString, FqName paramFqName)
    {
      Intrinsics.checkParameterIsNotNull(paramString, "className");
      Intrinsics.checkParameterIsNotNull(paramFqName, "packageFqName");
      paramString = ((Companion)this).parseClassName(paramString, paramFqName);
      if (paramString != null) {
        paramString = paramString.getKind();
      } else {
        paramString = null;
      }
      return paramString;
    }
  }
  
  private static final class KindWithArity
  {
    private final int arity;
    private final FunctionClassDescriptor.Kind kind;
    
    public KindWithArity(FunctionClassDescriptor.Kind paramKind, int paramInt)
    {
      this.kind = paramKind;
      this.arity = paramInt;
    }
    
    public final FunctionClassDescriptor.Kind component1()
    {
      return this.kind;
    }
    
    public final int component2()
    {
      return this.arity;
    }
    
    public boolean equals(Object paramObject)
    {
      if (this != paramObject)
      {
        if ((paramObject instanceof KindWithArity))
        {
          paramObject = (KindWithArity)paramObject;
          if (Intrinsics.areEqual(this.kind, paramObject.kind))
          {
            int i;
            if (this.arity == paramObject.arity) {
              i = 1;
            } else {
              i = 0;
            }
            if (i != 0) {
              break label58;
            }
          }
        }
        return false;
      }
      label58:
      return true;
    }
    
    public final FunctionClassDescriptor.Kind getKind()
    {
      return this.kind;
    }
    
    public int hashCode()
    {
      FunctionClassDescriptor.Kind localKind = this.kind;
      int i;
      if (localKind != null) {
        i = localKind.hashCode();
      } else {
        i = 0;
      }
      return i * 31 + this.arity;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("KindWithArity(kind=");
      localStringBuilder.append(this.kind);
      localStringBuilder.append(", arity=");
      localStringBuilder.append(this.arity);
      localStringBuilder.append(")");
      return localStringBuilder.toString();
    }
  }
}

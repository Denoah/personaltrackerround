package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;

public final class SignatureBuildingComponents
{
  public static final SignatureBuildingComponents INSTANCE = new SignatureBuildingComponents();
  
  private SignatureBuildingComponents() {}
  
  private final String escapeClassName(String paramString)
  {
    Object localObject = paramString;
    if (paramString.length() > 1)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append('L');
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append(';');
      localObject = ((StringBuilder)localObject).toString();
    }
    return localObject;
  }
  
  public final String[] constructors(String... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "signatures");
    Collection localCollection = (Collection)new ArrayList(paramVarArgs.length);
    int i = paramVarArgs.length;
    for (int j = 0; j < i; j++)
    {
      String str = paramVarArgs[j];
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("<init>(");
      localStringBuilder.append(str);
      localStringBuilder.append(")V");
      localCollection.add(localStringBuilder.toString());
    }
    paramVarArgs = ((Collection)localCollection).toArray(new String[0]);
    if (paramVarArgs != null) {
      return (String[])paramVarArgs;
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
  }
  
  public final LinkedHashSet<String> inClass(String paramString, String... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "internalName");
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "signatures");
    Collection localCollection = (Collection)new LinkedHashSet();
    int i = paramVarArgs.length;
    for (int j = 0; j < i; j++)
    {
      String str = paramVarArgs[j];
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString);
      localStringBuilder.append('.');
      localStringBuilder.append(str);
      localCollection.add(localStringBuilder.toString());
    }
    return (LinkedHashSet)localCollection;
  }
  
  public final LinkedHashSet<String> inJavaLang(String paramString, String... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "name");
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "signatures");
    return inClass(javaLang(paramString), (String[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
  }
  
  public final LinkedHashSet<String> inJavaUtil(String paramString, String... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "name");
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "signatures");
    return inClass(javaUtil(paramString), (String[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
  }
  
  public final String javaFunction(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "name");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("java/util/function/");
    localStringBuilder.append(paramString);
    return localStringBuilder.toString();
  }
  
  public final String javaLang(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "name");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("java/lang/");
    localStringBuilder.append(paramString);
    return localStringBuilder.toString();
  }
  
  public final String javaUtil(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "name");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("java/util/");
    localStringBuilder.append(paramString);
    return localStringBuilder.toString();
  }
  
  public final String jvmDescriptor(String paramString1, List<String> paramList, String paramString2)
  {
    Intrinsics.checkParameterIsNotNull(paramString1, "name");
    Intrinsics.checkParameterIsNotNull(paramList, "parameters");
    Intrinsics.checkParameterIsNotNull(paramString2, "ret");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString1);
    localStringBuilder.append('(');
    localStringBuilder.append(CollectionsKt.joinToString$default((Iterable)paramList, (CharSequence)"", null, null, 0, null, (Function1)jvmDescriptor.1.INSTANCE, 30, null));
    localStringBuilder.append(')');
    localStringBuilder.append(escapeClassName(paramString2));
    return localStringBuilder.toString();
  }
  
  public final String signature(String paramString1, String paramString2)
  {
    Intrinsics.checkParameterIsNotNull(paramString1, "internalName");
    Intrinsics.checkParameterIsNotNull(paramString2, "jvmDescriptor");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString1);
    localStringBuilder.append('.');
    localStringBuilder.append(paramString2);
    return localStringBuilder.toString();
  }
  
  public final String signature(ClassDescriptor paramClassDescriptor, String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "classDescriptor");
    Intrinsics.checkParameterIsNotNull(paramString, "jvmDescriptor");
    return signature(MethodSignatureMappingKt.getInternalName(paramClassDescriptor), paramString);
  }
}

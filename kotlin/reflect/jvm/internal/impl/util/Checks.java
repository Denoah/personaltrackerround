package kotlin.reflect.jvm.internal.impl.util;

import java.util.Arrays;
import java.util.Collection;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.text.Regex;

public final class Checks
{
  private final Function1<FunctionDescriptor, String> additionalCheck;
  private final Check[] checks;
  private final Name name;
  private final Collection<Name> nameList;
  private final Regex regex;
  
  public Checks(Collection<Name> paramCollection, Check[] paramArrayOfCheck, Function1<? super FunctionDescriptor, String> paramFunction1)
  {
    this(null, null, paramCollection, paramFunction1, (Check[])Arrays.copyOf(paramArrayOfCheck, paramArrayOfCheck.length));
  }
  
  private Checks(Name paramName, Regex paramRegex, Collection<Name> paramCollection, Function1<? super FunctionDescriptor, String> paramFunction1, Check... paramVarArgs)
  {
    this.name = paramName;
    this.regex = paramRegex;
    this.nameList = paramCollection;
    this.additionalCheck = paramFunction1;
    this.checks = paramVarArgs;
  }
  
  public Checks(Name paramName, Check[] paramArrayOfCheck, Function1<? super FunctionDescriptor, String> paramFunction1)
  {
    this(paramName, null, null, paramFunction1, (Check[])Arrays.copyOf(paramArrayOfCheck, paramArrayOfCheck.length));
  }
  
  public Checks(Regex paramRegex, Check[] paramArrayOfCheck, Function1<? super FunctionDescriptor, String> paramFunction1)
  {
    this(null, paramRegex, null, paramFunction1, (Check[])Arrays.copyOf(paramArrayOfCheck, paramArrayOfCheck.length));
  }
  
  public final CheckResult checkAll(FunctionDescriptor paramFunctionDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramFunctionDescriptor, "functionDescriptor");
    Check[] arrayOfCheck = this.checks;
    int i = arrayOfCheck.length;
    for (int j = 0; j < i; j++)
    {
      String str = arrayOfCheck[j].invoke(paramFunctionDescriptor);
      if (str != null) {
        return (CheckResult)new CheckResult.IllegalSignature(str);
      }
    }
    paramFunctionDescriptor = (String)this.additionalCheck.invoke(paramFunctionDescriptor);
    if (paramFunctionDescriptor != null) {
      return (CheckResult)new CheckResult.IllegalSignature(paramFunctionDescriptor);
    }
    return (CheckResult)CheckResult.SuccessCheck.INSTANCE;
  }
  
  public final boolean isApplicable(FunctionDescriptor paramFunctionDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramFunctionDescriptor, "functionDescriptor");
    if ((this.name != null) && ((Intrinsics.areEqual(paramFunctionDescriptor.getName(), this.name) ^ true))) {
      return false;
    }
    if (this.regex != null)
    {
      localObject = paramFunctionDescriptor.getName().asString();
      Intrinsics.checkExpressionValueIsNotNull(localObject, "functionDescriptor.name.asString()");
      localObject = (CharSequence)localObject;
      if (!this.regex.matches((CharSequence)localObject)) {
        return false;
      }
    }
    Object localObject = this.nameList;
    return (localObject == null) || (((Collection)localObject).contains(paramFunctionDescriptor.getName()));
  }
}

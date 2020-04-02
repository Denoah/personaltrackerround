package kotlin.reflect.jvm.internal.impl.util;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;

public abstract class ReturnsCheck
  implements Check
{
  private final String description;
  private final String name;
  private final Function1<KotlinBuiltIns, KotlinType> type;
  
  private ReturnsCheck(String paramString, Function1<? super KotlinBuiltIns, ? extends KotlinType> paramFunction1)
  {
    this.name = paramString;
    this.type = paramFunction1;
    paramString = new StringBuilder();
    paramString.append("must return ");
    paramString.append(this.name);
    this.description = paramString.toString();
  }
  
  public boolean check(FunctionDescriptor paramFunctionDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramFunctionDescriptor, "functionDescriptor");
    return Intrinsics.areEqual(paramFunctionDescriptor.getReturnType(), (KotlinType)this.type.invoke(DescriptorUtilsKt.getBuiltIns((DeclarationDescriptor)paramFunctionDescriptor)));
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public String invoke(FunctionDescriptor paramFunctionDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramFunctionDescriptor, "functionDescriptor");
    return Check.DefaultImpls.invoke(this, paramFunctionDescriptor);
  }
  
  public static final class ReturnsBoolean
    extends ReturnsCheck
  {
    public static final ReturnsBoolean INSTANCE = new ReturnsBoolean();
    
    private ReturnsBoolean()
    {
      super((Function1)1.INSTANCE, null);
    }
  }
  
  public static final class ReturnsInt
    extends ReturnsCheck
  {
    public static final ReturnsInt INSTANCE = new ReturnsInt();
    
    private ReturnsInt()
    {
      super((Function1)1.INSTANCE, null);
    }
  }
  
  public static final class ReturnsUnit
    extends ReturnsCheck
  {
    public static final ReturnsUnit INSTANCE = new ReturnsUnit();
    
    private ReturnsUnit()
    {
      super((Function1)1.INSTANCE, null);
    }
  }
}

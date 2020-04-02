package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;

public abstract class ErrorValue
  extends ConstantValue<Unit>
{
  public static final Companion Companion = new Companion(null);
  
  public ErrorValue()
  {
    super(Unit.INSTANCE);
  }
  
  public Unit getValue()
  {
    throw ((Throwable)new UnsupportedOperationException());
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    public final ErrorValue create(String paramString)
    {
      Intrinsics.checkParameterIsNotNull(paramString, "message");
      return (ErrorValue)new ErrorValue.ErrorValueWithMessage(paramString);
    }
  }
  
  public static final class ErrorValueWithMessage
    extends ErrorValue
  {
    private final String message;
    
    public ErrorValueWithMessage(String paramString)
    {
      this.message = paramString;
    }
    
    public SimpleType getType(ModuleDescriptor paramModuleDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "module");
      paramModuleDescriptor = ErrorUtils.createErrorType(this.message);
      Intrinsics.checkExpressionValueIsNotNull(paramModuleDescriptor, "ErrorUtils.createErrorType(message)");
      return paramModuleDescriptor;
    }
    
    public String toString()
    {
      return this.message;
    }
  }
}

package kotlin.text;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0004\n\000\n\002\020\016\n\000\n\002\020\b\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\030\002\n\002\b\002\n\002\020\000\n\000\n\002\020\021\n\002\b\003\032.\020\000\032\0020\0012\006\020\002\032\0020\0032\033\020\004\032\027\022\b\022\0060\006j\002`\007\022\004\022\0020\b0\005?\006\002\b\tH?\b\032&\020\000\032\0020\0012\033\020\004\032\027\022\b\022\0060\006j\002`\007\022\004\022\0020\b0\005?\006\002\b\tH?\b\032\037\020\n\032\0060\006j\002`\007*\0060\006j\002`\0072\b\020\013\032\004\030\0010\fH?\b\032/\020\n\032\0060\006j\002`\007*\0060\006j\002`\0072\026\020\r\032\f\022\b\b\001\022\004\030\0010\f0\016\"\004\030\0010\f?\006\002\020\017\032/\020\n\032\0060\006j\002`\007*\0060\006j\002`\0072\026\020\r\032\f\022\b\b\001\022\004\030\0010\0010\016\"\004\030\0010\001?\006\002\020\020?\006\021"}, d2={"buildString", "", "capacity", "", "builderAction", "Lkotlin/Function1;", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "", "Lkotlin/ExtensionFunctionType;", "append", "obj", "", "value", "", "(Ljava/lang/StringBuilder;[Ljava/lang/Object;)Ljava/lang/StringBuilder;", "(Ljava/lang/StringBuilder;[Ljava/lang/String;)Ljava/lang/StringBuilder;", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/text/StringsKt")
class StringsKt__StringBuilderKt
  extends StringsKt__StringBuilderJVMKt
{
  public StringsKt__StringBuilderKt() {}
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Use append(value: Any?) instead", replaceWith=@ReplaceWith(expression="append(value = obj)", imports={}))
  private static final StringBuilder append(StringBuilder paramStringBuilder, Object paramObject)
  {
    paramStringBuilder.append(paramObject);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder, "this.append(obj)");
    return paramStringBuilder;
  }
  
  public static final StringBuilder append(StringBuilder paramStringBuilder, Object... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramStringBuilder, "$this$append");
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "value");
    int i = paramVarArgs.length;
    for (int j = 0; j < i; j++) {
      paramStringBuilder.append(paramVarArgs[j]);
    }
    return paramStringBuilder;
  }
  
  public static final StringBuilder append(StringBuilder paramStringBuilder, String... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramStringBuilder, "$this$append");
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "value");
    int i = paramVarArgs.length;
    for (int j = 0; j < i; j++) {
      paramStringBuilder.append(paramVarArgs[j]);
    }
    return paramStringBuilder;
  }
  
  private static final String buildString(int paramInt, Function1<? super StringBuilder, Unit> paramFunction1)
  {
    StringBuilder localStringBuilder = new StringBuilder(paramInt);
    paramFunction1.invoke(localStringBuilder);
    paramFunction1 = localStringBuilder.toString();
    Intrinsics.checkExpressionValueIsNotNull(paramFunction1, "StringBuilder(capacity).…builderAction).toString()");
    return paramFunction1;
  }
  
  private static final String buildString(Function1<? super StringBuilder, Unit> paramFunction1)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    paramFunction1.invoke(localStringBuilder);
    paramFunction1 = localStringBuilder.toString();
    Intrinsics.checkExpressionValueIsNotNull(paramFunction1, "StringBuilder().apply(builderAction).toString()");
    return paramFunction1;
  }
}

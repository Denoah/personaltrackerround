package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000Z\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\031\n\000\n\002\020\b\n\000\n\002\020\r\n\000\n\002\030\002\n\002\030\002\n\002\020\f\n\002\030\002\n\002\020\000\n\002\020\013\n\002\020\005\n\002\020\006\n\002\020\007\n\002\020\t\n\002\020\n\n\002\020\016\n\002\b\006\n\002\020\002\n\002\b\005\032-\020\000\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\006H?\b\032/\020\000\032\0060\001j\002`\002*\0060\001j\002`\0022\b\020\003\032\004\030\0010\b2\006\020\005\032\0020\0062\006\020\007\032\0020\006H?\b\032\022\020\t\032\0060\nj\002`\013*\0060\nj\002`\013\032\035\020\t\032\0060\nj\002`\013*\0060\nj\002`\0132\006\020\003\032\0020\fH?\b\032\037\020\t\032\0060\nj\002`\013*\0060\nj\002`\0132\b\020\003\032\004\030\0010\bH?\b\032\022\020\t\032\0060\001j\002`\002*\0060\001j\002`\002\032\037\020\t\032\0060\001j\002`\002*\0060\001j\002`\0022\b\020\003\032\004\030\0010\rH?\b\032\037\020\t\032\0060\001j\002`\002*\0060\001j\002`\0022\b\020\003\032\004\030\0010\016H?\b\032\035\020\t\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\017H?\b\032\035\020\t\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\020H?\b\032\035\020\t\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\fH?\b\032\035\020\t\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\004H?\b\032\037\020\t\032\0060\001j\002`\002*\0060\001j\002`\0022\b\020\003\032\004\030\0010\bH?\b\032\035\020\t\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\021H?\b\032\035\020\t\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\022H?\b\032\035\020\t\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\006H?\b\032\035\020\t\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\023H?\b\032\035\020\t\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\003\032\0020\024H?\b\032\037\020\t\032\0060\001j\002`\002*\0060\001j\002`\0022\b\020\003\032\004\030\0010\025H?\b\032%\020\t\032\0060\001j\002`\002*\0060\001j\002`\0022\016\020\003\032\n\030\0010\001j\004\030\001`\002H?\b\032\024\020\026\032\0060\001j\002`\002*\0060\001j\002`\002H\007\032\035\020\027\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\030\032\0020\006H?\b\032%\020\031\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\005\032\0020\0062\006\020\007\032\0020\006H?\b\0325\020\032\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\030\032\0020\0062\006\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\006H?\b\0327\020\032\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\030\032\0020\0062\b\020\003\032\004\030\0010\b2\006\020\005\032\0020\0062\006\020\007\032\0020\006H?\b\032!\020\033\032\0020\034*\0060\001j\002`\0022\006\020\030\032\0020\0062\006\020\003\032\0020\fH?\n\032-\020\035\032\0060\001j\002`\002*\0060\001j\002`\0022\006\020\005\032\0020\0062\006\020\007\032\0020\0062\006\020\003\032\0020\025H?\b\0327\020\036\032\0020\034*\0060\001j\002`\0022\006\020\037\032\0020\0042\b\b\002\020 \032\0020\0062\b\b\002\020\005\032\0020\0062\b\b\002\020\007\032\0020\006H?\b?\006!"}, d2={"appendRange", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "value", "", "startIndex", "", "endIndex", "", "appendln", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "", "Ljava/lang/StringBuffer;", "", "", "", "", "", "", "", "", "clear", "deleteAt", "index", "deleteRange", "insertRange", "set", "", "setRange", "toCharArray", "destination", "destinationOffset", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/text/StringsKt")
class StringsKt__StringBuilderJVMKt
  extends StringsKt__RegexExtensionsKt
{
  public StringsKt__StringBuilderJVMKt() {}
  
  private static final StringBuilder appendRange(StringBuilder paramStringBuilder, CharSequence paramCharSequence, int paramInt1, int paramInt2)
  {
    paramStringBuilder.append(paramCharSequence, paramInt1, paramInt2);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder, "this.append(value, startIndex, endIndex)");
    return paramStringBuilder;
  }
  
  private static final StringBuilder appendRange(StringBuilder paramStringBuilder, char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    paramStringBuilder.append(paramArrayOfChar, paramInt1, paramInt2 - paramInt1);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder, "this.append(value, start…x, endIndex - startIndex)");
    return paramStringBuilder;
  }
  
  public static final Appendable appendln(Appendable paramAppendable)
  {
    Intrinsics.checkParameterIsNotNull(paramAppendable, "$this$appendln");
    paramAppendable = paramAppendable.append((CharSequence)SystemProperties.LINE_SEPARATOR);
    Intrinsics.checkExpressionValueIsNotNull(paramAppendable, "append(SystemProperties.LINE_SEPARATOR)");
    return paramAppendable;
  }
  
  private static final Appendable appendln(Appendable paramAppendable, char paramChar)
  {
    paramAppendable = paramAppendable.append(paramChar);
    Intrinsics.checkExpressionValueIsNotNull(paramAppendable, "append(value)");
    return StringsKt.appendln(paramAppendable);
  }
  
  private static final Appendable appendln(Appendable paramAppendable, CharSequence paramCharSequence)
  {
    paramAppendable = paramAppendable.append(paramCharSequence);
    Intrinsics.checkExpressionValueIsNotNull(paramAppendable, "append(value)");
    return StringsKt.appendln(paramAppendable);
  }
  
  public static final StringBuilder appendln(StringBuilder paramStringBuilder)
  {
    Intrinsics.checkParameterIsNotNull(paramStringBuilder, "$this$appendln");
    paramStringBuilder.append(SystemProperties.LINE_SEPARATOR);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder, "append(SystemProperties.LINE_SEPARATOR)");
    return paramStringBuilder;
  }
  
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, byte paramByte)
  {
    paramStringBuilder.append(paramByte);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder, "append(value.toInt())");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, char paramChar)
  {
    paramStringBuilder.append(paramChar);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder, "append(value)");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, double paramDouble)
  {
    paramStringBuilder.append(paramDouble);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder, "append(value)");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, float paramFloat)
  {
    paramStringBuilder.append(paramFloat);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder, "append(value)");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, int paramInt)
  {
    paramStringBuilder.append(paramInt);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder, "append(value)");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, long paramLong)
  {
    paramStringBuilder.append(paramLong);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder, "append(value)");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, CharSequence paramCharSequence)
  {
    paramStringBuilder.append(paramCharSequence);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder, "append(value)");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, Object paramObject)
  {
    paramStringBuilder.append(paramObject);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder, "append(value)");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, String paramString)
  {
    paramStringBuilder.append(paramString);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder, "append(value)");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, StringBuffer paramStringBuffer)
  {
    paramStringBuilder.append(paramStringBuffer);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder, "append(value)");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  private static final StringBuilder appendln(StringBuilder paramStringBuilder1, StringBuilder paramStringBuilder2)
  {
    paramStringBuilder1.append((CharSequence)paramStringBuilder2);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder1, "append(value)");
    return StringsKt.appendln(paramStringBuilder1);
  }
  
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, short paramShort)
  {
    paramStringBuilder.append(paramShort);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder, "append(value.toInt())");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, boolean paramBoolean)
  {
    paramStringBuilder.append(paramBoolean);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder, "append(value)");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  private static final StringBuilder appendln(StringBuilder paramStringBuilder, char[] paramArrayOfChar)
  {
    paramStringBuilder.append(paramArrayOfChar);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder, "append(value)");
    return StringsKt.appendln(paramStringBuilder);
  }
  
  public static final StringBuilder clear(StringBuilder paramStringBuilder)
  {
    Intrinsics.checkParameterIsNotNull(paramStringBuilder, "$this$clear");
    paramStringBuilder.setLength(0);
    return paramStringBuilder;
  }
  
  private static final StringBuilder deleteAt(StringBuilder paramStringBuilder, int paramInt)
  {
    paramStringBuilder = paramStringBuilder.deleteCharAt(paramInt);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder, "this.deleteCharAt(index)");
    return paramStringBuilder;
  }
  
  private static final StringBuilder deleteRange(StringBuilder paramStringBuilder, int paramInt1, int paramInt2)
  {
    paramStringBuilder = paramStringBuilder.delete(paramInt1, paramInt2);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder, "this.delete(startIndex, endIndex)");
    return paramStringBuilder;
  }
  
  private static final StringBuilder insertRange(StringBuilder paramStringBuilder, int paramInt1, CharSequence paramCharSequence, int paramInt2, int paramInt3)
  {
    paramStringBuilder = paramStringBuilder.insert(paramInt1, paramCharSequence, paramInt2, paramInt3);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder, "this.insert(index, value, startIndex, endIndex)");
    return paramStringBuilder;
  }
  
  private static final StringBuilder insertRange(StringBuilder paramStringBuilder, int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3)
  {
    paramStringBuilder = paramStringBuilder.insert(paramInt1, paramArrayOfChar, paramInt2, paramInt3 - paramInt2);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder, "this.insert(index, value…x, endIndex - startIndex)");
    return paramStringBuilder;
  }
  
  private static final void set(StringBuilder paramStringBuilder, int paramInt, char paramChar)
  {
    Intrinsics.checkParameterIsNotNull(paramStringBuilder, "$this$set");
    paramStringBuilder.setCharAt(paramInt, paramChar);
  }
  
  private static final StringBuilder setRange(StringBuilder paramStringBuilder, int paramInt1, int paramInt2, String paramString)
  {
    paramStringBuilder = paramStringBuilder.replace(paramInt1, paramInt2, paramString);
    Intrinsics.checkExpressionValueIsNotNull(paramStringBuilder, "this.replace(startIndex, endIndex, value)");
    return paramStringBuilder;
  }
  
  private static final void toCharArray(StringBuilder paramStringBuilder, char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3)
  {
    paramStringBuilder.getChars(paramInt2, paramInt3, paramArrayOfChar, paramInt1);
  }
}

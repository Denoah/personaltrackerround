package kotlin.text;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\b\002\n\002\030\002\n\002\030\002\n\000\n\002\020\021\n\002\020\r\n\002\b\002\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\003\0325\020\000\032\002H\001\"\f\b\000\020\001*\0060\002j\002`\003*\002H\0012\026\020\004\032\f\022\b\b\001\022\004\030\0010\0060\005\"\004\030\0010\006?\006\002\020\007\0329\020\b\032\0020\t\"\004\b\000\020\001*\0060\002j\002`\0032\006\020\n\032\002H\0012\024\020\013\032\020\022\004\022\002H\001\022\004\022\0020\006\030\0010\fH\000?\006\002\020\r\0329\020\016\032\002H\001\"\f\b\000\020\001*\0060\002j\002`\003*\002H\0012\b\020\004\032\004\030\0010\0062\006\020\017\032\0020\0202\006\020\021\032\0020\020H\007?\006\002\020\022?\006\023"}, d2={"append", "T", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "value", "", "", "(Ljava/lang/Appendable;[Ljava/lang/CharSequence;)Ljava/lang/Appendable;", "appendElement", "", "element", "transform", "Lkotlin/Function1;", "(Ljava/lang/Appendable;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "appendRange", "startIndex", "", "endIndex", "(Ljava/lang/Appendable;Ljava/lang/CharSequence;II)Ljava/lang/Appendable;", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/text/StringsKt")
class StringsKt__AppendableKt
{
  public StringsKt__AppendableKt() {}
  
  public static final <T extends Appendable> T append(T paramT, CharSequence... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramT, "$this$append");
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "value");
    int i = paramVarArgs.length;
    for (int j = 0; j < i; j++) {
      paramT.append(paramVarArgs[j]);
    }
    return paramT;
  }
  
  public static final <T> void appendElement(Appendable paramAppendable, T paramT, Function1<? super T, ? extends CharSequence> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramAppendable, "$this$appendElement");
    if (paramFunction1 != null)
    {
      paramAppendable.append((CharSequence)paramFunction1.invoke(paramT));
    }
    else
    {
      boolean bool;
      if (paramT != null) {
        bool = paramT instanceof CharSequence;
      } else {
        bool = true;
      }
      if (bool) {
        paramAppendable.append((CharSequence)paramT);
      } else if ((paramT instanceof Character)) {
        paramAppendable.append(((Character)paramT).charValue());
      } else {
        paramAppendable.append((CharSequence)String.valueOf(paramT));
      }
    }
  }
  
  public static final <T extends Appendable> T appendRange(T paramT, CharSequence paramCharSequence, int paramInt1, int paramInt2)
  {
    Intrinsics.checkParameterIsNotNull(paramT, "$this$appendRange");
    paramT = paramT.append(paramCharSequence, paramInt1, paramInt2);
    if (paramT != null) {
      return paramT;
    }
    throw new TypeCastException("null cannot be cast to non-null type T");
  }
}

package kotlin.text;

import java.util.regex.Pattern;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\f\n\000\n\002\030\002\n\002\030\002\n\000\032\r\020\000\032\0020\001*\0020\002H?\b?\006\003"}, d2={"toRegex", "Lkotlin/text/Regex;", "Ljava/util/regex/Pattern;", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/text/StringsKt")
class StringsKt__RegexExtensionsJVMKt
  extends StringsKt__IndentKt
{
  public StringsKt__RegexExtensionsJVMKt() {}
  
  private static final Regex toRegex(Pattern paramPattern)
  {
    return new Regex(paramPattern);
  }
}

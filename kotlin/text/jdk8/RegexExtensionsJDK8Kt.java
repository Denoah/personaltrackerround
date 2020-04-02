package kotlin.text.jdk8;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.MatchGroup;
import kotlin.text.MatchGroupCollection;
import kotlin.text.MatchNamedGroupCollection;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\000\032\027\020\000\032\004\030\0010\001*\0020\0022\006\020\003\032\0020\004H?\002?\006\005"}, d2={"get", "Lkotlin/text/MatchGroup;", "Lkotlin/text/MatchGroupCollection;", "name", "", "kotlin-stdlib-jdk8"}, k=2, mv={1, 1, 15}, pn="kotlin.text")
public final class RegexExtensionsJDK8Kt
{
  public static final MatchGroup get(MatchGroupCollection paramMatchGroupCollection, String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramMatchGroupCollection, "$this$get");
    Intrinsics.checkParameterIsNotNull(paramString, "name");
    MatchGroupCollection localMatchGroupCollection = paramMatchGroupCollection;
    if (!(paramMatchGroupCollection instanceof MatchNamedGroupCollection)) {
      localMatchGroupCollection = null;
    }
    paramMatchGroupCollection = (MatchNamedGroupCollection)localMatchGroupCollection;
    if (paramMatchGroupCollection != null) {
      return paramMatchGroupCollection.get(paramString);
    }
    throw ((Throwable)new UnsupportedOperationException("Retrieving groups by name is not supported on this platform."));
  }
}

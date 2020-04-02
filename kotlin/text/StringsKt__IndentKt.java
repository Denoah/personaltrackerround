package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.sequences.SequencesKt;

@Metadata(bv={1, 0, 3}, d1={"\000\036\n\000\n\002\030\002\n\002\020\016\n\002\b\003\n\002\020\b\n\002\b\003\n\002\020 \n\002\b\013\032!\020\000\032\016\022\004\022\0020\002\022\004\022\0020\0020\0012\006\020\003\032\0020\002H\002?\006\002\b\004\032\021\020\005\032\0020\006*\0020\002H\002?\006\002\b\007\032\024\020\b\032\0020\002*\0020\0022\b\b\002\020\003\032\0020\002\032J\020\t\032\0020\002*\b\022\004\022\0020\0020\n2\006\020\013\032\0020\0062\022\020\f\032\016\022\004\022\0020\002\022\004\022\0020\0020\0012\024\020\r\032\020\022\004\022\0020\002\022\006\022\004\030\0010\0020\001H?\b?\006\002\b\016\032\024\020\017\032\0020\002*\0020\0022\b\b\002\020\020\032\0020\002\032\036\020\021\032\0020\002*\0020\0022\b\b\002\020\020\032\0020\0022\b\b\002\020\022\032\0020\002\032\n\020\023\032\0020\002*\0020\002\032\024\020\024\032\0020\002*\0020\0022\b\b\002\020\022\032\0020\002?\006\025"}, d2={"getIndentFunction", "Lkotlin/Function1;", "", "indent", "getIndentFunction$StringsKt__IndentKt", "indentWidth", "", "indentWidth$StringsKt__IndentKt", "prependIndent", "reindent", "", "resultSizeEstimate", "indentAddFunction", "indentCutFunction", "reindent$StringsKt__IndentKt", "replaceIndent", "newIndent", "replaceIndentByMargin", "marginPrefix", "trimIndent", "trimMargin", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/text/StringsKt")
class StringsKt__IndentKt
  extends StringsKt__AppendableKt
{
  public StringsKt__IndentKt() {}
  
  private static final Function1<String, String> getIndentFunction$StringsKt__IndentKt(String paramString)
  {
    int i;
    if (((CharSequence)paramString).length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      paramString = (Function1)getIndentFunction.1.INSTANCE;
    } else {
      paramString = (Function1)new Lambda(paramString)
      {
        public final String invoke(String paramAnonymousString)
        {
          Intrinsics.checkParameterIsNotNull(paramAnonymousString, "line");
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append(this.$indent);
          localStringBuilder.append(paramAnonymousString);
          return localStringBuilder.toString();
        }
      };
    }
    return paramString;
  }
  
  private static final int indentWidth$StringsKt__IndentKt(String paramString)
  {
    CharSequence localCharSequence = (CharSequence)paramString;
    int i = localCharSequence.length();
    for (int j = 0; j < i; j++) {
      if ((CharsKt.isWhitespace(localCharSequence.charAt(j)) ^ true)) {
        break label45;
      }
    }
    j = -1;
    label45:
    i = j;
    if (j == -1) {
      i = paramString.length();
    }
    return i;
  }
  
  public static final String prependIndent(String paramString1, String paramString2)
  {
    Intrinsics.checkParameterIsNotNull(paramString1, "$this$prependIndent");
    Intrinsics.checkParameterIsNotNull(paramString2, "indent");
    SequencesKt.joinToString$default(SequencesKt.map(StringsKt.lineSequence((CharSequence)paramString1), (Function1)new Lambda(paramString2)
    {
      public final String invoke(String paramAnonymousString)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousString, "it");
        Object localObject;
        if (StringsKt.isBlank((CharSequence)paramAnonymousString))
        {
          localObject = paramAnonymousString;
          if (paramAnonymousString.length() < this.$indent.length()) {
            localObject = this.$indent;
          }
        }
        else
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append(this.$indent);
          ((StringBuilder)localObject).append(paramAnonymousString);
          localObject = ((StringBuilder)localObject).toString();
        }
        return localObject;
      }
    }), (CharSequence)"\n", null, null, 0, null, null, 62, null);
  }
  
  private static final String reindent$StringsKt__IndentKt(List<String> paramList, int paramInt, Function1<? super String, String> paramFunction11, Function1<? super String, String> paramFunction12)
  {
    int i = CollectionsKt.getLastIndex(paramList);
    paramList = (Iterable)paramList;
    Collection localCollection = (Collection)new ArrayList();
    Iterator localIterator = paramList.iterator();
    for (int j = 0; localIterator.hasNext(); j++)
    {
      paramList = localIterator.next();
      if (j < 0) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
          CollectionsKt.throwIndexOverflow();
        } else {
          throw ((Throwable)new ArithmeticException("Index overflow has happened."));
        }
      }
      String str1 = (String)paramList;
      if (((j == 0) || (j == i)) && (StringsKt.isBlank((CharSequence)str1)))
      {
        paramList = null;
      }
      else
      {
        String str2 = (String)paramFunction12.invoke(str1);
        paramList = str1;
        if (str2 != null)
        {
          str2 = (String)paramFunction11.invoke(str2);
          paramList = str1;
          if (str2 != null) {
            paramList = str2;
          }
        }
      }
      if (paramList != null) {
        localCollection.add(paramList);
      }
    }
    paramList = ((StringBuilder)CollectionsKt.joinTo$default((Iterable)localCollection, (Appendable)new StringBuilder(paramInt), (CharSequence)"\n", null, null, 0, null, null, 124, null)).toString();
    Intrinsics.checkExpressionValueIsNotNull(paramList, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
    return paramList;
  }
  
  public static final String replaceIndent(String paramString1, String paramString2)
  {
    Intrinsics.checkParameterIsNotNull(paramString1, "$this$replaceIndent");
    Intrinsics.checkParameterIsNotNull(paramString2, "newIndent");
    Object localObject1 = StringsKt.lines((CharSequence)paramString1);
    Object localObject2 = (Iterable)localObject1;
    Object localObject3 = (Collection)new ArrayList();
    Iterator localIterator = ((Iterable)localObject2).iterator();
    while (localIterator.hasNext())
    {
      localObject4 = localIterator.next();
      if ((StringsKt.isBlank((CharSequence)localObject4) ^ true)) {
        ((Collection)localObject3).add(localObject4);
      }
    }
    localObject3 = (Iterable)localObject3;
    Object localObject4 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject3, 10));
    localObject3 = ((Iterable)localObject3).iterator();
    while (((Iterator)localObject3).hasNext()) {
      ((Collection)localObject4).add(Integer.valueOf(indentWidth$StringsKt__IndentKt((String)((Iterator)localObject3).next())));
    }
    localObject4 = (Integer)CollectionsKt.min((Iterable)localObject4);
    int i = 0;
    int j;
    if (localObject4 != null) {
      j = ((Integer)localObject4).intValue();
    } else {
      j = 0;
    }
    int k = paramString1.length();
    int m = paramString2.length();
    int n = ((List)localObject1).size();
    localObject4 = getIndentFunction$StringsKt__IndentKt(paramString2);
    int i1 = CollectionsKt.getLastIndex((List)localObject1);
    localObject1 = (Collection)new ArrayList();
    localObject3 = ((Iterable)localObject2).iterator();
    while (((Iterator)localObject3).hasNext())
    {
      paramString1 = ((Iterator)localObject3).next();
      if (i < 0) {
        CollectionsKt.throwIndexOverflow();
      }
      paramString2 = (String)paramString1;
      if (((i == 0) || (i == i1)) && (StringsKt.isBlank((CharSequence)paramString2)))
      {
        paramString1 = null;
      }
      else
      {
        localObject2 = StringsKt.drop(paramString2, j);
        paramString1 = paramString2;
        if (localObject2 != null)
        {
          localObject2 = (String)((Function1)localObject4).invoke(localObject2);
          paramString1 = paramString2;
          if (localObject2 != null) {
            paramString1 = (String)localObject2;
          }
        }
      }
      if (paramString1 != null) {
        ((Collection)localObject1).add(paramString1);
      }
      i++;
    }
    paramString1 = ((StringBuilder)CollectionsKt.joinTo$default((Iterable)localObject1, (Appendable)new StringBuilder(k + m * n), (CharSequence)"\n", null, null, 0, null, null, 124, null)).toString();
    Intrinsics.checkExpressionValueIsNotNull(paramString1, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
    return paramString1;
  }
  
  public static final String replaceIndentByMargin(String paramString1, String paramString2, String paramString3)
  {
    Intrinsics.checkParameterIsNotNull(paramString1, "$this$replaceIndentByMargin");
    Intrinsics.checkParameterIsNotNull(paramString2, "newIndent");
    Intrinsics.checkParameterIsNotNull(paramString3, "marginPrefix");
    if ((StringsKt.isBlank((CharSequence)paramString3) ^ true))
    {
      Object localObject = StringsKt.lines((CharSequence)paramString1);
      int i = paramString1.length();
      int j = paramString2.length();
      int k = ((List)localObject).size();
      Function1 localFunction1 = getIndentFunction$StringsKt__IndentKt(paramString2);
      int m = CollectionsKt.getLastIndex((List)localObject);
      paramString1 = (Iterable)localObject;
      Collection localCollection = (Collection)new ArrayList();
      Iterator localIterator = paramString1.iterator();
      for (int n = 0; localIterator.hasNext(); n++)
      {
        paramString1 = localIterator.next();
        if (n < 0) {
          CollectionsKt.throwIndexOverflow();
        }
        localObject = (String)paramString1;
        paramString2 = null;
        if (((n == 0) || (n == m)) && (StringsKt.isBlank((CharSequence)localObject)))
        {
          paramString1 = null;
        }
        else
        {
          paramString1 = (CharSequence)localObject;
          int i1 = paramString1.length();
          for (int i2 = 0; i2 < i1; i2++) {
            if ((CharsKt.isWhitespace(paramString1.charAt(i2)) ^ true)) {
              break label209;
            }
          }
          i2 = -1;
          label209:
          if ((i2 != -1) && (StringsKt.startsWith$default((String)localObject, paramString3, i2, false, 4, null)))
          {
            i1 = paramString3.length();
            if (localObject != null)
            {
              paramString2 = ((String)localObject).substring(i2 + i1);
              Intrinsics.checkExpressionValueIsNotNull(paramString2, "(this as java.lang.String).substring(startIndex)");
            }
            else
            {
              throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
          }
          paramString1 = (String)localObject;
          if (paramString2 != null)
          {
            paramString2 = (String)localFunction1.invoke(paramString2);
            paramString1 = (String)localObject;
            if (paramString2 != null) {
              paramString1 = paramString2;
            }
          }
        }
        if (paramString1 != null) {
          localCollection.add(paramString1);
        }
      }
      paramString1 = ((StringBuilder)CollectionsKt.joinTo$default((Iterable)localCollection, (Appendable)new StringBuilder(i + j * k), (CharSequence)"\n", null, null, 0, null, null, 124, null)).toString();
      Intrinsics.checkExpressionValueIsNotNull(paramString1, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
      return paramString1;
    }
    throw ((Throwable)new IllegalArgumentException("marginPrefix must be non-blank string.".toString()));
  }
  
  public static final String trimIndent(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$trimIndent");
    return StringsKt.replaceIndent(paramString, "");
  }
  
  public static final String trimMargin(String paramString1, String paramString2)
  {
    Intrinsics.checkParameterIsNotNull(paramString1, "$this$trimMargin");
    Intrinsics.checkParameterIsNotNull(paramString2, "marginPrefix");
    return StringsKt.replaceIndentByMargin(paramString1, "", paramString2);
  }
}

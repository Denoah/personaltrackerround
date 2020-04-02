package okhttp3;

import .r8.backportedMethods.utility.Boolean.1.hashCode;
import .r8.backportedMethods.utility.Long.1.hashCode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.internal.HostnamesKt;
import okhttp3.internal.Util;
import okhttp3.internal.http.DatesKt;
import okhttp3.internal.publicsuffix.PublicSuffixDatabase;
import okhttp3.internal.publicsuffix.PublicSuffixDatabase.Companion;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\020\000\n\000\n\002\020\016\n\002\b\002\n\002\020\t\n\002\b\003\n\002\020\013\n\002\b\f\n\002\020\b\n\002\b\004\n\002\030\002\n\002\b\013\030\000 &2\0020\001:\002%&BO\b\002\022\006\020\002\032\0020\003\022\006\020\004\032\0020\003\022\006\020\005\032\0020\006\022\006\020\007\032\0020\003\022\006\020\b\032\0020\003\022\006\020\t\032\0020\n\022\006\020\013\032\0020\n\022\006\020\f\032\0020\n\022\006\020\r\032\0020\n?\006\002\020\016J\r\020\007\032\0020\003H\007?\006\002\b\022J\023\020\023\032\0020\n2\b\020\024\032\004\030\0010\001H?\002J\r\020\005\032\0020\006H\007?\006\002\b\025J\b\020\026\032\0020\027H\027J\r\020\r\032\0020\nH\007?\006\002\b\030J\r\020\013\032\0020\nH\007?\006\002\b\031J\016\020\032\032\0020\n2\006\020\033\032\0020\034J\r\020\002\032\0020\003H\007?\006\002\b\035J\r\020\b\032\0020\003H\007?\006\002\b\036J\r\020\f\032\0020\nH\007?\006\002\b\037J\r\020\t\032\0020\nH\007?\006\002\b J\b\020!\032\0020\003H\026J\025\020!\032\0020\0032\006\020\"\032\0020\nH\000?\006\002\b#J\r\020\004\032\0020\003H\007?\006\002\b$R\023\020\007\032\0020\0038\007?\006\b\n\000\032\004\b\007\020\017R\023\020\005\032\0020\0068\007?\006\b\n\000\032\004\b\005\020\020R\023\020\r\032\0020\n8\007?\006\b\n\000\032\004\b\r\020\021R\023\020\013\032\0020\n8\007?\006\b\n\000\032\004\b\013\020\021R\023\020\002\032\0020\0038\007?\006\b\n\000\032\004\b\002\020\017R\023\020\b\032\0020\0038\007?\006\b\n\000\032\004\b\b\020\017R\023\020\f\032\0020\n8\007?\006\b\n\000\032\004\b\f\020\021R\023\020\t\032\0020\n8\007?\006\b\n\000\032\004\b\t\020\021R\023\020\004\032\0020\0038\007?\006\b\n\000\032\004\b\004\020\017?\006'"}, d2={"Lokhttp3/Cookie;", "", "name", "", "value", "expiresAt", "", "domain", "path", "secure", "", "httpOnly", "persistent", "hostOnly", "(Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;ZZZZ)V", "()Ljava/lang/String;", "()J", "()Z", "-deprecated_domain", "equals", "other", "-deprecated_expiresAt", "hashCode", "", "-deprecated_hostOnly", "-deprecated_httpOnly", "matches", "url", "Lokhttp3/HttpUrl;", "-deprecated_name", "-deprecated_path", "-deprecated_persistent", "-deprecated_secure", "toString", "forObsoleteRfc2965", "toString$okhttp", "-deprecated_value", "Builder", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class Cookie
{
  public static final Companion Companion = new Companion(null);
  private static final Pattern DAY_OF_MONTH_PATTERN = Pattern.compile("(\\d{1,2})[^\\d]*");
  private static final Pattern MONTH_PATTERN;
  private static final Pattern TIME_PATTERN = Pattern.compile("(\\d{1,2}):(\\d{1,2}):(\\d{1,2})[^\\d]*");
  private static final Pattern YEAR_PATTERN = Pattern.compile("(\\d{2,4})[^\\d]*");
  private final String domain;
  private final long expiresAt;
  private final boolean hostOnly;
  private final boolean httpOnly;
  private final String name;
  private final String path;
  private final boolean persistent;
  private final boolean secure;
  private final String value;
  
  static
  {
    MONTH_PATTERN = Pattern.compile("(?i)(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec).*");
  }
  
  private Cookie(String paramString1, String paramString2, long paramLong, String paramString3, String paramString4, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    this.name = paramString1;
    this.value = paramString2;
    this.expiresAt = paramLong;
    this.domain = paramString3;
    this.path = paramString4;
    this.secure = paramBoolean1;
    this.httpOnly = paramBoolean2;
    this.persistent = paramBoolean3;
    this.hostOnly = paramBoolean4;
  }
  
  @JvmStatic
  public static final Cookie parse(HttpUrl paramHttpUrl, String paramString)
  {
    return Companion.parse(paramHttpUrl, paramString);
  }
  
  @JvmStatic
  public static final List<Cookie> parseAll(HttpUrl paramHttpUrl, Headers paramHeaders)
  {
    return Companion.parseAll(paramHttpUrl, paramHeaders);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="domain", imports={}))
  public final String -deprecated_domain()
  {
    return this.domain;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="expiresAt", imports={}))
  public final long -deprecated_expiresAt()
  {
    return this.expiresAt;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="hostOnly", imports={}))
  public final boolean -deprecated_hostOnly()
  {
    return this.hostOnly;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="httpOnly", imports={}))
  public final boolean -deprecated_httpOnly()
  {
    return this.httpOnly;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="name", imports={}))
  public final String -deprecated_name()
  {
    return this.name;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="path", imports={}))
  public final String -deprecated_path()
  {
    return this.path;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="persistent", imports={}))
  public final boolean -deprecated_persistent()
  {
    return this.persistent;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="secure", imports={}))
  public final boolean -deprecated_secure()
  {
    return this.secure;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="value", imports={}))
  public final String -deprecated_value()
  {
    return this.value;
  }
  
  public final String domain()
  {
    return this.domain;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof Cookie))
    {
      paramObject = (Cookie)paramObject;
      if ((Intrinsics.areEqual(paramObject.name, this.name)) && (Intrinsics.areEqual(paramObject.value, this.value)) && (paramObject.expiresAt == this.expiresAt) && (Intrinsics.areEqual(paramObject.domain, this.domain)) && (Intrinsics.areEqual(paramObject.path, this.path)) && (paramObject.secure == this.secure) && (paramObject.httpOnly == this.httpOnly) && (paramObject.persistent == this.persistent) && (paramObject.hostOnly == this.hostOnly)) {
        return true;
      }
    }
    boolean bool = false;
    return bool;
  }
  
  public final long expiresAt()
  {
    return this.expiresAt;
  }
  
  public int hashCode()
  {
    return ((((((((527 + this.name.hashCode()) * 31 + this.value.hashCode()) * 31 + .r8.backportedMethods.utility.Long.1.hashCode.hashCode(this.expiresAt)) * 31 + this.domain.hashCode()) * 31 + this.path.hashCode()) * 31 + .r8.backportedMethods.utility.Boolean.1.hashCode.hashCode(this.secure)) * 31 + .r8.backportedMethods.utility.Boolean.1.hashCode.hashCode(this.httpOnly)) * 31 + .r8.backportedMethods.utility.Boolean.1.hashCode.hashCode(this.persistent)) * 31 + .r8.backportedMethods.utility.Boolean.1.hashCode.hashCode(this.hostOnly);
  }
  
  public final boolean hostOnly()
  {
    return this.hostOnly;
  }
  
  public final boolean httpOnly()
  {
    return this.httpOnly;
  }
  
  public final boolean matches(HttpUrl paramHttpUrl)
  {
    Intrinsics.checkParameterIsNotNull(paramHttpUrl, "url");
    boolean bool1;
    if (this.hostOnly) {
      bool1 = Intrinsics.areEqual(paramHttpUrl.host(), this.domain);
    } else {
      bool1 = Companion.access$domainMatch(Companion, paramHttpUrl.host(), this.domain);
    }
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    if (!Companion.access$pathMatch(Companion, paramHttpUrl, this.path)) {
      return false;
    }
    if (this.secure)
    {
      bool1 = bool2;
      if (!paramHttpUrl.isHttps()) {}
    }
    else
    {
      bool1 = true;
    }
    return bool1;
  }
  
  public final String name()
  {
    return this.name;
  }
  
  public final String path()
  {
    return this.path;
  }
  
  public final boolean persistent()
  {
    return this.persistent;
  }
  
  public final boolean secure()
  {
    return this.secure;
  }
  
  public String toString()
  {
    return toString$okhttp(false);
  }
  
  public final String toString$okhttp(boolean paramBoolean)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.name);
    ((StringBuilder)localObject).append('=');
    ((StringBuilder)localObject).append(this.value);
    if (this.persistent) {
      if (this.expiresAt == Long.MIN_VALUE)
      {
        ((StringBuilder)localObject).append("; max-age=0");
      }
      else
      {
        ((StringBuilder)localObject).append("; expires=");
        ((StringBuilder)localObject).append(DatesKt.toHttpDateString(new Date(this.expiresAt)));
      }
    }
    if (!this.hostOnly)
    {
      ((StringBuilder)localObject).append("; domain=");
      if (paramBoolean) {
        ((StringBuilder)localObject).append(".");
      }
      ((StringBuilder)localObject).append(this.domain);
    }
    ((StringBuilder)localObject).append("; path=");
    ((StringBuilder)localObject).append(this.path);
    if (this.secure) {
      ((StringBuilder)localObject).append("; secure");
    }
    if (this.httpOnly) {
      ((StringBuilder)localObject).append("; httponly");
    }
    localObject = ((StringBuilder)localObject).toString();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "toString()");
    return localObject;
  }
  
  public final String value()
  {
    return this.value;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\000\n\002\020\t\n\000\n\002\020\013\n\002\b\007\n\002\030\002\n\002\b\002\030\0002\0020\001B\005?\006\002\020\002J\006\020\017\032\0020\020J\016\020\003\032\0020\0002\006\020\003\032\0020\004J\030\020\003\032\0020\0002\006\020\003\032\0020\0042\006\020\007\032\0020\bH\002J\016\020\005\032\0020\0002\006\020\005\032\0020\006J\016\020\021\032\0020\0002\006\020\003\032\0020\004J\006\020\t\032\0020\000J\016\020\n\032\0020\0002\006\020\n\032\0020\004J\016\020\013\032\0020\0002\006\020\013\032\0020\004J\006\020\r\032\0020\000J\016\020\016\032\0020\0002\006\020\016\032\0020\004R\020\020\003\032\004\030\0010\004X?\016?\006\002\n\000R\016\020\005\032\0020\006X?\016?\006\002\n\000R\016\020\007\032\0020\bX?\016?\006\002\n\000R\016\020\t\032\0020\bX?\016?\006\002\n\000R\020\020\n\032\004\030\0010\004X?\016?\006\002\n\000R\016\020\013\032\0020\004X?\016?\006\002\n\000R\016\020\f\032\0020\bX?\016?\006\002\n\000R\016\020\r\032\0020\bX?\016?\006\002\n\000R\020\020\016\032\004\030\0010\004X?\016?\006\002\n\000?\006\022"}, d2={"Lokhttp3/Cookie$Builder;", "", "()V", "domain", "", "expiresAt", "", "hostOnly", "", "httpOnly", "name", "path", "persistent", "secure", "value", "build", "Lokhttp3/Cookie;", "hostOnlyDomain", "okhttp"}, k=1, mv={1, 1, 16})
  public static final class Builder
  {
    private String domain;
    private long expiresAt = 253402300799999L;
    private boolean hostOnly;
    private boolean httpOnly;
    private String name;
    private String path = "/";
    private boolean persistent;
    private boolean secure;
    private String value;
    
    public Builder() {}
    
    private final Builder domain(String paramString, boolean paramBoolean)
    {
      Builder localBuilder = (Builder)this;
      Object localObject = HostnamesKt.toCanonicalHost(paramString);
      if (localObject != null)
      {
        localBuilder.domain = ((String)localObject);
        localBuilder.hostOnly = paramBoolean;
        return localBuilder;
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("unexpected domain: ");
      ((StringBuilder)localObject).append(paramString);
      throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject).toString()));
    }
    
    public final Cookie build()
    {
      String str1 = this.name;
      if (str1 != null)
      {
        String str2 = this.value;
        if (str2 != null)
        {
          long l = this.expiresAt;
          String str3 = this.domain;
          if (str3 != null) {
            return new Cookie(str1, str2, l, str3, this.path, this.secure, this.httpOnly, this.persistent, this.hostOnly, null);
          }
          throw ((Throwable)new NullPointerException("builder.domain == null"));
        }
        throw ((Throwable)new NullPointerException("builder.value == null"));
      }
      throw ((Throwable)new NullPointerException("builder.name == null"));
    }
    
    public final Builder domain(String paramString)
    {
      Intrinsics.checkParameterIsNotNull(paramString, "domain");
      return domain(paramString, false);
    }
    
    public final Builder expiresAt(long paramLong)
    {
      Builder localBuilder = (Builder)this;
      long l = paramLong;
      if (paramLong <= 0L) {
        l = Long.MIN_VALUE;
      }
      paramLong = l;
      if (l > 253402300799999L) {
        paramLong = 253402300799999L;
      }
      localBuilder.expiresAt = paramLong;
      localBuilder.persistent = true;
      return localBuilder;
    }
    
    public final Builder hostOnlyDomain(String paramString)
    {
      Intrinsics.checkParameterIsNotNull(paramString, "domain");
      return domain(paramString, true);
    }
    
    public final Builder httpOnly()
    {
      Builder localBuilder = (Builder)this;
      localBuilder.httpOnly = true;
      return localBuilder;
    }
    
    public final Builder name(String paramString)
    {
      Intrinsics.checkParameterIsNotNull(paramString, "name");
      Builder localBuilder = (Builder)this;
      if (Intrinsics.areEqual(StringsKt.trim((CharSequence)paramString).toString(), paramString))
      {
        localBuilder.name = paramString;
        return localBuilder;
      }
      throw ((Throwable)new IllegalArgumentException("name is not trimmed".toString()));
    }
    
    public final Builder path(String paramString)
    {
      Intrinsics.checkParameterIsNotNull(paramString, "path");
      Builder localBuilder = (Builder)this;
      if (StringsKt.startsWith$default(paramString, "/", false, 2, null))
      {
        localBuilder.path = paramString;
        return localBuilder;
      }
      throw ((Throwable)new IllegalArgumentException("path must start with '/'".toString()));
    }
    
    public final Builder secure()
    {
      Builder localBuilder = (Builder)this;
      localBuilder.secure = true;
      return localBuilder;
    }
    
    public final Builder value(String paramString)
    {
      Intrinsics.checkParameterIsNotNull(paramString, "value");
      Builder localBuilder = (Builder)this;
      if (Intrinsics.areEqual(StringsKt.trim((CharSequence)paramString).toString(), paramString))
      {
        localBuilder.value = paramString;
        return localBuilder;
      }
      throw ((Throwable)new IllegalArgumentException("value is not trimmed".toString()));
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000L\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\005\n\002\020\b\n\000\n\002\020\016\n\002\b\003\n\002\020\013\n\002\b\004\n\002\030\002\n\000\n\002\020\t\n\000\n\002\030\002\n\002\b\003\n\002\020 \n\000\n\002\030\002\n\002\b\007\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J(\020\t\032\0020\n2\006\020\013\032\0020\f2\006\020\r\032\0020\n2\006\020\016\032\0020\n2\006\020\017\032\0020\020H\002J\030\020\021\032\0020\0202\006\020\022\032\0020\f2\006\020\023\032\0020\fH\002J'\020\024\032\004\030\0010\0252\006\020\026\032\0020\0272\006\020\030\032\0020\0312\006\020\032\032\0020\fH\000?\006\002\b\033J\032\020\024\032\004\030\0010\0252\006\020\030\032\0020\0312\006\020\032\032\0020\fH\007J\036\020\034\032\b\022\004\022\0020\0250\0352\006\020\030\032\0020\0312\006\020\036\032\0020\037H\007J\020\020 \032\0020\f2\006\020!\032\0020\fH\002J \020\"\032\0020\0272\006\020!\032\0020\f2\006\020\r\032\0020\n2\006\020\016\032\0020\nH\002J\020\020#\032\0020\0272\006\020!\032\0020\fH\002J\030\020$\032\0020\0202\006\020\030\032\0020\0312\006\020%\032\0020\fH\002R\026\020\003\032\n \005*\004\030\0010\0040\004X?\004?\006\002\n\000R\026\020\006\032\n \005*\004\030\0010\0040\004X?\004?\006\002\n\000R\026\020\007\032\n \005*\004\030\0010\0040\004X?\004?\006\002\n\000R\026\020\b\032\n \005*\004\030\0010\0040\004X?\004?\006\002\n\000?\006&"}, d2={"Lokhttp3/Cookie$Companion;", "", "()V", "DAY_OF_MONTH_PATTERN", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "MONTH_PATTERN", "TIME_PATTERN", "YEAR_PATTERN", "dateCharacterOffset", "", "input", "", "pos", "limit", "invert", "", "domainMatch", "urlHost", "domain", "parse", "Lokhttp3/Cookie;", "currentTimeMillis", "", "url", "Lokhttp3/HttpUrl;", "setCookie", "parse$okhttp", "parseAll", "", "headers", "Lokhttp3/Headers;", "parseDomain", "s", "parseExpires", "parseMaxAge", "pathMatch", "path", "okhttp"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    private final int dateCharacterOffset(String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      while (paramInt1 < paramInt2)
      {
        int i = paramString.charAt(paramInt1);
        if (((i >= 32) || (i == 9)) && (i < 127) && ((48 > i) || (57 < i)) && ((97 > i) || (122 < i)) && ((65 > i) || (90 < i)) && (i != 58)) {
          i = 0;
        } else {
          i = 1;
        }
        if (i == (paramBoolean ^ true)) {
          return paramInt1;
        }
        paramInt1++;
      }
      return paramInt2;
    }
    
    private final boolean domainMatch(String paramString1, String paramString2)
    {
      boolean bool1 = Intrinsics.areEqual(paramString1, paramString2);
      boolean bool2 = true;
      if (bool1) {
        return true;
      }
      if ((!StringsKt.endsWith$default(paramString1, paramString2, false, 2, null)) || (paramString1.charAt(paramString1.length() - paramString2.length() - 1) != '.') || (Util.canParseAsIpAddress(paramString1))) {
        bool2 = false;
      }
      return bool2;
    }
    
    private final String parseDomain(String paramString)
    {
      if ((StringsKt.endsWith$default(paramString, ".", false, 2, null) ^ true))
      {
        paramString = HostnamesKt.toCanonicalHost(StringsKt.removePrefix(paramString, (CharSequence)"."));
        if (paramString != null) {
          return paramString;
        }
        throw ((Throwable)new IllegalArgumentException());
      }
      throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
    }
    
    private final long parseExpires(String paramString, int paramInt1, int paramInt2)
    {
      Companion localCompanion = (Companion)this;
      int i = localCompanion.dateCharacterOffset(paramString, paramInt1, paramInt2, false);
      Matcher localMatcher = Cookie.access$getTIME_PATTERN$cp().matcher((CharSequence)paramString);
      int j = -1;
      paramInt1 = j;
      int k = paramInt1;
      int m = k;
      int n = m;
      int i1 = n;
      int i2 = paramInt1;
      paramInt1 = j;
      while (i < paramInt2)
      {
        int i3 = localCompanion.dateCharacterOffset(paramString, i + 1, paramInt2, true);
        localMatcher.region(i, i3);
        Object localObject;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        if ((i2 == -1) && (localMatcher.usePattern(Cookie.access$getTIME_PATTERN$cp()).matches()))
        {
          localObject = localMatcher.group(1);
          Intrinsics.checkExpressionValueIsNotNull(localObject, "matcher.group(1)");
          j = Integer.parseInt((String)localObject);
          localObject = localMatcher.group(2);
          Intrinsics.checkExpressionValueIsNotNull(localObject, "matcher.group(2)");
          i4 = Integer.parseInt((String)localObject);
          localObject = localMatcher.group(3);
          Intrinsics.checkExpressionValueIsNotNull(localObject, "matcher.group(3)");
          i5 = Integer.parseInt((String)localObject);
          i6 = paramInt1;
          i7 = k;
          i8 = m;
        }
        else if ((k == -1) && (localMatcher.usePattern(Cookie.access$getDAY_OF_MONTH_PATTERN$cp()).matches()))
        {
          localObject = localMatcher.group(1);
          Intrinsics.checkExpressionValueIsNotNull(localObject, "matcher.group(1)");
          i7 = Integer.parseInt((String)localObject);
          i6 = paramInt1;
          j = i2;
          i8 = m;
          i4 = n;
          i5 = i1;
        }
        else if ((m == -1) && (localMatcher.usePattern(Cookie.access$getMONTH_PATTERN$cp()).matches()))
        {
          String str = localMatcher.group(1);
          Intrinsics.checkExpressionValueIsNotNull(str, "matcher.group(1)");
          localObject = Locale.US;
          Intrinsics.checkExpressionValueIsNotNull(localObject, "Locale.US");
          if (str != null)
          {
            localObject = str.toLowerCase((Locale)localObject);
            Intrinsics.checkExpressionValueIsNotNull(localObject, "(this as java.lang.String).toLowerCase(locale)");
            str = Cookie.access$getMONTH_PATTERN$cp().pattern();
            Intrinsics.checkExpressionValueIsNotNull(str, "MONTH_PATTERN.pattern()");
            i8 = StringsKt.indexOf$default((CharSequence)str, (String)localObject, 0, false, 6, null) / 4;
            i6 = paramInt1;
            j = i2;
            i7 = k;
            i4 = n;
            i5 = i1;
          }
          else
          {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
          }
        }
        else
        {
          i6 = paramInt1;
          j = i2;
          i7 = k;
          i8 = m;
          i4 = n;
          i5 = i1;
          if (paramInt1 == -1)
          {
            i6 = paramInt1;
            j = i2;
            i7 = k;
            i8 = m;
            i4 = n;
            i5 = i1;
            if (localMatcher.usePattern(Cookie.access$getYEAR_PATTERN$cp()).matches())
            {
              localObject = localMatcher.group(1);
              Intrinsics.checkExpressionValueIsNotNull(localObject, "matcher.group(1)");
              i6 = Integer.parseInt((String)localObject);
              i5 = i1;
              i4 = n;
              i8 = m;
              i7 = k;
              j = i2;
            }
          }
        }
        i = localCompanion.dateCharacterOffset(paramString, i3 + 1, paramInt2, false);
        paramInt1 = i6;
        i2 = j;
        k = i7;
        m = i8;
        n = i4;
        i1 = i5;
      }
      if (70 > paramInt1)
      {
        paramInt2 = paramInt1;
      }
      else
      {
        paramInt2 = paramInt1;
        if (99 >= paramInt1) {
          paramInt2 = paramInt1 + 1900;
        }
      }
      if (paramInt2 < 0)
      {
        paramInt1 = paramInt2;
      }
      else
      {
        paramInt1 = paramInt2;
        if (69 >= paramInt2) {
          paramInt1 = paramInt2 + 2000;
        }
      }
      if (paramInt1 >= 1601) {
        paramInt2 = 1;
      } else {
        paramInt2 = 0;
      }
      if (paramInt2 != 0)
      {
        if (m != -1) {
          paramInt2 = 1;
        } else {
          paramInt2 = 0;
        }
        if (paramInt2 != 0)
        {
          if ((1 <= k) && (31 >= k)) {
            paramInt2 = 1;
          } else {
            paramInt2 = 0;
          }
          if (paramInt2 != 0)
          {
            if ((i2 >= 0) && (23 >= i2)) {
              paramInt2 = 1;
            } else {
              paramInt2 = 0;
            }
            if (paramInt2 != 0)
            {
              if ((n >= 0) && (59 >= n)) {
                paramInt2 = 1;
              } else {
                paramInt2 = 0;
              }
              if (paramInt2 != 0)
              {
                if ((i1 >= 0) && (59 >= i1)) {
                  paramInt2 = 1;
                } else {
                  paramInt2 = 0;
                }
                if (paramInt2 != 0)
                {
                  paramString = new GregorianCalendar(Util.UTC);
                  paramString.setLenient(false);
                  paramString.set(1, paramInt1);
                  paramString.set(2, m - 1);
                  paramString.set(5, k);
                  paramString.set(11, i2);
                  paramString.set(12, n);
                  paramString.set(13, i1);
                  paramString.set(14, 0);
                  return paramString.getTimeInMillis();
                }
                throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
              }
              throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
            }
            throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
          }
          throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
        }
        throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
      }
      throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
    }
    
    private final long parseMaxAge(String paramString)
    {
      long l1 = Long.MIN_VALUE;
      try
      {
        long l2 = Long.parseLong(paramString);
        if (l2 > 0L) {
          l1 = l2;
        }
        return l1;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        CharSequence localCharSequence = (CharSequence)paramString;
        if (new Regex("-?\\d+").matches(localCharSequence))
        {
          if (!StringsKt.startsWith$default(paramString, "-", false, 2, null)) {
            l1 = Long.MAX_VALUE;
          }
          return l1;
        }
        throw ((Throwable)localNumberFormatException);
      }
    }
    
    private final boolean pathMatch(HttpUrl paramHttpUrl, String paramString)
    {
      paramHttpUrl = paramHttpUrl.encodedPath();
      if (Intrinsics.areEqual(paramHttpUrl, paramString)) {
        return true;
      }
      if (StringsKt.startsWith$default(paramHttpUrl, paramString, false, 2, null))
      {
        if (StringsKt.endsWith$default(paramString, "/", false, 2, null)) {
          return true;
        }
        if (paramHttpUrl.charAt(paramString.length()) == '/') {
          return true;
        }
      }
      return false;
    }
    
    @JvmStatic
    public final Cookie parse(HttpUrl paramHttpUrl, String paramString)
    {
      Intrinsics.checkParameterIsNotNull(paramHttpUrl, "url");
      Intrinsics.checkParameterIsNotNull(paramString, "setCookie");
      return ((Companion)this).parse$okhttp(System.currentTimeMillis(), paramHttpUrl, paramString);
    }
    
    public final Cookie parse$okhttp(long paramLong, HttpUrl paramHttpUrl, String paramString)
    {
      Intrinsics.checkParameterIsNotNull(paramHttpUrl, "url");
      Intrinsics.checkParameterIsNotNull(paramString, "setCookie");
      int i = Util.delimiterOffset$default(paramString, ';', 0, 0, 6, null);
      int j = Util.delimiterOffset$default(paramString, '=', 0, i, 2, null);
      if (j == i) {
        return null;
      }
      String str1 = Util.trimSubstring$default(paramString, 0, j, 1, null);
      int k;
      if (((CharSequence)str1).length() == 0) {
        k = 1;
      } else {
        k = 0;
      }
      if ((k == 0) && (Util.indexOfControlOrNonAscii(str1) == -1))
      {
        String str2 = Util.trimSubstring(paramString, j + 1, i);
        if (Util.indexOfControlOrNonAscii(str2) != -1) {
          return null;
        }
        localObject1 = (String)null;
        k = i + 1;
        i = paramString.length();
        localObject2 = localObject1;
        bool1 = false;
        boolean bool2 = bool1;
        bool3 = bool2;
        bool4 = true;
        l1 = -1L;
        for (l2 = 253402300799999L; k < i; l2 = l4)
        {
          j = Util.delimiterOffset(paramString, ';', k, i);
          int m = Util.delimiterOffset(paramString, '=', k, j);
          String str3 = Util.trimSubstring(paramString, k, m);
          if (m < j) {
            localObject3 = Util.trimSubstring(paramString, m + 1, j);
          } else {
            localObject3 = "";
          }
          if (StringsKt.equals(str3, "expires", true)) {}
          try
          {
            l3 = ((Companion)this).parseExpires((String)localObject3, 0, ((String)localObject3).length());
            l2 = l3;
            if (StringsKt.equals(str3, "max-age", true))
            {
              l3 = ((Companion)this).parseMaxAge((String)localObject3);
              l1 = l3;
              bool5 = true;
              localObject3 = localObject1;
              localObject5 = localObject2;
              bool6 = bool1;
              bool7 = bool4;
              l3 = l1;
              l4 = l2;
            }
            else if (StringsKt.equals(str3, "domain", true))
            {
              localObject3 = ((Companion)this).parseDomain((String)localObject3);
              bool7 = false;
              localObject5 = localObject2;
              bool6 = bool1;
              bool5 = bool3;
              l3 = l1;
              l4 = l2;
            }
            else if (StringsKt.equals(str3, "path", true))
            {
              localObject5 = localObject3;
              localObject3 = localObject1;
              bool6 = bool1;
              bool5 = bool3;
              bool7 = bool4;
              l3 = l1;
              l4 = l2;
            }
            else if (StringsKt.equals(str3, "secure", true))
            {
              bool6 = true;
              localObject3 = localObject1;
              localObject5 = localObject2;
              bool5 = bool3;
              bool7 = bool4;
              l3 = l1;
              l4 = l2;
            }
            else
            {
              localObject3 = localObject1;
              localObject5 = localObject2;
              bool6 = bool1;
              bool5 = bool3;
              bool7 = bool4;
              l3 = l1;
              l4 = l2;
              if (StringsKt.equals(str3, "httponly", true))
              {
                bool2 = true;
                l4 = l2;
                l3 = l1;
                bool7 = bool4;
                bool5 = bool3;
                bool6 = bool1;
                localObject5 = localObject2;
                localObject3 = localObject1;
              }
            }
          }
          catch (IllegalArgumentException|NumberFormatException localIllegalArgumentException)
          {
            for (;;)
            {
              Object localObject4 = localObject1;
              Object localObject5 = localObject2;
              boolean bool6 = bool1;
              boolean bool5 = bool3;
              boolean bool7 = bool4;
              long l3 = l1;
              long l4 = l2;
            }
          }
          k = j + 1;
          localObject1 = localObject3;
          localObject2 = localObject5;
          bool1 = bool6;
          bool3 = bool5;
          bool4 = bool7;
          l1 = l3;
        }
        l3 = Long.MIN_VALUE;
        if (l1 == Long.MIN_VALUE) {
          paramLong = l3;
        }
        for (;;)
        {
          break;
          if (l1 != -1L)
          {
            if (l1 <= 9223372036854775L) {
              l2 = l1 * '?';
            } else {
              l2 = Long.MAX_VALUE;
            }
            l2 = paramLong + l2;
            if (l2 >= paramLong)
            {
              paramLong = l2;
              if (l2 <= 253402300799999L) {
                break;
              }
            }
            else
            {
              paramLong = 253402300799999L;
            }
          }
          else
          {
            paramLong = l2;
          }
        }
        Object localObject3 = paramHttpUrl.host();
        if (localObject1 == null)
        {
          paramString = (String)localObject3;
        }
        else
        {
          if (!((Companion)this).domainMatch((String)localObject3, (String)localObject1)) {
            return null;
          }
          paramString = (String)localObject1;
        }
        if ((((String)localObject3).length() != paramString.length()) && (PublicSuffixDatabase.Companion.get().getEffectiveTldPlusOne(paramString) == null)) {
          return null;
        }
        localObject1 = "/";
        if ((localObject2 != null) && (StringsKt.startsWith$default((String)localObject2, "/", false, 2, null)))
        {
          paramHttpUrl = (HttpUrl)localObject2;
        }
        else
        {
          localObject2 = paramHttpUrl.encodedPath();
          k = StringsKt.lastIndexOf$default((CharSequence)localObject2, '/', 0, false, 6, null);
          paramHttpUrl = (HttpUrl)localObject1;
          if (k != 0) {
            if (localObject2 != null)
            {
              paramHttpUrl = ((String)localObject2).substring(0, k);
              Intrinsics.checkExpressionValueIsNotNull(paramHttpUrl, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            }
            else
            {
              throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
          }
        }
        return new Cookie(str1, str2, paramLong, paramString, paramHttpUrl, bool1, bool2, bool3, bool4, null);
      }
      return null;
    }
    
    @JvmStatic
    public final List<Cookie> parseAll(HttpUrl paramHttpUrl, Headers paramHeaders)
    {
      Intrinsics.checkParameterIsNotNull(paramHttpUrl, "url");
      Intrinsics.checkParameterIsNotNull(paramHeaders, "headers");
      List localList = paramHeaders.values("Set-Cookie");
      paramHeaders = (List)null;
      int i = localList.size();
      int j = 0;
      while (j < i)
      {
        Cookie localCookie = ((Companion)this).parse(paramHttpUrl, (String)localList.get(j));
        Object localObject = paramHeaders;
        if (localCookie != null)
        {
          localObject = paramHeaders;
          if (paramHeaders == null) {
            localObject = (List)new ArrayList();
          }
          ((List)localObject).add(localCookie);
        }
        j++;
        paramHeaders = (Headers)localObject;
      }
      if (paramHeaders != null)
      {
        paramHttpUrl = Collections.unmodifiableList(paramHeaders);
        Intrinsics.checkExpressionValueIsNotNull(paramHttpUrl, "Collections.unmodifiableList(cookies)");
      }
      else
      {
        paramHttpUrl = CollectionsKt.emptyList();
      }
      return paramHttpUrl;
    }
  }
}

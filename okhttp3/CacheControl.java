package okhttp3;

import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.internal.Util;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\020\000\n\000\n\002\020\013\n\002\b\002\n\002\020\b\n\002\b\n\n\002\020\016\n\002\b\021\030\000 !2\0020\001:\002 !Bq\b\002\022\006\020\002\032\0020\003\022\006\020\004\032\0020\003\022\006\020\005\032\0020\006\022\006\020\007\032\0020\006\022\006\020\b\032\0020\003\022\006\020\t\032\0020\003\022\006\020\n\032\0020\003\022\006\020\013\032\0020\006\022\006\020\f\032\0020\006\022\006\020\r\032\0020\003\022\006\020\016\032\0020\003\022\006\020\017\032\0020\003\022\b\020\020\032\004\030\0010\021?\006\002\020\022J\r\020\017\032\0020\003H\007?\006\002\b\025J\r\020\005\032\0020\006H\007?\006\002\b\026J\r\020\013\032\0020\006H\007?\006\002\b\027J\r\020\f\032\0020\006H\007?\006\002\b\030J\r\020\n\032\0020\003H\007?\006\002\b\031J\r\020\002\032\0020\003H\007?\006\002\b\032J\r\020\004\032\0020\003H\007?\006\002\b\033J\r\020\016\032\0020\003H\007?\006\002\b\034J\r\020\r\032\0020\003H\007?\006\002\b\035J\r\020\007\032\0020\006H\007?\006\002\b\036J\b\020\037\032\0020\021H\026R\020\020\020\032\004\030\0010\021X?\016?\006\002\n\000R\023\020\017\032\0020\0038\007?\006\b\n\000\032\004\b\017\020\023R\021\020\b\032\0020\003?\006\b\n\000\032\004\b\b\020\023R\021\020\t\032\0020\003?\006\b\n\000\032\004\b\t\020\023R\023\020\005\032\0020\0068\007?\006\b\n\000\032\004\b\005\020\024R\023\020\013\032\0020\0068\007?\006\b\n\000\032\004\b\013\020\024R\023\020\f\032\0020\0068\007?\006\b\n\000\032\004\b\f\020\024R\023\020\n\032\0020\0038\007?\006\b\n\000\032\004\b\n\020\023R\023\020\002\032\0020\0038\007?\006\b\n\000\032\004\b\002\020\023R\023\020\004\032\0020\0038\007?\006\b\n\000\032\004\b\004\020\023R\023\020\016\032\0020\0038\007?\006\b\n\000\032\004\b\016\020\023R\023\020\r\032\0020\0038\007?\006\b\n\000\032\004\b\r\020\023R\023\020\007\032\0020\0068\007?\006\b\n\000\032\004\b\007\020\024?\006\""}, d2={"Lokhttp3/CacheControl;", "", "noCache", "", "noStore", "maxAgeSeconds", "", "sMaxAgeSeconds", "isPrivate", "isPublic", "mustRevalidate", "maxStaleSeconds", "minFreshSeconds", "onlyIfCached", "noTransform", "immutable", "headerValue", "", "(ZZIIZZZIIZZZLjava/lang/String;)V", "()Z", "()I", "-deprecated_immutable", "-deprecated_maxAgeSeconds", "-deprecated_maxStaleSeconds", "-deprecated_minFreshSeconds", "-deprecated_mustRevalidate", "-deprecated_noCache", "-deprecated_noStore", "-deprecated_noTransform", "-deprecated_onlyIfCached", "-deprecated_sMaxAgeSeconds", "toString", "Builder", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class CacheControl
{
  public static final Companion Companion = new Companion(null);
  public static final CacheControl FORCE_CACHE = new Builder().onlyIfCached().maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS).build();
  public static final CacheControl FORCE_NETWORK = new Builder().noCache().build();
  private String headerValue;
  private final boolean immutable;
  private final boolean isPrivate;
  private final boolean isPublic;
  private final int maxAgeSeconds;
  private final int maxStaleSeconds;
  private final int minFreshSeconds;
  private final boolean mustRevalidate;
  private final boolean noCache;
  private final boolean noStore;
  private final boolean noTransform;
  private final boolean onlyIfCached;
  private final int sMaxAgeSeconds;
  
  private CacheControl(boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, int paramInt3, int paramInt4, boolean paramBoolean6, boolean paramBoolean7, boolean paramBoolean8, String paramString)
  {
    this.noCache = paramBoolean1;
    this.noStore = paramBoolean2;
    this.maxAgeSeconds = paramInt1;
    this.sMaxAgeSeconds = paramInt2;
    this.isPrivate = paramBoolean3;
    this.isPublic = paramBoolean4;
    this.mustRevalidate = paramBoolean5;
    this.maxStaleSeconds = paramInt3;
    this.minFreshSeconds = paramInt4;
    this.onlyIfCached = paramBoolean6;
    this.noTransform = paramBoolean7;
    this.immutable = paramBoolean8;
    this.headerValue = paramString;
  }
  
  @JvmStatic
  public static final CacheControl parse(Headers paramHeaders)
  {
    return Companion.parse(paramHeaders);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="immutable", imports={}))
  public final boolean -deprecated_immutable()
  {
    return this.immutable;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="maxAgeSeconds", imports={}))
  public final int -deprecated_maxAgeSeconds()
  {
    return this.maxAgeSeconds;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="maxStaleSeconds", imports={}))
  public final int -deprecated_maxStaleSeconds()
  {
    return this.maxStaleSeconds;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="minFreshSeconds", imports={}))
  public final int -deprecated_minFreshSeconds()
  {
    return this.minFreshSeconds;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="mustRevalidate", imports={}))
  public final boolean -deprecated_mustRevalidate()
  {
    return this.mustRevalidate;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="noCache", imports={}))
  public final boolean -deprecated_noCache()
  {
    return this.noCache;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="noStore", imports={}))
  public final boolean -deprecated_noStore()
  {
    return this.noStore;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="noTransform", imports={}))
  public final boolean -deprecated_noTransform()
  {
    return this.noTransform;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="onlyIfCached", imports={}))
  public final boolean -deprecated_onlyIfCached()
  {
    return this.onlyIfCached;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="sMaxAgeSeconds", imports={}))
  public final int -deprecated_sMaxAgeSeconds()
  {
    return this.sMaxAgeSeconds;
  }
  
  public final boolean immutable()
  {
    return this.immutable;
  }
  
  public final boolean isPrivate()
  {
    return this.isPrivate;
  }
  
  public final boolean isPublic()
  {
    return this.isPublic;
  }
  
  public final int maxAgeSeconds()
  {
    return this.maxAgeSeconds;
  }
  
  public final int maxStaleSeconds()
  {
    return this.maxStaleSeconds;
  }
  
  public final int minFreshSeconds()
  {
    return this.minFreshSeconds;
  }
  
  public final boolean mustRevalidate()
  {
    return this.mustRevalidate;
  }
  
  public final boolean noCache()
  {
    return this.noCache;
  }
  
  public final boolean noStore()
  {
    return this.noStore;
  }
  
  public final boolean noTransform()
  {
    return this.noTransform;
  }
  
  public final boolean onlyIfCached()
  {
    return this.onlyIfCached;
  }
  
  public final int sMaxAgeSeconds()
  {
    return this.sMaxAgeSeconds;
  }
  
  public String toString()
  {
    String str = this.headerValue;
    Object localObject = str;
    if (str == null)
    {
      localObject = new StringBuilder();
      if (this.noCache) {
        ((StringBuilder)localObject).append("no-cache, ");
      }
      if (this.noStore) {
        ((StringBuilder)localObject).append("no-store, ");
      }
      if (this.maxAgeSeconds != -1)
      {
        ((StringBuilder)localObject).append("max-age=");
        ((StringBuilder)localObject).append(this.maxAgeSeconds);
        ((StringBuilder)localObject).append(", ");
      }
      if (this.sMaxAgeSeconds != -1)
      {
        ((StringBuilder)localObject).append("s-maxage=");
        ((StringBuilder)localObject).append(this.sMaxAgeSeconds);
        ((StringBuilder)localObject).append(", ");
      }
      if (this.isPrivate) {
        ((StringBuilder)localObject).append("private, ");
      }
      if (this.isPublic) {
        ((StringBuilder)localObject).append("public, ");
      }
      if (this.mustRevalidate) {
        ((StringBuilder)localObject).append("must-revalidate, ");
      }
      if (this.maxStaleSeconds != -1)
      {
        ((StringBuilder)localObject).append("max-stale=");
        ((StringBuilder)localObject).append(this.maxStaleSeconds);
        ((StringBuilder)localObject).append(", ");
      }
      if (this.minFreshSeconds != -1)
      {
        ((StringBuilder)localObject).append("min-fresh=");
        ((StringBuilder)localObject).append(this.minFreshSeconds);
        ((StringBuilder)localObject).append(", ");
      }
      if (this.onlyIfCached) {
        ((StringBuilder)localObject).append("only-if-cached, ");
      }
      if (this.noTransform) {
        ((StringBuilder)localObject).append("no-transform, ");
      }
      if (this.immutable) {
        ((StringBuilder)localObject).append("immutable, ");
      }
      int i;
      if (((CharSequence)localObject).length() == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        return "";
      }
      ((StringBuilder)localObject).delete(((StringBuilder)localObject).length() - 2, ((StringBuilder)localObject).length());
      localObject = ((StringBuilder)localObject).toString();
      Intrinsics.checkExpressionValueIsNotNull(localObject, "StringBuilder().apply(builderAction).toString()");
      this.headerValue = ((String)localObject);
    }
    return localObject;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0000\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\013\n\000\n\002\020\b\n\002\b\007\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\t\n\000\030\0002\0020\001B\005?\006\002\020\002J\006\020\r\032\0020\016J\006\020\003\032\0020\000J\026\020\017\032\0020\0002\006\020\017\032\0020\0062\006\020\020\032\0020\021J\026\020\022\032\0020\0002\006\020\022\032\0020\0062\006\020\020\032\0020\021J\026\020\023\032\0020\0002\006\020\023\032\0020\0062\006\020\020\032\0020\021J\006\020\t\032\0020\000J\006\020\n\032\0020\000J\006\020\013\032\0020\000J\006\020\f\032\0020\000J\f\020\024\032\0020\006*\0020\025H\002R\016\020\003\032\0020\004X?\016?\006\002\n\000R\016\020\005\032\0020\006X?\016?\006\002\n\000R\016\020\007\032\0020\006X?\016?\006\002\n\000R\016\020\b\032\0020\006X?\016?\006\002\n\000R\016\020\t\032\0020\004X?\016?\006\002\n\000R\016\020\n\032\0020\004X?\016?\006\002\n\000R\016\020\013\032\0020\004X?\016?\006\002\n\000R\016\020\f\032\0020\004X?\016?\006\002\n\000?\006\026"}, d2={"Lokhttp3/CacheControl$Builder;", "", "()V", "immutable", "", "maxAgeSeconds", "", "maxStaleSeconds", "minFreshSeconds", "noCache", "noStore", "noTransform", "onlyIfCached", "build", "Lokhttp3/CacheControl;", "maxAge", "timeUnit", "Ljava/util/concurrent/TimeUnit;", "maxStale", "minFresh", "clampToInt", "", "okhttp"}, k=1, mv={1, 1, 16})
  public static final class Builder
  {
    private boolean immutable;
    private int maxAgeSeconds = -1;
    private int maxStaleSeconds = -1;
    private int minFreshSeconds = -1;
    private boolean noCache;
    private boolean noStore;
    private boolean noTransform;
    private boolean onlyIfCached;
    
    public Builder() {}
    
    private final int clampToInt(long paramLong)
    {
      int i = Integer.MAX_VALUE;
      if (paramLong <= Integer.MAX_VALUE) {
        i = (int)paramLong;
      }
      return i;
    }
    
    public final CacheControl build()
    {
      return new CacheControl(this.noCache, this.noStore, this.maxAgeSeconds, -1, false, false, false, this.maxStaleSeconds, this.minFreshSeconds, this.onlyIfCached, this.noTransform, this.immutable, null, null);
    }
    
    public final Builder immutable()
    {
      Builder localBuilder = (Builder)this;
      localBuilder.immutable = true;
      return localBuilder;
    }
    
    public final Builder maxAge(int paramInt, TimeUnit paramTimeUnit)
    {
      Intrinsics.checkParameterIsNotNull(paramTimeUnit, "timeUnit");
      Builder localBuilder = (Builder)this;
      int i;
      if (paramInt >= 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        localBuilder.maxAgeSeconds = localBuilder.clampToInt(paramTimeUnit.toSeconds(paramInt));
        return localBuilder;
      }
      paramTimeUnit = new StringBuilder();
      paramTimeUnit.append("maxAge < 0: ");
      paramTimeUnit.append(paramInt);
      throw ((Throwable)new IllegalArgumentException(paramTimeUnit.toString().toString()));
    }
    
    public final Builder maxStale(int paramInt, TimeUnit paramTimeUnit)
    {
      Intrinsics.checkParameterIsNotNull(paramTimeUnit, "timeUnit");
      Builder localBuilder = (Builder)this;
      int i;
      if (paramInt >= 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        localBuilder.maxStaleSeconds = localBuilder.clampToInt(paramTimeUnit.toSeconds(paramInt));
        return localBuilder;
      }
      paramTimeUnit = new StringBuilder();
      paramTimeUnit.append("maxStale < 0: ");
      paramTimeUnit.append(paramInt);
      throw ((Throwable)new IllegalArgumentException(paramTimeUnit.toString().toString()));
    }
    
    public final Builder minFresh(int paramInt, TimeUnit paramTimeUnit)
    {
      Intrinsics.checkParameterIsNotNull(paramTimeUnit, "timeUnit");
      Builder localBuilder = (Builder)this;
      int i;
      if (paramInt >= 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        localBuilder.minFreshSeconds = localBuilder.clampToInt(paramTimeUnit.toSeconds(paramInt));
        return localBuilder;
      }
      paramTimeUnit = new StringBuilder();
      paramTimeUnit.append("minFresh < 0: ");
      paramTimeUnit.append(paramInt);
      throw ((Throwable)new IllegalArgumentException(paramTimeUnit.toString().toString()));
    }
    
    public final Builder noCache()
    {
      Builder localBuilder = (Builder)this;
      localBuilder.noCache = true;
      return localBuilder;
    }
    
    public final Builder noStore()
    {
      Builder localBuilder = (Builder)this;
      localBuilder.noStore = true;
      return localBuilder;
    }
    
    public final Builder noTransform()
    {
      Builder localBuilder = (Builder)this;
      localBuilder.noTransform = true;
      return localBuilder;
    }
    
    public final Builder onlyIfCached()
    {
      Builder localBuilder = (Builder)this;
      localBuilder.onlyIfCached = true;
      return localBuilder;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\b\n\002\020\016\n\002\b\003\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\020\020\006\032\0020\0042\006\020\007\032\0020\bH\007J\036\020\t\032\0020\n*\0020\0132\006\020\f\032\0020\0132\b\b\002\020\r\032\0020\nH\002R\020\020\003\032\0020\0048\006X?\004?\006\002\n\000R\020\020\005\032\0020\0048\006X?\004?\006\002\n\000?\006\016"}, d2={"Lokhttp3/CacheControl$Companion;", "", "()V", "FORCE_CACHE", "Lokhttp3/CacheControl;", "FORCE_NETWORK", "parse", "headers", "Lokhttp3/Headers;", "indexOfElement", "", "", "characters", "startIndex", "okhttp"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    private final int indexOfElement(String paramString1, String paramString2, int paramInt)
    {
      int i = paramString1.length();
      while (paramInt < i)
      {
        if (StringsKt.contains$default((CharSequence)paramString2, paramString1.charAt(paramInt), false, 2, null)) {
          return paramInt;
        }
        paramInt++;
      }
      return paramString1.length();
    }
    
    @JvmStatic
    public final CacheControl parse(Headers paramHeaders)
    {
      Intrinsics.checkParameterIsNotNull(paramHeaders, "headers");
      String str1 = (String)null;
      int i = paramHeaders.size();
      boolean bool1 = true;
      Object localObject1 = str1;
      int j = 1;
      int k = 0;
      boolean bool2 = false;
      boolean bool3 = false;
      int m = -1;
      int n = -1;
      boolean bool4 = false;
      boolean bool5 = false;
      boolean bool6 = false;
      int i1 = -1;
      int i2 = -1;
      boolean bool7 = false;
      boolean bool8 = false;
      boolean bool9 = false;
      for (;;)
      {
        Object localObject2 = paramHeaders;
        if (k >= i) {
          break;
        }
        String str2 = ((Headers)localObject2).name(k);
        String str3 = ((Headers)localObject2).value(k);
        if (StringsKt.equals(str2, "Cache-Control", bool1))
        {
          if (localObject1 == null)
          {
            localObject1 = str3;
            break label133;
          }
        }
        else {
          if (!StringsKt.equals(str2, "Pragma", bool1)) {
            break label824;
          }
        }
        j = 0;
        label133:
        int i3 = 0;
        int i4 = i;
        i = i3;
        while (i < str3.length())
        {
          localObject2 = (Companion)this;
          i3 = ((Companion)localObject2).indexOfElement(str3, "=,;", i);
          if (str3 != null)
          {
            str2 = str3.substring(i, i3);
            Intrinsics.checkExpressionValueIsNotNull(str2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            if (str2 != null)
            {
              str2 = StringsKt.trim((CharSequence)str2).toString();
              if ((i3 != str3.length()) && (str3.charAt(i3) != ',') && (str3.charAt(i3) != ';'))
              {
                i3 = Util.indexOfNonWhitespace(str3, i3 + 1);
                if ((i3 < str3.length()) && (str3.charAt(i3) == '"'))
                {
                  i = i3 + 1;
                  i3 = StringsKt.indexOf$default((CharSequence)str3, '"', i, false, 4, null);
                  if (str3 != null)
                  {
                    localObject2 = str3.substring(i, i3);
                    Intrinsics.checkExpressionValueIsNotNull(localObject2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    i = i3 + 1;
                  }
                  else
                  {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                  }
                }
                else
                {
                  i = ((Companion)localObject2).indexOfElement(str3, ",;", i3);
                  if (str3 != null)
                  {
                    localObject2 = str3.substring(i3, i);
                    Intrinsics.checkExpressionValueIsNotNull(localObject2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    if (localObject2 != null) {
                      localObject2 = StringsKt.trim((CharSequence)localObject2).toString();
                    } else {
                      throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                    }
                  }
                  else
                  {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                  }
                }
              }
              else
              {
                i = i3 + 1;
                localObject2 = str1;
              }
              boolean bool10 = true;
              if (StringsKt.equals("no-cache", str2, true)) {
                bool2 = true;
              }
              for (;;)
              {
                break;
                if (StringsKt.equals("no-store", str2, true))
                {
                  bool3 = true;
                }
                else
                {
                  int i5;
                  boolean bool11;
                  int i6;
                  if (StringsKt.equals("max-age", str2, true))
                  {
                    i5 = Util.toNonNegativeInt((String)localObject2, -1);
                    bool11 = bool8;
                    bool1 = bool7;
                    i3 = i2;
                    i6 = n;
                  }
                  for (;;)
                  {
                    m = i5;
                    n = i6;
                    i2 = i3;
                    bool7 = bool1;
                    bool8 = bool11;
                    break;
                    if (StringsKt.equals("s-maxage", str2, true))
                    {
                      i6 = Util.toNonNegativeInt((String)localObject2, -1);
                      i5 = m;
                      i3 = i2;
                      bool1 = bool7;
                      bool11 = bool8;
                    }
                    else
                    {
                      if (StringsKt.equals("private", str2, true))
                      {
                        bool4 = true;
                        break;
                      }
                      if (StringsKt.equals("public", str2, true))
                      {
                        bool5 = true;
                        break;
                      }
                      if (StringsKt.equals("must-revalidate", str2, true))
                      {
                        bool6 = true;
                        break;
                      }
                      if (StringsKt.equals("max-stale", str2, true))
                      {
                        i1 = Util.toNonNegativeInt((String)localObject2, Integer.MAX_VALUE);
                        break;
                      }
                      if (StringsKt.equals("min-fresh", str2, true))
                      {
                        i3 = Util.toNonNegativeInt((String)localObject2, -1);
                        i5 = m;
                        i6 = n;
                        bool1 = bool7;
                        bool11 = bool8;
                      }
                      else if (StringsKt.equals("only-if-cached", str2, true))
                      {
                        bool1 = true;
                        i5 = m;
                        i6 = n;
                        i3 = i2;
                        bool11 = bool8;
                      }
                      else if (StringsKt.equals("no-transform", str2, true))
                      {
                        bool11 = true;
                        i5 = m;
                        i6 = n;
                        i3 = i2;
                        bool1 = bool7;
                      }
                      else
                      {
                        i5 = m;
                        i6 = n;
                        i3 = i2;
                        bool1 = bool7;
                        bool11 = bool8;
                        if (StringsKt.equals("immutable", str2, true))
                        {
                          bool9 = true;
                          i5 = m;
                          i6 = n;
                          i3 = i2;
                          bool1 = bool7;
                          bool11 = bool8;
                        }
                      }
                    }
                  }
                }
              }
              bool1 = bool10;
            }
            else
            {
              throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            }
          }
          else
          {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
          }
        }
        i = i4;
        label824:
        k++;
      }
      if (j == 0) {
        paramHeaders = str1;
      } else {
        paramHeaders = (Headers)localObject1;
      }
      return new CacheControl(bool2, bool3, m, n, bool4, bool5, bool6, i1, i2, bool7, bool8, bool9, paramHeaders, null);
    }
  }
}

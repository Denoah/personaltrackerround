package okhttp3.internal.publicsuffix;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.IDN;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import okhttp3.internal.Util;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;
import okio.Source;

@Metadata(bv={1, 0, 3}, d1={"\0004\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\022\n\002\b\002\n\002\030\002\n\000\n\002\020 \n\002\020\016\n\002\b\004\n\002\020\002\n\002\b\004\030\000 \0242\0020\001:\001\024B\005?\006\002\020\002J\034\020\n\032\b\022\004\022\0020\f0\0132\f\020\r\032\b\022\004\022\0020\f0\013H\002J\020\020\016\032\004\030\0010\f2\006\020\017\032\0020\fJ\b\020\020\032\0020\021H\002J\b\020\022\032\0020\021H\002J\026\020\023\032\0020\0212\006\020\007\032\0020\0062\006\020\005\032\0020\006R\016\020\003\032\0020\004X?\004?\006\002\n\000R\016\020\005\032\0020\006X?.?\006\002\n\000R\016\020\007\032\0020\006X?.?\006\002\n\000R\016\020\b\032\0020\tX?\004?\006\002\n\000?\006\025"}, d2={"Lokhttp3/internal/publicsuffix/PublicSuffixDatabase;", "", "()V", "listRead", "Ljava/util/concurrent/atomic/AtomicBoolean;", "publicSuffixExceptionListBytes", "", "publicSuffixListBytes", "readCompleteLatch", "Ljava/util/concurrent/CountDownLatch;", "findMatchingRule", "", "", "domainLabels", "getEffectiveTldPlusOne", "domain", "readTheList", "", "readTheListUninterruptibly", "setListBytes", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class PublicSuffixDatabase
{
  public static final Companion Companion = new Companion(null);
  private static final char EXCEPTION_MARKER = '!';
  private static final List<String> PREVAILING_RULE = CollectionsKt.listOf("*");
  public static final String PUBLIC_SUFFIX_RESOURCE = "publicsuffixes.gz";
  private static final byte[] WILDCARD_LABEL = { (byte)42 };
  private static final PublicSuffixDatabase instance = new PublicSuffixDatabase();
  private final AtomicBoolean listRead = new AtomicBoolean(false);
  private byte[] publicSuffixExceptionListBytes;
  private byte[] publicSuffixListBytes;
  private final CountDownLatch readCompleteLatch = new CountDownLatch(1);
  
  public PublicSuffixDatabase() {}
  
  private final List<String> findMatchingRule(List<String> paramList)
  {
    if ((!this.listRead.get()) && (this.listRead.compareAndSet(false, true))) {
      readTheListUninterruptibly();
    } else {
      try
      {
        this.readCompleteLatch.await();
      }
      catch (InterruptedException localInterruptedException)
      {
        Thread.currentThread().interrupt();
      }
    }
    int i;
    if (((PublicSuffixDatabase)this).publicSuffixListBytes != null) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      int j = paramList.size();
      Object localObject1 = new byte[j][];
      i = 0;
      while (i < j)
      {
        localObject2 = (String)paramList.get(i);
        localObject3 = StandardCharsets.UTF_8;
        Intrinsics.checkExpressionValueIsNotNull(localObject3, "UTF_8");
        if (localObject2 != null)
        {
          localObject2 = ((String)localObject2).getBytes((Charset)localObject3);
          Intrinsics.checkExpressionValueIsNotNull(localObject2, "(this as java.lang.String).getBytes(charset)");
          localObject1[i] = localObject2;
          i++;
        }
        else
        {
          throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
      }
      byte[][] arrayOfByte = (byte[][])localObject1;
      paramList = (String)null;
      j = arrayOfByte.length;
      for (i = 0; i < j; i++)
      {
        localObject1 = Companion;
        localObject2 = this.publicSuffixListBytes;
        if (localObject2 == null) {
          Intrinsics.throwUninitializedPropertyAccessException("publicSuffixListBytes");
        }
        localObject1 = Companion.access$binarySearch((Companion)localObject1, (byte[])localObject2, arrayOfByte, i);
        if (localObject1 != null) {
          break label224;
        }
      }
      localObject1 = paramList;
      label224:
      Object localObject4 = (Object[])arrayOfByte;
      if (localObject4.length > 1)
      {
        localObject3 = (byte[][])localObject4.clone();
        j = ((Object[])localObject3).length;
        for (i = 0; i < j - 1; i++)
        {
          localObject3[i] = WILDCARD_LABEL;
          Companion localCompanion = Companion;
          localObject2 = this.publicSuffixListBytes;
          if (localObject2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("publicSuffixListBytes");
          }
          localObject2 = Companion.access$binarySearch(localCompanion, (byte[])localObject2, (byte[][])localObject3, i);
          if (localObject2 != null) {
            break label323;
          }
        }
      }
      Object localObject2 = paramList;
      label323:
      Object localObject3 = paramList;
      if (localObject2 != null)
      {
        j = localObject4.length;
        for (i = 0;; i++)
        {
          localObject3 = paramList;
          if (i >= j - 1) {
            break;
          }
          localObject4 = Companion;
          localObject3 = this.publicSuffixExceptionListBytes;
          if (localObject3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("publicSuffixExceptionListBytes");
          }
          localObject3 = Companion.access$binarySearch((Companion)localObject4, (byte[])localObject3, arrayOfByte, i);
          if (localObject3 != null) {
            break;
          }
        }
      }
      if (localObject3 != null)
      {
        paramList = new StringBuilder();
        paramList.append('!');
        paramList.append((String)localObject3);
        return StringsKt.split$default((CharSequence)paramList.toString(), new char[] { '.' }, false, 0, 6, null);
      }
      if ((localObject1 == null) && (localObject2 == null)) {
        return PREVAILING_RULE;
      }
      if (localObject1 != null)
      {
        paramList = StringsKt.split$default((CharSequence)localObject1, new char[] { '.' }, false, 0, 6, null);
        if (paramList != null) {}
      }
      else
      {
        paramList = CollectionsKt.emptyList();
      }
      if (localObject2 != null)
      {
        localObject1 = StringsKt.split$default((CharSequence)localObject2, new char[] { '.' }, false, 0, 6, null);
        if (localObject1 != null) {}
      }
      else
      {
        localObject1 = CollectionsKt.emptyList();
      }
      if (paramList.size() <= ((List)localObject1).size()) {
        paramList = (List<String>)localObject1;
      }
      return paramList;
    }
    throw ((Throwable)new IllegalStateException("Unable to load publicsuffixes.gz resource from the classpath.".toString()));
  }
  
  private final void readTheList()
    throws IOException
  {
    Object localObject1 = (byte[])null;
    localObject1 = PublicSuffixDatabase.class.getResourceAsStream("publicsuffixes.gz");
    if (localObject1 != null)
    {
      localObject1 = (Closeable)Okio.buffer((Source)new GzipSource(Okio.source((InputStream)localObject1)));
      Throwable localThrowable1 = (Throwable)null;
      try
      {
        Object localObject2 = (BufferedSource)localObject1;
        byte[] arrayOfByte1 = ((BufferedSource)localObject2).readByteArray(((BufferedSource)localObject2).readInt());
        byte[] arrayOfByte2 = ((BufferedSource)localObject2).readByteArray(((BufferedSource)localObject2).readInt());
        localObject2 = Unit.INSTANCE;
        CloseableKt.closeFinally((Closeable)localObject1, localThrowable1);
        if (arrayOfByte1 == null) {}
        try
        {
          Intrinsics.throwNpe();
          this.publicSuffixListBytes = arrayOfByte1;
          if (arrayOfByte2 == null) {
            Intrinsics.throwNpe();
          }
          this.publicSuffixExceptionListBytes = arrayOfByte2;
          localObject1 = Unit.INSTANCE;
          this.readCompleteLatch.countDown();
          return;
        }
        finally {}
        return;
      }
      finally {}
    }
  }
  
  /* Error */
  private final void readTheListUninterruptibly()
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_1
    //   2: aload_0
    //   3: invokespecial 273	okhttp3/internal/publicsuffix/PublicSuffixDatabase:readTheList	()V
    //   6: iload_1
    //   7: ifeq +9 -> 16
    //   10: invokestatic 125	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   13: invokevirtual 128	java/lang/Thread:interrupt	()V
    //   16: return
    //   17: astore_2
    //   18: goto +42 -> 60
    //   21: astore_2
    //   22: getstatic 278	okhttp3/internal/platform/Platform:Companion	Lokhttp3/internal/platform/Platform$Companion;
    //   25: invokevirtual 283	okhttp3/internal/platform/Platform$Companion:get	()Lokhttp3/internal/platform/Platform;
    //   28: ldc_w 285
    //   31: iconst_5
    //   32: aload_2
    //   33: checkcast 212	java/lang/Throwable
    //   36: invokevirtual 289	okhttp3/internal/platform/Platform:log	(Ljava/lang/String;ILjava/lang/Throwable;)V
    //   39: iload_1
    //   40: ifeq +9 -> 49
    //   43: invokestatic 125	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   46: invokevirtual 128	java/lang/Thread:interrupt	()V
    //   49: return
    //   50: astore_2
    //   51: invokestatic 292	java/lang/Thread:interrupted	()Z
    //   54: pop
    //   55: iconst_1
    //   56: istore_1
    //   57: goto -55 -> 2
    //   60: iload_1
    //   61: ifeq +9 -> 70
    //   64: invokestatic 125	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   67: invokevirtual 128	java/lang/Thread:interrupt	()V
    //   70: aload_2
    //   71: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	72	0	this	PublicSuffixDatabase
    //   1	60	1	i	int
    //   17	1	2	localObject	Object
    //   21	12	2	localIOException	IOException
    //   50	21	2	localInterruptedIOException	java.io.InterruptedIOException
    // Exception table:
    //   from	to	target	type
    //   2	6	17	finally
    //   22	39	17	finally
    //   51	55	17	finally
    //   2	6	21	java/io/IOException
    //   10	16	21	java/io/IOException
    //   2	6	50	java/io/InterruptedIOException
    //   10	16	50	java/io/InterruptedIOException
  }
  
  public final String getEffectiveTldPlusOne(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "domain");
    Object localObject = IDN.toUnicode(paramString);
    Intrinsics.checkExpressionValueIsNotNull(localObject, "unicodeDomain");
    localObject = StringsKt.split$default((CharSequence)localObject, new char[] { '.' }, false, 0, 6, null);
    List localList = findMatchingRule((List)localObject);
    if ((((List)localObject).size() == localList.size()) && (((String)localList.get(0)).charAt(0) != '!')) {
      return null;
    }
    int i;
    int j;
    if (((String)localList.get(0)).charAt(0) == '!')
    {
      i = ((List)localObject).size();
      j = localList.size();
    }
    else
    {
      i = ((List)localObject).size();
      j = localList.size() + 1;
    }
    return SequencesKt.joinToString$default(SequencesKt.drop(CollectionsKt.asSequence((Iterable)StringsKt.split$default((CharSequence)paramString, new char[] { '.' }, false, 0, 6, null)), i - j), (CharSequence)".", null, null, 0, null, null, 62, null);
  }
  
  public final void setListBytes(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte1, "publicSuffixListBytes");
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte2, "publicSuffixExceptionListBytes");
    this.publicSuffixListBytes = paramArrayOfByte1;
    this.publicSuffixExceptionListBytes = paramArrayOfByte2;
    this.listRead.set(true);
    this.readCompleteLatch.countDown();
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000:\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\f\n\000\n\002\020 \n\002\020\016\n\002\b\002\n\002\020\022\n\000\n\002\030\002\n\002\b\003\n\002\020\021\n\000\n\002\020\b\n\002\b\002\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\006\020\r\032\0020\fJ)\020\016\032\004\030\0010\007*\0020\n2\f\020\017\032\b\022\004\022\0020\n0\0202\006\020\021\032\0020\022H\002?\006\002\020\023R\016\020\003\032\0020\004X?T?\006\002\n\000R\024\020\005\032\b\022\004\022\0020\0070\006X?\004?\006\002\n\000R\016\020\b\032\0020\007X?T?\006\002\n\000R\016\020\t\032\0020\nX?\004?\006\002\n\000R\016\020\013\032\0020\fX?\004?\006\002\n\000?\006\024"}, d2={"Lokhttp3/internal/publicsuffix/PublicSuffixDatabase$Companion;", "", "()V", "EXCEPTION_MARKER", "", "PREVAILING_RULE", "", "", "PUBLIC_SUFFIX_RESOURCE", "WILDCARD_LABEL", "", "instance", "Lokhttp3/internal/publicsuffix/PublicSuffixDatabase;", "get", "binarySearch", "labels", "", "labelIndex", "", "([B[[BI)Ljava/lang/String;", "okhttp"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    private final String binarySearch(byte[] paramArrayOfByte, byte[][] paramArrayOfByte1, int paramInt)
    {
      int i = paramArrayOfByte.length;
      String str1 = (String)null;
      int j = 0;
      String str2 = str1;
      if (j < i)
      {
        for (int k = (j + i) / 2; (k > -1) && (paramArrayOfByte[k] != (byte)10); k--) {}
        int m = k + 1;
        int n;
        for (k = 1;; k++)
        {
          n = m + k;
          if (paramArrayOfByte[n] == (byte)10) {
            break;
          }
        }
        int i1 = n - m;
        int i2 = paramInt;
        int i3 = 0;
        k = 0;
        int i4 = 0;
        label205:
        label329:
        label341:
        for (;;)
        {
          int i5;
          if (i3 != 0)
          {
            i5 = 46;
            i3 = 0;
          }
          else
          {
            i5 = Util.and(paramArrayOfByte1[i2][k], 255);
          }
          i5 -= Util.and(paramArrayOfByte[(m + i4)], 255);
          if (i5 == 0)
          {
            i4++;
            k++;
            if (i4 != i1)
            {
              if (paramArrayOfByte1[i2].length != k) {
                break label341;
              }
              if (i2 != ((Object[])paramArrayOfByte1).length - 1) {
                break label329;
              }
            }
          }
          if (i5 < 0)
          {
            i = m - 1;
            break;
          }
          if (i5 > 0) {}
          do
          {
            j = n + 1;
            break;
            i4 = i1 - i4;
            i3 = paramArrayOfByte1[i2].length - k;
            k = i2 + 1;
            i2 = ((Object[])paramArrayOfByte1).length;
            while (k < i2)
            {
              i3 += paramArrayOfByte1[k].length;
              k++;
            }
            if (i3 < i4) {
              break label205;
            }
          } while (i3 > i4);
          paramArrayOfByte1 = StandardCharsets.UTF_8;
          Intrinsics.checkExpressionValueIsNotNull(paramArrayOfByte1, "UTF_8");
          str2 = new String(paramArrayOfByte, m, i1, paramArrayOfByte1);
          break label344;
          i2++;
          k = -1;
          i3 = 1;
        }
      }
      label344:
      return str2;
    }
    
    public final PublicSuffixDatabase get()
    {
      return PublicSuffixDatabase.access$getInstance$cp();
    }
  }
}

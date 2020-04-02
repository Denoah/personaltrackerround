package okio;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000<\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\016\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\t\n\000\n\002\030\002\n\002\b\003\030\000 \0262\0020\001:\001\026B\027\b\020\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005?\006\002\020\006B\037\b\020\022\006\020\002\032\0020\003\022\006\020\007\032\0020\b\022\006\020\004\032\0020\005?\006\002\020\tJ\r\020\n\032\0020\bH\007?\006\002\b\020J\030\020\021\032\0020\0222\006\020\023\032\0020\0242\006\020\025\032\0020\022H\026R\021\020\n\032\0020\b8G?\006\006\032\004\b\n\020\013R\020\020\f\032\004\030\0010\rX?\004?\006\002\n\000R\020\020\016\032\004\030\0010\017X?\004?\006\002\n\000?\006\027"}, d2={"Lokio/HashingSource;", "Lokio/ForwardingSource;", "source", "Lokio/Source;", "algorithm", "", "(Lokio/Source;Ljava/lang/String;)V", "key", "Lokio/ByteString;", "(Lokio/Source;Lokio/ByteString;Ljava/lang/String;)V", "hash", "()Lokio/ByteString;", "mac", "Ljavax/crypto/Mac;", "messageDigest", "Ljava/security/MessageDigest;", "-deprecated_hash", "read", "", "sink", "Lokio/Buffer;", "byteCount", "Companion", "okio"}, k=1, mv={1, 1, 16})
public final class HashingSource
  extends ForwardingSource
{
  public static final Companion Companion = new Companion(null);
  private final Mac mac;
  private final MessageDigest messageDigest;
  
  public HashingSource(Source paramSource, String paramString)
  {
    super(paramSource);
    this.messageDigest = MessageDigest.getInstance(paramString);
    this.mac = ((Mac)null);
  }
  
  public HashingSource(Source paramSource, ByteString paramByteString, String paramString)
  {
    super(paramSource);
    try
    {
      paramSource = Mac.getInstance(paramString);
      SecretKeySpec localSecretKeySpec = new javax/crypto/spec/SecretKeySpec;
      localSecretKeySpec.<init>(paramByteString.toByteArray(), paramString);
      paramSource.init((Key)localSecretKeySpec);
      this.mac = paramSource;
      this.messageDigest = ((MessageDigest)null);
      return;
    }
    catch (InvalidKeyException paramSource)
    {
      throw ((Throwable)new IllegalArgumentException((Throwable)paramSource));
    }
  }
  
  @JvmStatic
  public static final HashingSource hmacSha1(Source paramSource, ByteString paramByteString)
  {
    return Companion.hmacSha1(paramSource, paramByteString);
  }
  
  @JvmStatic
  public static final HashingSource hmacSha256(Source paramSource, ByteString paramByteString)
  {
    return Companion.hmacSha256(paramSource, paramByteString);
  }
  
  @JvmStatic
  public static final HashingSource hmacSha512(Source paramSource, ByteString paramByteString)
  {
    return Companion.hmacSha512(paramSource, paramByteString);
  }
  
  @JvmStatic
  public static final HashingSource md5(Source paramSource)
  {
    return Companion.md5(paramSource);
  }
  
  @JvmStatic
  public static final HashingSource sha1(Source paramSource)
  {
    return Companion.sha1(paramSource);
  }
  
  @JvmStatic
  public static final HashingSource sha256(Source paramSource)
  {
    return Companion.sha256(paramSource);
  }
  
  @JvmStatic
  public static final HashingSource sha512(Source paramSource)
  {
    return Companion.sha512(paramSource);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="hash", imports={}))
  public final ByteString -deprecated_hash()
  {
    return hash();
  }
  
  public final ByteString hash()
  {
    Object localObject = this.messageDigest;
    if (localObject != null)
    {
      localObject = ((MessageDigest)localObject).digest();
    }
    else
    {
      localObject = this.mac;
      if (localObject == null) {
        Intrinsics.throwNpe();
      }
      localObject = ((Mac)localObject).doFinal();
    }
    Intrinsics.checkExpressionValueIsNotNull(localObject, "result");
    return new ByteString((byte[])localObject);
  }
  
  public long read(Buffer paramBuffer, long paramLong)
    throws IOException
  {
    Intrinsics.checkParameterIsNotNull(paramBuffer, "sink");
    long l1 = super.read(paramBuffer, paramLong);
    if (l1 != -1L)
    {
      long l2 = paramBuffer.size() - l1;
      long l3 = paramBuffer.size();
      Object localObject1 = paramBuffer.head;
      paramLong = l3;
      Object localObject2 = localObject1;
      if (localObject1 == null)
      {
        Intrinsics.throwNpe();
        localObject2 = localObject1;
      }
      long l4;
      for (paramLong = l3;; paramLong -= ((Segment)localObject2).limit - ((Segment)localObject2).pos)
      {
        l3 = l2;
        l4 = paramLong;
        localObject1 = localObject2;
        if (paramLong <= l2) {
          break;
        }
        localObject2 = ((Segment)localObject2).prev;
        if (localObject2 == null) {
          Intrinsics.throwNpe();
        }
      }
      while (l4 < paramBuffer.size())
      {
        int i = (int)(((Segment)localObject1).pos + l3 - l4);
        localObject2 = this.messageDigest;
        if (localObject2 != null)
        {
          ((MessageDigest)localObject2).update(((Segment)localObject1).data, i, ((Segment)localObject1).limit - i);
        }
        else
        {
          localObject2 = this.mac;
          if (localObject2 == null) {
            Intrinsics.throwNpe();
          }
          ((Mac)localObject2).update(((Segment)localObject1).data, i, ((Segment)localObject1).limit - i);
        }
        l4 += ((Segment)localObject1).limit - ((Segment)localObject1).pos;
        localObject1 = ((Segment)localObject1).next;
        if (localObject1 == null) {
          Intrinsics.throwNpe();
        }
        l3 = l4;
      }
    }
    return l1;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\007\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\030\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\bH\007J\030\020\t\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\bH\007J\030\020\n\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\bH\007J\020\020\013\032\0020\0042\006\020\005\032\0020\006H\007J\020\020\f\032\0020\0042\006\020\005\032\0020\006H\007J\020\020\r\032\0020\0042\006\020\005\032\0020\006H\007J\020\020\016\032\0020\0042\006\020\005\032\0020\006H\007?\006\017"}, d2={"Lokio/HashingSource$Companion;", "", "()V", "hmacSha1", "Lokio/HashingSource;", "source", "Lokio/Source;", "key", "Lokio/ByteString;", "hmacSha256", "hmacSha512", "md5", "sha1", "sha256", "sha512", "okio"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    @JvmStatic
    public final HashingSource hmacSha1(Source paramSource, ByteString paramByteString)
    {
      Intrinsics.checkParameterIsNotNull(paramSource, "source");
      Intrinsics.checkParameterIsNotNull(paramByteString, "key");
      return new HashingSource(paramSource, paramByteString, "HmacSHA1");
    }
    
    @JvmStatic
    public final HashingSource hmacSha256(Source paramSource, ByteString paramByteString)
    {
      Intrinsics.checkParameterIsNotNull(paramSource, "source");
      Intrinsics.checkParameterIsNotNull(paramByteString, "key");
      return new HashingSource(paramSource, paramByteString, "HmacSHA256");
    }
    
    @JvmStatic
    public final HashingSource hmacSha512(Source paramSource, ByteString paramByteString)
    {
      Intrinsics.checkParameterIsNotNull(paramSource, "source");
      Intrinsics.checkParameterIsNotNull(paramByteString, "key");
      return new HashingSource(paramSource, paramByteString, "HmacSHA512");
    }
    
    @JvmStatic
    public final HashingSource md5(Source paramSource)
    {
      Intrinsics.checkParameterIsNotNull(paramSource, "source");
      return new HashingSource(paramSource, "MD5");
    }
    
    @JvmStatic
    public final HashingSource sha1(Source paramSource)
    {
      Intrinsics.checkParameterIsNotNull(paramSource, "source");
      return new HashingSource(paramSource, "SHA-1");
    }
    
    @JvmStatic
    public final HashingSource sha256(Source paramSource)
    {
      Intrinsics.checkParameterIsNotNull(paramSource, "source");
      return new HashingSource(paramSource, "SHA-256");
    }
    
    @JvmStatic
    public final HashingSource sha512(Source paramSource)
    {
      Intrinsics.checkParameterIsNotNull(paramSource, "source");
      return new HashingSource(paramSource, "SHA-512");
    }
  }
}

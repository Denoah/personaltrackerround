package okhttp3.internal.tls;

import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0008\n\002\030\002\n\002\030\002\n\000\n\002\020\021\n\002\030\002\n\002\b\002\n\002\020$\n\002\030\002\n\002\020\"\n\000\n\002\020\013\n\000\n\002\020\000\n\002\b\003\n\002\020\b\n\000\030\0002\0020\001B\031\022\022\020\002\032\n\022\006\b\001\022\0020\0040\003\"\0020\004?\006\002\020\005J\023\020\n\032\0020\0132\b\020\f\032\004\030\0010\rH?\002J\022\020\016\032\004\030\0010\0042\006\020\017\032\0020\004H\026J\b\020\020\032\0020\021H\026R \020\006\032\024\022\004\022\0020\b\022\n\022\b\022\004\022\0020\0040\t0\007X?\004?\006\002\n\000?\006\022"}, d2={"Lokhttp3/internal/tls/BasicTrustRootIndex;", "Lokhttp3/internal/tls/TrustRootIndex;", "caCerts", "", "Ljava/security/cert/X509Certificate;", "([Ljava/security/cert/X509Certificate;)V", "subjectToCaCerts", "", "Ljavax/security/auth/x500/X500Principal;", "", "equals", "", "other", "", "findByIssuerAndSignature", "cert", "hashCode", "", "okhttp"}, k=1, mv={1, 1, 16})
public final class BasicTrustRootIndex
  implements TrustRootIndex
{
  private final Map<X500Principal, Set<X509Certificate>> subjectToCaCerts;
  
  public BasicTrustRootIndex(X509Certificate... paramVarArgs)
  {
    Map localMap = (Map)new LinkedHashMap();
    int i = paramVarArgs.length;
    for (int j = 0; j < i; j++)
    {
      X509Certificate localX509Certificate = paramVarArgs[j];
      X500Principal localX500Principal = localX509Certificate.getSubjectX500Principal();
      Intrinsics.checkExpressionValueIsNotNull(localX500Principal, "caCert.subjectX500Principal");
      Object localObject1 = localMap.get(localX500Principal);
      Object localObject2 = localObject1;
      if (localObject1 == null)
      {
        localObject2 = (Set)new LinkedHashSet();
        localMap.put(localX500Principal, localObject2);
      }
      ((Set)localObject2).add(localX509Certificate);
    }
    this.subjectToCaCerts = localMap;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if ((paramObject != (BasicTrustRootIndex)this) && ((!(paramObject instanceof BasicTrustRootIndex)) || (!Intrinsics.areEqual(((BasicTrustRootIndex)paramObject).subjectToCaCerts, this.subjectToCaCerts)))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public X509Certificate findByIssuerAndSignature(X509Certificate paramX509Certificate)
  {
    Intrinsics.checkParameterIsNotNull(paramX509Certificate, "cert");
    Object localObject1 = paramX509Certificate.getIssuerX500Principal();
    Object localObject2 = (Set)this.subjectToCaCerts.get(localObject1);
    localObject1 = null;
    Object localObject3 = null;
    if (localObject2 != null)
    {
      localObject2 = ((Iterable)localObject2).iterator();
      int i;
      do
      {
        localObject1 = localObject3;
        if (!((Iterator)localObject2).hasNext()) {
          break;
        }
        localObject1 = ((Iterator)localObject2).next();
        X509Certificate localX509Certificate = (X509Certificate)localObject1;
        try
        {
          paramX509Certificate.verify(localX509Certificate.getPublicKey());
          i = 1;
        }
        catch (Exception localException)
        {
          i = 0;
        }
      } while (i == 0);
      localObject1 = (X509Certificate)localObject1;
    }
    return localObject1;
  }
  
  public int hashCode()
  {
    return this.subjectToCaCerts.hashCode();
  }
}

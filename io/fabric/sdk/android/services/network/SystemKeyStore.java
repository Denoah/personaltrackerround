package io.fabric.sdk.android.services.network;

import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashMap;
import javax.security.auth.x500.X500Principal;

class SystemKeyStore
{
  private final HashMap<Principal, X509Certificate> trustRoots;
  final KeyStore trustStore;
  
  public SystemKeyStore(InputStream paramInputStream, String paramString)
  {
    paramInputStream = getTrustStore(paramInputStream, paramString);
    this.trustRoots = initializeTrustedRoots(paramInputStream);
    this.trustStore = paramInputStream;
  }
  
  /* Error */
  private KeyStore getTrustStore(InputStream paramInputStream, String paramString)
  {
    // Byte code:
    //   0: ldc 37
    //   2: invokestatic 43	java/security/KeyStore:getInstance	(Ljava/lang/String;)Ljava/security/KeyStore;
    //   5: astore_3
    //   6: new 45	java/io/BufferedInputStream
    //   9: astore 4
    //   11: aload 4
    //   13: aload_1
    //   14: invokespecial 48	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   17: aload_3
    //   18: aload 4
    //   20: aload_2
    //   21: invokevirtual 54	java/lang/String:toCharArray	()[C
    //   24: invokevirtual 58	java/security/KeyStore:load	(Ljava/io/InputStream;[C)V
    //   27: aload 4
    //   29: invokevirtual 61	java/io/BufferedInputStream:close	()V
    //   32: aload_3
    //   33: areturn
    //   34: astore_1
    //   35: aload 4
    //   37: invokevirtual 61	java/io/BufferedInputStream:close	()V
    //   40: aload_1
    //   41: athrow
    //   42: astore_1
    //   43: new 63	java/lang/AssertionError
    //   46: dup
    //   47: aload_1
    //   48: invokespecial 66	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   51: athrow
    //   52: astore_1
    //   53: new 63	java/lang/AssertionError
    //   56: dup
    //   57: aload_1
    //   58: invokespecial 66	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   61: athrow
    //   62: astore_1
    //   63: new 63	java/lang/AssertionError
    //   66: dup
    //   67: aload_1
    //   68: invokespecial 66	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   71: athrow
    //   72: astore_1
    //   73: new 63	java/lang/AssertionError
    //   76: dup
    //   77: aload_1
    //   78: invokespecial 66	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   81: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	82	0	this	SystemKeyStore
    //   0	82	1	paramInputStream	InputStream
    //   0	82	2	paramString	String
    //   5	28	3	localKeyStore	KeyStore
    //   9	27	4	localBufferedInputStream	java.io.BufferedInputStream
    // Exception table:
    //   from	to	target	type
    //   17	27	34	finally
    //   0	17	42	java/io/IOException
    //   27	32	42	java/io/IOException
    //   35	42	42	java/io/IOException
    //   0	17	52	java/security/cert/CertificateException
    //   27	32	52	java/security/cert/CertificateException
    //   35	42	52	java/security/cert/CertificateException
    //   0	17	62	java/security/NoSuchAlgorithmException
    //   27	32	62	java/security/NoSuchAlgorithmException
    //   35	42	62	java/security/NoSuchAlgorithmException
    //   0	17	72	java/security/KeyStoreException
    //   27	32	72	java/security/KeyStoreException
    //   35	42	72	java/security/KeyStoreException
  }
  
  private HashMap<Principal, X509Certificate> initializeTrustedRoots(KeyStore paramKeyStore)
  {
    try
    {
      HashMap localHashMap = new java/util/HashMap;
      localHashMap.<init>();
      Enumeration localEnumeration = paramKeyStore.aliases();
      while (localEnumeration.hasMoreElements())
      {
        X509Certificate localX509Certificate = (X509Certificate)paramKeyStore.getCertificate((String)localEnumeration.nextElement());
        if (localX509Certificate != null) {
          localHashMap.put(localX509Certificate.getSubjectX500Principal(), localX509Certificate);
        }
      }
      return localHashMap;
    }
    catch (KeyStoreException paramKeyStore)
    {
      throw new AssertionError(paramKeyStore);
    }
  }
  
  public X509Certificate getTrustRootFor(X509Certificate paramX509Certificate)
  {
    X509Certificate localX509Certificate = (X509Certificate)this.trustRoots.get(paramX509Certificate.getIssuerX500Principal());
    if (localX509Certificate == null) {
      return null;
    }
    if (localX509Certificate.getSubjectX500Principal().equals(paramX509Certificate.getSubjectX500Principal())) {
      return null;
    }
    try
    {
      paramX509Certificate.verify(localX509Certificate.getPublicKey());
      return localX509Certificate;
    }
    catch (GeneralSecurityException paramX509Certificate) {}
    return null;
  }
  
  public boolean isTrustRoot(X509Certificate paramX509Certificate)
  {
    X509Certificate localX509Certificate = (X509Certificate)this.trustRoots.get(paramX509Certificate.getSubjectX500Principal());
    boolean bool;
    if ((localX509Certificate != null) && (localX509Certificate.getPublicKey().equals(paramX509Certificate.getPublicKey()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}

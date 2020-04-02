package io.fabric.sdk.android.services.network;

import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedList;
import javax.security.auth.x500.X500Principal;

final class CertificateChainCleaner
{
  private CertificateChainCleaner() {}
  
  public static X509Certificate[] getCleanChain(X509Certificate[] paramArrayOfX509Certificate, SystemKeyStore paramSystemKeyStore)
    throws CertificateException
  {
    LinkedList localLinkedList = new LinkedList();
    boolean bool1 = paramSystemKeyStore.isTrustRoot(paramArrayOfX509Certificate[0]);
    localLinkedList.add(paramArrayOfX509Certificate[0]);
    boolean bool2 = true;
    boolean bool3;
    for (int i = 1;; i++)
    {
      bool3 = bool1;
      if (i >= paramArrayOfX509Certificate.length) {
        break;
      }
      if (paramSystemKeyStore.isTrustRoot(paramArrayOfX509Certificate[i])) {
        bool1 = true;
      }
      bool3 = bool1;
      if (!isValidLink(paramArrayOfX509Certificate[i], paramArrayOfX509Certificate[(i - 1)])) {
        break;
      }
      localLinkedList.add(paramArrayOfX509Certificate[i]);
    }
    paramArrayOfX509Certificate = paramSystemKeyStore.getTrustRootFor(paramArrayOfX509Certificate[(i - 1)]);
    if (paramArrayOfX509Certificate != null)
    {
      localLinkedList.add(paramArrayOfX509Certificate);
      bool3 = bool2;
    }
    if (bool3) {
      return (X509Certificate[])localLinkedList.toArray(new X509Certificate[localLinkedList.size()]);
    }
    throw new CertificateException("Didn't find a trust anchor in chain cleanup!");
  }
  
  private static boolean isValidLink(X509Certificate paramX509Certificate1, X509Certificate paramX509Certificate2)
  {
    if (!paramX509Certificate1.getSubjectX500Principal().equals(paramX509Certificate2.getIssuerX500Principal())) {
      return false;
    }
    try
    {
      paramX509Certificate2.verify(paramX509Certificate1.getPublicKey());
      return true;
    }
    catch (GeneralSecurityException paramX509Certificate1) {}
    return false;
  }
}

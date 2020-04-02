package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

class PinningTrustManager
  implements X509TrustManager
{
  private static final X509Certificate[] NO_ISSUERS = new X509Certificate[0];
  private static final long PIN_FRESHNESS_DURATION_MILLIS = 15552000000L;
  private final Set<X509Certificate> cache = Collections.synchronizedSet(new HashSet());
  private final long pinCreationTimeMillis;
  private final List<byte[]> pins = new LinkedList();
  private final SystemKeyStore systemKeyStore;
  private final TrustManager[] systemTrustManagers = initializeSystemTrustManagers(paramSystemKeyStore);
  
  public PinningTrustManager(SystemKeyStore paramSystemKeyStore, PinningInfoProvider paramPinningInfoProvider)
  {
    this.systemKeyStore = paramSystemKeyStore;
    this.pinCreationTimeMillis = paramPinningInfoProvider.getPinCreationTimeInMillis();
    for (paramPinningInfoProvider : paramPinningInfoProvider.getPins()) {
      this.pins.add(hexStringToByteArray(paramPinningInfoProvider));
    }
  }
  
  private void checkPinTrust(X509Certificate[] paramArrayOfX509Certificate)
    throws CertificateException
  {
    if ((this.pinCreationTimeMillis != -1L) && (System.currentTimeMillis() - this.pinCreationTimeMillis > 15552000000L))
    {
      Logger localLogger = Fabric.getLogger();
      paramArrayOfX509Certificate = new StringBuilder();
      paramArrayOfX509Certificate.append("Certificate pins are stale, (");
      paramArrayOfX509Certificate.append(System.currentTimeMillis() - this.pinCreationTimeMillis);
      paramArrayOfX509Certificate.append(" millis vs ");
      paramArrayOfX509Certificate.append(15552000000L);
      paramArrayOfX509Certificate.append(" millis) falling back to system trust.");
      localLogger.w("Fabric", paramArrayOfX509Certificate.toString());
      return;
    }
    paramArrayOfX509Certificate = CertificateChainCleaner.getCleanChain(paramArrayOfX509Certificate, this.systemKeyStore);
    int i = paramArrayOfX509Certificate.length;
    for (int j = 0; j < i; j++) {
      if (isValidPin(paramArrayOfX509Certificate[j])) {
        return;
      }
    }
    throw new CertificateException("No valid pins found in chain!");
  }
  
  private void checkSystemTrust(X509Certificate[] paramArrayOfX509Certificate, String paramString)
    throws CertificateException
  {
    TrustManager[] arrayOfTrustManager = this.systemTrustManagers;
    int i = arrayOfTrustManager.length;
    for (int j = 0; j < i; j++) {
      ((X509TrustManager)arrayOfTrustManager[j]).checkServerTrusted(paramArrayOfX509Certificate, paramString);
    }
  }
  
  private byte[] hexStringToByteArray(String paramString)
  {
    int i = paramString.length();
    byte[] arrayOfByte = new byte[i / 2];
    for (int j = 0; j < i; j += 2) {
      arrayOfByte[(j / 2)] = ((byte)(byte)((Character.digit(paramString.charAt(j), 16) << 4) + Character.digit(paramString.charAt(j + 1), 16)));
    }
    return arrayOfByte;
  }
  
  private TrustManager[] initializeSystemTrustManagers(SystemKeyStore paramSystemKeyStore)
  {
    try
    {
      TrustManagerFactory localTrustManagerFactory = TrustManagerFactory.getInstance("X509");
      localTrustManagerFactory.init(paramSystemKeyStore.trustStore);
      paramSystemKeyStore = localTrustManagerFactory.getTrustManagers();
      return paramSystemKeyStore;
    }
    catch (KeyStoreException paramSystemKeyStore)
    {
      throw new AssertionError(paramSystemKeyStore);
    }
    catch (NoSuchAlgorithmException paramSystemKeyStore)
    {
      throw new AssertionError(paramSystemKeyStore);
    }
  }
  
  private boolean isValidPin(X509Certificate paramX509Certificate)
    throws CertificateException
  {
    try
    {
      byte[] arrayOfByte = MessageDigest.getInstance("SHA1").digest(paramX509Certificate.getPublicKey().getEncoded());
      paramX509Certificate = this.pins.iterator();
      while (paramX509Certificate.hasNext())
      {
        boolean bool = Arrays.equals((byte[])paramX509Certificate.next(), arrayOfByte);
        if (bool) {
          return true;
        }
      }
      return false;
    }
    catch (NoSuchAlgorithmException paramX509Certificate)
    {
      throw new CertificateException(paramX509Certificate);
    }
  }
  
  public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
    throws CertificateException
  {
    throw new CertificateException("Client certificates not supported!");
  }
  
  public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
    throws CertificateException
  {
    if (this.cache.contains(paramArrayOfX509Certificate[0])) {
      return;
    }
    checkSystemTrust(paramArrayOfX509Certificate, paramString);
    checkPinTrust(paramArrayOfX509Certificate);
    this.cache.add(paramArrayOfX509Certificate[0]);
  }
  
  public X509Certificate[] getAcceptedIssuers()
  {
    return NO_ISSUERS;
  }
}

package androidx.core.hardware.fingerprint;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintManager.AuthenticationCallback;
import android.hardware.fingerprint.FingerprintManager.AuthenticationResult;
import android.hardware.fingerprint.FingerprintManager.CryptoObject;
import android.os.Build.VERSION;
import android.os.Handler;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.Mac;

@Deprecated
public class FingerprintManagerCompat
{
  private final Context mContext;
  
  private FingerprintManagerCompat(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public static FingerprintManagerCompat from(Context paramContext)
  {
    return new FingerprintManagerCompat(paramContext);
  }
  
  private static FingerprintManager getFingerprintManagerOrNull(Context paramContext)
  {
    if (Build.VERSION.SDK_INT == 23) {
      return (FingerprintManager)paramContext.getSystemService(FingerprintManager.class);
    }
    if ((Build.VERSION.SDK_INT > 23) && (paramContext.getPackageManager().hasSystemFeature("android.hardware.fingerprint"))) {
      return (FingerprintManager)paramContext.getSystemService(FingerprintManager.class);
    }
    return null;
  }
  
  static CryptoObject unwrapCryptoObject(FingerprintManager.CryptoObject paramCryptoObject)
  {
    CryptoObject localCryptoObject = null;
    if (paramCryptoObject == null) {
      return null;
    }
    if (paramCryptoObject.getCipher() != null) {
      return new CryptoObject(paramCryptoObject.getCipher());
    }
    if (paramCryptoObject.getSignature() != null) {
      return new CryptoObject(paramCryptoObject.getSignature());
    }
    if (paramCryptoObject.getMac() != null) {
      localCryptoObject = new CryptoObject(paramCryptoObject.getMac());
    }
    return localCryptoObject;
  }
  
  private static FingerprintManager.AuthenticationCallback wrapCallback(AuthenticationCallback paramAuthenticationCallback)
  {
    new FingerprintManager.AuthenticationCallback()
    {
      public void onAuthenticationError(int paramAnonymousInt, CharSequence paramAnonymousCharSequence)
      {
        this.val$callback.onAuthenticationError(paramAnonymousInt, paramAnonymousCharSequence);
      }
      
      public void onAuthenticationFailed()
      {
        this.val$callback.onAuthenticationFailed();
      }
      
      public void onAuthenticationHelp(int paramAnonymousInt, CharSequence paramAnonymousCharSequence)
      {
        this.val$callback.onAuthenticationHelp(paramAnonymousInt, paramAnonymousCharSequence);
      }
      
      public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult paramAnonymousAuthenticationResult)
      {
        this.val$callback.onAuthenticationSucceeded(new FingerprintManagerCompat.AuthenticationResult(FingerprintManagerCompat.unwrapCryptoObject(paramAnonymousAuthenticationResult.getCryptoObject())));
      }
    };
  }
  
  private static FingerprintManager.CryptoObject wrapCryptoObject(CryptoObject paramCryptoObject)
  {
    FingerprintManager.CryptoObject localCryptoObject = null;
    if (paramCryptoObject == null) {
      return null;
    }
    if (paramCryptoObject.getCipher() != null) {
      return new FingerprintManager.CryptoObject(paramCryptoObject.getCipher());
    }
    if (paramCryptoObject.getSignature() != null) {
      return new FingerprintManager.CryptoObject(paramCryptoObject.getSignature());
    }
    if (paramCryptoObject.getMac() != null) {
      localCryptoObject = new FingerprintManager.CryptoObject(paramCryptoObject.getMac());
    }
    return localCryptoObject;
  }
  
  public void authenticate(CryptoObject paramCryptoObject, int paramInt, androidx.core.os.CancellationSignal paramCancellationSignal, AuthenticationCallback paramAuthenticationCallback, Handler paramHandler)
  {
    if (Build.VERSION.SDK_INT >= 23)
    {
      FingerprintManager localFingerprintManager = getFingerprintManagerOrNull(this.mContext);
      if (localFingerprintManager != null)
      {
        if (paramCancellationSignal != null) {
          paramCancellationSignal = (android.os.CancellationSignal)paramCancellationSignal.getCancellationSignalObject();
        } else {
          paramCancellationSignal = null;
        }
        localFingerprintManager.authenticate(wrapCryptoObject(paramCryptoObject), paramCancellationSignal, paramInt, wrapCallback(paramAuthenticationCallback), paramHandler);
      }
    }
  }
  
  public boolean hasEnrolledFingerprints()
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (i >= 23)
    {
      FingerprintManager localFingerprintManager = getFingerprintManagerOrNull(this.mContext);
      bool2 = bool1;
      if (localFingerprintManager != null)
      {
        bool2 = bool1;
        if (localFingerprintManager.hasEnrolledFingerprints()) {
          bool2 = true;
        }
      }
    }
    return bool2;
  }
  
  public boolean isHardwareDetected()
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (i >= 23)
    {
      FingerprintManager localFingerprintManager = getFingerprintManagerOrNull(this.mContext);
      bool2 = bool1;
      if (localFingerprintManager != null)
      {
        bool2 = bool1;
        if (localFingerprintManager.isHardwareDetected()) {
          bool2 = true;
        }
      }
    }
    return bool2;
  }
  
  public static abstract class AuthenticationCallback
  {
    public AuthenticationCallback() {}
    
    public void onAuthenticationError(int paramInt, CharSequence paramCharSequence) {}
    
    public void onAuthenticationFailed() {}
    
    public void onAuthenticationHelp(int paramInt, CharSequence paramCharSequence) {}
    
    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult paramAuthenticationResult) {}
  }
  
  public static final class AuthenticationResult
  {
    private final FingerprintManagerCompat.CryptoObject mCryptoObject;
    
    public AuthenticationResult(FingerprintManagerCompat.CryptoObject paramCryptoObject)
    {
      this.mCryptoObject = paramCryptoObject;
    }
    
    public FingerprintManagerCompat.CryptoObject getCryptoObject()
    {
      return this.mCryptoObject;
    }
  }
  
  public static class CryptoObject
  {
    private final Cipher mCipher;
    private final Mac mMac;
    private final Signature mSignature;
    
    public CryptoObject(Signature paramSignature)
    {
      this.mSignature = paramSignature;
      this.mCipher = null;
      this.mMac = null;
    }
    
    public CryptoObject(Cipher paramCipher)
    {
      this.mCipher = paramCipher;
      this.mSignature = null;
      this.mMac = null;
    }
    
    public CryptoObject(Mac paramMac)
    {
      this.mMac = paramMac;
      this.mCipher = null;
      this.mSignature = null;
    }
    
    public Cipher getCipher()
    {
      return this.mCipher;
    }
    
    public Mac getMac()
    {
      return this.mMac;
    }
    
    public Signature getSignature()
    {
      return this.mSignature;
    }
  }
}

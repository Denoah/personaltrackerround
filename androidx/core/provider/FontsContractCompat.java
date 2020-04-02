package androidx.core.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build.VERSION;
import android.os.CancellationSignal;
import android.os.Handler;
import android.provider.BaseColumns;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.content.res.ResourcesCompat.FontCallback;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.graphics.TypefaceCompatUtil;
import androidx.core.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class FontsContractCompat
{
  private static final int BACKGROUND_THREAD_KEEP_ALIVE_DURATION_MS = 10000;
  public static final String PARCEL_FONT_RESULTS = "font_results";
  static final int RESULT_CODE_PROVIDER_NOT_FOUND = -1;
  static final int RESULT_CODE_WRONG_CERTIFICATES = -2;
  private static final SelfDestructiveThread sBackgroundThread;
  private static final Comparator<byte[]> sByteArrayComparator = new Comparator()
  {
    public int compare(byte[] paramAnonymousArrayOfByte1, byte[] paramAnonymousArrayOfByte2)
    {
      int i;
      if (paramAnonymousArrayOfByte1.length != paramAnonymousArrayOfByte2.length)
      {
        i = paramAnonymousArrayOfByte1.length;
        j = paramAnonymousArrayOfByte2.length;
        return i - j;
      }
      for (int j = 0;; j++)
      {
        if (j >= paramAnonymousArrayOfByte1.length) {
          break label60;
        }
        if (paramAnonymousArrayOfByte1[j] != paramAnonymousArrayOfByte2[j])
        {
          i = paramAnonymousArrayOfByte1[j];
          j = paramAnonymousArrayOfByte2[j];
          break;
        }
      }
      label60:
      return 0;
    }
  };
  static final Object sLock;
  static final SimpleArrayMap<String, ArrayList<SelfDestructiveThread.ReplyCallback<TypefaceResult>>> sPendingReplies;
  static final LruCache<String, Typeface> sTypefaceCache = new LruCache(16);
  
  static
  {
    sBackgroundThread = new SelfDestructiveThread("fonts", 10, 10000);
    sLock = new Object();
    sPendingReplies = new SimpleArrayMap();
  }
  
  private FontsContractCompat() {}
  
  public static Typeface buildTypeface(Context paramContext, CancellationSignal paramCancellationSignal, FontInfo[] paramArrayOfFontInfo)
  {
    return TypefaceCompat.createFromFontInfo(paramContext, paramCancellationSignal, paramArrayOfFontInfo, 0);
  }
  
  private static List<byte[]> convertToByteArrayList(Signature[] paramArrayOfSignature)
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < paramArrayOfSignature.length; i++) {
      localArrayList.add(paramArrayOfSignature[i].toByteArray());
    }
    return localArrayList;
  }
  
  private static boolean equalsByteArrayList(List<byte[]> paramList1, List<byte[]> paramList2)
  {
    if (paramList1.size() != paramList2.size()) {
      return false;
    }
    for (int i = 0; i < paramList1.size(); i++) {
      if (!Arrays.equals((byte[])paramList1.get(i), (byte[])paramList2.get(i))) {
        return false;
      }
    }
    return true;
  }
  
  public static FontFamilyResult fetchFonts(Context paramContext, CancellationSignal paramCancellationSignal, FontRequest paramFontRequest)
    throws PackageManager.NameNotFoundException
  {
    ProviderInfo localProviderInfo = getProvider(paramContext.getPackageManager(), paramFontRequest, paramContext.getResources());
    if (localProviderInfo == null) {
      return new FontFamilyResult(1, null);
    }
    return new FontFamilyResult(0, getFontFromProvider(paramContext, paramFontRequest, localProviderInfo.authority, paramCancellationSignal));
  }
  
  private static List<List<byte[]>> getCertificates(FontRequest paramFontRequest, Resources paramResources)
  {
    if (paramFontRequest.getCertificates() != null) {
      return paramFontRequest.getCertificates();
    }
    return FontResourcesParserCompat.readCerts(paramResources, paramFontRequest.getCertificatesArrayResId());
  }
  
  static FontInfo[] getFontFromProvider(Context paramContext, FontRequest paramFontRequest, String paramString, CancellationSignal paramCancellationSignal)
  {
    Object localObject1 = new ArrayList();
    Uri localUri1 = new Uri.Builder().scheme("content").authority(paramString).build();
    Uri localUri2 = new Uri.Builder().scheme("content").authority(paramString).appendPath("file").build();
    Object localObject2 = null;
    paramString = localObject2;
    try
    {
      if (Build.VERSION.SDK_INT > 16)
      {
        paramString = localObject2;
        paramContext = paramContext.getContentResolver();
        paramString = localObject2;
        paramFontRequest = paramFontRequest.getQuery();
        paramString = localObject2;
        paramContext = paramContext.query(localUri1, new String[] { "_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code" }, "query = ?", new String[] { paramFontRequest }, null, paramCancellationSignal);
      }
      else
      {
        paramString = localObject2;
        paramContext = paramContext.getContentResolver();
        paramString = localObject2;
        paramFontRequest = paramFontRequest.getQuery();
        paramString = localObject2;
        paramContext = paramContext.query(localUri1, new String[] { "_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code" }, "query = ?", new String[] { paramFontRequest }, null);
      }
      paramFontRequest = (FontRequest)localObject1;
      if (paramContext != null)
      {
        paramFontRequest = (FontRequest)localObject1;
        paramString = paramContext;
        if (paramContext.getCount() > 0)
        {
          paramString = paramContext;
          int i = paramContext.getColumnIndex("result_code");
          paramString = paramContext;
          paramCancellationSignal = new java/util/ArrayList;
          paramString = paramContext;
          paramCancellationSignal.<init>();
          paramString = paramContext;
          int j = paramContext.getColumnIndex("_id");
          paramString = paramContext;
          int k = paramContext.getColumnIndex("file_id");
          paramString = paramContext;
          int m = paramContext.getColumnIndex("font_ttc_index");
          paramString = paramContext;
          int n = paramContext.getColumnIndex("font_weight");
          paramString = paramContext;
          int i1 = paramContext.getColumnIndex("font_italic");
          for (;;)
          {
            paramString = paramContext;
            if (!paramContext.moveToNext()) {
              break;
            }
            int i2;
            if (i != -1)
            {
              paramString = paramContext;
              i2 = paramContext.getInt(i);
            }
            else
            {
              i2 = 0;
            }
            int i3;
            if (m != -1)
            {
              paramString = paramContext;
              i3 = paramContext.getInt(m);
            }
            else
            {
              i3 = 0;
            }
            if (k == -1)
            {
              paramString = paramContext;
              paramFontRequest = ContentUris.withAppendedId(localUri1, paramContext.getLong(j));
            }
            else
            {
              paramString = paramContext;
              paramFontRequest = ContentUris.withAppendedId(localUri2, paramContext.getLong(k));
            }
            int i4;
            if (n != -1)
            {
              paramString = paramContext;
              i4 = paramContext.getInt(n);
            }
            else
            {
              i4 = 400;
            }
            if (i1 != -1)
            {
              paramString = paramContext;
              if (paramContext.getInt(i1) == 1)
              {
                bool = true;
                break label490;
              }
            }
            boolean bool = false;
            label490:
            paramString = paramContext;
            localObject1 = new androidx/core/provider/FontsContractCompat$FontInfo;
            paramString = paramContext;
            ((FontInfo)localObject1).<init>(paramFontRequest, i3, i4, bool, i2);
            paramString = paramContext;
            paramCancellationSignal.add(localObject1);
          }
          paramFontRequest = paramCancellationSignal;
        }
      }
      return (FontInfo[])paramFontRequest.toArray(new FontInfo[0]);
    }
    finally
    {
      if (paramString != null) {
        paramString.close();
      }
    }
  }
  
  static TypefaceResult getFontInternal(Context paramContext, FontRequest paramFontRequest, int paramInt)
  {
    try
    {
      paramFontRequest = fetchFonts(paramContext, null, paramFontRequest);
      int i = paramFontRequest.getStatusCode();
      int j = -3;
      if (i == 0)
      {
        paramContext = TypefaceCompat.createFromFontInfo(paramContext, null, paramFontRequest.getFonts(), paramInt);
        if (paramContext != null) {
          j = 0;
        }
        return new TypefaceResult(paramContext, j);
      }
      if (paramFontRequest.getStatusCode() == 1) {
        j = -2;
      }
      return new TypefaceResult(null, j);
    }
    catch (PackageManager.NameNotFoundException paramContext) {}
    return new TypefaceResult(null, -1);
  }
  
  public static Typeface getFontSync(Context paramContext, final FontRequest paramFontRequest, ResourcesCompat.FontCallback arg2, final Handler paramHandler, boolean paramBoolean, int paramInt1, final int paramInt2)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(paramFontRequest.getIdentifier());
    ((StringBuilder)localObject).append("-");
    ((StringBuilder)localObject).append(paramInt2);
    localObject = ((StringBuilder)localObject).toString();
    Typeface localTypeface = (Typeface)sTypefaceCache.get(localObject);
    if (localTypeface != null)
    {
      if (??? != null) {
        ???.onFontRetrieved(localTypeface);
      }
      return localTypeface;
    }
    if ((paramBoolean) && (paramInt1 == -1))
    {
      paramContext = getFontInternal(paramContext, paramFontRequest, paramInt2);
      if (??? != null) {
        if (paramContext.mResult == 0) {
          ???.callbackSuccessAsync(paramContext.mTypeface, paramHandler);
        } else {
          ???.callbackFailAsync(paramContext.mResult, paramHandler);
        }
      }
      return paramContext.mTypeface;
    }
    paramFontRequest = new Callable()
    {
      public FontsContractCompat.TypefaceResult call()
        throws Exception
      {
        FontsContractCompat.TypefaceResult localTypefaceResult = FontsContractCompat.getFontInternal(this.val$context, paramFontRequest, paramInt2);
        if (localTypefaceResult.mTypeface != null) {
          FontsContractCompat.sTypefaceCache.put(this.val$id, localTypefaceResult.mTypeface);
        }
        return localTypefaceResult;
      }
    };
    paramContext = null;
    if (paramBoolean) {}
    try
    {
      paramFontRequest = ((TypefaceResult)sBackgroundThread.postAndWait(paramFontRequest, paramInt1)).mTypeface;
      paramContext = paramFontRequest;
    }
    catch (InterruptedException paramFontRequest)
    {
      for (;;) {}
    }
    return paramContext;
    if (??? == null) {
      paramContext = null;
    } else {
      paramContext = new SelfDestructiveThread.ReplyCallback()
      {
        public void onReply(FontsContractCompat.TypefaceResult paramAnonymousTypefaceResult)
        {
          if (paramAnonymousTypefaceResult == null) {
            this.val$fontCallback.callbackFailAsync(1, paramHandler);
          } else if (paramAnonymousTypefaceResult.mResult == 0) {
            this.val$fontCallback.callbackSuccessAsync(paramAnonymousTypefaceResult.mTypeface, paramHandler);
          } else {
            this.val$fontCallback.callbackFailAsync(paramAnonymousTypefaceResult.mResult, paramHandler);
          }
        }
      };
    }
    synchronized (sLock)
    {
      paramHandler = (ArrayList)sPendingReplies.get(localObject);
      if (paramHandler != null)
      {
        if (paramContext != null) {
          paramHandler.add(paramContext);
        }
        return null;
      }
      if (paramContext != null)
      {
        paramHandler = new java/util/ArrayList;
        paramHandler.<init>();
        paramHandler.add(paramContext);
        sPendingReplies.put(localObject, paramHandler);
      }
      sBackgroundThread.postAndReply(paramFontRequest, new SelfDestructiveThread.ReplyCallback()
      {
        public void onReply(FontsContractCompat.TypefaceResult paramAnonymousTypefaceResult)
        {
          synchronized (FontsContractCompat.sLock)
          {
            ArrayList localArrayList = (ArrayList)FontsContractCompat.sPendingReplies.get(this.val$id);
            if (localArrayList == null) {
              return;
            }
            FontsContractCompat.sPendingReplies.remove(this.val$id);
            for (int i = 0; i < localArrayList.size(); i++) {
              ((SelfDestructiveThread.ReplyCallback)localArrayList.get(i)).onReply(paramAnonymousTypefaceResult);
            }
            return;
          }
        }
      });
      return null;
    }
  }
  
  public static ProviderInfo getProvider(PackageManager paramPackageManager, FontRequest paramFontRequest, Resources paramResources)
    throws PackageManager.NameNotFoundException
  {
    String str = paramFontRequest.getProviderAuthority();
    int i = 0;
    ProviderInfo localProviderInfo = paramPackageManager.resolveContentProvider(str, 0);
    if (localProviderInfo != null)
    {
      if (localProviderInfo.packageName.equals(paramFontRequest.getProviderPackage()))
      {
        paramPackageManager = convertToByteArrayList(paramPackageManager.getPackageInfo(localProviderInfo.packageName, 64).signatures);
        Collections.sort(paramPackageManager, sByteArrayComparator);
        paramFontRequest = getCertificates(paramFontRequest, paramResources);
        while (i < paramFontRequest.size())
        {
          paramResources = new ArrayList((Collection)paramFontRequest.get(i));
          Collections.sort(paramResources, sByteArrayComparator);
          if (equalsByteArrayList(paramPackageManager, paramResources)) {
            return localProviderInfo;
          }
          i++;
        }
        return null;
      }
      paramPackageManager = new StringBuilder();
      paramPackageManager.append("Found content provider ");
      paramPackageManager.append(str);
      paramPackageManager.append(", but package was not ");
      paramPackageManager.append(paramFontRequest.getProviderPackage());
      throw new PackageManager.NameNotFoundException(paramPackageManager.toString());
    }
    paramPackageManager = new StringBuilder();
    paramPackageManager.append("No package found for authority: ");
    paramPackageManager.append(str);
    throw new PackageManager.NameNotFoundException(paramPackageManager.toString());
  }
  
  public static Map<Uri, ByteBuffer> prepareFontData(Context paramContext, FontInfo[] paramArrayOfFontInfo, CancellationSignal paramCancellationSignal)
  {
    HashMap localHashMap = new HashMap();
    int i = paramArrayOfFontInfo.length;
    for (int j = 0; j < i; j++)
    {
      Object localObject = paramArrayOfFontInfo[j];
      if (((FontInfo)localObject).getResultCode() == 0)
      {
        localObject = ((FontInfo)localObject).getUri();
        if (!localHashMap.containsKey(localObject)) {
          localHashMap.put(localObject, TypefaceCompatUtil.mmap(paramContext, paramCancellationSignal, (Uri)localObject));
        }
      }
    }
    return Collections.unmodifiableMap(localHashMap);
  }
  
  public static void requestFont(Context paramContext, FontRequest paramFontRequest, FontRequestCallback paramFontRequestCallback, Handler paramHandler)
  {
    requestFontInternal(paramContext.getApplicationContext(), paramFontRequest, paramFontRequestCallback, paramHandler);
  }
  
  private static void requestFontInternal(Context paramContext, final FontRequest paramFontRequest, final FontRequestCallback paramFontRequestCallback, Handler paramHandler)
  {
    paramHandler.post(new Runnable()
    {
      public void run()
      {
        try
        {
          Object localObject = FontsContractCompat.fetchFonts(this.val$appContext, null, paramFontRequest);
          final int i;
          if (((FontsContractCompat.FontFamilyResult)localObject).getStatusCode() != 0)
          {
            i = ((FontsContractCompat.FontFamilyResult)localObject).getStatusCode();
            if (i != 1)
            {
              if (i != 2)
              {
                this.val$callerThreadHandler.post(new Runnable()
                {
                  public void run()
                  {
                    FontsContractCompat.4.this.val$callback.onTypefaceRequestFailed(-3);
                  }
                });
                return;
              }
              this.val$callerThreadHandler.post(new Runnable()
              {
                public void run()
                {
                  FontsContractCompat.4.this.val$callback.onTypefaceRequestFailed(-3);
                }
              });
              return;
            }
            this.val$callerThreadHandler.post(new Runnable()
            {
              public void run()
              {
                FontsContractCompat.4.this.val$callback.onTypefaceRequestFailed(-2);
              }
            });
            return;
          }
          FontsContractCompat.FontInfo[] arrayOfFontInfo = ((FontsContractCompat.FontFamilyResult)localObject).getFonts();
          if ((arrayOfFontInfo != null) && (arrayOfFontInfo.length != 0))
          {
            int j = arrayOfFontInfo.length;
            for (i = 0; i < j; i++)
            {
              localObject = arrayOfFontInfo[i];
              if (((FontsContractCompat.FontInfo)localObject).getResultCode() != 0)
              {
                i = ((FontsContractCompat.FontInfo)localObject).getResultCode();
                if (i < 0) {
                  this.val$callerThreadHandler.post(new Runnable()
                  {
                    public void run()
                    {
                      FontsContractCompat.4.this.val$callback.onTypefaceRequestFailed(-3);
                    }
                  });
                } else {
                  this.val$callerThreadHandler.post(new Runnable()
                  {
                    public void run()
                    {
                      FontsContractCompat.4.this.val$callback.onTypefaceRequestFailed(i);
                    }
                  });
                }
                return;
              }
            }
            localObject = FontsContractCompat.buildTypeface(this.val$appContext, null, arrayOfFontInfo);
            if (localObject == null)
            {
              this.val$callerThreadHandler.post(new Runnable()
              {
                public void run()
                {
                  FontsContractCompat.4.this.val$callback.onTypefaceRequestFailed(-3);
                }
              });
              return;
            }
            this.val$callerThreadHandler.post(new Runnable()
            {
              public void run()
              {
                FontsContractCompat.4.this.val$callback.onTypefaceRetrieved(this.val$typeface);
              }
            });
            return;
          }
          this.val$callerThreadHandler.post(new Runnable()
          {
            public void run()
            {
              FontsContractCompat.4.this.val$callback.onTypefaceRequestFailed(1);
            }
          });
          return;
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException)
        {
          this.val$callerThreadHandler.post(new Runnable()
          {
            public void run()
            {
              FontsContractCompat.4.this.val$callback.onTypefaceRequestFailed(-1);
            }
          });
        }
      }
    });
  }
  
  public static void resetCache()
  {
    sTypefaceCache.evictAll();
  }
  
  public static final class Columns
    implements BaseColumns
  {
    public static final String FILE_ID = "file_id";
    public static final String ITALIC = "font_italic";
    public static final String RESULT_CODE = "result_code";
    public static final int RESULT_CODE_FONT_NOT_FOUND = 1;
    public static final int RESULT_CODE_FONT_UNAVAILABLE = 2;
    public static final int RESULT_CODE_MALFORMED_QUERY = 3;
    public static final int RESULT_CODE_OK = 0;
    public static final String TTC_INDEX = "font_ttc_index";
    public static final String VARIATION_SETTINGS = "font_variation_settings";
    public static final String WEIGHT = "font_weight";
    
    public Columns() {}
  }
  
  public static class FontFamilyResult
  {
    public static final int STATUS_OK = 0;
    public static final int STATUS_UNEXPECTED_DATA_PROVIDED = 2;
    public static final int STATUS_WRONG_CERTIFICATES = 1;
    private final FontsContractCompat.FontInfo[] mFonts;
    private final int mStatusCode;
    
    public FontFamilyResult(int paramInt, FontsContractCompat.FontInfo[] paramArrayOfFontInfo)
    {
      this.mStatusCode = paramInt;
      this.mFonts = paramArrayOfFontInfo;
    }
    
    public FontsContractCompat.FontInfo[] getFonts()
    {
      return this.mFonts;
    }
    
    public int getStatusCode()
    {
      return this.mStatusCode;
    }
  }
  
  public static class FontInfo
  {
    private final boolean mItalic;
    private final int mResultCode;
    private final int mTtcIndex;
    private final Uri mUri;
    private final int mWeight;
    
    public FontInfo(Uri paramUri, int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3)
    {
      this.mUri = ((Uri)Preconditions.checkNotNull(paramUri));
      this.mTtcIndex = paramInt1;
      this.mWeight = paramInt2;
      this.mItalic = paramBoolean;
      this.mResultCode = paramInt3;
    }
    
    public int getResultCode()
    {
      return this.mResultCode;
    }
    
    public int getTtcIndex()
    {
      return this.mTtcIndex;
    }
    
    public Uri getUri()
    {
      return this.mUri;
    }
    
    public int getWeight()
    {
      return this.mWeight;
    }
    
    public boolean isItalic()
    {
      return this.mItalic;
    }
  }
  
  public static class FontRequestCallback
  {
    public static final int FAIL_REASON_FONT_LOAD_ERROR = -3;
    public static final int FAIL_REASON_FONT_NOT_FOUND = 1;
    public static final int FAIL_REASON_FONT_UNAVAILABLE = 2;
    public static final int FAIL_REASON_MALFORMED_QUERY = 3;
    public static final int FAIL_REASON_PROVIDER_NOT_FOUND = -1;
    public static final int FAIL_REASON_SECURITY_VIOLATION = -4;
    public static final int FAIL_REASON_WRONG_CERTIFICATES = -2;
    public static final int RESULT_OK = 0;
    
    public FontRequestCallback() {}
    
    public void onTypefaceRequestFailed(int paramInt) {}
    
    public void onTypefaceRetrieved(Typeface paramTypeface) {}
    
    @Retention(RetentionPolicy.SOURCE)
    public static @interface FontRequestFailReason {}
  }
  
  private static final class TypefaceResult
  {
    final int mResult;
    final Typeface mTypeface;
    
    TypefaceResult(Typeface paramTypeface, int paramInt)
    {
      this.mTypeface = paramTypeface;
      this.mResult = paramInt;
    }
  }
}

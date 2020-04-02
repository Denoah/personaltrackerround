package androidx.core.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

public class FileProvider
  extends ContentProvider
{
  private static final String ATTR_NAME = "name";
  private static final String ATTR_PATH = "path";
  private static final String[] COLUMNS = { "_display_name", "_size" };
  private static final File DEVICE_ROOT = new File("/");
  private static final String META_DATA_FILE_PROVIDER_PATHS = "android.support.FILE_PROVIDER_PATHS";
  private static final String TAG_CACHE_PATH = "cache-path";
  private static final String TAG_EXTERNAL = "external-path";
  private static final String TAG_EXTERNAL_CACHE = "external-cache-path";
  private static final String TAG_EXTERNAL_FILES = "external-files-path";
  private static final String TAG_EXTERNAL_MEDIA = "external-media-path";
  private static final String TAG_FILES_PATH = "files-path";
  private static final String TAG_ROOT_PATH = "root-path";
  private static HashMap<String, PathStrategy> sCache = new HashMap();
  private PathStrategy mStrategy;
  
  public FileProvider() {}
  
  private static File buildPath(File paramFile, String... paramVarArgs)
  {
    int i = paramVarArgs.length;
    int j = 0;
    for (File localFile = paramFile; j < i; localFile = paramFile)
    {
      String str = paramVarArgs[j];
      paramFile = localFile;
      if (str != null) {
        paramFile = new File(localFile, str);
      }
      j++;
    }
    return localFile;
  }
  
  private static Object[] copyOf(Object[] paramArrayOfObject, int paramInt)
  {
    Object[] arrayOfObject = new Object[paramInt];
    System.arraycopy(paramArrayOfObject, 0, arrayOfObject, 0, paramInt);
    return arrayOfObject;
  }
  
  private static String[] copyOf(String[] paramArrayOfString, int paramInt)
  {
    String[] arrayOfString = new String[paramInt];
    System.arraycopy(paramArrayOfString, 0, arrayOfString, 0, paramInt);
    return arrayOfString;
  }
  
  private static PathStrategy getPathStrategy(Context paramContext, String paramString)
  {
    synchronized (sCache)
    {
      PathStrategy localPathStrategy1 = (PathStrategy)sCache.get(paramString);
      PathStrategy localPathStrategy2 = localPathStrategy1;
      if (localPathStrategy1 == null) {
        try
        {
          localPathStrategy2 = parsePathStrategy(paramContext, paramString);
          sCache.put(paramString, localPathStrategy2);
        }
        catch (XmlPullParserException paramContext)
        {
          paramString = new java/lang/IllegalArgumentException;
          paramString.<init>("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", paramContext);
          throw paramString;
        }
        catch (IOException paramString)
        {
          paramContext = new java/lang/IllegalArgumentException;
          paramContext.<init>("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", paramString);
          throw paramContext;
        }
      }
      return localPathStrategy2;
    }
  }
  
  public static Uri getUriForFile(Context paramContext, String paramString, File paramFile)
  {
    return getPathStrategy(paramContext, paramString).getUriForFile(paramFile);
  }
  
  private static int modeToMode(String paramString)
  {
    int i;
    if ("r".equals(paramString)) {
      i = 268435456;
    } else if ((!"w".equals(paramString)) && (!"wt".equals(paramString)))
    {
      if ("wa".equals(paramString))
      {
        i = 704643072;
      }
      else if ("rw".equals(paramString))
      {
        i = 939524096;
      }
      else if ("rwt".equals(paramString))
      {
        i = 1006632960;
      }
      else
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Invalid mode: ");
        localStringBuilder.append(paramString);
        throw new IllegalArgumentException(localStringBuilder.toString());
      }
    }
    else {
      i = 738197504;
    }
    return i;
  }
  
  private static PathStrategy parsePathStrategy(Context paramContext, String paramString)
    throws IOException, XmlPullParserException
  {
    SimplePathStrategy localSimplePathStrategy = new SimplePathStrategy(paramString);
    ProviderInfo localProviderInfo = paramContext.getPackageManager().resolveContentProvider(paramString, 128);
    if (localProviderInfo != null)
    {
      XmlResourceParser localXmlResourceParser = localProviderInfo.loadXmlMetaData(paramContext.getPackageManager(), "android.support.FILE_PROVIDER_PATHS");
      if (localXmlResourceParser != null)
      {
        for (;;)
        {
          int i = localXmlResourceParser.next();
          if (i == 1) {
            break;
          }
          if (i == 2)
          {
            Object localObject = localXmlResourceParser.getName();
            localProviderInfo = null;
            String str1 = localXmlResourceParser.getAttributeValue(null, "name");
            String str2 = localXmlResourceParser.getAttributeValue(null, "path");
            if ("root-path".equals(localObject))
            {
              paramString = DEVICE_ROOT;
            }
            else if ("files-path".equals(localObject))
            {
              paramString = paramContext.getFilesDir();
            }
            else if ("cache-path".equals(localObject))
            {
              paramString = paramContext.getCacheDir();
            }
            else if ("external-path".equals(localObject))
            {
              paramString = Environment.getExternalStorageDirectory();
            }
            else if ("external-files-path".equals(localObject))
            {
              localObject = ContextCompat.getExternalFilesDirs(paramContext, null);
              paramString = localProviderInfo;
              if (localObject.length > 0) {
                paramString = localObject[0];
              }
            }
            else if ("external-cache-path".equals(localObject))
            {
              localObject = ContextCompat.getExternalCacheDirs(paramContext);
              paramString = localProviderInfo;
              if (localObject.length > 0) {
                paramString = localObject[0];
              }
            }
            else
            {
              paramString = localProviderInfo;
              if (Build.VERSION.SDK_INT >= 21)
              {
                paramString = localProviderInfo;
                if ("external-media-path".equals(localObject))
                {
                  localObject = paramContext.getExternalMediaDirs();
                  paramString = localProviderInfo;
                  if (localObject.length > 0) {
                    paramString = localObject[0];
                  }
                }
              }
            }
            if (paramString != null) {
              localSimplePathStrategy.addRoot(str1, buildPath(paramString, new String[] { str2 }));
            }
          }
        }
        return localSimplePathStrategy;
      }
      throw new IllegalArgumentException("Missing android.support.FILE_PROVIDER_PATHS meta-data");
    }
    paramContext = new StringBuilder();
    paramContext.append("Couldn't find meta-data for provider with authority ");
    paramContext.append(paramString);
    throw new IllegalArgumentException(paramContext.toString());
  }
  
  public void attachInfo(Context paramContext, ProviderInfo paramProviderInfo)
  {
    super.attachInfo(paramContext, paramProviderInfo);
    if (!paramProviderInfo.exported)
    {
      if (paramProviderInfo.grantUriPermissions)
      {
        this.mStrategy = getPathStrategy(paramContext, paramProviderInfo.authority);
        return;
      }
      throw new SecurityException("Provider must grant uri permissions");
    }
    throw new SecurityException("Provider must not be exported");
  }
  
  public int delete(Uri paramUri, String paramString, String[] paramArrayOfString)
  {
    return this.mStrategy.getFileForUri(paramUri).delete();
  }
  
  public String getType(Uri paramUri)
  {
    paramUri = this.mStrategy.getFileForUri(paramUri);
    int i = paramUri.getName().lastIndexOf('.');
    if (i >= 0)
    {
      paramUri = paramUri.getName().substring(i + 1);
      paramUri = MimeTypeMap.getSingleton().getMimeTypeFromExtension(paramUri);
      if (paramUri != null) {
        return paramUri;
      }
    }
    return "application/octet-stream";
  }
  
  public Uri insert(Uri paramUri, ContentValues paramContentValues)
  {
    throw new UnsupportedOperationException("No external inserts");
  }
  
  public boolean onCreate()
  {
    return true;
  }
  
  public ParcelFileDescriptor openFile(Uri paramUri, String paramString)
    throws FileNotFoundException
  {
    return ParcelFileDescriptor.open(this.mStrategy.getFileForUri(paramUri), modeToMode(paramString));
  }
  
  public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2)
  {
    paramString1 = this.mStrategy.getFileForUri(paramUri);
    paramUri = paramArrayOfString1;
    if (paramArrayOfString1 == null) {
      paramUri = COLUMNS;
    }
    paramArrayOfString2 = new String[paramUri.length];
    paramArrayOfString1 = new Object[paramUri.length];
    int i = paramUri.length;
    int j = 0;
    int m;
    for (int k = 0; j < i; k = m)
    {
      paramString2 = paramUri[j];
      if ("_display_name".equals(paramString2))
      {
        paramArrayOfString2[k] = "_display_name";
        m = k + 1;
        paramArrayOfString1[k] = paramString1.getName();
      }
      for (k = m;; k = m)
      {
        m = k;
        break;
        m = k;
        if (!"_size".equals(paramString2)) {
          break;
        }
        paramArrayOfString2[k] = "_size";
        m = k + 1;
        paramArrayOfString1[k] = Long.valueOf(paramString1.length());
      }
      j++;
    }
    paramString1 = copyOf(paramArrayOfString2, k);
    paramUri = copyOf(paramArrayOfString1, k);
    paramArrayOfString1 = new MatrixCursor(paramString1, 1);
    paramArrayOfString1.addRow(paramUri);
    return paramArrayOfString1;
  }
  
  public int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString)
  {
    throw new UnsupportedOperationException("No external updates");
  }
  
  static abstract interface PathStrategy
  {
    public abstract File getFileForUri(Uri paramUri);
    
    public abstract Uri getUriForFile(File paramFile);
  }
  
  static class SimplePathStrategy
    implements FileProvider.PathStrategy
  {
    private final String mAuthority;
    private final HashMap<String, File> mRoots = new HashMap();
    
    SimplePathStrategy(String paramString)
    {
      this.mAuthority = paramString;
    }
    
    void addRoot(String paramString, File paramFile)
    {
      if (!TextUtils.isEmpty(paramString)) {
        try
        {
          File localFile = paramFile.getCanonicalFile();
          this.mRoots.put(paramString, localFile);
          return;
        }
        catch (IOException localIOException)
        {
          paramString = new StringBuilder();
          paramString.append("Failed to resolve canonical path for ");
          paramString.append(paramFile);
          throw new IllegalArgumentException(paramString.toString(), localIOException);
        }
      }
      throw new IllegalArgumentException("Name must not be empty");
    }
    
    public File getFileForUri(Uri paramUri)
    {
      Object localObject1 = paramUri.getEncodedPath();
      int i = ((String)localObject1).indexOf('/', 1);
      Object localObject2 = Uri.decode(((String)localObject1).substring(1, i));
      localObject1 = Uri.decode(((String)localObject1).substring(i + 1));
      localObject2 = (File)this.mRoots.get(localObject2);
      if (localObject2 != null)
      {
        paramUri = new File((File)localObject2, (String)localObject1);
        try
        {
          localObject1 = paramUri.getCanonicalFile();
          if (((File)localObject1).getPath().startsWith(((File)localObject2).getPath())) {
            return localObject1;
          }
          throw new SecurityException("Resolved path jumped beyond configured root");
        }
        catch (IOException localIOException)
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Failed to resolve canonical path for ");
          localStringBuilder.append(paramUri);
          throw new IllegalArgumentException(localStringBuilder.toString());
        }
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unable to find configured root for ");
      localStringBuilder.append(paramUri);
      throw new IllegalArgumentException(localStringBuilder.toString());
    }
    
    public Uri getUriForFile(File paramFile)
    {
      try
      {
        Object localObject1 = paramFile.getCanonicalPath();
        paramFile = null;
        Iterator localIterator = this.mRoots.entrySet().iterator();
        Object localObject2;
        while (localIterator.hasNext())
        {
          localObject2 = (Map.Entry)localIterator.next();
          String str = ((File)((Map.Entry)localObject2).getValue()).getPath();
          if ((((String)localObject1).startsWith(str)) && ((paramFile == null) || (str.length() > ((File)paramFile.getValue()).getPath().length()))) {
            paramFile = (File)localObject2;
          }
        }
        if (paramFile != null)
        {
          localObject2 = ((File)paramFile.getValue()).getPath();
          if (((String)localObject2).endsWith("/")) {
            localObject2 = ((String)localObject1).substring(((String)localObject2).length());
          } else {
            localObject2 = ((String)localObject1).substring(((String)localObject2).length() + 1);
          }
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append(Uri.encode((String)paramFile.getKey()));
          ((StringBuilder)localObject1).append('/');
          ((StringBuilder)localObject1).append(Uri.encode((String)localObject2, "/"));
          paramFile = ((StringBuilder)localObject1).toString();
          return new Uri.Builder().scheme("content").authority(this.mAuthority).encodedPath(paramFile).build();
        }
        paramFile = new StringBuilder();
        paramFile.append("Failed to find configured root that contains ");
        paramFile.append((String)localObject1);
        throw new IllegalArgumentException(paramFile.toString());
      }
      catch (IOException localIOException)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Failed to resolve canonical path for ");
        localStringBuilder.append(paramFile);
        throw new IllegalArgumentException(localStringBuilder.toString());
      }
    }
  }
}

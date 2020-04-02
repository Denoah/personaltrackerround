package androidx.core.net;

import android.net.Uri;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\000\032\n\020\000\032\0020\001*\0020\002\032\r\020\003\032\0020\002*\0020\001H?\b\032\r\020\003\032\0020\002*\0020\004H?\b?\006\005"}, d2={"toFile", "Ljava/io/File;", "Landroid/net/Uri;", "toUri", "", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class UriKt
{
  public static final File toFile(Uri paramUri)
  {
    Intrinsics.checkParameterIsNotNull(paramUri, "$this$toFile");
    if (Intrinsics.areEqual(paramUri.getScheme(), "file"))
    {
      localObject = paramUri.getPath();
      if (localObject != null) {
        return new File((String)localObject);
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Uri path is null: ");
      ((StringBuilder)localObject).append(paramUri);
      throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject).toString().toString()));
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Uri lacks 'file' scheme: ");
    ((StringBuilder)localObject).append(paramUri);
    throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject).toString().toString()));
  }
  
  public static final Uri toUri(File paramFile)
  {
    Intrinsics.checkParameterIsNotNull(paramFile, "$this$toUri");
    paramFile = Uri.fromFile(paramFile);
    Intrinsics.checkExpressionValueIsNotNull(paramFile, "Uri.fromFile(this)");
    return paramFile;
  }
  
  public static final Uri toUri(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toUri");
    paramString = Uri.parse(paramString);
    Intrinsics.checkExpressionValueIsNotNull(paramString, "Uri.parse(this)");
    return paramString;
  }
}

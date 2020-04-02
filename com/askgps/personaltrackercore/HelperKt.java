package com.askgps.personaltrackercore;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.MediaStore.Files;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

@Metadata(bv={1, 0, 3}, d1={"\000\036\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\016\n\002\b\003\032\026\020\000\032\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\005\032\020\020\006\032\0020\0012\b\020\007\032\004\030\0010\b\032\016\020\t\032\0020\b2\006\020\n\032\0020\b?\006\013"}, d2={"deleteFileFromMediaStore", "", "contentResolver", "Landroid/content/ContentResolver;", "file", "Ljava/io/File;", "deletePictureFile", "path", "", "md5", "s", "personaltrackercore_release"}, k=2, mv={1, 1, 16})
public final class HelperKt
{
  public static final void deleteFileFromMediaStore(ContentResolver paramContentResolver, File paramFile)
  {
    Intrinsics.checkParameterIsNotNull(paramContentResolver, "contentResolver");
    Intrinsics.checkParameterIsNotNull(paramFile, "file");
    String str2;
    try
    {
      String str1 = paramFile.getCanonicalPath();
      Intrinsics.checkExpressionValueIsNotNull(str1, "file.canonicalPath");
    }
    catch (IOException localIOException)
    {
      str2 = paramFile.getAbsolutePath();
      Intrinsics.checkExpressionValueIsNotNull(str2, "file.absolutePath");
    }
    Uri localUri = MediaStore.Files.getContentUri("external");
    if (paramContentResolver.delete(localUri, "_data=?", new String[] { str2 }) == 0)
    {
      paramFile = paramFile.getAbsolutePath();
      Intrinsics.checkExpressionValueIsNotNull(paramFile, "file.absolutePath");
      if ((Intrinsics.areEqual(paramFile, str2) ^ true)) {
        paramContentResolver.delete(localUri, "_data=?", new String[] { paramFile });
      }
    }
  }
  
  public static final void deletePictureFile(String paramString)
  {
    if (paramString != null) {
      LogKt.toFile$default(Boolean.valueOf(new File(paramString).delete()), "image was deleted", null, null, 6, null);
    }
  }
  
  public static final String md5(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "s");
    try
    {
      Object localObject = MessageDigest.getInstance("MD5");
      Intrinsics.checkExpressionValueIsNotNull(localObject, "MessageDigest.getInstance(\"MD5\")");
      paramString = paramString.getBytes(Charsets.UTF_8);
      Intrinsics.checkExpressionValueIsNotNull(paramString, "(this as java.lang.String).getBytes(charset)");
      ((MessageDigest)localObject).update(paramString);
      paramString = ((MessageDigest)localObject).digest();
      Intrinsics.checkExpressionValueIsNotNull(paramString, "digest.digest()");
      localObject = new java/lang/StringBuffer;
      ((StringBuffer)localObject).<init>();
      int i = 0;
      int j = paramString.length;
      while (i < j)
      {
        ((StringBuffer)localObject).append(Integer.toHexString(paramString[i] & 0xFF));
        i++;
      }
      paramString = ((StringBuffer)localObject).toString();
      Intrinsics.checkExpressionValueIsNotNull(paramString, "hexString.toString()");
      return paramString;
    }
    catch (NoSuchAlgorithmException paramString)
    {
      paramString.printStackTrace();
    }
    return "";
  }
}

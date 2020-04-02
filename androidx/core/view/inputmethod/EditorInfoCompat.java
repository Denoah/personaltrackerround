package androidx.core.view.inputmethod;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;

public final class EditorInfoCompat
{
  private static final String CONTENT_MIME_TYPES_INTEROP_KEY = "android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES";
  private static final String CONTENT_MIME_TYPES_KEY = "androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES";
  private static final String[] EMPTY_STRING_ARRAY = new String[0];
  public static final int IME_FLAG_FORCE_ASCII = Integer.MIN_VALUE;
  public static final int IME_FLAG_NO_PERSONALIZED_LEARNING = 16777216;
  
  @Deprecated
  public EditorInfoCompat() {}
  
  public static String[] getContentMimeTypes(EditorInfo paramEditorInfo)
  {
    if (Build.VERSION.SDK_INT >= 25)
    {
      paramEditorInfo = paramEditorInfo.contentMimeTypes;
      if (paramEditorInfo == null) {
        paramEditorInfo = EMPTY_STRING_ARRAY;
      }
      return paramEditorInfo;
    }
    if (paramEditorInfo.extras == null) {
      return EMPTY_STRING_ARRAY;
    }
    String[] arrayOfString1 = paramEditorInfo.extras.getStringArray("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES");
    String[] arrayOfString2 = arrayOfString1;
    if (arrayOfString1 == null) {
      arrayOfString2 = paramEditorInfo.extras.getStringArray("android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES");
    }
    if (arrayOfString2 == null) {
      arrayOfString2 = EMPTY_STRING_ARRAY;
    }
    return arrayOfString2;
  }
  
  static int getProtocol(EditorInfo paramEditorInfo)
  {
    if (Build.VERSION.SDK_INT >= 25) {
      return 1;
    }
    if (paramEditorInfo.extras == null) {
      return 0;
    }
    boolean bool1 = paramEditorInfo.extras.containsKey("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES");
    boolean bool2 = paramEditorInfo.extras.containsKey("android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES");
    if ((bool1) && (bool2)) {
      return 4;
    }
    if (bool1) {
      return 3;
    }
    if (bool2) {
      return 2;
    }
    return 0;
  }
  
  public static void setContentMimeTypes(EditorInfo paramEditorInfo, String[] paramArrayOfString)
  {
    if (Build.VERSION.SDK_INT >= 25)
    {
      paramEditorInfo.contentMimeTypes = paramArrayOfString;
    }
    else
    {
      if (paramEditorInfo.extras == null) {
        paramEditorInfo.extras = new Bundle();
      }
      paramEditorInfo.extras.putStringArray("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES", paramArrayOfString);
      paramEditorInfo.extras.putStringArray("android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES", paramArrayOfString);
    }
  }
}

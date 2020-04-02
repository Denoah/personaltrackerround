package androidx.core.view.inputmethod;

import android.content.ClipDescription;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputContentInfo;

public final class InputConnectionCompat
{
  private static final String COMMIT_CONTENT_ACTION = "androidx.core.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
  private static final String COMMIT_CONTENT_CONTENT_URI_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_URI";
  private static final String COMMIT_CONTENT_CONTENT_URI_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_URI";
  private static final String COMMIT_CONTENT_DESCRIPTION_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
  private static final String COMMIT_CONTENT_DESCRIPTION_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
  private static final String COMMIT_CONTENT_FLAGS_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
  private static final String COMMIT_CONTENT_FLAGS_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
  private static final String COMMIT_CONTENT_INTEROP_ACTION = "android.support.v13.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
  private static final String COMMIT_CONTENT_LINK_URI_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
  private static final String COMMIT_CONTENT_LINK_URI_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
  private static final String COMMIT_CONTENT_OPTS_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
  private static final String COMMIT_CONTENT_OPTS_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
  private static final String COMMIT_CONTENT_RESULT_INTEROP_RECEIVER_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";
  private static final String COMMIT_CONTENT_RESULT_RECEIVER_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";
  public static final int INPUT_CONTENT_GRANT_READ_URI_PERMISSION = 1;
  
  @Deprecated
  public InputConnectionCompat() {}
  
  public static boolean commitContent(InputConnection paramInputConnection, EditorInfo paramEditorInfo, InputContentInfoCompat paramInputContentInfoCompat, int paramInt, Bundle paramBundle)
  {
    Object localObject = paramInputContentInfoCompat.getDescription();
    String[] arrayOfString = EditorInfoCompat.getContentMimeTypes(paramEditorInfo);
    int i = arrayOfString.length;
    int j = 0;
    for (int k = 0; k < i; k++) {
      if (((ClipDescription)localObject).hasMimeType(arrayOfString[k]))
      {
        k = 1;
        break label58;
      }
    }
    k = 0;
    label58:
    if (k == 0) {
      return false;
    }
    if (Build.VERSION.SDK_INT >= 25) {
      return paramInputConnection.commitContent((InputContentInfo)paramInputContentInfoCompat.unwrap(), paramInt, paramBundle);
    }
    i = EditorInfoCompat.getProtocol(paramEditorInfo);
    if (i != 2)
    {
      k = j;
      if (i != 3)
      {
        k = j;
        if (i != 4) {
          return false;
        }
      }
    }
    else
    {
      k = 1;
    }
    localObject = new Bundle();
    if (k != 0) {
      paramEditorInfo = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_URI";
    } else {
      paramEditorInfo = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_URI";
    }
    ((Bundle)localObject).putParcelable(paramEditorInfo, paramInputContentInfoCompat.getContentUri());
    if (k != 0) {
      paramEditorInfo = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
    } else {
      paramEditorInfo = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
    }
    ((Bundle)localObject).putParcelable(paramEditorInfo, paramInputContentInfoCompat.getDescription());
    if (k != 0) {
      paramEditorInfo = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
    } else {
      paramEditorInfo = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
    }
    ((Bundle)localObject).putParcelable(paramEditorInfo, paramInputContentInfoCompat.getLinkUri());
    if (k != 0) {
      paramEditorInfo = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
    } else {
      paramEditorInfo = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
    }
    ((Bundle)localObject).putInt(paramEditorInfo, paramInt);
    if (k != 0) {
      paramEditorInfo = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
    } else {
      paramEditorInfo = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
    }
    ((Bundle)localObject).putParcelable(paramEditorInfo, paramBundle);
    if (k != 0) {
      paramEditorInfo = "android.support.v13.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
    } else {
      paramEditorInfo = "androidx.core.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
    }
    return paramInputConnection.performPrivateCommand(paramEditorInfo, (Bundle)localObject);
  }
  
  public static InputConnection createWrapper(InputConnection paramInputConnection, EditorInfo paramEditorInfo, final OnCommitContentListener paramOnCommitContentListener)
  {
    if (paramInputConnection != null)
    {
      if (paramEditorInfo != null)
      {
        if (paramOnCommitContentListener != null)
        {
          if (Build.VERSION.SDK_INT >= 25) {
            new InputConnectionWrapper(paramInputConnection, false)
            {
              public boolean commitContent(InputContentInfo paramAnonymousInputContentInfo, int paramAnonymousInt, Bundle paramAnonymousBundle)
              {
                if (paramOnCommitContentListener.onCommitContent(InputContentInfoCompat.wrap(paramAnonymousInputContentInfo), paramAnonymousInt, paramAnonymousBundle)) {
                  return true;
                }
                return super.commitContent(paramAnonymousInputContentInfo, paramAnonymousInt, paramAnonymousBundle);
              }
            };
          }
          if (EditorInfoCompat.getContentMimeTypes(paramEditorInfo).length == 0) {
            return paramInputConnection;
          }
          new InputConnectionWrapper(paramInputConnection, false)
          {
            public boolean performPrivateCommand(String paramAnonymousString, Bundle paramAnonymousBundle)
            {
              if (InputConnectionCompat.handlePerformPrivateCommand(paramAnonymousString, paramAnonymousBundle, paramOnCommitContentListener)) {
                return true;
              }
              return super.performPrivateCommand(paramAnonymousString, paramAnonymousBundle);
            }
          };
        }
        throw new IllegalArgumentException("onCommitContentListener must be non-null");
      }
      throw new IllegalArgumentException("editorInfo must be non-null");
    }
    throw new IllegalArgumentException("inputConnection must be non-null");
  }
  
  /* Error */
  static boolean handlePerformPrivateCommand(String paramString, Bundle paramBundle, OnCommitContentListener paramOnCommitContentListener)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_3
    //   2: aload_1
    //   3: ifnonnull +5 -> 8
    //   6: iconst_0
    //   7: ireturn
    //   8: ldc 15
    //   10: aload_0
    //   11: invokestatic 151	android/text/TextUtils:equals	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   14: ifeq +9 -> 23
    //   17: iconst_0
    //   18: istore 4
    //   20: goto +15 -> 35
    //   23: ldc 36
    //   25: aload_0
    //   26: invokestatic 151	android/text/TextUtils:equals	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   29: ifeq +226 -> 255
    //   32: iconst_1
    //   33: istore 4
    //   35: iload 4
    //   37: ifeq +9 -> 46
    //   40: ldc 51
    //   42: astore_0
    //   43: goto +6 -> 49
    //   46: ldc 54
    //   48: astore_0
    //   49: aload_1
    //   50: aload_0
    //   51: invokevirtual 155	android/os/Bundle:getParcelable	(Ljava/lang/String;)Landroid/os/Parcelable;
    //   54: checkcast 157	android/os/ResultReceiver
    //   57: astore 5
    //   59: iload 4
    //   61: ifeq +9 -> 70
    //   64: ldc 18
    //   66: astore_0
    //   67: goto +6 -> 73
    //   70: ldc 21
    //   72: astore_0
    //   73: aload_1
    //   74: aload_0
    //   75: invokevirtual 155	android/os/Bundle:getParcelable	(Ljava/lang/String;)Landroid/os/Parcelable;
    //   78: checkcast 159	android/net/Uri
    //   81: astore 6
    //   83: iload 4
    //   85: ifeq +9 -> 94
    //   88: ldc 24
    //   90: astore_0
    //   91: goto +6 -> 97
    //   94: ldc 27
    //   96: astore_0
    //   97: aload_1
    //   98: aload_0
    //   99: invokevirtual 155	android/os/Bundle:getParcelable	(Ljava/lang/String;)Landroid/os/Parcelable;
    //   102: checkcast 80	android/content/ClipDescription
    //   105: astore 7
    //   107: iload 4
    //   109: ifeq +9 -> 118
    //   112: ldc 39
    //   114: astore_0
    //   115: goto +6 -> 121
    //   118: ldc 42
    //   120: astore_0
    //   121: aload_1
    //   122: aload_0
    //   123: invokevirtual 155	android/os/Bundle:getParcelable	(Ljava/lang/String;)Landroid/os/Parcelable;
    //   126: checkcast 159	android/net/Uri
    //   129: astore 8
    //   131: iload 4
    //   133: ifeq +9 -> 142
    //   136: ldc 30
    //   138: astore_0
    //   139: goto +6 -> 145
    //   142: ldc 33
    //   144: astore_0
    //   145: aload_1
    //   146: aload_0
    //   147: invokevirtual 163	android/os/Bundle:getInt	(Ljava/lang/String;)I
    //   150: istore 9
    //   152: iload 4
    //   154: ifeq +9 -> 163
    //   157: ldc 45
    //   159: astore_0
    //   160: goto +6 -> 166
    //   163: ldc 48
    //   165: astore_0
    //   166: aload_1
    //   167: aload_0
    //   168: invokevirtual 155	android/os/Bundle:getParcelable	(Ljava/lang/String;)Landroid/os/Parcelable;
    //   171: checkcast 106	android/os/Bundle
    //   174: astore_1
    //   175: iload_3
    //   176: istore 4
    //   178: aload 6
    //   180: ifnull +37 -> 217
    //   183: iload_3
    //   184: istore 4
    //   186: aload 7
    //   188: ifnull +29 -> 217
    //   191: new 68	androidx/core/view/inputmethod/InputContentInfoCompat
    //   194: astore_0
    //   195: aload_0
    //   196: aload 6
    //   198: aload 7
    //   200: aload 8
    //   202: invokespecial 166	androidx/core/view/inputmethod/InputContentInfoCompat:<init>	(Landroid/net/Uri;Landroid/content/ClipDescription;Landroid/net/Uri;)V
    //   205: aload_2
    //   206: aload_0
    //   207: iload 9
    //   209: aload_1
    //   210: invokeinterface 170 4 0
    //   215: istore 4
    //   217: aload 5
    //   219: ifnull +11 -> 230
    //   222: aload 5
    //   224: iload 4
    //   226: aconst_null
    //   227: invokevirtual 174	android/os/ResultReceiver:send	(ILandroid/os/Bundle;)V
    //   230: iload 4
    //   232: ireturn
    //   233: astore_1
    //   234: aload 5
    //   236: astore_0
    //   237: goto +6 -> 243
    //   240: astore_1
    //   241: aconst_null
    //   242: astore_0
    //   243: aload_0
    //   244: ifnull +9 -> 253
    //   247: aload_0
    //   248: iconst_0
    //   249: aconst_null
    //   250: invokevirtual 174	android/os/ResultReceiver:send	(ILandroid/os/Bundle;)V
    //   253: aload_1
    //   254: athrow
    //   255: iconst_0
    //   256: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	257	0	paramString	String
    //   0	257	1	paramBundle	Bundle
    //   0	257	2	paramOnCommitContentListener	OnCommitContentListener
    //   1	183	3	i	int
    //   18	213	4	j	int
    //   57	178	5	localResultReceiver	android.os.ResultReceiver
    //   81	116	6	localUri1	android.net.Uri
    //   105	94	7	localClipDescription	ClipDescription
    //   129	72	8	localUri2	android.net.Uri
    //   150	58	9	k	int
    // Exception table:
    //   from	to	target	type
    //   73	83	233	finally
    //   97	107	233	finally
    //   121	131	233	finally
    //   145	152	233	finally
    //   166	175	233	finally
    //   191	217	233	finally
    //   49	59	240	finally
  }
  
  public static abstract interface OnCommitContentListener
  {
    public abstract boolean onCommitContent(InputContentInfoCompat paramInputContentInfoCompat, int paramInt, Bundle paramBundle);
  }
}

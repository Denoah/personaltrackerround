package com.askgps.personaltrackercore.extension;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\f\n\000\n\002\030\002\n\002\030\002\n\000\032\f\020\000\032\004\030\0010\001*\0020\002?\006\003"}, d2={"dispatchTakePictureIntent", "Ljava/io/File;", "Landroid/app/Activity;", "personaltrackercore_release"}, k=2, mv={1, 1, 16})
public final class ExtensionKt
{
  public static final File dispatchTakePictureIntent(Activity paramActivity)
  {
    Intrinsics.checkParameterIsNotNull(paramActivity, "$this$dispatchTakePictureIntent");
    Intent localIntent = new Intent("android.media.action.IMAGE_CAPTURE");
    ComponentName localComponentName = localIntent.resolveActivity(paramActivity.getPackageManager());
    Object localObject1 = null;
    Object localObject2 = null;
    if (localComponentName != null) {}
    try
    {
      localObject1 = ContextExtensionKt.createImageFile((Context)paramActivity);
      localObject2 = localObject1;
    }
    catch (IOException localIOException)
    {
      for (;;) {}
    }
    localObject1 = localObject2;
    if (localObject2 != null)
    {
      localObject1 = FileProvider.getUriForFile((Context)paramActivity, paramActivity.getPackageName(), localObject2);
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "FileProvider.getUriForFi…     it\n                )");
      localIntent.putExtra("output", (Parcelable)localObject1);
      paramActivity.startActivityForResult(localIntent, 2);
      localObject1 = localObject2;
    }
    return localObject1;
  }
}

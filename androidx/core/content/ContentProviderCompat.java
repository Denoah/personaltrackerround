package androidx.core.content;

import android.content.ContentProvider;
import android.content.Context;

public final class ContentProviderCompat
{
  private ContentProviderCompat() {}
  
  public static Context requireContext(ContentProvider paramContentProvider)
  {
    paramContentProvider = paramContentProvider.getContext();
    if (paramContentProvider != null) {
      return paramContentProvider;
    }
    throw new IllegalStateException("Cannot find context from the provider.");
  }
}

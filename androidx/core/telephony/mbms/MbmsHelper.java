package androidx.core.telephony.mbms;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.LocaleList;
import android.telephony.mbms.ServiceInfo;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public final class MbmsHelper
{
  private MbmsHelper() {}
  
  public static CharSequence getBestNameForService(Context paramContext, ServiceInfo paramServiceInfo)
  {
    int i = Build.VERSION.SDK_INT;
    Object localObject = null;
    if (i < 28) {
      return null;
    }
    LocaleList localLocaleList = paramContext.getResources().getConfiguration().getLocales();
    i = paramServiceInfo.getNamedContentLocales().size();
    if (i == 0) {
      return null;
    }
    String[] arrayOfString = new String[i];
    i = 0;
    paramContext = paramServiceInfo.getNamedContentLocales().iterator();
    while (paramContext.hasNext())
    {
      arrayOfString[i] = ((Locale)paramContext.next()).toLanguageTag();
      i++;
    }
    paramContext = localLocaleList.getFirstMatch(arrayOfString);
    if (paramContext == null) {
      paramContext = localObject;
    } else {
      paramContext = paramServiceInfo.getNameForLocale(paramContext);
    }
    return paramContext;
  }
}

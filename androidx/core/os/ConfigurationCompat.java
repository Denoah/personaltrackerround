package androidx.core.os;

import android.content.res.Configuration;
import android.os.Build.VERSION;
import java.util.Locale;

public final class ConfigurationCompat
{
  private ConfigurationCompat() {}
  
  public static LocaleListCompat getLocales(Configuration paramConfiguration)
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return LocaleListCompat.wrap(paramConfiguration.getLocales());
    }
    return LocaleListCompat.create(new Locale[] { paramConfiguration.locale });
  }
}

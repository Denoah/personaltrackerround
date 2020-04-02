package androidx.core.os;

import java.util.Locale;

abstract interface LocaleListInterface
{
  public abstract Locale get(int paramInt);
  
  public abstract Locale getFirstMatch(String[] paramArrayOfString);
  
  public abstract Object getLocaleList();
  
  public abstract int indexOf(Locale paramLocale);
  
  public abstract boolean isEmpty();
  
  public abstract int size();
  
  public abstract String toLanguageTags();
}

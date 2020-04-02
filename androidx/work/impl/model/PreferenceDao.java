package androidx.work.impl.model;

import androidx.lifecycle.LiveData;

public abstract interface PreferenceDao
{
  public abstract Long getLongValue(String paramString);
  
  public abstract LiveData<Long> getObservableLongValue(String paramString);
  
  public abstract void insertPreference(Preference paramPreference);
}

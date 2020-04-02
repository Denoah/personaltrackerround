package androidx.core.content.pm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutInfo.Builder;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.PersistableBundle;
import android.text.TextUtils;
import androidx.core.graphics.drawable.IconCompat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ShortcutInfoCompat
{
  private static final String EXTRA_LONG_LIVED = "extraLongLived";
  private static final String EXTRA_PERSON_ = "extraPerson_";
  private static final String EXTRA_PERSON_COUNT = "extraPersonCount";
  ComponentName mActivity;
  Set<String> mCategories;
  Context mContext;
  CharSequence mDisabledMessage;
  IconCompat mIcon;
  String mId;
  Intent[] mIntents;
  boolean mIsAlwaysBadged;
  boolean mIsLongLived;
  CharSequence mLabel;
  CharSequence mLongLabel;
  androidx.core.app.Person[] mPersons;
  int mRank;
  
  ShortcutInfoCompat() {}
  
  private PersistableBundle buildLegacyExtrasBundle()
  {
    PersistableBundle localPersistableBundle = new PersistableBundle();
    Object localObject = this.mPersons;
    if ((localObject != null) && (localObject.length > 0))
    {
      localPersistableBundle.putInt("extraPersonCount", localObject.length);
      int j;
      for (int i = 0; i < this.mPersons.length; i = j)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("extraPerson_");
        j = i + 1;
        ((StringBuilder)localObject).append(j);
        localPersistableBundle.putPersistableBundle(((StringBuilder)localObject).toString(), this.mPersons[i].toPersistableBundle());
      }
    }
    localPersistableBundle.putBoolean("extraLongLived", this.mIsLongLived);
    return localPersistableBundle;
  }
  
  static boolean getLongLivedFromExtra(PersistableBundle paramPersistableBundle)
  {
    if ((paramPersistableBundle != null) && (paramPersistableBundle.containsKey("extraLongLived"))) {
      return paramPersistableBundle.getBoolean("extraLongLived");
    }
    return false;
  }
  
  static androidx.core.app.Person[] getPersonsFromExtra(PersistableBundle paramPersistableBundle)
  {
    if ((paramPersistableBundle != null) && (paramPersistableBundle.containsKey("extraPersonCount")))
    {
      int i = paramPersistableBundle.getInt("extraPersonCount");
      androidx.core.app.Person[] arrayOfPerson = new androidx.core.app.Person[i];
      int k;
      for (int j = 0; j < i; j = k)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("extraPerson_");
        k = j + 1;
        localStringBuilder.append(k);
        arrayOfPerson[j] = androidx.core.app.Person.fromPersistableBundle(paramPersistableBundle.getPersistableBundle(localStringBuilder.toString()));
      }
      return arrayOfPerson;
    }
    return null;
  }
  
  Intent addToIntent(Intent paramIntent)
  {
    Object localObject1 = this.mIntents;
    paramIntent.putExtra("android.intent.extra.shortcut.INTENT", localObject1[(localObject1.length - 1)]).putExtra("android.intent.extra.shortcut.NAME", this.mLabel.toString());
    Object localObject3;
    Object localObject4;
    PackageManager localPackageManager;
    if (this.mIcon != null)
    {
      localObject3 = null;
      localObject4 = null;
      if (this.mIsAlwaysBadged)
      {
        localPackageManager = this.mContext.getPackageManager();
        localObject3 = this.mActivity;
        localObject1 = localObject4;
        if (localObject3 == null) {}
      }
    }
    try
    {
      localObject1 = localPackageManager.getActivityIcon((ComponentName)localObject3);
      localObject3 = localObject1;
      if (localObject1 == null) {
        localObject3 = this.mContext.getApplicationInfo().loadIcon(localPackageManager);
      }
      this.mIcon.addToShortcutIntent(paramIntent, (Drawable)localObject3, this.mContext);
      return paramIntent;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      for (;;)
      {
        Object localObject2 = localObject4;
      }
    }
  }
  
  public ComponentName getActivity()
  {
    return this.mActivity;
  }
  
  public Set<String> getCategories()
  {
    return this.mCategories;
  }
  
  public CharSequence getDisabledMessage()
  {
    return this.mDisabledMessage;
  }
  
  public IconCompat getIcon()
  {
    return this.mIcon;
  }
  
  public String getId()
  {
    return this.mId;
  }
  
  public Intent getIntent()
  {
    Intent[] arrayOfIntent = this.mIntents;
    return arrayOfIntent[(arrayOfIntent.length - 1)];
  }
  
  public Intent[] getIntents()
  {
    Intent[] arrayOfIntent = this.mIntents;
    return (Intent[])Arrays.copyOf(arrayOfIntent, arrayOfIntent.length);
  }
  
  public CharSequence getLongLabel()
  {
    return this.mLongLabel;
  }
  
  public int getRank()
  {
    return this.mRank;
  }
  
  public CharSequence getShortLabel()
  {
    return this.mLabel;
  }
  
  public ShortcutInfo toShortcutInfo()
  {
    ShortcutInfo.Builder localBuilder = new ShortcutInfo.Builder(this.mContext, this.mId).setShortLabel(this.mLabel).setIntents(this.mIntents);
    Object localObject = this.mIcon;
    if (localObject != null) {
      localBuilder.setIcon(((IconCompat)localObject).toIcon(this.mContext));
    }
    if (!TextUtils.isEmpty(this.mLongLabel)) {
      localBuilder.setLongLabel(this.mLongLabel);
    }
    if (!TextUtils.isEmpty(this.mDisabledMessage)) {
      localBuilder.setDisabledMessage(this.mDisabledMessage);
    }
    localObject = this.mActivity;
    if (localObject != null) {
      localBuilder.setActivity((ComponentName)localObject);
    }
    localObject = this.mCategories;
    if (localObject != null) {
      localBuilder.setCategories((Set)localObject);
    }
    localBuilder.setRank(this.mRank);
    if (Build.VERSION.SDK_INT >= 29)
    {
      localObject = this.mPersons;
      if ((localObject != null) && (localObject.length > 0))
      {
        int i = localObject.length;
        localObject = new android.app.Person[i];
        for (int j = 0; j < i; j++) {
          localObject[j] = this.mPersons[j].toAndroidPerson();
        }
        localBuilder.setPersons((android.app.Person[])localObject);
      }
      localBuilder.setLongLived(this.mIsLongLived);
    }
    else
    {
      localBuilder.setExtras(buildLegacyExtrasBundle());
    }
    return localBuilder.build();
  }
  
  public static class Builder
  {
    private final ShortcutInfoCompat mInfo;
    
    public Builder(Context paramContext, ShortcutInfo paramShortcutInfo)
    {
      ShortcutInfoCompat localShortcutInfoCompat = new ShortcutInfoCompat();
      this.mInfo = localShortcutInfoCompat;
      localShortcutInfoCompat.mContext = paramContext;
      this.mInfo.mId = paramShortcutInfo.getId();
      paramContext = paramShortcutInfo.getIntents();
      this.mInfo.mIntents = ((Intent[])Arrays.copyOf(paramContext, paramContext.length));
      this.mInfo.mActivity = paramShortcutInfo.getActivity();
      this.mInfo.mLabel = paramShortcutInfo.getShortLabel();
      this.mInfo.mLongLabel = paramShortcutInfo.getLongLabel();
      this.mInfo.mDisabledMessage = paramShortcutInfo.getDisabledMessage();
      this.mInfo.mCategories = paramShortcutInfo.getCategories();
      this.mInfo.mPersons = ShortcutInfoCompat.getPersonsFromExtra(paramShortcutInfo.getExtras());
      this.mInfo.mRank = paramShortcutInfo.getRank();
    }
    
    public Builder(Context paramContext, String paramString)
    {
      ShortcutInfoCompat localShortcutInfoCompat = new ShortcutInfoCompat();
      this.mInfo = localShortcutInfoCompat;
      localShortcutInfoCompat.mContext = paramContext;
      this.mInfo.mId = paramString;
    }
    
    public Builder(ShortcutInfoCompat paramShortcutInfoCompat)
    {
      ShortcutInfoCompat localShortcutInfoCompat = new ShortcutInfoCompat();
      this.mInfo = localShortcutInfoCompat;
      localShortcutInfoCompat.mContext = paramShortcutInfoCompat.mContext;
      this.mInfo.mId = paramShortcutInfoCompat.mId;
      this.mInfo.mIntents = ((Intent[])Arrays.copyOf(paramShortcutInfoCompat.mIntents, paramShortcutInfoCompat.mIntents.length));
      this.mInfo.mActivity = paramShortcutInfoCompat.mActivity;
      this.mInfo.mLabel = paramShortcutInfoCompat.mLabel;
      this.mInfo.mLongLabel = paramShortcutInfoCompat.mLongLabel;
      this.mInfo.mDisabledMessage = paramShortcutInfoCompat.mDisabledMessage;
      this.mInfo.mIcon = paramShortcutInfoCompat.mIcon;
      this.mInfo.mIsAlwaysBadged = paramShortcutInfoCompat.mIsAlwaysBadged;
      this.mInfo.mIsLongLived = paramShortcutInfoCompat.mIsLongLived;
      this.mInfo.mRank = paramShortcutInfoCompat.mRank;
      if (paramShortcutInfoCompat.mPersons != null) {
        this.mInfo.mPersons = ((androidx.core.app.Person[])Arrays.copyOf(paramShortcutInfoCompat.mPersons, paramShortcutInfoCompat.mPersons.length));
      }
      if (paramShortcutInfoCompat.mCategories != null) {
        this.mInfo.mCategories = new HashSet(paramShortcutInfoCompat.mCategories);
      }
    }
    
    public ShortcutInfoCompat build()
    {
      if (!TextUtils.isEmpty(this.mInfo.mLabel))
      {
        if ((this.mInfo.mIntents != null) && (this.mInfo.mIntents.length != 0)) {
          return this.mInfo;
        }
        throw new IllegalArgumentException("Shortcut must have an intent");
      }
      throw new IllegalArgumentException("Shortcut must have a non-empty label");
    }
    
    public Builder setActivity(ComponentName paramComponentName)
    {
      this.mInfo.mActivity = paramComponentName;
      return this;
    }
    
    public Builder setAlwaysBadged()
    {
      this.mInfo.mIsAlwaysBadged = true;
      return this;
    }
    
    public Builder setCategories(Set<String> paramSet)
    {
      this.mInfo.mCategories = paramSet;
      return this;
    }
    
    public Builder setDisabledMessage(CharSequence paramCharSequence)
    {
      this.mInfo.mDisabledMessage = paramCharSequence;
      return this;
    }
    
    public Builder setIcon(IconCompat paramIconCompat)
    {
      this.mInfo.mIcon = paramIconCompat;
      return this;
    }
    
    public Builder setIntent(Intent paramIntent)
    {
      return setIntents(new Intent[] { paramIntent });
    }
    
    public Builder setIntents(Intent[] paramArrayOfIntent)
    {
      this.mInfo.mIntents = paramArrayOfIntent;
      return this;
    }
    
    public Builder setLongLabel(CharSequence paramCharSequence)
    {
      this.mInfo.mLongLabel = paramCharSequence;
      return this;
    }
    
    @Deprecated
    public Builder setLongLived()
    {
      this.mInfo.mIsLongLived = true;
      return this;
    }
    
    public Builder setLongLived(boolean paramBoolean)
    {
      this.mInfo.mIsLongLived = paramBoolean;
      return this;
    }
    
    public Builder setPerson(androidx.core.app.Person paramPerson)
    {
      return setPersons(new androidx.core.app.Person[] { paramPerson });
    }
    
    public Builder setPersons(androidx.core.app.Person[] paramArrayOfPerson)
    {
      this.mInfo.mPersons = paramArrayOfPerson;
      return this;
    }
    
    public Builder setRank(int paramInt)
    {
      this.mInfo.mRank = paramInt;
      return this;
    }
    
    public Builder setShortLabel(CharSequence paramCharSequence)
    {
      this.mInfo.mLabel = paramCharSequence;
      return this;
    }
  }
}

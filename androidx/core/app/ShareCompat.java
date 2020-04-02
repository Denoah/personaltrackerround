package androidx.core.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.ActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import androidx.core.util.Preconditions;
import java.util.ArrayList;

public final class ShareCompat
{
  public static final String EXTRA_CALLING_ACTIVITY = "androidx.core.app.EXTRA_CALLING_ACTIVITY";
  public static final String EXTRA_CALLING_ACTIVITY_INTEROP = "android.support.v4.app.EXTRA_CALLING_ACTIVITY";
  public static final String EXTRA_CALLING_PACKAGE = "androidx.core.app.EXTRA_CALLING_PACKAGE";
  public static final String EXTRA_CALLING_PACKAGE_INTEROP = "android.support.v4.app.EXTRA_CALLING_PACKAGE";
  private static final String HISTORY_FILENAME_PREFIX = ".sharecompat_";
  
  private ShareCompat() {}
  
  public static void configureMenuItem(Menu paramMenu, int paramInt, IntentBuilder paramIntentBuilder)
  {
    paramMenu = paramMenu.findItem(paramInt);
    if (paramMenu != null)
    {
      configureMenuItem(paramMenu, paramIntentBuilder);
      return;
    }
    paramMenu = new StringBuilder();
    paramMenu.append("Could not find menu item with id ");
    paramMenu.append(paramInt);
    paramMenu.append(" in the supplied menu");
    throw new IllegalArgumentException(paramMenu.toString());
  }
  
  public static void configureMenuItem(MenuItem paramMenuItem, IntentBuilder paramIntentBuilder)
  {
    Object localObject = paramMenuItem.getActionProvider();
    if (!(localObject instanceof ShareActionProvider)) {
      localObject = new ShareActionProvider(paramIntentBuilder.getContext());
    } else {
      localObject = (ShareActionProvider)localObject;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(".sharecompat_");
    localStringBuilder.append(paramIntentBuilder.getContext().getClass().getName());
    ((ShareActionProvider)localObject).setShareHistoryFileName(localStringBuilder.toString());
    ((ShareActionProvider)localObject).setShareIntent(paramIntentBuilder.getIntent());
    paramMenuItem.setActionProvider((ActionProvider)localObject);
    if ((Build.VERSION.SDK_INT < 16) && (!paramMenuItem.hasSubMenu())) {
      paramMenuItem.setIntent(paramIntentBuilder.createChooserIntent());
    }
  }
  
  public static ComponentName getCallingActivity(Activity paramActivity)
  {
    Intent localIntent = paramActivity.getIntent();
    ComponentName localComponentName = paramActivity.getCallingActivity();
    paramActivity = localComponentName;
    if (localComponentName == null) {
      paramActivity = getCallingActivity(localIntent);
    }
    return paramActivity;
  }
  
  static ComponentName getCallingActivity(Intent paramIntent)
  {
    ComponentName localComponentName1 = (ComponentName)paramIntent.getParcelableExtra("androidx.core.app.EXTRA_CALLING_ACTIVITY");
    ComponentName localComponentName2 = localComponentName1;
    if (localComponentName1 == null) {
      localComponentName2 = (ComponentName)paramIntent.getParcelableExtra("android.support.v4.app.EXTRA_CALLING_ACTIVITY");
    }
    return localComponentName2;
  }
  
  public static String getCallingPackage(Activity paramActivity)
  {
    Intent localIntent = paramActivity.getIntent();
    String str = paramActivity.getCallingPackage();
    paramActivity = str;
    if (str == null)
    {
      paramActivity = str;
      if (localIntent != null) {
        paramActivity = getCallingPackage(localIntent);
      }
    }
    return paramActivity;
  }
  
  static String getCallingPackage(Intent paramIntent)
  {
    String str1 = paramIntent.getStringExtra("androidx.core.app.EXTRA_CALLING_PACKAGE");
    String str2 = str1;
    if (str1 == null) {
      str2 = paramIntent.getStringExtra("android.support.v4.app.EXTRA_CALLING_PACKAGE");
    }
    return str2;
  }
  
  public static class IntentBuilder
  {
    private ArrayList<String> mBccAddresses;
    private ArrayList<String> mCcAddresses;
    private CharSequence mChooserTitle;
    private final Context mContext;
    private final Intent mIntent;
    private ArrayList<Uri> mStreams;
    private ArrayList<String> mToAddresses;
    
    private IntentBuilder(Context paramContext, ComponentName paramComponentName)
    {
      this.mContext = ((Context)Preconditions.checkNotNull(paramContext));
      Intent localIntent = new Intent().setAction("android.intent.action.SEND");
      this.mIntent = localIntent;
      localIntent.putExtra("androidx.core.app.EXTRA_CALLING_PACKAGE", paramContext.getPackageName());
      this.mIntent.putExtra("android.support.v4.app.EXTRA_CALLING_PACKAGE", paramContext.getPackageName());
      this.mIntent.putExtra("androidx.core.app.EXTRA_CALLING_ACTIVITY", paramComponentName);
      this.mIntent.putExtra("android.support.v4.app.EXTRA_CALLING_ACTIVITY", paramComponentName);
      this.mIntent.addFlags(524288);
    }
    
    private void combineArrayExtra(String paramString, ArrayList<String> paramArrayList)
    {
      String[] arrayOfString1 = this.mIntent.getStringArrayExtra(paramString);
      int i;
      if (arrayOfString1 != null) {
        i = arrayOfString1.length;
      } else {
        i = 0;
      }
      String[] arrayOfString2 = new String[paramArrayList.size() + i];
      paramArrayList.toArray(arrayOfString2);
      if (arrayOfString1 != null) {
        System.arraycopy(arrayOfString1, 0, arrayOfString2, paramArrayList.size(), i);
      }
      this.mIntent.putExtra(paramString, arrayOfString2);
    }
    
    private void combineArrayExtra(String paramString, String[] paramArrayOfString)
    {
      Intent localIntent = getIntent();
      String[] arrayOfString1 = localIntent.getStringArrayExtra(paramString);
      int i;
      if (arrayOfString1 != null) {
        i = arrayOfString1.length;
      } else {
        i = 0;
      }
      String[] arrayOfString2 = new String[paramArrayOfString.length + i];
      if (arrayOfString1 != null) {
        System.arraycopy(arrayOfString1, 0, arrayOfString2, 0, i);
      }
      System.arraycopy(paramArrayOfString, 0, arrayOfString2, i, paramArrayOfString.length);
      localIntent.putExtra(paramString, arrayOfString2);
    }
    
    public static IntentBuilder from(Activity paramActivity)
    {
      return from((Context)Preconditions.checkNotNull(paramActivity), paramActivity.getComponentName());
    }
    
    private static IntentBuilder from(Context paramContext, ComponentName paramComponentName)
    {
      return new IntentBuilder(paramContext, paramComponentName);
    }
    
    public IntentBuilder addEmailBcc(String paramString)
    {
      if (this.mBccAddresses == null) {
        this.mBccAddresses = new ArrayList();
      }
      this.mBccAddresses.add(paramString);
      return this;
    }
    
    public IntentBuilder addEmailBcc(String[] paramArrayOfString)
    {
      combineArrayExtra("android.intent.extra.BCC", paramArrayOfString);
      return this;
    }
    
    public IntentBuilder addEmailCc(String paramString)
    {
      if (this.mCcAddresses == null) {
        this.mCcAddresses = new ArrayList();
      }
      this.mCcAddresses.add(paramString);
      return this;
    }
    
    public IntentBuilder addEmailCc(String[] paramArrayOfString)
    {
      combineArrayExtra("android.intent.extra.CC", paramArrayOfString);
      return this;
    }
    
    public IntentBuilder addEmailTo(String paramString)
    {
      if (this.mToAddresses == null) {
        this.mToAddresses = new ArrayList();
      }
      this.mToAddresses.add(paramString);
      return this;
    }
    
    public IntentBuilder addEmailTo(String[] paramArrayOfString)
    {
      combineArrayExtra("android.intent.extra.EMAIL", paramArrayOfString);
      return this;
    }
    
    public IntentBuilder addStream(Uri paramUri)
    {
      Uri localUri = (Uri)this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
      if ((this.mStreams == null) && (localUri == null)) {
        return setStream(paramUri);
      }
      if (this.mStreams == null) {
        this.mStreams = new ArrayList();
      }
      if (localUri != null)
      {
        this.mIntent.removeExtra("android.intent.extra.STREAM");
        this.mStreams.add(localUri);
      }
      this.mStreams.add(paramUri);
      return this;
    }
    
    public Intent createChooserIntent()
    {
      return Intent.createChooser(getIntent(), this.mChooserTitle);
    }
    
    Context getContext()
    {
      return this.mContext;
    }
    
    public Intent getIntent()
    {
      ArrayList localArrayList = this.mToAddresses;
      if (localArrayList != null)
      {
        combineArrayExtra("android.intent.extra.EMAIL", localArrayList);
        this.mToAddresses = null;
      }
      localArrayList = this.mCcAddresses;
      if (localArrayList != null)
      {
        combineArrayExtra("android.intent.extra.CC", localArrayList);
        this.mCcAddresses = null;
      }
      localArrayList = this.mBccAddresses;
      if (localArrayList != null)
      {
        combineArrayExtra("android.intent.extra.BCC", localArrayList);
        this.mBccAddresses = null;
      }
      localArrayList = this.mStreams;
      int i = 1;
      if ((localArrayList == null) || (localArrayList.size() <= 1)) {
        i = 0;
      }
      boolean bool = "android.intent.action.SEND_MULTIPLE".equals(this.mIntent.getAction());
      if ((i == 0) && (bool))
      {
        this.mIntent.setAction("android.intent.action.SEND");
        localArrayList = this.mStreams;
        if ((localArrayList != null) && (!localArrayList.isEmpty())) {
          this.mIntent.putExtra("android.intent.extra.STREAM", (Parcelable)this.mStreams.get(0));
        } else {
          this.mIntent.removeExtra("android.intent.extra.STREAM");
        }
        this.mStreams = null;
      }
      if ((i != 0) && (!bool))
      {
        this.mIntent.setAction("android.intent.action.SEND_MULTIPLE");
        localArrayList = this.mStreams;
        if ((localArrayList != null) && (!localArrayList.isEmpty())) {
          this.mIntent.putParcelableArrayListExtra("android.intent.extra.STREAM", this.mStreams);
        } else {
          this.mIntent.removeExtra("android.intent.extra.STREAM");
        }
      }
      return this.mIntent;
    }
    
    public IntentBuilder setChooserTitle(int paramInt)
    {
      return setChooserTitle(this.mContext.getText(paramInt));
    }
    
    public IntentBuilder setChooserTitle(CharSequence paramCharSequence)
    {
      this.mChooserTitle = paramCharSequence;
      return this;
    }
    
    public IntentBuilder setEmailBcc(String[] paramArrayOfString)
    {
      this.mIntent.putExtra("android.intent.extra.BCC", paramArrayOfString);
      return this;
    }
    
    public IntentBuilder setEmailCc(String[] paramArrayOfString)
    {
      this.mIntent.putExtra("android.intent.extra.CC", paramArrayOfString);
      return this;
    }
    
    public IntentBuilder setEmailTo(String[] paramArrayOfString)
    {
      if (this.mToAddresses != null) {
        this.mToAddresses = null;
      }
      this.mIntent.putExtra("android.intent.extra.EMAIL", paramArrayOfString);
      return this;
    }
    
    public IntentBuilder setHtmlText(String paramString)
    {
      this.mIntent.putExtra("android.intent.extra.HTML_TEXT", paramString);
      if (!this.mIntent.hasExtra("android.intent.extra.TEXT")) {
        setText(Html.fromHtml(paramString));
      }
      return this;
    }
    
    public IntentBuilder setStream(Uri paramUri)
    {
      if (!"android.intent.action.SEND".equals(this.mIntent.getAction())) {
        this.mIntent.setAction("android.intent.action.SEND");
      }
      this.mStreams = null;
      this.mIntent.putExtra("android.intent.extra.STREAM", paramUri);
      return this;
    }
    
    public IntentBuilder setSubject(String paramString)
    {
      this.mIntent.putExtra("android.intent.extra.SUBJECT", paramString);
      return this;
    }
    
    public IntentBuilder setText(CharSequence paramCharSequence)
    {
      this.mIntent.putExtra("android.intent.extra.TEXT", paramCharSequence);
      return this;
    }
    
    public IntentBuilder setType(String paramString)
    {
      this.mIntent.setType(paramString);
      return this;
    }
    
    public void startChooser()
    {
      this.mContext.startActivity(createChooserIntent());
    }
  }
  
  public static class IntentReader
  {
    private static final String TAG = "IntentReader";
    private final ComponentName mCallingActivity;
    private final String mCallingPackage;
    private final Context mContext;
    private final Intent mIntent;
    private ArrayList<Uri> mStreams;
    
    private IntentReader(Context paramContext, Intent paramIntent)
    {
      this.mContext = ((Context)Preconditions.checkNotNull(paramContext));
      this.mIntent = ((Intent)Preconditions.checkNotNull(paramIntent));
      this.mCallingPackage = ShareCompat.getCallingPackage(paramIntent);
      this.mCallingActivity = ShareCompat.getCallingActivity(paramIntent);
    }
    
    public static IntentReader from(Activity paramActivity)
    {
      return from((Context)Preconditions.checkNotNull(paramActivity), paramActivity.getIntent());
    }
    
    private static IntentReader from(Context paramContext, Intent paramIntent)
    {
      return new IntentReader(paramContext, paramIntent);
    }
    
    private static void withinStyle(StringBuilder paramStringBuilder, CharSequence paramCharSequence, int paramInt1, int paramInt2)
    {
      while (paramInt1 < paramInt2)
      {
        char c = paramCharSequence.charAt(paramInt1);
        if (c == '<')
        {
          paramStringBuilder.append("&lt;");
        }
        else if (c == '>')
        {
          paramStringBuilder.append("&gt;");
        }
        else if (c == '&')
        {
          paramStringBuilder.append("&amp;");
        }
        else if ((c <= '~') && (c >= ' '))
        {
          if (c == ' ')
          {
            for (;;)
            {
              int i = paramInt1 + 1;
              if ((i >= paramInt2) || (paramCharSequence.charAt(i) != ' ')) {
                break;
              }
              paramStringBuilder.append("&nbsp;");
              paramInt1 = i;
            }
            paramStringBuilder.append(' ');
          }
          else
          {
            paramStringBuilder.append(c);
          }
        }
        else
        {
          paramStringBuilder.append("&#");
          paramStringBuilder.append(c);
          paramStringBuilder.append(";");
        }
        paramInt1++;
      }
    }
    
    public ComponentName getCallingActivity()
    {
      return this.mCallingActivity;
    }
    
    public Drawable getCallingActivityIcon()
    {
      if (this.mCallingActivity == null) {
        return null;
      }
      Object localObject = this.mContext.getPackageManager();
      try
      {
        localObject = ((PackageManager)localObject).getActivityIcon(this.mCallingActivity);
        return localObject;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        Log.e("IntentReader", "Could not retrieve icon for calling activity", localNameNotFoundException);
      }
      return null;
    }
    
    public Drawable getCallingApplicationIcon()
    {
      if (this.mCallingPackage == null) {
        return null;
      }
      Object localObject = this.mContext.getPackageManager();
      try
      {
        localObject = ((PackageManager)localObject).getApplicationIcon(this.mCallingPackage);
        return localObject;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        Log.e("IntentReader", "Could not retrieve icon for calling application", localNameNotFoundException);
      }
      return null;
    }
    
    public CharSequence getCallingApplicationLabel()
    {
      if (this.mCallingPackage == null) {
        return null;
      }
      Object localObject = this.mContext.getPackageManager();
      try
      {
        localObject = ((PackageManager)localObject).getApplicationLabel(((PackageManager)localObject).getApplicationInfo(this.mCallingPackage, 0));
        return localObject;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        Log.e("IntentReader", "Could not retrieve label for calling application", localNameNotFoundException);
      }
      return null;
    }
    
    public String getCallingPackage()
    {
      return this.mCallingPackage;
    }
    
    public String[] getEmailBcc()
    {
      return this.mIntent.getStringArrayExtra("android.intent.extra.BCC");
    }
    
    public String[] getEmailCc()
    {
      return this.mIntent.getStringArrayExtra("android.intent.extra.CC");
    }
    
    public String[] getEmailTo()
    {
      return this.mIntent.getStringArrayExtra("android.intent.extra.EMAIL");
    }
    
    public String getHtmlText()
    {
      String str = this.mIntent.getStringExtra("android.intent.extra.HTML_TEXT");
      Object localObject = str;
      if (str == null)
      {
        CharSequence localCharSequence = getText();
        if ((localCharSequence instanceof Spanned))
        {
          localObject = Html.toHtml((Spanned)localCharSequence);
        }
        else
        {
          localObject = str;
          if (localCharSequence != null) {
            if (Build.VERSION.SDK_INT >= 16)
            {
              localObject = Html.escapeHtml(localCharSequence);
            }
            else
            {
              localObject = new StringBuilder();
              withinStyle((StringBuilder)localObject, localCharSequence, 0, localCharSequence.length());
              localObject = ((StringBuilder)localObject).toString();
            }
          }
        }
      }
      return localObject;
    }
    
    public Uri getStream()
    {
      return (Uri)this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
    }
    
    public Uri getStream(int paramInt)
    {
      if ((this.mStreams == null) && (isMultipleShare())) {
        this.mStreams = this.mIntent.getParcelableArrayListExtra("android.intent.extra.STREAM");
      }
      Object localObject = this.mStreams;
      if (localObject != null) {
        return (Uri)((ArrayList)localObject).get(paramInt);
      }
      if (paramInt == 0) {
        return (Uri)this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Stream items available: ");
      ((StringBuilder)localObject).append(getStreamCount());
      ((StringBuilder)localObject).append(" index requested: ");
      ((StringBuilder)localObject).append(paramInt);
      throw new IndexOutOfBoundsException(((StringBuilder)localObject).toString());
    }
    
    public int getStreamCount()
    {
      if ((this.mStreams == null) && (isMultipleShare())) {
        this.mStreams = this.mIntent.getParcelableArrayListExtra("android.intent.extra.STREAM");
      }
      ArrayList localArrayList = this.mStreams;
      if (localArrayList != null) {
        return localArrayList.size();
      }
      return this.mIntent.hasExtra("android.intent.extra.STREAM");
    }
    
    public String getSubject()
    {
      return this.mIntent.getStringExtra("android.intent.extra.SUBJECT");
    }
    
    public CharSequence getText()
    {
      return this.mIntent.getCharSequenceExtra("android.intent.extra.TEXT");
    }
    
    public String getType()
    {
      return this.mIntent.getType();
    }
    
    public boolean isMultipleShare()
    {
      return "android.intent.action.SEND_MULTIPLE".equals(this.mIntent.getAction());
    }
    
    public boolean isShareIntent()
    {
      String str = this.mIntent.getAction();
      boolean bool;
      if ((!"android.intent.action.SEND".equals(str)) && (!"android.intent.action.SEND_MULTIPLE".equals(str))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public boolean isSingleShare()
    {
      return "android.intent.action.SEND".equals(this.mIntent.getAction());
    }
  }
}

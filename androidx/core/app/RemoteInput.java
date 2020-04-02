package androidx.core.app;

import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipDescription;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class RemoteInput
{
  public static final int EDIT_CHOICES_BEFORE_SENDING_AUTO = 0;
  public static final int EDIT_CHOICES_BEFORE_SENDING_DISABLED = 1;
  public static final int EDIT_CHOICES_BEFORE_SENDING_ENABLED = 2;
  private static final String EXTRA_DATA_TYPE_RESULTS_DATA = "android.remoteinput.dataTypeResultsData";
  public static final String EXTRA_RESULTS_DATA = "android.remoteinput.resultsData";
  private static final String EXTRA_RESULTS_SOURCE = "android.remoteinput.resultsSource";
  public static final String RESULTS_CLIP_LABEL = "android.remoteinput.results";
  public static final int SOURCE_CHOICE = 1;
  public static final int SOURCE_FREE_FORM_INPUT = 0;
  private static final String TAG = "RemoteInput";
  private final boolean mAllowFreeFormTextInput;
  private final Set<String> mAllowedDataTypes;
  private final CharSequence[] mChoices;
  private final int mEditChoicesBeforeSending;
  private final Bundle mExtras;
  private final CharSequence mLabel;
  private final String mResultKey;
  
  RemoteInput(String paramString, CharSequence paramCharSequence, CharSequence[] paramArrayOfCharSequence, boolean paramBoolean, int paramInt, Bundle paramBundle, Set<String> paramSet)
  {
    this.mResultKey = paramString;
    this.mLabel = paramCharSequence;
    this.mChoices = paramArrayOfCharSequence;
    this.mAllowFreeFormTextInput = paramBoolean;
    this.mEditChoicesBeforeSending = paramInt;
    this.mExtras = paramBundle;
    this.mAllowedDataTypes = paramSet;
    if ((getEditChoicesBeforeSending() == 2) && (!getAllowFreeFormInput())) {
      throw new IllegalArgumentException("setEditChoicesBeforeSending requires setAllowFreeFormInput");
    }
  }
  
  public static void addDataResultToIntent(RemoteInput paramRemoteInput, Intent paramIntent, Map<String, Uri> paramMap)
  {
    if (Build.VERSION.SDK_INT >= 26)
    {
      android.app.RemoteInput.addDataResultToIntent(fromCompat(paramRemoteInput), paramIntent, paramMap);
    }
    else if (Build.VERSION.SDK_INT >= 16)
    {
      Object localObject1 = getClipDataIntentFromIntent(paramIntent);
      Object localObject2 = localObject1;
      if (localObject1 == null) {
        localObject2 = new Intent();
      }
      Iterator localIterator = paramMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        paramMap = (Map.Entry)localIterator.next();
        String str = (String)paramMap.getKey();
        Uri localUri = (Uri)paramMap.getValue();
        if (str != null)
        {
          localObject1 = ((Intent)localObject2).getBundleExtra(getExtraResultsKeyForData(str));
          paramMap = (Map<String, Uri>)localObject1;
          if (localObject1 == null) {
            paramMap = new Bundle();
          }
          paramMap.putString(paramRemoteInput.getResultKey(), localUri.toString());
          ((Intent)localObject2).putExtra(getExtraResultsKeyForData(str), paramMap);
        }
      }
      paramIntent.setClipData(ClipData.newIntent("android.remoteinput.results", (Intent)localObject2));
    }
  }
  
  public static void addResultsToIntent(RemoteInput[] paramArrayOfRemoteInput, Intent paramIntent, Bundle paramBundle)
  {
    if (Build.VERSION.SDK_INT >= 26)
    {
      android.app.RemoteInput.addResultsToIntent(fromCompat(paramArrayOfRemoteInput), paramIntent, paramBundle);
    }
    else
    {
      int i = Build.VERSION.SDK_INT;
      int j = 0;
      Object localObject1;
      Object localObject2;
      if (i >= 20)
      {
        localObject1 = getResultsFromIntent(paramIntent);
        int k = getResultsSource(paramIntent);
        if (localObject1 != null)
        {
          ((Bundle)localObject1).putAll(paramBundle);
          paramBundle = (Bundle)localObject1;
        }
        i = paramArrayOfRemoteInput.length;
        for (j = 0; j < i; j++)
        {
          localObject1 = paramArrayOfRemoteInput[j];
          localObject2 = getDataResultsFromIntent(paramIntent, ((RemoteInput)localObject1).getResultKey());
          android.app.RemoteInput.addResultsToIntent(fromCompat(new RemoteInput[] { localObject1 }), paramIntent, paramBundle);
          if (localObject2 != null) {
            addDataResultToIntent((RemoteInput)localObject1, paramIntent, (Map)localObject2);
          }
        }
        setResultsSource(paramIntent, k);
      }
      else if (Build.VERSION.SDK_INT >= 16)
      {
        localObject2 = getClipDataIntentFromIntent(paramIntent);
        localObject1 = localObject2;
        if (localObject2 == null) {
          localObject1 = new Intent();
        }
        Object localObject3 = ((Intent)localObject1).getBundleExtra("android.remoteinput.resultsData");
        localObject2 = localObject3;
        if (localObject3 == null) {
          localObject2 = new Bundle();
        }
        i = paramArrayOfRemoteInput.length;
        while (j < i)
        {
          localObject3 = paramArrayOfRemoteInput[j];
          Object localObject4 = paramBundle.get(((RemoteInput)localObject3).getResultKey());
          if ((localObject4 instanceof CharSequence)) {
            ((Bundle)localObject2).putCharSequence(((RemoteInput)localObject3).getResultKey(), (CharSequence)localObject4);
          }
          j++;
        }
        ((Intent)localObject1).putExtra("android.remoteinput.resultsData", (Bundle)localObject2);
        paramIntent.setClipData(ClipData.newIntent("android.remoteinput.results", (Intent)localObject1));
      }
    }
  }
  
  static android.app.RemoteInput fromCompat(RemoteInput paramRemoteInput)
  {
    android.app.RemoteInput.Builder localBuilder = new android.app.RemoteInput.Builder(paramRemoteInput.getResultKey()).setLabel(paramRemoteInput.getLabel()).setChoices(paramRemoteInput.getChoices()).setAllowFreeFormInput(paramRemoteInput.getAllowFreeFormInput()).addExtras(paramRemoteInput.getExtras());
    if (Build.VERSION.SDK_INT >= 29) {
      localBuilder.setEditChoicesBeforeSending(paramRemoteInput.getEditChoicesBeforeSending());
    }
    return localBuilder.build();
  }
  
  static android.app.RemoteInput[] fromCompat(RemoteInput[] paramArrayOfRemoteInput)
  {
    if (paramArrayOfRemoteInput == null) {
      return null;
    }
    android.app.RemoteInput[] arrayOfRemoteInput = new android.app.RemoteInput[paramArrayOfRemoteInput.length];
    for (int i = 0; i < paramArrayOfRemoteInput.length; i++) {
      arrayOfRemoteInput[i] = fromCompat(paramArrayOfRemoteInput[i]);
    }
    return arrayOfRemoteInput;
  }
  
  private static Intent getClipDataIntentFromIntent(Intent paramIntent)
  {
    ClipData localClipData = paramIntent.getClipData();
    if (localClipData == null) {
      return null;
    }
    paramIntent = localClipData.getDescription();
    if (!paramIntent.hasMimeType("text/vnd.android.intent")) {
      return null;
    }
    if (!paramIntent.getLabel().toString().contentEquals("android.remoteinput.results")) {
      return null;
    }
    return localClipData.getItemAt(0).getIntent();
  }
  
  public static Map<String, Uri> getDataResultsFromIntent(Intent paramIntent, String paramString)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return android.app.RemoteInput.getDataResultsFromIntent(paramIntent, paramString);
    }
    int i = Build.VERSION.SDK_INT;
    Object localObject1 = null;
    Object localObject2 = localObject1;
    if (i >= 16)
    {
      Intent localIntent = getClipDataIntentFromIntent(paramIntent);
      if (localIntent == null) {
        return null;
      }
      localObject2 = new HashMap();
      Iterator localIterator = localIntent.getExtras().keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        if (str.startsWith("android.remoteinput.dataTypeResultsData"))
        {
          paramIntent = str.substring(39);
          if (!paramIntent.isEmpty())
          {
            str = localIntent.getBundleExtra(str).getString(paramString);
            if ((str != null) && (!str.isEmpty())) {
              ((Map)localObject2).put(paramIntent, Uri.parse(str));
            }
          }
        }
      }
      if (((Map)localObject2).isEmpty()) {
        localObject2 = localObject1;
      }
    }
    return localObject2;
  }
  
  private static String getExtraResultsKeyForData(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("android.remoteinput.dataTypeResultsData");
    localStringBuilder.append(paramString);
    return localStringBuilder.toString();
  }
  
  public static Bundle getResultsFromIntent(Intent paramIntent)
  {
    if (Build.VERSION.SDK_INT >= 20) {
      return android.app.RemoteInput.getResultsFromIntent(paramIntent);
    }
    if (Build.VERSION.SDK_INT >= 16)
    {
      paramIntent = getClipDataIntentFromIntent(paramIntent);
      if (paramIntent == null) {
        return null;
      }
      return (Bundle)paramIntent.getExtras().getParcelable("android.remoteinput.resultsData");
    }
    return null;
  }
  
  public static int getResultsSource(Intent paramIntent)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return android.app.RemoteInput.getResultsSource(paramIntent);
    }
    if (Build.VERSION.SDK_INT >= 16)
    {
      paramIntent = getClipDataIntentFromIntent(paramIntent);
      if (paramIntent == null) {
        return 0;
      }
      return paramIntent.getExtras().getInt("android.remoteinput.resultsSource", 0);
    }
    return 0;
  }
  
  public static void setResultsSource(Intent paramIntent, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 28)
    {
      android.app.RemoteInput.setResultsSource(paramIntent, paramInt);
    }
    else if (Build.VERSION.SDK_INT >= 16)
    {
      Intent localIntent1 = getClipDataIntentFromIntent(paramIntent);
      Intent localIntent2 = localIntent1;
      if (localIntent1 == null) {
        localIntent2 = new Intent();
      }
      localIntent2.putExtra("android.remoteinput.resultsSource", paramInt);
      paramIntent.setClipData(ClipData.newIntent("android.remoteinput.results", localIntent2));
    }
  }
  
  public boolean getAllowFreeFormInput()
  {
    return this.mAllowFreeFormTextInput;
  }
  
  public Set<String> getAllowedDataTypes()
  {
    return this.mAllowedDataTypes;
  }
  
  public CharSequence[] getChoices()
  {
    return this.mChoices;
  }
  
  public int getEditChoicesBeforeSending()
  {
    return this.mEditChoicesBeforeSending;
  }
  
  public Bundle getExtras()
  {
    return this.mExtras;
  }
  
  public CharSequence getLabel()
  {
    return this.mLabel;
  }
  
  public String getResultKey()
  {
    return this.mResultKey;
  }
  
  public boolean isDataOnly()
  {
    boolean bool;
    if ((!getAllowFreeFormInput()) && ((getChoices() == null) || (getChoices().length == 0)) && (getAllowedDataTypes() != null) && (!getAllowedDataTypes().isEmpty())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final class Builder
  {
    private boolean mAllowFreeFormTextInput = true;
    private final Set<String> mAllowedDataTypes = new HashSet();
    private CharSequence[] mChoices;
    private int mEditChoicesBeforeSending = 0;
    private final Bundle mExtras = new Bundle();
    private CharSequence mLabel;
    private final String mResultKey;
    
    public Builder(String paramString)
    {
      if (paramString != null)
      {
        this.mResultKey = paramString;
        return;
      }
      throw new IllegalArgumentException("Result key can't be null");
    }
    
    public Builder addExtras(Bundle paramBundle)
    {
      if (paramBundle != null) {
        this.mExtras.putAll(paramBundle);
      }
      return this;
    }
    
    public RemoteInput build()
    {
      return new RemoteInput(this.mResultKey, this.mLabel, this.mChoices, this.mAllowFreeFormTextInput, this.mEditChoicesBeforeSending, this.mExtras, this.mAllowedDataTypes);
    }
    
    public Bundle getExtras()
    {
      return this.mExtras;
    }
    
    public Builder setAllowDataType(String paramString, boolean paramBoolean)
    {
      if (paramBoolean) {
        this.mAllowedDataTypes.add(paramString);
      } else {
        this.mAllowedDataTypes.remove(paramString);
      }
      return this;
    }
    
    public Builder setAllowFreeFormInput(boolean paramBoolean)
    {
      this.mAllowFreeFormTextInput = paramBoolean;
      return this;
    }
    
    public Builder setChoices(CharSequence[] paramArrayOfCharSequence)
    {
      this.mChoices = paramArrayOfCharSequence;
      return this;
    }
    
    public Builder setEditChoicesBeforeSending(int paramInt)
    {
      this.mEditChoicesBeforeSending = paramInt;
      return this;
    }
    
    public Builder setLabel(CharSequence paramCharSequence)
    {
      this.mLabel = paramCharSequence;
      return this;
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface EditChoicesBeforeSending {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Source {}
}

package androidx.fragment.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater.Factory2;
import android.view.View;
import androidx.fragment.R.styleable;

class FragmentLayoutInflaterFactory
  implements LayoutInflater.Factory2
{
  private static final String TAG = "FragmentManager";
  private final FragmentManager mFragmentManager;
  
  FragmentLayoutInflaterFactory(FragmentManager paramFragmentManager)
  {
    this.mFragmentManager = paramFragmentManager;
  }
  
  public View onCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    if (FragmentContainerView.class.getName().equals(paramString)) {
      return new FragmentContainerView(paramContext, paramAttributeSet, this.mFragmentManager);
    }
    boolean bool = "fragment".equals(paramString);
    paramString = null;
    if (!bool) {
      return null;
    }
    String str1 = paramAttributeSet.getAttributeValue(null, "class");
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.Fragment);
    String str2 = str1;
    if (str1 == null) {
      str2 = localTypedArray.getString(R.styleable.Fragment_android_name);
    }
    int i = localTypedArray.getResourceId(R.styleable.Fragment_android_id, -1);
    str1 = localTypedArray.getString(R.styleable.Fragment_android_tag);
    localTypedArray.recycle();
    if ((str2 != null) && (FragmentFactory.isFragmentClass(paramContext.getClassLoader(), str2)))
    {
      int j;
      if (paramView != null) {
        j = paramView.getId();
      } else {
        j = 0;
      }
      if ((j == -1) && (i == -1) && (str1 == null))
      {
        paramView = new StringBuilder();
        paramView.append(paramAttributeSet.getPositionDescription());
        paramView.append(": Must specify unique android:id, android:tag, or have a parent with an id for ");
        paramView.append(str2);
        throw new IllegalArgumentException(paramView.toString());
      }
      if (i != -1) {
        paramString = this.mFragmentManager.findFragmentById(i);
      }
      paramView = paramString;
      if (paramString == null)
      {
        paramView = paramString;
        if (str1 != null) {
          paramView = this.mFragmentManager.findFragmentByTag(str1);
        }
      }
      paramString = paramView;
      if (paramView == null)
      {
        paramString = paramView;
        if (j != -1) {
          paramString = this.mFragmentManager.findFragmentById(j);
        }
      }
      if (FragmentManager.isLoggingEnabled(2))
      {
        paramView = new StringBuilder();
        paramView.append("onCreateView: id=0x");
        paramView.append(Integer.toHexString(i));
        paramView.append(" fname=");
        paramView.append(str2);
        paramView.append(" existing=");
        paramView.append(paramString);
        Log.v("FragmentManager", paramView.toString());
      }
      if (paramString == null)
      {
        paramString = this.mFragmentManager.getFragmentFactory().instantiate(paramContext.getClassLoader(), str2);
        paramString.mFromLayout = true;
        int k;
        if (i != 0) {
          k = i;
        } else {
          k = j;
        }
        paramString.mFragmentId = k;
        paramString.mContainerId = j;
        paramString.mTag = str1;
        paramString.mInLayout = true;
        paramString.mFragmentManager = this.mFragmentManager;
        paramString.mHost = this.mFragmentManager.mHost;
        paramString.onInflate(this.mFragmentManager.mHost.getContext(), paramAttributeSet, paramString.mSavedFragmentState);
        this.mFragmentManager.addFragment(paramString);
        this.mFragmentManager.moveToState(paramString);
      }
      else
      {
        if (paramString.mInLayout) {
          break label636;
        }
        paramString.mInLayout = true;
        paramString.mHost = this.mFragmentManager.mHost;
        paramString.onInflate(this.mFragmentManager.mHost.getContext(), paramAttributeSet, paramString.mSavedFragmentState);
      }
      if ((this.mFragmentManager.mCurState < 1) && (paramString.mFromLayout)) {
        this.mFragmentManager.moveToState(paramString, 1);
      } else {
        this.mFragmentManager.moveToState(paramString);
      }
      if (paramString.mView != null)
      {
        if (i != 0) {
          paramString.mView.setId(i);
        }
        if (paramString.mView.getTag() == null) {
          paramString.mView.setTag(str1);
        }
        return paramString.mView;
      }
      paramView = new StringBuilder();
      paramView.append("Fragment ");
      paramView.append(str2);
      paramView.append(" did not create a view.");
      throw new IllegalStateException(paramView.toString());
      label636:
      paramView = new StringBuilder();
      paramView.append(paramAttributeSet.getPositionDescription());
      paramView.append(": Duplicate id 0x");
      paramView.append(Integer.toHexString(i));
      paramView.append(", tag ");
      paramView.append(str1);
      paramView.append(", or parent id 0x");
      paramView.append(Integer.toHexString(j));
      paramView.append(" with another fragment for ");
      paramView.append(str2);
      throw new IllegalArgumentException(paramView.toString());
    }
    return null;
  }
  
  public View onCreateView(String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    return onCreateView(null, paramString, paramContext, paramAttributeSet);
  }
}

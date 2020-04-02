package androidx.fragment.app;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ListFragment
  extends Fragment
{
  static final int INTERNAL_EMPTY_ID = 16711681;
  static final int INTERNAL_LIST_CONTAINER_ID = 16711683;
  static final int INTERNAL_PROGRESS_CONTAINER_ID = 16711682;
  ListAdapter mAdapter;
  CharSequence mEmptyText;
  View mEmptyView;
  private final Handler mHandler = new Handler();
  ListView mList;
  View mListContainer;
  boolean mListShown;
  private final AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener()
  {
    public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
    {
      ListFragment.this.onListItemClick((ListView)paramAnonymousAdapterView, paramAnonymousView, paramAnonymousInt, paramAnonymousLong);
    }
  };
  View mProgressContainer;
  private final Runnable mRequestFocus = new Runnable()
  {
    public void run()
    {
      ListFragment.this.mList.focusableViewAvailable(ListFragment.this.mList);
    }
  };
  TextView mStandardEmptyView;
  
  public ListFragment() {}
  
  private void ensureList()
  {
    if (this.mList != null) {
      return;
    }
    Object localObject1 = getView();
    if (localObject1 != null)
    {
      if ((localObject1 instanceof ListView))
      {
        this.mList = ((ListView)localObject1);
      }
      else
      {
        localObject2 = (TextView)((View)localObject1).findViewById(16711681);
        this.mStandardEmptyView = ((TextView)localObject2);
        if (localObject2 == null) {
          this.mEmptyView = ((View)localObject1).findViewById(16908292);
        } else {
          ((TextView)localObject2).setVisibility(8);
        }
        this.mProgressContainer = ((View)localObject1).findViewById(16711682);
        this.mListContainer = ((View)localObject1).findViewById(16711683);
        localObject2 = ((View)localObject1).findViewById(16908298);
        if (!(localObject2 instanceof ListView))
        {
          if (localObject2 == null) {
            throw new RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'");
          }
          throw new RuntimeException("Content has view with id attribute 'android.R.id.list' that is not a ListView class");
        }
        localObject1 = (ListView)localObject2;
        this.mList = ((ListView)localObject1);
        localObject2 = this.mEmptyView;
        if (localObject2 != null)
        {
          ((ListView)localObject1).setEmptyView((View)localObject2);
        }
        else
        {
          localObject2 = this.mEmptyText;
          if (localObject2 != null)
          {
            this.mStandardEmptyView.setText((CharSequence)localObject2);
            this.mList.setEmptyView(this.mStandardEmptyView);
          }
        }
      }
      this.mListShown = true;
      this.mList.setOnItemClickListener(this.mOnClickListener);
      Object localObject2 = this.mAdapter;
      if (localObject2 != null)
      {
        this.mAdapter = null;
        setListAdapter((ListAdapter)localObject2);
      }
      else if (this.mProgressContainer != null)
      {
        setListShown(false, false);
      }
      this.mHandler.post(this.mRequestFocus);
      return;
    }
    throw new IllegalStateException("Content view not yet created");
  }
  
  private void setListShown(boolean paramBoolean1, boolean paramBoolean2)
  {
    ensureList();
    View localView = this.mProgressContainer;
    if (localView != null)
    {
      if (this.mListShown == paramBoolean1) {
        return;
      }
      this.mListShown = paramBoolean1;
      if (paramBoolean1)
      {
        if (paramBoolean2)
        {
          localView.startAnimation(AnimationUtils.loadAnimation(getContext(), 17432577));
          this.mListContainer.startAnimation(AnimationUtils.loadAnimation(getContext(), 17432576));
        }
        else
        {
          localView.clearAnimation();
          this.mListContainer.clearAnimation();
        }
        this.mProgressContainer.setVisibility(8);
        this.mListContainer.setVisibility(0);
      }
      else
      {
        if (paramBoolean2)
        {
          localView.startAnimation(AnimationUtils.loadAnimation(getContext(), 17432576));
          this.mListContainer.startAnimation(AnimationUtils.loadAnimation(getContext(), 17432577));
        }
        else
        {
          localView.clearAnimation();
          this.mListContainer.clearAnimation();
        }
        this.mProgressContainer.setVisibility(0);
        this.mListContainer.setVisibility(8);
      }
      return;
    }
    throw new IllegalStateException("Can't be used with a custom content view");
  }
  
  public ListAdapter getListAdapter()
  {
    return this.mAdapter;
  }
  
  public ListView getListView()
  {
    ensureList();
    return this.mList;
  }
  
  public long getSelectedItemId()
  {
    ensureList();
    return this.mList.getSelectedItemId();
  }
  
  public int getSelectedItemPosition()
  {
    ensureList();
    return this.mList.getSelectedItemPosition();
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramBundle = requireContext();
    paramLayoutInflater = new FrameLayout(paramBundle);
    paramViewGroup = new LinearLayout(paramBundle);
    paramViewGroup.setId(16711682);
    paramViewGroup.setOrientation(1);
    paramViewGroup.setVisibility(8);
    paramViewGroup.setGravity(17);
    paramViewGroup.addView(new ProgressBar(paramBundle, null, 16842874), new FrameLayout.LayoutParams(-2, -2));
    paramLayoutInflater.addView(paramViewGroup, new FrameLayout.LayoutParams(-1, -1));
    paramViewGroup = new FrameLayout(paramBundle);
    paramViewGroup.setId(16711683);
    TextView localTextView = new TextView(paramBundle);
    localTextView.setId(16711681);
    localTextView.setGravity(17);
    paramViewGroup.addView(localTextView, new FrameLayout.LayoutParams(-1, -1));
    paramBundle = new ListView(paramBundle);
    paramBundle.setId(16908298);
    paramBundle.setDrawSelectorOnTop(false);
    paramViewGroup.addView(paramBundle, new FrameLayout.LayoutParams(-1, -1));
    paramLayoutInflater.addView(paramViewGroup, new FrameLayout.LayoutParams(-1, -1));
    paramLayoutInflater.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    this.mHandler.removeCallbacks(this.mRequestFocus);
    this.mList = null;
    this.mListShown = false;
    this.mListContainer = null;
    this.mProgressContainer = null;
    this.mEmptyView = null;
    this.mStandardEmptyView = null;
    super.onDestroyView();
  }
  
  public void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong) {}
  
  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    ensureList();
  }
  
  public final ListAdapter requireListAdapter()
  {
    Object localObject = getListAdapter();
    if (localObject != null) {
      return localObject;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("ListFragment ");
    ((StringBuilder)localObject).append(this);
    ((StringBuilder)localObject).append(" does not have a ListAdapter.");
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public void setEmptyText(CharSequence paramCharSequence)
  {
    ensureList();
    TextView localTextView = this.mStandardEmptyView;
    if (localTextView != null)
    {
      localTextView.setText(paramCharSequence);
      if (this.mEmptyText == null) {
        this.mList.setEmptyView(this.mStandardEmptyView);
      }
      this.mEmptyText = paramCharSequence;
      return;
    }
    throw new IllegalStateException("Can't be used with a custom content view");
  }
  
  public void setListAdapter(ListAdapter paramListAdapter)
  {
    Object localObject = this.mAdapter;
    boolean bool = false;
    int i;
    if (localObject != null) {
      i = 1;
    } else {
      i = 0;
    }
    this.mAdapter = paramListAdapter;
    localObject = this.mList;
    if (localObject != null)
    {
      ((ListView)localObject).setAdapter(paramListAdapter);
      if ((!this.mListShown) && (i == 0))
      {
        if (requireView().getWindowToken() != null) {
          bool = true;
        }
        setListShown(true, bool);
      }
    }
  }
  
  public void setListShown(boolean paramBoolean)
  {
    setListShown(paramBoolean, true);
  }
  
  public void setListShownNoAnimation(boolean paramBoolean)
  {
    setListShown(paramBoolean, false);
  }
  
  public void setSelection(int paramInt)
  {
    ensureList();
    this.mList.setSelection(paramInt);
  }
}

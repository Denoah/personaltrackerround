package androidx.fragment.app;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import androidx.lifecycle.Lifecycle.State;

final class FragmentState
  implements Parcelable
{
  public static final Parcelable.Creator<FragmentState> CREATOR = new Parcelable.Creator()
  {
    public FragmentState createFromParcel(Parcel paramAnonymousParcel)
    {
      return new FragmentState(paramAnonymousParcel);
    }
    
    public FragmentState[] newArray(int paramAnonymousInt)
    {
      return new FragmentState[paramAnonymousInt];
    }
  };
  final Bundle mArguments;
  final String mClassName;
  final int mContainerId;
  final boolean mDetached;
  final int mFragmentId;
  final boolean mFromLayout;
  final boolean mHidden;
  final int mMaxLifecycleState;
  final boolean mRemoving;
  final boolean mRetainInstance;
  Bundle mSavedFragmentState;
  final String mTag;
  final String mWho;
  
  FragmentState(Parcel paramParcel)
  {
    this.mClassName = paramParcel.readString();
    this.mWho = paramParcel.readString();
    int i = paramParcel.readInt();
    boolean bool1 = true;
    boolean bool2;
    if (i != 0) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    this.mFromLayout = bool2;
    this.mFragmentId = paramParcel.readInt();
    this.mContainerId = paramParcel.readInt();
    this.mTag = paramParcel.readString();
    if (paramParcel.readInt() != 0) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    this.mRetainInstance = bool2;
    if (paramParcel.readInt() != 0) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    this.mRemoving = bool2;
    if (paramParcel.readInt() != 0) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    this.mDetached = bool2;
    this.mArguments = paramParcel.readBundle();
    if (paramParcel.readInt() != 0) {
      bool2 = bool1;
    } else {
      bool2 = false;
    }
    this.mHidden = bool2;
    this.mSavedFragmentState = paramParcel.readBundle();
    this.mMaxLifecycleState = paramParcel.readInt();
  }
  
  FragmentState(Fragment paramFragment)
  {
    this.mClassName = paramFragment.getClass().getName();
    this.mWho = paramFragment.mWho;
    this.mFromLayout = paramFragment.mFromLayout;
    this.mFragmentId = paramFragment.mFragmentId;
    this.mContainerId = paramFragment.mContainerId;
    this.mTag = paramFragment.mTag;
    this.mRetainInstance = paramFragment.mRetainInstance;
    this.mRemoving = paramFragment.mRemoving;
    this.mDetached = paramFragment.mDetached;
    this.mArguments = paramFragment.mArguments;
    this.mHidden = paramFragment.mHidden;
    this.mMaxLifecycleState = paramFragment.mMaxState.ordinal();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    localStringBuilder.append("FragmentState{");
    localStringBuilder.append(this.mClassName);
    localStringBuilder.append(" (");
    localStringBuilder.append(this.mWho);
    localStringBuilder.append(")}:");
    if (this.mFromLayout) {
      localStringBuilder.append(" fromLayout");
    }
    if (this.mContainerId != 0)
    {
      localStringBuilder.append(" id=0x");
      localStringBuilder.append(Integer.toHexString(this.mContainerId));
    }
    String str = this.mTag;
    if ((str != null) && (!str.isEmpty()))
    {
      localStringBuilder.append(" tag=");
      localStringBuilder.append(this.mTag);
    }
    if (this.mRetainInstance) {
      localStringBuilder.append(" retainInstance");
    }
    if (this.mRemoving) {
      localStringBuilder.append(" removing");
    }
    if (this.mDetached) {
      localStringBuilder.append(" detached");
    }
    if (this.mHidden) {
      localStringBuilder.append(" hidden");
    }
    return localStringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mClassName);
    paramParcel.writeString(this.mWho);
    paramParcel.writeInt(this.mFromLayout);
    paramParcel.writeInt(this.mFragmentId);
    paramParcel.writeInt(this.mContainerId);
    paramParcel.writeString(this.mTag);
    paramParcel.writeInt(this.mRetainInstance);
    paramParcel.writeInt(this.mRemoving);
    paramParcel.writeInt(this.mDetached);
    paramParcel.writeBundle(this.mArguments);
    paramParcel.writeInt(this.mHidden);
    paramParcel.writeBundle(this.mSavedFragmentState);
    paramParcel.writeInt(this.mMaxLifecycleState);
  }
}

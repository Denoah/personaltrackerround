package androidx.core.app;

import android.app.Notification;
import android.app.Notification.Action.Builder;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.widget.RemoteViews;
import androidx.core.graphics.drawable.IconCompat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class NotificationCompatBuilder
  implements NotificationBuilderWithBuilderAccessor
{
  private final List<Bundle> mActionExtrasList = new ArrayList();
  private RemoteViews mBigContentView;
  private final Notification.Builder mBuilder;
  private final NotificationCompat.Builder mBuilderCompat;
  private RemoteViews mContentView;
  private final Bundle mExtras = new Bundle();
  private int mGroupAlertBehavior;
  private RemoteViews mHeadsUpContentView;
  
  NotificationCompatBuilder(NotificationCompat.Builder paramBuilder)
  {
    this.mBuilderCompat = paramBuilder;
    if (Build.VERSION.SDK_INT >= 26) {
      this.mBuilder = new Notification.Builder(paramBuilder.mContext, paramBuilder.mChannelId);
    } else {
      this.mBuilder = new Notification.Builder(paramBuilder.mContext);
    }
    Object localObject1 = paramBuilder.mNotification;
    Object localObject2 = this.mBuilder.setWhen(((Notification)localObject1).when).setSmallIcon(((Notification)localObject1).icon, ((Notification)localObject1).iconLevel).setContent(((Notification)localObject1).contentView).setTicker(((Notification)localObject1).tickerText, paramBuilder.mTickerView).setVibrate(((Notification)localObject1).vibrate).setLights(((Notification)localObject1).ledARGB, ((Notification)localObject1).ledOnMS, ((Notification)localObject1).ledOffMS);
    boolean bool;
    if ((((Notification)localObject1).flags & 0x2) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    localObject2 = ((Notification.Builder)localObject2).setOngoing(bool);
    if ((((Notification)localObject1).flags & 0x8) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    localObject2 = ((Notification.Builder)localObject2).setOnlyAlertOnce(bool);
    if ((((Notification)localObject1).flags & 0x10) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    localObject2 = ((Notification.Builder)localObject2).setAutoCancel(bool).setDefaults(((Notification)localObject1).defaults).setContentTitle(paramBuilder.mContentTitle).setContentText(paramBuilder.mContentText).setContentInfo(paramBuilder.mContentInfo).setContentIntent(paramBuilder.mContentIntent).setDeleteIntent(((Notification)localObject1).deleteIntent);
    PendingIntent localPendingIntent = paramBuilder.mFullScreenIntent;
    if ((((Notification)localObject1).flags & 0x80) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    ((Notification.Builder)localObject2).setFullScreenIntent(localPendingIntent, bool).setLargeIcon(paramBuilder.mLargeIcon).setNumber(paramBuilder.mNumber).setProgress(paramBuilder.mProgressMax, paramBuilder.mProgress, paramBuilder.mProgressIndeterminate);
    if (Build.VERSION.SDK_INT < 21) {
      this.mBuilder.setSound(((Notification)localObject1).sound, ((Notification)localObject1).audioStreamType);
    }
    if (Build.VERSION.SDK_INT >= 16)
    {
      this.mBuilder.setSubText(paramBuilder.mSubText).setUsesChronometer(paramBuilder.mUseChronometer).setPriority(paramBuilder.mPriority);
      localObject2 = paramBuilder.mActions.iterator();
      while (((Iterator)localObject2).hasNext()) {
        addAction((NotificationCompat.Action)((Iterator)localObject2).next());
      }
      if (paramBuilder.mExtras != null) {
        this.mExtras.putAll(paramBuilder.mExtras);
      }
      if (Build.VERSION.SDK_INT < 20)
      {
        if (paramBuilder.mLocalOnly) {
          this.mExtras.putBoolean("android.support.localOnly", true);
        }
        if (paramBuilder.mGroupKey != null)
        {
          this.mExtras.putString("android.support.groupKey", paramBuilder.mGroupKey);
          if (paramBuilder.mGroupSummary) {
            this.mExtras.putBoolean("android.support.isGroupSummary", true);
          } else {
            this.mExtras.putBoolean("android.support.useSideChannel", true);
          }
        }
        if (paramBuilder.mSortKey != null) {
          this.mExtras.putString("android.support.sortKey", paramBuilder.mSortKey);
        }
      }
      this.mContentView = paramBuilder.mContentView;
      this.mBigContentView = paramBuilder.mBigContentView;
    }
    if (Build.VERSION.SDK_INT >= 19)
    {
      this.mBuilder.setShowWhen(paramBuilder.mShowWhen);
      if ((Build.VERSION.SDK_INT < 21) && (paramBuilder.mPeople != null) && (!paramBuilder.mPeople.isEmpty())) {
        this.mExtras.putStringArray("android.people", (String[])paramBuilder.mPeople.toArray(new String[paramBuilder.mPeople.size()]));
      }
    }
    if (Build.VERSION.SDK_INT >= 20)
    {
      this.mBuilder.setLocalOnly(paramBuilder.mLocalOnly).setGroup(paramBuilder.mGroupKey).setGroupSummary(paramBuilder.mGroupSummary).setSortKey(paramBuilder.mSortKey);
      this.mGroupAlertBehavior = paramBuilder.mGroupAlertBehavior;
    }
    if (Build.VERSION.SDK_INT >= 21)
    {
      this.mBuilder.setCategory(paramBuilder.mCategory).setColor(paramBuilder.mColor).setVisibility(paramBuilder.mVisibility).setPublicVersion(paramBuilder.mPublicVersion).setSound(((Notification)localObject1).sound, ((Notification)localObject1).audioAttributes);
      localObject1 = paramBuilder.mPeople.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (String)((Iterator)localObject1).next();
        this.mBuilder.addPerson((String)localObject2);
      }
      this.mHeadsUpContentView = paramBuilder.mHeadsUpContentView;
      if (paramBuilder.mInvisibleActions.size() > 0)
      {
        localObject2 = paramBuilder.getExtras().getBundle("android.car.EXTENSIONS");
        localObject1 = localObject2;
        if (localObject2 == null) {
          localObject1 = new Bundle();
        }
        localObject2 = new Bundle();
        for (int i = 0; i < paramBuilder.mInvisibleActions.size(); i++) {
          ((Bundle)localObject2).putBundle(Integer.toString(i), NotificationCompatJellybean.getBundleForAction((NotificationCompat.Action)paramBuilder.mInvisibleActions.get(i)));
        }
        ((Bundle)localObject1).putBundle("invisible_actions", (Bundle)localObject2);
        paramBuilder.getExtras().putBundle("android.car.EXTENSIONS", (Bundle)localObject1);
        this.mExtras.putBundle("android.car.EXTENSIONS", (Bundle)localObject1);
      }
    }
    if (Build.VERSION.SDK_INT >= 24)
    {
      this.mBuilder.setExtras(paramBuilder.mExtras).setRemoteInputHistory(paramBuilder.mRemoteInputHistory);
      if (paramBuilder.mContentView != null) {
        this.mBuilder.setCustomContentView(paramBuilder.mContentView);
      }
      if (paramBuilder.mBigContentView != null) {
        this.mBuilder.setCustomBigContentView(paramBuilder.mBigContentView);
      }
      if (paramBuilder.mHeadsUpContentView != null) {
        this.mBuilder.setCustomHeadsUpContentView(paramBuilder.mHeadsUpContentView);
      }
    }
    if (Build.VERSION.SDK_INT >= 26)
    {
      this.mBuilder.setBadgeIconType(paramBuilder.mBadgeIcon).setShortcutId(paramBuilder.mShortcutId).setTimeoutAfter(paramBuilder.mTimeout).setGroupAlertBehavior(paramBuilder.mGroupAlertBehavior);
      if (paramBuilder.mColorizedSet) {
        this.mBuilder.setColorized(paramBuilder.mColorized);
      }
      if (!TextUtils.isEmpty(paramBuilder.mChannelId)) {
        this.mBuilder.setSound(null).setDefaults(0).setLights(0, 0, 0).setVibrate(null);
      }
    }
    if (Build.VERSION.SDK_INT >= 29)
    {
      this.mBuilder.setAllowSystemGeneratedContextualActions(paramBuilder.mAllowSystemGeneratedContextualActions);
      this.mBuilder.setBubbleMetadata(NotificationCompat.BubbleMetadata.toPlatform(paramBuilder.mBubbleMetadata));
    }
    if (paramBuilder.mSilent)
    {
      if (this.mBuilderCompat.mGroupSummary) {
        this.mGroupAlertBehavior = 2;
      } else {
        this.mGroupAlertBehavior = 1;
      }
      if (Build.VERSION.SDK_INT >= 26)
      {
        if (TextUtils.isEmpty(this.mBuilderCompat.mGroupKey)) {
          this.mBuilder.setGroup("silent");
        }
        this.mBuilder.setGroupAlertBehavior(this.mGroupAlertBehavior);
      }
    }
  }
  
  private void addAction(NotificationCompat.Action paramAction)
  {
    if (Build.VERSION.SDK_INT >= 20)
    {
      Object localObject1 = paramAction.getIconCompat();
      int i = Build.VERSION.SDK_INT;
      int j = 0;
      if (i >= 23)
      {
        if (localObject1 != null) {
          localObject1 = ((IconCompat)localObject1).toIcon();
        } else {
          localObject1 = null;
        }
        localObject1 = new Notification.Action.Builder((Icon)localObject1, paramAction.getTitle(), paramAction.getActionIntent());
      }
      else
      {
        if (localObject1 != null) {
          i = ((IconCompat)localObject1).getResId();
        } else {
          i = 0;
        }
        localObject1 = new Notification.Action.Builder(i, paramAction.getTitle(), paramAction.getActionIntent());
      }
      Object localObject2;
      if (paramAction.getRemoteInputs() != null)
      {
        localObject2 = RemoteInput.fromCompat(paramAction.getRemoteInputs());
        int k = localObject2.length;
        for (i = j; i < k; i++) {
          ((Notification.Action.Builder)localObject1).addRemoteInput(localObject2[i]);
        }
      }
      if (paramAction.getExtras() != null) {
        localObject2 = new Bundle(paramAction.getExtras());
      } else {
        localObject2 = new Bundle();
      }
      ((Bundle)localObject2).putBoolean("android.support.allowGeneratedReplies", paramAction.getAllowGeneratedReplies());
      if (Build.VERSION.SDK_INT >= 24) {
        ((Notification.Action.Builder)localObject1).setAllowGeneratedReplies(paramAction.getAllowGeneratedReplies());
      }
      ((Bundle)localObject2).putInt("android.support.action.semanticAction", paramAction.getSemanticAction());
      if (Build.VERSION.SDK_INT >= 28) {
        ((Notification.Action.Builder)localObject1).setSemanticAction(paramAction.getSemanticAction());
      }
      if (Build.VERSION.SDK_INT >= 29) {
        ((Notification.Action.Builder)localObject1).setContextual(paramAction.isContextual());
      }
      ((Bundle)localObject2).putBoolean("android.support.action.showsUserInterface", paramAction.getShowsUserInterface());
      ((Notification.Action.Builder)localObject1).addExtras((Bundle)localObject2);
      this.mBuilder.addAction(((Notification.Action.Builder)localObject1).build());
    }
    else if (Build.VERSION.SDK_INT >= 16)
    {
      this.mActionExtrasList.add(NotificationCompatJellybean.writeActionAndGetExtras(this.mBuilder, paramAction));
    }
  }
  
  private void removeSoundAndVibration(Notification paramNotification)
  {
    paramNotification.sound = null;
    paramNotification.vibrate = null;
    paramNotification.defaults &= 0xFFFFFFFE;
    paramNotification.defaults &= 0xFFFFFFFD;
  }
  
  public Notification build()
  {
    NotificationCompat.Style localStyle = this.mBuilderCompat.mStyle;
    if (localStyle != null) {
      localStyle.apply(this);
    }
    Object localObject;
    if (localStyle != null) {
      localObject = localStyle.makeContentView(this);
    } else {
      localObject = null;
    }
    Notification localNotification = buildInternal();
    if (localObject != null) {
      localNotification.contentView = ((RemoteViews)localObject);
    } else if (this.mBuilderCompat.mContentView != null) {
      localNotification.contentView = this.mBuilderCompat.mContentView;
    }
    if ((Build.VERSION.SDK_INT >= 16) && (localStyle != null))
    {
      localObject = localStyle.makeBigContentView(this);
      if (localObject != null) {
        localNotification.bigContentView = ((RemoteViews)localObject);
      }
    }
    if ((Build.VERSION.SDK_INT >= 21) && (localStyle != null))
    {
      localObject = this.mBuilderCompat.mStyle.makeHeadsUpContentView(this);
      if (localObject != null) {
        localNotification.headsUpContentView = ((RemoteViews)localObject);
      }
    }
    if ((Build.VERSION.SDK_INT >= 16) && (localStyle != null))
    {
      localObject = NotificationCompat.getExtras(localNotification);
      if (localObject != null) {
        localStyle.addCompatExtras((Bundle)localObject);
      }
    }
    return localNotification;
  }
  
  protected Notification buildInternal()
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return this.mBuilder.build();
    }
    Object localObject1;
    if (Build.VERSION.SDK_INT >= 24)
    {
      localObject1 = this.mBuilder.build();
      if (this.mGroupAlertBehavior != 0)
      {
        if ((((Notification)localObject1).getGroup() != null) && ((((Notification)localObject1).flags & 0x200) != 0) && (this.mGroupAlertBehavior == 2)) {
          removeSoundAndVibration((Notification)localObject1);
        }
        if ((((Notification)localObject1).getGroup() != null) && ((((Notification)localObject1).flags & 0x200) == 0) && (this.mGroupAlertBehavior == 1)) {
          removeSoundAndVibration((Notification)localObject1);
        }
      }
      return localObject1;
    }
    Object localObject2;
    if (Build.VERSION.SDK_INT >= 21)
    {
      this.mBuilder.setExtras(this.mExtras);
      localObject1 = this.mBuilder.build();
      localObject2 = this.mContentView;
      if (localObject2 != null) {
        ((Notification)localObject1).contentView = ((RemoteViews)localObject2);
      }
      localObject2 = this.mBigContentView;
      if (localObject2 != null) {
        ((Notification)localObject1).bigContentView = ((RemoteViews)localObject2);
      }
      localObject2 = this.mHeadsUpContentView;
      if (localObject2 != null) {
        ((Notification)localObject1).headsUpContentView = ((RemoteViews)localObject2);
      }
      if (this.mGroupAlertBehavior != 0)
      {
        if ((((Notification)localObject1).getGroup() != null) && ((((Notification)localObject1).flags & 0x200) != 0) && (this.mGroupAlertBehavior == 2)) {
          removeSoundAndVibration((Notification)localObject1);
        }
        if ((((Notification)localObject1).getGroup() != null) && ((((Notification)localObject1).flags & 0x200) == 0) && (this.mGroupAlertBehavior == 1)) {
          removeSoundAndVibration((Notification)localObject1);
        }
      }
      return localObject1;
    }
    if (Build.VERSION.SDK_INT >= 20)
    {
      this.mBuilder.setExtras(this.mExtras);
      localObject1 = this.mBuilder.build();
      localObject2 = this.mContentView;
      if (localObject2 != null) {
        ((Notification)localObject1).contentView = ((RemoteViews)localObject2);
      }
      localObject2 = this.mBigContentView;
      if (localObject2 != null) {
        ((Notification)localObject1).bigContentView = ((RemoteViews)localObject2);
      }
      if (this.mGroupAlertBehavior != 0)
      {
        if ((((Notification)localObject1).getGroup() != null) && ((((Notification)localObject1).flags & 0x200) != 0) && (this.mGroupAlertBehavior == 2)) {
          removeSoundAndVibration((Notification)localObject1);
        }
        if ((((Notification)localObject1).getGroup() != null) && ((((Notification)localObject1).flags & 0x200) == 0) && (this.mGroupAlertBehavior == 1)) {
          removeSoundAndVibration((Notification)localObject1);
        }
      }
      return localObject1;
    }
    if (Build.VERSION.SDK_INT >= 19)
    {
      localObject1 = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList);
      if (localObject1 != null) {
        this.mExtras.putSparseParcelableArray("android.support.actionExtras", (SparseArray)localObject1);
      }
      this.mBuilder.setExtras(this.mExtras);
      localObject1 = this.mBuilder.build();
      localObject2 = this.mContentView;
      if (localObject2 != null) {
        ((Notification)localObject1).contentView = ((RemoteViews)localObject2);
      }
      localObject2 = this.mBigContentView;
      if (localObject2 != null) {
        ((Notification)localObject1).bigContentView = ((RemoteViews)localObject2);
      }
      return localObject1;
    }
    if (Build.VERSION.SDK_INT >= 16)
    {
      localObject1 = this.mBuilder.build();
      localObject2 = NotificationCompat.getExtras((Notification)localObject1);
      Bundle localBundle = new Bundle(this.mExtras);
      Iterator localIterator = this.mExtras.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        if (((Bundle)localObject2).containsKey(str)) {
          localBundle.remove(str);
        }
      }
      ((Bundle)localObject2).putAll(localBundle);
      localObject2 = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList);
      if (localObject2 != null) {
        NotificationCompat.getExtras((Notification)localObject1).putSparseParcelableArray("android.support.actionExtras", (SparseArray)localObject2);
      }
      localObject2 = this.mContentView;
      if (localObject2 != null) {
        ((Notification)localObject1).contentView = ((RemoteViews)localObject2);
      }
      localObject2 = this.mBigContentView;
      if (localObject2 != null) {
        ((Notification)localObject1).bigContentView = ((RemoteViews)localObject2);
      }
      return localObject1;
    }
    return this.mBuilder.getNotification();
  }
  
  public Notification.Builder getBuilder()
  {
    return this.mBuilder;
  }
}

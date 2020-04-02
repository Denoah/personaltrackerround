package androidx.fragment.app;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.collection.ArrayMap;
import androidx.core.app.SharedElementCallback;
import androidx.core.os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class FragmentTransition
{
  private static final int[] INVERSE_OPS = { 0, 3, 0, 1, 5, 4, 7, 6, 9, 8, 10 };
  private static final FragmentTransitionImpl PLATFORM_IMPL;
  private static final FragmentTransitionImpl SUPPORT_IMPL = resolveSupportImpl();
  
  static
  {
    FragmentTransitionCompat21 localFragmentTransitionCompat21;
    if (Build.VERSION.SDK_INT >= 21) {
      localFragmentTransitionCompat21 = new FragmentTransitionCompat21();
    } else {
      localFragmentTransitionCompat21 = null;
    }
    PLATFORM_IMPL = localFragmentTransitionCompat21;
  }
  
  private FragmentTransition() {}
  
  private static void addSharedElementsWithMatchingNames(ArrayList<View> paramArrayList, ArrayMap<String, View> paramArrayMap, Collection<String> paramCollection)
  {
    for (int i = paramArrayMap.size() - 1; i >= 0; i--)
    {
      View localView = (View)paramArrayMap.valueAt(i);
      if (paramCollection.contains(ViewCompat.getTransitionName(localView))) {
        paramArrayList.add(localView);
      }
    }
  }
  
  private static void addToFirstInLastOut(BackStackRecord paramBackStackRecord, FragmentTransaction.Op paramOp, SparseArray<FragmentContainerTransition> paramSparseArray, boolean paramBoolean1, boolean paramBoolean2)
  {
    Fragment localFragment = paramOp.mFragment;
    if (localFragment == null) {
      return;
    }
    int i = localFragment.mContainerId;
    if (i == 0) {
      return;
    }
    if (paramBoolean1) {
      j = INVERSE_OPS[paramOp.mCmd];
    } else {
      j = paramOp.mCmd;
    }
    boolean bool = false;
    if (j != 1)
    {
      if (j != 3) {
        if (j != 4)
        {
          if (j != 5)
          {
            if (j != 6)
            {
              if (j == 7) {
                break label282;
              }
              k = 0;
              j = k;
              m = j;
              break label332;
            }
          }
          else
          {
            if (paramBoolean2)
            {
              if ((!localFragment.mHiddenChanged) || (localFragment.mHidden) || (!localFragment.mAdded)) {
                break label319;
              }
              break label313;
            }
            bool = localFragment.mHidden;
            break label322;
          }
        }
        else
        {
          if (paramBoolean2) {
            if ((!localFragment.mHiddenChanged) || (!localFragment.mAdded) || (!localFragment.mHidden)) {
              break label244;
            }
          }
          for (;;)
          {
            break;
            if ((!localFragment.mAdded) || (localFragment.mHidden)) {
              break label244;
            }
          }
        }
      }
      if (paramBoolean2) {
        if ((localFragment.mAdded) || (localFragment.mView == null) || (localFragment.mView.getVisibility() != 0) || (localFragment.mPostponedAlpha < 0.0F)) {}
      }
      for (;;)
      {
        j = 1;
        break;
        label244:
        do
        {
          j = 0;
          break;
        } while ((!localFragment.mAdded) || (localFragment.mHidden));
      }
      m = j;
      k = 0;
      j = 1;
      break label332;
    }
    label282:
    if (paramBoolean2) {
      bool = localFragment.mIsNewlyAdded;
    } else if ((!localFragment.mAdded) && (!localFragment.mHidden)) {
      label313:
      bool = true;
    } else {
      label319:
      bool = false;
    }
    label322:
    int j = 0;
    int m = j;
    int k = 1;
    label332:
    Object localObject = (FragmentContainerTransition)paramSparseArray.get(i);
    paramOp = (FragmentTransaction.Op)localObject;
    if (bool)
    {
      paramOp = ensureContainer((FragmentContainerTransition)localObject, paramSparseArray, i);
      paramOp.lastIn = localFragment;
      paramOp.lastInIsPop = paramBoolean1;
      paramOp.lastInTransaction = paramBackStackRecord;
    }
    if ((!paramBoolean2) && (k != 0))
    {
      if ((paramOp != null) && (paramOp.firstOut == localFragment)) {
        paramOp.firstOut = null;
      }
      localObject = paramBackStackRecord.mManager;
      if ((localFragment.mState < 1) && (((FragmentManager)localObject).mCurState >= 1) && (!paramBackStackRecord.mReorderingAllowed))
      {
        ((FragmentManager)localObject).makeActive(localFragment);
        ((FragmentManager)localObject).moveToState(localFragment, 1);
      }
    }
    localObject = paramOp;
    if (m != 0) {
      if (paramOp != null)
      {
        localObject = paramOp;
        if (paramOp.firstOut != null) {}
      }
      else
      {
        localObject = ensureContainer(paramOp, paramSparseArray, i);
        ((FragmentContainerTransition)localObject).firstOut = localFragment;
        ((FragmentContainerTransition)localObject).firstOutIsPop = paramBoolean1;
        ((FragmentContainerTransition)localObject).firstOutTransaction = paramBackStackRecord;
      }
    }
    if ((!paramBoolean2) && (j != 0) && (localObject != null) && (((FragmentContainerTransition)localObject).lastIn == localFragment)) {
      ((FragmentContainerTransition)localObject).lastIn = null;
    }
  }
  
  public static void calculateFragments(BackStackRecord paramBackStackRecord, SparseArray<FragmentContainerTransition> paramSparseArray, boolean paramBoolean)
  {
    int i = paramBackStackRecord.mOps.size();
    for (int j = 0; j < i; j++) {
      addToFirstInLastOut(paramBackStackRecord, (FragmentTransaction.Op)paramBackStackRecord.mOps.get(j), paramSparseArray, false, paramBoolean);
    }
  }
  
  private static ArrayMap<String, String> calculateNameOverrides(int paramInt1, ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt2, int paramInt3)
  {
    ArrayMap localArrayMap = new ArrayMap();
    paramInt3--;
    while (paramInt3 >= paramInt2)
    {
      Object localObject = (BackStackRecord)paramArrayList.get(paramInt3);
      if (((BackStackRecord)localObject).interactsWith(paramInt1))
      {
        boolean bool = ((Boolean)paramArrayList1.get(paramInt3)).booleanValue();
        if (((BackStackRecord)localObject).mSharedElementSourceNames != null)
        {
          int i = ((BackStackRecord)localObject).mSharedElementSourceNames.size();
          ArrayList localArrayList1;
          ArrayList localArrayList2;
          if (bool)
          {
            localArrayList1 = ((BackStackRecord)localObject).mSharedElementSourceNames;
            localArrayList2 = ((BackStackRecord)localObject).mSharedElementTargetNames;
          }
          else
          {
            localArrayList2 = ((BackStackRecord)localObject).mSharedElementSourceNames;
            localArrayList1 = ((BackStackRecord)localObject).mSharedElementTargetNames;
          }
          for (int j = 0; j < i; j++)
          {
            String str1 = (String)localArrayList2.get(j);
            String str2 = (String)localArrayList1.get(j);
            localObject = (String)localArrayMap.remove(str2);
            if (localObject != null) {
              localArrayMap.put(str1, localObject);
            } else {
              localArrayMap.put(str1, str2);
            }
          }
        }
      }
      paramInt3--;
    }
    return localArrayMap;
  }
  
  public static void calculatePopFragments(BackStackRecord paramBackStackRecord, SparseArray<FragmentContainerTransition> paramSparseArray, boolean paramBoolean)
  {
    if (!paramBackStackRecord.mManager.mContainer.onHasView()) {
      return;
    }
    for (int i = paramBackStackRecord.mOps.size() - 1; i >= 0; i--) {
      addToFirstInLastOut(paramBackStackRecord, (FragmentTransaction.Op)paramBackStackRecord.mOps.get(i), paramSparseArray, true, paramBoolean);
    }
  }
  
  static void callSharedElementStartEnd(Fragment paramFragment1, Fragment paramFragment2, boolean paramBoolean1, ArrayMap<String, View> paramArrayMap, boolean paramBoolean2)
  {
    if (paramBoolean1) {
      paramFragment1 = paramFragment2.getEnterTransitionCallback();
    } else {
      paramFragment1 = paramFragment1.getEnterTransitionCallback();
    }
    if (paramFragment1 != null)
    {
      paramFragment2 = new ArrayList();
      ArrayList localArrayList = new ArrayList();
      int i = 0;
      int j;
      if (paramArrayMap == null) {
        j = 0;
      } else {
        j = paramArrayMap.size();
      }
      while (i < j)
      {
        localArrayList.add(paramArrayMap.keyAt(i));
        paramFragment2.add(paramArrayMap.valueAt(i));
        i++;
      }
      if (paramBoolean2) {
        paramFragment1.onSharedElementStart(localArrayList, paramFragment2, null);
      } else {
        paramFragment1.onSharedElementEnd(localArrayList, paramFragment2, null);
      }
    }
  }
  
  private static boolean canHandleAll(FragmentTransitionImpl paramFragmentTransitionImpl, List<Object> paramList)
  {
    int i = paramList.size();
    for (int j = 0; j < i; j++) {
      if (!paramFragmentTransitionImpl.canHandle(paramList.get(j))) {
        return false;
      }
    }
    return true;
  }
  
  static ArrayMap<String, View> captureInSharedElements(FragmentTransitionImpl paramFragmentTransitionImpl, ArrayMap<String, String> paramArrayMap, Object paramObject, FragmentContainerTransition paramFragmentContainerTransition)
  {
    Fragment localFragment = paramFragmentContainerTransition.lastIn;
    View localView = localFragment.getView();
    if ((!paramArrayMap.isEmpty()) && (paramObject != null) && (localView != null))
    {
      ArrayMap localArrayMap = new ArrayMap();
      paramFragmentTransitionImpl.findNamedViews(localArrayMap, localView);
      paramFragmentTransitionImpl = paramFragmentContainerTransition.lastInTransaction;
      if (paramFragmentContainerTransition.lastInIsPop)
      {
        paramObject = localFragment.getExitTransitionCallback();
        paramFragmentTransitionImpl = paramFragmentTransitionImpl.mSharedElementSourceNames;
      }
      else
      {
        paramObject = localFragment.getEnterTransitionCallback();
        paramFragmentTransitionImpl = paramFragmentTransitionImpl.mSharedElementTargetNames;
      }
      if (paramFragmentTransitionImpl != null)
      {
        localArrayMap.retainAll(paramFragmentTransitionImpl);
        localArrayMap.retainAll(paramArrayMap.values());
      }
      if (paramObject != null)
      {
        paramObject.onMapSharedElements(paramFragmentTransitionImpl, localArrayMap);
        for (int i = paramFragmentTransitionImpl.size() - 1; i >= 0; i--)
        {
          paramFragmentContainerTransition = (String)paramFragmentTransitionImpl.get(i);
          paramObject = (View)localArrayMap.get(paramFragmentContainerTransition);
          if (paramObject == null)
          {
            paramObject = findKeyForValue(paramArrayMap, paramFragmentContainerTransition);
            if (paramObject != null) {
              paramArrayMap.remove(paramObject);
            }
          }
          else if (!paramFragmentContainerTransition.equals(ViewCompat.getTransitionName(paramObject)))
          {
            paramFragmentContainerTransition = findKeyForValue(paramArrayMap, paramFragmentContainerTransition);
            if (paramFragmentContainerTransition != null) {
              paramArrayMap.put(paramFragmentContainerTransition, ViewCompat.getTransitionName(paramObject));
            }
          }
        }
      }
      retainValues(paramArrayMap, localArrayMap);
      return localArrayMap;
    }
    paramArrayMap.clear();
    return null;
  }
  
  private static ArrayMap<String, View> captureOutSharedElements(FragmentTransitionImpl paramFragmentTransitionImpl, ArrayMap<String, String> paramArrayMap, Object paramObject, FragmentContainerTransition paramFragmentContainerTransition)
  {
    if ((!paramArrayMap.isEmpty()) && (paramObject != null))
    {
      paramObject = paramFragmentContainerTransition.firstOut;
      ArrayMap localArrayMap = new ArrayMap();
      paramFragmentTransitionImpl.findNamedViews(localArrayMap, paramObject.requireView());
      paramFragmentTransitionImpl = paramFragmentContainerTransition.firstOutTransaction;
      if (paramFragmentContainerTransition.firstOutIsPop)
      {
        paramObject = paramObject.getEnterTransitionCallback();
        paramFragmentTransitionImpl = paramFragmentTransitionImpl.mSharedElementTargetNames;
      }
      else
      {
        paramObject = paramObject.getExitTransitionCallback();
        paramFragmentTransitionImpl = paramFragmentTransitionImpl.mSharedElementSourceNames;
      }
      if (paramFragmentTransitionImpl != null) {
        localArrayMap.retainAll(paramFragmentTransitionImpl);
      }
      if (paramObject != null)
      {
        paramObject.onMapSharedElements(paramFragmentTransitionImpl, localArrayMap);
        for (int i = paramFragmentTransitionImpl.size() - 1; i >= 0; i--)
        {
          paramFragmentContainerTransition = (String)paramFragmentTransitionImpl.get(i);
          paramObject = (View)localArrayMap.get(paramFragmentContainerTransition);
          if (paramObject == null)
          {
            paramArrayMap.remove(paramFragmentContainerTransition);
          }
          else if (!paramFragmentContainerTransition.equals(ViewCompat.getTransitionName(paramObject)))
          {
            paramFragmentContainerTransition = (String)paramArrayMap.remove(paramFragmentContainerTransition);
            paramArrayMap.put(ViewCompat.getTransitionName(paramObject), paramFragmentContainerTransition);
          }
        }
      }
      paramArrayMap.retainAll(localArrayMap.keySet());
      return localArrayMap;
    }
    paramArrayMap.clear();
    return null;
  }
  
  private static FragmentTransitionImpl chooseImpl(Fragment paramFragment1, Fragment paramFragment2)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramFragment1 != null)
    {
      Object localObject = paramFragment1.getExitTransition();
      if (localObject != null) {
        localArrayList.add(localObject);
      }
      localObject = paramFragment1.getReturnTransition();
      if (localObject != null) {
        localArrayList.add(localObject);
      }
      paramFragment1 = paramFragment1.getSharedElementReturnTransition();
      if (paramFragment1 != null) {
        localArrayList.add(paramFragment1);
      }
    }
    if (paramFragment2 != null)
    {
      paramFragment1 = paramFragment2.getEnterTransition();
      if (paramFragment1 != null) {
        localArrayList.add(paramFragment1);
      }
      paramFragment1 = paramFragment2.getReenterTransition();
      if (paramFragment1 != null) {
        localArrayList.add(paramFragment1);
      }
      paramFragment1 = paramFragment2.getSharedElementEnterTransition();
      if (paramFragment1 != null) {
        localArrayList.add(paramFragment1);
      }
    }
    if (localArrayList.isEmpty()) {
      return null;
    }
    paramFragment1 = PLATFORM_IMPL;
    if ((paramFragment1 != null) && (canHandleAll(paramFragment1, localArrayList))) {
      return PLATFORM_IMPL;
    }
    paramFragment1 = SUPPORT_IMPL;
    if ((paramFragment1 != null) && (canHandleAll(paramFragment1, localArrayList))) {
      return SUPPORT_IMPL;
    }
    if ((PLATFORM_IMPL == null) && (SUPPORT_IMPL == null)) {
      return null;
    }
    throw new IllegalArgumentException("Invalid Transition types");
  }
  
  static ArrayList<View> configureEnteringExitingViews(FragmentTransitionImpl paramFragmentTransitionImpl, Object paramObject, Fragment paramFragment, ArrayList<View> paramArrayList, View paramView)
  {
    if (paramObject != null)
    {
      ArrayList localArrayList = new ArrayList();
      paramFragment = paramFragment.getView();
      if (paramFragment != null) {
        paramFragmentTransitionImpl.captureTransitioningViews(localArrayList, paramFragment);
      }
      if (paramArrayList != null) {
        localArrayList.removeAll(paramArrayList);
      }
      paramFragment = localArrayList;
      if (!localArrayList.isEmpty())
      {
        localArrayList.add(paramView);
        paramFragmentTransitionImpl.addTargets(paramObject, localArrayList);
        paramFragment = localArrayList;
      }
    }
    else
    {
      paramFragment = null;
    }
    return paramFragment;
  }
  
  private static Object configureSharedElementsOrdered(FragmentTransitionImpl paramFragmentTransitionImpl, ViewGroup paramViewGroup, final View paramView, final ArrayMap<String, String> paramArrayMap, final FragmentContainerTransition paramFragmentContainerTransition, final ArrayList<View> paramArrayList1, final ArrayList<View> paramArrayList2, final Object paramObject1, final Object paramObject2)
  {
    final Fragment localFragment1 = paramFragmentContainerTransition.lastIn;
    final Fragment localFragment2 = paramFragmentContainerTransition.firstOut;
    if ((localFragment1 != null) && (localFragment2 != null))
    {
      final boolean bool = paramFragmentContainerTransition.lastInIsPop;
      final Object localObject;
      if (paramArrayMap.isEmpty()) {
        localObject = null;
      } else {
        localObject = getSharedElementTransition(paramFragmentTransitionImpl, localFragment1, localFragment2, bool);
      }
      ArrayMap localArrayMap = captureOutSharedElements(paramFragmentTransitionImpl, paramArrayMap, localObject, paramFragmentContainerTransition);
      if (paramArrayMap.isEmpty()) {
        localObject = null;
      } else {
        paramArrayList1.addAll(localArrayMap.values());
      }
      if ((paramObject1 == null) && (paramObject2 == null) && (localObject == null)) {
        return null;
      }
      callSharedElementStartEnd(localFragment1, localFragment2, bool, localArrayMap, true);
      if (localObject != null)
      {
        Rect localRect = new Rect();
        paramFragmentTransitionImpl.setSharedElementTargets(localObject, paramView, paramArrayList1);
        setOutEpicenter(paramFragmentTransitionImpl, localObject, paramObject2, localArrayMap, paramFragmentContainerTransition.firstOutIsPop, paramFragmentContainerTransition.firstOutTransaction);
        paramObject2 = localRect;
        if (paramObject1 != null)
        {
          paramFragmentTransitionImpl.setEpicenter(paramObject1, localRect);
          paramObject2 = localRect;
        }
      }
      else
      {
        paramObject2 = null;
      }
      OneShotPreDrawListener.add(paramViewGroup, new Runnable()
      {
        public void run()
        {
          Object localObject1 = FragmentTransition.captureInSharedElements(this.val$impl, paramArrayMap, localObject, paramFragmentContainerTransition);
          if (localObject1 != null)
          {
            paramArrayList2.addAll(((ArrayMap)localObject1).values());
            paramArrayList2.add(paramView);
          }
          FragmentTransition.callSharedElementStartEnd(localFragment1, localFragment2, bool, (ArrayMap)localObject1, false);
          Object localObject2 = localObject;
          if (localObject2 != null)
          {
            this.val$impl.swapSharedElementTargets(localObject2, paramArrayList1, paramArrayList2);
            localObject1 = FragmentTransition.getInEpicenterView((ArrayMap)localObject1, paramFragmentContainerTransition, paramObject1, bool);
            if (localObject1 != null) {
              this.val$impl.getBoundsOnScreen((View)localObject1, paramObject2);
            }
          }
        }
      });
      return localObject;
    }
    return null;
  }
  
  private static Object configureSharedElementsReordered(final FragmentTransitionImpl paramFragmentTransitionImpl, ViewGroup paramViewGroup, View paramView, ArrayMap<String, String> paramArrayMap, final FragmentContainerTransition paramFragmentContainerTransition, final ArrayList<View> paramArrayList1, ArrayList<View> paramArrayList2, Object paramObject1, Object paramObject2)
  {
    Fragment localFragment1 = paramFragmentContainerTransition.lastIn;
    final Fragment localFragment2 = paramFragmentContainerTransition.firstOut;
    if (localFragment1 != null) {
      localFragment1.requireView().setVisibility(0);
    }
    if ((localFragment1 != null) && (localFragment2 != null))
    {
      final boolean bool = paramFragmentContainerTransition.lastInIsPop;
      Object localObject;
      if (paramArrayMap.isEmpty()) {
        localObject = null;
      } else {
        localObject = getSharedElementTransition(paramFragmentTransitionImpl, localFragment1, localFragment2, bool);
      }
      ArrayMap localArrayMap1 = captureOutSharedElements(paramFragmentTransitionImpl, paramArrayMap, localObject, paramFragmentContainerTransition);
      final ArrayMap localArrayMap2 = captureInSharedElements(paramFragmentTransitionImpl, paramArrayMap, localObject, paramFragmentContainerTransition);
      if (paramArrayMap.isEmpty())
      {
        if (localArrayMap1 != null) {
          localArrayMap1.clear();
        }
        if (localArrayMap2 != null) {
          localArrayMap2.clear();
        }
        paramArrayMap = null;
      }
      else
      {
        addSharedElementsWithMatchingNames(paramArrayList1, localArrayMap1, paramArrayMap.keySet());
        addSharedElementsWithMatchingNames(paramArrayList2, localArrayMap2, paramArrayMap.values());
        paramArrayMap = localObject;
      }
      if ((paramObject1 == null) && (paramObject2 == null) && (paramArrayMap == null)) {
        return null;
      }
      callSharedElementStartEnd(localFragment1, localFragment2, bool, localArrayMap1, true);
      if (paramArrayMap != null)
      {
        paramArrayList2.add(paramView);
        paramFragmentTransitionImpl.setSharedElementTargets(paramArrayMap, paramView, paramArrayList1);
        setOutEpicenter(paramFragmentTransitionImpl, paramArrayMap, paramObject2, localArrayMap1, paramFragmentContainerTransition.firstOutIsPop, paramFragmentContainerTransition.firstOutTransaction);
        paramView = new Rect();
        paramArrayList1 = getInEpicenterView(localArrayMap2, paramFragmentContainerTransition, paramObject1, bool);
        if (paramArrayList1 != null) {
          paramFragmentTransitionImpl.setEpicenter(paramObject1, paramView);
        }
        paramFragmentContainerTransition = paramView;
      }
      else
      {
        paramView = null;
        paramFragmentContainerTransition = paramView;
        paramArrayList1 = paramView;
      }
      OneShotPreDrawListener.add(paramViewGroup, new Runnable()
      {
        public void run()
        {
          FragmentTransition.callSharedElementStartEnd(this.val$inFragment, localFragment2, bool, localArrayMap2, false);
          View localView = paramArrayList1;
          if (localView != null) {
            paramFragmentTransitionImpl.getBoundsOnScreen(localView, paramFragmentContainerTransition);
          }
        }
      });
      return paramArrayMap;
    }
    return null;
  }
  
  private static void configureTransitionsOrdered(FragmentManager paramFragmentManager, int paramInt, FragmentContainerTransition paramFragmentContainerTransition, View paramView, ArrayMap<String, String> paramArrayMap, Callback paramCallback)
  {
    if (paramFragmentManager.mContainer.onHasView()) {
      paramFragmentManager = (ViewGroup)paramFragmentManager.mContainer.onFindViewById(paramInt);
    } else {
      paramFragmentManager = null;
    }
    if (paramFragmentManager == null) {
      return;
    }
    Fragment localFragment1 = paramFragmentContainerTransition.lastIn;
    final Fragment localFragment2 = paramFragmentContainerTransition.firstOut;
    FragmentTransitionImpl localFragmentTransitionImpl = chooseImpl(localFragment2, localFragment1);
    if (localFragmentTransitionImpl == null) {
      return;
    }
    boolean bool1 = paramFragmentContainerTransition.lastInIsPop;
    boolean bool2 = paramFragmentContainerTransition.firstOutIsPop;
    Object localObject1 = getEnterTransition(localFragmentTransitionImpl, localFragment1, bool1);
    Object localObject2 = getExitTransition(localFragmentTransitionImpl, localFragment2, bool2);
    Object localObject3 = new ArrayList();
    ArrayList localArrayList1 = new ArrayList();
    Object localObject4 = configureSharedElementsOrdered(localFragmentTransitionImpl, paramFragmentManager, paramView, paramArrayMap, paramFragmentContainerTransition, (ArrayList)localObject3, localArrayList1, localObject1, localObject2);
    if ((localObject1 == null) && (localObject4 == null) && (localObject2 == null)) {
      return;
    }
    ArrayList localArrayList2 = configureEnteringExitingViews(localFragmentTransitionImpl, localObject2, localFragment2, (ArrayList)localObject3, paramView);
    if ((localArrayList2 != null) && (!localArrayList2.isEmpty())) {
      break label183;
    }
    localObject2 = null;
    label183:
    localFragmentTransitionImpl.addTarget(localObject1, paramView);
    paramFragmentContainerTransition = mergeTransitions(localFragmentTransitionImpl, localObject1, localObject2, localObject4, localFragment1, paramFragmentContainerTransition.lastInIsPop);
    if ((localFragment2 != null) && (localArrayList2 != null) && ((localArrayList2.size() > 0) || (((ArrayList)localObject3).size() > 0)))
    {
      localObject3 = new CancellationSignal();
      paramCallback.onStart(localFragment2, (CancellationSignal)localObject3);
      localFragmentTransitionImpl.setListenerForTransitionEnd(localFragment2, paramFragmentContainerTransition, (CancellationSignal)localObject3, new Runnable()
      {
        public void run()
        {
          this.val$callback.onComplete(localFragment2, this.val$signal);
        }
      });
    }
    if (paramFragmentContainerTransition != null)
    {
      paramCallback = new ArrayList();
      localFragmentTransitionImpl.scheduleRemoveTargets(paramFragmentContainerTransition, localObject1, paramCallback, localObject2, localArrayList2, localObject4, localArrayList1);
      scheduleTargetChange(localFragmentTransitionImpl, paramFragmentManager, localFragment1, paramView, localArrayList1, localObject1, paramCallback, localObject2, localArrayList2);
      localFragmentTransitionImpl.setNameOverridesOrdered(paramFragmentManager, localArrayList1, paramArrayMap);
      localFragmentTransitionImpl.beginDelayedTransition(paramFragmentManager, paramFragmentContainerTransition);
      localFragmentTransitionImpl.scheduleNameReset(paramFragmentManager, localArrayList1, paramArrayMap);
    }
  }
  
  private static void configureTransitionsReordered(FragmentManager paramFragmentManager, int paramInt, FragmentContainerTransition paramFragmentContainerTransition, View paramView, ArrayMap<String, String> paramArrayMap, Callback paramCallback)
  {
    if (paramFragmentManager.mContainer.onHasView()) {
      paramFragmentManager = (ViewGroup)paramFragmentManager.mContainer.onFindViewById(paramInt);
    } else {
      paramFragmentManager = null;
    }
    if (paramFragmentManager == null) {
      return;
    }
    Object localObject1 = paramFragmentContainerTransition.lastIn;
    final Fragment localFragment = paramFragmentContainerTransition.firstOut;
    FragmentTransitionImpl localFragmentTransitionImpl = chooseImpl(localFragment, (Fragment)localObject1);
    if (localFragmentTransitionImpl == null) {
      return;
    }
    boolean bool1 = paramFragmentContainerTransition.lastInIsPop;
    boolean bool2 = paramFragmentContainerTransition.firstOutIsPop;
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    Object localObject2 = getEnterTransition(localFragmentTransitionImpl, (Fragment)localObject1, bool1);
    Object localObject3 = getExitTransition(localFragmentTransitionImpl, localFragment, bool2);
    Object localObject4 = configureSharedElementsReordered(localFragmentTransitionImpl, paramFragmentManager, paramView, paramArrayMap, paramFragmentContainerTransition, localArrayList2, localArrayList1, localObject2, localObject3);
    if ((localObject2 == null) && (localObject4 == null) && (localObject3 == null)) {
      return;
    }
    paramFragmentContainerTransition = (FragmentContainerTransition)localObject3;
    localObject3 = configureEnteringExitingViews(localFragmentTransitionImpl, paramFragmentContainerTransition, localFragment, localArrayList2, paramView);
    paramView = configureEnteringExitingViews(localFragmentTransitionImpl, localObject2, (Fragment)localObject1, localArrayList1, paramView);
    setViewVisibility(paramView, 4);
    localObject1 = mergeTransitions(localFragmentTransitionImpl, localObject2, paramFragmentContainerTransition, localObject4, (Fragment)localObject1, bool1);
    if ((localFragment != null) && (localObject3 != null) && ((((ArrayList)localObject3).size() > 0) || (localArrayList2.size() > 0)))
    {
      final CancellationSignal localCancellationSignal = new CancellationSignal();
      paramCallback.onStart(localFragment, localCancellationSignal);
      localFragmentTransitionImpl.setListenerForTransitionEnd(localFragment, localObject1, localCancellationSignal, new Runnable()
      {
        public void run()
        {
          this.val$callback.onComplete(localFragment, localCancellationSignal);
        }
      });
    }
    if (localObject1 != null)
    {
      replaceHide(localFragmentTransitionImpl, paramFragmentContainerTransition, localFragment, (ArrayList)localObject3);
      paramCallback = localFragmentTransitionImpl.prepareSetNameOverridesReordered(localArrayList1);
      localFragmentTransitionImpl.scheduleRemoveTargets(localObject1, localObject2, paramView, paramFragmentContainerTransition, (ArrayList)localObject3, localObject4, localArrayList1);
      localFragmentTransitionImpl.beginDelayedTransition(paramFragmentManager, localObject1);
      localFragmentTransitionImpl.setNameOverridesReordered(paramFragmentManager, localArrayList2, localArrayList1, paramCallback, paramArrayMap);
      setViewVisibility(paramView, 0);
      localFragmentTransitionImpl.swapSharedElementTargets(localObject4, localArrayList2, localArrayList1);
    }
  }
  
  private static FragmentContainerTransition ensureContainer(FragmentContainerTransition paramFragmentContainerTransition, SparseArray<FragmentContainerTransition> paramSparseArray, int paramInt)
  {
    FragmentContainerTransition localFragmentContainerTransition = paramFragmentContainerTransition;
    if (paramFragmentContainerTransition == null)
    {
      localFragmentContainerTransition = new FragmentContainerTransition();
      paramSparseArray.put(paramInt, localFragmentContainerTransition);
    }
    return localFragmentContainerTransition;
  }
  
  private static String findKeyForValue(ArrayMap<String, String> paramArrayMap, String paramString)
  {
    int i = paramArrayMap.size();
    for (int j = 0; j < i; j++) {
      if (paramString.equals(paramArrayMap.valueAt(j))) {
        return (String)paramArrayMap.keyAt(j);
      }
    }
    return null;
  }
  
  private static Object getEnterTransition(FragmentTransitionImpl paramFragmentTransitionImpl, Fragment paramFragment, boolean paramBoolean)
  {
    if (paramFragment == null) {
      return null;
    }
    if (paramBoolean) {
      paramFragment = paramFragment.getReenterTransition();
    } else {
      paramFragment = paramFragment.getEnterTransition();
    }
    return paramFragmentTransitionImpl.cloneTransition(paramFragment);
  }
  
  private static Object getExitTransition(FragmentTransitionImpl paramFragmentTransitionImpl, Fragment paramFragment, boolean paramBoolean)
  {
    if (paramFragment == null) {
      return null;
    }
    if (paramBoolean) {
      paramFragment = paramFragment.getReturnTransition();
    } else {
      paramFragment = paramFragment.getExitTransition();
    }
    return paramFragmentTransitionImpl.cloneTransition(paramFragment);
  }
  
  static View getInEpicenterView(ArrayMap<String, View> paramArrayMap, FragmentContainerTransition paramFragmentContainerTransition, Object paramObject, boolean paramBoolean)
  {
    paramFragmentContainerTransition = paramFragmentContainerTransition.lastInTransaction;
    if ((paramObject != null) && (paramArrayMap != null) && (paramFragmentContainerTransition.mSharedElementSourceNames != null) && (!paramFragmentContainerTransition.mSharedElementSourceNames.isEmpty()))
    {
      if (paramBoolean) {
        paramFragmentContainerTransition = (String)paramFragmentContainerTransition.mSharedElementSourceNames.get(0);
      } else {
        paramFragmentContainerTransition = (String)paramFragmentContainerTransition.mSharedElementTargetNames.get(0);
      }
      return (View)paramArrayMap.get(paramFragmentContainerTransition);
    }
    return null;
  }
  
  private static Object getSharedElementTransition(FragmentTransitionImpl paramFragmentTransitionImpl, Fragment paramFragment1, Fragment paramFragment2, boolean paramBoolean)
  {
    if ((paramFragment1 != null) && (paramFragment2 != null))
    {
      if (paramBoolean) {
        paramFragment1 = paramFragment2.getSharedElementReturnTransition();
      } else {
        paramFragment1 = paramFragment1.getSharedElementEnterTransition();
      }
      return paramFragmentTransitionImpl.wrapTransitionInSet(paramFragmentTransitionImpl.cloneTransition(paramFragment1));
    }
    return null;
  }
  
  private static Object mergeTransitions(FragmentTransitionImpl paramFragmentTransitionImpl, Object paramObject1, Object paramObject2, Object paramObject3, Fragment paramFragment, boolean paramBoolean)
  {
    if ((paramObject1 != null) && (paramObject2 != null) && (paramFragment != null))
    {
      if (paramBoolean) {
        paramBoolean = paramFragment.getAllowReturnTransitionOverlap();
      } else {
        paramBoolean = paramFragment.getAllowEnterTransitionOverlap();
      }
    }
    else {
      paramBoolean = true;
    }
    if (paramBoolean) {
      paramFragmentTransitionImpl = paramFragmentTransitionImpl.mergeTransitionsTogether(paramObject2, paramObject1, paramObject3);
    } else {
      paramFragmentTransitionImpl = paramFragmentTransitionImpl.mergeTransitionsInSequence(paramObject2, paramObject1, paramObject3);
    }
    return paramFragmentTransitionImpl;
  }
  
  private static void replaceHide(FragmentTransitionImpl paramFragmentTransitionImpl, Object paramObject, Fragment paramFragment, ArrayList<View> paramArrayList)
  {
    if ((paramFragment != null) && (paramObject != null) && (paramFragment.mAdded) && (paramFragment.mHidden) && (paramFragment.mHiddenChanged))
    {
      paramFragment.setHideReplaced(true);
      paramFragmentTransitionImpl.scheduleHideFragmentView(paramObject, paramFragment.getView(), paramArrayList);
      OneShotPreDrawListener.add(paramFragment.mContainer, new Runnable()
      {
        public void run()
        {
          FragmentTransition.setViewVisibility(this.val$exitingViews, 4);
        }
      });
    }
  }
  
  private static FragmentTransitionImpl resolveSupportImpl()
  {
    try
    {
      FragmentTransitionImpl localFragmentTransitionImpl = (FragmentTransitionImpl)Class.forName("androidx.transition.FragmentTransitionSupport").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
      return localFragmentTransitionImpl;
    }
    catch (Exception localException) {}
    return null;
  }
  
  private static void retainValues(ArrayMap<String, String> paramArrayMap, ArrayMap<String, View> paramArrayMap1)
  {
    for (int i = paramArrayMap.size() - 1; i >= 0; i--) {
      if (!paramArrayMap1.containsKey((String)paramArrayMap.valueAt(i))) {
        paramArrayMap.removeAt(i);
      }
    }
  }
  
  private static void scheduleTargetChange(final FragmentTransitionImpl paramFragmentTransitionImpl, ViewGroup paramViewGroup, final Fragment paramFragment, final View paramView, final ArrayList<View> paramArrayList1, Object paramObject1, final ArrayList<View> paramArrayList2, final Object paramObject2, final ArrayList<View> paramArrayList3)
  {
    OneShotPreDrawListener.add(paramViewGroup, new Runnable()
    {
      public void run()
      {
        Object localObject = this.val$enterTransition;
        if (localObject != null)
        {
          paramFragmentTransitionImpl.removeTarget(localObject, paramView);
          localObject = FragmentTransition.configureEnteringExitingViews(paramFragmentTransitionImpl, this.val$enterTransition, paramFragment, paramArrayList1, paramView);
          paramArrayList2.addAll((Collection)localObject);
        }
        if (paramArrayList3 != null)
        {
          if (paramObject2 != null)
          {
            localObject = new ArrayList();
            ((ArrayList)localObject).add(paramView);
            paramFragmentTransitionImpl.replaceTargets(paramObject2, paramArrayList3, (ArrayList)localObject);
          }
          paramArrayList3.clear();
          paramArrayList3.add(paramView);
        }
      }
    });
  }
  
  private static void setOutEpicenter(FragmentTransitionImpl paramFragmentTransitionImpl, Object paramObject1, Object paramObject2, ArrayMap<String, View> paramArrayMap, boolean paramBoolean, BackStackRecord paramBackStackRecord)
  {
    if ((paramBackStackRecord.mSharedElementSourceNames != null) && (!paramBackStackRecord.mSharedElementSourceNames.isEmpty()))
    {
      if (paramBoolean) {
        paramBackStackRecord = (String)paramBackStackRecord.mSharedElementTargetNames.get(0);
      } else {
        paramBackStackRecord = (String)paramBackStackRecord.mSharedElementSourceNames.get(0);
      }
      paramArrayMap = (View)paramArrayMap.get(paramBackStackRecord);
      paramFragmentTransitionImpl.setEpicenter(paramObject1, paramArrayMap);
      if (paramObject2 != null) {
        paramFragmentTransitionImpl.setEpicenter(paramObject2, paramArrayMap);
      }
    }
  }
  
  static void setViewVisibility(ArrayList<View> paramArrayList, int paramInt)
  {
    if (paramArrayList == null) {
      return;
    }
    for (int i = paramArrayList.size() - 1; i >= 0; i--) {
      ((View)paramArrayList.get(i)).setVisibility(paramInt);
    }
  }
  
  static void startTransitions(FragmentManager paramFragmentManager, ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2, boolean paramBoolean, Callback paramCallback)
  {
    if (paramFragmentManager.mCurState < 1) {
      return;
    }
    SparseArray localSparseArray = new SparseArray();
    Object localObject;
    for (int i = paramInt1; i < paramInt2; i++)
    {
      localObject = (BackStackRecord)paramArrayList.get(i);
      if (((Boolean)paramArrayList1.get(i)).booleanValue()) {
        calculatePopFragments((BackStackRecord)localObject, localSparseArray, paramBoolean);
      } else {
        calculateFragments((BackStackRecord)localObject, localSparseArray, paramBoolean);
      }
    }
    if (localSparseArray.size() != 0)
    {
      View localView = new View(paramFragmentManager.mHost.getContext());
      int j = localSparseArray.size();
      for (i = 0; i < j; i++)
      {
        int k = localSparseArray.keyAt(i);
        ArrayMap localArrayMap = calculateNameOverrides(k, paramArrayList, paramArrayList1, paramInt1, paramInt2);
        localObject = (FragmentContainerTransition)localSparseArray.valueAt(i);
        if (paramBoolean) {
          configureTransitionsReordered(paramFragmentManager, k, (FragmentContainerTransition)localObject, localView, localArrayMap, paramCallback);
        } else {
          configureTransitionsOrdered(paramFragmentManager, k, (FragmentContainerTransition)localObject, localView, localArrayMap, paramCallback);
        }
      }
    }
  }
  
  static boolean supportsTransition()
  {
    boolean bool;
    if ((PLATFORM_IMPL == null) && (SUPPORT_IMPL == null)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  static abstract interface Callback
  {
    public abstract void onComplete(Fragment paramFragment, CancellationSignal paramCancellationSignal);
    
    public abstract void onStart(Fragment paramFragment, CancellationSignal paramCancellationSignal);
  }
  
  static class FragmentContainerTransition
  {
    public Fragment firstOut;
    public boolean firstOutIsPop;
    public BackStackRecord firstOutTransaction;
    public Fragment lastIn;
    public boolean lastInIsPop;
    public BackStackRecord lastInTransaction;
    
    FragmentContainerTransition() {}
  }
}

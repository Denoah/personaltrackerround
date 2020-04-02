package com.askgps.personaltrackercore.ui.camera;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Size;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraSelector.Builder;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageAnalysis.Analyzer;
import androidx.camera.core.ImageAnalysis.Builder;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCapture.Builder;
import androidx.camera.core.ImageCapture.OnImageSavedCallback;
import androidx.camera.core.ImageCapture.OutputFileOptions.Builder;
import androidx.camera.core.ImageCapture.OutputFileResults;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.Preview.Builder;
import androidx.camera.core.UseCase;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import com.askgps.personaltrackercore.BaseMainActivity;
import com.askgps.personaltrackercore.BaseMainActivity.Companion;
import com.askgps.personaltrackercore.R.drawable;
import com.askgps.personaltrackercore.R.id;
import com.askgps.personaltrackercore.R.layout;
import com.askgps.personaltrackercore.R.style;
import com.askgps.personaltrackercore.config.CustomerCategory;
import com.askgps.personaltrackercore.utils.FaceDetector.FaceDetectorAnalyzer;
import com.google.common.util.concurrent.ListenableFuture;
import com.hadilq.liveevent.LiveEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(bv={1, 0, 3}, d1={"\000?\001\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\b\n\000\n\002\020\021\n\002\020\016\n\000\n\002\020\025\n\002\b\007\030\000 62\0020\001:\0016B\005?\006\002\020\002J\032\020\007\032\0020\b2\006\020\t\032\0020\n2\b\020\013\032\004\030\0010\fH\002J\b\020\r\032\0020\016H\002J\b\020\017\032\0020\020H\002J\b\020\021\032\0020\bH\002J\b\020\022\032\0020\023H\002J\b\020\024\032\0020\bH\002J\b\020\025\032\0020\bH\002J\b\020\026\032\0020\027H\002J\b\020\030\032\0020\nH\002J\020\020\031\032\0020\0322\006\020\t\032\0020\nH\002J\b\020\033\032\0020\034H\002J\022\020\035\032\0020\b2\b\020\036\032\004\030\0010\037H\026J\022\020 \032\0020!2\b\020\036\032\004\030\0010\037H\026J&\020\"\032\004\030\0010#2\006\020$\032\0020%2\b\020&\032\004\030\0010'2\b\020\036\032\004\030\0010\037H\026J\b\020(\032\0020\bH\026J+\020)\032\0020\b2\006\020*\032\0020+2\f\020,\032\b\022\004\022\0020.0-2\006\020/\032\00200H\026?\006\002\0201J\b\0202\032\0020\bH\026J\b\0203\032\0020\bH\002J\b\0204\032\0020\bH\002J\b\0205\032\0020\bH\002R\016\020\003\032\0020\004X?.?\006\002\n\000R\016\020\005\032\0020\006X?.?\006\002\n\000?\0067"}, d2={"Lcom/askgps/personaltrackercore/ui/camera/CameraFragment;", "Landroidx/fragment/app/DialogFragment;", "()V", "avd", "Landroidx/vectordrawable/graphics/drawable/AnimatedVectorDrawableCompat;", "viewModel", "Lcom/askgps/personaltrackercore/ui/camera/CameraViewModel;", "FaceDetectorAnalyzerCalbback", "", "imageCapture", "Landroidx/camera/core/ImageCapture;", "image", "Landroidx/camera/core/ImageProxy;", "allPermissionsGranted", "", "createImageFile", "Ljava/io/File;", "disableAvd", "getFrameSize", "Landroid/util/Size;", "hideSystemUI", "initAvd", "initCameraSelector", "Landroidx/camera/core/CameraSelector;", "initCapture", "initImageAnalysis", "Landroidx/camera/core/ImageAnalysis;", "initPreview", "Landroidx/camera/core/Preview;", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onCreateDialog", "Landroid/app/Dialog;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onPause", "onRequestPermissionsResult", "requestCode", "", "permissions", "", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "onResume", "requestCameraPermission", "startCamera", "updateTransform", "Companion", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class CameraFragment
  extends DialogFragment
{
  public static final Companion Companion = new Companion(null);
  public static final int REQUEST_CAMERA_PERMISSION = 10;
  private static final String[] REQUIRED_PERMISSIONS = { "android.permission.CAMERA" };
  private HashMap _$_findViewCache;
  private AnimatedVectorDrawableCompat avd;
  private CameraViewModel viewModel;
  
  public CameraFragment() {}
  
  private final void FaceDetectorAnalyzerCalbback(ImageCapture paramImageCapture, final ImageProxy paramImageProxy)
  {
    final File localFile = createImageFile();
    paramImageCapture.takePicture(new ImageCapture.OutputFileOptions.Builder(localFile).build(), (Executor)Executors.newSingleThreadExecutor(), (ImageCapture.OnImageSavedCallback)new ImageCapture.OnImageSavedCallback()
    {
      public void onError(ImageCaptureException paramAnonymousImageCaptureException)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousImageCaptureException, "exception");
        paramAnonymousImageCaptureException = paramImageProxy;
        if (paramAnonymousImageCaptureException != null) {
          paramAnonymousImageCaptureException.close();
        }
      }
      
      public void onImageSaved(ImageCapture.OutputFileResults paramAnonymousOutputFileResults)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousOutputFileResults, "outputFileResults");
        CameraViewModel localCameraViewModel;
        if (BaseMainActivity.Companion.getCustomer() == CustomerCategory.BUILDER_WATCH)
        {
          localCameraViewModel = CameraFragment.access$getViewModel$p(this.this$0);
          paramAnonymousOutputFileResults = localFile.getAbsolutePath();
          Intrinsics.checkExpressionValueIsNotNull(paramAnonymousOutputFileResults, "file.absolutePath");
          paramAnonymousOutputFileResults = localCameraViewModel.searchUser(paramAnonymousOutputFileResults);
          if (paramAnonymousOutputFileResults != null)
          {
            this.this$0.dismiss();
            CameraFragment.access$getViewModel$p(this.this$0).startIdentificationAsync(paramAnonymousOutputFileResults);
          }
          else
          {
            paramAnonymousOutputFileResults = paramImageProxy;
            if (paramAnonymousOutputFileResults != null) {
              paramAnonymousOutputFileResults.close();
            }
          }
        }
        else
        {
          this.this$0.dismiss();
          localCameraViewModel = CameraFragment.access$getViewModel$p(this.this$0);
          paramAnonymousOutputFileResults = localFile.getAbsolutePath();
          Intrinsics.checkExpressionValueIsNotNull(paramAnonymousOutputFileResults, "file.absolutePath");
          localCameraViewModel.startIdentificationOnNavigationAsync(paramAnonymousOutputFileResults);
        }
      }
    });
  }
  
  private final boolean allPermissionsGranted()
  {
    String[] arrayOfString = REQUIRED_PERMISSIONS;
    int i = arrayOfString.length;
    boolean bool = false;
    for (int j = 0;; j++)
    {
      int k = 1;
      if (j >= i) {
        break;
      }
      String str = arrayOfString[j];
      Context localContext = getContext();
      if (localContext == null) {
        Intrinsics.throwNpe();
      }
      if (ContextCompat.checkSelfPermission(localContext, str) != 0) {
        k = 0;
      }
      if (k == 0) {
        return bool;
      }
    }
    bool = true;
    return bool;
  }
  
  private final File createImageFile()
    throws IOException
  {
    Object localObject1 = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "SimpleDateFormat(\"yyyyMMdd_HHmmss\").format(Date())");
    Object localObject2 = getContext();
    if (localObject2 == null) {
      Intrinsics.throwNpe();
    }
    File localFile = ((Context)localObject2).getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    Intrinsics.checkExpressionValueIsNotNull(localFile, "context!!.getExternalFil…nment.DIRECTORY_PICTURES)");
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("photo_");
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append('_');
    localObject1 = File.createTempFile(((StringBuilder)localObject2).toString(), ".jpg", localFile);
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "File.createTempFile(\n   …/* directory */\n        )");
    return localObject1;
  }
  
  private final void disableAvd()
  {
    AnimatedVectorDrawableCompat localAnimatedVectorDrawableCompat = this.avd;
    if (localAnimatedVectorDrawableCompat == null) {
      Intrinsics.throwUninitializedPropertyAccessException("avd");
    }
    localAnimatedVectorDrawableCompat.clearAnimationCallbacks();
    localAnimatedVectorDrawableCompat = this.avd;
    if (localAnimatedVectorDrawableCompat == null) {
      Intrinsics.throwUninitializedPropertyAccessException("avd");
    }
    localAnimatedVectorDrawableCompat.stop();
  }
  
  private final Size getFrameSize()
  {
    PreviewView localPreviewView = (PreviewView)_$_findCachedViewById(R.id.camera_preview);
    Intrinsics.checkExpressionValueIsNotNull(localPreviewView, "camera_preview");
    int i = Math.min(localPreviewView.getWidth(), 480);
    localPreviewView = (PreviewView)_$_findCachedViewById(R.id.camera_preview);
    Intrinsics.checkExpressionValueIsNotNull(localPreviewView, "camera_preview");
    return new Size(i, Math.min(localPreviewView.getHeight(), 360));
  }
  
  private final void hideSystemUI()
  {
    Object localObject = getDialog();
    if (localObject != null)
    {
      localObject = ((Dialog)localObject).getWindow();
      if (localObject != null)
      {
        localObject = ((Window)localObject).getDecorView();
        if (localObject != null) {
          ((View)localObject).setSystemUiVisibility(3846);
        }
      }
    }
  }
  
  private final void initAvd()
  {
    Object localObject = getContext();
    if (localObject == null) {
      Intrinsics.throwNpe();
    }
    localObject = AnimatedVectorDrawableCompat.create((Context)localObject, R.drawable.face_detect);
    if (localObject == null) {
      Intrinsics.throwNpe();
    }
    this.avd = ((AnimatedVectorDrawableCompat)localObject);
    localObject = (AppCompatImageView)_$_findCachedViewById(R.id.camera_face_detector_animation);
    AnimatedVectorDrawableCompat localAnimatedVectorDrawableCompat = this.avd;
    if (localAnimatedVectorDrawableCompat == null) {
      Intrinsics.throwUninitializedPropertyAccessException("avd");
    }
    ((AppCompatImageView)localObject).setImageDrawable((Drawable)localAnimatedVectorDrawableCompat);
    localObject = this.avd;
    if (localObject == null) {
      Intrinsics.throwUninitializedPropertyAccessException("avd");
    }
    ((AnimatedVectorDrawableCompat)localObject).registerAnimationCallback((Animatable2Compat.AnimationCallback)new Animatable2Compat.AnimationCallback()
    {
      public void onAnimationEnd(Drawable paramAnonymousDrawable)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousDrawable, "drawable");
        ((AppCompatImageView)this.this$0._$_findCachedViewById(R.id.camera_face_detector_animation)).post((Runnable)new Runnable()
        {
          public final void run()
          {
            CameraFragment.access$getAvd$p(this.this$0.this$0).start();
          }
        });
      }
    });
    localObject = this.avd;
    if (localObject == null) {
      Intrinsics.throwUninitializedPropertyAccessException("avd");
    }
    ((AnimatedVectorDrawableCompat)localObject).start();
  }
  
  private final CameraSelector initCameraSelector()
  {
    Object localObject = new CameraSelector.Builder();
    int i;
    if (BaseMainActivity.Companion.getCustomer() == CustomerCategory.PATIENT_PHONE) {
      i = 0;
    } else {
      i = 1;
    }
    localObject = ((CameraSelector.Builder)localObject).requireLensFacing(i).build();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "CameraSelector.Builder()…CK))\n            .build()");
    return localObject;
  }
  
  private final ImageCapture initCapture()
  {
    final ImageCapture localImageCapture = new ImageCapture.Builder().setTargetResolution(getFrameSize()).setCaptureMode(1).build();
    Intrinsics.checkExpressionValueIsNotNull(localImageCapture, "ImageCapture.Builder()\n …NCY)\n            .build()");
    ((AppCompatButton)_$_findCachedViewById(R.id.camera_btn_send)).setOnClickListener((View.OnClickListener)new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        CameraFragment.access$FaceDetectorAnalyzerCalbback(this.this$0, localImageCapture, null);
      }
    });
    return localImageCapture;
  }
  
  private final ImageAnalysis initImageAnalysis(final ImageCapture paramImageCapture)
  {
    ImageAnalysis localImageAnalysis = new ImageAnalysis.Builder().setTargetResolution(getFrameSize()).setBackpressureStrategy(0).build();
    Intrinsics.checkExpressionValueIsNotNull(localImageAnalysis, "ImageAnalysis.Builder()\n…EST)\n            .build()");
    localImageAnalysis.setAnalyzer((Executor)Executors.newSingleThreadExecutor(), (ImageAnalysis.Analyzer)new FaceDetector.FaceDetectorAnalyzer((Function1)new Lambda(paramImageCapture)
    {
      public final void invoke(ImageProxy paramAnonymousImageProxy)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousImageProxy, "image");
        CameraFragment.access$FaceDetectorAnalyzerCalbback(this.this$0, paramImageCapture, paramAnonymousImageProxy);
      }
    }));
    return localImageAnalysis;
  }
  
  private final Preview initPreview()
  {
    Object localObject1 = new Preview.Builder().setTargetName("Preview").setTargetResolution(getFrameSize());
    Object localObject2 = getActivity();
    if (localObject2 == null) {
      Intrinsics.throwNpe();
    }
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "activity!!");
    localObject2 = ((FragmentActivity)localObject2).getWindowManager();
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "activity!!.windowManager");
    localObject2 = ((WindowManager)localObject2).getDefaultDisplay();
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "activity!!.windowManager.defaultDisplay");
    localObject2 = ((Preview.Builder)localObject1).setTargetRotation(((Display)localObject2).getRotation()).build();
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "Preview.Builder()\n      …ion)\n            .build()");
    localObject1 = (PreviewView)_$_findCachedViewById(R.id.camera_preview);
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "camera_preview");
    ((Preview)localObject2).setSurfaceProvider(((PreviewView)localObject1).getPreviewSurfaceProvider());
    return localObject2;
  }
  
  private final void requestCameraPermission()
  {
    FragmentActivity localFragmentActivity = getActivity();
    if (localFragmentActivity == null) {
      Intrinsics.throwNpe();
    }
    ActivityCompat.requestPermissions((Activity)localFragmentActivity, REQUIRED_PERMISSIONS, 10);
  }
  
  private final void startCamera()
  {
    Object localObject = getContext();
    if (localObject == null) {
      Intrinsics.throwNpe();
    }
    Intrinsics.checkExpressionValueIsNotNull(localObject, "context!!");
    final ListenableFuture localListenableFuture = ProcessCameraProvider.getInstance(((Context)localObject).getApplicationContext());
    Intrinsics.checkExpressionValueIsNotNull(localListenableFuture, "ProcessCameraProvider.ge…ext!!.applicationContext)");
    localObject = (Runnable)new Runnable()
    {
      public final void run()
      {
        ProcessCameraProvider localProcessCameraProvider = (ProcessCameraProvider)localListenableFuture.get();
        ImageCapture localImageCapture = CameraFragment.access$initCapture(this.this$0);
        ImageAnalysis localImageAnalysis = CameraFragment.access$initImageAnalysis(this.this$0, localImageCapture);
        Preview localPreview = CameraFragment.access$initPreview(this.this$0);
        CameraSelector localCameraSelector = CameraFragment.access$initCameraSelector(this.this$0);
        localProcessCameraProvider.bindToLifecycle(this.this$0.getViewLifecycleOwner(), localCameraSelector, new UseCase[] { (UseCase)localImageCapture, (UseCase)localPreview, (UseCase)localImageAnalysis });
      }
    };
    Context localContext = getContext();
    if (localContext == null) {
      Intrinsics.throwNpe();
    }
    localListenableFuture.addListener((Runnable)localObject, ContextCompat.getMainExecutor(localContext));
  }
  
  private final void updateTransform()
  {
    Matrix localMatrix = new Matrix();
    Object localObject = (PreviewView)_$_findCachedViewById(R.id.camera_preview);
    Intrinsics.checkExpressionValueIsNotNull(localObject, "camera_preview");
    float f1 = ((PreviewView)localObject).getWidth() / 2.0F;
    localObject = (PreviewView)_$_findCachedViewById(R.id.camera_preview);
    Intrinsics.checkExpressionValueIsNotNull(localObject, "camera_preview");
    float f2 = ((PreviewView)localObject).getHeight() / 2.0F;
    localObject = (PreviewView)_$_findCachedViewById(R.id.camera_preview);
    Intrinsics.checkExpressionValueIsNotNull(localObject, "camera_preview");
    localObject = ((PreviewView)localObject).getDisplay();
    Intrinsics.checkExpressionValueIsNotNull(localObject, "camera_preview.display");
    int i = ((Display)localObject).getRotation();
    if (i != 0)
    {
      if (i != 1)
      {
        if (i != 2)
        {
          if (i != 3) {
            return;
          }
          i = 270;
        }
        else
        {
          i = 180;
        }
      }
      else {
        i = 90;
      }
    }
    else {
      i = 0;
    }
    localMatrix.postRotate(-i, f1, f2);
    localObject = (PreviewView)_$_findCachedViewById(R.id.camera_preview);
    Intrinsics.checkExpressionValueIsNotNull(localObject, "camera_preview");
    float f3 = ((PreviewView)localObject).getHeight();
    localObject = (PreviewView)_$_findCachedViewById(R.id.camera_preview);
    Intrinsics.checkExpressionValueIsNotNull(localObject, "camera_preview");
    float f4 = f3 / ((PreviewView)localObject).getWidth();
    localObject = (PreviewView)_$_findCachedViewById(R.id.camera_preview);
    Intrinsics.checkExpressionValueIsNotNull(localObject, "camera_preview");
    f3 = ((PreviewView)localObject).getWidth();
    localObject = (PreviewView)_$_findCachedViewById(R.id.camera_preview);
    Intrinsics.checkExpressionValueIsNotNull(localObject, "camera_preview");
    localMatrix.postScale(f4, f3 / ((PreviewView)localObject).getWidth(), f1, f2);
  }
  
  public void _$_clearFindViewByIdCache()
  {
    HashMap localHashMap = this._$_findViewCache;
    if (localHashMap != null) {
      localHashMap.clear();
    }
  }
  
  public View _$_findCachedViewById(int paramInt)
  {
    if (this._$_findViewCache == null) {
      this._$_findViewCache = new HashMap();
    }
    View localView1 = (View)this._$_findViewCache.get(Integer.valueOf(paramInt));
    View localView2 = localView1;
    if (localView1 == null)
    {
      localView2 = getView();
      if (localView2 == null) {
        return null;
      }
      localView2 = localView2.findViewById(paramInt);
      this._$_findViewCache.put(Integer.valueOf(paramInt), localView2);
    }
    return localView2;
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    paramBundle = ViewModelProviders.of((Fragment)this).get(CameraViewModel.class);
    Intrinsics.checkExpressionValueIsNotNull(paramBundle, "ViewModelProviders.of(th…eraViewModel::class.java)");
    this.viewModel = ((CameraViewModel)paramBundle);
    if (BaseMainActivity.Companion.getCustomer() == CustomerCategory.BUILDER_WATCH)
    {
      paramBundle = this.viewModel;
      if (paramBundle == null) {
        Intrinsics.throwUninitializedPropertyAccessException("viewModel");
      }
      LiveEvent localLiveEvent = paramBundle.getIdxId();
      paramBundle = getViewLifecycleOwner();
      Intrinsics.checkExpressionValueIsNotNull(paramBundle, "viewLifecycleOwner");
      localLiveEvent.observe(paramBundle, (Observer)new Observer()
      {
        public final void onChanged(String paramAnonymousString)
        {
          this.this$0.dismiss();
        }
      });
    }
    if (allPermissionsGranted()) {
      ((PreviewView)_$_findCachedViewById(R.id.camera_preview)).post((Runnable)new Runnable()
      {
        public final void run()
        {
          CameraFragment.access$startCamera(this.this$0);
        }
      });
    } else {
      requestCameraPermission();
    }
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    paramBundle = getContext();
    if (paramBundle == null) {
      Intrinsics.throwNpe();
    }
    return new Dialog(paramBundle, R.style.FullScreenDialog);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    Intrinsics.checkParameterIsNotNull(paramLayoutInflater, "inflater");
    return paramLayoutInflater.inflate(R.layout.camera_fragment, paramViewGroup, false);
  }
  
  public void onPause()
  {
    super.onPause();
    disableAvd();
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfInt)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfString, "permissions");
    Intrinsics.checkParameterIsNotNull(paramArrayOfInt, "grantResults");
    if (paramInt == 10) {
      if (allPermissionsGranted())
      {
        ((PreviewView)_$_findCachedViewById(R.id.camera_preview)).post((Runnable)new Runnable()
        {
          public final void run()
          {
            CameraFragment.access$startCamera(this.this$0);
          }
        });
      }
      else
      {
        Toast.makeText(getContext(), (CharSequence)"Permissions not granted by the user.", 0).show();
        dismiss();
      }
    }
  }
  
  public void onResume()
  {
    super.onResume();
    hideSystemUI();
    initAvd();
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\000\n\002\020\021\n\002\020\016\n\002\b\004\n\002\030\002\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\006\020\013\032\0020\fR\016\020\003\032\0020\004X?T?\006\002\n\000R\031\020\005\032\b\022\004\022\0020\0070\006?\006\n\n\002\020\n\032\004\b\b\020\t?\006\r"}, d2={"Lcom/askgps/personaltrackercore/ui/camera/CameraFragment$Companion;", "", "()V", "REQUEST_CAMERA_PERMISSION", "", "REQUIRED_PERMISSIONS", "", "", "getREQUIRED_PERMISSIONS", "()[Ljava/lang/String;", "[Ljava/lang/String;", "newInstance", "Lcom/askgps/personaltrackercore/ui/camera/CameraFragment;", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final String[] getREQUIRED_PERMISSIONS()
    {
      return CameraFragment.access$getREQUIRED_PERMISSIONS$cp();
    }
    
    public final CameraFragment newInstance()
    {
      return new CameraFragment();
    }
  }
}

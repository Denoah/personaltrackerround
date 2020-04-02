package com.askgps.personaltrackercore.utils;

import android.media.Image;
import androidx.camera.core.ImageAnalysis.Analyzer;
import androidx.camera.core.ImageInfo;
import androidx.camera.core.ImageProxy;
import com.askgps.personaltrackercore.LogKt;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions.Builder;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.KProperty;

@Metadata(bv={1, 0, 3}, d1={"\000\f\n\002\030\002\n\002\020\000\n\002\b\004\030\000 \0032\0020\001:\002\003\004B\005?\006\002\020\002?\006\005"}, d2={"Lcom/askgps/personaltrackercore/utils/FaceDetector;", "", "()V", "Companion", "FaceDetectorAnalyzer", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class FaceDetector
{
  public static final Companion Companion = new Companion(null);
  private static final FirebaseVisionFaceDetectorOptions options;
  
  static
  {
    FirebaseVisionFaceDetectorOptions localFirebaseVisionFaceDetectorOptions = new FirebaseVisionFaceDetectorOptions.Builder().setContourMode(2).build();
    Intrinsics.checkExpressionValueIsNotNull(localFirebaseVisionFaceDetectorOptions, "FirebaseVisionFaceDetect…URS)\n            .build()");
    options = localFirebaseVisionFaceDetectorOptions;
  }
  
  public FaceDetector() {}
  
  @Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\021\020\003\032\0020\004?\006\b\n\000\032\004\b\005\020\006?\006\007"}, d2={"Lcom/askgps/personaltrackercore/utils/FaceDetector$Companion;", "", "()V", "options", "Lcom/google/firebase/ml/vision/face/FirebaseVisionFaceDetectorOptions;", "getOptions", "()Lcom/google/firebase/ml/vision/face/FirebaseVisionFaceDetectorOptions;", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final FirebaseVisionFaceDetectorOptions getOptions()
    {
      return FaceDetector.access$getOptions$cp();
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\006\n\002\020\b\n\002\b\002\b\000\030\0002\0020\001B(\022!\020\002\032\035\022\023\022\0210\004?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\003?\006\002\020\tJ\020\020\020\032\0020\b2\006\020\007\032\0020\004H\027J\020\020\021\032\0020\0222\006\020\023\032\0020\022H\002R)\020\002\032\035\022\023\022\0210\004?\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\003X?\004?\006\002\n\000R\033\020\n\032\0020\0138BX??\002?\006\f\n\004\b\016\020\017\032\004\b\f\020\r?\006\024"}, d2={"Lcom/askgps/personaltrackercore/utils/FaceDetector$FaceDetectorAnalyzer;", "Landroidx/camera/core/ImageAnalysis$Analyzer;", "OnSuccessListener", "Lkotlin/Function1;", "Landroidx/camera/core/ImageProxy;", "Lkotlin/ParameterName;", "name", "image", "", "(Lkotlin/jvm/functions/Function1;)V", "faceDetector", "Lcom/google/firebase/ml/vision/face/FirebaseVisionFaceDetector;", "getFaceDetector", "()Lcom/google/firebase/ml/vision/face/FirebaseVisionFaceDetector;", "faceDetector$delegate", "Lkotlin/Lazy;", "analyze", "degreesToFirebaseRotation", "", "degrees", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
  public static final class FaceDetectorAnalyzer
    implements ImageAnalysis.Analyzer
  {
    private final Function1<ImageProxy, Unit> OnSuccessListener;
    private final Lazy faceDetector$delegate;
    
    public FaceDetectorAnalyzer(Function1<? super ImageProxy, Unit> paramFunction1)
    {
      this.OnSuccessListener = paramFunction1;
      this.faceDetector$delegate = LazyKt.lazy((Function0)faceDetector.2.INSTANCE);
    }
    
    private final int degreesToFirebaseRotation(int paramInt)
    {
      if (paramInt != 0)
      {
        if (paramInt != 90)
        {
          if (paramInt != 180)
          {
            if (paramInt == 270) {
              paramInt = 3;
            } else {
              throw ((Throwable)new Exception("Rotation must be 0, 90, 180, or 270."));
            }
          }
          else {
            paramInt = 2;
          }
        }
        else {
          paramInt = 1;
        }
      }
      else {
        paramInt = 0;
      }
      return paramInt;
    }
    
    private final FirebaseVisionFaceDetector getFaceDetector()
    {
      Lazy localLazy = this.faceDetector$delegate;
      KProperty localKProperty = $$delegatedProperties[0];
      return (FirebaseVisionFaceDetector)localLazy.getValue();
    }
    
    public void analyze(final ImageProxy paramImageProxy)
    {
      Intrinsics.checkParameterIsNotNull(paramImageProxy, "image");
      Object localObject = paramImageProxy.getImage();
      ImageInfo localImageInfo = paramImageProxy.getImageInfo();
      Intrinsics.checkExpressionValueIsNotNull(localImageInfo, "image.imageInfo");
      int i = degreesToFirebaseRotation(localImageInfo.getRotationDegrees());
      if (localObject != null)
      {
        localObject = FirebaseVisionImage.fromMediaImage((Image)localObject, i);
        Intrinsics.checkExpressionValueIsNotNull(localObject, "FirebaseVisionImage.from…ediaImage, imageRotation)");
        getFaceDetector().detectInImage((FirebaseVisionImage)localObject).addOnSuccessListener((OnSuccessListener)new OnSuccessListener()
        {
          public final void onSuccess(List<FirebaseVisionFace> paramAnonymousList)
          {
            Intrinsics.checkExpressionValueIsNotNull(paramAnonymousList, "faces");
            LogKt.toLog$default((Iterable)paramAnonymousList, "face detected", null, null, 6, null);
            if (paramAnonymousList.size() > 0) {
              FaceDetector.FaceDetectorAnalyzer.access$getOnSuccessListener$p(this.this$0).invoke(paramImageProxy);
            } else {
              paramImageProxy.close();
            }
          }
        }).addOnFailureListener((OnFailureListener)new OnFailureListener()
        {
          public final void onFailure(Exception paramAnonymousException)
          {
            Intrinsics.checkParameterIsNotNull(paramAnonymousException, "e");
            LogKt.toLog$default((Throwable)paramAnonymousException, "error face detected", null, null, 6, null);
            this.$image.close();
          }
        });
      }
    }
  }
}

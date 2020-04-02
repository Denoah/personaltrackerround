package kotlin.reflect.jvm.internal.impl.types.model;

public enum CaptureStatus
{
  static
  {
    CaptureStatus localCaptureStatus1 = new CaptureStatus("FOR_SUBTYPING", 0);
    FOR_SUBTYPING = localCaptureStatus1;
    CaptureStatus localCaptureStatus2 = new CaptureStatus("FOR_INCORPORATION", 1);
    FOR_INCORPORATION = localCaptureStatus2;
    CaptureStatus localCaptureStatus3 = new CaptureStatus("FROM_EXPRESSION", 2);
    FROM_EXPRESSION = localCaptureStatus3;
    $VALUES = new CaptureStatus[] { localCaptureStatus1, localCaptureStatus2, localCaptureStatus3 };
  }
  
  private CaptureStatus() {}
}

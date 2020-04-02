package androidx.camera.extensions;

public abstract interface ExtensionsErrorListener
{
  public abstract void onError(ExtensionsErrorCode paramExtensionsErrorCode);
  
  public static enum ExtensionsErrorCode
  {
    static
    {
      PREVIEW_EXTENSION_REQUIRED = new ExtensionsErrorCode("PREVIEW_EXTENSION_REQUIRED", 1);
      IMAGE_CAPTURE_EXTENSION_REQUIRED = new ExtensionsErrorCode("IMAGE_CAPTURE_EXTENSION_REQUIRED", 2);
      ExtensionsErrorCode localExtensionsErrorCode = new ExtensionsErrorCode("MISMATCHED_EXTENSIONS_ENABLED", 3);
      MISMATCHED_EXTENSIONS_ENABLED = localExtensionsErrorCode;
      $VALUES = new ExtensionsErrorCode[] { UNKNOWN, PREVIEW_EXTENSION_REQUIRED, IMAGE_CAPTURE_EXTENSION_REQUIRED, localExtensionsErrorCode };
    }
    
    private ExtensionsErrorCode() {}
  }
}

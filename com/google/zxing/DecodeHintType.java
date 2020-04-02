package com.google.zxing;

import java.util.List;

public enum DecodeHintType
{
  private final Class<?> valueType;
  
  static
  {
    POSSIBLE_FORMATS = new DecodeHintType("POSSIBLE_FORMATS", 2, List.class);
    TRY_HARDER = new DecodeHintType("TRY_HARDER", 3, Void.class);
    CHARACTER_SET = new DecodeHintType("CHARACTER_SET", 4, String.class);
    ALLOWED_LENGTHS = new DecodeHintType("ALLOWED_LENGTHS", 5, [I.class);
    ASSUME_CODE_39_CHECK_DIGIT = new DecodeHintType("ASSUME_CODE_39_CHECK_DIGIT", 6, Void.class);
    ASSUME_GS1 = new DecodeHintType("ASSUME_GS1", 7, Void.class);
    RETURN_CODABAR_START_END = new DecodeHintType("RETURN_CODABAR_START_END", 8, Void.class);
    NEED_RESULT_POINT_CALLBACK = new DecodeHintType("NEED_RESULT_POINT_CALLBACK", 9, ResultPointCallback.class);
    DecodeHintType localDecodeHintType = new DecodeHintType("ALLOWED_EAN_EXTENSIONS", 10, [I.class);
    ALLOWED_EAN_EXTENSIONS = localDecodeHintType;
    $VALUES = new DecodeHintType[] { OTHER, PURE_BARCODE, POSSIBLE_FORMATS, TRY_HARDER, CHARACTER_SET, ALLOWED_LENGTHS, ASSUME_CODE_39_CHECK_DIGIT, ASSUME_GS1, RETURN_CODABAR_START_END, NEED_RESULT_POINT_CALLBACK, localDecodeHintType };
  }
  
  private DecodeHintType(Class<?> paramClass)
  {
    this.valueType = paramClass;
  }
  
  public Class<?> getValueType()
  {
    return this.valueType;
  }
}

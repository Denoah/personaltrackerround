package com.google.zxing;

public enum EncodeHintType
{
  static
  {
    CHARACTER_SET = new EncodeHintType("CHARACTER_SET", 1);
    DATA_MATRIX_SHAPE = new EncodeHintType("DATA_MATRIX_SHAPE", 2);
    MIN_SIZE = new EncodeHintType("MIN_SIZE", 3);
    MAX_SIZE = new EncodeHintType("MAX_SIZE", 4);
    MARGIN = new EncodeHintType("MARGIN", 5);
    PDF417_COMPACT = new EncodeHintType("PDF417_COMPACT", 6);
    PDF417_COMPACTION = new EncodeHintType("PDF417_COMPACTION", 7);
    PDF417_DIMENSIONS = new EncodeHintType("PDF417_DIMENSIONS", 8);
    EncodeHintType localEncodeHintType = new EncodeHintType("AZTEC_LAYERS", 9);
    AZTEC_LAYERS = localEncodeHintType;
    $VALUES = new EncodeHintType[] { ERROR_CORRECTION, CHARACTER_SET, DATA_MATRIX_SHAPE, MIN_SIZE, MAX_SIZE, MARGIN, PDF417_COMPACT, PDF417_COMPACTION, PDF417_DIMENSIONS, localEncodeHintType };
  }
  
  private EncodeHintType() {}
}

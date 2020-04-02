package com.google.zxing;

public enum ResultMetadataType
{
  static
  {
    ORIENTATION = new ResultMetadataType("ORIENTATION", 1);
    BYTE_SEGMENTS = new ResultMetadataType("BYTE_SEGMENTS", 2);
    ERROR_CORRECTION_LEVEL = new ResultMetadataType("ERROR_CORRECTION_LEVEL", 3);
    ISSUE_NUMBER = new ResultMetadataType("ISSUE_NUMBER", 4);
    SUGGESTED_PRICE = new ResultMetadataType("SUGGESTED_PRICE", 5);
    POSSIBLE_COUNTRY = new ResultMetadataType("POSSIBLE_COUNTRY", 6);
    UPC_EAN_EXTENSION = new ResultMetadataType("UPC_EAN_EXTENSION", 7);
    PDF417_EXTRA_METADATA = new ResultMetadataType("PDF417_EXTRA_METADATA", 8);
    STRUCTURED_APPEND_SEQUENCE = new ResultMetadataType("STRUCTURED_APPEND_SEQUENCE", 9);
    ResultMetadataType localResultMetadataType = new ResultMetadataType("STRUCTURED_APPEND_PARITY", 10);
    STRUCTURED_APPEND_PARITY = localResultMetadataType;
    $VALUES = new ResultMetadataType[] { OTHER, ORIENTATION, BYTE_SEGMENTS, ERROR_CORRECTION_LEVEL, ISSUE_NUMBER, SUGGESTED_PRICE, POSSIBLE_COUNTRY, UPC_EAN_EXTENSION, PDF417_EXTRA_METADATA, STRUCTURED_APPEND_SEQUENCE, localResultMetadataType };
  }
  
  private ResultMetadataType() {}
}

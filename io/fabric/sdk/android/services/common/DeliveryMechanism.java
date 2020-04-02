package io.fabric.sdk.android.services.common;

public enum DeliveryMechanism
{
  public static final String BETA_APP_PACKAGE_NAME = "io.crash.air";
  private final int id;
  
  static
  {
    TEST_DISTRIBUTION = new DeliveryMechanism("TEST_DISTRIBUTION", 2, 3);
    DeliveryMechanism localDeliveryMechanism = new DeliveryMechanism("APP_STORE", 3, 4);
    APP_STORE = localDeliveryMechanism;
    $VALUES = new DeliveryMechanism[] { DEVELOPER, USER_SIDELOAD, TEST_DISTRIBUTION, localDeliveryMechanism };
  }
  
  private DeliveryMechanism(int paramInt)
  {
    this.id = paramInt;
  }
  
  public static DeliveryMechanism determineFrom(String paramString)
  {
    if ("io.crash.air".equals(paramString)) {
      return TEST_DISTRIBUTION;
    }
    if (paramString != null) {
      return APP_STORE;
    }
    return DEVELOPER;
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public String toString()
  {
    return Integer.toString(this.id);
  }
}

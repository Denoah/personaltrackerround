package androidx.work;

public enum NetworkType
{
  static
  {
    CONNECTED = new NetworkType("CONNECTED", 1);
    UNMETERED = new NetworkType("UNMETERED", 2);
    NOT_ROAMING = new NetworkType("NOT_ROAMING", 3);
    NetworkType localNetworkType = new NetworkType("METERED", 4);
    METERED = localNetworkType;
    $VALUES = new NetworkType[] { NOT_REQUIRED, CONNECTED, UNMETERED, NOT_ROAMING, localNetworkType };
  }
  
  private NetworkType() {}
}

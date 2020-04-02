package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

public enum KotlinRetention
{
  static
  {
    KotlinRetention localKotlinRetention1 = new KotlinRetention("RUNTIME", 0);
    RUNTIME = localKotlinRetention1;
    KotlinRetention localKotlinRetention2 = new KotlinRetention("BINARY", 1);
    BINARY = localKotlinRetention2;
    KotlinRetention localKotlinRetention3 = new KotlinRetention("SOURCE", 2);
    SOURCE = localKotlinRetention3;
    $VALUES = new KotlinRetention[] { localKotlinRetention1, localKotlinRetention2, localKotlinRetention3 };
  }
  
  private KotlinRetention() {}
}

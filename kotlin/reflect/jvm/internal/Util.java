package kotlin.reflect.jvm.internal;

class Util
{
  public static Object getEnumConstantByName(Class<? extends Enum<?>> paramClass, String paramString)
  {
    return Enum.valueOf(paramClass, paramString);
  }
}

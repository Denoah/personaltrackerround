package kotlin.jvm.internal;

import java.util.Arrays;
import kotlin.KotlinNullPointerException;
import kotlin.UninitializedPropertyAccessException;

public class Intrinsics
{
  private Intrinsics() {}
  
  public static boolean areEqual(double paramDouble, Double paramDouble1)
  {
    boolean bool;
    if ((paramDouble1 != null) && (paramDouble == paramDouble1.doubleValue())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean areEqual(float paramFloat, Float paramFloat1)
  {
    boolean bool;
    if ((paramFloat1 != null) && (paramFloat == paramFloat1.floatValue())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean areEqual(Double paramDouble, double paramDouble1)
  {
    boolean bool;
    if ((paramDouble != null) && (paramDouble.doubleValue() == paramDouble1)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean areEqual(Double paramDouble1, Double paramDouble2)
  {
    boolean bool = true;
    if (paramDouble1 == null ? paramDouble2 == null : (paramDouble2 == null) || (paramDouble1.doubleValue() != paramDouble2.doubleValue())) {
      bool = false;
    }
    return bool;
  }
  
  public static boolean areEqual(Float paramFloat, float paramFloat1)
  {
    boolean bool;
    if ((paramFloat != null) && (paramFloat.floatValue() == paramFloat1)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean areEqual(Float paramFloat1, Float paramFloat2)
  {
    boolean bool = true;
    if (paramFloat1 == null ? paramFloat2 == null : (paramFloat2 == null) || (paramFloat1.floatValue() != paramFloat2.floatValue())) {
      bool = false;
    }
    return bool;
  }
  
  public static boolean areEqual(Object paramObject1, Object paramObject2)
  {
    boolean bool;
    if (paramObject1 == null)
    {
      if (paramObject2 == null) {
        bool = true;
      } else {
        bool = false;
      }
    }
    else {
      bool = paramObject1.equals(paramObject2);
    }
    return bool;
  }
  
  public static void checkExpressionValueIsNotNull(Object paramObject, String paramString)
  {
    if (paramObject != null) {
      return;
    }
    paramObject = new StringBuilder();
    paramObject.append(paramString);
    paramObject.append(" must not be null");
    throw ((IllegalStateException)sanitizeStackTrace(new IllegalStateException(paramObject.toString())));
  }
  
  public static void checkFieldIsNotNull(Object paramObject, String paramString)
  {
    if (paramObject != null) {
      return;
    }
    throw ((IllegalStateException)sanitizeStackTrace(new IllegalStateException(paramString)));
  }
  
  public static void checkFieldIsNotNull(Object paramObject, String paramString1, String paramString2)
  {
    if (paramObject != null) {
      return;
    }
    paramObject = new StringBuilder();
    paramObject.append("Field specified as non-null is null: ");
    paramObject.append(paramString1);
    paramObject.append(".");
    paramObject.append(paramString2);
    throw ((IllegalStateException)sanitizeStackTrace(new IllegalStateException(paramObject.toString())));
  }
  
  public static void checkHasClass(String paramString)
    throws ClassNotFoundException
  {
    String str = paramString.replace('/', '.');
    try
    {
      Class.forName(str);
      return;
    }
    catch (ClassNotFoundException paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Class ");
      localStringBuilder.append(str);
      localStringBuilder.append(" is not found. Please update the Kotlin runtime to the latest version");
      throw ((ClassNotFoundException)sanitizeStackTrace(new ClassNotFoundException(localStringBuilder.toString(), paramString)));
    }
  }
  
  public static void checkHasClass(String paramString1, String paramString2)
    throws ClassNotFoundException
  {
    String str = paramString1.replace('/', '.');
    try
    {
      Class.forName(str);
      return;
    }
    catch (ClassNotFoundException paramString1)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Class ");
      localStringBuilder.append(str);
      localStringBuilder.append(" is not found: this code requires the Kotlin runtime of version at least ");
      localStringBuilder.append(paramString2);
      throw ((ClassNotFoundException)sanitizeStackTrace(new ClassNotFoundException(localStringBuilder.toString(), paramString1)));
    }
  }
  
  public static void checkNotNull(Object paramObject)
  {
    if (paramObject == null) {
      throwJavaNpe();
    }
  }
  
  public static void checkNotNull(Object paramObject, String paramString)
  {
    if (paramObject == null) {
      throwJavaNpe(paramString);
    }
  }
  
  public static void checkNotNullExpressionValue(Object paramObject, String paramString)
  {
    if (paramObject != null) {
      return;
    }
    paramObject = new StringBuilder();
    paramObject.append(paramString);
    paramObject.append(" must not be null");
    throw ((NullPointerException)sanitizeStackTrace(new NullPointerException(paramObject.toString())));
  }
  
  public static void checkNotNullParameter(Object paramObject, String paramString)
  {
    if (paramObject != null) {
      return;
    }
    throw ((NullPointerException)sanitizeStackTrace(new NullPointerException(paramString)));
  }
  
  public static void checkParameterIsNotNull(Object paramObject, String paramString)
  {
    if (paramObject == null) {
      throwParameterIsNullException(paramString);
    }
  }
  
  public static void checkReturnedValueIsNotNull(Object paramObject, String paramString)
  {
    if (paramObject != null) {
      return;
    }
    throw ((IllegalStateException)sanitizeStackTrace(new IllegalStateException(paramString)));
  }
  
  public static void checkReturnedValueIsNotNull(Object paramObject, String paramString1, String paramString2)
  {
    if (paramObject != null) {
      return;
    }
    paramObject = new StringBuilder();
    paramObject.append("Method specified as non-null returned null: ");
    paramObject.append(paramString1);
    paramObject.append(".");
    paramObject.append(paramString2);
    throw ((IllegalStateException)sanitizeStackTrace(new IllegalStateException(paramObject.toString())));
  }
  
  public static int compare(int paramInt1, int paramInt2)
  {
    if (paramInt1 < paramInt2) {
      paramInt1 = -1;
    } else if (paramInt1 == paramInt2) {
      paramInt1 = 0;
    } else {
      paramInt1 = 1;
    }
    return paramInt1;
  }
  
  public static int compare(long paramLong1, long paramLong2)
  {
    boolean bool = paramLong1 < paramLong2;
    int i;
    if (bool) {
      i = -1;
    } else if (i == 0) {
      i = 0;
    } else {
      i = 1;
    }
    return i;
  }
  
  public static void needClassReification() {}
  
  public static void needClassReification(String paramString)
  {
    throwUndefinedForReified(paramString);
  }
  
  public static void reifiedOperationMarker(int paramInt, String paramString) {}
  
  public static void reifiedOperationMarker(int paramInt, String paramString1, String paramString2)
  {
    throwUndefinedForReified(paramString2);
  }
  
  private static <T extends Throwable> T sanitizeStackTrace(T paramT)
  {
    return sanitizeStackTrace(paramT, Intrinsics.class.getName());
  }
  
  static <T extends Throwable> T sanitizeStackTrace(T paramT, String paramString)
  {
    StackTraceElement[] arrayOfStackTraceElement = paramT.getStackTrace();
    int i = arrayOfStackTraceElement.length;
    int j = -1;
    for (int k = 0; k < i; k++) {
      if (paramString.equals(arrayOfStackTraceElement[k].getClassName())) {
        j = k;
      }
    }
    paramT.setStackTrace((StackTraceElement[])Arrays.copyOfRange(arrayOfStackTraceElement, j + 1, i));
    return paramT;
  }
  
  public static String stringPlus(String paramString, Object paramObject)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append(paramObject);
    return localStringBuilder.toString();
  }
  
  public static void throwAssert()
  {
    throw ((AssertionError)sanitizeStackTrace(new AssertionError()));
  }
  
  public static void throwAssert(String paramString)
  {
    throw ((AssertionError)sanitizeStackTrace(new AssertionError(paramString)));
  }
  
  public static void throwIllegalArgument()
  {
    throw ((IllegalArgumentException)sanitizeStackTrace(new IllegalArgumentException()));
  }
  
  public static void throwIllegalArgument(String paramString)
  {
    throw ((IllegalArgumentException)sanitizeStackTrace(new IllegalArgumentException(paramString)));
  }
  
  public static void throwIllegalState()
  {
    throw ((IllegalStateException)sanitizeStackTrace(new IllegalStateException()));
  }
  
  public static void throwIllegalState(String paramString)
  {
    throw ((IllegalStateException)sanitizeStackTrace(new IllegalStateException(paramString)));
  }
  
  public static void throwJavaNpe()
  {
    throw ((NullPointerException)sanitizeStackTrace(new NullPointerException()));
  }
  
  public static void throwJavaNpe(String paramString)
  {
    throw ((NullPointerException)sanitizeStackTrace(new NullPointerException(paramString)));
  }
  
  public static void throwNpe()
  {
    throw ((KotlinNullPointerException)sanitizeStackTrace(new KotlinNullPointerException()));
  }
  
  public static void throwNpe(String paramString)
  {
    throw ((KotlinNullPointerException)sanitizeStackTrace(new KotlinNullPointerException(paramString)));
  }
  
  private static void throwParameterIsNullException(String paramString)
  {
    Object localObject = Thread.currentThread().getStackTrace()[3];
    String str = ((StackTraceElement)localObject).getClassName();
    localObject = ((StackTraceElement)localObject).getMethodName();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Parameter specified as non-null is null: method ");
    localStringBuilder.append(str);
    localStringBuilder.append(".");
    localStringBuilder.append((String)localObject);
    localStringBuilder.append(", parameter ");
    localStringBuilder.append(paramString);
    throw ((IllegalArgumentException)sanitizeStackTrace(new IllegalArgumentException(localStringBuilder.toString())));
  }
  
  public static void throwUndefinedForReified()
  {
    throwUndefinedForReified("This function has a reified type parameter and thus can only be inlined at compilation time, not called directly.");
  }
  
  public static void throwUndefinedForReified(String paramString)
  {
    throw new UnsupportedOperationException(paramString);
  }
  
  public static void throwUninitializedProperty(String paramString)
  {
    throw ((UninitializedPropertyAccessException)sanitizeStackTrace(new UninitializedPropertyAccessException(paramString)));
  }
  
  public static void throwUninitializedPropertyAccessException(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("lateinit property ");
    localStringBuilder.append(paramString);
    localStringBuilder.append(" has not been initialized");
    throwUninitializedProperty(localStringBuilder.toString());
  }
}

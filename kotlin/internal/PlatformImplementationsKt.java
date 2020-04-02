package kotlin.internal;

import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

@Metadata(bv={1, 0, 3}, d1={"\000\036\n\000\n\002\030\002\n\000\n\002\020\013\n\000\n\002\020\b\n\002\b\004\n\002\020\000\n\002\b\004\032 \020\002\032\0020\0032\006\020\004\032\0020\0052\006\020\006\032\0020\0052\006\020\007\032\0020\005H\001\032\"\020\b\032\002H\t\"\n\b\000\020\t\030\001*\0020\n2\006\020\013\032\0020\nH?\b?\006\002\020\f\032\b\020\r\032\0020\005H\002\"\020\020\000\032\0020\0018\000X?\004?\006\002\n\000?\006\016"}, d2={"IMPLEMENTATIONS", "Lkotlin/internal/PlatformImplementations;", "apiVersionIsAtLeast", "", "major", "", "minor", "patch", "castToBaseType", "T", "", "instance", "(Ljava/lang/Object;)Ljava/lang/Object;", "getJavaVersion", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class PlatformImplementationsKt
{
  public static final PlatformImplementations IMPLEMENTATIONS;
  
  static
  {
    int i = getJavaVersion();
    if (i >= 65544)
    {
      try
      {
        localObject1 = Class.forName("kotlin.internal.jdk8.JDK8PlatformImplementations").newInstance();
        Intrinsics.checkExpressionValueIsNotNull(localObject1, "Class.forName(\"kotlin.in…entations\").newInstance()");
        if (localObject1 != null)
        {
          try
          {
            PlatformImplementations localPlatformImplementations1 = (PlatformImplementations)localObject1;
          }
          catch (ClassCastException localClassCastException1)
          {
            break label53;
          }
        }
        else
        {
          localObject2 = new kotlin/TypeCastException;
          ((TypeCastException)localObject2).<init>("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
          throw ((Throwable)localObject2);
        }
      }
      catch (ClassNotFoundException localClassNotFoundException1)
      {
        try
        {
          Object localObject2;
          label53:
          localObject1 = Class.forName("kotlin.internal.JRE8PlatformImplementations").newInstance();
          Intrinsics.checkExpressionValueIsNotNull(localObject1, "Class.forName(\"kotlin.in…entations\").newInstance()");
          if (localObject1 != null)
          {
            try
            {
              PlatformImplementations localPlatformImplementations2 = (PlatformImplementations)localObject1;
            }
            catch (ClassCastException localClassCastException2)
            {
              break label184;
            }
          }
          else
          {
            localObject3 = new kotlin/TypeCastException;
            ((TypeCastException)localObject3).<init>("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
            throw ((Throwable)localObject3);
          }
        }
        catch (ClassNotFoundException localClassNotFoundException3)
        {
          Object localObject1;
          Object localObject6;
          Object localObject7;
          Object localObject8;
          Object localObject3;
          for (;;) {}
        }
        localObject6 = localObject1.getClass().getClassLoader();
        localObject7 = PlatformImplementations.class.getClassLoader();
        localObject8 = new java/lang/ClassCastException;
        localObject1 = new java/lang/StringBuilder;
        ((StringBuilder)localObject1).<init>();
        ((StringBuilder)localObject1).append("Instance classloader: ");
        ((StringBuilder)localObject1).append(localObject6);
        ((StringBuilder)localObject1).append(", base type classloader: ");
        ((StringBuilder)localObject1).append(localObject7);
        ((ClassCastException)localObject8).<init>(((StringBuilder)localObject1).toString());
        localObject3 = ((ClassCastException)localObject8).initCause((Throwable)localObject3);
        Intrinsics.checkExpressionValueIsNotNull(localObject3, "ClassCastException(\"Inst…baseTypeCL\").initCause(e)");
        throw ((Throwable)localObject3);
      }
      localObject1 = localObject1.getClass().getClassLoader();
      localObject6 = PlatformImplementations.class.getClassLoader();
      localObject7 = new java/lang/ClassCastException;
      localObject8 = new java/lang/StringBuilder;
      ((StringBuilder)localObject8).<init>();
      ((StringBuilder)localObject8).append("Instance classloader: ");
      ((StringBuilder)localObject8).append(localObject1);
      ((StringBuilder)localObject8).append(", base type classloader: ");
      ((StringBuilder)localObject8).append(localObject6);
      ((ClassCastException)localObject7).<init>(((StringBuilder)localObject8).toString());
      localObject2 = ((ClassCastException)localObject7).initCause((Throwable)localObject2);
      Intrinsics.checkExpressionValueIsNotNull(localObject2, "ClassCastException(\"Inst…baseTypeCL\").initCause(e)");
      throw ((Throwable)localObject2);
    }
    label184:
    if (i >= 65543)
    {
      try
      {
        localObject1 = Class.forName("kotlin.internal.jdk7.JDK7PlatformImplementations").newInstance();
        Intrinsics.checkExpressionValueIsNotNull(localObject1, "Class.forName(\"kotlin.in…entations\").newInstance()");
        if (localObject1 != null)
        {
          try
          {
            localObject3 = (PlatformImplementations)localObject1;
          }
          catch (ClassCastException localClassCastException3)
          {
            break label315;
          }
        }
        else
        {
          localObject4 = new kotlin/TypeCastException;
          ((TypeCastException)localObject4).<init>("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
          throw ((Throwable)localObject4);
        }
      }
      catch (ClassNotFoundException localClassNotFoundException2)
      {
        try
        {
          Object localObject4;
          label315:
          localObject1 = Class.forName("kotlin.internal.JRE7PlatformImplementations").newInstance();
          Intrinsics.checkExpressionValueIsNotNull(localObject1, "Class.forName(\"kotlin.in…entations\").newInstance()");
          if (localObject1 != null)
          {
            try
            {
              PlatformImplementations localPlatformImplementations3 = (PlatformImplementations)localObject1;
            }
            catch (ClassCastException localClassCastException4)
            {
              break label445;
            }
          }
          else
          {
            localObject5 = new kotlin/TypeCastException;
            ((TypeCastException)localObject5).<init>("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
            throw ((Throwable)localObject5);
          }
        }
        catch (ClassNotFoundException localClassNotFoundException4)
        {
          Object localObject5;
          for (;;) {}
        }
        localObject1 = localObject1.getClass().getClassLoader();
        localObject7 = PlatformImplementations.class.getClassLoader();
        localObject6 = new java/lang/ClassCastException;
        localObject8 = new java/lang/StringBuilder;
        ((StringBuilder)localObject8).<init>();
        ((StringBuilder)localObject8).append("Instance classloader: ");
        ((StringBuilder)localObject8).append(localObject1);
        ((StringBuilder)localObject8).append(", base type classloader: ");
        ((StringBuilder)localObject8).append(localObject7);
        ((ClassCastException)localObject6).<init>(((StringBuilder)localObject8).toString());
        localObject5 = ((ClassCastException)localObject6).initCause((Throwable)localObject5);
        Intrinsics.checkExpressionValueIsNotNull(localObject5, "ClassCastException(\"Inst…baseTypeCL\").initCause(e)");
        throw ((Throwable)localObject5);
      }
      localObject1 = localObject1.getClass().getClassLoader();
      localObject8 = PlatformImplementations.class.getClassLoader();
      localObject6 = new java/lang/ClassCastException;
      localObject7 = new java/lang/StringBuilder;
      ((StringBuilder)localObject7).<init>();
      ((StringBuilder)localObject7).append("Instance classloader: ");
      ((StringBuilder)localObject7).append(localObject1);
      ((StringBuilder)localObject7).append(", base type classloader: ");
      ((StringBuilder)localObject7).append(localObject8);
      ((ClassCastException)localObject6).<init>(((StringBuilder)localObject7).toString());
      localObject4 = ((ClassCastException)localObject6).initCause((Throwable)localObject4);
      Intrinsics.checkExpressionValueIsNotNull(localObject4, "ClassCastException(\"Inst…baseTypeCL\").initCause(e)");
      throw ((Throwable)localObject4);
    }
    label445:
    localObject5 = new PlatformImplementations();
    IMPLEMENTATIONS = (PlatformImplementations)localObject5;
  }
  
  public static final boolean apiVersionIsAtLeast(int paramInt1, int paramInt2, int paramInt3)
  {
    return KotlinVersion.CURRENT.isAtLeast(paramInt1, paramInt2, paramInt3);
  }
  
  private static final int getJavaVersion()
  {
    String str = System.getProperty("java.specification.version");
    int i = 65542;
    Object localObject;
    int j;
    if (str != null)
    {
      localObject = (CharSequence)str;
      j = StringsKt.indexOf$default((CharSequence)localObject, '.', 0, false, 6, null);
      if (j >= 0) {}
    }
    try
    {
      k = Integer.parseInt(str);
      i = k * 65536;
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      int k;
      int m;
      int n;
      for (;;) {}
    }
    return i;
    m = j + 1;
    n = StringsKt.indexOf$default((CharSequence)localObject, '.', m, false, 4, null);
    k = n;
    if (n < 0) {
      k = str.length();
    }
    if (str != null)
    {
      localObject = str.substring(0, j);
      Intrinsics.checkExpressionValueIsNotNull(localObject, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      if (str != null)
      {
        str = str.substring(m, k);
        Intrinsics.checkExpressionValueIsNotNull(str, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      }
    }
    try
    {
      k = Integer.parseInt((String)localObject);
      n = Integer.parseInt(str);
      i = k * 65536 + n;
    }
    catch (NumberFormatException localNumberFormatException2)
    {
      for (;;) {}
    }
    return i;
    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    return 65542;
  }
}

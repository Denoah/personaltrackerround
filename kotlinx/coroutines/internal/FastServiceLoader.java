package kotlinx.coroutines.internal;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

@Metadata(bv={1, 0, 3}, d1={"\000N\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\003\n\002\020 \n\002\b\006\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\b?\002\030\0002\0020\001B\007\b\002?\006\002\020\002J!\020\005\032\004\030\0010\0062\f\020\007\032\b\022\004\022\0020\0060\b2\006\020\t\032\0020\004H?\bJ1\020\n\032\002H\013\"\004\b\000\020\0132\006\020\f\032\0020\0042\006\020\r\032\0020\0162\f\020\017\032\b\022\004\022\002H\0130\bH\002?\006\002\020\020J*\020\021\032\b\022\004\022\002H\0130\022\"\004\b\000\020\0132\f\020\017\032\b\022\004\022\002H\0130\b2\006\020\r\032\0020\016H\002J\023\020\023\032\b\022\004\022\0020\0060\022H\000?\006\002\b\024J/\020\025\032\b\022\004\022\002H\0130\022\"\004\b\000\020\0132\f\020\017\032\b\022\004\022\002H\0130\b2\006\020\r\032\0020\016H\000?\006\002\b\026J\026\020\027\032\b\022\004\022\0020\0040\0222\006\020\030\032\0020\031H\002J\026\020\032\032\b\022\004\022\0020\0040\0222\006\020\033\032\0020\034H\002J,\020\035\032\002H\036\"\004\b\000\020\036*\0020\0372\022\020 \032\016\022\004\022\0020\037\022\004\022\002H\0360!H?\b?\006\002\020\"R\016\020\003\032\0020\004X?T?\006\002\n\000?\006#"}, d2={"Lkotlinx/coroutines/internal/FastServiceLoader;", "", "()V", "PREFIX", "", "createInstanceOf", "Lkotlinx/coroutines/internal/MainDispatcherFactory;", "baseClass", "Ljava/lang/Class;", "serviceClass", "getProviderInstance", "S", "name", "loader", "Ljava/lang/ClassLoader;", "service", "(Ljava/lang/String;Ljava/lang/ClassLoader;Ljava/lang/Class;)Ljava/lang/Object;", "load", "", "loadMainDispatcherFactory", "loadMainDispatcherFactory$kotlinx_coroutines_core", "loadProviders", "loadProviders$kotlinx_coroutines_core", "parse", "url", "Ljava/net/URL;", "parseFile", "r", "Ljava/io/BufferedReader;", "use", "R", "Ljava/util/jar/JarFile;", "block", "Lkotlin/Function1;", "(Ljava/util/jar/JarFile;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class FastServiceLoader
{
  public static final FastServiceLoader INSTANCE = new FastServiceLoader();
  private static final String PREFIX = "META-INF/services/";
  
  private FastServiceLoader() {}
  
  private final MainDispatcherFactory createInstanceOf(Class<MainDispatcherFactory> paramClass, String paramString)
  {
    try
    {
      paramClass = (MainDispatcherFactory)paramClass.cast(Class.forName(paramString, true, paramClass.getClassLoader()).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
    }
    catch (ClassNotFoundException paramClass)
    {
      paramClass = null;
    }
    return paramClass;
  }
  
  private final <S> S getProviderInstance(String paramString, ClassLoader paramClassLoader, Class<S> paramClass)
  {
    paramString = Class.forName(paramString, false, paramClassLoader);
    if (paramClass.isAssignableFrom(paramString)) {
      return paramClass.cast(paramString.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
    }
    paramClassLoader = new StringBuilder();
    paramClassLoader.append("Expected service of class ");
    paramClassLoader.append(paramClass);
    paramClassLoader.append(", but found ");
    paramClassLoader.append(paramString);
    throw ((Throwable)new IllegalArgumentException(paramClassLoader.toString().toString()));
  }
  
  /* Error */
  private final <S> List<S> load(Class<S> paramClass, ClassLoader paramClassLoader)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: aload_2
    //   3: invokevirtual 126	kotlinx/coroutines/internal/FastServiceLoader:loadProviders$kotlinx_coroutines_core	(Ljava/lang/Class;Ljava/lang/ClassLoader;)Ljava/util/List;
    //   6: astore_3
    //   7: aload_3
    //   8: astore_1
    //   9: goto +24 -> 33
    //   12: astore_3
    //   13: aload_1
    //   14: aload_2
    //   15: invokestatic 131	java/util/ServiceLoader:load	(Ljava/lang/Class;Ljava/lang/ClassLoader;)Ljava/util/ServiceLoader;
    //   18: astore_1
    //   19: aload_1
    //   20: ldc -123
    //   22: invokestatic 139	kotlin/jvm/internal/Intrinsics:checkExpressionValueIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   25: aload_1
    //   26: checkcast 141	java/lang/Iterable
    //   29: invokestatic 147	kotlin/collections/CollectionsKt:toList	(Ljava/lang/Iterable;)Ljava/util/List;
    //   32: astore_1
    //   33: aload_1
    //   34: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	35	0	this	FastServiceLoader
    //   0	35	1	paramClass	Class<S>
    //   0	35	2	paramClassLoader	ClassLoader
    //   6	2	3	localList	List
    //   12	1	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   0	7	12	finally
  }
  
  private final List<String> parse(URL paramURL)
  {
    Object localObject1 = paramURL.toString();
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "url.toString()");
    if (StringsKt.startsWith$default((String)localObject1, "jar", false, 2, null))
    {
      paramURL = StringsKt.substringBefore$default(StringsKt.substringAfter$default((String)localObject1, "jar:file:", null, 2, null), '!', null, 2, null);
      localObject1 = StringsKt.substringAfter$default((String)localObject1, "!/", null, 2, null);
      paramURL = new JarFile(paramURL, false);
      Object localObject2 = (Throwable)null;
      try
      {
        Object localObject7 = new java/io/BufferedReader;
        localObject2 = new java/io/InputStreamReader;
        ZipEntry localZipEntry = new java/util/zip/ZipEntry;
        localZipEntry.<init>((String)localObject1);
        ((InputStreamReader)localObject2).<init>(paramURL.getInputStream(localZipEntry), "UTF-8");
        ((BufferedReader)localObject7).<init>((Reader)localObject2);
        localObject1 = (Closeable)localObject7;
        localObject2 = (Throwable)null;
        try
        {
          localObject7 = (BufferedReader)localObject1;
          localObject7 = INSTANCE.parseFile((BufferedReader)localObject7);
          CloseableKt.closeFinally((Closeable)localObject1, (Throwable)localObject2);
          try
          {
            paramURL.close();
            return localObject7;
          }
          finally {}
          localThrowable1 = finally;
        }
        finally {}
        paramURL = (Closeable)new BufferedReader((Reader)new InputStreamReader(paramURL.openStream()));
      }
      finally
      {
        try
        {
          throw localThrowable1;
        }
        finally {}
      }
    }
    Throwable localThrowable2 = (Throwable)null;
    try
    {
      Object localObject5 = (BufferedReader)paramURL;
      localObject5 = INSTANCE.parseFile((BufferedReader)localObject5);
      CloseableKt.closeFinally(paramURL, localThrowable2);
      return localObject5;
    }
    finally
    {
      try
      {
        throw localThrowable3;
      }
      finally
      {
        CloseableKt.closeFinally(paramURL, localThrowable3);
      }
    }
  }
  
  private final List<String> parseFile(BufferedReader paramBufferedReader)
  {
    Set localSet = (Set)new LinkedHashSet();
    String str;
    for (;;)
    {
      str = paramBufferedReader.readLine();
      if (str == null) {
        break label216;
      }
      str = StringsKt.substringBefore$default(str, "#", null, 2, null);
      if (str == null) {
        break label205;
      }
      str = StringsKt.trim((CharSequence)str).toString();
      CharSequence localCharSequence = (CharSequence)str;
      int i = 0;
      for (int j = 0; j < localCharSequence.length(); j++)
      {
        char c = localCharSequence.charAt(j);
        int k;
        if ((c != '.') && (!Character.isJavaIdentifierPart(c))) {
          k = 0;
        } else {
          k = 1;
        }
        if (k == 0)
        {
          j = 0;
          break label127;
        }
      }
      j = 1;
      label127:
      if (j == 0) {
        break;
      }
      j = i;
      if (localCharSequence.length() > 0) {
        j = 1;
      }
      if (j != 0) {
        localSet.add(str);
      }
    }
    paramBufferedReader = new StringBuilder();
    paramBufferedReader.append("Illegal service provider class name: ");
    paramBufferedReader.append(str);
    throw ((Throwable)new IllegalArgumentException(paramBufferedReader.toString().toString()));
    label205:
    throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
    label216:
    return CollectionsKt.toList((Iterable)localSet);
  }
  
  /* Error */
  private final <R> R use(JarFile paramJarFile, kotlin.jvm.functions.Function1<? super JarFile, ? extends R> paramFunction1)
  {
    // Byte code:
    //   0: aconst_null
    //   1: checkcast 122	java/lang/Throwable
    //   4: astore_3
    //   5: aload_2
    //   6: aload_1
    //   7: invokeinterface 277 2 0
    //   12: astore_2
    //   13: iconst_1
    //   14: invokestatic 283	kotlin/jvm/internal/InlineMarker:finallyStart	(I)V
    //   17: aload_1
    //   18: invokevirtual 214	java/util/jar/JarFile:close	()V
    //   21: iconst_1
    //   22: invokestatic 286	kotlin/jvm/internal/InlineMarker:finallyEnd	(I)V
    //   25: aload_2
    //   26: areturn
    //   27: astore_1
    //   28: aload_1
    //   29: athrow
    //   30: astore_2
    //   31: aload_2
    //   32: athrow
    //   33: astore_3
    //   34: iconst_1
    //   35: invokestatic 283	kotlin/jvm/internal/InlineMarker:finallyStart	(I)V
    //   38: aload_1
    //   39: invokevirtual 214	java/util/jar/JarFile:close	()V
    //   42: iconst_1
    //   43: invokestatic 286	kotlin/jvm/internal/InlineMarker:finallyEnd	(I)V
    //   46: aload_3
    //   47: athrow
    //   48: astore_1
    //   49: aload_2
    //   50: aload_1
    //   51: invokestatic 220	kotlin/ExceptionsKt:addSuppressed	(Ljava/lang/Throwable;Ljava/lang/Throwable;)V
    //   54: aload_2
    //   55: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	56	0	this	FastServiceLoader
    //   0	56	1	paramJarFile	JarFile
    //   0	56	2	paramFunction1	kotlin.jvm.functions.Function1<? super JarFile, ? extends R>
    //   4	1	3	localThrowable	Throwable
    //   33	14	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   17	21	27	finally
    //   5	13	30	finally
    //   31	33	33	finally
    //   38	42	48	finally
  }
  
  /* Error */
  public final List<MainDispatcherFactory> loadMainDispatcherFactory$kotlinx_coroutines_core()
  {
    // Byte code:
    //   0: invokestatic 294	kotlinx/coroutines/internal/FastServiceLoaderKt:getANDROID_DETECTED	()Z
    //   3: ifne +24 -> 27
    //   6: ldc 90
    //   8: invokevirtual 70	java/lang/Class:getClassLoader	()Ljava/lang/ClassLoader;
    //   11: astore_1
    //   12: aload_1
    //   13: ldc_w 296
    //   16: invokestatic 139	kotlin/jvm/internal/Intrinsics:checkExpressionValueIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   19: aload_0
    //   20: ldc 90
    //   22: aload_1
    //   23: invokespecial 298	kotlinx/coroutines/internal/FastServiceLoader:load	(Ljava/lang/Class;Ljava/lang/ClassLoader;)Ljava/util/List;
    //   26: areturn
    //   27: new 300	java/util/ArrayList
    //   30: astore_2
    //   31: aload_2
    //   32: iconst_2
    //   33: invokespecial 302	java/util/ArrayList:<init>	(I)V
    //   36: aconst_null
    //   37: astore_3
    //   38: ldc 90
    //   40: ldc_w 304
    //   43: iconst_1
    //   44: ldc 90
    //   46: invokevirtual 70	java/lang/Class:getClassLoader	()Ljava/lang/ClassLoader;
    //   49: invokestatic 74	java/lang/Class:forName	(Ljava/lang/String;ZLjava/lang/ClassLoader;)Ljava/lang/Class;
    //   52: iconst_0
    //   53: anewarray 66	java/lang/Class
    //   56: invokevirtual 78	java/lang/Class:getDeclaredConstructor	([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
    //   59: iconst_0
    //   60: anewarray 4	java/lang/Object
    //   63: invokevirtual 84	java/lang/reflect/Constructor:newInstance	([Ljava/lang/Object;)Ljava/lang/Object;
    //   66: invokevirtual 88	java/lang/Class:cast	(Ljava/lang/Object;)Ljava/lang/Object;
    //   69: checkcast 90	kotlinx/coroutines/internal/MainDispatcherFactory
    //   72: astore_1
    //   73: goto +6 -> 79
    //   76: astore_1
    //   77: aconst_null
    //   78: astore_1
    //   79: aload_1
    //   80: ifnull +9 -> 89
    //   83: aload_2
    //   84: aload_1
    //   85: invokevirtual 305	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   88: pop
    //   89: ldc 90
    //   91: ldc_w 307
    //   94: iconst_1
    //   95: ldc 90
    //   97: invokevirtual 70	java/lang/Class:getClassLoader	()Ljava/lang/ClassLoader;
    //   100: invokestatic 74	java/lang/Class:forName	(Ljava/lang/String;ZLjava/lang/ClassLoader;)Ljava/lang/Class;
    //   103: iconst_0
    //   104: anewarray 66	java/lang/Class
    //   107: invokevirtual 78	java/lang/Class:getDeclaredConstructor	([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
    //   110: iconst_0
    //   111: anewarray 4	java/lang/Object
    //   114: invokevirtual 84	java/lang/reflect/Constructor:newInstance	([Ljava/lang/Object;)Ljava/lang/Object;
    //   117: invokevirtual 88	java/lang/Class:cast	(Ljava/lang/Object;)Ljava/lang/Object;
    //   120: checkcast 90	kotlinx/coroutines/internal/MainDispatcherFactory
    //   123: astore_1
    //   124: aload_1
    //   125: ifnull +9 -> 134
    //   128: aload_2
    //   129: aload_1
    //   130: invokevirtual 305	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   133: pop
    //   134: aload_2
    //   135: checkcast 309	java/util/List
    //   138: astore_1
    //   139: goto +25 -> 164
    //   142: astore_1
    //   143: ldc 90
    //   145: invokevirtual 70	java/lang/Class:getClassLoader	()Ljava/lang/ClassLoader;
    //   148: astore_1
    //   149: aload_1
    //   150: ldc_w 296
    //   153: invokestatic 139	kotlin/jvm/internal/Intrinsics:checkExpressionValueIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   156: aload_0
    //   157: ldc 90
    //   159: aload_1
    //   160: invokespecial 298	kotlinx/coroutines/internal/FastServiceLoader:load	(Ljava/lang/Class;Ljava/lang/ClassLoader;)Ljava/util/List;
    //   163: astore_1
    //   164: aload_1
    //   165: areturn
    //   166: astore_1
    //   167: aload_3
    //   168: astore_1
    //   169: goto -45 -> 124
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	172	0	this	FastServiceLoader
    //   11	62	1	localObject1	Object
    //   76	1	1	localClassNotFoundException1	ClassNotFoundException
    //   78	61	1	localObject2	Object
    //   142	1	1	localObject3	Object
    //   148	17	1	localObject4	Object
    //   166	1	1	localClassNotFoundException2	ClassNotFoundException
    //   168	1	1	localObject5	Object
    //   30	105	2	localArrayList	ArrayList
    //   37	131	3	localObject6	Object
    // Exception table:
    //   from	to	target	type
    //   38	73	76	java/lang/ClassNotFoundException
    //   27	36	142	finally
    //   38	73	142	finally
    //   83	89	142	finally
    //   89	124	142	finally
    //   128	134	142	finally
    //   134	139	142	finally
    //   89	124	166	java/lang/ClassNotFoundException
  }
  
  public final <S> List<S> loadProviders$kotlinx_coroutines_core(Class<S> paramClass, ClassLoader paramClassLoader)
  {
    Intrinsics.checkParameterIsNotNull(paramClass, "service");
    Intrinsics.checkParameterIsNotNull(paramClassLoader, "loader");
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("META-INF/services/");
    ((StringBuilder)localObject1).append(paramClass.getName());
    localObject1 = paramClassLoader.getResources(((StringBuilder)localObject1).toString());
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "urls");
    localObject1 = Collections.list((Enumeration)localObject1);
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "java.util.Collections.list(this)");
    Object localObject2 = (Iterable)localObject1;
    localObject1 = (Collection)new ArrayList();
    Object localObject3 = ((Iterable)localObject2).iterator();
    while (((Iterator)localObject3).hasNext())
    {
      localObject2 = (URL)((Iterator)localObject3).next();
      FastServiceLoader localFastServiceLoader = INSTANCE;
      Intrinsics.checkExpressionValueIsNotNull(localObject2, "it");
      CollectionsKt.addAll((Collection)localObject1, (Iterable)localFastServiceLoader.parse((URL)localObject2));
    }
    localObject1 = CollectionsKt.toSet((Iterable)localObject1);
    if ((((Collection)localObject1).isEmpty() ^ true))
    {
      localObject2 = (Iterable)localObject1;
      localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
      localObject2 = ((Iterable)localObject2).iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject3 = (String)((Iterator)localObject2).next();
        ((Collection)localObject1).add(INSTANCE.getProviderInstance((String)localObject3, paramClassLoader, paramClass));
      }
      return (List)localObject1;
    }
    throw ((Throwable)new IllegalArgumentException("No providers were loaded with FastServiceLoader".toString()));
  }
}

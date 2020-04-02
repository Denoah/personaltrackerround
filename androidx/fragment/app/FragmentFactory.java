package androidx.fragment.app;

import androidx.collection.SimpleArrayMap;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class FragmentFactory
{
  private static final SimpleArrayMap<String, Class<?>> sClassMap = new SimpleArrayMap();
  
  public FragmentFactory() {}
  
  static boolean isFragmentClass(ClassLoader paramClassLoader, String paramString)
  {
    try
    {
      boolean bool = Fragment.class.isAssignableFrom(loadClass(paramClassLoader, paramString));
      return bool;
    }
    catch (ClassNotFoundException paramClassLoader) {}
    return false;
  }
  
  private static Class<?> loadClass(ClassLoader paramClassLoader, String paramString)
    throws ClassNotFoundException
  {
    Class localClass1 = (Class)sClassMap.get(paramString);
    Class localClass2 = localClass1;
    if (localClass1 == null)
    {
      localClass2 = Class.forName(paramString, false, paramClassLoader);
      sClassMap.put(paramString, localClass2);
    }
    return localClass2;
  }
  
  public static Class<? extends Fragment> loadFragmentClass(ClassLoader paramClassLoader, String paramString)
  {
    try
    {
      paramClassLoader = loadClass(paramClassLoader, paramString);
      return paramClassLoader;
    }
    catch (ClassCastException paramClassLoader)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unable to instantiate fragment ");
      localStringBuilder.append(paramString);
      localStringBuilder.append(": make sure class is a valid subclass of Fragment");
      throw new Fragment.InstantiationException(localStringBuilder.toString(), paramClassLoader);
    }
    catch (ClassNotFoundException paramClassLoader)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unable to instantiate fragment ");
      localStringBuilder.append(paramString);
      localStringBuilder.append(": make sure class name exists");
      throw new Fragment.InstantiationException(localStringBuilder.toString(), paramClassLoader);
    }
  }
  
  public Fragment instantiate(ClassLoader paramClassLoader, String paramString)
  {
    try
    {
      paramClassLoader = (Fragment)loadFragmentClass(paramClassLoader, paramString).getConstructor(new Class[0]).newInstance(new Object[0]);
      return paramClassLoader;
    }
    catch (InvocationTargetException paramClassLoader)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unable to instantiate fragment ");
      localStringBuilder.append(paramString);
      localStringBuilder.append(": calling Fragment constructor caused an exception");
      throw new Fragment.InstantiationException(localStringBuilder.toString(), paramClassLoader);
    }
    catch (NoSuchMethodException paramClassLoader)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unable to instantiate fragment ");
      localStringBuilder.append(paramString);
      localStringBuilder.append(": could not find Fragment constructor");
      throw new Fragment.InstantiationException(localStringBuilder.toString(), paramClassLoader);
    }
    catch (IllegalAccessException paramClassLoader)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unable to instantiate fragment ");
      localStringBuilder.append(paramString);
      localStringBuilder.append(": make sure class name exists, is public, and has an empty constructor that is public");
      throw new Fragment.InstantiationException(localStringBuilder.toString(), paramClassLoader);
    }
    catch (InstantiationException localInstantiationException)
    {
      paramClassLoader = new StringBuilder();
      paramClassLoader.append("Unable to instantiate fragment ");
      paramClassLoader.append(paramString);
      paramClassLoader.append(": make sure class name exists, is public, and has an empty constructor that is public");
      throw new Fragment.InstantiationException(paramClassLoader.toString(), localInstantiationException);
    }
  }
}

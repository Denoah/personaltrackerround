package com.google.gson.internal;

import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public abstract class UnsafeAllocator
{
  public UnsafeAllocator() {}
  
  static void assertInstantiable(Class<?> paramClass)
  {
    int i = paramClass.getModifiers();
    if (!Modifier.isInterface(i))
    {
      if (!Modifier.isAbstract(i)) {
        return;
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Abstract class can't be instantiated! Class name: ");
      localStringBuilder.append(paramClass.getName());
      throw new UnsupportedOperationException(localStringBuilder.toString());
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Interface can't be instantiated! Interface name: ");
    localStringBuilder.append(paramClass.getName());
    throw new UnsupportedOperationException(localStringBuilder.toString());
  }
  
  public static UnsafeAllocator create()
  {
    try
    {
      Object localObject1 = Class.forName("sun.misc.Unsafe");
      final Object localObject4 = ((Class)localObject1).getDeclaredField("theUnsafe");
      ((Field)localObject4).setAccessible(true);
      localObject4 = ((Field)localObject4).get(null);
      localObject1 = new UnsafeAllocator()
      {
        public <T> T newInstance(Class<T> paramAnonymousClass)
          throws Exception
        {
          assertInstantiable(paramAnonymousClass);
          return UnsafeAllocator.this.invoke(localObject4, new Object[] { paramAnonymousClass });
        }
      };
      return localObject1;
    }
    catch (Exception localException1)
    {
      try
      {
        Object localObject2 = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", new Class[] { Class.class });
        ((Method)localObject2).setAccessible(true);
        final int i = ((Integer)((Method)localObject2).invoke(null, new Object[] { Object.class })).intValue();
        localObject2 = ObjectStreamClass.class.getDeclaredMethod("newInstance", new Class[] { Class.class, Integer.TYPE });
        ((Method)localObject2).setAccessible(true);
        localObject2 = new UnsafeAllocator()
        {
          public <T> T newInstance(Class<T> paramAnonymousClass)
            throws Exception
          {
            assertInstantiable(paramAnonymousClass);
            return UnsafeAllocator.this.invoke(null, new Object[] { paramAnonymousClass, Integer.valueOf(i) });
          }
        };
        return localObject2;
      }
      catch (Exception localException2)
      {
        try
        {
          Object localObject3 = ObjectInputStream.class.getDeclaredMethod("newInstance", new Class[] { Class.class, Class.class });
          ((Method)localObject3).setAccessible(true);
          localObject3 = new UnsafeAllocator()
          {
            public <T> T newInstance(Class<T> paramAnonymousClass)
              throws Exception
            {
              assertInstantiable(paramAnonymousClass);
              return UnsafeAllocator.this.invoke(null, new Object[] { paramAnonymousClass, Object.class });
            }
          };
          return localObject3;
        }
        catch (Exception localException3) {}
      }
    }
    new UnsafeAllocator()
    {
      public <T> T newInstance(Class<T> paramAnonymousClass)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Cannot allocate ");
        localStringBuilder.append(paramAnonymousClass);
        throw new UnsupportedOperationException(localStringBuilder.toString());
      }
    };
  }
  
  public abstract <T> T newInstance(Class<T> paramClass)
    throws Exception;
}

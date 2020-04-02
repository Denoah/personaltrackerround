package com.google.gson.internal;

import java.lang.reflect.Type;

public final class Primitives
{
  private Primitives() {}
  
  public static boolean isPrimitive(Type paramType)
  {
    boolean bool;
    if (((paramType instanceof Class)) && (((Class)paramType).isPrimitive())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isWrapperType(Type paramType)
  {
    boolean bool;
    if ((paramType != Integer.class) && (paramType != Float.class) && (paramType != Byte.class) && (paramType != Double.class) && (paramType != Long.class) && (paramType != Character.class) && (paramType != Boolean.class) && (paramType != Short.class) && (paramType != Void.class)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static <T> Class<T> unwrap(Class<T> paramClass)
  {
    if (paramClass == Integer.class) {
      return Integer.TYPE;
    }
    if (paramClass == Float.class) {
      return Float.TYPE;
    }
    if (paramClass == Byte.class) {
      return Byte.TYPE;
    }
    if (paramClass == Double.class) {
      return Double.TYPE;
    }
    if (paramClass == Long.class) {
      return Long.TYPE;
    }
    if (paramClass == Character.class) {
      return Character.TYPE;
    }
    if (paramClass == Boolean.class) {
      return Boolean.TYPE;
    }
    if (paramClass == Short.class) {
      return Short.TYPE;
    }
    Object localObject = paramClass;
    if (paramClass == Void.class) {
      localObject = Void.TYPE;
    }
    return localObject;
  }
  
  public static <T> Class<T> wrap(Class<T> paramClass)
  {
    if (paramClass == Integer.TYPE) {
      return Integer.class;
    }
    if (paramClass == Float.TYPE) {
      return Float.class;
    }
    if (paramClass == Byte.TYPE) {
      return Byte.class;
    }
    if (paramClass == Double.TYPE) {
      return Double.class;
    }
    if (paramClass == Long.TYPE) {
      return Long.class;
    }
    if (paramClass == Character.TYPE) {
      return Character.class;
    }
    if (paramClass == Boolean.TYPE) {
      return Boolean.class;
    }
    if (paramClass == Short.TYPE) {
      return Short.class;
    }
    Object localObject = paramClass;
    if (paramClass == Void.TYPE) {
      localObject = Void.class;
    }
    return localObject;
  }
}

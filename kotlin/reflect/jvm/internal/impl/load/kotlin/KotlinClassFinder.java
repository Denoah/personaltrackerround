package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.KotlinMetadataFinder;

public abstract interface KotlinClassFinder
  extends KotlinMetadataFinder
{
  public abstract Result findKotlinClassOrContent(JavaClass paramJavaClass);
  
  public abstract Result findKotlinClassOrContent(ClassId paramClassId);
  
  public static abstract class Result
  {
    private Result() {}
    
    public final KotlinJvmBinaryClass toKotlinJvmBinaryClass()
    {
      boolean bool = this instanceof KotlinClass;
      Object localObject1 = null;
      if (!bool) {
        localObject2 = null;
      } else {
        localObject2 = this;
      }
      KotlinClass localKotlinClass = (KotlinClass)localObject2;
      Object localObject2 = localObject1;
      if (localKotlinClass != null) {
        localObject2 = localKotlinClass.getKotlinJvmBinaryClass();
      }
      return localObject2;
    }
    
    public static final class ClassFileContent
      extends KotlinClassFinder.Result
    {
      private final byte[] content;
      
      public boolean equals(Object paramObject)
      {
        if (this != paramObject) {
          if ((paramObject instanceof ClassFileContent))
          {
            paramObject = (ClassFileContent)paramObject;
            if (Intrinsics.areEqual(this.content, paramObject.content)) {}
          }
          else
          {
            return false;
          }
        }
        return true;
      }
      
      public final byte[] getContent()
      {
        return this.content;
      }
      
      public int hashCode()
      {
        byte[] arrayOfByte = this.content;
        int i;
        if (arrayOfByte != null) {
          i = Arrays.hashCode(arrayOfByte);
        } else {
          i = 0;
        }
        return i;
      }
      
      public String toString()
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("ClassFileContent(content=");
        localStringBuilder.append(Arrays.toString(this.content));
        localStringBuilder.append(")");
        return localStringBuilder.toString();
      }
    }
    
    public static final class KotlinClass
      extends KotlinClassFinder.Result
    {
      private final KotlinJvmBinaryClass kotlinJvmBinaryClass;
      
      public KotlinClass(KotlinJvmBinaryClass paramKotlinJvmBinaryClass)
      {
        super();
        this.kotlinJvmBinaryClass = paramKotlinJvmBinaryClass;
      }
      
      public boolean equals(Object paramObject)
      {
        if (this != paramObject) {
          if ((paramObject instanceof KotlinClass))
          {
            paramObject = (KotlinClass)paramObject;
            if (Intrinsics.areEqual(this.kotlinJvmBinaryClass, paramObject.kotlinJvmBinaryClass)) {}
          }
          else
          {
            return false;
          }
        }
        return true;
      }
      
      public final KotlinJvmBinaryClass getKotlinJvmBinaryClass()
      {
        return this.kotlinJvmBinaryClass;
      }
      
      public int hashCode()
      {
        KotlinJvmBinaryClass localKotlinJvmBinaryClass = this.kotlinJvmBinaryClass;
        int i;
        if (localKotlinJvmBinaryClass != null) {
          i = localKotlinJvmBinaryClass.hashCode();
        } else {
          i = 0;
        }
        return i;
      }
      
      public String toString()
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("KotlinClass(kotlinJvmBinaryClass=");
        localStringBuilder.append(this.kotlinJvmBinaryClass);
        localStringBuilder.append(")");
        return localStringBuilder.toString();
      }
    }
  }
}

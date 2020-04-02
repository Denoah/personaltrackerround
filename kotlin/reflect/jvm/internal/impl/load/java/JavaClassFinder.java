package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.Arrays;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaPackage;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;

public abstract interface JavaClassFinder
{
  public abstract JavaClass findClass(Request paramRequest);
  
  public abstract JavaPackage findPackage(FqName paramFqName);
  
  public abstract Set<String> knownClassNamesInPackage(FqName paramFqName);
  
  public static final class Request
  {
    private final ClassId classId;
    private final JavaClass outerClass;
    private final byte[] previouslyFoundClassFileContent;
    
    public Request(ClassId paramClassId, byte[] paramArrayOfByte, JavaClass paramJavaClass)
    {
      this.classId = paramClassId;
      this.previouslyFoundClassFileContent = paramArrayOfByte;
      this.outerClass = paramJavaClass;
    }
    
    public boolean equals(Object paramObject)
    {
      if (this != paramObject) {
        if ((paramObject instanceof Request))
        {
          paramObject = (Request)paramObject;
          if ((Intrinsics.areEqual(this.classId, paramObject.classId)) && (Intrinsics.areEqual(this.previouslyFoundClassFileContent, paramObject.previouslyFoundClassFileContent)) && (Intrinsics.areEqual(this.outerClass, paramObject.outerClass))) {}
        }
        else
        {
          return false;
        }
      }
      return true;
    }
    
    public final ClassId getClassId()
    {
      return this.classId;
    }
    
    public int hashCode()
    {
      Object localObject = this.classId;
      int i = 0;
      int j;
      if (localObject != null) {
        j = localObject.hashCode();
      } else {
        j = 0;
      }
      localObject = this.previouslyFoundClassFileContent;
      int k;
      if (localObject != null) {
        k = Arrays.hashCode((byte[])localObject);
      } else {
        k = 0;
      }
      localObject = this.outerClass;
      if (localObject != null) {
        i = localObject.hashCode();
      }
      return (j * 31 + k) * 31 + i;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Request(classId=");
      localStringBuilder.append(this.classId);
      localStringBuilder.append(", previouslyFoundClassFileContent=");
      localStringBuilder.append(Arrays.toString(this.previouslyFoundClassFileContent));
      localStringBuilder.append(", outerClass=");
      localStringBuilder.append(this.outerClass);
      localStringBuilder.append(")");
      return localStringBuilder.toString();
    }
  }
}

package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass.AnnotationVisitor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass.MemberVisitor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.KotlinClassHeader;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.ReadKotlinClassHeaderAnnotationVisitor;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.text.StringsKt;

public final class ReflectKotlinClass
  implements KotlinJvmBinaryClass
{
  public static final Factory Factory = new Factory(null);
  private final KotlinClassHeader classHeader;
  private final Class<?> klass;
  
  private ReflectKotlinClass(Class<?> paramClass, KotlinClassHeader paramKotlinClassHeader)
  {
    this.klass = paramClass;
    this.classHeader = paramKotlinClassHeader;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof ReflectKotlinClass)) && (Intrinsics.areEqual(this.klass, ((ReflectKotlinClass)paramObject).klass))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public KotlinClassHeader getClassHeader()
  {
    return this.classHeader;
  }
  
  public ClassId getClassId()
  {
    return ReflectClassUtilKt.getClassId(this.klass);
  }
  
  public final Class<?> getKlass()
  {
    return this.klass;
  }
  
  public String getLocation()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    String str = this.klass.getName();
    Intrinsics.checkExpressionValueIsNotNull(str, "klass.name");
    localStringBuilder.append(StringsKt.replace$default(str, '.', '/', false, 4, null));
    localStringBuilder.append(".class");
    return localStringBuilder.toString();
  }
  
  public int hashCode()
  {
    return this.klass.hashCode();
  }
  
  public void loadClassAnnotations(KotlinJvmBinaryClass.AnnotationVisitor paramAnnotationVisitor, byte[] paramArrayOfByte)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotationVisitor, "visitor");
    ReflectClassStructure.INSTANCE.loadClassAnnotations(this.klass, paramAnnotationVisitor);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getName());
    localStringBuilder.append(": ");
    localStringBuilder.append(this.klass);
    return localStringBuilder.toString();
  }
  
  public void visitMembers(KotlinJvmBinaryClass.MemberVisitor paramMemberVisitor, byte[] paramArrayOfByte)
  {
    Intrinsics.checkParameterIsNotNull(paramMemberVisitor, "visitor");
    ReflectClassStructure.INSTANCE.visitMembers(this.klass, paramMemberVisitor);
  }
  
  public static final class Factory
  {
    private Factory() {}
    
    public final ReflectKotlinClass create(Class<?> paramClass)
    {
      Intrinsics.checkParameterIsNotNull(paramClass, "klass");
      Object localObject = new ReadKotlinClassHeaderAnnotationVisitor();
      ReflectClassStructure.INSTANCE.loadClassAnnotations(paramClass, (KotlinJvmBinaryClass.AnnotationVisitor)localObject);
      localObject = ((ReadKotlinClassHeaderAnnotationVisitor)localObject).createHeader();
      if (localObject != null) {
        return new ReflectKotlinClass(paramClass, (KotlinClassHeader)localObject, null);
      }
      return null;
    }
  }
}

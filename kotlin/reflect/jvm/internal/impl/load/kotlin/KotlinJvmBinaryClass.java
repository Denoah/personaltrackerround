package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.KotlinClassHeader;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ClassLiteralValue;

public abstract interface KotlinJvmBinaryClass
{
  public abstract KotlinClassHeader getClassHeader();
  
  public abstract ClassId getClassId();
  
  public abstract String getLocation();
  
  public abstract void loadClassAnnotations(AnnotationVisitor paramAnnotationVisitor, byte[] paramArrayOfByte);
  
  public abstract void visitMembers(MemberVisitor paramMemberVisitor, byte[] paramArrayOfByte);
  
  public static abstract interface AnnotationArgumentVisitor
  {
    public abstract void visit(Name paramName, Object paramObject);
    
    public abstract AnnotationArgumentVisitor visitAnnotation(Name paramName, ClassId paramClassId);
    
    public abstract KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor visitArray(Name paramName);
    
    public abstract void visitClassLiteral(Name paramName, ClassLiteralValue paramClassLiteralValue);
    
    public abstract void visitEnd();
    
    public abstract void visitEnum(Name paramName1, ClassId paramClassId, Name paramName2);
  }
  
  public static abstract interface AnnotationArrayArgumentVisitor
  {
    public abstract void visit(Object paramObject);
    
    public abstract void visitClassLiteral(ClassLiteralValue paramClassLiteralValue);
    
    public abstract void visitEnd();
    
    public abstract void visitEnum(ClassId paramClassId, Name paramName);
  }
  
  public static abstract interface AnnotationVisitor
  {
    public abstract KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(ClassId paramClassId, SourceElement paramSourceElement);
    
    public abstract void visitEnd();
  }
  
  public static abstract interface MemberVisitor
  {
    public abstract KotlinJvmBinaryClass.AnnotationVisitor visitField(Name paramName, String paramString, Object paramObject);
    
    public abstract KotlinJvmBinaryClass.MethodAnnotationVisitor visitMethod(Name paramName, String paramString);
  }
  
  public static abstract interface MethodAnnotationVisitor
    extends KotlinJvmBinaryClass.AnnotationVisitor
  {
    public abstract KotlinJvmBinaryClass.AnnotationArgumentVisitor visitParameterAnnotation(int paramInt, ClassId paramClassId, SourceElement paramSourceElement);
  }
}

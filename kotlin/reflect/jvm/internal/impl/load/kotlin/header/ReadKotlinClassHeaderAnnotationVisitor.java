package kotlin.reflect.jvm.internal.impl.load.kotlin.header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNames;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass.AnnotationVisitor;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmBytecodeBinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMetadataVersion;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ClassLiteralValue;

public class ReadKotlinClassHeaderAnnotationVisitor
  implements KotlinJvmBinaryClass.AnnotationVisitor
{
  private static final Map<ClassId, KotlinClassHeader.Kind> HEADER_KINDS;
  private static final boolean IGNORE_OLD_METADATA = "true".equals(System.getProperty("kotlin.ignore.old.metadata"));
  private JvmBytecodeBinaryVersion bytecodeVersion = null;
  private String[] data = null;
  private int extraInt = 0;
  private String extraString = null;
  private KotlinClassHeader.Kind headerKind = null;
  private String[] incompatibleData = null;
  private int[] metadataVersionArray = null;
  private String packageName = null;
  private String[] strings = null;
  
  static
  {
    HashMap localHashMap = new HashMap();
    HEADER_KINDS = localHashMap;
    localHashMap.put(ClassId.topLevel(new FqName("kotlin.jvm.internal.KotlinClass")), KotlinClassHeader.Kind.CLASS);
    HEADER_KINDS.put(ClassId.topLevel(new FqName("kotlin.jvm.internal.KotlinFileFacade")), KotlinClassHeader.Kind.FILE_FACADE);
    HEADER_KINDS.put(ClassId.topLevel(new FqName("kotlin.jvm.internal.KotlinMultifileClass")), KotlinClassHeader.Kind.MULTIFILE_CLASS);
    HEADER_KINDS.put(ClassId.topLevel(new FqName("kotlin.jvm.internal.KotlinMultifileClassPart")), KotlinClassHeader.Kind.MULTIFILE_CLASS_PART);
    HEADER_KINDS.put(ClassId.topLevel(new FqName("kotlin.jvm.internal.KotlinSyntheticClass")), KotlinClassHeader.Kind.SYNTHETIC_CLASS);
  }
  
  public ReadKotlinClassHeaderAnnotationVisitor() {}
  
  private boolean shouldHaveData()
  {
    boolean bool;
    if ((this.headerKind != KotlinClassHeader.Kind.CLASS) && (this.headerKind != KotlinClassHeader.Kind.FILE_FACADE) && (this.headerKind != KotlinClassHeader.Kind.MULTIFILE_CLASS_PART)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public KotlinClassHeader createHeader()
  {
    if ((this.headerKind != null) && (this.metadataVersionArray != null))
    {
      Object localObject = this.metadataVersionArray;
      boolean bool;
      if ((this.extraInt & 0x8) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      JvmMetadataVersion localJvmMetadataVersion = new JvmMetadataVersion((int[])localObject, bool);
      if (!localJvmMetadataVersion.isCompatible())
      {
        this.incompatibleData = this.data;
        this.data = null;
      }
      else if ((shouldHaveData()) && (this.data == null))
      {
        return null;
      }
      KotlinClassHeader.Kind localKind = this.headerKind;
      localObject = this.bytecodeVersion;
      if (localObject == null) {
        localObject = JvmBytecodeBinaryVersion.INVALID_VERSION;
      }
      return new KotlinClassHeader(localKind, localJvmMetadataVersion, (JvmBytecodeBinaryVersion)localObject, this.data, this.incompatibleData, this.strings, this.extraString, this.extraInt, this.packageName);
    }
    return null;
  }
  
  public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(ClassId paramClassId, SourceElement paramSourceElement)
  {
    if (paramClassId == null) {
      $$$reportNull$$$0(0);
    }
    if (paramSourceElement == null) {
      $$$reportNull$$$0(1);
    }
    if (paramClassId.asSingleFqName().equals(JvmAnnotationNames.METADATA_FQ_NAME)) {
      return new KotlinMetadataArgumentVisitor(null);
    }
    if (IGNORE_OLD_METADATA) {
      return null;
    }
    if (this.headerKind != null) {
      return null;
    }
    paramClassId = (KotlinClassHeader.Kind)HEADER_KINDS.get(paramClassId);
    if (paramClassId != null)
    {
      this.headerKind = paramClassId;
      return new OldDeprecatedAnnotationArgumentVisitor(null);
    }
    return null;
  }
  
  public void visitEnd() {}
  
  private static abstract class CollectStringArrayAnnotationVisitor
    implements KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor
  {
    private final List<String> strings = new ArrayList();
    
    public CollectStringArrayAnnotationVisitor() {}
    
    public void visit(Object paramObject)
    {
      if ((paramObject instanceof String)) {
        this.strings.add((String)paramObject);
      }
    }
    
    public void visitClassLiteral(ClassLiteralValue paramClassLiteralValue)
    {
      if (paramClassLiteralValue == null) {
        $$$reportNull$$$0(2);
      }
    }
    
    public void visitEnd()
    {
      visitEnd((String[])this.strings.toArray(new String[0]));
    }
    
    protected abstract void visitEnd(String[] paramArrayOfString);
    
    public void visitEnum(ClassId paramClassId, Name paramName)
    {
      if (paramClassId == null) {
        $$$reportNull$$$0(0);
      }
      if (paramName == null) {
        $$$reportNull$$$0(1);
      }
    }
  }
  
  private class KotlinMetadataArgumentVisitor
    implements KotlinJvmBinaryClass.AnnotationArgumentVisitor
  {
    private KotlinMetadataArgumentVisitor() {}
    
    private KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor dataArrayVisitor()
    {
      new ReadKotlinClassHeaderAnnotationVisitor.CollectStringArrayAnnotationVisitor()
      {
        protected void visitEnd(String[] paramAnonymousArrayOfString)
        {
          if (paramAnonymousArrayOfString == null) {
            $$$reportNull$$$0(0);
          }
          ReadKotlinClassHeaderAnnotationVisitor.access$802(ReadKotlinClassHeaderAnnotationVisitor.this, paramAnonymousArrayOfString);
        }
      };
    }
    
    private KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor stringsArrayVisitor()
    {
      new ReadKotlinClassHeaderAnnotationVisitor.CollectStringArrayAnnotationVisitor()
      {
        protected void visitEnd(String[] paramAnonymousArrayOfString)
        {
          if (paramAnonymousArrayOfString == null) {
            $$$reportNull$$$0(0);
          }
          ReadKotlinClassHeaderAnnotationVisitor.access$902(ReadKotlinClassHeaderAnnotationVisitor.this, paramAnonymousArrayOfString);
        }
      };
    }
    
    public void visit(Name paramName, Object paramObject)
    {
      if (paramName == null) {
        return;
      }
      paramName = paramName.asString();
      if ("k".equals(paramName))
      {
        if ((paramObject instanceof Integer)) {
          ReadKotlinClassHeaderAnnotationVisitor.access$202(ReadKotlinClassHeaderAnnotationVisitor.this, KotlinClassHeader.Kind.getById(((Integer)paramObject).intValue()));
        }
      }
      else if ("mv".equals(paramName))
      {
        if ((paramObject instanceof int[])) {
          ReadKotlinClassHeaderAnnotationVisitor.access$302(ReadKotlinClassHeaderAnnotationVisitor.this, (int[])paramObject);
        }
      }
      else if ("bv".equals(paramName))
      {
        if ((paramObject instanceof int[])) {
          ReadKotlinClassHeaderAnnotationVisitor.access$402(ReadKotlinClassHeaderAnnotationVisitor.this, new JvmBytecodeBinaryVersion((int[])paramObject));
        }
      }
      else if ("xs".equals(paramName))
      {
        if ((paramObject instanceof String)) {
          ReadKotlinClassHeaderAnnotationVisitor.access$502(ReadKotlinClassHeaderAnnotationVisitor.this, (String)paramObject);
        }
      }
      else if ("xi".equals(paramName))
      {
        if ((paramObject instanceof Integer)) {
          ReadKotlinClassHeaderAnnotationVisitor.access$602(ReadKotlinClassHeaderAnnotationVisitor.this, ((Integer)paramObject).intValue());
        }
      }
      else if (("pn".equals(paramName)) && ((paramObject instanceof String))) {
        ReadKotlinClassHeaderAnnotationVisitor.access$702(ReadKotlinClassHeaderAnnotationVisitor.this, (String)paramObject);
      }
    }
    
    public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(Name paramName, ClassId paramClassId)
    {
      if (paramName == null) {
        $$$reportNull$$$0(6);
      }
      if (paramClassId == null) {
        $$$reportNull$$$0(7);
      }
      return null;
    }
    
    public KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor visitArray(Name paramName)
    {
      if (paramName == null) {
        $$$reportNull$$$0(2);
      }
      paramName = paramName.asString();
      if ("d1".equals(paramName)) {
        return dataArrayVisitor();
      }
      if ("d2".equals(paramName)) {
        return stringsArrayVisitor();
      }
      return null;
    }
    
    public void visitClassLiteral(Name paramName, ClassLiteralValue paramClassLiteralValue)
    {
      if (paramName == null) {
        $$$reportNull$$$0(0);
      }
      if (paramClassLiteralValue == null) {
        $$$reportNull$$$0(1);
      }
    }
    
    public void visitEnd() {}
    
    public void visitEnum(Name paramName1, ClassId paramClassId, Name paramName2)
    {
      if (paramName1 == null) {
        $$$reportNull$$$0(3);
      }
      if (paramClassId == null) {
        $$$reportNull$$$0(4);
      }
      if (paramName2 == null) {
        $$$reportNull$$$0(5);
      }
    }
  }
  
  private class OldDeprecatedAnnotationArgumentVisitor
    implements KotlinJvmBinaryClass.AnnotationArgumentVisitor
  {
    private OldDeprecatedAnnotationArgumentVisitor() {}
    
    private KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor dataArrayVisitor()
    {
      new ReadKotlinClassHeaderAnnotationVisitor.CollectStringArrayAnnotationVisitor()
      {
        protected void visitEnd(String[] paramAnonymousArrayOfString)
        {
          if (paramAnonymousArrayOfString == null) {
            $$$reportNull$$$0(0);
          }
          ReadKotlinClassHeaderAnnotationVisitor.access$802(ReadKotlinClassHeaderAnnotationVisitor.this, paramAnonymousArrayOfString);
        }
      };
    }
    
    private KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor stringsArrayVisitor()
    {
      new ReadKotlinClassHeaderAnnotationVisitor.CollectStringArrayAnnotationVisitor()
      {
        protected void visitEnd(String[] paramAnonymousArrayOfString)
        {
          if (paramAnonymousArrayOfString == null) {
            $$$reportNull$$$0(0);
          }
          ReadKotlinClassHeaderAnnotationVisitor.access$902(ReadKotlinClassHeaderAnnotationVisitor.this, paramAnonymousArrayOfString);
        }
      };
    }
    
    public void visit(Name paramName, Object paramObject)
    {
      if (paramName == null) {
        return;
      }
      paramName = paramName.asString();
      if ("version".equals(paramName))
      {
        if ((paramObject instanceof int[]))
        {
          paramName = ReadKotlinClassHeaderAnnotationVisitor.this;
          paramObject = (int[])paramObject;
          ReadKotlinClassHeaderAnnotationVisitor.access$302(paramName, paramObject);
          if (ReadKotlinClassHeaderAnnotationVisitor.this.bytecodeVersion == null) {
            ReadKotlinClassHeaderAnnotationVisitor.access$402(ReadKotlinClassHeaderAnnotationVisitor.this, new JvmBytecodeBinaryVersion(paramObject));
          }
        }
      }
      else if ("multifileClassName".equals(paramName))
      {
        ReadKotlinClassHeaderAnnotationVisitor localReadKotlinClassHeaderAnnotationVisitor = ReadKotlinClassHeaderAnnotationVisitor.this;
        if ((paramObject instanceof String)) {
          paramName = (String)paramObject;
        } else {
          paramName = null;
        }
        ReadKotlinClassHeaderAnnotationVisitor.access$502(localReadKotlinClassHeaderAnnotationVisitor, paramName);
      }
    }
    
    public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(Name paramName, ClassId paramClassId)
    {
      if (paramName == null) {
        $$$reportNull$$$0(6);
      }
      if (paramClassId == null) {
        $$$reportNull$$$0(7);
      }
      return null;
    }
    
    public KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor visitArray(Name paramName)
    {
      if (paramName == null) {
        $$$reportNull$$$0(2);
      }
      paramName = paramName.asString();
      if ((!"data".equals(paramName)) && (!"filePartClassNames".equals(paramName)))
      {
        if ("strings".equals(paramName)) {
          return stringsArrayVisitor();
        }
        return null;
      }
      return dataArrayVisitor();
    }
    
    public void visitClassLiteral(Name paramName, ClassLiteralValue paramClassLiteralValue)
    {
      if (paramName == null) {
        $$$reportNull$$$0(0);
      }
      if (paramClassLiteralValue == null) {
        $$$reportNull$$$0(1);
      }
    }
    
    public void visitEnd() {}
    
    public void visitEnum(Name paramName1, ClassId paramClassId, Name paramName2)
    {
      if (paramName1 == null) {
        $$$reportNull$$$0(3);
      }
      if (paramClassId == null) {
        $$$reportNull$$$0(4);
      }
      if (paramName2 == null) {
        $$$reportNull$$$0(5);
      }
    }
  }
}

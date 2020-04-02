package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.load.java.components.DescriptorResolverUtils;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.AnnotationValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ByteValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ClassLiteralValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValueFactory;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ErrorValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ErrorValue.Companion;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.LongValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ShortValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.UByteValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.UIntValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ULongValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.UShortValue;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.AnnotationDeserializer;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.text.StringsKt;

public final class BinaryClassAnnotationAndConstantLoaderImpl
  extends AbstractBinaryClassAnnotationAndConstantLoader<AnnotationDescriptor, ConstantValue<?>>
{
  private final AnnotationDeserializer annotationDeserializer;
  private final ModuleDescriptor module;
  private final NotFoundClasses notFoundClasses;
  
  public BinaryClassAnnotationAndConstantLoaderImpl(ModuleDescriptor paramModuleDescriptor, NotFoundClasses paramNotFoundClasses, StorageManager paramStorageManager, KotlinClassFinder paramKotlinClassFinder)
  {
    super(paramStorageManager, paramKotlinClassFinder);
    this.module = paramModuleDescriptor;
    this.notFoundClasses = paramNotFoundClasses;
    this.annotationDeserializer = new AnnotationDeserializer(paramModuleDescriptor, paramNotFoundClasses);
  }
  
  private final ClassDescriptor resolveClass(ClassId paramClassId)
  {
    return FindClassInModuleKt.findNonGenericClassAcrossDependencies(this.module, paramClassId, this.notFoundClasses);
  }
  
  protected KotlinJvmBinaryClass.AnnotationArgumentVisitor loadAnnotation(ClassId paramClassId, final SourceElement paramSourceElement, final List<AnnotationDescriptor> paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramClassId, "annotationClassId");
    Intrinsics.checkParameterIsNotNull(paramSourceElement, "source");
    Intrinsics.checkParameterIsNotNull(paramList, "result");
    (KotlinJvmBinaryClass.AnnotationArgumentVisitor)new KotlinJvmBinaryClass.AnnotationArgumentVisitor()
    {
      private final HashMap<Name, ConstantValue<?>> arguments = new HashMap();
      
      private final ConstantValue<?> createConstant(Name paramAnonymousName, Object paramAnonymousObject)
      {
        paramAnonymousObject = ConstantValueFactory.INSTANCE.createConstantValue(paramAnonymousObject);
        if (paramAnonymousObject != null)
        {
          paramAnonymousName = paramAnonymousObject;
        }
        else
        {
          paramAnonymousObject = ErrorValue.Companion;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Unsupported annotation argument: ");
          localStringBuilder.append(paramAnonymousName);
          paramAnonymousName = (ConstantValue)paramAnonymousObject.create(localStringBuilder.toString());
        }
        return paramAnonymousName;
      }
      
      public void visit(Name paramAnonymousName, Object paramAnonymousObject)
      {
        if (paramAnonymousName != null) {
          ((Map)this.arguments).put(paramAnonymousName, createConstant(paramAnonymousName, paramAnonymousObject));
        }
      }
      
      public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(final Name paramAnonymousName, final ClassId paramAnonymousClassId)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousName, "name");
        Intrinsics.checkParameterIsNotNull(paramAnonymousClassId, "classId");
        final ArrayList localArrayList = new ArrayList();
        BinaryClassAnnotationAndConstantLoaderImpl localBinaryClassAnnotationAndConstantLoaderImpl = this.this$0;
        SourceElement localSourceElement = SourceElement.NO_SOURCE;
        Intrinsics.checkExpressionValueIsNotNull(localSourceElement, "SourceElement.NO_SOURCE");
        paramAnonymousClassId = localBinaryClassAnnotationAndConstantLoaderImpl.loadAnnotation(paramAnonymousClassId, localSourceElement, (List)localArrayList);
        if (paramAnonymousClassId == null) {
          Intrinsics.throwNpe();
        }
        (KotlinJvmBinaryClass.AnnotationArgumentVisitor)new KotlinJvmBinaryClass.AnnotationArgumentVisitor()
        {
          public void visit(Name paramAnonymous2Name, Object paramAnonymous2Object)
          {
            this.$$delegate_0.visit(paramAnonymous2Name, paramAnonymous2Object);
          }
          
          public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(Name paramAnonymous2Name, ClassId paramAnonymous2ClassId)
          {
            Intrinsics.checkParameterIsNotNull(paramAnonymous2Name, "name");
            Intrinsics.checkParameterIsNotNull(paramAnonymous2ClassId, "classId");
            return this.$$delegate_0.visitAnnotation(paramAnonymous2Name, paramAnonymous2ClassId);
          }
          
          public KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor visitArray(Name paramAnonymous2Name)
          {
            Intrinsics.checkParameterIsNotNull(paramAnonymous2Name, "name");
            return this.$$delegate_0.visitArray(paramAnonymous2Name);
          }
          
          public void visitClassLiteral(Name paramAnonymous2Name, ClassLiteralValue paramAnonymous2ClassLiteralValue)
          {
            Intrinsics.checkParameterIsNotNull(paramAnonymous2Name, "name");
            Intrinsics.checkParameterIsNotNull(paramAnonymous2ClassLiteralValue, "value");
            this.$$delegate_0.visitClassLiteral(paramAnonymous2Name, paramAnonymous2ClassLiteralValue);
          }
          
          public void visitEnd()
          {
            paramAnonymousClassId.visitEnd();
            ((Map)BinaryClassAnnotationAndConstantLoaderImpl.loadAnnotation.1.access$getArguments$p(this.this$0)).put(paramAnonymousName, new AnnotationValue((AnnotationDescriptor)kotlin.collections.CollectionsKt.single((List)localArrayList)));
          }
          
          public void visitEnum(Name paramAnonymous2Name1, ClassId paramAnonymous2ClassId, Name paramAnonymous2Name2)
          {
            Intrinsics.checkParameterIsNotNull(paramAnonymous2Name1, "name");
            Intrinsics.checkParameterIsNotNull(paramAnonymous2ClassId, "enumClassId");
            Intrinsics.checkParameterIsNotNull(paramAnonymous2Name2, "enumEntryName");
            this.$$delegate_0.visitEnum(paramAnonymous2Name1, paramAnonymous2ClassId, paramAnonymous2Name2);
          }
        };
      }
      
      public KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor visitArray(final Name paramAnonymousName)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousName, "name");
        (KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor)new KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor()
        {
          private final ArrayList<ConstantValue<?>> elements = new ArrayList();
          
          public void visit(Object paramAnonymous2Object)
          {
            this.elements.add(BinaryClassAnnotationAndConstantLoaderImpl.loadAnnotation.1.access$createConstant(this.this$0, paramAnonymousName, paramAnonymous2Object));
          }
          
          public void visitClassLiteral(ClassLiteralValue paramAnonymous2ClassLiteralValue)
          {
            Intrinsics.checkParameterIsNotNull(paramAnonymous2ClassLiteralValue, "value");
            this.elements.add(new KClassValue(paramAnonymous2ClassLiteralValue));
          }
          
          public void visitEnd()
          {
            Object localObject = DescriptorResolverUtils.getAnnotationParameterByName(paramAnonymousName, this.this$0.$annotationClass);
            if (localObject != null)
            {
              Map localMap = (Map)BinaryClassAnnotationAndConstantLoaderImpl.loadAnnotation.1.access$getArguments$p(this.this$0);
              Name localName = paramAnonymousName;
              ConstantValueFactory localConstantValueFactory = ConstantValueFactory.INSTANCE;
              List localList = kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.compact(this.elements);
              localObject = ((ValueParameterDescriptor)localObject).getType();
              Intrinsics.checkExpressionValueIsNotNull(localObject, "parameter.type");
              localMap.put(localName, localConstantValueFactory.createArrayValue(localList, (KotlinType)localObject));
            }
          }
          
          public void visitEnum(ClassId paramAnonymous2ClassId, Name paramAnonymous2Name)
          {
            Intrinsics.checkParameterIsNotNull(paramAnonymous2ClassId, "enumClassId");
            Intrinsics.checkParameterIsNotNull(paramAnonymous2Name, "enumEntryName");
            this.elements.add(new EnumValue(paramAnonymous2ClassId, paramAnonymous2Name));
          }
        };
      }
      
      public void visitClassLiteral(Name paramAnonymousName, ClassLiteralValue paramAnonymousClassLiteralValue)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousName, "name");
        Intrinsics.checkParameterIsNotNull(paramAnonymousClassLiteralValue, "value");
        ((Map)this.arguments).put(paramAnonymousName, new KClassValue(paramAnonymousClassLiteralValue));
      }
      
      public void visitEnd()
      {
        paramList.add(new AnnotationDescriptorImpl((KotlinType)this.$annotationClass.getDefaultType(), (Map)this.arguments, paramSourceElement));
      }
      
      public void visitEnum(Name paramAnonymousName1, ClassId paramAnonymousClassId, Name paramAnonymousName2)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousName1, "name");
        Intrinsics.checkParameterIsNotNull(paramAnonymousClassId, "enumClassId");
        Intrinsics.checkParameterIsNotNull(paramAnonymousName2, "enumEntryName");
        ((Map)this.arguments).put(paramAnonymousName1, new EnumValue(paramAnonymousClassId, paramAnonymousName2));
      }
    };
  }
  
  protected ConstantValue<?> loadConstant(String paramString, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "desc");
    Intrinsics.checkParameterIsNotNull(paramObject, "initializer");
    CharSequence localCharSequence1 = (CharSequence)"ZBCS";
    CharSequence localCharSequence2 = (CharSequence)paramString;
    boolean bool = false;
    Object localObject = paramObject;
    if (StringsKt.contains$default(localCharSequence1, localCharSequence2, false, 2, null))
    {
      int i = ((Integer)paramObject).intValue();
      int j = paramString.hashCode();
      if (j != 66)
      {
        if (j != 67)
        {
          if (j != 83)
          {
            if ((j == 90) && (paramString.equals("Z")))
            {
              if (i != 0) {
                bool = true;
              }
              localObject = Boolean.valueOf(bool);
              break label184;
            }
          }
          else if (paramString.equals("S"))
          {
            localObject = Short.valueOf((short)i);
            break label184;
          }
        }
        else if (paramString.equals("C"))
        {
          localObject = Character.valueOf((char)i);
          break label184;
        }
      }
      else if (paramString.equals("B"))
      {
        localObject = Byte.valueOf((byte)i);
        break label184;
      }
      throw ((Throwable)new AssertionError(paramString));
    }
    label184:
    return ConstantValueFactory.INSTANCE.createConstantValue(localObject);
  }
  
  protected AnnotationDescriptor loadTypeAnnotation(ProtoBuf.Annotation paramAnnotation, NameResolver paramNameResolver)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotation, "proto");
    Intrinsics.checkParameterIsNotNull(paramNameResolver, "nameResolver");
    return this.annotationDeserializer.deserializeAnnotation(paramAnnotation, paramNameResolver);
  }
  
  protected ConstantValue<?> transformToUnsignedConstant(ConstantValue<?> paramConstantValue)
  {
    Intrinsics.checkParameterIsNotNull(paramConstantValue, "constant");
    Object localObject;
    if ((paramConstantValue instanceof ByteValue))
    {
      localObject = (ConstantValue)new UByteValue(((Number)((ByteValue)paramConstantValue).getValue()).byteValue());
    }
    else if ((paramConstantValue instanceof ShortValue))
    {
      localObject = (ConstantValue)new UShortValue(((Number)((ShortValue)paramConstantValue).getValue()).shortValue());
    }
    else if ((paramConstantValue instanceof IntValue))
    {
      localObject = (ConstantValue)new UIntValue(((Number)((IntValue)paramConstantValue).getValue()).intValue());
    }
    else
    {
      localObject = paramConstantValue;
      if ((paramConstantValue instanceof LongValue)) {
        localObject = (ConstantValue)new ULongValue(((Number)((LongValue)paramConstantValue).getValue()).longValue());
      }
    }
    return localObject;
  }
}

package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.NullabilityQualifierWithApplicability;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ArrayValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.utils.Jsr305State;
import kotlin.reflect.jvm.internal.impl.utils.ReportLevel;

public final class AnnotationTypeQualifierResolver
{
  private final boolean disabled;
  private final Jsr305State jsr305State;
  private final MemoizedFunctionToNullable<ClassDescriptor, AnnotationDescriptor> resolvedNicknames;
  
  public AnnotationTypeQualifierResolver(StorageManager paramStorageManager, Jsr305State paramJsr305State)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc 23
    //   3: invokestatic 29	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_2
    //   7: ldc 30
    //   9: invokestatic 29	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   12: aload_0
    //   13: invokespecial 33	java/lang/Object:<init>	()V
    //   16: aload_0
    //   17: aload_2
    //   18: putfield 35	kotlin/reflect/jvm/internal/impl/load/java/AnnotationTypeQualifierResolver:jsr305State	Lkotlin/reflect/jvm/internal/impl/utils/Jsr305State;
    //   21: aload_0
    //   22: aload_1
    //   23: new 12	kotlin/reflect/jvm/internal/impl/load/java/AnnotationTypeQualifierResolver$resolvedNicknames$1
    //   26: dup
    //   27: aload_0
    //   28: checkcast 2	kotlin/reflect/jvm/internal/impl/load/java/AnnotationTypeQualifierResolver
    //   31: invokespecial 38	kotlin/reflect/jvm/internal/impl/load/java/AnnotationTypeQualifierResolver$resolvedNicknames$1:<init>	(Lkotlin/reflect/jvm/internal/impl/load/java/AnnotationTypeQualifierResolver;)V
    //   34: checkcast 40	kotlin/jvm/functions/Function1
    //   37: invokeinterface 46 2 0
    //   42: putfield 48	kotlin/reflect/jvm/internal/impl/load/java/AnnotationTypeQualifierResolver:resolvedNicknames	Lkotlin/reflect/jvm/internal/impl/storage/MemoizedFunctionToNullable;
    //   45: aload_0
    //   46: aload_0
    //   47: getfield 35	kotlin/reflect/jvm/internal/impl/load/java/AnnotationTypeQualifierResolver:jsr305State	Lkotlin/reflect/jvm/internal/impl/utils/Jsr305State;
    //   50: invokevirtual 54	kotlin/reflect/jvm/internal/impl/utils/Jsr305State:getDisabled	()Z
    //   53: putfield 56	kotlin/reflect/jvm/internal/impl/load/java/AnnotationTypeQualifierResolver:disabled	Z
    //   56: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	57	0	this	AnnotationTypeQualifierResolver
    //   0	57	1	paramStorageManager	StorageManager
    //   0	57	2	paramJsr305State	Jsr305State
  }
  
  private final AnnotationDescriptor computeTypeQualifierNickname(ClassDescriptor paramClassDescriptor)
  {
    boolean bool = paramClassDescriptor.getAnnotations().hasAnnotation(AnnotationTypeQualifierResolverKt.getTYPE_QUALIFIER_NICKNAME_FQNAME());
    Object localObject = null;
    if (!bool) {
      return null;
    }
    Iterator localIterator = ((Iterable)paramClassDescriptor.getAnnotations()).iterator();
    do
    {
      paramClassDescriptor = localObject;
      if (!localIterator.hasNext()) {
        break;
      }
      paramClassDescriptor = (AnnotationDescriptor)localIterator.next();
      paramClassDescriptor = ((AnnotationTypeQualifierResolver)this).resolveTypeQualifierAnnotation(paramClassDescriptor);
    } while (paramClassDescriptor == null);
    return paramClassDescriptor;
  }
  
  private final List<QualifierApplicabilityType> mapConstantToQualifierApplicabilityTypes(ConstantValue<?> paramConstantValue)
  {
    if ((paramConstantValue instanceof ArrayValue))
    {
      Object localObject = (Iterable)((ArrayValue)paramConstantValue).getValue();
      paramConstantValue = (Collection)new ArrayList();
      localObject = ((Iterable)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        CollectionsKt.addAll(paramConstantValue, (Iterable)mapConstantToQualifierApplicabilityTypes((ConstantValue)((Iterator)localObject).next()));
      }
      paramConstantValue = (List)paramConstantValue;
    }
    else if ((paramConstantValue instanceof EnumValue))
    {
      paramConstantValue = ((EnumValue)paramConstantValue).getEnumEntryName().getIdentifier();
      switch (paramConstantValue.hashCode())
      {
      default: 
        break;
      case 446088073: 
        if (paramConstantValue.equals("PARAMETER")) {
          paramConstantValue = QualifierApplicabilityType.VALUE_PARAMETER;
        }
        break;
      case 107598562: 
        if (paramConstantValue.equals("TYPE_USE")) {
          paramConstantValue = QualifierApplicabilityType.TYPE_USE;
        }
        break;
      case 66889946: 
        if (paramConstantValue.equals("FIELD")) {
          paramConstantValue = QualifierApplicabilityType.FIELD;
        }
        break;
      case -2024225567: 
        if (paramConstantValue.equals("METHOD")) {
          paramConstantValue = QualifierApplicabilityType.METHOD_RETURN_TYPE;
        }
        break;
      }
      paramConstantValue = null;
      paramConstantValue = CollectionsKt.listOfNotNull(paramConstantValue);
    }
    else
    {
      paramConstantValue = CollectionsKt.emptyList();
    }
    return paramConstantValue;
  }
  
  private final ReportLevel migrationAnnotationStatus(ClassDescriptor paramClassDescriptor)
  {
    paramClassDescriptor = paramClassDescriptor.getAnnotations().findAnnotation(AnnotationTypeQualifierResolverKt.getMIGRATION_ANNOTATION_FQNAME());
    Object localObject1 = null;
    if (paramClassDescriptor != null) {
      paramClassDescriptor = DescriptorUtilsKt.firstArgument(paramClassDescriptor);
    } else {
      paramClassDescriptor = null;
    }
    Object localObject2 = paramClassDescriptor;
    if (!(paramClassDescriptor instanceof EnumValue)) {
      localObject2 = null;
    }
    localObject2 = (EnumValue)localObject2;
    paramClassDescriptor = localObject1;
    if (localObject2 != null)
    {
      paramClassDescriptor = this.jsr305State.getMigration();
      if (paramClassDescriptor != null) {
        return paramClassDescriptor;
      }
      localObject2 = ((EnumValue)localObject2).getEnumEntryName().asString();
      int i = ((String)localObject2).hashCode();
      if (i != -2137067054)
      {
        if (i != -1838656823)
        {
          if (i != 2656902)
          {
            paramClassDescriptor = localObject1;
          }
          else
          {
            paramClassDescriptor = localObject1;
            if (((String)localObject2).equals("WARN")) {
              paramClassDescriptor = ReportLevel.WARN;
            }
          }
        }
        else
        {
          paramClassDescriptor = localObject1;
          if (((String)localObject2).equals("STRICT")) {
            paramClassDescriptor = ReportLevel.STRICT;
          }
        }
      }
      else
      {
        paramClassDescriptor = localObject1;
        if (((String)localObject2).equals("IGNORE")) {
          paramClassDescriptor = ReportLevel.IGNORE;
        }
      }
    }
    return paramClassDescriptor;
  }
  
  private final AnnotationDescriptor resolveTypeQualifierNickname(ClassDescriptor paramClassDescriptor)
  {
    if (paramClassDescriptor.getKind() != ClassKind.ANNOTATION_CLASS) {
      return null;
    }
    return (AnnotationDescriptor)this.resolvedNicknames.invoke(paramClassDescriptor);
  }
  
  public final boolean getDisabled()
  {
    return this.disabled;
  }
  
  public final ReportLevel resolveJsr305AnnotationState(AnnotationDescriptor paramAnnotationDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotationDescriptor, "annotationDescriptor");
    paramAnnotationDescriptor = resolveJsr305CustomState(paramAnnotationDescriptor);
    if (paramAnnotationDescriptor != null) {
      return paramAnnotationDescriptor;
    }
    return this.jsr305State.getGlobal();
  }
  
  public final ReportLevel resolveJsr305CustomState(AnnotationDescriptor paramAnnotationDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotationDescriptor, "annotationDescriptor");
    Map localMap = this.jsr305State.getUser();
    Object localObject1 = paramAnnotationDescriptor.getFqName();
    Object localObject2 = null;
    if (localObject1 != null) {
      localObject1 = ((FqName)localObject1).asString();
    } else {
      localObject1 = null;
    }
    localObject1 = (ReportLevel)localMap.get(localObject1);
    if (localObject1 != null) {
      return localObject1;
    }
    localObject1 = DescriptorUtilsKt.getAnnotationClass(paramAnnotationDescriptor);
    paramAnnotationDescriptor = localObject2;
    if (localObject1 != null) {
      paramAnnotationDescriptor = migrationAnnotationStatus((ClassDescriptor)localObject1);
    }
    return paramAnnotationDescriptor;
  }
  
  public final NullabilityQualifierWithApplicability resolveQualifierBuiltInDefaultAnnotation(AnnotationDescriptor paramAnnotationDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotationDescriptor, "annotationDescriptor");
    if (this.jsr305State.getDisabled()) {
      return null;
    }
    Object localObject = (NullabilityQualifierWithApplicability)AnnotationTypeQualifierResolverKt.getBUILT_IN_TYPE_QUALIFIER_DEFAULT_ANNOTATIONS().get(paramAnnotationDescriptor.getFqName());
    if (localObject != null)
    {
      NullabilityQualifierWithMigrationStatus localNullabilityQualifierWithMigrationStatus = ((NullabilityQualifierWithApplicability)localObject).component1();
      localObject = ((NullabilityQualifierWithApplicability)localObject).component2();
      paramAnnotationDescriptor = resolveJsr305AnnotationState(paramAnnotationDescriptor);
      int i;
      if (paramAnnotationDescriptor != ReportLevel.IGNORE) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        paramAnnotationDescriptor = null;
      }
      if (paramAnnotationDescriptor != null) {
        return new NullabilityQualifierWithApplicability(NullabilityQualifierWithMigrationStatus.copy$default(localNullabilityQualifierWithMigrationStatus, null, paramAnnotationDescriptor.isWarning(), 1, null), (Collection)localObject);
      }
      return null;
    }
    return (NullabilityQualifierWithApplicability)null;
  }
  
  public final AnnotationDescriptor resolveTypeQualifierAnnotation(AnnotationDescriptor paramAnnotationDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotationDescriptor, "annotationDescriptor");
    if (this.jsr305State.getDisabled()) {
      return null;
    }
    ClassDescriptor localClassDescriptor = DescriptorUtilsKt.getAnnotationClass(paramAnnotationDescriptor);
    if (localClassDescriptor != null)
    {
      if (AnnotationTypeQualifierResolverKt.access$isAnnotatedWithTypeQualifier$p(localClassDescriptor)) {
        return paramAnnotationDescriptor;
      }
      return resolveTypeQualifierNickname(localClassDescriptor);
    }
    return null;
  }
  
  public final TypeQualifierWithApplicability resolveTypeQualifierDefaultAnnotation(AnnotationDescriptor paramAnnotationDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotationDescriptor, "annotationDescriptor");
    if (this.jsr305State.getDisabled()) {
      return null;
    }
    Object localObject1 = DescriptorUtilsKt.getAnnotationClass(paramAnnotationDescriptor);
    if (localObject1 != null)
    {
      if (!((ClassDescriptor)localObject1).getAnnotations().hasAnnotation(AnnotationTypeQualifierResolverKt.getTYPE_QUALIFIER_DEFAULT_FQNAME())) {
        localObject1 = null;
      }
      if (localObject1 != null)
      {
        paramAnnotationDescriptor = DescriptorUtilsKt.getAnnotationClass(paramAnnotationDescriptor);
        if (paramAnnotationDescriptor == null) {
          Intrinsics.throwNpe();
        }
        paramAnnotationDescriptor = paramAnnotationDescriptor.getAnnotations().findAnnotation(AnnotationTypeQualifierResolverKt.getTYPE_QUALIFIER_DEFAULT_FQNAME());
        if (paramAnnotationDescriptor == null) {
          Intrinsics.throwNpe();
        }
        paramAnnotationDescriptor = paramAnnotationDescriptor.getAllValueArguments();
        Collection localCollection = (Collection)new ArrayList();
        Iterator localIterator = paramAnnotationDescriptor.entrySet().iterator();
        while (localIterator.hasNext())
        {
          Object localObject2 = (Map.Entry)localIterator.next();
          paramAnnotationDescriptor = (Name)((Map.Entry)localObject2).getKey();
          localObject2 = (ConstantValue)((Map.Entry)localObject2).getValue();
          if (Intrinsics.areEqual(paramAnnotationDescriptor, JvmAnnotationNames.DEFAULT_ANNOTATION_MEMBER_NAME)) {
            paramAnnotationDescriptor = mapConstantToQualifierApplicabilityTypes((ConstantValue)localObject2);
          } else {
            paramAnnotationDescriptor = CollectionsKt.emptyList();
          }
          CollectionsKt.addAll(localCollection, (Iterable)paramAnnotationDescriptor);
        }
        paramAnnotationDescriptor = ((Iterable)localCollection).iterator();
        int i = 0;
        while (paramAnnotationDescriptor.hasNext()) {
          i |= 1 << ((QualifierApplicabilityType)paramAnnotationDescriptor.next()).ordinal();
        }
        localObject1 = ((Iterable)((ClassDescriptor)localObject1).getAnnotations()).iterator();
        while (((Iterator)localObject1).hasNext())
        {
          paramAnnotationDescriptor = ((Iterator)localObject1).next();
          int j;
          if (resolveTypeQualifierAnnotation((AnnotationDescriptor)paramAnnotationDescriptor) != null) {
            j = 1;
          } else {
            j = 0;
          }
          if (j != 0) {
            break label307;
          }
        }
        paramAnnotationDescriptor = null;
        label307:
        paramAnnotationDescriptor = (AnnotationDescriptor)paramAnnotationDescriptor;
        if (paramAnnotationDescriptor != null) {
          return new TypeQualifierWithApplicability(paramAnnotationDescriptor, i);
        }
      }
    }
    return null;
  }
  
  public static enum QualifierApplicabilityType
  {
    static
    {
      QualifierApplicabilityType localQualifierApplicabilityType1 = new QualifierApplicabilityType("METHOD_RETURN_TYPE", 0);
      METHOD_RETURN_TYPE = localQualifierApplicabilityType1;
      QualifierApplicabilityType localQualifierApplicabilityType2 = new QualifierApplicabilityType("VALUE_PARAMETER", 1);
      VALUE_PARAMETER = localQualifierApplicabilityType2;
      QualifierApplicabilityType localQualifierApplicabilityType3 = new QualifierApplicabilityType("FIELD", 2);
      FIELD = localQualifierApplicabilityType3;
      QualifierApplicabilityType localQualifierApplicabilityType4 = new QualifierApplicabilityType("TYPE_USE", 3);
      TYPE_USE = localQualifierApplicabilityType4;
      $VALUES = new QualifierApplicabilityType[] { localQualifierApplicabilityType1, localQualifierApplicabilityType2, localQualifierApplicabilityType3, localQualifierApplicabilityType4 };
    }
    
    private QualifierApplicabilityType() {}
  }
  
  public static final class TypeQualifierWithApplicability
  {
    private final int applicability;
    private final AnnotationDescriptor typeQualifier;
    
    public TypeQualifierWithApplicability(AnnotationDescriptor paramAnnotationDescriptor, int paramInt)
    {
      this.typeQualifier = paramAnnotationDescriptor;
      this.applicability = paramInt;
    }
    
    private final boolean isApplicableConsideringMask(AnnotationTypeQualifierResolver.QualifierApplicabilityType paramQualifierApplicabilityType)
    {
      int i = this.applicability;
      int j = paramQualifierApplicabilityType.ordinal();
      boolean bool = true;
      if ((1 << j & i) == 0) {
        bool = false;
      }
      return bool;
    }
    
    private final boolean isApplicableTo(AnnotationTypeQualifierResolver.QualifierApplicabilityType paramQualifierApplicabilityType)
    {
      boolean bool;
      if ((!isApplicableConsideringMask(AnnotationTypeQualifierResolver.QualifierApplicabilityType.TYPE_USE)) && (!isApplicableConsideringMask(paramQualifierApplicabilityType))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public final AnnotationDescriptor component1()
    {
      return this.typeQualifier;
    }
    
    public final List<AnnotationTypeQualifierResolver.QualifierApplicabilityType> component2()
    {
      AnnotationTypeQualifierResolver.QualifierApplicabilityType[] arrayOfQualifierApplicabilityType = AnnotationTypeQualifierResolver.QualifierApplicabilityType.values();
      Collection localCollection = (Collection)new ArrayList();
      int i = arrayOfQualifierApplicabilityType.length;
      for (int j = 0; j < i; j++)
      {
        AnnotationTypeQualifierResolver.QualifierApplicabilityType localQualifierApplicabilityType = arrayOfQualifierApplicabilityType[j];
        if (((TypeQualifierWithApplicability)this).isApplicableTo(localQualifierApplicabilityType)) {
          localCollection.add(localQualifierApplicabilityType);
        }
      }
      return (List)localCollection;
    }
  }
}

package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.Variance;

public final class TypeMappingMode
{
  public static final TypeMappingMode CLASS_DECLARATION = new TypeMappingMode(false, true, false, false, false, GENERIC_ARGUMENT, false, null, null, 476, null);
  public static final Companion Companion = new Companion(null);
  public static final TypeMappingMode DEFAULT;
  public static final TypeMappingMode GENERIC_ARGUMENT = new TypeMappingMode(false, false, false, false, false, null, false, null, null, 511, null);
  public static final TypeMappingMode RETURN_TYPE_BOXED = new TypeMappingMode(false, true, false, false, false, null, false, null, null, 509, null);
  public static final TypeMappingMode SUPER_TYPE = new TypeMappingMode(false, false, false, true, false, GENERIC_ARGUMENT, false, null, null, 471, null);
  public static final TypeMappingMode SUPER_TYPE_KOTLIN_COLLECTIONS_AS_IS = new TypeMappingMode(false, false, false, true, false, GENERIC_ARGUMENT, false, null, null, 407, null);
  public static final TypeMappingMode VALUE_FOR_ANNOTATION = new TypeMappingMode(false, false, true, false, false, new TypeMappingMode(false, false, true, false, false, GENERIC_ARGUMENT, false, null, null, 475, null), false, null, null, 472, null);
  private final TypeMappingMode genericArgumentMode;
  private final TypeMappingMode genericContravariantArgumentMode;
  private final TypeMappingMode genericInvariantArgumentMode;
  private final boolean isForAnnotationParameter;
  private final boolean kotlinCollectionsToJavaCollections;
  private final boolean needInlineClassWrapping;
  private final boolean needPrimitiveBoxing;
  private final boolean skipDeclarationSiteWildcards;
  private final boolean skipDeclarationSiteWildcardsIfPossible;
  
  static
  {
    DEFAULT = new TypeMappingMode(false, false, false, false, false, GENERIC_ARGUMENT, false, null, null, 476, null);
  }
  
  private TypeMappingMode(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, TypeMappingMode paramTypeMappingMode1, boolean paramBoolean6, TypeMappingMode paramTypeMappingMode2, TypeMappingMode paramTypeMappingMode3)
  {
    this.needPrimitiveBoxing = paramBoolean1;
    this.needInlineClassWrapping = paramBoolean2;
    this.isForAnnotationParameter = paramBoolean3;
    this.skipDeclarationSiteWildcards = paramBoolean4;
    this.skipDeclarationSiteWildcardsIfPossible = paramBoolean5;
    this.genericArgumentMode = paramTypeMappingMode1;
    this.kotlinCollectionsToJavaCollections = paramBoolean6;
    this.genericContravariantArgumentMode = paramTypeMappingMode2;
    this.genericInvariantArgumentMode = paramTypeMappingMode3;
  }
  
  public final boolean getKotlinCollectionsToJavaCollections()
  {
    return this.kotlinCollectionsToJavaCollections;
  }
  
  public final boolean getNeedInlineClassWrapping()
  {
    return this.needInlineClassWrapping;
  }
  
  public final boolean getNeedPrimitiveBoxing()
  {
    return this.needPrimitiveBoxing;
  }
  
  public final boolean isForAnnotationParameter()
  {
    return this.isForAnnotationParameter;
  }
  
  public final TypeMappingMode toGenericArgumentMode(Variance paramVariance)
  {
    Intrinsics.checkParameterIsNotNull(paramVariance, "effectiveVariance");
    int i = TypeMappingMode.WhenMappings.$EnumSwitchMapping$0[paramVariance.ordinal()];
    if (i != 1)
    {
      if (i != 2)
      {
        paramVariance = this.genericArgumentMode;
        if (paramVariance != null) {
          return paramVariance;
        }
      }
      else
      {
        paramVariance = this.genericInvariantArgumentMode;
        if (paramVariance != null) {
          return paramVariance;
        }
      }
    }
    else
    {
      paramVariance = this.genericContravariantArgumentMode;
      if (paramVariance != null) {
        return paramVariance;
      }
    }
    paramVariance = this;
    return paramVariance;
  }
  
  public final TypeMappingMode wrapInlineClassesMode()
  {
    return new TypeMappingMode(this.needPrimitiveBoxing, true, this.isForAnnotationParameter, this.skipDeclarationSiteWildcards, this.skipDeclarationSiteWildcardsIfPossible, this.genericArgumentMode, this.kotlinCollectionsToJavaCollections, this.genericContravariantArgumentMode, this.genericInvariantArgumentMode);
  }
  
  public static final class Companion
  {
    private Companion() {}
  }
}

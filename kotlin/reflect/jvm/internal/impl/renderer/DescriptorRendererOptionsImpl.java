package kotlin.reflect.jvm.internal.impl.renderer;

import java.lang.reflect.Field;
import java.util.Set;
import kotlin._Assertions;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ObservableProperty;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.text.StringsKt;

public final class DescriptorRendererOptionsImpl
  implements DescriptorRendererOptions
{
  private final ReadWriteProperty actualPropertiesInPrimaryConstructor$delegate;
  private final ReadWriteProperty alwaysRenderModifiers$delegate;
  private final ReadWriteProperty annotationArgumentsRenderingPolicy$delegate;
  private final ReadWriteProperty annotationFilter$delegate;
  private final ReadWriteProperty boldOnlyForNamesInHtml$delegate;
  private final ReadWriteProperty classWithPrimaryConstructor$delegate;
  private final ReadWriteProperty classifierNamePolicy$delegate = property(ClassifierNamePolicy.SOURCE_CODE_QUALIFIED.INSTANCE);
  private final ReadWriteProperty debugMode$delegate;
  private final ReadWriteProperty defaultParameterValueRenderer$delegate;
  private final ReadWriteProperty eachAnnotationOnNewLine$delegate;
  private final ReadWriteProperty enhancedTypes$delegate;
  private final ReadWriteProperty excludedAnnotationClasses$delegate;
  private final ReadWriteProperty excludedTypeAnnotationClasses$delegate;
  private final ReadWriteProperty includeAdditionalModifiers$delegate;
  private final ReadWriteProperty includePropertyConstant$delegate;
  private boolean isLocked;
  private final ReadWriteProperty modifiers$delegate;
  private final ReadWriteProperty normalizedVisibilities$delegate;
  private final ReadWriteProperty overrideRenderingPolicy$delegate;
  private final ReadWriteProperty parameterNameRenderingPolicy$delegate;
  private final ReadWriteProperty parameterNamesInFunctionalTypes$delegate;
  private final ReadWriteProperty presentableUnresolvedTypes$delegate;
  private final ReadWriteProperty propertyAccessorRenderingPolicy$delegate;
  private final ReadWriteProperty receiverAfterName$delegate;
  private final ReadWriteProperty renderCompanionObjectName$delegate;
  private final ReadWriteProperty renderConstructorDelegation$delegate;
  private final ReadWriteProperty renderConstructorKeyword$delegate;
  private final ReadWriteProperty renderDefaultAnnotationArguments$delegate;
  private final ReadWriteProperty renderDefaultModality$delegate;
  private final ReadWriteProperty renderDefaultVisibility$delegate;
  private final ReadWriteProperty renderFunctionContracts$delegate;
  private final ReadWriteProperty renderPrimaryConstructorParametersAsProperties$delegate;
  private final ReadWriteProperty renderTypeExpansions$delegate;
  private final ReadWriteProperty renderUnabbreviatedType$delegate;
  private final ReadWriteProperty secondaryConstructorsAsPrimary$delegate;
  private final ReadWriteProperty startFromDeclarationKeyword$delegate;
  private final ReadWriteProperty startFromName$delegate;
  private final ReadWriteProperty textFormat$delegate;
  private final ReadWriteProperty typeNormalizer$delegate;
  private final ReadWriteProperty uninferredTypeParameterAsName$delegate;
  private final ReadWriteProperty unitReturnType$delegate;
  private final ReadWriteProperty valueParametersHandler$delegate;
  private final ReadWriteProperty verbose$delegate;
  private final ReadWriteProperty withDefinedIn$delegate;
  private final ReadWriteProperty withSourceFileForTopLevel$delegate;
  private final ReadWriteProperty withoutReturnType$delegate;
  private final ReadWriteProperty withoutSuperTypes$delegate;
  private final ReadWriteProperty withoutTypeParameters$delegate;
  
  public DescriptorRendererOptionsImpl()
  {
    Boolean localBoolean1 = Boolean.valueOf(true);
    this.withDefinedIn$delegate = property(localBoolean1);
    this.withSourceFileForTopLevel$delegate = property(localBoolean1);
    this.modifiers$delegate = property(DescriptorRendererModifier.DEFAULTS);
    Boolean localBoolean2 = Boolean.valueOf(false);
    this.startFromName$delegate = property(localBoolean2);
    this.startFromDeclarationKeyword$delegate = property(localBoolean2);
    this.debugMode$delegate = property(localBoolean2);
    this.classWithPrimaryConstructor$delegate = property(localBoolean2);
    this.verbose$delegate = property(localBoolean2);
    this.unitReturnType$delegate = property(localBoolean1);
    this.withoutReturnType$delegate = property(localBoolean2);
    this.enhancedTypes$delegate = property(localBoolean2);
    this.normalizedVisibilities$delegate = property(localBoolean2);
    this.renderDefaultVisibility$delegate = property(localBoolean1);
    this.renderDefaultModality$delegate = property(localBoolean1);
    this.renderConstructorDelegation$delegate = property(localBoolean2);
    this.renderPrimaryConstructorParametersAsProperties$delegate = property(localBoolean2);
    this.actualPropertiesInPrimaryConstructor$delegate = property(localBoolean2);
    this.uninferredTypeParameterAsName$delegate = property(localBoolean2);
    this.includePropertyConstant$delegate = property(localBoolean2);
    this.withoutTypeParameters$delegate = property(localBoolean2);
    this.withoutSuperTypes$delegate = property(localBoolean2);
    this.typeNormalizer$delegate = property(typeNormalizer.2.INSTANCE);
    this.defaultParameterValueRenderer$delegate = property(defaultParameterValueRenderer.2.INSTANCE);
    this.secondaryConstructorsAsPrimary$delegate = property(localBoolean1);
    this.overrideRenderingPolicy$delegate = property(OverrideRenderingPolicy.RENDER_OPEN);
    this.valueParametersHandler$delegate = property(DescriptorRenderer.ValueParametersHandler.DEFAULT.INSTANCE);
    this.textFormat$delegate = property(RenderingFormat.PLAIN);
    this.parameterNameRenderingPolicy$delegate = property(ParameterNameRenderingPolicy.ALL);
    this.receiverAfterName$delegate = property(localBoolean2);
    this.renderCompanionObjectName$delegate = property(localBoolean2);
    this.propertyAccessorRenderingPolicy$delegate = property(PropertyAccessorRenderingPolicy.DEBUG);
    this.renderDefaultAnnotationArguments$delegate = property(localBoolean2);
    this.eachAnnotationOnNewLine$delegate = property(localBoolean2);
    this.excludedAnnotationClasses$delegate = property(SetsKt.emptySet());
    this.excludedTypeAnnotationClasses$delegate = property(ExcludedTypeAnnotations.INSTANCE.getInternalAnnotationsForResolve());
    this.annotationFilter$delegate = property(null);
    this.annotationArgumentsRenderingPolicy$delegate = property(AnnotationArgumentsRenderingPolicy.NO_ARGUMENTS);
    this.alwaysRenderModifiers$delegate = property(localBoolean2);
    this.renderConstructorKeyword$delegate = property(localBoolean1);
    this.renderUnabbreviatedType$delegate = property(localBoolean1);
    this.renderTypeExpansions$delegate = property(localBoolean2);
    this.includeAdditionalModifiers$delegate = property(localBoolean1);
    this.parameterNamesInFunctionalTypes$delegate = property(localBoolean1);
    this.renderFunctionContracts$delegate = property(localBoolean2);
    this.presentableUnresolvedTypes$delegate = property(localBoolean2);
    this.boldOnlyForNamesInHtml$delegate = property(localBoolean2);
  }
  
  private final <T> ReadWriteProperty<DescriptorRendererOptionsImpl, T> property(T paramT)
  {
    Delegates localDelegates = Delegates.INSTANCE;
    (ReadWriteProperty)new ObservableProperty(paramT)
    {
      protected boolean beforeChange(KProperty<?> paramAnonymousKProperty, T paramAnonymousT1, T paramAnonymousT2)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousKProperty, "property");
        if (!jdField_this.isLocked()) {
          return true;
        }
        throw ((Throwable)new IllegalStateException("Cannot modify readonly DescriptorRendererOptions"));
      }
    };
  }
  
  public final DescriptorRendererOptionsImpl copy()
  {
    DescriptorRendererOptionsImpl localDescriptorRendererOptionsImpl = new DescriptorRendererOptionsImpl();
    for (Field localField : getClass().getDeclaredFields())
    {
      Intrinsics.checkExpressionValueIsNotNull(localField, "field");
      if ((localField.getModifiers() & 0x8) == 0)
      {
        localField.setAccessible(true);
        Object localObject1 = localField.get(this);
        Object localObject2 = localObject1;
        if (!(localObject1 instanceof ObservableProperty)) {
          localObject2 = null;
        }
        localObject2 = (ObservableProperty)localObject2;
        if (localObject2 != null)
        {
          localObject1 = localField.getName();
          Intrinsics.checkExpressionValueIsNotNull(localObject1, "field.name");
          boolean bool = StringsKt.startsWith$default((String)localObject1, "is", false, 2, null);
          if ((_Assertions.ENABLED) && (!(true ^ bool))) {
            throw ((Throwable)new AssertionError("Fields named is* are not supported here yet"));
          }
          KDeclarationContainer localKDeclarationContainer = (KDeclarationContainer)Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class);
          String str = localField.getName();
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("get");
          localObject1 = localField.getName();
          Intrinsics.checkExpressionValueIsNotNull(localObject1, "field.name");
          localStringBuilder.append(StringsKt.capitalize((String)localObject1));
          localField.set(localDescriptorRendererOptionsImpl, localDescriptorRendererOptionsImpl.property(((ObservableProperty)localObject2).getValue(this, (KProperty)new PropertyReference1Impl(localKDeclarationContainer, str, localStringBuilder.toString()))));
        }
      }
    }
    return localDescriptorRendererOptionsImpl;
  }
  
  public boolean getActualPropertiesInPrimaryConstructor()
  {
    return ((Boolean)this.actualPropertiesInPrimaryConstructor$delegate.getValue(this, $$delegatedProperties[17])).booleanValue();
  }
  
  public boolean getAlwaysRenderModifiers()
  {
    return ((Boolean)this.alwaysRenderModifiers$delegate.getValue(this, $$delegatedProperties[38])).booleanValue();
  }
  
  public AnnotationArgumentsRenderingPolicy getAnnotationArgumentsRenderingPolicy()
  {
    return (AnnotationArgumentsRenderingPolicy)this.annotationArgumentsRenderingPolicy$delegate.getValue(this, $$delegatedProperties[37]);
  }
  
  public Function1<AnnotationDescriptor, Boolean> getAnnotationFilter()
  {
    return (Function1)this.annotationFilter$delegate.getValue(this, $$delegatedProperties[36]);
  }
  
  public boolean getBoldOnlyForNamesInHtml()
  {
    return ((Boolean)this.boldOnlyForNamesInHtml$delegate.getValue(this, $$delegatedProperties[46])).booleanValue();
  }
  
  public boolean getClassWithPrimaryConstructor()
  {
    return ((Boolean)this.classWithPrimaryConstructor$delegate.getValue(this, $$delegatedProperties[7])).booleanValue();
  }
  
  public ClassifierNamePolicy getClassifierNamePolicy()
  {
    return (ClassifierNamePolicy)this.classifierNamePolicy$delegate.getValue(this, $$delegatedProperties[0]);
  }
  
  public boolean getDebugMode()
  {
    return ((Boolean)this.debugMode$delegate.getValue(this, $$delegatedProperties[6])).booleanValue();
  }
  
  public Function1<ValueParameterDescriptor, String> getDefaultParameterValueRenderer()
  {
    return (Function1)this.defaultParameterValueRenderer$delegate.getValue(this, $$delegatedProperties[23]);
  }
  
  public boolean getEachAnnotationOnNewLine()
  {
    return ((Boolean)this.eachAnnotationOnNewLine$delegate.getValue(this, $$delegatedProperties[33])).booleanValue();
  }
  
  public boolean getEnhancedTypes()
  {
    return ((Boolean)this.enhancedTypes$delegate.getValue(this, $$delegatedProperties[11])).booleanValue();
  }
  
  public Set<FqName> getExcludedAnnotationClasses()
  {
    return (Set)this.excludedAnnotationClasses$delegate.getValue(this, $$delegatedProperties[34]);
  }
  
  public Set<FqName> getExcludedTypeAnnotationClasses()
  {
    return (Set)this.excludedTypeAnnotationClasses$delegate.getValue(this, $$delegatedProperties[35]);
  }
  
  public boolean getIncludeAdditionalModifiers()
  {
    return ((Boolean)this.includeAdditionalModifiers$delegate.getValue(this, $$delegatedProperties[42])).booleanValue();
  }
  
  public boolean getIncludeAnnotationArguments()
  {
    return DescriptorRendererOptions.DefaultImpls.getIncludeAnnotationArguments(this);
  }
  
  public boolean getIncludeEmptyAnnotationArguments()
  {
    return DescriptorRendererOptions.DefaultImpls.getIncludeEmptyAnnotationArguments(this);
  }
  
  public boolean getIncludePropertyConstant()
  {
    return ((Boolean)this.includePropertyConstant$delegate.getValue(this, $$delegatedProperties[19])).booleanValue();
  }
  
  public Set<DescriptorRendererModifier> getModifiers()
  {
    return (Set)this.modifiers$delegate.getValue(this, $$delegatedProperties[3]);
  }
  
  public boolean getNormalizedVisibilities()
  {
    return ((Boolean)this.normalizedVisibilities$delegate.getValue(this, $$delegatedProperties[12])).booleanValue();
  }
  
  public OverrideRenderingPolicy getOverrideRenderingPolicy()
  {
    return (OverrideRenderingPolicy)this.overrideRenderingPolicy$delegate.getValue(this, $$delegatedProperties[25]);
  }
  
  public ParameterNameRenderingPolicy getParameterNameRenderingPolicy()
  {
    return (ParameterNameRenderingPolicy)this.parameterNameRenderingPolicy$delegate.getValue(this, $$delegatedProperties[28]);
  }
  
  public boolean getParameterNamesInFunctionalTypes()
  {
    return ((Boolean)this.parameterNamesInFunctionalTypes$delegate.getValue(this, $$delegatedProperties[43])).booleanValue();
  }
  
  public boolean getPresentableUnresolvedTypes()
  {
    return ((Boolean)this.presentableUnresolvedTypes$delegate.getValue(this, $$delegatedProperties[45])).booleanValue();
  }
  
  public PropertyAccessorRenderingPolicy getPropertyAccessorRenderingPolicy()
  {
    return (PropertyAccessorRenderingPolicy)this.propertyAccessorRenderingPolicy$delegate.getValue(this, $$delegatedProperties[31]);
  }
  
  public boolean getReceiverAfterName()
  {
    return ((Boolean)this.receiverAfterName$delegate.getValue(this, $$delegatedProperties[29])).booleanValue();
  }
  
  public boolean getRenderCompanionObjectName()
  {
    return ((Boolean)this.renderCompanionObjectName$delegate.getValue(this, $$delegatedProperties[30])).booleanValue();
  }
  
  public boolean getRenderConstructorDelegation()
  {
    return ((Boolean)this.renderConstructorDelegation$delegate.getValue(this, $$delegatedProperties[15])).booleanValue();
  }
  
  public boolean getRenderConstructorKeyword()
  {
    return ((Boolean)this.renderConstructorKeyword$delegate.getValue(this, $$delegatedProperties[39])).booleanValue();
  }
  
  public boolean getRenderDefaultAnnotationArguments()
  {
    return ((Boolean)this.renderDefaultAnnotationArguments$delegate.getValue(this, $$delegatedProperties[32])).booleanValue();
  }
  
  public boolean getRenderDefaultModality()
  {
    return ((Boolean)this.renderDefaultModality$delegate.getValue(this, $$delegatedProperties[14])).booleanValue();
  }
  
  public boolean getRenderDefaultVisibility()
  {
    return ((Boolean)this.renderDefaultVisibility$delegate.getValue(this, $$delegatedProperties[13])).booleanValue();
  }
  
  public boolean getRenderPrimaryConstructorParametersAsProperties()
  {
    return ((Boolean)this.renderPrimaryConstructorParametersAsProperties$delegate.getValue(this, $$delegatedProperties[16])).booleanValue();
  }
  
  public boolean getRenderTypeExpansions()
  {
    return ((Boolean)this.renderTypeExpansions$delegate.getValue(this, $$delegatedProperties[41])).booleanValue();
  }
  
  public boolean getRenderUnabbreviatedType()
  {
    return ((Boolean)this.renderUnabbreviatedType$delegate.getValue(this, $$delegatedProperties[40])).booleanValue();
  }
  
  public boolean getSecondaryConstructorsAsPrimary()
  {
    return ((Boolean)this.secondaryConstructorsAsPrimary$delegate.getValue(this, $$delegatedProperties[24])).booleanValue();
  }
  
  public boolean getStartFromDeclarationKeyword()
  {
    return ((Boolean)this.startFromDeclarationKeyword$delegate.getValue(this, $$delegatedProperties[5])).booleanValue();
  }
  
  public boolean getStartFromName()
  {
    return ((Boolean)this.startFromName$delegate.getValue(this, $$delegatedProperties[4])).booleanValue();
  }
  
  public RenderingFormat getTextFormat()
  {
    return (RenderingFormat)this.textFormat$delegate.getValue(this, $$delegatedProperties[27]);
  }
  
  public Function1<KotlinType, KotlinType> getTypeNormalizer()
  {
    return (Function1)this.typeNormalizer$delegate.getValue(this, $$delegatedProperties[22]);
  }
  
  public boolean getUninferredTypeParameterAsName()
  {
    return ((Boolean)this.uninferredTypeParameterAsName$delegate.getValue(this, $$delegatedProperties[18])).booleanValue();
  }
  
  public boolean getUnitReturnType()
  {
    return ((Boolean)this.unitReturnType$delegate.getValue(this, $$delegatedProperties[9])).booleanValue();
  }
  
  public DescriptorRenderer.ValueParametersHandler getValueParametersHandler()
  {
    return (DescriptorRenderer.ValueParametersHandler)this.valueParametersHandler$delegate.getValue(this, $$delegatedProperties[26]);
  }
  
  public boolean getVerbose()
  {
    return ((Boolean)this.verbose$delegate.getValue(this, $$delegatedProperties[8])).booleanValue();
  }
  
  public boolean getWithDefinedIn()
  {
    return ((Boolean)this.withDefinedIn$delegate.getValue(this, $$delegatedProperties[1])).booleanValue();
  }
  
  public boolean getWithSourceFileForTopLevel()
  {
    return ((Boolean)this.withSourceFileForTopLevel$delegate.getValue(this, $$delegatedProperties[2])).booleanValue();
  }
  
  public boolean getWithoutReturnType()
  {
    return ((Boolean)this.withoutReturnType$delegate.getValue(this, $$delegatedProperties[10])).booleanValue();
  }
  
  public boolean getWithoutSuperTypes()
  {
    return ((Boolean)this.withoutSuperTypes$delegate.getValue(this, $$delegatedProperties[21])).booleanValue();
  }
  
  public boolean getWithoutTypeParameters()
  {
    return ((Boolean)this.withoutTypeParameters$delegate.getValue(this, $$delegatedProperties[20])).booleanValue();
  }
  
  public final boolean isLocked()
  {
    return this.isLocked;
  }
  
  public final void lock()
  {
    boolean bool = this.isLocked;
    if ((_Assertions.ENABLED) && (!(bool ^ true))) {
      throw ((Throwable)new AssertionError("Assertion failed"));
    }
    this.isLocked = true;
  }
  
  public void setAnnotationArgumentsRenderingPolicy(AnnotationArgumentsRenderingPolicy paramAnnotationArgumentsRenderingPolicy)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotationArgumentsRenderingPolicy, "<set-?>");
    this.annotationArgumentsRenderingPolicy$delegate.setValue(this, $$delegatedProperties[37], paramAnnotationArgumentsRenderingPolicy);
  }
  
  public void setClassifierNamePolicy(ClassifierNamePolicy paramClassifierNamePolicy)
  {
    Intrinsics.checkParameterIsNotNull(paramClassifierNamePolicy, "<set-?>");
    this.classifierNamePolicy$delegate.setValue(this, $$delegatedProperties[0], paramClassifierNamePolicy);
  }
  
  public void setDebugMode(boolean paramBoolean)
  {
    this.debugMode$delegate.setValue(this, $$delegatedProperties[6], Boolean.valueOf(paramBoolean));
  }
  
  public void setExcludedTypeAnnotationClasses(Set<FqName> paramSet)
  {
    Intrinsics.checkParameterIsNotNull(paramSet, "<set-?>");
    this.excludedTypeAnnotationClasses$delegate.setValue(this, $$delegatedProperties[35], paramSet);
  }
  
  public void setModifiers(Set<? extends DescriptorRendererModifier> paramSet)
  {
    Intrinsics.checkParameterIsNotNull(paramSet, "<set-?>");
    this.modifiers$delegate.setValue(this, $$delegatedProperties[3], paramSet);
  }
  
  public void setParameterNameRenderingPolicy(ParameterNameRenderingPolicy paramParameterNameRenderingPolicy)
  {
    Intrinsics.checkParameterIsNotNull(paramParameterNameRenderingPolicy, "<set-?>");
    this.parameterNameRenderingPolicy$delegate.setValue(this, $$delegatedProperties[28], paramParameterNameRenderingPolicy);
  }
  
  public void setReceiverAfterName(boolean paramBoolean)
  {
    this.receiverAfterName$delegate.setValue(this, $$delegatedProperties[29], Boolean.valueOf(paramBoolean));
  }
  
  public void setRenderCompanionObjectName(boolean paramBoolean)
  {
    this.renderCompanionObjectName$delegate.setValue(this, $$delegatedProperties[30], Boolean.valueOf(paramBoolean));
  }
  
  public void setStartFromName(boolean paramBoolean)
  {
    this.startFromName$delegate.setValue(this, $$delegatedProperties[4], Boolean.valueOf(paramBoolean));
  }
  
  public void setTextFormat(RenderingFormat paramRenderingFormat)
  {
    Intrinsics.checkParameterIsNotNull(paramRenderingFormat, "<set-?>");
    this.textFormat$delegate.setValue(this, $$delegatedProperties[27], paramRenderingFormat);
  }
  
  public void setVerbose(boolean paramBoolean)
  {
    this.verbose$delegate.setValue(this, $$delegatedProperties[8], Boolean.valueOf(paramBoolean));
  }
  
  public void setWithDefinedIn(boolean paramBoolean)
  {
    this.withDefinedIn$delegate.setValue(this, $$delegatedProperties[1], Boolean.valueOf(paramBoolean));
  }
  
  public void setWithoutSuperTypes(boolean paramBoolean)
  {
    this.withoutSuperTypes$delegate.setValue(this, $$delegatedProperties[21], Boolean.valueOf(paramBoolean));
  }
  
  public void setWithoutTypeParameters(boolean paramBoolean)
  {
    this.withoutTypeParameters$delegate.setValue(this, $$delegatedProperties[20], Boolean.valueOf(paramBoolean));
  }
}

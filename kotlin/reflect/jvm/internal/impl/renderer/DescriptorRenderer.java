package kotlin.reflect.jvm.internal.impl.renderer;

import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationUseSiteTarget;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;

public abstract class DescriptorRenderer
{
  public static final DescriptorRenderer COMPACT;
  public static final DescriptorRenderer COMPACT_WITHOUT_SUPERTYPES;
  public static final DescriptorRenderer COMPACT_WITH_MODIFIERS;
  public static final DescriptorRenderer COMPACT_WITH_SHORT_TYPES;
  public static final Companion Companion;
  public static final DescriptorRenderer DEBUG_TEXT = Companion.withOptions((Function1)DescriptorRenderer.Companion.DEBUG_TEXT.1.INSTANCE);
  public static final DescriptorRenderer FQ_NAMES_IN_TYPES;
  public static final DescriptorRenderer HTML = Companion.withOptions((Function1)DescriptorRenderer.Companion.HTML.1.INSTANCE);
  public static final DescriptorRenderer ONLY_NAMES_WITH_SHORT_TYPES;
  public static final DescriptorRenderer SHORT_NAMES_IN_TYPES;
  
  static
  {
    Companion localCompanion = new Companion(null);
    Companion = localCompanion;
    COMPACT_WITH_MODIFIERS = localCompanion.withOptions((Function1)DescriptorRenderer.Companion.COMPACT_WITH_MODIFIERS.1.INSTANCE);
    COMPACT = Companion.withOptions((Function1)DescriptorRenderer.Companion.COMPACT.1.INSTANCE);
    COMPACT_WITHOUT_SUPERTYPES = Companion.withOptions((Function1)DescriptorRenderer.Companion.COMPACT_WITHOUT_SUPERTYPES.1.INSTANCE);
    COMPACT_WITH_SHORT_TYPES = Companion.withOptions((Function1)DescriptorRenderer.Companion.COMPACT_WITH_SHORT_TYPES.1.INSTANCE);
    ONLY_NAMES_WITH_SHORT_TYPES = Companion.withOptions((Function1)DescriptorRenderer.Companion.ONLY_NAMES_WITH_SHORT_TYPES.1.INSTANCE);
    FQ_NAMES_IN_TYPES = Companion.withOptions((Function1)DescriptorRenderer.Companion.FQ_NAMES_IN_TYPES.1.INSTANCE);
    SHORT_NAMES_IN_TYPES = Companion.withOptions((Function1)DescriptorRenderer.Companion.SHORT_NAMES_IN_TYPES.1.INSTANCE);
  }
  
  public DescriptorRenderer() {}
  
  public abstract String render(DeclarationDescriptor paramDeclarationDescriptor);
  
  public abstract String renderAnnotation(AnnotationDescriptor paramAnnotationDescriptor, AnnotationUseSiteTarget paramAnnotationUseSiteTarget);
  
  public abstract String renderFlexibleType(String paramString1, String paramString2, KotlinBuiltIns paramKotlinBuiltIns);
  
  public abstract String renderFqName(FqNameUnsafe paramFqNameUnsafe);
  
  public abstract String renderName(Name paramName, boolean paramBoolean);
  
  public abstract String renderType(KotlinType paramKotlinType);
  
  public abstract String renderTypeProjection(TypeProjection paramTypeProjection);
  
  public final DescriptorRenderer withOptions(Function1<? super DescriptorRendererOptions, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction1, "changeOptions");
    DescriptorRendererOptionsImpl localDescriptorRendererOptionsImpl = ((DescriptorRendererImpl)this).getOptions().copy();
    paramFunction1.invoke(localDescriptorRendererOptionsImpl);
    localDescriptorRendererOptionsImpl.lock();
    return (DescriptorRenderer)new DescriptorRendererImpl(localDescriptorRendererOptionsImpl);
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    public final String getClassifierKindPrefix(ClassifierDescriptorWithTypeParameters paramClassifierDescriptorWithTypeParameters)
    {
      Intrinsics.checkParameterIsNotNull(paramClassifierDescriptorWithTypeParameters, "classifier");
      if ((paramClassifierDescriptorWithTypeParameters instanceof TypeAliasDescriptor))
      {
        paramClassifierDescriptorWithTypeParameters = "typealias";
      }
      else
      {
        if (!(paramClassifierDescriptorWithTypeParameters instanceof ClassDescriptor)) {
          break label143;
        }
        paramClassifierDescriptorWithTypeParameters = (ClassDescriptor)paramClassifierDescriptorWithTypeParameters;
        if (paramClassifierDescriptorWithTypeParameters.isCompanionObject())
        {
          paramClassifierDescriptorWithTypeParameters = "companion object";
        }
        else
        {
          paramClassifierDescriptorWithTypeParameters = paramClassifierDescriptorWithTypeParameters.getKind();
          switch (DescriptorRenderer.Companion.WhenMappings.$EnumSwitchMapping$0[paramClassifierDescriptorWithTypeParameters.ordinal()])
          {
          default: 
            throw new NoWhenBranchMatchedException();
          case 6: 
            paramClassifierDescriptorWithTypeParameters = "enum entry";
            break;
          case 5: 
            paramClassifierDescriptorWithTypeParameters = "annotation class";
            break;
          case 4: 
            paramClassifierDescriptorWithTypeParameters = "object";
            break;
          case 3: 
            paramClassifierDescriptorWithTypeParameters = "enum class";
            break;
          case 2: 
            paramClassifierDescriptorWithTypeParameters = "interface";
            break;
          case 1: 
            paramClassifierDescriptorWithTypeParameters = "class";
          }
        }
      }
      return paramClassifierDescriptorWithTypeParameters;
      label143:
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unexpected classifier: ");
      localStringBuilder.append(paramClassifierDescriptorWithTypeParameters);
      throw ((Throwable)new AssertionError(localStringBuilder.toString()));
    }
    
    public final DescriptorRenderer withOptions(Function1<? super DescriptorRendererOptions, Unit> paramFunction1)
    {
      Intrinsics.checkParameterIsNotNull(paramFunction1, "changeOptions");
      DescriptorRendererOptionsImpl localDescriptorRendererOptionsImpl = new DescriptorRendererOptionsImpl();
      paramFunction1.invoke(localDescriptorRendererOptionsImpl);
      localDescriptorRendererOptionsImpl.lock();
      return (DescriptorRenderer)new DescriptorRendererImpl(localDescriptorRendererOptionsImpl);
    }
  }
  
  public static abstract interface ValueParametersHandler
  {
    public abstract void appendAfterValueParameter(ValueParameterDescriptor paramValueParameterDescriptor, int paramInt1, int paramInt2, StringBuilder paramStringBuilder);
    
    public abstract void appendAfterValueParameters(int paramInt, StringBuilder paramStringBuilder);
    
    public abstract void appendBeforeValueParameter(ValueParameterDescriptor paramValueParameterDescriptor, int paramInt1, int paramInt2, StringBuilder paramStringBuilder);
    
    public abstract void appendBeforeValueParameters(int paramInt, StringBuilder paramStringBuilder);
    
    public static final class DEFAULT
      implements DescriptorRenderer.ValueParametersHandler
    {
      public static final DEFAULT INSTANCE = new DEFAULT();
      
      private DEFAULT() {}
      
      public void appendAfterValueParameter(ValueParameterDescriptor paramValueParameterDescriptor, int paramInt1, int paramInt2, StringBuilder paramStringBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramValueParameterDescriptor, "parameter");
        Intrinsics.checkParameterIsNotNull(paramStringBuilder, "builder");
        if (paramInt1 != paramInt2 - 1) {
          paramStringBuilder.append(", ");
        }
      }
      
      public void appendAfterValueParameters(int paramInt, StringBuilder paramStringBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramStringBuilder, "builder");
        paramStringBuilder.append(")");
      }
      
      public void appendBeforeValueParameter(ValueParameterDescriptor paramValueParameterDescriptor, int paramInt1, int paramInt2, StringBuilder paramStringBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramValueParameterDescriptor, "parameter");
        Intrinsics.checkParameterIsNotNull(paramStringBuilder, "builder");
      }
      
      public void appendBeforeValueParameters(int paramInt, StringBuilder paramStringBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramStringBuilder, "builder");
        paramStringBuilder.append("(");
      }
    }
  }
}

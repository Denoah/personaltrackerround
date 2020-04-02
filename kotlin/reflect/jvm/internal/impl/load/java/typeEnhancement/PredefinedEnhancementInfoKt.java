package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;

public final class PredefinedEnhancementInfoKt
{
  private static final JavaTypeQualifiers NOT_NULLABLE;
  private static final JavaTypeQualifiers NOT_PLATFORM;
  private static final JavaTypeQualifiers NULLABLE = new JavaTypeQualifiers(NullabilityQualifier.NULLABLE, null, false, false, 8, null);
  private static final Map<String, PredefinedFunctionEnhancementInfo> PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE;
  
  static
  {
    NOT_PLATFORM = new JavaTypeQualifiers(NullabilityQualifier.NOT_NULL, null, false, false, 8, null);
    NOT_NULLABLE = new JavaTypeQualifiers(NullabilityQualifier.NOT_NULL, null, true, false, 8, null);
    SignatureBuildingComponents localSignatureBuildingComponents = SignatureBuildingComponents.INSTANCE;
    final String str1 = localSignatureBuildingComponents.javaLang("Object");
    final String str2 = localSignatureBuildingComponents.javaFunction("Predicate");
    final String str3 = localSignatureBuildingComponents.javaFunction("Function");
    final String str4 = localSignatureBuildingComponents.javaFunction("Consumer");
    final String str5 = localSignatureBuildingComponents.javaFunction("BiFunction");
    final String str6 = localSignatureBuildingComponents.javaFunction("BiConsumer");
    final String str7 = localSignatureBuildingComponents.javaFunction("UnaryOperator");
    final String str8 = localSignatureBuildingComponents.javaUtil("stream/Stream");
    final String str9 = localSignatureBuildingComponents.javaUtil("Optional");
    SignatureEnhancementBuilder localSignatureEnhancementBuilder = new SignatureEnhancementBuilder();
    new SignatureEnhancementBuilder.ClassEnhancementBuilder(localSignatureEnhancementBuilder, localSignatureBuildingComponents.javaUtil("Iterator")).function("forEachRemaining", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.parameter(str4, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
      }
    });
    new SignatureEnhancementBuilder.ClassEnhancementBuilder(localSignatureEnhancementBuilder, localSignatureBuildingComponents.javaLang("Iterable")).function("spliterator", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.returns(this.$this_signatures$inlined.javaUtil("Spliterator"), new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
      }
    });
    SignatureEnhancementBuilder.ClassEnhancementBuilder localClassEnhancementBuilder = new SignatureEnhancementBuilder.ClassEnhancementBuilder(localSignatureEnhancementBuilder, localSignatureBuildingComponents.javaUtil("Collection"));
    localClassEnhancementBuilder.function("removeIf", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.parameter(str2, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
        paramAnonymousFunctionEnhancementBuilder.returns(JvmPrimitiveType.BOOLEAN);
      }
    });
    localClassEnhancementBuilder.function("stream", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.returns(str8, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
      }
    });
    localClassEnhancementBuilder.function("parallelStream", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.returns(str8, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
      }
    });
    new SignatureEnhancementBuilder.ClassEnhancementBuilder(localSignatureEnhancementBuilder, localSignatureBuildingComponents.javaUtil("List")).function("replaceAll", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.parameter(str7, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
      }
    });
    localClassEnhancementBuilder = new SignatureEnhancementBuilder.ClassEnhancementBuilder(localSignatureEnhancementBuilder, localSignatureBuildingComponents.javaUtil("Map"));
    localClassEnhancementBuilder.function("forEach", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.parameter(str6, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
      }
    });
    localClassEnhancementBuilder.function("putIfAbsent", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
        paramAnonymousFunctionEnhancementBuilder.returns(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNULLABLE$p() });
      }
    });
    localClassEnhancementBuilder.function("replace", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
        paramAnonymousFunctionEnhancementBuilder.returns(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNULLABLE$p() });
      }
    });
    localClassEnhancementBuilder.function("replace", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
        paramAnonymousFunctionEnhancementBuilder.returns(JvmPrimitiveType.BOOLEAN);
      }
    });
    localClassEnhancementBuilder.function("replaceAll", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.parameter(str5, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
      }
    });
    localClassEnhancementBuilder.function("compute", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
        paramAnonymousFunctionEnhancementBuilder.parameter(str5, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNULLABLE$p(), PredefinedEnhancementInfoKt.access$getNULLABLE$p() });
        paramAnonymousFunctionEnhancementBuilder.returns(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNULLABLE$p() });
      }
    });
    localClassEnhancementBuilder.function("computeIfAbsent", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
        paramAnonymousFunctionEnhancementBuilder.parameter(str3, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
        paramAnonymousFunctionEnhancementBuilder.returns(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
      }
    });
    localClassEnhancementBuilder.function("computeIfPresent", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
        paramAnonymousFunctionEnhancementBuilder.parameter(str5, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_NULLABLE$p(), PredefinedEnhancementInfoKt.access$getNULLABLE$p() });
        paramAnonymousFunctionEnhancementBuilder.returns(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNULLABLE$p() });
      }
    });
    localClassEnhancementBuilder.function("merge", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_NULLABLE$p() });
        paramAnonymousFunctionEnhancementBuilder.parameter(str5, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_NULLABLE$p(), PredefinedEnhancementInfoKt.access$getNOT_NULLABLE$p(), PredefinedEnhancementInfoKt.access$getNULLABLE$p() });
        paramAnonymousFunctionEnhancementBuilder.returns(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNULLABLE$p() });
      }
    });
    localClassEnhancementBuilder = new SignatureEnhancementBuilder.ClassEnhancementBuilder(localSignatureEnhancementBuilder, str9);
    localClassEnhancementBuilder.function("empty", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.returns(str9, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_NULLABLE$p() });
      }
    });
    localClassEnhancementBuilder.function("of", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_NULLABLE$p() });
        paramAnonymousFunctionEnhancementBuilder.returns(str9, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_NULLABLE$p() });
      }
    });
    localClassEnhancementBuilder.function("ofNullable", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNULLABLE$p() });
        paramAnonymousFunctionEnhancementBuilder.returns(str9, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_NULLABLE$p() });
      }
    });
    localClassEnhancementBuilder.function("get", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.returns(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_NULLABLE$p() });
      }
    });
    localClassEnhancementBuilder.function("ifPresent", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.parameter(str4, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p(), PredefinedEnhancementInfoKt.access$getNOT_NULLABLE$p() });
      }
    });
    new SignatureEnhancementBuilder.ClassEnhancementBuilder(localSignatureEnhancementBuilder, localSignatureBuildingComponents.javaLang("ref/Reference")).function("get", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.returns(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNULLABLE$p() });
      }
    });
    new SignatureEnhancementBuilder.ClassEnhancementBuilder(localSignatureEnhancementBuilder, str2).function("test", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
        paramAnonymousFunctionEnhancementBuilder.returns(JvmPrimitiveType.BOOLEAN);
      }
    });
    new SignatureEnhancementBuilder.ClassEnhancementBuilder(localSignatureEnhancementBuilder, localSignatureBuildingComponents.javaFunction("BiPredicate")).function("test", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
        paramAnonymousFunctionEnhancementBuilder.returns(JvmPrimitiveType.BOOLEAN);
      }
    });
    new SignatureEnhancementBuilder.ClassEnhancementBuilder(localSignatureEnhancementBuilder, str4).function("accept", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
      }
    });
    new SignatureEnhancementBuilder.ClassEnhancementBuilder(localSignatureEnhancementBuilder, str6).function("accept", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
      }
    });
    new SignatureEnhancementBuilder.ClassEnhancementBuilder(localSignatureEnhancementBuilder, str3).function("apply", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
        paramAnonymousFunctionEnhancementBuilder.returns(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
      }
    });
    new SignatureEnhancementBuilder.ClassEnhancementBuilder(localSignatureEnhancementBuilder, str5).function("apply", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
        paramAnonymousFunctionEnhancementBuilder.parameter(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
        paramAnonymousFunctionEnhancementBuilder.returns(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
      }
    });
    new SignatureEnhancementBuilder.ClassEnhancementBuilder(localSignatureEnhancementBuilder, localSignatureBuildingComponents.javaFunction("Supplier")).function("get", (Function1)new Lambda(localSignatureBuildingComponents)
    {
      public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder paramAnonymousFunctionEnhancementBuilder)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunctionEnhancementBuilder, "$receiver");
        paramAnonymousFunctionEnhancementBuilder.returns(str1, new JavaTypeQualifiers[] { PredefinedEnhancementInfoKt.access$getNOT_PLATFORM$p() });
      }
    });
    PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE = localSignatureEnhancementBuilder.build();
  }
  
  public static final Map<String, PredefinedFunctionEnhancementInfo> getPREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE()
  {
    return PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE;
  }
}

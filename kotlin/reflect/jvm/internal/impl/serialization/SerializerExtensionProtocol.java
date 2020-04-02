package kotlin.reflect.jvm.internal.impl.serialization;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Annotation.Argument.Value;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Class;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Constructor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.EnumEntry;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Function;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Package;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Property;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Type;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.TypeParameter;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.ValueParameter;
import kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite.GeneratedExtension;

public class SerializerExtensionProtocol
{
  private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, List<ProtoBuf.Annotation>> classAnnotation;
  private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, ProtoBuf.Annotation.Argument.Value> compileTimeValue;
  private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Constructor, List<ProtoBuf.Annotation>> constructorAnnotation;
  private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.EnumEntry, List<ProtoBuf.Annotation>> enumEntryAnnotation;
  private final ExtensionRegistryLite extensionRegistry;
  private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> functionAnnotation;
  private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, Integer> packageFqName;
  private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.ValueParameter, List<ProtoBuf.Annotation>> parameterAnnotation;
  private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyAnnotation;
  private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertyGetterAnnotation;
  private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> propertySetterAnnotation;
  private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Type, List<ProtoBuf.Annotation>> typeAnnotation;
  private final GeneratedMessageLite.GeneratedExtension<ProtoBuf.TypeParameter, List<ProtoBuf.Annotation>> typeParameterAnnotation;
  
  public SerializerExtensionProtocol(ExtensionRegistryLite paramExtensionRegistryLite, GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, Integer> paramGeneratedExtension, GeneratedMessageLite.GeneratedExtension<ProtoBuf.Constructor, List<ProtoBuf.Annotation>> paramGeneratedExtension1, GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, List<ProtoBuf.Annotation>> paramGeneratedExtension2, GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> paramGeneratedExtension3, GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> paramGeneratedExtension4, GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> paramGeneratedExtension5, GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> paramGeneratedExtension6, GeneratedMessageLite.GeneratedExtension<ProtoBuf.EnumEntry, List<ProtoBuf.Annotation>> paramGeneratedExtension7, GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, ProtoBuf.Annotation.Argument.Value> paramGeneratedExtension8, GeneratedMessageLite.GeneratedExtension<ProtoBuf.ValueParameter, List<ProtoBuf.Annotation>> paramGeneratedExtension9, GeneratedMessageLite.GeneratedExtension<ProtoBuf.Type, List<ProtoBuf.Annotation>> paramGeneratedExtension10, GeneratedMessageLite.GeneratedExtension<ProtoBuf.TypeParameter, List<ProtoBuf.Annotation>> paramGeneratedExtension11)
  {
    this.extensionRegistry = paramExtensionRegistryLite;
    this.packageFqName = paramGeneratedExtension;
    this.constructorAnnotation = paramGeneratedExtension1;
    this.classAnnotation = paramGeneratedExtension2;
    this.functionAnnotation = paramGeneratedExtension3;
    this.propertyAnnotation = paramGeneratedExtension4;
    this.propertyGetterAnnotation = paramGeneratedExtension5;
    this.propertySetterAnnotation = paramGeneratedExtension6;
    this.enumEntryAnnotation = paramGeneratedExtension7;
    this.compileTimeValue = paramGeneratedExtension8;
    this.parameterAnnotation = paramGeneratedExtension9;
    this.typeAnnotation = paramGeneratedExtension10;
    this.typeParameterAnnotation = paramGeneratedExtension11;
  }
  
  public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, List<ProtoBuf.Annotation>> getClassAnnotation()
  {
    return this.classAnnotation;
  }
  
  public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, ProtoBuf.Annotation.Argument.Value> getCompileTimeValue()
  {
    return this.compileTimeValue;
  }
  
  public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Constructor, List<ProtoBuf.Annotation>> getConstructorAnnotation()
  {
    return this.constructorAnnotation;
  }
  
  public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.EnumEntry, List<ProtoBuf.Annotation>> getEnumEntryAnnotation()
  {
    return this.enumEntryAnnotation;
  }
  
  public final ExtensionRegistryLite getExtensionRegistry()
  {
    return this.extensionRegistry;
  }
  
  public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, List<ProtoBuf.Annotation>> getFunctionAnnotation()
  {
    return this.functionAnnotation;
  }
  
  public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.ValueParameter, List<ProtoBuf.Annotation>> getParameterAnnotation()
  {
    return this.parameterAnnotation;
  }
  
  public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> getPropertyAnnotation()
  {
    return this.propertyAnnotation;
  }
  
  public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> getPropertyGetterAnnotation()
  {
    return this.propertyGetterAnnotation;
  }
  
  public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, List<ProtoBuf.Annotation>> getPropertySetterAnnotation()
  {
    return this.propertySetterAnnotation;
  }
  
  public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.Type, List<ProtoBuf.Annotation>> getTypeAnnotation()
  {
    return this.typeAnnotation;
  }
  
  public final GeneratedMessageLite.GeneratedExtension<ProtoBuf.TypeParameter, List<ProtoBuf.Annotation>> getTypeParameterAnnotation()
  {
    return this.typeParameterAnnotation;
  }
}

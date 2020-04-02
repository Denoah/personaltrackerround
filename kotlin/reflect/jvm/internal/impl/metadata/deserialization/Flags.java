package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Class.Kind;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.MemberKind;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Modality;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.Visibility;
import kotlin.reflect.jvm.internal.impl.protobuf.Internal.EnumLite;

public class Flags
{
  public static final FlagField<ProtoBuf.Class.Kind> CLASS_KIND;
  public static final BooleanFlagField DECLARES_DEFAULT_VALUE;
  public static final BooleanFlagField HAS_ANNOTATIONS;
  public static final BooleanFlagField HAS_CONSTANT;
  public static final BooleanFlagField HAS_GETTER;
  public static final BooleanFlagField HAS_SETTER;
  public static final BooleanFlagField IS_CONST;
  public static final BooleanFlagField IS_CROSSINLINE;
  public static final BooleanFlagField IS_DATA;
  public static final BooleanFlagField IS_DELEGATED;
  public static final BooleanFlagField IS_EXPECT_CLASS;
  public static final BooleanFlagField IS_EXPECT_FUNCTION;
  public static final BooleanFlagField IS_EXPECT_PROPERTY;
  public static final BooleanFlagField IS_EXTERNAL_ACCESSOR;
  public static final BooleanFlagField IS_EXTERNAL_CLASS;
  public static final BooleanFlagField IS_EXTERNAL_FUNCTION;
  public static final BooleanFlagField IS_EXTERNAL_PROPERTY;
  public static final BooleanFlagField IS_INFIX;
  public static final BooleanFlagField IS_INLINE;
  public static final BooleanFlagField IS_INLINE_ACCESSOR;
  public static final BooleanFlagField IS_INLINE_CLASS;
  public static final BooleanFlagField IS_INNER;
  public static final BooleanFlagField IS_LATEINIT;
  public static final BooleanFlagField IS_NEGATED;
  public static final BooleanFlagField IS_NOINLINE;
  public static final BooleanFlagField IS_NOT_DEFAULT;
  public static final BooleanFlagField IS_NULL_CHECK_PREDICATE;
  public static final BooleanFlagField IS_OPERATOR;
  public static final BooleanFlagField IS_SECONDARY;
  public static final BooleanFlagField IS_SUSPEND;
  public static final BooleanFlagField IS_TAILREC;
  public static final BooleanFlagField IS_UNSIGNED = FlagField.booleanFirst();
  public static final BooleanFlagField IS_VAR;
  public static final FlagField<ProtoBuf.MemberKind> MEMBER_KIND;
  public static final FlagField<ProtoBuf.Modality> MODALITY;
  public static final BooleanFlagField SUSPEND_TYPE = ;
  public static final FlagField<ProtoBuf.Visibility> VISIBILITY;
  
  static
  {
    Object localObject = FlagField.booleanFirst();
    HAS_ANNOTATIONS = (BooleanFlagField)localObject;
    localObject = FlagField.after((FlagField)localObject, ProtoBuf.Visibility.values());
    VISIBILITY = (FlagField)localObject;
    localObject = FlagField.after((FlagField)localObject, ProtoBuf.Modality.values());
    MODALITY = (FlagField)localObject;
    localObject = FlagField.after((FlagField)localObject, ProtoBuf.Class.Kind.values());
    CLASS_KIND = (FlagField)localObject;
    localObject = FlagField.booleanAfter((FlagField)localObject);
    IS_INNER = (BooleanFlagField)localObject;
    localObject = FlagField.booleanAfter((FlagField)localObject);
    IS_DATA = (BooleanFlagField)localObject;
    localObject = FlagField.booleanAfter((FlagField)localObject);
    IS_EXTERNAL_CLASS = (BooleanFlagField)localObject;
    localObject = FlagField.booleanAfter((FlagField)localObject);
    IS_EXPECT_CLASS = (BooleanFlagField)localObject;
    IS_INLINE_CLASS = FlagField.booleanAfter((FlagField)localObject);
    IS_SECONDARY = FlagField.booleanAfter(VISIBILITY);
    localObject = FlagField.after(MODALITY, ProtoBuf.MemberKind.values());
    MEMBER_KIND = (FlagField)localObject;
    localObject = FlagField.booleanAfter((FlagField)localObject);
    IS_OPERATOR = (BooleanFlagField)localObject;
    localObject = FlagField.booleanAfter((FlagField)localObject);
    IS_INFIX = (BooleanFlagField)localObject;
    localObject = FlagField.booleanAfter((FlagField)localObject);
    IS_INLINE = (BooleanFlagField)localObject;
    localObject = FlagField.booleanAfter((FlagField)localObject);
    IS_TAILREC = (BooleanFlagField)localObject;
    localObject = FlagField.booleanAfter((FlagField)localObject);
    IS_EXTERNAL_FUNCTION = (BooleanFlagField)localObject;
    localObject = FlagField.booleanAfter((FlagField)localObject);
    IS_SUSPEND = (BooleanFlagField)localObject;
    IS_EXPECT_FUNCTION = FlagField.booleanAfter((FlagField)localObject);
    localObject = FlagField.booleanAfter(MEMBER_KIND);
    IS_VAR = (BooleanFlagField)localObject;
    localObject = FlagField.booleanAfter((FlagField)localObject);
    HAS_GETTER = (BooleanFlagField)localObject;
    localObject = FlagField.booleanAfter((FlagField)localObject);
    HAS_SETTER = (BooleanFlagField)localObject;
    localObject = FlagField.booleanAfter((FlagField)localObject);
    IS_CONST = (BooleanFlagField)localObject;
    localObject = FlagField.booleanAfter((FlagField)localObject);
    IS_LATEINIT = (BooleanFlagField)localObject;
    localObject = FlagField.booleanAfter((FlagField)localObject);
    HAS_CONSTANT = (BooleanFlagField)localObject;
    localObject = FlagField.booleanAfter((FlagField)localObject);
    IS_EXTERNAL_PROPERTY = (BooleanFlagField)localObject;
    localObject = FlagField.booleanAfter((FlagField)localObject);
    IS_DELEGATED = (BooleanFlagField)localObject;
    IS_EXPECT_PROPERTY = FlagField.booleanAfter((FlagField)localObject);
    localObject = FlagField.booleanAfter(HAS_ANNOTATIONS);
    DECLARES_DEFAULT_VALUE = (BooleanFlagField)localObject;
    localObject = FlagField.booleanAfter((FlagField)localObject);
    IS_CROSSINLINE = (BooleanFlagField)localObject;
    IS_NOINLINE = FlagField.booleanAfter((FlagField)localObject);
    localObject = FlagField.booleanAfter(MODALITY);
    IS_NOT_DEFAULT = (BooleanFlagField)localObject;
    localObject = FlagField.booleanAfter((FlagField)localObject);
    IS_EXTERNAL_ACCESSOR = (BooleanFlagField)localObject;
    IS_INLINE_ACCESSOR = FlagField.booleanAfter((FlagField)localObject);
    localObject = FlagField.booleanFirst();
    IS_NEGATED = (BooleanFlagField)localObject;
    IS_NULL_CHECK_PREDICATE = FlagField.booleanAfter((FlagField)localObject);
  }
  
  public static int getAccessorFlags(boolean paramBoolean1, ProtoBuf.Visibility paramVisibility, ProtoBuf.Modality paramModality, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    if (paramVisibility == null) {
      $$$reportNull$$$0(10);
    }
    if (paramModality == null) {
      $$$reportNull$$$0(11);
    }
    return HAS_ANNOTATIONS.toFlags(Boolean.valueOf(paramBoolean1)) | MODALITY.toFlags(paramModality) | VISIBILITY.toFlags(paramVisibility) | IS_NOT_DEFAULT.toFlags(Boolean.valueOf(paramBoolean2)) | IS_EXTERNAL_ACCESSOR.toFlags(Boolean.valueOf(paramBoolean3)) | IS_INLINE_ACCESSOR.toFlags(Boolean.valueOf(paramBoolean4));
  }
  
  public static class BooleanFlagField
    extends Flags.FlagField<Boolean>
  {
    public BooleanFlagField(int paramInt)
    {
      super(1, null);
    }
    
    public Boolean get(int paramInt)
    {
      int i = this.offset;
      boolean bool = true;
      if ((paramInt & 1 << i) == 0) {
        bool = false;
      }
      Boolean localBoolean = Boolean.valueOf(bool);
      if (localBoolean == null) {
        $$$reportNull$$$0(0);
      }
      return localBoolean;
    }
    
    public int toFlags(Boolean paramBoolean)
    {
      int i;
      if (paramBoolean.booleanValue()) {
        i = 1 << this.offset;
      } else {
        i = 0;
      }
      return i;
    }
  }
  
  private static class EnumLiteFlagField<E extends Internal.EnumLite>
    extends Flags.FlagField<E>
  {
    private final E[] values;
    
    public EnumLiteFlagField(int paramInt, E[] paramArrayOfE)
    {
      super(bitWidth(paramArrayOfE), null);
      this.values = paramArrayOfE;
    }
    
    private static <E> int bitWidth(E[] paramArrayOfE)
    {
      if (paramArrayOfE == null) {
        $$$reportNull$$$0(0);
      }
      int i = paramArrayOfE.length - 1;
      if (i == 0) {
        return 1;
      }
      for (int j = 31; j >= 0; j--) {
        if ((1 << j & i) != 0) {
          return j + 1;
        }
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Empty enum: ");
      localStringBuilder.append(paramArrayOfE.getClass());
      throw new IllegalStateException(localStringBuilder.toString());
    }
    
    public E get(int paramInt)
    {
      int i = this.bitWidth;
      int j = this.offset;
      int k = this.offset;
      for (Internal.EnumLite localEnumLite : this.values) {
        if (localEnumLite.getNumber() == (paramInt & (1 << i) - 1 << j) >> k) {
          return localEnumLite;
        }
      }
      return null;
    }
    
    public int toFlags(E paramE)
    {
      return paramE.getNumber() << this.offset;
    }
  }
  
  public static abstract class FlagField<E>
  {
    public final int bitWidth;
    public final int offset;
    
    private FlagField(int paramInt1, int paramInt2)
    {
      this.offset = paramInt1;
      this.bitWidth = paramInt2;
    }
    
    public static <E extends Internal.EnumLite> FlagField<E> after(FlagField<?> paramFlagField, E[] paramArrayOfE)
    {
      return new Flags.EnumLiteFlagField(paramFlagField.offset + paramFlagField.bitWidth, paramArrayOfE);
    }
    
    public static Flags.BooleanFlagField booleanAfter(FlagField<?> paramFlagField)
    {
      return new Flags.BooleanFlagField(paramFlagField.offset + paramFlagField.bitWidth);
    }
    
    public static Flags.BooleanFlagField booleanFirst()
    {
      return new Flags.BooleanFlagField(0);
    }
    
    public abstract E get(int paramInt);
    
    public abstract int toFlags(E paramE);
  }
}

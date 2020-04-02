package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.Modifier;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.load.java.JavaVisibilities;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaModifierListOwner;

public abstract interface ReflectJavaModifierListOwner
  extends JavaModifierListOwner
{
  public abstract int getModifiers();
  
  public static final class DefaultImpls
  {
    public static Visibility getVisibility(ReflectJavaModifierListOwner paramReflectJavaModifierListOwner)
    {
      int i = paramReflectJavaModifierListOwner.getModifiers();
      if (Modifier.isPublic(i))
      {
        paramReflectJavaModifierListOwner = Visibilities.PUBLIC;
        Intrinsics.checkExpressionValueIsNotNull(paramReflectJavaModifierListOwner, "Visibilities.PUBLIC");
      }
      else if (Modifier.isPrivate(i))
      {
        paramReflectJavaModifierListOwner = Visibilities.PRIVATE;
        Intrinsics.checkExpressionValueIsNotNull(paramReflectJavaModifierListOwner, "Visibilities.PRIVATE");
      }
      else if (Modifier.isProtected(i))
      {
        if (Modifier.isStatic(i)) {
          paramReflectJavaModifierListOwner = JavaVisibilities.PROTECTED_STATIC_VISIBILITY;
        } else {
          paramReflectJavaModifierListOwner = JavaVisibilities.PROTECTED_AND_PACKAGE;
        }
        Intrinsics.checkExpressionValueIsNotNull(paramReflectJavaModifierListOwner, "if (Modifier.isStatic(mo…ies.PROTECTED_AND_PACKAGE");
      }
      else
      {
        paramReflectJavaModifierListOwner = JavaVisibilities.PACKAGE_VISIBILITY;
        Intrinsics.checkExpressionValueIsNotNull(paramReflectJavaModifierListOwner, "JavaVisibilities.PACKAGE_VISIBILITY");
      }
      return paramReflectJavaModifierListOwner;
    }
    
    public static boolean isAbstract(ReflectJavaModifierListOwner paramReflectJavaModifierListOwner)
    {
      return Modifier.isAbstract(paramReflectJavaModifierListOwner.getModifiers());
    }
    
    public static boolean isFinal(ReflectJavaModifierListOwner paramReflectJavaModifierListOwner)
    {
      return Modifier.isFinal(paramReflectJavaModifierListOwner.getModifiers());
    }
    
    public static boolean isStatic(ReflectJavaModifierListOwner paramReflectJavaModifierListOwner)
    {
      return Modifier.isStatic(paramReflectJavaModifierListOwner.getModifiers());
    }
  }
}

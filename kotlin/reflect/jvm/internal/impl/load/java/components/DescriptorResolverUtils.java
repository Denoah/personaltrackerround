package kotlin.reflect.jvm.internal.impl.load.java.components;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifierType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMember;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMethod;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaValueParameter;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.NonReportingOverrideStrategy;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ErrorReporter;

public final class DescriptorResolverUtils
{
  public static ValueParameterDescriptor getAnnotationParameterByName(Name paramName, ClassDescriptor paramClassDescriptor)
  {
    if (paramName == null) {
      $$$reportNull$$$0(19);
    }
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(20);
    }
    paramClassDescriptor = paramClassDescriptor.getConstructors();
    if (paramClassDescriptor.size() != 1) {
      return null;
    }
    Iterator localIterator = ((ClassConstructorDescriptor)paramClassDescriptor.iterator().next()).getValueParameters().iterator();
    while (localIterator.hasNext())
    {
      paramClassDescriptor = (ValueParameterDescriptor)localIterator.next();
      if (paramClassDescriptor.getName().equals(paramName)) {
        return paramClassDescriptor;
      }
    }
    return null;
  }
  
  private static boolean isMethodWithOneObjectParameter(JavaMethod paramJavaMethod)
  {
    if (paramJavaMethod == null) {
      $$$reportNull$$$0(23);
    }
    paramJavaMethod = paramJavaMethod.getValueParameters();
    int i = paramJavaMethod.size();
    boolean bool = true;
    if (i == 1)
    {
      paramJavaMethod = ((JavaValueParameter)paramJavaMethod.get(0)).getType();
      if ((paramJavaMethod instanceof JavaClassifierType))
      {
        paramJavaMethod = ((JavaClassifierType)paramJavaMethod).getClassifier();
        if ((paramJavaMethod instanceof JavaClass))
        {
          paramJavaMethod = ((JavaClass)paramJavaMethod).getFqName();
          if ((paramJavaMethod == null) || (!paramJavaMethod.asString().equals("java.lang.Object"))) {
            bool = false;
          }
          return bool;
        }
      }
    }
    return false;
  }
  
  private static boolean isObjectMethod(JavaMethod paramJavaMethod)
  {
    if (paramJavaMethod == null) {
      $$$reportNull$$$0(22);
    }
    String str = paramJavaMethod.getName().asString();
    if ((!str.equals("toString")) && (!str.equals("hashCode")))
    {
      if (str.equals("equals")) {
        return isMethodWithOneObjectParameter(paramJavaMethod);
      }
      return false;
    }
    return paramJavaMethod.getValueParameters().isEmpty();
  }
  
  public static boolean isObjectMethodInInterface(JavaMember paramJavaMember)
  {
    if (paramJavaMember == null) {
      $$$reportNull$$$0(21);
    }
    boolean bool;
    if ((paramJavaMember.getContainingClass().isInterface()) && ((paramJavaMember instanceof JavaMethod)) && (isObjectMethod((JavaMethod)paramJavaMember))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static <D extends CallableMemberDescriptor> Collection<D> resolveOverrides(Name paramName, Collection<D> paramCollection1, Collection<D> paramCollection2, ClassDescriptor paramClassDescriptor, ErrorReporter paramErrorReporter, OverridingUtil paramOverridingUtil, final boolean paramBoolean)
  {
    if (paramName == null) {
      $$$reportNull$$$0(12);
    }
    if (paramCollection1 == null) {
      $$$reportNull$$$0(13);
    }
    if (paramCollection2 == null) {
      $$$reportNull$$$0(14);
    }
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(15);
    }
    if (paramErrorReporter == null) {
      $$$reportNull$$$0(16);
    }
    if (paramOverridingUtil == null) {
      $$$reportNull$$$0(17);
    }
    final LinkedHashSet localLinkedHashSet = new LinkedHashSet();
    paramOverridingUtil.generateOverridesInFunctionGroup(paramName, paramCollection1, paramCollection2, paramClassDescriptor, new NonReportingOverrideStrategy()
    {
      public void addFakeOverride(CallableMemberDescriptor paramAnonymousCallableMemberDescriptor)
      {
        if (paramAnonymousCallableMemberDescriptor == null) {
          $$$reportNull$$$0(0);
        }
        OverridingUtil.resolveUnknownVisibilityForMember(paramAnonymousCallableMemberDescriptor, new Function1()
        {
          public Unit invoke(CallableMemberDescriptor paramAnonymous2CallableMemberDescriptor)
          {
            if (paramAnonymous2CallableMemberDescriptor == null) {
              $$$reportNull$$$0(0);
            }
            DescriptorResolverUtils.1.this.val$errorReporter.reportCannotInferVisibility(paramAnonymous2CallableMemberDescriptor);
            return Unit.INSTANCE;
          }
        });
        localLinkedHashSet.add(paramAnonymousCallableMemberDescriptor);
      }
      
      public void conflict(CallableMemberDescriptor paramAnonymousCallableMemberDescriptor1, CallableMemberDescriptor paramAnonymousCallableMemberDescriptor2)
      {
        if (paramAnonymousCallableMemberDescriptor1 == null) {
          $$$reportNull$$$0(1);
        }
        if (paramAnonymousCallableMemberDescriptor2 == null) {
          $$$reportNull$$$0(2);
        }
      }
      
      public void setOverriddenDescriptors(CallableMemberDescriptor paramAnonymousCallableMemberDescriptor, Collection<? extends CallableMemberDescriptor> paramAnonymousCollection)
      {
        if (paramAnonymousCallableMemberDescriptor == null) {
          $$$reportNull$$$0(3);
        }
        if (paramAnonymousCollection == null) {
          $$$reportNull$$$0(4);
        }
        if ((paramBoolean) && (paramAnonymousCallableMemberDescriptor.getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE)) {
          return;
        }
        super.setOverriddenDescriptors(paramAnonymousCallableMemberDescriptor, paramAnonymousCollection);
      }
    });
    return localLinkedHashSet;
  }
  
  public static <D extends CallableMemberDescriptor> Collection<D> resolveOverridesForNonStaticMembers(Name paramName, Collection<D> paramCollection1, Collection<D> paramCollection2, ClassDescriptor paramClassDescriptor, ErrorReporter paramErrorReporter, OverridingUtil paramOverridingUtil)
  {
    if (paramName == null) {
      $$$reportNull$$$0(0);
    }
    if (paramCollection1 == null) {
      $$$reportNull$$$0(1);
    }
    if (paramCollection2 == null) {
      $$$reportNull$$$0(2);
    }
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(3);
    }
    if (paramErrorReporter == null) {
      $$$reportNull$$$0(4);
    }
    if (paramOverridingUtil == null) {
      $$$reportNull$$$0(5);
    }
    return resolveOverrides(paramName, paramCollection1, paramCollection2, paramClassDescriptor, paramErrorReporter, paramOverridingUtil, false);
  }
  
  public static <D extends CallableMemberDescriptor> Collection<D> resolveOverridesForStaticMembers(Name paramName, Collection<D> paramCollection1, Collection<D> paramCollection2, ClassDescriptor paramClassDescriptor, ErrorReporter paramErrorReporter, OverridingUtil paramOverridingUtil)
  {
    if (paramName == null) {
      $$$reportNull$$$0(6);
    }
    if (paramCollection1 == null) {
      $$$reportNull$$$0(7);
    }
    if (paramCollection2 == null) {
      $$$reportNull$$$0(8);
    }
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(9);
    }
    if (paramErrorReporter == null) {
      $$$reportNull$$$0(10);
    }
    if (paramOverridingUtil == null) {
      $$$reportNull$$$0(11);
    }
    return resolveOverrides(paramName, paramCollection1, paramCollection2, paramClassDescriptor, paramErrorReporter, paramOverridingUtil, true);
  }
}

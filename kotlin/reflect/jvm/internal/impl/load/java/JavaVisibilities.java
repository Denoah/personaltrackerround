package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithVisibility;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ReceiverValue;

public class JavaVisibilities
{
  public static final Visibility PACKAGE_VISIBILITY = new Visibility("package", false)
  {
    protected Integer compareTo(Visibility paramAnonymousVisibility)
    {
      if (paramAnonymousVisibility == null) {
        $$$reportNull$$$0(2);
      }
      if (this == paramAnonymousVisibility) {
        return Integer.valueOf(0);
      }
      if (Visibilities.isPrivate(paramAnonymousVisibility)) {
        return Integer.valueOf(1);
      }
      return Integer.valueOf(-1);
    }
    
    public String getInternalDisplayName()
    {
      return "public/*package*/";
    }
    
    public boolean isVisible(ReceiverValue paramAnonymousReceiverValue, DeclarationDescriptorWithVisibility paramAnonymousDeclarationDescriptorWithVisibility, DeclarationDescriptor paramAnonymousDeclarationDescriptor)
    {
      if (paramAnonymousDeclarationDescriptorWithVisibility == null) {
        $$$reportNull$$$0(0);
      }
      if (paramAnonymousDeclarationDescriptor == null) {
        $$$reportNull$$$0(1);
      }
      return JavaVisibilities.areInSamePackage(paramAnonymousDeclarationDescriptorWithVisibility, paramAnonymousDeclarationDescriptor);
    }
    
    public Visibility normalize()
    {
      Visibility localVisibility = Visibilities.PROTECTED;
      if (localVisibility == null) {
        $$$reportNull$$$0(3);
      }
      return localVisibility;
    }
  };
  public static final Visibility PROTECTED_AND_PACKAGE = new Visibility("protected_and_package", true)
  {
    protected Integer compareTo(Visibility paramAnonymousVisibility)
    {
      if (paramAnonymousVisibility == null) {
        $$$reportNull$$$0(2);
      }
      if (this == paramAnonymousVisibility) {
        return Integer.valueOf(0);
      }
      if (paramAnonymousVisibility == Visibilities.INTERNAL) {
        return null;
      }
      if (Visibilities.isPrivate(paramAnonymousVisibility)) {
        return Integer.valueOf(1);
      }
      return Integer.valueOf(-1);
    }
    
    public String getInternalDisplayName()
    {
      return "protected/*protected and package*/";
    }
    
    public boolean isVisible(ReceiverValue paramAnonymousReceiverValue, DeclarationDescriptorWithVisibility paramAnonymousDeclarationDescriptorWithVisibility, DeclarationDescriptor paramAnonymousDeclarationDescriptor)
    {
      if (paramAnonymousDeclarationDescriptorWithVisibility == null) {
        $$$reportNull$$$0(0);
      }
      if (paramAnonymousDeclarationDescriptor == null) {
        $$$reportNull$$$0(1);
      }
      return JavaVisibilities.isVisibleForProtectedAndPackage(paramAnonymousReceiverValue, paramAnonymousDeclarationDescriptorWithVisibility, paramAnonymousDeclarationDescriptor);
    }
    
    public Visibility normalize()
    {
      Visibility localVisibility = Visibilities.PROTECTED;
      if (localVisibility == null) {
        $$$reportNull$$$0(3);
      }
      return localVisibility;
    }
  };
  public static final Visibility PROTECTED_STATIC_VISIBILITY = new Visibility("protected_static", true)
  {
    public String getInternalDisplayName()
    {
      return "protected/*protected static*/";
    }
    
    public boolean isVisible(ReceiverValue paramAnonymousReceiverValue, DeclarationDescriptorWithVisibility paramAnonymousDeclarationDescriptorWithVisibility, DeclarationDescriptor paramAnonymousDeclarationDescriptor)
    {
      if (paramAnonymousDeclarationDescriptorWithVisibility == null) {
        $$$reportNull$$$0(0);
      }
      if (paramAnonymousDeclarationDescriptor == null) {
        $$$reportNull$$$0(1);
      }
      return JavaVisibilities.isVisibleForProtectedAndPackage(paramAnonymousReceiverValue, paramAnonymousDeclarationDescriptorWithVisibility, paramAnonymousDeclarationDescriptor);
    }
    
    public Visibility normalize()
    {
      Visibility localVisibility = Visibilities.PROTECTED;
      if (localVisibility == null) {
        $$$reportNull$$$0(2);
      }
      return localVisibility;
    }
  };
  
  private static boolean areInSamePackage(DeclarationDescriptor paramDeclarationDescriptor1, DeclarationDescriptor paramDeclarationDescriptor2)
  {
    if (paramDeclarationDescriptor1 == null) {
      $$$reportNull$$$0(2);
    }
    if (paramDeclarationDescriptor2 == null) {
      $$$reportNull$$$0(3);
    }
    boolean bool1 = false;
    paramDeclarationDescriptor1 = (PackageFragmentDescriptor)DescriptorUtils.getParentOfType(paramDeclarationDescriptor1, PackageFragmentDescriptor.class, false);
    paramDeclarationDescriptor2 = (PackageFragmentDescriptor)DescriptorUtils.getParentOfType(paramDeclarationDescriptor2, PackageFragmentDescriptor.class, false);
    boolean bool2 = bool1;
    if (paramDeclarationDescriptor2 != null)
    {
      bool2 = bool1;
      if (paramDeclarationDescriptor1 != null)
      {
        bool2 = bool1;
        if (paramDeclarationDescriptor1.getFqName().equals(paramDeclarationDescriptor2.getFqName())) {
          bool2 = true;
        }
      }
    }
    return bool2;
  }
  
  private static boolean isVisibleForProtectedAndPackage(ReceiverValue paramReceiverValue, DeclarationDescriptorWithVisibility paramDeclarationDescriptorWithVisibility, DeclarationDescriptor paramDeclarationDescriptor)
  {
    if (paramDeclarationDescriptorWithVisibility == null) {
      $$$reportNull$$$0(0);
    }
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(1);
    }
    if (areInSamePackage(DescriptorUtils.unwrapFakeOverrideToAnyDeclaration(paramDeclarationDescriptorWithVisibility), paramDeclarationDescriptor)) {
      return true;
    }
    return Visibilities.PROTECTED.isVisible(paramReceiverValue, paramDeclarationDescriptorWithVisibility, paramDeclarationDescriptor);
  }
}

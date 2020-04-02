package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import kotlin.collections.SetsKt;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeAliasConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ReceiverValue;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.SuperCallReceiverValue;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ThisClassReceiver;
import kotlin.reflect.jvm.internal.impl.types.DynamicTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.util.ModuleVisibilityHelper;
import kotlin.reflect.jvm.internal.impl.util.ModuleVisibilityHelper.EMPTY;
import kotlin.reflect.jvm.internal.impl.utils.CollectionsKt;

public class Visibilities
{
  public static final ReceiverValue ALWAYS_SUITABLE_RECEIVER;
  public static final Visibility DEFAULT_VISIBILITY;
  @Deprecated
  public static final ReceiverValue FALSE_IF_PROTECTED;
  public static final Visibility INHERITED;
  public static final Visibility INTERNAL;
  public static final Visibility INVISIBLE_FAKE;
  public static final Set<Visibility> INVISIBLE_FROM_OTHER_MODULES;
  private static final ReceiverValue IRRELEVANT_RECEIVER;
  public static final Visibility LOCAL;
  private static final ModuleVisibilityHelper MODULE_VISIBILITY_HELPER;
  private static final Map<Visibility, Integer> ORDERED_VISIBILITIES;
  public static final Visibility PRIVATE;
  public static final Visibility PRIVATE_TO_THIS;
  public static final Visibility PROTECTED;
  public static final Visibility PUBLIC;
  public static final Visibility UNKNOWN;
  
  static
  {
    Object localObject = Integer.valueOf(0);
    PRIVATE = new Visibility("private", false)
    {
      private boolean hasContainingSourceFile(DeclarationDescriptor paramAnonymousDeclarationDescriptor)
      {
        boolean bool = false;
        if (paramAnonymousDeclarationDescriptor == null) {
          $$$reportNull$$$0(0);
        }
        if (DescriptorUtils.getContainingSourceFile(paramAnonymousDeclarationDescriptor) != SourceFile.NO_SOURCE_FILE) {
          bool = true;
        }
        return bool;
      }
      
      public boolean isVisible(ReceiverValue paramAnonymousReceiverValue, DeclarationDescriptorWithVisibility paramAnonymousDeclarationDescriptorWithVisibility, DeclarationDescriptor paramAnonymousDeclarationDescriptor)
      {
        boolean bool = true;
        if (paramAnonymousDeclarationDescriptorWithVisibility == null) {
          $$$reportNull$$$0(1);
        }
        if (paramAnonymousDeclarationDescriptor == null) {
          $$$reportNull$$$0(2);
        }
        if ((DescriptorUtils.isTopLevelDeclaration(paramAnonymousDeclarationDescriptorWithVisibility)) && (hasContainingSourceFile(paramAnonymousDeclarationDescriptor))) {
          return Visibilities.inSameFile(paramAnonymousDeclarationDescriptorWithVisibility, paramAnonymousDeclarationDescriptor);
        }
        paramAnonymousReceiverValue = paramAnonymousDeclarationDescriptorWithVisibility;
        Object localObject;
        if ((paramAnonymousDeclarationDescriptorWithVisibility instanceof ConstructorDescriptor))
        {
          localObject = ((ConstructorDescriptor)paramAnonymousDeclarationDescriptorWithVisibility).getContainingDeclaration();
          paramAnonymousReceiverValue = paramAnonymousDeclarationDescriptorWithVisibility;
          if (DescriptorUtils.isSealedClass((DeclarationDescriptor)localObject))
          {
            paramAnonymousReceiverValue = paramAnonymousDeclarationDescriptorWithVisibility;
            if (DescriptorUtils.isTopLevelDeclaration((DeclarationDescriptor)localObject))
            {
              paramAnonymousReceiverValue = paramAnonymousDeclarationDescriptorWithVisibility;
              if ((paramAnonymousDeclarationDescriptor instanceof ConstructorDescriptor))
              {
                paramAnonymousReceiverValue = paramAnonymousDeclarationDescriptorWithVisibility;
                if (DescriptorUtils.isTopLevelDeclaration(paramAnonymousDeclarationDescriptor.getContainingDeclaration()))
                {
                  paramAnonymousReceiverValue = paramAnonymousDeclarationDescriptorWithVisibility;
                  if (Visibilities.inSameFile(paramAnonymousDeclarationDescriptorWithVisibility, paramAnonymousDeclarationDescriptor)) {
                    return true;
                  }
                }
              }
            }
          }
        }
        do
        {
          paramAnonymousDeclarationDescriptorWithVisibility = paramAnonymousReceiverValue;
          if (paramAnonymousReceiverValue == null) {
            break;
          }
          localObject = paramAnonymousReceiverValue.getContainingDeclaration();
          if ((localObject instanceof ClassDescriptor))
          {
            paramAnonymousDeclarationDescriptorWithVisibility = (DeclarationDescriptorWithVisibility)localObject;
            if (!DescriptorUtils.isCompanionObject((DeclarationDescriptor)localObject)) {
              break;
            }
          }
          paramAnonymousReceiverValue = (ReceiverValue)localObject;
        } while (!(localObject instanceof PackageFragmentDescriptor));
        paramAnonymousDeclarationDescriptorWithVisibility = (DeclarationDescriptorWithVisibility)localObject;
        if (paramAnonymousDeclarationDescriptorWithVisibility == null) {
          return false;
        }
        while (paramAnonymousDeclarationDescriptor != null)
        {
          if (paramAnonymousDeclarationDescriptorWithVisibility == paramAnonymousDeclarationDescriptor) {
            return true;
          }
          if ((paramAnonymousDeclarationDescriptor instanceof PackageFragmentDescriptor))
          {
            if ((!(paramAnonymousDeclarationDescriptorWithVisibility instanceof PackageFragmentDescriptor)) || (!((PackageFragmentDescriptor)paramAnonymousDeclarationDescriptorWithVisibility).getFqName().equals(((PackageFragmentDescriptor)paramAnonymousDeclarationDescriptor).getFqName())) || (!DescriptorUtils.areInSameModule(paramAnonymousDeclarationDescriptor, paramAnonymousDeclarationDescriptorWithVisibility))) {
              bool = false;
            }
            return bool;
          }
          paramAnonymousDeclarationDescriptor = paramAnonymousDeclarationDescriptor.getContainingDeclaration();
        }
        return false;
      }
    };
    PRIVATE_TO_THIS = new Visibility("private_to_this", false)
    {
      public String getInternalDisplayName()
      {
        return "private/*private to this*/";
      }
      
      public boolean isVisible(ReceiverValue paramAnonymousReceiverValue, DeclarationDescriptorWithVisibility paramAnonymousDeclarationDescriptorWithVisibility, DeclarationDescriptor paramAnonymousDeclarationDescriptor)
      {
        if (paramAnonymousDeclarationDescriptorWithVisibility == null) {
          $$$reportNull$$$0(0);
        }
        if (paramAnonymousDeclarationDescriptor == null) {
          $$$reportNull$$$0(1);
        }
        if (Visibilities.PRIVATE.isVisible(paramAnonymousReceiverValue, paramAnonymousDeclarationDescriptorWithVisibility, paramAnonymousDeclarationDescriptor))
        {
          if (paramAnonymousReceiverValue == Visibilities.ALWAYS_SUITABLE_RECEIVER) {
            return true;
          }
          if (paramAnonymousReceiverValue == Visibilities.IRRELEVANT_RECEIVER) {
            return false;
          }
          paramAnonymousDeclarationDescriptorWithVisibility = DescriptorUtils.getParentOfType(paramAnonymousDeclarationDescriptorWithVisibility, ClassDescriptor.class);
          if ((paramAnonymousDeclarationDescriptorWithVisibility != null) && ((paramAnonymousReceiverValue instanceof ThisClassReceiver))) {
            return ((ThisClassReceiver)paramAnonymousReceiverValue).getClassDescriptor().getOriginal().equals(paramAnonymousDeclarationDescriptorWithVisibility.getOriginal());
          }
        }
        return false;
      }
    };
    Integer localInteger = Integer.valueOf(1);
    PROTECTED = new Visibility("protected", true)
    {
      private boolean doesReceiverFitForProtectedVisibility(ReceiverValue paramAnonymousReceiverValue, DeclarationDescriptorWithVisibility paramAnonymousDeclarationDescriptorWithVisibility, ClassDescriptor paramAnonymousClassDescriptor)
      {
        if (paramAnonymousDeclarationDescriptorWithVisibility == null) {
          $$$reportNull$$$0(2);
        }
        if (paramAnonymousClassDescriptor == null) {
          $$$reportNull$$$0(3);
        }
        ReceiverValue localReceiverValue = Visibilities.FALSE_IF_PROTECTED;
        boolean bool1 = false;
        if (paramAnonymousReceiverValue == localReceiverValue) {
          return false;
        }
        if (!(paramAnonymousDeclarationDescriptorWithVisibility instanceof CallableMemberDescriptor)) {
          return true;
        }
        if ((paramAnonymousDeclarationDescriptorWithVisibility instanceof ConstructorDescriptor)) {
          return true;
        }
        if (paramAnonymousReceiverValue == Visibilities.ALWAYS_SUITABLE_RECEIVER) {
          return true;
        }
        boolean bool2 = bool1;
        if (paramAnonymousReceiverValue != Visibilities.IRRELEVANT_RECEIVER) {
          if (paramAnonymousReceiverValue == null)
          {
            bool2 = bool1;
          }
          else
          {
            if ((paramAnonymousReceiverValue instanceof SuperCallReceiverValue)) {
              paramAnonymousReceiverValue = ((SuperCallReceiverValue)paramAnonymousReceiverValue).getThisType();
            } else {
              paramAnonymousReceiverValue = paramAnonymousReceiverValue.getType();
            }
            if (!DescriptorUtils.isSubtypeOfClass(paramAnonymousReceiverValue, paramAnonymousClassDescriptor))
            {
              bool2 = bool1;
              if (!DynamicTypesKt.isDynamic(paramAnonymousReceiverValue)) {}
            }
            else
            {
              bool2 = true;
            }
          }
        }
        return bool2;
      }
      
      public boolean isVisible(ReceiverValue paramAnonymousReceiverValue, DeclarationDescriptorWithVisibility paramAnonymousDeclarationDescriptorWithVisibility, DeclarationDescriptor paramAnonymousDeclarationDescriptor)
      {
        if (paramAnonymousDeclarationDescriptorWithVisibility == null) {
          $$$reportNull$$$0(0);
        }
        if (paramAnonymousDeclarationDescriptor == null) {
          $$$reportNull$$$0(1);
        }
        Object localObject = (ClassDescriptor)DescriptorUtils.getParentOfType(paramAnonymousDeclarationDescriptorWithVisibility, ClassDescriptor.class);
        paramAnonymousDeclarationDescriptor = (ClassDescriptor)DescriptorUtils.getParentOfType(paramAnonymousDeclarationDescriptor, ClassDescriptor.class, false);
        if (paramAnonymousDeclarationDescriptor == null) {
          return false;
        }
        if ((localObject != null) && (DescriptorUtils.isCompanionObject((DeclarationDescriptor)localObject)))
        {
          localObject = (ClassDescriptor)DescriptorUtils.getParentOfType((DeclarationDescriptor)localObject, ClassDescriptor.class);
          if ((localObject != null) && (DescriptorUtils.isSubclass(paramAnonymousDeclarationDescriptor, (ClassDescriptor)localObject))) {
            return true;
          }
        }
        localObject = DescriptorUtils.unwrapFakeOverrideToAnyDeclaration(paramAnonymousDeclarationDescriptorWithVisibility);
        ClassDescriptor localClassDescriptor = (ClassDescriptor)DescriptorUtils.getParentOfType((DeclarationDescriptor)localObject, ClassDescriptor.class);
        if (localClassDescriptor == null) {
          return false;
        }
        if ((DescriptorUtils.isSubclass(paramAnonymousDeclarationDescriptor, localClassDescriptor)) && (doesReceiverFitForProtectedVisibility(paramAnonymousReceiverValue, (DeclarationDescriptorWithVisibility)localObject, paramAnonymousDeclarationDescriptor))) {
          return true;
        }
        return isVisible(paramAnonymousReceiverValue, paramAnonymousDeclarationDescriptorWithVisibility, paramAnonymousDeclarationDescriptor.getContainingDeclaration());
      }
    };
    INTERNAL = new Visibility("internal", false)
    {
      public boolean isVisible(ReceiverValue paramAnonymousReceiverValue, DeclarationDescriptorWithVisibility paramAnonymousDeclarationDescriptorWithVisibility, DeclarationDescriptor paramAnonymousDeclarationDescriptor)
      {
        if (paramAnonymousDeclarationDescriptorWithVisibility == null) {
          $$$reportNull$$$0(0);
        }
        if (paramAnonymousDeclarationDescriptor == null) {
          $$$reportNull$$$0(1);
        }
        paramAnonymousReceiverValue = DescriptorUtils.getContainingModule(paramAnonymousDeclarationDescriptorWithVisibility);
        if (!DescriptorUtils.getContainingModule(paramAnonymousDeclarationDescriptor).shouldSeeInternalsOf(paramAnonymousReceiverValue)) {
          return false;
        }
        return Visibilities.MODULE_VISIBILITY_HELPER.isInFriendModule(paramAnonymousDeclarationDescriptorWithVisibility, paramAnonymousDeclarationDescriptor);
      }
    };
    PUBLIC = new Visibility("public", true)
    {
      public boolean isVisible(ReceiverValue paramAnonymousReceiverValue, DeclarationDescriptorWithVisibility paramAnonymousDeclarationDescriptorWithVisibility, DeclarationDescriptor paramAnonymousDeclarationDescriptor)
      {
        if (paramAnonymousDeclarationDescriptorWithVisibility == null) {
          $$$reportNull$$$0(0);
        }
        if (paramAnonymousDeclarationDescriptor == null) {
          $$$reportNull$$$0(1);
        }
        return true;
      }
    };
    LOCAL = new Visibility("local", false)
    {
      public boolean isVisible(ReceiverValue paramAnonymousReceiverValue, DeclarationDescriptorWithVisibility paramAnonymousDeclarationDescriptorWithVisibility, DeclarationDescriptor paramAnonymousDeclarationDescriptor)
      {
        if (paramAnonymousDeclarationDescriptorWithVisibility == null) {
          $$$reportNull$$$0(0);
        }
        if (paramAnonymousDeclarationDescriptor == null) {
          $$$reportNull$$$0(1);
        }
        throw new IllegalStateException("This method shouldn't be invoked for LOCAL visibility");
      }
    };
    INHERITED = new Visibility("inherited", false)
    {
      public boolean isVisible(ReceiverValue paramAnonymousReceiverValue, DeclarationDescriptorWithVisibility paramAnonymousDeclarationDescriptorWithVisibility, DeclarationDescriptor paramAnonymousDeclarationDescriptor)
      {
        if (paramAnonymousDeclarationDescriptorWithVisibility == null) {
          $$$reportNull$$$0(0);
        }
        if (paramAnonymousDeclarationDescriptor == null) {
          $$$reportNull$$$0(1);
        }
        throw new IllegalStateException("Visibility is unknown yet");
      }
    };
    INVISIBLE_FAKE = new Visibility("invisible_fake", false)
    {
      public boolean isVisible(ReceiverValue paramAnonymousReceiverValue, DeclarationDescriptorWithVisibility paramAnonymousDeclarationDescriptorWithVisibility, DeclarationDescriptor paramAnonymousDeclarationDescriptor)
      {
        if (paramAnonymousDeclarationDescriptorWithVisibility == null) {
          $$$reportNull$$$0(0);
        }
        if (paramAnonymousDeclarationDescriptor == null) {
          $$$reportNull$$$0(1);
        }
        return false;
      }
    };
    UNKNOWN = new Visibility("unknown", false)
    {
      public boolean isVisible(ReceiverValue paramAnonymousReceiverValue, DeclarationDescriptorWithVisibility paramAnonymousDeclarationDescriptorWithVisibility, DeclarationDescriptor paramAnonymousDeclarationDescriptor)
      {
        if (paramAnonymousDeclarationDescriptorWithVisibility == null) {
          $$$reportNull$$$0(0);
        }
        if (paramAnonymousDeclarationDescriptor == null) {
          $$$reportNull$$$0(1);
        }
        return false;
      }
    };
    INVISIBLE_FROM_OTHER_MODULES = Collections.unmodifiableSet(SetsKt.setOf(new Visibility[] { PRIVATE, PRIVATE_TO_THIS, INTERNAL, LOCAL }));
    HashMap localHashMap = CollectionsKt.newHashMapWithExpectedSize(4);
    localHashMap.put(PRIVATE_TO_THIS, localObject);
    localHashMap.put(PRIVATE, localObject);
    localHashMap.put(INTERNAL, localInteger);
    localHashMap.put(PROTECTED, localInteger);
    localHashMap.put(PUBLIC, Integer.valueOf(2));
    ORDERED_VISIBILITIES = Collections.unmodifiableMap(localHashMap);
    DEFAULT_VISIBILITY = PUBLIC;
    IRRELEVANT_RECEIVER = new ReceiverValue()
    {
      public KotlinType getType()
      {
        throw new IllegalStateException("This method should not be called");
      }
    };
    ALWAYS_SUITABLE_RECEIVER = new ReceiverValue()
    {
      public KotlinType getType()
      {
        throw new IllegalStateException("This method should not be called");
      }
    };
    FALSE_IF_PROTECTED = new ReceiverValue()
    {
      public KotlinType getType()
      {
        throw new IllegalStateException("This method should not be called");
      }
    };
    localObject = ServiceLoader.load(ModuleVisibilityHelper.class, ModuleVisibilityHelper.class.getClassLoader()).iterator();
    if (((Iterator)localObject).hasNext()) {
      localObject = (ModuleVisibilityHelper)((Iterator)localObject).next();
    } else {
      localObject = ModuleVisibilityHelper.EMPTY.INSTANCE;
    }
    MODULE_VISIBILITY_HELPER = (ModuleVisibilityHelper)localObject;
  }
  
  public static Integer compare(Visibility paramVisibility1, Visibility paramVisibility2)
  {
    if (paramVisibility1 == null) {
      $$$reportNull$$$0(12);
    }
    if (paramVisibility2 == null) {
      $$$reportNull$$$0(13);
    }
    Integer localInteger = paramVisibility1.compareTo(paramVisibility2);
    if (localInteger != null) {
      return localInteger;
    }
    paramVisibility1 = paramVisibility2.compareTo(paramVisibility1);
    if (paramVisibility1 != null) {
      return Integer.valueOf(-paramVisibility1.intValue());
    }
    return null;
  }
  
  static Integer compareLocal(Visibility paramVisibility1, Visibility paramVisibility2)
  {
    if (paramVisibility1 == null) {
      $$$reportNull$$$0(10);
    }
    if (paramVisibility2 == null) {
      $$$reportNull$$$0(11);
    }
    if (paramVisibility1 == paramVisibility2) {
      return Integer.valueOf(0);
    }
    paramVisibility1 = (Integer)ORDERED_VISIBILITIES.get(paramVisibility1);
    paramVisibility2 = (Integer)ORDERED_VISIBILITIES.get(paramVisibility2);
    if ((paramVisibility1 != null) && (paramVisibility2 != null) && (!paramVisibility1.equals(paramVisibility2))) {
      return Integer.valueOf(paramVisibility1.intValue() - paramVisibility2.intValue());
    }
    return null;
  }
  
  public static DeclarationDescriptorWithVisibility findInvisibleMember(ReceiverValue paramReceiverValue, DeclarationDescriptorWithVisibility paramDeclarationDescriptorWithVisibility, DeclarationDescriptor paramDeclarationDescriptor)
  {
    if (paramDeclarationDescriptorWithVisibility == null) {
      $$$reportNull$$$0(8);
    }
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(9);
    }
    for (DeclarationDescriptorWithVisibility localDeclarationDescriptorWithVisibility = (DeclarationDescriptorWithVisibility)paramDeclarationDescriptorWithVisibility.getOriginal(); (localDeclarationDescriptorWithVisibility != null) && (localDeclarationDescriptorWithVisibility.getVisibility() != LOCAL); localDeclarationDescriptorWithVisibility = (DeclarationDescriptorWithVisibility)DescriptorUtils.getParentOfType(localDeclarationDescriptorWithVisibility, DeclarationDescriptorWithVisibility.class)) {
      if (!localDeclarationDescriptorWithVisibility.getVisibility().isVisible(paramReceiverValue, localDeclarationDescriptorWithVisibility, paramDeclarationDescriptor)) {
        return localDeclarationDescriptorWithVisibility;
      }
    }
    if ((paramDeclarationDescriptorWithVisibility instanceof TypeAliasConstructorDescriptor))
    {
      paramReceiverValue = findInvisibleMember(paramReceiverValue, ((TypeAliasConstructorDescriptor)paramDeclarationDescriptorWithVisibility).getUnderlyingConstructorDescriptor(), paramDeclarationDescriptor);
      if (paramReceiverValue != null) {
        return paramReceiverValue;
      }
    }
    return null;
  }
  
  public static boolean inSameFile(DeclarationDescriptor paramDeclarationDescriptor1, DeclarationDescriptor paramDeclarationDescriptor2)
  {
    if (paramDeclarationDescriptor1 == null) {
      $$$reportNull$$$0(6);
    }
    if (paramDeclarationDescriptor2 == null) {
      $$$reportNull$$$0(7);
    }
    paramDeclarationDescriptor2 = DescriptorUtils.getContainingSourceFile(paramDeclarationDescriptor2);
    if (paramDeclarationDescriptor2 != SourceFile.NO_SOURCE_FILE) {
      return paramDeclarationDescriptor2.equals(DescriptorUtils.getContainingSourceFile(paramDeclarationDescriptor1));
    }
    return false;
  }
  
  public static boolean isPrivate(Visibility paramVisibility)
  {
    if (paramVisibility == null) {
      $$$reportNull$$$0(14);
    }
    boolean bool;
    if ((paramVisibility != PRIVATE) && (paramVisibility != PRIVATE_TO_THIS)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static boolean isVisibleIgnoringReceiver(DeclarationDescriptorWithVisibility paramDeclarationDescriptorWithVisibility, DeclarationDescriptor paramDeclarationDescriptor)
  {
    if (paramDeclarationDescriptorWithVisibility == null) {
      $$$reportNull$$$0(2);
    }
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(3);
    }
    boolean bool;
    if (findInvisibleMember(ALWAYS_SUITABLE_RECEIVER, paramDeclarationDescriptorWithVisibility, paramDeclarationDescriptor) == null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}

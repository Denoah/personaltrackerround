package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.Comparator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.renderer.AnnotationArgumentsRenderingPolicy;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer.Companion;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererModifier;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

public class MemberComparator
  implements Comparator<DeclarationDescriptor>
{
  public static final MemberComparator INSTANCE = new MemberComparator();
  private static final DescriptorRenderer RENDERER = DescriptorRenderer.Companion.withOptions(new Function1()
  {
    public Unit invoke(DescriptorRendererOptions paramAnonymousDescriptorRendererOptions)
    {
      paramAnonymousDescriptorRendererOptions.setWithDefinedIn(false);
      paramAnonymousDescriptorRendererOptions.setVerbose(true);
      paramAnonymousDescriptorRendererOptions.setAnnotationArgumentsRenderingPolicy(AnnotationArgumentsRenderingPolicy.UNLESS_EMPTY);
      paramAnonymousDescriptorRendererOptions.setModifiers(DescriptorRendererModifier.ALL);
      return Unit.INSTANCE;
    }
  });
  
  private MemberComparator() {}
  
  public int compare(DeclarationDescriptor paramDeclarationDescriptor1, DeclarationDescriptor paramDeclarationDescriptor2)
  {
    Object localObject1 = NameAndTypeMemberComparator.compareInternal(paramDeclarationDescriptor1, paramDeclarationDescriptor2);
    if (localObject1 != null) {
      return ((Integer)localObject1).intValue();
    }
    Object localObject2;
    if (((paramDeclarationDescriptor1 instanceof TypeAliasDescriptor)) && ((paramDeclarationDescriptor2 instanceof TypeAliasDescriptor)))
    {
      localObject2 = (TypeAliasDescriptor)paramDeclarationDescriptor1;
      localObject1 = (TypeAliasDescriptor)paramDeclarationDescriptor2;
      i = RENDERER.renderType(((TypeAliasDescriptor)localObject2).getUnderlyingType()).compareTo(RENDERER.renderType(((TypeAliasDescriptor)localObject1).getUnderlyingType()));
      if (i != 0) {
        return i;
      }
    }
    else if (((paramDeclarationDescriptor1 instanceof CallableDescriptor)) && ((paramDeclarationDescriptor2 instanceof CallableDescriptor)))
    {
      localObject2 = (CallableDescriptor)paramDeclarationDescriptor1;
      localObject1 = (CallableDescriptor)paramDeclarationDescriptor2;
      Object localObject3 = ((CallableDescriptor)localObject2).getExtensionReceiverParameter();
      Object localObject4 = ((CallableDescriptor)localObject1).getExtensionReceiverParameter();
      if (localObject3 != null)
      {
        i = RENDERER.renderType(((ReceiverParameterDescriptor)localObject3).getType()).compareTo(RENDERER.renderType(((ReceiverParameterDescriptor)localObject4).getType()));
        if (i != 0) {
          return i;
        }
      }
      localObject3 = ((CallableDescriptor)localObject2).getValueParameters();
      localObject4 = ((CallableDescriptor)localObject1).getValueParameters();
      int j;
      for (i = 0; i < Math.min(((List)localObject3).size(), ((List)localObject4).size()); i++)
      {
        j = RENDERER.renderType(((ValueParameterDescriptor)((List)localObject3).get(i)).getType()).compareTo(RENDERER.renderType(((ValueParameterDescriptor)((List)localObject4).get(i)).getType()));
        if (j != 0) {
          return j;
        }
      }
      i = ((List)localObject3).size() - ((List)localObject4).size();
      if (i != 0) {
        return i;
      }
      List localList1 = ((CallableDescriptor)localObject2).getTypeParameters();
      List localList2 = ((CallableDescriptor)localObject1).getTypeParameters();
      for (i = 0; i < Math.min(localList1.size(), localList2.size()); i++)
      {
        localObject3 = ((TypeParameterDescriptor)localList1.get(i)).getUpperBounds();
        localObject4 = ((TypeParameterDescriptor)localList2.get(i)).getUpperBounds();
        j = ((List)localObject3).size() - ((List)localObject4).size();
        if (j != 0) {
          return j;
        }
        for (j = 0; j < ((List)localObject3).size(); j++)
        {
          int k = RENDERER.renderType((KotlinType)((List)localObject3).get(j)).compareTo(RENDERER.renderType((KotlinType)((List)localObject4).get(j)));
          if (k != 0) {
            return k;
          }
        }
      }
      i = localList1.size() - localList2.size();
      if (i != 0) {
        return i;
      }
      if (((localObject2 instanceof CallableMemberDescriptor)) && ((localObject1 instanceof CallableMemberDescriptor)))
      {
        localObject2 = ((CallableMemberDescriptor)localObject2).getKind();
        localObject1 = ((CallableMemberDescriptor)localObject1).getKind();
        i = ((CallableMemberDescriptor.Kind)localObject2).ordinal() - ((CallableMemberDescriptor.Kind)localObject1).ordinal();
        if (i != 0) {
          return i;
        }
      }
    }
    else
    {
      boolean bool = paramDeclarationDescriptor1 instanceof ClassDescriptor;
      i = 1;
      if ((!bool) || (!(paramDeclarationDescriptor2 instanceof ClassDescriptor))) {
        break label717;
      }
      localObject1 = (ClassDescriptor)paramDeclarationDescriptor1;
      localObject2 = (ClassDescriptor)paramDeclarationDescriptor2;
      if (((ClassDescriptor)localObject1).getKind().ordinal() != ((ClassDescriptor)localObject2).getKind().ordinal()) {
        return ((ClassDescriptor)localObject1).getKind().ordinal() - ((ClassDescriptor)localObject2).getKind().ordinal();
      }
      if (((ClassDescriptor)localObject1).isCompanionObject() != ((ClassDescriptor)localObject2).isCompanionObject())
      {
        if (!((ClassDescriptor)localObject1).isCompanionObject()) {
          i = -1;
        }
        return i;
      }
    }
    int i = RENDERER.render(paramDeclarationDescriptor1).compareTo(RENDERER.render(paramDeclarationDescriptor2));
    if (i != 0) {
      return i;
    }
    return DescriptorUtils.getContainingModule(paramDeclarationDescriptor1).getName().compareTo(DescriptorUtils.getContainingModule(paramDeclarationDescriptor2).getName());
    label717:
    throw new AssertionError(String.format("Unsupported pair of descriptors:\n'%s' Class: %s\n%s' Class: %s", new Object[] { paramDeclarationDescriptor1, paramDeclarationDescriptor1.getClass(), paramDeclarationDescriptor2, paramDeclarationDescriptor2.getClass() }));
  }
  
  public static class NameAndTypeMemberComparator
    implements Comparator<DeclarationDescriptor>
  {
    public static final NameAndTypeMemberComparator INSTANCE = new NameAndTypeMemberComparator();
    
    private NameAndTypeMemberComparator() {}
    
    private static Integer compareInternal(DeclarationDescriptor paramDeclarationDescriptor1, DeclarationDescriptor paramDeclarationDescriptor2)
    {
      int i = getDeclarationPriority(paramDeclarationDescriptor2) - getDeclarationPriority(paramDeclarationDescriptor1);
      if (i != 0) {
        return Integer.valueOf(i);
      }
      if ((DescriptorUtils.isEnumEntry(paramDeclarationDescriptor1)) && (DescriptorUtils.isEnumEntry(paramDeclarationDescriptor2))) {
        return Integer.valueOf(0);
      }
      i = paramDeclarationDescriptor1.getName().compareTo(paramDeclarationDescriptor2.getName());
      if (i != 0) {
        return Integer.valueOf(i);
      }
      return null;
    }
    
    private static int getDeclarationPriority(DeclarationDescriptor paramDeclarationDescriptor)
    {
      if (DescriptorUtils.isEnumEntry(paramDeclarationDescriptor)) {
        return 8;
      }
      if ((paramDeclarationDescriptor instanceof ConstructorDescriptor)) {
        return 7;
      }
      if ((paramDeclarationDescriptor instanceof PropertyDescriptor))
      {
        if (((PropertyDescriptor)paramDeclarationDescriptor).getExtensionReceiverParameter() == null) {
          return 6;
        }
        return 5;
      }
      if ((paramDeclarationDescriptor instanceof FunctionDescriptor))
      {
        if (((FunctionDescriptor)paramDeclarationDescriptor).getExtensionReceiverParameter() == null) {
          return 4;
        }
        return 3;
      }
      if ((paramDeclarationDescriptor instanceof ClassDescriptor)) {
        return 2;
      }
      if ((paramDeclarationDescriptor instanceof TypeAliasDescriptor)) {
        return 1;
      }
      return 0;
    }
    
    public int compare(DeclarationDescriptor paramDeclarationDescriptor1, DeclarationDescriptor paramDeclarationDescriptor2)
    {
      paramDeclarationDescriptor1 = compareInternal(paramDeclarationDescriptor1, paramDeclarationDescriptor2);
      int i;
      if (paramDeclarationDescriptor1 != null) {
        i = paramDeclarationDescriptor1.intValue();
      } else {
        i = 0;
      }
      return i;
    }
  }
}

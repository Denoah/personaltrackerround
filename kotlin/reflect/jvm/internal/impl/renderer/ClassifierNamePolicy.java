package kotlin.reflect.jvm.internal.impl.renderer;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;

public abstract interface ClassifierNamePolicy
{
  public abstract String renderClassifier(ClassifierDescriptor paramClassifierDescriptor, DescriptorRenderer paramDescriptorRenderer);
  
  public static final class FULLY_QUALIFIED
    implements ClassifierNamePolicy
  {
    public static final FULLY_QUALIFIED INSTANCE = new FULLY_QUALIFIED();
    
    private FULLY_QUALIFIED() {}
    
    public String renderClassifier(ClassifierDescriptor paramClassifierDescriptor, DescriptorRenderer paramDescriptorRenderer)
    {
      Intrinsics.checkParameterIsNotNull(paramClassifierDescriptor, "classifier");
      Intrinsics.checkParameterIsNotNull(paramDescriptorRenderer, "renderer");
      if ((paramClassifierDescriptor instanceof TypeParameterDescriptor))
      {
        paramClassifierDescriptor = ((TypeParameterDescriptor)paramClassifierDescriptor).getName();
        Intrinsics.checkExpressionValueIsNotNull(paramClassifierDescriptor, "classifier.name");
        return paramDescriptorRenderer.renderName(paramClassifierDescriptor, false);
      }
      paramClassifierDescriptor = DescriptorUtils.getFqName((DeclarationDescriptor)paramClassifierDescriptor);
      Intrinsics.checkExpressionValueIsNotNull(paramClassifierDescriptor, "DescriptorUtils.getFqName(classifier)");
      return paramDescriptorRenderer.renderFqName(paramClassifierDescriptor);
    }
  }
  
  public static final class SHORT
    implements ClassifierNamePolicy
  {
    public static final SHORT INSTANCE = new SHORT();
    
    private SHORT() {}
    
    public String renderClassifier(ClassifierDescriptor paramClassifierDescriptor, DescriptorRenderer paramDescriptorRenderer)
    {
      Intrinsics.checkParameterIsNotNull(paramClassifierDescriptor, "classifier");
      Intrinsics.checkParameterIsNotNull(paramDescriptorRenderer, "renderer");
      if ((paramClassifierDescriptor instanceof TypeParameterDescriptor))
      {
        paramClassifierDescriptor = ((TypeParameterDescriptor)paramClassifierDescriptor).getName();
        Intrinsics.checkExpressionValueIsNotNull(paramClassifierDescriptor, "classifier.name");
        return paramDescriptorRenderer.renderName(paramClassifierDescriptor, false);
      }
      ArrayList localArrayList = new ArrayList();
      paramClassifierDescriptor = (DeclarationDescriptor)paramClassifierDescriptor;
      do
      {
        localArrayList.add(paramClassifierDescriptor.getName());
        paramDescriptorRenderer = paramClassifierDescriptor.getContainingDeclaration();
        paramClassifierDescriptor = paramDescriptorRenderer;
      } while ((paramDescriptorRenderer instanceof ClassDescriptor));
      return RenderingUtilsKt.renderFqName(CollectionsKt.asReversedMutable((List)localArrayList));
    }
  }
  
  public static final class SOURCE_CODE_QUALIFIED
    implements ClassifierNamePolicy
  {
    public static final SOURCE_CODE_QUALIFIED INSTANCE = new SOURCE_CODE_QUALIFIED();
    
    private SOURCE_CODE_QUALIFIED() {}
    
    private final String qualifiedNameForSourceCode(ClassifierDescriptor paramClassifierDescriptor)
    {
      Object localObject = paramClassifierDescriptor.getName();
      Intrinsics.checkExpressionValueIsNotNull(localObject, "descriptor.name");
      localObject = RenderingUtilsKt.render((Name)localObject);
      if ((paramClassifierDescriptor instanceof TypeParameterDescriptor)) {
        return localObject;
      }
      paramClassifierDescriptor = paramClassifierDescriptor.getContainingDeclaration();
      Intrinsics.checkExpressionValueIsNotNull(paramClassifierDescriptor, "descriptor.containingDeclaration");
      String str = qualifierName(paramClassifierDescriptor);
      paramClassifierDescriptor = (ClassifierDescriptor)localObject;
      if (str != null)
      {
        paramClassifierDescriptor = (ClassifierDescriptor)localObject;
        if ((Intrinsics.areEqual(str, "") ^ true))
        {
          paramClassifierDescriptor = new StringBuilder();
          paramClassifierDescriptor.append(str);
          paramClassifierDescriptor.append(".");
          paramClassifierDescriptor.append((String)localObject);
          paramClassifierDescriptor = paramClassifierDescriptor.toString();
        }
      }
      return paramClassifierDescriptor;
    }
    
    private final String qualifierName(DeclarationDescriptor paramDeclarationDescriptor)
    {
      if ((paramDeclarationDescriptor instanceof ClassDescriptor))
      {
        paramDeclarationDescriptor = qualifiedNameForSourceCode((ClassifierDescriptor)paramDeclarationDescriptor);
      }
      else if ((paramDeclarationDescriptor instanceof PackageFragmentDescriptor))
      {
        paramDeclarationDescriptor = ((PackageFragmentDescriptor)paramDeclarationDescriptor).getFqName().toUnsafe();
        Intrinsics.checkExpressionValueIsNotNull(paramDeclarationDescriptor, "descriptor.fqName.toUnsafe()");
        paramDeclarationDescriptor = RenderingUtilsKt.render(paramDeclarationDescriptor);
      }
      else
      {
        paramDeclarationDescriptor = null;
      }
      return paramDeclarationDescriptor;
    }
    
    public String renderClassifier(ClassifierDescriptor paramClassifierDescriptor, DescriptorRenderer paramDescriptorRenderer)
    {
      Intrinsics.checkParameterIsNotNull(paramClassifierDescriptor, "classifier");
      Intrinsics.checkParameterIsNotNull(paramDescriptorRenderer, "renderer");
      return qualifiedNameForSourceCode(paramClassifierDescriptor);
    }
  }
}

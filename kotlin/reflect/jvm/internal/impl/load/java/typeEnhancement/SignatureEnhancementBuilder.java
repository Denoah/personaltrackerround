package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;

final class SignatureEnhancementBuilder
{
  private final Map<String, PredefinedFunctionEnhancementInfo> signatures = (Map)new LinkedHashMap();
  
  public SignatureEnhancementBuilder() {}
  
  public final Map<String, PredefinedFunctionEnhancementInfo> build()
  {
    return this.signatures;
  }
  
  public final class ClassEnhancementBuilder
  {
    private final String className;
    
    public ClassEnhancementBuilder()
    {
      this.className = localObject;
    }
    
    public final void function(String paramString, Function1<? super FunctionEnhancementBuilder, Unit> paramFunction1)
    {
      Intrinsics.checkParameterIsNotNull(paramString, "name");
      Intrinsics.checkParameterIsNotNull(paramFunction1, "block");
      Map localMap = SignatureEnhancementBuilder.access$getSignatures$p(SignatureEnhancementBuilder.this);
      paramString = new FunctionEnhancementBuilder(paramString);
      paramFunction1.invoke(paramString);
      paramString = paramString.build();
      localMap.put(paramString.getFirst(), paramString.getSecond());
    }
    
    public final String getClassName()
    {
      return this.className;
    }
    
    public final class FunctionEnhancementBuilder
    {
      private final String functionName;
      private final List<Pair<String, TypeEnhancementInfo>> parameters;
      private Pair<String, TypeEnhancementInfo> returnType;
      
      public FunctionEnhancementBuilder()
      {
        this.functionName = localObject;
        this.parameters = ((List)new ArrayList());
        this.returnType = TuplesKt.to("V", null);
      }
      
      public final Pair<String, PredefinedFunctionEnhancementInfo> build()
      {
        Object localObject1 = SignatureBuildingComponents.INSTANCE;
        Object localObject2 = SignatureEnhancementBuilder.ClassEnhancementBuilder.this.getClassName();
        String str = this.functionName;
        Object localObject3 = (Iterable)this.parameters;
        Object localObject4 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject3, 10));
        localObject3 = ((Iterable)localObject3).iterator();
        while (((Iterator)localObject3).hasNext()) {
          ((Collection)localObject4).add((String)((Pair)((Iterator)localObject3).next()).getFirst());
        }
        str = ((SignatureBuildingComponents)localObject1).signature((String)localObject2, ((SignatureBuildingComponents)localObject1).jvmDescriptor(str, (List)localObject4, (String)this.returnType.getFirst()));
        localObject1 = (TypeEnhancementInfo)this.returnType.getSecond();
        localObject4 = (Iterable)this.parameters;
        localObject2 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject4, 10));
        localObject4 = ((Iterable)localObject4).iterator();
        while (((Iterator)localObject4).hasNext()) {
          ((Collection)localObject2).add((TypeEnhancementInfo)((Pair)((Iterator)localObject4).next()).getSecond());
        }
        return TuplesKt.to(str, new PredefinedFunctionEnhancementInfo((TypeEnhancementInfo)localObject1, (List)localObject2));
      }
      
      public final void parameter(String paramString, JavaTypeQualifiers... paramVarArgs)
      {
        Intrinsics.checkParameterIsNotNull(paramString, "type");
        Intrinsics.checkParameterIsNotNull(paramVarArgs, "qualifiers");
        Collection localCollection = (Collection)this.parameters;
        int i;
        if (paramVarArgs.length == 0) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          paramVarArgs = null;
        }
        else
        {
          Object localObject = ArraysKt.withIndex(paramVarArgs);
          paramVarArgs = (Map)new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault((Iterable)localObject, 10)), 16));
          Iterator localIterator = ((Iterable)localObject).iterator();
          while (localIterator.hasNext())
          {
            localObject = (IndexedValue)localIterator.next();
            paramVarArgs.put(Integer.valueOf(((IndexedValue)localObject).getIndex()), (JavaTypeQualifiers)((IndexedValue)localObject).getValue());
          }
          paramVarArgs = new TypeEnhancementInfo(paramVarArgs);
        }
        localCollection.add(TuplesKt.to(paramString, paramVarArgs));
      }
      
      public final void returns(String paramString, JavaTypeQualifiers... paramVarArgs)
      {
        Intrinsics.checkParameterIsNotNull(paramString, "type");
        Intrinsics.checkParameterIsNotNull(paramVarArgs, "qualifiers");
        Object localObject = ArraysKt.withIndex(paramVarArgs);
        paramVarArgs = (Map)new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault((Iterable)localObject, 10)), 16));
        Iterator localIterator = ((Iterable)localObject).iterator();
        while (localIterator.hasNext())
        {
          localObject = (IndexedValue)localIterator.next();
          paramVarArgs.put(Integer.valueOf(((IndexedValue)localObject).getIndex()), (JavaTypeQualifiers)((IndexedValue)localObject).getValue());
        }
        this.returnType = TuplesKt.to(paramString, new TypeEnhancementInfo(paramVarArgs));
      }
      
      public final void returns(JvmPrimitiveType paramJvmPrimitiveType)
      {
        Intrinsics.checkParameterIsNotNull(paramJvmPrimitiveType, "type");
        this.returnType = TuplesKt.to(paramJvmPrimitiveType.getDesc(), null);
      }
    }
  }
}

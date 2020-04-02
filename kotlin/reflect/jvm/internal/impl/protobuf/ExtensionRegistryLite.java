package kotlin.reflect.jvm.internal.impl.protobuf;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ExtensionRegistryLite
{
  private static final ExtensionRegistryLite EMPTY = new ExtensionRegistryLite(true);
  private static volatile boolean eagerlyParseMessageSets = false;
  private final Map<ObjectIntPair, GeneratedMessageLite.GeneratedExtension<?, ?>> extensionsByNumber;
  
  ExtensionRegistryLite()
  {
    this.extensionsByNumber = new HashMap();
  }
  
  private ExtensionRegistryLite(boolean paramBoolean)
  {
    this.extensionsByNumber = Collections.emptyMap();
  }
  
  public static ExtensionRegistryLite getEmptyRegistry()
  {
    return EMPTY;
  }
  
  public static ExtensionRegistryLite newInstance()
  {
    return new ExtensionRegistryLite();
  }
  
  public final void add(GeneratedMessageLite.GeneratedExtension<?, ?> paramGeneratedExtension)
  {
    this.extensionsByNumber.put(new ObjectIntPair(paramGeneratedExtension.getContainingTypeDefaultInstance(), paramGeneratedExtension.getNumber()), paramGeneratedExtension);
  }
  
  public <ContainingType extends MessageLite> GeneratedMessageLite.GeneratedExtension<ContainingType, ?> findLiteExtensionByNumber(ContainingType paramContainingType, int paramInt)
  {
    return (GeneratedMessageLite.GeneratedExtension)this.extensionsByNumber.get(new ObjectIntPair(paramContainingType, paramInt));
  }
  
  private static final class ObjectIntPair
  {
    private final int number;
    private final Object object;
    
    ObjectIntPair(Object paramObject, int paramInt)
    {
      this.object = paramObject;
      this.number = paramInt;
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool1 = paramObject instanceof ObjectIntPair;
      boolean bool2 = false;
      if (!bool1) {
        return false;
      }
      paramObject = (ObjectIntPair)paramObject;
      bool1 = bool2;
      if (this.object == paramObject.object)
      {
        bool1 = bool2;
        if (this.number == paramObject.number) {
          bool1 = true;
        }
      }
      return bool1;
    }
    
    public int hashCode()
    {
      return System.identityHashCode(this.object) * 65535 + this.number;
    }
  }
}

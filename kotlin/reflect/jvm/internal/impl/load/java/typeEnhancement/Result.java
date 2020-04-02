package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.reflect.jvm.internal.impl.types.KotlinType;

class Result
{
  private final int subtreeSize;
  private final KotlinType type;
  private final boolean wereChanges;
  
  public Result(KotlinType paramKotlinType, int paramInt, boolean paramBoolean)
  {
    this.type = paramKotlinType;
    this.subtreeSize = paramInt;
    this.wereChanges = paramBoolean;
  }
  
  public final int getSubtreeSize()
  {
    return this.subtreeSize;
  }
  
  public KotlinType getType()
  {
    return this.type;
  }
  
  public final KotlinType getTypeIfChanged()
  {
    KotlinType localKotlinType = getType();
    if (!this.wereChanges) {
      localKotlinType = null;
    }
    return localKotlinType;
  }
  
  public final boolean getWereChanges()
  {
    return this.wereChanges;
  }
}

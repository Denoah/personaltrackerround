package kotlin.jvm.internal;

import kotlin.reflect.KDeclarationContainer;

public class FunctionReferenceImpl
  extends FunctionReference
{
  private final String name;
  private final KDeclarationContainer owner;
  private final String signature;
  
  public FunctionReferenceImpl(int paramInt, KDeclarationContainer paramKDeclarationContainer, String paramString1, String paramString2)
  {
    super(paramInt);
    this.owner = paramKDeclarationContainer;
    this.name = paramString1;
    this.signature = paramString2;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public KDeclarationContainer getOwner()
  {
    return this.owner;
  }
  
  public String getSignature()
  {
    return this.signature;
  }
}

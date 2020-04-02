package kotlin.reflect.jvm.internal.impl.load.java.structure;

public abstract interface JavaMember
  extends JavaAnnotationOwner, JavaModifierListOwner, JavaNamedElement
{
  public abstract JavaClass getContainingClass();
}

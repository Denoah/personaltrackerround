package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.text.StringsKt;

public class JvmDescriptorTypeWriter<T>
{
  private T jvmCurrentType;
  private int jvmCurrentTypeArrayLevel;
  private final JvmTypeFactory<T> jvmTypeFactory;
  
  public void writeArrayEnd() {}
  
  public void writeArrayType()
  {
    if (this.jvmCurrentType == null) {
      this.jvmCurrentTypeArrayLevel += 1;
    }
  }
  
  public void writeClass(T paramT)
  {
    Intrinsics.checkParameterIsNotNull(paramT, "objectType");
    writeJvmTypeAsIs(paramT);
  }
  
  protected final void writeJvmTypeAsIs(T paramT)
  {
    Intrinsics.checkParameterIsNotNull(paramT, "type");
    if (this.jvmCurrentType == null)
    {
      Object localObject = paramT;
      if (this.jvmCurrentTypeArrayLevel > 0)
      {
        localObject = this.jvmTypeFactory;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(StringsKt.repeat((CharSequence)"[", this.jvmCurrentTypeArrayLevel));
        localStringBuilder.append(this.jvmTypeFactory.toString(paramT));
        localObject = ((JvmTypeFactory)localObject).createFromString(localStringBuilder.toString());
      }
      this.jvmCurrentType = localObject;
    }
  }
  
  public void writeTypeVariable(Name paramName, T paramT)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    Intrinsics.checkParameterIsNotNull(paramT, "type");
    writeJvmTypeAsIs(paramT);
  }
}

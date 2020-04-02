package kotlin.reflect.jvm.internal.impl.incremental.components;

import java.io.Serializable;

public final class Position
  implements Serializable
{
  public static final Companion Companion = new Companion(null);
  private static final Position NO_POSITION = new Position(-1, -1);
  private final int column;
  private final int line;
  
  public Position(int paramInt1, int paramInt2)
  {
    this.line = paramInt1;
    this.column = paramInt2;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject)
    {
      if ((paramObject instanceof Position))
      {
        paramObject = (Position)paramObject;
        int i;
        if (this.line == paramObject.line) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          if (this.column == paramObject.column) {
            i = 1;
          } else {
            i = 0;
          }
          if (i != 0) {
            break label66;
          }
        }
      }
      return false;
    }
    label66:
    return true;
  }
  
  public int hashCode()
  {
    return this.line * 31 + this.column;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Position(line=");
    localStringBuilder.append(this.line);
    localStringBuilder.append(", column=");
    localStringBuilder.append(this.column);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    public final Position getNO_POSITION()
    {
      return Position.access$getNO_POSITION$cp();
    }
  }
}

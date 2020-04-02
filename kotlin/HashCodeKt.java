package kotlin;

@Metadata(bv={1, 0, 3}, d1={"\000\f\n\000\n\002\020\b\n\002\020\000\n\000\032\017\020\000\032\0020\001*\004\030\0010\002H?\b?\006\003"}, d2={"hashCode", "", "", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class HashCodeKt
{
  private static final int hashCode(Object paramObject)
  {
    int i;
    if (paramObject != null) {
      i = paramObject.hashCode();
    } else {
      i = 0;
    }
    return i;
  }
}

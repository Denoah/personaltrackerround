package kotlin.reflect.jvm.internal.pcollections;

final class IntTree<V>
{
  static final IntTree<Object> EMPTYNODE = new IntTree();
  private final long key;
  private final IntTree<V> left;
  private final IntTree<V> right;
  private final int size;
  private final V value;
  
  private IntTree()
  {
    this.size = 0;
    this.key = 0L;
    this.value = null;
    this.left = null;
    this.right = null;
  }
  
  private IntTree(long paramLong, V paramV, IntTree<V> paramIntTree1, IntTree<V> paramIntTree2)
  {
    this.key = paramLong;
    this.value = paramV;
    this.left = paramIntTree1;
    this.right = paramIntTree2;
    this.size = (paramIntTree1.size + 1 + paramIntTree2.size);
  }
  
  private long minKey()
  {
    IntTree localIntTree = this.left;
    if (localIntTree.size == 0) {
      return this.key;
    }
    return localIntTree.minKey() + this.key;
  }
  
  private static <V> IntTree<V> rebalanced(long paramLong, V paramV, IntTree<V> paramIntTree1, IntTree<V> paramIntTree2)
  {
    int i = paramIntTree1.size;
    int j = paramIntTree2.size;
    if (i + j > 1)
    {
      IntTree localIntTree1;
      IntTree localIntTree2;
      long l1;
      Object localObject1;
      long l2;
      IntTree localIntTree3;
      Object localObject2;
      long l3;
      long l4;
      long l5;
      if (i >= j * 5)
      {
        localIntTree1 = paramIntTree1.left;
        localIntTree2 = paramIntTree1.right;
        if (localIntTree2.size < localIntTree1.size * 2)
        {
          l1 = paramIntTree1.key;
          localObject1 = paramIntTree1.value;
          l2 = paramIntTree1.key;
          return new IntTree(l1 + paramLong, localObject1, localIntTree1, new IntTree(-l2, paramV, localIntTree2.withKey(localIntTree2.key + l2), paramIntTree2));
        }
        localIntTree3 = localIntTree2.left;
        localObject1 = localIntTree2.right;
        l1 = localIntTree2.key;
        l2 = paramIntTree1.key;
        localObject2 = localIntTree2.value;
        l3 = localIntTree2.key;
        localIntTree1 = new IntTree(-l3, paramIntTree1.value, localIntTree1, localIntTree3.withKey(localIntTree3.key + l3));
        l4 = paramIntTree1.key;
        l3 = -l4;
        l5 = localIntTree2.key;
        return new IntTree(l1 + l2 + paramLong, localObject2, localIntTree1, new IntTree(l3 - l5, paramV, ((IntTree)localObject1).withKey(((IntTree)localObject1).key + l5 + l4), paramIntTree2));
      }
      if (j >= i * 5)
      {
        localIntTree1 = paramIntTree2.left;
        localIntTree2 = paramIntTree2.right;
        if (localIntTree1.size < localIntTree2.size * 2)
        {
          l1 = paramIntTree2.key;
          localObject1 = paramIntTree2.value;
          l2 = paramIntTree2.key;
          return new IntTree(l1 + paramLong, localObject1, new IntTree(-l2, paramV, paramIntTree1, localIntTree1.withKey(localIntTree1.key + l2)), localIntTree2);
        }
        localIntTree3 = localIntTree1.left;
        localObject1 = localIntTree1.right;
        l2 = localIntTree1.key;
        l1 = paramIntTree2.key;
        localObject2 = localIntTree1.value;
        l5 = paramIntTree2.key;
        l4 = -l5;
        l3 = localIntTree1.key;
        paramV = new IntTree(l4 - l3, paramV, paramIntTree1, localIntTree3.withKey(localIntTree3.key + l3 + l5));
        l3 = localIntTree1.key;
        return new IntTree(l2 + l1 + paramLong, localObject2, paramV, new IntTree(-l3, paramIntTree2.value, ((IntTree)localObject1).withKey(((IntTree)localObject1).key + l3), localIntTree2));
      }
    }
    return new IntTree(paramLong, paramV, paramIntTree1, paramIntTree2);
  }
  
  private IntTree<V> rebalanced(IntTree<V> paramIntTree1, IntTree<V> paramIntTree2)
  {
    if ((paramIntTree1 == this.left) && (paramIntTree2 == this.right)) {
      return this;
    }
    return rebalanced(this.key, this.value, paramIntTree1, paramIntTree2);
  }
  
  private IntTree<V> withKey(long paramLong)
  {
    if ((this.size != 0) && (paramLong != this.key)) {
      return new IntTree(paramLong, this.value, this.left, this.right);
    }
    return this;
  }
  
  V get(long paramLong)
  {
    if (this.size == 0) {
      return null;
    }
    long l = this.key;
    if (paramLong < l) {
      return this.left.get(paramLong - l);
    }
    if (paramLong > l) {
      return this.right.get(paramLong - l);
    }
    return this.value;
  }
  
  IntTree<V> minus(long paramLong)
  {
    if (this.size == 0) {
      return this;
    }
    long l = this.key;
    if (paramLong < l) {
      return rebalanced(this.left.minus(paramLong - l), this.right);
    }
    if (paramLong > l) {
      return rebalanced(this.left, this.right.minus(paramLong - l));
    }
    IntTree localIntTree1 = this.left;
    if (localIntTree1.size == 0)
    {
      localObject = this.right;
      return ((IntTree)localObject).withKey(((IntTree)localObject).key + l);
    }
    Object localObject = this.right;
    if (((IntTree)localObject).size == 0) {
      return localIntTree1.withKey(localIntTree1.key + l);
    }
    l = ((IntTree)localObject).minKey();
    paramLong = this.key;
    l += paramLong;
    localObject = this.right.get(l - paramLong);
    localIntTree1 = this.right.minus(l - this.key);
    IntTree localIntTree2 = localIntTree1.withKey(localIntTree1.key + this.key - l);
    localIntTree1 = this.left;
    return rebalanced(l, localObject, localIntTree1.withKey(localIntTree1.key + this.key - l), localIntTree2);
  }
  
  IntTree<V> plus(long paramLong, V paramV)
  {
    if (this.size == 0) {
      return new IntTree(paramLong, paramV, this, this);
    }
    long l = this.key;
    if (paramLong < l) {
      return rebalanced(this.left.plus(paramLong - l, paramV), this.right);
    }
    if (paramLong > l) {
      return rebalanced(this.left, this.right.plus(paramLong - l, paramV));
    }
    if (paramV == this.value) {
      return this;
    }
    return new IntTree(paramLong, paramV, this.left, this.right);
  }
}

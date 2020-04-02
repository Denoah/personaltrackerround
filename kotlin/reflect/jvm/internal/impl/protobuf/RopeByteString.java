package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

class RopeByteString
  extends ByteString
{
  private static final int[] minLengthByDepth;
  private int hash = 0;
  private final ByteString left;
  private final int leftLength;
  private final ByteString right;
  private final int totalLength;
  private final int treeDepth;
  
  static
  {
    ArrayList localArrayList = new ArrayList();
    int i = 1;
    int j = 1;
    for (;;)
    {
      int k = j;
      if (i <= 0) {
        break;
      }
      localArrayList.add(Integer.valueOf(i));
      j = i;
      i = k + i;
    }
    localArrayList.add(Integer.valueOf(Integer.MAX_VALUE));
    minLengthByDepth = new int[localArrayList.size()];
    for (i = 0;; i++)
    {
      int[] arrayOfInt = minLengthByDepth;
      if (i >= arrayOfInt.length) {
        break;
      }
      arrayOfInt[i] = ((Integer)localArrayList.get(i)).intValue();
    }
  }
  
  private RopeByteString(ByteString paramByteString1, ByteString paramByteString2)
  {
    this.left = paramByteString1;
    this.right = paramByteString2;
    int i = paramByteString1.size();
    this.leftLength = i;
    this.totalLength = (i + paramByteString2.size());
    this.treeDepth = (Math.max(paramByteString1.getTreeDepth(), paramByteString2.getTreeDepth()) + 1);
  }
  
  static ByteString concatenate(ByteString paramByteString1, ByteString paramByteString2)
  {
    RopeByteString localRopeByteString;
    if ((paramByteString1 instanceof RopeByteString)) {
      localRopeByteString = (RopeByteString)paramByteString1;
    } else {
      localRopeByteString = null;
    }
    if (paramByteString2.size() != 0)
    {
      if (paramByteString1.size() == 0) {}
      int i;
      for (;;)
      {
        return paramByteString2;
        i = paramByteString1.size() + paramByteString2.size();
        if (i < 128) {
          return concatenateBytes(paramByteString1, paramByteString2);
        }
        if ((localRopeByteString != null) && (localRopeByteString.right.size() + paramByteString2.size() < 128))
        {
          paramByteString1 = concatenateBytes(localRopeByteString.right, paramByteString2);
          paramByteString2 = new RopeByteString(localRopeByteString.left, paramByteString1);
        }
        else
        {
          if ((localRopeByteString == null) || (localRopeByteString.left.getTreeDepth() <= localRopeByteString.right.getTreeDepth()) || (localRopeByteString.getTreeDepth() <= paramByteString2.getTreeDepth())) {
            break;
          }
          paramByteString1 = new RopeByteString(localRopeByteString.right, paramByteString2);
          paramByteString2 = new RopeByteString(localRopeByteString.left, paramByteString1);
        }
      }
      int j = Math.max(paramByteString1.getTreeDepth(), paramByteString2.getTreeDepth());
      if (i >= minLengthByDepth[(j + 1)]) {
        paramByteString1 = new RopeByteString(paramByteString1, paramByteString2);
      } else {
        paramByteString1 = new Balancer(null).balance(paramByteString1, paramByteString2);
      }
    }
    return paramByteString1;
  }
  
  private static LiteralByteString concatenateBytes(ByteString paramByteString1, ByteString paramByteString2)
  {
    int i = paramByteString1.size();
    int j = paramByteString2.size();
    byte[] arrayOfByte = new byte[i + j];
    paramByteString1.copyTo(arrayOfByte, 0, 0, i);
    paramByteString2.copyTo(arrayOfByte, 0, i, j);
    return new LiteralByteString(arrayOfByte);
  }
  
  private boolean equalsFragments(ByteString paramByteString)
  {
    PieceIterator localPieceIterator1 = new PieceIterator(this, null);
    LiteralByteString localLiteralByteString = (LiteralByteString)localPieceIterator1.next();
    PieceIterator localPieceIterator2 = new PieceIterator(paramByteString, null);
    paramByteString = (LiteralByteString)localPieceIterator2.next();
    int i = 0;
    int j = i;
    int k = j;
    for (;;)
    {
      int m = localLiteralByteString.size() - i;
      int n = paramByteString.size() - j;
      int i1 = Math.min(m, n);
      boolean bool;
      if (i == 0) {
        bool = localLiteralByteString.equalsRange(paramByteString, j, i1);
      } else {
        bool = paramByteString.equalsRange(localLiteralByteString, i, i1);
      }
      if (!bool) {
        return false;
      }
      k += i1;
      int i2 = this.totalLength;
      if (k >= i2)
      {
        if (k == i2) {
          return true;
        }
        throw new IllegalStateException();
      }
      if (i1 == m)
      {
        localLiteralByteString = (LiteralByteString)localPieceIterator1.next();
        i = 0;
      }
      else
      {
        i += i1;
      }
      if (i1 == n)
      {
        paramByteString = (LiteralByteString)localPieceIterator2.next();
        j = 0;
      }
      else
      {
        j += i1;
      }
    }
  }
  
  protected void copyToInternal(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = this.leftLength;
    if (paramInt1 + paramInt3 <= i)
    {
      this.left.copyToInternal(paramArrayOfByte, paramInt1, paramInt2, paramInt3);
    }
    else if (paramInt1 >= i)
    {
      this.right.copyToInternal(paramArrayOfByte, paramInt1 - i, paramInt2, paramInt3);
    }
    else
    {
      i -= paramInt1;
      this.left.copyToInternal(paramArrayOfByte, paramInt1, paramInt2, i);
      this.right.copyToInternal(paramArrayOfByte, 0, paramInt2 + i, paramInt3 - i);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof ByteString)) {
      return false;
    }
    paramObject = (ByteString)paramObject;
    if (this.totalLength != paramObject.size()) {
      return false;
    }
    if (this.totalLength == 0) {
      return true;
    }
    if (this.hash != 0)
    {
      int i = paramObject.peekCachedHashCode();
      if ((i != 0) && (this.hash != i)) {
        return false;
      }
    }
    return equalsFragments(paramObject);
  }
  
  protected int getTreeDepth()
  {
    return this.treeDepth;
  }
  
  public int hashCode()
  {
    int i = this.hash;
    int j = i;
    if (i == 0)
    {
      j = this.totalLength;
      i = partialHash(j, 0, j);
      j = i;
      if (i == 0) {
        j = 1;
      }
      this.hash = j;
    }
    return j;
  }
  
  protected boolean isBalanced()
  {
    boolean bool;
    if (this.totalLength >= minLengthByDepth[this.treeDepth]) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isValidUtf8()
  {
    ByteString localByteString = this.left;
    int i = this.leftLength;
    boolean bool = false;
    i = localByteString.partialIsValidUtf8(0, 0, i);
    localByteString = this.right;
    if (localByteString.partialIsValidUtf8(i, 0, localByteString.size()) == 0) {
      bool = true;
    }
    return bool;
  }
  
  public ByteString.ByteIterator iterator()
  {
    return new RopeByteIterator(null);
  }
  
  public CodedInputStream newCodedInput()
  {
    return CodedInputStream.newInstance(new RopeInputStream());
  }
  
  protected int partialHash(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = this.leftLength;
    if (paramInt2 + paramInt3 <= i) {
      return this.left.partialHash(paramInt1, paramInt2, paramInt3);
    }
    if (paramInt2 >= i) {
      return this.right.partialHash(paramInt1, paramInt2 - i, paramInt3);
    }
    i -= paramInt2;
    paramInt1 = this.left.partialHash(paramInt1, paramInt2, i);
    return this.right.partialHash(paramInt1, 0, paramInt3 - i);
  }
  
  protected int partialIsValidUtf8(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = this.leftLength;
    if (paramInt2 + paramInt3 <= i) {
      return this.left.partialIsValidUtf8(paramInt1, paramInt2, paramInt3);
    }
    if (paramInt2 >= i) {
      return this.right.partialIsValidUtf8(paramInt1, paramInt2 - i, paramInt3);
    }
    i -= paramInt2;
    paramInt1 = this.left.partialIsValidUtf8(paramInt1, paramInt2, i);
    return this.right.partialIsValidUtf8(paramInt1, 0, paramInt3 - i);
  }
  
  protected int peekCachedHashCode()
  {
    return this.hash;
  }
  
  public int size()
  {
    return this.totalLength;
  }
  
  public String toString(String paramString)
    throws UnsupportedEncodingException
  {
    return new String(toByteArray(), paramString);
  }
  
  void writeToInternal(OutputStream paramOutputStream, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = this.leftLength;
    if (paramInt1 + paramInt2 <= i)
    {
      this.left.writeToInternal(paramOutputStream, paramInt1, paramInt2);
    }
    else if (paramInt1 >= i)
    {
      this.right.writeToInternal(paramOutputStream, paramInt1 - i, paramInt2);
    }
    else
    {
      i -= paramInt1;
      this.left.writeToInternal(paramOutputStream, paramInt1, i);
      this.right.writeToInternal(paramOutputStream, 0, paramInt2 - i);
    }
  }
  
  private static class Balancer
  {
    private final Stack<ByteString> prefixesStack = new Stack();
    
    private Balancer() {}
    
    private ByteString balance(ByteString paramByteString1, ByteString paramByteString2)
    {
      doBalance(paramByteString1);
      doBalance(paramByteString2);
      for (paramByteString1 = (ByteString)this.prefixesStack.pop(); !this.prefixesStack.isEmpty(); paramByteString1 = new RopeByteString((ByteString)this.prefixesStack.pop(), paramByteString1, null)) {}
      return paramByteString1;
    }
    
    private void doBalance(ByteString paramByteString)
    {
      if (paramByteString.isBalanced())
      {
        insert(paramByteString);
      }
      else
      {
        if (!(paramByteString instanceof RopeByteString)) {
          break label44;
        }
        paramByteString = (RopeByteString)paramByteString;
        doBalance(paramByteString.left);
        doBalance(paramByteString.right);
      }
      return;
      label44:
      String str = String.valueOf(String.valueOf(paramByteString.getClass()));
      paramByteString = new StringBuilder(str.length() + 49);
      paramByteString.append("Has a new type of ByteString been created? Found ");
      paramByteString.append(str);
      throw new IllegalArgumentException(paramByteString.toString());
    }
    
    private int getDepthBinForLength(int paramInt)
    {
      int i = Arrays.binarySearch(RopeByteString.minLengthByDepth, paramInt);
      paramInt = i;
      if (i < 0) {
        paramInt = -(i + 1) - 1;
      }
      return paramInt;
    }
    
    private void insert(ByteString paramByteString)
    {
      int i = getDepthBinForLength(paramByteString.size());
      int j = RopeByteString.minLengthByDepth[(i + 1)];
      if ((!this.prefixesStack.isEmpty()) && (((ByteString)this.prefixesStack.peek()).size() < j))
      {
        j = RopeByteString.minLengthByDepth[i];
        for (Object localObject = (ByteString)this.prefixesStack.pop(); (!this.prefixesStack.isEmpty()) && (((ByteString)this.prefixesStack.peek()).size() < j); localObject = new RopeByteString((ByteString)this.prefixesStack.pop(), (ByteString)localObject, null)) {}
        for (paramByteString = new RopeByteString((ByteString)localObject, paramByteString, null); !this.prefixesStack.isEmpty(); paramByteString = new RopeByteString((ByteString)this.prefixesStack.pop(), paramByteString, null))
        {
          j = getDepthBinForLength(paramByteString.size());
          j = RopeByteString.minLengthByDepth[(j + 1)];
          if (((ByteString)this.prefixesStack.peek()).size() >= j) {
            break;
          }
        }
        this.prefixesStack.push(paramByteString);
      }
      else
      {
        this.prefixesStack.push(paramByteString);
      }
    }
  }
  
  private static class PieceIterator
    implements Iterator<LiteralByteString>
  {
    private final Stack<RopeByteString> breadCrumbs = new Stack();
    private LiteralByteString next = getLeafByLeft(paramByteString);
    
    private PieceIterator(ByteString paramByteString) {}
    
    private LiteralByteString getLeafByLeft(ByteString paramByteString)
    {
      while ((paramByteString instanceof RopeByteString))
      {
        paramByteString = (RopeByteString)paramByteString;
        this.breadCrumbs.push(paramByteString);
        paramByteString = paramByteString.left;
      }
      return (LiteralByteString)paramByteString;
    }
    
    private LiteralByteString getNextNonEmptyLeaf()
    {
      LiteralByteString localLiteralByteString;
      do
      {
        if (this.breadCrumbs.isEmpty()) {
          return null;
        }
        localLiteralByteString = getLeafByLeft(((RopeByteString)this.breadCrumbs.pop()).right);
      } while (localLiteralByteString.isEmpty());
      return localLiteralByteString;
    }
    
    public boolean hasNext()
    {
      boolean bool;
      if (this.next != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public LiteralByteString next()
    {
      LiteralByteString localLiteralByteString = this.next;
      if (localLiteralByteString != null)
      {
        this.next = getNextNonEmptyLeaf();
        return localLiteralByteString;
      }
      throw new NoSuchElementException();
    }
    
    public void remove()
    {
      throw new UnsupportedOperationException();
    }
  }
  
  private class RopeByteIterator
    implements ByteString.ByteIterator
  {
    private ByteString.ByteIterator bytes;
    int bytesRemaining;
    private final RopeByteString.PieceIterator pieces;
    
    private RopeByteIterator()
    {
      RopeByteString.PieceIterator localPieceIterator = new RopeByteString.PieceIterator(RopeByteString.this, null);
      this.pieces = localPieceIterator;
      this.bytes = localPieceIterator.next().iterator();
      this.bytesRemaining = RopeByteString.this.size();
    }
    
    public boolean hasNext()
    {
      boolean bool;
      if (this.bytesRemaining > 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public Byte next()
    {
      return Byte.valueOf(nextByte());
    }
    
    public byte nextByte()
    {
      if (!this.bytes.hasNext()) {
        this.bytes = this.pieces.next().iterator();
      }
      this.bytesRemaining -= 1;
      return this.bytes.nextByte();
    }
    
    public void remove()
    {
      throw new UnsupportedOperationException();
    }
  }
  
  private class RopeInputStream
    extends InputStream
  {
    private LiteralByteString currentPiece;
    private int currentPieceIndex;
    private int currentPieceOffsetInRope;
    private int currentPieceSize;
    private int mark;
    private RopeByteString.PieceIterator pieceIterator;
    
    public RopeInputStream()
    {
      initialize();
    }
    
    private void advanceIfCurrentPieceFullyRead()
    {
      if (this.currentPiece != null)
      {
        int i = this.currentPieceIndex;
        int j = this.currentPieceSize;
        if (i == j)
        {
          this.currentPieceOffsetInRope += j;
          this.currentPieceIndex = 0;
          if (this.pieceIterator.hasNext())
          {
            LiteralByteString localLiteralByteString = this.pieceIterator.next();
            this.currentPiece = localLiteralByteString;
            this.currentPieceSize = localLiteralByteString.size();
          }
          else
          {
            this.currentPiece = null;
            this.currentPieceSize = 0;
          }
        }
      }
    }
    
    private void initialize()
    {
      Object localObject = new RopeByteString.PieceIterator(RopeByteString.this, null);
      this.pieceIterator = ((RopeByteString.PieceIterator)localObject);
      localObject = ((RopeByteString.PieceIterator)localObject).next();
      this.currentPiece = ((LiteralByteString)localObject);
      this.currentPieceSize = ((LiteralByteString)localObject).size();
      this.currentPieceIndex = 0;
      this.currentPieceOffsetInRope = 0;
    }
    
    private int readSkipInternal(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
      int i = paramInt2;
      for (int j = paramInt1; i > 0; j = paramInt1)
      {
        advanceIfCurrentPieceFullyRead();
        if (this.currentPiece == null)
        {
          if (i != paramInt2) {
            break;
          }
          return -1;
        }
        int k = Math.min(this.currentPieceSize - this.currentPieceIndex, i);
        paramInt1 = j;
        if (paramArrayOfByte != null)
        {
          this.currentPiece.copyTo(paramArrayOfByte, this.currentPieceIndex, j, k);
          paramInt1 = j + k;
        }
        this.currentPieceIndex += k;
        i -= k;
      }
      return paramInt2 - i;
    }
    
    public int available()
      throws IOException
    {
      int i = this.currentPieceOffsetInRope;
      int j = this.currentPieceIndex;
      return RopeByteString.this.size() - (i + j);
    }
    
    public void mark(int paramInt)
    {
      this.mark = (this.currentPieceOffsetInRope + this.currentPieceIndex);
    }
    
    public boolean markSupported()
    {
      return true;
    }
    
    public int read()
      throws IOException
    {
      advanceIfCurrentPieceFullyRead();
      LiteralByteString localLiteralByteString = this.currentPiece;
      if (localLiteralByteString == null) {
        return -1;
      }
      int i = this.currentPieceIndex;
      this.currentPieceIndex = (i + 1);
      return localLiteralByteString.byteAt(i) & 0xFF;
    }
    
    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
      if (paramArrayOfByte != null)
      {
        if ((paramInt1 >= 0) && (paramInt2 >= 0) && (paramInt2 <= paramArrayOfByte.length - paramInt1)) {
          return readSkipInternal(paramArrayOfByte, paramInt1, paramInt2);
        }
        throw new IndexOutOfBoundsException();
      }
      throw null;
    }
    
    public void reset()
    {
      try
      {
        initialize();
        readSkipInternal(null, 0, this.mark);
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
    
    public long skip(long paramLong)
    {
      if (paramLong >= 0L)
      {
        long l = paramLong;
        if (paramLong > 2147483647L) {
          l = 2147483647L;
        }
        return readSkipInternal(null, 0, (int)l);
      }
      throw new IndexOutOfBoundsException();
    }
  }
}

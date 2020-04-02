package io.reactivex.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class CompositeException
  extends RuntimeException
{
  private static final long serialVersionUID = 3026362227162912146L;
  private Throwable cause;
  private final List<Throwable> exceptions;
  private final String message;
  
  public CompositeException(Iterable<? extends Throwable> paramIterable)
  {
    LinkedHashSet localLinkedHashSet = new LinkedHashSet();
    ArrayList localArrayList = new ArrayList();
    if (paramIterable != null)
    {
      paramIterable = paramIterable.iterator();
      while (paramIterable.hasNext())
      {
        Throwable localThrowable = (Throwable)paramIterable.next();
        if ((localThrowable instanceof CompositeException)) {
          localLinkedHashSet.addAll(((CompositeException)localThrowable).getExceptions());
        } else if (localThrowable != null) {
          localLinkedHashSet.add(localThrowable);
        } else {
          localLinkedHashSet.add(new NullPointerException("Throwable was null!"));
        }
      }
    }
    localLinkedHashSet.add(new NullPointerException("errors was null"));
    if (!localLinkedHashSet.isEmpty())
    {
      localArrayList.addAll(localLinkedHashSet);
      this.exceptions = Collections.unmodifiableList(localArrayList);
      paramIterable = new StringBuilder();
      paramIterable.append(this.exceptions.size());
      paramIterable.append(" exceptions occurred. ");
      this.message = paramIterable.toString();
      return;
    }
    throw new IllegalArgumentException("errors is empty");
  }
  
  public CompositeException(Throwable... paramVarArgs)
  {
    this(paramVarArgs);
  }
  
  private void appendStackTrace(StringBuilder paramStringBuilder, Throwable paramThrowable, String paramString)
  {
    paramStringBuilder.append(paramString);
    paramStringBuilder.append(paramThrowable);
    paramStringBuilder.append('\n');
    for (paramString : paramThrowable.getStackTrace())
    {
      paramStringBuilder.append("\t\tat ");
      paramStringBuilder.append(paramString);
      paramStringBuilder.append('\n');
    }
    if (paramThrowable.getCause() != null)
    {
      paramStringBuilder.append("\tCaused by: ");
      appendStackTrace(paramStringBuilder, paramThrowable.getCause(), "");
    }
  }
  
  private List<Throwable> getListOfCauses(Throwable paramThrowable)
  {
    ArrayList localArrayList = new ArrayList();
    Throwable localThrowable1 = paramThrowable.getCause();
    if (localThrowable1 != null)
    {
      Throwable localThrowable2 = localThrowable1;
      if (localThrowable1 != paramThrowable) {
        for (;;)
        {
          localArrayList.add(localThrowable2);
          paramThrowable = localThrowable2.getCause();
          if ((paramThrowable == null) || (paramThrowable == localThrowable2)) {
            break;
          }
          localThrowable2 = paramThrowable;
        }
      }
    }
    return localArrayList;
  }
  
  private void printStackTrace(PrintStreamOrWriter paramPrintStreamOrWriter)
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    localStringBuilder.append(this);
    localStringBuilder.append('\n');
    Object localObject2;
    for (localObject2 : getStackTrace())
    {
      localStringBuilder.append("\tat ");
      localStringBuilder.append(localObject2);
      localStringBuilder.append('\n');
    }
    ??? = this.exceptions.iterator();
    for (??? = 1; ((Iterator)???).hasNext(); ???++)
    {
      localObject2 = (Throwable)((Iterator)???).next();
      localStringBuilder.append("  ComposedException ");
      localStringBuilder.append(???);
      localStringBuilder.append(" :\n");
      appendStackTrace(localStringBuilder, (Throwable)localObject2, "\t");
    }
    paramPrintStreamOrWriter.println(localStringBuilder.toString());
  }
  
  /* Error */
  public Throwable getCause()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 187	io/reactivex/exceptions/CompositeException:cause	Ljava/lang/Throwable;
    //   6: ifnonnull +171 -> 177
    //   9: new 6	io/reactivex/exceptions/CompositeException$CompositeExceptionCausalChain
    //   12: astore_1
    //   13: aload_1
    //   14: invokespecial 188	io/reactivex/exceptions/CompositeException$CompositeExceptionCausalChain:<init>	()V
    //   17: new 190	java/util/HashSet
    //   20: astore_2
    //   21: aload_2
    //   22: invokespecial 191	java/util/HashSet:<init>	()V
    //   25: aload_0
    //   26: getfield 93	io/reactivex/exceptions/CompositeException:exceptions	Ljava/util/List;
    //   29: invokeinterface 175 1 0
    //   34: astore_3
    //   35: aload_1
    //   36: astore 4
    //   38: aload_3
    //   39: invokeinterface 50 1 0
    //   44: ifeq +128 -> 172
    //   47: aload_3
    //   48: invokeinterface 54 1 0
    //   53: checkcast 56	java/lang/Throwable
    //   56: astore 5
    //   58: aload_2
    //   59: aload 5
    //   61: invokeinterface 194 2 0
    //   66: ifeq +6 -> 72
    //   69: goto -31 -> 38
    //   72: aload_2
    //   73: aload 5
    //   75: invokeinterface 70 2 0
    //   80: pop
    //   81: aload_0
    //   82: aload 5
    //   84: invokespecial 196	io/reactivex/exceptions/CompositeException:getListOfCauses	(Ljava/lang/Throwable;)Ljava/util/List;
    //   87: invokeinterface 175 1 0
    //   92: astore 6
    //   94: aload 6
    //   96: invokeinterface 50 1 0
    //   101: ifeq +52 -> 153
    //   104: aload 6
    //   106: invokeinterface 54 1 0
    //   111: checkcast 56	java/lang/Throwable
    //   114: astore 7
    //   116: aload_2
    //   117: aload 7
    //   119: invokeinterface 194 2 0
    //   124: ifeq +17 -> 141
    //   127: new 4	java/lang/RuntimeException
    //   130: dup
    //   131: ldc -58
    //   133: invokespecial 199	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   136: astore 5
    //   138: goto -44 -> 94
    //   141: aload_2
    //   142: aload 7
    //   144: invokeinterface 70 2 0
    //   149: pop
    //   150: goto -56 -> 94
    //   153: aload 4
    //   155: aload 5
    //   157: invokevirtual 203	java/lang/Throwable:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   160: pop
    //   161: aload_0
    //   162: aload 4
    //   164: invokevirtual 206	io/reactivex/exceptions/CompositeException:getRootCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   167: astore 4
    //   169: goto -131 -> 38
    //   172: aload_0
    //   173: aload_1
    //   174: putfield 187	io/reactivex/exceptions/CompositeException:cause	Ljava/lang/Throwable;
    //   177: aload_0
    //   178: getfield 187	io/reactivex/exceptions/CompositeException:cause	Ljava/lang/Throwable;
    //   181: astore 4
    //   183: aload_0
    //   184: monitorexit
    //   185: aload 4
    //   187: areturn
    //   188: astore 4
    //   190: aload_0
    //   191: monitorexit
    //   192: aload 4
    //   194: athrow
    //   195: astore 5
    //   197: goto -36 -> 161
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	200	0	this	CompositeException
    //   12	162	1	localCompositeExceptionCausalChain	CompositeExceptionCausalChain
    //   20	122	2	localHashSet	java.util.HashSet
    //   34	14	3	localIterator1	Iterator
    //   36	150	4	localObject1	Object
    //   188	5	4	localObject2	Object
    //   56	100	5	localObject3	Object
    //   195	1	5	localObject4	Object
    //   92	13	6	localIterator2	Iterator
    //   114	29	7	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   2	35	188	finally
    //   38	69	188	finally
    //   72	94	188	finally
    //   94	138	188	finally
    //   141	150	188	finally
    //   161	169	188	finally
    //   172	177	188	finally
    //   177	183	188	finally
    //   153	161	195	finally
  }
  
  public List<Throwable> getExceptions()
  {
    return this.exceptions;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  Throwable getRootCause(Throwable paramThrowable)
  {
    Throwable localThrowable1 = paramThrowable.getCause();
    if (localThrowable1 != null)
    {
      Throwable localThrowable2 = localThrowable1;
      if (paramThrowable != localThrowable1)
      {
        for (;;)
        {
          paramThrowable = localThrowable2.getCause();
          if ((paramThrowable == null) || (paramThrowable == localThrowable2)) {
            break;
          }
          localThrowable2 = paramThrowable;
        }
        return localThrowable2;
      }
    }
    return paramThrowable;
  }
  
  public void printStackTrace()
  {
    printStackTrace(System.err);
  }
  
  public void printStackTrace(PrintStream paramPrintStream)
  {
    printStackTrace(new WrappedPrintStream(paramPrintStream));
  }
  
  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    printStackTrace(new WrappedPrintWriter(paramPrintWriter));
  }
  
  public int size()
  {
    return this.exceptions.size();
  }
  
  static final class CompositeExceptionCausalChain
    extends RuntimeException
  {
    static final String MESSAGE = "Chain of Causes for CompositeException In Order Received =>";
    private static final long serialVersionUID = 3875212506787802066L;
    
    CompositeExceptionCausalChain() {}
    
    public String getMessage()
    {
      return "Chain of Causes for CompositeException In Order Received =>";
    }
  }
  
  static abstract class PrintStreamOrWriter
  {
    PrintStreamOrWriter() {}
    
    abstract void println(Object paramObject);
  }
  
  static final class WrappedPrintStream
    extends CompositeException.PrintStreamOrWriter
  {
    private final PrintStream printStream;
    
    WrappedPrintStream(PrintStream paramPrintStream)
    {
      this.printStream = paramPrintStream;
    }
    
    void println(Object paramObject)
    {
      this.printStream.println(paramObject);
    }
  }
  
  static final class WrappedPrintWriter
    extends CompositeException.PrintStreamOrWriter
  {
    private final PrintWriter printWriter;
    
    WrappedPrintWriter(PrintWriter paramPrintWriter)
    {
      this.printWriter = paramPrintWriter;
    }
    
    void println(Object paramObject)
    {
      this.printWriter.println(paramObject);
    }
  }
}

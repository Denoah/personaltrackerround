package androidx.room;

import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class EntityInsertionAdapter<T>
  extends SharedSQLiteStatement
{
  public EntityInsertionAdapter(RoomDatabase paramRoomDatabase)
  {
    super(paramRoomDatabase);
  }
  
  protected abstract void bind(SupportSQLiteStatement paramSupportSQLiteStatement, T paramT);
  
  public final void insert(Iterable<? extends T> paramIterable)
  {
    SupportSQLiteStatement localSupportSQLiteStatement = acquire();
    try
    {
      paramIterable = paramIterable.iterator();
      while (paramIterable.hasNext())
      {
        bind(localSupportSQLiteStatement, paramIterable.next());
        localSupportSQLiteStatement.executeInsert();
      }
      return;
    }
    finally
    {
      release(localSupportSQLiteStatement);
    }
  }
  
  public final void insert(T paramT)
  {
    SupportSQLiteStatement localSupportSQLiteStatement = acquire();
    try
    {
      bind(localSupportSQLiteStatement, paramT);
      localSupportSQLiteStatement.executeInsert();
      return;
    }
    finally
    {
      release(localSupportSQLiteStatement);
    }
  }
  
  public final void insert(T[] paramArrayOfT)
  {
    SupportSQLiteStatement localSupportSQLiteStatement = acquire();
    try
    {
      int i = paramArrayOfT.length;
      for (int j = 0; j < i; j++)
      {
        bind(localSupportSQLiteStatement, paramArrayOfT[j]);
        localSupportSQLiteStatement.executeInsert();
      }
      return;
    }
    finally
    {
      release(localSupportSQLiteStatement);
    }
  }
  
  public final long insertAndReturnId(T paramT)
  {
    SupportSQLiteStatement localSupportSQLiteStatement = acquire();
    try
    {
      bind(localSupportSQLiteStatement, paramT);
      long l = localSupportSQLiteStatement.executeInsert();
      return l;
    }
    finally
    {
      release(localSupportSQLiteStatement);
    }
  }
  
  public final long[] insertAndReturnIdsArray(Collection<? extends T> paramCollection)
  {
    SupportSQLiteStatement localSupportSQLiteStatement = acquire();
    try
    {
      long[] arrayOfLong = new long[paramCollection.size()];
      int i = 0;
      paramCollection = paramCollection.iterator();
      while (paramCollection.hasNext())
      {
        bind(localSupportSQLiteStatement, paramCollection.next());
        arrayOfLong[i] = localSupportSQLiteStatement.executeInsert();
        i++;
      }
      return arrayOfLong;
    }
    finally
    {
      release(localSupportSQLiteStatement);
    }
  }
  
  public final long[] insertAndReturnIdsArray(T[] paramArrayOfT)
  {
    SupportSQLiteStatement localSupportSQLiteStatement = acquire();
    try
    {
      long[] arrayOfLong = new long[paramArrayOfT.length];
      int i = paramArrayOfT.length;
      int j = 0;
      int k = 0;
      while (j < i)
      {
        bind(localSupportSQLiteStatement, paramArrayOfT[j]);
        arrayOfLong[k] = localSupportSQLiteStatement.executeInsert();
        k++;
        j++;
      }
      return arrayOfLong;
    }
    finally
    {
      release(localSupportSQLiteStatement);
    }
  }
  
  public final Long[] insertAndReturnIdsArrayBox(Collection<? extends T> paramCollection)
  {
    SupportSQLiteStatement localSupportSQLiteStatement = acquire();
    try
    {
      Long[] arrayOfLong = new Long[paramCollection.size()];
      int i = 0;
      paramCollection = paramCollection.iterator();
      while (paramCollection.hasNext())
      {
        bind(localSupportSQLiteStatement, paramCollection.next());
        arrayOfLong[i] = Long.valueOf(localSupportSQLiteStatement.executeInsert());
        i++;
      }
      return arrayOfLong;
    }
    finally
    {
      release(localSupportSQLiteStatement);
    }
  }
  
  public final Long[] insertAndReturnIdsArrayBox(T[] paramArrayOfT)
  {
    SupportSQLiteStatement localSupportSQLiteStatement = acquire();
    try
    {
      Long[] arrayOfLong = new Long[paramArrayOfT.length];
      int i = paramArrayOfT.length;
      int j = 0;
      int k = 0;
      while (j < i)
      {
        bind(localSupportSQLiteStatement, paramArrayOfT[j]);
        arrayOfLong[k] = Long.valueOf(localSupportSQLiteStatement.executeInsert());
        k++;
        j++;
      }
      return arrayOfLong;
    }
    finally
    {
      release(localSupportSQLiteStatement);
    }
  }
  
  public final List<Long> insertAndReturnIdsList(Collection<? extends T> paramCollection)
  {
    SupportSQLiteStatement localSupportSQLiteStatement = acquire();
    try
    {
      ArrayList localArrayList = new java/util/ArrayList;
      localArrayList.<init>(paramCollection.size());
      int i = 0;
      paramCollection = paramCollection.iterator();
      while (paramCollection.hasNext())
      {
        bind(localSupportSQLiteStatement, paramCollection.next());
        localArrayList.add(i, Long.valueOf(localSupportSQLiteStatement.executeInsert()));
        i++;
      }
      return localArrayList;
    }
    finally
    {
      release(localSupportSQLiteStatement);
    }
  }
  
  public final List<Long> insertAndReturnIdsList(T[] paramArrayOfT)
  {
    SupportSQLiteStatement localSupportSQLiteStatement = acquire();
    try
    {
      ArrayList localArrayList = new java/util/ArrayList;
      localArrayList.<init>(paramArrayOfT.length);
      int i = paramArrayOfT.length;
      int j = 0;
      int k = 0;
      while (j < i)
      {
        bind(localSupportSQLiteStatement, paramArrayOfT[j]);
        localArrayList.add(k, Long.valueOf(localSupportSQLiteStatement.executeInsert()));
        k++;
        j++;
      }
      return localArrayList;
    }
    finally
    {
      release(localSupportSQLiteStatement);
    }
  }
}

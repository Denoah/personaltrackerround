package androidx.room;

import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.Iterator;

public abstract class EntityDeletionOrUpdateAdapter<T>
  extends SharedSQLiteStatement
{
  public EntityDeletionOrUpdateAdapter(RoomDatabase paramRoomDatabase)
  {
    super(paramRoomDatabase);
  }
  
  protected abstract void bind(SupportSQLiteStatement paramSupportSQLiteStatement, T paramT);
  
  protected abstract String createQuery();
  
  public final int handle(T paramT)
  {
    SupportSQLiteStatement localSupportSQLiteStatement = acquire();
    try
    {
      bind(localSupportSQLiteStatement, paramT);
      int i = localSupportSQLiteStatement.executeUpdateDelete();
      return i;
    }
    finally
    {
      release(localSupportSQLiteStatement);
    }
  }
  
  public final int handleMultiple(Iterable<? extends T> paramIterable)
  {
    SupportSQLiteStatement localSupportSQLiteStatement = acquire();
    int i = 0;
    try
    {
      paramIterable = paramIterable.iterator();
      while (paramIterable.hasNext())
      {
        bind(localSupportSQLiteStatement, paramIterable.next());
        int j = localSupportSQLiteStatement.executeUpdateDelete();
        i += j;
      }
      return i;
    }
    finally
    {
      release(localSupportSQLiteStatement);
    }
  }
  
  public final int handleMultiple(T[] paramArrayOfT)
  {
    SupportSQLiteStatement localSupportSQLiteStatement = acquire();
    try
    {
      int i = paramArrayOfT.length;
      int j = 0;
      int k = 0;
      while (j < i)
      {
        bind(localSupportSQLiteStatement, paramArrayOfT[j]);
        int m = localSupportSQLiteStatement.executeUpdateDelete();
        k += m;
        j++;
      }
      return k;
    }
    finally
    {
      release(localSupportSQLiteStatement);
    }
  }
}

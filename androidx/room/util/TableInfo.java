package androidx.room.util;

import android.database.Cursor;
import android.os.Build.VERSION;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TableInfo
{
  public static final int CREATED_FROM_DATABASE = 2;
  public static final int CREATED_FROM_ENTITY = 1;
  public static final int CREATED_FROM_UNKNOWN = 0;
  public final Map<String, Column> columns;
  public final Set<ForeignKey> foreignKeys;
  public final Set<Index> indices;
  public final String name;
  
  public TableInfo(String paramString, Map<String, Column> paramMap, Set<ForeignKey> paramSet)
  {
    this(paramString, paramMap, paramSet, Collections.emptySet());
  }
  
  public TableInfo(String paramString, Map<String, Column> paramMap, Set<ForeignKey> paramSet, Set<Index> paramSet1)
  {
    this.name = paramString;
    this.columns = Collections.unmodifiableMap(paramMap);
    this.foreignKeys = Collections.unmodifiableSet(paramSet);
    if (paramSet1 == null) {
      paramString = null;
    } else {
      paramString = Collections.unmodifiableSet(paramSet1);
    }
    this.indices = paramString;
  }
  
  public static TableInfo read(SupportSQLiteDatabase paramSupportSQLiteDatabase, String paramString)
  {
    return new TableInfo(paramString, readColumns(paramSupportSQLiteDatabase, paramString), readForeignKeys(paramSupportSQLiteDatabase, paramString), readIndices(paramSupportSQLiteDatabase, paramString));
  }
  
  private static Map<String, Column> readColumns(SupportSQLiteDatabase paramSupportSQLiteDatabase, String paramString)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("PRAGMA table_info(`");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append("`)");
    paramSupportSQLiteDatabase = paramSupportSQLiteDatabase.query(((StringBuilder)localObject).toString());
    paramString = new HashMap();
    try
    {
      if (paramSupportSQLiteDatabase.getColumnCount() > 0)
      {
        int i = paramSupportSQLiteDatabase.getColumnIndex("name");
        int j = paramSupportSQLiteDatabase.getColumnIndex("type");
        int k = paramSupportSQLiteDatabase.getColumnIndex("notnull");
        int m = paramSupportSQLiteDatabase.getColumnIndex("pk");
        int n = paramSupportSQLiteDatabase.getColumnIndex("dflt_value");
        while (paramSupportSQLiteDatabase.moveToNext())
        {
          String str1 = paramSupportSQLiteDatabase.getString(i);
          localObject = paramSupportSQLiteDatabase.getString(j);
          boolean bool;
          if (paramSupportSQLiteDatabase.getInt(k) != 0) {
            bool = true;
          } else {
            bool = false;
          }
          int i1 = paramSupportSQLiteDatabase.getInt(m);
          String str2 = paramSupportSQLiteDatabase.getString(n);
          Column localColumn = new androidx/room/util/TableInfo$Column;
          localColumn.<init>(str1, (String)localObject, bool, i1, str2, 2);
          paramString.put(str1, localColumn);
        }
      }
      return paramString;
    }
    finally
    {
      paramSupportSQLiteDatabase.close();
    }
  }
  
  private static List<ForeignKeyWithSequence> readForeignKeyFieldMappings(Cursor paramCursor)
  {
    int i = paramCursor.getColumnIndex("id");
    int j = paramCursor.getColumnIndex("seq");
    int k = paramCursor.getColumnIndex("from");
    int m = paramCursor.getColumnIndex("to");
    int n = paramCursor.getCount();
    ArrayList localArrayList = new ArrayList();
    for (int i1 = 0; i1 < n; i1++)
    {
      paramCursor.moveToPosition(i1);
      localArrayList.add(new ForeignKeyWithSequence(paramCursor.getInt(i), paramCursor.getInt(j), paramCursor.getString(k), paramCursor.getString(m)));
    }
    Collections.sort(localArrayList);
    return localArrayList;
  }
  
  private static Set<ForeignKey> readForeignKeys(SupportSQLiteDatabase paramSupportSQLiteDatabase, String paramString)
  {
    HashSet localHashSet = new HashSet();
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("PRAGMA foreign_key_list(`");
    ((StringBuilder)localObject1).append(paramString);
    ((StringBuilder)localObject1).append("`)");
    paramSupportSQLiteDatabase = paramSupportSQLiteDatabase.query(((StringBuilder)localObject1).toString());
    try
    {
      int i = paramSupportSQLiteDatabase.getColumnIndex("id");
      int j = paramSupportSQLiteDatabase.getColumnIndex("seq");
      int k = paramSupportSQLiteDatabase.getColumnIndex("table");
      int m = paramSupportSQLiteDatabase.getColumnIndex("on_delete");
      int n = paramSupportSQLiteDatabase.getColumnIndex("on_update");
      paramString = readForeignKeyFieldMappings(paramSupportSQLiteDatabase);
      int i1 = paramSupportSQLiteDatabase.getCount();
      for (int i2 = 0; i2 < i1; i2++)
      {
        paramSupportSQLiteDatabase.moveToPosition(i2);
        if (paramSupportSQLiteDatabase.getInt(j) == 0)
        {
          int i3 = paramSupportSQLiteDatabase.getInt(i);
          ArrayList localArrayList = new java/util/ArrayList;
          localArrayList.<init>();
          localObject1 = new java/util/ArrayList;
          ((ArrayList)localObject1).<init>();
          Object localObject2 = paramString.iterator();
          while (((Iterator)localObject2).hasNext())
          {
            ForeignKeyWithSequence localForeignKeyWithSequence = (ForeignKeyWithSequence)((Iterator)localObject2).next();
            if (localForeignKeyWithSequence.mId == i3)
            {
              localArrayList.add(localForeignKeyWithSequence.mFrom);
              ((List)localObject1).add(localForeignKeyWithSequence.mTo);
            }
          }
          localObject2 = new androidx/room/util/TableInfo$ForeignKey;
          ((ForeignKey)localObject2).<init>(paramSupportSQLiteDatabase.getString(k), paramSupportSQLiteDatabase.getString(m), paramSupportSQLiteDatabase.getString(n), localArrayList, (List)localObject1);
          localHashSet.add(localObject2);
        }
      }
      return localHashSet;
    }
    finally
    {
      paramSupportSQLiteDatabase.close();
    }
  }
  
  private static Index readIndex(SupportSQLiteDatabase paramSupportSQLiteDatabase, String paramString, boolean paramBoolean)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("PRAGMA index_xinfo(`");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append("`)");
    paramSupportSQLiteDatabase = paramSupportSQLiteDatabase.query(((StringBuilder)localObject).toString());
    try
    {
      int i = paramSupportSQLiteDatabase.getColumnIndex("seqno");
      int j = paramSupportSQLiteDatabase.getColumnIndex("cid");
      int k = paramSupportSQLiteDatabase.getColumnIndex("name");
      if ((i != -1) && (j != -1) && (k != -1))
      {
        localObject = new java/util/TreeMap;
        ((TreeMap)localObject).<init>();
        while (paramSupportSQLiteDatabase.moveToNext()) {
          if (paramSupportSQLiteDatabase.getInt(j) >= 0) {
            ((TreeMap)localObject).put(Integer.valueOf(paramSupportSQLiteDatabase.getInt(i)), paramSupportSQLiteDatabase.getString(k));
          }
        }
        ArrayList localArrayList = new java/util/ArrayList;
        localArrayList.<init>(((TreeMap)localObject).size());
        localArrayList.addAll(((TreeMap)localObject).values());
        paramString = new Index(paramString, paramBoolean, localArrayList);
        return paramString;
      }
      return null;
    }
    finally
    {
      paramSupportSQLiteDatabase.close();
    }
  }
  
  private static Set<Index> readIndices(SupportSQLiteDatabase paramSupportSQLiteDatabase, String paramString)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("PRAGMA index_list(`");
    ((StringBuilder)localObject1).append(paramString);
    ((StringBuilder)localObject1).append("`)");
    paramString = paramSupportSQLiteDatabase.query(((StringBuilder)localObject1).toString());
    try
    {
      int i = paramString.getColumnIndex("name");
      int j = paramString.getColumnIndex("origin");
      int k = paramString.getColumnIndex("unique");
      if ((i != -1) && (j != -1) && (k != -1))
      {
        localObject1 = new java/util/HashSet;
        ((HashSet)localObject1).<init>();
        while (paramString.moveToNext()) {
          if ("c".equals(paramString.getString(j)))
          {
            Object localObject2 = paramString.getString(i);
            int m = paramString.getInt(k);
            boolean bool = true;
            if (m != 1) {
              bool = false;
            }
            localObject2 = readIndex(paramSupportSQLiteDatabase, (String)localObject2, bool);
            if (localObject2 == null) {
              return null;
            }
            ((HashSet)localObject1).add(localObject2);
          }
        }
        return localObject1;
      }
      return null;
    }
    finally
    {
      paramString.close();
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if ((paramObject != null) && (getClass() == paramObject.getClass()))
    {
      paramObject = (TableInfo)paramObject;
      Object localObject = this.name;
      if (localObject != null ? !((String)localObject).equals(paramObject.name) : paramObject.name != null) {
        return false;
      }
      localObject = this.columns;
      if (localObject != null ? !((Map)localObject).equals(paramObject.columns) : paramObject.columns != null) {
        return false;
      }
      localObject = this.foreignKeys;
      if (localObject != null ? !((Set)localObject).equals(paramObject.foreignKeys) : paramObject.foreignKeys != null) {
        return false;
      }
      localObject = this.indices;
      if (localObject != null)
      {
        paramObject = paramObject.indices;
        if (paramObject != null) {
          return ((Set)localObject).equals(paramObject);
        }
      }
      return true;
    }
    return false;
  }
  
  public int hashCode()
  {
    Object localObject = this.name;
    int i = 0;
    int j;
    if (localObject != null) {
      j = ((String)localObject).hashCode();
    } else {
      j = 0;
    }
    localObject = this.columns;
    int k;
    if (localObject != null) {
      k = ((Map)localObject).hashCode();
    } else {
      k = 0;
    }
    localObject = this.foreignKeys;
    if (localObject != null) {
      i = ((Set)localObject).hashCode();
    }
    return (j * 31 + k) * 31 + i;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("TableInfo{name='");
    localStringBuilder.append(this.name);
    localStringBuilder.append('\'');
    localStringBuilder.append(", columns=");
    localStringBuilder.append(this.columns);
    localStringBuilder.append(", foreignKeys=");
    localStringBuilder.append(this.foreignKeys);
    localStringBuilder.append(", indices=");
    localStringBuilder.append(this.indices);
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
  
  public static class Column
  {
    public final int affinity;
    public final String defaultValue;
    private final int mCreatedFrom;
    public final String name;
    public final boolean notNull;
    public final int primaryKeyPosition;
    public final String type;
    
    @Deprecated
    public Column(String paramString1, String paramString2, boolean paramBoolean, int paramInt)
    {
      this(paramString1, paramString2, paramBoolean, paramInt, null, 0);
    }
    
    public Column(String paramString1, String paramString2, boolean paramBoolean, int paramInt1, String paramString3, int paramInt2)
    {
      this.name = paramString1;
      this.type = paramString2;
      this.notNull = paramBoolean;
      this.primaryKeyPosition = paramInt1;
      this.affinity = findAffinity(paramString2);
      this.defaultValue = paramString3;
      this.mCreatedFrom = paramInt2;
    }
    
    private static int findAffinity(String paramString)
    {
      if (paramString == null) {
        return 5;
      }
      paramString = paramString.toUpperCase(Locale.US);
      if (paramString.contains("INT")) {
        return 3;
      }
      if ((!paramString.contains("CHAR")) && (!paramString.contains("CLOB")) && (!paramString.contains("TEXT")))
      {
        if (paramString.contains("BLOB")) {
          return 5;
        }
        if ((!paramString.contains("REAL")) && (!paramString.contains("FLOA")) && (!paramString.contains("DOUB"))) {
          return 1;
        }
        return 4;
      }
      return 2;
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool = true;
      if (this == paramObject) {
        return true;
      }
      if ((paramObject != null) && (getClass() == paramObject.getClass()))
      {
        paramObject = (Column)paramObject;
        if (Build.VERSION.SDK_INT >= 20)
        {
          if (this.primaryKeyPosition != paramObject.primaryKeyPosition) {
            return false;
          }
        }
        else if (isPrimaryKey() != paramObject.isPrimaryKey()) {
          return false;
        }
        if (!this.name.equals(paramObject.name)) {
          return false;
        }
        if (this.notNull != paramObject.notNull) {
          return false;
        }
        String str;
        if ((this.mCreatedFrom == 1) && (paramObject.mCreatedFrom == 2))
        {
          str = this.defaultValue;
          if ((str != null) && (!str.equals(paramObject.defaultValue))) {
            return false;
          }
        }
        if ((this.mCreatedFrom == 2) && (paramObject.mCreatedFrom == 1))
        {
          str = paramObject.defaultValue;
          if ((str != null) && (!str.equals(this.defaultValue))) {
            return false;
          }
        }
        int i = this.mCreatedFrom;
        if ((i != 0) && (i == paramObject.mCreatedFrom))
        {
          str = this.defaultValue;
          if (str != null ? !str.equals(paramObject.defaultValue) : paramObject.defaultValue != null) {
            return false;
          }
        }
        if (this.affinity != paramObject.affinity) {
          bool = false;
        }
        return bool;
      }
      return false;
    }
    
    public int hashCode()
    {
      int i = this.name.hashCode();
      int j = this.affinity;
      int k;
      if (this.notNull) {
        k = 1231;
      } else {
        k = 1237;
      }
      return ((i * 31 + j) * 31 + k) * 31 + this.primaryKeyPosition;
    }
    
    public boolean isPrimaryKey()
    {
      boolean bool;
      if (this.primaryKeyPosition > 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Column{name='");
      localStringBuilder.append(this.name);
      localStringBuilder.append('\'');
      localStringBuilder.append(", type='");
      localStringBuilder.append(this.type);
      localStringBuilder.append('\'');
      localStringBuilder.append(", affinity='");
      localStringBuilder.append(this.affinity);
      localStringBuilder.append('\'');
      localStringBuilder.append(", notNull=");
      localStringBuilder.append(this.notNull);
      localStringBuilder.append(", primaryKeyPosition=");
      localStringBuilder.append(this.primaryKeyPosition);
      localStringBuilder.append(", defaultValue='");
      localStringBuilder.append(this.defaultValue);
      localStringBuilder.append('\'');
      localStringBuilder.append('}');
      return localStringBuilder.toString();
    }
  }
  
  public static class ForeignKey
  {
    public final List<String> columnNames;
    public final String onDelete;
    public final String onUpdate;
    public final List<String> referenceColumnNames;
    public final String referenceTable;
    
    public ForeignKey(String paramString1, String paramString2, String paramString3, List<String> paramList1, List<String> paramList2)
    {
      this.referenceTable = paramString1;
      this.onDelete = paramString2;
      this.onUpdate = paramString3;
      this.columnNames = Collections.unmodifiableList(paramList1);
      this.referenceColumnNames = Collections.unmodifiableList(paramList2);
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {
        return true;
      }
      if ((paramObject != null) && (getClass() == paramObject.getClass()))
      {
        paramObject = (ForeignKey)paramObject;
        if (!this.referenceTable.equals(paramObject.referenceTable)) {
          return false;
        }
        if (!this.onDelete.equals(paramObject.onDelete)) {
          return false;
        }
        if (!this.onUpdate.equals(paramObject.onUpdate)) {
          return false;
        }
        if (!this.columnNames.equals(paramObject.columnNames)) {
          return false;
        }
        return this.referenceColumnNames.equals(paramObject.referenceColumnNames);
      }
      return false;
    }
    
    public int hashCode()
    {
      return (((this.referenceTable.hashCode() * 31 + this.onDelete.hashCode()) * 31 + this.onUpdate.hashCode()) * 31 + this.columnNames.hashCode()) * 31 + this.referenceColumnNames.hashCode();
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("ForeignKey{referenceTable='");
      localStringBuilder.append(this.referenceTable);
      localStringBuilder.append('\'');
      localStringBuilder.append(", onDelete='");
      localStringBuilder.append(this.onDelete);
      localStringBuilder.append('\'');
      localStringBuilder.append(", onUpdate='");
      localStringBuilder.append(this.onUpdate);
      localStringBuilder.append('\'');
      localStringBuilder.append(", columnNames=");
      localStringBuilder.append(this.columnNames);
      localStringBuilder.append(", referenceColumnNames=");
      localStringBuilder.append(this.referenceColumnNames);
      localStringBuilder.append('}');
      return localStringBuilder.toString();
    }
  }
  
  static class ForeignKeyWithSequence
    implements Comparable<ForeignKeyWithSequence>
  {
    final String mFrom;
    final int mId;
    final int mSequence;
    final String mTo;
    
    ForeignKeyWithSequence(int paramInt1, int paramInt2, String paramString1, String paramString2)
    {
      this.mId = paramInt1;
      this.mSequence = paramInt2;
      this.mFrom = paramString1;
      this.mTo = paramString2;
    }
    
    public int compareTo(ForeignKeyWithSequence paramForeignKeyWithSequence)
    {
      int i = this.mId - paramForeignKeyWithSequence.mId;
      int j = i;
      if (i == 0) {
        j = this.mSequence - paramForeignKeyWithSequence.mSequence;
      }
      return j;
    }
  }
  
  public static class Index
  {
    public static final String DEFAULT_PREFIX = "index_";
    public final List<String> columns;
    public final String name;
    public final boolean unique;
    
    public Index(String paramString, boolean paramBoolean, List<String> paramList)
    {
      this.name = paramString;
      this.unique = paramBoolean;
      this.columns = paramList;
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {
        return true;
      }
      if ((paramObject != null) && (getClass() == paramObject.getClass()))
      {
        paramObject = (Index)paramObject;
        if (this.unique != paramObject.unique) {
          return false;
        }
        if (!this.columns.equals(paramObject.columns)) {
          return false;
        }
        if (this.name.startsWith("index_")) {
          return paramObject.name.startsWith("index_");
        }
        return this.name.equals(paramObject.name);
      }
      return false;
    }
    
    public int hashCode()
    {
      int i;
      if (this.name.startsWith("index_")) {
        i = -1184239155;
      } else {
        i = this.name.hashCode();
      }
      return (i * 31 + this.unique) * 31 + this.columns.hashCode();
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Index{name='");
      localStringBuilder.append(this.name);
      localStringBuilder.append('\'');
      localStringBuilder.append(", unique=");
      localStringBuilder.append(this.unique);
      localStringBuilder.append(", columns=");
      localStringBuilder.append(this.columns);
      localStringBuilder.append('}');
      return localStringBuilder.toString();
    }
  }
}

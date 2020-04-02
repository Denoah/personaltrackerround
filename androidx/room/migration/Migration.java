package androidx.room.migration;

import androidx.sqlite.db.SupportSQLiteDatabase;

public abstract class Migration
{
  public final int endVersion;
  public final int startVersion;
  
  public Migration(int paramInt1, int paramInt2)
  {
    this.startVersion = paramInt1;
    this.endVersion = paramInt2;
  }
  
  public abstract void migrate(SupportSQLiteDatabase paramSupportSQLiteDatabase);
}

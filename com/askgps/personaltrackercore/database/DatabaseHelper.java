package com.askgps.personaltrackercore.database;

import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\b'\030\000 \0132\0020\001:\001\013B\005?\006\002\020\002J\b\020\003\032\0020\004H&J\b\020\005\032\0020\006H&J\b\020\007\032\0020\bH&J\b\020\t\032\0020\nH&?\006\f"}, d2={"Lcom/askgps/personaltrackercore/database/DatabaseHelper;", "Landroidx/room/RoomDatabase;", "()V", "locationDao", "Lcom/askgps/personaltrackercore/database/LocationDao;", "phoneBookDao", "Lcom/askgps/personaltrackercore/database/PhoneBookDao;", "settingsDao", "Lcom/askgps/personaltrackercore/database/SettingsDao;", "stepSensorDao", "Lcom/askgps/personaltrackercore/database/StepSensorDao;", "Companion", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public abstract class DatabaseHelper
  extends RoomDatabase
{
  public static final Companion Companion = new Companion(null);
  public static final String DATABASE_NAME = "database";
  private static final Migration[] MIGRATIONS;
  private static final Migration MIGRATION_2_3 = (Migration)new DatabaseHelper.Companion.MIGRATION_2_3.1(2, 3);
  private static final Migration MIGRATION_3_4 = (Migration)new DatabaseHelper.Companion.MIGRATION_3_4.1(3, 4);
  private static final Migration MIGRATION_4_5 = (Migration)new DatabaseHelper.Companion.MIGRATION_4_5.1(4, 5);
  private static final Migration MIGRATION_5_6 = (Migration)new DatabaseHelper.Companion.MIGRATION_5_6.1(5, 6);
  private static final Migration MIGRATION_6_7 = (Migration)new DatabaseHelper.Companion.MIGRATION_6_7.1(6, 7);
  private static final Migration MIGRATION_7_8 = (Migration)new DatabaseHelper.Companion.MIGRATION_7_8.1(7, 8);
  private static final Migration MIGRATION_8_9;
  
  static
  {
    Migration localMigration = (Migration)new DatabaseHelper.Companion.MIGRATION_8_9.1(8, 9);
    MIGRATION_8_9 = localMigration;
    MIGRATIONS = new Migration[] { MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6, MIGRATION_6_7, MIGRATION_7_8, localMigration };
  }
  
  public DatabaseHelper() {}
  
  public abstract LocationDao locationDao();
  
  public abstract PhoneBookDao phoneBookDao();
  
  public abstract SettingsDao settingsDao();
  
  public abstract StepSensorDao stepSensorDao();
  
  @Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\000\n\002\020\021\n\002\030\002\n\002\b\023\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\016\020\003\032\0020\004X?T?\006\002\n\000R\031\020\005\032\b\022\004\022\0020\0070\006?\006\n\n\002\020\n\032\004\b\b\020\tR\021\020\013\032\0020\007?\006\b\n\000\032\004\b\f\020\rR\021\020\016\032\0020\007?\006\b\n\000\032\004\b\017\020\rR\021\020\020\032\0020\007?\006\b\n\000\032\004\b\021\020\rR\021\020\022\032\0020\007?\006\b\n\000\032\004\b\023\020\rR\021\020\024\032\0020\007?\006\b\n\000\032\004\b\025\020\rR\021\020\026\032\0020\007?\006\b\n\000\032\004\b\027\020\rR\021\020\030\032\0020\007?\006\b\n\000\032\004\b\031\020\r?\006\032"}, d2={"Lcom/askgps/personaltrackercore/database/DatabaseHelper$Companion;", "", "()V", "DATABASE_NAME", "", "MIGRATIONS", "", "Landroidx/room/migration/Migration;", "getMIGRATIONS", "()[Landroidx/room/migration/Migration;", "[Landroidx/room/migration/Migration;", "MIGRATION_2_3", "getMIGRATION_2_3", "()Landroidx/room/migration/Migration;", "MIGRATION_3_4", "getMIGRATION_3_4", "MIGRATION_4_5", "getMIGRATION_4_5", "MIGRATION_5_6", "getMIGRATION_5_6", "MIGRATION_6_7", "getMIGRATION_6_7", "MIGRATION_7_8", "getMIGRATION_7_8", "MIGRATION_8_9", "getMIGRATION_8_9", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final Migration[] getMIGRATIONS()
    {
      return DatabaseHelper.access$getMIGRATIONS$cp();
    }
    
    public final Migration getMIGRATION_2_3()
    {
      return DatabaseHelper.access$getMIGRATION_2_3$cp();
    }
    
    public final Migration getMIGRATION_3_4()
    {
      return DatabaseHelper.access$getMIGRATION_3_4$cp();
    }
    
    public final Migration getMIGRATION_4_5()
    {
      return DatabaseHelper.access$getMIGRATION_4_5$cp();
    }
    
    public final Migration getMIGRATION_5_6()
    {
      return DatabaseHelper.access$getMIGRATION_5_6$cp();
    }
    
    public final Migration getMIGRATION_6_7()
    {
      return DatabaseHelper.access$getMIGRATION_6_7$cp();
    }
    
    public final Migration getMIGRATION_7_8()
    {
      return DatabaseHelper.access$getMIGRATION_7_8$cp();
    }
    
    public final Migration getMIGRATION_8_9()
    {
      return DatabaseHelper.access$getMIGRATION_8_9$cp();
    }
  }
}

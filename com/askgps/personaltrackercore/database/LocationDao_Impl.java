package com.askgps.personaltrackercore.database;

import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.askgps.personaltrackercore.database.model.Location;
import java.util.List;

public final class LocationDao_Impl
  implements LocationDao
{
  private final RoomDatabase __db;
  private final EntityDeletionOrUpdateAdapter<Location> __deletionAdapterOfLocation;
  private final IndoorNavigationTypeConverter __indoorNavigationTypeConverter = new IndoorNavigationTypeConverter();
  private final EntityInsertionAdapter<Location> __insertionAdapterOfLocation;
  
  public LocationDao_Impl(RoomDatabase paramRoomDatabase)
  {
    this.__db = paramRoomDatabase;
    this.__insertionAdapterOfLocation = new EntityInsertionAdapter(paramRoomDatabase)
    {
      public void bind(SupportSQLiteStatement paramAnonymousSupportSQLiteStatement, Location paramAnonymousLocation)
      {
        if (paramAnonymousLocation.getImei() == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(1);
        } else {
          paramAnonymousSupportSQLiteStatement.bindString(1, paramAnonymousLocation.getImei());
        }
        paramAnonymousSupportSQLiteStatement.bindLong(2, paramAnonymousLocation.getDatetime());
        if (paramAnonymousLocation.getLocationDatetime() == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(3);
        } else {
          paramAnonymousSupportSQLiteStatement.bindLong(3, paramAnonymousLocation.getLocationDatetime().longValue());
        }
        if (paramAnonymousLocation.getLat() == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(4);
        } else {
          paramAnonymousSupportSQLiteStatement.bindDouble(4, paramAnonymousLocation.getLat().doubleValue());
        }
        if (paramAnonymousLocation.getLon() == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(5);
        } else {
          paramAnonymousSupportSQLiteStatement.bindDouble(5, paramAnonymousLocation.getLon().doubleValue());
        }
        if (paramAnonymousLocation.getAltitude() == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(6);
        } else {
          paramAnonymousSupportSQLiteStatement.bindDouble(6, paramAnonymousLocation.getAltitude().floatValue());
        }
        if (paramAnonymousLocation.getAccuracy() == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(7);
        } else {
          paramAnonymousSupportSQLiteStatement.bindLong(7, paramAnonymousLocation.getAccuracy().intValue());
        }
        if (paramAnonymousLocation.getBearing() == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(8);
        } else {
          paramAnonymousSupportSQLiteStatement.bindLong(8, paramAnonymousLocation.getBearing().shortValue());
        }
        if (paramAnonymousLocation.getSpeed() == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(9);
        } else {
          paramAnonymousSupportSQLiteStatement.bindDouble(9, paramAnonymousLocation.getSpeed().floatValue());
        }
        if (paramAnonymousLocation.getProvider() == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(10);
        } else {
          paramAnonymousSupportSQLiteStatement.bindString(10, paramAnonymousLocation.getProvider());
        }
        if (paramAnonymousLocation.getStepCount() == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(11);
        } else {
          paramAnonymousSupportSQLiteStatement.bindLong(11, paramAnonymousLocation.getStepCount().intValue());
        }
        Object localObject1 = paramAnonymousLocation.isLeaveHand();
        Object localObject2 = null;
        if (localObject1 == null) {
          localObject1 = null;
        } else {
          localObject1 = Integer.valueOf(paramAnonymousLocation.isLeaveHand().booleanValue());
        }
        if (localObject1 == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(12);
        } else {
          paramAnonymousSupportSQLiteStatement.bindLong(12, ((Integer)localObject1).intValue());
        }
        if (paramAnonymousLocation.getBatteryLevel() == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(13);
        } else {
          paramAnonymousSupportSQLiteStatement.bindLong(13, paramAnonymousLocation.getBatteryLevel().byteValue());
        }
        if (paramAnonymousLocation.isFall() == null) {
          localObject1 = null;
        } else {
          localObject1 = Integer.valueOf(paramAnonymousLocation.isFall().booleanValue());
        }
        if (localObject1 == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(14);
        } else {
          paramAnonymousSupportSQLiteStatement.bindLong(14, ((Integer)localObject1).intValue());
        }
        if (paramAnonymousLocation.getPowerOn() == null) {
          localObject1 = null;
        } else {
          localObject1 = Integer.valueOf(paramAnonymousLocation.getPowerOn().booleanValue());
        }
        if (localObject1 == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(15);
        } else {
          paramAnonymousSupportSQLiteStatement.bindLong(15, ((Integer)localObject1).intValue());
        }
        if (paramAnonymousLocation.getSos() == null) {
          localObject1 = null;
        } else {
          localObject1 = Integer.valueOf(paramAnonymousLocation.getSos().booleanValue());
        }
        if (localObject1 == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(16);
        } else {
          paramAnonymousSupportSQLiteStatement.bindLong(16, ((Integer)localObject1).intValue());
        }
        if (paramAnonymousLocation.getVersion() == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(17);
        } else {
          paramAnonymousSupportSQLiteStatement.bindLong(17, paramAnonymousLocation.getVersion().intValue());
        }
        if (paramAnonymousLocation.getWorkShift() == null) {
          localObject1 = null;
        } else {
          localObject1 = Integer.valueOf(paramAnonymousLocation.getWorkShift().booleanValue());
        }
        if (localObject1 == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(18);
        } else {
          paramAnonymousSupportSQLiteStatement.bindLong(18, ((Integer)localObject1).intValue());
        }
        if (paramAnonymousLocation.isValid() == null) {
          localObject1 = localObject2;
        } else {
          localObject1 = Integer.valueOf(paramAnonymousLocation.isValid().booleanValue());
        }
        if (localObject1 == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(19);
        } else {
          paramAnonymousSupportSQLiteStatement.bindLong(19, ((Integer)localObject1).intValue());
        }
        paramAnonymousLocation = LocationDao_Impl.this.__indoorNavigationTypeConverter.fromIndoorNavigation(paramAnonymousLocation.getIndoorNavigation());
        if (paramAnonymousLocation == null) {
          paramAnonymousSupportSQLiteStatement.bindNull(20);
        } else {
          paramAnonymousSupportSQLiteStatement.bindString(20, paramAnonymousLocation);
        }
      }
      
      public String createQuery()
      {
        return "INSERT OR IGNORE INTO `Location` (`imei`,`datetime`,`locationDatetime`,`lat`,`lon`,`altitude`,`accuracy`,`bearing`,`speed`,`provider`,`stepCount`,`isLeaveHand`,`batteryLevel`,`isFall`,`powerOn`,`sos`,`version`,`workShift`,`isValid`,`indoorNavigation`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }
    };
    this.__deletionAdapterOfLocation = new EntityDeletionOrUpdateAdapter(paramRoomDatabase)
    {
      public void bind(SupportSQLiteStatement paramAnonymousSupportSQLiteStatement, Location paramAnonymousLocation)
      {
        paramAnonymousSupportSQLiteStatement.bindLong(1, paramAnonymousLocation.getDatetime());
      }
      
      public String createQuery()
      {
        return "DELETE FROM `Location` WHERE `datetime` = ?";
      }
    };
  }
  
  /* Error */
  public List<Location> getLocations()
  {
    // Byte code:
    //   0: ldc 47
    //   2: iconst_0
    //   3: invokestatic 53	androidx/room/RoomSQLiteQuery:acquire	(Ljava/lang/String;I)Landroidx/room/RoomSQLiteQuery;
    //   6: astore_1
    //   7: aload_0
    //   8: getfield 32	com/askgps/personaltrackercore/database/LocationDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   11: invokevirtual 58	androidx/room/RoomDatabase:assertNotSuspendingTransaction	()V
    //   14: aload_0
    //   15: getfield 32	com/askgps/personaltrackercore/database/LocationDao_Impl:__db	Landroidx/room/RoomDatabase;
    //   18: aload_1
    //   19: iconst_0
    //   20: aconst_null
    //   21: invokestatic 64	androidx/room/util/DBUtil:query	(Landroidx/room/RoomDatabase;Landroidx/sqlite/db/SupportSQLiteQuery;ZLandroid/os/CancellationSignal;)Landroid/database/Cursor;
    //   24: astore_2
    //   25: aload_2
    //   26: ldc 66
    //   28: invokestatic 72	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   31: istore_3
    //   32: aload_2
    //   33: ldc 74
    //   35: invokestatic 72	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   38: istore 4
    //   40: aload_2
    //   41: ldc 76
    //   43: invokestatic 72	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   46: istore 5
    //   48: aload_2
    //   49: ldc 78
    //   51: invokestatic 72	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   54: istore 6
    //   56: aload_2
    //   57: ldc 80
    //   59: invokestatic 72	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   62: istore 7
    //   64: aload_2
    //   65: ldc 82
    //   67: invokestatic 72	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   70: istore 8
    //   72: aload_2
    //   73: ldc 84
    //   75: invokestatic 72	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   78: istore 9
    //   80: aload_2
    //   81: ldc 86
    //   83: invokestatic 72	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   86: istore 10
    //   88: aload_2
    //   89: ldc 88
    //   91: invokestatic 72	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   94: istore 11
    //   96: aload_2
    //   97: ldc 90
    //   99: invokestatic 72	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   102: istore 12
    //   104: aload_2
    //   105: ldc 92
    //   107: invokestatic 72	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   110: istore 13
    //   112: aload_2
    //   113: ldc 94
    //   115: invokestatic 72	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   118: istore 14
    //   120: aload_2
    //   121: ldc 96
    //   123: invokestatic 72	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   126: istore 15
    //   128: aload_2
    //   129: ldc 98
    //   131: invokestatic 72	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   134: istore 16
    //   136: aload_2
    //   137: ldc 100
    //   139: invokestatic 72	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   142: istore 17
    //   144: aload_2
    //   145: ldc 102
    //   147: invokestatic 72	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   150: istore 18
    //   152: aload_2
    //   153: ldc 104
    //   155: invokestatic 72	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   158: istore 19
    //   160: aload_2
    //   161: ldc 106
    //   163: invokestatic 72	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   166: istore 20
    //   168: aload_2
    //   169: ldc 108
    //   171: invokestatic 72	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   174: istore 21
    //   176: aload_2
    //   177: ldc 110
    //   179: invokestatic 72	androidx/room/util/CursorUtil:getColumnIndexOrThrow	(Landroid/database/Cursor;Ljava/lang/String;)I
    //   182: istore 22
    //   184: new 112	java/util/ArrayList
    //   187: astore 23
    //   189: aload 23
    //   191: aload_2
    //   192: invokeinterface 118 1 0
    //   197: invokespecial 121	java/util/ArrayList:<init>	(I)V
    //   200: aload_2
    //   201: invokeinterface 125 1 0
    //   206: ifeq +834 -> 1040
    //   209: aload_2
    //   210: iload_3
    //   211: invokeinterface 129 2 0
    //   216: astore 24
    //   218: aload_2
    //   219: iload 4
    //   221: invokeinterface 133 2 0
    //   226: lstore 25
    //   228: aload_2
    //   229: iload 5
    //   231: invokeinterface 137 2 0
    //   236: ifeq +9 -> 245
    //   239: aconst_null
    //   240: astore 27
    //   242: goto +16 -> 258
    //   245: aload_2
    //   246: iload 5
    //   248: invokeinterface 133 2 0
    //   253: invokestatic 143	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   256: astore 27
    //   258: aload_2
    //   259: iload 6
    //   261: invokeinterface 137 2 0
    //   266: ifeq +9 -> 275
    //   269: aconst_null
    //   270: astore 28
    //   272: goto +16 -> 288
    //   275: aload_2
    //   276: iload 6
    //   278: invokeinterface 147 2 0
    //   283: invokestatic 152	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   286: astore 28
    //   288: aload_2
    //   289: iload 7
    //   291: invokeinterface 137 2 0
    //   296: ifeq +9 -> 305
    //   299: aconst_null
    //   300: astore 29
    //   302: goto +16 -> 318
    //   305: aload_2
    //   306: iload 7
    //   308: invokeinterface 147 2 0
    //   313: invokestatic 152	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   316: astore 29
    //   318: aload_2
    //   319: iload 8
    //   321: invokeinterface 137 2 0
    //   326: ifeq +9 -> 335
    //   329: aconst_null
    //   330: astore 30
    //   332: goto +16 -> 348
    //   335: aload_2
    //   336: iload 8
    //   338: invokeinterface 156 2 0
    //   343: invokestatic 161	java/lang/Float:valueOf	(F)Ljava/lang/Float;
    //   346: astore 30
    //   348: aload_2
    //   349: iload 9
    //   351: invokeinterface 137 2 0
    //   356: ifeq +9 -> 365
    //   359: aconst_null
    //   360: astore 31
    //   362: goto +16 -> 378
    //   365: aload_2
    //   366: iload 9
    //   368: invokeinterface 165 2 0
    //   373: invokestatic 170	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   376: astore 31
    //   378: aload_2
    //   379: iload 10
    //   381: invokeinterface 137 2 0
    //   386: ifeq +9 -> 395
    //   389: aconst_null
    //   390: astore 32
    //   392: goto +16 -> 408
    //   395: aload_2
    //   396: iload 10
    //   398: invokeinterface 174 2 0
    //   403: invokestatic 179	java/lang/Short:valueOf	(S)Ljava/lang/Short;
    //   406: astore 32
    //   408: aload_2
    //   409: iload 11
    //   411: invokeinterface 137 2 0
    //   416: ifeq +9 -> 425
    //   419: aconst_null
    //   420: astore 33
    //   422: goto +16 -> 438
    //   425: aload_2
    //   426: iload 11
    //   428: invokeinterface 156 2 0
    //   433: invokestatic 161	java/lang/Float:valueOf	(F)Ljava/lang/Float;
    //   436: astore 33
    //   438: aload_2
    //   439: iload 12
    //   441: invokeinterface 129 2 0
    //   446: astore 34
    //   448: aload_2
    //   449: iload 13
    //   451: invokeinterface 137 2 0
    //   456: ifeq +9 -> 465
    //   459: aconst_null
    //   460: astore 35
    //   462: goto +16 -> 478
    //   465: aload_2
    //   466: iload 13
    //   468: invokeinterface 165 2 0
    //   473: invokestatic 170	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   476: astore 35
    //   478: aload_2
    //   479: iload 14
    //   481: invokeinterface 137 2 0
    //   486: ifeq +9 -> 495
    //   489: aconst_null
    //   490: astore 36
    //   492: goto +16 -> 508
    //   495: aload_2
    //   496: iload 14
    //   498: invokeinterface 165 2 0
    //   503: invokestatic 170	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   506: astore 36
    //   508: iconst_1
    //   509: istore 37
    //   511: aload 36
    //   513: ifnonnull +9 -> 522
    //   516: aconst_null
    //   517: astore 36
    //   519: goto +27 -> 546
    //   522: aload 36
    //   524: invokevirtual 182	java/lang/Integer:intValue	()I
    //   527: ifeq +9 -> 536
    //   530: iconst_1
    //   531: istore 38
    //   533: goto +6 -> 539
    //   536: iconst_0
    //   537: istore 38
    //   539: iload 38
    //   541: invokestatic 187	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   544: astore 36
    //   546: aload_2
    //   547: iload 15
    //   549: invokeinterface 137 2 0
    //   554: ifeq +9 -> 563
    //   557: aconst_null
    //   558: astore 39
    //   560: goto +17 -> 577
    //   563: aload_2
    //   564: iload 15
    //   566: invokeinterface 174 2 0
    //   571: i2b
    //   572: invokestatic 192	java/lang/Byte:valueOf	(B)Ljava/lang/Byte;
    //   575: astore 39
    //   577: aload_2
    //   578: iload 16
    //   580: invokeinterface 137 2 0
    //   585: ifeq +9 -> 594
    //   588: aconst_null
    //   589: astore 40
    //   591: goto +16 -> 607
    //   594: aload_2
    //   595: iload 16
    //   597: invokeinterface 165 2 0
    //   602: invokestatic 170	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   605: astore 40
    //   607: aload 40
    //   609: ifnonnull +9 -> 618
    //   612: aconst_null
    //   613: astore 40
    //   615: goto +30 -> 645
    //   618: aload 40
    //   620: invokevirtual 182	java/lang/Integer:intValue	()I
    //   623: ifeq +9 -> 632
    //   626: iconst_1
    //   627: istore 38
    //   629: goto +6 -> 635
    //   632: iconst_0
    //   633: istore 38
    //   635: iload 38
    //   637: invokestatic 187	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   640: astore 40
    //   642: goto -27 -> 615
    //   645: aload_2
    //   646: iload 17
    //   648: invokeinterface 137 2 0
    //   653: ifeq +9 -> 662
    //   656: aconst_null
    //   657: astore 41
    //   659: goto +16 -> 675
    //   662: aload_2
    //   663: iload 17
    //   665: invokeinterface 165 2 0
    //   670: invokestatic 170	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   673: astore 41
    //   675: aload 41
    //   677: ifnonnull +9 -> 686
    //   680: aconst_null
    //   681: astore 41
    //   683: goto +30 -> 713
    //   686: aload 41
    //   688: invokevirtual 182	java/lang/Integer:intValue	()I
    //   691: ifeq +9 -> 700
    //   694: iconst_1
    //   695: istore 38
    //   697: goto +6 -> 703
    //   700: iconst_0
    //   701: istore 38
    //   703: iload 38
    //   705: invokestatic 187	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   708: astore 41
    //   710: goto -27 -> 683
    //   713: aload_2
    //   714: iload 18
    //   716: invokeinterface 137 2 0
    //   721: ifeq +9 -> 730
    //   724: aconst_null
    //   725: astore 42
    //   727: goto +16 -> 743
    //   730: aload_2
    //   731: iload 18
    //   733: invokeinterface 165 2 0
    //   738: invokestatic 170	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   741: astore 42
    //   743: aload 42
    //   745: ifnonnull +9 -> 754
    //   748: aconst_null
    //   749: astore 42
    //   751: goto +30 -> 781
    //   754: aload 42
    //   756: invokevirtual 182	java/lang/Integer:intValue	()I
    //   759: ifeq +9 -> 768
    //   762: iconst_1
    //   763: istore 38
    //   765: goto +6 -> 771
    //   768: iconst_0
    //   769: istore 38
    //   771: iload 38
    //   773: invokestatic 187	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   776: astore 42
    //   778: goto -27 -> 751
    //   781: aload_2
    //   782: iload 19
    //   784: invokeinterface 137 2 0
    //   789: ifeq +9 -> 798
    //   792: aconst_null
    //   793: astore 43
    //   795: goto +19 -> 814
    //   798: aload_2
    //   799: iload 19
    //   801: invokeinterface 165 2 0
    //   806: invokestatic 170	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   809: astore 43
    //   811: goto -16 -> 795
    //   814: aload_2
    //   815: iload 20
    //   817: invokeinterface 137 2 0
    //   822: ifeq +9 -> 831
    //   825: aconst_null
    //   826: astore 44
    //   828: goto +16 -> 844
    //   831: aload_2
    //   832: iload 20
    //   834: invokeinterface 165 2 0
    //   839: invokestatic 170	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   842: astore 44
    //   844: aload 44
    //   846: ifnonnull +9 -> 855
    //   849: aconst_null
    //   850: astore 44
    //   852: goto +30 -> 882
    //   855: aload 44
    //   857: invokevirtual 182	java/lang/Integer:intValue	()I
    //   860: ifeq +9 -> 869
    //   863: iconst_1
    //   864: istore 38
    //   866: goto +6 -> 872
    //   869: iconst_0
    //   870: istore 38
    //   872: iload 38
    //   874: invokestatic 187	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   877: astore 44
    //   879: goto -27 -> 852
    //   882: aload_2
    //   883: iload 21
    //   885: invokeinterface 137 2 0
    //   890: ifeq +9 -> 899
    //   893: aconst_null
    //   894: astore 45
    //   896: goto +16 -> 912
    //   899: aload_2
    //   900: iload 21
    //   902: invokeinterface 165 2 0
    //   907: invokestatic 170	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   910: astore 45
    //   912: aload 45
    //   914: ifnonnull +9 -> 923
    //   917: aconst_null
    //   918: astore 45
    //   920: goto +31 -> 951
    //   923: aload 45
    //   925: invokevirtual 182	java/lang/Integer:intValue	()I
    //   928: ifeq +10 -> 938
    //   931: iload 37
    //   933: istore 38
    //   935: goto +6 -> 941
    //   938: iconst_0
    //   939: istore 38
    //   941: iload 38
    //   943: invokestatic 187	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   946: astore 45
    //   948: goto -28 -> 920
    //   951: aload_2
    //   952: iload 22
    //   954: invokeinterface 129 2 0
    //   959: astore 46
    //   961: aload_0
    //   962: getfield 30	com/askgps/personaltrackercore/database/LocationDao_Impl:__indoorNavigationTypeConverter	Lcom/askgps/personaltrackercore/database/IndoorNavigationTypeConverter;
    //   965: aload 46
    //   967: invokevirtual 196	com/askgps/personaltrackercore/database/IndoorNavigationTypeConverter:toIndoorNavigation	(Ljava/lang/String;)Lcom/askgps/personaltrackercore/database/model/IndoorNavigation;
    //   970: astore 46
    //   972: new 198	com/askgps/personaltrackercore/database/model/Location
    //   975: astore 47
    //   977: aload 47
    //   979: aload 24
    //   981: lload 25
    //   983: aload 27
    //   985: aload 28
    //   987: aload 29
    //   989: aload 30
    //   991: aload 31
    //   993: aload 32
    //   995: aload 33
    //   997: aload 34
    //   999: aload 35
    //   1001: aload 36
    //   1003: aload 39
    //   1005: aload 40
    //   1007: aload 41
    //   1009: aload 42
    //   1011: aload 43
    //   1013: aload 44
    //   1015: aload 45
    //   1017: aload 46
    //   1019: invokespecial 201	com/askgps/personaltrackercore/database/model/Location:<init>	(Ljava/lang/String;JLjava/lang/Long;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Float;Ljava/lang/Integer;Ljava/lang/Short;Ljava/lang/Float;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Boolean;Ljava/lang/Byte;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Integer;Ljava/lang/Boolean;Ljava/lang/Boolean;Lcom/askgps/personaltrackercore/database/model/IndoorNavigation;)V
    //   1022: aload 23
    //   1024: aload 47
    //   1026: invokeinterface 207 2 0
    //   1031: pop
    //   1032: goto -832 -> 200
    //   1035: astore 27
    //   1037: goto +28 -> 1065
    //   1040: aload_2
    //   1041: invokeinterface 210 1 0
    //   1046: aload_1
    //   1047: invokevirtual 213	androidx/room/RoomSQLiteQuery:release	()V
    //   1050: aload 23
    //   1052: areturn
    //   1053: astore 27
    //   1055: goto +10 -> 1065
    //   1058: astore 27
    //   1060: goto +5 -> 1065
    //   1063: astore 27
    //   1065: aload_2
    //   1066: invokeinterface 210 1 0
    //   1071: aload_1
    //   1072: invokevirtual 213	androidx/room/RoomSQLiteQuery:release	()V
    //   1075: aload 27
    //   1077: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1078	0	this	LocationDao_Impl
    //   6	1066	1	localRoomSQLiteQuery	androidx.room.RoomSQLiteQuery
    //   24	1042	2	localCursor	android.database.Cursor
    //   31	180	3	i	int
    //   38	182	4	j	int
    //   46	201	5	k	int
    //   54	223	6	m	int
    //   62	245	7	n	int
    //   70	267	8	i1	int
    //   78	289	9	i2	int
    //   86	311	10	i3	int
    //   94	333	11	i4	int
    //   102	338	12	i5	int
    //   110	357	13	i6	int
    //   118	379	14	i7	int
    //   126	439	15	i8	int
    //   134	462	16	i9	int
    //   142	522	17	i10	int
    //   150	582	18	i11	int
    //   158	642	19	i12	int
    //   166	667	20	i13	int
    //   174	727	21	i14	int
    //   182	771	22	i15	int
    //   187	864	23	localArrayList	java.util.ArrayList
    //   216	764	24	str1	String
    //   226	756	25	l	long
    //   240	744	27	localLong	Long
    //   1035	1	27	localObject1	Object
    //   1053	1	27	localObject2	Object
    //   1058	1	27	localObject3	Object
    //   1063	13	27	localObject4	Object
    //   270	716	28	localDouble1	Double
    //   300	688	29	localDouble2	Double
    //   330	660	30	localFloat1	Float
    //   360	632	31	localInteger1	Integer
    //   390	604	32	localShort	Short
    //   420	576	33	localFloat2	Float
    //   446	552	34	str2	String
    //   460	540	35	localInteger2	Integer
    //   490	512	36	localObject5	Object
    //   509	423	37	bool1	boolean
    //   531	411	38	bool2	boolean
    //   558	446	39	localByte	Byte
    //   589	417	40	localObject6	Object
    //   657	351	41	localObject7	Object
    //   725	285	42	localObject8	Object
    //   793	219	43	localInteger3	Integer
    //   826	188	44	localObject9	Object
    //   894	122	45	localObject10	Object
    //   959	59	46	localObject11	Object
    //   975	50	47	localLocation	Location
    // Exception table:
    //   from	to	target	type
    //   961	1032	1035	finally
    //   136	189	1053	finally
    //   189	200	1053	finally
    //   200	239	1053	finally
    //   245	258	1053	finally
    //   258	269	1053	finally
    //   275	288	1053	finally
    //   288	299	1053	finally
    //   305	318	1053	finally
    //   318	329	1053	finally
    //   335	348	1053	finally
    //   348	359	1053	finally
    //   365	378	1053	finally
    //   378	389	1053	finally
    //   395	408	1053	finally
    //   408	419	1053	finally
    //   425	438	1053	finally
    //   438	459	1053	finally
    //   465	478	1053	finally
    //   478	489	1053	finally
    //   495	508	1053	finally
    //   522	530	1053	finally
    //   539	546	1053	finally
    //   546	557	1053	finally
    //   563	577	1053	finally
    //   577	588	1053	finally
    //   594	607	1053	finally
    //   618	626	1053	finally
    //   635	642	1053	finally
    //   645	656	1053	finally
    //   662	675	1053	finally
    //   686	694	1053	finally
    //   703	710	1053	finally
    //   713	724	1053	finally
    //   730	743	1053	finally
    //   754	762	1053	finally
    //   771	778	1053	finally
    //   781	792	1053	finally
    //   798	811	1053	finally
    //   814	825	1053	finally
    //   831	844	1053	finally
    //   855	863	1053	finally
    //   872	879	1053	finally
    //   882	893	1053	finally
    //   899	912	1053	finally
    //   923	931	1053	finally
    //   941	948	1053	finally
    //   951	961	1053	finally
    //   128	136	1058	finally
    //   25	128	1063	finally
  }
  
  public void insertLocations(List<Location> paramList)
  {
    this.__db.assertNotSuspendingTransaction();
    this.__db.beginTransaction();
    try
    {
      this.__insertionAdapterOfLocation.insert(paramList);
      this.__db.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.__db.endTransaction();
    }
  }
  
  public void removeLocation(List<Location> paramList)
  {
    this.__db.assertNotSuspendingTransaction();
    this.__db.beginTransaction();
    try
    {
      this.__deletionAdapterOfLocation.handleMultiple(paramList);
      this.__db.setTransactionSuccessful();
      return;
    }
    finally
    {
      this.__db.endTransaction();
    }
  }
}

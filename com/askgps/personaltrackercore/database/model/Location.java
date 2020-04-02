package com.askgps.personaltrackercore.database.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000J\n\002\030\002\n\002\020\000\n\000\n\002\020\016\n\000\n\002\020\t\n\002\b\002\n\002\020\006\n\002\b\002\n\002\020\007\n\000\n\002\020\b\n\000\n\002\020\n\n\002\b\004\n\002\020\013\n\000\n\002\020\005\n\002\b\007\n\002\030\002\n\002\b%\b\007\030\0002\0020\001B?\001\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\n\b\002\020\006\032\004\030\0010\005\022\n\b\002\020\007\032\004\030\0010\b\022\n\b\002\020\t\032\004\030\0010\b\022\n\b\002\020\n\032\004\030\0010\013\022\n\b\002\020\f\032\004\030\0010\r\022\n\b\002\020\016\032\004\030\0010\017\022\n\b\002\020\020\032\004\030\0010\013\022\n\b\002\020\021\032\004\030\0010\003\022\n\b\002\020\022\032\004\030\0010\r\022\n\b\002\020\023\032\004\030\0010\024\022\n\b\002\020\025\032\004\030\0010\026\022\n\b\002\020\027\032\004\030\0010\024\022\n\b\002\020\030\032\004\030\0010\024\022\n\b\002\020\031\032\004\030\0010\024\022\n\b\002\020\032\032\004\030\0010\r\022\n\b\002\020\033\032\004\030\0010\024\022\n\b\002\020\034\032\004\030\0010\024\022\n\b\002\020\035\032\004\030\0010\036?\006\002\020\037J\b\020B\032\0020\003H\026R\032\020\f\032\004\030\0010\r8\006X?\004?\006\n\n\002\020\"\032\004\b \020!R\032\020\n\032\004\030\0010\0138\006X?\004?\006\n\n\002\020%\032\004\b#\020$R\032\020\025\032\004\030\0010\0268\006X?\004?\006\n\n\002\020(\032\004\b&\020'R\032\020\016\032\004\030\0010\0178\006X?\004?\006\n\n\002\020+\032\004\b)\020*R\026\020\004\032\0020\0058\006X?\004?\006\b\n\000\032\004\b,\020-R\026\020\002\032\0020\0038\006X?\004?\006\b\n\000\032\004\b.\020/R\030\020\035\032\004\030\0010\0368\006X?\004?\006\b\n\000\032\004\b0\0201R\032\020\027\032\004\030\0010\0248\006X?\004?\006\n\n\002\0203\032\004\b\027\0202R\032\020\023\032\004\030\0010\0248\006X?\004?\006\n\n\002\0203\032\004\b\023\0202R\032\020\034\032\004\030\0010\0248\006X?\004?\006\n\n\002\0203\032\004\b\034\0202R\032\020\007\032\004\030\0010\b8\006X?\004?\006\n\n\002\0206\032\004\b4\0205R\032\020\006\032\004\030\0010\0058\006X?\004?\006\n\n\002\0209\032\004\b7\0208R\032\020\t\032\004\030\0010\b8\006X?\004?\006\n\n\002\0206\032\004\b:\0205R\032\020\030\032\004\030\0010\0248\006X?\004?\006\n\n\002\0203\032\004\b;\0202R\030\020\021\032\004\030\0010\0038\006X?\004?\006\b\n\000\032\004\b<\020/R\032\020\031\032\004\030\0010\0248\006X?\004?\006\n\n\002\0203\032\004\b=\0202R\032\020\020\032\004\030\0010\0138\006X?\004?\006\n\n\002\020%\032\004\b>\020$R\032\020\022\032\004\030\0010\r8\006X?\004?\006\n\n\002\020\"\032\004\b?\020!R\032\020\032\032\004\030\0010\r8\006X?\004?\006\n\n\002\020\"\032\004\b@\020!R\032\020\033\032\004\030\0010\0248\006X?\004?\006\n\n\002\0203\032\004\bA\0202?\006C"}, d2={"Lcom/askgps/personaltrackercore/database/model/Location;", "", "imei", "", "datetime", "", "locationDatetime", "lat", "", "lon", "altitude", "", "accuracy", "", "bearing", "", "speed", "provider", "stepCount", "isLeaveHand", "", "batteryLevel", "", "isFall", "powerOn", "sos", "version", "workShift", "isValid", "indoorNavigation", "Lcom/askgps/personaltrackercore/database/model/IndoorNavigation;", "(Ljava/lang/String;JLjava/lang/Long;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Float;Ljava/lang/Integer;Ljava/lang/Short;Ljava/lang/Float;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Boolean;Ljava/lang/Byte;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Integer;Ljava/lang/Boolean;Ljava/lang/Boolean;Lcom/askgps/personaltrackercore/database/model/IndoorNavigation;)V", "getAccuracy", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getAltitude", "()Ljava/lang/Float;", "Ljava/lang/Float;", "getBatteryLevel", "()Ljava/lang/Byte;", "Ljava/lang/Byte;", "getBearing", "()Ljava/lang/Short;", "Ljava/lang/Short;", "getDatetime", "()J", "getImei", "()Ljava/lang/String;", "getIndoorNavigation", "()Lcom/askgps/personaltrackercore/database/model/IndoorNavigation;", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "getLat", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getLocationDatetime", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getLon", "getPowerOn", "getProvider", "getSos", "getSpeed", "getStepCount", "getVersion", "getWorkShift", "toString", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class Location
{
  @SerializedName("accuracy")
  private final Integer accuracy;
  @SerializedName("altitude")
  private final Float altitude;
  @SerializedName("battery_level")
  private final Byte batteryLevel;
  @SerializedName("bearing")
  private final Short bearing;
  @JsonAdapter(DateSerializer.class)
  @SerializedName("datetime")
  private final long datetime;
  @SerializedName("phone_number")
  private final String imei;
  @SerializedName("indoorNavigation")
  private final IndoorNavigation indoorNavigation;
  @SerializedName("is_fall")
  private final Boolean isFall;
  @SerializedName("is_leave_hand")
  private final Boolean isLeaveHand;
  @SerializedName("isValid")
  private final Boolean isValid;
  @SerializedName("lat")
  private final Double lat;
  @JsonAdapter(DateSerializer.class)
  @SerializedName("locationDatetime")
  private final Long locationDatetime;
  @SerializedName("lon")
  private final Double lon;
  @SerializedName("power_on")
  private final Boolean powerOn;
  @SerializedName("provider")
  private final String provider;
  @SerializedName("sos")
  private final Boolean sos;
  @SerializedName("speed")
  private final Float speed;
  @SerializedName("step_count")
  private final Integer stepCount;
  @SerializedName("version")
  private final Integer version;
  @SerializedName("workShift")
  private final Boolean workShift;
  
  public Location(String paramString1, long paramLong, Long paramLong1, Double paramDouble1, Double paramDouble2, Float paramFloat1, Integer paramInteger1, Short paramShort, Float paramFloat2, String paramString2, Integer paramInteger2, Boolean paramBoolean1, Byte paramByte, Boolean paramBoolean2, Boolean paramBoolean3, Boolean paramBoolean4, Integer paramInteger3, Boolean paramBoolean5, Boolean paramBoolean6, IndoorNavigation paramIndoorNavigation)
  {
    this.imei = paramString1;
    this.datetime = paramLong;
    this.locationDatetime = paramLong1;
    this.lat = paramDouble1;
    this.lon = paramDouble2;
    this.altitude = paramFloat1;
    this.accuracy = paramInteger1;
    this.bearing = paramShort;
    this.speed = paramFloat2;
    this.provider = paramString2;
    this.stepCount = paramInteger2;
    this.isLeaveHand = paramBoolean1;
    this.batteryLevel = paramByte;
    this.isFall = paramBoolean2;
    this.powerOn = paramBoolean3;
    this.sos = paramBoolean4;
    this.version = paramInteger3;
    this.workShift = paramBoolean5;
    this.isValid = paramBoolean6;
    this.indoorNavigation = paramIndoorNavigation;
  }
  
  public final Integer getAccuracy()
  {
    return this.accuracy;
  }
  
  public final Float getAltitude()
  {
    return this.altitude;
  }
  
  public final Byte getBatteryLevel()
  {
    return this.batteryLevel;
  }
  
  public final Short getBearing()
  {
    return this.bearing;
  }
  
  public final long getDatetime()
  {
    return this.datetime;
  }
  
  public final String getImei()
  {
    return this.imei;
  }
  
  public final IndoorNavigation getIndoorNavigation()
  {
    return this.indoorNavigation;
  }
  
  public final Double getLat()
  {
    return this.lat;
  }
  
  public final Long getLocationDatetime()
  {
    return this.locationDatetime;
  }
  
  public final Double getLon()
  {
    return this.lon;
  }
  
  public final Boolean getPowerOn()
  {
    return this.powerOn;
  }
  
  public final String getProvider()
  {
    return this.provider;
  }
  
  public final Boolean getSos()
  {
    return this.sos;
  }
  
  public final Float getSpeed()
  {
    return this.speed;
  }
  
  public final Integer getStepCount()
  {
    return this.stepCount;
  }
  
  public final Integer getVersion()
  {
    return this.version;
  }
  
  public final Boolean getWorkShift()
  {
    return this.workShift;
  }
  
  public final Boolean isFall()
  {
    return this.isFall;
  }
  
  public final Boolean isLeaveHand()
  {
    return this.isLeaveHand;
  }
  
  public final Boolean isValid()
  {
    return this.isValid;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Location(imei='");
    localStringBuilder.append(this.imei);
    localStringBuilder.append("', datetime=");
    localStringBuilder.append(this.datetime);
    localStringBuilder.append(", locationDatetime=");
    localStringBuilder.append(this.locationDatetime);
    localStringBuilder.append(", lat=");
    localStringBuilder.append(this.lat);
    localStringBuilder.append(", lon=");
    localStringBuilder.append(this.lon);
    localStringBuilder.append(", altitude=");
    localStringBuilder.append(this.altitude);
    localStringBuilder.append(", accuracy=");
    localStringBuilder.append(this.accuracy);
    localStringBuilder.append(", bearing=");
    localStringBuilder.append(this.bearing);
    localStringBuilder.append(", speed=");
    localStringBuilder.append(this.speed);
    localStringBuilder.append(", provider=");
    localStringBuilder.append(this.provider);
    localStringBuilder.append(", stepCount=");
    localStringBuilder.append(this.stepCount);
    localStringBuilder.append(", isLeaveHand=");
    localStringBuilder.append(this.isLeaveHand);
    localStringBuilder.append(", batteryLevel=");
    localStringBuilder.append(this.batteryLevel);
    localStringBuilder.append(", isFall=");
    localStringBuilder.append(this.isFall);
    localStringBuilder.append(", powerOn=");
    localStringBuilder.append(this.powerOn);
    localStringBuilder.append(", sos=");
    localStringBuilder.append(this.sos);
    localStringBuilder.append(", version=");
    localStringBuilder.append(this.version);
    localStringBuilder.append(", workShift=");
    localStringBuilder.append(this.workShift);
    localStringBuilder.append(", isValid=");
    localStringBuilder.append(this.isValid);
    localStringBuilder.append(", indoorNavigation=");
    localStringBuilder.append(this.indoorNavigation);
    localStringBuilder.append(')');
    return localStringBuilder.toString();
  }
}

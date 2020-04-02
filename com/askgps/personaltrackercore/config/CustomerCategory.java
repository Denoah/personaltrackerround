package com.askgps.personaltrackercore.config;

import kotlin.Metadata;
import org.koin.core.Koin;
import org.koin.core.KoinComponent;
import org.koin.core.KoinComponent.DefaultImpls;

@Metadata(bv={1, 0, 3}, d1={"\000\034\n\002\030\002\n\002\020\020\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020\t\n\002\b\013\b?\001\030\0002\b\022\004\022\0020\0000\0012\0020\002B\037\b\002\022\006\020\003\032\0020\004\022\006\020\005\032\0020\006\022\006\020\007\032\0020\006?\006\002\020\bR\021\020\003\032\0020\004?\006\b\n\000\032\004\b\t\020\nR\021\020\005\032\0020\006?\006\b\n\000\032\004\b\013\020\fR\021\020\007\032\0020\006?\006\b\n\000\032\004\b\r\020\fj\002\b\016j\002\b\017j\002\b\020?\006\021"}, d2={"Lcom/askgps/personaltrackercore/config/CustomerCategory;", "", "Lorg/koin/core/KoinComponent;", "address", "", "sendLocationInterval", "", "updateLocationInterval", "(Ljava/lang/String;ILjava/lang/String;JJ)V", "getAddress", "()Ljava/lang/String;", "getSendLocationInterval", "()J", "getUpdateLocationInterval", "PATIENT_PHONE", "PATIENT_WATCH", "BUILDER_WATCH", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public enum CustomerCategory
  implements KoinComponent
{
  private final String address;
  private final long sendLocationInterval;
  private final long updateLocationInterval;
  
  static
  {
    CustomerCategory localCustomerCategory1 = new CustomerCategory("PATIENT_PHONE", 0, "http://185.221.153.208", 120000L, 60000L);
    PATIENT_PHONE = localCustomerCategory1;
    CustomerCategory localCustomerCategory2 = new CustomerCategory("PATIENT_WATCH", 1, "http://185.221.153.208", 120000L, 60000L);
    PATIENT_WATCH = localCustomerCategory2;
    CustomerCategory localCustomerCategory3 = new CustomerCategory("BUILDER_WATCH", 2, "http://195.93.229.67:12310", 1200000L, 300000L);
    BUILDER_WATCH = localCustomerCategory3;
    $VALUES = new CustomerCategory[] { localCustomerCategory1, localCustomerCategory2, localCustomerCategory3 };
  }
  
  private CustomerCategory(String paramString, long paramLong1, long paramLong2)
  {
    this.address = paramString;
    this.sendLocationInterval = paramLong1;
    this.updateLocationInterval = paramLong2;
  }
  
  public final String getAddress()
  {
    return this.address;
  }
  
  public Koin getKoin()
  {
    return KoinComponent.DefaultImpls.getKoin(this);
  }
  
  public final long getSendLocationInterval()
  {
    return this.sendLocationInterval;
  }
  
  public final long getUpdateLocationInterval()
  {
    return this.updateLocationInterval;
  }
}

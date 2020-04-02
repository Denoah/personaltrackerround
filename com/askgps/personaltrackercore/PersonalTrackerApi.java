package com.askgps.personaltrackercore;

import com.askgps.personaltrackercore.config.CustomerCategory;
import com.askgps.personaltrackercore.database.model.AuthResponse;
import com.askgps.personaltrackercore.database.model.CheckDeviceResponse;
import com.askgps.personaltrackercore.database.model.Device;
import com.askgps.personaltrackercore.database.model.Location;
import java.security.Security;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.KProperty;
import okhttp3.Interceptor;
import okhttp3.MultipartBody.Part;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import okhttp3.logging.HttpLoggingInterceptor.Logger;
import org.conscrypt.Conscrypt;
import retrofit2.Call;
import retrofit2.Converter.Factory;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

@Metadata(bv={1, 0, 3}, d1={"\000L\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020 \n\002\030\002\n\002\b\002\n\002\020\021\n\002\020$\n\002\b\002\bf\030\000 \0302\0020\001:\001\030J\030\020\002\032\b\022\004\022\0020\0040\0032\b\b\001\020\005\032\0020\006H'J\"\020\007\032\b\022\004\022\0020\b0\0032\b\b\001\020\005\032\0020\0062\b\b\001\020\t\032\0020\nH'J\030\020\013\032\b\022\004\022\0020\f0\0032\b\b\001\020\r\032\0020\016H'J\030\020\017\032\b\022\004\022\0020\b0\0032\b\b\001\020\r\032\0020\016H'J\036\020\020\032\b\022\004\022\0020\b0\0032\016\b\001\020\021\032\b\022\004\022\0020\0230\022H'J(\020\024\032\b\022\004\022\0020\b0\0032\b\b\001\020\005\032\0020\0062\016\b\001\020\021\032\b\022\004\022\0020\0230\022H'J:\020\025\032\032\022\026\022\024\022\020\022\016\022\004\022\0020\006\022\004\022\0020\0060\0270\0260\0032\b\b\001\020\005\032\0020\0062\016\b\001\020\021\032\b\022\004\022\0020\0230\022H'?\006\031"}, d2={"Lcom/askgps/personaltrackercore/PersonalTrackerApi;", "", "checkDevicestatus", "Lretrofit2/Call;", "Lcom/askgps/personaltrackercore/database/model/CheckDeviceResponse;", "imei", "", "patientIdentification", "", "photo", "Lokhttp3/MultipartBody$Part;", "registerDevice", "Lcom/askgps/personaltrackercore/database/model/AuthResponse;", "data", "Lcom/askgps/personaltrackercore/database/model/Device;", "requestPassword", "sendBuilderLocation", "location", "", "Lcom/askgps/personaltrackercore/database/model/Location;", "sendPatientLocation", "sendPatientLocationWithCommand", "", "", "Factory", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public abstract interface PersonalTrackerApi
{
  public static final Factory Factory = Factory.$$INSTANCE;
  
  @GET("/api/covid/Device/{deviceId}/status")
  public abstract Call<CheckDeviceResponse> checkDevicestatus(@Path("deviceId") String paramString);
  
  @Multipart
  @POST("/api/covid/Device/{deviceId}/photo")
  public abstract Call<Unit> patientIdentification(@Path("deviceId") String paramString, @Part MultipartBody.Part paramPart);
  
  @POST("/api/covid/Device/auth")
  public abstract Call<AuthResponse> registerDevice(@Body Device paramDevice);
  
  @POST("/api/covid/Device/password")
  public abstract Call<Unit> requestPassword(@Body Device paramDevice);
  
  @POST("/personaltracker")
  public abstract Call<Unit> sendBuilderLocation(@Body List<Location> paramList);
  
  @POST("/api/covid/Device/{deviceId}/message")
  public abstract Call<Unit> sendPatientLocation(@Path("deviceId") String paramString, @Body List<Location> paramList);
  
  @POST("/api/covid/Device/{deviceId}/message")
  public abstract Call<Map<String, String>[]> sendPatientLocationWithCommand(@Path("deviceId") String paramString, @Body List<Location> paramList);
  
  @Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\t\n\000\n\002\030\002\n\002\b\005\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\016\020\003\032\0020\004X?T?\006\002\n\000R\033\020\005\032\0020\0068FX??\002?\006\f\n\004\b\t\020\n\032\004\b\007\020\b?\006\013"}, d2={"Lcom/askgps/personaltrackercore/PersonalTrackerApi$Factory;", "", "()V", "TIMEOUT", "", "instance", "Lcom/askgps/personaltrackercore/PersonalTrackerApi;", "getInstance", "()Lcom/askgps/personaltrackercore/PersonalTrackerApi;", "instance$delegate", "Lkotlin/Lazy;", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
  public static final class Factory
  {
    private static final long TIMEOUT = 10000L;
    private static final Lazy instance$delegate;
    
    static
    {
      Factory localFactory = new Factory();
      $$INSTANCE = localFactory;
      instance$delegate = LazyKt.lazy((Function0)new Lambda(localFactory)
      {
        public final PersonalTrackerApi invoke()
        {
          Object localObject = new HttpLoggingInterceptor((HttpLoggingInterceptor.Logger)new HttpLoggingInterceptor.Logger()
          {
            public void log(String paramAnonymous2String)
            {
              Intrinsics.checkParameterIsNotNull(paramAnonymous2String, "message");
              LogKt.toFile$default(paramAnonymous2String, null, "telemetry http", null, 5, null);
            }
          });
          ((HttpLoggingInterceptor)localObject).level(HttpLoggingInterceptor.Level.BASIC);
          Security.insertProviderAt(Conscrypt.newProvider(), 1);
          localObject = new OkHttpClient.Builder().readTimeout(10000L, TimeUnit.MILLISECONDS).writeTimeout(10000L, TimeUnit.MILLISECONDS).addInterceptor((Interceptor)localObject).build();
          return (PersonalTrackerApi)new Retrofit.Builder().baseUrl(BaseMainActivity.Companion.getCustomer().getAddress()).addConverterFactory((Converter.Factory)GsonConverterFactory.create()).client((OkHttpClient)localObject).build().create(PersonalTrackerApi.class);
        }
      });
    }
    
    private Factory() {}
    
    public final PersonalTrackerApi getInstance()
    {
      Lazy localLazy = instance$delegate;
      KProperty localKProperty = $$delegatedProperties[0];
      return (PersonalTrackerApi)localLazy.getValue();
    }
  }
}

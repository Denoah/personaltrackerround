package com.askgps.personaltrackercore;

import com.askgps.personaltrackercore.database.model.EmptyResponse;
import java.security.Security;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Interceptor;
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
import retrofit2.http.GET;
import retrofit2.http.Query;

@Metadata(bv={1, 0, 3}, d1={"\000,\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\002\b\002\n\002\020\006\n\002\b\002\n\002\020\002\n\002\b\002\bf\030\000 \r2\0020\001:\001\rJ6\020\002\032\b\022\004\022\0020\0040\0032\b\b\001\020\005\032\0020\0062\b\b\001\020\007\032\0020\0062\b\b\001\020\b\032\0020\t2\b\b\001\020\n\032\0020\tH'J\030\020\013\032\b\022\004\022\0020\f0\0032\b\b\001\020\005\032\0020\006H'?\006\016"}, d2={"Lcom/askgps/personaltrackercore/GaskarApi;", "", "start", "Lretrofit2/Call;", "Lcom/askgps/personaltrackercore/database/model/EmptyResponse;", "imei", "", "idxid", "latitude", "", "longitude", "stop", "", "Factory", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public abstract interface GaskarApi
{
  public static final Factory Factory = Factory.$$INSTANCE;
  
  @GET("/card/rest/workshift/start")
  public abstract Call<EmptyResponse> start(@Query("deviceEUI") String paramString1, @Query("idxid") String paramString2, @Query("latitude") double paramDouble1, @Query("longitude") double paramDouble2);
  
  @GET("/card/rest/workshift/stop")
  public abstract Call<Unit> stop(@Query("deviceEUI") String paramString);
  
  @Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\t\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\016\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\016\020\t\032\0020\n2\006\020\013\032\0020\fR\016\020\003\032\0020\004X?T?\006\002\n\000R\016\020\005\032\0020\006X?\004?\006\002\n\000R\016\020\007\032\0020\bX?\004?\006\002\n\000?\006\r"}, d2={"Lcom/askgps/personaltrackercore/GaskarApi$Factory;", "", "()V", "TIMEOUT", "", "httpClient", "Lokhttp3/OkHttpClient;", "logging", "Lokhttp3/logging/HttpLoggingInterceptor;", "getInstance", "Lcom/askgps/personaltrackercore/GaskarApi;", "url", "", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
  public static final class Factory
  {
    private static final long TIMEOUT = 10000L;
    private static final OkHttpClient httpClient = new OkHttpClient.Builder().readTimeout(10000L, TimeUnit.MILLISECONDS).writeTimeout(10000L, TimeUnit.MILLISECONDS).addInterceptor((Interceptor)logging).build();
    private static final HttpLoggingInterceptor logging;
    
    static
    {
      HttpLoggingInterceptor localHttpLoggingInterceptor = new HttpLoggingInterceptor((HttpLoggingInterceptor.Logger)new HttpLoggingInterceptor.Logger()
      {
        public void log(String paramAnonymousString)
        {
          Intrinsics.checkParameterIsNotNull(paramAnonymousString, "message");
          LogKt.toFile$default(paramAnonymousString, null, "gaskar http", null, 5, null);
        }
      });
      localHttpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BASIC);
      logging = localHttpLoggingInterceptor;
    }
    
    private Factory() {}
    
    public final GaskarApi getInstance(String paramString)
    {
      Intrinsics.checkParameterIsNotNull(paramString, "url");
      Security.insertProviderAt(Conscrypt.newProvider(), 1);
      paramString = new Retrofit.Builder().baseUrl("http://watch.telemetry.mos.ru").addConverterFactory((Converter.Factory)GsonConverterFactory.create()).client(httpClient).build().create(GaskarApi.class);
      Intrinsics.checkExpressionValueIsNotNull(paramString, "Retrofit.Builder()\n//   …te(GaskarApi::class.java)");
      return (GaskarApi)paramString;
    }
  }
}

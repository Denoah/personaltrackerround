package com.askgps.personaltrackercore;

import com.askgps.personaltrackercore.database.model.IdentixOneResponse;
import java.security.Security;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
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
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\003\bf\030\000 \n2\0020\001:\001\nJ,\020\002\032\b\022\004\022\0020\0040\0032\b\b\001\020\005\032\0020\0062\b\b\003\020\007\032\0020\b2\b\b\003\020\t\032\0020\bH'?\006\013"}, d2={"Lcom/askgps/personaltrackercore/IdentixOneApi;", "", "searchUser", "Lretrofit2/Call;", "Lcom/askgps/personaltrackercore/database/model/IdentixOneResponse;", "photo", "Lokhttp3/MultipartBody$Part;", "asm", "", "liveness", "Factory", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public abstract interface IdentixOneApi
{
  public static final Factory Factory = Factory.$$INSTANCE;
  public static final String HOST = "api.identix.one";
  public static final String TOKEN = "a2879be8028a654f81c5a94b7a2472e943efb927a14ede776a5f5d039717bffdffad94e14c1e68e0248be4bda355d2b747e156e508ac5fc8dadec5b600ce7bdc";
  public static final String URL = "https://api.identix.one";
  
  @Headers({"Authorization: Token a2879be8028a654f81c5a94b7a2472e943efb927a14ede776a5f5d039717bffdffad94e14c1e68e0248be4bda355d2b747e156e508ac5fc8dadec5b600ce7bdc"})
  @Multipart
  @POST("/v1/persons/search/")
  public abstract Call<IdentixOneResponse> searchUser(@Part MultipartBody.Part paramPart, @Part("asm") boolean paramBoolean1, @Part("liveness") boolean paramBoolean2);
  
  @Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
  public static final class DefaultImpls {}
  
  @Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\000\n\002\020\t\n\002\b\003\n\002\030\002\n\002\b\005\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\016\020\003\032\0020\004X?T?\006\002\n\000R\016\020\005\032\0020\006X?T?\006\002\n\000R\016\020\007\032\0020\004X?T?\006\002\n\000R\016\020\b\032\0020\004X?T?\006\002\n\000R\033\020\t\032\0020\n8FX??\002?\006\f\n\004\b\r\020\016\032\004\b\013\020\f?\006\017"}, d2={"Lcom/askgps/personaltrackercore/IdentixOneApi$Factory;", "", "()V", "HOST", "", "TIMEOUT", "", "TOKEN", "URL", "instance", "Lcom/askgps/personaltrackercore/IdentixOneApi;", "getInstance", "()Lcom/askgps/personaltrackercore/IdentixOneApi;", "instance$delegate", "Lkotlin/Lazy;", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
  public static final class Factory
  {
    public static final String HOST = "api.identix.one";
    private static final long TIMEOUT = 10000L;
    public static final String TOKEN = "a2879be8028a654f81c5a94b7a2472e943efb927a14ede776a5f5d039717bffdffad94e14c1e68e0248be4bda355d2b747e156e508ac5fc8dadec5b600ce7bdc";
    public static final String URL = "https://api.identix.one";
    private static final Lazy instance$delegate;
    
    static
    {
      Factory localFactory = new Factory();
      $$INSTANCE = localFactory;
      instance$delegate = LazyKt.lazy((Function0)new Lambda(localFactory)
      {
        public final IdentixOneApi invoke()
        {
          Object localObject = new HttpLoggingInterceptor((HttpLoggingInterceptor.Logger)new HttpLoggingInterceptor.Logger()
          {
            public void log(String paramAnonymous2String)
            {
              Intrinsics.checkParameterIsNotNull(paramAnonymous2String, "message");
              LogKt.toFile$default(paramAnonymous2String, null, "http", null, 5, null);
            }
          });
          ((HttpLoggingInterceptor)localObject).level(HttpLoggingInterceptor.Level.HEADERS);
          Security.insertProviderAt(Conscrypt.newProvider(), 1);
          localObject = new OkHttpClient.Builder().readTimeout(10000L, TimeUnit.MILLISECONDS).writeTimeout(10000L, TimeUnit.MILLISECONDS).addInterceptor((Interceptor)localObject).build();
          return (IdentixOneApi)new Retrofit.Builder().baseUrl("https://api.identix.one").addConverterFactory((Converter.Factory)GsonConverterFactory.create()).client((OkHttpClient)localObject).build().create(IdentixOneApi.class);
        }
      });
    }
    
    private Factory() {}
    
    public final IdentixOneApi getInstance()
    {
      Lazy localLazy = instance$delegate;
      KProperty localKProperty = $$delegatedProperties[0];
      return (IdentixOneApi)localLazy.getValue();
    }
  }
}

package io.fabric.sdk.android.services.common;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.lang.reflect.Method;

final class FirebaseAppImpl
  implements FirebaseApp
{
  private static final String FIREBASE_APP_CLASS = "com.google.firebase.FirebaseApp";
  private static final String GET_INSTANCE_METHOD = "getInstance";
  private static final String IS_DATA_COLLECTION_ENABLED_METHOD = "isDataCollectionDefaultEnabled";
  private final Object firebaseAppInstance;
  private final Method isDataCollectionDefaultEnabledMethod;
  
  private FirebaseAppImpl(Class paramClass, Object paramObject)
    throws NoSuchMethodException
  {
    this.firebaseAppInstance = paramObject;
    this.isDataCollectionDefaultEnabledMethod = paramClass.getDeclaredMethod("isDataCollectionDefaultEnabled", new Class[0]);
  }
  
  public static FirebaseApp getInstance(Context paramContext)
  {
    try
    {
      paramContext = paramContext.getClassLoader().loadClass("com.google.firebase.FirebaseApp");
      paramContext = new FirebaseAppImpl(paramContext, paramContext.getDeclaredMethod("getInstance", new Class[0]).invoke(paramContext, new Object[0]));
      return paramContext;
    }
    catch (Exception paramContext)
    {
      Fabric.getLogger().d("Fabric", "Unexpected error loading FirebaseApp instance.", paramContext);
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      paramContext = Fabric.getLogger();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Could not find method: ");
      localStringBuilder.append(localNoSuchMethodException.getMessage());
      paramContext.d("Fabric", localStringBuilder.toString());
    }
    catch (ClassNotFoundException paramContext)
    {
      Fabric.getLogger().d("Fabric", "Could not find class: com.google.firebase.FirebaseApp");
    }
    return null;
  }
  
  public boolean isDataCollectionDefaultEnabled()
  {
    try
    {
      boolean bool = ((Boolean)this.isDataCollectionDefaultEnabledMethod.invoke(this.firebaseAppInstance, new Object[0])).booleanValue();
      return bool;
    }
    catch (Exception localException)
    {
      Fabric.getLogger().d("Fabric", "Cannot check isDataCollectionDefaultEnabled on FirebaseApp.", localException);
    }
    return false;
  }
}

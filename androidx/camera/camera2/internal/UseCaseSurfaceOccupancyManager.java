package androidx.camera.camera2.internal;

import androidx.camera.core.ImageCapture;
import androidx.camera.core.UseCase;
import androidx.camera.core.VideoCapture;
import java.util.Iterator;
import java.util.List;

final class UseCaseSurfaceOccupancyManager
{
  private UseCaseSurfaceOccupancyManager() {}
  
  static void checkUseCaseLimitNotExceeded(List<UseCase> paramList1, List<UseCase> paramList2)
  {
    if ((paramList2 != null) && (!paramList2.isEmpty()))
    {
      int i = 0;
      int j = 0;
      if (paramList1 != null)
      {
        paramList1 = paramList1.iterator();
        int k = 0;
        for (;;)
        {
          i = j;
          m = k;
          if (!paramList1.hasNext()) {
            break;
          }
          UseCase localUseCase = (UseCase)paramList1.next();
          if ((localUseCase instanceof ImageCapture)) {
            j++;
          } else if ((localUseCase instanceof VideoCapture)) {
            k++;
          }
        }
      }
      int m = 0;
      paramList1 = paramList2.iterator();
      while (paramList1.hasNext())
      {
        paramList2 = (UseCase)paramList1.next();
        if ((paramList2 instanceof ImageCapture)) {
          i++;
        } else if ((paramList2 instanceof VideoCapture)) {
          m++;
        }
      }
      if (i <= 1)
      {
        if (m <= 1) {
          return;
        }
        throw new IllegalArgumentException("Exceeded max simultaneously bound video capture use cases.");
      }
      throw new IllegalArgumentException("Exceeded max simultaneously bound image capture use cases.");
    }
    throw new IllegalArgumentException("No new use cases to be bound.");
  }
}

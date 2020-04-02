package com.askgps.personaltrackercore.database;

import androidx.lifecycle.LiveData;
import com.askgps.personaltrackercore.database.model.PhoneNumber;
import java.util.List;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\020\000\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020 \n\000\bg\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H'J\020\020\006\032\0020\0032\006\020\004\032\0020\005H'J\024\020\007\032\016\022\n\022\b\022\004\022\0020\0050\t0\bH'?\006\n"}, d2={"Lcom/askgps/personaltrackercore/database/PhoneBookDao;", "", "addNumber", "", "phoneNumber", "Lcom/askgps/personaltrackercore/database/model/PhoneNumber;", "deleteNumber", "getNumbers", "Landroidx/lifecycle/LiveData;", "", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public abstract interface PhoneBookDao
{
  public abstract void addNumber(PhoneNumber paramPhoneNumber);
  
  public abstract void deleteNumber(PhoneNumber paramPhoneNumber);
  
  public abstract LiveData<List<PhoneNumber>> getNumbers();
}

package androidx.work;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class OverwritingInputMerger
  extends InputMerger
{
  public OverwritingInputMerger() {}
  
  public Data merge(List<Data> paramList)
  {
    Data.Builder localBuilder = new Data.Builder();
    HashMap localHashMap = new HashMap();
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      localHashMap.putAll(((Data)paramList.next()).getKeyValueMap());
    }
    localBuilder.putAll(localHashMap);
    return localBuilder.build();
  }
}

package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.VersionRequirement;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.VersionRequirementTable;

public final class VersionRequirementTable
{
  public static final Companion Companion = new Companion(null);
  private static final VersionRequirementTable EMPTY = new VersionRequirementTable(CollectionsKt.emptyList());
  private final List<ProtoBuf.VersionRequirement> infos;
  
  private VersionRequirementTable(List<ProtoBuf.VersionRequirement> paramList)
  {
    this.infos = paramList;
  }
  
  public final ProtoBuf.VersionRequirement get(int paramInt)
  {
    return (ProtoBuf.VersionRequirement)CollectionsKt.getOrNull(this.infos, paramInt);
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    public final VersionRequirementTable create(ProtoBuf.VersionRequirementTable paramVersionRequirementTable)
    {
      Intrinsics.checkParameterIsNotNull(paramVersionRequirementTable, "table");
      if (paramVersionRequirementTable.getRequirementCount() == 0)
      {
        paramVersionRequirementTable = ((Companion)this).getEMPTY();
      }
      else
      {
        paramVersionRequirementTable = paramVersionRequirementTable.getRequirementList();
        Intrinsics.checkExpressionValueIsNotNull(paramVersionRequirementTable, "table.requirementList");
        paramVersionRequirementTable = new VersionRequirementTable(paramVersionRequirementTable, null);
      }
      return paramVersionRequirementTable;
    }
    
    public final VersionRequirementTable getEMPTY()
    {
      return VersionRequirementTable.access$getEMPTY$cp();
    }
  }
}

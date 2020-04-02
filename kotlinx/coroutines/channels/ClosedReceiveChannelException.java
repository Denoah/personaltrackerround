package kotlinx.coroutines.channels;

import java.util.NoSuchElementException;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\026\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\002\b\002\030\0002\0060\001j\002`\002B\017\022\b\020\003\032\004\030\0010\004?\006\002\020\005?\006\006"}, d2={"Lkotlinx/coroutines/channels/ClosedReceiveChannelException;", "Ljava/util/NoSuchElementException;", "Lkotlin/NoSuchElementException;", "message", "", "(Ljava/lang/String;)V", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class ClosedReceiveChannelException
  extends NoSuchElementException
{
  public ClosedReceiveChannelException(String paramString)
  {
    super(paramString);
  }
}

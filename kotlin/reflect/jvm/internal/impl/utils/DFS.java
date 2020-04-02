package kotlin.reflect.jvm.internal.impl.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import kotlin.jvm.functions.Function1;

public class DFS
{
  public static <N, R> R dfs(Collection<N> paramCollection, Neighbors<N> paramNeighbors, NodeHandler<N, R> paramNodeHandler)
  {
    if (paramCollection == null) {
      $$$reportNull$$$0(4);
    }
    if (paramNeighbors == null) {
      $$$reportNull$$$0(5);
    }
    if (paramNodeHandler == null) {
      $$$reportNull$$$0(6);
    }
    return dfs(paramCollection, paramNeighbors, new VisitedWithSet(), paramNodeHandler);
  }
  
  public static <N, R> R dfs(Collection<N> paramCollection, Neighbors<N> paramNeighbors, Visited<N> paramVisited, NodeHandler<N, R> paramNodeHandler)
  {
    if (paramCollection == null) {
      $$$reportNull$$$0(0);
    }
    if (paramNeighbors == null) {
      $$$reportNull$$$0(1);
    }
    if (paramVisited == null) {
      $$$reportNull$$$0(2);
    }
    if (paramNodeHandler == null) {
      $$$reportNull$$$0(3);
    }
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext()) {
      doDfs(paramCollection.next(), paramNeighbors, paramVisited, paramNodeHandler);
    }
    return paramNodeHandler.result();
  }
  
  public static <N> void doDfs(N paramN, Neighbors<N> paramNeighbors, Visited<N> paramVisited, NodeHandler<N, ?> paramNodeHandler)
  {
    if (paramN == null) {
      $$$reportNull$$$0(22);
    }
    if (paramNeighbors == null) {
      $$$reportNull$$$0(23);
    }
    if (paramVisited == null) {
      $$$reportNull$$$0(24);
    }
    if (paramNodeHandler == null) {
      $$$reportNull$$$0(25);
    }
    if (!paramVisited.checkAndMarkVisited(paramN)) {
      return;
    }
    if (!paramNodeHandler.beforeChildren(paramN)) {
      return;
    }
    Iterator localIterator = paramNeighbors.getNeighbors(paramN).iterator();
    while (localIterator.hasNext()) {
      doDfs(localIterator.next(), paramNeighbors, paramVisited, paramNodeHandler);
    }
    paramNodeHandler.afterChildren(paramN);
  }
  
  public static <N> Boolean ifAny(Collection<N> paramCollection, Neighbors<N> paramNeighbors, Function1<N, Boolean> paramFunction1)
  {
    if (paramCollection == null) {
      $$$reportNull$$$0(7);
    }
    if (paramNeighbors == null) {
      $$$reportNull$$$0(8);
    }
    if (paramFunction1 == null) {
      $$$reportNull$$$0(9);
    }
    (Boolean)dfs(paramCollection, paramNeighbors, new AbstractNodeHandler()
    {
      public boolean beforeChildren(N paramAnonymousN)
      {
        if (((Boolean)this.val$predicate.invoke(paramAnonymousN)).booleanValue()) {
          this.val$result[0] = true;
        }
        return this.val$result[0] ^ 0x1;
      }
      
      public Boolean result()
      {
        return Boolean.valueOf(this.val$result[0]);
      }
    });
  }
  
  public static abstract class AbstractNodeHandler<N, R>
    implements DFS.NodeHandler<N, R>
  {
    public AbstractNodeHandler() {}
    
    public void afterChildren(N paramN) {}
    
    public boolean beforeChildren(N paramN)
    {
      return true;
    }
  }
  
  public static abstract class CollectingNodeHandler<N, R, C extends Iterable<R>>
    extends DFS.AbstractNodeHandler<N, C>
  {
    protected final C result;
    
    protected CollectingNodeHandler(C paramC)
    {
      this.result = paramC;
    }
    
    public C result()
    {
      Iterable localIterable = this.result;
      if (localIterable == null) {
        $$$reportNull$$$0(1);
      }
      return localIterable;
    }
  }
  
  public static abstract interface Neighbors<N>
  {
    public abstract Iterable<? extends N> getNeighbors(N paramN);
  }
  
  public static abstract interface NodeHandler<N, R>
  {
    public abstract void afterChildren(N paramN);
    
    public abstract boolean beforeChildren(N paramN);
    
    public abstract R result();
  }
  
  public static abstract class NodeHandlerWithListResult<N, R>
    extends DFS.CollectingNodeHandler<N, R, LinkedList<R>>
  {
    protected NodeHandlerWithListResult()
    {
      super();
    }
  }
  
  public static abstract interface Visited<N>
  {
    public abstract boolean checkAndMarkVisited(N paramN);
  }
  
  public static class VisitedWithSet<N>
    implements DFS.Visited<N>
  {
    private final Set<N> visited;
    
    public VisitedWithSet()
    {
      this(new HashSet());
    }
    
    public VisitedWithSet(Set<N> paramSet)
    {
      this.visited = paramSet;
    }
    
    public boolean checkAndMarkVisited(N paramN)
    {
      return this.visited.add(paramN);
    }
  }
}

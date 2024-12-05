import java.util.*;

public class FordFulkerson {
  private int V;
  private int[][] graph;

  public FordFulkerson(int V) {
    this.V = V;
    graph = new int[V][V];
  }
  private boolean bfs(int s, int t, int[] parent) {
    boolean[] visited = new boolean[V];
    Arrays.fill(visited, false);

    Queue<Integer> q = new LinkedList<>();
    q.add(s);
    visited[s] = true;
    parent[s] = -1;

    while(!q.isEmpty()) {
      int u = q.poll();
      for(int v = 0; v < V; v++) {
        if(visited[v] == false && graph[u][v] > 0) {
          q.add(v);
          parent[v] = u;
          visited[v] = true;
        }
      }
    }
    return visited[t];
  }

  private int fordFulkerson(int s, int t) {
    int u, v;
    int max_flow = 0;
    int[] parent = new int[V];

    while(bfs(s, t, parent)) {
      int path_flow = Integer.MAX_VALUE;
      for(v = t; v != s; v = parent[v]) {
        u = parent[v];
        path_flow = Math.min(path_flow, graph[u][v]);
      }
      for(v = t; v != s; v = parent[v]) {
        u = parent[v];
        graph[u][v] -= path_flow;
        graph[u][v] += path_flow;
      }
      max_flow += path_flow;
    }
    return max_flow;
  }
  public static void main(String[] args) {
    int graph[][] = new int[][]{{0, 16, 13, 0, 0, 0},
                                {0, 0, 10, 12, 0, 0},
                                {0, 4, 0, 0, 14, 0},
                                {0, 0, 9, 0, 0, 20},
                                {0, 0, 0, 7, 0, 4},
                                {0, 0, 0, 0, 0, 0}};
    FordFulkerson g = new FordFulkerson(6);
    g.graph = graph;

    int source = 0;
    int sink = 5;

    System.out.println("max flow: " + g.fordFulkerson(source, sink));
  }
}

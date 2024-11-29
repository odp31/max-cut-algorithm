import java.util.*;

public class GreedyMaxCut {
  public static int maxCut(int[][] graph)
  {
    int n = graph.length;
    int[] partition = new int[n];     // 0 for set A, 1 for set B
    Arrays.fill(partition, -1);       // initially all vertices are unassigned
    // choose random vertex to start with
    int startVertex = (int) (Math.random() * n);
    partition[startVertex] = 0;

    int maxCut = 0;
    for(int i = 0; i < n; i++)
      {
        if(partition[i] == -1)
        {
          int cutEdges = 0;
          for(int j = 0; j < n; j++)
            {
              if(partition[j] != -1 && graph[i][j] == 1)
              {
                cutEdges++;
              }
            }
          partition[i] = cutEdges > n / 2 ? 1 : 0;
          maxCut += cutEdges;
        }
      }
    return maxCut;
  }
  public static void main(String[] args)
  {
    int[][] graph = {
      {0, 1, 1, 0},
      {1, 0, 1, 1},
      {1, 1, 0, 1}, 
      {0, 1, 1, 0}
    };
    int maxCut = maxCut(graph);
    System.out.println("maximum cut: " + maxCut);
  }
}

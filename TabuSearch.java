// avoids getting stuck in local optima by maintaining a tabu list
// list stores recently visited solutions preventing the algorithm from revisiting them and cycling

import java.util.*;

public class TabuSearch
  {
    private static final int MAX_ITERATIONS = 100;
    private static final int TABU_TENURE = 10;

    public static int[] tabuSearch(int[][] matrix)
    {
      int n = matrix.length;
      int[] currentSolution = new int[n];
      Random rand = new Random();
      for(int i = 0; i < n; i ++) {
        currentSolution[i] = rand.nextInt(2);
      }
      int currentCost = cost(matrix, currentSolution);
      int bestSolution[] = currentSolution.clone();
      int bestCost = currentCost;
      // Tabu List
      Set<String> tabuList = new HashSet<>();

      for(int i = 0; i < MAX_ITERATIONS; i++) {
        List<int[]> neighbors = generateNeighbors(currentSolution);
        int bestNeighborCost = Integer.MIN_VALUE;
        int[] bestNeighbor = null;

        for(int[] neighbor : neighbors) {
          String neighborString = Arrays.toString(neighbor);
          if(!tabuList.contains(neighborString)) {
            int neighborCost = cost(matrix, neighbor);
            if(neighborCost > bestNeighborCost) {
              bestNeighborCost = neighborCost;
              bestNeighbor = neighbor;
            }
          }
        }
        if(bestNeighbor != null) {
          currentSolution = bestNeighbor;
          currentCost = bestNeighborCost;
          bestCost = Math.max(bestCost, currentCost);
          tabuList.add(Arrays.toString(currentSolution));
          if(tabuList.size() > TABU_TENURE) {
            tabuList.remove(tabuList.iterator().next());
          }
          else {
            int randomIndex = rand.nextInt(neighbors.size());
            currentSolution = neighbors.get(randomIndex);
            currentCost = cost(matrix, currentSolution):
            tabuList.add(Arrays.toString(currentSolution));
            if(tabuList.size() > TABU_TENURE) {
              tabuList.remove(tabuList.iterator().next());
            }
          }
        }
        return bestSolution;
      }
    }
    

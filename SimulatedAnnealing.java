// meta-heuristic optimization algorithm inspired by the annealing process in metallurgy
// starts with an initial solution and iteratively explores the solution space 

import java.util.Random;

public class SimulatedAnnealing
  {
    public static int simulatedAnnealing(int[][] matrix)
    {
      int n = matrix.length;
      int[] currentSolution = new int[n];
      Random rand = new Random();
      for(int i = 0; i < n; i++) {
        currentSolution[i] = rand.nextInt(2);
      }
      int currentCost = cost(matrix, currentSolution);
      int bestSolution[] = currentSolution.clone();
      int bestCost = currentCost;

      double temperature = 1000.0;
      double coolingRate = 0.99;

      while(temperature > 1) {
        int[] neighbor = currentSolution.clone();
        int randomIndex = rand.nextInt(n);
        neighbor[randomIndex] = 1 - neighbor[randomIndex];
        int neighborCost = cost(matrix, neighbor);

        double deltaE = neighborCost - currentCost;
        if(deltaE > 0 || Math.random() < Math.exp(-deltaE / temperature)) {
          currentSolution = neighbor;
          currentCost = neighborCost;
          bestCost = Math.max(bestCost, currentCost);
        }
        temperature *= coolingRate;
      }
      return bestCost;
    }
  }

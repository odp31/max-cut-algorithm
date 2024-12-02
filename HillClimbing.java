// simple optimization algorithm that iteratively moves towards a better solution by choosing the best neighbor 
// analogous to climbing a hill where you always take the steepest uphill step

public class HillClimbing {
  public static int hillClimbing(int[][] matrix) {
    int n = matrix.length;
    int[] solution = new int[n];
    // initialize solution randomly
    for(int i = 0; i < n; i++) {
      solution[i] = (int) (Math.random() * 2);
    }
    int currentCost = cost(matrix, solution);
    int bestCost = currentCost;
    while(true) {
      boolean improved = false;
      for(int i = 0; i < n; i++) {
        int[] neighbor = solution.clone();
        neighbor[i] = 1 - neighbor[i];   // flip the bit
        int neighborCost = cost(matrix, neighbor);
        if(neighborCost > currentCost) {
          solution = neighbor;
          currentCost = neighborCost;
          improved = true;
          break;
        }
      }
      if(!improved) {
        break;
      }
      bestCost = Math.max(bestCost, currentCost);
    }
    return bestCost;
  }
  private static int cost(int[][] matrix, int[] solution)
  {
    int cost = 0;
    for(int i = 0; i < matrix.length; i++) {
      for(int j = i + 1; j < matrix.length; j++) {
        if(solution[i] != solution[j]) {
          cost += matrix[i][j];
        }
      }
    }
    return cost;
  }
}

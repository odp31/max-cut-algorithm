import com.example.sdp.SDP;
import com.example.sdp.SDPSolution;

public class GoemansWilliamson {
  public static int[] maxCut(int[][] graph) {
    int n = graph.length;
    // formulate the SDP
    SDP sdp = new SDP(n);
    sdp.addConstraint(SDP.diagonalConstraint(n));
    sdp.addConstraint(SDP.positiveSemidefiniteConstraint(n));
    sdp.setObjective(graph);

    // solve SDP
    SDPSolution solution = sdp.solve();

    // round the solution
    double[][] X = solution.getX();
    double[] v = new double[n];
    for(int i = 0; i < n; i++) {
      v[i] = Math.random() * 2 - 1;
    }
    int[] partition = new int[n];
    for(int i = 0; i < n; i++) {
      partition[i] = (int) Math.signum(X[i] * v);
    }
    return partition;
  }
}

# formulates problem as a semidefinite program (SDP); involves relaxing the integer constraints
# of the Max-Cut problem to continuous constraints
# solves the SDP using a solver like CVXPY or Gurobi 
# CVXPY: python based modeling language for convex optimization 

import cvxpy as cp

def max_cut_goemans_williamson(graph):
  n = len(graph)
  X = cp.Variable((n, n), symmetric=True)

  constraints = [
    cp.diag(X) == 1,
    X >> 0
  ]
  obj = cp.Maximize(cp.sum(cp.multiply(graph, X))) / 2)
  prob = cp.Problem(obj, constraints)
  prob.solve()

  # Rounding
  v = np.random.randn(n)
  partition = np.sign(X.value @ v)
  partition[partition == 0] = 1

  # calculate cut value
  cut_value = 0
  for i in range(n):
    for j in range(i + 1, n):
      cut_value += graph[i][j] * (partition[i] != partition[j])
  return partition, cut_value 
